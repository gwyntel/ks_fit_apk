package androidx.health.connect.client.impl.platform.records;

import android.health.connect.datatypes.Metadata;
import android.health.connect.datatypes.OxygenSaturationRecord;
import android.health.connect.datatypes.units.Percentage;
import java.time.Instant;

/* loaded from: classes.dex */
public abstract /* synthetic */ class cm {
    public static /* synthetic */ OxygenSaturationRecord.Builder a(Metadata metadata, Instant instant, Percentage percentage) {
        return new OxygenSaturationRecord.Builder(metadata, instant, percentage);
    }
}
