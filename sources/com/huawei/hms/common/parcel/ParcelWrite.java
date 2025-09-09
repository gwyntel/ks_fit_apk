package com.huawei.hms.common.parcel;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;
import androidx.core.internal.view.SupportMenu;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/* loaded from: classes4.dex */
public class ParcelWrite {

    /* renamed from: b, reason: collision with root package name */
    public static final int f16006b = 65262;

    /* renamed from: a, reason: collision with root package name */
    public Parcel f16007a;

    public ParcelWrite(Parcel parcel) {
        this.f16007a = parcel;
    }

    private int a(int i2) {
        this.f16007a.writeInt(i2 | SupportMenu.CATEGORY_MASK);
        this.f16007a.writeInt(0);
        return this.f16007a.dataPosition();
    }

    private void b(int i2) {
        int iDataPosition = this.f16007a.dataPosition();
        this.f16007a.setDataPosition(i2 - 4);
        this.f16007a.writeInt(iDataPosition - i2);
        this.f16007a.setDataPosition(iDataPosition);
    }

    public int beginObjectHeader() {
        return a(65262);
    }

    public void finishObjectHeader(int i2) {
        b(i2);
    }

    public void writeBigDecimal(int i2, BigDecimal bigDecimal, boolean z2) {
        if (bigDecimal == null) {
            if (z2) {
                a(i2, 0);
            }
        } else {
            int iA = a(i2);
            this.f16007a.writeByteArray(bigDecimal.unscaledValue().toByteArray());
            this.f16007a.writeInt(bigDecimal.scale());
            b(iA);
        }
    }

    public void writeBigDecimalArray(int i2, BigDecimal[] bigDecimalArr, boolean z2) {
        if (bigDecimalArr == null) {
            if (z2) {
                a(i2, 0);
                return;
            }
            return;
        }
        int iA = a(i2);
        int length = bigDecimalArr.length;
        this.f16007a.writeInt(length);
        for (int i3 = 0; i3 < length; i3++) {
            this.f16007a.writeByteArray(bigDecimalArr[i3].unscaledValue().toByteArray());
            this.f16007a.writeInt(bigDecimalArr[i3].scale());
        }
        b(iA);
    }

    public void writeBigInteger(int i2, BigInteger bigInteger, boolean z2) {
        if (bigInteger == null) {
            if (z2) {
                a(i2, 0);
            }
        } else {
            int iA = a(i2);
            this.f16007a.writeByteArray(bigInteger.toByteArray());
            b(iA);
        }
    }

    public void writeBigIntegerArray(int i2, BigInteger[] bigIntegerArr, boolean z2) {
        if (bigIntegerArr == null) {
            if (z2) {
                a(i2, 0);
                return;
            }
            return;
        }
        int iA = a(i2);
        this.f16007a.writeInt(bigIntegerArr.length);
        for (BigInteger bigInteger : bigIntegerArr) {
            this.f16007a.writeByteArray(bigInteger.toByteArray());
        }
        b(iA);
    }

    public void writeBoolean(int i2, boolean z2) {
        a(i2, 4);
        this.f16007a.writeInt(z2 ? 1 : 0);
    }

    public void writeBooleanArray(int i2, boolean[] zArr, boolean z2) {
        if (zArr == null) {
            if (z2) {
                a(i2, 0);
            }
        } else {
            int iA = a(i2);
            this.f16007a.writeBooleanArray(zArr);
            b(iA);
        }
    }

    public void writeBooleanList(int i2, List<Boolean> list, boolean z2) {
        if (list == null) {
            if (z2) {
                a(i2, 0);
                return;
            }
            return;
        }
        int iA = a(i2);
        int size = list.size();
        this.f16007a.writeInt(size);
        for (int i3 = 0; i3 < size; i3++) {
            this.f16007a.writeInt(list.get(i3).booleanValue() ? 1 : 0);
        }
        b(iA);
    }

    public void writeBooleanObject(int i2, Boolean bool) {
        writeBooleanObject(i2, bool, false);
    }

    public void writeBundle(int i2, Bundle bundle, boolean z2) {
        if (bundle == null) {
            if (z2) {
                a(i2, 0);
            }
        } else {
            int iA = a(i2);
            this.f16007a.writeBundle(bundle);
            b(iA);
        }
    }

