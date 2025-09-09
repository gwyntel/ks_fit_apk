package com.xiaomi.push;

import android.content.SharedPreferences;

/* loaded from: classes4.dex */
class o implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ n f24406a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ String f934a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ String f24407b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ String f24408c;

    o(n nVar, String str, String str2, String str3) {
        this.f24406a = nVar;
        this.f934a = str;
        this.f24407b = str2;
        this.f24408c = str3;
    }

    @Override // java.lang.Runnable
    public void run() {
        SharedPreferences.Editor editorEdit = this.f24406a.f931a.getSharedPreferences(this.f934a, 4).edit();
        editorEdit.putString(this.f24407b, this.f24408c);
        editorEdit.commit();
    }
}
