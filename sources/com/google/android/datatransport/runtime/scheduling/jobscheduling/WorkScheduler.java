package com.google.android.datatransport.runtime.scheduling.jobscheduling;

import com.google.android.datatransport.runtime.TransportContext;

/* loaded from: classes3.dex */
public interface WorkScheduler {
    void schedule(TransportContext transportContext, int i2);

    void schedule(TransportContext transportContext, int i2, boolean z2);
}
