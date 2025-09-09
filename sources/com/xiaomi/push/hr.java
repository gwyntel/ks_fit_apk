package com.xiaomi.push;

import android.os.Bundle;
import android.text.TextUtils;

/* loaded from: classes4.dex */
public class hr extends hs {

    /* renamed from: a, reason: collision with root package name */
    private boolean f23899a;

    /* renamed from: b, reason: collision with root package name */
    private String f23900b;

    /* renamed from: b, reason: collision with other field name */
    private boolean f548b;

    /* renamed from: c, reason: collision with root package name */
    private String f23901c;

    /* renamed from: d, reason: collision with root package name */
    private String f23902d;

    /* renamed from: e, reason: collision with root package name */
    private String f23903e;

    /* renamed from: f, reason: collision with root package name */
    private String f23904f;

    /* renamed from: g, reason: collision with root package name */
    private String f23905g;

    /* renamed from: h, reason: collision with root package name */
    private String f23906h;

    /* renamed from: i, reason: collision with root package name */
    private String f23907i;

    /* renamed from: j, reason: collision with root package name */
    private String f23908j;

    /* renamed from: k, reason: collision with root package name */
    private String f23909k;

    /* renamed from: l, reason: collision with root package name */
    private String f23910l;

    public hr() {
        this.f23900b = null;
        this.f23901c = null;
        this.f23899a = false;
        this.f23907i = "";
        this.f23908j = "";
        this.f23909k = "";
        this.f23910l = "";
        this.f548b = false;
    }

    public void a(boolean z2) {
        this.f23899a = z2;
    }

    public String b() {
        return this.f23900b;
    }

    public String c() {
        return this.f23906h;
    }

    public String d() {
        return this.f23907i;
    }

    public String e() {
        return this.f23908j;
    }

