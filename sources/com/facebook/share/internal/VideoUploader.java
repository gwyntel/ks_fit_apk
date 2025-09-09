package com.facebook.share.internal;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.util.Log;
import androidx.media3.common.PlaybackException;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookGraphResponseException;
import com.facebook.FacebookRequestError;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import com.facebook.internal.WorkQueue;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareVideo;
import com.facebook.share.model.ShareVideoContent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class VideoUploader {
    private static final String ERROR_BAD_SERVER_RESPONSE = "Unexpected error in server response";
    private static final String ERROR_UPLOAD = "Video upload failed";
    private static final int MAX_RETRIES_PER_PHASE = 2;
    private static final String PARAM_DESCRIPTION = "description";
    private static final String PARAM_END_OFFSET = "end_offset";
    private static final String PARAM_FILE_SIZE = "file_size";
    private static final String PARAM_REF = "ref";
    private static final String PARAM_SESSION_ID = "upload_session_id";
    private static final String PARAM_START_OFFSET = "start_offset";
    private static final String PARAM_TITLE = "title";
    private static final String PARAM_UPLOAD_PHASE = "upload_phase";
    private static final String PARAM_VALUE_UPLOAD_FINISH_PHASE = "finish";
    private static final String PARAM_VALUE_UPLOAD_START_PHASE = "start";
    private static final String PARAM_VALUE_UPLOAD_TRANSFER_PHASE = "transfer";
    private static final String PARAM_VIDEO_FILE_CHUNK = "video_file_chunk";
    private static final String PARAM_VIDEO_ID = "video_id";
    private static final int RETRY_DELAY_BACK_OFF_FACTOR = 3;
    private static final int RETRY_DELAY_UNIT_MS = 5000;
    private static final String TAG = "VideoUploader";
    private static final int UPLOAD_QUEUE_MAX_CONCURRENT = 8;
    private static AccessTokenTracker accessTokenTracker;
    private static Handler handler;
    private static boolean initialized;
    private static WorkQueue uploadQueue = new WorkQueue(8);
    private static Set<UploadContext> pendingUploads = new HashSet();

    private static class FinishUploadWorkItem extends UploadWorkItemBase {
        static final Set<Integer> transientErrorCodes = new HashSet<Integer>() { // from class: com.facebook.share.internal.VideoUploader.FinishUploadWorkItem.1
            {
                add(1363011);
            }
        };

        public FinishUploadWorkItem(UploadContext uploadContext, int i2) {
            super(uploadContext, i2);
        }

        @Override // com.facebook.share.internal.VideoUploader.UploadWorkItemBase
        protected void enqueueRetry(int i2) {
            VideoUploader.enqueueUploadFinish(this.uploadContext, i2);
        }

        @Override // com.facebook.share.internal.VideoUploader.UploadWorkItemBase
        public Bundle getParameters() {
            Bundle bundle = new Bundle();
            Bundle bundle2 = this.uploadContext.params;
            if (bundle2 != null) {
                bundle.putAll(bundle2);
            }
            bundle.putString(VideoUploader.PARAM_UPLOAD_PHASE, VideoUploader.PARAM_VALUE_UPLOAD_FINISH_PHASE);
            bundle.putString(VideoUploader.PARAM_SESSION_ID, this.uploadContext.sessionId);
            Utility.putNonEmptyString(bundle, "title", this.uploadContext.title);
            Utility.putNonEmptyString(bundle, "description", this.uploadContext.description);
            Utility.putNonEmptyString(bundle, VideoUploader.PARAM_REF, this.uploadContext.ref);
            return bundle;
        }

        @Override // com.facebook.share.internal.VideoUploader.UploadWorkItemBase
        protected Set<Integer> getTransientErrorCodes() {
            return transientErrorCodes;
        }

        @Override // com.facebook.share.internal.VideoUploader.UploadWorkItemBase
        protected void handleError(FacebookException facebookException) {
            VideoUploader.logError(facebookException, "Video '%s' failed to finish uploading", this.uploadContext.videoId);
            endUploadWithFailure(facebookException);
        }

        @Override // com.facebook.share.internal.VideoUploader.UploadWorkItemBase
        protected void handleSuccess(JSONObject jSONObject) throws JSONException {
            if (jSONObject.getBoolean("success")) {
                issueResponseOnMainThread(null, this.uploadContext.videoId);
            } else {
                handleError(new FacebookException(VideoUploader.ERROR_BAD_SERVER_RESPONSE));
            }
        }
    }

    private static class StartUploadWorkItem extends UploadWorkItemBase {
        static final Set<Integer> transientErrorCodes = new HashSet<Integer>() { // from class: com.facebook.share.internal.VideoUploader.StartUploadWorkItem.1
            {
                add(Integer.valueOf(PlaybackException.ERROR_CODE_DRM_UNSPECIFIED));
            }
        };

        public StartUploadWorkItem(UploadContext uploadContext, int i2) {
            super(uploadContext, i2);
        }

        @Override // com.facebook.share.internal.VideoUploader.UploadWorkItemBase
        protected void enqueueRetry(int i2) {
            VideoUploader.enqueueUploadStart(this.uploadContext, i2);
        }

        @Override // com.facebook.share.internal.VideoUploader.UploadWorkItemBase
        public Bundle getParameters() {
            Bundle bundle = new Bundle();
            bundle.putString(VideoUploader.PARAM_UPLOAD_PHASE, "start");
            bundle.putLong(VideoUploader.PARAM_FILE_SIZE, this.uploadContext.videoSize);
            return bundle;
        }

        @Override // com.facebook.share.internal.VideoUploader.UploadWorkItemBase
        protected Set<Integer> getTransientErrorCodes() {
            return transientErrorCodes;
        }

        @Override // com.facebook.share.internal.VideoUploader.UploadWorkItemBase
        protected void handleError(FacebookException facebookException) {
            VideoUploader.logError(facebookException, "Error starting video upload", new Object[0]);
            endUploadWithFailure(facebookException);
        }

        @Override // com.facebook.share.internal.VideoUploader.UploadWorkItemBase
        protected void handleSuccess(JSONObject jSONObject) throws JSONException {
            this.uploadContext.sessionId = jSONObject.getString(VideoUploader.PARAM_SESSION_ID);
            this.uploadContext.videoId = jSONObject.getString(VideoUploader.PARAM_VIDEO_ID);
            VideoUploader.enqueueUploadChunk(this.uploadContext, jSONObject.getString(VideoUploader.PARAM_START_OFFSET), jSONObject.getString(VideoUploader.PARAM_END_OFFSET), 0);
        }
    }

    private static class TransferChunkWorkItem extends UploadWorkItemBase {
        static final Set<Integer> transientErrorCodes = new HashSet<Integer>() { // from class: com.facebook.share.internal.VideoUploader.TransferChunkWorkItem.1
            {
                add(1363019);
                add(1363021);
                add(1363030);
                add(1363033);
                add(1363041);
            }
        };
        private String chunkEnd;
        private String chunkStart;

        public TransferChunkWorkItem(UploadContext uploadContext, String str, String str2, int i2) {
            super(uploadContext, i2);
            this.chunkStart = str;
            this.chunkEnd = str2;
        }

        @Override // com.facebook.share.internal.VideoUploader.UploadWorkItemBase
        protected void enqueueRetry(int i2) {
            VideoUploader.enqueueUploadChunk(this.uploadContext, this.chunkStart, this.chunkEnd, i2);
        }

        @Override // com.facebook.share.internal.VideoUploader.UploadWorkItemBase
        public Bundle getParameters() throws IOException {
            Bundle bundle = new Bundle();
            bundle.putString(VideoUploader.PARAM_UPLOAD_PHASE, VideoUploader.PARAM_VALUE_UPLOAD_TRANSFER_PHASE);
            bundle.putString(VideoUploader.PARAM_SESSION_ID, this.uploadContext.sessionId);
            bundle.putString(VideoUploader.PARAM_START_OFFSET, this.chunkStart);
            byte[] chunk = VideoUploader.getChunk(this.uploadContext, this.chunkStart, this.chunkEnd);
            if (chunk == null) {
                throw new FacebookException("Error reading video");
            }
            bundle.putByteArray(VideoUploader.PARAM_VIDEO_FILE_CHUNK, chunk);
            return bundle;
        }

        @Override // com.facebook.share.internal.VideoUploader.UploadWorkItemBase
        protected Set<Integer> getTransientErrorCodes() {
            return transientErrorCodes;
        }

        @Override // com.facebook.share.internal.VideoUploader.UploadWorkItemBase
        protected void handleError(FacebookException facebookException) {
            VideoUploader.logError(facebookException, "Error uploading video '%s'", this.uploadContext.videoId);
            endUploadWithFailure(facebookException);
        }

        @Override // com.facebook.share.internal.VideoUploader.UploadWorkItemBase
        protected void handleSuccess(JSONObject jSONObject) throws JSONException {
            String string = jSONObject.getString(VideoUploader.PARAM_START_OFFSET);
            String string2 = jSONObject.getString(VideoUploader.PARAM_END_OFFSET);
            if (Utility.areObjectsEqual(string, string2)) {
                VideoUploader.enqueueUploadFinish(this.uploadContext, 0);
            } else {
                VideoUploader.enqueueUploadChunk(this.uploadContext, string, string2, 0);
            }
        }
    }

    private static class UploadContext {
        public final AccessToken accessToken;
        public final FacebookCallback<Sharer.Result> callback;
        public String chunkStart;
        public final String description;
        public final String graphNode;
        public boolean isCanceled;
        public Bundle params;
        public final String ref;
        public String sessionId;
        public final String title;
        public String videoId;
        public long videoSize;
        public InputStream videoStream;
        public final Uri videoUri;
        public WorkQueue.WorkItem workItem;

        /* JADX INFO: Access modifiers changed from: private */
        public void initialize() throws IOException {
            try {
                if (Utility.isFileUri(this.videoUri)) {
                    ParcelFileDescriptor parcelFileDescriptorOpen = ParcelFileDescriptor.open(new File(this.videoUri.getPath()), 268435456);
                    this.videoSize = parcelFileDescriptorOpen.getStatSize();
                    this.videoStream = new ParcelFileDescriptor.AutoCloseInputStream(parcelFileDescriptorOpen);
                } else {
                    if (!Utility.isContentUri(this.videoUri)) {
                        throw new FacebookException("Uri must be a content:// or file:// uri");
                    }
                    this.videoSize = Utility.getContentSize(this.videoUri);
                    this.videoStream = FacebookSdk.getApplicationContext().getContentResolver().openInputStream(this.videoUri);
                }
            } catch (FileNotFoundException e2) {
                Utility.closeQuietly(this.videoStream);
                throw e2;
            }
        }

        private UploadContext(ShareVideoContent shareVideoContent, String str, FacebookCallback<Sharer.Result> facebookCallback) {
            this.chunkStart = "0";
            this.accessToken = AccessToken.getCurrentAccessToken();
            this.videoUri = shareVideoContent.getVideo().getLocalUrl();
            this.title = shareVideoContent.getContentTitle();
            this.description = shareVideoContent.getContentDescription();
            this.ref = shareVideoContent.getRef();
            this.graphNode = str;
            this.callback = facebookCallback;
            this.params = shareVideoContent.getVideo().getParameters();
            if (!Utility.isNullOrEmpty(shareVideoContent.getPeopleIds())) {
                this.params.putString("tags", TextUtils.join(", ", shareVideoContent.getPeopleIds()));
            }
            if (!Utility.isNullOrEmpty(shareVideoContent.getPlaceId())) {
                this.params.putString("place", shareVideoContent.getPlaceId());
            }
            if (Utility.isNullOrEmpty(shareVideoContent.getRef())) {
                return;
            }
            this.params.putString(VideoUploader.PARAM_REF, shareVideoContent.getRef());
        }
    }

    private static abstract class UploadWorkItemBase implements Runnable {
        protected int completedRetries;
        protected UploadContext uploadContext;

        protected UploadWorkItemBase(UploadContext uploadContext, int i2) {
            this.uploadContext = uploadContext;
            this.completedRetries = i2;
        }

        private boolean attemptRetry(int i2) {
            if (this.completedRetries >= 2 || !getTransientErrorCodes().contains(Integer.valueOf(i2))) {
                return false;
            }
            VideoUploader.getHandler().postDelayed(new Runnable() { // from class: com.facebook.share.internal.VideoUploader.UploadWorkItemBase.1
                @Override // java.lang.Runnable
                public void run() {
                    UploadWorkItemBase uploadWorkItemBase = UploadWorkItemBase.this;
                    uploadWorkItemBase.enqueueRetry(uploadWorkItemBase.completedRetries + 1);
                }
            }, ((int) Math.pow(3.0d, this.completedRetries)) * 5000);
            return true;
        }

        protected void endUploadWithFailure(FacebookException facebookException) {
            issueResponseOnMainThread(facebookException, null);
        }

        protected abstract void enqueueRetry(int i2);

        protected void executeGraphRequestSynchronously(Bundle bundle) {
            UploadContext uploadContext = this.uploadContext;
            GraphResponse graphResponseExecuteAndWait = new GraphRequest(uploadContext.accessToken, String.format(Locale.ROOT, "%s/videos", uploadContext.graphNode), bundle, HttpMethod.POST, null).executeAndWait();
            if (graphResponseExecuteAndWait == null) {
                handleError(new FacebookException(VideoUploader.ERROR_BAD_SERVER_RESPONSE));
                return;
            }
            FacebookRequestError error = graphResponseExecuteAndWait.getError();
            JSONObject jSONObject = graphResponseExecuteAndWait.getJSONObject();
            if (error != null) {
                if (attemptRetry(error.getSubErrorCode())) {
                    return;
                }
                handleError(new FacebookGraphResponseException(graphResponseExecuteAndWait, VideoUploader.ERROR_UPLOAD));
            } else {
                if (jSONObject == null) {
                    handleError(new FacebookException(VideoUploader.ERROR_BAD_SERVER_RESPONSE));
                    return;
                }
                try {
                    handleSuccess(jSONObject);
                } catch (JSONException e2) {
                    endUploadWithFailure(new FacebookException(VideoUploader.ERROR_BAD_SERVER_RESPONSE, e2));
                }
            }
        }

        protected abstract Bundle getParameters() throws Exception;

        protected abstract Set<Integer> getTransientErrorCodes();

        protected abstract void handleError(FacebookException facebookException);

        protected abstract void handleSuccess(JSONObject jSONObject) throws JSONException;

        protected void issueResponseOnMainThread(final FacebookException facebookException, final String str) {
            VideoUploader.getHandler().post(new Runnable() { // from class: com.facebook.share.internal.VideoUploader.UploadWorkItemBase.2
                @Override // java.lang.Runnable
                public void run() throws IOException {
                    VideoUploader.issueResponse(UploadWorkItemBase.this.uploadContext, facebookException, str);
                }
            });
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.uploadContext.isCanceled) {
                endUploadWithFailure(null);
                return;
            }
            try {
                executeGraphRequestSynchronously(getParameters());
            } catch (FacebookException e2) {
                endUploadWithFailure(e2);
            } catch (Exception e3) {
                endUploadWithFailure(new FacebookException(VideoUploader.ERROR_UPLOAD, e3));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static synchronized void cancelAllRequests() {
        Iterator<UploadContext> it = pendingUploads.iterator();
        while (it.hasNext()) {
            it.next().isCanceled = true;
        }
    }

    private static synchronized void enqueueRequest(UploadContext uploadContext, Runnable runnable) {
        uploadContext.workItem = uploadQueue.addActiveWorkItem(runnable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void enqueueUploadChunk(UploadContext uploadContext, String str, String str2, int i2) {
        enqueueRequest(uploadContext, new TransferChunkWorkItem(uploadContext, str, str2, i2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void enqueueUploadFinish(UploadContext uploadContext, int i2) {
        enqueueRequest(uploadContext, new FinishUploadWorkItem(uploadContext, i2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void enqueueUploadStart(UploadContext uploadContext, int i2) {
        enqueueRequest(uploadContext, new StartUploadWorkItem(uploadContext, i2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static byte[] getChunk(UploadContext uploadContext, String str, String str2) throws IOException {
        int i2;
        if (!Utility.areObjectsEqual(str, uploadContext.chunkStart)) {
            logError(null, "Error reading video chunk. Expected chunk '%s'. Requested chunk '%s'.", uploadContext.chunkStart, str);
            return null;
        }
        int i3 = (int) (Long.parseLong(str2) - Long.parseLong(str));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[Math.min(8192, i3)];
        do {
            i2 = uploadContext.videoStream.read(bArr);
            if (i2 != -1) {
                byteArrayOutputStream.write(bArr, 0, i2);
                i3 -= i2;
                if (i3 == 0) {
                }
            }
            uploadContext.chunkStart = str2;
            return byteArrayOutputStream.toByteArray();
        } while (i3 >= 0);
        logError(null, "Error reading video chunk. Expected buffer length - '%d'. Actual - '%d'.", Integer.valueOf(i3 + i2), Integer.valueOf(i2));
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static synchronized Handler getHandler() {
        try {
            if (handler == null) {
                handler = new Handler(Looper.getMainLooper());
            }
        } catch (Throwable th) {
            throw th;
        }
        return handler;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void issueResponse(UploadContext uploadContext, FacebookException facebookException, String str) throws IOException {
        removePendingUpload(uploadContext);
        Utility.closeQuietly(uploadContext.videoStream);
        FacebookCallback<Sharer.Result> facebookCallback = uploadContext.callback;
        if (facebookCallback != null) {
            if (facebookException != null) {
                ShareInternalUtility.invokeOnErrorCallback(facebookCallback, facebookException);
            } else if (uploadContext.isCanceled) {
                ShareInternalUtility.invokeOnCancelCallback(facebookCallback);
            } else {
                ShareInternalUtility.invokeOnSuccessCallback(facebookCallback, str);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void logError(Exception exc, String str, Object... objArr) {
        Log.e(TAG, String.format(Locale.ROOT, str, objArr), exc);
    }

    private static void registerAccessTokenTracker() {
        accessTokenTracker = new AccessTokenTracker() { // from class: com.facebook.share.internal.VideoUploader.1
            @Override // com.facebook.AccessTokenTracker
            protected void onCurrentAccessTokenChanged(AccessToken accessToken, AccessToken accessToken2) {
                if (accessToken == null) {
                    return;
                }
                if (accessToken2 == null || !Utility.areObjectsEqual(accessToken2.getUserId(), accessToken.getUserId())) {
                    VideoUploader.cancelAllRequests();
                }
            }
        };
    }

    private static synchronized void removePendingUpload(UploadContext uploadContext) {
        pendingUploads.remove(uploadContext);
    }

    public static synchronized void uploadAsync(ShareVideoContent shareVideoContent, FacebookCallback<Sharer.Result> facebookCallback) throws FileNotFoundException {
        uploadAsync(shareVideoContent, "me", facebookCallback);
    }

    public static synchronized void uploadAsync(ShareVideoContent shareVideoContent, String str, FacebookCallback<Sharer.Result> facebookCallback) throws FileNotFoundException {
        try {
            if (!initialized) {
                registerAccessTokenTracker();
                initialized = true;
            }
            Validate.notNull(shareVideoContent, "videoContent");
            Validate.notNull(str, "graphNode");
            ShareVideo video = shareVideoContent.getVideo();
            Validate.notNull(video, "videoContent.video");
            Validate.notNull(video.getLocalUrl(), "videoContent.video.localUrl");
            UploadContext uploadContext = new UploadContext(shareVideoContent, str, facebookCallback);
            uploadContext.initialize();
            pendingUploads.add(uploadContext);
            enqueueUploadStart(uploadContext, 0);
        } catch (Throwable th) {
            throw th;
        }
    }
}
