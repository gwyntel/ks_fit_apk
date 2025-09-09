package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.util.Arrays;

@GwtCompatible(emulated = true, serializable = true)
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
class ObjectCountLinkedHashMap<K> extends ObjectCountHashMap<K> {
    private static final int ENDPOINT = -2;

    /* renamed from: f, reason: collision with root package name */
    transient long[] f14271f;
    private transient int firstEntry;
    private transient int lastEntry;

    ObjectCountLinkedHashMap(int i2) {
        this(i2, 1.0f);
    }

    private int getPredecessor(int i2) {
        return (int) (this.f14271f[i2] >>> 32);
    }

    private int getSuccessor(int i2) {
        return (int) this.f14271f[i2];
    }

    private void setPredecessor(int i2, int i3) {
        long[] jArr = this.f14271f;
        jArr[i2] = (jArr[i2] & 4294967295L) | (i3 << 32);
    }

    private void setSucceeds(int i2, int i3) {
        if (i2 == -2) {
            this.firstEntry = i3;
        } else {
            setSuccessor(i2, i3);
        }
        if (i3 == -2) {
            this.lastEntry = i2;
        } else {
            setPredecessor(i3, i2);
        }
    }

    private void setSuccessor(int i2, int i3) {
        long[] jArr = this.f14271f;
        jArr[i2] = (jArr[i2] & (-4294967296L)) | (i3 & 4294967295L);
    }

    @Override // com.google.common.collect.ObjectCountHashMap
    public void clear() {
        super.clear();
        this.firstEntry = -2;
        this.lastEntry = -2;
    }

    @Override // com.google.common.collect.ObjectCountHashMap
    int d() {
        int i2 = this.firstEntry;
        if (i2 == -2) {
            return -1;
        }
        return i2;
    }

    @Override // com.google.common.collect.ObjectCountHashMap
    void i(int i2, float f2) {
        super.i(i2, f2);
        this.firstEntry = -2;
        this.lastEntry = -2;
        long[] jArr = new long[i2];
        this.f14271f = jArr;
        Arrays.fill(jArr, -1L);
    }

    @Override // com.google.common.collect.ObjectCountHashMap
    void j(int i2, Object obj, int i3, int i4) {
        super.j(i2, obj, i3, i4);
        setSucceeds(this.lastEntry, i2);
        setSucceeds(i2, -2);
    }

    @Override // com.google.common.collect.ObjectCountHashMap
    void k(int i2) {
        int iQ = q() - 1;
        setSucceeds(getPredecessor(i2), getSuccessor(i2));
        if (i2 < iQ) {
            setSucceeds(getPredecessor(iQ), i2);
            setSucceeds(i2, getSuccessor(iQ));
        }
        super.k(i2);
    }

    @Override // com.google.common.collect.ObjectCountHashMap
    int l(int i2) {
        int successor = getSuccessor(i2);
        if (successor == -2) {
            return -1;
        }
        return successor;
    }

    @Override // com.google.common.collect.ObjectCountHashMap
    int m(int i2, int i3) {
        return i2 == q() ? i3 : i2;
    }

    @Override // com.google.common.collect.ObjectCountHashMap
    void o(int i2) {
        super.o(i2);
        long[] jArr = this.f14271f;
        int length = jArr.length;
        long[] jArrCopyOf = Arrays.copyOf(jArr, i2);
        this.f14271f = jArrCopyOf;
        Arrays.fill(jArrCopyOf, length, i2, -1L);
    }

    ObjectCountLinkedHashMap(int i2, float f2) {
        super(i2, f2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    ObjectCountLinkedHashMap(ObjectCountHashMap objectCountHashMap) {
        i(objectCountHashMap.q(), 1.0f);
        int iD = objectCountHashMap.d();
        while (iD != -1) {
            put(objectCountHashMap.f(iD), objectCountHashMap.g(iD));
            iD = objectCountHashMap.l(iD);
        }
    }
}
