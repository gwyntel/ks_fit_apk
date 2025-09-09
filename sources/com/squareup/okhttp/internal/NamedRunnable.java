package com.squareup.okhttp.internal;

/* loaded from: classes4.dex */
public abstract class NamedRunnable implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    protected final String f19919a;

    public NamedRunnable(String str, Object... objArr) {
        this.f19919a = String.format(str, objArr);
    }

    protected abstract void execute();

    @Override // java.lang.Runnable
    public final void run() {
        String name = Thread.currentThread().getName();
        Thread.currentThread().setName(this.f19919a);
        try {
            execute();
        } finally {
            Thread.currentThread().setName(name);
        }
    }
}
