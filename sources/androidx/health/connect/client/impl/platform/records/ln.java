package androidx.health.connect.client.impl.platform.records;

import android.health.connect.datatypes.BoneMassRecord;
import android.health.connect.datatypes.Metadata;
import android.health.connect.datatypes.units.Mass;
import java.time.Instant;

/* loaded from: classes.dex */
public abstract /* synthetic */ class ln {
    public static /* synthetic */ BoneMassRecord.Builder a(Metadata metadata, Instant instant, Mass mass) {
        return new BoneMassRecord.Builder(metadata, instant, mass);
    }
}
