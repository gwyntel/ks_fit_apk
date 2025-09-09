package org.joda.time;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Locale;
import org.joda.convert.FromString;
import org.joda.convert.ToString;
import org.joda.time.base.BaseDateTime;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.field.AbstractReadableInstantFieldProperty;
import org.joda.time.field.FieldUtils;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

/* loaded from: classes5.dex */
public class MutableDateTime extends BaseDateTime implements ReadWritableDateTime, Cloneable, Serializable {
    public static final int ROUND_CEILING = 2;
    public static final int ROUND_FLOOR = 1;
    public static final int ROUND_HALF_CEILING = 4;
    public static final int ROUND_HALF_EVEN = 5;
    public static final int ROUND_HALF_FLOOR = 3;
    public static final int ROUND_NONE = 0;
    private static final long serialVersionUID = 2852608688135209575L;
    private DateTimeField iRoundingField;
    private int iRoundingMode;

    public MutableDateTime() {
    }

    public static MutableDateTime now() {
        return new MutableDateTime();
    }

    @FromString
    public static MutableDateTime parse(String str) {
        return parse(str, ISODateTimeFormat.dateTimeParser().withOffsetParsed());
    }

    @Override // org.joda.time.ReadWritableInstant
    public void add(long j2) {
        setMillis(FieldUtils.safeAdd(getMillis(), j2));
    }

    @Override // org.joda.time.ReadWritableDateTime
    public void addDays(int i2) {
        setMillis(getChronology().days().add(getMillis(), i2));
    }

    @Override // org.joda.time.ReadWritableDateTime
    public void addHours(int i2) {
        setMillis(getChronology().hours().add(getMillis(), i2));
    }

    @Override // org.joda.time.ReadWritableDateTime
    public void addMillis(int i2) {
        setMillis(getChronology().millis().add(getMillis(), i2));
    }

    @Override // org.joda.time.ReadWritableDateTime
    public void addMinutes(int i2) {
        setMillis(getChronology().minutes().add(getMillis(), i2));
    }

    @Override // org.joda.time.ReadWritableDateTime
    public void addMonths(int i2) {
        setMillis(getChronology().months().add(getMillis(), i2));
    }

    @Override // org.joda.time.ReadWritableDateTime
    public void addSeconds(int i2) {
        setMillis(getChronology().seconds().add(getMillis(), i2));
    }

    @Override // org.joda.time.ReadWritableDateTime
    public void addWeeks(int i2) {
        setMillis(getChronology().weeks().add(getMillis(), i2));
    }

    @Override // org.joda.time.ReadWritableDateTime
    public void addWeekyears(int i2) {
        setMillis(getChronology().weekyears().add(getMillis(), i2));
    }

    @Override // org.joda.time.ReadWritableDateTime
    public void addYears(int i2) {
        setMillis(getChronology().years().add(getMillis(), i2));
    }

