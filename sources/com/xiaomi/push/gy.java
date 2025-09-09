package com.xiaomi.push;

/* loaded from: classes4.dex */
class gy extends Thread {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ gx f23855a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    gy(gx gxVar, String str) {
        super(str);
        this.f23855a = gxVar;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        try {
            this.f23855a.f23854a.m456a();
        } catch (Exception e2) {
            this.f23855a.c(9, e2);
        }
    }
}
