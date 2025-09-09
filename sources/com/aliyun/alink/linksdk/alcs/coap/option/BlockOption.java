package com.aliyun.alink.linksdk.alcs.coap.option;

import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class BlockOption {
    private static final String TAG = "BlockOption";

    /* renamed from: m, reason: collision with root package name */
    private final boolean f10676m;
    private final int num;
    private final int szx;

    public BlockOption(int i2, boolean z2, int i3) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (i2 < 0 || 7 < i2) {
            ALog.e(TAG, "Block option's szx must be between 0 and 7 inclusive");
        } else if (i3 < 0 || 1048575 < i3) {
            ALog.e(TAG, "Block option's num must be between 0 and 524288 inclusive");
        }
        this.szx = i2;
        this.f10676m = z2;
        this.num = i3;
    }

    public static int size2Szx(int i2) {
        if (i2 >= 1024) {
            return 6;
        }
        if (i2 <= 16) {
            return 0;
        }
        return Integer.numberOfTrailingZeros(Integer.highestOneBit(i2)) - 4;
    }

    public static int szx2Size(int i2) {
        if (i2 <= 0) {
            return 16;
        }
        if (i2 >= 6) {
            return 1024;
        }
        return 1 << (i2 + 4);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof BlockOption)) {
            return false;
        }
        BlockOption blockOption = (BlockOption) obj;
        return this.szx == blockOption.szx && this.num == blockOption.num && this.f10676m == blockOption.f10676m;
    }

    public int getNum() {
        return this.num;
    }

    public int getOffset() {
        return this.num * szx2Size(this.szx);
    }

    public int getSize() {
        return 1 << (this.szx + 4);
    }

    public int getSzx() {
        return this.szx;
    }

    public byte[] getValue() {
        int i2 = this.szx;
        boolean z2 = this.f10676m;
        int i3 = (z2 ? 8 : 0) | i2;
        int i4 = this.num;
        return (i4 == 0 && !z2 && i2 == 0) ? new byte[0] : i4 < 16 ? new byte[]{(byte) ((i4 << 4) | i3)} : i4 < 4096 ? new byte[]{(byte) (i4 >> 4), (byte) ((i4 << 4) | i3)} : new byte[]{(byte) (i4 >> 12), (byte) (i4 >> 4), (byte) (i3 | (i4 << 4))};
    }

    public int hashCode() {
        return (((this.szx * 31) + (this.f10676m ? 1 : 0)) * 31) + this.num;
    }

    public boolean isM() {
        return this.f10676m;
    }

    public String toString() {
        return String.format("(szx=%d/%d, m=%b, num=%d)", Integer.valueOf(this.szx), Integer.valueOf(szx2Size(this.szx)), Boolean.valueOf(this.f10676m), Integer.valueOf(this.num));
    }

    public BlockOption(BlockOption blockOption) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (blockOption == null) {
            ALog.e(TAG, "origin empty");
            this.szx = 0;
            this.f10676m = false;
            this.num = 0;
            return;
        }
        this.szx = blockOption.getSzx();
        this.f10676m = blockOption.isM();
        this.num = blockOption.getNum();
    }

    public BlockOption(byte[] bArr) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (bArr == null || bArr.length > 3) {
            ALog.e(TAG, "value emtpy");
            byte b2 = bArr[bArr.length - 1];
            this.szx = b2 & 7;
            this.f10676m = ((b2 >> 3) & 1) == 1;
            int i2 = (b2 & 255) >> 4;
            for (int i3 = 1; i3 < bArr.length; i3++) {
                i2 += (bArr[(bArr.length - i3) - 1] & 255) << ((i3 * 8) - 4);
            }
            this.num = i2;
            return;
        }
        if (bArr.length > 3) {
            ALog.e(TAG, "Block option's length must at most 3 bytes inclusive");
            byte b3 = bArr[bArr.length - 1];
            this.szx = b3 & 7;
            this.f10676m = ((b3 >> 3) & 1) == 1;
            int i4 = (b3 & 255) >> 4;
            for (int i5 = 1; i5 < bArr.length; i5++) {
                i4 += (bArr[(bArr.length - i5) - 1] & 255) << ((i5 * 8) - 4);
            }
            this.num = i4;
            return;
        }
        if (bArr.length == 0) {
            this.szx = 0;
            this.f10676m = false;
            this.num = 0;
            return;
        }
        byte b4 = bArr[bArr.length - 1];
        this.szx = b4 & 7;
        this.f10676m = ((b4 >> 3) & 1) == 1;
        int i6 = (b4 & 255) >> 4;
        for (int i7 = 1; i7 < bArr.length; i7++) {
            i6 += (bArr[(bArr.length - i7) - 1] & 255) << ((i7 * 8) - 4);
        }
        this.num = i6;
    }
}
