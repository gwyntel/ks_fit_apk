package androidx.health.connect.client.impl.platform.records;

import android.health.connect.datatypes.IntermenstrualBleedingRecord;
import android.health.connect.datatypes.Metadata;
import java.time.Instant;

/* loaded from: classes.dex */
public abstract /* synthetic */ class vk {
    public static /* synthetic */ IntermenstrualBleedingRecord.Builder a(Metadata metadata, Instant instant) {
        return new IntermenstrualBleedingRecord.Builder(metadata, instant);
    }
}
