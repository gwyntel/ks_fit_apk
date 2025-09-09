package com.google.android.datatransport.cct.internal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.datatransport.cct.internal.AutoValue_LogRequest;
import com.google.auto.value.AutoValue;
import com.google.firebase.encoders.annotations.Encodable;
import java.util.List;

@AutoValue
/* loaded from: classes3.dex */
public abstract class LogRequest {

    @AutoValue.Builder
    public static abstract class Builder {
        abstract Builder a(Integer num);

        abstract Builder b(String str);

        @NonNull
        public abstract LogRequest build();

        @NonNull
        public abstract Builder setClientInfo(@Nullable ClientInfo clientInfo);

        @NonNull
        public abstract Builder setLogEvents(@Nullable List<LogEvent> list);

        @NonNull
        public abstract Builder setQosTier(@Nullable QosTier qosTier);

        @NonNull
        public abstract Builder setRequestTimeMs(long j2);

        @NonNull
        public abstract Builder setRequestUptimeMs(long j2);

        @NonNull
        public Builder setSource(int i2) {
            return a(Integer.valueOf(i2));
        }

        @NonNull
        public Builder setSource(@NonNull String str) {
            return b(str);
        }
    }

    @NonNull
    public static Builder builder() {
        return new AutoValue_LogRequest.Builder();
    }

    @Nullable
    public abstract ClientInfo getClientInfo();

    @Nullable
    @Encodable.Field(name = "logEvent")
    public abstract List<LogEvent> getLogEvents();

    @Nullable
    public abstract Integer getLogSource();

    @Nullable
    public abstract String getLogSourceName();

    @Nullable
    public abstract QosTier getQosTier();

    public abstract long getRequestTimeMs();

    public abstract long getRequestUptimeMs();
}
