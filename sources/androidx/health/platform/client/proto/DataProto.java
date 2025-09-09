package androidx.health.platform.client.proto;

import androidx.health.platform.client.proto.GeneratedMessageLite;
import androidx.health.platform.client.proto.Internal;
import androidx.health.platform.client.proto.WireFormat;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public final class DataProto {

    /* renamed from: androidx.health.platform.client.proto.DataProto$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f4415a;

        static {
            int[] iArr = new int[GeneratedMessageLite.MethodToInvoke.values().length];
            f4415a = iArr;
            try {
                iArr[GeneratedMessageLite.MethodToInvoke.NEW_MUTABLE_INSTANCE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f4415a[GeneratedMessageLite.MethodToInvoke.NEW_BUILDER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f4415a[GeneratedMessageLite.MethodToInvoke.BUILD_MESSAGE_INFO.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f4415a[GeneratedMessageLite.MethodToInvoke.GET_DEFAULT_INSTANCE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f4415a[GeneratedMessageLite.MethodToInvoke.GET_PARSER.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f4415a[GeneratedMessageLite.MethodToInvoke.GET_MEMOIZED_IS_INITIALIZED.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f4415a[GeneratedMessageLite.MethodToInvoke.SET_MEMOIZED_IS_INITIALIZED.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    public static final class AggregateDataRow extends GeneratedMessageLite<AggregateDataRow, Builder> implements AggregateDataRowOrBuilder {
        public static final int DATA_ORIGINS_FIELD_NUMBER = 8;
        private static final AggregateDataRow DEFAULT_INSTANCE;
        public static final int DOUBLE_VALUES_FIELD_NUMBER = 6;
        public static final int END_LOCAL_DATE_TIME_FIELD_NUMBER = 4;
        public static final int END_TIME_EPOCH_MS_FIELD_NUMBER = 2;
        public static final int LONG_VALUES_FIELD_NUMBER = 7;
        private static volatile Parser<AggregateDataRow> PARSER = null;
        public static final int START_LOCAL_DATE_TIME_FIELD_NUMBER = 3;
        public static final int START_TIME_EPOCH_MS_FIELD_NUMBER = 1;
        public static final int ZONE_OFFSET_SECONDS_FIELD_NUMBER = 5;
        private int bitField0_;
        private long endTimeEpochMs_;
        private long startTimeEpochMs_;
        private int zoneOffsetSeconds_;
        private MapFieldLite<String, Double> doubleValues_ = MapFieldLite.emptyMapField();
        private MapFieldLite<String, Long> longValues_ = MapFieldLite.emptyMapField();
        private String startLocalDateTime_ = "";
        private String endLocalDateTime_ = "";
        private Internal.ProtobufList<DataOrigin> dataOrigins_ = GeneratedMessageLite.r();

        public static final class Builder extends GeneratedMessageLite.Builder<AggregateDataRow, Builder> implements AggregateDataRowOrBuilder {
            /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
                this();
            }

            public Builder addAllDataOrigins(Iterable<? extends DataOrigin> iterable) {
                d();
                ((AggregateDataRow) this.f4444a).addAllDataOrigins(iterable);
                return this;
            }

            public Builder addDataOrigins(DataOrigin dataOrigin) {
                d();
                ((AggregateDataRow) this.f4444a).addDataOrigins(dataOrigin);
                return this;
            }

            public Builder clearDataOrigins() {
                d();
                ((AggregateDataRow) this.f4444a).clearDataOrigins();
                return this;
            }

            public Builder clearDoubleValues() {
                d();
                ((AggregateDataRow) this.f4444a).getMutableDoubleValuesMap().clear();
                return this;
            }

            public Builder clearEndLocalDateTime() {
                d();
                ((AggregateDataRow) this.f4444a).clearEndLocalDateTime();
                return this;
            }

            public Builder clearEndTimeEpochMs() {
                d();
                ((AggregateDataRow) this.f4444a).clearEndTimeEpochMs();
                return this;
            }

            public Builder clearLongValues() {
                d();
                ((AggregateDataRow) this.f4444a).getMutableLongValuesMap().clear();
                return this;
            }

            public Builder clearStartLocalDateTime() {
                d();
                ((AggregateDataRow) this.f4444a).clearStartLocalDateTime();
                return this;
            }

            public Builder clearStartTimeEpochMs() {
                d();
                ((AggregateDataRow) this.f4444a).clearStartTimeEpochMs();
                return this;
            }

            public Builder clearZoneOffsetSeconds() {
                d();
                ((AggregateDataRow) this.f4444a).clearZoneOffsetSeconds();
                return this;
            }

            @Override // androidx.health.platform.client.proto.DataProto.AggregateDataRowOrBuilder
            public boolean containsDoubleValues(String str) {
                str.getClass();
                return ((AggregateDataRow) this.f4444a).getDoubleValuesMap().containsKey(str);
            }

            @Override // androidx.health.platform.client.proto.DataProto.AggregateDataRowOrBuilder
            public boolean containsLongValues(String str) {
                str.getClass();
                return ((AggregateDataRow) this.f4444a).getLongValuesMap().containsKey(str);
            }

            @Override // androidx.health.platform.client.proto.DataProto.AggregateDataRowOrBuilder
            public DataOrigin getDataOrigins(int i2) {
                return ((AggregateDataRow) this.f4444a).getDataOrigins(i2);
            }

            @Override // androidx.health.platform.client.proto.DataProto.AggregateDataRowOrBuilder
            public int getDataOriginsCount() {
                return ((AggregateDataRow) this.f4444a).getDataOriginsCount();
            }

            @Override // androidx.health.platform.client.proto.DataProto.AggregateDataRowOrBuilder
            public List<DataOrigin> getDataOriginsList() {
                return Collections.unmodifiableList(((AggregateDataRow) this.f4444a).getDataOriginsList());
            }

            @Override // androidx.health.platform.client.proto.DataProto.AggregateDataRowOrBuilder
            @Deprecated
            public Map<String, Double> getDoubleValues() {
                return getDoubleValuesMap();
            }

            @Override // androidx.health.platform.client.proto.DataProto.AggregateDataRowOrBuilder
            public int getDoubleValuesCount() {
                return ((AggregateDataRow) this.f4444a).getDoubleValuesMap().size();
            }

            @Override // androidx.health.platform.client.proto.DataProto.AggregateDataRowOrBuilder
            public Map<String, Double> getDoubleValuesMap() {
                return Collections.unmodifiableMap(((AggregateDataRow) this.f4444a).getDoubleValuesMap());
            }

            @Override // androidx.health.platform.client.proto.DataProto.AggregateDataRowOrBuilder
            public double getDoubleValuesOrDefault(String str, double d2) {
                str.getClass();
                Map<String, Double> doubleValuesMap = ((AggregateDataRow) this.f4444a).getDoubleValuesMap();
                return doubleValuesMap.containsKey(str) ? doubleValuesMap.get(str).doubleValue() : d2;
            }

            @Override // androidx.health.platform.client.proto.DataProto.AggregateDataRowOrBuilder
            public double getDoubleValuesOrThrow(String str) {
                str.getClass();
                Map<String, Double> doubleValuesMap = ((AggregateDataRow) this.f4444a).getDoubleValuesMap();
                if (doubleValuesMap.containsKey(str)) {
                    return doubleValuesMap.get(str).doubleValue();
                }
                throw new IllegalArgumentException();
            }

            @Override // androidx.health.platform.client.proto.DataProto.AggregateDataRowOrBuilder
            public String getEndLocalDateTime() {
                return ((AggregateDataRow) this.f4444a).getEndLocalDateTime();
            }

            @Override // androidx.health.platform.client.proto.DataProto.AggregateDataRowOrBuilder
            public ByteString getEndLocalDateTimeBytes() {
                return ((AggregateDataRow) this.f4444a).getEndLocalDateTimeBytes();
            }

            @Override // androidx.health.platform.client.proto.DataProto.AggregateDataRowOrBuilder
            public long getEndTimeEpochMs() {
                return ((AggregateDataRow) this.f4444a).getEndTimeEpochMs();
            }

            @Override // androidx.health.platform.client.proto.DataProto.AggregateDataRowOrBuilder
            @Deprecated
            public Map<String, Long> getLongValues() {
                return getLongValuesMap();
            }

            @Override // androidx.health.platform.client.proto.DataProto.AggregateDataRowOrBuilder
            public int getLongValuesCount() {
                return ((AggregateDataRow) this.f4444a).getLongValuesMap().size();
            }

            @Override // androidx.health.platform.client.proto.DataProto.AggregateDataRowOrBuilder
            public Map<String, Long> getLongValuesMap() {
                return Collections.unmodifiableMap(((AggregateDataRow) this.f4444a).getLongValuesMap());
            }

            @Override // androidx.health.platform.client.proto.DataProto.AggregateDataRowOrBuilder
            public long getLongValuesOrDefault(String str, long j2) {
                str.getClass();
                Map<String, Long> longValuesMap = ((AggregateDataRow) this.f4444a).getLongValuesMap();
                return longValuesMap.containsKey(str) ? longValuesMap.get(str).longValue() : j2;
            }

            @Override // androidx.health.platform.client.proto.DataProto.AggregateDataRowOrBuilder
            public long getLongValuesOrThrow(String str) {
                str.getClass();
                Map<String, Long> longValuesMap = ((AggregateDataRow) this.f4444a).getLongValuesMap();
                if (longValuesMap.containsKey(str)) {
                    return longValuesMap.get(str).longValue();
                }
                throw new IllegalArgumentException();
            }

            @Override // androidx.health.platform.client.proto.DataProto.AggregateDataRowOrBuilder
            public String getStartLocalDateTime() {
                return ((AggregateDataRow) this.f4444a).getStartLocalDateTime();
            }

            @Override // androidx.health.platform.client.proto.DataProto.AggregateDataRowOrBuilder
            public ByteString getStartLocalDateTimeBytes() {
                return ((AggregateDataRow) this.f4444a).getStartLocalDateTimeBytes();
            }

            @Override // androidx.health.platform.client.proto.DataProto.AggregateDataRowOrBuilder
            public long getStartTimeEpochMs() {
                return ((AggregateDataRow) this.f4444a).getStartTimeEpochMs();
            }

            @Override // androidx.health.platform.client.proto.DataProto.AggregateDataRowOrBuilder
            public int getZoneOffsetSeconds() {
                return ((AggregateDataRow) this.f4444a).getZoneOffsetSeconds();
            }

            @Override // androidx.health.platform.client.proto.DataProto.AggregateDataRowOrBuilder
            public boolean hasEndLocalDateTime() {
                return ((AggregateDataRow) this.f4444a).hasEndLocalDateTime();
            }

            @Override // androidx.health.platform.client.proto.DataProto.AggregateDataRowOrBuilder
            public boolean hasEndTimeEpochMs() {
                return ((AggregateDataRow) this.f4444a).hasEndTimeEpochMs();
            }

            @Override // androidx.health.platform.client.proto.DataProto.AggregateDataRowOrBuilder
            public boolean hasStartLocalDateTime() {
                return ((AggregateDataRow) this.f4444a).hasStartLocalDateTime();
            }

            @Override // androidx.health.platform.client.proto.DataProto.AggregateDataRowOrBuilder
            public boolean hasStartTimeEpochMs() {
                return ((AggregateDataRow) this.f4444a).hasStartTimeEpochMs();
            }

            @Override // androidx.health.platform.client.proto.DataProto.AggregateDataRowOrBuilder
            public boolean hasZoneOffsetSeconds() {
                return ((AggregateDataRow) this.f4444a).hasZoneOffsetSeconds();
            }

            public Builder putAllDoubleValues(Map<String, Double> map) {
                d();
                ((AggregateDataRow) this.f4444a).getMutableDoubleValuesMap().putAll(map);
                return this;
            }

            public Builder putAllLongValues(Map<String, Long> map) {
                d();
                ((AggregateDataRow) this.f4444a).getMutableLongValuesMap().putAll(map);
                return this;
            }

            public Builder putDoubleValues(String str, double d2) {
                str.getClass();
                d();
                ((AggregateDataRow) this.f4444a).getMutableDoubleValuesMap().put(str, Double.valueOf(d2));
                return this;
            }

            public Builder putLongValues(String str, long j2) {
                str.getClass();
                d();
                ((AggregateDataRow) this.f4444a).getMutableLongValuesMap().put(str, Long.valueOf(j2));
                return this;
            }

            public Builder removeDataOrigins(int i2) {
                d();
                ((AggregateDataRow) this.f4444a).removeDataOrigins(i2);
                return this;
            }

            public Builder removeDoubleValues(String str) {
                str.getClass();
                d();
                ((AggregateDataRow) this.f4444a).getMutableDoubleValuesMap().remove(str);
                return this;
            }

            public Builder removeLongValues(String str) {
                str.getClass();
                d();
                ((AggregateDataRow) this.f4444a).getMutableLongValuesMap().remove(str);
                return this;
            }

            public Builder setDataOrigins(int i2, DataOrigin dataOrigin) {
                d();
                ((AggregateDataRow) this.f4444a).setDataOrigins(i2, dataOrigin);
                return this;
            }

            public Builder setEndLocalDateTime(String str) {
                d();
                ((AggregateDataRow) this.f4444a).setEndLocalDateTime(str);
                return this;
            }

            public Builder setEndLocalDateTimeBytes(ByteString byteString) {
                d();
                ((AggregateDataRow) this.f4444a).setEndLocalDateTimeBytes(byteString);
                return this;
            }

            public Builder setEndTimeEpochMs(long j2) {
                d();
                ((AggregateDataRow) this.f4444a).setEndTimeEpochMs(j2);
                return this;
            }

            public Builder setStartLocalDateTime(String str) {
                d();
                ((AggregateDataRow) this.f4444a).setStartLocalDateTime(str);
                return this;
            }

            public Builder setStartLocalDateTimeBytes(ByteString byteString) {
                d();
                ((AggregateDataRow) this.f4444a).setStartLocalDateTimeBytes(byteString);
                return this;
            }

            public Builder setStartTimeEpochMs(long j2) {
                d();
                ((AggregateDataRow) this.f4444a).setStartTimeEpochMs(j2);
                return this;
            }

            public Builder setZoneOffsetSeconds(int i2) {
                d();
                ((AggregateDataRow) this.f4444a).setZoneOffsetSeconds(i2);
                return this;
            }

            private Builder() {
                super(AggregateDataRow.DEFAULT_INSTANCE);
            }

            public Builder addDataOrigins(int i2, DataOrigin dataOrigin) {
                d();
                ((AggregateDataRow) this.f4444a).addDataOrigins(i2, dataOrigin);
                return this;
            }

            public Builder setDataOrigins(int i2, DataOrigin.Builder builder) {
                d();
                ((AggregateDataRow) this.f4444a).setDataOrigins(i2, builder.build());
                return this;
            }

            public Builder addDataOrigins(DataOrigin.Builder builder) {
                d();
                ((AggregateDataRow) this.f4444a).addDataOrigins(builder.build());
                return this;
            }

            public Builder addDataOrigins(int i2, DataOrigin.Builder builder) {
                d();
                ((AggregateDataRow) this.f4444a).addDataOrigins(i2, builder.build());
                return this;
            }
        }

        private static final class DoubleValuesDefaultEntryHolder {

            /* renamed from: a, reason: collision with root package name */
            static final MapEntryLite f4416a = MapEntryLite.newDefaultInstance(WireFormat.FieldType.STRING, "", WireFormat.FieldType.DOUBLE, Double.valueOf(0.0d));

            private DoubleValuesDefaultEntryHolder() {
            }
        }

        private static final class LongValuesDefaultEntryHolder {

            /* renamed from: a, reason: collision with root package name */
            static final MapEntryLite f4417a = MapEntryLite.newDefaultInstance(WireFormat.FieldType.STRING, "", WireFormat.FieldType.INT64, 0L);

            private LongValuesDefaultEntryHolder() {
            }
        }

        static {
            AggregateDataRow aggregateDataRow = new AggregateDataRow();
            DEFAULT_INSTANCE = aggregateDataRow;
            GeneratedMessageLite.T(AggregateDataRow.class, aggregateDataRow);
        }

        private AggregateDataRow() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addAllDataOrigins(Iterable<? extends DataOrigin> iterable) {
            ensureDataOriginsIsMutable();
            AbstractMessageLite.a(iterable, this.dataOrigins_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addDataOrigins(DataOrigin dataOrigin) {
            dataOrigin.getClass();
            ensureDataOriginsIsMutable();
            this.dataOrigins_.add(dataOrigin);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearDataOrigins() {
            this.dataOrigins_ = GeneratedMessageLite.r();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearEndLocalDateTime() {
            this.bitField0_ &= -9;
            this.endLocalDateTime_ = getDefaultInstance().getEndLocalDateTime();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearEndTimeEpochMs() {
            this.bitField0_ &= -3;
            this.endTimeEpochMs_ = 0L;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearStartLocalDateTime() {
            this.bitField0_ &= -5;
            this.startLocalDateTime_ = getDefaultInstance().getStartLocalDateTime();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearStartTimeEpochMs() {
            this.bitField0_ &= -2;
            this.startTimeEpochMs_ = 0L;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearZoneOffsetSeconds() {
            this.bitField0_ &= -17;
            this.zoneOffsetSeconds_ = 0;
        }

        private void ensureDataOriginsIsMutable() {
            Internal.ProtobufList<DataOrigin> protobufList = this.dataOrigins_;
            if (protobufList.isModifiable()) {
                return;
            }
            this.dataOrigins_ = GeneratedMessageLite.C(protobufList);
        }

        public static AggregateDataRow getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Map<String, Double> getMutableDoubleValuesMap() {
            return internalGetMutableDoubleValues();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Map<String, Long> getMutableLongValuesMap() {
            return internalGetMutableLongValues();
        }

        private MapFieldLite<String, Double> internalGetDoubleValues() {
            return this.doubleValues_;
        }

        private MapFieldLite<String, Long> internalGetLongValues() {
            return this.longValues_;
        }

        private MapFieldLite<String, Double> internalGetMutableDoubleValues() {
            if (!this.doubleValues_.isMutable()) {
                this.doubleValues_ = this.doubleValues_.mutableCopy();
            }
            return this.doubleValues_;
        }

        private MapFieldLite<String, Long> internalGetMutableLongValues() {
            if (!this.longValues_.isMutable()) {
                this.longValues_ = this.longValues_.mutableCopy();
            }
            return this.longValues_;
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.m();
        }

        public static AggregateDataRow parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (AggregateDataRow) GeneratedMessageLite.F(DEFAULT_INSTANCE, inputStream);
        }

        public static AggregateDataRow parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (AggregateDataRow) GeneratedMessageLite.N(DEFAULT_INSTANCE, byteBuffer);
        }

        public static Parser<AggregateDataRow> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void removeDataOrigins(int i2) {
            ensureDataOriginsIsMutable();
            this.dataOrigins_.remove(i2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setDataOrigins(int i2, DataOrigin dataOrigin) {
            dataOrigin.getClass();
            ensureDataOriginsIsMutable();
            this.dataOrigins_.set(i2, dataOrigin);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setEndLocalDateTime(String str) {
            str.getClass();
            this.bitField0_ |= 8;
            this.endLocalDateTime_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setEndLocalDateTimeBytes(ByteString byteString) {
            this.endLocalDateTime_ = byteString.toStringUtf8();
            this.bitField0_ |= 8;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setEndTimeEpochMs(long j2) {
            this.bitField0_ |= 2;
            this.endTimeEpochMs_ = j2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setStartLocalDateTime(String str) {
            str.getClass();
            this.bitField0_ |= 4;
            this.startLocalDateTime_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setStartLocalDateTimeBytes(ByteString byteString) {
            this.startLocalDateTime_ = byteString.toStringUtf8();
            this.bitField0_ |= 4;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setStartTimeEpochMs(long j2) {
            this.bitField0_ |= 1;
            this.startTimeEpochMs_ = j2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setZoneOffsetSeconds(int i2) {
            this.bitField0_ |= 16;
            this.zoneOffsetSeconds_ = i2;
        }

        @Override // androidx.health.platform.client.proto.DataProto.AggregateDataRowOrBuilder
        public boolean containsDoubleValues(String str) {
            str.getClass();
            return internalGetDoubleValues().containsKey(str);
        }

        @Override // androidx.health.platform.client.proto.DataProto.AggregateDataRowOrBuilder
        public boolean containsLongValues(String str) {
            str.getClass();
            return internalGetLongValues().containsKey(str);
        }

        @Override // androidx.health.platform.client.proto.DataProto.AggregateDataRowOrBuilder
        public DataOrigin getDataOrigins(int i2) {
            return this.dataOrigins_.get(i2);
        }

        @Override // androidx.health.platform.client.proto.DataProto.AggregateDataRowOrBuilder
        public int getDataOriginsCount() {
            return this.dataOrigins_.size();
        }

        @Override // androidx.health.platform.client.proto.DataProto.AggregateDataRowOrBuilder
        public List<DataOrigin> getDataOriginsList() {
            return this.dataOrigins_;
        }

        public DataOriginOrBuilder getDataOriginsOrBuilder(int i2) {
            return this.dataOrigins_.get(i2);
        }

        public List<? extends DataOriginOrBuilder> getDataOriginsOrBuilderList() {
            return this.dataOrigins_;
        }

        @Override // androidx.health.platform.client.proto.DataProto.AggregateDataRowOrBuilder
        @Deprecated
        public Map<String, Double> getDoubleValues() {
            return getDoubleValuesMap();
        }

        @Override // androidx.health.platform.client.proto.DataProto.AggregateDataRowOrBuilder
        public int getDoubleValuesCount() {
            return internalGetDoubleValues().size();
        }

        @Override // androidx.health.platform.client.proto.DataProto.AggregateDataRowOrBuilder
        public Map<String, Double> getDoubleValuesMap() {
            return Collections.unmodifiableMap(internalGetDoubleValues());
        }

        @Override // androidx.health.platform.client.proto.DataProto.AggregateDataRowOrBuilder
        public double getDoubleValuesOrDefault(String str, double d2) {
            str.getClass();
            MapFieldLite<String, Double> mapFieldLiteInternalGetDoubleValues = internalGetDoubleValues();
            return mapFieldLiteInternalGetDoubleValues.containsKey(str) ? mapFieldLiteInternalGetDoubleValues.get(str).doubleValue() : d2;
        }

        @Override // androidx.health.platform.client.proto.DataProto.AggregateDataRowOrBuilder
        public double getDoubleValuesOrThrow(String str) {
            str.getClass();
            MapFieldLite<String, Double> mapFieldLiteInternalGetDoubleValues = internalGetDoubleValues();
            if (mapFieldLiteInternalGetDoubleValues.containsKey(str)) {
                return mapFieldLiteInternalGetDoubleValues.get(str).doubleValue();
            }
            throw new IllegalArgumentException();
        }

        @Override // androidx.health.platform.client.proto.DataProto.AggregateDataRowOrBuilder
        public String getEndLocalDateTime() {
            return this.endLocalDateTime_;
        }

        @Override // androidx.health.platform.client.proto.DataProto.AggregateDataRowOrBuilder
        public ByteString getEndLocalDateTimeBytes() {
            return ByteString.copyFromUtf8(this.endLocalDateTime_);
        }

        @Override // androidx.health.platform.client.proto.DataProto.AggregateDataRowOrBuilder
        public long getEndTimeEpochMs() {
            return this.endTimeEpochMs_;
        }

        @Override // androidx.health.platform.client.proto.DataProto.AggregateDataRowOrBuilder
        @Deprecated
        public Map<String, Long> getLongValues() {
            return getLongValuesMap();
        }

        @Override // androidx.health.platform.client.proto.DataProto.AggregateDataRowOrBuilder
        public int getLongValuesCount() {
            return internalGetLongValues().size();
        }

        @Override // androidx.health.platform.client.proto.DataProto.AggregateDataRowOrBuilder
        public Map<String, Long> getLongValuesMap() {
            return Collections.unmodifiableMap(internalGetLongValues());
        }

        @Override // androidx.health.platform.client.proto.DataProto.AggregateDataRowOrBuilder
        public long getLongValuesOrDefault(String str, long j2) {
            str.getClass();
            MapFieldLite<String, Long> mapFieldLiteInternalGetLongValues = internalGetLongValues();
            return mapFieldLiteInternalGetLongValues.containsKey(str) ? mapFieldLiteInternalGetLongValues.get(str).longValue() : j2;
        }

        @Override // androidx.health.platform.client.proto.DataProto.AggregateDataRowOrBuilder
        public long getLongValuesOrThrow(String str) {
            str.getClass();
            MapFieldLite<String, Long> mapFieldLiteInternalGetLongValues = internalGetLongValues();
            if (mapFieldLiteInternalGetLongValues.containsKey(str)) {
                return mapFieldLiteInternalGetLongValues.get(str).longValue();
            }
            throw new IllegalArgumentException();
        }

        @Override // androidx.health.platform.client.proto.DataProto.AggregateDataRowOrBuilder
        public String getStartLocalDateTime() {
            return this.startLocalDateTime_;
        }

        @Override // androidx.health.platform.client.proto.DataProto.AggregateDataRowOrBuilder
        public ByteString getStartLocalDateTimeBytes() {
            return ByteString.copyFromUtf8(this.startLocalDateTime_);
        }

        @Override // androidx.health.platform.client.proto.DataProto.AggregateDataRowOrBuilder
        public long getStartTimeEpochMs() {
            return this.startTimeEpochMs_;
        }

        @Override // androidx.health.platform.client.proto.DataProto.AggregateDataRowOrBuilder
        public int getZoneOffsetSeconds() {
            return this.zoneOffsetSeconds_;
        }

        @Override // androidx.health.platform.client.proto.DataProto.AggregateDataRowOrBuilder
        public boolean hasEndLocalDateTime() {
            return (this.bitField0_ & 8) != 0;
        }

        @Override // androidx.health.platform.client.proto.DataProto.AggregateDataRowOrBuilder
        public boolean hasEndTimeEpochMs() {
            return (this.bitField0_ & 2) != 0;
        }

        @Override // androidx.health.platform.client.proto.DataProto.AggregateDataRowOrBuilder
        public boolean hasStartLocalDateTime() {
            return (this.bitField0_ & 4) != 0;
        }

        @Override // androidx.health.platform.client.proto.DataProto.AggregateDataRowOrBuilder
        public boolean hasStartTimeEpochMs() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override // androidx.health.platform.client.proto.DataProto.AggregateDataRowOrBuilder
        public boolean hasZoneOffsetSeconds() {
            return (this.bitField0_ & 16) != 0;
        }

        @Override // androidx.health.platform.client.proto.GeneratedMessageLite
        protected final Object q(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            AnonymousClass1 anonymousClass1 = null;
            switch (AnonymousClass1.f4415a[methodToInvoke.ordinal()]) {
                case 1:
                    return new AggregateDataRow();
                case 2:
                    return new Builder(anonymousClass1);
                case 3:
                    return GeneratedMessageLite.D(DEFAULT_INSTANCE, "\u0001\b\u0000\u0001\u0001\b\b\u0002\u0001\u0000\u0001ဂ\u0000\u0002ဂ\u0001\u0003ဈ\u0002\u0004ဈ\u0003\u0005င\u0004\u00062\u00072\b\u001b", new Object[]{"bitField0_", "startTimeEpochMs_", "endTimeEpochMs_", "startLocalDateTime_", "endLocalDateTime_", "zoneOffsetSeconds_", "doubleValues_", DoubleValuesDefaultEntryHolder.f4416a, "longValues_", LongValuesDefaultEntryHolder.f4417a, "dataOrigins_", DataOrigin.class});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<AggregateDataRow> defaultInstanceBasedParser = PARSER;
                    if (defaultInstanceBasedParser == null) {
                        synchronized (AggregateDataRow.class) {
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

        public static Builder newBuilder(AggregateDataRow aggregateDataRow) {
            return (Builder) DEFAULT_INSTANCE.n(aggregateDataRow);
        }

        public static AggregateDataRow parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (AggregateDataRow) GeneratedMessageLite.G(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static AggregateDataRow parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (AggregateDataRow) GeneratedMessageLite.O(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static AggregateDataRow parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (AggregateDataRow) GeneratedMessageLite.H(DEFAULT_INSTANCE, byteString);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addDataOrigins(int i2, DataOrigin dataOrigin) {
            dataOrigin.getClass();
            ensureDataOriginsIsMutable();
            this.dataOrigins_.add(i2, dataOrigin);
        }

        public static AggregateDataRow parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (AggregateDataRow) GeneratedMessageLite.I(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static AggregateDataRow parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (AggregateDataRow) GeneratedMessageLite.P(DEFAULT_INSTANCE, bArr);
        }

        public static AggregateDataRow parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (AggregateDataRow) GeneratedMessageLite.Q(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static AggregateDataRow parseFrom(InputStream inputStream) throws IOException {
            return (AggregateDataRow) GeneratedMessageLite.L(DEFAULT_INSTANCE, inputStream);
        }

        public static AggregateDataRow parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (AggregateDataRow) GeneratedMessageLite.M(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static AggregateDataRow parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (AggregateDataRow) GeneratedMessageLite.J(DEFAULT_INSTANCE, codedInputStream);
        }

        public static AggregateDataRow parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (AggregateDataRow) GeneratedMessageLite.K(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }
    }

    public interface AggregateDataRowOrBuilder extends MessageLiteOrBuilder {
        boolean containsDoubleValues(String str);

        boolean containsLongValues(String str);

        DataOrigin getDataOrigins(int i2);

        int getDataOriginsCount();

        List<DataOrigin> getDataOriginsList();

        @Deprecated
        Map<String, Double> getDoubleValues();

        int getDoubleValuesCount();

        Map<String, Double> getDoubleValuesMap();

        double getDoubleValuesOrDefault(String str, double d2);

        double getDoubleValuesOrThrow(String str);

        String getEndLocalDateTime();

        ByteString getEndLocalDateTimeBytes();

        long getEndTimeEpochMs();

        @Deprecated
        Map<String, Long> getLongValues();

        int getLongValuesCount();

        Map<String, Long> getLongValuesMap();

        long getLongValuesOrDefault(String str, long j2);

        long getLongValuesOrThrow(String str);

        String getStartLocalDateTime();

        ByteString getStartLocalDateTimeBytes();

        long getStartTimeEpochMs();

        int getZoneOffsetSeconds();

        boolean hasEndLocalDateTime();

        boolean hasEndTimeEpochMs();

        boolean hasStartLocalDateTime();

        boolean hasStartTimeEpochMs();

        boolean hasZoneOffsetSeconds();
    }

    public static final class AggregatedValue extends GeneratedMessageLite<AggregatedValue, Builder> implements AggregatedValueOrBuilder {
        private static final AggregatedValue DEFAULT_INSTANCE;
        private static volatile Parser<AggregatedValue> PARSER = null;
        public static final int VALUES_FIELD_NUMBER = 1;
        private MapFieldLite<String, Value> values_ = MapFieldLite.emptyMapField();

        public static final class Builder extends GeneratedMessageLite.Builder<AggregatedValue, Builder> implements AggregatedValueOrBuilder {
            /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
                this();
            }

            public Builder clearValues() {
                d();
                ((AggregatedValue) this.f4444a).getMutableValuesMap().clear();
                return this;
            }

            @Override // androidx.health.platform.client.proto.DataProto.AggregatedValueOrBuilder
            public boolean containsValues(String str) {
                str.getClass();
                return ((AggregatedValue) this.f4444a).getValuesMap().containsKey(str);
            }

            @Override // androidx.health.platform.client.proto.DataProto.AggregatedValueOrBuilder
            @Deprecated
            public Map<String, Value> getValues() {
                return getValuesMap();
            }

            @Override // androidx.health.platform.client.proto.DataProto.AggregatedValueOrBuilder
            public int getValuesCount() {
                return ((AggregatedValue) this.f4444a).getValuesMap().size();
            }

            @Override // androidx.health.platform.client.proto.DataProto.AggregatedValueOrBuilder
            public Map<String, Value> getValuesMap() {
                return Collections.unmodifiableMap(((AggregatedValue) this.f4444a).getValuesMap());
            }

            @Override // androidx.health.platform.client.proto.DataProto.AggregatedValueOrBuilder
            public Value getValuesOrDefault(String str, Value value) {
                str.getClass();
                Map<String, Value> valuesMap = ((AggregatedValue) this.f4444a).getValuesMap();
                return valuesMap.containsKey(str) ? valuesMap.get(str) : value;
            }

            @Override // androidx.health.platform.client.proto.DataProto.AggregatedValueOrBuilder
            public Value getValuesOrThrow(String str) {
                str.getClass();
                Map<String, Value> valuesMap = ((AggregatedValue) this.f4444a).getValuesMap();
                if (valuesMap.containsKey(str)) {
                    return valuesMap.get(str);
                }
                throw new IllegalArgumentException();
            }

            public Builder putAllValues(Map<String, Value> map) {
                d();
                ((AggregatedValue) this.f4444a).getMutableValuesMap().putAll(map);
                return this;
            }

            public Builder putValues(String str, Value value) {
                str.getClass();
                value.getClass();
                d();
                ((AggregatedValue) this.f4444a).getMutableValuesMap().put(str, value);
                return this;
            }

            public Builder removeValues(String str) {
                str.getClass();
                d();
                ((AggregatedValue) this.f4444a).getMutableValuesMap().remove(str);
                return this;
            }

            private Builder() {
                super(AggregatedValue.DEFAULT_INSTANCE);
            }
        }

        private static final class ValuesDefaultEntryHolder {

            /* renamed from: a, reason: collision with root package name */
            static final MapEntryLite f4418a = MapEntryLite.newDefaultInstance(WireFormat.FieldType.STRING, "", WireFormat.FieldType.MESSAGE, Value.getDefaultInstance());

            private ValuesDefaultEntryHolder() {
            }
        }

        static {
            AggregatedValue aggregatedValue = new AggregatedValue();
            DEFAULT_INSTANCE = aggregatedValue;
            GeneratedMessageLite.T(AggregatedValue.class, aggregatedValue);
        }

        private AggregatedValue() {
        }

        public static AggregatedValue getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Map<String, Value> getMutableValuesMap() {
            return internalGetMutableValues();
        }

        private MapFieldLite<String, Value> internalGetMutableValues() {
            if (!this.values_.isMutable()) {
                this.values_ = this.values_.mutableCopy();
            }
            return this.values_;
        }

        private MapFieldLite<String, Value> internalGetValues() {
            return this.values_;
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.m();
        }

        public static AggregatedValue parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (AggregatedValue) GeneratedMessageLite.F(DEFAULT_INSTANCE, inputStream);
        }

        public static AggregatedValue parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (AggregatedValue) GeneratedMessageLite.N(DEFAULT_INSTANCE, byteBuffer);
        }

        public static Parser<AggregatedValue> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        @Override // androidx.health.platform.client.proto.DataProto.AggregatedValueOrBuilder
        public boolean containsValues(String str) {
            str.getClass();
            return internalGetValues().containsKey(str);
        }

        @Override // androidx.health.platform.client.proto.DataProto.AggregatedValueOrBuilder
        @Deprecated
        public Map<String, Value> getValues() {
            return getValuesMap();
        }

        @Override // androidx.health.platform.client.proto.DataProto.AggregatedValueOrBuilder
        public int getValuesCount() {
            return internalGetValues().size();
        }

        @Override // androidx.health.platform.client.proto.DataProto.AggregatedValueOrBuilder
        public Map<String, Value> getValuesMap() {
            return Collections.unmodifiableMap(internalGetValues());
        }

        @Override // androidx.health.platform.client.proto.DataProto.AggregatedValueOrBuilder
        public Value getValuesOrDefault(String str, Value value) {
            str.getClass();
            MapFieldLite<String, Value> mapFieldLiteInternalGetValues = internalGetValues();
            return mapFieldLiteInternalGetValues.containsKey(str) ? mapFieldLiteInternalGetValues.get(str) : value;
        }

        @Override // androidx.health.platform.client.proto.DataProto.AggregatedValueOrBuilder
        public Value getValuesOrThrow(String str) {
            str.getClass();
            MapFieldLite<String, Value> mapFieldLiteInternalGetValues = internalGetValues();
            if (mapFieldLiteInternalGetValues.containsKey(str)) {
                return mapFieldLiteInternalGetValues.get(str);
            }
            throw new IllegalArgumentException();
        }

        @Override // androidx.health.platform.client.proto.GeneratedMessageLite
        protected final Object q(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            AnonymousClass1 anonymousClass1 = null;
            switch (AnonymousClass1.f4415a[methodToInvoke.ordinal()]) {
                case 1:
                    return new AggregatedValue();
                case 2:
                    return new Builder(anonymousClass1);
                case 3:
                    return GeneratedMessageLite.D(DEFAULT_INSTANCE, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u00012", new Object[]{"values_", ValuesDefaultEntryHolder.f4418a});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<AggregatedValue> defaultInstanceBasedParser = PARSER;
                    if (defaultInstanceBasedParser == null) {
                        synchronized (AggregatedValue.class) {
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

        public static Builder newBuilder(AggregatedValue aggregatedValue) {
            return (Builder) DEFAULT_INSTANCE.n(aggregatedValue);
        }

        public static AggregatedValue parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (AggregatedValue) GeneratedMessageLite.G(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static AggregatedValue parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (AggregatedValue) GeneratedMessageLite.O(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static AggregatedValue parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (AggregatedValue) GeneratedMessageLite.H(DEFAULT_INSTANCE, byteString);
        }

        public static AggregatedValue parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (AggregatedValue) GeneratedMessageLite.I(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static AggregatedValue parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (AggregatedValue) GeneratedMessageLite.P(DEFAULT_INSTANCE, bArr);
        }

        public static AggregatedValue parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (AggregatedValue) GeneratedMessageLite.Q(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static AggregatedValue parseFrom(InputStream inputStream) throws IOException {
            return (AggregatedValue) GeneratedMessageLite.L(DEFAULT_INSTANCE, inputStream);
        }

        public static AggregatedValue parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (AggregatedValue) GeneratedMessageLite.M(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static AggregatedValue parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (AggregatedValue) GeneratedMessageLite.J(DEFAULT_INSTANCE, codedInputStream);
        }

        public static AggregatedValue parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (AggregatedValue) GeneratedMessageLite.K(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }
    }

    public interface AggregatedValueOrBuilder extends MessageLiteOrBuilder {
        boolean containsValues(String str);

        @Deprecated
        Map<String, Value> getValues();

        int getValuesCount();

        Map<String, Value> getValuesMap();

        Value getValuesOrDefault(String str, Value value);

        Value getValuesOrThrow(String str);
    }

    public static final class DataOrigin extends GeneratedMessageLite<DataOrigin, Builder> implements DataOriginOrBuilder {
        public static final int APPLICATION_ID_FIELD_NUMBER = 1;
        private static final DataOrigin DEFAULT_INSTANCE;
        private static volatile Parser<DataOrigin> PARSER;
        private String applicationId_ = "";
        private int bitField0_;

        public static final class Builder extends GeneratedMessageLite.Builder<DataOrigin, Builder> implements DataOriginOrBuilder {
            /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
                this();
            }

            public Builder clearApplicationId() {
                d();
                ((DataOrigin) this.f4444a).clearApplicationId();
                return this;
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataOriginOrBuilder
            public String getApplicationId() {
                return ((DataOrigin) this.f4444a).getApplicationId();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataOriginOrBuilder
            public ByteString getApplicationIdBytes() {
                return ((DataOrigin) this.f4444a).getApplicationIdBytes();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataOriginOrBuilder
            public boolean hasApplicationId() {
                return ((DataOrigin) this.f4444a).hasApplicationId();
            }

            public Builder setApplicationId(String str) {
                d();
                ((DataOrigin) this.f4444a).setApplicationId(str);
                return this;
            }

            public Builder setApplicationIdBytes(ByteString byteString) {
                d();
                ((DataOrigin) this.f4444a).setApplicationIdBytes(byteString);
                return this;
            }

            private Builder() {
                super(DataOrigin.DEFAULT_INSTANCE);
            }
        }

        static {
            DataOrigin dataOrigin = new DataOrigin();
            DEFAULT_INSTANCE = dataOrigin;
            GeneratedMessageLite.T(DataOrigin.class, dataOrigin);
        }

        private DataOrigin() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearApplicationId() {
            this.bitField0_ &= -2;
            this.applicationId_ = getDefaultInstance().getApplicationId();
        }

        public static DataOrigin getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.m();
        }

        public static DataOrigin parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (DataOrigin) GeneratedMessageLite.F(DEFAULT_INSTANCE, inputStream);
        }

        public static DataOrigin parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (DataOrigin) GeneratedMessageLite.N(DEFAULT_INSTANCE, byteBuffer);
        }

        public static Parser<DataOrigin> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setApplicationId(String str) {
            str.getClass();
            this.bitField0_ |= 1;
            this.applicationId_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setApplicationIdBytes(ByteString byteString) {
            this.applicationId_ = byteString.toStringUtf8();
            this.bitField0_ |= 1;
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataOriginOrBuilder
        public String getApplicationId() {
            return this.applicationId_;
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataOriginOrBuilder
        public ByteString getApplicationIdBytes() {
            return ByteString.copyFromUtf8(this.applicationId_);
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataOriginOrBuilder
        public boolean hasApplicationId() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override // androidx.health.platform.client.proto.GeneratedMessageLite
        protected final Object q(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            AnonymousClass1 anonymousClass1 = null;
            switch (AnonymousClass1.f4415a[methodToInvoke.ordinal()]) {
                case 1:
                    return new DataOrigin();
                case 2:
                    return new Builder(anonymousClass1);
                case 3:
                    return GeneratedMessageLite.D(DEFAULT_INSTANCE, "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001ဈ\u0000", new Object[]{"bitField0_", "applicationId_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<DataOrigin> defaultInstanceBasedParser = PARSER;
                    if (defaultInstanceBasedParser == null) {
                        synchronized (DataOrigin.class) {
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

        public static Builder newBuilder(DataOrigin dataOrigin) {
            return (Builder) DEFAULT_INSTANCE.n(dataOrigin);
        }

        public static DataOrigin parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (DataOrigin) GeneratedMessageLite.G(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static DataOrigin parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (DataOrigin) GeneratedMessageLite.O(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static DataOrigin parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (DataOrigin) GeneratedMessageLite.H(DEFAULT_INSTANCE, byteString);
        }

        public static DataOrigin parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (DataOrigin) GeneratedMessageLite.I(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static DataOrigin parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (DataOrigin) GeneratedMessageLite.P(DEFAULT_INSTANCE, bArr);
        }

        public static DataOrigin parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (DataOrigin) GeneratedMessageLite.Q(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static DataOrigin parseFrom(InputStream inputStream) throws IOException {
            return (DataOrigin) GeneratedMessageLite.L(DEFAULT_INSTANCE, inputStream);
        }

        public static DataOrigin parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (DataOrigin) GeneratedMessageLite.M(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static DataOrigin parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (DataOrigin) GeneratedMessageLite.J(DEFAULT_INSTANCE, codedInputStream);
        }

        public static DataOrigin parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (DataOrigin) GeneratedMessageLite.K(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }
    }

    public interface DataOriginOrBuilder extends MessageLiteOrBuilder {
        String getApplicationId();

        ByteString getApplicationIdBytes();

        boolean hasApplicationId();
    }

    public static final class DataPoint extends GeneratedMessageLite<DataPoint, Builder> implements DataPointOrBuilder {
        public static final int AVG_FIELD_NUMBER = 18;
        public static final int CLIENT_ID_FIELD_NUMBER = 11;
        public static final int CLIENT_VERSION_FIELD_NUMBER = 12;
        public static final int DATA_ORIGIN_FIELD_NUMBER = 5;
        public static final int DATA_TYPE_FIELD_NUMBER = 1;
        private static final DataPoint DEFAULT_INSTANCE;
        public static final int DEVICE_FIELD_NUMBER = 13;
        public static final int END_TIME_MILLIS_FIELD_NUMBER = 10;
        public static final int END_ZONE_OFFSET_SECONDS_FIELD_NUMBER = 20;
        public static final int INSTANT_TIME_MILLIS_FIELD_NUMBER = 8;
        public static final int MAX_FIELD_NUMBER = 17;
        public static final int MIN_FIELD_NUMBER = 16;
        public static final int ORIGIN_SAMPLE_UID_FIELD_NUMBER = 14;
        public static final int ORIGIN_SERIES_UID_FIELD_NUMBER = 4;
        private static volatile Parser<DataPoint> PARSER = null;
        public static final int RECORDING_METHOD_FIELD_NUMBER = 23;
        public static final int SERIES_VALUES_FIELD_NUMBER = 15;
        public static final int START_TIME_MILLIS_FIELD_NUMBER = 9;
        public static final int START_ZONE_OFFSET_SECONDS_FIELD_NUMBER = 19;
        public static final int SUB_TYPE_DATA_LISTS_FIELD_NUMBER = 22;
        public static final int UID_FIELD_NUMBER = 3;
        public static final int UPDATE_TIME_MILLIS_FIELD_NUMBER = 7;
        public static final int VALUES_FIELD_NUMBER = 2;
        public static final int ZONE_OFFSET_SECONDS_FIELD_NUMBER = 6;
        private AggregatedValue avg_;
        private int bitField0_;
        private long clientVersion_;
        private DataOrigin dataOrigin_;
        private DataType dataType_;
        private Device device_;
        private long endTimeMillis_;
        private int endZoneOffsetSeconds_;
        private long instantTimeMillis_;
        private AggregatedValue max_;
        private AggregatedValue min_;
        private int recordingMethod_;
        private long startTimeMillis_;
        private int startZoneOffsetSeconds_;
        private long updateTimeMillis_;
        private int zoneOffsetSeconds_;
        private MapFieldLite<String, Value> values_ = MapFieldLite.emptyMapField();
        private MapFieldLite<String, SubTypeDataList> subTypeDataLists_ = MapFieldLite.emptyMapField();
        private String uid_ = "";
        private String originSeriesUid_ = "";
        private String clientId_ = "";
        private String originSampleUid_ = "";
        private Internal.ProtobufList<SeriesValue> seriesValues_ = GeneratedMessageLite.r();

        public static final class Builder extends GeneratedMessageLite.Builder<DataPoint, Builder> implements DataPointOrBuilder {
            /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
                this();
            }

            public Builder addAllSeriesValues(Iterable<? extends SeriesValue> iterable) {
                d();
                ((DataPoint) this.f4444a).addAllSeriesValues(iterable);
                return this;
            }

            public Builder addSeriesValues(SeriesValue seriesValue) {
                d();
                ((DataPoint) this.f4444a).addSeriesValues(seriesValue);
                return this;
            }

            public Builder clearAvg() {
                d();
                ((DataPoint) this.f4444a).clearAvg();
                return this;
            }

            public Builder clearClientId() {
                d();
                ((DataPoint) this.f4444a).clearClientId();
                return this;
            }

            public Builder clearClientVersion() {
                d();
                ((DataPoint) this.f4444a).clearClientVersion();
                return this;
            }

            public Builder clearDataOrigin() {
                d();
                ((DataPoint) this.f4444a).clearDataOrigin();
                return this;
            }

            public Builder clearDataType() {
                d();
                ((DataPoint) this.f4444a).clearDataType();
                return this;
            }

            public Builder clearDevice() {
                d();
                ((DataPoint) this.f4444a).clearDevice();
                return this;
            }

            public Builder clearEndTimeMillis() {
                d();
                ((DataPoint) this.f4444a).clearEndTimeMillis();
                return this;
            }

            public Builder clearEndZoneOffsetSeconds() {
                d();
                ((DataPoint) this.f4444a).clearEndZoneOffsetSeconds();
                return this;
            }

            public Builder clearInstantTimeMillis() {
                d();
                ((DataPoint) this.f4444a).clearInstantTimeMillis();
                return this;
            }

            public Builder clearMax() {
                d();
                ((DataPoint) this.f4444a).clearMax();
                return this;
            }

            public Builder clearMin() {
                d();
                ((DataPoint) this.f4444a).clearMin();
                return this;
            }

            public Builder clearOriginSampleUid() {
                d();
                ((DataPoint) this.f4444a).clearOriginSampleUid();
                return this;
            }

            public Builder clearOriginSeriesUid() {
                d();
                ((DataPoint) this.f4444a).clearOriginSeriesUid();
                return this;
            }

            public Builder clearRecordingMethod() {
                d();
                ((DataPoint) this.f4444a).clearRecordingMethod();
                return this;
            }

            public Builder clearSeriesValues() {
                d();
                ((DataPoint) this.f4444a).clearSeriesValues();
                return this;
            }

            public Builder clearStartTimeMillis() {
                d();
                ((DataPoint) this.f4444a).clearStartTimeMillis();
                return this;
            }

            public Builder clearStartZoneOffsetSeconds() {
                d();
                ((DataPoint) this.f4444a).clearStartZoneOffsetSeconds();
                return this;
            }

            public Builder clearSubTypeDataLists() {
                d();
                ((DataPoint) this.f4444a).getMutableSubTypeDataListsMap().clear();
                return this;
            }

            public Builder clearUid() {
                d();
                ((DataPoint) this.f4444a).clearUid();
                return this;
            }

            public Builder clearUpdateTimeMillis() {
                d();
                ((DataPoint) this.f4444a).clearUpdateTimeMillis();
                return this;
            }

            public Builder clearValues() {
                d();
                ((DataPoint) this.f4444a).getMutableValuesMap().clear();
                return this;
            }

            public Builder clearZoneOffsetSeconds() {
                d();
                ((DataPoint) this.f4444a).clearZoneOffsetSeconds();
                return this;
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
            public boolean containsSubTypeDataLists(String str) {
                str.getClass();
                return ((DataPoint) this.f4444a).getSubTypeDataListsMap().containsKey(str);
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
            public boolean containsValues(String str) {
                str.getClass();
                return ((DataPoint) this.f4444a).getValuesMap().containsKey(str);
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
            public AggregatedValue getAvg() {
                return ((DataPoint) this.f4444a).getAvg();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
            public String getClientId() {
                return ((DataPoint) this.f4444a).getClientId();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
            public ByteString getClientIdBytes() {
                return ((DataPoint) this.f4444a).getClientIdBytes();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
            public long getClientVersion() {
                return ((DataPoint) this.f4444a).getClientVersion();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
            public DataOrigin getDataOrigin() {
                return ((DataPoint) this.f4444a).getDataOrigin();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
            public DataType getDataType() {
                return ((DataPoint) this.f4444a).getDataType();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
            public Device getDevice() {
                return ((DataPoint) this.f4444a).getDevice();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
            public long getEndTimeMillis() {
                return ((DataPoint) this.f4444a).getEndTimeMillis();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
            public int getEndZoneOffsetSeconds() {
                return ((DataPoint) this.f4444a).getEndZoneOffsetSeconds();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
            public long getInstantTimeMillis() {
                return ((DataPoint) this.f4444a).getInstantTimeMillis();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
            public AggregatedValue getMax() {
                return ((DataPoint) this.f4444a).getMax();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
            public AggregatedValue getMin() {
                return ((DataPoint) this.f4444a).getMin();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
            public String getOriginSampleUid() {
                return ((DataPoint) this.f4444a).getOriginSampleUid();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
            public ByteString getOriginSampleUidBytes() {
                return ((DataPoint) this.f4444a).getOriginSampleUidBytes();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
            public String getOriginSeriesUid() {
                return ((DataPoint) this.f4444a).getOriginSeriesUid();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
            public ByteString getOriginSeriesUidBytes() {
                return ((DataPoint) this.f4444a).getOriginSeriesUidBytes();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
            public int getRecordingMethod() {
                return ((DataPoint) this.f4444a).getRecordingMethod();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
            public SeriesValue getSeriesValues(int i2) {
                return ((DataPoint) this.f4444a).getSeriesValues(i2);
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
            public int getSeriesValuesCount() {
                return ((DataPoint) this.f4444a).getSeriesValuesCount();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
            public List<SeriesValue> getSeriesValuesList() {
                return Collections.unmodifiableList(((DataPoint) this.f4444a).getSeriesValuesList());
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
            public long getStartTimeMillis() {
                return ((DataPoint) this.f4444a).getStartTimeMillis();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
            public int getStartZoneOffsetSeconds() {
                return ((DataPoint) this.f4444a).getStartZoneOffsetSeconds();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
            @Deprecated
            public Map<String, SubTypeDataList> getSubTypeDataLists() {
                return getSubTypeDataListsMap();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
            public int getSubTypeDataListsCount() {
                return ((DataPoint) this.f4444a).getSubTypeDataListsMap().size();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
            public Map<String, SubTypeDataList> getSubTypeDataListsMap() {
                return Collections.unmodifiableMap(((DataPoint) this.f4444a).getSubTypeDataListsMap());
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
            public SubTypeDataList getSubTypeDataListsOrDefault(String str, SubTypeDataList subTypeDataList) {
                str.getClass();
                Map<String, SubTypeDataList> subTypeDataListsMap = ((DataPoint) this.f4444a).getSubTypeDataListsMap();
                return subTypeDataListsMap.containsKey(str) ? subTypeDataListsMap.get(str) : subTypeDataList;
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
            public SubTypeDataList getSubTypeDataListsOrThrow(String str) {
                str.getClass();
                Map<String, SubTypeDataList> subTypeDataListsMap = ((DataPoint) this.f4444a).getSubTypeDataListsMap();
                if (subTypeDataListsMap.containsKey(str)) {
                    return subTypeDataListsMap.get(str);
                }
                throw new IllegalArgumentException();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
            public String getUid() {
                return ((DataPoint) this.f4444a).getUid();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
            public ByteString getUidBytes() {
                return ((DataPoint) this.f4444a).getUidBytes();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
            public long getUpdateTimeMillis() {
                return ((DataPoint) this.f4444a).getUpdateTimeMillis();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
            @Deprecated
            public Map<String, Value> getValues() {
                return getValuesMap();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
            public int getValuesCount() {
                return ((DataPoint) this.f4444a).getValuesMap().size();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
            public Map<String, Value> getValuesMap() {
                return Collections.unmodifiableMap(((DataPoint) this.f4444a).getValuesMap());
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
            public Value getValuesOrDefault(String str, Value value) {
                str.getClass();
                Map<String, Value> valuesMap = ((DataPoint) this.f4444a).getValuesMap();
                return valuesMap.containsKey(str) ? valuesMap.get(str) : value;
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
            public Value getValuesOrThrow(String str) {
                str.getClass();
                Map<String, Value> valuesMap = ((DataPoint) this.f4444a).getValuesMap();
                if (valuesMap.containsKey(str)) {
                    return valuesMap.get(str);
                }
                throw new IllegalArgumentException();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
            public int getZoneOffsetSeconds() {
                return ((DataPoint) this.f4444a).getZoneOffsetSeconds();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
            public boolean hasAvg() {
                return ((DataPoint) this.f4444a).hasAvg();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
            public boolean hasClientId() {
                return ((DataPoint) this.f4444a).hasClientId();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
            public boolean hasClientVersion() {
                return ((DataPoint) this.f4444a).hasClientVersion();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
            public boolean hasDataOrigin() {
                return ((DataPoint) this.f4444a).hasDataOrigin();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
            public boolean hasDataType() {
                return ((DataPoint) this.f4444a).hasDataType();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
            public boolean hasDevice() {
                return ((DataPoint) this.f4444a).hasDevice();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
            public boolean hasEndTimeMillis() {
                return ((DataPoint) this.f4444a).hasEndTimeMillis();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
            public boolean hasEndZoneOffsetSeconds() {
                return ((DataPoint) this.f4444a).hasEndZoneOffsetSeconds();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
            public boolean hasInstantTimeMillis() {
                return ((DataPoint) this.f4444a).hasInstantTimeMillis();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
            public boolean hasMax() {
                return ((DataPoint) this.f4444a).hasMax();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
            public boolean hasMin() {
                return ((DataPoint) this.f4444a).hasMin();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
            public boolean hasOriginSampleUid() {
                return ((DataPoint) this.f4444a).hasOriginSampleUid();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
            public boolean hasOriginSeriesUid() {
                return ((DataPoint) this.f4444a).hasOriginSeriesUid();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
            public boolean hasRecordingMethod() {
                return ((DataPoint) this.f4444a).hasRecordingMethod();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
            public boolean hasStartTimeMillis() {
                return ((DataPoint) this.f4444a).hasStartTimeMillis();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
            public boolean hasStartZoneOffsetSeconds() {
                return ((DataPoint) this.f4444a).hasStartZoneOffsetSeconds();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
            public boolean hasUid() {
                return ((DataPoint) this.f4444a).hasUid();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
            public boolean hasUpdateTimeMillis() {
                return ((DataPoint) this.f4444a).hasUpdateTimeMillis();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
            public boolean hasZoneOffsetSeconds() {
                return ((DataPoint) this.f4444a).hasZoneOffsetSeconds();
            }

            public Builder mergeAvg(AggregatedValue aggregatedValue) {
                d();
                ((DataPoint) this.f4444a).mergeAvg(aggregatedValue);
                return this;
            }

            public Builder mergeDataOrigin(DataOrigin dataOrigin) {
                d();
                ((DataPoint) this.f4444a).mergeDataOrigin(dataOrigin);
                return this;
            }

            public Builder mergeDataType(DataType dataType) {
                d();
                ((DataPoint) this.f4444a).mergeDataType(dataType);
                return this;
            }

            public Builder mergeDevice(Device device) {
                d();
                ((DataPoint) this.f4444a).mergeDevice(device);
                return this;
            }

            public Builder mergeMax(AggregatedValue aggregatedValue) {
                d();
                ((DataPoint) this.f4444a).mergeMax(aggregatedValue);
                return this;
            }

            public Builder mergeMin(AggregatedValue aggregatedValue) {
                d();
                ((DataPoint) this.f4444a).mergeMin(aggregatedValue);
                return this;
            }

            public Builder putAllSubTypeDataLists(Map<String, SubTypeDataList> map) {
                d();
                ((DataPoint) this.f4444a).getMutableSubTypeDataListsMap().putAll(map);
                return this;
            }

            public Builder putAllValues(Map<String, Value> map) {
                d();
                ((DataPoint) this.f4444a).getMutableValuesMap().putAll(map);
                return this;
            }

            public Builder putSubTypeDataLists(String str, SubTypeDataList subTypeDataList) {
                str.getClass();
                subTypeDataList.getClass();
                d();
                ((DataPoint) this.f4444a).getMutableSubTypeDataListsMap().put(str, subTypeDataList);
                return this;
            }

            public Builder putValues(String str, Value value) {
                str.getClass();
                value.getClass();
                d();
                ((DataPoint) this.f4444a).getMutableValuesMap().put(str, value);
                return this;
            }

            public Builder removeSeriesValues(int i2) {
                d();
                ((DataPoint) this.f4444a).removeSeriesValues(i2);
                return this;
            }

            public Builder removeSubTypeDataLists(String str) {
                str.getClass();
                d();
                ((DataPoint) this.f4444a).getMutableSubTypeDataListsMap().remove(str);
                return this;
            }

            public Builder removeValues(String str) {
                str.getClass();
                d();
                ((DataPoint) this.f4444a).getMutableValuesMap().remove(str);
                return this;
            }

            public Builder setAvg(AggregatedValue aggregatedValue) {
                d();
                ((DataPoint) this.f4444a).setAvg(aggregatedValue);
                return this;
            }

            public Builder setClientId(String str) {
                d();
                ((DataPoint) this.f4444a).setClientId(str);
                return this;
            }

            public Builder setClientIdBytes(ByteString byteString) {
                d();
                ((DataPoint) this.f4444a).setClientIdBytes(byteString);
                return this;
            }

            public Builder setClientVersion(long j2) {
                d();
                ((DataPoint) this.f4444a).setClientVersion(j2);
                return this;
            }

            public Builder setDataOrigin(DataOrigin dataOrigin) {
                d();
                ((DataPoint) this.f4444a).setDataOrigin(dataOrigin);
                return this;
            }

            public Builder setDataType(DataType dataType) {
                d();
                ((DataPoint) this.f4444a).setDataType(dataType);
                return this;
            }

            public Builder setDevice(Device device) {
                d();
                ((DataPoint) this.f4444a).setDevice(device);
                return this;
            }

            public Builder setEndTimeMillis(long j2) {
                d();
                ((DataPoint) this.f4444a).setEndTimeMillis(j2);
                return this;
            }

            public Builder setEndZoneOffsetSeconds(int i2) {
                d();
                ((DataPoint) this.f4444a).setEndZoneOffsetSeconds(i2);
                return this;
            }

            public Builder setInstantTimeMillis(long j2) {
                d();
                ((DataPoint) this.f4444a).setInstantTimeMillis(j2);
                return this;
            }

            public Builder setMax(AggregatedValue aggregatedValue) {
                d();
                ((DataPoint) this.f4444a).setMax(aggregatedValue);
                return this;
            }

            public Builder setMin(AggregatedValue aggregatedValue) {
                d();
                ((DataPoint) this.f4444a).setMin(aggregatedValue);
                return this;
            }

            public Builder setOriginSampleUid(String str) {
                d();
                ((DataPoint) this.f4444a).setOriginSampleUid(str);
                return this;
            }

            public Builder setOriginSampleUidBytes(ByteString byteString) {
                d();
                ((DataPoint) this.f4444a).setOriginSampleUidBytes(byteString);
                return this;
            }

            public Builder setOriginSeriesUid(String str) {
                d();
                ((DataPoint) this.f4444a).setOriginSeriesUid(str);
                return this;
            }

            public Builder setOriginSeriesUidBytes(ByteString byteString) {
                d();
                ((DataPoint) this.f4444a).setOriginSeriesUidBytes(byteString);
                return this;
            }

            public Builder setRecordingMethod(int i2) {
                d();
                ((DataPoint) this.f4444a).setRecordingMethod(i2);
                return this;
            }

            public Builder setSeriesValues(int i2, SeriesValue seriesValue) {
                d();
                ((DataPoint) this.f4444a).setSeriesValues(i2, seriesValue);
                return this;
            }

            public Builder setStartTimeMillis(long j2) {
                d();
                ((DataPoint) this.f4444a).setStartTimeMillis(j2);
                return this;
            }

            public Builder setStartZoneOffsetSeconds(int i2) {
                d();
                ((DataPoint) this.f4444a).setStartZoneOffsetSeconds(i2);
                return this;
            }

            public Builder setUid(String str) {
                d();
                ((DataPoint) this.f4444a).setUid(str);
                return this;
            }

            public Builder setUidBytes(ByteString byteString) {
                d();
                ((DataPoint) this.f4444a).setUidBytes(byteString);
                return this;
            }

            public Builder setUpdateTimeMillis(long j2) {
                d();
                ((DataPoint) this.f4444a).setUpdateTimeMillis(j2);
                return this;
            }

            public Builder setZoneOffsetSeconds(int i2) {
                d();
                ((DataPoint) this.f4444a).setZoneOffsetSeconds(i2);
                return this;
            }

            private Builder() {
                super(DataPoint.DEFAULT_INSTANCE);
            }

            public Builder addSeriesValues(int i2, SeriesValue seriesValue) {
                d();
                ((DataPoint) this.f4444a).addSeriesValues(i2, seriesValue);
                return this;
            }

            public Builder setAvg(AggregatedValue.Builder builder) {
                d();
                ((DataPoint) this.f4444a).setAvg(builder.build());
                return this;
            }

            public Builder setDataOrigin(DataOrigin.Builder builder) {
                d();
                ((DataPoint) this.f4444a).setDataOrigin(builder.build());
                return this;
            }

            public Builder setDataType(DataType.Builder builder) {
                d();
                ((DataPoint) this.f4444a).setDataType(builder.build());
                return this;
            }

            public Builder setDevice(Device.Builder builder) {
                d();
                ((DataPoint) this.f4444a).setDevice(builder.build());
                return this;
            }

            public Builder setMax(AggregatedValue.Builder builder) {
                d();
                ((DataPoint) this.f4444a).setMax(builder.build());
                return this;
            }

            public Builder setMin(AggregatedValue.Builder builder) {
                d();
                ((DataPoint) this.f4444a).setMin(builder.build());
                return this;
            }

            public Builder setSeriesValues(int i2, SeriesValue.Builder builder) {
                d();
                ((DataPoint) this.f4444a).setSeriesValues(i2, builder.build());
                return this;
            }

            public Builder addSeriesValues(SeriesValue.Builder builder) {
                d();
                ((DataPoint) this.f4444a).addSeriesValues(builder.build());
                return this;
            }

            public Builder addSeriesValues(int i2, SeriesValue.Builder builder) {
                d();
                ((DataPoint) this.f4444a).addSeriesValues(i2, builder.build());
                return this;
            }
        }

        public static final class SubTypeDataList extends GeneratedMessageLite<SubTypeDataList, Builder> implements SubTypeDataListOrBuilder {
            private static final SubTypeDataList DEFAULT_INSTANCE;
            private static volatile Parser<SubTypeDataList> PARSER = null;
            public static final int VALUES_FIELD_NUMBER = 1;
            private Internal.ProtobufList<SubTypeDataValue> values_ = GeneratedMessageLite.r();

            public static final class Builder extends GeneratedMessageLite.Builder<SubTypeDataList, Builder> implements SubTypeDataListOrBuilder {
                /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
                    this();
                }

                public Builder addAllValues(Iterable<? extends SubTypeDataValue> iterable) {
                    d();
                    ((SubTypeDataList) this.f4444a).addAllValues(iterable);
                    return this;
                }

                public Builder addValues(SubTypeDataValue subTypeDataValue) {
                    d();
                    ((SubTypeDataList) this.f4444a).addValues(subTypeDataValue);
                    return this;
                }

                public Builder clearValues() {
                    d();
                    ((SubTypeDataList) this.f4444a).clearValues();
                    return this;
                }

                @Override // androidx.health.platform.client.proto.DataProto.DataPoint.SubTypeDataListOrBuilder
                public SubTypeDataValue getValues(int i2) {
                    return ((SubTypeDataList) this.f4444a).getValues(i2);
                }

                @Override // androidx.health.platform.client.proto.DataProto.DataPoint.SubTypeDataListOrBuilder
                public int getValuesCount() {
                    return ((SubTypeDataList) this.f4444a).getValuesCount();
                }

                @Override // androidx.health.platform.client.proto.DataProto.DataPoint.SubTypeDataListOrBuilder
                public List<SubTypeDataValue> getValuesList() {
                    return Collections.unmodifiableList(((SubTypeDataList) this.f4444a).getValuesList());
                }

                public Builder removeValues(int i2) {
                    d();
                    ((SubTypeDataList) this.f4444a).removeValues(i2);
                    return this;
                }

                public Builder setValues(int i2, SubTypeDataValue subTypeDataValue) {
                    d();
                    ((SubTypeDataList) this.f4444a).setValues(i2, subTypeDataValue);
                    return this;
                }

                private Builder() {
                    super(SubTypeDataList.DEFAULT_INSTANCE);
                }

                public Builder addValues(int i2, SubTypeDataValue subTypeDataValue) {
                    d();
                    ((SubTypeDataList) this.f4444a).addValues(i2, subTypeDataValue);
                    return this;
                }

                public Builder setValues(int i2, SubTypeDataValue.Builder builder) {
                    d();
                    ((SubTypeDataList) this.f4444a).setValues(i2, builder.build());
                    return this;
                }

                public Builder addValues(SubTypeDataValue.Builder builder) {
                    d();
                    ((SubTypeDataList) this.f4444a).addValues(builder.build());
                    return this;
                }

                public Builder addValues(int i2, SubTypeDataValue.Builder builder) {
                    d();
                    ((SubTypeDataList) this.f4444a).addValues(i2, builder.build());
                    return this;
                }
            }

            static {
                SubTypeDataList subTypeDataList = new SubTypeDataList();
                DEFAULT_INSTANCE = subTypeDataList;
                GeneratedMessageLite.T(SubTypeDataList.class, subTypeDataList);
            }

            private SubTypeDataList() {
            }

            /* JADX INFO: Access modifiers changed from: private */
            public void addAllValues(Iterable<? extends SubTypeDataValue> iterable) {
                ensureValuesIsMutable();
                AbstractMessageLite.a(iterable, this.values_);
            }

            /* JADX INFO: Access modifiers changed from: private */
            public void addValues(SubTypeDataValue subTypeDataValue) {
                subTypeDataValue.getClass();
                ensureValuesIsMutable();
                this.values_.add(subTypeDataValue);
            }

            /* JADX INFO: Access modifiers changed from: private */
            public void clearValues() {
                this.values_ = GeneratedMessageLite.r();
            }

            private void ensureValuesIsMutable() {
                Internal.ProtobufList<SubTypeDataValue> protobufList = this.values_;
                if (protobufList.isModifiable()) {
                    return;
                }
                this.values_ = GeneratedMessageLite.C(protobufList);
            }

            public static SubTypeDataList getDefaultInstance() {
                return DEFAULT_INSTANCE;
            }

            public static Builder newBuilder() {
                return (Builder) DEFAULT_INSTANCE.m();
            }

            public static SubTypeDataList parseDelimitedFrom(InputStream inputStream) throws IOException {
                return (SubTypeDataList) GeneratedMessageLite.F(DEFAULT_INSTANCE, inputStream);
            }

            public static SubTypeDataList parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
                return (SubTypeDataList) GeneratedMessageLite.N(DEFAULT_INSTANCE, byteBuffer);
            }

            public static Parser<SubTypeDataList> parser() {
                return DEFAULT_INSTANCE.getParserForType();
            }

            /* JADX INFO: Access modifiers changed from: private */
            public void removeValues(int i2) {
                ensureValuesIsMutable();
                this.values_.remove(i2);
            }

            /* JADX INFO: Access modifiers changed from: private */
            public void setValues(int i2, SubTypeDataValue subTypeDataValue) {
                subTypeDataValue.getClass();
                ensureValuesIsMutable();
                this.values_.set(i2, subTypeDataValue);
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataPoint.SubTypeDataListOrBuilder
            public SubTypeDataValue getValues(int i2) {
                return this.values_.get(i2);
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataPoint.SubTypeDataListOrBuilder
            public int getValuesCount() {
                return this.values_.size();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataPoint.SubTypeDataListOrBuilder
            public List<SubTypeDataValue> getValuesList() {
                return this.values_;
            }

            public SubTypeDataValueOrBuilder getValuesOrBuilder(int i2) {
                return this.values_.get(i2);
            }

            public List<? extends SubTypeDataValueOrBuilder> getValuesOrBuilderList() {
                return this.values_;
            }

            @Override // androidx.health.platform.client.proto.GeneratedMessageLite
            protected final Object q(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
                AnonymousClass1 anonymousClass1 = null;
                switch (AnonymousClass1.f4415a[methodToInvoke.ordinal()]) {
                    case 1:
                        return new SubTypeDataList();
                    case 2:
                        return new Builder(anonymousClass1);
                    case 3:
                        return GeneratedMessageLite.D(DEFAULT_INSTANCE, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b", new Object[]{"values_", SubTypeDataValue.class});
                    case 4:
                        return DEFAULT_INSTANCE;
                    case 5:
                        Parser<SubTypeDataList> defaultInstanceBasedParser = PARSER;
                        if (defaultInstanceBasedParser == null) {
                            synchronized (SubTypeDataList.class) {
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

            public static Builder newBuilder(SubTypeDataList subTypeDataList) {
                return (Builder) DEFAULT_INSTANCE.n(subTypeDataList);
            }

            public static SubTypeDataList parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
                return (SubTypeDataList) GeneratedMessageLite.G(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
            }

            public static SubTypeDataList parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return (SubTypeDataList) GeneratedMessageLite.O(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
            }

            public static SubTypeDataList parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
                return (SubTypeDataList) GeneratedMessageLite.H(DEFAULT_INSTANCE, byteString);
            }

            /* JADX INFO: Access modifiers changed from: private */
            public void addValues(int i2, SubTypeDataValue subTypeDataValue) {
                subTypeDataValue.getClass();
                ensureValuesIsMutable();
                this.values_.add(i2, subTypeDataValue);
            }

            public static SubTypeDataList parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return (SubTypeDataList) GeneratedMessageLite.I(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
            }

            public static SubTypeDataList parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
                return (SubTypeDataList) GeneratedMessageLite.P(DEFAULT_INSTANCE, bArr);
            }

            public static SubTypeDataList parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return (SubTypeDataList) GeneratedMessageLite.Q(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
            }

            public static SubTypeDataList parseFrom(InputStream inputStream) throws IOException {
                return (SubTypeDataList) GeneratedMessageLite.L(DEFAULT_INSTANCE, inputStream);
            }

            public static SubTypeDataList parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
                return (SubTypeDataList) GeneratedMessageLite.M(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
            }

            public static SubTypeDataList parseFrom(CodedInputStream codedInputStream) throws IOException {
                return (SubTypeDataList) GeneratedMessageLite.J(DEFAULT_INSTANCE, codedInputStream);
            }

            public static SubTypeDataList parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
                return (SubTypeDataList) GeneratedMessageLite.K(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
            }
        }

        public interface SubTypeDataListOrBuilder extends MessageLiteOrBuilder {
            SubTypeDataValue getValues(int i2);

            int getValuesCount();

            List<SubTypeDataValue> getValuesList();
        }

        private static final class SubTypeDataListsDefaultEntryHolder {

            /* renamed from: a, reason: collision with root package name */
            static final MapEntryLite f4419a = MapEntryLite.newDefaultInstance(WireFormat.FieldType.STRING, "", WireFormat.FieldType.MESSAGE, SubTypeDataList.getDefaultInstance());

            private SubTypeDataListsDefaultEntryHolder() {
            }
        }

        private static final class ValuesDefaultEntryHolder {

            /* renamed from: a, reason: collision with root package name */
            static final MapEntryLite f4420a = MapEntryLite.newDefaultInstance(WireFormat.FieldType.STRING, "", WireFormat.FieldType.MESSAGE, Value.getDefaultInstance());

            private ValuesDefaultEntryHolder() {
            }
        }

        static {
            DataPoint dataPoint = new DataPoint();
            DEFAULT_INSTANCE = dataPoint;
            GeneratedMessageLite.T(DataPoint.class, dataPoint);
        }

        private DataPoint() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addAllSeriesValues(Iterable<? extends SeriesValue> iterable) {
            ensureSeriesValuesIsMutable();
            AbstractMessageLite.a(iterable, this.seriesValues_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addSeriesValues(SeriesValue seriesValue) {
            seriesValue.getClass();
            ensureSeriesValuesIsMutable();
            this.seriesValues_.add(seriesValue);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearAvg() {
            this.avg_ = null;
            this.bitField0_ &= -32769;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearClientId() {
            this.bitField0_ &= -513;
            this.clientId_ = getDefaultInstance().getClientId();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearClientVersion() {
            this.bitField0_ &= -1025;
            this.clientVersion_ = 0L;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearDataOrigin() {
            this.dataOrigin_ = null;
            this.bitField0_ &= -9;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearDataType() {
            this.dataType_ = null;
            this.bitField0_ &= -2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearDevice() {
            this.device_ = null;
            this.bitField0_ &= -2049;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearEndTimeMillis() {
            this.bitField0_ &= -257;
            this.endTimeMillis_ = 0L;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearEndZoneOffsetSeconds() {
            this.bitField0_ &= -131073;
            this.endZoneOffsetSeconds_ = 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearInstantTimeMillis() {
            this.bitField0_ &= -65;
            this.instantTimeMillis_ = 0L;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearMax() {
            this.max_ = null;
            this.bitField0_ &= -16385;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearMin() {
            this.min_ = null;
            this.bitField0_ &= -8193;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearOriginSampleUid() {
            this.bitField0_ &= -4097;
            this.originSampleUid_ = getDefaultInstance().getOriginSampleUid();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearOriginSeriesUid() {
            this.bitField0_ &= -5;
            this.originSeriesUid_ = getDefaultInstance().getOriginSeriesUid();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearRecordingMethod() {
            this.bitField0_ &= -262145;
            this.recordingMethod_ = 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearSeriesValues() {
            this.seriesValues_ = GeneratedMessageLite.r();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearStartTimeMillis() {
            this.bitField0_ &= -129;
            this.startTimeMillis_ = 0L;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearStartZoneOffsetSeconds() {
            this.bitField0_ &= -65537;
            this.startZoneOffsetSeconds_ = 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearUid() {
            this.bitField0_ &= -3;
            this.uid_ = getDefaultInstance().getUid();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearUpdateTimeMillis() {
            this.bitField0_ &= -33;
            this.updateTimeMillis_ = 0L;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearZoneOffsetSeconds() {
            this.bitField0_ &= -17;
            this.zoneOffsetSeconds_ = 0;
        }

        private void ensureSeriesValuesIsMutable() {
            Internal.ProtobufList<SeriesValue> protobufList = this.seriesValues_;
            if (protobufList.isModifiable()) {
                return;
            }
            this.seriesValues_ = GeneratedMessageLite.C(protobufList);
        }

        public static DataPoint getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Map<String, SubTypeDataList> getMutableSubTypeDataListsMap() {
            return internalGetMutableSubTypeDataLists();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Map<String, Value> getMutableValuesMap() {
            return internalGetMutableValues();
        }

        private MapFieldLite<String, SubTypeDataList> internalGetMutableSubTypeDataLists() {
            if (!this.subTypeDataLists_.isMutable()) {
                this.subTypeDataLists_ = this.subTypeDataLists_.mutableCopy();
            }
            return this.subTypeDataLists_;
        }

        private MapFieldLite<String, Value> internalGetMutableValues() {
            if (!this.values_.isMutable()) {
                this.values_ = this.values_.mutableCopy();
            }
            return this.values_;
        }

        private MapFieldLite<String, SubTypeDataList> internalGetSubTypeDataLists() {
            return this.subTypeDataLists_;
        }

        private MapFieldLite<String, Value> internalGetValues() {
            return this.values_;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mergeAvg(AggregatedValue aggregatedValue) {
            aggregatedValue.getClass();
            AggregatedValue aggregatedValue2 = this.avg_;
            if (aggregatedValue2 == null || aggregatedValue2 == AggregatedValue.getDefaultInstance()) {
                this.avg_ = aggregatedValue;
            } else {
                this.avg_ = AggregatedValue.newBuilder(this.avg_).mergeFrom((AggregatedValue.Builder) aggregatedValue).buildPartial();
            }
            this.bitField0_ |= 32768;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mergeDataOrigin(DataOrigin dataOrigin) {
            dataOrigin.getClass();
            DataOrigin dataOrigin2 = this.dataOrigin_;
            if (dataOrigin2 == null || dataOrigin2 == DataOrigin.getDefaultInstance()) {
                this.dataOrigin_ = dataOrigin;
            } else {
                this.dataOrigin_ = DataOrigin.newBuilder(this.dataOrigin_).mergeFrom((DataOrigin.Builder) dataOrigin).buildPartial();
            }
            this.bitField0_ |= 8;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mergeDataType(DataType dataType) {
            dataType.getClass();
            DataType dataType2 = this.dataType_;
            if (dataType2 == null || dataType2 == DataType.getDefaultInstance()) {
                this.dataType_ = dataType;
            } else {
                this.dataType_ = DataType.newBuilder(this.dataType_).mergeFrom((DataType.Builder) dataType).buildPartial();
            }
            this.bitField0_ |= 1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mergeDevice(Device device) {
            device.getClass();
            Device device2 = this.device_;
            if (device2 == null || device2 == Device.getDefaultInstance()) {
                this.device_ = device;
            } else {
                this.device_ = Device.newBuilder(this.device_).mergeFrom((Device.Builder) device).buildPartial();
            }
            this.bitField0_ |= 2048;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mergeMax(AggregatedValue aggregatedValue) {
            aggregatedValue.getClass();
            AggregatedValue aggregatedValue2 = this.max_;
            if (aggregatedValue2 == null || aggregatedValue2 == AggregatedValue.getDefaultInstance()) {
                this.max_ = aggregatedValue;
            } else {
                this.max_ = AggregatedValue.newBuilder(this.max_).mergeFrom((AggregatedValue.Builder) aggregatedValue).buildPartial();
            }
            this.bitField0_ |= 16384;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mergeMin(AggregatedValue aggregatedValue) {
            aggregatedValue.getClass();
            AggregatedValue aggregatedValue2 = this.min_;
            if (aggregatedValue2 == null || aggregatedValue2 == AggregatedValue.getDefaultInstance()) {
                this.min_ = aggregatedValue;
            } else {
                this.min_ = AggregatedValue.newBuilder(this.min_).mergeFrom((AggregatedValue.Builder) aggregatedValue).buildPartial();
            }
            this.bitField0_ |= 8192;
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.m();
        }

        public static DataPoint parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (DataPoint) GeneratedMessageLite.F(DEFAULT_INSTANCE, inputStream);
        }

        public static DataPoint parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (DataPoint) GeneratedMessageLite.N(DEFAULT_INSTANCE, byteBuffer);
        }

        public static Parser<DataPoint> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void removeSeriesValues(int i2) {
            ensureSeriesValuesIsMutable();
            this.seriesValues_.remove(i2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setAvg(AggregatedValue aggregatedValue) {
            aggregatedValue.getClass();
            this.avg_ = aggregatedValue;
            this.bitField0_ |= 32768;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setClientId(String str) {
            str.getClass();
            this.bitField0_ |= 512;
            this.clientId_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setClientIdBytes(ByteString byteString) {
            this.clientId_ = byteString.toStringUtf8();
            this.bitField0_ |= 512;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setClientVersion(long j2) {
            this.bitField0_ |= 1024;
            this.clientVersion_ = j2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setDataOrigin(DataOrigin dataOrigin) {
            dataOrigin.getClass();
            this.dataOrigin_ = dataOrigin;
            this.bitField0_ |= 8;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setDataType(DataType dataType) {
            dataType.getClass();
            this.dataType_ = dataType;
            this.bitField0_ |= 1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setDevice(Device device) {
            device.getClass();
            this.device_ = device;
            this.bitField0_ |= 2048;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setEndTimeMillis(long j2) {
            this.bitField0_ |= 256;
            this.endTimeMillis_ = j2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setEndZoneOffsetSeconds(int i2) {
            this.bitField0_ |= 131072;
            this.endZoneOffsetSeconds_ = i2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setInstantTimeMillis(long j2) {
            this.bitField0_ |= 64;
            this.instantTimeMillis_ = j2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setMax(AggregatedValue aggregatedValue) {
            aggregatedValue.getClass();
            this.max_ = aggregatedValue;
            this.bitField0_ |= 16384;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setMin(AggregatedValue aggregatedValue) {
            aggregatedValue.getClass();
            this.min_ = aggregatedValue;
            this.bitField0_ |= 8192;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setOriginSampleUid(String str) {
            str.getClass();
            this.bitField0_ |= 4096;
            this.originSampleUid_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setOriginSampleUidBytes(ByteString byteString) {
            this.originSampleUid_ = byteString.toStringUtf8();
            this.bitField0_ |= 4096;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setOriginSeriesUid(String str) {
            str.getClass();
            this.bitField0_ |= 4;
            this.originSeriesUid_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setOriginSeriesUidBytes(ByteString byteString) {
            this.originSeriesUid_ = byteString.toStringUtf8();
            this.bitField0_ |= 4;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setRecordingMethod(int i2) {
            this.bitField0_ |= 262144;
            this.recordingMethod_ = i2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setSeriesValues(int i2, SeriesValue seriesValue) {
            seriesValue.getClass();
            ensureSeriesValuesIsMutable();
            this.seriesValues_.set(i2, seriesValue);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setStartTimeMillis(long j2) {
            this.bitField0_ |= 128;
            this.startTimeMillis_ = j2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setStartZoneOffsetSeconds(int i2) {
            this.bitField0_ |= 65536;
            this.startZoneOffsetSeconds_ = i2;
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

        /* JADX INFO: Access modifiers changed from: private */
        public void setUpdateTimeMillis(long j2) {
            this.bitField0_ |= 32;
            this.updateTimeMillis_ = j2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setZoneOffsetSeconds(int i2) {
            this.bitField0_ |= 16;
            this.zoneOffsetSeconds_ = i2;
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
        public boolean containsSubTypeDataLists(String str) {
            str.getClass();
            return internalGetSubTypeDataLists().containsKey(str);
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
        public boolean containsValues(String str) {
            str.getClass();
            return internalGetValues().containsKey(str);
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
        public AggregatedValue getAvg() {
            AggregatedValue aggregatedValue = this.avg_;
            return aggregatedValue == null ? AggregatedValue.getDefaultInstance() : aggregatedValue;
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
        public String getClientId() {
            return this.clientId_;
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
        public ByteString getClientIdBytes() {
            return ByteString.copyFromUtf8(this.clientId_);
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
        public long getClientVersion() {
            return this.clientVersion_;
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
        public DataOrigin getDataOrigin() {
            DataOrigin dataOrigin = this.dataOrigin_;
            return dataOrigin == null ? DataOrigin.getDefaultInstance() : dataOrigin;
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
        public DataType getDataType() {
            DataType dataType = this.dataType_;
            return dataType == null ? DataType.getDefaultInstance() : dataType;
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
        public Device getDevice() {
            Device device = this.device_;
            return device == null ? Device.getDefaultInstance() : device;
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
        public long getEndTimeMillis() {
            return this.endTimeMillis_;
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
        public int getEndZoneOffsetSeconds() {
            return this.endZoneOffsetSeconds_;
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
        public long getInstantTimeMillis() {
            return this.instantTimeMillis_;
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
        public AggregatedValue getMax() {
            AggregatedValue aggregatedValue = this.max_;
            return aggregatedValue == null ? AggregatedValue.getDefaultInstance() : aggregatedValue;
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
        public AggregatedValue getMin() {
            AggregatedValue aggregatedValue = this.min_;
            return aggregatedValue == null ? AggregatedValue.getDefaultInstance() : aggregatedValue;
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
        public String getOriginSampleUid() {
            return this.originSampleUid_;
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
        public ByteString getOriginSampleUidBytes() {
            return ByteString.copyFromUtf8(this.originSampleUid_);
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
        public String getOriginSeriesUid() {
            return this.originSeriesUid_;
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
        public ByteString getOriginSeriesUidBytes() {
            return ByteString.copyFromUtf8(this.originSeriesUid_);
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
        public int getRecordingMethod() {
            return this.recordingMethod_;
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
        public SeriesValue getSeriesValues(int i2) {
            return this.seriesValues_.get(i2);
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
        public int getSeriesValuesCount() {
            return this.seriesValues_.size();
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
        public List<SeriesValue> getSeriesValuesList() {
            return this.seriesValues_;
        }

        public SeriesValueOrBuilder getSeriesValuesOrBuilder(int i2) {
            return this.seriesValues_.get(i2);
        }

        public List<? extends SeriesValueOrBuilder> getSeriesValuesOrBuilderList() {
            return this.seriesValues_;
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
        public long getStartTimeMillis() {
            return this.startTimeMillis_;
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
        public int getStartZoneOffsetSeconds() {
            return this.startZoneOffsetSeconds_;
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
        @Deprecated
        public Map<String, SubTypeDataList> getSubTypeDataLists() {
            return getSubTypeDataListsMap();
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
        public int getSubTypeDataListsCount() {
            return internalGetSubTypeDataLists().size();
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
        public Map<String, SubTypeDataList> getSubTypeDataListsMap() {
            return Collections.unmodifiableMap(internalGetSubTypeDataLists());
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
        public SubTypeDataList getSubTypeDataListsOrDefault(String str, SubTypeDataList subTypeDataList) {
            str.getClass();
            MapFieldLite<String, SubTypeDataList> mapFieldLiteInternalGetSubTypeDataLists = internalGetSubTypeDataLists();
            return mapFieldLiteInternalGetSubTypeDataLists.containsKey(str) ? mapFieldLiteInternalGetSubTypeDataLists.get(str) : subTypeDataList;
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
        public SubTypeDataList getSubTypeDataListsOrThrow(String str) {
            str.getClass();
            MapFieldLite<String, SubTypeDataList> mapFieldLiteInternalGetSubTypeDataLists = internalGetSubTypeDataLists();
            if (mapFieldLiteInternalGetSubTypeDataLists.containsKey(str)) {
                return mapFieldLiteInternalGetSubTypeDataLists.get(str);
            }
            throw new IllegalArgumentException();
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
        public String getUid() {
            return this.uid_;
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
        public ByteString getUidBytes() {
            return ByteString.copyFromUtf8(this.uid_);
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
        public long getUpdateTimeMillis() {
            return this.updateTimeMillis_;
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
        @Deprecated
        public Map<String, Value> getValues() {
            return getValuesMap();
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
        public int getValuesCount() {
            return internalGetValues().size();
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
        public Map<String, Value> getValuesMap() {
            return Collections.unmodifiableMap(internalGetValues());
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
        public Value getValuesOrDefault(String str, Value value) {
            str.getClass();
            MapFieldLite<String, Value> mapFieldLiteInternalGetValues = internalGetValues();
            return mapFieldLiteInternalGetValues.containsKey(str) ? mapFieldLiteInternalGetValues.get(str) : value;
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
        public Value getValuesOrThrow(String str) {
            str.getClass();
            MapFieldLite<String, Value> mapFieldLiteInternalGetValues = internalGetValues();
            if (mapFieldLiteInternalGetValues.containsKey(str)) {
                return mapFieldLiteInternalGetValues.get(str);
            }
            throw new IllegalArgumentException();
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
        public int getZoneOffsetSeconds() {
            return this.zoneOffsetSeconds_;
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
        public boolean hasAvg() {
            return (this.bitField0_ & 32768) != 0;
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
        public boolean hasClientId() {
            return (this.bitField0_ & 512) != 0;
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
        public boolean hasClientVersion() {
            return (this.bitField0_ & 1024) != 0;
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
        public boolean hasDataOrigin() {
            return (this.bitField0_ & 8) != 0;
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
        public boolean hasDataType() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
        public boolean hasDevice() {
            return (this.bitField0_ & 2048) != 0;
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
        public boolean hasEndTimeMillis() {
            return (this.bitField0_ & 256) != 0;
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
        public boolean hasEndZoneOffsetSeconds() {
            return (this.bitField0_ & 131072) != 0;
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
        public boolean hasInstantTimeMillis() {
            return (this.bitField0_ & 64) != 0;
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
        public boolean hasMax() {
            return (this.bitField0_ & 16384) != 0;
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
        public boolean hasMin() {
            return (this.bitField0_ & 8192) != 0;
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
        public boolean hasOriginSampleUid() {
            return (this.bitField0_ & 4096) != 0;
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
        public boolean hasOriginSeriesUid() {
            return (this.bitField0_ & 4) != 0;
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
        public boolean hasRecordingMethod() {
            return (this.bitField0_ & 262144) != 0;
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
        public boolean hasStartTimeMillis() {
            return (this.bitField0_ & 128) != 0;
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
        public boolean hasStartZoneOffsetSeconds() {
            return (this.bitField0_ & 65536) != 0;
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
        public boolean hasUid() {
            return (this.bitField0_ & 2) != 0;
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
        public boolean hasUpdateTimeMillis() {
            return (this.bitField0_ & 32) != 0;
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataPointOrBuilder
        public boolean hasZoneOffsetSeconds() {
            return (this.bitField0_ & 16) != 0;
        }

        @Override // androidx.health.platform.client.proto.GeneratedMessageLite
        protected final Object q(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            AnonymousClass1 anonymousClass1 = null;
            switch (AnonymousClass1.f4415a[methodToInvoke.ordinal()]) {
                case 1:
                    return new DataPoint();
                case 2:
                    return new Builder(anonymousClass1);
                case 3:
                    return GeneratedMessageLite.D(DEFAULT_INSTANCE, "\u0001\u0016\u0000\u0001\u0001\u0017\u0016\u0002\u0001\u0000\u0001ဉ\u0000\u00022\u0003ဈ\u0001\u0004ဈ\u0002\u0005ဉ\u0003\u0006င\u0004\u0007ဂ\u0005\bဂ\u0006\tဂ\u0007\nဂ\b\u000bဈ\t\fဂ\n\rဉ\u000b\u000eဈ\f\u000f\u001b\u0010ဉ\r\u0011ဉ\u000e\u0012ဉ\u000f\u0013င\u0010\u0014င\u0011\u00162\u0017င\u0012", new Object[]{"bitField0_", "dataType_", "values_", ValuesDefaultEntryHolder.f4420a, "uid_", "originSeriesUid_", "dataOrigin_", "zoneOffsetSeconds_", "updateTimeMillis_", "instantTimeMillis_", "startTimeMillis_", "endTimeMillis_", "clientId_", "clientVersion_", "device_", "originSampleUid_", "seriesValues_", SeriesValue.class, "min_", "max_", "avg_", "startZoneOffsetSeconds_", "endZoneOffsetSeconds_", "subTypeDataLists_", SubTypeDataListsDefaultEntryHolder.f4419a, "recordingMethod_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<DataPoint> defaultInstanceBasedParser = PARSER;
                    if (defaultInstanceBasedParser == null) {
                        synchronized (DataPoint.class) {
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

        public static Builder newBuilder(DataPoint dataPoint) {
            return (Builder) DEFAULT_INSTANCE.n(dataPoint);
        }

        public static DataPoint parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (DataPoint) GeneratedMessageLite.G(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static DataPoint parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (DataPoint) GeneratedMessageLite.O(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static DataPoint parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (DataPoint) GeneratedMessageLite.H(DEFAULT_INSTANCE, byteString);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addSeriesValues(int i2, SeriesValue seriesValue) {
            seriesValue.getClass();
            ensureSeriesValuesIsMutable();
            this.seriesValues_.add(i2, seriesValue);
        }

        public static DataPoint parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (DataPoint) GeneratedMessageLite.I(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static DataPoint parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (DataPoint) GeneratedMessageLite.P(DEFAULT_INSTANCE, bArr);
        }

        public static DataPoint parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (DataPoint) GeneratedMessageLite.Q(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static DataPoint parseFrom(InputStream inputStream) throws IOException {
            return (DataPoint) GeneratedMessageLite.L(DEFAULT_INSTANCE, inputStream);
        }

        public static DataPoint parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (DataPoint) GeneratedMessageLite.M(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static DataPoint parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (DataPoint) GeneratedMessageLite.J(DEFAULT_INSTANCE, codedInputStream);
        }

        public static DataPoint parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (DataPoint) GeneratedMessageLite.K(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }
    }

    public interface DataPointOrBuilder extends MessageLiteOrBuilder {
        boolean containsSubTypeDataLists(String str);

        boolean containsValues(String str);

        AggregatedValue getAvg();

        String getClientId();

        ByteString getClientIdBytes();

        long getClientVersion();

        DataOrigin getDataOrigin();

        DataType getDataType();

        Device getDevice();

        long getEndTimeMillis();

        int getEndZoneOffsetSeconds();

        long getInstantTimeMillis();

        AggregatedValue getMax();

        AggregatedValue getMin();

        String getOriginSampleUid();

        ByteString getOriginSampleUidBytes();

        String getOriginSeriesUid();

        ByteString getOriginSeriesUidBytes();

        int getRecordingMethod();

        SeriesValue getSeriesValues(int i2);

        int getSeriesValuesCount();

        List<SeriesValue> getSeriesValuesList();

        long getStartTimeMillis();

        int getStartZoneOffsetSeconds();

        @Deprecated
        Map<String, DataPoint.SubTypeDataList> getSubTypeDataLists();

        int getSubTypeDataListsCount();

        Map<String, DataPoint.SubTypeDataList> getSubTypeDataListsMap();

        DataPoint.SubTypeDataList getSubTypeDataListsOrDefault(String str, DataPoint.SubTypeDataList subTypeDataList);

        DataPoint.SubTypeDataList getSubTypeDataListsOrThrow(String str);

        String getUid();

        ByteString getUidBytes();

        long getUpdateTimeMillis();

        @Deprecated
        Map<String, Value> getValues();

        int getValuesCount();

        Map<String, Value> getValuesMap();

        Value getValuesOrDefault(String str, Value value);

        Value getValuesOrThrow(String str);

        int getZoneOffsetSeconds();

        boolean hasAvg();

        boolean hasClientId();

        boolean hasClientVersion();

        boolean hasDataOrigin();

        boolean hasDataType();

        boolean hasDevice();

        boolean hasEndTimeMillis();

        boolean hasEndZoneOffsetSeconds();

        boolean hasInstantTimeMillis();

        boolean hasMax();

        boolean hasMin();

        boolean hasOriginSampleUid();

        boolean hasOriginSeriesUid();

        boolean hasRecordingMethod();

        boolean hasStartTimeMillis();

        boolean hasStartZoneOffsetSeconds();

        boolean hasUid();

        boolean hasUpdateTimeMillis();

        boolean hasZoneOffsetSeconds();
    }

    public static final class DataType extends GeneratedMessageLite<DataType, Builder> implements DataTypeOrBuilder {
        private static final DataType DEFAULT_INSTANCE;
        public static final int NAME_FIELD_NUMBER = 1;
        private static volatile Parser<DataType> PARSER;
        private int bitField0_;
        private String name_ = "";

        public static final class Builder extends GeneratedMessageLite.Builder<DataType, Builder> implements DataTypeOrBuilder {
            /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
                this();
            }

            public Builder clearName() {
                d();
                ((DataType) this.f4444a).clearName();
                return this;
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataTypeOrBuilder
            public String getName() {
                return ((DataType) this.f4444a).getName();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataTypeOrBuilder
            public ByteString getNameBytes() {
                return ((DataType) this.f4444a).getNameBytes();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DataTypeOrBuilder
            public boolean hasName() {
                return ((DataType) this.f4444a).hasName();
            }

            public Builder setName(String str) {
                d();
                ((DataType) this.f4444a).setName(str);
                return this;
            }

            public Builder setNameBytes(ByteString byteString) {
                d();
                ((DataType) this.f4444a).setNameBytes(byteString);
                return this;
            }

            private Builder() {
                super(DataType.DEFAULT_INSTANCE);
            }
        }

        static {
            DataType dataType = new DataType();
            DEFAULT_INSTANCE = dataType;
            GeneratedMessageLite.T(DataType.class, dataType);
        }

        private DataType() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearName() {
            this.bitField0_ &= -2;
            this.name_ = getDefaultInstance().getName();
        }

        public static DataType getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.m();
        }

        public static DataType parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (DataType) GeneratedMessageLite.F(DEFAULT_INSTANCE, inputStream);
        }

        public static DataType parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (DataType) GeneratedMessageLite.N(DEFAULT_INSTANCE, byteBuffer);
        }

        public static Parser<DataType> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setName(String str) {
            str.getClass();
            this.bitField0_ |= 1;
            this.name_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setNameBytes(ByteString byteString) {
            this.name_ = byteString.toStringUtf8();
            this.bitField0_ |= 1;
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataTypeOrBuilder
        public String getName() {
            return this.name_;
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataTypeOrBuilder
        public ByteString getNameBytes() {
            return ByteString.copyFromUtf8(this.name_);
        }

        @Override // androidx.health.platform.client.proto.DataProto.DataTypeOrBuilder
        public boolean hasName() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override // androidx.health.platform.client.proto.GeneratedMessageLite
        protected final Object q(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            AnonymousClass1 anonymousClass1 = null;
            switch (AnonymousClass1.f4415a[methodToInvoke.ordinal()]) {
                case 1:
                    return new DataType();
                case 2:
                    return new Builder(anonymousClass1);
                case 3:
                    return GeneratedMessageLite.D(DEFAULT_INSTANCE, "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001ဈ\u0000", new Object[]{"bitField0_", "name_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<DataType> defaultInstanceBasedParser = PARSER;
                    if (defaultInstanceBasedParser == null) {
                        synchronized (DataType.class) {
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

        public static Builder newBuilder(DataType dataType) {
            return (Builder) DEFAULT_INSTANCE.n(dataType);
        }

        public static DataType parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (DataType) GeneratedMessageLite.G(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static DataType parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (DataType) GeneratedMessageLite.O(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static DataType parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (DataType) GeneratedMessageLite.H(DEFAULT_INSTANCE, byteString);
        }

        public static DataType parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (DataType) GeneratedMessageLite.I(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static DataType parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (DataType) GeneratedMessageLite.P(DEFAULT_INSTANCE, bArr);
        }

        public static DataType parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (DataType) GeneratedMessageLite.Q(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static DataType parseFrom(InputStream inputStream) throws IOException {
            return (DataType) GeneratedMessageLite.L(DEFAULT_INSTANCE, inputStream);
        }

        public static DataType parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (DataType) GeneratedMessageLite.M(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static DataType parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (DataType) GeneratedMessageLite.J(DEFAULT_INSTANCE, codedInputStream);
        }

        public static DataType parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (DataType) GeneratedMessageLite.K(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }
    }

    public interface DataTypeOrBuilder extends MessageLiteOrBuilder {
        String getName();

        ByteString getNameBytes();

        boolean hasName();
    }

    public static final class Device extends GeneratedMessageLite<Device, Builder> implements DeviceOrBuilder {
        private static final Device DEFAULT_INSTANCE;
        public static final int IDENTIFIER_FIELD_NUMBER = 1;
        public static final int MANUFACTURER_FIELD_NUMBER = 2;
        public static final int MODEL_FIELD_NUMBER = 3;
        private static volatile Parser<Device> PARSER = null;
        public static final int TYPE_FIELD_NUMBER = 4;
        private int bitField0_;
        private String identifier_ = "";
        private String manufacturer_ = "";
        private String model_ = "";
        private String type_ = "";

        public static final class Builder extends GeneratedMessageLite.Builder<Device, Builder> implements DeviceOrBuilder {
            /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
                this();
            }

            public Builder clearIdentifier() {
                d();
                ((Device) this.f4444a).clearIdentifier();
                return this;
            }

            public Builder clearManufacturer() {
                d();
                ((Device) this.f4444a).clearManufacturer();
                return this;
            }

            public Builder clearModel() {
                d();
                ((Device) this.f4444a).clearModel();
                return this;
            }

            public Builder clearType() {
                d();
                ((Device) this.f4444a).clearType();
                return this;
            }

            @Override // androidx.health.platform.client.proto.DataProto.DeviceOrBuilder
            public String getIdentifier() {
                return ((Device) this.f4444a).getIdentifier();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DeviceOrBuilder
            public ByteString getIdentifierBytes() {
                return ((Device) this.f4444a).getIdentifierBytes();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DeviceOrBuilder
            public String getManufacturer() {
                return ((Device) this.f4444a).getManufacturer();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DeviceOrBuilder
            public ByteString getManufacturerBytes() {
                return ((Device) this.f4444a).getManufacturerBytes();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DeviceOrBuilder
            public String getModel() {
                return ((Device) this.f4444a).getModel();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DeviceOrBuilder
            public ByteString getModelBytes() {
                return ((Device) this.f4444a).getModelBytes();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DeviceOrBuilder
            public String getType() {
                return ((Device) this.f4444a).getType();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DeviceOrBuilder
            public ByteString getTypeBytes() {
                return ((Device) this.f4444a).getTypeBytes();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DeviceOrBuilder
            public boolean hasIdentifier() {
                return ((Device) this.f4444a).hasIdentifier();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DeviceOrBuilder
            public boolean hasManufacturer() {
                return ((Device) this.f4444a).hasManufacturer();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DeviceOrBuilder
            public boolean hasModel() {
                return ((Device) this.f4444a).hasModel();
            }

            @Override // androidx.health.platform.client.proto.DataProto.DeviceOrBuilder
            public boolean hasType() {
                return ((Device) this.f4444a).hasType();
            }

            public Builder setIdentifier(String str) {
                d();
                ((Device) this.f4444a).setIdentifier(str);
                return this;
            }

            public Builder setIdentifierBytes(ByteString byteString) {
                d();
                ((Device) this.f4444a).setIdentifierBytes(byteString);
                return this;
            }

            public Builder setManufacturer(String str) {
                d();
                ((Device) this.f4444a).setManufacturer(str);
                return this;
            }

            public Builder setManufacturerBytes(ByteString byteString) {
                d();
                ((Device) this.f4444a).setManufacturerBytes(byteString);
                return this;
            }

            public Builder setModel(String str) {
                d();
                ((Device) this.f4444a).setModel(str);
                return this;
            }

            public Builder setModelBytes(ByteString byteString) {
                d();
                ((Device) this.f4444a).setModelBytes(byteString);
                return this;
            }

            public Builder setType(String str) {
                d();
                ((Device) this.f4444a).setType(str);
                return this;
            }

            public Builder setTypeBytes(ByteString byteString) {
                d();
                ((Device) this.f4444a).setTypeBytes(byteString);
                return this;
            }

            private Builder() {
                super(Device.DEFAULT_INSTANCE);
            }
        }

        static {
            Device device = new Device();
            DEFAULT_INSTANCE = device;
            GeneratedMessageLite.T(Device.class, device);
        }

        private Device() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearIdentifier() {
            this.bitField0_ &= -2;
            this.identifier_ = getDefaultInstance().getIdentifier();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearManufacturer() {
            this.bitField0_ &= -3;
            this.manufacturer_ = getDefaultInstance().getManufacturer();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearModel() {
            this.bitField0_ &= -5;
            this.model_ = getDefaultInstance().getModel();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearType() {
            this.bitField0_ &= -9;
            this.type_ = getDefaultInstance().getType();
        }

        public static Device getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.m();
        }

        public static Device parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (Device) GeneratedMessageLite.F(DEFAULT_INSTANCE, inputStream);
        }

        public static Device parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (Device) GeneratedMessageLite.N(DEFAULT_INSTANCE, byteBuffer);
        }

        public static Parser<Device> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setIdentifier(String str) {
            str.getClass();
            this.bitField0_ |= 1;
            this.identifier_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setIdentifierBytes(ByteString byteString) {
            this.identifier_ = byteString.toStringUtf8();
            this.bitField0_ |= 1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setManufacturer(String str) {
            str.getClass();
            this.bitField0_ |= 2;
            this.manufacturer_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setManufacturerBytes(ByteString byteString) {
            this.manufacturer_ = byteString.toStringUtf8();
            this.bitField0_ |= 2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setModel(String str) {
            str.getClass();
            this.bitField0_ |= 4;
            this.model_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setModelBytes(ByteString byteString) {
            this.model_ = byteString.toStringUtf8();
            this.bitField0_ |= 4;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setType(String str) {
            str.getClass();
            this.bitField0_ |= 8;
            this.type_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setTypeBytes(ByteString byteString) {
            this.type_ = byteString.toStringUtf8();
            this.bitField0_ |= 8;
        }

        @Override // androidx.health.platform.client.proto.DataProto.DeviceOrBuilder
        public String getIdentifier() {
            return this.identifier_;
        }

        @Override // androidx.health.platform.client.proto.DataProto.DeviceOrBuilder
        public ByteString getIdentifierBytes() {
            return ByteString.copyFromUtf8(this.identifier_);
        }

        @Override // androidx.health.platform.client.proto.DataProto.DeviceOrBuilder
        public String getManufacturer() {
            return this.manufacturer_;
        }

        @Override // androidx.health.platform.client.proto.DataProto.DeviceOrBuilder
        public ByteString getManufacturerBytes() {
            return ByteString.copyFromUtf8(this.manufacturer_);
        }

        @Override // androidx.health.platform.client.proto.DataProto.DeviceOrBuilder
        public String getModel() {
            return this.model_;
        }

        @Override // androidx.health.platform.client.proto.DataProto.DeviceOrBuilder
        public ByteString getModelBytes() {
            return ByteString.copyFromUtf8(this.model_);
        }

        @Override // androidx.health.platform.client.proto.DataProto.DeviceOrBuilder
        public String getType() {
            return this.type_;
        }

        @Override // androidx.health.platform.client.proto.DataProto.DeviceOrBuilder
        public ByteString getTypeBytes() {
            return ByteString.copyFromUtf8(this.type_);
        }

        @Override // androidx.health.platform.client.proto.DataProto.DeviceOrBuilder
        public boolean hasIdentifier() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override // androidx.health.platform.client.proto.DataProto.DeviceOrBuilder
        public boolean hasManufacturer() {
            return (this.bitField0_ & 2) != 0;
        }

        @Override // androidx.health.platform.client.proto.DataProto.DeviceOrBuilder
        public boolean hasModel() {
            return (this.bitField0_ & 4) != 0;
        }

        @Override // androidx.health.platform.client.proto.DataProto.DeviceOrBuilder
        public boolean hasType() {
            return (this.bitField0_ & 8) != 0;
        }

        @Override // androidx.health.platform.client.proto.GeneratedMessageLite
        protected final Object q(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            AnonymousClass1 anonymousClass1 = null;
            switch (AnonymousClass1.f4415a[methodToInvoke.ordinal()]) {
                case 1:
                    return new Device();
                case 2:
                    return new Builder(anonymousClass1);
                case 3:
                    return GeneratedMessageLite.D(DEFAULT_INSTANCE, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001ဈ\u0000\u0002ဈ\u0001\u0003ဈ\u0002\u0004ဈ\u0003", new Object[]{"bitField0_", "identifier_", "manufacturer_", "model_", "type_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<Device> defaultInstanceBasedParser = PARSER;
                    if (defaultInstanceBasedParser == null) {
                        synchronized (Device.class) {
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

        public static Builder newBuilder(Device device) {
            return (Builder) DEFAULT_INSTANCE.n(device);
        }

        public static Device parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Device) GeneratedMessageLite.G(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static Device parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Device) GeneratedMessageLite.O(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static Device parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (Device) GeneratedMessageLite.H(DEFAULT_INSTANCE, byteString);
        }

        public static Device parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Device) GeneratedMessageLite.I(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static Device parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (Device) GeneratedMessageLite.P(DEFAULT_INSTANCE, bArr);
        }

        public static Device parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Device) GeneratedMessageLite.Q(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static Device parseFrom(InputStream inputStream) throws IOException {
            return (Device) GeneratedMessageLite.L(DEFAULT_INSTANCE, inputStream);
        }

        public static Device parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Device) GeneratedMessageLite.M(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static Device parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (Device) GeneratedMessageLite.J(DEFAULT_INSTANCE, codedInputStream);
        }

        public static Device parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Device) GeneratedMessageLite.K(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }
    }

    public interface DeviceOrBuilder extends MessageLiteOrBuilder {
        String getIdentifier();

        ByteString getIdentifierBytes();

        String getManufacturer();

        ByteString getManufacturerBytes();

        String getModel();

        ByteString getModelBytes();

        String getType();

        ByteString getTypeBytes();

        boolean hasIdentifier();

        boolean hasManufacturer();

        boolean hasModel();

        boolean hasType();
    }

    public static final class SeriesValue extends GeneratedMessageLite<SeriesValue, Builder> implements SeriesValueOrBuilder {
        private static final SeriesValue DEFAULT_INSTANCE;
        public static final int INSTANT_TIME_MILLIS_FIELD_NUMBER = 2;
        private static volatile Parser<SeriesValue> PARSER = null;
        public static final int VALUES_FIELD_NUMBER = 1;
        private int bitField0_;
        private long instantTimeMillis_;
        private MapFieldLite<String, Value> values_ = MapFieldLite.emptyMapField();

        public static final class Builder extends GeneratedMessageLite.Builder<SeriesValue, Builder> implements SeriesValueOrBuilder {
            /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
                this();
            }

            public Builder clearInstantTimeMillis() {
                d();
                ((SeriesValue) this.f4444a).clearInstantTimeMillis();
                return this;
            }

            public Builder clearValues() {
                d();
                ((SeriesValue) this.f4444a).getMutableValuesMap().clear();
                return this;
            }

            @Override // androidx.health.platform.client.proto.DataProto.SeriesValueOrBuilder
            public boolean containsValues(String str) {
                str.getClass();
                return ((SeriesValue) this.f4444a).getValuesMap().containsKey(str);
            }

            @Override // androidx.health.platform.client.proto.DataProto.SeriesValueOrBuilder
            public long getInstantTimeMillis() {
                return ((SeriesValue) this.f4444a).getInstantTimeMillis();
            }

            @Override // androidx.health.platform.client.proto.DataProto.SeriesValueOrBuilder
            @Deprecated
            public Map<String, Value> getValues() {
                return getValuesMap();
            }

            @Override // androidx.health.platform.client.proto.DataProto.SeriesValueOrBuilder
            public int getValuesCount() {
                return ((SeriesValue) this.f4444a).getValuesMap().size();
            }

            @Override // androidx.health.platform.client.proto.DataProto.SeriesValueOrBuilder
            public Map<String, Value> getValuesMap() {
                return Collections.unmodifiableMap(((SeriesValue) this.f4444a).getValuesMap());
            }

            @Override // androidx.health.platform.client.proto.DataProto.SeriesValueOrBuilder
            public Value getValuesOrDefault(String str, Value value) {
                str.getClass();
                Map<String, Value> valuesMap = ((SeriesValue) this.f4444a).getValuesMap();
                return valuesMap.containsKey(str) ? valuesMap.get(str) : value;
            }

            @Override // androidx.health.platform.client.proto.DataProto.SeriesValueOrBuilder
            public Value getValuesOrThrow(String str) {
                str.getClass();
                Map<String, Value> valuesMap = ((SeriesValue) this.f4444a).getValuesMap();
                if (valuesMap.containsKey(str)) {
                    return valuesMap.get(str);
                }
                throw new IllegalArgumentException();
            }

            @Override // androidx.health.platform.client.proto.DataProto.SeriesValueOrBuilder
            public boolean hasInstantTimeMillis() {
                return ((SeriesValue) this.f4444a).hasInstantTimeMillis();
            }

            public Builder putAllValues(Map<String, Value> map) {
                d();
                ((SeriesValue) this.f4444a).getMutableValuesMap().putAll(map);
                return this;
            }

            public Builder putValues(String str, Value value) {
                str.getClass();
                value.getClass();
                d();
                ((SeriesValue) this.f4444a).getMutableValuesMap().put(str, value);
                return this;
            }

            public Builder removeValues(String str) {
                str.getClass();
                d();
                ((SeriesValue) this.f4444a).getMutableValuesMap().remove(str);
                return this;
            }

            public Builder setInstantTimeMillis(long j2) {
                d();
                ((SeriesValue) this.f4444a).setInstantTimeMillis(j2);
                return this;
            }

            private Builder() {
                super(SeriesValue.DEFAULT_INSTANCE);
            }
        }

        private static final class ValuesDefaultEntryHolder {

            /* renamed from: a, reason: collision with root package name */
            static final MapEntryLite f4421a = MapEntryLite.newDefaultInstance(WireFormat.FieldType.STRING, "", WireFormat.FieldType.MESSAGE, Value.getDefaultInstance());

            private ValuesDefaultEntryHolder() {
            }
        }

        static {
            SeriesValue seriesValue = new SeriesValue();
            DEFAULT_INSTANCE = seriesValue;
            GeneratedMessageLite.T(SeriesValue.class, seriesValue);
        }

        private SeriesValue() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearInstantTimeMillis() {
            this.bitField0_ &= -2;
            this.instantTimeMillis_ = 0L;
        }

        public static SeriesValue getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Map<String, Value> getMutableValuesMap() {
            return internalGetMutableValues();
        }

        private MapFieldLite<String, Value> internalGetMutableValues() {
            if (!this.values_.isMutable()) {
                this.values_ = this.values_.mutableCopy();
            }
            return this.values_;
        }

        private MapFieldLite<String, Value> internalGetValues() {
            return this.values_;
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.m();
        }

        public static SeriesValue parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (SeriesValue) GeneratedMessageLite.F(DEFAULT_INSTANCE, inputStream);
        }

        public static SeriesValue parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (SeriesValue) GeneratedMessageLite.N(DEFAULT_INSTANCE, byteBuffer);
        }

        public static Parser<SeriesValue> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setInstantTimeMillis(long j2) {
            this.bitField0_ |= 1;
            this.instantTimeMillis_ = j2;
        }

        @Override // androidx.health.platform.client.proto.DataProto.SeriesValueOrBuilder
        public boolean containsValues(String str) {
            str.getClass();
            return internalGetValues().containsKey(str);
        }

        @Override // androidx.health.platform.client.proto.DataProto.SeriesValueOrBuilder
        public long getInstantTimeMillis() {
            return this.instantTimeMillis_;
        }

        @Override // androidx.health.platform.client.proto.DataProto.SeriesValueOrBuilder
        @Deprecated
        public Map<String, Value> getValues() {
            return getValuesMap();
        }

        @Override // androidx.health.platform.client.proto.DataProto.SeriesValueOrBuilder
        public int getValuesCount() {
            return internalGetValues().size();
        }

        @Override // androidx.health.platform.client.proto.DataProto.SeriesValueOrBuilder
        public Map<String, Value> getValuesMap() {
            return Collections.unmodifiableMap(internalGetValues());
        }

        @Override // androidx.health.platform.client.proto.DataProto.SeriesValueOrBuilder
        public Value getValuesOrDefault(String str, Value value) {
            str.getClass();
            MapFieldLite<String, Value> mapFieldLiteInternalGetValues = internalGetValues();
            return mapFieldLiteInternalGetValues.containsKey(str) ? mapFieldLiteInternalGetValues.get(str) : value;
        }

        @Override // androidx.health.platform.client.proto.DataProto.SeriesValueOrBuilder
        public Value getValuesOrThrow(String str) {
            str.getClass();
            MapFieldLite<String, Value> mapFieldLiteInternalGetValues = internalGetValues();
            if (mapFieldLiteInternalGetValues.containsKey(str)) {
                return mapFieldLiteInternalGetValues.get(str);
            }
            throw new IllegalArgumentException();
        }

        @Override // androidx.health.platform.client.proto.DataProto.SeriesValueOrBuilder
        public boolean hasInstantTimeMillis() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override // androidx.health.platform.client.proto.GeneratedMessageLite
        protected final Object q(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            AnonymousClass1 anonymousClass1 = null;
            switch (AnonymousClass1.f4415a[methodToInvoke.ordinal()]) {
                case 1:
                    return new SeriesValue();
                case 2:
                    return new Builder(anonymousClass1);
                case 3:
                    return GeneratedMessageLite.D(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0001\u0000\u0000\u00012\u0002ဂ\u0000", new Object[]{"bitField0_", "values_", ValuesDefaultEntryHolder.f4421a, "instantTimeMillis_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<SeriesValue> defaultInstanceBasedParser = PARSER;
                    if (defaultInstanceBasedParser == null) {
                        synchronized (SeriesValue.class) {
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

        public static Builder newBuilder(SeriesValue seriesValue) {
            return (Builder) DEFAULT_INSTANCE.n(seriesValue);
        }

        public static SeriesValue parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (SeriesValue) GeneratedMessageLite.G(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static SeriesValue parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (SeriesValue) GeneratedMessageLite.O(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static SeriesValue parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (SeriesValue) GeneratedMessageLite.H(DEFAULT_INSTANCE, byteString);
        }

        public static SeriesValue parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (SeriesValue) GeneratedMessageLite.I(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static SeriesValue parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (SeriesValue) GeneratedMessageLite.P(DEFAULT_INSTANCE, bArr);
        }

        public static SeriesValue parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (SeriesValue) GeneratedMessageLite.Q(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static SeriesValue parseFrom(InputStream inputStream) throws IOException {
            return (SeriesValue) GeneratedMessageLite.L(DEFAULT_INSTANCE, inputStream);
        }

        public static SeriesValue parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (SeriesValue) GeneratedMessageLite.M(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static SeriesValue parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (SeriesValue) GeneratedMessageLite.J(DEFAULT_INSTANCE, codedInputStream);
        }

        public static SeriesValue parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (SeriesValue) GeneratedMessageLite.K(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }
    }

    public interface SeriesValueOrBuilder extends MessageLiteOrBuilder {
        boolean containsValues(String str);

        long getInstantTimeMillis();

        @Deprecated
        Map<String, Value> getValues();

        int getValuesCount();

        Map<String, Value> getValuesMap();

        Value getValuesOrDefault(String str, Value value);

        Value getValuesOrThrow(String str);

        boolean hasInstantTimeMillis();
    }

    public static final class SubTypeDataValue extends GeneratedMessageLite<SubTypeDataValue, Builder> implements SubTypeDataValueOrBuilder {
        private static final SubTypeDataValue DEFAULT_INSTANCE;
        public static final int END_TIME_MILLIS_FIELD_NUMBER = 3;
        private static volatile Parser<SubTypeDataValue> PARSER = null;
        public static final int START_TIME_MILLIS_FIELD_NUMBER = 2;
        public static final int VALUES_FIELD_NUMBER = 1;
        private int bitField0_;
        private long endTimeMillis_;
        private long startTimeMillis_;
        private MapFieldLite<String, Value> values_ = MapFieldLite.emptyMapField();

        public static final class Builder extends GeneratedMessageLite.Builder<SubTypeDataValue, Builder> implements SubTypeDataValueOrBuilder {
            /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
                this();
            }

            public Builder clearEndTimeMillis() {
                d();
                ((SubTypeDataValue) this.f4444a).clearEndTimeMillis();
                return this;
            }

            public Builder clearStartTimeMillis() {
                d();
                ((SubTypeDataValue) this.f4444a).clearStartTimeMillis();
                return this;
            }

            public Builder clearValues() {
                d();
                ((SubTypeDataValue) this.f4444a).getMutableValuesMap().clear();
                return this;
            }

            @Override // androidx.health.platform.client.proto.DataProto.SubTypeDataValueOrBuilder
            public boolean containsValues(String str) {
                str.getClass();
                return ((SubTypeDataValue) this.f4444a).getValuesMap().containsKey(str);
            }

            @Override // androidx.health.platform.client.proto.DataProto.SubTypeDataValueOrBuilder
            public long getEndTimeMillis() {
                return ((SubTypeDataValue) this.f4444a).getEndTimeMillis();
            }

            @Override // androidx.health.platform.client.proto.DataProto.SubTypeDataValueOrBuilder
            public long getStartTimeMillis() {
                return ((SubTypeDataValue) this.f4444a).getStartTimeMillis();
            }

            @Override // androidx.health.platform.client.proto.DataProto.SubTypeDataValueOrBuilder
            @Deprecated
            public Map<String, Value> getValues() {
                return getValuesMap();
            }

            @Override // androidx.health.platform.client.proto.DataProto.SubTypeDataValueOrBuilder
            public int getValuesCount() {
                return ((SubTypeDataValue) this.f4444a).getValuesMap().size();
            }

            @Override // androidx.health.platform.client.proto.DataProto.SubTypeDataValueOrBuilder
            public Map<String, Value> getValuesMap() {
                return Collections.unmodifiableMap(((SubTypeDataValue) this.f4444a).getValuesMap());
            }

            @Override // androidx.health.platform.client.proto.DataProto.SubTypeDataValueOrBuilder
            public Value getValuesOrDefault(String str, Value value) {
                str.getClass();
                Map<String, Value> valuesMap = ((SubTypeDataValue) this.f4444a).getValuesMap();
                return valuesMap.containsKey(str) ? valuesMap.get(str) : value;
            }

            @Override // androidx.health.platform.client.proto.DataProto.SubTypeDataValueOrBuilder
            public Value getValuesOrThrow(String str) {
                str.getClass();
                Map<String, Value> valuesMap = ((SubTypeDataValue) this.f4444a).getValuesMap();
                if (valuesMap.containsKey(str)) {
                    return valuesMap.get(str);
                }
                throw new IllegalArgumentException();
            }

            @Override // androidx.health.platform.client.proto.DataProto.SubTypeDataValueOrBuilder
            public boolean hasEndTimeMillis() {
                return ((SubTypeDataValue) this.f4444a).hasEndTimeMillis();
            }

            @Override // androidx.health.platform.client.proto.DataProto.SubTypeDataValueOrBuilder
            public boolean hasStartTimeMillis() {
                return ((SubTypeDataValue) this.f4444a).hasStartTimeMillis();
            }

            public Builder putAllValues(Map<String, Value> map) {
                d();
                ((SubTypeDataValue) this.f4444a).getMutableValuesMap().putAll(map);
                return this;
            }

            public Builder putValues(String str, Value value) {
                str.getClass();
                value.getClass();
                d();
                ((SubTypeDataValue) this.f4444a).getMutableValuesMap().put(str, value);
                return this;
            }

            public Builder removeValues(String str) {
                str.getClass();
                d();
                ((SubTypeDataValue) this.f4444a).getMutableValuesMap().remove(str);
                return this;
            }

            public Builder setEndTimeMillis(long j2) {
                d();
                ((SubTypeDataValue) this.f4444a).setEndTimeMillis(j2);
                return this;
            }

            public Builder setStartTimeMillis(long j2) {
                d();
                ((SubTypeDataValue) this.f4444a).setStartTimeMillis(j2);
                return this;
            }

            private Builder() {
                super(SubTypeDataValue.DEFAULT_INSTANCE);
            }
        }

        private static final class ValuesDefaultEntryHolder {

            /* renamed from: a, reason: collision with root package name */
            static final MapEntryLite f4422a = MapEntryLite.newDefaultInstance(WireFormat.FieldType.STRING, "", WireFormat.FieldType.MESSAGE, Value.getDefaultInstance());

            private ValuesDefaultEntryHolder() {
            }
        }

        static {
            SubTypeDataValue subTypeDataValue = new SubTypeDataValue();
            DEFAULT_INSTANCE = subTypeDataValue;
            GeneratedMessageLite.T(SubTypeDataValue.class, subTypeDataValue);
        }

        private SubTypeDataValue() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearEndTimeMillis() {
            this.bitField0_ &= -3;
            this.endTimeMillis_ = 0L;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearStartTimeMillis() {
            this.bitField0_ &= -2;
            this.startTimeMillis_ = 0L;
        }

        public static SubTypeDataValue getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Map<String, Value> getMutableValuesMap() {
            return internalGetMutableValues();
        }

        private MapFieldLite<String, Value> internalGetMutableValues() {
            if (!this.values_.isMutable()) {
                this.values_ = this.values_.mutableCopy();
            }
            return this.values_;
        }

        private MapFieldLite<String, Value> internalGetValues() {
            return this.values_;
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.m();
        }

        public static SubTypeDataValue parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (SubTypeDataValue) GeneratedMessageLite.F(DEFAULT_INSTANCE, inputStream);
        }

        public static SubTypeDataValue parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (SubTypeDataValue) GeneratedMessageLite.N(DEFAULT_INSTANCE, byteBuffer);
        }

        public static Parser<SubTypeDataValue> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setEndTimeMillis(long j2) {
            this.bitField0_ |= 2;
            this.endTimeMillis_ = j2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setStartTimeMillis(long j2) {
            this.bitField0_ |= 1;
            this.startTimeMillis_ = j2;
        }

        @Override // androidx.health.platform.client.proto.DataProto.SubTypeDataValueOrBuilder
        public boolean containsValues(String str) {
            str.getClass();
            return internalGetValues().containsKey(str);
        }

        @Override // androidx.health.platform.client.proto.DataProto.SubTypeDataValueOrBuilder
        public long getEndTimeMillis() {
            return this.endTimeMillis_;
        }

        @Override // androidx.health.platform.client.proto.DataProto.SubTypeDataValueOrBuilder
        public long getStartTimeMillis() {
            return this.startTimeMillis_;
        }

        @Override // androidx.health.platform.client.proto.DataProto.SubTypeDataValueOrBuilder
        @Deprecated
        public Map<String, Value> getValues() {
            return getValuesMap();
        }

        @Override // androidx.health.platform.client.proto.DataProto.SubTypeDataValueOrBuilder
        public int getValuesCount() {
            return internalGetValues().size();
        }

        @Override // androidx.health.platform.client.proto.DataProto.SubTypeDataValueOrBuilder
        public Map<String, Value> getValuesMap() {
            return Collections.unmodifiableMap(internalGetValues());
        }

        @Override // androidx.health.platform.client.proto.DataProto.SubTypeDataValueOrBuilder
        public Value getValuesOrDefault(String str, Value value) {
            str.getClass();
            MapFieldLite<String, Value> mapFieldLiteInternalGetValues = internalGetValues();
            return mapFieldLiteInternalGetValues.containsKey(str) ? mapFieldLiteInternalGetValues.get(str) : value;
        }

        @Override // androidx.health.platform.client.proto.DataProto.SubTypeDataValueOrBuilder
        public Value getValuesOrThrow(String str) {
            str.getClass();
            MapFieldLite<String, Value> mapFieldLiteInternalGetValues = internalGetValues();
            if (mapFieldLiteInternalGetValues.containsKey(str)) {
                return mapFieldLiteInternalGetValues.get(str);
            }
            throw new IllegalArgumentException();
        }

        @Override // androidx.health.platform.client.proto.DataProto.SubTypeDataValueOrBuilder
        public boolean hasEndTimeMillis() {
            return (this.bitField0_ & 2) != 0;
        }

        @Override // androidx.health.platform.client.proto.DataProto.SubTypeDataValueOrBuilder
        public boolean hasStartTimeMillis() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override // androidx.health.platform.client.proto.GeneratedMessageLite
        protected final Object q(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            AnonymousClass1 anonymousClass1 = null;
            switch (AnonymousClass1.f4415a[methodToInvoke.ordinal()]) {
                case 1:
                    return new SubTypeDataValue();
                case 2:
                    return new Builder(anonymousClass1);
                case 3:
                    return GeneratedMessageLite.D(DEFAULT_INSTANCE, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0001\u0000\u0000\u00012\u0002ဂ\u0000\u0003ဂ\u0001", new Object[]{"bitField0_", "values_", ValuesDefaultEntryHolder.f4422a, "startTimeMillis_", "endTimeMillis_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<SubTypeDataValue> defaultInstanceBasedParser = PARSER;
                    if (defaultInstanceBasedParser == null) {
                        synchronized (SubTypeDataValue.class) {
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

        public static Builder newBuilder(SubTypeDataValue subTypeDataValue) {
            return (Builder) DEFAULT_INSTANCE.n(subTypeDataValue);
        }

        public static SubTypeDataValue parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (SubTypeDataValue) GeneratedMessageLite.G(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static SubTypeDataValue parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (SubTypeDataValue) GeneratedMessageLite.O(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static SubTypeDataValue parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (SubTypeDataValue) GeneratedMessageLite.H(DEFAULT_INSTANCE, byteString);
        }

        public static SubTypeDataValue parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (SubTypeDataValue) GeneratedMessageLite.I(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static SubTypeDataValue parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (SubTypeDataValue) GeneratedMessageLite.P(DEFAULT_INSTANCE, bArr);
        }

        public static SubTypeDataValue parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (SubTypeDataValue) GeneratedMessageLite.Q(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static SubTypeDataValue parseFrom(InputStream inputStream) throws IOException {
            return (SubTypeDataValue) GeneratedMessageLite.L(DEFAULT_INSTANCE, inputStream);
        }

        public static SubTypeDataValue parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (SubTypeDataValue) GeneratedMessageLite.M(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static SubTypeDataValue parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (SubTypeDataValue) GeneratedMessageLite.J(DEFAULT_INSTANCE, codedInputStream);
        }

        public static SubTypeDataValue parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (SubTypeDataValue) GeneratedMessageLite.K(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }
    }

    public interface SubTypeDataValueOrBuilder extends MessageLiteOrBuilder {
        boolean containsValues(String str);

        long getEndTimeMillis();

        long getStartTimeMillis();

        @Deprecated
        Map<String, Value> getValues();

        int getValuesCount();

        Map<String, Value> getValuesMap();

        Value getValuesOrDefault(String str, Value value);

        Value getValuesOrThrow(String str);

        boolean hasEndTimeMillis();

        boolean hasStartTimeMillis();
    }

    public static final class Value extends GeneratedMessageLite<Value, Builder> implements ValueOrBuilder {
        public static final int BOOLEAN_VAL_FIELD_NUMBER = 5;
        private static final Value DEFAULT_INSTANCE;
        public static final int DOUBLE_VAL_FIELD_NUMBER = 2;
        public static final int ENUM_VAL_FIELD_NUMBER = 4;
        public static final int LONG_VAL_FIELD_NUMBER = 1;
        private static volatile Parser<Value> PARSER = null;
        public static final int STRING_VAL_FIELD_NUMBER = 3;
        private int bitField0_;
        private int valueCase_ = 0;
        private Object value_;

        public static final class Builder extends GeneratedMessageLite.Builder<Value, Builder> implements ValueOrBuilder {
            /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
                this();
            }

            public Builder clearBooleanVal() {
                d();
                ((Value) this.f4444a).clearBooleanVal();
                return this;
            }

            public Builder clearDoubleVal() {
                d();
                ((Value) this.f4444a).clearDoubleVal();
                return this;
            }

            public Builder clearEnumVal() {
                d();
                ((Value) this.f4444a).clearEnumVal();
                return this;
            }

            public Builder clearLongVal() {
                d();
                ((Value) this.f4444a).clearLongVal();
                return this;
            }

            public Builder clearStringVal() {
                d();
                ((Value) this.f4444a).clearStringVal();
                return this;
            }

            public Builder clearValue() {
                d();
                ((Value) this.f4444a).clearValue();
                return this;
            }

            @Override // androidx.health.platform.client.proto.DataProto.ValueOrBuilder
            public boolean getBooleanVal() {
                return ((Value) this.f4444a).getBooleanVal();
            }

            @Override // androidx.health.platform.client.proto.DataProto.ValueOrBuilder
            public double getDoubleVal() {
                return ((Value) this.f4444a).getDoubleVal();
            }

            @Override // androidx.health.platform.client.proto.DataProto.ValueOrBuilder
            public String getEnumVal() {
                return ((Value) this.f4444a).getEnumVal();
            }

            @Override // androidx.health.platform.client.proto.DataProto.ValueOrBuilder
            public ByteString getEnumValBytes() {
                return ((Value) this.f4444a).getEnumValBytes();
            }

            @Override // androidx.health.platform.client.proto.DataProto.ValueOrBuilder
            public long getLongVal() {
                return ((Value) this.f4444a).getLongVal();
            }

            @Override // androidx.health.platform.client.proto.DataProto.ValueOrBuilder
            public String getStringVal() {
                return ((Value) this.f4444a).getStringVal();
            }

            @Override // androidx.health.platform.client.proto.DataProto.ValueOrBuilder
            public ByteString getStringValBytes() {
                return ((Value) this.f4444a).getStringValBytes();
            }

            @Override // androidx.health.platform.client.proto.DataProto.ValueOrBuilder
            public ValueCase getValueCase() {
                return ((Value) this.f4444a).getValueCase();
            }

            @Override // androidx.health.platform.client.proto.DataProto.ValueOrBuilder
            public boolean hasBooleanVal() {
                return ((Value) this.f4444a).hasBooleanVal();
            }

            @Override // androidx.health.platform.client.proto.DataProto.ValueOrBuilder
            public boolean hasDoubleVal() {
                return ((Value) this.f4444a).hasDoubleVal();
            }

            @Override // androidx.health.platform.client.proto.DataProto.ValueOrBuilder
            public boolean hasEnumVal() {
                return ((Value) this.f4444a).hasEnumVal();
            }

            @Override // androidx.health.platform.client.proto.DataProto.ValueOrBuilder
            public boolean hasLongVal() {
                return ((Value) this.f4444a).hasLongVal();
            }

            @Override // androidx.health.platform.client.proto.DataProto.ValueOrBuilder
            public boolean hasStringVal() {
                return ((Value) this.f4444a).hasStringVal();
            }

            public Builder setBooleanVal(boolean z2) {
                d();
                ((Value) this.f4444a).setBooleanVal(z2);
                return this;
            }

            public Builder setDoubleVal(double d2) {
                d();
                ((Value) this.f4444a).setDoubleVal(d2);
                return this;
            }

            public Builder setEnumVal(String str) {
                d();
                ((Value) this.f4444a).setEnumVal(str);
                return this;
            }

            public Builder setEnumValBytes(ByteString byteString) {
                d();
                ((Value) this.f4444a).setEnumValBytes(byteString);
                return this;
            }

            public Builder setLongVal(long j2) {
                d();
                ((Value) this.f4444a).setLongVal(j2);
                return this;
            }

            public Builder setStringVal(String str) {
                d();
                ((Value) this.f4444a).setStringVal(str);
                return this;
            }

            public Builder setStringValBytes(ByteString byteString) {
                d();
                ((Value) this.f4444a).setStringValBytes(byteString);
                return this;
            }

            private Builder() {
                super(Value.DEFAULT_INSTANCE);
            }
        }

        public enum ValueCase {
            LONG_VAL(1),
            DOUBLE_VAL(2),
            STRING_VAL(3),
            ENUM_VAL(4),
            BOOLEAN_VAL(5),
            VALUE_NOT_SET(0);

            private final int value;

            ValueCase(int i2) {
                this.value = i2;
            }

            public static ValueCase forNumber(int i2) {
                if (i2 == 0) {
                    return VALUE_NOT_SET;
                }
                if (i2 == 1) {
                    return LONG_VAL;
                }
                if (i2 == 2) {
                    return DOUBLE_VAL;
                }
                if (i2 == 3) {
                    return STRING_VAL;
                }
                if (i2 == 4) {
                    return ENUM_VAL;
                }
                if (i2 != 5) {
                    return null;
                }
                return BOOLEAN_VAL;
            }

            public int getNumber() {
                return this.value;
            }

            @Deprecated
            public static ValueCase valueOf(int i2) {
                return forNumber(i2);
            }
        }

        static {
            Value value = new Value();
            DEFAULT_INSTANCE = value;
            GeneratedMessageLite.T(Value.class, value);
        }

        private Value() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearBooleanVal() {
            if (this.valueCase_ == 5) {
                this.valueCase_ = 0;
                this.value_ = null;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearDoubleVal() {
            if (this.valueCase_ == 2) {
                this.valueCase_ = 0;
                this.value_ = null;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearEnumVal() {
            if (this.valueCase_ == 4) {
                this.valueCase_ = 0;
                this.value_ = null;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearLongVal() {
            if (this.valueCase_ == 1) {
                this.valueCase_ = 0;
                this.value_ = null;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearStringVal() {
            if (this.valueCase_ == 3) {
                this.valueCase_ = 0;
                this.value_ = null;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearValue() {
            this.valueCase_ = 0;
            this.value_ = null;
        }

        public static Value getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.m();
        }

        public static Value parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (Value) GeneratedMessageLite.F(DEFAULT_INSTANCE, inputStream);
        }

        public static Value parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (Value) GeneratedMessageLite.N(DEFAULT_INSTANCE, byteBuffer);
        }

        public static Parser<Value> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setBooleanVal(boolean z2) {
            this.valueCase_ = 5;
            this.value_ = Boolean.valueOf(z2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setDoubleVal(double d2) {
            this.valueCase_ = 2;
            this.value_ = Double.valueOf(d2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setEnumVal(String str) {
            str.getClass();
            this.valueCase_ = 4;
            this.value_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setEnumValBytes(ByteString byteString) {
            this.value_ = byteString.toStringUtf8();
            this.valueCase_ = 4;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setLongVal(long j2) {
            this.valueCase_ = 1;
            this.value_ = Long.valueOf(j2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setStringVal(String str) {
            str.getClass();
            this.valueCase_ = 3;
            this.value_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setStringValBytes(ByteString byteString) {
            this.value_ = byteString.toStringUtf8();
            this.valueCase_ = 3;
        }

        @Override // androidx.health.platform.client.proto.DataProto.ValueOrBuilder
        public boolean getBooleanVal() {
            if (this.valueCase_ == 5) {
                return ((Boolean) this.value_).booleanValue();
            }
            return false;
        }

        @Override // androidx.health.platform.client.proto.DataProto.ValueOrBuilder
        public double getDoubleVal() {
            if (this.valueCase_ == 2) {
                return ((Double) this.value_).doubleValue();
            }
            return 0.0d;
        }

        @Override // androidx.health.platform.client.proto.DataProto.ValueOrBuilder
        public String getEnumVal() {
            return this.valueCase_ == 4 ? (String) this.value_ : "";
        }

        @Override // androidx.health.platform.client.proto.DataProto.ValueOrBuilder
        public ByteString getEnumValBytes() {
            return ByteString.copyFromUtf8(this.valueCase_ == 4 ? (String) this.value_ : "");
        }

        @Override // androidx.health.platform.client.proto.DataProto.ValueOrBuilder
        public long getLongVal() {
            if (this.valueCase_ == 1) {
                return ((Long) this.value_).longValue();
            }
            return 0L;
        }

        @Override // androidx.health.platform.client.proto.DataProto.ValueOrBuilder
        public String getStringVal() {
            return this.valueCase_ == 3 ? (String) this.value_ : "";
        }

        @Override // androidx.health.platform.client.proto.DataProto.ValueOrBuilder
        public ByteString getStringValBytes() {
            return ByteString.copyFromUtf8(this.valueCase_ == 3 ? (String) this.value_ : "");
        }

        @Override // androidx.health.platform.client.proto.DataProto.ValueOrBuilder
        public ValueCase getValueCase() {
            return ValueCase.forNumber(this.valueCase_);
        }

        @Override // androidx.health.platform.client.proto.DataProto.ValueOrBuilder
        public boolean hasBooleanVal() {
            return this.valueCase_ == 5;
        }

        @Override // androidx.health.platform.client.proto.DataProto.ValueOrBuilder
        public boolean hasDoubleVal() {
            return this.valueCase_ == 2;
        }

        @Override // androidx.health.platform.client.proto.DataProto.ValueOrBuilder
        public boolean hasEnumVal() {
            return this.valueCase_ == 4;
        }

        @Override // androidx.health.platform.client.proto.DataProto.ValueOrBuilder
        public boolean hasLongVal() {
            return this.valueCase_ == 1;
        }

        @Override // androidx.health.platform.client.proto.DataProto.ValueOrBuilder
        public boolean hasStringVal() {
            return this.valueCase_ == 3;
        }

        @Override // androidx.health.platform.client.proto.GeneratedMessageLite
        protected final Object q(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            AnonymousClass1 anonymousClass1 = null;
            switch (AnonymousClass1.f4415a[methodToInvoke.ordinal()]) {
                case 1:
                    return new Value();
                case 2:
                    return new Builder(anonymousClass1);
                case 3:
                    return GeneratedMessageLite.D(DEFAULT_INSTANCE, "\u0001\u0005\u0001\u0001\u0001\u0005\u0005\u0000\u0000\u0000\u0001ဵ\u0000\u0002ဳ\u0000\u0003ျ\u0000\u0004ျ\u0000\u0005်\u0000", new Object[]{"value_", "valueCase_", "bitField0_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<Value> defaultInstanceBasedParser = PARSER;
                    if (defaultInstanceBasedParser == null) {
                        synchronized (Value.class) {
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

        public static Builder newBuilder(Value value) {
            return (Builder) DEFAULT_INSTANCE.n(value);
        }

        public static Value parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Value) GeneratedMessageLite.G(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static Value parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Value) GeneratedMessageLite.O(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static Value parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (Value) GeneratedMessageLite.H(DEFAULT_INSTANCE, byteString);
        }

        public static Value parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Value) GeneratedMessageLite.I(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static Value parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (Value) GeneratedMessageLite.P(DEFAULT_INSTANCE, bArr);
        }

        public static Value parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Value) GeneratedMessageLite.Q(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static Value parseFrom(InputStream inputStream) throws IOException {
            return (Value) GeneratedMessageLite.L(DEFAULT_INSTANCE, inputStream);
        }

        public static Value parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Value) GeneratedMessageLite.M(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static Value parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (Value) GeneratedMessageLite.J(DEFAULT_INSTANCE, codedInputStream);
        }

        public static Value parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Value) GeneratedMessageLite.K(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }
    }

    public interface ValueOrBuilder extends MessageLiteOrBuilder {
        boolean getBooleanVal();

        double getDoubleVal();

        String getEnumVal();

        ByteString getEnumValBytes();

        long getLongVal();

        String getStringVal();

        ByteString getStringValBytes();

        Value.ValueCase getValueCase();

        boolean hasBooleanVal();

        boolean hasDoubleVal();

        boolean hasEnumVal();

        boolean hasLongVal();

        boolean hasStringVal();
    }

    private DataProto() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite extensionRegistryLite) {
    }
}
