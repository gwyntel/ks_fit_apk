package com.taobao.accs.client;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.net.ConnectivityManager;
import android.text.TextUtils;
import androidx.annotation.Keep;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.taobao.accs.IAgooAppReceiver;
import com.taobao.accs.IAppReceiver;
import com.taobao.accs.ILoginInfo;
import com.taobao.accs.base.AccsAbstractDataListener;
import com.taobao.accs.base.AccsDataListener;
import com.taobao.accs.common.ThreadPoolExecutorFactory;
import com.taobao.accs.utl.ALog;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.android.agoo.common.AgooConstants;

@Keep
/* loaded from: classes4.dex */
public class GlobalClientInfo {
    public static final String AGOO_SERVICE_ID = "agooSend";

    /* renamed from: a, reason: collision with root package name */
    public static Context f20065a = null;

    /* renamed from: b, reason: collision with root package name */
    public static IAgooAppReceiver f20066b = null;

    /* renamed from: c, reason: collision with root package name */
    public static String f20067c = null;

    /* renamed from: d, reason: collision with root package name */
    public static boolean f20068d = false;

    /* renamed from: e, reason: collision with root package name */
    private static final String f20069e = "com.taobao.accs.client.GlobalClientInfo";

    /* renamed from: f, reason: collision with root package name */
    private static volatile GlobalClientInfo f20070f;

    /* renamed from: l, reason: collision with root package name */
    private static Map<String, String> f20071l = new ConcurrentHashMap();

    /* renamed from: m, reason: collision with root package name */
    private static Map<String, Map<String, String>> f20072m = new ConcurrentHashMap();

    /* renamed from: g, reason: collision with root package name */
    private ConcurrentHashMap<String, ILoginInfo> f20073g;

    /* renamed from: h, reason: collision with root package name */
    private ConcurrentHashMap<String, IAppReceiver> f20074h;

    /* renamed from: i, reason: collision with root package name */
    private ActivityManager f20075i;

    /* renamed from: j, reason: collision with root package name */
    private ConnectivityManager f20076j;

    /* renamed from: k, reason: collision with root package name */
    private PackageInfo f20077k;

    /* renamed from: n, reason: collision with root package name */
    private Map<String, AccsDataListener> f20078n = new ConcurrentHashMap();

    static {
        f20071l.put(AGOO_SERVICE_ID, "org.android.agoo.accs.AgooService");
        f20071l.put(AgooConstants.AGOO_SERVICE_AGOOACK, "org.android.agoo.accs.AgooService");
        f20071l.put("agooTokenReport", "org.android.agoo.accs.AgooService");
    }

    private GlobalClientInfo(Context context) {
        Context context2 = getContext();
        f20065a = context2;
        if (context2 == null && context != null) {
            f20065a = context.getApplicationContext();
        }
        ThreadPoolExecutorFactory.execute(new c(this));
    }

    private void a(String str, Map<String, String> map) {
        if (map == null) {
            return;
        }
        if (f20072m.get(str) == null) {
            f20072m.put(str, new ConcurrentHashMap());
        }
        f20072m.get(str).putAll(map);
    }

    public static Context getContext() {
        return f20065a;
    }

    @Keep
    public static GlobalClientInfo getInstance(Context context) {
        if (f20070f == null) {
            synchronized (GlobalClientInfo.class) {
                try {
                    if (f20070f == null) {
                        f20070f = new GlobalClientInfo(context);
                    }
                } finally {
                }
            }
        }
        return f20070f;
    }

    public void clearLoginInfoImpl() {
        this.f20073g = null;
    }

    public ActivityManager getActivityManager() {
        if (this.f20075i == null) {
            this.f20075i = (ActivityManager) f20065a.getSystemService(PushConstants.INTENT_ACTIVITY_NAME);
        }
        return this.f20075i;
    }

    public Map<String, String> getAllService(String str) {
        if (f20072m.get(str) == null || f20072m.get(str).isEmpty()) {
            return null;
        }
        return f20072m.get(str);
    }

    public Map<String, IAppReceiver> getAppReceiver() {
        return this.f20074h;
    }

    public ConnectivityManager getConnectivityManager() {
        if (this.f20076j == null) {
            this.f20076j = (ConnectivityManager) f20065a.getSystemService("connectivity");
        }
        return this.f20076j;
    }

