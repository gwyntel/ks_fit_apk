package com.xiaomi.push.service;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import com.aliyun.alink.linksdk.alcs.lpbs.api.AlcsPalConst;
import com.aliyun.alink.linksdk.tmp.device.panel.data.InvokeServiceData;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import com.xiaomi.push.BuildConfig;
import com.xiaomi.push.C0472r;
import com.xiaomi.push.s;
import com.xiaomi.push.service.bf;
import java.util.Locale;

/* loaded from: classes4.dex */
public class u {

    /* renamed from: a, reason: collision with root package name */
    public final int f24623a;

    /* renamed from: a, reason: collision with other field name */
    public final String f1111a;

    /* renamed from: b, reason: collision with root package name */
    public final String f24624b;

    /* renamed from: c, reason: collision with root package name */
    public final String f24625c;

    /* renamed from: d, reason: collision with root package name */
    public final String f24626d;

    /* renamed from: e, reason: collision with root package name */
    public final String f24627e;

    /* renamed from: f, reason: collision with root package name */
    public final String f24628f;

    public u(String str, String str2, String str3, String str4, String str5, String str6, int i2) {
        this.f1111a = str;
        this.f24624b = str2;
        this.f24625c = str3;
        this.f24626d = str4;
        this.f24627e = str5;
        this.f24628f = str6;
        this.f24623a = i2;
    }

    private static boolean b(Context context) {
        return context.getPackageName().equals("com.xiaomi.xmsf");
    }

    public bf.b a(XMPushService xMPushService) {
        bf.b bVar = new bf.b(xMPushService);
        a(bVar, xMPushService, xMPushService.m713b(), com.umeng.analytics.pro.bc.aL);
        return bVar;
    }

    public bf.b a(bf.b bVar, Context context, k kVar, String str) {
        bVar.f1040a = context.getPackageName();
        bVar.f1043b = this.f1111a;
        bVar.f24504h = this.f24625c;
        bVar.f24499c = this.f24624b;
        bVar.f24503g = AlcsPalConst.MODEL_TYPE_TGMESH;
        bVar.f24500d = "XMPUSH-PASS";
        bVar.f1042a = false;
        s.a aVar = new s.a();
        aVar.a(HiAnalyticsConstant.BI_KEY_SDK_VER, 48).a("cpvn", BuildConfig.VERSION_NAME).a("cpvc", Integer.valueOf(BuildConfig.VERSION_CODE)).a("country_code", a.a(context).b()).a("region", a.a(context).a()).a("miui_vn", com.xiaomi.push.j.c()).a("miui_vc", Integer.valueOf(com.xiaomi.push.j.a(context))).a("xmsf_vc", Integer.valueOf(com.xiaomi.push.g.a(context, "com.xiaomi.xmsf"))).a("android_ver", Integer.valueOf(Build.VERSION.SDK_INT)).a("n_belong_to_app", Boolean.valueOf(aw.m741a(context))).a("systemui_vc", Integer.valueOf(com.xiaomi.push.g.a(context)));
        String strA = a(context);
        if (!TextUtils.isEmpty(strA)) {
            aVar.a("latest_country_code", strA);
        }
        String strD = com.xiaomi.push.j.d();
        if (!TextUtils.isEmpty(strD)) {
            aVar.a("device_ch", strD);
        }
        String strE = com.xiaomi.push.j.e();
        if (!TextUtils.isEmpty(strE)) {
            aVar.a("device_mfr", strE);
        }
        bVar.f24501e = aVar.toString();
        String str2 = b(context) ? "1000271" : this.f24626d;
        s.a aVar2 = new s.a();
        aVar2.a("appid", str2).a("locale", Locale.getDefault().toString()).a(InvokeServiceData.CALL_TYPE_SYNC, 1);
        if (m801a(context)) {
            aVar2.a("ab", str);
        }
        bVar.f24502f = aVar2.toString();
        bVar.f1039a = kVar;
        return bVar;
    }

    /* renamed from: a, reason: collision with other method in class */
    public static boolean m801a(Context context) {
        return "com.xiaomi.xmsf".equals(context.getPackageName()) && a();
    }

    public static boolean a() {
        try {
            return C0472r.a(null, "miui.os.Build").getField("IS_ALPHA_BUILD").getBoolean(null);
        } catch (Exception unused) {
            return false;
        }
    }

    private static String a(Context context) {
        if ("com.xiaomi.xmsf".equals(context)) {
            if (!TextUtils.isEmpty(null)) {
                return null;
            }
            String strM547a = com.xiaomi.push.j.m547a("ro.miui.region");
            return TextUtils.isEmpty(strM547a) ? com.xiaomi.push.j.m547a("ro.product.locale.region") : strM547a;
        }
        return com.xiaomi.push.j.b();
    }
}
