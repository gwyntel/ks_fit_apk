package anet.channel.session;

import android.content.Context;
import android.text.TextUtils;
import anet.channel.AwcnConfig;
import anet.channel.Config;
import anet.channel.DataFrameCb;
import anet.channel.GlobalAppRuntimeInfo;
import anet.channel.IAuth;
import anet.channel.RequestCb;
import anet.channel.Session;
import anet.channel.appmonitor.AppMonitor;
import anet.channel.bytes.ByteArray;
import anet.channel.bytes.a;
import anet.channel.entity.ConnType;
import anet.channel.heartbeat.HeartbeatManager;
import anet.channel.heartbeat.IHeartbeat;
import anet.channel.request.Cancelable;
import anet.channel.request.Request;
import anet.channel.security.ISecurity;
import anet.channel.statist.ExceptionStatistic;
import anet.channel.statist.RequestStatistic;
import anet.channel.statist.SessionMonitor;
import anet.channel.statist.SessionStatistic;
import anet.channel.status.NetworkStatusHelper;
import anet.channel.strategy.ConnEvent;
import anet.channel.strategy.StrategyCenter;
import anet.channel.util.ALog;
import anet.channel.util.ErrorConstant;
import anet.channel.util.HttpHelper;
import anet.channel.util.Utils;
import anetwork.channel.util.RequestConstant;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import com.aliyun.alink.linksdk.alcs.coap.resources.LinkFormat;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.BaseMonitor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.android.spdy.RequestPriority;
import org.android.spdy.SessionCb;
import org.android.spdy.SessionInfo;
import org.android.spdy.SpdyAgent;
import org.android.spdy.SpdyByteArray;
import org.android.spdy.SpdyDataProvider;
import org.android.spdy.SpdyErrorException;
import org.android.spdy.SpdyRequest;
import org.android.spdy.SpdySession;
import org.android.spdy.SpdySessionKind;
import org.android.spdy.SpdyVersion;
import org.android.spdy.SuperviseConnectInfo;
import org.android.spdy.SuperviseData;

/* loaded from: classes2.dex */
public class TnetSpdySession extends Session implements SessionCb {
    protected long A;
    protected int B;
    protected DataFrameCb C;
    protected IHeartbeat D;
    protected IAuth E;
    protected String F;
    protected ISecurity G;
    private int H;
    private boolean I;

    /* renamed from: w, reason: collision with root package name */
    protected SpdyAgent f6897w;

    /* renamed from: x, reason: collision with root package name */
    protected SpdySession f6898x;

    /* renamed from: y, reason: collision with root package name */
    protected volatile boolean f6899y;

    /* renamed from: z, reason: collision with root package name */
    protected long f6900z;

    private class a extends anet.channel.session.a {

        /* renamed from: b, reason: collision with root package name */
        private Request f6902b;

        /* renamed from: c, reason: collision with root package name */
        private RequestCb f6903c;

        /* renamed from: d, reason: collision with root package name */
        private int f6904d = 0;

        /* renamed from: e, reason: collision with root package name */
        private long f6905e = 0;

        public a(Request request, RequestCb requestCb) {
            this.f6902b = request;
            this.f6903c = requestCb;
        }

        private void a(SuperviseData superviseData, int i2, String str) {
            try {
                this.f6902b.f6850a.rspEnd = System.currentTimeMillis();
                if (this.f6902b.f6850a.isDone.get()) {
                    return;
                }
                if (i2 > 0) {
                    this.f6902b.f6850a.ret = 1;
                }
                this.f6902b.f6850a.statusCode = i2;
                this.f6902b.f6850a.msg = str;
                if (superviseData != null) {
                    this.f6902b.f6850a.rspEnd = superviseData.responseEnd;
                    this.f6902b.f6850a.sendBeforeTime = superviseData.sendStart - superviseData.requestStart;
                    RequestStatistic requestStatistic = this.f6902b.f6850a;
                    requestStatistic.sendDataTime = superviseData.sendEnd - requestStatistic.sendStart;
                    this.f6902b.f6850a.firstDataTime = superviseData.responseStart - superviseData.sendEnd;
                    this.f6902b.f6850a.recDataTime = superviseData.responseEnd - superviseData.responseStart;
                    this.f6902b.f6850a.sendDataSize = superviseData.bodySize + superviseData.compressSize;
                    this.f6902b.f6850a.recDataSize = this.f6905e + superviseData.recvUncompressSize;
                    this.f6902b.f6850a.reqHeadInflateSize = superviseData.uncompressSize;
                    this.f6902b.f6850a.reqHeadDeflateSize = superviseData.compressSize;
                    this.f6902b.f6850a.reqBodyInflateSize = superviseData.bodySize;
                    this.f6902b.f6850a.reqBodyDeflateSize = superviseData.bodySize;
                    this.f6902b.f6850a.rspHeadDeflateSize = superviseData.recvCompressSize;
                    this.f6902b.f6850a.rspHeadInflateSize = superviseData.recvUncompressSize;
                    this.f6902b.f6850a.rspBodyDeflateSize = superviseData.recvBodySize;
                    this.f6902b.f6850a.rspBodyInflateSize = this.f6905e;
                    if (this.f6902b.f6850a.contentLength == 0) {
                        this.f6902b.f6850a.contentLength = superviseData.originContentLength;
                    }
                    SessionStatistic sessionStatistic = TnetSpdySession.this.f6633q;
                    sessionStatistic.recvSizeCount += superviseData.recvBodySize + superviseData.recvCompressSize;
                    sessionStatistic.sendSizeCount += superviseData.bodySize + superviseData.compressSize;
                }
            } catch (Exception unused) {
            }
        }

