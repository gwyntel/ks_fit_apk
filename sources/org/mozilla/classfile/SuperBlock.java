package org.mozilla.classfile;

/* loaded from: classes5.dex */
final class SuperBlock {
    private int end;
    private int index;
    private boolean isInQueue;
    private boolean isInitialized;
    private int[] locals;
    private int[] stack;
    private int start;

    SuperBlock(int i2, int i3, int i4, int[] iArr) {
        this.index = i2;
        this.start = i3;
        this.end = i4;
        int[] iArr2 = new int[iArr.length];
        this.locals = iArr2;
        System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
        this.stack = new int[0];
        this.isInitialized = false;
        this.isInQueue = false;
    }

    private boolean mergeState(int[] iArr, int[] iArr2, int i2, ConstantPool constantPool) {
        boolean z2 = false;
        for (int i3 = 0; i3 < i2; i3++) {
            int i4 = iArr[i3];
            int iMerge = TypeInfo.merge(i4, iArr2[i3], constantPool);
            iArr[i3] = iMerge;
            if (i4 != iMerge) {
                z2 = true;
            }
        }
        return z2;
    }

    int getEnd() {
        return this.end;
    }

    int getIndex() {
        return this.index;
    }

    int[] getLocals() {
        int[] iArr = this.locals;
        int[] iArr2 = new int[iArr.length];
        System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
        return iArr2;
    }

    int[] getStack() {
        int[] iArr = this.stack;
        int[] iArr2 = new int[iArr.length];
        System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
        return iArr2;
    }

    int getStart() {
        return this.start;
    }

    int[] getTrimmedLocals() {
        int length = this.locals.length - 1;
        while (length >= 0) {
            int[] iArr = this.locals;
            if (iArr[length] != 0 || TypeInfo.isTwoWords(iArr[length - 1])) {
                break;
            }
            length--;
        }
        int i2 = length + 1;
        int i3 = 0;
        int i4 = i2;
        for (int i5 = 0; i5 < i2; i5++) {
            if (TypeInfo.isTwoWords(this.locals[i5])) {
                i4--;
            }
        }
        int[] iArr2 = new int[i4];
        int i6 = 0;
        while (i3 < i4) {
            int[] iArr3 = this.locals;
            iArr2[i3] = iArr3[i6];
            if (TypeInfo.isTwoWords(iArr3[i6])) {
                i6++;
            }
            i3++;
            i6++;
        }
        return iArr2;
    }

    boolean isInQueue() {
        return this.isInQueue;
    }

    boolean isInitialized() {
        return this.isInitialized;
    }

    boolean merge(int[] iArr, int i2, int[] iArr2, int i3, ConstantPool constantPool) {
        if (this.isInitialized) {
            int[] iArr3 = this.locals;
            if (iArr3.length == i2 && this.stack.length == i3) {
                return mergeState(iArr3, iArr, i2, constantPool) || mergeState(this.stack, iArr2, i3, constantPool);
            }
            throw new IllegalArgumentException("bad merge attempt");
        }
        System.arraycopy(iArr, 0, this.locals, 0, i2);
        int[] iArr4 = new int[i3];
        this.stack = iArr4;
        System.arraycopy(iArr2, 0, iArr4, 0, i3);
        this.isInitialized = true;
        return true;
    }

    void setInQueue(boolean z2) {
        this.isInQueue = z2;
    }

    void setInitialized(boolean z2) {
        this.isInitialized = z2;
    }

    public String toString() {
        return "sb " + this.index;
    }
}
