package androidx.health.connect.client.impl.platform.records;

import android.health.connect.datatypes.ExerciseSessionRecord;
import android.health.connect.datatypes.Metadata;
import java.time.Instant;

/* loaded from: classes.dex */
public abstract /* synthetic */ class tn {
    public static /* synthetic */ ExerciseSessionRecord.Builder a(Metadata metadata, Instant instant, Instant instant2, int i2) {
        return new ExerciseSessionRecord.Builder(metadata, instant, instant2, i2);
    }
}
