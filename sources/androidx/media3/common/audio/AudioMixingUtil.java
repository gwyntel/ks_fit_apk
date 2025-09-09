package androidx.media3.common.audio;

import androidx.media3.common.audio.AudioProcessor;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;
import java.nio.ByteBuffer;
import javax.jmdns.impl.constants.DNSRecordClass;

@UnstableApi
/* loaded from: classes.dex */
public final class AudioMixingUtil {
    private static final float FLOAT_PCM_MAX_VALUE = 1.0f;
    private static final float FLOAT_PCM_MIN_VALUE = -1.0f;

    private AudioMixingUtil() {
    }

    public static boolean canMix(AudioProcessor.AudioFormat audioFormat) {
        if (audioFormat.sampleRate == -1 || audioFormat.channelCount == -1) {
            return false;
        }
        int i2 = audioFormat.encoding;
        return i2 == 2 || i2 == 4;
    }

    private static float floatSampleToInt16Pcm(float f2) {
        return Util.constrainValue(f2 * (f2 < 0.0f ? 32768 : DNSRecordClass.CLASS_MASK), -32768.0f, 32767.0f);
    }

    private static float getPcmSample(ByteBuffer byteBuffer, boolean z2, boolean z3) {
        return z3 ? z2 ? byteBuffer.getShort() : floatSampleToInt16Pcm(byteBuffer.getFloat()) : z2 ? int16SampleToFloatPcm(byteBuffer.getShort()) : byteBuffer.getFloat();
    }

    private static float int16SampleToFloatPcm(short s2) {
        return s2 / (s2 < 0 ? 32768 : DNSRecordClass.CLASS_MASK);
    }

    public static ByteBuffer mix(ByteBuffer byteBuffer, AudioProcessor.AudioFormat audioFormat, ByteBuffer byteBuffer2, AudioProcessor.AudioFormat audioFormat2, ChannelMixingMatrix channelMixingMatrix, int i2, boolean z2, boolean z3) {
        AudioProcessor.AudioFormat audioFormat3;
        boolean z4;
        if (audioFormat.encoding == 2) {
            audioFormat3 = audioFormat2;
            z4 = true;
        } else {
            audioFormat3 = audioFormat2;
            z4 = false;
        }
        boolean z5 = audioFormat3.encoding == 2;
        int inputChannelCount = channelMixingMatrix.getInputChannelCount();
        int outputChannelCount = channelMixingMatrix.getOutputChannelCount();
        float[] fArr = new float[inputChannelCount];
        float[] fArr2 = new float[outputChannelCount];
        for (int i3 = 0; i3 < i2; i3++) {
            if (z2) {
                int iPosition = byteBuffer2.position();
                for (int i4 = 0; i4 < outputChannelCount; i4++) {
                    fArr2[i4] = getPcmSample(byteBuffer2, z5, z5);
                }
                byteBuffer2.position(iPosition);
            }
            for (int i5 = 0; i5 < inputChannelCount; i5++) {
                fArr[i5] = getPcmSample(byteBuffer, z4, z5);
            }
            for (int i6 = 0; i6 < outputChannelCount; i6++) {
                for (int i7 = 0; i7 < inputChannelCount; i7++) {
                    fArr2[i6] = fArr2[i6] + (fArr[i7] * channelMixingMatrix.getMixingCoefficient(i7, i6));
                }
                if (z5) {
                    byteBuffer2.putShort((short) Util.constrainValue(fArr2[i6], -32768.0f, 32767.0f));
                } else {
                    byteBuffer2.putFloat(z3 ? Util.constrainValue(fArr2[i6], FLOAT_PCM_MIN_VALUE, 1.0f) : fArr2[i6]);
                }
                fArr2[i6] = 0.0f;
            }
        }
        return byteBuffer2;
    }

    public static boolean canMix(AudioProcessor.AudioFormat audioFormat, AudioProcessor.AudioFormat audioFormat2) {
        return audioFormat.sampleRate == audioFormat2.sampleRate && canMix(audioFormat) && canMix(audioFormat2);
    }
}
