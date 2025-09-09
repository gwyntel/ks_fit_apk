package androidx.media3.extractor.mp4;

import androidx.annotation.Nullable;
import androidx.media3.common.C;
import androidx.media3.common.Metadata;
import androidx.media3.common.util.ParsableByteArray;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.extractor.metadata.mp4.SmtaMetadataEntry;

@UnstableApi
/* loaded from: classes2.dex */
public final class SmtaAtomUtil {
    private static final int CAMCORDER_FRC_SUPERSLOW_MOTION = 9;
    private static final int CAMCORDER_FRC_SUPERSLOW_MOTION_HEVC = 22;
    private static final int CAMCORDER_NORMAL = 0;
    private static final int CAMCORDER_QFRC_SUPERSLOW_MOTION = 23;
    private static final int CAMCORDER_SINGLE_SUPERSLOW_MOTION = 7;
    private static final int CAMCORDER_SLOW_MOTION_V2 = 12;
    private static final int CAMCORDER_SLOW_MOTION_V2_120 = 13;
    private static final int CAMCORDER_SLOW_MOTION_V2_HEVC = 21;
    private static final int NO_VALUE = -1;

    private SmtaAtomUtil() {
    }

    private static int getCaptureFrameRate(int i2, ParsableByteArray parsableByteArray, int i3) {
        if (i2 == 12) {
            return 240;
        }
        if (i2 == 13) {
            return 120;
        }
        if (i2 == 21 && parsableByteArray.bytesLeft() >= 8 && parsableByteArray.getPosition() + 8 <= i3) {
            int i4 = parsableByteArray.readInt();
            int i5 = parsableByteArray.readInt();
            if (i4 >= 12 && i5 == 1936877170) {
                return parsableByteArray.readUnsignedFixedPoint1616();
            }
        }
        return C.RATE_UNSET_INT;
    }

    @Nullable
    public static Metadata parseSmta(ParsableByteArray parsableByteArray, int i2) {
        parsableByteArray.skipBytes(12);
        while (parsableByteArray.getPosition() < i2) {
            int position = parsableByteArray.getPosition();
            int i3 = parsableByteArray.readInt();
            if (parsableByteArray.readInt() == 1935766900) {
                if (i3 < 16) {
                    return null;
                }
                parsableByteArray.skipBytes(4);
                int i4 = -1;
                int i5 = 0;
                for (int i6 = 0; i6 < 2; i6++) {
                    int unsignedByte = parsableByteArray.readUnsignedByte();
                    int unsignedByte2 = parsableByteArray.readUnsignedByte();
                    if (unsignedByte == 0) {
                        i4 = unsignedByte2;
                    } else if (unsignedByte == 1) {
                        i5 = unsignedByte2;
                    }
                }
                int captureFrameRate = getCaptureFrameRate(i4, parsableByteArray, i2);
                if (captureFrameRate == -2147483647) {
                    return null;
                }
                return new Metadata(new SmtaMetadataEntry(captureFrameRate, i5));
            }
            parsableByteArray.setPosition(position + i3);
        }
        return null;
    }
}
