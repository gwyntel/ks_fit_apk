package androidx.media3.exoplayer.upstream.experimental;

import androidx.media3.common.util.UnstableApi;

@UnstableApi
/* loaded from: classes2.dex */
public class ExponentialWeightedAverageStatistic implements BandwidthStatistic {
    public static final double DEFAULT_SMOOTHING_FACTOR = 0.9999d;
    private long bitrateEstimate;
    private final double smoothingFactor;

    public ExponentialWeightedAverageStatistic() {
        this(0.9999d);
    }

    @Override // androidx.media3.exoplayer.upstream.experimental.BandwidthStatistic
    public void addSample(long j2, long j3) {
        long j4 = (8000000 * j2) / j3;
        if (this.bitrateEstimate == Long.MIN_VALUE) {
            this.bitrateEstimate = j4;
        } else {
            double dPow = Math.pow(this.smoothingFactor, Math.sqrt(j2));
            this.bitrateEstimate = (long) ((this.bitrateEstimate * dPow) + ((1.0d - dPow) * j4));
        }
    }

    @Override // androidx.media3.exoplayer.upstream.experimental.BandwidthStatistic
    public long getBandwidthEstimate() {
        return this.bitrateEstimate;
    }

    @Override // androidx.media3.exoplayer.upstream.experimental.BandwidthStatistic
    public void reset() {
        this.bitrateEstimate = Long.MIN_VALUE;
    }

    public ExponentialWeightedAverageStatistic(double d2) {
        this.smoothingFactor = d2;
        this.bitrateEstimate = Long.MIN_VALUE;
    }
}
