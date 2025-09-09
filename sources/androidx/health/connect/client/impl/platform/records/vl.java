package androidx.health.connect.client.impl.platform.records;

import android.health.connect.datatypes.BodyFatRecord;
import android.health.connect.datatypes.Metadata;
import android.health.connect.datatypes.units.Percentage;
import java.time.Instant;

/* loaded from: classes.dex */
public abstract /* synthetic */ class vl {
    public static /* synthetic */ BodyFatRecord.Builder a(Metadata metadata, Instant instant, Percentage percentage) {
        return new BodyFatRecord.Builder(metadata, instant, percentage);
    }
}
