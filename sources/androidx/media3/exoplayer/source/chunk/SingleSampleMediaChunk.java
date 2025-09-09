package androidx.media3.exoplayer.source.chunk;

import androidx.annotation.Nullable;
import androidx.media3.common.C;
import androidx.media3.common.DataReader;
import androidx.media3.common.Format;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.datasource.DataSource;
import androidx.media3.datasource.DataSourceUtil;
import androidx.media3.datasource.DataSpec;
import androidx.media3.extractor.DefaultExtractorInput;
import androidx.media3.extractor.TrackOutput;
import java.io.IOException;

@UnstableApi
/* loaded from: classes2.dex */
public final class SingleSampleMediaChunk extends BaseMediaChunk {
    private boolean loadCompleted;
    private long nextLoadPosition;
    private final Format sampleFormat;
    private final int trackType;

    public SingleSampleMediaChunk(DataSource dataSource, DataSpec dataSpec, Format format, int i2, @Nullable Object obj, long j2, long j3, long j4, int i3, Format format2) {
        super(dataSource, dataSpec, format, i2, obj, j2, j3, C.TIME_UNSET, C.TIME_UNSET, j4);
        this.trackType = i3;
        this.sampleFormat = format2;
    }

    @Override // androidx.media3.exoplayer.upstream.Loader.Loadable
    public void cancelLoad() {
    }

    @Override // androidx.media3.exoplayer.source.chunk.MediaChunk
    public boolean isLoadCompleted() {
        return this.loadCompleted;
    }

    @Override // androidx.media3.exoplayer.upstream.Loader.Loadable
    public void load() throws IOException {
        BaseMediaChunkOutput baseMediaChunkOutputA = a();
        baseMediaChunkOutputA.setSampleOffsetUs(0L);
        TrackOutput trackOutputTrack = baseMediaChunkOutputA.track(0, this.trackType);
        trackOutputTrack.format(this.sampleFormat);
        try {
            long jOpen = this.f5439a.open(this.dataSpec.subrange(this.nextLoadPosition));
            if (jOpen != -1) {
                jOpen += this.nextLoadPosition;
            }
            DefaultExtractorInput defaultExtractorInput = new DefaultExtractorInput(this.f5439a, this.nextLoadPosition, jOpen);
            for (int iSampleData = 0; iSampleData != -1; iSampleData = trackOutputTrack.sampleData((DataReader) defaultExtractorInput, Integer.MAX_VALUE, true)) {
                this.nextLoadPosition += iSampleData;
            }
            trackOutputTrack.sampleMetadata(this.startTimeUs, 1, (int) this.nextLoadPosition, 0, null);
            DataSourceUtil.closeQuietly(this.f5439a);
            this.loadCompleted = true;
        } catch (Throwable th) {
            DataSourceUtil.closeQuietly(this.f5439a);
            throw th;
        }
    }
}
