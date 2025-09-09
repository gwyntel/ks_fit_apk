package androidx.core.text;

import android.text.SpannableStringBuilder;
import java.util.Locale;
import kotlin.text.Typography;

/* loaded from: classes.dex */
public final class BidiFormatter {
    private static final int DEFAULT_FLAGS = 2;
    private static final int DIR_LTR = -1;
    private static final int DIR_RTL = 1;
    private static final int DIR_UNKNOWN = 0;
    private static final String EMPTY_STRING = "";
    private static final int FLAG_STEREO_RESET = 2;
    private static final char LRE = 8234;
    private static final char LRM = 8206;
    private static final String LRM_STRING;
    private static final char PDF = 8236;
    private static final char RLE = 8235;
    private static final char RLM = 8207;
    private static final String RLM_STRING;

    /* renamed from: a, reason: collision with root package name */
    static final TextDirectionHeuristicCompat f3696a;

    /* renamed from: b, reason: collision with root package name */
    static final BidiFormatter f3697b;

    /* renamed from: c, reason: collision with root package name */
    static final BidiFormatter f3698c;
    private final TextDirectionHeuristicCompat mDefaultTextDirectionHeuristicCompat;
    private final int mFlags;
    private final boolean mIsRtlContext;

    private static class DirectionalityEstimator {
        private int charIndex;
        private final boolean isHtml;
        private char lastChar;
        private final int length;
        private final CharSequence text;
        private static final int DIR_TYPE_CACHE_SIZE = 1792;
        private static final byte[] DIR_TYPE_CACHE = new byte[DIR_TYPE_CACHE_SIZE];

        static {
            for (int i2 = 0; i2 < DIR_TYPE_CACHE_SIZE; i2++) {
                DIR_TYPE_CACHE[i2] = Character.getDirectionality(i2);
            }
        }

        DirectionalityEstimator(CharSequence charSequence, boolean z2) {
            this.text = charSequence;
            this.isHtml = z2;
            this.length = charSequence.length();
        }

        private static byte getCachedDirectionality(char c2) {
            return c2 < DIR_TYPE_CACHE_SIZE ? DIR_TYPE_CACHE[c2] : Character.getDirectionality(c2);
        }

        private byte skipEntityBackward() {
            char cCharAt;
            int i2 = this.charIndex;
            do {
                int i3 = this.charIndex;
                if (i3 <= 0) {
                    break;
                }
                CharSequence charSequence = this.text;
                int i4 = i3 - 1;
                this.charIndex = i4;
                cCharAt = charSequence.charAt(i4);
                this.lastChar = cCharAt;
                if (cCharAt == '&') {
                    return (byte) 12;
                }
            } while (cCharAt != ';');
            this.charIndex = i2;
            this.lastChar = ';';
            return (byte) 13;
        }

        private byte skipEntityForward() {
            char cCharAt;
            do {
                int i2 = this.charIndex;
                if (i2 >= this.length) {
                    return (byte) 12;
                }
                CharSequence charSequence = this.text;
                this.charIndex = i2 + 1;
                cCharAt = charSequence.charAt(i2);
                this.lastChar = cCharAt;
            } while (cCharAt != ';');
            return (byte) 12;
        }

        private byte skipTagBackward() {
            char cCharAt;
            int i2 = this.charIndex;
            while (true) {
                int i3 = this.charIndex;
                if (i3 <= 0) {
                    break;
                }
                CharSequence charSequence = this.text;
                int i4 = i3 - 1;
                this.charIndex = i4;
                char cCharAt2 = charSequence.charAt(i4);
                this.lastChar = cCharAt2;
                if (cCharAt2 == '<') {
                    return (byte) 12;
                }
                if (cCharAt2 == '>') {
                    break;
                }
                if (cCharAt2 == '\"' || cCharAt2 == '\'') {
                    do {
                        int i5 = this.charIndex;
                        if (i5 > 0) {
                            CharSequence charSequence2 = this.text;
                            int i6 = i5 - 1;
                            this.charIndex = i6;
                            cCharAt = charSequence2.charAt(i6);
                            this.lastChar = cCharAt;
                        }
                    } while (cCharAt != cCharAt2);
                }
            }
            this.charIndex = i2;
            this.lastChar = Typography.greater;
            return (byte) 13;
        }

