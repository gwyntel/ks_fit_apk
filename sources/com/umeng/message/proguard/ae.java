package com.umeng.message.proguard;

import android.content.ContentValues;
import android.database.Cursor;

/* loaded from: classes4.dex */
public final class ae {

    /* renamed from: a, reason: collision with root package name */
    long f22713a;

    /* renamed from: b, reason: collision with root package name */
    String f22714b;

    /* renamed from: c, reason: collision with root package name */
    int f22715c;

    /* renamed from: d, reason: collision with root package name */
    public int f22716d;

    /* renamed from: e, reason: collision with root package name */
    public int f22717e;

    /* renamed from: f, reason: collision with root package name */
    public int f22718f;

    /* renamed from: g, reason: collision with root package name */
    public int f22719g;

    /* renamed from: h, reason: collision with root package name */
    public int f22720h;

    /* renamed from: i, reason: collision with root package name */
    public int f22721i;

    /* renamed from: j, reason: collision with root package name */
    public int f22722j;

    public ae(String str, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
        this.f22713a = System.currentTimeMillis();
        this.f22714b = str;
        this.f22715c = i2;
        this.f22716d = i3;
        this.f22717e = i4;
        this.f22718f = i5;
        this.f22719g = i6;
        this.f22720h = i7;
        this.f22721i = i8;
        this.f22722j = i9;
    }

    public final ContentValues a() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("Time", Long.valueOf(this.f22713a));
        contentValues.put("MsgId", this.f22714b);
        contentValues.put("MsgType", Integer.valueOf(this.f22715c));
        contentValues.put("NumDisplay", Integer.valueOf(this.f22716d));
        contentValues.put("NumOpenFull", Integer.valueOf(this.f22717e));
        contentValues.put("NumOpenTop", Integer.valueOf(this.f22718f));
        contentValues.put("NumOpenBottom", Integer.valueOf(this.f22719g));
        contentValues.put("NumClose", Integer.valueOf(this.f22720h));
        contentValues.put("NumDuration", Integer.valueOf(this.f22721i));
        contentValues.put("NumCustom", Integer.valueOf(this.f22722j));
        return contentValues;
    }

    public ae(Cursor cursor) {
        this.f22714b = cursor.getString(cursor.getColumnIndex("MsgId"));
        this.f22715c = cursor.getInt(cursor.getColumnIndex("MsgType"));
        this.f22716d = cursor.getInt(cursor.getColumnIndex("NumDisplay"));
        this.f22717e = cursor.getInt(cursor.getColumnIndex("NumOpenFull"));
        this.f22718f = cursor.getInt(cursor.getColumnIndex("NumOpenTop"));
        this.f22719g = cursor.getInt(cursor.getColumnIndex("NumOpenBottom"));
        this.f22720h = cursor.getInt(cursor.getColumnIndex("NumClose"));
        this.f22721i = cursor.getInt(cursor.getColumnIndex("NumDuration"));
        this.f22722j = cursor.getInt(cursor.getColumnIndex("NumCustom"));
    }
}
