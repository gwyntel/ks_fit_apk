package androidx.health.connect.client.impl.platform.records;

import android.health.connect.datatypes.Metadata;
import android.health.connect.datatypes.SexualActivityRecord;
import java.time.Instant;

/* loaded from: classes.dex */
public abstract /* synthetic */ class kl {
    public static /* synthetic */ SexualActivityRecord.Builder a(Metadata metadata, Instant instant, int i2) {
        return new SexualActivityRecord.Builder(metadata, instant, i2);
    }
}
