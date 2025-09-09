package androidx.media3.extractor;

import androidx.media3.common.util.UnstableApi;
import java.io.IOException;

@UnstableApi
/* loaded from: classes2.dex */
public class ForwardingExtractorInput implements ExtractorInput {
    private final ExtractorInput input;

    public ForwardingExtractorInput(ExtractorInput extractorInput) {
        this.input = extractorInput;
    }

    @Override // androidx.media3.extractor.ExtractorInput
    public boolean advancePeekPosition(int i2, boolean z2) throws IOException {
        return this.input.advancePeekPosition(i2, z2);
    }

    @Override // androidx.media3.extractor.ExtractorInput
    public long getLength() {
        return this.input.getLength();
    }

    @Override // androidx.media3.extractor.ExtractorInput
    public long getPeekPosition() {
        return this.input.getPeekPosition();
    }

    @Override // androidx.media3.extractor.ExtractorInput
    public long getPosition() {
        return this.input.getPosition();
    }

    @Override // androidx.media3.extractor.ExtractorInput
    public int peek(byte[] bArr, int i2, int i3) throws IOException {
        return this.input.peek(bArr, i2, i3);
    }

    @Override // androidx.media3.extractor.ExtractorInput
    public boolean peekFully(byte[] bArr, int i2, int i3, boolean z2) throws IOException {
        return this.input.peekFully(bArr, i2, i3, z2);
    }

    @Override // androidx.media3.extractor.ExtractorInput, androidx.media3.common.DataReader
    public int read(byte[] bArr, int i2, int i3) throws IOException {
        return this.input.read(bArr, i2, i3);
    }

    @Override // androidx.media3.extractor.ExtractorInput
    public boolean readFully(byte[] bArr, int i2, int i3, boolean z2) throws IOException {
        return this.input.readFully(bArr, i2, i3, z2);
    }

    @Override // androidx.media3.extractor.ExtractorInput
    public void resetPeekPosition() {
        this.input.resetPeekPosition();
    }

    @Override // androidx.media3.extractor.ExtractorInput
    public <E extends Throwable> void setRetryPosition(long j2, E e2) throws Throwable {
        this.input.setRetryPosition(j2, e2);
    }

    @Override // androidx.media3.extractor.ExtractorInput
    public int skip(int i2) throws IOException {
        return this.input.skip(i2);
    }

    @Override // androidx.media3.extractor.ExtractorInput
    public boolean skipFully(int i2, boolean z2) throws IOException {
        return this.input.skipFully(i2, z2);
    }

    @Override // androidx.media3.extractor.ExtractorInput
    public void advancePeekPosition(int i2) throws IOException {
        this.input.advancePeekPosition(i2);
    }

    @Override // androidx.media3.extractor.ExtractorInput
    public void peekFully(byte[] bArr, int i2, int i3) throws IOException {
        this.input.peekFully(bArr, i2, i3);
    }

    @Override // androidx.media3.extractor.ExtractorInput
    public void readFully(byte[] bArr, int i2, int i3) throws IOException {
        this.input.readFully(bArr, i2, i3);
    }

    @Override // androidx.media3.extractor.ExtractorInput
    public void skipFully(int i2) throws IOException {
        this.input.skipFully(i2);
    }
}
