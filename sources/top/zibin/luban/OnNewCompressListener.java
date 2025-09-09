package top.zibin.luban;

import java.io.File;

/* loaded from: classes5.dex */
public interface OnNewCompressListener {
    void onError(String str, Throwable th);

    void onStart();

    void onSuccess(String str, File file);
}
