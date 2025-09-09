package androidx.health.platform.client.proto;

import androidx.health.platform.client.proto.Internal;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

/* loaded from: classes.dex */
final class FloatArrayList extends AbstractProtobufList<Float> implements Internal.FloatList, RandomAccess, PrimitiveNonBoxingCollection {
    private static final FloatArrayList EMPTY_LIST;
    private float[] array;
    private int size;

    static {
        FloatArrayList floatArrayList = new FloatArrayList(new float[0], 0);
        EMPTY_LIST = floatArrayList;
        floatArrayList.makeImmutable();
    }

    FloatArrayList() {
        this(new float[10], 0);
    }

    public static FloatArrayList emptyList() {
        return EMPTY_LIST;
    }

    private void ensureIndexInRange(int i2) {
        if (i2 < 0 || i2 >= this.size) {
            throw new IndexOutOfBoundsException(makeOutOfBoundsExceptionMessage(i2));
        }
    }

    private String makeOutOfBoundsExceptionMessage(int i2) {
        return "Index:" + i2 + ", Size:" + this.size;
    }

    @Override // androidx.health.platform.client.proto.AbstractProtobufList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean addAll(Collection<? extends Float> collection) {
        a();
        Internal.a(collection);
        if (!(collection instanceof FloatArrayList)) {
            return super.addAll(collection);
        }
        FloatArrayList floatArrayList = (FloatArrayList) collection;
        int i2 = floatArrayList.size;
        if (i2 == 0) {
            return false;
        }
        int i3 = this.size;
        if (Integer.MAX_VALUE - i3 < i2) {
            throw new OutOfMemoryError();
        }
        int i4 = i3 + i2;
        float[] fArr = this.array;
        if (i4 > fArr.length) {
            this.array = Arrays.copyOf(fArr, i4);
        }
        System.arraycopy(floatArrayList.array, 0, this.array, this.size, floatArrayList.size);
        this.size = i4;
        ((AbstractList) this).modCount++;
        return true;
    }

    @Override // androidx.health.platform.client.proto.Internal.FloatList
    public void addFloat(float f2) {
        a();
        int i2 = this.size;
        float[] fArr = this.array;
        if (i2 == fArr.length) {
            float[] fArr2 = new float[((i2 * 3) / 2) + 1];
            System.arraycopy(fArr, 0, fArr2, 0, i2);
            this.array = fArr2;
        }
        float[] fArr3 = this.array;
        int i3 = this.size;
        this.size = i3 + 1;
        fArr3[i3] = f2;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean contains(Object obj) {
        return indexOf(obj) != -1;
    }

    @Override // androidx.health.platform.client.proto.AbstractProtobufList, java.util.AbstractList, java.util.Collection, java.util.List
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FloatArrayList)) {
            return super.equals(obj);
        }
        FloatArrayList floatArrayList = (FloatArrayList) obj;
        if (this.size != floatArrayList.size) {
            return false;
        }
        float[] fArr = floatArrayList.array;
        for (int i2 = 0; i2 < this.size; i2++) {
            if (Float.floatToIntBits(this.array[i2]) != Float.floatToIntBits(fArr[i2])) {
                return false;
            }
        }
        return true;
    }

    @Override // androidx.health.platform.client.proto.Internal.FloatList
    public float getFloat(int i2) {
        ensureIndexInRange(i2);
        return this.array[i2];
    }

    @Override // androidx.health.platform.client.proto.AbstractProtobufList, java.util.AbstractList, java.util.Collection, java.util.List
    public int hashCode() {
        int iFloatToIntBits = 1;
        for (int i2 = 0; i2 < this.size; i2++) {
            iFloatToIntBits = (iFloatToIntBits * 31) + Float.floatToIntBits(this.array[i2]);
        }
        return iFloatToIntBits;
    }

    @Override // java.util.AbstractList, java.util.List
    public int indexOf(Object obj) {
        if (!(obj instanceof Float)) {
            return -1;
        }
        float fFloatValue = ((Float) obj).floatValue();
        int size = size();
        for (int i2 = 0; i2 < size; i2++) {
            if (this.array[i2] == fFloatValue) {
                return i2;
            }
        }
        return -1;
    }

    @Override // java.util.AbstractList
    protected void removeRange(int i2, int i3) {
        a();
        if (i3 < i2) {
            throw new IndexOutOfBoundsException("toIndex < fromIndex");
        }
        float[] fArr = this.array;
        System.arraycopy(fArr, i3, fArr, i2, this.size - i3);
        this.size -= i3 - i2;
        ((AbstractList) this).modCount++;
    }

    @Override // androidx.health.platform.client.proto.Internal.FloatList
    public float setFloat(int i2, float f2) {
        a();
        ensureIndexInRange(i2);
        float[] fArr = this.array;
        float f3 = fArr[i2];
        fArr[i2] = f2;
        return f3;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        return this.size;
    }

    private FloatArrayList(float[] fArr, int i2) {
        this.array = fArr;
        this.size = i2;
    }

    @Override // java.util.AbstractList, java.util.List
    public Float get(int i2) {
        return Float.valueOf(getFloat(i2));
    }

    @Override // androidx.health.platform.client.proto.Internal.ProtobufList, androidx.health.platform.client.proto.Internal.BooleanList
    /* renamed from: mutableCopyWithCapacity, reason: merged with bridge method [inline-methods] */
    public Internal.ProtobufList<Float> mutableCopyWithCapacity2(int i2) {
        if (i2 >= this.size) {
            return new FloatArrayList(Arrays.copyOf(this.array, i2), this.size);
        }
        throw new IllegalArgumentException();
    }

    @Override // androidx.health.platform.client.proto.AbstractProtobufList, java.util.AbstractList, java.util.List
    public Float remove(int i2) {
        a();
        ensureIndexInRange(i2);
        float[] fArr = this.array;
        float f2 = fArr[i2];
        if (i2 < this.size - 1) {
            System.arraycopy(fArr, i2 + 1, fArr, i2, (r2 - i2) - 1);
        }
        this.size--;
        ((AbstractList) this).modCount++;
        return Float.valueOf(f2);
    }

    @Override // androidx.health.platform.client.proto.AbstractProtobufList, java.util.AbstractList, java.util.List
    public Float set(int i2, Float f2) {
        return Float.valueOf(setFloat(i2, f2.floatValue()));
    }

    @Override // androidx.health.platform.client.proto.AbstractProtobufList, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean add(Float f2) {
        addFloat(f2.floatValue());
        return true;
    }

    @Override // androidx.health.platform.client.proto.AbstractProtobufList, java.util.AbstractList, java.util.List
    public void add(int i2, Float f2) {
        addFloat(i2, f2.floatValue());
    }

    private void addFloat(int i2, float f2) {
        int i3;
        a();
        if (i2 >= 0 && i2 <= (i3 = this.size)) {
            float[] fArr = this.array;
            if (i3 < fArr.length) {
                System.arraycopy(fArr, i2, fArr, i2 + 1, i3 - i2);
            } else {
                float[] fArr2 = new float[((i3 * 3) / 2) + 1];
                System.arraycopy(fArr, 0, fArr2, 0, i2);
                System.arraycopy(this.array, i2, fArr2, i2 + 1, this.size - i2);
                this.array = fArr2;
            }
            this.array[i2] = f2;
            this.size++;
            ((AbstractList) this).modCount++;
            return;
        }
        throw new IndexOutOfBoundsException(makeOutOfBoundsExceptionMessage(i2));
    }
}
