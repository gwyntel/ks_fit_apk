package androidx.media3.exoplayer.source.chunk;

import androidx.annotation.Nullable;
import androidx.media3.common.Format;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.analytics.PlayerId;
import androidx.media3.extractor.ChunkIndex;
import androidx.media3.extractor.ExtractorInput;
import androidx.media3.extractor.TrackOutput;
import androidx.media3.extractor.text.SubtitleParser;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.IOException;
import java.util.List;

@UnstableApi
/* loaded from: classes2.dex */
public interface ChunkExtractor {

    public interface Factory {
        @Nullable
        ChunkExtractor createProgressiveMediaExtractor(int i2, Format format, boolean z2, List<Format> list, @Nullable TrackOutput trackOutput, PlayerId playerId);

        @CanIgnoreReturnValue
        Factory experimentalParseSubtitlesDuringExtraction(boolean z2);

        Format getOutputTextFormat(Format format);

        @CanIgnoreReturnValue
        Factory setSubtitleParserFactory(SubtitleParser.Factory factory);
    }

    public interface TrackOutputProvider {
        TrackOutput track(int i2, int i3);
    }

    @Nullable
    ChunkIndex getChunkIndex();

    @Nullable
    Format[] getSampleFormats();

    void init(@Nullable TrackOutputProvider trackOutputProvider, long j2, long j3);

    boolean read(ExtractorInput extractorInput) throws IOException;

    void release();
}
