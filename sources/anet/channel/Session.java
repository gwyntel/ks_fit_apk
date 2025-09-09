package anet.channel;

import android.content.Context;
import android.text.TextUtils;
import anet.channel.entity.ConnType;
import anet.channel.entity.EventCb;
import anet.channel.request.Cancelable;
import anet.channel.request.Request;
import anet.channel.statist.SessionStatistic;
import anet.channel.strategy.IConnStrategy;
import anet.channel.strategy.StrategyCenter;
import anet.channel.thread.ThreadPoolExecutorFactory;
import anet.channel.util.ALog;
import anet.channel.util.HttpConstant;
import anet.channel.util.HttpHelper;
import anet.channel.util.StringUtils;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.android.spdy.SpdyAgent;
import org.android.spdy.SpdyErrorException;
import org.android.spdy.SpdySessionKind;
import org.android.spdy.SpdyVersion;

/* loaded from: classes2.dex */
public abstract class Session implements Comparable<Session> {

    /* renamed from: v, reason: collision with root package name */
    static ExecutorService f6616v = Executors.newSingleThreadExecutor();

    /* renamed from: a, reason: collision with root package name */
    public Context f6617a;

    /* renamed from: c, reason: collision with root package name */
    public String f6619c;

    /* renamed from: d, reason: collision with root package name */
    public String f6620d;

    /* renamed from: e, reason: collision with root package name */
    public String f6621e;

    /* renamed from: f, reason: collision with root package name */
    public String f6622f;

    /* renamed from: g, reason: collision with root package name */
    public int f6623g;

    /* renamed from: h, reason: collision with root package name */
    public String f6624h;

    /* renamed from: i, reason: collision with root package name */
    public int f6625i;

    /* renamed from: j, reason: collision with root package name */
    public ConnType f6626j;

    /* renamed from: k, reason: collision with root package name */
    public IConnStrategy f6627k;

    /* renamed from: m, reason: collision with root package name */
    public boolean f6629m;

    /* renamed from: o, reason: collision with root package name */
    protected Runnable f6631o;

    /* renamed from: p, reason: collision with root package name */
    public final String f6632p;

    /* renamed from: q, reason: collision with root package name */
    public final SessionStatistic f6633q;

    /* renamed from: r, reason: collision with root package name */
    public int f6634r;

    /* renamed from: s, reason: collision with root package name */
    public int f6635s;

    /* renamed from: x, reason: collision with root package name */
    private Future<?> f6639x;

    /* renamed from: b, reason: collision with root package name */
    Map<EventCb, Integer> f6618b = new LinkedHashMap();

    /* renamed from: w, reason: collision with root package name */
    private boolean f6638w = false;

    /* renamed from: l, reason: collision with root package name */
    public String f6628l = null;

    /* renamed from: n, reason: collision with root package name */
    public int f6630n = 6;

    /* renamed from: t, reason: collision with root package name */
    public boolean f6636t = false;

    /* renamed from: u, reason: collision with root package name */
    protected boolean f6637u = true;

    /* renamed from: y, reason: collision with root package name */
    private List<Long> f6640y = null;

    /* renamed from: z, reason: collision with root package name */
    private long f6641z = 0;

    public static class a {
        public static final int AUTHING = 3;
        public static final int AUTH_FAIL = 5;
        public static final int AUTH_SUCC = 4;
        public static final int CONNECTED = 0;
        public static final int CONNECTING = 1;
        public static final int CONNETFAIL = 2;
        public static final int DISCONNECTED = 6;
        public static final int DISCONNECTING = 7;

        /* renamed from: a, reason: collision with root package name */
        static final String[] f6642a = {"CONNECTED", "CONNECTING", "CONNETFAIL", "AUTHING", "AUTH_SUCC", "AUTH_FAIL", "DISCONNECTED", "DISCONNECTING"};

        static String a(int i2) {
            return f6642a[i2];
        }
    }

    public Session(Context context, anet.channel.entity.a aVar) {
        boolean z2 = false;
        this.f6629m = false;
        this.f6617a = context;
        String strA = aVar.a();
        this.f6621e = strA;
        this.f6622f = strA;
        this.f6623g = aVar.b();
        this.f6626j = aVar.c();
        String strF = aVar.f();
        this.f6619c = strF;
        this.f6620d = strF.substring(strF.indexOf(HttpConstant.SCHEME_SPLIT) + 3);
        this.f6635s = aVar.e();
        this.f6634r = aVar.d();
        IConnStrategy iConnStrategy = aVar.f6769a;
        this.f6627k = iConnStrategy;
        if (iConnStrategy != null && iConnStrategy.getIpType() == -1) {
            z2 = true;
        }
        this.f6629m = z2;
        this.f6632p = aVar.h();
        SessionStatistic sessionStatistic = new SessionStatistic(aVar);
        this.f6633q = sessionStatistic;
        sessionStatistic.host = this.f6620d;
    }

