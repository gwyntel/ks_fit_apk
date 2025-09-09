package androidx.health.connect.client.impl.platform.records;

import android.health.connect.datatypes.SleepSessionRecord;
import java.time.Instant;

/* loaded from: classes.dex */
public abstract /* synthetic */ class gl {
    public static /* synthetic */ SleepSessionRecord.Stage a(Instant instant, Instant instant2, int i2) {
        return new SleepSessionRecord.Stage(instant, instant2, i2);
    }
}
