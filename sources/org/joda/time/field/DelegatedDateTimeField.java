package org.joda.time.field;

import java.io.Serializable;
import java.util.Locale;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DurationField;
import org.joda.time.ReadablePartial;

/* loaded from: classes5.dex */
public class DelegatedDateTimeField extends DateTimeField implements Serializable {
    private static final long serialVersionUID = -4730164440214502503L;
    private final DateTimeField iField;
    private final DateTimeFieldType iType;

    public DelegatedDateTimeField(DateTimeField dateTimeField) {
        this(dateTimeField, null);
    }

    @Override // org.joda.time.DateTimeField
    public long add(long j2, int i2) {
        return this.iField.add(j2, i2);
    }

    @Override // org.joda.time.DateTimeField
    public long addWrapField(long j2, int i2) {
        return this.iField.addWrapField(j2, i2);
    }

    @Override // org.joda.time.DateTimeField
    public int[] addWrapPartial(ReadablePartial readablePartial, int i2, int[] iArr, int i3) {
        return this.iField.addWrapPartial(readablePartial, i2, iArr, i3);
    }

    @Override // org.joda.time.DateTimeField
    public int get(long j2) {
        return this.iField.get(j2);
    }

    @Override // org.joda.time.DateTimeField
    public String getAsShortText(long j2, Locale locale) {
        return this.iField.getAsShortText(j2, locale);
    }

    @Override // org.joda.time.DateTimeField
    public String getAsText(long j2, Locale locale) {
        return this.iField.getAsText(j2, locale);
    }

    @Override // org.joda.time.DateTimeField
    public int getDifference(long j2, long j3) {
        return this.iField.getDifference(j2, j3);
    }

    @Override // org.joda.time.DateTimeField
    public long getDifferenceAsLong(long j2, long j3) {
        return this.iField.getDifferenceAsLong(j2, j3);
    }

    @Override // org.joda.time.DateTimeField
    public DurationField getDurationField() {
        return this.iField.getDurationField();
    }

    @Override // org.joda.time.DateTimeField
    public int getLeapAmount(long j2) {
        return this.iField.getLeapAmount(j2);
    }

    @Override // org.joda.time.DateTimeField
    public DurationField getLeapDurationField() {
        return this.iField.getLeapDurationField();
    }

    @Override // org.joda.time.DateTimeField
    public int getMaximumShortTextLength(Locale locale) {
        return this.iField.getMaximumShortTextLength(locale);
    }

    @Override // org.joda.time.DateTimeField
    public int getMaximumTextLength(Locale locale) {
        return this.iField.getMaximumTextLength(locale);
    }

    @Override // org.joda.time.DateTimeField
    public int getMaximumValue() {
        return this.iField.getMaximumValue();
    }

    @Override // org.joda.time.DateTimeField
    public int getMinimumValue() {
        return this.iField.getMinimumValue();
    }

    @Override // org.joda.time.DateTimeField
    public String getName() {
        return this.iType.getName();
    }

    @Override // org.joda.time.DateTimeField
    public DurationField getRangeDurationField() {
        return this.iField.getRangeDurationField();
    }

    @Override // org.joda.time.DateTimeField
    public DateTimeFieldType getType() {
        return this.iType;
    }

    public final DateTimeField getWrappedField() {
        return this.iField;
    }

    @Override // org.joda.time.DateTimeField
    public boolean isLeap(long j2) {
        return this.iField.isLeap(j2);
    }

    @Override // org.joda.time.DateTimeField
    public boolean isLenient() {
        return this.iField.isLenient();
    }

    @Override // org.joda.time.DateTimeField
    public boolean isSupported() {
        return this.iField.isSupported();
    }

    @Override // org.joda.time.DateTimeField
    public long remainder(long j2) {
        return this.iField.remainder(j2);
    }

    @Override // org.joda.time.DateTimeField
    public long roundCeiling(long j2) {
        return this.iField.roundCeiling(j2);
    }

    @Override // org.joda.time.DateTimeField
    public long roundFloor(long j2) {
        return this.iField.roundFloor(j2);
    }

    @Override // org.joda.time.DateTimeField
    public long roundHalfCeiling(long j2) {
        return this.iField.roundHalfCeiling(j2);
    }

    @Override // org.joda.time.DateTimeField
    public long roundHalfEven(long j2) {
        return this.iField.roundHalfEven(j2);
    }

