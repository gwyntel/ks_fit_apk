package androidx.media3.exoplayer.audio;

import androidx.media3.common.audio.AudioProcessor;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;
import java.nio.ByteBuffer;

@UnstableApi
/* loaded from: classes.dex */
public final class SilenceSkippingAudioProcessor extends androidx.media3.common.audio.BaseAudioProcessor {
    private static final int AVOID_TRUNCATION_FACTOR = 1000;
    public static final long DEFAULT_MAX_SILENCE_TO_KEEP_DURATION_US = 2000000;
    public static final long DEFAULT_MINIMUM_SILENCE_DURATION_US = 100000;
    public static final int DEFAULT_MIN_VOLUME_TO_KEEP_PERCENTAGE = 10;

    @Deprecated
    public static final long DEFAULT_PADDING_SILENCE_US = 20000;
    public static final float DEFAULT_SILENCE_RETENTION_RATIO = 0.2f;
    public static final short DEFAULT_SILENCE_THRESHOLD_LEVEL = 1024;
    private static final int DO_NOT_CHANGE_VOLUME = 3;
    private static final int FADE_IN = 2;
    private static final int FADE_OUT = 0;
    private static final int MUTE = 1;
    private static final int STATE_NOISY = 0;
    private static final int STATE_SHORTENING_SILENCE = 1;
    private int bytesPerFrame;
    private byte[] contiguousOutputBuffer;
    private boolean enabled;
    private final long maxSilenceToKeepDurationUs;
    private byte[] maybeSilenceBuffer;
    private int maybeSilenceBufferContentsSize;
    private int maybeSilenceBufferStartIndex;
    private final int minVolumeToKeepPercentageWhenMuting;
    private final long minimumSilenceDurationUs;
    private int outputSilenceFramesSinceNoise;
    private final float silenceRetentionRatio;
    private final short silenceThresholdLevel;
    private long skippedFrames;
    private int state;

    public SilenceSkippingAudioProcessor() {
        this(DEFAULT_MINIMUM_SILENCE_DURATION_US, 0.2f, DEFAULT_MAX_SILENCE_TO_KEEP_DURATION_US, 10, (short) 1024);
    }

    private int alignToBytePerFrameBoundary(int i2) {
        int i3 = this.bytesPerFrame;
        return (i2 / i3) * i3;
    }

    private int calculateFadeInPercentage(int i2, int i3) {
        int i4 = this.minVolumeToKeepPercentageWhenMuting;
        return i4 + ((((100 - i4) * (i2 * 1000)) / i3) / 1000);
    }

    private int calculateFadeOutPercentage(int i2, int i3) {
        return (((this.minVolumeToKeepPercentageWhenMuting - 100) * ((i2 * 1000) / i3)) / 1000) + 100;
    }

    private int calculateShortenedSilenceLength(int i2) {
        int iDurationUsToFrames = ((durationUsToFrames(this.maxSilenceToKeepDurationUs) - this.outputSilenceFramesSinceNoise) * this.bytesPerFrame) - (this.maybeSilenceBuffer.length / 2);
        Assertions.checkState(iDurationUsToFrames >= 0);
        return alignToBytePerFrameBoundary(Math.min((i2 * this.silenceRetentionRatio) + 0.5f, iDurationUsToFrames));
    }

    private int durationUsToFrames(long j2) {
        return (int) ((j2 * this.f4756a.sampleRate) / 1000000);
    }

    private int findNoiseLimit(ByteBuffer byteBuffer) {
        for (int iLimit = byteBuffer.limit() - 1; iLimit >= byteBuffer.position(); iLimit -= 2) {
            if (isNoise(byteBuffer.get(iLimit), byteBuffer.get(iLimit - 1))) {
                int i2 = this.bytesPerFrame;
                return ((iLimit / i2) * i2) + i2;
            }
        }
        return byteBuffer.position();
    }

    private int findNoisePosition(ByteBuffer byteBuffer) {
        for (int iPosition = byteBuffer.position() + 1; iPosition < byteBuffer.limit(); iPosition += 2) {
            if (isNoise(byteBuffer.get(iPosition), byteBuffer.get(iPosition - 1))) {
                int i2 = this.bytesPerFrame;
                return i2 * (iPosition / i2);
            }
        }
        return byteBuffer.limit();
    }

