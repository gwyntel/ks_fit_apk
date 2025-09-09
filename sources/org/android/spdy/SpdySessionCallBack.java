package org.android.spdy;

import java.util.List;
import java.util.Map;

/* loaded from: classes5.dex */
final class SpdySessionCallBack implements Intenalcb {
    SpdySessionCallBack() {
    }

    @Override // org.android.spdy.Intenalcb
    public void bioPingRecvCallback(SpdySession spdySession, int i2) {
        spduLog.Logi("tnet-jni", "[SpdySessionCallBack.bioPingRecvCallback] - " + spdySession);
        SessionCb sessionCb = spdySession.sessionCallBack;
        if (sessionCb != null) {
            sessionCb.bioPingRecvCallback(spdySession, i2);
        } else {
            spduLog.Loge("tnet-jni", "[SpdySessionCallBack.bioPingRecvCallback] - no sessionCallBack.");
        }
    }

    @Override // org.android.spdy.Intenalcb
    public byte[] getSSLMeta(SpdySession spdySession) {
        spduLog.Logi("tnet-jni", "[SpdySessionCallBack.getSSLMeta] - " + spdySession);
        SessionCb sessionCb = spdySession.sessionCallBack;
        if (sessionCb != null) {
            return sessionCb.getSSLMeta(spdySession);
        }
        spduLog.Loge("tnet-jni", "[SpdySessionCallBack.getSSLMeta] - no sessionCallBack.");
        return null;
    }

    @Override // org.android.spdy.Intenalcb
    public int putSSLMeta(SpdySession spdySession, byte[] bArr) {
        spduLog.Logi("tnet-jni", "[SpdySessionCallBack.putSSLMeta] - " + spdySession);
        SessionCb sessionCb = spdySession.sessionCallBack;
        if (sessionCb != null) {
            return sessionCb.putSSLMeta(spdySession, bArr);
        }
        spduLog.Loge("tnet-jni", "[SpdySessionCallBack.putSSLMeta] - no sessionCallBack.");
        return -1;
    }

    @Override // org.android.spdy.Intenalcb
    public void spdyCustomControlFrameFailCallback(SpdySession spdySession, Object obj, int i2, int i3) {
        spduLog.Logi("tnet-jni", "[SpdySessionCallBack.spdyCustomControlFrameFailCallback] - " + spdySession);
        SessionCb sessionCb = spdySession.sessionCallBack;
        if (sessionCb != null) {
            sessionCb.spdyCustomControlFrameFailCallback(spdySession, obj, i2, i3);
        } else {
            spduLog.Loge("tnet-jni", "[SpdySessionCallBack.spdyCustomControlFrameFailCallback] - no sessionCallBack.");
        }
    }

    @Override // org.android.spdy.Intenalcb
    public void spdyCustomControlFrameRecvCallback(SpdySession spdySession, Object obj, int i2, int i3, int i4, int i5, byte[] bArr) {
        spduLog.Logi("tnet-jni", "[SpdySessionCallBack.spdyCustomControlFrameRecvCallback] - " + spdySession);
        SessionCb sessionCb = spdySession.sessionCallBack;
        if (sessionCb != null) {
            sessionCb.spdyCustomControlFrameRecvCallback(spdySession, obj, i2, i3, i4, i5, bArr);
        } else {
            spduLog.Loge("tnet-jni", "[SpdySessionCallBack.spdyCustomControlFrameRecvCallback] - no sessionCallBack.");
        }
    }

    @Override // org.android.spdy.Intenalcb
    public void spdyDataChunkRecvCB(SpdySession spdySession, boolean z2, long j2, SpdyByteArray spdyByteArray, int i2) {
        Spdycb spdycb;
        spduLog.Logi("tnet-jni", "[SpdySessionCallBack.spdyDataChunkRecvCB] - " + spdySession);
        long jBegin = NetTimeGaurd.begin();
        SpdyStreamContext spdyStream = spdySession.getSpdyStream(i2);
        if (spdyStream == null || (spdycb = spdyStream.callBack) == null) {
            spduLog.Loge("tnet-jni", "[SpdySessionCallBack.spdyDataChunkRecvCB] - no sessionCallBack.");
        } else {
            spdycb.spdyDataChunkRecvCB(spdySession, z2, j2, spdyByteArray, spdyStream.streamContext);
        }
        NetTimeGaurd.end("spdyDataChunkRecvCB", 3, jBegin);
    }