    @Override // org.joda.time.DateTimeField
    public long roundHalfFloor(long j2) {
        return this.iField.roundHalfFloor(j2);
    }

    @Override // org.joda.time.DateTimeField
    public long set(long j2, int i2) {
        return this.iField.set(j2, i2);
    }

    @Override // org.joda.time.DateTimeField
    public String toString() {
        return "DateTimeField[" + getName() + ']';
    }

    public DelegatedDateTimeField(DateTimeField dateTimeField, DateTimeFieldType dateTimeFieldType) {
        if (dateTimeField == null) {
            throw new IllegalArgumentException("The field must not be null");
        }
        this.iField = dateTimeField;
        this.iType = dateTimeFieldType == null ? dateTimeField.getType() : dateTimeFieldType;
    }

    @Override // org.joda.time.DateTimeField
    public long add(long j2, long j3) {
        return this.iField.add(j2, j3);
    }

    @Override // org.joda.time.DateTimeField
    public int[] addWrapField(ReadablePartial readablePartial, int i2, int[] iArr, int i3) {
        return this.iField.addWrapField(readablePartial, i2, iArr, i3);
    }

    @Override // org.joda.time.DateTimeField
    public String getAsShortText(long j2) {
        return this.iField.getAsShortText(j2);
    }

    @Override // org.joda.time.DateTimeField
    public String getAsText(long j2) {
        return this.iField.getAsText(j2);
    }

    @Override // org.joda.time.DateTimeField
    public int getMaximumValue(long j2) {
        return this.iField.getMaximumValue(j2);
    }

    @Override // org.joda.time.DateTimeField
    public int getMinimumValue(long j2) {
        return this.iField.getMinimumValue(j2);
    }

    @Override // org.joda.time.DateTimeField
    public long set(long j2, String str, Locale locale) {
        return this.iField.set(j2, str, locale);
    }

    @Override // org.joda.time.DateTimeField
    public int[] add(ReadablePartial readablePartial, int i2, int[] iArr, int i3) {
        return this.iField.add(readablePartial, i2, iArr, i3);
    }

    @Override // org.joda.time.DateTimeField
    public String getAsShortText(ReadablePartial readablePartial, int i2, Locale locale) {
        return this.iField.getAsShortText(readablePartial, i2, locale);
    }

    @Override // org.joda.time.DateTimeField
    public String getAsText(ReadablePartial readablePartial, int i2, Locale locale) {
        return this.iField.getAsText(readablePartial, i2, locale);
    }

    @Override // org.joda.time.DateTimeField
    public int getMaximumValue(ReadablePartial readablePartial) {
        return this.iField.getMaximumValue(readablePartial);
    }

    @Override // org.joda.time.DateTimeField
    public int getMinimumValue(ReadablePartial readablePartial) {
        return this.iField.getMinimumValue(readablePartial);
    }

    @Override // org.joda.time.DateTimeField
    public long set(long j2, String str) {
        return this.iField.set(j2, str);
    }

    @Override // org.joda.time.DateTimeField
    public String getAsShortText(ReadablePartial readablePartial, Locale locale) {
        return this.iField.getAsShortText(readablePartial, locale);
    }

    @Override // org.joda.time.DateTimeField
    public String getAsText(ReadablePartial readablePartial, Locale locale) {
        return this.iField.getAsText(readablePartial, locale);
    }

    @Override // org.joda.time.DateTimeField
    public int getMaximumValue(ReadablePartial readablePartial, int[] iArr) {
        return this.iField.getMaximumValue(readablePartial, iArr);
    }

    @Override // org.joda.time.DateTimeField
    public int getMinimumValue(ReadablePartial readablePartial, int[] iArr) {
        return this.iField.getMinimumValue(readablePartial, iArr);
    }

    @Override // org.joda.time.DateTimeField
    public int[] set(ReadablePartial readablePartial, int i2, int[] iArr, int i3) {
        return this.iField.set(readablePartial, i2, iArr, i3);
    }

    @Override // org.joda.time.DateTimeField
    public String getAsShortText(int i2, Locale locale) {
        return this.iField.getAsShortText(i2, locale);
    }

    @Override // org.joda.time.DateTimeField
    public String getAsText(int i2, Locale locale) {
        return this.iField.getAsText(i2, locale);
    }

    @Override // org.joda.time.DateTimeField
    public int[] set(ReadablePartial readablePartial, int i2, int[] iArr, String str, Locale locale) {
        return this.iField.set(readablePartial, i2, iArr, str, locale);
    }
}
