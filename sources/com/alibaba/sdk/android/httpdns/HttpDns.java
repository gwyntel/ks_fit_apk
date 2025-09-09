package com.alibaba.sdk.android.httpdns;

import android.content.Context;
import android.text.TextUtils;
import com.alibaba.sdk.android.httpdns.probe.IPProbeItem;
import com.alibaba.sdk.android.utils.AMSConfigUtils;
import com.alibaba.sdk.android.utils.AMSDevReporter;
import com.alibaba.sdk.android.utils.crashdefend.SDKMessageCallback;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class HttpDns implements HttpDnsService {
    private boolean isExpiredIPEnabled = false;
    private static d hostManager = d.a();
    private static DegradationFilter degradationFilter = null;
    static HttpDns instance = null;
    private static boolean inited = false;
    private static String sAccountId = null;
    private static String sSecretKey = null;
    private static Context sContext = null;

    private HttpDns(Context context, String str) {
        f.c(str);
        com.alibaba.sdk.android.httpdns.a.a.a().c(context, str);
        com.alibaba.sdk.android.httpdns.a.a.a().a(com.alibaba.sdk.android.httpdns.d.b.a(context));
    }

    private static void disableReport() {
        com.alibaba.sdk.android.httpdns.d.b.a().k();
    }

    private static String getAccountId() {
        if (!TextUtils.isEmpty(sAccountId)) {
            return sAccountId;
        }
        String accountId = AMSConfigUtils.getAccountId(sContext);
        sAccountId = accountId;
        return accountId;
    }

    private String getIpByHost(String str) {
        if (!b.a()) {
            i.f("HttpDns service turned off");
            return null;
        }
        String[] ipsByHost = getIpsByHost(str);
        if (ipsByHost != null && ipsByHost.length > 0) {
            return ipsByHost[0];
        }
        return null;
    }

    private String[] getIpsByHost(String str) {
        if (!b.a()) {
            i.f("HttpDns service turned off");
        } else {
            if (!l.b(str)) {
                return f.f20c;
            }
            if (l.c(str)) {
                return new String[]{str};
            }
            DegradationFilter degradationFilter2 = degradationFilter;
            if (degradationFilter2 != null && degradationFilter2.shouldDegradeHttpDNS(str)) {
                return f.f20c;
            }
            if (u.e()) {
                return getIpsByHostAsync(str);
            }
            e eVarM33a = hostManager.m33a(str);
            if (eVarM33a != null && eVarM33a.m43b() && this.isExpiredIPEnabled) {
                if (!hostManager.m38a(str)) {
                    i.d("refresh host async: " + str);
                    c.a().submit(new q(str, s.QUERY_HOST));
                }
                return eVarM33a.getIps();
            }
            if (eVarM33a != null && !eVarM33a.m43b()) {
                return eVarM33a.getIps();
            }
            i.d("refresh host sync: " + str);
            try {
                return (String[]) c.a().submit(new q(str, s.QUERY_HOST)).get();
            } catch (Exception e2) {
                i.a(e2);
            }
        }
        return f.f20c;
    }

    private static String getSecretKey() {
        if (!TextUtils.isEmpty(sSecretKey)) {
            return sSecretKey;
        }
        String httpdnsSecretKey = AMSConfigUtils.getHttpdnsSecretKey(sContext);
        sSecretKey = httpdnsSecretKey;
        return httpdnsSecretKey;
    }

    public static synchronized HttpDnsService getService(Context context) {
        try {
            if (instance == null && context != null) {
                Context applicationContext = context.getApplicationContext();
                sContext = applicationContext;
                b.a(applicationContext);
                com.alibaba.sdk.android.httpdns.d.b.a(sContext).a(new SDKMessageCallback() { // from class: com.alibaba.sdk.android.httpdns.HttpDns.3
                    @Override // com.alibaba.sdk.android.utils.crashdefend.SDKMessageCallback
                    public void crashDefendMessage(int i2, int i3) {
                        boolean unused = HttpDns.inited = true;
                        if (i2 > i3) {
                            b.b(true);
                        } else {
                            i.f("crash limit exceeds, httpdns disabled");
                            b.b(false);
                        }
                    }
                });
                if (!inited) {
                    i.f("sdk crash defend not returned");
                }
                if (b.a()) {
                    initHttpDns(sContext, getAccountId(), getSecretKey());
                } else {
                    instance = new HttpDns(sContext, getAccountId());
                }
            }
        } catch (Throwable th) {
            throw th;
        }
        return instance;
    }

    private static void initHttpDns(Context context, String str, String str2) {
        if (instance == null) {
            HashMap map = new HashMap();
            map.put(AMSDevReporter.AMSSdkExtInfoKeyEnum.AMS_EXTINFO_KEY_VERSION.toString(), "1.3.2.3-no-bssid-ssid");
            AMSDevReporter.asyncReport(context, AMSDevReporter.AMSSdkTypeEnum.AMS_HTTPDNS, map);
            p.setContext(context);
            q.setContext(context);
            com.alibaba.sdk.android.httpdns.b.b.a(context);
            com.alibaba.sdk.android.httpdns.b.b.b(context);
            u.a(context);
            n.a().a(context, str);
            if (!TextUtils.isEmpty(str2)) {
                a.setSecretKey(str2);
            }
            reportActive(context, str);
            instance = new HttpDns(context, str);
        }
    }

    private static void reportActive(Context context, String str) {
        if (context == null || TextUtils.isEmpty(str)) {
            i.f("report active failed due to missing context or accountid");
        } else {
            com.alibaba.sdk.android.httpdns.d.b.a(context).setAccountId(str);
            com.alibaba.sdk.android.httpdns.d.b.a(context).l();
        }
    }

    private static void reportHttpDnsSuccess(String str, int i2) {
        com.alibaba.sdk.android.httpdns.d.b bVarA = com.alibaba.sdk.android.httpdns.d.b.a();
        if (bVarA != null) {
            bVarA.a(str, i2, com.alibaba.sdk.android.httpdns.d.c.a(), com.alibaba.sdk.android.httpdns.b.b.m28a() ? 1 : 0);
        }
    }

    private static void reportUserGetIP(String str, int i2) {
        com.alibaba.sdk.android.httpdns.d.b bVarA = com.alibaba.sdk.android.httpdns.d.b.a();
        if (bVarA != null) {
            bVarA.b(str, i2, com.alibaba.sdk.android.httpdns.d.c.a(), com.alibaba.sdk.android.httpdns.b.b.m28a() ? 1 : 0);
        }
    }

    private static void setAccountId(String str) {
        sAccountId = str;
    }

    private static void setSecretKey(String str) {
        sSecretKey = str;
    }

    static synchronized void switchDnsService(boolean z2) {
        b.a(z2);
        if (!b.a()) {
            i.f("httpdns service disabled");
        }
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void clearSdnsGlobalParams() {
        f.clearSdnsGlobalParams();
    }

    @Override // com.alibaba.sdk.android.httpdns.net64.Net64Service
    public void enableIPv6(boolean z2) {
        com.alibaba.sdk.android.httpdns.net64.a.a().enableIPv6(z2);
        try {
            com.alibaba.sdk.android.httpdns.d.b bVarA = com.alibaba.sdk.android.httpdns.d.b.a();
            if (bVarA != null) {
                bVarA.e(z2 ? 1 : 0);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.alibaba.sdk.android.httpdns.net64.Net64Service
    public String getIPv6ByHostAsync(String str) {
        try {
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        if (!b.a()) {
            i.f("HttpDns service turned off");
            return null;
        }
        if (com.alibaba.sdk.android.httpdns.net64.a.a().m47a()) {
            getIpsByHostAsync(str);
            e eVarM33a = hostManager.m33a(str);
            if (eVarM33a != null) {
                String iPv6ByHostAsync = com.alibaba.sdk.android.httpdns.net64.a.a().getIPv6ByHostAsync(str);
                if (this.isExpiredIPEnabled) {
                    i.d("ipv6 is expired enable, hostName: " + str + " ipv6: " + iPv6ByHostAsync);
                    return iPv6ByHostAsync;
                }
                if (!eVarM33a.m43b()) {
                    i.d("ipv6 is not expired, hostName: " + str + " ipv6: " + iPv6ByHostAsync);
                    return iPv6ByHostAsync;
                }
                if (!eVarM33a.c()) {
                    i.d("ipv6 is expired.");
                    return null;
                }
                i.d("ipv6 is from cache, hostName: " + str + " ipv6: " + iPv6ByHostAsync);
                return iPv6ByHostAsync;
            }
        }
        return null;
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public String getIpByHostAsync(String str) {
        try {
            if (!b.a()) {
                i.f("HttpDns service turned off");
                return null;
            }
            String[] ipsByHostAsync = getIpsByHostAsync(str);
            if (ipsByHostAsync != null && ipsByHostAsync.length > 0) {
                return ipsByHostAsync[0];
            }
            return null;
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x0092 A[PHI: r3
      0x0092: PHI (r3v5 boolean) = (r3v1 boolean), (r3v6 boolean) binds: [B:27:0x0061, B:23:0x0057] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00c1  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00cc  */
    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.alibaba.sdk.android.httpdns.HTTPDNSResult getIpsByHostAsync(java.lang.String r6, java.util.Map<java.lang.String, java.lang.String> r7, java.lang.String r8) {
        /*
            Method dump skipped, instructions count: 383
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.httpdns.HttpDns.getIpsByHostAsync(java.lang.String, java.util.Map, java.lang.String):com.alibaba.sdk.android.httpdns.HTTPDNSResult");
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public String getSessionId() {
        return com.alibaba.sdk.android.httpdns.e.a.a().getSessionId();
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setAuthCurrentTime(long j2) {
        if (b.a()) {
            a.setAuthCurrentTime(j2);
        } else {
            i.f("HttpDns service turned off");
        }
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setCachedIPEnabled(boolean z2) {
        setCachedIPEnabled(z2, true);
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setDegradationFilter(DegradationFilter degradationFilter2) {
        if (b.a()) {
            degradationFilter = degradationFilter2;
        } else {
            i.f("HttpDns service turned off");
        }
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setExpiredIPEnabled(boolean z2) {
        if (!b.a()) {
            i.f("HttpDns service turned off");
            return;
        }
        this.isExpiredIPEnabled = z2;
        com.alibaba.sdk.android.httpdns.d.b bVarA = com.alibaba.sdk.android.httpdns.d.b.a();
        if (bVarA != null) {
            bVarA.d(z2 ? 1 : 0);
        }
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setHTTPSRequestEnabled(boolean z2) {
        if (b.a()) {
            f.setHTTPSRequestEnabled(z2);
        } else {
            i.f("HttpDns service turned off");
        }
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setIPProbeList(List<IPProbeItem> list) {
        if (b.a()) {
            f.a(list);
        } else {
            i.f("HttpDns service turned off");
        }
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setLogEnabled(boolean z2) {
        i.setLogEnabled(z2);
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setLogger(ILogger iLogger) {
        i.setLogger(iLogger);
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setPreResolveAfterNetworkChanged(boolean z2) {
        if (b.a()) {
            p.f8883i = z2;
        } else {
            i.f("HttpDns service turned off");
        }
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setPreResolveHosts(ArrayList<String> arrayList) {
        if (!b.a()) {
            i.f("HttpDns service turned off");
            return;
        }
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            String str = arrayList.get(i2);
            if (l.b(str) && !hostManager.m38a(str)) {
                c.a().submit(new q(str, s.QUERY_HOST));
            }
        }
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setRegion(String str) {
        if (!b.a()) {
            i.f("HttpDns service turned off");
        } else if (TextUtils.isEmpty(str)) {
            i.f("region cannot be empty");
        } else {
            n.a().b(sContext, str);
        }
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setSdnsGlobalParams(Map<String, String> map) {
        f.setSdnsGlobalParams(map);
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setTimeoutInterval(int i2) {
        if (b.a()) {
            f.setTimeoutInterval(i2);
        } else {
            i.f("HttpDns service turned off");
        }
    }

    public static synchronized HttpDnsService getService(Context context, String str) {
        try {
            if (instance == null && context != null) {
                sContext = context.getApplicationContext();
                setAccountId(str);
                b.a(sContext);
                com.alibaba.sdk.android.httpdns.d.b.a(sContext).a(new SDKMessageCallback() { // from class: com.alibaba.sdk.android.httpdns.HttpDns.1
                    @Override // com.alibaba.sdk.android.utils.crashdefend.SDKMessageCallback
                    public void crashDefendMessage(int i2, int i3) {
                        boolean unused = HttpDns.inited = true;
                        if (i2 > i3) {
                            b.b(true);
                        } else {
                            i.f("crash limit exceeds, httpdns disabled");
                            b.b(false);
                        }
                    }
                });
                if (!inited) {
                    i.f("sdk crash defend not returned");
                }
                if (b.a()) {
                    initHttpDns(sContext, getAccountId(), getSecretKey());
                } else {
                    instance = new HttpDns(sContext, getAccountId());
                }
            }
        } catch (Throwable th) {
            throw th;
        }
        return instance;
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x0079 A[PHI: r3
      0x0079: PHI (r3v5 boolean) = (r3v1 boolean), (r3v6 boolean) binds: [B:27:0x0048, B:23:0x003e] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00a4  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00aa  */
    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String[] getIpsByHostAsync(java.lang.String r6) {
        /*
            Method dump skipped, instructions count: 303
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.httpdns.HttpDns.getIpsByHostAsync(java.lang.String):java.lang.String[]");
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setCachedIPEnabled(boolean z2, boolean z3) {
        try {
            if (!b.a()) {
                i.f("HttpDns service turned off");
                return;
            }
            i.f("Httpdns DB cache enable = " + z2 + ". autoCleanCacheAfterLoad = " + z3);
            com.alibaba.sdk.android.httpdns.b.b.a(z2, z3);
            d.a().m35a();
            com.alibaba.sdk.android.httpdns.d.b bVarA = com.alibaba.sdk.android.httpdns.d.b.a();
            if (bVarA != null) {
                bVarA.c(z2 ? 1 : 0);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static synchronized HttpDnsService getService(Context context, String str, String str2) {
        try {
            if (instance == null && context != null) {
                sContext = context.getApplicationContext();
                setAccountId(str);
                setSecretKey(str2);
                b.a(sContext);
                com.alibaba.sdk.android.httpdns.d.b.a(sContext).a(new SDKMessageCallback() { // from class: com.alibaba.sdk.android.httpdns.HttpDns.2
                    @Override // com.alibaba.sdk.android.utils.crashdefend.SDKMessageCallback
                    public void crashDefendMessage(int i2, int i3) {
                        boolean unused = HttpDns.inited = true;
                        if (i2 > i3) {
                            b.b(true);
                        } else {
                            i.f("crash limit exceeds, httpdns disabled");
                            b.b(false);
                        }
                    }
                });
                if (!inited) {
                    i.f("sdk crash defend not returned");
                }
                if (b.a()) {
                    initHttpDns(sContext, getAccountId(), getSecretKey());
                } else {
                    instance = new HttpDns(sContext, getAccountId());
                }
            }
        } catch (Throwable th) {
            throw th;
        }
        return instance;
    }
}