        private byte skipTagForward() {
            char cCharAt;
            int i2 = this.charIndex;
            while (true) {
                int i3 = this.charIndex;
                if (i3 >= this.length) {
                    this.charIndex = i2;
                    this.lastChar = Typography.less;
                    return (byte) 13;
                }
                CharSequence charSequence = this.text;
                this.charIndex = i3 + 1;
                char cCharAt2 = charSequence.charAt(i3);
                this.lastChar = cCharAt2;
                if (cCharAt2 == '>') {
                    return (byte) 12;
                }
                if (cCharAt2 == '\"' || cCharAt2 == '\'') {
                    do {
                        int i4 = this.charIndex;
                        if (i4 < this.length) {
                            CharSequence charSequence2 = this.text;
                            this.charIndex = i4 + 1;
                            cCharAt = charSequence2.charAt(i4);
                            this.lastChar = cCharAt;
                        }
                    } while (cCharAt != cCharAt2);
                }
            }
        }

        byte a() {
            char cCharAt = this.text.charAt(this.charIndex - 1);
            this.lastChar = cCharAt;
            if (Character.isLowSurrogate(cCharAt)) {
                int iCodePointBefore = Character.codePointBefore(this.text, this.charIndex);
                this.charIndex -= Character.charCount(iCodePointBefore);
                return Character.getDirectionality(iCodePointBefore);
            }
            this.charIndex--;
            byte cachedDirectionality = getCachedDirectionality(this.lastChar);
            if (!this.isHtml) {
                return cachedDirectionality;
            }
            char c2 = this.lastChar;
            return c2 == '>' ? skipTagBackward() : c2 == ';' ? skipEntityBackward() : cachedDirectionality;
        }

        byte b() {
            char cCharAt = this.text.charAt(this.charIndex);
            this.lastChar = cCharAt;
            if (Character.isHighSurrogate(cCharAt)) {
                int iCodePointAt = Character.codePointAt(this.text, this.charIndex);
                this.charIndex += Character.charCount(iCodePointAt);
                return Character.getDirectionality(iCodePointAt);
            }
            this.charIndex++;
            byte cachedDirectionality = getCachedDirectionality(this.lastChar);
            if (!this.isHtml) {
                return cachedDirectionality;
            }
            char c2 = this.lastChar;
            return c2 == '<' ? skipTagForward() : c2 == '&' ? skipEntityForward() : cachedDirectionality;
        }

        int c() {
            this.charIndex = 0;
            int i2 = 0;
            int i3 = 0;
            int i4 = 0;
            while (this.charIndex < this.length && i2 == 0) {
                byte b2 = b();
                if (b2 != 0) {
                    if (b2 == 1 || b2 == 2) {
                        if (i4 == 0) {
                            return 1;
                        }
                    } else if (b2 != 9) {
                        switch (b2) {
                            case 14:
                            case 15:
                                i4++;
                                i3 = -1;
                                continue;
                            case 16:
                            case 17:
                                i4++;
                                i3 = 1;
                                continue;
                            case 18:
                                i4--;
                                i3 = 0;
                                continue;
                        }
                    }
                } else if (i4 == 0) {
                    return -1;
                }
                i2 = i4;
            }
            if (i2 == 0) {
                return 0;
            }
            if (i3 != 0) {
                return i3;
            }
            while (this.charIndex > 0) {
                switch (a()) {
                    case 14:
                    case 15:
                        if (i2 == i4) {
                            return -1;
                        }
                        break;
                    case 16:
                    case 17:
                        if (i2 == i4) {
                            return 1;
                        }
                        break;
                    case 18:
                        i4++;
                        continue;
                }
                i4--;
            }
            return 0;
        }

