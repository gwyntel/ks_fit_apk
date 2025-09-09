package com.tencent.bugly.proguard;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public final class w {

    /* renamed from: a, reason: collision with root package name */
    public static boolean f21163a = false;

    /* renamed from: b, reason: collision with root package name */
    private static w f21164b;

    /* renamed from: c, reason: collision with root package name */
    private static x f21165c;

    class a extends Thread {

        /* renamed from: b, reason: collision with root package name */
        private int f21167b = 4;

        /* renamed from: c, reason: collision with root package name */
        private v f21168c = null;

        /* renamed from: d, reason: collision with root package name */
        private String f21169d;

        /* renamed from: e, reason: collision with root package name */
        private ContentValues f21170e;

        /* renamed from: f, reason: collision with root package name */
        private boolean f21171f;

        /* renamed from: g, reason: collision with root package name */
        private String[] f21172g;

        /* renamed from: h, reason: collision with root package name */
        private String f21173h;

        /* renamed from: i, reason: collision with root package name */
        private String[] f21174i;

        /* renamed from: j, reason: collision with root package name */
        private String f21175j;

        /* renamed from: k, reason: collision with root package name */
        private String f21176k;

        /* renamed from: l, reason: collision with root package name */
        private String f21177l;

        /* renamed from: m, reason: collision with root package name */
        private String f21178m;

        /* renamed from: n, reason: collision with root package name */
        private String f21179n;

        /* renamed from: o, reason: collision with root package name */
        private String[] f21180o;

        /* renamed from: p, reason: collision with root package name */
        private int f21181p;

        /* renamed from: q, reason: collision with root package name */
        private String f21182q;

        /* renamed from: r, reason: collision with root package name */
        private byte[] f21183r;

        public a() {
        }

        public final void a(int i2, String str, byte[] bArr) {
            this.f21181p = i2;
            this.f21182q = str;
            this.f21183r = bArr;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            switch (this.f21167b) {
                case 1:
                    w.this.a(this.f21169d, this.f21170e, this.f21168c);
                    break;
                case 2:
                    w.this.a(this.f21169d, this.f21179n, this.f21180o, this.f21168c);
                    break;
                case 3:
                    Cursor cursorA = w.this.a(this.f21171f, this.f21169d, this.f21172g, this.f21173h, this.f21174i, this.f21175j, this.f21176k, this.f21177l, this.f21178m, this.f21168c);
                    if (cursorA != null) {
                        cursorA.close();
                        break;
                    }
                    break;
                case 4:
                    w.this.a(this.f21181p, this.f21182q, this.f21183r, this.f21168c);
                    break;
                case 5:
                    w.this.a(this.f21181p, this.f21168c);
                    break;
                case 6:
                    w.this.a(this.f21181p, this.f21182q, this.f21168c);
                    break;
            }
        }
    }

    private w(Context context, List<o> list) {
        f21165c = new x(context, list);
    }

    private synchronized boolean b(y yVar) {
        ContentValues contentValuesD;
        SQLiteDatabase writableDatabase = null;
        try {
            writableDatabase = f21165c.getWritableDatabase();
            if (writableDatabase == null || (contentValuesD = d(yVar)) == null) {
                return false;
            }
            long jReplace = writableDatabase.replace("t_pf", com.umeng.analytics.pro.bg.f21483d, contentValuesD);
            if (jReplace < 0) {
                if (f21163a) {
                    writableDatabase.close();
                }
                return false;
            }
            al.c("[Database] insert %s success.", "t_pf");
            yVar.f21188a = jReplace;
            if (f21163a) {
                writableDatabase.close();
            }
            return true;
        } catch (Throwable th) {
            try {
                if (!al.a(th)) {
                    th.printStackTrace();
                }
                if (f21163a && writableDatabase != null) {
                    writableDatabase.close();
                }
                return false;
            } finally {
                if (f21163a && writableDatabase != null) {
                    writableDatabase.close();
                }
            }
        }
    }

    private static ContentValues c(y yVar) {
        if (yVar == null) {
            return null;
        }
        try {
            ContentValues contentValues = new ContentValues();
            long j2 = yVar.f21188a;
            if (j2 > 0) {
                contentValues.put(com.umeng.analytics.pro.bg.f21483d, Long.valueOf(j2));
            }
            contentValues.put(com.umeng.analytics.pro.bg.f21484e, Integer.valueOf(yVar.f21189b));
            contentValues.put("_pc", yVar.f21190c);
            contentValues.put("_th", yVar.f21191d);
            contentValues.put("_tm", Long.valueOf(yVar.f21192e));
            byte[] bArr = yVar.f21194g;
            if (bArr != null) {
                contentValues.put("_dt", bArr);
            }
            return contentValues;
        } catch (Throwable th) {
            if (!al.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    private static ContentValues d(y yVar) {
        if (yVar != null && !ap.b(yVar.f21193f)) {
            try {
                ContentValues contentValues = new ContentValues();
                long j2 = yVar.f21188a;
                if (j2 > 0) {
                    contentValues.put(com.umeng.analytics.pro.bg.f21483d, Long.valueOf(j2));
                }
                contentValues.put(com.umeng.analytics.pro.bg.f21484e, yVar.f21193f);
                contentValues.put("_tm", Long.valueOf(yVar.f21192e));
                byte[] bArr = yVar.f21194g;
                if (bArr != null) {
                    contentValues.put("_dt", bArr);
                }
                return contentValues;
            } catch (Throwable th) {
                if (!al.a(th)) {
                    th.printStackTrace();
                }
            }
        }
        return null;
    }

    public static synchronized w a(Context context, List<o> list) {
        try {
            if (f21164b == null) {
                f21164b = new w(context, list);
            }
        } catch (Throwable th) {
            throw th;
        }
        return f21164b;
    }

    public static synchronized w a() {
        return f21164b;
    }

    public final Cursor a(String str, String[] strArr, String str2) {
        return a(str, strArr, str2, (String) null, (String) null);
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x00ae A[Catch: all -> 0x002a, PHI: r2
      0x00ae: PHI (r2v2 android.database.sqlite.SQLiteDatabase) = (r2v1 android.database.sqlite.SQLiteDatabase), (r2v4 android.database.sqlite.SQLiteDatabase) binds: [B:57:0x00ca, B:42:0x00ac] A[DONT_GENERATE, DONT_INLINE], TRY_LEAVE, TryCatch #4 {all -> 0x002a, blocks: (B:10:0x0026, B:13:0x002d, B:15:0x0031, B:33:0x0099, B:35:0x00a0, B:54:0x00c3, B:55:0x00c6, B:43:0x00ae, B:62:0x00d1, B:63:0x00d4, B:66:0x00da, B:67:0x00dd, B:40:0x00a8, B:47:0x00b5, B:49:0x00bb), top: B:78:0x0003, inners: #5 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private synchronized java.util.List<com.tencent.bugly.proguard.y> c(int r13) {
        /*
            Method dump skipped, instructions count: 224
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.w.c(int):java.util.List");
    }

    public final Cursor a(String str, String[] strArr, String str2, String str3, String str4) {
        return a(false, str, strArr, str2, null, null, null, str3, str4, null);
    }

    public final int a(String str, String str2) {
        return a(str, str2, (String[]) null, (v) null);
    }

    public final synchronized long a(String str, ContentValues contentValues, v vVar) {
        long j2;
        j2 = -1;
        SQLiteDatabase writableDatabase = null;
        try {
            writableDatabase = f21165c.getWritableDatabase();
            if (writableDatabase != null && contentValues != null) {
                long jReplace = writableDatabase.replace(str, com.umeng.analytics.pro.bg.f21483d, contentValues);
                if (jReplace >= 0) {
                    al.c("[Database] insert %s success.", str);
                } else {
                    al.d("[Database] replace %s error.", str);
                }
                j2 = jReplace;
            }
        } catch (Throwable th) {
            try {
                if (!al.a(th)) {
                    th.printStackTrace();
                }
                if (f21163a && 0 != 0) {
                }
            } finally {
                if (f21163a && 0 != 0) {
                    writableDatabase.close();
                }
            }
        }
        return j2;
    }

    public final synchronized void b(int i2) {
        String strConcat;
        SQLiteDatabase writableDatabase = f21165c.getWritableDatabase();
        if (writableDatabase != null) {
            if (i2 >= 0) {
                try {
                    strConcat = "_tp = ".concat(String.valueOf(i2));
                } catch (Throwable th) {
                    try {
                        if (!al.a(th)) {
                            th.printStackTrace();
                        }
                        if (f21163a) {
                            writableDatabase.close();
                            return;
                        }
                    } finally {
                        if (f21163a) {
                            writableDatabase.close();
                        }
                    }
                }
            } else {
                strConcat = null;
            }
            al.c("[Database] deleted %s data %d", "t_lr", Integer.valueOf(writableDatabase.delete("t_lr", strConcat, null)));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized Cursor a(boolean z2, String str, String[] strArr, String str2, String[] strArr2, String str3, String str4, String str5, String str6, v vVar) {
        Cursor cursorQuery;
        cursorQuery = null;
        try {
            SQLiteDatabase writableDatabase = f21165c.getWritableDatabase();
            if (writableDatabase != null) {
                cursorQuery = writableDatabase.query(z2, str, strArr, str2, strArr2, str3, str4, str5, str6);
            }
        } finally {
            try {
                return cursorQuery;
            } finally {
            }
        }
        return cursorQuery;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized int a(String str, String str2, String[] strArr, v vVar) {
        int iDelete;
        SQLiteDatabase writableDatabase = null;
        try {
            writableDatabase = f21165c.getWritableDatabase();
            iDelete = writableDatabase != null ? writableDatabase.delete(str, str2, strArr) : 0;
        } catch (Throwable th) {
            try {
                if (!al.a(th)) {
                    th.printStackTrace();
                }
                if (f21163a && writableDatabase != null) {
                }
            } finally {
                if (f21163a && writableDatabase != null) {
                    writableDatabase.close();
                }
            }
        }
        return iDelete;
    }

    private static y b(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        try {
            y yVar = new y();
            yVar.f21188a = cursor.getLong(cursor.getColumnIndex(com.umeng.analytics.pro.bg.f21483d));
            yVar.f21192e = cursor.getLong(cursor.getColumnIndex("_tm"));
            yVar.f21193f = cursor.getString(cursor.getColumnIndex(com.umeng.analytics.pro.bg.f21484e));
            yVar.f21194g = cursor.getBlob(cursor.getColumnIndex("_dt"));
            return yVar;
        } catch (Throwable th) {
            if (!al.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    public final boolean a(int i2, String str, byte[] bArr, boolean z2) {
        if (!z2) {
            a aVar = new a();
            aVar.a(i2, str, bArr);
            ak.a().a(aVar);
            return true;
        }
        return a(i2, str, bArr, (v) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(int i2, String str, byte[] bArr, v vVar) {
        try {
            y yVar = new y();
            yVar.f21188a = i2;
            yVar.f21193f = str;
            yVar.f21192e = System.currentTimeMillis();
            yVar.f21194g = bArr;
            return b(yVar);
        } catch (Throwable th) {
            if (!al.a(th)) {
                th.printStackTrace();
            }
            return false;
        }
    }

    public final Map<String, byte[]> a(int i2, v vVar) {
        HashMap map = null;
        try {
            List<y> listC = c(i2);
            if (listC == null) {
                return null;
            }
            HashMap map2 = new HashMap();
            try {
                for (y yVar : listC) {
                    byte[] bArr = yVar.f21194g;
                    if (bArr != null) {
                        map2.put(yVar.f21193f, bArr);
                    }
                }
                return map2;
            } catch (Throwable th) {
                th = th;
                map = map2;
                if (al.a(th)) {
                    return map;
                }
                th.printStackTrace();
                return map;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public final synchronized boolean a(y yVar) {
        ContentValues contentValuesC;
        SQLiteDatabase writableDatabase = null;
        try {
            writableDatabase = f21165c.getWritableDatabase();
            if (writableDatabase == null || (contentValuesC = c(yVar)) == null) {
                return false;
            }
            long jReplace = writableDatabase.replace("t_lr", com.umeng.analytics.pro.bg.f21483d, contentValuesC);
            if (jReplace >= 0) {
                al.c("[Database] insert %s success.", "t_lr");
                yVar.f21188a = jReplace;
                if (f21163a) {
                    writableDatabase.close();
                }
                return true;
            }
            if (f21163a) {
                writableDatabase.close();
            }
            return false;
        } catch (Throwable th) {
            try {
                if (!al.a(th)) {
                    th.printStackTrace();
                }
                if (f21163a && writableDatabase != null) {
                    writableDatabase.close();
                }
                return false;
            } finally {
                if (f21163a && writableDatabase != null) {
                    writableDatabase.close();
                }
            }
        }
    }

    /* JADX WARN: Finally extract failed */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00b2 A[Catch: all -> 0x00b6, TRY_LEAVE, TryCatch #2 {all -> 0x00b6, blocks: (B:43:0x00ac, B:45:0x00b2), top: B:67:0x00ac, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00ba A[Catch: all -> 0x0032, TRY_ENTER, TryCatch #1 {all -> 0x0032, blocks: (B:4:0x0002, B:15:0x002e, B:18:0x0035, B:20:0x0039, B:38:0x00a0, B:40:0x00a7, B:50:0x00ba, B:51:0x00bd, B:53:0x00c1, B:55:0x00c7, B:56:0x00ca, B:58:0x00ce, B:59:0x00d1, B:43:0x00ac, B:45:0x00b2), top: B:66:0x0002, inners: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00c1 A[Catch: all -> 0x0032, TryCatch #1 {all -> 0x0032, blocks: (B:4:0x0002, B:15:0x002e, B:18:0x0035, B:20:0x0039, B:38:0x00a0, B:40:0x00a7, B:50:0x00ba, B:51:0x00bd, B:53:0x00c1, B:55:0x00c7, B:56:0x00ca, B:58:0x00ce, B:59:0x00d1, B:43:0x00ac, B:45:0x00b2), top: B:66:0x0002, inners: #2 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final synchronized java.util.List<com.tencent.bugly.proguard.y> a(int r13) {
        /*
            Method dump skipped, instructions count: 214
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.w.a(int):java.util.List");
    }

    public final synchronized void a(List<y> list) {
        if (list != null) {
            try {
                if (list.size() != 0) {
                    SQLiteDatabase writableDatabase = f21165c.getWritableDatabase();
                    if (writableDatabase != null) {
                        StringBuilder sb = new StringBuilder();
                        for (y yVar : list) {
                            sb.append(" or _id = ");
                            sb.append(yVar.f21188a);
                        }
                        String string = sb.toString();
                        if (string.length() > 0) {
                            string = string.substring(4);
                        }
                        sb.setLength(0);
                        try {
                            al.c("[Database] deleted %s data %d", "t_lr", Integer.valueOf(writableDatabase.delete("t_lr", string, null)));
                        } catch (Throwable th) {
                            try {
                                if (!al.a(th)) {
                                    th.printStackTrace();
                                }
                                if (f21163a) {
                                    writableDatabase.close();
                                }
                            } finally {
                                if (f21163a) {
                                    writableDatabase.close();
                                }
                            }
                        }
                    }
                }
            } finally {
            }
        }
    }

    private static y a(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        try {
            y yVar = new y();
            yVar.f21188a = cursor.getLong(cursor.getColumnIndex(com.umeng.analytics.pro.bg.f21483d));
            yVar.f21189b = cursor.getInt(cursor.getColumnIndex(com.umeng.analytics.pro.bg.f21484e));
            yVar.f21190c = cursor.getString(cursor.getColumnIndex("_pc"));
            yVar.f21191d = cursor.getString(cursor.getColumnIndex("_th"));
            yVar.f21192e = cursor.getLong(cursor.getColumnIndex("_tm"));
            yVar.f21194g = cursor.getBlob(cursor.getColumnIndex("_dt"));
            return yVar;
        } catch (Throwable th) {
            if (!al.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized boolean a(int i2, String str, v vVar) {
        String strConcat;
        boolean z2 = false;
        synchronized (this) {
            SQLiteDatabase sQLiteDatabase = null;
            try {
                SQLiteDatabase writableDatabase = f21165c.getWritableDatabase();
                if (writableDatabase != null) {
                    try {
                        if (ap.b(str)) {
                            strConcat = "_id = ".concat(String.valueOf(i2));
                        } else {
                            strConcat = "_id = " + i2 + " and _tp = \"" + str + "\"";
                        }
                        int iDelete = writableDatabase.delete("t_pf", strConcat, null);
                        al.c("[Database] deleted %s data %d", "t_pf", Integer.valueOf(iDelete));
                        z2 = iDelete > 0;
                    } catch (Throwable th) {
                        th = th;
                        sQLiteDatabase = writableDatabase;
                        try {
                            if (!al.a(th)) {
                                th.printStackTrace();
                            }
                            return z2;
                        } finally {
                            if (f21163a && sQLiteDatabase != null) {
                                sQLiteDatabase.close();
                            }
                        }
                    }
                }
                if (f21163a && writableDatabase != null) {
                    writableDatabase.close();
                }
            } catch (Throwable th2) {
                th = th2;
            }
        }
        return z2;
    }
}
