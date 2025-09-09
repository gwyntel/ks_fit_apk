package androidx.media3.exoplayer.audio;

import androidx.media3.common.audio.AudioProcessor;
import androidx.media3.common.util.Util;
import java.nio.ByteBuffer;

/* loaded from: classes.dex */
final class TrimmingAudioProcessor extends androidx.media3.common.audio.BaseAudioProcessor {
    private static final int OUTPUT_ENCODING = 2;
    private byte[] endBuffer = Util.EMPTY_BYTE_ARRAY;
    private int endBufferSize;
    private int pendingTrimStartBytes;
    private boolean reconfigurationPending;
    private int trimEndFrames;
    private int trimStartFrames;
    private long trimmedFrameCount;

    @Override // androidx.media3.common.audio.BaseAudioProcessor, androidx.media3.common.audio.AudioProcessor
    public long getDurationAfterProcessorApplied(long j2) {
        return j2 - Util.sampleCountToDurationUs(this.trimEndFrames + this.trimStartFrames, this.f4756a.sampleRate);
    }

    @Override // androidx.media3.common.audio.BaseAudioProcessor, androidx.media3.common.audio.AudioProcessor
    public ByteBuffer getOutput() {
        int i2;
        if (super.isEnded() && (i2 = this.endBufferSize) > 0) {
            b(i2).put(this.endBuffer, 0, this.endBufferSize).flip();
            this.endBufferSize = 0;
        }
        return super.getOutput();
    }

    public long getTrimmedFrameCount() {
        return this.trimmedFrameCount;
    }

    @Override // androidx.media3.common.audio.BaseAudioProcessor, androidx.media3.common.audio.AudioProcessor
    public boolean isEnded() {
        return super.isEnded() && this.endBufferSize == 0;
    }

    @Override // androidx.media3.common.audio.BaseAudioProcessor
    public AudioProcessor.AudioFormat onConfigure(AudioProcessor.AudioFormat audioFormat) throws AudioProcessor.UnhandledAudioFormatException {
        if (audioFormat.encoding != 2) {
            throw new AudioProcessor.UnhandledAudioFormatException(audioFormat);
        }
        this.reconfigurationPending = true;
        return (this.trimStartFrames == 0 && this.trimEndFrames == 0) ? AudioProcessor.AudioFormat.NOT_SET : audioFormat;
    }

    @Override // androidx.media3.common.audio.BaseAudioProcessor
    protected void onFlush() {
        if (this.reconfigurationPending) {
            this.reconfigurationPending = false;
            int i2 = this.trimEndFrames;
            int i3 = this.f4756a.bytesPerFrame;
            this.endBuffer = new byte[i2 * i3];
            this.pendingTrimStartBytes = this.trimStartFrames * i3;
        }
        this.endBufferSize = 0;
    }

    @Override // androidx.media3.common.audio.BaseAudioProcessor
    protected void onQueueEndOfStream() {
        if (this.reconfigurationPending) {
            if (this.endBufferSize > 0) {
                this.trimmedFrameCount += r0 / this.f4756a.bytesPerFrame;
            }
            this.endBufferSize = 0;
        }
    }

    @Override // androidx.media3.common.audio.BaseAudioProcessor
    protected void onReset() {
        this.endBuffer = Util.EMPTY_BYTE_ARRAY;
    }

    @Override // androidx.media3.common.audio.AudioProcessor
    public void queueInput(ByteBuffer byteBuffer) {
        int iPosition = byteBuffer.position();
        int iLimit = byteBuffer.limit();
        int i2 = iLimit - iPosition;
        if (i2 == 0) {
            return;
        }
        int iMin = Math.min(i2, this.pendingTrimStartBytes);
        this.trimmedFrameCount += iMin / this.f4756a.bytesPerFrame;
        this.pendingTrimStartBytes -= iMin;
        byteBuffer.position(iPosition + iMin);
        if (this.pendingTrimStartBytes > 0) {
            return;
        }
        int i3 = i2 - iMin;
        int length = (this.endBufferSize + i3) - this.endBuffer.length;
        ByteBuffer byteBufferB = b(length);
        int iConstrainValue = Util.constrainValue(length, 0, this.endBufferSize);
        byteBufferB.put(this.endBuffer, 0, iConstrainValue);
        int iConstrainValue2 = Util.constrainValue(length - iConstrainValue, 0, i3);
        byteBuffer.limit(byteBuffer.position() + iConstrainValue2);
        byteBufferB.put(byteBuffer);
        byteBuffer.limit(iLimit);
        int i4 = i3 - iConstrainValue2;
        int i5 = this.endBufferSize - iConstrainValue;
        this.endBufferSize = i5;
        byte[] bArr = this.endBuffer;
        System.arraycopy(bArr, iConstrainValue, bArr, 0, i5);
        byteBuffer.get(this.endBuffer, this.endBufferSize, i4);
        this.endBufferSize += i4;
        byteBufferB.flip();
    }

    public void resetTrimmedFrameCount() {
        this.trimmedFrameCount = 0L;
    }

    public void setTrimFrameCount(int i2, int i3) {
        this.trimStartFrames = i2;
        this.trimEndFrames = i3;
    }
}
