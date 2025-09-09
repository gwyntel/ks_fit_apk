package com.bumptech.glide.load.engine;

import com.bumptech.glide.load.Key;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
final class Jobs {
    private final Map<Key, EngineJob<?>> jobs = new HashMap();
    private final Map<Key, EngineJob<?>> onlyCacheJobs = new HashMap();

    Jobs() {
    }

    private Map<Key, EngineJob<?>> getJobMap(boolean z2) {
        return z2 ? this.onlyCacheJobs : this.jobs;
    }

    EngineJob a(Key key, boolean z2) {
        return getJobMap(z2).get(key);
    }

    void b(Key key, EngineJob engineJob) {
        getJobMap(engineJob.j()).put(key, engineJob);
    }

    void c(Key key, EngineJob engineJob) {
        Map<Key, EngineJob<?>> jobMap = getJobMap(engineJob.j());
        if (engineJob.equals(jobMap.get(key))) {
            jobMap.remove(key);
        }
    }
}
