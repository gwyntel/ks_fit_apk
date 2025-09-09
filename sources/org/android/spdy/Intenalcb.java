package org.android.spdy;

import java.util.List;
import java.util.Map;

/* loaded from: classes5.dex */
public interface Intenalcb {
    void bioPingRecvCallback(SpdySession spdySession, int i2);

    byte[] getSSLMeta(SpdySession spdySession);

    int putSSLMeta(SpdySession spdySession, byte[] bArr);

    void spdyCustomControlFrameFailCallback(SpdySession spdySession, Object obj, int i2, int i3);

    void spdyCustomControlFrameRecvCallback(SpdySession spdySession, Object obj, int i2, int i3, int i4, int i5, byte[] bArr);

    void spdyDataChunkRecvCB(SpdySession spdySession, boolean z2, long j2, SpdyByteArray spdyByteArray, int i2);

    void spdyDataRecvCallback(SpdySession spdySession, boolean z2, long j2, int i2, int i3);

    void spdyDataSendCallback(SpdySession spdySession, boolean z2, long j2, int i2, int i3);

    void spdyOnStreamResponse(SpdySession spdySession, long j2, Map<String, List<String>> map, int i2);

    void spdyPingRecvCallback(SpdySession spdySession, long j2, Object obj);

    void spdyRequestRecvCallback(SpdySession spdySession, long j2, int i2);

    void spdySessionCloseCallback(SpdySession spdySession, Object obj, SuperviseConnectInfo superviseConnectInfo, int i2);

    void spdySessionConnectCB(SpdySession spdySession, SuperviseConnectInfo superviseConnectInfo);

    void spdySessionFailedError(SpdySession spdySession, int i2, Object obj);

    void spdySessionOnWritable(SpdySession spdySession, Object obj, int i2);

    void spdyStreamCloseCallback(SpdySession spdySession, long j2, int i2, int i3, SuperviseData superviseData);
}
