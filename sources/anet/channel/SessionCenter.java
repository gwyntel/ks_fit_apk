package anet.channel;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.LruCache;
import anet.channel.Config;
import anet.channel.detect.n;
import anet.channel.entity.ConnType;
import anet.channel.entity.ENV;
import anet.channel.status.NetworkStatusHelper;
import anet.channel.strategy.ConnProtocol;
import anet.channel.strategy.IStrategyListener;
import anet.channel.strategy.StrategyCenter;
import anet.channel.strategy.dispatch.AmdcRuntimeInfo;
import anet.channel.strategy.l;
import anet.channel.util.ALog;
import anet.channel.util.AppLifecycle;
import anet.channel.util.HttpConstant;
import anet.channel.util.HttpUrl;
import anet.channel.util.StringUtils;
import anet.channel.util.Utils;
import anetwork.channel.util.RequestConstant;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.umeng.analytics.pro.bc;
import java.net.ConnectException;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import org.android.spdy.SpdyAgent;
import org.android.spdy.SpdySessionKind;
import org.android.spdy.SpdyVersion;

/* loaded from: classes2.dex */
public class SessionCenter {
    public static final String TAG = "awcn.SessionCenter";

    /* renamed from: a, reason: collision with root package name */
    static Map<Config, SessionCenter> f6643a = new HashMap();

    /* renamed from: j, reason: collision with root package name */
    private static boolean f6644j = false;

    /* renamed from: b, reason: collision with root package name */
    Context f6645b;

    /* renamed from: c, reason: collision with root package name */
    String f6646c;

    /* renamed from: d, reason: collision with root package name */
    Config f6647d;

    /* renamed from: e, reason: collision with root package name */
    final e f6648e = new e();

    /* renamed from: f, reason: collision with root package name */
    final LruCache<String, SessionRequest> f6649f = new LruCache<>(32);

    /* renamed from: g, reason: collision with root package name */
    final c f6650g = new c();

    /* renamed from: h, reason: collision with root package name */
    final AccsSessionManager f6651h;

    /* renamed from: i, reason: collision with root package name */
    final a f6652i;

    private SessionCenter(Config config) {
        a aVar = new a(this, null);
        this.f6652i = aVar;
        this.f6645b = GlobalAppRuntimeInfo.getContext();
        this.f6647d = config;
        this.f6646c = config.getAppkey();
        aVar.a();
        this.f6651h = new AccsSessionManager(this);
        if (config.getAppkey().equals("[default]")) {
            return;
        }
        AmdcRuntimeInfo.setSign(new d(this, config.getAppkey(), config.getSecurity()));
    }

    public static void checkAndStartAccsSession() {
        Iterator<SessionCenter> it = f6643a.values().iterator();
        while (it.hasNext()) {
            it.next().f6651h.checkAndStartSession();
        }
    }

    public static synchronized SessionCenter getInstance(String str) {
        Config configByTag;
        configByTag = Config.getConfigByTag(str);
        if (configByTag == null) {
            throw new RuntimeException("tag not exist!");
        }
        return getInstance(configByTag);
    }

