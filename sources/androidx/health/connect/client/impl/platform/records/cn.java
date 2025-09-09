package androidx.health.connect.client.impl.platform.records;

import android.health.connect.datatypes.HeartRateVariabilityRmssdRecord;
import android.health.connect.datatypes.Metadata;
import java.time.Instant;

/* loaded from: classes.dex */
public abstract /* synthetic */ class cn {
    public static /* synthetic */ HeartRateVariabilityRmssdRecord.Builder a(Metadata metadata, Instant instant, double d2) {
        return new HeartRateVariabilityRmssdRecord.Builder(metadata, instant, d2);
    }
}
