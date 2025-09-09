package com.xiaomi.push;

import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* loaded from: classes4.dex */
public class hp implements ht {

    /* renamed from: a, reason: collision with root package name */
    private String f23890a;

    /* renamed from: a, reason: collision with other field name */
    private List<hp> f543a;

    /* renamed from: a, reason: collision with other field name */
    private String[] f544a;

    /* renamed from: b, reason: collision with root package name */
    private String f23891b;

    /* renamed from: b, reason: collision with other field name */
    private String[] f545b;

    /* renamed from: c, reason: collision with root package name */
    private String f23892c;

    public hp(String str, String str2, String[] strArr, String[] strArr2) {
        this.f543a = null;
        this.f23890a = str;
        this.f23891b = str2;
        this.f544a = strArr;
        this.f545b = strArr2;
    }

    /* renamed from: a, reason: collision with other method in class */
    public String m482a() {
        return this.f23890a;
    }

    public String b() {
        return this.f23891b;
    }

    public String c() {
        return !TextUtils.isEmpty(this.f23892c) ? id.b(this.f23892c) : this.f23892c;
    }

    @Override // com.xiaomi.push.ht
    public String d() {
        StringBuilder sb = new StringBuilder();
        sb.append("<");
        sb.append(this.f23890a);
        if (!TextUtils.isEmpty(this.f23891b)) {
            sb.append(" ");
            sb.append("xmlns=");
            sb.append("\"");
            sb.append(this.f23891b);
            sb.append("\"");
        }
        String[] strArr = this.f544a;
        if (strArr != null && strArr.length > 0) {
            for (int i2 = 0; i2 < this.f544a.length; i2++) {
                if (!TextUtils.isEmpty(this.f545b[i2])) {
                    sb.append(" ");
                    sb.append(this.f544a[i2]);
                    sb.append("=\"");
                    sb.append(id.a(this.f545b[i2]));
                    sb.append("\"");
                }
            }
        }
        if (TextUtils.isEmpty(this.f23892c)) {
            List<hp> list = this.f543a;
            if (list == null || list.size() <= 0) {
                sb.append("/>");
            } else {
                sb.append(">");
                Iterator<hp> it = this.f543a.iterator();
                while (it.hasNext()) {
                    sb.append(it.next().d());
                }
                sb.append("</");
                sb.append(this.f23890a);
                sb.append(">");
            }
        } else {
            sb.append(">");
            sb.append(this.f23892c);
            sb.append("</");
            sb.append(this.f23890a);
            sb.append(">");
        }
        return sb.toString();
    }

    public String toString() {
        return d();
    }

    public Bundle a() {
        Bundle bundle = new Bundle();
        bundle.putString("ext_ele_name", this.f23890a);
        bundle.putString("ext_ns", this.f23891b);
        bundle.putString("ext_text", this.f23892c);
        Bundle bundle2 = new Bundle();
        String[] strArr = this.f544a;
        if (strArr != null && strArr.length > 0) {
            int i2 = 0;
            while (true) {
                String[] strArr2 = this.f544a;
                if (i2 >= strArr2.length) {
                    break;
                }
                bundle2.putString(strArr2[i2], this.f545b[i2]);
                i2++;
            }
        }
        bundle.putBundle("attributes", bundle2);
        List<hp> list = this.f543a;
        if (list != null && list.size() > 0) {
            bundle.putParcelableArray("children", a(this.f543a));
        }
        return bundle;
    }

    public hp(String str, String str2, String[] strArr, String[] strArr2, String str3, List<hp> list) {
        this.f23890a = str;
        this.f23891b = str2;
        this.f544a = strArr;
        this.f545b = strArr2;
        this.f23892c = str3;
        this.f543a = list;
    }

    /* renamed from: a, reason: collision with other method in class */
    public Parcelable m481a() {
        return a();
    }

    public static Parcelable[] a(hp[] hpVarArr) {
        if (hpVarArr == null) {
            return null;
        }
        Parcelable[] parcelableArr = new Parcelable[hpVarArr.length];
        for (int i2 = 0; i2 < hpVarArr.length; i2++) {
            parcelableArr[i2] = hpVarArr[i2].m481a();
        }
        return parcelableArr;
    }

    public static Parcelable[] a(List<hp> list) {
        return a((hp[]) list.toArray(new hp[list.size()]));
    }

    public static hp a(Bundle bundle) {
        ArrayList arrayList;
        String string = bundle.getString("ext_ele_name");
        String string2 = bundle.getString("ext_ns");
        String string3 = bundle.getString("ext_text");
        Bundle bundle2 = bundle.getBundle("attributes");
        Set<String> setKeySet = bundle2.keySet();
        String[] strArr = new String[setKeySet.size()];
        String[] strArr2 = new String[setKeySet.size()];
        int i2 = 0;
        for (String str : setKeySet) {
            strArr[i2] = str;
            strArr2[i2] = bundle2.getString(str);
            i2++;
        }
        if (bundle.containsKey("children")) {
            Parcelable[] parcelableArray = bundle.getParcelableArray("children");
            ArrayList arrayList2 = new ArrayList(parcelableArray.length);
            for (Parcelable parcelable : parcelableArray) {
                arrayList2.add(a((Bundle) parcelable));
            }
            arrayList = arrayList2;
        } else {
            arrayList = null;
        }
        return new hp(string, string2, strArr, strArr2, string3, arrayList);
    }

    public String a(String str) {
        if (str != null) {
            if (this.f544a == null) {
                return null;
            }
            int i2 = 0;
            while (true) {
                String[] strArr = this.f544a;
                if (i2 >= strArr.length) {
                    return null;
                }
                if (str.equals(strArr[i2])) {
                    return this.f545b[i2];
                }
                i2++;
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void a(hp hpVar) {
        if (this.f543a == null) {
            this.f543a = new ArrayList();
        }
        if (this.f543a.contains(hpVar)) {
            return;
        }
        this.f543a.add(hpVar);
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m483a(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.f23892c = id.a(str);
        } else {
            this.f23892c = str;
        }
    }
}
