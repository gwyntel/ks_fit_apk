package androidx.health.connect.client.impl.platform.records;

import android.health.connect.datatypes.BasalBodyTemperatureRecord;
import android.health.connect.datatypes.Metadata;
import android.health.connect.datatypes.units.Temperature;
import java.time.Instant;

/* loaded from: classes.dex */
public abstract /* synthetic */ class ym {
    public static /* synthetic */ BasalBodyTemperatureRecord.Builder a(Metadata metadata, Instant instant, int i2, Temperature temperature) {
        return new BasalBodyTemperatureRecord.Builder(metadata, instant, i2, temperature);
    }
}