    private boolean isNoise(byte b2, byte b3) {
        return Math.abs(twoByteSampleToInt(b2, b3)) > this.silenceThresholdLevel;
    }

    private void modifyVolume(byte[] bArr, int i2, int i3) {
        if (i3 == 3) {
            return;
        }
        for (int i4 = 0; i4 < i2; i4 += 2) {
            sampleIntToTwoBigEndianBytes(bArr, i4, (twoByteSampleToInt(bArr[i4 + 1], bArr[i4]) * (i3 == 0 ? calculateFadeOutPercentage(i4, i2 - 1) : i3 == 2 ? calculateFadeInPercentage(i4, i2 - 1) : this.minVolumeToKeepPercentageWhenMuting)) / 100);
        }
    }

    private void output(ByteBuffer byteBuffer) {
        b(byteBuffer.remaining()).put(byteBuffer).flip();
    }

    private void outputRange(byte[] bArr, int i2, int i3) {
        Assertions.checkArgument(i2 % this.bytesPerFrame == 0, "byteOutput size is not aligned to frame size " + i2);
        modifyVolume(bArr, i2, i3);
        b(i2).put(bArr, 0, i2).flip();
    }

    private void outputShortenedSilenceBuffer(boolean z2) {
        int length;
        int iCalculateShortenedSilenceLength;
        int i2 = this.maybeSilenceBufferContentsSize;
        byte[] bArr = this.maybeSilenceBuffer;
        if (i2 == bArr.length || z2) {
            if (this.outputSilenceFramesSinceNoise == 0) {
                if (z2) {
                    outputSilence(i2, 3);
                    length = i2;
                } else {
                    Assertions.checkState(i2 >= bArr.length / 2);
                    length = this.maybeSilenceBuffer.length / 2;
                    outputSilence(length, 0);
                }
                iCalculateShortenedSilenceLength = length;
            } else if (z2) {
                int length2 = i2 - (bArr.length / 2);
                int length3 = (bArr.length / 2) + length2;
                int iCalculateShortenedSilenceLength2 = calculateShortenedSilenceLength(length2) + (this.maybeSilenceBuffer.length / 2);
                outputSilence(iCalculateShortenedSilenceLength2, 2);
                iCalculateShortenedSilenceLength = iCalculateShortenedSilenceLength2;
                length = length3;
            } else {
                length = i2 - (bArr.length / 2);
                iCalculateShortenedSilenceLength = calculateShortenedSilenceLength(length);
                outputSilence(iCalculateShortenedSilenceLength, 1);
            }
            Assertions.checkState(length % this.bytesPerFrame == 0, "bytesConsumed is not aligned to frame size: %s" + length);
            Assertions.checkState(i2 >= iCalculateShortenedSilenceLength);
            this.maybeSilenceBufferContentsSize -= length;
            int i3 = this.maybeSilenceBufferStartIndex + length;
            this.maybeSilenceBufferStartIndex = i3;
            this.maybeSilenceBufferStartIndex = i3 % this.maybeSilenceBuffer.length;
            this.outputSilenceFramesSinceNoise = this.outputSilenceFramesSinceNoise + (iCalculateShortenedSilenceLength / this.bytesPerFrame);
            this.skippedFrames += (length - iCalculateShortenedSilenceLength) / r2;
        }
    }

