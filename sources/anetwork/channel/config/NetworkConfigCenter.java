package anetwork.channel.config;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import androidx.webkit.ProxyConfig;
import anet.channel.AwcnConfig;
import anet.channel.GlobalAppRuntimeInfo;
import anet.channel.statist.RequestStatistic;
import anet.channel.strategy.dispatch.HttpDispatcher;
import anet.channel.strategy.utils.c;
import anet.channel.thread.ThreadPoolExecutorFactory;
import anet.channel.util.ALog;
import anet.channel.util.HttpUrl;
import anetwork.channel.cache.CacheManager;
import anetwork.channel.http.NetworkSdkSetting;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class NetworkConfigCenter {
    public static final String SERVICE_OPTIMIZE = "SERVICE_OPTIMIZE";
    public static final String SESSION_ASYNC_OPTIMIZE = "SESSION_ASYNC_OPTIMIZE";

    /* renamed from: a, reason: collision with root package name */
    private static volatile boolean f7144a = true;

    /* renamed from: b, reason: collision with root package name */
    private static volatile boolean f7145b = true;

    /* renamed from: c, reason: collision with root package name */
    private static volatile boolean f7146c = true;

    /* renamed from: d, reason: collision with root package name */
    private static volatile int f7147d = 5;

    /* renamed from: e, reason: collision with root package name */
    private static volatile boolean f7148e = true;

    /* renamed from: f, reason: collision with root package name */
    private static volatile boolean f7149f = true;

    /* renamed from: g, reason: collision with root package name */
    private static volatile boolean f7150g = false;

    /* renamed from: h, reason: collision with root package name */
    private static volatile long f7151h = 0;

    /* renamed from: i, reason: collision with root package name */
    private static volatile boolean f7152i = false;

    /* renamed from: j, reason: collision with root package name */
    private static volatile ConcurrentHashMap<String, List<String>> f7153j;

    /* renamed from: k, reason: collision with root package name */
    private static volatile CopyOnWriteArrayList<String> f7154k;

    /* renamed from: l, reason: collision with root package name */
    private static final List<String> f7155l = new ArrayList();

    /* renamed from: m, reason: collision with root package name */
    private static volatile int f7156m = 10000;

    /* renamed from: n, reason: collision with root package name */
    private static volatile boolean f7157n = true;

    /* renamed from: o, reason: collision with root package name */
    private static volatile boolean f7158o = false;

    /* renamed from: p, reason: collision with root package name */
    private static volatile int f7159p = 60000;

    /* renamed from: q, reason: collision with root package name */
    private static volatile CopyOnWriteArrayList<String> f7160q = null;

    /* renamed from: r, reason: collision with root package name */
    private static volatile ConcurrentHashMap<String, List<String>> f7161r = null;

    /* renamed from: s, reason: collision with root package name */
    private static volatile boolean f7162s = true;

    /* renamed from: t, reason: collision with root package name */
    private static volatile boolean f7163t = false;

    /* renamed from: u, reason: collision with root package name */
    private static volatile boolean f7164u = false;

    /* renamed from: v, reason: collision with root package name */
    private static volatile boolean f7165v = true;

    /* renamed from: w, reason: collision with root package name */
    private static volatile boolean f7166w = true;

    /* renamed from: x, reason: collision with root package name */
    private static volatile IRemoteConfig f7167x;

    public static void enableNetworkSdkOptimizeTest(boolean z2) {
        if (!z2) {
            setGetSessionAsyncEnable(false);
            ThreadPoolExecutorFactory.setNormalExecutorPoolSize(6);
        } else {
            setGetSessionAsyncEnable(true);
            ThreadPoolExecutorFactory.setNormalExecutorPoolSize(16);
            AwcnConfig.registerPresetSessions("[{\"host\":\"trade-acs.m.taobao.com\", \"protocol\":\"http2\", \"rtt\":\"0rtt\", \"publicKey\": \"acs\", \"isKeepAlive\":true}]");
        }
    }

    public static int getBgForbidRequestThreshold() {
        return f7159p;
    }

    public static int getRequestStatisticSampleRate() {
        return f7156m;
    }

    public static int getServiceBindWaitTime() {
        return f7147d;
    }

    public static void init() {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(NetworkSdkSetting.getContext());
        f7151h = defaultSharedPreferences.getLong("Cache.Flag", 0L);
        f7164u = defaultSharedPreferences.getBoolean("CHANNEL_LOCAL_INSTANCE_ENABLE", false);
        f7165v = defaultSharedPreferences.getBoolean("ALLOW_SPDY_WHEN_BIND_SERVICE_FAILED", true);
    }

    public static boolean isAllowHttpIpRetry() {
        return f7148e && f7150g;
    }

    public static boolean isAllowSpdyWhenBindServiceFailed() {
        return f7165v;
    }

    public static boolean isBgRequestForbidden() {
        return f7152i;
    }

    public static boolean isBindServiceOptimize() {
        return f7163t;
    }

    public static boolean isBizInWhiteList(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        CopyOnWriteArrayList<String> copyOnWriteArrayList = f7154k;
        if (f7154k == null) {
            return false;
        }
        Iterator<String> it = copyOnWriteArrayList.iterator();
        while (it.hasNext()) {
            if (str.equalsIgnoreCase(it.next())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isChannelLocalInstanceEnable() {
        return f7164u;
    }

    public static boolean isCookieEnable() {
        return f7166w;
    }

    public static boolean isGetSessionAsyncEnable() {
        return f7158o;
    }

    public static boolean isHttpCacheEnable() {
        return f7149f;
    }

    public static boolean isHttpSessionEnable() {
        return f7148e;
    }

    public static boolean isRemoteNetworkServiceEnable() {
        return f7146c;
    }

    public static boolean isRequestDelayRetryForNoNetwork() {
        return f7162s;
    }

    public static boolean isRequestInMonitorList(RequestStatistic requestStatistic) {
        CopyOnWriteArrayList<String> copyOnWriteArrayList;
        if (requestStatistic == null || (copyOnWriteArrayList = f7160q) == null || TextUtils.isEmpty(requestStatistic.host)) {
            return false;
        }
        Iterator<String> it = copyOnWriteArrayList.iterator();
        while (it.hasNext()) {
            if (requestStatistic.host.equalsIgnoreCase(it.next())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isResponseBufferEnable() {
        return f7157n;
    }

    public static boolean isSSLEnabled() {
        return f7144a;
    }

    public static boolean isSpdyEnabled() {
        return f7145b;
    }

    public static boolean isUrlInDegradeList(HttpUrl httpUrl) {
        ConcurrentHashMap<String, List<String>> concurrentHashMap;
        List<String> list;
        if (httpUrl == null || (concurrentHashMap = f7161r) == null || (list = concurrentHashMap.get(httpUrl.host())) == null) {
            return false;
        }
        if (list == f7155l) {
            return true;
        }
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            if (httpUrl.path().startsWith(it.next())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isUrlInWhiteList(HttpUrl httpUrl) {
        ConcurrentHashMap<String, List<String>> concurrentHashMap;
        List<String> list;
        if (httpUrl == null || (concurrentHashMap = f7153j) == null || (list = concurrentHashMap.get(httpUrl.host())) == null) {
            return false;
        }
        if (list == f7155l) {
            return true;
        }
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            if (httpUrl.path().startsWith(it.next())) {
                return true;
            }
        }
        return false;
    }

    public static void setAllowHttpIpRetry(boolean z2) {
        f7150g = z2;
    }

    public static void setAllowSpdyWhenBindServiceFailed(boolean z2) {
        f7165v = z2;
        SharedPreferences.Editor editorEdit = PreferenceManager.getDefaultSharedPreferences(NetworkSdkSetting.getContext()).edit();
        editorEdit.putBoolean("ALLOW_SPDY_WHEN_BIND_SERVICE_FAILED", f7165v);
        editorEdit.apply();
    }

    public static void setAmdcPresetHosts(String str) throws JSONException {
        if (GlobalAppRuntimeInfo.isTargetProcess()) {
            try {
                JSONArray jSONArray = new JSONArray(str);
                int length = jSONArray.length();
                ArrayList arrayList = new ArrayList(length);
                for (int i2 = 0; i2 < length; i2++) {
                    String string = jSONArray.getString(i2);
                    if (c.c(string)) {
                        arrayList.add(string);
                    }
                }
                HttpDispatcher.getInstance().addHosts(arrayList);
            } catch (JSONException e2) {
                ALog.e("anet.NetworkConfigCenter", "parse hosts failed", null, e2, new Object[0]);
            }
        }
    }

    public static void setBgForbidRequestThreshold(int i2) {
        f7159p = i2;
    }

    public static void setBgRequestForbidden(boolean z2) {
        f7152i = z2;
    }

    public static void setBindServiceOptimize(boolean z2) {
        f7163t = z2;
    }

    public static void setCacheFlag(long j2) {
        if (j2 != f7151h) {
            ALog.i("anet.NetworkConfigCenter", "set cache flag", null, "old", Long.valueOf(f7151h), "new", Long.valueOf(j2));
            f7151h = j2;
            SharedPreferences.Editor editorEdit = PreferenceManager.getDefaultSharedPreferences(NetworkSdkSetting.getContext()).edit();
            editorEdit.putLong("Cache.Flag", f7151h);
            editorEdit.apply();
            CacheManager.clearAllCache();
        }
    }

    public static void setChannelLocalInstanceEnable(boolean z2) {
        f7164u = z2;
        SharedPreferences.Editor editorEdit = PreferenceManager.getDefaultSharedPreferences(NetworkSdkSetting.getContext()).edit();
        editorEdit.putBoolean("CHANNEL_LOCAL_INSTANCE_ENABLE", f7164u);
        editorEdit.apply();
    }

    public static void setCookieEnable(boolean z2) {
        f7166w = z2;
    }

    public static void setDegradeRequestList(String str) throws JSONException {
        if (ALog.isPrintLog(2)) {
            ALog.i("anet.NetworkConfigCenter", "setDegradeRequestList", null, "Degrade List", str);
        }
        if (TextUtils.isEmpty(str)) {
            f7161r = null;
            return;
        }
        ConcurrentHashMap<String, List<String>> concurrentHashMap = new ConcurrentHashMap<>();
        try {
            JSONObject jSONObject = new JSONObject(str);
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                Object obj = jSONObject.get(next);
                try {
                    if (ProxyConfig.MATCH_ALL_SCHEMES.equals(obj)) {
                        concurrentHashMap.put(next, f7155l);
                    } else if (obj instanceof JSONArray) {
                        JSONArray jSONArray = (JSONArray) obj;
                        int length = jSONArray.length();
                        ArrayList arrayList = new ArrayList(length);
                        for (int i2 = 0; i2 < length; i2++) {
                            Object obj2 = jSONArray.get(i2);
                            if (obj2 instanceof String) {
                                arrayList.add((String) obj2);
                            }
                        }
                        if (!arrayList.isEmpty()) {
                            concurrentHashMap.put(next, arrayList);
                        }
                    }
                } catch (JSONException unused) {
                }
            }
        } catch (JSONException e2) {
            ALog.e("anet.NetworkConfigCenter", "parse jsonObject failed", null, e2, new Object[0]);
        }
        f7161r = concurrentHashMap;
    }

    public static void setGetSessionAsyncEnable(boolean z2) {
        f7158o = z2;
    }

    public static void setHttpCacheEnable(boolean z2) {
        f7149f = z2;
    }

    public static void setHttpSessionEnable(boolean z2) {
        f7148e = z2;
    }

    @Deprecated
    public static void setHttpsValidationEnabled(boolean z2) {
    }

    public static void setMonitorRequestList(String str) throws JSONException {
        if (TextUtils.isEmpty(str)) {
            f7160q = null;
        }
        try {
            JSONArray jSONArray = new JSONObject(str).getJSONArray("host");
            int length = jSONArray.length();
            CopyOnWriteArrayList<String> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
            for (int i2 = 0; i2 < length; i2++) {
                String string = jSONArray.getString(i2);
                if (c.c(string)) {
                    copyOnWriteArrayList.add(string);
                }
            }
            f7160q = copyOnWriteArrayList;
        } catch (JSONException e2) {
            ALog.e("anet.NetworkConfigCenter", "parse hosts failed", null, e2, new Object[0]);
        }
    }

    public static void setRemoteConfig(IRemoteConfig iRemoteConfig) {
        if (f7167x != null) {
            f7167x.unRegister();
        }
        if (iRemoteConfig != null) {
            iRemoteConfig.register();
        }
        f7167x = iRemoteConfig;
    }

    public static void setRemoteNetworkServiceEnable(boolean z2) {
        f7146c = z2;
    }

    public static void setRequestDelayRetryForNoNetwork(boolean z2) {
        f7162s = z2;
    }

    public static void setRequestStatisticSampleRate(int i2) {
        f7156m = i2;
    }

    public static void setResponseBufferEnable(boolean z2) {
        f7157n = z2;
    }

    public static void setSSLEnabled(boolean z2) {
        ALog.i("anet.NetworkConfigCenter", "[setSSLEnabled]", null, "enable", Boolean.valueOf(z2));
        f7144a = z2;
    }

    public static void setServiceBindWaitTime(int i2) {
        f7147d = i2;
    }

    public static void setSpdyEnabled(boolean z2) {
        ALog.i("anet.NetworkConfigCenter", "[setSpdyEnabled]", null, "enable", Boolean.valueOf(z2));
        f7145b = z2;
    }

    public static void updateBizWhiteList(String str) throws JSONException {
        if (ALog.isPrintLog(2)) {
            ALog.i("anet.NetworkConfigCenter", "updateRequestWhiteList", null, "White List", str);
        }
        if (TextUtils.isEmpty(str)) {
            f7154k = null;
            return;
        }
        try {
            JSONArray jSONArray = new JSONArray(str);
            int length = jSONArray.length();
            CopyOnWriteArrayList<String> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
            for (int i2 = 0; i2 < length; i2++) {
                String string = jSONArray.getString(i2);
                if (!string.isEmpty()) {
                    copyOnWriteArrayList.add(string);
                }
            }
            f7154k = copyOnWriteArrayList;
        } catch (JSONException e2) {
            ALog.e("anet.NetworkConfigCenter", "parse bizId failed", null, e2, new Object[0]);
        }
    }

    public static void updateWhiteListMap(String str) throws JSONException {
        if (ALog.isPrintLog(2)) {
            ALog.i("anet.NetworkConfigCenter", "updateWhiteUrlList", null, "White List", str);
        }
        if (TextUtils.isEmpty(str)) {
            f7153j = null;
            return;
        }
        ConcurrentHashMap<String, List<String>> concurrentHashMap = new ConcurrentHashMap<>();
        try {
            JSONObject jSONObject = new JSONObject(str);
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                Object obj = jSONObject.get(next);
                try {
                    if (ProxyConfig.MATCH_ALL_SCHEMES.equals(obj)) {
                        concurrentHashMap.put(next, f7155l);
                    } else if (obj instanceof JSONArray) {
                        JSONArray jSONArray = (JSONArray) obj;
                        int length = jSONArray.length();
                        ArrayList arrayList = new ArrayList(length);
                        for (int i2 = 0; i2 < length; i2++) {
                            Object obj2 = jSONArray.get(i2);
                            if (obj2 instanceof String) {
                                arrayList.add((String) obj2);
                            }
                        }
                        if (!arrayList.isEmpty()) {
                            concurrentHashMap.put(next, arrayList);
                        }
                    }
                } catch (JSONException unused) {
                }
            }
        } catch (JSONException e2) {
            ALog.e("anet.NetworkConfigCenter", "parse jsonObject failed", null, e2, new Object[0]);
        }
        f7153j = concurrentHashMap;
    }
}