    public void writeByte(int i2, byte b2) {
        a(i2, 4);
        this.f16007a.writeInt(b2);
    }

    public void writeByteArray(int i2, byte[] bArr, boolean z2) {
        if (bArr == null) {
            if (z2) {
                a(i2, 0);
            }
        } else {
            int iA = a(i2);
            this.f16007a.writeByteArray(bArr);
            b(iA);
        }
    }

    public void writeByteArrayArray(int i2, byte[][] bArr, boolean z2) {
        if (bArr == null) {
            if (z2) {
                a(i2, 0);
                return;
            }
            return;
        }
        int iA = a(i2);
        this.f16007a.writeInt(bArr.length);
        for (byte[] bArr2 : bArr) {
            this.f16007a.writeByteArray(bArr2);
        }
        b(iA);
    }

    public void writeByteArraySparseArray(int i2, SparseArray<byte[]> sparseArray, boolean z2) {
        if (sparseArray == null) {
            if (z2) {
                a(i2, 0);
                return;
            }
            return;
        }
        int iA = a(i2);
        int size = sparseArray.size();
        this.f16007a.writeInt(size);
        for (int i3 = 0; i3 < size; i3++) {
            this.f16007a.writeInt(sparseArray.keyAt(i3));
            this.f16007a.writeByteArray(sparseArray.valueAt(i3));
        }
        b(iA);
    }

    public void writeChar(int i2, char c2) {
        a(i2, 4);
        this.f16007a.writeInt(c2);
    }

    public void writeCharArray(int i2, char[] cArr, boolean z2) {
        if (cArr == null) {
            if (z2) {
                a(i2, 0);
            }
        } else {
            int iA = a(i2);
            this.f16007a.writeCharArray(cArr);
            b(iA);
        }
    }

    public void writeDouble(int i2, double d2) {
        a(i2, 8);
        this.f16007a.writeDouble(d2);
    }

    public void writeDoubleArray(int i2, double[] dArr, boolean z2) {
        if (dArr == null) {
            if (z2) {
                a(i2, 0);
            }
        } else {
            int iA = a(i2);
            this.f16007a.writeDoubleArray(dArr);
            b(iA);
        }
    }

    public void writeDoubleList(int i2, List<Double> list, boolean z2) {
        if (list == null) {
            if (z2) {
                a(i2, 0);
                return;
            }
            return;
        }
        int iA = a(i2);
        int size = list.size();
        this.f16007a.writeInt(size);
        for (int i3 = 0; i3 < size; i3++) {
            this.f16007a.writeDouble(list.get(i3).doubleValue());
        }
        b(iA);
    }

    public void writeDoubleObject(int i2, Double d2, boolean z2) {
        if (d2 != null) {
            a(i2, 8);
            this.f16007a.writeDouble(d2.doubleValue());
        } else if (z2) {
            a(i2, 0);
        }
    }

    public void writeDoubleSparseArray(int i2, SparseArray<Double> sparseArray, boolean z2) {
        if (sparseArray == null) {
            if (z2) {
                a(i2, 0);
                return;
            }
            return;
        }
        int iA = a(i2);
        int size = sparseArray.size();
        this.f16007a.writeInt(size);
        for (int i3 = 0; i3 < size; i3++) {
            this.f16007a.writeInt(sparseArray.keyAt(i3));
            this.f16007a.writeDouble(sparseArray.valueAt(i3).doubleValue());
        }
        b(iA);
    }

    public void writeFloat(int i2, float f2) {
        a(i2, 4);
        this.f16007a.writeFloat(f2);
    }

    public void writeFloatArray(int i2, float[] fArr, boolean z2) {
        if (fArr == null) {
            if (z2) {
                a(i2, 0);
            }
        } else {
            int iA = a(i2);
            this.f16007a.writeFloatArray(fArr);
            b(iA);
        }
    }

    public void writeFloatList(int i2, List<Float> list, boolean z2) {
        if (list == null) {
            if (z2) {
                a(i2, 0);
                return;
            }
            return;
        }
        int iA = a(i2);
        int size = list.size();
        this.f16007a.writeInt(size);
        for (int i3 = 0; i3 < size; i3++) {
            this.f16007a.writeFloat(list.get(i3).floatValue());
        }
        b(iA);
    }

