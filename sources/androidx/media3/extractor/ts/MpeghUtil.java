package androidx.media3.extractor.ts;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.media3.common.ParserException;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.ParsableBitArray;
import androidx.media3.extractor.AacUtil;
import androidx.media3.extractor.OpusUtil;
import com.google.common.math.IntMath;
import com.google.common.math.LongMath;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* loaded from: classes2.dex */
final class MpeghUtil {
    private static final int MHAS_SYNC_WORD = 12583333;

    public static class MhasPacketHeader {
        public static final int PACTYPE_EARCON = 19;
        public static final int PACTYPE_PCMCONFIG = 20;
        public static final int PACTYPE_PCMDATA = 21;
        public static final int PACTYP_AUDIOSCENEINFO = 3;
        public static final int PACTYP_AUDIOTRUNCATION = 17;
        public static final int PACTYP_BUFFERINFO = 14;
        public static final int PACTYP_CRC16 = 9;
        public static final int PACTYP_CRC32 = 10;
        public static final int PACTYP_DESCRIPTOR = 11;
        public static final int PACTYP_FILLDATA = 0;
        public static final int PACTYP_GENDATA = 18;
        public static final int PACTYP_GLOBAL_CRC16 = 15;
        public static final int PACTYP_GLOBAL_CRC32 = 16;
        public static final int PACTYP_LOUDNESS = 22;
        public static final int PACTYP_LOUDNESS_DRC = 13;
        public static final int PACTYP_MARKER = 8;
        public static final int PACTYP_MPEGH3DACFG = 1;
        public static final int PACTYP_MPEGH3DAFRAME = 2;
        public static final int PACTYP_SYNC = 6;
        public static final int PACTYP_SYNCGAP = 7;
        public static final int PACTYP_USERINTERACTION = 12;
        public long packetLabel;
        public int packetLength;
        public int packetType;

        @Target({ElementType.TYPE_USE})
        @Documented
        @Retention(RetentionPolicy.SOURCE)
        public @interface Type {
        }
    }

    public static class Mpegh3daConfig {

        @Nullable
        public final byte[] compatibleProfileLevelSet;
        public final int profileLevelIndication;
        public final int samplingFrequency;
        public final int standardFrameLength;

        private Mpegh3daConfig(int i2, int i3, int i4, @Nullable byte[] bArr) {
            this.profileLevelIndication = i2;
            this.samplingFrequency = i3;
            this.standardFrameLength = i4;
            this.compatibleProfileLevelSet = bArr;
        }
    }

    private MpeghUtil() {
    }

    private static int getOutputFrameLength(int i2) throws ParserException {
        if (i2 == 0) {
            return 768;
        }
        if (i2 == 1) {
            return 1024;
        }
        if (i2 == 2 || i2 == 3) {
            return 2048;
        }
        if (i2 == 4) {
            return 4096;
        }
        throw ParserException.createForUnsupportedContainerFeature("Unsupported coreSbrFrameLengthIndex " + i2);
    }

    private static double getResamplingRatio(int i2) throws ParserException {
        switch (i2) {
            case 14700:
            case AacUtil.AAC_HE_V1_MAX_RATE_BYTES_PER_SECOND /* 16000 */:
                return 3.0d;
            case 22050:
            case 24000:
                return 2.0d;
            case 29400:
            case 32000:
            case 58800:
            case 64000:
                return 1.5d;
            case 44100:
            case OpusUtil.SAMPLE_RATE /* 48000 */:
            case 88200:
            case 96000:
                return 1.0d;
            default:
                throw ParserException.createForUnsupportedContainerFeature("Unsupported sampling rate " + i2);
        }
    }

