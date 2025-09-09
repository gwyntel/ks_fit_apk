package androidx.health.connect.client.impl.platform.records;

import android.health.connect.datatypes.Metadata;
import android.health.connect.datatypes.RestingHeartRateRecord;
import java.time.Instant;

/* loaded from: classes.dex */
public abstract /* synthetic */ class fn {
    public static /* synthetic */ RestingHeartRateRecord.Builder a(Metadata metadata, Instant instant, long j2) {
        return new RestingHeartRateRecord.Builder(metadata, instant, j2);
    }
}
