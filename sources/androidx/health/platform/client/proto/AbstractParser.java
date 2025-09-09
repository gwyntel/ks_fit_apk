package androidx.health.platform.client.proto;

import androidx.health.platform.client.proto.AbstractMessageLite;
import androidx.health.platform.client.proto.MessageLite;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/* loaded from: classes.dex */
public abstract class AbstractParser<MessageType extends MessageLite> implements Parser<MessageType> {
    private static final ExtensionRegistryLite EMPTY_REGISTRY = ExtensionRegistryLite.getEmptyRegistry();

    private MessageType checkMessageInitialized(MessageType messagetype) throws InvalidProtocolBufferException {
        if (messagetype == null || messagetype.isInitialized()) {
            return messagetype;
        }
        throw newUninitializedMessageException(messagetype).asInvalidProtocolBufferException().setUnfinishedMessage(messagetype);
    }

    private UninitializedMessageException newUninitializedMessageException(MessageType messagetype) {
        return messagetype instanceof AbstractMessageLite ? ((AbstractMessageLite) messagetype).e() : new UninitializedMessageException(messagetype);
    }

    @Override // androidx.health.platform.client.proto.Parser
    public MessageType parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (MessageType) checkMessageInitialized(parsePartialDelimitedFrom(inputStream, extensionRegistryLite));
    }

    @Override // androidx.health.platform.client.proto.Parser
    public MessageType parsePartialDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        try {
            int i2 = inputStream.read();
            if (i2 == -1) {
                return null;
            }
            return (MessageType) parsePartialFrom((InputStream) new AbstractMessageLite.Builder.LimitedInputStream(inputStream, CodedInputStream.readRawVarint32(i2, inputStream)), extensionRegistryLite);
        } catch (IOException e2) {
            throw new InvalidProtocolBufferException(e2);
        }
    }

    @Override // androidx.health.platform.client.proto.Parser
    public MessageType parseDelimitedFrom(InputStream inputStream) throws InvalidProtocolBufferException {
        return (MessageType) parseDelimitedFrom(inputStream, EMPTY_REGISTRY);
    }

    @Override // androidx.health.platform.client.proto.Parser
    public MessageType parsePartialDelimitedFrom(InputStream inputStream) throws InvalidProtocolBufferException {
        return (MessageType) parsePartialDelimitedFrom(inputStream, EMPTY_REGISTRY);
    }

    @Override // androidx.health.platform.client.proto.Parser
    public MessageType parsePartialFrom(CodedInputStream codedInputStream) throws InvalidProtocolBufferException {
        return parsePartialFrom(codedInputStream, EMPTY_REGISTRY);
    }

    @Override // androidx.health.platform.client.proto.Parser
    public MessageType parsePartialFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        CodedInputStream codedInputStreamNewCodedInput = byteString.newCodedInput();
        MessageType partialFrom = parsePartialFrom(codedInputStreamNewCodedInput, extensionRegistryLite);
        try {
            codedInputStreamNewCodedInput.checkLastTagWas(0);
            return partialFrom;
        } catch (InvalidProtocolBufferException e2) {
            throw e2.setUnfinishedMessage(partialFrom);
        }
    }

    @Override // androidx.health.platform.client.proto.Parser
    public MessageType parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (MessageType) checkMessageInitialized(parsePartialFrom(codedInputStream, extensionRegistryLite));
    }

    @Override // androidx.health.platform.client.proto.Parser
    public MessageType parseFrom(CodedInputStream codedInputStream) throws InvalidProtocolBufferException {
        return (MessageType) parseFrom(codedInputStream, EMPTY_REGISTRY);
    }

    @Override // androidx.health.platform.client.proto.Parser
    public MessageType parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (MessageType) checkMessageInitialized(parsePartialFrom(byteString, extensionRegistryLite));
    }

    @Override // androidx.health.platform.client.proto.Parser
    public MessageType parsePartialFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return (MessageType) parsePartialFrom(byteString, EMPTY_REGISTRY);
    }

    @Override // androidx.health.platform.client.proto.Parser
    public MessageType parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return (MessageType) parseFrom(byteString, EMPTY_REGISTRY);
    }

    @Override // androidx.health.platform.client.proto.Parser
    public MessageType parsePartialFrom(byte[] bArr, int i2, int i3, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        CodedInputStream codedInputStreamNewInstance = CodedInputStream.newInstance(bArr, i2, i3);
        MessageType partialFrom = parsePartialFrom(codedInputStreamNewInstance, extensionRegistryLite);
        try {
            codedInputStreamNewInstance.checkLastTagWas(0);
            return partialFrom;
        } catch (InvalidProtocolBufferException e2) {
            throw e2.setUnfinishedMessage(partialFrom);
        }
    }

    @Override // androidx.health.platform.client.proto.Parser
    public MessageType parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        CodedInputStream codedInputStreamNewInstance = CodedInputStream.newInstance(byteBuffer);
        MessageType partialFrom = parsePartialFrom(codedInputStreamNewInstance, extensionRegistryLite);
        try {
            codedInputStreamNewInstance.checkLastTagWas(0);
            return (MessageType) checkMessageInitialized(partialFrom);
        } catch (InvalidProtocolBufferException e2) {
            throw e2.setUnfinishedMessage(partialFrom);
        }
    }

    @Override // androidx.health.platform.client.proto.Parser
    public MessageType parsePartialFrom(byte[] bArr, int i2, int i3) throws InvalidProtocolBufferException {
        return (MessageType) parsePartialFrom(bArr, i2, i3, EMPTY_REGISTRY);
    }

    @Override // androidx.health.platform.client.proto.Parser
    public MessageType parsePartialFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (MessageType) parsePartialFrom(bArr, 0, bArr.length, extensionRegistryLite);
    }

    @Override // androidx.health.platform.client.proto.Parser
    public MessageType parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
        return (MessageType) parseFrom(byteBuffer, EMPTY_REGISTRY);
    }

    @Override // androidx.health.platform.client.proto.Parser
    public MessageType parsePartialFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return (MessageType) parsePartialFrom(bArr, 0, bArr.length, EMPTY_REGISTRY);
    }

    @Override // androidx.health.platform.client.proto.Parser
    public MessageType parseFrom(byte[] bArr, int i2, int i3, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (MessageType) checkMessageInitialized(parsePartialFrom(bArr, i2, i3, extensionRegistryLite));
    }

    @Override // androidx.health.platform.client.proto.Parser
    public MessageType parsePartialFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        CodedInputStream codedInputStreamNewInstance = CodedInputStream.newInstance(inputStream);
        MessageType partialFrom = parsePartialFrom(codedInputStreamNewInstance, extensionRegistryLite);
        try {
            codedInputStreamNewInstance.checkLastTagWas(0);
            return partialFrom;
        } catch (InvalidProtocolBufferException e2) {
            throw e2.setUnfinishedMessage(partialFrom);
        }
    }

    @Override // androidx.health.platform.client.proto.Parser
    public MessageType parseFrom(byte[] bArr, int i2, int i3) throws InvalidProtocolBufferException {
        return (MessageType) parseFrom(bArr, i2, i3, EMPTY_REGISTRY);
    }

    @Override // androidx.health.platform.client.proto.Parser
    public MessageType parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (MessageType) parseFrom(bArr, 0, bArr.length, extensionRegistryLite);
    }

    @Override // androidx.health.platform.client.proto.Parser
    public MessageType parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return (MessageType) parseFrom(bArr, EMPTY_REGISTRY);
    }

    @Override // androidx.health.platform.client.proto.Parser
    public MessageType parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (MessageType) checkMessageInitialized(parsePartialFrom(inputStream, extensionRegistryLite));
    }

    @Override // androidx.health.platform.client.proto.Parser
    public MessageType parsePartialFrom(InputStream inputStream) throws InvalidProtocolBufferException {
        return (MessageType) parsePartialFrom(inputStream, EMPTY_REGISTRY);
    }

    @Override // androidx.health.platform.client.proto.Parser
    public MessageType parseFrom(InputStream inputStream) throws InvalidProtocolBufferException {
        return (MessageType) parseFrom(inputStream, EMPTY_REGISTRY);
    }
}
