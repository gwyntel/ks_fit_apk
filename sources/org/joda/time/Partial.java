package org.joda.time;

import com.alipay.sdk.m.n.a;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import org.joda.time.base.AbstractPartial;
import org.joda.time.field.AbstractPartialFieldProperty;
import org.joda.time.field.FieldUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

/* loaded from: classes5.dex */
public final class Partial extends AbstractPartial implements ReadablePartial, Serializable {
    private static final long serialVersionUID = 12324121189002L;
    private final Chronology iChronology;
    private transient DateTimeFormatter[] iFormatter;
    private final DateTimeFieldType[] iTypes;
    private final int[] iValues;

    public Partial() {
        this((Chronology) null);
    }

    @Override // org.joda.time.ReadablePartial
    public Chronology getChronology() {
        return this.iChronology;
    }

    @Override // org.joda.time.base.AbstractPartial
    protected DateTimeField getField(int i2, Chronology chronology) {
        return this.iTypes[i2].getField(chronology);
    }

    @Override // org.joda.time.base.AbstractPartial, org.joda.time.ReadablePartial
    public DateTimeFieldType getFieldType(int i2) {
        return this.iTypes[i2];
    }

    @Override // org.joda.time.base.AbstractPartial
    public DateTimeFieldType[] getFieldTypes() {
        return (DateTimeFieldType[]) this.iTypes.clone();
    }

    public DateTimeFormatter getFormatter() {
        DateTimeFormatter[] dateTimeFormatterArr = this.iFormatter;
        if (dateTimeFormatterArr == null) {
            if (size() == 0) {
                return null;
            }
            dateTimeFormatterArr = new DateTimeFormatter[2];
            try {
                ArrayList arrayList = new ArrayList(Arrays.asList(this.iTypes));
                dateTimeFormatterArr[0] = ISODateTimeFormat.forFields(arrayList, true, false);
                if (arrayList.size() == 0) {
                    dateTimeFormatterArr[1] = dateTimeFormatterArr[0];
                }
            } catch (IllegalArgumentException unused) {
            }
            this.iFormatter = dateTimeFormatterArr;
        }
        return dateTimeFormatterArr[0];
    }

    @Override // org.joda.time.ReadablePartial
    public int getValue(int i2) {
        return this.iValues[i2];
    }

    @Override // org.joda.time.base.AbstractPartial
    public int[] getValues() {
        return (int[]) this.iValues.clone();
    }

    public boolean isMatch(ReadableInstant readableInstant) {
        long instantMillis = DateTimeUtils.getInstantMillis(readableInstant);
        Chronology instantChronology = DateTimeUtils.getInstantChronology(readableInstant);
        int i2 = 0;
        while (true) {
            DateTimeFieldType[] dateTimeFieldTypeArr = this.iTypes;
            if (i2 >= dateTimeFieldTypeArr.length) {
                return true;
            }
            if (dateTimeFieldTypeArr[i2].getField(instantChronology).get(instantMillis) != this.iValues[i2]) {
                return false;
            }
            i2++;
        }
    }

    public Partial minus(ReadablePeriod readablePeriod) {
        return withPeriodAdded(readablePeriod, -1);
    }

    public Partial plus(ReadablePeriod readablePeriod) {
        return withPeriodAdded(readablePeriod, 1);
    }

    public Property property(DateTimeFieldType dateTimeFieldType) {
        return new Property(this, indexOfSupported(dateTimeFieldType));
    }

    @Override // org.joda.time.ReadablePartial
    public int size() {
        return this.iTypes.length;
    }

    @Override // org.joda.time.ReadablePartial
    public String toString() {
        DateTimeFormatter[] dateTimeFormatterArr = this.iFormatter;
        if (dateTimeFormatterArr == null) {
            getFormatter();
            dateTimeFormatterArr = this.iFormatter;
            if (dateTimeFormatterArr == null) {
                return toStringList();
            }
        }
        DateTimeFormatter dateTimeFormatter = dateTimeFormatterArr[1];
        return dateTimeFormatter == null ? toStringList() : dateTimeFormatter.print(this);
    }

    public String toStringList() {
        int size = size();
        StringBuilder sb = new StringBuilder(size * 20);
        sb.append('[');
        for (int i2 = 0; i2 < size; i2++) {
            if (i2 > 0) {
                sb.append(',');
                sb.append(' ');
            }
            sb.append(this.iTypes[i2].getName());
            sb.append(a.f9565h);
            sb.append(this.iValues[i2]);
        }
        sb.append(']');
        return sb.toString();
    }

