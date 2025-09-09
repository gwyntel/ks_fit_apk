package com.tekartik.sqflite;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;
import android.os.Build;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.tekartik.sqflite.operation.BatchOperation;
import com.tekartik.sqflite.operation.MethodCallOperation;
import com.tekartik.sqflite.operation.Operation;
import com.tekartik.sqflite.operation.QueuedOperation;
import com.tekartik.sqflite.operation.SqlErrorInfo;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
class Database {
    private static final String WAL_ENABLED_META_NAME = "com.tekartik.sqflite.wal_enabled";
    private static Boolean walGloballyEnabled;

    /* renamed from: a, reason: collision with root package name */
    final boolean f20455a;

    /* renamed from: b, reason: collision with root package name */
    final String f20456b;

    /* renamed from: c, reason: collision with root package name */
    final int f20457c;

    @Nullable
    private Integer currentTransactionId;

    /* renamed from: d, reason: collision with root package name */
    final int f20458d;
    public DatabaseWorkerPool databaseWorkerPool;

    /* renamed from: e, reason: collision with root package name */
    final Context f20459e;

    /* renamed from: h, reason: collision with root package name */
    SQLiteDatabase f20462h;

    /* renamed from: f, reason: collision with root package name */
    final List f20460f = new ArrayList();

    /* renamed from: g, reason: collision with root package name */
    final Map f20461g = new HashMap();
    private int transactionDepth = 0;
    private int lastTransactionId = 0;
    private int lastCursorId = 0;

    Database(Context context, String str, int i2, boolean z2, int i3) {
        this.f20459e = context;
        this.f20456b = str;
        this.f20455a = z2;
        this.f20457c = i2;
        this.f20458d = i3;
    }

    private void closeCursor(@NonNull SqfliteCursor sqfliteCursor) {
        try {
            int i2 = sqfliteCursor.f20473a;
            if (LogLevel.c(this.f20458d)) {
                Log.d(Constant.TAG, n() + "closing cursor " + i2);
            }
            this.f20461g.remove(Integer.valueOf(i2));
            sqfliteCursor.f20475c.close();
        } catch (Exception unused) {
        }
    }

    private Map<String, Object> cursorToResults(Cursor cursor, @Nullable Integer num) {
        HashMap map = null;
        int columnCount = 0;
        ArrayList arrayList = null;
        while (cursor.moveToNext()) {
            if (map == null) {
                ArrayList arrayList2 = new ArrayList();
                HashMap map2 = new HashMap();
                columnCount = cursor.getColumnCount();
                map2.put(Constant.PARAM_COLUMNS, Arrays.asList(cursor.getColumnNames()));
                map2.put(Constant.PARAM_ROWS, arrayList2);
                arrayList = arrayList2;
                map = map2;
            }
            arrayList.add(Utils.cursorRowToList(cursor, columnCount));
            if (num != null && arrayList.size() >= num.intValue()) {
                break;
            }
        }
        return map == null ? new HashMap() : map;
    }

