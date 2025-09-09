package androidx.health.connect.client.impl.platform.records;

import android.health.connect.datatypes.DistanceRecord;
import android.health.connect.datatypes.Metadata;
import android.health.connect.datatypes.units.Length;
import java.time.Instant;

/* loaded from: classes.dex */
public abstract /* synthetic */ class pm {
    public static /* synthetic */ DistanceRecord.Builder a(Metadata metadata, Instant instant, Instant instant2, Length length) {
        return new DistanceRecord.Builder(metadata, instant, instant2, length);
    }
}
