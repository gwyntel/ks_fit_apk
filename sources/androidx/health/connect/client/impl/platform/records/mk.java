package androidx.health.connect.client.impl.platform.records;

import android.health.connect.datatypes.StepsCadenceRecord;
import java.time.Instant;

/* loaded from: classes.dex */
public abstract /* synthetic */ class mk {
    public static /* synthetic */ StepsCadenceRecord.StepsCadenceRecordSample a(double d2, Instant instant) {
        return new StepsCadenceRecord.StepsCadenceRecordSample(d2, instant);
    }
}
