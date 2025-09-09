package androidx.health.platform.client.proto;

import androidx.health.platform.client.proto.DataProto;
import androidx.health.platform.client.proto.GeneratedMessageLite;
import androidx.health.platform.client.proto.Internal;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public final class ChangeProto {

    /* renamed from: androidx.health.platform.client.proto.ChangeProto$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f4402a;

        static {
            int[] iArr = new int[GeneratedMessageLite.MethodToInvoke.values().length];
            f4402a = iArr;
            try {
                iArr[GeneratedMessageLite.MethodToInvoke.NEW_MUTABLE_INSTANCE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f4402a[GeneratedMessageLite.MethodToInvoke.NEW_BUILDER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f4402a[GeneratedMessageLite.MethodToInvoke.BUILD_MESSAGE_INFO.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f4402a[GeneratedMessageLite.MethodToInvoke.GET_DEFAULT_INSTANCE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f4402a[GeneratedMessageLite.MethodToInvoke.GET_PARSER.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f4402a[GeneratedMessageLite.MethodToInvoke.GET_MEMOIZED_IS_INITIALIZED.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f4402a[GeneratedMessageLite.MethodToInvoke.SET_MEMOIZED_IS_INITIALIZED.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    public static final class ChangesEvent extends GeneratedMessageLite<ChangesEvent, Builder> implements ChangesEventOrBuilder {
        public static final int CHANGES_FIELD_NUMBER = 2;
        private static final ChangesEvent DEFAULT_INSTANCE;
        public static final int NEXT_CHANGES_TOKEN_FIELD_NUMBER = 1;
        private static volatile Parser<ChangesEvent> PARSER;
        private int bitField0_;
        private String nextChangesToken_ = "";
        private Internal.ProtobufList<DataChange> changes_ = GeneratedMessageLite.r();

        public static final class Builder extends GeneratedMessageLite.Builder<ChangesEvent, Builder> implements ChangesEventOrBuilder {
            /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
                this();
            }

            public Builder addAllChanges(Iterable<? extends DataChange> iterable) {
                d();
                ((ChangesEvent) this.f4444a).addAllChanges(iterable);
                return this;
            }

            public Builder addChanges(DataChange dataChange) {
                d();
                ((ChangesEvent) this.f4444a).addChanges(dataChange);
                return this;
            }

            public Builder clearChanges() {
                d();
                ((ChangesEvent) this.f4444a).clearChanges();
                return this;
            }

            public Builder clearNextChangesToken() {
                d();
                ((ChangesEvent) this.f4444a).clearNextChangesToken();
                return this;
            }

            @Override // androidx.health.platform.client.proto.ChangeProto.ChangesEventOrBuilder
            public DataChange getChanges(int i2) {
                return ((ChangesEvent) this.f4444a).getChanges(i2);
            }

            @Override // androidx.health.platform.client.proto.ChangeProto.ChangesEventOrBuilder
            public int getChangesCount() {
                return ((ChangesEvent) this.f4444a).getChangesCount();
            }

            @Override // androidx.health.platform.client.proto.ChangeProto.ChangesEventOrBuilder
            public List<DataChange> getChangesList() {
                return Collections.unmodifiableList(((ChangesEvent) this.f4444a).getChangesList());
            }

            @Override // androidx.health.platform.client.proto.ChangeProto.ChangesEventOrBuilder
            public String getNextChangesToken() {
                return ((ChangesEvent) this.f4444a).getNextChangesToken();
            }

            @Override // androidx.health.platform.client.proto.ChangeProto.ChangesEventOrBuilder
            public ByteString getNextChangesTokenBytes() {
                return ((ChangesEvent) this.f4444a).getNextChangesTokenBytes();
            }

            @Override // androidx.health.platform.client.proto.ChangeProto.ChangesEventOrBuilder
            public boolean hasNextChangesToken() {
                return ((ChangesEvent) this.f4444a).hasNextChangesToken();
            }

            public Builder removeChanges(int i2) {
                d();
                ((ChangesEvent) this.f4444a).removeChanges(i2);
                return this;
            }

            public Builder setChanges(int i2, DataChange dataChange) {
                d();
                ((ChangesEvent) this.f4444a).setChanges(i2, dataChange);
                return this;
            }

            public Builder setNextChangesToken(String str) {
                d();
                ((ChangesEvent) this.f4444a).setNextChangesToken(str);
                return this;
            }

            public Builder setNextChangesTokenBytes(ByteString byteString) {
                d();
                ((ChangesEvent) this.f4444a).setNextChangesTokenBytes(byteString);
                return this;
            }

            private Builder() {
                super(ChangesEvent.DEFAULT_INSTANCE);
            }

            public Builder addChanges(int i2, DataChange dataChange) {
                d();
                ((ChangesEvent) this.f4444a).addChanges(i2, dataChange);
                return this;
            }

            public Builder setChanges(int i2, DataChange.Builder builder) {
                d();
                ((ChangesEvent) this.f4444a).setChanges(i2, builder.build());
                return this;
            }

            public Builder addChanges(DataChange.Builder builder) {
                d();
                ((ChangesEvent) this.f4444a).addChanges(builder.build());
                return this;
            }

            public Builder addChanges(int i2, DataChange.Builder builder) {
                d();
                ((ChangesEvent) this.f4444a).addChanges(i2, builder.build());
                return this;
            }
        }

        static {
            ChangesEvent changesEvent = new ChangesEvent();
            DEFAULT_INSTANCE = changesEvent;
            GeneratedMessageLite.T(ChangesEvent.class, changesEvent);
        }

        private ChangesEvent() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addAllChanges(Iterable<? extends DataChange> iterable) {
            ensureChangesIsMutable();
            AbstractMessageLite.a(iterable, this.changes_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addChanges(DataChange dataChange) {
            dataChange.getClass();
            ensureChangesIsMutable();
            this.changes_.add(dataChange);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearChanges() {
            this.changes_ = GeneratedMessageLite.r();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearNextChangesToken() {
            this.bitField0_ &= -2;
            this.nextChangesToken_ = getDefaultInstance().getNextChangesToken();
        }

        private void ensureChangesIsMutable() {
            Internal.ProtobufList<DataChange> protobufList = this.changes_;
            if (protobufList.isModifiable()) {
                return;
            }
            this.changes_ = GeneratedMessageLite.C(protobufList);
        }

        public static ChangesEvent getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.m();
        }

        public static ChangesEvent parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (ChangesEvent) GeneratedMessageLite.F(DEFAULT_INSTANCE, inputStream);
        }

        public static ChangesEvent parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (ChangesEvent) GeneratedMessageLite.N(DEFAULT_INSTANCE, byteBuffer);
        }

        public static Parser<ChangesEvent> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void removeChanges(int i2) {
            ensureChangesIsMutable();
            this.changes_.remove(i2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setChanges(int i2, DataChange dataChange) {
            dataChange.getClass();
            ensureChangesIsMutable();
            this.changes_.set(i2, dataChange);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setNextChangesToken(String str) {
            str.getClass();
            this.bitField0_ |= 1;
            this.nextChangesToken_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setNextChangesTokenBytes(ByteString byteString) {
            this.nextChangesToken_ = byteString.toStringUtf8();
            this.bitField0_ |= 1;
        }

        @Override // androidx.health.platform.client.proto.ChangeProto.ChangesEventOrBuilder
        public DataChange getChanges(int i2) {
            return this.changes_.get(i2);
        }

        @Override // androidx.health.platform.client.proto.ChangeProto.ChangesEventOrBuilder
        public int getChangesCount() {
            return this.changes_.size();
        }

        @Override // androidx.health.platform.client.proto.ChangeProto.ChangesEventOrBuilder
        public List<DataChange> getChangesList() {
            return this.changes_;
        }

        public DataChangeOrBuilder getChangesOrBuilder(int i2) {
            return this.changes_.get(i2);
        }

        public List<? extends DataChangeOrBuilder> getChangesOrBuilderList() {
            return this.changes_;
        }

        @Override // androidx.health.platform.client.proto.ChangeProto.ChangesEventOrBuilder
        public String getNextChangesToken() {
            return this.nextChangesToken_;
        }

        @Override // androidx.health.platform.client.proto.ChangeProto.ChangesEventOrBuilder
        public ByteString getNextChangesTokenBytes() {
            return ByteString.copyFromUtf8(this.nextChangesToken_);
        }

        @Override // androidx.health.platform.client.proto.ChangeProto.ChangesEventOrBuilder
        public boolean hasNextChangesToken() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override // androidx.health.platform.client.proto.GeneratedMessageLite
        protected final Object q(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            AnonymousClass1 anonymousClass1 = null;
            switch (AnonymousClass1.f4402a[methodToInvoke.ordinal()]) {
                case 1:
                    return new ChangesEvent();
                case 2:
                    return new Builder(anonymousClass1);
                case 3:
                    return GeneratedMessageLite.D(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0001\u0000\u0001ဈ\u0000\u0002\u001b", new Object[]{"bitField0_", "nextChangesToken_", "changes_", DataChange.class});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<ChangesEvent> defaultInstanceBasedParser = PARSER;
                    if (defaultInstanceBasedParser == null) {
                        synchronized (ChangesEvent.class) {
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

        public static Builder newBuilder(ChangesEvent changesEvent) {
            return (Builder) DEFAULT_INSTANCE.n(changesEvent);
        }

        public static ChangesEvent parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ChangesEvent) GeneratedMessageLite.G(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static ChangesEvent parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ChangesEvent) GeneratedMessageLite.O(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static ChangesEvent parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (ChangesEvent) GeneratedMessageLite.H(DEFAULT_INSTANCE, byteString);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addChanges(int i2, DataChange dataChange) {
            dataChange.getClass();
            ensureChangesIsMutable();
            this.changes_.add(i2, dataChange);
        }

        public static ChangesEvent parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ChangesEvent) GeneratedMessageLite.I(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static ChangesEvent parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (ChangesEvent) GeneratedMessageLite.P(DEFAULT_INSTANCE, bArr);
        }

        public static ChangesEvent parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ChangesEvent) GeneratedMessageLite.Q(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static ChangesEvent parseFrom(InputStream inputStream) throws IOException {
            return (ChangesEvent) GeneratedMessageLite.L(DEFAULT_INSTANCE, inputStream);
        }

        public static ChangesEvent parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ChangesEvent) GeneratedMessageLite.M(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static ChangesEvent parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (ChangesEvent) GeneratedMessageLite.J(DEFAULT_INSTANCE, codedInputStream);
        }

        public static ChangesEvent parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ChangesEvent) GeneratedMessageLite.K(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }
    }

    public interface ChangesEventOrBuilder extends MessageLiteOrBuilder {
        DataChange getChanges(int i2);

        int getChangesCount();

        List<DataChange> getChangesList();

        String getNextChangesToken();

        ByteString getNextChangesTokenBytes();

        boolean hasNextChangesToken();
    }

    public static final class DataChange extends GeneratedMessageLite<DataChange, Builder> implements DataChangeOrBuilder {
        private static final DataChange DEFAULT_INSTANCE;
        public static final int DELETE_UID_FIELD_NUMBER = 2;
        private static volatile Parser<DataChange> PARSER = null;
        public static final int UPSERT_DATA_POINT_FIELD_NUMBER = 1;
        private int bitField0_;
        private int changeCase_ = 0;
        private Object change_;

        public static final class Builder extends GeneratedMessageLite.Builder<DataChange, Builder> implements DataChangeOrBuilder {
            /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
                this();
            }

            public Builder clearChange() {
                d();
                ((DataChange) this.f4444a).clearChange();
                return this;
            }

            public Builder clearDeleteUid() {
                d();
                ((DataChange) this.f4444a).clearDeleteUid();
                return this;
            }

            public Builder clearUpsertDataPoint() {
                d();
                ((DataChange) this.f4444a).clearUpsertDataPoint();
                return this;
            }

            @Override // androidx.health.platform.client.proto.ChangeProto.DataChangeOrBuilder
            public ChangeCase getChangeCase() {
                return ((DataChange) this.f4444a).getChangeCase();
            }

            @Override // androidx.health.platform.client.proto.ChangeProto.DataChangeOrBuilder
            public String getDeleteUid() {
                return ((DataChange) this.f4444a).getDeleteUid();
            }

            @Override // androidx.health.platform.client.proto.ChangeProto.DataChangeOrBuilder
            public ByteString getDeleteUidBytes() {
                return ((DataChange) this.f4444a).getDeleteUidBytes();
            }

            @Override // androidx.health.platform.client.proto.ChangeProto.DataChangeOrBuilder
            public DataProto.DataPoint getUpsertDataPoint() {
                return ((DataChange) this.f4444a).getUpsertDataPoint();
            }

            @Override // androidx.health.platform.client.proto.ChangeProto.DataChangeOrBuilder
            public boolean hasDeleteUid() {
                return ((DataChange) this.f4444a).hasDeleteUid();
            }

            @Override // androidx.health.platform.client.proto.ChangeProto.DataChangeOrBuilder
            public boolean hasUpsertDataPoint() {
                return ((DataChange) this.f4444a).hasUpsertDataPoint();
            }

            public Builder mergeUpsertDataPoint(DataProto.DataPoint dataPoint) {
                d();
                ((DataChange) this.f4444a).mergeUpsertDataPoint(dataPoint);
                return this;
            }

            public Builder setDeleteUid(String str) {
                d();
                ((DataChange) this.f4444a).setDeleteUid(str);
                return this;
            }

            public Builder setDeleteUidBytes(ByteString byteString) {
                d();
                ((DataChange) this.f4444a).setDeleteUidBytes(byteString);
                return this;
            }

            public Builder setUpsertDataPoint(DataProto.DataPoint dataPoint) {
                d();
                ((DataChange) this.f4444a).setUpsertDataPoint(dataPoint);
                return this;
            }

            private Builder() {
                super(DataChange.DEFAULT_INSTANCE);
            }

            public Builder setUpsertDataPoint(DataProto.DataPoint.Builder builder) {
                d();
                ((DataChange) this.f4444a).setUpsertDataPoint(builder.build());
                return this;
            }
        }

        public enum ChangeCase {
            UPSERT_DATA_POINT(1),
            DELETE_UID(2),
            CHANGE_NOT_SET(0);

            private final int value;

            ChangeCase(int i2) {
                this.value = i2;
            }

            public static ChangeCase forNumber(int i2) {
                if (i2 == 0) {
                    return CHANGE_NOT_SET;
                }
                if (i2 == 1) {
                    return UPSERT_DATA_POINT;
                }
                if (i2 != 2) {
                    return null;
                }
                return DELETE_UID;
            }

            public int getNumber() {
                return this.value;
            }

            @Deprecated
            public static ChangeCase valueOf(int i2) {
                return forNumber(i2);
            }
        }

        static {
            DataChange dataChange = new DataChange();
            DEFAULT_INSTANCE = dataChange;
            GeneratedMessageLite.T(DataChange.class, dataChange);
        }

        private DataChange() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearChange() {
            this.changeCase_ = 0;
            this.change_ = null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearDeleteUid() {
            if (this.changeCase_ == 2) {
                this.changeCase_ = 0;
                this.change_ = null;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearUpsertDataPoint() {
            if (this.changeCase_ == 1) {
                this.changeCase_ = 0;
                this.change_ = null;
            }
        }

        public static DataChange getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mergeUpsertDataPoint(DataProto.DataPoint dataPoint) {
            dataPoint.getClass();
            if (this.changeCase_ != 1 || this.change_ == DataProto.DataPoint.getDefaultInstance()) {
                this.change_ = dataPoint;
            } else {
                this.change_ = DataProto.DataPoint.newBuilder((DataProto.DataPoint) this.change_).mergeFrom((DataProto.DataPoint.Builder) dataPoint).buildPartial();
            }
            this.changeCase_ = 1;
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.m();
        }

        public static DataChange parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (DataChange) GeneratedMessageLite.F(DEFAULT_INSTANCE, inputStream);
        }

        public static DataChange parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (DataChange) GeneratedMessageLite.N(DEFAULT_INSTANCE, byteBuffer);
        }

        public static Parser<DataChange> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setDeleteUid(String str) {
            str.getClass();
            this.changeCase_ = 2;
            this.change_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setDeleteUidBytes(ByteString byteString) {
            this.change_ = byteString.toStringUtf8();
            this.changeCase_ = 2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setUpsertDataPoint(DataProto.DataPoint dataPoint) {
            dataPoint.getClass();
            this.change_ = dataPoint;
            this.changeCase_ = 1;
        }

        @Override // androidx.health.platform.client.proto.ChangeProto.DataChangeOrBuilder
        public ChangeCase getChangeCase() {
            return ChangeCase.forNumber(this.changeCase_);
        }

        @Override // androidx.health.platform.client.proto.ChangeProto.DataChangeOrBuilder
        public String getDeleteUid() {
            return this.changeCase_ == 2 ? (String) this.change_ : "";
        }

        @Override // androidx.health.platform.client.proto.ChangeProto.DataChangeOrBuilder
        public ByteString getDeleteUidBytes() {
            return ByteString.copyFromUtf8(this.changeCase_ == 2 ? (String) this.change_ : "");
        }

        @Override // androidx.health.platform.client.proto.ChangeProto.DataChangeOrBuilder
        public DataProto.DataPoint getUpsertDataPoint() {
            return this.changeCase_ == 1 ? (DataProto.DataPoint) this.change_ : DataProto.DataPoint.getDefaultInstance();
        }

        @Override // androidx.health.platform.client.proto.ChangeProto.DataChangeOrBuilder
        public boolean hasDeleteUid() {
            return this.changeCase_ == 2;
        }

        @Override // androidx.health.platform.client.proto.ChangeProto.DataChangeOrBuilder
        public boolean hasUpsertDataPoint() {
            return this.changeCase_ == 1;
        }

        @Override // androidx.health.platform.client.proto.GeneratedMessageLite
        protected final Object q(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            AnonymousClass1 anonymousClass1 = null;
            switch (AnonymousClass1.f4402a[methodToInvoke.ordinal()]) {
                case 1:
                    return new DataChange();
                case 2:
                    return new Builder(anonymousClass1);
                case 3:
                    return GeneratedMessageLite.D(DEFAULT_INSTANCE, "\u0001\u0002\u0001\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ြ\u0000\u0002ျ\u0000", new Object[]{"change_", "changeCase_", "bitField0_", DataProto.DataPoint.class});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<DataChange> defaultInstanceBasedParser = PARSER;
                    if (defaultInstanceBasedParser == null) {
                        synchronized (DataChange.class) {
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

        public static Builder newBuilder(DataChange dataChange) {
            return (Builder) DEFAULT_INSTANCE.n(dataChange);
        }

        public static DataChange parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (DataChange) GeneratedMessageLite.G(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static DataChange parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (DataChange) GeneratedMessageLite.O(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static DataChange parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (DataChange) GeneratedMessageLite.H(DEFAULT_INSTANCE, byteString);
        }

        public static DataChange parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (DataChange) GeneratedMessageLite.I(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static DataChange parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (DataChange) GeneratedMessageLite.P(DEFAULT_INSTANCE, bArr);
        }

        public static DataChange parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (DataChange) GeneratedMessageLite.Q(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static DataChange parseFrom(InputStream inputStream) throws IOException {
            return (DataChange) GeneratedMessageLite.L(DEFAULT_INSTANCE, inputStream);
        }

        public static DataChange parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (DataChange) GeneratedMessageLite.M(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static DataChange parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (DataChange) GeneratedMessageLite.J(DEFAULT_INSTANCE, codedInputStream);
        }

        public static DataChange parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (DataChange) GeneratedMessageLite.K(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }
    }

    public interface DataChangeOrBuilder extends MessageLiteOrBuilder {
        DataChange.ChangeCase getChangeCase();

        String getDeleteUid();

        ByteString getDeleteUidBytes();

        DataProto.DataPoint getUpsertDataPoint();

        boolean hasDeleteUid();

        boolean hasUpsertDataPoint();
    }

    private ChangeProto() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite extensionRegistryLite) {
    }
}
