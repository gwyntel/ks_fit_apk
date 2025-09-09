package org.joda.time.field;

import java.util.Locale;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DurationField;
import org.joda.time.IllegalFieldValueException;
import org.joda.time.ReadablePartial;

/* loaded from: classes5.dex */
public abstract class BaseDateTimeField extends DateTimeField {
    private final DateTimeFieldType iType;

    protected BaseDateTimeField(DateTimeFieldType dateTimeFieldType) {
        if (dateTimeFieldType == null) {
            throw new IllegalArgumentException("The type must not be null");
        }
        this.iType = dateTimeFieldType;
    }

    protected int a(String str, Locale locale) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException unused) {
            throw new IllegalFieldValueException(getType(), str);
        }
    }

    @Override // org.joda.time.DateTimeField
    public long add(long j2, int i2) {
        return getDurationField().add(j2, i2);
    }

    @Override // org.joda.time.DateTimeField
    public long addWrapField(long j2, int i2) {
        return set(j2, FieldUtils.getWrappedValue(get(j2), i2, getMinimumValue(j2), getMaximumValue(j2)));
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x005c, code lost:
    
        if (r12 >= 0) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x005e, code lost:
    
        r2 = getMinimumValue(r9, r11);
        r3 = r11[r10];
        r4 = r3 + r12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x006a, code lost:
    
        if (r4 < r2) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x006c, code lost:
    
        r11[r10] = (int) r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0070, code lost:
    
        if (r0 != null) goto L45;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0072, code lost:
    
        if (r10 != 0) goto L46;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0074, code lost:
    
        r12 = r12 - ((r2 - 1) - r3);
        r11[r10] = getMaximumValue(r9, r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x007f, code lost:
    
        r0 = r9.getField(r10 - 1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0095, code lost:
    
        if (getRangeDurationField().getType() != r0.getDurationField().getType()) goto L48;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x009d, code lost:
    
        throw new java.lang.IllegalArgumentException("Fields invalid for add");
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x009e, code lost:
    
        r12 = r12 - ((r2 - 1) - r11[r10]);
        r11 = r0.addWrapPartial(r9, r10 - 1, r11, -1);
        r11[r10] = getMaximumValue(r9, r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x00b8, code lost:
    
        return set(r9, r10, r11, r11[r10]);
     */
    @Override // org.joda.time.DateTimeField
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int[] addWrapPartial(org.joda.time.ReadablePartial r9, int r10, int[] r11, int r12) {
        /*
            r8 = this;
            if (r12 != 0) goto L3
            return r11
        L3:
            r0 = 0
        L4:
            java.lang.String r1 = "Fields invalid for add"
            if (r12 <= 0) goto L5c
            int r2 = r8.getMaximumValue(r9, r11)
            r3 = r11[r10]
            int r4 = r3 + r12
            long r4 = (long) r4
            long r6 = (long) r2
            int r6 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r6 > 0) goto L1a
            int r2 = (int) r4
            r11[r10] = r2
            goto L5c
        L1a:
            if (r0 != 0) goto L48
            if (r10 != 0) goto L29
            int r2 = r2 + 1
            int r2 = r2 - r3
            int r12 = r12 - r2
            int r1 = r8.getMinimumValue(r9, r11)
            r11[r10] = r1
            goto L4
        L29:
            int r0 = r10 + (-1)
            org.joda.time.DateTimeField r0 = r9.getField(r0)
            org.joda.time.DurationField r3 = r8.getRangeDurationField()
            org.joda.time.DurationFieldType r3 = r3.getType()
            org.joda.time.DurationField r4 = r0.getDurationField()
            org.joda.time.DurationFieldType r4 = r4.getType()
            if (r3 != r4) goto L42
            goto L48
        L42:
            java.lang.IllegalArgumentException r9 = new java.lang.IllegalArgumentException
            r9.<init>(r1)
            throw r9
        L48:
            int r2 = r2 + 1
            r1 = r11[r10]
            int r2 = r2 - r1
            int r12 = r12 - r2
            int r1 = r10 + (-1)
            r2 = 1
            int[] r11 = r0.addWrapPartial(r9, r1, r11, r2)
            int r1 = r8.getMinimumValue(r9, r11)
            r11[r10] = r1
            goto L4
        L5c:
            if (r12 >= 0) goto Lb2
            int r2 = r8.getMinimumValue(r9, r11)
            r3 = r11[r10]
            int r4 = r3 + r12
            long r4 = (long) r4
            long r6 = (long) r2
            int r6 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r6 < 0) goto L70
            int r12 = (int) r4
            r11[r10] = r12
            goto Lb2
        L70:
            if (r0 != 0) goto L9e
            if (r10 != 0) goto L7f
            int r2 = r2 + (-1)
            int r2 = r2 - r3
            int r12 = r12 - r2
            int r2 = r8.getMaximumValue(r9, r11)
            r11[r10] = r2
            goto L5c
        L7f:
            int r0 = r10 + (-1)
            org.joda.time.DateTimeField r0 = r9.getField(r0)
            org.joda.time.DurationField r3 = r8.getRangeDurationField()
            org.joda.time.DurationFieldType r3 = r3.getType()
            org.joda.time.DurationField r4 = r0.getDurationField()
            org.joda.time.DurationFieldType r4 = r4.getType()
            if (r3 != r4) goto L98
            goto L9e
        L98:
            java.lang.IllegalArgumentException r9 = new java.lang.IllegalArgumentException
            r9.<init>(r1)
            throw r9
        L9e:
            int r2 = r2 + (-1)
            r3 = r11[r10]
            int r2 = r2 - r3
            int r12 = r12 - r2
            int r2 = r10 + (-1)
            r3 = -1
            int[] r11 = r0.addWrapPartial(r9, r2, r11, r3)
            int r2 = r8.getMaximumValue(r9, r11)
            r11[r10] = r2
            goto L5c
        Lb2:
            r12 = r11[r10]
            int[] r9 = r8.set(r9, r10, r11, r12)
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: org.joda.time.field.BaseDateTimeField.addWrapPartial(org.joda.time.ReadablePartial, int, int[], int):int[]");
    }

    @Override // org.joda.time.DateTimeField
    public abstract int get(long j2);

    @Override // org.joda.time.DateTimeField
    public String getAsShortText(long j2, Locale locale) {
        return getAsShortText(get(j2), locale);
    }

    @Override // org.joda.time.DateTimeField
    public String getAsText(long j2, Locale locale) {
        return getAsText(get(j2), locale);
    }

    @Override // org.joda.time.DateTimeField
    public int getDifference(long j2, long j3) {
        return getDurationField().getDifference(j2, j3);
    }

    @Override // org.joda.time.DateTimeField
    public long getDifferenceAsLong(long j2, long j3) {
        return getDurationField().getDifferenceAsLong(j2, j3);
    }

    @Override // org.joda.time.DateTimeField
    public abstract DurationField getDurationField();

    @Override // org.joda.time.DateTimeField
    public int getLeapAmount(long j2) {
        return 0;
    }

    @Override // org.joda.time.DateTimeField
    public DurationField getLeapDurationField() {
        return null;
    }

    @Override // org.joda.time.DateTimeField
    public int getMaximumShortTextLength(Locale locale) {
        return getMaximumTextLength(locale);
    }

    @Override // org.joda.time.DateTimeField
    public int getMaximumTextLength(Locale locale) {
        int maximumValue = getMaximumValue();
        if (maximumValue >= 0) {
            if (maximumValue < 10) {
                return 1;
            }
            if (maximumValue < 100) {
                return 2;
            }
            if (maximumValue < 1000) {
                return 3;
            }
        }
        return Integer.toString(maximumValue).length();
    }

    @Override // org.joda.time.DateTimeField
    public abstract int getMaximumValue();

    @Override // org.joda.time.DateTimeField
    public int getMaximumValue(long j2) {
        return getMaximumValue();
    }

    @Override // org.joda.time.DateTimeField
    public abstract int getMinimumValue();

    @Override // org.joda.time.DateTimeField
    public int getMinimumValue(long j2) {
        return getMinimumValue();
    }

    @Override // org.joda.time.DateTimeField
    public final String getName() {
        return this.iType.getName();
    }

    @Override // org.joda.time.DateTimeField
    public abstract DurationField getRangeDurationField();

    @Override // org.joda.time.DateTimeField
    public final DateTimeFieldType getType() {
        return this.iType;
    }

    @Override // org.joda.time.DateTimeField
    public boolean isLeap(long j2) {
        return false;
    }

    @Override // org.joda.time.DateTimeField
    public final boolean isSupported() {
        return true;
    }

    @Override // org.joda.time.DateTimeField
    public long remainder(long j2) {
        return j2 - roundFloor(j2);
    }

    @Override // org.joda.time.DateTimeField
    public long roundCeiling(long j2) {
        long jRoundFloor = roundFloor(j2);
        return jRoundFloor != j2 ? add(jRoundFloor, 1) : j2;
    }

    @Override // org.joda.time.DateTimeField
    public abstract long roundFloor(long j2);

    @Override // org.joda.time.DateTimeField
    public long roundHalfCeiling(long j2) {
        long jRoundFloor = roundFloor(j2);
        long jRoundCeiling = roundCeiling(j2);
        return jRoundCeiling - j2 <= j2 - jRoundFloor ? jRoundCeiling : jRoundFloor;
    }

    @Override // org.joda.time.DateTimeField
    public long roundHalfEven(long j2) {
        long jRoundFloor = roundFloor(j2);
        long jRoundCeiling = roundCeiling(j2);
        long j3 = j2 - jRoundFloor;
        long j4 = jRoundCeiling - j2;
        return j3 < j4 ? jRoundFloor : (j4 >= j3 && (get(jRoundCeiling) & 1) != 0) ? jRoundFloor : jRoundCeiling;
    }

    @Override // org.joda.time.DateTimeField
    public long roundHalfFloor(long j2) {
        long jRoundFloor = roundFloor(j2);
        long jRoundCeiling = roundCeiling(j2);
        return j2 - jRoundFloor <= jRoundCeiling - j2 ? jRoundFloor : jRoundCeiling;
    }

    @Override // org.joda.time.DateTimeField
    public abstract long set(long j2, int i2);

    @Override // org.joda.time.DateTimeField
    public int[] set(ReadablePartial readablePartial, int i2, int[] iArr, int i3) {
        FieldUtils.verifyValueBounds(this, i3, getMinimumValue(readablePartial, iArr), getMaximumValue(readablePartial, iArr));
        iArr[i2] = i3;
        while (true) {
            i2++;
            if (i2 >= readablePartial.size()) {
                return iArr;
            }
            DateTimeField field = readablePartial.getField(i2);
            if (iArr[i2] > field.getMaximumValue(readablePartial, iArr)) {
                iArr[i2] = field.getMaximumValue(readablePartial, iArr);
            }
            if (iArr[i2] < field.getMinimumValue(readablePartial, iArr)) {
                iArr[i2] = field.getMinimumValue(readablePartial, iArr);
            }
        }
    }

    @Override // org.joda.time.DateTimeField
    public String toString() {
        return "DateTimeField[" + getName() + ']';
    }

    @Override // org.joda.time.DateTimeField
    public long add(long j2, long j3) {
        return getDurationField().add(j2, j3);
    }

    @Override // org.joda.time.DateTimeField
    public final String getAsShortText(long j2) {
        return getAsShortText(j2, (Locale) null);
    }

    @Override // org.joda.time.DateTimeField
    public final String getAsText(long j2) {
        return getAsText(j2, (Locale) null);
    }

    @Override // org.joda.time.DateTimeField
    public int getMaximumValue(ReadablePartial readablePartial) {
        return getMaximumValue();
    }

    @Override // org.joda.time.DateTimeField
    public int getMinimumValue(ReadablePartial readablePartial) {
        return getMinimumValue();
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0058, code lost:
    
        if (r12 >= 0) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x005a, code lost:
    
        r3 = getMinimumValue(r9, r11);
        r4 = r11[r10] + r12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0065, code lost:
    
        if (r4 < r3) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0067, code lost:
    
        r11[r10] = (int) r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x006b, code lost:
    
        if (r0 != null) goto L46;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x006d, code lost:
    
        if (r10 == 0) goto L44;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x006f, code lost:
    
        r0 = r9.getField(r10 - 1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0085, code lost:
    
        if (getRangeDurationField().getType() != r0.getDurationField().getType()) goto L45;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x008d, code lost:
    
        throw new java.lang.IllegalArgumentException("Fields invalid for add");
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0093, code lost:
    
        throw new java.lang.IllegalArgumentException("Maximum value exceeded for add");
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0094, code lost:
    
        r12 = r12 - ((r3 - 1) - r11[r10]);
        r11 = r0.add(r9, r10 - 1, r11, -1);
        r11[r10] = getMaximumValue(r9, r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00ae, code lost:
    
        return set(r9, r10, r11, r11[r10]);
     */
    @Override // org.joda.time.DateTimeField
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int[] add(org.joda.time.ReadablePartial r9, int r10, int[] r11, int r12) {
        /*
            r8 = this;
            if (r12 != 0) goto L3
            return r11
        L3:
            r0 = 0
        L4:
            java.lang.String r1 = "Fields invalid for add"
            java.lang.String r2 = "Maximum value exceeded for add"
            if (r12 <= 0) goto L58
            int r3 = r8.getMaximumValue(r9, r11)
            r4 = r11[r10]
            int r4 = r4 + r12
            long r4 = (long) r4
            long r6 = (long) r3
            int r6 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r6 > 0) goto L1b
            int r3 = (int) r4
            r11[r10] = r3
            goto L58
        L1b:
            if (r0 != 0) goto L44
            if (r10 == 0) goto L3e
            int r0 = r10 + (-1)
            org.joda.time.DateTimeField r0 = r9.getField(r0)
            org.joda.time.DurationField r2 = r8.getRangeDurationField()
            org.joda.time.DurationFieldType r2 = r2.getType()
            org.joda.time.DurationField r4 = r0.getDurationField()
            org.joda.time.DurationFieldType r4 = r4.getType()
            if (r2 != r4) goto L38
            goto L44
        L38:
            java.lang.IllegalArgumentException r9 = new java.lang.IllegalArgumentException
            r9.<init>(r1)
            throw r9
        L3e:
            java.lang.IllegalArgumentException r9 = new java.lang.IllegalArgumentException
            r9.<init>(r2)
            throw r9
        L44:
            int r3 = r3 + 1
            r1 = r11[r10]
            int r3 = r3 - r1
            int r12 = r12 - r3
            int r1 = r10 + (-1)
            r2 = 1
            int[] r11 = r0.add(r9, r1, r11, r2)
            int r1 = r8.getMinimumValue(r9, r11)
            r11[r10] = r1
            goto L4
        L58:
            if (r12 >= 0) goto La8
            int r3 = r8.getMinimumValue(r9, r11)
            r4 = r11[r10]
            int r4 = r4 + r12
            long r4 = (long) r4
            long r6 = (long) r3
            int r6 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r6 < 0) goto L6b
            int r12 = (int) r4
            r11[r10] = r12
            goto La8
        L6b:
            if (r0 != 0) goto L94
            if (r10 == 0) goto L8e
            int r0 = r10 + (-1)
            org.joda.time.DateTimeField r0 = r9.getField(r0)
            org.joda.time.DurationField r4 = r8.getRangeDurationField()
            org.joda.time.DurationFieldType r4 = r4.getType()
            org.joda.time.DurationField r5 = r0.getDurationField()
            org.joda.time.DurationFieldType r5 = r5.getType()
            if (r4 != r5) goto L88
            goto L94
        L88:
            java.lang.IllegalArgumentException r9 = new java.lang.IllegalArgumentException
            r9.<init>(r1)
            throw r9
        L8e:
            java.lang.IllegalArgumentException r9 = new java.lang.IllegalArgumentException
            r9.<init>(r2)
            throw r9
        L94:
            int r3 = r3 + (-1)
            r4 = r11[r10]
            int r3 = r3 - r4
            int r12 = r12 - r3
            int r3 = r10 + (-1)
            r4 = -1
            int[] r11 = r0.add(r9, r3, r11, r4)
            int r3 = r8.getMaximumValue(r9, r11)
            r11[r10] = r3
            goto L58
        La8:
            r12 = r11[r10]
            int[] r9 = r8.set(r9, r10, r11, r12)
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: org.joda.time.field.BaseDateTimeField.add(org.joda.time.ReadablePartial, int, int[], int):int[]");
    }

    @Override // org.joda.time.DateTimeField
    public String getAsShortText(ReadablePartial readablePartial, int i2, Locale locale) {
        return getAsShortText(i2, locale);
    }

    @Override // org.joda.time.DateTimeField
    public String getAsText(ReadablePartial readablePartial, int i2, Locale locale) {
        return getAsText(i2, locale);
    }

    @Override // org.joda.time.DateTimeField
    public int getMaximumValue(ReadablePartial readablePartial, int[] iArr) {
        return getMaximumValue(readablePartial);
    }

    @Override // org.joda.time.DateTimeField
    public int getMinimumValue(ReadablePartial readablePartial, int[] iArr) {
        return getMinimumValue(readablePartial);
    }

    @Override // org.joda.time.DateTimeField
    public int[] addWrapField(ReadablePartial readablePartial, int i2, int[] iArr, int i3) {
        return set(readablePartial, i2, iArr, FieldUtils.getWrappedValue(iArr[i2], i3, getMinimumValue(readablePartial), getMaximumValue(readablePartial)));
    }

    @Override // org.joda.time.DateTimeField
    public final String getAsShortText(ReadablePartial readablePartial, Locale locale) {
        return getAsShortText(readablePartial, readablePartial.get(getType()), locale);
    }

    @Override // org.joda.time.DateTimeField
    public final String getAsText(ReadablePartial readablePartial, Locale locale) {
        return getAsText(readablePartial, readablePartial.get(getType()), locale);
    }

    @Override // org.joda.time.DateTimeField
    public String getAsShortText(int i2, Locale locale) {
        return getAsText(i2, locale);
    }

    @Override // org.joda.time.DateTimeField
    public String getAsText(int i2, Locale locale) {
        return Integer.toString(i2);
    }

    @Override // org.joda.time.DateTimeField
    public long set(long j2, String str, Locale locale) {
        return set(j2, a(str, locale));
    }

    @Override // org.joda.time.DateTimeField
    public final long set(long j2, String str) {
        return set(j2, str, null);
    }

    @Override // org.joda.time.DateTimeField
    public int[] set(ReadablePartial readablePartial, int i2, int[] iArr, String str, Locale locale) {
        return set(readablePartial, i2, iArr, a(str, locale));
    }
}
