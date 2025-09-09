package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

@GwtCompatible(emulated = true)
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
final class Platform {
    private Platform() {
    }

    static Object[] a(Object[] objArr, int i2, int i3, Object[] objArr2) {
        return Arrays.copyOfRange(objArr, i2, i3, objArr2.getClass());
    }

    static Class b(Enum r02) {
        return r02.getDeclaringClass();
    }

    static Object[] c(Object[] objArr, int i2) {
        if (objArr.length != 0) {
            objArr = Arrays.copyOf(objArr, 0);
        }
        return Arrays.copyOf(objArr, i2);
    }

    static Map d(int i2) {
        return CompactHashMap.createWithExpectedSize(i2);
    }

    static Set e(int i2) {
        return CompactHashSet.createWithExpectedSize(i2);
    }

    static Map f(int i2) {
        return CompactLinkedHashMap.createWithExpectedSize(i2);
    }

    static Set g(int i2) {
        return CompactLinkedHashSet.createWithExpectedSize(i2);
    }

    static Map h() {
        return CompactHashMap.create();
    }

    static Map i(int i2) {
        return Maps.newLinkedHashMapWithExpectedSize(i2);
    }

    static MapMaker j(MapMaker mapMaker) {
        return mapMaker.weakKeys();
    }
}
