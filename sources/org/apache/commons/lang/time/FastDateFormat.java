package org.apache.commons.lang.time;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.text.StrBuilder;

/* loaded from: classes5.dex */
public class FastDateFormat extends Format {
    public static final int FULL = 0;
    public static final int LONG = 1;
    public static final int MEDIUM = 2;
    public static final int SHORT = 3;
    private static String cDefaultPattern = null;
    private static final long serialVersionUID = 1;
    private final Locale mLocale;
    private final boolean mLocaleForced;
    private transient int mMaxLengthEstimate;
    private final String mPattern;
    private transient Rule[] mRules;
    private final TimeZone mTimeZone;
    private final boolean mTimeZoneForced;
    private static final Map cInstanceCache = new HashMap(7);
    private static final Map cDateInstanceCache = new HashMap(7);
    private static final Map cTimeInstanceCache = new HashMap(7);
    private static final Map cDateTimeInstanceCache = new HashMap(7);
    private static final Map cTimeZoneDisplayCache = new HashMap(7);

    private static class CharacterLiteral implements Rule {
        private final char mValue;

        CharacterLiteral(char c2) {
            this.mValue = c2;
        }

        @Override // org.apache.commons.lang.time.FastDateFormat.Rule
        public void appendTo(StringBuffer stringBuffer, Calendar calendar) {
            stringBuffer.append(this.mValue);
        }

        @Override // org.apache.commons.lang.time.FastDateFormat.Rule
        public int estimateLength() {
            return 1;
        }
    }

    private interface NumberRule extends Rule {
        void appendTo(StringBuffer stringBuffer, int i2);
    }

    private static class PaddedNumberField implements NumberRule {
        private final int mField;
        private final int mSize;

        PaddedNumberField(int i2, int i3) {
            if (i3 < 3) {
                throw new IllegalArgumentException();
            }
            this.mField = i2;
            this.mSize = i3;
        }

        @Override // org.apache.commons.lang.time.FastDateFormat.Rule
        public void appendTo(StringBuffer stringBuffer, Calendar calendar) {
            appendTo(stringBuffer, calendar.get(this.mField));
        }

        @Override // org.apache.commons.lang.time.FastDateFormat.Rule
        public int estimateLength() {
            return 4;
        }

        @Override // org.apache.commons.lang.time.FastDateFormat.NumberRule
        public final void appendTo(StringBuffer stringBuffer, int i2) {
            int length;
            if (i2 < 100) {
                int i3 = this.mSize;
                while (true) {
                    i3--;
                    if (i3 < 2) {
                        stringBuffer.append((char) ((i2 / 10) + 48));
                        stringBuffer.append((char) ((i2 % 10) + 48));
                        return;
                    }
                    stringBuffer.append('0');
                }
            } else {
                if (i2 < 1000) {
                    length = 3;
                } else {
                    Validate.isTrue(i2 > -1, "Negative values should not be possible", i2);
                    length = Integer.toString(i2).length();
                }
                int i4 = this.mSize;
                while (true) {
                    i4--;
                    if (i4 < length) {
                        stringBuffer.append(Integer.toString(i2));
                        return;
                    }
                    stringBuffer.append('0');
                }
            }
        }
    }

    private static class Pair {
        private final Object mObj1;
        private final Object mObj2;

        public Pair(Object obj, Object obj2) {
            this.mObj1 = obj;
            this.mObj2 = obj2;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Pair)) {
                return false;
            }
            Pair pair = (Pair) obj;
            Object obj2 = this.mObj1;
            if (obj2 != null ? obj2.equals(pair.mObj1) : pair.mObj1 == null) {
                Object obj3 = this.mObj2;
                Object obj4 = pair.mObj2;
                if (obj3 == null) {
                    if (obj4 == null) {
                        return true;
                    }
                } else if (obj3.equals(obj4)) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            Object obj = this.mObj1;
            int iHashCode = obj == null ? 0 : obj.hashCode();
            Object obj2 = this.mObj2;
            return iHashCode + (obj2 != null ? obj2.hashCode() : 0);
        }

