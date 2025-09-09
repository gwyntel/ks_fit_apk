package androidx.media3.exoplayer.text;

import androidx.media3.extractor.text.SimpleSubtitleDecoder;
import androidx.media3.extractor.text.Subtitle;
import androidx.media3.extractor.text.SubtitleParser;

/* loaded from: classes2.dex */
final class DelegatingSubtitleDecoder extends SimpleSubtitleDecoder {
    private final SubtitleParser subtitleParser;

    public DelegatingSubtitleDecoder(String str, SubtitleParser subtitleParser) {
        super(str);
        this.subtitleParser = subtitleParser;
    }

    @Override // androidx.media3.extractor.text.SimpleSubtitleDecoder
    protected Subtitle m(byte[] bArr, int i2, boolean z2) {
        if (z2) {
            this.subtitleParser.reset();
        }
        return this.subtitleParser.parseToLegacySubtitle(bArr, 0, i2);
    }
}
