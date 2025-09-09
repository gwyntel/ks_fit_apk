package org.joda.time.chrono;

import java.io.IOException;
import java.io.ObjectInputStream;
import org.joda.time.Chronology;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeZone;
import org.joda.time.DurationField;

/* loaded from: classes5.dex */
public abstract class AssembledChronology extends BaseChronology {
    private static final long serialVersionUID = -6728465968995518215L;
    private final Chronology iBase;
    private transient int iBaseFlags;
    private transient DurationField iCenturies;
    private transient DateTimeField iCenturyOfEra;
    private transient DateTimeField iClockhourOfDay;
    private transient DateTimeField iClockhourOfHalfday;
    private transient DateTimeField iDayOfMonth;
    private transient DateTimeField iDayOfWeek;
    private transient DateTimeField iDayOfYear;
    private transient DurationField iDays;
    private transient DateTimeField iEra;
    private transient DurationField iEras;
    private transient DateTimeField iHalfdayOfDay;
    private transient DurationField iHalfdays;
    private transient DateTimeField iHourOfDay;
    private transient DateTimeField iHourOfHalfday;
    private transient DurationField iHours;
    private transient DurationField iMillis;
    private transient DateTimeField iMillisOfDay;
    private transient DateTimeField iMillisOfSecond;
    private transient DateTimeField iMinuteOfDay;
    private transient DateTimeField iMinuteOfHour;
    private transient DurationField iMinutes;
    private transient DateTimeField iMonthOfYear;
    private transient DurationField iMonths;
    private final Object iParam;
    private transient DateTimeField iSecondOfDay;
    private transient DateTimeField iSecondOfMinute;
    private transient DurationField iSeconds;
    private transient DateTimeField iWeekOfWeekyear;
    private transient DurationField iWeeks;
    private transient DateTimeField iWeekyear;
    private transient DateTimeField iWeekyearOfCentury;
    private transient DurationField iWeekyears;
    private transient DateTimeField iYear;
    private transient DateTimeField iYearOfCentury;
    private transient DateTimeField iYearOfEra;
    private transient DurationField iYears;

    public static final class Fields {
        public DurationField centuries;
        public DateTimeField centuryOfEra;
        public DateTimeField clockhourOfDay;
        public DateTimeField clockhourOfHalfday;
        public DateTimeField dayOfMonth;
        public DateTimeField dayOfWeek;
        public DateTimeField dayOfYear;
        public DurationField days;
        public DateTimeField era;
        public DurationField eras;
        public DateTimeField halfdayOfDay;
        public DurationField halfdays;
        public DateTimeField hourOfDay;
        public DateTimeField hourOfHalfday;
        public DurationField hours;
        public DurationField millis;
        public DateTimeField millisOfDay;
        public DateTimeField millisOfSecond;
        public DateTimeField minuteOfDay;
        public DateTimeField minuteOfHour;
        public DurationField minutes;
        public DateTimeField monthOfYear;
        public DurationField months;
        public DateTimeField secondOfDay;
        public DateTimeField secondOfMinute;
        public DurationField seconds;
        public DateTimeField weekOfWeekyear;
        public DurationField weeks;
        public DateTimeField weekyear;
        public DateTimeField weekyearOfCentury;
        public DurationField weekyears;
        public DateTimeField year;
        public DateTimeField yearOfCentury;
        public DateTimeField yearOfEra;
        public DurationField years;

        Fields() {
        }

        private static boolean isSupported(DurationField durationField) {
            if (durationField == null) {
                return false;
            }
            return durationField.isSupported();
        }

