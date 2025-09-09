package org.joda.time.format;

import java.util.Locale;
import org.joda.time.ReadWritablePeriod;

/* loaded from: classes5.dex */
public interface PeriodParser {
    int parseInto(ReadWritablePeriod readWritablePeriod, String str, int i2, Locale locale);
}
