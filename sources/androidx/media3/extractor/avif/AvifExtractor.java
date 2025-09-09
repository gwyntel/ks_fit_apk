package androidx.media3.extractor.avif;

import androidx.media3.common.MimeTypes;
import androidx.media3.common.util.ParsableByteArray;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.extractor.Extractor;
import androidx.media3.extractor.ExtractorInput;
import androidx.media3.extractor.ExtractorOutput;
import androidx.media3.extractor.PositionHolder;
import androidx.media3.extractor.SingleSampleExtractor;
import androidx.media3.extractor.d;
import java.io.IOException;
import java.util.List;

@UnstableApi
/* loaded from: classes2.dex */
public final class AvifExtractor implements Extractor {
    private static final int AVIF_FILE_SIGNATURE_PART_1 = 1718909296;
    private static final int AVIF_FILE_SIGNATURE_PART_2 = 1635150182;
    private static final int FILE_SIGNATURE_SEGMENT_LENGTH = 4;
    private final ParsableByteArray scratch = new ParsableByteArray(4);
    private final SingleSampleExtractor imageExtractor = new SingleSampleExtractor(-1, -1, MimeTypes.IMAGE_AVIF);

    private boolean readAndCompareFourBytes(ExtractorInput extractorInput, int i2) throws IOException {
        this.scratch.reset(4);
        extractorInput.peekFully(this.scratch.getData(), 0, 4);
        return this.scratch.readUnsignedInt() == ((long) i2);
    }

    @Override // androidx.media3.extractor.Extractor
    public /* synthetic */ List getSniffFailureDetails() {
        return d.a(this);
    }

    @Override // androidx.media3.extractor.Extractor
    public /* synthetic */ Extractor getUnderlyingImplementation() {
        return d.b(this);
    }

    @Override // androidx.media3.extractor.Extractor
    public void init(ExtractorOutput extractorOutput) {
        this.imageExtractor.init(extractorOutput);
    }

    @Override // androidx.media3.extractor.Extractor
    public int read(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException {
        return this.imageExtractor.read(extractorInput, positionHolder);
    }

    @Override // androidx.media3.extractor.Extractor
    public void release() {
    }

    @Override // androidx.media3.extractor.Extractor
    public void seek(long j2, long j3) {
        this.imageExtractor.seek(j2, j3);
    }

    @Override // androidx.media3.extractor.Extractor
    public boolean sniff(ExtractorInput extractorInput) throws IOException {
        extractorInput.advancePeekPosition(4);
        return readAndCompareFourBytes(extractorInput, 1718909296) && readAndCompareFourBytes(extractorInput, AVIF_FILE_SIGNATURE_PART_2);
    }
}