    public Partial with(DateTimeFieldType dateTimeFieldType, int i2) {
        int i3;
        int iCompareTo;
        if (dateTimeFieldType == null) {
            throw new IllegalArgumentException("The field type must not be null");
        }
        int iIndexOf = indexOf(dateTimeFieldType);
        if (iIndexOf != -1) {
            return i2 == getValue(iIndexOf) ? this : new Partial(this, getField(iIndexOf).set(this, iIndexOf, getValues(), i2));
        }
        int length = this.iTypes.length + 1;
        DateTimeFieldType[] dateTimeFieldTypeArr = new DateTimeFieldType[length];
        int[] iArr = new int[length];
        DurationField field = dateTimeFieldType.getDurationType().getField(this.iChronology);
        if (field.isSupported()) {
            i3 = 0;
            while (true) {
                DateTimeFieldType[] dateTimeFieldTypeArr2 = this.iTypes;
                if (i3 >= dateTimeFieldTypeArr2.length) {
                    break;
                }
                DateTimeFieldType dateTimeFieldType2 = dateTimeFieldTypeArr2[i3];
                DurationField field2 = dateTimeFieldType2.getDurationType().getField(this.iChronology);
                if (field2.isSupported() && ((iCompareTo = field.compareTo(field2)) > 0 || (iCompareTo == 0 && dateTimeFieldType.getRangeDurationType().getField(this.iChronology).compareTo(dateTimeFieldType2.getRangeDurationType().getField(this.iChronology)) > 0))) {
                    break;
                }
                i3++;
            }
        } else {
            i3 = 0;
        }
        System.arraycopy(this.iTypes, 0, dateTimeFieldTypeArr, 0, i3);
        System.arraycopy(this.iValues, 0, iArr, 0, i3);
        dateTimeFieldTypeArr[i3] = dateTimeFieldType;
        iArr[i3] = i2;
        int i4 = i3 + 1;
        int i5 = (length - i3) - 1;
        System.arraycopy(this.iTypes, i3, dateTimeFieldTypeArr, i4, i5);
        System.arraycopy(this.iValues, i3, iArr, i4, i5);
        Partial partial = new Partial(this.iChronology, dateTimeFieldTypeArr, iArr);
        this.iChronology.validate(partial, iArr);
        return partial;
    }

    public Partial withChronologyRetainFields(Chronology chronology) {
        Chronology chronologyWithUTC = DateTimeUtils.getChronology(chronology).withUTC();
        if (chronologyWithUTC == getChronology()) {
            return this;
        }
        Partial partial = new Partial(chronologyWithUTC, this.iTypes, this.iValues);
        chronologyWithUTC.validate(partial, this.iValues);
        return partial;
    }

    public Partial withField(DateTimeFieldType dateTimeFieldType, int i2) {
        int iIndexOfSupported = indexOfSupported(dateTimeFieldType);
        if (i2 == getValue(iIndexOfSupported)) {
            return this;
        }
        return new Partial(this, getField(iIndexOfSupported).set(this, iIndexOfSupported, getValues(), i2));
    }

    public Partial withFieldAddWrapped(DurationFieldType durationFieldType, int i2) {
        int iIndexOfSupported = indexOfSupported(durationFieldType);
        if (i2 == 0) {
            return this;
        }
        return new Partial(this, getField(iIndexOfSupported).addWrapPartial(this, iIndexOfSupported, getValues(), i2));
    }

    public Partial withFieldAdded(DurationFieldType durationFieldType, int i2) {
        int iIndexOfSupported = indexOfSupported(durationFieldType);
        if (i2 == 0) {
            return this;
        }
        return new Partial(this, getField(iIndexOfSupported).add(this, iIndexOfSupported, getValues(), i2));
    }

    public Partial withPeriodAdded(ReadablePeriod readablePeriod, int i2) {
        if (readablePeriod == null || i2 == 0) {
            return this;
        }
        int[] values = getValues();
        for (int i3 = 0; i3 < readablePeriod.size(); i3++) {
            int iIndexOf = indexOf(readablePeriod.getFieldType(i3));
            if (iIndexOf >= 0) {
                values = getField(iIndexOf).add(this, iIndexOf, values, FieldUtils.safeMultiply(readablePeriod.getValue(i3), i2));
            }
        }
        return new Partial(this, values);
    }

