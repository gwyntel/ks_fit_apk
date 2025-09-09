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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes4.dex */
public class ParcelReader {

    /* renamed from: c, reason: collision with root package name */
    public static final int f16000c = 0;

    /* renamed from: d, reason: collision with root package name */
    public static final int f16001d = 1;

    /* renamed from: e, reason: collision with root package name */
    public static final int f16002e = 65262;

    /* renamed from: a, reason: collision with root package name */
    public HashMap<Integer, Integer[]> f16003a = new HashMap<>();

    /* renamed from: b, reason: collision with root package name */
    public Parcel f16004b;

    public class ParseException extends RuntimeException {
        public ParseException(String str, Parcel parcel) {
            super(str);
        }
    }

    public ParcelReader(Parcel parcel) {
        this.f16004b = parcel;
        a();
    }

    private int a(int i2) {
        if (i2 < 0) {
            return 0;
        }
        if (i2 > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        return i2;
    }

    private int b(int i2) {
        Integer[] numArr = this.f16003a.get(Integer.valueOf(i2));
        if (numArr != null) {
            this.f16004b.setDataPosition(numArr[0].intValue());
            return numArr[1].intValue();
        }
        throw new ParseException("Field is null:" + numArr, this.f16004b);
    }

    public BigDecimal createBigDecimal(int i2, BigDecimal bigDecimal) {
        if (!this.f16003a.containsKey(Integer.valueOf(i2))) {
            return bigDecimal;
        }
        int iB = b(i2);
        if (iB == 0) {
            return null;
        }
        int iDataPosition = this.f16004b.dataPosition();
        byte[] bArrCreateByteArray = this.f16004b.createByteArray();
        int i3 = this.f16004b.readInt();
        this.f16004b.setDataPosition(iDataPosition + iB);
        return new BigDecimal(new BigInteger(bArrCreateByteArray), i3);
    }

    public BigDecimal[] createBigDecimalArray(int i2, BigDecimal[] bigDecimalArr) {
        if (!this.f16003a.containsKey(Integer.valueOf(i2))) {
            return bigDecimalArr;
        }
        int iB = b(i2);
        if (iB == 0) {
            return null;
        }
        int iDataPosition = this.f16004b.dataPosition();
        int iA = a(this.f16004b.readInt());
        BigDecimal[] bigDecimalArr2 = new BigDecimal[iA];
        for (int i3 = 0; i3 < iA; i3++) {
            byte[] bArrCreateByteArray = this.f16004b.createByteArray();
            bigDecimalArr2[i3] = new BigDecimal(new BigInteger(bArrCreateByteArray), this.f16004b.readInt());
        }
        this.f16004b.setDataPosition(iDataPosition + iB);
        return bigDecimalArr2;
    }

    public BigInteger createBigInteger(int i2, BigInteger bigInteger) {
        if (!this.f16003a.containsKey(Integer.valueOf(i2))) {
            return bigInteger;
        }
        int iB = b(i2);
        if (iB == 0) {
            return null;
        }
        int iDataPosition = this.f16004b.dataPosition();
        byte[] bArrCreateByteArray = this.f16004b.createByteArray();
        this.f16004b.setDataPosition(iDataPosition + iB);
        return new BigInteger(bArrCreateByteArray);
    }

    public BigInteger[] createBigIntegerArray(int i2, BigInteger[] bigIntegerArr) {
        if (!this.f16003a.containsKey(Integer.valueOf(i2))) {
            return bigIntegerArr;
        }
        int iB = b(i2);
        if (iB == 0) {
            return null;
        }
        int iDataPosition = this.f16004b.dataPosition();
        int iA = a(this.f16004b.readInt());
        BigInteger[] bigIntegerArr2 = new BigInteger[iA];
        for (int i3 = 0; i3 < iA; i3++) {
            bigIntegerArr2[i3] = new BigInteger(this.f16004b.createByteArray());
        }
        this.f16004b.setDataPosition(iDataPosition + iB);
        return bigIntegerArr2;
    }

    public boolean[] createBooleanArray(int i2, boolean[] zArr) {
        if (!this.f16003a.containsKey(Integer.valueOf(i2))) {
            return zArr;
        }
        int iB = b(i2);
        if (iB == 0) {
            return null;
        }
        int iDataPosition = this.f16004b.dataPosition();
        boolean[] zArrCreateBooleanArray = this.f16004b.createBooleanArray();
        this.f16004b.setDataPosition(iDataPosition + iB);
        return zArrCreateBooleanArray;
    }

    public ArrayList<Boolean> createBooleanList(int i2, ArrayList<Boolean> arrayList) {
        if (!this.f16003a.containsKey(Integer.valueOf(i2))) {
            return arrayList;
        }
        int iB = b(i2);
        if (iB == 0) {
            return null;
        }
        int iDataPosition = this.f16004b.dataPosition();
        ArrayList<Boolean> arrayList2 = new ArrayList<>();
        int iA = a(this.f16004b.readInt());
        for (int i3 = 0; i3 < iA; i3++) {
            arrayList2.add(Boolean.valueOf(this.f16004b.readInt() != 0));
        }
        this.f16004b.setDataPosition(iDataPosition + iB);
        return arrayList2;
    }

    public byte[] createByteArray(int i2, byte[] bArr) {
        if (!this.f16003a.containsKey(Integer.valueOf(i2))) {
            return bArr;
        }
        int iB = b(i2);
        if (iB == 0) {
            return null;
        }
        int iDataPosition = this.f16004b.dataPosition();
        byte[] bArrCreateByteArray = this.f16004b.createByteArray();
        this.f16004b.setDataPosition(iDataPosition + iB);
        return bArrCreateByteArray;
    }

    public byte[][] createByteArrayArray(int i2, byte[][] bArr) {
        if (!this.f16003a.containsKey(Integer.valueOf(i2))) {
            return bArr;
        }
        int iB = b(i2);
        if (iB == 0) {
            return null;
        }
        int iDataPosition = this.f16004b.dataPosition();
        int iA = a(this.f16004b.readInt());
        byte[][] bArr2 = new byte[iA][];
        for (int i3 = 0; i3 < iA; i3++) {
            bArr2[i3] = this.f16004b.createByteArray();
        }
        this.f16004b.setDataPosition(iDataPosition + iB);
        return bArr2;
    }

    public SparseArray<byte[]> createByteArraySparseArray(int i2, SparseArray<byte[]> sparseArray) {
        if (!this.f16003a.containsKey(Integer.valueOf(i2))) {
            return sparseArray;
        }
        int iB = b(i2);
        if (iB == 0) {
            return null;
        }
        int iDataPosition = this.f16004b.dataPosition();
        int iA = a(this.f16004b.readInt());
        SparseArray<byte[]> sparseArray2 = new SparseArray<>(iA);
        for (int i3 = 0; i3 < iA; i3++) {
            sparseArray2.append(this.f16004b.readInt(), this.f16004b.createByteArray());
        }
        this.f16004b.setDataPosition(iDataPosition + iB);
        return sparseArray2;
    }

    public char[] createCharArray(int i2, char[] cArr) {
        if (!this.f16003a.containsKey(Integer.valueOf(i2))) {
            return cArr;
        }
        int iB = b(i2);
        if (iB == 0) {
            return null;
        }
        int iDataPosition = this.f16004b.dataPosition();
        char[] cArrCreateCharArray = this.f16004b.createCharArray();
        this.f16004b.setDataPosition(iDataPosition + iB);
        return cArrCreateCharArray;
    }

    public double[] createDoubleArray(int i2, double[] dArr) {
        if (!this.f16003a.containsKey(Integer.valueOf(i2))) {
            return dArr;
        }
        int iB = b(i2);
        if (iB == 0) {
            return null;
        }
        int iDataPosition = this.f16004b.dataPosition();
        double[] dArrCreateDoubleArray = this.f16004b.createDoubleArray();
        this.f16004b.setDataPosition(iDataPosition + iB);
        return dArrCreateDoubleArray;
    }

    public ArrayList<Double> createDoubleList(int i2, ArrayList<Double> arrayList) {
        if (!this.f16003a.containsKey(Integer.valueOf(i2))) {
            return arrayList;
        }
        int iB = b(i2);
        if (iB == 0) {
            return null;
        }
        int iDataPosition = this.f16004b.dataPosition();
        ArrayList<Double> arrayList2 = new ArrayList<>();
        int iA = a(this.f16004b.readInt());
        for (int i3 = 0; i3 < iA; i3++) {
            arrayList2.add(Double.valueOf(this.f16004b.readDouble()));
        }
        this.f16004b.setDataPosition(iDataPosition + iB);
        return arrayList2;
    }

    public SparseArray<Double> createDoubleSparseArray(int i2, SparseArray<Double> sparseArray) {
        if (!this.f16003a.containsKey(Integer.valueOf(i2))) {
            return sparseArray;
        }
        int iB = b(i2);
        if (iB == 0) {
            return null;
        }
        int iDataPosition = this.f16004b.dataPosition();
        SparseArray<Double> sparseArray2 = new SparseArray<>();
        int iA = a(this.f16004b.readInt());
        for (int i3 = 0; i3 < iA; i3++) {
            sparseArray2.append(this.f16004b.readInt(), Double.valueOf(this.f16004b.readDouble()));
        }
        this.f16004b.setDataPosition(iDataPosition + iB);
        return sparseArray2;
    }

    public float[] createFloatArray(int i2, float[] fArr) {
        if (!this.f16003a.containsKey(Integer.valueOf(i2))) {
            return fArr;
        }
        int iB = b(i2);
        if (iB == 0) {
            return null;
        }
        int iDataPosition = this.f16004b.dataPosition();
        float[] fArrCreateFloatArray = this.f16004b.createFloatArray();
        this.f16004b.setDataPosition(iDataPosition + iB);
        return fArrCreateFloatArray;
    }

    public ArrayList<Float> createFloatList(int i2, ArrayList<Float> arrayList) {
        if (!this.f16003a.containsKey(Integer.valueOf(i2))) {
            return arrayList;
        }
        int iB = b(i2);
        if (iB == 0) {
            return null;
        }
        int iDataPosition = this.f16004b.dataPosition();
        ArrayList<Float> arrayList2 = new ArrayList<>();
        int iA = a(this.f16004b.readInt());
        for (int i3 = 0; i3 < iA; i3++) {
            arrayList2.add(Float.valueOf(this.f16004b.readFloat()));
        }
        this.f16004b.setDataPosition(iDataPosition + iB);
        return arrayList2;
    }

    public SparseArray<Float> createFloatSparseArray(int i2, SparseArray<Float> sparseArray) {
        if (!this.f16003a.containsKey(Integer.valueOf(i2))) {
            return sparseArray;
        }
        int iB = b(i2);
        if (iB == 0) {
            return null;
        }
        int iDataPosition = this.f16004b.dataPosition();
        SparseArray<Float> sparseArray2 = new SparseArray<>();
        int iA = a(this.f16004b.readInt());
        for (int i3 = 0; i3 < iA; i3++) {
            sparseArray2.append(this.f16004b.readInt(), Float.valueOf(this.f16004b.readFloat()));
        }
        this.f16004b.setDataPosition(iDataPosition + iB);
        return sparseArray2;
    }

    public IBinder[] createIBinderArray(int i2, IBinder[] iBinderArr) {
        if (!this.f16003a.containsKey(Integer.valueOf(i2))) {
            return iBinderArr;
        }
        int iB = b(i2);
        if (iB == 0) {
            return null;
        }
        int iDataPosition = this.f16004b.dataPosition();
        IBinder[] iBinderArrCreateBinderArray = this.f16004b.createBinderArray();
        this.f16004b.setDataPosition(iDataPosition + iB);
        return iBinderArrCreateBinderArray;
    }

    public ArrayList<IBinder> createIBinderList(int i2, ArrayList<IBinder> arrayList) {
        if (!this.f16003a.containsKey(Integer.valueOf(i2))) {
            return arrayList;
        }
        int iB = b(i2);
        if (iB == 0) {
            return null;
        }
        int iDataPosition = this.f16004b.dataPosition();
        ArrayList<IBinder> arrayListCreateBinderArrayList = this.f16004b.createBinderArrayList();
        this.f16004b.setDataPosition(iDataPosition + iB);
        return arrayListCreateBinderArrayList;
    }

    public SparseArray<IBinder> createIBinderSparseArray(int i2, SparseArray<IBinder> sparseArray) {
        if (!this.f16003a.containsKey(Integer.valueOf(i2))) {
            return sparseArray;
        }
        int iB = b(i2);
        if (iB == 0) {
            return null;
        }
        int iDataPosition = this.f16004b.dataPosition();
        int iA = a(this.f16004b.readInt());
        SparseArray<IBinder> sparseArray2 = new SparseArray<>(iA);
        for (int i3 = 0; i3 < iA; i3++) {
            sparseArray2.append(this.f16004b.readInt(), this.f16004b.readStrongBinder());
        }
        this.f16004b.setDataPosition(iDataPosition + iB);
        return sparseArray2;
    }

    public int[] createIntArray(int i2, int[] iArr) {
        if (!this.f16003a.containsKey(Integer.valueOf(i2))) {
            return iArr;
        }
        int iB = b(i2);
        if (iB == 0) {
            return null;
        }
        int iDataPosition = this.f16004b.dataPosition();
        int[] iArrCreateIntArray = this.f16004b.createIntArray();
        this.f16004b.setDataPosition(iDataPosition + iB);
        return iArrCreateIntArray;
    }

    public ArrayList<Integer> createIntegerList(int i2, ArrayList<Integer> arrayList) {
        if (!this.f16003a.containsKey(Integer.valueOf(i2))) {
            return arrayList;
        }
        int iB = b(i2);
        if (iB == 0) {
            return null;
        }
        int iDataPosition = this.f16004b.dataPosition();
        ArrayList<Integer> arrayList2 = new ArrayList<>();
        int iA = a(this.f16004b.readInt());
        for (int i3 = 0; i3 < iA; i3++) {
            arrayList2.add(Integer.valueOf(this.f16004b.readInt()));
        }
        this.f16004b.setDataPosition(iDataPosition + iB);
        return arrayList2;
    }

    public long[] createLongArray(int i2, long[] jArr) {
        if (!this.f16003a.containsKey(Integer.valueOf(i2))) {
            return jArr;
        }
        int iB = b(i2);
        if (iB == 0) {
            return null;
        }
        int iDataPosition = this.f16004b.dataPosition();
        long[] jArrCreateLongArray = this.f16004b.createLongArray();
        this.f16004b.setDataPosition(iDataPosition + iB);
        return jArrCreateLongArray;
    }

    public ArrayList<Long> createLongList(int i2, ArrayList<Long> arrayList) {
        if (!this.f16003a.containsKey(Integer.valueOf(i2))) {
            return arrayList;
        }
        int iB = b(i2);
        if (iB == 0) {
            return null;
        }
        int iDataPosition = this.f16004b.dataPosition();
        ArrayList<Long> arrayList2 = new ArrayList<>();
        int iA = a(this.f16004b.readInt());
        for (int i3 = 0; i3 < iA; i3++) {
            arrayList2.add(Long.valueOf(this.f16004b.readLong()));
        }
        this.f16004b.setDataPosition(iDataPosition + iB);
        return arrayList2;
    }

    public Parcel createParcel(int i2, Parcel parcel) {
        if (!this.f16003a.containsKey(Integer.valueOf(i2))) {
            return parcel;
        }
        int iB = b(i2);
        if (iB == 0) {
            return null;
        }
        int iDataPosition = this.f16004b.dataPosition();
        Parcel parcelObtain = Parcel.obtain();
        parcelObtain.appendFrom(this.f16004b, iDataPosition, iB);
        this.f16004b.setDataPosition(iDataPosition + iB);
        return parcelObtain;
    }

    public Parcel[] createParcelArray(int i2, Parcel[] parcelArr) {
        if (!this.f16003a.containsKey(Integer.valueOf(i2))) {
            return parcelArr;
        }
        int iB = b(i2);
        if (iB == 0) {
            return null;
        }
        int iDataPosition = this.f16004b.dataPosition();
        int iA = a(this.f16004b.readInt());
        Parcel[] parcelArr2 = new Parcel[iA];
        for (int i3 = 0; i3 < iA; i3++) {
            int i4 = this.f16004b.readInt();
            if (i4 != 0) {
                int iDataPosition2 = this.f16004b.dataPosition();
                Parcel parcelObtain = Parcel.obtain();
                parcelObtain.appendFrom(this.f16004b, iDataPosition2, i4);
                parcelArr2[i3] = parcelObtain;
                this.f16004b.setDataPosition(iDataPosition2 + i4);
            } else {
                parcelArr2[i3] = null;
            }
        }
        this.f16004b.setDataPosition(iDataPosition + iB);
        return parcelArr2;
    }

    public ArrayList<Parcel> createParcelList(int i2, ArrayList<Parcel> arrayList) {
        if (!this.f16003a.containsKey(Integer.valueOf(i2))) {
            return arrayList;
        }
        int iB = b(i2);
        if (iB == 0) {
            return null;
        }
        int iDataPosition = this.f16004b.dataPosition();
        int iA = a(this.f16004b.readInt());
        ArrayList<Parcel> arrayList2 = new ArrayList<>();
        for (int i3 = 0; i3 < iA; i3++) {
            int i4 = this.f16004b.readInt();
            if (i4 != 0) {
                int iDataPosition2 = this.f16004b.dataPosition();
                Parcel parcelObtain = Parcel.obtain();
                parcelObtain.appendFrom(this.f16004b, iDataPosition2, i4);
                arrayList2.add(parcelObtain);
                this.f16004b.setDataPosition(iDataPosition2 + i4);
            } else {
                arrayList2.add(null);
            }
        }
        this.f16004b.setDataPosition(iDataPosition + iB);
        return arrayList2;
    }

    public SparseArray<Parcel> createParcelSparseArray(int i2, SparseArray<Parcel> sparseArray) {
        if (!this.f16003a.containsKey(Integer.valueOf(i2))) {
            return sparseArray;
        }
        int iB = b(i2);
        if (iB == 0) {
            return null;
        }
        int iDataPosition = this.f16004b.dataPosition();
        int iA = a(this.f16004b.readInt());
        SparseArray<Parcel> sparseArray2 = new SparseArray<>();
        for (int i3 = 0; i3 < iA; i3++) {
            int i4 = this.f16004b.readInt();
            int i5 = this.f16004b.readInt();
            if (i5 != 0) {
                int iDataPosition2 = this.f16004b.dataPosition();
                Parcel parcelObtain = Parcel.obtain();
                parcelObtain.appendFrom(this.f16004b, iDataPosition2, i5);
                sparseArray2.append(i4, parcelObtain);
                this.f16004b.setDataPosition(iDataPosition2 + i5);
            } else {
                sparseArray2.append(i4, null);
            }
        }
        this.f16004b.setDataPosition(iDataPosition + iB);
        return sparseArray2;
    }

    public SparseBooleanArray createSparseBooleanArray(int i2, SparseBooleanArray sparseBooleanArray) {
        if (!this.f16003a.containsKey(Integer.valueOf(i2))) {
            return sparseBooleanArray;
        }
        int iB = b(i2);
        if (iB == 0) {
            return null;
        }
        int iDataPosition = this.f16004b.dataPosition();
        SparseBooleanArray sparseBooleanArray2 = this.f16004b.readSparseBooleanArray();
        this.f16004b.setDataPosition(iDataPosition + iB);
        return sparseBooleanArray2;
    }

    public SparseIntArray createSparseIntArray(int i2, SparseIntArray sparseIntArray) {
        if (!this.f16003a.containsKey(Integer.valueOf(i2))) {
            return sparseIntArray;
        }
        int iB = b(i2);
        if (iB == 0) {
            return null;
        }
        int iDataPosition = this.f16004b.dataPosition();
        SparseIntArray sparseIntArray2 = new SparseIntArray();
        int iA = a(this.f16004b.readInt());
        for (int i3 = 0; i3 < iA; i3++) {
            sparseIntArray2.append(this.f16004b.readInt(), this.f16004b.readInt());
        }
        this.f16004b.setDataPosition(iDataPosition + iB);
        return sparseIntArray2;
    }

    public SparseLongArray createSparseLongArray(int i2, SparseLongArray sparseLongArray) {
        if (!this.f16003a.containsKey(Integer.valueOf(i2))) {
            return sparseLongArray;
        }
        int iB = b(i2);
        if (iB == 0) {
            return null;
        }
        int iDataPosition = this.f16004b.dataPosition();
        SparseLongArray sparseLongArray2 = new SparseLongArray();
        int iA = a(this.f16004b.readInt());
        for (int i3 = 0; i3 < iA; i3++) {
            sparseLongArray2.append(this.f16004b.readInt(), this.f16004b.readLong());
        }
        this.f16004b.setDataPosition(iDataPosition + iB);
        return sparseLongArray2;
    }

    public String createString(int i2, String str) {
        if (!this.f16003a.containsKey(Integer.valueOf(i2))) {
            return str;
        }
        int iB = b(i2);
        if (iB == 0) {
            return null;
        }
        int iDataPosition = this.f16004b.dataPosition();
        String string = this.f16004b.readString();
        this.f16004b.setDataPosition(iDataPosition + iB);
        return string;
    }

    public String[] createStringArray(int i2, String[] strArr) {
        if (!this.f16003a.containsKey(Integer.valueOf(i2))) {
            return strArr;
        }
        int iB = b(i2);
        if (iB == 0) {
            return null;
        }
        int iDataPosition = this.f16004b.dataPosition();
        String[] strArrCreateStringArray = this.f16004b.createStringArray();
        this.f16004b.setDataPosition(iDataPosition + iB);
        return strArrCreateStringArray;
    }

    public ArrayList<String> createStringList(int i2, ArrayList<String> arrayList) {
        if (!this.f16003a.containsKey(Integer.valueOf(i2))) {
            return arrayList;
        }
        int iB = b(i2);
        if (iB == 0) {
            return null;
        }
        int iDataPosition = this.f16004b.dataPosition();
        ArrayList<String> arrayListCreateStringArrayList = this.f16004b.createStringArrayList();
        this.f16004b.setDataPosition(iDataPosition + iB);
        return arrayListCreateStringArrayList;
    }

    public SparseArray<String> createStringSparseArray(int i2, SparseArray<String> sparseArray) {
        if (!this.f16003a.containsKey(Integer.valueOf(i2))) {
            return sparseArray;
        }
        int iB = b(i2);
        if (iB == 0) {
            return null;
        }
        int iDataPosition = this.f16004b.dataPosition();
        SparseArray<String> sparseArray2 = new SparseArray<>();
        int iA = a(this.f16004b.readInt());
        for (int i3 = 0; i3 < iA; i3++) {
            sparseArray2.append(this.f16004b.readInt(), this.f16004b.readString());
        }
        this.f16004b.setDataPosition(iDataPosition + iB);
        return sparseArray2;
    }

    public <T> T[] createTypedArray(int i2, Parcelable.Creator<T> creator, T[] tArr) {
        if (!this.f16003a.containsKey(Integer.valueOf(i2))) {
            return tArr;
        }
        int iB = b(i2);
        if (iB == 0) {
            return null;
        }
        int iDataPosition = this.f16004b.dataPosition();
        T[] tArr2 = (T[]) this.f16004b.createTypedArray(creator);
        this.f16004b.setDataPosition(iDataPosition + iB);
        return tArr2;
    }

    public <T> ArrayList<T> createTypedList(int i2, Parcelable.Creator<T> creator, ArrayList<T> arrayList) {
        if (!this.f16003a.containsKey(Integer.valueOf(i2))) {
            return arrayList;
        }
        int iB = b(i2);
        if (iB == 0) {
            return null;
        }
        int iDataPosition = this.f16004b.dataPosition();
        ArrayList<T> arrayListCreateTypedArrayList = this.f16004b.createTypedArrayList(creator);
        this.f16004b.setDataPosition(iDataPosition + iB);
        return arrayListCreateTypedArrayList;
    }

    public <T> SparseArray<T> createTypedSparseArray(int i2, Parcelable.Creator<T> creator, SparseArray<T> sparseArray) {
        if (!this.f16003a.containsKey(Integer.valueOf(i2))) {
            return sparseArray;
        }
        int iB = b(i2);
        if (iB == 0) {
            return null;
        }
        int iDataPosition = this.f16004b.dataPosition();
        int iA = a(this.f16004b.readInt());
        SparseArray<T> sparseArray2 = new SparseArray<>();
        for (int i3 = 0; i3 < iA; i3++) {
            sparseArray2.append(this.f16004b.readInt(), this.f16004b.readInt() != 0 ? creator.createFromParcel(this.f16004b) : null);
        }
        this.f16004b.setDataPosition(iDataPosition + iB);
        return sparseArray2;
    }

    public boolean readBoolean(int i2, boolean z2) {
        if (!this.f16003a.containsKey(Integer.valueOf(i2))) {
            return z2;
        }
        b(i2, 4);
        return this.f16004b.readInt() != 0;
    }

    public Boolean readBooleanObject(int i2, Boolean bool) {
        if (!this.f16003a.containsKey(Integer.valueOf(i2))) {
            return bool;
        }
        if (b(i2) == 0) {
            return null;
        }
        a(i2, 4);
        int i3 = this.f16004b.readInt();
        if (i3 == 0) {
            return Boolean.FALSE;
        }
        if (i3 != 1) {
            return null;
        }
        return Boolean.TRUE;
    }

    public Bundle readBundle(int i2, Bundle bundle) {
        if (!this.f16003a.containsKey(Integer.valueOf(i2))) {
            return bundle;
        }
        int iB = b(i2);
        if (iB == 0) {
            return null;
        }
        int iDataPosition = this.f16004b.dataPosition();
        Bundle bundle2 = this.f16004b.readBundle();
        this.f16004b.setDataPosition(iDataPosition + iB);
        return bundle2;
    }

    public byte readByte(int i2, byte b2) {
        if (!this.f16003a.containsKey(Integer.valueOf(i2))) {
            return b2;
        }
        b(i2, 4);
        return (byte) this.f16004b.readInt();
    }

    public char readChar(int i2, char c2) {
        if (!this.f16003a.containsKey(Integer.valueOf(i2))) {
            return c2;
        }
        b(i2, 4);
        return (char) this.f16004b.readInt();
    }

    public double readDouble(int i2, double d2) {
        if (!this.f16003a.containsKey(Integer.valueOf(i2))) {
            return d2;
        }
        b(i2, 8);
        return this.f16004b.readDouble();
    }

    public Double readDoubleObject(int i2, Double d2) {
        if (!this.f16003a.containsKey(Integer.valueOf(i2))) {
            return d2;
        }
        int iB = b(i2);
        if (iB == 0) {
            return null;
        }
        a(iB, 8);
        return Double.valueOf(this.f16004b.readDouble());
    }

    public float readFloat(int i2, float f2) {
        if (!this.f16003a.containsKey(Integer.valueOf(i2))) {
            return f2;
        }
        b(i2, 4);
        return this.f16004b.readFloat();
    }

    public Float readFloatObject(int i2, Float f2) {
        if (!this.f16003a.containsKey(Integer.valueOf(i2))) {
            return f2;
        }
        if (b(i2) == 0) {
            return null;
        }
        a(i2, 4);
        return Float.valueOf(this.f16004b.readFloat());
    }

    public IBinder readIBinder(int i2, IBinder iBinder) {
        if (!this.f16003a.containsKey(Integer.valueOf(i2))) {
            return iBinder;
        }
        int iB = b(i2);
        if (iB == 0) {
            return null;
        }
        int iDataPosition = this.f16004b.dataPosition();
        IBinder strongBinder = this.f16004b.readStrongBinder();
        this.f16004b.setDataPosition(iDataPosition + iB);
        return strongBinder;
    }

    public int readInt(int i2, int i3) {
        if (!this.f16003a.containsKey(Integer.valueOf(i2))) {
            return i3;
        }
        b(i2, 4);
        return this.f16004b.readInt();
    }

    public Integer readIntegerObject(int i2, Integer num) {
        if (!this.f16003a.containsKey(Integer.valueOf(i2))) {
            return num;
        }
        int iB = b(i2);
        if (iB == 0) {
            return null;
        }
        a(iB, 4);
        return Integer.valueOf(this.f16004b.readInt());
    }

    public void readList(int i2, List list, ClassLoader classLoader) {
        if (this.f16003a.containsKey(Integer.valueOf(i2))) {
            int iB = b(i2);
            int iDataPosition = this.f16004b.dataPosition();
            if (iB != 0) {
                this.f16004b.readList(list, classLoader);
                this.f16004b.setDataPosition(iDataPosition + iB);
            }
        }
    }

    public long readLong(int i2, long j2) {
        if (!this.f16003a.containsKey(Integer.valueOf(i2))) {
            return j2;
        }
        b(i2, 8);
        return this.f16004b.readLong();
    }

    public Long readLongObject(int i2, Long l2) {
        if (!this.f16003a.containsKey(Integer.valueOf(i2))) {
            return l2;
        }
        int iB = b(i2);
        if (iB == 0) {
            return null;
        }
        a(iB, 8);
        return Long.valueOf(this.f16004b.readLong());
    }

    public <T extends Parcelable> T readParcelable(int i2, Parcelable.Creator<T> creator, T t2) {
        if (!this.f16003a.containsKey(Integer.valueOf(i2))) {
            return t2;
        }
        int iB = b(i2);
        if (iB == 0) {
            return null;
        }
        int iDataPosition = this.f16004b.dataPosition();
        T tCreateFromParcel = creator.createFromParcel(this.f16004b);
        this.f16004b.setDataPosition(iDataPosition + iB);
        return tCreateFromParcel;
    }

    public short readShort(int i2, short s2) {
        if (!this.f16003a.containsKey(Integer.valueOf(i2))) {
            return s2;
        }
        b(i2, 4);
        return (short) this.f16004b.readInt();
    }

    private void a() {
        int i2 = this.f16004b.readInt();
        int i3 = i2 & 65535;
        int i4 = (i2 & SupportMenu.CATEGORY_MASK) != -65536 ? (i2 >> 16) & 65535 : this.f16004b.readInt();
        if (i3 != 65262) {
            throw new ParseException("Parse header error, not 65262. Got 0x" + Integer.toHexString(i3), this.f16004b);
        }
        int iDataPosition = this.f16004b.dataPosition();
        int i5 = i4 + iDataPosition;
        if (i5 < iDataPosition || i5 > this.f16004b.dataSize()) {
            throw new ParseException("invalid size, start=" + iDataPosition + " end=" + i5, this.f16004b);
        }
        while (this.f16004b.dataPosition() < i5) {
            int i6 = this.f16004b.readInt();
            int i7 = i6 & 65535;
            int i8 = (i6 & SupportMenu.CATEGORY_MASK) != -65536 ? (i6 >> 16) & 65535 : this.f16004b.readInt();
            int iDataPosition2 = this.f16004b.dataPosition();
            this.f16003a.put(Integer.valueOf(i7), new Integer[]{Integer.valueOf(iDataPosition2), Integer.valueOf(i8)});
            this.f16004b.setDataPosition(iDataPosition2 + i8);
        }
        if (this.f16004b.dataPosition() == i5) {
            return;
        }
        throw new ParseException("the dataPosition is not" + i5, this.f16004b);
    }

    private int b(int i2, int i3) {
        Integer[] numArr = this.f16003a.get(Integer.valueOf(i2));
        if (numArr != null) {
            this.f16004b.setDataPosition(numArr[0].intValue());
            a(i2, i3);
            return i3;
        }
        throw new ParseException("Field is null:" + numArr, this.f16004b);
    }

    private void a(int i2, int i3) {
        Integer[] numArr = this.f16003a.get(Integer.valueOf(i2));
        if (numArr == null) {
            throw new ParseException("Field is null:" + numArr, this.f16004b);
        }
        int iIntValue = numArr[1].intValue();
        if (iIntValue == i3) {
            return;
        }
        throw new ParseException("the field size is not " + i3 + " got " + iIntValue + " (0x" + Integer.toHexString(iIntValue) + ")", this.f16004b);
    }
}