        public void copyFieldsFrom(Chronology chronology) {
            DurationField durationFieldMillis = chronology.millis();
            if (isSupported(durationFieldMillis)) {
                this.millis = durationFieldMillis;
            }
            DurationField durationFieldSeconds = chronology.seconds();
            if (isSupported(durationFieldSeconds)) {
                this.seconds = durationFieldSeconds;
            }
            DurationField durationFieldMinutes = chronology.minutes();
            if (isSupported(durationFieldMinutes)) {
                this.minutes = durationFieldMinutes;
            }
            DurationField durationFieldHours = chronology.hours();
            if (isSupported(durationFieldHours)) {
                this.hours = durationFieldHours;
            }
            DurationField durationFieldHalfdays = chronology.halfdays();
            if (isSupported(durationFieldHalfdays)) {
                this.halfdays = durationFieldHalfdays;
            }
            DurationField durationFieldDays = chronology.days();
            if (isSupported(durationFieldDays)) {
                this.days = durationFieldDays;
            }
            DurationField durationFieldWeeks = chronology.weeks();
            if (isSupported(durationFieldWeeks)) {
                this.weeks = durationFieldWeeks;
            }
            DurationField durationFieldWeekyears = chronology.weekyears();
            if (isSupported(durationFieldWeekyears)) {
                this.weekyears = durationFieldWeekyears;
            }
            DurationField durationFieldMonths = chronology.months();
            if (isSupported(durationFieldMonths)) {
                this.months = durationFieldMonths;
            }
            DurationField durationFieldYears = chronology.years();
            if (isSupported(durationFieldYears)) {
                this.years = durationFieldYears;
            }
            DurationField durationFieldCenturies = chronology.centuries();
            if (isSupported(durationFieldCenturies)) {
                this.centuries = durationFieldCenturies;
            }
            DurationField durationFieldEras = chronology.eras();
            if (isSupported(durationFieldEras)) {
                this.eras = durationFieldEras;
            }
            DateTimeField dateTimeFieldMillisOfSecond = chronology.millisOfSecond();
            if (isSupported(dateTimeFieldMillisOfSecond)) {
                this.millisOfSecond = dateTimeFieldMillisOfSecond;
            }
            DateTimeField dateTimeFieldMillisOfDay = chronology.millisOfDay();
            if (isSupported(dateTimeFieldMillisOfDay)) {
                this.millisOfDay = dateTimeFieldMillisOfDay;
            }
            DateTimeField dateTimeFieldSecondOfMinute = chronology.secondOfMinute();
            if (isSupported(dateTimeFieldSecondOfMinute)) {
                this.secondOfMinute = dateTimeFieldSecondOfMinute;
            }
            DateTimeField dateTimeFieldSecondOfDay = chronology.secondOfDay();
            if (isSupported(dateTimeFieldSecondOfDay)) {
                this.secondOfDay = dateTimeFieldSecondOfDay;
            }
            DateTimeField dateTimeFieldMinuteOfHour = chronology.minuteOfHour();
            if (isSupported(dateTimeFieldMinuteOfHour)) {
                this.minuteOfHour = dateTimeFieldMinuteOfHour;
            }
            DateTimeField dateTimeFieldMinuteOfDay = chronology.minuteOfDay();
            if (isSupported(dateTimeFieldMinuteOfDay)) {
                this.minuteOfDay = dateTimeFieldMinuteOfDay;
            }
            DateTimeField dateTimeFieldHourOfDay = chronology.hourOfDay();
            if (isSupported(dateTimeFieldHourOfDay)) {
                this.hourOfDay = dateTimeFieldHourOfDay;
            }
            DateTimeField dateTimeFieldClockhourOfDay = chronology.clockhourOfDay();
            if (isSupported(dateTimeFieldClockhourOfDay)) {
                this.clockhourOfDay = dateTimeFieldClockhourOfDay;
            }
            DateTimeField dateTimeFieldHourOfHalfday = chronology.hourOfHalfday();
            if (isSupported(dateTimeFieldHourOfHalfday)) {
                this.hourOfHalfday = dateTimeFieldHourOfHalfday;
            }
            DateTimeField dateTimeFieldClockhourOfHalfday = chronology.clockhourOfHalfday();
            if (isSupported(dateTimeFieldClockhourOfHalfday)) {
                this.clockhourOfHalfday = dateTimeFieldClockhourOfHalfday;
            }
            DateTimeField dateTimeFieldHalfdayOfDay = chronology.halfdayOfDay();
            if (isSupported(dateTimeFieldHalfdayOfDay)) {
                this.halfdayOfDay = dateTimeFieldHalfdayOfDay;
            }
            DateTimeField dateTimeFieldDayOfWeek = chronology.dayOfWeek();
            if (isSupported(dateTimeFieldDayOfWeek)) {
                this.dayOfWeek = dateTimeFieldDayOfWeek;
            }
            DateTimeField dateTimeFieldDayOfMonth = chronology.dayOfMonth();
            if (isSupported(dateTimeFieldDayOfMonth)) {
                this.dayOfMonth = dateTimeFieldDayOfMonth;
            }
            DateTimeField dateTimeFieldDayOfYear = chronology.dayOfYear();
            if (isSupported(dateTimeFieldDayOfYear)) {
                this.dayOfYear = dateTimeFieldDayOfYear;
            }
            DateTimeField dateTimeFieldWeekOfWeekyear = chronology.weekOfWeekyear();
            if (isSupported(dateTimeFieldWeekOfWeekyear)) {
                this.weekOfWeekyear = dateTimeFieldWeekOfWeekyear;
            }
            DateTimeField dateTimeFieldWeekyear = chronology.weekyear();
            if (isSupported(dateTimeFieldWeekyear)) {
                this.weekyear = dateTimeFieldWeekyear;
            }
            DateTimeField dateTimeFieldWeekyearOfCentury = chronology.weekyearOfCentury();
            if (isSupported(dateTimeFieldWeekyearOfCentury)) {
                this.weekyearOfCentury = dateTimeFieldWeekyearOfCentury;
            }
            DateTimeField dateTimeFieldMonthOfYear = chronology.monthOfYear();
            if (isSupported(dateTimeFieldMonthOfYear)) {
                this.monthOfYear = dateTimeFieldMonthOfYear;
            }
            DateTimeField dateTimeFieldYear = chronology.year();
            if (isSupported(dateTimeFieldYear)) {
                this.year = dateTimeFieldYear;
            }
            DateTimeField dateTimeFieldYearOfEra = chronology.yearOfEra();
            if (isSupported(dateTimeFieldYearOfEra)) {
                this.yearOfEra = dateTimeFieldYearOfEra;
            }
            DateTimeField dateTimeFieldYearOfCentury = chronology.yearOfCentury();
            if (isSupported(dateTimeFieldYearOfCentury)) {
                this.yearOfCentury = dateTimeFieldYearOfCentury;
            }
            DateTimeField dateTimeFieldCenturyOfEra = chronology.centuryOfEra();
            if (isSupported(dateTimeFieldCenturyOfEra)) {
                this.centuryOfEra = dateTimeFieldCenturyOfEra;
            }
            DateTimeField dateTimeFieldEra = chronology.era();
            if (isSupported(dateTimeFieldEra)) {
                this.era = dateTimeFieldEra;
            }
        }

