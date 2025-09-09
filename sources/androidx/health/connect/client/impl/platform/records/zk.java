package androidx.health.connect.client.impl.platform.records;

import android.health.connect.datatypes.MenstruationFlowRecord;
import android.health.connect.datatypes.Metadata;
import java.time.Instant;

/* loaded from: classes.dex */
public abstract /* synthetic */ class zk {
    public static /* synthetic */ MenstruationFlowRecord.Builder a(Metadata metadata, Instant instant, int i2) {
        return new MenstruationFlowRecord.Builder(metadata, instant, i2);
    }
}
