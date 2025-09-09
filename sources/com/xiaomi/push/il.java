package com.xiaomi.push;

import android.content.Context;
import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class il {

    /* renamed from: a, reason: collision with root package name */
    private static volatile il f23989a;

    /* renamed from: a, reason: collision with other field name */
    private final Context f580a;

    /* renamed from: a, reason: collision with other field name */
    private Map<String, im> f581a = new HashMap();

    private il(Context context) {
        this.f580a = context;
    }

    public static il a(Context context) {
        if (context == null) {
            com.xiaomi.channel.commonutils.logger.b.d("[TinyDataManager]:mContext is null, TinyDataManager.getInstance(Context) failed.");
            return null;
        }
        if (f23989a == null) {
            synchronized (il.class) {
                try {
                    if (f23989a == null) {
                        f23989a = new il(context);
                    }
                } finally {
                }
            }
        }
        return f23989a;
    }

    public void a(im imVar, String str) {
        if (imVar == null) {
            com.xiaomi.channel.commonutils.logger.b.d("[TinyDataManager]: please do not add null mUploader to TinyDataManager.");
        } else if (TextUtils.isEmpty(str)) {
            com.xiaomi.channel.commonutils.logger.b.d("[TinyDataManager]: can not add a provider from unkown resource.");
        } else {
            m512a().put(str, imVar);
        }
    }

    im a() {
        im imVar = this.f581a.get("UPLOADER_PUSH_CHANNEL");
        if (imVar != null) {
            return imVar;
        }
        im imVar2 = this.f581a.get("UPLOADER_HTTP");
        if (imVar2 != null) {
            return imVar2;
        }
        return null;
    }

    /* renamed from: a, reason: collision with other method in class */
    Map<String, im> m512a() {
        return this.f581a;
    }

    public boolean a(ir irVar, String str) {
        if (TextUtils.isEmpty(str)) {
            com.xiaomi.channel.commonutils.logger.b.m91a("pkgName is null or empty, upload ClientUploadDataItem failed.");
            return false;
        }
        if (com.xiaomi.push.service.ca.a(irVar, false)) {
            return false;
        }
        if (TextUtils.isEmpty(irVar.d())) {
            irVar.f(com.xiaomi.push.service.ca.a());
        }
        irVar.g(str);
        com.xiaomi.push.service.cb.a(this.f580a, irVar);
        return true;
    }

    public boolean a(String str, String str2, long j2, String str3) {
        return a(this.f580a.getPackageName(), this.f580a.getPackageName(), str, str2, j2, str3);
    }

    private boolean a(String str, String str2, String str3, String str4, long j2, String str5) {
        ir irVar = new ir();
        irVar.d(str3);
        irVar.c(str4);
        irVar.a(j2);
        irVar.b(str5);
        irVar.a(true);
        irVar.a("push_sdk_channel");
        irVar.e(str2);
        return a(irVar, str);
    }
}