        private static boolean isSupported(DateTimeField dateTimeField) {
            if (dateTimeField == null) {
                return false;
            }
            return dateTimeField.isSupported();
        }
    }

    protected AssembledChronology(Chronology chronology, Object obj) {
        this.iBase = chronology;
        this.iParam = obj;
        setFields();
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        setFields();
    }

    private void setFields() {
        Fields fields = new Fields();
        Chronology chronology = this.iBase;
        if (chronology != null) {
            fields.copyFieldsFrom(chronology);
        }
        assemble(fields);
        DurationField durationFieldMillis = fields.millis;
        if (durationFieldMillis == null) {
            durationFieldMillis = super.millis();
        }
        this.iMillis = durationFieldMillis;
        DurationField durationFieldSeconds = fields.seconds;
        if (durationFieldSeconds == null) {
            durationFieldSeconds = super.seconds();
        }
        this.iSeconds = durationFieldSeconds;
        DurationField durationFieldMinutes = fields.minutes;
        if (durationFieldMinutes == null) {
            durationFieldMinutes = super.minutes();
        }
        this.iMinutes = durationFieldMinutes;
        DurationField durationFieldHours = fields.hours;
        if (durationFieldHours == null) {
            durationFieldHours = super.hours();
        }
        this.iHours = durationFieldHours;
        DurationField durationFieldHalfdays = fields.halfdays;
        if (durationFieldHalfdays == null) {
            durationFieldHalfdays = super.halfdays();
        }
        this.iHalfdays = durationFieldHalfdays;
        DurationField durationFieldDays = fields.days;
        if (durationFieldDays == null) {
            durationFieldDays = super.days();
        }
        this.iDays = durationFieldDays;
        DurationField durationFieldWeeks = fields.weeks;
        if (durationFieldWeeks == null) {
            durationFieldWeeks = super.weeks();
        }
        this.iWeeks = durationFieldWeeks;
        DurationField durationFieldWeekyears = fields.weekyears;
        if (durationFieldWeekyears == null) {
            durationFieldWeekyears = super.weekyears();
        }
        this.iWeekyears = durationFieldWeekyears;
        DurationField durationFieldMonths = fields.months;
        if (durationFieldMonths == null) {
            durationFieldMonths = super.months();
        }
        this.iMonths = durationFieldMonths;
        DurationField durationFieldYears = fields.years;
        if (durationFieldYears == null) {
            durationFieldYears = super.years();
        }
        this.iYears = durationFieldYears;
        DurationField durationFieldCenturies = fields.centuries;
        if (durationFieldCenturies == null) {
            durationFieldCenturies = super.centuries();
        }
        this.iCenturies = durationFieldCenturies;
        DurationField durationFieldEras = fields.eras;
        if (durationFieldEras == null) {
            durationFieldEras = super.eras();
        }
        this.iEras = durationFieldEras;
        DateTimeField dateTimeFieldMillisOfSecond = fields.millisOfSecond;
        if (dateTimeFieldMillisOfSecond == null) {
            dateTimeFieldMillisOfSecond = super.millisOfSecond();
        }
        this.iMillisOfSecond = dateTimeFieldMillisOfSecond;
        DateTimeField dateTimeFieldMillisOfDay = fields.millisOfDay;
        if (dateTimeFieldMillisOfDay == null) {
            dateTimeFieldMillisOfDay = super.millisOfDay();
        }
        this.iMillisOfDay = dateTimeFieldMillisOfDay;
        DateTimeField dateTimeFieldSecondOfMinute = fields.secondOfMinute;
        if (dateTimeFieldSecondOfMinute == null) {
            dateTimeFieldSecondOfMinute = super.secondOfMinute();
        }
        this.iSecondOfMinute = dateTimeFieldSecondOfMinute;
        DateTimeField dateTimeFieldSecondOfDay = fields.secondOfDay;
        if (dateTimeFieldSecondOfDay == null) {
            dateTimeFieldSecondOfDay = super.secondOfDay();
        }
        this.iSecondOfDay = dateTimeFieldSecondOfDay;
        DateTimeField dateTimeFieldMinuteOfHour = fields.minuteOfHour;
        if (dateTimeFieldMinuteOfHour == null) {
            dateTimeFieldMinuteOfHour = super.minuteOfHour();
        }
        this.iMinuteOfHour = dateTimeFieldMinuteOfHour;
        DateTimeField dateTimeFieldMinuteOfDay = fields.minuteOfDay;
        if (dateTimeFieldMinuteOfDay == null) {
            dateTimeFieldMinuteOfDay = super.minuteOfDay();
        }
        this.iMinuteOfDay = dateTimeFieldMinuteOfDay;
        DateTimeField dateTimeFieldHourOfDay = fields.hourOfDay;
        if (dateTimeFieldHourOfDay == null) {
            dateTimeFieldHourOfDay = super.hourOfDay();
        }
        this.iHourOfDay = dateTimeFieldHourOfDay;
        DateTimeField dateTimeFieldClockhourOfDay = fields.clockhourOfDay;
        if (dateTimeFieldClockhourOfDay == null) {
            dateTimeFieldClockhourOfDay = super.clockhourOfDay();
        }
        this.iClockhourOfDay = dateTimeFieldClockhourOfDay;
        DateTimeField dateTimeFieldHourOfHalfday = fields.hourOfHalfday;
        if (dateTimeFieldHourOfHalfday == null) {
            dateTimeFieldHourOfHalfday = super.hourOfHalfday();
        }
        this.iHourOfHalfday = dateTimeFieldHourOfHalfday;
        DateTimeField dateTimeFieldClockhourOfHalfday = fields.clockhourOfHalfday;
        if (dateTimeFieldClockhourOfHalfday == null) {
            dateTimeFieldClockhourOfHalfday = super.clockhourOfHalfday();
        }
        this.iClockhourOfHalfday = dateTimeFieldClockhourOfHalfday;
        DateTimeField dateTimeFieldHalfdayOfDay = fields.halfdayOfDay;
        if (dateTimeFieldHalfdayOfDay == null) {
            dateTimeFieldHalfdayOfDay = super.halfdayOfDay();
        }
        this.iHalfdayOfDay = dateTimeFieldHalfdayOfDay;
        DateTimeField dateTimeFieldDayOfWeek = fields.dayOfWeek;
        if (dateTimeFieldDayOfWeek == null) {
            dateTimeFieldDayOfWeek = super.dayOfWeek();
        }
        this.iDayOfWeek = dateTimeFieldDayOfWeek;
        DateTimeField dateTimeFieldDayOfMonth = fields.dayOfMonth;
        if (dateTimeFieldDayOfMonth == null) {
            dateTimeFieldDayOfMonth = super.dayOfMonth();
        }
        this.iDayOfMonth = dateTimeFieldDayOfMonth;
        DateTimeField dateTimeFieldDayOfYear = fields.dayOfYear;
        if (dateTimeFieldDayOfYear == null) {
            dateTimeFieldDayOfYear = super.dayOfYear();
        }
        this.iDayOfYear = dateTimeFieldDayOfYear;
        DateTimeField dateTimeFieldWeekOfWeekyear = fields.weekOfWeekyear;
        if (dateTimeFieldWeekOfWeekyear == null) {
            dateTimeFieldWeekOfWeekyear = super.weekOfWeekyear();
        }
        this.iWeekOfWeekyear = dateTimeFieldWeekOfWeekyear;
        DateTimeField dateTimeFieldWeekyear = fields.weekyear;
        if (dateTimeFieldWeekyear == null) {
            dateTimeFieldWeekyear = super.weekyear();
        }
        this.iWeekyear = dateTimeFieldWeekyear;
        DateTimeField dateTimeFieldWeekyearOfCentury = fields.weekyearOfCentury;
        if (dateTimeFieldWeekyearOfCentury == null) {
            dateTimeFieldWeekyearOfCentury = super.weekyearOfCentury();
        }
        this.iWeekyearOfCentury = dateTimeFieldWeekyearOfCentury;
        DateTimeField dateTimeFieldMonthOfYear = fields.monthOfYear;
        if (dateTimeFieldMonthOfYear == null) {
            dateTimeFieldMonthOfYear = super.monthOfYear();
        }
        this.iMonthOfYear = dateTimeFieldMonthOfYear;
        DateTimeField dateTimeFieldYear = fields.year;
        if (dateTimeFieldYear == null) {
            dateTimeFieldYear = super.year();
        }
        this.iYear = dateTimeFieldYear;
        DateTimeField dateTimeFieldYearOfEra = fields.yearOfEra;
        if (dateTimeFieldYearOfEra == null) {
            dateTimeFieldYearOfEra = super.yearOfEra();
        }
        this.iYearOfEra = dateTimeFieldYearOfEra;
        DateTimeField dateTimeFieldYearOfCentury = fields.yearOfCentury;
        if (dateTimeFieldYearOfCentury == null) {
            dateTimeFieldYearOfCentury = super.yearOfCentury();
        }
        this.iYearOfCentury = dateTimeFieldYearOfCentury;
        DateTimeField dateTimeFieldCenturyOfEra = fields.centuryOfEra;
        if (dateTimeFieldCenturyOfEra == null) {
            dateTimeFieldCenturyOfEra = super.centuryOfEra();
        }
        this.iCenturyOfEra = dateTimeFieldCenturyOfEra;
        DateTimeField dateTimeFieldEra = fields.era;
        if (dateTimeFieldEra == null) {
            dateTimeFieldEra = super.era();
        }
        this.iEra = dateTimeFieldEra;
        Chronology chronology2 = this.iBase;
        int i2 = 0;
        if (chronology2 != null) {
            int i3 = ((this.iHourOfDay == chronology2.hourOfDay() && this.iMinuteOfHour == this.iBase.minuteOfHour() && this.iSecondOfMinute == this.iBase.secondOfMinute() && this.iMillisOfSecond == this.iBase.millisOfSecond()) ? 1 : 0) | (this.iMillisOfDay == this.iBase.millisOfDay() ? 2 : 0);
            if (this.iYear == this.iBase.year() && this.iMonthOfYear == this.iBase.monthOfYear() && this.iDayOfMonth == this.iBase.dayOfMonth()) {
                i2 = 4;
            }
            i2 |= i3;
        }
        this.iBaseFlags = i2;
    }

