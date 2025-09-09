package com.google.android.gms.common.data;

import androidx.annotation.NonNull;
import com.alipay.apmobilesecuritysdk.common.RushTimeUtil$1;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes3.dex */
public final class FreezableUtils {
    @NonNull
    public static <T, E extends Freezable<T>> ArrayList<T> freeze(@NonNull ArrayList<E> arrayList) {
        RushTimeUtil$1 rushTimeUtil$1 = (ArrayList<T>) new ArrayList(arrayList.size());
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            rushTimeUtil$1.add(arrayList.get(i2).freeze());
        }
        return rushTimeUtil$1;
    }

    @NonNull
    public static <T, E extends Freezable<T>> ArrayList<T> freezeIterable(@NonNull Iterable<E> iterable) {
        RushTimeUtil$1 rushTimeUtil$1 = (ArrayList<T>) new ArrayList();
        Iterator<E> it = iterable.iterator();
        while (it.hasNext()) {
            rushTimeUtil$1.add(it.next().freeze());
        }
        return rushTimeUtil$1;
    }

    @NonNull
    public static <T, E extends Freezable<T>> ArrayList<T> freeze(@NonNull E[] eArr) {
        RushTimeUtil$1 rushTimeUtil$1 = (ArrayList<T>) new ArrayList(eArr.length);
        for (E e2 : eArr) {
            rushTimeUtil$1.add(e2.freeze());
        }
        return rushTimeUtil$1;
    }
}
