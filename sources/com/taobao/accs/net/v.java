package com.taobao.accs.net;

import android.content.Context;
import android.text.TextUtils;
import androidx.media3.extractor.Ac3Util;
import anet.channel.GlobalAppRuntimeInfo;
import anet.channel.appmonitor.AppMonitor;
import anet.channel.strategy.IConnStrategy;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import com.taobao.accs.AccsClientConfig;
import com.taobao.accs.ErrorCode;
import com.taobao.accs.common.Constants;
import com.taobao.accs.common.ThreadPoolExecutorFactory;
import com.taobao.accs.data.Message;
import com.taobao.accs.ut.monitor.SessionMonitor;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.BaseMonitor;
import com.taobao.accs.utl.UTMini;
import com.taobao.accs.utl.UtilityImpl;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.android.spdy.RequestPriority;
import org.android.spdy.SessionCb;
import org.android.spdy.SessionInfo;
import org.android.spdy.SpdyAgent;
import org.android.spdy.SpdyByteArray;
import org.android.spdy.SpdyDataProvider;
import org.android.spdy.SpdyRequest;
import org.android.spdy.SpdySession;
import org.android.spdy.SpdySessionKind;
import org.android.spdy.SpdyVersion;
import org.android.spdy.Spdycb;
import org.android.spdy.SuperviseConnectInfo;
import org.android.spdy.SuperviseData;

/* loaded from: classes4.dex */
public class v extends com.taobao.accs.net.a implements SessionCb, Spdycb {
    private SpdySession A;
    private Object B;
    private long C;
    private long D;
    private long E;
    private long F;
    private int G;
    private String H;
    private SessionMonitor I;
    private com.taobao.accs.ut.a.c J;
    private boolean K;
    private String L;
    private boolean M;
    private h N;
    private String O;

    /* renamed from: o, reason: collision with root package name */
    protected ScheduledFuture<?> f20263o;

    /* renamed from: p, reason: collision with root package name */
    protected String f20264p;

    /* renamed from: q, reason: collision with root package name */
    protected int f20265q;

    /* renamed from: r, reason: collision with root package name */
    protected String f20266r;

    /* renamed from: s, reason: collision with root package name */
    protected int f20267s;

    /* renamed from: t, reason: collision with root package name */
    private int f20268t;

    /* renamed from: u, reason: collision with root package name */
    private LinkedList<Message> f20269u;

    /* renamed from: v, reason: collision with root package name */
    private a f20270v;

    /* renamed from: w, reason: collision with root package name */
    private boolean f20271w;

    /* renamed from: x, reason: collision with root package name */
    private String f20272x;

    /* renamed from: y, reason: collision with root package name */
    private String f20273y;

    /* renamed from: z, reason: collision with root package name */
    private SpdyAgent f20274z;

    private class a extends Thread {

        /* renamed from: a, reason: collision with root package name */
        public int f20275a;

        /* renamed from: b, reason: collision with root package name */
        long f20276b;

        /* renamed from: d, reason: collision with root package name */
        private final String f20278d;

        public a(String str) {
            super(str);
            this.f20278d = getName();
            this.f20275a = 0;
        }

