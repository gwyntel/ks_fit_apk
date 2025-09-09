package androidx.health.connect.client.impl.platform.records;

import android.health.connect.datatypes.Metadata;
import android.health.connect.datatypes.WeightRecord;
import android.health.connect.datatypes.units.Mass;
import java.time.Instant;

/* loaded from: classes.dex */
public abstract /* synthetic */ class gk {
    public static /* synthetic */ WeightRecord.Builder a(Metadata metadata, Instant instant, Mass mass) {
        return new WeightRecord.Builder(metadata, instant, mass);
    }
}
