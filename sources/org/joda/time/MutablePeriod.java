package org.joda.time;

import java.io.Serializable;
import org.joda.convert.FromString;
import org.joda.time.base.BasePeriod;
import org.joda.time.field.FieldUtils;
import org.joda.time.format.ISOPeriodFormat;
import org.joda.time.format.PeriodFormatter;

/* loaded from: classes5.dex */
public class MutablePeriod extends BasePeriod implements ReadWritablePeriod, Cloneable, Serializable {
    private static final long serialVersionUID = 3436451121567212165L;

    public MutablePeriod() {
        super(0L, (PeriodType) null, (Chronology) null);
    }

    @FromString
    public static MutablePeriod parse(String str) {
        return parse(str, ISOPeriodFormat.standard());
    }

    @Override // org.joda.time.ReadWritablePeriod
    public void add(DurationFieldType durationFieldType, int i2) {
        super.addField(durationFieldType, i2);
    }

    @Override // org.joda.time.ReadWritablePeriod
    public void addDays(int i2) {
        super.addField(DurationFieldType.days(), i2);
    }

    @Override // org.joda.time.ReadWritablePeriod
    public void addHours(int i2) {
        super.addField(DurationFieldType.hours(), i2);
    }

    @Override // org.joda.time.ReadWritablePeriod
    public void addMillis(int i2) {
        super.addField(DurationFieldType.millis(), i2);
    }

    @Override // org.joda.time.ReadWritablePeriod
    public void addMinutes(int i2) {
        super.addField(DurationFieldType.minutes(), i2);
    }

    @Override // org.joda.time.ReadWritablePeriod
    public void addMonths(int i2) {
        super.addField(DurationFieldType.months(), i2);
    }

    @Override // org.joda.time.ReadWritablePeriod
    public void addSeconds(int i2) {
        super.addField(DurationFieldType.seconds(), i2);
    }

    @Override // org.joda.time.ReadWritablePeriod
    public void addWeeks(int i2) {
        super.addField(DurationFieldType.weeks(), i2);
    }

    @Override // org.joda.time.ReadWritablePeriod
    public void addYears(int i2) {
        super.addField(DurationFieldType.years(), i2);
    }

