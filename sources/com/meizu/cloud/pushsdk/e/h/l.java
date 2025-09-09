package com.meizu.cloud.pushsdk.e.h;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;

/* loaded from: classes4.dex */
public interface l extends Closeable, Flushable {
    void a(b bVar, long j2) throws IOException;

    void close() throws IOException;

    @Override // java.io.Flushable
    void flush() throws IOException;
}
