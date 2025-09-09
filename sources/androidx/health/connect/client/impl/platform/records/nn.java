package androidx.health.connect.client.impl.platform.records;

import android.health.connect.datatypes.Metadata;
import android.health.connect.datatypes.PowerRecord;
import java.time.Instant;
import java.util.List;

/* loaded from: classes.dex */
public abstract /* synthetic */ class nn {
    public static /* synthetic */ PowerRecord.Builder a(Metadata metadata, Instant instant, Instant instant2, List list) {
        return new PowerRecord.Builder(metadata, instant, instant2, list);
    }
}