        @Override // anet.channel.session.a, org.android.spdy.Spdycb
        public void spdyDataChunkRecvCB(SpdySession spdySession, boolean z2, long j2, SpdyByteArray spdyByteArray, Object obj) {
            if (ALog.isPrintLog(1)) {
                ALog.d("awcn.TnetSpdySession", "spdyDataChunkRecvCB", this.f6902b.getSeq(), "len", Integer.valueOf(spdyByteArray.getDataLength()), "fin", Boolean.valueOf(z2));
            }
            this.f6905e += spdyByteArray.getDataLength();
            this.f6902b.f6850a.recDataSize += spdyByteArray.getDataLength();
            IHeartbeat iHeartbeat = TnetSpdySession.this.D;
            if (iHeartbeat != null) {
                iHeartbeat.reSchedule();
            }
            if (this.f6903c != null) {
                ByteArray byteArrayA = a.C0009a.f6699a.a(spdyByteArray.getByteArray(), spdyByteArray.getDataLength());
                spdyByteArray.recycle();
                this.f6903c.onDataReceive(byteArrayA, z2);
            }
            TnetSpdySession.this.handleCallbacks(32, null);
        }

        @Override // anet.channel.session.a, org.android.spdy.Spdycb
        public void spdyOnStreamResponse(SpdySession spdySession, long j2, Map<String, List<String>> map, Object obj) {
            this.f6902b.f6850a.firstDataTime = System.currentTimeMillis() - this.f6902b.f6850a.sendStart;
            this.f6904d = HttpHelper.parseStatusCode(map);
            TnetSpdySession.this.H = 0;
            ALog.i("awcn.TnetSpdySession", "", this.f6902b.getSeq(), HiAnalyticsConstant.HaKey.BI_KEY_RESULT, Integer.valueOf(this.f6904d));
            ALog.i("awcn.TnetSpdySession", "", this.f6902b.getSeq(), "response headers", map);
            RequestCb requestCb = this.f6903c;
            if (requestCb != null) {
                requestCb.onResponseCode(this.f6904d, HttpHelper.cloneMap(map));
            }
            TnetSpdySession.this.handleCallbacks(16, null);
            this.f6902b.f6850a.contentEncoding = HttpHelper.getSingleHeaderFieldByKey(map, "Content-Encoding");
            this.f6902b.f6850a.contentType = HttpHelper.getSingleHeaderFieldByKey(map, "Content-Type");
            this.f6902b.f6850a.contentLength = HttpHelper.parseContentLength(map);
            this.f6902b.f6850a.serverRT = HttpHelper.parseServerRT(map);
            TnetSpdySession.this.handleResponseCode(this.f6902b, this.f6904d);
            TnetSpdySession.this.handleResponseHeaders(this.f6902b, map);
            IHeartbeat iHeartbeat = TnetSpdySession.this.D;
            if (iHeartbeat != null) {
                iHeartbeat.reSchedule();
            }
        }

