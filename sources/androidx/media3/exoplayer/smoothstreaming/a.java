package androidx.media3.exoplayer.smoothstreaming;

import androidx.media3.common.Format;
import androidx.media3.exoplayer.smoothstreaming.SsChunkSource;
import androidx.media3.extractor.text.SubtitleParser;

/* loaded from: classes.dex */
public abstract /* synthetic */ class a {
    public static SsChunkSource.Factory a(SsChunkSource.Factory factory, boolean z2) {
        return factory;
    }

    public static Format b(SsChunkSource.Factory factory, Format format) {
        return format;
    }

    public static SsChunkSource.Factory c(SsChunkSource.Factory factory, SubtitleParser.Factory factory2) {
        return factory;
    }
}
