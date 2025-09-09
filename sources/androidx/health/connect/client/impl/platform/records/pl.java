package androidx.health.connect.client.impl.platform.records;

import android.health.connect.datatypes.Metadata;
import android.health.connect.datatypes.OvulationTestRecord;
import java.time.Instant;

/* loaded from: classes.dex */
public abstract /* synthetic */ class pl {
    public static /* synthetic */ OvulationTestRecord.Builder a(Metadata metadata, Instant instant, int i2) {
        return new OvulationTestRecord.Builder(metadata, instant, i2);
    }
}
