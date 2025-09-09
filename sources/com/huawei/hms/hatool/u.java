package com.huawei.hms.hatool;

import android.text.TextUtils;
import com.xiaomi.mipush.sdk.Constants;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/* loaded from: classes4.dex */
public class u {

    /* renamed from: a, reason: collision with root package name */
    private List<b1> f16490a;

    /* renamed from: b, reason: collision with root package name */
    private String f16491b;

    /* renamed from: c, reason: collision with root package name */
    private String f16492c;

    /* renamed from: d, reason: collision with root package name */
    private String f16493d;

    public u(List<b1> list, String str, String str2, String str3) {
        this.f16490a = list;
        this.f16491b = str;
        this.f16492c = str2;
        this.f16493d = str3;
    }

    public void a() {
        if (!"_default_config_tag".equals(this.f16492c)) {
            a(this.f16490a, this.f16492c, this.f16491b);
            return;
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        for (b1 b1Var : this.f16490a) {
            String strC = b1Var.c();
            if (TextUtils.isEmpty(strC) || "oper".equals(strC)) {
                arrayList4.add(b1Var);
            } else if ("maint".equals(strC)) {
                arrayList.add(b1Var);
            } else if ("preins".equals(strC)) {
                arrayList2.add(b1Var);
            } else if ("diffprivacy".equals(strC)) {
                arrayList3.add(b1Var);
            }
        }
        a(arrayList4, "oper", "_default_config_tag");
        a(arrayList, "maint", "_default_config_tag");
        a(arrayList2, "preins", "_default_config_tag");
        a(arrayList3, "diffprivacy", "_default_config_tag");
    }

    private void a(List<b1> list, String str, String str2) {
        if (list.isEmpty()) {
            return;
        }
        int size = (list.size() / 500) + 1;
        for (int i2 = 0; i2 < size; i2++) {
            int i3 = i2 * 500;
            List<b1> listSubList = list.subList(i3, Math.min(list.size(), i3 + 500));
            String strReplace = UUID.randomUUID().toString().replace(Constants.ACCEPT_TIME_SEPARATOR_SERVER, "");
            long jCurrentTimeMillis = System.currentTimeMillis();
            long jB = a1.b(str2, str) * 86400000;
            ArrayList arrayList = new ArrayList();
            for (b1 b1Var : listSubList) {
                if (!c0.a(b1Var.b(), jCurrentTimeMillis, jB)) {
                    arrayList.add(b1Var);
                }
            }
            if (arrayList.size() > 0) {
                new l0(str2, str, this.f16493d, arrayList, strReplace).a();
            } else {
                v.e("hmsSdk", "No data to report handler");
            }
        }
    }
}
