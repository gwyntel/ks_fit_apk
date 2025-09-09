package androidx.health.connect.client.impl.platform.records;

import android.health.connect.datatypes.Metadata;
import android.health.connect.datatypes.NutritionRecord;
import java.time.Instant;

/* loaded from: classes.dex */
public abstract /* synthetic */ class cl {
    public static /* synthetic */ NutritionRecord.Builder a(Metadata metadata, Instant instant, Instant instant2) {
        return new NutritionRecord.Builder(metadata, instant, instant2);
    }
}
