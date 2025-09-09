package com.tekartik.sqflite.operation;

/* loaded from: classes4.dex */
public class QueuedOperation {

    /* renamed from: a, reason: collision with root package name */
    final Operation f20526a;

    /* renamed from: b, reason: collision with root package name */
    final Runnable f20527b;

    public QueuedOperation(Operation operation, Runnable runnable) {
        this.f20526a = operation;
        this.f20527b = runnable;
    }

    public void run() {
        this.f20527b.run();
    }
}
