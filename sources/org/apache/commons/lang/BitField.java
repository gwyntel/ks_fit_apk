package org.apache.commons.lang;

/* loaded from: classes5.dex */
public class BitField {
    private final int _mask;
    private final int _shift_count;

    public BitField(int i2) {
        this._mask = i2;
        int i3 = 0;
        if (i2 != 0) {
            while ((i2 & 1) == 0) {
                i3++;
                i2 >>= 1;
            }
        }
        this._shift_count = i3;
    }

    public int clear(int i2) {
        return i2 & (~this._mask);
    }

    public byte clearByte(byte b2) {
        return (byte) clear(b2);
    }

    public short clearShort(short s2) {
        return (short) clear(s2);
    }

    public int getRawValue(int i2) {
        return i2 & this._mask;
    }

    public short getShortRawValue(short s2) {
        return (short) getRawValue(s2);
    }

    public short getShortValue(short s2) {
        return (short) getValue(s2);
    }

    public int getValue(int i2) {
        return getRawValue(i2) >> this._shift_count;
    }

    public boolean isAllSet(int i2) {
        int i3 = this._mask;
        return (i2 & i3) == i3;
    }

    public boolean isSet(int i2) {
        return (i2 & this._mask) != 0;
    }

    public int set(int i2) {
        return i2 | this._mask;
    }

    public int setBoolean(int i2, boolean z2) {
        return z2 ? set(i2) : clear(i2);
    }

    public byte setByte(byte b2) {
        return (byte) set(b2);
    }

    public byte setByteBoolean(byte b2, boolean z2) {
        return z2 ? setByte(b2) : clearByte(b2);
    }

    public short setShort(short s2) {
        return (short) set(s2);
    }

    public short setShortBoolean(short s2, boolean z2) {
        return z2 ? setShort(s2) : clearShort(s2);
    }

    public short setShortValue(short s2, short s3) {
        return (short) setValue(s2, s3);
    }

    public int setValue(int i2, int i3) {
        int i4 = this._mask;
        return (i2 & (~i4)) | ((i3 << this._shift_count) & i4);
    }
}
