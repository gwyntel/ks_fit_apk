package com.umeng.analytics.pro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.database.sqlite.SQLiteOpenHelper;
import com.umeng.commonsdk.debug.UMRTLog;

/* loaded from: classes4.dex */
public class be extends SQLiteOpenHelper {

    /* renamed from: b, reason: collision with root package name */
    private static final Object f21468b = new Object();

    /* renamed from: c, reason: collision with root package name */
    private static be f21469c = null;

    /* renamed from: d, reason: collision with root package name */
    private static final String f21470d = "CREATE TABLE IF NOT EXISTS stf(_id INTEGER PRIMARY KEY AUTOINCREMENT, _tp TEXT, _hd TEXT, _bd TEXT, _ts TEXT, _uuid TEXT, _re1 TEXT, _re2 TEXT)";

    /* renamed from: e, reason: collision with root package name */
    private static final String f21471e = "DROP TABLE IF EXISTS stf";

    /* renamed from: f, reason: collision with root package name */
    private static final String f21472f = "DELETE FROM stf WHERE _id IN( SELECT _id FROM stf ORDER BY _id LIMIT 1)";

    /* renamed from: a, reason: collision with root package name */
    private final Context f21473a;

    private be(Context context, String str, SQLiteDatabase.CursorFactory cursorFactory, int i2) {
        super(context, str, cursorFactory, i2);
        this.f21473a = context;
    }

    public static final int a() {
        return 1;
    }

    private void b(SQLiteDatabase sQLiteDatabase) throws SQLException {
        try {
            sQLiteDatabase.execSQL(f21470d);
        } catch (SQLiteDatabaseCorruptException unused) {
            a(sQLiteDatabase);
        } catch (Throwable th) {
            UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> [有状态]创建二级缓存数据库失败: " + th.getMessage());
        }
    }

