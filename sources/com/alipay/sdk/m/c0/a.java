package com.alipay.sdk.m.c0;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/* loaded from: classes2.dex */
public final class a {

    /* renamed from: a, reason: collision with root package name */
    public String f9211a;

    /* renamed from: b, reason: collision with root package name */
    public String f9212b;

    /* renamed from: c, reason: collision with root package name */
    public String f9213c;

    /* renamed from: d, reason: collision with root package name */
    public String f9214d;

    /* renamed from: e, reason: collision with root package name */
    public String f9215e;

    /* renamed from: f, reason: collision with root package name */
    public String f9216f;

    /* renamed from: g, reason: collision with root package name */
    public String f9217g;

    public a(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        this.f9211a = str;
        this.f9212b = str2;
        this.f9213c = str3;
        this.f9214d = str4;
        this.f9215e = str5;
        this.f9216f = str6;
        this.f9217g = str7;
    }

    public final String toString() {
        StringBuilder sb;
        String strSubstring;
        StringBuilder sb2;
        String strSubstring2;
        StringBuilder sb3;
        String strSubstring3;
        StringBuffer stringBuffer = new StringBuffer(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(Calendar.getInstance().getTime()));
        stringBuffer.append("," + this.f9211a);
        stringBuffer.append("," + this.f9212b);
        stringBuffer.append("," + this.f9213c);
        stringBuffer.append("," + this.f9214d);
        if (com.alipay.sdk.m.z.a.a(this.f9215e) || this.f9215e.length() < 20) {
            sb = new StringBuilder(",");
            strSubstring = this.f9215e;
        } else {
            sb = new StringBuilder(",");
            strSubstring = this.f9215e.substring(0, 20);
        }
        sb.append(strSubstring);
        stringBuffer.append(sb.toString());
        if (com.alipay.sdk.m.z.a.a(this.f9216f) || this.f9216f.length() < 20) {
            sb2 = new StringBuilder(",");
            strSubstring2 = this.f9216f;
        } else {
            sb2 = new StringBuilder(",");
            strSubstring2 = this.f9216f.substring(0, 20);
        }
        sb2.append(strSubstring2);
        stringBuffer.append(sb2.toString());
        if (com.alipay.sdk.m.z.a.a(this.f9217g) || this.f9217g.length() < 20) {
            sb3 = new StringBuilder(",");
            strSubstring3 = this.f9217g;
        } else {
            sb3 = new StringBuilder(",");
            strSubstring3 = this.f9217g.substring(0, 20);
        }
        sb3.append(strSubstring3);
        stringBuffer.append(sb3.toString());
        return stringBuffer.toString();
    }
}
