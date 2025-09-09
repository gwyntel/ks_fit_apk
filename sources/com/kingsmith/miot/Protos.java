package com.kingsmith.miot;

import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.Parser;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

/* loaded from: classes4.dex */
public final class Protos {

    /* renamed from: com.kingsmith.miot.Protos$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f18713a;

        static {
            int[] iArr = new int[GeneratedMessageLite.MethodToInvoke.values().length];
            f18713a = iArr;
            try {
                iArr[GeneratedMessageLite.MethodToInvoke.NEW_MUTABLE_INSTANCE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f18713a[GeneratedMessageLite.MethodToInvoke.NEW_BUILDER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f18713a[GeneratedMessageLite.MethodToInvoke.BUILD_MESSAGE_INFO.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f18713a[GeneratedMessageLite.MethodToInvoke.GET_DEFAULT_INSTANCE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f18713a[GeneratedMessageLite.MethodToInvoke.GET_PARSER.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f18713a[GeneratedMessageLite.MethodToInvoke.GET_MEMOIZED_IS_INITIALIZED.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f18713a[GeneratedMessageLite.MethodToInvoke.SET_MEMOIZED_IS_INITIALIZED.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    public static final class ListOfDeviceMi extends GeneratedMessageLite<ListOfDeviceMi, Builder> implements ListOfDeviceMiOrBuilder {
        public static final int CODE_FIELD_NUMBER = 2;
        private static final ListOfDeviceMi DEFAULT_INSTANCE;
        public static final int DEVICE_FIELD_NUMBER = 1;
        private static volatile Parser<ListOfDeviceMi> PARSER;
        private int code_;
        private Internal.ProtobufList<PDeviceMi> device_ = GeneratedMessageLite.emptyProtobufList();

        public static final class Builder extends GeneratedMessageLite.Builder<ListOfDeviceMi, Builder> implements ListOfDeviceMiOrBuilder {
            public Builder addAllDevice(Iterable<? extends PDeviceMi> iterable) {
                copyOnWrite();
                ((ListOfDeviceMi) this.instance).addAllDevice(iterable);
                return this;
            }

            public Builder addDevice(PDeviceMi pDeviceMi) {
                copyOnWrite();
                ((ListOfDeviceMi) this.instance).addDevice(pDeviceMi);
                return this;
            }

            public Builder clearCode() {
                copyOnWrite();
                ((ListOfDeviceMi) this.instance).clearCode();
                return this;
            }

            public Builder clearDevice() {
                copyOnWrite();
                ((ListOfDeviceMi) this.instance).clearDevice();
                return this;
            }

            @Override // com.kingsmith.miot.Protos.ListOfDeviceMiOrBuilder
            public int getCode() {
                return ((ListOfDeviceMi) this.instance).getCode();
            }

            @Override // com.kingsmith.miot.Protos.ListOfDeviceMiOrBuilder
            public PDeviceMi getDevice(int i2) {
                return ((ListOfDeviceMi) this.instance).getDevice(i2);
            }

            @Override // com.kingsmith.miot.Protos.ListOfDeviceMiOrBuilder
            public int getDeviceCount() {
                return ((ListOfDeviceMi) this.instance).getDeviceCount();
            }

            @Override // com.kingsmith.miot.Protos.ListOfDeviceMiOrBuilder
            public List<PDeviceMi> getDeviceList() {
                return Collections.unmodifiableList(((ListOfDeviceMi) this.instance).getDeviceList());
            }

            public Builder removeDevice(int i2) {
                copyOnWrite();
                ((ListOfDeviceMi) this.instance).removeDevice(i2);
                return this;
            }

            public Builder setCode(int i2) {
                copyOnWrite();
                ((ListOfDeviceMi) this.instance).setCode(i2);
                return this;
            }

            public Builder setDevice(int i2, PDeviceMi pDeviceMi) {
                copyOnWrite();
                ((ListOfDeviceMi) this.instance).setDevice(i2, pDeviceMi);
                return this;
            }

            private Builder() {
                super(ListOfDeviceMi.DEFAULT_INSTANCE);
            }

            public Builder addDevice(int i2, PDeviceMi pDeviceMi) {
                copyOnWrite();
                ((ListOfDeviceMi) this.instance).addDevice(i2, pDeviceMi);
                return this;
            }

            public Builder setDevice(int i2, PDeviceMi.Builder builder) {
                copyOnWrite();
                ((ListOfDeviceMi) this.instance).setDevice(i2, builder.build());
                return this;
            }

            public Builder addDevice(PDeviceMi.Builder builder) {
                copyOnWrite();
                ((ListOfDeviceMi) this.instance).addDevice(builder.build());
                return this;
            }

            public Builder addDevice(int i2, PDeviceMi.Builder builder) {
                copyOnWrite();
                ((ListOfDeviceMi) this.instance).addDevice(i2, builder.build());
                return this;
            }
        }

        static {
            ListOfDeviceMi listOfDeviceMi = new ListOfDeviceMi();
            DEFAULT_INSTANCE = listOfDeviceMi;
            GeneratedMessageLite.registerDefaultInstance(ListOfDeviceMi.class, listOfDeviceMi);
        }

        private ListOfDeviceMi() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addAllDevice(Iterable<? extends PDeviceMi> iterable) {
            ensureDeviceIsMutable();
            AbstractMessageLite.addAll((Iterable) iterable, (List) this.device_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addDevice(PDeviceMi pDeviceMi) {
            pDeviceMi.getClass();
            ensureDeviceIsMutable();
            this.device_.add(pDeviceMi);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearCode() {
            this.code_ = 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearDevice() {
            this.device_ = GeneratedMessageLite.emptyProtobufList();
        }

        private void ensureDeviceIsMutable() {
            Internal.ProtobufList<PDeviceMi> protobufList = this.device_;
            if (protobufList.isModifiable()) {
                return;
            }
            this.device_ = GeneratedMessageLite.mutableCopy(protobufList);
        }

        public static ListOfDeviceMi getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.createBuilder();
        }

        public static ListOfDeviceMi parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (ListOfDeviceMi) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static ListOfDeviceMi parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (ListOfDeviceMi) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer);
        }

        public static Parser<ListOfDeviceMi> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void removeDevice(int i2) {
            ensureDeviceIsMutable();
            this.device_.remove(i2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setCode(int i2) {
            this.code_ = i2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setDevice(int i2, PDeviceMi pDeviceMi) {
            pDeviceMi.getClass();
            ensureDeviceIsMutable();
            this.device_.set(i2, pDeviceMi);
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (AnonymousClass1.f18713a[methodToInvoke.ordinal()]) {
                case 1:
                    return new ListOfDeviceMi();
                case 2:
                    return new Builder();
                case 3:
                    return GeneratedMessageLite.newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0001\u0000\u0001\u001b\u0002\u0004", new Object[]{"device_", PDeviceMi.class, "code_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<ListOfDeviceMi> defaultInstanceBasedParser = PARSER;
                    if (defaultInstanceBasedParser == null) {
                        synchronized (ListOfDeviceMi.class) {
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

        @Override // com.kingsmith.miot.Protos.ListOfDeviceMiOrBuilder
        public int getCode() {
            return this.code_;
        }

        @Override // com.kingsmith.miot.Protos.ListOfDeviceMiOrBuilder
        public PDeviceMi getDevice(int i2) {
            return this.device_.get(i2);
        }

        @Override // com.kingsmith.miot.Protos.ListOfDeviceMiOrBuilder
        public int getDeviceCount() {
            return this.device_.size();
        }

        @Override // com.kingsmith.miot.Protos.ListOfDeviceMiOrBuilder
        public List<PDeviceMi> getDeviceList() {
            return this.device_;
        }

        public PDeviceMiOrBuilder getDeviceOrBuilder(int i2) {
            return this.device_.get(i2);
        }

        public List<? extends PDeviceMiOrBuilder> getDeviceOrBuilderList() {
            return this.device_;
        }

        public static Builder newBuilder(ListOfDeviceMi listOfDeviceMi) {
            return DEFAULT_INSTANCE.createBuilder(listOfDeviceMi);
        }

        public static ListOfDeviceMi parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ListOfDeviceMi) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static ListOfDeviceMi parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ListOfDeviceMi) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static ListOfDeviceMi parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (ListOfDeviceMi) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addDevice(int i2, PDeviceMi pDeviceMi) {
            pDeviceMi.getClass();
            ensureDeviceIsMutable();
            this.device_.add(i2, pDeviceMi);
        }

        public static ListOfDeviceMi parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ListOfDeviceMi) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static ListOfDeviceMi parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (ListOfDeviceMi) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
        }

        public static ListOfDeviceMi parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ListOfDeviceMi) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static ListOfDeviceMi parseFrom(InputStream inputStream) throws IOException {
            return (ListOfDeviceMi) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static ListOfDeviceMi parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ListOfDeviceMi) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static ListOfDeviceMi parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (ListOfDeviceMi) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
        }

        public static ListOfDeviceMi parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ListOfDeviceMi) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }
    }

    public interface ListOfDeviceMiOrBuilder extends MessageLiteOrBuilder {
        int getCode();

        PDeviceMi getDevice(int i2);

        int getDeviceCount();

        List<PDeviceMi> getDeviceList();
    }

    public static final class PDeviceMi extends GeneratedMessageLite<PDeviceMi, Builder> implements PDeviceMiOrBuilder {
        public static final int ADDRESS_FIELD_NUMBER = 7;
        private static final PDeviceMi DEFAULT_INSTANCE;
        public static final int IOTID_FIELD_NUMBER = 6;
        public static final int MODEL_FIELD_NUMBER = 2;
        public static final int NAME_FIELD_NUMBER = 1;
        public static final int NETTYPE_FIELD_NUMBER = 3;
        public static final int OWNED_FIELD_NUMBER = 5;
        private static volatile Parser<PDeviceMi> PARSER = null;
        public static final int STATUS_FIELD_NUMBER = 4;
        private int owned_;
        private int status_;
        private String name_ = "";
        private String model_ = "";
        private String netType_ = "";
        private String iotId_ = "";
        private String address_ = "";

        public static final class Builder extends GeneratedMessageLite.Builder<PDeviceMi, Builder> implements PDeviceMiOrBuilder {
            public Builder clearAddress() {
                copyOnWrite();
                ((PDeviceMi) this.instance).clearAddress();
                return this;
            }

            public Builder clearIotId() {
                copyOnWrite();
                ((PDeviceMi) this.instance).clearIotId();
                return this;
            }

            public Builder clearModel() {
                copyOnWrite();
                ((PDeviceMi) this.instance).clearModel();
                return this;
            }

            public Builder clearName() {
                copyOnWrite();
                ((PDeviceMi) this.instance).clearName();
                return this;
            }

            public Builder clearNetType() {
                copyOnWrite();
                ((PDeviceMi) this.instance).clearNetType();
                return this;
            }

            public Builder clearOwned() {
                copyOnWrite();
                ((PDeviceMi) this.instance).clearOwned();
                return this;
            }

            public Builder clearStatus() {
                copyOnWrite();
                ((PDeviceMi) this.instance).clearStatus();
                return this;
            }

            @Override // com.kingsmith.miot.Protos.PDeviceMiOrBuilder
            public String getAddress() {
                return ((PDeviceMi) this.instance).getAddress();
            }

            @Override // com.kingsmith.miot.Protos.PDeviceMiOrBuilder
            public ByteString getAddressBytes() {
                return ((PDeviceMi) this.instance).getAddressBytes();
            }

            @Override // com.kingsmith.miot.Protos.PDeviceMiOrBuilder
            public String getIotId() {
                return ((PDeviceMi) this.instance).getIotId();
            }

            @Override // com.kingsmith.miot.Protos.PDeviceMiOrBuilder
            public ByteString getIotIdBytes() {
                return ((PDeviceMi) this.instance).getIotIdBytes();
            }

            @Override // com.kingsmith.miot.Protos.PDeviceMiOrBuilder
            public String getModel() {
                return ((PDeviceMi) this.instance).getModel();
            }

            @Override // com.kingsmith.miot.Protos.PDeviceMiOrBuilder
            public ByteString getModelBytes() {
                return ((PDeviceMi) this.instance).getModelBytes();
            }

            @Override // com.kingsmith.miot.Protos.PDeviceMiOrBuilder
            public String getName() {
                return ((PDeviceMi) this.instance).getName();
            }

            @Override // com.kingsmith.miot.Protos.PDeviceMiOrBuilder
            public ByteString getNameBytes() {
                return ((PDeviceMi) this.instance).getNameBytes();
            }

            @Override // com.kingsmith.miot.Protos.PDeviceMiOrBuilder
            public String getNetType() {
                return ((PDeviceMi) this.instance).getNetType();
            }

            @Override // com.kingsmith.miot.Protos.PDeviceMiOrBuilder
            public ByteString getNetTypeBytes() {
                return ((PDeviceMi) this.instance).getNetTypeBytes();
            }

            @Override // com.kingsmith.miot.Protos.PDeviceMiOrBuilder
            public int getOwned() {
                return ((PDeviceMi) this.instance).getOwned();
            }

            @Override // com.kingsmith.miot.Protos.PDeviceMiOrBuilder
            public int getStatus() {
                return ((PDeviceMi) this.instance).getStatus();
            }

            public Builder setAddress(String str) {
                copyOnWrite();
                ((PDeviceMi) this.instance).setAddress(str);
                return this;
            }

            public Builder setAddressBytes(ByteString byteString) throws IllegalArgumentException {
                copyOnWrite();
                ((PDeviceMi) this.instance).setAddressBytes(byteString);
                return this;
            }

            public Builder setIotId(String str) {
                copyOnWrite();
                ((PDeviceMi) this.instance).setIotId(str);
                return this;
            }

            public Builder setIotIdBytes(ByteString byteString) throws IllegalArgumentException {
                copyOnWrite();
                ((PDeviceMi) this.instance).setIotIdBytes(byteString);
                return this;
            }

            public Builder setModel(String str) {
                copyOnWrite();
                ((PDeviceMi) this.instance).setModel(str);
                return this;
            }

            public Builder setModelBytes(ByteString byteString) throws IllegalArgumentException {
                copyOnWrite();
                ((PDeviceMi) this.instance).setModelBytes(byteString);
                return this;
            }

            public Builder setName(String str) {
                copyOnWrite();
                ((PDeviceMi) this.instance).setName(str);
                return this;
            }

            public Builder setNameBytes(ByteString byteString) throws IllegalArgumentException {
                copyOnWrite();
                ((PDeviceMi) this.instance).setNameBytes(byteString);
                return this;
            }

            public Builder setNetType(String str) {
                copyOnWrite();
                ((PDeviceMi) this.instance).setNetType(str);
                return this;
            }

            public Builder setNetTypeBytes(ByteString byteString) throws IllegalArgumentException {
                copyOnWrite();
                ((PDeviceMi) this.instance).setNetTypeBytes(byteString);
                return this;
            }

            public Builder setOwned(int i2) {
                copyOnWrite();
                ((PDeviceMi) this.instance).setOwned(i2);
                return this;
            }

            public Builder setStatus(int i2) {
                copyOnWrite();
                ((PDeviceMi) this.instance).setStatus(i2);
                return this;
            }

            private Builder() {
                super(PDeviceMi.DEFAULT_INSTANCE);
            }
        }

        static {
            PDeviceMi pDeviceMi = new PDeviceMi();
            DEFAULT_INSTANCE = pDeviceMi;
            GeneratedMessageLite.registerDefaultInstance(PDeviceMi.class, pDeviceMi);
        }

        private PDeviceMi() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearAddress() {
            this.address_ = getDefaultInstance().getAddress();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearIotId() {
            this.iotId_ = getDefaultInstance().getIotId();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearModel() {
            this.model_ = getDefaultInstance().getModel();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearName() {
            this.name_ = getDefaultInstance().getName();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearNetType() {
            this.netType_ = getDefaultInstance().getNetType();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearOwned() {
            this.owned_ = 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearStatus() {
            this.status_ = 0;
        }

        public static PDeviceMi getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.createBuilder();
        }

        public static PDeviceMi parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (PDeviceMi) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static PDeviceMi parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (PDeviceMi) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer);
        }

        public static Parser<PDeviceMi> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setAddress(String str) {
            str.getClass();
            this.address_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setAddressBytes(ByteString byteString) throws IllegalArgumentException {
            AbstractMessageLite.checkByteStringIsUtf8(byteString);
            this.address_ = byteString.toStringUtf8();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setIotId(String str) {
            str.getClass();
            this.iotId_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setIotIdBytes(ByteString byteString) throws IllegalArgumentException {
            AbstractMessageLite.checkByteStringIsUtf8(byteString);
            this.iotId_ = byteString.toStringUtf8();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setModel(String str) {
            str.getClass();
            this.model_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setModelBytes(ByteString byteString) throws IllegalArgumentException {
            AbstractMessageLite.checkByteStringIsUtf8(byteString);
            this.model_ = byteString.toStringUtf8();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setName(String str) {
            str.getClass();
            this.name_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setNameBytes(ByteString byteString) throws IllegalArgumentException {
            AbstractMessageLite.checkByteStringIsUtf8(byteString);
            this.name_ = byteString.toStringUtf8();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setNetType(String str) {
            str.getClass();
            this.netType_ = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setNetTypeBytes(ByteString byteString) throws IllegalArgumentException {
            AbstractMessageLite.checkByteStringIsUtf8(byteString);
            this.netType_ = byteString.toStringUtf8();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setOwned(int i2) {
            this.owned_ = i2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setStatus(int i2) {
            this.status_ = i2;
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (AnonymousClass1.f18713a[methodToInvoke.ordinal()]) {
                case 1:
                    return new PDeviceMi();
                case 2:
                    return new Builder();
                case 3:
                    return GeneratedMessageLite.newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0007\u0000\u0000\u0001\u0007\u0007\u0000\u0000\u0000\u0001Ȉ\u0002Ȉ\u0003Ȉ\u0004\u0004\u0005\u0004\u0006Ȉ\u0007Ȉ", new Object[]{"name_", "model_", "netType_", "status_", "owned_", "iotId_", "address_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<PDeviceMi> defaultInstanceBasedParser = PARSER;
                    if (defaultInstanceBasedParser == null) {
                        synchronized (PDeviceMi.class) {
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

        @Override // com.kingsmith.miot.Protos.PDeviceMiOrBuilder
        public String getAddress() {
            return this.address_;
        }

        @Override // com.kingsmith.miot.Protos.PDeviceMiOrBuilder
        public ByteString getAddressBytes() {
            return ByteString.copyFromUtf8(this.address_);
        }

        @Override // com.kingsmith.miot.Protos.PDeviceMiOrBuilder
        public String getIotId() {
            return this.iotId_;
        }

        @Override // com.kingsmith.miot.Protos.PDeviceMiOrBuilder
        public ByteString getIotIdBytes() {
            return ByteString.copyFromUtf8(this.iotId_);
        }

        @Override // com.kingsmith.miot.Protos.PDeviceMiOrBuilder
        public String getModel() {
            return this.model_;
        }

        @Override // com.kingsmith.miot.Protos.PDeviceMiOrBuilder
        public ByteString getModelBytes() {
            return ByteString.copyFromUtf8(this.model_);
        }

        @Override // com.kingsmith.miot.Protos.PDeviceMiOrBuilder
        public String getName() {
            return this.name_;
        }

        @Override // com.kingsmith.miot.Protos.PDeviceMiOrBuilder
        public ByteString getNameBytes() {
            return ByteString.copyFromUtf8(this.name_);
        }

        @Override // com.kingsmith.miot.Protos.PDeviceMiOrBuilder
        public String getNetType() {
            return this.netType_;
        }

        @Override // com.kingsmith.miot.Protos.PDeviceMiOrBuilder
        public ByteString getNetTypeBytes() {
            return ByteString.copyFromUtf8(this.netType_);
        }

        @Override // com.kingsmith.miot.Protos.PDeviceMiOrBuilder
        public int getOwned() {
            return this.owned_;
        }

        @Override // com.kingsmith.miot.Protos.PDeviceMiOrBuilder
        public int getStatus() {
            return this.status_;
        }

        public static Builder newBuilder(PDeviceMi pDeviceMi) {
            return DEFAULT_INSTANCE.createBuilder(pDeviceMi);
        }

        public static PDeviceMi parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (PDeviceMi) GeneratedMessageLite.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static PDeviceMi parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (PDeviceMi) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static PDeviceMi parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (PDeviceMi) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
        }

        public static PDeviceMi parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (PDeviceMi) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static PDeviceMi parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (PDeviceMi) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
        }

        public static PDeviceMi parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (PDeviceMi) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static PDeviceMi parseFrom(InputStream inputStream) throws IOException {
            return (PDeviceMi) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static PDeviceMi parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (PDeviceMi) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static PDeviceMi parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (PDeviceMi) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
        }

        public static PDeviceMi parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (PDeviceMi) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }
    }

    public interface PDeviceMiOrBuilder extends MessageLiteOrBuilder {
        String getAddress();

        ByteString getAddressBytes();

        String getIotId();

        ByteString getIotIdBytes();

        String getModel();

        ByteString getModelBytes();

        String getName();

        ByteString getNameBytes();

        String getNetType();

        ByteString getNetTypeBytes();

        int getOwned();

        int getStatus();
    }

    private Protos() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite extensionRegistryLite) {
    }
}
