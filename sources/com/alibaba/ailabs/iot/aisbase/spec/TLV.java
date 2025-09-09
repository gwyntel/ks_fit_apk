package com.alibaba.ailabs.iot.aisbase.spec;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes2.dex */
public class TLV {

    /* renamed from: a, reason: collision with root package name */
    public byte f8570a;

    /* renamed from: b, reason: collision with root package name */
    public byte f8571b;

    /* renamed from: c, reason: collision with root package name */
    public byte[] f8572c;

    public TLV() {
    }

    public static List<TLV> parseMultiFromBytes(byte[] bArr) {
        ArrayList arrayList = new ArrayList();
        int length = 0;
        int length2 = bArr == null ? 0 : bArr.length;
        while (true) {
            TLV singleFromBytes = parseSingleFromBytes(bArr, length, length2);
            if (singleFromBytes == null) {
                return arrayList;
            }
            arrayList.add(singleFromBytes);
            length += singleFromBytes.getLength() + 2;
        }
    }

    public static TLV parseSingleFromBytes(byte[] bArr, int i2, int i3) {
        int i4;
        if (bArr != null && bArr.length != 0 && i2 < bArr.length) {
            TLV tlv = new TLV();
            if (i3 > bArr.length) {
                i3 = bArr.length;
            }
            byte[] bArrCopyOfRange = Arrays.copyOfRange(bArr, i2, i3);
            tlv.setType(bArrCopyOfRange[0]);
            byte b2 = bArrCopyOfRange.length > 1 ? bArrCopyOfRange[1] : (byte) 0;
            if (b2 >= 0 && bArrCopyOfRange.length >= (i4 = b2 + 2)) {
                tlv.setLength(b2);
                tlv.setValue(Arrays.copyOfRange(bArrCopyOfRange, 2, i4));
                return tlv;
            }
        }
        return null;
    }

    public byte getLength() {
        return this.f8571b;
    }

    public byte getType() {
        return this.f8570a;
    }

    public byte[] getValue() {
        return this.f8572c;
    }

    public void setLength(byte b2) {
        this.f8571b = b2;
    }

    public void setType(byte b2) {
        this.f8570a = b2;
    }

    public void setValue(byte[] bArr) {
        this.f8572c = bArr;
    }

    public byte[] toBytes() {
        byte[] bArr = this.f8572c;
        byte[] bArr2 = new byte[(bArr == null ? 0 : bArr.length) + 2];
        bArr2[0] = this.f8570a;
        bArr2[1] = this.f8571b;
        if (bArr != null && bArr.length > 0) {
            System.arraycopy(bArr, 0, bArr2, 2, bArr.length);
        }
        return bArr2;
    }

    public TLV(byte b2, byte b3, byte[] bArr) {
        this.f8570a = b2;
        this.f8571b = b3;
        this.f8572c = bArr;
    }
}
