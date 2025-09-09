package org.mozilla.javascript;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/* loaded from: classes5.dex */
public class ObjToIntMap implements Serializable {
    private static final int A = -1640531527;
    private static final Object DELETED = new Object();
    private static final boolean check = false;
    static final long serialVersionUID = -1542220580748809402L;
    private int keyCount;
    private transient Object[] keys;
    private transient int occupiedCount;
    private int power;
    private transient int[] values;

    public static class Iterator {
        private int cursor;
        private Object[] keys;
        ObjToIntMap master;
        private int remaining;
        private int[] values;

        Iterator(ObjToIntMap objToIntMap) {
            this.master = objToIntMap;
        }

        public boolean done() {
            return this.remaining < 0;
        }

        public Object getKey() {
            Object obj = this.keys[this.cursor];
            if (obj == UniqueTag.NULL_VALUE) {
                return null;
            }
            return obj;
        }

        public int getValue() {
            return this.values[this.cursor];
        }

        final void init(Object[] objArr, int[] iArr, int i2) {
            this.keys = objArr;
            this.values = iArr;
            this.cursor = -1;
            this.remaining = i2;
        }

        public void next() throws RuntimeException {
            if (this.remaining == -1) {
                Kit.codeBug();
            }
            if (this.remaining == 0) {
                this.remaining = -1;
                this.cursor = -1;
                return;
            }
            int i2 = this.cursor;
            while (true) {
                this.cursor = i2 + 1;
                Object obj = this.keys[this.cursor];
                if (obj != null && obj != ObjToIntMap.DELETED) {
                    this.remaining--;
                    return;
                }
                i2 = this.cursor;
            }
        }

        public void setValue(int i2) {
            this.values[this.cursor] = i2;
        }

        public void start() throws RuntimeException {
            this.master.initIterator(this);
            next();
        }
    }

    public ObjToIntMap() {
        this(4);
    }

    private int ensureIndex(Object obj) {
        int i2;
        int iHashCode = obj.hashCode();
        Object[] objArr = this.keys;
        if (objArr != null) {
            int i3 = A * iHashCode;
            int i4 = this.power;
            i2 = i3 >>> (32 - i4);
            Object obj2 = objArr[i2];
            if (obj2 != null) {
                int i5 = 1 << i4;
                if (obj2 != obj && (this.values[i5 + i2] != iHashCode || !obj2.equals(obj))) {
                    i = obj2 == DELETED ? i2 : -1;
                    int i6 = i5 - 1;
                    int iTableLookupStep = tableLookupStep(i3, i6, this.power);
                    while (true) {
                        i2 = (i2 + iTableLookupStep) & i6;
                        Object obj3 = this.keys[i2];
                        if (obj3 == null) {
                            break;
                        }
                        if (obj3 == obj || (this.values[i5 + i2] == iHashCode && obj3.equals(obj))) {
                            break;
                        }
                        if (obj3 == DELETED && i < 0) {
                            i = i2;
                        }
                    }
                }
                return i2;
            }
        } else {
            i2 = -1;
        }
        if (i < 0) {
            if (this.keys != null) {
                int i7 = this.occupiedCount;
                if (i7 * 4 < (1 << this.power) * 3) {
                    this.occupiedCount = i7 + 1;
                    i = i2;
                }
            }
            rehashTable();
            return insertNewKey(obj, iHashCode);
        }
        this.keys[i] = obj;
        this.values[(1 << this.power) + i] = iHashCode;
        this.keyCount++;
        return i;
    }

    private int findIndex(Object obj) {
        if (this.keys == null) {
            return -1;
        }
        int iHashCode = obj.hashCode();
        int i2 = A * iHashCode;
        int i3 = this.power;
        int i4 = i2 >>> (32 - i3);
        Object obj2 = this.keys[i4];
        if (obj2 == null) {
            return -1;
        }
        int i5 = 1 << i3;
        if (obj2 != obj && (this.values[i5 + i4] != iHashCode || !obj2.equals(obj))) {
            int i6 = i5 - 1;
            int iTableLookupStep = tableLookupStep(i2, i6, this.power);
            while (true) {
                i4 = (i4 + iTableLookupStep) & i6;
                Object obj3 = this.keys[i4];
                if (obj3 == null) {
                    return -1;
                }
                if (obj3 == obj || (this.values[i5 + i4] == iHashCode && obj3.equals(obj))) {
                    break;
                }
            }
        }
        return i4;
    }

