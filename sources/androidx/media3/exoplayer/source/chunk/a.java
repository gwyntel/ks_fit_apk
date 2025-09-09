package androidx.media3.exoplayer.source.chunk;

import androidx.media3.common.Format;
import androidx.media3.exoplayer.source.chunk.ChunkExtractor;
import androidx.media3.extractor.text.SubtitleParser;

/* loaded from: classes2.dex */
public abstract /* synthetic */ class a {
    public static ChunkExtractor.Factory a(ChunkExtractor.Factory factory, boolean z2) {
        return factory;
    }

    public static Format b(ChunkExtractor.Factory factory, Format format) {
        return format;
    }

    public static ChunkExtractor.Factory c(ChunkExtractor.Factory factory, SubtitleParser.Factory factory2) {
        return factory;
    }
}
