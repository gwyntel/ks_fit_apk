package com.google.android.gms.common.internal.safeparcel;

import android.app.PendingIntent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;
import androidx.annotation.NonNull;
import androidx.core.internal.view.SupportMenu;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class SafeParcelReader {

    public static class ParseException extends RuntimeException {
        public ParseException(@NonNull String str, @NonNull Parcel parcel) {
            super(str + " Parcel: pos=" + parcel.dataPosition() + " size=" + parcel.dataSize());
        }
    }

    private SafeParcelReader() {
    }

    @NonNull
    public static BigDecimal createBigDecimal(@NonNull Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        byte[] bArrCreateByteArray = parcel.createByteArray();
        int i3 = parcel.readInt();
        parcel.setDataPosition(iDataPosition + size);
        return new BigDecimal(new BigInteger(bArrCreateByteArray), i3);
    }

    @NonNull
    public static BigDecimal[] createBigDecimalArray(@NonNull Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        int i3 = parcel.readInt();
        BigDecimal[] bigDecimalArr = new BigDecimal[i3];
        for (int i4 = 0; i4 < i3; i4++) {
            byte[] bArrCreateByteArray = parcel.createByteArray();
            bigDecimalArr[i4] = new BigDecimal(new BigInteger(bArrCreateByteArray), parcel.readInt());
        }
        parcel.setDataPosition(iDataPosition + size);
        return bigDecimalArr;
    }

    @NonNull
    public static BigInteger createBigInteger(@NonNull Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        byte[] bArrCreateByteArray = parcel.createByteArray();
        parcel.setDataPosition(iDataPosition + size);
        return new BigInteger(bArrCreateByteArray);
    }

    @NonNull
    public static BigInteger[] createBigIntegerArray(@NonNull Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        int i3 = parcel.readInt();
        BigInteger[] bigIntegerArr = new BigInteger[i3];
        for (int i4 = 0; i4 < i3; i4++) {
            bigIntegerArr[i4] = new BigInteger(parcel.createByteArray());
        }
        parcel.setDataPosition(iDataPosition + size);
        return bigIntegerArr;
    }

    @NonNull
    public static boolean[] createBooleanArray(@NonNull Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        boolean[] zArrCreateBooleanArray = parcel.createBooleanArray();
        parcel.setDataPosition(iDataPosition + size);
        return zArrCreateBooleanArray;
    }

    @NonNull
    public static ArrayList<Boolean> createBooleanList(@NonNull Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        ArrayList<Boolean> arrayList = new ArrayList<>();
        int i3 = parcel.readInt();
        for (int i4 = 0; i4 < i3; i4++) {
            arrayList.add(Boolean.valueOf(parcel.readInt() != 0));
        }
        parcel.setDataPosition(iDataPosition + size);
        return arrayList;
    }

    @NonNull
    public static Bundle createBundle(@NonNull Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        Bundle bundle = parcel.readBundle();
        parcel.setDataPosition(iDataPosition + size);
        return bundle;
    }

    @NonNull
    public static byte[] createByteArray(@NonNull Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        byte[] bArrCreateByteArray = parcel.createByteArray();
        parcel.setDataPosition(iDataPosition + size);
        return bArrCreateByteArray;
    }

    @NonNull
    public static byte[][] createByteArrayArray(@NonNull Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        int i3 = parcel.readInt();
        byte[][] bArr = new byte[i3][];
        for (int i4 = 0; i4 < i3; i4++) {
            bArr[i4] = parcel.createByteArray();
        }
        parcel.setDataPosition(iDataPosition + size);
        return bArr;
    }

    @NonNull
    public static SparseArray<byte[]> createByteArraySparseArray(@NonNull Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        int i3 = parcel.readInt();
        SparseArray<byte[]> sparseArray = new SparseArray<>(i3);
        for (int i4 = 0; i4 < i3; i4++) {
            sparseArray.append(parcel.readInt(), parcel.createByteArray());
        }
        parcel.setDataPosition(iDataPosition + size);
        return sparseArray;
    }

    @NonNull
    public static char[] createCharArray(@NonNull Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        char[] cArrCreateCharArray = parcel.createCharArray();
        parcel.setDataPosition(iDataPosition + size);
        return cArrCreateCharArray;
    }

    @NonNull
    public static double[] createDoubleArray(@NonNull Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        double[] dArrCreateDoubleArray = parcel.createDoubleArray();
        parcel.setDataPosition(iDataPosition + size);
        return dArrCreateDoubleArray;
    }

    @NonNull
    public static ArrayList<Double> createDoubleList(@NonNull Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        ArrayList<Double> arrayList = new ArrayList<>();
        int i3 = parcel.readInt();
        for (int i4 = 0; i4 < i3; i4++) {
            arrayList.add(Double.valueOf(parcel.readDouble()));
        }
        parcel.setDataPosition(iDataPosition + size);
        return arrayList;
    }

    @NonNull
    public static SparseArray<Double> createDoubleSparseArray(@NonNull Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        SparseArray<Double> sparseArray = new SparseArray<>();
        int i3 = parcel.readInt();
        for (int i4 = 0; i4 < i3; i4++) {
            sparseArray.append(parcel.readInt(), Double.valueOf(parcel.readDouble()));
        }
        parcel.setDataPosition(iDataPosition + size);
        return sparseArray;
    }

    @NonNull
    public static float[] createFloatArray(@NonNull Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        float[] fArrCreateFloatArray = parcel.createFloatArray();
        parcel.setDataPosition(iDataPosition + size);
        return fArrCreateFloatArray;
    }

    @NonNull
    public static ArrayList<Float> createFloatList(@NonNull Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        ArrayList<Float> arrayList = new ArrayList<>();
        int i3 = parcel.readInt();
        for (int i4 = 0; i4 < i3; i4++) {
            arrayList.add(Float.valueOf(parcel.readFloat()));
        }
        parcel.setDataPosition(iDataPosition + size);
        return arrayList;
    }

    @NonNull
    public static SparseArray<Float> createFloatSparseArray(@NonNull Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        SparseArray<Float> sparseArray = new SparseArray<>();
        int i3 = parcel.readInt();
        for (int i4 = 0; i4 < i3; i4++) {
            sparseArray.append(parcel.readInt(), Float.valueOf(parcel.readFloat()));
        }
        parcel.setDataPosition(iDataPosition + size);
        return sparseArray;
    }

    @NonNull
    public static IBinder[] createIBinderArray(@NonNull Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        IBinder[] iBinderArrCreateBinderArray = parcel.createBinderArray();
        parcel.setDataPosition(iDataPosition + size);
        return iBinderArrCreateBinderArray;
    }

    @NonNull
    public static ArrayList<IBinder> createIBinderList(@NonNull Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        ArrayList<IBinder> arrayListCreateBinderArrayList = parcel.createBinderArrayList();
        parcel.setDataPosition(iDataPosition + size);
        return arrayListCreateBinderArrayList;
    }

    @NonNull
    public static SparseArray<IBinder> createIBinderSparseArray(@NonNull Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        int i3 = parcel.readInt();
        SparseArray<IBinder> sparseArray = new SparseArray<>(i3);
        for (int i4 = 0; i4 < i3; i4++) {
            sparseArray.append(parcel.readInt(), parcel.readStrongBinder());
        }
        parcel.setDataPosition(iDataPosition + size);
        return sparseArray;
    }

    @NonNull
    public static int[] createIntArray(@NonNull Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        int[] iArrCreateIntArray = parcel.createIntArray();
        parcel.setDataPosition(iDataPosition + size);
        return iArrCreateIntArray;
    }

    @NonNull
    public static ArrayList<Integer> createIntegerList(@NonNull Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        ArrayList<Integer> arrayList = new ArrayList<>();
        int i3 = parcel.readInt();
        for (int i4 = 0; i4 < i3; i4++) {
            arrayList.add(Integer.valueOf(parcel.readInt()));
        }
        parcel.setDataPosition(iDataPosition + size);
        return arrayList;
    }

    @NonNull
    public static long[] createLongArray(@NonNull Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        long[] jArrCreateLongArray = parcel.createLongArray();
        parcel.setDataPosition(iDataPosition + size);
        return jArrCreateLongArray;
    }

    @NonNull
    public static ArrayList<Long> createLongList(@NonNull Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        ArrayList<Long> arrayList = new ArrayList<>();
        int i3 = parcel.readInt();
        for (int i4 = 0; i4 < i3; i4++) {
            arrayList.add(Long.valueOf(parcel.readLong()));
        }
        parcel.setDataPosition(iDataPosition + size);
        return arrayList;
    }

    @NonNull
    public static Parcel createParcel(@NonNull Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        Parcel parcelObtain = Parcel.obtain();
        parcelObtain.appendFrom(parcel, iDataPosition, size);
        parcel.setDataPosition(iDataPosition + size);
        return parcelObtain;
    }

    @NonNull
    public static Parcel[] createParcelArray(@NonNull Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        int i3 = parcel.readInt();
        Parcel[] parcelArr = new Parcel[i3];
        for (int i4 = 0; i4 < i3; i4++) {
            int i5 = parcel.readInt();
            if (i5 != 0) {
                int iDataPosition2 = parcel.dataPosition();
                Parcel parcelObtain = Parcel.obtain();
                parcelObtain.appendFrom(parcel, iDataPosition2, i5);
                parcelArr[i4] = parcelObtain;
                parcel.setDataPosition(iDataPosition2 + i5);
            } else {
                parcelArr[i4] = null;
            }
        }
        parcel.setDataPosition(iDataPosition + size);
        return parcelArr;
    }

    @NonNull
    public static ArrayList<Parcel> createParcelList(@NonNull Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        int i3 = parcel.readInt();
        ArrayList<Parcel> arrayList = new ArrayList<>();
        for (int i4 = 0; i4 < i3; i4++) {
            int i5 = parcel.readInt();
            if (i5 != 0) {
                int iDataPosition2 = parcel.dataPosition();
                Parcel parcelObtain = Parcel.obtain();
                parcelObtain.appendFrom(parcel, iDataPosition2, i5);
                arrayList.add(parcelObtain);
                parcel.setDataPosition(iDataPosition2 + i5);
            } else {
                arrayList.add(null);
            }
        }
        parcel.setDataPosition(iDataPosition + size);
        return arrayList;
    }

    @NonNull
    public static SparseArray<Parcel> createParcelSparseArray(@NonNull Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        int i3 = parcel.readInt();
        SparseArray<Parcel> sparseArray = new SparseArray<>();
        for (int i4 = 0; i4 < i3; i4++) {
            int i5 = parcel.readInt();
            int i6 = parcel.readInt();
            if (i6 != 0) {
                int iDataPosition2 = parcel.dataPosition();
                Parcel parcelObtain = Parcel.obtain();
                parcelObtain.appendFrom(parcel, iDataPosition2, i6);
                sparseArray.append(i5, parcelObtain);
                parcel.setDataPosition(iDataPosition2 + i6);
            } else {
                sparseArray.append(i5, null);
            }
        }
        parcel.setDataPosition(iDataPosition + size);
        return sparseArray;
    }

    @NonNull
    public static <T extends Parcelable> T createParcelable(@NonNull Parcel parcel, int i2, @NonNull Parcelable.Creator<T> creator) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        T tCreateFromParcel = creator.createFromParcel(parcel);
        parcel.setDataPosition(iDataPosition + size);
        return tCreateFromParcel;
    }

    @NonNull
    public static SparseBooleanArray createSparseBooleanArray(@NonNull Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        SparseBooleanArray sparseBooleanArray = parcel.readSparseBooleanArray();
        parcel.setDataPosition(iDataPosition + size);
        return sparseBooleanArray;
    }

    @NonNull
    public static SparseIntArray createSparseIntArray(@NonNull Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        SparseIntArray sparseIntArray = new SparseIntArray();
        int i3 = parcel.readInt();
        for (int i4 = 0; i4 < i3; i4++) {
            sparseIntArray.append(parcel.readInt(), parcel.readInt());
        }
        parcel.setDataPosition(iDataPosition + size);
        return sparseIntArray;
    }

    @NonNull
    public static SparseLongArray createSparseLongArray(@NonNull Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        SparseLongArray sparseLongArray = new SparseLongArray();
        int i3 = parcel.readInt();
        for (int i4 = 0; i4 < i3; i4++) {
            sparseLongArray.append(parcel.readInt(), parcel.readLong());
        }
        parcel.setDataPosition(iDataPosition + size);
        return sparseLongArray;
    }

    @NonNull
    public static String createString(@NonNull Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        String string = parcel.readString();
        parcel.setDataPosition(iDataPosition + size);
        return string;
    }

    @NonNull
    public static String[] createStringArray(@NonNull Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        String[] strArrCreateStringArray = parcel.createStringArray();
        parcel.setDataPosition(iDataPosition + size);
        return strArrCreateStringArray;
    }

    @NonNull
    public static ArrayList<String> createStringList(@NonNull Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
        parcel.setDataPosition(iDataPosition + size);
        return arrayListCreateStringArrayList;
    }

    @NonNull
    public static SparseArray<String> createStringSparseArray(@NonNull Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        SparseArray<String> sparseArray = new SparseArray<>();
        int i3 = parcel.readInt();
        for (int i4 = 0; i4 < i3; i4++) {
            sparseArray.append(parcel.readInt(), parcel.readString());
        }
        parcel.setDataPosition(iDataPosition + size);
        return sparseArray;
    }

    @NonNull
    public static <T> T[] createTypedArray(@NonNull Parcel parcel, int i2, @NonNull Parcelable.Creator<T> creator) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        T[] tArr = (T[]) parcel.createTypedArray(creator);
        parcel.setDataPosition(iDataPosition + size);
        return tArr;
    }

    @NonNull
    public static <T> ArrayList<T> createTypedList(@NonNull Parcel parcel, int i2, @NonNull Parcelable.Creator<T> creator) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        ArrayList<T> arrayListCreateTypedArrayList = parcel.createTypedArrayList(creator);
        parcel.setDataPosition(iDataPosition + size);
        return arrayListCreateTypedArrayList;
    }

    @NonNull
    public static <T> SparseArray<T> createTypedSparseArray(@NonNull Parcel parcel, int i2, @NonNull Parcelable.Creator<T> creator) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        int i3 = parcel.readInt();
        SparseArray<T> sparseArray = new SparseArray<>();
        for (int i4 = 0; i4 < i3; i4++) {
            sparseArray.append(parcel.readInt(), parcel.readInt() != 0 ? creator.createFromParcel(parcel) : null);
        }
        parcel.setDataPosition(iDataPosition + size);
        return sparseArray;
    }

    public static void ensureAtEnd(@NonNull Parcel parcel, int i2) {
        if (parcel.dataPosition() == i2) {
            return;
        }
        throw new ParseException("Overread allowed size end=" + i2, parcel);
    }

    public static int getFieldId(int i2) {
        return (char) i2;
    }

    public static boolean readBoolean(@NonNull Parcel parcel, int i2) {
        zzb(parcel, i2, 4);
        return parcel.readInt() != 0;
    }

    @NonNull
    public static Boolean readBooleanObject(@NonNull Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        if (size == 0) {
            return null;
        }
        zza(parcel, i2, size, 4);
        return Boolean.valueOf(parcel.readInt() != 0);
    }

    public static byte readByte(@NonNull Parcel parcel, int i2) {
        zzb(parcel, i2, 4);
        return (byte) parcel.readInt();
    }

    public static char readChar(@NonNull Parcel parcel, int i2) {
        zzb(parcel, i2, 4);
        return (char) parcel.readInt();
    }

    public static double readDouble(@NonNull Parcel parcel, int i2) {
        zzb(parcel, i2, 8);
        return parcel.readDouble();
    }

    @NonNull
    public static Double readDoubleObject(@NonNull Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        if (size == 0) {
            return null;
        }
        zza(parcel, i2, size, 8);
        return Double.valueOf(parcel.readDouble());
    }

    public static float readFloat(@NonNull Parcel parcel, int i2) {
        zzb(parcel, i2, 4);
        return parcel.readFloat();
    }

    @NonNull
    public static Float readFloatObject(@NonNull Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        if (size == 0) {
            return null;
        }
        zza(parcel, i2, size, 4);
        return Float.valueOf(parcel.readFloat());
    }

    public static int readHeader(@NonNull Parcel parcel) {
        return parcel.readInt();
    }

    @NonNull
    public static IBinder readIBinder(@NonNull Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        IBinder strongBinder = parcel.readStrongBinder();
        parcel.setDataPosition(iDataPosition + size);
        return strongBinder;
    }

    public static int readInt(@NonNull Parcel parcel, int i2) {
        zzb(parcel, i2, 4);
        return parcel.readInt();
    }

    @NonNull
    public static Integer readIntegerObject(@NonNull Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        if (size == 0) {
            return null;
        }
        zza(parcel, i2, size, 4);
        return Integer.valueOf(parcel.readInt());
    }

    public static void readList(@NonNull Parcel parcel, int i2, @NonNull List list, @NonNull ClassLoader classLoader) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return;
        }
        parcel.readList(list, classLoader);
        parcel.setDataPosition(iDataPosition + size);
    }

    public static long readLong(@NonNull Parcel parcel, int i2) {
        zzb(parcel, i2, 8);
        return parcel.readLong();
    }

    @NonNull
    public static Long readLongObject(@NonNull Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        if (size == 0) {
            return null;
        }
        zza(parcel, i2, size, 8);
        return Long.valueOf(parcel.readLong());
    }

    @NonNull
    public static PendingIntent readPendingIntent(@NonNull Parcel parcel, int i2) {
        int size = readSize(parcel, i2);
        int iDataPosition = parcel.dataPosition();
        if (size == 0) {
            return null;
        }
        PendingIntent pendingIntentOrNullFromParcel = PendingIntent.readPendingIntentOrNullFromParcel(parcel);
        parcel.setDataPosition(iDataPosition + size);
        return pendingIntentOrNullFromParcel;
    }

    public static short readShort(@NonNull Parcel parcel, int i2) {
        zzb(parcel, i2, 4);
        return (short) parcel.readInt();
    }

    public static int readSize(@NonNull Parcel parcel, int i2) {
        return (i2 & SupportMenu.CATEGORY_MASK) != -65536 ? (char) (i2 >> 16) : parcel.readInt();
    }

    public static void skipUnknownField(@NonNull Parcel parcel, int i2) {
        parcel.setDataPosition(parcel.dataPosition() + readSize(parcel, i2));
    }

    public static int validateObjectHeader(@NonNull Parcel parcel) {
        int header = readHeader(parcel);
        int size = readSize(parcel, header);
        int fieldId = getFieldId(header);
        int iDataPosition = parcel.dataPosition();
        if (fieldId != 20293) {
            throw new ParseException("Expected object header. Got 0x".concat(String.valueOf(Integer.toHexString(header))), parcel);
        }
        int i2 = size + iDataPosition;
        if (i2 >= iDataPosition && i2 <= parcel.dataSize()) {
            return i2;
        }
        throw new ParseException("Size read is invalid start=" + iDataPosition + " end=" + i2, parcel);
    }

    private static void zza(Parcel parcel, int i2, int i3, int i4) {
        if (i3 == i4) {
            return;
        }
        throw new ParseException("Expected size " + i4 + " got " + i3 + " (0x" + Integer.toHexString(i3) + ")", parcel);
    }

    private static void zzb(Parcel parcel, int i2, int i3) {
        int size = readSize(parcel, i2);
        if (size == i3) {
            return;
        }
        throw new ParseException("Expected size " + i3 + " got " + size + " (0x" + Integer.toHexString(size) + ")", parcel);
    }
}
