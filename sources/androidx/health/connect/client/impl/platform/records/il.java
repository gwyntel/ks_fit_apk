package androidx.health.connect.client.impl.platform.records;

import android.health.connect.datatypes.Metadata;
import android.health.connect.datatypes.Vo2MaxRecord;
import java.time.Instant;

/* loaded from: classes.dex */
public abstract /* synthetic */ class il {
    public static /* synthetic */ Vo2MaxRecord.Builder a(Metadata metadata, Instant instant, int i2, double d2) {
        return new Vo2MaxRecord.Builder(metadata, instant, i2, d2);
    }
}
