package com.xiaomi.push;

import android.content.Context;
import java.util.concurrent.Callable;

/* loaded from: classes4.dex */
class bw implements Callable<String> {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Context f23519a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ bv f219a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ String f220a;

    bw(bv bvVar, Context context, String str) {
        this.f219a = bvVar;
        this.f23519a = context;
        this.f220a = str;
    }

    @Override // java.util.concurrent.Callable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public String call() {
        return String.valueOf(jx.a(this.f23519a, this.f220a));
    }
}