    public Partial without(DateTimeFieldType dateTimeFieldType) {
        int iIndexOf = indexOf(dateTimeFieldType);
        if (iIndexOf == -1) {
            return this;
        }
        int size = size() - 1;
        DateTimeFieldType[] dateTimeFieldTypeArr = new DateTimeFieldType[size];
        int size2 = size() - 1;
        int[] iArr = new int[size2];
        System.arraycopy(this.iTypes, 0, dateTimeFieldTypeArr, 0, iIndexOf);
        int i2 = iIndexOf + 1;
        System.arraycopy(this.iTypes, i2, dateTimeFieldTypeArr, iIndexOf, size - iIndexOf);
        System.arraycopy(this.iValues, 0, iArr, 0, iIndexOf);
        System.arraycopy(this.iValues, i2, iArr, iIndexOf, size2 - iIndexOf);
        Partial partial = new Partial(this.iChronology, dateTimeFieldTypeArr, iArr);
        this.iChronology.validate(partial, iArr);
        return partial;
    }

    public Partial(Chronology chronology) {
        this.iChronology = DateTimeUtils.getChronology(chronology).withUTC();
        this.iTypes = new DateTimeFieldType[0];
        this.iValues = new int[0];
    }

    public static class Property extends AbstractPartialFieldProperty implements Serializable {
        private static final long serialVersionUID = 53278362873888L;
        private final int iFieldIndex;
        private final Partial iPartial;

        Property(Partial partial, int i2) {
            this.iPartial = partial;
            this.iFieldIndex = i2;
        }

        public Partial addToCopy(int i2) {
            return new Partial(this.iPartial, getField().add(this.iPartial, this.iFieldIndex, this.iPartial.getValues(), i2));
        }

        public Partial addWrapFieldToCopy(int i2) {
            return new Partial(this.iPartial, getField().addWrapField(this.iPartial, this.iFieldIndex, this.iPartial.getValues(), i2));
        }

        @Override // org.joda.time.field.AbstractPartialFieldProperty
        public int get() {
            return this.iPartial.getValue(this.iFieldIndex);
        }

        @Override // org.joda.time.field.AbstractPartialFieldProperty
        public DateTimeField getField() {
            return this.iPartial.getField(this.iFieldIndex);
        }

        public Partial getPartial() {
            return this.iPartial;
        }

        @Override // org.joda.time.field.AbstractPartialFieldProperty
        protected ReadablePartial getReadablePartial() {
            return this.iPartial;
        }

        public Partial setCopy(int i2) {
            return new Partial(this.iPartial, getField().set(this.iPartial, this.iFieldIndex, this.iPartial.getValues(), i2));
        }

        public Partial withMaximumValue() {
            return setCopy(getMaximumValue());
        }

        public Partial withMinimumValue() {
            return setCopy(getMinimumValue());
        }

        public Partial setCopy(String str, Locale locale) {
            return new Partial(this.iPartial, getField().set(this.iPartial, this.iFieldIndex, this.iPartial.getValues(), str, locale));
        }

        public Partial setCopy(String str) {
            return setCopy(str, null);
        }
    }

    public Partial(DateTimeFieldType dateTimeFieldType, int i2) {
        this(dateTimeFieldType, i2, (Chronology) null);
    }

    public boolean isMatch(ReadablePartial readablePartial) {
        if (readablePartial == null) {
            throw new IllegalArgumentException("The partial must not be null");
        }
        int i2 = 0;
        while (true) {
            DateTimeFieldType[] dateTimeFieldTypeArr = this.iTypes;
            if (i2 >= dateTimeFieldTypeArr.length) {
                return true;
            }
            if (readablePartial.get(dateTimeFieldTypeArr[i2]) != this.iValues[i2]) {
                return false;
            }
            i2++;
        }
    }

    public Partial(DateTimeFieldType dateTimeFieldType, int i2, Chronology chronology) {
        Chronology chronologyWithUTC = DateTimeUtils.getChronology(chronology).withUTC();
        this.iChronology = chronologyWithUTC;
        if (dateTimeFieldType != null) {
            this.iTypes = new DateTimeFieldType[]{dateTimeFieldType};
            int[] iArr = {i2};
            this.iValues = iArr;
            chronologyWithUTC.validate(this, iArr);
            return;
        }
        throw new IllegalArgumentException("The field type must not be null");
    }

    public String toString(String str) {
        if (str == null) {
            return toString();
        }
        return DateTimeFormat.forPattern(str).print(this);
    }

    public String toString(String str, Locale locale) {
        if (str == null) {
            return toString();
        }
        return DateTimeFormat.forPattern(str).withLocale(locale).print(this);
    }

