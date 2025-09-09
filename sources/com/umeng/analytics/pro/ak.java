package com.umeng.analytics.pro;

import android.text.TextUtils;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class ak {

    /* renamed from: a, reason: collision with root package name */
    private String f21298a;

    /* renamed from: b, reason: collision with root package name */
    private ArrayList<al> f21299b = new ArrayList<>();

    public ak(String str) {
        this.f21298a = "";
        this.f21298a = str;
        if (TextUtils.isEmpty(str)) {
            return;
        }
        a();
    }

    private void a() {
        try {
            if (!this.f21298a.contains(",")) {
                String str = this.f21298a;
                if (TextUtils.isEmpty(str)) {
                    return;
                }
                String strTrim = str.trim();
                if (this.f21299b != null) {
                    this.f21299b.add(new al(strTrim));
                    return;
                }
                return;
            }
            for (String str2 : this.f21298a.split(",")) {
                if (!TextUtils.isEmpty(str2)) {
                    String strTrim2 = str2.trim();
                    if (this.f21299b != null) {
                        this.f21299b.add(new al(strTrim2));
                    }
                }
            }
        } catch (Throwable unused) {
        }
    }

    public boolean a(int i2) {
        try {
            ArrayList<al> arrayList = this.f21299b;
            if (arrayList == null) {
                return false;
            }
            int size = arrayList.size();
            for (int i3 = 0; i3 < size; i3++) {
                al alVar = this.f21299b.get(i3);
                if (alVar != null && alVar.a(i2)) {
                    return true;
                }
            }
            return false;
        } catch (Throwable unused) {
            return false;
        }
    }
}
