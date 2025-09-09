package androidx.profileinstaller;

/* loaded from: classes2.dex */
enum FileSectionType {
    DEX_FILES(0),
    EXTRA_DESCRIPTORS(1),
    CLASSES(2),
    METHODS(3),
    AGGREGATION_COUNT(4);

    private final long mValue;

    FileSectionType(long j2) {
        this.mValue = j2;
    }

    static FileSectionType fromValue(long j2) {
        FileSectionType[] fileSectionTypeArrValues = values();
        for (int i2 = 0; i2 < fileSectionTypeArrValues.length; i2++) {
            if (fileSectionTypeArrValues[i2].getValue() == j2) {
                return fileSectionTypeArrValues[i2];
            }
        }
        throw new IllegalArgumentException("Unsupported FileSection Type " + j2);
    }

    public long getValue() {
        return this.mValue;
    }
}
