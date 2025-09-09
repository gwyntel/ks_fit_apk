package androidx.health.connect.client.impl.platform.records;

import android.health.connect.datatypes.ActiveCaloriesBurnedRecord;
import android.health.connect.datatypes.Metadata;
import android.health.connect.datatypes.units.Energy;
import java.time.Instant;

/* loaded from: classes.dex */
public abstract /* synthetic */ class am {
    public static /* synthetic */ ActiveCaloriesBurnedRecord.Builder a(Metadata metadata, Instant instant, Instant instant2, Energy energy) {
        return new ActiveCaloriesBurnedRecord.Builder(metadata, instant, instant2, energy);
    }
}
