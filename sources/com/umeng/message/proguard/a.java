package com.umeng.message.proguard;

import android.app.Application;
import android.content.res.Resources;
import android.text.TextUtils;
import com.umeng.commonsdk.debug.UMLog;
import com.umeng.message.PushAgent;
import com.umeng.message.common.UPLog;

/* loaded from: classes4.dex */
public final class a {

    /* renamed from: b, reason: collision with root package name */
    private static a f22702b;

    /* renamed from: a, reason: collision with root package name */
    public Class<?> f22703a;

    /* renamed from: c, reason: collision with root package name */
    private Class<?> f22704c;

    /* renamed from: d, reason: collision with root package name */
    private Class<?> f22705d;

    /* renamed from: e, reason: collision with root package name */
    private Class<?> f22706e;

    private a() throws ClassNotFoundException {
        Application applicationA = x.a();
        String resourcePackageName = PushAgent.getInstance(applicationA).getResourcePackageName();
        resourcePackageName = TextUtils.isEmpty(resourcePackageName) ? applicationA.getPackageName() : resourcePackageName;
        UPLog.d("R2", "resPackageName:", resourcePackageName);
        try {
            this.f22705d = Class.forName(resourcePackageName + ".R$drawable");
        } catch (ClassNotFoundException e2) {
            UPLog.d("R2", UPLog.getStackTrace(e2));
            UMLog.aq(ab.f22708b, 0, "\\|");
        }
        try {
            this.f22703a = Class.forName(resourcePackageName + ".R$layout");
        } catch (ClassNotFoundException e3) {
            UPLog.d("R2", UPLog.getStackTrace(e3));
        }
        try {
            this.f22704c = Class.forName(resourcePackageName + ".R$id");
        } catch (ClassNotFoundException e4) {
            UPLog.d("R2", UPLog.getStackTrace(e4));
        }
        try {
            this.f22706e = Class.forName(resourcePackageName + ".R$raw");
        } catch (ClassNotFoundException e5) {
            UPLog.d("R2", UPLog.getStackTrace(e5));
        }
    }

    public static int a(String str) {
        return a(a().f22704c, str);
    }

    public final int b(String str) {
        return a(this.f22705d, str);
    }

    public final int c(String str) {
        return a(this.f22706e, str);
    }

    public static a a() {
        if (f22702b == null) {
            f22702b = new a();
        }
        return f22702b;
    }

    public static int a(Class<?> cls, String str) throws ClassNotFoundException {
        if (cls != null) {
            return cls.getField(str).getInt(str);
        }
        UPLog.e("R2", "getRes(null,", str, ")");
        throw new Resources.NotFoundException(x.a().getPackageName() + ".R$* field=" + str + " not exist.");
    }
}
