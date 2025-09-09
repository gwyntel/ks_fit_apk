package androidx.health.connect.client.impl.platform.records;

import android.health.connect.datatypes.HeartRateRecord;
import android.health.connect.datatypes.Metadata;
import java.time.Instant;
import java.util.List;

/* loaded from: classes.dex */
public abstract /* synthetic */ class wn {
    public static /* synthetic */ HeartRateRecord.Builder a(Metadata metadata, Instant instant, Instant instant2, List list) {
        return new HeartRateRecord.Builder(metadata, instant, instant2, list);
    }
}
