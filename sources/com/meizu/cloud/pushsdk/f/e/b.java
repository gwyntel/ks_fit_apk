package com.meizu.cloud.pushsdk.f.e;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/* loaded from: classes4.dex */
public class b extends SQLiteOpenHelper {

    /* renamed from: a, reason: collision with root package name */
    private static final String f19616a = "com.meizu.cloud.pushsdk.f.e.b";

    /* renamed from: b, reason: collision with root package name */
    private static b f19617b;

    private b(Context context, String str) {
        super(context, str, (SQLiteDatabase.CursorFactory) null, 1);
    }

    public static b a(Context context, String str) {
        if (f19617b == null) {
            f19617b = new b(context.getApplicationContext(), str);
        }
        return f19617b;
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) throws SQLException {
        sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS 'events' (id INTEGER PRIMARY KEY, eventData BLOB, dateCreated TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i2, int i3) throws SQLException {
        com.meizu.cloud.pushsdk.f.g.c.a(f19616a, "Upgrading database from version " + i2 + " to " + i3 + ". Destroying old data now..", new Object[0]);
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS 'events'");
        onCreate(sQLiteDatabase);
    }
}
