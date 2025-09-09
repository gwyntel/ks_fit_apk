package com.alipay.sdk.m.j;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.alibaba.ailabs.iot.aisbase.plugin.ota.ReportProgressUtil;
import com.alipay.sdk.m.m.a;
import com.alipay.sdk.m.u.n;
import java.util.Collections;
import java.util.List;
import org.android.agoo.common.AgooConstants;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class d {

    /* renamed from: a, reason: collision with root package name */
    public static final int f9330a = 1010;

    /* renamed from: b, reason: collision with root package name */
    public static a f9331b;

    public interface a {
        void a(boolean z2, JSONObject jSONObject, String str);
    }

    public static boolean a(com.alipay.sdk.m.s.a aVar, Context context) {
        return n.a(aVar, context, (List<a.b>) Collections.singletonList(new a.b(AgooConstants.TAOBAO_PACKAGE, 0, "")), false);
    }

    public static boolean a(com.alipay.sdk.m.s.a aVar, Activity activity, int i2, String str, String str2, a aVar2) {
        try {
            com.alipay.sdk.m.k.a.a(aVar, com.alipay.sdk.m.k.b.f9364l, com.alipay.sdk.m.k.b.f9377r0);
            activity.startActivityForResult(new Intent(str2, Uri.parse(str)), i2);
            f9331b = aVar2;
            return true;
        } catch (Throwable th) {
            aVar2.a(false, null, "UNKNOWN_ERROR");
            com.alipay.sdk.m.k.a.a(aVar, com.alipay.sdk.m.k.b.f9364l, com.alipay.sdk.m.k.b.f9385v0, th);
            return false;
        }
    }

    public static boolean a(com.alipay.sdk.m.s.a aVar, int i2, int i3, Intent intent) {
        if (i2 != 1010 || intent == null) {
            return false;
        }
        a aVar2 = f9331b;
        if (aVar2 == null) {
            return true;
        }
        f9331b = null;
        if (i3 == -1) {
            com.alipay.sdk.m.k.a.a(aVar, com.alipay.sdk.m.k.b.f9364l, com.alipay.sdk.m.k.b.f9383u0, intent.toUri(1));
            aVar2.a(true, n.a(intent), ReportProgressUtil.CODE_OK);
        } else if (i3 != 0) {
            com.alipay.sdk.m.k.a.b(aVar, com.alipay.sdk.m.k.b.f9364l, com.alipay.sdk.m.k.b.f9381t0, "" + i3);
        } else {
            com.alipay.sdk.m.k.a.a(aVar, com.alipay.sdk.m.k.b.f9364l, com.alipay.sdk.m.k.b.f9379s0, intent.toUri(1));
            aVar2.a(false, null, "CANCELED");
        }
        return true;
    }
}
