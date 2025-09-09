package androidx.health.connect.client.impl.platform.records;

import android.health.connect.datatypes.FloorsClimbedRecord;
import android.health.connect.datatypes.Metadata;
import java.time.Instant;

/* loaded from: classes.dex */
public abstract /* synthetic */ class rk {
    public static /* synthetic */ FloorsClimbedRecord.Builder a(Metadata metadata, Instant instant, Instant instant2, double d2) {
        return new FloorsClimbedRecord.Builder(metadata, instant, instant2, d2);
    }
}
