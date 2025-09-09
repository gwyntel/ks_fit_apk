package com.taobao.accs.net;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import anet.channel.Config;
import anet.channel.SessionCenter;
import anet.channel.entity.ConnType;
import anet.channel.entity.ENV;
import anet.channel.strategy.ConnProtocol;
import anet.channel.strategy.StrategyTemplate;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.taobao.accs.AccsClientConfig;
import com.taobao.accs.ErrorCode;
import com.taobao.accs.base.AccsConnectStateListener;
import com.taobao.accs.common.Constants;
import com.taobao.accs.common.ThreadPoolExecutorFactory;
import com.taobao.accs.data.Message;
import com.taobao.accs.ut.monitor.NetPerformanceMonitor;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.BaseMonitor;
import com.taobao.accs.utl.UtilityImpl;
import com.umeng.message.PushAgent;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public abstract class a {
    public static final int ACCS_RECEIVE_TIMEOUT = 40000;
    public static final int INAPP = 1;
    public static final int SERVICE = 0;

    /* renamed from: n, reason: collision with root package name */
    protected static int f20190n;

    /* renamed from: b, reason: collision with root package name */
    public String f20192b;

    /* renamed from: c, reason: collision with root package name */
    protected int f20193c;

    /* renamed from: d, reason: collision with root package name */
    protected Context f20194d;

    /* renamed from: e, reason: collision with root package name */
    protected com.taobao.accs.data.d f20195e;

    /* renamed from: h, reason: collision with root package name */
    public com.taobao.accs.client.b f20198h;

    /* renamed from: i, reason: collision with root package name */
    public AccsClientConfig f20199i;

    /* renamed from: j, reason: collision with root package name */
    protected String f20200j;

    /* renamed from: m, reason: collision with root package name */
    public String f20203m;

    /* renamed from: r, reason: collision with root package name */
    private Runnable f20207r;

    /* renamed from: s, reason: collision with root package name */
    private ScheduledFuture<?> f20208s;

    /* renamed from: a, reason: collision with root package name */
    public String f20191a = "android@umeng";

    /* renamed from: f, reason: collision with root package name */
    protected int f20196f = 0;

    /* renamed from: o, reason: collision with root package name */
    private long f20204o = 0;

    /* renamed from: g, reason: collision with root package name */
    protected volatile boolean f20197g = false;

    /* renamed from: k, reason: collision with root package name */
    protected String f20201k = null;

    /* renamed from: p, reason: collision with root package name */
    private boolean f20205p = false;

    /* renamed from: l, reason: collision with root package name */
    protected LinkedHashMap<Integer, Message> f20202l = new LinkedHashMap<Integer, Message>() { // from class: com.taobao.accs.net.BaseConnection$1
        @Override // java.util.LinkedHashMap
        protected boolean removeEldestEntry(Map.Entry<Integer, Message> entry) {
            return size() > 10;
        }
    };

    /* renamed from: q, reason: collision with root package name */
    private final ArrayList<AccsConnectStateListener> f20206q = new ArrayList<>();

    protected a(Context context, int i2, String str) {
        this.f20192b = "";
        this.f20193c = i2;
        this.f20194d = context.getApplicationContext();
        AccsClientConfig configByTag = AccsClientConfig.getConfigByTag(str);
        this.f20199i = configByTag;
        if (configByTag == null) {
            ALog.e(d(), "BaseConnection config null!!", new Object[0]);
            try {
                PushAgent pushAgent = PushAgent.getInstance(context);
                pushAgent.register(pushAgent.getRegisterCallback());
                this.f20199i = AccsClientConfig.getConfigByTag(str);
            } catch (Throwable th) {
                ALog.e(d(), "BaseConnection build config", th, new Object[0]);
            }
        }
        AccsClientConfig accsClientConfig = this.f20199i;
        if (accsClientConfig != null) {
            this.f20203m = accsClientConfig.getTag();
            this.f20192b = this.f20199i.getAppKey();
        }
        com.taobao.accs.data.d dVar = new com.taobao.accs.data.d(context, this);
        this.f20195e = dVar;
        dVar.f20138b = this.f20193c;
        ALog.d(d(), "new connection", new Object[0]);
    }

    protected String a(int i2) {
        return i2 != 1 ? i2 != 2 ? (i2 == 3 || i2 != 4) ? "DISCONNECTED" : "DISCONNECTING" : "CONNECTING" : "CONNECTED";
    }

    public abstract void a();

    public abstract void a(Message message, boolean z2);

    public abstract void a(String str, boolean z2, String str2);

    public abstract void a(boolean z2, boolean z3);

    public abstract boolean a(String str);

    public abstract void b();

    public void b(Message message, boolean z2) {
        Object obj;
        long jA;
        if (!message.isAck && !UtilityImpl.i(this.f20194d)) {
            ALog.e(d(), "sendMessage ready no network", Constants.KEY_DATA_ID, message.dataId);
            this.f20195e.a(message, -13);
            return;
        }
        if (message.getType() != 2) {
            obj = "delay";
            jA = this.f20195e.f20140d.a(message.serviceId, message.bizId);
        } else {
            obj = "delay";
            jA = 0;
        }
        if (jA == -1) {
            ALog.e(d(), "sendMessage ready server limit high", Constants.KEY_DATA_ID, message.dataId);
            this.f20195e.a(message, ErrorCode.SERVIER_HIGH_LIMIT);
            return;
        }
        if (jA == -1000) {
            ALog.e(d(), "sendMessage ready server limit high for brush", Constants.KEY_DATA_ID, message.dataId);
            this.f20195e.a(message, ErrorCode.SERVIER_HIGH_LIMIT_BRUSH);
            return;
        }
        if (jA > 0) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            long j2 = this.f20204o;
            if (jCurrentTimeMillis > j2) {
                message.delyTime = jA;
            } else {
                message.delyTime = (j2 + jA) - System.currentTimeMillis();
            }
            this.f20204o = System.currentTimeMillis() + message.delyTime;
            ALog.e(d(), "sendMessage ready", Constants.KEY_DATA_ID, message.dataId, "type", Message.MsgType.name(message.getType()), obj, Long.valueOf(message.delyTime));
        } else if ("accs".equals(message.serviceId)) {
            ALog.e(d(), "sendMessage ready", Constants.KEY_DATA_ID, message.dataId, "type", Message.MsgType.name(message.getType()), obj, Long.valueOf(message.delyTime));
        } else if (ALog.isPrintLog(ALog.Level.D)) {
            ALog.d(d(), "sendMessage ready", Constants.KEY_DATA_ID, message.dataId, "type", Message.MsgType.name(message.getType()), obj, Long.valueOf(message.delyTime));
        }
        try {
            if (TextUtils.isEmpty(this.f20200j)) {
                this.f20200j = UtilityImpl.j(this.f20194d);
            }
            if (message.isTimeOut()) {
                this.f20195e.a(message, -9);
            } else {
                a(message, z2);
            }
        } catch (RejectedExecutionException unused) {
            this.f20195e.a(message, ErrorCode.MESSAGE_QUEUE_FULL);
            ALog.e(d(), "sendMessage ready queue full", "size", Integer.valueOf(ThreadPoolExecutorFactory.getSendScheduledExecutor().getQueue().size()));
        }
    }

    public abstract com.taobao.accs.ut.a.c c();

    protected String c(String str) {
        String strEncode;
        String strJ = UtilityImpl.j(this.f20194d);
        try {
            strEncode = URLEncoder.encode(strJ);
        } catch (Throwable th) {
            ALog.e(d(), "buildAuthUrl", th, new Object[0]);
            strEncode = strJ;
        }
        String strA = UtilityImpl.a(i(), this.f20199i.getAppSecret(), strJ);
        StringBuilder sb = new StringBuilder(256);
        sb.append(str);
        sb.append("auth?1=");
        sb.append(strEncode);
        sb.append("&2=");
        sb.append(strA);
        sb.append("&3=");
        sb.append(i());
        if (this.f20201k != null) {
            sb.append("&4=");
            sb.append(this.f20201k);
        }
        sb.append("&5=");
        sb.append(this.f20193c);
        sb.append("&6=");
        sb.append(UtilityImpl.g(this.f20194d));
        sb.append("&7=");
        sb.append(UtilityImpl.d());
        sb.append("&8=");
        sb.append(this.f20193c == 1 ? "1.1.2" : 221);
        sb.append("&9=");
        sb.append(System.currentTimeMillis());
        sb.append("&10=");
        sb.append(1);
        sb.append("&11=");
        sb.append(Build.VERSION.SDK_INT);
        sb.append("&12=");
        sb.append(this.f20194d.getPackageName());
        sb.append("&13=");
        sb.append(UtilityImpl.l(this.f20194d));
        sb.append("&14=");
        sb.append(this.f20191a);
        sb.append("&15=");
        sb.append(UtilityImpl.b(Build.MODEL));
        sb.append("&16=");
        sb.append(UtilityImpl.b(Build.BRAND));
        sb.append("&17=");
        sb.append("221");
        sb.append("&19=");
        sb.append(!l() ? 1 : 0);
        sb.append("&20=");
        sb.append(this.f20199i.getStoreId());
        return sb.toString();
    }

    public abstract String d();

    public void e() {
    }

    protected void f() {
        if (this.f20207r == null) {
            this.f20207r = new c(this);
        }
        g();
        this.f20208s = ThreadPoolExecutorFactory.getScheduledExecutor().schedule(this.f20207r, 40000L, TimeUnit.MILLISECONDS);
    }

    protected void g() {
        ScheduledFuture<?> scheduledFuture = this.f20208s;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
        }
    }

    protected boolean h() {
        return true;
    }

    public String i() {
        return this.f20192b;
    }

    public com.taobao.accs.client.b j() {
        if (this.f20198h == null) {
            ALog.d(d(), "new ClientManager", Constants.KEY_CONFIG_TAG, this.f20203m);
            this.f20198h = new com.taobao.accs.client.b(this.f20194d, this.f20203m);
        }
        return this.f20198h;
    }

    public void k() {
        try {
            ThreadPoolExecutorFactory.schedule(new d(this), 10000L, TimeUnit.MILLISECONDS);
        } catch (Throwable th) {
            ALog.w(d(), "startChannelService", th, new Object[0]);
        }
    }

    public boolean l() {
        return 2 == this.f20199i.getSecurity();
    }

    public boolean m() {
        return false;
    }

    protected ArrayList<AccsConnectStateListener> n() {
        return this.f20206q;
    }

    protected void a(String str, boolean z2, long j2) {
        ThreadPoolExecutorFactory.getScheduledExecutor().schedule(new b(this, str, z2), j2, TimeUnit.MILLISECONDS);
    }

    protected boolean a(Message message, int i2) {
        boolean z2;
        int i3;
        try {
            i3 = message.retryTimes;
        } catch (Throwable th) {
            th = th;
            z2 = false;
        }
        if (i3 > 3) {
            return false;
        }
        z2 = true;
        message.retryTimes = i3 + 1;
        message.delyTime = i2;
        ALog.e(d(), "reSend dataid:" + message.dataId + " retryTimes:" + message.retryTimes, new Object[0]);
        b(message, true);
        try {
            if (message.getNetPermanceMonitor() != null) {
                message.getNetPermanceMonitor().take_date = 0L;
                message.getNetPermanceMonitor().to_tnet_date = 0L;
                NetPerformanceMonitor netPermanceMonitor = message.getNetPermanceMonitor();
                int i4 = message.retryTimes;
                netPermanceMonitor.retry_times = i4;
                if (i4 == 1) {
                    com.taobao.accs.utl.k.a("accs", BaseMonitor.COUNT_POINT_RESEND, AlinkConstants.KEY_TOTAL, 0.0d);
                }
            }
        } catch (Throwable th2) {
            th = th2;
            this.f20195e.a(message, -8);
            ALog.e(d(), "reSend error", th, new Object[0]);
            return z2;
        }
        return z2;
    }

    protected void a(Context context) {
        try {
            ENV env = ENV.ONLINE;
            int i2 = AccsClientConfig.mEnv;
            if (i2 == 2) {
                env = ENV.TEST;
                SessionCenter.switchEnvironment(env);
            } else if (i2 == 1) {
                env = ENV.PREPARE;
                SessionCenter.switchEnvironment(env);
            }
            SessionCenter.init(context, new Config.Builder().setAppkey(this.f20192b).setAppSecret(this.f20199i.getAppSecret()).setAuthCode(this.f20199i.getAuthCode()).setEnv(env).setTag(this.f20199i.getAppKey()).build());
            String str = ConnType.PK_ACS;
            if (this.f20199i.getInappPubKey() == 10 || this.f20199i.getInappPubKey() == 11) {
                str = "open";
            }
            ALog.i(d(), "init awcn register new conn protocol host:", this.f20199i.getInappHost());
            StrategyTemplate.getInstance().registerConnProtocol(this.f20199i.getInappHost(), ConnProtocol.valueOf(ConnType.HTTP2, ConnType.RTT_0, str, false));
        } catch (Throwable th) {
            ALog.e(d(), "initAwcn", th, new Object[0]);
        }
    }

    protected void b(int i2) {
        if (i2 < 0) {
            ALog.e(d(), "reSendAck", Constants.KEY_DATA_ID, Integer.valueOf(i2));
            Message message = this.f20202l.get(Integer.valueOf(i2));
            if (message != null) {
                a(message, 5000);
                com.taobao.accs.utl.k.a("accs", BaseMonitor.COUNT_POINT_RESEND, BaseMonitor.COUNT_ACK, 0.0d);
            }
        }
    }

    public void c(int i2) {
        f20190n = i2 != 1 ? 0 : 1;
    }

    public String b(String str) {
        String inappHost = this.f20199i.getInappHost();
        StringBuilder sb = new StringBuilder();
        sb.append("https://");
        sb.append(TextUtils.isEmpty(str) ? "" : str);
        sb.append(inappHost);
        String string = sb.toString();
        try {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("https://");
            if (TextUtils.isEmpty(str)) {
                str = "";
            }
            sb2.append(str);
            sb2.append(inappHost);
            return sb2.toString();
        } catch (Throwable th) {
            ALog.e("InAppConnection", "getHost", th, new Object[0]);
            return string;
        }
    }

    public void b(Message message, int i2) {
        this.f20195e.a(message, i2);
    }

    public void b(AccsConnectStateListener accsConnectStateListener) {
        synchronized (this.f20206q) {
            this.f20206q.remove(accsConnectStateListener);
        }
    }

    public void a(AccsConnectStateListener accsConnectStateListener) {
        synchronized (this.f20206q) {
            this.f20206q.add(accsConnectStateListener);
        }
    }
}