    @Override // org.android.spdy.Intenalcb
    public void spdyDataRecvCallback(SpdySession spdySession, boolean z2, long j2, int i2, int i3) {
        Spdycb spdycb;
        spduLog.Logi("tnet-jni", "[SpdySessionCallBack.spdyDataRecvCallback] - " + spdySession);
        long jBegin = NetTimeGaurd.begin();
        SpdyStreamContext spdyStream = spdySession.getSpdyStream(i3);
        if (spdyStream == null || (spdycb = spdyStream.callBack) == null) {
            spduLog.Loge("tnet-jni", "[SpdySessionCallBack.spdyDataRecvCallback] - no sessionCallBack.");
        } else {
            spdycb.spdyDataRecvCallback(spdySession, z2, j2, i2, spdyStream.streamContext);
        }
        NetTimeGaurd.end("spdyDataRecvCallback", 3, jBegin);
    }

    @Override // org.android.spdy.Intenalcb
    public void spdyDataSendCallback(SpdySession spdySession, boolean z2, long j2, int i2, int i3) {
        Spdycb spdycb;
        spduLog.Logi("tnet-jni", "[SpdySessionCallBack.spdyDataSendCallback] - ");
        SpdyStreamContext spdyStream = spdySession.getSpdyStream(i3);
        if (spdyStream == null || (spdycb = spdyStream.callBack) == null) {
            spduLog.Loge("tnet-jni", "[SpdySessionCallBack.spdyDataSendCallback] - no sessionCallBack.");
        } else {
            spdycb.spdyDataSendCallback(spdySession, z2, j2, i2, spdyStream.streamContext);
        }
    }

    @Override // org.android.spdy.Intenalcb
    public void spdyOnStreamResponse(SpdySession spdySession, long j2, Map<String, List<String>> map, int i2) {
        Spdycb spdycb;
        spduLog.Logi("tnet-jni", "[SpdySessionCallBack.spdyOnStreamResponse] - " + spdySession);
        NetTimeGaurd.start(3);
        long jBegin = NetTimeGaurd.begin();
        SpdyStreamContext spdyStream = spdySession.getSpdyStream(i2);
        if (spdyStream == null || (spdycb = spdyStream.callBack) == null) {
            spduLog.Loge("tnet-jni", "[SpdySessionCallBack.spdyOnStreamResponse] - no sessionCallBack.");
        } else {
            spdycb.spdyOnStreamResponse(spdySession, j2, map, spdyStream.streamContext);
        }
        NetTimeGaurd.end("spdyOnStreamResponse", 3, jBegin);
    }

    @Override // org.android.spdy.Intenalcb
    public void spdyPingRecvCallback(SpdySession spdySession, long j2, Object obj) {
        spduLog.Logi("tnet-jni", "[SpdySessionCallBack.spdyPingRecvCallback] - " + spdySession);
        NetTimeGaurd.start(1);
        if (spdySession.sessionCallBack != null) {
            long jBegin = NetTimeGaurd.begin();
            spdySession.sessionCallBack.spdyPingRecvCallback(spdySession, j2, obj);
            NetTimeGaurd.end("spdyPingRecvCallback", 1, jBegin);
        } else {
            spduLog.Loge("tnet-jni", "[SpdySessionCallBack.spdyPingRecvCallback] - no sessionCallBack.");
        }
        NetTimeGaurd.finish(1);
    }

    @Override // org.android.spdy.Intenalcb
    public void spdyRequestRecvCallback(SpdySession spdySession, long j2, int i2) {
        Spdycb spdycb;
        spduLog.Logd("tnet-jni", "[SpdySessionCallBack.spdyOnStreamResponse] - " + spdySession);
        long jBegin = NetTimeGaurd.begin();
        SpdyStreamContext spdyStream = spdySession.getSpdyStream(i2);
        if (spdyStream == null || (spdycb = spdyStream.callBack) == null) {
            spduLog.Loge("tnet-jni", "[SpdySessionCallBack.spdyRequestRecvCallback] - no sessionCallBack.");
        } else {
            spdycb.spdyRequestRecvCallback(spdySession, j2, spdyStream.streamContext);
        }
        NetTimeGaurd.end("spdyPingRecvCallback", 3, jBegin);
    }

