package aisble.data;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import anet.channel.entity.EventType;

/* loaded from: classes.dex */
public class MutableData extends Data {
    public static final int FLOAT_EXPONENT_MAX = 127;
    public static final int FLOAT_EXPONENT_MIN = -128;
    public static final int FLOAT_MANTISSA_MAX = 8388605;
    public static final int FLOAT_NAN = 8388607;
    public static final int FLOAT_NEGATIVE_INFINITY = 8388610;
    public static final int FLOAT_POSITIVE_INFINITY = 8388606;
    public static final int FLOAT_PRECISION = 10000000;
    public static final int SFLOAT_EXPONENT_MAX = 7;
    public static final int SFLOAT_EXPONENT_MIN = -8;
    public static final int SFLOAT_MANTISSA_MAX = 2045;
    public static final float SFLOAT_MAX = 2.045E10f;
    public static final float SFLOAT_MIN = -2.045E10f;
    public static final int SFLOAT_NAN = 2047;
    public static final int SFLOAT_NEGATIVE_INFINITY = 2050;
    public static final int SFLOAT_POSITIVE_INFINITY = 2046;
    public static final int SFLOAT_PRECISION = 10000;

    public MutableData() {
    }

    public static int floatToInt(float f2) {
        if (Float.isNaN(f2)) {
            return FLOAT_NAN;
        }
        if (f2 == Float.POSITIVE_INFINITY) {
            return FLOAT_POSITIVE_INFINITY;
        }
        if (f2 == Float.NEGATIVE_INFINITY) {
            return FLOAT_NEGATIVE_INFINITY;
        }
        int i2 = f2 >= 0.0f ? 1 : -1;
        float fAbs = Math.abs(f2);
        int i3 = 0;
        while (fAbs > 8388605.0f) {
            fAbs /= 10.0f;
            i3++;
            if (i3 > 127) {
                return i2 > 0 ? FLOAT_POSITIVE_INFINITY : FLOAT_NEGATIVE_INFINITY;
            }
        }
        while (fAbs < 1.0f) {
            fAbs *= 10.0f;
            i3--;
            if (i3 < -128) {
                return 0;
            }
        }
        double dAbs = Math.abs(Math.round(fAbs * 1.0E7f) - (Math.round(fAbs) * 10000000));
        while (dAbs > 0.5d && i3 > -128) {
            float f3 = fAbs * 10.0f;
            if (f3 > 8388605.0f) {
                break;
            }
            i3--;
            dAbs = Math.abs(Math.round(f3 * 1.0E7f) - (Math.round(f3) * 10000000));
            fAbs = f3;
        }
        return (Math.round(i2 * fAbs) & ViewCompat.MEASURED_SIZE_MASK) | (i3 << 24);
    }

    public static MutableData from(@NonNull BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        return new MutableData(bluetoothGattCharacteristic.getValue());
    }

    public static int intToSignedBits(int i2, int i3) {
        if (i2 >= 0) {
            return i2;
        }
        int i4 = 1 << (i3 - 1);
        return (i2 & (i4 - 1)) + i4;
    }

    public static long longToSignedBits(long j2, int i2) {
        if (j2 >= 0) {
            return j2;
        }
        long j3 = 1 << (i2 - 1);
        return (j2 & (j3 - 1)) + j3;
    }

    public static int sfloatToInt(float f2) {
        if (Float.isNaN(f2)) {
            return SFLOAT_NAN;
        }
        if (f2 > 2.045E10f) {
            return SFLOAT_POSITIVE_INFINITY;
        }
        if (f2 < -2.045E10f) {
            return 2050;
        }
        int i2 = f2 >= 0.0f ? 1 : -1;
        float fAbs = Math.abs(f2);
        int i3 = 0;
        while (fAbs > 2045.0f) {
            fAbs /= 10.0f;
            i3++;
            if (i3 > 7) {
                if (i2 > 0) {
                    return SFLOAT_POSITIVE_INFINITY;
                }
                return 2050;
            }
        }
        while (fAbs < 1.0f) {
            fAbs *= 10.0f;
            i3--;
            if (i3 < -8) {
                return 0;
            }
        }
        double dAbs = Math.abs(Math.round(fAbs * 10000.0f) - (Math.round(fAbs) * 10000));
        while (dAbs > 0.5d && i3 > -8) {
            float f3 = fAbs * 10.0f;
            if (f3 > 2045.0f) {
                break;
            }
            i3--;
            dAbs = Math.abs(Math.round(f3 * 10000.0f) - (Math.round(f3) * 10000));
            fAbs = f3;
        }
        return (Math.round(i2 * fAbs) & EventType.ALL) | ((i3 & 15) << 12);
    }

    public boolean setByte(int i2, @IntRange(from = 0) int i3) {
        int i4 = i3 + 1;
        if (this.mValue == null) {
            this.mValue = new byte[i4];
        }
        byte[] bArr = this.mValue;
        if (i4 > bArr.length) {
            return false;
        }
        bArr[i3] = (byte) i2;
        return true;
    }

    public boolean setValue(@Nullable byte[] bArr) {
        this.mValue = bArr;
        return true;
    }

    public MutableData(@Nullable byte[] bArr) {
        super(bArr);
    }

    public static MutableData from(@NonNull BluetoothGattDescriptor bluetoothGattDescriptor) {
        return new MutableData(bluetoothGattDescriptor.getValue());
    }

