package com.umeng.analytics.pro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;
import com.umeng.analytics.pro.g;
import com.umeng.commonsdk.debug.UMRTLog;
import com.umeng.commonsdk.service.UMGlobalContext;
import com.umeng.commonsdk.statistics.common.DataHelper;
import com.umeng.commonsdk.statistics.internal.PreferenceWrapper;
import com.umeng.commonsdk.utils.UMUtils;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class k {

    /* renamed from: a, reason: collision with root package name */
    public static final int f21780a = 2049;

    /* renamed from: b, reason: collision with root package name */
    public static final int f21781b = 2050;

    /* renamed from: c, reason: collision with root package name */
    private static final int f21782c = 1000;

    /* renamed from: d, reason: collision with root package name */
    private static Context f21783d = null;

    /* renamed from: e, reason: collision with root package name */
    private static String f21784e = null;

    /* renamed from: f, reason: collision with root package name */
    private static final String f21785f = "umeng+";

    /* renamed from: g, reason: collision with root package name */
    private static final String f21786g = "ek__id";

    /* renamed from: h, reason: collision with root package name */
    private static final String f21787h = "ek_key";

    /* renamed from: i, reason: collision with root package name */
    private List<String> f21788i;

    /* renamed from: j, reason: collision with root package name */
    private List<Integer> f21789j;

    /* renamed from: k, reason: collision with root package name */
    private String f21790k;

    /* renamed from: l, reason: collision with root package name */
    private List<String> f21791l;

    public enum a {
        AUTOPAGE,
        PAGE,
        BEGIN,
        END,
        NEWSESSION,
        INSTANTSESSIONBEGIN
    }

    private static class b {

        /* renamed from: a, reason: collision with root package name */
        private static final k f21799a = new k();

        private b() {
        }
    }

    public static k a(Context context) {
        k kVar = b.f21799a;
        if (f21783d == null && context != null) {
            f21783d = context.getApplicationContext();
            kVar.k();
        }
        return kVar;
    }

    private void k() {
        synchronized (this) {
            l();
            this.f21788i.clear();
            this.f21791l.clear();
            this.f21789j.clear();
        }
    }

    private void l() {
        try {
            if (TextUtils.isEmpty(f21784e)) {
                String multiProcessSP = UMUtils.getMultiProcessSP(f21783d, f21786g);
                if (TextUtils.isEmpty(multiProcessSP)) {
                    multiProcessSP = PreferenceWrapper.getDefault(f21783d).getString(f21786g, null);
                    if (TextUtils.isEmpty(multiProcessSP)) {
                        multiProcessSP = UMUtils.genId();
                    }
                    if (!TextUtils.isEmpty(multiProcessSP)) {
                        UMUtils.setMultiProcessSP(f21783d, f21786g, multiProcessSP);
                    }
                }
                if (!TextUtils.isEmpty(multiProcessSP)) {
                    String strSubstring = multiProcessSP.substring(1, 9);
                    StringBuilder sb = new StringBuilder();
                    for (int i2 = 0; i2 < strSubstring.length(); i2++) {
                        char cCharAt = strSubstring.charAt(i2);
                        if (!Character.isDigit(cCharAt)) {
                            sb.append(cCharAt);
                        } else if (Integer.parseInt(Character.toString(cCharAt)) == 0) {
                            sb.append(0);
                        } else {
                            sb.append(10 - Integer.parseInt(Character.toString(cCharAt)));
                        }
                    }
                    f21784e = sb.toString();
                }
                if (TextUtils.isEmpty(f21784e)) {
                    return;
                }
                f21784e += new StringBuilder(f21784e).reverse().toString();
                String multiProcessSP2 = UMUtils.getMultiProcessSP(f21783d, f21787h);
                if (TextUtils.isEmpty(multiProcessSP2)) {
                    UMUtils.setMultiProcessSP(f21783d, f21787h, c(f21785f));
                } else {
                    if (f21785f.equals(d(multiProcessSP2))) {
                        return;
                    }
                    b(true, false);
                    a(true, false);
                    h();
                    i();
                }
            }
        } catch (Throwable unused) {
        }
    }

    public void b() {
        this.f21791l.clear();
    }

    public boolean c() {
        return this.f21791l.isEmpty();
    }

    public void d() {
        String strC;
        SQLiteDatabase sQLiteDatabaseA = null;
        try {
            try {
                sQLiteDatabaseA = i.a(f21783d).a();
                sQLiteDatabaseA.beginTransaction();
                strC = w.a().c();
            } catch (SQLiteDatabaseCorruptException unused) {
                j.a(f21783d);
                if (sQLiteDatabaseA != null) {
                }
            } catch (Throwable unused2) {
            }
            if (TextUtils.isEmpty(strC)) {
                try {
                    sQLiteDatabaseA.endTransaction();
                } catch (Throwable unused3) {
                }
                i.a(f21783d).b();
                return;
            }
            String[] strArr = {"", "-1"};
            for (int i2 = 0; i2 < 2; i2++) {
                sQLiteDatabaseA.execSQL("update __et set __i=\"" + strC + "\" where __i=\"" + strArr[i2] + "\"");
            }
            sQLiteDatabaseA.setTransactionSuccessful();
        } finally {
            if (sQLiteDatabaseA != null) {
                try {
                    sQLiteDatabaseA.endTransaction();
                } catch (Throwable unused4) {
                }
            }
            i.a(f21783d).b();
        }
    }

    public boolean e() {
        return this.f21788i.isEmpty();
    }

    /* JADX WARN: Finally extract failed */
    /* JADX WARN: Removed duplicated region for block: B:53:0x008a A[EXC_TOP_SPLITTER, PHI: r2 r5
      0x008a: PHI (r2v5 android.database.sqlite.SQLiteDatabase) = (r2v4 android.database.sqlite.SQLiteDatabase), (r2v6 android.database.sqlite.SQLiteDatabase) binds: [B:28:0x0088, B:34:0x00a2] A[DONT_GENERATE, DONT_INLINE]
      0x008a: PHI (r5v3 org.json.JSONObject) = (r5v2 org.json.JSONObject), (r5v5 org.json.JSONObject) binds: [B:28:0x0088, B:34:0x00a2] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public org.json.JSONObject f() {
        /*
            r14 = this;
            java.lang.String r0 = "__vc"
            java.lang.String r1 = "__av"
            java.util.List<java.lang.String> r2 = r14.f21791l
            boolean r2 = r2.isEmpty()
            r3 = 0
            if (r2 == 0) goto Le
            return r3
        Le:
            android.content.Context r2 = com.umeng.analytics.pro.k.f21783d     // Catch: java.lang.Throwable -> L7d android.database.sqlite.SQLiteDatabaseCorruptException -> L80
            com.umeng.analytics.pro.i r2 = com.umeng.analytics.pro.i.a(r2)     // Catch: java.lang.Throwable -> L7d android.database.sqlite.SQLiteDatabaseCorruptException -> L80
            android.database.sqlite.SQLiteDatabase r2 = r2.a()     // Catch: java.lang.Throwable -> L7d android.database.sqlite.SQLiteDatabaseCorruptException -> L80
            r2.beginTransaction()     // Catch: java.lang.Throwable -> L79 android.database.sqlite.SQLiteDatabaseCorruptException -> L7b
            java.util.List<java.lang.String> r4 = r14.f21791l     // Catch: java.lang.Throwable -> L79 android.database.sqlite.SQLiteDatabaseCorruptException -> L7b
            r5 = 0
            java.lang.Object r4 = r4.get(r5)     // Catch: java.lang.Throwable -> L79 android.database.sqlite.SQLiteDatabaseCorruptException -> L7b
            java.lang.String r4 = (java.lang.String) r4     // Catch: java.lang.Throwable -> L79 android.database.sqlite.SQLiteDatabaseCorruptException -> L7b
            java.lang.String r5 = "__is"
            java.lang.String r8 = "__ii=? "
            java.lang.String[] r9 = new java.lang.String[]{r4}     // Catch: java.lang.Throwable -> L79 android.database.sqlite.SQLiteDatabaseCorruptException -> L7b
            r12 = 0
            r13 = 0
            r7 = 0
            r10 = 0
            r11 = 0
            r4 = r14
            r6 = r2
            android.database.Cursor r4 = r4.a(r5, r6, r7, r8, r9, r10, r11, r12, r13)     // Catch: java.lang.Throwable -> L79 android.database.sqlite.SQLiteDatabaseCorruptException -> L7b
            if (r4 == 0) goto L64
            boolean r5 = r4.moveToNext()     // Catch: java.lang.Throwable -> L60 android.database.sqlite.SQLiteDatabaseCorruptException -> L62
            if (r5 == 0) goto L64
            org.json.JSONObject r5 = new org.json.JSONObject     // Catch: java.lang.Throwable -> L60 android.database.sqlite.SQLiteDatabaseCorruptException -> L62
            r5.<init>()     // Catch: java.lang.Throwable -> L60 android.database.sqlite.SQLiteDatabaseCorruptException -> L62
            int r3 = r4.getColumnIndex(r1)     // Catch: java.lang.Throwable -> L5c android.database.sqlite.SQLiteDatabaseCorruptException -> L5e
            java.lang.String r3 = r4.getString(r3)     // Catch: java.lang.Throwable -> L5c android.database.sqlite.SQLiteDatabaseCorruptException -> L5e
            int r6 = r4.getColumnIndex(r0)     // Catch: java.lang.Throwable -> L5c android.database.sqlite.SQLiteDatabaseCorruptException -> L5e
            java.lang.String r6 = r4.getString(r6)     // Catch: java.lang.Throwable -> L5c android.database.sqlite.SQLiteDatabaseCorruptException -> L5e
            r5.put(r1, r3)     // Catch: java.lang.Throwable -> L5c android.database.sqlite.SQLiteDatabaseCorruptException -> L5e
            r5.put(r0, r6)     // Catch: java.lang.Throwable -> L5c android.database.sqlite.SQLiteDatabaseCorruptException -> L5e
            r3 = r5
            goto L64
        L5c:
            r3 = r4
            goto L83
        L5e:
            r3 = r4
            goto L98
        L60:
            r5 = r3
            goto L5c
        L62:
            r5 = r3
            goto L5e
        L64:
            r2.setTransactionSuccessful()     // Catch: java.lang.Throwable -> L60 android.database.sqlite.SQLiteDatabaseCorruptException -> L62
            if (r4 == 0) goto L6c
            r4.close()
        L6c:
            r2.endTransaction()     // Catch: java.lang.Throwable -> L6f
        L6f:
            android.content.Context r0 = com.umeng.analytics.pro.k.f21783d
            com.umeng.analytics.pro.i r0 = com.umeng.analytics.pro.i.a(r0)
            r0.b()
            goto La5
        L79:
            r5 = r3
            goto L83
        L7b:
            r5 = r3
            goto L98
        L7d:
            r2 = r3
            r5 = r2
            goto L83
        L80:
            r2 = r3
            r5 = r2
            goto L98
        L83:
            if (r3 == 0) goto L88
            r3.close()
        L88:
            if (r2 == 0) goto L8d
        L8a:
            r2.endTransaction()     // Catch: java.lang.Throwable -> L8d
        L8d:
            android.content.Context r0 = com.umeng.analytics.pro.k.f21783d
            com.umeng.analytics.pro.i r0 = com.umeng.analytics.pro.i.a(r0)
            r0.b()
            r3 = r5
            goto La5
        L98:
            android.content.Context r0 = com.umeng.analytics.pro.k.f21783d     // Catch: java.lang.Throwable -> La6
            com.umeng.analytics.pro.j.a(r0)     // Catch: java.lang.Throwable -> La6
            if (r3 == 0) goto La2
            r3.close()
        La2:
            if (r2 == 0) goto L8d
            goto L8a
        La5:
            return r3
        La6:
            r0 = move-exception
            if (r3 == 0) goto Lac
            r3.close()
        Lac:
            if (r2 == 0) goto Lb1
            r2.endTransaction()     // Catch: java.lang.Throwable -> Lb1
        Lb1:
            android.content.Context r1 = com.umeng.analytics.pro.k.f21783d
            com.umeng.analytics.pro.i r1 = com.umeng.analytics.pro.i.a(r1)
            r1.b()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.k.f():org.json.JSONObject");
    }

    /* JADX WARN: Finally extract failed */
    /* JADX WARN: Removed duplicated region for block: B:53:0x008a A[EXC_TOP_SPLITTER, PHI: r2 r5
      0x008a: PHI (r2v5 android.database.sqlite.SQLiteDatabase) = (r2v4 android.database.sqlite.SQLiteDatabase), (r2v6 android.database.sqlite.SQLiteDatabase) binds: [B:28:0x0088, B:34:0x00a2] A[DONT_GENERATE, DONT_INLINE]
      0x008a: PHI (r5v3 org.json.JSONObject) = (r5v2 org.json.JSONObject), (r5v5 org.json.JSONObject) binds: [B:28:0x0088, B:34:0x00a2] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public org.json.JSONObject g() {
        /*
            r14 = this;
            java.lang.String r0 = "__vc"
            java.lang.String r1 = "__av"
            java.util.List<java.lang.String> r2 = r14.f21788i
            boolean r2 = r2.isEmpty()
            r3 = 0
            if (r2 == 0) goto Le
            return r3
        Le:
            android.content.Context r2 = com.umeng.analytics.pro.k.f21783d     // Catch: java.lang.Throwable -> L7d android.database.sqlite.SQLiteDatabaseCorruptException -> L80
            com.umeng.analytics.pro.i r2 = com.umeng.analytics.pro.i.a(r2)     // Catch: java.lang.Throwable -> L7d android.database.sqlite.SQLiteDatabaseCorruptException -> L80
            android.database.sqlite.SQLiteDatabase r2 = r2.a()     // Catch: java.lang.Throwable -> L7d android.database.sqlite.SQLiteDatabaseCorruptException -> L80
            r2.beginTransaction()     // Catch: java.lang.Throwable -> L79 android.database.sqlite.SQLiteDatabaseCorruptException -> L7b
            java.util.List<java.lang.String> r4 = r14.f21788i     // Catch: java.lang.Throwable -> L79 android.database.sqlite.SQLiteDatabaseCorruptException -> L7b
            r5 = 0
            java.lang.Object r4 = r4.get(r5)     // Catch: java.lang.Throwable -> L79 android.database.sqlite.SQLiteDatabaseCorruptException -> L7b
            java.lang.String r4 = (java.lang.String) r4     // Catch: java.lang.Throwable -> L79 android.database.sqlite.SQLiteDatabaseCorruptException -> L7b
            java.lang.String r5 = "__sd"
            java.lang.String r8 = "__ii=? "
            java.lang.String[] r9 = new java.lang.String[]{r4}     // Catch: java.lang.Throwable -> L79 android.database.sqlite.SQLiteDatabaseCorruptException -> L7b
            r12 = 0
            r13 = 0
            r7 = 0
            r10 = 0
            r11 = 0
            r4 = r14
            r6 = r2
            android.database.Cursor r4 = r4.a(r5, r6, r7, r8, r9, r10, r11, r12, r13)     // Catch: java.lang.Throwable -> L79 android.database.sqlite.SQLiteDatabaseCorruptException -> L7b
            if (r4 == 0) goto L64
            boolean r5 = r4.moveToNext()     // Catch: java.lang.Throwable -> L60 android.database.sqlite.SQLiteDatabaseCorruptException -> L62
            if (r5 == 0) goto L64
            org.json.JSONObject r5 = new org.json.JSONObject     // Catch: java.lang.Throwable -> L60 android.database.sqlite.SQLiteDatabaseCorruptException -> L62
            r5.<init>()     // Catch: java.lang.Throwable -> L60 android.database.sqlite.SQLiteDatabaseCorruptException -> L62
            int r3 = r4.getColumnIndex(r1)     // Catch: java.lang.Throwable -> L5c android.database.sqlite.SQLiteDatabaseCorruptException -> L5e
            java.lang.String r3 = r4.getString(r3)     // Catch: java.lang.Throwable -> L5c android.database.sqlite.SQLiteDatabaseCorruptException -> L5e
            int r6 = r4.getColumnIndex(r0)     // Catch: java.lang.Throwable -> L5c android.database.sqlite.SQLiteDatabaseCorruptException -> L5e
            java.lang.String r6 = r4.getString(r6)     // Catch: java.lang.Throwable -> L5c android.database.sqlite.SQLiteDatabaseCorruptException -> L5e
            r5.put(r1, r3)     // Catch: java.lang.Throwable -> L5c android.database.sqlite.SQLiteDatabaseCorruptException -> L5e
            r5.put(r0, r6)     // Catch: java.lang.Throwable -> L5c android.database.sqlite.SQLiteDatabaseCorruptException -> L5e
            r3 = r5
            goto L64
        L5c:
            r3 = r4
            goto L83
        L5e:
            r3 = r4
            goto L98
        L60:
            r5 = r3
            goto L5c
        L62:
            r5 = r3
            goto L5e
        L64:
            r2.setTransactionSuccessful()     // Catch: java.lang.Throwable -> L60 android.database.sqlite.SQLiteDatabaseCorruptException -> L62
            if (r4 == 0) goto L6c
            r4.close()
        L6c:
            r2.endTransaction()     // Catch: java.lang.Throwable -> L6f
        L6f:
            android.content.Context r0 = com.umeng.analytics.pro.k.f21783d
            com.umeng.analytics.pro.i r0 = com.umeng.analytics.pro.i.a(r0)
            r0.b()
            goto La5
        L79:
            r5 = r3
            goto L83
        L7b:
            r5 = r3
            goto L98
        L7d:
            r2 = r3
            r5 = r2
            goto L83
        L80:
            r2 = r3
            r5 = r2
            goto L98
        L83:
            if (r3 == 0) goto L88
            r3.close()
        L88:
            if (r2 == 0) goto L8d
        L8a:
            r2.endTransaction()     // Catch: java.lang.Throwable -> L8d
        L8d:
            android.content.Context r0 = com.umeng.analytics.pro.k.f21783d
            com.umeng.analytics.pro.i r0 = com.umeng.analytics.pro.i.a(r0)
            r0.b()
            r3 = r5
            goto La5
        L98:
            android.content.Context r0 = com.umeng.analytics.pro.k.f21783d     // Catch: java.lang.Throwable -> La6
            com.umeng.analytics.pro.j.a(r0)     // Catch: java.lang.Throwable -> La6
            if (r3 == 0) goto La2
            r3.close()
        La2:
            if (r2 == 0) goto L8d
            goto L8a
        La5:
            return r3
        La6:
            r0 = move-exception
            if (r3 == 0) goto Lac
            r3.close()
        Lac:
            if (r2 == 0) goto Lb1
            r2.endTransaction()     // Catch: java.lang.Throwable -> Lb1
        Lb1:
            android.content.Context r1 = com.umeng.analytics.pro.k.f21783d
            com.umeng.analytics.pro.i r1 = com.umeng.analytics.pro.i.a(r1)
            r1.b()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.k.g():org.json.JSONObject");
    }

    public void h() {
        SQLiteDatabase sQLiteDatabaseA = null;
        try {
            try {
                sQLiteDatabaseA = i.a(f21783d).a();
                sQLiteDatabaseA.beginTransaction();
                if (this.f21789j.size() > 0) {
                    for (int i2 = 0; i2 < this.f21789j.size(); i2++) {
                        sQLiteDatabaseA.execSQL("delete from __et where rowid=" + this.f21789j.get(i2));
                    }
                }
                this.f21789j.clear();
                sQLiteDatabaseA.setTransactionSuccessful();
            } catch (SQLiteDatabaseCorruptException unused) {
                j.a(f21783d);
                if (sQLiteDatabaseA != null) {
                }
            } catch (Throwable unused2) {
            }
        } finally {
            if (sQLiteDatabaseA != null) {
                try {
                    sQLiteDatabaseA.endTransaction();
                } catch (Throwable unused3) {
                }
            }
            i.a(f21783d).b();
        }
    }

    public void i() {
        SQLiteDatabase sQLiteDatabaseA = null;
        try {
            try {
                sQLiteDatabaseA = i.a(f21783d).a();
                sQLiteDatabaseA.beginTransaction();
                sQLiteDatabaseA.execSQL("delete from __er");
                sQLiteDatabaseA.setTransactionSuccessful();
            } catch (SQLiteDatabaseCorruptException unused) {
                j.a(f21783d);
                if (sQLiteDatabaseA != null) {
                }
            } catch (Throwable unused2) {
            }
        } finally {
            if (sQLiteDatabaseA != null) {
                try {
                    sQLiteDatabaseA.endTransaction();
                } catch (Throwable unused3) {
                }
            }
            i.a(f21783d).b();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x004d A[EXC_TOP_SPLITTER, PHI: r1
      0x004d: PHI (r1v8 android.database.sqlite.SQLiteDatabase) = 
      (r1v4 android.database.sqlite.SQLiteDatabase)
      (r1v5 android.database.sqlite.SQLiteDatabase)
      (r1v11 android.database.sqlite.SQLiteDatabase)
     binds: [B:10:0x005e, B:13:0x0066, B:5:0x0015] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void j() {
        /*
            r5 = this;
            java.lang.String r0 = "\""
            java.lang.String r1 = r5.f21790k
            boolean r1 = android.text.TextUtils.isEmpty(r1)
            r2 = 0
            if (r1 != 0) goto L79
            android.content.Context r1 = com.umeng.analytics.pro.k.f21783d     // Catch: java.lang.Throwable -> L5a android.database.sqlite.SQLiteDatabaseCorruptException -> L5c
            com.umeng.analytics.pro.i r1 = com.umeng.analytics.pro.i.a(r1)     // Catch: java.lang.Throwable -> L5a android.database.sqlite.SQLiteDatabaseCorruptException -> L5c
            android.database.sqlite.SQLiteDatabase r1 = r1.a()     // Catch: java.lang.Throwable -> L5a android.database.sqlite.SQLiteDatabaseCorruptException -> L5c
            r1.beginTransaction()     // Catch: java.lang.Throwable -> L5e android.database.sqlite.SQLiteDatabaseCorruptException -> L61
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L5e android.database.sqlite.SQLiteDatabaseCorruptException -> L61
            r3.<init>()     // Catch: java.lang.Throwable -> L5e android.database.sqlite.SQLiteDatabaseCorruptException -> L61
            java.lang.String r4 = "delete from __er where __i=\""
            r3.append(r4)     // Catch: java.lang.Throwable -> L5e android.database.sqlite.SQLiteDatabaseCorruptException -> L61
            java.lang.String r4 = r5.f21790k     // Catch: java.lang.Throwable -> L5e android.database.sqlite.SQLiteDatabaseCorruptException -> L61
            r3.append(r4)     // Catch: java.lang.Throwable -> L5e android.database.sqlite.SQLiteDatabaseCorruptException -> L61
            r3.append(r0)     // Catch: java.lang.Throwable -> L5e android.database.sqlite.SQLiteDatabaseCorruptException -> L61
            java.lang.String r3 = r3.toString()     // Catch: java.lang.Throwable -> L5e android.database.sqlite.SQLiteDatabaseCorruptException -> L61
            r1.execSQL(r3)     // Catch: java.lang.Throwable -> L5e android.database.sqlite.SQLiteDatabaseCorruptException -> L61
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L5e android.database.sqlite.SQLiteDatabaseCorruptException -> L61
            r3.<init>()     // Catch: java.lang.Throwable -> L5e android.database.sqlite.SQLiteDatabaseCorruptException -> L61
            java.lang.String r4 = "delete from __et where __i=\""
            r3.append(r4)     // Catch: java.lang.Throwable -> L5e android.database.sqlite.SQLiteDatabaseCorruptException -> L61
            java.lang.String r4 = r5.f21790k     // Catch: java.lang.Throwable -> L5e android.database.sqlite.SQLiteDatabaseCorruptException -> L61
            r3.append(r4)     // Catch: java.lang.Throwable -> L5e android.database.sqlite.SQLiteDatabaseCorruptException -> L61
            r3.append(r0)     // Catch: java.lang.Throwable -> L5e android.database.sqlite.SQLiteDatabaseCorruptException -> L61
            java.lang.String r0 = r3.toString()     // Catch: java.lang.Throwable -> L5e android.database.sqlite.SQLiteDatabaseCorruptException -> L61
            r1.execSQL(r0)     // Catch: java.lang.Throwable -> L5e android.database.sqlite.SQLiteDatabaseCorruptException -> L61
            r1.setTransactionSuccessful()     // Catch: java.lang.Throwable -> L5e android.database.sqlite.SQLiteDatabaseCorruptException -> L61
        L4d:
            r1.endTransaction()     // Catch: java.lang.Throwable -> L50
        L50:
            android.content.Context r0 = com.umeng.analytics.pro.k.f21783d
            com.umeng.analytics.pro.i r0 = com.umeng.analytics.pro.i.a(r0)
            r0.b()
            goto L79
        L5a:
            r1 = r2
            goto L5e
        L5c:
            r1 = r2
            goto L61
        L5e:
            if (r1 == 0) goto L50
            goto L4d
        L61:
            android.content.Context r0 = com.umeng.analytics.pro.k.f21783d     // Catch: java.lang.Throwable -> L69
            com.umeng.analytics.pro.j.a(r0)     // Catch: java.lang.Throwable -> L69
            if (r1 == 0) goto L50
            goto L4d
        L69:
            r0 = move-exception
            if (r1 == 0) goto L6f
            r1.endTransaction()     // Catch: java.lang.Throwable -> L6f
        L6f:
            android.content.Context r1 = com.umeng.analytics.pro.k.f21783d
            com.umeng.analytics.pro.i r1 = com.umeng.analytics.pro.i.a(r1)
            r1.b()
            throw r0
        L79:
            r5.f21790k = r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.k.j():void");
    }

    private k() {
        this.f21788i = new ArrayList();
        this.f21789j = new ArrayList();
        this.f21790k = null;
        this.f21791l = new ArrayList();
    }

    private void b(String str, JSONObject jSONObject, SQLiteDatabase sQLiteDatabase) {
        try {
            long jLongValue = ((Long) jSONObject.get("__e")).longValue();
            JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("__sp");
            JSONObject jSONObjectOptJSONObject2 = jSONObject.optJSONObject("__pp");
            String strC = "";
            String strC2 = (jSONObjectOptJSONObject == null || jSONObjectOptJSONObject.length() <= 0) ? "" : c(jSONObjectOptJSONObject.toString());
            if (jSONObjectOptJSONObject2 != null && jSONObjectOptJSONObject2.length() > 0) {
                strC = c(jSONObjectOptJSONObject2.toString());
            }
            ContentValues contentValues = new ContentValues();
            contentValues.put("__ii", str);
            contentValues.put("__e", String.valueOf(jLongValue));
            contentValues.put("__sp", strC2);
            contentValues.put("__pp", strC);
            contentValues.put("__av", UMGlobalContext.getInstance(f21783d).getAppVersion());
            contentValues.put("__vc", UMUtils.getAppVersionCode(f21783d));
            sQLiteDatabase.insert(g.c.f21734a, null, contentValues);
        } catch (Throwable unused) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x0119  */
    /* JADX WARN: Removed duplicated region for block: B:46:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void c(java.lang.String r20, org.json.JSONObject r21, android.database.sqlite.SQLiteDatabase r22) {
        /*
            Method dump skipped, instructions count: 285
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.k.c(java.lang.String, org.json.JSONObject, android.database.sqlite.SQLiteDatabase):void");
    }

    private Cursor a(String str, SQLiteDatabase sQLiteDatabase, String[] strArr, String str2, String[] strArr2, String str3, String str4, String str5, String str6) {
        if (sQLiteDatabase == null) {
            return null;
        }
        try {
            if (sQLiteDatabase.isOpen()) {
                return sQLiteDatabase.query(str, strArr, str2, strArr2, str3, str4, str5, str6);
            }
            return null;
        } catch (Throwable unused) {
            return null;
        }
    }

    public void a() {
        this.f21788i.clear();
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x00a2, code lost:
    
        if (r2 == null) goto L21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x00a4, code lost:
    
        r2.endTransaction();
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x00ad, code lost:
    
        if (r2 == null) goto L21;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void a(org.json.JSONArray r11) {
        /*
            r10 = this;
            java.lang.String r0 = "__t"
            java.lang.String r1 = "__i"
            r2 = 0
            android.content.Context r3 = com.umeng.analytics.pro.k.f21783d     // Catch: java.lang.Throwable -> La2 android.database.sqlite.SQLiteDatabaseCorruptException -> La8
            com.umeng.analytics.pro.i r3 = com.umeng.analytics.pro.i.a(r3)     // Catch: java.lang.Throwable -> La2 android.database.sqlite.SQLiteDatabaseCorruptException -> La8
            android.database.sqlite.SQLiteDatabase r3 = r3.a()     // Catch: java.lang.Throwable -> La2 android.database.sqlite.SQLiteDatabaseCorruptException -> La8
            r3.beginTransaction()     // Catch: java.lang.Throwable -> L35 android.database.sqlite.SQLiteDatabaseCorruptException -> La0
            r4 = 0
        L13:
            int r5 = r11.length()     // Catch: java.lang.Throwable -> L35 android.database.sqlite.SQLiteDatabaseCorruptException -> La0
            if (r4 >= r5) goto L90
            org.json.JSONObject r5 = r11.getJSONObject(r4)     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L8d
            android.content.ContentValues r6 = new android.content.ContentValues     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L8d
            r6.<init>()     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L8d
            java.lang.String r7 = r5.optString(r1)     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L8d
            boolean r8 = android.text.TextUtils.isEmpty(r7)     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L8d
            java.lang.String r9 = "-1"
            if (r8 != 0) goto L37
            boolean r8 = r9.equals(r7)     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L8d
            if (r8 == 0) goto L46
            goto L37
        L35:
            r2 = r3
            goto La2
        L37:
            com.umeng.analytics.pro.w r7 = com.umeng.analytics.pro.w.a()     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L8d
            java.lang.String r7 = r7.b()     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L8d
            boolean r8 = android.text.TextUtils.isEmpty(r7)     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L8d
            if (r8 == 0) goto L46
            r7 = r9
        L46:
            r6.put(r1, r7)     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L8d
            java.lang.String r7 = "__e"
            java.lang.String r8 = "id"
            java.lang.String r8 = r5.optString(r8)     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L8d
            r6.put(r7, r8)     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L8d
            int r7 = r5.optInt(r0)     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L8d
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L8d
            r6.put(r0, r7)     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L8d
            java.lang.String r7 = "__av"
            android.content.Context r8 = com.umeng.analytics.pro.k.f21783d     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L8d
            java.lang.String r8 = com.umeng.commonsdk.utils.UMUtils.getAppVersionName(r8)     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L8d
            r6.put(r7, r8)     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L8d
            java.lang.String r7 = "__vc"
            android.content.Context r8 = com.umeng.analytics.pro.k.f21783d     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L8d
            java.lang.String r8 = com.umeng.commonsdk.utils.UMUtils.getAppVersionCode(r8)     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L8d
            r6.put(r7, r8)     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L8d
            r5.remove(r1)     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L8d
            r5.remove(r0)     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L8d
            java.lang.String r7 = "__s"
            java.lang.String r5 = r5.toString()     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L8d
            java.lang.String r5 = r10.c(r5)     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L8d
            r6.put(r7, r5)     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L8d
            java.lang.String r5 = "__et"
            r3.insert(r5, r2, r6)     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L8d
        L8d:
            int r4 = r4 + 1
            goto L13
        L90:
            r3.setTransactionSuccessful()     // Catch: java.lang.Throwable -> L35 android.database.sqlite.SQLiteDatabaseCorruptException -> La0
            r3.endTransaction()     // Catch: java.lang.Throwable -> L96
        L96:
            android.content.Context r11 = com.umeng.analytics.pro.k.f21783d
            com.umeng.analytics.pro.i r11 = com.umeng.analytics.pro.i.a(r11)
            r11.b()
            goto Lb0
        La0:
            r2 = r3
            goto La8
        La2:
            if (r2 == 0) goto L96
        La4:
            r2.endTransaction()     // Catch: java.lang.Throwable -> L96
            goto L96
        La8:
            android.content.Context r11 = com.umeng.analytics.pro.k.f21783d     // Catch: java.lang.Throwable -> Lb1
            com.umeng.analytics.pro.j.a(r11)     // Catch: java.lang.Throwable -> Lb1
            if (r2 == 0) goto L96
            goto La4
        Lb0:
            return
        Lb1:
            r11 = move-exception
            if (r2 == 0) goto Lb7
            r2.endTransaction()     // Catch: java.lang.Throwable -> Lb7
        Lb7:
            android.content.Context r0 = com.umeng.analytics.pro.k.f21783d
            com.umeng.analytics.pro.i r0 = com.umeng.analytics.pro.i.a(r0)
            r0.b()
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.k.a(org.json.JSONArray):void");
    }

    public JSONObject b(boolean z2) {
        JSONObject jSONObject = new JSONObject();
        b(jSONObject, z2);
        return jSONObject;
    }

    public String d(String str) {
        try {
            return TextUtils.isEmpty(f21784e) ? str : new String(DataHelper.decrypt(Base64.decode(str.getBytes(), 0), f21784e.getBytes()));
        } catch (Exception unused) {
            if (Build.VERSION.SDK_INT >= 29 && !TextUtils.isEmpty(str)) {
                try {
                    new JSONObject(str);
                    UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> UMStoreManager decrypt failed, return origin data.");
                    return str;
                } catch (Throwable unused2) {
                    return null;
                }
            }
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:49:0x0079 A[PHI: r0 r1
      0x0079: PHI (r0v4 android.database.Cursor) = (r0v2 android.database.Cursor), (r0v3 android.database.Cursor), (r0v5 android.database.Cursor) binds: [B:29:0x0091, B:36:0x00a0, B:22:0x0076] A[DONT_GENERATE, DONT_INLINE]
      0x0079: PHI (r1v5 android.database.sqlite.SQLiteDatabase) = 
      (r1v3 android.database.sqlite.SQLiteDatabase)
      (r1v4 android.database.sqlite.SQLiteDatabase)
      (r1v6 android.database.sqlite.SQLiteDatabase)
     binds: [B:29:0x0091, B:36:0x00a0, B:22:0x0076] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0076 A[EXC_TOP_SPLITTER, PHI: r0 r1
      0x0076: PHI (r0v5 android.database.Cursor) = 
      (r0v2 android.database.Cursor)
      (r0v3 android.database.Cursor)
      (r0v8 android.database.Cursor)
      (r0v8 android.database.Cursor)
     binds: [B:29:0x0091, B:36:0x00a0, B:20:0x0071, B:21:0x0073] A[DONT_GENERATE, DONT_INLINE]
      0x0076: PHI (r1v6 android.database.sqlite.SQLiteDatabase) = 
      (r1v3 android.database.sqlite.SQLiteDatabase)
      (r1v4 android.database.sqlite.SQLiteDatabase)
      (r1v9 android.database.sqlite.SQLiteDatabase)
      (r1v9 android.database.sqlite.SQLiteDatabase)
     binds: [B:29:0x0091, B:36:0x00a0, B:20:0x0071, B:21:0x0073] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void b(org.json.JSONObject r13, java.lang.String r14) {
        /*
            r12 = this;
            r0 = 0
            android.content.Context r1 = com.umeng.analytics.pro.k.f21783d     // Catch: java.lang.Throwable -> L83 android.database.sqlite.SQLiteDatabaseCorruptException -> L85
            com.umeng.analytics.pro.i r1 = com.umeng.analytics.pro.i.a(r1)     // Catch: java.lang.Throwable -> L83 android.database.sqlite.SQLiteDatabaseCorruptException -> L85
            android.database.sqlite.SQLiteDatabase r1 = r1.a()     // Catch: java.lang.Throwable -> L83 android.database.sqlite.SQLiteDatabaseCorruptException -> L85
            r1.beginTransaction()     // Catch: java.lang.Throwable -> L87 android.database.sqlite.SQLiteDatabaseCorruptException -> L96
            boolean r2 = android.text.TextUtils.isEmpty(r14)     // Catch: java.lang.Throwable -> L87 android.database.sqlite.SQLiteDatabaseCorruptException -> L96
            if (r2 != 0) goto L29
            java.lang.String r3 = "__er"
            java.lang.String r6 = "__i=? "
            java.lang.String[] r7 = new java.lang.String[]{r14}     // Catch: java.lang.Throwable -> L87 android.database.sqlite.SQLiteDatabaseCorruptException -> L96
            r10 = 0
            r11 = 0
            r5 = 0
            r8 = 0
            r9 = 0
            r2 = r12
            r4 = r1
            android.database.Cursor r14 = r2.a(r3, r4, r5, r6, r7, r8, r9, r10, r11)     // Catch: java.lang.Throwable -> L87 android.database.sqlite.SQLiteDatabaseCorruptException -> L96
        L27:
            r0 = r14
            goto L39
        L29:
            java.lang.String r3 = "__er"
            r10 = 0
            r11 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            r9 = 0
            r2 = r12
            r4 = r1
            android.database.Cursor r14 = r2.a(r3, r4, r5, r6, r7, r8, r9, r10, r11)     // Catch: java.lang.Throwable -> L87 android.database.sqlite.SQLiteDatabaseCorruptException -> L96
            goto L27
        L39:
            if (r0 == 0) goto L6e
            org.json.JSONArray r14 = new org.json.JSONArray     // Catch: java.lang.Throwable -> L87 android.database.sqlite.SQLiteDatabaseCorruptException -> L96
            r14.<init>()     // Catch: java.lang.Throwable -> L87 android.database.sqlite.SQLiteDatabaseCorruptException -> L96
        L40:
            boolean r2 = r0.moveToNext()     // Catch: java.lang.Throwable -> L87 android.database.sqlite.SQLiteDatabaseCorruptException -> L96
            if (r2 == 0) goto L63
            java.lang.String r2 = "__a"
            int r2 = r0.getColumnIndex(r2)     // Catch: java.lang.Throwable -> L87 android.database.sqlite.SQLiteDatabaseCorruptException -> L96
            java.lang.String r2 = r0.getString(r2)     // Catch: java.lang.Throwable -> L87 android.database.sqlite.SQLiteDatabaseCorruptException -> L96
            boolean r3 = android.text.TextUtils.isEmpty(r2)     // Catch: java.lang.Throwable -> L87 android.database.sqlite.SQLiteDatabaseCorruptException -> L96
            if (r3 != 0) goto L40
            org.json.JSONObject r3 = new org.json.JSONObject     // Catch: java.lang.Throwable -> L87 android.database.sqlite.SQLiteDatabaseCorruptException -> L96
            java.lang.String r2 = r12.d(r2)     // Catch: java.lang.Throwable -> L87 android.database.sqlite.SQLiteDatabaseCorruptException -> L96
            r3.<init>(r2)     // Catch: java.lang.Throwable -> L87 android.database.sqlite.SQLiteDatabaseCorruptException -> L96
            r14.put(r3)     // Catch: java.lang.Throwable -> L87 android.database.sqlite.SQLiteDatabaseCorruptException -> L96
            goto L40
        L63:
            int r2 = r14.length()     // Catch: java.lang.Throwable -> L87 android.database.sqlite.SQLiteDatabaseCorruptException -> L96
            if (r2 <= 0) goto L6e
            java.lang.String r2 = "error"
            r13.put(r2, r14)     // Catch: java.lang.Throwable -> L87 android.database.sqlite.SQLiteDatabaseCorruptException -> L96
        L6e:
            r1.setTransactionSuccessful()     // Catch: java.lang.Throwable -> L87 android.database.sqlite.SQLiteDatabaseCorruptException -> L96
            if (r0 == 0) goto L76
            r0.close()
        L76:
            r1.endTransaction()     // Catch: java.lang.Throwable -> L79
        L79:
            android.content.Context r13 = com.umeng.analytics.pro.k.f21783d
            com.umeng.analytics.pro.i r13 = com.umeng.analytics.pro.i.a(r13)
            r13.b()
            goto La3
        L83:
            r1 = r0
            goto L87
        L85:
            r1 = r0
            goto L96
        L87:
            android.content.Context r13 = com.umeng.analytics.pro.k.f21783d     // Catch: java.lang.Throwable -> L94
            com.umeng.analytics.pro.j.a(r13)     // Catch: java.lang.Throwable -> L94
            if (r0 == 0) goto L91
            r0.close()
        L91:
            if (r1 == 0) goto L79
            goto L76
        L94:
            r13 = move-exception
            goto La4
        L96:
            android.content.Context r13 = com.umeng.analytics.pro.k.f21783d     // Catch: java.lang.Throwable -> L94
            com.umeng.analytics.pro.j.a(r13)     // Catch: java.lang.Throwable -> L94
            if (r0 == 0) goto La0
            r0.close()
        La0:
            if (r1 == 0) goto L79
            goto L76
        La3:
            return
        La4:
            if (r0 == 0) goto La9
            r0.close()
        La9:
            if (r1 == 0) goto Lae
            r1.endTransaction()     // Catch: java.lang.Throwable -> Lae
        Lae:
            android.content.Context r14 = com.umeng.analytics.pro.k.f21783d
            com.umeng.analytics.pro.i r14 = com.umeng.analytics.pro.i.a(r14)
            r14.b()
            throw r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.k.b(org.json.JSONObject, java.lang.String):void");
    }

    public String c(String str) {
        try {
            return TextUtils.isEmpty(f21784e) ? str : Base64.encodeToString(DataHelper.encrypt(str.getBytes(), f21784e.getBytes()), 0);
        } catch (Exception unused) {
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0060, code lost:
    
        if (r0 == null) goto L11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0062, code lost:
    
        r0.endTransaction();
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x006b, code lost:
    
        if (r0 == null) goto L11;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean a(java.lang.String r5, java.lang.String r6, int r7) {
        /*
            r4 = this;
            r0 = 0
            android.content.Context r1 = com.umeng.analytics.pro.k.f21783d     // Catch: java.lang.Throwable -> L60 android.database.sqlite.SQLiteDatabaseCorruptException -> L66
            com.umeng.analytics.pro.i r1 = com.umeng.analytics.pro.i.a(r1)     // Catch: java.lang.Throwable -> L60 android.database.sqlite.SQLiteDatabaseCorruptException -> L66
            android.database.sqlite.SQLiteDatabase r1 = r1.a()     // Catch: java.lang.Throwable -> L60 android.database.sqlite.SQLiteDatabaseCorruptException -> L66
            r1.beginTransaction()     // Catch: java.lang.Throwable -> L4c android.database.sqlite.SQLiteDatabaseCorruptException -> L4e
            android.content.ContentValues r2 = new android.content.ContentValues     // Catch: java.lang.Throwable -> L4c android.database.sqlite.SQLiteDatabaseCorruptException -> L4e
            r2.<init>()     // Catch: java.lang.Throwable -> L4c android.database.sqlite.SQLiteDatabaseCorruptException -> L4e
            java.lang.String r3 = "__i"
            r2.put(r3, r5)     // Catch: java.lang.Throwable -> L4c android.database.sqlite.SQLiteDatabaseCorruptException -> L4e
            java.lang.String r5 = r4.c(r6)     // Catch: java.lang.Throwable -> L4c android.database.sqlite.SQLiteDatabaseCorruptException -> L4e
            boolean r6 = android.text.TextUtils.isEmpty(r5)     // Catch: java.lang.Throwable -> L4c android.database.sqlite.SQLiteDatabaseCorruptException -> L4e
            if (r6 != 0) goto L50
            java.lang.String r6 = "__a"
            r2.put(r6, r5)     // Catch: java.lang.Throwable -> L4c android.database.sqlite.SQLiteDatabaseCorruptException -> L4e
            java.lang.String r5 = "__t"
            java.lang.Integer r6 = java.lang.Integer.valueOf(r7)     // Catch: java.lang.Throwable -> L4c android.database.sqlite.SQLiteDatabaseCorruptException -> L4e
            r2.put(r5, r6)     // Catch: java.lang.Throwable -> L4c android.database.sqlite.SQLiteDatabaseCorruptException -> L4e
            java.lang.String r5 = "__av"
            android.content.Context r6 = com.umeng.analytics.pro.k.f21783d     // Catch: java.lang.Throwable -> L4c android.database.sqlite.SQLiteDatabaseCorruptException -> L4e
            java.lang.String r6 = com.umeng.commonsdk.utils.UMUtils.getAppVersionName(r6)     // Catch: java.lang.Throwable -> L4c android.database.sqlite.SQLiteDatabaseCorruptException -> L4e
            r2.put(r5, r6)     // Catch: java.lang.Throwable -> L4c android.database.sqlite.SQLiteDatabaseCorruptException -> L4e
            java.lang.String r5 = "__vc"
            android.content.Context r6 = com.umeng.analytics.pro.k.f21783d     // Catch: java.lang.Throwable -> L4c android.database.sqlite.SQLiteDatabaseCorruptException -> L4e
            java.lang.String r6 = com.umeng.commonsdk.utils.UMUtils.getAppVersionCode(r6)     // Catch: java.lang.Throwable -> L4c android.database.sqlite.SQLiteDatabaseCorruptException -> L4e
            r2.put(r5, r6)     // Catch: java.lang.Throwable -> L4c android.database.sqlite.SQLiteDatabaseCorruptException -> L4e
            java.lang.String r5 = "__er"
            r1.insert(r5, r0, r2)     // Catch: java.lang.Throwable -> L4c android.database.sqlite.SQLiteDatabaseCorruptException -> L4e
            goto L50
        L4c:
            r0 = r1
            goto L60
        L4e:
            r0 = r1
            goto L66
        L50:
            r1.setTransactionSuccessful()     // Catch: java.lang.Throwable -> L4c android.database.sqlite.SQLiteDatabaseCorruptException -> L4e
            r1.endTransaction()     // Catch: java.lang.Throwable -> L56
        L56:
            android.content.Context r5 = com.umeng.analytics.pro.k.f21783d
            com.umeng.analytics.pro.i r5 = com.umeng.analytics.pro.i.a(r5)
            r5.b()
            goto L6e
        L60:
            if (r0 == 0) goto L56
        L62:
            r0.endTransaction()     // Catch: java.lang.Throwable -> L56
            goto L56
        L66:
            android.content.Context r5 = com.umeng.analytics.pro.k.f21783d     // Catch: java.lang.Throwable -> L70
            com.umeng.analytics.pro.j.a(r5)     // Catch: java.lang.Throwable -> L70
            if (r0 == 0) goto L56
            goto L62
        L6e:
            r5 = 0
            return r5
        L70:
            r5 = move-exception
            if (r0 == 0) goto L76
            r0.endTransaction()     // Catch: java.lang.Throwable -> L76
        L76:
            android.content.Context r6 = com.umeng.analytics.pro.k.f21783d
            com.umeng.analytics.pro.i r6 = com.umeng.analytics.pro.i.a(r6)
            r6.b()
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.k.a(java.lang.String, java.lang.String, int):boolean");
    }

    private JSONArray b(JSONArray jSONArray) {
        JSONArray jSONArray2 = new JSONArray();
        int length = jSONArray.length();
        for (int i2 = 0; i2 < length; i2++) {
            JSONObject jSONObjectOptJSONObject = jSONArray.optJSONObject(i2);
            if (jSONObjectOptJSONObject != null && jSONObjectOptJSONObject.optLong("duration") > 0) {
                jSONArray2.put(jSONObjectOptJSONObject);
            }
        }
        return jSONArray2;
    }

    /* JADX WARN: Finally extract failed */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00de A[EXC_TOP_SPLITTER, PHI: r1 r13
      0x00de: PHI (r1v4 android.database.sqlite.SQLiteDatabase) = (r1v3 android.database.sqlite.SQLiteDatabase), (r1v6 android.database.sqlite.SQLiteDatabase) binds: [B:38:0x00dc, B:46:0x00f8] A[DONT_GENERATE, DONT_INLINE]
      0x00de: PHI (r13v5 java.lang.Object) = (r13v4 java.lang.Object), (r13v7 java.lang.Object) binds: [B:38:0x00dc, B:46:0x00f8] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r0v11 */
    /* JADX WARN: Type inference failed for: r0v13 */
    /* JADX WARN: Type inference failed for: r0v18 */
    /* JADX WARN: Type inference failed for: r0v19 */
    /* JADX WARN: Type inference failed for: r0v20 */
    /* JADX WARN: Type inference failed for: r0v22 */
    /* JADX WARN: Type inference failed for: r0v23 */
    /* JADX WARN: Type inference failed for: r0v3 */
    /* JADX WARN: Type inference failed for: r0v5, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r0v9 */
    /* JADX WARN: Type inference failed for: r13v0, types: [org.json.JSONObject] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.String b(org.json.JSONObject r13, boolean r14) {
        /*
            Method dump skipped, instructions count: 272
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.k.b(org.json.JSONObject, boolean):java.lang.String");
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x008e, code lost:
    
        if (r2 == null) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0090, code lost:
    
        r2.endTransaction();
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0099, code lost:
    
        if (r2 == null) goto L29;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean a(java.lang.String r7, org.json.JSONObject r8, com.umeng.analytics.pro.k.a r9) {
        /*
            r6 = this;
            java.lang.String r0 = "__e"
            r1 = 0
            if (r8 != 0) goto L6
            return r1
        L6:
            r2 = 0
            android.content.Context r3 = com.umeng.analytics.pro.k.f21783d     // Catch: java.lang.Throwable -> L8e android.database.sqlite.SQLiteDatabaseCorruptException -> L94
            com.umeng.analytics.pro.i r3 = com.umeng.analytics.pro.i.a(r3)     // Catch: java.lang.Throwable -> L8e android.database.sqlite.SQLiteDatabaseCorruptException -> L94
            android.database.sqlite.SQLiteDatabase r3 = r3.a()     // Catch: java.lang.Throwable -> L8e android.database.sqlite.SQLiteDatabaseCorruptException -> L94
            r3.beginTransaction()     // Catch: java.lang.Throwable -> L4f android.database.sqlite.SQLiteDatabaseCorruptException -> L51
            com.umeng.analytics.pro.k$a r4 = com.umeng.analytics.pro.k.a.BEGIN     // Catch: java.lang.Throwable -> L4f android.database.sqlite.SQLiteDatabaseCorruptException -> L51
            if (r9 != r4) goto L53
            java.lang.Object r8 = r8.opt(r0)     // Catch: java.lang.Throwable -> L4f android.database.sqlite.SQLiteDatabaseCorruptException -> L51
            java.lang.Long r8 = (java.lang.Long) r8     // Catch: java.lang.Throwable -> L4f android.database.sqlite.SQLiteDatabaseCorruptException -> L51
            long r8 = r8.longValue()     // Catch: java.lang.Throwable -> L4f android.database.sqlite.SQLiteDatabaseCorruptException -> L51
            android.content.ContentValues r4 = new android.content.ContentValues     // Catch: java.lang.Throwable -> L4f android.database.sqlite.SQLiteDatabaseCorruptException -> L51
            r4.<init>()     // Catch: java.lang.Throwable -> L4f android.database.sqlite.SQLiteDatabaseCorruptException -> L51
            java.lang.String r5 = "__ii"
            r4.put(r5, r7)     // Catch: java.lang.Throwable -> L4f android.database.sqlite.SQLiteDatabaseCorruptException -> L51
            java.lang.String r7 = java.lang.String.valueOf(r8)     // Catch: java.lang.Throwable -> L4f android.database.sqlite.SQLiteDatabaseCorruptException -> L51
            r4.put(r0, r7)     // Catch: java.lang.Throwable -> L4f android.database.sqlite.SQLiteDatabaseCorruptException -> L51
            java.lang.String r7 = "__av"
            android.content.Context r8 = com.umeng.analytics.pro.k.f21783d     // Catch: java.lang.Throwable -> L4f android.database.sqlite.SQLiteDatabaseCorruptException -> L51
            java.lang.String r8 = com.umeng.commonsdk.utils.UMUtils.getAppVersionName(r8)     // Catch: java.lang.Throwable -> L4f android.database.sqlite.SQLiteDatabaseCorruptException -> L51
            r4.put(r7, r8)     // Catch: java.lang.Throwable -> L4f android.database.sqlite.SQLiteDatabaseCorruptException -> L51
            java.lang.String r7 = "__vc"
            android.content.Context r8 = com.umeng.analytics.pro.k.f21783d     // Catch: java.lang.Throwable -> L4f android.database.sqlite.SQLiteDatabaseCorruptException -> L51
            java.lang.String r8 = com.umeng.commonsdk.utils.UMUtils.getAppVersionCode(r8)     // Catch: java.lang.Throwable -> L4f android.database.sqlite.SQLiteDatabaseCorruptException -> L51
            r4.put(r7, r8)     // Catch: java.lang.Throwable -> L4f android.database.sqlite.SQLiteDatabaseCorruptException -> L51
            java.lang.String r7 = "__sd"
            r3.insert(r7, r2, r4)     // Catch: java.lang.Throwable -> L4f android.database.sqlite.SQLiteDatabaseCorruptException -> L51
            goto L7e
        L4f:
            r2 = r3
            goto L8e
        L51:
            r2 = r3
            goto L94
        L53:
            com.umeng.analytics.pro.k$a r0 = com.umeng.analytics.pro.k.a.INSTANTSESSIONBEGIN     // Catch: java.lang.Throwable -> L4f android.database.sqlite.SQLiteDatabaseCorruptException -> L51
            if (r9 != r0) goto L5b
            r6.b(r7, r8, r3)     // Catch: java.lang.Throwable -> L4f android.database.sqlite.SQLiteDatabaseCorruptException -> L51
            goto L7e
        L5b:
            com.umeng.analytics.pro.k$a r0 = com.umeng.analytics.pro.k.a.END     // Catch: java.lang.Throwable -> L4f android.database.sqlite.SQLiteDatabaseCorruptException -> L51
            if (r9 != r0) goto L63
            r6.a(r7, r8, r3)     // Catch: java.lang.Throwable -> L4f android.database.sqlite.SQLiteDatabaseCorruptException -> L51
            goto L7e
        L63:
            com.umeng.analytics.pro.k$a r0 = com.umeng.analytics.pro.k.a.PAGE     // Catch: java.lang.Throwable -> L4f android.database.sqlite.SQLiteDatabaseCorruptException -> L51
            if (r9 != r0) goto L6d
            java.lang.String r9 = "__a"
            r6.a(r7, r8, r3, r9)     // Catch: java.lang.Throwable -> L4f android.database.sqlite.SQLiteDatabaseCorruptException -> L51
            goto L7e
        L6d:
            com.umeng.analytics.pro.k$a r0 = com.umeng.analytics.pro.k.a.AUTOPAGE     // Catch: java.lang.Throwable -> L4f android.database.sqlite.SQLiteDatabaseCorruptException -> L51
            if (r9 != r0) goto L77
            java.lang.String r9 = "__b"
            r6.a(r7, r8, r3, r9)     // Catch: java.lang.Throwable -> L4f android.database.sqlite.SQLiteDatabaseCorruptException -> L51
            goto L7e
        L77:
            com.umeng.analytics.pro.k$a r0 = com.umeng.analytics.pro.k.a.NEWSESSION     // Catch: java.lang.Throwable -> L4f android.database.sqlite.SQLiteDatabaseCorruptException -> L51
            if (r9 != r0) goto L7e
            r6.c(r7, r8, r3)     // Catch: java.lang.Throwable -> L4f android.database.sqlite.SQLiteDatabaseCorruptException -> L51
        L7e:
            r3.setTransactionSuccessful()     // Catch: java.lang.Throwable -> L4f android.database.sqlite.SQLiteDatabaseCorruptException -> L51
            r3.endTransaction()     // Catch: java.lang.Throwable -> L84
        L84:
            android.content.Context r7 = com.umeng.analytics.pro.k.f21783d
            com.umeng.analytics.pro.i r7 = com.umeng.analytics.pro.i.a(r7)
            r7.b()
            goto L9c
        L8e:
            if (r2 == 0) goto L84
        L90:
            r2.endTransaction()     // Catch: java.lang.Throwable -> L84
            goto L84
        L94:
            android.content.Context r7 = com.umeng.analytics.pro.k.f21783d     // Catch: java.lang.Throwable -> L9d
            com.umeng.analytics.pro.j.a(r7)     // Catch: java.lang.Throwable -> L9d
            if (r2 == 0) goto L84
            goto L90
        L9c:
            return r1
        L9d:
            r7 = move-exception
            if (r2 == 0) goto La3
            r2.endTransaction()     // Catch: java.lang.Throwable -> La3
        La3:
            android.content.Context r8 = com.umeng.analytics.pro.k.f21783d
            com.umeng.analytics.pro.i r8 = com.umeng.analytics.pro.i.a(r8)
            r8.b()
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.k.a(java.lang.String, org.json.JSONObject, com.umeng.analytics.pro.k$a):boolean");
    }

    private void a(String str, JSONObject jSONObject, SQLiteDatabase sQLiteDatabase) {
        String strC;
        try {
            long jLongValue = ((Long) jSONObject.opt(g.d.a.f21754g)).longValue();
            Object objOpt = jSONObject.opt(g.d.a.f21755h);
            long jLongValue2 = (objOpt == null || !(objOpt instanceof Long)) ? 0L : ((Long) objOpt).longValue();
            JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("__sp");
            JSONObject jSONObjectOptJSONObject2 = jSONObject.optJSONObject("__pp");
            String strC2 = "";
            if (jSONObjectOptJSONObject == null || jSONObjectOptJSONObject.length() <= 0) {
                strC = "";
            } else {
                strC = c(jSONObjectOptJSONObject.toString());
            }
            if (jSONObjectOptJSONObject2 != null && jSONObjectOptJSONObject2.length() > 0) {
                strC2 = c(jSONObjectOptJSONObject2.toString());
            }
            sQLiteDatabase.execSQL("update __sd set __f=\"" + jLongValue + "\", " + g.d.a.f21755h + "=\"" + jLongValue2 + "\", __sp=\"" + strC + "\", __pp=\"" + strC2 + "\" where __ii=\"" + str + "\"");
        } catch (Throwable unused) {
        }
    }

    public void b(boolean z2, boolean z3) {
        SQLiteDatabase sQLiteDatabaseA = null;
        try {
            try {
                sQLiteDatabaseA = i.a(f21783d).a();
                sQLiteDatabaseA.beginTransaction();
                if (z3) {
                    if (z2) {
                        sQLiteDatabaseA.execSQL("delete from __sd");
                    }
                } else if (this.f21788i.size() > 0) {
                    for (int i2 = 0; i2 < this.f21788i.size(); i2++) {
                        sQLiteDatabaseA.execSQL("delete from __sd where __ii=\"" + this.f21788i.get(i2) + "\"");
                    }
                }
                sQLiteDatabaseA.setTransactionSuccessful();
            } catch (SQLiteDatabaseCorruptException unused) {
                j.a(f21783d);
                if (sQLiteDatabaseA != null) {
                }
            } catch (Throwable unused2) {
            }
        } finally {
            if (sQLiteDatabaseA != null) {
                try {
                    sQLiteDatabaseA.endTransaction();
                } catch (Throwable unused3) {
                }
            }
            i.a(f21783d).b();
        }
    }

    public long a(String str) throws Throwable {
        SQLiteDatabase sQLiteDatabaseA;
        Cursor cursorA = null;
        long j2 = 0;
        try {
            try {
                sQLiteDatabaseA = i.a(f21783d).a();
                try {
                    sQLiteDatabaseA.beginTransaction();
                    cursorA = a(g.d.f21747a, sQLiteDatabaseA, new String[]{g.d.a.f21754g}, "__ii=? ", new String[]{str}, null, null, null, null);
                    if (cursorA != null) {
                        cursorA.moveToFirst();
                        j2 = cursorA.getLong(cursorA.getColumnIndex(g.d.a.f21754g));
                    }
                    if (cursorA != null) {
                        cursorA.close();
                    }
                } catch (Exception unused) {
                    if (cursorA != null) {
                        cursorA.close();
                    }
                    if (sQLiteDatabaseA != null) {
                        sQLiteDatabaseA.endTransaction();
                    }
                    i.a(f21783d).b();
                    return j2;
                } catch (Throwable th) {
                    th = th;
                    if (cursorA != null) {
                        try {
                            cursorA.close();
                        } catch (Exception unused2) {
                            i.a(f21783d).b();
                            throw th;
                        }
                    }
                    if (sQLiteDatabaseA != null) {
                        sQLiteDatabaseA.endTransaction();
                    }
                    i.a(f21783d).b();
                    throw th;
                }
            } catch (Exception unused3) {
                sQLiteDatabaseA = null;
            } catch (Throwable th2) {
                th = th2;
                sQLiteDatabaseA = null;
            }
            sQLiteDatabaseA.endTransaction();
        } catch (Exception unused4) {
        }
        i.a(f21783d).b();
        return j2;
    }

    public void b(String str) {
        SQLiteDatabase sQLiteDatabaseA = null;
        try {
            try {
                sQLiteDatabaseA = i.a(f21783d).a();
                sQLiteDatabaseA.beginTransaction();
                if (!TextUtils.isEmpty(str)) {
                    sQLiteDatabaseA.execSQL("delete from __is where __ii=\"" + str + "\"");
                }
                sQLiteDatabaseA.setTransactionSuccessful();
            } catch (SQLiteDatabaseCorruptException unused) {
                j.a(f21783d);
                if (sQLiteDatabaseA != null) {
                }
            } catch (Throwable unused2) {
            }
        } finally {
            if (sQLiteDatabaseA != null) {
                try {
                    sQLiteDatabaseA.endTransaction();
                } catch (Throwable unused3) {
                }
            }
            i.a(f21783d).b();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0070 A[Catch: all -> 0x0062, TryCatch #1 {all -> 0x0062, blocks: (B:22:0x004f, B:24:0x0055, B:26:0x0065, B:28:0x0070, B:29:0x0075, B:36:0x0084, B:38:0x008a, B:40:0x0090, B:42:0x0096, B:44:0x00a4, B:41:0x0093), top: B:54:0x004f }] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x007d  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0083  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x004f A[EXC_TOP_SPLITTER, LOOP:1: B:54:0x004f->B:24:0x0055, LOOP_START, PHI: r13
      0x004f: PHI (r13v2 java.lang.String) = (r13v7 java.lang.String), (r13v3 java.lang.String) binds: [B:21:0x004d, B:24:0x0055] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    @android.annotation.SuppressLint({"Range"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void a(java.lang.String r16, org.json.JSONObject r17, android.database.sqlite.SQLiteDatabase r18, java.lang.String r19) throws org.json.JSONException {
        /*
            r15 = this;
            r10 = r15
            r0 = r17
            r11 = r19
            java.lang.String r12 = "=\""
            java.lang.String r1 = "__b"
            java.lang.String r2 = "__a"
            r13 = 0
            boolean r3 = r2.equals(r11)     // Catch: java.lang.Throwable -> Ldd
            if (r3 == 0) goto L22
            org.json.JSONArray r0 = r0.optJSONArray(r2)     // Catch: java.lang.Throwable -> Ldd
            if (r0 == 0) goto L21
            int r1 = r0.length()     // Catch: java.lang.Throwable -> Ldd
            if (r1 > 0) goto L1f
            goto L21
        L1f:
            r14 = r0
            goto L36
        L21:
            return
        L22:
            boolean r2 = r1.equals(r11)     // Catch: java.lang.Throwable -> Ldd
            if (r2 == 0) goto L35
            org.json.JSONArray r0 = r0.optJSONArray(r1)     // Catch: java.lang.Throwable -> Ldd
            if (r0 == 0) goto L34
            int r1 = r0.length()     // Catch: java.lang.Throwable -> Ldd
            if (r1 > 0) goto L1f
        L34:
            return
        L35:
            r14 = r13
        L36:
            java.lang.String[] r3 = new java.lang.String[]{r19}     // Catch: java.lang.Throwable -> Ldd
            java.lang.String r1 = "__sd"
            java.lang.String r4 = "__ii=? "
            java.lang.String[] r5 = new java.lang.String[]{r16}     // Catch: java.lang.Throwable -> Ldd
            r8 = 0
            r9 = 0
            r6 = 0
            r7 = 0
            r0 = r15
            r2 = r18
            android.database.Cursor r0 = r0.a(r1, r2, r3, r4, r5, r6, r7, r8, r9)     // Catch: java.lang.Throwable -> Ldd
            if (r0 == 0) goto L65
        L4f:
            boolean r1 = r0.moveToNext()     // Catch: java.lang.Throwable -> L62
            if (r1 == 0) goto L65
            int r1 = r0.getColumnIndex(r11)     // Catch: java.lang.Throwable -> L62
            java.lang.String r1 = r0.getString(r1)     // Catch: java.lang.Throwable -> L62
            java.lang.String r13 = r15.d(r1)     // Catch: java.lang.Throwable -> L62
            goto L4f
        L62:
            r13 = r0
            goto Ldd
        L65:
            org.json.JSONArray r1 = new org.json.JSONArray     // Catch: java.lang.Throwable -> L62
            r1.<init>()     // Catch: java.lang.Throwable -> L62
            boolean r2 = android.text.TextUtils.isEmpty(r13)     // Catch: java.lang.Throwable -> L62
            if (r2 != 0) goto L75
            org.json.JSONArray r1 = new org.json.JSONArray     // Catch: java.lang.Throwable -> L62
            r1.<init>(r13)     // Catch: java.lang.Throwable -> L62
        L75:
            int r2 = r1.length()     // Catch: java.lang.Throwable -> L62
            r3 = 1000(0x3e8, float:1.401E-42)
            if (r2 <= r3) goto L83
            if (r0 == 0) goto L82
            r0.close()
        L82:
            return
        L83:
            r2 = 0
        L84:
            int r3 = r14.length()     // Catch: java.lang.Throwable -> L62
            if (r2 >= r3) goto L96
            org.json.JSONObject r3 = r14.getJSONObject(r2)     // Catch: java.lang.Throwable -> L62 org.json.JSONException -> L93
            if (r3 == 0) goto L93
            r1.put(r3)     // Catch: java.lang.Throwable -> L62
        L93:
            int r2 = r2 + 1
            goto L84
        L96:
            java.lang.String r1 = r1.toString()     // Catch: java.lang.Throwable -> L62
            java.lang.String r1 = r15.c(r1)     // Catch: java.lang.Throwable -> L62
            boolean r2 = android.text.TextUtils.isEmpty(r1)     // Catch: java.lang.Throwable -> L62
            if (r2 != 0) goto Ld7
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L62
            r2.<init>()     // Catch: java.lang.Throwable -> L62
            java.lang.String r3 = "update __sd set "
            r2.append(r3)     // Catch: java.lang.Throwable -> L62
            r2.append(r11)     // Catch: java.lang.Throwable -> L62
            r2.append(r12)     // Catch: java.lang.Throwable -> L62
            r2.append(r1)     // Catch: java.lang.Throwable -> L62
            java.lang.String r1 = "\" where "
            r2.append(r1)     // Catch: java.lang.Throwable -> L62
            java.lang.String r1 = "__ii"
            r2.append(r1)     // Catch: java.lang.Throwable -> L62
            r2.append(r12)     // Catch: java.lang.Throwable -> L62
            r1 = r16
            r2.append(r1)     // Catch: java.lang.Throwable -> L62
            java.lang.String r1 = "\""
            r2.append(r1)     // Catch: java.lang.Throwable -> L62
            java.lang.String r1 = r2.toString()     // Catch: java.lang.Throwable -> L62
            r2 = r18
            r2.execSQL(r1)     // Catch: java.lang.Throwable -> L62
        Ld7:
            if (r0 == 0) goto Le2
            r0.close()
            goto Le2
        Ldd:
            if (r13 == 0) goto Le2
            r13.close()
        Le2:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.k.a(java.lang.String, org.json.JSONObject, android.database.sqlite.SQLiteDatabase, java.lang.String):void");
    }

    public JSONObject a(boolean z2) {
        a();
        this.f21789j.clear();
        JSONObject jSONObject = new JSONObject();
        if (!z2) {
            a(jSONObject, z2);
            b(jSONObject, (String) null);
            a(jSONObject, (String) null);
        } else {
            String strA = a(jSONObject, z2);
            if (!TextUtils.isEmpty(strA)) {
                b(jSONObject, strA);
                a(jSONObject, strA);
            }
        }
        return jSONObject;
    }

    /* JADX WARN: Removed duplicated region for block: B:91:0x0177 A[PHI: r0 r1
      0x0177: PHI (r0v4 android.database.Cursor) = (r0v2 android.database.Cursor), (r0v3 android.database.Cursor), (r0v5 android.database.Cursor) binds: [B:71:0x018f, B:78:0x019e, B:64:0x0174] A[DONT_GENERATE, DONT_INLINE]
      0x0177: PHI (r1v5 android.database.sqlite.SQLiteDatabase) = 
      (r1v3 android.database.sqlite.SQLiteDatabase)
      (r1v4 android.database.sqlite.SQLiteDatabase)
      (r1v6 android.database.sqlite.SQLiteDatabase)
     binds: [B:71:0x018f, B:78:0x019e, B:64:0x0174] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0174 A[EXC_TOP_SPLITTER, PHI: r0 r1
      0x0174: PHI (r0v5 android.database.Cursor) = 
      (r0v2 android.database.Cursor)
      (r0v3 android.database.Cursor)
      (r0v8 android.database.Cursor)
      (r0v8 android.database.Cursor)
     binds: [B:71:0x018f, B:78:0x019e, B:62:0x016f, B:63:0x0171] A[DONT_GENERATE, DONT_INLINE]
      0x0174: PHI (r1v6 android.database.sqlite.SQLiteDatabase) = 
      (r1v3 android.database.sqlite.SQLiteDatabase)
      (r1v4 android.database.sqlite.SQLiteDatabase)
      (r1v9 android.database.sqlite.SQLiteDatabase)
      (r1v9 android.database.sqlite.SQLiteDatabase)
     binds: [B:71:0x018f, B:78:0x019e, B:62:0x016f, B:63:0x0171] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void a(org.json.JSONObject r13, java.lang.String r14) {
        /*
            Method dump skipped, instructions count: 438
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.k.a(org.json.JSONObject, java.lang.String):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x023b  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x020e A[EXC_TOP_SPLITTER, PHI: r0 r12 r14
      0x020e: PHI (r0v6 java.lang.String) = (r0v2 java.lang.String), (r0v3 java.lang.String), (r0v39 java.lang.String), (r0v39 java.lang.String) binds: [B:94:0x022f, B:101:0x023e, B:83:0x0209, B:84:0x020b] A[DONT_GENERATE, DONT_INLINE]
      0x020e: PHI (r12v6 android.database.sqlite.SQLiteDatabase) = 
      (r12v3 android.database.sqlite.SQLiteDatabase)
      (r12v4 android.database.sqlite.SQLiteDatabase)
      (r12v7 android.database.sqlite.SQLiteDatabase)
      (r12v7 android.database.sqlite.SQLiteDatabase)
     binds: [B:94:0x022f, B:101:0x023e, B:83:0x0209, B:84:0x020b] A[DONT_GENERATE, DONT_INLINE]
      0x020e: PHI (r14v6 android.database.Cursor) = 
      (r14v3 android.database.Cursor)
      (r14v4 android.database.Cursor)
      (r14v33 android.database.Cursor)
      (r14v33 android.database.Cursor)
     binds: [B:94:0x022f, B:101:0x023e, B:83:0x0209, B:84:0x020b] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:125:0x0211 A[PHI: r0 r12 r14
      0x0211: PHI (r0v5 java.lang.String) = (r0v2 java.lang.String), (r0v3 java.lang.String), (r0v6 java.lang.String) binds: [B:94:0x022f, B:101:0x023e, B:85:0x020e] A[DONT_GENERATE, DONT_INLINE]
      0x0211: PHI (r12v5 android.database.sqlite.SQLiteDatabase) = 
      (r12v3 android.database.sqlite.SQLiteDatabase)
      (r12v4 android.database.sqlite.SQLiteDatabase)
      (r12v6 android.database.sqlite.SQLiteDatabase)
     binds: [B:94:0x022f, B:101:0x023e, B:85:0x020e] A[DONT_GENERATE, DONT_INLINE]
      0x0211: PHI (r14v5 android.database.Cursor) = (r14v3 android.database.Cursor), (r14v4 android.database.Cursor), (r14v6 android.database.Cursor) binds: [B:94:0x022f, B:101:0x023e, B:85:0x020e] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:93:0x022c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.String a(org.json.JSONObject r22, boolean r23) {
        /*
            Method dump skipped, instructions count: 598
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.analytics.pro.k.a(org.json.JSONObject, boolean):java.lang.String");
    }

    public void a(boolean z2, boolean z3) {
        SQLiteDatabase sQLiteDatabaseA = null;
        try {
            try {
                sQLiteDatabaseA = i.a(f21783d).a();
                sQLiteDatabaseA.beginTransaction();
                if (!z3) {
                    int size = this.f21791l.size();
                    int i2 = 0;
                    if (size > 0) {
                        int i3 = 0;
                        while (i2 < size) {
                            String str = this.f21791l.get(i2);
                            if (str == null) {
                                i3 = 1;
                            }
                            sQLiteDatabaseA.execSQL("delete from __is where __ii=\"" + str + "\"");
                            i2++;
                        }
                        i2 = i3;
                    }
                    if (i2 != 0) {
                        sQLiteDatabaseA.execSQL("delete from __is where __ii is null");
                    }
                } else if (z2) {
                    sQLiteDatabaseA.execSQL("delete from __is");
                }
                sQLiteDatabaseA.setTransactionSuccessful();
            } catch (SQLiteDatabaseCorruptException unused) {
                j.a(f21783d);
                if (sQLiteDatabaseA != null) {
                }
                i.a(f21783d).b();
            } catch (Throwable unused2) {
                j.a(f21783d);
                if (sQLiteDatabaseA != null) {
                }
            }
            try {
                sQLiteDatabaseA.endTransaction();
            } catch (Throwable unused3) {
            }
            i.a(f21783d).b();
        } finally {
            if (sQLiteDatabaseA != null) {
                try {
                    sQLiteDatabaseA.endTransaction();
                } catch (Throwable unused4) {
                }
            }
            i.a(f21783d).b();
        }
    }

    public void a(boolean z2, String str) {
        SQLiteDatabase sQLiteDatabaseA = null;
        try {
            try {
                sQLiteDatabaseA = i.a(f21783d).a();
                sQLiteDatabaseA.beginTransaction();
                if (!TextUtils.isEmpty(str)) {
                    sQLiteDatabaseA.execSQL("delete from __er where __i=\"" + str + "\"");
                    sQLiteDatabaseA.execSQL("delete from __et where __i=\"" + str + "\"");
                    this.f21789j.clear();
                    sQLiteDatabaseA.execSQL("delete from __sd where __ii=\"" + str + "\"");
                }
                sQLiteDatabaseA.setTransactionSuccessful();
            } catch (SQLiteDatabaseCorruptException unused) {
                j.a(f21783d);
                if (sQLiteDatabaseA != null) {
                }
            } catch (Throwable unused2) {
            }
        } finally {
            if (sQLiteDatabaseA != null) {
                try {
                    sQLiteDatabaseA.endTransaction();
                } catch (Throwable unused3) {
                }
            }
            i.a(f21783d).b();
        }
    }
}
