package com.alibaba.sdk.android.openaccount.ui;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class LayoutMapping {
    public static Map<Class, Integer> class2LayoutId = new HashMap();

    public static Integer get(Class cls) {
        return class2LayoutId.get(cls);
    }

    public static boolean hasCustomLayout(Class cls) {
        return class2LayoutId.get(cls) != null;
    }

    public static void put(Class cls, Integer num) {
        class2LayoutId.put(cls, num);
    }
}
