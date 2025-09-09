package com.xiaomi.push;

import android.content.Context;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: classes4.dex */
class av implements ar {

    /* renamed from: a, reason: collision with root package name */
    private Context f23458a;

    /* renamed from: a, reason: collision with other field name */
    private Class<?> f192a;

    /* renamed from: a, reason: collision with other field name */
    private Object f193a;

    /* renamed from: a, reason: collision with other field name */
    private Method f194a = null;

    /* renamed from: b, reason: collision with root package name */
    private Method f23459b = null;

    /* renamed from: c, reason: collision with root package name */
    private Method f23460c = null;

    /* renamed from: d, reason: collision with root package name */
    private Method f23461d = null;

    public av(Context context) {
        this.f23458a = context;
        a(context);
    }

    private void a(Context context) {
        try {
            Class<?> clsA = C0472r.a(context, "com.android.id.impl.IdProviderImpl");
            this.f192a = clsA;
            this.f193a = clsA.newInstance();
            this.f23459b = this.f192a.getMethod("getOAID", Context.class);
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.a("miui load class error", e2);
        }
    }

    @Override // com.xiaomi.push.ar
    /* renamed from: a */
    public boolean mo180a() {
        return (this.f192a == null || this.f193a == null) ? false : true;
    }

    @Override // com.xiaomi.push.ar
    /* renamed from: a */
    public String mo179a() {
        return a(this.f23458a, this.f23459b);
    }

    private String a(Context context, Method method) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Object obj = this.f193a;
        if (obj == null || method == null) {
            return null;
        }
        try {
            Object objInvoke = method.invoke(obj, context);
            if (objInvoke != null) {
                return (String) objInvoke;
            }
            return null;
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.a("miui invoke error", e2);
            return null;
        }
    }
}
