package androidx.health.connect.client.impl.platform.records;

import android.health.connect.datatypes.CervicalMucusRecord;
import android.health.connect.datatypes.Metadata;
import java.time.Instant;

/* loaded from: classes.dex */
public abstract /* synthetic */ class em {
    public static /* synthetic */ CervicalMucusRecord.Builder a(Metadata metadata, Instant instant, int i2, int i3) {
        return new CervicalMucusRecord.Builder(metadata, instant, i2, i3);
    }
}
