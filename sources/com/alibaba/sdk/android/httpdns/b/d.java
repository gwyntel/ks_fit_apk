package com.alibaba.sdk.android.httpdns.b;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.kingsmith.miot.KsProperty;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
class d extends SQLiteOpenHelper {

    /* renamed from: a, reason: collision with root package name */
    private static final Object f8800a = new Object();

    d(Context context) {
        super(context, "aliclound_httpdns.db", (SQLiteDatabase.CursorFactory) null, 1);
    }

    private long a(SQLiteDatabase sQLiteDatabase, g gVar) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("host_id", Long.valueOf(gVar.f8805i));
        contentValues.put("ip", gVar.f8806o);
        contentValues.put(RemoteMessageConst.TTL, gVar.f8807p);
        try {
            return sQLiteDatabase.insert("ip", null, contentValues);
        } catch (Exception unused) {
            return 0L;
        }
    }

    private long b(SQLiteDatabase sQLiteDatabase, g gVar) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("host_id", Long.valueOf(gVar.f8805i));
        contentValues.put("ip", gVar.f8806o);
        contentValues.put(RemoteMessageConst.TTL, gVar.f8807p);
        try {
            return sQLiteDatabase.insert("ipv6", null, contentValues);
        } catch (Exception unused) {
            return 0L;
        }
    }

    private void c(long j2) {
        SQLiteDatabase writableDatabase = null;
        try {
            writableDatabase = getWritableDatabase();
            writableDatabase.delete("ipv6", "id = ?", new String[]{String.valueOf(j2)});
        } catch (Exception unused) {
            if (writableDatabase == null) {
                return;
            }
        } catch (Throwable th) {
            if (writableDatabase != null) {
                writableDatabase.close();
            }
            throw th;
        }
        writableDatabase.close();
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) throws SQLException {
        try {
            sQLiteDatabase.execSQL("CREATE TABLE host (id INTEGER PRIMARY KEY,host TEXT,sp TEXT,time TEXT,extra TEXT,cache_key TEXT);");
            sQLiteDatabase.execSQL("CREATE TABLE ip (id INTEGER PRIMARY KEY,host_id INTEGER,ip TEXT,ttl TEXT);");
            sQLiteDatabase.execSQL("CREATE TABLE ipv6 (id INTEGER PRIMARY KEY,host_id INTEGER,ip TEXT,ttl TEXT);");
        } catch (Exception unused) {
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i2, int i3) throws SQLException {
        if (i2 != i3) {
            try {
                sQLiteDatabase.beginTransaction();
                sQLiteDatabase.execSQL("DROP TABLE IF EXISTS host;");
                sQLiteDatabase.execSQL("DROP TABLE IF EXISTS ip;");
                sQLiteDatabase.execSQL("DROP TABLE IF EXISTS ipv6;");
                sQLiteDatabase.setTransactionSuccessful();
                sQLiteDatabase.endTransaction();
                onCreate(sQLiteDatabase);
            } catch (Exception unused) {
            }
        }
    }

    private void c(e eVar) {
        m29a(eVar.id);
    }

    /* renamed from: a, reason: collision with other method in class */
    long m31a(e eVar) {
        synchronized (f8800a) {
            try {
                b(eVar.f8803m, eVar.host);
                ContentValues contentValues = new ContentValues();
                SQLiteDatabase sQLiteDatabase = null;
                try {
                    SQLiteDatabase writableDatabase = getWritableDatabase();
                    try {
                        writableDatabase.beginTransaction();
                        contentValues.put("host", eVar.host);
                        contentValues.put(KsProperty.Sp, eVar.f8803m);
                        contentValues.put("time", c.c(eVar.f8804n));
                        contentValues.put(PushConstants.EXTRA, eVar.f8801a);
                        contentValues.put("cache_key", eVar.f8802b);
                        long jInsert = writableDatabase.insert("host", null, contentValues);
                        eVar.id = jInsert;
                        ArrayList<g> arrayList = eVar.f7a;
                        if (arrayList != null) {
                            Iterator<g> it = arrayList.iterator();
                            while (it.hasNext()) {
                                g next = it.next();
                                next.f8805i = jInsert;
                                next.id = a(writableDatabase, next);
                            }
                        }
                        ArrayList<g> arrayList2 = eVar.f8b;
                        if (arrayList2 != null) {
                            Iterator<g> it2 = arrayList2.iterator();
                            while (it2.hasNext()) {
                                g next2 = it2.next();
                                next2.f8805i = jInsert;
                                next2.id = b(writableDatabase, next2);
                            }
                        }
                        writableDatabase.setTransactionSuccessful();
                        writableDatabase.endTransaction();
                        writableDatabase.close();
                        return jInsert;
                    } catch (Exception unused) {
                        sQLiteDatabase = writableDatabase;
                        if (sQLiteDatabase != null) {
                            sQLiteDatabase.endTransaction();
                            sQLiteDatabase.close();
                        }
                        return 0L;
                    } catch (Throwable th) {
                        th = th;
                        sQLiteDatabase = writableDatabase;
                        if (sQLiteDatabase != null) {
                            sQLiteDatabase.endTransaction();
                            sQLiteDatabase.close();
                        }
                        throw th;
                    }
                } catch (Exception unused2) {
                } catch (Throwable th2) {
                    th = th2;
                }
            } finally {
            }
        }
    }

    List<e> b() {
        ArrayList arrayList;
        SQLiteDatabase readableDatabase;
        synchronized (f8800a) {
            try {
                arrayList = new ArrayList();
                Cursor cursorQuery = null;
                try {
                    readableDatabase = getReadableDatabase();
                } catch (Exception unused) {
                    readableDatabase = null;
                } catch (Throwable th) {
                    th = th;
                    readableDatabase = null;
                }
                try {
                    cursorQuery = readableDatabase.query("host", null, null, null, null, null, null);
                    if (cursorQuery != null && cursorQuery.getCount() > 0) {
                        cursorQuery.moveToFirst();
                        do {
                            e eVar = new e();
                            eVar.id = cursorQuery.getInt(cursorQuery.getColumnIndex("id"));
                            eVar.host = cursorQuery.getString(cursorQuery.getColumnIndex("host"));
                            eVar.f8803m = cursorQuery.getString(cursorQuery.getColumnIndex(KsProperty.Sp));
                            eVar.f8804n = c.d(cursorQuery.getString(cursorQuery.getColumnIndex("time")));
                            eVar.f7a = (ArrayList) a(eVar);
                            eVar.f8b = (ArrayList) b(eVar);
                            eVar.f8801a = cursorQuery.getString(cursorQuery.getColumnIndex(PushConstants.EXTRA));
                            eVar.f8802b = cursorQuery.getString(cursorQuery.getColumnIndex("cache_key"));
                            arrayList.add(eVar);
                        } while (cursorQuery.moveToNext());
                    }
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                } catch (Exception unused2) {
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    if (readableDatabase != null) {
                        readableDatabase.close();
                    }
                    return arrayList;
                } catch (Throwable th2) {
                    th = th2;
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    if (readableDatabase != null) {
                        readableDatabase.close();
                    }
                    throw th;
                }
                readableDatabase.close();
            } catch (Throwable th3) {
                throw th3;
            }
        }
        return arrayList;
    }

    private List<g> b(long j2) throws Throwable {
        SQLiteDatabase writableDatabase;
        ArrayList arrayList = new ArrayList();
        Cursor cursorQuery = null;
        try {
            writableDatabase = getWritableDatabase();
            try {
                cursorQuery = writableDatabase.query("ipv6", null, "host_id=?", new String[]{String.valueOf(j2)}, null, null, null);
                if (cursorQuery != null && cursorQuery.getCount() > 0) {
                    cursorQuery.moveToFirst();
                    do {
                        g gVar = new g();
                        gVar.id = cursorQuery.getInt(cursorQuery.getColumnIndex("id"));
                        gVar.f8805i = cursorQuery.getInt(cursorQuery.getColumnIndex("host_id"));
                        gVar.f8806o = cursorQuery.getString(cursorQuery.getColumnIndex("ip"));
                        gVar.f8807p = cursorQuery.getString(cursorQuery.getColumnIndex(RemoteMessageConst.TTL));
                        arrayList.add(gVar);
                    } while (cursorQuery.moveToNext());
                }
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
            } catch (Exception unused) {
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                if (writableDatabase != null) {
                    writableDatabase.close();
                }
                return arrayList;
            } catch (Throwable th) {
                th = th;
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                if (writableDatabase != null) {
                    writableDatabase.close();
                }
                throw th;
            }
        } catch (Exception unused2) {
            writableDatabase = null;
        } catch (Throwable th2) {
            th = th2;
            writableDatabase = null;
        }
        writableDatabase.close();
        return arrayList;
    }

    e a(String str, String str2) {
        SQLiteDatabase readableDatabase;
        e eVar;
        e eVar2;
        synchronized (f8800a) {
            Cursor cursor = null;
            eVar2 = null;
            eVar2 = null;
            cursor = null;
            cursor = null;
            Cursor cursor2 = null;
            try {
                try {
                    readableDatabase = getReadableDatabase();
                    try {
                        Cursor cursorQuery = readableDatabase.query("host", null, "sp=? AND host=?", new String[]{str, str2}, null, null, null);
                        if (cursorQuery != null) {
                            try {
                                try {
                                    if (cursorQuery.getCount() > 0) {
                                        cursorQuery.moveToFirst();
                                        eVar = new e();
                                        try {
                                            eVar.id = cursorQuery.getInt(cursorQuery.getColumnIndex("id"));
                                            eVar.host = cursorQuery.getString(cursorQuery.getColumnIndex("host"));
                                            eVar.f8803m = cursorQuery.getString(cursorQuery.getColumnIndex(KsProperty.Sp));
                                            eVar.f8804n = c.d(cursorQuery.getString(cursorQuery.getColumnIndex("time")));
                                            eVar.f7a = (ArrayList) a(eVar);
                                            eVar.f8b = (ArrayList) b(eVar);
                                            eVar.f8801a = cursorQuery.getString(cursorQuery.getColumnIndex(PushConstants.EXTRA));
                                            eVar.f8802b = cursorQuery.getString(cursorQuery.getColumnIndex("cache_key"));
                                            eVar2 = eVar;
                                        } catch (Exception unused) {
                                            cursor2 = cursorQuery;
                                            if (cursor2 != null) {
                                                cursor2.close();
                                            }
                                            if (readableDatabase != null) {
                                                readableDatabase.close();
                                            }
                                            eVar2 = eVar;
                                            return eVar2;
                                        }
                                    }
                                } catch (Throwable th) {
                                    th = th;
                                    cursor = cursorQuery;
                                    if (cursor != null) {
                                        cursor.close();
                                    }
                                    if (readableDatabase != null) {
                                        readableDatabase.close();
                                    }
                                    throw th;
                                }
                            } catch (Exception unused2) {
                                eVar = null;
                            }
                        }
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        readableDatabase.close();
                    } catch (Exception unused3) {
                        eVar = null;
                    } catch (Throwable th2) {
                        th = th2;
                    }
                } catch (Exception unused4) {
                    eVar = null;
                    readableDatabase = null;
                } catch (Throwable th3) {
                    th = th3;
                    readableDatabase = null;
                }
            } finally {
            }
        }
        return eVar2;
    }

    private List<g> a(long j2) throws Throwable {
        SQLiteDatabase writableDatabase;
        ArrayList arrayList = new ArrayList();
        Cursor cursorQuery = null;
        try {
            writableDatabase = getWritableDatabase();
            try {
                cursorQuery = writableDatabase.query("ip", null, "host_id=?", new String[]{String.valueOf(j2)}, null, null, null);
                if (cursorQuery != null && cursorQuery.getCount() > 0) {
                    cursorQuery.moveToFirst();
                    do {
                        g gVar = new g();
                        gVar.id = cursorQuery.getInt(cursorQuery.getColumnIndex("id"));
                        gVar.f8805i = cursorQuery.getInt(cursorQuery.getColumnIndex("host_id"));
                        gVar.f8806o = cursorQuery.getString(cursorQuery.getColumnIndex("ip"));
                        gVar.f8807p = cursorQuery.getString(cursorQuery.getColumnIndex(RemoteMessageConst.TTL));
                        arrayList.add(gVar);
                    } while (cursorQuery.moveToNext());
                }
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
            } catch (Exception unused) {
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                if (writableDatabase != null) {
                    writableDatabase.close();
                }
                return arrayList;
            } catch (Throwable th) {
                th = th;
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                if (writableDatabase != null) {
                    writableDatabase.close();
                }
                throw th;
            }
        } catch (Exception unused2) {
            writableDatabase = null;
        } catch (Throwable th2) {
            th = th2;
            writableDatabase = null;
        }
        writableDatabase.close();
        return arrayList;
    }

    private List<g> b(e eVar) {
        return b(eVar.id);
    }

    private List<g> a(e eVar) {
        return a(eVar.id);
    }

    /* renamed from: b, reason: collision with other method in class */
    private void m30b(long j2) {
        SQLiteDatabase writableDatabase = null;
        try {
            writableDatabase = getWritableDatabase();
            writableDatabase.delete("ip", "id = ?", new String[]{String.valueOf(j2)});
        } catch (Exception unused) {
            if (writableDatabase == null) {
                return;
            }
        } catch (Throwable th) {
            if (writableDatabase != null) {
                writableDatabase.close();
            }
            throw th;
        }
        writableDatabase.close();
    }

    /* renamed from: a, reason: collision with other method in class */
    private void m29a(long j2) {
        SQLiteDatabase writableDatabase = null;
        try {
            writableDatabase = getWritableDatabase();
            writableDatabase.delete("host", "id = ?", new String[]{String.valueOf(j2)});
        } catch (Exception unused) {
            if (writableDatabase == null) {
                return;
            }
        } catch (Throwable th) {
            if (writableDatabase != null) {
                writableDatabase.close();
            }
            throw th;
        }
        writableDatabase.close();
    }

    private void b(g gVar) {
        c(gVar.id);
    }

    private void a(g gVar) {
        m30b(gVar.id);
    }

    void b(String str, String str2) {
        synchronized (f8800a) {
            try {
                e eVarA = a(str, str2);
                if (eVarA != null) {
                    c(eVarA);
                    ArrayList<g> arrayList = eVarA.f7a;
                    if (arrayList != null) {
                        Iterator<g> it = arrayList.iterator();
                        while (it.hasNext()) {
                            a(it.next());
                        }
                    }
                    ArrayList<g> arrayList2 = eVarA.f8b;
                    if (arrayList2 != null) {
                        Iterator<g> it2 = arrayList2.iterator();
                        while (it2.hasNext()) {
                            b(it2.next());
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
