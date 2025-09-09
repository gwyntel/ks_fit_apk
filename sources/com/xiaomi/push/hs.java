package com.xiaomi.push;

import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import com.xiaomi.mipush.sdk.Constants;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes4.dex */
public abstract class hs {

    /* renamed from: a, reason: collision with other field name */
    public static final DateFormat f549a;

    /* renamed from: b, reason: collision with root package name */
    private static long f23912b;

    /* renamed from: c, reason: collision with root package name */
    private static String f23913c;

    /* renamed from: a, reason: collision with other field name */
    public long f551a;

    /* renamed from: a, reason: collision with other field name */
    private hw f552a;

    /* renamed from: a, reason: collision with other field name */
    private List<hp> f553a;

    /* renamed from: a, reason: collision with other field name */
    private final Map<String, Object> f554a;

    /* renamed from: d, reason: collision with root package name */
    private String f23914d;

    /* renamed from: e, reason: collision with root package name */
    private String f23915e;

    /* renamed from: f, reason: collision with root package name */
    private String f23916f;

    /* renamed from: g, reason: collision with root package name */
    private String f23917g;

    /* renamed from: h, reason: collision with root package name */
    private String f23918h;

    /* renamed from: i, reason: collision with root package name */
    private String f23919i;

    /* renamed from: a, reason: collision with root package name */
    protected static final String f23911a = Locale.getDefault().getLanguage().toLowerCase();

    /* renamed from: b, reason: collision with other field name */
    private static String f550b = null;

    static {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        f549a = simpleDateFormat;
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        f23913c = id.a(5) + Constants.ACCEPT_TIME_SEPARATOR_SERVER;
        f23912b = 0L;
    }

    public hs() {
        this.f23914d = f550b;
        this.f23915e = null;
        this.f23916f = null;
        this.f23917g = null;
        this.f23918h = null;
        this.f23919i = null;
        this.f553a = new CopyOnWriteArrayList();
        this.f554a = new HashMap();
        this.f552a = null;
    }

    public static synchronized String i() {
        StringBuilder sb;
        sb = new StringBuilder();
        sb.append(f23913c);
        long j2 = f23912b;
        f23912b = 1 + j2;
        sb.append(Long.toString(j2));
        return sb.toString();
    }

