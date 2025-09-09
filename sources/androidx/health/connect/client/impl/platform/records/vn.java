package androidx.health.connect.client.impl.platform.records;

import android.health.connect.datatypes.CyclingPedalingCadenceRecord;
import android.health.connect.datatypes.Metadata;
import java.time.Instant;
import java.util.List;

/* loaded from: classes.dex */
public abstract /* synthetic */ class vn {
    public static /* synthetic */ CyclingPedalingCadenceRecord.Builder a(Metadata metadata, Instant instant, Instant instant2, List list) {
        return new CyclingPedalingCadenceRecord.Builder(metadata, instant, instant2, list);
    }
}
