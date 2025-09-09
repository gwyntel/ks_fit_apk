package com.facebook.internal.metrics;

import android.content.Context;
import android.os.SystemClock;
import com.facebook.internal.Utility;
import java.lang.ref.WeakReference;
import java.util.HashMap;

/* loaded from: classes3.dex */
public class MetricsUtil {
    private WeakReference<Context> ctx;
    private MetricsUtil metricsUtil;
    private final String CLASS_TAG = "internal.MetricsUtil";
    private final String STARTUP_METRICS_PREFERENCES = "MetricsUtil";
    private final String TIME_DIFFERENCE_BASE_PREF = "time_difference";
    private HashMap<Tag, Long> taggedStartTimer = new HashMap<>();

    private MetricsUtil(Context context) {
        this.ctx = new WeakReference<>(context);
    }

    private void updateLastTimeDifferenceFor(Tag tag, long j2) {
        if (this.ctx.get() == null) {
            Utility.logd("internal.MetricsUtil", "updateLastTimeDifferenceFor: Context is null");
            return;
        }
        this.ctx.get().getSharedPreferences("MetricsUtil", 0).edit().putLong("time_difference" + tag.getSuffix(), j2).apply();
    }

    public synchronized MetricsUtil getInstance(Context context) {
        MetricsUtil metricsUtil = this.metricsUtil;
        if (metricsUtil != null) {
            return metricsUtil;
        }
        MetricsUtil metricsUtil2 = new MetricsUtil(context);
        this.metricsUtil = metricsUtil2;
        return metricsUtil2;
    }

    public long getLastTimeDifferenceFor(Tag tag) {
        if (this.ctx.get() == null) {
            Utility.logd("internal.MetricsUtil", "getLastTimeDifferenceFor: Context is null");
            return -1L;
        }
        return this.ctx.get().getSharedPreferences("MetricsUtil", 0).getLong("time_difference" + tag.getSuffix(), -1L);
    }

    public void startMeasureFor(Tag tag) {
        this.taggedStartTimer.put(tag, Long.valueOf(SystemClock.elapsedRealtime()));
    }

    public void stopMeasureFor(Tag tag) {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        if (this.taggedStartTimer.containsKey(tag)) {
            long jLongValue = jElapsedRealtime - this.taggedStartTimer.get(tag).longValue();
            this.taggedStartTimer.remove(tag);
            updateLastTimeDifferenceFor(tag, jLongValue);
        }
    }
}
