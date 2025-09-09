package a.a.a.a.b.m;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes.dex */
public class i implements ThreadFactory {

    /* renamed from: a, reason: collision with root package name */
    public final int f1588a;

    /* renamed from: b, reason: collision with root package name */
    public final AtomicInteger f1589b = new AtomicInteger();

    /* renamed from: c, reason: collision with root package name */
    public final String f1590c;

    public i(String str, int i2) {
        this.f1590c = str;
        this.f1588a = i2;
    }

    @Override // java.util.concurrent.ThreadFactory
    public Thread newThread(Runnable runnable) {
        return new h(this, runnable, this.f1590c + '-' + this.f1589b.getAndIncrement());
    }
}
