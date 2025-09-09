package com.umeng.analytics.pro;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import com.umeng.analytics.pro.g;

/* loaded from: classes4.dex */
class h extends SQLiteOpenHelper {

    /* renamed from: b, reason: collision with root package name */
    private static Context f21772b;

    /* renamed from: a, reason: collision with root package name */
    private String f21773a;

    private static class a {

        /* renamed from: a, reason: collision with root package name */
        private static final h f21774a = new h(h.f21772b, j.b(h.f21772b), g.f21706b, null, 2);

        private a() {
        }
    }

    public static h a(Context context) {
        if (f21772b == null) {
            f21772b = context.getApplicationContext();
        }
        return a.f21774a;
    }

    private void c(SQLiteDatabase sQLiteDatabase) throws SQLException {
        try {
            this.f21773a = "create table if not exists __sd(id INTEGER primary key autoincrement, __ii TEXT unique, __a TEXT, __b TEXT, __c TEXT, __d TEXT, __e TEXT, __f TEXT, __g TEXT, __sp TEXT, __pp TEXT, __av TEXT, __vc TEXT)";
            sQLiteDatabase.execSQL("create table if not exists __sd(id INTEGER primary key autoincrement, __ii TEXT unique, __a TEXT, __b TEXT, __c TEXT, __d TEXT, __e TEXT, __f TEXT, __g TEXT, __sp TEXT, __pp TEXT, __av TEXT, __vc TEXT)");
        } catch (SQLException unused) {
        }
    }

    private void d(SQLiteDatabase sQLiteDatabase) throws SQLException {
        try {
            this.f21773a = "create table if not exists __is(id INTEGER primary key autoincrement, __ii TEXT unique, __e TEXT, __sp TEXT, __pp TEXT, __av TEXT, __vc TEXT)";
            sQLiteDatabase.execSQL("create table if not exists __is(id INTEGER primary key autoincrement, __ii TEXT unique, __e TEXT, __sp TEXT, __pp TEXT, __av TEXT, __vc TEXT)");
        } catch (SQLException unused) {
        }
    }

    private void e(SQLiteDatabase sQLiteDatabase) throws SQLException {
        if (!j.a(sQLiteDatabase, g.d.f21747a, "__av")) {
            j.a(sQLiteDatabase, g.d.f21747a, "__sp", "TEXT");
            j.a(sQLiteDatabase, g.d.f21747a, "__pp", "TEXT");
            j.a(sQLiteDatabase, g.d.f21747a, "__av", "TEXT");
            j.a(sQLiteDatabase, g.d.f21747a, "__vc", "TEXT");
        }
        if (!j.a(sQLiteDatabase, g.b.f21721a, "__av")) {
            j.a(sQLiteDatabase, g.b.f21721a, "__av", "TEXT");
            j.a(sQLiteDatabase, g.b.f21721a, "__vc", "TEXT");
        }
        if (j.a(sQLiteDatabase, g.a.f21710a, "__av")) {
            return;
        }
        j.a(sQLiteDatabase, g.a.f21710a, "__av", "TEXT");
        j.a(sQLiteDatabase, g.a.f21710a, "__vc", "TEXT");
    }

    private void f(SQLiteDatabase sQLiteDatabase) throws SQLException {
        a(sQLiteDatabase, g.d.f21747a);
        a(sQLiteDatabase, g.b.f21721a);
        a(sQLiteDatabase, g.a.f21710a);
        a();
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        try {
            try {
                sQLiteDatabase.beginTransaction();
                c(sQLiteDatabase);
                d(sQLiteDatabase);
                b(sQLiteDatabase);
                a(sQLiteDatabase);
                sQLiteDatabase.setTransactionSuccessful();
            } catch (SQLiteDatabaseCorruptException unused) {
                j.a(f21772b);
                if (sQLiteDatabase == null) {
                    return;
                }
            } catch (Throwable unused2) {
                if (sQLiteDatabase == null) {
                    return;
                }
            }
            try {
                sQLiteDatabase.endTransaction();
            } catch (Throwable unused3) {
            }
        } catch (Throwable th) {
            if (sQLiteDatabase != null) {
                try {
                    sQLiteDatabase.endTransaction();
                } catch (Throwable unused4) {
                }
            }
            throw th;
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i2, int i3) throws SQLException {
        if (i3 <= i2 || i2 != 1) {
            return;
        }
        try {
            try {
                e(sQLiteDatabase);
            } catch (Exception unused) {
                e(sQLiteDatabase);
            }
        } catch (Exception unused2) {
            f(sQLiteDatabase);
        }
    }

    private h(Context context, String str, String str2, SQLiteDatabase.CursorFactory cursorFactory, int i2) {
        this(new e(context, str), str2, cursorFactory, i2);
    }

    private void b(SQLiteDatabase sQLiteDatabase) throws SQLException {
        try {
            this.f21773a = "create table if not exists __et(id INTEGER primary key autoincrement, __i TEXT, __e TEXT, __s TEXT, __t INTEGER, __av TEXT, __vc TEXT)";
            sQLiteDatabase.execSQL("create table if not exists __et(id INTEGER primary key autoincrement, __i TEXT, __e TEXT, __s TEXT, __t INTEGER, __av TEXT, __vc TEXT)");
        } catch (SQLException unused) {
        }
    }

    private h(Context context, String str, SQLiteDatabase.CursorFactory cursorFactory, int i2) {
        super(context, TextUtils.isEmpty(str) ? g.f21706b : str, cursorFactory, i2);
        this.f21773a = null;
        a();
    }

    public void a() {
        try {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            if (!j.a(g.d.f21747a, writableDatabase)) {
                c(writableDatabase);
            }
            if (!j.a(g.c.f21734a, writableDatabase)) {
                d(writableDatabase);
            }
            if (!j.a(g.b.f21721a, writableDatabase)) {
                b(writableDatabase);
            }
            if (j.a(g.a.f21710a, writableDatabase)) {
                return;
            }
            a(writableDatabase);
        } catch (Exception unused) {
        }
    }

    private void a(SQLiteDatabase sQLiteDatabase) throws SQLException {
        try {
            this.f21773a = "create table if not exists __er(id INTEGER primary key autoincrement, __i TEXT, __a TEXT, __t INTEGER, __av TEXT, __vc TEXT)";
            sQLiteDatabase.execSQL("create table if not exists __er(id INTEGER primary key autoincrement, __i TEXT, __a TEXT, __t INTEGER, __av TEXT, __vc TEXT)");
        } catch (SQLException unused) {
        }
    }

    private void a(SQLiteDatabase sQLiteDatabase, String str) throws SQLException {
        try {
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS " + str);
        } catch (SQLException unused) {
        }
    }
}
