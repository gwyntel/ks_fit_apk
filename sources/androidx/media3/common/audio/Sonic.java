package androidx.media3.common.audio;

import androidx.media3.common.util.Assertions;
import java.nio.ShortBuffer;
import java.util.Arrays;

/* loaded from: classes.dex */
final class Sonic {
    private static final int AMDF_FREQUENCY = 4000;
    private static final int BYTES_PER_SAMPLE = 2;
    private static final int MAXIMUM_PITCH = 400;
    private static final int MINIMUM_PITCH = 65;
    private final int channelCount;
    private final short[] downSampleBuffer;
    private short[] inputBuffer;
    private int inputFrameCount;
    private final int inputSampleRateHz;
    private int maxDiff;
    private final int maxPeriod;
    private final int maxRequiredFrameCount;
    private int minDiff;
    private final int minPeriod;
    private int newRatePosition;
    private int oldRatePosition;
    private short[] outputBuffer;
    private int outputFrameCount;
    private final float pitch;
    private short[] pitchBuffer;
    private int pitchFrameCount;
    private int prevMinDiff;
    private int prevPeriod;
    private final float rate;
    private int remainingInputToCopyFrameCount;
    private final float speed;

    public Sonic(int i2, int i3, float f2, float f3, int i4) {
        this.inputSampleRateHz = i2;
        this.channelCount = i3;
        this.speed = f2;
        this.pitch = f3;
        this.rate = i2 / i4;
        this.minPeriod = i2 / 400;
        int i5 = i2 / 65;
        this.maxPeriod = i5;
        int i6 = i5 * 2;
        this.maxRequiredFrameCount = i6;
        this.downSampleBuffer = new short[i6];
        this.inputBuffer = new short[i6 * i3];
        this.outputBuffer = new short[i6 * i3];
        this.pitchBuffer = new short[i6 * i3];
    }

    private void adjustRate(float f2, int i2) {
        int i3;
        int i4;
        if (this.outputFrameCount == i2) {
            return;
        }
        int i5 = this.inputSampleRateHz;
        int i6 = (int) (i5 / f2);
        while (true) {
            if (i6 <= 16384 && i5 <= 16384) {
                break;
            }
            i6 /= 2;
            i5 /= 2;
        }
        moveNewSamplesToPitchBuffer(i2);
        int i7 = 0;
        while (true) {
            int i8 = this.pitchFrameCount;
            if (i7 >= i8 - 1) {
                removePitchFrames(i8 - 1);
                return;
            }
            while (true) {
                i3 = this.oldRatePosition;
                int i9 = (i3 + 1) * i6;
                i4 = this.newRatePosition;
                if (i9 <= i4 * i5) {
                    break;
                }
                this.outputBuffer = ensureSpaceForAdditionalFrames(this.outputBuffer, this.outputFrameCount, 1);
                int i10 = 0;
                while (true) {
                    int i11 = this.channelCount;
                    if (i10 < i11) {
                        this.outputBuffer[(this.outputFrameCount * i11) + i10] = interpolate(this.pitchBuffer, (i11 * i7) + i10, i5, i6);
                        i10++;
                    }
                }
                this.newRatePosition++;
                this.outputFrameCount++;
            }
            int i12 = i3 + 1;
            this.oldRatePosition = i12;
            if (i12 == i5) {
                this.oldRatePosition = 0;
                Assertions.checkState(i4 == i6);
                this.newRatePosition = 0;
            }
            i7++;
        }
    }

    private void changeSpeed(float f2) {
        int iSkipPitchPeriod;
        int i2 = this.inputFrameCount;
        if (i2 < this.maxRequiredFrameCount) {
            return;
        }
        int i3 = 0;
        do {
            if (this.remainingInputToCopyFrameCount > 0) {
                iSkipPitchPeriod = copyInputToOutput(i3);
            } else {
                int iFindPitchPeriod = findPitchPeriod(this.inputBuffer, i3);
                iSkipPitchPeriod = ((double) f2) > 1.0d ? iFindPitchPeriod + skipPitchPeriod(this.inputBuffer, i3, f2, iFindPitchPeriod) : insertPitchPeriod(this.inputBuffer, i3, f2, iFindPitchPeriod);
            }
            i3 += iSkipPitchPeriod;
        } while (this.maxRequiredFrameCount + i3 <= i2);
        removeProcessedInputFrames(i3);
    }