        int d() {
            this.charIndex = this.length;
            int i2 = 0;
            while (true) {
                int i3 = i2;
                while (this.charIndex > 0) {
                    byte bA = a();
                    if (bA != 0) {
                        if (bA == 1 || bA == 2) {
                            if (i2 == 0) {
                                return 1;
                            }
                            if (i3 == 0) {
                                break;
                            }
                        } else if (bA != 9) {
                            switch (bA) {
                                case 14:
                                case 15:
                                    if (i3 == i2) {
                                        return -1;
                                    }
                                    i2--;
                                    break;
                                case 16:
                                case 17:
                                    if (i3 == i2) {
                                        return 1;
                                    }
                                    i2--;
                                    break;
                                case 18:
                                    i2++;
                                    break;
                                default:
                                    if (i3 != 0) {
                                        break;
                                    } else {
                                        break;
                                    }
                            }
                        } else {
                            continue;
                        }
                    } else {
                        if (i2 == 0) {
                            return -1;
                        }
                        if (i3 == 0) {
                            break;
                        }
                    }
                }
                return 0;
            }
        }
    }

    static {
        TextDirectionHeuristicCompat textDirectionHeuristicCompat = TextDirectionHeuristicsCompat.FIRSTSTRONG_LTR;
        f3696a = textDirectionHeuristicCompat;
        LRM_STRING = Character.toString(LRM);
        RLM_STRING = Character.toString(RLM);
        f3697b = new BidiFormatter(false, 2, textDirectionHeuristicCompat);
        f3698c = new BidiFormatter(true, 2, textDirectionHeuristicCompat);
    }

    BidiFormatter(boolean z2, int i2, TextDirectionHeuristicCompat textDirectionHeuristicCompat) {
        this.mIsRtlContext = z2;
        this.mFlags = i2;
        this.mDefaultTextDirectionHeuristicCompat = textDirectionHeuristicCompat;
    }

    static boolean a(Locale locale) {
        return TextUtilsCompat.getLayoutDirectionFromLocale(locale) == 1;
    }

    private static int getEntryDir(CharSequence charSequence) {
        return new DirectionalityEstimator(charSequence, false).c();
    }

    private static int getExitDir(CharSequence charSequence) {
        return new DirectionalityEstimator(charSequence, false).d();
    }

    public static BidiFormatter getInstance() {
        return new Builder().build();
    }

    private String markAfter(CharSequence charSequence, TextDirectionHeuristicCompat textDirectionHeuristicCompat) {
        boolean zIsRtl = textDirectionHeuristicCompat.isRtl(charSequence, 0, charSequence.length());
        return (this.mIsRtlContext || !(zIsRtl || getExitDir(charSequence) == 1)) ? this.mIsRtlContext ? (!zIsRtl || getExitDir(charSequence) == -1) ? RLM_STRING : "" : "" : LRM_STRING;
    }

    private String markBefore(CharSequence charSequence, TextDirectionHeuristicCompat textDirectionHeuristicCompat) {
        boolean zIsRtl = textDirectionHeuristicCompat.isRtl(charSequence, 0, charSequence.length());
        return (this.mIsRtlContext || !(zIsRtl || getEntryDir(charSequence) == 1)) ? this.mIsRtlContext ? (!zIsRtl || getEntryDir(charSequence) == -1) ? RLM_STRING : "" : "" : LRM_STRING;
    }

    public boolean getStereoReset() {
        return (this.mFlags & 2) != 0;
    }

    public boolean isRtl(String str) {
        return isRtl((CharSequence) str);
    }

    public boolean isRtlContext() {
        return this.mIsRtlContext;
    }

    public String unicodeWrap(String str, TextDirectionHeuristicCompat textDirectionHeuristicCompat, boolean z2) {
        if (str == null) {
            return null;
        }
        return unicodeWrap((CharSequence) str, textDirectionHeuristicCompat, z2).toString();
    }

    public static final class Builder {
        private int mFlags;
        private boolean mIsRtlContext;
        private TextDirectionHeuristicCompat mTextDirectionHeuristicCompat;

