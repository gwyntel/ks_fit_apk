package androidx.health.connect.client.impl.platform.records;

import android.health.connect.datatypes.HydrationRecord;
import android.health.connect.datatypes.Metadata;
import android.health.connect.datatypes.units.Volume;
import java.time.Instant;

/* loaded from: classes.dex */
public abstract /* synthetic */ class rl {
    public static /* synthetic */ HydrationRecord.Builder a(Metadata metadata, Instant instant, Instant instant2, Volume volume) {
        return new HydrationRecord.Builder(metadata, instant, instant2, volume);
    }
}
