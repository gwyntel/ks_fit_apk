package org.joda.time.chrono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.joda.time.Chronology;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeUtils;
import org.joda.time.DateTimeZone;
import org.joda.time.DurationField;
import org.joda.time.IllegalFieldValueException;
import org.joda.time.Instant;
import org.joda.time.LocalDate;
import org.joda.time.ReadableInstant;
import org.joda.time.ReadablePartial;
import org.joda.time.chrono.AssembledChronology;
import org.joda.time.field.BaseDateTimeField;
import org.joda.time.field.DecoratedDurationField;
import org.joda.time.format.ISODateTimeFormat;

/* loaded from: classes5.dex */
public final class GJChronology extends AssembledChronology {
    static final Instant DEFAULT_CUTOVER = new Instant(-12219292800000L);
    private static final Map<DateTimeZone, ArrayList<GJChronology>> cCache = new HashMap();
    private static final long serialVersionUID = -2545574827706931671L;
    private Instant iCutoverInstant;
    private long iCutoverMillis;
    private long iGapDuration;
    private GregorianChronology iGregorianChronology;
    private JulianChronology iJulianChronology;

    private class CutoverField extends BaseDateTimeField {
        private static final long serialVersionUID = 3528501219481026402L;

        /* renamed from: a, reason: collision with root package name */
        final DateTimeField f26687a;

        /* renamed from: b, reason: collision with root package name */
        final DateTimeField f26688b;

        /* renamed from: c, reason: collision with root package name */
        final long f26689c;

        /* renamed from: d, reason: collision with root package name */
        final boolean f26690d;

        /* renamed from: e, reason: collision with root package name */
        protected DurationField f26691e;

        /* renamed from: f, reason: collision with root package name */
        protected DurationField f26692f;

