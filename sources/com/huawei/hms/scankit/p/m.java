package com.huawei.hms.scankit.p;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
final class m {

    /* renamed from: a, reason: collision with root package name */
    private final Map<Integer, Integer> f17516a = new HashMap();

    m() {
    }

    void a(int i2) {
        Integer num = this.f17516a.get(Integer.valueOf(i2));
        if (num == null) {
            num = 0;
        }
        this.f17516a.put(Integer.valueOf(i2), Integer.valueOf(num.intValue() + 1));
    }

    int[] a() {
        ArrayList arrayList = new ArrayList();
        int iIntValue = -1;
        for (Map.Entry<Integer, Integer> entry : this.f17516a.entrySet()) {
            if (entry.getValue().intValue() > iIntValue) {
                iIntValue = entry.getValue().intValue();
                arrayList.clear();
                arrayList.add(entry.getKey());
            } else if (entry.getValue().intValue() == iIntValue) {
                arrayList.add(entry.getKey());
            }
        }
        return n5.a(arrayList);
    }
}
