package com.alipay.android.phone.mrpc.core;

import androidx.browser.trusted.sharing.ShareTarget;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.Header;

/* loaded from: classes2.dex */
public final class o extends t {

    /* renamed from: b, reason: collision with root package name */
    public String f9011b;

    /* renamed from: c, reason: collision with root package name */
    public byte[] f9012c;

    /* renamed from: g, reason: collision with root package name */
    public boolean f9016g;

    /* renamed from: e, reason: collision with root package name */
    public ArrayList<Header> f9014e = new ArrayList<>();

    /* renamed from: f, reason: collision with root package name */
    public Map<String, String> f9015f = new HashMap();

    /* renamed from: d, reason: collision with root package name */
    public String f9013d = ShareTarget.ENCODING_TYPE_URL_ENCODED;

    public o(String str) {
        this.f9011b = str;
    }

    public final String a() {
        return this.f9011b;
    }

    public final String b(String str) {
        Map<String, String> map = this.f9015f;
        if (map == null) {
            return null;
        }
        return map.get(str);
    }

    public final String c() {
        return this.f9013d;
    }

    public final ArrayList<Header> d() {
        return this.f9014e;
    }

    public final boolean e() {
        return this.f9016g;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || o.class != obj.getClass()) {
            return false;
        }
        o oVar = (o) obj;
        byte[] bArr = this.f9012c;
        if (bArr == null) {
            if (oVar.f9012c != null) {
                return false;
            }
        } else if (!bArr.equals(oVar.f9012c)) {
            return false;
        }
        String str = this.f9011b;
        String str2 = oVar.f9011b;
        if (str == null) {
            if (str2 != null) {
                return false;
            }
        } else if (!str.equals(str2)) {
            return false;
        }
        return true;
    }

    public final int hashCode() {
        Map<String, String> map = this.f9015f;
        int iHashCode = ((map == null || !map.containsKey("id")) ? 1 : this.f9015f.get("id").hashCode() + 31) * 31;
        String str = this.f9011b;
        return iHashCode + (str == null ? 0 : str.hashCode());
    }

    public final String toString() {
        return String.format("Url : %s,HttpHeader: %s", this.f9011b, this.f9014e);
    }

    public final void a(String str) {
        this.f9013d = str;
    }

    public final byte[] b() {
        return this.f9012c;
    }

    public final void a(String str, String str2) {
        if (this.f9015f == null) {
            this.f9015f = new HashMap();
        }
        this.f9015f.put(str, str2);
    }

    public final void a(Header header) {
        this.f9014e.add(header);
    }

    public final void a(boolean z2) {
        this.f9016g = z2;
    }

    public final void a(byte[] bArr) {
        this.f9012c = bArr;
    }
}
