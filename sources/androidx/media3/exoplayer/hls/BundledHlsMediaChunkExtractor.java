package androidx.media3.exoplayer.hls;

import androidx.media3.common.Format;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.TimestampAdjuster;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.extractor.Extractor;
import androidx.media3.extractor.ExtractorInput;
import androidx.media3.extractor.ExtractorOutput;
import androidx.media3.extractor.PositionHolder;
import androidx.media3.extractor.mp3.Mp3Extractor;
import androidx.media3.extractor.mp4.FragmentedMp4Extractor;
import androidx.media3.extractor.text.SubtitleParser;
import androidx.media3.extractor.ts.Ac3Extractor;
import androidx.media3.extractor.ts.Ac4Extractor;
import androidx.media3.extractor.ts.AdtsExtractor;
import androidx.media3.extractor.ts.TsExtractor;
import java.io.IOException;

@UnstableApi
/* loaded from: classes.dex */
public final class BundledHlsMediaChunkExtractor implements HlsMediaChunkExtractor {
    private static final PositionHolder POSITION_HOLDER = new PositionHolder();

    /* renamed from: a, reason: collision with root package name */
    final Extractor f5254a;
    private final Format multivariantPlaylistFormat;
    private final boolean parseSubtitlesDuringExtraction;
    private final SubtitleParser.Factory subtitleParserFactory;
    private final TimestampAdjuster timestampAdjuster;

    public BundledHlsMediaChunkExtractor(Extractor extractor, Format format, TimestampAdjuster timestampAdjuster) {
        this(extractor, format, timestampAdjuster, SubtitleParser.Factory.UNSUPPORTED, false);
    }

    @Override // androidx.media3.exoplayer.hls.HlsMediaChunkExtractor
    public void init(ExtractorOutput extractorOutput) {
        this.f5254a.init(extractorOutput);
    }

    @Override // androidx.media3.exoplayer.hls.HlsMediaChunkExtractor
    public boolean isPackedAudioExtractor() {
        Extractor underlyingImplementation = this.f5254a.getUnderlyingImplementation();
        return (underlyingImplementation instanceof AdtsExtractor) || (underlyingImplementation instanceof Ac3Extractor) || (underlyingImplementation instanceof Ac4Extractor) || (underlyingImplementation instanceof Mp3Extractor);
    }

    @Override // androidx.media3.exoplayer.hls.HlsMediaChunkExtractor
    public boolean isReusable() {
        Extractor underlyingImplementation = this.f5254a.getUnderlyingImplementation();
        return (underlyingImplementation instanceof TsExtractor) || (underlyingImplementation instanceof FragmentedMp4Extractor);
    }

    @Override // androidx.media3.exoplayer.hls.HlsMediaChunkExtractor
    public void onTruncatedSegmentParsed() {
        this.f5254a.seek(0L, 0L);
    }

    @Override // androidx.media3.exoplayer.hls.HlsMediaChunkExtractor
    public boolean read(ExtractorInput extractorInput) throws IOException {
        return this.f5254a.read(extractorInput, POSITION_HOLDER) == 0;
    }

    @Override // androidx.media3.exoplayer.hls.HlsMediaChunkExtractor
    public HlsMediaChunkExtractor recreate() {
        Extractor mp3Extractor;
        Assertions.checkState(!isReusable());
        Assertions.checkState(this.f5254a.getUnderlyingImplementation() == this.f5254a, "Can't recreate wrapped extractors. Outer type: " + this.f5254a.getClass());
        Extractor extractor = this.f5254a;
        if (extractor instanceof WebvttExtractor) {
            mp3Extractor = new WebvttExtractor(this.multivariantPlaylistFormat.language, this.timestampAdjuster, this.subtitleParserFactory, this.parseSubtitlesDuringExtraction);
        } else if (extractor instanceof AdtsExtractor) {
            mp3Extractor = new AdtsExtractor();
        } else if (extractor instanceof Ac3Extractor) {
            mp3Extractor = new Ac3Extractor();
        } else if (extractor instanceof Ac4Extractor) {
            mp3Extractor = new Ac4Extractor();
        } else {
            if (!(extractor instanceof Mp3Extractor)) {
                throw new IllegalStateException("Unexpected extractor type for recreation: " + this.f5254a.getClass().getSimpleName());
            }
            mp3Extractor = new Mp3Extractor();
        }
        return new BundledHlsMediaChunkExtractor(mp3Extractor, this.multivariantPlaylistFormat, this.timestampAdjuster, this.subtitleParserFactory, this.parseSubtitlesDuringExtraction);
    }

    BundledHlsMediaChunkExtractor(Extractor extractor, Format format, TimestampAdjuster timestampAdjuster, SubtitleParser.Factory factory, boolean z2) {
        this.f5254a = extractor;
        this.multivariantPlaylistFormat = format;
        this.timestampAdjuster = timestampAdjuster;
        this.subtitleParserFactory = factory;
        this.parseSubtitlesDuringExtraction = z2;
    }
}
