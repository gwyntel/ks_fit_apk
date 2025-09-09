package org.mozilla.javascript;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/* loaded from: classes5.dex */
public class ObjArray implements Serializable {
    private static final int FIELDS_STORE_SIZE = 5;
    static final long serialVersionUID = 4174889037736658296L;
    private transient Object[] data;

    /* renamed from: f0, reason: collision with root package name */
    private transient Object f26761f0;
    private transient Object f1;
    private transient Object f2;
    private transient Object f3;
    private transient Object f4;
    private boolean sealed;
    private int size;

    private void ensureCapacity(int i2) {
        int i3 = i2 - 5;
        if (i3 <= 0) {
            throw new IllegalArgumentException();
        }
        Object[] objArr = this.data;
        if (objArr == null) {
            if (10 >= i3) {
                i3 = 10;
            }
            this.data = new Object[i3];
            return;
        }
        int length = objArr.length;
        if (length < i3) {
            int i4 = length > 5 ? length * 2 : 10;
            if (i4 >= i3) {
                i3 = i4;
            }
            Object[] objArr2 = new Object[i3];
            int i5 = this.size;
            if (i5 > 5) {
                System.arraycopy(objArr, 0, objArr2, 0, i5 - 5);
            }
            this.data = objArr2;
        }
    }

    private Object getImpl(int i2) {
        return i2 != 0 ? i2 != 1 ? i2 != 2 ? i2 != 3 ? i2 != 4 ? this.data[i2 - 5] : this.f4 : this.f3 : this.f2 : this.f1 : this.f26761f0;
    }

    private static RuntimeException onEmptyStackTopRead() {
        throw new RuntimeException("Empty stack");
    }

    private static RuntimeException onInvalidIndex(int i2, int i3) {
        throw new IndexOutOfBoundsException(i2 + " ∉ [0, " + i3 + ')');
    }

    private static RuntimeException onSeledMutation() {
        throw new IllegalStateException("Attempt to modify sealed array");
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        int i2 = this.size;
        if (i2 > 5) {
            this.data = new Object[i2 - 5];
        }
        for (int i3 = 0; i3 != i2; i3++) {
            setImpl(i3, objectInputStream.readObject());
        }
    }

