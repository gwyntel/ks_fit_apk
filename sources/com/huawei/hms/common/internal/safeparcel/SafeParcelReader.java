package com.huawei.hms.common.internal.safeparcel;

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
import java.util.List;

/* loaded from: classes4.dex */
public class SafeParcelReader {

    public static class ParseException extends RuntimeException {
        public ParseException(String str, Parcel parcel) {
            StringBuffer stringBuffer = new StringBuffer(String.valueOf(str).length() + 41);
            stringBuffer.append(str);
            stringBuffer.append(" Parcel: pos=");
            stringBuffer.append(parcel.dataPosition());
            stringBuffer.append(" size=");
            stringBuffer.append(parcel.dataSize());
            super(stringBuffer.toString());
        }
    }

    private SafeParcelReader() {
    }

    private static boolean a(int i2, int i3) {
        long j2 = i2 + i3;
        return j2 > 2147483647L || j2 < -2147483648L;
    }

    private static void b(Parcel parcel, int i2, int i3) {
        int size = readSize(parcel, i2);
        if (size == i3) {
            return;
        }
        String hexString = Integer.toHexString(size);
        StringBuilder sb = new StringBuilder(hexString.length() + 46);
        sb.append("Expected size ");
        sb.append(i3);
        sb.append(" got ");
        sb.append(size);
        sb.append(" (0x");
        sb.append(hexString);
        sb.append(")");
        throw new ParseException(sb.toString(), parcel);
    }

    public static BigDecimal createBigDecimal(Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        a(parcel, size, iDataPosition);
        byte[] bArrCreateByteArray = parcel.createByteArray();
        int i3 = parcel.readInt();
        parcel.setDataPosition(size + iDataPosition);
        return new BigDecimal(new BigInteger(bArrCreateByteArray), i3);
    }