    private static int getSamplingFrequency(int i2) throws ParserException {
        switch (i2) {
            case 0:
                return 96000;
            case 1:
                return 88200;
            case 2:
                return 64000;
            case 3:
                return OpusUtil.SAMPLE_RATE;
            case 4:
                return 44100;
            case 5:
                return 32000;
            case 6:
                return 24000;
            case 7:
                return 22050;
            case 8:
                return AacUtil.AAC_HE_V1_MAX_RATE_BYTES_PER_SECOND;
            case 9:
                return 12000;
            case 10:
                return 11025;
            case 11:
                return 8000;
            case 12:
                return 7350;
            case 13:
            case 14:
            default:
                throw ParserException.createForUnsupportedContainerFeature("Unsupported sampling rate index " + i2);
            case 15:
                return 57600;
            case 16:
                return 51200;
            case 17:
                return 40000;
            case 18:
                return 38400;
            case 19:
                return 34150;
            case 20:
                return 28800;
            case 21:
                return 25600;
            case 22:
                return 20000;
            case 23:
                return 19200;
            case 24:
                return 17075;
            case 25:
                return 14400;
            case 26:
                return 12800;
            case 27:
                return 9600;
        }
    }

    private static int getSbrRatioIndex(int i2) throws ParserException {
        if (i2 == 0 || i2 == 1) {
            return 0;
        }
        int i3 = 2;
        if (i2 != 2) {
            i3 = 3;
            if (i2 != 3) {
                if (i2 == 4) {
                    return 1;
                }
                throw ParserException.createForUnsupportedContainerFeature("Unsupported coreSbrFrameLengthIndex " + i2);
            }
        }
        return i3;
    }

    public static boolean isSyncWord(int i2) {
        return (i2 & ViewCompat.MEASURED_SIZE_MASK) == MHAS_SYNC_WORD;
    }

    public static int parseAudioTruncationInfo(ParsableBitArray parsableBitArray) {
        if (!parsableBitArray.readBit()) {
            return 0;
        }
        parsableBitArray.skipBits(2);
        return parsableBitArray.readBits(13);
    }

    public static boolean parseMhasPacketHeader(ParsableBitArray parsableBitArray, MhasPacketHeader mhasPacketHeader) throws ParserException {
        parsableBitArray.getBytePosition();
        int escapedIntValue = readEscapedIntValue(parsableBitArray, 3, 8, 8);
        mhasPacketHeader.packetType = escapedIntValue;
        if (escapedIntValue == -1) {
            return false;
        }
        long escapedLongValue = readEscapedLongValue(parsableBitArray, 2, 8, 32);
        mhasPacketHeader.packetLabel = escapedLongValue;
        if (escapedLongValue == -1) {
            return false;
        }
        if (escapedLongValue > 16) {
            throw ParserException.createForUnsupportedContainerFeature("Contains sub-stream with an invalid packet label " + mhasPacketHeader.packetLabel);
        }
        if (escapedLongValue == 0) {
            int i2 = mhasPacketHeader.packetType;
            if (i2 == 1) {
                throw ParserException.createForMalformedContainer("Mpegh3daConfig packet with invalid packet label 0", null);
            }
            if (i2 == 2) {
                throw ParserException.createForMalformedContainer("Mpegh3daFrame packet with invalid packet label 0", null);
            }
            if (i2 == 17) {
                throw ParserException.createForMalformedContainer("AudioTruncation packet with invalid packet label 0", null);
            }
        }
        int escapedIntValue2 = readEscapedIntValue(parsableBitArray, 11, 24, 24);
        mhasPacketHeader.packetLength = escapedIntValue2;
        return escapedIntValue2 != -1;
    }

    public static Mpegh3daConfig parseMpegh3daConfig(ParsableBitArray parsableBitArray) throws ParserException {
        int bits = parsableBitArray.readBits(8);
        int bits2 = parsableBitArray.readBits(5);
        int bits3 = bits2 == 31 ? parsableBitArray.readBits(24) : getSamplingFrequency(bits2);
        int bits4 = parsableBitArray.readBits(3);
        int outputFrameLength = getOutputFrameLength(bits4);
        int sbrRatioIndex = getSbrRatioIndex(bits4);
        parsableBitArray.skipBits(2);
        skipSpeakerConfig3d(parsableBitArray);
        skipMpegh3daDecoderConfig(parsableBitArray, parseSignals3d(parsableBitArray), sbrRatioIndex);
        byte[] bArr = null;
        if (parsableBitArray.readBit()) {
            int escapedIntValue = readEscapedIntValue(parsableBitArray, 2, 4, 8) + 1;
            for (int i2 = 0; i2 < escapedIntValue; i2++) {
                int escapedIntValue2 = readEscapedIntValue(parsableBitArray, 4, 8, 16);
                int escapedIntValue3 = readEscapedIntValue(parsableBitArray, 4, 8, 16);
                if (escapedIntValue2 == 7) {
                    int bits5 = parsableBitArray.readBits(4) + 1;
                    parsableBitArray.skipBits(4);
                    byte[] bArr2 = new byte[bits5];
                    for (int i3 = 0; i3 < bits5; i3++) {
                        bArr2[i3] = (byte) parsableBitArray.readBits(8);
                    }
                    bArr = bArr2;
                } else {
                    parsableBitArray.skipBits(escapedIntValue3 * 8);
                }
            }
        }
        byte[] bArr3 = bArr;
        double resamplingRatio = getResamplingRatio(bits3);
        return new Mpegh3daConfig(bits, (int) (bits3 * resamplingRatio), (int) (outputFrameLength * resamplingRatio), bArr3);
    }

