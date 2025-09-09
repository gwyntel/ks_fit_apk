package org.apache.commons.lang.time;

import androidx.exifinterface.media.ExifInterface;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrBuilder;

/* loaded from: classes5.dex */
public class DurationFormatUtils {
    public static final String ISO_EXTENDED_FORMAT_PATTERN = "'P'yyyy'Y'M'M'd'DT'H'H'm'M's.S'S'";

    /* renamed from: a, reason: collision with root package name */
    static final Object f26638a = "y";

    /* renamed from: b, reason: collision with root package name */
    static final Object f26639b = "M";

    /* renamed from: c, reason: collision with root package name */
    static final Object f26640c = "d";

    /* renamed from: d, reason: collision with root package name */
    static final Object f26641d = "H";

    /* renamed from: e, reason: collision with root package name */
    static final Object f26642e = "m";

    /* renamed from: f, reason: collision with root package name */
    static final Object f26643f = "s";

    /* renamed from: g, reason: collision with root package name */
    static final Object f26644g = ExifInterface.LATITUDE_SOUTH;

    static class Token {
        private int count = 1;
        private Object value;

        Token(Object obj) {
            this.value = obj;
        }

        static boolean a(Token[] tokenArr, Object obj) {
            for (Token token : tokenArr) {
                if (token.c() == obj) {
                    return true;
                }
            }
            return false;
        }

        int b() {
            return this.count;
        }

        Object c() {
            return this.value;
        }

