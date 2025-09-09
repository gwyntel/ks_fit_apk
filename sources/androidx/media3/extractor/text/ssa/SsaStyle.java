package androidx.media3.extractor.text.ssa;

import android.graphics.Color;
import android.graphics.PointF;
import android.text.TextUtils;
import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.Log;
import androidx.media3.common.util.Util;
import com.google.common.primitives.Ints;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes2.dex */
final class SsaStyle {
    public static final int SSA_ALIGNMENT_BOTTOM_CENTER = 2;
    public static final int SSA_ALIGNMENT_BOTTOM_LEFT = 1;
    public static final int SSA_ALIGNMENT_BOTTOM_RIGHT = 3;
    public static final int SSA_ALIGNMENT_MIDDLE_CENTER = 5;
    public static final int SSA_ALIGNMENT_MIDDLE_LEFT = 4;
    public static final int SSA_ALIGNMENT_MIDDLE_RIGHT = 6;
    public static final int SSA_ALIGNMENT_TOP_CENTER = 8;
    public static final int SSA_ALIGNMENT_TOP_LEFT = 7;
    public static final int SSA_ALIGNMENT_TOP_RIGHT = 9;
    public static final int SSA_ALIGNMENT_UNKNOWN = -1;
    public static final int SSA_BORDER_STYLE_BOX = 3;
    public static final int SSA_BORDER_STYLE_OUTLINE = 1;
    public static final int SSA_BORDER_STYLE_UNKNOWN = -1;
    private static final String TAG = "SsaStyle";
    public final int alignment;
    public final boolean bold;
    public final int borderStyle;
    public final float fontSize;
    public final boolean italic;
    public final String name;

    @Nullable
    @ColorInt
    public final Integer outlineColor;

    @Nullable
    @ColorInt
    public final Integer primaryColor;
    public final boolean strikeout;
    public final boolean underline;

    static final class Format {
        public final int alignmentIndex;
        public final int boldIndex;
        public final int borderStyleIndex;
        public final int fontSizeIndex;
        public final int italicIndex;
        public final int length;
        public final int nameIndex;
        public final int outlineColorIndex;
        public final int primaryColorIndex;
        public final int strikeoutIndex;
        public final int underlineIndex;