    private int copyInputToOutput(int i2) {
        int iMin = Math.min(this.maxRequiredFrameCount, this.remainingInputToCopyFrameCount);
        copyToOutput(this.inputBuffer, i2, iMin);
        this.remainingInputToCopyFrameCount -= iMin;
        return iMin;
    }

    private void copyToOutput(short[] sArr, int i2, int i3) {
        short[] sArrEnsureSpaceForAdditionalFrames = ensureSpaceForAdditionalFrames(this.outputBuffer, this.outputFrameCount, i3);
        this.outputBuffer = sArrEnsureSpaceForAdditionalFrames;
        int i4 = this.channelCount;
        System.arraycopy(sArr, i2 * i4, sArrEnsureSpaceForAdditionalFrames, this.outputFrameCount * i4, i4 * i3);
        this.outputFrameCount += i3;
    }

    private void downSampleInput(short[] sArr, int i2, int i3) {
        int i4 = this.maxRequiredFrameCount / i3;
        int i5 = this.channelCount;
        int i6 = i3 * i5;
        int i7 = i2 * i5;
        for (int i8 = 0; i8 < i4; i8++) {
            int i9 = 0;
            for (int i10 = 0; i10 < i6; i10++) {
                i9 += sArr[(i8 * i6) + i7 + i10];
            }
            this.downSampleBuffer[i8] = (short) (i9 / i6);
        }
    }

    private short[] ensureSpaceForAdditionalFrames(short[] sArr, int i2, int i3) {
        int length = sArr.length;
        int i4 = this.channelCount;
        int i5 = length / i4;
        return i2 + i3 <= i5 ? sArr : Arrays.copyOf(sArr, (((i5 * 3) / 2) + i3) * i4);
    }

    private int findPitchPeriod(short[] sArr, int i2) {
        int iFindPitchPeriodInRange;
        int i3 = this.inputSampleRateHz;
        int i4 = i3 > 4000 ? i3 / 4000 : 1;
        if (this.channelCount == 1 && i4 == 1) {
            iFindPitchPeriodInRange = findPitchPeriodInRange(sArr, i2, this.minPeriod, this.maxPeriod);
        } else {
            downSampleInput(sArr, i2, i4);
            int iFindPitchPeriodInRange2 = findPitchPeriodInRange(this.downSampleBuffer, 0, this.minPeriod / i4, this.maxPeriod / i4);
            if (i4 != 1) {
                int i5 = iFindPitchPeriodInRange2 * i4;
                int i6 = i4 * 4;
                int i7 = i5 - i6;
                int i8 = i5 + i6;
                int i9 = this.minPeriod;
                if (i7 < i9) {
                    i7 = i9;
                }
                int i10 = this.maxPeriod;
                if (i8 > i10) {
                    i8 = i10;
                }
                if (this.channelCount == 1) {
                    iFindPitchPeriodInRange = findPitchPeriodInRange(sArr, i2, i7, i8);
                } else {
                    downSampleInput(sArr, i2, 1);
                    iFindPitchPeriodInRange = findPitchPeriodInRange(this.downSampleBuffer, 0, i7, i8);
                }
            } else {
                iFindPitchPeriodInRange = iFindPitchPeriodInRange2;
            }
        }
        int i11 = previousPeriodBetter(this.minDiff, this.maxDiff) ? this.prevPeriod : iFindPitchPeriodInRange;
        this.prevMinDiff = this.minDiff;
        this.prevPeriod = iFindPitchPeriodInRange;
        return i11;
    }

    private int findPitchPeriodInRange(short[] sArr, int i2, int i3, int i4) {
        int i5 = i2 * this.channelCount;
        int i6 = 255;
        int i7 = 1;
        int i8 = 0;
        int i9 = 0;
        while (i3 <= i4) {
            int iAbs = 0;
            for (int i10 = 0; i10 < i3; i10++) {
                iAbs += Math.abs(sArr[i5 + i10] - sArr[(i5 + i3) + i10]);
            }
            if (iAbs * i8 < i7 * i3) {
                i8 = i3;
                i7 = iAbs;
            }
            if (iAbs * i6 > i9 * i3) {
                i6 = i3;
                i9 = iAbs;
            }
            i3++;
        }
        this.minDiff = i7 / i8;
        this.maxDiff = i9 / i6;
        return i8;
    }

