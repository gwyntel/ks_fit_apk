package com.meizu.cloud.pushsdk.f.e;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import com.meizu.cloud.pushinternal.DebugLogger;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class a implements d {

    /* renamed from: a, reason: collision with root package name */
    private static final String f19610a = "a";

    /* renamed from: b, reason: collision with root package name */
    private SQLiteDatabase f19611b;

    /* renamed from: c, reason: collision with root package name */
    private final b f19612c;

    /* renamed from: d, reason: collision with root package name */
    private final String[] f19613d = {"id", "eventData", "dateCreated"};

    /* renamed from: e, reason: collision with root package name */
    private long f19614e = -1;

    /* renamed from: f, reason: collision with root package name */
    private final int f19615f;

    public a(Context context, int i2) {
        this.f19612c = b.a(context, a(context));
        e();
        this.f19615f = i2;
    }

    private String a(Context context) {
        String str = null;
        try {
            str = (String) Class.forName("com.meizu.cloud.utils.ProcessUtils").getDeclaredMethod("getCurrentProcessName", Context.class).invoke(null, context);
        } catch (Exception e2) {
            DebugLogger.e(f19610a, "getCurrentProcessName error " + e2.getMessage());
        }
        if (TextUtils.isEmpty(str)) {
            return "PushEvents.db";
        }
        return str + OpenAccountUIConstants.UNDER_LINE + "PushEvents.db";
    }

    @Override // com.meizu.cloud.pushsdk.f.e.d
    public long b() {
        if (d()) {
            return DatabaseUtils.queryNumEntries(this.f19611b, "events");
        }
        return 0L;
    }

    @Override // com.meizu.cloud.pushsdk.f.e.d
    public com.meizu.cloud.pushsdk.f.c.c c() {
        LinkedList linkedList = new LinkedList();
        ArrayList arrayList = new ArrayList();
        for (Map<String, Object> map : a(this.f19615f)) {
            com.meizu.cloud.pushsdk.f.b.c cVar = new com.meizu.cloud.pushsdk.f.b.c();
            cVar.a((Map) map.get("eventData"));
            linkedList.add((Long) map.get("id"));
            arrayList.add(cVar);
        }
        return new com.meizu.cloud.pushsdk.f.c.c(arrayList, linkedList);
    }

    public boolean d() {
        SQLiteDatabase sQLiteDatabase = this.f19611b;
        return sQLiteDatabase != null && sQLiteDatabase.isOpen();
    }

    public void e() {
        if (d()) {
            return;
        }
        try {
            SQLiteDatabase writableDatabase = this.f19612c.getWritableDatabase();
            this.f19611b = writableDatabase;
            writableDatabase.enableWriteAheadLogging();
        } catch (Exception e2) {
            com.meizu.cloud.pushsdk.f.g.c.b(f19610a, " open database error " + e2.getMessage(), new Object[0]);
        }
    }

    public List<Map<String, Object>> a(int i2) {
        return a(null, "id ASC LIMIT " + i2);
    }

    public long b(com.meizu.cloud.pushsdk.f.b.a aVar) throws IOException {
        if (d()) {
            byte[] bArrA = a((Map<String, String>) aVar.a());
            ContentValues contentValues = new ContentValues(2);
            contentValues.put("eventData", bArrA);
            this.f19614e = this.f19611b.insert("events", null, contentValues);
        }
        com.meizu.cloud.pushsdk.f.g.c.a(f19610a, "Added event to database: " + this.f19614e, new Object[0]);
        return this.f19614e;
    }

    public List<Map<String, Object>> a(String str, String str2) {
        ArrayList arrayList = new ArrayList();
        if (d()) {
            Cursor cursorQuery = this.f19611b.query("events", this.f19613d, str, null, null, null, str2);
            cursorQuery.moveToFirst();
            while (!cursorQuery.isAfterLast()) {
                HashMap map = new HashMap(4);
                map.put("id", Long.valueOf(cursorQuery.getLong(0)));
                map.put("eventData", a(cursorQuery.getBlob(1)));
                map.put("dateCreated", cursorQuery.getString(2));
                cursorQuery.moveToNext();
                arrayList.add(map);
            }
            cursorQuery.close();
        }
        return arrayList;
    }

    public static Map<String, String> a(byte[] bArr) throws IOException {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            HashMap map = (HashMap) objectInputStream.readObject();
            objectInputStream.close();
            byteArrayInputStream.close();
            return map;
        } catch (IOException | ClassNotFoundException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    @Override // com.meizu.cloud.pushsdk.f.e.d
    public void a(com.meizu.cloud.pushsdk.f.b.a aVar) throws IOException {
        b(aVar);
    }

    @Override // com.meizu.cloud.pushsdk.f.e.d
    public boolean a() {
        return d();
    }

    @Override // com.meizu.cloud.pushsdk.f.e.d
    public boolean a(long j2) {
        int iDelete;
        if (d()) {
            iDelete = this.f19611b.delete("events", "id=" + j2, null);
        } else {
            iDelete = -1;
        }
        com.meizu.cloud.pushsdk.f.g.c.a(f19610a, "Removed event from database: " + j2, new Object[0]);
        return iDelete == 1;
    }

    public static byte[] a(Map<String, String> map) throws IOException {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(map);
            objectOutputStream.close();
            byteArrayOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e2) {
            e2.printStackTrace();
            return null;
        }
    }
}