    private boolean doExecute(Operation operation) {
        if (!executeOrError(operation)) {
            return false;
        }
        operation.success(null);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00d1  */
    /* renamed from: doInsert, reason: merged with bridge method [inline-methods] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean lambda$insert$4(com.tekartik.sqflite.operation.Operation r10) throws java.lang.Throwable {
        /*
            r9 = this;
            boolean r0 = r9.executeOrError(r10)
            r1 = 0
            if (r0 != 0) goto L8
            return r1
        L8:
            boolean r0 = r10.getNoResult()
            r2 = 0
            r3 = 1
            if (r0 == 0) goto L14
            r10.success(r2)
            return r3
        L14:
            java.lang.String r0 = "SELECT changes(), last_insert_rowid()"
            android.database.sqlite.SQLiteDatabase r4 = r9.getWritableDatabase()     // Catch: java.lang.Throwable -> Lc0 java.lang.Exception -> Lc2
            android.database.Cursor r0 = r4.rawQuery(r0, r2)     // Catch: java.lang.Throwable -> Lc0 java.lang.Exception -> Lc2
            java.lang.String r4 = "Sqflite"
            if (r0 == 0) goto L9f
            int r5 = r0.getCount()     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L64
            if (r5 <= 0) goto L9f
            boolean r5 = r0.moveToFirst()     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L64
            if (r5 == 0) goto L9f
            int r5 = r0.getInt(r1)     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L64
            if (r5 != 0) goto L6d
            int r5 = r9.f20458d     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L64
            boolean r5 = com.tekartik.sqflite.LogLevel.b(r5)     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L64
            if (r5 == 0) goto L66
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L64
            r5.<init>()     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L64
            java.lang.String r6 = r9.n()     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L64
            r5.append(r6)     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L64
            java.lang.String r6 = "no changes (id was "
            r5.append(r6)     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L64
            long r6 = r0.getLong(r3)     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L64
            r5.append(r6)     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L64
            java.lang.String r6 = ")"
            r5.append(r6)     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L64
            java.lang.String r5 = r5.toString()     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L64
            android.util.Log.d(r4, r5)     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L64
            goto L66
        L61:
            r10 = move-exception
            r2 = r0
            goto Lcf
        L64:
            r2 = move-exception
            goto Lc6
        L66:
            r10.success(r2)     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L64
            r0.close()
            return r3
        L6d:
            long r5 = r0.getLong(r3)     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L64
            int r2 = r9.f20458d     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L64
            boolean r2 = com.tekartik.sqflite.LogLevel.b(r2)     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L64
            if (r2 == 0) goto L94
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L64
            r2.<init>()     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L64
            java.lang.String r7 = r9.n()     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L64
            r2.append(r7)     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L64
            java.lang.String r7 = "inserted "
            r2.append(r7)     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L64
            r2.append(r5)     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L64
            java.lang.String r2 = r2.toString()     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L64
            android.util.Log.d(r4, r2)     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L64
        L94:
            java.lang.Long r2 = java.lang.Long.valueOf(r5)     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L64
            r10.success(r2)     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L64
            r0.close()
            return r3
        L9f:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L64
            r5.<init>()     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L64
            java.lang.String r6 = r9.n()     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L64
            r5.append(r6)     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L64
            java.lang.String r6 = "fail to read changes for Insert"
            r5.append(r6)     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L64
            java.lang.String r5 = r5.toString()     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L64
            android.util.Log.e(r4, r5)     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L64
            r10.success(r2)     // Catch: java.lang.Throwable -> L61 java.lang.Exception -> L64
            if (r0 == 0) goto Lbf
            r0.close()
        Lbf:
            return r3
        Lc0:
            r10 = move-exception
            goto Lcf
        Lc2:
            r0 = move-exception
            r8 = r2
            r2 = r0
            r0 = r8
        Lc6:
            r9.p(r2, r10)     // Catch: java.lang.Throwable -> L61
            if (r0 == 0) goto Lce
            r0.close()
        Lce:
            return r1
        Lcf:
            if (r2 == 0) goto Ld4
            r2.close()
        Ld4:
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tekartik.sqflite.Database.lambda$insert$4(com.tekartik.sqflite.operation.Operation):boolean");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: doQuery, reason: merged with bridge method [inline-methods] */
    public boolean lambda$query$0(@NonNull Operation operation) throws Throwable {
        Cursor cursorRawQueryWithFactory;
        Integer num = (Integer) operation.getArgument(Constant.PARAM_CURSOR_PAGE_SIZE);
        final SqlCommand sqlCommand = operation.getSqlCommand();
        if (LogLevel.b(this.f20458d)) {
            Log.d(Constant.TAG, n() + sqlCommand);
        }
        SqfliteCursor sqfliteCursor = null;
        try {
            cursorRawQueryWithFactory = getReadableDatabase().rawQueryWithFactory(new SQLiteDatabase.CursorFactory() { // from class: com.tekartik.sqflite.i
                @Override // android.database.sqlite.SQLiteDatabase.CursorFactory
                public final Cursor newCursor(SQLiteDatabase sQLiteDatabase, SQLiteCursorDriver sQLiteCursorDriver, String str, SQLiteQuery sQLiteQuery) {
                    return Database.lambda$doQuery$1(sqlCommand, sQLiteDatabase, sQLiteCursorDriver, str, sQLiteQuery);
                }
            }, sqlCommand.getSql(), Constant.EMPTY_STRING_ARRAY, null);
            try {
                try {
                    Map<String, Object> mapCursorToResults = cursorToResults(cursorRawQueryWithFactory, num);
                    if (num != null && !cursorRawQueryWithFactory.isLast() && !cursorRawQueryWithFactory.isAfterLast()) {
                        int i2 = this.lastCursorId + 1;
                        this.lastCursorId = i2;
                        mapCursorToResults.put(Constant.PARAM_CURSOR_ID, Integer.valueOf(i2));
                        SqfliteCursor sqfliteCursor2 = new SqfliteCursor(i2, num.intValue(), cursorRawQueryWithFactory);
                        try {
                            this.f20461g.put(Integer.valueOf(i2), sqfliteCursor2);
                            sqfliteCursor = sqfliteCursor2;
                        } catch (Exception e2) {
                            e = e2;
                            sqfliteCursor = sqfliteCursor2;
                            p(e, operation);
                            if (sqfliteCursor != null) {
                                closeCursor(sqfliteCursor);
                            }
                            if (sqfliteCursor != null || cursorRawQueryWithFactory == null) {
                                return false;
                            }
                            cursorRawQueryWithFactory.close();
                            return false;
                        } catch (Throwable th) {
                            th = th;
                            sqfliteCursor = sqfliteCursor2;
                            if (sqfliteCursor == null && cursorRawQueryWithFactory != null) {
                                cursorRawQueryWithFactory.close();
                            }
                            throw th;
                        }
                    }
                    operation.success(mapCursorToResults);
                    if (sqfliteCursor == null && cursorRawQueryWithFactory != null) {
                        cursorRawQueryWithFactory.close();
                    }
                    return true;
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Exception e3) {
                e = e3;
            }
        } catch (Exception e4) {
            e = e4;
            cursorRawQueryWithFactory = null;
        } catch (Throwable th3) {
            th = th3;
            cursorRawQueryWithFactory = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: doQueryCursorNext, reason: merged with bridge method [inline-methods] */
    public boolean lambda$queryCursorNext$2(@NonNull Operation operation) throws Throwable {
        boolean z2;
        Integer num = (Integer) operation.getArgument(Constant.PARAM_CURSOR_ID);
        int iIntValue = num.intValue();
        boolean zEquals = Boolean.TRUE.equals(operation.getArgument(Constant.PARAM_CANCEL));
        if (LogLevel.c(this.f20458d)) {
            StringBuilder sb = new StringBuilder();
            sb.append(n());
            sb.append("cursor ");
            sb.append(iIntValue);
            sb.append(zEquals ? " cancel" : " next");
            Log.d(Constant.TAG, sb.toString());
        }
        SqfliteCursor sqfliteCursor = null;
        if (zEquals) {
            closeCursor(iIntValue);
            operation.success(null);
            return true;
        }
        SqfliteCursor sqfliteCursor2 = (SqfliteCursor) this.f20461g.get(num);
        int i2 = 0;
        try {
            try {
                if (sqfliteCursor2 == null) {
                    throw new IllegalStateException("Cursor " + iIntValue + " not found");
                }
                Cursor cursor = sqfliteCursor2.f20475c;
                Map<String, Object> mapCursorToResults = cursorToResults(cursor, Integer.valueOf(sqfliteCursor2.f20474b));
                z2 = (cursor.isLast() || cursor.isAfterLast()) ? false : true;
                if (z2) {
                    try {
                        mapCursorToResults.put(Constant.PARAM_CURSOR_ID, num);
                    } catch (Exception e2) {
                        e = e2;
                        p(e, operation);
                        if (sqfliteCursor2 != null) {
                            closeCursor(sqfliteCursor2);
                        } else {
                            sqfliteCursor = sqfliteCursor2;
                        }
                        if (!z2 && sqfliteCursor != null) {
                            closeCursor(sqfliteCursor);
                        }
                        return false;
                    }
                }
                operation.success(mapCursorToResults);
                if (!z2) {
                    closeCursor(sqfliteCursor2);
                }
                return true;
            } catch (Exception e3) {
                e = e3;
                z2 = false;
            } catch (Throwable th) {
                th = th;
                if (i2 == 0) {
                    closeCursor(sqfliteCursor2);
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            i2 = iIntValue;
            if (i2 == 0 && sqfliteCursor2 != null) {
                closeCursor(sqfliteCursor2);
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: doUpdate, reason: merged with bridge method [inline-methods] */
    public boolean lambda$update$5(Operation operation) throws Throwable {
        if (!executeOrError(operation)) {
            return false;
        }
        Cursor cursor = null;
        if (operation.getNoResult()) {
            operation.success(null);
            return true;
        }
        try {
            try {
                Cursor cursorRawQuery = getWritableDatabase().rawQuery("SELECT changes()", null);
                if (cursorRawQuery != null) {
                    try {
                        if (cursorRawQuery.getCount() > 0 && cursorRawQuery.moveToFirst()) {
                            int i2 = cursorRawQuery.getInt(0);
                            if (LogLevel.b(this.f20458d)) {
                                Log.d(Constant.TAG, n() + "changed " + i2);
                            }
                            operation.success(Integer.valueOf(i2));
                            cursorRawQuery.close();
                            return true;
                        }
                    } catch (Exception e2) {
                        e = e2;
                        cursor = cursorRawQuery;
                        p(e, operation);
                        if (cursor != null) {
                            cursor.close();
                        }
                        return false;
                    } catch (Throwable th) {
                        th = th;
                        cursor = cursorRawQuery;
                        if (cursor != null) {
                            cursor.close();
                        }
                        throw th;
                    }
                }
                Log.e(Constant.TAG, n() + "fail to read changes for Update/Delete");
                operation.success(null);
                if (cursorRawQuery != null) {
                    cursorRawQuery.close();
                }
                return true;
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e3) {
            e = e3;
        }
    }

    private boolean executeOrError(Operation operation) throws SQLException {
        SqlCommand sqlCommand = operation.getSqlCommand();
        if (LogLevel.b(this.f20458d)) {
            Log.d(Constant.TAG, n() + sqlCommand);
        }
        Boolean inTransactionChange = operation.getInTransactionChange();
        try {
            getWritableDatabase().execSQL(sqlCommand.getSql(), sqlCommand.getSqlArguments());
            l(inTransactionChange);
            return true;
        } catch (Exception e2) {
            p(e2, operation);
            return false;
        }
    }

    public static boolean existsDatabase(String str) {
        try {
            return new File(str).exists();
        } catch (Exception unused) {
            return false;
        }
    }

    protected static boolean i(Context context, String str, boolean z2) {
        try {
            String packageName = context.getPackageName();
            return (Build.VERSION.SDK_INT >= 33 ? context.getPackageManager().getApplicationInfo(packageName, PackageManager.ApplicationInfoFlags.of(128L)) : m(context, packageName, 128)).metaData.getBoolean(str, z2);
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    protected static boolean j(Context context) {
        return i(context, WAL_ENABLED_META_NAME, false);
    }

    static void k(String str) {
        SQLiteDatabase.deleteDatabase(new File(str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Cursor lambda$doQuery$1(SqlCommand sqlCommand, SQLiteDatabase sQLiteDatabase, SQLiteCursorDriver sQLiteCursorDriver, String str, SQLiteQuery sQLiteQuery) {
        sqlCommand.bindTo(sQLiteQuery);
        return new SQLiteCursor(sQLiteCursorDriver, str, sQLiteQuery);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$execute$3(Operation operation) {
        Boolean inTransactionChange = operation.getInTransactionChange();
        boolean z2 = Boolean.TRUE.equals(inTransactionChange) && operation.hasNullTransactionId();
        if (z2) {
            int i2 = this.lastTransactionId + 1;
            this.lastTransactionId = i2;
            this.currentTransactionId = Integer.valueOf(i2);
        }
        if (!executeOrError(operation)) {
            if (z2) {
                this.currentTransactionId = null;
            }
        } else if (z2) {
            HashMap map = new HashMap();
            map.put(Constant.PARAM_TRANSACTION_ID, this.currentTransactionId);
            operation.success(map);
        } else {
            if (Boolean.FALSE.equals(inTransactionChange)) {
                this.currentTransactionId = null;
            }
            operation.success(null);
        }
    }

    static ApplicationInfo m(Context context, String str, int i2) {
        return context.getPackageManager().getApplicationInfo(str, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void runQueuedOperations() {
        while (!this.f20460f.isEmpty() && this.currentTransactionId == null) {
            ((QueuedOperation) this.f20460f.get(0)).run();
            this.f20460f.remove(0);
        }
    }

    private void wrapSqlOperationHandler(@NonNull Operation operation, Runnable runnable) {
        Integer transactionId = operation.getTransactionId();
        Integer num = this.currentTransactionId;
        if (num == null) {
            runnable.run();
            return;
        }
        if (transactionId == null || !(transactionId.equals(num) || transactionId.intValue() == -1)) {
            this.f20460f.add(new QueuedOperation(operation, runnable));
            return;
        }
        runnable.run();
        if (this.currentTransactionId != null || this.f20460f.isEmpty()) {
            return;
        }
        this.databaseWorkerPool.post(this, new Runnable() { // from class: com.tekartik.sqflite.e
            @Override // java.lang.Runnable
            public final void run() {
                this.f20491a.runQueuedOperations();
            }
        });
    }

    public void close() {
        if (!this.f20461g.isEmpty() && LogLevel.b(this.f20458d)) {
            Log.d(Constant.TAG, n() + this.f20461g.size() + " cursor(s) are left opened");
        }
        this.f20462h.close();
    }

    public boolean enableWriteAheadLogging() {
        try {
            return this.f20462h.enableWriteAheadLogging();
        } catch (Exception e2) {
            Log.e(Constant.TAG, n() + "enable WAL error: " + e2);
            return false;
        }
    }

    public void execute(@NonNull final Operation operation) {
        wrapSqlOperationHandler(operation, new Runnable() { // from class: com.tekartik.sqflite.h
            @Override // java.lang.Runnable
            public final void run() {
                this.f20496a.lambda$execute$3(operation);
            }
        });
    }

    public SQLiteDatabase getReadableDatabase() {
        return this.f20462h;
    }

    public SQLiteDatabase getWritableDatabase() {
        return this.f20462h;
    }

    void h(MethodCall methodCall, MethodChannel.Result result) {
        BatchOperation batchOperation;
        String method;
        MethodCallOperation methodCallOperation = new MethodCallOperation(methodCall, result);
        boolean noResult = methodCallOperation.getNoResult();
        boolean continueOnError = methodCallOperation.getContinueOnError();
        List list = (List) methodCallOperation.getArgument("operations");
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            batchOperation = new BatchOperation((Map) it.next(), noResult);
            method = batchOperation.getMethod();
            method.hashCode();
            switch (method) {
                case "execute":
                    if (doExecute(batchOperation)) {
                        batchOperation.handleSuccess(arrayList);
                        break;
                    } else if (!continueOnError) {
                        batchOperation.handleError(result);
                        return;
                    } else {
                        batchOperation.handleErrorContinue(arrayList);
                        break;
                    }
                case "insert":
                    if (lambda$insert$4(batchOperation)) {
                        batchOperation.handleSuccess(arrayList);
                        break;
                    } else if (!continueOnError) {
                        batchOperation.handleError(result);
                        return;
                    } else {
                        batchOperation.handleErrorContinue(arrayList);
                        break;
                    }
                case "update":
                    if (lambda$update$5(batchOperation)) {
                        batchOperation.handleSuccess(arrayList);
                        break;
                    } else if (!continueOnError) {
                        batchOperation.handleError(result);
                        return;
                    } else {
                        batchOperation.handleErrorContinue(arrayList);
                        break;
                    }
                case "query":
                    if (lambda$query$0(batchOperation)) {
                        batchOperation.handleSuccess(arrayList);
                        break;
                    } else if (!continueOnError) {
                        batchOperation.handleError(result);
                        return;
                    } else {
                        batchOperation.handleErrorContinue(arrayList);
                        break;
                    }
                default:
                    result.error("bad_param", "Batch method '" + method + "' not supported", null);
                    return;
            }
        }
        if (noResult) {
            result.success(null);
        } else {
            result.success(arrayList);
        }
    }

    public void insert(final Operation operation) {
        wrapSqlOperationHandler(operation, new Runnable() { // from class: com.tekartik.sqflite.g
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f20494a.lambda$insert$4(operation);
            }
        });
    }

    synchronized void l(Boolean bool) {
        try {
            if (Boolean.TRUE.equals(bool)) {
                this.transactionDepth++;
            } else if (Boolean.FALSE.equals(bool)) {
                this.transactionDepth--;
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    String n() {
        return "[" + o() + "] ";
    }

    String o() {
        Thread threadCurrentThread = Thread.currentThread();
        return this.f20457c + "," + threadCurrentThread.getName() + "(" + threadCurrentThread.getId() + ")";
    }

    public void open() {
        if (walGloballyEnabled == null) {
            Boolean boolValueOf = Boolean.valueOf(j(this.f20459e));
            walGloballyEnabled = boolValueOf;
            if (boolValueOf.booleanValue() && LogLevel.c(this.f20458d)) {
                Log.d(Constant.TAG, n() + "[sqflite] WAL enabled");
            }
        }
        this.f20462h = SQLiteDatabase.openDatabase(this.f20456b, null, walGloballyEnabled.booleanValue() ? 805306368 : 268435456);
    }

    public void openReadOnly() {
        this.f20462h = SQLiteDatabase.openDatabase(this.f20456b, null, 1, new DatabaseErrorHandler() { // from class: com.tekartik.sqflite.Database.1
            @Override // android.database.DatabaseErrorHandler
            public void onCorruption(SQLiteDatabase sQLiteDatabase) {
            }
        });
    }

    void p(Exception exc, Operation operation) {
        if (exc instanceof SQLiteCantOpenDatabaseException) {
            operation.error("sqlite_error", "open_failed " + this.f20456b, null);
            return;
        }
        if (exc instanceof SQLException) {
            operation.error("sqlite_error", exc.getMessage(), SqlErrorInfo.getMap(operation));
        } else {
            operation.error("sqlite_error", exc.getMessage(), SqlErrorInfo.getMap(operation));
        }
    }

    synchronized boolean q() {
        return this.transactionDepth > 0;
    }

    public void query(@NonNull final Operation operation) {
        wrapSqlOperationHandler(operation, new Runnable() { // from class: com.tekartik.sqflite.d
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f20489a.lambda$query$0(operation);
            }
        });
    }

    public void queryCursorNext(@NonNull final Operation operation) {
        wrapSqlOperationHandler(operation, new Runnable() { // from class: com.tekartik.sqflite.c
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f20487a.lambda$queryCursorNext$2(operation);
            }
        });
    }

    public void update(@NonNull final Operation operation) {
        wrapSqlOperationHandler(operation, new Runnable() { // from class: com.tekartik.sqflite.f
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f20492a.lambda$update$5(operation);
            }
        });
    }

    private void closeCursor(int i2) {
        SqfliteCursor sqfliteCursor = (SqfliteCursor) this.f20461g.get(Integer.valueOf(i2));
        if (sqfliteCursor != null) {
            closeCursor(sqfliteCursor);
        }
    }
}
