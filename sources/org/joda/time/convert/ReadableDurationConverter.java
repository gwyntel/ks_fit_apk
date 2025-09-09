package org.joda.time.convert;

import org.joda.time.Chronology;
import org.joda.time.DateTimeUtils;
import org.joda.time.ReadWritablePeriod;
import org.joda.time.ReadableDuration;

/* loaded from: classes5.dex */
class ReadableDurationConverter extends AbstractConverter implements DurationConverter, PeriodConverter {

    /* renamed from: a, reason: collision with root package name */
    static final ReadableDurationConverter f26709a = new ReadableDurationConverter();

    protected ReadableDurationConverter() {
    }

    @Override // org.joda.time.convert.DurationConverter
    public long getDurationMillis(Object obj) {
        return ((ReadableDuration) obj).getMillis();
    }

    @Override // org.joda.time.convert.Converter
    public Class<?> getSupportedType() {
        return ReadableDuration.class;
    }

    @Override // org.joda.time.convert.PeriodConverter
    public void setInto(ReadWritablePeriod readWritablePeriod, Object obj, Chronology chronology) {
        int[] iArr = DateTimeUtils.getChronology(chronology).get(readWritablePeriod, ((ReadableDuration) obj).getMillis());
        for (int i2 = 0; i2 < iArr.length; i2++) {
            readWritablePeriod.setValue(i2, iArr[i2]);
        }
    }
}
