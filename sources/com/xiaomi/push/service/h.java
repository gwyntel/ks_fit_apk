package com.xiaomi.push.service;

import android.util.SparseArray;

/* loaded from: classes4.dex */
class h extends SparseArray<Integer> {
    h(int i2) {
        super(i2);
        put(1, 32);
        put(2, 16);
        put(4, 8);
        put(8, 4);
        put(16, 2);
        put(32, 64);
        put(64, 128);
    }
}
