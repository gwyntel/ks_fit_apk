package com.xiaomi.push;

import android.os.Bundle;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class hq extends hs {

    /* renamed from: a, reason: collision with root package name */
    private a f23893a;

    /* renamed from: a, reason: collision with other field name */
    private final Map<String, String> f546a;

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        public static final a f23894a = new a(TmpConstant.PROPERTY_IDENTIFIER_GET);

        /* renamed from: b, reason: collision with root package name */
        public static final a f23895b = new a(TmpConstant.PROPERTY_IDENTIFIER_SET);

        /* renamed from: c, reason: collision with root package name */
        public static final a f23896c = new a("result");

        /* renamed from: d, reason: collision with root package name */
        public static final a f23897d = new a("error");

        /* renamed from: e, reason: collision with root package name */
        public static final a f23898e = new a("command");

        /* renamed from: a, reason: collision with other field name */
        private String f547a;

        private a(String str) {
            this.f547a = str;
        }

        public static a a(String str) {
            if (str == null) {
                return null;
            }
            String lowerCase = str.toLowerCase();
            a aVar = f23894a;
            if (aVar.toString().equals(lowerCase)) {
                return aVar;
            }
            a aVar2 = f23895b;
            if (aVar2.toString().equals(lowerCase)) {
                return aVar2;
            }
            a aVar3 = f23897d;
            if (aVar3.toString().equals(lowerCase)) {
                return aVar3;
            }
            a aVar4 = f23896c;
            if (aVar4.toString().equals(lowerCase)) {
                return aVar4;
            }
            a aVar5 = f23898e;
            if (aVar5.toString().equals(lowerCase)) {
                return aVar5;
            }
            return null;
        }

        public String toString() {
            return this.f547a;
        }
    }

    public hq() {
        this.f23893a = a.f23894a;
        this.f546a = new HashMap();
    }

    public synchronized void a(Map<String, String> map) {
        this.f546a.putAll(map);
    }

    public String b() {
        return null;
    }

    /* renamed from: a, reason: collision with other method in class */
    public a m484a() {
        return this.f23893a;
    }

    public hq(Bundle bundle) {
        super(bundle);
        this.f23893a = a.f23894a;
        this.f546a = new HashMap();
        if (bundle.containsKey("ext_iq_type")) {
            this.f23893a = a.a(bundle.getString("ext_iq_type"));
        }
    }

    public void a(a aVar) {
        if (aVar == null) {
            this.f23893a = a.f23894a;
        } else {
            this.f23893a = aVar;
        }
    }

    @Override // com.xiaomi.push.hs
    public Bundle a() {
        Bundle bundleA = super.a();
        a aVar = this.f23893a;
        if (aVar != null) {
            bundleA.putString("ext_iq_type", aVar.toString());
        }
        return bundleA;
    }

    @Override // com.xiaomi.push.hs
    /* renamed from: a, reason: collision with other method in class */
    public String mo485a() {
        StringBuilder sb = new StringBuilder();
        sb.append("<iq ");
        if (j() != null) {
            sb.append("id=\"" + j() + "\" ");
        }
        if (l() != null) {
            sb.append("to=\"");
            sb.append(id.a(l()));
            sb.append("\" ");
        }
        if (m() != null) {
            sb.append("from=\"");
            sb.append(id.a(m()));
            sb.append("\" ");
        }
        if (k() != null) {
            sb.append("chid=\"");
            sb.append(id.a(k()));
            sb.append("\" ");
        }
        for (Map.Entry<String, String> entry : this.f546a.entrySet()) {
            sb.append(id.a(entry.getKey()));
            sb.append("=\"");
            sb.append(id.a(entry.getValue()));
            sb.append("\" ");
        }
        if (this.f23893a == null) {
            sb.append("type=\"get\">");
        } else {
            sb.append("type=\"");
            sb.append(m484a());
            sb.append("\">");
        }
        String strB = b();
        if (strB != null) {
            sb.append(strB);
        }
        sb.append(o());
        hw hwVarM486a = m486a();
        if (hwVarM486a != null) {
            sb.append(hwVarM486a.m489a());
        }
        sb.append("</iq>");
        return sb.toString();
    }
}
