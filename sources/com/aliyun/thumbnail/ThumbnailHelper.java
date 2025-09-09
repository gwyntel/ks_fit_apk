package com.aliyun.thumbnail;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.aliyun.utils.a;
import com.aliyun.utils.f;
import com.aliyun.utils.g;
import com.cicada.player.utils.Logger;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.ssl.HttpsURLConnection;

/* loaded from: classes3.dex */
public class ThumbnailHelper {
    private static final int CONNECTION_TIMEOUT = 5000;
    private static final int MSG_KEY_BITMAP_FAIL = 2;
    private static final int MSG_KEY_BITMAP_SUCCESS = 3;
    private static final int MSG_KEY_PREPARE_FAIL = 0;
    private static final int MSG_KEY_PREPARE_SUCCESS = 1;
    private static final String TAG = "ThumbnailHelper";
    private BitmapFactory.Options mBitmapOptions;
    private BitmapRegionDecoder mBitmapRegionDecoder;
    private Rect mRect;
    private ThumbnailInfo[] mThumbnailInfoArray;
    private String mUrl;
    private final Object lock = new Object();
    private String mLastPath = "";
    private Map<String, byte[]> mUrlDataMap = new HashMap();
    private volatile boolean hasPrepared = false;
    private OnPrepareListener mOnPrepareListener = null;
    private OnThumbnailGetListener mOnThumbnailGetListener = null;
    private ResultHandler mResultHandler = new ResultHandler(this);

    private class ByteHttp extends a {
        byte[] bytes;
        int len;

        private ByteHttp() {
            this.bytes = null;
            this.len = 0;
        }

        @Override // com.aliyun.utils.a
        protected void handleErrorInputStream(InputStream inputStream) {
        }

        @Override // com.aliyun.utils.a
        protected void handleOKInputStream(InputStream inputStream) throws IOException {
            AtomicBoolean atomicBoolean = new AtomicBoolean(false);
            byte[] stream = ThumbnailHelper.readStream(inputStream, atomicBoolean);
            this.bytes = stream;
            if (stream == null && atomicBoolean.get()) {
                return;
            }
            this.len = this.bytes.length;
        }
    }

    private interface OnImgDataResultListener {
        void onFail();

        void onSuccess(byte[] bArr);
    }

    public interface OnPrepareListener {
        void onPrepareFail();

        void onPrepareSuccess();
    }

    public interface OnThumbnailGetListener {
        void onThumbnailGetFail(long j2, String str);

        void onThumbnailGetSuccess(long j2, ThumbnailBitmapInfo thumbnailBitmapInfo);
    }

    private static class ResultHandler extends Handler {
        private WeakReference<ThumbnailHelper> thumbnailHelperWeakReference;

        ResultHandler(ThumbnailHelper thumbnailHelper) {
            super(Looper.getMainLooper());
            this.thumbnailHelperWeakReference = new WeakReference<>(thumbnailHelper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            ThumbnailHelper thumbnailHelper = this.thumbnailHelperWeakReference.get();
            if (thumbnailHelper != null) {
                thumbnailHelper.handleMessage(message);
            }
            super.handleMessage(message);
        }
    }

    static {
        f.f();
    }

    public ThumbnailHelper(String str) {
        this.mUrl = str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Bitmap getBitmap(ThumbnailInfo thumbnailInfo, byte[] bArr) {
        try {
            if (this.mBitmapRegionDecoder == null || !this.mLastPath.equals(thumbnailInfo.mPath)) {
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
                this.mLastPath = thumbnailInfo.mPath;
                this.mBitmapRegionDecoder = BitmapRegionDecoder.newInstance((InputStream) byteArrayInputStream, true);
            }
            if (this.mBitmapOptions == null) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                this.mBitmapOptions = options;
                options.inPreferredConfig = Bitmap.Config.RGB_565;
            }
            if (this.mRect == null) {
                this.mRect = new Rect();
            }
            Rect rect = this.mRect;
            int i2 = thumbnailInfo.mLeft;
            rect.left = i2;
            int i3 = thumbnailInfo.mTop;
            rect.top = i3;
            rect.right = i2 + thumbnailInfo.mWidth;
            rect.bottom = i3 + thumbnailInfo.mHeight;
            return this.mBitmapRegionDecoder.decodeRegion(rect, this.mBitmapOptions);
        } catch (IOException e2) {
            e2.printStackTrace();
            Logger.d(TAG, "获取缩略图异常。。" + e2.getMessage());
            return null;
        }
    }

    private URLConnection getHttpUrlConnection(String str) throws IOException {
        URLConnection uRLConnectionOpenConnection;
        URLConnection uRLConnection = null;
        try {
            uRLConnectionOpenConnection = new URL(str).openConnection();
        } catch (Exception unused) {
        }
        try {
            if (!(uRLConnectionOpenConnection instanceof HttpURLConnection)) {
                return null;
            }
            HttpURLConnection httpURLConnection = (HttpURLConnection) uRLConnectionOpenConnection;
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setReadTimeout(5000);
            return uRLConnectionOpenConnection;
        } catch (Exception unused2) {
            uRLConnection = uRLConnectionOpenConnection;
            return uRLConnection;
        }
    }