    private void setImpl(int i2, Object obj) {
        if (i2 == 0) {
            this.f26761f0 = obj;
            return;
        }
        if (i2 == 1) {
            this.f1 = obj;
            return;
        }
        if (i2 == 2) {
            this.f2 = obj;
            return;
        }
        if (i2 == 3) {
            this.f3 = obj;
        } else if (i2 != 4) {
            this.data[i2 - 5] = obj;
        } else {
            this.f4 = obj;
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        int i2 = this.size;
        for (int i3 = 0; i3 != i2; i3++) {
            objectOutputStream.writeObject(getImpl(i3));
        }
    }

    public final void add(Object obj) {
        if (this.sealed) {
            throw onSeledMutation();
        }
        int i2 = this.size;
        if (i2 >= 5) {
            ensureCapacity(i2 + 1);
        }
        this.size = i2 + 1;
        setImpl(i2, obj);
    }

    public final void clear() {
        if (this.sealed) {
            throw onSeledMutation();
        }
        int i2 = this.size;
        for (int i3 = 0; i3 != i2; i3++) {
            setImpl(i3, null);
        }
        this.size = 0;
    }

    public final Object get(int i2) {
        if (i2 < 0 || i2 >= this.size) {
            throw onInvalidIndex(i2, this.size);
        }
        return getImpl(i2);
    }

    public int indexOf(Object obj) {
        int i2 = this.size;
        for (int i3 = 0; i3 != i2; i3++) {
            Object impl = getImpl(i3);
            if (impl == obj || (impl != null && impl.equals(obj))) {
                return i3;
            }
        }
        return -1;
    }

    public final boolean isEmpty() {
        return this.size == 0;
    }

    public final boolean isSealed() {
        return this.sealed;
    }

    public int lastIndexOf(Object obj) {
        int i2 = this.size;
        while (i2 != 0) {
            i2--;
            Object impl = getImpl(i2);
            if (impl == obj || (impl != null && impl.equals(obj))) {
                return i2;
            }
        }
        return -1;
    }

    public final Object peek() {
        int i2 = this.size;
        if (i2 != 0) {
            return getImpl(i2 - 1);
        }
        throw onEmptyStackTopRead();
    }

    public final Object pop() {
        Object obj;
        if (this.sealed) {
            throw onSeledMutation();
        }
        int i2 = this.size;
        int i3 = i2 - 1;
        if (i3 == -1) {
            throw onEmptyStackTopRead();
        }
        if (i3 == 0) {
            obj = this.f26761f0;
            this.f26761f0 = null;
        } else if (i3 == 1) {
            obj = this.f1;
            this.f1 = null;
        } else if (i3 == 2) {
            obj = this.f2;
            this.f2 = null;
        } else if (i3 == 3) {
            obj = this.f3;
            this.f3 = null;
        } else if (i3 != 4) {
            Object[] objArr = this.data;
            int i4 = i2 - 6;
            obj = objArr[i4];
            objArr[i4] = null;
        } else {
            obj = this.f4;
            this.f4 = null;
        }
        this.size = i3;
        return obj;
    }

    public final void push(Object obj) {
        add(obj);
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0030  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0039  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x003c  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0042  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0045  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x004f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void remove(int r8) {
        /*
            r7 = this;
            int r0 = r7.size
            if (r8 < 0) goto L68
            if (r8 >= r0) goto L68
            boolean r1 = r7.sealed
            if (r1 != 0) goto L63
            int r1 = r0 + (-1)
            r2 = 3
            r3 = 2
            r4 = 1
            r5 = 4
            r6 = 0
            if (r8 == 0) goto L1c
            if (r8 == r4) goto L25
            if (r8 == r3) goto L2e
            if (r8 == r2) goto L37
            if (r8 == r5) goto L40
            goto L4d
        L1c:
            if (r1 != 0) goto L21
            r7.f26761f0 = r6
            goto L60
        L21:
            java.lang.Object r8 = r7.f1
            r7.f26761f0 = r8
        L25:
            if (r1 != r4) goto L2a
            r7.f1 = r6
            goto L60
        L2a:
            java.lang.Object r8 = r7.f2
            r7.f1 = r8
        L2e:
            if (r1 != r3) goto L33
            r7.f2 = r6
            goto L60
        L33:
            java.lang.Object r8 = r7.f3
            r7.f2 = r8
        L37:
            if (r1 != r2) goto L3c
            r7.f3 = r6
            goto L60
        L3c:
            java.lang.Object r8 = r7.f4
            r7.f3 = r8
        L40:
            if (r1 != r5) goto L45
            r7.f4 = r6
            goto L60
        L45:
            java.lang.Object[] r8 = r7.data
            r2 = 0
            r8 = r8[r2]
            r7.f4 = r8
            r8 = 5
        L4d:
            if (r8 == r1) goto L5a
            java.lang.Object[] r2 = r7.data
            int r3 = r8 + (-5)
            int r4 = r8 + (-4)
            int r8 = r1 - r8
            java.lang.System.arraycopy(r2, r4, r2, r3, r8)
        L5a:
            java.lang.Object[] r8 = r7.data
            int r0 = r0 + (-6)
            r8[r0] = r6
        L60:
            r7.size = r1
            return
        L63:
            java.lang.RuntimeException r8 = onSeledMutation()
            throw r8
        L68:
            java.lang.RuntimeException r8 = onInvalidIndex(r8, r0)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.ObjArray.remove(int):void");
    }

    public final void seal() {
        this.sealed = true;
    }

    public final void set(int i2, Object obj) {
        if (i2 < 0 || i2 >= this.size) {
            throw onInvalidIndex(i2, this.size);
        }
        if (this.sealed) {
            throw onSeledMutation();
        }
        setImpl(i2, obj);
    }

    public final void setSize(int i2) {
        if (i2 < 0) {
            throw new IllegalArgumentException();
        }
        if (this.sealed) {
            throw onSeledMutation();
        }
        int i3 = this.size;
        if (i2 < i3) {
            for (int i4 = i2; i4 != i3; i4++) {
                setImpl(i4, null);
            }
        } else if (i2 > i3 && i2 > 5) {
            ensureCapacity(i2);
        }
        this.size = i2;
    }

    public final int size() {
        return this.size;
    }

    public final Object[] toArray() {
        Object[] objArr = new Object[this.size];
        toArray(objArr, 0);
        return objArr;
    }

    public final void toArray(Object[] objArr) {
        toArray(objArr, 0);
    }

    public final void toArray(Object[] objArr, int i2) {
        int i3 = this.size;
        if (i3 != 0) {
            if (i3 != 1) {
                if (i3 != 2) {
                    if (i3 != 3) {
                        if (i3 != 4) {
                            if (i3 != 5) {
                                System.arraycopy(this.data, 0, objArr, i2 + 5, i3 - 5);
                            }
                            objArr[i2 + 4] = this.f4;
                        }
                        objArr[i2 + 3] = this.f3;
                    }
                    objArr[i2 + 2] = this.f2;
                }
                objArr[i2 + 1] = this.f1;
            }
            objArr[i2] = this.f26761f0;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0030  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x003a  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x003d  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0044  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0047  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0054  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void add(int r8, java.lang.Object r9) {
        /*
            r7 = this;
            int r0 = r7.size
            r1 = 1
            if (r8 < 0) goto L6d
            if (r8 > r0) goto L6d
            boolean r2 = r7.sealed
            if (r2 != 0) goto L68
            r2 = 3
            r3 = 2
            r4 = 5
            r5 = 4
            if (r8 == 0) goto L1a
            if (r8 == r1) goto L24
            if (r8 == r3) goto L2e
            if (r8 == r2) goto L38
            if (r8 == r5) goto L42
            goto L4d
        L1a:
            if (r0 != 0) goto L1f
            r7.f26761f0 = r9
            goto L64
        L1f:
            java.lang.Object r8 = r7.f26761f0
            r7.f26761f0 = r9
            r9 = r8
        L24:
            if (r0 != r1) goto L29
            r7.f1 = r9
            goto L64
        L29:
            java.lang.Object r8 = r7.f1
            r7.f1 = r9
            r9 = r8
        L2e:
            if (r0 != r3) goto L33
            r7.f2 = r9
            goto L64
        L33:
            java.lang.Object r8 = r7.f2
            r7.f2 = r9
            r9 = r8
        L38:
            if (r0 != r2) goto L3d
            r7.f3 = r9
            goto L64
        L3d:
            java.lang.Object r8 = r7.f3
            r7.f3 = r9
            r9 = r8
        L42:
            if (r0 != r5) goto L47
            r7.f4 = r9
            goto L64
        L47:
            java.lang.Object r8 = r7.f4
            r7.f4 = r9
            r9 = r8
            r8 = r4
        L4d:
            int r2 = r0 + 1
            r7.ensureCapacity(r2)
            if (r8 == r0) goto L5f
            java.lang.Object[] r2 = r7.data
            int r3 = r8 + (-5)
            int r5 = r8 + (-4)
            int r6 = r0 - r8
            java.lang.System.arraycopy(r2, r3, r2, r5, r6)
        L5f:
            java.lang.Object[] r2 = r7.data
            int r8 = r8 - r4
            r2[r8] = r9
        L64:
            int r0 = r0 + r1
            r7.size = r0
            return
        L68:
            java.lang.RuntimeException r8 = onSeledMutation()
            throw r8
        L6d:
            int r0 = r0 + r1
            java.lang.RuntimeException r8 = onInvalidIndex(r8, r0)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.ObjArray.add(int, java.lang.Object):void");
    }
}
