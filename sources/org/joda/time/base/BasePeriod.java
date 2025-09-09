package org.joda.time.base;

import java.io.Serializable;
import org.joda.time.Chronology;
import org.joda.time.DateTimeUtils;
import org.joda.time.Duration;
import org.joda.time.DurationFieldType;
import org.joda.time.MutablePeriod;
import org.joda.time.PeriodType;
import org.joda.time.ReadWritablePeriod;
import org.joda.time.ReadableDuration;
import org.joda.time.ReadableInstant;
import org.joda.time.ReadablePartial;
import org.joda.time.ReadablePeriod;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.convert.ConverterManager;
import org.joda.time.convert.PeriodConverter;
import org.joda.time.field.FieldUtils;

/* loaded from: classes5.dex */
public abstract class BasePeriod extends AbstractPeriod implements ReadablePeriod, Serializable {
    private static final ReadablePeriod DUMMY_PERIOD = new AbstractPeriod() { // from class: org.joda.time.base.BasePeriod.1
        @Override // org.joda.time.ReadablePeriod
        public PeriodType getPeriodType() {
            return PeriodType.time();
        }

        @Override // org.joda.time.ReadablePeriod
        public int getValue(int i2) {
            return 0;
        }
    };
    private static final long serialVersionUID = -2110953284060001145L;
    private final PeriodType iType;
    private final int[] iValues;

    protected BasePeriod(int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, PeriodType periodType) {
        this.iType = checkPeriodType(periodType);
        this.iValues = setPeriodInternal(i2, i3, i4, i5, i6, i7, i8, i9);
    }

    private void checkAndUpdate(DurationFieldType durationFieldType, int[] iArr, int i2) {
        int iIndexOf = indexOf(durationFieldType);
        if (iIndexOf != -1) {
            iArr[iIndexOf] = i2;
        } else {
            if (i2 == 0) {
                return;
            }
            throw new IllegalArgumentException("Period does not support field '" + durationFieldType.getName() + "'");
        }
    }

    private void setPeriodInternal(ReadablePeriod readablePeriod) {
        int[] iArr = new int[size()];
        int size = readablePeriod.size();
        for (int i2 = 0; i2 < size; i2++) {
            checkAndUpdate(readablePeriod.getFieldType(i2), iArr, readablePeriod.getValue(i2));
        }
        setValues(iArr);
    }

    protected void addField(DurationFieldType durationFieldType, int i2) {
        addFieldInto(this.iValues, durationFieldType, i2);
    }

    protected void addFieldInto(int[] iArr, DurationFieldType durationFieldType, int i2) {
        int iIndexOf = indexOf(durationFieldType);
        if (iIndexOf != -1) {
            iArr[iIndexOf] = FieldUtils.safeAdd(iArr[iIndexOf], i2);
            return;
        }
        if (i2 != 0 || durationFieldType == null) {
            throw new IllegalArgumentException("Period does not support field '" + durationFieldType + "'");
        }
    }

    protected void addPeriod(ReadablePeriod readablePeriod) {
        if (readablePeriod != null) {
            setValues(addPeriodInto(getValues(), readablePeriod));
        }
    }

    protected int[] addPeriodInto(int[] iArr, ReadablePeriod readablePeriod) {
        int size = readablePeriod.size();
        for (int i2 = 0; i2 < size; i2++) {
            DurationFieldType fieldType = readablePeriod.getFieldType(i2);
            int value = readablePeriod.getValue(i2);
            if (value != 0) {
                int iIndexOf = indexOf(fieldType);
                if (iIndexOf == -1) {
                    throw new IllegalArgumentException("Period does not support field '" + fieldType.getName() + "'");
                }
                iArr[iIndexOf] = FieldUtils.safeAdd(getValue(iIndexOf), value);
            }
        }
        return iArr;
    }

    protected PeriodType checkPeriodType(PeriodType periodType) {
        return DateTimeUtils.getPeriodType(periodType);
    }

    @Override // org.joda.time.ReadablePeriod
    public PeriodType getPeriodType() {
        return this.iType;
    }

    @Override // org.joda.time.ReadablePeriod
    public int getValue(int i2) {
        return this.iValues[i2];
    }

    protected void mergePeriod(ReadablePeriod readablePeriod) {
        if (readablePeriod != null) {
            setValues(mergePeriodInto(getValues(), readablePeriod));
        }
    }

