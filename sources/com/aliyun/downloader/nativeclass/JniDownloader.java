package com.aliyun.downloader.nativeclass;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.aliyun.downloader.AliMediaDownloader;
import com.aliyun.downloader.DownloaderConfig;
import com.aliyun.player.bean.ErrorCode;
import com.aliyun.player.bean.ErrorInfo;
import com.aliyun.player.nativeclass.MediaInfo;
import com.aliyun.player.source.VidAuth;
import com.aliyun.player.source.VidSts;
import com.aliyun.utils.f;
import com.cicada.player.utils.Logger;
import java.lang.ref.WeakReference;

/* loaded from: classes2.dex */
public class JniDownloader {
    static final String TAG = "JniDownloader";
    private static AliMediaDownloader.ConvertURLCallback sConvertURLCallback;
    private MainHandler mCurrentThreadHandler;
    private long mNativeContext;
    private AliMediaDownloader.OnPreparedListener mOnPreparedListener = null;
    private AliMediaDownloader.OnErrorListener mOnErrorListener = null;
    private AliMediaDownloader.OnProgressListener mOnProgressListener = null;
    private AliMediaDownloader.OnCompletionListener mOnCompletionListener = null;

    private static class MainHandler extends Handler {
        private WeakReference<JniDownloader> downloaderWeakReference;

        public MainHandler(JniDownloader jniDownloader, Looper looper) {
            super(looper);
            this.downloaderWeakReference = new WeakReference<>(jniDownloader);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            JniDownloader jniDownloader = this.downloaderWeakReference.get();
            if (jniDownloader != null) {
                jniDownloader.handleMessage(message);
            }
            super.handleMessage(message);
        }
    }

    static {
        f.f();
        f.e();
        sConvertURLCallback = null;
    }

    public JniDownloader(Context context) {
        if (!f.b()) {
            f.f();
        } else if (!f.a()) {
            f.e();
        } else {
            this.mCurrentThreadHandler = new MainHandler(this, Looper.getMainLooper());
            nConstruct();
        }
    }

