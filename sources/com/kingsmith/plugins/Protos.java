package com.kingsmith.plugins;

import androidx.exifinterface.media.ExifInterface;
import androidx.media3.exoplayer.rtsp.RtspHeaders;
import com.google.protobuf.AbstractMessage;
import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.AbstractParser;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Internal;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.LazyStringArrayList;
import com.google.protobuf.LazyStringList;
import com.google.protobuf.Message;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.ProtocolStringList;
import com.google.protobuf.RepeatedFieldBuilderV3;
import com.google.protobuf.SingleFieldBuilderV3;
import com.google.protobuf.UnknownFieldSet;
import com.huawei.hms.hmsscankit.DetailRect;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes4.dex */
public final class Protos {
    private static Descriptors.FileDescriptor descriptor = Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(new String[]{"\n\fplugin.proto\"\u001b\n\nInt32Value\u0012\r\n\u0005value\u0018\u0001 \u0001(\u0005\"+\n\u0003Env\u0012\u000f\n\u0007app_key\u0018\u0001 \u0001(\t\u0012\u0013\n\u000bproduct_key\u0018\u0002 \u0001(\t\"!\n\u0005PUser\u0012\n\n\u0002id\u0018\u0001 \u0001(\t\u0012\f\n\u0004name\u0018\u0002 \u0001(\t\"e\n\u0007PDevice\u0012\f\n\u0004name\u0018\u0001 \u0001(\t\u0012\r\n\u0005model\u0018\u0002 \u0001(\t\u0012\u000f\n\u0007netType\u0018\u0003 \u0001(\t\u0012\u000e\n\u0006status\u0018\u0004 \u0001(\u0005\u0012\r\n\u0005owned\u0018\u0005 \u0001(\u0005\u0012\r\n\u0005iotId\u0018\u0006 \u0001(\t\",\n\u0010ListOfDeviceInfo\u0012\u0018\n\u0006device\u0018\u0001 \u0003(\u000b2\b.PDevice\"A\n\u0015ConnectConfigResponse\u0012\u0013\n\u000bproduct_key\u0018\u0001 \u0001(\t\u0012\u0013\n\u000bdevice_name\u0018\u0002 \u0001(\t\"&\n\tPResponse\u0012\f\n\u0004code\u0018\u0001 \u0001(\u0005\u0012\u000b\n\u0003msg\u0018\u0002 \u0001(\t\"(\n\u0007PAction\u0012\u000e\n\u0006action\u0018\u0001 \u0001(\t\u0012\r\n\u0005value\u0018\u0002 \u0003(\t\"â\u0001\n\u0007PMotion\u0012\r\n\u0005speed\u0018\u0001 \u0001(\u0001\u0012\f\n\u0004dist\u0018\u0002 \u0001(\u0005\u0012\f\n\u0004step\u0018\u0003 \u0001(\u0005\u0012\f\n\u0004time\u0018\u0004 \u0001(\u0005\u0012\u000b\n\u0003cal\u0018\u0005 \u0001(\u0005\u0012\u000b\n\u0003spm\u0018\u0006 \u0001(\u0005\u0012\u0011\n\tbutton_id\u0018\u0007 \u0001(\u0005\u0012\r\n\u0005state\u0018\b \u0001(\t\u0012\f\n\u0004mode\u0018\t \u0001(\t\u0012\u0012\n\nchild_lock\u0018\n \u0001(\t\u0012\r\n\u0005power\u0018\u000b \u0001(\t\u0012\f\n\u0004flod\u0018\f \u0001(\u0005\u0012\u0015\n\rmotion_finish\u0018\r \u0001(\b\u0012\f\n\u0004code\u0018\u000e \u0001(\u0005\"K\n\u000bPProperties\u0012\u000b\n\u0003max\u0018\u0001 \u0001(\u0001\u0012\u0012\n\nchild_lock\u0018\u0002 \u0001(\t\u0012\r\n\u0005bssid\u0018\u0003 \u0001(\t\u0012\f\n\u0004code\u0018\u0004 \u0001(\u0005\"å\u0001\n\rPPropertiesWp\u0012\u001c\n\u0006parent\u0018\u0001 \u0001(\u000b2\f.PProperties\u0012\u0013\n\u000bsensitivity\u0018\u0002 \u0001(\u0005\u0012\u0011\n\tgoal_type\u0018\u0003 \u0001(\u0005\u0012\u0012\n\ngoal_value\u0018\u0004 \u0001(\u0005\u0012\u0013\n\u000bstart_speed\u0018\u0005 \u0001(\u0001\u0012\u0014\n\fstartup_type\u0018\u0006 \u0001(\u0005\u0012\f\n\u0004disp\u0018\u0007 \u0001(\u0005\u0012\u000f\n\u0007initial\u0018\b \u0001(\u0005\u0012\u0013\n\u000bmcu_version\u0018\t \u0001(\t\u0012\f\n\u0004unit\u0018\n \u0001(\u0005\u0012\r\n\u0005max_w\u0018\u000b \u0001(\u0001\"\u008d\u0001\n\rPPropertiesTr\u0012\u001c\n\u0006parent\u0018\u0001 \u0001(\u000b2\f.PProperties\u0012\f\n\u0004key1\u0018\u0002 \u0001(\u0005\u0012\f\n\u0004key2\u0018\u0003 \u0001(\u0005\u0012\f\n\u0004key3\u0018\u0004 \u0001(\u0005\u0012\u0010\n\bprogram1\u0018\u0005 \u0001(\t\u0012\u0010\n\bprogram2\u0018\u0006 \u0001(\t\u0012\u0010\n\bprogram3\u0018\u0007 \u0001(\t\".\n\nPOTAStatus\u0012\u0010\n\bprogress\u0018\u0001 \u0001(\u0005\u0012\u000e\n\u0006status\u0018\u0002 \u0001(\t\"J\n\fPOTAFirmware\u0012\u0017\n\u000fcurrent_version\u0018\u0001 \u0001(\t\u0012\u0013\n\u000bnew_version\u0018\u0002 \u0001(\t\u0012\f\n\u0004desc\u0018\u0003 \u0001(\tB'\n\u0014com.kingsmith.pluginB\u0006Protos¢\u0002\u0006Protosb\u0006proto3"}, new Descriptors.FileDescriptor[0]);
    private static final Descriptors.Descriptor internal_static_ConnectConfigResponse_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_ConnectConfigResponse_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Env_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Env_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_Int32Value_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_Int32Value_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_ListOfDeviceInfo_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_ListOfDeviceInfo_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_PAction_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_PAction_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_PDevice_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_PDevice_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_PMotion_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_PMotion_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_POTAFirmware_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_POTAFirmware_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_POTAStatus_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_POTAStatus_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_PPropertiesTr_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_PPropertiesTr_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_PPropertiesWp_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_PPropertiesWp_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_PProperties_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_PProperties_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_PResponse_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_PResponse_fieldAccessorTable;
    private static final Descriptors.Descriptor internal_static_PUser_descriptor;
    private static final GeneratedMessageV3.FieldAccessorTable internal_static_PUser_fieldAccessorTable;

    public static final class ConnectConfigResponse extends GeneratedMessageV3 implements ConnectConfigResponseOrBuilder {
        public static final int DEVICE_NAME_FIELD_NUMBER = 2;
        public static final int PRODUCT_KEY_FIELD_NUMBER = 1;
        private static final long serialVersionUID = 0;
        private volatile Object deviceName_;
        private byte memoizedIsInitialized;
        private volatile Object productKey_;
        private static final ConnectConfigResponse DEFAULT_INSTANCE = new ConnectConfigResponse();
        private static final Parser<ConnectConfigResponse> PARSER = new AbstractParser<ConnectConfigResponse>() { // from class: com.kingsmith.plugins.Protos.ConnectConfigResponse.1
            @Override // com.google.protobuf.Parser
            public ConnectConfigResponse parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new ConnectConfigResponse(codedInputStream, extensionRegistryLite);
            }
        };

