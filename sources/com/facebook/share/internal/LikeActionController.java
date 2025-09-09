package com.facebook.share.internal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.media3.common.MimeTypes;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.FacebookRequestError;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestBatch;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.LoggingBehavior;
import com.facebook.appevents.InternalAppEventsLogger;
import com.facebook.internal.AnalyticsEvents;
import com.facebook.internal.AppCall;
import com.facebook.internal.BundleJSONConverter;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.internal.FileLruCache;
import com.facebook.internal.FragmentWrapper;
import com.facebook.internal.Logger;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.PlatformServiceClient;
import com.facebook.internal.Utility;
import com.facebook.internal.WorkQueue;
import com.facebook.share.internal.LikeContent;
import com.facebook.share.widget.LikeView;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Deprecated
/* loaded from: classes3.dex */
public class LikeActionController {

    @Deprecated
    public static final String ACTION_LIKE_ACTION_CONTROLLER_DID_ERROR = "com.facebook.sdk.LikeActionController.DID_ERROR";

    @Deprecated
    public static final String ACTION_LIKE_ACTION_CONTROLLER_DID_RESET = "com.facebook.sdk.LikeActionController.DID_RESET";

    @Deprecated
    public static final String ACTION_LIKE_ACTION_CONTROLLER_UPDATED = "com.facebook.sdk.LikeActionController.UPDATED";

    @Deprecated
    public static final String ACTION_OBJECT_ID_KEY = "com.facebook.sdk.LikeActionController.OBJECT_ID";
    private static final int ERROR_CODE_OBJECT_ALREADY_LIKED = 3501;

    @Deprecated
    public static final String ERROR_INVALID_OBJECT_ID = "Invalid Object Id";

    @Deprecated
    public static final String ERROR_PUBLISH_ERROR = "Unable to publish the like/unlike action";
    private static final String JSON_BOOL_IS_OBJECT_LIKED_KEY = "is_object_liked";
    private static final String JSON_BUNDLE_FACEBOOK_DIALOG_ANALYTICS_BUNDLE = "facebook_dialog_analytics_bundle";
    private static final String JSON_INT_OBJECT_TYPE_KEY = "object_type";
    private static final String JSON_INT_VERSION_KEY = "com.facebook.share.internal.LikeActionController.version";
    private static final String JSON_STRING_LIKE_COUNT_WITHOUT_LIKE_KEY = "like_count_string_without_like";
    private static final String JSON_STRING_LIKE_COUNT_WITH_LIKE_KEY = "like_count_string_with_like";
    private static final String JSON_STRING_OBJECT_ID_KEY = "object_id";
    private static final String JSON_STRING_SOCIAL_SENTENCE_WITHOUT_LIKE_KEY = "social_sentence_without_like";
    private static final String JSON_STRING_SOCIAL_SENTENCE_WITH_LIKE_KEY = "social_sentence_with_like";
    private static final String JSON_STRING_UNLIKE_TOKEN_KEY = "unlike_token";
    private static final String LIKE_ACTION_CONTROLLER_STORE = "com.facebook.LikeActionController.CONTROLLER_STORE_KEY";
    private static final String LIKE_ACTION_CONTROLLER_STORE_OBJECT_SUFFIX_KEY = "OBJECT_SUFFIX";
    private static final String LIKE_ACTION_CONTROLLER_STORE_PENDING_OBJECT_ID_KEY = "PENDING_CONTROLLER_KEY";
    private static final int LIKE_ACTION_CONTROLLER_VERSION = 3;
    private static final String LIKE_DIALOG_RESPONSE_LIKE_COUNT_STRING_KEY = "like_count_string";
    private static final String LIKE_DIALOG_RESPONSE_OBJECT_IS_LIKED_KEY = "object_is_liked";
    private static final String LIKE_DIALOG_RESPONSE_SOCIAL_SENTENCE_KEY = "social_sentence";
    private static final String LIKE_DIALOG_RESPONSE_UNLIKE_TOKEN_KEY = "unlike_token";
    private static final int MAX_CACHE_SIZE = 128;
    private static final int MAX_OBJECT_SUFFIX = 1000;
    private static final String TAG = "LikeActionController";
    private static AccessTokenTracker accessTokenTracker;
    private static FileLruCache controllerDiskCache;
    private static Handler handler;
    private static boolean isInitialized;
    private static String objectIdForPendingController;
    private static volatile int objectSuffix;
    private Bundle facebookDialogAnalyticsBundle;
    private boolean isObjectLiked;
    private boolean isObjectLikedOnServer;
    private boolean isPendingLikeOrUnlike;
    private String likeCountStringWithLike;
    private String likeCountStringWithoutLike;
    private InternalAppEventsLogger logger;
    private String objectId;
    private boolean objectIsPage;
    private LikeView.ObjectType objectType;
    private String socialSentenceWithLike;
    private String socialSentenceWithoutLike;
    private String unlikeToken;
    private String verifiedObjectId;
    private static final ConcurrentHashMap<String, LikeActionController> cache = new ConcurrentHashMap<>();
    private static WorkQueue mruCacheWorkQueue = new WorkQueue(1);
    private static WorkQueue diskIOWorkQueue = new WorkQueue(1);

