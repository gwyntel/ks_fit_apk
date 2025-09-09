package com.taobao.accs.data;

import java.util.Comparator;

/* loaded from: classes4.dex */
class b implements Comparator<Integer> {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ a f20135a;

    b(a aVar) {
        this.f20135a = aVar;
    }

    @Override // java.util.Comparator
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compare(Integer num, Integer num2) {
        return num.intValue() - num2.intValue();
    }
}
