package androidx.media3.extractor.mp3;

import androidx.media3.extractor.ConstantBitrateSeekMap;
import androidx.media3.extractor.MpegAudioUtil;

/* loaded from: classes2.dex */
final class ConstantBitrateSeeker extends ConstantBitrateSeekMap implements Seeker {
    private final int bitrate;

    public ConstantBitrateSeeker(long j2, long j3, MpegAudioUtil.Header header, boolean z2) {
        this(j2, j3, header.bitrate, header.frameSize, z2);
    }

    @Override // androidx.media3.extractor.mp3.Seeker
    public int getAverageBitrate() {
        return this.bitrate;
    }

    @Override // androidx.media3.extractor.mp3.Seeker
    public long getDataEndPosition() {
        return -1L;
    }

    @Override // androidx.media3.extractor.mp3.Seeker
    public long getTimeUs(long j2) {
        return getTimeUsAtPosition(j2);
    }

    public ConstantBitrateSeeker(long j2, long j3, int i2, int i3, boolean z2) {
        super(j2, j3, i2, i3, z2);
        this.bitrate = i2;
    }
}
