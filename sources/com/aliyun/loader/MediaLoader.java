package com.aliyun.loader;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.aliyun.player.bean.ErrorCode;
import com.aliyun.player.bean.ErrorInfo;
import com.aliyun.utils.f;
import com.cicada.player.utils.NativeUsed;

/* loaded from: classes3.dex */
public class MediaLoader {
    public static final int OLD_CODE_EADDED = -300;
    public static final int OLD_CODE_ENOT_ENABLE = -301;
    private static final String TAG;
    private static Handler mMainHandler;
    private static MediaLoader sInstance;
    private OnLoadStatusListener onLoadStatusListener = null;

    public interface OnLoadStatusListener {
        void onCanceled(String str);

        void onCompleted(String str);

        @Deprecated
        void onError(String str, int i2, String str2);

        void onErrorV2(String str, ErrorInfo errorInfo);
    }

    static {
        f.f();
        TAG = MediaLoader.class.getSimpleName();
        sInstance = null;
        mMainHandler = new Handler(Looper.getMainLooper());
    }

    private MediaLoader() {
    }

    public static MediaLoader getInstance() {
        if (sInstance == null) {
            synchronized (MediaLoader.class) {
                try {
                    if (sInstance == null) {
                        sInstance = new MediaLoader();
                    }
                } finally {
                }
            }
        }
        return sInstance;
    }

    public static void loadClass() {
    }

    private static native void nCancel(String str);

    private static native void nCancelAll();

    private static native void nLoad(String str, long j2, int i2, int i3);

    @NativeUsed
    private static void nOnCanceled(final String str) {
        mMainHandler.post(new Runnable() { // from class: com.aliyun.loader.MediaLoader.4
            @Override // java.lang.Runnable
            public void run() {
                if (MediaLoader.getInstance().onLoadStatusListener != null) {
                    MediaLoader.getInstance().onLoadStatusListener.onCanceled(str);
                }
            }
        });
    }

    @NativeUsed
    private static void nOnCompleted(final String str) {
        mMainHandler.post(new Runnable() { // from class: com.aliyun.loader.MediaLoader.3
            @Override // java.lang.Runnable
            public void run() {
                if (MediaLoader.getInstance().onLoadStatusListener != null) {
                    MediaLoader.getInstance().onLoadStatusListener.onCompleted(str);
                }
                MediaLoader.getInstance().cancel(str);
            }
        });
    }

    @NativeUsed
    private static void nOnError(final String str, final int i2, final String str2) {
        mMainHandler.post(new Runnable() { // from class: com.aliyun.loader.MediaLoader.1
            @Override // java.lang.Runnable
            public void run() {
                if (MediaLoader.getInstance().onLoadStatusListener != null) {
                    MediaLoader.getInstance().onLoadStatusListener.onError(str, i2, str2);
                }
                if (i2 != -300) {
                    MediaLoader.getInstance().cancel(str);
                }
            }
        });
    }

    @NativeUsed
    private static void nOnErrorV2(final String str, final int i2, final String str2) {
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
        mMainHandler.post(new Runnable() { // from class: com.aliyun.loader.MediaLoader.2
            @Override // java.lang.Runnable
            public void run() {
                if (MediaLoader.getInstance().onLoadStatusListener != null) {
                    ErrorInfo errorInfo = new ErrorInfo();
                    errorInfo.setCode(errorCode);
                    errorInfo.setMsg(str2);
                    MediaLoader.getInstance().onLoadStatusListener.onErrorV2(str, errorInfo);
                }
                if (i2 != ErrorCode.MEDIALOADER_ERROR_ADDED.getValue()) {
                    MediaLoader.getInstance().cancel(str);
                }
            }
        });
    }

    private static native void nPause(boolean z2, String str);

    public void cancel(String str) {
        if (f.b()) {
            if (TextUtils.isEmpty(str)) {
                nCancelAll();
            } else {
                nCancel(str);
            }
        }
    }

    public void load(String str, long j2) {
        if (!f.b()) {
            f.f();
        } else {
            if (TextUtils.isEmpty(str)) {
                return;
            }
            nLoad(str, j2, 0, 0);
        }
    }

    public void loadWithResolution(String str, long j2, int i2) {
        if (f.b() && !TextUtils.isEmpty(str)) {
            nLoad(str, j2, -1, i2);
        }
    }

    public void pause(String str) {
        if (f.b()) {
            nPause(true, str);
        }
    }

    public void resume(String str) {
        if (f.b()) {
            nPause(false, str);
        }
    }

    public void setOnLoadStatusListener(OnLoadStatusListener onLoadStatusListener) {
        this.onLoadStatusListener = onLoadStatusListener;
    }

    public void load(String str, long j2, int i2) {
        if (f.b() && !TextUtils.isEmpty(str)) {
            nLoad(str, j2, i2, -1);
        }
    }
}
