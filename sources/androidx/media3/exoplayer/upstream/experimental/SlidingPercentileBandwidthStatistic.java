package androidx.media3.exoplayer.upstream.experimental;

import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.TreeSet;

@UnstableApi
/* loaded from: classes2.dex */
public class SlidingPercentileBandwidthStatistic implements BandwidthStatistic {
    public static final int DEFAULT_MAX_SAMPLES_COUNT = 10;
    public static final double DEFAULT_PERCENTILE = 0.5d;
    private long bitrateEstimate;
    private final int maxSampleCount;
    private final double percentile;
    private final ArrayDeque<Sample> samples;
    private final TreeSet<Sample> sortedSamples;
    private double weightSum;

    private static class Sample implements Comparable<Sample> {
        private final long bitrate;
        private final double weight;

        public Sample(long j2, double d2) {
            this.bitrate = j2;
            this.weight = d2;
        }

        @Override // java.lang.Comparable
        public int compareTo(Sample sample) {
            return Util.compareLong(this.bitrate, sample.bitrate);
        }
    }

    public SlidingPercentileBandwidthStatistic() {
        this(10, 0.5d);
    }

    private long calculateBitrateEstimate() {
        if (this.samples.isEmpty()) {
            return Long.MIN_VALUE;
        }
        double d2 = this.weightSum * this.percentile;
        Iterator<Sample> it = this.sortedSamples.iterator();
        double d3 = 0.0d;
        long j2 = 0;
        double d4 = 0.0d;
        while (it.hasNext()) {
            Sample next = it.next();
            double d5 = d3 + (next.weight / 2.0d);
            if (d5 >= d2) {
                return j2 == 0 ? next.bitrate : j2 + ((long) (((next.bitrate - j2) * (d2 - d4)) / (d5 - d4)));
            }
            j2 = next.bitrate;
            d3 = (next.weight / 2.0d) + d5;
            d4 = d5;
        }
        return j2;
    }

    @Override // androidx.media3.exoplayer.upstream.experimental.BandwidthStatistic
    public void addSample(long j2, long j3) {
        while (this.samples.size() >= this.maxSampleCount) {
            Sample sampleRemove = this.samples.remove();
            this.sortedSamples.remove(sampleRemove);
            this.weightSum -= sampleRemove.weight;
        }
        double dSqrt = Math.sqrt(j2);
        Sample sample = new Sample((j2 * 8000000) / j3, dSqrt);
        this.samples.add(sample);
        this.sortedSamples.add(sample);
        this.weightSum += dSqrt;
        this.bitrateEstimate = calculateBitrateEstimate();
    }

    @Override // androidx.media3.exoplayer.upstream.experimental.BandwidthStatistic
    public long getBandwidthEstimate() {
        return this.bitrateEstimate;
    }

    @Override // androidx.media3.exoplayer.upstream.experimental.BandwidthStatistic
    public void reset() {
        this.samples.clear();
        this.sortedSamples.clear();
        this.weightSum = 0.0d;
        this.bitrateEstimate = Long.MIN_VALUE;
    }

    public SlidingPercentileBandwidthStatistic(int i2, double d2) {
        Assertions.checkArgument(d2 >= 0.0d && d2 <= 1.0d);
        this.maxSampleCount = i2;
        this.percentile = d2;
        this.samples = new ArrayDeque<>();
        this.sortedSamples = new TreeSet<>();
        this.bitrateEstimate = Long.MIN_VALUE;
    }
}