    private static boolean parseMpegh3daCoreConfig(ParsableBitArray parsableBitArray) {
        parsableBitArray.skipBits(3);
        boolean bit = parsableBitArray.readBit();
        if (bit) {
            parsableBitArray.skipBits(13);
        }
        return bit;
    }

    private static int parseSignals3d(ParsableBitArray parsableBitArray) {
        int bits = parsableBitArray.readBits(5);
        int escapedIntValue = 0;
        for (int i2 = 0; i2 < bits + 1; i2++) {
            int bits2 = parsableBitArray.readBits(3);
            escapedIntValue += readEscapedIntValue(parsableBitArray, 5, 8, 16) + 1;
            if ((bits2 == 0 || bits2 == 2) && parsableBitArray.readBit()) {
                skipSpeakerConfig3d(parsableBitArray);
            }
        }
        return escapedIntValue;
    }

    private static int readEscapedIntValue(ParsableBitArray parsableBitArray, int i2, int i3, int i4) {
        Assertions.checkArgument(Math.max(Math.max(i2, i3), i4) <= 31);
        int i5 = (1 << i2) - 1;
        int i6 = (1 << i3) - 1;
        IntMath.checkedAdd(IntMath.checkedAdd(i5, i6), 1 << i4);
        if (parsableBitArray.bitsLeft() < i2) {
            return -1;
        }
        int bits = parsableBitArray.readBits(i2);
        if (bits != i5) {
            return bits;
        }
        if (parsableBitArray.bitsLeft() < i3) {
            return -1;
        }
        int bits2 = parsableBitArray.readBits(i3);
        int i7 = bits + bits2;
        if (bits2 != i6) {
            return i7;
        }
        if (parsableBitArray.bitsLeft() < i4) {
            return -1;
        }
        return i7 + parsableBitArray.readBits(i4);
    }

    private static long readEscapedLongValue(ParsableBitArray parsableBitArray, int i2, int i3, int i4) {
        Assertions.checkArgument(Math.max(Math.max(i2, i3), i4) <= 63);
        long j2 = (1 << i2) - 1;
        long j3 = (1 << i3) - 1;
        LongMath.checkedAdd(LongMath.checkedAdd(j2, j3), 1 << i4);
        if (parsableBitArray.bitsLeft() < i2) {
            return -1L;
        }
        long bitsToLong = parsableBitArray.readBitsToLong(i2);
        if (bitsToLong != j2) {
            return bitsToLong;
        }
        if (parsableBitArray.bitsLeft() < i3) {
            return -1L;
        }
        long bitsToLong2 = parsableBitArray.readBitsToLong(i3);
        long j4 = bitsToLong + bitsToLong2;
        if (bitsToLong2 != j3) {
            return j4;
        }
        if (parsableBitArray.bitsLeft() < i4) {
            return -1L;
        }
        return j4 + parsableBitArray.readBitsToLong(i4);
    }

