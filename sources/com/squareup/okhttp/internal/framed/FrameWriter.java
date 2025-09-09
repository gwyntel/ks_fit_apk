package com.squareup.okhttp.internal.framed;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import okio.Buffer;

/* loaded from: classes4.dex */
public interface FrameWriter extends Closeable {
    void ackSettings(Settings settings) throws IOException;

    void connectionPreface() throws IOException;

    void data(boolean z2, int i2, Buffer buffer, int i3) throws IOException;

    void flush() throws IOException;

    void goAway(int i2, ErrorCode errorCode, byte[] bArr) throws IOException;

    void headers(int i2, List<Header> list) throws IOException;

    int maxDataLength();

    void ping(boolean z2, int i2, int i3) throws IOException;

    void pushPromise(int i2, int i3, List<Header> list) throws IOException;

    void rstStream(int i2, ErrorCode errorCode) throws IOException;

    void settings(Settings settings) throws IOException;

    void synReply(boolean z2, int i2, List<Header> list) throws IOException;

    void synStream(boolean z2, boolean z3, int i2, int i3, List<Header> list) throws IOException;

    void windowUpdate(int i2, long j2) throws IOException;
}
