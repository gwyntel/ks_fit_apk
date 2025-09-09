package com.alibaba.sdk.android.oss.common;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/* loaded from: classes2.dex */
public class OSSSQLiteHelper extends SQLiteOpenHelper {
    private static final String CREATE_TABLE_PART_INFO = "create table if not exists part_info(id INTEGER primary key,upload_id VARCHAR(255),num INTEGER,crc64 INTEGER,size INTEGER,etag VARCHAR(255))";
    public static final String TABLE_NAME_PART_INFO = "part_info";

    public OSSSQLiteHelper(Context context) {
        this(context, "oss_android_sdk.db", null, 1);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) throws SQLException {
        sQLiteDatabase.execSQL(CREATE_TABLE_PART_INFO);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i2, int i3) {
    }

    public OSSSQLiteHelper(Context context, String str, SQLiteDatabase.CursorFactory cursorFactory, int i2) {
        super(context, str, cursorFactory, i2);
    }
}
