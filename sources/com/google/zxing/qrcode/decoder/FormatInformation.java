package com.google.zxing.qrcode.decoder;

/* loaded from: classes3.dex */
final class FormatInformation {
    private final byte dataMask;
    private final ErrorCorrectionLevel errorCorrectionLevel;
    private static final int FORMAT_INFO_MASK_QR = 21522;
    private static final int[][] FORMAT_INFO_DECODE_LOOKUP = {new int[]{FORMAT_INFO_MASK_QR, 0}, new int[]{20773, 1}, new int[]{24188, 2}, new int[]{23371, 3}, new int[]{17913, 4}, new int[]{16590, 5}, new int[]{20375, 6}, new int[]{19104, 7}, new int[]{30660, 8}, new int[]{29427, 9}, new int[]{32170, 10}, new int[]{30877, 11}, new int[]{26159, 12}, new int[]{25368, 13}, new int[]{27713, 14}, new int[]{26998, 15}, new int[]{5769, 16}, new int[]{5054, 17}, new int[]{7399, 18}, new int[]{6608, 19}, new int[]{1890, 20}, new int[]{597, 21}, new int[]{3340, 22}, new int[]{2107, 23}, new int[]{13663, 24}, new int[]{12392, 25}, new int[]{16177, 26}, new int[]{14854, 27}, new int[]{9396, 28}, new int[]{8579, 29}, new int[]{11994, 30}, new int[]{11245, 31}};

    private FormatInformation(int i2) {
        this.errorCorrectionLevel = ErrorCorrectionLevel.forBits((i2 >> 3) & 3);
        this.dataMask = (byte) (i2 & 7);
    }

    static FormatInformation a(int i2, int i3) {
        FormatInformation formatInformationDoDecodeFormatInformation = doDecodeFormatInformation(i2, i3);
        return formatInformationDoDecodeFormatInformation != null ? formatInformationDoDecodeFormatInformation : doDecodeFormatInformation(i2 ^ FORMAT_INFO_MASK_QR, i3 ^ FORMAT_INFO_MASK_QR);
    }

    static int d(int i2, int i3) {
        return Integer.bitCount(i2 ^ i3);
    }

    private static FormatInformation doDecodeFormatInformation(int i2, int i3) {
        int iD;
        int i4 = Integer.MAX_VALUE;
        int i5 = 0;
        for (int[] iArr : FORMAT_INFO_DECODE_LOOKUP) {
            int i6 = iArr[0];
            if (i6 == i2 || i6 == i3) {
                return new FormatInformation(iArr[1]);
            }
            int iD2 = d(i2, i6);
            if (iD2 < i4) {
                i5 = iArr[1];
                i4 = iD2;
            }
            if (i2 != i3 && (iD = d(i3, i6)) < i4) {
                i5 = iArr[1];
                i4 = iD;
            }
        }
        if (i4 <= 3) {
            return new FormatInformation(i5);
        }
        return null;
    }

    byte b() {
        return this.dataMask;
    }

    ErrorCorrectionLevel c() {
        return this.errorCorrectionLevel;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof FormatInformation)) {
            return false;
        }
        FormatInformation formatInformation = (FormatInformation) obj;
        return this.errorCorrectionLevel == formatInformation.errorCorrectionLevel && this.dataMask == formatInformation.dataMask;
    }

    public int hashCode() {
        return (this.errorCorrectionLevel.ordinal() << 3) | this.dataMask;
    }
}
