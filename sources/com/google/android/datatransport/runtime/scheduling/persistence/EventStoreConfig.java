package com.google.android.datatransport.runtime.scheduling.persistence;

import com.google.android.datatransport.runtime.scheduling.persistence.AutoValue_EventStoreConfig;
import com.google.auto.value.AutoValue;

@AutoValue
/* loaded from: classes3.dex */
abstract class EventStoreConfig {
    private static final int LOAD_BATCH_SIZE = 200;
    private static final int LOCK_TIME_OUT_MS = 10000;
    private static final long MAX_DB_STORAGE_SIZE_IN_BYTES = 10485760;
    private static final long DURATION_ONE_WEEK_MS = 604800000;
    private static final int MAX_BLOB_BYTE_SIZE_PER_ROW = 81920;

    /* renamed from: a, reason: collision with root package name */
    static final EventStoreConfig f12558a = a().f(MAX_DB_STORAGE_SIZE_IN_BYTES).d(200).b(10000).c(DURATION_ONE_WEEK_MS).e(MAX_BLOB_BYTE_SIZE_PER_ROW).a();

    @AutoValue.Builder
    static abstract class Builder {
        Builder() {
        }

        abstract EventStoreConfig a();

        abstract Builder b(int i2);

        abstract Builder c(long j2);

        abstract Builder d(int i2);

        abstract Builder e(int i2);

        abstract Builder f(long j2);
    }

    EventStoreConfig() {
    }

    static Builder a() {
        return new AutoValue_EventStoreConfig.Builder();
    }

    abstract int b();

    abstract long c();

    abstract int d();

    abstract int e();

    abstract long f();
}