    @Override // org.android.spdy.Intenalcb
    public void spdySessionCloseCallback(SpdySession spdySession, Object obj, SuperviseConnectInfo superviseConnectInfo, int i2) {
        spduLog.Logi("tnet-jni", "[SpdySessionCallBack.spdySessionCloseCallback] - " + spdySession);
        SessionCb sessionCb = spdySession.sessionCallBack;
        if (sessionCb != null) {
            sessionCb.spdySessionCloseCallback(spdySession, obj, superviseConnectInfo, i2);
        } else {
            spduLog.Loge("tnet-jni", "[SpdySessionCallBack.spdySessionCloseCallback] - no sessionCallBack.");
        }
    }

    @Override // org.android.spdy.Intenalcb
    public void spdySessionConnectCB(SpdySession spdySession, SuperviseConnectInfo superviseConnectInfo) {
        spduLog.Logi("tnet-jni", "[SpdySessionCallBack.spdySessionConnectCB] - " + spdySession);
        NetTimeGaurd.start(0);
        if (spdySession.sessionCallBack != null) {
            long jBegin = NetTimeGaurd.begin();
            spdySession.sessionCallBack.spdySessionConnectCB(spdySession, superviseConnectInfo);
            NetTimeGaurd.end("spdySessionConnectCB", 0, jBegin);
        } else {
            spduLog.Loge("tnet-jni", "[SpdySessionCallBack.spdySessionConnectCB] - no sessionCallBack.");
        }
        NetTimeGaurd.finish(0);
    }

    @Override // org.android.spdy.Intenalcb
    public void spdySessionFailedError(SpdySession spdySession, int i2, Object obj) {
        spduLog.Logi("tnet-jni", "[SpdySessionCallBack.spdySessionFailedError] - " + spdySession);
        NetTimeGaurd.start(2);
        if (spdySession.sessionCallBack != null) {
            long jBegin = NetTimeGaurd.begin();
            spdySession.sessionCallBack.spdySessionFailedError(spdySession, i2, obj);
            spdySession.clearAllStreamCb();
            NetTimeGaurd.end("spdySessionFailedError", 2, jBegin);
        } else {
            spduLog.Loge("tnet-jni", "[SpdySessionCallBack.spdySessionFailedError] - no sessionCallBack.");
        }
        NetTimeGaurd.finish(2);
    }

    @Override // org.android.spdy.Intenalcb
    public void spdySessionOnWritable(SpdySession spdySession, Object obj, int i2) {
        spduLog.Logi("tnet-jni", "[SpdySessionCallBack.spdySessionOnWritable] - " + spdySession);
        NetTimeGaurd.start(2);
        SessionCb sessionCb = spdySession.sessionCallBack;
        if (sessionCb == null || !(sessionCb instanceof SessionExtraCb)) {
            spduLog.Loge("tnet-jni", "[SpdySessionCallBack.spdySessionOnWritable] - no sessionCallBack.");
        } else {
            long jBegin = NetTimeGaurd.begin();
            ((SessionExtraCb) spdySession.sessionCallBack).spdySessionOnWritable(spdySession, obj, i2);
            NetTimeGaurd.end("spdySessionOnWritable", 2, jBegin);
        }
        NetTimeGaurd.finish(2);
    }

    @Override // org.android.spdy.Intenalcb
    public void spdyStreamCloseCallback(SpdySession spdySession, long j2, int i2, int i3, SuperviseData superviseData) {
        spduLog.Logi("tnet-jni", "[SpdySessionCallBack.spdyStreamCloseCallback] - " + spdySession);
        long jBegin = NetTimeGaurd.begin();
        SpdyStreamContext spdyStream = spdySession.getSpdyStream(i3);
        if (spdyStream == null || spdyStream.callBack == null) {
            spduLog.Loge("tnet-jni", "[SpdySessionCallBack.spdyStreamCloseCallback] - no sessionCallBack.");
        } else {
            spduLog.Logi("tnet-jni", "index=" + i3 + "    endtime=" + System.currentTimeMillis());
            spdyStream.callBack.spdyStreamCloseCallback(spdySession, j2, i2, spdyStream.streamContext, superviseData);
            spdySession.removeSpdyStream(i3);
        }
        NetTimeGaurd.end("spdyStreamCloseCallback", 3, jBegin);
        NetTimeGaurd.finish(3);
    }
}
