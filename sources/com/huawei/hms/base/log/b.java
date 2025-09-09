package com.huawei.hms.base.log;

import android.content.Context;
import android.util.Log;

/* loaded from: classes.dex */
public class b {

    /* renamed from: b, reason: collision with root package name */
    private String f15874b;

    /* renamed from: a, reason: collision with root package name */
    private int f15873a = 4;

    /* renamed from: c, reason: collision with root package name */
    private d f15875c = new c();

    public void a(Context context, int i2, String str) {
        this.f15873a = i2;
        this.f15874b = str;
        this.f15875c.a(context, "HMSCore");
    }

    public void b(int i2, String str, String str2, Throwable th) {
        try {
            if (a(i2)) {
                e eVarA = a(i2, str, str2, th);
                String str3 = eVarA.c() + eVarA.a();
                this.f15875c.a(str3, i2, str, str2 + '\n' + Log.getStackTraceString(th));
            }
        } catch (OutOfMemoryError unused) {
            b();
        }
    }

    public d a() {
        return this.f15875c;
    }

    public void a(d dVar) {
        this.f15875c = dVar;
    }

    private void b() {
        try {
            Log.e("HMSSDK_LogAdaptor", "log happened OOM error.");
        } catch (Throwable unused) {
        }
    }

    public boolean a(int i2) {
        return i2 >= this.f15873a;
    }

    public void a(int i2, String str, String str2) {
        try {
            if (a(i2)) {
                e eVarA = a(i2, str, str2, null);
                this.f15875c.a(eVarA.c() + eVarA.a(), i2, str, str2);
            }
        } catch (OutOfMemoryError unused) {
            b();
        }
    }

    public void a(String str, String str2) {
        try {
            e eVarA = a(4, str, str2, null);
            this.f15875c.a(eVarA.c() + '\n' + eVarA.a(), 4, str, str2);
        } catch (OutOfMemoryError unused) {
            b();
        }
    }

    private e a(int i2, String str, String str2, Throwable th) {
        e eVar = new e(8, this.f15874b, i2, str);
        eVar.a((e) str2);
        eVar.a(th);
        return eVar;
    }
}