    private int insertNewKey(Object obj, int i2) {
        int i3 = A * i2;
        int i4 = this.power;
        int i5 = i3 >>> (32 - i4);
        int i6 = 1 << i4;
        if (this.keys[i5] != null) {
            int i7 = i6 - 1;
            int iTableLookupStep = tableLookupStep(i3, i7, i4);
            do {
                i5 = (i5 + iTableLookupStep) & i7;
            } while (this.keys[i5] != null);
        }
        this.keys[i5] = obj;
        this.values[i6 + i5] = i2;
        this.occupiedCount++;
        this.keyCount++;
        return i5;
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        int i2 = this.keyCount;
        if (i2 != 0) {
            this.keyCount = 0;
            int i3 = 1 << this.power;
            this.keys = new Object[i3];
            this.values = new int[i3 * 2];
            for (int i4 = 0; i4 != i2; i4++) {
                Object object = objectInputStream.readObject();
                this.values[insertNewKey(object, object.hashCode())] = objectInputStream.readInt();
            }
        }
    }

    private void rehashTable() {
        Object[] objArr = this.keys;
        if (objArr == null) {
            int i2 = 1 << this.power;
            this.keys = new Object[i2];
            this.values = new int[i2 * 2];
            return;
        }
        int i3 = this.keyCount;
        if (i3 * 2 >= this.occupiedCount) {
            this.power++;
        }
        int i4 = 1 << this.power;
        int[] iArr = this.values;
        int length = objArr.length;
        this.keys = new Object[i4];
        this.values = new int[i4 * 2];
        int i5 = 0;
        this.keyCount = 0;
        this.occupiedCount = 0;
        while (i3 != 0) {
            Object obj = objArr[i5];
            if (obj != null && obj != DELETED) {
                this.values[insertNewKey(obj, iArr[length + i5])] = iArr[i5];
                i3--;
            }
            i5++;
        }
    }

    private static int tableLookupStep(int i2, int i3, int i4) {
        int i5 = 32 - (i4 * 2);
        if (i5 >= 0) {
            i2 >>>= i5;
        } else {
            i3 >>>= -i5;
        }
        return (i2 & i3) | 1;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        int i2 = this.keyCount;
        int i3 = 0;
        while (i2 != 0) {
            Object obj = this.keys[i3];
            if (obj != null && obj != DELETED) {
                i2--;
                objectOutputStream.writeObject(obj);
                objectOutputStream.writeInt(this.values[i3]);
            }
            i3++;
        }
    }

    public void clear() {
        int length = this.keys.length;
        while (length != 0) {
            length--;
            this.keys[length] = null;
        }
        this.keyCount = 0;
        this.occupiedCount = 0;
    }

    public int get(Object obj, int i2) {
        if (obj == null) {
            obj = UniqueTag.NULL_VALUE;
        }
        int iFindIndex = findIndex(obj);
        return iFindIndex >= 0 ? this.values[iFindIndex] : i2;
    }

    public int getExisting(Object obj) throws RuntimeException {
        if (obj == null) {
            obj = UniqueTag.NULL_VALUE;
        }
        int iFindIndex = findIndex(obj);
        if (iFindIndex >= 0) {
            return this.values[iFindIndex];
        }
        Kit.codeBug();
        return 0;
    }

    public Object[] getKeys() {
        Object[] objArr = new Object[this.keyCount];
        getKeys(objArr, 0);
        return objArr;
    }

    public boolean has(Object obj) {
        if (obj == null) {
            obj = UniqueTag.NULL_VALUE;
        }
        return findIndex(obj) >= 0;
    }

    final void initIterator(Iterator iterator) {
        iterator.init(this.keys, this.values, this.keyCount);
    }

    public Object intern(Object obj) {
        boolean z2;
        if (obj == null) {
            obj = UniqueTag.NULL_VALUE;
            z2 = true;
        } else {
            z2 = false;
        }
        int iEnsureIndex = ensureIndex(obj);
        this.values[iEnsureIndex] = 0;
        if (z2) {
            return null;
        }
        return this.keys[iEnsureIndex];
    }

    public boolean isEmpty() {
        return this.keyCount == 0;
    }

    public Iterator newIterator() {
        return new Iterator(this);
    }

    public void put(Object obj, int i2) {
        if (obj == null) {
            obj = UniqueTag.NULL_VALUE;
        }
        this.values[ensureIndex(obj)] = i2;
    }

    public void remove(Object obj) {
        if (obj == null) {
            obj = UniqueTag.NULL_VALUE;
        }
        int iFindIndex = findIndex(obj);
        if (iFindIndex >= 0) {
            this.keys[iFindIndex] = DELETED;
            this.keyCount--;
        }
    }

    public int size() {
        return this.keyCount;
    }

    public ObjToIntMap(int i2) throws RuntimeException {
        if (i2 < 0) {
            Kit.codeBug();
        }
        int i3 = 2;
        while ((1 << i3) < (i2 * 4) / 3) {
            i3++;
        }
        this.power = i3;
    }

    public void getKeys(Object[] objArr, int i2) {
        int i3 = this.keyCount;
        int i4 = 0;
        while (i3 != 0) {
            Object obj = this.keys[i4];
            if (obj != null && obj != DELETED) {
                if (obj == UniqueTag.NULL_VALUE) {
                    obj = null;
                }
                objArr[i2] = obj;
                i2++;
                i3--;
            }
            i4++;
        }
    }
}