        void d() {
            this.count++;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof Token)) {
                return false;
            }
            Token token = (Token) obj;
            if (this.value.getClass() != token.value.getClass() || this.count != token.count) {
                return false;
            }
            Object obj2 = this.value;
            return obj2 instanceof StringBuffer ? obj2.toString().equals(token.value.toString()) : obj2 instanceof Number ? obj2.equals(token.value) : obj2 == token.value;
        }

        public int hashCode() {
            return this.value.hashCode();
        }

        public String toString() {
            return StringUtils.repeat(this.value.toString(), this.count);
        }
    }

    static String a(Token[] tokenArr, int i2, int i3, int i4, int i5, int i6, int i7, int i8, boolean z2) {
        StrBuilder strBuilder = new StrBuilder();
        int i9 = i8;
        boolean z3 = false;
        for (Token token : tokenArr) {
            Object objC = token.c();
            int iB = token.b();
            if (objC instanceof StringBuffer) {
                strBuilder.append(objC.toString());
            } else {
                if (objC == f26638a) {
                    String string = Integer.toString(i2);
                    if (z2) {
                        string = StringUtils.leftPad(string, iB, '0');
                    }
                    strBuilder.append(string);
                } else if (objC == f26639b) {
                    String string2 = Integer.toString(i3);
                    if (z2) {
                        string2 = StringUtils.leftPad(string2, iB, '0');
                    }
                    strBuilder.append(string2);
                } else if (objC == f26640c) {
                    String string3 = Integer.toString(i4);
                    if (z2) {
                        string3 = StringUtils.leftPad(string3, iB, '0');
                    }
                    strBuilder.append(string3);
                } else if (objC == f26641d) {
                    String string4 = Integer.toString(i5);
                    if (z2) {
                        string4 = StringUtils.leftPad(string4, iB, '0');
                    }
                    strBuilder.append(string4);
                } else if (objC == f26642e) {
                    String string5 = Integer.toString(i6);
                    if (z2) {
                        string5 = StringUtils.leftPad(string5, iB, '0');
                    }
                    strBuilder.append(string5);
                } else if (objC == f26643f) {
                    String string6 = Integer.toString(i7);
                    if (z2) {
                        string6 = StringUtils.leftPad(string6, iB, '0');
                    }
                    strBuilder.append(string6);
                    z3 = true;
                } else if (objC == f26644g) {
                    if (z3) {
                        i9 += 1000;
                        String string7 = Integer.toString(i9);
                        if (z2) {
                            string7 = StringUtils.leftPad(string7, iB, '0');
                        }
                        strBuilder.append(string7.substring(1));
                    } else {
                        String string8 = Integer.toString(i9);
                        if (z2) {
                            string8 = StringUtils.leftPad(string8, iB, '0');
                        }
                        strBuilder.append(string8);
                    }
                }
                z3 = false;
            }
        }
        return strBuilder.toString();
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x007e  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0093 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static org.apache.commons.lang.time.DurationFormatUtils.Token[] b(java.lang.String r10) {
        /*
            char[] r10 = r10.toCharArray()
            java.util.ArrayList r0 = new java.util.ArrayList
            int r1 = r10.length
            r0.<init>(r1)
            int r1 = r10.length
            r2 = 0
            r3 = 0
            r4 = r2
            r5 = r4
            r6 = r3
            r7 = r6
        L11:
            if (r4 >= r1) goto L97
            char r8 = r10[r4]
            r9 = 39
            if (r5 == 0) goto L20
            if (r8 == r9) goto L20
            r6.append(r8)
            goto L93
        L20:
            if (r8 == r9) goto L67
            r9 = 72
            if (r8 == r9) goto L64
            r9 = 77
            if (r8 == r9) goto L61
            r9 = 83
            if (r8 == r9) goto L5e
            r9 = 100
            if (r8 == r9) goto L5b
            r9 = 109(0x6d, float:1.53E-43)
            if (r8 == r9) goto L58
            r9 = 115(0x73, float:1.61E-43)
            if (r8 == r9) goto L55
            r9 = 121(0x79, float:1.7E-43)
            if (r8 == r9) goto L52
            if (r6 != 0) goto L4d
            java.lang.StringBuffer r6 = new java.lang.StringBuffer
            r6.<init>()
            org.apache.commons.lang.time.DurationFormatUtils$Token r9 = new org.apache.commons.lang.time.DurationFormatUtils$Token
            r9.<init>(r6)
            r0.add(r9)
        L4d:
            r6.append(r8)
        L50:
            r8 = r3
            goto L7c
        L52:
            java.lang.Object r8 = org.apache.commons.lang.time.DurationFormatUtils.f26638a
            goto L7c
        L55:
            java.lang.Object r8 = org.apache.commons.lang.time.DurationFormatUtils.f26643f
            goto L7c
        L58:
            java.lang.Object r8 = org.apache.commons.lang.time.DurationFormatUtils.f26642e
            goto L7c
        L5b:
            java.lang.Object r8 = org.apache.commons.lang.time.DurationFormatUtils.f26640c
            goto L7c
        L5e:
            java.lang.Object r8 = org.apache.commons.lang.time.DurationFormatUtils.f26644g
            goto L7c
        L61:
            java.lang.Object r8 = org.apache.commons.lang.time.DurationFormatUtils.f26639b
            goto L7c
        L64:
            java.lang.Object r8 = org.apache.commons.lang.time.DurationFormatUtils.f26641d
            goto L7c
        L67:
            if (r5 == 0) goto L6d
            r5 = r2
            r6 = r3
            r8 = r6
            goto L7c
        L6d:
            java.lang.StringBuffer r6 = new java.lang.StringBuffer
            r6.<init>()
            org.apache.commons.lang.time.DurationFormatUtils$Token r5 = new org.apache.commons.lang.time.DurationFormatUtils$Token
            r5.<init>(r6)
            r0.add(r5)
            r5 = 1
            goto L50
        L7c:
            if (r8 == 0) goto L93
            if (r7 == 0) goto L8a
            java.lang.Object r6 = r7.c()
            if (r6 != r8) goto L8a
            r7.d()
            goto L92
        L8a:
            org.apache.commons.lang.time.DurationFormatUtils$Token r7 = new org.apache.commons.lang.time.DurationFormatUtils$Token
            r7.<init>(r8)
            r0.add(r7)
        L92:
            r6 = r3
        L93:
            int r4 = r4 + 1
            goto L11
        L97:
            int r10 = r0.size()
            org.apache.commons.lang.time.DurationFormatUtils$Token[] r10 = new org.apache.commons.lang.time.DurationFormatUtils.Token[r10]
            java.lang.Object[] r10 = r0.toArray(r10)
            org.apache.commons.lang.time.DurationFormatUtils$Token[] r10 = (org.apache.commons.lang.time.DurationFormatUtils.Token[]) r10
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.lang.time.DurationFormatUtils.b(java.lang.String):org.apache.commons.lang.time.DurationFormatUtils$Token[]");
    }

    public static String formatDuration(long j2, String str) {
        return formatDuration(j2, str, true);
    }

    public static String formatDurationHMS(long j2) {
        return formatDuration(j2, "H:mm:ss.SSS");
    }

    public static String formatDurationISO(long j2) {
        return formatDuration(j2, ISO_EXTENDED_FORMAT_PATTERN, false);
    }

    public static String formatDurationWords(long j2, boolean z2, boolean z3) {
        String duration = formatDuration(j2, "d' days 'H' hours 'm' minutes 's' seconds'");
        if (z2) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(" ");
            stringBuffer.append(duration);
            duration = stringBuffer.toString();
            String strReplaceOnce = StringUtils.replaceOnce(duration, " 0 days", "");
            if (strReplaceOnce.length() != duration.length()) {
                String strReplaceOnce2 = StringUtils.replaceOnce(strReplaceOnce, " 0 hours", "");
                if (strReplaceOnce2.length() != strReplaceOnce.length()) {
                    duration = StringUtils.replaceOnce(strReplaceOnce2, " 0 minutes", "");
                    if (duration.length() != duration.length()) {
                        duration = StringUtils.replaceOnce(duration, " 0 seconds", "");
                    }
                } else {
                    duration = strReplaceOnce;
                }
            }
            if (duration.length() != 0) {
                duration = duration.substring(1);
            }
        }
        if (z3) {
            String strReplaceOnce3 = StringUtils.replaceOnce(duration, " 0 seconds", "");
            if (strReplaceOnce3.length() != duration.length()) {
                duration = StringUtils.replaceOnce(strReplaceOnce3, " 0 minutes", "");
                if (duration.length() != strReplaceOnce3.length()) {
                    String strReplaceOnce4 = StringUtils.replaceOnce(duration, " 0 hours", "");
                    if (strReplaceOnce4.length() != duration.length()) {
                        duration = StringUtils.replaceOnce(strReplaceOnce4, " 0 days", "");
                    }
                } else {
                    duration = strReplaceOnce3;
                }
            }
        }
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append(" ");
        stringBuffer2.append(duration);
        return StringUtils.replaceOnce(StringUtils.replaceOnce(StringUtils.replaceOnce(StringUtils.replaceOnce(stringBuffer2.toString(), " 1 seconds", " 1 second"), " 1 minutes", " 1 minute"), " 1 hours", " 1 hour"), " 1 days", " 1 day").trim();
    }

    public static String formatPeriod(long j2, long j3, String str) {
        return formatPeriod(j2, j3, str, true, TimeZone.getDefault());
    }

    public static String formatPeriodISO(long j2, long j3) {
        return formatPeriod(j2, j3, ISO_EXTENDED_FORMAT_PATTERN, false, TimeZone.getDefault());
    }

    public static String formatDuration(long j2, String str, boolean z2) {
        int i2;
        int i3;
        int i4;
        int i5;
        Token[] tokenArrB = b(str);
        if (Token.a(tokenArrB, f26640c)) {
            int i6 = (int) (j2 / 86400000);
            j2 -= i6 * 86400000;
            i2 = i6;
        } else {
            i2 = 0;
        }
        if (Token.a(tokenArrB, f26641d)) {
            int i7 = (int) (j2 / 3600000);
            j2 -= i7 * 3600000;
            i3 = i7;
        } else {
            i3 = 0;
        }
        if (Token.a(tokenArrB, f26642e)) {
            int i8 = (int) (j2 / 60000);
            j2 -= i8 * 60000;
            i4 = i8;
        } else {
            i4 = 0;
        }
        if (Token.a(tokenArrB, f26643f)) {
            int i9 = (int) (j2 / 1000);
            j2 -= i9 * 1000;
            i5 = i9;
        } else {
            i5 = 0;
        }
        return a(tokenArrB, 0, 0, i2, i3, i4, i5, Token.a(tokenArrB, f26644g) ? (int) j2 : 0, z2);
    }

    public static String formatPeriod(long j2, long j3, String str, boolean z2, TimeZone timeZone) {
        int i2;
        int i3;
        int i4;
        Token[] tokenArrB = b(str);
        Calendar calendar = Calendar.getInstance(timeZone);
        calendar.setTime(new Date(j2));
        Calendar calendar2 = Calendar.getInstance(timeZone);
        calendar2.setTime(new Date(j3));
        int i5 = calendar2.get(14) - calendar.get(14);
        int i6 = calendar2.get(13) - calendar.get(13);
        int i7 = calendar2.get(12) - calendar.get(12);
        int i8 = calendar2.get(11) - calendar.get(11);
        int actualMaximum = calendar2.get(5) - calendar.get(5);
        int i9 = calendar2.get(2) - calendar.get(2);
        int i10 = calendar2.get(1) - calendar.get(1);
        while (i5 < 0) {
            i5 += 1000;
            i6--;
        }
        while (i6 < 0) {
            i6 += 60;
            i7--;
        }
        while (i7 < 0) {
            i7 += 60;
            i8--;
        }
        while (i8 < 0) {
            i8 += 24;
            actualMaximum--;
        }
        int i11 = 0;
        if (Token.a(tokenArrB, f26639b)) {
            while (actualMaximum < 0) {
                actualMaximum += calendar.getActualMaximum(5);
                i9--;
                calendar.add(2, 1);
            }
            while (i9 < 0) {
                i9 += 12;
                i10--;
            }
            if (!Token.a(tokenArrB, f26638a) && i10 != 0) {
                while (i10 != 0) {
                    i9 += i10 * 12;
                    i10 = 0;
                }
            }
            i2 = i9;
        } else {
            if (!Token.a(tokenArrB, f26638a)) {
                int i12 = calendar2.get(1);
                if (i9 < 0) {
                    i12--;
                }
                while (calendar.get(1) != i12) {
                    int actualMaximum2 = actualMaximum + (calendar.getActualMaximum(6) - calendar.get(6));
                    if ((calendar instanceof GregorianCalendar) && calendar.get(2) == 1 && calendar.get(5) == 29) {
                        actualMaximum2++;
                    }
                    calendar.add(1, 1);
                    actualMaximum = actualMaximum2 + calendar.get(6);
                }
                i10 = 0;
            }
            while (calendar.get(2) != calendar2.get(2)) {
                actualMaximum += calendar.getActualMaximum(5);
                calendar.add(2, 1);
            }
            i2 = 0;
            while (actualMaximum < 0) {
                actualMaximum += calendar.getActualMaximum(5);
                i2--;
                calendar.add(2, 1);
            }
        }
        int i13 = i10;
        if (Token.a(tokenArrB, f26640c)) {
            i3 = actualMaximum;
        } else {
            i8 += actualMaximum * 24;
            i3 = 0;
        }
        if (!Token.a(tokenArrB, f26641d)) {
            i7 += i8 * 60;
            i8 = 0;
        }
        if (!Token.a(tokenArrB, f26642e)) {
            i6 += i7 * 60;
            i7 = 0;
        }
        if (Token.a(tokenArrB, f26643f)) {
            i4 = i5;
            i11 = i6;
        } else {
            i4 = i5 + (i6 * 1000);
        }
        return a(tokenArrB, i13, i2, i3, i8, i7, i11, i4, z2);
    }
}
