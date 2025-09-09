package androidx.media3.extractor.ts;

import androidx.media3.common.util.Assertions;
import java.util.Arrays;

/* loaded from: classes2.dex */
final class NalUnitTargetBuffer {
    private boolean isCompleted;
    private boolean isFilling;
    public byte[] nalData;
    public int nalLength;
    private final int targetType;

    public NalUnitTargetBuffer(int i2, int i3) {
        this.targetType = i2;
        byte[] bArr = new byte[i3 + 3];
        this.nalData = bArr;
        bArr[2] = 1;
    }

    public void appendToNalUnit(byte[] bArr, int i2, int i3) {
        if (this.isFilling) {
            int i4 = i3 - i2;
            byte[] bArr2 = this.nalData;
            int length = bArr2.length;
            int i5 = this.nalLength;
            if (length < i5 + i4) {
                this.nalData = Arrays.copyOf(bArr2, (i5 + i4) * 2);
            }
            System.arraycopy(bArr, i2, this.nalData, this.nalLength, i4);
            this.nalLength += i4;
        }
    }

    public boolean endNalUnit(int i2) {
        if (!this.isFilling) {
            return false;
        }
        this.nalLength -= i2;
        this.isFilling = false;
        this.isCompleted = true;
        return true;
    }

    public boolean isCompleted() {
        return this.isCompleted;
    }

    public void reset() {
        this.isFilling = false;
        this.isCompleted = false;
    }

    public void startNalUnit(int i2) {
        Assertions.checkState(!this.isFilling);
        boolean z2 = i2 == this.targetType;
        this.isFilling = z2;
        if (z2) {
            this.nalLength = 3;
            this.isCompleted = false;
        }
    }
}
