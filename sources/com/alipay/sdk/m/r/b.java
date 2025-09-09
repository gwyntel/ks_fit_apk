package com.alipay.sdk.m.r;

import android.text.TextUtils;
import com.alipay.sdk.m.u.i;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    public a f9692a;

    /* renamed from: b, reason: collision with root package name */
    public String f9693b;

    /* renamed from: c, reason: collision with root package name */
    public String[] f9694c;

    public b(String str) {
        this.f9693b = str;
    }

    public static void a(b bVar) throws JSONException {
        String[] strArrC = bVar.c();
        if (strArrC.length == 3 && TextUtils.equals("tid", strArrC[0])) {
            com.alipay.sdk.m.t.a aVarA = com.alipay.sdk.m.t.a.a(com.alipay.sdk.m.s.b.d().b());
            if (TextUtils.isEmpty(strArrC[1]) || TextUtils.isEmpty(strArrC[2])) {
                return;
            }
            aVarA.a(strArrC[1], strArrC[2]);
        }
    }

    public static String[] b(String str) {
        ArrayList arrayList = new ArrayList();
        int iIndexOf = str.indexOf(40);
        int iLastIndexOf = str.lastIndexOf(41);
        if (iIndexOf == -1 || iLastIndexOf == -1 || iLastIndexOf <= iIndexOf) {
            return null;
        }
        for (String str2 : str.substring(iIndexOf + 1, iLastIndexOf).split("' *, *'", -1)) {
            arrayList.add(str2.trim().replaceAll("'", "").replaceAll("\"", ""));
        }
        return (String[]) arrayList.toArray(new String[0]);
    }

    public String[] c() {
        return this.f9694c;
    }

    public b(String str, a aVar) {
        this.f9693b = str;
        this.f9692a = aVar;
    }

    public static List<b> a(JSONObject jSONObject) {
        ArrayList arrayList = new ArrayList();
        if (jSONObject == null) {
            return arrayList;
        }
        String[] strArrA = a(jSONObject.optString("name", ""));
        for (int i2 = 0; i2 < strArrA.length; i2++) {
            a aVarA = a.a(strArrA[i2]);
            if (aVarA != a.None) {
                b bVar = new b(strArrA[i2], aVarA);
                bVar.f9694c = b(strArrA[i2]);
                arrayList.add(bVar);
            }
        }
        return arrayList;
    }

    public String b() {
        return this.f9693b;
    }

    public static String[] a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return str.split(i.f9802b);
    }

    public a a() {
        return this.f9692a;
    }
}