    private int insertPitchPeriod(short[] sArr, int i2, float f2, int i3) {
        int i4;
        if (f2 < 0.5f) {
            i4 = (int) ((i3 * f2) / (1.0f - f2));
        } else {
            this.remainingInputToCopyFrameCount = (int) ((i3 * ((2.0f * f2) - 1.0f)) / (1.0f - f2));
            i4 = i3;
        }
        int i5 = i3 + i4;
        short[] sArrEnsureSpaceForAdditionalFrames = ensureSpaceForAdditionalFrames(this.outputBuffer, this.outputFrameCount, i5);
        this.outputBuffer = sArrEnsureSpaceForAdditionalFrames;
        int i6 = this.channelCount;
        System.arraycopy(sArr, i2 * i6, sArrEnsureSpaceForAdditionalFrames, this.outputFrameCount * i6, i6 * i3);
        overlapAdd(i4, this.channelCount, this.outputBuffer, this.outputFrameCount + i3, sArr, i2 + i3, sArr, i2);
        this.outputFrameCount += i5;
        return i4;
    }

    private short interpolate(short[] sArr, int i2, int i3, int i4) {
        short s2 = sArr[i2];
        short s3 = sArr[i2 + this.channelCount];
        int i5 = this.newRatePosition * i3;
        int i6 = this.oldRatePosition;
        int i7 = i6 * i4;
        int i8 = (i6 + 1) * i4;
        int i9 = i8 - i5;
        int i10 = i8 - i7;
        return (short) (((s2 * i9) + ((i10 - i9) * s3)) / i10);
    }

    private void moveNewSamplesToPitchBuffer(int i2) {
        int i3 = this.outputFrameCount - i2;
        short[] sArrEnsureSpaceForAdditionalFrames = ensureSpaceForAdditionalFrames(this.pitchBuffer, this.pitchFrameCount, i3);
        this.pitchBuffer = sArrEnsureSpaceForAdditionalFrames;
        short[] sArr = this.outputBuffer;
        int i4 = this.channelCount;
        System.arraycopy(sArr, i2 * i4, sArrEnsureSpaceForAdditionalFrames, this.pitchFrameCount * i4, i4 * i3);
        this.outputFrameCount = i2;
        this.pitchFrameCount += i3;
    }

    private static void overlapAdd(int i2, int i3, short[] sArr, int i4, short[] sArr2, int i5, short[] sArr3, int i6) {
        for (int i7 = 0; i7 < i3; i7++) {
            int i8 = (i4 * i3) + i7;
            int i9 = (i6 * i3) + i7;
            int i10 = (i5 * i3) + i7;
            for (int i11 = 0; i11 < i2; i11++) {
                sArr[i8] = (short) (((sArr2[i10] * (i2 - i11)) + (sArr3[i9] * i11)) / i2);
                i8 += i3;
                i10 += i3;
                i9 += i3;
            }
        }
    }

    private boolean previousPeriodBetter(int i2, int i3) {
        return i2 != 0 && this.prevPeriod != 0 && i3 <= i2 * 3 && i2 * 2 > this.prevMinDiff * 3;
    }

    private void processStreamInput() {
        int i2 = this.outputFrameCount;
        float f2 = this.speed;
        float f3 = this.pitch;
        float f4 = f2 / f3;
        float f5 = this.rate * f3;
        double d2 = f4;
        if (d2 > 1.00001d || d2 < 0.99999d) {
            changeSpeed(f4);
        } else {
            copyToOutput(this.inputBuffer, 0, this.inputFrameCount);
            this.inputFrameCount = 0;
        }
        if (f5 != 1.0f) {
            adjustRate(f5, i2);
        }
    }

    private void removePitchFrames(int i2) {
        if (i2 == 0) {
            return;
        }
        short[] sArr = this.pitchBuffer;
        int i3 = this.channelCount;
        System.arraycopy(sArr, i2 * i3, sArr, 0, (this.pitchFrameCount - i2) * i3);
        this.pitchFrameCount -= i2;
    }