    private void outputSilence(int i2, int i3) {
        if (i2 == 0) {
            return;
        }
        Assertions.checkArgument(this.maybeSilenceBufferContentsSize >= i2);
        if (i3 == 2) {
            int i4 = this.maybeSilenceBufferStartIndex;
            int i5 = this.maybeSilenceBufferContentsSize;
            int i6 = i4 + i5;
            byte[] bArr = this.maybeSilenceBuffer;
            if (i6 <= bArr.length) {
                System.arraycopy(bArr, (i4 + i5) - i2, this.contiguousOutputBuffer, 0, i2);
            } else {
                int length = i5 - (bArr.length - i4);
                if (length >= i2) {
                    System.arraycopy(bArr, length - i2, this.contiguousOutputBuffer, 0, i2);
                } else {
                    int i7 = i2 - length;
                    System.arraycopy(bArr, bArr.length - i7, this.contiguousOutputBuffer, 0, i7);
                    System.arraycopy(this.maybeSilenceBuffer, 0, this.contiguousOutputBuffer, i7, length);
                }
            }
        } else {
            int i8 = this.maybeSilenceBufferStartIndex;
            int i9 = i8 + i2;
            byte[] bArr2 = this.maybeSilenceBuffer;
            if (i9 <= bArr2.length) {
                System.arraycopy(bArr2, i8, this.contiguousOutputBuffer, 0, i2);
            } else {
                int length2 = bArr2.length - i8;
                System.arraycopy(bArr2, i8, this.contiguousOutputBuffer, 0, length2);
                System.arraycopy(this.maybeSilenceBuffer, 0, this.contiguousOutputBuffer, length2, i2 - length2);
            }
        }
        Assertions.checkArgument(i2 % this.bytesPerFrame == 0, "sizeToOutput is not aligned to frame size: " + i2);
        Assertions.checkState(this.maybeSilenceBufferStartIndex < this.maybeSilenceBuffer.length);
        outputRange(this.contiguousOutputBuffer, i2, i3);
    }

    private void processNoisy(ByteBuffer byteBuffer) {
        int iLimit = byteBuffer.limit();
        byteBuffer.limit(Math.min(iLimit, byteBuffer.position() + this.maybeSilenceBuffer.length));
        int iFindNoiseLimit = findNoiseLimit(byteBuffer);
        if (iFindNoiseLimit == byteBuffer.position()) {
            this.state = 1;
        } else {
            byteBuffer.limit(Math.min(iFindNoiseLimit, byteBuffer.capacity()));
            output(byteBuffer);
        }
        byteBuffer.limit(iLimit);
    }

    private static void sampleIntToTwoBigEndianBytes(byte[] bArr, int i2, int i3) {
        if (i3 >= 32767) {
            bArr[i2] = -1;
            bArr[i2 + 1] = Byte.MAX_VALUE;
        } else if (i3 <= -32768) {
            bArr[i2] = 0;
            bArr[i2 + 1] = Byte.MIN_VALUE;
        } else {
            bArr[i2] = (byte) (i3 & 255);
            bArr[i2 + 1] = (byte) (i3 >> 8);
        }
    }

    private void shortenSilenceSilenceUntilNoise(ByteBuffer byteBuffer) {
        int length;
        int i2;
        Assertions.checkState(this.maybeSilenceBufferStartIndex < this.maybeSilenceBuffer.length);
        int iLimit = byteBuffer.limit();
        int iFindNoisePosition = findNoisePosition(byteBuffer);
        int iPosition = iFindNoisePosition - byteBuffer.position();
        int i3 = this.maybeSilenceBufferStartIndex;
        int i4 = this.maybeSilenceBufferContentsSize;
        int i5 = i3 + i4;
        byte[] bArr = this.maybeSilenceBuffer;
        if (i5 < bArr.length) {
            length = bArr.length - (i4 + i3);
            i2 = i3 + i4;
        } else {
            int length2 = i4 - (bArr.length - i3);
            length = i3 - length2;
            i2 = length2;
        }
        boolean z2 = iFindNoisePosition < iLimit;
        int iMin = Math.min(iPosition, length);
        byteBuffer.limit(byteBuffer.position() + iMin);
        byteBuffer.get(this.maybeSilenceBuffer, i2, iMin);
        int i6 = this.maybeSilenceBufferContentsSize + iMin;
        this.maybeSilenceBufferContentsSize = i6;
        Assertions.checkState(i6 <= this.maybeSilenceBuffer.length);
        boolean z3 = z2 && iPosition < length;
        outputShortenedSilenceBuffer(z3);
        if (z3) {
            this.state = 0;
            this.outputSilenceFramesSinceNoise = 0;
        }
        byteBuffer.limit(iLimit);
    }

