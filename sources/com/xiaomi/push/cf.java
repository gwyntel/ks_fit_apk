package com.xiaomi.push;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.xiaomi.push.ah;
import com.xiaomi.push.cr;

/* loaded from: classes4.dex */
public class cf {

    /* renamed from: a, reason: collision with root package name */
    private static volatile cf f23527a;

    /* renamed from: a, reason: collision with other field name */
    private Context f228a;

    /* renamed from: a, reason: collision with other field name */
    private cu f230a;

    /* renamed from: a, reason: collision with other field name */
    private cv f231a;

    /* renamed from: e, reason: collision with root package name */
    private String f23531e;

    /* renamed from: f, reason: collision with root package name */
    private String f23532f;

    /* renamed from: a, reason: collision with other field name */
    private final String f232a = "push_stat_sp";

    /* renamed from: b, reason: collision with other field name */
    private final String f233b = "upload_time";

    /* renamed from: c, reason: collision with other field name */
    private final String f234c = "delete_time";

    /* renamed from: d, reason: collision with root package name */
    private final String f23530d = "check_time";

    /* renamed from: a, reason: collision with other field name */
    private ah.a f229a = new cg(this);

    /* renamed from: b, reason: collision with root package name */
    private ah.a f23528b = new ch(this);

    /* renamed from: c, reason: collision with root package name */
    private ah.a f23529c = new ci(this);

    private cf(Context context) {
        this.f228a = context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(String str) {
        SharedPreferences.Editor editorEdit = this.f228a.getSharedPreferences("push_stat_sp", 0).edit();
        editorEdit.putLong(str, System.currentTimeMillis());
        p.a(editorEdit);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String c() {
        return this.f228a.getDatabasePath(cj.f236a).getAbsolutePath();
    }

    public static cf a(Context context) {
        if (f23527a == null) {
            synchronized (cf.class) {
                try {
                    if (f23527a == null) {
                        f23527a = new cf(context);
                    }
                } finally {
                }
            }
        }
        return f23527a;
    }

    public String b() {
        return this.f23532f;
    }

    private boolean a() {
        return com.xiaomi.push.service.az.a(this.f228a).a(is.StatDataSwitch.a(), true);
    }

    public void a(cr.a aVar) {
        cr.a(this.f228a).a(aVar);
    }

    public void a(String str, String str2, Boolean bool) {
        if (this.f230a != null) {
            if (bool.booleanValue()) {
                this.f230a.a(this.f228a, str2, str);
            } else {
                this.f230a.b(this.f228a, str2, str);
            }
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public String m253a() {
        return this.f23531e;
    }

    public void a(String str) {
        if (a() && !TextUtils.isEmpty(str)) {
            a(cw.a(this.f228a, str));
        }
    }

    public void a(ir irVar) {
        if (a() && com.xiaomi.push.service.ca.a(irVar.e())) {
            a(co.a(this.f228a, c(), irVar));
        }
    }
}
