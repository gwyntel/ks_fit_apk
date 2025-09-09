package com.vivo.push.e;

import java.util.ArrayList;

/* loaded from: classes4.dex */
public final class a {

    /* renamed from: a, reason: collision with root package name */
    private static String[] f23157a = {"com.vivo.pushservice", "com.vivo.pushdemo.test", "com.vivo.sdk.test", "com.vivo.hybrid"};

    /* renamed from: b, reason: collision with root package name */
    private ArrayList<String> f23158b;

    /* renamed from: com.vivo.push.e.a$a, reason: collision with other inner class name */
    private static class C0190a {

        /* renamed from: a, reason: collision with root package name */
        private static a f23164a = new a(0);
    }

    /* synthetic */ a(byte b2) {
        this();
    }

    public static a a() {
        return C0190a.f23164a;
    }

    public final ArrayList<String> b() {
        return new ArrayList<>(this.f23158b);
    }

    public final boolean c() {
        ArrayList<String> arrayList = this.f23158b;
        return (arrayList == null || arrayList.size() == 0) ? false : true;
    }

    private a() {
        this.f23158b = null;
        this.f23158b = new ArrayList<>();
    }
}
