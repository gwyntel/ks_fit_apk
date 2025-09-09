package org.joda.time.tz;

import java.util.Set;
import org.joda.time.DateTimeZone;

/* loaded from: classes5.dex */
public interface Provider {
    Set<String> getAvailableIDs();

    DateTimeZone getZone(String str);
}
