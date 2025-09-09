package androidx.health.connect.client.impl.platform.records;

import android.health.connect.datatypes.PowerRecord;
import android.health.connect.datatypes.units.Power;
import java.time.Instant;

/* loaded from: classes.dex */
public abstract /* synthetic */ class tl {
    public static /* synthetic */ PowerRecord.PowerRecordSample a(Power power, Instant instant) {
        return new PowerRecord.PowerRecordSample(power, instant);
    }
}