    private URLConnection getHttpsUrlConnection(String str) throws IOException {
        URLConnection uRLConnectionOpenConnection;
        URLConnection uRLConnection = null;
        try {
            uRLConnectionOpenConnection = new URL(str).openConnection();
        } catch (Exception unused) {
        }
        try {
            if (!(uRLConnectionOpenConnection instanceof HttpsURLConnection)) {
                return null;
            }
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) uRLConnectionOpenConnection;
            httpsURLConnection.setRequestMethod("GET");
            httpsURLConnection.setConnectTimeout(5000);
            httpsURLConnection.setReadTimeout(5000);
            return uRLConnectionOpenConnection;
        } catch (Exception unused2) {
            uRLConnection = uRLConnectionOpenConnection;
            return uRLConnection;
        }
    }

    private ThumbnailInfo getInfoByPosition(long j2) {
        String str = TAG;
        Logger.d(str, "getInfoByPosition position = " + j2);
        ThumbnailInfo[] thumbnailInfoArr = this.mThumbnailInfoArray;
        ThumbnailInfo thumbnailInfo = null;
        if (thumbnailInfoArr == null) {
            Logger.e(str, "mThumbnailInfoArray == null");
            return null;
        }
        int length = thumbnailInfoArr.length;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                break;
            }
            ThumbnailInfo thumbnailInfo2 = this.mThumbnailInfoArray[i2];
            if (thumbnailInfo2.mStart <= j2 && thumbnailInfo2.mUntil >= j2) {
                thumbnailInfo = thumbnailInfo2;
                break;
            }
            i2++;
        }
        Logger.d(TAG, "mThumbnailInfoArray targetInfo = " + thumbnailInfo);
        return thumbnailInfo;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getResponseCode(URLConnection uRLConnection) throws IOException {
        HttpURLConnection httpURLConnection;
        if (uRLConnection instanceof HttpsURLConnection) {
            httpURLConnection = (HttpsURLConnection) uRLConnection;
        } else {
            if (!(uRLConnection instanceof HttpURLConnection)) {
                return 0;
            }
            httpURLConnection = (HttpURLConnection) uRLConnection;
        }
        return httpURLConnection.getResponseCode();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public native Object[] getThumbnailInfos(String str, String str2);

    /* JADX INFO: Access modifiers changed from: private */
    public URLConnection getUrlConnection(String str) {
        if (str.startsWith("https://")) {
            return getHttpsUrlConnection(str);
        }
        if (str.startsWith("http://")) {
            return getHttpUrlConnection(str);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleMessage(Message message) {
        int i2 = message.what;
        if (i2 == 1) {
            OnPrepareListener onPrepareListener = this.mOnPrepareListener;
            if (onPrepareListener != null) {
                onPrepareListener.onPrepareSuccess();
                return;
            }
            return;
        }
        if (i2 == 0) {
            OnPrepareListener onPrepareListener2 = this.mOnPrepareListener;
            if (onPrepareListener2 != null) {
                onPrepareListener2.onPrepareFail();
                return;
            }
            return;
        }
        if (i2 == 2) {
            if (this.mOnThumbnailGetListener != null) {
                this.mOnThumbnailGetListener.onThumbnailGetFail(message.getData().getLong("pos"), (String) message.obj);
            }
        } else {
            if (i2 != 3 || this.mOnThumbnailGetListener == null) {
                return;
            }
            long j2 = message.getData().getLong("pos");
            long j3 = message.getData().getLong("start");
            long j4 = message.getData().getLong("until");
            Bitmap bitmap = (Bitmap) message.obj;
            ThumbnailBitmapInfo thumbnailBitmapInfo = new ThumbnailBitmapInfo();
            thumbnailBitmapInfo.setPositionRange(new long[]{j3, j4});
            thumbnailBitmapInfo.setThumbnailBitmap(bitmap);
            this.mOnThumbnailGetListener.onThumbnailGetSuccess(j2, thumbnailBitmapInfo);
        }
    }

    public static void loadClass() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static byte[] readStream(InputStream inputStream, AtomicBoolean atomicBoolean) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[1024];
        while (true) {
            try {
                try {
                    int i2 = inputStream.read(bArr);
                    if (i2 != -1) {
                        byteArrayOutputStream.write(bArr, 0, i2);
                    }
                } catch (IOException e2) {
                    atomicBoolean.set(true);
                    e2.printStackTrace();
                }
                try {
                    break;
                } catch (IOException e3) {
                }
            } finally {
                try {
                    byteArrayOutputStream.close();
                } catch (IOException e32) {
                    e32.printStackTrace();
                }
            }
        }
        return byteArrayOutputStream.toByteArray();
    }

    private void requestImgData(final String str, final OnImgDataResultListener onImgDataResultListener) {
        g.f12145a.submit(new Runnable() { // from class: com.aliyun.thumbnail.ThumbnailHelper.3
            /* JADX WARN: Code restructure failed: missing block: B:32:0x00b0, code lost:
            
                if (r2 == null) goto L35;
             */
            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r2v5, types: [java.io.IOException, java.lang.Throwable] */
            /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:21:0x0071 -> B:57:0x00b6). Please report as a decompilation issue!!! */
            /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:34:0x00b3 -> B:57:0x00b6). Please report as a decompilation issue!!! */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void run() throws java.io.IOException {
                /*
                    Method dump skipped, instructions count: 238
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.aliyun.thumbnail.ThumbnailHelper.AnonymousClass3.run():void");
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendPrepareFailMsg() {
        Message messageObtainMessage = this.mResultHandler.obtainMessage();
        messageObtainMessage.what = 0;
        this.mResultHandler.sendMessage(messageObtainMessage);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendPrepareSuccessMsg() {
        Message messageObtainMessage = this.mResultHandler.obtainMessage();
        messageObtainMessage.what = 1;
        this.mResultHandler.sendMessage(messageObtainMessage);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendRequestBitmapFailMsg(String str, long j2) {
        Message messageObtainMessage = this.mResultHandler.obtainMessage();
        messageObtainMessage.what = 2;
        messageObtainMessage.obj = str;
        Bundle bundle = new Bundle();
        bundle.putLong("pos", j2);
        messageObtainMessage.setData(bundle);
        this.mResultHandler.sendMessage(messageObtainMessage);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendRequestBitmapSuccMsg(ThumbnailInfo thumbnailInfo, long j2, Bitmap bitmap) {
        Message messageObtainMessage = this.mResultHandler.obtainMessage();
        messageObtainMessage.what = 3;
        messageObtainMessage.obj = bitmap;
        Bundle bundle = new Bundle();
        bundle.putLong("pos", j2);
        bundle.putLong("start", thumbnailInfo.mStart);
        bundle.putLong("until", thumbnailInfo.mUntil);
        messageObtainMessage.setData(bundle);
        this.mResultHandler.sendMessage(messageObtainMessage);
    }

    public void prepare() {
        synchronized (this.lock) {
            try {
                if (this.hasPrepared) {
                    Logger.e(TAG, "prepare again?");
                } else {
                    this.hasPrepared = true;
                    g.f12145a.submit(new Runnable() { // from class: com.aliyun.thumbnail.ThumbnailHelper.1
                        @Override // java.lang.Runnable
                        public void run() throws Throwable {
                            ByteHttp byteHttp = new ByteHttp();
                            byteHttp.doGet(ThumbnailHelper.this.mUrl);
                            Matcher matcher = Pattern.compile("([a-zA-Z]+://[^/]+).*[/]").matcher(ThumbnailHelper.this.mUrl);
                            if (matcher.find() && byteHttp.bytes != null) {
                                Object[] thumbnailInfos = ThumbnailHelper.this.getThumbnailInfos(matcher.group(0), new String(byteHttp.bytes));
                                if (thumbnailInfos == null) {
                                    ThumbnailHelper.this.mThumbnailInfoArray = null;
                                } else {
                                    ThumbnailHelper.this.mThumbnailInfoArray = (ThumbnailInfo[]) thumbnailInfos;
                                }
                            }
                            if (ThumbnailHelper.this.mThumbnailInfoArray != null) {
                                ThumbnailHelper.this.sendPrepareSuccessMsg();
                            } else {
                                ThumbnailHelper.this.sendPrepareFailMsg();
                            }
                        }
                    });
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void requestBitmapAtPosition(final long j2) {
        final ThumbnailInfo infoByPosition = getInfoByPosition(j2);
        if (infoByPosition != null) {
            requestImgData(infoByPosition.mPath, new OnImgDataResultListener() { // from class: com.aliyun.thumbnail.ThumbnailHelper.2
                @Override // com.aliyun.thumbnail.ThumbnailHelper.OnImgDataResultListener
                public void onFail() {
                    ThumbnailHelper.this.sendRequestBitmapFailMsg("can not get thumbnail at position:" + j2, j2);
                }

                @Override // com.aliyun.thumbnail.ThumbnailHelper.OnImgDataResultListener
                public void onSuccess(byte[] bArr) {
                    Bitmap bitmap = ThumbnailHelper.this.getBitmap(infoByPosition, bArr);
                    if (bitmap != null) {
                        ThumbnailHelper.this.sendRequestBitmapSuccMsg(infoByPosition, j2, bitmap);
                        return;
                    }
                    ThumbnailHelper.this.sendRequestBitmapFailMsg("can not get thumbnail at position:" + j2, j2);
                }
            });
            return;
        }
        sendRequestBitmapFailMsg("no match thumbnail at position:" + j2, j2);
    }

    public void setOnPrepareListener(OnPrepareListener onPrepareListener) {
        this.mOnPrepareListener = onPrepareListener;
    }

    public void setOnThumbnailGetListener(OnThumbnailGetListener onThumbnailGetListener) {
        this.mOnThumbnailGetListener = onThumbnailGetListener;
    }
}
