package androidx.media3.exoplayer.source.chunk;

import androidx.annotation.Nullable;
import androidx.media3.common.C;
import androidx.media3.common.Format;
import androidx.media3.common.MimeTypes;
import androidx.media3.common.util.ParsableByteArray;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.datasource.DataSource;
import androidx.media3.datasource.DataSourceUtil;
import androidx.media3.datasource.DataSpec;
import androidx.media3.datasource.StatsDataSource;
import androidx.media3.exoplayer.source.chunk.ChunkExtractor;
import androidx.media3.extractor.DefaultExtractorInput;
import androidx.media3.extractor.TrackOutput;
import java.io.IOException;

@UnstableApi
/* loaded from: classes2.dex */
public class ContainerMediaChunk extends BaseMediaChunk {
    private final int chunkCount;
    private final ChunkExtractor chunkExtractor;
    private volatile boolean loadCanceled;
    private boolean loadCompleted;
    private long nextLoadPosition;
    private final long sampleOffsetUs;

    public ContainerMediaChunk(DataSource dataSource, DataSpec dataSpec, Format format, int i2, @Nullable Object obj, long j2, long j3, long j4, long j5, long j6, int i3, long j7, ChunkExtractor chunkExtractor) {
        super(dataSource, dataSpec, format, i2, obj, j2, j3, j4, j5, j6);
        this.chunkCount = i3;
        this.sampleOffsetUs = j7;
        this.chunkExtractor = chunkExtractor;
    }

    private void maybeWriteEmptySamples(BaseMediaChunkOutput baseMediaChunkOutput) {
        if (MimeTypes.isImage(this.trackFormat.containerMimeType)) {
            Format format = this.trackFormat;
            int i2 = format.tileCountHorizontal;
            if ((i2 <= 1 && format.tileCountVertical <= 1) || i2 == -1 || format.tileCountVertical == -1) {
                return;
            }
            TrackOutput trackOutputTrack = baseMediaChunkOutput.track(0, 4);
            Format format2 = this.trackFormat;
            int i3 = format2.tileCountVertical * format2.tileCountHorizontal;
            long j2 = (this.endTimeUs - this.startTimeUs) / i3;
            for (int i4 = 1; i4 < i3; i4++) {
                trackOutputTrack.sampleData(new ParsableByteArray(), 0);
                trackOutputTrack.sampleMetadata(i4 * j2, 0, 0, 0, null);
            }
        }
    }

    protected ChunkExtractor.TrackOutputProvider b(BaseMediaChunkOutput baseMediaChunkOutput) {
        return baseMediaChunkOutput;
    }

    @Override // androidx.media3.exoplayer.upstream.Loader.Loadable
    public final void cancelLoad() {
        this.loadCanceled = true;
    }

    @Override // androidx.media3.exoplayer.source.chunk.MediaChunk
    public long getNextChunkIndex() {
        return this.chunkIndex + this.chunkCount;
    }

    @Override // androidx.media3.exoplayer.source.chunk.MediaChunk
    public boolean isLoadCompleted() {
        return this.loadCompleted;
    }

    @Override // androidx.media3.exoplayer.upstream.Loader.Loadable
    public final void load() throws IOException {
        BaseMediaChunkOutput baseMediaChunkOutputA = a();
        if (this.nextLoadPosition == 0) {
            baseMediaChunkOutputA.setSampleOffsetUs(this.sampleOffsetUs);
            ChunkExtractor chunkExtractor = this.chunkExtractor;
            ChunkExtractor.TrackOutputProvider trackOutputProviderB = b(baseMediaChunkOutputA);
            long j2 = this.clippedStartTimeUs;
            long j3 = j2 == C.TIME_UNSET ? -9223372036854775807L : j2 - this.sampleOffsetUs;
            long j4 = this.clippedEndTimeUs;
            chunkExtractor.init(trackOutputProviderB, j3, j4 == C.TIME_UNSET ? -9223372036854775807L : j4 - this.sampleOffsetUs);
        }
        try {
            DataSpec dataSpecSubrange = this.dataSpec.subrange(this.nextLoadPosition);
            StatsDataSource statsDataSource = this.f5439a;
            DefaultExtractorInput defaultExtractorInput = new DefaultExtractorInput(statsDataSource, dataSpecSubrange.position, statsDataSource.open(dataSpecSubrange));
            do {
                try {
                    if (this.loadCanceled) {
                        break;
                    }
                } finally {
                    this.nextLoadPosition = defaultExtractorInput.getPosition() - this.dataSpec.position;
                }
            } while (this.chunkExtractor.read(defaultExtractorInput));
            maybeWriteEmptySamples(baseMediaChunkOutputA);
            this.nextLoadPosition = defaultExtractorInput.getPosition() - this.dataSpec.position;
            DataSourceUtil.closeQuietly(this.f5439a);
            this.loadCompleted = !this.loadCanceled;
        } catch (Throwable th) {
            DataSourceUtil.closeQuietly(this.f5439a);
            throw th;
        }
    }
}
