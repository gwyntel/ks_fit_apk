package androidx.health.connect.client.impl.platform.records;

import android.health.connect.datatypes.Metadata;
import android.health.connect.datatypes.SpeedRecord;
import java.time.Instant;
import java.util.List;

/* loaded from: classes.dex */
public abstract /* synthetic */ class sn {
    public static /* synthetic */ SpeedRecord.Builder a(Metadata metadata, Instant instant, Instant instant2, List list) {
        return new SpeedRecord.Builder(metadata, instant, instant2, list);
    }
}
