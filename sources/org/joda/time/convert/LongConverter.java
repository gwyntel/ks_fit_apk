package org.joda.time.convert;

import org.joda.time.Chronology;

/* loaded from: classes5.dex */
class LongConverter extends AbstractConverter implements InstantConverter, PartialConverter, DurationConverter {

    /* renamed from: a, reason: collision with root package name */
    static final LongConverter f26707a = new LongConverter();

    protected LongConverter() {
    }

    @Override // org.joda.time.convert.DurationConverter
    public long getDurationMillis(Object obj) {
        return ((Long) obj).longValue();
    }

    @Override // org.joda.time.convert.AbstractConverter, org.joda.time.convert.InstantConverter
    public long getInstantMillis(Object obj, Chronology chronology) {
        return ((Long) obj).longValue();
    }

    @Override // org.joda.time.convert.Converter
    public Class<?> getSupportedType() {
        return Long.class;
    }
}