    private static int twoByteSampleToInt(byte b2, byte b3) {
        return (b2 << 8) | (b3 & 255);
    }

    public long getSkippedFrames() {
        return this.skippedFrames;
    }

    @Override // androidx.media3.common.audio.BaseAudioProcessor, androidx.media3.common.audio.AudioProcessor
    public boolean isActive() {
        return super.isActive() && this.enabled;
    }

    @Override // androidx.media3.common.audio.BaseAudioProcessor
    protected AudioProcessor.AudioFormat onConfigure(AudioProcessor.AudioFormat audioFormat) throws AudioProcessor.UnhandledAudioFormatException {
        if (audioFormat.encoding == 2) {
            return audioFormat.sampleRate == -1 ? AudioProcessor.AudioFormat.NOT_SET : audioFormat;
        }
        throw new AudioProcessor.UnhandledAudioFormatException(audioFormat);
    }

    @Override // androidx.media3.common.audio.BaseAudioProcessor
    public void onFlush() {
        if (isActive()) {
            this.bytesPerFrame = this.f4756a.channelCount * 2;
            int iAlignToBytePerFrameBoundary = alignToBytePerFrameBoundary(durationUsToFrames(this.minimumSilenceDurationUs) / 2) * 2;
            if (this.maybeSilenceBuffer.length != iAlignToBytePerFrameBoundary) {
                this.maybeSilenceBuffer = new byte[iAlignToBytePerFrameBoundary];
                this.contiguousOutputBuffer = new byte[iAlignToBytePerFrameBoundary];
            }
        }
        this.state = 0;
        this.skippedFrames = 0L;
        this.outputSilenceFramesSinceNoise = 0;
        this.maybeSilenceBufferStartIndex = 0;
        this.maybeSilenceBufferContentsSize = 0;
    }

    @Override // androidx.media3.common.audio.BaseAudioProcessor
    public void onQueueEndOfStream() {
        if (this.maybeSilenceBufferContentsSize > 0) {
            outputShortenedSilenceBuffer(true);
            this.outputSilenceFramesSinceNoise = 0;
        }
    }

    @Override // androidx.media3.common.audio.BaseAudioProcessor
    public void onReset() {
        this.enabled = false;
        byte[] bArr = Util.EMPTY_BYTE_ARRAY;
        this.maybeSilenceBuffer = bArr;
        this.contiguousOutputBuffer = bArr;
    }

    @Override // androidx.media3.common.audio.AudioProcessor
    public void queueInput(ByteBuffer byteBuffer) {
        while (byteBuffer.hasRemaining() && !a()) {
            int i2 = this.state;
            if (i2 == 0) {
                processNoisy(byteBuffer);
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException();
                }
                shortenSilenceSilenceUntilNoise(byteBuffer);
            }
        }
    }

    public void setEnabled(boolean z2) {
        this.enabled = z2;
    }

    @Deprecated
    public SilenceSkippingAudioProcessor(long j2, long j3, short s2) {
        this(j2, j3 / j2, j2, 0, s2);
    }

    private int alignToBytePerFrameBoundary(float f2) {
        return alignToBytePerFrameBoundary((int) f2);
    }

    public SilenceSkippingAudioProcessor(long j2, float f2, long j3, int i2, short s2) {
        boolean z2 = false;
        this.outputSilenceFramesSinceNoise = 0;
        this.maybeSilenceBufferStartIndex = 0;
        this.maybeSilenceBufferContentsSize = 0;
        if (f2 >= 0.0f && f2 <= 1.0f) {
            z2 = true;
        }
        Assertions.checkArgument(z2);
        this.minimumSilenceDurationUs = j2;
        this.silenceRetentionRatio = f2;
        this.maxSilenceToKeepDurationUs = j3;
        this.minVolumeToKeepPercentageWhenMuting = i2;
        this.silenceThresholdLevel = s2;
        byte[] bArr = Util.EMPTY_BYTE_ARRAY;
        this.maybeSilenceBuffer = bArr;
        this.contiguousOutputBuffer = bArr;
    }
}
