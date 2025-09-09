package org.apache.commons.lang.text;

import java.util.Arrays;
import kotlin.text.Typography;

/* loaded from: classes5.dex */
public abstract class StrMatcher {
    private static final StrMatcher COMMA_MATCHER = new CharMatcher(',');
    private static final StrMatcher TAB_MATCHER = new CharMatcher('\t');
    private static final StrMatcher SPACE_MATCHER = new CharMatcher(' ');
    private static final StrMatcher SPLIT_MATCHER = new CharSetMatcher(" \t\n\r\f".toCharArray());
    private static final StrMatcher TRIM_MATCHER = new TrimMatcher();
    private static final StrMatcher SINGLE_QUOTE_MATCHER = new CharMatcher('\'');
    private static final StrMatcher DOUBLE_QUOTE_MATCHER = new CharMatcher(Typography.quote);
    private static final StrMatcher QUOTE_MATCHER = new CharSetMatcher("'\"".toCharArray());
    private static final StrMatcher NONE_MATCHER = new NoMatcher();

    static final class CharMatcher extends StrMatcher {
        private final char ch;

        CharMatcher(char c2) {
            this.ch = c2;
        }

        @Override // org.apache.commons.lang.text.StrMatcher
        public int isMatch(char[] cArr, int i2, int i3, int i4) {
            return this.ch == cArr[i2] ? 1 : 0;
        }
    }

    static final class CharSetMatcher extends StrMatcher {
        private final char[] chars;

        CharSetMatcher(char[] cArr) {
            char[] cArr2 = (char[]) cArr.clone();
            this.chars = cArr2;
            Arrays.sort(cArr2);
        }

        @Override // org.apache.commons.lang.text.StrMatcher
        public int isMatch(char[] cArr, int i2, int i3, int i4) {
            return Arrays.binarySearch(this.chars, cArr[i2]) >= 0 ? 1 : 0;
        }
    }

    static final class NoMatcher extends StrMatcher {
        NoMatcher() {
        }

        @Override // org.apache.commons.lang.text.StrMatcher
        public int isMatch(char[] cArr, int i2, int i3, int i4) {
            return 0;
        }
    }

    static final class StringMatcher extends StrMatcher {
        private final char[] chars;

        StringMatcher(String str) {
            this.chars = str.toCharArray();
        }

        @Override // org.apache.commons.lang.text.StrMatcher
        public int isMatch(char[] cArr, int i2, int i3, int i4) {
            int length = this.chars.length;
            if (i2 + length > i4) {
                return 0;
            }
            int i5 = 0;
            while (true) {
                char[] cArr2 = this.chars;
                if (i5 >= cArr2.length) {
                    return length;
                }
                if (cArr2[i5] != cArr[i2]) {
                    return 0;
                }
                i5++;
                i2++;
            }
        }
    }

    static final class TrimMatcher extends StrMatcher {
        TrimMatcher() {
        }

        @Override // org.apache.commons.lang.text.StrMatcher
        public int isMatch(char[] cArr, int i2, int i3, int i4) {
            return cArr[i2] <= ' ' ? 1 : 0;
        }
    }

    protected StrMatcher() {
    }

    public static StrMatcher charMatcher(char c2) {
        return new CharMatcher(c2);
    }

    public static StrMatcher charSetMatcher(char[] cArr) {
        return (cArr == null || cArr.length == 0) ? NONE_MATCHER : cArr.length == 1 ? new CharMatcher(cArr[0]) : new CharSetMatcher(cArr);
    }

    public static StrMatcher commaMatcher() {
        return COMMA_MATCHER;
    }

    public static StrMatcher doubleQuoteMatcher() {
        return DOUBLE_QUOTE_MATCHER;
    }

    public static StrMatcher noneMatcher() {
        return NONE_MATCHER;
    }

    public static StrMatcher quoteMatcher() {
        return QUOTE_MATCHER;
    }

    public static StrMatcher singleQuoteMatcher() {
        return SINGLE_QUOTE_MATCHER;
    }

    public static StrMatcher spaceMatcher() {
        return SPACE_MATCHER;
    }

    public static StrMatcher splitMatcher() {
        return SPLIT_MATCHER;
    }

    public static StrMatcher stringMatcher(String str) {
        return (str == null || str.length() == 0) ? NONE_MATCHER : new StringMatcher(str);
    }

    public static StrMatcher tabMatcher() {
        return TAB_MATCHER;
    }

    public static StrMatcher trimMatcher() {
        return TRIM_MATCHER;
    }

    public int isMatch(char[] cArr, int i2) {
        return isMatch(cArr, i2, 0, cArr.length);
    }

    public abstract int isMatch(char[] cArr, int i2, int i3, int i4);

    public static StrMatcher charSetMatcher(String str) {
        if (str != null && str.length() != 0) {
            if (str.length() == 1) {
                return new CharMatcher(str.charAt(0));
            }
            return new CharSetMatcher(str.toCharArray());
        }
        return NONE_MATCHER;
    }
}
