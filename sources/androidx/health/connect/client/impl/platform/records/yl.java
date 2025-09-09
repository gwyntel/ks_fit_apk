package androidx.health.connect.client.impl.platform.records;

import android.health.connect.datatypes.Metadata;
import android.health.connect.datatypes.StepsRecord;
import java.time.Instant;

/* loaded from: classes.dex */
public abstract /* synthetic */ class yl {
    public static /* synthetic */ StepsRecord.Builder a(Metadata metadata, Instant instant, Instant instant2, long j2) {
        return new StepsRecord.Builder(metadata, instant, instant2, j2);
    }
}
