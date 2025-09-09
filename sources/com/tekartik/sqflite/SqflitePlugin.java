package com.tekartik.sqflite;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.tekartik.sqflite.DatabaseWorkerPool;
import com.tekartik.sqflite.dev.Debug;
import com.tekartik.sqflite.operation.MethodCallOperation;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.StandardMethodCodec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.CharUtils;

/* loaded from: classes4.dex */
public class SqflitePlugin implements FlutterPlugin, MethodChannel.MethodCallHandler {

    /* renamed from: d, reason: collision with root package name */
    static String f20479d;
    private static DatabaseWorkerPool databaseWorkerPool;
    private Context context;
    private MethodChannel methodChannel;

    /* renamed from: a, reason: collision with root package name */
    static final Map f20476a = new HashMap();

    /* renamed from: b, reason: collision with root package name */
    static final Map f20477b = new HashMap();
    private static final Object databaseMapLocker = new Object();
    private static final Object openCloseLocker = new Object();

    /* renamed from: c, reason: collision with root package name */
    static int f20478c = 0;
    private static int THREAD_PRIORITY = 0;
    private static int THREAD_COUNT = 1;
    private static int databaseId = 0;

    public SqflitePlugin() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void closeDatabase(Database database) {
        try {
            if (LogLevel.b(database.f20458d)) {
                Log.d(Constant.TAG, database.n() + "closing database ");
            }
            database.close();
        } catch (Exception e2) {
            Log.e(Constant.TAG, "error " + e2 + " while closing database " + databaseId);
        }
        synchronized (databaseMapLocker) {
            try {
                if (f20477b.isEmpty() && databaseWorkerPool != null) {
                    if (LogLevel.b(database.f20458d)) {
                        Log.d(Constant.TAG, database.n() + "stopping thread");
                    }
                    databaseWorkerPool.quit();
                    databaseWorkerPool = null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private static Map<String, Object> fixMap(Map<Object, Object> map) {
        HashMap map2 = new HashMap();
        for (Map.Entry<Object, Object> entry : map.entrySet()) {
            Object value = entry.getValue();
            map2.put(toString(entry.getKey()), value instanceof Map ? fixMap((Map) value) : toString(value));
        }
        return map2;
    }

    private Context getContext() {
        return this.context;
    }

    private Database getDatabase(int i2) {
        return (Database) f20477b.get(Integer.valueOf(i2));
    }

    private Database getDatabaseOrError(MethodCall methodCall, MethodChannel.Result result) {
        int iIntValue = ((Integer) methodCall.argument("id")).intValue();
        Database database = getDatabase(iIntValue);
        if (database != null) {
            return database;
        }
        result.error("sqlite_error", "database_closed " + iIntValue, null);
        return null;
    }

    static boolean l(String str) {
        return str == null || str.equals(":memory:");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onExecuteCall$4(MethodCall methodCall, MethodChannel.Result result, Database database) {
        database.execute(new MethodCallOperation(methodCall, result));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onInsertCall$3(MethodCall methodCall, MethodChannel.Result result, Database database) {
        database.insert(new MethodCallOperation(methodCall, result));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0048 A[Catch: all -> 0x003e, Exception -> 0x004c, TryCatch #1 {Exception -> 0x004c, blocks: (B:16:0x0040, B:18:0x0048, B:21:0x004e), top: B:45:0x0040, outer: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x004e A[Catch: all -> 0x003e, Exception -> 0x004c, TRY_LEAVE, TryCatch #1 {Exception -> 0x004c, blocks: (B:16:0x0040, B:18:0x0048, B:21:0x004e), top: B:45:0x0040, outer: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0054  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static /* synthetic */ void lambda$onOpenDatabaseCall$7(boolean r2, java.lang.String r3, io.flutter.plugin.common.MethodChannel.Result r4, java.lang.Boolean r5, com.tekartik.sqflite.Database r6, io.flutter.plugin.common.MethodCall r7, boolean r8, int r9) {
        /*
            java.lang.Object r0 = com.tekartik.sqflite.SqflitePlugin.openCloseLocker
            monitor-enter(r0)
            if (r2 != 0) goto L40
            java.io.File r2 = new java.io.File     // Catch: java.lang.Throwable -> L3e
            r2.<init>(r3)     // Catch: java.lang.Throwable -> L3e
            java.io.File r1 = new java.io.File     // Catch: java.lang.Throwable -> L3e
            java.lang.String r2 = r2.getParent()     // Catch: java.lang.Throwable -> L3e
            r1.<init>(r2)     // Catch: java.lang.Throwable -> L3e
            boolean r2 = r1.exists()     // Catch: java.lang.Throwable -> L3e
            if (r2 != 0) goto L40
            boolean r2 = r1.mkdirs()     // Catch: java.lang.Throwable -> L3e
            if (r2 != 0) goto L40
            boolean r2 = r1.exists()     // Catch: java.lang.Throwable -> L3e
            if (r2 != 0) goto L40
            java.lang.String r2 = "sqlite_error"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L3e
            r5.<init>()     // Catch: java.lang.Throwable -> L3e
            java.lang.String r6 = "open_failed "
            r5.append(r6)     // Catch: java.lang.Throwable -> L3e
            r5.append(r3)     // Catch: java.lang.Throwable -> L3e
            java.lang.String r3 = r5.toString()     // Catch: java.lang.Throwable -> L3e
            r5 = 0
            r4.error(r2, r3, r5)     // Catch: java.lang.Throwable -> L3e
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L3e
            return
        L3e:
            r2 = move-exception
            goto Laf
        L40:
            java.lang.Boolean r2 = java.lang.Boolean.TRUE     // Catch: java.lang.Throwable -> L3e java.lang.Exception -> L4c
            boolean r2 = r2.equals(r5)     // Catch: java.lang.Throwable -> L3e java.lang.Exception -> L4c
            if (r2 == 0) goto L4e
            r6.openReadOnly()     // Catch: java.lang.Throwable -> L3e java.lang.Exception -> L4c
            goto L51
        L4c:
            r2 = move-exception
            goto La5
        L4e:
            r6.open()     // Catch: java.lang.Throwable -> L3e java.lang.Exception -> L4c
        L51:
            java.lang.Object r2 = com.tekartik.sqflite.SqflitePlugin.databaseMapLocker     // Catch: java.lang.Throwable -> L3e
            monitor-enter(r2)     // Catch: java.lang.Throwable -> L3e
            if (r8 == 0) goto L62
            java.util.Map r5 = com.tekartik.sqflite.SqflitePlugin.f20476a     // Catch: java.lang.Throwable -> L60
            java.lang.Integer r7 = java.lang.Integer.valueOf(r9)     // Catch: java.lang.Throwable -> L60
            r5.put(r3, r7)     // Catch: java.lang.Throwable -> L60
            goto L62
        L60:
            r3 = move-exception
            goto La3
        L62:
            java.util.Map r5 = com.tekartik.sqflite.SqflitePlugin.f20477b     // Catch: java.lang.Throwable -> L60
            java.lang.Integer r7 = java.lang.Integer.valueOf(r9)     // Catch: java.lang.Throwable -> L60
            r5.put(r7, r6)     // Catch: java.lang.Throwable -> L60
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L60
            int r2 = r6.f20458d     // Catch: java.lang.Throwable -> L3e
            boolean r2 = com.tekartik.sqflite.LogLevel.b(r2)     // Catch: java.lang.Throwable -> L3e
            if (r2 == 0) goto L99
            java.lang.String r2 = "Sqflite"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L3e
            r5.<init>()     // Catch: java.lang.Throwable -> L3e
            java.lang.String r6 = r6.n()     // Catch: java.lang.Throwable -> L3e
            r5.append(r6)     // Catch: java.lang.Throwable -> L3e
            java.lang.String r6 = "opened "
            r5.append(r6)     // Catch: java.lang.Throwable -> L3e
            r5.append(r9)     // Catch: java.lang.Throwable -> L3e
            java.lang.String r6 = " "
            r5.append(r6)     // Catch: java.lang.Throwable -> L3e
            r5.append(r3)     // Catch: java.lang.Throwable -> L3e
            java.lang.String r3 = r5.toString()     // Catch: java.lang.Throwable -> L3e
            android.util.Log.d(r2, r3)     // Catch: java.lang.Throwable -> L3e
        L99:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L3e
            r2 = 0
            java.util.Map r2 = m(r9, r2, r2)
            r4.success(r2)
            return
        La3:
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L60
            throw r3     // Catch: java.lang.Throwable -> L3e
        La5:
            com.tekartik.sqflite.operation.MethodCallOperation r3 = new com.tekartik.sqflite.operation.MethodCallOperation     // Catch: java.lang.Throwable -> L3e
            r3.<init>(r7, r4)     // Catch: java.lang.Throwable -> L3e
            r6.p(r2, r3)     // Catch: java.lang.Throwable -> L3e
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L3e
            return
        Laf:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L3e
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tekartik.sqflite.SqflitePlugin.lambda$onOpenDatabaseCall$7(boolean, java.lang.String, io.flutter.plugin.common.MethodChannel$Result, java.lang.Boolean, com.tekartik.sqflite.Database, io.flutter.plugin.common.MethodCall, boolean, int):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onQueryCall$0(MethodCall methodCall, MethodChannel.Result result, Database database) {
        database.query(new MethodCallOperation(methodCall, result));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onQueryCursorNextCall$1(MethodCall methodCall, MethodChannel.Result result, Database database) {
        database.queryCursorNext(new MethodCallOperation(methodCall, result));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onSetLocaleCall$5(MethodCall methodCall, Database database, MethodChannel.Result result) {
        try {
            database.f20462h.setLocale(Utils.b((String) methodCall.argument("locale")));
            result.success(null);
        } catch (Exception e2) {
            result.error("sqlite_error", "Error calling setLocale: " + e2.getMessage(), null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onUpdateCall$6(MethodCall methodCall, MethodChannel.Result result, Database database) {
        database.update(new MethodCallOperation(methodCall, result));
    }

    static Map m(int i2, boolean z2, boolean z3) {
        HashMap map = new HashMap();
        map.put("id", Integer.valueOf(i2));
        if (z2) {
            map.put(Constant.PARAM_RECOVERED, Boolean.TRUE);
        }
        if (z3) {
            map.put(Constant.PARAM_RECOVERED_IN_TRANSACTION, Boolean.TRUE);
        }
        return map;
    }

    private void onBatchCall(final MethodCall methodCall, final MethodChannel.Result result) {
        final Database databaseOrError = getDatabaseOrError(methodCall, result);
        if (databaseOrError == null) {
            return;
        }
        databaseWorkerPool.post(databaseOrError, new Runnable() { // from class: com.tekartik.sqflite.t
            @Override // java.lang.Runnable
            public final void run() {
                databaseOrError.h(methodCall, result);
            }
        });
    }

    private void onCloseDatabaseCall(MethodCall methodCall, final MethodChannel.Result result) {
        Integer num = (Integer) methodCall.argument("id");
        int iIntValue = num.intValue();
        final Database databaseOrError = getDatabaseOrError(methodCall, result);
        if (databaseOrError == null) {
            return;
        }
        if (LogLevel.b(databaseOrError.f20458d)) {
            Log.d(Constant.TAG, databaseOrError.n() + "closing " + iIntValue + " " + databaseOrError.f20456b);
        }
        String str = databaseOrError.f20456b;
        synchronized (databaseMapLocker) {
            try {
                f20477b.remove(num);
                if (databaseOrError.f20455a) {
                    f20476a.remove(str);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        databaseWorkerPool.post(databaseOrError, new Runnable() { // from class: com.tekartik.sqflite.SqflitePlugin.1
            @Override // java.lang.Runnable
            public void run() {
                synchronized (SqflitePlugin.openCloseLocker) {
                    SqflitePlugin.this.closeDatabase(databaseOrError);
                }
                result.success(null);
            }
        });
    }

    private void onDatabaseExistsCall(MethodCall methodCall, MethodChannel.Result result) {
        result.success(Boolean.valueOf(Database.existsDatabase((String) methodCall.argument("path"))));
    }

    private void onDebugCall(MethodCall methodCall, MethodChannel.Result result) {
        String str = (String) methodCall.argument(com.taobao.agoo.a.a.b.JSON_CMD);
        HashMap map = new HashMap();
        if (TmpConstant.PROPERTY_IDENTIFIER_GET.equals(str)) {
            int i2 = f20478c;
            if (i2 > 0) {
                map.put("logLevel", Integer.valueOf(i2));
            }
            Map map2 = f20477b;
            if (!map2.isEmpty()) {
                HashMap map3 = new HashMap();
                for (Map.Entry entry : map2.entrySet()) {
                    Database database = (Database) entry.getValue();
                    HashMap map4 = new HashMap();
                    map4.put("path", database.f20456b);
                    map4.put("singleInstance", Boolean.valueOf(database.f20455a));
                    int i3 = database.f20458d;
                    if (i3 > 0) {
                        map4.put("logLevel", Integer.valueOf(i3));
                    }
                    map3.put(((Integer) entry.getKey()).toString(), map4);
                }
                map.put("databases", map3);
            }
        }
        result.success(map);
    }

    private void onDebugModeCall(MethodCall methodCall, MethodChannel.Result result) {
        Debug.LOGV = Boolean.TRUE.equals(methodCall.arguments());
        Debug.EXTRA_LOGV = Debug._EXTRA_LOGV && Debug.LOGV;
        if (!Debug.LOGV) {
            f20478c = 0;
        } else if (Debug.EXTRA_LOGV) {
            f20478c = 2;
        } else if (Debug.LOGV) {
            f20478c = 1;
        }
        result.success(null);
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0099  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void onDeleteDatabaseCall(io.flutter.plugin.common.MethodCall r9, final io.flutter.plugin.common.MethodChannel.Result r10) {
        /*
            r8 = this;
            java.lang.String r0 = "path"
            java.lang.Object r9 = r9.argument(r0)
            java.lang.String r9 = (java.lang.String) r9
            java.lang.Object r0 = com.tekartik.sqflite.SqflitePlugin.databaseMapLocker
            monitor-enter(r0)
            int r1 = com.tekartik.sqflite.SqflitePlugin.f20478c     // Catch: java.lang.Throwable -> L38
            boolean r1 = com.tekartik.sqflite.LogLevel.c(r1)     // Catch: java.lang.Throwable -> L38
            if (r1 == 0) goto L3b
            java.lang.String r1 = "Sqflite"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L38
            r2.<init>()     // Catch: java.lang.Throwable -> L38
            java.lang.String r3 = "Look for "
            r2.append(r3)     // Catch: java.lang.Throwable -> L38
            r2.append(r9)     // Catch: java.lang.Throwable -> L38
            java.lang.String r3 = " in "
            r2.append(r3)     // Catch: java.lang.Throwable -> L38
            java.util.Map r3 = com.tekartik.sqflite.SqflitePlugin.f20476a     // Catch: java.lang.Throwable -> L38
            java.util.Set r3 = r3.keySet()     // Catch: java.lang.Throwable -> L38
            r2.append(r3)     // Catch: java.lang.Throwable -> L38
            java.lang.String r2 = r2.toString()     // Catch: java.lang.Throwable -> L38
            android.util.Log.d(r1, r2)     // Catch: java.lang.Throwable -> L38
            goto L3b
        L38:
            r9 = move-exception
            goto Lac
        L3b:
            java.util.Map r1 = com.tekartik.sqflite.SqflitePlugin.f20476a     // Catch: java.lang.Throwable -> L38
            java.lang.Object r2 = r1.get(r9)     // Catch: java.lang.Throwable -> L38
            java.lang.Integer r2 = (java.lang.Integer) r2     // Catch: java.lang.Throwable -> L38
            if (r2 == 0) goto L99
            java.util.Map r3 = com.tekartik.sqflite.SqflitePlugin.f20477b     // Catch: java.lang.Throwable -> L38
            java.lang.Object r4 = r3.get(r2)     // Catch: java.lang.Throwable -> L38
            com.tekartik.sqflite.Database r4 = (com.tekartik.sqflite.Database) r4     // Catch: java.lang.Throwable -> L38
            if (r4 == 0) goto L99
            android.database.sqlite.SQLiteDatabase r5 = r4.f20462h     // Catch: java.lang.Throwable -> L38
            boolean r5 = r5.isOpen()     // Catch: java.lang.Throwable -> L38
            if (r5 == 0) goto L99
            int r5 = com.tekartik.sqflite.SqflitePlugin.f20478c     // Catch: java.lang.Throwable -> L38
            boolean r5 = com.tekartik.sqflite.LogLevel.c(r5)     // Catch: java.lang.Throwable -> L38
            if (r5 == 0) goto L92
            java.lang.String r5 = "Sqflite"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L38
            r6.<init>()     // Catch: java.lang.Throwable -> L38
            java.lang.String r7 = r4.n()     // Catch: java.lang.Throwable -> L38
            r6.append(r7)     // Catch: java.lang.Throwable -> L38
            java.lang.String r7 = "found single instance "
            r6.append(r7)     // Catch: java.lang.Throwable -> L38
            boolean r7 = r4.q()     // Catch: java.lang.Throwable -> L38
            if (r7 == 0) goto L7b
            java.lang.String r7 = "(in transaction) "
            goto L7d
        L7b:
            java.lang.String r7 = ""
        L7d:
            r6.append(r7)     // Catch: java.lang.Throwable -> L38
            r6.append(r2)     // Catch: java.lang.Throwable -> L38
            java.lang.String r7 = " "
            r6.append(r7)     // Catch: java.lang.Throwable -> L38
            r6.append(r9)     // Catch: java.lang.Throwable -> L38
            java.lang.String r6 = r6.toString()     // Catch: java.lang.Throwable -> L38
            android.util.Log.d(r5, r6)     // Catch: java.lang.Throwable -> L38
        L92:
            r3.remove(r2)     // Catch: java.lang.Throwable -> L38
            r1.remove(r9)     // Catch: java.lang.Throwable -> L38
            goto L9a
        L99:
            r4 = 0
        L9a:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L38
            com.tekartik.sqflite.SqflitePlugin$2 r0 = new com.tekartik.sqflite.SqflitePlugin$2
            r0.<init>()
            com.tekartik.sqflite.DatabaseWorkerPool r9 = com.tekartik.sqflite.SqflitePlugin.databaseWorkerPool
            if (r9 == 0) goto La8
            r9.post(r4, r0)
            goto Lab
        La8:
            r0.run()
        Lab:
            return
        Lac:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L38
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tekartik.sqflite.SqflitePlugin.onDeleteDatabaseCall(io.flutter.plugin.common.MethodCall, io.flutter.plugin.common.MethodChannel$Result):void");
    }

    private void onExecuteCall(final MethodCall methodCall, final MethodChannel.Result result) {
        final Database databaseOrError = getDatabaseOrError(methodCall, result);
        if (databaseOrError == null) {
            return;
        }
        databaseWorkerPool.post(databaseOrError, new Runnable() { // from class: com.tekartik.sqflite.q
            @Override // java.lang.Runnable
            public final void run() {
                SqflitePlugin.lambda$onExecuteCall$4(methodCall, result, databaseOrError);
            }
        });
    }

    private void onInsertCall(final MethodCall methodCall, final MethodChannel.Result result) {
        final Database databaseOrError = getDatabaseOrError(methodCall, result);
        if (databaseOrError == null) {
            return;
        }
        databaseWorkerPool.post(databaseOrError, new Runnable() { // from class: com.tekartik.sqflite.m
            @Override // java.lang.Runnable
            public final void run() {
                SqflitePlugin.lambda$onInsertCall$3(methodCall, result, databaseOrError);
            }
        });
    }

    private void onOpenDatabaseCall(final MethodCall methodCall, final MethodChannel.Result result) {
        final int i2;
        Database database;
        final String str = (String) methodCall.argument("path");
        final Boolean bool = (Boolean) methodCall.argument("readOnly");
        final boolean zL = l(str);
        boolean z2 = (Boolean.FALSE.equals(methodCall.argument("singleInstance")) || zL) ? false : true;
        if (z2) {
            synchronized (databaseMapLocker) {
                try {
                    if (LogLevel.c(f20478c)) {
                        Log.d(Constant.TAG, "Look for " + str + " in " + f20476a.keySet());
                    }
                    Integer num = (Integer) f20476a.get(str);
                    if (num != null && (database = (Database) f20477b.get(num)) != null) {
                        if (database.f20462h.isOpen()) {
                            if (LogLevel.c(f20478c)) {
                                StringBuilder sb = new StringBuilder();
                                sb.append(database.n());
                                sb.append("re-opened single instance ");
                                sb.append(database.q() ? "(in transaction) " : "");
                                sb.append(num);
                                sb.append(" ");
                                sb.append(str);
                                Log.d(Constant.TAG, sb.toString());
                            }
                            result.success(m(num.intValue(), true, database.q()));
                            return;
                        }
                        if (LogLevel.c(f20478c)) {
                            Log.d(Constant.TAG, database.n() + "single instance database of " + str + " not opened");
                        }
                    }
                } finally {
                }
            }
        }
        Object obj = databaseMapLocker;
        synchronized (obj) {
            i2 = databaseId + 1;
            databaseId = i2;
        }
        final Database database2 = new Database(this.context, str, i2, z2, f20478c);
        synchronized (obj) {
            try {
                if (databaseWorkerPool == null) {
                    DatabaseWorkerPool databaseWorkerPoolB = DatabaseWorkerPool.CC.b(Constant.TAG, THREAD_COUNT, THREAD_PRIORITY);
                    databaseWorkerPool = databaseWorkerPoolB;
                    databaseWorkerPoolB.start();
                    if (LogLevel.b(database2.f20458d)) {
                        Log.d(Constant.TAG, database2.n() + "starting worker pool with priority " + THREAD_PRIORITY);
                    }
                }
                database2.databaseWorkerPool = databaseWorkerPool;
                if (LogLevel.b(database2.f20458d)) {
                    Log.d(Constant.TAG, database2.n() + "opened " + i2 + " " + str);
                }
                final boolean z3 = z2;
                databaseWorkerPool.post(database2, new Runnable() { // from class: com.tekartik.sqflite.p
                    @Override // java.lang.Runnable
                    public final void run() {
                        SqflitePlugin.lambda$onOpenDatabaseCall$7(zL, str, result, bool, database2, methodCall, z3, i2);
                    }
                });
            } finally {
            }
        }
    }

    private void onQueryCall(final MethodCall methodCall, final MethodChannel.Result result) {
        final Database databaseOrError = getDatabaseOrError(methodCall, result);
        if (databaseOrError == null) {
            return;
        }
        databaseWorkerPool.post(databaseOrError, new Runnable() { // from class: com.tekartik.sqflite.n
            @Override // java.lang.Runnable
            public final void run() {
                SqflitePlugin.lambda$onQueryCall$0(methodCall, result, databaseOrError);
            }
        });
    }

    private void onQueryCursorNextCall(final MethodCall methodCall, final MethodChannel.Result result) {
        final Database databaseOrError = getDatabaseOrError(methodCall, result);
        if (databaseOrError == null) {
            return;
        }
        databaseWorkerPool.post(databaseOrError, new Runnable() { // from class: com.tekartik.sqflite.l
            @Override // java.lang.Runnable
            public final void run() {
                SqflitePlugin.lambda$onQueryCursorNextCall$1(methodCall, result, databaseOrError);
            }
        });
    }

    private void onSetLocaleCall(final MethodCall methodCall, final MethodChannel.Result result) {
        final Database databaseOrError = getDatabaseOrError(methodCall, result);
        if (databaseOrError == null) {
            return;
        }
        databaseWorkerPool.post(databaseOrError, new Runnable() { // from class: com.tekartik.sqflite.o
            @Override // java.lang.Runnable
            public final void run() {
                SqflitePlugin.lambda$onSetLocaleCall$5(methodCall, databaseOrError, result);
            }
        });
    }

    private void onUpdateCall(final MethodCall methodCall, final MethodChannel.Result result) {
        final Database databaseOrError = getDatabaseOrError(methodCall, result);
        if (databaseOrError == null) {
            return;
        }
        databaseWorkerPool.post(databaseOrError, new Runnable() { // from class: com.tekartik.sqflite.s
            @Override // java.lang.Runnable
            public final void run() {
                SqflitePlugin.lambda$onUpdateCall$6(methodCall, result, databaseOrError);
            }
        });
    }

    private static String toString(Object obj) {
        if (obj == null) {
            return null;
        }
        if (!(obj instanceof byte[])) {
            return obj instanceof Map ? fixMap((Map) obj).toString() : obj.toString();
        }
        ArrayList arrayList = new ArrayList();
        for (byte b2 : (byte[]) obj) {
            arrayList.add(Integer.valueOf(b2));
        }
        return arrayList.toString();
    }

    void n(MethodCall methodCall, MethodChannel.Result result) {
        if (f20479d == null) {
            f20479d = this.context.getDatabasePath("tekartik_sqflite.db").getParent();
        }
        result.success(f20479d);
    }

    void o(MethodCall methodCall, MethodChannel.Result result) {
        Object objArgument = methodCall.argument("androidThreadPriority");
        if (objArgument != null) {
            THREAD_PRIORITY = ((Integer) objArgument).intValue();
        }
        Object objArgument2 = methodCall.argument("androidThreadCount");
        if (objArgument2 != null && !objArgument2.equals(Integer.valueOf(THREAD_COUNT))) {
            THREAD_COUNT = ((Integer) objArgument2).intValue();
            DatabaseWorkerPool databaseWorkerPool2 = databaseWorkerPool;
            if (databaseWorkerPool2 != null) {
                databaseWorkerPool2.quit();
                databaseWorkerPool = null;
            }
        }
        Integer numA = LogLevel.a(methodCall);
        if (numA != null) {
            f20478c = numA.intValue();
        }
        result.success(null);
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onAttachedToEngine(FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        onAttachedToEngine(flutterPluginBinding.getApplicationContext(), flutterPluginBinding.getBinaryMessenger());
    }

    @Override // io.flutter.embedding.engine.plugins.FlutterPlugin
    public void onDetachedFromEngine(FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
        this.context = null;
        this.methodChannel.setMethodCallHandler(null);
        this.methodChannel = null;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // io.flutter.plugin.common.MethodChannel.MethodCallHandler
    public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
        String str = methodCall.method;
        str.hashCode();
        char c2 = 65535;
        switch (str.hashCode()) {
            case -1319569547:
                if (str.equals(Constant.METHOD_EXECUTE)) {
                    c2 = 0;
                    break;
                }
                break;
            case -1253581933:
                if (str.equals(Constant.METHOD_CLOSE_DATABASE)) {
                    c2 = 1;
                    break;
                }
                break;
            case -1249474914:
                if (str.equals(Constant.METHOD_OPTIONS)) {
                    c2 = 2;
                    break;
                }
                break;
            case -1183792455:
                if (str.equals(Constant.METHOD_INSERT)) {
                    c2 = 3;
                    break;
                }
                break;
            case -838846263:
                if (str.equals(Constant.METHOD_UPDATE)) {
                    c2 = 4;
                    break;
                }
                break;
            case -396289107:
                if (str.equals(Constant.METHOD_ANDROID_SET_LOCALE)) {
                    c2 = 5;
                    break;
                }
                break;
            case -263511994:
                if (str.equals(Constant.METHOD_DELETE_DATABASE)) {
                    c2 = 6;
                    break;
                }
                break;
            case -198450538:
                if (str.equals(Constant.METHOD_DEBUG_MODE)) {
                    c2 = 7;
                    break;
                }
                break;
            case -17190427:
                if (str.equals(Constant.METHOD_OPEN_DATABASE)) {
                    c2 = '\b';
                    break;
                }
                break;
            case 93509434:
                if (str.equals("batch")) {
                    c2 = '\t';
                    break;
                }
                break;
            case 95458899:
                if (str.equals("debug")) {
                    c2 = '\n';
                    break;
                }
                break;
            case 107944136:
                if (str.equals("query")) {
                    c2 = 11;
                    break;
                }
                break;
            case 956410295:
                if (str.equals(Constant.METHOD_DATABASE_EXISTS)) {
                    c2 = '\f';
                    break;
                }
                break;
            case 1193546321:
                if (str.equals(Constant.METHOD_QUERY_CURSOR_NEXT)) {
                    c2 = CharUtils.CR;
                    break;
                }
                break;
            case 1385449135:
                if (str.equals(Constant.METHOD_GET_PLATFORM_VERSION)) {
                    c2 = 14;
                    break;
                }
                break;
            case 1863829223:
                if (str.equals(Constant.METHOD_GET_DATABASES_PATH)) {
                    c2 = 15;
                    break;
                }
                break;
        }
        switch (c2) {
            case 0:
                onExecuteCall(methodCall, result);
                break;
            case 1:
                onCloseDatabaseCall(methodCall, result);
                break;
            case 2:
                o(methodCall, result);
                break;
            case 3:
                onInsertCall(methodCall, result);
                break;
            case 4:
                onUpdateCall(methodCall, result);
                break;
            case 5:
                onSetLocaleCall(methodCall, result);
                break;
            case 6:
                onDeleteDatabaseCall(methodCall, result);
                break;
            case 7:
                onDebugModeCall(methodCall, result);
                break;
            case '\b':
                onOpenDatabaseCall(methodCall, result);
                break;
            case '\t':
                onBatchCall(methodCall, result);
                break;
            case '\n':
                onDebugCall(methodCall, result);
                break;
            case 11:
                onQueryCall(methodCall, result);
                break;
            case '\f':
                onDatabaseExistsCall(methodCall, result);
                break;
            case '\r':
                onQueryCursorNextCall(methodCall, result);
                break;
            case 14:
                result.success("Android " + Build.VERSION.RELEASE);
                break;
            case 15:
                n(methodCall, result);
                break;
            default:
                result.notImplemented();
                break;
        }
    }

    public SqflitePlugin(Context context) {
        this.context = context.getApplicationContext();
    }

    private void onAttachedToEngine(Context context, BinaryMessenger binaryMessenger) {
        this.context = context;
        MethodChannel methodChannel = new MethodChannel(binaryMessenger, Constant.PLUGIN_KEY, StandardMethodCodec.INSTANCE, binaryMessenger.makeBackgroundTaskQueue());
        this.methodChannel = methodChannel;
        methodChannel.setMethodCallHandler(this);
    }
}
