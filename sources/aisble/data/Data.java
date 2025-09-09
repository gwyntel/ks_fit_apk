package aisble.data;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.alibaba.ailabs.iot.aisbase.C0388a;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public class Data implements Parcelable {
    public static final int FORMAT_FLOAT = 52;
    public static final int FORMAT_SFLOAT = 50;
    public static final int FORMAT_SINT16 = 34;
    public static final int FORMAT_SINT24 = 35;
    public static final int FORMAT_SINT32 = 36;
    public static final int FORMAT_SINT8 = 33;
    public static final int FORMAT_UINT16 = 18;
    public static final int FORMAT_UINT24 = 19;
    public static final int FORMAT_UINT32 = 20;
    public static final int FORMAT_UINT8 = 17;
    public byte[] mValue;

    /* renamed from: a, reason: collision with root package name */
    public static char[] f1655a = "0123456789ABCDEF".toCharArray();
    public static final Parcelable.Creator<Data> CREATOR = new C0388a();

    @Retention(RetentionPolicy.SOURCE)
    public @interface FloatFormat {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface IntFormat {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface LongFormat {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ValueFormat {
    }

    public Data() {
        this.mValue = null;
    }

    public static int a(byte b2) {
        return b2 & 255;
    }

    public static long b(byte b2) {
        return b2 & 255;
    }

    public static long c(byte b2, byte b3, byte b4, byte b5) {
        return b(b2) + (b(b3) << 8) + (b(b4) << 16) + (b(b5) << 24);
    }

    public static Data from(@NonNull String str) {
        return new Data(str.getBytes());
    }

    public static int getTypeLen(int i2) {
        return i2 & 15;
    }

    public static Data opCode(byte b2) {
        return new Data(new byte[]{b2});
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Nullable
    public Byte getByte(@IntRange(from = 0) int i2) {
        if (i2 + 1 > size()) {
            return null;
        }
        return Byte.valueOf(this.mValue[i2]);
    }

    @Nullable
    public Float getFloatValue(int i2, @IntRange(from = 0) int i3) {
        if (getTypeLen(i2) + i3 > size()) {
            return null;
        }
        if (i2 == 50) {
            byte[] bArr = this.mValue;
            byte b2 = bArr[i3 + 1];
            return (b2 == 7 && bArr[i3] == -2) ? Float.valueOf(Float.POSITIVE_INFINITY) : ((b2 == 7 && bArr[i3] == -1) || (b2 == 8 && bArr[i3] == 0) || (b2 == 8 && bArr[i3] == 1)) ? Float.valueOf(Float.NaN) : (b2 == 8 && bArr[i3] == 2) ? Float.valueOf(Float.NEGATIVE_INFINITY) : Float.valueOf(a(bArr[i3], b2));
        }
        if (i2 != 52) {
            return null;
        }
        byte[] bArr2 = this.mValue;
        byte b3 = bArr2[i3 + 3];
        if (b3 == 0) {
            byte b4 = bArr2[i3 + 2];
            if (b4 == Byte.MAX_VALUE && bArr2[i3 + 1] == -1) {
                byte b5 = bArr2[i3];
                if (b5 == -2) {
                    return Float.valueOf(Float.POSITIVE_INFINITY);
                }
                if (b5 == -1) {
                    return Float.valueOf(Float.NaN);
                }
            } else if (b4 == Byte.MIN_VALUE && bArr2[i3 + 1] == 0) {
                byte b6 = bArr2[i3];
                if (b6 == 0 || b6 == 1) {
                    return Float.valueOf(Float.NaN);
                }
                if (b6 == 2) {
                    return Float.valueOf(Float.NEGATIVE_INFINITY);
                }
            }
        }
        return Float.valueOf(a(bArr2[i3], bArr2[i3 + 1], bArr2[i3 + 2], b3));
    }

    @Nullable
    public Integer getIntValue(int i2, @IntRange(from = 0) int i3) {
        if (getTypeLen(i2) + i3 > size()) {
            return null;
        }
        switch (i2) {
            case 17:
                break;
            case 18:
                byte[] bArr = this.mValue;
                break;
            case 19:
                byte[] bArr2 = this.mValue;
                break;
            case 20:
                byte[] bArr3 = this.mValue;
                break;
            default:
                switch (i2) {
                    case 34:
                        byte[] bArr4 = this.mValue;
                        break;
                    case 35:
                        byte[] bArr5 = this.mValue;
                        break;
                    case 36:
                        byte[] bArr6 = this.mValue;
                        break;
                }
        }
        return null;
    }

    @Nullable
    public Long getLongValue(int i2, @IntRange(from = 0) int i3) {
        if (getTypeLen(i2) + i3 > size()) {
            return null;
        }
        if (i2 == 20) {
            byte[] bArr = this.mValue;
            return Long.valueOf(c(bArr[i3], bArr[i3 + 1], bArr[i3 + 2], bArr[i3 + 3]));
        }
        if (i2 != 36) {
            return null;
        }
        byte[] bArr2 = this.mValue;
        return Long.valueOf(a(c(bArr2[i3], bArr2[i3 + 1], bArr2[i3 + 2], bArr2[i3 + 3]), 32));
    }

    @Nullable
    public String getStringValue(@IntRange(from = 0) int i2) {
        byte[] bArr = this.mValue;
        if (bArr == null || i2 > bArr.length) {
            return null;
        }
        byte[] bArr2 = new byte[bArr.length - i2];
        int i3 = 0;
        while (true) {
            byte[] bArr3 = this.mValue;
            if (i3 == bArr3.length - i2) {
                return new String(bArr2);
            }
            bArr2[i3] = bArr3[i2 + i3];
            i3++;
        }
    }

    @Nullable
    public byte[] getValue() {
        return this.mValue;
    }

    public int size() {
        byte[] bArr = this.mValue;
        if (bArr != null) {
            return bArr.length;
        }
        return 0;
    }

    public String toString() {
        if (size() == 0) {
            return "";
        }
        char[] cArr = new char[(this.mValue.length * 3) - 1];
        int i2 = 0;
        while (true) {
            byte[] bArr = this.mValue;
            if (i2 >= bArr.length) {
                return "(0x) " + new String(cArr);
            }
            byte b2 = bArr[i2];
            int i3 = i2 * 3;
            char[] cArr2 = f1655a;
            cArr[i3] = cArr2[(b2 & 255) >>> 4];
            cArr[i3 + 1] = cArr2[b2 & 15];
            if (i2 != bArr.length - 1) {
                cArr[i3 + 2] = '-';
            }
            i2++;
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeByteArray(this.mValue);
    }

    public static int a(int i2, int i3) {
        int i4 = 1 << (i3 - 1);
        return (i2 & i4) != 0 ? (i4 - (i2 & (i4 - 1))) * (-1) : i2;
    }

    public static int b(byte b2, byte b3) {
        return a(b2) + (a(b3) << 8);
    }

    public static Data from(@NonNull BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        return new Data(bluetoothGattCharacteristic.getValue());
    }

    public static Data opCode(byte b2, byte b3) {
        return new Data(new byte[]{b2, b3});
    }

    public Data(@Nullable byte[] bArr) {
        this.mValue = bArr;
    }

    public static long a(long j2, int i2) {
        long j3 = 1 << (i2 - 1);
        return (j2 & j3) != 0 ? (-1) * (j3 - (j2 & (r9 - 1))) : j2;
    }

    public static int b(byte b2, byte b3, byte b4, byte b5) {
        return a(b2) + (a(b3) << 8) + (a(b4) << 16) + (a(b5) << 24);
    }

    public static Data from(@NonNull BluetoothGattDescriptor bluetoothGattDescriptor) {
        return new Data(bluetoothGattDescriptor.getValue());
    }

    public static float a(byte b2, byte b3) {
        return (float) (a(a(b2) + ((a(b3) & 15) << 8), 12) * Math.pow(10.0d, a(a(b3) >> 4, 4)));
    }

    public Data(Parcel parcel) {
        this.mValue = parcel.createByteArray();
    }

    public static float a(byte b2, byte b3, byte b4, byte b5) {
        return (float) (a(a(b2) + (a(b3) << 8) + (a(b4) << 16), 24) * Math.pow(10.0d, b5));
    }
}
