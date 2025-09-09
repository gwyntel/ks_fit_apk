package org.joda.time.convert;

import org.joda.time.Chronology;
import org.joda.time.DateTimeUtils;
import org.joda.time.ReadWritableInterval;
import org.joda.time.ReadWritablePeriod;
import org.joda.time.ReadableInterval;

/* loaded from: classes5.dex */
class ReadableIntervalConverter extends AbstractConverter implements IntervalConverter, DurationConverter, PeriodConverter {

    /* renamed from: a, reason: collision with root package name */
    static final ReadableIntervalConverter f26711a = new ReadableIntervalConverter();

    protected ReadableIntervalConverter() {
    }

    @Override // org.joda.time.convert.DurationConverter
    public long getDurationMillis(Object obj) {
        return ((ReadableInterval) obj).toDurationMillis();
    }

    @Override // org.joda.time.convert.Converter
    public Class<?> getSupportedType() {
        return ReadableInterval.class;
    }

    @Override // org.joda.time.convert.AbstractConverter, org.joda.time.convert.IntervalConverter
    public boolean isReadableInterval(Object obj, Chronology chronology) {
        return true;
    }

    @Override // org.joda.time.convert.PeriodConverter
    public void setInto(ReadWritablePeriod readWritablePeriod, Object obj, Chronology chronology) {
        ReadableInterval readableInterval = (ReadableInterval) obj;
        if (chronology == null) {
            chronology = DateTimeUtils.getIntervalChronology(readableInterval);
        }
        int[] iArr = chronology.get(readWritablePeriod, readableInterval.getStartMillis(), readableInterval.getEndMillis());
        for (int i2 = 0; i2 < iArr.length; i2++) {
            readWritablePeriod.setValue(i2, iArr[i2]);
        }
    }

    @Override // org.joda.time.convert.IntervalConverter
    public void setInto(ReadWritableInterval readWritableInterval, Object obj, Chronology chronology) {
        ReadableInterval readableInterval = (ReadableInterval) obj;
        readWritableInterval.setInterval(readableInterval);
        if (chronology != null) {
            readWritableInterval.setChronology(chronology);
        } else {
            readWritableInterval.setChronology(readableInterval.getChronology());
        }
    }
}
