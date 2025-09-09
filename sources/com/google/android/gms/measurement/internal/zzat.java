package com.google.android.gms.measurement.internal;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;
import androidx.annotation.WorkerThread;
import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* loaded from: classes3.dex */
public final class zzat {
    static void a(zzfs zzfsVar, SQLiteDatabase sQLiteDatabase) {
        if (zzfsVar == null) {
            throw new IllegalArgumentException("Monitor must not be null");
        }
        File file = new File(sQLiteDatabase.getPath());
        if (!file.setReadable(false, false)) {
            zzfsVar.zzu().zza("Failed to turn off database read permission");
        }
        if (!file.setWritable(false, false)) {
            zzfsVar.zzu().zza("Failed to turn off database write permission");
        }
        if (!file.setReadable(true, true)) {
            zzfsVar.zzu().zza("Failed to turn on database read permission for owner");
        }
        if (file.setWritable(true, true)) {
            return;
        }
        zzfsVar.zzu().zza("Failed to turn on database write permission for owner");
    }

    static void b(zzfs zzfsVar, SQLiteDatabase sQLiteDatabase, String str, String str2, String str3, String[] strArr) throws SQLException {
        if (zzfsVar == null) {
            throw new IllegalArgumentException("Monitor must not be null");
        }
        if (!zza(zzfsVar, sQLiteDatabase, str)) {
            sQLiteDatabase.execSQL(str2);
        }
        try {
            Set<String> setZza = zza(sQLiteDatabase, str);
            for (String str4 : str3.split(",")) {
                if (!setZza.remove(str4)) {
                    throw new SQLiteException("Table " + str + " is missing required column: " + str4);
                }
            }
            if (strArr != null) {
                for (int i2 = 0; i2 < strArr.length; i2 += 2) {
                    if (!setZza.remove(strArr[i2])) {
                        sQLiteDatabase.execSQL(strArr[i2 + 1]);
                    }
                }
            }
            if (setZza.isEmpty()) {
                return;
            }
            zzfsVar.zzu().zza("Table has extra columns. table, columns", str, TextUtils.join(", ", setZza));
        } catch (SQLiteException e2) {
            zzfsVar.zzg().zza("Failed to verify columns on table that was just created", str);
            throw e2;
        }
    }

    @WorkerThread
    private static Set<String> zza(SQLiteDatabase sQLiteDatabase, String str) {
        HashSet hashSet = new HashSet();
        Cursor cursorRawQuery = sQLiteDatabase.rawQuery("SELECT * FROM " + str + " LIMIT 0", null);
        try {
            Collections.addAll(hashSet, cursorRawQuery.getColumnNames());
            return hashSet;
        } finally {
            cursorRawQuery.close();
        }
    }

    @WorkerThread
    private static boolean zza(zzfs zzfsVar, SQLiteDatabase sQLiteDatabase, String str) {
        if (zzfsVar != null) {
            Cursor cursorQuery = null;
            try {
                try {
                    cursorQuery = sQLiteDatabase.query("SQLITE_MASTER", new String[]{"name"}, "name=?", new String[]{str}, null, null, null);
                    boolean zMoveToFirst = cursorQuery.moveToFirst();
                    cursorQuery.close();
                    return zMoveToFirst;
                } catch (SQLiteException e2) {
                    zzfsVar.zzu().zza("Error querying for table", str, e2);
                    if (cursorQuery == null) {
                        return false;
                    }
                    cursorQuery.close();
                    return false;
                }
            } catch (Throwable th) {
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                throw th;
            }
        }
        throw new IllegalArgumentException("Monitor must not be null");
    }
}
