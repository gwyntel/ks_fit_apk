package com.huawei.hms.hatool;

import android.content.Context;
import android.text.TextUtils;
import java.util.LinkedHashMap;
import org.json.JSONException;

/* loaded from: classes4.dex */
public class z0 {

    /* renamed from: a, reason: collision with root package name */
    private String f16505a;

    /* renamed from: b, reason: collision with root package name */
    public l1 f16506b;

    public z0(String str) {
        this.f16505a = str;
        this.f16506b = new l1(str);
        s.c().a(this.f16505a, this.f16506b);
    }

    private s0 b(int i2) {
        if (i2 == 0) {
            return this.f16506b.c();
        }
        if (i2 == 1) {
            return this.f16506b.b();
        }
        if (i2 == 2) {
            return this.f16506b.d();
        }
        if (i2 != 3) {
            return null;
        }
        return this.f16506b.a();
    }

    private boolean c(int i2) {
        String str;
        if (i2 != 2) {
            s0 s0VarB = b(i2);
            if (s0VarB != null && !TextUtils.isEmpty(s0VarB.h())) {
                return true;
            }
            str = "verifyURL(): URL check failed. type: " + i2;
        } else {
            if ("_default_config_tag".equals(this.f16505a)) {
                return true;
            }
            str = "verifyURL(): type: preins. Only default config can report Pre-install data.";
        }
        v.e("hmsSdk", str);
        return false;
    }

    public void a(int i2) {
        v.d("hmsSdk", "onReport. TAG: " + this.f16505a + ", TYPE: " + i2);
        g0.a().a(this.f16505a, i2);
    }

    public void a(int i2, String str, LinkedHashMap<String, String> linkedHashMap) throws JSONException {
        v.d("hmsSdk", "onEvent. TAG: " + this.f16505a + ", TYPE: " + i2 + ", eventId : " + str);
        if (e1.a(str) || !c(i2)) {
            v.e("hmsSdk", "onEvent() parameters check fail. Nothing will be recorded.TAG: " + this.f16505a + ", TYPE: " + i2);
            return;
        }
        if (!e1.a(linkedHashMap)) {
            v.e("hmsSdk", "onEvent() parameter mapValue will be cleared.TAG: " + this.f16505a + ", TYPE: " + i2);
            linkedHashMap = null;
        }
        g0.a().a(this.f16505a, i2, str, linkedHashMap);
    }

    public void b(int i2, String str, LinkedHashMap<String, String> linkedHashMap) throws JSONException {
        v.d("hmsSdk", "onStreamEvent. TAG: " + this.f16505a + ", TYPE: " + i2 + ", eventId : " + str);
        if (e1.a(str) || !c(i2)) {
            v.e("hmsSdk", "onStreamEvent() parameters check fail. Nothing will be recorded.TAG: " + this.f16505a + ", TYPE: " + i2);
            return;
        }
        if (!e1.a(linkedHashMap)) {
            v.e("hmsSdk", "onStreamEvent() parameter mapValue will be cleared.TAG: " + this.f16505a + ", TYPE: " + i2);
            linkedHashMap = null;
        }
        g0.a().b(this.f16505a, i2, str, linkedHashMap);
    }

    public void a(Context context, String str, String str2) throws JSONException {
        v.d("hmsSdk", "onEvent(context). TAG: " + this.f16505a + ", eventId : " + str);
        if (context == null) {
            v.e("hmsSdk", "context is null in onevent ");
            return;
        }
        if (e1.a(str) || !c(0)) {
            v.e("hmsSdk", "onEvent() parameters check fail. Nothing will be recorded.TAG: " + this.f16505a);
            return;
        }
        if (!e1.a("value", str2, 65536)) {
            v.e("hmsSdk", "onEvent() parameter VALUE is overlong, content will be cleared.TAG: " + this.f16505a);
            str2 = "";
        }
        g0.a().a(this.f16505a, context, str, str2);
    }

    public void b(s0 s0Var) {
        v.c("hmsSdk", "HiAnalyticsInstanceImpl.setOperConf() is executed.TAG: " + this.f16505a);
        if (s0Var != null) {
            this.f16506b.b(s0Var);
        } else {
            this.f16506b.b(null);
            v.e("hmsSdk", "HiAnalyticsInstanceImpl.setOperConf(): config for oper is null!");
        }
    }

    public void a(s0 s0Var) {
        v.c("hmsSdk", "HiAnalyticsInstanceImpl.setMaintConf() is executed.TAG : " + this.f16505a);
        if (s0Var != null) {
            this.f16506b.a(s0Var);
        } else {
            v.e("hmsSdk", "HiAnalyticsInstanceImpl.setMaintConf(): config for maint is null!");
            this.f16506b.a((s0) null);
        }
    }
}
