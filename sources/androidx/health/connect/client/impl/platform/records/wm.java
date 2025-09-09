package androidx.health.connect.client.impl.platform.records;

import android.health.connect.datatypes.Metadata;
import android.health.connect.datatypes.RespiratoryRateRecord;
import java.time.Instant;

/* loaded from: classes.dex */
public abstract /* synthetic */ class wm {
    public static /* synthetic */ RespiratoryRateRecord.Builder a(Metadata metadata, Instant instant, double d2) {
        return new RespiratoryRateRecord.Builder(metadata, instant, d2);
    }
}
