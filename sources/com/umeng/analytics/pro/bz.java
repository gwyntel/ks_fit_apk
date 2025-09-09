package com.umeng.analytics.pro;

import com.umeng.analytics.pro.cj;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

/* loaded from: classes4.dex */
public class bz {

    /* renamed from: a, reason: collision with root package name */
    private final ByteArrayOutputStream f21544a;

    /* renamed from: b, reason: collision with root package name */
    private final db f21545b;

    /* renamed from: c, reason: collision with root package name */
    private cp f21546c;

    public bz() {
        this(new cj.a());
    }

    public byte[] a(bq bqVar) throws bw {
        this.f21544a.reset();
        bqVar.write(this.f21546c);
        return this.f21544a.toByteArray();
    }

    public String b(bq bqVar) throws bw {
        return new String(a(bqVar));
    }

    public bz(cr crVar) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        this.f21544a = byteArrayOutputStream;
        db dbVar = new db(byteArrayOutputStream);
        this.f21545b = dbVar;
        this.f21546c = crVar.a(dbVar);
    }

    public String a(bq bqVar, String str) throws bw {
        try {
            return new String(a(bqVar), str);
        } catch (UnsupportedEncodingException unused) {
            throw new bw("JVM DOES NOT SUPPORT ENCODING: " + str);
        }
    }
}
