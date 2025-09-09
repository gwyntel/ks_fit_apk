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
import java.util.List;

/* loaded from: classes3.dex */
public class SafeParcelWriter {
    private SafeParcelWriter() {
    }

    public static int beginObjectHeader(@NonNull Parcel parcel) {
        return zza(parcel, 20293);
    }

    public static void finishObjectHeader(@NonNull Parcel parcel, int i2) {
        zzb(parcel, i2);
    }

    public static void writeBigDecimal(@NonNull Parcel parcel, int i2, @NonNull BigDecimal bigDecimal, boolean z2) {
        if (bigDecimal == null) {
            if (z2) {
                zzc(parcel, i2, 0);
            }
        } else {
            int iZza = zza(parcel, i2);
            parcel.writeByteArray(bigDecimal.unscaledValue().toByteArray());
            parcel.writeInt(bigDecimal.scale());
            zzb(parcel, iZza);
        }
    }

    public static void writeBigDecimalArray(@NonNull Parcel parcel, int i2, @NonNull BigDecimal[] bigDecimalArr, boolean z2) {
        if (bigDecimalArr == null) {
            if (z2) {
                zzc(parcel, i2, 0);
                return;
            }
            return;
        }
        int iZza = zza(parcel, i2);
        int length = bigDecimalArr.length;
        parcel.writeInt(length);
        for (int i3 = 0; i3 < length; i3++) {
            parcel.writeByteArray(bigDecimalArr[i3].unscaledValue().toByteArray());
            parcel.writeInt(bigDecimalArr[i3].scale());
        }
        zzb(parcel, iZza);
    }

    public static void writeBigInteger(@NonNull Parcel parcel, int i2, @NonNull BigInteger bigInteger, boolean z2) {
        if (bigInteger == null) {
            if (z2) {
                zzc(parcel, i2, 0);
            }
        } else {
            int iZza = zza(parcel, i2);
            parcel.writeByteArray(bigInteger.toByteArray());
            zzb(parcel, iZza);
        }
    }

    public static void writeBigIntegerArray(@NonNull Parcel parcel, int i2, @NonNull BigInteger[] bigIntegerArr, boolean z2) {
        if (bigIntegerArr == null) {
            if (z2) {
                zzc(parcel, i2, 0);
                return;
            }
            return;
        }
        int iZza = zza(parcel, i2);
        parcel.writeInt(bigIntegerArr.length);
        for (BigInteger bigInteger : bigIntegerArr) {
            parcel.writeByteArray(bigInteger.toByteArray());
        }
        zzb(parcel, iZza);
    }

    public static void writeBoolean(@NonNull Parcel parcel, int i2, boolean z2) {
        zzc(parcel, i2, 4);
        parcel.writeInt(z2 ? 1 : 0);
    }

    public static void writeBooleanArray(@NonNull Parcel parcel, int i2, @NonNull boolean[] zArr, boolean z2) {
        if (zArr == null) {
            if (z2) {
                zzc(parcel, i2, 0);
            }
        } else {
            int iZza = zza(parcel, i2);
            parcel.writeBooleanArray(zArr);
            zzb(parcel, iZza);
        }
    }

    public static void writeBooleanList(@NonNull Parcel parcel, int i2, @NonNull List<Boolean> list, boolean z2) {
        if (list == null) {
            if (z2) {
                zzc(parcel, i2, 0);
                return;
            }
            return;
        }
        int iZza = zza(parcel, i2);
        int size = list.size();
        parcel.writeInt(size);
        for (int i3 = 0; i3 < size; i3++) {
            parcel.writeInt(list.get(i3).booleanValue() ? 1 : 0);
        }
        zzb(parcel, iZza);
    }

    public static void writeBooleanObject(@NonNull Parcel parcel, int i2, @NonNull Boolean bool, boolean z2) {
        if (bool != null) {
            zzc(parcel, i2, 4);
            parcel.writeInt(bool.booleanValue() ? 1 : 0);
        } else if (z2) {
            zzc(parcel, i2, 0);
        }
    }

    public static void writeBundle(@NonNull Parcel parcel, int i2, @NonNull Bundle bundle, boolean z2) {
        if (bundle == null) {
            if (z2) {
                zzc(parcel, i2, 0);
            }
        } else {
            int iZza = zza(parcel, i2);
            parcel.writeBundle(bundle);
            zzb(parcel, iZza);
        }
    }