    public AccsDataListener getListener(String str) {
        return this.f20078n.get(str);
    }

    public String getNick(String str) {
        ILoginInfo iLoginInfo;
        ConcurrentHashMap<String, ILoginInfo> concurrentHashMap = this.f20073g;
        if (concurrentHashMap == null || (iLoginInfo = concurrentHashMap.get(str)) == null) {
            return null;
        }
        return iLoginInfo.getNick();
    }

    public PackageInfo getPackageInfo() {
        try {
            if (this.f20077k == null) {
                this.f20077k = f20065a.getPackageManager().getPackageInfo(f20065a.getPackageName(), 0);
            }
        } catch (Throwable th) {
            ALog.e("GlobalClientInfo", "getPackageInfo", th, new Object[0]);
        }
        return this.f20077k;
    }

    public String getService(String str) {
        return f20071l.get(str);
    }

    public String getSid(String str) {
        ILoginInfo iLoginInfo;
        ConcurrentHashMap<String, ILoginInfo> concurrentHashMap = this.f20073g;
        if (concurrentHashMap == null || (iLoginInfo = concurrentHashMap.get(str)) == null) {
            return null;
        }
        return iLoginInfo.getSid();
    }

    public String getUserId(String str) {
        ILoginInfo iLoginInfo;
        ConcurrentHashMap<String, ILoginInfo> concurrentHashMap = this.f20073g;
        if (concurrentHashMap == null || (iLoginInfo = concurrentHashMap.get(str)) == null) {
            return null;
        }
        return iLoginInfo.getUserId();
    }

    public void registerAllRemoteService(String str, Map<String, String> map) {
        if (f20072m.get(str) == null) {
            f20072m.put(str, new ConcurrentHashMap());
        }
        f20072m.get(str).putAll(map);
    }

    public void registerListener(String str, AccsAbstractDataListener accsAbstractDataListener) {
        registerListener(str, (AccsDataListener) accsAbstractDataListener);
    }

    @Keep
    public void registerRemoteListener(String str, AccsDataListener accsDataListener) {
        this.f20078n.put(str, accsDataListener);
    }

    public void registerRemoteService(String str, String str2) {
        f20071l.put(str, str2);
    }

    public void registerService(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return;
        }
        f20071l.put(str, str2);
    }

    public void setAppReceiver(String str, IAppReceiver iAppReceiver) {
        if (iAppReceiver != null) {
            if (iAppReceiver instanceof IAgooAppReceiver) {
                f20066b = (IAgooAppReceiver) iAppReceiver;
                return;
            }
            if (this.f20074h == null) {
                this.f20074h = new ConcurrentHashMap<>(2);
            }
            this.f20074h.put(str, iAppReceiver);
            a(str, iAppReceiver.getAllServices());
        }
    }

    public void setLoginInfoImpl(String str, ILoginInfo iLoginInfo) {
        if (this.f20073g == null) {
            this.f20073g = new ConcurrentHashMap<>(1);
        }
        if (iLoginInfo != null) {
            this.f20073g.put(str, iLoginInfo);
        }
    }

    @Keep
    public void setRemoteAgooAppReceiver(IAgooAppReceiver iAgooAppReceiver) {
        f20066b = iAgooAppReceiver;
    }

    @Keep
    public void setRemoteAppReceiver(String str, IAppReceiver iAppReceiver) {
        if (this.f20074h == null) {
            this.f20074h = new ConcurrentHashMap<>(2);
        }
        this.f20074h.put(str, iAppReceiver);
        a(str, iAppReceiver.getAllServices());
    }

    public void unRegisterService(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        f20071l.remove(str);
    }

    public void unregisterListener(String str) {
        this.f20078n.remove(str);
    }

    public void unregisterRemoteListener(String str) {
        this.f20078n.remove(str);
    }

    public void unregisterRemoteService(String str) {
        f20071l.remove(str);
    }

    public String getService(String str, String str2) {
        if (f20072m.get(str) != null) {
            return f20072m.get(str).get(str2);
        }
        return null;
    }

    public void registerListener(String str, AccsDataListener accsDataListener) {
        if (TextUtils.isEmpty(str) || accsDataListener == null) {
            return;
        }
        this.f20078n.put(str, accsDataListener);
    }
}
