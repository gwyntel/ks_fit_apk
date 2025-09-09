package androidx.media3.common.audio;

import androidx.annotation.GuardedBy;
import androidx.media3.common.C;
import androidx.media3.common.audio.AudioProcessor;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.LongArray;
import androidx.media3.common.util.LongArrayQueue;
import androidx.media3.common.util.SpeedProviderUtil;
import androidx.media3.common.util.TimestampConsumer;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;
import com.kingsmith.miot.KsProperty;
import java.math.RoundingMode;
import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.Queue;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

@UnstableApi
/* loaded from: classes.dex */
public final class SpeedChangingAudioProcessor extends BaseAudioProcessor {
    private long bytesRead;

    @GuardedBy(KsProperty.Lock)
    private float currentSpeed;
    private boolean endOfStreamQueuedToSonic;

    @GuardedBy(KsProperty.Lock)
    private LongArray inputSegmentStartTimesUs;

    @GuardedBy(KsProperty.Lock)
    private long lastProcessedInputTimeUs;

    @GuardedBy(KsProperty.Lock)
    private long lastSpeedAdjustedInputTimeUs;

    @GuardedBy(KsProperty.Lock)
    private long lastSpeedAdjustedOutputTimeUs;
    private final Object lock;

    @GuardedBy(KsProperty.Lock)
    private LongArray outputSegmentStartTimesUs;

    @GuardedBy(KsProperty.Lock)
    private final LongArrayQueue pendingCallbackInputTimesUs;

    @GuardedBy(KsProperty.Lock)
    private final Queue<TimestampConsumer> pendingCallbacks;
    private final SynchronizedSonicAudioProcessor sonicAudioProcessor;

    @GuardedBy(KsProperty.Lock)
    private long speedAdjustedTimeAsyncInputTimeUs;
    private final SpeedProvider speedProvider;

    public SpeedChangingAudioProcessor(SpeedProvider speedProvider) {
        this.speedProvider = speedProvider;
        Object obj = new Object();
        this.lock = obj;
        this.sonicAudioProcessor = new SynchronizedSonicAudioProcessor(obj);
        this.pendingCallbackInputTimesUs = new LongArrayQueue();
        this.pendingCallbacks = new ArrayDeque();
        this.speedAdjustedTimeAsyncInputTimeUs = C.TIME_UNSET;
        resetState();
    }

    private long calculateSpeedAdjustedTime(long j2) {
        long jRound;
        int size = this.inputSegmentStartTimesUs.size() - 1;
        while (size > 0 && this.inputSegmentStartTimesUs.get(size) > j2) {
            size--;
        }
        if (size == this.inputSegmentStartTimesUs.size() - 1) {
            if (this.lastSpeedAdjustedInputTimeUs < this.inputSegmentStartTimesUs.get(size)) {
                this.lastSpeedAdjustedInputTimeUs = this.inputSegmentStartTimesUs.get(size);
                this.lastSpeedAdjustedOutputTimeUs = this.outputSegmentStartTimesUs.get(size);
            }
            jRound = getPlayoutDurationUsAtCurrentSpeed(j2 - this.lastSpeedAdjustedInputTimeUs);
        } else {
            int i2 = size + 1;
            jRound = Math.round((j2 - this.lastSpeedAdjustedInputTimeUs) * divide(this.outputSegmentStartTimesUs.get(i2) - this.outputSegmentStartTimesUs.get(size), this.inputSegmentStartTimesUs.get(i2) - this.inputSegmentStartTimesUs.get(size)));
        }
        this.lastSpeedAdjustedInputTimeUs = j2;
        long j3 = this.lastSpeedAdjustedOutputTimeUs + jRound;
        this.lastSpeedAdjustedOutputTimeUs = j3;
        return j3;
    }

    private static double divide(long j2, long j3) {
        return j2 / j3;
    }

    private long getMediaDurationUsAtCurrentSpeed(long j2) {
        return isUsingSonic() ? this.sonicAudioProcessor.getMediaDuration(j2) : j2;
    }

    private long getPlayoutDurationUsAtCurrentSpeed(long j2) {
        return isUsingSonic() ? this.sonicAudioProcessor.getPlayoutDuration(j2) : j2;
    }

    private boolean isUsingSonic() {
        boolean z2;
        synchronized (this.lock) {
            z2 = this.currentSpeed != 1.0f;
        }
        return z2;
    }

