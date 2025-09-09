package org.joda.time.format;

/* loaded from: classes5.dex */
public interface DateTimeParser {
    int estimateParsedLength();

    int parseInto(DateTimeParserBucket dateTimeParserBucket, String str, int i2);
}