    private static void skipMpegh3daDecoderConfig(ParsableBitArray parsableBitArray, int i2, int i3) {
        int bits;
        int escapedIntValue = readEscapedIntValue(parsableBitArray, 4, 8, 16) + 1;
        parsableBitArray.skipBit();
        for (int i4 = 0; i4 < escapedIntValue; i4++) {
            int bits2 = parsableBitArray.readBits(2);
            if (bits2 == 0) {
                parseMpegh3daCoreConfig(parsableBitArray);
                if (i3 > 0) {
                    skipSbrConfig(parsableBitArray);
                }
            } else if (bits2 == 1) {
                if (parseMpegh3daCoreConfig(parsableBitArray)) {
                    parsableBitArray.skipBit();
                }
                if (i3 > 0) {
                    skipSbrConfig(parsableBitArray);
                    bits = parsableBitArray.readBits(2);
                } else {
                    bits = 0;
                }
                if (bits > 0) {
                    parsableBitArray.skipBits(6);
                    int bits3 = parsableBitArray.readBits(2);
                    parsableBitArray.skipBits(4);
                    if (parsableBitArray.readBit()) {
                        parsableBitArray.skipBits(5);
                    }
                    if (bits == 2 || bits == 3) {
                        parsableBitArray.skipBits(6);
                    }
                    if (bits3 == 2) {
                        parsableBitArray.skipBit();
                    }
                }
                int iFloor = ((int) Math.floor(Math.log(i2 - 1) / Math.log(2.0d))) + 1;
                int bits4 = parsableBitArray.readBits(2);
                if (bits4 > 0 && parsableBitArray.readBit()) {
                    parsableBitArray.skipBits(iFloor);
                }
                if (parsableBitArray.readBit()) {
                    parsableBitArray.skipBits(iFloor);
                }
                if (i3 == 0 && bits4 == 0) {
                    parsableBitArray.skipBit();
                }
            } else if (bits2 == 3) {
                readEscapedIntValue(parsableBitArray, 4, 8, 16);
                int escapedIntValue2 = readEscapedIntValue(parsableBitArray, 4, 8, 16);
                if (parsableBitArray.readBit()) {
                    readEscapedIntValue(parsableBitArray, 8, 16, 0);
                }
                parsableBitArray.skipBit();
                if (escapedIntValue2 > 0) {
                    parsableBitArray.skipBits(escapedIntValue2 * 8);
                }
            }
        }
    }

    private static void skipMpegh3daFlexibleSpeakerConfig(ParsableBitArray parsableBitArray, int i2) {
        int bits;
        boolean bit = parsableBitArray.readBit();
        int i3 = bit ? 1 : 5;
        int i4 = bit ? 7 : 5;
        int i5 = bit ? 8 : 6;
        int i6 = 0;
        while (i6 < i2) {
            if (parsableBitArray.readBit()) {
                parsableBitArray.skipBits(7);
                bits = 0;
            } else {
                if (parsableBitArray.readBits(2) == 3 && parsableBitArray.readBits(i4) * i3 != 0) {
                    parsableBitArray.skipBit();
                }
                bits = parsableBitArray.readBits(i5) * i3;
                if (bits != 0 && bits != 180) {
                    parsableBitArray.skipBit();
                }
                parsableBitArray.skipBit();
            }
            if (bits != 0 && bits != 180 && parsableBitArray.readBit()) {
                i6++;
            }
            i6++;
        }
    }

    private static void skipSbrConfig(ParsableBitArray parsableBitArray) {
        parsableBitArray.skipBits(3);
        parsableBitArray.skipBits(8);
        boolean bit = parsableBitArray.readBit();
        boolean bit2 = parsableBitArray.readBit();
        if (bit) {
            parsableBitArray.skipBits(5);
        }
        if (bit2) {
            parsableBitArray.skipBits(6);
        }
    }

    private static void skipSpeakerConfig3d(ParsableBitArray parsableBitArray) {
        int bits = parsableBitArray.readBits(2);
        if (bits == 0) {
            parsableBitArray.skipBits(6);
            return;
        }
        int escapedIntValue = readEscapedIntValue(parsableBitArray, 5, 8, 16) + 1;
        if (bits == 1) {
            parsableBitArray.skipBits(escapedIntValue * 7);
        } else if (bits == 2) {
            skipMpegh3daFlexibleSpeakerConfig(parsableBitArray, escapedIntValue);
        }
    }
}
