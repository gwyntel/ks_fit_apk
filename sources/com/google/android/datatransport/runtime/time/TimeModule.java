package com.google.android.datatransport.runtime.time;

import com.google.android.datatransport.runtime.dagger.Module;

@Module
/* loaded from: classes3.dex */
public abstract class TimeModule {
    static Clock a() {
        return new WallTimeClock();
    }

    static Clock b() {
        return new UptimeClock();
    }
}