    public static void writeByte(@NonNull Parcel parcel, int i2, byte b2) {
        zzc(parcel, i2, 4);
        parcel.writeInt(b2);
    }

    public static void writeByteArray(@NonNull Parcel parcel, int i2, @NonNull byte[] bArr, boolean z2) {
        if (bArr == null) {
            if (z2) {
                zzc(parcel, i2, 0);
            }
        } else {
            int iZza = zza(parcel, i2);
            parcel.writeByteArray(bArr);
            zzb(parcel, iZza);
        }
    }

    public static void writeByteArrayArray(@NonNull Parcel parcel, int i2, @NonNull byte[][] bArr, boolean z2) {
        if (bArr == null) {
            if (z2) {
                zzc(parcel, i2, 0);
                return;
            }
            return;
        }
        int iZza = zza(parcel, i2);
        parcel.writeInt(bArr.length);
        for (byte[] bArr2 : bArr) {
            parcel.writeByteArray(bArr2);
        }
        zzb(parcel, iZza);
    }

    public static void writeByteArraySparseArray(@NonNull Parcel parcel, int i2, @NonNull SparseArray<byte[]> sparseArray, boolean z2) {
        if (sparseArray == null) {
            if (z2) {
                zzc(parcel, i2, 0);
                return;
            }
            return;
        }
        int iZza = zza(parcel, i2);
        int size = sparseArray.size();
        parcel.writeInt(size);
        for (int i3 = 0; i3 < size; i3++) {
            parcel.writeInt(sparseArray.keyAt(i3));
            parcel.writeByteArray(sparseArray.valueAt(i3));
        }
        zzb(parcel, iZza);
    }

    public static void writeChar(@NonNull Parcel parcel, int i2, char c2) {
        zzc(parcel, i2, 4);
        parcel.writeInt(c2);
    }

    public static void writeCharArray(@NonNull Parcel parcel, int i2, @NonNull char[] cArr, boolean z2) {
        if (cArr == null) {
            if (z2) {
                zzc(parcel, i2, 0);
            }
        } else {
            int iZza = zza(parcel, i2);
            parcel.writeCharArray(cArr);
            zzb(parcel, iZza);
        }
    }

    public static void writeDouble(@NonNull Parcel parcel, int i2, double d2) {
        zzc(parcel, i2, 8);
        parcel.writeDouble(d2);
    }

    public static void writeDoubleArray(@NonNull Parcel parcel, int i2, @NonNull double[] dArr, boolean z2) {
        if (dArr == null) {
            if (z2) {
                zzc(parcel, i2, 0);
            }
        } else {
            int iZza = zza(parcel, i2);
            parcel.writeDoubleArray(dArr);
            zzb(parcel, iZza);
        }
    }

    public static void writeDoubleList(@NonNull Parcel parcel, int i2, @NonNull List<Double> list, boolean z2) {
        if (list == null) {
            if (z2) {
                zzc(parcel, i2, 0);
                return;
            }
            return;
        }
        int iZza = zza(parcel, i2);
        int size = list.size();
        parcel.writeInt(size);
        for (int i3 = 0; i3 < size; i3++) {
            parcel.writeDouble(list.get(i3).doubleValue());
        }
        zzb(parcel, iZza);
    }

    public static void writeDoubleObject(@NonNull Parcel parcel, int i2, @NonNull Double d2, boolean z2) {
        if (d2 != null) {
            zzc(parcel, i2, 8);
            parcel.writeDouble(d2.doubleValue());
        } else if (z2) {
            zzc(parcel, i2, 0);
        }
    }

    public static void writeDoubleSparseArray(@NonNull Parcel parcel, int i2, @NonNull SparseArray<Double> sparseArray, boolean z2) {
        if (sparseArray == null) {
            if (z2) {
                zzc(parcel, i2, 0);
                return;
            }
            return;
        }
        int iZza = zza(parcel, i2);
        int size = sparseArray.size();
        parcel.writeInt(size);
        for (int i3 = 0; i3 < size; i3++) {
            parcel.writeInt(sparseArray.keyAt(i3));
            parcel.writeDouble(sparseArray.valueAt(i3).doubleValue());
        }
        zzb(parcel, iZza);
    }