    protected abstract void assemble(Fields fields);

    @Override // org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public final DurationField centuries() {
        return this.iCenturies;
    }

    @Override // org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public final DateTimeField centuryOfEra() {
        return this.iCenturyOfEra;
    }

    @Override // org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public final DateTimeField clockhourOfDay() {
        return this.iClockhourOfDay;
    }

    @Override // org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public final DateTimeField clockhourOfHalfday() {
        return this.iClockhourOfHalfday;
    }

    @Override // org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public final DateTimeField dayOfMonth() {
        return this.iDayOfMonth;
    }

    @Override // org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public final DateTimeField dayOfWeek() {
        return this.iDayOfWeek;
    }

    @Override // org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public final DateTimeField dayOfYear() {
        return this.iDayOfYear;
    }

    @Override // org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public final DurationField days() {
        return this.iDays;
    }

    @Override // org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public final DateTimeField era() {
        return this.iEra;
    }

    @Override // org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public final DurationField eras() {
        return this.iEras;
    }

    protected final Chronology getBase() {
        return this.iBase;
    }

    @Override // org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public long getDateTimeMillis(int i2, int i3, int i4, int i5) throws IllegalArgumentException {
        Chronology chronology = this.iBase;
        return (chronology == null || (this.iBaseFlags & 6) != 6) ? super.getDateTimeMillis(i2, i3, i4, i5) : chronology.getDateTimeMillis(i2, i3, i4, i5);
    }

    protected final Object getParam() {
        return this.iParam;
    }

    @Override // org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public DateTimeZone getZone() {
        Chronology chronology = this.iBase;
        if (chronology != null) {
            return chronology.getZone();
        }
        return null;
    }

    @Override // org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public final DateTimeField halfdayOfDay() {
        return this.iHalfdayOfDay;
    }

    @Override // org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public final DurationField halfdays() {
        return this.iHalfdays;
    }

    @Override // org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public final DateTimeField hourOfDay() {
        return this.iHourOfDay;
    }

    @Override // org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public final DateTimeField hourOfHalfday() {
        return this.iHourOfHalfday;
    }

    @Override // org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public final DurationField hours() {
        return this.iHours;
    }

    @Override // org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public final DurationField millis() {
        return this.iMillis;
    }

    @Override // org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public final DateTimeField millisOfDay() {
        return this.iMillisOfDay;
    }

    @Override // org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public final DateTimeField millisOfSecond() {
        return this.iMillisOfSecond;
    }

    @Override // org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public final DateTimeField minuteOfDay() {
        return this.iMinuteOfDay;
    }

    @Override // org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public final DateTimeField minuteOfHour() {
        return this.iMinuteOfHour;
    }

    @Override // org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public final DurationField minutes() {
        return this.iMinutes;
    }

    @Override // org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public final DateTimeField monthOfYear() {
        return this.iMonthOfYear;
    }

    @Override // org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public final DurationField months() {
        return this.iMonths;
    }

    @Override // org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public final DateTimeField secondOfDay() {
        return this.iSecondOfDay;
    }

    @Override // org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public final DateTimeField secondOfMinute() {
        return this.iSecondOfMinute;
    }

    @Override // org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public final DurationField seconds() {
        return this.iSeconds;
    }

    @Override // org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public final DateTimeField weekOfWeekyear() {
        return this.iWeekOfWeekyear;
    }

    @Override // org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public final DurationField weeks() {
        return this.iWeeks;
    }

    @Override // org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public final DateTimeField weekyear() {
        return this.iWeekyear;
    }

    @Override // org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public final DateTimeField weekyearOfCentury() {
        return this.iWeekyearOfCentury;
    }

    @Override // org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public final DurationField weekyears() {
        return this.iWeekyears;
    }

    @Override // org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public final DateTimeField year() {
        return this.iYear;
    }

    @Override // org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public final DateTimeField yearOfCentury() {
        return this.iYearOfCentury;
    }

    @Override // org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public final DateTimeField yearOfEra() {
        return this.iYearOfEra;
    }

    @Override // org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public final DurationField years() {
        return this.iYears;
    }

    @Override // org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public long getDateTimeMillis(int i2, int i3, int i4, int i5, int i6, int i7, int i8) throws IllegalArgumentException {
        Chronology chronology = this.iBase;
        if (chronology != null && (this.iBaseFlags & 5) == 5) {
            return chronology.getDateTimeMillis(i2, i3, i4, i5, i6, i7, i8);
        }
        return super.getDateTimeMillis(i2, i3, i4, i5, i6, i7, i8);
    }

    @Override // org.joda.time.chrono.BaseChronology, org.joda.time.Chronology
    public long getDateTimeMillis(long j2, int i2, int i3, int i4, int i5) throws IllegalArgumentException {
        Chronology chronology = this.iBase;
        if (chronology != null && (this.iBaseFlags & 1) == 1) {
            return chronology.getDateTimeMillis(j2, i2, i3, i4, i5);
        }
        return super.getDateTimeMillis(j2, i2, i3, i4, i5);
    }
}
