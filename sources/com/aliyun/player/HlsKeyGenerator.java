package com.aliyun.player;

import com.aliyun.utils.f;

/* loaded from: classes3.dex */
public class HlsKeyGenerator {
    private static HlsKeyGenerator instance;
    private OnKeyGenerateListener mOnKeyGenerateListener = null;

    public interface OnKeyGenerateListener {
        byte[] getHlsKey(String str, String str2);

        void onHlsKeyInfoInit(String str, int i2, String str2);
    }

    static {
        f.f();
        instance = null;
    }

    private HlsKeyGenerator() {
    }

    private static byte[] getHlsKey(String str, String str2) {
        OnKeyGenerateListener onKeyGenerateListener = getInstance().mOnKeyGenerateListener;
        if (onKeyGenerateListener != null) {
            return onKeyGenerateListener.getHlsKey(str, str2);
        }
        return null;
    }

    public static HlsKeyGenerator getInstance() {
        if (instance == null) {
            synchronized (HlsKeyGenerator.class) {
                try {
                    if (instance == null) {
                        instance = new HlsKeyGenerator();
                    }
                } finally {
                }
            }
        }
        return instance;
    }

    public static void loadClass() {
    }

    private static void onHlsKeyInfoInit(String str, int i2, String str2) {
        OnKeyGenerateListener onKeyGenerateListener = getInstance().mOnKeyGenerateListener;
        if (onKeyGenerateListener != null) {
            onKeyGenerateListener.onHlsKeyInfoInit(str, i2, str2);
        }
    }

    public void setOnKeyGenerateListener(OnKeyGenerateListener onKeyGenerateListener) {
        this.mOnKeyGenerateListener = onKeyGenerateListener;
    }
}