        private void a(boolean z2) throws InterruptedException {
            if (v.this.f20268t == 1) {
                if (v.this.f20268t != 1 || System.currentTimeMillis() - this.f20276b <= 5000) {
                    return;
                }
                this.f20275a = 0;
                return;
            }
            ALog.d(v.this.d(), "tryConnect", "force", Boolean.valueOf(z2));
            if (!UtilityImpl.i(v.this.f20194d)) {
                ALog.e(this.f20278d, "Network not available", new Object[0]);
                return;
            }
            if (z2) {
                this.f20275a = 0;
            }
            ALog.i(this.f20278d, "tryConnect", "force", Boolean.valueOf(z2), "failTimes", Integer.valueOf(this.f20275a));
            if (v.this.f20268t != 1 && this.f20275a >= 4) {
                v.this.K = true;
                ALog.e(this.f20278d, "tryConnect fail", "maxTimes", 4);
                return;
            }
            if (v.this.f20268t != 1) {
                if (v.this.f20193c == 1 && this.f20275a == 0) {
                    ALog.i(this.f20278d, "tryConnect in app, no sleep", new Object[0]);
                } else {
                    ALog.i(this.f20278d, "tryConnect, need sleep", new Object[0]);
                    try {
                        Thread.sleep(5000L);
                    } catch (InterruptedException e2) {
                        e2.printStackTrace();
                    }
                }
                v.this.L = "";
                if (this.f20275a == 3) {
                    v.this.N.b(v.this.p());
                }
                v.this.d((String) null);
                v.this.I.setRetryTimes(this.f20275a);
                if (v.this.f20268t == 1) {
                    this.f20276b = System.currentTimeMillis();
                    return;
                }
                this.f20275a++;
                ALog.e(this.f20278d, "try connect fail, ready for reconnect", new Object[0]);
                a(false);
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:19:0x0069 A[Catch: all -> 0x004a, TryCatch #4 {, blocks: (B:7:0x002b, B:9:0x0037, B:17:0x0054, B:19:0x0069, B:21:0x007b, B:23:0x0083, B:14:0x004e, B:15:0x0051), top: B:219:0x002b, inners: #3 }] */
        @Override // java.lang.Thread, java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void run() {
            /*
                Method dump skipped, instructions count: 1324
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.net.v.a.run():void");
        }
    }

    public v(Context context, int i2, String str) {
        super(context, i2, str);
        this.f20268t = 3;
        this.f20269u = new LinkedList<>();
        this.f20271w = true;
        this.f20274z = null;
        this.A = null;
        this.B = new Object();
        this.G = -1;
        this.H = null;
        this.K = false;
        this.L = "";
        this.M = false;
        this.N = new h(p());
        u();
    }

    private int r() {
        boolean zL = l();
        if (AccsClientConfig.mEnv == 2) {
            return 0;
        }
        int channelPubKey = this.f20199i.getChannelPubKey();
        if (channelPubKey <= 0) {
            return zL ? 4 : 3;
        }
        ALog.i(d(), "getPublicKeyType use custom pub key", "pubKey", Integer.valueOf(channelPubKey));
        return channelPubKey;
    }

    private void s() {
        if (this.A == null) {
            d(3);
            return;
        }
        try {
            String strEncode = URLEncoder.encode(UtilityImpl.j(this.f20194d));
            String strA = UtilityImpl.a(i(), this.f20199i.getAppSecret(), UtilityImpl.j(this.f20194d));
            String strC = c(this.f20272x);
            ALog.e(d(), "auth", "url", strC);
            this.f20273y = strC;
            if (!a(strEncode, i(), strA)) {
                ALog.e(d(), "auth param error!", new Object[0]);
                e(-6);
            } else {
                SpdyRequest spdyRequest = new SpdyRequest(new URL(strC), "GET", RequestPriority.DEFAULT_PRIORITY, Ac3Util.AC3_MAX_RATE_BYTES_PER_SECOND, 40000);
                spdyRequest.setDomain(p());
                this.A.submitRequest(spdyRequest, new SpdyDataProvider((byte[]) null), p(), this);
            }
        } catch (Throwable th) {
            ALog.e(d(), "auth exception ", th, new Object[0]);
            e(-7);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void t() {
        if (this.f20193c == 1) {
            return;
        }
        this.C = System.currentTimeMillis();
        this.D = System.nanoTime();
        g.a(this.f20194d).a();
    }

    private void u() {
        try {
            SpdyAgent.enableDebug = ALog.isPrintLog();
            this.f20274z = SpdyAgent.getInstance(this.f20194d, SpdyVersion.SPDY3, SpdySessionKind.NONE_SESSION);
            if (SpdyAgent.checkLoadSucc()) {
                com.taobao.accs.utl.q.a();
            } else {
                ALog.e(d(), "initClient", new Object[0]);
                this.f20274z = null;
                com.taobao.accs.utl.q.b();
            }
        } catch (Throwable th) {
            ALog.e(d(), "initClient", th, new Object[0]);
        }
    }

    @Override // org.android.spdy.SessionCb
    public void bioPingRecvCallback(SpdySession spdySession, int i2) {
        ALog.w(d(), "bioPingRecvCallback uniId:" + i2, new Object[0]);
    }

    @Override // org.android.spdy.SessionCb
    public byte[] getSSLMeta(SpdySession spdySession) {
        spdySession.getDomain();
        return UtilityImpl.c();
    }

    @Override // com.taobao.accs.net.a
    protected boolean h() {
        return false;
    }

    public void o() {
        ALog.e(d(), " force close!", new Object[0]);
        try {
            this.A.closeSession();
            this.I.setCloseType(1);
        } catch (Exception unused) {
        }
        d(3);
    }

    public String p() {
        String channelHost = this.f20199i.getChannelHost();
        ALog.i(d(), "getChannelHost", "host", channelHost);
        return channelHost == null ? "" : channelHost;
    }

    @Override // org.android.spdy.SessionCb
    public int putSSLMeta(SpdySession spdySession, byte[] bArr) {
        spdySession.getDomain();
        return UtilityImpl.b();
    }

    public boolean q() {
        return this.f20271w;
    }

    @Override // org.android.spdy.SessionCb
    public void spdyCustomControlFrameFailCallback(SpdySession spdySession, Object obj, int i2, int i3) {
        b(i2);
    }

    @Override // org.android.spdy.SessionCb
    public void spdyCustomControlFrameRecvCallback(SpdySession spdySession, Object obj, int i2, int i3, int i4, int i5, byte[] bArr) {
        t();
        ALog.e(d(), "onFrame", "type", Integer.valueOf(i3), "len", Integer.valueOf(bArr.length));
        StringBuilder sb = new StringBuilder();
        if (ALog.isPrintLog(ALog.Level.D) && bArr.length < 512) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            for (byte b2 : bArr) {
                sb.append(Integer.toHexString(b2 & 255));
                sb.append(" ");
            }
            ALog.d(d(), ((Object) sb) + " log time:" + (System.currentTimeMillis() - jCurrentTimeMillis), new Object[0]);
        }
        if (i3 == 200) {
            try {
                long jCurrentTimeMillis2 = System.currentTimeMillis();
                this.f20195e.a(bArr);
                com.taobao.accs.ut.a.d dVarG = this.f20195e.g();
                if (dVarG != null) {
                    dVarG.f20311c = String.valueOf(jCurrentTimeMillis2);
                    dVarG.f20315g = this.f20193c == 0 ? "service" : "inapp";
                    dVarG.a();
                }
            } catch (Throwable th) {
                ALog.e(d(), "onDataReceive ", th, new Object[0]);
                UTMini.getInstance().commitEvent(66001, "SERVICE_DATA_RECEIVE", UtilityImpl.a(th));
            }
            ALog.d(d(), "try handle msg", new Object[0]);
            g();
        } else {
            ALog.e(d(), "drop frame", "len", Integer.valueOf(bArr.length));
        }
        ALog.d(d(), "spdyCustomControlFrameRecvCallback", new Object[0]);
    }

    @Override // org.android.spdy.Spdycb
    public void spdyDataChunkRecvCB(SpdySession spdySession, boolean z2, long j2, SpdyByteArray spdyByteArray, Object obj) {
        ALog.d(d(), "spdyDataChunkRecvCB", new Object[0]);
    }

    @Override // org.android.spdy.Spdycb
    public void spdyDataRecvCallback(SpdySession spdySession, boolean z2, long j2, int i2, Object obj) {
        ALog.d(d(), "spdyDataRecvCallback", new Object[0]);
    }

    @Override // org.android.spdy.Spdycb
    public void spdyDataSendCallback(SpdySession spdySession, boolean z2, long j2, int i2, Object obj) {
        ALog.d(d(), "spdyDataSendCallback", new Object[0]);
    }

    @Override // org.android.spdy.Spdycb
    public void spdyOnStreamResponse(SpdySession spdySession, long j2, Map<String, List<String>> map, Object obj) throws NumberFormatException {
        this.C = System.currentTimeMillis();
        this.D = System.nanoTime();
        try {
            Map<String, String> mapA = UtilityImpl.a(map);
            ALog.d("SilenceConn_", "spdyOnStreamResponse", "header", map);
            int i2 = Integer.parseInt(mapA.get(":status"));
            ALog.e(d(), "spdyOnStreamResponse", "httpStatusCode", Integer.valueOf(i2));
            if (i2 == 200) {
                d(1);
                String str = mapA.get("x-at");
                if (!TextUtils.isEmpty(str)) {
                    this.f20201k = str;
                }
                SessionMonitor sessionMonitor = this.I;
                sessionMonitor.auth_time = sessionMonitor.connection_stop_date > 0 ? System.currentTimeMillis() - this.I.connection_stop_date : 0L;
                String str2 = this.f20193c == 0 ? "service" : "inapp";
                UTMini.getInstance().commitEvent(66001, "CONNECTED 200 " + str2, (Object) this.f20273y, (Object) this.L, (Object) 221, "0");
                com.taobao.accs.utl.k.a("accs", "auth", "");
            } else {
                e(i2);
            }
        } catch (Exception e2) {
            ALog.e(d(), e2.toString(), new Object[0]);
            o();
            this.I.setCloseReason("exception");
        }
        ALog.d(d(), "spdyOnStreamResponse", new Object[0]);
    }

    @Override // org.android.spdy.SessionCb
    public void spdyPingRecvCallback(SpdySession spdySession, long j2, Object obj) {
        ALog.d(d(), "spdyPingRecvCallback uniId:" + j2, new Object[0]);
        if (j2 < 0) {
            return;
        }
        this.f20195e.b();
        g.a(this.f20194d).e();
        g.a(this.f20194d).a();
        this.I.onPingCBReceive();
        if (this.I.ping_rec_times % 2 == 0) {
            UtilityImpl.a(this.f20194d, Constants.SP_KEY_SERVICE_END, System.currentTimeMillis());
        }
    }

    @Override // org.android.spdy.Spdycb
    public void spdyRequestRecvCallback(SpdySession spdySession, long j2, Object obj) {
        ALog.d(d(), "spdyRequestRecvCallback", new Object[0]);
    }

    @Override // org.android.spdy.SessionCb
    public void spdySessionCloseCallback(SpdySession spdySession, Object obj, SuperviseConnectInfo superviseConnectInfo, int i2) {
        ALog.e(d(), "spdySessionCloseCallback", "errorCode", Integer.valueOf(i2));
        if (spdySession != null) {
            try {
                spdySession.cleanUp();
            } catch (Exception e2) {
                ALog.e(d(), "session cleanUp has exception: " + e2, new Object[0]);
            }
        }
        d(3);
        this.I.onCloseConnect();
        if (this.I.getConCloseDate() > 0 && this.I.getConStopDate() > 0) {
            this.I.getConCloseDate();
            this.I.getConStopDate();
        }
        this.I.setCloseReason(this.I.getCloseReason() + "tnet error:" + i2);
        if (superviseConnectInfo != null) {
            this.I.live_time = superviseConnectInfo.keepalive_period_second;
        }
        AppMonitor.getInstance().commitStat(this.I);
        for (Message message : this.f20195e.e()) {
            if (message.getNetPermanceMonitor() != null) {
                message.getNetPermanceMonitor().setRet(false);
                message.getNetPermanceMonitor().setFailReason("session close");
                AppMonitor.getInstance().commitStat(message.getNetPermanceMonitor());
            }
        }
        String str = this.f20193c == 0 ? "service" : "inapp";
        ALog.d(d(), "spdySessionCloseCallback, conKeepTime:" + this.I.live_time + " connectType:" + str, new Object[0]);
        UTMini uTMini = UTMini.getInstance();
        StringBuilder sb = new StringBuilder();
        sb.append("DISCONNECT CLOSE ");
        sb.append(str);
        uTMini.commitEvent(66001, sb.toString(), (Object) Integer.valueOf(i2), (Object) Long.valueOf(this.I.live_time), (Object) 221, this.f20273y, this.L);
    }

    @Override // org.android.spdy.SessionCb
    public void spdySessionConnectCB(SpdySession spdySession, SuperviseConnectInfo superviseConnectInfo) {
        this.G = superviseConnectInfo.connectTime;
        int i2 = superviseConnectInfo.handshakeTime;
        ALog.e(d(), "spdySessionConnectCB", "sessionConnectInterval", Integer.valueOf(this.G), "sslTime", Integer.valueOf(i2), "reuse", Integer.valueOf(superviseConnectInfo.sessionTicketReused));
        s();
        this.I.setRet(true);
        this.I.onConnectStop();
        SessionMonitor sessionMonitor = this.I;
        sessionMonitor.tcp_time = this.G;
        sessionMonitor.ssl_time = i2;
        String str = this.f20193c == 0 ? "service" : "inapp";
        UTMini.getInstance().commitEvent(66001, "CONNECTED " + str + " " + superviseConnectInfo.sessionTicketReused, (Object) String.valueOf(this.G), (Object) String.valueOf(i2), (Object) 221, String.valueOf(superviseConnectInfo.sessionTicketReused), this.f20273y, this.L);
        com.taobao.accs.utl.k.a("accs", BaseMonitor.ALARM_POINT_CONNECT, "");
    }

    @Override // org.android.spdy.SessionCb
    public void spdySessionFailedError(SpdySession spdySession, int i2, Object obj) {
        if (spdySession != null) {
            try {
                spdySession.cleanUp();
            } catch (Exception e2) {
                ALog.e(d(), "session cleanUp has exception: " + e2, new Object[0]);
            }
        }
        a aVar = this.f20270v;
        int i3 = aVar != null ? aVar.f20275a : 0;
        ALog.e(d(), "spdySessionFailedError", "retryTimes", Integer.valueOf(i3), "errorId", Integer.valueOf(i2));
        this.K = false;
        this.M = true;
        d(3);
        this.I.setFailReason(i2);
        this.I.onConnectStop();
        String str = this.f20193c == 0 ? "service" : "inapp";
        UTMini.getInstance().commitEvent(66001, "DISCONNECT " + str, (Object) Integer.valueOf(i2), (Object) Integer.valueOf(i3), (Object) 221, this.f20273y, this.L);
        com.taobao.accs.utl.k.a("accs", BaseMonitor.ALARM_POINT_CONNECT, "retrytimes:" + i3, i2 + "", "");
    }

    @Override // org.android.spdy.Spdycb
    public void spdyStreamCloseCallback(SpdySession spdySession, long j2, int i2, Object obj, SuperviseData superviseData) {
        ALog.d(d(), "spdyStreamCloseCallback", new Object[0]);
        if (i2 != 0) {
            ALog.e(d(), "spdyStreamCloseCallback", HiAnalyticsConstant.HaKey.BI_KEY_RESULT, Integer.valueOf(i2));
            e(i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d(String str) {
        SessionInfo sessionInfo;
        int i2 = this.f20268t;
        if (i2 == 2 || i2 == 1) {
            return;
        }
        if (this.N == null) {
            this.N = new h(p());
        }
        List<IConnStrategy> listA = this.N.a(p());
        if (listA == null || listA.size() <= 0) {
            if (str != null) {
                this.f20264p = str;
            } else {
                this.f20264p = p();
            }
            this.f20265q = System.currentTimeMillis() % 2 == 0 ? 80 : Constants.PORT;
            com.taobao.accs.utl.k.a("accs", BaseMonitor.COUNT_POINT_DNS, "localdns", 0.0d);
            ALog.i(d(), "connect get ip from amdc fail!!", new Object[0]);
        } else {
            for (IConnStrategy iConnStrategy : listA) {
                if (iConnStrategy != null) {
                    ALog.e(d(), BaseMonitor.ALARM_POINT_CONNECT, "ip", iConnStrategy.getIp(), "port", Integer.valueOf(iConnStrategy.getPort()));
                }
            }
            if (this.M) {
                this.N.b();
                this.M = false;
            }
            IConnStrategy iConnStrategyA = this.N.a();
            this.f20264p = iConnStrategyA == null ? p() : iConnStrategyA.getIp();
            this.f20265q = iConnStrategyA == null ? Constants.PORT : iConnStrategyA.getPort();
            com.taobao.accs.utl.k.a("accs", BaseMonitor.COUNT_POINT_DNS, "httpdns", 0.0d);
            ALog.e(d(), "connect from amdc succ", "ip", this.f20264p, "port", Integer.valueOf(this.f20265q), "originPos", Integer.valueOf(this.N.c()));
        }
        this.f20272x = "https://" + this.f20264p + ":" + this.f20265q + "/accs/";
        ALog.e(d(), BaseMonitor.ALARM_POINT_CONNECT, "URL", this.f20272x);
        this.O = String.valueOf(System.currentTimeMillis());
        if (this.I != null) {
            AppMonitor.getInstance().commitStat(this.I);
        }
        SessionMonitor sessionMonitor = new SessionMonitor();
        this.I = sessionMonitor;
        sessionMonitor.setConnectType(this.f20193c == 0 ? "service" : "inapp");
        if (this.f20274z != null) {
            try {
                this.E = System.currentTimeMillis();
                this.F = System.nanoTime();
                this.f20266r = UtilityImpl.a(this.f20194d);
                this.f20267s = UtilityImpl.b(this.f20194d);
                this.C = System.currentTimeMillis();
                this.I.onStartConnect();
                d(2);
                synchronized (this.B) {
                    try {
                        if (TextUtils.isEmpty(this.f20266r) || this.f20267s < 0 || !this.K) {
                            ALog.e(d(), "connect normal", new Object[0]);
                            sessionInfo = new SessionInfo(this.f20264p, this.f20265q, p() + OpenAccountUIConstants.UNDER_LINE + this.f20192b, null, 0, this.O, this, 4226);
                            this.L = "";
                        } else {
                            ALog.e(d(), BaseMonitor.ALARM_POINT_CONNECT, "proxy", this.f20266r, "port", Integer.valueOf(this.f20267s));
                            sessionInfo = new SessionInfo(this.f20264p, this.f20265q, p() + OpenAccountUIConstants.UNDER_LINE + this.f20192b, this.f20266r, this.f20267s, this.O, this, 4226);
                            this.L = this.f20266r + ":" + this.f20267s;
                        }
                        sessionInfo.setPubKeySeqNum(r());
                        sessionInfo.setConnectionTimeoutMs(40000);
                        this.A = this.f20274z.createSession(sessionInfo);
                        this.I.connection_stop_date = 0L;
                        this.B.wait();
                    } catch (InterruptedException e2) {
                        e2.printStackTrace();
                    } catch (Exception e3) {
                        e3.printStackTrace();
                        this.K = false;
                    } finally {
                    }
                }
            } catch (Exception e4) {
                e4.printStackTrace();
            }
        }
    }

    @Override // com.taobao.accs.net.a
    public com.taobao.accs.ut.a.c c() {
        if (this.J == null) {
            this.J = new com.taobao.accs.ut.a.c();
        }
        com.taobao.accs.ut.a.c cVar = this.J;
        cVar.f20299b = this.f20193c;
        cVar.f20301d = this.f20269u.size();
        this.J.f20306i = UtilityImpl.i(this.f20194d);
        com.taobao.accs.ut.a.c cVar2 = this.J;
        cVar2.f20303f = this.L;
        cVar2.f20298a = this.f20268t;
        SessionMonitor sessionMonitor = this.I;
        cVar2.f20300c = sessionMonitor != null && sessionMonitor.getRet();
        this.J.f20307j = q();
        com.taobao.accs.ut.a.c cVar3 = this.J;
        com.taobao.accs.data.d dVar = this.f20195e;
        cVar3.f20302e = dVar != null ? dVar.d() : 0;
        com.taobao.accs.ut.a.c cVar4 = this.J;
        cVar4.f20304g = this.f20273y;
        return cVar4;
    }

    @Override // com.taobao.accs.net.a
    public void e() {
        super.e();
        this.f20271w = false;
        ThreadPoolExecutorFactory.getScheduledExecutor().execute(new x(this));
        ALog.e(d(), "shut down", new Object[0]);
    }

    @Override // com.taobao.accs.net.a
    public void b() {
        this.K = false;
        this.f20196f = 0;
    }

    private void e(int i2) {
        this.f20201k = null;
        o();
        a aVar = this.f20270v;
        int i3 = aVar != null ? aVar.f20275a : 0;
        this.I.setCloseReason("code not 200 is" + i2);
        this.M = true;
        String str = this.f20193c == 0 ? "service" : "inapp";
        UTMini.getInstance().commitEvent(66001, "CONNECTED NO 200 " + str, (Object) Integer.valueOf(i2), (Object) Integer.valueOf(i3), (Object) 221, this.f20273y, this.L);
        com.taobao.accs.utl.k.a("accs", "auth", "", i2 + "", "");
    }

    @Override // com.taobao.accs.net.a
    public void a() {
        this.f20271w = true;
        ALog.d(d(), "start", new Object[0]);
        a(this.f20194d);
        if (this.f20270v == null) {
            ALog.i(d(), "start thread", new Object[0]);
            a aVar = new a("NetworkThread_" + this.f20203m);
            this.f20270v = aVar;
            aVar.setPriority(2);
            this.f20270v.start();
        }
        a(false, false);
    }

    @Override // com.taobao.accs.net.a
    public String b(String str) {
        return "https://" + this.f20199i.getChannelHost();
    }

    @Override // com.taobao.accs.net.a
    public void a(Message message, boolean z2) {
        if (this.f20271w && message != null) {
            try {
                if (ThreadPoolExecutorFactory.getScheduledExecutor().getQueue().size() <= 1000) {
                    ScheduledFuture<?> scheduledFutureSchedule = ThreadPoolExecutorFactory.getScheduledExecutor().schedule(new w(this, message, z2), message.delyTime, TimeUnit.MILLISECONDS);
                    if (message.getType() == 1 && message.cunstomDataId != null) {
                        if (message.isControlFrame()) {
                            a(message.cunstomDataId);
                        }
                        this.f20195e.f20137a.put(message.cunstomDataId, scheduledFutureSchedule);
                    }
                    if (message.getNetPermanceMonitor() != null) {
                        message.getNetPermanceMonitor().setDeviceId(UtilityImpl.j(this.f20194d));
                        message.getNetPermanceMonitor().setConnType(this.f20193c);
                        message.getNetPermanceMonitor().onEnterQueueData();
                        return;
                    }
                    return;
                }
                throw new RejectedExecutionException("accs");
            } catch (RejectedExecutionException unused) {
                this.f20195e.a(message, ErrorCode.MESSAGE_QUEUE_FULL);
                ALog.e(d(), "send queue full count:" + ThreadPoolExecutorFactory.getScheduledExecutor().getQueue().size(), new Object[0]);
                return;
            } catch (Throwable th) {
                this.f20195e.a(message, -8);
                ALog.e(d(), "send error", th, new Object[0]);
                return;
            }
        }
        ALog.e(d(), "not running or msg null! " + this.f20271w, new Object[0]);
    }

    @Override // com.taobao.accs.net.a
    public void a(boolean z2, boolean z3) {
        ALog.d(d(), "try ping, force:" + z2, new Object[0]);
        if (this.f20193c == 1) {
            ALog.d(d(), "INAPP, skip", new Object[0]);
            return;
        }
        Message messageBuildPing = Message.BuildPing(z2, (int) (z3 ? Math.random() * 10.0d * 1000.0d : 0.0d));
        int pingTimeout = this.f20199i.getPingTimeout();
        if (pingTimeout > 0) {
            messageBuildPing.timeout = pingTimeout;
        }
        b(messageBuildPing, z2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Message message) {
        if (message.command == null || this.f20269u.size() == 0) {
            return;
        }
        for (int size = this.f20269u.size() - 1; size >= 0; size--) {
            Message message2 = this.f20269u.get(size);
            if (message2 != null && message2.command != null && message2.getPackageName().equals(message.getPackageName())) {
                switch (message.command.intValue()) {
                    case 1:
                    case 2:
                        if (message2.command.intValue() == 1 || message2.command.intValue() == 2) {
                            this.f20269u.remove(size);
                            break;
                        }
                        break;
                    case 3:
                    case 4:
                        if (message2.command.intValue() == 3 || message2.command.intValue() == 4) {
                            this.f20269u.remove(size);
                            break;
                        }
                        break;
                    case 5:
                    case 6:
                        if (message2.command.intValue() == 5 || message2.command.intValue() == 6) {
                            this.f20269u.remove(size);
                            break;
                        }
                        break;
                }
                ALog.d(d(), "clearRepeatControlCommand message:" + message2.command + "/" + message2.getPackageName(), new Object[0]);
            }
        }
        com.taobao.accs.data.d dVar = this.f20195e;
        if (dVar != null) {
            dVar.b(message);
        }
    }

    private synchronized void d(int i2) {
        ALog.e(d(), "notifyStatus start", "status", a(i2));
        if (i2 == this.f20268t) {
            ALog.i(d(), "ignore notifyStatus", new Object[0]);
            return;
        }
        this.f20268t = i2;
        if (i2 == 1) {
            g.a(this.f20194d).f();
            t();
            ScheduledFuture<?> scheduledFuture = this.f20263o;
            if (scheduledFuture != null) {
                scheduledFuture.cancel(true);
            }
            synchronized (this.B) {
                try {
                    this.B.notifyAll();
                } catch (Exception unused) {
                }
            }
            synchronized (this.f20269u) {
                try {
                    this.f20269u.notifyAll();
                } catch (Exception unused2) {
                }
            }
            ALog.i(d(), "notifyStatus end", "status", a(i2));
        }
        if (i2 == 2) {
            ScheduledFuture<?> scheduledFuture2 = this.f20263o;
            if (scheduledFuture2 != null) {
                scheduledFuture2.cancel(true);
            }
            ThreadPoolExecutorFactory.getScheduledExecutor().schedule(new y(this, this.O), 120000L, TimeUnit.MILLISECONDS);
        } else if (i2 == 3) {
            t();
            g.a(this.f20194d).d();
            synchronized (this.B) {
                try {
                    this.B.notifyAll();
                } catch (Exception unused3) {
                }
            }
            this.f20195e.a(-10);
            a(false, true);
        }
        ALog.i(d(), "notifyStatus end", "status", a(i2));
    }

    private boolean a(String str, String str2, String str3) {
        int i2 = 1;
        if (com.taobao.accs.utl.v.b(this.f20194d) == 2) {
            return true;
        }
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str3)) {
            return true;
        }
        d(3);
        if (!TextUtils.isEmpty(str)) {
            if (TextUtils.isEmpty(str2)) {
                i2 = 2;
            } else if (TextUtils.isEmpty(str3)) {
                i2 = 3;
            }
        }
        this.I.setFailReason(i2);
        this.I.onConnectStop();
        String str4 = this.f20193c == 0 ? "service" : "inapp";
        a aVar = this.f20270v;
        int i3 = aVar != null ? aVar.f20275a : 0;
        UTMini.getInstance().commitEvent(66001, "DISCONNECT " + str4, (Object) Integer.valueOf(i2), (Object) Integer.valueOf(i3), (Object) 221, this.f20273y, this.L);
        com.taobao.accs.utl.k.a("accs", BaseMonitor.ALARM_POINT_CONNECT, "retrytimes:" + i3, i2 + "", "");
        return false;
    }

    @Override // com.taobao.accs.net.a
    public void a(String str, boolean z2, String str2) {
        try {
            d(4);
            o();
            this.I.setCloseReason(str2);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.taobao.accs.net.a
    public boolean a(String str) {
        boolean z2;
        String str2;
        synchronized (this.f20269u) {
            try {
                z2 = true;
                int size = this.f20269u.size() - 1;
                while (true) {
                    if (size >= 0) {
                        Message message = this.f20269u.get(size);
                        if (message != null && message.getType() == 1 && (str2 = message.cunstomDataId) != null && str2.equals(str)) {
                            this.f20269u.remove(size);
                            break;
                        }
                        size--;
                    } else {
                        z2 = false;
                        break;
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return z2;
    }

    @Override // com.taobao.accs.net.a
    public String d() {
        return "SilenceConn_" + this.f20203m;
    }

    @Override // com.taobao.accs.net.a
    protected void a(Context context) {
        if (this.f20197g) {
            return;
        }
        super.a(context);
        GlobalAppRuntimeInfo.setBackground(false);
        this.f20197g = true;
        ALog.i(d(), "init awcn success!", new Object[0]);
    }
}
