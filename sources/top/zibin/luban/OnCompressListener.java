package top.zibin.luban;

import java.io.File;

/* loaded from: classes5.dex */
public interface OnCompressListener {
    void onError(int i2, Throwable th);

    void onStart();

    void onSuccess(int i2, File file);
}
