package com.xiaomi.push;

import android.os.Bundle;

/* loaded from: classes4.dex */
public class hu extends hs {

    /* renamed from: a, reason: collision with root package name */
    private int f23920a;

    /* renamed from: a, reason: collision with other field name */
    private a f555a;

    /* renamed from: a, reason: collision with other field name */
    private b f556a;

    /* renamed from: b, reason: collision with root package name */
    private String f23921b;

    public enum a {
        chat,
        available,
        away,
        xa,
        dnd
    }

    public enum b {
        available,
        unavailable,
        subscribe,
        subscribed,
        unsubscribe,
        unsubscribed,
        error,
        probe
    }

    public hu(b bVar) {
        this.f556a = b.available;
        this.f23921b = null;
        this.f23920a = Integer.MIN_VALUE;
        this.f555a = null;
        a(bVar);
    }

    @Override // com.xiaomi.push.hs
    public Bundle a() {
        Bundle bundleA = super.a();
        b bVar = this.f556a;
        if (bVar != null) {
            bundleA.putString("ext_pres_type", bVar.toString());
        }
        String str = this.f23921b;
        if (str != null) {
            bundleA.putString("ext_pres_status", str);
        }
        int i2 = this.f23920a;
        if (i2 != Integer.MIN_VALUE) {
            bundleA.putInt("ext_pres_prio", i2);
        }
        a aVar = this.f555a;
        if (aVar != null && aVar != a.available) {
            bundleA.putString("ext_pres_mode", aVar.toString());
        }
        return bundleA;
    }

    public hu(Bundle bundle) {
        super(bundle);
        this.f556a = b.available;
        this.f23921b = null;
        this.f23920a = Integer.MIN_VALUE;
        this.f555a = null;
        if (bundle.containsKey("ext_pres_type")) {
            this.f556a = b.valueOf(bundle.getString("ext_pres_type"));
        }
        if (bundle.containsKey("ext_pres_status")) {
            this.f23921b = bundle.getString("ext_pres_status");
        }
        if (bundle.containsKey("ext_pres_prio")) {
            this.f23920a = bundle.getInt("ext_pres_prio");
        }
        if (bundle.containsKey("ext_pres_mode")) {
            this.f555a = a.valueOf(bundle.getString("ext_pres_mode"));
        }
    }

    public void a(b bVar) {
        if (bVar != null) {
            this.f556a = bVar;
            return;
        }
        throw new NullPointerException("Type cannot be null");
    }

    public void a(String str) {
        this.f23921b = str;
    }

    public void a(int i2) {
        if (i2 >= -128 && i2 <= 128) {
            this.f23920a = i2;
            return;
        }
        throw new IllegalArgumentException("Priority value " + i2 + " is not valid. Valid range is -128 through 128.");
    }

    public void a(a aVar) {
        this.f555a = aVar;
    }

    @Override // com.xiaomi.push.hs
    /* renamed from: a */
    public String mo485a() {
        StringBuilder sb = new StringBuilder();
        sb.append("<presence");
        if (p() != null) {
            sb.append(" xmlns=\"");
            sb.append(p());
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
        if (this.f556a != null) {
            sb.append(" type=\"");
            sb.append(this.f556a);
            sb.append("\"");
        }
        sb.append(">");
        if (this.f23921b != null) {
            sb.append("<status>");
            sb.append(id.a(this.f23921b));
            sb.append("</status>");
        }
        if (this.f23920a != Integer.MIN_VALUE) {
            sb.append("<priority>");
            sb.append(this.f23920a);
            sb.append("</priority>");
        }
        a aVar = this.f555a;
        if (aVar != null && aVar != a.available) {
            sb.append("<show>");
            sb.append(this.f555a);
            sb.append("</show>");
        }
        sb.append(o());
        hw hwVarM486a = m486a();
        if (hwVarM486a != null) {
            sb.append(hwVarM486a.m489a());
        }
        sb.append("</presence>");
        return sb.toString();
    }
}
