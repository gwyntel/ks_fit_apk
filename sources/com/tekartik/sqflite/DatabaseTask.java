package com.tekartik.sqflite;

import androidx.annotation.Nullable;

/* loaded from: classes4.dex */
final class DatabaseTask {

    /* renamed from: a, reason: collision with root package name */
    final Runnable f20464a;

    @Nullable
    private final DatabaseDelegate database;

    DatabaseTask(DatabaseDelegate databaseDelegate, Runnable runnable) {
        this.database = databaseDelegate;
        this.f20464a = runnable;
    }

    public Integer getDatabaseId() {
        DatabaseDelegate databaseDelegate = this.database;
        if (databaseDelegate != null) {
            return Integer.valueOf(databaseDelegate.getDatabaseId());
        }
        return null;
    }

    public boolean isInTransaction() {
        DatabaseDelegate databaseDelegate = this.database;
        return databaseDelegate != null && databaseDelegate.isInTransaction();
    }
}
