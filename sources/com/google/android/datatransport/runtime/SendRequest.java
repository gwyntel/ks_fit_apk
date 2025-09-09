package com.google.android.datatransport.runtime;

import com.google.android.datatransport.Encoding;
import com.google.android.datatransport.Event;
import com.google.android.datatransport.Transformer;
import com.google.android.datatransport.runtime.AutoValue_SendRequest;
import com.google.auto.value.AutoValue;

@AutoValue
/* loaded from: classes3.dex */
abstract class SendRequest {

    @AutoValue.Builder
    public static abstract class Builder {
        abstract Builder a(Encoding encoding);

        abstract Builder b(Event event);

        public abstract SendRequest build();

        abstract Builder c(Transformer transformer);

        public <T> Builder setEvent(Event<T> event, Encoding encoding, Transformer<T, byte[]> transformer) {
            b(event);
            a(encoding);
            c(transformer);
            return this;
        }

        public abstract Builder setTransportContext(TransportContext transportContext);

        public abstract Builder setTransportName(String str);
    }

    SendRequest() {
    }

    public static Builder builder() {
        return new AutoValue_SendRequest.Builder();
    }

    abstract Event a();

    abstract Transformer b();

    public abstract Encoding getEncoding();

    public byte[] getPayload() {
        return (byte[]) b().apply(a().getPayload());
    }

    public abstract TransportContext getTransportContext();

    public abstract String getTransportName();
}