    public static void writeFloat(@NonNull Parcel parcel, int i2, float f2) {
        zzc(parcel, i2, 4);
        parcel.writeFloat(f2);
    }

    public static void writeFloatArray(@NonNull Parcel parcel, int i2, @NonNull float[] fArr, boolean z2) {
        if (fArr == null) {
            if (z2) {
                zzc(parcel, i2, 0);
            }
        } else {
            int iZza = zza(parcel, i2);
            parcel.writeFloatArray(fArr);
            zzb(parcel, iZza);
        }
    }

    public static void writeFloatList(@NonNull Parcel parcel, int i2, @NonNull List<Float> list, boolean z2) {
        if (list == null) {
            if (z2) {
                zzc(parcel, i2, 0);
                return;
            }
            return;
        }
        int iZza = zza(parcel, i2);
        int size = list.size();
        parcel.writeInt(size);
        for (int i3 = 0; i3 < size; i3++) {
            parcel.writeFloat(list.get(i3).floatValue());
        }
        zzb(parcel, iZza);
    }

    public static void writeFloatObject(@NonNull Parcel parcel, int i2, @NonNull Float f2, boolean z2) {
        if (f2 != null) {
            zzc(parcel, i2, 4);
            parcel.writeFloat(f2.floatValue());
        } else if (z2) {
            zzc(parcel, i2, 0);
        }
    }

    public static void writeFloatSparseArray(@NonNull Parcel parcel, int i2, @NonNull SparseArray<Float> sparseArray, boolean z2) {
        if (sparseArray == null) {
            if (z2) {
                zzc(parcel, i2, 0);
                return;
            }
            return;
        }
        int iZza = zza(parcel, i2);
        int size = sparseArray.size();
        parcel.writeInt(size);
        for (int i3 = 0; i3 < size; i3++) {
            parcel.writeInt(sparseArray.keyAt(i3));
            parcel.writeFloat(sparseArray.valueAt(i3).floatValue());
        }
        zzb(parcel, iZza);
    }

    public static void writeIBinder(@NonNull Parcel parcel, int i2, @NonNull IBinder iBinder, boolean z2) {
        if (iBinder == null) {
            if (z2) {
                zzc(parcel, i2, 0);
            }
        } else {
            int iZza = zza(parcel, i2);
            parcel.writeStrongBinder(iBinder);
            zzb(parcel, iZza);
        }
    }

    public static void writeIBinderArray(@NonNull Parcel parcel, int i2, @NonNull IBinder[] iBinderArr, boolean z2) {
        if (iBinderArr == null) {
            if (z2) {
                zzc(parcel, i2, 0);
            }
        } else {
            int iZza = zza(parcel, i2);
            parcel.writeBinderArray(iBinderArr);
            zzb(parcel, iZza);
        }
    }

    public static void writeIBinderList(@NonNull Parcel parcel, int i2, @NonNull List<IBinder> list, boolean z2) {
        if (list == null) {
            if (z2) {
                zzc(parcel, i2, 0);
            }
        } else {
            int iZza = zza(parcel, i2);
            parcel.writeBinderList(list);
            zzb(parcel, iZza);
        }
    }

    public static void writeIBinderSparseArray(@NonNull Parcel parcel, int i2, @NonNull SparseArray<IBinder> sparseArray, boolean z2) {
        if (sparseArray == null) {
            if (z2) {
                zzc(parcel, i2, 0);
                return;
            }
            return;
        }
        int iZza = zza(parcel, i2);
        int size = sparseArray.size();
        parcel.writeInt(size);
        for (int i3 = 0; i3 < size; i3++) {
            parcel.writeInt(sparseArray.keyAt(i3));
            parcel.writeStrongBinder(sparseArray.valueAt(i3));
        }
        zzb(parcel, iZza);
    }

    public static void writeInt(@NonNull Parcel parcel, int i2, int i3) {
        zzc(parcel, i2, 4);
        parcel.writeInt(i3);
    }

    public static void writeIntArray(@NonNull Parcel parcel, int i2, @NonNull int[] iArr, boolean z2) {
        if (iArr == null) {
            if (z2) {
                zzc(parcel, i2, 0);
            }
        } else {
            int iZza = zza(parcel, i2);
            parcel.writeIntArray(iArr);
            zzb(parcel, iZza);
        }
    }