        CutoverField(GJChronology gJChronology, DateTimeField dateTimeField, DateTimeField dateTimeField2, long j2) {
            this(dateTimeField, dateTimeField2, j2, false);
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public long add(long j2, int i2) {
            return this.f26688b.add(j2, i2);
        }

        protected long b(long j2) {
            return this.f26690d ? GJChronology.this.gregorianToJulianByWeekyear(j2) : GJChronology.this.gregorianToJulianByYear(j2);
        }

        protected long c(long j2) {
            return this.f26690d ? GJChronology.this.julianToGregorianByWeekyear(j2) : GJChronology.this.julianToGregorianByYear(j2);
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public int get(long j2) {
            return j2 >= this.f26689c ? this.f26688b.get(j2) : this.f26687a.get(j2);
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public String getAsShortText(long j2, Locale locale) {
            return j2 >= this.f26689c ? this.f26688b.getAsShortText(j2, locale) : this.f26687a.getAsShortText(j2, locale);
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public String getAsText(long j2, Locale locale) {
            return j2 >= this.f26689c ? this.f26688b.getAsText(j2, locale) : this.f26687a.getAsText(j2, locale);
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public int getDifference(long j2, long j3) {
            return this.f26688b.getDifference(j2, j3);
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public long getDifferenceAsLong(long j2, long j3) {
            return this.f26688b.getDifferenceAsLong(j2, j3);
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public DurationField getDurationField() {
            return this.f26691e;
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public int getLeapAmount(long j2) {
            return j2 >= this.f26689c ? this.f26688b.getLeapAmount(j2) : this.f26687a.getLeapAmount(j2);
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public DurationField getLeapDurationField() {
            return this.f26688b.getLeapDurationField();
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public int getMaximumShortTextLength(Locale locale) {
            return Math.max(this.f26687a.getMaximumShortTextLength(locale), this.f26688b.getMaximumShortTextLength(locale));
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public int getMaximumTextLength(Locale locale) {
            return Math.max(this.f26687a.getMaximumTextLength(locale), this.f26688b.getMaximumTextLength(locale));
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public int getMaximumValue() {
            return this.f26688b.getMaximumValue();
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public int getMinimumValue() {
            return this.f26687a.getMinimumValue();
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public DurationField getRangeDurationField() {
            return this.f26692f;
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public boolean isLeap(long j2) {
            return j2 >= this.f26689c ? this.f26688b.isLeap(j2) : this.f26687a.isLeap(j2);
        }

        @Override // org.joda.time.DateTimeField
        public boolean isLenient() {
            return false;
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public long roundCeiling(long j2) {
            if (j2 >= this.f26689c) {
                return this.f26688b.roundCeiling(j2);
            }
            long jRoundCeiling = this.f26687a.roundCeiling(j2);
            return (jRoundCeiling < this.f26689c || jRoundCeiling - GJChronology.this.iGapDuration < this.f26689c) ? jRoundCeiling : c(jRoundCeiling);
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public long roundFloor(long j2) {
            if (j2 < this.f26689c) {
                return this.f26687a.roundFloor(j2);
            }
            long jRoundFloor = this.f26688b.roundFloor(j2);
            return (jRoundFloor >= this.f26689c || GJChronology.this.iGapDuration + jRoundFloor >= this.f26689c) ? jRoundFloor : b(jRoundFloor);
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public long set(long j2, int i2) {
            long jC;
            if (j2 >= this.f26689c) {
                jC = this.f26688b.set(j2, i2);
                if (jC < this.f26689c) {
                    if (GJChronology.this.iGapDuration + jC < this.f26689c) {
                        jC = b(jC);
                    }
                    if (get(jC) != i2) {
                        throw new IllegalFieldValueException(this.f26688b.getType(), Integer.valueOf(i2), (Number) null, (Number) null);
                    }
                }
            } else {
                jC = this.f26687a.set(j2, i2);
                if (jC >= this.f26689c) {
                    if (jC - GJChronology.this.iGapDuration >= this.f26689c) {
                        jC = c(jC);
                    }
                    if (get(jC) != i2) {
                        throw new IllegalFieldValueException(this.f26687a.getType(), Integer.valueOf(i2), (Number) null, (Number) null);
                    }
                }
            }
            return jC;
        }

        CutoverField(DateTimeField dateTimeField, DateTimeField dateTimeField2, long j2, boolean z2) {
            super(dateTimeField2.getType());
            this.f26687a = dateTimeField;
            this.f26688b = dateTimeField2;
            this.f26689c = j2;
            this.f26690d = z2;
            this.f26691e = dateTimeField2.getDurationField();
            DurationField rangeDurationField = dateTimeField2.getRangeDurationField();
            this.f26692f = rangeDurationField == null ? dateTimeField.getRangeDurationField() : rangeDurationField;
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public long add(long j2, long j3) {
            return this.f26688b.add(j2, j3);
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public int getMaximumValue(long j2) {
            if (j2 >= this.f26689c) {
                return this.f26688b.getMaximumValue(j2);
            }
            int maximumValue = this.f26687a.getMaximumValue(j2);
            long j3 = this.f26687a.set(j2, maximumValue);
            long j4 = this.f26689c;
            if (j3 < j4) {
                return maximumValue;
            }
            DateTimeField dateTimeField = this.f26687a;
            return dateTimeField.get(dateTimeField.add(j4, -1));
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public int getMinimumValue(ReadablePartial readablePartial) {
            return this.f26687a.getMinimumValue(readablePartial);
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public int[] add(ReadablePartial readablePartial, int i2, int[] iArr, int i3) {
            if (i3 == 0) {
                return iArr;
            }
            if (DateTimeUtils.isContiguous(readablePartial)) {
                int size = readablePartial.size();
                long j2 = 0;
                for (int i4 = 0; i4 < size; i4++) {
                    j2 = readablePartial.getFieldType(i4).getField(GJChronology.this).set(j2, iArr[i4]);
                }
                return GJChronology.this.get(readablePartial, add(j2, i3));
            }
            return super.add(readablePartial, i2, iArr, i3);
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public int getMinimumValue(ReadablePartial readablePartial, int[] iArr) {
            return this.f26687a.getMinimumValue(readablePartial, iArr);
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public String getAsShortText(int i2, Locale locale) {
            return this.f26688b.getAsShortText(i2, locale);
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public String getAsText(int i2, Locale locale) {
            return this.f26688b.getAsText(i2, locale);
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public int getMinimumValue(long j2) {
            if (j2 < this.f26689c) {
                return this.f26687a.getMinimumValue(j2);
            }
            int minimumValue = this.f26688b.getMinimumValue(j2);
            long j3 = this.f26688b.set(j2, minimumValue);
            long j4 = this.f26689c;
            return j3 < j4 ? this.f26688b.get(j4) : minimumValue;
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public int getMaximumValue(ReadablePartial readablePartial) {
            return getMaximumValue(GJChronology.getInstanceUTC().set(readablePartial, 0L));
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public int getMaximumValue(ReadablePartial readablePartial, int[] iArr) {
            GJChronology instanceUTC = GJChronology.getInstanceUTC();
            int size = readablePartial.size();
            long j2 = 0;
            for (int i2 = 0; i2 < size; i2++) {
                DateTimeField field = readablePartial.getFieldType(i2).getField(instanceUTC);
                if (iArr[i2] <= field.getMaximumValue(j2)) {
                    j2 = field.set(j2, iArr[i2]);
                }
            }
            return getMaximumValue(j2);
        }

        @Override // org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public long set(long j2, String str, Locale locale) {
            if (j2 >= this.f26689c) {
                long j3 = this.f26688b.set(j2, str, locale);
                return (j3 >= this.f26689c || GJChronology.this.iGapDuration + j3 >= this.f26689c) ? j3 : b(j3);
            }
            long j4 = this.f26687a.set(j2, str, locale);
            return (j4 < this.f26689c || j4 - GJChronology.this.iGapDuration < this.f26689c) ? j4 : c(j4);
        }
    }

    private final class ImpreciseCutoverField extends CutoverField {
        private static final long serialVersionUID = 3410248757173576441L;

        ImpreciseCutoverField(GJChronology gJChronology, DateTimeField dateTimeField, DateTimeField dateTimeField2, long j2) {
            this(dateTimeField, dateTimeField2, null, j2, false);
        }

        @Override // org.joda.time.chrono.GJChronology.CutoverField, org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public long add(long j2, int i2) {
            if (j2 < this.f26689c) {
                long jAdd = this.f26687a.add(j2, i2);
                return (jAdd < this.f26689c || jAdd - GJChronology.this.iGapDuration < this.f26689c) ? jAdd : c(jAdd);
            }
            long jAdd2 = this.f26688b.add(j2, i2);
            if (jAdd2 >= this.f26689c || GJChronology.this.iGapDuration + jAdd2 >= this.f26689c) {
                return jAdd2;
            }
            if (this.f26690d) {
                if (GJChronology.this.iGregorianChronology.weekyear().get(jAdd2) <= 0) {
                    jAdd2 = GJChronology.this.iGregorianChronology.weekyear().add(jAdd2, -1);
                }
            } else if (GJChronology.this.iGregorianChronology.year().get(jAdd2) <= 0) {
                jAdd2 = GJChronology.this.iGregorianChronology.year().add(jAdd2, -1);
            }
            return b(jAdd2);
        }

        @Override // org.joda.time.chrono.GJChronology.CutoverField, org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public int getDifference(long j2, long j3) {
            long j4 = this.f26689c;
            if (j2 >= j4) {
                if (j3 >= j4) {
                    return this.f26688b.getDifference(j2, j3);
                }
                return this.f26687a.getDifference(b(j2), j3);
            }
            if (j3 < j4) {
                return this.f26687a.getDifference(j2, j3);
            }
            return this.f26688b.getDifference(c(j2), j3);
        }

        @Override // org.joda.time.chrono.GJChronology.CutoverField, org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public long getDifferenceAsLong(long j2, long j3) {
            long j4 = this.f26689c;
            if (j2 >= j4) {
                if (j3 >= j4) {
                    return this.f26688b.getDifferenceAsLong(j2, j3);
                }
                return this.f26687a.getDifferenceAsLong(b(j2), j3);
            }
            if (j3 < j4) {
                return this.f26687a.getDifferenceAsLong(j2, j3);
            }
            return this.f26688b.getDifferenceAsLong(c(j2), j3);
        }

        @Override // org.joda.time.chrono.GJChronology.CutoverField, org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public int getMaximumValue(long j2) {
            return j2 >= this.f26689c ? this.f26688b.getMaximumValue(j2) : this.f26687a.getMaximumValue(j2);
        }

        @Override // org.joda.time.chrono.GJChronology.CutoverField, org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public int getMinimumValue(long j2) {
            return j2 >= this.f26689c ? this.f26688b.getMinimumValue(j2) : this.f26687a.getMinimumValue(j2);
        }

        ImpreciseCutoverField(GJChronology gJChronology, DateTimeField dateTimeField, DateTimeField dateTimeField2, DurationField durationField, long j2) {
            this(dateTimeField, dateTimeField2, durationField, j2, false);
        }

        ImpreciseCutoverField(DateTimeField dateTimeField, DateTimeField dateTimeField2, DurationField durationField, long j2, boolean z2) {
            super(dateTimeField, dateTimeField2, j2, z2);
            this.f26691e = durationField == null ? new LinkedDurationField(this.f26691e, this) : durationField;
        }

        @Override // org.joda.time.chrono.GJChronology.CutoverField, org.joda.time.field.BaseDateTimeField, org.joda.time.DateTimeField
        public long add(long j2, long j3) {
            if (j2 >= this.f26689c) {
                long jAdd = this.f26688b.add(j2, j3);
                if (jAdd >= this.f26689c || GJChronology.this.iGapDuration + jAdd >= this.f26689c) {
                    return jAdd;
                }
                if (this.f26690d) {
                    if (GJChronology.this.iGregorianChronology.weekyear().get(jAdd) <= 0) {
                        jAdd = GJChronology.this.iGregorianChronology.weekyear().add(jAdd, -1);
                    }
                } else if (GJChronology.this.iGregorianChronology.year().get(jAdd) <= 0) {
                    jAdd = GJChronology.this.iGregorianChronology.year().add(jAdd, -1);
                }
                return b(jAdd);
            }
            long jAdd2 = this.f26687a.add(j2, j3);
            return (jAdd2 < this.f26689c || jAdd2 - GJChronology.this.iGapDuration < this.f26689c) ? jAdd2 : c(jAdd2);
        }
    }

    private static class LinkedDurationField extends DecoratedDurationField {
        private static final long serialVersionUID = 4097975388007713084L;
        private final ImpreciseCutoverField iField;

        LinkedDurationField(DurationField durationField, ImpreciseCutoverField impreciseCutoverField) {
            super(durationField, durationField.getType());
            this.iField = impreciseCutoverField;
        }

        @Override // org.joda.time.field.DecoratedDurationField, org.joda.time.DurationField
        public long add(long j2, int i2) {
            return this.iField.add(j2, i2);
        }

        @Override // org.joda.time.field.BaseDurationField, org.joda.time.DurationField
        public int getDifference(long j2, long j3) {
            return this.iField.getDifference(j2, j3);
        }

        @Override // org.joda.time.field.DecoratedDurationField, org.joda.time.DurationField
        public long getDifferenceAsLong(long j2, long j3) {
            return this.iField.getDifferenceAsLong(j2, j3);
        }

        @Override // org.joda.time.field.DecoratedDurationField, org.joda.time.DurationField
        public long add(long j2, long j3) {
            return this.iField.add(j2, j3);
        }
    }

    private GJChronology(JulianChronology julianChronology, GregorianChronology gregorianChronology, Instant instant) {
        super(null, new Object[]{julianChronology, gregorianChronology, instant});
    }

    private static long convertByWeekyear(long j2, Chronology chronology, Chronology chronology2) {
        return chronology2.millisOfDay().set(chronology2.dayOfWeek().set(chronology2.weekOfWeekyear().set(chronology2.weekyear().set(0L, chronology.weekyear().get(j2)), chronology.weekOfWeekyear().get(j2)), chronology.dayOfWeek().get(j2)), chronology.millisOfDay().get(j2));
    }

    private static long convertByYear(long j2, Chronology chronology, Chronology chronology2) {
        return chronology2.getDateTimeMillis(chronology.year().get(j2), chronology.monthOfYear().get(j2), chronology.dayOfMonth().get(j2), chronology.millisOfDay().get(j2));
    }

    public static GJChronology getInstance() {
        return getInstance(DateTimeZone.getDefault(), DEFAULT_CUTOVER, 4);
    }

    public static GJChronology getInstanceUTC() {
        return getInstance(DateTimeZone.UTC, DEFAULT_CUTOVER, 4);
    }

    private Object readResolve() {
        return getInstance(getZone(), this.iCutoverInstant, getMinimumDaysInFirstWeek());
    }

    @Override // org.joda.time.chrono.AssembledChronology
    protected void assemble(AssembledChronology.Fields fields) {
        Object[] objArr = (Object[]) getParam();
        JulianChronology julianChronology = (JulianChronology) objArr[0];
        GregorianChronology gregorianChronology = (GregorianChronology) objArr[1];
        Instant instant = (Instant) objArr[2];
        this.iCutoverMillis = instant.getMillis();
        this.iJulianChronology = julianChronology;
        this.iGregorianChronology = gregorianChronology;
        this.iCutoverInstant = instant;
        if (getBase() != null) {
            return;
        }
        if (julianChronology.getMinimumDaysInFirstWeek() != gregorianChronology.getMinimumDaysInFirstWeek()) {
            throw new IllegalArgumentException();
        }
        long j2 = this.iCutoverMillis;
        this.iGapDuration = j2 - julianToGregorianByYear(j2);
        fields.copyFieldsFrom(gregorianChronology);
        if (gregorianChronology.millisOfDay().get(this.iCutoverMillis) == 0) {
            fields.millisOfSecond = new CutoverField(this, julianChronology.millisOfSecond(), fields.millisOfSecond, this.iCutoverMillis);
            fields.millisOfDay = new CutoverField(this, julianChronology.millisOfDay(), fields.millisOfDay, this.iCutoverMillis);
            fields.secondOfMinute = new CutoverField(this, julianChronology.secondOfMinute(), fields.secondOfMinute, this.iCutoverMillis);
            fields.secondOfDay = new CutoverField(this, julianChronology.secondOfDay(), fields.secondOfDay, this.iCutoverMillis);
            fields.minuteOfHour = new CutoverField(this, julianChronology.minuteOfHour(), fields.minuteOfHour, this.iCutoverMillis);
            fields.minuteOfDay = new CutoverField(this, julianChronology.minuteOfDay(), fields.minuteOfDay, this.iCutoverMillis);
            fields.hourOfDay = new CutoverField(this, julianChronology.hourOfDay(), fields.hourOfDay, this.iCutoverMillis);
            fields.hourOfHalfday = new CutoverField(this, julianChronology.hourOfHalfday(), fields.hourOfHalfday, this.iCutoverMillis);
            fields.clockhourOfDay = new CutoverField(this, julianChronology.clockhourOfDay(), fields.clockhourOfDay, this.iCutoverMillis);
            fields.clockhourOfHalfday = new CutoverField(this, julianChronology.clockhourOfHalfday(), fields.clockhourOfHalfday, this.iCutoverMillis);
            fields.halfdayOfDay = new CutoverField(this, julianChronology.halfdayOfDay(), fields.halfdayOfDay, this.iCutoverMillis);
        }
        fields.era = new CutoverField(this, julianChronology.era(), fields.era, this.iCutoverMillis);
        fields.dayOfYear = new CutoverField(this, julianChronology.dayOfYear(), fields.dayOfYear, gregorianChronology.year().roundCeiling(this.iCutoverMillis));
        fields.weekOfWeekyear = new CutoverField(julianChronology.weekOfWeekyear(), fields.weekOfWeekyear, gregorianChronology.weekyear().roundCeiling(this.iCutoverMillis), true);
        ImpreciseCutoverField impreciseCutoverField = new ImpreciseCutoverField(this, julianChronology.year(), fields.year, this.iCutoverMillis);
        fields.year = impreciseCutoverField;
        fields.years = impreciseCutoverField.getDurationField();
        fields.yearOfEra = new ImpreciseCutoverField(this, julianChronology.yearOfEra(), fields.yearOfEra, fields.years, this.iCutoverMillis);
        fields.yearOfCentury = new ImpreciseCutoverField(this, julianChronology.yearOfCentury(), fields.yearOfCentury, fields.years, this.iCutoverMillis);
        ImpreciseCutoverField impreciseCutoverField2 = new ImpreciseCutoverField(this, julianChronology.centuryOfEra(), fields.centuryOfEra, this.iCutoverMillis);
        fields.centuryOfEra = impreciseCutoverField2;
        fields.centuries = impreciseCutoverField2.getDurationField();
        ImpreciseCutoverField impreciseCutoverField3 = new ImpreciseCutoverField(this, julianChronology.monthOfYear(), fields.monthOfYear, this.iCutoverMillis);
        fields.monthOfYear = impreciseCutoverField3;
        fields.months = impreciseCutoverField3.getDurationField();
        fields.weekyear = new ImpreciseCutoverField(julianChronology.weekyear(), fields.weekyear, null, this.iCutoverMillis, true);
        fields.weekyearOfCentury = new ImpreciseCutoverField(this, julianChronology.weekyearOfCentury(), fields.weekyearOfCentury, fields.weekyears, this.iCutoverMillis);
        fields.weekyears = fields.weekyear.getDurationField();
        CutoverField cutoverField = new CutoverField(this, julianChronology.dayOfMonth(), fields.dayOfMonth, this.iCutoverMillis);
        cutoverField.f26692f = fields.months;
        fields.dayOfMonth = cutoverField;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof GJChronology)) {
            return false;
        }
        GJChronology gJChronology = (GJChronology) obj;
        return this.iCutoverMillis == gJChronology.iCutoverMillis && getMinimumDaysInFirstWeek() == gJChronology.getMinimumDaysInFirstWeek() && getZone().equals(gJChronology.getZone());
    }

    @Override // org.joda.time.chrono.AssembledChronology, org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public long getDateTimeMillis(int i2, int i3, int i4, int i5) throws IllegalArgumentException {
        Chronology base = getBase();
        if (base != null) {
            return base.getDateTimeMillis(i2, i3, i4, i5);
        }
        long dateTimeMillis = this.iGregorianChronology.getDateTimeMillis(i2, i3, i4, i5);
        if (dateTimeMillis < this.iCutoverMillis) {
            dateTimeMillis = this.iJulianChronology.getDateTimeMillis(i2, i3, i4, i5);
            if (dateTimeMillis >= this.iCutoverMillis) {
                throw new IllegalArgumentException("Specified date does not exist");
            }
        }
        return dateTimeMillis;
    }

    public Instant getGregorianCutover() {
        return this.iCutoverInstant;
    }

    public int getMinimumDaysInFirstWeek() {
        return this.iGregorianChronology.getMinimumDaysInFirstWeek();
    }

    @Override // org.joda.time.chrono.AssembledChronology, org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public DateTimeZone getZone() {
        Chronology base = getBase();
        return base != null ? base.getZone() : DateTimeZone.UTC;
    }

    long gregorianToJulianByWeekyear(long j2) {
        return convertByWeekyear(j2, this.iGregorianChronology, this.iJulianChronology);
    }

    long gregorianToJulianByYear(long j2) {
        return convertByYear(j2, this.iGregorianChronology, this.iJulianChronology);
    }

    public int hashCode() {
        return 25025 + getZone().hashCode() + getMinimumDaysInFirstWeek() + this.iCutoverInstant.hashCode();
    }

    long julianToGregorianByWeekyear(long j2) {
        return convertByWeekyear(j2, this.iJulianChronology, this.iGregorianChronology);
    }

    long julianToGregorianByYear(long j2) {
        return convertByYear(j2, this.iJulianChronology, this.iGregorianChronology);
    }

    @Override // org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer(60);
        stringBuffer.append("GJChronology");
        stringBuffer.append('[');
        stringBuffer.append(getZone().getID());
        if (this.iCutoverMillis != DEFAULT_CUTOVER.getMillis()) {
            stringBuffer.append(",cutover=");
            (withUTC().dayOfYear().remainder(this.iCutoverMillis) == 0 ? ISODateTimeFormat.date() : ISODateTimeFormat.dateTime()).withChronology(withUTC()).printTo(stringBuffer, this.iCutoverMillis);
        }
        if (getMinimumDaysInFirstWeek() != 4) {
            stringBuffer.append(",mdfw=");
            stringBuffer.append(getMinimumDaysInFirstWeek());
        }
        stringBuffer.append(']');
        return stringBuffer.toString();
    }

    @Override // org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public Chronology withUTC() {
        return withZone(DateTimeZone.UTC);
    }

    @Override // org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public Chronology withZone(DateTimeZone dateTimeZone) {
        if (dateTimeZone == null) {
            dateTimeZone = DateTimeZone.getDefault();
        }
        return dateTimeZone == getZone() ? this : getInstance(dateTimeZone, this.iCutoverInstant, getMinimumDaysInFirstWeek());
    }

    private GJChronology(Chronology chronology, JulianChronology julianChronology, GregorianChronology gregorianChronology, Instant instant) {
        super(chronology, new Object[]{julianChronology, gregorianChronology, instant});
    }

    public static GJChronology getInstance(DateTimeZone dateTimeZone) {
        return getInstance(dateTimeZone, DEFAULT_CUTOVER, 4);
    }

    public static GJChronology getInstance(DateTimeZone dateTimeZone, ReadableInstant readableInstant) {
        return getInstance(dateTimeZone, readableInstant, 4);
    }

    public static synchronized GJChronology getInstance(DateTimeZone dateTimeZone, ReadableInstant readableInstant, int i2) {
        Instant instant;
        GJChronology gJChronology;
        try {
            DateTimeZone zone = DateTimeUtils.getZone(dateTimeZone);
            if (readableInstant == null) {
                instant = DEFAULT_CUTOVER;
            } else {
                instant = readableInstant.toInstant();
                if (new LocalDate(instant.getMillis(), GregorianChronology.getInstance(zone)).getYear() <= 0) {
                    throw new IllegalArgumentException("Cutover too early. Must be on or after 0001-01-01.");
                }
            }
            Map<DateTimeZone, ArrayList<GJChronology>> map = cCache;
            synchronized (map) {
                try {
                    ArrayList<GJChronology> arrayList = map.get(zone);
                    if (arrayList == null) {
                        arrayList = new ArrayList<>(2);
                        map.put(zone, arrayList);
                    } else {
                        int size = arrayList.size();
                        while (true) {
                            size--;
                            if (size < 0) {
                                break;
                            }
                            GJChronology gJChronology2 = arrayList.get(size);
                            if (i2 == gJChronology2.getMinimumDaysInFirstWeek() && instant.equals(gJChronology2.getGregorianCutover())) {
                                return gJChronology2;
                            }
                        }
                    }
                    DateTimeZone dateTimeZone2 = DateTimeZone.UTC;
                    if (zone == dateTimeZone2) {
                        gJChronology = new GJChronology(JulianChronology.getInstance(zone, i2), GregorianChronology.getInstance(zone, i2), instant);
                    } else {
                        GJChronology gJChronology3 = getInstance(dateTimeZone2, instant, i2);
                        gJChronology = new GJChronology(ZonedChronology.getInstance(gJChronology3, zone), gJChronology3.iJulianChronology, gJChronology3.iGregorianChronology, gJChronology3.iCutoverInstant);
                    }
                    arrayList.add(gJChronology);
                    return gJChronology;
                } finally {
                }
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    @Override // org.joda.time.chrono.AssembledChronology, org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public long getDateTimeMillis(int i2, int i3, int i4, int i5, int i6, int i7, int i8) throws IllegalArgumentException {
        long dateTimeMillis;
        Chronology base = getBase();
        if (base != null) {
            return base.getDateTimeMillis(i2, i3, i4, i5, i6, i7, i8);
        }
        try {
            dateTimeMillis = this.iGregorianChronology.getDateTimeMillis(i2, i3, i4, i5, i6, i7, i8);
        } catch (IllegalFieldValueException e2) {
            if (i3 == 2 && i4 == 29) {
                dateTimeMillis = this.iGregorianChronology.getDateTimeMillis(i2, i3, 28, i5, i6, i7, i8);
                if (dateTimeMillis >= this.iCutoverMillis) {
                    throw e2;
                }
            } else {
                throw e2;
            }
        }
        if (dateTimeMillis < this.iCutoverMillis) {
            dateTimeMillis = this.iJulianChronology.getDateTimeMillis(i2, i3, i4, i5, i6, i7, i8);
            if (dateTimeMillis >= this.iCutoverMillis) {
                throw new IllegalArgumentException("Specified date does not exist");
            }
        }
        return dateTimeMillis;
    }

    public static GJChronology getInstance(DateTimeZone dateTimeZone, long j2, int i2) {
        return getInstance(dateTimeZone, j2 == DEFAULT_CUTOVER.getMillis() ? null : new Instant(j2), i2);
    }
}
