package androidx.health.connect.client.impl.platform.records;

import android.health.connect.datatypes.HeightRecord;
import android.health.connect.datatypes.Metadata;
import android.health.connect.datatypes.units.Length;
import java.time.Instant;

/* loaded from: classes.dex */
public abstract /* synthetic */ class nm {
    public static /* synthetic */ HeightRecord.Builder a(Metadata metadata, Instant instant, Length length) {
        return new HeightRecord.Builder(metadata, instant, length);
    }
}