    public boolean setValue(int i2, int i3, @IntRange(from = 0) int i4) {
        int typeLen = Data.getTypeLen(i3) + i4;
        if (this.mValue == null) {
            this.mValue = new byte[typeLen];
        }
        if (typeLen > this.mValue.length) {
            return false;
        }
        switch (i3) {
            case 17:
                this.mValue[i4] = (byte) (i2 & 255);
                break;
            case 18:
                byte[] bArr = this.mValue;
                bArr[i4] = (byte) (i2 & 255);
                bArr[i4 + 1] = (byte) ((i2 >> 8) & 255);
                break;
            case 19:
                byte[] bArr2 = this.mValue;
                bArr2[i4] = (byte) (i2 & 255);
                bArr2[i4 + 1] = (byte) ((i2 >> 8) & 255);
                bArr2[i4 + 2] = (byte) ((i2 >> 16) & 255);
                break;
            case 20:
                byte[] bArr3 = this.mValue;
                bArr3[i4] = (byte) (i2 & 255);
                bArr3[i4 + 1] = (byte) ((i2 >> 8) & 255);
                bArr3[i4 + 2] = (byte) ((i2 >> 16) & 255);
                bArr3[i4 + 3] = (byte) ((i2 >> 24) & 255);
                break;
            default:
                switch (i3) {
                    case 33:
                        i2 = intToSignedBits(i2, 8);
                        this.mValue[i4] = (byte) (i2 & 255);
                        break;
                    case 34:
                        i2 = intToSignedBits(i2, 16);
                        byte[] bArr4 = this.mValue;
                        bArr4[i4] = (byte) (i2 & 255);
                        bArr4[i4 + 1] = (byte) ((i2 >> 8) & 255);
                        break;
                    case 35:
                        i2 = intToSignedBits(i2, 24);
                        byte[] bArr22 = this.mValue;
                        bArr22[i4] = (byte) (i2 & 255);
                        bArr22[i4 + 1] = (byte) ((i2 >> 8) & 255);
                        bArr22[i4 + 2] = (byte) ((i2 >> 16) & 255);
                        break;
                    case 36:
                        i2 = intToSignedBits(i2, 32);
                        byte[] bArr32 = this.mValue;
                        bArr32[i4] = (byte) (i2 & 255);
                        bArr32[i4 + 1] = (byte) ((i2 >> 8) & 255);
                        bArr32[i4 + 2] = (byte) ((i2 >> 16) & 255);
                        bArr32[i4 + 3] = (byte) ((i2 >> 24) & 255);
                        break;
                }
        }
        return false;
    }

    public boolean setValue(int i2, int i3, int i4, @IntRange(from = 0) int i5) {
        int typeLen = Data.getTypeLen(i4) + i5;
        if (this.mValue == null) {
            this.mValue = new byte[typeLen];
        }
        if (typeLen > this.mValue.length) {
            return false;
        }
        if (i4 == 50) {
            int iIntToSignedBits = intToSignedBits(i2, 12);
            int iIntToSignedBits2 = intToSignedBits(i3, 4);
            byte[] bArr = this.mValue;
            int i6 = i5 + 1;
            bArr[i5] = (byte) (iIntToSignedBits & 255);
            byte b2 = (byte) ((iIntToSignedBits >> 8) & 15);
            bArr[i6] = b2;
            bArr[i6] = (byte) (b2 + ((byte) ((iIntToSignedBits2 & 15) << 4)));
            return true;
        }
        if (i4 != 52) {
            return false;
        }
        int iIntToSignedBits3 = intToSignedBits(i2, 24);
        int iIntToSignedBits4 = intToSignedBits(i3, 8);
        byte[] bArr2 = this.mValue;
        bArr2[i5] = (byte) (iIntToSignedBits3 & 255);
        int i7 = i5 + 2;
        bArr2[i5 + 1] = (byte) ((iIntToSignedBits3 >> 8) & 255);
        int i8 = i5 + 3;
        bArr2[i7] = (byte) ((iIntToSignedBits3 >> 16) & 255);
        bArr2[i8] = (byte) (bArr2[i8] + ((byte) (iIntToSignedBits4 & 255)));
        return true;
    }

    public boolean setValue(long j2, int i2, @IntRange(from = 0) int i3) {
        int typeLen = Data.getTypeLen(i2) + i3;
        if (this.mValue == null) {
            this.mValue = new byte[typeLen];
        }
        if (typeLen > this.mValue.length) {
            return false;
        }
        if (i2 != 20) {
            if (i2 != 36) {
                return false;
            }
            j2 = longToSignedBits(j2, 32);
        }
        byte[] bArr = this.mValue;
        bArr[i3] = (byte) (j2 & 255);
        bArr[i3 + 1] = (byte) ((j2 >> 8) & 255);
        bArr[i3 + 2] = (byte) ((j2 >> 16) & 255);
        bArr[i3 + 3] = (byte) ((j2 >> 24) & 255);
        return true;
    }

    public boolean setValue(float f2, int i2, @IntRange(from = 0) int i3) {
        int typeLen = Data.getTypeLen(i2) + i3;
        if (this.mValue == null) {
            this.mValue = new byte[typeLen];
        }
        if (typeLen > this.mValue.length) {
            return false;
        }
        if (i2 == 50) {
            int iSfloatToInt = sfloatToInt(f2);
            byte[] bArr = this.mValue;
            bArr[i3] = (byte) (iSfloatToInt & 255);
            bArr[i3 + 1] = (byte) ((iSfloatToInt >> 8) & 255);
            return true;
        }
        if (i2 != 52) {
            return false;
        }
        int iFloatToInt = floatToInt(f2);
        byte[] bArr2 = this.mValue;
        bArr2[i3] = (byte) (iFloatToInt & 255);
        int i4 = i3 + 2;
        bArr2[i3 + 1] = (byte) ((iFloatToInt >> 8) & 255);
        int i5 = i3 + 3;
        bArr2[i4] = (byte) ((iFloatToInt >> 16) & 255);
        bArr2[i5] = (byte) (bArr2[i5] + ((byte) ((iFloatToInt >> 24) & 255)));
        return true;
    }
}
