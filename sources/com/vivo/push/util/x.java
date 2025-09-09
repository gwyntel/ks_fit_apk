package com.vivo.push.util;

import android.content.Context;
import android.content.SharedPreferences;

/* loaded from: classes4.dex */
public final class x implements d {

    /* renamed from: a, reason: collision with root package name */
    private static String f23264a = "SpCache";

    /* renamed from: b, reason: collision with root package name */
    private static String f23265b = "com.vivo.push.cache";

    /* renamed from: c, reason: collision with root package name */
    private SharedPreferences f23266c;

    @Override // com.vivo.push.util.d
    public final boolean a(Context context) {
        if (this.f23266c != null) {
            return true;
        }
        this.f23266c = context.getSharedPreferences(f23265b, 0);
        return true;
    }

    @Override // com.vivo.push.util.d
    public final void b(String str, String str2) {
        SharedPreferences.Editor editorEdit = this.f23266c.edit();
        if (editorEdit == null) {
            p.b(f23264a, "putString error by ".concat(String.valueOf(str)));
            return;
        }
        editorEdit.putString(str, str2);
        b.a(editorEdit);
        p.d(f23264a, "putString by ".concat(String.valueOf(str)));
    }

    @Override // com.vivo.push.util.d
    public final String a(String str, String str2) {
        String string = this.f23266c.getString(str, str2);
        p.d(f23264a, "getString " + str + " is " + string);
        return string;
    }

    public final void a() {
        SharedPreferences.Editor editorEdit = this.f23266c.edit();
        if (editorEdit != null) {
            editorEdit.clear();
            b.a(editorEdit);
        }
        p.d(f23264a, "system cache is cleared");
    }
}
