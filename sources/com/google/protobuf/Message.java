package com.google.protobuf;

import com.google.protobuf.Descriptors;
import com.google.protobuf.MessageLite;
import java.io.IOException;
import java.io.InputStream;

@CheckReturnValue
/* loaded from: classes2.dex */
public interface Message extends MessageLite, MessageOrBuilder {

    public interface Builder extends MessageLite.Builder, MessageOrBuilder {
        @CanIgnoreReturnValue
        Builder addRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, Object obj);

        @Override // com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
        Message build();

        /* bridge */ /* synthetic */ MessageLite build();

        @Override // com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
        Message buildPartial();

        /* bridge */ /* synthetic */ MessageLite buildPartial();

        @CanIgnoreReturnValue
        Builder clear();

        @Override // com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
        @CanIgnoreReturnValue
        /* bridge */ /* synthetic */ MessageLite.Builder clear();

        @CanIgnoreReturnValue
        Builder clearField(Descriptors.FieldDescriptor fieldDescriptor);

        @CanIgnoreReturnValue
        Builder clearOneof(Descriptors.OneofDescriptor oneofDescriptor);

        /* renamed from: clone */
        Builder mo65clone();

        @Override // com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
        /* renamed from: clone */
        /* bridge */ /* synthetic */ MessageLite.Builder mo65clone();

        @Override // com.google.protobuf.MessageOrBuilder
        Descriptors.Descriptor getDescriptorForType();

        Builder getFieldBuilder(Descriptors.FieldDescriptor fieldDescriptor);

        Builder getRepeatedFieldBuilder(Descriptors.FieldDescriptor fieldDescriptor, int i2);

        @Override // com.google.protobuf.MessageLite.Builder
        boolean mergeDelimitedFrom(InputStream inputStream) throws IOException;

        @Override // com.google.protobuf.MessageLite.Builder
        boolean mergeDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException;

        @CanIgnoreReturnValue
        Builder mergeFrom(ByteString byteString) throws InvalidProtocolBufferException;

        @CanIgnoreReturnValue
        Builder mergeFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException;

        @CanIgnoreReturnValue
        Builder mergeFrom(CodedInputStream codedInputStream) throws IOException;

        @CanIgnoreReturnValue
        Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException;

        @CanIgnoreReturnValue
        Builder mergeFrom(Message message);

        @CanIgnoreReturnValue
        Builder mergeFrom(InputStream inputStream) throws IOException;

        @CanIgnoreReturnValue
        Builder mergeFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException;

        @CanIgnoreReturnValue
        Builder mergeFrom(byte[] bArr) throws InvalidProtocolBufferException;

        @CanIgnoreReturnValue
        Builder mergeFrom(byte[] bArr, int i2, int i3) throws InvalidProtocolBufferException;

        @CanIgnoreReturnValue
        Builder mergeFrom(byte[] bArr, int i2, int i3, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException;

        @CanIgnoreReturnValue
        Builder mergeFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException;

        @Override // com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
        @CanIgnoreReturnValue
        /* bridge */ /* synthetic */ MessageLite.Builder mergeFrom(ByteString byteString) throws InvalidProtocolBufferException;

        @Override // com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
        @CanIgnoreReturnValue
        /* bridge */ /* synthetic */ MessageLite.Builder mergeFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException;

        @Override // com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
        @CanIgnoreReturnValue
        /* bridge */ /* synthetic */ MessageLite.Builder mergeFrom(CodedInputStream codedInputStream) throws IOException;

        @Override // com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
        @CanIgnoreReturnValue
        /* bridge */ /* synthetic */ MessageLite.Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException;

        @Override // com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
        @CanIgnoreReturnValue
        /* bridge */ /* synthetic */ MessageLite.Builder mergeFrom(InputStream inputStream) throws IOException;

        @Override // com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
        @CanIgnoreReturnValue
        /* bridge */ /* synthetic */ MessageLite.Builder mergeFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException;

        @Override // com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
        @CanIgnoreReturnValue
        /* bridge */ /* synthetic */ MessageLite.Builder mergeFrom(byte[] bArr) throws InvalidProtocolBufferException;

        @Override // com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
        @CanIgnoreReturnValue
        /* bridge */ /* synthetic */ MessageLite.Builder mergeFrom(byte[] bArr, int i2, int i3) throws InvalidProtocolBufferException;

        @Override // com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
        @CanIgnoreReturnValue
        /* bridge */ /* synthetic */ MessageLite.Builder mergeFrom(byte[] bArr, int i2, int i3, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException;

        @Override // com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
        @CanIgnoreReturnValue
        /* bridge */ /* synthetic */ MessageLite.Builder mergeFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException;

        @CanIgnoreReturnValue
        Builder mergeUnknownFields(UnknownFieldSet unknownFieldSet);

        Builder newBuilderForField(Descriptors.FieldDescriptor fieldDescriptor);

        @CanIgnoreReturnValue
        Builder setField(Descriptors.FieldDescriptor fieldDescriptor, Object obj);

        @CanIgnoreReturnValue
        Builder setRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, int i2, Object obj);

        @CanIgnoreReturnValue
        Builder setUnknownFields(UnknownFieldSet unknownFieldSet);
    }

    boolean equals(Object obj);

    Parser<? extends Message> getParserForType();

    int hashCode();

    @Override // com.google.protobuf.MessageLite, com.google.protobuf.Message
    Builder newBuilderForType();

    /* bridge */ /* synthetic */ MessageLite.Builder newBuilderForType();

    @Override // com.google.protobuf.MessageLite, com.google.protobuf.Message
    Builder toBuilder();

    /* bridge */ /* synthetic */ MessageLite.Builder toBuilder();

    String toString();
}
