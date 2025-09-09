package com.tekartik.sqflite;

/* loaded from: classes4.dex */
public interface DatabaseWorkerPool {

    /* renamed from: com.tekartik.sqflite.DatabaseWorkerPool$-CC, reason: invalid class name */
    public abstract /* synthetic */ class CC {
        public static void a(final DatabaseWorkerPool databaseWorkerPool, final Database database, Runnable runnable) {
            databaseWorkerPool.post(new DatabaseTask(database == null ? null : new DatabaseDelegate() { // from class: com.tekartik.sqflite.DatabaseWorkerPool.1
                @Override // com.tekartik.sqflite.DatabaseDelegate
                public int getDatabaseId() {
                    return database.f20457c;
                }

                @Override // com.tekartik.sqflite.DatabaseDelegate
                public boolean isInTransaction() {
                    return database.q();
                }
            }, runnable));
        }

        public static DatabaseWorkerPool b(String str, int i2, int i3) {
            return i2 == 1 ? new SingleDatabaseWorkerPoolImpl(str, i3) : new DatabaseWorkerPoolImpl(str, i2, i3);
        }
    }

    void post(Database database, Runnable runnable);

    void post(DatabaseTask databaseTask);

    void quit();

    void start();
}
