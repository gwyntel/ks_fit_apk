package androidx.media3.exoplayer.dash;

import androidx.media3.common.Format;
import androidx.media3.exoplayer.dash.DashChunkSource;
import androidx.media3.extractor.text.SubtitleParser;

/* loaded from: classes.dex */
public abstract /* synthetic */ class b {
    public static DashChunkSource.Factory a(DashChunkSource.Factory factory, boolean z2) {
        return factory;
    }

    public static Format b(DashChunkSource.Factory factory, Format format) {
        return format;
    }

    public static DashChunkSource.Factory c(DashChunkSource.Factory factory, SubtitleParser.Factory factory2) {
        return factory;
    }
}
