package androidx.media3.exoplayer.audio;

import android.util.SparseArray;
import androidx.annotation.FloatRange;
import androidx.health.connect.client.records.HeartRateVariabilityRmssdRecord;
import androidx.media3.common.audio.AudioMixingUtil;
import androidx.media3.common.audio.AudioProcessor;
import androidx.media3.common.audio.ChannelMixingMatrix;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;
import androidx.media3.exoplayer.audio.TeeAudioProcessor;
import com.google.common.base.Preconditions;
import java.nio.ByteBuffer;

@UnstableApi
/* loaded from: classes.dex */
public class WaveformAudioBufferSink implements TeeAudioProcessor.AudioBufferSink {
    private final int barsPerSecond;
    private ChannelMixingMatrix channelMixingMatrix;
    private AudioProcessor.AudioFormat inputAudioFormat;
    private final Listener listener;
    private AudioProcessor.AudioFormat mixingAudioFormat;
    private final ByteBuffer mixingBuffer;
    private final SparseArray<WaveformBar> outputChannels;
    private int samplesPerBar;

    public interface Listener {
        void onNewWaveformBar(int i2, WaveformBar waveformBar);
    }

    public static class WaveformBar {
        private int sampleCount;
        private double squareSum;
        private float minSampleValue = 1.0f;
        private float maxSampleValue = -1.0f;

        public void addSample(@FloatRange(from = -1.0d, to = HeartRateVariabilityRmssdRecord.MIN_HRV_RMSSD) float f2) {
            Preconditions.checkArgument(f2 >= -1.0f && f2 <= 1.0f);
            this.minSampleValue = Math.min(this.minSampleValue, f2);
            this.maxSampleValue = Math.max(this.maxSampleValue, f2);
            double d2 = f2;
            this.squareSum += d2 * d2;
            this.sampleCount++;
        }

        public double getMaxSampleValue() {
            return this.maxSampleValue;
        }

        public double getMinSampleValue() {
            return this.minSampleValue;
        }

        public double getRootMeanSquare() {
            return Math.sqrt(this.squareSum / this.sampleCount);
        }

        public int getSampleCount() {
            return this.sampleCount;
        }
    }

    public WaveformAudioBufferSink(int i2, int i3, Listener listener) {
        this.barsPerSecond = i2;
        this.listener = listener;
        this.mixingBuffer = ByteBuffer.allocate(Util.getPcmFrameSize(4, i3));
        this.outputChannels = new SparseArray<>(i3);
        for (int i4 = 0; i4 < i3; i4++) {
            this.outputChannels.append(i4, new WaveformBar());
        }
    }

    @Override // androidx.media3.exoplayer.audio.TeeAudioProcessor.AudioBufferSink
    public void flush(int i2, int i3, int i4) {
        this.samplesPerBar = i2 / this.barsPerSecond;
        this.inputAudioFormat = new AudioProcessor.AudioFormat(i2, i3, i4);
        this.mixingAudioFormat = new AudioProcessor.AudioFormat(i2, this.outputChannels.size(), 4);
        this.channelMixingMatrix = ChannelMixingMatrix.create(i3, this.outputChannels.size());
    }

    @Override // androidx.media3.exoplayer.audio.TeeAudioProcessor.AudioBufferSink
    public void handleBuffer(ByteBuffer byteBuffer) {
        Assertions.checkStateNotNull(this.inputAudioFormat);
        Assertions.checkStateNotNull(this.mixingAudioFormat);
        Assertions.checkStateNotNull(this.channelMixingMatrix);
        while (byteBuffer.hasRemaining()) {
            this.mixingBuffer.rewind();
            AudioMixingUtil.mix(byteBuffer, this.inputAudioFormat, this.mixingBuffer, this.mixingAudioFormat, this.channelMixingMatrix, 1, false, true);
            this.mixingBuffer.rewind();
            for (int i2 = 0; i2 < this.outputChannels.size(); i2++) {
                WaveformBar waveformBar = this.outputChannels.get(i2);
                waveformBar.addSample(this.mixingBuffer.getFloat());
                if (waveformBar.getSampleCount() >= this.samplesPerBar) {
                    this.listener.onNewWaveformBar(i2, waveformBar);
                    this.outputChannels.put(i2, new WaveformBar());
                }
            }
        }
    }
}
