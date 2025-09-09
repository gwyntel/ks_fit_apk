package androidx.health.connect.client.impl.platform.records;

import android.health.connect.datatypes.HeartRateRecord;
import java.time.Instant;

/* loaded from: classes.dex */
public abstract /* synthetic */ class nl {
    public static /* synthetic */ HeartRateRecord.HeartRateSample a(long j2, Instant instant) {
        return new HeartRateRecord.HeartRateSample(j2, instant);
    }
}
