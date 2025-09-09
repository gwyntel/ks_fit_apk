package androidx.health.connect.client.impl.platform.records;

import android.health.connect.datatypes.CyclingPedalingCadenceRecord;
import java.time.Instant;

/* loaded from: classes.dex */
public abstract /* synthetic */ class tk {
    public static /* synthetic */ CyclingPedalingCadenceRecord.CyclingPedalingCadenceRecordSample a(double d2, Instant instant) {
        return new CyclingPedalingCadenceRecord.CyclingPedalingCadenceRecordSample(d2, instant);
    }
}