        @Override // anet.channel.session.a, org.android.spdy.Spdycb
        public void spdyStreamCloseCallback(SpdySession spdySession, long j2, int i2, Object obj, SuperviseData superviseData) {
            String msg;
            if (ALog.isPrintLog(1)) {
                ALog.d("awcn.TnetSpdySession", "spdyStreamCloseCallback", this.f6902b.getSeq(), "streamId", Long.valueOf(j2), "errorCode", Integer.valueOf(i2));
            }
            if (i2 != 0) {
                this.f6904d = ErrorConstant.ERROR_TNET_REQUEST_FAIL;
                msg = ErrorConstant.formatMsg(ErrorConstant.ERROR_TNET_REQUEST_FAIL, String.valueOf(i2));
                if (i2 != -2005) {
                    AppMonitor.getInstance().commitStat(new ExceptionStatistic(-300, msg, this.f6902b.f6850a, null));
                }
                ALog.e("awcn.TnetSpdySession", "spdyStreamCloseCallback error", this.f6902b.getSeq(), "session", TnetSpdySession.this.f6632p, "status code", Integer.valueOf(i2), "URL", this.f6902b.getHttpUrl().simpleUrlString());
            } else {
                msg = "SUCCESS";
            }
            this.f6902b.f6850a.tnetErrorCode = i2;
            a(superviseData, this.f6904d, msg);
            RequestCb requestCb = this.f6903c;
            if (requestCb != null) {
                requestCb.onFinish(this.f6904d, msg, this.f6902b.f6850a);
            }
            if (i2 == -2004) {
                if (!TnetSpdySession.this.f6899y) {
                    TnetSpdySession.this.ping(true);
                }
                if (TnetSpdySession.e(TnetSpdySession.this) >= 2) {
                    ConnEvent connEvent = new ConnEvent();
                    connEvent.isSuccess = false;
                    connEvent.isAccs = TnetSpdySession.this.I;
                    StrategyCenter.getInstance().notifyConnEvent(TnetSpdySession.this.f6620d, TnetSpdySession.this.f6627k, connEvent);
                    TnetSpdySession.this.close(true);
                }
            }
        }
    }

    public TnetSpdySession(Context context, anet.channel.entity.a aVar) {
        super(context, aVar);
        this.f6899y = false;
        this.A = 0L;
        this.H = 0;
        this.B = -1;
        this.C = null;
        this.D = null;
        this.E = null;
        this.F = null;
        this.G = null;
        this.I = false;
    }

    static /* synthetic */ int e(TnetSpdySession tnetSpdySession) {
        int i2 = tnetSpdySession.H + 1;
        tnetSpdySession.H = i2;
        return i2;
    }

    @Override // org.android.spdy.SessionCb
    public void bioPingRecvCallback(SpdySession spdySession, int i2) {
    }

    @Override // anet.channel.Session
    public void close() {
        ALog.e("awcn.TnetSpdySession", "force close!", this.f6632p, "session", this);
        notifyStatus(7, null);
        try {
            IHeartbeat iHeartbeat = this.D;
            if (iHeartbeat != null) {
                iHeartbeat.stop();
                this.D = null;
            }
            SpdySession spdySession = this.f6898x;
            if (spdySession != null) {
                spdySession.closeSession();
            }
        } catch (Exception unused) {
        }
    }

