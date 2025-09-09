package anetwork.channel.unified;

import android.text.TextUtils;
import androidx.media3.exoplayer.rtsp.RtspHeaders;
import anet.channel.Config;
import anet.channel.GlobalAppRuntimeInfo;
import anet.channel.NoAvailStrategyException;
import anet.channel.Session;
import anet.channel.SessionCenter;
import anet.channel.appmonitor.AppMonitor;
import anet.channel.bytes.ByteArray;
import anet.channel.entity.ENV;
import anet.channel.request.Cancelable;
import anet.channel.request.Request;
import anet.channel.statist.ExceptionStatistic;
import anet.channel.statist.RequestStatistic;
import anet.channel.status.NetworkStatusHelper;
import anet.channel.thread.ThreadPoolExecutorFactory;
import anet.channel.util.ALog;
import anet.channel.util.AppLifecycle;
import anet.channel.util.ErrorConstant;
import anet.channel.util.HttpConstant;
import anet.channel.util.HttpUrl;
import anet.channel.util.StringUtils;
import anetwork.channel.aidl.DefaultFinishEvent;
import anetwork.channel.cache.Cache;
import anetwork.channel.config.NetworkConfigCenter;
import anetwork.channel.http.NetworkSdkSetting;
import anetwork.channel.interceptor.Callback;
import anetwork.channel.util.RequestConstant;
import com.aliyun.alink.linksdk.alcs.coap.resources.LinkFormat;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes2.dex */
class e implements IUnifiedTask {
    public static final int MAX_RSP_BUFFER_LENGTH = 131072;
    public static final String TAG = "anet.NetworkTask";

    /* renamed from: a, reason: collision with root package name */
    j f7253a;

    /* renamed from: b, reason: collision with root package name */
    Cache f7254b;

    /* renamed from: c, reason: collision with root package name */
    Cache.Entry f7255c;

    /* renamed from: e, reason: collision with root package name */
    String f7257e;

    /* renamed from: h, reason: collision with root package name */
    volatile AtomicBoolean f7260h;

    /* renamed from: d, reason: collision with root package name */
    ByteArrayOutputStream f7256d = null;

    /* renamed from: f, reason: collision with root package name */
    volatile Cancelable f7258f = null;

    /* renamed from: g, reason: collision with root package name */
    volatile boolean f7259g = false;

    /* renamed from: i, reason: collision with root package name */
    int f7261i = 0;

    /* renamed from: j, reason: collision with root package name */
    int f7262j = 0;

    /* renamed from: k, reason: collision with root package name */
    boolean f7263k = false;

    /* renamed from: l, reason: collision with root package name */
    boolean f7264l = false;

    /* renamed from: m, reason: collision with root package name */
    a f7265m = null;

    e(j jVar, Cache cache, Cache.Entry entry) {
        this.f7254b = null;
        this.f7255c = null;
        this.f7257e = "other";
        this.f7260h = null;
        this.f7253a = jVar;
        this.f7260h = jVar.f7289d;
        this.f7254b = cache;
        this.f7255c = entry;
        this.f7257e = jVar.f7286a.h().get(HttpConstant.F_REFER);
    }

    private Session b() {
        Session throwsException;
        SessionCenter sessionCenterA = a();
        HttpUrl httpUrlF = this.f7253a.f7286a.f();
        boolean zContainsNonDefaultPort = httpUrlF.containsNonDefaultPort();
        anetwork.channel.entity.g gVar = this.f7253a.f7286a;
        RequestStatistic requestStatistic = gVar.f7230b;
        if (gVar.f7234f != 1 || !NetworkConfigCenter.isSpdyEnabled() || this.f7253a.f7286a.f7229a != 0 || zContainsNonDefaultPort) {
            return a(null, sessionCenterA, httpUrlF, zContainsNonDefaultPort);
        }
        HttpUrl httpUrlA = a(httpUrlF);
        try {
            throwsException = sessionCenterA.getThrowsException(httpUrlA, anet.channel.entity.c.f6777a, 0L);
        } catch (NoAvailStrategyException unused) {
            return a(null, sessionCenterA, httpUrlF, zContainsNonDefaultPort);
        } catch (Exception unused2) {
            throwsException = null;
        }
        if (throwsException == null) {
            ThreadPoolExecutorFactory.submitPriorityTask(new g(this, sessionCenterA, httpUrlA, requestStatistic, httpUrlF, zContainsNonDefaultPort), ThreadPoolExecutorFactory.Priority.NORMAL);
            return null;
        }
        ALog.i(TAG, "tryGetSession", this.f7253a.f7288c, RtspHeaders.SESSION, throwsException);
        requestStatistic.spdyRequestSend = true;
        return throwsException;
    }

