package com.huawei.hms.framework.network.grs.g;

import android.content.Context;
import android.text.TextUtils;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.huawei.hms.framework.common.Logger;
import com.huawei.hms.framework.common.StringUtils;
import com.huawei.hms.framework.network.grs.GrsBaseInfo;
import org.json.JSONException;

/* loaded from: classes4.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    private final Context f16236a;

    /* renamed from: b, reason: collision with root package name */
    private final GrsBaseInfo f16237b;

    /* renamed from: c, reason: collision with root package name */
    private final com.huawei.hms.framework.network.grs.e.a f16238c;

    public b(Context context, com.huawei.hms.framework.network.grs.e.a aVar, GrsBaseInfo grsBaseInfo) {
        this.f16236a = context;
        this.f16237b = grsBaseInfo;
        this.f16238c = aVar;
    }

    public String a(boolean z2) {
        String strA;
        String str = com.huawei.hms.framework.network.grs.a.a(this.f16238c.a().a("geoipCountryCode", ""), "geoip.countrycode").get("ROOT");
        Logger.i("GeoipCountry", "geoIpCountry is: " + str);
        String strA2 = this.f16238c.a().a("geoipCountryCodetime", "0");
        long j2 = 0;
        if (!TextUtils.isEmpty(strA2) && strA2.matches("\\d+")) {
            try {
                j2 = Long.parseLong(strA2);
            } catch (NumberFormatException e2) {
                Logger.w("GeoipCountry", "convert urlParamKey from String to Long catch NumberFormatException.", e2);
            }
        }
        if (TextUtils.isEmpty(str) || com.huawei.hms.framework.network.grs.h.e.a(Long.valueOf(j2))) {
            com.huawei.hms.framework.network.grs.g.j.c cVar = new com.huawei.hms.framework.network.grs.g.j.c(this.f16237b, this.f16236a);
            cVar.a("geoip.countrycode");
            com.huawei.hms.framework.network.grs.e.c cVarC = this.f16238c.c();
            if (cVarC != null) {
                try {
                    strA = h.a(cVarC.a(TmpConstant.DEVICE_MODEL_SERVICES, ""), cVar.c());
                } catch (JSONException e3) {
                    Logger.w("GeoipCountry", "getGeoipCountry merge services occure jsonException. %s", StringUtils.anonymizeMessage(e3.getMessage()));
                    strA = null;
                }
                if (!TextUtils.isEmpty(strA)) {
                    cVarC.b(TmpConstant.DEVICE_MODEL_SERVICES, strA);
                }
            }
            if (z2) {
                d dVarA = this.f16238c.b().a(cVar, "geoip.countrycode", cVarC, -1);
                if (dVarA != null) {
                    str = com.huawei.hms.framework.network.grs.a.a(dVarA.j(), "geoip.countrycode").get("ROOT");
                }
                Logger.i("GeoipCountry", "sync request to query geoip.countrycode is:" + str);
            } else {
                Logger.i("GeoipCountry", "async request to query geoip.countrycode");
                this.f16238c.b().a(cVar, null, "geoip.countrycode", cVarC, -1);
            }
        }
        return str;
    }
}
