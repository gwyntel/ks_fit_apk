package com.squareup.okhttp.internal.framed;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import okio.BufferedSource;
import okio.ByteString;

/* loaded from: classes4.dex */
public interface FrameReader extends Closeable {

    public interface Handler {
        void ackSettings();

        void alternateService(int i2, String str, ByteString byteString, String str2, int i3, long j2);

        void data(boolean z2, int i2, BufferedSource bufferedSource, int i3) throws IOException;

        void goAway(int i2, ErrorCode errorCode, ByteString byteString);

        void headers(boolean z2, boolean z3, int i2, int i3, List<Header> list, HeadersMode headersMode);

        void ping(boolean z2, int i2, int i3);

        void priority(int i2, int i3, int i4, boolean z2);

        void pushPromise(int i2, int i3, List<Header> list) throws IOException;

        void rstStream(int i2, ErrorCode errorCode);

        void settings(boolean z2, Settings settings);

        void windowUpdate(int i2, long j2);
    }

    boolean nextFrame(Handler handler) throws IOException;

    void readConnectionPreface() throws IOException;
}
