package androidx.health.connect.client.impl.platform.records;

import android.health.connect.datatypes.BloodGlucoseRecord;
import android.health.connect.datatypes.Metadata;
import android.health.connect.datatypes.units.BloodGlucose;
import java.time.Instant;

/* loaded from: classes.dex */
public abstract /* synthetic */ class an {
    public static /* synthetic */ BloodGlucoseRecord.Builder a(Metadata metadata, Instant instant, int i2, BloodGlucose bloodGlucose, int i3, int i4) {
        return new BloodGlucoseRecord.Builder(metadata, instant, i2, bloodGlucose, i3, i4);
    }
}
