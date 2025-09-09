package com.umeng.message.proguard;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import com.umeng.message.common.UPLog;

/* loaded from: classes4.dex */
public final class ao {

    /* renamed from: b, reason: collision with root package name */
    private static ao f22778b;

    /* renamed from: a, reason: collision with root package name */
    public final Context f22779a;

    private ao(Context context) {
        this.f22779a = context.getApplicationContext();
    }

    public static ao a(Context context) {
        if (f22778b == null) {
            f22778b = new ao(context);
        }
        return f22778b;
    }

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        public String f22780a;

        /* renamed from: b, reason: collision with root package name */
        public long f22781b;

        /* renamed from: c, reason: collision with root package name */
        public int f22782c;

        public a(String str, int i2, long j2) {
            this.f22780a = str;
            this.f22782c = i2;
            this.f22781b = j2;
        }

        public final ContentValues a() {
            ContentValues contentValues = new ContentValues();
            contentValues.put("MsgId", this.f22780a);
            contentValues.put("Time", Long.valueOf(this.f22781b));
            contentValues.put("ActionType", Integer.valueOf(this.f22782c));
            return contentValues;
        }

        public a(Cursor cursor) {
            this.f22780a = cursor.getString(cursor.getColumnIndex("MsgId"));
            this.f22781b = cursor.getLong(cursor.getColumnIndex("Time"));
            this.f22782c = cursor.getInt(cursor.getColumnIndex("ActionType"));
        }
    }

    public final void a(String str, int i2, long j2) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        try {
            this.f22779a.getContentResolver().insert(h.d(this.f22779a), new a(str, i2, j2).a());
        } catch (Exception e2) {
            UPLog.e("MsgLog", e2);
        }
    }
}