    public static void configTnetALog(Context context, String str, int i2, int i3) throws SpdyErrorException, UnsatisfiedLinkError {
        SpdyAgent spdyAgent = SpdyAgent.getInstance(context, SpdyVersion.SPDY3, SpdySessionKind.NONE_SESSION);
        if (spdyAgent == null || !SpdyAgent.checkLoadSucc()) {
            ALog.e("agent null or configTnetALog load so fail!!!", null, "loadso", Boolean.valueOf(SpdyAgent.checkLoadSucc()));
        } else {
            spdyAgent.configLogFile(str, i2, i3);
        }
    }

    protected void a() {
        Future<?> future;
        if (this.f6631o == null || (future = this.f6639x) == null) {
            return;
        }
        future.cancel(true);
    }

    public void checkAvailable() {
        ping(true);
    }

    public abstract void close();

    public void close(boolean z2) {
        this.f6636t = z2;
        close();
    }

    public void connect() {
    }

    public IConnStrategy getConnStrategy() {
        return this.f6627k;
    }

    public ConnType getConnType() {
        return this.f6626j;
    }

    public String getHost() {
        return this.f6619c;
    }

    public String getIp() {
        return this.f6621e;
    }

    public int getPort() {
        return this.f6623g;
    }

    public String getRealHost() {
        return this.f6620d;
    }

    public abstract Runnable getRecvTimeOutRunnable();

    public String getUnit() {
        return this.f6628l;
    }

    public void handleCallbacks(int i2, anet.channel.entity.b bVar) {
        f6616v.submit(new b(this, i2, bVar));
    }

    public void handleResponseCode(Request request, int i2) {
        if (request.getHeaders().containsKey(HttpConstant.X_PV) && i2 >= 500 && i2 < 600) {
            synchronized (this) {
                try {
                    if (this.f6640y == null) {
                        this.f6640y = new LinkedList();
                    }
                    if (this.f6640y.size() < 5) {
                        this.f6640y.add(Long.valueOf(System.currentTimeMillis()));
                    } else {
                        long jLongValue = this.f6640y.remove(0).longValue();
                        long jCurrentTimeMillis = System.currentTimeMillis();
                        if (jCurrentTimeMillis - jLongValue <= 60000) {
                            StrategyCenter.getInstance().forceRefreshStrategy(request.getHost());
                            this.f6640y.clear();
                        } else {
                            this.f6640y.add(Long.valueOf(jCurrentTimeMillis));
                        }
                    }
                } finally {
                }
            }
        }
    }

    public void handleResponseHeaders(Request request, Map<String, List<String>> map) {
        try {
            if (map.containsKey(HttpConstant.X_SWITCH_UNIT)) {
                String singleHeaderFieldByKey = HttpHelper.getSingleHeaderFieldByKey(map, HttpConstant.X_SWITCH_UNIT);
                if (TextUtils.isEmpty(singleHeaderFieldByKey)) {
                    singleHeaderFieldByKey = null;
                }
                if (StringUtils.isStringEqual(this.f6628l, singleHeaderFieldByKey)) {
                    return;
                }
                long jCurrentTimeMillis = System.currentTimeMillis();
                if (jCurrentTimeMillis - this.f6641z > 60000) {
                    StrategyCenter.getInstance().forceRefreshStrategy(request.getHost());
                    this.f6641z = jCurrentTimeMillis;
                }
            }
        } catch (Exception unused) {
        }
    }

    public abstract boolean isAvailable();

    public synchronized void notifyStatus(int i2, anet.channel.entity.b bVar) {
        ALog.e("awcn.Session", "notifyStatus", this.f6632p, "status", a.a(i2));
        if (i2 == this.f6630n) {
            ALog.i("awcn.Session", "ignore notifyStatus", this.f6632p, new Object[0]);
            return;
        }
        this.f6630n = i2;
        if (i2 == 0) {
            handleCallbacks(1, bVar);
        } else if (i2 == 2) {
            handleCallbacks(256, bVar);
        } else if (i2 == 4) {
            this.f6628l = StrategyCenter.getInstance().getUnitByHost(this.f6620d);
            handleCallbacks(512, bVar);
        } else if (i2 == 5) {
            handleCallbacks(1024, bVar);
        } else if (i2 == 6) {
            onDisconnect();
            if (!this.f6638w) {
                handleCallbacks(2, bVar);
            }
        }
    }

    public void onDisconnect() {
    }

    public void ping(boolean z2) {
    }

    public void registerEventcb(int i2, EventCb eventCb) {
        Map<EventCb, Integer> map = this.f6618b;
        if (map != null) {
            map.put(eventCb, Integer.valueOf(i2));
        }
    }

    public abstract Cancelable request(Request request, RequestCb requestCb);

    public void sendCustomFrame(int i2, byte[] bArr, int i3) {
    }

    public void setPingTimeout(int i2) {
        if (this.f6631o == null) {
            this.f6631o = getRecvTimeOutRunnable();
        }
        a();
        Runnable runnable = this.f6631o;
        if (runnable != null) {
            this.f6639x = ThreadPoolExecutorFactory.submitScheduledTask(runnable, i2, TimeUnit.MILLISECONDS);
        }
    }

    public String toString() {
        return "Session@[" + this.f6632p + '|' + this.f6626j + ']';
    }

    @Override // java.lang.Comparable
    public int compareTo(Session session) {
        return ConnType.compare(this.f6626j, session.f6626j);
    }

    public void ping(boolean z2, int i2) {
    }
}
