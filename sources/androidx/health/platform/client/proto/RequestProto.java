package androidx.health.platform.client.proto;

import androidx.health.platform.client.proto.DataProto;
import androidx.health.platform.client.proto.GeneratedMessageLite;
import androidx.health.platform.client.proto.Internal;
import androidx.health.platform.client.proto.TimeProto;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public final class RequestProto {

    /* renamed from: androidx.health.platform.client.proto.RequestProto$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f4477a;

        static {
            int[] iArr = new int[GeneratedMessageLite.MethodToInvoke.values().length];
            f4477a = iArr;
            try {
                iArr[GeneratedMessageLite.MethodToInvoke.NEW_MUTABLE_INSTANCE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f4477a[GeneratedMessageLite.MethodToInvoke.NEW_BUILDER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f4477a[GeneratedMessageLite.MethodToInvoke.BUILD_MESSAGE_INFO.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f4477a[GeneratedMessageLite.MethodToInvoke.GET_DEFAULT_INSTANCE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f4477a[GeneratedMessageLite.MethodToInvoke.GET_PARSER.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f4477a[GeneratedMessageLite.MethodToInvoke.GET_MEMOIZED_IS_INITIALIZED.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f4477a[GeneratedMessageLite.MethodToInvoke.SET_MEMOIZED_IS_INITIALIZED.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    public static final class AggregateDataRequest extends GeneratedMessageLite<AggregateDataRequest, Builder> implements AggregateDataRequestOrBuilder {
        public static final int DATA_ORIGIN_FIELD_NUMBER = 3;
        private static final AggregateDataRequest DEFAULT_INSTANCE;
        public static final int METRIC_SPEC_FIELD_NUMBER = 2;
        private static volatile Parser<AggregateDataRequest> PARSER = null;
        public static final int SLICE_DURATION_MILLIS_FIELD_NUMBER = 4;
        public static final int SLICE_PERIOD_FIELD_NUMBER = 5;
        public static final int TIME_SPEC_FIELD_NUMBER = 1;
        private int bitField0_;
        private long sliceDurationMillis_;
        private TimeProto.TimeSpec timeSpec_;
        private Internal.ProtobufList<AggregateMetricSpec> metricSpec_ = GeneratedMessageLite.r();
        private Internal.ProtobufList<DataProto.DataOrigin> dataOrigin_ = GeneratedMessageLite.r();
        private String slicePeriod_ = "";

        public static final class Builder extends GeneratedMessageLite.Builder<AggregateDataRequest, Builder> implements AggregateDataRequestOrBuilder {
            /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
                this();
            }

            public Builder addAllDataOrigin(Iterable<? extends DataProto.DataOrigin> iterable) {
                d();
                ((AggregateDataRequest) this.f4444a).addAllDataOrigin(iterable);
                return this;
            }

            public Builder addAllMetricSpec(Iterable<? extends AggregateMetricSpec> iterable) {
                d();
                ((AggregateDataRequest) this.f4444a).addAllMetricSpec(iterable);
                return this;
            }

            public Builder addDataOrigin(DataProto.DataOrigin dataOrigin) {
                d();
                ((AggregateDataRequest) this.f4444a).addDataOrigin(dataOrigin);
                return this;
            }

            public Builder addMetricSpec(AggregateMetricSpec aggregateMetricSpec) {
                d();
                ((AggregateDataRequest) this.f4444a).addMetricSpec(aggregateMetricSpec);
                return this;
            }

            public Builder clearDataOrigin() {
                d();
                ((AggregateDataRequest) this.f4444a).clearDataOrigin();
                return this;
            }

            public Builder clearMetricSpec() {
                d();
                ((AggregateDataRequest) this.f4444a).clearMetricSpec();
                return this;
            }

            public Builder clearSliceDurationMillis() {
                d();
                ((AggregateDataRequest) this.f4444a).clearSliceDurationMillis();
                return this;
            }

            public Builder clearSlicePeriod() {
                d();
                ((AggregateDataRequest) this.f4444a).clearSlicePeriod();
                return this;
            }

            public Builder clearTimeSpec() {
                d();
                ((AggregateDataRequest) this.f4444a).clearTimeSpec();
                return this;
            }

            @Override // androidx.health.platform.client.proto.RequestProto.AggregateDataRequestOrBuilder
            public DataProto.DataOrigin getDataOrigin(int i2) {
                return ((AggregateDataRequest) this.f4444a).getDataOrigin(i2);
            }

            @Override // androidx.health.platform.client.proto.RequestProto.AggregateDataRequestOrBuilder
            public int getDataOriginCount() {
                return ((AggregateDataRequest) this.f4444a).getDataOriginCount();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.AggregateDataRequestOrBuilder
            public List<DataProto.DataOrigin> getDataOriginList() {
                return Collections.unmodifiableList(((AggregateDataRequest) this.f4444a).getDataOriginList());
            }

            @Override // androidx.health.platform.client.proto.RequestProto.AggregateDataRequestOrBuilder
            public AggregateMetricSpec getMetricSpec(int i2) {
                return ((AggregateDataRequest) this.f4444a).getMetricSpec(i2);
            }

            @Override // androidx.health.platform.client.proto.RequestProto.AggregateDataRequestOrBuilder
            public int getMetricSpecCount() {
                return ((AggregateDataRequest) this.f4444a).getMetricSpecCount();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.AggregateDataRequestOrBuilder
            public List<AggregateMetricSpec> getMetricSpecList() {
                return Collections.unmodifiableList(((AggregateDataRequest) this.f4444a).getMetricSpecList());
            }

            @Override // androidx.health.platform.client.proto.RequestProto.AggregateDataRequestOrBuilder
            public long getSliceDurationMillis() {
                return ((AggregateDataRequest) this.f4444a).getSliceDurationMillis();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.AggregateDataRequestOrBuilder
            public String getSlicePeriod() {
                return ((AggregateDataRequest) this.f4444a).getSlicePeriod();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.AggregateDataRequestOrBuilder
            public ByteString getSlicePeriodBytes() {
                return ((AggregateDataRequest) this.f4444a).getSlicePeriodBytes();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.AggregateDataRequestOrBuilder
            public TimeProto.TimeSpec getTimeSpec() {
                return ((AggregateDataRequest) this.f4444a).getTimeSpec();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.AggregateDataRequestOrBuilder
            public boolean hasSliceDurationMillis() {
                return ((AggregateDataRequest) this.f4444a).hasSliceDurationMillis();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.AggregateDataRequestOrBuilder
            public boolean hasSlicePeriod() {
                return ((AggregateDataRequest) this.f4444a).hasSlicePeriod();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.AggregateDataRequestOrBuilder
            public boolean hasTimeSpec() {
                return ((AggregateDataRequest) this.f4444a).hasTimeSpec();
            }

            public Builder mergeTimeSpec(TimeProto.TimeSpec timeSpec) {
                d();
                ((AggregateDataRequest) this.f4444a).mergeTimeSpec(timeSpec);
                return this;
            }

            public Builder removeDataOrigin(int i2) {
                d();
                ((AggregateDataRequest) this.f4444a).removeDataOrigin(i2);
                return this;
            }

            public Builder removeMetricSpec(int i2) {
                d();
                ((AggregateDataRequest) this.f4444a).removeMetricSpec(i2);
                return this;
            }

            public Builder setDataOrigin(int i2, DataProto.DataOrigin dataOrigin) {
                d();
                ((AggregateDataRequest) this.f4444a).setDataOrigin(i2, dataOrigin);
                return this;
            }

            public Builder setMetricSpec(int i2, AggregateMetricSpec aggregateMetricSpec) {
                d();
                ((AggregateDataRequest) this.f4444a).setMetricSpec(i2, aggregateMetricSpec);
                return this;
            }

            public Builder setSliceDurationMillis(long j2) {
                d();
                ((AggregateDataRequest) this.f4444a).setSliceDurationMillis(j2);
                return this;
            }

            public Builder setSlicePeriod(String str) {
                d();
                ((AggregateDataRequest) this.f4444a).setSlicePeriod(str);
                return this;
            }

            public Builder setSlicePeriodBytes(ByteString byteString) {
                d();
                ((AggregateDataRequest) this.f4444a).setSlicePeriodBytes(byteString);
                return this;
            }

            public Builder setTimeSpec(TimeProto.TimeSpec timeSpec) {
                d();
                ((AggregateDataRequest) this.f4444a).setTimeSpec(timeSpec);
                return this;
            }

            private Builder() {
                super(AggregateDataRequest.DEFAULT_INSTANCE);
            }

            public Builder addDataOrigin(int i2, DataProto.DataOrigin dataOrigin) {
                d();
                ((AggregateDataRequest) this.f4444a).addDataOrigin(i2, dataOrigin);
                return this;
            }

            public Builder addMetricSpec(int i2, AggregateMetricSpec aggregateMetricSpec) {
                d();
                ((AggregateDataRequest) this.f4444a).addMetricSpec(i2, aggregateMetricSpec);
                return this;
            }

            public Builder setDataOrigin(int i2, DataProto.DataOrigin.Builder builder) {
                d();
                ((AggregateDataRequest) this.f4444a).setDataOrigin(i2, builder.build());
                return this;
            }

            public Builder setMetricSpec(int i2, AggregateMetricSpec.Builder builder) {
                d();
                ((AggregateDataRequest) this.f4444a).setMetricSpec(i2, builder.build());
                return this;
            }

            public Builder setTimeSpec(TimeProto.TimeSpec.Builder builder) {
                d();
                ((AggregateDataRequest) this.f4444a).setTimeSpec(builder.build());
                return this;
            }

            public Builder addDataOrigin(DataProto.DataOrigin.Builder builder) {
                d();
                ((AggregateDataRequest) this.f4444a).addDataOrigin(builder.build());
                return this;
            }

            public Builder addMetricSpec(AggregateMetricSpec.Builder builder) {
                d();
                ((AggregateDataRequest) this.f4444a).addMetricSpec(builder.build());
                return this;
            }

            public Builder addDataOrigin(int i2, DataProto.DataOrigin.Builder builder) {
                d();
                ((AggregateDataRequest) this.f4444a).addDataOrigin(i2, builder.build());
                return this;
            }

            public Builder addMetricSpec(int i2, AggregateMetricSpec.Builder builder) {
                d();
                ((AggregateDataRequest) this.f4444a).addMetricSpec(i2, builder.build());
                return this;
            }
        }

        static {
            AggregateDataRequest aggregateDataRequest = new AggregateDataRequest();
            DEFAULT_INSTANCE = aggregateDataRequest;
            GeneratedMessageLite.T(AggregateDataRequest.class, aggregateDataRequest);
        }

        private AggregateDataRequest() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addAllDataOrigin(Iterable<? extends DataProto.DataOrigin> iterable) {
            ensureDataOriginIsMutable();
            AbstractMessageLite.a(iterable, this.dataOrigin_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addAllMetricSpec(Iterable<? extends AggregateMetricSpec> iterable) {
            ensureMetricSpecIsMutable();
            AbstractMessageLite.a(iterable, this.metricSpec_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addDataOrigin(DataProto.DataOrigin dataOrigin) {
            dataOrigin.getClass();
            ensureDataOriginIsMutable();
            this.dataOrigin_.add(dataOrigin);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addMetricSpec(AggregateMetricSpec aggregateMetricSpec) {
            aggregateMetricSpec.getClass();
            ensureMetricSpecIsMutable();
            this.metricSpec_.add(aggregateMetricSpec);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearDataOrigin() {
            this.dataOrigin_ = GeneratedMessageLite.r();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearMetricSpec() {
            this.metricSpec_ = GeneratedMessageLite.r();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearSliceDurationMillis() {
            this.bitField0_ &= -3;
            this.sliceDurationMillis_ = 0L;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearSlicePeriod() {
            this.bitField0_ &= -5;
            this.slicePeriod_ = getDefaultInstance().getSlicePeriod();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearTimeSpec() {
            this.timeSpec_ = null;
            this.bitField0_ &= -2;
        }

        private void ensureDataOriginIsMutable() {
            Internal.ProtobufList<DataProto.DataOrigin> protobufList = this.dataOrigin_;
            if (protobufList.isModifiable()) {
                return;
            }
            this.dataOrigin_ = GeneratedMessageLite.C(protobufList);
        }

        private void ensureMetricSpecIsMutable() {
            Internal.ProtobufList<AggregateMetricSpec> protobufList = this.metricSpec_;
            if (protobufList.isModifiable()) {
                return;
            }
            this.metricSpec_ = GeneratedMessageLite.C(protobufList);
        }

        public static AggregateDataRequest getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mergeTimeSpec(TimeProto.TimeSpec timeSpec) {
            timeSpec.getClass();
            TimeProto.TimeSpec timeSpec2 = this.timeSpec_;
            if (timeSpec2 == null || timeSpec2 == TimeProto.TimeSpec.getDefaultInstance()) {
                this.timeSpec_ = timeSpec;
            } else {
                this.timeSpec_ = TimeProto.TimeSpec.newBuilder(this.timeSpec_).mergeFrom((TimeProto.TimeSpec.Builder) timeSpec).buildPartial();
            }
            this.bitField0_ |= 1;
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.m();
        }

        public static AggregateDataRequest parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (AggregateDataRequest) GeneratedMessageLite.F(DEFAULT_INSTANCE, inputStream);
        }

        public static AggregateDataRequest parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (AggregateDataRequest) GeneratedMessageLite.N(DEFAULT_INSTANCE, byteBuffer);
        }

        public static Parser<AggregateDataRequest> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void removeDataOrigin(int i2) {
            ensureDataOriginIsMutable();
            this.dataOrigin_.remove(i2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void removeMetricSpec(int i2) {
            ensureMetricSpecIsMutable();
            this.metricSpec_.remove(i2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setDataOrigin(int i2, DataProto.DataOrigin dataOrigin) {
            dataOrigin.getClass();
            ensureDataOriginIsMutable();
            this.dataOrigin_.set(i2, dataOrigin);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setMetricSpec(int i2, AggregateMetricSpec aggregateMetricSpec) {
            aggregateMetricSpec.getClass();
            ensureMetricSpecIsMutable();
            this.metricSpec_.set(i2, aggregateMetricSpec);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setSliceDurationMillis(long j2) {
            this.bitField0_ |= 2;
            this.sliceDurationMillis_ = j2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setSlicePeriod(String str) {
            str.getClass();
            this.bitField0_ |= 4;
            this.slicePeriod_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setSlicePeriodBytes(ByteString byteString) {
            this.slicePeriod_ = byteString.toStringUtf8();
            this.bitField0_ |= 4;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setTimeSpec(TimeProto.TimeSpec timeSpec) {
            timeSpec.getClass();
            this.timeSpec_ = timeSpec;
            this.bitField0_ |= 1;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.AggregateDataRequestOrBuilder
        public DataProto.DataOrigin getDataOrigin(int i2) {
            return this.dataOrigin_.get(i2);
        }

        @Override // androidx.health.platform.client.proto.RequestProto.AggregateDataRequestOrBuilder
        public int getDataOriginCount() {
            return this.dataOrigin_.size();
        }

        @Override // androidx.health.platform.client.proto.RequestProto.AggregateDataRequestOrBuilder
        public List<DataProto.DataOrigin> getDataOriginList() {
            return this.dataOrigin_;
        }

        public DataProto.DataOriginOrBuilder getDataOriginOrBuilder(int i2) {
            return this.dataOrigin_.get(i2);
        }

        public List<? extends DataProto.DataOriginOrBuilder> getDataOriginOrBuilderList() {
            return this.dataOrigin_;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.AggregateDataRequestOrBuilder
        public AggregateMetricSpec getMetricSpec(int i2) {
            return this.metricSpec_.get(i2);
        }

        @Override // androidx.health.platform.client.proto.RequestProto.AggregateDataRequestOrBuilder
        public int getMetricSpecCount() {
            return this.metricSpec_.size();
        }

        @Override // androidx.health.platform.client.proto.RequestProto.AggregateDataRequestOrBuilder
        public List<AggregateMetricSpec> getMetricSpecList() {
            return this.metricSpec_;
        }

        public AggregateMetricSpecOrBuilder getMetricSpecOrBuilder(int i2) {
            return this.metricSpec_.get(i2);
        }

        public List<? extends AggregateMetricSpecOrBuilder> getMetricSpecOrBuilderList() {
            return this.metricSpec_;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.AggregateDataRequestOrBuilder
        public long getSliceDurationMillis() {
            return this.sliceDurationMillis_;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.AggregateDataRequestOrBuilder
        public String getSlicePeriod() {
            return this.slicePeriod_;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.AggregateDataRequestOrBuilder
        public ByteString getSlicePeriodBytes() {
            return ByteString.copyFromUtf8(this.slicePeriod_);
        }

        @Override // androidx.health.platform.client.proto.RequestProto.AggregateDataRequestOrBuilder
        public TimeProto.TimeSpec getTimeSpec() {
            TimeProto.TimeSpec timeSpec = this.timeSpec_;
            return timeSpec == null ? TimeProto.TimeSpec.getDefaultInstance() : timeSpec;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.AggregateDataRequestOrBuilder
        public boolean hasSliceDurationMillis() {
            return (this.bitField0_ & 2) != 0;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.AggregateDataRequestOrBuilder
        public boolean hasSlicePeriod() {
            return (this.bitField0_ & 4) != 0;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.AggregateDataRequestOrBuilder
        public boolean hasTimeSpec() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override // androidx.health.platform.client.proto.GeneratedMessageLite
        protected final Object q(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            AnonymousClass1 anonymousClass1 = null;
            switch (AnonymousClass1.f4477a[methodToInvoke.ordinal()]) {
                case 1:
                    return new AggregateDataRequest();
                case 2:
                    return new Builder(anonymousClass1);
                case 3:
                    return GeneratedMessageLite.D(DEFAULT_INSTANCE, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0002\u0000\u0001ဉ\u0000\u0002\u001b\u0003\u001b\u0004ဂ\u0001\u0005ဈ\u0002", new Object[]{"bitField0_", "timeSpec_", "metricSpec_", AggregateMetricSpec.class, "dataOrigin_", DataProto.DataOrigin.class, "sliceDurationMillis_", "slicePeriod_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<AggregateDataRequest> defaultInstanceBasedParser = PARSER;
                    if (defaultInstanceBasedParser == null) {
                        synchronized (AggregateDataRequest.class) {
                            try {
                                defaultInstanceBasedParser = PARSER;
                                if (defaultInstanceBasedParser == null) {
                                    defaultInstanceBasedParser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                    PARSER = defaultInstanceBasedParser;
                                }
                            } finally {
                            }
                        }
                    }
                    return defaultInstanceBasedParser;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public static Builder newBuilder(AggregateDataRequest aggregateDataRequest) {
            return (Builder) DEFAULT_INSTANCE.n(aggregateDataRequest);
        }

        public static AggregateDataRequest parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (AggregateDataRequest) GeneratedMessageLite.G(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static AggregateDataRequest parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (AggregateDataRequest) GeneratedMessageLite.O(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static AggregateDataRequest parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (AggregateDataRequest) GeneratedMessageLite.H(DEFAULT_INSTANCE, byteString);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addDataOrigin(int i2, DataProto.DataOrigin dataOrigin) {
            dataOrigin.getClass();
            ensureDataOriginIsMutable();
            this.dataOrigin_.add(i2, dataOrigin);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addMetricSpec(int i2, AggregateMetricSpec aggregateMetricSpec) {
            aggregateMetricSpec.getClass();
            ensureMetricSpecIsMutable();
            this.metricSpec_.add(i2, aggregateMetricSpec);
        }

        public static AggregateDataRequest parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (AggregateDataRequest) GeneratedMessageLite.I(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static AggregateDataRequest parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (AggregateDataRequest) GeneratedMessageLite.P(DEFAULT_INSTANCE, bArr);
        }

        public static AggregateDataRequest parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (AggregateDataRequest) GeneratedMessageLite.Q(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static AggregateDataRequest parseFrom(InputStream inputStream) throws IOException {
            return (AggregateDataRequest) GeneratedMessageLite.L(DEFAULT_INSTANCE, inputStream);
        }

        public static AggregateDataRequest parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (AggregateDataRequest) GeneratedMessageLite.M(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static AggregateDataRequest parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (AggregateDataRequest) GeneratedMessageLite.J(DEFAULT_INSTANCE, codedInputStream);
        }

        public static AggregateDataRequest parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (AggregateDataRequest) GeneratedMessageLite.K(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }
    }

    public interface AggregateDataRequestOrBuilder extends MessageLiteOrBuilder {
        DataProto.DataOrigin getDataOrigin(int i2);

        int getDataOriginCount();

        List<DataProto.DataOrigin> getDataOriginList();

        AggregateMetricSpec getMetricSpec(int i2);

        int getMetricSpecCount();

        List<AggregateMetricSpec> getMetricSpecList();

        long getSliceDurationMillis();

        String getSlicePeriod();

        ByteString getSlicePeriodBytes();

        TimeProto.TimeSpec getTimeSpec();

        boolean hasSliceDurationMillis();

        boolean hasSlicePeriod();

        boolean hasTimeSpec();
    }

    public static final class AggregateMetricSpec extends GeneratedMessageLite<AggregateMetricSpec, Builder> implements AggregateMetricSpecOrBuilder {
        public static final int AGGREGATION_TYPE_FIELD_NUMBER = 2;
        public static final int DATA_TYPE_NAME_FIELD_NUMBER = 1;
        private static final AggregateMetricSpec DEFAULT_INSTANCE;
        public static final int FIELD_NAME_FIELD_NUMBER = 3;
        private static volatile Parser<AggregateMetricSpec> PARSER;
        private int bitField0_;
        private String dataTypeName_ = "";
        private String aggregationType_ = "";
        private String fieldName_ = "";

        public static final class Builder extends GeneratedMessageLite.Builder<AggregateMetricSpec, Builder> implements AggregateMetricSpecOrBuilder {
            /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
                this();
            }

            public Builder clearAggregationType() {
                d();
                ((AggregateMetricSpec) this.f4444a).clearAggregationType();
                return this;
            }

            public Builder clearDataTypeName() {
                d();
                ((AggregateMetricSpec) this.f4444a).clearDataTypeName();
                return this;
            }

            public Builder clearFieldName() {
                d();
                ((AggregateMetricSpec) this.f4444a).clearFieldName();
                return this;
            }

            @Override // androidx.health.platform.client.proto.RequestProto.AggregateMetricSpecOrBuilder
            public String getAggregationType() {
                return ((AggregateMetricSpec) this.f4444a).getAggregationType();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.AggregateMetricSpecOrBuilder
            public ByteString getAggregationTypeBytes() {
                return ((AggregateMetricSpec) this.f4444a).getAggregationTypeBytes();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.AggregateMetricSpecOrBuilder
            public String getDataTypeName() {
                return ((AggregateMetricSpec) this.f4444a).getDataTypeName();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.AggregateMetricSpecOrBuilder
            public ByteString getDataTypeNameBytes() {
                return ((AggregateMetricSpec) this.f4444a).getDataTypeNameBytes();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.AggregateMetricSpecOrBuilder
            public String getFieldName() {
                return ((AggregateMetricSpec) this.f4444a).getFieldName();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.AggregateMetricSpecOrBuilder
            public ByteString getFieldNameBytes() {
                return ((AggregateMetricSpec) this.f4444a).getFieldNameBytes();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.AggregateMetricSpecOrBuilder
            public boolean hasAggregationType() {
                return ((AggregateMetricSpec) this.f4444a).hasAggregationType();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.AggregateMetricSpecOrBuilder
            public boolean hasDataTypeName() {
                return ((AggregateMetricSpec) this.f4444a).hasDataTypeName();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.AggregateMetricSpecOrBuilder
            public boolean hasFieldName() {
                return ((AggregateMetricSpec) this.f4444a).hasFieldName();
            }

            public Builder setAggregationType(String str) {
                d();
                ((AggregateMetricSpec) this.f4444a).setAggregationType(str);
                return this;
            }

            public Builder setAggregationTypeBytes(ByteString byteString) {
                d();
                ((AggregateMetricSpec) this.f4444a).setAggregationTypeBytes(byteString);
                return this;
            }

            public Builder setDataTypeName(String str) {
                d();
                ((AggregateMetricSpec) this.f4444a).setDataTypeName(str);
                return this;
            }

            public Builder setDataTypeNameBytes(ByteString byteString) {
                d();
                ((AggregateMetricSpec) this.f4444a).setDataTypeNameBytes(byteString);
                return this;
            }

            public Builder setFieldName(String str) {
                d();
                ((AggregateMetricSpec) this.f4444a).setFieldName(str);
                return this;
            }

            public Builder setFieldNameBytes(ByteString byteString) {
                d();
                ((AggregateMetricSpec) this.f4444a).setFieldNameBytes(byteString);
                return this;
            }

            private Builder() {
                super(AggregateMetricSpec.DEFAULT_INSTANCE);
            }
        }

        static {
            AggregateMetricSpec aggregateMetricSpec = new AggregateMetricSpec();
            DEFAULT_INSTANCE = aggregateMetricSpec;
            GeneratedMessageLite.T(AggregateMetricSpec.class, aggregateMetricSpec);
        }

        private AggregateMetricSpec() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearAggregationType() {
            this.bitField0_ &= -3;
            this.aggregationType_ = getDefaultInstance().getAggregationType();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearDataTypeName() {
            this.bitField0_ &= -2;
            this.dataTypeName_ = getDefaultInstance().getDataTypeName();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearFieldName() {
            this.bitField0_ &= -5;
            this.fieldName_ = getDefaultInstance().getFieldName();
        }

        public static AggregateMetricSpec getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.m();
        }

        public static AggregateMetricSpec parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (AggregateMetricSpec) GeneratedMessageLite.F(DEFAULT_INSTANCE, inputStream);
        }

        public static AggregateMetricSpec parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (AggregateMetricSpec) GeneratedMessageLite.N(DEFAULT_INSTANCE, byteBuffer);
        }

        public static Parser<AggregateMetricSpec> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setAggregationType(String str) {
            str.getClass();
            this.bitField0_ |= 2;
            this.aggregationType_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setAggregationTypeBytes(ByteString byteString) {
            this.aggregationType_ = byteString.toStringUtf8();
            this.bitField0_ |= 2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setDataTypeName(String str) {
            str.getClass();
            this.bitField0_ |= 1;
            this.dataTypeName_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setDataTypeNameBytes(ByteString byteString) {
            this.dataTypeName_ = byteString.toStringUtf8();
            this.bitField0_ |= 1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setFieldName(String str) {
            str.getClass();
            this.bitField0_ |= 4;
            this.fieldName_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setFieldNameBytes(ByteString byteString) {
            this.fieldName_ = byteString.toStringUtf8();
            this.bitField0_ |= 4;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.AggregateMetricSpecOrBuilder
        public String getAggregationType() {
            return this.aggregationType_;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.AggregateMetricSpecOrBuilder
        public ByteString getAggregationTypeBytes() {
            return ByteString.copyFromUtf8(this.aggregationType_);
        }

        @Override // androidx.health.platform.client.proto.RequestProto.AggregateMetricSpecOrBuilder
        public String getDataTypeName() {
            return this.dataTypeName_;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.AggregateMetricSpecOrBuilder
        public ByteString getDataTypeNameBytes() {
            return ByteString.copyFromUtf8(this.dataTypeName_);
        }

        @Override // androidx.health.platform.client.proto.RequestProto.AggregateMetricSpecOrBuilder
        public String getFieldName() {
            return this.fieldName_;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.AggregateMetricSpecOrBuilder
        public ByteString getFieldNameBytes() {
            return ByteString.copyFromUtf8(this.fieldName_);
        }

        @Override // androidx.health.platform.client.proto.RequestProto.AggregateMetricSpecOrBuilder
        public boolean hasAggregationType() {
            return (this.bitField0_ & 2) != 0;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.AggregateMetricSpecOrBuilder
        public boolean hasDataTypeName() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.AggregateMetricSpecOrBuilder
        public boolean hasFieldName() {
            return (this.bitField0_ & 4) != 0;
        }

        @Override // androidx.health.platform.client.proto.GeneratedMessageLite
        protected final Object q(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            AnonymousClass1 anonymousClass1 = null;
            switch (AnonymousClass1.f4477a[methodToInvoke.ordinal()]) {
                case 1:
                    return new AggregateMetricSpec();
                case 2:
                    return new Builder(anonymousClass1);
                case 3:
                    return GeneratedMessageLite.D(DEFAULT_INSTANCE, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဈ\u0000\u0002ဈ\u0001\u0003ဈ\u0002", new Object[]{"bitField0_", "dataTypeName_", "aggregationType_", "fieldName_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<AggregateMetricSpec> defaultInstanceBasedParser = PARSER;
                    if (defaultInstanceBasedParser == null) {
                        synchronized (AggregateMetricSpec.class) {
                            try {
                                defaultInstanceBasedParser = PARSER;
                                if (defaultInstanceBasedParser == null) {
                                    defaultInstanceBasedParser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                    PARSER = defaultInstanceBasedParser;
                                }
                            } finally {
                            }
                        }
                    }
                    return defaultInstanceBasedParser;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public static Builder newBuilder(AggregateMetricSpec aggregateMetricSpec) {
            return (Builder) DEFAULT_INSTANCE.n(aggregateMetricSpec);
        }

        public static AggregateMetricSpec parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (AggregateMetricSpec) GeneratedMessageLite.G(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static AggregateMetricSpec parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (AggregateMetricSpec) GeneratedMessageLite.O(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static AggregateMetricSpec parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (AggregateMetricSpec) GeneratedMessageLite.H(DEFAULT_INSTANCE, byteString);
        }

        public static AggregateMetricSpec parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (AggregateMetricSpec) GeneratedMessageLite.I(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static AggregateMetricSpec parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (AggregateMetricSpec) GeneratedMessageLite.P(DEFAULT_INSTANCE, bArr);
        }

        public static AggregateMetricSpec parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (AggregateMetricSpec) GeneratedMessageLite.Q(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static AggregateMetricSpec parseFrom(InputStream inputStream) throws IOException {
            return (AggregateMetricSpec) GeneratedMessageLite.L(DEFAULT_INSTANCE, inputStream);
        }

        public static AggregateMetricSpec parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (AggregateMetricSpec) GeneratedMessageLite.M(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static AggregateMetricSpec parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (AggregateMetricSpec) GeneratedMessageLite.J(DEFAULT_INSTANCE, codedInputStream);
        }

        public static AggregateMetricSpec parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (AggregateMetricSpec) GeneratedMessageLite.K(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }
    }

    public interface AggregateMetricSpecOrBuilder extends MessageLiteOrBuilder {
        String getAggregationType();

        ByteString getAggregationTypeBytes();

        String getDataTypeName();

        ByteString getDataTypeNameBytes();

        String getFieldName();

        ByteString getFieldNameBytes();

        boolean hasAggregationType();

        boolean hasDataTypeName();

        boolean hasFieldName();
    }

    public static final class DataTypeIdPair extends GeneratedMessageLite<DataTypeIdPair, Builder> implements DataTypeIdPairOrBuilder {
        public static final int DATA_TYPE_FIELD_NUMBER = 1;
        private static final DataTypeIdPair DEFAULT_INSTANCE;
        public static final int ID_FIELD_NUMBER = 2;
        private static volatile Parser<DataTypeIdPair> PARSER;
        private int bitField0_;
        private DataProto.DataType dataType_;
        private String id_ = "";

        public static final class Builder extends GeneratedMessageLite.Builder<DataTypeIdPair, Builder> implements DataTypeIdPairOrBuilder {
            /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
                this();
            }

            public Builder clearDataType() {
                d();
                ((DataTypeIdPair) this.f4444a).clearDataType();
                return this;
            }

            public Builder clearId() {
                d();
                ((DataTypeIdPair) this.f4444a).clearId();
                return this;
            }

            @Override // androidx.health.platform.client.proto.RequestProto.DataTypeIdPairOrBuilder
            public DataProto.DataType getDataType() {
                return ((DataTypeIdPair) this.f4444a).getDataType();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.DataTypeIdPairOrBuilder
            public String getId() {
                return ((DataTypeIdPair) this.f4444a).getId();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.DataTypeIdPairOrBuilder
            public ByteString getIdBytes() {
                return ((DataTypeIdPair) this.f4444a).getIdBytes();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.DataTypeIdPairOrBuilder
            public boolean hasDataType() {
                return ((DataTypeIdPair) this.f4444a).hasDataType();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.DataTypeIdPairOrBuilder
            public boolean hasId() {
                return ((DataTypeIdPair) this.f4444a).hasId();
            }

            public Builder mergeDataType(DataProto.DataType dataType) {
                d();
                ((DataTypeIdPair) this.f4444a).mergeDataType(dataType);
                return this;
            }

            public Builder setDataType(DataProto.DataType dataType) {
                d();
                ((DataTypeIdPair) this.f4444a).setDataType(dataType);
                return this;
            }

            public Builder setId(String str) {
                d();
                ((DataTypeIdPair) this.f4444a).setId(str);
                return this;
            }

            public Builder setIdBytes(ByteString byteString) {
                d();
                ((DataTypeIdPair) this.f4444a).setIdBytes(byteString);
                return this;
            }

            private Builder() {
                super(DataTypeIdPair.DEFAULT_INSTANCE);
            }

            public Builder setDataType(DataProto.DataType.Builder builder) {
                d();
                ((DataTypeIdPair) this.f4444a).setDataType(builder.build());
                return this;
            }
        }

        static {
            DataTypeIdPair dataTypeIdPair = new DataTypeIdPair();
            DEFAULT_INSTANCE = dataTypeIdPair;
            GeneratedMessageLite.T(DataTypeIdPair.class, dataTypeIdPair);
        }

        private DataTypeIdPair() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearDataType() {
            this.dataType_ = null;
            this.bitField0_ &= -2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearId() {
            this.bitField0_ &= -3;
            this.id_ = getDefaultInstance().getId();
        }

        public static DataTypeIdPair getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mergeDataType(DataProto.DataType dataType) {
            dataType.getClass();
            DataProto.DataType dataType2 = this.dataType_;
            if (dataType2 == null || dataType2 == DataProto.DataType.getDefaultInstance()) {
                this.dataType_ = dataType;
            } else {
                this.dataType_ = DataProto.DataType.newBuilder(this.dataType_).mergeFrom((DataProto.DataType.Builder) dataType).buildPartial();
            }
            this.bitField0_ |= 1;
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.m();
        }

        public static DataTypeIdPair parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (DataTypeIdPair) GeneratedMessageLite.F(DEFAULT_INSTANCE, inputStream);
        }

        public static DataTypeIdPair parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (DataTypeIdPair) GeneratedMessageLite.N(DEFAULT_INSTANCE, byteBuffer);
        }

        public static Parser<DataTypeIdPair> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setDataType(DataProto.DataType dataType) {
            dataType.getClass();
            this.dataType_ = dataType;
            this.bitField0_ |= 1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setId(String str) {
            str.getClass();
            this.bitField0_ |= 2;
            this.id_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setIdBytes(ByteString byteString) {
            this.id_ = byteString.toStringUtf8();
            this.bitField0_ |= 2;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.DataTypeIdPairOrBuilder
        public DataProto.DataType getDataType() {
            DataProto.DataType dataType = this.dataType_;
            return dataType == null ? DataProto.DataType.getDefaultInstance() : dataType;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.DataTypeIdPairOrBuilder
        public String getId() {
            return this.id_;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.DataTypeIdPairOrBuilder
        public ByteString getIdBytes() {
            return ByteString.copyFromUtf8(this.id_);
        }

        @Override // androidx.health.platform.client.proto.RequestProto.DataTypeIdPairOrBuilder
        public boolean hasDataType() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.DataTypeIdPairOrBuilder
        public boolean hasId() {
            return (this.bitField0_ & 2) != 0;
        }

        @Override // androidx.health.platform.client.proto.GeneratedMessageLite
        protected final Object q(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            AnonymousClass1 anonymousClass1 = null;
            switch (AnonymousClass1.f4477a[methodToInvoke.ordinal()]) {
                case 1:
                    return new DataTypeIdPair();
                case 2:
                    return new Builder(anonymousClass1);
                case 3:
                    return GeneratedMessageLite.D(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဈ\u0001", new Object[]{"bitField0_", "dataType_", "id_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<DataTypeIdPair> defaultInstanceBasedParser = PARSER;
                    if (defaultInstanceBasedParser == null) {
                        synchronized (DataTypeIdPair.class) {
                            try {
                                defaultInstanceBasedParser = PARSER;
                                if (defaultInstanceBasedParser == null) {
                                    defaultInstanceBasedParser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                    PARSER = defaultInstanceBasedParser;
                                }
                            } finally {
                            }
                        }
                    }
                    return defaultInstanceBasedParser;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public static Builder newBuilder(DataTypeIdPair dataTypeIdPair) {
            return (Builder) DEFAULT_INSTANCE.n(dataTypeIdPair);
        }

        public static DataTypeIdPair parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (DataTypeIdPair) GeneratedMessageLite.G(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static DataTypeIdPair parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (DataTypeIdPair) GeneratedMessageLite.O(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static DataTypeIdPair parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (DataTypeIdPair) GeneratedMessageLite.H(DEFAULT_INSTANCE, byteString);
        }

        public static DataTypeIdPair parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (DataTypeIdPair) GeneratedMessageLite.I(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static DataTypeIdPair parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (DataTypeIdPair) GeneratedMessageLite.P(DEFAULT_INSTANCE, bArr);
        }

        public static DataTypeIdPair parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (DataTypeIdPair) GeneratedMessageLite.Q(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static DataTypeIdPair parseFrom(InputStream inputStream) throws IOException {
            return (DataTypeIdPair) GeneratedMessageLite.L(DEFAULT_INSTANCE, inputStream);
        }

        public static DataTypeIdPair parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (DataTypeIdPair) GeneratedMessageLite.M(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static DataTypeIdPair parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (DataTypeIdPair) GeneratedMessageLite.J(DEFAULT_INSTANCE, codedInputStream);
        }

        public static DataTypeIdPair parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (DataTypeIdPair) GeneratedMessageLite.K(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }
    }

    public interface DataTypeIdPairOrBuilder extends MessageLiteOrBuilder {
        DataProto.DataType getDataType();

        String getId();

        ByteString getIdBytes();

        boolean hasDataType();

        boolean hasId();
    }

    public static final class DeleteDataRangeRequest extends GeneratedMessageLite<DeleteDataRangeRequest, Builder> implements DeleteDataRangeRequestOrBuilder {
        public static final int DATA_TYPE_FIELD_NUMBER = 2;
        private static final DeleteDataRangeRequest DEFAULT_INSTANCE;
        private static volatile Parser<DeleteDataRangeRequest> PARSER = null;
        public static final int TIME_SPEC_FIELD_NUMBER = 1;
        private int bitField0_;
        private Internal.ProtobufList<DataProto.DataType> dataType_ = GeneratedMessageLite.r();
        private TimeProto.TimeSpec timeSpec_;

        public static final class Builder extends GeneratedMessageLite.Builder<DeleteDataRangeRequest, Builder> implements DeleteDataRangeRequestOrBuilder {
            /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
                this();
            }

            public Builder addAllDataType(Iterable<? extends DataProto.DataType> iterable) {
                d();
                ((DeleteDataRangeRequest) this.f4444a).addAllDataType(iterable);
                return this;
            }

            public Builder addDataType(DataProto.DataType dataType) {
                d();
                ((DeleteDataRangeRequest) this.f4444a).addDataType(dataType);
                return this;
            }

            public Builder clearDataType() {
                d();
                ((DeleteDataRangeRequest) this.f4444a).clearDataType();
                return this;
            }

            public Builder clearTimeSpec() {
                d();
                ((DeleteDataRangeRequest) this.f4444a).clearTimeSpec();
                return this;
            }

            @Override // androidx.health.platform.client.proto.RequestProto.DeleteDataRangeRequestOrBuilder
            public DataProto.DataType getDataType(int i2) {
                return ((DeleteDataRangeRequest) this.f4444a).getDataType(i2);
            }

            @Override // androidx.health.platform.client.proto.RequestProto.DeleteDataRangeRequestOrBuilder
            public int getDataTypeCount() {
                return ((DeleteDataRangeRequest) this.f4444a).getDataTypeCount();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.DeleteDataRangeRequestOrBuilder
            public List<DataProto.DataType> getDataTypeList() {
                return Collections.unmodifiableList(((DeleteDataRangeRequest) this.f4444a).getDataTypeList());
            }

            @Override // androidx.health.platform.client.proto.RequestProto.DeleteDataRangeRequestOrBuilder
            public TimeProto.TimeSpec getTimeSpec() {
                return ((DeleteDataRangeRequest) this.f4444a).getTimeSpec();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.DeleteDataRangeRequestOrBuilder
            public boolean hasTimeSpec() {
                return ((DeleteDataRangeRequest) this.f4444a).hasTimeSpec();
            }

            public Builder mergeTimeSpec(TimeProto.TimeSpec timeSpec) {
                d();
                ((DeleteDataRangeRequest) this.f4444a).mergeTimeSpec(timeSpec);
                return this;
            }

            public Builder removeDataType(int i2) {
                d();
                ((DeleteDataRangeRequest) this.f4444a).removeDataType(i2);
                return this;
            }

            public Builder setDataType(int i2, DataProto.DataType dataType) {
                d();
                ((DeleteDataRangeRequest) this.f4444a).setDataType(i2, dataType);
                return this;
            }

            public Builder setTimeSpec(TimeProto.TimeSpec timeSpec) {
                d();
                ((DeleteDataRangeRequest) this.f4444a).setTimeSpec(timeSpec);
                return this;
            }

            private Builder() {
                super(DeleteDataRangeRequest.DEFAULT_INSTANCE);
            }

            public Builder addDataType(int i2, DataProto.DataType dataType) {
                d();
                ((DeleteDataRangeRequest) this.f4444a).addDataType(i2, dataType);
                return this;
            }

            public Builder setDataType(int i2, DataProto.DataType.Builder builder) {
                d();
                ((DeleteDataRangeRequest) this.f4444a).setDataType(i2, builder.build());
                return this;
            }

            public Builder setTimeSpec(TimeProto.TimeSpec.Builder builder) {
                d();
                ((DeleteDataRangeRequest) this.f4444a).setTimeSpec(builder.build());
                return this;
            }

            public Builder addDataType(DataProto.DataType.Builder builder) {
                d();
                ((DeleteDataRangeRequest) this.f4444a).addDataType(builder.build());
                return this;
            }

            public Builder addDataType(int i2, DataProto.DataType.Builder builder) {
                d();
                ((DeleteDataRangeRequest) this.f4444a).addDataType(i2, builder.build());
                return this;
            }
        }

        static {
            DeleteDataRangeRequest deleteDataRangeRequest = new DeleteDataRangeRequest();
            DEFAULT_INSTANCE = deleteDataRangeRequest;
            GeneratedMessageLite.T(DeleteDataRangeRequest.class, deleteDataRangeRequest);
        }

        private DeleteDataRangeRequest() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addAllDataType(Iterable<? extends DataProto.DataType> iterable) {
            ensureDataTypeIsMutable();
            AbstractMessageLite.a(iterable, this.dataType_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addDataType(DataProto.DataType dataType) {
            dataType.getClass();
            ensureDataTypeIsMutable();
            this.dataType_.add(dataType);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearDataType() {
            this.dataType_ = GeneratedMessageLite.r();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearTimeSpec() {
            this.timeSpec_ = null;
            this.bitField0_ &= -2;
        }

        private void ensureDataTypeIsMutable() {
            Internal.ProtobufList<DataProto.DataType> protobufList = this.dataType_;
            if (protobufList.isModifiable()) {
                return;
            }
            this.dataType_ = GeneratedMessageLite.C(protobufList);
        }

        public static DeleteDataRangeRequest getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mergeTimeSpec(TimeProto.TimeSpec timeSpec) {
            timeSpec.getClass();
            TimeProto.TimeSpec timeSpec2 = this.timeSpec_;
            if (timeSpec2 == null || timeSpec2 == TimeProto.TimeSpec.getDefaultInstance()) {
                this.timeSpec_ = timeSpec;
            } else {
                this.timeSpec_ = TimeProto.TimeSpec.newBuilder(this.timeSpec_).mergeFrom((TimeProto.TimeSpec.Builder) timeSpec).buildPartial();
            }
            this.bitField0_ |= 1;
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.m();
        }

        public static DeleteDataRangeRequest parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (DeleteDataRangeRequest) GeneratedMessageLite.F(DEFAULT_INSTANCE, inputStream);
        }

        public static DeleteDataRangeRequest parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (DeleteDataRangeRequest) GeneratedMessageLite.N(DEFAULT_INSTANCE, byteBuffer);
        }

        public static Parser<DeleteDataRangeRequest> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void removeDataType(int i2) {
            ensureDataTypeIsMutable();
            this.dataType_.remove(i2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setDataType(int i2, DataProto.DataType dataType) {
            dataType.getClass();
            ensureDataTypeIsMutable();
            this.dataType_.set(i2, dataType);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setTimeSpec(TimeProto.TimeSpec timeSpec) {
            timeSpec.getClass();
            this.timeSpec_ = timeSpec;
            this.bitField0_ |= 1;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.DeleteDataRangeRequestOrBuilder
        public DataProto.DataType getDataType(int i2) {
            return this.dataType_.get(i2);
        }

        @Override // androidx.health.platform.client.proto.RequestProto.DeleteDataRangeRequestOrBuilder
        public int getDataTypeCount() {
            return this.dataType_.size();
        }

        @Override // androidx.health.platform.client.proto.RequestProto.DeleteDataRangeRequestOrBuilder
        public List<DataProto.DataType> getDataTypeList() {
            return this.dataType_;
        }

        public DataProto.DataTypeOrBuilder getDataTypeOrBuilder(int i2) {
            return this.dataType_.get(i2);
        }

        public List<? extends DataProto.DataTypeOrBuilder> getDataTypeOrBuilderList() {
            return this.dataType_;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.DeleteDataRangeRequestOrBuilder
        public TimeProto.TimeSpec getTimeSpec() {
            TimeProto.TimeSpec timeSpec = this.timeSpec_;
            return timeSpec == null ? TimeProto.TimeSpec.getDefaultInstance() : timeSpec;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.DeleteDataRangeRequestOrBuilder
        public boolean hasTimeSpec() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override // androidx.health.platform.client.proto.GeneratedMessageLite
        protected final Object q(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            AnonymousClass1 anonymousClass1 = null;
            switch (AnonymousClass1.f4477a[methodToInvoke.ordinal()]) {
                case 1:
                    return new DeleteDataRangeRequest();
                case 2:
                    return new Builder(anonymousClass1);
                case 3:
                    return GeneratedMessageLite.D(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0001\u0000\u0001ဉ\u0000\u0002\u001b", new Object[]{"bitField0_", "timeSpec_", "dataType_", DataProto.DataType.class});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<DeleteDataRangeRequest> defaultInstanceBasedParser = PARSER;
                    if (defaultInstanceBasedParser == null) {
                        synchronized (DeleteDataRangeRequest.class) {
                            try {
                                defaultInstanceBasedParser = PARSER;
                                if (defaultInstanceBasedParser == null) {
                                    defaultInstanceBasedParser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                    PARSER = defaultInstanceBasedParser;
                                }
                            } finally {
                            }
                        }
                    }
                    return defaultInstanceBasedParser;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public static Builder newBuilder(DeleteDataRangeRequest deleteDataRangeRequest) {
            return (Builder) DEFAULT_INSTANCE.n(deleteDataRangeRequest);
        }

        public static DeleteDataRangeRequest parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (DeleteDataRangeRequest) GeneratedMessageLite.G(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static DeleteDataRangeRequest parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (DeleteDataRangeRequest) GeneratedMessageLite.O(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static DeleteDataRangeRequest parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (DeleteDataRangeRequest) GeneratedMessageLite.H(DEFAULT_INSTANCE, byteString);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addDataType(int i2, DataProto.DataType dataType) {
            dataType.getClass();
            ensureDataTypeIsMutable();
            this.dataType_.add(i2, dataType);
        }

        public static DeleteDataRangeRequest parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (DeleteDataRangeRequest) GeneratedMessageLite.I(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static DeleteDataRangeRequest parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (DeleteDataRangeRequest) GeneratedMessageLite.P(DEFAULT_INSTANCE, bArr);
        }

        public static DeleteDataRangeRequest parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (DeleteDataRangeRequest) GeneratedMessageLite.Q(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static DeleteDataRangeRequest parseFrom(InputStream inputStream) throws IOException {
            return (DeleteDataRangeRequest) GeneratedMessageLite.L(DEFAULT_INSTANCE, inputStream);
        }

        public static DeleteDataRangeRequest parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (DeleteDataRangeRequest) GeneratedMessageLite.M(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static DeleteDataRangeRequest parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (DeleteDataRangeRequest) GeneratedMessageLite.J(DEFAULT_INSTANCE, codedInputStream);
        }

        public static DeleteDataRangeRequest parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (DeleteDataRangeRequest) GeneratedMessageLite.K(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }
    }

    public interface DeleteDataRangeRequestOrBuilder extends MessageLiteOrBuilder {
        DataProto.DataType getDataType(int i2);

        int getDataTypeCount();

        List<DataProto.DataType> getDataTypeList();

        TimeProto.TimeSpec getTimeSpec();

        boolean hasTimeSpec();
    }

    public static final class DeleteDataRequest extends GeneratedMessageLite<DeleteDataRequest, Builder> implements DeleteDataRequestOrBuilder {
        public static final int CLIENT_IDS_FIELD_NUMBER = 2;
        private static final DeleteDataRequest DEFAULT_INSTANCE;
        private static volatile Parser<DeleteDataRequest> PARSER = null;
        public static final int UIDS_FIELD_NUMBER = 1;
        private Internal.ProtobufList<DataTypeIdPair> uids_ = GeneratedMessageLite.r();
        private Internal.ProtobufList<DataTypeIdPair> clientIds_ = GeneratedMessageLite.r();

        public static final class Builder extends GeneratedMessageLite.Builder<DeleteDataRequest, Builder> implements DeleteDataRequestOrBuilder {
            /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
                this();
            }

            public Builder addAllClientIds(Iterable<? extends DataTypeIdPair> iterable) {
                d();
                ((DeleteDataRequest) this.f4444a).addAllClientIds(iterable);
                return this;
            }

            public Builder addAllUids(Iterable<? extends DataTypeIdPair> iterable) {
                d();
                ((DeleteDataRequest) this.f4444a).addAllUids(iterable);
                return this;
            }

            public Builder addClientIds(DataTypeIdPair dataTypeIdPair) {
                d();
                ((DeleteDataRequest) this.f4444a).addClientIds(dataTypeIdPair);
                return this;
            }

            public Builder addUids(DataTypeIdPair dataTypeIdPair) {
                d();
                ((DeleteDataRequest) this.f4444a).addUids(dataTypeIdPair);
                return this;
            }

            public Builder clearClientIds() {
                d();
                ((DeleteDataRequest) this.f4444a).clearClientIds();
                return this;
            }

            public Builder clearUids() {
                d();
                ((DeleteDataRequest) this.f4444a).clearUids();
                return this;
            }

            @Override // androidx.health.platform.client.proto.RequestProto.DeleteDataRequestOrBuilder
            public DataTypeIdPair getClientIds(int i2) {
                return ((DeleteDataRequest) this.f4444a).getClientIds(i2);
            }

            @Override // androidx.health.platform.client.proto.RequestProto.DeleteDataRequestOrBuilder
            public int getClientIdsCount() {
                return ((DeleteDataRequest) this.f4444a).getClientIdsCount();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.DeleteDataRequestOrBuilder
            public List<DataTypeIdPair> getClientIdsList() {
                return Collections.unmodifiableList(((DeleteDataRequest) this.f4444a).getClientIdsList());
            }

            @Override // androidx.health.platform.client.proto.RequestProto.DeleteDataRequestOrBuilder
            public DataTypeIdPair getUids(int i2) {
                return ((DeleteDataRequest) this.f4444a).getUids(i2);
            }

            @Override // androidx.health.platform.client.proto.RequestProto.DeleteDataRequestOrBuilder
            public int getUidsCount() {
                return ((DeleteDataRequest) this.f4444a).getUidsCount();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.DeleteDataRequestOrBuilder
            public List<DataTypeIdPair> getUidsList() {
                return Collections.unmodifiableList(((DeleteDataRequest) this.f4444a).getUidsList());
            }

            public Builder removeClientIds(int i2) {
                d();
                ((DeleteDataRequest) this.f4444a).removeClientIds(i2);
                return this;
            }

            public Builder removeUids(int i2) {
                d();
                ((DeleteDataRequest) this.f4444a).removeUids(i2);
                return this;
            }

            public Builder setClientIds(int i2, DataTypeIdPair dataTypeIdPair) {
                d();
                ((DeleteDataRequest) this.f4444a).setClientIds(i2, dataTypeIdPair);
                return this;
            }

            public Builder setUids(int i2, DataTypeIdPair dataTypeIdPair) {
                d();
                ((DeleteDataRequest) this.f4444a).setUids(i2, dataTypeIdPair);
                return this;
            }

            private Builder() {
                super(DeleteDataRequest.DEFAULT_INSTANCE);
            }

            public Builder addClientIds(int i2, DataTypeIdPair dataTypeIdPair) {
                d();
                ((DeleteDataRequest) this.f4444a).addClientIds(i2, dataTypeIdPair);
                return this;
            }

            public Builder addUids(int i2, DataTypeIdPair dataTypeIdPair) {
                d();
                ((DeleteDataRequest) this.f4444a).addUids(i2, dataTypeIdPair);
                return this;
            }

            public Builder setClientIds(int i2, DataTypeIdPair.Builder builder) {
                d();
                ((DeleteDataRequest) this.f4444a).setClientIds(i2, builder.build());
                return this;
            }

            public Builder setUids(int i2, DataTypeIdPair.Builder builder) {
                d();
                ((DeleteDataRequest) this.f4444a).setUids(i2, builder.build());
                return this;
            }

            public Builder addClientIds(DataTypeIdPair.Builder builder) {
                d();
                ((DeleteDataRequest) this.f4444a).addClientIds(builder.build());
                return this;
            }

            public Builder addUids(DataTypeIdPair.Builder builder) {
                d();
                ((DeleteDataRequest) this.f4444a).addUids(builder.build());
                return this;
            }

            public Builder addClientIds(int i2, DataTypeIdPair.Builder builder) {
                d();
                ((DeleteDataRequest) this.f4444a).addClientIds(i2, builder.build());
                return this;
            }

            public Builder addUids(int i2, DataTypeIdPair.Builder builder) {
                d();
                ((DeleteDataRequest) this.f4444a).addUids(i2, builder.build());
                return this;
            }
        }

        static {
            DeleteDataRequest deleteDataRequest = new DeleteDataRequest();
            DEFAULT_INSTANCE = deleteDataRequest;
            GeneratedMessageLite.T(DeleteDataRequest.class, deleteDataRequest);
        }

        private DeleteDataRequest() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addAllClientIds(Iterable<? extends DataTypeIdPair> iterable) {
            ensureClientIdsIsMutable();
            AbstractMessageLite.a(iterable, this.clientIds_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addAllUids(Iterable<? extends DataTypeIdPair> iterable) {
            ensureUidsIsMutable();
            AbstractMessageLite.a(iterable, this.uids_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addClientIds(DataTypeIdPair dataTypeIdPair) {
            dataTypeIdPair.getClass();
            ensureClientIdsIsMutable();
            this.clientIds_.add(dataTypeIdPair);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addUids(DataTypeIdPair dataTypeIdPair) {
            dataTypeIdPair.getClass();
            ensureUidsIsMutable();
            this.uids_.add(dataTypeIdPair);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearClientIds() {
            this.clientIds_ = GeneratedMessageLite.r();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearUids() {
            this.uids_ = GeneratedMessageLite.r();
        }

        private void ensureClientIdsIsMutable() {
            Internal.ProtobufList<DataTypeIdPair> protobufList = this.clientIds_;
            if (protobufList.isModifiable()) {
                return;
            }
            this.clientIds_ = GeneratedMessageLite.C(protobufList);
        }

        private void ensureUidsIsMutable() {
            Internal.ProtobufList<DataTypeIdPair> protobufList = this.uids_;
            if (protobufList.isModifiable()) {
                return;
            }
            this.uids_ = GeneratedMessageLite.C(protobufList);
        }

        public static DeleteDataRequest getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.m();
        }

        public static DeleteDataRequest parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (DeleteDataRequest) GeneratedMessageLite.F(DEFAULT_INSTANCE, inputStream);
        }

        public static DeleteDataRequest parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (DeleteDataRequest) GeneratedMessageLite.N(DEFAULT_INSTANCE, byteBuffer);
        }

        public static Parser<DeleteDataRequest> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void removeClientIds(int i2) {
            ensureClientIdsIsMutable();
            this.clientIds_.remove(i2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void removeUids(int i2) {
            ensureUidsIsMutable();
            this.uids_.remove(i2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setClientIds(int i2, DataTypeIdPair dataTypeIdPair) {
            dataTypeIdPair.getClass();
            ensureClientIdsIsMutable();
            this.clientIds_.set(i2, dataTypeIdPair);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setUids(int i2, DataTypeIdPair dataTypeIdPair) {
            dataTypeIdPair.getClass();
            ensureUidsIsMutable();
            this.uids_.set(i2, dataTypeIdPair);
        }

        @Override // androidx.health.platform.client.proto.RequestProto.DeleteDataRequestOrBuilder
        public DataTypeIdPair getClientIds(int i2) {
            return this.clientIds_.get(i2);
        }

        @Override // androidx.health.platform.client.proto.RequestProto.DeleteDataRequestOrBuilder
        public int getClientIdsCount() {
            return this.clientIds_.size();
        }

        @Override // androidx.health.platform.client.proto.RequestProto.DeleteDataRequestOrBuilder
        public List<DataTypeIdPair> getClientIdsList() {
            return this.clientIds_;
        }

        public DataTypeIdPairOrBuilder getClientIdsOrBuilder(int i2) {
            return this.clientIds_.get(i2);
        }

        public List<? extends DataTypeIdPairOrBuilder> getClientIdsOrBuilderList() {
            return this.clientIds_;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.DeleteDataRequestOrBuilder
        public DataTypeIdPair getUids(int i2) {
            return this.uids_.get(i2);
        }

        @Override // androidx.health.platform.client.proto.RequestProto.DeleteDataRequestOrBuilder
        public int getUidsCount() {
            return this.uids_.size();
        }

        @Override // androidx.health.platform.client.proto.RequestProto.DeleteDataRequestOrBuilder
        public List<DataTypeIdPair> getUidsList() {
            return this.uids_;
        }

        public DataTypeIdPairOrBuilder getUidsOrBuilder(int i2) {
            return this.uids_.get(i2);
        }

        public List<? extends DataTypeIdPairOrBuilder> getUidsOrBuilderList() {
            return this.uids_;
        }

        @Override // androidx.health.platform.client.proto.GeneratedMessageLite
        protected final Object q(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            AnonymousClass1 anonymousClass1 = null;
            switch (AnonymousClass1.f4477a[methodToInvoke.ordinal()]) {
                case 1:
                    return new DeleteDataRequest();
                case 2:
                    return new Builder(anonymousClass1);
                case 3:
                    return GeneratedMessageLite.D(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0002\u0000\u0001\u001b\u0002\u001b", new Object[]{"uids_", DataTypeIdPair.class, "clientIds_", DataTypeIdPair.class});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<DeleteDataRequest> defaultInstanceBasedParser = PARSER;
                    if (defaultInstanceBasedParser == null) {
                        synchronized (DeleteDataRequest.class) {
                            try {
                                defaultInstanceBasedParser = PARSER;
                                if (defaultInstanceBasedParser == null) {
                                    defaultInstanceBasedParser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                    PARSER = defaultInstanceBasedParser;
                                }
                            } finally {
                            }
                        }
                    }
                    return defaultInstanceBasedParser;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public static Builder newBuilder(DeleteDataRequest deleteDataRequest) {
            return (Builder) DEFAULT_INSTANCE.n(deleteDataRequest);
        }

        public static DeleteDataRequest parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (DeleteDataRequest) GeneratedMessageLite.G(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static DeleteDataRequest parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (DeleteDataRequest) GeneratedMessageLite.O(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static DeleteDataRequest parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (DeleteDataRequest) GeneratedMessageLite.H(DEFAULT_INSTANCE, byteString);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addClientIds(int i2, DataTypeIdPair dataTypeIdPair) {
            dataTypeIdPair.getClass();
            ensureClientIdsIsMutable();
            this.clientIds_.add(i2, dataTypeIdPair);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addUids(int i2, DataTypeIdPair dataTypeIdPair) {
            dataTypeIdPair.getClass();
            ensureUidsIsMutable();
            this.uids_.add(i2, dataTypeIdPair);
        }

        public static DeleteDataRequest parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (DeleteDataRequest) GeneratedMessageLite.I(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static DeleteDataRequest parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (DeleteDataRequest) GeneratedMessageLite.P(DEFAULT_INSTANCE, bArr);
        }

        public static DeleteDataRequest parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (DeleteDataRequest) GeneratedMessageLite.Q(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static DeleteDataRequest parseFrom(InputStream inputStream) throws IOException {
            return (DeleteDataRequest) GeneratedMessageLite.L(DEFAULT_INSTANCE, inputStream);
        }

        public static DeleteDataRequest parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (DeleteDataRequest) GeneratedMessageLite.M(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static DeleteDataRequest parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (DeleteDataRequest) GeneratedMessageLite.J(DEFAULT_INSTANCE, codedInputStream);
        }

        public static DeleteDataRequest parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (DeleteDataRequest) GeneratedMessageLite.K(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }
    }

    public interface DeleteDataRequestOrBuilder extends MessageLiteOrBuilder {
        DataTypeIdPair getClientIds(int i2);

        int getClientIdsCount();

        List<DataTypeIdPair> getClientIdsList();

        DataTypeIdPair getUids(int i2);

        int getUidsCount();

        List<DataTypeIdPair> getUidsList();
    }

    public static final class GetChangesRequest extends GeneratedMessageLite<GetChangesRequest, Builder> implements GetChangesRequestOrBuilder {
        public static final int CHANGES_TOKEN_FIELD_NUMBER = 1;
        private static final GetChangesRequest DEFAULT_INSTANCE;
        private static volatile Parser<GetChangesRequest> PARSER;
        private int bitField0_;
        private String changesToken_ = "";

        public static final class Builder extends GeneratedMessageLite.Builder<GetChangesRequest, Builder> implements GetChangesRequestOrBuilder {
            /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
                this();
            }

            public Builder clearChangesToken() {
                d();
                ((GetChangesRequest) this.f4444a).clearChangesToken();
                return this;
            }

            @Override // androidx.health.platform.client.proto.RequestProto.GetChangesRequestOrBuilder
            public String getChangesToken() {
                return ((GetChangesRequest) this.f4444a).getChangesToken();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.GetChangesRequestOrBuilder
            public ByteString getChangesTokenBytes() {
                return ((GetChangesRequest) this.f4444a).getChangesTokenBytes();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.GetChangesRequestOrBuilder
            public boolean hasChangesToken() {
                return ((GetChangesRequest) this.f4444a).hasChangesToken();
            }

            public Builder setChangesToken(String str) {
                d();
                ((GetChangesRequest) this.f4444a).setChangesToken(str);
                return this;
            }

            public Builder setChangesTokenBytes(ByteString byteString) {
                d();
                ((GetChangesRequest) this.f4444a).setChangesTokenBytes(byteString);
                return this;
            }

            private Builder() {
                super(GetChangesRequest.DEFAULT_INSTANCE);
            }
        }

        static {
            GetChangesRequest getChangesRequest = new GetChangesRequest();
            DEFAULT_INSTANCE = getChangesRequest;
            GeneratedMessageLite.T(GetChangesRequest.class, getChangesRequest);
        }

        private GetChangesRequest() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearChangesToken() {
            this.bitField0_ &= -2;
            this.changesToken_ = getDefaultInstance().getChangesToken();
        }

        public static GetChangesRequest getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.m();
        }

        public static GetChangesRequest parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (GetChangesRequest) GeneratedMessageLite.F(DEFAULT_INSTANCE, inputStream);
        }

        public static GetChangesRequest parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (GetChangesRequest) GeneratedMessageLite.N(DEFAULT_INSTANCE, byteBuffer);
        }

        public static Parser<GetChangesRequest> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setChangesToken(String str) {
            str.getClass();
            this.bitField0_ |= 1;
            this.changesToken_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setChangesTokenBytes(ByteString byteString) {
            this.changesToken_ = byteString.toStringUtf8();
            this.bitField0_ |= 1;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.GetChangesRequestOrBuilder
        public String getChangesToken() {
            return this.changesToken_;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.GetChangesRequestOrBuilder
        public ByteString getChangesTokenBytes() {
            return ByteString.copyFromUtf8(this.changesToken_);
        }

        @Override // androidx.health.platform.client.proto.RequestProto.GetChangesRequestOrBuilder
        public boolean hasChangesToken() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override // androidx.health.platform.client.proto.GeneratedMessageLite
        protected final Object q(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            AnonymousClass1 anonymousClass1 = null;
            switch (AnonymousClass1.f4477a[methodToInvoke.ordinal()]) {
                case 1:
                    return new GetChangesRequest();
                case 2:
                    return new Builder(anonymousClass1);
                case 3:
                    return GeneratedMessageLite.D(DEFAULT_INSTANCE, "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001ဈ\u0000", new Object[]{"bitField0_", "changesToken_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<GetChangesRequest> defaultInstanceBasedParser = PARSER;
                    if (defaultInstanceBasedParser == null) {
                        synchronized (GetChangesRequest.class) {
                            try {
                                defaultInstanceBasedParser = PARSER;
                                if (defaultInstanceBasedParser == null) {
                                    defaultInstanceBasedParser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                    PARSER = defaultInstanceBasedParser;
                                }
                            } finally {
                            }
                        }
                    }
                    return defaultInstanceBasedParser;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public static Builder newBuilder(GetChangesRequest getChangesRequest) {
            return (Builder) DEFAULT_INSTANCE.n(getChangesRequest);
        }

        public static GetChangesRequest parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (GetChangesRequest) GeneratedMessageLite.G(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static GetChangesRequest parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (GetChangesRequest) GeneratedMessageLite.O(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static GetChangesRequest parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (GetChangesRequest) GeneratedMessageLite.H(DEFAULT_INSTANCE, byteString);
        }

        public static GetChangesRequest parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (GetChangesRequest) GeneratedMessageLite.I(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static GetChangesRequest parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (GetChangesRequest) GeneratedMessageLite.P(DEFAULT_INSTANCE, bArr);
        }

        public static GetChangesRequest parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (GetChangesRequest) GeneratedMessageLite.Q(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static GetChangesRequest parseFrom(InputStream inputStream) throws IOException {
            return (GetChangesRequest) GeneratedMessageLite.L(DEFAULT_INSTANCE, inputStream);
        }

        public static GetChangesRequest parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (GetChangesRequest) GeneratedMessageLite.M(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static GetChangesRequest parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (GetChangesRequest) GeneratedMessageLite.J(DEFAULT_INSTANCE, codedInputStream);
        }

        public static GetChangesRequest parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (GetChangesRequest) GeneratedMessageLite.K(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }
    }

    public interface GetChangesRequestOrBuilder extends MessageLiteOrBuilder {
        String getChangesToken();

        ByteString getChangesTokenBytes();

        boolean hasChangesToken();
    }

    public static final class GetChangesTokenRequest extends GeneratedMessageLite<GetChangesTokenRequest, Builder> implements GetChangesTokenRequestOrBuilder {
        public static final int DATA_ORIGIN_FILTERS_FIELD_NUMBER = 2;
        public static final int DATA_TYPE_FIELD_NUMBER = 1;
        private static final GetChangesTokenRequest DEFAULT_INSTANCE;
        private static volatile Parser<GetChangesTokenRequest> PARSER;
        private Internal.ProtobufList<DataProto.DataType> dataType_ = GeneratedMessageLite.r();
        private Internal.ProtobufList<DataProto.DataOrigin> dataOriginFilters_ = GeneratedMessageLite.r();

        public static final class Builder extends GeneratedMessageLite.Builder<GetChangesTokenRequest, Builder> implements GetChangesTokenRequestOrBuilder {
            /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
                this();
            }

            public Builder addAllDataOriginFilters(Iterable<? extends DataProto.DataOrigin> iterable) {
                d();
                ((GetChangesTokenRequest) this.f4444a).addAllDataOriginFilters(iterable);
                return this;
            }

            public Builder addAllDataType(Iterable<? extends DataProto.DataType> iterable) {
                d();
                ((GetChangesTokenRequest) this.f4444a).addAllDataType(iterable);
                return this;
            }

            public Builder addDataOriginFilters(DataProto.DataOrigin dataOrigin) {
                d();
                ((GetChangesTokenRequest) this.f4444a).addDataOriginFilters(dataOrigin);
                return this;
            }

            public Builder addDataType(DataProto.DataType dataType) {
                d();
                ((GetChangesTokenRequest) this.f4444a).addDataType(dataType);
                return this;
            }

            public Builder clearDataOriginFilters() {
                d();
                ((GetChangesTokenRequest) this.f4444a).clearDataOriginFilters();
                return this;
            }

            public Builder clearDataType() {
                d();
                ((GetChangesTokenRequest) this.f4444a).clearDataType();
                return this;
            }

            @Override // androidx.health.platform.client.proto.RequestProto.GetChangesTokenRequestOrBuilder
            public DataProto.DataOrigin getDataOriginFilters(int i2) {
                return ((GetChangesTokenRequest) this.f4444a).getDataOriginFilters(i2);
            }

            @Override // androidx.health.platform.client.proto.RequestProto.GetChangesTokenRequestOrBuilder
            public int getDataOriginFiltersCount() {
                return ((GetChangesTokenRequest) this.f4444a).getDataOriginFiltersCount();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.GetChangesTokenRequestOrBuilder
            public List<DataProto.DataOrigin> getDataOriginFiltersList() {
                return Collections.unmodifiableList(((GetChangesTokenRequest) this.f4444a).getDataOriginFiltersList());
            }

            @Override // androidx.health.platform.client.proto.RequestProto.GetChangesTokenRequestOrBuilder
            public DataProto.DataType getDataType(int i2) {
                return ((GetChangesTokenRequest) this.f4444a).getDataType(i2);
            }

            @Override // androidx.health.platform.client.proto.RequestProto.GetChangesTokenRequestOrBuilder
            public int getDataTypeCount() {
                return ((GetChangesTokenRequest) this.f4444a).getDataTypeCount();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.GetChangesTokenRequestOrBuilder
            public List<DataProto.DataType> getDataTypeList() {
                return Collections.unmodifiableList(((GetChangesTokenRequest) this.f4444a).getDataTypeList());
            }

            public Builder removeDataOriginFilters(int i2) {
                d();
                ((GetChangesTokenRequest) this.f4444a).removeDataOriginFilters(i2);
                return this;
            }

            public Builder removeDataType(int i2) {
                d();
                ((GetChangesTokenRequest) this.f4444a).removeDataType(i2);
                return this;
            }

            public Builder setDataOriginFilters(int i2, DataProto.DataOrigin dataOrigin) {
                d();
                ((GetChangesTokenRequest) this.f4444a).setDataOriginFilters(i2, dataOrigin);
                return this;
            }

            public Builder setDataType(int i2, DataProto.DataType dataType) {
                d();
                ((GetChangesTokenRequest) this.f4444a).setDataType(i2, dataType);
                return this;
            }

            private Builder() {
                super(GetChangesTokenRequest.DEFAULT_INSTANCE);
            }

            public Builder addDataOriginFilters(int i2, DataProto.DataOrigin dataOrigin) {
                d();
                ((GetChangesTokenRequest) this.f4444a).addDataOriginFilters(i2, dataOrigin);
                return this;
            }

            public Builder addDataType(int i2, DataProto.DataType dataType) {
                d();
                ((GetChangesTokenRequest) this.f4444a).addDataType(i2, dataType);
                return this;
            }

            public Builder setDataOriginFilters(int i2, DataProto.DataOrigin.Builder builder) {
                d();
                ((GetChangesTokenRequest) this.f4444a).setDataOriginFilters(i2, builder.build());
                return this;
            }

            public Builder setDataType(int i2, DataProto.DataType.Builder builder) {
                d();
                ((GetChangesTokenRequest) this.f4444a).setDataType(i2, builder.build());
                return this;
            }

            public Builder addDataOriginFilters(DataProto.DataOrigin.Builder builder) {
                d();
                ((GetChangesTokenRequest) this.f4444a).addDataOriginFilters(builder.build());
                return this;
            }

            public Builder addDataType(DataProto.DataType.Builder builder) {
                d();
                ((GetChangesTokenRequest) this.f4444a).addDataType(builder.build());
                return this;
            }

            public Builder addDataOriginFilters(int i2, DataProto.DataOrigin.Builder builder) {
                d();
                ((GetChangesTokenRequest) this.f4444a).addDataOriginFilters(i2, builder.build());
                return this;
            }

            public Builder addDataType(int i2, DataProto.DataType.Builder builder) {
                d();
                ((GetChangesTokenRequest) this.f4444a).addDataType(i2, builder.build());
                return this;
            }
        }

        static {
            GetChangesTokenRequest getChangesTokenRequest = new GetChangesTokenRequest();
            DEFAULT_INSTANCE = getChangesTokenRequest;
            GeneratedMessageLite.T(GetChangesTokenRequest.class, getChangesTokenRequest);
        }

        private GetChangesTokenRequest() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addAllDataOriginFilters(Iterable<? extends DataProto.DataOrigin> iterable) {
            ensureDataOriginFiltersIsMutable();
            AbstractMessageLite.a(iterable, this.dataOriginFilters_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addAllDataType(Iterable<? extends DataProto.DataType> iterable) {
            ensureDataTypeIsMutable();
            AbstractMessageLite.a(iterable, this.dataType_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addDataOriginFilters(DataProto.DataOrigin dataOrigin) {
            dataOrigin.getClass();
            ensureDataOriginFiltersIsMutable();
            this.dataOriginFilters_.add(dataOrigin);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addDataType(DataProto.DataType dataType) {
            dataType.getClass();
            ensureDataTypeIsMutable();
            this.dataType_.add(dataType);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearDataOriginFilters() {
            this.dataOriginFilters_ = GeneratedMessageLite.r();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearDataType() {
            this.dataType_ = GeneratedMessageLite.r();
        }

        private void ensureDataOriginFiltersIsMutable() {
            Internal.ProtobufList<DataProto.DataOrigin> protobufList = this.dataOriginFilters_;
            if (protobufList.isModifiable()) {
                return;
            }
            this.dataOriginFilters_ = GeneratedMessageLite.C(protobufList);
        }

        private void ensureDataTypeIsMutable() {
            Internal.ProtobufList<DataProto.DataType> protobufList = this.dataType_;
            if (protobufList.isModifiable()) {
                return;
            }
            this.dataType_ = GeneratedMessageLite.C(protobufList);
        }

        public static GetChangesTokenRequest getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.m();
        }

        public static GetChangesTokenRequest parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (GetChangesTokenRequest) GeneratedMessageLite.F(DEFAULT_INSTANCE, inputStream);
        }

        public static GetChangesTokenRequest parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (GetChangesTokenRequest) GeneratedMessageLite.N(DEFAULT_INSTANCE, byteBuffer);
        }

        public static Parser<GetChangesTokenRequest> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void removeDataOriginFilters(int i2) {
            ensureDataOriginFiltersIsMutable();
            this.dataOriginFilters_.remove(i2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void removeDataType(int i2) {
            ensureDataTypeIsMutable();
            this.dataType_.remove(i2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setDataOriginFilters(int i2, DataProto.DataOrigin dataOrigin) {
            dataOrigin.getClass();
            ensureDataOriginFiltersIsMutable();
            this.dataOriginFilters_.set(i2, dataOrigin);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setDataType(int i2, DataProto.DataType dataType) {
            dataType.getClass();
            ensureDataTypeIsMutable();
            this.dataType_.set(i2, dataType);
        }

        @Override // androidx.health.platform.client.proto.RequestProto.GetChangesTokenRequestOrBuilder
        public DataProto.DataOrigin getDataOriginFilters(int i2) {
            return this.dataOriginFilters_.get(i2);
        }

        @Override // androidx.health.platform.client.proto.RequestProto.GetChangesTokenRequestOrBuilder
        public int getDataOriginFiltersCount() {
            return this.dataOriginFilters_.size();
        }

        @Override // androidx.health.platform.client.proto.RequestProto.GetChangesTokenRequestOrBuilder
        public List<DataProto.DataOrigin> getDataOriginFiltersList() {
            return this.dataOriginFilters_;
        }

        public DataProto.DataOriginOrBuilder getDataOriginFiltersOrBuilder(int i2) {
            return this.dataOriginFilters_.get(i2);
        }

        public List<? extends DataProto.DataOriginOrBuilder> getDataOriginFiltersOrBuilderList() {
            return this.dataOriginFilters_;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.GetChangesTokenRequestOrBuilder
        public DataProto.DataType getDataType(int i2) {
            return this.dataType_.get(i2);
        }

        @Override // androidx.health.platform.client.proto.RequestProto.GetChangesTokenRequestOrBuilder
        public int getDataTypeCount() {
            return this.dataType_.size();
        }

        @Override // androidx.health.platform.client.proto.RequestProto.GetChangesTokenRequestOrBuilder
        public List<DataProto.DataType> getDataTypeList() {
            return this.dataType_;
        }

        public DataProto.DataTypeOrBuilder getDataTypeOrBuilder(int i2) {
            return this.dataType_.get(i2);
        }

        public List<? extends DataProto.DataTypeOrBuilder> getDataTypeOrBuilderList() {
            return this.dataType_;
        }

        @Override // androidx.health.platform.client.proto.GeneratedMessageLite
        protected final Object q(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            AnonymousClass1 anonymousClass1 = null;
            switch (AnonymousClass1.f4477a[methodToInvoke.ordinal()]) {
                case 1:
                    return new GetChangesTokenRequest();
                case 2:
                    return new Builder(anonymousClass1);
                case 3:
                    return GeneratedMessageLite.D(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0002\u0000\u0001\u001b\u0002\u001b", new Object[]{"dataType_", DataProto.DataType.class, "dataOriginFilters_", DataProto.DataOrigin.class});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<GetChangesTokenRequest> defaultInstanceBasedParser = PARSER;
                    if (defaultInstanceBasedParser == null) {
                        synchronized (GetChangesTokenRequest.class) {
                            try {
                                defaultInstanceBasedParser = PARSER;
                                if (defaultInstanceBasedParser == null) {
                                    defaultInstanceBasedParser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                    PARSER = defaultInstanceBasedParser;
                                }
                            } finally {
                            }
                        }
                    }
                    return defaultInstanceBasedParser;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public static Builder newBuilder(GetChangesTokenRequest getChangesTokenRequest) {
            return (Builder) DEFAULT_INSTANCE.n(getChangesTokenRequest);
        }

        public static GetChangesTokenRequest parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (GetChangesTokenRequest) GeneratedMessageLite.G(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static GetChangesTokenRequest parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (GetChangesTokenRequest) GeneratedMessageLite.O(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static GetChangesTokenRequest parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (GetChangesTokenRequest) GeneratedMessageLite.H(DEFAULT_INSTANCE, byteString);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addDataOriginFilters(int i2, DataProto.DataOrigin dataOrigin) {
            dataOrigin.getClass();
            ensureDataOriginFiltersIsMutable();
            this.dataOriginFilters_.add(i2, dataOrigin);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addDataType(int i2, DataProto.DataType dataType) {
            dataType.getClass();
            ensureDataTypeIsMutable();
            this.dataType_.add(i2, dataType);
        }

        public static GetChangesTokenRequest parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (GetChangesTokenRequest) GeneratedMessageLite.I(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static GetChangesTokenRequest parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (GetChangesTokenRequest) GeneratedMessageLite.P(DEFAULT_INSTANCE, bArr);
        }

        public static GetChangesTokenRequest parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (GetChangesTokenRequest) GeneratedMessageLite.Q(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static GetChangesTokenRequest parseFrom(InputStream inputStream) throws IOException {
            return (GetChangesTokenRequest) GeneratedMessageLite.L(DEFAULT_INSTANCE, inputStream);
        }

        public static GetChangesTokenRequest parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (GetChangesTokenRequest) GeneratedMessageLite.M(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static GetChangesTokenRequest parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (GetChangesTokenRequest) GeneratedMessageLite.J(DEFAULT_INSTANCE, codedInputStream);
        }

        public static GetChangesTokenRequest parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (GetChangesTokenRequest) GeneratedMessageLite.K(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }
    }

    public interface GetChangesTokenRequestOrBuilder extends MessageLiteOrBuilder {
        DataProto.DataOrigin getDataOriginFilters(int i2);

        int getDataOriginFiltersCount();

        List<DataProto.DataOrigin> getDataOriginFiltersList();

        DataProto.DataType getDataType(int i2);

        int getDataTypeCount();

        List<DataProto.DataType> getDataTypeList();
    }

    public static final class ReadDataPointRequest extends GeneratedMessageLite<ReadDataPointRequest, Builder> implements ReadDataPointRequestOrBuilder {
        public static final int CLIENT_ID_FIELD_NUMBER = 3;
        public static final int DATA_TYPE_FIELD_NUMBER = 1;
        private static final ReadDataPointRequest DEFAULT_INSTANCE;
        private static volatile Parser<ReadDataPointRequest> PARSER = null;
        public static final int UID_FIELD_NUMBER = 2;
        private int bitField0_;
        private DataProto.DataType dataType_;
        private String uid_ = "";
        private String clientId_ = "";

        public static final class Builder extends GeneratedMessageLite.Builder<ReadDataPointRequest, Builder> implements ReadDataPointRequestOrBuilder {
            /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
                this();
            }

            public Builder clearClientId() {
                d();
                ((ReadDataPointRequest) this.f4444a).clearClientId();
                return this;
            }

            public Builder clearDataType() {
                d();
                ((ReadDataPointRequest) this.f4444a).clearDataType();
                return this;
            }

            public Builder clearUid() {
                d();
                ((ReadDataPointRequest) this.f4444a).clearUid();
                return this;
            }

            @Override // androidx.health.platform.client.proto.RequestProto.ReadDataPointRequestOrBuilder
            public String getClientId() {
                return ((ReadDataPointRequest) this.f4444a).getClientId();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.ReadDataPointRequestOrBuilder
            public ByteString getClientIdBytes() {
                return ((ReadDataPointRequest) this.f4444a).getClientIdBytes();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.ReadDataPointRequestOrBuilder
            public DataProto.DataType getDataType() {
                return ((ReadDataPointRequest) this.f4444a).getDataType();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.ReadDataPointRequestOrBuilder
            public String getUid() {
                return ((ReadDataPointRequest) this.f4444a).getUid();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.ReadDataPointRequestOrBuilder
            public ByteString getUidBytes() {
                return ((ReadDataPointRequest) this.f4444a).getUidBytes();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.ReadDataPointRequestOrBuilder
            public boolean hasClientId() {
                return ((ReadDataPointRequest) this.f4444a).hasClientId();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.ReadDataPointRequestOrBuilder
            public boolean hasDataType() {
                return ((ReadDataPointRequest) this.f4444a).hasDataType();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.ReadDataPointRequestOrBuilder
            public boolean hasUid() {
                return ((ReadDataPointRequest) this.f4444a).hasUid();
            }

            public Builder mergeDataType(DataProto.DataType dataType) {
                d();
                ((ReadDataPointRequest) this.f4444a).mergeDataType(dataType);
                return this;
            }

            public Builder setClientId(String str) {
                d();
                ((ReadDataPointRequest) this.f4444a).setClientId(str);
                return this;
            }

            public Builder setClientIdBytes(ByteString byteString) {
                d();
                ((ReadDataPointRequest) this.f4444a).setClientIdBytes(byteString);
                return this;
            }

            public Builder setDataType(DataProto.DataType dataType) {
                d();
                ((ReadDataPointRequest) this.f4444a).setDataType(dataType);
                return this;
            }

            public Builder setUid(String str) {
                d();
                ((ReadDataPointRequest) this.f4444a).setUid(str);
                return this;
            }

            public Builder setUidBytes(ByteString byteString) {
                d();
                ((ReadDataPointRequest) this.f4444a).setUidBytes(byteString);
                return this;
            }

            private Builder() {
                super(ReadDataPointRequest.DEFAULT_INSTANCE);
            }

            public Builder setDataType(DataProto.DataType.Builder builder) {
                d();
                ((ReadDataPointRequest) this.f4444a).setDataType(builder.build());
                return this;
            }
        }

        static {
            ReadDataPointRequest readDataPointRequest = new ReadDataPointRequest();
            DEFAULT_INSTANCE = readDataPointRequest;
            GeneratedMessageLite.T(ReadDataPointRequest.class, readDataPointRequest);
        }

        private ReadDataPointRequest() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearClientId() {
            this.bitField0_ &= -5;
            this.clientId_ = getDefaultInstance().getClientId();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearDataType() {
            this.dataType_ = null;
            this.bitField0_ &= -2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearUid() {
            this.bitField0_ &= -3;
            this.uid_ = getDefaultInstance().getUid();
        }

        public static ReadDataPointRequest getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mergeDataType(DataProto.DataType dataType) {
            dataType.getClass();
            DataProto.DataType dataType2 = this.dataType_;
            if (dataType2 == null || dataType2 == DataProto.DataType.getDefaultInstance()) {
                this.dataType_ = dataType;
            } else {
                this.dataType_ = DataProto.DataType.newBuilder(this.dataType_).mergeFrom((DataProto.DataType.Builder) dataType).buildPartial();
            }
            this.bitField0_ |= 1;
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.m();
        }

        public static ReadDataPointRequest parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (ReadDataPointRequest) GeneratedMessageLite.F(DEFAULT_INSTANCE, inputStream);
        }

        public static ReadDataPointRequest parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (ReadDataPointRequest) GeneratedMessageLite.N(DEFAULT_INSTANCE, byteBuffer);
        }

        public static Parser<ReadDataPointRequest> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setClientId(String str) {
            str.getClass();
            this.bitField0_ |= 4;
            this.clientId_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setClientIdBytes(ByteString byteString) {
            this.clientId_ = byteString.toStringUtf8();
            this.bitField0_ |= 4;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setDataType(DataProto.DataType dataType) {
            dataType.getClass();
            this.dataType_ = dataType;
            this.bitField0_ |= 1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setUid(String str) {
            str.getClass();
            this.bitField0_ |= 2;
            this.uid_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setUidBytes(ByteString byteString) {
            this.uid_ = byteString.toStringUtf8();
            this.bitField0_ |= 2;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.ReadDataPointRequestOrBuilder
        public String getClientId() {
            return this.clientId_;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.ReadDataPointRequestOrBuilder
        public ByteString getClientIdBytes() {
            return ByteString.copyFromUtf8(this.clientId_);
        }

        @Override // androidx.health.platform.client.proto.RequestProto.ReadDataPointRequestOrBuilder
        public DataProto.DataType getDataType() {
            DataProto.DataType dataType = this.dataType_;
            return dataType == null ? DataProto.DataType.getDefaultInstance() : dataType;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.ReadDataPointRequestOrBuilder
        public String getUid() {
            return this.uid_;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.ReadDataPointRequestOrBuilder
        public ByteString getUidBytes() {
            return ByteString.copyFromUtf8(this.uid_);
        }

        @Override // androidx.health.platform.client.proto.RequestProto.ReadDataPointRequestOrBuilder
        public boolean hasClientId() {
            return (this.bitField0_ & 4) != 0;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.ReadDataPointRequestOrBuilder
        public boolean hasDataType() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.ReadDataPointRequestOrBuilder
        public boolean hasUid() {
            return (this.bitField0_ & 2) != 0;
        }

        @Override // androidx.health.platform.client.proto.GeneratedMessageLite
        protected final Object q(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            AnonymousClass1 anonymousClass1 = null;
            switch (AnonymousClass1.f4477a[methodToInvoke.ordinal()]) {
                case 1:
                    return new ReadDataPointRequest();
                case 2:
                    return new Builder(anonymousClass1);
                case 3:
                    return GeneratedMessageLite.D(DEFAULT_INSTANCE, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဈ\u0001\u0003ဈ\u0002", new Object[]{"bitField0_", "dataType_", "uid_", "clientId_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<ReadDataPointRequest> defaultInstanceBasedParser = PARSER;
                    if (defaultInstanceBasedParser == null) {
                        synchronized (ReadDataPointRequest.class) {
                            try {
                                defaultInstanceBasedParser = PARSER;
                                if (defaultInstanceBasedParser == null) {
                                    defaultInstanceBasedParser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                    PARSER = defaultInstanceBasedParser;
                                }
                            } finally {
                            }
                        }
                    }
                    return defaultInstanceBasedParser;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public static Builder newBuilder(ReadDataPointRequest readDataPointRequest) {
            return (Builder) DEFAULT_INSTANCE.n(readDataPointRequest);
        }

        public static ReadDataPointRequest parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ReadDataPointRequest) GeneratedMessageLite.G(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static ReadDataPointRequest parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ReadDataPointRequest) GeneratedMessageLite.O(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static ReadDataPointRequest parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (ReadDataPointRequest) GeneratedMessageLite.H(DEFAULT_INSTANCE, byteString);
        }

        public static ReadDataPointRequest parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ReadDataPointRequest) GeneratedMessageLite.I(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static ReadDataPointRequest parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (ReadDataPointRequest) GeneratedMessageLite.P(DEFAULT_INSTANCE, bArr);
        }

        public static ReadDataPointRequest parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ReadDataPointRequest) GeneratedMessageLite.Q(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static ReadDataPointRequest parseFrom(InputStream inputStream) throws IOException {
            return (ReadDataPointRequest) GeneratedMessageLite.L(DEFAULT_INSTANCE, inputStream);
        }

        public static ReadDataPointRequest parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ReadDataPointRequest) GeneratedMessageLite.M(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static ReadDataPointRequest parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (ReadDataPointRequest) GeneratedMessageLite.J(DEFAULT_INSTANCE, codedInputStream);
        }

        public static ReadDataPointRequest parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ReadDataPointRequest) GeneratedMessageLite.K(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }
    }

    public interface ReadDataPointRequestOrBuilder extends MessageLiteOrBuilder {
        String getClientId();

        ByteString getClientIdBytes();

        DataProto.DataType getDataType();

        String getUid();

        ByteString getUidBytes();

        boolean hasClientId();

        boolean hasDataType();

        boolean hasUid();
    }

    public static final class ReadDataRangeRequest extends GeneratedMessageLite<ReadDataRangeRequest, Builder> implements ReadDataRangeRequestOrBuilder {
        public static final int ASC_ORDERING_FIELD_NUMBER = 7;
        public static final int DATA_ORIGIN_FILTERS_FIELD_NUMBER = 3;
        public static final int DATA_TYPE_FIELD_NUMBER = 2;
        private static final ReadDataRangeRequest DEFAULT_INSTANCE;
        public static final int LIMIT_FIELD_NUMBER = 4;
        public static final int PAGE_SIZE_FIELD_NUMBER = 5;
        public static final int PAGE_TOKEN_FIELD_NUMBER = 6;
        private static volatile Parser<ReadDataRangeRequest> PARSER = null;
        public static final int TIME_SPEC_FIELD_NUMBER = 1;
        private int bitField0_;
        private DataProto.DataType dataType_;
        private int limit_;
        private int pageSize_;
        private TimeProto.TimeSpec timeSpec_;
        private Internal.ProtobufList<DataProto.DataOrigin> dataOriginFilters_ = GeneratedMessageLite.r();
        private boolean ascOrdering_ = true;
        private String pageToken_ = "";

        public static final class Builder extends GeneratedMessageLite.Builder<ReadDataRangeRequest, Builder> implements ReadDataRangeRequestOrBuilder {
            /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
                this();
            }

            public Builder addAllDataOriginFilters(Iterable<? extends DataProto.DataOrigin> iterable) {
                d();
                ((ReadDataRangeRequest) this.f4444a).addAllDataOriginFilters(iterable);
                return this;
            }

            public Builder addDataOriginFilters(DataProto.DataOrigin dataOrigin) {
                d();
                ((ReadDataRangeRequest) this.f4444a).addDataOriginFilters(dataOrigin);
                return this;
            }

            public Builder clearAscOrdering() {
                d();
                ((ReadDataRangeRequest) this.f4444a).clearAscOrdering();
                return this;
            }

            public Builder clearDataOriginFilters() {
                d();
                ((ReadDataRangeRequest) this.f4444a).clearDataOriginFilters();
                return this;
            }

            public Builder clearDataType() {
                d();
                ((ReadDataRangeRequest) this.f4444a).clearDataType();
                return this;
            }

            public Builder clearLimit() {
                d();
                ((ReadDataRangeRequest) this.f4444a).clearLimit();
                return this;
            }

            public Builder clearPageSize() {
                d();
                ((ReadDataRangeRequest) this.f4444a).clearPageSize();
                return this;
            }

            public Builder clearPageToken() {
                d();
                ((ReadDataRangeRequest) this.f4444a).clearPageToken();
                return this;
            }

            public Builder clearTimeSpec() {
                d();
                ((ReadDataRangeRequest) this.f4444a).clearTimeSpec();
                return this;
            }

            @Override // androidx.health.platform.client.proto.RequestProto.ReadDataRangeRequestOrBuilder
            public boolean getAscOrdering() {
                return ((ReadDataRangeRequest) this.f4444a).getAscOrdering();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.ReadDataRangeRequestOrBuilder
            public DataProto.DataOrigin getDataOriginFilters(int i2) {
                return ((ReadDataRangeRequest) this.f4444a).getDataOriginFilters(i2);
            }

            @Override // androidx.health.platform.client.proto.RequestProto.ReadDataRangeRequestOrBuilder
            public int getDataOriginFiltersCount() {
                return ((ReadDataRangeRequest) this.f4444a).getDataOriginFiltersCount();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.ReadDataRangeRequestOrBuilder
            public List<DataProto.DataOrigin> getDataOriginFiltersList() {
                return Collections.unmodifiableList(((ReadDataRangeRequest) this.f4444a).getDataOriginFiltersList());
            }

            @Override // androidx.health.platform.client.proto.RequestProto.ReadDataRangeRequestOrBuilder
            public DataProto.DataType getDataType() {
                return ((ReadDataRangeRequest) this.f4444a).getDataType();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.ReadDataRangeRequestOrBuilder
            public int getLimit() {
                return ((ReadDataRangeRequest) this.f4444a).getLimit();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.ReadDataRangeRequestOrBuilder
            public int getPageSize() {
                return ((ReadDataRangeRequest) this.f4444a).getPageSize();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.ReadDataRangeRequestOrBuilder
            public String getPageToken() {
                return ((ReadDataRangeRequest) this.f4444a).getPageToken();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.ReadDataRangeRequestOrBuilder
            public ByteString getPageTokenBytes() {
                return ((ReadDataRangeRequest) this.f4444a).getPageTokenBytes();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.ReadDataRangeRequestOrBuilder
            public TimeProto.TimeSpec getTimeSpec() {
                return ((ReadDataRangeRequest) this.f4444a).getTimeSpec();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.ReadDataRangeRequestOrBuilder
            public boolean hasAscOrdering() {
                return ((ReadDataRangeRequest) this.f4444a).hasAscOrdering();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.ReadDataRangeRequestOrBuilder
            public boolean hasDataType() {
                return ((ReadDataRangeRequest) this.f4444a).hasDataType();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.ReadDataRangeRequestOrBuilder
            public boolean hasLimit() {
                return ((ReadDataRangeRequest) this.f4444a).hasLimit();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.ReadDataRangeRequestOrBuilder
            public boolean hasPageSize() {
                return ((ReadDataRangeRequest) this.f4444a).hasPageSize();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.ReadDataRangeRequestOrBuilder
            public boolean hasPageToken() {
                return ((ReadDataRangeRequest) this.f4444a).hasPageToken();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.ReadDataRangeRequestOrBuilder
            public boolean hasTimeSpec() {
                return ((ReadDataRangeRequest) this.f4444a).hasTimeSpec();
            }

            public Builder mergeDataType(DataProto.DataType dataType) {
                d();
                ((ReadDataRangeRequest) this.f4444a).mergeDataType(dataType);
                return this;
            }

            public Builder mergeTimeSpec(TimeProto.TimeSpec timeSpec) {
                d();
                ((ReadDataRangeRequest) this.f4444a).mergeTimeSpec(timeSpec);
                return this;
            }

            public Builder removeDataOriginFilters(int i2) {
                d();
                ((ReadDataRangeRequest) this.f4444a).removeDataOriginFilters(i2);
                return this;
            }

            public Builder setAscOrdering(boolean z2) {
                d();
                ((ReadDataRangeRequest) this.f4444a).setAscOrdering(z2);
                return this;
            }

            public Builder setDataOriginFilters(int i2, DataProto.DataOrigin dataOrigin) {
                d();
                ((ReadDataRangeRequest) this.f4444a).setDataOriginFilters(i2, dataOrigin);
                return this;
            }

            public Builder setDataType(DataProto.DataType dataType) {
                d();
                ((ReadDataRangeRequest) this.f4444a).setDataType(dataType);
                return this;
            }

            public Builder setLimit(int i2) {
                d();
                ((ReadDataRangeRequest) this.f4444a).setLimit(i2);
                return this;
            }

            public Builder setPageSize(int i2) {
                d();
                ((ReadDataRangeRequest) this.f4444a).setPageSize(i2);
                return this;
            }

            public Builder setPageToken(String str) {
                d();
                ((ReadDataRangeRequest) this.f4444a).setPageToken(str);
                return this;
            }

            public Builder setPageTokenBytes(ByteString byteString) {
                d();
                ((ReadDataRangeRequest) this.f4444a).setPageTokenBytes(byteString);
                return this;
            }

            public Builder setTimeSpec(TimeProto.TimeSpec timeSpec) {
                d();
                ((ReadDataRangeRequest) this.f4444a).setTimeSpec(timeSpec);
                return this;
            }

            private Builder() {
                super(ReadDataRangeRequest.DEFAULT_INSTANCE);
            }

            public Builder addDataOriginFilters(int i2, DataProto.DataOrigin dataOrigin) {
                d();
                ((ReadDataRangeRequest) this.f4444a).addDataOriginFilters(i2, dataOrigin);
                return this;
            }

            public Builder setDataOriginFilters(int i2, DataProto.DataOrigin.Builder builder) {
                d();
                ((ReadDataRangeRequest) this.f4444a).setDataOriginFilters(i2, builder.build());
                return this;
            }

            public Builder setDataType(DataProto.DataType.Builder builder) {
                d();
                ((ReadDataRangeRequest) this.f4444a).setDataType(builder.build());
                return this;
            }

            public Builder setTimeSpec(TimeProto.TimeSpec.Builder builder) {
                d();
                ((ReadDataRangeRequest) this.f4444a).setTimeSpec(builder.build());
                return this;
            }

            public Builder addDataOriginFilters(DataProto.DataOrigin.Builder builder) {
                d();
                ((ReadDataRangeRequest) this.f4444a).addDataOriginFilters(builder.build());
                return this;
            }

            public Builder addDataOriginFilters(int i2, DataProto.DataOrigin.Builder builder) {
                d();
                ((ReadDataRangeRequest) this.f4444a).addDataOriginFilters(i2, builder.build());
                return this;
            }
        }

        static {
            ReadDataRangeRequest readDataRangeRequest = new ReadDataRangeRequest();
            DEFAULT_INSTANCE = readDataRangeRequest;
            GeneratedMessageLite.T(ReadDataRangeRequest.class, readDataRangeRequest);
        }

        private ReadDataRangeRequest() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addAllDataOriginFilters(Iterable<? extends DataProto.DataOrigin> iterable) {
            ensureDataOriginFiltersIsMutable();
            AbstractMessageLite.a(iterable, this.dataOriginFilters_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addDataOriginFilters(DataProto.DataOrigin dataOrigin) {
            dataOrigin.getClass();
            ensureDataOriginFiltersIsMutable();
            this.dataOriginFilters_.add(dataOrigin);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearAscOrdering() {
            this.bitField0_ &= -5;
            this.ascOrdering_ = true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearDataOriginFilters() {
            this.dataOriginFilters_ = GeneratedMessageLite.r();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearDataType() {
            this.dataType_ = null;
            this.bitField0_ &= -3;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearLimit() {
            this.bitField0_ &= -9;
            this.limit_ = 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearPageSize() {
            this.bitField0_ &= -17;
            this.pageSize_ = 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearPageToken() {
            this.bitField0_ &= -33;
            this.pageToken_ = getDefaultInstance().getPageToken();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearTimeSpec() {
            this.timeSpec_ = null;
            this.bitField0_ &= -2;
        }

        private void ensureDataOriginFiltersIsMutable() {
            Internal.ProtobufList<DataProto.DataOrigin> protobufList = this.dataOriginFilters_;
            if (protobufList.isModifiable()) {
                return;
            }
            this.dataOriginFilters_ = GeneratedMessageLite.C(protobufList);
        }

        public static ReadDataRangeRequest getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mergeDataType(DataProto.DataType dataType) {
            dataType.getClass();
            DataProto.DataType dataType2 = this.dataType_;
            if (dataType2 == null || dataType2 == DataProto.DataType.getDefaultInstance()) {
                this.dataType_ = dataType;
            } else {
                this.dataType_ = DataProto.DataType.newBuilder(this.dataType_).mergeFrom((DataProto.DataType.Builder) dataType).buildPartial();
            }
            this.bitField0_ |= 2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mergeTimeSpec(TimeProto.TimeSpec timeSpec) {
            timeSpec.getClass();
            TimeProto.TimeSpec timeSpec2 = this.timeSpec_;
            if (timeSpec2 == null || timeSpec2 == TimeProto.TimeSpec.getDefaultInstance()) {
                this.timeSpec_ = timeSpec;
            } else {
                this.timeSpec_ = TimeProto.TimeSpec.newBuilder(this.timeSpec_).mergeFrom((TimeProto.TimeSpec.Builder) timeSpec).buildPartial();
            }
            this.bitField0_ |= 1;
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.m();
        }

        public static ReadDataRangeRequest parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (ReadDataRangeRequest) GeneratedMessageLite.F(DEFAULT_INSTANCE, inputStream);
        }

        public static ReadDataRangeRequest parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (ReadDataRangeRequest) GeneratedMessageLite.N(DEFAULT_INSTANCE, byteBuffer);
        }

        public static Parser<ReadDataRangeRequest> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void removeDataOriginFilters(int i2) {
            ensureDataOriginFiltersIsMutable();
            this.dataOriginFilters_.remove(i2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setAscOrdering(boolean z2) {
            this.bitField0_ |= 4;
            this.ascOrdering_ = z2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setDataOriginFilters(int i2, DataProto.DataOrigin dataOrigin) {
            dataOrigin.getClass();
            ensureDataOriginFiltersIsMutable();
            this.dataOriginFilters_.set(i2, dataOrigin);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setDataType(DataProto.DataType dataType) {
            dataType.getClass();
            this.dataType_ = dataType;
            this.bitField0_ |= 2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setLimit(int i2) {
            this.bitField0_ |= 8;
            this.limit_ = i2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setPageSize(int i2) {
            this.bitField0_ |= 16;
            this.pageSize_ = i2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setPageToken(String str) {
            str.getClass();
            this.bitField0_ |= 32;
            this.pageToken_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setPageTokenBytes(ByteString byteString) {
            this.pageToken_ = byteString.toStringUtf8();
            this.bitField0_ |= 32;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setTimeSpec(TimeProto.TimeSpec timeSpec) {
            timeSpec.getClass();
            this.timeSpec_ = timeSpec;
            this.bitField0_ |= 1;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.ReadDataRangeRequestOrBuilder
        public boolean getAscOrdering() {
            return this.ascOrdering_;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.ReadDataRangeRequestOrBuilder
        public DataProto.DataOrigin getDataOriginFilters(int i2) {
            return this.dataOriginFilters_.get(i2);
        }

        @Override // androidx.health.platform.client.proto.RequestProto.ReadDataRangeRequestOrBuilder
        public int getDataOriginFiltersCount() {
            return this.dataOriginFilters_.size();
        }

        @Override // androidx.health.platform.client.proto.RequestProto.ReadDataRangeRequestOrBuilder
        public List<DataProto.DataOrigin> getDataOriginFiltersList() {
            return this.dataOriginFilters_;
        }

        public DataProto.DataOriginOrBuilder getDataOriginFiltersOrBuilder(int i2) {
            return this.dataOriginFilters_.get(i2);
        }

        public List<? extends DataProto.DataOriginOrBuilder> getDataOriginFiltersOrBuilderList() {
            return this.dataOriginFilters_;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.ReadDataRangeRequestOrBuilder
        public DataProto.DataType getDataType() {
            DataProto.DataType dataType = this.dataType_;
            return dataType == null ? DataProto.DataType.getDefaultInstance() : dataType;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.ReadDataRangeRequestOrBuilder
        public int getLimit() {
            return this.limit_;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.ReadDataRangeRequestOrBuilder
        public int getPageSize() {
            return this.pageSize_;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.ReadDataRangeRequestOrBuilder
        public String getPageToken() {
            return this.pageToken_;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.ReadDataRangeRequestOrBuilder
        public ByteString getPageTokenBytes() {
            return ByteString.copyFromUtf8(this.pageToken_);
        }

        @Override // androidx.health.platform.client.proto.RequestProto.ReadDataRangeRequestOrBuilder
        public TimeProto.TimeSpec getTimeSpec() {
            TimeProto.TimeSpec timeSpec = this.timeSpec_;
            return timeSpec == null ? TimeProto.TimeSpec.getDefaultInstance() : timeSpec;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.ReadDataRangeRequestOrBuilder
        public boolean hasAscOrdering() {
            return (this.bitField0_ & 4) != 0;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.ReadDataRangeRequestOrBuilder
        public boolean hasDataType() {
            return (this.bitField0_ & 2) != 0;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.ReadDataRangeRequestOrBuilder
        public boolean hasLimit() {
            return (this.bitField0_ & 8) != 0;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.ReadDataRangeRequestOrBuilder
        public boolean hasPageSize() {
            return (this.bitField0_ & 16) != 0;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.ReadDataRangeRequestOrBuilder
        public boolean hasPageToken() {
            return (this.bitField0_ & 32) != 0;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.ReadDataRangeRequestOrBuilder
        public boolean hasTimeSpec() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override // androidx.health.platform.client.proto.GeneratedMessageLite
        protected final Object q(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            AnonymousClass1 anonymousClass1 = null;
            switch (AnonymousClass1.f4477a[methodToInvoke.ordinal()]) {
                case 1:
                    return new ReadDataRangeRequest();
                case 2:
                    return new Builder(anonymousClass1);
                case 3:
                    return GeneratedMessageLite.D(DEFAULT_INSTANCE, "\u0001\u0007\u0000\u0001\u0001\u0007\u0007\u0000\u0001\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003\u001b\u0004င\u0003\u0005င\u0004\u0006ဈ\u0005\u0007ဇ\u0002", new Object[]{"bitField0_", "timeSpec_", "dataType_", "dataOriginFilters_", DataProto.DataOrigin.class, "limit_", "pageSize_", "pageToken_", "ascOrdering_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<ReadDataRangeRequest> defaultInstanceBasedParser = PARSER;
                    if (defaultInstanceBasedParser == null) {
                        synchronized (ReadDataRangeRequest.class) {
                            try {
                                defaultInstanceBasedParser = PARSER;
                                if (defaultInstanceBasedParser == null) {
                                    defaultInstanceBasedParser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                    PARSER = defaultInstanceBasedParser;
                                }
                            } finally {
                            }
                        }
                    }
                    return defaultInstanceBasedParser;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public static Builder newBuilder(ReadDataRangeRequest readDataRangeRequest) {
            return (Builder) DEFAULT_INSTANCE.n(readDataRangeRequest);
        }

        public static ReadDataRangeRequest parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ReadDataRangeRequest) GeneratedMessageLite.G(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static ReadDataRangeRequest parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ReadDataRangeRequest) GeneratedMessageLite.O(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static ReadDataRangeRequest parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (ReadDataRangeRequest) GeneratedMessageLite.H(DEFAULT_INSTANCE, byteString);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addDataOriginFilters(int i2, DataProto.DataOrigin dataOrigin) {
            dataOrigin.getClass();
            ensureDataOriginFiltersIsMutable();
            this.dataOriginFilters_.add(i2, dataOrigin);
        }

        public static ReadDataRangeRequest parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ReadDataRangeRequest) GeneratedMessageLite.I(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static ReadDataRangeRequest parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (ReadDataRangeRequest) GeneratedMessageLite.P(DEFAULT_INSTANCE, bArr);
        }

        public static ReadDataRangeRequest parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ReadDataRangeRequest) GeneratedMessageLite.Q(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static ReadDataRangeRequest parseFrom(InputStream inputStream) throws IOException {
            return (ReadDataRangeRequest) GeneratedMessageLite.L(DEFAULT_INSTANCE, inputStream);
        }

        public static ReadDataRangeRequest parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ReadDataRangeRequest) GeneratedMessageLite.M(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static ReadDataRangeRequest parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (ReadDataRangeRequest) GeneratedMessageLite.J(DEFAULT_INSTANCE, codedInputStream);
        }

        public static ReadDataRangeRequest parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ReadDataRangeRequest) GeneratedMessageLite.K(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }
    }

    public interface ReadDataRangeRequestOrBuilder extends MessageLiteOrBuilder {
        boolean getAscOrdering();

        DataProto.DataOrigin getDataOriginFilters(int i2);

        int getDataOriginFiltersCount();

        List<DataProto.DataOrigin> getDataOriginFiltersList();

        DataProto.DataType getDataType();

        int getLimit();

        int getPageSize();

        String getPageToken();

        ByteString getPageTokenBytes();

        TimeProto.TimeSpec getTimeSpec();

        boolean hasAscOrdering();

        boolean hasDataType();

        boolean hasLimit();

        boolean hasPageSize();

        boolean hasPageToken();

        boolean hasTimeSpec();
    }

    public static final class ReadDataRequest extends GeneratedMessageLite<ReadDataRequest, Builder> implements ReadDataRequestOrBuilder {
        public static final int DATA_TYPE_ID_PAIR_FIELD_NUMBER = 1;
        private static final ReadDataRequest DEFAULT_INSTANCE;
        private static volatile Parser<ReadDataRequest> PARSER;
        private int bitField0_;
        private DataTypeIdPair dataTypeIdPair_;

        public static final class Builder extends GeneratedMessageLite.Builder<ReadDataRequest, Builder> implements ReadDataRequestOrBuilder {
            /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
                this();
            }

            public Builder clearDataTypeIdPair() {
                d();
                ((ReadDataRequest) this.f4444a).clearDataTypeIdPair();
                return this;
            }

            @Override // androidx.health.platform.client.proto.RequestProto.ReadDataRequestOrBuilder
            public DataTypeIdPair getDataTypeIdPair() {
                return ((ReadDataRequest) this.f4444a).getDataTypeIdPair();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.ReadDataRequestOrBuilder
            public boolean hasDataTypeIdPair() {
                return ((ReadDataRequest) this.f4444a).hasDataTypeIdPair();
            }

            public Builder mergeDataTypeIdPair(DataTypeIdPair dataTypeIdPair) {
                d();
                ((ReadDataRequest) this.f4444a).mergeDataTypeIdPair(dataTypeIdPair);
                return this;
            }

            public Builder setDataTypeIdPair(DataTypeIdPair dataTypeIdPair) {
                d();
                ((ReadDataRequest) this.f4444a).setDataTypeIdPair(dataTypeIdPair);
                return this;
            }

            private Builder() {
                super(ReadDataRequest.DEFAULT_INSTANCE);
            }

            public Builder setDataTypeIdPair(DataTypeIdPair.Builder builder) {
                d();
                ((ReadDataRequest) this.f4444a).setDataTypeIdPair(builder.build());
                return this;
            }
        }

        static {
            ReadDataRequest readDataRequest = new ReadDataRequest();
            DEFAULT_INSTANCE = readDataRequest;
            GeneratedMessageLite.T(ReadDataRequest.class, readDataRequest);
        }

        private ReadDataRequest() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearDataTypeIdPair() {
            this.dataTypeIdPair_ = null;
            this.bitField0_ &= -2;
        }

        public static ReadDataRequest getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mergeDataTypeIdPair(DataTypeIdPair dataTypeIdPair) {
            dataTypeIdPair.getClass();
            DataTypeIdPair dataTypeIdPair2 = this.dataTypeIdPair_;
            if (dataTypeIdPair2 == null || dataTypeIdPair2 == DataTypeIdPair.getDefaultInstance()) {
                this.dataTypeIdPair_ = dataTypeIdPair;
            } else {
                this.dataTypeIdPair_ = DataTypeIdPair.newBuilder(this.dataTypeIdPair_).mergeFrom((DataTypeIdPair.Builder) dataTypeIdPair).buildPartial();
            }
            this.bitField0_ |= 1;
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.m();
        }

        public static ReadDataRequest parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (ReadDataRequest) GeneratedMessageLite.F(DEFAULT_INSTANCE, inputStream);
        }

        public static ReadDataRequest parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (ReadDataRequest) GeneratedMessageLite.N(DEFAULT_INSTANCE, byteBuffer);
        }

        public static Parser<ReadDataRequest> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setDataTypeIdPair(DataTypeIdPair dataTypeIdPair) {
            dataTypeIdPair.getClass();
            this.dataTypeIdPair_ = dataTypeIdPair;
            this.bitField0_ |= 1;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.ReadDataRequestOrBuilder
        public DataTypeIdPair getDataTypeIdPair() {
            DataTypeIdPair dataTypeIdPair = this.dataTypeIdPair_;
            return dataTypeIdPair == null ? DataTypeIdPair.getDefaultInstance() : dataTypeIdPair;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.ReadDataRequestOrBuilder
        public boolean hasDataTypeIdPair() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override // androidx.health.platform.client.proto.GeneratedMessageLite
        protected final Object q(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            AnonymousClass1 anonymousClass1 = null;
            switch (AnonymousClass1.f4477a[methodToInvoke.ordinal()]) {
                case 1:
                    return new ReadDataRequest();
                case 2:
                    return new Builder(anonymousClass1);
                case 3:
                    return GeneratedMessageLite.D(DEFAULT_INSTANCE, "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001ဉ\u0000", new Object[]{"bitField0_", "dataTypeIdPair_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<ReadDataRequest> defaultInstanceBasedParser = PARSER;
                    if (defaultInstanceBasedParser == null) {
                        synchronized (ReadDataRequest.class) {
                            try {
                                defaultInstanceBasedParser = PARSER;
                                if (defaultInstanceBasedParser == null) {
                                    defaultInstanceBasedParser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                    PARSER = defaultInstanceBasedParser;
                                }
                            } finally {
                            }
                        }
                    }
                    return defaultInstanceBasedParser;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public static Builder newBuilder(ReadDataRequest readDataRequest) {
            return (Builder) DEFAULT_INSTANCE.n(readDataRequest);
        }

        public static ReadDataRequest parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ReadDataRequest) GeneratedMessageLite.G(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static ReadDataRequest parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ReadDataRequest) GeneratedMessageLite.O(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static ReadDataRequest parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (ReadDataRequest) GeneratedMessageLite.H(DEFAULT_INSTANCE, byteString);
        }

        public static ReadDataRequest parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ReadDataRequest) GeneratedMessageLite.I(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static ReadDataRequest parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (ReadDataRequest) GeneratedMessageLite.P(DEFAULT_INSTANCE, bArr);
        }

        public static ReadDataRequest parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ReadDataRequest) GeneratedMessageLite.Q(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static ReadDataRequest parseFrom(InputStream inputStream) throws IOException {
            return (ReadDataRequest) GeneratedMessageLite.L(DEFAULT_INSTANCE, inputStream);
        }

        public static ReadDataRequest parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ReadDataRequest) GeneratedMessageLite.M(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static ReadDataRequest parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (ReadDataRequest) GeneratedMessageLite.J(DEFAULT_INSTANCE, codedInputStream);
        }

        public static ReadDataRequest parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ReadDataRequest) GeneratedMessageLite.K(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }
    }

    public interface ReadDataRequestOrBuilder extends MessageLiteOrBuilder {
        DataTypeIdPair getDataTypeIdPair();

        boolean hasDataTypeIdPair();
    }

    public static final class ReadExerciseRouteRequest extends GeneratedMessageLite<ReadExerciseRouteRequest, Builder> implements ReadExerciseRouteRequestOrBuilder {
        private static final ReadExerciseRouteRequest DEFAULT_INSTANCE;
        private static volatile Parser<ReadExerciseRouteRequest> PARSER = null;
        public static final int SESSIONUID_FIELD_NUMBER = 1;
        private int bitField0_;
        private String sessionUid_ = "";

        public static final class Builder extends GeneratedMessageLite.Builder<ReadExerciseRouteRequest, Builder> implements ReadExerciseRouteRequestOrBuilder {
            /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
                this();
            }

            public Builder clearSessionUid() {
                d();
                ((ReadExerciseRouteRequest) this.f4444a).clearSessionUid();
                return this;
            }

            @Override // androidx.health.platform.client.proto.RequestProto.ReadExerciseRouteRequestOrBuilder
            public String getSessionUid() {
                return ((ReadExerciseRouteRequest) this.f4444a).getSessionUid();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.ReadExerciseRouteRequestOrBuilder
            public ByteString getSessionUidBytes() {
                return ((ReadExerciseRouteRequest) this.f4444a).getSessionUidBytes();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.ReadExerciseRouteRequestOrBuilder
            public boolean hasSessionUid() {
                return ((ReadExerciseRouteRequest) this.f4444a).hasSessionUid();
            }

            public Builder setSessionUid(String str) {
                d();
                ((ReadExerciseRouteRequest) this.f4444a).setSessionUid(str);
                return this;
            }

            public Builder setSessionUidBytes(ByteString byteString) {
                d();
                ((ReadExerciseRouteRequest) this.f4444a).setSessionUidBytes(byteString);
                return this;
            }

            private Builder() {
                super(ReadExerciseRouteRequest.DEFAULT_INSTANCE);
            }
        }

        static {
            ReadExerciseRouteRequest readExerciseRouteRequest = new ReadExerciseRouteRequest();
            DEFAULT_INSTANCE = readExerciseRouteRequest;
            GeneratedMessageLite.T(ReadExerciseRouteRequest.class, readExerciseRouteRequest);
        }

        private ReadExerciseRouteRequest() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearSessionUid() {
            this.bitField0_ &= -2;
            this.sessionUid_ = getDefaultInstance().getSessionUid();
        }

        public static ReadExerciseRouteRequest getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.m();
        }

        public static ReadExerciseRouteRequest parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (ReadExerciseRouteRequest) GeneratedMessageLite.F(DEFAULT_INSTANCE, inputStream);
        }

        public static ReadExerciseRouteRequest parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (ReadExerciseRouteRequest) GeneratedMessageLite.N(DEFAULT_INSTANCE, byteBuffer);
        }

        public static Parser<ReadExerciseRouteRequest> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setSessionUid(String str) {
            str.getClass();
            this.bitField0_ |= 1;
            this.sessionUid_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setSessionUidBytes(ByteString byteString) {
            this.sessionUid_ = byteString.toStringUtf8();
            this.bitField0_ |= 1;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.ReadExerciseRouteRequestOrBuilder
        public String getSessionUid() {
            return this.sessionUid_;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.ReadExerciseRouteRequestOrBuilder
        public ByteString getSessionUidBytes() {
            return ByteString.copyFromUtf8(this.sessionUid_);
        }

        @Override // androidx.health.platform.client.proto.RequestProto.ReadExerciseRouteRequestOrBuilder
        public boolean hasSessionUid() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override // androidx.health.platform.client.proto.GeneratedMessageLite
        protected final Object q(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            AnonymousClass1 anonymousClass1 = null;
            switch (AnonymousClass1.f4477a[methodToInvoke.ordinal()]) {
                case 1:
                    return new ReadExerciseRouteRequest();
                case 2:
                    return new Builder(anonymousClass1);
                case 3:
                    return GeneratedMessageLite.D(DEFAULT_INSTANCE, "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001ဈ\u0000", new Object[]{"bitField0_", "sessionUid_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<ReadExerciseRouteRequest> defaultInstanceBasedParser = PARSER;
                    if (defaultInstanceBasedParser == null) {
                        synchronized (ReadExerciseRouteRequest.class) {
                            try {
                                defaultInstanceBasedParser = PARSER;
                                if (defaultInstanceBasedParser == null) {
                                    defaultInstanceBasedParser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                    PARSER = defaultInstanceBasedParser;
                                }
                            } finally {
                            }
                        }
                    }
                    return defaultInstanceBasedParser;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public static Builder newBuilder(ReadExerciseRouteRequest readExerciseRouteRequest) {
            return (Builder) DEFAULT_INSTANCE.n(readExerciseRouteRequest);
        }

        public static ReadExerciseRouteRequest parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ReadExerciseRouteRequest) GeneratedMessageLite.G(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static ReadExerciseRouteRequest parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ReadExerciseRouteRequest) GeneratedMessageLite.O(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static ReadExerciseRouteRequest parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (ReadExerciseRouteRequest) GeneratedMessageLite.H(DEFAULT_INSTANCE, byteString);
        }

        public static ReadExerciseRouteRequest parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ReadExerciseRouteRequest) GeneratedMessageLite.I(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static ReadExerciseRouteRequest parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (ReadExerciseRouteRequest) GeneratedMessageLite.P(DEFAULT_INSTANCE, bArr);
        }

        public static ReadExerciseRouteRequest parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ReadExerciseRouteRequest) GeneratedMessageLite.Q(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static ReadExerciseRouteRequest parseFrom(InputStream inputStream) throws IOException {
            return (ReadExerciseRouteRequest) GeneratedMessageLite.L(DEFAULT_INSTANCE, inputStream);
        }

        public static ReadExerciseRouteRequest parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ReadExerciseRouteRequest) GeneratedMessageLite.M(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static ReadExerciseRouteRequest parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (ReadExerciseRouteRequest) GeneratedMessageLite.J(DEFAULT_INSTANCE, codedInputStream);
        }

        public static ReadExerciseRouteRequest parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ReadExerciseRouteRequest) GeneratedMessageLite.K(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }
    }

    public interface ReadExerciseRouteRequestOrBuilder extends MessageLiteOrBuilder {
        String getSessionUid();

        ByteString getSessionUidBytes();

        boolean hasSessionUid();
    }

    public static final class RegisterForDataNotificationsRequest extends GeneratedMessageLite<RegisterForDataNotificationsRequest, Builder> implements RegisterForDataNotificationsRequestOrBuilder {
        public static final int DATA_TYPES_FIELD_NUMBER = 2;
        private static final RegisterForDataNotificationsRequest DEFAULT_INSTANCE;
        public static final int NOTIFICATIONINTENTACTION_FIELD_NUMBER = 1;
        private static volatile Parser<RegisterForDataNotificationsRequest> PARSER;
        private int bitField0_;
        private String notificationIntentAction_ = "";
        private Internal.ProtobufList<DataProto.DataType> dataTypes_ = GeneratedMessageLite.r();

        public static final class Builder extends GeneratedMessageLite.Builder<RegisterForDataNotificationsRequest, Builder> implements RegisterForDataNotificationsRequestOrBuilder {
            /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
                this();
            }

            public Builder addAllDataTypes(Iterable<? extends DataProto.DataType> iterable) {
                d();
                ((RegisterForDataNotificationsRequest) this.f4444a).addAllDataTypes(iterable);
                return this;
            }

            public Builder addDataTypes(DataProto.DataType dataType) {
                d();
                ((RegisterForDataNotificationsRequest) this.f4444a).addDataTypes(dataType);
                return this;
            }

            public Builder clearDataTypes() {
                d();
                ((RegisterForDataNotificationsRequest) this.f4444a).clearDataTypes();
                return this;
            }

            public Builder clearNotificationIntentAction() {
                d();
                ((RegisterForDataNotificationsRequest) this.f4444a).clearNotificationIntentAction();
                return this;
            }

            @Override // androidx.health.platform.client.proto.RequestProto.RegisterForDataNotificationsRequestOrBuilder
            public DataProto.DataType getDataTypes(int i2) {
                return ((RegisterForDataNotificationsRequest) this.f4444a).getDataTypes(i2);
            }

            @Override // androidx.health.platform.client.proto.RequestProto.RegisterForDataNotificationsRequestOrBuilder
            public int getDataTypesCount() {
                return ((RegisterForDataNotificationsRequest) this.f4444a).getDataTypesCount();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.RegisterForDataNotificationsRequestOrBuilder
            public List<DataProto.DataType> getDataTypesList() {
                return Collections.unmodifiableList(((RegisterForDataNotificationsRequest) this.f4444a).getDataTypesList());
            }

            @Override // androidx.health.platform.client.proto.RequestProto.RegisterForDataNotificationsRequestOrBuilder
            public String getNotificationIntentAction() {
                return ((RegisterForDataNotificationsRequest) this.f4444a).getNotificationIntentAction();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.RegisterForDataNotificationsRequestOrBuilder
            public ByteString getNotificationIntentActionBytes() {
                return ((RegisterForDataNotificationsRequest) this.f4444a).getNotificationIntentActionBytes();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.RegisterForDataNotificationsRequestOrBuilder
            public boolean hasNotificationIntentAction() {
                return ((RegisterForDataNotificationsRequest) this.f4444a).hasNotificationIntentAction();
            }

            public Builder removeDataTypes(int i2) {
                d();
                ((RegisterForDataNotificationsRequest) this.f4444a).removeDataTypes(i2);
                return this;
            }

            public Builder setDataTypes(int i2, DataProto.DataType dataType) {
                d();
                ((RegisterForDataNotificationsRequest) this.f4444a).setDataTypes(i2, dataType);
                return this;
            }

            public Builder setNotificationIntentAction(String str) {
                d();
                ((RegisterForDataNotificationsRequest) this.f4444a).setNotificationIntentAction(str);
                return this;
            }

            public Builder setNotificationIntentActionBytes(ByteString byteString) {
                d();
                ((RegisterForDataNotificationsRequest) this.f4444a).setNotificationIntentActionBytes(byteString);
                return this;
            }

            private Builder() {
                super(RegisterForDataNotificationsRequest.DEFAULT_INSTANCE);
            }

            public Builder addDataTypes(int i2, DataProto.DataType dataType) {
                d();
                ((RegisterForDataNotificationsRequest) this.f4444a).addDataTypes(i2, dataType);
                return this;
            }

            public Builder setDataTypes(int i2, DataProto.DataType.Builder builder) {
                d();
                ((RegisterForDataNotificationsRequest) this.f4444a).setDataTypes(i2, builder.build());
                return this;
            }

            public Builder addDataTypes(DataProto.DataType.Builder builder) {
                d();
                ((RegisterForDataNotificationsRequest) this.f4444a).addDataTypes(builder.build());
                return this;
            }

            public Builder addDataTypes(int i2, DataProto.DataType.Builder builder) {
                d();
                ((RegisterForDataNotificationsRequest) this.f4444a).addDataTypes(i2, builder.build());
                return this;
            }
        }

        static {
            RegisterForDataNotificationsRequest registerForDataNotificationsRequest = new RegisterForDataNotificationsRequest();
            DEFAULT_INSTANCE = registerForDataNotificationsRequest;
            GeneratedMessageLite.T(RegisterForDataNotificationsRequest.class, registerForDataNotificationsRequest);
        }

        private RegisterForDataNotificationsRequest() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addAllDataTypes(Iterable<? extends DataProto.DataType> iterable) {
            ensureDataTypesIsMutable();
            AbstractMessageLite.a(iterable, this.dataTypes_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addDataTypes(DataProto.DataType dataType) {
            dataType.getClass();
            ensureDataTypesIsMutable();
            this.dataTypes_.add(dataType);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearDataTypes() {
            this.dataTypes_ = GeneratedMessageLite.r();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearNotificationIntentAction() {
            this.bitField0_ &= -2;
            this.notificationIntentAction_ = getDefaultInstance().getNotificationIntentAction();
        }

        private void ensureDataTypesIsMutable() {
            Internal.ProtobufList<DataProto.DataType> protobufList = this.dataTypes_;
            if (protobufList.isModifiable()) {
                return;
            }
            this.dataTypes_ = GeneratedMessageLite.C(protobufList);
        }

        public static RegisterForDataNotificationsRequest getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.m();
        }

        public static RegisterForDataNotificationsRequest parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (RegisterForDataNotificationsRequest) GeneratedMessageLite.F(DEFAULT_INSTANCE, inputStream);
        }

        public static RegisterForDataNotificationsRequest parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (RegisterForDataNotificationsRequest) GeneratedMessageLite.N(DEFAULT_INSTANCE, byteBuffer);
        }

        public static Parser<RegisterForDataNotificationsRequest> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void removeDataTypes(int i2) {
            ensureDataTypesIsMutable();
            this.dataTypes_.remove(i2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setDataTypes(int i2, DataProto.DataType dataType) {
            dataType.getClass();
            ensureDataTypesIsMutable();
            this.dataTypes_.set(i2, dataType);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setNotificationIntentAction(String str) {
            str.getClass();
            this.bitField0_ |= 1;
            this.notificationIntentAction_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setNotificationIntentActionBytes(ByteString byteString) {
            this.notificationIntentAction_ = byteString.toStringUtf8();
            this.bitField0_ |= 1;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.RegisterForDataNotificationsRequestOrBuilder
        public DataProto.DataType getDataTypes(int i2) {
            return this.dataTypes_.get(i2);
        }

        @Override // androidx.health.platform.client.proto.RequestProto.RegisterForDataNotificationsRequestOrBuilder
        public int getDataTypesCount() {
            return this.dataTypes_.size();
        }

        @Override // androidx.health.platform.client.proto.RequestProto.RegisterForDataNotificationsRequestOrBuilder
        public List<DataProto.DataType> getDataTypesList() {
            return this.dataTypes_;
        }

        public DataProto.DataTypeOrBuilder getDataTypesOrBuilder(int i2) {
            return this.dataTypes_.get(i2);
        }

        public List<? extends DataProto.DataTypeOrBuilder> getDataTypesOrBuilderList() {
            return this.dataTypes_;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.RegisterForDataNotificationsRequestOrBuilder
        public String getNotificationIntentAction() {
            return this.notificationIntentAction_;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.RegisterForDataNotificationsRequestOrBuilder
        public ByteString getNotificationIntentActionBytes() {
            return ByteString.copyFromUtf8(this.notificationIntentAction_);
        }

        @Override // androidx.health.platform.client.proto.RequestProto.RegisterForDataNotificationsRequestOrBuilder
        public boolean hasNotificationIntentAction() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override // androidx.health.platform.client.proto.GeneratedMessageLite
        protected final Object q(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            AnonymousClass1 anonymousClass1 = null;
            switch (AnonymousClass1.f4477a[methodToInvoke.ordinal()]) {
                case 1:
                    return new RegisterForDataNotificationsRequest();
                case 2:
                    return new Builder(anonymousClass1);
                case 3:
                    return GeneratedMessageLite.D(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0001\u0000\u0001ဈ\u0000\u0002\u001b", new Object[]{"bitField0_", "notificationIntentAction_", "dataTypes_", DataProto.DataType.class});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<RegisterForDataNotificationsRequest> defaultInstanceBasedParser = PARSER;
                    if (defaultInstanceBasedParser == null) {
                        synchronized (RegisterForDataNotificationsRequest.class) {
                            try {
                                defaultInstanceBasedParser = PARSER;
                                if (defaultInstanceBasedParser == null) {
                                    defaultInstanceBasedParser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                    PARSER = defaultInstanceBasedParser;
                                }
                            } finally {
                            }
                        }
                    }
                    return defaultInstanceBasedParser;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public static Builder newBuilder(RegisterForDataNotificationsRequest registerForDataNotificationsRequest) {
            return (Builder) DEFAULT_INSTANCE.n(registerForDataNotificationsRequest);
        }

        public static RegisterForDataNotificationsRequest parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (RegisterForDataNotificationsRequest) GeneratedMessageLite.G(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static RegisterForDataNotificationsRequest parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (RegisterForDataNotificationsRequest) GeneratedMessageLite.O(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static RegisterForDataNotificationsRequest parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (RegisterForDataNotificationsRequest) GeneratedMessageLite.H(DEFAULT_INSTANCE, byteString);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addDataTypes(int i2, DataProto.DataType dataType) {
            dataType.getClass();
            ensureDataTypesIsMutable();
            this.dataTypes_.add(i2, dataType);
        }

        public static RegisterForDataNotificationsRequest parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (RegisterForDataNotificationsRequest) GeneratedMessageLite.I(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static RegisterForDataNotificationsRequest parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (RegisterForDataNotificationsRequest) GeneratedMessageLite.P(DEFAULT_INSTANCE, bArr);
        }

        public static RegisterForDataNotificationsRequest parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (RegisterForDataNotificationsRequest) GeneratedMessageLite.Q(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static RegisterForDataNotificationsRequest parseFrom(InputStream inputStream) throws IOException {
            return (RegisterForDataNotificationsRequest) GeneratedMessageLite.L(DEFAULT_INSTANCE, inputStream);
        }

        public static RegisterForDataNotificationsRequest parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (RegisterForDataNotificationsRequest) GeneratedMessageLite.M(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static RegisterForDataNotificationsRequest parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (RegisterForDataNotificationsRequest) GeneratedMessageLite.J(DEFAULT_INSTANCE, codedInputStream);
        }

        public static RegisterForDataNotificationsRequest parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (RegisterForDataNotificationsRequest) GeneratedMessageLite.K(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }
    }

    public interface RegisterForDataNotificationsRequestOrBuilder extends MessageLiteOrBuilder {
        DataProto.DataType getDataTypes(int i2);

        int getDataTypesCount();

        List<DataProto.DataType> getDataTypesList();

        String getNotificationIntentAction();

        ByteString getNotificationIntentActionBytes();

        boolean hasNotificationIntentAction();
    }

    public static final class RequestContext extends GeneratedMessageLite<RequestContext, Builder> implements RequestContextOrBuilder {
        public static final int CALLING_PACKAGE_FIELD_NUMBER = 1;
        private static final RequestContext DEFAULT_INSTANCE;
        public static final int IS_IN_FOREGROUND_FIELD_NUMBER = 4;
        private static volatile Parser<RequestContext> PARSER = null;
        public static final int PERMISSION_TOKEN_FIELD_NUMBER = 3;
        public static final int SDK_VERSION_FIELD_NUMBER = 2;
        private int bitField0_;
        private boolean isInForeground_;
        private int sdkVersion_;
        private String callingPackage_ = "";
        private String permissionToken_ = "";

        public static final class Builder extends GeneratedMessageLite.Builder<RequestContext, Builder> implements RequestContextOrBuilder {
            /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
                this();
            }

            public Builder clearCallingPackage() {
                d();
                ((RequestContext) this.f4444a).clearCallingPackage();
                return this;
            }

            public Builder clearIsInForeground() {
                d();
                ((RequestContext) this.f4444a).clearIsInForeground();
                return this;
            }

            public Builder clearPermissionToken() {
                d();
                ((RequestContext) this.f4444a).clearPermissionToken();
                return this;
            }

            public Builder clearSdkVersion() {
                d();
                ((RequestContext) this.f4444a).clearSdkVersion();
                return this;
            }

            @Override // androidx.health.platform.client.proto.RequestProto.RequestContextOrBuilder
            public String getCallingPackage() {
                return ((RequestContext) this.f4444a).getCallingPackage();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.RequestContextOrBuilder
            public ByteString getCallingPackageBytes() {
                return ((RequestContext) this.f4444a).getCallingPackageBytes();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.RequestContextOrBuilder
            public boolean getIsInForeground() {
                return ((RequestContext) this.f4444a).getIsInForeground();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.RequestContextOrBuilder
            public String getPermissionToken() {
                return ((RequestContext) this.f4444a).getPermissionToken();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.RequestContextOrBuilder
            public ByteString getPermissionTokenBytes() {
                return ((RequestContext) this.f4444a).getPermissionTokenBytes();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.RequestContextOrBuilder
            public int getSdkVersion() {
                return ((RequestContext) this.f4444a).getSdkVersion();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.RequestContextOrBuilder
            public boolean hasCallingPackage() {
                return ((RequestContext) this.f4444a).hasCallingPackage();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.RequestContextOrBuilder
            public boolean hasIsInForeground() {
                return ((RequestContext) this.f4444a).hasIsInForeground();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.RequestContextOrBuilder
            public boolean hasPermissionToken() {
                return ((RequestContext) this.f4444a).hasPermissionToken();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.RequestContextOrBuilder
            public boolean hasSdkVersion() {
                return ((RequestContext) this.f4444a).hasSdkVersion();
            }

            public Builder setCallingPackage(String str) {
                d();
                ((RequestContext) this.f4444a).setCallingPackage(str);
                return this;
            }

            public Builder setCallingPackageBytes(ByteString byteString) {
                d();
                ((RequestContext) this.f4444a).setCallingPackageBytes(byteString);
                return this;
            }

            public Builder setIsInForeground(boolean z2) {
                d();
                ((RequestContext) this.f4444a).setIsInForeground(z2);
                return this;
            }

            public Builder setPermissionToken(String str) {
                d();
                ((RequestContext) this.f4444a).setPermissionToken(str);
                return this;
            }

            public Builder setPermissionTokenBytes(ByteString byteString) {
                d();
                ((RequestContext) this.f4444a).setPermissionTokenBytes(byteString);
                return this;
            }

            public Builder setSdkVersion(int i2) {
                d();
                ((RequestContext) this.f4444a).setSdkVersion(i2);
                return this;
            }

            private Builder() {
                super(RequestContext.DEFAULT_INSTANCE);
            }
        }

        static {
            RequestContext requestContext = new RequestContext();
            DEFAULT_INSTANCE = requestContext;
            GeneratedMessageLite.T(RequestContext.class, requestContext);
        }

        private RequestContext() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearCallingPackage() {
            this.bitField0_ &= -2;
            this.callingPackage_ = getDefaultInstance().getCallingPackage();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearIsInForeground() {
            this.bitField0_ &= -9;
            this.isInForeground_ = false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearPermissionToken() {
            this.bitField0_ &= -5;
            this.permissionToken_ = getDefaultInstance().getPermissionToken();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearSdkVersion() {
            this.bitField0_ &= -3;
            this.sdkVersion_ = 0;
        }

        public static RequestContext getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.m();
        }

        public static RequestContext parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (RequestContext) GeneratedMessageLite.F(DEFAULT_INSTANCE, inputStream);
        }

        public static RequestContext parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (RequestContext) GeneratedMessageLite.N(DEFAULT_INSTANCE, byteBuffer);
        }

        public static Parser<RequestContext> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setCallingPackage(String str) {
            str.getClass();
            this.bitField0_ |= 1;
            this.callingPackage_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setCallingPackageBytes(ByteString byteString) {
            this.callingPackage_ = byteString.toStringUtf8();
            this.bitField0_ |= 1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setIsInForeground(boolean z2) {
            this.bitField0_ |= 8;
            this.isInForeground_ = z2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setPermissionToken(String str) {
            str.getClass();
            this.bitField0_ |= 4;
            this.permissionToken_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setPermissionTokenBytes(ByteString byteString) {
            this.permissionToken_ = byteString.toStringUtf8();
            this.bitField0_ |= 4;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setSdkVersion(int i2) {
            this.bitField0_ |= 2;
            this.sdkVersion_ = i2;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.RequestContextOrBuilder
        public String getCallingPackage() {
            return this.callingPackage_;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.RequestContextOrBuilder
        public ByteString getCallingPackageBytes() {
            return ByteString.copyFromUtf8(this.callingPackage_);
        }

        @Override // androidx.health.platform.client.proto.RequestProto.RequestContextOrBuilder
        public boolean getIsInForeground() {
            return this.isInForeground_;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.RequestContextOrBuilder
        public String getPermissionToken() {
            return this.permissionToken_;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.RequestContextOrBuilder
        public ByteString getPermissionTokenBytes() {
            return ByteString.copyFromUtf8(this.permissionToken_);
        }

        @Override // androidx.health.platform.client.proto.RequestProto.RequestContextOrBuilder
        public int getSdkVersion() {
            return this.sdkVersion_;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.RequestContextOrBuilder
        public boolean hasCallingPackage() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.RequestContextOrBuilder
        public boolean hasIsInForeground() {
            return (this.bitField0_ & 8) != 0;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.RequestContextOrBuilder
        public boolean hasPermissionToken() {
            return (this.bitField0_ & 4) != 0;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.RequestContextOrBuilder
        public boolean hasSdkVersion() {
            return (this.bitField0_ & 2) != 0;
        }

        @Override // androidx.health.platform.client.proto.GeneratedMessageLite
        protected final Object q(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            AnonymousClass1 anonymousClass1 = null;
            switch (AnonymousClass1.f4477a[methodToInvoke.ordinal()]) {
                case 1:
                    return new RequestContext();
                case 2:
                    return new Builder(anonymousClass1);
                case 3:
                    return GeneratedMessageLite.D(DEFAULT_INSTANCE, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001ဈ\u0000\u0002င\u0001\u0003ဈ\u0002\u0004ဇ\u0003", new Object[]{"bitField0_", "callingPackage_", "sdkVersion_", "permissionToken_", "isInForeground_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<RequestContext> defaultInstanceBasedParser = PARSER;
                    if (defaultInstanceBasedParser == null) {
                        synchronized (RequestContext.class) {
                            try {
                                defaultInstanceBasedParser = PARSER;
                                if (defaultInstanceBasedParser == null) {
                                    defaultInstanceBasedParser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                    PARSER = defaultInstanceBasedParser;
                                }
                            } finally {
                            }
                        }
                    }
                    return defaultInstanceBasedParser;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public static Builder newBuilder(RequestContext requestContext) {
            return (Builder) DEFAULT_INSTANCE.n(requestContext);
        }

        public static RequestContext parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (RequestContext) GeneratedMessageLite.G(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static RequestContext parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (RequestContext) GeneratedMessageLite.O(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static RequestContext parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (RequestContext) GeneratedMessageLite.H(DEFAULT_INSTANCE, byteString);
        }

        public static RequestContext parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (RequestContext) GeneratedMessageLite.I(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static RequestContext parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (RequestContext) GeneratedMessageLite.P(DEFAULT_INSTANCE, bArr);
        }

        public static RequestContext parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (RequestContext) GeneratedMessageLite.Q(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static RequestContext parseFrom(InputStream inputStream) throws IOException {
            return (RequestContext) GeneratedMessageLite.L(DEFAULT_INSTANCE, inputStream);
        }

        public static RequestContext parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (RequestContext) GeneratedMessageLite.M(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static RequestContext parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (RequestContext) GeneratedMessageLite.J(DEFAULT_INSTANCE, codedInputStream);
        }

        public static RequestContext parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (RequestContext) GeneratedMessageLite.K(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }
    }

    public interface RequestContextOrBuilder extends MessageLiteOrBuilder {
        String getCallingPackage();

        ByteString getCallingPackageBytes();

        boolean getIsInForeground();

        String getPermissionToken();

        ByteString getPermissionTokenBytes();

        int getSdkVersion();

        boolean hasCallingPackage();

        boolean hasIsInForeground();

        boolean hasPermissionToken();

        boolean hasSdkVersion();
    }

    public static final class SimpleDataRequest extends GeneratedMessageLite<SimpleDataRequest, Builder> implements SimpleDataRequestOrBuilder {
        public static final int DATA_POINT_FIELD_NUMBER = 1;
        private static final SimpleDataRequest DEFAULT_INSTANCE;
        private static volatile Parser<SimpleDataRequest> PARSER;
        private int bitField0_;
        private DataProto.DataPoint dataPoint_;

        public static final class Builder extends GeneratedMessageLite.Builder<SimpleDataRequest, Builder> implements SimpleDataRequestOrBuilder {
            /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
                this();
            }

            public Builder clearDataPoint() {
                d();
                ((SimpleDataRequest) this.f4444a).clearDataPoint();
                return this;
            }

            @Override // androidx.health.platform.client.proto.RequestProto.SimpleDataRequestOrBuilder
            public DataProto.DataPoint getDataPoint() {
                return ((SimpleDataRequest) this.f4444a).getDataPoint();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.SimpleDataRequestOrBuilder
            public boolean hasDataPoint() {
                return ((SimpleDataRequest) this.f4444a).hasDataPoint();
            }

            public Builder mergeDataPoint(DataProto.DataPoint dataPoint) {
                d();
                ((SimpleDataRequest) this.f4444a).mergeDataPoint(dataPoint);
                return this;
            }

            public Builder setDataPoint(DataProto.DataPoint dataPoint) {
                d();
                ((SimpleDataRequest) this.f4444a).setDataPoint(dataPoint);
                return this;
            }

            private Builder() {
                super(SimpleDataRequest.DEFAULT_INSTANCE);
            }

            public Builder setDataPoint(DataProto.DataPoint.Builder builder) {
                d();
                ((SimpleDataRequest) this.f4444a).setDataPoint(builder.build());
                return this;
            }
        }

        static {
            SimpleDataRequest simpleDataRequest = new SimpleDataRequest();
            DEFAULT_INSTANCE = simpleDataRequest;
            GeneratedMessageLite.T(SimpleDataRequest.class, simpleDataRequest);
        }

        private SimpleDataRequest() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearDataPoint() {
            this.dataPoint_ = null;
            this.bitField0_ &= -2;
        }

        public static SimpleDataRequest getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mergeDataPoint(DataProto.DataPoint dataPoint) {
            dataPoint.getClass();
            DataProto.DataPoint dataPoint2 = this.dataPoint_;
            if (dataPoint2 == null || dataPoint2 == DataProto.DataPoint.getDefaultInstance()) {
                this.dataPoint_ = dataPoint;
            } else {
                this.dataPoint_ = DataProto.DataPoint.newBuilder(this.dataPoint_).mergeFrom((DataProto.DataPoint.Builder) dataPoint).buildPartial();
            }
            this.bitField0_ |= 1;
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.m();
        }

        public static SimpleDataRequest parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (SimpleDataRequest) GeneratedMessageLite.F(DEFAULT_INSTANCE, inputStream);
        }

        public static SimpleDataRequest parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (SimpleDataRequest) GeneratedMessageLite.N(DEFAULT_INSTANCE, byteBuffer);
        }

        public static Parser<SimpleDataRequest> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setDataPoint(DataProto.DataPoint dataPoint) {
            dataPoint.getClass();
            this.dataPoint_ = dataPoint;
            this.bitField0_ |= 1;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.SimpleDataRequestOrBuilder
        public DataProto.DataPoint getDataPoint() {
            DataProto.DataPoint dataPoint = this.dataPoint_;
            return dataPoint == null ? DataProto.DataPoint.getDefaultInstance() : dataPoint;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.SimpleDataRequestOrBuilder
        public boolean hasDataPoint() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override // androidx.health.platform.client.proto.GeneratedMessageLite
        protected final Object q(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            AnonymousClass1 anonymousClass1 = null;
            switch (AnonymousClass1.f4477a[methodToInvoke.ordinal()]) {
                case 1:
                    return new SimpleDataRequest();
                case 2:
                    return new Builder(anonymousClass1);
                case 3:
                    return GeneratedMessageLite.D(DEFAULT_INSTANCE, "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001ဉ\u0000", new Object[]{"bitField0_", "dataPoint_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<SimpleDataRequest> defaultInstanceBasedParser = PARSER;
                    if (defaultInstanceBasedParser == null) {
                        synchronized (SimpleDataRequest.class) {
                            try {
                                defaultInstanceBasedParser = PARSER;
                                if (defaultInstanceBasedParser == null) {
                                    defaultInstanceBasedParser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                    PARSER = defaultInstanceBasedParser;
                                }
                            } finally {
                            }
                        }
                    }
                    return defaultInstanceBasedParser;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public static Builder newBuilder(SimpleDataRequest simpleDataRequest) {
            return (Builder) DEFAULT_INSTANCE.n(simpleDataRequest);
        }

        public static SimpleDataRequest parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (SimpleDataRequest) GeneratedMessageLite.G(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static SimpleDataRequest parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (SimpleDataRequest) GeneratedMessageLite.O(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static SimpleDataRequest parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (SimpleDataRequest) GeneratedMessageLite.H(DEFAULT_INSTANCE, byteString);
        }

        public static SimpleDataRequest parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (SimpleDataRequest) GeneratedMessageLite.I(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static SimpleDataRequest parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (SimpleDataRequest) GeneratedMessageLite.P(DEFAULT_INSTANCE, bArr);
        }

        public static SimpleDataRequest parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (SimpleDataRequest) GeneratedMessageLite.Q(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static SimpleDataRequest parseFrom(InputStream inputStream) throws IOException {
            return (SimpleDataRequest) GeneratedMessageLite.L(DEFAULT_INSTANCE, inputStream);
        }

        public static SimpleDataRequest parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (SimpleDataRequest) GeneratedMessageLite.M(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static SimpleDataRequest parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (SimpleDataRequest) GeneratedMessageLite.J(DEFAULT_INSTANCE, codedInputStream);
        }

        public static SimpleDataRequest parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (SimpleDataRequest) GeneratedMessageLite.K(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }
    }

    public interface SimpleDataRequestOrBuilder extends MessageLiteOrBuilder {
        DataProto.DataPoint getDataPoint();

        boolean hasDataPoint();
    }

    public static final class UnregisterFromDataNotificationsRequest extends GeneratedMessageLite<UnregisterFromDataNotificationsRequest, Builder> implements UnregisterFromDataNotificationsRequestOrBuilder {
        private static final UnregisterFromDataNotificationsRequest DEFAULT_INSTANCE;
        public static final int NOTIFICATIONINTENTACTION_FIELD_NUMBER = 1;
        private static volatile Parser<UnregisterFromDataNotificationsRequest> PARSER;
        private int bitField0_;
        private String notificationIntentAction_ = "";

        public static final class Builder extends GeneratedMessageLite.Builder<UnregisterFromDataNotificationsRequest, Builder> implements UnregisterFromDataNotificationsRequestOrBuilder {
            /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
                this();
            }

            public Builder clearNotificationIntentAction() {
                d();
                ((UnregisterFromDataNotificationsRequest) this.f4444a).clearNotificationIntentAction();
                return this;
            }

            @Override // androidx.health.platform.client.proto.RequestProto.UnregisterFromDataNotificationsRequestOrBuilder
            public String getNotificationIntentAction() {
                return ((UnregisterFromDataNotificationsRequest) this.f4444a).getNotificationIntentAction();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.UnregisterFromDataNotificationsRequestOrBuilder
            public ByteString getNotificationIntentActionBytes() {
                return ((UnregisterFromDataNotificationsRequest) this.f4444a).getNotificationIntentActionBytes();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.UnregisterFromDataNotificationsRequestOrBuilder
            public boolean hasNotificationIntentAction() {
                return ((UnregisterFromDataNotificationsRequest) this.f4444a).hasNotificationIntentAction();
            }

            public Builder setNotificationIntentAction(String str) {
                d();
                ((UnregisterFromDataNotificationsRequest) this.f4444a).setNotificationIntentAction(str);
                return this;
            }

            public Builder setNotificationIntentActionBytes(ByteString byteString) {
                d();
                ((UnregisterFromDataNotificationsRequest) this.f4444a).setNotificationIntentActionBytes(byteString);
                return this;
            }

            private Builder() {
                super(UnregisterFromDataNotificationsRequest.DEFAULT_INSTANCE);
            }
        }

        static {
            UnregisterFromDataNotificationsRequest unregisterFromDataNotificationsRequest = new UnregisterFromDataNotificationsRequest();
            DEFAULT_INSTANCE = unregisterFromDataNotificationsRequest;
            GeneratedMessageLite.T(UnregisterFromDataNotificationsRequest.class, unregisterFromDataNotificationsRequest);
        }

        private UnregisterFromDataNotificationsRequest() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearNotificationIntentAction() {
            this.bitField0_ &= -2;
            this.notificationIntentAction_ = getDefaultInstance().getNotificationIntentAction();
        }

        public static UnregisterFromDataNotificationsRequest getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.m();
        }

        public static UnregisterFromDataNotificationsRequest parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (UnregisterFromDataNotificationsRequest) GeneratedMessageLite.F(DEFAULT_INSTANCE, inputStream);
        }

        public static UnregisterFromDataNotificationsRequest parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (UnregisterFromDataNotificationsRequest) GeneratedMessageLite.N(DEFAULT_INSTANCE, byteBuffer);
        }

        public static Parser<UnregisterFromDataNotificationsRequest> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setNotificationIntentAction(String str) {
            str.getClass();
            this.bitField0_ |= 1;
            this.notificationIntentAction_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setNotificationIntentActionBytes(ByteString byteString) {
            this.notificationIntentAction_ = byteString.toStringUtf8();
            this.bitField0_ |= 1;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.UnregisterFromDataNotificationsRequestOrBuilder
        public String getNotificationIntentAction() {
            return this.notificationIntentAction_;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.UnregisterFromDataNotificationsRequestOrBuilder
        public ByteString getNotificationIntentActionBytes() {
            return ByteString.copyFromUtf8(this.notificationIntentAction_);
        }

        @Override // androidx.health.platform.client.proto.RequestProto.UnregisterFromDataNotificationsRequestOrBuilder
        public boolean hasNotificationIntentAction() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override // androidx.health.platform.client.proto.GeneratedMessageLite
        protected final Object q(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            AnonymousClass1 anonymousClass1 = null;
            switch (AnonymousClass1.f4477a[methodToInvoke.ordinal()]) {
                case 1:
                    return new UnregisterFromDataNotificationsRequest();
                case 2:
                    return new Builder(anonymousClass1);
                case 3:
                    return GeneratedMessageLite.D(DEFAULT_INSTANCE, "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001ဈ\u0000", new Object[]{"bitField0_", "notificationIntentAction_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<UnregisterFromDataNotificationsRequest> defaultInstanceBasedParser = PARSER;
                    if (defaultInstanceBasedParser == null) {
                        synchronized (UnregisterFromDataNotificationsRequest.class) {
                            try {
                                defaultInstanceBasedParser = PARSER;
                                if (defaultInstanceBasedParser == null) {
                                    defaultInstanceBasedParser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                    PARSER = defaultInstanceBasedParser;
                                }
                            } finally {
                            }
                        }
                    }
                    return defaultInstanceBasedParser;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public static Builder newBuilder(UnregisterFromDataNotificationsRequest unregisterFromDataNotificationsRequest) {
            return (Builder) DEFAULT_INSTANCE.n(unregisterFromDataNotificationsRequest);
        }

        public static UnregisterFromDataNotificationsRequest parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (UnregisterFromDataNotificationsRequest) GeneratedMessageLite.G(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static UnregisterFromDataNotificationsRequest parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (UnregisterFromDataNotificationsRequest) GeneratedMessageLite.O(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static UnregisterFromDataNotificationsRequest parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (UnregisterFromDataNotificationsRequest) GeneratedMessageLite.H(DEFAULT_INSTANCE, byteString);
        }

        public static UnregisterFromDataNotificationsRequest parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (UnregisterFromDataNotificationsRequest) GeneratedMessageLite.I(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static UnregisterFromDataNotificationsRequest parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (UnregisterFromDataNotificationsRequest) GeneratedMessageLite.P(DEFAULT_INSTANCE, bArr);
        }

        public static UnregisterFromDataNotificationsRequest parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (UnregisterFromDataNotificationsRequest) GeneratedMessageLite.Q(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static UnregisterFromDataNotificationsRequest parseFrom(InputStream inputStream) throws IOException {
            return (UnregisterFromDataNotificationsRequest) GeneratedMessageLite.L(DEFAULT_INSTANCE, inputStream);
        }

        public static UnregisterFromDataNotificationsRequest parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (UnregisterFromDataNotificationsRequest) GeneratedMessageLite.M(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static UnregisterFromDataNotificationsRequest parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (UnregisterFromDataNotificationsRequest) GeneratedMessageLite.J(DEFAULT_INSTANCE, codedInputStream);
        }

        public static UnregisterFromDataNotificationsRequest parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (UnregisterFromDataNotificationsRequest) GeneratedMessageLite.K(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }
    }

    public interface UnregisterFromDataNotificationsRequestOrBuilder extends MessageLiteOrBuilder {
        String getNotificationIntentAction();

        ByteString getNotificationIntentActionBytes();

        boolean hasNotificationIntentAction();
    }

    public static final class UpsertDataRequest extends GeneratedMessageLite<UpsertDataRequest, Builder> implements UpsertDataRequestOrBuilder {
        public static final int DATA_POINT_FIELD_NUMBER = 1;
        private static final UpsertDataRequest DEFAULT_INSTANCE;
        private static volatile Parser<UpsertDataRequest> PARSER;
        private Internal.ProtobufList<DataProto.DataPoint> dataPoint_ = GeneratedMessageLite.r();

        public static final class Builder extends GeneratedMessageLite.Builder<UpsertDataRequest, Builder> implements UpsertDataRequestOrBuilder {
            /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
                this();
            }

            public Builder addAllDataPoint(Iterable<? extends DataProto.DataPoint> iterable) {
                d();
                ((UpsertDataRequest) this.f4444a).addAllDataPoint(iterable);
                return this;
            }

            public Builder addDataPoint(DataProto.DataPoint dataPoint) {
                d();
                ((UpsertDataRequest) this.f4444a).addDataPoint(dataPoint);
                return this;
            }

            public Builder clearDataPoint() {
                d();
                ((UpsertDataRequest) this.f4444a).clearDataPoint();
                return this;
            }

            @Override // androidx.health.platform.client.proto.RequestProto.UpsertDataRequestOrBuilder
            public DataProto.DataPoint getDataPoint(int i2) {
                return ((UpsertDataRequest) this.f4444a).getDataPoint(i2);
            }

            @Override // androidx.health.platform.client.proto.RequestProto.UpsertDataRequestOrBuilder
            public int getDataPointCount() {
                return ((UpsertDataRequest) this.f4444a).getDataPointCount();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.UpsertDataRequestOrBuilder
            public List<DataProto.DataPoint> getDataPointList() {
                return Collections.unmodifiableList(((UpsertDataRequest) this.f4444a).getDataPointList());
            }

            public Builder removeDataPoint(int i2) {
                d();
                ((UpsertDataRequest) this.f4444a).removeDataPoint(i2);
                return this;
            }

            public Builder setDataPoint(int i2, DataProto.DataPoint dataPoint) {
                d();
                ((UpsertDataRequest) this.f4444a).setDataPoint(i2, dataPoint);
                return this;
            }

            private Builder() {
                super(UpsertDataRequest.DEFAULT_INSTANCE);
            }

            public Builder addDataPoint(int i2, DataProto.DataPoint dataPoint) {
                d();
                ((UpsertDataRequest) this.f4444a).addDataPoint(i2, dataPoint);
                return this;
            }

            public Builder setDataPoint(int i2, DataProto.DataPoint.Builder builder) {
                d();
                ((UpsertDataRequest) this.f4444a).setDataPoint(i2, builder.build());
                return this;
            }

            public Builder addDataPoint(DataProto.DataPoint.Builder builder) {
                d();
                ((UpsertDataRequest) this.f4444a).addDataPoint(builder.build());
                return this;
            }

            public Builder addDataPoint(int i2, DataProto.DataPoint.Builder builder) {
                d();
                ((UpsertDataRequest) this.f4444a).addDataPoint(i2, builder.build());
                return this;
            }
        }

        static {
            UpsertDataRequest upsertDataRequest = new UpsertDataRequest();
            DEFAULT_INSTANCE = upsertDataRequest;
            GeneratedMessageLite.T(UpsertDataRequest.class, upsertDataRequest);
        }

        private UpsertDataRequest() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addAllDataPoint(Iterable<? extends DataProto.DataPoint> iterable) {
            ensureDataPointIsMutable();
            AbstractMessageLite.a(iterable, this.dataPoint_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addDataPoint(DataProto.DataPoint dataPoint) {
            dataPoint.getClass();
            ensureDataPointIsMutable();
            this.dataPoint_.add(dataPoint);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearDataPoint() {
            this.dataPoint_ = GeneratedMessageLite.r();
        }

        private void ensureDataPointIsMutable() {
            Internal.ProtobufList<DataProto.DataPoint> protobufList = this.dataPoint_;
            if (protobufList.isModifiable()) {
                return;
            }
            this.dataPoint_ = GeneratedMessageLite.C(protobufList);
        }

        public static UpsertDataRequest getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.m();
        }

        public static UpsertDataRequest parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (UpsertDataRequest) GeneratedMessageLite.F(DEFAULT_INSTANCE, inputStream);
        }

        public static UpsertDataRequest parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (UpsertDataRequest) GeneratedMessageLite.N(DEFAULT_INSTANCE, byteBuffer);
        }

        public static Parser<UpsertDataRequest> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void removeDataPoint(int i2) {
            ensureDataPointIsMutable();
            this.dataPoint_.remove(i2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setDataPoint(int i2, DataProto.DataPoint dataPoint) {
            dataPoint.getClass();
            ensureDataPointIsMutable();
            this.dataPoint_.set(i2, dataPoint);
        }

        @Override // androidx.health.platform.client.proto.RequestProto.UpsertDataRequestOrBuilder
        public DataProto.DataPoint getDataPoint(int i2) {
            return this.dataPoint_.get(i2);
        }

        @Override // androidx.health.platform.client.proto.RequestProto.UpsertDataRequestOrBuilder
        public int getDataPointCount() {
            return this.dataPoint_.size();
        }

        @Override // androidx.health.platform.client.proto.RequestProto.UpsertDataRequestOrBuilder
        public List<DataProto.DataPoint> getDataPointList() {
            return this.dataPoint_;
        }

        public DataProto.DataPointOrBuilder getDataPointOrBuilder(int i2) {
            return this.dataPoint_.get(i2);
        }

        public List<? extends DataProto.DataPointOrBuilder> getDataPointOrBuilderList() {
            return this.dataPoint_;
        }

        @Override // androidx.health.platform.client.proto.GeneratedMessageLite
        protected final Object q(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            AnonymousClass1 anonymousClass1 = null;
            switch (AnonymousClass1.f4477a[methodToInvoke.ordinal()]) {
                case 1:
                    return new UpsertDataRequest();
                case 2:
                    return new Builder(anonymousClass1);
                case 3:
                    return GeneratedMessageLite.D(DEFAULT_INSTANCE, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b", new Object[]{"dataPoint_", DataProto.DataPoint.class});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<UpsertDataRequest> defaultInstanceBasedParser = PARSER;
                    if (defaultInstanceBasedParser == null) {
                        synchronized (UpsertDataRequest.class) {
                            try {
                                defaultInstanceBasedParser = PARSER;
                                if (defaultInstanceBasedParser == null) {
                                    defaultInstanceBasedParser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                    PARSER = defaultInstanceBasedParser;
                                }
                            } finally {
                            }
                        }
                    }
                    return defaultInstanceBasedParser;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public static Builder newBuilder(UpsertDataRequest upsertDataRequest) {
            return (Builder) DEFAULT_INSTANCE.n(upsertDataRequest);
        }

        public static UpsertDataRequest parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (UpsertDataRequest) GeneratedMessageLite.G(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static UpsertDataRequest parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (UpsertDataRequest) GeneratedMessageLite.O(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static UpsertDataRequest parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (UpsertDataRequest) GeneratedMessageLite.H(DEFAULT_INSTANCE, byteString);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addDataPoint(int i2, DataProto.DataPoint dataPoint) {
            dataPoint.getClass();
            ensureDataPointIsMutable();
            this.dataPoint_.add(i2, dataPoint);
        }

        public static UpsertDataRequest parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (UpsertDataRequest) GeneratedMessageLite.I(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static UpsertDataRequest parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (UpsertDataRequest) GeneratedMessageLite.P(DEFAULT_INSTANCE, bArr);
        }

        public static UpsertDataRequest parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (UpsertDataRequest) GeneratedMessageLite.Q(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static UpsertDataRequest parseFrom(InputStream inputStream) throws IOException {
            return (UpsertDataRequest) GeneratedMessageLite.L(DEFAULT_INSTANCE, inputStream);
        }

        public static UpsertDataRequest parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (UpsertDataRequest) GeneratedMessageLite.M(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static UpsertDataRequest parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (UpsertDataRequest) GeneratedMessageLite.J(DEFAULT_INSTANCE, codedInputStream);
        }

        public static UpsertDataRequest parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (UpsertDataRequest) GeneratedMessageLite.K(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }
    }

    public interface UpsertDataRequestOrBuilder extends MessageLiteOrBuilder {
        DataProto.DataPoint getDataPoint(int i2);

        int getDataPointCount();

        List<DataProto.DataPoint> getDataPointList();
    }

    public static final class UpsertExerciseRouteRequest extends GeneratedMessageLite<UpsertExerciseRouteRequest, Builder> implements UpsertExerciseRouteRequestOrBuilder {
        private static final UpsertExerciseRouteRequest DEFAULT_INSTANCE;
        public static final int EXERCISEROUTE_FIELD_NUMBER = 2;
        private static volatile Parser<UpsertExerciseRouteRequest> PARSER = null;
        public static final int SESSIONUID_FIELD_NUMBER = 1;
        private int bitField0_;
        private DataProto.DataPoint exerciseRoute_;
        private String sessionUid_ = "";

        public static final class Builder extends GeneratedMessageLite.Builder<UpsertExerciseRouteRequest, Builder> implements UpsertExerciseRouteRequestOrBuilder {
            /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
                this();
            }

            public Builder clearExerciseRoute() {
                d();
                ((UpsertExerciseRouteRequest) this.f4444a).clearExerciseRoute();
                return this;
            }

            public Builder clearSessionUid() {
                d();
                ((UpsertExerciseRouteRequest) this.f4444a).clearSessionUid();
                return this;
            }

            @Override // androidx.health.platform.client.proto.RequestProto.UpsertExerciseRouteRequestOrBuilder
            public DataProto.DataPoint getExerciseRoute() {
                return ((UpsertExerciseRouteRequest) this.f4444a).getExerciseRoute();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.UpsertExerciseRouteRequestOrBuilder
            public String getSessionUid() {
                return ((UpsertExerciseRouteRequest) this.f4444a).getSessionUid();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.UpsertExerciseRouteRequestOrBuilder
            public ByteString getSessionUidBytes() {
                return ((UpsertExerciseRouteRequest) this.f4444a).getSessionUidBytes();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.UpsertExerciseRouteRequestOrBuilder
            public boolean hasExerciseRoute() {
                return ((UpsertExerciseRouteRequest) this.f4444a).hasExerciseRoute();
            }

            @Override // androidx.health.platform.client.proto.RequestProto.UpsertExerciseRouteRequestOrBuilder
            public boolean hasSessionUid() {
                return ((UpsertExerciseRouteRequest) this.f4444a).hasSessionUid();
            }

            public Builder mergeExerciseRoute(DataProto.DataPoint dataPoint) {
                d();
                ((UpsertExerciseRouteRequest) this.f4444a).mergeExerciseRoute(dataPoint);
                return this;
            }

            public Builder setExerciseRoute(DataProto.DataPoint dataPoint) {
                d();
                ((UpsertExerciseRouteRequest) this.f4444a).setExerciseRoute(dataPoint);
                return this;
            }

            public Builder setSessionUid(String str) {
                d();
                ((UpsertExerciseRouteRequest) this.f4444a).setSessionUid(str);
                return this;
            }

            public Builder setSessionUidBytes(ByteString byteString) {
                d();
                ((UpsertExerciseRouteRequest) this.f4444a).setSessionUidBytes(byteString);
                return this;
            }

            private Builder() {
                super(UpsertExerciseRouteRequest.DEFAULT_INSTANCE);
            }

            public Builder setExerciseRoute(DataProto.DataPoint.Builder builder) {
                d();
                ((UpsertExerciseRouteRequest) this.f4444a).setExerciseRoute(builder.build());
                return this;
            }
        }

        static {
            UpsertExerciseRouteRequest upsertExerciseRouteRequest = new UpsertExerciseRouteRequest();
            DEFAULT_INSTANCE = upsertExerciseRouteRequest;
            GeneratedMessageLite.T(UpsertExerciseRouteRequest.class, upsertExerciseRouteRequest);
        }

        private UpsertExerciseRouteRequest() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearExerciseRoute() {
            this.exerciseRoute_ = null;
            this.bitField0_ &= -3;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearSessionUid() {
            this.bitField0_ &= -2;
            this.sessionUid_ = getDefaultInstance().getSessionUid();
        }

        public static UpsertExerciseRouteRequest getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mergeExerciseRoute(DataProto.DataPoint dataPoint) {
            dataPoint.getClass();
            DataProto.DataPoint dataPoint2 = this.exerciseRoute_;
            if (dataPoint2 == null || dataPoint2 == DataProto.DataPoint.getDefaultInstance()) {
                this.exerciseRoute_ = dataPoint;
            } else {
                this.exerciseRoute_ = DataProto.DataPoint.newBuilder(this.exerciseRoute_).mergeFrom((DataProto.DataPoint.Builder) dataPoint).buildPartial();
            }
            this.bitField0_ |= 2;
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.m();
        }

        public static UpsertExerciseRouteRequest parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (UpsertExerciseRouteRequest) GeneratedMessageLite.F(DEFAULT_INSTANCE, inputStream);
        }

        public static UpsertExerciseRouteRequest parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (UpsertExerciseRouteRequest) GeneratedMessageLite.N(DEFAULT_INSTANCE, byteBuffer);
        }

        public static Parser<UpsertExerciseRouteRequest> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setExerciseRoute(DataProto.DataPoint dataPoint) {
            dataPoint.getClass();
            this.exerciseRoute_ = dataPoint;
            this.bitField0_ |= 2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setSessionUid(String str) {
            str.getClass();
            this.bitField0_ |= 1;
            this.sessionUid_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setSessionUidBytes(ByteString byteString) {
            this.sessionUid_ = byteString.toStringUtf8();
            this.bitField0_ |= 1;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.UpsertExerciseRouteRequestOrBuilder
        public DataProto.DataPoint getExerciseRoute() {
            DataProto.DataPoint dataPoint = this.exerciseRoute_;
            return dataPoint == null ? DataProto.DataPoint.getDefaultInstance() : dataPoint;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.UpsertExerciseRouteRequestOrBuilder
        public String getSessionUid() {
            return this.sessionUid_;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.UpsertExerciseRouteRequestOrBuilder
        public ByteString getSessionUidBytes() {
            return ByteString.copyFromUtf8(this.sessionUid_);
        }

        @Override // androidx.health.platform.client.proto.RequestProto.UpsertExerciseRouteRequestOrBuilder
        public boolean hasExerciseRoute() {
            return (this.bitField0_ & 2) != 0;
        }

        @Override // androidx.health.platform.client.proto.RequestProto.UpsertExerciseRouteRequestOrBuilder
        public boolean hasSessionUid() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override // androidx.health.platform.client.proto.GeneratedMessageLite
        protected final Object q(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            AnonymousClass1 anonymousClass1 = null;
            switch (AnonymousClass1.f4477a[methodToInvoke.ordinal()]) {
                case 1:
                    return new UpsertExerciseRouteRequest();
                case 2:
                    return new Builder(anonymousClass1);
                case 3:
                    return GeneratedMessageLite.D(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ဈ\u0000\u0002ဉ\u0001", new Object[]{"bitField0_", "sessionUid_", "exerciseRoute_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<UpsertExerciseRouteRequest> defaultInstanceBasedParser = PARSER;
                    if (defaultInstanceBasedParser == null) {
                        synchronized (UpsertExerciseRouteRequest.class) {
                            try {
                                defaultInstanceBasedParser = PARSER;
                                if (defaultInstanceBasedParser == null) {
                                    defaultInstanceBasedParser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                    PARSER = defaultInstanceBasedParser;
                                }
                            } finally {
                            }
                        }
                    }
                    return defaultInstanceBasedParser;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public static Builder newBuilder(UpsertExerciseRouteRequest upsertExerciseRouteRequest) {
            return (Builder) DEFAULT_INSTANCE.n(upsertExerciseRouteRequest);
        }

        public static UpsertExerciseRouteRequest parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (UpsertExerciseRouteRequest) GeneratedMessageLite.G(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static UpsertExerciseRouteRequest parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (UpsertExerciseRouteRequest) GeneratedMessageLite.O(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static UpsertExerciseRouteRequest parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (UpsertExerciseRouteRequest) GeneratedMessageLite.H(DEFAULT_INSTANCE, byteString);
        }

        public static UpsertExerciseRouteRequest parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (UpsertExerciseRouteRequest) GeneratedMessageLite.I(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static UpsertExerciseRouteRequest parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (UpsertExerciseRouteRequest) GeneratedMessageLite.P(DEFAULT_INSTANCE, bArr);
        }

        public static UpsertExerciseRouteRequest parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (UpsertExerciseRouteRequest) GeneratedMessageLite.Q(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static UpsertExerciseRouteRequest parseFrom(InputStream inputStream) throws IOException {
            return (UpsertExerciseRouteRequest) GeneratedMessageLite.L(DEFAULT_INSTANCE, inputStream);
        }

        public static UpsertExerciseRouteRequest parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (UpsertExerciseRouteRequest) GeneratedMessageLite.M(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static UpsertExerciseRouteRequest parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (UpsertExerciseRouteRequest) GeneratedMessageLite.J(DEFAULT_INSTANCE, codedInputStream);
        }

        public static UpsertExerciseRouteRequest parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (UpsertExerciseRouteRequest) GeneratedMessageLite.K(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }
    }

    public interface UpsertExerciseRouteRequestOrBuilder extends MessageLiteOrBuilder {
        DataProto.DataPoint getExerciseRoute();

        String getSessionUid();

        ByteString getSessionUidBytes();

        boolean hasExerciseRoute();

        boolean hasSessionUid();
    }

    private RequestProto() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite extensionRegistryLite) {
    }
}