    public static void writeIntegerList(@NonNull Parcel parcel, int i2, @NonNull List<Integer> list, boolean z2) {
        if (list == null) {
            if (z2) {
                zzc(parcel, i2, 0);
                return;
            }
            return;
        }
        int iZza = zza(parcel, i2);
        int size = list.size();
        parcel.writeInt(size);
        for (int i3 = 0; i3 < size; i3++) {
            parcel.writeInt(list.get(i3).intValue());
        }
        zzb(parcel, iZza);
    }

    public static void writeIntegerObject(@NonNull Parcel parcel, int i2, @NonNull Integer num, boolean z2) {
        if (num != null) {
            zzc(parcel, i2, 4);
            parcel.writeInt(num.intValue());
        } else if (z2) {
            zzc(parcel, i2, 0);
        }
    }

    public static void writeList(@NonNull Parcel parcel, int i2, @NonNull List list, boolean z2) {
        if (list == null) {
            if (z2) {
                zzc(parcel, i2, 0);
            }
        } else {
            int iZza = zza(parcel, i2);
            parcel.writeList(list);
            zzb(parcel, iZza);
        }
    }

    public static void writeLong(@NonNull Parcel parcel, int i2, long j2) {
        zzc(parcel, i2, 8);
        parcel.writeLong(j2);
    }

    public static void writeLongArray(@NonNull Parcel parcel, int i2, @NonNull long[] jArr, boolean z2) {
        if (jArr == null) {
            if (z2) {
                zzc(parcel, i2, 0);
            }
        } else {
            int iZza = zza(parcel, i2);
            parcel.writeLongArray(jArr);
            zzb(parcel, iZza);
        }
    }

    public static void writeLongList(@NonNull Parcel parcel, int i2, @NonNull List<Long> list, boolean z2) {
        if (list == null) {
            if (z2) {
                zzc(parcel, i2, 0);
                return;
            }
            return;
        }
        int iZza = zza(parcel, i2);
        int size = list.size();
        parcel.writeInt(size);
        for (int i3 = 0; i3 < size; i3++) {
            parcel.writeLong(list.get(i3).longValue());
        }
        zzb(parcel, iZza);
    }

    public static void writeLongObject(@NonNull Parcel parcel, int i2, @NonNull Long l2, boolean z2) {
        if (l2 != null) {
            zzc(parcel, i2, 8);
            parcel.writeLong(l2.longValue());
        } else if (z2) {
            zzc(parcel, i2, 0);
        }
    }

    public static void writeParcel(@NonNull Parcel parcel, int i2, @NonNull Parcel parcel2, boolean z2) {
        if (parcel2 == null) {
            if (z2) {
                zzc(parcel, i2, 0);
            }
        } else {
            int iZza = zza(parcel, i2);
            parcel.appendFrom(parcel2, 0, parcel2.dataSize());
            zzb(parcel, iZza);
        }
    }

    public static void writeParcelArray(@NonNull Parcel parcel, int i2, @NonNull Parcel[] parcelArr, boolean z2) {
        if (parcelArr == null) {
            if (z2) {
                zzc(parcel, i2, 0);
                return;
            }
            return;
        }
        int iZza = zza(parcel, i2);
        parcel.writeInt(parcelArr.length);
        for (Parcel parcel2 : parcelArr) {
            if (parcel2 != null) {
                parcel.writeInt(parcel2.dataSize());
                parcel.appendFrom(parcel2, 0, parcel2.dataSize());
            } else {
                parcel.writeInt(0);
            }
        }
        zzb(parcel, iZza);
    }

    public static void writeParcelList(@NonNull Parcel parcel, int i2, @NonNull List<Parcel> list, boolean z2) {
        if (list == null) {
            if (z2) {
                zzc(parcel, i2, 0);
                return;
            }
            return;
        }
        int iZza = zza(parcel, i2);
        int size = list.size();
        parcel.writeInt(size);
        for (int i3 = 0; i3 < size; i3++) {
            Parcel parcel2 = list.get(i3);
            if (parcel2 != null) {
                parcel.writeInt(parcel2.dataSize());
                parcel.appendFrom(parcel2, 0, parcel2.dataSize());
            } else {
                parcel.writeInt(0);
            }
        }
        zzb(parcel, iZza);
    }

