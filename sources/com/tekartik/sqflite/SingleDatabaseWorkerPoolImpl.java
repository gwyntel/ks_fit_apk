package com.tekartik.sqflite;

import android.os.Handler;
import android.os.HandlerThread;
import com.tekartik.sqflite.DatabaseWorkerPool;

/* loaded from: classes4.dex */
class SingleDatabaseWorkerPoolImpl implements DatabaseWorkerPool {

    /* renamed from: a, reason: collision with root package name */
    final String f20471a;

    /* renamed from: b, reason: collision with root package name */
    final int f20472b;
    private Handler handler;
    private HandlerThread handlerThread;

    SingleDatabaseWorkerPoolImpl(String str, int i2) {
        this.f20471a = str;
        this.f20472b = i2;
    }

    @Override // com.tekartik.sqflite.DatabaseWorkerPool
    public /* synthetic */ void post(Database database, Runnable runnable) {
        DatabaseWorkerPool.CC.a(this, database, runnable);
    }

    @Override // com.tekartik.sqflite.DatabaseWorkerPool
    public void quit() {
        HandlerThread handlerThread = this.handlerThread;
        if (handlerThread != null) {
            handlerThread.quit();
            this.handlerThread = null;
            this.handler = null;
        }
    }

    @Override // com.tekartik.sqflite.DatabaseWorkerPool
    public void start() {
        HandlerThread handlerThread = new HandlerThread(this.f20471a, this.f20472b);
        this.handlerThread = handlerThread;
        handlerThread.start();
        this.handler = new Handler(this.handlerThread.getLooper());
    }

    @Override // com.tekartik.sqflite.DatabaseWorkerPool
    public void post(DatabaseTask databaseTask) {
        this.handler.post(databaseTask.f20464a);
    }
}