    public void writeFloatObject(int i2, Float f2, boolean z2) {
        if (f2 != null) {
            a(i2, 4);
            this.f16007a.writeFloat(f2.floatValue());
        } else if (z2) {
            a(i2, 0);
        }
    }

    public void writeFloatSparseArray(int i2, SparseArray<Float> sparseArray, boolean z2) {
        if (sparseArray == null) {
            if (z2) {
                a(i2, 0);
                return;
            }
            return;
        }
        int iA = a(i2);
        int size = sparseArray.size();
        this.f16007a.writeInt(size);
        for (int i3 = 0; i3 < size; i3++) {
            this.f16007a.writeInt(sparseArray.keyAt(i3));
            this.f16007a.writeFloat(sparseArray.valueAt(i3).floatValue());
        }
        b(iA);
    }

    public void writeIBinder(int i2, IBinder iBinder, boolean z2) {
        if (iBinder == null) {
            if (z2) {
                a(i2, 0);
            }
        } else {
            int iA = a(i2);
            this.f16007a.writeStrongBinder(iBinder);
            b(iA);
        }
    }

    public void writeIBinderArray(int i2, IBinder[] iBinderArr, boolean z2) {
        if (iBinderArr == null) {
            if (z2) {
                a(i2, 0);
            }
        } else {
            int iA = a(i2);
            this.f16007a.writeBinderArray(iBinderArr);
            b(iA);
        }
    }

    public void writeIBinderList(int i2, List<IBinder> list, boolean z2) {
        if (list == null) {
            if (z2) {
                a(i2, 0);
            }
        } else {
            int iA = a(i2);
            this.f16007a.writeBinderList(list);
            b(iA);
        }
    }

    public void writeIBinderSparseArray(int i2, SparseArray<IBinder> sparseArray, boolean z2) {
        if (sparseArray == null) {
            if (z2) {
                a(i2, 0);
                return;
            }
            return;
        }
        int iA = a(i2);
        int size = sparseArray.size();
        this.f16007a.writeInt(size);
        for (int i3 = 0; i3 < size; i3++) {
            this.f16007a.writeInt(sparseArray.keyAt(i3));
            this.f16007a.writeStrongBinder(sparseArray.valueAt(i3));
        }
        b(iA);
    }

    public void writeInt(int i2, int i3) {
        a(i2, 4);
        this.f16007a.writeInt(i3);
    }

    public void writeIntArray(int i2, int[] iArr, boolean z2) {
        if (iArr == null) {
            if (z2) {
                a(i2, 0);
            }
        } else {
            int iA = a(i2);
            this.f16007a.writeIntArray(iArr);
            b(iA);
        }
    }

    public void writeIntegerList(int i2, List<Integer> list, boolean z2) {
        if (list == null) {
            if (z2) {
                a(i2, 0);
                return;
            }
            return;
        }
        int iA = a(i2);
        int size = list.size();
        this.f16007a.writeInt(size);
        for (int i3 = 0; i3 < size; i3++) {
            this.f16007a.writeInt(list.get(i3).intValue());
        }
        b(iA);
    }

    public void writeIntegerObject(int i2, Integer num, boolean z2) {
        if (num != null) {
            a(i2, 4);
            this.f16007a.writeInt(num.intValue());
        } else if (z2) {
            a(i2, 0);
        }
    }

    public void writeList(int i2, List list, boolean z2) {
        if (list == null) {
            if (z2) {
                a(i2, 0);
            }
        } else {
            int iA = a(i2);
            this.f16007a.writeList(list);
            b(iA);
        }
    }

    public void writeLong(int i2, long j2) {
        a(i2, 8);
        this.f16007a.writeLong(j2);
    }

    public void writeLongArray(int i2, long[] jArr, boolean z2) {
        if (jArr == null) {
            if (z2) {
                a(i2, 0);
            }
        } else {
            int iA = a(i2);
            this.f16007a.writeLongArray(jArr);
            b(iA);
        }
    }

    public void writeLongList(int i2, List<Long> list, boolean z2) {
        if (list == null) {
            if (z2) {
                a(i2, 0);
                return;
            }
            return;
        }
        int iA = a(i2);
        int size = list.size();
        this.f16007a.writeInt(size);
        for (int i3 = 0; i3 < size; i3++) {
            this.f16007a.writeLong(list.get(i3).longValue());
        }
        b(iA);
    }

