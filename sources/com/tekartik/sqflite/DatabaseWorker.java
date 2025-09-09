package com.tekartik.sqflite;

import android.os.Handler;
import android.os.HandlerThread;

/* loaded from: classes4.dex */
class DatabaseWorker {

    /* renamed from: a, reason: collision with root package name */
    protected Runnable f20465a;
    private Handler handler;
    private HandlerThread handlerThread;
    private DatabaseTask lastTask;
    private final String name;
    private final int priority;

    DatabaseWorker(String str, int i2) {
        this.name = str;
        this.priority = i2;
    }

    boolean b() {
        DatabaseTask databaseTask = this.lastTask;
        return databaseTask != null && databaseTask.isInTransaction();
    }

    Integer c() {
        DatabaseTask databaseTask = this.lastTask;
        if (databaseTask != null) {
            return databaseTask.getDatabaseId();
        }
        return null;
    }

    void d(final DatabaseTask databaseTask) {
        this.handler.post(new Runnable() { // from class: com.tekartik.sqflite.j
            @Override // java.lang.Runnable
            public final void run() {
                this.f20499a.lambda$postTask$0(databaseTask);
            }
        });
    }

    synchronized void e() {
        HandlerThread handlerThread = this.handlerThread;
        if (handlerThread != null) {
            handlerThread.quit();
            this.handlerThread = null;
            this.handler = null;
        }
    }

    synchronized void f(Runnable runnable) {
        HandlerThread handlerThread = new HandlerThread(this.name, this.priority);
        this.handlerThread = handlerThread;
        handlerThread.start();
        this.handler = new Handler(this.handlerThread.getLooper());
        this.f20465a = runnable;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: g, reason: merged with bridge method [inline-methods] */
    public void lambda$postTask$0(DatabaseTask databaseTask) {
        databaseTask.f20464a.run();
        this.lastTask = databaseTask;
        this.f20465a.run();
    }
}