    public static synchronized void init(Context context) {
        try {
            if (context == null) {
                ALog.e(TAG, "context is null!", null, new Object[0]);
                throw new NullPointerException("init failed. context is null");
            }
            GlobalAppRuntimeInfo.setContext(context.getApplicationContext());
            if (!f6644j) {
                Map<Config, SessionCenter> map = f6643a;
                Config config = Config.DEFAULT_CONFIG;
                map.put(config, new SessionCenter(config));
                AppLifecycle.initialize();
                NetworkStatusHelper.startListener(context);
                if (!AwcnConfig.isTbNextLaunch()) {
                    StrategyCenter.getInstance().initialize(GlobalAppRuntimeInfo.getContext());
                }
                if (GlobalAppRuntimeInfo.isTargetProcess()) {
                    n.a();
                    anet.channel.e.a.a();
                }
                f6644j = true;
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public static synchronized void switchEnvironment(ENV env) {
        synchronized (SessionCenter.class) {
            try {
                if (GlobalAppRuntimeInfo.getEnv() != env) {
                    ALog.i(TAG, "switch env", null, "old", GlobalAppRuntimeInfo.getEnv(), "new", env);
                    GlobalAppRuntimeInfo.setEnv(env);
                    StrategyCenter.getInstance().switchEnv();
                    SpdyAgent.getInstance(GlobalAppRuntimeInfo.getContext(), SpdyVersion.SPDY3, SpdySessionKind.NONE_SESSION).switchAccsServer(env == ENV.TEST ? 0 : 1);
                }
                Iterator<Map.Entry<Config, SessionCenter>> it = f6643a.entrySet().iterator();
                while (it.hasNext()) {
                    SessionCenter value = it.next().getValue();
                    if (value.f6647d.getEnv() != env) {
                        ALog.i(TAG, "remove instance", value.f6646c, RequestConstant.ENVIRONMENT, value.f6647d.getEnv());
                        value.f6651h.forceCloseSession(false);
                        value.f6652i.b();
                        it.remove();
                    }
                }
            } finally {
            }
        }
    }

    public void asyncGet(HttpUrl httpUrl, int i2, long j2, SessionGetCallback sessionGetCallback) {
        if (sessionGetCallback == null) {
            throw new NullPointerException("cb is null");
        }
        if (j2 <= 0) {
            throw new InvalidParameterException("timeout must > 0");
        }
        try {
            b(httpUrl, i2, j2, sessionGetCallback);
        } catch (Exception unused) {
            sessionGetCallback.onSessionGetFail();
        }
    }

    protected void b(HttpUrl httpUrl, int i2, long j2, SessionGetCallback sessionGetCallback) throws Exception {
        SessionInfo sessionInfoB;
        if (!f6644j) {
            ALog.e(TAG, "getInternal not inited!", this.f6646c, new Object[0]);
            throw new IllegalStateException("getInternal not inited");
        }
        if (httpUrl == null) {
            throw new InvalidParameterException("httpUrl is null");
        }
        if (sessionGetCallback == null) {
            throw new InvalidParameterException("sessionGetCallback is null");
        }
        ALog.d(TAG, "getInternal", this.f6646c, bc.aN, httpUrl.urlString(), "sessionType", i2 == anet.channel.entity.c.f6777a ? "LongLink" : "ShortLink", "timeout", Long.valueOf(j2));
        SessionRequest sessionRequestA = a(httpUrl);
        Session sessionA = this.f6648e.a(sessionRequestA, i2);
        if (sessionA != null) {
            ALog.d(TAG, "get internal hit cache session", this.f6646c, "session", sessionA);
            sessionGetCallback.onSessionGetSuccess(sessionA);
            return;
        }
        if (this.f6647d == Config.DEFAULT_CONFIG && i2 != anet.channel.entity.c.f6778b) {
            sessionGetCallback.onSessionGetFail();
            return;
        }
        if (GlobalAppRuntimeInfo.isAppBackground() && i2 == anet.channel.entity.c.f6777a && AwcnConfig.isAccsSessionCreateForbiddenInBg() && (sessionInfoB = this.f6650g.b(httpUrl.host())) != null && sessionInfoB.isAccs) {
            ALog.w(TAG, "app background, forbid to create accs session", this.f6646c, new Object[0]);
            throw new ConnectException("accs session connecting forbidden in background");
        }
        sessionRequestA.b(this.f6645b, i2, anet.channel.util.i.a(this.f6646c), sessionGetCallback, j2);
    }

    @Deprecated
    public void enterBackground() {
        AppLifecycle.onBackground();
    }

    @Deprecated
    public void enterForeground() {
        AppLifecycle.onForeground();
    }

    public void forceRecreateAccsSession() {
        this.f6651h.forceCloseSession(true);
    }

    public Session get(String str, long j2) {
        return get(HttpUrl.parse(str), anet.channel.entity.c.f6779c, j2);
    }

    public Session getThrowsException(String str, long j2) throws Exception {
        return a(HttpUrl.parse(str), anet.channel.entity.c.f6779c, j2, null);
    }

    public void registerAccsSessionListener(ISessionListener iSessionListener) {
        this.f6651h.registerListener(iSessionListener);
    }

    public void registerPublicKey(String str, int i2) {
        this.f6650g.a(str, i2);
    }

    public void registerSessionInfo(SessionInfo sessionInfo) {
        this.f6650g.a(sessionInfo);
        if (sessionInfo.isKeepAlive) {
            this.f6651h.checkAndStartSession();
        }
    }

    @Deprecated
    public synchronized void switchEnv(ENV env) {
        switchEnvironment(env);
    }

    public void unregisterAccsSessionListener(ISessionListener iSessionListener) {
        this.f6651h.unregisterListener(iSessionListener);
    }

    public void unregisterSessionInfo(String str) {
        SessionInfo sessionInfoA = this.f6650g.a(str);
        if (sessionInfoA == null || !sessionInfoA.isKeepAlive) {
            return;
        }
        this.f6651h.checkAndStartSession();
    }

    private class a implements NetworkStatusHelper.INetworkStatusChangeListener, IStrategyListener, AppLifecycle.AppLifecycleListener {

        /* renamed from: a, reason: collision with root package name */
        boolean f6653a;

        private a() {
            this.f6653a = false;
        }

        void a() {
            AppLifecycle.registerLifecycleListener(this);
            NetworkStatusHelper.addStatusChangeListener(this);
            StrategyCenter.getInstance().registerListener(this);
        }

        void b() {
            StrategyCenter.getInstance().unregisterListener(this);
            AppLifecycle.unregisterLifecycleListener(this);
            NetworkStatusHelper.removeStatusChangeListener(this);
        }

        @Override // anet.channel.util.AppLifecycle.AppLifecycleListener
        public void background() {
            ALog.i(SessionCenter.TAG, "[background]", SessionCenter.this.f6646c, new Object[0]);
            if (!SessionCenter.f6644j) {
                ALog.e(SessionCenter.TAG, "background not inited!", SessionCenter.this.f6646c, new Object[0]);
                return;
            }
            try {
                StrategyCenter.getInstance().saveData();
                if (AwcnConfig.isAccsSessionCreateForbiddenInBg() && "OPPO".equalsIgnoreCase(Build.BRAND)) {
                    ALog.i(SessionCenter.TAG, "close session for OPPO", SessionCenter.this.f6646c, new Object[0]);
                    SessionCenter.this.f6651h.forceCloseSession(false);
                }
            } catch (Exception unused) {
            }
        }

        @Override // anet.channel.util.AppLifecycle.AppLifecycleListener
        public void forground() {
            ALog.i(SessionCenter.TAG, "[forground]", SessionCenter.this.f6646c, new Object[0]);
            if (SessionCenter.this.f6645b == null || this.f6653a) {
                return;
            }
            this.f6653a = true;
            try {
                if (!SessionCenter.f6644j) {
                    ALog.e(SessionCenter.TAG, "forground not inited!", SessionCenter.this.f6646c, new Object[0]);
                    return;
                }
                try {
                    if (AppLifecycle.lastEnterBackgroundTime == 0 || System.currentTimeMillis() - AppLifecycle.lastEnterBackgroundTime <= 60000) {
                        SessionCenter.this.f6651h.checkAndStartSession();
                    } else {
                        SessionCenter.this.f6651h.forceCloseSession(true);
                    }
                } catch (Exception unused) {
                } catch (Throwable th) {
                    this.f6653a = false;
                    throw th;
                }
                this.f6653a = false;
            } catch (Exception unused2) {
            }
        }

        @Override // anet.channel.status.NetworkStatusHelper.INetworkStatusChangeListener
        public void onNetworkStatusChanged(NetworkStatusHelper.NetworkStatus networkStatus) {
            ALog.e(SessionCenter.TAG, "onNetworkStatusChanged.", SessionCenter.this.f6646c, "networkStatus", networkStatus);
            List<SessionRequest> listA = SessionCenter.this.f6648e.a();
            if (!listA.isEmpty()) {
                for (SessionRequest sessionRequest : listA) {
                    ALog.d(SessionCenter.TAG, "network change, try recreate session", SessionCenter.this.f6646c, new Object[0]);
                    sessionRequest.a((String) null);
                }
            }
            SessionCenter.this.f6651h.checkAndStartSession();
        }

        @Override // anet.channel.strategy.IStrategyListener
        public void onStrategyUpdated(l.d dVar) {
            SessionCenter.this.a(dVar);
            SessionCenter.this.f6651h.checkAndStartSession();
        }

        /* synthetic */ a(SessionCenter sessionCenter, d dVar) {
            this();
        }
    }

    @Deprecated
    public Session get(String str, ConnType.TypeLevel typeLevel, long j2) {
        return get(HttpUrl.parse(str), typeLevel == ConnType.TypeLevel.SPDY ? anet.channel.entity.c.f6777a : anet.channel.entity.c.f6778b, j2);
    }

    @Deprecated
    public Session getThrowsException(String str, ConnType.TypeLevel typeLevel, long j2) throws Exception {
        return a(HttpUrl.parse(str), typeLevel == ConnType.TypeLevel.SPDY ? anet.channel.entity.c.f6777a : anet.channel.entity.c.f6778b, j2, null);
    }

    private SessionRequest a(HttpUrl httpUrl) {
        String cNameByHost = StrategyCenter.getInstance().getCNameByHost(httpUrl.host());
        if (cNameByHost == null) {
            cNameByHost = httpUrl.host();
        }
        String strScheme = httpUrl.scheme();
        if (!httpUrl.isSchemeLocked()) {
            strScheme = StrategyCenter.getInstance().getSchemeByHost(cNameByHost, strScheme);
        }
        return a(StringUtils.concatString(strScheme, HttpConstant.SCHEME_SPLIT, cNameByHost));
    }

    @Deprecated
    public Session get(HttpUrl httpUrl, ConnType.TypeLevel typeLevel, long j2) {
        return get(httpUrl, typeLevel == ConnType.TypeLevel.SPDY ? anet.channel.entity.c.f6777a : anet.channel.entity.c.f6778b, j2);
    }

    public Session getThrowsException(HttpUrl httpUrl, int i2, long j2) throws Exception {
        return a(httpUrl, i2, j2, null);
    }

    public Session get(HttpUrl httpUrl, int i2, long j2) {
        try {
            return a(httpUrl, i2, j2, null);
        } catch (NoAvailStrategyException e2) {
            ALog.i(TAG, "[Get]" + e2.getMessage(), this.f6646c, null, "url", httpUrl.urlString());
            return null;
        } catch (ConnectException e3) {
            ALog.e(TAG, "[Get]connect exception", this.f6646c, AlinkConstants.KEY_ERR_MSG, e3.getMessage(), "url", httpUrl.urlString());
            return null;
        } catch (InvalidParameterException e4) {
            ALog.e(TAG, "[Get]param url is invalid", this.f6646c, e4, "url", httpUrl);
            return null;
        } catch (TimeoutException e5) {
            ALog.e(TAG, "[Get]timeout exception", this.f6646c, e5, "url", httpUrl.urlString());
            return null;
        } catch (Exception e6) {
            ALog.e(TAG, "[Get]" + e6.getMessage(), this.f6646c, null, "url", httpUrl.urlString());
            return null;
        }
    }

    @Deprecated
    public Session getThrowsException(HttpUrl httpUrl, ConnType.TypeLevel typeLevel, long j2) throws Exception {
        return a(httpUrl, typeLevel == ConnType.TypeLevel.SPDY ? anet.channel.entity.c.f6777a : anet.channel.entity.c.f6778b, j2, null);
    }

    public static synchronized SessionCenter getInstance(Config config) {
        SessionCenter sessionCenter;
        Context appContext;
        try {
            if (config != null) {
                if (!f6644j && (appContext = Utils.getAppContext()) != null) {
                    init(appContext);
                }
                sessionCenter = f6643a.get(config);
                if (sessionCenter == null) {
                    sessionCenter = new SessionCenter(config);
                    f6643a.put(config, sessionCenter);
                }
            } else {
                throw new NullPointerException("config is null!");
            }
        } catch (Throwable th) {
            throw th;
        }
        return sessionCenter;
    }

    protected Session a(HttpUrl httpUrl, int i2, long j2, SessionGetCallback sessionGetCallback) throws Exception {
        SessionInfo sessionInfoB;
        if (!f6644j) {
            ALog.e(TAG, "getInternal not inited!", this.f6646c, new Object[0]);
            throw new IllegalStateException("getInternal not inited");
        }
        if (httpUrl != null) {
            ALog.d(TAG, "getInternal", this.f6646c, bc.aN, httpUrl.urlString(), "sessionType", i2 == anet.channel.entity.c.f6777a ? "LongLink" : "ShortLink", "timeout", Long.valueOf(j2));
            SessionRequest sessionRequestA = a(httpUrl);
            Session sessionA = this.f6648e.a(sessionRequestA, i2);
            if (sessionA != null) {
                ALog.d(TAG, "get internal hit cache session", this.f6646c, "session", sessionA);
            } else {
                if (this.f6647d == Config.DEFAULT_CONFIG && i2 != anet.channel.entity.c.f6778b) {
                    if (sessionGetCallback == null) {
                        return null;
                    }
                    sessionGetCallback.onSessionGetFail();
                    return null;
                }
                if (GlobalAppRuntimeInfo.isAppBackground() && i2 == anet.channel.entity.c.f6777a && AwcnConfig.isAccsSessionCreateForbiddenInBg() && (sessionInfoB = this.f6650g.b(httpUrl.host())) != null && sessionInfoB.isAccs) {
                    ALog.w(TAG, "app background, forbid to create accs session", this.f6646c, new Object[0]);
                    throw new ConnectException("accs session connecting forbidden in background");
                }
                sessionRequestA.a(this.f6645b, i2, anet.channel.util.i.a(this.f6646c), sessionGetCallback, j2);
                if (sessionGetCallback == null && j2 > 0 && (i2 == anet.channel.entity.c.f6779c || sessionRequestA.b() == i2)) {
                    sessionRequestA.a(j2);
                    sessionA = this.f6648e.a(sessionRequestA, i2);
                    if (sessionA == null) {
                        throw new ConnectException("session connecting failed or timeout");
                    }
                }
            }
            return sessionA;
        }
        throw new InvalidParameterException("httpUrl is null");
    }

    @Deprecated
    public static synchronized SessionCenter getInstance() {
        Context appContext;
        try {
            if (!f6644j && (appContext = Utils.getAppContext()) != null) {
                init(appContext);
            }
            SessionCenter sessionCenter = null;
            for (Map.Entry<Config, SessionCenter> entry : f6643a.entrySet()) {
                SessionCenter value = entry.getValue();
                if (entry.getKey() != Config.DEFAULT_CONFIG) {
                    return value;
                }
                sessionCenter = value;
            }
            return sessionCenter;
        } catch (Throwable th) {
            throw th;
        }
    }

    @Deprecated
    public static synchronized void init(Context context, String str) {
        init(context, str, GlobalAppRuntimeInfo.getEnv());
    }

    public static synchronized void init(Context context, String str, ENV env) {
        try {
            if (context != null) {
                Config config = Config.getConfig(str, env);
                if (config == null) {
                    config = new Config.Builder().setAppkey(str).setEnv(env).build();
                }
                init(context, config);
            } else {
                ALog.e(TAG, "context is null!", null, new Object[0]);
                throw new NullPointerException("init failed. context is null");
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    private void b(l.b bVar) {
        int i2;
        ALog.i(TAG, "find effectNow", this.f6646c, "host", bVar.f7029a);
        l.a[] aVarArr = bVar.f7036h;
        String[] strArr = bVar.f7034f;
        for (Session session : this.f6648e.a(a(StringUtils.buildKey(bVar.f7031c, bVar.f7029a)))) {
            if (!session.getConnType().isHttpType()) {
                int i3 = 0;
                while (true) {
                    if (i3 < strArr.length) {
                        if (session.getIp().equals(strArr[i3])) {
                            while (true) {
                                if (i2 < aVarArr.length) {
                                    i2 = (session.getPort() == aVarArr[i2].f7021a && session.getConnType().equals(ConnType.valueOf(ConnProtocol.valueOf(aVarArr[i2])))) ? 0 : i2 + 1;
                                } else {
                                    if (ALog.isPrintLog(2)) {
                                        ALog.i(TAG, "aisle not match", session.f6632p, "port", Integer.valueOf(session.getPort()), "connType", session.getConnType(), "aisle", Arrays.toString(aVarArr));
                                    }
                                    session.close(true);
                                }
                            }
                        } else {
                            i3++;
                        }
                    } else {
                        if (ALog.isPrintLog(2)) {
                            ALog.i(TAG, "ip not match", session.f6632p, "session ip", session.getIp(), "ips", Arrays.toString(strArr));
                        }
                        session.close(true);
                    }
                }
            }
        }
    }

    public static synchronized void init(Context context, Config config) {
        if (context == null) {
            ALog.e(TAG, "context is null!", null, new Object[0]);
            throw new NullPointerException("init failed. context is null");
        }
        if (config != null) {
            init(context);
            if (!f6643a.containsKey(config)) {
                f6643a.put(config, new SessionCenter(config));
            }
        } else {
            ALog.e(TAG, "paramter config is null!", null, new Object[0]);
            throw new NullPointerException("init failed. config is null");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(l.d dVar) {
        try {
            for (l.b bVar : dVar.f7044b) {
                if (bVar.f7039k) {
                    b(bVar);
                }
                if (bVar.f7033e != null) {
                    a(bVar);
                }
            }
        } catch (Exception e2) {
            ALog.e(TAG, "checkStrategy failed", this.f6646c, e2, new Object[0]);
        }
    }

    private void a(l.b bVar) {
        for (Session session : this.f6648e.a(a(StringUtils.buildKey(bVar.f7031c, bVar.f7029a)))) {
            if (!StringUtils.isStringEqual(session.f6628l, bVar.f7033e)) {
                ALog.i(TAG, "unit change", session.f6632p, "session unit", session.f6628l, "unit", bVar.f7033e);
                session.close(true);
            }
        }
    }

    protected SessionRequest a(String str) {
        SessionRequest sessionRequest;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        synchronized (this.f6649f) {
            try {
                sessionRequest = this.f6649f.get(str);
                if (sessionRequest == null) {
                    sessionRequest = new SessionRequest(str, this);
                    this.f6649f.put(str, sessionRequest);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return sessionRequest;
    }
}
