package androidx.health.connect.client.impl.platform.records;

import android.health.connect.AggregateRecordsRequest;
import android.health.connect.TimeRangeFilter;

/* loaded from: classes.dex */
public abstract /* synthetic */ class zr {
    public static /* synthetic */ AggregateRecordsRequest.Builder a(TimeRangeFilter timeRangeFilter) {
        return new AggregateRecordsRequest.Builder(timeRangeFilter);
    }
}
