package androidx.health.platform.client.proto;

import androidx.health.platform.client.proto.ChangeProto;
import androidx.health.platform.client.proto.DataProto;
import androidx.health.platform.client.proto.GeneratedMessageLite;
import androidx.health.platform.client.proto.Internal;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public final class ResponseProto {

    /* renamed from: androidx.health.platform.client.proto.ResponseProto$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f4478a;

        static {
            int[] iArr = new int[GeneratedMessageLite.MethodToInvoke.values().length];
            f4478a = iArr;
            try {
                iArr[GeneratedMessageLite.MethodToInvoke.NEW_MUTABLE_INSTANCE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f4478a[GeneratedMessageLite.MethodToInvoke.NEW_BUILDER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f4478a[GeneratedMessageLite.MethodToInvoke.BUILD_MESSAGE_INFO.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f4478a[GeneratedMessageLite.MethodToInvoke.GET_DEFAULT_INSTANCE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f4478a[GeneratedMessageLite.MethodToInvoke.GET_PARSER.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f4478a[GeneratedMessageLite.MethodToInvoke.GET_MEMOIZED_IS_INITIALIZED.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f4478a[GeneratedMessageLite.MethodToInvoke.SET_MEMOIZED_IS_INITIALIZED.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    public static final class AggregateDataResponse extends GeneratedMessageLite<AggregateDataResponse, Builder> implements AggregateDataResponseOrBuilder {
        private static final AggregateDataResponse DEFAULT_INSTANCE;
        private static volatile Parser<AggregateDataResponse> PARSER = null;
        public static final int ROWS_FIELD_NUMBER = 1;
        private Internal.ProtobufList<DataProto.AggregateDataRow> rows_ = GeneratedMessageLite.r();

        public static final class Builder extends GeneratedMessageLite.Builder<AggregateDataResponse, Builder> implements AggregateDataResponseOrBuilder {
            /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
                this();
            }

            public Builder addAllRows(Iterable<? extends DataProto.AggregateDataRow> iterable) {
                d();
                ((AggregateDataResponse) this.f4444a).addAllRows(iterable);
                return this;
            }

            public Builder addRows(DataProto.AggregateDataRow aggregateDataRow) {
                d();
                ((AggregateDataResponse) this.f4444a).addRows(aggregateDataRow);
                return this;
            }

            public Builder clearRows() {
                d();
                ((AggregateDataResponse) this.f4444a).clearRows();
                return this;
            }

            @Override // androidx.health.platform.client.proto.ResponseProto.AggregateDataResponseOrBuilder
            public DataProto.AggregateDataRow getRows(int i2) {
                return ((AggregateDataResponse) this.f4444a).getRows(i2);
            }

            @Override // androidx.health.platform.client.proto.ResponseProto.AggregateDataResponseOrBuilder
            public int getRowsCount() {
                return ((AggregateDataResponse) this.f4444a).getRowsCount();
            }

            @Override // androidx.health.platform.client.proto.ResponseProto.AggregateDataResponseOrBuilder
            public List<DataProto.AggregateDataRow> getRowsList() {
                return Collections.unmodifiableList(((AggregateDataResponse) this.f4444a).getRowsList());
            }

            public Builder removeRows(int i2) {
                d();
                ((AggregateDataResponse) this.f4444a).removeRows(i2);
                return this;
            }

            public Builder setRows(int i2, DataProto.AggregateDataRow aggregateDataRow) {
                d();
                ((AggregateDataResponse) this.f4444a).setRows(i2, aggregateDataRow);
                return this;
            }

            private Builder() {
                super(AggregateDataResponse.DEFAULT_INSTANCE);
            }

            public Builder addRows(int i2, DataProto.AggregateDataRow aggregateDataRow) {
                d();
                ((AggregateDataResponse) this.f4444a).addRows(i2, aggregateDataRow);
                return this;
            }

            public Builder setRows(int i2, DataProto.AggregateDataRow.Builder builder) {
                d();
                ((AggregateDataResponse) this.f4444a).setRows(i2, builder.build());
                return this;
            }

            public Builder addRows(DataProto.AggregateDataRow.Builder builder) {
                d();
                ((AggregateDataResponse) this.f4444a).addRows(builder.build());
                return this;
            }

            public Builder addRows(int i2, DataProto.AggregateDataRow.Builder builder) {
                d();
                ((AggregateDataResponse) this.f4444a).addRows(i2, builder.build());
                return this;
            }
        }

        static {
            AggregateDataResponse aggregateDataResponse = new AggregateDataResponse();
            DEFAULT_INSTANCE = aggregateDataResponse;
            GeneratedMessageLite.T(AggregateDataResponse.class, aggregateDataResponse);
        }

        private AggregateDataResponse() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addAllRows(Iterable<? extends DataProto.AggregateDataRow> iterable) {
            ensureRowsIsMutable();
            AbstractMessageLite.a(iterable, this.rows_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addRows(DataProto.AggregateDataRow aggregateDataRow) {
            aggregateDataRow.getClass();
            ensureRowsIsMutable();
            this.rows_.add(aggregateDataRow);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearRows() {
            this.rows_ = GeneratedMessageLite.r();
        }

        private void ensureRowsIsMutable() {
            Internal.ProtobufList<DataProto.AggregateDataRow> protobufList = this.rows_;
            if (protobufList.isModifiable()) {
                return;
            }
            this.rows_ = GeneratedMessageLite.C(protobufList);
        }

        public static AggregateDataResponse getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.m();
        }

        public static AggregateDataResponse parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (AggregateDataResponse) GeneratedMessageLite.F(DEFAULT_INSTANCE, inputStream);
        }

        public static AggregateDataResponse parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (AggregateDataResponse) GeneratedMessageLite.N(DEFAULT_INSTANCE, byteBuffer);
        }

        public static Parser<AggregateDataResponse> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void removeRows(int i2) {
            ensureRowsIsMutable();
            this.rows_.remove(i2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setRows(int i2, DataProto.AggregateDataRow aggregateDataRow) {
            aggregateDataRow.getClass();
            ensureRowsIsMutable();
            this.rows_.set(i2, aggregateDataRow);
        }

        @Override // androidx.health.platform.client.proto.ResponseProto.AggregateDataResponseOrBuilder
        public DataProto.AggregateDataRow getRows(int i2) {
            return this.rows_.get(i2);
        }

        @Override // androidx.health.platform.client.proto.ResponseProto.AggregateDataResponseOrBuilder
        public int getRowsCount() {
            return this.rows_.size();
        }

        @Override // androidx.health.platform.client.proto.ResponseProto.AggregateDataResponseOrBuilder
        public List<DataProto.AggregateDataRow> getRowsList() {
            return this.rows_;
        }

        public DataProto.AggregateDataRowOrBuilder getRowsOrBuilder(int i2) {
            return this.rows_.get(i2);
        }

        public List<? extends DataProto.AggregateDataRowOrBuilder> getRowsOrBuilderList() {
            return this.rows_;
        }

        @Override // androidx.health.platform.client.proto.GeneratedMessageLite
        protected final Object q(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            AnonymousClass1 anonymousClass1 = null;
            switch (AnonymousClass1.f4478a[methodToInvoke.ordinal()]) {
                case 1:
                    return new AggregateDataResponse();
                case 2:
                    return new Builder(anonymousClass1);
                case 3:
                    return GeneratedMessageLite.D(DEFAULT_INSTANCE, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b", new Object[]{"rows_", DataProto.AggregateDataRow.class});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<AggregateDataResponse> defaultInstanceBasedParser = PARSER;
                    if (defaultInstanceBasedParser == null) {
                        synchronized (AggregateDataResponse.class) {
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

        public static Builder newBuilder(AggregateDataResponse aggregateDataResponse) {
            return (Builder) DEFAULT_INSTANCE.n(aggregateDataResponse);
        }

        public static AggregateDataResponse parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (AggregateDataResponse) GeneratedMessageLite.G(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static AggregateDataResponse parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (AggregateDataResponse) GeneratedMessageLite.O(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static AggregateDataResponse parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (AggregateDataResponse) GeneratedMessageLite.H(DEFAULT_INSTANCE, byteString);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addRows(int i2, DataProto.AggregateDataRow aggregateDataRow) {
            aggregateDataRow.getClass();
            ensureRowsIsMutable();
            this.rows_.add(i2, aggregateDataRow);
        }

        public static AggregateDataResponse parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (AggregateDataResponse) GeneratedMessageLite.I(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static AggregateDataResponse parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (AggregateDataResponse) GeneratedMessageLite.P(DEFAULT_INSTANCE, bArr);
        }

        public static AggregateDataResponse parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (AggregateDataResponse) GeneratedMessageLite.Q(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static AggregateDataResponse parseFrom(InputStream inputStream) throws IOException {
            return (AggregateDataResponse) GeneratedMessageLite.L(DEFAULT_INSTANCE, inputStream);
        }

        public static AggregateDataResponse parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (AggregateDataResponse) GeneratedMessageLite.M(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static AggregateDataResponse parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (AggregateDataResponse) GeneratedMessageLite.J(DEFAULT_INSTANCE, codedInputStream);
        }

        public static AggregateDataResponse parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (AggregateDataResponse) GeneratedMessageLite.K(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }
    }

    public interface AggregateDataResponseOrBuilder extends MessageLiteOrBuilder {
        DataProto.AggregateDataRow getRows(int i2);

        int getRowsCount();

        List<DataProto.AggregateDataRow> getRowsList();
    }

    public static final class GetChangesResponse extends GeneratedMessageLite<GetChangesResponse, Builder> implements GetChangesResponseOrBuilder {
        public static final int CHANGES_FIELD_NUMBER = 1;
        public static final int CHANGES_TOKEN_EXPIRED_FIELD_NUMBER = 4;
        private static final GetChangesResponse DEFAULT_INSTANCE;
        public static final int HAS_MORE_FIELD_NUMBER = 2;
        public static final int NEXT_CHANGES_TOKEN_FIELD_NUMBER = 3;
        private static volatile Parser<GetChangesResponse> PARSER;
        private int bitField0_;
        private boolean changesTokenExpired_;
        private boolean hasMore_;
        private Internal.ProtobufList<ChangeProto.DataChange> changes_ = GeneratedMessageLite.r();
        private String nextChangesToken_ = "";

        public static final class Builder extends GeneratedMessageLite.Builder<GetChangesResponse, Builder> implements GetChangesResponseOrBuilder {
            /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
                this();
            }

            public Builder addAllChanges(Iterable<? extends ChangeProto.DataChange> iterable) {
                d();
                ((GetChangesResponse) this.f4444a).addAllChanges(iterable);
                return this;
            }

            public Builder addChanges(ChangeProto.DataChange dataChange) {
                d();
                ((GetChangesResponse) this.f4444a).addChanges(dataChange);
                return this;
            }

            public Builder clearChanges() {
                d();
                ((GetChangesResponse) this.f4444a).clearChanges();
                return this;
            }

            public Builder clearChangesTokenExpired() {
                d();
                ((GetChangesResponse) this.f4444a).clearChangesTokenExpired();
                return this;
            }

            public Builder clearHasMore() {
                d();
                ((GetChangesResponse) this.f4444a).clearHasMore();
                return this;
            }

            public Builder clearNextChangesToken() {
                d();
                ((GetChangesResponse) this.f4444a).clearNextChangesToken();
                return this;
            }

            @Override // androidx.health.platform.client.proto.ResponseProto.GetChangesResponseOrBuilder
            public ChangeProto.DataChange getChanges(int i2) {
                return ((GetChangesResponse) this.f4444a).getChanges(i2);
            }

            @Override // androidx.health.platform.client.proto.ResponseProto.GetChangesResponseOrBuilder
            public int getChangesCount() {
                return ((GetChangesResponse) this.f4444a).getChangesCount();
            }

            @Override // androidx.health.platform.client.proto.ResponseProto.GetChangesResponseOrBuilder
            public List<ChangeProto.DataChange> getChangesList() {
                return Collections.unmodifiableList(((GetChangesResponse) this.f4444a).getChangesList());
            }

            @Override // androidx.health.platform.client.proto.ResponseProto.GetChangesResponseOrBuilder
            public boolean getChangesTokenExpired() {
                return ((GetChangesResponse) this.f4444a).getChangesTokenExpired();
            }

            @Override // androidx.health.platform.client.proto.ResponseProto.GetChangesResponseOrBuilder
            public boolean getHasMore() {
                return ((GetChangesResponse) this.f4444a).getHasMore();
            }

            @Override // androidx.health.platform.client.proto.ResponseProto.GetChangesResponseOrBuilder
            public String getNextChangesToken() {
                return ((GetChangesResponse) this.f4444a).getNextChangesToken();
            }

            @Override // androidx.health.platform.client.proto.ResponseProto.GetChangesResponseOrBuilder
            public ByteString getNextChangesTokenBytes() {
                return ((GetChangesResponse) this.f4444a).getNextChangesTokenBytes();
            }

            @Override // androidx.health.platform.client.proto.ResponseProto.GetChangesResponseOrBuilder
            public boolean hasChangesTokenExpired() {
                return ((GetChangesResponse) this.f4444a).hasChangesTokenExpired();
            }

            @Override // androidx.health.platform.client.proto.ResponseProto.GetChangesResponseOrBuilder
            public boolean hasHasMore() {
                return ((GetChangesResponse) this.f4444a).hasHasMore();
            }

            @Override // androidx.health.platform.client.proto.ResponseProto.GetChangesResponseOrBuilder
            public boolean hasNextChangesToken() {
                return ((GetChangesResponse) this.f4444a).hasNextChangesToken();
            }

            public Builder removeChanges(int i2) {
                d();
                ((GetChangesResponse) this.f4444a).removeChanges(i2);
                return this;
            }

            public Builder setChanges(int i2, ChangeProto.DataChange dataChange) {
                d();
                ((GetChangesResponse) this.f4444a).setChanges(i2, dataChange);
                return this;
            }

            public Builder setChangesTokenExpired(boolean z2) {
                d();
                ((GetChangesResponse) this.f4444a).setChangesTokenExpired(z2);
                return this;
            }

            public Builder setHasMore(boolean z2) {
                d();
                ((GetChangesResponse) this.f4444a).setHasMore(z2);
                return this;
            }

            public Builder setNextChangesToken(String str) {
                d();
                ((GetChangesResponse) this.f4444a).setNextChangesToken(str);
                return this;
            }

            public Builder setNextChangesTokenBytes(ByteString byteString) {
                d();
                ((GetChangesResponse) this.f4444a).setNextChangesTokenBytes(byteString);
                return this;
            }

            private Builder() {
                super(GetChangesResponse.DEFAULT_INSTANCE);
            }

            public Builder addChanges(int i2, ChangeProto.DataChange dataChange) {
                d();
                ((GetChangesResponse) this.f4444a).addChanges(i2, dataChange);
                return this;
            }

            public Builder setChanges(int i2, ChangeProto.DataChange.Builder builder) {
                d();
                ((GetChangesResponse) this.f4444a).setChanges(i2, builder.build());
                return this;
            }

            public Builder addChanges(ChangeProto.DataChange.Builder builder) {
                d();
                ((GetChangesResponse) this.f4444a).addChanges(builder.build());
                return this;
            }

            public Builder addChanges(int i2, ChangeProto.DataChange.Builder builder) {
                d();
                ((GetChangesResponse) this.f4444a).addChanges(i2, builder.build());
                return this;
            }
        }

        static {
            GetChangesResponse getChangesResponse = new GetChangesResponse();
            DEFAULT_INSTANCE = getChangesResponse;
            GeneratedMessageLite.T(GetChangesResponse.class, getChangesResponse);
        }

        private GetChangesResponse() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addAllChanges(Iterable<? extends ChangeProto.DataChange> iterable) {
            ensureChangesIsMutable();
            AbstractMessageLite.a(iterable, this.changes_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addChanges(ChangeProto.DataChange dataChange) {
            dataChange.getClass();
            ensureChangesIsMutable();
            this.changes_.add(dataChange);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearChanges() {
            this.changes_ = GeneratedMessageLite.r();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearChangesTokenExpired() {
            this.bitField0_ &= -5;
            this.changesTokenExpired_ = false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearHasMore() {
            this.bitField0_ &= -2;
            this.hasMore_ = false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearNextChangesToken() {
            this.bitField0_ &= -3;
            this.nextChangesToken_ = getDefaultInstance().getNextChangesToken();
        }

        private void ensureChangesIsMutable() {
            Internal.ProtobufList<ChangeProto.DataChange> protobufList = this.changes_;
            if (protobufList.isModifiable()) {
                return;
            }
            this.changes_ = GeneratedMessageLite.C(protobufList);
        }

        public static GetChangesResponse getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.m();
        }

        public static GetChangesResponse parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (GetChangesResponse) GeneratedMessageLite.F(DEFAULT_INSTANCE, inputStream);
        }

        public static GetChangesResponse parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (GetChangesResponse) GeneratedMessageLite.N(DEFAULT_INSTANCE, byteBuffer);
        }

        public static Parser<GetChangesResponse> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void removeChanges(int i2) {
            ensureChangesIsMutable();
            this.changes_.remove(i2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setChanges(int i2, ChangeProto.DataChange dataChange) {
            dataChange.getClass();
            ensureChangesIsMutable();
            this.changes_.set(i2, dataChange);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setChangesTokenExpired(boolean z2) {
            this.bitField0_ |= 4;
            this.changesTokenExpired_ = z2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setHasMore(boolean z2) {
            this.bitField0_ |= 1;
            this.hasMore_ = z2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setNextChangesToken(String str) {
            str.getClass();
            this.bitField0_ |= 2;
            this.nextChangesToken_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setNextChangesTokenBytes(ByteString byteString) {
            this.nextChangesToken_ = byteString.toStringUtf8();
            this.bitField0_ |= 2;
        }

        @Override // androidx.health.platform.client.proto.ResponseProto.GetChangesResponseOrBuilder
        public ChangeProto.DataChange getChanges(int i2) {
            return this.changes_.get(i2);
        }

        @Override // androidx.health.platform.client.proto.ResponseProto.GetChangesResponseOrBuilder
        public int getChangesCount() {
            return this.changes_.size();
        }

        @Override // androidx.health.platform.client.proto.ResponseProto.GetChangesResponseOrBuilder
        public List<ChangeProto.DataChange> getChangesList() {
            return this.changes_;
        }

        public ChangeProto.DataChangeOrBuilder getChangesOrBuilder(int i2) {
            return this.changes_.get(i2);
        }

        public List<? extends ChangeProto.DataChangeOrBuilder> getChangesOrBuilderList() {
            return this.changes_;
        }

        @Override // androidx.health.platform.client.proto.ResponseProto.GetChangesResponseOrBuilder
        public boolean getChangesTokenExpired() {
            return this.changesTokenExpired_;
        }

        @Override // androidx.health.platform.client.proto.ResponseProto.GetChangesResponseOrBuilder
        public boolean getHasMore() {
            return this.hasMore_;
        }

        @Override // androidx.health.platform.client.proto.ResponseProto.GetChangesResponseOrBuilder
        public String getNextChangesToken() {
            return this.nextChangesToken_;
        }

        @Override // androidx.health.platform.client.proto.ResponseProto.GetChangesResponseOrBuilder
        public ByteString getNextChangesTokenBytes() {
            return ByteString.copyFromUtf8(this.nextChangesToken_);
        }

        @Override // androidx.health.platform.client.proto.ResponseProto.GetChangesResponseOrBuilder
        public boolean hasChangesTokenExpired() {
            return (this.bitField0_ & 4) != 0;
        }

        @Override // androidx.health.platform.client.proto.ResponseProto.GetChangesResponseOrBuilder
        public boolean hasHasMore() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override // androidx.health.platform.client.proto.ResponseProto.GetChangesResponseOrBuilder
        public boolean hasNextChangesToken() {
            return (this.bitField0_ & 2) != 0;
        }

        @Override // androidx.health.platform.client.proto.GeneratedMessageLite
        protected final Object q(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            AnonymousClass1 anonymousClass1 = null;
            switch (AnonymousClass1.f4478a[methodToInvoke.ordinal()]) {
                case 1:
                    return new GetChangesResponse();
                case 2:
                    return new Builder(anonymousClass1);
                case 3:
                    return GeneratedMessageLite.D(DEFAULT_INSTANCE, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0001\u0000\u0001\u001b\u0002ဇ\u0000\u0003ဈ\u0001\u0004ဇ\u0002", new Object[]{"bitField0_", "changes_", ChangeProto.DataChange.class, "hasMore_", "nextChangesToken_", "changesTokenExpired_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<GetChangesResponse> defaultInstanceBasedParser = PARSER;
                    if (defaultInstanceBasedParser == null) {
                        synchronized (GetChangesResponse.class) {
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

        public static Builder newBuilder(GetChangesResponse getChangesResponse) {
            return (Builder) DEFAULT_INSTANCE.n(getChangesResponse);
        }

        public static GetChangesResponse parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (GetChangesResponse) GeneratedMessageLite.G(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static GetChangesResponse parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (GetChangesResponse) GeneratedMessageLite.O(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static GetChangesResponse parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (GetChangesResponse) GeneratedMessageLite.H(DEFAULT_INSTANCE, byteString);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addChanges(int i2, ChangeProto.DataChange dataChange) {
            dataChange.getClass();
            ensureChangesIsMutable();
            this.changes_.add(i2, dataChange);
        }

        public static GetChangesResponse parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (GetChangesResponse) GeneratedMessageLite.I(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static GetChangesResponse parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (GetChangesResponse) GeneratedMessageLite.P(DEFAULT_INSTANCE, bArr);
        }

        public static GetChangesResponse parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (GetChangesResponse) GeneratedMessageLite.Q(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static GetChangesResponse parseFrom(InputStream inputStream) throws IOException {
            return (GetChangesResponse) GeneratedMessageLite.L(DEFAULT_INSTANCE, inputStream);
        }

        public static GetChangesResponse parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (GetChangesResponse) GeneratedMessageLite.M(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static GetChangesResponse parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (GetChangesResponse) GeneratedMessageLite.J(DEFAULT_INSTANCE, codedInputStream);
        }

        public static GetChangesResponse parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (GetChangesResponse) GeneratedMessageLite.K(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }
    }

    public interface GetChangesResponseOrBuilder extends MessageLiteOrBuilder {
        ChangeProto.DataChange getChanges(int i2);

        int getChangesCount();

        List<ChangeProto.DataChange> getChangesList();

        boolean getChangesTokenExpired();

        boolean getHasMore();

        String getNextChangesToken();

        ByteString getNextChangesTokenBytes();

        boolean hasChangesTokenExpired();

        boolean hasHasMore();

        boolean hasNextChangesToken();
    }

    public static final class GetChangesTokenResponse extends GeneratedMessageLite<GetChangesTokenResponse, Builder> implements GetChangesTokenResponseOrBuilder {
        public static final int CHANGES_TOKEN_FIELD_NUMBER = 1;
        private static final GetChangesTokenResponse DEFAULT_INSTANCE;
        private static volatile Parser<GetChangesTokenResponse> PARSER;
        private int bitField0_;
        private String changesToken_ = "";

        public static final class Builder extends GeneratedMessageLite.Builder<GetChangesTokenResponse, Builder> implements GetChangesTokenResponseOrBuilder {
            /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
                this();
            }

            public Builder clearChangesToken() {
                d();
                ((GetChangesTokenResponse) this.f4444a).clearChangesToken();
                return this;
            }

            @Override // androidx.health.platform.client.proto.ResponseProto.GetChangesTokenResponseOrBuilder
            public String getChangesToken() {
                return ((GetChangesTokenResponse) this.f4444a).getChangesToken();
            }

            @Override // androidx.health.platform.client.proto.ResponseProto.GetChangesTokenResponseOrBuilder
            public ByteString getChangesTokenBytes() {
                return ((GetChangesTokenResponse) this.f4444a).getChangesTokenBytes();
            }

            @Override // androidx.health.platform.client.proto.ResponseProto.GetChangesTokenResponseOrBuilder
            public boolean hasChangesToken() {
                return ((GetChangesTokenResponse) this.f4444a).hasChangesToken();
            }

            public Builder setChangesToken(String str) {
                d();
                ((GetChangesTokenResponse) this.f4444a).setChangesToken(str);
                return this;
            }

            public Builder setChangesTokenBytes(ByteString byteString) {
                d();
                ((GetChangesTokenResponse) this.f4444a).setChangesTokenBytes(byteString);
                return this;
            }

            private Builder() {
                super(GetChangesTokenResponse.DEFAULT_INSTANCE);
            }
        }

        static {
            GetChangesTokenResponse getChangesTokenResponse = new GetChangesTokenResponse();
            DEFAULT_INSTANCE = getChangesTokenResponse;
            GeneratedMessageLite.T(GetChangesTokenResponse.class, getChangesTokenResponse);
        }

        private GetChangesTokenResponse() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearChangesToken() {
            this.bitField0_ &= -2;
            this.changesToken_ = getDefaultInstance().getChangesToken();
        }

        public static GetChangesTokenResponse getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.m();
        }

        public static GetChangesTokenResponse parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (GetChangesTokenResponse) GeneratedMessageLite.F(DEFAULT_INSTANCE, inputStream);
        }

        public static GetChangesTokenResponse parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (GetChangesTokenResponse) GeneratedMessageLite.N(DEFAULT_INSTANCE, byteBuffer);
        }

        public static Parser<GetChangesTokenResponse> parser() {
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

        @Override // androidx.health.platform.client.proto.ResponseProto.GetChangesTokenResponseOrBuilder
        public String getChangesToken() {
            return this.changesToken_;
        }

        @Override // androidx.health.platform.client.proto.ResponseProto.GetChangesTokenResponseOrBuilder
        public ByteString getChangesTokenBytes() {
            return ByteString.copyFromUtf8(this.changesToken_);
        }

        @Override // androidx.health.platform.client.proto.ResponseProto.GetChangesTokenResponseOrBuilder
        public boolean hasChangesToken() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override // androidx.health.platform.client.proto.GeneratedMessageLite
        protected final Object q(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            AnonymousClass1 anonymousClass1 = null;
            switch (AnonymousClass1.f4478a[methodToInvoke.ordinal()]) {
                case 1:
                    return new GetChangesTokenResponse();
                case 2:
                    return new Builder(anonymousClass1);
                case 3:
                    return GeneratedMessageLite.D(DEFAULT_INSTANCE, "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001ဈ\u0000", new Object[]{"bitField0_", "changesToken_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<GetChangesTokenResponse> defaultInstanceBasedParser = PARSER;
                    if (defaultInstanceBasedParser == null) {
                        synchronized (GetChangesTokenResponse.class) {
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

        public static Builder newBuilder(GetChangesTokenResponse getChangesTokenResponse) {
            return (Builder) DEFAULT_INSTANCE.n(getChangesTokenResponse);
        }

        public static GetChangesTokenResponse parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (GetChangesTokenResponse) GeneratedMessageLite.G(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static GetChangesTokenResponse parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (GetChangesTokenResponse) GeneratedMessageLite.O(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static GetChangesTokenResponse parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (GetChangesTokenResponse) GeneratedMessageLite.H(DEFAULT_INSTANCE, byteString);
        }

        public static GetChangesTokenResponse parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (GetChangesTokenResponse) GeneratedMessageLite.I(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static GetChangesTokenResponse parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (GetChangesTokenResponse) GeneratedMessageLite.P(DEFAULT_INSTANCE, bArr);
        }

        public static GetChangesTokenResponse parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (GetChangesTokenResponse) GeneratedMessageLite.Q(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static GetChangesTokenResponse parseFrom(InputStream inputStream) throws IOException {
            return (GetChangesTokenResponse) GeneratedMessageLite.L(DEFAULT_INSTANCE, inputStream);
        }

        public static GetChangesTokenResponse parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (GetChangesTokenResponse) GeneratedMessageLite.M(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static GetChangesTokenResponse parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (GetChangesTokenResponse) GeneratedMessageLite.J(DEFAULT_INSTANCE, codedInputStream);
        }

        public static GetChangesTokenResponse parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (GetChangesTokenResponse) GeneratedMessageLite.K(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }
    }

    public interface GetChangesTokenResponseOrBuilder extends MessageLiteOrBuilder {
        String getChangesToken();

        ByteString getChangesTokenBytes();

        boolean hasChangesToken();
    }

    public static final class InsertDataResponse extends GeneratedMessageLite<InsertDataResponse, Builder> implements InsertDataResponseOrBuilder {
        public static final int DATA_POINT_UID_FIELD_NUMBER = 1;
        private static final InsertDataResponse DEFAULT_INSTANCE;
        private static volatile Parser<InsertDataResponse> PARSER;
        private Internal.ProtobufList<String> dataPointUid_ = GeneratedMessageLite.r();

        public static final class Builder extends GeneratedMessageLite.Builder<InsertDataResponse, Builder> implements InsertDataResponseOrBuilder {
            /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
                this();
            }

            public Builder addAllDataPointUid(Iterable<String> iterable) {
                d();
                ((InsertDataResponse) this.f4444a).addAllDataPointUid(iterable);
                return this;
            }

            public Builder addDataPointUid(String str) {
                d();
                ((InsertDataResponse) this.f4444a).addDataPointUid(str);
                return this;
            }

            public Builder addDataPointUidBytes(ByteString byteString) {
                d();
                ((InsertDataResponse) this.f4444a).addDataPointUidBytes(byteString);
                return this;
            }

            public Builder clearDataPointUid() {
                d();
                ((InsertDataResponse) this.f4444a).clearDataPointUid();
                return this;
            }

            @Override // androidx.health.platform.client.proto.ResponseProto.InsertDataResponseOrBuilder
            public String getDataPointUid(int i2) {
                return ((InsertDataResponse) this.f4444a).getDataPointUid(i2);
            }

            @Override // androidx.health.platform.client.proto.ResponseProto.InsertDataResponseOrBuilder
            public ByteString getDataPointUidBytes(int i2) {
                return ((InsertDataResponse) this.f4444a).getDataPointUidBytes(i2);
            }

            @Override // androidx.health.platform.client.proto.ResponseProto.InsertDataResponseOrBuilder
            public int getDataPointUidCount() {
                return ((InsertDataResponse) this.f4444a).getDataPointUidCount();
            }

            @Override // androidx.health.platform.client.proto.ResponseProto.InsertDataResponseOrBuilder
            public List<String> getDataPointUidList() {
                return Collections.unmodifiableList(((InsertDataResponse) this.f4444a).getDataPointUidList());
            }

            public Builder setDataPointUid(int i2, String str) {
                d();
                ((InsertDataResponse) this.f4444a).setDataPointUid(i2, str);
                return this;
            }

            private Builder() {
                super(InsertDataResponse.DEFAULT_INSTANCE);
            }
        }

        static {
            InsertDataResponse insertDataResponse = new InsertDataResponse();
            DEFAULT_INSTANCE = insertDataResponse;
            GeneratedMessageLite.T(InsertDataResponse.class, insertDataResponse);
        }

        private InsertDataResponse() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addAllDataPointUid(Iterable<String> iterable) {
            ensureDataPointUidIsMutable();
            AbstractMessageLite.a(iterable, this.dataPointUid_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addDataPointUid(String str) {
            str.getClass();
            ensureDataPointUidIsMutable();
            this.dataPointUid_.add(str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addDataPointUidBytes(ByteString byteString) {
            ensureDataPointUidIsMutable();
            this.dataPointUid_.add(byteString.toStringUtf8());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearDataPointUid() {
            this.dataPointUid_ = GeneratedMessageLite.r();
        }

        private void ensureDataPointUidIsMutable() {
            Internal.ProtobufList<String> protobufList = this.dataPointUid_;
            if (protobufList.isModifiable()) {
                return;
            }
            this.dataPointUid_ = GeneratedMessageLite.C(protobufList);
        }

        public static InsertDataResponse getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.m();
        }

        public static InsertDataResponse parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (InsertDataResponse) GeneratedMessageLite.F(DEFAULT_INSTANCE, inputStream);
        }

        public static InsertDataResponse parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (InsertDataResponse) GeneratedMessageLite.N(DEFAULT_INSTANCE, byteBuffer);
        }

        public static Parser<InsertDataResponse> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setDataPointUid(int i2, String str) {
            str.getClass();
            ensureDataPointUidIsMutable();
            this.dataPointUid_.set(i2, str);
        }

        @Override // androidx.health.platform.client.proto.ResponseProto.InsertDataResponseOrBuilder
        public String getDataPointUid(int i2) {
            return this.dataPointUid_.get(i2);
        }

        @Override // androidx.health.platform.client.proto.ResponseProto.InsertDataResponseOrBuilder
        public ByteString getDataPointUidBytes(int i2) {
            return ByteString.copyFromUtf8(this.dataPointUid_.get(i2));
        }

        @Override // androidx.health.platform.client.proto.ResponseProto.InsertDataResponseOrBuilder
        public int getDataPointUidCount() {
            return this.dataPointUid_.size();
        }

        @Override // androidx.health.platform.client.proto.ResponseProto.InsertDataResponseOrBuilder
        public List<String> getDataPointUidList() {
            return this.dataPointUid_;
        }

        @Override // androidx.health.platform.client.proto.GeneratedMessageLite
        protected final Object q(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            AnonymousClass1 anonymousClass1 = null;
            switch (AnonymousClass1.f4478a[methodToInvoke.ordinal()]) {
                case 1:
                    return new InsertDataResponse();
                case 2:
                    return new Builder(anonymousClass1);
                case 3:
                    return GeneratedMessageLite.D(DEFAULT_INSTANCE, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001a", new Object[]{"dataPointUid_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<InsertDataResponse> defaultInstanceBasedParser = PARSER;
                    if (defaultInstanceBasedParser == null) {
                        synchronized (InsertDataResponse.class) {
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

        public static Builder newBuilder(InsertDataResponse insertDataResponse) {
            return (Builder) DEFAULT_INSTANCE.n(insertDataResponse);
        }

        public static InsertDataResponse parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (InsertDataResponse) GeneratedMessageLite.G(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static InsertDataResponse parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (InsertDataResponse) GeneratedMessageLite.O(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static InsertDataResponse parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (InsertDataResponse) GeneratedMessageLite.H(DEFAULT_INSTANCE, byteString);
        }

        public static InsertDataResponse parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (InsertDataResponse) GeneratedMessageLite.I(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static InsertDataResponse parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (InsertDataResponse) GeneratedMessageLite.P(DEFAULT_INSTANCE, bArr);
        }

        public static InsertDataResponse parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (InsertDataResponse) GeneratedMessageLite.Q(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static InsertDataResponse parseFrom(InputStream inputStream) throws IOException {
            return (InsertDataResponse) GeneratedMessageLite.L(DEFAULT_INSTANCE, inputStream);
        }

        public static InsertDataResponse parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (InsertDataResponse) GeneratedMessageLite.M(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static InsertDataResponse parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (InsertDataResponse) GeneratedMessageLite.J(DEFAULT_INSTANCE, codedInputStream);
        }

        public static InsertDataResponse parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (InsertDataResponse) GeneratedMessageLite.K(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }
    }

    public interface InsertDataResponseOrBuilder extends MessageLiteOrBuilder {
        String getDataPointUid(int i2);

        ByteString getDataPointUidBytes(int i2);

        int getDataPointUidCount();

        List<String> getDataPointUidList();
    }

    public static final class ReadDataPointResponse extends GeneratedMessageLite<ReadDataPointResponse, Builder> implements ReadDataPointResponseOrBuilder {
        public static final int DATA_FIELD_NUMBER = 1;
        private static final ReadDataPointResponse DEFAULT_INSTANCE;
        private static volatile Parser<ReadDataPointResponse> PARSER;
        private int bitField0_;
        private DataProto.DataPoint data_;

        public static final class Builder extends GeneratedMessageLite.Builder<ReadDataPointResponse, Builder> implements ReadDataPointResponseOrBuilder {
            /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
                this();
            }

            public Builder clearData() {
                d();
                ((ReadDataPointResponse) this.f4444a).clearData();
                return this;
            }

            @Override // androidx.health.platform.client.proto.ResponseProto.ReadDataPointResponseOrBuilder
            public DataProto.DataPoint getData() {
                return ((ReadDataPointResponse) this.f4444a).getData();
            }

            @Override // androidx.health.platform.client.proto.ResponseProto.ReadDataPointResponseOrBuilder
            public boolean hasData() {
                return ((ReadDataPointResponse) this.f4444a).hasData();
            }

            public Builder mergeData(DataProto.DataPoint dataPoint) {
                d();
                ((ReadDataPointResponse) this.f4444a).mergeData(dataPoint);
                return this;
            }

            public Builder setData(DataProto.DataPoint dataPoint) {
                d();
                ((ReadDataPointResponse) this.f4444a).setData(dataPoint);
                return this;
            }

            private Builder() {
                super(ReadDataPointResponse.DEFAULT_INSTANCE);
            }

            public Builder setData(DataProto.DataPoint.Builder builder) {
                d();
                ((ReadDataPointResponse) this.f4444a).setData(builder.build());
                return this;
            }
        }

        static {
            ReadDataPointResponse readDataPointResponse = new ReadDataPointResponse();
            DEFAULT_INSTANCE = readDataPointResponse;
            GeneratedMessageLite.T(ReadDataPointResponse.class, readDataPointResponse);
        }

        private ReadDataPointResponse() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearData() {
            this.data_ = null;
            this.bitField0_ &= -2;
        }

        public static ReadDataPointResponse getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mergeData(DataProto.DataPoint dataPoint) {
            dataPoint.getClass();
            DataProto.DataPoint dataPoint2 = this.data_;
            if (dataPoint2 == null || dataPoint2 == DataProto.DataPoint.getDefaultInstance()) {
                this.data_ = dataPoint;
            } else {
                this.data_ = DataProto.DataPoint.newBuilder(this.data_).mergeFrom((DataProto.DataPoint.Builder) dataPoint).buildPartial();
            }
            this.bitField0_ |= 1;
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.m();
        }

        public static ReadDataPointResponse parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (ReadDataPointResponse) GeneratedMessageLite.F(DEFAULT_INSTANCE, inputStream);
        }

        public static ReadDataPointResponse parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (ReadDataPointResponse) GeneratedMessageLite.N(DEFAULT_INSTANCE, byteBuffer);
        }

        public static Parser<ReadDataPointResponse> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setData(DataProto.DataPoint dataPoint) {
            dataPoint.getClass();
            this.data_ = dataPoint;
            this.bitField0_ |= 1;
        }

        @Override // androidx.health.platform.client.proto.ResponseProto.ReadDataPointResponseOrBuilder
        public DataProto.DataPoint getData() {
            DataProto.DataPoint dataPoint = this.data_;
            return dataPoint == null ? DataProto.DataPoint.getDefaultInstance() : dataPoint;
        }

        @Override // androidx.health.platform.client.proto.ResponseProto.ReadDataPointResponseOrBuilder
        public boolean hasData() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override // androidx.health.platform.client.proto.GeneratedMessageLite
        protected final Object q(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            AnonymousClass1 anonymousClass1 = null;
            switch (AnonymousClass1.f4478a[methodToInvoke.ordinal()]) {
                case 1:
                    return new ReadDataPointResponse();
                case 2:
                    return new Builder(anonymousClass1);
                case 3:
                    return GeneratedMessageLite.D(DEFAULT_INSTANCE, "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001ဉ\u0000", new Object[]{"bitField0_", "data_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<ReadDataPointResponse> defaultInstanceBasedParser = PARSER;
                    if (defaultInstanceBasedParser == null) {
                        synchronized (ReadDataPointResponse.class) {
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

        public static Builder newBuilder(ReadDataPointResponse readDataPointResponse) {
            return (Builder) DEFAULT_INSTANCE.n(readDataPointResponse);
        }

        public static ReadDataPointResponse parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ReadDataPointResponse) GeneratedMessageLite.G(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static ReadDataPointResponse parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ReadDataPointResponse) GeneratedMessageLite.O(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static ReadDataPointResponse parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (ReadDataPointResponse) GeneratedMessageLite.H(DEFAULT_INSTANCE, byteString);
        }

        public static ReadDataPointResponse parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ReadDataPointResponse) GeneratedMessageLite.I(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static ReadDataPointResponse parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (ReadDataPointResponse) GeneratedMessageLite.P(DEFAULT_INSTANCE, bArr);
        }

        public static ReadDataPointResponse parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ReadDataPointResponse) GeneratedMessageLite.Q(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static ReadDataPointResponse parseFrom(InputStream inputStream) throws IOException {
            return (ReadDataPointResponse) GeneratedMessageLite.L(DEFAULT_INSTANCE, inputStream);
        }

        public static ReadDataPointResponse parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ReadDataPointResponse) GeneratedMessageLite.M(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static ReadDataPointResponse parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (ReadDataPointResponse) GeneratedMessageLite.J(DEFAULT_INSTANCE, codedInputStream);
        }

        public static ReadDataPointResponse parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ReadDataPointResponse) GeneratedMessageLite.K(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }
    }

    public interface ReadDataPointResponseOrBuilder extends MessageLiteOrBuilder {
        DataProto.DataPoint getData();

        boolean hasData();
    }

    public static final class ReadDataRangeResponse extends GeneratedMessageLite<ReadDataRangeResponse, Builder> implements ReadDataRangeResponseOrBuilder {
        public static final int DATA_POINT_FIELD_NUMBER = 1;
        private static final ReadDataRangeResponse DEFAULT_INSTANCE;
        public static final int PAGE_TOKEN_FIELD_NUMBER = 2;
        private static volatile Parser<ReadDataRangeResponse> PARSER;
        private int bitField0_;
        private Internal.ProtobufList<DataProto.DataPoint> dataPoint_ = GeneratedMessageLite.r();
        private String pageToken_ = "";

        public static final class Builder extends GeneratedMessageLite.Builder<ReadDataRangeResponse, Builder> implements ReadDataRangeResponseOrBuilder {
            /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
                this();
            }

            public Builder addAllDataPoint(Iterable<? extends DataProto.DataPoint> iterable) {
                d();
                ((ReadDataRangeResponse) this.f4444a).addAllDataPoint(iterable);
                return this;
            }

            public Builder addDataPoint(DataProto.DataPoint dataPoint) {
                d();
                ((ReadDataRangeResponse) this.f4444a).addDataPoint(dataPoint);
                return this;
            }

            public Builder clearDataPoint() {
                d();
                ((ReadDataRangeResponse) this.f4444a).clearDataPoint();
                return this;
            }

            public Builder clearPageToken() {
                d();
                ((ReadDataRangeResponse) this.f4444a).clearPageToken();
                return this;
            }

            @Override // androidx.health.platform.client.proto.ResponseProto.ReadDataRangeResponseOrBuilder
            public DataProto.DataPoint getDataPoint(int i2) {
                return ((ReadDataRangeResponse) this.f4444a).getDataPoint(i2);
            }

            @Override // androidx.health.platform.client.proto.ResponseProto.ReadDataRangeResponseOrBuilder
            public int getDataPointCount() {
                return ((ReadDataRangeResponse) this.f4444a).getDataPointCount();
            }

            @Override // androidx.health.platform.client.proto.ResponseProto.ReadDataRangeResponseOrBuilder
            public List<DataProto.DataPoint> getDataPointList() {
                return Collections.unmodifiableList(((ReadDataRangeResponse) this.f4444a).getDataPointList());
            }

            @Override // androidx.health.platform.client.proto.ResponseProto.ReadDataRangeResponseOrBuilder
            public String getPageToken() {
                return ((ReadDataRangeResponse) this.f4444a).getPageToken();
            }

            @Override // androidx.health.platform.client.proto.ResponseProto.ReadDataRangeResponseOrBuilder
            public ByteString getPageTokenBytes() {
                return ((ReadDataRangeResponse) this.f4444a).getPageTokenBytes();
            }

            @Override // androidx.health.platform.client.proto.ResponseProto.ReadDataRangeResponseOrBuilder
            public boolean hasPageToken() {
                return ((ReadDataRangeResponse) this.f4444a).hasPageToken();
            }

            public Builder removeDataPoint(int i2) {
                d();
                ((ReadDataRangeResponse) this.f4444a).removeDataPoint(i2);
                return this;
            }

            public Builder setDataPoint(int i2, DataProto.DataPoint dataPoint) {
                d();
                ((ReadDataRangeResponse) this.f4444a).setDataPoint(i2, dataPoint);
                return this;
            }

            public Builder setPageToken(String str) {
                d();
                ((ReadDataRangeResponse) this.f4444a).setPageToken(str);
                return this;
            }

            public Builder setPageTokenBytes(ByteString byteString) {
                d();
                ((ReadDataRangeResponse) this.f4444a).setPageTokenBytes(byteString);
                return this;
            }

            private Builder() {
                super(ReadDataRangeResponse.DEFAULT_INSTANCE);
            }

            public Builder addDataPoint(int i2, DataProto.DataPoint dataPoint) {
                d();
                ((ReadDataRangeResponse) this.f4444a).addDataPoint(i2, dataPoint);
                return this;
            }

            public Builder setDataPoint(int i2, DataProto.DataPoint.Builder builder) {
                d();
                ((ReadDataRangeResponse) this.f4444a).setDataPoint(i2, builder.build());
                return this;
            }

            public Builder addDataPoint(DataProto.DataPoint.Builder builder) {
                d();
                ((ReadDataRangeResponse) this.f4444a).addDataPoint(builder.build());
                return this;
            }

            public Builder addDataPoint(int i2, DataProto.DataPoint.Builder builder) {
                d();
                ((ReadDataRangeResponse) this.f4444a).addDataPoint(i2, builder.build());
                return this;
            }
        }

        static {
            ReadDataRangeResponse readDataRangeResponse = new ReadDataRangeResponse();
            DEFAULT_INSTANCE = readDataRangeResponse;
            GeneratedMessageLite.T(ReadDataRangeResponse.class, readDataRangeResponse);
        }

        private ReadDataRangeResponse() {
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

        /* JADX INFO: Access modifiers changed from: private */
        public void clearPageToken() {
            this.bitField0_ &= -2;
            this.pageToken_ = getDefaultInstance().getPageToken();
        }

        private void ensureDataPointIsMutable() {
            Internal.ProtobufList<DataProto.DataPoint> protobufList = this.dataPoint_;
            if (protobufList.isModifiable()) {
                return;
            }
            this.dataPoint_ = GeneratedMessageLite.C(protobufList);
        }

        public static ReadDataRangeResponse getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.m();
        }

        public static ReadDataRangeResponse parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (ReadDataRangeResponse) GeneratedMessageLite.F(DEFAULT_INSTANCE, inputStream);
        }

        public static ReadDataRangeResponse parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (ReadDataRangeResponse) GeneratedMessageLite.N(DEFAULT_INSTANCE, byteBuffer);
        }

        public static Parser<ReadDataRangeResponse> parser() {
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

        /* JADX INFO: Access modifiers changed from: private */
        public void setPageToken(String str) {
            str.getClass();
            this.bitField0_ |= 1;
            this.pageToken_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setPageTokenBytes(ByteString byteString) {
            this.pageToken_ = byteString.toStringUtf8();
            this.bitField0_ |= 1;
        }

        @Override // androidx.health.platform.client.proto.ResponseProto.ReadDataRangeResponseOrBuilder
        public DataProto.DataPoint getDataPoint(int i2) {
            return this.dataPoint_.get(i2);
        }

        @Override // androidx.health.platform.client.proto.ResponseProto.ReadDataRangeResponseOrBuilder
        public int getDataPointCount() {
            return this.dataPoint_.size();
        }

        @Override // androidx.health.platform.client.proto.ResponseProto.ReadDataRangeResponseOrBuilder
        public List<DataProto.DataPoint> getDataPointList() {
            return this.dataPoint_;
        }

        public DataProto.DataPointOrBuilder getDataPointOrBuilder(int i2) {
            return this.dataPoint_.get(i2);
        }

        public List<? extends DataProto.DataPointOrBuilder> getDataPointOrBuilderList() {
            return this.dataPoint_;
        }

        @Override // androidx.health.platform.client.proto.ResponseProto.ReadDataRangeResponseOrBuilder
        public String getPageToken() {
            return this.pageToken_;
        }

        @Override // androidx.health.platform.client.proto.ResponseProto.ReadDataRangeResponseOrBuilder
        public ByteString getPageTokenBytes() {
            return ByteString.copyFromUtf8(this.pageToken_);
        }

        @Override // androidx.health.platform.client.proto.ResponseProto.ReadDataRangeResponseOrBuilder
        public boolean hasPageToken() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override // androidx.health.platform.client.proto.GeneratedMessageLite
        protected final Object q(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            AnonymousClass1 anonymousClass1 = null;
            switch (AnonymousClass1.f4478a[methodToInvoke.ordinal()]) {
                case 1:
                    return new ReadDataRangeResponse();
                case 2:
                    return new Builder(anonymousClass1);
                case 3:
                    return GeneratedMessageLite.D(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0001\u0000\u0001\u001b\u0002ဈ\u0000", new Object[]{"bitField0_", "dataPoint_", DataProto.DataPoint.class, "pageToken_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<ReadDataRangeResponse> defaultInstanceBasedParser = PARSER;
                    if (defaultInstanceBasedParser == null) {
                        synchronized (ReadDataRangeResponse.class) {
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

        public static Builder newBuilder(ReadDataRangeResponse readDataRangeResponse) {
            return (Builder) DEFAULT_INSTANCE.n(readDataRangeResponse);
        }

        public static ReadDataRangeResponse parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ReadDataRangeResponse) GeneratedMessageLite.G(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static ReadDataRangeResponse parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ReadDataRangeResponse) GeneratedMessageLite.O(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static ReadDataRangeResponse parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (ReadDataRangeResponse) GeneratedMessageLite.H(DEFAULT_INSTANCE, byteString);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addDataPoint(int i2, DataProto.DataPoint dataPoint) {
            dataPoint.getClass();
            ensureDataPointIsMutable();
            this.dataPoint_.add(i2, dataPoint);
        }

        public static ReadDataRangeResponse parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ReadDataRangeResponse) GeneratedMessageLite.I(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static ReadDataRangeResponse parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (ReadDataRangeResponse) GeneratedMessageLite.P(DEFAULT_INSTANCE, bArr);
        }

        public static ReadDataRangeResponse parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ReadDataRangeResponse) GeneratedMessageLite.Q(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static ReadDataRangeResponse parseFrom(InputStream inputStream) throws IOException {
            return (ReadDataRangeResponse) GeneratedMessageLite.L(DEFAULT_INSTANCE, inputStream);
        }

        public static ReadDataRangeResponse parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ReadDataRangeResponse) GeneratedMessageLite.M(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static ReadDataRangeResponse parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (ReadDataRangeResponse) GeneratedMessageLite.J(DEFAULT_INSTANCE, codedInputStream);
        }

        public static ReadDataRangeResponse parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ReadDataRangeResponse) GeneratedMessageLite.K(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }
    }

    public interface ReadDataRangeResponseOrBuilder extends MessageLiteOrBuilder {
        DataProto.DataPoint getDataPoint(int i2);

        int getDataPointCount();

        List<DataProto.DataPoint> getDataPointList();

        String getPageToken();

        ByteString getPageTokenBytes();

        boolean hasPageToken();
    }

    public static final class ReadDataResponse extends GeneratedMessageLite<ReadDataResponse, Builder> implements ReadDataResponseOrBuilder {
        public static final int DATA_POINT_FIELD_NUMBER = 1;
        private static final ReadDataResponse DEFAULT_INSTANCE;
        private static volatile Parser<ReadDataResponse> PARSER;
        private int bitField0_;
        private DataProto.DataPoint dataPoint_;

        public static final class Builder extends GeneratedMessageLite.Builder<ReadDataResponse, Builder> implements ReadDataResponseOrBuilder {
            /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
                this();
            }

            public Builder clearDataPoint() {
                d();
                ((ReadDataResponse) this.f4444a).clearDataPoint();
                return this;
            }

            @Override // androidx.health.platform.client.proto.ResponseProto.ReadDataResponseOrBuilder
            public DataProto.DataPoint getDataPoint() {
                return ((ReadDataResponse) this.f4444a).getDataPoint();
            }

            @Override // androidx.health.platform.client.proto.ResponseProto.ReadDataResponseOrBuilder
            public boolean hasDataPoint() {
                return ((ReadDataResponse) this.f4444a).hasDataPoint();
            }

            public Builder mergeDataPoint(DataProto.DataPoint dataPoint) {
                d();
                ((ReadDataResponse) this.f4444a).mergeDataPoint(dataPoint);
                return this;
            }

            public Builder setDataPoint(DataProto.DataPoint dataPoint) {
                d();
                ((ReadDataResponse) this.f4444a).setDataPoint(dataPoint);
                return this;
            }

            private Builder() {
                super(ReadDataResponse.DEFAULT_INSTANCE);
            }

            public Builder setDataPoint(DataProto.DataPoint.Builder builder) {
                d();
                ((ReadDataResponse) this.f4444a).setDataPoint(builder.build());
                return this;
            }
        }

        static {
            ReadDataResponse readDataResponse = new ReadDataResponse();
            DEFAULT_INSTANCE = readDataResponse;
            GeneratedMessageLite.T(ReadDataResponse.class, readDataResponse);
        }

        private ReadDataResponse() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearDataPoint() {
            this.dataPoint_ = null;
            this.bitField0_ &= -2;
        }

        public static ReadDataResponse getDefaultInstance() {
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

        public static ReadDataResponse parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (ReadDataResponse) GeneratedMessageLite.F(DEFAULT_INSTANCE, inputStream);
        }

        public static ReadDataResponse parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (ReadDataResponse) GeneratedMessageLite.N(DEFAULT_INSTANCE, byteBuffer);
        }

        public static Parser<ReadDataResponse> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setDataPoint(DataProto.DataPoint dataPoint) {
            dataPoint.getClass();
            this.dataPoint_ = dataPoint;
            this.bitField0_ |= 1;
        }

        @Override // androidx.health.platform.client.proto.ResponseProto.ReadDataResponseOrBuilder
        public DataProto.DataPoint getDataPoint() {
            DataProto.DataPoint dataPoint = this.dataPoint_;
            return dataPoint == null ? DataProto.DataPoint.getDefaultInstance() : dataPoint;
        }

        @Override // androidx.health.platform.client.proto.ResponseProto.ReadDataResponseOrBuilder
        public boolean hasDataPoint() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override // androidx.health.platform.client.proto.GeneratedMessageLite
        protected final Object q(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            AnonymousClass1 anonymousClass1 = null;
            switch (AnonymousClass1.f4478a[methodToInvoke.ordinal()]) {
                case 1:
                    return new ReadDataResponse();
                case 2:
                    return new Builder(anonymousClass1);
                case 3:
                    return GeneratedMessageLite.D(DEFAULT_INSTANCE, "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001ဉ\u0000", new Object[]{"bitField0_", "dataPoint_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<ReadDataResponse> defaultInstanceBasedParser = PARSER;
                    if (defaultInstanceBasedParser == null) {
                        synchronized (ReadDataResponse.class) {
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

        public static Builder newBuilder(ReadDataResponse readDataResponse) {
            return (Builder) DEFAULT_INSTANCE.n(readDataResponse);
        }

        public static ReadDataResponse parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ReadDataResponse) GeneratedMessageLite.G(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static ReadDataResponse parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ReadDataResponse) GeneratedMessageLite.O(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static ReadDataResponse parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (ReadDataResponse) GeneratedMessageLite.H(DEFAULT_INSTANCE, byteString);
        }

        public static ReadDataResponse parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ReadDataResponse) GeneratedMessageLite.I(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static ReadDataResponse parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (ReadDataResponse) GeneratedMessageLite.P(DEFAULT_INSTANCE, bArr);
        }

        public static ReadDataResponse parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ReadDataResponse) GeneratedMessageLite.Q(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static ReadDataResponse parseFrom(InputStream inputStream) throws IOException {
            return (ReadDataResponse) GeneratedMessageLite.L(DEFAULT_INSTANCE, inputStream);
        }

        public static ReadDataResponse parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ReadDataResponse) GeneratedMessageLite.M(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static ReadDataResponse parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (ReadDataResponse) GeneratedMessageLite.J(DEFAULT_INSTANCE, codedInputStream);
        }

        public static ReadDataResponse parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ReadDataResponse) GeneratedMessageLite.K(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }
    }

    public interface ReadDataResponseOrBuilder extends MessageLiteOrBuilder {
        DataProto.DataPoint getDataPoint();

        boolean hasDataPoint();
    }

    public static final class ReadExerciseRouteResponse extends GeneratedMessageLite<ReadExerciseRouteResponse, Builder> implements ReadExerciseRouteResponseOrBuilder {
        public static final int DATA_POINT_FIELD_NUMBER = 1;
        private static final ReadExerciseRouteResponse DEFAULT_INSTANCE;
        private static volatile Parser<ReadExerciseRouteResponse> PARSER;
        private int bitField0_;
        private DataProto.DataPoint dataPoint_;

        public static final class Builder extends GeneratedMessageLite.Builder<ReadExerciseRouteResponse, Builder> implements ReadExerciseRouteResponseOrBuilder {
            /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
                this();
            }

            public Builder clearDataPoint() {
                d();
                ((ReadExerciseRouteResponse) this.f4444a).clearDataPoint();
                return this;
            }

            @Override // androidx.health.platform.client.proto.ResponseProto.ReadExerciseRouteResponseOrBuilder
            public DataProto.DataPoint getDataPoint() {
                return ((ReadExerciseRouteResponse) this.f4444a).getDataPoint();
            }

            @Override // androidx.health.platform.client.proto.ResponseProto.ReadExerciseRouteResponseOrBuilder
            public boolean hasDataPoint() {
                return ((ReadExerciseRouteResponse) this.f4444a).hasDataPoint();
            }

            public Builder mergeDataPoint(DataProto.DataPoint dataPoint) {
                d();
                ((ReadExerciseRouteResponse) this.f4444a).mergeDataPoint(dataPoint);
                return this;
            }

            public Builder setDataPoint(DataProto.DataPoint dataPoint) {
                d();
                ((ReadExerciseRouteResponse) this.f4444a).setDataPoint(dataPoint);
                return this;
            }

            private Builder() {
                super(ReadExerciseRouteResponse.DEFAULT_INSTANCE);
            }

            public Builder setDataPoint(DataProto.DataPoint.Builder builder) {
                d();
                ((ReadExerciseRouteResponse) this.f4444a).setDataPoint(builder.build());
                return this;
            }
        }

        static {
            ReadExerciseRouteResponse readExerciseRouteResponse = new ReadExerciseRouteResponse();
            DEFAULT_INSTANCE = readExerciseRouteResponse;
            GeneratedMessageLite.T(ReadExerciseRouteResponse.class, readExerciseRouteResponse);
        }

        private ReadExerciseRouteResponse() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearDataPoint() {
            this.dataPoint_ = null;
            this.bitField0_ &= -2;
        }

        public static ReadExerciseRouteResponse getDefaultInstance() {
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

        public static ReadExerciseRouteResponse parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (ReadExerciseRouteResponse) GeneratedMessageLite.F(DEFAULT_INSTANCE, inputStream);
        }

        public static ReadExerciseRouteResponse parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (ReadExerciseRouteResponse) GeneratedMessageLite.N(DEFAULT_INSTANCE, byteBuffer);
        }

        public static Parser<ReadExerciseRouteResponse> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setDataPoint(DataProto.DataPoint dataPoint) {
            dataPoint.getClass();
            this.dataPoint_ = dataPoint;
            this.bitField0_ |= 1;
        }

        @Override // androidx.health.platform.client.proto.ResponseProto.ReadExerciseRouteResponseOrBuilder
        public DataProto.DataPoint getDataPoint() {
            DataProto.DataPoint dataPoint = this.dataPoint_;
            return dataPoint == null ? DataProto.DataPoint.getDefaultInstance() : dataPoint;
        }

        @Override // androidx.health.platform.client.proto.ResponseProto.ReadExerciseRouteResponseOrBuilder
        public boolean hasDataPoint() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override // androidx.health.platform.client.proto.GeneratedMessageLite
        protected final Object q(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            AnonymousClass1 anonymousClass1 = null;
            switch (AnonymousClass1.f4478a[methodToInvoke.ordinal()]) {
                case 1:
                    return new ReadExerciseRouteResponse();
                case 2:
                    return new Builder(anonymousClass1);
                case 3:
                    return GeneratedMessageLite.D(DEFAULT_INSTANCE, "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001ဉ\u0000", new Object[]{"bitField0_", "dataPoint_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<ReadExerciseRouteResponse> defaultInstanceBasedParser = PARSER;
                    if (defaultInstanceBasedParser == null) {
                        synchronized (ReadExerciseRouteResponse.class) {
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

        public static Builder newBuilder(ReadExerciseRouteResponse readExerciseRouteResponse) {
            return (Builder) DEFAULT_INSTANCE.n(readExerciseRouteResponse);
        }

        public static ReadExerciseRouteResponse parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ReadExerciseRouteResponse) GeneratedMessageLite.G(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static ReadExerciseRouteResponse parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ReadExerciseRouteResponse) GeneratedMessageLite.O(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static ReadExerciseRouteResponse parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (ReadExerciseRouteResponse) GeneratedMessageLite.H(DEFAULT_INSTANCE, byteString);
        }

        public static ReadExerciseRouteResponse parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ReadExerciseRouteResponse) GeneratedMessageLite.I(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static ReadExerciseRouteResponse parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (ReadExerciseRouteResponse) GeneratedMessageLite.P(DEFAULT_INSTANCE, bArr);
        }

        public static ReadExerciseRouteResponse parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ReadExerciseRouteResponse) GeneratedMessageLite.Q(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static ReadExerciseRouteResponse parseFrom(InputStream inputStream) throws IOException {
            return (ReadExerciseRouteResponse) GeneratedMessageLite.L(DEFAULT_INSTANCE, inputStream);
        }

        public static ReadExerciseRouteResponse parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ReadExerciseRouteResponse) GeneratedMessageLite.M(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static ReadExerciseRouteResponse parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (ReadExerciseRouteResponse) GeneratedMessageLite.J(DEFAULT_INSTANCE, codedInputStream);
        }

        public static ReadExerciseRouteResponse parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ReadExerciseRouteResponse) GeneratedMessageLite.K(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }
    }

    public interface ReadExerciseRouteResponseOrBuilder extends MessageLiteOrBuilder {
        DataProto.DataPoint getDataPoint();

        boolean hasDataPoint();
    }

    private ResponseProto() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite extensionRegistryLite) {
    }
}
