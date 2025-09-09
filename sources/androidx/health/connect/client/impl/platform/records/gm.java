package androidx.health.connect.client.impl.platform.records;

import android.health.connect.datatypes.BloodPressureRecord;
import android.health.connect.datatypes.Metadata;
import android.health.connect.datatypes.units.Pressure;
import java.time.Instant;

/* loaded from: classes.dex */
public abstract /* synthetic */ class gm {
    public static /* synthetic */ BloodPressureRecord.Builder a(Metadata metadata, Instant instant, int i2, Pressure pressure, Pressure pressure2, int i3) {
        return new BloodPressureRecord.Builder(metadata, instant, i2, pressure, pressure2, i3);
    }
}
