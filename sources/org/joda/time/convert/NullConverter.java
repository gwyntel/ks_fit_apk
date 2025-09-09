package org.joda.time.convert;

import org.joda.time.Chronology;
import org.joda.time.DateTimeUtils;
import org.joda.time.ReadWritableInterval;
import org.joda.time.ReadWritablePeriod;
import org.joda.time.ReadablePeriod;

/* loaded from: classes5.dex */
class NullConverter extends AbstractConverter implements InstantConverter, PartialConverter, DurationConverter, PeriodConverter, IntervalConverter {

    /* renamed from: a, reason: collision with root package name */
    static final NullConverter f26708a = new NullConverter();

    protected NullConverter() {
    }

    @Override // org.joda.time.convert.DurationConverter
    public long getDurationMillis(Object obj) {
        return 0L;
    }

    @Override // org.joda.time.convert.Converter
    public Class<?> getSupportedType() {
        return null;
    }

    @Override // org.joda.time.convert.PeriodConverter
    public void setInto(ReadWritablePeriod readWritablePeriod, Object obj, Chronology chronology) {
        readWritablePeriod.setPeriod((ReadablePeriod) null);
    }

    @Override // org.joda.time.convert.IntervalConverter
    public void setInto(ReadWritableInterval readWritableInterval, Object obj, Chronology chronology) {
        readWritableInterval.setChronology(chronology);
        long jCurrentTimeMillis = DateTimeUtils.currentTimeMillis();
        readWritableInterval.setInterval(jCurrentTimeMillis, jCurrentTimeMillis);
    }
}
