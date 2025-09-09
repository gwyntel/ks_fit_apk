package com.xiaomi.push;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.util.HashMap;

/* loaded from: classes4.dex */
public final class fd {

    /* renamed from: a, reason: collision with root package name */
    private static volatile fd f23747a;

    /* renamed from: a, reason: collision with other field name */
    private int f413a;

    /* renamed from: a, reason: collision with other field name */
    private Context f414a;

    /* renamed from: a, reason: collision with other field name */
    private fh f415a;

    /* renamed from: a, reason: collision with other field name */
    private String f416a;

    /* renamed from: a, reason: collision with other field name */
    private HashMap<ff, fg> f417a;

    /* renamed from: b, reason: collision with root package name */
    private String f23748b;

    private fd(Context context) {
        HashMap<ff, fg> map = new HashMap<>();
        this.f417a = map;
        this.f414a = context;
        map.put(ff.SERVICE_ACTION, new fj());
        this.f417a.put(ff.SERVICE_COMPONENT, new fk());
        this.f417a.put(ff.ACTIVITY, new fb());
        this.f417a.put(ff.PROVIDER, new fi());
    }

    public String b() {
        return this.f23748b;
    }

    public static fd a(Context context) {
        if (f23747a == null) {
            synchronized (fd.class) {
                try {
                    if (f23747a == null) {
                        f23747a = new fd(context);
                    }
                } finally {
                }
            }
        }
        return f23747a;
    }

    public void b(String str) {
        this.f23748b = str;
    }

    public void a(String str, String str2, int i2, fh fhVar) {
        a(str);
        b(str2);
        a(i2);
        a(fhVar);
    }

    /* renamed from: a, reason: collision with other method in class */
    public fh m401a() {
        return this.f415a;
    }

    public void a(fh fhVar) {
        this.f415a = fhVar;
    }

    /* renamed from: a, reason: collision with other method in class */
    public String m402a() {
        return this.f416a;
    }

    public void a(String str) {
        this.f416a = str;
    }

    public int a() {
        return this.f413a;
    }

    public void a(int i2) {
        this.f413a = i2;
    }

    public void a(Context context, String str, int i2, String str2, String str3) {
        if (context != null && !TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str3)) {
            a(i2);
            ah.a(this.f414a).a(new fe(this, str, context, str2, str3));
        } else {
            ez.a(context, "" + str, 1008, "A receive a incorrect message");
        }
    }

    public void a(ff ffVar, Context context, Intent intent, String str) {
        if (ffVar != null) {
            this.f417a.get(ffVar).a(context, intent, str);
        } else {
            ez.a(context, TmpConstant.GROUP_ROLE_UNKNOWN, 1008, "A receive a incorrect message with empty type");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(ff ffVar, Context context, fc fcVar) {
        this.f417a.get(ffVar).a(context, fcVar);
    }

    /* renamed from: a, reason: collision with other method in class */
    public static boolean m400a(Context context) {
        return com.xiaomi.push.service.al.m725a(context, context.getPackageName());
    }
}