    public static void writeParcelSparseArray(@NonNull Parcel parcel, int i2, @NonNull SparseArray<Parcel> sparseArray, boolean z2) {
        if (sparseArray == null) {
            if (z2) {
                zzc(parcel, i2, 0);
                return;
            }
            return;
        }
        int iZza = zza(parcel, i2);
        int size = sparseArray.size();
        parcel.writeInt(size);
        for (int i3 = 0; i3 < size; i3++) {
            parcel.writeInt(sparseArray.keyAt(i3));
            Parcel parcelValueAt = sparseArray.valueAt(i3);
            if (parcelValueAt != null) {
                parcel.writeInt(parcelValueAt.dataSize());
                parcel.appendFrom(parcelValueAt, 0, parcelValueAt.dataSize());
            } else {
                parcel.writeInt(0);
            }
        }
        zzb(parcel, iZza);
    }

    public static void writeParcelable(@NonNull Parcel parcel, int i2, @NonNull Parcelable parcelable, int i3, boolean z2) {
        if (parcelable == null) {
            if (z2) {
                zzc(parcel, i2, 0);
            }
        } else {
            int iZza = zza(parcel, i2);
            parcelable.writeToParcel(parcel, i3);
            zzb(parcel, iZza);
        }
    }

    public static void writePendingIntent(@NonNull Parcel parcel, int i2, @NonNull PendingIntent pendingIntent, boolean z2) {
        if (pendingIntent == null) {
            if (z2) {
                zzc(parcel, i2, 0);
            }
        } else {
            int iZza = zza(parcel, i2);
            PendingIntent.writePendingIntentOrNullToParcel(pendingIntent, parcel);
            zzb(parcel, iZza);
        }
    }

    public static void writeShort(@NonNull Parcel parcel, int i2, short s2) {
        zzc(parcel, i2, 4);
        parcel.writeInt(s2);
    }

    public static void writeSparseBooleanArray(@NonNull Parcel parcel, int i2, @NonNull SparseBooleanArray sparseBooleanArray, boolean z2) {
        if (sparseBooleanArray == null) {
            if (z2) {
                zzc(parcel, i2, 0);
            }
        } else {
            int iZza = zza(parcel, i2);
            parcel.writeSparseBooleanArray(sparseBooleanArray);
            zzb(parcel, iZza);
        }
    }

    public static void writeSparseIntArray(@NonNull Parcel parcel, int i2, @NonNull SparseIntArray sparseIntArray, boolean z2) {
        if (sparseIntArray == null) {
            if (z2) {
                zzc(parcel, i2, 0);
                return;
            }
            return;
        }
        int iZza = zza(parcel, i2);
        int size = sparseIntArray.size();
        parcel.writeInt(size);
        for (int i3 = 0; i3 < size; i3++) {
            parcel.writeInt(sparseIntArray.keyAt(i3));
            parcel.writeInt(sparseIntArray.valueAt(i3));
        }
        zzb(parcel, iZza);
    }

    public static void writeSparseLongArray(@NonNull Parcel parcel, int i2, @NonNull SparseLongArray sparseLongArray, boolean z2) {
        if (sparseLongArray == null) {
            if (z2) {
                zzc(parcel, i2, 0);
                return;
            }
            return;
        }
        int iZza = zza(parcel, i2);
        int size = sparseLongArray.size();
        parcel.writeInt(size);
        for (int i3 = 0; i3 < size; i3++) {
            parcel.writeInt(sparseLongArray.keyAt(i3));
            parcel.writeLong(sparseLongArray.valueAt(i3));
        }
        zzb(parcel, iZza);
    }

    public static void writeString(@NonNull Parcel parcel, int i2, @NonNull String str, boolean z2) {
        if (str == null) {
            if (z2) {
                zzc(parcel, i2, 0);
            }
        } else {
            int iZza = zza(parcel, i2);
            parcel.writeString(str);
            zzb(parcel, iZza);
        }
    }

