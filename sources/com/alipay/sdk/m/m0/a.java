package com.alipay.sdk.m.m0;

import android.content.Context;
import android.content.SharedPreferences;
import com.alipay.sdk.m.l0.f;

/* loaded from: classes2.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    public String f9553a;

    /* renamed from: b, reason: collision with root package name */
    public SharedPreferences f9554b;

    /* renamed from: c, reason: collision with root package name */
    public SharedPreferences.Editor f9555c = null;

    /* renamed from: d, reason: collision with root package name */
    public Context f9556d;

    /* renamed from: e, reason: collision with root package name */
    public boolean f9557e;

    public a(Context context, String str, String str2, boolean z2, boolean z3) {
        this.f9554b = null;
        this.f9557e = z3;
        this.f9553a = str2;
        this.f9556d = context;
        if (context != null) {
            this.f9554b = context.getSharedPreferences(str2, 0);
        }
    }

    private void b() {
        SharedPreferences sharedPreferences;
        if (this.f9555c != null || (sharedPreferences = this.f9554b) == null) {
            return;
        }
        this.f9555c = sharedPreferences.edit();
    }

    public void a(String str, String str2) {
        if (f.m56a(str) || str.equals("t")) {
            return;
        }
        b();
        SharedPreferences.Editor editor = this.f9555c;
        if (editor != null) {
            editor.putString(str, str2);
        }
    }

    public void b(String str) {
        if (f.m56a(str) || str.equals("t")) {
            return;
        }
        b();
        SharedPreferences.Editor editor = this.f9555c;
        if (editor != null) {
            editor.remove(str);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0020  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean a() {
        /*
            r5 = this;
            long r0 = java.lang.System.currentTimeMillis()
            android.content.SharedPreferences$Editor r2 = r5.f9555c
            r3 = 0
            if (r2 == 0) goto L20
            boolean r4 = r5.f9557e
            if (r4 != 0) goto L16
            android.content.SharedPreferences r4 = r5.f9554b
            if (r4 == 0) goto L16
            java.lang.String r4 = "t"
            r2.putLong(r4, r0)
        L16:
            android.content.SharedPreferences$Editor r0 = r5.f9555c
            boolean r0 = r0.commit()
            if (r0 != 0) goto L20
            r0 = r3
            goto L21
        L20:
            r0 = 1
        L21:
            android.content.SharedPreferences r1 = r5.f9554b
            if (r1 == 0) goto L31
            android.content.Context r1 = r5.f9556d
            if (r1 == 0) goto L31
            java.lang.String r2 = r5.f9553a
            android.content.SharedPreferences r1 = r1.getSharedPreferences(r2, r3)
            r5.f9554b = r1
        L31:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.m.m0.a.a():boolean");
    }

    public String a(String str) {
        SharedPreferences sharedPreferences = this.f9554b;
        if (sharedPreferences != null) {
            String string = sharedPreferences.getString(str, "");
            if (!f.m56a(string)) {
                return string;
            }
        }
        return "";
    }
}
