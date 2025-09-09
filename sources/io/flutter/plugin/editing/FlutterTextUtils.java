package io.flutter.plugin.editing;

import io.flutter.embedding.engine.FlutterJNI;

/* loaded from: classes4.dex */
class FlutterTextUtils {
    public static final int CANCEL_TAG = 917631;
    public static final int CARRIAGE_RETURN = 13;
    public static final int COMBINING_ENCLOSING_KEYCAP = 8419;
    public static final int LINE_FEED = 10;
    public static final int ZERO_WIDTH_JOINER = 8205;
    private final FlutterJNI flutterJNI;

    public FlutterTextUtils(FlutterJNI flutterJNI) {
        this.flutterJNI = flutterJNI;
    }

    /* JADX WARN: Removed duplicated region for block: B:89:0x0137 A[PHI: r2 r4
      0x0137: PHI (r2v2 int) = (r2v1 int), (r2v4 int) binds: [B:58:0x00b9, B:81:0x0112] A[DONT_GENERATE, DONT_INLINE]
      0x0137: PHI (r4v2 int) = (r4v1 int), (r4v4 int) binds: [B:58:0x00b9, B:81:0x0112] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int getOffsetAfter(java.lang.CharSequence r10, int r11) {
        /*
            Method dump skipped, instructions count: 325
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: io.flutter.plugin.editing.FlutterTextUtils.getOffsetAfter(java.lang.CharSequence, int):int");
    }

    /* JADX WARN: Removed duplicated region for block: B:90:0x0149 A[PHI: r2 r4
      0x0149: PHI (r2v6 int) = (r2v3 int), (r2v8 int) binds: [B:80:0x0117, B:82:0x0124] A[DONT_GENERATE, DONT_INLINE]
      0x0149: PHI (r4v5 int) = (r4v3 int), (r4v7 int) binds: [B:80:0x0117, B:82:0x0124] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int getOffsetBefore(java.lang.CharSequence r9, int r10) {
        /*
            Method dump skipped, instructions count: 343
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: io.flutter.plugin.editing.FlutterTextUtils.getOffsetBefore(java.lang.CharSequence, int):int");
    }

    public boolean isEmoji(int i2) {
        return this.flutterJNI.isCodePointEmoji(i2);
    }

    public boolean isEmojiModifier(int i2) {
        return this.flutterJNI.isCodePointEmojiModifier(i2);
    }

    public boolean isEmojiModifierBase(int i2) {
        return this.flutterJNI.isCodePointEmojiModifierBase(i2);
    }

    public boolean isKeycapBase(int i2) {
        return (48 <= i2 && i2 <= 57) || i2 == 35 || i2 == 42;
    }

    public boolean isRegionalIndicatorSymbol(int i2) {
        return this.flutterJNI.isCodePointRegionalIndicator(i2);
    }

    public boolean isTagSpecChar(int i2) {
        return 917536 <= i2 && i2 <= 917630;
    }

    public boolean isVariationSelector(int i2) {
        return this.flutterJNI.isCodePointVariantSelector(i2);
    }
}
