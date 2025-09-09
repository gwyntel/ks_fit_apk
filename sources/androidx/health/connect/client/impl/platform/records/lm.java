package androidx.health.connect.client.impl.platform.records;

import android.health.connect.datatypes.ExerciseLap;
import java.time.Instant;

/* loaded from: classes.dex */
public abstract /* synthetic */ class lm {
    public static /* synthetic */ ExerciseLap.Builder a(Instant instant, Instant instant2) {
        return new ExerciseLap.Builder(instant, instant2);
    }
}