        public static ConnectConfigResponse getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return Protos.internal_static_ConnectConfigResponse_descriptor;
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static ConnectConfigResponse parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (ConnectConfigResponse) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, inputStream);
        }

        public static ConnectConfigResponse parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteBuffer);
        }

        public static Parser<ConnectConfigResponse> parser() {
            return PARSER;
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.Message
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof ConnectConfigResponse)) {
                return super.equals(obj);
            }
            ConnectConfigResponse connectConfigResponse = (ConnectConfigResponse) obj;
            return getProductKey().equals(connectConfigResponse.getProductKey()) && getDeviceName().equals(connectConfigResponse.getDeviceName()) && this.unknownFields.equals(connectConfigResponse.unknownFields);
        }

        @Override // com.kingsmith.plugins.Protos.ConnectConfigResponseOrBuilder
        public String getDeviceName() {
            Object obj = this.deviceName_;
            if (obj instanceof String) {
                return (String) obj;
            }
            String stringUtf8 = ((ByteString) obj).toStringUtf8();
            this.deviceName_ = stringUtf8;
            return stringUtf8;
        }

        @Override // com.kingsmith.plugins.Protos.ConnectConfigResponseOrBuilder
        public ByteString getDeviceNameBytes() {
            Object obj = this.deviceName_;
            if (!(obj instanceof String)) {
                return (ByteString) obj;
            }
            ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
            this.deviceName_ = byteStringCopyFromUtf8;
            return byteStringCopyFromUtf8;
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.MessageLite, com.google.protobuf.Message
        public Parser<ConnectConfigResponse> getParserForType() {
            return PARSER;
        }

        @Override // com.kingsmith.plugins.Protos.ConnectConfigResponseOrBuilder
        public String getProductKey() {
            Object obj = this.productKey_;
            if (obj instanceof String) {
                return (String) obj;
            }
            String stringUtf8 = ((ByteString) obj).toStringUtf8();
            this.productKey_ = stringUtf8;
            return stringUtf8;
        }

        @Override // com.kingsmith.plugins.Protos.ConnectConfigResponseOrBuilder
        public ByteString getProductKeyBytes() {
            Object obj = this.productKey_;
            if (!(obj instanceof String)) {
                return (ByteString) obj;
            }
            ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
            this.productKey_ = byteStringCopyFromUtf8;
            return byteStringCopyFromUtf8;
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite
        public int getSerializedSize() {
            int i2 = this.memoizedSize;
            if (i2 != -1) {
                return i2;
            }
            int iComputeStringSize = !getProductKeyBytes().isEmpty() ? GeneratedMessageV3.computeStringSize(1, this.productKey_) : 0;
            if (!getDeviceNameBytes().isEmpty()) {
                iComputeStringSize += GeneratedMessageV3.computeStringSize(2, this.deviceName_);
            }
            int serializedSize = iComputeStringSize + this.unknownFields.getSerializedSize();
            this.memoizedSize = serializedSize;
            return serializedSize;
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.MessageOrBuilder
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.Message
        public int hashCode() {
            int i2 = this.memoizedHashCode;
            if (i2 != 0) {
                return i2;
            }
            int iHashCode = ((((((((((779 + getDescriptor().hashCode()) * 37) + 1) * 53) + getProductKey().hashCode()) * 37) + 2) * 53) + getDeviceName().hashCode()) * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = iHashCode;
            return iHashCode;
        }

        @Override // com.google.protobuf.GeneratedMessageV3
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return Protos.internal_static_ConnectConfigResponse_fieldAccessorTable.ensureFieldAccessorsInitialized(ConnectConfigResponse.class, Builder.class);
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            byte b2 = this.memoizedIsInitialized;
            if (b2 == 1) {
                return true;
            }
            if (b2 == 0) {
                return false;
            }
            this.memoizedIsInitialized = (byte) 1;
            return true;
        }

        @Override // com.google.protobuf.GeneratedMessageV3
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unusedPrivateParameter) {
            return new ConnectConfigResponse();
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            if (!getProductKeyBytes().isEmpty()) {
                GeneratedMessageV3.writeString(codedOutputStream, 1, this.productKey_);
            }
            if (!getDeviceNameBytes().isEmpty()) {
                GeneratedMessageV3.writeString(codedOutputStream, 2, this.deviceName_);
            }
            this.unknownFields.writeTo(codedOutputStream);
        }

        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements ConnectConfigResponseOrBuilder {
            private Object deviceName_;
            private Object productKey_;

            public static final Descriptors.Descriptor getDescriptor() {
                return Protos.internal_static_ConnectConfigResponse_descriptor;
            }

            private void maybeForceBuilderInitialization() {
                boolean unused = GeneratedMessageV3.alwaysUseFieldBuilders;
            }

            public Builder clearDeviceName() {
                this.deviceName_ = ConnectConfigResponse.getDefaultInstance().getDeviceName();
                p();
                return this;
            }

            public Builder clearProductKey() {
                this.productKey_ = ConnectConfigResponse.getDefaultInstance().getProductKey();
                p();
                return this;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder, com.google.protobuf.MessageOrBuilder
            public Descriptors.Descriptor getDescriptorForType() {
                return Protos.internal_static_ConnectConfigResponse_descriptor;
            }

            @Override // com.kingsmith.plugins.Protos.ConnectConfigResponseOrBuilder
            public String getDeviceName() {
                Object obj = this.deviceName_;
                if (obj instanceof String) {
                    return (String) obj;
                }
                String stringUtf8 = ((ByteString) obj).toStringUtf8();
                this.deviceName_ = stringUtf8;
                return stringUtf8;
            }

            @Override // com.kingsmith.plugins.Protos.ConnectConfigResponseOrBuilder
            public ByteString getDeviceNameBytes() {
                Object obj = this.deviceName_;
                if (!(obj instanceof String)) {
                    return (ByteString) obj;
                }
                ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                this.deviceName_ = byteStringCopyFromUtf8;
                return byteStringCopyFromUtf8;
            }

            @Override // com.kingsmith.plugins.Protos.ConnectConfigResponseOrBuilder
            public String getProductKey() {
                Object obj = this.productKey_;
                if (obj instanceof String) {
                    return (String) obj;
                }
                String stringUtf8 = ((ByteString) obj).toStringUtf8();
                this.productKey_ = stringUtf8;
                return stringUtf8;
            }

            @Override // com.kingsmith.plugins.Protos.ConnectConfigResponseOrBuilder
            public ByteString getProductKeyBytes() {
                Object obj = this.productKey_;
                if (!(obj instanceof String)) {
                    return (ByteString) obj;
                }
                ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                this.productKey_ = byteStringCopyFromUtf8;
                return byteStringCopyFromUtf8;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                return true;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder
            protected GeneratedMessageV3.FieldAccessorTable j() {
                return Protos.internal_static_ConnectConfigResponse_fieldAccessorTable.ensureFieldAccessorsInitialized(ConnectConfigResponse.class, Builder.class);
            }

            public Builder setDeviceName(String str) {
                str.getClass();
                this.deviceName_ = str;
                p();
                return this;
            }

            public Builder setDeviceNameBytes(ByteString byteString) throws IllegalArgumentException {
                byteString.getClass();
                AbstractMessageLite.checkByteStringIsUtf8(byteString);
                this.deviceName_ = byteString;
                p();
                return this;
            }

            public Builder setProductKey(String str) {
                str.getClass();
                this.productKey_ = str;
                p();
                return this;
            }

            public Builder setProductKeyBytes(ByteString byteString) throws IllegalArgumentException {
                byteString.getClass();
                AbstractMessageLite.checkByteStringIsUtf8(byteString);
                this.productKey_ = byteString;
                p();
                return this;
            }

            private Builder() {
                this.productKey_ = "";
                this.deviceName_ = "";
                maybeForceBuilderInitialization();
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public Builder addRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, Object obj) {
                return (Builder) super.addRepeatedField(fieldDescriptor, obj);
            }

            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
            public ConnectConfigResponse build() {
                ConnectConfigResponse connectConfigResponseBuildPartial = buildPartial();
                if (connectConfigResponseBuildPartial.isInitialized()) {
                    return connectConfigResponseBuildPartial;
                }
                throw AbstractMessage.Builder.f(connectConfigResponseBuildPartial);
            }

            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
            public ConnectConfigResponse buildPartial() {
                ConnectConfigResponse connectConfigResponse = new ConnectConfigResponse(this);
                connectConfigResponse.productKey_ = this.productKey_;
                connectConfigResponse.deviceName_ = this.deviceName_;
                o();
                return connectConfigResponse;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public Builder clearField(Descriptors.FieldDescriptor fieldDescriptor) {
                return (Builder) super.clearField(fieldDescriptor);
            }

            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLiteOrBuilder, com.google.protobuf.MessageOrBuilder
            public ConnectConfigResponse getDefaultInstanceForType() {
                return ConnectConfigResponse.getDefaultInstance();
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public Builder setField(Descriptors.FieldDescriptor fieldDescriptor, Object obj) {
                return (Builder) super.setField(fieldDescriptor, obj);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public Builder setRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, int i2, Object obj) {
                return (Builder) super.setRepeatedField(fieldDescriptor, i2, obj);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public final Builder setUnknownFields(UnknownFieldSet unknownFieldSet) {
                return (Builder) super.setUnknownFields(unknownFieldSet);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.Message.Builder
            public Builder clearOneof(Descriptors.OneofDescriptor oneofDescriptor) {
                return (Builder) super.clearOneof(oneofDescriptor);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.Message.Builder
            public final Builder mergeUnknownFields(UnknownFieldSet unknownFieldSet) {
                return (Builder) super.mergeUnknownFields(unknownFieldSet);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
            public Builder clear() {
                super.clear();
                this.productKey_ = "";
                this.deviceName_ = "";
                return this;
            }

            private Builder(GeneratedMessageV3.BuilderParent builderParent) {
                super(builderParent);
                this.productKey_ = "";
                this.deviceName_ = "";
                maybeForceBuilderInitialization();
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.AbstractMessageLite.Builder
            /* renamed from: clone */
            public Builder mo65clone() {
                return (Builder) super.mo65clone();
            }

            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.Message.Builder
            public Builder mergeFrom(Message message) {
                if (message instanceof ConnectConfigResponse) {
                    return mergeFrom((ConnectConfigResponse) message);
                }
                super.mergeFrom(message);
                return this;
            }

            public Builder mergeFrom(ConnectConfigResponse connectConfigResponse) {
                if (connectConfigResponse == ConnectConfigResponse.getDefaultInstance()) {
                    return this;
                }
                if (!connectConfigResponse.getProductKey().isEmpty()) {
                    this.productKey_ = connectConfigResponse.productKey_;
                    p();
                }
                if (!connectConfigResponse.getDeviceName().isEmpty()) {
                    this.deviceName_ = connectConfigResponse.deviceName_;
                    p();
                }
                mergeUnknownFields(((GeneratedMessageV3) connectConfigResponse).unknownFields);
                p();
                return this;
            }

            /* JADX WARN: Removed duplicated region for block: B:16:0x0023  */
            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.AbstractMessageLite.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public com.kingsmith.plugins.Protos.ConnectConfigResponse.Builder mergeFrom(com.google.protobuf.CodedInputStream r3, com.google.protobuf.ExtensionRegistryLite r4) throws java.lang.Throwable {
                /*
                    r2 = this;
                    r0 = 0
                    com.google.protobuf.Parser r1 = com.kingsmith.plugins.Protos.ConnectConfigResponse.e()     // Catch: java.lang.Throwable -> L11 com.google.protobuf.InvalidProtocolBufferException -> L13
                    java.lang.Object r3 = r1.parsePartialFrom(r3, r4)     // Catch: java.lang.Throwable -> L11 com.google.protobuf.InvalidProtocolBufferException -> L13
                    com.kingsmith.plugins.Protos$ConnectConfigResponse r3 = (com.kingsmith.plugins.Protos.ConnectConfigResponse) r3     // Catch: java.lang.Throwable -> L11 com.google.protobuf.InvalidProtocolBufferException -> L13
                    if (r3 == 0) goto L10
                    r2.mergeFrom(r3)
                L10:
                    return r2
                L11:
                    r3 = move-exception
                    goto L21
                L13:
                    r3 = move-exception
                    com.google.protobuf.MessageLite r4 = r3.getUnfinishedMessage()     // Catch: java.lang.Throwable -> L11
                    com.kingsmith.plugins.Protos$ConnectConfigResponse r4 = (com.kingsmith.plugins.Protos.ConnectConfigResponse) r4     // Catch: java.lang.Throwable -> L11
                    java.io.IOException r3 = r3.unwrapIOException()     // Catch: java.lang.Throwable -> L1f
                    throw r3     // Catch: java.lang.Throwable -> L1f
                L1f:
                    r3 = move-exception
                    r0 = r4
                L21:
                    if (r0 == 0) goto L26
                    r2.mergeFrom(r0)
                L26:
                    throw r3
                */
                throw new UnsupportedOperationException("Method not decompiled: com.kingsmith.plugins.Protos.ConnectConfigResponse.Builder.mergeFrom(com.google.protobuf.CodedInputStream, com.google.protobuf.ExtensionRegistryLite):com.kingsmith.plugins.Protos$ConnectConfigResponse$Builder");
            }
        }

        public static Builder newBuilder(ConnectConfigResponse connectConfigResponse) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(connectConfigResponse);
        }

        public static ConnectConfigResponse parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteBuffer, extensionRegistryLite);
        }

        private ConnectConfigResponse(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
        }

        public static ConnectConfigResponse parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ConnectConfigResponse) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, inputStream, extensionRegistryLite);
        }

        public static ConnectConfigResponse parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString);
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLiteOrBuilder, com.google.protobuf.MessageOrBuilder
        public ConnectConfigResponse getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite, com.google.protobuf.Message
        public Builder toBuilder() {
            return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
        }

        public static ConnectConfigResponse parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString, extensionRegistryLite);
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite, com.google.protobuf.Message
        public Builder newBuilderForType() {
            return newBuilder();
        }

        private ConnectConfigResponse() {
            this.memoizedIsInitialized = (byte) -1;
            this.productKey_ = "";
            this.deviceName_ = "";
        }

        public static ConnectConfigResponse parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.protobuf.GeneratedMessageV3
        public Builder newBuilderForType(GeneratedMessageV3.BuilderParent builderParent) {
            return new Builder(builderParent);
        }

        public static ConnectConfigResponse parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr, extensionRegistryLite);
        }

        public static ConnectConfigResponse parseFrom(InputStream inputStream) throws IOException {
            return (ConnectConfigResponse) GeneratedMessageV3.parseWithIOException(PARSER, inputStream);
        }

        private ConnectConfigResponse(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            this();
            extensionRegistryLite.getClass();
            UnknownFieldSet.Builder builderNewBuilder = UnknownFieldSet.newBuilder();
            boolean z2 = false;
            while (!z2) {
                try {
                    try {
                        int tag = codedInputStream.readTag();
                        if (tag != 0) {
                            if (tag == 10) {
                                this.productKey_ = codedInputStream.readStringRequireUtf8();
                            } else if (tag != 18) {
                                if (!parseUnknownField(codedInputStream, builderNewBuilder, extensionRegistryLite, tag)) {
                                }
                            } else {
                                this.deviceName_ = codedInputStream.readStringRequireUtf8();
                            }
                        }
                        z2 = true;
                    } catch (InvalidProtocolBufferException e2) {
                        throw e2.setUnfinishedMessage(this);
                    } catch (IOException e3) {
                        throw new InvalidProtocolBufferException(e3).setUnfinishedMessage(this);
                    }
                } catch (Throwable th) {
                    this.unknownFields = builderNewBuilder.build();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            this.unknownFields = builderNewBuilder.build();
            makeExtensionsImmutable();
        }

        public static ConnectConfigResponse parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ConnectConfigResponse) GeneratedMessageV3.parseWithIOException(PARSER, inputStream, extensionRegistryLite);
        }

        public static ConnectConfigResponse parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (ConnectConfigResponse) GeneratedMessageV3.parseWithIOException(PARSER, codedInputStream);
        }

        public static ConnectConfigResponse parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ConnectConfigResponse) GeneratedMessageV3.parseWithIOException(PARSER, codedInputStream, extensionRegistryLite);
        }
    }

    public interface ConnectConfigResponseOrBuilder extends MessageOrBuilder {
        String getDeviceName();

        ByteString getDeviceNameBytes();

        String getProductKey();

        ByteString getProductKeyBytes();
    }

    public static final class Env extends GeneratedMessageV3 implements EnvOrBuilder {
        public static final int APP_KEY_FIELD_NUMBER = 1;
        private static final Env DEFAULT_INSTANCE = new Env();
        private static final Parser<Env> PARSER = new AbstractParser<Env>() { // from class: com.kingsmith.plugins.Protos.Env.1
            @Override // com.google.protobuf.Parser
            public Env parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new Env(codedInputStream, extensionRegistryLite);
            }
        };
        public static final int PRODUCT_KEY_FIELD_NUMBER = 2;
        private static final long serialVersionUID = 0;
        private volatile Object appKey_;
        private byte memoizedIsInitialized;
        private volatile Object productKey_;

        public static Env getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return Protos.internal_static_Env_descriptor;
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Env parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (Env) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, inputStream);
        }

        public static Env parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteBuffer);
        }

        public static Parser<Env> parser() {
            return PARSER;
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.Message
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Env)) {
                return super.equals(obj);
            }
            Env env = (Env) obj;
            return getAppKey().equals(env.getAppKey()) && getProductKey().equals(env.getProductKey()) && this.unknownFields.equals(env.unknownFields);
        }

        @Override // com.kingsmith.plugins.Protos.EnvOrBuilder
        public String getAppKey() {
            Object obj = this.appKey_;
            if (obj instanceof String) {
                return (String) obj;
            }
            String stringUtf8 = ((ByteString) obj).toStringUtf8();
            this.appKey_ = stringUtf8;
            return stringUtf8;
        }

        @Override // com.kingsmith.plugins.Protos.EnvOrBuilder
        public ByteString getAppKeyBytes() {
            Object obj = this.appKey_;
            if (!(obj instanceof String)) {
                return (ByteString) obj;
            }
            ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
            this.appKey_ = byteStringCopyFromUtf8;
            return byteStringCopyFromUtf8;
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.MessageLite, com.google.protobuf.Message
        public Parser<Env> getParserForType() {
            return PARSER;
        }

        @Override // com.kingsmith.plugins.Protos.EnvOrBuilder
        public String getProductKey() {
            Object obj = this.productKey_;
            if (obj instanceof String) {
                return (String) obj;
            }
            String stringUtf8 = ((ByteString) obj).toStringUtf8();
            this.productKey_ = stringUtf8;
            return stringUtf8;
        }

        @Override // com.kingsmith.plugins.Protos.EnvOrBuilder
        public ByteString getProductKeyBytes() {
            Object obj = this.productKey_;
            if (!(obj instanceof String)) {
                return (ByteString) obj;
            }
            ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
            this.productKey_ = byteStringCopyFromUtf8;
            return byteStringCopyFromUtf8;
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite
        public int getSerializedSize() {
            int i2 = this.memoizedSize;
            if (i2 != -1) {
                return i2;
            }
            int iComputeStringSize = !getAppKeyBytes().isEmpty() ? GeneratedMessageV3.computeStringSize(1, this.appKey_) : 0;
            if (!getProductKeyBytes().isEmpty()) {
                iComputeStringSize += GeneratedMessageV3.computeStringSize(2, this.productKey_);
            }
            int serializedSize = iComputeStringSize + this.unknownFields.getSerializedSize();
            this.memoizedSize = serializedSize;
            return serializedSize;
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.MessageOrBuilder
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.Message
        public int hashCode() {
            int i2 = this.memoizedHashCode;
            if (i2 != 0) {
                return i2;
            }
            int iHashCode = ((((((((((779 + getDescriptor().hashCode()) * 37) + 1) * 53) + getAppKey().hashCode()) * 37) + 2) * 53) + getProductKey().hashCode()) * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = iHashCode;
            return iHashCode;
        }

        @Override // com.google.protobuf.GeneratedMessageV3
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return Protos.internal_static_Env_fieldAccessorTable.ensureFieldAccessorsInitialized(Env.class, Builder.class);
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            byte b2 = this.memoizedIsInitialized;
            if (b2 == 1) {
                return true;
            }
            if (b2 == 0) {
                return false;
            }
            this.memoizedIsInitialized = (byte) 1;
            return true;
        }

        @Override // com.google.protobuf.GeneratedMessageV3
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unusedPrivateParameter) {
            return new Env();
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            if (!getAppKeyBytes().isEmpty()) {
                GeneratedMessageV3.writeString(codedOutputStream, 1, this.appKey_);
            }
            if (!getProductKeyBytes().isEmpty()) {
                GeneratedMessageV3.writeString(codedOutputStream, 2, this.productKey_);
            }
            this.unknownFields.writeTo(codedOutputStream);
        }

        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements EnvOrBuilder {
            private Object appKey_;
            private Object productKey_;

            public static final Descriptors.Descriptor getDescriptor() {
                return Protos.internal_static_Env_descriptor;
            }

            private void maybeForceBuilderInitialization() {
                boolean unused = GeneratedMessageV3.alwaysUseFieldBuilders;
            }

            public Builder clearAppKey() {
                this.appKey_ = Env.getDefaultInstance().getAppKey();
                p();
                return this;
            }

            public Builder clearProductKey() {
                this.productKey_ = Env.getDefaultInstance().getProductKey();
                p();
                return this;
            }

            @Override // com.kingsmith.plugins.Protos.EnvOrBuilder
            public String getAppKey() {
                Object obj = this.appKey_;
                if (obj instanceof String) {
                    return (String) obj;
                }
                String stringUtf8 = ((ByteString) obj).toStringUtf8();
                this.appKey_ = stringUtf8;
                return stringUtf8;
            }

            @Override // com.kingsmith.plugins.Protos.EnvOrBuilder
            public ByteString getAppKeyBytes() {
                Object obj = this.appKey_;
                if (!(obj instanceof String)) {
                    return (ByteString) obj;
                }
                ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                this.appKey_ = byteStringCopyFromUtf8;
                return byteStringCopyFromUtf8;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder, com.google.protobuf.MessageOrBuilder
            public Descriptors.Descriptor getDescriptorForType() {
                return Protos.internal_static_Env_descriptor;
            }

            @Override // com.kingsmith.plugins.Protos.EnvOrBuilder
            public String getProductKey() {
                Object obj = this.productKey_;
                if (obj instanceof String) {
                    return (String) obj;
                }
                String stringUtf8 = ((ByteString) obj).toStringUtf8();
                this.productKey_ = stringUtf8;
                return stringUtf8;
            }

            @Override // com.kingsmith.plugins.Protos.EnvOrBuilder
            public ByteString getProductKeyBytes() {
                Object obj = this.productKey_;
                if (!(obj instanceof String)) {
                    return (ByteString) obj;
                }
                ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                this.productKey_ = byteStringCopyFromUtf8;
                return byteStringCopyFromUtf8;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                return true;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder
            protected GeneratedMessageV3.FieldAccessorTable j() {
                return Protos.internal_static_Env_fieldAccessorTable.ensureFieldAccessorsInitialized(Env.class, Builder.class);
            }

            public Builder setAppKey(String str) {
                str.getClass();
                this.appKey_ = str;
                p();
                return this;
            }

            public Builder setAppKeyBytes(ByteString byteString) throws IllegalArgumentException {
                byteString.getClass();
                AbstractMessageLite.checkByteStringIsUtf8(byteString);
                this.appKey_ = byteString;
                p();
                return this;
            }

            public Builder setProductKey(String str) {
                str.getClass();
                this.productKey_ = str;
                p();
                return this;
            }

            public Builder setProductKeyBytes(ByteString byteString) throws IllegalArgumentException {
                byteString.getClass();
                AbstractMessageLite.checkByteStringIsUtf8(byteString);
                this.productKey_ = byteString;
                p();
                return this;
            }

            private Builder() {
                this.appKey_ = "";
                this.productKey_ = "";
                maybeForceBuilderInitialization();
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public Builder addRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, Object obj) {
                return (Builder) super.addRepeatedField(fieldDescriptor, obj);
            }

            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
            public Env build() {
                Env envBuildPartial = buildPartial();
                if (envBuildPartial.isInitialized()) {
                    return envBuildPartial;
                }
                throw AbstractMessage.Builder.f(envBuildPartial);
            }

            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
            public Env buildPartial() {
                Env env = new Env(this);
                env.appKey_ = this.appKey_;
                env.productKey_ = this.productKey_;
                o();
                return env;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public Builder clearField(Descriptors.FieldDescriptor fieldDescriptor) {
                return (Builder) super.clearField(fieldDescriptor);
            }

            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLiteOrBuilder, com.google.protobuf.MessageOrBuilder
            public Env getDefaultInstanceForType() {
                return Env.getDefaultInstance();
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public Builder setField(Descriptors.FieldDescriptor fieldDescriptor, Object obj) {
                return (Builder) super.setField(fieldDescriptor, obj);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public Builder setRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, int i2, Object obj) {
                return (Builder) super.setRepeatedField(fieldDescriptor, i2, obj);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public final Builder setUnknownFields(UnknownFieldSet unknownFieldSet) {
                return (Builder) super.setUnknownFields(unknownFieldSet);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.Message.Builder
            public Builder clearOneof(Descriptors.OneofDescriptor oneofDescriptor) {
                return (Builder) super.clearOneof(oneofDescriptor);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.Message.Builder
            public final Builder mergeUnknownFields(UnknownFieldSet unknownFieldSet) {
                return (Builder) super.mergeUnknownFields(unknownFieldSet);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
            public Builder clear() {
                super.clear();
                this.appKey_ = "";
                this.productKey_ = "";
                return this;
            }

            private Builder(GeneratedMessageV3.BuilderParent builderParent) {
                super(builderParent);
                this.appKey_ = "";
                this.productKey_ = "";
                maybeForceBuilderInitialization();
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.AbstractMessageLite.Builder
            /* renamed from: clone */
            public Builder mo65clone() {
                return (Builder) super.mo65clone();
            }

            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.Message.Builder
            public Builder mergeFrom(Message message) {
                if (message instanceof Env) {
                    return mergeFrom((Env) message);
                }
                super.mergeFrom(message);
                return this;
            }

            public Builder mergeFrom(Env env) {
                if (env == Env.getDefaultInstance()) {
                    return this;
                }
                if (!env.getAppKey().isEmpty()) {
                    this.appKey_ = env.appKey_;
                    p();
                }
                if (!env.getProductKey().isEmpty()) {
                    this.productKey_ = env.productKey_;
                    p();
                }
                mergeUnknownFields(((GeneratedMessageV3) env).unknownFields);
                p();
                return this;
            }

            /* JADX WARN: Removed duplicated region for block: B:16:0x0023  */
            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.AbstractMessageLite.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public com.kingsmith.plugins.Protos.Env.Builder mergeFrom(com.google.protobuf.CodedInputStream r3, com.google.protobuf.ExtensionRegistryLite r4) throws java.lang.Throwable {
                /*
                    r2 = this;
                    r0 = 0
                    com.google.protobuf.Parser r1 = com.kingsmith.plugins.Protos.Env.e()     // Catch: java.lang.Throwable -> L11 com.google.protobuf.InvalidProtocolBufferException -> L13
                    java.lang.Object r3 = r1.parsePartialFrom(r3, r4)     // Catch: java.lang.Throwable -> L11 com.google.protobuf.InvalidProtocolBufferException -> L13
                    com.kingsmith.plugins.Protos$Env r3 = (com.kingsmith.plugins.Protos.Env) r3     // Catch: java.lang.Throwable -> L11 com.google.protobuf.InvalidProtocolBufferException -> L13
                    if (r3 == 0) goto L10
                    r2.mergeFrom(r3)
                L10:
                    return r2
                L11:
                    r3 = move-exception
                    goto L21
                L13:
                    r3 = move-exception
                    com.google.protobuf.MessageLite r4 = r3.getUnfinishedMessage()     // Catch: java.lang.Throwable -> L11
                    com.kingsmith.plugins.Protos$Env r4 = (com.kingsmith.plugins.Protos.Env) r4     // Catch: java.lang.Throwable -> L11
                    java.io.IOException r3 = r3.unwrapIOException()     // Catch: java.lang.Throwable -> L1f
                    throw r3     // Catch: java.lang.Throwable -> L1f
                L1f:
                    r3 = move-exception
                    r0 = r4
                L21:
                    if (r0 == 0) goto L26
                    r2.mergeFrom(r0)
                L26:
                    throw r3
                */
                throw new UnsupportedOperationException("Method not decompiled: com.kingsmith.plugins.Protos.Env.Builder.mergeFrom(com.google.protobuf.CodedInputStream, com.google.protobuf.ExtensionRegistryLite):com.kingsmith.plugins.Protos$Env$Builder");
            }
        }

        public static Builder newBuilder(Env env) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(env);
        }

        public static Env parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteBuffer, extensionRegistryLite);
        }

        private Env(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
        }

        public static Env parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Env) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, inputStream, extensionRegistryLite);
        }

        public static Env parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString);
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLiteOrBuilder, com.google.protobuf.MessageOrBuilder
        public Env getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite, com.google.protobuf.Message
        public Builder toBuilder() {
            return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
        }

        public static Env parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString, extensionRegistryLite);
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite, com.google.protobuf.Message
        public Builder newBuilderForType() {
            return newBuilder();
        }

        private Env() {
            this.memoizedIsInitialized = (byte) -1;
            this.appKey_ = "";
            this.productKey_ = "";
        }

        public static Env parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.protobuf.GeneratedMessageV3
        public Builder newBuilderForType(GeneratedMessageV3.BuilderParent builderParent) {
            return new Builder(builderParent);
        }

        public static Env parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr, extensionRegistryLite);
        }

        public static Env parseFrom(InputStream inputStream) throws IOException {
            return (Env) GeneratedMessageV3.parseWithIOException(PARSER, inputStream);
        }

        private Env(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            this();
            extensionRegistryLite.getClass();
            UnknownFieldSet.Builder builderNewBuilder = UnknownFieldSet.newBuilder();
            boolean z2 = false;
            while (!z2) {
                try {
                    try {
                        int tag = codedInputStream.readTag();
                        if (tag != 0) {
                            if (tag == 10) {
                                this.appKey_ = codedInputStream.readStringRequireUtf8();
                            } else if (tag != 18) {
                                if (!parseUnknownField(codedInputStream, builderNewBuilder, extensionRegistryLite, tag)) {
                                }
                            } else {
                                this.productKey_ = codedInputStream.readStringRequireUtf8();
                            }
                        }
                        z2 = true;
                    } catch (InvalidProtocolBufferException e2) {
                        throw e2.setUnfinishedMessage(this);
                    } catch (IOException e3) {
                        throw new InvalidProtocolBufferException(e3).setUnfinishedMessage(this);
                    }
                } catch (Throwable th) {
                    this.unknownFields = builderNewBuilder.build();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            this.unknownFields = builderNewBuilder.build();
            makeExtensionsImmutable();
        }

        public static Env parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Env) GeneratedMessageV3.parseWithIOException(PARSER, inputStream, extensionRegistryLite);
        }

        public static Env parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (Env) GeneratedMessageV3.parseWithIOException(PARSER, codedInputStream);
        }

        public static Env parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Env) GeneratedMessageV3.parseWithIOException(PARSER, codedInputStream, extensionRegistryLite);
        }
    }

    public interface EnvOrBuilder extends MessageOrBuilder {
        String getAppKey();

        ByteString getAppKeyBytes();

        String getProductKey();

        ByteString getProductKeyBytes();
    }

    public static final class Int32Value extends GeneratedMessageV3 implements Int32ValueOrBuilder {
        private static final Int32Value DEFAULT_INSTANCE = new Int32Value();
        private static final Parser<Int32Value> PARSER = new AbstractParser<Int32Value>() { // from class: com.kingsmith.plugins.Protos.Int32Value.1
            @Override // com.google.protobuf.Parser
            public Int32Value parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new Int32Value(codedInputStream, extensionRegistryLite);
            }
        };
        public static final int VALUE_FIELD_NUMBER = 1;
        private static final long serialVersionUID = 0;
        private byte memoizedIsInitialized;
        private int value_;

        public static Int32Value getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return Protos.internal_static_Int32Value_descriptor;
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Int32Value parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (Int32Value) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, inputStream);
        }

        public static Int32Value parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteBuffer);
        }

        public static Parser<Int32Value> parser() {
            return PARSER;
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.Message
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Int32Value)) {
                return super.equals(obj);
            }
            Int32Value int32Value = (Int32Value) obj;
            return getValue() == int32Value.getValue() && this.unknownFields.equals(int32Value.unknownFields);
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.MessageLite, com.google.protobuf.Message
        public Parser<Int32Value> getParserForType() {
            return PARSER;
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite
        public int getSerializedSize() {
            int i2 = this.memoizedSize;
            if (i2 != -1) {
                return i2;
            }
            int i3 = this.value_;
            int iComputeInt32Size = (i3 != 0 ? CodedOutputStream.computeInt32Size(1, i3) : 0) + this.unknownFields.getSerializedSize();
            this.memoizedSize = iComputeInt32Size;
            return iComputeInt32Size;
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.MessageOrBuilder
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        @Override // com.kingsmith.plugins.Protos.Int32ValueOrBuilder
        public int getValue() {
            return this.value_;
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.Message
        public int hashCode() {
            int i2 = this.memoizedHashCode;
            if (i2 != 0) {
                return i2;
            }
            int iHashCode = ((((((779 + getDescriptor().hashCode()) * 37) + 1) * 53) + getValue()) * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = iHashCode;
            return iHashCode;
        }

        @Override // com.google.protobuf.GeneratedMessageV3
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return Protos.internal_static_Int32Value_fieldAccessorTable.ensureFieldAccessorsInitialized(Int32Value.class, Builder.class);
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            byte b2 = this.memoizedIsInitialized;
            if (b2 == 1) {
                return true;
            }
            if (b2 == 0) {
                return false;
            }
            this.memoizedIsInitialized = (byte) 1;
            return true;
        }

        @Override // com.google.protobuf.GeneratedMessageV3
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unusedPrivateParameter) {
            return new Int32Value();
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            int i2 = this.value_;
            if (i2 != 0) {
                codedOutputStream.writeInt32(1, i2);
            }
            this.unknownFields.writeTo(codedOutputStream);
        }

        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements Int32ValueOrBuilder {
            private int value_;

            public static final Descriptors.Descriptor getDescriptor() {
                return Protos.internal_static_Int32Value_descriptor;
            }

            private void maybeForceBuilderInitialization() {
                boolean unused = GeneratedMessageV3.alwaysUseFieldBuilders;
            }

            public Builder clearValue() {
                this.value_ = 0;
                p();
                return this;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder, com.google.protobuf.MessageOrBuilder
            public Descriptors.Descriptor getDescriptorForType() {
                return Protos.internal_static_Int32Value_descriptor;
            }

            @Override // com.kingsmith.plugins.Protos.Int32ValueOrBuilder
            public int getValue() {
                return this.value_;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                return true;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder
            protected GeneratedMessageV3.FieldAccessorTable j() {
                return Protos.internal_static_Int32Value_fieldAccessorTable.ensureFieldAccessorsInitialized(Int32Value.class, Builder.class);
            }

            public Builder setValue(int i2) {
                this.value_ = i2;
                p();
                return this;
            }

            private Builder() {
                maybeForceBuilderInitialization();
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public Builder addRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, Object obj) {
                return (Builder) super.addRepeatedField(fieldDescriptor, obj);
            }

            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
            public Int32Value build() {
                Int32Value int32ValueBuildPartial = buildPartial();
                if (int32ValueBuildPartial.isInitialized()) {
                    return int32ValueBuildPartial;
                }
                throw AbstractMessage.Builder.f(int32ValueBuildPartial);
            }

            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
            public Int32Value buildPartial() {
                Int32Value int32Value = new Int32Value(this);
                int32Value.value_ = this.value_;
                o();
                return int32Value;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public Builder clearField(Descriptors.FieldDescriptor fieldDescriptor) {
                return (Builder) super.clearField(fieldDescriptor);
            }

            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLiteOrBuilder, com.google.protobuf.MessageOrBuilder
            public Int32Value getDefaultInstanceForType() {
                return Int32Value.getDefaultInstance();
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public Builder setField(Descriptors.FieldDescriptor fieldDescriptor, Object obj) {
                return (Builder) super.setField(fieldDescriptor, obj);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public Builder setRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, int i2, Object obj) {
                return (Builder) super.setRepeatedField(fieldDescriptor, i2, obj);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public final Builder setUnknownFields(UnknownFieldSet unknownFieldSet) {
                return (Builder) super.setUnknownFields(unknownFieldSet);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.Message.Builder
            public Builder clearOneof(Descriptors.OneofDescriptor oneofDescriptor) {
                return (Builder) super.clearOneof(oneofDescriptor);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.Message.Builder
            public final Builder mergeUnknownFields(UnknownFieldSet unknownFieldSet) {
                return (Builder) super.mergeUnknownFields(unknownFieldSet);
            }

            private Builder(GeneratedMessageV3.BuilderParent builderParent) {
                super(builderParent);
                maybeForceBuilderInitialization();
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
            public Builder clear() {
                super.clear();
                this.value_ = 0;
                return this;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.AbstractMessageLite.Builder
            /* renamed from: clone */
            public Builder mo65clone() {
                return (Builder) super.mo65clone();
            }

            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.Message.Builder
            public Builder mergeFrom(Message message) {
                if (message instanceof Int32Value) {
                    return mergeFrom((Int32Value) message);
                }
                super.mergeFrom(message);
                return this;
            }

            public Builder mergeFrom(Int32Value int32Value) {
                if (int32Value == Int32Value.getDefaultInstance()) {
                    return this;
                }
                if (int32Value.getValue() != 0) {
                    setValue(int32Value.getValue());
                }
                mergeUnknownFields(((GeneratedMessageV3) int32Value).unknownFields);
                p();
                return this;
            }

            /* JADX WARN: Removed duplicated region for block: B:16:0x0023  */
            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.AbstractMessageLite.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public com.kingsmith.plugins.Protos.Int32Value.Builder mergeFrom(com.google.protobuf.CodedInputStream r3, com.google.protobuf.ExtensionRegistryLite r4) throws java.lang.Throwable {
                /*
                    r2 = this;
                    r0 = 0
                    com.google.protobuf.Parser r1 = com.kingsmith.plugins.Protos.Int32Value.b()     // Catch: java.lang.Throwable -> L11 com.google.protobuf.InvalidProtocolBufferException -> L13
                    java.lang.Object r3 = r1.parsePartialFrom(r3, r4)     // Catch: java.lang.Throwable -> L11 com.google.protobuf.InvalidProtocolBufferException -> L13
                    com.kingsmith.plugins.Protos$Int32Value r3 = (com.kingsmith.plugins.Protos.Int32Value) r3     // Catch: java.lang.Throwable -> L11 com.google.protobuf.InvalidProtocolBufferException -> L13
                    if (r3 == 0) goto L10
                    r2.mergeFrom(r3)
                L10:
                    return r2
                L11:
                    r3 = move-exception
                    goto L21
                L13:
                    r3 = move-exception
                    com.google.protobuf.MessageLite r4 = r3.getUnfinishedMessage()     // Catch: java.lang.Throwable -> L11
                    com.kingsmith.plugins.Protos$Int32Value r4 = (com.kingsmith.plugins.Protos.Int32Value) r4     // Catch: java.lang.Throwable -> L11
                    java.io.IOException r3 = r3.unwrapIOException()     // Catch: java.lang.Throwable -> L1f
                    throw r3     // Catch: java.lang.Throwable -> L1f
                L1f:
                    r3 = move-exception
                    r0 = r4
                L21:
                    if (r0 == 0) goto L26
                    r2.mergeFrom(r0)
                L26:
                    throw r3
                */
                throw new UnsupportedOperationException("Method not decompiled: com.kingsmith.plugins.Protos.Int32Value.Builder.mergeFrom(com.google.protobuf.CodedInputStream, com.google.protobuf.ExtensionRegistryLite):com.kingsmith.plugins.Protos$Int32Value$Builder");
            }
        }

        public static Builder newBuilder(Int32Value int32Value) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(int32Value);
        }

        public static Int32Value parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteBuffer, extensionRegistryLite);
        }

        private Int32Value(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
        }

        public static Int32Value parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Int32Value) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, inputStream, extensionRegistryLite);
        }

        public static Int32Value parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString);
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLiteOrBuilder, com.google.protobuf.MessageOrBuilder
        public Int32Value getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite, com.google.protobuf.Message
        public Builder toBuilder() {
            return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
        }

        public static Int32Value parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString, extensionRegistryLite);
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite, com.google.protobuf.Message
        public Builder newBuilderForType() {
            return newBuilder();
        }

        private Int32Value() {
            this.memoizedIsInitialized = (byte) -1;
        }

        public static Int32Value parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.protobuf.GeneratedMessageV3
        public Builder newBuilderForType(GeneratedMessageV3.BuilderParent builderParent) {
            return new Builder(builderParent);
        }

        public static Int32Value parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr, extensionRegistryLite);
        }

        private Int32Value(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            this();
            extensionRegistryLite.getClass();
            UnknownFieldSet.Builder builderNewBuilder = UnknownFieldSet.newBuilder();
            boolean z2 = false;
            while (!z2) {
                try {
                    try {
                        try {
                            int tag = codedInputStream.readTag();
                            if (tag != 0) {
                                if (tag != 8) {
                                    if (!parseUnknownField(codedInputStream, builderNewBuilder, extensionRegistryLite, tag)) {
                                    }
                                } else {
                                    this.value_ = codedInputStream.readInt32();
                                }
                            }
                            z2 = true;
                        } catch (IOException e2) {
                            throw new InvalidProtocolBufferException(e2).setUnfinishedMessage(this);
                        }
                    } catch (InvalidProtocolBufferException e3) {
                        throw e3.setUnfinishedMessage(this);
                    }
                } catch (Throwable th) {
                    this.unknownFields = builderNewBuilder.build();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            this.unknownFields = builderNewBuilder.build();
            makeExtensionsImmutable();
        }

        public static Int32Value parseFrom(InputStream inputStream) throws IOException {
            return (Int32Value) GeneratedMessageV3.parseWithIOException(PARSER, inputStream);
        }

        public static Int32Value parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Int32Value) GeneratedMessageV3.parseWithIOException(PARSER, inputStream, extensionRegistryLite);
        }

        public static Int32Value parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (Int32Value) GeneratedMessageV3.parseWithIOException(PARSER, codedInputStream);
        }

        public static Int32Value parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Int32Value) GeneratedMessageV3.parseWithIOException(PARSER, codedInputStream, extensionRegistryLite);
        }
    }

    public interface Int32ValueOrBuilder extends MessageOrBuilder {
        int getValue();
    }

    public static final class ListOfDeviceInfo extends GeneratedMessageV3 implements ListOfDeviceInfoOrBuilder {
        public static final int DEVICE_FIELD_NUMBER = 1;
        private static final long serialVersionUID = 0;
        private List<PDevice> device_;
        private byte memoizedIsInitialized;
        private static final ListOfDeviceInfo DEFAULT_INSTANCE = new ListOfDeviceInfo();
        private static final Parser<ListOfDeviceInfo> PARSER = new AbstractParser<ListOfDeviceInfo>() { // from class: com.kingsmith.plugins.Protos.ListOfDeviceInfo.1
            @Override // com.google.protobuf.Parser
            public ListOfDeviceInfo parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new ListOfDeviceInfo(codedInputStream, extensionRegistryLite);
            }
        };

        public static ListOfDeviceInfo getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return Protos.internal_static_ListOfDeviceInfo_descriptor;
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static ListOfDeviceInfo parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (ListOfDeviceInfo) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, inputStream);
        }

        public static ListOfDeviceInfo parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteBuffer);
        }

        public static Parser<ListOfDeviceInfo> parser() {
            return PARSER;
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.Message
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof ListOfDeviceInfo)) {
                return super.equals(obj);
            }
            ListOfDeviceInfo listOfDeviceInfo = (ListOfDeviceInfo) obj;
            return getDeviceList().equals(listOfDeviceInfo.getDeviceList()) && this.unknownFields.equals(listOfDeviceInfo.unknownFields);
        }

        @Override // com.kingsmith.plugins.Protos.ListOfDeviceInfoOrBuilder
        public PDevice getDevice(int i2) {
            return this.device_.get(i2);
        }

        @Override // com.kingsmith.plugins.Protos.ListOfDeviceInfoOrBuilder
        public int getDeviceCount() {
            return this.device_.size();
        }

        @Override // com.kingsmith.plugins.Protos.ListOfDeviceInfoOrBuilder
        public List<PDevice> getDeviceList() {
            return this.device_;
        }

        @Override // com.kingsmith.plugins.Protos.ListOfDeviceInfoOrBuilder
        public PDeviceOrBuilder getDeviceOrBuilder(int i2) {
            return this.device_.get(i2);
        }

        @Override // com.kingsmith.plugins.Protos.ListOfDeviceInfoOrBuilder
        public List<? extends PDeviceOrBuilder> getDeviceOrBuilderList() {
            return this.device_;
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.MessageLite, com.google.protobuf.Message
        public Parser<ListOfDeviceInfo> getParserForType() {
            return PARSER;
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite
        public int getSerializedSize() {
            int i2 = this.memoizedSize;
            if (i2 != -1) {
                return i2;
            }
            int iComputeMessageSize = 0;
            for (int i3 = 0; i3 < this.device_.size(); i3++) {
                iComputeMessageSize += CodedOutputStream.computeMessageSize(1, this.device_.get(i3));
            }
            int serializedSize = iComputeMessageSize + this.unknownFields.getSerializedSize();
            this.memoizedSize = serializedSize;
            return serializedSize;
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.MessageOrBuilder
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.Message
        public int hashCode() {
            int i2 = this.memoizedHashCode;
            if (i2 != 0) {
                return i2;
            }
            int iHashCode = 779 + getDescriptor().hashCode();
            if (getDeviceCount() > 0) {
                iHashCode = (((iHashCode * 37) + 1) * 53) + getDeviceList().hashCode();
            }
            int iHashCode2 = (iHashCode * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = iHashCode2;
            return iHashCode2;
        }

        @Override // com.google.protobuf.GeneratedMessageV3
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return Protos.internal_static_ListOfDeviceInfo_fieldAccessorTable.ensureFieldAccessorsInitialized(ListOfDeviceInfo.class, Builder.class);
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            byte b2 = this.memoizedIsInitialized;
            if (b2 == 1) {
                return true;
            }
            if (b2 == 0) {
                return false;
            }
            this.memoizedIsInitialized = (byte) 1;
            return true;
        }

        @Override // com.google.protobuf.GeneratedMessageV3
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unusedPrivateParameter) {
            return new ListOfDeviceInfo();
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            for (int i2 = 0; i2 < this.device_.size(); i2++) {
                codedOutputStream.writeMessage(1, this.device_.get(i2));
            }
            this.unknownFields.writeTo(codedOutputStream);
        }

        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements ListOfDeviceInfoOrBuilder {
            private int bitField0_;
            private RepeatedFieldBuilderV3<PDevice, PDevice.Builder, PDeviceOrBuilder> deviceBuilder_;
            private List<PDevice> device_;

            private void ensureDeviceIsMutable() {
                if ((this.bitField0_ & 1) == 0) {
                    this.device_ = new ArrayList(this.device_);
                    this.bitField0_ |= 1;
                }
            }

            public static final Descriptors.Descriptor getDescriptor() {
                return Protos.internal_static_ListOfDeviceInfo_descriptor;
            }

            private RepeatedFieldBuilderV3<PDevice, PDevice.Builder, PDeviceOrBuilder> getDeviceFieldBuilder() {
                if (this.deviceBuilder_ == null) {
                    this.deviceBuilder_ = new RepeatedFieldBuilderV3<>(this.device_, (this.bitField0_ & 1) != 0, i(), m());
                    this.device_ = null;
                }
                return this.deviceBuilder_;
            }

            private void maybeForceBuilderInitialization() {
                if (GeneratedMessageV3.alwaysUseFieldBuilders) {
                    getDeviceFieldBuilder();
                }
            }

            public Builder addAllDevice(Iterable<? extends PDevice> iterable) {
                RepeatedFieldBuilderV3<PDevice, PDevice.Builder, PDeviceOrBuilder> repeatedFieldBuilderV3 = this.deviceBuilder_;
                if (repeatedFieldBuilderV3 == null) {
                    ensureDeviceIsMutable();
                    AbstractMessageLite.Builder.addAll((Iterable) iterable, (List) this.device_);
                    p();
                } else {
                    repeatedFieldBuilderV3.addAllMessages(iterable);
                }
                return this;
            }

            public Builder addDevice(PDevice pDevice) {
                RepeatedFieldBuilderV3<PDevice, PDevice.Builder, PDeviceOrBuilder> repeatedFieldBuilderV3 = this.deviceBuilder_;
                if (repeatedFieldBuilderV3 == null) {
                    pDevice.getClass();
                    ensureDeviceIsMutable();
                    this.device_.add(pDevice);
                    p();
                } else {
                    repeatedFieldBuilderV3.addMessage(pDevice);
                }
                return this;
            }

            public PDevice.Builder addDeviceBuilder() {
                return (PDevice.Builder) getDeviceFieldBuilder().addBuilder(PDevice.getDefaultInstance());
            }

            public Builder clearDevice() {
                RepeatedFieldBuilderV3<PDevice, PDevice.Builder, PDeviceOrBuilder> repeatedFieldBuilderV3 = this.deviceBuilder_;
                if (repeatedFieldBuilderV3 == null) {
                    this.device_ = Collections.emptyList();
                    this.bitField0_ &= -2;
                    p();
                } else {
                    repeatedFieldBuilderV3.clear();
                }
                return this;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder, com.google.protobuf.MessageOrBuilder
            public Descriptors.Descriptor getDescriptorForType() {
                return Protos.internal_static_ListOfDeviceInfo_descriptor;
            }

            @Override // com.kingsmith.plugins.Protos.ListOfDeviceInfoOrBuilder
            public PDevice getDevice(int i2) {
                RepeatedFieldBuilderV3<PDevice, PDevice.Builder, PDeviceOrBuilder> repeatedFieldBuilderV3 = this.deviceBuilder_;
                return repeatedFieldBuilderV3 == null ? this.device_.get(i2) : (PDevice) repeatedFieldBuilderV3.getMessage(i2);
            }

            public PDevice.Builder getDeviceBuilder(int i2) {
                return (PDevice.Builder) getDeviceFieldBuilder().getBuilder(i2);
            }

            public List<PDevice.Builder> getDeviceBuilderList() {
                return getDeviceFieldBuilder().getBuilderList();
            }

            @Override // com.kingsmith.plugins.Protos.ListOfDeviceInfoOrBuilder
            public int getDeviceCount() {
                RepeatedFieldBuilderV3<PDevice, PDevice.Builder, PDeviceOrBuilder> repeatedFieldBuilderV3 = this.deviceBuilder_;
                return repeatedFieldBuilderV3 == null ? this.device_.size() : repeatedFieldBuilderV3.getCount();
            }

            @Override // com.kingsmith.plugins.Protos.ListOfDeviceInfoOrBuilder
            public List<PDevice> getDeviceList() {
                RepeatedFieldBuilderV3<PDevice, PDevice.Builder, PDeviceOrBuilder> repeatedFieldBuilderV3 = this.deviceBuilder_;
                return repeatedFieldBuilderV3 == null ? Collections.unmodifiableList(this.device_) : repeatedFieldBuilderV3.getMessageList();
            }

            @Override // com.kingsmith.plugins.Protos.ListOfDeviceInfoOrBuilder
            public PDeviceOrBuilder getDeviceOrBuilder(int i2) {
                RepeatedFieldBuilderV3<PDevice, PDevice.Builder, PDeviceOrBuilder> repeatedFieldBuilderV3 = this.deviceBuilder_;
                return repeatedFieldBuilderV3 == null ? this.device_.get(i2) : (PDeviceOrBuilder) repeatedFieldBuilderV3.getMessageOrBuilder(i2);
            }

            @Override // com.kingsmith.plugins.Protos.ListOfDeviceInfoOrBuilder
            public List<? extends PDeviceOrBuilder> getDeviceOrBuilderList() {
                RepeatedFieldBuilderV3<PDevice, PDevice.Builder, PDeviceOrBuilder> repeatedFieldBuilderV3 = this.deviceBuilder_;
                return repeatedFieldBuilderV3 != null ? repeatedFieldBuilderV3.getMessageOrBuilderList() : Collections.unmodifiableList(this.device_);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                return true;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder
            protected GeneratedMessageV3.FieldAccessorTable j() {
                return Protos.internal_static_ListOfDeviceInfo_fieldAccessorTable.ensureFieldAccessorsInitialized(ListOfDeviceInfo.class, Builder.class);
            }

            public Builder removeDevice(int i2) {
                RepeatedFieldBuilderV3<PDevice, PDevice.Builder, PDeviceOrBuilder> repeatedFieldBuilderV3 = this.deviceBuilder_;
                if (repeatedFieldBuilderV3 == null) {
                    ensureDeviceIsMutable();
                    this.device_.remove(i2);
                    p();
                } else {
                    repeatedFieldBuilderV3.remove(i2);
                }
                return this;
            }

            public Builder setDevice(int i2, PDevice pDevice) {
                RepeatedFieldBuilderV3<PDevice, PDevice.Builder, PDeviceOrBuilder> repeatedFieldBuilderV3 = this.deviceBuilder_;
                if (repeatedFieldBuilderV3 == null) {
                    pDevice.getClass();
                    ensureDeviceIsMutable();
                    this.device_.set(i2, pDevice);
                    p();
                } else {
                    repeatedFieldBuilderV3.setMessage(i2, pDevice);
                }
                return this;
            }

            private Builder() {
                this.device_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public Builder addRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, Object obj) {
                return (Builder) super.addRepeatedField(fieldDescriptor, obj);
            }

            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
            public ListOfDeviceInfo build() {
                ListOfDeviceInfo listOfDeviceInfoBuildPartial = buildPartial();
                if (listOfDeviceInfoBuildPartial.isInitialized()) {
                    return listOfDeviceInfoBuildPartial;
                }
                throw AbstractMessage.Builder.f(listOfDeviceInfoBuildPartial);
            }

            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
            public ListOfDeviceInfo buildPartial() {
                ListOfDeviceInfo listOfDeviceInfo = new ListOfDeviceInfo(this);
                int i2 = this.bitField0_;
                RepeatedFieldBuilderV3<PDevice, PDevice.Builder, PDeviceOrBuilder> repeatedFieldBuilderV3 = this.deviceBuilder_;
                if (repeatedFieldBuilderV3 == null) {
                    if ((i2 & 1) != 0) {
                        this.device_ = Collections.unmodifiableList(this.device_);
                        this.bitField0_ &= -2;
                    }
                    listOfDeviceInfo.device_ = this.device_;
                } else {
                    listOfDeviceInfo.device_ = repeatedFieldBuilderV3.build();
                }
                o();
                return listOfDeviceInfo;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public Builder clearField(Descriptors.FieldDescriptor fieldDescriptor) {
                return (Builder) super.clearField(fieldDescriptor);
            }

            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLiteOrBuilder, com.google.protobuf.MessageOrBuilder
            public ListOfDeviceInfo getDefaultInstanceForType() {
                return ListOfDeviceInfo.getDefaultInstance();
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public Builder setField(Descriptors.FieldDescriptor fieldDescriptor, Object obj) {
                return (Builder) super.setField(fieldDescriptor, obj);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public Builder setRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, int i2, Object obj) {
                return (Builder) super.setRepeatedField(fieldDescriptor, i2, obj);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public final Builder setUnknownFields(UnknownFieldSet unknownFieldSet) {
                return (Builder) super.setUnknownFields(unknownFieldSet);
            }

            public PDevice.Builder addDeviceBuilder(int i2) {
                return (PDevice.Builder) getDeviceFieldBuilder().addBuilder(i2, PDevice.getDefaultInstance());
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.Message.Builder
            public Builder clearOneof(Descriptors.OneofDescriptor oneofDescriptor) {
                return (Builder) super.clearOneof(oneofDescriptor);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.Message.Builder
            public final Builder mergeUnknownFields(UnknownFieldSet unknownFieldSet) {
                return (Builder) super.mergeUnknownFields(unknownFieldSet);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
            public Builder clear() {
                super.clear();
                RepeatedFieldBuilderV3<PDevice, PDevice.Builder, PDeviceOrBuilder> repeatedFieldBuilderV3 = this.deviceBuilder_;
                if (repeatedFieldBuilderV3 == null) {
                    this.device_ = Collections.emptyList();
                    this.bitField0_ &= -2;
                } else {
                    repeatedFieldBuilderV3.clear();
                }
                return this;
            }

            private Builder(GeneratedMessageV3.BuilderParent builderParent) {
                super(builderParent);
                this.device_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            public Builder addDevice(int i2, PDevice pDevice) {
                RepeatedFieldBuilderV3<PDevice, PDevice.Builder, PDeviceOrBuilder> repeatedFieldBuilderV3 = this.deviceBuilder_;
                if (repeatedFieldBuilderV3 == null) {
                    pDevice.getClass();
                    ensureDeviceIsMutable();
                    this.device_.add(i2, pDevice);
                    p();
                } else {
                    repeatedFieldBuilderV3.addMessage(i2, pDevice);
                }
                return this;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.AbstractMessageLite.Builder
            /* renamed from: clone */
            public Builder mo65clone() {
                return (Builder) super.mo65clone();
            }

            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.Message.Builder
            public Builder mergeFrom(Message message) {
                if (message instanceof ListOfDeviceInfo) {
                    return mergeFrom((ListOfDeviceInfo) message);
                }
                super.mergeFrom(message);
                return this;
            }

            public Builder setDevice(int i2, PDevice.Builder builder) {
                RepeatedFieldBuilderV3<PDevice, PDevice.Builder, PDeviceOrBuilder> repeatedFieldBuilderV3 = this.deviceBuilder_;
                if (repeatedFieldBuilderV3 == null) {
                    ensureDeviceIsMutable();
                    this.device_.set(i2, builder.build());
                    p();
                } else {
                    repeatedFieldBuilderV3.setMessage(i2, builder.build());
                }
                return this;
            }

            public Builder mergeFrom(ListOfDeviceInfo listOfDeviceInfo) {
                if (listOfDeviceInfo == ListOfDeviceInfo.getDefaultInstance()) {
                    return this;
                }
                if (this.deviceBuilder_ == null) {
                    if (!listOfDeviceInfo.device_.isEmpty()) {
                        if (this.device_.isEmpty()) {
                            this.device_ = listOfDeviceInfo.device_;
                            this.bitField0_ &= -2;
                        } else {
                            ensureDeviceIsMutable();
                            this.device_.addAll(listOfDeviceInfo.device_);
                        }
                        p();
                    }
                } else if (!listOfDeviceInfo.device_.isEmpty()) {
                    if (!this.deviceBuilder_.isEmpty()) {
                        this.deviceBuilder_.addAllMessages(listOfDeviceInfo.device_);
                    } else {
                        this.deviceBuilder_.dispose();
                        this.deviceBuilder_ = null;
                        this.device_ = listOfDeviceInfo.device_;
                        this.bitField0_ &= -2;
                        this.deviceBuilder_ = GeneratedMessageV3.alwaysUseFieldBuilders ? getDeviceFieldBuilder() : null;
                    }
                }
                mergeUnknownFields(((GeneratedMessageV3) listOfDeviceInfo).unknownFields);
                p();
                return this;
            }

            public Builder addDevice(PDevice.Builder builder) {
                RepeatedFieldBuilderV3<PDevice, PDevice.Builder, PDeviceOrBuilder> repeatedFieldBuilderV3 = this.deviceBuilder_;
                if (repeatedFieldBuilderV3 == null) {
                    ensureDeviceIsMutable();
                    this.device_.add(builder.build());
                    p();
                } else {
                    repeatedFieldBuilderV3.addMessage(builder.build());
                }
                return this;
            }

            public Builder addDevice(int i2, PDevice.Builder builder) {
                RepeatedFieldBuilderV3<PDevice, PDevice.Builder, PDeviceOrBuilder> repeatedFieldBuilderV3 = this.deviceBuilder_;
                if (repeatedFieldBuilderV3 == null) {
                    ensureDeviceIsMutable();
                    this.device_.add(i2, builder.build());
                    p();
                } else {
                    repeatedFieldBuilderV3.addMessage(i2, builder.build());
                }
                return this;
            }

            /* JADX WARN: Removed duplicated region for block: B:16:0x0023  */
            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.AbstractMessageLite.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public com.kingsmith.plugins.Protos.ListOfDeviceInfo.Builder mergeFrom(com.google.protobuf.CodedInputStream r3, com.google.protobuf.ExtensionRegistryLite r4) throws java.lang.Throwable {
                /*
                    r2 = this;
                    r0 = 0
                    com.google.protobuf.Parser r1 = com.kingsmith.plugins.Protos.ListOfDeviceInfo.c()     // Catch: java.lang.Throwable -> L11 com.google.protobuf.InvalidProtocolBufferException -> L13
                    java.lang.Object r3 = r1.parsePartialFrom(r3, r4)     // Catch: java.lang.Throwable -> L11 com.google.protobuf.InvalidProtocolBufferException -> L13
                    com.kingsmith.plugins.Protos$ListOfDeviceInfo r3 = (com.kingsmith.plugins.Protos.ListOfDeviceInfo) r3     // Catch: java.lang.Throwable -> L11 com.google.protobuf.InvalidProtocolBufferException -> L13
                    if (r3 == 0) goto L10
                    r2.mergeFrom(r3)
                L10:
                    return r2
                L11:
                    r3 = move-exception
                    goto L21
                L13:
                    r3 = move-exception
                    com.google.protobuf.MessageLite r4 = r3.getUnfinishedMessage()     // Catch: java.lang.Throwable -> L11
                    com.kingsmith.plugins.Protos$ListOfDeviceInfo r4 = (com.kingsmith.plugins.Protos.ListOfDeviceInfo) r4     // Catch: java.lang.Throwable -> L11
                    java.io.IOException r3 = r3.unwrapIOException()     // Catch: java.lang.Throwable -> L1f
                    throw r3     // Catch: java.lang.Throwable -> L1f
                L1f:
                    r3 = move-exception
                    r0 = r4
                L21:
                    if (r0 == 0) goto L26
                    r2.mergeFrom(r0)
                L26:
                    throw r3
                */
                throw new UnsupportedOperationException("Method not decompiled: com.kingsmith.plugins.Protos.ListOfDeviceInfo.Builder.mergeFrom(com.google.protobuf.CodedInputStream, com.google.protobuf.ExtensionRegistryLite):com.kingsmith.plugins.Protos$ListOfDeviceInfo$Builder");
            }
        }

        public static Builder newBuilder(ListOfDeviceInfo listOfDeviceInfo) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(listOfDeviceInfo);
        }

        public static ListOfDeviceInfo parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteBuffer, extensionRegistryLite);
        }

        private ListOfDeviceInfo(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
        }

        public static ListOfDeviceInfo parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ListOfDeviceInfo) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, inputStream, extensionRegistryLite);
        }

        public static ListOfDeviceInfo parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString);
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLiteOrBuilder, com.google.protobuf.MessageOrBuilder
        public ListOfDeviceInfo getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite, com.google.protobuf.Message
        public Builder toBuilder() {
            return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
        }

        public static ListOfDeviceInfo parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString, extensionRegistryLite);
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite, com.google.protobuf.Message
        public Builder newBuilderForType() {
            return newBuilder();
        }

        private ListOfDeviceInfo() {
            this.memoizedIsInitialized = (byte) -1;
            this.device_ = Collections.emptyList();
        }

        public static ListOfDeviceInfo parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.protobuf.GeneratedMessageV3
        public Builder newBuilderForType(GeneratedMessageV3.BuilderParent builderParent) {
            return new Builder(builderParent);
        }

        public static ListOfDeviceInfo parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr, extensionRegistryLite);
        }

        public static ListOfDeviceInfo parseFrom(InputStream inputStream) throws IOException {
            return (ListOfDeviceInfo) GeneratedMessageV3.parseWithIOException(PARSER, inputStream);
        }

        private ListOfDeviceInfo(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            this();
            extensionRegistryLite.getClass();
            UnknownFieldSet.Builder builderNewBuilder = UnknownFieldSet.newBuilder();
            boolean z2 = false;
            boolean z3 = false;
            while (!z2) {
                try {
                    try {
                        int tag = codedInputStream.readTag();
                        if (tag != 0) {
                            if (tag != 10) {
                                if (!parseUnknownField(codedInputStream, builderNewBuilder, extensionRegistryLite, tag)) {
                                }
                            } else {
                                if (!z3) {
                                    this.device_ = new ArrayList();
                                    z3 = true;
                                }
                                this.device_.add((PDevice) codedInputStream.readMessage(PDevice.parser(), extensionRegistryLite));
                            }
                        }
                        z2 = true;
                    } catch (InvalidProtocolBufferException e2) {
                        throw e2.setUnfinishedMessage(this);
                    } catch (IOException e3) {
                        throw new InvalidProtocolBufferException(e3).setUnfinishedMessage(this);
                    }
                } catch (Throwable th) {
                    if (z3) {
                        this.device_ = Collections.unmodifiableList(this.device_);
                    }
                    this.unknownFields = builderNewBuilder.build();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            if (z3) {
                this.device_ = Collections.unmodifiableList(this.device_);
            }
            this.unknownFields = builderNewBuilder.build();
            makeExtensionsImmutable();
        }

        public static ListOfDeviceInfo parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ListOfDeviceInfo) GeneratedMessageV3.parseWithIOException(PARSER, inputStream, extensionRegistryLite);
        }

        public static ListOfDeviceInfo parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (ListOfDeviceInfo) GeneratedMessageV3.parseWithIOException(PARSER, codedInputStream);
        }

        public static ListOfDeviceInfo parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ListOfDeviceInfo) GeneratedMessageV3.parseWithIOException(PARSER, codedInputStream, extensionRegistryLite);
        }
    }

    public interface ListOfDeviceInfoOrBuilder extends MessageOrBuilder {
        PDevice getDevice(int i2);

        int getDeviceCount();

        List<PDevice> getDeviceList();

        PDeviceOrBuilder getDeviceOrBuilder(int i2);

        List<? extends PDeviceOrBuilder> getDeviceOrBuilderList();
    }

    public static final class PAction extends GeneratedMessageV3 implements PActionOrBuilder {
        public static final int ACTION_FIELD_NUMBER = 1;
        private static final PAction DEFAULT_INSTANCE = new PAction();
        private static final Parser<PAction> PARSER = new AbstractParser<PAction>() { // from class: com.kingsmith.plugins.Protos.PAction.1
            @Override // com.google.protobuf.Parser
            public PAction parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new PAction(codedInputStream, extensionRegistryLite);
            }
        };
        public static final int VALUE_FIELD_NUMBER = 2;
        private static final long serialVersionUID = 0;
        private volatile Object action_;
        private byte memoizedIsInitialized;
        private LazyStringList value_;

        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements PActionOrBuilder {
            private Object action_;
            private int bitField0_;
            private LazyStringList value_;

            private void ensureValueIsMutable() {
                if ((this.bitField0_ & 1) == 0) {
                    this.value_ = new LazyStringArrayList(this.value_);
                    this.bitField0_ |= 1;
                }
            }

            public static final Descriptors.Descriptor getDescriptor() {
                return Protos.internal_static_PAction_descriptor;
            }

            private void maybeForceBuilderInitialization() {
                boolean unused = GeneratedMessageV3.alwaysUseFieldBuilders;
            }

            public Builder addAllValue(Iterable<String> iterable) {
                ensureValueIsMutable();
                AbstractMessageLite.Builder.addAll((Iterable) iterable, (List) this.value_);
                p();
                return this;
            }

            public Builder addValue(String str) {
                str.getClass();
                ensureValueIsMutable();
                this.value_.add((LazyStringList) str);
                p();
                return this;
            }

            public Builder addValueBytes(ByteString byteString) throws IllegalArgumentException {
                byteString.getClass();
                AbstractMessageLite.checkByteStringIsUtf8(byteString);
                ensureValueIsMutable();
                this.value_.add(byteString);
                p();
                return this;
            }

            public Builder clearAction() {
                this.action_ = PAction.getDefaultInstance().getAction();
                p();
                return this;
            }

            public Builder clearValue() {
                this.value_ = LazyStringArrayList.EMPTY;
                this.bitField0_ &= -2;
                p();
                return this;
            }

            @Override // com.kingsmith.plugins.Protos.PActionOrBuilder
            public String getAction() {
                Object obj = this.action_;
                if (obj instanceof String) {
                    return (String) obj;
                }
                String stringUtf8 = ((ByteString) obj).toStringUtf8();
                this.action_ = stringUtf8;
                return stringUtf8;
            }

            @Override // com.kingsmith.plugins.Protos.PActionOrBuilder
            public ByteString getActionBytes() {
                Object obj = this.action_;
                if (!(obj instanceof String)) {
                    return (ByteString) obj;
                }
                ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                this.action_ = byteStringCopyFromUtf8;
                return byteStringCopyFromUtf8;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder, com.google.protobuf.MessageOrBuilder
            public Descriptors.Descriptor getDescriptorForType() {
                return Protos.internal_static_PAction_descriptor;
            }

            @Override // com.kingsmith.plugins.Protos.PActionOrBuilder
            public String getValue(int i2) {
                return this.value_.get(i2);
            }

            @Override // com.kingsmith.plugins.Protos.PActionOrBuilder
            public ByteString getValueBytes(int i2) {
                return this.value_.getByteString(i2);
            }

            @Override // com.kingsmith.plugins.Protos.PActionOrBuilder
            public int getValueCount() {
                return this.value_.size();
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                return true;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder
            protected GeneratedMessageV3.FieldAccessorTable j() {
                return Protos.internal_static_PAction_fieldAccessorTable.ensureFieldAccessorsInitialized(PAction.class, Builder.class);
            }

            public Builder setAction(String str) {
                str.getClass();
                this.action_ = str;
                p();
                return this;
            }

            public Builder setActionBytes(ByteString byteString) throws IllegalArgumentException {
                byteString.getClass();
                AbstractMessageLite.checkByteStringIsUtf8(byteString);
                this.action_ = byteString;
                p();
                return this;
            }

            public Builder setValue(int i2, String str) {
                str.getClass();
                ensureValueIsMutable();
                this.value_.set(i2, (int) str);
                p();
                return this;
            }

            @Override // com.kingsmith.plugins.Protos.PActionOrBuilder
            public ProtocolStringList getValueList() {
                return this.value_.getUnmodifiableView();
            }

            private Builder() {
                this.action_ = "";
                this.value_ = LazyStringArrayList.EMPTY;
                maybeForceBuilderInitialization();
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public Builder addRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, Object obj) {
                return (Builder) super.addRepeatedField(fieldDescriptor, obj);
            }

            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
            public PAction build() {
                PAction pActionBuildPartial = buildPartial();
                if (pActionBuildPartial.isInitialized()) {
                    return pActionBuildPartial;
                }
                throw AbstractMessage.Builder.f(pActionBuildPartial);
            }

            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
            public PAction buildPartial() {
                PAction pAction = new PAction(this);
                pAction.action_ = this.action_;
                if ((this.bitField0_ & 1) != 0) {
                    this.value_ = this.value_.getUnmodifiableView();
                    this.bitField0_ &= -2;
                }
                pAction.value_ = this.value_;
                o();
                return pAction;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public Builder clearField(Descriptors.FieldDescriptor fieldDescriptor) {
                return (Builder) super.clearField(fieldDescriptor);
            }

            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLiteOrBuilder, com.google.protobuf.MessageOrBuilder
            public PAction getDefaultInstanceForType() {
                return PAction.getDefaultInstance();
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public Builder setField(Descriptors.FieldDescriptor fieldDescriptor, Object obj) {
                return (Builder) super.setField(fieldDescriptor, obj);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public Builder setRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, int i2, Object obj) {
                return (Builder) super.setRepeatedField(fieldDescriptor, i2, obj);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public final Builder setUnknownFields(UnknownFieldSet unknownFieldSet) {
                return (Builder) super.setUnknownFields(unknownFieldSet);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.Message.Builder
            public Builder clearOneof(Descriptors.OneofDescriptor oneofDescriptor) {
                return (Builder) super.clearOneof(oneofDescriptor);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.Message.Builder
            public final Builder mergeUnknownFields(UnknownFieldSet unknownFieldSet) {
                return (Builder) super.mergeUnknownFields(unknownFieldSet);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
            public Builder clear() {
                super.clear();
                this.action_ = "";
                this.value_ = LazyStringArrayList.EMPTY;
                this.bitField0_ &= -2;
                return this;
            }

            private Builder(GeneratedMessageV3.BuilderParent builderParent) {
                super(builderParent);
                this.action_ = "";
                this.value_ = LazyStringArrayList.EMPTY;
                maybeForceBuilderInitialization();
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.AbstractMessageLite.Builder
            /* renamed from: clone */
            public Builder mo65clone() {
                return (Builder) super.mo65clone();
            }

            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.Message.Builder
            public Builder mergeFrom(Message message) {
                if (message instanceof PAction) {
                    return mergeFrom((PAction) message);
                }
                super.mergeFrom(message);
                return this;
            }

            public Builder mergeFrom(PAction pAction) {
                if (pAction == PAction.getDefaultInstance()) {
                    return this;
                }
                if (!pAction.getAction().isEmpty()) {
                    this.action_ = pAction.action_;
                    p();
                }
                if (!pAction.value_.isEmpty()) {
                    if (this.value_.isEmpty()) {
                        this.value_ = pAction.value_;
                        this.bitField0_ &= -2;
                    } else {
                        ensureValueIsMutable();
                        this.value_.addAll(pAction.value_);
                    }
                    p();
                }
                mergeUnknownFields(((GeneratedMessageV3) pAction).unknownFields);
                p();
                return this;
            }

            /* JADX WARN: Removed duplicated region for block: B:16:0x0023  */
            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.AbstractMessageLite.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public com.kingsmith.plugins.Protos.PAction.Builder mergeFrom(com.google.protobuf.CodedInputStream r3, com.google.protobuf.ExtensionRegistryLite r4) throws java.lang.Throwable {
                /*
                    r2 = this;
                    r0 = 0
                    com.google.protobuf.Parser r1 = com.kingsmith.plugins.Protos.PAction.e()     // Catch: java.lang.Throwable -> L11 com.google.protobuf.InvalidProtocolBufferException -> L13
                    java.lang.Object r3 = r1.parsePartialFrom(r3, r4)     // Catch: java.lang.Throwable -> L11 com.google.protobuf.InvalidProtocolBufferException -> L13
                    com.kingsmith.plugins.Protos$PAction r3 = (com.kingsmith.plugins.Protos.PAction) r3     // Catch: java.lang.Throwable -> L11 com.google.protobuf.InvalidProtocolBufferException -> L13
                    if (r3 == 0) goto L10
                    r2.mergeFrom(r3)
                L10:
                    return r2
                L11:
                    r3 = move-exception
                    goto L21
                L13:
                    r3 = move-exception
                    com.google.protobuf.MessageLite r4 = r3.getUnfinishedMessage()     // Catch: java.lang.Throwable -> L11
                    com.kingsmith.plugins.Protos$PAction r4 = (com.kingsmith.plugins.Protos.PAction) r4     // Catch: java.lang.Throwable -> L11
                    java.io.IOException r3 = r3.unwrapIOException()     // Catch: java.lang.Throwable -> L1f
                    throw r3     // Catch: java.lang.Throwable -> L1f
                L1f:
                    r3 = move-exception
                    r0 = r4
                L21:
                    if (r0 == 0) goto L26
                    r2.mergeFrom(r0)
                L26:
                    throw r3
                */
                throw new UnsupportedOperationException("Method not decompiled: com.kingsmith.plugins.Protos.PAction.Builder.mergeFrom(com.google.protobuf.CodedInputStream, com.google.protobuf.ExtensionRegistryLite):com.kingsmith.plugins.Protos$PAction$Builder");
            }
        }

        public static PAction getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return Protos.internal_static_PAction_descriptor;
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static PAction parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (PAction) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, inputStream);
        }

        public static PAction parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteBuffer);
        }

        public static Parser<PAction> parser() {
            return PARSER;
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.Message
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof PAction)) {
                return super.equals(obj);
            }
            PAction pAction = (PAction) obj;
            return getAction().equals(pAction.getAction()) && getValueList().equals(pAction.getValueList()) && this.unknownFields.equals(pAction.unknownFields);
        }

        @Override // com.kingsmith.plugins.Protos.PActionOrBuilder
        public String getAction() {
            Object obj = this.action_;
            if (obj instanceof String) {
                return (String) obj;
            }
            String stringUtf8 = ((ByteString) obj).toStringUtf8();
            this.action_ = stringUtf8;
            return stringUtf8;
        }

        @Override // com.kingsmith.plugins.Protos.PActionOrBuilder
        public ByteString getActionBytes() {
            Object obj = this.action_;
            if (!(obj instanceof String)) {
                return (ByteString) obj;
            }
            ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
            this.action_ = byteStringCopyFromUtf8;
            return byteStringCopyFromUtf8;
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.MessageLite, com.google.protobuf.Message
        public Parser<PAction> getParserForType() {
            return PARSER;
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite
        public int getSerializedSize() {
            int i2 = this.memoizedSize;
            if (i2 != -1) {
                return i2;
            }
            int iComputeStringSize = !getActionBytes().isEmpty() ? GeneratedMessageV3.computeStringSize(1, this.action_) : 0;
            int iComputeStringSizeNoTag = 0;
            for (int i3 = 0; i3 < this.value_.size(); i3++) {
                iComputeStringSizeNoTag += GeneratedMessageV3.computeStringSizeNoTag(this.value_.getRaw(i3));
            }
            int size = iComputeStringSize + iComputeStringSizeNoTag + getValueList().size() + this.unknownFields.getSerializedSize();
            this.memoizedSize = size;
            return size;
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.MessageOrBuilder
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        @Override // com.kingsmith.plugins.Protos.PActionOrBuilder
        public String getValue(int i2) {
            return this.value_.get(i2);
        }

        @Override // com.kingsmith.plugins.Protos.PActionOrBuilder
        public ByteString getValueBytes(int i2) {
            return this.value_.getByteString(i2);
        }

        @Override // com.kingsmith.plugins.Protos.PActionOrBuilder
        public int getValueCount() {
            return this.value_.size();
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.Message
        public int hashCode() {
            int i2 = this.memoizedHashCode;
            if (i2 != 0) {
                return i2;
            }
            int iHashCode = ((((779 + getDescriptor().hashCode()) * 37) + 1) * 53) + getAction().hashCode();
            if (getValueCount() > 0) {
                iHashCode = (((iHashCode * 37) + 2) * 53) + getValueList().hashCode();
            }
            int iHashCode2 = (iHashCode * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = iHashCode2;
            return iHashCode2;
        }

        @Override // com.google.protobuf.GeneratedMessageV3
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return Protos.internal_static_PAction_fieldAccessorTable.ensureFieldAccessorsInitialized(PAction.class, Builder.class);
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            byte b2 = this.memoizedIsInitialized;
            if (b2 == 1) {
                return true;
            }
            if (b2 == 0) {
                return false;
            }
            this.memoizedIsInitialized = (byte) 1;
            return true;
        }

        @Override // com.google.protobuf.GeneratedMessageV3
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unusedPrivateParameter) {
            return new PAction();
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            if (!getActionBytes().isEmpty()) {
                GeneratedMessageV3.writeString(codedOutputStream, 1, this.action_);
            }
            for (int i2 = 0; i2 < this.value_.size(); i2++) {
                GeneratedMessageV3.writeString(codedOutputStream, 2, this.value_.getRaw(i2));
            }
            this.unknownFields.writeTo(codedOutputStream);
        }

        public static Builder newBuilder(PAction pAction) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(pAction);
        }

        public static PAction parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteBuffer, extensionRegistryLite);
        }

        @Override // com.kingsmith.plugins.Protos.PActionOrBuilder
        public ProtocolStringList getValueList() {
            return this.value_;
        }

        private PAction(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
        }

        public static PAction parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (PAction) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, inputStream, extensionRegistryLite);
        }

        public static PAction parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString);
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLiteOrBuilder, com.google.protobuf.MessageOrBuilder
        public PAction getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite, com.google.protobuf.Message
        public Builder toBuilder() {
            return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
        }

        public static PAction parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString, extensionRegistryLite);
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite, com.google.protobuf.Message
        public Builder newBuilderForType() {
            return newBuilder();
        }

        private PAction() {
            this.memoizedIsInitialized = (byte) -1;
            this.action_ = "";
            this.value_ = LazyStringArrayList.EMPTY;
        }

        public static PAction parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.protobuf.GeneratedMessageV3
        public Builder newBuilderForType(GeneratedMessageV3.BuilderParent builderParent) {
            return new Builder(builderParent);
        }

        public static PAction parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr, extensionRegistryLite);
        }

        public static PAction parseFrom(InputStream inputStream) throws IOException {
            return (PAction) GeneratedMessageV3.parseWithIOException(PARSER, inputStream);
        }

        private PAction(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            this();
            extensionRegistryLite.getClass();
            UnknownFieldSet.Builder builderNewBuilder = UnknownFieldSet.newBuilder();
            boolean z2 = false;
            boolean z3 = false;
            while (!z2) {
                try {
                    try {
                        int tag = codedInputStream.readTag();
                        if (tag != 0) {
                            if (tag == 10) {
                                this.action_ = codedInputStream.readStringRequireUtf8();
                            } else if (tag != 18) {
                                if (!parseUnknownField(codedInputStream, builderNewBuilder, extensionRegistryLite, tag)) {
                                }
                            } else {
                                String stringRequireUtf8 = codedInputStream.readStringRequireUtf8();
                                if (!z3) {
                                    this.value_ = new LazyStringArrayList();
                                    z3 = true;
                                }
                                this.value_.add((LazyStringList) stringRequireUtf8);
                            }
                        }
                        z2 = true;
                    } catch (InvalidProtocolBufferException e2) {
                        throw e2.setUnfinishedMessage(this);
                    } catch (IOException e3) {
                        throw new InvalidProtocolBufferException(e3).setUnfinishedMessage(this);
                    }
                } catch (Throwable th) {
                    if (z3) {
                        this.value_ = this.value_.getUnmodifiableView();
                    }
                    this.unknownFields = builderNewBuilder.build();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            if (z3) {
                this.value_ = this.value_.getUnmodifiableView();
            }
            this.unknownFields = builderNewBuilder.build();
            makeExtensionsImmutable();
        }

        public static PAction parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (PAction) GeneratedMessageV3.parseWithIOException(PARSER, inputStream, extensionRegistryLite);
        }

        public static PAction parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (PAction) GeneratedMessageV3.parseWithIOException(PARSER, codedInputStream);
        }

        public static PAction parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (PAction) GeneratedMessageV3.parseWithIOException(PARSER, codedInputStream, extensionRegistryLite);
        }
    }

    public interface PActionOrBuilder extends MessageOrBuilder {
        String getAction();

        ByteString getActionBytes();

        String getValue(int i2);

        ByteString getValueBytes(int i2);

        int getValueCount();

        List<String> getValueList();
    }

    public static final class PDevice extends GeneratedMessageV3 implements PDeviceOrBuilder {
        public static final int IOTID_FIELD_NUMBER = 6;
        public static final int MODEL_FIELD_NUMBER = 2;
        public static final int NAME_FIELD_NUMBER = 1;
        public static final int NETTYPE_FIELD_NUMBER = 3;
        public static final int OWNED_FIELD_NUMBER = 5;
        public static final int STATUS_FIELD_NUMBER = 4;
        private static final long serialVersionUID = 0;
        private volatile Object iotId_;
        private byte memoizedIsInitialized;
        private volatile Object model_;
        private volatile Object name_;
        private volatile Object netType_;
        private int owned_;
        private int status_;
        private static final PDevice DEFAULT_INSTANCE = new PDevice();
        private static final Parser<PDevice> PARSER = new AbstractParser<PDevice>() { // from class: com.kingsmith.plugins.Protos.PDevice.1
            @Override // com.google.protobuf.Parser
            public PDevice parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new PDevice(codedInputStream, extensionRegistryLite);
            }
        };

        public static PDevice getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return Protos.internal_static_PDevice_descriptor;
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static PDevice parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (PDevice) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, inputStream);
        }

        public static PDevice parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteBuffer);
        }

        public static Parser<PDevice> parser() {
            return PARSER;
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.Message
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof PDevice)) {
                return super.equals(obj);
            }
            PDevice pDevice = (PDevice) obj;
            return getName().equals(pDevice.getName()) && getModel().equals(pDevice.getModel()) && getNetType().equals(pDevice.getNetType()) && getStatus() == pDevice.getStatus() && getOwned() == pDevice.getOwned() && getIotId().equals(pDevice.getIotId()) && this.unknownFields.equals(pDevice.unknownFields);
        }

        @Override // com.kingsmith.plugins.Protos.PDeviceOrBuilder
        public String getIotId() {
            Object obj = this.iotId_;
            if (obj instanceof String) {
                return (String) obj;
            }
            String stringUtf8 = ((ByteString) obj).toStringUtf8();
            this.iotId_ = stringUtf8;
            return stringUtf8;
        }

        @Override // com.kingsmith.plugins.Protos.PDeviceOrBuilder
        public ByteString getIotIdBytes() {
            Object obj = this.iotId_;
            if (!(obj instanceof String)) {
                return (ByteString) obj;
            }
            ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
            this.iotId_ = byteStringCopyFromUtf8;
            return byteStringCopyFromUtf8;
        }

        @Override // com.kingsmith.plugins.Protos.PDeviceOrBuilder
        public String getModel() {
            Object obj = this.model_;
            if (obj instanceof String) {
                return (String) obj;
            }
            String stringUtf8 = ((ByteString) obj).toStringUtf8();
            this.model_ = stringUtf8;
            return stringUtf8;
        }

        @Override // com.kingsmith.plugins.Protos.PDeviceOrBuilder
        public ByteString getModelBytes() {
            Object obj = this.model_;
            if (!(obj instanceof String)) {
                return (ByteString) obj;
            }
            ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
            this.model_ = byteStringCopyFromUtf8;
            return byteStringCopyFromUtf8;
        }

        @Override // com.kingsmith.plugins.Protos.PDeviceOrBuilder
        public String getName() {
            Object obj = this.name_;
            if (obj instanceof String) {
                return (String) obj;
            }
            String stringUtf8 = ((ByteString) obj).toStringUtf8();
            this.name_ = stringUtf8;
            return stringUtf8;
        }

        @Override // com.kingsmith.plugins.Protos.PDeviceOrBuilder
        public ByteString getNameBytes() {
            Object obj = this.name_;
            if (!(obj instanceof String)) {
                return (ByteString) obj;
            }
            ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
            this.name_ = byteStringCopyFromUtf8;
            return byteStringCopyFromUtf8;
        }

        @Override // com.kingsmith.plugins.Protos.PDeviceOrBuilder
        public String getNetType() {
            Object obj = this.netType_;
            if (obj instanceof String) {
                return (String) obj;
            }
            String stringUtf8 = ((ByteString) obj).toStringUtf8();
            this.netType_ = stringUtf8;
            return stringUtf8;
        }

        @Override // com.kingsmith.plugins.Protos.PDeviceOrBuilder
        public ByteString getNetTypeBytes() {
            Object obj = this.netType_;
            if (!(obj instanceof String)) {
                return (ByteString) obj;
            }
            ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
            this.netType_ = byteStringCopyFromUtf8;
            return byteStringCopyFromUtf8;
        }

        @Override // com.kingsmith.plugins.Protos.PDeviceOrBuilder
        public int getOwned() {
            return this.owned_;
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.MessageLite, com.google.protobuf.Message
        public Parser<PDevice> getParserForType() {
            return PARSER;
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite
        public int getSerializedSize() {
            int i2 = this.memoizedSize;
            if (i2 != -1) {
                return i2;
            }
            int iComputeStringSize = !getNameBytes().isEmpty() ? GeneratedMessageV3.computeStringSize(1, this.name_) : 0;
            if (!getModelBytes().isEmpty()) {
                iComputeStringSize += GeneratedMessageV3.computeStringSize(2, this.model_);
            }
            if (!getNetTypeBytes().isEmpty()) {
                iComputeStringSize += GeneratedMessageV3.computeStringSize(3, this.netType_);
            }
            int i3 = this.status_;
            if (i3 != 0) {
                iComputeStringSize += CodedOutputStream.computeInt32Size(4, i3);
            }
            int i4 = this.owned_;
            if (i4 != 0) {
                iComputeStringSize += CodedOutputStream.computeInt32Size(5, i4);
            }
            if (!getIotIdBytes().isEmpty()) {
                iComputeStringSize += GeneratedMessageV3.computeStringSize(6, this.iotId_);
            }
            int serializedSize = iComputeStringSize + this.unknownFields.getSerializedSize();
            this.memoizedSize = serializedSize;
            return serializedSize;
        }

        @Override // com.kingsmith.plugins.Protos.PDeviceOrBuilder
        public int getStatus() {
            return this.status_;
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.MessageOrBuilder
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.Message
        public int hashCode() {
            int i2 = this.memoizedHashCode;
            if (i2 != 0) {
                return i2;
            }
            int iHashCode = ((((((((((((((((((((((((((779 + getDescriptor().hashCode()) * 37) + 1) * 53) + getName().hashCode()) * 37) + 2) * 53) + getModel().hashCode()) * 37) + 3) * 53) + getNetType().hashCode()) * 37) + 4) * 53) + getStatus()) * 37) + 5) * 53) + getOwned()) * 37) + 6) * 53) + getIotId().hashCode()) * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = iHashCode;
            return iHashCode;
        }

        @Override // com.google.protobuf.GeneratedMessageV3
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return Protos.internal_static_PDevice_fieldAccessorTable.ensureFieldAccessorsInitialized(PDevice.class, Builder.class);
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            byte b2 = this.memoizedIsInitialized;
            if (b2 == 1) {
                return true;
            }
            if (b2 == 0) {
                return false;
            }
            this.memoizedIsInitialized = (byte) 1;
            return true;
        }

        @Override // com.google.protobuf.GeneratedMessageV3
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unusedPrivateParameter) {
            return new PDevice();
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            if (!getNameBytes().isEmpty()) {
                GeneratedMessageV3.writeString(codedOutputStream, 1, this.name_);
            }
            if (!getModelBytes().isEmpty()) {
                GeneratedMessageV3.writeString(codedOutputStream, 2, this.model_);
            }
            if (!getNetTypeBytes().isEmpty()) {
                GeneratedMessageV3.writeString(codedOutputStream, 3, this.netType_);
            }
            int i2 = this.status_;
            if (i2 != 0) {
                codedOutputStream.writeInt32(4, i2);
            }
            int i3 = this.owned_;
            if (i3 != 0) {
                codedOutputStream.writeInt32(5, i3);
            }
            if (!getIotIdBytes().isEmpty()) {
                GeneratedMessageV3.writeString(codedOutputStream, 6, this.iotId_);
            }
            this.unknownFields.writeTo(codedOutputStream);
        }

        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements PDeviceOrBuilder {
            private Object iotId_;
            private Object model_;
            private Object name_;
            private Object netType_;
            private int owned_;
            private int status_;

            public static final Descriptors.Descriptor getDescriptor() {
                return Protos.internal_static_PDevice_descriptor;
            }

            private void maybeForceBuilderInitialization() {
                boolean unused = GeneratedMessageV3.alwaysUseFieldBuilders;
            }

            public Builder clearIotId() {
                this.iotId_ = PDevice.getDefaultInstance().getIotId();
                p();
                return this;
            }

            public Builder clearModel() {
                this.model_ = PDevice.getDefaultInstance().getModel();
                p();
                return this;
            }

            public Builder clearName() {
                this.name_ = PDevice.getDefaultInstance().getName();
                p();
                return this;
            }

            public Builder clearNetType() {
                this.netType_ = PDevice.getDefaultInstance().getNetType();
                p();
                return this;
            }

            public Builder clearOwned() {
                this.owned_ = 0;
                p();
                return this;
            }

            public Builder clearStatus() {
                this.status_ = 0;
                p();
                return this;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder, com.google.protobuf.MessageOrBuilder
            public Descriptors.Descriptor getDescriptorForType() {
                return Protos.internal_static_PDevice_descriptor;
            }

            @Override // com.kingsmith.plugins.Protos.PDeviceOrBuilder
            public String getIotId() {
                Object obj = this.iotId_;
                if (obj instanceof String) {
                    return (String) obj;
                }
                String stringUtf8 = ((ByteString) obj).toStringUtf8();
                this.iotId_ = stringUtf8;
                return stringUtf8;
            }

            @Override // com.kingsmith.plugins.Protos.PDeviceOrBuilder
            public ByteString getIotIdBytes() {
                Object obj = this.iotId_;
                if (!(obj instanceof String)) {
                    return (ByteString) obj;
                }
                ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                this.iotId_ = byteStringCopyFromUtf8;
                return byteStringCopyFromUtf8;
            }

            @Override // com.kingsmith.plugins.Protos.PDeviceOrBuilder
            public String getModel() {
                Object obj = this.model_;
                if (obj instanceof String) {
                    return (String) obj;
                }
                String stringUtf8 = ((ByteString) obj).toStringUtf8();
                this.model_ = stringUtf8;
                return stringUtf8;
            }

            @Override // com.kingsmith.plugins.Protos.PDeviceOrBuilder
            public ByteString getModelBytes() {
                Object obj = this.model_;
                if (!(obj instanceof String)) {
                    return (ByteString) obj;
                }
                ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                this.model_ = byteStringCopyFromUtf8;
                return byteStringCopyFromUtf8;
            }

            @Override // com.kingsmith.plugins.Protos.PDeviceOrBuilder
            public String getName() {
                Object obj = this.name_;
                if (obj instanceof String) {
                    return (String) obj;
                }
                String stringUtf8 = ((ByteString) obj).toStringUtf8();
                this.name_ = stringUtf8;
                return stringUtf8;
            }

            @Override // com.kingsmith.plugins.Protos.PDeviceOrBuilder
            public ByteString getNameBytes() {
                Object obj = this.name_;
                if (!(obj instanceof String)) {
                    return (ByteString) obj;
                }
                ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                this.name_ = byteStringCopyFromUtf8;
                return byteStringCopyFromUtf8;
            }

            @Override // com.kingsmith.plugins.Protos.PDeviceOrBuilder
            public String getNetType() {
                Object obj = this.netType_;
                if (obj instanceof String) {
                    return (String) obj;
                }
                String stringUtf8 = ((ByteString) obj).toStringUtf8();
                this.netType_ = stringUtf8;
                return stringUtf8;
            }

            @Override // com.kingsmith.plugins.Protos.PDeviceOrBuilder
            public ByteString getNetTypeBytes() {
                Object obj = this.netType_;
                if (!(obj instanceof String)) {
                    return (ByteString) obj;
                }
                ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                this.netType_ = byteStringCopyFromUtf8;
                return byteStringCopyFromUtf8;
            }

            @Override // com.kingsmith.plugins.Protos.PDeviceOrBuilder
            public int getOwned() {
                return this.owned_;
            }

            @Override // com.kingsmith.plugins.Protos.PDeviceOrBuilder
            public int getStatus() {
                return this.status_;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                return true;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder
            protected GeneratedMessageV3.FieldAccessorTable j() {
                return Protos.internal_static_PDevice_fieldAccessorTable.ensureFieldAccessorsInitialized(PDevice.class, Builder.class);
            }

            public Builder setIotId(String str) {
                str.getClass();
                this.iotId_ = str;
                p();
                return this;
            }

            public Builder setIotIdBytes(ByteString byteString) throws IllegalArgumentException {
                byteString.getClass();
                AbstractMessageLite.checkByteStringIsUtf8(byteString);
                this.iotId_ = byteString;
                p();
                return this;
            }

            public Builder setModel(String str) {
                str.getClass();
                this.model_ = str;
                p();
                return this;
            }

            public Builder setModelBytes(ByteString byteString) throws IllegalArgumentException {
                byteString.getClass();
                AbstractMessageLite.checkByteStringIsUtf8(byteString);
                this.model_ = byteString;
                p();
                return this;
            }

            public Builder setName(String str) {
                str.getClass();
                this.name_ = str;
                p();
                return this;
            }

            public Builder setNameBytes(ByteString byteString) throws IllegalArgumentException {
                byteString.getClass();
                AbstractMessageLite.checkByteStringIsUtf8(byteString);
                this.name_ = byteString;
                p();
                return this;
            }

            public Builder setNetType(String str) {
                str.getClass();
                this.netType_ = str;
                p();
                return this;
            }

            public Builder setNetTypeBytes(ByteString byteString) throws IllegalArgumentException {
                byteString.getClass();
                AbstractMessageLite.checkByteStringIsUtf8(byteString);
                this.netType_ = byteString;
                p();
                return this;
            }

            public Builder setOwned(int i2) {
                this.owned_ = i2;
                p();
                return this;
            }

            public Builder setStatus(int i2) {
                this.status_ = i2;
                p();
                return this;
            }

            private Builder() {
                this.name_ = "";
                this.model_ = "";
                this.netType_ = "";
                this.iotId_ = "";
                maybeForceBuilderInitialization();
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public Builder addRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, Object obj) {
                return (Builder) super.addRepeatedField(fieldDescriptor, obj);
            }

            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
            public PDevice build() {
                PDevice pDeviceBuildPartial = buildPartial();
                if (pDeviceBuildPartial.isInitialized()) {
                    return pDeviceBuildPartial;
                }
                throw AbstractMessage.Builder.f(pDeviceBuildPartial);
            }

            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
            public PDevice buildPartial() {
                PDevice pDevice = new PDevice(this);
                pDevice.name_ = this.name_;
                pDevice.model_ = this.model_;
                pDevice.netType_ = this.netType_;
                pDevice.status_ = this.status_;
                pDevice.owned_ = this.owned_;
                pDevice.iotId_ = this.iotId_;
                o();
                return pDevice;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public Builder clearField(Descriptors.FieldDescriptor fieldDescriptor) {
                return (Builder) super.clearField(fieldDescriptor);
            }

            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLiteOrBuilder, com.google.protobuf.MessageOrBuilder
            public PDevice getDefaultInstanceForType() {
                return PDevice.getDefaultInstance();
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public Builder setField(Descriptors.FieldDescriptor fieldDescriptor, Object obj) {
                return (Builder) super.setField(fieldDescriptor, obj);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public Builder setRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, int i2, Object obj) {
                return (Builder) super.setRepeatedField(fieldDescriptor, i2, obj);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public final Builder setUnknownFields(UnknownFieldSet unknownFieldSet) {
                return (Builder) super.setUnknownFields(unknownFieldSet);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.Message.Builder
            public Builder clearOneof(Descriptors.OneofDescriptor oneofDescriptor) {
                return (Builder) super.clearOneof(oneofDescriptor);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.Message.Builder
            public final Builder mergeUnknownFields(UnknownFieldSet unknownFieldSet) {
                return (Builder) super.mergeUnknownFields(unknownFieldSet);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
            public Builder clear() {
                super.clear();
                this.name_ = "";
                this.model_ = "";
                this.netType_ = "";
                this.status_ = 0;
                this.owned_ = 0;
                this.iotId_ = "";
                return this;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.AbstractMessageLite.Builder
            /* renamed from: clone */
            public Builder mo65clone() {
                return (Builder) super.mo65clone();
            }

            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.Message.Builder
            public Builder mergeFrom(Message message) {
                if (message instanceof PDevice) {
                    return mergeFrom((PDevice) message);
                }
                super.mergeFrom(message);
                return this;
            }

            private Builder(GeneratedMessageV3.BuilderParent builderParent) {
                super(builderParent);
                this.name_ = "";
                this.model_ = "";
                this.netType_ = "";
                this.iotId_ = "";
                maybeForceBuilderInitialization();
            }

            public Builder mergeFrom(PDevice pDevice) {
                if (pDevice == PDevice.getDefaultInstance()) {
                    return this;
                }
                if (!pDevice.getName().isEmpty()) {
                    this.name_ = pDevice.name_;
                    p();
                }
                if (!pDevice.getModel().isEmpty()) {
                    this.model_ = pDevice.model_;
                    p();
                }
                if (!pDevice.getNetType().isEmpty()) {
                    this.netType_ = pDevice.netType_;
                    p();
                }
                if (pDevice.getStatus() != 0) {
                    setStatus(pDevice.getStatus());
                }
                if (pDevice.getOwned() != 0) {
                    setOwned(pDevice.getOwned());
                }
                if (!pDevice.getIotId().isEmpty()) {
                    this.iotId_ = pDevice.iotId_;
                    p();
                }
                mergeUnknownFields(((GeneratedMessageV3) pDevice).unknownFields);
                p();
                return this;
            }

            /* JADX WARN: Removed duplicated region for block: B:16:0x0023  */
            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.AbstractMessageLite.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public com.kingsmith.plugins.Protos.PDevice.Builder mergeFrom(com.google.protobuf.CodedInputStream r3, com.google.protobuf.ExtensionRegistryLite r4) throws java.lang.Throwable {
                /*
                    r2 = this;
                    r0 = 0
                    com.google.protobuf.Parser r1 = com.kingsmith.plugins.Protos.PDevice.k()     // Catch: java.lang.Throwable -> L11 com.google.protobuf.InvalidProtocolBufferException -> L13
                    java.lang.Object r3 = r1.parsePartialFrom(r3, r4)     // Catch: java.lang.Throwable -> L11 com.google.protobuf.InvalidProtocolBufferException -> L13
                    com.kingsmith.plugins.Protos$PDevice r3 = (com.kingsmith.plugins.Protos.PDevice) r3     // Catch: java.lang.Throwable -> L11 com.google.protobuf.InvalidProtocolBufferException -> L13
                    if (r3 == 0) goto L10
                    r2.mergeFrom(r3)
                L10:
                    return r2
                L11:
                    r3 = move-exception
                    goto L21
                L13:
                    r3 = move-exception
                    com.google.protobuf.MessageLite r4 = r3.getUnfinishedMessage()     // Catch: java.lang.Throwable -> L11
                    com.kingsmith.plugins.Protos$PDevice r4 = (com.kingsmith.plugins.Protos.PDevice) r4     // Catch: java.lang.Throwable -> L11
                    java.io.IOException r3 = r3.unwrapIOException()     // Catch: java.lang.Throwable -> L1f
                    throw r3     // Catch: java.lang.Throwable -> L1f
                L1f:
                    r3 = move-exception
                    r0 = r4
                L21:
                    if (r0 == 0) goto L26
                    r2.mergeFrom(r0)
                L26:
                    throw r3
                */
                throw new UnsupportedOperationException("Method not decompiled: com.kingsmith.plugins.Protos.PDevice.Builder.mergeFrom(com.google.protobuf.CodedInputStream, com.google.protobuf.ExtensionRegistryLite):com.kingsmith.plugins.Protos$PDevice$Builder");
            }
        }

        public static Builder newBuilder(PDevice pDevice) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(pDevice);
        }

        public static PDevice parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteBuffer, extensionRegistryLite);
        }

        private PDevice(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
        }

        public static PDevice parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (PDevice) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, inputStream, extensionRegistryLite);
        }

        public static PDevice parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString);
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLiteOrBuilder, com.google.protobuf.MessageOrBuilder
        public PDevice getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite, com.google.protobuf.Message
        public Builder toBuilder() {
            return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
        }

        public static PDevice parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString, extensionRegistryLite);
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite, com.google.protobuf.Message
        public Builder newBuilderForType() {
            return newBuilder();
        }

        private PDevice() {
            this.memoizedIsInitialized = (byte) -1;
            this.name_ = "";
            this.model_ = "";
            this.netType_ = "";
            this.iotId_ = "";
        }

        public static PDevice parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.protobuf.GeneratedMessageV3
        public Builder newBuilderForType(GeneratedMessageV3.BuilderParent builderParent) {
            return new Builder(builderParent);
        }

        public static PDevice parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr, extensionRegistryLite);
        }

        public static PDevice parseFrom(InputStream inputStream) throws IOException {
            return (PDevice) GeneratedMessageV3.parseWithIOException(PARSER, inputStream);
        }

        public static PDevice parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (PDevice) GeneratedMessageV3.parseWithIOException(PARSER, inputStream, extensionRegistryLite);
        }

        private PDevice(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            this();
            extensionRegistryLite.getClass();
            UnknownFieldSet.Builder builderNewBuilder = UnknownFieldSet.newBuilder();
            boolean z2 = false;
            while (!z2) {
                try {
                    try {
                        int tag = codedInputStream.readTag();
                        if (tag != 0) {
                            if (tag == 10) {
                                this.name_ = codedInputStream.readStringRequireUtf8();
                            } else if (tag == 18) {
                                this.model_ = codedInputStream.readStringRequireUtf8();
                            } else if (tag == 26) {
                                this.netType_ = codedInputStream.readStringRequireUtf8();
                            } else if (tag == 32) {
                                this.status_ = codedInputStream.readInt32();
                            } else if (tag == 40) {
                                this.owned_ = codedInputStream.readInt32();
                            } else if (tag != 50) {
                                if (!parseUnknownField(codedInputStream, builderNewBuilder, extensionRegistryLite, tag)) {
                                }
                            } else {
                                this.iotId_ = codedInputStream.readStringRequireUtf8();
                            }
                        }
                        z2 = true;
                    } catch (InvalidProtocolBufferException e2) {
                        throw e2.setUnfinishedMessage(this);
                    } catch (IOException e3) {
                        throw new InvalidProtocolBufferException(e3).setUnfinishedMessage(this);
                    }
                } catch (Throwable th) {
                    this.unknownFields = builderNewBuilder.build();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            this.unknownFields = builderNewBuilder.build();
            makeExtensionsImmutable();
        }

        public static PDevice parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (PDevice) GeneratedMessageV3.parseWithIOException(PARSER, codedInputStream);
        }

        public static PDevice parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (PDevice) GeneratedMessageV3.parseWithIOException(PARSER, codedInputStream, extensionRegistryLite);
        }
    }

    public interface PDeviceOrBuilder extends MessageOrBuilder {
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

    public static final class PMotion extends GeneratedMessageV3 implements PMotionOrBuilder {
        public static final int BUTTON_ID_FIELD_NUMBER = 7;
        public static final int CAL_FIELD_NUMBER = 5;
        public static final int CHILD_LOCK_FIELD_NUMBER = 10;
        public static final int CODE_FIELD_NUMBER = 14;
        public static final int DIST_FIELD_NUMBER = 2;
        public static final int FLOD_FIELD_NUMBER = 12;
        public static final int MODE_FIELD_NUMBER = 9;
        public static final int MOTION_FINISH_FIELD_NUMBER = 13;
        public static final int POWER_FIELD_NUMBER = 11;
        public static final int SPEED_FIELD_NUMBER = 1;
        public static final int SPM_FIELD_NUMBER = 6;
        public static final int STATE_FIELD_NUMBER = 8;
        public static final int STEP_FIELD_NUMBER = 3;
        public static final int TIME_FIELD_NUMBER = 4;
        private static final long serialVersionUID = 0;
        private int buttonId_;
        private int cal_;
        private volatile Object childLock_;
        private int code_;
        private int dist_;
        private int flod_;
        private byte memoizedIsInitialized;
        private volatile Object mode_;
        private boolean motionFinish_;
        private volatile Object power_;
        private double speed_;
        private int spm_;
        private volatile Object state_;
        private int step_;
        private int time_;
        private static final PMotion DEFAULT_INSTANCE = new PMotion();
        private static final Parser<PMotion> PARSER = new AbstractParser<PMotion>() { // from class: com.kingsmith.plugins.Protos.PMotion.1
            @Override // com.google.protobuf.Parser
            public PMotion parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new PMotion(codedInputStream, extensionRegistryLite);
            }
        };

        public static PMotion getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return Protos.internal_static_PMotion_descriptor;
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static PMotion parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (PMotion) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, inputStream);
        }

        public static PMotion parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteBuffer);
        }

        public static Parser<PMotion> parser() {
            return PARSER;
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.Message
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof PMotion)) {
                return super.equals(obj);
            }
            PMotion pMotion = (PMotion) obj;
            return Double.doubleToLongBits(getSpeed()) == Double.doubleToLongBits(pMotion.getSpeed()) && getDist() == pMotion.getDist() && getStep() == pMotion.getStep() && getTime() == pMotion.getTime() && getCal() == pMotion.getCal() && getSpm() == pMotion.getSpm() && getButtonId() == pMotion.getButtonId() && getState().equals(pMotion.getState()) && getMode().equals(pMotion.getMode()) && getChildLock().equals(pMotion.getChildLock()) && getPower().equals(pMotion.getPower()) && getFlod() == pMotion.getFlod() && getMotionFinish() == pMotion.getMotionFinish() && getCode() == pMotion.getCode() && this.unknownFields.equals(pMotion.unknownFields);
        }

        @Override // com.kingsmith.plugins.Protos.PMotionOrBuilder
        public int getButtonId() {
            return this.buttonId_;
        }

        @Override // com.kingsmith.plugins.Protos.PMotionOrBuilder
        public int getCal() {
            return this.cal_;
        }

        @Override // com.kingsmith.plugins.Protos.PMotionOrBuilder
        public String getChildLock() {
            Object obj = this.childLock_;
            if (obj instanceof String) {
                return (String) obj;
            }
            String stringUtf8 = ((ByteString) obj).toStringUtf8();
            this.childLock_ = stringUtf8;
            return stringUtf8;
        }

        @Override // com.kingsmith.plugins.Protos.PMotionOrBuilder
        public ByteString getChildLockBytes() {
            Object obj = this.childLock_;
            if (!(obj instanceof String)) {
                return (ByteString) obj;
            }
            ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
            this.childLock_ = byteStringCopyFromUtf8;
            return byteStringCopyFromUtf8;
        }

        @Override // com.kingsmith.plugins.Protos.PMotionOrBuilder
        public int getCode() {
            return this.code_;
        }

        @Override // com.kingsmith.plugins.Protos.PMotionOrBuilder
        public int getDist() {
            return this.dist_;
        }

        @Override // com.kingsmith.plugins.Protos.PMotionOrBuilder
        public int getFlod() {
            return this.flod_;
        }

        @Override // com.kingsmith.plugins.Protos.PMotionOrBuilder
        public String getMode() {
            Object obj = this.mode_;
            if (obj instanceof String) {
                return (String) obj;
            }
            String stringUtf8 = ((ByteString) obj).toStringUtf8();
            this.mode_ = stringUtf8;
            return stringUtf8;
        }

        @Override // com.kingsmith.plugins.Protos.PMotionOrBuilder
        public ByteString getModeBytes() {
            Object obj = this.mode_;
            if (!(obj instanceof String)) {
                return (ByteString) obj;
            }
            ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
            this.mode_ = byteStringCopyFromUtf8;
            return byteStringCopyFromUtf8;
        }

        @Override // com.kingsmith.plugins.Protos.PMotionOrBuilder
        public boolean getMotionFinish() {
            return this.motionFinish_;
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.MessageLite, com.google.protobuf.Message
        public Parser<PMotion> getParserForType() {
            return PARSER;
        }

        @Override // com.kingsmith.plugins.Protos.PMotionOrBuilder
        public String getPower() {
            Object obj = this.power_;
            if (obj instanceof String) {
                return (String) obj;
            }
            String stringUtf8 = ((ByteString) obj).toStringUtf8();
            this.power_ = stringUtf8;
            return stringUtf8;
        }

        @Override // com.kingsmith.plugins.Protos.PMotionOrBuilder
        public ByteString getPowerBytes() {
            Object obj = this.power_;
            if (!(obj instanceof String)) {
                return (ByteString) obj;
            }
            ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
            this.power_ = byteStringCopyFromUtf8;
            return byteStringCopyFromUtf8;
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite
        public int getSerializedSize() {
            int i2 = this.memoizedSize;
            if (i2 != -1) {
                return i2;
            }
            double d2 = this.speed_;
            int iComputeDoubleSize = d2 != 0.0d ? CodedOutputStream.computeDoubleSize(1, d2) : 0;
            int i3 = this.dist_;
            if (i3 != 0) {
                iComputeDoubleSize += CodedOutputStream.computeInt32Size(2, i3);
            }
            int i4 = this.step_;
            if (i4 != 0) {
                iComputeDoubleSize += CodedOutputStream.computeInt32Size(3, i4);
            }
            int i5 = this.time_;
            if (i5 != 0) {
                iComputeDoubleSize += CodedOutputStream.computeInt32Size(4, i5);
            }
            int i6 = this.cal_;
            if (i6 != 0) {
                iComputeDoubleSize += CodedOutputStream.computeInt32Size(5, i6);
            }
            int i7 = this.spm_;
            if (i7 != 0) {
                iComputeDoubleSize += CodedOutputStream.computeInt32Size(6, i7);
            }
            int i8 = this.buttonId_;
            if (i8 != 0) {
                iComputeDoubleSize += CodedOutputStream.computeInt32Size(7, i8);
            }
            if (!getStateBytes().isEmpty()) {
                iComputeDoubleSize += GeneratedMessageV3.computeStringSize(8, this.state_);
            }
            if (!getModeBytes().isEmpty()) {
                iComputeDoubleSize += GeneratedMessageV3.computeStringSize(9, this.mode_);
            }
            if (!getChildLockBytes().isEmpty()) {
                iComputeDoubleSize += GeneratedMessageV3.computeStringSize(10, this.childLock_);
            }
            if (!getPowerBytes().isEmpty()) {
                iComputeDoubleSize += GeneratedMessageV3.computeStringSize(11, this.power_);
            }
            int i9 = this.flod_;
            if (i9 != 0) {
                iComputeDoubleSize += CodedOutputStream.computeInt32Size(12, i9);
            }
            boolean z2 = this.motionFinish_;
            if (z2) {
                iComputeDoubleSize += CodedOutputStream.computeBoolSize(13, z2);
            }
            int i10 = this.code_;
            if (i10 != 0) {
                iComputeDoubleSize += CodedOutputStream.computeInt32Size(14, i10);
            }
            int serializedSize = iComputeDoubleSize + this.unknownFields.getSerializedSize();
            this.memoizedSize = serializedSize;
            return serializedSize;
        }

        @Override // com.kingsmith.plugins.Protos.PMotionOrBuilder
        public double getSpeed() {
            return this.speed_;
        }

        @Override // com.kingsmith.plugins.Protos.PMotionOrBuilder
        public int getSpm() {
            return this.spm_;
        }

        @Override // com.kingsmith.plugins.Protos.PMotionOrBuilder
        public String getState() {
            Object obj = this.state_;
            if (obj instanceof String) {
                return (String) obj;
            }
            String stringUtf8 = ((ByteString) obj).toStringUtf8();
            this.state_ = stringUtf8;
            return stringUtf8;
        }

        @Override // com.kingsmith.plugins.Protos.PMotionOrBuilder
        public ByteString getStateBytes() {
            Object obj = this.state_;
            if (!(obj instanceof String)) {
                return (ByteString) obj;
            }
            ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
            this.state_ = byteStringCopyFromUtf8;
            return byteStringCopyFromUtf8;
        }

        @Override // com.kingsmith.plugins.Protos.PMotionOrBuilder
        public int getStep() {
            return this.step_;
        }

        @Override // com.kingsmith.plugins.Protos.PMotionOrBuilder
        public int getTime() {
            return this.time_;
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.MessageOrBuilder
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.Message
        public int hashCode() {
            int i2 = this.memoizedHashCode;
            if (i2 != 0) {
                return i2;
            }
            int iHashCode = ((((((((((((((((((((((((((((((((((((((((((((((((((((((((((779 + getDescriptor().hashCode()) * 37) + 1) * 53) + Internal.hashLong(Double.doubleToLongBits(getSpeed()))) * 37) + 2) * 53) + getDist()) * 37) + 3) * 53) + getStep()) * 37) + 4) * 53) + getTime()) * 37) + 5) * 53) + getCal()) * 37) + 6) * 53) + getSpm()) * 37) + 7) * 53) + getButtonId()) * 37) + 8) * 53) + getState().hashCode()) * 37) + 9) * 53) + getMode().hashCode()) * 37) + 10) * 53) + getChildLock().hashCode()) * 37) + 11) * 53) + getPower().hashCode()) * 37) + 12) * 53) + getFlod()) * 37) + 13) * 53) + Internal.hashBoolean(getMotionFinish())) * 37) + 14) * 53) + getCode()) * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = iHashCode;
            return iHashCode;
        }

        @Override // com.google.protobuf.GeneratedMessageV3
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return Protos.internal_static_PMotion_fieldAccessorTable.ensureFieldAccessorsInitialized(PMotion.class, Builder.class);
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            byte b2 = this.memoizedIsInitialized;
            if (b2 == 1) {
                return true;
            }
            if (b2 == 0) {
                return false;
            }
            this.memoizedIsInitialized = (byte) 1;
            return true;
        }

        @Override // com.google.protobuf.GeneratedMessageV3
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unusedPrivateParameter) {
            return new PMotion();
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            double d2 = this.speed_;
            if (d2 != 0.0d) {
                codedOutputStream.writeDouble(1, d2);
            }
            int i2 = this.dist_;
            if (i2 != 0) {
                codedOutputStream.writeInt32(2, i2);
            }
            int i3 = this.step_;
            if (i3 != 0) {
                codedOutputStream.writeInt32(3, i3);
            }
            int i4 = this.time_;
            if (i4 != 0) {
                codedOutputStream.writeInt32(4, i4);
            }
            int i5 = this.cal_;
            if (i5 != 0) {
                codedOutputStream.writeInt32(5, i5);
            }
            int i6 = this.spm_;
            if (i6 != 0) {
                codedOutputStream.writeInt32(6, i6);
            }
            int i7 = this.buttonId_;
            if (i7 != 0) {
                codedOutputStream.writeInt32(7, i7);
            }
            if (!getStateBytes().isEmpty()) {
                GeneratedMessageV3.writeString(codedOutputStream, 8, this.state_);
            }
            if (!getModeBytes().isEmpty()) {
                GeneratedMessageV3.writeString(codedOutputStream, 9, this.mode_);
            }
            if (!getChildLockBytes().isEmpty()) {
                GeneratedMessageV3.writeString(codedOutputStream, 10, this.childLock_);
            }
            if (!getPowerBytes().isEmpty()) {
                GeneratedMessageV3.writeString(codedOutputStream, 11, this.power_);
            }
            int i8 = this.flod_;
            if (i8 != 0) {
                codedOutputStream.writeInt32(12, i8);
            }
            boolean z2 = this.motionFinish_;
            if (z2) {
                codedOutputStream.writeBool(13, z2);
            }
            int i9 = this.code_;
            if (i9 != 0) {
                codedOutputStream.writeInt32(14, i9);
            }
            this.unknownFields.writeTo(codedOutputStream);
        }

        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements PMotionOrBuilder {
            private int buttonId_;
            private int cal_;
            private Object childLock_;
            private int code_;
            private int dist_;
            private int flod_;
            private Object mode_;
            private boolean motionFinish_;
            private Object power_;
            private double speed_;
            private int spm_;
            private Object state_;
            private int step_;
            private int time_;

            public static final Descriptors.Descriptor getDescriptor() {
                return Protos.internal_static_PMotion_descriptor;
            }

            private void maybeForceBuilderInitialization() {
                boolean unused = GeneratedMessageV3.alwaysUseFieldBuilders;
            }

            public Builder clearButtonId() {
                this.buttonId_ = 0;
                p();
                return this;
            }

            public Builder clearCal() {
                this.cal_ = 0;
                p();
                return this;
            }

            public Builder clearChildLock() {
                this.childLock_ = PMotion.getDefaultInstance().getChildLock();
                p();
                return this;
            }

            public Builder clearCode() {
                this.code_ = 0;
                p();
                return this;
            }

            public Builder clearDist() {
                this.dist_ = 0;
                p();
                return this;
            }

            public Builder clearFlod() {
                this.flod_ = 0;
                p();
                return this;
            }

            public Builder clearMode() {
                this.mode_ = PMotion.getDefaultInstance().getMode();
                p();
                return this;
            }

            public Builder clearMotionFinish() {
                this.motionFinish_ = false;
                p();
                return this;
            }

            public Builder clearPower() {
                this.power_ = PMotion.getDefaultInstance().getPower();
                p();
                return this;
            }

            public Builder clearSpeed() {
                this.speed_ = 0.0d;
                p();
                return this;
            }

            public Builder clearSpm() {
                this.spm_ = 0;
                p();
                return this;
            }

            public Builder clearState() {
                this.state_ = PMotion.getDefaultInstance().getState();
                p();
                return this;
            }

            public Builder clearStep() {
                this.step_ = 0;
                p();
                return this;
            }

            public Builder clearTime() {
                this.time_ = 0;
                p();
                return this;
            }

            @Override // com.kingsmith.plugins.Protos.PMotionOrBuilder
            public int getButtonId() {
                return this.buttonId_;
            }

            @Override // com.kingsmith.plugins.Protos.PMotionOrBuilder
            public int getCal() {
                return this.cal_;
            }

            @Override // com.kingsmith.plugins.Protos.PMotionOrBuilder
            public String getChildLock() {
                Object obj = this.childLock_;
                if (obj instanceof String) {
                    return (String) obj;
                }
                String stringUtf8 = ((ByteString) obj).toStringUtf8();
                this.childLock_ = stringUtf8;
                return stringUtf8;
            }

            @Override // com.kingsmith.plugins.Protos.PMotionOrBuilder
            public ByteString getChildLockBytes() {
                Object obj = this.childLock_;
                if (!(obj instanceof String)) {
                    return (ByteString) obj;
                }
                ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                this.childLock_ = byteStringCopyFromUtf8;
                return byteStringCopyFromUtf8;
            }

            @Override // com.kingsmith.plugins.Protos.PMotionOrBuilder
            public int getCode() {
                return this.code_;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder, com.google.protobuf.MessageOrBuilder
            public Descriptors.Descriptor getDescriptorForType() {
                return Protos.internal_static_PMotion_descriptor;
            }

            @Override // com.kingsmith.plugins.Protos.PMotionOrBuilder
            public int getDist() {
                return this.dist_;
            }

            @Override // com.kingsmith.plugins.Protos.PMotionOrBuilder
            public int getFlod() {
                return this.flod_;
            }

            @Override // com.kingsmith.plugins.Protos.PMotionOrBuilder
            public String getMode() {
                Object obj = this.mode_;
                if (obj instanceof String) {
                    return (String) obj;
                }
                String stringUtf8 = ((ByteString) obj).toStringUtf8();
                this.mode_ = stringUtf8;
                return stringUtf8;
            }

            @Override // com.kingsmith.plugins.Protos.PMotionOrBuilder
            public ByteString getModeBytes() {
                Object obj = this.mode_;
                if (!(obj instanceof String)) {
                    return (ByteString) obj;
                }
                ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                this.mode_ = byteStringCopyFromUtf8;
                return byteStringCopyFromUtf8;
            }

            @Override // com.kingsmith.plugins.Protos.PMotionOrBuilder
            public boolean getMotionFinish() {
                return this.motionFinish_;
            }

            @Override // com.kingsmith.plugins.Protos.PMotionOrBuilder
            public String getPower() {
                Object obj = this.power_;
                if (obj instanceof String) {
                    return (String) obj;
                }
                String stringUtf8 = ((ByteString) obj).toStringUtf8();
                this.power_ = stringUtf8;
                return stringUtf8;
            }

            @Override // com.kingsmith.plugins.Protos.PMotionOrBuilder
            public ByteString getPowerBytes() {
                Object obj = this.power_;
                if (!(obj instanceof String)) {
                    return (ByteString) obj;
                }
                ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                this.power_ = byteStringCopyFromUtf8;
                return byteStringCopyFromUtf8;
            }

            @Override // com.kingsmith.plugins.Protos.PMotionOrBuilder
            public double getSpeed() {
                return this.speed_;
            }

            @Override // com.kingsmith.plugins.Protos.PMotionOrBuilder
            public int getSpm() {
                return this.spm_;
            }

            @Override // com.kingsmith.plugins.Protos.PMotionOrBuilder
            public String getState() {
                Object obj = this.state_;
                if (obj instanceof String) {
                    return (String) obj;
                }
                String stringUtf8 = ((ByteString) obj).toStringUtf8();
                this.state_ = stringUtf8;
                return stringUtf8;
            }

            @Override // com.kingsmith.plugins.Protos.PMotionOrBuilder
            public ByteString getStateBytes() {
                Object obj = this.state_;
                if (!(obj instanceof String)) {
                    return (ByteString) obj;
                }
                ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                this.state_ = byteStringCopyFromUtf8;
                return byteStringCopyFromUtf8;
            }

            @Override // com.kingsmith.plugins.Protos.PMotionOrBuilder
            public int getStep() {
                return this.step_;
            }

            @Override // com.kingsmith.plugins.Protos.PMotionOrBuilder
            public int getTime() {
                return this.time_;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                return true;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder
            protected GeneratedMessageV3.FieldAccessorTable j() {
                return Protos.internal_static_PMotion_fieldAccessorTable.ensureFieldAccessorsInitialized(PMotion.class, Builder.class);
            }

            public Builder setButtonId(int i2) {
                this.buttonId_ = i2;
                p();
                return this;
            }

            public Builder setCal(int i2) {
                this.cal_ = i2;
                p();
                return this;
            }

            public Builder setChildLock(String str) {
                str.getClass();
                this.childLock_ = str;
                p();
                return this;
            }

            public Builder setChildLockBytes(ByteString byteString) throws IllegalArgumentException {
                byteString.getClass();
                AbstractMessageLite.checkByteStringIsUtf8(byteString);
                this.childLock_ = byteString;
                p();
                return this;
            }

            public Builder setCode(int i2) {
                this.code_ = i2;
                p();
                return this;
            }

            public Builder setDist(int i2) {
                this.dist_ = i2;
                p();
                return this;
            }

            public Builder setFlod(int i2) {
                this.flod_ = i2;
                p();
                return this;
            }

            public Builder setMode(String str) {
                str.getClass();
                this.mode_ = str;
                p();
                return this;
            }

            public Builder setModeBytes(ByteString byteString) throws IllegalArgumentException {
                byteString.getClass();
                AbstractMessageLite.checkByteStringIsUtf8(byteString);
                this.mode_ = byteString;
                p();
                return this;
            }

            public Builder setMotionFinish(boolean z2) {
                this.motionFinish_ = z2;
                p();
                return this;
            }

            public Builder setPower(String str) {
                str.getClass();
                this.power_ = str;
                p();
                return this;
            }

            public Builder setPowerBytes(ByteString byteString) throws IllegalArgumentException {
                byteString.getClass();
                AbstractMessageLite.checkByteStringIsUtf8(byteString);
                this.power_ = byteString;
                p();
                return this;
            }

            public Builder setSpeed(double d2) {
                this.speed_ = d2;
                p();
                return this;
            }

            public Builder setSpm(int i2) {
                this.spm_ = i2;
                p();
                return this;
            }

            public Builder setState(String str) {
                str.getClass();
                this.state_ = str;
                p();
                return this;
            }

            public Builder setStateBytes(ByteString byteString) throws IllegalArgumentException {
                byteString.getClass();
                AbstractMessageLite.checkByteStringIsUtf8(byteString);
                this.state_ = byteString;
                p();
                return this;
            }

            public Builder setStep(int i2) {
                this.step_ = i2;
                p();
                return this;
            }

            public Builder setTime(int i2) {
                this.time_ = i2;
                p();
                return this;
            }

            private Builder() {
                this.state_ = "";
                this.mode_ = "";
                this.childLock_ = "";
                this.power_ = "";
                maybeForceBuilderInitialization();
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public Builder addRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, Object obj) {
                return (Builder) super.addRepeatedField(fieldDescriptor, obj);
            }

            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
            public PMotion build() {
                PMotion pMotionBuildPartial = buildPartial();
                if (pMotionBuildPartial.isInitialized()) {
                    return pMotionBuildPartial;
                }
                throw AbstractMessage.Builder.f(pMotionBuildPartial);
            }

            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
            public PMotion buildPartial() {
                PMotion pMotion = new PMotion(this);
                pMotion.speed_ = this.speed_;
                pMotion.dist_ = this.dist_;
                pMotion.step_ = this.step_;
                pMotion.time_ = this.time_;
                pMotion.cal_ = this.cal_;
                pMotion.spm_ = this.spm_;
                pMotion.buttonId_ = this.buttonId_;
                pMotion.state_ = this.state_;
                pMotion.mode_ = this.mode_;
                pMotion.childLock_ = this.childLock_;
                pMotion.power_ = this.power_;
                pMotion.flod_ = this.flod_;
                pMotion.motionFinish_ = this.motionFinish_;
                pMotion.code_ = this.code_;
                o();
                return pMotion;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public Builder clearField(Descriptors.FieldDescriptor fieldDescriptor) {
                return (Builder) super.clearField(fieldDescriptor);
            }

            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLiteOrBuilder, com.google.protobuf.MessageOrBuilder
            public PMotion getDefaultInstanceForType() {
                return PMotion.getDefaultInstance();
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public Builder setField(Descriptors.FieldDescriptor fieldDescriptor, Object obj) {
                return (Builder) super.setField(fieldDescriptor, obj);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public Builder setRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, int i2, Object obj) {
                return (Builder) super.setRepeatedField(fieldDescriptor, i2, obj);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public final Builder setUnknownFields(UnknownFieldSet unknownFieldSet) {
                return (Builder) super.setUnknownFields(unknownFieldSet);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.Message.Builder
            public Builder clearOneof(Descriptors.OneofDescriptor oneofDescriptor) {
                return (Builder) super.clearOneof(oneofDescriptor);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.Message.Builder
            public final Builder mergeUnknownFields(UnknownFieldSet unknownFieldSet) {
                return (Builder) super.mergeUnknownFields(unknownFieldSet);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
            public Builder clear() {
                super.clear();
                this.speed_ = 0.0d;
                this.dist_ = 0;
                this.step_ = 0;
                this.time_ = 0;
                this.cal_ = 0;
                this.spm_ = 0;
                this.buttonId_ = 0;
                this.state_ = "";
                this.mode_ = "";
                this.childLock_ = "";
                this.power_ = "";
                this.flod_ = 0;
                this.motionFinish_ = false;
                this.code_ = 0;
                return this;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.AbstractMessageLite.Builder
            /* renamed from: clone */
            public Builder mo65clone() {
                return (Builder) super.mo65clone();
            }

            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.Message.Builder
            public Builder mergeFrom(Message message) {
                if (message instanceof PMotion) {
                    return mergeFrom((PMotion) message);
                }
                super.mergeFrom(message);
                return this;
            }

            private Builder(GeneratedMessageV3.BuilderParent builderParent) {
                super(builderParent);
                this.state_ = "";
                this.mode_ = "";
                this.childLock_ = "";
                this.power_ = "";
                maybeForceBuilderInitialization();
            }

            public Builder mergeFrom(PMotion pMotion) {
                if (pMotion == PMotion.getDefaultInstance()) {
                    return this;
                }
                if (pMotion.getSpeed() != 0.0d) {
                    setSpeed(pMotion.getSpeed());
                }
                if (pMotion.getDist() != 0) {
                    setDist(pMotion.getDist());
                }
                if (pMotion.getStep() != 0) {
                    setStep(pMotion.getStep());
                }
                if (pMotion.getTime() != 0) {
                    setTime(pMotion.getTime());
                }
                if (pMotion.getCal() != 0) {
                    setCal(pMotion.getCal());
                }
                if (pMotion.getSpm() != 0) {
                    setSpm(pMotion.getSpm());
                }
                if (pMotion.getButtonId() != 0) {
                    setButtonId(pMotion.getButtonId());
                }
                if (!pMotion.getState().isEmpty()) {
                    this.state_ = pMotion.state_;
                    p();
                }
                if (!pMotion.getMode().isEmpty()) {
                    this.mode_ = pMotion.mode_;
                    p();
                }
                if (!pMotion.getChildLock().isEmpty()) {
                    this.childLock_ = pMotion.childLock_;
                    p();
                }
                if (!pMotion.getPower().isEmpty()) {
                    this.power_ = pMotion.power_;
                    p();
                }
                if (pMotion.getFlod() != 0) {
                    setFlod(pMotion.getFlod());
                }
                if (pMotion.getMotionFinish()) {
                    setMotionFinish(pMotion.getMotionFinish());
                }
                if (pMotion.getCode() != 0) {
                    setCode(pMotion.getCode());
                }
                mergeUnknownFields(((GeneratedMessageV3) pMotion).unknownFields);
                p();
                return this;
            }

            /* JADX WARN: Removed duplicated region for block: B:16:0x0023  */
            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.AbstractMessageLite.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public com.kingsmith.plugins.Protos.PMotion.Builder mergeFrom(com.google.protobuf.CodedInputStream r3, com.google.protobuf.ExtensionRegistryLite r4) throws java.lang.Throwable {
                /*
                    r2 = this;
                    r0 = 0
                    com.google.protobuf.Parser r1 = com.kingsmith.plugins.Protos.PMotion.s()     // Catch: java.lang.Throwable -> L11 com.google.protobuf.InvalidProtocolBufferException -> L13
                    java.lang.Object r3 = r1.parsePartialFrom(r3, r4)     // Catch: java.lang.Throwable -> L11 com.google.protobuf.InvalidProtocolBufferException -> L13
                    com.kingsmith.plugins.Protos$PMotion r3 = (com.kingsmith.plugins.Protos.PMotion) r3     // Catch: java.lang.Throwable -> L11 com.google.protobuf.InvalidProtocolBufferException -> L13
                    if (r3 == 0) goto L10
                    r2.mergeFrom(r3)
                L10:
                    return r2
                L11:
                    r3 = move-exception
                    goto L21
                L13:
                    r3 = move-exception
                    com.google.protobuf.MessageLite r4 = r3.getUnfinishedMessage()     // Catch: java.lang.Throwable -> L11
                    com.kingsmith.plugins.Protos$PMotion r4 = (com.kingsmith.plugins.Protos.PMotion) r4     // Catch: java.lang.Throwable -> L11
                    java.io.IOException r3 = r3.unwrapIOException()     // Catch: java.lang.Throwable -> L1f
                    throw r3     // Catch: java.lang.Throwable -> L1f
                L1f:
                    r3 = move-exception
                    r0 = r4
                L21:
                    if (r0 == 0) goto L26
                    r2.mergeFrom(r0)
                L26:
                    throw r3
                */
                throw new UnsupportedOperationException("Method not decompiled: com.kingsmith.plugins.Protos.PMotion.Builder.mergeFrom(com.google.protobuf.CodedInputStream, com.google.protobuf.ExtensionRegistryLite):com.kingsmith.plugins.Protos$PMotion$Builder");
            }
        }

        public static Builder newBuilder(PMotion pMotion) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(pMotion);
        }

        public static PMotion parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteBuffer, extensionRegistryLite);
        }

        private PMotion(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
        }

        public static PMotion parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (PMotion) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, inputStream, extensionRegistryLite);
        }

        public static PMotion parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString);
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLiteOrBuilder, com.google.protobuf.MessageOrBuilder
        public PMotion getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite, com.google.protobuf.Message
        public Builder toBuilder() {
            return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
        }

        public static PMotion parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString, extensionRegistryLite);
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite, com.google.protobuf.Message
        public Builder newBuilderForType() {
            return newBuilder();
        }

        private PMotion() {
            this.memoizedIsInitialized = (byte) -1;
            this.state_ = "";
            this.mode_ = "";
            this.childLock_ = "";
            this.power_ = "";
        }

        public static PMotion parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.protobuf.GeneratedMessageV3
        public Builder newBuilderForType(GeneratedMessageV3.BuilderParent builderParent) {
            return new Builder(builderParent);
        }

        public static PMotion parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr, extensionRegistryLite);
        }

        public static PMotion parseFrom(InputStream inputStream) throws IOException {
            return (PMotion) GeneratedMessageV3.parseWithIOException(PARSER, inputStream);
        }

        public static PMotion parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (PMotion) GeneratedMessageV3.parseWithIOException(PARSER, inputStream, extensionRegistryLite);
        }

        private PMotion(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            this();
            extensionRegistryLite.getClass();
            UnknownFieldSet.Builder builderNewBuilder = UnknownFieldSet.newBuilder();
            boolean z2 = false;
            while (!z2) {
                try {
                    try {
                        int tag = codedInputStream.readTag();
                        switch (tag) {
                            case 0:
                                z2 = true;
                            case 9:
                                this.speed_ = codedInputStream.readDouble();
                            case 16:
                                this.dist_ = codedInputStream.readInt32();
                            case 24:
                                this.step_ = codedInputStream.readInt32();
                            case 32:
                                this.time_ = codedInputStream.readInt32();
                            case 40:
                                this.cal_ = codedInputStream.readInt32();
                            case 48:
                                this.spm_ = codedInputStream.readInt32();
                            case 56:
                                this.buttonId_ = codedInputStream.readInt32();
                            case 66:
                                this.state_ = codedInputStream.readStringRequireUtf8();
                            case 74:
                                this.mode_ = codedInputStream.readStringRequireUtf8();
                            case 82:
                                this.childLock_ = codedInputStream.readStringRequireUtf8();
                            case 90:
                                this.power_ = codedInputStream.readStringRequireUtf8();
                            case 96:
                                this.flod_ = codedInputStream.readInt32();
                            case 104:
                                this.motionFinish_ = codedInputStream.readBool();
                            case 112:
                                this.code_ = codedInputStream.readInt32();
                            default:
                                if (!parseUnknownField(codedInputStream, builderNewBuilder, extensionRegistryLite, tag)) {
                                    z2 = true;
                                }
                        }
                    } catch (InvalidProtocolBufferException e2) {
                        throw e2.setUnfinishedMessage(this);
                    } catch (IOException e3) {
                        throw new InvalidProtocolBufferException(e3).setUnfinishedMessage(this);
                    }
                } catch (Throwable th) {
                    this.unknownFields = builderNewBuilder.build();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            this.unknownFields = builderNewBuilder.build();
            makeExtensionsImmutable();
        }

        public static PMotion parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (PMotion) GeneratedMessageV3.parseWithIOException(PARSER, codedInputStream);
        }

        public static PMotion parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (PMotion) GeneratedMessageV3.parseWithIOException(PARSER, codedInputStream, extensionRegistryLite);
        }
    }

    public interface PMotionOrBuilder extends MessageOrBuilder {
        int getButtonId();

        int getCal();

        String getChildLock();

        ByteString getChildLockBytes();

        int getCode();

        int getDist();

        int getFlod();

        String getMode();

        ByteString getModeBytes();

        boolean getMotionFinish();

        String getPower();

        ByteString getPowerBytes();

        double getSpeed();

        int getSpm();

        String getState();

        ByteString getStateBytes();

        int getStep();

        int getTime();
    }

    public static final class POTAFirmware extends GeneratedMessageV3 implements POTAFirmwareOrBuilder {
        public static final int CURRENT_VERSION_FIELD_NUMBER = 1;
        public static final int DESC_FIELD_NUMBER = 3;
        public static final int NEW_VERSION_FIELD_NUMBER = 2;
        private static final long serialVersionUID = 0;
        private volatile Object currentVersion_;
        private volatile Object desc_;
        private byte memoizedIsInitialized;
        private volatile Object newVersion_;
        private static final POTAFirmware DEFAULT_INSTANCE = new POTAFirmware();
        private static final Parser<POTAFirmware> PARSER = new AbstractParser<POTAFirmware>() { // from class: com.kingsmith.plugins.Protos.POTAFirmware.1
            @Override // com.google.protobuf.Parser
            public POTAFirmware parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new POTAFirmware(codedInputStream, extensionRegistryLite);
            }
        };

        public static POTAFirmware getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return Protos.internal_static_POTAFirmware_descriptor;
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static POTAFirmware parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (POTAFirmware) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, inputStream);
        }

        public static POTAFirmware parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteBuffer);
        }

        public static Parser<POTAFirmware> parser() {
            return PARSER;
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.Message
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof POTAFirmware)) {
                return super.equals(obj);
            }
            POTAFirmware pOTAFirmware = (POTAFirmware) obj;
            return getCurrentVersion().equals(pOTAFirmware.getCurrentVersion()) && getNewVersion().equals(pOTAFirmware.getNewVersion()) && getDesc().equals(pOTAFirmware.getDesc()) && this.unknownFields.equals(pOTAFirmware.unknownFields);
        }

        @Override // com.kingsmith.plugins.Protos.POTAFirmwareOrBuilder
        public String getCurrentVersion() {
            Object obj = this.currentVersion_;
            if (obj instanceof String) {
                return (String) obj;
            }
            String stringUtf8 = ((ByteString) obj).toStringUtf8();
            this.currentVersion_ = stringUtf8;
            return stringUtf8;
        }

        @Override // com.kingsmith.plugins.Protos.POTAFirmwareOrBuilder
        public ByteString getCurrentVersionBytes() {
            Object obj = this.currentVersion_;
            if (!(obj instanceof String)) {
                return (ByteString) obj;
            }
            ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
            this.currentVersion_ = byteStringCopyFromUtf8;
            return byteStringCopyFromUtf8;
        }

        @Override // com.kingsmith.plugins.Protos.POTAFirmwareOrBuilder
        public String getDesc() {
            Object obj = this.desc_;
            if (obj instanceof String) {
                return (String) obj;
            }
            String stringUtf8 = ((ByteString) obj).toStringUtf8();
            this.desc_ = stringUtf8;
            return stringUtf8;
        }

        @Override // com.kingsmith.plugins.Protos.POTAFirmwareOrBuilder
        public ByteString getDescBytes() {
            Object obj = this.desc_;
            if (!(obj instanceof String)) {
                return (ByteString) obj;
            }
            ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
            this.desc_ = byteStringCopyFromUtf8;
            return byteStringCopyFromUtf8;
        }

        @Override // com.kingsmith.plugins.Protos.POTAFirmwareOrBuilder
        public String getNewVersion() {
            Object obj = this.newVersion_;
            if (obj instanceof String) {
                return (String) obj;
            }
            String stringUtf8 = ((ByteString) obj).toStringUtf8();
            this.newVersion_ = stringUtf8;
            return stringUtf8;
        }

        @Override // com.kingsmith.plugins.Protos.POTAFirmwareOrBuilder
        public ByteString getNewVersionBytes() {
            Object obj = this.newVersion_;
            if (!(obj instanceof String)) {
                return (ByteString) obj;
            }
            ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
            this.newVersion_ = byteStringCopyFromUtf8;
            return byteStringCopyFromUtf8;
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.MessageLite, com.google.protobuf.Message
        public Parser<POTAFirmware> getParserForType() {
            return PARSER;
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite
        public int getSerializedSize() {
            int i2 = this.memoizedSize;
            if (i2 != -1) {
                return i2;
            }
            int iComputeStringSize = !getCurrentVersionBytes().isEmpty() ? GeneratedMessageV3.computeStringSize(1, this.currentVersion_) : 0;
            if (!getNewVersionBytes().isEmpty()) {
                iComputeStringSize += GeneratedMessageV3.computeStringSize(2, this.newVersion_);
            }
            if (!getDescBytes().isEmpty()) {
                iComputeStringSize += GeneratedMessageV3.computeStringSize(3, this.desc_);
            }
            int serializedSize = iComputeStringSize + this.unknownFields.getSerializedSize();
            this.memoizedSize = serializedSize;
            return serializedSize;
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.MessageOrBuilder
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.Message
        public int hashCode() {
            int i2 = this.memoizedHashCode;
            if (i2 != 0) {
                return i2;
            }
            int iHashCode = ((((((((((((((779 + getDescriptor().hashCode()) * 37) + 1) * 53) + getCurrentVersion().hashCode()) * 37) + 2) * 53) + getNewVersion().hashCode()) * 37) + 3) * 53) + getDesc().hashCode()) * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = iHashCode;
            return iHashCode;
        }

        @Override // com.google.protobuf.GeneratedMessageV3
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return Protos.internal_static_POTAFirmware_fieldAccessorTable.ensureFieldAccessorsInitialized(POTAFirmware.class, Builder.class);
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            byte b2 = this.memoizedIsInitialized;
            if (b2 == 1) {
                return true;
            }
            if (b2 == 0) {
                return false;
            }
            this.memoizedIsInitialized = (byte) 1;
            return true;
        }

        @Override // com.google.protobuf.GeneratedMessageV3
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unusedPrivateParameter) {
            return new POTAFirmware();
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            if (!getCurrentVersionBytes().isEmpty()) {
                GeneratedMessageV3.writeString(codedOutputStream, 1, this.currentVersion_);
            }
            if (!getNewVersionBytes().isEmpty()) {
                GeneratedMessageV3.writeString(codedOutputStream, 2, this.newVersion_);
            }
            if (!getDescBytes().isEmpty()) {
                GeneratedMessageV3.writeString(codedOutputStream, 3, this.desc_);
            }
            this.unknownFields.writeTo(codedOutputStream);
        }

        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements POTAFirmwareOrBuilder {
            private Object currentVersion_;
            private Object desc_;
            private Object newVersion_;

            public static final Descriptors.Descriptor getDescriptor() {
                return Protos.internal_static_POTAFirmware_descriptor;
            }

            private void maybeForceBuilderInitialization() {
                boolean unused = GeneratedMessageV3.alwaysUseFieldBuilders;
            }

            public Builder clearCurrentVersion() {
                this.currentVersion_ = POTAFirmware.getDefaultInstance().getCurrentVersion();
                p();
                return this;
            }

            public Builder clearDesc() {
                this.desc_ = POTAFirmware.getDefaultInstance().getDesc();
                p();
                return this;
            }

            public Builder clearNewVersion() {
                this.newVersion_ = POTAFirmware.getDefaultInstance().getNewVersion();
                p();
                return this;
            }

            @Override // com.kingsmith.plugins.Protos.POTAFirmwareOrBuilder
            public String getCurrentVersion() {
                Object obj = this.currentVersion_;
                if (obj instanceof String) {
                    return (String) obj;
                }
                String stringUtf8 = ((ByteString) obj).toStringUtf8();
                this.currentVersion_ = stringUtf8;
                return stringUtf8;
            }

            @Override // com.kingsmith.plugins.Protos.POTAFirmwareOrBuilder
            public ByteString getCurrentVersionBytes() {
                Object obj = this.currentVersion_;
                if (!(obj instanceof String)) {
                    return (ByteString) obj;
                }
                ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                this.currentVersion_ = byteStringCopyFromUtf8;
                return byteStringCopyFromUtf8;
            }

            @Override // com.kingsmith.plugins.Protos.POTAFirmwareOrBuilder
            public String getDesc() {
                Object obj = this.desc_;
                if (obj instanceof String) {
                    return (String) obj;
                }
                String stringUtf8 = ((ByteString) obj).toStringUtf8();
                this.desc_ = stringUtf8;
                return stringUtf8;
            }

            @Override // com.kingsmith.plugins.Protos.POTAFirmwareOrBuilder
            public ByteString getDescBytes() {
                Object obj = this.desc_;
                if (!(obj instanceof String)) {
                    return (ByteString) obj;
                }
                ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                this.desc_ = byteStringCopyFromUtf8;
                return byteStringCopyFromUtf8;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder, com.google.protobuf.MessageOrBuilder
            public Descriptors.Descriptor getDescriptorForType() {
                return Protos.internal_static_POTAFirmware_descriptor;
            }

            @Override // com.kingsmith.plugins.Protos.POTAFirmwareOrBuilder
            public String getNewVersion() {
                Object obj = this.newVersion_;
                if (obj instanceof String) {
                    return (String) obj;
                }
                String stringUtf8 = ((ByteString) obj).toStringUtf8();
                this.newVersion_ = stringUtf8;
                return stringUtf8;
            }

            @Override // com.kingsmith.plugins.Protos.POTAFirmwareOrBuilder
            public ByteString getNewVersionBytes() {
                Object obj = this.newVersion_;
                if (!(obj instanceof String)) {
                    return (ByteString) obj;
                }
                ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                this.newVersion_ = byteStringCopyFromUtf8;
                return byteStringCopyFromUtf8;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                return true;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder
            protected GeneratedMessageV3.FieldAccessorTable j() {
                return Protos.internal_static_POTAFirmware_fieldAccessorTable.ensureFieldAccessorsInitialized(POTAFirmware.class, Builder.class);
            }

            public Builder setCurrentVersion(String str) {
                str.getClass();
                this.currentVersion_ = str;
                p();
                return this;
            }

            public Builder setCurrentVersionBytes(ByteString byteString) throws IllegalArgumentException {
                byteString.getClass();
                AbstractMessageLite.checkByteStringIsUtf8(byteString);
                this.currentVersion_ = byteString;
                p();
                return this;
            }

            public Builder setDesc(String str) {
                str.getClass();
                this.desc_ = str;
                p();
                return this;
            }

            public Builder setDescBytes(ByteString byteString) throws IllegalArgumentException {
                byteString.getClass();
                AbstractMessageLite.checkByteStringIsUtf8(byteString);
                this.desc_ = byteString;
                p();
                return this;
            }

            public Builder setNewVersion(String str) {
                str.getClass();
                this.newVersion_ = str;
                p();
                return this;
            }

            public Builder setNewVersionBytes(ByteString byteString) throws IllegalArgumentException {
                byteString.getClass();
                AbstractMessageLite.checkByteStringIsUtf8(byteString);
                this.newVersion_ = byteString;
                p();
                return this;
            }

            private Builder() {
                this.currentVersion_ = "";
                this.newVersion_ = "";
                this.desc_ = "";
                maybeForceBuilderInitialization();
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public Builder addRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, Object obj) {
                return (Builder) super.addRepeatedField(fieldDescriptor, obj);
            }

            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
            public POTAFirmware build() {
                POTAFirmware pOTAFirmwareBuildPartial = buildPartial();
                if (pOTAFirmwareBuildPartial.isInitialized()) {
                    return pOTAFirmwareBuildPartial;
                }
                throw AbstractMessage.Builder.f(pOTAFirmwareBuildPartial);
            }

            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
            public POTAFirmware buildPartial() {
                POTAFirmware pOTAFirmware = new POTAFirmware(this);
                pOTAFirmware.currentVersion_ = this.currentVersion_;
                pOTAFirmware.newVersion_ = this.newVersion_;
                pOTAFirmware.desc_ = this.desc_;
                o();
                return pOTAFirmware;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public Builder clearField(Descriptors.FieldDescriptor fieldDescriptor) {
                return (Builder) super.clearField(fieldDescriptor);
            }

            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLiteOrBuilder, com.google.protobuf.MessageOrBuilder
            public POTAFirmware getDefaultInstanceForType() {
                return POTAFirmware.getDefaultInstance();
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public Builder setField(Descriptors.FieldDescriptor fieldDescriptor, Object obj) {
                return (Builder) super.setField(fieldDescriptor, obj);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public Builder setRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, int i2, Object obj) {
                return (Builder) super.setRepeatedField(fieldDescriptor, i2, obj);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public final Builder setUnknownFields(UnknownFieldSet unknownFieldSet) {
                return (Builder) super.setUnknownFields(unknownFieldSet);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.Message.Builder
            public Builder clearOneof(Descriptors.OneofDescriptor oneofDescriptor) {
                return (Builder) super.clearOneof(oneofDescriptor);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.Message.Builder
            public final Builder mergeUnknownFields(UnknownFieldSet unknownFieldSet) {
                return (Builder) super.mergeUnknownFields(unknownFieldSet);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
            public Builder clear() {
                super.clear();
                this.currentVersion_ = "";
                this.newVersion_ = "";
                this.desc_ = "";
                return this;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.AbstractMessageLite.Builder
            /* renamed from: clone */
            public Builder mo65clone() {
                return (Builder) super.mo65clone();
            }

            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.Message.Builder
            public Builder mergeFrom(Message message) {
                if (message instanceof POTAFirmware) {
                    return mergeFrom((POTAFirmware) message);
                }
                super.mergeFrom(message);
                return this;
            }

            private Builder(GeneratedMessageV3.BuilderParent builderParent) {
                super(builderParent);
                this.currentVersion_ = "";
                this.newVersion_ = "";
                this.desc_ = "";
                maybeForceBuilderInitialization();
            }

            public Builder mergeFrom(POTAFirmware pOTAFirmware) {
                if (pOTAFirmware == POTAFirmware.getDefaultInstance()) {
                    return this;
                }
                if (!pOTAFirmware.getCurrentVersion().isEmpty()) {
                    this.currentVersion_ = pOTAFirmware.currentVersion_;
                    p();
                }
                if (!pOTAFirmware.getNewVersion().isEmpty()) {
                    this.newVersion_ = pOTAFirmware.newVersion_;
                    p();
                }
                if (!pOTAFirmware.getDesc().isEmpty()) {
                    this.desc_ = pOTAFirmware.desc_;
                    p();
                }
                mergeUnknownFields(((GeneratedMessageV3) pOTAFirmware).unknownFields);
                p();
                return this;
            }

            /* JADX WARN: Removed duplicated region for block: B:16:0x0023  */
            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.AbstractMessageLite.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public com.kingsmith.plugins.Protos.POTAFirmware.Builder mergeFrom(com.google.protobuf.CodedInputStream r3, com.google.protobuf.ExtensionRegistryLite r4) throws java.lang.Throwable {
                /*
                    r2 = this;
                    r0 = 0
                    com.google.protobuf.Parser r1 = com.kingsmith.plugins.Protos.POTAFirmware.g()     // Catch: java.lang.Throwable -> L11 com.google.protobuf.InvalidProtocolBufferException -> L13
                    java.lang.Object r3 = r1.parsePartialFrom(r3, r4)     // Catch: java.lang.Throwable -> L11 com.google.protobuf.InvalidProtocolBufferException -> L13
                    com.kingsmith.plugins.Protos$POTAFirmware r3 = (com.kingsmith.plugins.Protos.POTAFirmware) r3     // Catch: java.lang.Throwable -> L11 com.google.protobuf.InvalidProtocolBufferException -> L13
                    if (r3 == 0) goto L10
                    r2.mergeFrom(r3)
                L10:
                    return r2
                L11:
                    r3 = move-exception
                    goto L21
                L13:
                    r3 = move-exception
                    com.google.protobuf.MessageLite r4 = r3.getUnfinishedMessage()     // Catch: java.lang.Throwable -> L11
                    com.kingsmith.plugins.Protos$POTAFirmware r4 = (com.kingsmith.plugins.Protos.POTAFirmware) r4     // Catch: java.lang.Throwable -> L11
                    java.io.IOException r3 = r3.unwrapIOException()     // Catch: java.lang.Throwable -> L1f
                    throw r3     // Catch: java.lang.Throwable -> L1f
                L1f:
                    r3 = move-exception
                    r0 = r4
                L21:
                    if (r0 == 0) goto L26
                    r2.mergeFrom(r0)
                L26:
                    throw r3
                */
                throw new UnsupportedOperationException("Method not decompiled: com.kingsmith.plugins.Protos.POTAFirmware.Builder.mergeFrom(com.google.protobuf.CodedInputStream, com.google.protobuf.ExtensionRegistryLite):com.kingsmith.plugins.Protos$POTAFirmware$Builder");
            }
        }

        public static Builder newBuilder(POTAFirmware pOTAFirmware) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(pOTAFirmware);
        }

        public static POTAFirmware parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteBuffer, extensionRegistryLite);
        }

        private POTAFirmware(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
        }

        public static POTAFirmware parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (POTAFirmware) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, inputStream, extensionRegistryLite);
        }

        public static POTAFirmware parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString);
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLiteOrBuilder, com.google.protobuf.MessageOrBuilder
        public POTAFirmware getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite, com.google.protobuf.Message
        public Builder toBuilder() {
            return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
        }

        public static POTAFirmware parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString, extensionRegistryLite);
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite, com.google.protobuf.Message
        public Builder newBuilderForType() {
            return newBuilder();
        }

        private POTAFirmware() {
            this.memoizedIsInitialized = (byte) -1;
            this.currentVersion_ = "";
            this.newVersion_ = "";
            this.desc_ = "";
        }

        public static POTAFirmware parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.protobuf.GeneratedMessageV3
        public Builder newBuilderForType(GeneratedMessageV3.BuilderParent builderParent) {
            return new Builder(builderParent);
        }

        public static POTAFirmware parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr, extensionRegistryLite);
        }

        public static POTAFirmware parseFrom(InputStream inputStream) throws IOException {
            return (POTAFirmware) GeneratedMessageV3.parseWithIOException(PARSER, inputStream);
        }

        public static POTAFirmware parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (POTAFirmware) GeneratedMessageV3.parseWithIOException(PARSER, inputStream, extensionRegistryLite);
        }

        private POTAFirmware(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            this();
            extensionRegistryLite.getClass();
            UnknownFieldSet.Builder builderNewBuilder = UnknownFieldSet.newBuilder();
            boolean z2 = false;
            while (!z2) {
                try {
                    try {
                        int tag = codedInputStream.readTag();
                        if (tag != 0) {
                            if (tag == 10) {
                                this.currentVersion_ = codedInputStream.readStringRequireUtf8();
                            } else if (tag == 18) {
                                this.newVersion_ = codedInputStream.readStringRequireUtf8();
                            } else if (tag != 26) {
                                if (!parseUnknownField(codedInputStream, builderNewBuilder, extensionRegistryLite, tag)) {
                                }
                            } else {
                                this.desc_ = codedInputStream.readStringRequireUtf8();
                            }
                        }
                        z2 = true;
                    } catch (InvalidProtocolBufferException e2) {
                        throw e2.setUnfinishedMessage(this);
                    } catch (IOException e3) {
                        throw new InvalidProtocolBufferException(e3).setUnfinishedMessage(this);
                    }
                } catch (Throwable th) {
                    this.unknownFields = builderNewBuilder.build();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            this.unknownFields = builderNewBuilder.build();
            makeExtensionsImmutable();
        }

        public static POTAFirmware parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (POTAFirmware) GeneratedMessageV3.parseWithIOException(PARSER, codedInputStream);
        }

        public static POTAFirmware parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (POTAFirmware) GeneratedMessageV3.parseWithIOException(PARSER, codedInputStream, extensionRegistryLite);
        }
    }

    public interface POTAFirmwareOrBuilder extends MessageOrBuilder {
        String getCurrentVersion();

        ByteString getCurrentVersionBytes();

        String getDesc();

        ByteString getDescBytes();

        String getNewVersion();

        ByteString getNewVersionBytes();
    }

    public static final class POTAStatus extends GeneratedMessageV3 implements POTAStatusOrBuilder {
        private static final POTAStatus DEFAULT_INSTANCE = new POTAStatus();
        private static final Parser<POTAStatus> PARSER = new AbstractParser<POTAStatus>() { // from class: com.kingsmith.plugins.Protos.POTAStatus.1
            @Override // com.google.protobuf.Parser
            public POTAStatus parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new POTAStatus(codedInputStream, extensionRegistryLite);
            }
        };
        public static final int PROGRESS_FIELD_NUMBER = 1;
        public static final int STATUS_FIELD_NUMBER = 2;
        private static final long serialVersionUID = 0;
        private byte memoizedIsInitialized;
        private int progress_;
        private volatile Object status_;

        public static POTAStatus getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return Protos.internal_static_POTAStatus_descriptor;
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static POTAStatus parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (POTAStatus) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, inputStream);
        }

        public static POTAStatus parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteBuffer);
        }

        public static Parser<POTAStatus> parser() {
            return PARSER;
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.Message
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof POTAStatus)) {
                return super.equals(obj);
            }
            POTAStatus pOTAStatus = (POTAStatus) obj;
            return getProgress() == pOTAStatus.getProgress() && getStatus().equals(pOTAStatus.getStatus()) && this.unknownFields.equals(pOTAStatus.unknownFields);
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.MessageLite, com.google.protobuf.Message
        public Parser<POTAStatus> getParserForType() {
            return PARSER;
        }

        @Override // com.kingsmith.plugins.Protos.POTAStatusOrBuilder
        public int getProgress() {
            return this.progress_;
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite
        public int getSerializedSize() {
            int i2 = this.memoizedSize;
            if (i2 != -1) {
                return i2;
            }
            int i3 = this.progress_;
            int iComputeInt32Size = i3 != 0 ? CodedOutputStream.computeInt32Size(1, i3) : 0;
            if (!getStatusBytes().isEmpty()) {
                iComputeInt32Size += GeneratedMessageV3.computeStringSize(2, this.status_);
            }
            int serializedSize = iComputeInt32Size + this.unknownFields.getSerializedSize();
            this.memoizedSize = serializedSize;
            return serializedSize;
        }

        @Override // com.kingsmith.plugins.Protos.POTAStatusOrBuilder
        public String getStatus() {
            Object obj = this.status_;
            if (obj instanceof String) {
                return (String) obj;
            }
            String stringUtf8 = ((ByteString) obj).toStringUtf8();
            this.status_ = stringUtf8;
            return stringUtf8;
        }

        @Override // com.kingsmith.plugins.Protos.POTAStatusOrBuilder
        public ByteString getStatusBytes() {
            Object obj = this.status_;
            if (!(obj instanceof String)) {
                return (ByteString) obj;
            }
            ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
            this.status_ = byteStringCopyFromUtf8;
            return byteStringCopyFromUtf8;
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.MessageOrBuilder
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.Message
        public int hashCode() {
            int i2 = this.memoizedHashCode;
            if (i2 != 0) {
                return i2;
            }
            int iHashCode = ((((((((((779 + getDescriptor().hashCode()) * 37) + 1) * 53) + getProgress()) * 37) + 2) * 53) + getStatus().hashCode()) * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = iHashCode;
            return iHashCode;
        }

        @Override // com.google.protobuf.GeneratedMessageV3
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return Protos.internal_static_POTAStatus_fieldAccessorTable.ensureFieldAccessorsInitialized(POTAStatus.class, Builder.class);
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            byte b2 = this.memoizedIsInitialized;
            if (b2 == 1) {
                return true;
            }
            if (b2 == 0) {
                return false;
            }
            this.memoizedIsInitialized = (byte) 1;
            return true;
        }

        @Override // com.google.protobuf.GeneratedMessageV3
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unusedPrivateParameter) {
            return new POTAStatus();
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            int i2 = this.progress_;
            if (i2 != 0) {
                codedOutputStream.writeInt32(1, i2);
            }
            if (!getStatusBytes().isEmpty()) {
                GeneratedMessageV3.writeString(codedOutputStream, 2, this.status_);
            }
            this.unknownFields.writeTo(codedOutputStream);
        }

        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements POTAStatusOrBuilder {
            private int progress_;
            private Object status_;

            public static final Descriptors.Descriptor getDescriptor() {
                return Protos.internal_static_POTAStatus_descriptor;
            }

            private void maybeForceBuilderInitialization() {
                boolean unused = GeneratedMessageV3.alwaysUseFieldBuilders;
            }

            public Builder clearProgress() {
                this.progress_ = 0;
                p();
                return this;
            }

            public Builder clearStatus() {
                this.status_ = POTAStatus.getDefaultInstance().getStatus();
                p();
                return this;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder, com.google.protobuf.MessageOrBuilder
            public Descriptors.Descriptor getDescriptorForType() {
                return Protos.internal_static_POTAStatus_descriptor;
            }

            @Override // com.kingsmith.plugins.Protos.POTAStatusOrBuilder
            public int getProgress() {
                return this.progress_;
            }

            @Override // com.kingsmith.plugins.Protos.POTAStatusOrBuilder
            public String getStatus() {
                Object obj = this.status_;
                if (obj instanceof String) {
                    return (String) obj;
                }
                String stringUtf8 = ((ByteString) obj).toStringUtf8();
                this.status_ = stringUtf8;
                return stringUtf8;
            }

            @Override // com.kingsmith.plugins.Protos.POTAStatusOrBuilder
            public ByteString getStatusBytes() {
                Object obj = this.status_;
                if (!(obj instanceof String)) {
                    return (ByteString) obj;
                }
                ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                this.status_ = byteStringCopyFromUtf8;
                return byteStringCopyFromUtf8;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                return true;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder
            protected GeneratedMessageV3.FieldAccessorTable j() {
                return Protos.internal_static_POTAStatus_fieldAccessorTable.ensureFieldAccessorsInitialized(POTAStatus.class, Builder.class);
            }

            public Builder setProgress(int i2) {
                this.progress_ = i2;
                p();
                return this;
            }

            public Builder setStatus(String str) {
                str.getClass();
                this.status_ = str;
                p();
                return this;
            }

            public Builder setStatusBytes(ByteString byteString) throws IllegalArgumentException {
                byteString.getClass();
                AbstractMessageLite.checkByteStringIsUtf8(byteString);
                this.status_ = byteString;
                p();
                return this;
            }

            private Builder() {
                this.status_ = "";
                maybeForceBuilderInitialization();
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public Builder addRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, Object obj) {
                return (Builder) super.addRepeatedField(fieldDescriptor, obj);
            }

            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
            public POTAStatus build() {
                POTAStatus pOTAStatusBuildPartial = buildPartial();
                if (pOTAStatusBuildPartial.isInitialized()) {
                    return pOTAStatusBuildPartial;
                }
                throw AbstractMessage.Builder.f(pOTAStatusBuildPartial);
            }

            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
            public POTAStatus buildPartial() {
                POTAStatus pOTAStatus = new POTAStatus(this);
                pOTAStatus.progress_ = this.progress_;
                pOTAStatus.status_ = this.status_;
                o();
                return pOTAStatus;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public Builder clearField(Descriptors.FieldDescriptor fieldDescriptor) {
                return (Builder) super.clearField(fieldDescriptor);
            }

            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLiteOrBuilder, com.google.protobuf.MessageOrBuilder
            public POTAStatus getDefaultInstanceForType() {
                return POTAStatus.getDefaultInstance();
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public Builder setField(Descriptors.FieldDescriptor fieldDescriptor, Object obj) {
                return (Builder) super.setField(fieldDescriptor, obj);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public Builder setRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, int i2, Object obj) {
                return (Builder) super.setRepeatedField(fieldDescriptor, i2, obj);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public final Builder setUnknownFields(UnknownFieldSet unknownFieldSet) {
                return (Builder) super.setUnknownFields(unknownFieldSet);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.Message.Builder
            public Builder clearOneof(Descriptors.OneofDescriptor oneofDescriptor) {
                return (Builder) super.clearOneof(oneofDescriptor);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.Message.Builder
            public final Builder mergeUnknownFields(UnknownFieldSet unknownFieldSet) {
                return (Builder) super.mergeUnknownFields(unknownFieldSet);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
            public Builder clear() {
                super.clear();
                this.progress_ = 0;
                this.status_ = "";
                return this;
            }

            private Builder(GeneratedMessageV3.BuilderParent builderParent) {
                super(builderParent);
                this.status_ = "";
                maybeForceBuilderInitialization();
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.AbstractMessageLite.Builder
            /* renamed from: clone */
            public Builder mo65clone() {
                return (Builder) super.mo65clone();
            }

            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.Message.Builder
            public Builder mergeFrom(Message message) {
                if (message instanceof POTAStatus) {
                    return mergeFrom((POTAStatus) message);
                }
                super.mergeFrom(message);
                return this;
            }

            public Builder mergeFrom(POTAStatus pOTAStatus) {
                if (pOTAStatus == POTAStatus.getDefaultInstance()) {
                    return this;
                }
                if (pOTAStatus.getProgress() != 0) {
                    setProgress(pOTAStatus.getProgress());
                }
                if (!pOTAStatus.getStatus().isEmpty()) {
                    this.status_ = pOTAStatus.status_;
                    p();
                }
                mergeUnknownFields(((GeneratedMessageV3) pOTAStatus).unknownFields);
                p();
                return this;
            }

            /* JADX WARN: Removed duplicated region for block: B:16:0x0023  */
            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.AbstractMessageLite.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public com.kingsmith.plugins.Protos.POTAStatus.Builder mergeFrom(com.google.protobuf.CodedInputStream r3, com.google.protobuf.ExtensionRegistryLite r4) throws java.lang.Throwable {
                /*
                    r2 = this;
                    r0 = 0
                    com.google.protobuf.Parser r1 = com.kingsmith.plugins.Protos.POTAStatus.d()     // Catch: java.lang.Throwable -> L11 com.google.protobuf.InvalidProtocolBufferException -> L13
                    java.lang.Object r3 = r1.parsePartialFrom(r3, r4)     // Catch: java.lang.Throwable -> L11 com.google.protobuf.InvalidProtocolBufferException -> L13
                    com.kingsmith.plugins.Protos$POTAStatus r3 = (com.kingsmith.plugins.Protos.POTAStatus) r3     // Catch: java.lang.Throwable -> L11 com.google.protobuf.InvalidProtocolBufferException -> L13
                    if (r3 == 0) goto L10
                    r2.mergeFrom(r3)
                L10:
                    return r2
                L11:
                    r3 = move-exception
                    goto L21
                L13:
                    r3 = move-exception
                    com.google.protobuf.MessageLite r4 = r3.getUnfinishedMessage()     // Catch: java.lang.Throwable -> L11
                    com.kingsmith.plugins.Protos$POTAStatus r4 = (com.kingsmith.plugins.Protos.POTAStatus) r4     // Catch: java.lang.Throwable -> L11
                    java.io.IOException r3 = r3.unwrapIOException()     // Catch: java.lang.Throwable -> L1f
                    throw r3     // Catch: java.lang.Throwable -> L1f
                L1f:
                    r3 = move-exception
                    r0 = r4
                L21:
                    if (r0 == 0) goto L26
                    r2.mergeFrom(r0)
                L26:
                    throw r3
                */
                throw new UnsupportedOperationException("Method not decompiled: com.kingsmith.plugins.Protos.POTAStatus.Builder.mergeFrom(com.google.protobuf.CodedInputStream, com.google.protobuf.ExtensionRegistryLite):com.kingsmith.plugins.Protos$POTAStatus$Builder");
            }
        }

        public static Builder newBuilder(POTAStatus pOTAStatus) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(pOTAStatus);
        }

        public static POTAStatus parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteBuffer, extensionRegistryLite);
        }

        private POTAStatus(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
        }

        public static POTAStatus parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (POTAStatus) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, inputStream, extensionRegistryLite);
        }

        public static POTAStatus parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString);
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLiteOrBuilder, com.google.protobuf.MessageOrBuilder
        public POTAStatus getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite, com.google.protobuf.Message
        public Builder toBuilder() {
            return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
        }

        public static POTAStatus parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString, extensionRegistryLite);
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite, com.google.protobuf.Message
        public Builder newBuilderForType() {
            return newBuilder();
        }

        private POTAStatus() {
            this.memoizedIsInitialized = (byte) -1;
            this.status_ = "";
        }

        public static POTAStatus parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.protobuf.GeneratedMessageV3
        public Builder newBuilderForType(GeneratedMessageV3.BuilderParent builderParent) {
            return new Builder(builderParent);
        }

        public static POTAStatus parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr, extensionRegistryLite);
        }

        public static POTAStatus parseFrom(InputStream inputStream) throws IOException {
            return (POTAStatus) GeneratedMessageV3.parseWithIOException(PARSER, inputStream);
        }

        private POTAStatus(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            this();
            extensionRegistryLite.getClass();
            UnknownFieldSet.Builder builderNewBuilder = UnknownFieldSet.newBuilder();
            boolean z2 = false;
            while (!z2) {
                try {
                    try {
                        int tag = codedInputStream.readTag();
                        if (tag != 0) {
                            if (tag == 8) {
                                this.progress_ = codedInputStream.readInt32();
                            } else if (tag != 18) {
                                if (!parseUnknownField(codedInputStream, builderNewBuilder, extensionRegistryLite, tag)) {
                                }
                            } else {
                                this.status_ = codedInputStream.readStringRequireUtf8();
                            }
                        }
                        z2 = true;
                    } catch (InvalidProtocolBufferException e2) {
                        throw e2.setUnfinishedMessage(this);
                    } catch (IOException e3) {
                        throw new InvalidProtocolBufferException(e3).setUnfinishedMessage(this);
                    }
                } catch (Throwable th) {
                    this.unknownFields = builderNewBuilder.build();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            this.unknownFields = builderNewBuilder.build();
            makeExtensionsImmutable();
        }

        public static POTAStatus parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (POTAStatus) GeneratedMessageV3.parseWithIOException(PARSER, inputStream, extensionRegistryLite);
        }

        public static POTAStatus parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (POTAStatus) GeneratedMessageV3.parseWithIOException(PARSER, codedInputStream);
        }

        public static POTAStatus parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (POTAStatus) GeneratedMessageV3.parseWithIOException(PARSER, codedInputStream, extensionRegistryLite);
        }
    }

    public interface POTAStatusOrBuilder extends MessageOrBuilder {
        int getProgress();

        String getStatus();

        ByteString getStatusBytes();
    }

    public static final class PProperties extends GeneratedMessageV3 implements PPropertiesOrBuilder {
        public static final int BSSID_FIELD_NUMBER = 3;
        public static final int CHILD_LOCK_FIELD_NUMBER = 2;
        public static final int CODE_FIELD_NUMBER = 4;
        public static final int MAX_FIELD_NUMBER = 1;
        private static final long serialVersionUID = 0;
        private volatile Object bssid_;
        private volatile Object childLock_;
        private int code_;
        private double max_;
        private byte memoizedIsInitialized;
        private static final PProperties DEFAULT_INSTANCE = new PProperties();
        private static final Parser<PProperties> PARSER = new AbstractParser<PProperties>() { // from class: com.kingsmith.plugins.Protos.PProperties.1
            @Override // com.google.protobuf.Parser
            public PProperties parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new PProperties(codedInputStream, extensionRegistryLite);
            }
        };

        public static PProperties getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return Protos.internal_static_PProperties_descriptor;
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static PProperties parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (PProperties) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, inputStream);
        }

        public static PProperties parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteBuffer);
        }

        public static Parser<PProperties> parser() {
            return PARSER;
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.Message
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof PProperties)) {
                return super.equals(obj);
            }
            PProperties pProperties = (PProperties) obj;
            return Double.doubleToLongBits(getMax()) == Double.doubleToLongBits(pProperties.getMax()) && getChildLock().equals(pProperties.getChildLock()) && getBssid().equals(pProperties.getBssid()) && getCode() == pProperties.getCode() && this.unknownFields.equals(pProperties.unknownFields);
        }

        @Override // com.kingsmith.plugins.Protos.PPropertiesOrBuilder
        public String getBssid() {
            Object obj = this.bssid_;
            if (obj instanceof String) {
                return (String) obj;
            }
            String stringUtf8 = ((ByteString) obj).toStringUtf8();
            this.bssid_ = stringUtf8;
            return stringUtf8;
        }

        @Override // com.kingsmith.plugins.Protos.PPropertiesOrBuilder
        public ByteString getBssidBytes() {
            Object obj = this.bssid_;
            if (!(obj instanceof String)) {
                return (ByteString) obj;
            }
            ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
            this.bssid_ = byteStringCopyFromUtf8;
            return byteStringCopyFromUtf8;
        }

        @Override // com.kingsmith.plugins.Protos.PPropertiesOrBuilder
        public String getChildLock() {
            Object obj = this.childLock_;
            if (obj instanceof String) {
                return (String) obj;
            }
            String stringUtf8 = ((ByteString) obj).toStringUtf8();
            this.childLock_ = stringUtf8;
            return stringUtf8;
        }

        @Override // com.kingsmith.plugins.Protos.PPropertiesOrBuilder
        public ByteString getChildLockBytes() {
            Object obj = this.childLock_;
            if (!(obj instanceof String)) {
                return (ByteString) obj;
            }
            ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
            this.childLock_ = byteStringCopyFromUtf8;
            return byteStringCopyFromUtf8;
        }

        @Override // com.kingsmith.plugins.Protos.PPropertiesOrBuilder
        public int getCode() {
            return this.code_;
        }

        @Override // com.kingsmith.plugins.Protos.PPropertiesOrBuilder
        public double getMax() {
            return this.max_;
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.MessageLite, com.google.protobuf.Message
        public Parser<PProperties> getParserForType() {
            return PARSER;
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite
        public int getSerializedSize() {
            int i2 = this.memoizedSize;
            if (i2 != -1) {
                return i2;
            }
            double d2 = this.max_;
            int iComputeDoubleSize = d2 != 0.0d ? CodedOutputStream.computeDoubleSize(1, d2) : 0;
            if (!getChildLockBytes().isEmpty()) {
                iComputeDoubleSize += GeneratedMessageV3.computeStringSize(2, this.childLock_);
            }
            if (!getBssidBytes().isEmpty()) {
                iComputeDoubleSize += GeneratedMessageV3.computeStringSize(3, this.bssid_);
            }
            int i3 = this.code_;
            if (i3 != 0) {
                iComputeDoubleSize += CodedOutputStream.computeInt32Size(4, i3);
            }
            int serializedSize = iComputeDoubleSize + this.unknownFields.getSerializedSize();
            this.memoizedSize = serializedSize;
            return serializedSize;
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.MessageOrBuilder
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.Message
        public int hashCode() {
            int i2 = this.memoizedHashCode;
            if (i2 != 0) {
                return i2;
            }
            int iHashCode = ((((((((((((((((((779 + getDescriptor().hashCode()) * 37) + 1) * 53) + Internal.hashLong(Double.doubleToLongBits(getMax()))) * 37) + 2) * 53) + getChildLock().hashCode()) * 37) + 3) * 53) + getBssid().hashCode()) * 37) + 4) * 53) + getCode()) * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = iHashCode;
            return iHashCode;
        }

        @Override // com.google.protobuf.GeneratedMessageV3
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return Protos.internal_static_PProperties_fieldAccessorTable.ensureFieldAccessorsInitialized(PProperties.class, Builder.class);
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            byte b2 = this.memoizedIsInitialized;
            if (b2 == 1) {
                return true;
            }
            if (b2 == 0) {
                return false;
            }
            this.memoizedIsInitialized = (byte) 1;
            return true;
        }

        @Override // com.google.protobuf.GeneratedMessageV3
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unusedPrivateParameter) {
            return new PProperties();
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            double d2 = this.max_;
            if (d2 != 0.0d) {
                codedOutputStream.writeDouble(1, d2);
            }
            if (!getChildLockBytes().isEmpty()) {
                GeneratedMessageV3.writeString(codedOutputStream, 2, this.childLock_);
            }
            if (!getBssidBytes().isEmpty()) {
                GeneratedMessageV3.writeString(codedOutputStream, 3, this.bssid_);
            }
            int i2 = this.code_;
            if (i2 != 0) {
                codedOutputStream.writeInt32(4, i2);
            }
            this.unknownFields.writeTo(codedOutputStream);
        }

        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements PPropertiesOrBuilder {
            private Object bssid_;
            private Object childLock_;
            private int code_;
            private double max_;

            public static final Descriptors.Descriptor getDescriptor() {
                return Protos.internal_static_PProperties_descriptor;
            }

            private void maybeForceBuilderInitialization() {
                boolean unused = GeneratedMessageV3.alwaysUseFieldBuilders;
            }

            public Builder clearBssid() {
                this.bssid_ = PProperties.getDefaultInstance().getBssid();
                p();
                return this;
            }

            public Builder clearChildLock() {
                this.childLock_ = PProperties.getDefaultInstance().getChildLock();
                p();
                return this;
            }

            public Builder clearCode() {
                this.code_ = 0;
                p();
                return this;
            }

            public Builder clearMax() {
                this.max_ = 0.0d;
                p();
                return this;
            }

            @Override // com.kingsmith.plugins.Protos.PPropertiesOrBuilder
            public String getBssid() {
                Object obj = this.bssid_;
                if (obj instanceof String) {
                    return (String) obj;
                }
                String stringUtf8 = ((ByteString) obj).toStringUtf8();
                this.bssid_ = stringUtf8;
                return stringUtf8;
            }

            @Override // com.kingsmith.plugins.Protos.PPropertiesOrBuilder
            public ByteString getBssidBytes() {
                Object obj = this.bssid_;
                if (!(obj instanceof String)) {
                    return (ByteString) obj;
                }
                ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                this.bssid_ = byteStringCopyFromUtf8;
                return byteStringCopyFromUtf8;
            }

            @Override // com.kingsmith.plugins.Protos.PPropertiesOrBuilder
            public String getChildLock() {
                Object obj = this.childLock_;
                if (obj instanceof String) {
                    return (String) obj;
                }
                String stringUtf8 = ((ByteString) obj).toStringUtf8();
                this.childLock_ = stringUtf8;
                return stringUtf8;
            }

            @Override // com.kingsmith.plugins.Protos.PPropertiesOrBuilder
            public ByteString getChildLockBytes() {
                Object obj = this.childLock_;
                if (!(obj instanceof String)) {
                    return (ByteString) obj;
                }
                ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                this.childLock_ = byteStringCopyFromUtf8;
                return byteStringCopyFromUtf8;
            }

            @Override // com.kingsmith.plugins.Protos.PPropertiesOrBuilder
            public int getCode() {
                return this.code_;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder, com.google.protobuf.MessageOrBuilder
            public Descriptors.Descriptor getDescriptorForType() {
                return Protos.internal_static_PProperties_descriptor;
            }

            @Override // com.kingsmith.plugins.Protos.PPropertiesOrBuilder
            public double getMax() {
                return this.max_;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                return true;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder
            protected GeneratedMessageV3.FieldAccessorTable j() {
                return Protos.internal_static_PProperties_fieldAccessorTable.ensureFieldAccessorsInitialized(PProperties.class, Builder.class);
            }

            public Builder setBssid(String str) {
                str.getClass();
                this.bssid_ = str;
                p();
                return this;
            }

            public Builder setBssidBytes(ByteString byteString) throws IllegalArgumentException {
                byteString.getClass();
                AbstractMessageLite.checkByteStringIsUtf8(byteString);
                this.bssid_ = byteString;
                p();
                return this;
            }

            public Builder setChildLock(String str) {
                str.getClass();
                this.childLock_ = str;
                p();
                return this;
            }

            public Builder setChildLockBytes(ByteString byteString) throws IllegalArgumentException {
                byteString.getClass();
                AbstractMessageLite.checkByteStringIsUtf8(byteString);
                this.childLock_ = byteString;
                p();
                return this;
            }

            public Builder setCode(int i2) {
                this.code_ = i2;
                p();
                return this;
            }

            public Builder setMax(double d2) {
                this.max_ = d2;
                p();
                return this;
            }

            private Builder() {
                this.childLock_ = "";
                this.bssid_ = "";
                maybeForceBuilderInitialization();
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public Builder addRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, Object obj) {
                return (Builder) super.addRepeatedField(fieldDescriptor, obj);
            }

            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
            public PProperties build() {
                PProperties pPropertiesBuildPartial = buildPartial();
                if (pPropertiesBuildPartial.isInitialized()) {
                    return pPropertiesBuildPartial;
                }
                throw AbstractMessage.Builder.f(pPropertiesBuildPartial);
            }

            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
            public PProperties buildPartial() {
                PProperties pProperties = new PProperties(this);
                pProperties.max_ = this.max_;
                pProperties.childLock_ = this.childLock_;
                pProperties.bssid_ = this.bssid_;
                pProperties.code_ = this.code_;
                o();
                return pProperties;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public Builder clearField(Descriptors.FieldDescriptor fieldDescriptor) {
                return (Builder) super.clearField(fieldDescriptor);
            }

            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLiteOrBuilder, com.google.protobuf.MessageOrBuilder
            public PProperties getDefaultInstanceForType() {
                return PProperties.getDefaultInstance();
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public Builder setField(Descriptors.FieldDescriptor fieldDescriptor, Object obj) {
                return (Builder) super.setField(fieldDescriptor, obj);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public Builder setRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, int i2, Object obj) {
                return (Builder) super.setRepeatedField(fieldDescriptor, i2, obj);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public final Builder setUnknownFields(UnknownFieldSet unknownFieldSet) {
                return (Builder) super.setUnknownFields(unknownFieldSet);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.Message.Builder
            public Builder clearOneof(Descriptors.OneofDescriptor oneofDescriptor) {
                return (Builder) super.clearOneof(oneofDescriptor);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.Message.Builder
            public final Builder mergeUnknownFields(UnknownFieldSet unknownFieldSet) {
                return (Builder) super.mergeUnknownFields(unknownFieldSet);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
            public Builder clear() {
                super.clear();
                this.max_ = 0.0d;
                this.childLock_ = "";
                this.bssid_ = "";
                this.code_ = 0;
                return this;
            }

            private Builder(GeneratedMessageV3.BuilderParent builderParent) {
                super(builderParent);
                this.childLock_ = "";
                this.bssid_ = "";
                maybeForceBuilderInitialization();
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.AbstractMessageLite.Builder
            /* renamed from: clone */
            public Builder mo65clone() {
                return (Builder) super.mo65clone();
            }

            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.Message.Builder
            public Builder mergeFrom(Message message) {
                if (message instanceof PProperties) {
                    return mergeFrom((PProperties) message);
                }
                super.mergeFrom(message);
                return this;
            }

            public Builder mergeFrom(PProperties pProperties) {
                if (pProperties == PProperties.getDefaultInstance()) {
                    return this;
                }
                if (pProperties.getMax() != 0.0d) {
                    setMax(pProperties.getMax());
                }
                if (!pProperties.getChildLock().isEmpty()) {
                    this.childLock_ = pProperties.childLock_;
                    p();
                }
                if (!pProperties.getBssid().isEmpty()) {
                    this.bssid_ = pProperties.bssid_;
                    p();
                }
                if (pProperties.getCode() != 0) {
                    setCode(pProperties.getCode());
                }
                mergeUnknownFields(((GeneratedMessageV3) pProperties).unknownFields);
                p();
                return this;
            }

            /* JADX WARN: Removed duplicated region for block: B:16:0x0023  */
            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.AbstractMessageLite.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public com.kingsmith.plugins.Protos.PProperties.Builder mergeFrom(com.google.protobuf.CodedInputStream r3, com.google.protobuf.ExtensionRegistryLite r4) throws java.lang.Throwable {
                /*
                    r2 = this;
                    r0 = 0
                    com.google.protobuf.Parser r1 = com.kingsmith.plugins.Protos.PProperties.g()     // Catch: java.lang.Throwable -> L11 com.google.protobuf.InvalidProtocolBufferException -> L13
                    java.lang.Object r3 = r1.parsePartialFrom(r3, r4)     // Catch: java.lang.Throwable -> L11 com.google.protobuf.InvalidProtocolBufferException -> L13
                    com.kingsmith.plugins.Protos$PProperties r3 = (com.kingsmith.plugins.Protos.PProperties) r3     // Catch: java.lang.Throwable -> L11 com.google.protobuf.InvalidProtocolBufferException -> L13
                    if (r3 == 0) goto L10
                    r2.mergeFrom(r3)
                L10:
                    return r2
                L11:
                    r3 = move-exception
                    goto L21
                L13:
                    r3 = move-exception
                    com.google.protobuf.MessageLite r4 = r3.getUnfinishedMessage()     // Catch: java.lang.Throwable -> L11
                    com.kingsmith.plugins.Protos$PProperties r4 = (com.kingsmith.plugins.Protos.PProperties) r4     // Catch: java.lang.Throwable -> L11
                    java.io.IOException r3 = r3.unwrapIOException()     // Catch: java.lang.Throwable -> L1f
                    throw r3     // Catch: java.lang.Throwable -> L1f
                L1f:
                    r3 = move-exception
                    r0 = r4
                L21:
                    if (r0 == 0) goto L26
                    r2.mergeFrom(r0)
                L26:
                    throw r3
                */
                throw new UnsupportedOperationException("Method not decompiled: com.kingsmith.plugins.Protos.PProperties.Builder.mergeFrom(com.google.protobuf.CodedInputStream, com.google.protobuf.ExtensionRegistryLite):com.kingsmith.plugins.Protos$PProperties$Builder");
            }
        }

        public static Builder newBuilder(PProperties pProperties) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(pProperties);
        }

        public static PProperties parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteBuffer, extensionRegistryLite);
        }

        private PProperties(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
        }

        public static PProperties parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (PProperties) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, inputStream, extensionRegistryLite);
        }

        public static PProperties parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString);
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLiteOrBuilder, com.google.protobuf.MessageOrBuilder
        public PProperties getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite, com.google.protobuf.Message
        public Builder toBuilder() {
            return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
        }

        public static PProperties parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString, extensionRegistryLite);
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite, com.google.protobuf.Message
        public Builder newBuilderForType() {
            return newBuilder();
        }

        private PProperties() {
            this.memoizedIsInitialized = (byte) -1;
            this.childLock_ = "";
            this.bssid_ = "";
        }

        public static PProperties parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.protobuf.GeneratedMessageV3
        public Builder newBuilderForType(GeneratedMessageV3.BuilderParent builderParent) {
            return new Builder(builderParent);
        }

        public static PProperties parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr, extensionRegistryLite);
        }

        public static PProperties parseFrom(InputStream inputStream) throws IOException {
            return (PProperties) GeneratedMessageV3.parseWithIOException(PARSER, inputStream);
        }

        private PProperties(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            this();
            extensionRegistryLite.getClass();
            UnknownFieldSet.Builder builderNewBuilder = UnknownFieldSet.newBuilder();
            boolean z2 = false;
            while (!z2) {
                try {
                    try {
                        int tag = codedInputStream.readTag();
                        if (tag != 0) {
                            if (tag == 9) {
                                this.max_ = codedInputStream.readDouble();
                            } else if (tag == 18) {
                                this.childLock_ = codedInputStream.readStringRequireUtf8();
                            } else if (tag == 26) {
                                this.bssid_ = codedInputStream.readStringRequireUtf8();
                            } else if (tag != 32) {
                                if (!parseUnknownField(codedInputStream, builderNewBuilder, extensionRegistryLite, tag)) {
                                }
                            } else {
                                this.code_ = codedInputStream.readInt32();
                            }
                        }
                        z2 = true;
                    } catch (InvalidProtocolBufferException e2) {
                        throw e2.setUnfinishedMessage(this);
                    } catch (IOException e3) {
                        throw new InvalidProtocolBufferException(e3).setUnfinishedMessage(this);
                    }
                } catch (Throwable th) {
                    this.unknownFields = builderNewBuilder.build();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            this.unknownFields = builderNewBuilder.build();
            makeExtensionsImmutable();
        }

        public static PProperties parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (PProperties) GeneratedMessageV3.parseWithIOException(PARSER, inputStream, extensionRegistryLite);
        }

        public static PProperties parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (PProperties) GeneratedMessageV3.parseWithIOException(PARSER, codedInputStream);
        }

        public static PProperties parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (PProperties) GeneratedMessageV3.parseWithIOException(PARSER, codedInputStream, extensionRegistryLite);
        }
    }

    public interface PPropertiesOrBuilder extends MessageOrBuilder {
        String getBssid();

        ByteString getBssidBytes();

        String getChildLock();

        ByteString getChildLockBytes();

        int getCode();

        double getMax();
    }

    public static final class PPropertiesTr extends GeneratedMessageV3 implements PPropertiesTrOrBuilder {
        public static final int KEY1_FIELD_NUMBER = 2;
        public static final int KEY2_FIELD_NUMBER = 3;
        public static final int KEY3_FIELD_NUMBER = 4;
        public static final int PARENT_FIELD_NUMBER = 1;
        public static final int PROGRAM1_FIELD_NUMBER = 5;
        public static final int PROGRAM2_FIELD_NUMBER = 6;
        public static final int PROGRAM3_FIELD_NUMBER = 7;
        private static final long serialVersionUID = 0;
        private int key1_;
        private int key2_;
        private int key3_;
        private byte memoizedIsInitialized;
        private PProperties parent_;
        private volatile Object program1_;
        private volatile Object program2_;
        private volatile Object program3_;
        private static final PPropertiesTr DEFAULT_INSTANCE = new PPropertiesTr();
        private static final Parser<PPropertiesTr> PARSER = new AbstractParser<PPropertiesTr>() { // from class: com.kingsmith.plugins.Protos.PPropertiesTr.1
            @Override // com.google.protobuf.Parser
            public PPropertiesTr parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new PPropertiesTr(codedInputStream, extensionRegistryLite);
            }
        };

        public static PPropertiesTr getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return Protos.internal_static_PPropertiesTr_descriptor;
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static PPropertiesTr parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (PPropertiesTr) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, inputStream);
        }

        public static PPropertiesTr parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteBuffer);
        }

        public static Parser<PPropertiesTr> parser() {
            return PARSER;
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.Message
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof PPropertiesTr)) {
                return super.equals(obj);
            }
            PPropertiesTr pPropertiesTr = (PPropertiesTr) obj;
            if (hasParent() != pPropertiesTr.hasParent()) {
                return false;
            }
            return (!hasParent() || getParent().equals(pPropertiesTr.getParent())) && getKey1() == pPropertiesTr.getKey1() && getKey2() == pPropertiesTr.getKey2() && getKey3() == pPropertiesTr.getKey3() && getProgram1().equals(pPropertiesTr.getProgram1()) && getProgram2().equals(pPropertiesTr.getProgram2()) && getProgram3().equals(pPropertiesTr.getProgram3()) && this.unknownFields.equals(pPropertiesTr.unknownFields);
        }

        @Override // com.kingsmith.plugins.Protos.PPropertiesTrOrBuilder
        public int getKey1() {
            return this.key1_;
        }

        @Override // com.kingsmith.plugins.Protos.PPropertiesTrOrBuilder
        public int getKey2() {
            return this.key2_;
        }

        @Override // com.kingsmith.plugins.Protos.PPropertiesTrOrBuilder
        public int getKey3() {
            return this.key3_;
        }

        @Override // com.kingsmith.plugins.Protos.PPropertiesTrOrBuilder
        public PProperties getParent() {
            PProperties pProperties = this.parent_;
            return pProperties == null ? PProperties.getDefaultInstance() : pProperties;
        }

        @Override // com.kingsmith.plugins.Protos.PPropertiesTrOrBuilder
        public PPropertiesOrBuilder getParentOrBuilder() {
            return getParent();
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.MessageLite, com.google.protobuf.Message
        public Parser<PPropertiesTr> getParserForType() {
            return PARSER;
        }

        @Override // com.kingsmith.plugins.Protos.PPropertiesTrOrBuilder
        public String getProgram1() {
            Object obj = this.program1_;
            if (obj instanceof String) {
                return (String) obj;
            }
            String stringUtf8 = ((ByteString) obj).toStringUtf8();
            this.program1_ = stringUtf8;
            return stringUtf8;
        }

        @Override // com.kingsmith.plugins.Protos.PPropertiesTrOrBuilder
        public ByteString getProgram1Bytes() {
            Object obj = this.program1_;
            if (!(obj instanceof String)) {
                return (ByteString) obj;
            }
            ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
            this.program1_ = byteStringCopyFromUtf8;
            return byteStringCopyFromUtf8;
        }

        @Override // com.kingsmith.plugins.Protos.PPropertiesTrOrBuilder
        public String getProgram2() {
            Object obj = this.program2_;
            if (obj instanceof String) {
                return (String) obj;
            }
            String stringUtf8 = ((ByteString) obj).toStringUtf8();
            this.program2_ = stringUtf8;
            return stringUtf8;
        }

        @Override // com.kingsmith.plugins.Protos.PPropertiesTrOrBuilder
        public ByteString getProgram2Bytes() {
            Object obj = this.program2_;
            if (!(obj instanceof String)) {
                return (ByteString) obj;
            }
            ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
            this.program2_ = byteStringCopyFromUtf8;
            return byteStringCopyFromUtf8;
        }

        @Override // com.kingsmith.plugins.Protos.PPropertiesTrOrBuilder
        public String getProgram3() {
            Object obj = this.program3_;
            if (obj instanceof String) {
                return (String) obj;
            }
            String stringUtf8 = ((ByteString) obj).toStringUtf8();
            this.program3_ = stringUtf8;
            return stringUtf8;
        }

        @Override // com.kingsmith.plugins.Protos.PPropertiesTrOrBuilder
        public ByteString getProgram3Bytes() {
            Object obj = this.program3_;
            if (!(obj instanceof String)) {
                return (ByteString) obj;
            }
            ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
            this.program3_ = byteStringCopyFromUtf8;
            return byteStringCopyFromUtf8;
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite
        public int getSerializedSize() {
            int i2 = this.memoizedSize;
            if (i2 != -1) {
                return i2;
            }
            int iComputeMessageSize = this.parent_ != null ? CodedOutputStream.computeMessageSize(1, getParent()) : 0;
            int i3 = this.key1_;
            if (i3 != 0) {
                iComputeMessageSize += CodedOutputStream.computeInt32Size(2, i3);
            }
            int i4 = this.key2_;
            if (i4 != 0) {
                iComputeMessageSize += CodedOutputStream.computeInt32Size(3, i4);
            }
            int i5 = this.key3_;
            if (i5 != 0) {
                iComputeMessageSize += CodedOutputStream.computeInt32Size(4, i5);
            }
            if (!getProgram1Bytes().isEmpty()) {
                iComputeMessageSize += GeneratedMessageV3.computeStringSize(5, this.program1_);
            }
            if (!getProgram2Bytes().isEmpty()) {
                iComputeMessageSize += GeneratedMessageV3.computeStringSize(6, this.program2_);
            }
            if (!getProgram3Bytes().isEmpty()) {
                iComputeMessageSize += GeneratedMessageV3.computeStringSize(7, this.program3_);
            }
            int serializedSize = iComputeMessageSize + this.unknownFields.getSerializedSize();
            this.memoizedSize = serializedSize;
            return serializedSize;
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.MessageOrBuilder
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        @Override // com.kingsmith.plugins.Protos.PPropertiesTrOrBuilder
        public boolean hasParent() {
            return this.parent_ != null;
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.Message
        public int hashCode() {
            int i2 = this.memoizedHashCode;
            if (i2 != 0) {
                return i2;
            }
            int iHashCode = 779 + getDescriptor().hashCode();
            if (hasParent()) {
                iHashCode = (((iHashCode * 37) + 1) * 53) + getParent().hashCode();
            }
            int key1 = (((((((((((((((((((((((((iHashCode * 37) + 2) * 53) + getKey1()) * 37) + 3) * 53) + getKey2()) * 37) + 4) * 53) + getKey3()) * 37) + 5) * 53) + getProgram1().hashCode()) * 37) + 6) * 53) + getProgram2().hashCode()) * 37) + 7) * 53) + getProgram3().hashCode()) * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = key1;
            return key1;
        }

        @Override // com.google.protobuf.GeneratedMessageV3
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return Protos.internal_static_PPropertiesTr_fieldAccessorTable.ensureFieldAccessorsInitialized(PPropertiesTr.class, Builder.class);
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            byte b2 = this.memoizedIsInitialized;
            if (b2 == 1) {
                return true;
            }
            if (b2 == 0) {
                return false;
            }
            this.memoizedIsInitialized = (byte) 1;
            return true;
        }

        @Override // com.google.protobuf.GeneratedMessageV3
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unusedPrivateParameter) {
            return new PPropertiesTr();
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            if (this.parent_ != null) {
                codedOutputStream.writeMessage(1, getParent());
            }
            int i2 = this.key1_;
            if (i2 != 0) {
                codedOutputStream.writeInt32(2, i2);
            }
            int i3 = this.key2_;
            if (i3 != 0) {
                codedOutputStream.writeInt32(3, i3);
            }
            int i4 = this.key3_;
            if (i4 != 0) {
                codedOutputStream.writeInt32(4, i4);
            }
            if (!getProgram1Bytes().isEmpty()) {
                GeneratedMessageV3.writeString(codedOutputStream, 5, this.program1_);
            }
            if (!getProgram2Bytes().isEmpty()) {
                GeneratedMessageV3.writeString(codedOutputStream, 6, this.program2_);
            }
            if (!getProgram3Bytes().isEmpty()) {
                GeneratedMessageV3.writeString(codedOutputStream, 7, this.program3_);
            }
            this.unknownFields.writeTo(codedOutputStream);
        }

        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements PPropertiesTrOrBuilder {
            private int key1_;
            private int key2_;
            private int key3_;
            private SingleFieldBuilderV3<PProperties, PProperties.Builder, PPropertiesOrBuilder> parentBuilder_;
            private PProperties parent_;
            private Object program1_;
            private Object program2_;
            private Object program3_;

            public static final Descriptors.Descriptor getDescriptor() {
                return Protos.internal_static_PPropertiesTr_descriptor;
            }

            private SingleFieldBuilderV3<PProperties, PProperties.Builder, PPropertiesOrBuilder> getParentFieldBuilder() {
                if (this.parentBuilder_ == null) {
                    this.parentBuilder_ = new SingleFieldBuilderV3<>(getParent(), i(), m());
                    this.parent_ = null;
                }
                return this.parentBuilder_;
            }

            private void maybeForceBuilderInitialization() {
                boolean unused = GeneratedMessageV3.alwaysUseFieldBuilders;
            }

            public Builder clearKey1() {
                this.key1_ = 0;
                p();
                return this;
            }

            public Builder clearKey2() {
                this.key2_ = 0;
                p();
                return this;
            }

            public Builder clearKey3() {
                this.key3_ = 0;
                p();
                return this;
            }

            public Builder clearParent() {
                if (this.parentBuilder_ == null) {
                    this.parent_ = null;
                    p();
                } else {
                    this.parent_ = null;
                    this.parentBuilder_ = null;
                }
                return this;
            }

            public Builder clearProgram1() {
                this.program1_ = PPropertiesTr.getDefaultInstance().getProgram1();
                p();
                return this;
            }

            public Builder clearProgram2() {
                this.program2_ = PPropertiesTr.getDefaultInstance().getProgram2();
                p();
                return this;
            }

            public Builder clearProgram3() {
                this.program3_ = PPropertiesTr.getDefaultInstance().getProgram3();
                p();
                return this;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder, com.google.protobuf.MessageOrBuilder
            public Descriptors.Descriptor getDescriptorForType() {
                return Protos.internal_static_PPropertiesTr_descriptor;
            }

            @Override // com.kingsmith.plugins.Protos.PPropertiesTrOrBuilder
            public int getKey1() {
                return this.key1_;
            }

            @Override // com.kingsmith.plugins.Protos.PPropertiesTrOrBuilder
            public int getKey2() {
                return this.key2_;
            }

            @Override // com.kingsmith.plugins.Protos.PPropertiesTrOrBuilder
            public int getKey3() {
                return this.key3_;
            }

            @Override // com.kingsmith.plugins.Protos.PPropertiesTrOrBuilder
            public PProperties getParent() {
                SingleFieldBuilderV3<PProperties, PProperties.Builder, PPropertiesOrBuilder> singleFieldBuilderV3 = this.parentBuilder_;
                if (singleFieldBuilderV3 != null) {
                    return (PProperties) singleFieldBuilderV3.getMessage();
                }
                PProperties pProperties = this.parent_;
                return pProperties == null ? PProperties.getDefaultInstance() : pProperties;
            }

            public PProperties.Builder getParentBuilder() {
                p();
                return (PProperties.Builder) getParentFieldBuilder().getBuilder();
            }

            @Override // com.kingsmith.plugins.Protos.PPropertiesTrOrBuilder
            public PPropertiesOrBuilder getParentOrBuilder() {
                SingleFieldBuilderV3<PProperties, PProperties.Builder, PPropertiesOrBuilder> singleFieldBuilderV3 = this.parentBuilder_;
                if (singleFieldBuilderV3 != null) {
                    return (PPropertiesOrBuilder) singleFieldBuilderV3.getMessageOrBuilder();
                }
                PProperties pProperties = this.parent_;
                return pProperties == null ? PProperties.getDefaultInstance() : pProperties;
            }

            @Override // com.kingsmith.plugins.Protos.PPropertiesTrOrBuilder
            public String getProgram1() {
                Object obj = this.program1_;
                if (obj instanceof String) {
                    return (String) obj;
                }
                String stringUtf8 = ((ByteString) obj).toStringUtf8();
                this.program1_ = stringUtf8;
                return stringUtf8;
            }

            @Override // com.kingsmith.plugins.Protos.PPropertiesTrOrBuilder
            public ByteString getProgram1Bytes() {
                Object obj = this.program1_;
                if (!(obj instanceof String)) {
                    return (ByteString) obj;
                }
                ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                this.program1_ = byteStringCopyFromUtf8;
                return byteStringCopyFromUtf8;
            }

            @Override // com.kingsmith.plugins.Protos.PPropertiesTrOrBuilder
            public String getProgram2() {
                Object obj = this.program2_;
                if (obj instanceof String) {
                    return (String) obj;
                }
                String stringUtf8 = ((ByteString) obj).toStringUtf8();
                this.program2_ = stringUtf8;
                return stringUtf8;
            }

            @Override // com.kingsmith.plugins.Protos.PPropertiesTrOrBuilder
            public ByteString getProgram2Bytes() {
                Object obj = this.program2_;
                if (!(obj instanceof String)) {
                    return (ByteString) obj;
                }
                ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                this.program2_ = byteStringCopyFromUtf8;
                return byteStringCopyFromUtf8;
            }

            @Override // com.kingsmith.plugins.Protos.PPropertiesTrOrBuilder
            public String getProgram3() {
                Object obj = this.program3_;
                if (obj instanceof String) {
                    return (String) obj;
                }
                String stringUtf8 = ((ByteString) obj).toStringUtf8();
                this.program3_ = stringUtf8;
                return stringUtf8;
            }

            @Override // com.kingsmith.plugins.Protos.PPropertiesTrOrBuilder
            public ByteString getProgram3Bytes() {
                Object obj = this.program3_;
                if (!(obj instanceof String)) {
                    return (ByteString) obj;
                }
                ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                this.program3_ = byteStringCopyFromUtf8;
                return byteStringCopyFromUtf8;
            }

            @Override // com.kingsmith.plugins.Protos.PPropertiesTrOrBuilder
            public boolean hasParent() {
                return (this.parentBuilder_ == null && this.parent_ == null) ? false : true;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                return true;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder
            protected GeneratedMessageV3.FieldAccessorTable j() {
                return Protos.internal_static_PPropertiesTr_fieldAccessorTable.ensureFieldAccessorsInitialized(PPropertiesTr.class, Builder.class);
            }

            public Builder mergeParent(PProperties pProperties) {
                SingleFieldBuilderV3<PProperties, PProperties.Builder, PPropertiesOrBuilder> singleFieldBuilderV3 = this.parentBuilder_;
                if (singleFieldBuilderV3 == null) {
                    PProperties pProperties2 = this.parent_;
                    if (pProperties2 != null) {
                        this.parent_ = PProperties.newBuilder(pProperties2).mergeFrom(pProperties).buildPartial();
                    } else {
                        this.parent_ = pProperties;
                    }
                    p();
                } else {
                    singleFieldBuilderV3.mergeFrom(pProperties);
                }
                return this;
            }

            public Builder setKey1(int i2) {
                this.key1_ = i2;
                p();
                return this;
            }

            public Builder setKey2(int i2) {
                this.key2_ = i2;
                p();
                return this;
            }

            public Builder setKey3(int i2) {
                this.key3_ = i2;
                p();
                return this;
            }

            public Builder setParent(PProperties pProperties) {
                SingleFieldBuilderV3<PProperties, PProperties.Builder, PPropertiesOrBuilder> singleFieldBuilderV3 = this.parentBuilder_;
                if (singleFieldBuilderV3 == null) {
                    pProperties.getClass();
                    this.parent_ = pProperties;
                    p();
                } else {
                    singleFieldBuilderV3.setMessage(pProperties);
                }
                return this;
            }

            public Builder setProgram1(String str) {
                str.getClass();
                this.program1_ = str;
                p();
                return this;
            }

            public Builder setProgram1Bytes(ByteString byteString) throws IllegalArgumentException {
                byteString.getClass();
                AbstractMessageLite.checkByteStringIsUtf8(byteString);
                this.program1_ = byteString;
                p();
                return this;
            }

            public Builder setProgram2(String str) {
                str.getClass();
                this.program2_ = str;
                p();
                return this;
            }

            public Builder setProgram2Bytes(ByteString byteString) throws IllegalArgumentException {
                byteString.getClass();
                AbstractMessageLite.checkByteStringIsUtf8(byteString);
                this.program2_ = byteString;
                p();
                return this;
            }

            public Builder setProgram3(String str) {
                str.getClass();
                this.program3_ = str;
                p();
                return this;
            }

            public Builder setProgram3Bytes(ByteString byteString) throws IllegalArgumentException {
                byteString.getClass();
                AbstractMessageLite.checkByteStringIsUtf8(byteString);
                this.program3_ = byteString;
                p();
                return this;
            }

            private Builder() {
                this.program1_ = "";
                this.program2_ = "";
                this.program3_ = "";
                maybeForceBuilderInitialization();
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public Builder addRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, Object obj) {
                return (Builder) super.addRepeatedField(fieldDescriptor, obj);
            }

            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
            public PPropertiesTr build() {
                PPropertiesTr pPropertiesTrBuildPartial = buildPartial();
                if (pPropertiesTrBuildPartial.isInitialized()) {
                    return pPropertiesTrBuildPartial;
                }
                throw AbstractMessage.Builder.f(pPropertiesTrBuildPartial);
            }

            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
            public PPropertiesTr buildPartial() {
                PPropertiesTr pPropertiesTr = new PPropertiesTr(this);
                SingleFieldBuilderV3<PProperties, PProperties.Builder, PPropertiesOrBuilder> singleFieldBuilderV3 = this.parentBuilder_;
                if (singleFieldBuilderV3 == null) {
                    pPropertiesTr.parent_ = this.parent_;
                } else {
                    pPropertiesTr.parent_ = (PProperties) singleFieldBuilderV3.build();
                }
                pPropertiesTr.key1_ = this.key1_;
                pPropertiesTr.key2_ = this.key2_;
                pPropertiesTr.key3_ = this.key3_;
                pPropertiesTr.program1_ = this.program1_;
                pPropertiesTr.program2_ = this.program2_;
                pPropertiesTr.program3_ = this.program3_;
                o();
                return pPropertiesTr;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public Builder clearField(Descriptors.FieldDescriptor fieldDescriptor) {
                return (Builder) super.clearField(fieldDescriptor);
            }

            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLiteOrBuilder, com.google.protobuf.MessageOrBuilder
            public PPropertiesTr getDefaultInstanceForType() {
                return PPropertiesTr.getDefaultInstance();
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public Builder setField(Descriptors.FieldDescriptor fieldDescriptor, Object obj) {
                return (Builder) super.setField(fieldDescriptor, obj);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public Builder setRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, int i2, Object obj) {
                return (Builder) super.setRepeatedField(fieldDescriptor, i2, obj);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public final Builder setUnknownFields(UnknownFieldSet unknownFieldSet) {
                return (Builder) super.setUnknownFields(unknownFieldSet);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.Message.Builder
            public Builder clearOneof(Descriptors.OneofDescriptor oneofDescriptor) {
                return (Builder) super.clearOneof(oneofDescriptor);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.Message.Builder
            public final Builder mergeUnknownFields(UnknownFieldSet unknownFieldSet) {
                return (Builder) super.mergeUnknownFields(unknownFieldSet);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
            public Builder clear() {
                super.clear();
                if (this.parentBuilder_ == null) {
                    this.parent_ = null;
                } else {
                    this.parent_ = null;
                    this.parentBuilder_ = null;
                }
                this.key1_ = 0;
                this.key2_ = 0;
                this.key3_ = 0;
                this.program1_ = "";
                this.program2_ = "";
                this.program3_ = "";
                return this;
            }

            public Builder setParent(PProperties.Builder builder) {
                SingleFieldBuilderV3<PProperties, PProperties.Builder, PPropertiesOrBuilder> singleFieldBuilderV3 = this.parentBuilder_;
                if (singleFieldBuilderV3 == null) {
                    this.parent_ = builder.build();
                    p();
                } else {
                    singleFieldBuilderV3.setMessage(builder.build());
                }
                return this;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.AbstractMessageLite.Builder
            /* renamed from: clone */
            public Builder mo65clone() {
                return (Builder) super.mo65clone();
            }

            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.Message.Builder
            public Builder mergeFrom(Message message) {
                if (message instanceof PPropertiesTr) {
                    return mergeFrom((PPropertiesTr) message);
                }
                super.mergeFrom(message);
                return this;
            }

            private Builder(GeneratedMessageV3.BuilderParent builderParent) {
                super(builderParent);
                this.program1_ = "";
                this.program2_ = "";
                this.program3_ = "";
                maybeForceBuilderInitialization();
            }

            public Builder mergeFrom(PPropertiesTr pPropertiesTr) {
                if (pPropertiesTr == PPropertiesTr.getDefaultInstance()) {
                    return this;
                }
                if (pPropertiesTr.hasParent()) {
                    mergeParent(pPropertiesTr.getParent());
                }
                if (pPropertiesTr.getKey1() != 0) {
                    setKey1(pPropertiesTr.getKey1());
                }
                if (pPropertiesTr.getKey2() != 0) {
                    setKey2(pPropertiesTr.getKey2());
                }
                if (pPropertiesTr.getKey3() != 0) {
                    setKey3(pPropertiesTr.getKey3());
                }
                if (!pPropertiesTr.getProgram1().isEmpty()) {
                    this.program1_ = pPropertiesTr.program1_;
                    p();
                }
                if (!pPropertiesTr.getProgram2().isEmpty()) {
                    this.program2_ = pPropertiesTr.program2_;
                    p();
                }
                if (!pPropertiesTr.getProgram3().isEmpty()) {
                    this.program3_ = pPropertiesTr.program3_;
                    p();
                }
                mergeUnknownFields(((GeneratedMessageV3) pPropertiesTr).unknownFields);
                p();
                return this;
            }

            /* JADX WARN: Removed duplicated region for block: B:16:0x0023  */
            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.AbstractMessageLite.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public com.kingsmith.plugins.Protos.PPropertiesTr.Builder mergeFrom(com.google.protobuf.CodedInputStream r3, com.google.protobuf.ExtensionRegistryLite r4) throws java.lang.Throwable {
                /*
                    r2 = this;
                    r0 = 0
                    com.google.protobuf.Parser r1 = com.kingsmith.plugins.Protos.PPropertiesTr.k()     // Catch: java.lang.Throwable -> L11 com.google.protobuf.InvalidProtocolBufferException -> L13
                    java.lang.Object r3 = r1.parsePartialFrom(r3, r4)     // Catch: java.lang.Throwable -> L11 com.google.protobuf.InvalidProtocolBufferException -> L13
                    com.kingsmith.plugins.Protos$PPropertiesTr r3 = (com.kingsmith.plugins.Protos.PPropertiesTr) r3     // Catch: java.lang.Throwable -> L11 com.google.protobuf.InvalidProtocolBufferException -> L13
                    if (r3 == 0) goto L10
                    r2.mergeFrom(r3)
                L10:
                    return r2
                L11:
                    r3 = move-exception
                    goto L21
                L13:
                    r3 = move-exception
                    com.google.protobuf.MessageLite r4 = r3.getUnfinishedMessage()     // Catch: java.lang.Throwable -> L11
                    com.kingsmith.plugins.Protos$PPropertiesTr r4 = (com.kingsmith.plugins.Protos.PPropertiesTr) r4     // Catch: java.lang.Throwable -> L11
                    java.io.IOException r3 = r3.unwrapIOException()     // Catch: java.lang.Throwable -> L1f
                    throw r3     // Catch: java.lang.Throwable -> L1f
                L1f:
                    r3 = move-exception
                    r0 = r4
                L21:
                    if (r0 == 0) goto L26
                    r2.mergeFrom(r0)
                L26:
                    throw r3
                */
                throw new UnsupportedOperationException("Method not decompiled: com.kingsmith.plugins.Protos.PPropertiesTr.Builder.mergeFrom(com.google.protobuf.CodedInputStream, com.google.protobuf.ExtensionRegistryLite):com.kingsmith.plugins.Protos$PPropertiesTr$Builder");
            }
        }

        public static Builder newBuilder(PPropertiesTr pPropertiesTr) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(pPropertiesTr);
        }

        public static PPropertiesTr parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteBuffer, extensionRegistryLite);
        }

        private PPropertiesTr(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
        }

        public static PPropertiesTr parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (PPropertiesTr) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, inputStream, extensionRegistryLite);
        }

        public static PPropertiesTr parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString);
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLiteOrBuilder, com.google.protobuf.MessageOrBuilder
        public PPropertiesTr getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite, com.google.protobuf.Message
        public Builder toBuilder() {
            return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
        }

        public static PPropertiesTr parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString, extensionRegistryLite);
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite, com.google.protobuf.Message
        public Builder newBuilderForType() {
            return newBuilder();
        }

        private PPropertiesTr() {
            this.memoizedIsInitialized = (byte) -1;
            this.program1_ = "";
            this.program2_ = "";
            this.program3_ = "";
        }

        public static PPropertiesTr parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.protobuf.GeneratedMessageV3
        public Builder newBuilderForType(GeneratedMessageV3.BuilderParent builderParent) {
            return new Builder(builderParent);
        }

        public static PPropertiesTr parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr, extensionRegistryLite);
        }

        public static PPropertiesTr parseFrom(InputStream inputStream) throws IOException {
            return (PPropertiesTr) GeneratedMessageV3.parseWithIOException(PARSER, inputStream);
        }

        public static PPropertiesTr parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (PPropertiesTr) GeneratedMessageV3.parseWithIOException(PARSER, inputStream, extensionRegistryLite);
        }

        private PPropertiesTr(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            this();
            extensionRegistryLite.getClass();
            UnknownFieldSet.Builder builderNewBuilder = UnknownFieldSet.newBuilder();
            boolean z2 = false;
            while (!z2) {
                try {
                    try {
                        int tag = codedInputStream.readTag();
                        if (tag != 0) {
                            if (tag == 10) {
                                PProperties pProperties = this.parent_;
                                PProperties.Builder builder = pProperties != null ? pProperties.toBuilder() : null;
                                PProperties pProperties2 = (PProperties) codedInputStream.readMessage(PProperties.parser(), extensionRegistryLite);
                                this.parent_ = pProperties2;
                                if (builder != null) {
                                    builder.mergeFrom(pProperties2);
                                    this.parent_ = builder.buildPartial();
                                }
                            } else if (tag == 16) {
                                this.key1_ = codedInputStream.readInt32();
                            } else if (tag == 24) {
                                this.key2_ = codedInputStream.readInt32();
                            } else if (tag == 32) {
                                this.key3_ = codedInputStream.readInt32();
                            } else if (tag == 42) {
                                this.program1_ = codedInputStream.readStringRequireUtf8();
                            } else if (tag == 50) {
                                this.program2_ = codedInputStream.readStringRequireUtf8();
                            } else if (tag != 58) {
                                if (!parseUnknownField(codedInputStream, builderNewBuilder, extensionRegistryLite, tag)) {
                                }
                            } else {
                                this.program3_ = codedInputStream.readStringRequireUtf8();
                            }
                        }
                        z2 = true;
                    } catch (InvalidProtocolBufferException e2) {
                        throw e2.setUnfinishedMessage(this);
                    } catch (IOException e3) {
                        throw new InvalidProtocolBufferException(e3).setUnfinishedMessage(this);
                    }
                } catch (Throwable th) {
                    this.unknownFields = builderNewBuilder.build();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            this.unknownFields = builderNewBuilder.build();
            makeExtensionsImmutable();
        }

        public static PPropertiesTr parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (PPropertiesTr) GeneratedMessageV3.parseWithIOException(PARSER, codedInputStream);
        }

        public static PPropertiesTr parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (PPropertiesTr) GeneratedMessageV3.parseWithIOException(PARSER, codedInputStream, extensionRegistryLite);
        }
    }

    public interface PPropertiesTrOrBuilder extends MessageOrBuilder {
        int getKey1();

        int getKey2();

        int getKey3();

        PProperties getParent();

        PPropertiesOrBuilder getParentOrBuilder();

        String getProgram1();

        ByteString getProgram1Bytes();

        String getProgram2();

        ByteString getProgram2Bytes();

        String getProgram3();

        ByteString getProgram3Bytes();

        boolean hasParent();
    }

    public static final class PPropertiesWp extends GeneratedMessageV3 implements PPropertiesWpOrBuilder {
        public static final int DISP_FIELD_NUMBER = 7;
        public static final int GOAL_TYPE_FIELD_NUMBER = 3;
        public static final int GOAL_VALUE_FIELD_NUMBER = 4;
        public static final int INITIAL_FIELD_NUMBER = 8;
        public static final int MAX_W_FIELD_NUMBER = 11;
        public static final int MCU_VERSION_FIELD_NUMBER = 9;
        public static final int PARENT_FIELD_NUMBER = 1;
        public static final int SENSITIVITY_FIELD_NUMBER = 2;
        public static final int STARTUP_TYPE_FIELD_NUMBER = 6;
        public static final int START_SPEED_FIELD_NUMBER = 5;
        public static final int UNIT_FIELD_NUMBER = 10;
        private static final long serialVersionUID = 0;
        private int disp_;
        private int goalType_;
        private int goalValue_;
        private int initial_;
        private double maxW_;
        private volatile Object mcuVersion_;
        private byte memoizedIsInitialized;
        private PProperties parent_;
        private int sensitivity_;
        private double startSpeed_;
        private int startupType_;
        private int unit_;
        private static final PPropertiesWp DEFAULT_INSTANCE = new PPropertiesWp();
        private static final Parser<PPropertiesWp> PARSER = new AbstractParser<PPropertiesWp>() { // from class: com.kingsmith.plugins.Protos.PPropertiesWp.1
            @Override // com.google.protobuf.Parser
            public PPropertiesWp parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new PPropertiesWp(codedInputStream, extensionRegistryLite);
            }
        };

        public static PPropertiesWp getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return Protos.internal_static_PPropertiesWp_descriptor;
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static PPropertiesWp parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (PPropertiesWp) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, inputStream);
        }

        public static PPropertiesWp parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteBuffer);
        }

        public static Parser<PPropertiesWp> parser() {
            return PARSER;
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.Message
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof PPropertiesWp)) {
                return super.equals(obj);
            }
            PPropertiesWp pPropertiesWp = (PPropertiesWp) obj;
            if (hasParent() != pPropertiesWp.hasParent()) {
                return false;
            }
            return (!hasParent() || getParent().equals(pPropertiesWp.getParent())) && getSensitivity() == pPropertiesWp.getSensitivity() && getGoalType() == pPropertiesWp.getGoalType() && getGoalValue() == pPropertiesWp.getGoalValue() && Double.doubleToLongBits(getStartSpeed()) == Double.doubleToLongBits(pPropertiesWp.getStartSpeed()) && getStartupType() == pPropertiesWp.getStartupType() && getDisp() == pPropertiesWp.getDisp() && getInitial() == pPropertiesWp.getInitial() && getMcuVersion().equals(pPropertiesWp.getMcuVersion()) && getUnit() == pPropertiesWp.getUnit() && Double.doubleToLongBits(getMaxW()) == Double.doubleToLongBits(pPropertiesWp.getMaxW()) && this.unknownFields.equals(pPropertiesWp.unknownFields);
        }

        @Override // com.kingsmith.plugins.Protos.PPropertiesWpOrBuilder
        public int getDisp() {
            return this.disp_;
        }

        @Override // com.kingsmith.plugins.Protos.PPropertiesWpOrBuilder
        public int getGoalType() {
            return this.goalType_;
        }

        @Override // com.kingsmith.plugins.Protos.PPropertiesWpOrBuilder
        public int getGoalValue() {
            return this.goalValue_;
        }

        @Override // com.kingsmith.plugins.Protos.PPropertiesWpOrBuilder
        public int getInitial() {
            return this.initial_;
        }

        @Override // com.kingsmith.plugins.Protos.PPropertiesWpOrBuilder
        public double getMaxW() {
            return this.maxW_;
        }

        @Override // com.kingsmith.plugins.Protos.PPropertiesWpOrBuilder
        public String getMcuVersion() {
            Object obj = this.mcuVersion_;
            if (obj instanceof String) {
                return (String) obj;
            }
            String stringUtf8 = ((ByteString) obj).toStringUtf8();
            this.mcuVersion_ = stringUtf8;
            return stringUtf8;
        }

        @Override // com.kingsmith.plugins.Protos.PPropertiesWpOrBuilder
        public ByteString getMcuVersionBytes() {
            Object obj = this.mcuVersion_;
            if (!(obj instanceof String)) {
                return (ByteString) obj;
            }
            ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
            this.mcuVersion_ = byteStringCopyFromUtf8;
            return byteStringCopyFromUtf8;
        }

        @Override // com.kingsmith.plugins.Protos.PPropertiesWpOrBuilder
        public PProperties getParent() {
            PProperties pProperties = this.parent_;
            return pProperties == null ? PProperties.getDefaultInstance() : pProperties;
        }

        @Override // com.kingsmith.plugins.Protos.PPropertiesWpOrBuilder
        public PPropertiesOrBuilder getParentOrBuilder() {
            return getParent();
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.MessageLite, com.google.protobuf.Message
        public Parser<PPropertiesWp> getParserForType() {
            return PARSER;
        }

        @Override // com.kingsmith.plugins.Protos.PPropertiesWpOrBuilder
        public int getSensitivity() {
            return this.sensitivity_;
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite
        public int getSerializedSize() {
            int i2 = this.memoizedSize;
            if (i2 != -1) {
                return i2;
            }
            int iComputeMessageSize = this.parent_ != null ? CodedOutputStream.computeMessageSize(1, getParent()) : 0;
            int i3 = this.sensitivity_;
            if (i3 != 0) {
                iComputeMessageSize += CodedOutputStream.computeInt32Size(2, i3);
            }
            int i4 = this.goalType_;
            if (i4 != 0) {
                iComputeMessageSize += CodedOutputStream.computeInt32Size(3, i4);
            }
            int i5 = this.goalValue_;
            if (i5 != 0) {
                iComputeMessageSize += CodedOutputStream.computeInt32Size(4, i5);
            }
            double d2 = this.startSpeed_;
            if (d2 != 0.0d) {
                iComputeMessageSize += CodedOutputStream.computeDoubleSize(5, d2);
            }
            int i6 = this.startupType_;
            if (i6 != 0) {
                iComputeMessageSize += CodedOutputStream.computeInt32Size(6, i6);
            }
            int i7 = this.disp_;
            if (i7 != 0) {
                iComputeMessageSize += CodedOutputStream.computeInt32Size(7, i7);
            }
            int i8 = this.initial_;
            if (i8 != 0) {
                iComputeMessageSize += CodedOutputStream.computeInt32Size(8, i8);
            }
            if (!getMcuVersionBytes().isEmpty()) {
                iComputeMessageSize += GeneratedMessageV3.computeStringSize(9, this.mcuVersion_);
            }
            int i9 = this.unit_;
            if (i9 != 0) {
                iComputeMessageSize += CodedOutputStream.computeInt32Size(10, i9);
            }
            double d3 = this.maxW_;
            if (d3 != 0.0d) {
                iComputeMessageSize += CodedOutputStream.computeDoubleSize(11, d3);
            }
            int serializedSize = iComputeMessageSize + this.unknownFields.getSerializedSize();
            this.memoizedSize = serializedSize;
            return serializedSize;
        }

        @Override // com.kingsmith.plugins.Protos.PPropertiesWpOrBuilder
        public double getStartSpeed() {
            return this.startSpeed_;
        }

        @Override // com.kingsmith.plugins.Protos.PPropertiesWpOrBuilder
        public int getStartupType() {
            return this.startupType_;
        }

        @Override // com.kingsmith.plugins.Protos.PPropertiesWpOrBuilder
        public int getUnit() {
            return this.unit_;
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.MessageOrBuilder
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        @Override // com.kingsmith.plugins.Protos.PPropertiesWpOrBuilder
        public boolean hasParent() {
            return this.parent_ != null;
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.Message
        public int hashCode() {
            int i2 = this.memoizedHashCode;
            if (i2 != 0) {
                return i2;
            }
            int iHashCode = 779 + getDescriptor().hashCode();
            if (hasParent()) {
                iHashCode = (((iHashCode * 37) + 1) * 53) + getParent().hashCode();
            }
            int sensitivity = (((((((((((((((((((((((((((((((((((((((((iHashCode * 37) + 2) * 53) + getSensitivity()) * 37) + 3) * 53) + getGoalType()) * 37) + 4) * 53) + getGoalValue()) * 37) + 5) * 53) + Internal.hashLong(Double.doubleToLongBits(getStartSpeed()))) * 37) + 6) * 53) + getStartupType()) * 37) + 7) * 53) + getDisp()) * 37) + 8) * 53) + getInitial()) * 37) + 9) * 53) + getMcuVersion().hashCode()) * 37) + 10) * 53) + getUnit()) * 37) + 11) * 53) + Internal.hashLong(Double.doubleToLongBits(getMaxW()))) * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = sensitivity;
            return sensitivity;
        }

        @Override // com.google.protobuf.GeneratedMessageV3
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return Protos.internal_static_PPropertiesWp_fieldAccessorTable.ensureFieldAccessorsInitialized(PPropertiesWp.class, Builder.class);
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            byte b2 = this.memoizedIsInitialized;
            if (b2 == 1) {
                return true;
            }
            if (b2 == 0) {
                return false;
            }
            this.memoizedIsInitialized = (byte) 1;
            return true;
        }

        @Override // com.google.protobuf.GeneratedMessageV3
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unusedPrivateParameter) {
            return new PPropertiesWp();
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            if (this.parent_ != null) {
                codedOutputStream.writeMessage(1, getParent());
            }
            int i2 = this.sensitivity_;
            if (i2 != 0) {
                codedOutputStream.writeInt32(2, i2);
            }
            int i3 = this.goalType_;
            if (i3 != 0) {
                codedOutputStream.writeInt32(3, i3);
            }
            int i4 = this.goalValue_;
            if (i4 != 0) {
                codedOutputStream.writeInt32(4, i4);
            }
            double d2 = this.startSpeed_;
            if (d2 != 0.0d) {
                codedOutputStream.writeDouble(5, d2);
            }
            int i5 = this.startupType_;
            if (i5 != 0) {
                codedOutputStream.writeInt32(6, i5);
            }
            int i6 = this.disp_;
            if (i6 != 0) {
                codedOutputStream.writeInt32(7, i6);
            }
            int i7 = this.initial_;
            if (i7 != 0) {
                codedOutputStream.writeInt32(8, i7);
            }
            if (!getMcuVersionBytes().isEmpty()) {
                GeneratedMessageV3.writeString(codedOutputStream, 9, this.mcuVersion_);
            }
            int i8 = this.unit_;
            if (i8 != 0) {
                codedOutputStream.writeInt32(10, i8);
            }
            double d3 = this.maxW_;
            if (d3 != 0.0d) {
                codedOutputStream.writeDouble(11, d3);
            }
            this.unknownFields.writeTo(codedOutputStream);
        }

        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements PPropertiesWpOrBuilder {
            private int disp_;
            private int goalType_;
            private int goalValue_;
            private int initial_;
            private double maxW_;
            private Object mcuVersion_;
            private SingleFieldBuilderV3<PProperties, PProperties.Builder, PPropertiesOrBuilder> parentBuilder_;
            private PProperties parent_;
            private int sensitivity_;
            private double startSpeed_;
            private int startupType_;
            private int unit_;

            public static final Descriptors.Descriptor getDescriptor() {
                return Protos.internal_static_PPropertiesWp_descriptor;
            }

            private SingleFieldBuilderV3<PProperties, PProperties.Builder, PPropertiesOrBuilder> getParentFieldBuilder() {
                if (this.parentBuilder_ == null) {
                    this.parentBuilder_ = new SingleFieldBuilderV3<>(getParent(), i(), m());
                    this.parent_ = null;
                }
                return this.parentBuilder_;
            }

            private void maybeForceBuilderInitialization() {
                boolean unused = GeneratedMessageV3.alwaysUseFieldBuilders;
            }

            public Builder clearDisp() {
                this.disp_ = 0;
                p();
                return this;
            }

            public Builder clearGoalType() {
                this.goalType_ = 0;
                p();
                return this;
            }

            public Builder clearGoalValue() {
                this.goalValue_ = 0;
                p();
                return this;
            }

            public Builder clearInitial() {
                this.initial_ = 0;
                p();
                return this;
            }

            public Builder clearMaxW() {
                this.maxW_ = 0.0d;
                p();
                return this;
            }

            public Builder clearMcuVersion() {
                this.mcuVersion_ = PPropertiesWp.getDefaultInstance().getMcuVersion();
                p();
                return this;
            }

            public Builder clearParent() {
                if (this.parentBuilder_ == null) {
                    this.parent_ = null;
                    p();
                } else {
                    this.parent_ = null;
                    this.parentBuilder_ = null;
                }
                return this;
            }

            public Builder clearSensitivity() {
                this.sensitivity_ = 0;
                p();
                return this;
            }

            public Builder clearStartSpeed() {
                this.startSpeed_ = 0.0d;
                p();
                return this;
            }

            public Builder clearStartupType() {
                this.startupType_ = 0;
                p();
                return this;
            }

            public Builder clearUnit() {
                this.unit_ = 0;
                p();
                return this;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder, com.google.protobuf.MessageOrBuilder
            public Descriptors.Descriptor getDescriptorForType() {
                return Protos.internal_static_PPropertiesWp_descriptor;
            }

            @Override // com.kingsmith.plugins.Protos.PPropertiesWpOrBuilder
            public int getDisp() {
                return this.disp_;
            }

            @Override // com.kingsmith.plugins.Protos.PPropertiesWpOrBuilder
            public int getGoalType() {
                return this.goalType_;
            }

            @Override // com.kingsmith.plugins.Protos.PPropertiesWpOrBuilder
            public int getGoalValue() {
                return this.goalValue_;
            }

            @Override // com.kingsmith.plugins.Protos.PPropertiesWpOrBuilder
            public int getInitial() {
                return this.initial_;
            }

            @Override // com.kingsmith.plugins.Protos.PPropertiesWpOrBuilder
            public double getMaxW() {
                return this.maxW_;
            }

            @Override // com.kingsmith.plugins.Protos.PPropertiesWpOrBuilder
            public String getMcuVersion() {
                Object obj = this.mcuVersion_;
                if (obj instanceof String) {
                    return (String) obj;
                }
                String stringUtf8 = ((ByteString) obj).toStringUtf8();
                this.mcuVersion_ = stringUtf8;
                return stringUtf8;
            }

            @Override // com.kingsmith.plugins.Protos.PPropertiesWpOrBuilder
            public ByteString getMcuVersionBytes() {
                Object obj = this.mcuVersion_;
                if (!(obj instanceof String)) {
                    return (ByteString) obj;
                }
                ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                this.mcuVersion_ = byteStringCopyFromUtf8;
                return byteStringCopyFromUtf8;
            }

            @Override // com.kingsmith.plugins.Protos.PPropertiesWpOrBuilder
            public PProperties getParent() {
                SingleFieldBuilderV3<PProperties, PProperties.Builder, PPropertiesOrBuilder> singleFieldBuilderV3 = this.parentBuilder_;
                if (singleFieldBuilderV3 != null) {
                    return (PProperties) singleFieldBuilderV3.getMessage();
                }
                PProperties pProperties = this.parent_;
                return pProperties == null ? PProperties.getDefaultInstance() : pProperties;
            }

            public PProperties.Builder getParentBuilder() {
                p();
                return (PProperties.Builder) getParentFieldBuilder().getBuilder();
            }

            @Override // com.kingsmith.plugins.Protos.PPropertiesWpOrBuilder
            public PPropertiesOrBuilder getParentOrBuilder() {
                SingleFieldBuilderV3<PProperties, PProperties.Builder, PPropertiesOrBuilder> singleFieldBuilderV3 = this.parentBuilder_;
                if (singleFieldBuilderV3 != null) {
                    return (PPropertiesOrBuilder) singleFieldBuilderV3.getMessageOrBuilder();
                }
                PProperties pProperties = this.parent_;
                return pProperties == null ? PProperties.getDefaultInstance() : pProperties;
            }

            @Override // com.kingsmith.plugins.Protos.PPropertiesWpOrBuilder
            public int getSensitivity() {
                return this.sensitivity_;
            }

            @Override // com.kingsmith.plugins.Protos.PPropertiesWpOrBuilder
            public double getStartSpeed() {
                return this.startSpeed_;
            }

            @Override // com.kingsmith.plugins.Protos.PPropertiesWpOrBuilder
            public int getStartupType() {
                return this.startupType_;
            }

            @Override // com.kingsmith.plugins.Protos.PPropertiesWpOrBuilder
            public int getUnit() {
                return this.unit_;
            }

            @Override // com.kingsmith.plugins.Protos.PPropertiesWpOrBuilder
            public boolean hasParent() {
                return (this.parentBuilder_ == null && this.parent_ == null) ? false : true;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                return true;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder
            protected GeneratedMessageV3.FieldAccessorTable j() {
                return Protos.internal_static_PPropertiesWp_fieldAccessorTable.ensureFieldAccessorsInitialized(PPropertiesWp.class, Builder.class);
            }

            public Builder mergeParent(PProperties pProperties) {
                SingleFieldBuilderV3<PProperties, PProperties.Builder, PPropertiesOrBuilder> singleFieldBuilderV3 = this.parentBuilder_;
                if (singleFieldBuilderV3 == null) {
                    PProperties pProperties2 = this.parent_;
                    if (pProperties2 != null) {
                        this.parent_ = PProperties.newBuilder(pProperties2).mergeFrom(pProperties).buildPartial();
                    } else {
                        this.parent_ = pProperties;
                    }
                    p();
                } else {
                    singleFieldBuilderV3.mergeFrom(pProperties);
                }
                return this;
            }

            public Builder setDisp(int i2) {
                this.disp_ = i2;
                p();
                return this;
            }

            public Builder setGoalType(int i2) {
                this.goalType_ = i2;
                p();
                return this;
            }

            public Builder setGoalValue(int i2) {
                this.goalValue_ = i2;
                p();
                return this;
            }

            public Builder setInitial(int i2) {
                this.initial_ = i2;
                p();
                return this;
            }

            public Builder setMaxW(double d2) {
                this.maxW_ = d2;
                p();
                return this;
            }

            public Builder setMcuVersion(String str) {
                str.getClass();
                this.mcuVersion_ = str;
                p();
                return this;
            }

            public Builder setMcuVersionBytes(ByteString byteString) throws IllegalArgumentException {
                byteString.getClass();
                AbstractMessageLite.checkByteStringIsUtf8(byteString);
                this.mcuVersion_ = byteString;
                p();
                return this;
            }

            public Builder setParent(PProperties pProperties) {
                SingleFieldBuilderV3<PProperties, PProperties.Builder, PPropertiesOrBuilder> singleFieldBuilderV3 = this.parentBuilder_;
                if (singleFieldBuilderV3 == null) {
                    pProperties.getClass();
                    this.parent_ = pProperties;
                    p();
                } else {
                    singleFieldBuilderV3.setMessage(pProperties);
                }
                return this;
            }

            public Builder setSensitivity(int i2) {
                this.sensitivity_ = i2;
                p();
                return this;
            }

            public Builder setStartSpeed(double d2) {
                this.startSpeed_ = d2;
                p();
                return this;
            }

            public Builder setStartupType(int i2) {
                this.startupType_ = i2;
                p();
                return this;
            }

            public Builder setUnit(int i2) {
                this.unit_ = i2;
                p();
                return this;
            }

            private Builder() {
                this.mcuVersion_ = "";
                maybeForceBuilderInitialization();
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public Builder addRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, Object obj) {
                return (Builder) super.addRepeatedField(fieldDescriptor, obj);
            }

            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
            public PPropertiesWp build() {
                PPropertiesWp pPropertiesWpBuildPartial = buildPartial();
                if (pPropertiesWpBuildPartial.isInitialized()) {
                    return pPropertiesWpBuildPartial;
                }
                throw AbstractMessage.Builder.f(pPropertiesWpBuildPartial);
            }

            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
            public PPropertiesWp buildPartial() {
                PPropertiesWp pPropertiesWp = new PPropertiesWp(this);
                SingleFieldBuilderV3<PProperties, PProperties.Builder, PPropertiesOrBuilder> singleFieldBuilderV3 = this.parentBuilder_;
                if (singleFieldBuilderV3 == null) {
                    pPropertiesWp.parent_ = this.parent_;
                } else {
                    pPropertiesWp.parent_ = (PProperties) singleFieldBuilderV3.build();
                }
                pPropertiesWp.sensitivity_ = this.sensitivity_;
                pPropertiesWp.goalType_ = this.goalType_;
                pPropertiesWp.goalValue_ = this.goalValue_;
                pPropertiesWp.startSpeed_ = this.startSpeed_;
                pPropertiesWp.startupType_ = this.startupType_;
                pPropertiesWp.disp_ = this.disp_;
                pPropertiesWp.initial_ = this.initial_;
                pPropertiesWp.mcuVersion_ = this.mcuVersion_;
                pPropertiesWp.unit_ = this.unit_;
                pPropertiesWp.maxW_ = this.maxW_;
                o();
                return pPropertiesWp;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public Builder clearField(Descriptors.FieldDescriptor fieldDescriptor) {
                return (Builder) super.clearField(fieldDescriptor);
            }

            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLiteOrBuilder, com.google.protobuf.MessageOrBuilder
            public PPropertiesWp getDefaultInstanceForType() {
                return PPropertiesWp.getDefaultInstance();
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public Builder setField(Descriptors.FieldDescriptor fieldDescriptor, Object obj) {
                return (Builder) super.setField(fieldDescriptor, obj);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public Builder setRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, int i2, Object obj) {
                return (Builder) super.setRepeatedField(fieldDescriptor, i2, obj);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public final Builder setUnknownFields(UnknownFieldSet unknownFieldSet) {
                return (Builder) super.setUnknownFields(unknownFieldSet);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.Message.Builder
            public Builder clearOneof(Descriptors.OneofDescriptor oneofDescriptor) {
                return (Builder) super.clearOneof(oneofDescriptor);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.Message.Builder
            public final Builder mergeUnknownFields(UnknownFieldSet unknownFieldSet) {
                return (Builder) super.mergeUnknownFields(unknownFieldSet);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
            public Builder clear() {
                super.clear();
                if (this.parentBuilder_ == null) {
                    this.parent_ = null;
                } else {
                    this.parent_ = null;
                    this.parentBuilder_ = null;
                }
                this.sensitivity_ = 0;
                this.goalType_ = 0;
                this.goalValue_ = 0;
                this.startSpeed_ = 0.0d;
                this.startupType_ = 0;
                this.disp_ = 0;
                this.initial_ = 0;
                this.mcuVersion_ = "";
                this.unit_ = 0;
                this.maxW_ = 0.0d;
                return this;
            }

            private Builder(GeneratedMessageV3.BuilderParent builderParent) {
                super(builderParent);
                this.mcuVersion_ = "";
                maybeForceBuilderInitialization();
            }

            public Builder setParent(PProperties.Builder builder) {
                SingleFieldBuilderV3<PProperties, PProperties.Builder, PPropertiesOrBuilder> singleFieldBuilderV3 = this.parentBuilder_;
                if (singleFieldBuilderV3 == null) {
                    this.parent_ = builder.build();
                    p();
                } else {
                    singleFieldBuilderV3.setMessage(builder.build());
                }
                return this;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.AbstractMessageLite.Builder
            /* renamed from: clone */
            public Builder mo65clone() {
                return (Builder) super.mo65clone();
            }

            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.Message.Builder
            public Builder mergeFrom(Message message) {
                if (message instanceof PPropertiesWp) {
                    return mergeFrom((PPropertiesWp) message);
                }
                super.mergeFrom(message);
                return this;
            }

            public Builder mergeFrom(PPropertiesWp pPropertiesWp) {
                if (pPropertiesWp == PPropertiesWp.getDefaultInstance()) {
                    return this;
                }
                if (pPropertiesWp.hasParent()) {
                    mergeParent(pPropertiesWp.getParent());
                }
                if (pPropertiesWp.getSensitivity() != 0) {
                    setSensitivity(pPropertiesWp.getSensitivity());
                }
                if (pPropertiesWp.getGoalType() != 0) {
                    setGoalType(pPropertiesWp.getGoalType());
                }
                if (pPropertiesWp.getGoalValue() != 0) {
                    setGoalValue(pPropertiesWp.getGoalValue());
                }
                if (pPropertiesWp.getStartSpeed() != 0.0d) {
                    setStartSpeed(pPropertiesWp.getStartSpeed());
                }
                if (pPropertiesWp.getStartupType() != 0) {
                    setStartupType(pPropertiesWp.getStartupType());
                }
                if (pPropertiesWp.getDisp() != 0) {
                    setDisp(pPropertiesWp.getDisp());
                }
                if (pPropertiesWp.getInitial() != 0) {
                    setInitial(pPropertiesWp.getInitial());
                }
                if (!pPropertiesWp.getMcuVersion().isEmpty()) {
                    this.mcuVersion_ = pPropertiesWp.mcuVersion_;
                    p();
                }
                if (pPropertiesWp.getUnit() != 0) {
                    setUnit(pPropertiesWp.getUnit());
                }
                if (pPropertiesWp.getMaxW() != 0.0d) {
                    setMaxW(pPropertiesWp.getMaxW());
                }
                mergeUnknownFields(((GeneratedMessageV3) pPropertiesWp).unknownFields);
                p();
                return this;
            }

            /* JADX WARN: Removed duplicated region for block: B:16:0x0023  */
            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.AbstractMessageLite.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public com.kingsmith.plugins.Protos.PPropertiesWp.Builder mergeFrom(com.google.protobuf.CodedInputStream r3, com.google.protobuf.ExtensionRegistryLite r4) throws java.lang.Throwable {
                /*
                    r2 = this;
                    r0 = 0
                    com.google.protobuf.Parser r1 = com.kingsmith.plugins.Protos.PPropertiesWp.m()     // Catch: java.lang.Throwable -> L11 com.google.protobuf.InvalidProtocolBufferException -> L13
                    java.lang.Object r3 = r1.parsePartialFrom(r3, r4)     // Catch: java.lang.Throwable -> L11 com.google.protobuf.InvalidProtocolBufferException -> L13
                    com.kingsmith.plugins.Protos$PPropertiesWp r3 = (com.kingsmith.plugins.Protos.PPropertiesWp) r3     // Catch: java.lang.Throwable -> L11 com.google.protobuf.InvalidProtocolBufferException -> L13
                    if (r3 == 0) goto L10
                    r2.mergeFrom(r3)
                L10:
                    return r2
                L11:
                    r3 = move-exception
                    goto L21
                L13:
                    r3 = move-exception
                    com.google.protobuf.MessageLite r4 = r3.getUnfinishedMessage()     // Catch: java.lang.Throwable -> L11
                    com.kingsmith.plugins.Protos$PPropertiesWp r4 = (com.kingsmith.plugins.Protos.PPropertiesWp) r4     // Catch: java.lang.Throwable -> L11
                    java.io.IOException r3 = r3.unwrapIOException()     // Catch: java.lang.Throwable -> L1f
                    throw r3     // Catch: java.lang.Throwable -> L1f
                L1f:
                    r3 = move-exception
                    r0 = r4
                L21:
                    if (r0 == 0) goto L26
                    r2.mergeFrom(r0)
                L26:
                    throw r3
                */
                throw new UnsupportedOperationException("Method not decompiled: com.kingsmith.plugins.Protos.PPropertiesWp.Builder.mergeFrom(com.google.protobuf.CodedInputStream, com.google.protobuf.ExtensionRegistryLite):com.kingsmith.plugins.Protos$PPropertiesWp$Builder");
            }
        }

        public static Builder newBuilder(PPropertiesWp pPropertiesWp) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(pPropertiesWp);
        }

        public static PPropertiesWp parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteBuffer, extensionRegistryLite);
        }

        private PPropertiesWp(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
        }

        public static PPropertiesWp parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (PPropertiesWp) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, inputStream, extensionRegistryLite);
        }

        public static PPropertiesWp parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString);
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLiteOrBuilder, com.google.protobuf.MessageOrBuilder
        public PPropertiesWp getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite, com.google.protobuf.Message
        public Builder toBuilder() {
            return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
        }

        public static PPropertiesWp parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString, extensionRegistryLite);
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite, com.google.protobuf.Message
        public Builder newBuilderForType() {
            return newBuilder();
        }

        private PPropertiesWp() {
            this.memoizedIsInitialized = (byte) -1;
            this.mcuVersion_ = "";
        }

        public static PPropertiesWp parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.protobuf.GeneratedMessageV3
        public Builder newBuilderForType(GeneratedMessageV3.BuilderParent builderParent) {
            return new Builder(builderParent);
        }

        public static PPropertiesWp parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr, extensionRegistryLite);
        }

        public static PPropertiesWp parseFrom(InputStream inputStream) throws IOException {
            return (PPropertiesWp) GeneratedMessageV3.parseWithIOException(PARSER, inputStream);
        }

        private PPropertiesWp(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            this();
            extensionRegistryLite.getClass();
            UnknownFieldSet.Builder builderNewBuilder = UnknownFieldSet.newBuilder();
            boolean z2 = false;
            while (!z2) {
                try {
                    try {
                        int tag = codedInputStream.readTag();
                        switch (tag) {
                            case 0:
                                z2 = true;
                            case 10:
                                PProperties pProperties = this.parent_;
                                PProperties.Builder builder = pProperties != null ? pProperties.toBuilder() : null;
                                PProperties pProperties2 = (PProperties) codedInputStream.readMessage(PProperties.parser(), extensionRegistryLite);
                                this.parent_ = pProperties2;
                                if (builder != null) {
                                    builder.mergeFrom(pProperties2);
                                    this.parent_ = builder.buildPartial();
                                }
                            case 16:
                                this.sensitivity_ = codedInputStream.readInt32();
                            case 24:
                                this.goalType_ = codedInputStream.readInt32();
                            case 32:
                                this.goalValue_ = codedInputStream.readInt32();
                            case 41:
                                this.startSpeed_ = codedInputStream.readDouble();
                            case 48:
                                this.startupType_ = codedInputStream.readInt32();
                            case 56:
                                this.disp_ = codedInputStream.readInt32();
                            case 64:
                                this.initial_ = codedInputStream.readInt32();
                            case 74:
                                this.mcuVersion_ = codedInputStream.readStringRequireUtf8();
                            case 80:
                                this.unit_ = codedInputStream.readInt32();
                            case 89:
                                this.maxW_ = codedInputStream.readDouble();
                            default:
                                if (!parseUnknownField(codedInputStream, builderNewBuilder, extensionRegistryLite, tag)) {
                                    z2 = true;
                                }
                        }
                    } catch (InvalidProtocolBufferException e2) {
                        throw e2.setUnfinishedMessage(this);
                    } catch (IOException e3) {
                        throw new InvalidProtocolBufferException(e3).setUnfinishedMessage(this);
                    }
                } catch (Throwable th) {
                    this.unknownFields = builderNewBuilder.build();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            this.unknownFields = builderNewBuilder.build();
            makeExtensionsImmutable();
        }

        public static PPropertiesWp parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (PPropertiesWp) GeneratedMessageV3.parseWithIOException(PARSER, inputStream, extensionRegistryLite);
        }

        public static PPropertiesWp parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (PPropertiesWp) GeneratedMessageV3.parseWithIOException(PARSER, codedInputStream);
        }

        public static PPropertiesWp parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (PPropertiesWp) GeneratedMessageV3.parseWithIOException(PARSER, codedInputStream, extensionRegistryLite);
        }
    }

    public interface PPropertiesWpOrBuilder extends MessageOrBuilder {
        int getDisp();

        int getGoalType();

        int getGoalValue();

        int getInitial();

        double getMaxW();

        String getMcuVersion();

        ByteString getMcuVersionBytes();

        PProperties getParent();

        PPropertiesOrBuilder getParentOrBuilder();

        int getSensitivity();

        double getStartSpeed();

        int getStartupType();

        int getUnit();

        boolean hasParent();
    }

    public static final class PResponse extends GeneratedMessageV3 implements PResponseOrBuilder {
        public static final int CODE_FIELD_NUMBER = 1;
        public static final int MSG_FIELD_NUMBER = 2;
        private static final long serialVersionUID = 0;
        private int code_;
        private byte memoizedIsInitialized;
        private volatile Object msg_;
        private static final PResponse DEFAULT_INSTANCE = new PResponse();
        private static final Parser<PResponse> PARSER = new AbstractParser<PResponse>() { // from class: com.kingsmith.plugins.Protos.PResponse.1
            @Override // com.google.protobuf.Parser
            public PResponse parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new PResponse(codedInputStream, extensionRegistryLite);
            }
        };

        public static PResponse getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return Protos.internal_static_PResponse_descriptor;
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static PResponse parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (PResponse) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, inputStream);
        }

        public static PResponse parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteBuffer);
        }

        public static Parser<PResponse> parser() {
            return PARSER;
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.Message
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof PResponse)) {
                return super.equals(obj);
            }
            PResponse pResponse = (PResponse) obj;
            return getCode() == pResponse.getCode() && getMsg().equals(pResponse.getMsg()) && this.unknownFields.equals(pResponse.unknownFields);
        }

        @Override // com.kingsmith.plugins.Protos.PResponseOrBuilder
        public int getCode() {
            return this.code_;
        }

        @Override // com.kingsmith.plugins.Protos.PResponseOrBuilder
        public String getMsg() {
            Object obj = this.msg_;
            if (obj instanceof String) {
                return (String) obj;
            }
            String stringUtf8 = ((ByteString) obj).toStringUtf8();
            this.msg_ = stringUtf8;
            return stringUtf8;
        }

        @Override // com.kingsmith.plugins.Protos.PResponseOrBuilder
        public ByteString getMsgBytes() {
            Object obj = this.msg_;
            if (!(obj instanceof String)) {
                return (ByteString) obj;
            }
            ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
            this.msg_ = byteStringCopyFromUtf8;
            return byteStringCopyFromUtf8;
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.MessageLite, com.google.protobuf.Message
        public Parser<PResponse> getParserForType() {
            return PARSER;
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite
        public int getSerializedSize() {
            int i2 = this.memoizedSize;
            if (i2 != -1) {
                return i2;
            }
            int i3 = this.code_;
            int iComputeInt32Size = i3 != 0 ? CodedOutputStream.computeInt32Size(1, i3) : 0;
            if (!getMsgBytes().isEmpty()) {
                iComputeInt32Size += GeneratedMessageV3.computeStringSize(2, this.msg_);
            }
            int serializedSize = iComputeInt32Size + this.unknownFields.getSerializedSize();
            this.memoizedSize = serializedSize;
            return serializedSize;
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.MessageOrBuilder
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.Message
        public int hashCode() {
            int i2 = this.memoizedHashCode;
            if (i2 != 0) {
                return i2;
            }
            int iHashCode = ((((((((((779 + getDescriptor().hashCode()) * 37) + 1) * 53) + getCode()) * 37) + 2) * 53) + getMsg().hashCode()) * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = iHashCode;
            return iHashCode;
        }

        @Override // com.google.protobuf.GeneratedMessageV3
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return Protos.internal_static_PResponse_fieldAccessorTable.ensureFieldAccessorsInitialized(PResponse.class, Builder.class);
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            byte b2 = this.memoizedIsInitialized;
            if (b2 == 1) {
                return true;
            }
            if (b2 == 0) {
                return false;
            }
            this.memoizedIsInitialized = (byte) 1;
            return true;
        }

        @Override // com.google.protobuf.GeneratedMessageV3
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unusedPrivateParameter) {
            return new PResponse();
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            int i2 = this.code_;
            if (i2 != 0) {
                codedOutputStream.writeInt32(1, i2);
            }
            if (!getMsgBytes().isEmpty()) {
                GeneratedMessageV3.writeString(codedOutputStream, 2, this.msg_);
            }
            this.unknownFields.writeTo(codedOutputStream);
        }

        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements PResponseOrBuilder {
            private int code_;
            private Object msg_;

            public static final Descriptors.Descriptor getDescriptor() {
                return Protos.internal_static_PResponse_descriptor;
            }

            private void maybeForceBuilderInitialization() {
                boolean unused = GeneratedMessageV3.alwaysUseFieldBuilders;
            }

            public Builder clearCode() {
                this.code_ = 0;
                p();
                return this;
            }

            public Builder clearMsg() {
                this.msg_ = PResponse.getDefaultInstance().getMsg();
                p();
                return this;
            }

            @Override // com.kingsmith.plugins.Protos.PResponseOrBuilder
            public int getCode() {
                return this.code_;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder, com.google.protobuf.MessageOrBuilder
            public Descriptors.Descriptor getDescriptorForType() {
                return Protos.internal_static_PResponse_descriptor;
            }

            @Override // com.kingsmith.plugins.Protos.PResponseOrBuilder
            public String getMsg() {
                Object obj = this.msg_;
                if (obj instanceof String) {
                    return (String) obj;
                }
                String stringUtf8 = ((ByteString) obj).toStringUtf8();
                this.msg_ = stringUtf8;
                return stringUtf8;
            }

            @Override // com.kingsmith.plugins.Protos.PResponseOrBuilder
            public ByteString getMsgBytes() {
                Object obj = this.msg_;
                if (!(obj instanceof String)) {
                    return (ByteString) obj;
                }
                ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                this.msg_ = byteStringCopyFromUtf8;
                return byteStringCopyFromUtf8;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                return true;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder
            protected GeneratedMessageV3.FieldAccessorTable j() {
                return Protos.internal_static_PResponse_fieldAccessorTable.ensureFieldAccessorsInitialized(PResponse.class, Builder.class);
            }

            public Builder setCode(int i2) {
                this.code_ = i2;
                p();
                return this;
            }

            public Builder setMsg(String str) {
                str.getClass();
                this.msg_ = str;
                p();
                return this;
            }

            public Builder setMsgBytes(ByteString byteString) throws IllegalArgumentException {
                byteString.getClass();
                AbstractMessageLite.checkByteStringIsUtf8(byteString);
                this.msg_ = byteString;
                p();
                return this;
            }

            private Builder() {
                this.msg_ = "";
                maybeForceBuilderInitialization();
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public Builder addRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, Object obj) {
                return (Builder) super.addRepeatedField(fieldDescriptor, obj);
            }

            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
            public PResponse build() {
                PResponse pResponseBuildPartial = buildPartial();
                if (pResponseBuildPartial.isInitialized()) {
                    return pResponseBuildPartial;
                }
                throw AbstractMessage.Builder.f(pResponseBuildPartial);
            }

            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
            public PResponse buildPartial() {
                PResponse pResponse = new PResponse(this);
                pResponse.code_ = this.code_;
                pResponse.msg_ = this.msg_;
                o();
                return pResponse;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public Builder clearField(Descriptors.FieldDescriptor fieldDescriptor) {
                return (Builder) super.clearField(fieldDescriptor);
            }

            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLiteOrBuilder, com.google.protobuf.MessageOrBuilder
            public PResponse getDefaultInstanceForType() {
                return PResponse.getDefaultInstance();
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public Builder setField(Descriptors.FieldDescriptor fieldDescriptor, Object obj) {
                return (Builder) super.setField(fieldDescriptor, obj);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public Builder setRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, int i2, Object obj) {
                return (Builder) super.setRepeatedField(fieldDescriptor, i2, obj);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public final Builder setUnknownFields(UnknownFieldSet unknownFieldSet) {
                return (Builder) super.setUnknownFields(unknownFieldSet);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.Message.Builder
            public Builder clearOneof(Descriptors.OneofDescriptor oneofDescriptor) {
                return (Builder) super.clearOneof(oneofDescriptor);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.Message.Builder
            public final Builder mergeUnknownFields(UnknownFieldSet unknownFieldSet) {
                return (Builder) super.mergeUnknownFields(unknownFieldSet);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
            public Builder clear() {
                super.clear();
                this.code_ = 0;
                this.msg_ = "";
                return this;
            }

            private Builder(GeneratedMessageV3.BuilderParent builderParent) {
                super(builderParent);
                this.msg_ = "";
                maybeForceBuilderInitialization();
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.AbstractMessageLite.Builder
            /* renamed from: clone */
            public Builder mo65clone() {
                return (Builder) super.mo65clone();
            }

            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.Message.Builder
            public Builder mergeFrom(Message message) {
                if (message instanceof PResponse) {
                    return mergeFrom((PResponse) message);
                }
                super.mergeFrom(message);
                return this;
            }

            public Builder mergeFrom(PResponse pResponse) {
                if (pResponse == PResponse.getDefaultInstance()) {
                    return this;
                }
                if (pResponse.getCode() != 0) {
                    setCode(pResponse.getCode());
                }
                if (!pResponse.getMsg().isEmpty()) {
                    this.msg_ = pResponse.msg_;
                    p();
                }
                mergeUnknownFields(((GeneratedMessageV3) pResponse).unknownFields);
                p();
                return this;
            }

            /* JADX WARN: Removed duplicated region for block: B:16:0x0023  */
            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.AbstractMessageLite.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public com.kingsmith.plugins.Protos.PResponse.Builder mergeFrom(com.google.protobuf.CodedInputStream r3, com.google.protobuf.ExtensionRegistryLite r4) throws java.lang.Throwable {
                /*
                    r2 = this;
                    r0 = 0
                    com.google.protobuf.Parser r1 = com.kingsmith.plugins.Protos.PResponse.d()     // Catch: java.lang.Throwable -> L11 com.google.protobuf.InvalidProtocolBufferException -> L13
                    java.lang.Object r3 = r1.parsePartialFrom(r3, r4)     // Catch: java.lang.Throwable -> L11 com.google.protobuf.InvalidProtocolBufferException -> L13
                    com.kingsmith.plugins.Protos$PResponse r3 = (com.kingsmith.plugins.Protos.PResponse) r3     // Catch: java.lang.Throwable -> L11 com.google.protobuf.InvalidProtocolBufferException -> L13
                    if (r3 == 0) goto L10
                    r2.mergeFrom(r3)
                L10:
                    return r2
                L11:
                    r3 = move-exception
                    goto L21
                L13:
                    r3 = move-exception
                    com.google.protobuf.MessageLite r4 = r3.getUnfinishedMessage()     // Catch: java.lang.Throwable -> L11
                    com.kingsmith.plugins.Protos$PResponse r4 = (com.kingsmith.plugins.Protos.PResponse) r4     // Catch: java.lang.Throwable -> L11
                    java.io.IOException r3 = r3.unwrapIOException()     // Catch: java.lang.Throwable -> L1f
                    throw r3     // Catch: java.lang.Throwable -> L1f
                L1f:
                    r3 = move-exception
                    r0 = r4
                L21:
                    if (r0 == 0) goto L26
                    r2.mergeFrom(r0)
                L26:
                    throw r3
                */
                throw new UnsupportedOperationException("Method not decompiled: com.kingsmith.plugins.Protos.PResponse.Builder.mergeFrom(com.google.protobuf.CodedInputStream, com.google.protobuf.ExtensionRegistryLite):com.kingsmith.plugins.Protos$PResponse$Builder");
            }
        }

        public static Builder newBuilder(PResponse pResponse) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(pResponse);
        }

        public static PResponse parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteBuffer, extensionRegistryLite);
        }

        private PResponse(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
        }

        public static PResponse parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (PResponse) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, inputStream, extensionRegistryLite);
        }

        public static PResponse parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString);
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLiteOrBuilder, com.google.protobuf.MessageOrBuilder
        public PResponse getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite, com.google.protobuf.Message
        public Builder toBuilder() {
            return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
        }

        public static PResponse parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString, extensionRegistryLite);
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite, com.google.protobuf.Message
        public Builder newBuilderForType() {
            return newBuilder();
        }

        private PResponse() {
            this.memoizedIsInitialized = (byte) -1;
            this.msg_ = "";
        }

        public static PResponse parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.protobuf.GeneratedMessageV3
        public Builder newBuilderForType(GeneratedMessageV3.BuilderParent builderParent) {
            return new Builder(builderParent);
        }

        public static PResponse parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr, extensionRegistryLite);
        }

        public static PResponse parseFrom(InputStream inputStream) throws IOException {
            return (PResponse) GeneratedMessageV3.parseWithIOException(PARSER, inputStream);
        }

        private PResponse(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            this();
            extensionRegistryLite.getClass();
            UnknownFieldSet.Builder builderNewBuilder = UnknownFieldSet.newBuilder();
            boolean z2 = false;
            while (!z2) {
                try {
                    try {
                        int tag = codedInputStream.readTag();
                        if (tag != 0) {
                            if (tag == 8) {
                                this.code_ = codedInputStream.readInt32();
                            } else if (tag != 18) {
                                if (!parseUnknownField(codedInputStream, builderNewBuilder, extensionRegistryLite, tag)) {
                                }
                            } else {
                                this.msg_ = codedInputStream.readStringRequireUtf8();
                            }
                        }
                        z2 = true;
                    } catch (InvalidProtocolBufferException e2) {
                        throw e2.setUnfinishedMessage(this);
                    } catch (IOException e3) {
                        throw new InvalidProtocolBufferException(e3).setUnfinishedMessage(this);
                    }
                } catch (Throwable th) {
                    this.unknownFields = builderNewBuilder.build();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            this.unknownFields = builderNewBuilder.build();
            makeExtensionsImmutable();
        }

        public static PResponse parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (PResponse) GeneratedMessageV3.parseWithIOException(PARSER, inputStream, extensionRegistryLite);
        }

        public static PResponse parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (PResponse) GeneratedMessageV3.parseWithIOException(PARSER, codedInputStream);
        }

        public static PResponse parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (PResponse) GeneratedMessageV3.parseWithIOException(PARSER, codedInputStream, extensionRegistryLite);
        }
    }

    public interface PResponseOrBuilder extends MessageOrBuilder {
        int getCode();

        String getMsg();

        ByteString getMsgBytes();
    }

    public static final class PUser extends GeneratedMessageV3 implements PUserOrBuilder {
        public static final int ID_FIELD_NUMBER = 1;
        public static final int NAME_FIELD_NUMBER = 2;
        private static final long serialVersionUID = 0;
        private volatile Object id_;
        private byte memoizedIsInitialized;
        private volatile Object name_;
        private static final PUser DEFAULT_INSTANCE = new PUser();
        private static final Parser<PUser> PARSER = new AbstractParser<PUser>() { // from class: com.kingsmith.plugins.Protos.PUser.1
            @Override // com.google.protobuf.Parser
            public PUser parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return new PUser(codedInputStream, extensionRegistryLite);
            }
        };

        public static PUser getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static final Descriptors.Descriptor getDescriptor() {
            return Protos.internal_static_PUser_descriptor;
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static PUser parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (PUser) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, inputStream);
        }

        public static PUser parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteBuffer);
        }

        public static Parser<PUser> parser() {
            return PARSER;
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.Message
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof PUser)) {
                return super.equals(obj);
            }
            PUser pUser = (PUser) obj;
            return getId().equals(pUser.getId()) && getName().equals(pUser.getName()) && this.unknownFields.equals(pUser.unknownFields);
        }

        @Override // com.kingsmith.plugins.Protos.PUserOrBuilder
        public String getId() {
            Object obj = this.id_;
            if (obj instanceof String) {
                return (String) obj;
            }
            String stringUtf8 = ((ByteString) obj).toStringUtf8();
            this.id_ = stringUtf8;
            return stringUtf8;
        }

        @Override // com.kingsmith.plugins.Protos.PUserOrBuilder
        public ByteString getIdBytes() {
            Object obj = this.id_;
            if (!(obj instanceof String)) {
                return (ByteString) obj;
            }
            ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
            this.id_ = byteStringCopyFromUtf8;
            return byteStringCopyFromUtf8;
        }

        @Override // com.kingsmith.plugins.Protos.PUserOrBuilder
        public String getName() {
            Object obj = this.name_;
            if (obj instanceof String) {
                return (String) obj;
            }
            String stringUtf8 = ((ByteString) obj).toStringUtf8();
            this.name_ = stringUtf8;
            return stringUtf8;
        }

        @Override // com.kingsmith.plugins.Protos.PUserOrBuilder
        public ByteString getNameBytes() {
            Object obj = this.name_;
            if (!(obj instanceof String)) {
                return (ByteString) obj;
            }
            ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
            this.name_ = byteStringCopyFromUtf8;
            return byteStringCopyFromUtf8;
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.MessageLite, com.google.protobuf.Message
        public Parser<PUser> getParserForType() {
            return PARSER;
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite
        public int getSerializedSize() {
            int i2 = this.memoizedSize;
            if (i2 != -1) {
                return i2;
            }
            int iComputeStringSize = !getIdBytes().isEmpty() ? GeneratedMessageV3.computeStringSize(1, this.id_) : 0;
            if (!getNameBytes().isEmpty()) {
                iComputeStringSize += GeneratedMessageV3.computeStringSize(2, this.name_);
            }
            int serializedSize = iComputeStringSize + this.unknownFields.getSerializedSize();
            this.memoizedSize = serializedSize;
            return serializedSize;
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.MessageOrBuilder
        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.Message
        public int hashCode() {
            int i2 = this.memoizedHashCode;
            if (i2 != 0) {
                return i2;
            }
            int iHashCode = ((((((((((779 + getDescriptor().hashCode()) * 37) + 1) * 53) + getId().hashCode()) * 37) + 2) * 53) + getName().hashCode()) * 29) + this.unknownFields.hashCode();
            this.memoizedHashCode = iHashCode;
            return iHashCode;
        }

        @Override // com.google.protobuf.GeneratedMessageV3
        protected GeneratedMessageV3.FieldAccessorTable internalGetFieldAccessorTable() {
            return Protos.internal_static_PUser_fieldAccessorTable.ensureFieldAccessorsInitialized(PUser.class, Builder.class);
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            byte b2 = this.memoizedIsInitialized;
            if (b2 == 1) {
                return true;
            }
            if (b2 == 0) {
                return false;
            }
            this.memoizedIsInitialized = (byte) 1;
            return true;
        }

        @Override // com.google.protobuf.GeneratedMessageV3
        protected Object newInstance(GeneratedMessageV3.UnusedPrivateParameter unusedPrivateParameter) {
            return new PUser();
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            if (!getIdBytes().isEmpty()) {
                GeneratedMessageV3.writeString(codedOutputStream, 1, this.id_);
            }
            if (!getNameBytes().isEmpty()) {
                GeneratedMessageV3.writeString(codedOutputStream, 2, this.name_);
            }
            this.unknownFields.writeTo(codedOutputStream);
        }

        public static final class Builder extends GeneratedMessageV3.Builder<Builder> implements PUserOrBuilder {
            private Object id_;
            private Object name_;

            public static final Descriptors.Descriptor getDescriptor() {
                return Protos.internal_static_PUser_descriptor;
            }

            private void maybeForceBuilderInitialization() {
                boolean unused = GeneratedMessageV3.alwaysUseFieldBuilders;
            }

            public Builder clearId() {
                this.id_ = PUser.getDefaultInstance().getId();
                p();
                return this;
            }

            public Builder clearName() {
                this.name_ = PUser.getDefaultInstance().getName();
                p();
                return this;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder, com.google.protobuf.MessageOrBuilder
            public Descriptors.Descriptor getDescriptorForType() {
                return Protos.internal_static_PUser_descriptor;
            }

            @Override // com.kingsmith.plugins.Protos.PUserOrBuilder
            public String getId() {
                Object obj = this.id_;
                if (obj instanceof String) {
                    return (String) obj;
                }
                String stringUtf8 = ((ByteString) obj).toStringUtf8();
                this.id_ = stringUtf8;
                return stringUtf8;
            }

            @Override // com.kingsmith.plugins.Protos.PUserOrBuilder
            public ByteString getIdBytes() {
                Object obj = this.id_;
                if (!(obj instanceof String)) {
                    return (ByteString) obj;
                }
                ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                this.id_ = byteStringCopyFromUtf8;
                return byteStringCopyFromUtf8;
            }

            @Override // com.kingsmith.plugins.Protos.PUserOrBuilder
            public String getName() {
                Object obj = this.name_;
                if (obj instanceof String) {
                    return (String) obj;
                }
                String stringUtf8 = ((ByteString) obj).toStringUtf8();
                this.name_ = stringUtf8;
                return stringUtf8;
            }

            @Override // com.kingsmith.plugins.Protos.PUserOrBuilder
            public ByteString getNameBytes() {
                Object obj = this.name_;
                if (!(obj instanceof String)) {
                    return (ByteString) obj;
                }
                ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8((String) obj);
                this.name_ = byteStringCopyFromUtf8;
                return byteStringCopyFromUtf8;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.MessageLiteOrBuilder
            public final boolean isInitialized() {
                return true;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder
            protected GeneratedMessageV3.FieldAccessorTable j() {
                return Protos.internal_static_PUser_fieldAccessorTable.ensureFieldAccessorsInitialized(PUser.class, Builder.class);
            }

            public Builder setId(String str) {
                str.getClass();
                this.id_ = str;
                p();
                return this;
            }

            public Builder setIdBytes(ByteString byteString) throws IllegalArgumentException {
                byteString.getClass();
                AbstractMessageLite.checkByteStringIsUtf8(byteString);
                this.id_ = byteString;
                p();
                return this;
            }

            public Builder setName(String str) {
                str.getClass();
                this.name_ = str;
                p();
                return this;
            }

            public Builder setNameBytes(ByteString byteString) throws IllegalArgumentException {
                byteString.getClass();
                AbstractMessageLite.checkByteStringIsUtf8(byteString);
                this.name_ = byteString;
                p();
                return this;
            }

            private Builder() {
                this.id_ = "";
                this.name_ = "";
                maybeForceBuilderInitialization();
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public Builder addRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, Object obj) {
                return (Builder) super.addRepeatedField(fieldDescriptor, obj);
            }

            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
            public PUser build() {
                PUser pUserBuildPartial = buildPartial();
                if (pUserBuildPartial.isInitialized()) {
                    return pUserBuildPartial;
                }
                throw AbstractMessage.Builder.f(pUserBuildPartial);
            }

            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
            public PUser buildPartial() {
                PUser pUser = new PUser(this);
                pUser.id_ = this.id_;
                pUser.name_ = this.name_;
                o();
                return pUser;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public Builder clearField(Descriptors.FieldDescriptor fieldDescriptor) {
                return (Builder) super.clearField(fieldDescriptor);
            }

            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLiteOrBuilder, com.google.protobuf.MessageOrBuilder
            public PUser getDefaultInstanceForType() {
                return PUser.getDefaultInstance();
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public Builder setField(Descriptors.FieldDescriptor fieldDescriptor, Object obj) {
                return (Builder) super.setField(fieldDescriptor, obj);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public Builder setRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, int i2, Object obj) {
                return (Builder) super.setRepeatedField(fieldDescriptor, i2, obj);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
            public final Builder setUnknownFields(UnknownFieldSet unknownFieldSet) {
                return (Builder) super.setUnknownFields(unknownFieldSet);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.Message.Builder
            public Builder clearOneof(Descriptors.OneofDescriptor oneofDescriptor) {
                return (Builder) super.clearOneof(oneofDescriptor);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.Message.Builder
            public final Builder mergeUnknownFields(UnknownFieldSet unknownFieldSet) {
                return (Builder) super.mergeUnknownFields(unknownFieldSet);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
            public Builder clear() {
                super.clear();
                this.id_ = "";
                this.name_ = "";
                return this;
            }

            private Builder(GeneratedMessageV3.BuilderParent builderParent) {
                super(builderParent);
                this.id_ = "";
                this.name_ = "";
                maybeForceBuilderInitialization();
            }

            @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.AbstractMessageLite.Builder
            /* renamed from: clone */
            public Builder mo65clone() {
                return (Builder) super.mo65clone();
            }

            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.Message.Builder
            public Builder mergeFrom(Message message) {
                if (message instanceof PUser) {
                    return mergeFrom((PUser) message);
                }
                super.mergeFrom(message);
                return this;
            }

            public Builder mergeFrom(PUser pUser) {
                if (pUser == PUser.getDefaultInstance()) {
                    return this;
                }
                if (!pUser.getId().isEmpty()) {
                    this.id_ = pUser.id_;
                    p();
                }
                if (!pUser.getName().isEmpty()) {
                    this.name_ = pUser.name_;
                    p();
                }
                mergeUnknownFields(((GeneratedMessageV3) pUser).unknownFields);
                p();
                return this;
            }

            /* JADX WARN: Removed duplicated region for block: B:16:0x0023  */
            @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.AbstractMessageLite.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public com.kingsmith.plugins.Protos.PUser.Builder mergeFrom(com.google.protobuf.CodedInputStream r3, com.google.protobuf.ExtensionRegistryLite r4) throws java.lang.Throwable {
                /*
                    r2 = this;
                    r0 = 0
                    com.google.protobuf.Parser r1 = com.kingsmith.plugins.Protos.PUser.e()     // Catch: java.lang.Throwable -> L11 com.google.protobuf.InvalidProtocolBufferException -> L13
                    java.lang.Object r3 = r1.parsePartialFrom(r3, r4)     // Catch: java.lang.Throwable -> L11 com.google.protobuf.InvalidProtocolBufferException -> L13
                    com.kingsmith.plugins.Protos$PUser r3 = (com.kingsmith.plugins.Protos.PUser) r3     // Catch: java.lang.Throwable -> L11 com.google.protobuf.InvalidProtocolBufferException -> L13
                    if (r3 == 0) goto L10
                    r2.mergeFrom(r3)
                L10:
                    return r2
                L11:
                    r3 = move-exception
                    goto L21
                L13:
                    r3 = move-exception
                    com.google.protobuf.MessageLite r4 = r3.getUnfinishedMessage()     // Catch: java.lang.Throwable -> L11
                    com.kingsmith.plugins.Protos$PUser r4 = (com.kingsmith.plugins.Protos.PUser) r4     // Catch: java.lang.Throwable -> L11
                    java.io.IOException r3 = r3.unwrapIOException()     // Catch: java.lang.Throwable -> L1f
                    throw r3     // Catch: java.lang.Throwable -> L1f
                L1f:
                    r3 = move-exception
                    r0 = r4
                L21:
                    if (r0 == 0) goto L26
                    r2.mergeFrom(r0)
                L26:
                    throw r3
                */
                throw new UnsupportedOperationException("Method not decompiled: com.kingsmith.plugins.Protos.PUser.Builder.mergeFrom(com.google.protobuf.CodedInputStream, com.google.protobuf.ExtensionRegistryLite):com.kingsmith.plugins.Protos$PUser$Builder");
            }
        }

        public static Builder newBuilder(PUser pUser) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(pUser);
        }

        public static PUser parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteBuffer, extensionRegistryLite);
        }

        private PUser(GeneratedMessageV3.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
        }

        public static PUser parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (PUser) GeneratedMessageV3.parseDelimitedWithIOException(PARSER, inputStream, extensionRegistryLite);
        }

        public static PUser parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString);
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLiteOrBuilder, com.google.protobuf.MessageOrBuilder
        public PUser getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite, com.google.protobuf.Message
        public Builder toBuilder() {
            return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
        }

        public static PUser parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(byteString, extensionRegistryLite);
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite, com.google.protobuf.Message
        public Builder newBuilderForType() {
            return newBuilder();
        }

        private PUser() {
            this.memoizedIsInitialized = (byte) -1;
            this.id_ = "";
            this.name_ = "";
        }

        public static PUser parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.protobuf.GeneratedMessageV3
        public Builder newBuilderForType(GeneratedMessageV3.BuilderParent builderParent) {
            return new Builder(builderParent);
        }

        public static PUser parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return PARSER.parseFrom(bArr, extensionRegistryLite);
        }

        public static PUser parseFrom(InputStream inputStream) throws IOException {
            return (PUser) GeneratedMessageV3.parseWithIOException(PARSER, inputStream);
        }

        private PUser(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            this();
            extensionRegistryLite.getClass();
            UnknownFieldSet.Builder builderNewBuilder = UnknownFieldSet.newBuilder();
            boolean z2 = false;
            while (!z2) {
                try {
                    try {
                        int tag = codedInputStream.readTag();
                        if (tag != 0) {
                            if (tag == 10) {
                                this.id_ = codedInputStream.readStringRequireUtf8();
                            } else if (tag != 18) {
                                if (!parseUnknownField(codedInputStream, builderNewBuilder, extensionRegistryLite, tag)) {
                                }
                            } else {
                                this.name_ = codedInputStream.readStringRequireUtf8();
                            }
                        }
                        z2 = true;
                    } catch (InvalidProtocolBufferException e2) {
                        throw e2.setUnfinishedMessage(this);
                    } catch (IOException e3) {
                        throw new InvalidProtocolBufferException(e3).setUnfinishedMessage(this);
                    }
                } catch (Throwable th) {
                    this.unknownFields = builderNewBuilder.build();
                    makeExtensionsImmutable();
                    throw th;
                }
            }
            this.unknownFields = builderNewBuilder.build();
            makeExtensionsImmutable();
        }

        public static PUser parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (PUser) GeneratedMessageV3.parseWithIOException(PARSER, inputStream, extensionRegistryLite);
        }

        public static PUser parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (PUser) GeneratedMessageV3.parseWithIOException(PARSER, codedInputStream);
        }

        public static PUser parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (PUser) GeneratedMessageV3.parseWithIOException(PARSER, codedInputStream, extensionRegistryLite);
        }
    }

    public interface PUserOrBuilder extends MessageOrBuilder {
        String getId();

        ByteString getIdBytes();

        String getName();

        ByteString getNameBytes();
    }

    static {
        Descriptors.Descriptor descriptor2 = getDescriptor().getMessageTypes().get(0);
        internal_static_Int32Value_descriptor = descriptor2;
        internal_static_Int32Value_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(descriptor2, new String[]{"Value"});
        Descriptors.Descriptor descriptor3 = getDescriptor().getMessageTypes().get(1);
        internal_static_Env_descriptor = descriptor3;
        internal_static_Env_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(descriptor3, new String[]{"AppKey", "ProductKey"});
        Descriptors.Descriptor descriptor4 = getDescriptor().getMessageTypes().get(2);
        internal_static_PUser_descriptor = descriptor4;
        internal_static_PUser_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(descriptor4, new String[]{"Id", "Name"});
        Descriptors.Descriptor descriptor5 = getDescriptor().getMessageTypes().get(3);
        internal_static_PDevice_descriptor = descriptor5;
        internal_static_PDevice_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(descriptor5, new String[]{"Name", ExifInterface.TAG_MODEL, "NetType", "Status", "Owned", "IotId"});
        Descriptors.Descriptor descriptor6 = getDescriptor().getMessageTypes().get(4);
        internal_static_ListOfDeviceInfo_descriptor = descriptor6;
        internal_static_ListOfDeviceInfo_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(descriptor6, new String[]{"Device"});
        Descriptors.Descriptor descriptor7 = getDescriptor().getMessageTypes().get(5);
        internal_static_ConnectConfigResponse_descriptor = descriptor7;
        internal_static_ConnectConfigResponse_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(descriptor7, new String[]{"ProductKey", "DeviceName"});
        Descriptors.Descriptor descriptor8 = getDescriptor().getMessageTypes().get(6);
        internal_static_PResponse_descriptor = descriptor8;
        internal_static_PResponse_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(descriptor8, new String[]{"Code", "Msg"});
        Descriptors.Descriptor descriptor9 = getDescriptor().getMessageTypes().get(7);
        internal_static_PAction_descriptor = descriptor9;
        internal_static_PAction_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(descriptor9, new String[]{"Action", "Value"});
        Descriptors.Descriptor descriptor10 = getDescriptor().getMessageTypes().get(8);
        internal_static_PMotion_descriptor = descriptor10;
        internal_static_PMotion_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(descriptor10, new String[]{RtspHeaders.SPEED, "Dist", "Step", "Time", "Cal", "Spm", "ButtonId", "State", "Mode", "ChildLock", "Power", "Flod", "MotionFinish", "Code"});
        Descriptors.Descriptor descriptor11 = getDescriptor().getMessageTypes().get(9);
        internal_static_PProperties_descriptor = descriptor11;
        internal_static_PProperties_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(descriptor11, new String[]{"Max", "ChildLock", "Bssid", "Code"});
        Descriptors.Descriptor descriptor12 = getDescriptor().getMessageTypes().get(10);
        internal_static_PPropertiesWp_descriptor = descriptor12;
        internal_static_PPropertiesWp_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(descriptor12, new String[]{"Parent", "Sensitivity", "GoalType", "GoalValue", "StartSpeed", "StartupType", "Disp", "Initial", "McuVersion", "Unit", "MaxW"});
        Descriptors.Descriptor descriptor13 = getDescriptor().getMessageTypes().get(11);
        internal_static_PPropertiesTr_descriptor = descriptor13;
        internal_static_PPropertiesTr_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(descriptor13, new String[]{"Parent", "Key1", "Key2", "Key3", "Program1", "Program2", "Program3"});
        Descriptors.Descriptor descriptor14 = getDescriptor().getMessageTypes().get(12);
        internal_static_POTAStatus_descriptor = descriptor14;
        internal_static_POTAStatus_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(descriptor14, new String[]{"Progress", "Status"});
        Descriptors.Descriptor descriptor15 = getDescriptor().getMessageTypes().get(13);
        internal_static_POTAFirmware_descriptor = descriptor15;
        internal_static_POTAFirmware_fieldAccessorTable = new GeneratedMessageV3.FieldAccessorTable(descriptor15, new String[]{"CurrentVersion", DetailRect.NEW_VERSION, "Desc"});
    }

    private Protos() {
    }

    public static Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    public static void registerAllExtensions(ExtensionRegistryLite extensionRegistryLite) {
    }

    public static void registerAllExtensions(ExtensionRegistry extensionRegistry) {
        registerAllExtensions((ExtensionRegistryLite) extensionRegistry);
    }
}