    public void writeLongObject(int i2, Long l2, boolean z2) {
        if (l2 != null) {
            a(i2, 8);
            this.f16007a.writeLong(l2.longValue());
        } else if (z2) {
            a(i2, 0);
        }
    }

    public void writeParcel(int i2, Parcel parcel, boolean z2) {
        if (parcel == null) {
            if (z2) {
                a(i2, 0);
            }
        } else {
            int iA = a(i2);
            this.f16007a.appendFrom(parcel, 0, parcel.dataSize());
            b(iA);
        }
    }

    public void writeParcelArray(int i2, Parcel[] parcelArr, boolean z2) {
        if (parcelArr == null) {
            if (z2) {
                a(i2, 0);
                return;
            }
            return;
        }
        int iA = a(i2);
        this.f16007a.writeInt(parcelArr.length);
        for (Parcel parcel : parcelArr) {
            if (parcel != null) {
                this.f16007a.writeInt(parcel.dataSize());
                this.f16007a.appendFrom(parcel, 0, parcel.dataSize());
            } else {
                this.f16007a.writeInt(0);
            }
        }
        b(iA);
    }

    public void writeParcelList(int i2, List<Parcel> list, boolean z2) {
        if (list == null) {
            if (z2) {
                a(i2, 0);
                return;
            }
            return;
        }
        int iA = a(i2);
        int size = list.size();
        this.f16007a.writeInt(size);
        for (int i3 = 0; i3 < size; i3++) {
            Parcel parcel = list.get(i3);
            if (parcel != null) {
                this.f16007a.writeInt(parcel.dataSize());
                this.f16007a.appendFrom(parcel, 0, parcel.dataSize());
            } else {
                this.f16007a.writeInt(0);
            }
        }
        b(iA);
    }

    public void writeParcelSparseArray(int i2, SparseArray<Parcel> sparseArray, boolean z2) {
        if (sparseArray == null) {
            if (z2) {
                a(i2, 0);
                return;
            }
            return;
        }
        int iA = a(i2);
        int size = sparseArray.size();
        this.f16007a.writeInt(size);
        for (int i3 = 0; i3 < size; i3++) {
            this.f16007a.writeInt(sparseArray.keyAt(i3));
            Parcel parcelValueAt = sparseArray.valueAt(i3);
            if (parcelValueAt != null) {
                this.f16007a.writeInt(parcelValueAt.dataSize());
                this.f16007a.appendFrom(parcelValueAt, 0, parcelValueAt.dataSize());
            } else {
                this.f16007a.writeInt(0);
            }
        }
        b(iA);
    }

    public void writeParcelable(int i2, Parcelable parcelable, int i3, boolean z2) {
        if (parcelable == null) {
            if (z2) {
                a(i2, 0);
            }
        } else {
            int iA = a(i2);
            parcelable.writeToParcel(this.f16007a, i3);
            b(iA);
        }
    }

    public void writeShort(int i2, short s2) {
        a(i2, 4);
        this.f16007a.writeInt(s2);
    }

    public void writeSparseBooleanArray(int i2, SparseBooleanArray sparseBooleanArray, boolean z2) {
        if (sparseBooleanArray == null) {
            if (z2) {
                a(i2, 0);
            }
        } else {
            int iA = a(i2);
            this.f16007a.writeSparseBooleanArray(sparseBooleanArray);
            b(iA);
        }
    }

    public void writeSparseIntArray(int i2, SparseIntArray sparseIntArray, boolean z2) {
        if (sparseIntArray == null) {
            if (z2) {
                a(i2, 0);
                return;
            }
            return;
        }
        int iA = a(i2);
        int size = sparseIntArray.size();
        this.f16007a.writeInt(size);
        for (int i3 = 0; i3 < size; i3++) {
            this.f16007a.writeInt(sparseIntArray.keyAt(i3));
            this.f16007a.writeInt(sparseIntArray.valueAt(i3));
        }
        b(iA);
    }

    public void writeSparseLongArray(int i2, SparseLongArray sparseLongArray, boolean z2) {
        if (sparseLongArray == null) {
            if (z2) {
                a(i2, 0);
                return;
            }
            return;
        }
        int iA = a(i2);
        int size = sparseLongArray.size();
        this.f16007a.writeInt(size);
        for (int i3 = 0; i3 < size; i3++) {
            this.f16007a.writeInt(sparseLongArray.keyAt(i3));
            this.f16007a.writeLong(sparseLongArray.valueAt(i3));
        }
        b(iA);
    }

