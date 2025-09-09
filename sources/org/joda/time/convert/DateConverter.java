package org.joda.time.convert;

import java.util.Date;
import org.joda.time.Chronology;

/* loaded from: classes5.dex */
final class DateConverter extends AbstractConverter implements InstantConverter, PartialConverter {

    /* renamed from: a, reason: collision with root package name */
    static final DateConverter f26706a = new DateConverter();

    protected DateConverter() {
    }

    @Override // org.joda.time.convert.AbstractConverter, org.joda.time.convert.InstantConverter
    public long getInstantMillis(Object obj, Chronology chronology) {
        return ((Date) obj).getTime();
    }

    @Override // org.joda.time.convert.Converter
    public Class<?> getSupportedType() {
        return Date.class;
    }
}
