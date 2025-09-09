package androidx.media3.extractor;

import androidx.media3.common.DataReader;
import androidx.media3.common.MediaLibraryInfo;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;
import java.io.EOFException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.Arrays;

@UnstableApi
/* loaded from: classes2.dex */
public final class DefaultExtractorInput implements ExtractorInput {
    private static final int PEEK_MAX_FREE_SPACE = 524288;
    private static final int PEEK_MIN_FREE_SPACE_AFTER_RESIZE = 65536;
    private static final int SCRATCH_SPACE_SIZE = 4096;
    private final DataReader dataReader;
    private int peekBufferLength;
    private int peekBufferPosition;
    private long position;
    private final long streamLength;
    private byte[] peekBuffer = new byte[65536];
    private final byte[] scratchSpace = new byte[4096];

    static {
        MediaLibraryInfo.registerModule("media3.extractor");
    }

    public DefaultExtractorInput(DataReader dataReader, long j2, long j3) {
        this.dataReader = dataReader;
        this.position = j2;
        this.streamLength = j3;
    }

    private void commitBytesRead(int i2) {
        if (i2 != -1) {
            this.position += i2;
        }
    }

    private void ensureSpaceForPeek(int i2) {
        int i3 = this.peekBufferPosition + i2;
        byte[] bArr = this.peekBuffer;
        if (i3 > bArr.length) {
            this.peekBuffer = Arrays.copyOf(this.peekBuffer, Util.constrainValue(bArr.length * 2, 65536 + i3, i3 + 524288));
        }
    }

    private int readFromPeekBuffer(byte[] bArr, int i2, int i3) {
        int i4 = this.peekBufferLength;
        if (i4 == 0) {
            return 0;
        }
        int iMin = Math.min(i4, i3);
        System.arraycopy(this.peekBuffer, 0, bArr, i2, iMin);
        updatePeekBuffer(iMin);
        return iMin;
    }

    private int readFromUpstream(byte[] bArr, int i2, int i3, int i4, boolean z2) throws IOException {
        if (Thread.interrupted()) {
            throw new InterruptedIOException();
        }
        int i5 = this.dataReader.read(bArr, i2 + i4, i3 - i4);
        if (i5 != -1) {
            return i4 + i5;
        }
        if (i4 == 0 && z2) {
            return -1;
        }
        throw new EOFException();
    }

    private int skipFromPeekBuffer(int i2) {
        int iMin = Math.min(this.peekBufferLength, i2);
        updatePeekBuffer(iMin);
        return iMin;
    }

    private void updatePeekBuffer(int i2) {
        int i3 = this.peekBufferLength - i2;
        this.peekBufferLength = i3;
        this.peekBufferPosition = 0;
        byte[] bArr = this.peekBuffer;
        byte[] bArr2 = i3 < bArr.length - 524288 ? new byte[65536 + i3] : bArr;
        System.arraycopy(bArr, i2, bArr2, 0, i3);
        this.peekBuffer = bArr2;
    }

    @Override // androidx.media3.extractor.ExtractorInput
    public boolean advancePeekPosition(int i2, boolean z2) throws IOException {
        ensureSpaceForPeek(i2);
        int fromUpstream = this.peekBufferLength - this.peekBufferPosition;
        while (fromUpstream < i2) {
            fromUpstream = readFromUpstream(this.peekBuffer, this.peekBufferPosition, i2, fromUpstream, z2);
            if (fromUpstream == -1) {
                return false;
            }
            this.peekBufferLength = this.peekBufferPosition + fromUpstream;
        }
        this.peekBufferPosition += i2;
        return true;
    }

    @Override // androidx.media3.extractor.ExtractorInput
    public long getLength() {
        return this.streamLength;
    }

    @Override // androidx.media3.extractor.ExtractorInput
    public long getPeekPosition() {
        return this.position + this.peekBufferPosition;
    }

    @Override // androidx.media3.extractor.ExtractorInput
    public long getPosition() {
        return this.position;
    }

