package org.joda.time.format;

import java.io.IOException;
import java.io.Writer;
import java.util.Locale;
import org.joda.time.Chronology;
import org.joda.time.DateTimeZone;
import org.joda.time.ReadablePartial;

/* loaded from: classes5.dex */
public interface DateTimePrinter {
    int estimatePrintedLength();

    void printTo(Writer writer, long j2, Chronology chronology, int i2, DateTimeZone dateTimeZone, Locale locale) throws IOException;

    void printTo(Writer writer, ReadablePartial readablePartial, Locale locale) throws IOException;

    void printTo(StringBuffer stringBuffer, long j2, Chronology chronology, int i2, DateTimeZone dateTimeZone, Locale locale);

    void printTo(StringBuffer stringBuffer, ReadablePartial readablePartial, Locale locale);
}