    public static void writeStringArray(@NonNull Parcel parcel, int i2, @NonNull String[] strArr, boolean z2) {
        if (strArr == null) {
            if (z2) {
                zzc(parcel, i2, 0);
            }
        } else {
            int iZza = zza(parcel, i2);
            parcel.writeStringArray(strArr);
            zzb(parcel, iZza);
        }
    }

    public static void writeStringList(@NonNull Parcel parcel, int i2, @NonNull List<String> list, boolean z2) {
        if (list == null) {
            if (z2) {
                zzc(parcel, i2, 0);
            }
        } else {
            int iZza = zza(parcel, i2);
            parcel.writeStringList(list);
            zzb(parcel, iZza);
        }
    }

    public static void writeStringSparseArray(@NonNull Parcel parcel, int i2, @NonNull SparseArray<String> sparseArray, boolean z2) {
        if (sparseArray == null) {
            if (z2) {
                zzc(parcel, i2, 0);
                return;
            }
            return;
        }
        int iZza = zza(parcel, i2);
        int size = sparseArray.size();
        parcel.writeInt(size);
        for (int i3 = 0; i3 < size; i3++) {
            parcel.writeInt(sparseArray.keyAt(i3));
            parcel.writeString(sparseArray.valueAt(i3));
        }
        zzb(parcel, iZza);
    }

    public static <T extends Parcelable> void writeTypedArray(@NonNull Parcel parcel, int i2, @NonNull T[] tArr, int i3, boolean z2) {
        if (tArr == null) {
            if (z2) {
                zzc(parcel, i2, 0);
                return;
            }
            return;
        }
        int iZza = zza(parcel, i2);
        parcel.writeInt(tArr.length);
        for (T t2 : tArr) {
            if (t2 == null) {
                parcel.writeInt(0);
            } else {
                zzd(parcel, t2, i3);
            }
        }
        zzb(parcel, iZza);
    }

    public static <T extends Parcelable> void writeTypedList(@NonNull Parcel parcel, int i2, @NonNull List<T> list, boolean z2) {
        if (list == null) {
            if (z2) {
                zzc(parcel, i2, 0);
                return;
            }
            return;
        }
        int iZza = zza(parcel, i2);
        int size = list.size();
        parcel.writeInt(size);
        for (int i3 = 0; i3 < size; i3++) {
            T t2 = list.get(i3);
            if (t2 == null) {
                parcel.writeInt(0);
            } else {
                zzd(parcel, t2, 0);
            }
        }
        zzb(parcel, iZza);
    }

    public static <T extends Parcelable> void writeTypedSparseArray(@NonNull Parcel parcel, int i2, @NonNull SparseArray<T> sparseArray, boolean z2) {
        if (sparseArray == null) {
            if (z2) {
                zzc(parcel, i2, 0);
                return;
            }
            return;
        }
        int iZza = zza(parcel, i2);
        int size = sparseArray.size();
        parcel.writeInt(size);
        for (int i3 = 0; i3 < size; i3++) {
            parcel.writeInt(sparseArray.keyAt(i3));
            T tValueAt = sparseArray.valueAt(i3);
            if (tValueAt == null) {
                parcel.writeInt(0);
            } else {
                zzd(parcel, tValueAt, 0);
            }
        }
        zzb(parcel, iZza);
    }

    private static int zza(Parcel parcel, int i2) {
        parcel.writeInt(i2 | SupportMenu.CATEGORY_MASK);
        parcel.writeInt(0);
        return parcel.dataPosition();
    }

    private static void zzb(Parcel parcel, int i2) {
        int iDataPosition = parcel.dataPosition();
        parcel.setDataPosition(i2 - 4);
        parcel.writeInt(iDataPosition - i2);
        parcel.setDataPosition(iDataPosition);
    }

    private static void zzc(Parcel parcel, int i2, int i3) {
        parcel.writeInt(i2 | (i3 << 16));
    }

    private static void zzd(Parcel parcel, Parcelable parcelable, int i2) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(1);
        int iDataPosition2 = parcel.dataPosition();
        parcelable.writeToParcel(parcel, i2);
        int iDataPosition3 = parcel.dataPosition();
        parcel.setDataPosition(iDataPosition);
        parcel.writeInt(iDataPosition3 - iDataPosition2);
        parcel.setDataPosition(iDataPosition3);
    }
}
