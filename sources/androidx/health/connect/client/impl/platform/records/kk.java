package androidx.health.connect.client.impl.platform.records;

import android.health.connect.datatypes.SpeedRecord;
import android.health.connect.datatypes.units.Velocity;
import java.time.Instant;

/* loaded from: classes.dex */
public abstract /* synthetic */ class kk {
    public static /* synthetic */ SpeedRecord.SpeedRecordSample a(Velocity velocity, Instant instant) {
        return new SpeedRecord.SpeedRecordSample(velocity, instant);
    }
}