    public Partial(DateTimeFieldType[] dateTimeFieldTypeArr, int[] iArr) {
        this(dateTimeFieldTypeArr, iArr, (Chronology) null);
    }

    public Partial(DateTimeFieldType[] dateTimeFieldTypeArr, int[] iArr, Chronology chronology) {
        Chronology chronologyWithUTC = DateTimeUtils.getChronology(chronology).withUTC();
        this.iChronology = chronologyWithUTC;
        if (dateTimeFieldTypeArr == null) {
            throw new IllegalArgumentException("Types array must not be null");
        }
        if (iArr != null) {
            if (iArr.length == dateTimeFieldTypeArr.length) {
                if (dateTimeFieldTypeArr.length == 0) {
                    this.iTypes = dateTimeFieldTypeArr;
                    this.iValues = iArr;
                    return;
                }
                int i2 = 0;
                for (int i3 = 0; i3 < dateTimeFieldTypeArr.length; i3++) {
                    if (dateTimeFieldTypeArr[i3] == null) {
                        throw new IllegalArgumentException("Types array must not contain null: index " + i3);
                    }
                }
                DurationField durationField = null;
                while (i2 < dateTimeFieldTypeArr.length) {
                    DateTimeFieldType dateTimeFieldType = dateTimeFieldTypeArr[i2];
                    DurationField field = dateTimeFieldType.getDurationType().getField(this.iChronology);
                    if (i2 > 0) {
                        int iCompareTo = durationField.compareTo(field);
                        if (iCompareTo < 0 || !(iCompareTo == 0 || field.isSupported())) {
                            throw new IllegalArgumentException("Types array must be in order largest-smallest: " + dateTimeFieldTypeArr[i2 - 1].getName() + " < " + dateTimeFieldType.getName());
                        }
                        if (iCompareTo == 0) {
                            int i4 = i2 - 1;
                            if (dateTimeFieldTypeArr[i4].getRangeDurationType() == null) {
                                if (dateTimeFieldType.getRangeDurationType() == null) {
                                    throw new IllegalArgumentException("Types array must not contain duplicate: " + dateTimeFieldType.getName());
                                }
                            } else {
                                if (dateTimeFieldType.getRangeDurationType() == null) {
                                    throw new IllegalArgumentException("Types array must be in order largest-smallest: " + dateTimeFieldTypeArr[i4].getName() + " < " + dateTimeFieldType.getName());
                                }
                                DurationField field2 = dateTimeFieldTypeArr[i4].getRangeDurationType().getField(this.iChronology);
                                DurationField field3 = dateTimeFieldType.getRangeDurationType().getField(this.iChronology);
                                if (field2.compareTo(field3) < 0) {
                                    throw new IllegalArgumentException("Types array must be in order largest-smallest: " + dateTimeFieldTypeArr[i4].getName() + " < " + dateTimeFieldType.getName());
                                }
                                if (field2.compareTo(field3) == 0) {
                                    throw new IllegalArgumentException("Types array must not contain duplicate: " + dateTimeFieldType.getName());
                                }
                            }
                        } else {
                            continue;
                        }
                    }
                    i2++;
                    durationField = field;
                }
                this.iTypes = (DateTimeFieldType[]) dateTimeFieldTypeArr.clone();
                chronologyWithUTC.validate(this, iArr);
                this.iValues = (int[]) iArr.clone();
                return;
            }
            throw new IllegalArgumentException("Values array must be the same length as the types array");
        }
        throw new IllegalArgumentException("Values array must not be null");
    }

    public Partial(ReadablePartial readablePartial) {
        if (readablePartial != null) {
            this.iChronology = DateTimeUtils.getChronology(readablePartial.getChronology()).withUTC();
            this.iTypes = new DateTimeFieldType[readablePartial.size()];
            this.iValues = new int[readablePartial.size()];
            for (int i2 = 0; i2 < readablePartial.size(); i2++) {
                this.iTypes[i2] = readablePartial.getFieldType(i2);
                this.iValues[i2] = readablePartial.getValue(i2);
            }
            return;
        }
        throw new IllegalArgumentException("The partial must not be null");
    }

    Partial(Partial partial, int[] iArr) {
        this.iChronology = partial.iChronology;
        this.iTypes = partial.iTypes;
        this.iValues = iArr;
    }

    Partial(Chronology chronology, DateTimeFieldType[] dateTimeFieldTypeArr, int[] iArr) {
        this.iChronology = chronology;
        this.iTypes = dateTimeFieldTypeArr;
        this.iValues = iArr;
    }
}
