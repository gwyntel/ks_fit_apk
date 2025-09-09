package com.google.android.datatransport.runtime.scheduling.persistence;

import android.content.Context;
import com.google.android.datatransport.runtime.dagger.Module;

@Module
/* loaded from: classes3.dex */
public abstract class EventStoreModule {
    static String a() {
        return "com.google.android.datatransport.events";
    }

    static String b(Context context) {
        return context.getPackageName();
    }

    static int c() {
        return SchemaManager.f12561a;
    }

    static EventStoreConfig d() {
        return EventStoreConfig.f12558a;
    }
}