    @Override // com.xiaomi.push.hs
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        hr hrVar = (hr) obj;
        if (!super.equals(hrVar)) {
            return false;
        }
        String str = this.f23904f;
        if (str == null ? hrVar.f23904f != null : !str.equals(hrVar.f23904f)) {
            return false;
        }
        String str2 = this.f23902d;
        if (str2 == null ? hrVar.f23902d != null : !str2.equals(hrVar.f23902d)) {
            return false;
        }
        String str3 = this.f23903e;
        if (str3 == null ? hrVar.f23903e != null : !str3.equals(hrVar.f23903e)) {
            return false;
        }
        String str4 = this.f23901c;
        if (str4 == null ? hrVar.f23901c == null : str4.equals(hrVar.f23901c)) {
            return this.f23900b == hrVar.f23900b;
        }
        return false;
    }

    public String f() {
        return this.f23909k;
    }

    public String g() {
        return this.f23910l;
    }

    public void h(String str) {
        this.f23904f = str;
    }

    @Override // com.xiaomi.push.hs
    public int hashCode() {
        String str = this.f23900b;
        int iHashCode = (str != null ? str.hashCode() : 0) * 31;
        String str2 = this.f23904f;
        int iHashCode2 = (iHashCode + (str2 != null ? str2.hashCode() : 0)) * 31;
        String str3 = this.f23901c;
        int iHashCode3 = (iHashCode2 + (str3 != null ? str3.hashCode() : 0)) * 31;
        String str4 = this.f23902d;
        int iHashCode4 = (iHashCode3 + (str4 != null ? str4.hashCode() : 0)) * 31;
        String str5 = this.f23903e;
        return iHashCode4 + (str5 != null ? str5.hashCode() : 0);
    }

    public void i(String str) {
        this.f23901c = str;
    }

    public void j(String str) {
        this.f23902d = str;
    }

    public void a(String str) {
        this.f23906h = str;
    }

    public void b(String str) {
        this.f23907i = str;
    }

    public void c(String str) {
        this.f23908j = str;
    }

    public void d(String str) {
        this.f23909k = str;
    }

    public void e(String str) {
        this.f23910l = str;
    }

    public void f(String str) {
        this.f23900b = str;
    }

    public void g(String str) {
        this.f23903e = str;
    }

    public String h() {
        return this.f23902d;
    }

    public void a(String str, String str2) {
        this.f23904f = str;
        this.f23905g = str2;
    }

    public void b(boolean z2) {
        this.f548b = z2;
    }

    @Override // com.xiaomi.push.hs
    public Bundle a() {
        Bundle bundleA = super.a();
        if (!TextUtils.isEmpty(this.f23900b)) {
            bundleA.putString("ext_msg_type", this.f23900b);
        }
        String str = this.f23902d;
        if (str != null) {
            bundleA.putString("ext_msg_lang", str);
        }
        String str2 = this.f23903e;
        if (str2 != null) {
            bundleA.putString("ext_msg_sub", str2);
        }
        String str3 = this.f23904f;
        if (str3 != null) {
            bundleA.putString("ext_msg_body", str3);
        }
        if (!TextUtils.isEmpty(this.f23905g)) {
            bundleA.putString("ext_body_encode", this.f23905g);
        }
        String str4 = this.f23901c;
        if (str4 != null) {
            bundleA.putString("ext_msg_thread", str4);
        }
        String str5 = this.f23906h;
        if (str5 != null) {
            bundleA.putString("ext_msg_appid", str5);
        }
        if (this.f23899a) {
            bundleA.putBoolean("ext_msg_trans", true);
        }
        if (!TextUtils.isEmpty(this.f23907i)) {
            bundleA.putString("ext_msg_seq", this.f23907i);
        }
        if (!TextUtils.isEmpty(this.f23908j)) {
            bundleA.putString("ext_msg_mseq", this.f23908j);
        }
        if (!TextUtils.isEmpty(this.f23909k)) {
            bundleA.putString("ext_msg_fseq", this.f23909k);
        }
        if (this.f548b) {
            bundleA.putBoolean("ext_msg_encrypt", true);
        }
        if (!TextUtils.isEmpty(this.f23910l)) {
            bundleA.putString("ext_msg_status", this.f23910l);
        }
        return bundleA;
    }

    public hr(Bundle bundle) {
        super(bundle);
        this.f23900b = null;
        this.f23901c = null;
        this.f23899a = false;
        this.f23907i = "";
        this.f23908j = "";
        this.f23909k = "";
        this.f23910l = "";
        this.f548b = false;
        this.f23900b = bundle.getString("ext_msg_type");
        this.f23902d = bundle.getString("ext_msg_lang");
        this.f23901c = bundle.getString("ext_msg_thread");
        this.f23903e = bundle.getString("ext_msg_sub");
        this.f23904f = bundle.getString("ext_msg_body");
        this.f23905g = bundle.getString("ext_body_encode");
        this.f23906h = bundle.getString("ext_msg_appid");
        this.f23899a = bundle.getBoolean("ext_msg_trans", false);
        this.f548b = bundle.getBoolean("ext_msg_encrypt", false);
        this.f23907i = bundle.getString("ext_msg_seq");
        this.f23908j = bundle.getString("ext_msg_mseq");
        this.f23909k = bundle.getString("ext_msg_fseq");
        this.f23910l = bundle.getString("ext_msg_status");
    }

    @Override // com.xiaomi.push.hs
    /* renamed from: a */
    public String mo485a() {
        hw hwVarM486a;
        StringBuilder sb = new StringBuilder();
        sb.append("<message");
        if (p() != null) {
            sb.append(" xmlns=\"");
            sb.append(p());
            sb.append("\"");
        }
        if (this.f23902d != null) {
            sb.append(" xml:lang=\"");
            sb.append(h());
            sb.append("\"");
        }
        if (j() != null) {
            sb.append(" id=\"");
            sb.append(j());
            sb.append("\"");
        }
        if (l() != null) {
            sb.append(" to=\"");
            sb.append(id.a(l()));
            sb.append("\"");
        }
        if (!TextUtils.isEmpty(d())) {
            sb.append(" seq=\"");
            sb.append(d());
            sb.append("\"");
        }
        if (!TextUtils.isEmpty(e())) {
            sb.append(" mseq=\"");
            sb.append(e());
            sb.append("\"");
        }
        if (!TextUtils.isEmpty(f())) {
            sb.append(" fseq=\"");
            sb.append(f());
            sb.append("\"");
        }
        if (!TextUtils.isEmpty(g())) {
            sb.append(" status=\"");
            sb.append(g());
            sb.append("\"");
        }
        if (m() != null) {
            sb.append(" from=\"");
            sb.append(id.a(m()));
            sb.append("\"");
        }
        if (k() != null) {
            sb.append(" chid=\"");
            sb.append(id.a(k()));
            sb.append("\"");
        }
        if (this.f23899a) {
            sb.append(" transient=\"true\"");
        }
        if (!TextUtils.isEmpty(this.f23906h)) {
            sb.append(" appid=\"");
            sb.append(c());
            sb.append("\"");
        }
        if (!TextUtils.isEmpty(this.f23900b)) {
            sb.append(" type=\"");
            sb.append(this.f23900b);
            sb.append("\"");
        }
        if (this.f548b) {
            sb.append(" s=\"1\"");
        }
        sb.append(">");
        if (this.f23903e != null) {
            sb.append("<subject>");
            sb.append(id.a(this.f23903e));
            sb.append("</subject>");
        }
        if (this.f23904f != null) {
            sb.append("<body");
            if (!TextUtils.isEmpty(this.f23905g)) {
                sb.append(" encode=\"");
                sb.append(this.f23905g);
                sb.append("\"");
            }
            sb.append(">");
            sb.append(id.a(this.f23904f));
            sb.append("</body>");
        }
        if (this.f23901c != null) {
            sb.append("<thread>");
            sb.append(this.f23901c);
            sb.append("</thread>");
        }
        if ("error".equalsIgnoreCase(this.f23900b) && (hwVarM486a = m486a()) != null) {
            sb.append(hwVarM486a.m489a());
        }
        sb.append(o());
        sb.append("</message>");
        return sb.toString();
    }
}
