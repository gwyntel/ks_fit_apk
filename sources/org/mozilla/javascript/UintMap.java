package org.mozilla.javascript;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/* loaded from: classes5.dex */
public class UintMap implements Serializable {
    private static final int A = -1640531527;
    private static final int DELETED = -2;
    private static final int EMPTY = -1;
    private static final boolean check = false;
    static final long serialVersionUID = 4242698212885848444L;
    private transient int ivaluesShift;
    private int keyCount;
    private transient int[] keys;
    private transient int occupiedCount;
    private int power;
    private transient Object[] values;

    public UintMap() {
        this(4);
    }

    private int ensureIndex(int i2, boolean z2) {
        int i3;
        int i4;
        int[] iArr = this.keys;
        int i5 = -1;
        if (iArr != null) {
            int i6 = A * i2;
            int i7 = this.power;
            i3 = i6 >>> (32 - i7);
            int i8 = iArr[i3];
            if (i8 == i2) {
                return i3;
            }
            if (i8 != -1) {
                int i9 = i8 == -2 ? i3 : -1;
                int i10 = (1 << i7) - 1;
                int iTableLookupStep = tableLookupStep(i6, i10, i7);
                do {
                    i3 = (i3 + iTableLookupStep) & i10;
                    i4 = iArr[i3];
                    if (i4 == i2) {
                        return i3;
                    }
                    if (i4 == -2 && i9 < 0) {
                        i9 = i3;
                    }
                } while (i4 != -1);
                i5 = i9;
            }
        } else {
            i3 = -1;
        }
        if (i5 < 0) {
            if (iArr != null) {
                int i11 = this.occupiedCount;
                if (i11 * 4 < (1 << this.power) * 3) {
                    this.occupiedCount = i11 + 1;
                    i5 = i3;
                }
            }
            rehashTable(z2);
            return insertNewKey(i2);
        }
        iArr[i5] = i2;
        this.keyCount++;
        return i5;
    }

    private int findIndex(int i2) {
        int i3;
        int[] iArr = this.keys;
        if (iArr != null) {
            int i4 = A * i2;
            int i5 = this.power;
            int i6 = i4 >>> (32 - i5);
            int i7 = iArr[i6];
            if (i7 == i2) {
                return i6;
            }
            if (i7 != -1) {
                int i8 = (1 << i5) - 1;
                int iTableLookupStep = tableLookupStep(i4, i8, i5);
                do {
                    i6 = (i6 + iTableLookupStep) & i8;
                    i3 = iArr[i6];
                    if (i3 == i2) {
                        return i6;
                    }
                } while (i3 != -1);
            }
        }
        return -1;
    }

    private int insertNewKey(int i2) {
        int[] iArr = this.keys;
        int i3 = A * i2;
        int i4 = this.power;
        int i5 = i3 >>> (32 - i4);
        if (iArr[i5] != -1) {
            int i6 = (1 << i4) - 1;
            int iTableLookupStep = tableLookupStep(i3, i6, i4);
            do {
                i5 = (i5 + iTableLookupStep) & i6;
            } while (iArr[i5] != -1);
        }
        iArr[i5] = i2;
        this.occupiedCount++;
        this.keyCount++;
        return i5;
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        int i2 = this.keyCount;
        if (i2 != 0) {
            this.keyCount = 0;
            boolean z2 = objectInputStream.readBoolean();
            boolean z3 = objectInputStream.readBoolean();
            int i3 = 1 << this.power;
            if (z2) {
                this.keys = new int[i3 * 2];
                this.ivaluesShift = i3;
            } else {
                this.keys = new int[i3];
            }
            for (int i4 = 0; i4 != i3; i4++) {
                this.keys[i4] = -1;
            }
            if (z3) {
                this.values = new Object[i3];
            }
            for (int i5 = 0; i5 != i2; i5++) {
                int iInsertNewKey = insertNewKey(objectInputStream.readInt());
                if (z2) {
                    this.keys[this.ivaluesShift + iInsertNewKey] = objectInputStream.readInt();
                }
                if (z3) {
                    this.values[iInsertNewKey] = objectInputStream.readObject();
                }
            }
        }
    }

