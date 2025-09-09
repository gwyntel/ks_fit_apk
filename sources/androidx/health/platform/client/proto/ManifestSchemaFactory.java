package androidx.health.platform.client.proto;

@CheckReturnValue
/* loaded from: classes.dex */
final class ManifestSchemaFactory implements SchemaFactory {
    private static final MessageInfoFactory EMPTY_FACTORY = new MessageInfoFactory() { // from class: androidx.health.platform.client.proto.ManifestSchemaFactory.1
        @Override // androidx.health.platform.client.proto.MessageInfoFactory
        public boolean isSupported(Class<?> cls) {
            return false;
        }

        @Override // androidx.health.platform.client.proto.MessageInfoFactory
        public MessageInfo messageInfoFor(Class<?> cls) {
            throw new IllegalStateException("This should never be called.");
        }
    };
    private final MessageInfoFactory messageInfoFactory;

    private static class CompositeMessageInfoFactory implements MessageInfoFactory {
        private MessageInfoFactory[] factories;

        CompositeMessageInfoFactory(MessageInfoFactory... messageInfoFactoryArr) {
            this.factories = messageInfoFactoryArr;
        }

        @Override // androidx.health.platform.client.proto.MessageInfoFactory
        public boolean isSupported(Class<?> cls) {
            for (MessageInfoFactory messageInfoFactory : this.factories) {
                if (messageInfoFactory.isSupported(cls)) {
                    return true;
                }
            }
            return false;
        }

        @Override // androidx.health.platform.client.proto.MessageInfoFactory
        public MessageInfo messageInfoFor(Class<?> cls) {
            for (MessageInfoFactory messageInfoFactory : this.factories) {
                if (messageInfoFactory.isSupported(cls)) {
                    return messageInfoFactory.messageInfoFor(cls);
                }
            }
            throw new UnsupportedOperationException("No factory is available for message type: " + cls.getName());
        }
    }

    public ManifestSchemaFactory() {
        this(getDefaultMessageInfoFactory());
    }

    private static MessageInfoFactory getDefaultMessageInfoFactory() {
        return new CompositeMessageInfoFactory(GeneratedMessageInfoFactory.getInstance(), getDescriptorMessageInfoFactory());
    }

    private static MessageInfoFactory getDescriptorMessageInfoFactory() {
        try {
            return (MessageInfoFactory) Class.forName("androidx.health.platform.client.proto.DescriptorMessageInfoFactory").getDeclaredMethod("getInstance", null).invoke(null, null);
        } catch (Exception unused) {
            return EMPTY_FACTORY;
        }
    }

    private static boolean isProto2(MessageInfo messageInfo) {
        return messageInfo.getSyntax() == ProtoSyntax.PROTO2;
    }

    private static <T> Schema<T> newSchema(Class<T> cls, MessageInfo messageInfo) {
        return GeneratedMessageLite.class.isAssignableFrom(cls) ? isProto2(messageInfo) ? MessageSchema.b(cls, messageInfo, NewInstanceSchemas.b(), ListFieldSchema.b(), SchemaUtil.unknownFieldSetLiteSchema(), ExtensionSchemas.b(), MapFieldSchemas.b()) : MessageSchema.b(cls, messageInfo, NewInstanceSchemas.b(), ListFieldSchema.b(), SchemaUtil.unknownFieldSetLiteSchema(), null, MapFieldSchemas.b()) : isProto2(messageInfo) ? MessageSchema.b(cls, messageInfo, NewInstanceSchemas.a(), ListFieldSchema.a(), SchemaUtil.proto2UnknownFieldSetSchema(), ExtensionSchemas.a(), MapFieldSchemas.a()) : MessageSchema.b(cls, messageInfo, NewInstanceSchemas.a(), ListFieldSchema.a(), SchemaUtil.proto3UnknownFieldSetSchema(), null, MapFieldSchemas.a());
    }

    @Override // androidx.health.platform.client.proto.SchemaFactory
    public <T> Schema<T> createSchema(Class<T> cls) {
        SchemaUtil.requireGeneratedMessage(cls);
        MessageInfo messageInfoMessageInfoFor = this.messageInfoFactory.messageInfoFor(cls);
        return messageInfoMessageInfoFor.isMessageSetWireFormat() ? GeneratedMessageLite.class.isAssignableFrom(cls) ? MessageSetSchema.a(SchemaUtil.unknownFieldSetLiteSchema(), ExtensionSchemas.b(), messageInfoMessageInfoFor.getDefaultInstance()) : MessageSetSchema.a(SchemaUtil.proto2UnknownFieldSetSchema(), ExtensionSchemas.a(), messageInfoMessageInfoFor.getDefaultInstance()) : newSchema(cls, messageInfoMessageInfoFor);
    }

    private ManifestSchemaFactory(MessageInfoFactory messageInfoFactory) {
        this.messageInfoFactory = (MessageInfoFactory) Internal.b(messageInfoFactory, "messageInfoFactory");
    }
}
