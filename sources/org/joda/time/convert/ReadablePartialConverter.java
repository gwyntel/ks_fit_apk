package org.joda.time.convert;

import org.joda.time.Chronology;
import org.joda.time.DateTimeUtils;
import org.joda.time.DateTimeZone;
import org.joda.time.ReadablePartial;

/* loaded from: classes5.dex */
class ReadablePartialConverter extends AbstractConverter implements PartialConverter {

    /* renamed from: a, reason: collision with root package name */
    static final ReadablePartialConverter f26712a = new ReadablePartialConverter();

    protected ReadablePartialConverter() {
    }

    @Override // org.joda.time.convert.AbstractConverter, org.joda.time.convert.InstantConverter, org.joda.time.convert.PartialConverter
    public Chronology getChronology(Object obj, DateTimeZone dateTimeZone) {
        return getChronology(obj, (Chronology) null).withZone(dateTimeZone);
    }

    @Override // org.joda.time.convert.AbstractConverter, org.joda.time.convert.PartialConverter
    public int[] getPartialValues(ReadablePartial readablePartial, Object obj, Chronology chronology) {
        ReadablePartial readablePartial2 = (ReadablePartial) obj;
        int size = readablePartial.size();
        int[] iArr = new int[size];
        for (int i2 = 0; i2 < size; i2++) {
            iArr[i2] = readablePartial2.get(readablePartial.getFieldType(i2));
        }
        chronology.validate(readablePartial, iArr);
        return iArr;
    }

    @Override // org.joda.time.convert.Converter
    public Class<?> getSupportedType() {
        return ReadablePartial.class;
    }

    @Override // org.joda.time.convert.AbstractConverter, org.joda.time.convert.InstantConverter, org.joda.time.convert.PartialConverter
    public Chronology getChronology(Object obj, Chronology chronology) {
        return chronology == null ? DateTimeUtils.getChronology(((ReadablePartial) obj).getChronology()) : chronology;
    }
}