    private void removeProcessedInputFrames(int i2) {
        int i3 = this.inputFrameCount - i2;
        short[] sArr = this.inputBuffer;
        int i4 = this.channelCount;
        System.arraycopy(sArr, i2 * i4, sArr, 0, i4 * i3);
        this.inputFrameCount = i3;
    }

    private int skipPitchPeriod(short[] sArr, int i2, float f2, int i3) {
        int i4;
        if (f2 >= 2.0f) {
            i4 = (int) (i3 / (f2 - 1.0f));
        } else {
            this.remainingInputToCopyFrameCount = (int) ((i3 * (2.0f - f2)) / (f2 - 1.0f));
            i4 = i3;
        }
        short[] sArrEnsureSpaceForAdditionalFrames = ensureSpaceForAdditionalFrames(this.outputBuffer, this.outputFrameCount, i4);
        this.outputBuffer = sArrEnsureSpaceForAdditionalFrames;
        overlapAdd(i4, this.channelCount, sArrEnsureSpaceForAdditionalFrames, this.outputFrameCount, sArr, i2, sArr, i2 + i3);
        this.outputFrameCount += i4;
        return i4;
    }

    public void flush() {
        this.inputFrameCount = 0;
        this.outputFrameCount = 0;
        this.pitchFrameCount = 0;
        this.oldRatePosition = 0;
        this.newRatePosition = 0;
        this.remainingInputToCopyFrameCount = 0;
        this.prevPeriod = 0;
        this.prevMinDiff = 0;
        this.minDiff = 0;
        this.maxDiff = 0;
    }

    public void getOutput(ShortBuffer shortBuffer) {
        int iMin = Math.min(shortBuffer.remaining() / this.channelCount, this.outputFrameCount);
        shortBuffer.put(this.outputBuffer, 0, this.channelCount * iMin);
        int i2 = this.outputFrameCount - iMin;
        this.outputFrameCount = i2;
        short[] sArr = this.outputBuffer;
        int i3 = this.channelCount;
        System.arraycopy(sArr, iMin * i3, sArr, 0, i2 * i3);
    }

    public int getOutputSize() {
        return this.outputFrameCount * this.channelCount * 2;
    }

    public int getPendingInputBytes() {
        return this.inputFrameCount * this.channelCount * 2;
    }

    public void queueEndOfStream() {
        int i2;
        int i3 = this.inputFrameCount;
        float f2 = this.speed;
        float f3 = this.pitch;
        int i4 = this.outputFrameCount + ((int) ((((i3 / (f2 / f3)) + this.pitchFrameCount) / (this.rate * f3)) + 0.5f));
        this.inputBuffer = ensureSpaceForAdditionalFrames(this.inputBuffer, i3, (this.maxRequiredFrameCount * 2) + i3);
        int i5 = 0;
        while (true) {
            i2 = this.maxRequiredFrameCount;
            int i6 = this.channelCount;
            if (i5 >= i2 * 2 * i6) {
                break;
            }
            this.inputBuffer[(i6 * i3) + i5] = 0;
            i5++;
        }
        this.inputFrameCount += i2 * 2;
        processStreamInput();
        if (this.outputFrameCount > i4) {
            this.outputFrameCount = i4;
        }
        this.inputFrameCount = 0;
        this.remainingInputToCopyFrameCount = 0;
        this.pitchFrameCount = 0;
    }

    public void queueInput(ShortBuffer shortBuffer) {
        int iRemaining = shortBuffer.remaining();
        int i2 = this.channelCount;
        int i3 = iRemaining / i2;
        short[] sArrEnsureSpaceForAdditionalFrames = ensureSpaceForAdditionalFrames(this.inputBuffer, this.inputFrameCount, i3);
        this.inputBuffer = sArrEnsureSpaceForAdditionalFrames;
        shortBuffer.get(sArrEnsureSpaceForAdditionalFrames, this.inputFrameCount * this.channelCount, ((i2 * i3) * 2) / 2);
        this.inputFrameCount += i3;
        processStreamInput();
    }
}
