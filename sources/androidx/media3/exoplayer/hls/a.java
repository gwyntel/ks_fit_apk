package androidx.media3.exoplayer.hls;

import androidx.media3.common.Format;
import androidx.media3.extractor.text.SubtitleParser;

/* loaded from: classes.dex */
public abstract /* synthetic */ class a {
    public static HlsExtractorFactory a(HlsExtractorFactory hlsExtractorFactory, boolean z2) {
        return hlsExtractorFactory;
    }

    public static Format b(HlsExtractorFactory hlsExtractorFactory, Format format) {
        return format;
    }

    public static HlsExtractorFactory c(HlsExtractorFactory hlsExtractorFactory, SubtitleParser.Factory factory) {
        return hlsExtractorFactory;
    }
}
