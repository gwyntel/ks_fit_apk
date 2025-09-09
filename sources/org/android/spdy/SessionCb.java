package org.android.spdy;

/* loaded from: classes5.dex */
public interface SessionCb {
    void bioPingRecvCallback(SpdySession spdySession, int i2);

    byte[] getSSLMeta(SpdySession spdySession);

    int putSSLMeta(SpdySession spdySession, byte[] bArr);

    void spdyCustomControlFrameFailCallback(SpdySession spdySession, Object obj, int i2, int i3);

    void spdyCustomControlFrameRecvCallback(SpdySession spdySession, Object obj, int i2, int i3, int i4, int i5, byte[] bArr);

    void spdyPingRecvCallback(SpdySession spdySession, long j2, Object obj);

    void spdySessionCloseCallback(SpdySession spdySession, Object obj, SuperviseConnectInfo superviseConnectInfo, int i2);

    void spdySessionConnectCB(SpdySession spdySession, SuperviseConnectInfo superviseConnectInfo);

    void spdySessionFailedError(SpdySession spdySession, int i2, Object obj);
}