    public static BigDecimal[] createBigDecimalArray(Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return new BigDecimal[0];
        }
        a(parcel, size, iDataPosition);
        int i3 = parcel.readInt();
        a(parcel, i3);
        BigDecimal[] bigDecimalArr = new BigDecimal[i3];
        for (int i4 = 0; i4 < i3; i4++) {
            bigDecimalArr[i4] = new BigDecimal(new BigInteger(parcel.createByteArray()), parcel.readInt());
        }
        parcel.setDataPosition(iDataPosition + size);
        return bigDecimalArr;
    }

    public static BigInteger createBigInteger(Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        a(parcel, size, iDataPosition);
        byte[] bArrCreateByteArray = parcel.createByteArray();
        parcel.setDataPosition(size + iDataPosition);
        return new BigInteger(bArrCreateByteArray);
    }

    public static BigInteger[] createBigIntegerArray(Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return new BigInteger[0];
        }
        a(parcel, size, iDataPosition);
        int i3 = parcel.readInt();
        a(parcel, i3);
        BigInteger[] bigIntegerArr = new BigInteger[i3];
        for (int i4 = 0; i4 < i3; i4++) {
            bigIntegerArr[i4] = new BigInteger(parcel.createByteArray());
        }
        parcel.setDataPosition(iDataPosition + size);
        return bigIntegerArr;
    }

    public static boolean[] createBooleanArray(Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return new boolean[0];
        }
        a(parcel, size, iDataPosition);
        boolean[] zArrCreateBooleanArray = parcel.createBooleanArray();
        parcel.setDataPosition(size + iDataPosition);
        return zArrCreateBooleanArray;
    }

    public static ArrayList<Boolean> createBooleanList(Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        a(parcel, size, iDataPosition);
        ArrayList<Boolean> arrayList = new ArrayList<>();
        int i3 = parcel.readInt();
        a(parcel, i3);
        for (int i4 = 0; i4 < i3; i4++) {
            arrayList.add(Boolean.valueOf(parcel.readInt() != 0));
        }
        parcel.setDataPosition(iDataPosition + size);
        return arrayList;
    }

    public static Bundle createBundle(Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        a(parcel, size, iDataPosition);
        Bundle bundle = parcel.readBundle();
        parcel.setDataPosition(size + iDataPosition);
        return bundle;
    }

    public static byte[] createByteArray(Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return new byte[0];
        }
        a(parcel, size, iDataPosition);
        byte[] bArrCreateByteArray = parcel.createByteArray();
        parcel.setDataPosition(size + iDataPosition);
        return bArrCreateByteArray;
    }

    public static byte[][] createByteArrayArray(Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        a(parcel, size, iDataPosition);
        int i3 = parcel.readInt();
        a(parcel, i3);
        byte[][] bArr = new byte[i3][];
        for (int i4 = 0; i4 < i3; i4++) {
            bArr[i4] = parcel.createByteArray();
        }
        parcel.setDataPosition(iDataPosition + size);
        return bArr;
    }

    public static SparseArray<byte[]> createByteArraySparseArray(Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        a(parcel, size, iDataPosition);
        int i3 = parcel.readInt();
        a(parcel, i3);
        SparseArray<byte[]> sparseArray = new SparseArray<>(i3);
        for (int i4 = 0; i4 < i3; i4++) {
            sparseArray.append(parcel.readInt(), parcel.createByteArray());
        }
        parcel.setDataPosition(iDataPosition + size);
        return sparseArray;
    }

    public static char[] createCharArray(Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return new char[0];
        }
        a(parcel, size, iDataPosition);
        char[] cArrCreateCharArray = parcel.createCharArray();
        parcel.setDataPosition(size + iDataPosition);
        return cArrCreateCharArray;
    }

    public static double[] createDoubleArray(Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return new double[0];
        }
        a(parcel, size, iDataPosition);
        double[] dArrCreateDoubleArray = parcel.createDoubleArray();
        parcel.setDataPosition(size + iDataPosition);
        return dArrCreateDoubleArray;
    }

    public static ArrayList<Double> createDoubleList(Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        a(parcel, size, iDataPosition);
        ArrayList<Double> arrayList = new ArrayList<>();
        int i3 = parcel.readInt();
        a(parcel, i3);
        for (int i4 = 0; i4 < i3; i4++) {
            arrayList.add(Double.valueOf(parcel.readDouble()));
        }
        parcel.setDataPosition(iDataPosition + size);
        return arrayList;
    }

    public static SparseArray<Double> createDoubleSparseArray(Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        a(parcel, size, iDataPosition);
        SparseArray<Double> sparseArray = new SparseArray<>();
        int i3 = parcel.readInt();
        a(parcel, i3);
        for (int i4 = 0; i4 < i3; i4++) {
            sparseArray.append(parcel.readInt(), Double.valueOf(parcel.readDouble()));
        }
        parcel.setDataPosition(iDataPosition + size);
        return sparseArray;
    }

    public static float[] createFloatArray(Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return new float[0];
        }
        a(parcel, size, iDataPosition);
        float[] fArrCreateFloatArray = parcel.createFloatArray();
        parcel.setDataPosition(size + iDataPosition);
        return fArrCreateFloatArray;
    }

    public static ArrayList<Float> createFloatList(Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        a(parcel, size, iDataPosition);
        ArrayList<Float> arrayList = new ArrayList<>();
        int i3 = parcel.readInt();
        a(parcel, i3);
        for (int i4 = 0; i4 < i3; i4++) {
            arrayList.add(Float.valueOf(parcel.readFloat()));
        }
        parcel.setDataPosition(iDataPosition + size);
        return arrayList;
    }

    public static SparseArray<Float> createFloatSparseArray(Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        a(parcel, size, iDataPosition);
        SparseArray<Float> sparseArray = new SparseArray<>();
        int i3 = parcel.readInt();
        a(parcel, i3);
        for (int i4 = 0; i4 < i3; i4++) {
            sparseArray.append(parcel.readInt(), Float.valueOf(parcel.readFloat()));
        }
        parcel.setDataPosition(iDataPosition + size);
        return sparseArray;
    }

    public static IBinder[] createIBinderArray(Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return new IBinder[0];
        }
        a(parcel, size, iDataPosition);
        IBinder[] iBinderArrCreateBinderArray = parcel.createBinderArray();
        parcel.setDataPosition(size + iDataPosition);
        return iBinderArrCreateBinderArray;
    }

    public static ArrayList<IBinder> createIBinderList(Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        a(parcel, size, iDataPosition);
        ArrayList<IBinder> arrayListCreateBinderArrayList = parcel.createBinderArrayList();
        parcel.setDataPosition(size + iDataPosition);
        return arrayListCreateBinderArrayList;
    }

    public static SparseArray<IBinder> createIBinderSparseArray(Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        a(parcel, size, iDataPosition);
        int i3 = parcel.readInt();
        a(parcel, i3);
        SparseArray<IBinder> sparseArray = new SparseArray<>(i3);
        for (int i4 = 0; i4 < i3; i4++) {
            sparseArray.append(parcel.readInt(), parcel.readStrongBinder());
        }
        parcel.setDataPosition(iDataPosition + size);
        return sparseArray;
    }

    public static int[] createIntArray(Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return new int[0];
        }
        a(parcel, size, iDataPosition);
        int[] iArrCreateIntArray = parcel.createIntArray();
        parcel.setDataPosition(size + iDataPosition);
        return iArrCreateIntArray;
    }

    public static ArrayList<Integer> createIntegerList(Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        a(parcel, size, iDataPosition);
        ArrayList<Integer> arrayList = new ArrayList<>();
        int i3 = parcel.readInt();
        a(parcel, i3);
        for (int i4 = 0; i4 < i3; i4++) {
            arrayList.add(Integer.valueOf(parcel.readInt()));
        }
        parcel.setDataPosition(iDataPosition + size);
        return arrayList;
    }

    public static long[] createLongArray(Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return new long[0];
        }
        a(parcel, size, iDataPosition);
        long[] jArrCreateLongArray = parcel.createLongArray();
        parcel.setDataPosition(size + iDataPosition);
        return jArrCreateLongArray;
    }

    public static ArrayList<Long> createLongList(Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        a(parcel, size, iDataPosition);
        ArrayList<Long> arrayList = new ArrayList<>();
        int i3 = parcel.readInt();
        a(parcel, i3);
        for (int i4 = 0; i4 < i3; i4++) {
            arrayList.add(Long.valueOf(parcel.readLong()));
        }
        parcel.setDataPosition(iDataPosition + size);
        return arrayList;
    }

    public static Parcel createParcel(Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        a(parcel, size, iDataPosition);
        Parcel parcelObtain = Parcel.obtain();
        parcelObtain.appendFrom(parcel, iDataPosition, size);
        parcel.setDataPosition(size + iDataPosition);
        return parcelObtain;
    }

    public static Parcel[] createParcelArray(Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return new Parcel[0];
        }
        a(parcel, size, iDataPosition);
        int i3 = parcel.readInt();
        a(parcel, i3);
        Parcel[] parcelArr = new Parcel[i3];
        for (int i4 = 0; i4 < i3; i4++) {
            int i5 = parcel.readInt();
            if (i5 == 0) {
                parcelArr[i4] = null;
            } else {
                int iDataPosition2 = parcel.dataPosition();
                a(parcel, i5, iDataPosition2);
                Parcel parcelObtain = Parcel.obtain();
                parcelObtain.appendFrom(parcel, iDataPosition2, i5);
                parcelArr[i4] = parcelObtain;
                parcel.setDataPosition(i5 + iDataPosition2);
            }
        }
        parcel.setDataPosition(iDataPosition + size);
        return parcelArr;
    }

    public static ArrayList<Parcel> createParcelList(Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        a(parcel, size, iDataPosition);
        int i3 = parcel.readInt();
        a(parcel, i3);
        ArrayList<Parcel> arrayList = new ArrayList<>();
        for (int i4 = 0; i4 < i3; i4++) {
            int i5 = parcel.readInt();
            if (i5 == 0) {
                arrayList.add(null);
            } else {
                int iDataPosition2 = parcel.dataPosition();
                a(parcel, i5, iDataPosition2);
                Parcel parcelObtain = Parcel.obtain();
                parcelObtain.appendFrom(parcel, iDataPosition2, i5);
                arrayList.add(parcelObtain);
                parcel.setDataPosition(i5 + iDataPosition2);
            }
        }
        parcel.setDataPosition(iDataPosition + size);
        return arrayList;
    }

    public static SparseArray<Parcel> createParcelSparseArray(Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        a(parcel, size, iDataPosition);
        int i3 = parcel.readInt();
        a(parcel, i3);
        SparseArray<Parcel> sparseArray = new SparseArray<>();
        for (int i4 = 0; i4 < i3; i4++) {
            int i5 = parcel.readInt();
            int i6 = parcel.readInt();
            if (i6 == 0) {
                sparseArray.append(i5, null);
            } else {
                int iDataPosition2 = parcel.dataPosition();
                a(parcel, i6, iDataPosition2);
                Parcel parcelObtain = Parcel.obtain();
                parcelObtain.appendFrom(parcel, iDataPosition2, i6);
                sparseArray.append(i5, parcelObtain);
                parcel.setDataPosition(iDataPosition2 + i6);
            }
        }
        parcel.setDataPosition(iDataPosition + size);
        return sparseArray;
    }

    public static <P extends Parcelable> P createParcelable(Parcel parcel, int i2, Parcelable.Creator<P> creator) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        a(parcel, size, iDataPosition);
        P pCreateFromParcel = creator.createFromParcel(parcel);
        parcel.setDataPosition(size + iDataPosition);
        return pCreateFromParcel;
    }

    public static SparseBooleanArray createSparseBooleanArray(Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        a(parcel, size, iDataPosition);
        SparseBooleanArray sparseBooleanArray = parcel.readSparseBooleanArray();
        parcel.setDataPosition(size + iDataPosition);
        return sparseBooleanArray;
    }

    public static SparseIntArray createSparseIntArray(Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        a(parcel, size, iDataPosition);
        SparseIntArray sparseIntArray = new SparseIntArray();
        int i3 = parcel.readInt();
        a(parcel, i3);
        for (int i4 = 0; i4 < i3; i4++) {
            sparseIntArray.append(parcel.readInt(), parcel.readInt());
        }
        parcel.setDataPosition(iDataPosition + size);
        return sparseIntArray;
    }

    public static SparseLongArray createSparseLongArray(Parcel parcel, int i2) {
        if (parcel == null) {
            return null;
        }
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        a(parcel, size, iDataPosition);
        SparseLongArray sparseLongArray = new SparseLongArray();
        int i3 = parcel.readInt();
        a(parcel, i3);
        for (int i4 = 0; i4 < i3; i4++) {
            sparseLongArray.append(parcel.readInt(), parcel.readLong());
        }
        parcel.setDataPosition(iDataPosition + size);
        return sparseLongArray;
    }

    public static String createString(Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        a(parcel, size, iDataPosition);
        String string = parcel.readString();
        parcel.setDataPosition(size + iDataPosition);
        return string;
    }

    public static String[] createStringArray(Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return new String[0];
        }
        a(parcel, size, iDataPosition);
        String[] strArrCreateStringArray = parcel.createStringArray();
        parcel.setDataPosition(size + iDataPosition);
        return strArrCreateStringArray;
    }

    public static ArrayList<String> createStringList(Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        a(parcel, size, iDataPosition);
        ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
        parcel.setDataPosition(size + iDataPosition);
        return arrayListCreateStringArrayList;
    }

    public static SparseArray<String> createStringSparseArray(Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        a(parcel, size, iDataPosition);
        SparseArray<String> sparseArray = new SparseArray<>();
        int i3 = parcel.readInt();
        a(parcel, i3);
        for (int i4 = 0; i4 < i3; i4++) {
            sparseArray.append(parcel.readInt(), parcel.readString());
        }
        parcel.setDataPosition(iDataPosition + size);
        return sparseArray;
    }

    public static <C> C[] createTypedArray(Parcel parcel, int i2, Parcelable.Creator<C> creator) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return creator.newArray(0);
        }
        a(parcel, size, iDataPosition);
        C[] cArr = (C[]) parcel.createTypedArray(creator);
        parcel.setDataPosition(size + iDataPosition);
        return cArr;
    }

    public static <C> ArrayList<C> createTypedList(Parcel parcel, int i2, Parcelable.Creator<C> creator) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        a(parcel, size, iDataPosition);
        ArrayList<C> arrayListCreateTypedArrayList = parcel.createTypedArrayList(creator);
        parcel.setDataPosition(size + iDataPosition);
        return arrayListCreateTypedArrayList;
    }

    public static <C> SparseArray<C> createTypedSparseArray(Parcel parcel, int i2, Parcelable.Creator<C> creator) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        a(parcel, size, iDataPosition);
        int i3 = parcel.readInt();
        a(parcel, i3);
        SparseArray<C> sparseArray = new SparseArray<>();
        for (int i4 = 0; i4 < i3; i4++) {
            sparseArray.append(parcel.readInt(), parcel.readInt() != 0 ? creator.createFromParcel(parcel) : null);
        }
        parcel.setDataPosition(iDataPosition + size);
        return sparseArray;
    }

    public static void ensureAtEnd(Parcel parcel, int i2) {
        if (parcel.dataPosition() == i2) {
            return;
        }
        throw new ParseException("Overread allowed size end=" + i2, parcel);
    }

    public static int getFieldId(int i2) {
        return i2 & 65535;
    }

    public static boolean readBoolean(Parcel parcel, int i2) {
        b(parcel, i2, 4);
        return parcel.readInt() != 0;
    }

    public static Boolean readBooleanObject(Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        if (size == 0) {
            return Boolean.FALSE;
        }
        a(parcel, i2, size, 4);
        return Boolean.valueOf(parcel.readInt() != 0);
    }

    public static byte readByte(Parcel parcel, int i2) {
        b(parcel, i2, 4);
        return (byte) parcel.readInt();
    }

    public static char readChar(Parcel parcel, int i2) {
        b(parcel, i2, 4);
        return (char) parcel.readInt();
    }

    public static double readDouble(Parcel parcel, int i2) {
        b(parcel, i2, 8);
        return parcel.readDouble();
    }

    public static Double readDoubleObject(Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        if (size == 0) {
            return null;
        }
        a(parcel, i2, size, 8);
        return Double.valueOf(parcel.readDouble());
    }

    public static float readFloat(Parcel parcel, int i2) {
        b(parcel, i2, 4);
        return parcel.readFloat();
    }

    public static Float readFloatObject(Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        if (size == 0) {
            return null;
        }
        a(parcel, i2, size, 4);
        return Float.valueOf(parcel.readFloat());
    }

    public static int readHeader(Parcel parcel) {
        return parcel.readInt();
    }

    public static IBinder readIBinder(Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        a(parcel, size, iDataPosition);
        IBinder strongBinder = parcel.readStrongBinder();
        parcel.setDataPosition(size + iDataPosition);
        return strongBinder;
    }

    public static int readInt(Parcel parcel, int i2) {
        b(parcel, i2, 4);
        return parcel.readInt();
    }

    public static Integer readIntegerObject(Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        if (size == 0) {
            return null;
        }
        a(parcel, i2, size, 4);
        return Integer.valueOf(parcel.readInt());
    }

    public static void readList(Parcel parcel, int i2, List list, ClassLoader classLoader) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size != 0) {
            a(parcel, size, iDataPosition);
            parcel.readList(list, classLoader);
            parcel.setDataPosition(size + iDataPosition);
        }
    }

    public static long readLong(Parcel parcel, int i2) {
        b(parcel, i2, 8);
        return parcel.readLong();
    }

    public static Long readLongObject(Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        if (size == 0) {
            return null;
        }
        a(parcel, i2, size, 8);
        return Long.valueOf(parcel.readLong());
    }

    public static short readShort(Parcel parcel, int i2) {
        b(parcel, i2, 4);
        return (short) parcel.readInt();
    }

    public static int readSize(Parcel parcel, int i2) {
        return (i2 & SupportMenu.CATEGORY_MASK) != -65536 ? (i2 >> 16) & 65535 : parcel.readInt();
    }

    public static void skipUnknownField(Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        a(parcel, size, iDataPosition);
        parcel.setDataPosition(size + iDataPosition);
    }

    public static int validateObjectHeader(Parcel parcel) {
        int header = readHeader(parcel);
        int size = readSize(parcel, header);
        int iDataPosition = parcel.dataPosition();
        if (getFieldId(header) != 20293) {
            String hexString = Integer.toHexString(header);
            throw new ParseException(hexString.length() != 0 ? "Expected object header. Got 0x".concat(hexString) : "Expected object header. Got 0x", parcel);
        }
        int i2 = size + iDataPosition;
        if (i2 >= iDataPosition && i2 <= parcel.dataSize()) {
            return i2;
        }
        throw new ParseException("invalid start=" + iDataPosition + " end=" + i2, parcel);
    }

    private static void a(Parcel parcel, int i2, int i3, int i4) {
        if (i3 == i4) {
            return;
        }
        String hexString = Integer.toHexString(i3);
        StringBuilder sb = new StringBuilder(hexString.length() + 46);
        sb.append("Expected size ");
        sb.append(i4);
        sb.append(" got ");
        sb.append(i3);
        sb.append(" (0x");
        sb.append(hexString);
        sb.append(")");
        throw new ParseException(sb.toString(), parcel);
    }

    private static void a(Parcel parcel, int i2, int i3) {
        if (i2 < 0 || a(i2, i3)) {
            throw new ParseException("dataPosition cannot be beyond integer scope", parcel);
        }
    }

    private static void a(Parcel parcel, int i2) {
        if (i2 > 1024) {
            throw new ParseException("arraySize cannot be beyond 65535", parcel);
        }
    }
}