        private Format(int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12) {
            this.nameIndex = i2;
            this.alignmentIndex = i3;
            this.primaryColorIndex = i4;
            this.outlineColorIndex = i5;
            this.fontSizeIndex = i6;
            this.boldIndex = i7;
            this.italicIndex = i8;
            this.underlineIndex = i9;
            this.strikeoutIndex = i10;
            this.borderStyleIndex = i11;
            this.length = i12;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Removed duplicated region for block: B:7:0x0033  */
        @androidx.annotation.Nullable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public static androidx.media3.extractor.text.ssa.SsaStyle.Format fromFormatLine(java.lang.String r18) {
            /*
                Method dump skipped, instructions count: 276
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.media3.extractor.text.ssa.SsaStyle.Format.fromFormatLine(java.lang.String):androidx.media3.extractor.text.ssa.SsaStyle$Format");
        }
    }

    static final class Overrides {
        private static final String TAG = "SsaStyle.Overrides";
        public final int alignment;

        @Nullable
        public final PointF position;
        private static final Pattern BRACES_PATTERN = Pattern.compile("\\{([^}]*)\\}");
        private static final String PADDED_DECIMAL_PATTERN = "\\s*\\d+(?:\\.\\d+)?\\s*";
        private static final Pattern POSITION_PATTERN = Pattern.compile(Util.formatInvariant("\\\\pos\\((%1$s),(%1$s)\\)", PADDED_DECIMAL_PATTERN));
        private static final Pattern MOVE_PATTERN = Pattern.compile(Util.formatInvariant("\\\\move\\(%1$s,%1$s,(%1$s),(%1$s)(?:,%1$s,%1$s)?\\)", PADDED_DECIMAL_PATTERN));
        private static final Pattern ALIGNMENT_OVERRIDE_PATTERN = Pattern.compile("\\\\an(\\d+)");

        private Overrides(int i2, @Nullable PointF pointF) {
            this.alignment = i2;
            this.position = pointF;
        }

        private static int parseAlignmentOverride(String str) {
            Matcher matcher = ALIGNMENT_OVERRIDE_PATTERN.matcher(str);
            if (matcher.find()) {
                return SsaStyle.parseAlignment((String) Assertions.checkNotNull(matcher.group(1)));
            }
            return -1;
        }

        public static Overrides parseFromDialogue(String str) {
            Matcher matcher = BRACES_PATTERN.matcher(str);
            PointF pointF = null;
            int i2 = -1;
            while (matcher.find()) {
                String str2 = (String) Assertions.checkNotNull(matcher.group(1));
                try {
                    PointF position = parsePosition(str2);
                    if (position != null) {
                        pointF = position;
                    }
                } catch (RuntimeException unused) {
                }
                try {
                    int alignmentOverride = parseAlignmentOverride(str2);
                    if (alignmentOverride != -1) {
                        i2 = alignmentOverride;
                    }
                } catch (RuntimeException unused2) {
                }
            }
            return new Overrides(i2, pointF);
        }

        @Nullable
        private static PointF parsePosition(String str) {
            String strGroup;
            String strGroup2;
            Matcher matcher = POSITION_PATTERN.matcher(str);
            Matcher matcher2 = MOVE_PATTERN.matcher(str);
            boolean zFind = matcher.find();
            boolean zFind2 = matcher2.find();
            if (zFind) {
                if (zFind2) {
                    Log.i(TAG, "Override has both \\pos(x,y) and \\move(x1,y1,x2,y2); using \\pos values. override='" + str + "'");
                }
                strGroup = matcher.group(1);
                strGroup2 = matcher.group(2);
            } else {
                if (!zFind2) {
                    return null;
                }
                strGroup = matcher2.group(1);
                strGroup2 = matcher2.group(2);
            }
            return new PointF(Float.parseFloat(((String) Assertions.checkNotNull(strGroup)).trim()), Float.parseFloat(((String) Assertions.checkNotNull(strGroup2)).trim()));
        }

        public static String stripStyleOverrides(String str) {
            return BRACES_PATTERN.matcher(str).replaceAll("");
        }
    }

    @Target({ElementType.TYPE_USE})
    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface SsaAlignment {
    }

    @Target({ElementType.TYPE_USE})
    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface SsaBorderStyle {
    }

    private SsaStyle(String str, int i2, @Nullable @ColorInt Integer num, @Nullable @ColorInt Integer num2, float f2, boolean z2, boolean z3, boolean z4, boolean z5, int i3) {
        this.name = str;
        this.alignment = i2;
        this.primaryColor = num;
        this.outlineColor = num2;
        this.fontSize = f2;
        this.bold = z2;
        this.italic = z3;
        this.underline = z4;
        this.strikeout = z5;
        this.borderStyle = i3;
    }

    @Nullable
    public static SsaStyle fromStyleLine(String str, Format format) {
        Assertions.checkArgument(str.startsWith("Style:"));
        String[] strArrSplit = TextUtils.split(str.substring(6), ",");
        int length = strArrSplit.length;
        int i2 = format.length;
        if (length != i2) {
            Log.w(TAG, Util.formatInvariant("Skipping malformed 'Style:' line (expected %s values, found %s): '%s'", Integer.valueOf(i2), Integer.valueOf(strArrSplit.length), str));
            return null;
        }
        try {
            String strTrim = strArrSplit[format.nameIndex].trim();
            int i3 = format.alignmentIndex;
            int alignment = i3 != -1 ? parseAlignment(strArrSplit[i3].trim()) : -1;
            int i4 = format.primaryColorIndex;
            Integer color = i4 != -1 ? parseColor(strArrSplit[i4].trim()) : null;
            int i5 = format.outlineColorIndex;
            Integer color2 = i5 != -1 ? parseColor(strArrSplit[i5].trim()) : null;
            int i6 = format.fontSizeIndex;
            float fontSize = i6 != -1 ? parseFontSize(strArrSplit[i6].trim()) : -3.4028235E38f;
            int i7 = format.boldIndex;
            boolean z2 = i7 != -1 && parseBooleanValue(strArrSplit[i7].trim());
            int i8 = format.italicIndex;
            boolean z3 = i8 != -1 && parseBooleanValue(strArrSplit[i8].trim());
            int i9 = format.underlineIndex;
            boolean z4 = i9 != -1 && parseBooleanValue(strArrSplit[i9].trim());
            int i10 = format.strikeoutIndex;
            boolean z5 = i10 != -1 && parseBooleanValue(strArrSplit[i10].trim());
            int i11 = format.borderStyleIndex;
            return new SsaStyle(strTrim, alignment, color, color2, fontSize, z2, z3, z4, z5, i11 != -1 ? parseBorderStyle(strArrSplit[i11].trim()) : -1);
        } catch (RuntimeException e2) {
            Log.w(TAG, "Skipping malformed 'Style:' line: '" + str + "'", e2);
            return null;
        }
    }

    private static boolean isValidAlignment(int i2) {
        switch (i2) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                return true;
            default:
                return false;
        }
    }

    private static boolean isValidBorderStyle(int i2) {
        return i2 == 1 || i2 == 3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int parseAlignment(String str) throws NumberFormatException {
        try {
            int i2 = Integer.parseInt(str.trim());
            if (isValidAlignment(i2)) {
                return i2;
            }
        } catch (NumberFormatException unused) {
        }
        Log.w(TAG, "Ignoring unknown alignment: " + str);
        return -1;
    }

    private static boolean parseBooleanValue(String str) throws NumberFormatException {
        try {
            int i2 = Integer.parseInt(str);
            return i2 == 1 || i2 == -1;
        } catch (NumberFormatException e2) {
            Log.w(TAG, "Failed to parse boolean value: '" + str + "'", e2);
            return false;
        }
    }

    private static int parseBorderStyle(String str) throws NumberFormatException {
        try {
            int i2 = Integer.parseInt(str.trim());
            if (isValidBorderStyle(i2)) {
                return i2;
            }
        } catch (NumberFormatException unused) {
        }
        Log.w(TAG, "Ignoring unknown BorderStyle: " + str);
        return -1;
    }

    @Nullable
    @ColorInt
    public static Integer parseColor(String str) {
        try {
            long j2 = str.startsWith("&H") ? Long.parseLong(str.substring(2), 16) : Long.parseLong(str);
            Assertions.checkArgument(j2 <= 4294967295L);
            return Integer.valueOf(Color.argb(Ints.checkedCast(((j2 >> 24) & 255) ^ 255), Ints.checkedCast(j2 & 255), Ints.checkedCast((j2 >> 8) & 255), Ints.checkedCast((j2 >> 16) & 255)));
        } catch (IllegalArgumentException e2) {
            Log.w(TAG, "Failed to parse color expression: '" + str + "'", e2);
            return null;
        }
    }

    private static float parseFontSize(String str) {
        try {
            return Float.parseFloat(str);
        } catch (NumberFormatException e2) {
            Log.w(TAG, "Failed to parse font size: '" + str + "'", e2);
            return -3.4028235E38f;
        }
    }
}
