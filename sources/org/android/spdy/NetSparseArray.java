package org.android.spdy;

/* loaded from: classes5.dex */
public class NetSparseArray<E> implements Cloneable {
    private static final Object DELETED = new Object();
    private boolean mGarbage;
    private int[] mKeys;
    private int mSize;
    private Object[] mValues;

    public NetSparseArray() {
        this(10);
    }

    private static int binarySearch(int[] iArr, int i2, int i3, int i4) {
        int i5 = i3 + i2;
        int i6 = i2 - 1;
        int i7 = i5;
        while (i7 - i6 > 1) {
            int i8 = (i7 + i6) / 2;
            if (iArr[i8] < i4) {
                i6 = i8;
            } else {
                i7 = i8;
            }
        }
        return i7 == i5 ? ~i5 : iArr[i7] == i4 ? i7 : ~i7;
    }

    private void gc() {
        int i2 = this.mSize;
        int[] iArr = this.mKeys;
        Object[] objArr = this.mValues;
        int i3 = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            Object obj = objArr[i4];
            if (obj != DELETED) {
                if (i4 != i3) {
                    iArr[i3] = iArr[i4];
                    objArr[i3] = obj;
                    objArr[i4] = null;
                }
                i3++;
            }
        }
        this.mGarbage = false;
        this.mSize = i3;
    }

    public void append(int i2, E e2) {
        int i3 = this.mSize;
        if (i3 != 0 && i2 <= this.mKeys[i3 - 1]) {
            put(i2, e2);
            return;
        }
        if (this.mGarbage && i3 >= this.mKeys.length) {
            gc();
        }
        int i4 = this.mSize;
        int[] iArr = this.mKeys;
        if (i4 >= iArr.length) {
            int i5 = i4 + 1;
            int[] iArr2 = new int[i5];
            Object[] objArr = new Object[i5];
            System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
            Object[] objArr2 = this.mValues;
            System.arraycopy(objArr2, 0, objArr, 0, objArr2.length);
            this.mKeys = iArr2;
            this.mValues = objArr;
        }
        this.mKeys[i4] = i2;
        this.mValues[i4] = e2;
        this.mSize = i4 + 1;
    }

    public void clear() {
        int i2 = this.mSize;
        Object[] objArr = this.mValues;
        for (int i3 = 0; i3 < i2; i3++) {
            objArr[i3] = null;
        }
        this.mSize = 0;
        this.mGarbage = false;
    }

    public void delete(int i2) {
        int iBinarySearch = binarySearch(this.mKeys, 0, this.mSize, i2);
        if (iBinarySearch >= 0) {
            Object[] objArr = this.mValues;
            Object obj = objArr[iBinarySearch];
            Object obj2 = DELETED;
            if (obj != obj2) {
                objArr[iBinarySearch] = obj2;
                this.mGarbage = true;
            }
        }
    }

    public E get(int i2) {
        return get(i2, null);
    }

    public int indexOfKey(int i2) {
        if (this.mGarbage) {
            gc();
        }
        return binarySearch(this.mKeys, 0, this.mSize, i2);
    }

    public int indexOfValue(E e2) {
        if (this.mGarbage) {
            gc();
        }
        for (int i2 = 0; i2 < this.mSize; i2++) {
            if (this.mValues[i2] == e2) {
                return i2;
            }
        }
        return -1;
    }

    public int keyAt(int i2) {
        if (this.mGarbage) {
            gc();
        }
        return this.mKeys[i2];
    }

    public void put(int i2, E e2) {
        int iBinarySearch = binarySearch(this.mKeys, 0, this.mSize, i2);
        if (iBinarySearch >= 0) {
            this.mValues[iBinarySearch] = e2;
            return;
        }
        int i3 = ~iBinarySearch;
        int i4 = this.mSize;
        if (i3 < i4) {
            Object[] objArr = this.mValues;
            if (objArr[i3] == DELETED) {
                this.mKeys[i3] = i2;
                objArr[i3] = e2;
                return;
            }
        }
        if (this.mGarbage && i4 >= this.mKeys.length) {
            gc();
            i3 = ~binarySearch(this.mKeys, 0, this.mSize, i2);
        }
        int i5 = this.mSize;
        int[] iArr = this.mKeys;
        if (i5 >= iArr.length) {
            int i6 = i5 + 20;
            int[] iArr2 = new int[i6];
            Object[] objArr2 = new Object[i6];
            System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
            Object[] objArr3 = this.mValues;
            System.arraycopy(objArr3, 0, objArr2, 0, objArr3.length);
            this.mKeys = iArr2;
            this.mValues = objArr2;
        }
        int i7 = this.mSize;
        if (i7 - i3 != 0) {
            int[] iArr3 = this.mKeys;
            int i8 = i3 + 1;
            System.arraycopy(iArr3, i3, iArr3, i8, i7 - i3);
            Object[] objArr4 = this.mValues;
            System.arraycopy(objArr4, i3, objArr4, i8, this.mSize - i3);
        }
        this.mKeys[i3] = i2;
        this.mValues[i3] = e2;
        this.mSize++;
    }

    public void remove(int i2) {
        delete(i2);
    }

    public void removeAt(int i2) {
        Object[] objArr = this.mValues;
        Object obj = objArr[i2];
        Object obj2 = DELETED;
        if (obj != obj2) {
            objArr[i2] = obj2;
            this.mGarbage = true;
        }
    }

    public void setValueAt(int i2, E e2) {
        if (this.mGarbage) {
            gc();
        }
        this.mValues[i2] = e2;
    }

    public int size() {
        if (this.mGarbage) {
            gc();
        }
        return this.mSize;
    }

    public void toArray(E[] eArr) {
        for (int i2 = 0; i2 < eArr.length; i2++) {
            eArr[i2] = this.mValues[i2];
        }
    }

    public E valueAt(int i2) {
        if (this.mGarbage) {
            gc();
        }
        return (E) this.mValues[i2];
    }

    public NetSparseArray(int i2) {
        this.mGarbage = false;
        this.mKeys = new int[i2];
        this.mValues = new Object[i2];
        this.mSize = 0;
    }

    public E get(int i2, E e2) {
        E e3;
        int iBinarySearch = binarySearch(this.mKeys, 0, this.mSize, i2);
        return (iBinarySearch < 0 || (e3 = (E) this.mValues[iBinarySearch]) == DELETED) ? e2 : e3;
    }
}