    public void writeString(int i2, String str, boolean z2) {
        if (str == null) {
            if (z2) {
                a(i2, 0);
            }
        } else {
            int iA = a(i2);
            this.f16007a.writeString(str);
            b(iA);
        }
    }

    public void writeStringArray(int i2, String[] strArr, boolean z2) {
        if (strArr == null) {
            if (z2) {
                a(i2, 0);
            }
        } else {
            int iA = a(i2);
            this.f16007a.writeStringArray(strArr);
            b(iA);
        }
    }

    public void writeStringList(int i2, List<String> list, boolean z2) {
        if (list == null) {
            if (z2) {
                a(i2, 0);
            }
        } else {
            int iA = a(i2);
            this.f16007a.writeStringList(list);
            b(iA);
        }
    }

    public void writeStringSparseArray(int i2, SparseArray<String> sparseArray, boolean z2) {
        if (sparseArray == null) {
            if (z2) {
                a(i2, 0);
                return;
            }
            return;
        }
        int iA = a(i2);
        int size = sparseArray.size();
        this.f16007a.writeInt(size);
        for (int i3 = 0; i3 < size; i3++) {
            this.f16007a.writeInt(sparseArray.keyAt(i3));
            this.f16007a.writeString(sparseArray.valueAt(i3));
        }
        b(iA);
    }

    public <T extends Parcelable> void writeTypedArray(int i2, T[] tArr, int i3, boolean z2) {
        if (tArr == null) {
            if (z2) {
                a(i2, 0);
                return;
            }
            return;
        }
        int iA = a(i2);
        this.f16007a.writeInt(tArr.length);
        for (T t2 : tArr) {
            if (t2 == null) {
                this.f16007a.writeInt(0);
            } else {
                a((ParcelWrite) t2, i3);
            }
        }
        b(iA);
    }

    public <T extends Parcelable> void writeTypedList(int i2, List<T> list, boolean z2) {
        if (list == null) {
            if (z2) {
                a(i2, 0);
                return;
            }
            return;
        }
        int iA = a(i2);
        int size = list.size();
        this.f16007a.writeInt(size);
        for (int i3 = 0; i3 < size; i3++) {
            T t2 = list.get(i3);
            if (t2 == null) {
                this.f16007a.writeInt(0);
            } else {
                a((ParcelWrite) t2, 0);
            }
        }
        b(iA);
    }

    public <T extends Parcelable> void writeTypedSparseArray(int i2, SparseArray<T> sparseArray, boolean z2) {
        if (sparseArray == null) {
            if (z2) {
                a(i2, 0);
                return;
            }
            return;
        }
        int iA = a(i2);
        int size = sparseArray.size();
        this.f16007a.writeInt(size);
        for (int i3 = 0; i3 < size; i3++) {
            this.f16007a.writeInt(sparseArray.keyAt(i3));
            T tValueAt = sparseArray.valueAt(i3);
            if (tValueAt == null) {
                this.f16007a.writeInt(0);
            } else {
                a((ParcelWrite) tValueAt, 0);
            }
        }
        b(iA);
    }

    private void a(int i2, int i3) {
        if (i3 < 65535) {
            this.f16007a.writeInt(i2 | (i3 << 16));
        } else {
            this.f16007a.writeInt(i2 | SupportMenu.CATEGORY_MASK);
            this.f16007a.writeInt(i3);
        }
    }

    public void writeBooleanObject(int i2, Boolean bool, boolean z2) {
        if (bool != null) {
            a(i2, 4);
            this.f16007a.writeInt(bool.booleanValue() ? 1 : 0);
        } else if (z2) {
            a(i2, 0);
        }
    }

    private <T extends Parcelable> void a(T t2, int i2) {
        int iDataPosition = this.f16007a.dataPosition();
        this.f16007a.writeInt(1);
        int iDataPosition2 = this.f16007a.dataPosition();
        t2.writeToParcel(this.f16007a, i2);
        int iDataPosition3 = this.f16007a.dataPosition();
        this.f16007a.setDataPosition(iDataPosition);
        this.f16007a.writeInt(iDataPosition3 - iDataPosition2);
        this.f16007a.setDataPosition(iDataPosition3);
    }
}
