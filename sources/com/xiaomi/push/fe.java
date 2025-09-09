package com.xiaomi.push;

import android.content.Context;
import android.text.TextUtils;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
class fe implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Context f23749a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ fd f418a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ String f419a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ String f23750b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ String f23751c;

    fe(fd fdVar, String str, Context context, String str2, String str3) {
        this.f418a = fdVar;
        this.f419a = str;
        this.f23749a = context;
        this.f23750b = str2;
        this.f23751c = str3;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (TextUtils.isEmpty(this.f419a)) {
            ez.a(this.f23749a, TmpConstant.GROUP_ROLE_UNKNOWN, 1008, "A receive a incorrect message with empty info");
            return;
        }
        try {
            ez.a(this.f23749a, this.f419a, 1001, "get message");
            JSONObject jSONObject = new JSONObject(this.f419a);
            String strOptString = jSONObject.optString("action");
            String strOptString2 = jSONObject.optString("awakened_app_packagename");
            String strOptString3 = jSONObject.optString("awake_app_packagename");
            String strOptString4 = jSONObject.optString("awake_app");
            String strOptString5 = jSONObject.optString("awake_type");
            int iOptInt = jSONObject.optInt("awake_foreground", 0);
            if (this.f23750b.equals(strOptString3) && this.f23751c.equals(strOptString4)) {
                if (TextUtils.isEmpty(strOptString5) || TextUtils.isEmpty(strOptString3) || TextUtils.isEmpty(strOptString4) || TextUtils.isEmpty(strOptString2)) {
                    ez.a(this.f23749a, this.f419a, 1008, "A receive a incorrect message with empty type");
                    return;
                }
                this.f418a.b(strOptString3);
                this.f418a.a(strOptString4);
                fc fcVar = new fc();
                fcVar.b(strOptString);
                fcVar.a(strOptString2);
                fcVar.a(iOptInt);
                fcVar.d(this.f419a);
                if ("service".equals(strOptString5)) {
                    if (!TextUtils.isEmpty(strOptString)) {
                        this.f418a.a(ff.SERVICE_ACTION, this.f23749a, fcVar);
                        return;
                    } else {
                        fcVar.c("com.xiaomi.mipush.sdk.PushMessageHandler");
                        this.f418a.a(ff.SERVICE_COMPONENT, this.f23749a, fcVar);
                        return;
                    }
                }
                ff ffVar = ff.ACTIVITY;
                if (ffVar.f421a.equals(strOptString5)) {
                    this.f418a.a(ffVar, this.f23749a, fcVar);
                    return;
                }
                ff ffVar2 = ff.PROVIDER;
                if (ffVar2.f421a.equals(strOptString5)) {
                    this.f418a.a(ffVar2, this.f23749a, fcVar);
                    return;
                }
                ez.a(this.f23749a, this.f419a, 1008, "A receive a incorrect message with unknown type " + strOptString5);
                return;
            }
            ez.a(this.f23749a, this.f419a, 1008, "A receive a incorrect message with incorrect package info" + strOptString3);
        } catch (JSONException e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
            ez.a(this.f23749a, this.f419a, 1008, "A meet a exception when receive the message");
        }
    }
}
