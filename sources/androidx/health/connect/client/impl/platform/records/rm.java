package androidx.health.connect.client.impl.platform.records;

import android.health.connect.datatypes.Metadata;
import android.health.connect.datatypes.WheelchairPushesRecord;
import java.time.Instant;

/* loaded from: classes.dex */
public abstract /* synthetic */ class rm {
    public static /* synthetic */ WheelchairPushesRecord.Builder a(Metadata metadata, Instant instant, Instant instant2, long j2) {
        return new WheelchairPushesRecord.Builder(metadata, instant, instant2, j2);
    }
}
