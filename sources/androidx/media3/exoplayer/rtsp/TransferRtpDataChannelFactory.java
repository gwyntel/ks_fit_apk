package androidx.media3.exoplayer.rtsp;

import androidx.media3.exoplayer.rtsp.RtpDataChannel;

/* loaded from: classes.dex */
final class TransferRtpDataChannelFactory implements RtpDataChannel.Factory {
    private static final int INTERLEAVED_CHANNELS_PER_TRACK = 2;
    private final long timeoutMs;

    public TransferRtpDataChannelFactory(long j2) {
        this.timeoutMs = j2;
    }

    @Override // androidx.media3.exoplayer.rtsp.RtpDataChannel.Factory
    public RtpDataChannel createAndOpenDataChannel(int i2) {
        TransferRtpDataChannel transferRtpDataChannel = new TransferRtpDataChannel(this.timeoutMs);
        transferRtpDataChannel.open(RtpUtils.getIncomingRtpDataSpec(i2 * 2));
        return transferRtpDataChannel;
    }

    @Override // androidx.media3.exoplayer.rtsp.RtpDataChannel.Factory
    public /* synthetic */ RtpDataChannel.Factory createFallbackDataChannelFactory() {
        return a.a(this);
    }
}