    private void d() {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            if (writableDatabase == null || !writableDatabase.isOpen()) {
                return;
            }
            try {
                writableDatabase.execSQL(f21472f);
            } catch (Throwable unused) {
            }
            writableDatabase.close();
        } catch (Throwable unused2) {
        }
    }

    public boolean c() {
        return !b(bg.f21482c);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) throws SQLException {
        b(sQLiteDatabase);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i2, int i3) {
    }

    public static be a(Context context) {
        be beVar;
        synchronized (f21468b) {
            try {
                if (f21469c == null) {
                    f21469c = new be(context, bg.f21481b, null, 1);
                }
                beVar = f21469c;
            } catch (Throwable th) {
                throw th;
            }
        }
        return beVar;
    }

    public void b() {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            if (writableDatabase == null || !writableDatabase.isOpen()) {
                return;
            }
            writableDatabase.close();
        } catch (Throwable unused) {
        }
    }

    private void a(SQLiteDatabase sQLiteDatabase) throws SQLException {
        try {
            sQLiteDatabase.execSQL(f21471e);
            sQLiteDatabase.execSQL(f21470d);
        } catch (SQLException unused) {
        }
    }

    public boolean b(String str) {
        SQLiteDatabase writableDatabase;
        Cursor cursorQuery = null;
        try {
            writableDatabase = getWritableDatabase();
            if (writableDatabase != null) {
                try {
                    if (writableDatabase.isOpen()) {
                        cursorQuery = writableDatabase.query(str, null, null, null, null, null, null, null);
                    }
                } catch (Throwable unused) {
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    if (writableDatabase == null) {
                        return false;
                    }
                    writableDatabase.close();
                    return false;
                }
            }
            if (cursorQuery != null) {
                if (cursorQuery.getCount() > 0) {
                    cursorQuery.close();
                    if (writableDatabase == null) {
                        return true;
                    }
                    writableDatabase.close();
                    return true;
                }
            }
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            if (writableDatabase == null) {
                return false;
            }
        } catch (Throwable unused2) {
            writableDatabase = null;
        }
        writableDatabase.close();
        return false;
    }

    public void a(String str, ContentValues contentValues) {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            if (writableDatabase == null || !writableDatabase.isOpen()) {
                return;
            }
            try {
                writableDatabase.beginTransaction();
                writableDatabase.insert(str, null, contentValues);
                writableDatabase.setTransactionSuccessful();
                UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> [有状态]插入二级缓存数据记录 成功。");
            } catch (Throwable unused) {
            }
            writableDatabase.endTransaction();
            writableDatabase.close();
        } catch (Throwable unused2) {
        }
    }

    public void a(String str, String str2, String[] strArr) {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            if (writableDatabase == null || !writableDatabase.isOpen()) {
                return;
            }
            try {
                writableDatabase.beginTransaction();
                writableDatabase.delete(str, str2, strArr);
                writableDatabase.setTransactionSuccessful();
            } catch (Throwable unused) {
            }
            writableDatabase.endTransaction();
            writableDatabase.close();
        } catch (Throwable unused2) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x006f A[PHI: r0 r1
      0x006f: PHI (r0v4 com.umeng.analytics.pro.bf) = (r0v1 com.umeng.analytics.pro.bf), (r0v21 com.umeng.analytics.pro.bf) binds: [B:16:0x0077, B:12:0x006d] A[DONT_GENERATE, DONT_INLINE]
      0x006f: PHI (r1v2 android.database.Cursor) = (r1v1 android.database.Cursor), (r1v5 android.database.Cursor) binds: [B:16:0x0077, B:12:0x006d] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.umeng.analytics.pro.bf a(java.lang.String r11) {
        /*
            r10 = this;
            r0 = 0
            java.lang.String r1 = "_uuid"
            java.lang.String r2 = "_tp"
            java.lang.String r3 = "_hd"
            java.lang.String r4 = "_bd"
            java.lang.String r5 = "_re1"
            java.lang.String r6 = "_re2"
            java.lang.String[] r3 = new java.lang.String[]{r1, r2, r3, r4, r5, r6}     // Catch: java.lang.Throwable -> L73
            java.lang.String r9 = "1"
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            r1 = r10
            r2 = r11
            android.database.Cursor r1 = r1.a(r2, r3, r4, r5, r6, r7, r8, r9)     // Catch: java.lang.Throwable -> L73
            if (r1 == 0) goto L6d
            boolean r2 = r1.moveToFirst()     // Catch: java.lang.Throwable -> L74
            if (r2 == 0) goto L6d
            com.umeng.analytics.pro.bf r2 = new com.umeng.analytics.pro.bf     // Catch: java.lang.Throwable -> L74
            r2.<init>()     // Catch: java.lang.Throwable -> L74
            r0 = 0
            java.lang.String r0 = r1.getString(r0)     // Catch: java.lang.Throwable -> L6b
            r2.f21474a = r0     // Catch: java.lang.Throwable -> L6b
            r0 = 1
            java.lang.String r0 = r1.getString(r0)     // Catch: java.lang.Throwable -> L6b
            r2.f21475b = r0     // Catch: java.lang.Throwable -> L6b
            r0 = 2
            java.lang.String r0 = r1.getString(r0)     // Catch: java.lang.Throwable -> L6b
            r3 = 3
            java.lang.String r3 = r1.getString(r3)     // Catch: java.lang.Throwable -> L6b
            android.content.Context r4 = r10.f21473a     // Catch: java.lang.Throwable -> L6b
            com.umeng.analytics.pro.k r4 = com.umeng.analytics.pro.k.a(r4)     // Catch: java.lang.Throwable -> L6b
            java.lang.String r0 = r4.d(r0)     // Catch: java.lang.Throwable -> L6b
            r2.f21476c = r0     // Catch: java.lang.Throwable -> L6b
            android.content.Context r0 = r10.f21473a     // Catch: java.lang.Throwable -> L6b
            com.umeng.analytics.pro.k r0 = com.umeng.analytics.pro.k.a(r0)     // Catch: java.lang.Throwable -> L6b
            java.lang.String r0 = r0.d(r3)     // Catch: java.lang.Throwable -> L6b
            r2.f21477d = r0     // Catch: java.lang.Throwable -> L6b
            r0 = 4
            java.lang.String r0 = r1.getString(r0)     // Catch: java.lang.Throwable -> L6b
            r2.f21478e = r0     // Catch: java.lang.Throwable -> L6b
            r0 = 5
            java.lang.String r0 = r1.getString(r0)     // Catch: java.lang.Throwable -> L6b
            r2.f21479f = r0     // Catch: java.lang.Throwable -> L6b
            r0 = r2
            goto L6d
        L6b:
            r0 = r2
            goto L74
        L6d:
            if (r1 == 0) goto L7a
        L6f:
            r1.close()
            goto L7a
        L73:
            r1 = r0
        L74:
            r10.d()     // Catch: java.lang.Throwable -> L7b
            if (r1 == 0) goto L7a
            goto L6f
        L7a:
            return r0
        L7b:
            r0 = move-exception
            r2 = r0
            if (r1 == 0) goto L82
            r1.close()
        L82:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.be.a(java.lang.String):com.umeng.analytics.pro.bf");
    }

    public void a(String str, String str2) {
        a(str, "_uuid=?", new String[]{str2});
    }

    public Cursor a(String str, String[] strArr, String str2, String[] strArr2, String str3, String str4, String str5, String str6) {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            if (writableDatabase == null || !writableDatabase.isOpen()) {
                return null;
            }
            return writableDatabase.query(str, strArr, str2, strArr2, str3, str4, str5, str6);
        } catch (Throwable unused) {
            return null;
        }
    }
}