    @Override // anet.channel.Session
    public void connect() {
        int xquicCongControl;
        int i2 = this.f6630n;
        if (i2 == 1 || i2 == 0 || i2 == 4) {
            return;
        }
        try {
            if (this.f6897w == null) {
                c();
            }
            if (anet.channel.util.c.a() && anet.channel.strategy.utils.c.a(this.f6621e)) {
                try {
                    this.f6622f = anet.channel.util.c.a(this.f6621e);
                } catch (Exception unused) {
                }
            }
            String strValueOf = String.valueOf(System.currentTimeMillis());
            ALog.e("awcn.TnetSpdySession", BaseMonitor.ALARM_POINT_CONNECT, this.f6632p, "host", this.f6619c, "ip", this.f6622f, "port", Integer.valueOf(this.f6623g), "sessionId", strValueOf, "SpdyProtocol,", this.f6626j, "proxyIp,", this.f6624h, "proxyPort,", Integer.valueOf(this.f6625i));
            SessionInfo sessionInfo = new SessionInfo(this.f6622f, this.f6623g, this.f6619c + OpenAccountUIConstants.UNDER_LINE + this.F, this.f6624h, this.f6625i, strValueOf, this, this.f6626j.getTnetConType());
            sessionInfo.setConnectionTimeoutMs((int) (((float) this.f6634r) * Utils.getNetworkTimeFactor()));
            if (this.f6626j.isPublicKeyAuto() || this.f6626j.isH2S() || this.f6626j.isHTTP3()) {
                sessionInfo.setCertHost(this.f6629m ? this.f6621e : this.f6620d);
            } else {
                int i3 = this.B;
                if (i3 >= 0) {
                    sessionInfo.setPubKeySeqNum(i3);
                } else {
                    ConnType connType = this.f6626j;
                    ISecurity iSecurity = this.G;
                    int tnetPublicKey = connType.getTnetPublicKey(iSecurity != null ? iSecurity.isSecOff() : true);
                    this.B = tnetPublicKey;
                    sessionInfo.setPubKeySeqNum(tnetPublicKey);
                }
            }
            if (this.f6626j.isHTTP3() && (xquicCongControl = AwcnConfig.getXquicCongControl()) >= 0) {
                sessionInfo.setXquicCongControl(xquicCongControl);
            }
            SpdySession spdySessionCreateSession = this.f6897w.createSession(sessionInfo);
            this.f6898x = spdySessionCreateSession;
            if (spdySessionCreateSession.getRefCount() > 1) {
                ALog.e("awcn.TnetSpdySession", "get session ref count > 1!!!", this.f6632p, new Object[0]);
                notifyStatus(0, new anet.channel.entity.b(1));
                b();
                return;
            }
            notifyStatus(1, null);
            this.f6900z = System.currentTimeMillis();
            this.f6633q.isProxy = !TextUtils.isEmpty(this.f6624h) ? 1 : 0;
            SessionStatistic sessionStatistic = this.f6633q;
            sessionStatistic.isTunnel = RequestConstant.FALSE;
            sessionStatistic.isBackground = GlobalAppRuntimeInfo.isAppBackground();
            this.A = 0L;
        } catch (Throwable th) {
            notifyStatus(2, null);
            ALog.e("awcn.TnetSpdySession", "connect exception ", this.f6632p, th, new Object[0]);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // anet.channel.Session
    public Runnable getRecvTimeOutRunnable() {
        return new h(this);
    }

    @Override // org.android.spdy.SessionCb
    public byte[] getSSLMeta(SpdySession spdySession) {
        String domain = spdySession.getDomain();
        if (TextUtils.isEmpty(domain)) {
            ALog.i("awcn.TnetSpdySession", "get sslticket host is null", null, new Object[0]);
            return null;
        }
        try {
            ISecurity iSecurity = this.G;
            if (iSecurity == null) {
                return null;
            }
            return iSecurity.getBytes(this.f6617a, "accs_ssl_key2_" + domain);
        } catch (Throwable th) {
            ALog.e("awcn.TnetSpdySession", "getSSLMeta", null, th, new Object[0]);
            return null;
        }
    }

    public void initConfig(Config config) {
        if (config != null) {
            this.F = config.getAppkey();
            this.G = config.getSecurity();
        }
    }

    public void initSessionInfo(anet.channel.SessionInfo sessionInfo) {
        if (sessionInfo != null) {
            this.C = sessionInfo.dataFrameCb;
            this.E = sessionInfo.auth;
            if (sessionInfo.isKeepAlive) {
                this.f6633q.isKL = 1L;
                this.f6636t = true;
                IHeartbeat iHeartbeat = sessionInfo.heartbeat;
                this.D = iHeartbeat;
                boolean z2 = sessionInfo.isAccs;
                this.I = z2;
                if (iHeartbeat == null) {
                    if (!z2 || AwcnConfig.isAccsSessionCreateForbiddenInBg()) {
                        this.D = HeartbeatManager.getDefaultHeartbeat();
                    } else {
                        this.D = HeartbeatManager.getDefaultBackgroundAccsHeartbeat();
                    }
                }
            }
        }
        if (AwcnConfig.isIdleSessionCloseEnable() && this.D == null) {
            this.D = new anet.channel.heartbeat.c();
        }
    }

    @Override // anet.channel.Session
    public boolean isAvailable() {
        return this.f6630n == 4;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // anet.channel.Session
    public void onDisconnect() {
        this.f6899y = false;
    }

    @Override // anet.channel.Session
    public void ping(boolean z2) {
        ping(z2, this.f6635s);
    }

    @Override // org.android.spdy.SessionCb
    public int putSSLMeta(SpdySession spdySession, byte[] bArr) {
        String domain = spdySession.getDomain();
        if (TextUtils.isEmpty(domain)) {
            return -1;
        }
        try {
            ISecurity iSecurity = this.G;
            if (iSecurity == null) {
                return -1;
            }
            Context context = this.f6617a;
            StringBuilder sb = new StringBuilder();
            sb.append("accs_ssl_key2_");
            sb.append(domain);
            return iSecurity.saveBytes(context, sb.toString(), bArr) ? 0 : -1;
        } catch (Throwable th) {
            ALog.e("awcn.TnetSpdySession", "putSSLMeta", null, th, new Object[0]);
            return -1;
        }
    }

    @Override // anet.channel.Session
    public Cancelable request(Request request, RequestCb requestCb) {
        int i2;
        String str;
        SpdyRequest spdyRequest;
        anet.channel.request.c cVar = anet.channel.request.c.NULL;
        RequestStatistic requestStatistic = request != null ? request.f6850a : new RequestStatistic(this.f6620d, null);
        requestStatistic.setConnType(this.f6626j);
        if (requestStatistic.start == 0) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            requestStatistic.reqStart = jCurrentTimeMillis;
            requestStatistic.start = jCurrentTimeMillis;
        }
        requestStatistic.setIPAndPort(this.f6622f, this.f6623g);
        requestStatistic.ipRefer = this.f6627k.getIpSource();
        requestStatistic.ipType = this.f6627k.getIpType();
        requestStatistic.unit = this.f6628l;
        if (request == null || requestCb == null) {
            if (requestCb != null) {
                requestCb.onFinish(-102, ErrorConstant.getErrMsg(-102), requestStatistic);
            }
            return cVar;
        }
        try {
            if (this.f6898x == null || !((i2 = this.f6630n) == 0 || i2 == 4)) {
                requestCb.onFinish(-301, ErrorConstant.getErrMsg(-301), request.f6850a);
                return cVar;
            }
            if (this.f6629m) {
                request.setDnsOptimize(this.f6621e, this.f6623g);
            }
            request.setUrlScheme(this.f6626j.isSSL());
            URL url = request.getUrl();
            if (ALog.isPrintLog(2)) {
                ALog.i("awcn.TnetSpdySession", "", request.getSeq(), "request URL", url.toString());
                ALog.i("awcn.TnetSpdySession", "", request.getSeq(), "request Method", request.getMethod());
                ALog.i("awcn.TnetSpdySession", "", request.getSeq(), "request headers", request.getHeaders());
            }
            if (TextUtils.isEmpty(this.f6624h) || this.f6625i <= 0) {
                str = "";
                spdyRequest = new SpdyRequest(url, request.getMethod(), RequestPriority.DEFAULT_PRIORITY, -1, request.getConnectTimeout());
            } else {
                str = "";
                spdyRequest = new SpdyRequest(url, url.getHost(), url.getPort(), this.f6624h, this.f6625i, request.getMethod(), RequestPriority.DEFAULT_PRIORITY, -1, request.getConnectTimeout(), 0);
            }
            spdyRequest.setRequestRdTimeoutMs(request.getReadTimeout());
            Map<String, String> headers = request.getHeaders();
            if (headers.containsKey("Host")) {
                HashMap map = new HashMap(request.getHeaders());
                String strRemove = map.remove("Host");
                if (this.f6629m) {
                    strRemove = this.f6621e;
                }
                map.put(":host", strRemove);
                spdyRequest.addHeaders(map);
            } else {
                spdyRequest.addHeaders(headers);
                spdyRequest.addHeader(":host", this.f6629m ? this.f6621e : request.getHost());
            }
            SpdyDataProvider spdyDataProvider = new SpdyDataProvider(request.getBodyBytes());
            request.f6850a.sendStart = System.currentTimeMillis();
            RequestStatistic requestStatistic2 = request.f6850a;
            requestStatistic2.processTime = requestStatistic2.sendStart - request.f6850a.start;
            int iSubmitRequest = this.f6898x.submitRequest(spdyRequest, spdyDataProvider, this, new a(request, requestCb));
            if (ALog.isPrintLog(1)) {
                ALog.d("awcn.TnetSpdySession", str, request.getSeq(), "streamId", Integer.valueOf(iSubmitRequest));
            }
            anet.channel.request.c cVar2 = new anet.channel.request.c(this.f6898x, iSubmitRequest, request.getSeq());
            try {
                SessionStatistic sessionStatistic = this.f6633q;
                sessionStatistic.requestCount++;
                sessionStatistic.stdRCount++;
                this.f6900z = System.currentTimeMillis();
                IHeartbeat iHeartbeat = this.D;
                if (iHeartbeat != null) {
                    iHeartbeat.reSchedule();
                }
                return cVar2;
            } catch (SpdyErrorException e2) {
                e = e2;
                cVar = cVar2;
                if (e.SpdyErrorGetCode() == -1104 || e.SpdyErrorGetCode() == -1103) {
                    ALog.e("awcn.TnetSpdySession", "Send request on closed session!!!", this.f6632p, new Object[0]);
                    notifyStatus(6, new anet.channel.entity.b(2));
                }
                requestCb.onFinish(-300, ErrorConstant.formatMsg(-300, String.valueOf(e.SpdyErrorGetCode())), requestStatistic);
                return cVar;
            } catch (Exception unused) {
                cVar = cVar2;
                requestCb.onFinish(-101, ErrorConstant.getErrMsg(-101), requestStatistic);
                return cVar;
            }
        } catch (SpdyErrorException e3) {
            e = e3;
        } catch (Exception unused2) {
        }
    }

    @Override // anet.channel.Session
    public void sendCustomFrame(int i2, byte[] bArr, int i3) {
        SpdySession spdySession;
        try {
            if (this.C == null) {
                return;
            }
            ALog.e("awcn.TnetSpdySession", "sendCustomFrame", this.f6632p, Constants.KEY_DATA_ID, Integer.valueOf(i2), "type", Integer.valueOf(i3));
            if (this.f6630n != 4 || (spdySession = this.f6898x) == null) {
                ALog.e("awcn.TnetSpdySession", "sendCustomFrame", this.f6632p, "sendCustomFrame con invalid mStatus:" + this.f6630n);
                a(i2, -301, true, "session invalid");
                return;
            }
            if (bArr != null && bArr.length > 16384) {
                a(i2, -303, false, null);
                return;
            }
            spdySession.sendCustomControlFrame(i2, i3, 0, bArr == null ? 0 : bArr.length, bArr);
            SessionStatistic sessionStatistic = this.f6633q;
            sessionStatistic.requestCount++;
            sessionStatistic.cfRCount++;
            this.f6900z = System.currentTimeMillis();
            IHeartbeat iHeartbeat = this.D;
            if (iHeartbeat != null) {
                iHeartbeat.reSchedule();
            }
        } catch (SpdyErrorException e2) {
            ALog.e("awcn.TnetSpdySession", "sendCustomFrame error", this.f6632p, e2, new Object[0]);
            a(i2, -300, true, "SpdyErrorException: " + e2.toString());
        } catch (Exception e3) {
            ALog.e("awcn.TnetSpdySession", "sendCustomFrame error", this.f6632p, e3, new Object[0]);
            a(i2, -101, true, e3.toString());
        }
    }

    public void setTnetPublicKey(int i2) {
        this.B = i2;
    }

    @Override // org.android.spdy.SessionCb
    public void spdyCustomControlFrameFailCallback(SpdySession spdySession, Object obj, int i2, int i3) {
        ALog.e("awcn.TnetSpdySession", "spdyCustomControlFrameFailCallback", this.f6632p, Constants.KEY_DATA_ID, Integer.valueOf(i2));
        a(i2, i3, true, "tnet error");
    }

    @Override // org.android.spdy.SessionCb
    public void spdyCustomControlFrameRecvCallback(SpdySession spdySession, Object obj, int i2, int i3, int i4, int i5, byte[] bArr) {
        ALog.e("awcn.TnetSpdySession", "[spdyCustomControlFrameRecvCallback]", this.f6632p, "len", Integer.valueOf(i5), "frameCb", this.C);
        if (ALog.isPrintLog(1) && i5 < 512) {
            String str = "";
            for (byte b2 : bArr) {
                str = str + Integer.toHexString(b2 & 255) + " ";
            }
            ALog.e("awcn.TnetSpdySession", null, this.f6632p, "str", str);
        }
        DataFrameCb dataFrameCb = this.C;
        if (dataFrameCb != null) {
            dataFrameCb.onDataReceive(this, bArr, i2, i3);
        } else {
            ALog.e("awcn.TnetSpdySession", "AccsFrameCb is null", this.f6632p, new Object[0]);
            AppMonitor.getInstance().commitStat(new ExceptionStatistic(-105, null, LinkFormat.RESOURCE_TYPE));
        }
        this.f6633q.inceptCount++;
        IHeartbeat iHeartbeat = this.D;
        if (iHeartbeat != null) {
            iHeartbeat.reSchedule();
        }
    }

    @Override // org.android.spdy.SessionCb
    public void spdyPingRecvCallback(SpdySession spdySession, long j2, Object obj) {
        if (ALog.isPrintLog(2)) {
            ALog.i("awcn.TnetSpdySession", "ping receive", this.f6632p, "Host", this.f6619c, "id", Long.valueOf(j2));
        }
        if (j2 < 0) {
            return;
        }
        this.f6899y = false;
        this.H = 0;
        IHeartbeat iHeartbeat = this.D;
        if (iHeartbeat != null) {
            iHeartbeat.reSchedule();
        }
        handleCallbacks(128, null);
    }

    @Override // org.android.spdy.SessionCb
    public void spdySessionCloseCallback(SpdySession spdySession, Object obj, SuperviseConnectInfo superviseConnectInfo, int i2) {
        ALog.e("awcn.TnetSpdySession", "spdySessionCloseCallback", this.f6632p, " errorCode:", Integer.valueOf(i2));
        IHeartbeat iHeartbeat = this.D;
        if (iHeartbeat != null) {
            iHeartbeat.stop();
            this.D = null;
        }
        if (spdySession != null) {
            try {
                spdySession.cleanUp();
            } catch (Exception e2) {
                ALog.e("awcn.TnetSpdySession", "session clean up failed!", null, e2, new Object[0]);
            }
        }
        if (i2 == -3516) {
            ConnEvent connEvent = new ConnEvent();
            connEvent.isSuccess = false;
            StrategyCenter.getInstance().notifyConnEvent(this.f6620d, this.f6627k, connEvent);
        }
        notifyStatus(6, new anet.channel.entity.b(2));
        if (superviseConnectInfo != null) {
            SessionStatistic sessionStatistic = this.f6633q;
            sessionStatistic.requestCount = superviseConnectInfo.reused_counter;
            sessionStatistic.liveTime = superviseConnectInfo.keepalive_period_second;
            try {
                if (this.f6626j.isHTTP3()) {
                    if (spdySession != null) {
                        ALog.e("awcn.TnetSpdySession", "[HTTP3 spdySessionCloseCallback]", this.f6632p, "connectInfo", spdySession.getConnectInfoOnDisConnected());
                    }
                    this.f6633q.xqc0RttStatus = superviseConnectInfo.xqc0RttStatus;
                    this.f6633q.retransmissionRate = superviseConnectInfo.retransmissionRate;
                    this.f6633q.lossRate = superviseConnectInfo.lossRate;
                    this.f6633q.tlpCount = superviseConnectInfo.tlpCount;
                    this.f6633q.rtoCount = superviseConnectInfo.rtoCount;
                    this.f6633q.srtt = superviseConnectInfo.srtt;
                }
            } catch (Exception unused) {
            }
        }
        SessionStatistic sessionStatistic2 = this.f6633q;
        if (sessionStatistic2.errorCode == 0) {
            sessionStatistic2.errorCode = i2;
        }
        sessionStatistic2.lastPingInterval = (int) (System.currentTimeMillis() - this.f6900z);
        AppMonitor.getInstance().commitStat(this.f6633q);
        if (anet.channel.strategy.utils.c.b(this.f6633q.ip)) {
            AppMonitor.getInstance().commitStat(new SessionMonitor(this.f6633q));
        }
        AppMonitor.getInstance().commitAlarm(this.f6633q.getAlarmObject());
    }

    @Override // org.android.spdy.SessionCb
    public void spdySessionConnectCB(SpdySession spdySession, SuperviseConnectInfo superviseConnectInfo) {
        SessionStatistic sessionStatistic = this.f6633q;
        sessionStatistic.connectionTime = superviseConnectInfo.connectTime;
        sessionStatistic.sslTime = superviseConnectInfo.handshakeTime;
        sessionStatistic.sslCalTime = superviseConnectInfo.doHandshakeTime;
        sessionStatistic.netType = NetworkStatusHelper.getNetworkSubType();
        this.A = System.currentTimeMillis();
        notifyStatus(0, new anet.channel.entity.b(1));
        b();
        ALog.e("awcn.TnetSpdySession", "spdySessionConnectCB connect", this.f6632p, "connectTime", Integer.valueOf(superviseConnectInfo.connectTime), "sslTime", Integer.valueOf(superviseConnectInfo.handshakeTime));
        if (this.f6626j.isHTTP3()) {
            this.f6633q.scid = superviseConnectInfo.scid;
            this.f6633q.dcid = superviseConnectInfo.dcid;
            this.f6633q.congControlKind = superviseConnectInfo.congControlKind;
            ALog.e("awcn.TnetSpdySession", "[HTTP3 spdySessionConnectCB]", this.f6632p, "connectInfo", spdySession.getConnectInfoOnConnected());
        }
    }

    @Override // org.android.spdy.SessionCb
    public void spdySessionFailedError(SpdySession spdySession, int i2, Object obj) {
        if (spdySession != null) {
            try {
                spdySession.cleanUp();
            } catch (Exception e2) {
                ALog.e("awcn.TnetSpdySession", "[spdySessionFailedError]session clean up failed!", null, e2, new Object[0]);
            }
        }
        notifyStatus(2, new anet.channel.entity.b(256, i2, "tnet connect fail"));
        ALog.e("awcn.TnetSpdySession", null, this.f6632p, " errorId:", Integer.valueOf(i2));
        SessionStatistic sessionStatistic = this.f6633q;
        sessionStatistic.errorCode = i2;
        sessionStatistic.ret = 0;
        sessionStatistic.netType = NetworkStatusHelper.getNetworkSubType();
        AppMonitor.getInstance().commitStat(this.f6633q);
        if (anet.channel.strategy.utils.c.b(this.f6633q.ip)) {
            AppMonitor.getInstance().commitStat(new SessionMonitor(this.f6633q));
        }
        AppMonitor.getInstance().commitAlarm(this.f6633q.getAlarmObject());
    }

    @Override // anet.channel.Session
    public void ping(boolean z2, int i2) {
        if (ALog.isPrintLog(1)) {
            ALog.d("awcn.TnetSpdySession", "ping", this.f6632p, "host", this.f6619c, "thread", Thread.currentThread().getName());
        }
        if (z2) {
            try {
                if (this.f6898x == null) {
                    SessionStatistic sessionStatistic = this.f6633q;
                    if (sessionStatistic != null) {
                        sessionStatistic.closeReason = "session null";
                    }
                    ALog.e("awcn.TnetSpdySession", this.f6619c + " session null", this.f6632p, new Object[0]);
                    close();
                    return;
                }
                int i3 = this.f6630n;
                if (i3 == 0 || i3 == 4) {
                    handleCallbacks(64, null);
                    if (this.f6899y) {
                        return;
                    }
                    this.f6899y = true;
                    this.f6633q.ppkgCount++;
                    this.f6898x.submitPing();
                    if (ALog.isPrintLog(1)) {
                        ALog.d("awcn.TnetSpdySession", this.f6619c + " submit ping ms:" + (System.currentTimeMillis() - this.f6900z) + " force:" + z2, this.f6632p, new Object[0]);
                    }
                    setPingTimeout(i2);
                    this.f6900z = System.currentTimeMillis();
                    IHeartbeat iHeartbeat = this.D;
                    if (iHeartbeat != null) {
                        iHeartbeat.reSchedule();
                    }
                }
            } catch (SpdyErrorException e2) {
                if (e2.SpdyErrorGetCode() == -1104 || e2.SpdyErrorGetCode() == -1103) {
                    ALog.e("awcn.TnetSpdySession", "Send request on closed session!!!", this.f6632p, new Object[0]);
                    notifyStatus(6, new anet.channel.entity.b(2));
                }
                ALog.e("awcn.TnetSpdySession", "ping", this.f6632p, e2, new Object[0]);
            } catch (Exception e3) {
                ALog.e("awcn.TnetSpdySession", "ping", this.f6632p, e3, new Object[0]);
            }
        }
    }

    private void c() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        SpdyAgent.enableDebug = false;
        this.f6897w = SpdyAgent.getInstance(this.f6617a, SpdyVersion.SPDY3, SpdySessionKind.NONE_SESSION);
        ISecurity iSecurity = this.G;
        if (iSecurity != null && !iSecurity.isSecOff()) {
            this.f6897w.setAccsSslCallback(new j(this));
        }
        if (AwcnConfig.isTnetHeaderCacheEnable()) {
            return;
        }
        try {
            this.f6897w.getClass().getDeclaredMethod("disableHeaderCache", null).invoke(this.f6897w, null);
            ALog.i("awcn.TnetSpdySession", "tnet disableHeaderCache", null, new Object[0]);
        } catch (Exception e2) {
            ALog.e("awcn.TnetSpdySession", "tnet disableHeaderCache", null, e2, new Object[0]);
        }
    }

    protected void b() {
        IAuth iAuth = this.E;
        if (iAuth != null) {
            iAuth.auth(this, new i(this));
            return;
        }
        notifyStatus(4, null);
        this.f6633q.ret = 1;
        IHeartbeat iHeartbeat = this.D;
        if (iHeartbeat != null) {
            iHeartbeat.start(this);
        }
    }

    private void a(int i2, int i3, boolean z2, String str) {
        DataFrameCb dataFrameCb = this.C;
        if (dataFrameCb != null) {
            dataFrameCb.onException(i2, i3, z2, str);
        }
    }
}
