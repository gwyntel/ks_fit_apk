package androidx.media3.extractor.text;

import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.decoder.SimpleDecoder;
import java.nio.ByteBuffer;

@UnstableApi
/* loaded from: classes2.dex */
public abstract class SimpleSubtitleDecoder extends SimpleDecoder<SubtitleInputBuffer, SubtitleOutputBuffer, SubtitleDecoderException> implements SubtitleDecoder {
    private final String name;

    protected SimpleSubtitleDecoder(String str) {
        super(new SubtitleInputBuffer[2], new SubtitleOutputBuffer[2]);
        this.name = str;
        h(1024);
    }

    @Override // androidx.media3.decoder.Decoder
    public final String getName() {
        return this.name;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.media3.decoder.SimpleDecoder
    /* renamed from: j, reason: merged with bridge method [inline-methods] */
    public final SubtitleInputBuffer b() {
        return new SubtitleInputBuffer();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.media3.decoder.SimpleDecoder
    /* renamed from: k, reason: merged with bridge method [inline-methods] */
    public final SubtitleOutputBuffer c() {
        return new SubtitleOutputBuffer() { // from class: androidx.media3.extractor.text.SimpleSubtitleDecoder.1
            @Override // androidx.media3.decoder.DecoderOutputBuffer
            public void release() {
                SimpleSubtitleDecoder.this.g(this);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.media3.decoder.SimpleDecoder
    /* renamed from: l, reason: merged with bridge method [inline-methods] */
    public final SubtitleDecoderException d(Throwable th) {
        return new SubtitleDecoderException("Unexpected decode error", th);
    }

    protected abstract Subtitle m(byte[] bArr, int i2, boolean z2);

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.media3.decoder.SimpleDecoder
    /* renamed from: n, reason: merged with bridge method [inline-methods] */
    public final SubtitleDecoderException e(SubtitleInputBuffer subtitleInputBuffer, SubtitleOutputBuffer subtitleOutputBuffer, boolean z2) {
        try {
            ByteBuffer byteBuffer = (ByteBuffer) Assertions.checkNotNull(subtitleInputBuffer.data);
            subtitleOutputBuffer.setContent(subtitleInputBuffer.timeUs, m(byteBuffer.array(), byteBuffer.limit(), z2), subtitleInputBuffer.subsampleOffsetUs);
            subtitleOutputBuffer.shouldBeSkipped = false;
            return null;
        } catch (SubtitleDecoderException e2) {
            return e2;
        }
    }

    @Override // androidx.media3.extractor.text.SubtitleDecoder
    public void setPositionUs(long j2) {
    }
}
