package com.umeng.analytics.pro;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* loaded from: classes4.dex */
public class j {
    public static boolean a(String str, SQLiteDatabase sQLiteDatabase) {
        boolean z2 = false;
        if (str == null) {
            return false;
        }
        Cursor cursorQuery = null;
        try {
            cursorQuery = sQLiteDatabase.query("sqlite_master", new String[]{"count(*)"}, "type=? and name=?", new String[]{"table", str.trim()}, null, null, null, null);
            if (cursorQuery.moveToNext()) {
                if (cursorQuery.getInt(0) > 0) {
                    z2 = true;
                }
            }
        } catch (Exception unused) {
            if (cursorQuery != null) {
            }
        } catch (Throwable th) {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            throw th;
        }
        cursorQuery.close();
        return z2;
    }

    public static String b(Context context) {
        return context.getDatabasePath(g.f21706b).getParent() + File.separator;
    }

    public static String c(Context context) {
        return b(context) + g.f21705a;
    }

    public static List<String> b(List<String> list) {
        ArrayList arrayList = new ArrayList();
        try {
            for (String str : list) {
                if (Collections.frequency(arrayList, str) < 1) {
                    arrayList.add(str);
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return arrayList;
    }

    public static void a(Context context) {
        if (context == null) {
            return;
        }
        try {
            File databasePath = context.getDatabasePath(g.f21706b);
            if (databasePath != null && databasePath.exists()) {
                databasePath.delete();
            }
            h.a(context).a();
        } catch (Throwable unused) {
        }
    }

    public static String a(List<String> list) {
        return TextUtils.join("!", list);
    }

    public static List<String> a(String str) {
        return new ArrayList(Arrays.asList(str.split("!")));
    }

    public static boolean a(SQLiteDatabase sQLiteDatabase, String str, String str2) {
        boolean z2 = false;
        Cursor cursorQuery = null;
        try {
            cursorQuery = sQLiteDatabase.query(str, null, null, null, null, null, "LIMIT 0");
            if (cursorQuery != null) {
                if (cursorQuery.getColumnIndex(str2) != -1) {
                    z2 = true;
                }
            }
        } catch (Exception unused) {
            if (cursorQuery != null && !cursorQuery.isClosed()) {
            }
        } catch (Throwable th) {
            if (cursorQuery != null && !cursorQuery.isClosed()) {
                cursorQuery.close();
            }
            throw th;
        }
        if (cursorQuery != null && !cursorQuery.isClosed()) {
            cursorQuery.close();
        }
        return z2;
    }

    public static void a(SQLiteDatabase sQLiteDatabase, String str, String str2, String str3) throws SQLException {
        sQLiteDatabase.execSQL("alter table " + str + " add " + str2 + " " + str3);
    }
}