    @Override // org.joda.time.ReadWritablePeriod
    public void clear() {
        super.setValues(new int[size()]);
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException unused) {
            throw new InternalError("Clone error");
        }
    }

    public MutablePeriod copy() {
        return (MutablePeriod) clone();
    }

    public int getDays() {
        return getPeriodType().getIndexedField(this, PeriodType.DAY_INDEX);
    }

    public int getHours() {
        return getPeriodType().getIndexedField(this, PeriodType.HOUR_INDEX);
    }

    public int getMillis() {
        return getPeriodType().getIndexedField(this, PeriodType.MILLI_INDEX);
    }

    public int getMinutes() {
        return getPeriodType().getIndexedField(this, PeriodType.MINUTE_INDEX);
    }

    public int getMonths() {
        return getPeriodType().getIndexedField(this, PeriodType.MONTH_INDEX);
    }

    public int getSeconds() {
        return getPeriodType().getIndexedField(this, PeriodType.SECOND_INDEX);
    }

    public int getWeeks() {
        return getPeriodType().getIndexedField(this, PeriodType.WEEK_INDEX);
    }

    public int getYears() {
        return getPeriodType().getIndexedField(this, PeriodType.YEAR_INDEX);
    }

    @Override // org.joda.time.base.BasePeriod
    public void mergePeriod(ReadablePeriod readablePeriod) {
        super.mergePeriod(readablePeriod);
    }

    @Override // org.joda.time.ReadWritablePeriod
    public void set(DurationFieldType durationFieldType, int i2) {
        super.setField(durationFieldType, i2);
    }

    @Override // org.joda.time.ReadWritablePeriod
    public void setDays(int i2) {
        super.setField(DurationFieldType.days(), i2);
    }

    @Override // org.joda.time.ReadWritablePeriod
    public void setHours(int i2) {
        super.setField(DurationFieldType.hours(), i2);
    }

    @Override // org.joda.time.ReadWritablePeriod
    public void setMillis(int i2) {
        super.setField(DurationFieldType.millis(), i2);
    }

    @Override // org.joda.time.ReadWritablePeriod
    public void setMinutes(int i2) {
        super.setField(DurationFieldType.minutes(), i2);
    }

    @Override // org.joda.time.ReadWritablePeriod
    public void setMonths(int i2) {
        super.setField(DurationFieldType.months(), i2);
    }

    @Override // org.joda.time.base.BasePeriod, org.joda.time.ReadWritablePeriod
    public void setPeriod(ReadablePeriod readablePeriod) {
        super.setPeriod(readablePeriod);
    }

    @Override // org.joda.time.ReadWritablePeriod
    public void setSeconds(int i2) {
        super.setField(DurationFieldType.seconds(), i2);
    }

    @Override // org.joda.time.base.BasePeriod, org.joda.time.ReadWritablePeriod
    public void setValue(int i2, int i3) {
        super.setValue(i2, i3);
    }

    @Override // org.joda.time.ReadWritablePeriod
    public void setWeeks(int i2) {
        super.setField(DurationFieldType.weeks(), i2);
    }

    @Override // org.joda.time.ReadWritablePeriod
    public void setYears(int i2) {
        super.setField(DurationFieldType.years(), i2);
    }

    public MutablePeriod(PeriodType periodType) {
        super(0L, periodType, (Chronology) null);
    }

    public static MutablePeriod parse(String str, PeriodFormatter periodFormatter) {
        return periodFormatter.parsePeriod(str).toMutablePeriod();
    }

    @Override // org.joda.time.ReadWritablePeriod
    public void add(ReadablePeriod readablePeriod) {
        super.addPeriod(readablePeriod);
    }

    @Override // org.joda.time.base.BasePeriod, org.joda.time.ReadWritablePeriod
    public void setPeriod(int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
        super.setPeriod(i2, i3, i4, i5, i6, i7, i8, i9);
    }

    public MutablePeriod(int i2, int i3, int i4, int i5) {
        super(0, 0, 0, 0, i2, i3, i4, i5, PeriodType.standard());
    }

    @Override // org.joda.time.ReadWritablePeriod
    public void add(int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
        setPeriod(FieldUtils.safeAdd(getYears(), i2), FieldUtils.safeAdd(getMonths(), i3), FieldUtils.safeAdd(getWeeks(), i4), FieldUtils.safeAdd(getDays(), i5), FieldUtils.safeAdd(getHours(), i6), FieldUtils.safeAdd(getMinutes(), i7), FieldUtils.safeAdd(getSeconds(), i8), FieldUtils.safeAdd(getMillis(), i9));
    }

    @Override // org.joda.time.ReadWritablePeriod
    public void setPeriod(ReadableInterval readableInterval) {
        if (readableInterval == null) {
            setPeriod(0L);
        } else {
            setPeriod(readableInterval.getStartMillis(), readableInterval.getEndMillis(), DateTimeUtils.getChronology(readableInterval.getChronology()));
        }
    }

    public MutablePeriod(int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
        super(i2, i3, i4, i5, i6, i7, i8, i9, PeriodType.standard());
    }

    @Override // org.joda.time.ReadWritablePeriod
    public void add(ReadableInterval readableInterval) {
        if (readableInterval != null) {
            add(readableInterval.toPeriod(getPeriodType()));
        }
    }

    public MutablePeriod(int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, PeriodType periodType) {
        super(i2, i3, i4, i5, i6, i7, i8, i9, periodType);
    }

    public void add(ReadableDuration readableDuration) {
        if (readableDuration != null) {
            add(new Period(readableDuration.getMillis(), getPeriodType()));
        }
    }

    public MutablePeriod(long j2) {
        super(j2);
    }

    public void add(long j2) {
        add(new Period(j2, getPeriodType()));
    }

    public void setPeriod(ReadableInstant readableInstant, ReadableInstant readableInstant2) {
        if (readableInstant == readableInstant2) {
            setPeriod(0L);
        } else {
            setPeriod(DateTimeUtils.getInstantMillis(readableInstant), DateTimeUtils.getInstantMillis(readableInstant2), DateTimeUtils.getIntervalChronology(readableInstant, readableInstant2));
        }
    }

    public MutablePeriod(long j2, PeriodType periodType) {
        super(j2, periodType, (Chronology) null);
    }

    public void add(long j2, Chronology chronology) {
        add(new Period(j2, getPeriodType(), chronology));
    }

    public MutablePeriod(long j2, Chronology chronology) {
        super(j2, (PeriodType) null, chronology);
    }

    public MutablePeriod(long j2, PeriodType periodType, Chronology chronology) {
        super(j2, periodType, chronology);
    }

    public MutablePeriod(long j2, long j3) {
        super(j2, j3, null, null);
    }

    public MutablePeriod(long j2, long j3, PeriodType periodType) {
        super(j2, j3, periodType, null);
    }

    public void setPeriod(long j2, long j3) {
        setPeriod(j2, j3, null);
    }

    public MutablePeriod(long j2, long j3, Chronology chronology) {
        super(j2, j3, null, chronology);
    }

    public void setPeriod(long j2, long j3, Chronology chronology) {
        setValues(DateTimeUtils.getChronology(chronology).get(this, j2, j3));
    }

    public MutablePeriod(long j2, long j3, PeriodType periodType, Chronology chronology) {
        super(j2, j3, periodType, chronology);
    }

    public MutablePeriod(ReadableInstant readableInstant, ReadableInstant readableInstant2) {
        super(readableInstant, readableInstant2, (PeriodType) null);
    }

    public void setPeriod(ReadableDuration readableDuration) {
        setPeriod(readableDuration, (Chronology) null);
    }

    public MutablePeriod(ReadableInstant readableInstant, ReadableInstant readableInstant2, PeriodType periodType) {
        super(readableInstant, readableInstant2, periodType);
    }

    public void setPeriod(ReadableDuration readableDuration, Chronology chronology) {
        setPeriod(DateTimeUtils.getDurationMillis(readableDuration), chronology);
    }

    public MutablePeriod(ReadableInstant readableInstant, ReadableDuration readableDuration) {
        super(readableInstant, readableDuration, (PeriodType) null);
    }

    public MutablePeriod(ReadableInstant readableInstant, ReadableDuration readableDuration, PeriodType periodType) {
        super(readableInstant, readableDuration, periodType);
    }

    public void setPeriod(long j2) {
        setPeriod(j2, (Chronology) null);
    }

    public MutablePeriod(ReadableDuration readableDuration, ReadableInstant readableInstant) {
        super(readableDuration, readableInstant, (PeriodType) null);
    }

    public void setPeriod(long j2, Chronology chronology) {
        setValues(DateTimeUtils.getChronology(chronology).get(this, j2));
    }

    public MutablePeriod(ReadableDuration readableDuration, ReadableInstant readableInstant, PeriodType periodType) {
        super(readableDuration, readableInstant, periodType);
    }

    public MutablePeriod(Object obj) {
        super(obj, (PeriodType) null, (Chronology) null);
    }

    public MutablePeriod(Object obj, PeriodType periodType) {
        super(obj, periodType, (Chronology) null);
    }

    public MutablePeriod(Object obj, Chronology chronology) {
        super(obj, (PeriodType) null, chronology);
    }

    public MutablePeriod(Object obj, PeriodType periodType, Chronology chronology) {
        super(obj, periodType, chronology);
    }
}