    public Property centuryOfEra() {
        return new Property(this, getChronology().centuryOfEra());
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException unused) {
            throw new InternalError("Clone error");
        }
    }

    public MutableDateTime copy() {
        return (MutableDateTime) clone();
    }

    public Property dayOfMonth() {
        return new Property(this, getChronology().dayOfMonth());
    }

    public Property dayOfWeek() {
        return new Property(this, getChronology().dayOfWeek());
    }

    public Property dayOfYear() {
        return new Property(this, getChronology().dayOfYear());
    }

    public Property era() {
        return new Property(this, getChronology().era());
    }

    public DateTimeField getRoundingField() {
        return this.iRoundingField;
    }

    public int getRoundingMode() {
        return this.iRoundingMode;
    }

    public Property hourOfDay() {
        return new Property(this, getChronology().hourOfDay());
    }

    public Property millisOfDay() {
        return new Property(this, getChronology().millisOfDay());
    }

    public Property millisOfSecond() {
        return new Property(this, getChronology().millisOfSecond());
    }

    public Property minuteOfDay() {
        return new Property(this, getChronology().minuteOfDay());
    }

    public Property minuteOfHour() {
        return new Property(this, getChronology().minuteOfHour());
    }

    public Property monthOfYear() {
        return new Property(this, getChronology().monthOfYear());
    }

    public Property property(DateTimeFieldType dateTimeFieldType) {
        if (dateTimeFieldType == null) {
            throw new IllegalArgumentException("The DateTimeFieldType must not be null");
        }
        DateTimeField field = dateTimeFieldType.getField(getChronology());
        if (field.isSupported()) {
            return new Property(this, field);
        }
        throw new IllegalArgumentException("Field '" + dateTimeFieldType + "' is not supported");
    }

    public Property secondOfDay() {
        return new Property(this, getChronology().secondOfDay());
    }

    public Property secondOfMinute() {
        return new Property(this, getChronology().secondOfMinute());
    }

    @Override // org.joda.time.ReadWritableInstant
    public void set(DateTimeFieldType dateTimeFieldType, int i2) {
        if (dateTimeFieldType == null) {
            throw new IllegalArgumentException("Field must not be null");
        }
        setMillis(dateTimeFieldType.getField(getChronology()).set(getMillis(), i2));
    }

    @Override // org.joda.time.base.BaseDateTime, org.joda.time.ReadWritableInstant
    public void setChronology(Chronology chronology) {
        super.setChronology(chronology);
    }

    public void setDate(long j2) {
        setMillis(getChronology().millisOfDay().set(j2, getMillisOfDay()));
    }

    @Override // org.joda.time.ReadWritableDateTime
    public void setDateTime(int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        setMillis(getChronology().getDateTimeMillis(i2, i3, i4, i5, i6, i7, i8));
    }

    @Override // org.joda.time.ReadWritableDateTime
    public void setDayOfMonth(int i2) {
        setMillis(getChronology().dayOfMonth().set(getMillis(), i2));
    }

    @Override // org.joda.time.ReadWritableDateTime
    public void setDayOfWeek(int i2) {
        setMillis(getChronology().dayOfWeek().set(getMillis(), i2));
    }

    @Override // org.joda.time.ReadWritableDateTime
    public void setDayOfYear(int i2) {
        setMillis(getChronology().dayOfYear().set(getMillis(), i2));
    }

    @Override // org.joda.time.ReadWritableDateTime
    public void setHourOfDay(int i2) {
        setMillis(getChronology().hourOfDay().set(getMillis(), i2));
    }

    @Override // org.joda.time.base.BaseDateTime, org.joda.time.ReadWritableInstant
    public void setMillis(long j2) {
        int i2 = this.iRoundingMode;
        if (i2 == 1) {
            j2 = this.iRoundingField.roundFloor(j2);
        } else if (i2 == 2) {
            j2 = this.iRoundingField.roundCeiling(j2);
        } else if (i2 == 3) {
            j2 = this.iRoundingField.roundHalfFloor(j2);
        } else if (i2 == 4) {
            j2 = this.iRoundingField.roundHalfCeiling(j2);
        } else if (i2 == 5) {
            j2 = this.iRoundingField.roundHalfEven(j2);
        }
        super.setMillis(j2);
    }

    @Override // org.joda.time.ReadWritableDateTime
    public void setMillisOfDay(int i2) {
        setMillis(getChronology().millisOfDay().set(getMillis(), i2));
    }

    @Override // org.joda.time.ReadWritableDateTime
    public void setMillisOfSecond(int i2) {
        setMillis(getChronology().millisOfSecond().set(getMillis(), i2));
    }

    @Override // org.joda.time.ReadWritableDateTime
    public void setMinuteOfDay(int i2) {
        setMillis(getChronology().minuteOfDay().set(getMillis(), i2));
    }

    @Override // org.joda.time.ReadWritableDateTime
    public void setMinuteOfHour(int i2) {
        setMillis(getChronology().minuteOfHour().set(getMillis(), i2));
    }

    @Override // org.joda.time.ReadWritableDateTime
    public void setMonthOfYear(int i2) {
        setMillis(getChronology().monthOfYear().set(getMillis(), i2));
    }

    public void setRounding(DateTimeField dateTimeField) {
        setRounding(dateTimeField, 1);
    }

    @Override // org.joda.time.ReadWritableDateTime
    public void setSecondOfDay(int i2) {
        setMillis(getChronology().secondOfDay().set(getMillis(), i2));
    }

    @Override // org.joda.time.ReadWritableDateTime
    public void setSecondOfMinute(int i2) {
        setMillis(getChronology().secondOfMinute().set(getMillis(), i2));
    }

    public void setTime(long j2) {
        setMillis(getChronology().millisOfDay().set(getMillis(), ISOChronology.getInstanceUTC().millisOfDay().get(j2)));
    }

    @Override // org.joda.time.ReadWritableDateTime
    public void setWeekOfWeekyear(int i2) {
        setMillis(getChronology().weekOfWeekyear().set(getMillis(), i2));
    }

    @Override // org.joda.time.ReadWritableDateTime
    public void setWeekyear(int i2) {
        setMillis(getChronology().weekyear().set(getMillis(), i2));
    }

    @Override // org.joda.time.ReadWritableDateTime
    public void setYear(int i2) {
        setMillis(getChronology().year().set(getMillis(), i2));
    }

    @Override // org.joda.time.ReadWritableInstant
    public void setZone(DateTimeZone dateTimeZone) {
        DateTimeZone zone = DateTimeUtils.getZone(dateTimeZone);
        Chronology chronology = getChronology();
        if (chronology.getZone() != zone) {
            setChronology(chronology.withZone(zone));
        }
    }

    @Override // org.joda.time.ReadWritableInstant
    public void setZoneRetainFields(DateTimeZone dateTimeZone) {
        DateTimeZone zone = DateTimeUtils.getZone(dateTimeZone);
        DateTimeZone zone2 = DateTimeUtils.getZone(getZone());
        if (zone == zone2) {
            return;
        }
        long millisKeepLocal = zone2.getMillisKeepLocal(zone, getMillis());
        setChronology(getChronology().withZone(zone));
        setMillis(millisKeepLocal);
    }

    @Override // org.joda.time.base.AbstractInstant, org.joda.time.ReadableInstant
    @ToString
    public String toString() {
        return ISODateTimeFormat.dateTime().print(this);
    }

    public Property weekOfWeekyear() {
        return new Property(this, getChronology().weekOfWeekyear());
    }

    public Property weekyear() {
        return new Property(this, getChronology().weekyear());
    }

    public Property year() {
        return new Property(this, getChronology().year());
    }

    public Property yearOfCentury() {
        return new Property(this, getChronology().yearOfCentury());
    }

    public Property yearOfEra() {
        return new Property(this, getChronology().yearOfEra());
    }

    public static final class Property extends AbstractReadableInstantFieldProperty {
        private static final long serialVersionUID = -4481126543819298617L;
        private DateTimeField iField;
        private MutableDateTime iInstant;

        Property(MutableDateTime mutableDateTime, DateTimeField dateTimeField) {
            this.iInstant = mutableDateTime;
            this.iField = dateTimeField;
        }

        private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            this.iInstant = (MutableDateTime) objectInputStream.readObject();
            this.iField = ((DateTimeFieldType) objectInputStream.readObject()).getField(this.iInstant.getChronology());
        }

        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            objectOutputStream.writeObject(this.iInstant);
            objectOutputStream.writeObject(this.iField.getType());
        }

        public MutableDateTime add(int i2) {
            this.iInstant.setMillis(getField().add(this.iInstant.getMillis(), i2));
            return this.iInstant;
        }

        public MutableDateTime addWrapField(int i2) {
            this.iInstant.setMillis(getField().addWrapField(this.iInstant.getMillis(), i2));
            return this.iInstant;
        }

        @Override // org.joda.time.field.AbstractReadableInstantFieldProperty
        protected Chronology getChronology() {
            return this.iInstant.getChronology();
        }

        @Override // org.joda.time.field.AbstractReadableInstantFieldProperty
        public DateTimeField getField() {
            return this.iField;
        }

        @Override // org.joda.time.field.AbstractReadableInstantFieldProperty
        protected long getMillis() {
            return this.iInstant.getMillis();
        }

        public MutableDateTime getMutableDateTime() {
            return this.iInstant;
        }

        public MutableDateTime roundCeiling() {
            this.iInstant.setMillis(getField().roundCeiling(this.iInstant.getMillis()));
            return this.iInstant;
        }

        public MutableDateTime roundFloor() {
            this.iInstant.setMillis(getField().roundFloor(this.iInstant.getMillis()));
            return this.iInstant;
        }

        public MutableDateTime roundHalfCeiling() {
            this.iInstant.setMillis(getField().roundHalfCeiling(this.iInstant.getMillis()));
            return this.iInstant;
        }

        public MutableDateTime roundHalfEven() {
            this.iInstant.setMillis(getField().roundHalfEven(this.iInstant.getMillis()));
            return this.iInstant;
        }

        public MutableDateTime roundHalfFloor() {
            this.iInstant.setMillis(getField().roundHalfFloor(this.iInstant.getMillis()));
            return this.iInstant;
        }

        public MutableDateTime set(int i2) {
            this.iInstant.setMillis(getField().set(this.iInstant.getMillis(), i2));
            return this.iInstant;
        }

        public MutableDateTime add(long j2) {
            this.iInstant.setMillis(getField().add(this.iInstant.getMillis(), j2));
            return this.iInstant;
        }

        public MutableDateTime set(String str, Locale locale) {
            this.iInstant.setMillis(getField().set(this.iInstant.getMillis(), str, locale));
            return this.iInstant;
        }

        public MutableDateTime set(String str) {
            set(str, null);
            return this.iInstant;
        }
    }

    public MutableDateTime(DateTimeZone dateTimeZone) {
        super(dateTimeZone);
    }

    public static MutableDateTime now(DateTimeZone dateTimeZone) {
        if (dateTimeZone != null) {
            return new MutableDateTime(dateTimeZone);
        }
        throw new NullPointerException("Zone must not be null");
    }

    public static MutableDateTime parse(String str, DateTimeFormatter dateTimeFormatter) {
        return dateTimeFormatter.parseDateTime(str).toMutableDateTime();
    }

    @Override // org.joda.time.ReadWritableInstant
    public void add(ReadableDuration readableDuration) {
        add(readableDuration, 1);
    }

    public void setDate(ReadableInstant readableInstant) {
        DateTimeZone zone;
        long instantMillis = DateTimeUtils.getInstantMillis(readableInstant);
        if ((readableInstant instanceof ReadableDateTime) && (zone = DateTimeUtils.getChronology(((ReadableDateTime) readableInstant).getChronology()).getZone()) != null) {
            instantMillis = zone.getMillisKeepLocal(getZone(), instantMillis);
        }
        setDate(instantMillis);
    }

    public void setRounding(DateTimeField dateTimeField, int i2) {
        if (dateTimeField != null && (i2 < 0 || i2 > 5)) {
            throw new IllegalArgumentException("Illegal rounding mode: " + i2);
        }
        this.iRoundingField = i2 == 0 ? null : dateTimeField;
        if (dateTimeField == null) {
            i2 = 0;
        }
        this.iRoundingMode = i2;
        setMillis(getMillis());
    }

    public MutableDateTime(Chronology chronology) {
        super(chronology);
    }

    @Override // org.joda.time.ReadWritableInstant
    public void add(ReadableDuration readableDuration, int i2) {
        if (readableDuration != null) {
            add(FieldUtils.safeMultiply(readableDuration.getMillis(), i2));
        }
    }

    public void setTime(ReadableInstant readableInstant) {
        long instantMillis = DateTimeUtils.getInstantMillis(readableInstant);
        DateTimeZone zone = DateTimeUtils.getInstantChronology(readableInstant).getZone();
        if (zone != null) {
            instantMillis = zone.getMillisKeepLocal(DateTimeZone.UTC, instantMillis);
        }
        setTime(instantMillis);
    }

    public MutableDateTime(long j2) {
        super(j2);
    }

    public static MutableDateTime now(Chronology chronology) {
        if (chronology != null) {
            return new MutableDateTime(chronology);
        }
        throw new NullPointerException("Chronology must not be null");
    }

    @Override // org.joda.time.ReadWritableInstant
    public void add(ReadablePeriod readablePeriod) {
        add(readablePeriod, 1);
    }

    public MutableDateTime(long j2, DateTimeZone dateTimeZone) {
        super(j2, dateTimeZone);
    }

    @Override // org.joda.time.ReadWritableInstant
    public void add(ReadablePeriod readablePeriod, int i2) {
        if (readablePeriod != null) {
            setMillis(getChronology().add(readablePeriod, getMillis(), i2));
        }
    }

    public MutableDateTime(long j2, Chronology chronology) {
        super(j2, chronology);
    }

    @Override // org.joda.time.ReadWritableInstant
    public void add(DurationFieldType durationFieldType, int i2) {
        if (durationFieldType != null) {
            setMillis(durationFieldType.getField(getChronology()).add(getMillis(), i2));
            return;
        }
        throw new IllegalArgumentException("Field must not be null");
    }

    public MutableDateTime(Object obj) {
        super(obj, (Chronology) null);
    }

    public MutableDateTime(Object obj, DateTimeZone dateTimeZone) {
        super(obj, dateTimeZone);
    }

    @Override // org.joda.time.ReadWritableInstant
    public void setMillis(ReadableInstant readableInstant) {
        setMillis(DateTimeUtils.getInstantMillis(readableInstant));
    }

    @Override // org.joda.time.ReadWritableDateTime
    public void setTime(int i2, int i3, int i4, int i5) {
        setMillis(getChronology().getDateTimeMillis(getMillis(), i2, i3, i4, i5));
    }

    public MutableDateTime(Object obj, Chronology chronology) {
        super(obj, DateTimeUtils.getChronology(chronology));
    }

    @Override // org.joda.time.ReadWritableDateTime
    public void setDate(int i2, int i3, int i4) {
        setDate(getChronology().getDateTimeMillis(i2, i3, i4, 0));
    }

    public MutableDateTime(int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        super(i2, i3, i4, i5, i6, i7, i8);
    }

    public MutableDateTime(int i2, int i3, int i4, int i5, int i6, int i7, int i8, DateTimeZone dateTimeZone) {
        super(i2, i3, i4, i5, i6, i7, i8, dateTimeZone);
    }

    public MutableDateTime(int i2, int i3, int i4, int i5, int i6, int i7, int i8, Chronology chronology) {
        super(i2, i3, i4, i5, i6, i7, i8, chronology);
    }
}
