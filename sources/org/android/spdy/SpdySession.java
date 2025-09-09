package org.android.spdy;

import android.os.Handler;
import android.os.HandlerThread;
import java.util.concurrent.atomic.AtomicBoolean;
import org.android.spdy.ProtectedPointer;

/* loaded from: classes5.dex */
public final class SpdySession {
    private static volatile int count;
    private SpdyAgent agent;
    private String authority;
    private String domain;
    private Handler handler;
    Intenalcb intenalcb;
    private int mode;
    private ProtectedPointer pptr4sessionNativePtr;
    private int pubkey_seqnum;
    SessionCb sessionCallBack;
    private volatile long sessionNativePtr;
    private NetSparseArray<SpdyStreamContext> spdyStream;
    private HandlerThread thread;
    private Object userData;
    private AtomicBoolean closed = new AtomicBoolean();
    private boolean sessionClearedFromSessionMgr = false;
    private Object lock = new Object();
    private int streamcount = 1;
    volatile int refcount = 1;

    SpdySession(long j2, SpdyAgent spdyAgent, String str, String str2, SessionCb sessionCb, int i2, int i3, Object obj) {
        this.spdyStream = null;
        this.sessionCallBack = null;
        this.pubkey_seqnum = 0;
        this.userData = null;
        this.sessionNativePtr = j2;
        ProtectedPointer protectedPointer = new ProtectedPointer(this);
        this.pptr4sessionNativePtr = protectedPointer;
        protectedPointer.setHow2close(new ProtectedPointer.ProtectedPointerOnClose() { // from class: org.android.spdy.SpdySession.1
            @Override // org.android.spdy.ProtectedPointer.ProtectedPointerOnClose
            public void close(Object obj2) {
                SpdySession spdySession = (SpdySession) obj2;
                spdySession.NotifyNotInvokeAnyMoreN(spdySession.sessionNativePtr);
                spdySession.setSessionNativePtr(0L);
            }
        });
        this.agent = spdyAgent;
        this.authority = str;
        this.intenalcb = new SpdySessionCallBack();
        this.domain = str2;
        this.spdyStream = new NetSparseArray<>(5);
        this.sessionCallBack = sessionCb;
        this.pubkey_seqnum = i3;
        this.mode = i2;
        this.userData = obj;
        this.closed.set(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public native int NotifyNotInvokeAnyMoreN(long j2);

    private int closeprivate() {
        synchronized (this.lock) {
            try {
                if (!this.sessionClearedFromSessionMgr) {
                    this.agent.clearSpdySession(this.authority, this.domain, this.mode);
                    this.sessionClearedFromSessionMgr = true;
                }
            } finally {
            }
        }
        synchronized (this.lock) {
            try {
                SpdyStreamContext[] allStreamCb = getAllStreamCb();
                if (allStreamCb != null) {
                    for (SpdyStreamContext spdyStreamContext : allStreamCb) {
                        spduLog.Logi("tnet-jni", "[SpdySessionCallBack.spdyStreamCloseCallback] unfinished stm=" + spdyStreamContext.streamId);
                        spdyStreamContext.callBack.spdyStreamCloseCallback(this, (long) spdyStreamContext.streamId, TnetStatusCode.EASY_REASON_CONN_NOT_EXISTS, spdyStreamContext.streamContext, null);
                    }
                }
                this.spdyStream.clear();
            } finally {
            }
        }
        return 0;
    }

    private String getAuthority() {
        return this.authority;
    }

    private native int sendCustomControlFrameN(long j2, int i2, int i3, int i4, int i5, byte[] bArr);

    private native int sendHeadersN(long j2, int i2, String[] strArr, boolean z2);

    private native int setOptionN(long j2, int i2, int i3);

    private native int streamCloseN(long j2, int i2, int i3);

    private native int streamSendDataN(long j2, int i2, byte[] bArr, int i3, int i4, boolean z2);

    private native int submitBioPingN(long j2);

    private native int submitPingN(long j2);

    private native int submitRequestN(long j2, String str, byte b2, String[] strArr, byte[] bArr, boolean z2, int i2, int i3, int i4);

    public int cleanUp() {
        spduLog.Logd("tnet-jni", "[SpdySession.cleanUp] - ");
        if (this.closed.getAndSet(true)) {
            return 0;
        }
        this.agent.removeSession(this);
        return closeprivate();
    }

    public void clearAllStreamCb() {
        spduLog.Logd("tnet-jni", "[SpdySession.clearAllStreamCb] - ");
        synchronized (this.lock) {
            this.spdyStream.clear();
        }
    }

    int closeInternal() {
        if (this.closed.getAndSet(true)) {
            return 0;
        }
        return closeprivate();
    }

    public int closeSession() {
        int iCloseSession;
        ProtectedPointer protectedPointer;
        spduLog.Logd("tnet-jni", "[SpdySession.closeSession] - ");
        synchronized (this.lock) {
            iCloseSession = 0;
            if (!this.sessionClearedFromSessionMgr) {
                spduLog.Logd("tnet-jni", "[SpdySession.closeSession] - " + this.authority);
                this.agent.clearSpdySession(this.authority, this.domain, this.mode);
                this.sessionClearedFromSessionMgr = true;
                if (this.pptr4sessionNativePtr.enter()) {
                    try {
                        try {
                            iCloseSession = this.agent.closeSession(this.sessionNativePtr);
                            protectedPointer = this.pptr4sessionNativePtr;
                        } catch (Throwable th) {
                            this.pptr4sessionNativePtr.exit();
                            throw th;
                        }
                    } catch (UnsatisfiedLinkError e2) {
                        e2.printStackTrace();
                        protectedPointer = this.pptr4sessionNativePtr;
                    }
                    protectedPointer.exit();
                } else {
                    iCloseSession = TnetStatusCode.EASY_REASON_CONN_NOT_EXISTS;
                }
            }
        }
        return iCloseSession;
    }

    public SpdyStreamContext[] getAllStreamCb() {
        SpdyStreamContext[] spdyStreamContextArr;
        synchronized (this.lock) {
            try {
                int size = this.spdyStream.size();
                if (size > 0) {
                    spdyStreamContextArr = new SpdyStreamContext[size];
                    this.spdyStream.toArray(spdyStreamContextArr);
                } else {
                    spdyStreamContextArr = null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return spdyStreamContextArr;
    }

    public String getDomain() {
        return this.domain;
    }

    Handler getMsgHandler() {
        return this.handler;
    }

    public int getRefCount() {
        return this.refcount;
    }

    long getSessionNativePtr() {
        return this.sessionNativePtr;
    }

    SpdyAgent getSpdyAgent() {
        return this.agent;
    }

    SpdyStreamContext getSpdyStream(int i2) {
        SpdyStreamContext spdyStreamContext;
        if (i2 <= 0) {
            return null;
        }
        synchronized (this.lock) {
            spdyStreamContext = this.spdyStream.get(i2);
        }
        return spdyStreamContext;
    }

    public Object getUserData() {
        return this.userData;
    }

    void increRefCount() {
        this.refcount++;
    }

    int putSpdyStreamCtx(SpdyStreamContext spdyStreamContext) {
        int i2;
        synchronized (this.lock) {
            i2 = this.streamcount;
            this.streamcount = i2 + 1;
            this.spdyStream.put(i2, spdyStreamContext);
        }
        return i2;
    }

    void releasePptr() {
        this.pptr4sessionNativePtr.release();
    }

    void removeSpdyStream(int i2) {
        if (i2 > 0) {
            synchronized (this.lock) {
                this.spdyStream.remove(i2);
            }
        }
    }

    public int sendCustomControlFrame(int i2, int i3, int i4, int i5, byte[] bArr) throws SpdyErrorException {
        int iSendCustomControlFrameN;
        sessionIsOpen();
        if (bArr != null && bArr.length <= 0) {
            bArr = null;
        }
        byte[] bArr2 = bArr;
        spduLog.Logi("tnet-jni", "[sendCustomControlFrame] - type: " + i3);
        if (this.pptr4sessionNativePtr.enter()) {
            iSendCustomControlFrameN = sendCustomControlFrameN(this.sessionNativePtr, i2, i3, i4, i5, bArr2);
            this.pptr4sessionNativePtr.exit();
        } else {
            iSendCustomControlFrameN = TnetStatusCode.EASY_REASON_CONN_NOT_EXISTS;
        }
        if (iSendCustomControlFrameN == 0) {
            return iSendCustomControlFrameN;
        }
        throw new SpdyErrorException("sendCustomControlFrame error: " + iSendCustomControlFrameN, iSendCustomControlFrameN);
    }

    void sessionIsOpen() {
        if (this.closed.get()) {
            throw new SpdyErrorException("session is already closed: -1104", TnetStatusCode.TNET_JNI_ERR_ASYNC_CLOSE);
        }
    }

    public int setOption(int i2, int i3) throws SpdyErrorException {
        int optionN;
        sessionIsOpen();
        if (this.pptr4sessionNativePtr.enter()) {
            optionN = setOptionN(this.sessionNativePtr, i2, i3);
            this.pptr4sessionNativePtr.exit();
        } else {
            optionN = TnetStatusCode.EASY_REASON_CONN_NOT_EXISTS;
        }
        if (optionN == 0) {
            return optionN;
        }
        throw new SpdyErrorException("setOption error: " + optionN, optionN);
    }

    void setSessionNativePtr(long j2) {
        this.sessionNativePtr = j2;
    }

    public int streamReset(long j2, int i2) throws SpdyErrorException {
        int iStreamCloseN;
        sessionIsOpen();
        spduLog.Logd("tnet-jni", "[SpdySession.streamReset] - ");
        if (this.pptr4sessionNativePtr.enter()) {
            iStreamCloseN = streamCloseN(this.sessionNativePtr, (int) j2, i2);
            this.pptr4sessionNativePtr.exit();
        } else {
            iStreamCloseN = TnetStatusCode.EASY_REASON_CONN_NOT_EXISTS;
        }
        if (iStreamCloseN == 0) {
            return iStreamCloseN;
        }
        throw new SpdyErrorException("streamReset error: " + iStreamCloseN, iStreamCloseN);
    }

    @Deprecated
    public int submitBioPing() throws SpdyErrorException {
        int iSubmitBioPingN;
        sessionIsOpen();
        if (this.pptr4sessionNativePtr.enter()) {
            iSubmitBioPingN = submitBioPingN(this.sessionNativePtr);
            this.pptr4sessionNativePtr.exit();
        } else {
            iSubmitBioPingN = TnetStatusCode.EASY_REASON_CONN_NOT_EXISTS;
        }
        if (iSubmitBioPingN == 0) {
            return iSubmitBioPingN;
        }
        throw new SpdyErrorException("submitBioPing error: " + iSubmitBioPingN, iSubmitBioPingN);
    }

    public int submitPing() throws SpdyErrorException {
        int iSubmitPingN;
        sessionIsOpen();
        if (this.pptr4sessionNativePtr.enter()) {
            iSubmitPingN = submitPingN(this.sessionNativePtr);
            this.pptr4sessionNativePtr.exit();
        } else {
            iSubmitPingN = TnetStatusCode.EASY_REASON_CONN_NOT_EXISTS;
        }
        if (iSubmitPingN == 0) {
            return iSubmitPingN;
        }
        throw new SpdyErrorException("submitPing error: " + iSubmitPingN, iSubmitPingN);
    }

    public int submitRequest(SpdyRequest spdyRequest, SpdyDataProvider spdyDataProvider, Object obj, Spdycb spdycb) throws SpdyErrorException {
        SpdyStreamContext spdyStreamContext;
        String str;
        int iSubmitRequestN;
        if (spdyRequest == null || obj == null || spdyRequest.getAuthority() == null) {
            throw new SpdyErrorException("submitRequest error: -1102", TnetStatusCode.TNET_JNI_ERR_INVLID_PARAM);
        }
        sessionIsOpen();
        byte[] bArrDataproviderToByteArray = SpdyAgent.dataproviderToByteArray(spdyRequest, spdyDataProvider);
        if (bArrDataproviderToByteArray != null && bArrDataproviderToByteArray.length <= 0) {
            bArrDataproviderToByteArray = null;
        }
        byte[] bArr = bArrDataproviderToByteArray;
        boolean z2 = spdyDataProvider != null ? spdyDataProvider.finished : true;
        SpdyStreamContext spdyStreamContext2 = new SpdyStreamContext(obj, spdycb);
        int iPutSpdyStreamCtx = putSpdyStreamCtx(spdyStreamContext2);
        String[] strArrMapToByteArray = SpdyAgent.mapToByteArray(spdyRequest.getHeaders());
        spduLog.Logi("tnet-jni", "index=" + iPutSpdyStreamCtx + "  starttime=" + System.currentTimeMillis());
        if (this.pptr4sessionNativePtr.enter()) {
            spdyStreamContext = spdyStreamContext2;
            str = "tnet-jni";
            iSubmitRequestN = submitRequestN(this.sessionNativePtr, spdyRequest.getUrlPath(), (byte) spdyRequest.getPriority(), strArrMapToByteArray, bArr, z2, iPutSpdyStreamCtx, spdyRequest.getRequestTimeoutMs(), spdyRequest.getRequestRdTimeoutMs());
            this.pptr4sessionNativePtr.exit();
        } else {
            spdyStreamContext = spdyStreamContext2;
            str = "tnet-jni";
            iSubmitRequestN = TnetStatusCode.EASY_REASON_CONN_NOT_EXISTS;
        }
        spduLog.Logi(str, "index=" + iPutSpdyStreamCtx + "   calltime=" + System.currentTimeMillis());
        if (iSubmitRequestN >= 0) {
            spdyStreamContext.streamId = iSubmitRequestN;
            return iSubmitRequestN;
        }
        removeSpdyStream(iPutSpdyStreamCtx);
        throw new SpdyErrorException("submitRequest error: " + iSubmitRequestN, iSubmitRequestN);
    }
}