        public Builder() {
            initialize(BidiFormatter.a(Locale.getDefault()));
        }

        private static BidiFormatter getDefaultInstanceFromContext(boolean z2) {
            return z2 ? BidiFormatter.f3698c : BidiFormatter.f3697b;
        }

        private void initialize(boolean z2) {
            this.mIsRtlContext = z2;
            this.mTextDirectionHeuristicCompat = BidiFormatter.f3696a;
            this.mFlags = 2;
        }

        public BidiFormatter build() {
            return (this.mFlags == 2 && this.mTextDirectionHeuristicCompat == BidiFormatter.f3696a) ? getDefaultInstanceFromContext(this.mIsRtlContext) : new BidiFormatter(this.mIsRtlContext, this.mFlags, this.mTextDirectionHeuristicCompat);
        }

        public Builder setTextDirectionHeuristic(TextDirectionHeuristicCompat textDirectionHeuristicCompat) {
            this.mTextDirectionHeuristicCompat = textDirectionHeuristicCompat;
            return this;
        }

        public Builder stereoReset(boolean z2) {
            if (z2) {
                this.mFlags |= 2;
            } else {
                this.mFlags &= -3;
            }
            return this;
        }

        public Builder(boolean z2) {
            initialize(z2);
        }

        public Builder(Locale locale) {
            initialize(BidiFormatter.a(locale));
        }
    }

    public static BidiFormatter getInstance(boolean z2) {
        return new Builder(z2).build();
    }

    public boolean isRtl(CharSequence charSequence) {
        return this.mDefaultTextDirectionHeuristicCompat.isRtl(charSequence, 0, charSequence.length());
    }

    public CharSequence unicodeWrap(CharSequence charSequence, TextDirectionHeuristicCompat textDirectionHeuristicCompat, boolean z2) {
        if (charSequence == null) {
            return null;
        }
        boolean zIsRtl = textDirectionHeuristicCompat.isRtl(charSequence, 0, charSequence.length());
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        if (getStereoReset() && z2) {
            spannableStringBuilder.append((CharSequence) markBefore(charSequence, zIsRtl ? TextDirectionHeuristicsCompat.RTL : TextDirectionHeuristicsCompat.LTR));
        }
        if (zIsRtl != this.mIsRtlContext) {
            spannableStringBuilder.append(zIsRtl ? RLE : LRE);
            spannableStringBuilder.append(charSequence);
            spannableStringBuilder.append(PDF);
        } else {
            spannableStringBuilder.append(charSequence);
        }
        if (z2) {
            spannableStringBuilder.append((CharSequence) markAfter(charSequence, zIsRtl ? TextDirectionHeuristicsCompat.RTL : TextDirectionHeuristicsCompat.LTR));
        }
        return spannableStringBuilder;
    }

    public static BidiFormatter getInstance(Locale locale) {
        return new Builder(locale).build();
    }

    public String unicodeWrap(String str, TextDirectionHeuristicCompat textDirectionHeuristicCompat) {
        return unicodeWrap(str, textDirectionHeuristicCompat, true);
    }

    public CharSequence unicodeWrap(CharSequence charSequence, TextDirectionHeuristicCompat textDirectionHeuristicCompat) {
        return unicodeWrap(charSequence, textDirectionHeuristicCompat, true);
    }

    public String unicodeWrap(String str, boolean z2) {
        return unicodeWrap(str, this.mDefaultTextDirectionHeuristicCompat, z2);
    }

    public CharSequence unicodeWrap(CharSequence charSequence, boolean z2) {
        return unicodeWrap(charSequence, this.mDefaultTextDirectionHeuristicCompat, z2);
    }

    public String unicodeWrap(String str) {
        return unicodeWrap(str, this.mDefaultTextDirectionHeuristicCompat, true);
    }

    public CharSequence unicodeWrap(CharSequence charSequence) {
        return unicodeWrap(charSequence, this.mDefaultTextDirectionHeuristicCompat, true);
    }
}