    protected int[] mergePeriodInto(int[] iArr, ReadablePeriod readablePeriod) {
        int size = readablePeriod.size();
        for (int i2 = 0; i2 < size; i2++) {
            checkAndUpdate(readablePeriod.getFieldType(i2), iArr, readablePeriod.getValue(i2));
        }
        return iArr;
    }

    protected void setField(DurationFieldType durationFieldType, int i2) {
        setFieldInto(this.iValues, durationFieldType, i2);
    }

    protected void setFieldInto(int[] iArr, DurationFieldType durationFieldType, int i2) {
        int iIndexOf = indexOf(durationFieldType);
        if (iIndexOf != -1) {
            iArr[iIndexOf] = i2;
            return;
        }
        if (i2 != 0 || durationFieldType == null) {
            throw new IllegalArgumentException("Period does not support field '" + durationFieldType + "'");
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setPeriod(ReadablePeriod readablePeriod) {
        if (readablePeriod == null) {
            setValues(new int[size()]);
        } else {
            setPeriodInternal(readablePeriod);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setValue(int i2, int i3) {
        this.iValues[i2] = i3;
    }

    protected void setValues(int[] iArr) {
        int[] iArr2 = this.iValues;
        System.arraycopy(iArr, 0, iArr2, 0, iArr2.length);
    }

    public Duration toDurationFrom(ReadableInstant readableInstant) {
        long instantMillis = DateTimeUtils.getInstantMillis(readableInstant);
        return new Duration(instantMillis, DateTimeUtils.getInstantChronology(readableInstant).add(this, instantMillis, 1));
    }

    public Duration toDurationTo(ReadableInstant readableInstant) {
        long instantMillis = DateTimeUtils.getInstantMillis(readableInstant);
        return new Duration(DateTimeUtils.getInstantChronology(readableInstant).add(this, instantMillis, -1), instantMillis);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setPeriod(int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
        setValues(setPeriodInternal(i2, i3, i4, i5, i6, i7, i8, i9));
    }

    protected BasePeriod(long j2, long j3, PeriodType periodType, Chronology chronology) {
        PeriodType periodTypeCheckPeriodType = checkPeriodType(periodType);
        Chronology chronology2 = DateTimeUtils.getChronology(chronology);
        this.iType = periodTypeCheckPeriodType;
        this.iValues = chronology2.get(this, j2, j3);
    }

    private int[] setPeriodInternal(int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
        int[] iArr = new int[size()];
        checkAndUpdate(DurationFieldType.years(), iArr, i2);
        checkAndUpdate(DurationFieldType.months(), iArr, i3);
        checkAndUpdate(DurationFieldType.weeks(), iArr, i4);
        checkAndUpdate(DurationFieldType.days(), iArr, i5);
        checkAndUpdate(DurationFieldType.hours(), iArr, i6);
        checkAndUpdate(DurationFieldType.minutes(), iArr, i7);
        checkAndUpdate(DurationFieldType.seconds(), iArr, i8);
        checkAndUpdate(DurationFieldType.millis(), iArr, i9);
        return iArr;
    }

    protected BasePeriod(ReadableInstant readableInstant, ReadableInstant readableInstant2, PeriodType periodType) {
        PeriodType periodTypeCheckPeriodType = checkPeriodType(periodType);
        if (readableInstant == null && readableInstant2 == null) {
            this.iType = periodTypeCheckPeriodType;
            this.iValues = new int[size()];
            return;
        }
        long instantMillis = DateTimeUtils.getInstantMillis(readableInstant);
        long instantMillis2 = DateTimeUtils.getInstantMillis(readableInstant2);
        Chronology intervalChronology = DateTimeUtils.getIntervalChronology(readableInstant, readableInstant2);
        this.iType = periodTypeCheckPeriodType;
        this.iValues = intervalChronology.get(this, instantMillis, instantMillis2);
    }

    protected BasePeriod(ReadablePartial readablePartial, ReadablePartial readablePartial2, PeriodType periodType) {
        if (readablePartial != null && readablePartial2 != null) {
            if ((readablePartial instanceof BaseLocal) && (readablePartial2 instanceof BaseLocal) && readablePartial.getClass() == readablePartial2.getClass()) {
                PeriodType periodTypeCheckPeriodType = checkPeriodType(periodType);
                long localMillis = ((BaseLocal) readablePartial).getLocalMillis();
                long localMillis2 = ((BaseLocal) readablePartial2).getLocalMillis();
                Chronology chronology = DateTimeUtils.getChronology(readablePartial.getChronology());
                this.iType = periodTypeCheckPeriodType;
                this.iValues = chronology.get(this, localMillis, localMillis2);
                return;
            }
            if (readablePartial.size() == readablePartial2.size()) {
                int size = readablePartial.size();
                for (int i2 = 0; i2 < size; i2++) {
                    if (readablePartial.getFieldType(i2) != readablePartial2.getFieldType(i2)) {
                        throw new IllegalArgumentException("ReadablePartial objects must have the same set of fields");
                    }
                }
                if (DateTimeUtils.isContiguous(readablePartial)) {
                    this.iType = checkPeriodType(periodType);
                    Chronology chronologyWithUTC = DateTimeUtils.getChronology(readablePartial.getChronology()).withUTC();
                    this.iValues = chronologyWithUTC.get(this, chronologyWithUTC.set(readablePartial, 0L), chronologyWithUTC.set(readablePartial2, 0L));
                    return;
                }
                throw new IllegalArgumentException("ReadablePartial objects must be contiguous");
            }
            throw new IllegalArgumentException("ReadablePartial objects must have the same set of fields");
        }
        throw new IllegalArgumentException("ReadablePartial objects must not be null");
    }

    protected BasePeriod(ReadableInstant readableInstant, ReadableDuration readableDuration, PeriodType periodType) {
        PeriodType periodTypeCheckPeriodType = checkPeriodType(periodType);
        long instantMillis = DateTimeUtils.getInstantMillis(readableInstant);
        long jSafeAdd = FieldUtils.safeAdd(instantMillis, DateTimeUtils.getDurationMillis(readableDuration));
        Chronology instantChronology = DateTimeUtils.getInstantChronology(readableInstant);
        this.iType = periodTypeCheckPeriodType;
        this.iValues = instantChronology.get(this, instantMillis, jSafeAdd);
    }

    protected BasePeriod(ReadableDuration readableDuration, ReadableInstant readableInstant, PeriodType periodType) {
        PeriodType periodTypeCheckPeriodType = checkPeriodType(periodType);
        long durationMillis = DateTimeUtils.getDurationMillis(readableDuration);
        long instantMillis = DateTimeUtils.getInstantMillis(readableInstant);
        long jSafeSubtract = FieldUtils.safeSubtract(instantMillis, durationMillis);
        Chronology instantChronology = DateTimeUtils.getInstantChronology(readableInstant);
        this.iType = periodTypeCheckPeriodType;
        this.iValues = instantChronology.get(this, jSafeSubtract, instantMillis);
    }

    protected BasePeriod(long j2) {
        this.iType = PeriodType.standard();
        int[] iArr = ISOChronology.getInstanceUTC().get(DUMMY_PERIOD, j2);
        int[] iArr2 = new int[8];
        this.iValues = iArr2;
        System.arraycopy(iArr, 0, iArr2, 4, 4);
    }

    protected BasePeriod(long j2, PeriodType periodType, Chronology chronology) {
        PeriodType periodTypeCheckPeriodType = checkPeriodType(periodType);
        Chronology chronology2 = DateTimeUtils.getChronology(chronology);
        this.iType = periodTypeCheckPeriodType;
        this.iValues = chronology2.get(this, j2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected BasePeriod(Object obj, PeriodType periodType, Chronology chronology) {
        PeriodConverter periodConverter = ConverterManager.getInstance().getPeriodConverter(obj);
        PeriodType periodTypeCheckPeriodType = checkPeriodType(periodType == null ? periodConverter.getPeriodType(obj) : periodType);
        this.iType = periodTypeCheckPeriodType;
        if (this instanceof ReadWritablePeriod) {
            this.iValues = new int[size()];
            periodConverter.setInto((ReadWritablePeriod) this, obj, DateTimeUtils.getChronology(chronology));
        } else {
            this.iValues = new MutablePeriod(obj, periodTypeCheckPeriodType, chronology).getValues();
        }
    }

    protected BasePeriod(int[] iArr, PeriodType periodType) {
        this.iType = periodType;
        this.iValues = iArr;
    }
}
