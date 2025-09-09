package androidx.media3.extractor;

import androidx.annotation.Nullable;
import androidx.media3.common.DataReader;
import androidx.media3.common.Format;
import androidx.media3.common.util.ParsableByteArray;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.extractor.TrackOutput;
import java.io.EOFException;
import java.io.IOException;

@UnstableApi
/* loaded from: classes2.dex */
public final class DiscardingTrackOutput implements TrackOutput {
    private final byte[] readBuffer = new byte[4096];

    @Override // androidx.media3.extractor.TrackOutput
    public void format(Format format) {
    }

    @Override // androidx.media3.extractor.TrackOutput
    public /* synthetic */ int sampleData(DataReader dataReader, int i2, boolean z2) {
        return g.a(this, dataReader, i2, z2);
    }

    @Override // androidx.media3.extractor.TrackOutput
    public void sampleMetadata(long j2, int i2, int i3, int i4, @Nullable TrackOutput.CryptoData cryptoData) {
    }

    @Override // androidx.media3.extractor.TrackOutput
    public /* synthetic */ void sampleData(ParsableByteArray parsableByteArray, int i2) {
        g.b(this, parsableByteArray, i2);
    }

    @Override // androidx.media3.extractor.TrackOutput
    public int sampleData(DataReader dataReader, int i2, boolean z2, int i3) throws IOException {
        int i4 = dataReader.read(this.readBuffer, 0, Math.min(this.readBuffer.length, i2));
        if (i4 != -1) {
            return i4;
        }
        if (z2) {
            return -1;
        }
        throw new EOFException();
    }

    @Override // androidx.media3.extractor.TrackOutput
    public void sampleData(ParsableByteArray parsableByteArray, int i2, int i3) {
        parsableByteArray.skipBytes(i2);
    }
}
