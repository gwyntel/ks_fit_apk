package anet.channel;

import android.text.TextUtils;
import anet.channel.strategy.ConnProtocol;
import anet.channel.strategy.StrategyTemplate;
import anet.channel.util.ALog;
import java.util.concurrent.CopyOnWriteArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class AwcnConfig {
    public static final String HTTP3_ENABLE = "HTTP3_ENABLE";
    public static final String NEXT_LAUNCH_FORBID = "NEXT_LAUNCH_FORBID";

    /* renamed from: a, reason: collision with root package name */
    private static volatile boolean f6569a = false;

    /* renamed from: b, reason: collision with root package name */
    private static volatile boolean f6570b = true;

    /* renamed from: c, reason: collision with root package name */
    private static volatile boolean f6571c = true;

    /* renamed from: d, reason: collision with root package name */
    private static volatile boolean f6572d = true;

    /* renamed from: e, reason: collision with root package name */
    private static volatile boolean f6573e = false;

    /* renamed from: f, reason: collision with root package name */
    private static volatile boolean f6574f = true;

    /* renamed from: g, reason: collision with root package name */
    private static volatile long f6575g = 43200000;

    /* renamed from: h, reason: collision with root package name */
    private static volatile boolean f6576h = true;

    /* renamed from: i, reason: collision with root package name */
    private static volatile boolean f6577i = true;

    /* renamed from: j, reason: collision with root package name */
    private static boolean f6578j = true;

    /* renamed from: k, reason: collision with root package name */
    private static boolean f6579k = false;

    /* renamed from: l, reason: collision with root package name */
    private static volatile boolean f6580l = false;

    /* renamed from: m, reason: collision with root package name */
    private static volatile boolean f6581m = true;

    /* renamed from: n, reason: collision with root package name */
    private static volatile boolean f6582n = false;

    /* renamed from: o, reason: collision with root package name */
    private static volatile int f6583o = 10000;

    /* renamed from: p, reason: collision with root package name */
    private static volatile boolean f6584p = false;

    /* renamed from: q, reason: collision with root package name */
    private static volatile boolean f6585q = true;

    /* renamed from: r, reason: collision with root package name */
    private static volatile int f6586r = -1;

    /* renamed from: s, reason: collision with root package name */
    private static volatile boolean f6587s = true;

    /* renamed from: t, reason: collision with root package name */
    private static volatile boolean f6588t = true;

    /* renamed from: u, reason: collision with root package name */
    private static volatile boolean f6589u = false;

    /* renamed from: v, reason: collision with root package name */
    private static volatile boolean f6590v = true;

    /* renamed from: w, reason: collision with root package name */
    private static volatile CopyOnWriteArrayList<String> f6591w = null;

    /* renamed from: x, reason: collision with root package name */
    private static volatile boolean f6592x = true;

    /* renamed from: y, reason: collision with root package name */
    private static volatile boolean f6593y = true;

    public static int getAccsReconnectionDelayPeriod() {
        return f6583o;
    }

    public static long getIpv6BlackListTtl() {
        return f6575g;
    }

    public static int getXquicCongControl() {
        return f6586r;
    }

    public static boolean isAccsSessionCreateForbiddenInBg() {
        return f6569a;
    }

    public static boolean isAllowHttpDnsNotify(String str) {
        if (f6591w == null || TextUtils.isEmpty(str)) {
            return false;
        }
        return f6591w.contains(str);
    }

    public static boolean isAppLifeCycleListenerEnable() {
        return f6578j;
    }

    public static boolean isAsyncLoadStrategyEnable() {
        return f6579k;
    }

    public static boolean isCarrierInfoEnable() {
        return f6593y;
    }

    public static boolean isCookieHeaderRedundantFix() {
        return f6588t;
    }

    public static boolean isHorseRaceEnable() {
        return f6571c;
    }

    public static boolean isHttp3Enable() {
        return f6584p;
    }

    public static boolean isHttp3OrangeEnable() {
        return f6585q;
    }

    public static boolean isHttpsSniEnable() {
        return f6570b;
    }

    public static boolean isIdleSessionCloseEnable() {
        return f6574f;
    }

    public static boolean isIpStackDetectByUdpConnect() {
        return f6587s;
    }

    public static boolean isIpv6BlackListEnable() {
        return f6577i;
    }

    public static boolean isIpv6Enable() {
        return f6576h;
    }

    public static boolean isNetworkDetectEnable() {
        return f6582n;
    }

    public static boolean isPing6Enable() {
        return f6581m;
    }

    public static boolean isQuicEnable() {
        return f6573e;
    }

    public static boolean isSendConnectInfoByBroadcast() {
        return f6589u;
    }

    public static boolean isSendConnectInfoByService() {
        return f6590v;
    }

    public static boolean isTbNextLaunch() {
        return f6580l;
    }

    public static boolean isTnetHeaderCacheEnable() {
        return f6572d;
    }

    public static boolean isWifiInfoEnable() {
        return f6592x;
    }

    public static void registerPresetSessions(String str) {
        if (GlobalAppRuntimeInfo.isTargetProcess() && !TextUtils.isEmpty(str)) {
            try {
                JSONArray jSONArray = new JSONArray(str);
                int length = jSONArray.length();
                for (int i2 = 0; i2 < length; i2++) {
                    JSONObject jSONObject = jSONArray.getJSONObject(i2);
                    String string = jSONObject.getString("host");
                    if (!anet.channel.strategy.utils.c.c(string)) {
                        return;
                    }
                    StrategyTemplate.getInstance().registerConnProtocol(string, ConnProtocol.valueOf(jSONObject.getString("protocol"), jSONObject.getString("rtt"), jSONObject.getString("publicKey")));
                    if (jSONObject.getBoolean("isKeepAlive")) {
                        SessionCenter.getInstance().registerSessionInfo(SessionInfo.create(string, true, false, null, null, null));
                    }
                }
            } catch (Exception unused) {
            }
        }
    }

    public static void setAccsReconnectionDelayPeriod(int i2) {
        if (i2 < 0) {
            i2 = 0;
        }
        if (i2 > 10000) {
            i2 = 10000;
        }
        f6583o = i2;
    }

    public static void setAccsSessionCreateForbiddenInBg(boolean z2) {
        f6569a = z2;
    }

    public static void setAppLifeCycleListenerEnable(boolean z2) {
        f6578j = z2;
    }

    public static void setAsyncLoadStrategyEnable(boolean z2) {
        f6579k = z2;
    }

    public static void setCarrierInfoEnable(boolean z2) {
        f6593y = z2;
    }

    public static void setCookieHeaderRedundantFix(boolean z2) {
        f6588t = z2;
    }

    public static void setHorseRaceEnable(boolean z2) {
        f6571c = z2;
    }

    public static void setHttp3Enable(boolean z2) {
        f6584p = z2;
        ALog.e("awcn.AwcnConfig", "[setHttp3Enable]", null, "enable", Boolean.valueOf(z2));
    }

    public static void setHttp3OrangeEnable(boolean z2) {
        f6585q = z2;
    }

    public static void setHttpDnsNotifyWhiteList(String str) throws JSONException {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        try {
            CopyOnWriteArrayList<String> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
            JSONArray jSONArray = new JSONArray(str);
            for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                String string = jSONArray.getString(i2);
                if (!TextUtils.isEmpty(string)) {
                    copyOnWriteArrayList.add(string);
                }
            }
            f6591w = copyOnWriteArrayList;
        } catch (Exception e2) {
            ALog.e("awcn.AwcnConfig", "[setHttpDnsNotifyWhiteList] error", null, e2, new Object[0]);
        }
    }

    public static void setHttpsSniEnable(boolean z2) {
        f6570b = z2;
    }

    public static void setIdleSessionCloseEnable(boolean z2) {
        f6574f = z2;
    }

    public static void setIpStackDetectByUdpConnect(boolean z2) {
        f6587s = z2;
    }

    public static void setIpv6BlackListEnable(boolean z2) {
        f6577i = z2;
    }

    public static void setIpv6BlackListTtl(long j2) {
        f6575g = j2;
    }

    public static void setIpv6Enable(boolean z2) {
        f6576h = z2;
    }

    public static void setNetworkDetectEnable(boolean z2) {
        f6582n = z2;
    }

    public static void setPing6Enable(boolean z2) {
        f6581m = z2;
    }

    public static void setQuicEnable(boolean z2) {
        f6573e = z2;
    }

    public static void setSendConnectInfoByBroadcast(boolean z2) {
        f6589u = z2;
    }

    public static void setSendConnectInfoByService(boolean z2) {
        f6590v = z2;
    }

    public static void setTbNextLaunch(boolean z2) {
        f6580l = z2;
    }

    public static void setTnetHeaderCacheEnable(boolean z2) {
        f6572d = z2;
    }

    public static void setWifiInfoEnable(boolean z2) {
        f6592x = z2;
    }

    public static void setXquicCongControl(int i2) {
        if (i2 < 0) {
            return;
        }
        f6586r = i2;
    }
}
