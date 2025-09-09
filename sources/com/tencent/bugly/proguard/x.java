package com.tencent.bugly.proguard;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import java.io.File;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public final class x extends SQLiteOpenHelper {

    /* renamed from: a, reason: collision with root package name */
    public static String f21184a = "bugly_db";

    /* renamed from: b, reason: collision with root package name */
    public static int f21185b = 16;

    /* renamed from: c, reason: collision with root package name */
    protected Context f21186c;

    /* renamed from: d, reason: collision with root package name */
    private List<o> f21187d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public x(Context context, List<o> list) {
        super(context, f21184a + OpenAccountUIConstants.UNDER_LINE, (SQLiteDatabase.CursorFactory) null, f21185b);
        aa.a(context).getClass();
        this.f21186c = context;
        this.f21187d = list;
    }

    private synchronized boolean a(SQLiteDatabase sQLiteDatabase) {
        try {
            String[] strArr = {"t_lr", "t_ui", "t_pf"};
            for (int i2 = 0; i2 < 3; i2++) {
                sQLiteDatabase.execSQL("DROP TABLE IF EXISTS ".concat(String.valueOf(strArr[i2])), new String[0]);
            }
        } catch (Throwable th) {
            if (!al.b(th)) {
                th.printStackTrace();
            }
            return false;
        }
        return true;
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final synchronized SQLiteDatabase getReadableDatabase() {
        SQLiteDatabase readableDatabase;
        readableDatabase = null;
        int i2 = 0;
        while (readableDatabase == null && i2 < 5) {
            i2++;
            try {
                readableDatabase = super.getReadableDatabase();
            } catch (Throwable unused) {
                al.d("[Database] Try to get db(count: %d).", Integer.valueOf(i2));
                if (i2 == 5) {
                    al.e("[Database] Failed to get db.", new Object[0]);
                }
                try {
                    Thread.sleep(200L);
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
            }
        }
        return readableDatabase;
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final synchronized SQLiteDatabase getWritableDatabase() {
        SQLiteDatabase writableDatabase;
        writableDatabase = null;
        int i2 = 0;
        while (writableDatabase == null && i2 < 5) {
            i2++;
            try {
                writableDatabase = super.getWritableDatabase();
            } catch (Throwable unused) {
                al.d("[Database] Try to get db(count: %d).", Integer.valueOf(i2));
                if (i2 == 5) {
                    al.e("[Database] Failed to get db.", new Object[0]);
                }
                try {
                    Thread.sleep(200L);
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
            }
        }
        if (writableDatabase == null) {
            al.d("[Database] db error delay error record 1min.", new Object[0]);
        }
        return writableDatabase;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x00e5 A[DONT_GENERATE] */
    /* JADX WARN: Removed duplicated region for block: B:15:0x00e7 A[Catch: all -> 0x00df, TRY_ENTER, TryCatch #2 {all -> 0x00df, blocks: (B:11:0x00e1, B:15:0x00e7, B:16:0x00eb, B:18:0x00f1, B:22:0x00fc, B:24:0x0102, B:6:0x00d5, B:8:0x00db, B:3:0x0001, B:19:0x00f7), top: B:30:0x0001, inners: #0, #1 }] */
    @Override // android.database.sqlite.SQLiteOpenHelper
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final synchronized void onCreate(android.database.sqlite.SQLiteDatabase r5) {
        /*
            Method dump skipped, instructions count: 266
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.x.onCreate(android.database.sqlite.SQLiteDatabase):void");
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    @TargetApi(11)
    public final synchronized void onDowngrade(SQLiteDatabase sQLiteDatabase, int i2, int i3) {
        try {
            if (ab.c() >= 11) {
                al.d("[Database] Downgrade %d to %d drop tables.", Integer.valueOf(i2), Integer.valueOf(i3));
                List<o> list = this.f21187d;
                if (list != null) {
                    Iterator<o> it = list.iterator();
                    while (it.hasNext()) {
                        try {
                            it.next().onDbDowngrade(sQLiteDatabase, i2, i3);
                        } catch (Throwable th) {
                            if (!al.b(th)) {
                                th.printStackTrace();
                            }
                        }
                    }
                }
                if (a(sQLiteDatabase)) {
                    onCreate(sQLiteDatabase);
                    return;
                }
                al.d("[Database] Failed to drop, delete db.", new Object[0]);
                File databasePath = this.f21186c.getDatabasePath(f21184a);
                if (databasePath != null && databasePath.canWrite()) {
                    databasePath.delete();
                }
            }
        } catch (Throwable th2) {
            throw th2;
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public final synchronized void onUpgrade(SQLiteDatabase sQLiteDatabase, int i2, int i3) {
        try {
            al.d("[Database] Upgrade %d to %d , drop tables!", Integer.valueOf(i2), Integer.valueOf(i3));
            List<o> list = this.f21187d;
            if (list != null) {
                Iterator<o> it = list.iterator();
                while (it.hasNext()) {
                    try {
                        it.next().onDbUpgrade(sQLiteDatabase, i2, i3);
                    } catch (Throwable th) {
                        if (!al.b(th)) {
                            th.printStackTrace();
                        }
                    }
                }
            }
            if (a(sQLiteDatabase)) {
                onCreate(sQLiteDatabase);
                return;
            }
            al.d("[Database] Failed to drop, delete db.", new Object[0]);
            File databasePath = this.f21186c.getDatabasePath(f21184a);
            if (databasePath != null && databasePath.canWrite()) {
                databasePath.delete();
            }
        } catch (Throwable th2) {
            throw th2;
        }
    }
}
