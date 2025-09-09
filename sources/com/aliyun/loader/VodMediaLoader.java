package com.aliyun.loader;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.aliyun.player.bean.ErrorCode;
import com.aliyun.player.bean.ErrorInfo;
import com.aliyun.player.nativeclass.MediaInfo;
import com.aliyun.player.source.VidAuth;
import com.aliyun.player.source.VidSts;
import com.aliyun.utils.f;
import com.cicada.player.utils.NativeUsed;

/* loaded from: classes3.dex */
public class VodMediaLoader {
    private static final String TAG;
    private static Handler mMainHandler;
    private static VodMediaLoader sInstance;
    private OnLoadStatusListener onLoadStatusListener = null;

    public interface OnLoadStatusListener {
        void onCanceled(String str, int i2);

        void onCompleted(String str, int i2);

        @Deprecated
        void onError(String str, int i2, int i3, String str2);

        void onErrorV2(String str, int i2, ErrorInfo errorInfo);

        void onPrepared(MediaInfo mediaInfo);
    }

    static {
        f.f();
        TAG = MediaLoader.class.getSimpleName();
        sInstance = null;
        mMainHandler = new Handler(Looper.getMainLooper());
    }

    private VodMediaLoader() {
    }

    public static VodMediaLoader getInstance() {
        if (sInstance == null) {
            synchronized (MediaLoader.class) {
                try {
                    if (sInstance == null) {
                        sInstance = new VodMediaLoader();
                    }
                } finally {
                }
            }
        }
        return sInstance;
    }

    public static void loadClass() {
    }

    private static native void nCancel(String str, int i2);

    private static native void nLoad(String str, int i2, long j2);

    @NativeUsed
    private static void nOnCanceled(final String str, final int i2) {
        mMainHandler.post(new Runnable() { // from class: com.aliyun.loader.VodMediaLoader.5
            @Override // java.lang.Runnable
            public void run() {
                if (VodMediaLoader.getInstance().onLoadStatusListener != null) {
                    VodMediaLoader.getInstance().onLoadStatusListener.onCanceled(str, i2);
                }
            }
        });
    }

    @NativeUsed
    private static void nOnCompleted(final String str, final int i2) {
        mMainHandler.post(new Runnable() { // from class: com.aliyun.loader.VodMediaLoader.4
            @Override // java.lang.Runnable
            public void run() {
                if (VodMediaLoader.getInstance().onLoadStatusListener != null) {
                    VodMediaLoader.getInstance().onLoadStatusListener.onCompleted(str, i2);
                }
            }
        });
    }

    @NativeUsed
    private static void nOnError(final String str, final int i2, final int i3, final String str2) {
        mMainHandler.post(new Runnable() { // from class: com.aliyun.loader.VodMediaLoader.2
            @Override // java.lang.Runnable
            public void run() {
                if (VodMediaLoader.getInstance().onLoadStatusListener != null) {
                    VodMediaLoader.getInstance().onLoadStatusListener.onError(str, i2, i3, str2);
                }
            }
        });
    }

    @NativeUsed
    private static void nOnErrorV2(final String str, final int i2, int i3, final String str2) {
        final ErrorCode errorCode = ErrorCode.ERROR_UNKNOWN;
        ErrorCode[] errorCodeArrValues = ErrorCode.values();
        int length = errorCodeArrValues.length;
        int i4 = 0;
        while (true) {
            if (i4 >= length) {
                break;
            }
            ErrorCode errorCode2 = errorCodeArrValues[i4];
            if (errorCode2.getValue() == i3) {
                errorCode = errorCode2;
                break;
            }
            i4++;
        }
        mMainHandler.post(new Runnable() { // from class: com.aliyun.loader.VodMediaLoader.3
            @Override // java.lang.Runnable
            public void run() {
                if (VodMediaLoader.getInstance().onLoadStatusListener != null) {
                    ErrorInfo errorInfo = new ErrorInfo();
                    errorInfo.setCode(errorCode);
                    errorInfo.setMsg(str2);
                    VodMediaLoader.getInstance().onLoadStatusListener.onErrorV2(str, i2, errorInfo);
                    MediaLoader.getInstance().cancel(str);
                }
            }
        });
    }

    @NativeUsed
    private static void nOnPrepared(final Object obj) {
        mMainHandler.post(new Runnable() { // from class: com.aliyun.loader.VodMediaLoader.1
            @Override // java.lang.Runnable
            public void run() {
                if (VodMediaLoader.getInstance().onLoadStatusListener != null) {
                    VodMediaLoader.getInstance().onLoadStatusListener.onPrepared((MediaInfo) obj);
                }
            }
        });
    }

    private static native void nPause(boolean z2, String str, int i2);

    private static native void nPrepareAuth(Object obj);

    private static native void nPrepareSts(Object obj);

    private static native void nRemoveSource(String str);

    public void cancel(String str, int i2) {
        if (f.b()) {
            nCancel(str, i2);
        }
    }

    public void load(String str, int i2, long j2) {
        if (!f.b()) {
            f.f();
        } else {
            if (TextUtils.isEmpty(str) || i2 < 0) {
                return;
            }
            nLoad(str, i2, j2);
        }
    }

    public void pause(String str, int i2) {
        if (f.b() && !TextUtils.isEmpty(str) && i2 >= 0) {
            nPause(true, str, i2);
        }
    }

    public void prepareVidSource(VidAuth vidAuth) {
        if (f.b()) {
            nPrepareAuth(vidAuth);
        }
    }

    public void removeVidSource(String str) {
        if (f.b()) {
            nRemoveSource(str);
        }
    }

    public void resume(String str, int i2) {
        if (f.b() && !TextUtils.isEmpty(str) && i2 >= 0) {
            nPause(false, str, i2);
        }
    }

    public void setOnLoadStatusListener(OnLoadStatusListener onLoadStatusListener) {
        this.onLoadStatusListener = onLoadStatusListener;
    }

    public void prepareVidSource(VidSts vidSts) {
        if (f.b()) {
            nPrepareSts(vidSts);
        }
    }
}
