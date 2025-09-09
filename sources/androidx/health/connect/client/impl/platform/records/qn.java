package androidx.health.connect.client.impl.platform.records;

import android.health.connect.datatypes.Metadata;
import android.health.connect.datatypes.SleepSessionRecord;
import java.time.Instant;

/* loaded from: classes.dex */
public abstract /* synthetic */ class qn {
    public static /* synthetic */ SleepSessionRecord.Builder a(Metadata metadata, Instant instant, Instant instant2) {
        return new SleepSessionRecord.Builder(metadata, instant, instant2);
    }
}
