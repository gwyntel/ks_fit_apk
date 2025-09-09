package androidx.health.connect.client.impl.platform.records;

import android.health.connect.datatypes.BasalMetabolicRateRecord;
import android.health.connect.datatypes.Metadata;
import android.health.connect.datatypes.units.Power;
import java.time.Instant;

/* loaded from: classes.dex */
public abstract /* synthetic */ class el {
    public static /* synthetic */ BasalMetabolicRateRecord.Builder a(Metadata metadata, Instant instant, Power power) {
        return new BasalMetabolicRateRecord.Builder(metadata, instant, power);
    }
}
