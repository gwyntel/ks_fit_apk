package com.google.zxing.datamatrix.decoder;

import com.google.zxing.FormatException;

/* loaded from: classes3.dex */
public final class Version {
    private static final Version[] VERSIONS = buildVersions();
    private final int dataRegionSizeColumns;
    private final int dataRegionSizeRows;
    private final ECBlocks ecBlocks;
    private final int symbolSizeColumns;
    private final int symbolSizeRows;
    private final int totalCodewords;
    private final int versionNumber;

    static final class ECB {
        private final int count;
        private final int dataCodewords;

        int a() {
            return this.count;
        }

        int b() {
            return this.dataCodewords;
        }

        private ECB(int i2, int i3) {
            this.count = i2;
            this.dataCodewords = i3;
        }
    }

    private Version(int i2, int i3, int i4, int i5, int i6, ECBlocks eCBlocks) {
        this.versionNumber = i2;
        this.symbolSizeRows = i3;
        this.symbolSizeColumns = i4;
        this.dataRegionSizeRows = i5;
        this.dataRegionSizeColumns = i6;
        this.ecBlocks = eCBlocks;
        int iB = eCBlocks.b();
        int iA = 0;
        for (ECB ecb : eCBlocks.a()) {
            iA += ecb.a() * (ecb.b() + iB);
        }
        this.totalCodewords = iA;
    }

    private static Version[] buildVersions() {
        int i2 = 1;
        int i3 = 5;
        Version version = new Version(1, 10, 10, 8, 8, new ECBlocks(i3, new ECB(i2, 3)));
        int i4 = 12;
        Version version2 = new Version(2, 12, 12, 10, 10, new ECBlocks(7, new ECB(i2, i3)));
        Version version3 = new Version(3, 14, 14, 12, 12, new ECBlocks(10, new ECB(i2, 8)));
        Version version4 = new Version(4, 16, 16, 14, 14, new ECBlocks(i4, new ECB(i2, i4)));
        int i5 = 18;
        Version version5 = new Version(5, 18, 18, 16, 16, new ECBlocks(14, new ECB(i2, i5)));
        Version version6 = new Version(6, 20, 20, 18, 18, new ECBlocks(i5, new ECB(i2, 22)));
        Version version7 = new Version(7, 22, 22, 20, 20, new ECBlocks(20, new ECB(i2, 30)));
        int i6 = 36;
        Version version8 = new Version(8, 24, 24, 22, 22, new ECBlocks(24, new ECB(i2, i6)));
        Version version9 = new Version(9, 26, 26, 24, 24, new ECBlocks(28, new ECB(i2, 44)));
        Version version10 = new Version(10, 32, 32, 14, 14, new ECBlocks(i6, new ECB(i2, 62)));
        Version version11 = new Version(11, 36, 36, 16, 16, new ECBlocks(42, new ECB(i2, 86)));
        Version version12 = new Version(12, 40, 40, 18, 18, new ECBlocks(48, new ECB(i2, 114)));
        Version version13 = new Version(13, 44, 44, 20, 20, new ECBlocks(56, new ECB(i2, 144)));
        Version version14 = new Version(14, 48, 48, 22, 22, new ECBlocks(68, new ECB(i2, 174)));
        int i7 = 2;
        Version version15 = new Version(15, 52, 52, 24, 24, new ECBlocks(42, new ECB(i7, 102)));
        Version version16 = new Version(16, 64, 64, 14, 14, new ECBlocks(56, new ECB(i7, 140)));
        int i8 = 4;
        Version version17 = new Version(17, 72, 72, 16, 16, new ECBlocks(36, new ECB(i8, 92)));
        Version version18 = new Version(18, 80, 80, 18, 18, new ECBlocks(48, new ECB(i8, 114)));
        Version version19 = new Version(19, 88, 88, 20, 20, new ECBlocks(56, new ECB(i8, 144)));
        Version version20 = new Version(20, 96, 96, 22, 22, new ECBlocks(68, new ECB(i8, 174)));
        int i9 = 6;
        return new Version[]{version, version2, version3, version4, version5, version6, version7, version8, version9, version10, version11, version12, version13, version14, version15, version16, version17, version18, version19, version20, new Version(21, 104, 104, 24, 24, new ECBlocks(56, new ECB(i9, 136))), new Version(22, 120, 120, 18, 18, new ECBlocks(68, new ECB(i9, 175))), new Version(23, 132, 132, 20, 20, new ECBlocks(62, new ECB(8, 163))), new Version(24, 144, 144, 22, 22, new ECBlocks(62, new ECB(8, 156), new ECB(2, 155))), new Version(25, 8, 18, 6, 16, new ECBlocks(7, new ECB(1, 5))), new Version(26, 8, 32, 6, 14, new ECBlocks(11, new ECB(1, 10))), new Version(27, 12, 26, 10, 24, new ECBlocks(14, new ECB(1, 16))), new Version(28, 12, 36, 10, 16, new ECBlocks(18, new ECB(1, 22))), new Version(29, 16, 36, 14, 16, new ECBlocks(24, new ECB(1, 32))), new Version(30, 16, 48, 14, 22, new ECBlocks(28, new ECB(1, 49)))};
    }

    public static Version getVersionForDimensions(int i2, int i3) throws FormatException {
        if ((i2 & 1) != 0 || (i3 & 1) != 0) {
            throw FormatException.getFormatInstance();
        }
        for (Version version : VERSIONS) {
            if (version.symbolSizeRows == i2 && version.symbolSizeColumns == i3) {
                return version;
            }
        }
        throw FormatException.getFormatInstance();
    }

    ECBlocks a() {
        return this.ecBlocks;
    }

    public int getDataRegionSizeColumns() {
        return this.dataRegionSizeColumns;
    }

    public int getDataRegionSizeRows() {
        return this.dataRegionSizeRows;
    }

    public int getSymbolSizeColumns() {
        return this.symbolSizeColumns;
    }

    public int getSymbolSizeRows() {
        return this.symbolSizeRows;
    }

    public int getTotalCodewords() {
        return this.totalCodewords;
    }

    public int getVersionNumber() {
        return this.versionNumber;
    }

    public String toString() {
        return String.valueOf(this.versionNumber);
    }

    static final class ECBlocks {
        private final ECB[] ecBlocks;
        private final int ecCodewords;

        ECB[] a() {
            return this.ecBlocks;
        }

        int b() {
            return this.ecCodewords;
        }

        private ECBlocks(int i2, ECB ecb) {
            this.ecCodewords = i2;
            this.ecBlocks = new ECB[]{ecb};
        }

        private ECBlocks(int i2, ECB ecb, ECB ecb2) {
            this.ecCodewords = i2;
            this.ecBlocks = new ECB[]{ecb, ecb2};
        }
    }
}