        public String toString() {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("[");
            stringBuffer.append(this.mObj1);
            stringBuffer.append(':');
            stringBuffer.append(this.mObj2);
            stringBuffer.append(']');
            return stringBuffer.toString();
        }
    }

    private interface Rule {
        void appendTo(StringBuffer stringBuffer, Calendar calendar);

        int estimateLength();
    }

    private static class StringLiteral implements Rule {
        private final String mValue;

        StringLiteral(String str) {
            this.mValue = str;
        }

        @Override // org.apache.commons.lang.time.FastDateFormat.Rule
        public void appendTo(StringBuffer stringBuffer, Calendar calendar) {
            stringBuffer.append(this.mValue);
        }

        @Override // org.apache.commons.lang.time.FastDateFormat.Rule
        public int estimateLength() {
            return this.mValue.length();
        }
    }

    private static class TextField implements Rule {
        private final int mField;
        private final String[] mValues;

        TextField(int i2, String[] strArr) {
            this.mField = i2;
            this.mValues = strArr;
        }

        @Override // org.apache.commons.lang.time.FastDateFormat.Rule
        public void appendTo(StringBuffer stringBuffer, Calendar calendar) {
            stringBuffer.append(this.mValues[calendar.get(this.mField)]);
        }

        @Override // org.apache.commons.lang.time.FastDateFormat.Rule
        public int estimateLength() {
            int length = this.mValues.length;
            int i2 = 0;
            while (true) {
                length--;
                if (length < 0) {
                    return i2;
                }
                int length2 = this.mValues[length].length();
                if (length2 > i2) {
                    i2 = length2;
                }
            }
        }
    }

    private static class TimeZoneDisplayKey {
        private final Locale mLocale;
        private final int mStyle;
        private final TimeZone mTimeZone;

        TimeZoneDisplayKey(TimeZone timeZone, boolean z2, int i2, Locale locale) {
            this.mTimeZone = timeZone;
            this.mStyle = z2 ? i2 | Integer.MIN_VALUE : i2;
            this.mLocale = locale;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof TimeZoneDisplayKey)) {
                return false;
            }
            TimeZoneDisplayKey timeZoneDisplayKey = (TimeZoneDisplayKey) obj;
            return this.mTimeZone.equals(timeZoneDisplayKey.mTimeZone) && this.mStyle == timeZoneDisplayKey.mStyle && this.mLocale.equals(timeZoneDisplayKey.mLocale);
        }

        public int hashCode() {
            return (this.mStyle * 31) + this.mLocale.hashCode();
        }
    }

    private static class TimeZoneNameRule implements Rule {
        private final String mDaylight;
        private final Locale mLocale;
        private final String mStandard;
        private final int mStyle;
        private final TimeZone mTimeZone;
        private final boolean mTimeZoneForced;

        TimeZoneNameRule(TimeZone timeZone, boolean z2, Locale locale, int i2) {
            this.mTimeZone = timeZone;
            this.mTimeZoneForced = z2;
            this.mLocale = locale;
            this.mStyle = i2;
            if (z2) {
                this.mStandard = FastDateFormat.getTimeZoneDisplay(timeZone, false, i2, locale);
                this.mDaylight = FastDateFormat.getTimeZoneDisplay(timeZone, true, i2, locale);
            } else {
                this.mStandard = null;
                this.mDaylight = null;
            }
        }

        @Override // org.apache.commons.lang.time.FastDateFormat.Rule
        public void appendTo(StringBuffer stringBuffer, Calendar calendar) {
            if (this.mTimeZoneForced) {
                if (!this.mTimeZone.useDaylightTime() || calendar.get(16) == 0) {
                    stringBuffer.append(this.mStandard);
                    return;
                } else {
                    stringBuffer.append(this.mDaylight);
                    return;
                }
            }
            TimeZone timeZone = calendar.getTimeZone();
            if (!timeZone.useDaylightTime() || calendar.get(16) == 0) {
                stringBuffer.append(FastDateFormat.getTimeZoneDisplay(timeZone, false, this.mStyle, this.mLocale));
            } else {
                stringBuffer.append(FastDateFormat.getTimeZoneDisplay(timeZone, true, this.mStyle, this.mLocale));
            }
        }

        @Override // org.apache.commons.lang.time.FastDateFormat.Rule
        public int estimateLength() {
            return this.mTimeZoneForced ? Math.max(this.mStandard.length(), this.mDaylight.length()) : this.mStyle == 0 ? 4 : 40;
        }
    }

    private static class TimeZoneNumberRule implements Rule {

        /* renamed from: b, reason: collision with root package name */
        static final TimeZoneNumberRule f26645b = new TimeZoneNumberRule(true);

        /* renamed from: c, reason: collision with root package name */
        static final TimeZoneNumberRule f26646c = new TimeZoneNumberRule(false);

        /* renamed from: a, reason: collision with root package name */
        final boolean f26647a;

        TimeZoneNumberRule(boolean z2) {
            this.f26647a = z2;
        }

        @Override // org.apache.commons.lang.time.FastDateFormat.Rule
        public void appendTo(StringBuffer stringBuffer, Calendar calendar) {
            int i2 = calendar.get(15) + calendar.get(16);
            if (i2 < 0) {
                stringBuffer.append('-');
                i2 = -i2;
            } else {
                stringBuffer.append('+');
            }
            int i3 = i2 / 3600000;
            stringBuffer.append((char) ((i3 / 10) + 48));
            stringBuffer.append((char) ((i3 % 10) + 48));
            if (this.f26647a) {
                stringBuffer.append(':');
            }
            int i4 = (i2 / 60000) - (i3 * 60);
            stringBuffer.append((char) ((i4 / 10) + 48));
            stringBuffer.append((char) ((i4 % 10) + 48));
        }

        @Override // org.apache.commons.lang.time.FastDateFormat.Rule
        public int estimateLength() {
            return 5;
        }
    }

    private static class TwoDigitMonthField implements NumberRule {

        /* renamed from: a, reason: collision with root package name */
        static final TwoDigitMonthField f26648a = new TwoDigitMonthField();

        TwoDigitMonthField() {
        }

        @Override // org.apache.commons.lang.time.FastDateFormat.Rule
        public void appendTo(StringBuffer stringBuffer, Calendar calendar) {
            appendTo(stringBuffer, calendar.get(2) + 1);
        }

        @Override // org.apache.commons.lang.time.FastDateFormat.Rule
        public int estimateLength() {
            return 2;
        }

        @Override // org.apache.commons.lang.time.FastDateFormat.NumberRule
        public final void appendTo(StringBuffer stringBuffer, int i2) {
            stringBuffer.append((char) ((i2 / 10) + 48));
            stringBuffer.append((char) ((i2 % 10) + 48));
        }
    }

    private static class TwoDigitNumberField implements NumberRule {
        private final int mField;

        TwoDigitNumberField(int i2) {
            this.mField = i2;
        }

        @Override // org.apache.commons.lang.time.FastDateFormat.Rule
        public void appendTo(StringBuffer stringBuffer, Calendar calendar) {
            appendTo(stringBuffer, calendar.get(this.mField));
        }

        @Override // org.apache.commons.lang.time.FastDateFormat.Rule
        public int estimateLength() {
            return 2;
        }

        @Override // org.apache.commons.lang.time.FastDateFormat.NumberRule
        public final void appendTo(StringBuffer stringBuffer, int i2) {
            if (i2 >= 100) {
                stringBuffer.append(Integer.toString(i2));
            } else {
                stringBuffer.append((char) ((i2 / 10) + 48));
                stringBuffer.append((char) ((i2 % 10) + 48));
            }
        }
    }

    private static class TwoDigitYearField implements NumberRule {

        /* renamed from: a, reason: collision with root package name */
        static final TwoDigitYearField f26649a = new TwoDigitYearField();

        TwoDigitYearField() {
        }

        @Override // org.apache.commons.lang.time.FastDateFormat.Rule
        public void appendTo(StringBuffer stringBuffer, Calendar calendar) {
            appendTo(stringBuffer, calendar.get(1) % 100);
        }

        @Override // org.apache.commons.lang.time.FastDateFormat.Rule
        public int estimateLength() {
            return 2;
        }

        @Override // org.apache.commons.lang.time.FastDateFormat.NumberRule
        public final void appendTo(StringBuffer stringBuffer, int i2) {
            stringBuffer.append((char) ((i2 / 10) + 48));
            stringBuffer.append((char) ((i2 % 10) + 48));
        }
    }

    private static class UnpaddedMonthField implements NumberRule {

        /* renamed from: a, reason: collision with root package name */
        static final UnpaddedMonthField f26650a = new UnpaddedMonthField();

        UnpaddedMonthField() {
        }

        @Override // org.apache.commons.lang.time.FastDateFormat.Rule
        public void appendTo(StringBuffer stringBuffer, Calendar calendar) {
            appendTo(stringBuffer, calendar.get(2) + 1);
        }

        @Override // org.apache.commons.lang.time.FastDateFormat.Rule
        public int estimateLength() {
            return 2;
        }

        @Override // org.apache.commons.lang.time.FastDateFormat.NumberRule
        public final void appendTo(StringBuffer stringBuffer, int i2) {
            if (i2 < 10) {
                stringBuffer.append((char) (i2 + 48));
            } else {
                stringBuffer.append((char) ((i2 / 10) + 48));
                stringBuffer.append((char) ((i2 % 10) + 48));
            }
        }
    }

    private static class UnpaddedNumberField implements NumberRule {
        private final int mField;

        UnpaddedNumberField(int i2) {
            this.mField = i2;
        }

        @Override // org.apache.commons.lang.time.FastDateFormat.Rule
        public void appendTo(StringBuffer stringBuffer, Calendar calendar) {
            appendTo(stringBuffer, calendar.get(this.mField));
        }

        @Override // org.apache.commons.lang.time.FastDateFormat.Rule
        public int estimateLength() {
            return 4;
        }

        @Override // org.apache.commons.lang.time.FastDateFormat.NumberRule
        public final void appendTo(StringBuffer stringBuffer, int i2) {
            if (i2 < 10) {
                stringBuffer.append((char) (i2 + 48));
            } else if (i2 >= 100) {
                stringBuffer.append(Integer.toString(i2));
            } else {
                stringBuffer.append((char) ((i2 / 10) + 48));
                stringBuffer.append((char) ((i2 % 10) + 48));
            }
        }
    }

    protected FastDateFormat(String str, TimeZone timeZone, Locale locale) {
        if (str == null) {
            throw new IllegalArgumentException("The pattern must not be null");
        }
        this.mPattern = str;
        this.mTimeZoneForced = timeZone != null;
        this.mTimeZone = timeZone == null ? TimeZone.getDefault() : timeZone;
        this.mLocaleForced = locale != null;
        this.mLocale = locale == null ? Locale.getDefault() : locale;
    }

    public static FastDateFormat getDateInstance(int i2) {
        return getDateInstance(i2, null, null);
    }

    public static FastDateFormat getDateTimeInstance(int i2, int i3) {
        return getDateTimeInstance(i2, i3, null, null);
    }

    private static synchronized String getDefaultPattern() {
        try {
            if (cDefaultPattern == null) {
                cDefaultPattern = new SimpleDateFormat().toPattern();
            }
        } catch (Throwable th) {
            throw th;
        }
        return cDefaultPattern;
    }

    public static FastDateFormat getInstance() {
        return getInstance(getDefaultPattern(), null, null);
    }

    public static FastDateFormat getTimeInstance(int i2) {
        return getTimeInstance(i2, null, null);
    }

    static synchronized String getTimeZoneDisplay(TimeZone timeZone, boolean z2, int i2, Locale locale) {
        String displayName;
        TimeZoneDisplayKey timeZoneDisplayKey = new TimeZoneDisplayKey(timeZone, z2, i2, locale);
        Map map = cTimeZoneDisplayCache;
        displayName = (String) map.get(timeZoneDisplayKey);
        if (displayName == null) {
            displayName = timeZone.getDisplayName(z2, i2, locale);
            map.put(timeZoneDisplayKey, displayName);
        }
        return displayName;
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        init();
    }

    protected StringBuffer applyRules(Calendar calendar, StringBuffer stringBuffer) {
        for (Rule rule : this.mRules) {
            rule.appendTo(stringBuffer, calendar);
        }
        return stringBuffer;
    }

    public boolean equals(Object obj) {
        TimeZone timeZone;
        TimeZone timeZone2;
        Locale locale;
        Locale locale2;
        if (!(obj instanceof FastDateFormat)) {
            return false;
        }
        FastDateFormat fastDateFormat = (FastDateFormat) obj;
        String str = this.mPattern;
        String str2 = fastDateFormat.mPattern;
        return (str == str2 || str.equals(str2)) && ((timeZone = this.mTimeZone) == (timeZone2 = fastDateFormat.mTimeZone) || timeZone.equals(timeZone2)) && (((locale = this.mLocale) == (locale2 = fastDateFormat.mLocale) || locale.equals(locale2)) && this.mTimeZoneForced == fastDateFormat.mTimeZoneForced && this.mLocaleForced == fastDateFormat.mLocaleForced);
    }

    @Override // java.text.Format
    public StringBuffer format(Object obj, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        if (obj instanceof Date) {
            return format((Date) obj, stringBuffer);
        }
        if (obj instanceof Calendar) {
            return format((Calendar) obj, stringBuffer);
        }
        if (obj instanceof Long) {
            return format(((Long) obj).longValue(), stringBuffer);
        }
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("Unknown class: ");
        stringBuffer2.append(obj == null ? "<null>" : obj.getClass().getName());
        throw new IllegalArgumentException(stringBuffer2.toString());
    }

    public Locale getLocale() {
        return this.mLocale;
    }

    public int getMaxLengthEstimate() {
        return this.mMaxLengthEstimate;
    }

    public String getPattern() {
        return this.mPattern;
    }

    public TimeZone getTimeZone() {
        return this.mTimeZone;
    }

    public boolean getTimeZoneOverridesCalendar() {
        return this.mTimeZoneForced;
    }

    public int hashCode() {
        return this.mPattern.hashCode() + this.mTimeZone.hashCode() + (this.mTimeZoneForced ? 1 : 0) + this.mLocale.hashCode() + (this.mLocaleForced ? 1 : 0);
    }

    protected void init() {
        List pattern = parsePattern();
        Rule[] ruleArr = (Rule[]) pattern.toArray(new Rule[pattern.size()]);
        this.mRules = ruleArr;
        int length = ruleArr.length;
        int iEstimateLength = 0;
        while (true) {
            length--;
            if (length < 0) {
                this.mMaxLengthEstimate = iEstimateLength;
                return;
            }
            iEstimateLength += this.mRules[length].estimateLength();
        }
    }

    @Override // java.text.Format
    public Object parseObject(String str, ParsePosition parsePosition) {
        parsePosition.setIndex(0);
        parsePosition.setErrorIndex(0);
        return null;
    }

    protected List parsePattern() {
        int i2;
        Rule ruleSelectNumberRule;
        DateFormatSymbols dateFormatSymbols = new DateFormatSymbols(this.mLocale);
        ArrayList arrayList = new ArrayList();
        String[] eras = dateFormatSymbols.getEras();
        String[] months = dateFormatSymbols.getMonths();
        String[] shortMonths = dateFormatSymbols.getShortMonths();
        String[] weekdays = dateFormatSymbols.getWeekdays();
        String[] shortWeekdays = dateFormatSymbols.getShortWeekdays();
        String[] amPmStrings = dateFormatSymbols.getAmPmStrings();
        int length = this.mPattern.length();
        int i3 = 0;
        while (i3 < length) {
            int[] iArr = {i3};
            String token = parseToken(this.mPattern, iArr);
            int i4 = iArr[0];
            int length2 = token.length();
            if (length2 == 0) {
                return arrayList;
            }
            char cCharAt = token.charAt(0);
            if (cCharAt == 'y') {
                i2 = 1;
                ruleSelectNumberRule = length2 >= 4 ? selectNumberRule(1, length2) : TwoDigitYearField.f26649a;
            } else if (cCharAt != 'z') {
                switch (cCharAt) {
                    case '\'':
                        String strSubstring = token.substring(1);
                        ruleSelectNumberRule = strSubstring.length() == 1 ? new CharacterLiteral(strSubstring.charAt(0)) : new StringLiteral(strSubstring);
                        break;
                    case 'K':
                        ruleSelectNumberRule = selectNumberRule(10, length2);
                        break;
                    case 'M':
                        if (length2 < 4) {
                            if (length2 != 3) {
                                if (length2 != 2) {
                                    ruleSelectNumberRule = UnpaddedMonthField.f26650a;
                                    break;
                                } else {
                                    ruleSelectNumberRule = TwoDigitMonthField.f26648a;
                                    break;
                                }
                            } else {
                                ruleSelectNumberRule = new TextField(2, shortMonths);
                                break;
                            }
                        } else {
                            ruleSelectNumberRule = new TextField(2, months);
                            break;
                        }
                    case 'S':
                        ruleSelectNumberRule = selectNumberRule(14, length2);
                        break;
                    case 'W':
                        ruleSelectNumberRule = selectNumberRule(4, length2);
                        break;
                    case 'Z':
                        if (length2 != 1) {
                            ruleSelectNumberRule = TimeZoneNumberRule.f26645b;
                            break;
                        } else {
                            ruleSelectNumberRule = TimeZoneNumberRule.f26646c;
                            break;
                        }
                    case 'a':
                        ruleSelectNumberRule = new TextField(9, amPmStrings);
                        break;
                    case 'd':
                        ruleSelectNumberRule = selectNumberRule(5, length2);
                        break;
                    case 'h':
                        ruleSelectNumberRule = new TwelveHourField(selectNumberRule(10, length2));
                        break;
                    case 'k':
                        ruleSelectNumberRule = new TwentyFourHourField(selectNumberRule(11, length2));
                        break;
                    case 'm':
                        ruleSelectNumberRule = selectNumberRule(12, length2);
                        break;
                    case 's':
                        ruleSelectNumberRule = selectNumberRule(13, length2);
                        break;
                    case 'w':
                        ruleSelectNumberRule = selectNumberRule(3, length2);
                        break;
                    default:
                        switch (cCharAt) {
                            case 'D':
                                ruleSelectNumberRule = selectNumberRule(6, length2);
                                break;
                            case 'E':
                                ruleSelectNumberRule = new TextField(7, length2 < 4 ? shortWeekdays : weekdays);
                                break;
                            case 'F':
                                ruleSelectNumberRule = selectNumberRule(8, length2);
                                break;
                            case 'G':
                                ruleSelectNumberRule = new TextField(0, eras);
                                break;
                            case 'H':
                                ruleSelectNumberRule = selectNumberRule(11, length2);
                                break;
                            default:
                                StringBuffer stringBuffer = new StringBuffer();
                                stringBuffer.append("Illegal pattern component: ");
                                stringBuffer.append(token);
                                throw new IllegalArgumentException(stringBuffer.toString());
                        }
                }
                i2 = 1;
            } else if (length2 >= 4) {
                i2 = 1;
                ruleSelectNumberRule = new TimeZoneNameRule(this.mTimeZone, this.mTimeZoneForced, this.mLocale, 1);
            } else {
                i2 = 1;
                ruleSelectNumberRule = new TimeZoneNameRule(this.mTimeZone, this.mTimeZoneForced, this.mLocale, 0);
            }
            arrayList.add(ruleSelectNumberRule);
            i3 = i4 + i2;
        }
        return arrayList;
    }

    protected String parseToken(String str, int[] iArr) {
        StrBuilder strBuilder = new StrBuilder();
        int i2 = iArr[0];
        int length = str.length();
        char cCharAt = str.charAt(i2);
        if ((cCharAt < 'A' || cCharAt > 'Z') && (cCharAt < 'a' || cCharAt > 'z')) {
            strBuilder.append('\'');
            boolean z2 = false;
            while (i2 < length) {
                char cCharAt2 = str.charAt(i2);
                if (cCharAt2 != '\'') {
                    if (!z2 && ((cCharAt2 >= 'A' && cCharAt2 <= 'Z') || (cCharAt2 >= 'a' && cCharAt2 <= 'z'))) {
                        i2--;
                        break;
                    }
                    strBuilder.append(cCharAt2);
                } else {
                    int i3 = i2 + 1;
                    if (i3 >= length || str.charAt(i3) != '\'') {
                        z2 = !z2;
                    } else {
                        strBuilder.append(cCharAt2);
                        i2 = i3;
                    }
                }
                i2++;
            }
        } else {
            strBuilder.append(cCharAt);
            while (true) {
                int i4 = i2 + 1;
                if (i4 >= length || str.charAt(i4) != cCharAt) {
                    break;
                }
                strBuilder.append(cCharAt);
                i2 = i4;
            }
        }
        iArr[0] = i2;
        return strBuilder.toString();
    }

    protected NumberRule selectNumberRule(int i2, int i3) {
        return i3 != 1 ? i3 != 2 ? new PaddedNumberField(i2, i3) : new TwoDigitNumberField(i2) : new UnpaddedNumberField(i2);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("FastDateFormat[");
        stringBuffer.append(this.mPattern);
        stringBuffer.append("]");
        return stringBuffer.toString();
    }

    public static FastDateFormat getDateInstance(int i2, Locale locale) {
        return getDateInstance(i2, null, locale);
    }

    public static FastDateFormat getDateTimeInstance(int i2, int i3, Locale locale) {
        return getDateTimeInstance(i2, i3, null, locale);
    }

    public static FastDateFormat getInstance(String str) {
        return getInstance(str, null, null);
    }

    public static FastDateFormat getTimeInstance(int i2, Locale locale) {
        return getTimeInstance(i2, null, locale);
    }

    private static class TwelveHourField implements NumberRule {
        private final NumberRule mRule;

        TwelveHourField(NumberRule numberRule) {
            this.mRule = numberRule;
        }

        @Override // org.apache.commons.lang.time.FastDateFormat.Rule
        public void appendTo(StringBuffer stringBuffer, Calendar calendar) {
            int leastMaximum = calendar.get(10);
            if (leastMaximum == 0) {
                leastMaximum = calendar.getLeastMaximum(10) + 1;
            }
            this.mRule.appendTo(stringBuffer, leastMaximum);
        }

        @Override // org.apache.commons.lang.time.FastDateFormat.Rule
        public int estimateLength() {
            return this.mRule.estimateLength();
        }

        @Override // org.apache.commons.lang.time.FastDateFormat.NumberRule
        public void appendTo(StringBuffer stringBuffer, int i2) {
            this.mRule.appendTo(stringBuffer, i2);
        }
    }

    private static class TwentyFourHourField implements NumberRule {
        private final NumberRule mRule;

        TwentyFourHourField(NumberRule numberRule) {
            this.mRule = numberRule;
        }

        @Override // org.apache.commons.lang.time.FastDateFormat.Rule
        public void appendTo(StringBuffer stringBuffer, Calendar calendar) {
            int maximum = calendar.get(11);
            if (maximum == 0) {
                maximum = calendar.getMaximum(11) + 1;
            }
            this.mRule.appendTo(stringBuffer, maximum);
        }

        @Override // org.apache.commons.lang.time.FastDateFormat.Rule
        public int estimateLength() {
            return this.mRule.estimateLength();
        }

        @Override // org.apache.commons.lang.time.FastDateFormat.NumberRule
        public void appendTo(StringBuffer stringBuffer, int i2) {
            this.mRule.appendTo(stringBuffer, i2);
        }
    }

    public static FastDateFormat getDateInstance(int i2, TimeZone timeZone) {
        return getDateInstance(i2, timeZone, null);
    }

    public static FastDateFormat getDateTimeInstance(int i2, int i3, TimeZone timeZone) {
        return getDateTimeInstance(i2, i3, timeZone, null);
    }

    public static FastDateFormat getInstance(String str, TimeZone timeZone) {
        return getInstance(str, timeZone, null);
    }

    public static FastDateFormat getTimeInstance(int i2, TimeZone timeZone) {
        return getTimeInstance(i2, timeZone, null);
    }

    public static synchronized FastDateFormat getDateInstance(int i2, TimeZone timeZone, Locale locale) {
        FastDateFormat fastDateFormat;
        try {
            Object num = new Integer(i2);
            if (timeZone != null) {
                num = new Pair(num, timeZone);
            }
            if (locale == null) {
                locale = Locale.getDefault();
            }
            Pair pair = new Pair(num, locale);
            Map map = cDateInstanceCache;
            fastDateFormat = (FastDateFormat) map.get(pair);
            if (fastDateFormat == null) {
                try {
                    fastDateFormat = getInstance(((SimpleDateFormat) DateFormat.getDateInstance(i2, locale)).toPattern(), timeZone, locale);
                    map.put(pair, fastDateFormat);
                } catch (ClassCastException unused) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("No date pattern for locale: ");
                    stringBuffer.append(locale);
                    throw new IllegalArgumentException(stringBuffer.toString());
                }
            }
        } catch (Throwable th) {
            throw th;
        }
        return fastDateFormat;
    }

    public static synchronized FastDateFormat getDateTimeInstance(int i2, int i3, TimeZone timeZone, Locale locale) {
        FastDateFormat fastDateFormat;
        try {
            Pair pair = new Pair(new Integer(i2), new Integer(i3));
            if (timeZone != null) {
                pair = new Pair(pair, timeZone);
            }
            if (locale == null) {
                locale = Locale.getDefault();
            }
            Pair pair2 = new Pair(pair, locale);
            Map map = cDateTimeInstanceCache;
            fastDateFormat = (FastDateFormat) map.get(pair2);
            if (fastDateFormat == null) {
                try {
                    fastDateFormat = getInstance(((SimpleDateFormat) DateFormat.getDateTimeInstance(i2, i3, locale)).toPattern(), timeZone, locale);
                    map.put(pair2, fastDateFormat);
                } catch (ClassCastException unused) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("No date time pattern for locale: ");
                    stringBuffer.append(locale);
                    throw new IllegalArgumentException(stringBuffer.toString());
                }
            }
        } catch (Throwable th) {
            throw th;
        }
        return fastDateFormat;
    }

    public static FastDateFormat getInstance(String str, Locale locale) {
        return getInstance(str, null, locale);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v1, types: [org.apache.commons.lang.time.FastDateFormat$Pair] */
    /* JADX WARN: Type inference failed for: r2v2, types: [org.apache.commons.lang.time.FastDateFormat$Pair] */
    public static synchronized FastDateFormat getTimeInstance(int i2, TimeZone timeZone, Locale locale) {
        FastDateFormat fastDateFormat;
        try {
            Integer num = new Integer(i2);
            if (timeZone != null) {
                num = new Pair(num, timeZone);
            }
            if (locale != null) {
                num = new Pair(num, locale);
            }
            Map map = cTimeInstanceCache;
            fastDateFormat = (FastDateFormat) map.get(num);
            if (fastDateFormat == null) {
                if (locale == null) {
                    locale = Locale.getDefault();
                }
                try {
                    fastDateFormat = getInstance(((SimpleDateFormat) DateFormat.getTimeInstance(i2, locale)).toPattern(), timeZone, locale);
                    map.put(num, fastDateFormat);
                } catch (ClassCastException unused) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("No date pattern for locale: ");
                    stringBuffer.append(locale);
                    throw new IllegalArgumentException(stringBuffer.toString());
                }
            }
        } catch (Throwable th) {
            throw th;
        }
        return fastDateFormat;
    }

    public static synchronized FastDateFormat getInstance(String str, TimeZone timeZone, Locale locale) {
        FastDateFormat fastDateFormat;
        fastDateFormat = new FastDateFormat(str, timeZone, locale);
        Map map = cInstanceCache;
        FastDateFormat fastDateFormat2 = (FastDateFormat) map.get(fastDateFormat);
        if (fastDateFormat2 == null) {
            fastDateFormat.init();
            map.put(fastDateFormat, fastDateFormat);
        } else {
            fastDateFormat = fastDateFormat2;
        }
        return fastDateFormat;
    }

    public String format(long j2) {
        return format(new Date(j2));
    }

    public String format(Date date) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar(this.mTimeZone, this.mLocale);
        gregorianCalendar.setTime(date);
        return applyRules(gregorianCalendar, new StringBuffer(this.mMaxLengthEstimate)).toString();
    }

    public String format(Calendar calendar) {
        return format(calendar, new StringBuffer(this.mMaxLengthEstimate)).toString();
    }

    public StringBuffer format(long j2, StringBuffer stringBuffer) {
        return format(new Date(j2), stringBuffer);
    }

    public StringBuffer format(Date date, StringBuffer stringBuffer) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar(this.mTimeZone);
        gregorianCalendar.setTime(date);
        return applyRules(gregorianCalendar, stringBuffer);
    }

    public StringBuffer format(Calendar calendar, StringBuffer stringBuffer) {
        if (this.mTimeZoneForced) {
            calendar.getTime();
            calendar = (Calendar) calendar.clone();
            calendar.setTimeZone(this.mTimeZone);
        }
        return applyRules(calendar, stringBuffer);
    }
}