    @Override // androidx.media3.extractor.ExtractorInput
    public int peek(byte[] bArr, int i2, int i3) throws IOException {
        int iMin;
        ensureSpaceForPeek(i3);
        int i4 = this.peekBufferLength;
        int i5 = this.peekBufferPosition;
        int i6 = i4 - i5;
        if (i6 == 0) {
            iMin = readFromUpstream(this.peekBuffer, i5, i3, 0, true);
            if (iMin == -1) {
                return -1;
            }
            this.peekBufferLength += iMin;
        } else {
            iMin = Math.min(i3, i6);
        }
        System.arraycopy(this.peekBuffer, this.peekBufferPosition, bArr, i2, iMin);
        this.peekBufferPosition += iMin;
        return iMin;
    }

    @Override // androidx.media3.extractor.ExtractorInput
    public boolean peekFully(byte[] bArr, int i2, int i3, boolean z2) throws IOException {
        if (!advancePeekPosition(i3, z2)) {
            return false;
        }
        System.arraycopy(this.peekBuffer, this.peekBufferPosition - i3, bArr, i2, i3);
        return true;
    }

    @Override // androidx.media3.extractor.ExtractorInput, androidx.media3.common.DataReader
    public int read(byte[] bArr, int i2, int i3) throws IOException {
        int fromPeekBuffer = readFromPeekBuffer(bArr, i2, i3);
        if (fromPeekBuffer == 0) {
            fromPeekBuffer = readFromUpstream(bArr, i2, i3, 0, true);
        }
        commitBytesRead(fromPeekBuffer);
        return fromPeekBuffer;
    }

    @Override // androidx.media3.extractor.ExtractorInput
    public boolean readFully(byte[] bArr, int i2, int i3, boolean z2) throws IOException {
        int fromPeekBuffer = readFromPeekBuffer(bArr, i2, i3);
        while (fromPeekBuffer < i3 && fromPeekBuffer != -1) {
            fromPeekBuffer = readFromUpstream(bArr, i2, i3, fromPeekBuffer, z2);
        }
        commitBytesRead(fromPeekBuffer);
        return fromPeekBuffer != -1;
    }

    @Override // androidx.media3.extractor.ExtractorInput
    public void resetPeekPosition() {
        this.peekBufferPosition = 0;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: E extends java.lang.Throwable */
    @Override // androidx.media3.extractor.ExtractorInput
    public <E extends Throwable> void setRetryPosition(long j2, E e2) throws Throwable {
        Assertions.checkArgument(j2 >= 0);
        this.position = j2;
        throw e2;
    }

    @Override // androidx.media3.extractor.ExtractorInput
    public int skip(int i2) throws IOException {
        int iSkipFromPeekBuffer = skipFromPeekBuffer(i2);
        if (iSkipFromPeekBuffer == 0) {
            byte[] bArr = this.scratchSpace;
            iSkipFromPeekBuffer = readFromUpstream(bArr, 0, Math.min(i2, bArr.length), 0, true);
        }
        commitBytesRead(iSkipFromPeekBuffer);
        return iSkipFromPeekBuffer;
    }

    @Override // androidx.media3.extractor.ExtractorInput
    public boolean skipFully(int i2, boolean z2) throws IOException {
        int iSkipFromPeekBuffer = skipFromPeekBuffer(i2);
        while (iSkipFromPeekBuffer < i2 && iSkipFromPeekBuffer != -1) {
            iSkipFromPeekBuffer = readFromUpstream(this.scratchSpace, -iSkipFromPeekBuffer, Math.min(i2, this.scratchSpace.length + iSkipFromPeekBuffer), iSkipFromPeekBuffer, z2);
        }
        commitBytesRead(iSkipFromPeekBuffer);
        return iSkipFromPeekBuffer != -1;
    }

    @Override // androidx.media3.extractor.ExtractorInput
    public void peekFully(byte[] bArr, int i2, int i3) throws IOException {
        peekFully(bArr, i2, i3, false);
    }

    @Override // androidx.media3.extractor.ExtractorInput
    public void readFully(byte[] bArr, int i2, int i3) throws IOException {
        readFully(bArr, i2, i3, false);
    }

    @Override // androidx.media3.extractor.ExtractorInput
    public void skipFully(int i2) throws IOException {
        skipFully(i2, false);
    }

    @Override // androidx.media3.extractor.ExtractorInput
    public void advancePeekPosition(int i2) throws IOException {
        advancePeekPosition(i2, false);
    }
}