    private void c() {
        SessionCenter sessionCenterA = a();
        HttpUrl httpUrlF = this.f7253a.f7286a.f();
        boolean zContainsNonDefaultPort = httpUrlF.containsNonDefaultPort();
        anetwork.channel.entity.g gVar = this.f7253a.f7286a;
        RequestStatistic requestStatistic = gVar.f7230b;
        Request requestA = gVar.a();
        if (this.f7253a.f7286a.f7234f != 1 || !NetworkConfigCenter.isSpdyEnabled() || this.f7253a.f7286a.f7229a != 0 || zContainsNonDefaultPort) {
            a(a(null, sessionCenterA, httpUrlF, zContainsNonDefaultPort), requestA);
            return;
        }
        sessionCenterA.asyncGet(a(httpUrlF), anet.channel.entity.c.f6777a, 3000L, new h(this, requestStatistic, System.currentTimeMillis(), requestA, sessionCenterA, httpUrlF, zContainsNonDefaultPort));
    }

    @Override // anet.channel.request.Cancelable
    public void cancel() {
        this.f7259g = true;
        if (this.f7258f != null) {
            this.f7258f.cancel();
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.f7259g) {
            return;
        }
        RequestStatistic requestStatistic = this.f7253a.f7286a.f7230b;
        requestStatistic.f_refer = this.f7257e;
        if (!NetworkStatusHelper.isConnected()) {
            if (NetworkConfigCenter.isRequestDelayRetryForNoNetwork() && requestStatistic.statusCode != -200) {
                requestStatistic.statusCode = -200;
                ThreadPoolExecutorFactory.submitScheduledTask(new f(this), 1000L, TimeUnit.MILLISECONDS);
                return;
            }
            if (ALog.isPrintLog(2)) {
                ALog.i(TAG, "network unavailable", this.f7253a.f7288c, "NetworkStatus", NetworkStatusHelper.getStatus());
            }
            this.f7260h.set(true);
            this.f7253a.a();
            requestStatistic.isDone.set(true);
            requestStatistic.statusCode = -200;
            requestStatistic.msg = ErrorConstant.getErrMsg(-200);
            requestStatistic.rspEnd = System.currentTimeMillis();
            this.f7253a.f7287b.onFinish(new DefaultFinishEvent(-200, (String) null, this.f7253a.f7286a.a()));
            return;
        }
        if (!NetworkConfigCenter.isBgRequestForbidden() || !GlobalAppRuntimeInfo.isAppBackground() || AppLifecycle.lastEnterBackgroundTime <= 0 || AppLifecycle.isGoingForeground || System.currentTimeMillis() - AppLifecycle.lastEnterBackgroundTime <= NetworkConfigCenter.getBgForbidRequestThreshold() || NetworkConfigCenter.isUrlInWhiteList(this.f7253a.f7286a.f()) || NetworkConfigCenter.isBizInWhiteList(this.f7253a.f7286a.a().getBizId()) || this.f7253a.f7286a.a().isAllowRequestInBg()) {
            if (ALog.isPrintLog(2)) {
                j jVar = this.f7253a;
                ALog.i(TAG, "exec request", jVar.f7288c, "retryTimes", Integer.valueOf(jVar.f7286a.f7229a));
            }
            if (NetworkConfigCenter.isGetSessionAsyncEnable()) {
                c();
                return;
            }
            try {
                Session sessionB = b();
                if (sessionB == null) {
                    return;
                }
                a(sessionB, this.f7253a.f7286a.a());
                return;
            } catch (Exception e2) {
                ALog.e(TAG, "send request failed.", this.f7253a.f7288c, e2, new Object[0]);
                return;
            }
        }
        this.f7260h.set(true);
        this.f7253a.a();
        if (ALog.isPrintLog(2)) {
            j jVar2 = this.f7253a;
            ALog.i(TAG, "request forbidden in background", jVar2.f7288c, "url", jVar2.f7286a.f());
        }
        requestStatistic.isDone.set(true);
        requestStatistic.statusCode = -205;
        requestStatistic.msg = ErrorConstant.getErrMsg(-205);
        requestStatistic.rspEnd = System.currentTimeMillis();
        this.f7253a.f7287b.onFinish(new DefaultFinishEvent(-205, (String) null, this.f7253a.f7286a.a()));
        ExceptionStatistic exceptionStatistic = new ExceptionStatistic(-205, null, LinkFormat.RESOURCE_TYPE);
        exceptionStatistic.host = this.f7253a.f7286a.f().host();
        exceptionStatistic.url = this.f7253a.f7286a.g();
        AppMonitor.getInstance().commitStat(exceptionStatistic);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class a {

        /* renamed from: a, reason: collision with root package name */
        int f7266a;

        /* renamed from: b, reason: collision with root package name */
        Map<String, List<String>> f7267b;

        /* renamed from: c, reason: collision with root package name */
        List<ByteArray> f7268c = new ArrayList();

        a(int i2, Map<String, List<String>> map) {
            this.f7266a = i2;
            this.f7267b = map;
        }

        void a() {
            Iterator<ByteArray> it = this.f7268c.iterator();
            while (it.hasNext()) {
                it.next().recycle();
            }
        }

        int a(Callback callback, int i2) {
            callback.onResponseCode(this.f7266a, this.f7267b);
            Iterator<ByteArray> it = this.f7268c.iterator();
            int i3 = 1;
            while (it.hasNext()) {
                callback.onDataReceiveSize(i3, i2, it.next());
                i3++;
            }
            return i3;
        }
    }

    private HttpUrl a(HttpUrl httpUrl) {
        HttpUrl httpUrl2;
        String str = this.f7253a.f7286a.h().get(HttpConstant.X_HOST_CNAME);
        return (TextUtils.isEmpty(str) || (httpUrl2 = HttpUrl.parse(httpUrl.urlString().replaceFirst(httpUrl.host(), str))) == null) ? httpUrl : httpUrl2;
    }

    private SessionCenter a() {
        String strA = this.f7253a.f7286a.a(RequestConstant.APPKEY);
        if (TextUtils.isEmpty(strA)) {
            return SessionCenter.getInstance();
        }
        ENV env = ENV.ONLINE;
        String strA2 = this.f7253a.f7286a.a(RequestConstant.ENVIRONMENT);
        if ("pre".equalsIgnoreCase(strA2)) {
            env = ENV.PREPARE;
        } else if ("test".equalsIgnoreCase(strA2)) {
            env = ENV.TEST;
        }
        if (env != NetworkSdkSetting.CURRENT_ENV) {
            NetworkSdkSetting.CURRENT_ENV = env;
            SessionCenter.switchEnvironment(env);
        }
        Config config = Config.getConfig(strA, env);
        if (config == null) {
            config = new Config.Builder().setAppkey(strA).setEnv(env).setAuthCode(this.f7253a.f7286a.a(RequestConstant.AUTH_CODE)).build();
        }
        return SessionCenter.getInstance(config);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Session a(Session session, SessionCenter sessionCenter, HttpUrl httpUrl, boolean z2) {
        anetwork.channel.entity.g gVar = this.f7253a.f7286a;
        RequestStatistic requestStatistic = gVar.f7230b;
        if (session == null && gVar.e() && !z2 && !NetworkStatusHelper.isProxy()) {
            session = sessionCenter.get(httpUrl, anet.channel.entity.c.f6778b, 0L);
        }
        if (session == null) {
            ALog.i(TAG, "create HttpSession with local DNS", this.f7253a.f7288c, new Object[0]);
            session = new anet.channel.session.d(GlobalAppRuntimeInfo.getContext(), new anet.channel.entity.a(StringUtils.concatString(httpUrl.scheme(), HttpConstant.SCHEME_SPLIT, httpUrl.host()), this.f7253a.f7288c, null));
        }
        if (requestStatistic.spdyRequestSend) {
            requestStatistic.degraded = 1;
        }
        ALog.i(TAG, "tryGetHttpSession", this.f7253a.f7288c, RtspHeaders.SESSION, session);
        return session;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x003c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private anet.channel.request.Request a(anet.channel.request.Request r7) {
        /*
            r6 = this;
            anetwork.channel.unified.j r0 = r6.f7253a
            anetwork.channel.entity.g r0 = r0.f7286a
            boolean r0 = r0.i()
            if (r0 == 0) goto L3c
            anetwork.channel.unified.j r0 = r6.f7253a
            anetwork.channel.entity.g r0 = r0.f7286a
            java.lang.String r0 = r0.g()
            java.lang.String r0 = anetwork.channel.cookie.CookieManager.getCookie(r0)
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 != 0) goto L3c
            anet.channel.request.Request$Builder r1 = r7.newBuilder()
            java.util.Map r2 = r7.getHeaders()
            java.lang.String r3 = "Cookie"
            java.lang.Object r2 = r2.get(r3)
            java.lang.String r2 = (java.lang.String) r2
            boolean r4 = android.text.TextUtils.isEmpty(r2)
            if (r4 != 0) goto L38
            java.lang.String r4 = "; "
            java.lang.String r0 = anet.channel.util.StringUtils.concatString(r2, r4, r0)
        L38:
            r1.addHeader(r3, r0)
            goto L3d
        L3c:
            r1 = 0
        L3d:
            anetwork.channel.cache.Cache$Entry r0 = r6.f7255c
            if (r0 == 0) goto L65
            if (r1 != 0) goto L47
            anet.channel.request.Request$Builder r1 = r7.newBuilder()
        L47:
            anetwork.channel.cache.Cache$Entry r0 = r6.f7255c
            java.lang.String r0 = r0.etag
            if (r0 == 0) goto L52
            java.lang.String r2 = "If-None-Match"
            r1.addHeader(r2, r0)
        L52:
            anetwork.channel.cache.Cache$Entry r0 = r6.f7255c
            long r2 = r0.lastModified
            r4 = 0
            int r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r0 <= 0) goto L65
            java.lang.String r0 = "If-Modified-Since"
            java.lang.String r2 = anetwork.channel.cache.a.a(r2)
            r1.addHeader(r0, r2)
        L65:
            anetwork.channel.unified.j r0 = r6.f7253a
            anetwork.channel.entity.g r0 = r0.f7286a
            int r0 = r0.f7229a
            if (r0 != 0) goto L82
            java.lang.String r0 = "weex"
            java.lang.String r2 = r6.f7257e
            boolean r0 = r0.equalsIgnoreCase(r2)
            if (r0 == 0) goto L82
            if (r1 != 0) goto L7d
            anet.channel.request.Request$Builder r1 = r7.newBuilder()
        L7d:
            r0 = 3000(0xbb8, float:4.204E-42)
            r1.setReadTimeout(r0)
        L82:
            if (r1 != 0) goto L85
            goto L89
        L85:
            anet.channel.request.Request r7 = r1.build()
        L89:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: anetwork.channel.unified.e.a(anet.channel.request.Request):anet.channel.request.Request");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Session session, Request request) {
        if (session == null || this.f7259g) {
            return;
        }
        Request requestA = a(request);
        RequestStatistic requestStatistic = this.f7253a.f7286a.f7230b;
        requestStatistic.reqStart = System.currentTimeMillis();
        this.f7258f = session.request(requestA, new i(this, requestA, requestStatistic));
    }
}
