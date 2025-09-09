package com.huawei.hms.framework.network.grs.f;

import android.content.Context;
import android.text.TextUtils;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.huawei.hms.framework.common.Logger;
import com.huawei.hms.framework.common.StringUtils;
import com.huawei.hms.framework.network.grs.GrsApp;
import com.huawei.hms.framework.network.grs.GrsBaseInfo;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public abstract class a {

    /* renamed from: a, reason: collision with root package name */
    protected com.huawei.hms.framework.network.grs.local.model.a f16218a;

    /* renamed from: b, reason: collision with root package name */
    protected List<com.huawei.hms.framework.network.grs.local.model.b> f16219b;

    /* renamed from: c, reason: collision with root package name */
    protected boolean f16220c = false;

    /* renamed from: d, reason: collision with root package name */
    protected boolean f16221d = false;

    /* renamed from: e, reason: collision with root package name */
    protected Set<String> f16222e = new HashSet(16);

    private int b(String str, Context context) {
        if (g(com.huawei.hms.framework.network.grs.h.c.a(str, context)) != 0) {
            return -1;
        }
        Logger.i("AbstractLocalManager", "load APP_CONFIG_FILE success{%s}.", str);
        return 0;
    }

    private int g(String str) {
        int iC;
        if (TextUtils.isEmpty(str)) {
            return -1;
        }
        if (this.f16221d && (iC = c(str)) != 0) {
            return iC;
        }
        int iB = b(str);
        return iB != 0 ? iB : f(str);
    }

    private int h(String str) {
        List<com.huawei.hms.framework.network.grs.local.model.b> list;
        int iD;
        if (TextUtils.isEmpty(str)) {
            return -1;
        }
        return (!this.f16221d || !((list = this.f16219b) == null || list.isEmpty()) || (iD = d(str)) == 0) ? e(str) : iD;
    }

    int a(String str, Context context) {
        StringBuilder sb = new StringBuilder();
        sb.append(GrsApp.getInstance().getBrand("/"));
        sb.append(str);
        return b(sb.toString(), context) != 0 ? -1 : 0;
    }

    public abstract int b(String str);

    public abstract int c(String str);

    public boolean c() {
        return this.f16220c;
    }

    public int d(String str) throws JSONException {
        JSONArray jSONArray;
        this.f16219b = new ArrayList(16);
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has("countryOrAreaGroups")) {
                jSONArray = jSONObject.getJSONArray("countryOrAreaGroups");
            } else if (jSONObject.has("countryGroups")) {
                jSONArray = jSONObject.getJSONArray("countryGroups");
            } else {
                Logger.e("AbstractLocalManager", "maybe local config json is wrong because the default countryOrAreaGroups isn't config.");
                jSONArray = null;
            }
            if (jSONArray == null) {
                return -1;
            }
            this.f16219b.addAll(a(jSONArray));
            return 0;
        } catch (JSONException e2) {
            Logger.w("AbstractLocalManager", "parse countrygroup failed maybe json style is wrong. %s", StringUtils.anonymizeMessage(e2.getMessage()));
            return -1;
        }
    }

    public int e(String str) {
        try {
            b(new JSONObject(str).getJSONArray(TmpConstant.DEVICE_MODEL_SERVICES));
            return 0;
        } catch (JSONException e2) {
            Logger.w("AbstractLocalManager", "parse 2.0 services failed maybe because of json style.please check! %s", StringUtils.anonymizeMessage(e2.getMessage()));
            return -1;
        }
    }

    public abstract int f(String str);

    public com.huawei.hms.framework.network.grs.local.model.a a() {
        return this.f16218a;
    }

    public Set<String> b() {
        return this.f16222e;
    }

    public String a(Context context, com.huawei.hms.framework.network.grs.e.a aVar, GrsBaseInfo grsBaseInfo, String str, String str2, boolean z2) {
        Map<String, String> mapA = a(context, aVar, grsBaseInfo, str, z2);
        if (mapA != null) {
            return mapA.get(str2);
        }
        Logger.w("AbstractLocalManager", "addresses not found by routeby in local config{%s}", str);
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0065 A[PHI: r13
      0x0065: PHI (r13v5 java.lang.String) = (r13v0 java.lang.String), (r13v1 java.lang.String) binds: [B:18:0x0063, B:21:0x0070] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00ca A[PHI: r8
      0x00ca: PHI (r8v10 java.lang.String) = (r8v8 java.lang.String), (r8v9 java.lang.String) binds: [B:34:0x00c8, B:37:0x00d9] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00e9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void b(org.json.JSONArray r18) throws org.json.JSONException {
        /*
            r17 = this;
            r0 = r17
            r1 = r18
            r2 = 0
            r3 = 1
            if (r1 == 0) goto Lf8
            int r4 = r18.length()
            if (r4 != 0) goto L10
            goto Lf8
        L10:
            r4 = r2
        L11:
            int r5 = r18.length()
            if (r4 >= r5) goto Lf8
            org.json.JSONObject r5 = r1.getJSONObject(r4)
            com.huawei.hms.framework.network.grs.local.model.c r6 = new com.huawei.hms.framework.network.grs.local.model.c
            r6.<init>()
            java.lang.String r7 = "name"
            java.lang.String r7 = r5.getString(r7)
            r6.b(r7)
            java.util.Set<java.lang.String> r8 = r0.f16222e
            boolean r8 = r8.contains(r7)
            if (r8 != 0) goto Lf5
            java.util.Set<java.lang.String> r8 = r0.f16222e
            r8.add(r7)
            boolean r8 = r0.f16221d
            if (r8 == 0) goto Lf5
            java.lang.String r8 = "routeBy"
            java.lang.String r8 = r5.getString(r8)
            r6.c(r8)
            java.lang.String r8 = "servings"
            org.json.JSONArray r8 = r5.getJSONArray(r8)
            r9 = r2
        L4a:
            int r10 = r8.length()
            java.lang.String r11 = "AbstractLocalManager"
            if (r9 >= r10) goto Lc2
            java.lang.Object r10 = r8.get(r9)
            org.json.JSONObject r10 = (org.json.JSONObject) r10
            com.huawei.hms.framework.network.grs.local.model.d r12 = new com.huawei.hms.framework.network.grs.local.model.d
            r12.<init>()
            java.lang.String r13 = "countryOrAreaGroup"
            boolean r14 = r10.has(r13)
            if (r14 == 0) goto L6a
        L65:
            java.lang.String r11 = r10.getString(r13)
            goto L7e
        L6a:
            java.lang.String r13 = "countryGroup"
            boolean r14 = r10.has(r13)
            if (r14 == 0) goto L73
            goto L65
        L73:
            java.lang.Object[] r13 = new java.lang.Object[r3]
            r13[r2] = r7
            java.lang.String r14 = "maybe this service{%s} routeBy is unconditional."
            com.huawei.hms.framework.common.Logger.v(r11, r14, r13)
            java.lang.String r11 = "no-country"
        L7e:
            r12.a(r11)
            java.lang.String r11 = "addresses"
            org.json.JSONObject r10 = r10.getJSONObject(r11)
            java.util.concurrent.ConcurrentHashMap r11 = new java.util.concurrent.ConcurrentHashMap
            r13 = 16
            r11.<init>(r13)
            java.util.Iterator r13 = r10.keys()
        L92:
            boolean r14 = r13.hasNext()
            if (r14 == 0) goto Lb6
            java.lang.Object r14 = r13.next()
            java.lang.String r14 = (java.lang.String) r14
            java.lang.String r15 = r10.getString(r14)
            boolean r16 = android.text.TextUtils.isEmpty(r14)
            if (r16 != 0) goto L92
            boolean r15 = android.text.TextUtils.isEmpty(r15)
            if (r15 != 0) goto L92
            java.lang.String r15 = r10.getString(r14)
            r11.put(r14, r15)
            goto L92
        Lb6:
            r12.a(r11)
            java.lang.String r10 = r12.b()
            r6.a(r10, r12)
            int r9 = r9 + r3
            goto L4a
        Lc2:
            java.lang.String r8 = "countryOrAreaGroups"
            boolean r9 = r5.has(r8)
            if (r9 == 0) goto Ld3
        Lca:
            org.json.JSONArray r5 = r5.getJSONArray(r8)
            java.util.List r5 = r0.a(r5)
            goto Le2
        Ld3:
            java.lang.String r8 = "countryGroups"
            boolean r9 = r5.has(r8)
            if (r9 == 0) goto Ldc
            goto Lca
        Ldc:
            java.lang.String r5 = "service use default countryOrAreaGroup"
            com.huawei.hms.framework.common.Logger.i(r11, r5)
            r5 = 0
        Le2:
            r6.a(r5)
            com.huawei.hms.framework.network.grs.local.model.a r5 = r0.f16218a
            if (r5 != 0) goto Lf0
            com.huawei.hms.framework.network.grs.local.model.a r5 = new com.huawei.hms.framework.network.grs.local.model.a
            r5.<init>()
            r0.f16218a = r5
        Lf0:
            com.huawei.hms.framework.network.grs.local.model.a r5 = r0.f16218a
            r5.a(r7, r6)
        Lf5:
            int r4 = r4 + r3
            goto L11
        Lf8:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.framework.network.grs.f.a.b(org.json.JSONArray):void");
    }

    public String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if ("no_route_country".equals(str)) {
            return "no-country";
        }
        List<com.huawei.hms.framework.network.grs.local.model.b> list = this.f16219b;
        if (list != null && !list.isEmpty()) {
            for (com.huawei.hms.framework.network.grs.local.model.b bVar : this.f16219b) {
                if (bVar.a().contains(str)) {
                    return bVar.b();
                }
            }
        }
        return null;
    }

    public List<com.huawei.hms.framework.network.grs.local.model.b> a(JSONArray jSONArray) throws JSONException {
        JSONArray jSONArray2;
        if (jSONArray == null || jSONArray.length() == 0) {
            return new ArrayList();
        }
        try {
            ArrayList arrayList = new ArrayList(16);
            for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i2);
                com.huawei.hms.framework.network.grs.local.model.b bVar = new com.huawei.hms.framework.network.grs.local.model.b();
                bVar.b(jSONObject.getString("id"));
                bVar.c(jSONObject.getString("name"));
                bVar.a(jSONObject.getString("description"));
                if (jSONObject.has("countriesOrAreas")) {
                    jSONArray2 = jSONObject.getJSONArray("countriesOrAreas");
                } else if (jSONObject.has("countries")) {
                    jSONArray2 = jSONObject.getJSONArray("countries");
                } else {
                    Logger.w("AbstractLocalManager", "current country or area group has not config countries or areas.");
                    jSONArray2 = null;
                }
                HashSet hashSet = new HashSet(16);
                if (jSONArray2 != null && jSONArray2.length() != 0) {
                    for (int i3 = 0; i3 < jSONArray2.length(); i3++) {
                        hashSet.add((String) jSONArray2.get(i3));
                    }
                    bVar.a(hashSet);
                    arrayList.add(bVar);
                }
                return new ArrayList();
            }
            return arrayList;
        } catch (JSONException e2) {
            Logger.w("AbstractLocalManager", "parse countrygroup failed maybe json style is wrong. %s", StringUtils.anonymizeMessage(e2.getMessage()));
            return new ArrayList();
        }
    }

    public Map<String, String> a(Context context, com.huawei.hms.framework.network.grs.e.a aVar, GrsBaseInfo grsBaseInfo, String str, boolean z2) {
        com.huawei.hms.framework.network.grs.local.model.a aVar2 = this.f16218a;
        if (aVar2 == null) {
            Logger.w("AbstractLocalManager", "application data is null.");
            return null;
        }
        com.huawei.hms.framework.network.grs.local.model.c cVarA = aVar2.a(str);
        if (cVarA == null) {
            Logger.w("AbstractLocalManager", "service not found in local config{%s}", str);
            return null;
        }
        String strB = e.b(context, aVar, cVarA.b(), grsBaseInfo, z2);
        if (strB == null) {
            Logger.w("AbstractLocalManager", "country not found by routeby in local config{%s}", cVarA.b());
            return null;
        }
        List<com.huawei.hms.framework.network.grs.local.model.b> listA = cVarA.a();
        com.huawei.hms.framework.network.grs.local.model.d dVarA = cVarA.a((listA == null || listA.size() == 0) ? a(strB) : a(listA, grsBaseInfo, strB).get(strB));
        if (dVarA == null) {
            return null;
        }
        return dVarA.a();
    }

    private Map<String, String> a(List<com.huawei.hms.framework.network.grs.local.model.b> list, GrsBaseInfo grsBaseInfo, String str) {
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap(16);
        concurrentHashMap.put("no_route_country", "no-country");
        for (com.huawei.hms.framework.network.grs.local.model.b bVar : list) {
            if (bVar.a().contains(grsBaseInfo.getIssueCountry())) {
                concurrentHashMap.put(grsBaseInfo.getIssueCountry(), bVar.b());
            }
            if (bVar.a().contains(grsBaseInfo.getRegCountry())) {
                concurrentHashMap.put(grsBaseInfo.getRegCountry(), bVar.b());
            }
            if (bVar.a().contains(grsBaseInfo.getSerCountry())) {
                concurrentHashMap.put(grsBaseInfo.getSerCountry(), bVar.b());
            }
            if (bVar.a().contains(str)) {
                Logger.v("AbstractLocalManager", "get countryGroupID from geoIp");
                concurrentHashMap.put(str, bVar.b());
            }
        }
        return concurrentHashMap;
    }

    public void a(Context context, List<String> list) {
        if (list == null || list.size() <= 0) {
            return;
        }
        for (String str : list) {
            Logger.d("AbstractLocalManager", "getBatchLoadSdkSuccessFlag file:" + str);
            if (TextUtils.isEmpty(str) || !Pattern.matches("^grs_sdk_global_route_config_[a-zA-Z]+\\.json$", str)) {
                Logger.d("AbstractLocalManager", "load SDK_CONFIG_FILE: %s, skipped.", str);
            } else {
                if (h(com.huawei.hms.framework.network.grs.h.c.a(GrsApp.getInstance().getBrand("/") + str, context)) == 0) {
                    Logger.d("AbstractLocalManager", "load SDK_CONFIG_FILE: %s, sucess.", str);
                } else {
                    Logger.w("AbstractLocalManager", "load SDK_CONFIG_FILE: %s, failure.", str);
                }
            }
        }
    }
}
