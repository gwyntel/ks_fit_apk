package androidx.health.connect.client.impl.platform.records;

import android.health.connect.datatypes.BodyTemperatureRecord;
import android.health.connect.datatypes.Metadata;
import android.health.connect.datatypes.units.Temperature;
import java.time.Instant;

/* loaded from: classes.dex */
public abstract /* synthetic */ class ok {
    public static /* synthetic */ BodyTemperatureRecord.Builder a(Metadata metadata, Instant instant, int i2, Temperature temperature) {
        return new BodyTemperatureRecord.Builder(metadata, instant, i2, temperature);
    }
}