    public static String q() {
        return f23911a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public hw m486a() {
        return this.f552a;
    }

    /* renamed from: a */
    public abstract String mo485a();

    public synchronized Collection<String> b() {
        if (this.f554a == null) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet(new HashSet(this.f554a.keySet()));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        hs hsVar = (hs) obj;
        hw hwVar = this.f552a;
        if (hwVar == null ? hsVar.f552a != null : !hwVar.equals(hsVar.f552a)) {
            return false;
        }
        String str = this.f23917g;
        if (str == null ? hsVar.f23917g != null : !str.equals(hsVar.f23917g)) {
            return false;
        }
        if (!this.f553a.equals(hsVar.f553a)) {
            return false;
        }
        String str2 = this.f23915e;
        if (str2 == null ? hsVar.f23915e != null : !str2.equals(hsVar.f23915e)) {
            return false;
        }
        String str3 = this.f23918h;
        if (str3 == null ? hsVar.f23918h != null : !str3.equals(hsVar.f23918h)) {
            return false;
        }
        Map<String, Object> map = this.f554a;
        if (map == null ? hsVar.f554a != null : !map.equals(hsVar.f554a)) {
            return false;
        }
        String str4 = this.f23916f;
        if (str4 == null ? hsVar.f23916f != null : !str4.equals(hsVar.f23916f)) {
            return false;
        }
        String str5 = this.f23914d;
        String str6 = hsVar.f23914d;
        if (str5 != null) {
            if (str5.equals(str6)) {
                return true;
            }
        } else if (str6 == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        String str = this.f23914d;
        int iHashCode = (str != null ? str.hashCode() : 0) * 31;
        String str2 = this.f23915e;
        int iHashCode2 = (iHashCode + (str2 != null ? str2.hashCode() : 0)) * 31;
        String str3 = this.f23916f;
        int iHashCode3 = (iHashCode2 + (str3 != null ? str3.hashCode() : 0)) * 31;
        String str4 = this.f23917g;
        int iHashCode4 = (iHashCode3 + (str4 != null ? str4.hashCode() : 0)) * 31;
        String str5 = this.f23918h;
        int iHashCode5 = (((((iHashCode4 + (str5 != null ? str5.hashCode() : 0)) * 31) + this.f553a.hashCode()) * 31) + this.f554a.hashCode()) * 31;
        hw hwVar = this.f552a;
        return iHashCode5 + (hwVar != null ? hwVar.hashCode() : 0);
    }

    public String j() {
        if ("ID_NOT_AVAILABLE".equals(this.f23915e)) {
            return null;
        }
        if (this.f23915e == null) {
            this.f23915e = i();
        }
        return this.f23915e;
    }

    public void k(String str) {
        this.f23915e = str;
    }

    public void l(String str) {
        this.f23918h = str;
    }

    public void m(String str) {
        this.f23916f = str;
    }

    public void n(String str) {
        this.f23917g = str;
    }

    public void o(String str) {
        this.f23919i = str;
    }

    public String p() {
        return this.f23914d;
    }

    public void a(hw hwVar) {
        this.f552a = hwVar;
    }

    public String k() {
        return this.f23918h;
    }

    public String l() {
        return this.f23916f;
    }

    public String m() {
        return this.f23917g;
    }

    public String n() {
        return this.f23919i;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(9:35|93|36|91|37|(4:78|38|85|39)|83|40|104) */
    /* JADX WARN: Removed duplicated region for block: B:102:0x0129 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0108 A[EXC_TOP_SPLITTER, PHI: r4
      0x0108: PHI (r4v7 java.io.ByteArrayOutputStream) = (r4v6 java.io.ByteArrayOutputStream), (r4v8 java.io.ByteArrayOutputStream), (r4v8 java.io.ByteArrayOutputStream) binds: [B:58:0x0126, B:74:0x0108, B:39:0x0105] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0123 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected synchronized java.lang.String o() {
        /*
            Method dump skipped, instructions count: 328
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.hs.o():java.lang.String");
    }

    /* renamed from: a, reason: collision with other method in class */
    public synchronized Collection<hp> m488a() {
        if (this.f553a == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(new ArrayList(this.f553a));
    }

    public hp a(String str) {
        return a(str, null);
    }

    public hp a(String str, String str2) {
        for (hp hpVar : this.f553a) {
            if (str2 == null || str2.equals(hpVar.b())) {
                if (str.equals(hpVar.m482a())) {
                    return hpVar;
                }
            }
        }
        return null;
    }

    public void a(hp hpVar) {
        this.f553a.add(hpVar);
    }

    public hs(Bundle bundle) {
        this.f23914d = f550b;
        this.f23915e = null;
        this.f23916f = null;
        this.f23917g = null;
        this.f23918h = null;
        this.f23919i = null;
        this.f553a = new CopyOnWriteArrayList();
        this.f554a = new HashMap();
        this.f552a = null;
        this.f23916f = bundle.getString("ext_to");
        this.f23917g = bundle.getString("ext_from");
        this.f23918h = bundle.getString("ext_chid");
        this.f23915e = bundle.getString("ext_pkt_id");
        Parcelable[] parcelableArray = bundle.getParcelableArray("ext_exts");
        if (parcelableArray != null) {
            this.f553a = new ArrayList(parcelableArray.length);
            for (Parcelable parcelable : parcelableArray) {
                hp hpVarA = hp.a((Bundle) parcelable);
                if (hpVarA != null) {
                    this.f553a.add(hpVarA);
                }
            }
        }
        Bundle bundle2 = bundle.getBundle("ext_ERROR");
        if (bundle2 != null) {
            this.f552a = new hw(bundle2);
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public synchronized Object m487a(String str) {
        Map<String, Object> map = this.f554a;
        if (map == null) {
            return null;
        }
        return map.get(str);
    }

    public Bundle a() {
        Bundle bundle = new Bundle();
        if (!TextUtils.isEmpty(this.f23914d)) {
            bundle.putString("ext_ns", this.f23914d);
        }
        if (!TextUtils.isEmpty(this.f23917g)) {
            bundle.putString("ext_from", this.f23917g);
        }
        if (!TextUtils.isEmpty(this.f23916f)) {
            bundle.putString("ext_to", this.f23916f);
        }
        if (!TextUtils.isEmpty(this.f23915e)) {
            bundle.putString("ext_pkt_id", this.f23915e);
        }
        if (!TextUtils.isEmpty(this.f23918h)) {
            bundle.putString("ext_chid", this.f23918h);
        }
        hw hwVar = this.f552a;
        if (hwVar != null) {
            bundle.putBundle("ext_ERROR", hwVar.a());
        }
        List<hp> list = this.f553a;
        if (list != null) {
            Bundle[] bundleArr = new Bundle[list.size()];
            Iterator<hp> it = this.f553a.iterator();
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
}