    /* renamed from: com.facebook.share.internal.LikeActionController$12, reason: invalid class name */
    static /* synthetic */ class AnonymousClass12 {
        static final /* synthetic */ int[] $SwitchMap$com$facebook$share$widget$LikeView$ObjectType;

        static {
            int[] iArr = new int[LikeView.ObjectType.values().length];
            $SwitchMap$com$facebook$share$widget$LikeView$ObjectType = iArr;
            try {
                iArr[LikeView.ObjectType.PAGE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    private abstract class AbstractRequestWrapper implements RequestWrapper {
        protected FacebookRequestError error;
        protected String objectId;
        protected LikeView.ObjectType objectType;
        private GraphRequest request;

        protected AbstractRequestWrapper(String str, LikeView.ObjectType objectType) {
            this.objectId = str;
            this.objectType = objectType;
        }

        @Override // com.facebook.share.internal.LikeActionController.RequestWrapper
        public void addToBatch(GraphRequestBatch graphRequestBatch) {
            graphRequestBatch.add(this.request);
        }

        @Override // com.facebook.share.internal.LikeActionController.RequestWrapper
        public FacebookRequestError getError() {
            return this.error;
        }

        protected void processError(FacebookRequestError facebookRequestError) {
            Logger.log(LoggingBehavior.REQUESTS, LikeActionController.TAG, "Error running request for object '%s' with type '%s' : %s", this.objectId, this.objectType, facebookRequestError);
        }

        protected abstract void processSuccess(GraphResponse graphResponse);

        protected void setRequest(GraphRequest graphRequest) {
            this.request = graphRequest;
            graphRequest.setVersion(FacebookSdk.getGraphApiVersion());
            graphRequest.setCallback(new GraphRequest.Callback() { // from class: com.facebook.share.internal.LikeActionController.AbstractRequestWrapper.1
                @Override // com.facebook.GraphRequest.Callback
                public void onCompleted(GraphResponse graphResponse) {
                    AbstractRequestWrapper.this.error = graphResponse.getError();
                    AbstractRequestWrapper abstractRequestWrapper = AbstractRequestWrapper.this;
                    FacebookRequestError facebookRequestError = abstractRequestWrapper.error;
                    if (facebookRequestError != null) {
                        abstractRequestWrapper.processError(facebookRequestError);
                    } else {
                        abstractRequestWrapper.processSuccess(graphResponse);
                    }
                }
            });
        }
    }

    private static class CreateLikeActionControllerWorkItem implements Runnable {
        private CreationCallback callback;
        private String objectId;
        private LikeView.ObjectType objectType;

        CreateLikeActionControllerWorkItem(String str, LikeView.ObjectType objectType, CreationCallback creationCallback) {
            this.objectId = str;
            this.objectType = objectType;
            this.callback = creationCallback;
        }

        @Override // java.lang.Runnable
        public void run() throws Throwable {
            LikeActionController.createControllerForObjectIdAndType(this.objectId, this.objectType, this.callback);
        }
    }

    @Deprecated
    public interface CreationCallback {
        void onComplete(LikeActionController likeActionController, FacebookException facebookException);
    }

    private class GetEngagementRequestWrapper extends AbstractRequestWrapper {
        String likeCountStringWithLike;
        String likeCountStringWithoutLike;
        String socialSentenceStringWithLike;
        String socialSentenceStringWithoutLike;

        GetEngagementRequestWrapper(String str, LikeView.ObjectType objectType) {
            super(str, objectType);
            this.likeCountStringWithLike = LikeActionController.this.likeCountStringWithLike;
            this.likeCountStringWithoutLike = LikeActionController.this.likeCountStringWithoutLike;
            this.socialSentenceStringWithLike = LikeActionController.this.socialSentenceWithLike;
            this.socialSentenceStringWithoutLike = LikeActionController.this.socialSentenceWithoutLike;
            Bundle bundle = new Bundle();
            bundle.putString(GraphRequest.FIELDS_PARAM, "engagement.fields(count_string_with_like,count_string_without_like,social_sentence_with_like,social_sentence_without_like)");
            bundle.putString("locale", Locale.getDefault().toString());
            setRequest(new GraphRequest(AccessToken.getCurrentAccessToken(), str, bundle, HttpMethod.GET));
        }

        @Override // com.facebook.share.internal.LikeActionController.AbstractRequestWrapper
        protected void processError(FacebookRequestError facebookRequestError) {
            Logger.log(LoggingBehavior.REQUESTS, LikeActionController.TAG, "Error fetching engagement for object '%s' with type '%s' : %s", this.objectId, this.objectType, facebookRequestError);
            LikeActionController.this.logAppEventForError("get_engagement", facebookRequestError);
        }

        @Override // com.facebook.share.internal.LikeActionController.AbstractRequestWrapper
        protected void processSuccess(GraphResponse graphResponse) {
            JSONObject jSONObjectTryGetJSONObjectFromResponse = Utility.tryGetJSONObjectFromResponse(graphResponse.getJSONObject(), "engagement");
            if (jSONObjectTryGetJSONObjectFromResponse != null) {
                this.likeCountStringWithLike = jSONObjectTryGetJSONObjectFromResponse.optString("count_string_with_like", this.likeCountStringWithLike);
                this.likeCountStringWithoutLike = jSONObjectTryGetJSONObjectFromResponse.optString("count_string_without_like", this.likeCountStringWithoutLike);
                this.socialSentenceStringWithLike = jSONObjectTryGetJSONObjectFromResponse.optString(LikeActionController.JSON_STRING_SOCIAL_SENTENCE_WITH_LIKE_KEY, this.socialSentenceStringWithLike);
                this.socialSentenceStringWithoutLike = jSONObjectTryGetJSONObjectFromResponse.optString(LikeActionController.JSON_STRING_SOCIAL_SENTENCE_WITHOUT_LIKE_KEY, this.socialSentenceStringWithoutLike);
            }
        }
    }

    private class GetOGObjectIdRequestWrapper extends AbstractRequestWrapper {
        String verifiedObjectId;

        GetOGObjectIdRequestWrapper(String str, LikeView.ObjectType objectType) {
            super(str, objectType);
            Bundle bundle = new Bundle();
            bundle.putString(GraphRequest.FIELDS_PARAM, "og_object.fields(id)");
            bundle.putString("ids", str);
            setRequest(new GraphRequest(AccessToken.getCurrentAccessToken(), "", bundle, HttpMethod.GET));
        }

        @Override // com.facebook.share.internal.LikeActionController.AbstractRequestWrapper
        protected void processError(FacebookRequestError facebookRequestError) {
            if (facebookRequestError.getErrorMessage().contains("og_object")) {
                this.error = null;
            } else {
                Logger.log(LoggingBehavior.REQUESTS, LikeActionController.TAG, "Error getting the FB id for object '%s' with type '%s' : %s", this.objectId, this.objectType, facebookRequestError);
            }
        }

        @Override // com.facebook.share.internal.LikeActionController.AbstractRequestWrapper
        protected void processSuccess(GraphResponse graphResponse) {
            JSONObject jSONObjectOptJSONObject;
            JSONObject jSONObjectTryGetJSONObjectFromResponse = Utility.tryGetJSONObjectFromResponse(graphResponse.getJSONObject(), this.objectId);
            if (jSONObjectTryGetJSONObjectFromResponse == null || (jSONObjectOptJSONObject = jSONObjectTryGetJSONObjectFromResponse.optJSONObject("og_object")) == null) {
                return;
            }
            this.verifiedObjectId = jSONObjectOptJSONObject.optString("id");
        }
    }

    private class GetOGObjectLikesRequestWrapper extends AbstractRequestWrapper implements LikeRequestWrapper {
        private final String objectId;
        private boolean objectIsLiked;
        private final LikeView.ObjectType objectType;
        private String unlikeToken;

        GetOGObjectLikesRequestWrapper(String str, LikeView.ObjectType objectType) {
            super(str, objectType);
            this.objectIsLiked = LikeActionController.this.isObjectLiked;
            this.objectId = str;
            this.objectType = objectType;
            Bundle bundle = new Bundle();
            bundle.putString(GraphRequest.FIELDS_PARAM, "id,application");
            bundle.putString("object", str);
            setRequest(new GraphRequest(AccessToken.getCurrentAccessToken(), "me/og.likes", bundle, HttpMethod.GET));
        }

        @Override // com.facebook.share.internal.LikeActionController.LikeRequestWrapper
        public String getUnlikeToken() {
            return this.unlikeToken;
        }

        @Override // com.facebook.share.internal.LikeActionController.LikeRequestWrapper
        public boolean isObjectLiked() {
            return this.objectIsLiked;
        }

        @Override // com.facebook.share.internal.LikeActionController.AbstractRequestWrapper
        protected void processError(FacebookRequestError facebookRequestError) {
            Logger.log(LoggingBehavior.REQUESTS, LikeActionController.TAG, "Error fetching like status for object '%s' with type '%s' : %s", this.objectId, this.objectType, facebookRequestError);
            LikeActionController.this.logAppEventForError("get_og_object_like", facebookRequestError);
        }

        @Override // com.facebook.share.internal.LikeActionController.AbstractRequestWrapper
        protected void processSuccess(GraphResponse graphResponse) {
            JSONArray jSONArrayTryGetJSONArrayFromResponse = Utility.tryGetJSONArrayFromResponse(graphResponse.getJSONObject(), "data");
            if (jSONArrayTryGetJSONArrayFromResponse != null) {
                for (int i2 = 0; i2 < jSONArrayTryGetJSONArrayFromResponse.length(); i2++) {
                    JSONObject jSONObjectOptJSONObject = jSONArrayTryGetJSONArrayFromResponse.optJSONObject(i2);
                    if (jSONObjectOptJSONObject != null) {
                        this.objectIsLiked = true;
                        JSONObject jSONObjectOptJSONObject2 = jSONObjectOptJSONObject.optJSONObject(MimeTypes.BASE_TYPE_APPLICATION);
                        AccessToken currentAccessToken = AccessToken.getCurrentAccessToken();
                        if (jSONObjectOptJSONObject2 != null && AccessToken.isCurrentAccessTokenActive() && Utility.areObjectsEqual(currentAccessToken.getApplicationId(), jSONObjectOptJSONObject2.optString("id"))) {
                            this.unlikeToken = jSONObjectOptJSONObject.optString("id");
                        }
                    }
                }
            }
        }
    }

    private class GetPageIdRequestWrapper extends AbstractRequestWrapper {
        boolean objectIsPage;
        String verifiedObjectId;

        GetPageIdRequestWrapper(String str, LikeView.ObjectType objectType) {
            super(str, objectType);
            Bundle bundle = new Bundle();
            bundle.putString(GraphRequest.FIELDS_PARAM, "id");
            bundle.putString("ids", str);
            setRequest(new GraphRequest(AccessToken.getCurrentAccessToken(), "", bundle, HttpMethod.GET));
        }

        @Override // com.facebook.share.internal.LikeActionController.AbstractRequestWrapper
        protected void processError(FacebookRequestError facebookRequestError) {
            Logger.log(LoggingBehavior.REQUESTS, LikeActionController.TAG, "Error getting the FB id for object '%s' with type '%s' : %s", this.objectId, this.objectType, facebookRequestError);
        }

        @Override // com.facebook.share.internal.LikeActionController.AbstractRequestWrapper
        protected void processSuccess(GraphResponse graphResponse) {
            JSONObject jSONObjectTryGetJSONObjectFromResponse = Utility.tryGetJSONObjectFromResponse(graphResponse.getJSONObject(), this.objectId);
            if (jSONObjectTryGetJSONObjectFromResponse != null) {
                this.verifiedObjectId = jSONObjectTryGetJSONObjectFromResponse.optString("id");
                this.objectIsPage = !Utility.isNullOrEmpty(r2);
            }
        }
    }

    private class GetPageLikesRequestWrapper extends AbstractRequestWrapper implements LikeRequestWrapper {
        private boolean objectIsLiked;
        private String pageId;

        GetPageLikesRequestWrapper(String str) {
            super(str, LikeView.ObjectType.PAGE);
            this.objectIsLiked = LikeActionController.this.isObjectLiked;
            this.pageId = str;
            Bundle bundle = new Bundle();
            bundle.putString(GraphRequest.FIELDS_PARAM, "id");
            setRequest(new GraphRequest(AccessToken.getCurrentAccessToken(), "me/likes/" + str, bundle, HttpMethod.GET));
        }

        @Override // com.facebook.share.internal.LikeActionController.LikeRequestWrapper
        public String getUnlikeToken() {
            return null;
        }

        @Override // com.facebook.share.internal.LikeActionController.LikeRequestWrapper
        public boolean isObjectLiked() {
            return this.objectIsLiked;
        }

        @Override // com.facebook.share.internal.LikeActionController.AbstractRequestWrapper
        protected void processError(FacebookRequestError facebookRequestError) {
            Logger.log(LoggingBehavior.REQUESTS, LikeActionController.TAG, "Error fetching like status for page id '%s': %s", this.pageId, facebookRequestError);
            LikeActionController.this.logAppEventForError("get_page_like", facebookRequestError);
        }

        @Override // com.facebook.share.internal.LikeActionController.AbstractRequestWrapper
        protected void processSuccess(GraphResponse graphResponse) {
            JSONArray jSONArrayTryGetJSONArrayFromResponse = Utility.tryGetJSONArrayFromResponse(graphResponse.getJSONObject(), "data");
            if (jSONArrayTryGetJSONArrayFromResponse == null || jSONArrayTryGetJSONArrayFromResponse.length() <= 0) {
                return;
            }
            this.objectIsLiked = true;
        }
    }

    private interface LikeRequestWrapper extends RequestWrapper {
        String getUnlikeToken();

        boolean isObjectLiked();
    }

    private static class MRUCacheWorkItem implements Runnable {
        private static ArrayList<String> mruCachedItems = new ArrayList<>();
        private String cacheItem;
        private boolean shouldTrim;

        MRUCacheWorkItem(String str, boolean z2) {
            this.cacheItem = str;
            this.shouldTrim = z2;
        }

        @Override // java.lang.Runnable
        public void run() {
            String str = this.cacheItem;
            if (str != null) {
                mruCachedItems.remove(str);
                mruCachedItems.add(0, this.cacheItem);
            }
            if (!this.shouldTrim || mruCachedItems.size() < 128) {
                return;
            }
            while (64 < mruCachedItems.size()) {
                LikeActionController.cache.remove(mruCachedItems.remove(r0.size() - 1));
            }
        }
    }

    private class PublishLikeRequestWrapper extends AbstractRequestWrapper {
        String unlikeToken;

        PublishLikeRequestWrapper(String str, LikeView.ObjectType objectType) {
            super(str, objectType);
            Bundle bundle = new Bundle();
            bundle.putString("object", str);
            setRequest(new GraphRequest(AccessToken.getCurrentAccessToken(), "me/og.likes", bundle, HttpMethod.POST));
        }

        @Override // com.facebook.share.internal.LikeActionController.AbstractRequestWrapper
        protected void processError(FacebookRequestError facebookRequestError) {
            if (facebookRequestError.getErrorCode() == LikeActionController.ERROR_CODE_OBJECT_ALREADY_LIKED) {
                this.error = null;
            } else {
                Logger.log(LoggingBehavior.REQUESTS, LikeActionController.TAG, "Error liking object '%s' with type '%s' : %s", this.objectId, this.objectType, facebookRequestError);
                LikeActionController.this.logAppEventForError("publish_like", facebookRequestError);
            }
        }

        @Override // com.facebook.share.internal.LikeActionController.AbstractRequestWrapper
        protected void processSuccess(GraphResponse graphResponse) {
            this.unlikeToken = Utility.safeGetStringFromResponse(graphResponse.getJSONObject(), "id");
        }
    }

    private class PublishUnlikeRequestWrapper extends AbstractRequestWrapper {
        private String unlikeToken;

        PublishUnlikeRequestWrapper(String str) {
            super(null, null);
            this.unlikeToken = str;
            setRequest(new GraphRequest(AccessToken.getCurrentAccessToken(), str, null, HttpMethod.DELETE));
        }

        @Override // com.facebook.share.internal.LikeActionController.AbstractRequestWrapper
        protected void processError(FacebookRequestError facebookRequestError) {
            Logger.log(LoggingBehavior.REQUESTS, LikeActionController.TAG, "Error unliking object with unlike token '%s' : %s", this.unlikeToken, facebookRequestError);
            LikeActionController.this.logAppEventForError("publish_unlike", facebookRequestError);
        }

        @Override // com.facebook.share.internal.LikeActionController.AbstractRequestWrapper
        protected void processSuccess(GraphResponse graphResponse) {
        }
    }

    private interface RequestCompletionCallback {
        void onComplete();
    }

    private interface RequestWrapper {
        void addToBatch(GraphRequestBatch graphRequestBatch);

        FacebookRequestError getError();
    }

    private static class SerializeToDiskWorkItem implements Runnable {
        private String cacheKey;
        private String controllerJson;

        SerializeToDiskWorkItem(String str, String str2) {
            this.cacheKey = str;
            this.controllerJson = str2;
        }

        @Override // java.lang.Runnable
        public void run() throws IOException {
            LikeActionController.serializeToDiskSynchronously(this.cacheKey, this.controllerJson);
        }
    }

    private LikeActionController(String str, LikeView.ObjectType objectType) {
        this.objectId = str;
        this.objectType = objectType;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void broadcastAction(LikeActionController likeActionController, String str) {
        broadcastAction(likeActionController, str, null);
    }

    private boolean canUseOGPublish() {
        AccessToken currentAccessToken = AccessToken.getCurrentAccessToken();
        return (this.objectIsPage || this.verifiedObjectId == null || !AccessToken.isCurrentAccessTokenActive() || currentAccessToken.getPermissions() == null || !currentAccessToken.getPermissions().contains("publish_actions")) ? false : true;
    }

    private void clearState() {
        this.facebookDialogAnalyticsBundle = null;
        storeObjectIdForPendingController(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void createControllerForObjectIdAndType(String str, LikeView.ObjectType objectType, CreationCallback creationCallback) throws Throwable {
        LikeActionController controllerFromInMemoryCache = getControllerFromInMemoryCache(str);
        if (controllerFromInMemoryCache != null) {
            verifyControllerAndInvokeCallback(controllerFromInMemoryCache, objectType, creationCallback);
            return;
        }
        LikeActionController likeActionControllerDeserializeFromDiskSynchronously = deserializeFromDiskSynchronously(str);
        if (likeActionControllerDeserializeFromDiskSynchronously == null) {
            likeActionControllerDeserializeFromDiskSynchronously = new LikeActionController(str, objectType);
            serializeToDiskAsync(likeActionControllerDeserializeFromDiskSynchronously);
        }
        putControllerInMemoryCache(str, likeActionControllerDeserializeFromDiskSynchronously);
        handler.post(new Runnable() { // from class: com.facebook.share.internal.LikeActionController.2
            @Override // java.lang.Runnable
            public void run() {
                LikeActionController.this.refreshStatusAsync();
            }
        });
        invokeCallbackWithController(creationCallback, likeActionControllerDeserializeFromDiskSynchronously, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0022 A[PHI: r0 r5
      0x0022: PHI (r0v6 com.facebook.share.internal.LikeActionController) = (r0v0 com.facebook.share.internal.LikeActionController), (r0v9 com.facebook.share.internal.LikeActionController) binds: [B:20:0x0034, B:13:0x0020] A[DONT_GENERATE, DONT_INLINE]
      0x0022: PHI (r5v6 java.io.InputStream) = (r5v5 java.io.InputStream), (r5v8 java.io.InputStream) binds: [B:20:0x0034, B:13:0x0020] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x003a  */
    /* JADX WARN: Type inference failed for: r5v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r5v2 */
    /* JADX WARN: Type inference failed for: r5v4, types: [java.io.Closeable] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static com.facebook.share.internal.LikeActionController deserializeFromDiskSynchronously(java.lang.String r5) throws java.lang.Throwable {
        /*
            r0 = 0
            java.lang.String r5 = getCacheKeyForObjectId(r5)     // Catch: java.lang.Throwable -> L26 java.io.IOException -> L2b
            com.facebook.internal.FileLruCache r1 = com.facebook.share.internal.LikeActionController.controllerDiskCache     // Catch: java.lang.Throwable -> L26 java.io.IOException -> L2b
            java.io.InputStream r5 = r1.get(r5)     // Catch: java.lang.Throwable -> L26 java.io.IOException -> L2b
            if (r5 == 0) goto L20
            java.lang.String r1 = com.facebook.internal.Utility.readStreamToString(r5)     // Catch: java.lang.Throwable -> L1c java.io.IOException -> L1e
            boolean r2 = com.facebook.internal.Utility.isNullOrEmpty(r1)     // Catch: java.lang.Throwable -> L1c java.io.IOException -> L1e
            if (r2 != 0) goto L20
            com.facebook.share.internal.LikeActionController r0 = deserializeFromJson(r1)     // Catch: java.lang.Throwable -> L1c java.io.IOException -> L1e
            goto L20
        L1c:
            r0 = move-exception
            goto L38
        L1e:
            r1 = move-exception
            goto L2d
        L20:
            if (r5 == 0) goto L37
        L22:
            com.facebook.internal.Utility.closeQuietly(r5)
            goto L37
        L26:
            r5 = move-exception
            r4 = r0
            r0 = r5
            r5 = r4
            goto L38
        L2b:
            r1 = move-exception
            r5 = r0
        L2d:
            java.lang.String r2 = com.facebook.share.internal.LikeActionController.TAG     // Catch: java.lang.Throwable -> L1c
            java.lang.String r3 = "Unable to deserialize controller from disk"
            android.util.Log.e(r2, r3, r1)     // Catch: java.lang.Throwable -> L1c
            if (r5 == 0) goto L37
            goto L22
        L37:
            return r0
        L38:
            if (r5 == 0) goto L3d
            com.facebook.internal.Utility.closeQuietly(r5)
        L3d:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.share.internal.LikeActionController.deserializeFromDiskSynchronously(java.lang.String):com.facebook.share.internal.LikeActionController");
    }

    private static LikeActionController deserializeFromJson(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.optInt(JSON_INT_VERSION_KEY, -1) != 3) {
                return null;
            }
            LikeActionController likeActionController = new LikeActionController(jSONObject.getString("object_id"), LikeView.ObjectType.fromInt(jSONObject.optInt("object_type", LikeView.ObjectType.UNKNOWN.getValue())));
            likeActionController.likeCountStringWithLike = jSONObject.optString(JSON_STRING_LIKE_COUNT_WITH_LIKE_KEY, null);
            likeActionController.likeCountStringWithoutLike = jSONObject.optString(JSON_STRING_LIKE_COUNT_WITHOUT_LIKE_KEY, null);
            likeActionController.socialSentenceWithLike = jSONObject.optString(JSON_STRING_SOCIAL_SENTENCE_WITH_LIKE_KEY, null);
            likeActionController.socialSentenceWithoutLike = jSONObject.optString(JSON_STRING_SOCIAL_SENTENCE_WITHOUT_LIKE_KEY, null);
            likeActionController.isObjectLiked = jSONObject.optBoolean(JSON_BOOL_IS_OBJECT_LIKED_KEY);
            likeActionController.unlikeToken = jSONObject.optString("unlike_token", null);
            JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject(JSON_BUNDLE_FACEBOOK_DIALOG_ANALYTICS_BUNDLE);
            if (jSONObjectOptJSONObject != null) {
                likeActionController.facebookDialogAnalyticsBundle = BundleJSONConverter.convertToBundle(jSONObjectOptJSONObject);
            }
            return likeActionController;
        } catch (JSONException e2) {
            Log.e(TAG, "Unable to deserialize controller from JSON", e2);
            return null;
        }
    }

    private void fetchVerifiedObjectId(final RequestCompletionCallback requestCompletionCallback) {
        if (!Utility.isNullOrEmpty(this.verifiedObjectId)) {
            if (requestCompletionCallback != null) {
                requestCompletionCallback.onComplete();
                return;
            }
            return;
        }
        final GetOGObjectIdRequestWrapper getOGObjectIdRequestWrapper = new GetOGObjectIdRequestWrapper(this.objectId, this.objectType);
        final GetPageIdRequestWrapper getPageIdRequestWrapper = new GetPageIdRequestWrapper(this.objectId, this.objectType);
        GraphRequestBatch graphRequestBatch = new GraphRequestBatch();
        getOGObjectIdRequestWrapper.addToBatch(graphRequestBatch);
        getPageIdRequestWrapper.addToBatch(graphRequestBatch);
        graphRequestBatch.addCallback(new GraphRequestBatch.Callback() { // from class: com.facebook.share.internal.LikeActionController.11
            @Override // com.facebook.GraphRequestBatch.Callback
            public void onBatchCompleted(GraphRequestBatch graphRequestBatch2) {
                LikeActionController.this.verifiedObjectId = getOGObjectIdRequestWrapper.verifiedObjectId;
                if (Utility.isNullOrEmpty(LikeActionController.this.verifiedObjectId)) {
                    LikeActionController.this.verifiedObjectId = getPageIdRequestWrapper.verifiedObjectId;
                    LikeActionController.this.objectIsPage = getPageIdRequestWrapper.objectIsPage;
                }
                if (Utility.isNullOrEmpty(LikeActionController.this.verifiedObjectId)) {
                    Logger.log(LoggingBehavior.DEVELOPER_ERRORS, LikeActionController.TAG, "Unable to verify the FB id for '%s'. Verify that it is a valid FB object or page", LikeActionController.this.objectId);
                    LikeActionController.this.logAppEventForError("get_verified_id", getPageIdRequestWrapper.getError() != null ? getPageIdRequestWrapper.getError() : getOGObjectIdRequestWrapper.getError());
                }
                RequestCompletionCallback requestCompletionCallback2 = requestCompletionCallback;
                if (requestCompletionCallback2 != null) {
                    requestCompletionCallback2.onComplete();
                }
            }
        });
        graphRequestBatch.executeAsync();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public InternalAppEventsLogger getAppEventsLogger() {
        if (this.logger == null) {
            this.logger = new InternalAppEventsLogger(FacebookSdk.getApplicationContext());
        }
        return this.logger;
    }

    private static String getCacheKeyForObjectId(String str) {
        String token = AccessToken.isCurrentAccessTokenActive() ? AccessToken.getCurrentAccessToken().getToken() : null;
        if (token != null) {
            token = Utility.md5hash(token);
        }
        return String.format(Locale.ROOT, "%s|%s|com.fb.sdk.like|%d", str, Utility.coerceValueIfNullOrEmpty(token, ""), Integer.valueOf(objectSuffix));
    }

    @Deprecated
    public static void getControllerForObjectId(String str, LikeView.ObjectType objectType, CreationCallback creationCallback) {
        if (!isInitialized) {
            performFirstInitialize();
        }
        LikeActionController controllerFromInMemoryCache = getControllerFromInMemoryCache(str);
        if (controllerFromInMemoryCache != null) {
            verifyControllerAndInvokeCallback(controllerFromInMemoryCache, objectType, creationCallback);
        } else {
            diskIOWorkQueue.addActiveWorkItem(new CreateLikeActionControllerWorkItem(str, objectType, creationCallback));
        }
    }

    private static LikeActionController getControllerFromInMemoryCache(String str) {
        String cacheKeyForObjectId = getCacheKeyForObjectId(str);
        LikeActionController likeActionController = cache.get(cacheKeyForObjectId);
        if (likeActionController != null) {
            mruCacheWorkQueue.addActiveWorkItem(new MRUCacheWorkItem(cacheKeyForObjectId, false));
        }
        return likeActionController;
    }

    private ResultProcessor getResultProcessor(final Bundle bundle) {
        return new ResultProcessor(null) { // from class: com.facebook.share.internal.LikeActionController.6
            @Override // com.facebook.share.internal.ResultProcessor
            public void onCancel(AppCall appCall) {
                onError(appCall, new FacebookOperationCanceledException());
            }

            @Override // com.facebook.share.internal.ResultProcessor
            public void onError(AppCall appCall, FacebookException facebookException) {
                Logger.log(LoggingBehavior.REQUESTS, LikeActionController.TAG, "Like Dialog failed with error : %s", facebookException);
                Bundle bundle2 = bundle;
                if (bundle2 == null) {
                    bundle2 = new Bundle();
                }
                bundle2.putString(AnalyticsEvents.PARAMETER_CALL_ID, appCall.getCallId().toString());
                LikeActionController.this.logAppEventForError("present_dialog", bundle2);
                LikeActionController.broadcastAction(LikeActionController.this, LikeActionController.ACTION_LIKE_ACTION_CONTROLLER_DID_ERROR, NativeProtocol.createBundleForException(facebookException));
            }

            @Override // com.facebook.share.internal.ResultProcessor
            public void onSuccess(AppCall appCall, Bundle bundle2) throws JSONException {
                String string;
                String str;
                String string2;
                String str2;
                if (bundle2 == null || !bundle2.containsKey(LikeActionController.LIKE_DIALOG_RESPONSE_OBJECT_IS_LIKED_KEY)) {
                    return;
                }
                boolean z2 = bundle2.getBoolean(LikeActionController.LIKE_DIALOG_RESPONSE_OBJECT_IS_LIKED_KEY);
                String str3 = LikeActionController.this.likeCountStringWithLike;
                String str4 = LikeActionController.this.likeCountStringWithoutLike;
                if (bundle2.containsKey(LikeActionController.LIKE_DIALOG_RESPONSE_LIKE_COUNT_STRING_KEY)) {
                    string = bundle2.getString(LikeActionController.LIKE_DIALOG_RESPONSE_LIKE_COUNT_STRING_KEY);
                    str = string;
                } else {
                    string = str3;
                    str = str4;
                }
                String str5 = LikeActionController.this.socialSentenceWithLike;
                String str6 = LikeActionController.this.socialSentenceWithoutLike;
                if (bundle2.containsKey(LikeActionController.LIKE_DIALOG_RESPONSE_SOCIAL_SENTENCE_KEY)) {
                    string2 = bundle2.getString(LikeActionController.LIKE_DIALOG_RESPONSE_SOCIAL_SENTENCE_KEY);
                    str2 = string2;
                } else {
                    string2 = str5;
                    str2 = str6;
                }
                String string3 = bundle2.containsKey(LikeActionController.LIKE_DIALOG_RESPONSE_OBJECT_IS_LIKED_KEY) ? bundle2.getString("unlike_token") : LikeActionController.this.unlikeToken;
                Bundle bundle3 = bundle;
                if (bundle3 == null) {
                    bundle3 = new Bundle();
                }
                bundle3.putString(AnalyticsEvents.PARAMETER_CALL_ID, appCall.getCallId().toString());
                LikeActionController.this.getAppEventsLogger().logEventImplicitly(AnalyticsEvents.EVENT_LIKE_VIEW_DIALOG_DID_SUCCEED, bundle3);
                LikeActionController.this.updateState(z2, string, str, string2, str2, string3);
            }
        };
    }

    @Deprecated
    public static boolean handleOnActivityResult(final int i2, final int i3, final Intent intent) {
        if (Utility.isNullOrEmpty(objectIdForPendingController)) {
            objectIdForPendingController = FacebookSdk.getApplicationContext().getSharedPreferences(LIKE_ACTION_CONTROLLER_STORE, 0).getString(LIKE_ACTION_CONTROLLER_STORE_PENDING_OBJECT_ID_KEY, null);
        }
        if (Utility.isNullOrEmpty(objectIdForPendingController)) {
            return false;
        }
        getControllerForObjectId(objectIdForPendingController, LikeView.ObjectType.UNKNOWN, new CreationCallback() { // from class: com.facebook.share.internal.LikeActionController.1
            @Override // com.facebook.share.internal.LikeActionController.CreationCallback
            public void onComplete(LikeActionController likeActionController, FacebookException facebookException) {
                if (facebookException == null) {
                    likeActionController.onActivityResult(i2, i3, intent);
                } else {
                    Utility.logd(LikeActionController.TAG, facebookException);
                }
            }
        });
        return true;
    }

    private static void invokeCallbackWithController(final CreationCallback creationCallback, final LikeActionController likeActionController, final FacebookException facebookException) {
        if (creationCallback == null) {
            return;
        }
        handler.post(new Runnable() { // from class: com.facebook.share.internal.LikeActionController.4
            @Override // java.lang.Runnable
            public void run() {
                creationCallback.onComplete(likeActionController, facebookException);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void logAppEventForError(String str, Bundle bundle) {
        Bundle bundle2 = new Bundle(bundle);
        bundle2.putString("object_id", this.objectId);
        bundle2.putString("object_type", this.objectType.toString());
        bundle2.putString(AnalyticsEvents.PARAMETER_LIKE_VIEW_CURRENT_ACTION, str);
        getAppEventsLogger().logEventImplicitly(AnalyticsEvents.EVENT_LIKE_VIEW_ERROR, null, bundle2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onActivityResult(int i2, int i3, Intent intent) {
        ShareInternalUtility.handleActivityResult(i2, i3, intent, getResultProcessor(this.facebookDialogAnalyticsBundle));
        clearState();
    }

    private static synchronized void performFirstInitialize() {
        if (isInitialized) {
            return;
        }
        handler = new Handler(Looper.getMainLooper());
        objectSuffix = FacebookSdk.getApplicationContext().getSharedPreferences(LIKE_ACTION_CONTROLLER_STORE, 0).getInt(LIKE_ACTION_CONTROLLER_STORE_OBJECT_SUFFIX_KEY, 1);
        controllerDiskCache = new FileLruCache(TAG, new FileLruCache.Limits());
        registerAccessTokenTracker();
        CallbackManagerImpl.registerStaticCallback(CallbackManagerImpl.RequestCodeOffset.Like.toRequestCode(), new CallbackManagerImpl.Callback() { // from class: com.facebook.share.internal.LikeActionController.3
            @Override // com.facebook.internal.CallbackManagerImpl.Callback
            public boolean onActivityResult(int i2, Intent intent) {
                return LikeActionController.handleOnActivityResult(CallbackManagerImpl.RequestCodeOffset.Like.toRequestCode(), i2, intent);
            }
        });
        isInitialized = true;
    }

    private void presentLikeDialog(Activity activity, FragmentWrapper fragmentWrapper, Bundle bundle) throws JSONException {
        String str;
        if (LikeDialog.canShowNativeDialog()) {
            str = AnalyticsEvents.EVENT_LIKE_VIEW_DID_PRESENT_DIALOG;
        } else if (LikeDialog.canShowWebFallback()) {
            str = AnalyticsEvents.EVENT_LIKE_VIEW_DID_PRESENT_FALLBACK;
        } else {
            logAppEventForError("present_dialog", bundle);
            Utility.logd(TAG, "Cannot show the Like Dialog on this device.");
            broadcastAction(null, ACTION_LIKE_ACTION_CONTROLLER_UPDATED);
            str = null;
        }
        if (str != null) {
            LikeView.ObjectType objectType = this.objectType;
            LikeContent likeContentBuild = new LikeContent.Builder().setObjectId(this.objectId).setObjectType(objectType != null ? objectType.toString() : LikeView.ObjectType.UNKNOWN.toString()).build();
            if (fragmentWrapper != null) {
                new LikeDialog(fragmentWrapper).show(likeContentBuild);
            } else {
                new LikeDialog(activity).show(likeContentBuild);
            }
            saveState(bundle);
            getAppEventsLogger().logEventImplicitly(AnalyticsEvents.EVENT_LIKE_VIEW_DID_PRESENT_DIALOG, bundle);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void publishAgainIfNeeded(Bundle bundle) throws JSONException {
        boolean z2 = this.isObjectLiked;
        if (z2 == this.isObjectLikedOnServer || publishLikeOrUnlikeAsync(z2, bundle)) {
            return;
        }
        publishDidError(!this.isObjectLiked);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void publishDidError(boolean z2) throws JSONException {
        updateLikeState(z2);
        Bundle bundle = new Bundle();
        bundle.putString(NativeProtocol.STATUS_ERROR_DESCRIPTION, ERROR_PUBLISH_ERROR);
        broadcastAction(this, ACTION_LIKE_ACTION_CONTROLLER_DID_ERROR, bundle);
    }

    private void publishLikeAsync(final Bundle bundle) {
        this.isPendingLikeOrUnlike = true;
        fetchVerifiedObjectId(new RequestCompletionCallback() { // from class: com.facebook.share.internal.LikeActionController.7
            @Override // com.facebook.share.internal.LikeActionController.RequestCompletionCallback
            public void onComplete() {
                if (Utility.isNullOrEmpty(LikeActionController.this.verifiedObjectId)) {
                    Bundle bundle2 = new Bundle();
                    bundle2.putString(NativeProtocol.STATUS_ERROR_DESCRIPTION, LikeActionController.ERROR_INVALID_OBJECT_ID);
                    LikeActionController.broadcastAction(LikeActionController.this, LikeActionController.ACTION_LIKE_ACTION_CONTROLLER_DID_ERROR, bundle2);
                } else {
                    GraphRequestBatch graphRequestBatch = new GraphRequestBatch();
                    LikeActionController likeActionController = LikeActionController.this;
                    final PublishLikeRequestWrapper publishLikeRequestWrapper = likeActionController.new PublishLikeRequestWrapper(likeActionController.verifiedObjectId, LikeActionController.this.objectType);
                    publishLikeRequestWrapper.addToBatch(graphRequestBatch);
                    graphRequestBatch.addCallback(new GraphRequestBatch.Callback() { // from class: com.facebook.share.internal.LikeActionController.7.1
                        @Override // com.facebook.GraphRequestBatch.Callback
                        public void onBatchCompleted(GraphRequestBatch graphRequestBatch2) throws JSONException {
                            LikeActionController.this.isPendingLikeOrUnlike = false;
                            if (publishLikeRequestWrapper.getError() != null) {
                                LikeActionController.this.publishDidError(false);
                                return;
                            }
                            LikeActionController.this.unlikeToken = Utility.coerceValueIfNullOrEmpty(publishLikeRequestWrapper.unlikeToken, null);
                            LikeActionController.this.isObjectLikedOnServer = true;
                            LikeActionController.this.getAppEventsLogger().logEventImplicitly(AnalyticsEvents.EVENT_LIKE_VIEW_DID_LIKE, null, bundle);
                            AnonymousClass7 anonymousClass7 = AnonymousClass7.this;
                            LikeActionController.this.publishAgainIfNeeded(bundle);
                        }
                    });
                    graphRequestBatch.executeAsync();
                }
            }
        });
    }

    private boolean publishLikeOrUnlikeAsync(boolean z2, Bundle bundle) {
        if (canUseOGPublish()) {
            if (z2) {
                publishLikeAsync(bundle);
                return true;
            }
            if (!Utility.isNullOrEmpty(this.unlikeToken)) {
                publishUnlikeAsync(bundle);
                return true;
            }
        }
        return false;
    }

    private void publishUnlikeAsync(final Bundle bundle) {
        this.isPendingLikeOrUnlike = true;
        GraphRequestBatch graphRequestBatch = new GraphRequestBatch();
        final PublishUnlikeRequestWrapper publishUnlikeRequestWrapper = new PublishUnlikeRequestWrapper(this.unlikeToken);
        publishUnlikeRequestWrapper.addToBatch(graphRequestBatch);
        graphRequestBatch.addCallback(new GraphRequestBatch.Callback() { // from class: com.facebook.share.internal.LikeActionController.8
            @Override // com.facebook.GraphRequestBatch.Callback
            public void onBatchCompleted(GraphRequestBatch graphRequestBatch2) throws JSONException {
                LikeActionController.this.isPendingLikeOrUnlike = false;
                if (publishUnlikeRequestWrapper.getError() != null) {
                    LikeActionController.this.publishDidError(true);
                    return;
                }
                LikeActionController.this.unlikeToken = null;
                LikeActionController.this.isObjectLikedOnServer = false;
                LikeActionController.this.getAppEventsLogger().logEventImplicitly(AnalyticsEvents.EVENT_LIKE_VIEW_DID_UNLIKE, null, bundle);
                LikeActionController.this.publishAgainIfNeeded(bundle);
            }
        });
        graphRequestBatch.executeAsync();
    }

    private static void putControllerInMemoryCache(String str, LikeActionController likeActionController) {
        String cacheKeyForObjectId = getCacheKeyForObjectId(str);
        mruCacheWorkQueue.addActiveWorkItem(new MRUCacheWorkItem(cacheKeyForObjectId, true));
        cache.put(cacheKeyForObjectId, likeActionController);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshStatusAsync() {
        if (AccessToken.isCurrentAccessTokenActive()) {
            fetchVerifiedObjectId(new RequestCompletionCallback() { // from class: com.facebook.share.internal.LikeActionController.9
                @Override // com.facebook.share.internal.LikeActionController.RequestCompletionCallback
                public void onComplete() {
                    final LikeRequestWrapper getPageLikesRequestWrapper;
                    if (AnonymousClass12.$SwitchMap$com$facebook$share$widget$LikeView$ObjectType[LikeActionController.this.objectType.ordinal()] != 1) {
                        LikeActionController likeActionController = LikeActionController.this;
                        getPageLikesRequestWrapper = likeActionController.new GetOGObjectLikesRequestWrapper(likeActionController.verifiedObjectId, LikeActionController.this.objectType);
                    } else {
                        LikeActionController likeActionController2 = LikeActionController.this;
                        getPageLikesRequestWrapper = likeActionController2.new GetPageLikesRequestWrapper(likeActionController2.verifiedObjectId);
                    }
                    LikeActionController likeActionController3 = LikeActionController.this;
                    final GetEngagementRequestWrapper getEngagementRequestWrapper = likeActionController3.new GetEngagementRequestWrapper(likeActionController3.verifiedObjectId, LikeActionController.this.objectType);
                    GraphRequestBatch graphRequestBatch = new GraphRequestBatch();
                    getPageLikesRequestWrapper.addToBatch(graphRequestBatch);
                    getEngagementRequestWrapper.addToBatch(graphRequestBatch);
                    graphRequestBatch.addCallback(new GraphRequestBatch.Callback() { // from class: com.facebook.share.internal.LikeActionController.9.1
                        @Override // com.facebook.GraphRequestBatch.Callback
                        public void onBatchCompleted(GraphRequestBatch graphRequestBatch2) throws JSONException {
                            if (getPageLikesRequestWrapper.getError() != null || getEngagementRequestWrapper.getError() != null) {
                                Logger.log(LoggingBehavior.REQUESTS, LikeActionController.TAG, "Unable to refresh like state for id: '%s'", LikeActionController.this.objectId);
                                return;
                            }
                            LikeActionController likeActionController4 = LikeActionController.this;
                            boolean zIsObjectLiked = getPageLikesRequestWrapper.isObjectLiked();
                            GetEngagementRequestWrapper getEngagementRequestWrapper2 = getEngagementRequestWrapper;
                            likeActionController4.updateState(zIsObjectLiked, getEngagementRequestWrapper2.likeCountStringWithLike, getEngagementRequestWrapper2.likeCountStringWithoutLike, getEngagementRequestWrapper2.socialSentenceStringWithLike, getEngagementRequestWrapper2.socialSentenceStringWithoutLike, getPageLikesRequestWrapper.getUnlikeToken());
                        }
                    });
                    graphRequestBatch.executeAsync();
                }
            });
        } else {
            refreshStatusViaService();
        }
    }

    private void refreshStatusViaService() {
        LikeStatusClient likeStatusClient = new LikeStatusClient(FacebookSdk.getApplicationContext(), FacebookSdk.getApplicationId(), this.objectId);
        if (likeStatusClient.start()) {
            likeStatusClient.setCompletedListener(new PlatformServiceClient.CompletedListener() { // from class: com.facebook.share.internal.LikeActionController.10
                @Override // com.facebook.internal.PlatformServiceClient.CompletedListener
                public void completed(Bundle bundle) throws JSONException {
                    if (bundle == null || !bundle.containsKey(ShareConstants.EXTRA_OBJECT_IS_LIKED)) {
                        return;
                    }
                    LikeActionController.this.updateState(bundle.getBoolean(ShareConstants.EXTRA_OBJECT_IS_LIKED), bundle.containsKey(ShareConstants.EXTRA_LIKE_COUNT_STRING_WITH_LIKE) ? bundle.getString(ShareConstants.EXTRA_LIKE_COUNT_STRING_WITH_LIKE) : LikeActionController.this.likeCountStringWithLike, bundle.containsKey(ShareConstants.EXTRA_LIKE_COUNT_STRING_WITHOUT_LIKE) ? bundle.getString(ShareConstants.EXTRA_LIKE_COUNT_STRING_WITHOUT_LIKE) : LikeActionController.this.likeCountStringWithoutLike, bundle.containsKey(ShareConstants.EXTRA_SOCIAL_SENTENCE_WITH_LIKE) ? bundle.getString(ShareConstants.EXTRA_SOCIAL_SENTENCE_WITH_LIKE) : LikeActionController.this.socialSentenceWithLike, bundle.containsKey(ShareConstants.EXTRA_SOCIAL_SENTENCE_WITHOUT_LIKE) ? bundle.getString(ShareConstants.EXTRA_SOCIAL_SENTENCE_WITHOUT_LIKE) : LikeActionController.this.socialSentenceWithoutLike, bundle.containsKey(ShareConstants.EXTRA_UNLIKE_TOKEN) ? bundle.getString(ShareConstants.EXTRA_UNLIKE_TOKEN) : LikeActionController.this.unlikeToken);
                }
            });
        }
    }

    private static void registerAccessTokenTracker() {
        accessTokenTracker = new AccessTokenTracker() { // from class: com.facebook.share.internal.LikeActionController.5
            @Override // com.facebook.AccessTokenTracker
            protected void onCurrentAccessTokenChanged(AccessToken accessToken, AccessToken accessToken2) {
                Context applicationContext = FacebookSdk.getApplicationContext();
                if (accessToken2 == null) {
                    int unused = LikeActionController.objectSuffix = (LikeActionController.objectSuffix + 1) % 1000;
                    applicationContext.getSharedPreferences(LikeActionController.LIKE_ACTION_CONTROLLER_STORE, 0).edit().putInt(LikeActionController.LIKE_ACTION_CONTROLLER_STORE_OBJECT_SUFFIX_KEY, LikeActionController.objectSuffix).apply();
                    LikeActionController.cache.clear();
                    LikeActionController.controllerDiskCache.clearCache();
                }
                LikeActionController.broadcastAction(null, LikeActionController.ACTION_LIKE_ACTION_CONTROLLER_DID_RESET);
            }
        };
    }

    private void saveState(Bundle bundle) throws JSONException {
        storeObjectIdForPendingController(this.objectId);
        this.facebookDialogAnalyticsBundle = bundle;
        serializeToDiskAsync(this);
    }

    private static void serializeToDiskAsync(LikeActionController likeActionController) throws JSONException {
        String strSerializeToJson = serializeToJson(likeActionController);
        String cacheKeyForObjectId = getCacheKeyForObjectId(likeActionController.objectId);
        if (Utility.isNullOrEmpty(strSerializeToJson) || Utility.isNullOrEmpty(cacheKeyForObjectId)) {
            return;
        }
        diskIOWorkQueue.addActiveWorkItem(new SerializeToDiskWorkItem(cacheKeyForObjectId, strSerializeToJson));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void serializeToDiskSynchronously(String str, String str2) throws IOException {
        OutputStream outputStreamOpenPutStream = null;
        try {
            try {
                outputStreamOpenPutStream = controllerDiskCache.openPutStream(str);
                outputStreamOpenPutStream.write(str2.getBytes());
            } catch (IOException e2) {
                Log.e(TAG, "Unable to serialize controller to disk", e2);
                if (outputStreamOpenPutStream == null) {
                    return;
                }
            }
            Utility.closeQuietly(outputStreamOpenPutStream);
        } catch (Throwable th) {
            if (outputStreamOpenPutStream != null) {
                Utility.closeQuietly(outputStreamOpenPutStream);
            }
            throw th;
        }
    }

    private static String serializeToJson(LikeActionController likeActionController) throws JSONException {
        JSONObject jSONObjectConvertToJSON;
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(JSON_INT_VERSION_KEY, 3);
            jSONObject.put("object_id", likeActionController.objectId);
            jSONObject.put("object_type", likeActionController.objectType.getValue());
            jSONObject.put(JSON_STRING_LIKE_COUNT_WITH_LIKE_KEY, likeActionController.likeCountStringWithLike);
            jSONObject.put(JSON_STRING_LIKE_COUNT_WITHOUT_LIKE_KEY, likeActionController.likeCountStringWithoutLike);
            jSONObject.put(JSON_STRING_SOCIAL_SENTENCE_WITH_LIKE_KEY, likeActionController.socialSentenceWithLike);
            jSONObject.put(JSON_STRING_SOCIAL_SENTENCE_WITHOUT_LIKE_KEY, likeActionController.socialSentenceWithoutLike);
            jSONObject.put(JSON_BOOL_IS_OBJECT_LIKED_KEY, likeActionController.isObjectLiked);
            jSONObject.put("unlike_token", likeActionController.unlikeToken);
            Bundle bundle = likeActionController.facebookDialogAnalyticsBundle;
            if (bundle != null && (jSONObjectConvertToJSON = BundleJSONConverter.convertToJSON(bundle)) != null) {
                jSONObject.put(JSON_BUNDLE_FACEBOOK_DIALOG_ANALYTICS_BUNDLE, jSONObjectConvertToJSON);
            }
            return jSONObject.toString();
        } catch (JSONException e2) {
            Log.e(TAG, "Unable to serialize controller to JSON", e2);
            return null;
        }
    }

    private static void storeObjectIdForPendingController(String str) {
        objectIdForPendingController = str;
        FacebookSdk.getApplicationContext().getSharedPreferences(LIKE_ACTION_CONTROLLER_STORE, 0).edit().putString(LIKE_ACTION_CONTROLLER_STORE_PENDING_OBJECT_ID_KEY, objectIdForPendingController).apply();
    }

    private void updateLikeState(boolean z2) throws JSONException {
        updateState(z2, this.likeCountStringWithLike, this.likeCountStringWithoutLike, this.socialSentenceWithLike, this.socialSentenceWithoutLike, this.unlikeToken);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateState(boolean z2, String str, String str2, String str3, String str4, String str5) throws JSONException {
        String strCoerceValueIfNullOrEmpty = Utility.coerceValueIfNullOrEmpty(str, null);
        String strCoerceValueIfNullOrEmpty2 = Utility.coerceValueIfNullOrEmpty(str2, null);
        String strCoerceValueIfNullOrEmpty3 = Utility.coerceValueIfNullOrEmpty(str3, null);
        String strCoerceValueIfNullOrEmpty4 = Utility.coerceValueIfNullOrEmpty(str4, null);
        String strCoerceValueIfNullOrEmpty5 = Utility.coerceValueIfNullOrEmpty(str5, null);
        if (z2 == this.isObjectLiked && Utility.areObjectsEqual(strCoerceValueIfNullOrEmpty, this.likeCountStringWithLike) && Utility.areObjectsEqual(strCoerceValueIfNullOrEmpty2, this.likeCountStringWithoutLike) && Utility.areObjectsEqual(strCoerceValueIfNullOrEmpty3, this.socialSentenceWithLike) && Utility.areObjectsEqual(strCoerceValueIfNullOrEmpty4, this.socialSentenceWithoutLike) && Utility.areObjectsEqual(strCoerceValueIfNullOrEmpty5, this.unlikeToken)) {
            return;
        }
        this.isObjectLiked = z2;
        this.likeCountStringWithLike = strCoerceValueIfNullOrEmpty;
        this.likeCountStringWithoutLike = strCoerceValueIfNullOrEmpty2;
        this.socialSentenceWithLike = strCoerceValueIfNullOrEmpty3;
        this.socialSentenceWithoutLike = strCoerceValueIfNullOrEmpty4;
        this.unlikeToken = strCoerceValueIfNullOrEmpty5;
        serializeToDiskAsync(this);
        broadcastAction(this, ACTION_LIKE_ACTION_CONTROLLER_UPDATED);
    }

    private static void verifyControllerAndInvokeCallback(LikeActionController likeActionController, LikeView.ObjectType objectType, CreationCallback creationCallback) {
        LikeView.ObjectType mostSpecificObjectType = ShareInternalUtility.getMostSpecificObjectType(objectType, likeActionController.objectType);
        FacebookException facebookException = null;
        if (mostSpecificObjectType == null) {
            Object[] objArr = {likeActionController.objectId, likeActionController.objectType.toString(), objectType.toString()};
            likeActionController = null;
            facebookException = new FacebookException("Object with id:\"%s\" is already marked as type:\"%s\". Cannot change the type to:\"%s\"", objArr);
        } else {
            likeActionController.objectType = mostSpecificObjectType;
        }
        invokeCallbackWithController(creationCallback, likeActionController, facebookException);
    }

    @Deprecated
    public String getLikeCountString() {
        return this.isObjectLiked ? this.likeCountStringWithLike : this.likeCountStringWithoutLike;
    }

    @Deprecated
    public String getObjectId() {
        return this.objectId;
    }

    @Deprecated
    public String getSocialSentence() {
        return this.isObjectLiked ? this.socialSentenceWithLike : this.socialSentenceWithoutLike;
    }

    @Deprecated
    public boolean isObjectLiked() {
        return this.isObjectLiked;
    }

    @Deprecated
    public boolean shouldEnableView() {
        return false;
    }

    @Deprecated
    public void toggleLike(Activity activity, FragmentWrapper fragmentWrapper, Bundle bundle) {
        boolean z2 = this.isObjectLiked;
        boolean z3 = !z2;
        if (!canUseOGPublish()) {
            presentLikeDialog(activity, fragmentWrapper, bundle);
            return;
        }
        updateLikeState(z3);
        if (this.isPendingLikeOrUnlike) {
            getAppEventsLogger().logEventImplicitly(AnalyticsEvents.EVENT_LIKE_VIEW_DID_UNDO_QUICKLY, bundle);
        } else {
            if (publishLikeOrUnlikeAsync(z3, bundle)) {
                return;
            }
            updateLikeState(z2);
            presentLikeDialog(activity, fragmentWrapper, bundle);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void broadcastAction(LikeActionController likeActionController, String str, Bundle bundle) {
        Intent intent = new Intent(str);
        if (likeActionController != null) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putString(ACTION_OBJECT_ID_KEY, likeActionController.getObjectId());
        }
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        LocalBroadcastManager.getInstance(FacebookSdk.getApplicationContext()).sendBroadcast(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void logAppEventForError(String str, FacebookRequestError facebookRequestError) {
        JSONObject requestResult;
        Bundle bundle = new Bundle();
        if (facebookRequestError != null && (requestResult = facebookRequestError.getRequestResult()) != null) {
            bundle.putString("error", requestResult.toString());
        }
        logAppEventForError(str, bundle);
    }
}
