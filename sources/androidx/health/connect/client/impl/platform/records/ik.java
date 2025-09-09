package androidx.health.connect.client.impl.platform.records;

import android.health.connect.datatypes.BodyWaterMassRecord;
import android.health.connect.datatypes.Metadata;
import android.health.connect.datatypes.units.Mass;
import java.time.Instant;

/* loaded from: classes.dex */
public abstract /* synthetic */ class ik {
    public static /* synthetic */ BodyWaterMassRecord.Builder a(Metadata metadata, Instant instant, Mass mass) {
        return new BodyWaterMassRecord.Builder(metadata, instant, mass);
    }
}
