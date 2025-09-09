package com.huawei.hms.framework.network.grs;

import android.content.Context;
import android.text.TextUtils;
import com.huawei.hms.framework.common.Logger;
import com.huawei.hms.framework.common.StringUtils;
import com.huawei.hms.framework.network.grs.g.g;
import com.huawei.hms.framework.network.grs.local.model.CountryCodeBean;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: e, reason: collision with root package name */
    private static final String f16174e = "a";

    /* renamed from: a, reason: collision with root package name */
    private final GrsBaseInfo f16175a;

    /* renamed from: b, reason: collision with root package name */
    private com.huawei.hms.framework.network.grs.e.a f16176b;

    /* renamed from: c, reason: collision with root package name */
    private g f16177c;

    /* renamed from: d, reason: collision with root package name */
    private com.huawei.hms.framework.network.grs.e.c f16178d;

    /* renamed from: com.huawei.hms.framework.network.grs.a$a, reason: collision with other inner class name */
    private static class C0133a implements com.huawei.hms.framework.network.grs.b {

        /* renamed from: a, reason: collision with root package name */
        String f16179a;

        /* renamed from: b, reason: collision with root package name */
        Map<String, String> f16180b;

        /* renamed from: c, reason: collision with root package name */
        IQueryUrlsCallBack f16181c;

        /* renamed from: d, reason: collision with root package name */
        Context f16182d;

        /* renamed from: e, reason: collision with root package name */
        GrsBaseInfo f16183e;

        /* renamed from: f, reason: collision with root package name */
        com.huawei.hms.framework.network.grs.e.a f16184f;

        C0133a(String str, Map<String, String> map, IQueryUrlsCallBack iQueryUrlsCallBack, Context context, GrsBaseInfo grsBaseInfo, com.huawei.hms.framework.network.grs.e.a aVar) {
            this.f16179a = str;
            this.f16180b = map;
            this.f16181c = iQueryUrlsCallBack;
            this.f16182d = context;
            this.f16183e = grsBaseInfo;
            this.f16184f = aVar;
        }

        @Override // com.huawei.hms.framework.network.grs.b
        public void a() {
            Map<String, String> map = this.f16180b;
            if (map != null && !map.isEmpty()) {
                Logger.i(a.f16174e, "GrsClientManager.ayncGetGrsUrls: Return [%s] Urls: %s", this.f16179a, StringUtils.anonymizeMessage(new JSONObject(this.f16180b).toString()));
                this.f16181c.onCallBackSuccess(this.f16180b);
                return;
            }
            if (this.f16180b != null) {
                Logger.i(a.f16174e, "GrsClientManager.ayncGetGrsUrls：Return [%s] Urls is Empty", this.f16179a);
                this.f16181c.onCallBackFail(-3);
                return;
            }
            Logger.i(a.f16174e, "GrsClientManager.ayncGetGrsUrls: Get URL from Local JSON File");
            Map<String, String> mapA = com.huawei.hms.framework.network.grs.f.b.a(this.f16182d.getPackageName()).a(this.f16182d, this.f16184f, this.f16183e, this.f16179a, true);
            if (mapA == null || mapA.isEmpty()) {
                Logger.e(a.f16174e, "The serviceName[%s] is not configured in the JSON configuration files to reveal all the details", this.f16179a);
            }
            if (mapA == null) {
                mapA = new ConcurrentHashMap<>();
            }
            Logger.i(a.f16174e, "GrsClientManager.ayncGetGrsUrls: Return [%s] Urls: %s", this.f16179a, StringUtils.anonymizeMessage(new JSONObject(mapA).toString()));
            this.f16181c.onCallBackSuccess(mapA);
        }

        @Override // com.huawei.hms.framework.network.grs.b
        public void a(com.huawei.hms.framework.network.grs.g.d dVar) {
            String strJ = dVar.j();
            Map<String, String> mapA = a.a(strJ, this.f16179a);
            if (!mapA.isEmpty()) {
                Logger.i(a.f16174e, "GrsClientManager.ayncGetGrsUrls: Get URL from Current Called GRS Server Return [%s] Urls: %s", this.f16179a, StringUtils.anonymizeMessage(new JSONObject(mapA).toString()));
                this.f16181c.onCallBackSuccess(mapA);
                return;
            }
            Map<String, String> map = this.f16180b;
            if (map != null && !map.isEmpty()) {
                Logger.i(a.f16174e, "GrsClientManager.ayncGetGrsUrls: Return [%s][%s] Url: %s", this.f16179a, StringUtils.anonymizeMessage(new JSONObject(this.f16180b).toString()));
                this.f16181c.onCallBackSuccess(this.f16180b);
                return;
            }
            if (this.f16180b != null) {
                Logger.i(a.f16174e, "GrsClientManager.ayncGetGrsUrls：Return [%s] Urls is Empty", this.f16179a);
                this.f16181c.onCallBackFail(-5);
                return;
            }
            if (!TextUtils.isEmpty(strJ)) {
                Logger.e(a.f16174e, "The serviceName[%s] is not configured on the GRS server.", this.f16179a);
            }
            Logger.i(a.f16174e, "GrsClientManager.ayncGetGrsUrls: Get URL from Local JSON File");
            Map<String, String> mapA2 = com.huawei.hms.framework.network.grs.f.b.a(this.f16182d.getPackageName()).a(this.f16182d, this.f16184f, this.f16183e, this.f16179a, true);
            if (mapA2 == null || mapA2.isEmpty()) {
                Logger.e(a.f16174e, "The serviceName[%s] is not configured in the JSON configuration files to reveal all the details", this.f16179a);
            }
            if (mapA2 == null) {
                mapA2 = new ConcurrentHashMap<>();
            }
            Logger.i(a.f16174e, "GrsClientManager.ayncGetGrsUrls: Return [%s] Urls: %s", this.f16179a, StringUtils.anonymizeMessage(new JSONObject(mapA2).toString()));
            this.f16181c.onCallBackSuccess(mapA2);
        }
    }

    private static class b implements com.huawei.hms.framework.network.grs.b {

        /* renamed from: a, reason: collision with root package name */
        String f16185a;

        /* renamed from: b, reason: collision with root package name */
        String f16186b;

        /* renamed from: c, reason: collision with root package name */
        IQueryUrlCallBack f16187c;

        /* renamed from: d, reason: collision with root package name */
        String f16188d;

        /* renamed from: e, reason: collision with root package name */
        Context f16189e;

        /* renamed from: f, reason: collision with root package name */
        GrsBaseInfo f16190f;

        /* renamed from: g, reason: collision with root package name */
        com.huawei.hms.framework.network.grs.e.a f16191g;

        b(String str, String str2, IQueryUrlCallBack iQueryUrlCallBack, String str3, Context context, GrsBaseInfo grsBaseInfo, com.huawei.hms.framework.network.grs.e.a aVar) {
            this.f16185a = str;
            this.f16186b = str2;
            this.f16187c = iQueryUrlCallBack;
            this.f16188d = str3;
            this.f16189e = context;
            this.f16190f = grsBaseInfo;
            this.f16191g = aVar;
        }

        @Override // com.huawei.hms.framework.network.grs.b
        public void a() {
            if (!TextUtils.isEmpty(this.f16188d)) {
                Logger.i(a.f16174e, "GrsClientManager.ayncGetGrsUrl: Return [%s][%s] Url: %s", this.f16185a, this.f16186b, StringUtils.anonymizeMessage(this.f16188d));
                this.f16187c.onCallBackSuccess(this.f16188d);
                return;
            }
            if (!TextUtils.isEmpty(this.f16188d)) {
                Logger.i(a.f16174e, "GrsClientManager.ayncGetGrsUrl：Return [%s][%s] Url is Empty", this.f16185a, this.f16186b);
                this.f16187c.onCallBackFail(-3);
                return;
            }
            Logger.i(a.f16174e, "GrsClientManager.ayncGetGrsUrl: Get URL from Local JSON File");
            String strA = com.huawei.hms.framework.network.grs.f.b.a(this.f16189e.getPackageName()).a(this.f16189e, this.f16191g, this.f16190f, this.f16185a, this.f16186b, true);
            if (strA == null || strA.isEmpty()) {
                Logger.e(a.f16174e, "The serviceName[%s][%s] is not configured in the JSON configuration files to reveal all the details", this.f16185a, this.f16186b);
            }
            Logger.i(a.f16174e, "GrsClientManager.ayncGetGrsUrl: Return [%s][%s] Url: %s", this.f16185a, this.f16186b, StringUtils.anonymizeMessage(strA));
            this.f16187c.onCallBackSuccess(strA);
        }

        @Override // com.huawei.hms.framework.network.grs.b
        public void a(com.huawei.hms.framework.network.grs.g.d dVar) {
            IQueryUrlCallBack iQueryUrlCallBack;
            String str;
            String strJ = dVar.j();
            Map<String, String> mapA = a.a(strJ, this.f16185a);
            if (mapA.containsKey(this.f16186b)) {
                String str2 = a.f16174e;
                String str3 = this.f16185a;
                String str4 = this.f16186b;
                Logger.i(str2, "GrsClientManager.ayncGetGrsUrl: Get URL from Current Called GRS Server, Return [%s][%s] Url: %s", str3, str4, StringUtils.anonymizeMessage(mapA.get(str4)));
                iQueryUrlCallBack = this.f16187c;
                str = mapA.get(this.f16186b);
            } else {
                if (TextUtils.isEmpty(this.f16188d)) {
                    if (!TextUtils.isEmpty(this.f16188d)) {
                        Logger.i(a.f16174e, "GrsClientManager.ayncGetGrsUrl：Return [%s][%s] Url is Empty", this.f16185a, this.f16186b);
                        this.f16187c.onCallBackFail(-5);
                        return;
                    }
                    if (!TextUtils.isEmpty(strJ)) {
                        Logger.e(a.f16174e, "The serviceName[%s][%s] is not configured on the GRS server.", this.f16185a, this.f16186b);
                    }
                    Logger.i(a.f16174e, "GrsClientManager.ayncGetGrsUrl: Get URL from Local JSON File");
                    String strA = com.huawei.hms.framework.network.grs.f.b.a(this.f16189e.getPackageName()).a(this.f16189e, this.f16191g, this.f16190f, this.f16185a, this.f16186b, true);
                    if (strA == null || strA.isEmpty()) {
                        Logger.e(a.f16174e, "The serviceName[%s][%s] is not configured in the JSON configuration files to reveal all the details", this.f16185a, this.f16186b);
                    }
                    Logger.i(a.f16174e, "GrsClientManager.ayncGetGrsUrl: Return [%s][%s] Url: %s", this.f16185a, this.f16186b, StringUtils.anonymizeMessage(strA));
                    this.f16187c.onCallBackSuccess(strA);
                    return;
                }
                String str5 = a.f16174e;
                String str6 = this.f16185a;
                String str7 = this.f16186b;
                Logger.i(str5, "GrsClientManager.ayncGetGrsUrl: Return [%s][%s] Url: %s", str6, str7, StringUtils.anonymizeMessage(mapA.get(str7)));
                iQueryUrlCallBack = this.f16187c;
                str = this.f16188d;
            }
            iQueryUrlCallBack.onCallBackSuccess(str);
        }
    }

    public a(GrsBaseInfo grsBaseInfo, com.huawei.hms.framework.network.grs.e.a aVar, g gVar, com.huawei.hms.framework.network.grs.e.c cVar) {
        this.f16175a = grsBaseInfo;
        this.f16176b = aVar;
        this.f16177c = gVar;
        this.f16178d = cVar;
    }

    public static CountryCodeBean a(Context context, boolean z2) {
        return new CountryCodeBean(context, z2);
    }

    public String a(Context context, String str, int i2) {
        com.huawei.hms.framework.network.grs.g.d dVarA = this.f16177c.a(new com.huawei.hms.framework.network.grs.g.j.c(this.f16175a, context), str, this.f16178d, i2);
        return dVarA == null ? "" : dVarA.m() ? this.f16176b.a().a(this.f16175a.getGrsParasKey(true, true, context), "") : dVarA.j();
    }

    public String a(String str, String str2, Context context, int i2) {
        com.huawei.hms.framework.network.grs.e.b bVar = new com.huawei.hms.framework.network.grs.e.b();
        String strA = a(str, bVar, context).get(str2);
        if (bVar.a() && !TextUtils.isEmpty(strA)) {
            Logger.i(f16174e, "GrsClientManager.synGetGrsUrl: Return [%s][%s] Url: %s", str, str2, StringUtils.anonymizeMessage(strA));
            return strA;
        }
        String strA2 = a(context, str, i2);
        String str3 = a(strA2, str).get(str2);
        if (!TextUtils.isEmpty(str3)) {
            Logger.i(f16174e, "GrsClientManager.synGetGrsUrl: Get URL from Current Called GRS Server, Return [%s][%s] Url: %s", str, str2, StringUtils.anonymizeMessage(str3));
            return str3;
        }
        if (TextUtils.isEmpty(strA)) {
            if (!TextUtils.isEmpty(strA2)) {
                Logger.e(f16174e, "The serviceName[%s][%s] is not configured on the GRS server.", str, str2);
            }
            String str4 = f16174e;
            Logger.i(str4, "GrsClientManager.synGetGrsUrl: Get URL from Local JSON File.");
            strA = com.huawei.hms.framework.network.grs.f.b.a(context.getPackageName()).a(context, this.f16176b, this.f16175a, str, str2, true);
            if (strA == null || strA.isEmpty()) {
                Logger.e(str4, "The serviceName[%s][%s] is not configured in the JSON configuration files to reveal all the details", str, str2);
            }
        }
        Logger.i(f16174e, "GrsClientManager.synGetGrsUrl: Return [%s][%s] Url: %s", str, str2, StringUtils.anonymizeMessage(strA));
        return strA;
    }

    public static Map<String, Map<String, String>> a(String str) throws JSONException {
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap(16);
        if (TextUtils.isEmpty(str)) {
            Logger.v(f16174e, "isSpExpire jsonValue is null.");
            return concurrentHashMap;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                JSONObject jSONObject2 = jSONObject.getJSONObject(next);
                if (!TextUtils.isEmpty(next)) {
                    concurrentHashMap.put(next, a(jSONObject2));
                }
            }
            return concurrentHashMap;
        } catch (JSONException e2) {
            Logger.w(f16174e, "getServicesUrlsMap occur a JSONException: %s", StringUtils.anonymizeMessage(e2.getMessage()));
            return concurrentHashMap;
        }
    }

    public Map<String, String> a(String str, Context context, int i2) {
        com.huawei.hms.framework.network.grs.e.b bVar = new com.huawei.hms.framework.network.grs.e.b();
        Map<String, String> mapA = a(str, bVar, context);
        if (bVar.a() && !mapA.isEmpty()) {
            Logger.i(f16174e, "Return [%s] Urls: %s", str, StringUtils.anonymizeMessage(new JSONObject(mapA).toString()));
            return mapA;
        }
        String strA = a(context, str, i2);
        Map<String, String> mapA2 = a(strA, str);
        if (!mapA2.isEmpty()) {
            Logger.i(f16174e, "GrsClientManager.synGetGrsUrls: Get URL from Current Called GRS Server Return [%s] Urls: %s", str, StringUtils.anonymizeMessage(new JSONObject(mapA2).toString()));
            return mapA2;
        }
        if (mapA.isEmpty()) {
            if (!TextUtils.isEmpty(strA)) {
                Logger.e(f16174e, "The serviceName[%s] is not configured on the GRS server.", str);
            }
            String str2 = f16174e;
            Logger.i(str2, "GrsClientManager.synGetGrsUrls: Get URL from Local JSON File.");
            mapA = com.huawei.hms.framework.network.grs.f.b.a(context.getPackageName()).a(context, this.f16176b, this.f16175a, str, true);
            if (mapA == null || mapA.isEmpty()) {
                Logger.e(str2, "The serviceName[%s] is not configured in the JSON configuration files to reveal all the details", str);
            }
        }
        Logger.i(f16174e, "GrsClientManager.synGetGrsUrls: Return [%s] Urls: %s", str, StringUtils.anonymizeMessage(mapA != null ? new JSONObject(mapA).toString() : ""));
        return mapA;
    }

    private Map<String, String> a(String str, com.huawei.hms.framework.network.grs.e.b bVar, Context context) {
        Map<String, String> mapA = this.f16176b.a(this.f16175a, str, bVar, context);
        if (mapA != null && !mapA.isEmpty()) {
            Logger.i(f16174e, "GrsClientManager.getUrlsLocal: Get URL from GRS Server Cache");
            return mapA;
        }
        Map<String, String> mapA2 = com.huawei.hms.framework.network.grs.f.b.a(context.getPackageName()).a(context, this.f16176b, this.f16175a, str, false);
        Logger.i(f16174e, "GrsClientManager.getUrlsLocal: Get URL from Local JSON File");
        return mapA2 != null ? mapA2 : new HashMap();
    }

    public static Map<String, String> a(String str, String str2) {
        HashMap map = new HashMap();
        if (TextUtils.isEmpty(str)) {
            Logger.w(f16174e, "isSpExpire jsonValue from server is null.");
            return map;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            JSONObject jSONObject2 = jSONObject.has(str2) ? jSONObject.getJSONObject(str2) : null;
            if (jSONObject2 == null) {
                Logger.w(f16174e, "getServiceNameUrls: paser null from server json data by {%s}.", str2);
                return map;
            }
            Iterator<String> itKeys = jSONObject2.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                map.put(next, jSONObject2.get(next).toString());
            }
            return map;
        } catch (JSONException e2) {
            Logger.w(f16174e, "Method{getServiceNameUrls} query url from SP occur an JSONException: %s", StringUtils.anonymizeMessage(e2.getMessage()));
            return map;
        }
    }

    public static Map<String, String> a(JSONObject jSONObject) {
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap(16);
        try {
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                String string = jSONObject.get(next).toString();
                if (!TextUtils.isEmpty(next) && !TextUtils.isEmpty(string)) {
                    concurrentHashMap.put(next, string);
                }
            }
            return concurrentHashMap;
        } catch (JSONException e2) {
            Logger.w(f16174e, "getServiceUrls occur a JSONException: %s", StringUtils.anonymizeMessage(e2.getMessage()));
            return concurrentHashMap;
        }
    }

    public void a(String str, IQueryUrlsCallBack iQueryUrlsCallBack, Context context, int i2) {
        com.huawei.hms.framework.network.grs.e.b bVar = new com.huawei.hms.framework.network.grs.e.b();
        Map<String, String> mapA = a(str, bVar, context);
        if (!bVar.a()) {
            this.f16177c.a(new com.huawei.hms.framework.network.grs.g.j.c(this.f16175a, context), new C0133a(str, mapA, iQueryUrlsCallBack, context, this.f16175a, this.f16176b), str, this.f16178d, i2);
            return;
        }
        if (mapA.isEmpty()) {
            Logger.i(f16174e, "GrsClientManager.ayncGetGrsUrls：Return [%s] Urls is Empty", str);
            iQueryUrlsCallBack.onCallBackFail(-5);
        } else {
            String str2 = f16174e;
            Logger.i(str2, "GrsClientManager.ayncGetGrsUrls：Return [%s] Urls: %s", str, StringUtils.anonymizeMessage(new JSONObject(mapA).toString()));
            Logger.i(str2, "ayncGetGrsUrls: %s", StringUtils.anonymizeMessage(new JSONObject(mapA).toString()));
            iQueryUrlsCallBack.onCallBackSuccess(mapA);
        }
    }

    public void a(String str, String str2, IQueryUrlCallBack iQueryUrlCallBack, Context context, int i2) {
        com.huawei.hms.framework.network.grs.e.b bVar = new com.huawei.hms.framework.network.grs.e.b();
        String str3 = a(str, bVar, context).get(str2);
        if (!bVar.a()) {
            this.f16177c.a(new com.huawei.hms.framework.network.grs.g.j.c(this.f16175a, context), new b(str, str2, iQueryUrlCallBack, str3, context, this.f16175a, this.f16176b), str, this.f16178d, i2);
        } else if (TextUtils.isEmpty(str3)) {
            Logger.i(f16174e, "GrsClientManager.ayncGetGrsUrl：Return [%s][%s] Url is Empty", str, str2);
            iQueryUrlCallBack.onCallBackFail(-5);
        } else {
            Logger.i(f16174e, "GrsClientManager.ayncGetGrsUrl：Return [%s][%s] Url: %s", str, str2, StringUtils.anonymizeMessage(str3));
            iQueryUrlCallBack.onCallBackSuccess(str3);
        }
    }
}