    public static int deleteFile(String str, String str2, String str3, int i2) {
        if (f.b() && f.a()) {
            return sDeleteFile(str, str2, str3, i2);
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleMessage(Message message) {
    }

    protected static String nConvertURLCallback(String str, String str2) {
        AliMediaDownloader.ConvertURLCallback convertURLCallback = sConvertURLCallback;
        if (convertURLCallback != null) {
            return convertURLCallback.convertURL(str, str2);
        }
        return null;
    }

    private void onCompletion() {
        Logger.v(TAG, "onCompletion() ");
        this.mCurrentThreadHandler.post(new Runnable() { // from class: com.aliyun.downloader.nativeclass.JniDownloader.4
            @Override // java.lang.Runnable
            public void run() {
                if (JniDownloader.this.mOnCompletionListener != null) {
                    JniDownloader.this.mOnCompletionListener.onCompletion();
                }
            }
        });
    }

    private void onError(int i2, final String str, final String str2, String str3) {
        Logger.v(TAG, "onError() .. code = " + i2 + " , msg = " + str + " , extra = " + str2 + " , requestid = " + str3);
        final ErrorCode errorCode = ErrorCode.ERROR_UNKNOWN;
        ErrorCode[] errorCodeArrValues = ErrorCode.values();
        int length = errorCodeArrValues.length;
        int i3 = 0;
        while (true) {
            if (i3 >= length) {
                break;
            }
            ErrorCode errorCode2 = errorCodeArrValues[i3];
            if (errorCode2.getValue() == i2) {
                errorCode = errorCode2;
                break;
            }
            i3++;
        }
        this.mCurrentThreadHandler.post(new Runnable() { // from class: com.aliyun.downloader.nativeclass.JniDownloader.2
            @Override // java.lang.Runnable
            public void run() {
                if (JniDownloader.this.mOnErrorListener != null) {
                    ErrorInfo errorInfo = new ErrorInfo();
                    errorInfo.setCode(errorCode);
                    errorInfo.setMsg(str);
                    errorInfo.setExtra(str2);
                    JniDownloader.this.mOnErrorListener.onError(errorInfo);
                }
            }
        });
    }

    private void onPrepared(Object obj) {
        Logger.v(TAG, "onPrepared(mediaInfo) = " + obj);
        if (obj == null) {
            return;
        }
        final MediaInfo mediaInfo = (MediaInfo) obj;
        this.mCurrentThreadHandler.post(new Runnable() { // from class: com.aliyun.downloader.nativeclass.JniDownloader.1
            @Override // java.lang.Runnable
            public void run() {
                if (JniDownloader.this.mOnPreparedListener != null) {
                    JniDownloader.this.mOnPreparedListener.onPrepared(mediaInfo);
                }
            }
        });
    }

    private void onProgress(final int i2, final int i3) {
        Logger.v(TAG, "onProgress() .. type = " + i2 + ", percent = " + i3 + "%");
        this.mCurrentThreadHandler.post(new Runnable() { // from class: com.aliyun.downloader.nativeclass.JniDownloader.3
            @Override // java.lang.Runnable
            public void run() {
                if (JniDownloader.this.mOnProgressListener != null) {
                    if (i2 == 0) {
                        JniDownloader.this.mOnProgressListener.onDownloadingProgress(i3);
                    } else {
                        JniDownloader.this.mOnProgressListener.onProcessingProgress(i3);
                    }
                }
            }
        });
    }

    static native int sDeleteFile(String str, String str2, String str3, int i2);

    public static void setConvertURLCallback(AliMediaDownloader.ConvertURLCallback convertURLCallback) {
        sConvertURLCallback = convertURLCallback;
    }

    public String getFilePath() {
        if (!f.b() || !f.a()) {
            return "";
        }
        String strNGetFilePath = nGetFilePath();
        Logger.v(TAG, "getFilePath() , return = " + strNGetFilePath);
        return strNGetFilePath;
    }

    protected long getNativeContext() {
        return this.mNativeContext;
    }

    native void nConstruct();

    native void nDeleteFile();

    native String nGetFilePath();

    native void nPrepare(VidAuth vidAuth);

    native void nPrepare(VidSts vidSts);

    native void nRelease();

    native void nSelectItem(int i2);

    native void nSetConnectivityManager(Object obj);

    native void nSetDownloaderConfig(Object obj);

    native void nSetSaveDir(String str);

    native void nStart();

    native void nStop();

    native void nUpdateSource(VidAuth vidAuth);

    native void nUpdateSource(VidSts vidSts);

    public void prepare(VidAuth vidAuth) {
        if (f.b() && f.a()) {
            Logger.v(TAG, "prepare(vidAuth) vid :" + vidAuth.getVid());
            nPrepare(vidAuth);
        }
    }

    public void release() {
        if (f.b() && f.a()) {
            Logger.v(TAG, "release()");
            nRelease();
        }
    }

    public void selectItem(int i2) {
        if (f.b() && f.a()) {
            Logger.v(TAG, "selectItem(index) index :" + i2);
            nSelectItem(i2);
        }
    }

    public void setDownloaderConfig(DownloaderConfig downloaderConfig) {
        if (f.b() && f.a()) {
            Logger.v(TAG, "setDownloaderConfig() ");
            nSetDownloaderConfig(downloaderConfig);
        }
    }

    protected void setNativeContext(long j2) {
        Logger.d(TAG, "setNativeContext " + j2);
        this.mNativeContext = j2;
    }

    public void setOnCompletionListener(AliMediaDownloader.OnCompletionListener onCompletionListener) {
        this.mOnCompletionListener = onCompletionListener;
    }

    public void setOnErrorListener(AliMediaDownloader.OnErrorListener onErrorListener) {
        this.mOnErrorListener = onErrorListener;
    }

    public void setOnPreparedListener(AliMediaDownloader.OnPreparedListener onPreparedListener) {
        this.mOnPreparedListener = onPreparedListener;
    }

    public void setOnProgressListener(AliMediaDownloader.OnProgressListener onProgressListener) {
        this.mOnProgressListener = onProgressListener;
    }

    public void setSaveDir(String str) {
        if (f.b() && f.a()) {
            Logger.v(TAG, "setSaveDir() :" + str);
            nSetSaveDir(str);
        }
    }

    public void start() {
        if (f.b() && f.a()) {
            Logger.v(TAG, "start()");
            nStart();
        }
    }

    public void stop() {
        if (f.b() && f.a()) {
            Logger.v(TAG, "stop()");
            nStop();
        }
    }

    public void updateSource(VidAuth vidAuth) {
        if (f.b() && f.a()) {
            Logger.v(TAG, "updateSource(vidAuth) vid :" + vidAuth.getVid());
            nUpdateSource(vidAuth);
        }
    }

    public void deleteFile() {
        if (f.b() && f.a()) {
            Logger.v(TAG, "deleteFile()");
            nDeleteFile();
        }
    }

    public void prepare(VidSts vidSts) {
        if (f.b() && f.a()) {
            Logger.v(TAG, "prepare(vidSts) vid :" + vidSts.getVid());
            nPrepare(vidSts);
        }
    }

    public void updateSource(VidSts vidSts) {
        if (f.b() && f.a()) {
            Logger.v(TAG, "updateSource(vidsts) vid :" + vidSts.getVid());
            nUpdateSource(vidSts);
        }
    }
}
