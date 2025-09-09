package anet.channel.e;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import anet.channel.AwcnConfig;
import anet.channel.GlobalAppRuntimeInfo;
import anet.channel.status.NetworkStatusHelper;
import anet.channel.strategy.IConnStrategy;
import anet.channel.strategy.IStrategyFilter;
import anet.channel.strategy.IStrategyListener;
import anet.channel.strategy.StrategyCenter;
import anet.channel.thread.ThreadPoolExecutorFactory;
import anet.channel.util.ALog;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import org.android.spdy.SpdyAgent;
import org.android.spdy.SpdySessionKind;
import org.android.spdy.SpdyVersion;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private static b f6751a;

    /* renamed from: b, reason: collision with root package name */
    private static String f6752b;

    /* renamed from: f, reason: collision with root package name */
    private static SharedPreferences f6756f;

    /* renamed from: c, reason: collision with root package name */
    private static AtomicBoolean f6753c = new AtomicBoolean(false);

    /* renamed from: d, reason: collision with root package name */
    private static AtomicBoolean f6754d = new AtomicBoolean(false);

    /* renamed from: e, reason: collision with root package name */
    private static long f6755e = 21600000;

    /* renamed from: g, reason: collision with root package name */
    private static IStrategyFilter f6757g = new anet.channel.e.b();

    /* renamed from: h, reason: collision with root package name */
    private static AtomicInteger f6758h = new AtomicInteger(1);

    /* renamed from: i, reason: collision with root package name */
    private static IStrategyListener f6759i = new c();

    /* renamed from: j, reason: collision with root package name */
    private static NetworkStatusHelper.INetworkStatusChangeListener f6760j = new d();

    /* renamed from: anet.channel.e.a$a, reason: collision with other inner class name */
    private static class C0010a {

        /* renamed from: a, reason: collision with root package name */
        long f6761a;

        /* renamed from: b, reason: collision with root package name */
        boolean f6762b;

        private C0010a() {
        }

        /* synthetic */ C0010a(anet.channel.e.b bVar) {
            this();
        }
    }

    public static boolean b() {
        b bVar = f6751a;
        if (bVar != null) {
            return bVar.b(NetworkStatusHelper.getUniqueId(NetworkStatusHelper.getStatus()));
        }
        return false;
    }

    public static void a(NetworkStatusHelper.NetworkStatus networkStatus) {
        if (!AwcnConfig.isHttp3Enable()) {
            ALog.i("awcn.Http3ConnDetector", "startDetect", null, "http3 global config close.");
            return;
        }
        if (f6754d.get()) {
            ALog.e("awcn.Http3ConnDetector", "tnet exception.", null, new Object[0]);
            return;
        }
        if (NetworkStatusHelper.isConnected()) {
            if (TextUtils.isEmpty(f6752b)) {
                ALog.e("awcn.Http3ConnDetector", "startDetect", null, "host is null");
                return;
            }
            List<IConnStrategy> connStrategyListByHost = StrategyCenter.getInstance().getConnStrategyListByHost(f6752b, f6757g);
            if (connStrategyListByHost.isEmpty()) {
                ALog.e("awcn.Http3ConnDetector", "startDetect", null, "http3 strategy is null.");
                return;
            }
            if (f6753c.compareAndSet(false, true)) {
                try {
                    long jCurrentTimeMillis = System.currentTimeMillis();
                    SpdyAgent.getInstance(GlobalAppRuntimeInfo.getContext(), SpdyVersion.SPDY3, SpdySessionKind.NONE_SESSION).InitializeSecurityStuff();
                    ALog.e("awcn.Http3ConnDetector", "tnet init http3.", null, "cost", Long.valueOf(System.currentTimeMillis() - jCurrentTimeMillis));
                } catch (Throwable th) {
                    ALog.e("awcn.Http3ConnDetector", "tnet init http3 error.", null, th, new Object[0]);
                    f6754d.set(true);
                    return;
                }
            }
            if (f6751a == null) {
                f6751a = new b();
            }
            if (f6751a.a(NetworkStatusHelper.getUniqueId(networkStatus))) {
                ThreadPoolExecutorFactory.submitDetectTask(new e(connStrategyListByHost, networkStatus));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static IConnStrategy b(IConnStrategy iConnStrategy) {
        return new g(iConnStrategy);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class b {

        /* renamed from: a, reason: collision with root package name */
        private Map<String, C0010a> f6763a = new ConcurrentHashMap();

        b() throws JSONException {
            a();
        }

        private void a() throws JSONException {
            anet.channel.e.b bVar = null;
            String string = a.f6756f.getString("networksdk_http3_history_records", null);
            if (TextUtils.isEmpty(string)) {
                return;
            }
            try {
                JSONArray jSONArray = new JSONArray(string);
                for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                    JSONObject jSONObject = (JSONObject) jSONArray.get(i2);
                    C0010a c0010a = new C0010a(bVar);
                    String string2 = jSONObject.getString("networkUniqueId");
                    c0010a.f6761a = jSONObject.getLong("time");
                    c0010a.f6762b = jSONObject.getBoolean("enable");
                    if (a(c0010a.f6761a)) {
                        synchronized (this.f6763a) {
                            this.f6763a.put(string2, c0010a);
                        }
                    }
                }
            } catch (JSONException unused) {
            }
        }

        boolean b(String str) {
            synchronized (this.f6763a) {
                try {
                    C0010a c0010a = this.f6763a.get(str);
                    if (c0010a == null) {
                        return false;
                    }
                    return c0010a.f6762b;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        boolean a(String str) {
            synchronized (this.f6763a) {
                try {
                    if (this.f6763a.get(str) == null) {
                        return true;
                    }
                    return !a(r5.f6761a);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        private boolean a(long j2) {
            return System.currentTimeMillis() - j2 < a.f6755e;
        }

        void a(String str, boolean z2) {
            C0010a c0010a = new C0010a(null);
            c0010a.f6762b = z2;
            c0010a.f6761a = System.currentTimeMillis();
            JSONArray jSONArray = new JSONArray();
            synchronized (this.f6763a) {
                this.f6763a.put(str, c0010a);
                for (Map.Entry<String, C0010a> entry : this.f6763a.entrySet()) {
                    String key = entry.getKey();
                    C0010a value = entry.getValue();
                    try {
                        JSONObject jSONObject = new JSONObject();
                        jSONObject.put("networkUniqueId", key);
                        jSONObject.put("time", value.f6761a);
                        jSONObject.put("enable", value.f6762b);
                        jSONArray.put(jSONObject);
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                }
            }
            a.f6756f.edit().putString("networksdk_http3_history_records", jSONArray.toString()).apply();
        }
    }

    public static void a() {
        try {
            ALog.e("awcn.Http3ConnDetector", "registerListener", null, "http3Enable", Boolean.valueOf(AwcnConfig.isHttp3Enable()));
            SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(GlobalAppRuntimeInfo.getContext());
            f6756f = defaultSharedPreferences;
            f6752b = defaultSharedPreferences.getString("http3_detector_host", "");
            a(NetworkStatusHelper.getStatus());
            NetworkStatusHelper.addStatusChangeListener(f6760j);
            StrategyCenter.getInstance().registerListener(f6759i);
        } catch (Exception e2) {
            ALog.e("awcn.Http3ConnDetector", "[registerListener]error", null, e2, new Object[0]);
        }
    }

    public static void a(long j2) {
        if (j2 < 0) {
            return;
        }
        f6755e = j2;
    }

    public static void a(boolean z2) {
        b bVar = f6751a;
        if (bVar != null) {
            bVar.a(NetworkStatusHelper.getUniqueId(NetworkStatusHelper.getStatus()), z2);
        }
    }
}
