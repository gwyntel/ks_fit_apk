package androidx.media3.extractor;

import androidx.annotation.Nullable;
import androidx.media3.common.DataReader;
import androidx.media3.common.Format;
import androidx.media3.common.util.ParsableByteArray;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.extractor.TrackOutput;
import java.io.IOException;

@UnstableApi
@Deprecated
/* loaded from: classes2.dex */
public final class DummyTrackOutput implements TrackOutput {
    private final DiscardingTrackOutput discardingTrackOutput = new DiscardingTrackOutput();

    @Override // androidx.media3.extractor.TrackOutput
    public void format(Format format) {
        this.discardingTrackOutput.format(format);
    }

    @Override // androidx.media3.extractor.TrackOutput
    public int sampleData(DataReader dataReader, int i2, boolean z2) throws IOException {
        return this.discardingTrackOutput.sampleData(dataReader, i2, z2);
    }

    @Override // androidx.media3.extractor.TrackOutput
    public void sampleMetadata(long j2, int i2, int i3, int i4, @Nullable TrackOutput.CryptoData cryptoData) {
        this.discardingTrackOutput.sampleMetadata(j2, i2, i3, i4, cryptoData);
    }

    @Override // androidx.media3.extractor.TrackOutput
    public void sampleData(ParsableByteArray parsableByteArray, int i2) {
        this.discardingTrackOutput.sampleData(parsableByteArray, i2);
    }

    @Override // androidx.media3.extractor.TrackOutput
    public int sampleData(DataReader dataReader, int i2, boolean z2, int i3) throws IOException {
        return this.discardingTrackOutput.sampleData(dataReader, i2, z2, i3);
    }

    @Override // androidx.media3.extractor.TrackOutput
    public void sampleData(ParsableByteArray parsableByteArray, int i2, int i3) {
        this.discardingTrackOutput.sampleData(parsableByteArray, i2, i3);
    }
}
