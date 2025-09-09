package com.google.android.datatransport.runtime.scheduling.persistence;

import com.google.android.datatransport.runtime.scheduling.persistence.EventStoreConfig;

/* loaded from: classes3.dex */
final class AutoValue_EventStoreConfig extends EventStoreConfig {
    private final int criticalSectionEnterTimeoutMs;
    private final long eventCleanUpAge;
    private final int loadBatchSize;
    private final int maxBlobByteSizePerRow;
    private final long maxStorageSizeInBytes;

    static final class Builder extends EventStoreConfig.Builder {
        private Integer criticalSectionEnterTimeoutMs;
        private Long eventCleanUpAge;
        private Integer loadBatchSize;
        private Integer maxBlobByteSizePerRow;
        private Long maxStorageSizeInBytes;

        Builder() {
        }

        @Override // com.google.android.datatransport.runtime.scheduling.persistence.EventStoreConfig.Builder
        EventStoreConfig a() {
            String str = "";
            if (this.maxStorageSizeInBytes == null) {
                str = " maxStorageSizeInBytes";
            }
            if (this.loadBatchSize == null) {
                str = str + " loadBatchSize";
            }
            if (this.criticalSectionEnterTimeoutMs == null) {
                str = str + " criticalSectionEnterTimeoutMs";
            }
            if (this.eventCleanUpAge == null) {
                str = str + " eventCleanUpAge";
            }
            if (this.maxBlobByteSizePerRow == null) {
                str = str + " maxBlobByteSizePerRow";
            }
            if (str.isEmpty()) {
                return new AutoValue_EventStoreConfig(this.maxStorageSizeInBytes.longValue(), this.loadBatchSize.intValue(), this.criticalSectionEnterTimeoutMs.intValue(), this.eventCleanUpAge.longValue(), this.maxBlobByteSizePerRow.intValue());
            }
            throw new IllegalStateException("Missing required properties:" + str);
        }

        @Override // com.google.android.datatransport.runtime.scheduling.persistence.EventStoreConfig.Builder
        EventStoreConfig.Builder b(int i2) {
            this.criticalSectionEnterTimeoutMs = Integer.valueOf(i2);
            return this;
        }

        @Override // com.google.android.datatransport.runtime.scheduling.persistence.EventStoreConfig.Builder
        EventStoreConfig.Builder c(long j2) {
            this.eventCleanUpAge = Long.valueOf(j2);
            return this;
        }

        @Override // com.google.android.datatransport.runtime.scheduling.persistence.EventStoreConfig.Builder
        EventStoreConfig.Builder d(int i2) {
            this.loadBatchSize = Integer.valueOf(i2);
            return this;
        }

        @Override // com.google.android.datatransport.runtime.scheduling.persistence.EventStoreConfig.Builder
        EventStoreConfig.Builder e(int i2) {
            this.maxBlobByteSizePerRow = Integer.valueOf(i2);
            return this;
        }

        @Override // com.google.android.datatransport.runtime.scheduling.persistence.EventStoreConfig.Builder
        EventStoreConfig.Builder f(long j2) {
            this.maxStorageSizeInBytes = Long.valueOf(j2);
            return this;
        }
    }

    @Override // com.google.android.datatransport.runtime.scheduling.persistence.EventStoreConfig
    int b() {
        return this.criticalSectionEnterTimeoutMs;
    }

    @Override // com.google.android.datatransport.runtime.scheduling.persistence.EventStoreConfig
    long c() {
        return this.eventCleanUpAge;
    }

    @Override // com.google.android.datatransport.runtime.scheduling.persistence.EventStoreConfig
    int d() {
        return this.loadBatchSize;
    }

    @Override // com.google.android.datatransport.runtime.scheduling.persistence.EventStoreConfig
    int e() {
        return this.maxBlobByteSizePerRow;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof EventStoreConfig)) {
            return false;
        }
        EventStoreConfig eventStoreConfig = (EventStoreConfig) obj;
        return this.maxStorageSizeInBytes == eventStoreConfig.f() && this.loadBatchSize == eventStoreConfig.d() && this.criticalSectionEnterTimeoutMs == eventStoreConfig.b() && this.eventCleanUpAge == eventStoreConfig.c() && this.maxBlobByteSizePerRow == eventStoreConfig.e();
    }

    @Override // com.google.android.datatransport.runtime.scheduling.persistence.EventStoreConfig
    long f() {
        return this.maxStorageSizeInBytes;
    }

    public int hashCode() {
        long j2 = this.maxStorageSizeInBytes;
        int i2 = (((((((int) (j2 ^ (j2 >>> 32))) ^ 1000003) * 1000003) ^ this.loadBatchSize) * 1000003) ^ this.criticalSectionEnterTimeoutMs) * 1000003;
        long j3 = this.eventCleanUpAge;
        return ((i2 ^ ((int) ((j3 >>> 32) ^ j3))) * 1000003) ^ this.maxBlobByteSizePerRow;
    }

    public String toString() {
        return "EventStoreConfig{maxStorageSizeInBytes=" + this.maxStorageSizeInBytes + ", loadBatchSize=" + this.loadBatchSize + ", criticalSectionEnterTimeoutMs=" + this.criticalSectionEnterTimeoutMs + ", eventCleanUpAge=" + this.eventCleanUpAge + ", maxBlobByteSizePerRow=" + this.maxBlobByteSizePerRow + com.alipay.sdk.m.u.i.f9804d;
    }

    private AutoValue_EventStoreConfig(long j2, int i2, int i3, long j3, int i4) {
        this.maxStorageSizeInBytes = j2;
        this.loadBatchSize = i2;
        this.criticalSectionEnterTimeoutMs = i3;
        this.eventCleanUpAge = j3;
        this.maxBlobByteSizePerRow = i4;
    }
}