    private void processPendingCallbacks() {
        synchronized (this.lock) {
            while (!this.pendingCallbacks.isEmpty() && (this.pendingCallbackInputTimesUs.element() <= this.lastProcessedInputTimeUs || isEnded())) {
                try {
                    this.pendingCallbacks.remove().onTimestamp(calculateSpeedAdjustedTime(this.pendingCallbackInputTimesUs.remove()));
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    @EnsuresNonNull({"inputSegmentStartTimesUs", "outputSegmentStartTimesUs"})
    @RequiresNonNull({KsProperty.Lock})
    private void resetState() {
        synchronized (this.lock) {
            this.inputSegmentStartTimesUs = new LongArray();
            this.outputSegmentStartTimesUs = new LongArray();
            this.inputSegmentStartTimesUs.add(0L);
            this.outputSegmentStartTimesUs.add(0L);
            this.lastProcessedInputTimeUs = 0L;
            this.lastSpeedAdjustedInputTimeUs = 0L;
            this.lastSpeedAdjustedOutputTimeUs = 0L;
            this.currentSpeed = 1.0f;
        }
        this.bytesRead = 0L;
        this.endOfStreamQueuedToSonic = false;
    }

    private void updateLastProcessedInputTime() {
        synchronized (this.lock) {
            try {
                if (isUsingSonic()) {
                    long processedInputBytes = this.sonicAudioProcessor.getProcessedInputBytes();
                    AudioProcessor.AudioFormat audioFormat = this.f4756a;
                    this.lastProcessedInputTimeUs = this.inputSegmentStartTimesUs.get(r3.size() - 1) + Util.scaleLargeTimestamp(processedInputBytes, 1000000L, audioFormat.bytesPerFrame * audioFormat.sampleRate);
                } else {
                    long j2 = this.bytesRead;
                    AudioProcessor.AudioFormat audioFormat2 = this.f4756a;
                    this.lastProcessedInputTimeUs = Util.scaleLargeTimestamp(j2, 1000000L, audioFormat2.bytesPerFrame * audioFormat2.sampleRate);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private void updateSpeed(float f2, long j2) {
        synchronized (this.lock) {
            try {
                if (f2 != this.currentSpeed) {
                    updateSpeedChangeArrays(j2);
                    this.currentSpeed = f2;
                    if (isUsingSonic()) {
                        this.sonicAudioProcessor.setSpeed(f2);
                        this.sonicAudioProcessor.setPitch(f2);
                    }
                    this.sonicAudioProcessor.flush();
                    this.endOfStreamQueuedToSonic = false;
                    super.getOutput();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private void updateSpeedChangeArrays(long j2) {
        long j3 = this.outputSegmentStartTimesUs.get(r0.size() - 1);
        long j4 = j2 - this.inputSegmentStartTimesUs.get(r2.size() - 1);
        this.inputSegmentStartTimesUs.add(j2);
        this.outputSegmentStartTimesUs.add(j3 + getPlayoutDurationUsAtCurrentSpeed(j4));
    }

    @Override // androidx.media3.common.audio.BaseAudioProcessor, androidx.media3.common.audio.AudioProcessor
    public long getDurationAfterProcessorApplied(long j2) {
        return SpeedProviderUtil.getDurationAfterSpeedProviderApplied(this.speedProvider, j2);
    }

    public long getMediaDurationUs(long j2) {
        long jRound;
        long j3;
        synchronized (this.lock) {
            try {
                int size = this.outputSegmentStartTimesUs.size() - 1;
                while (size > 0 && this.outputSegmentStartTimesUs.get(size) > j2) {
                    size--;
                }
                long j4 = j2 - this.outputSegmentStartTimesUs.get(size);
                if (size == this.outputSegmentStartTimesUs.size() - 1) {
                    jRound = getMediaDurationUsAtCurrentSpeed(j4);
                } else {
                    int i2 = size + 1;
                    jRound = Math.round(j4 * divide(this.inputSegmentStartTimesUs.get(i2) - this.inputSegmentStartTimesUs.get(size), this.outputSegmentStartTimesUs.get(i2) - this.outputSegmentStartTimesUs.get(size)));
                }
                j3 = this.inputSegmentStartTimesUs.get(size) + jRound;
            } catch (Throwable th) {
                throw th;
            }
        }
        return j3;
    }

    @Override // androidx.media3.common.audio.BaseAudioProcessor, androidx.media3.common.audio.AudioProcessor
    public ByteBuffer getOutput() {
        ByteBuffer output = isUsingSonic() ? this.sonicAudioProcessor.getOutput() : super.getOutput();
        processPendingCallbacks();
        return output;
    }

    public void getSpeedAdjustedTimeAsync(long j2, TimestampConsumer timestampConsumer) {
        synchronized (this.lock) {
            try {
                Assertions.checkArgument(this.speedAdjustedTimeAsyncInputTimeUs < j2);
                this.speedAdjustedTimeAsyncInputTimeUs = j2;
                if (j2 > this.lastProcessedInputTimeUs || !this.pendingCallbackInputTimesUs.isEmpty()) {
                    if (!isEnded()) {
                        this.pendingCallbackInputTimesUs.add(j2);
                        this.pendingCallbacks.add(timestampConsumer);
                        return;
                    }
                }
                timestampConsumer.onTimestamp(calculateSpeedAdjustedTime(j2));
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // androidx.media3.common.audio.BaseAudioProcessor, androidx.media3.common.audio.AudioProcessor
    public boolean isEnded() {
        return super.isEnded() && this.sonicAudioProcessor.isEnded();
    }

    @Override // androidx.media3.common.audio.BaseAudioProcessor
    public AudioProcessor.AudioFormat onConfigure(AudioProcessor.AudioFormat audioFormat) throws AudioProcessor.UnhandledAudioFormatException {
        return this.sonicAudioProcessor.configure(audioFormat);
    }

    @Override // androidx.media3.common.audio.BaseAudioProcessor
    protected void onFlush() {
        resetState();
        this.sonicAudioProcessor.flush();
    }

    @Override // androidx.media3.common.audio.BaseAudioProcessor
    protected void onQueueEndOfStream() {
        if (this.endOfStreamQueuedToSonic) {
            return;
        }
        this.sonicAudioProcessor.queueEndOfStream();
        this.endOfStreamQueuedToSonic = true;
    }

    @Override // androidx.media3.common.audio.BaseAudioProcessor
    protected void onReset() {
        resetState();
        this.sonicAudioProcessor.reset();
    }

    @Override // androidx.media3.common.audio.AudioProcessor
    public void queueInput(ByteBuffer byteBuffer) {
        int iScaleLargeValue;
        long j2 = this.bytesRead;
        AudioProcessor.AudioFormat audioFormat = this.f4756a;
        long jScaleLargeTimestamp = Util.scaleLargeTimestamp(j2, 1000000L, audioFormat.sampleRate * audioFormat.bytesPerFrame);
        updateSpeed(this.speedProvider.getSpeed(jScaleLargeTimestamp), jScaleLargeTimestamp);
        int iLimit = byteBuffer.limit();
        long nextSpeedChangeTimeUs = this.speedProvider.getNextSpeedChangeTimeUs(jScaleLargeTimestamp);
        if (nextSpeedChangeTimeUs != C.TIME_UNSET) {
            long j3 = nextSpeedChangeTimeUs - jScaleLargeTimestamp;
            AudioProcessor.AudioFormat audioFormat2 = this.f4756a;
            iScaleLargeValue = (int) Util.scaleLargeValue(j3, audioFormat2.sampleRate * audioFormat2.bytesPerFrame, 1000000L, RoundingMode.CEILING);
            int i2 = this.f4756a.bytesPerFrame;
            int i3 = i2 - (iScaleLargeValue % i2);
            if (i3 != i2) {
                iScaleLargeValue += i3;
            }
            byteBuffer.limit(Math.min(iLimit, byteBuffer.position() + iScaleLargeValue));
        } else {
            iScaleLargeValue = -1;
        }
        long jPosition = byteBuffer.position();
        if (isUsingSonic()) {
            this.sonicAudioProcessor.queueInput(byteBuffer);
            if (iScaleLargeValue != -1 && byteBuffer.position() - jPosition == iScaleLargeValue) {
                this.sonicAudioProcessor.queueEndOfStream();
                this.endOfStreamQueuedToSonic = true;
            }
        } else {
            ByteBuffer byteBufferB = b(byteBuffer.remaining());
            if (byteBuffer.hasRemaining()) {
                byteBufferB.put(byteBuffer);
            }
            byteBufferB.flip();
        }
        this.bytesRead += byteBuffer.position() - jPosition;
        updateLastProcessedInputTime();
        byteBuffer.limit(iLimit);
    }
}
