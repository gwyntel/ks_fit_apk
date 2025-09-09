package androidx.media3.extractor.mkv;

import androidx.media3.common.util.ParsableByteArray;
import androidx.media3.extractor.ExtractorInput;
import java.io.IOException;

/* loaded from: classes2.dex */
final class Sniffer {
    private static final int ID_EBML = 440786851;
    private static final int SEARCH_LENGTH = 1024;
    private int peekLength;
    private final ParsableByteArray scratch = new ParsableByteArray(8);

    private long readUint(ExtractorInput extractorInput) throws IOException {
        int i2 = 0;
        extractorInput.peekFully(this.scratch.getData(), 0, 1);
        int i3 = this.scratch.getData()[0] & 255;
        if (i3 == 0) {
            return Long.MIN_VALUE;
        }
        int i4 = 128;
        int i5 = 0;
        while ((i3 & i4) == 0) {
            i4 >>= 1;
            i5++;
        }
        int i6 = i3 & (~i4);
        extractorInput.peekFully(this.scratch.getData(), 1, i5);
        while (i2 < i5) {
            i2++;
            i6 = (this.scratch.getData()[i2] & 255) + (i6 << 8);
        }
        this.peekLength += i5 + 1;
        return i6;
    }

    public boolean sniff(ExtractorInput extractorInput) throws IOException {
        long length = extractorInput.getLength();
        long j2 = 1024;
        if (length != -1 && length <= 1024) {
            j2 = length;
        }
        int i2 = (int) j2;
        extractorInput.peekFully(this.scratch.getData(), 0, 4);
        long unsignedInt = this.scratch.readUnsignedInt();
        this.peekLength = 4;
        while (unsignedInt != 440786851) {
            int i3 = this.peekLength + 1;
            this.peekLength = i3;
            if (i3 == i2) {
                return false;
            }
            extractorInput.peekFully(this.scratch.getData(), 0, 1);
            unsignedInt = ((unsignedInt << 8) & (-256)) | (this.scratch.getData()[0] & 255);
        }
        long uint = readUint(extractorInput);
        long j3 = this.peekLength;
        if (uint == Long.MIN_VALUE) {
            return false;
        }
        if (length != -1 && j3 + uint >= length) {
            return false;
        }
        while (true) {
            int i4 = this.peekLength;
            long j4 = j3 + uint;
            if (i4 >= j4) {
                return ((long) i4) == j4;
            }
            if (readUint(extractorInput) == Long.MIN_VALUE) {
                return false;
            }
            long uint2 = readUint(extractorInput);
            if (uint2 < 0 || uint2 > 2147483647L) {
                break;
            }
            if (uint2 != 0) {
                int i5 = (int) uint2;
                extractorInput.advancePeekPosition(i5);
                this.peekLength += i5;
            }
        }
        return false;
    }
}
