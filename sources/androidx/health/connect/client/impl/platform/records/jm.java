package androidx.health.connect.client.impl.platform.records;

import android.health.connect.datatypes.ExerciseSegment;
import java.time.Instant;

/* loaded from: classes.dex */
public abstract /* synthetic */ class jm {
    public static /* synthetic */ ExerciseSegment.Builder a(Instant instant, Instant instant2, int i2) {
        return new ExerciseSegment.Builder(instant, instant2, i2);
    }
}
