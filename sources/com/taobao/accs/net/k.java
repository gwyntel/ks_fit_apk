package com.taobao.accs.net;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import anet.channel.DataFrameCb;
import anet.channel.IAuth;
import anet.channel.ISessionListener;
import anet.channel.Session;
import anet.channel.SessionCenter;
import anet.channel.SessionInfo;
import anet.channel.entity.ConnType;
import anet.channel.request.Request;
import anet.channel.session.TnetSpdySession;
import anet.channel.strategy.ConnProtocol;
import anet.channel.strategy.StrategyTemplate;
import com.taobao.accs.AccsClientConfig;
import com.taobao.accs.ErrorCode;
import com.taobao.accs.common.ThreadPoolExecutorFactory;
import com.taobao.accs.data.Message;
import com.taobao.accs.ut.monitor.NetPerformanceMonitor;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.UtilityImpl;
import com.tekartik.sqflite.Constant;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class k extends com.taobao.accs.net.a implements DataFrameCb {

    /* renamed from: o, reason: collision with root package name */
    private boolean f20229o;

    /* renamed from: p, reason: collision with root package name */
    private long f20230p;

    /* renamed from: q, reason: collision with root package name */
    private ScheduledFuture f20231q;

    /* renamed from: r, reason: collision with root package name */
    private Handler f20232r;

    /* renamed from: s, reason: collision with root package name */
    private Runnable f20233s;

    /* renamed from: t, reason: collision with root package name */
    private ISessionListener f20234t;

    /* renamed from: u, reason: collision with root package name */
    private Runnable f20235u;

    /* renamed from: v, reason: collision with root package name */
    private Set<String> f20236v;

    public static class a implements IAuth {

        /* renamed from: a, reason: collision with root package name */
        private String f20237a;

        /* renamed from: b, reason: collision with root package name */
        private int f20238b;

        /* renamed from: c, reason: collision with root package name */
        private String f20239c;

        /* renamed from: d, reason: collision with root package name */
        private com.taobao.accs.net.a f20240d;

        public a(com.taobao.accs.net.a aVar, String str) {
            this.f20239c = aVar.d();
            this.f20237a = aVar.c("https://" + str + "/accs/");
            this.f20238b = aVar.f20193c;
            this.f20240d = aVar;
        }

        @Override // anet.channel.IAuth
        public void auth(Session session, IAuth.AuthCallback authCallback) {
            ALog.e(this.f20239c, "auth", "URL", this.f20237a);
            session.request(new Request.Builder().setUrl(this.f20237a).build(), new u(this, authCallback));
        }
    }

    public k(Context context, int i2, String str) {
        super(context, i2, str);
        this.f20229o = true;
        this.f20230p = 3600000L;
        this.f20232r = new Handler(Looper.getMainLooper());
        this.f20233s = new l(this);
        this.f20234t = new m(this);
        this.f20235u = new t(this);
        this.f20236v = Collections.synchronizedSet(new HashSet());
        ThreadPoolExecutorFactory.getScheduledExecutor().schedule(this.f20235u, 120000L, TimeUnit.MILLISECONDS);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void o() {
        if (this.f20199i.isAccsHeartbeatEnable()) {
            ALog.e(d(), "startAccsHeartBeat", new Object[0]);
            ScheduledFuture scheduledFuture = this.f20231q;
            if (scheduledFuture != null) {
                scheduledFuture.cancel(true);
            }
            ScheduledThreadPoolExecutor scheduledExecutor = ThreadPoolExecutorFactory.getScheduledExecutor();
            Runnable runnable = this.f20233s;
            long j2 = this.f20230p;
            this.f20231q = scheduledExecutor.scheduleAtFixedRate(runnable, j2, j2, TimeUnit.MILLISECONDS);
        }
    }

    @Override // com.taobao.accs.net.a
    public com.taobao.accs.ut.a.c c() {
        return null;
    }

    @Override // com.taobao.accs.net.a
    public String d() {
        return "InAppConn_" + this.f20203m;
    }

    @Override // com.taobao.accs.net.a
    public void e() {
        ALog.e(d(), "shut down", new Object[0]);
        this.f20229o = false;
    }

    @Override // com.taobao.accs.net.a
    public boolean m() {
        if (!this.f20197g) {
            return false;
        }
        try {
            Session session = SessionCenter.getInstance(this.f20199i.getAppKey()).get(b((String) null), ConnType.TypeLevel.SPDY, 0L);
            ALog.e("InAppConn_", "isConnected", "state", Boolean.valueOf(session == null || session.isAvailable()));
            if (session != null) {
                if (session.isAvailable()) {
                    return true;
                }
            }
            return false;
        } catch (Exception unused) {
            return true;
        }
    }

    @Override // anet.channel.DataFrameCb
    public void onDataReceive(TnetSpdySession tnetSpdySession, byte[] bArr, int i2, int i3) {
        if (ALog.isPrintLog(ALog.Level.E)) {
            ALog.e(d(), "onDataReceive", "type", Integer.valueOf(i3), "dataid", Integer.valueOf(i2));
        }
        ThreadPoolExecutorFactory.getScheduledExecutor().execute(new r(this, i3, bArr, tnetSpdySession));
    }

    @Override // anet.channel.DataFrameCb
    public void onException(int i2, int i3, boolean z2, String str) {
        ALog.e(d(), "errorId:" + i3 + "detail:" + str + " dataId:" + i2 + " needRetry:" + z2, new Object[0]);
        ThreadPoolExecutorFactory.getScheduledExecutor().execute(new s(this, i2, z2, i3));
    }

    @Override // com.taobao.accs.net.a
    public synchronized void a() {
        ALog.d(d(), "start", new Object[0]);
        this.f20229o = true;
        a(this.f20194d);
    }

    @Override // com.taobao.accs.net.a
    public void b() {
        this.f20196f = 0;
    }

    @Override // com.taobao.accs.net.a
    public void c(int i2) {
        super.c(i2);
    }

    @Override // com.taobao.accs.net.a
    public void a(Message message, boolean z2) {
        if (this.f20229o && message != null) {
            try {
                if (ThreadPoolExecutorFactory.getSendScheduledExecutor().getQueue().size() <= 1000) {
                    ScheduledFuture<?> scheduledFutureSchedule = ThreadPoolExecutorFactory.getSendScheduledExecutor().schedule(new o(this, message), message.delyTime, TimeUnit.MILLISECONDS);
                    if (message.getType() == 1 && message.cunstomDataId != null) {
                        if (message.isControlFrame() && a(message.cunstomDataId)) {
                            this.f20195e.b(message);
                        }
                        this.f20195e.f20137a.put(message.cunstomDataId, scheduledFutureSchedule);
                    }
                    NetPerformanceMonitor netPermanceMonitor = message.getNetPermanceMonitor();
                    if (netPermanceMonitor != null) {
                        netPermanceMonitor.setDeviceId(UtilityImpl.j(this.f20194d));
                        netPermanceMonitor.setConnType(this.f20193c);
                        netPermanceMonitor.onEnterQueueData();
                        return;
                    }
                    return;
                }
                throw new RejectedExecutionException("accs");
            } catch (RejectedExecutionException unused) {
                this.f20195e.a(message, ErrorCode.MESSAGE_QUEUE_FULL);
                ALog.e(d(), "send queue full count:" + ThreadPoolExecutorFactory.getSendScheduledExecutor().getQueue().size(), new Object[0]);
                return;
            } catch (Throwable th) {
                this.f20195e.a(message, -8);
                ALog.e(d(), "send error", th, new Object[0]);
                return;
            }
        }
        ALog.e(d(), "not running or msg null! " + this.f20229o, new Object[0]);
    }

    public k(Context context, int i2, String str, int i3) {
        super(context, i2, str);
        this.f20229o = true;
        this.f20230p = 3600000L;
        this.f20232r = new Handler(Looper.getMainLooper());
        this.f20233s = new l(this);
        this.f20234t = new m(this);
        this.f20235u = new t(this);
        this.f20236v = Collections.synchronizedSet(new HashSet());
        c(i3);
        ThreadPoolExecutorFactory.getScheduledExecutor().schedule(this.f20235u, 120000L, TimeUnit.MILLISECONDS);
    }

    @Override // com.taobao.accs.net.a
    protected void a(String str, boolean z2, long j2) {
        ThreadPoolExecutorFactory.getScheduledExecutor().schedule(new p(this, str, z2), j2, TimeUnit.MILLISECONDS);
    }

    @Override // com.taobao.accs.net.a
    public void a(boolean z2, boolean z3) {
        ThreadPoolExecutorFactory.getSendScheduledExecutor().execute(new q(this));
    }

    @Override // com.taobao.accs.net.a
    public void a(String str, boolean z2, String str2) {
        Session session;
        try {
            Message messageB = this.f20195e.b(str);
            if (messageB != null && messageB.host != null && (session = SessionCenter.getInstance(this.f20199i.getAppKey()).get(messageB.host.toString(), 0L)) != null) {
                if (z2) {
                    ALog.e(d(), "close session by time out", new Object[0]);
                    session.close(true);
                } else {
                    session.ping(true);
                }
            }
        } catch (Exception e2) {
            ALog.e(d(), "onTimeOut", e2, new Object[0]);
        }
    }

    @Override // com.taobao.accs.net.a
    public boolean a(String str) {
        if (str == null) {
            return false;
        }
        ScheduledFuture<?> scheduledFuture = this.f20195e.f20137a.get(str);
        boolean zCancel = scheduledFuture != null ? scheduledFuture.cancel(false) : false;
        if (zCancel) {
            ALog.e(d(), Constant.PARAM_CANCEL, "customDataId", str);
        }
        return zCancel;
    }

    public void a(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null) {
            ALog.e(d(), "onReceiveAccsHeartbeatResp response data is null", new Object[0]);
            return;
        }
        if (ALog.isPrintLog(ALog.Level.I)) {
            ALog.i(d(), "onReceiveAccsHeartbeatResp", "data", jSONObject);
        }
        try {
            int i2 = jSONObject.getInt("timeInterval");
            if (i2 == -1) {
                ScheduledFuture scheduledFuture = this.f20231q;
                if (scheduledFuture != null) {
                    scheduledFuture.cancel(true);
                    return;
                }
                return;
            }
            long j2 = i2 * 1000;
            if (this.f20230p != j2) {
                if (i2 == 0) {
                    j2 = 3600000;
                }
                this.f20230p = j2;
                ScheduledFuture scheduledFuture2 = this.f20231q;
                if (scheduledFuture2 != null) {
                    scheduledFuture2.cancel(true);
                }
                ScheduledThreadPoolExecutor scheduledExecutor = ThreadPoolExecutorFactory.getScheduledExecutor();
                Runnable runnable = this.f20233s;
                long j3 = this.f20230p;
                this.f20231q = scheduledExecutor.scheduleAtFixedRate(runnable, j3, j3, TimeUnit.MILLISECONDS);
            }
        } catch (JSONException e2) {
            ALog.e(d(), "onReceiveAccsHeartbeatResp", "e", e2.getMessage());
        }
    }

    @Override // com.taobao.accs.net.a
    protected void a(Context context) {
        boolean z2;
        try {
            if (this.f20197g) {
                return;
            }
            super.a(context);
            if (com.taobao.accs.utl.t.c()) {
                SessionCenter.getInstance(this.f20199i.getAppKey()).registerAccsSessionListener(this.f20234t);
            }
            String inappHost = this.f20199i.getInappHost();
            if (h() && this.f20199i.isKeepalive()) {
                z2 = true;
            } else {
                ALog.d(d(), "initAwcn close keepalive", new Object[0]);
                z2 = false;
            }
            a(SessionCenter.getInstance(this.f20199i.getAppKey()), inappHost, z2);
            this.f20197g = true;
            ALog.i(d(), "initAwcn success!", new Object[0]);
        } catch (Throwable th) {
            ALog.e(d(), "initAwcn", th, new Object[0]);
        }
    }

    public void a(SessionCenter sessionCenter, String str, boolean z2) {
        if (this.f20236v.contains(str)) {
            return;
        }
        sessionCenter.registerSessionInfo(SessionInfo.create(str, z2, true, new a(this, str), null, this));
        sessionCenter.registerPublicKey(str, this.f20199i.getInappPubKey());
        this.f20236v.add(str);
        ALog.i(d(), "registerSessionInfo", "host", str);
    }

    public void a(AccsClientConfig accsClientConfig) {
        boolean z2 = true;
        if (accsClientConfig == null) {
            ALog.i(d(), "updateConfig null", new Object[0]);
            return;
        }
        if (accsClientConfig.equals(this.f20199i)) {
            ALog.w(d(), "updateConfig not any changed", new Object[0]);
            return;
        }
        if (this.f20197g) {
            try {
                ALog.w(d(), "updateConfig", "old", this.f20199i, "new", accsClientConfig);
                String inappHost = this.f20199i.getInappHost();
                String inappHost2 = accsClientConfig.getInappHost();
                SessionCenter sessionCenter = SessionCenter.getInstance(this.f20199i.getAppKey());
                if (sessionCenter == null) {
                    ALog.w(d(), "updateConfig not need update", new Object[0]);
                    return;
                }
                sessionCenter.unregisterSessionInfo(inappHost);
                ALog.w(d(), "updateConfig unregisterSessionInfo", "host", inappHost);
                if (this.f20236v.contains(inappHost)) {
                    this.f20236v.remove(inappHost);
                    ALog.w(d(), "updateConfig removeSessionRegistered", "oldHost", inappHost);
                }
                this.f20199i = accsClientConfig;
                this.f20192b = accsClientConfig.getAppKey();
                this.f20203m = this.f20199i.getTag();
                String str = ConnType.PK_ACS;
                if (this.f20199i.getInappPubKey() == 10 || this.f20199i.getInappPubKey() == 11) {
                    str = "open";
                }
                ALog.i(d(), "update config register new conn protocol host:", this.f20199i.getInappHost());
                StrategyTemplate.getInstance().registerConnProtocol(this.f20199i.getInappHost(), ConnProtocol.valueOf(ConnType.HTTP2, ConnType.RTT_0, str, false));
                if (!h() || !this.f20199i.isKeepalive()) {
                    ALog.i(d(), "updateConfig close keepalive", new Object[0]);
                    z2 = false;
                }
                a(sessionCenter, inappHost2, z2);
                return;
            } catch (Throwable th) {
                ALog.e(d(), "updateConfig", th, new Object[0]);
                return;
            }
        }
        if (UtilityImpl.isMainProcess(this.f20194d)) {
            this.f20199i = accsClientConfig;
            a(this.f20194d);
        }
    }
}
