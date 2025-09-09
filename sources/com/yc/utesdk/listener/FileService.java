package com.yc.utesdk.listener;

import java.io.File;

/* loaded from: classes4.dex */
public interface FileService {

    public interface Callback {
        void onCompleted();

        void onFail(int i2, Throwable th);

        void onProgress(int i2, int i3);
    }

    public interface MultiCallback {
        void onCompleted(String str);

        void onFail(int i2, Throwable th);

        void onFound(String[] strArr);

        void onProgress(String str, int i2, int i3);

        File onStart(String str);
    }
}