    private void rehashTable(boolean z2) {
        int[] iArr = this.keys;
        if (iArr != null && this.keyCount * 2 >= this.occupiedCount) {
            this.power++;
        }
        int i2 = 1 << this.power;
        int i3 = this.ivaluesShift;
        if (i3 != 0 || z2) {
            this.ivaluesShift = i2;
            this.keys = new int[i2 * 2];
        } else {
            this.keys = new int[i2];
        }
        int i4 = 0;
        for (int i5 = 0; i5 != i2; i5++) {
            this.keys[i5] = -1;
        }
        Object[] objArr = this.values;
        if (objArr != null) {
            this.values = new Object[i2];
        }
        int i6 = this.keyCount;
        this.occupiedCount = 0;
        if (i6 != 0) {
            this.keyCount = 0;
            while (i6 != 0) {
                int i7 = iArr[i4];
                if (i7 != -1 && i7 != -2) {
                    int iInsertNewKey = insertNewKey(i7);
                    if (objArr != null) {
                        this.values[iInsertNewKey] = objArr[i4];
                    }
                    if (i3 != 0) {
                        this.keys[this.ivaluesShift + iInsertNewKey] = iArr[i3 + i4];
                    }
                    i6--;
                }
                i4++;
            }
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
        if (i2 != 0) {
            int i3 = 0;
            boolean z2 = this.ivaluesShift != 0;
            boolean z3 = this.values != null;
            objectOutputStream.writeBoolean(z2);
            objectOutputStream.writeBoolean(z3);
            while (i2 != 0) {
                int i4 = this.keys[i3];
                if (i4 != -1 && i4 != -2) {
                    i2--;
                    objectOutputStream.writeInt(i4);
                    if (z2) {
                        objectOutputStream.writeInt(this.keys[this.ivaluesShift + i3]);
                    }
                    if (z3) {
                        objectOutputStream.writeObject(this.values[i3]);
                    }
                }
                i3++;
            }
        }
    }

    public void clear() {
        int i2 = 1 << this.power;
        if (this.keys != null) {
            for (int i3 = 0; i3 != i2; i3++) {
                this.keys[i3] = -1;
            }
            if (this.values != null) {
                for (int i4 = 0; i4 != i2; i4++) {
                    this.values[i4] = null;
                }
            }
        }
        this.ivaluesShift = 0;
        this.keyCount = 0;
        this.occupiedCount = 0;
    }

    public int getExistingInt(int i2) throws RuntimeException {
        if (i2 < 0) {
            Kit.codeBug();
        }
        int iFindIndex = findIndex(i2);
        if (iFindIndex < 0) {
            Kit.codeBug();
            return 0;
        }
        int i3 = this.ivaluesShift;
        if (i3 != 0) {
            return this.keys[i3 + iFindIndex];
        }
        return 0;
    }

    public int getInt(int i2, int i3) throws RuntimeException {
        if (i2 < 0) {
            Kit.codeBug();
        }
        int iFindIndex = findIndex(i2);
        if (iFindIndex < 0) {
            return i3;
        }
        int i4 = this.ivaluesShift;
        if (i4 != 0) {
            return this.keys[i4 + iFindIndex];
        }
        return 0;
    }

    public int[] getKeys() {
        int[] iArr = this.keys;
        int i2 = this.keyCount;
        int[] iArr2 = new int[i2];
        int i3 = 0;
        while (i2 != 0) {
            int i4 = iArr[i3];
            if (i4 != -1 && i4 != -2) {
                i2--;
                iArr2[i2] = i4;
            }
            i3++;
        }
        return iArr2;
    }

    public Object getObject(int i2) throws RuntimeException {
        int iFindIndex;
        if (i2 < 0) {
            Kit.codeBug();
        }
        if (this.values == null || (iFindIndex = findIndex(i2)) < 0) {
            return null;
        }
        return this.values[iFindIndex];
    }

    public boolean has(int i2) throws RuntimeException {
        if (i2 < 0) {
            Kit.codeBug();
        }
        return findIndex(i2) >= 0;
    }

    public boolean isEmpty() {
        return this.keyCount == 0;
    }

    public void put(int i2, Object obj) throws RuntimeException {
        if (i2 < 0) {
            Kit.codeBug();
        }
        int iEnsureIndex = ensureIndex(i2, false);
        if (this.values == null) {
            this.values = new Object[1 << this.power];
        }
        this.values[iEnsureIndex] = obj;
    }

    public void remove(int i2) throws RuntimeException {
        if (i2 < 0) {
            Kit.codeBug();
        }
        int iFindIndex = findIndex(i2);
        if (iFindIndex >= 0) {
            int[] iArr = this.keys;
            iArr[iFindIndex] = -2;
            this.keyCount--;
            Object[] objArr = this.values;
            if (objArr != null) {
                objArr[iFindIndex] = null;
            }
            int i3 = this.ivaluesShift;
            if (i3 != 0) {
                iArr[i3 + iFindIndex] = 0;
            }
        }
    }

    public int size() {
        return this.keyCount;
    }

    public UintMap(int i2) throws RuntimeException {
        if (i2 < 0) {
            Kit.codeBug();
        }
        int i3 = 2;
        while ((1 << i3) < (i2 * 4) / 3) {
            i3++;
        }
        this.power = i3;
    }

    public void put(int i2, int i3) throws RuntimeException {
        if (i2 < 0) {
            Kit.codeBug();
        }
        int iEnsureIndex = ensureIndex(i2, true);
        if (this.ivaluesShift == 0) {
            int i4 = 1 << this.power;
            int[] iArr = this.keys;
            int i5 = i4 * 2;
            if (iArr.length != i5) {
                int[] iArr2 = new int[i5];
                System.arraycopy(iArr, 0, iArr2, 0, i4);
                this.keys = iArr2;
            }
            this.ivaluesShift = i4;
        }
        this.keys[this.ivaluesShift + iEnsureIndex] = i3;
    }
}
