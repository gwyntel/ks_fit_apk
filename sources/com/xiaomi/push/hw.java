package com.xiaomi.push;

import android.os.Bundle;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class hw {

    /* renamed from: a, reason: collision with root package name */
    private int f23936a;

    /* renamed from: a, reason: collision with other field name */
    private String f559a;

    /* renamed from: a, reason: collision with other field name */
    private List<hp> f560a;

    /* renamed from: b, reason: collision with root package name */
    private String f23937b;

    /* renamed from: c, reason: collision with root package name */
    private String f23938c;

    /* renamed from: d, reason: collision with root package name */
    private String f23939d;

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        public static final a f23940a = new a("internal-server-error");

        /* renamed from: b, reason: collision with root package name */
        public static final a f23941b = new a("forbidden");

        /* renamed from: c, reason: collision with root package name */
        public static final a f23942c = new a("bad-request");

        /* renamed from: d, reason: collision with root package name */
        public static final a f23943d = new a("conflict");

        /* renamed from: e, reason: collision with root package name */
        public static final a f23944e = new a("feature-not-implemented");

        /* renamed from: f, reason: collision with root package name */
        public static final a f23945f = new a("gone");

        /* renamed from: g, reason: collision with root package name */
        public static final a f23946g = new a("item-not-found");

        /* renamed from: h, reason: collision with root package name */
        public static final a f23947h = new a("jid-malformed");

        /* renamed from: i, reason: collision with root package name */
        public static final a f23948i = new a("not-acceptable");

        /* renamed from: j, reason: collision with root package name */
        public static final a f23949j = new a("not-allowed");

        /* renamed from: k, reason: collision with root package name */
        public static final a f23950k = new a("not-authorized");

        /* renamed from: l, reason: collision with root package name */
        public static final a f23951l = new a("payment-required");

        /* renamed from: m, reason: collision with root package name */
        public static final a f23952m = new a("recipient-unavailable");

        /* renamed from: n, reason: collision with root package name */
        public static final a f23953n = new a("redirect");

        /* renamed from: o, reason: collision with root package name */
        public static final a f23954o = new a("registration-required");

        /* renamed from: p, reason: collision with root package name */
        public static final a f23955p = new a("remote-server-error");

        /* renamed from: q, reason: collision with root package name */
        public static final a f23956q = new a("remote-server-not-found");

        /* renamed from: r, reason: collision with root package name */
        public static final a f23957r = new a("remote-server-timeout");

        /* renamed from: s, reason: collision with root package name */
        public static final a f23958s = new a("resource-constraint");

        /* renamed from: t, reason: collision with root package name */
        public static final a f23959t = new a("service-unavailable");

        /* renamed from: u, reason: collision with root package name */
        public static final a f23960u = new a("subscription-required");

        /* renamed from: v, reason: collision with root package name */
        public static final a f23961v = new a("undefined-condition");

        /* renamed from: w, reason: collision with root package name */
        public static final a f23962w = new a("unexpected-request");

        /* renamed from: x, reason: collision with root package name */
        public static final a f23963x = new a("request-timeout");

        /* renamed from: a, reason: collision with other field name */
        private String f561a;

        public a(String str) {
            this.f561a = str;
        }

        public String toString() {
            return this.f561a;
        }
    }

    public hw(a aVar) {
        this.f560a = null;
        a(aVar);
        this.f23939d = null;
    }

    private void a(a aVar) {
        this.f23937b = aVar.f561a;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        String str = this.f23937b;
        if (str != null) {
            sb.append(str);
        }
        sb.append("(");
        sb.append(this.f23936a);
        sb.append(")");
        if (this.f23939d != null) {
            sb.append(" ");
            sb.append(this.f23939d);
        }
        return sb.toString();
    }

    public Bundle a() {
        Bundle bundle = new Bundle();
        String str = this.f559a;
        if (str != null) {
            bundle.putString("ext_err_type", str);
        }
        bundle.putInt("ext_err_code", this.f23936a);
        String str2 = this.f23938c;
        if (str2 != null) {
            bundle.putString("ext_err_reason", str2);
        }
        String str3 = this.f23937b;
        if (str3 != null) {
            bundle.putString("ext_err_cond", str3);
        }
        String str4 = this.f23939d;
        if (str4 != null) {
            bundle.putString("ext_err_msg", str4);
        }
        List<hp> list = this.f560a;
        if (list != null) {
            Bundle[] bundleArr = new Bundle[list.size()];
            Iterator<hp> it = this.f560a.iterator();
            int i2 = 0;
            while (it.hasNext()) {
                Bundle bundleA = it.next().a();
                if (bundleA != null) {
                    bundleArr[i2] = bundleA;
                    i2++;
                }
            }
            bundle.putParcelableArray("ext_exts", bundleArr);
        }
        return bundle;
    }

    public hw(int i2, String str, String str2, String str3, String str4, List<hp> list) {
        this.f23936a = i2;
        this.f559a = str;
        this.f23938c = str2;
        this.f23937b = str3;
        this.f23939d = str4;
        this.f560a = list;
    }

    public hw(Bundle bundle) {
        this.f560a = null;
        this.f23936a = bundle.getInt("ext_err_code");
        if (bundle.containsKey("ext_err_type")) {
            this.f559a = bundle.getString("ext_err_type");
        }
        this.f23937b = bundle.getString("ext_err_cond");
        this.f23938c = bundle.getString("ext_err_reason");
        this.f23939d = bundle.getString("ext_err_msg");
        Parcelable[] parcelableArray = bundle.getParcelableArray("ext_exts");
        if (parcelableArray != null) {
            this.f560a = new ArrayList(parcelableArray.length);
            for (Parcelable parcelable : parcelableArray) {
                hp hpVarA = hp.a((Bundle) parcelable);
                if (hpVarA != null) {
                    this.f560a.add(hpVarA);
                }
            }
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public String m489a() {
        StringBuilder sb = new StringBuilder();
        sb.append("<error code=\"");
        sb.append(this.f23936a);
        sb.append("\"");
        if (this.f559a != null) {
            sb.append(" type=\"");
            sb.append(this.f559a);
            sb.append("\"");
        }
        if (this.f23938c != null) {
            sb.append(" reason=\"");
            sb.append(this.f23938c);
            sb.append("\"");
        }
        sb.append(">");
        if (this.f23937b != null) {
            sb.append("<");
            sb.append(this.f23937b);
            sb.append(" xmlns=\"urn:ietf:params:xml:ns:xmpp-stanzas\"/>");
        }
        if (this.f23939d != null) {
            sb.append("<text xml:lang=\"en\" xmlns=\"urn:ietf:params:xml:ns:xmpp-stanzas\">");
            sb.append(this.f23939d);
            sb.append("</text>");
        }
        Iterator<hp> it = m490a().iterator();
        while (it.hasNext()) {
            sb.append(it.next().d());
        }
        sb.append("</error>");
        return sb.toString();
    }

    /* renamed from: a, reason: collision with other method in class */
    public synchronized List<hp> m490a() {
        List<hp> list = this.f560a;
        if (list == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(list);
    }
}
