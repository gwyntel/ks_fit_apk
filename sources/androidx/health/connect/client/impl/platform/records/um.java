package androidx.health.connect.client.impl.platform.records;

import android.health.connect.datatypes.LeanBodyMassRecord;
import android.health.connect.datatypes.Metadata;
import android.health.connect.datatypes.units.Mass;
import java.time.Instant;

/* loaded from: classes.dex */
public abstract /* synthetic */ class um {
    public static /* synthetic */ LeanBodyMassRecord.Builder a(Metadata metadata, Instant instant, Mass mass) {
        return new LeanBodyMassRecord.Builder(metadata, instant, mass);
    }
}
