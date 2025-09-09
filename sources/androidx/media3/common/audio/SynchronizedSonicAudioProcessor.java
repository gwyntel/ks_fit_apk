package androidx.media3.common.audio;

import androidx.media3.common.audio.AudioProcessor;
import java.nio.ByteBuffer;

/* loaded from: classes.dex */
class SynchronizedSonicAudioProcessor implements AudioProcessor {
    private final Object lock;
    private final SonicAudioProcessor sonicAudioProcessor = new SonicAudioProcessor();

    public SynchronizedSonicAudioProcessor(Object obj) {
        this.lock = obj;
    }

    @Override // androidx.media3.common.audio.AudioProcessor
    public final AudioProcessor.AudioFormat configure(AudioProcessor.AudioFormat audioFormat) throws AudioProcessor.UnhandledAudioFormatException {
        AudioProcessor.AudioFormat audioFormatConfigure;
        synchronized (this.lock) {
            audioFormatConfigure = this.sonicAudioProcessor.configure(audioFormat);
        }
        return audioFormatConfigure;
    }

    @Override // androidx.media3.common.audio.AudioProcessor
    public final void flush() {
        synchronized (this.lock) {
            this.sonicAudioProcessor.flush();
        }
    }

    @Override // androidx.media3.common.audio.AudioProcessor
    public long getDurationAfterProcessorApplied(long j2) {
        return getPlayoutDuration(j2);
    }

    public final long getMediaDuration(long j2) {
        long mediaDuration;
        synchronized (this.lock) {
            mediaDuration = this.sonicAudioProcessor.getMediaDuration(j2);
        }
        return mediaDuration;
    }

    @Override // androidx.media3.common.audio.AudioProcessor
    public final ByteBuffer getOutput() {
        ByteBuffer output;
        synchronized (this.lock) {
            output = this.sonicAudioProcessor.getOutput();
        }
        return output;
    }

    public final long getPlayoutDuration(long j2) {
        long playoutDuration;
        synchronized (this.lock) {
            playoutDuration = this.sonicAudioProcessor.getPlayoutDuration(j2);
        }
        return playoutDuration;
    }

    public final long getProcessedInputBytes() {
        long processedInputBytes;
        synchronized (this.lock) {
            processedInputBytes = this.sonicAudioProcessor.getProcessedInputBytes();
        }
        return processedInputBytes;
    }

    @Override // androidx.media3.common.audio.AudioProcessor
    public final boolean isActive() {
        boolean zIsActive;
        synchronized (this.lock) {
            zIsActive = this.sonicAudioProcessor.isActive();
        }
        return zIsActive;
    }

    @Override // androidx.media3.common.audio.AudioProcessor
    public final boolean isEnded() {
        boolean zIsEnded;
        synchronized (this.lock) {
            zIsEnded = this.sonicAudioProcessor.isEnded();
        }
        return zIsEnded;
    }

    @Override // androidx.media3.common.audio.AudioProcessor
    public final void queueEndOfStream() {
        synchronized (this.lock) {
            this.sonicAudioProcessor.queueEndOfStream();
        }
    }

    @Override // androidx.media3.common.audio.AudioProcessor
    public final void queueInput(ByteBuffer byteBuffer) {
        synchronized (this.lock) {
            this.sonicAudioProcessor.queueInput(byteBuffer);
        }
    }

    @Override // androidx.media3.common.audio.AudioProcessor
    public final void reset() {
        synchronized (this.lock) {
            this.sonicAudioProcessor.reset();
        }
    }

    public final void setOutputSampleRateHz(int i2) {
        synchronized (this.lock) {
            this.sonicAudioProcessor.setOutputSampleRateHz(i2);
        }
    }

    public final void setPitch(float f2) {
        synchronized (this.lock) {
            this.sonicAudioProcessor.setPitch(f2);
        }
    }

    public final void setSpeed(float f2) {
        synchronized (this.lock) {
            this.sonicAudioProcessor.setSpeed(f2);
        }
    }
}
