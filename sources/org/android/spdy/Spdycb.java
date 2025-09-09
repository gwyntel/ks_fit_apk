package org.android.spdy;

import java.util.List;
import java.util.Map;

/* loaded from: classes5.dex */
public interface Spdycb {
    void spdyDataChunkRecvCB(SpdySession spdySession, boolean z2, long j2, SpdyByteArray spdyByteArray, Object obj);

    void spdyDataRecvCallback(SpdySession spdySession, boolean z2, long j2, int i2, Object obj);

    void spdyDataSendCallback(SpdySession spdySession, boolean z2, long j2, int i2, Object obj);

    void spdyOnStreamResponse(SpdySession spdySession, long j2, Map<String, List<String>> map, Object obj);

    void spdyRequestRecvCallback(SpdySession spdySession, long j2, Object obj);

    void spdyStreamCloseCallback(SpdySession spdySession, long j2, int i2, Object obj, SuperviseData superviseData);
}
