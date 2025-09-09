package com.alipay.android.phone.mrpc.core;

import java.io.Closeable;
import java.io.IOException;

/* loaded from: classes2.dex */
public final class r {
    public static void a(Closeable closeable) throws IOException {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException unused) {
            }
        }
    }
}
