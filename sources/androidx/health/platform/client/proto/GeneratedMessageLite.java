package androidx.health.platform.client.proto;

import androidx.health.platform.client.proto.AbstractMessageLite;
import androidx.health.platform.client.proto.ArrayDecoders;
import androidx.health.platform.client.proto.FieldSet;
import androidx.health.platform.client.proto.GeneratedMessageLite;
import androidx.health.platform.client.proto.GeneratedMessageLite.Builder;
import androidx.health.platform.client.proto.Internal;
import androidx.health.platform.client.proto.MessageLite;
import androidx.health.platform.client.proto.WireFormat;
import com.huawei.agconnect.config.impl.Utils;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes.dex */
public abstract class GeneratedMessageLite<MessageType extends GeneratedMessageLite<MessageType, BuilderType>, BuilderType extends Builder<MessageType, BuilderType>> extends AbstractMessageLite<MessageType, BuilderType> {
    private static final int MEMOIZED_SERIALIZED_SIZE_MASK = Integer.MAX_VALUE;
    private static final int MUTABLE_FLAG_MASK = Integer.MIN_VALUE;
    static final int UNINITIALIZED_HASH_CODE = 0;
    static final int UNINITIALIZED_SERIALIZED_SIZE = Integer.MAX_VALUE;
    private static Map<Object, GeneratedMessageLite<?, ?>> defaultInstanceMap = new ConcurrentHashMap();
    private int memoizedSerializedSize = -1;
    protected UnknownFieldSetLite unknownFields = UnknownFieldSetLite.getDefaultInstance();

    /* renamed from: androidx.health.platform.client.proto.GeneratedMessageLite$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f4443a;

        static {
            int[] iArr = new int[WireFormat.JavaType.values().length];
            f4443a = iArr;
            try {
                iArr[WireFormat.JavaType.MESSAGE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f4443a[WireFormat.JavaType.ENUM.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public static abstract class Builder<MessageType extends GeneratedMessageLite<MessageType, BuilderType>, BuilderType extends Builder<MessageType, BuilderType>> extends AbstractMessageLite.Builder<MessageType, BuilderType> {

        /* renamed from: a, reason: collision with root package name */
        protected GeneratedMessageLite f4444a;
        private final MessageType defaultInstance;

        /* JADX WARN: Multi-variable type inference failed */
        protected Builder(GeneratedMessageLite generatedMessageLite) {
            this.defaultInstance = generatedMessageLite;
            if (generatedMessageLite.x()) {
                throw new IllegalArgumentException("Default instance must be immutable.");
            }
            this.f4444a = newMutableInstance();
        }

        private static <MessageType> void mergeFromInstance(MessageType messagetype, MessageType messagetype2) {
            Protobuf.getInstance().schemaFor((Protobuf) messagetype).mergeFrom(messagetype, messagetype2);
        }

        private MessageType newMutableInstance() {
            return (MessageType) this.defaultInstance.E();
        }

        protected final void d() {
            if (this.f4444a.x()) {
                return;
            }
            e();
        }

        protected void e() {
            GeneratedMessageLite generatedMessageLiteNewMutableInstance = newMutableInstance();
            mergeFromInstance(generatedMessageLiteNewMutableInstance, this.f4444a);
            this.f4444a = generatedMessageLiteNewMutableInstance;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // androidx.health.platform.client.proto.AbstractMessageLite.Builder
        /* renamed from: f, reason: merged with bridge method [inline-methods] */
        public Builder b(GeneratedMessageLite generatedMessageLite) {
            return mergeFrom((Builder<MessageType, BuilderType>) generatedMessageLite);
        }

        @Override // androidx.health.platform.client.proto.MessageLiteOrBuilder
        public final boolean isInitialized() {
            return GeneratedMessageLite.w(this.f4444a, false);
        }

        @Override // androidx.health.platform.client.proto.MessageLite.Builder
        public final MessageType build() {
            MessageType messagetype = (MessageType) buildPartial();
            if (messagetype.isInitialized()) {
                return messagetype;
            }
            throw AbstractMessageLite.Builder.c(messagetype);
        }

        @Override // androidx.health.platform.client.proto.MessageLite.Builder
        public MessageType buildPartial() {
            if (!this.f4444a.x()) {
                return (MessageType) this.f4444a;
            }
            this.f4444a.y();
            return (MessageType) this.f4444a;
        }

        @Override // androidx.health.platform.client.proto.MessageLite.Builder
        public final BuilderType clear() {
            if (this.defaultInstance.x()) {
                throw new IllegalArgumentException("Default instance must be immutable.");
            }
            this.f4444a = newMutableInstance();
            return this;
        }

        @Override // androidx.health.platform.client.proto.MessageLiteOrBuilder
        public MessageType getDefaultInstanceForType() {
            return this.defaultInstance;
        }

        @Override // androidx.health.platform.client.proto.AbstractMessageLite.Builder
        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public BuilderType mo14clone() {
            BuilderType buildertype = (BuilderType) getDefaultInstanceForType().newBuilderForType();
            buildertype.f4444a = buildPartial();
            return buildertype;
        }

        public BuilderType mergeFrom(MessageType messagetype) {
            if (getDefaultInstanceForType().equals(messagetype)) {
                return this;
            }
            d();
            mergeFromInstance(this.f4444a, messagetype);
            return this;
        }

        @Override // androidx.health.platform.client.proto.AbstractMessageLite.Builder, androidx.health.platform.client.proto.MessageLite.Builder
        public BuilderType mergeFrom(byte[] bArr, int i2, int i3, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            d();
            try {
                Protobuf.getInstance().schemaFor((Protobuf) this.f4444a).mergeFrom(this.f4444a, bArr, i2, i2 + i3, new ArrayDecoders.Registers(extensionRegistryLite));
                return this;
            } catch (InvalidProtocolBufferException e2) {
                throw e2;
            } catch (IOException e3) {
                throw new RuntimeException("Reading from byte array should not throw IOException.", e3);
            } catch (IndexOutOfBoundsException unused) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
        }

        @Override // androidx.health.platform.client.proto.AbstractMessageLite.Builder, androidx.health.platform.client.proto.MessageLite.Builder
        public BuilderType mergeFrom(byte[] bArr, int i2, int i3) throws InvalidProtocolBufferException {
            return (BuilderType) mergeFrom(bArr, i2, i3, ExtensionRegistryLite.getEmptyRegistry());
        }

        @Override // androidx.health.platform.client.proto.AbstractMessageLite.Builder, androidx.health.platform.client.proto.MessageLite.Builder
        public BuilderType mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            d();
            try {
                Protobuf.getInstance().schemaFor((Protobuf) this.f4444a).mergeFrom(this.f4444a, CodedInputStreamReader.forCodedInput(codedInputStream), extensionRegistryLite);
                return this;
            } catch (RuntimeException e2) {
                if (e2.getCause() instanceof IOException) {
                    throw ((IOException) e2.getCause());
                }
                throw e2;
            }
        }
    }

    public static abstract class ExtendableBuilder<MessageType extends ExtendableMessage<MessageType, BuilderType>, BuilderType extends ExtendableBuilder<MessageType, BuilderType>> extends Builder<MessageType, BuilderType> implements ExtendableMessageOrBuilder<MessageType, BuilderType> {
        private FieldSet<ExtensionDescriptor> ensureExtensionsAreMutable() {
            FieldSet<ExtensionDescriptor> fieldSet = ((ExtendableMessage) this.f4444a).extensions;
            if (!fieldSet.isImmutable()) {
                return fieldSet;
            }
            FieldSet fieldSetClone = fieldSet.m15clone();
            ((ExtendableMessage) this.f4444a).extensions = fieldSetClone;
            return fieldSetClone;
        }

        private void verifyExtensionContainingType(GeneratedExtension<MessageType, ?> generatedExtension) {
            if (generatedExtension.getContainingTypeDefaultInstance() != getDefaultInstanceForType()) {
                throw new IllegalArgumentException("This extension is for a different message type.  Please make sure that you are not suppressing any generics type warnings.");
            }
        }

        public final <Type> BuilderType addExtension(ExtensionLite<MessageType, List<Type>> extensionLite, Type type) {
            GeneratedExtension<MessageType, ?> generatedExtensionCheckIsLite = GeneratedMessageLite.checkIsLite(extensionLite);
            verifyExtensionContainingType(generatedExtensionCheckIsLite);
            d();
            ensureExtensionsAreMutable().addRepeatedField(generatedExtensionCheckIsLite.f4454d, generatedExtensionCheckIsLite.d(type));
            return this;
        }

        public final BuilderType clearExtension(ExtensionLite<MessageType, ?> extensionLite) {
            GeneratedExtension<MessageType, ?> generatedExtensionCheckIsLite = GeneratedMessageLite.checkIsLite(extensionLite);
            verifyExtensionContainingType(generatedExtensionCheckIsLite);
            d();
            ensureExtensionsAreMutable().clearField(generatedExtensionCheckIsLite.f4454d);
            return this;
        }

        @Override // androidx.health.platform.client.proto.GeneratedMessageLite.Builder
        protected void e() {
            super.e();
            if (((ExtendableMessage) this.f4444a).extensions != FieldSet.emptySet()) {
                GeneratedMessageLite generatedMessageLite = this.f4444a;
                ((ExtendableMessage) generatedMessageLite).extensions = ((ExtendableMessage) generatedMessageLite).extensions.m15clone();
            }
        }

        @Override // androidx.health.platform.client.proto.GeneratedMessageLite.ExtendableMessageOrBuilder
        public final <Type> Type getExtension(ExtensionLite<MessageType, Type> extensionLite) {
            return (Type) ((ExtendableMessage) this.f4444a).getExtension(extensionLite);
        }

        @Override // androidx.health.platform.client.proto.GeneratedMessageLite.ExtendableMessageOrBuilder
        public final <Type> int getExtensionCount(ExtensionLite<MessageType, List<Type>> extensionLite) {
            return ((ExtendableMessage) this.f4444a).getExtensionCount(extensionLite);
        }

        @Override // androidx.health.platform.client.proto.GeneratedMessageLite.ExtendableMessageOrBuilder
        public final <Type> boolean hasExtension(ExtensionLite<MessageType, Type> extensionLite) {
            return ((ExtendableMessage) this.f4444a).hasExtension(extensionLite);
        }

        public final <Type> BuilderType setExtension(ExtensionLite<MessageType, Type> extensionLite, Type type) {
            GeneratedExtension<MessageType, ?> generatedExtensionCheckIsLite = GeneratedMessageLite.checkIsLite(extensionLite);
            verifyExtensionContainingType(generatedExtensionCheckIsLite);
            d();
            ensureExtensionsAreMutable().setField(generatedExtensionCheckIsLite.f4454d, generatedExtensionCheckIsLite.e(type));
            return this;
        }

        @Override // androidx.health.platform.client.proto.GeneratedMessageLite.ExtendableMessageOrBuilder
        public final <Type> Type getExtension(ExtensionLite<MessageType, List<Type>> extensionLite, int i2) {
            return (Type) ((ExtendableMessage) this.f4444a).getExtension(extensionLite, i2);
        }

        @Override // androidx.health.platform.client.proto.GeneratedMessageLite.Builder, androidx.health.platform.client.proto.MessageLite.Builder
        public final MessageType buildPartial() {
            if (!((ExtendableMessage) this.f4444a).x()) {
                return (MessageType) this.f4444a;
            }
            ((ExtendableMessage) this.f4444a).extensions.makeImmutable();
            return (MessageType) super.buildPartial();
        }

        public final <Type> BuilderType setExtension(ExtensionLite<MessageType, List<Type>> extensionLite, int i2, Type type) {
            GeneratedExtension<MessageType, ?> generatedExtensionCheckIsLite = GeneratedMessageLite.checkIsLite(extensionLite);
            verifyExtensionContainingType(generatedExtensionCheckIsLite);
            d();
            ensureExtensionsAreMutable().setRepeatedField(generatedExtensionCheckIsLite.f4454d, i2, generatedExtensionCheckIsLite.d(type));
            return this;
        }
    }

    public interface ExtendableMessageOrBuilder<MessageType extends ExtendableMessage<MessageType, BuilderType>, BuilderType extends ExtendableBuilder<MessageType, BuilderType>> extends MessageLiteOrBuilder {
        <Type> Type getExtension(ExtensionLite<MessageType, Type> extensionLite);

        <Type> Type getExtension(ExtensionLite<MessageType, List<Type>> extensionLite, int i2);

        <Type> int getExtensionCount(ExtensionLite<MessageType, List<Type>> extensionLite);

        <Type> boolean hasExtension(ExtensionLite<MessageType, Type> extensionLite);
    }

    static final class ExtensionDescriptor implements FieldSet.FieldDescriptorLite<ExtensionDescriptor> {

        /* renamed from: a, reason: collision with root package name */
        final Internal.EnumLiteMap f4446a;

        /* renamed from: b, reason: collision with root package name */
        final int f4447b;

        /* renamed from: c, reason: collision with root package name */
        final WireFormat.FieldType f4448c;

        /* renamed from: d, reason: collision with root package name */
        final boolean f4449d;

        /* renamed from: e, reason: collision with root package name */
        final boolean f4450e;

        ExtensionDescriptor(Internal.EnumLiteMap enumLiteMap, int i2, WireFormat.FieldType fieldType, boolean z2, boolean z3) {
            this.f4446a = enumLiteMap;
            this.f4447b = i2;
            this.f4448c = fieldType;
            this.f4449d = z2;
            this.f4450e = z3;
        }

        @Override // androidx.health.platform.client.proto.FieldSet.FieldDescriptorLite
        public Internal.EnumLiteMap<?> getEnumType() {
            return this.f4446a;
        }

        @Override // androidx.health.platform.client.proto.FieldSet.FieldDescriptorLite
        public WireFormat.JavaType getLiteJavaType() {
            return this.f4448c.getJavaType();
        }

        @Override // androidx.health.platform.client.proto.FieldSet.FieldDescriptorLite
        public WireFormat.FieldType getLiteType() {
            return this.f4448c;
        }

        @Override // androidx.health.platform.client.proto.FieldSet.FieldDescriptorLite
        public int getNumber() {
            return this.f4447b;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // androidx.health.platform.client.proto.FieldSet.FieldDescriptorLite
        public MessageLite.Builder internalMergeFrom(MessageLite.Builder builder, MessageLite messageLite) {
            return ((Builder) builder).mergeFrom((Builder) messageLite);
        }

        @Override // androidx.health.platform.client.proto.FieldSet.FieldDescriptorLite
        public boolean isPacked() {
            return this.f4450e;
        }

        @Override // androidx.health.platform.client.proto.FieldSet.FieldDescriptorLite
        public boolean isRepeated() {
            return this.f4449d;
        }

        @Override // java.lang.Comparable
        public int compareTo(ExtensionDescriptor extensionDescriptor) {
            return this.f4447b - extensionDescriptor.f4447b;
        }
    }

    public static class GeneratedExtension<ContainingType extends MessageLite, Type> extends ExtensionLite<ContainingType, Type> {

        /* renamed from: a, reason: collision with root package name */
        final MessageLite f4451a;

        /* renamed from: b, reason: collision with root package name */
        final Object f4452b;

        /* renamed from: c, reason: collision with root package name */
        final MessageLite f4453c;

        /* renamed from: d, reason: collision with root package name */
        final ExtensionDescriptor f4454d;

        GeneratedExtension(MessageLite messageLite, Object obj, MessageLite messageLite2, ExtensionDescriptor extensionDescriptor, Class cls) {
            if (messageLite == null) {
                throw new IllegalArgumentException("Null containingTypeDefaultInstance");
            }
            if (extensionDescriptor.getLiteType() == WireFormat.FieldType.MESSAGE && messageLite2 == null) {
                throw new IllegalArgumentException("Null messageDefaultInstance");
            }
            this.f4451a = messageLite;
            this.f4452b = obj;
            this.f4453c = messageLite2;
            this.f4454d = extensionDescriptor;
        }

        Object b(Object obj) {
            if (!this.f4454d.isRepeated()) {
                return c(obj);
            }
            if (this.f4454d.getLiteJavaType() != WireFormat.JavaType.ENUM) {
                return obj;
            }
            ArrayList arrayList = new ArrayList();
            Iterator it = ((List) obj).iterator();
            while (it.hasNext()) {
                arrayList.add(c(it.next()));
            }
            return arrayList;
        }

        Object c(Object obj) {
            return this.f4454d.getLiteJavaType() == WireFormat.JavaType.ENUM ? this.f4454d.f4446a.findValueByNumber(((Integer) obj).intValue()) : obj;
        }

        Object d(Object obj) {
            return this.f4454d.getLiteJavaType() == WireFormat.JavaType.ENUM ? Integer.valueOf(((Internal.EnumLite) obj).getNumber()) : obj;
        }

        Object e(Object obj) {
            if (!this.f4454d.isRepeated()) {
                return d(obj);
            }
            if (this.f4454d.getLiteJavaType() != WireFormat.JavaType.ENUM) {
                return obj;
            }
            ArrayList arrayList = new ArrayList();
            Iterator it = ((List) obj).iterator();
            while (it.hasNext()) {
                arrayList.add(d(it.next()));
            }
            return arrayList;
        }

        public ContainingType getContainingTypeDefaultInstance() {
            return (ContainingType) this.f4451a;
        }

        @Override // androidx.health.platform.client.proto.ExtensionLite
        public Type getDefaultValue() {
            return (Type) this.f4452b;
        }

        @Override // androidx.health.platform.client.proto.ExtensionLite
        public WireFormat.FieldType getLiteType() {
            return this.f4454d.getLiteType();
        }

        @Override // androidx.health.platform.client.proto.ExtensionLite
        public MessageLite getMessageDefaultInstance() {
            return this.f4453c;
        }

        @Override // androidx.health.platform.client.proto.ExtensionLite
        public int getNumber() {
            return this.f4454d.getNumber();
        }

        @Override // androidx.health.platform.client.proto.ExtensionLite
        public boolean isRepeated() {
            return this.f4454d.f4449d;
        }
    }

    public enum MethodToInvoke {
        GET_MEMOIZED_IS_INITIALIZED,
        SET_MEMOIZED_IS_INITIALIZED,
        BUILD_MESSAGE_INFO,
        NEW_MUTABLE_INSTANCE,
        NEW_BUILDER,
        GET_DEFAULT_INSTANCE,
        GET_PARSER
    }

    protected static final class SerializedForm implements Serializable {
        private static final long serialVersionUID = 0;
        private final byte[] asBytes;
        private final Class<?> messageClass;
        private final String messageClassName;

        SerializedForm(MessageLite messageLite) {
            Class<?> cls = messageLite.getClass();
            this.messageClass = cls;
            this.messageClassName = cls.getName();
            this.asBytes = messageLite.toByteArray();
        }

        public static SerializedForm of(MessageLite messageLite) {
            return new SerializedForm(messageLite);
        }

        @Deprecated
        private Object readResolveFallback() throws ObjectStreamException, NoSuchFieldException, SecurityException {
            try {
                java.lang.reflect.Field declaredField = resolveMessageClass().getDeclaredField("defaultInstance");
                declaredField.setAccessible(true);
                return ((MessageLite) declaredField.get(null)).newBuilderForType().mergeFrom(this.asBytes).buildPartial();
            } catch (InvalidProtocolBufferException e2) {
                throw new RuntimeException("Unable to understand proto buffer", e2);
            } catch (ClassNotFoundException e3) {
                throw new RuntimeException("Unable to find proto buffer class: " + this.messageClassName, e3);
            } catch (IllegalAccessException e4) {
                throw new RuntimeException("Unable to call parsePartialFrom", e4);
            } catch (NoSuchFieldException e5) {
                throw new RuntimeException("Unable to find defaultInstance in " + this.messageClassName, e5);
            } catch (SecurityException e6) {
                throw new RuntimeException("Unable to call defaultInstance in " + this.messageClassName, e6);
            }
        }

        private Class<?> resolveMessageClass() throws ClassNotFoundException {
            Class<?> cls = this.messageClass;
            return cls != null ? cls : Class.forName(this.messageClassName);
        }

        protected Object readResolve() throws ObjectStreamException, NoSuchFieldException, SecurityException {
            try {
                java.lang.reflect.Field declaredField = resolveMessageClass().getDeclaredField(Utils.DEFAULT_NAME);
                declaredField.setAccessible(true);
                return ((MessageLite) declaredField.get(null)).newBuilderForType().mergeFrom(this.asBytes).buildPartial();
            } catch (InvalidProtocolBufferException e2) {
                throw new RuntimeException("Unable to understand proto buffer", e2);
            } catch (ClassNotFoundException e3) {
                throw new RuntimeException("Unable to find proto buffer class: " + this.messageClassName, e3);
            } catch (IllegalAccessException e4) {
                throw new RuntimeException("Unable to call parsePartialFrom", e4);
            } catch (NoSuchFieldException unused) {
                return readResolveFallback();
            } catch (SecurityException e5) {
                throw new RuntimeException("Unable to call DEFAULT_INSTANCE in " + this.messageClassName, e5);
            }
        }
    }

    protected static Internal.ProtobufList C(Internal.ProtobufList protobufList) {
        int size = protobufList.size();
        return protobufList.mutableCopyWithCapacity2(size == 0 ? 10 : size * 2);
    }

    protected static Object D(MessageLite messageLite, String str, Object[] objArr) {
        return new RawMessageInfo(messageLite, str, objArr);
    }

    protected static GeneratedMessageLite F(GeneratedMessageLite generatedMessageLite, InputStream inputStream) {
        return checkMessageInitialized(parsePartialDelimitedFrom(generatedMessageLite, inputStream, ExtensionRegistryLite.getEmptyRegistry()));
    }

    protected static GeneratedMessageLite G(GeneratedMessageLite generatedMessageLite, InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) {
        return checkMessageInitialized(parsePartialDelimitedFrom(generatedMessageLite, inputStream, extensionRegistryLite));
    }

    protected static GeneratedMessageLite H(GeneratedMessageLite generatedMessageLite, ByteString byteString) {
        return checkMessageInitialized(I(generatedMessageLite, byteString, ExtensionRegistryLite.getEmptyRegistry()));
    }

    protected static GeneratedMessageLite I(GeneratedMessageLite generatedMessageLite, ByteString byteString, ExtensionRegistryLite extensionRegistryLite) {
        return checkMessageInitialized(parsePartialFrom(generatedMessageLite, byteString, extensionRegistryLite));
    }

    protected static GeneratedMessageLite J(GeneratedMessageLite generatedMessageLite, CodedInputStream codedInputStream) {
        return K(generatedMessageLite, codedInputStream, ExtensionRegistryLite.getEmptyRegistry());
    }

    protected static GeneratedMessageLite K(GeneratedMessageLite generatedMessageLite, CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) {
        return checkMessageInitialized(R(generatedMessageLite, codedInputStream, extensionRegistryLite));
    }

    protected static GeneratedMessageLite L(GeneratedMessageLite generatedMessageLite, InputStream inputStream) {
        return checkMessageInitialized(R(generatedMessageLite, CodedInputStream.newInstance(inputStream), ExtensionRegistryLite.getEmptyRegistry()));
    }

    protected static GeneratedMessageLite M(GeneratedMessageLite generatedMessageLite, InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) {
        return checkMessageInitialized(R(generatedMessageLite, CodedInputStream.newInstance(inputStream), extensionRegistryLite));
    }

    protected static GeneratedMessageLite N(GeneratedMessageLite generatedMessageLite, ByteBuffer byteBuffer) {
        return O(generatedMessageLite, byteBuffer, ExtensionRegistryLite.getEmptyRegistry());
    }

    protected static GeneratedMessageLite O(GeneratedMessageLite generatedMessageLite, ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) {
        return checkMessageInitialized(K(generatedMessageLite, CodedInputStream.newInstance(byteBuffer), extensionRegistryLite));
    }

    protected static GeneratedMessageLite P(GeneratedMessageLite generatedMessageLite, byte[] bArr) {
        return checkMessageInitialized(parsePartialFrom(generatedMessageLite, bArr, 0, bArr.length, ExtensionRegistryLite.getEmptyRegistry()));
    }

    protected static GeneratedMessageLite Q(GeneratedMessageLite generatedMessageLite, byte[] bArr, ExtensionRegistryLite extensionRegistryLite) {
        return checkMessageInitialized(parsePartialFrom(generatedMessageLite, bArr, 0, bArr.length, extensionRegistryLite));
    }

    static GeneratedMessageLite R(GeneratedMessageLite generatedMessageLite, CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        GeneratedMessageLite generatedMessageLiteE = generatedMessageLite.E();
        try {
            Schema schemaSchemaFor = Protobuf.getInstance().schemaFor((Protobuf) generatedMessageLiteE);
            schemaSchemaFor.mergeFrom(generatedMessageLiteE, CodedInputStreamReader.forCodedInput(codedInputStream), extensionRegistryLite);
            schemaSchemaFor.makeImmutable(generatedMessageLiteE);
            return generatedMessageLiteE;
        } catch (InvalidProtocolBufferException e2) {
            e = e2;
            if (e.getThrownFromInputStream()) {
                e = new InvalidProtocolBufferException((IOException) e);
            }
            throw e.setUnfinishedMessage(generatedMessageLiteE);
        } catch (UninitializedMessageException e3) {
            throw e3.asInvalidProtocolBufferException().setUnfinishedMessage(generatedMessageLiteE);
        } catch (IOException e4) {
            if (e4.getCause() instanceof InvalidProtocolBufferException) {
                throw ((InvalidProtocolBufferException) e4.getCause());
            }
            throw new InvalidProtocolBufferException(e4).setUnfinishedMessage(generatedMessageLiteE);
        } catch (RuntimeException e5) {
            if (e5.getCause() instanceof InvalidProtocolBufferException) {
                throw ((InvalidProtocolBufferException) e5.getCause());
            }
            throw e5;
        }
    }

    protected static void T(Class cls, GeneratedMessageLite generatedMessageLite) {
        generatedMessageLite.z();
        defaultInstanceMap.put(cls, generatedMessageLite);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <MessageType extends ExtendableMessage<MessageType, BuilderType>, BuilderType extends ExtendableBuilder<MessageType, BuilderType>, T> GeneratedExtension<MessageType, T> checkIsLite(ExtensionLite<MessageType, T> extensionLite) {
        if (extensionLite.a()) {
            return (GeneratedExtension) extensionLite;
        }
        throw new IllegalArgumentException("Expected a lite extension.");
    }

    private static <T extends GeneratedMessageLite<T, ?>> T checkMessageInitialized(T t2) throws InvalidProtocolBufferException {
        if (t2 == null || t2.isInitialized()) {
            return t2;
        }
        throw t2.e().asInvalidProtocolBufferException().setUnfinishedMessage(t2);
    }

    private int computeSerializedSize(Schema<?> schema) {
        return schema == null ? Protobuf.getInstance().schemaFor((Protobuf) this).getSerializedSize(this) : schema.getSerializedSize(this);
    }

    private final void ensureUnknownFieldsInitialized() {
        if (this.unknownFields == UnknownFieldSetLite.getDefaultInstance()) {
            this.unknownFields = UnknownFieldSetLite.g();
        }
    }

    public static <ContainingType extends MessageLite, Type> GeneratedExtension<ContainingType, Type> newRepeatedGeneratedExtension(ContainingType containingtype, MessageLite messageLite, Internal.EnumLiteMap<?> enumLiteMap, int i2, WireFormat.FieldType fieldType, boolean z2, Class cls) {
        return new GeneratedExtension<>(containingtype, Collections.emptyList(), messageLite, new ExtensionDescriptor(enumLiteMap, i2, fieldType, true, z2), cls);
    }

    public static <ContainingType extends MessageLite, Type> GeneratedExtension<ContainingType, Type> newSingularGeneratedExtension(ContainingType containingtype, Type type, MessageLite messageLite, Internal.EnumLiteMap<?> enumLiteMap, int i2, WireFormat.FieldType fieldType, Class cls) {
        return new GeneratedExtension<>(containingtype, type, messageLite, new ExtensionDescriptor(enumLiteMap, i2, fieldType, false, false), cls);
    }

    private static <T extends GeneratedMessageLite<T, ?>> T parsePartialDelimitedFrom(T t2, InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        try {
            int i2 = inputStream.read();
            if (i2 == -1) {
                return null;
            }
            CodedInputStream codedInputStreamNewInstance = CodedInputStream.newInstance(new AbstractMessageLite.Builder.LimitedInputStream(inputStream, CodedInputStream.readRawVarint32(i2, inputStream)));
            T t3 = (T) R(t2, codedInputStreamNewInstance, extensionRegistryLite);
            try {
                codedInputStreamNewInstance.checkLastTagWas(0);
                return t3;
            } catch (InvalidProtocolBufferException e2) {
                throw e2.setUnfinishedMessage(t3);
            }
        } catch (InvalidProtocolBufferException e3) {
            if (e3.getThrownFromInputStream()) {
                throw new InvalidProtocolBufferException((IOException) e3);
            }
            throw e3;
        } catch (IOException e4) {
            throw new InvalidProtocolBufferException(e4);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <T extends GeneratedMessageLite<T, ?>> T parsePartialFrom(T t2, byte[] bArr, int i2, int i3, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        T t3 = (T) t2.E();
        try {
            Schema schemaSchemaFor = Protobuf.getInstance().schemaFor((Protobuf) t3);
            schemaSchemaFor.mergeFrom(t3, bArr, i2, i2 + i3, new ArrayDecoders.Registers(extensionRegistryLite));
            schemaSchemaFor.makeImmutable(t3);
            return t3;
        } catch (InvalidProtocolBufferException e2) {
            e = e2;
            if (e.getThrownFromInputStream()) {
                e = new InvalidProtocolBufferException((IOException) e);
            }
            throw e.setUnfinishedMessage(t3);
        } catch (UninitializedMessageException e3) {
            throw e3.asInvalidProtocolBufferException().setUnfinishedMessage(t3);
        } catch (IOException e4) {
            if (e4.getCause() instanceof InvalidProtocolBufferException) {
                throw ((InvalidProtocolBufferException) e4.getCause());
            }
            throw new InvalidProtocolBufferException(e4).setUnfinishedMessage(t3);
        } catch (IndexOutOfBoundsException unused) {
            throw InvalidProtocolBufferException.truncatedMessage().setUnfinishedMessage(t3);
        }
    }

    protected static Internal.ProtobufList r() {
        return ProtobufArrayList.emptyList();
    }

    static GeneratedMessageLite s(Class cls) throws ClassNotFoundException {
        GeneratedMessageLite<?, ?> defaultInstanceForType = defaultInstanceMap.get(cls);
        if (defaultInstanceForType == null) {
            try {
                Class.forName(cls.getName(), true, cls.getClassLoader());
                defaultInstanceForType = defaultInstanceMap.get(cls);
            } catch (ClassNotFoundException e2) {
                throw new IllegalStateException("Class initialization cannot fail.", e2);
            }
        }
        if (defaultInstanceForType == null) {
            defaultInstanceForType = ((GeneratedMessageLite) UnsafeUtil.l(cls)).getDefaultInstanceForType();
            if (defaultInstanceForType == null) {
                throw new IllegalStateException();
            }
            defaultInstanceMap.put(cls, defaultInstanceForType);
        }
        return defaultInstanceForType;
    }

    static Object v(java.lang.reflect.Method method, Object obj, Object... objArr) {
        try {
            return method.invoke(obj, objArr);
        } catch (IllegalAccessException e2) {
            throw new RuntimeException("Couldn't use Java reflection to implement protocol message reflection.", e2);
        } catch (InvocationTargetException e3) {
            Throwable cause = e3.getCause();
            if (cause instanceof RuntimeException) {
                throw ((RuntimeException) cause);
            }
            if (cause instanceof Error) {
                throw ((Error) cause);
            }
            throw new RuntimeException("Unexpected exception thrown by generated accessor method.", cause);
        }
    }

    protected static final boolean w(GeneratedMessageLite generatedMessageLite, boolean z2) {
        byte bByteValue = ((Byte) generatedMessageLite.o(MethodToInvoke.GET_MEMOIZED_IS_INITIALIZED)).byteValue();
        if (bByteValue == 1) {
            return true;
        }
        if (bByteValue == 0) {
            return false;
        }
        boolean zIsInitialized = Protobuf.getInstance().schemaFor((Protobuf) generatedMessageLite).isInitialized(generatedMessageLite);
        if (z2) {
            generatedMessageLite.p(MethodToInvoke.SET_MEMOIZED_IS_INITIALIZED, zIsInitialized ? generatedMessageLite : null);
        }
        return zIsInitialized;
    }

    protected void A(int i2, ByteString byteString) {
        ensureUnknownFieldsInitialized();
        this.unknownFields.d(i2, byteString);
    }

    protected void B(int i2, int i3) {
        ensureUnknownFieldsInitialized();
        this.unknownFields.e(i2, i3);
    }

    GeneratedMessageLite E() {
        return (GeneratedMessageLite) o(MethodToInvoke.NEW_MUTABLE_INSTANCE);
    }

    protected boolean S(int i2, CodedInputStream codedInputStream) {
        if (WireFormat.getTagWireType(i2) == 4) {
            return false;
        }
        ensureUnknownFieldsInitialized();
        return this.unknownFields.b(i2, codedInputStream);
    }

    void U(int i2) {
        this.memoizedHashCode = i2;
    }

    @Override // androidx.health.platform.client.proto.AbstractMessageLite
    int c() {
        return this.memoizedSerializedSize & Integer.MAX_VALUE;
    }

    @Override // androidx.health.platform.client.proto.AbstractMessageLite
    int d(Schema schema) {
        if (!x()) {
            if (c() != Integer.MAX_VALUE) {
                return c();
            }
            int iComputeSerializedSize = computeSerializedSize(schema);
            f(iComputeSerializedSize);
            return iComputeSerializedSize;
        }
        int iComputeSerializedSize2 = computeSerializedSize(schema);
        if (iComputeSerializedSize2 >= 0) {
            return iComputeSerializedSize2;
        }
        throw new IllegalStateException("serialized size must be non-negative, was " + iComputeSerializedSize2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            return Protobuf.getInstance().schemaFor((Protobuf) this).equals(this, (GeneratedMessageLite) obj);
        }
        return false;
    }

    @Override // androidx.health.platform.client.proto.AbstractMessageLite
    void f(int i2) {
        if (i2 >= 0) {
            this.memoizedSerializedSize = (i2 & Integer.MAX_VALUE) | (this.memoizedSerializedSize & Integer.MIN_VALUE);
        } else {
            throw new IllegalStateException("serialized size must be non-negative, was " + i2);
        }
    }

    @Override // androidx.health.platform.client.proto.MessageLite
    public final Parser<MessageType> getParserForType() {
        return (Parser) o(MethodToInvoke.GET_PARSER);
    }

    @Override // androidx.health.platform.client.proto.MessageLite
    public int getSerializedSize() {
        return d(null);
    }

    public int hashCode() {
        if (x()) {
            return l();
        }
        if (u()) {
            U(l());
        }
        return t();
    }

    Object i() {
        return o(MethodToInvoke.BUILD_MESSAGE_INFO);
    }

    @Override // androidx.health.platform.client.proto.MessageLiteOrBuilder
    public final boolean isInitialized() {
        return w(this, true);
    }

    void j() {
        this.memoizedHashCode = 0;
    }

    void k() {
        f(Integer.MAX_VALUE);
    }

    int l() {
        return Protobuf.getInstance().schemaFor((Protobuf) this).hashCode(this);
    }

    protected final Builder m() {
        return (Builder) o(MethodToInvoke.NEW_BUILDER);
    }

    protected final Builder n(GeneratedMessageLite generatedMessageLite) {
        return m().mergeFrom((Builder) generatedMessageLite);
    }

    protected Object o(MethodToInvoke methodToInvoke) {
        return q(methodToInvoke, null, null);
    }

    protected Object p(MethodToInvoke methodToInvoke, Object obj) {
        return q(methodToInvoke, obj, null);
    }

    protected abstract Object q(MethodToInvoke methodToInvoke, Object obj, Object obj2);

    int t() {
        return this.memoizedHashCode;
    }

    public String toString() {
        return MessageLiteToString.b(this, super.toString());
    }

    boolean u() {
        return t() == 0;
    }

    @Override // androidx.health.platform.client.proto.MessageLite
    public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
        Protobuf.getInstance().schemaFor((Protobuf) this).writeTo(this, CodedOutputStreamWriter.forCodedOutput(codedOutputStream));
    }

    boolean x() {
        return (this.memoizedSerializedSize & Integer.MIN_VALUE) != 0;
    }

    protected void y() {
        Protobuf.getInstance().schemaFor((Protobuf) this).makeImmutable(this);
        z();
    }

    void z() {
        this.memoizedSerializedSize &= Integer.MAX_VALUE;
    }

    @Override // androidx.health.platform.client.proto.MessageLiteOrBuilder
    public final MessageType getDefaultInstanceForType() {
        return (MessageType) o(MethodToInvoke.GET_DEFAULT_INSTANCE);
    }

    @Override // androidx.health.platform.client.proto.MessageLite
    public final BuilderType newBuilderForType() {
        return (BuilderType) o(MethodToInvoke.NEW_BUILDER);
    }

    @Override // androidx.health.platform.client.proto.MessageLite
    public final BuilderType toBuilder() {
        return (BuilderType) ((Builder) o(MethodToInvoke.NEW_BUILDER)).mergeFrom((Builder) this);
    }

    protected static class DefaultInstanceBasedParser<T extends GeneratedMessageLite<T, ?>> extends AbstractParser<T> {
        private final T defaultInstance;

        public DefaultInstanceBasedParser(T t2) {
            this.defaultInstance = t2;
        }

        @Override // androidx.health.platform.client.proto.Parser
        public T parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (T) GeneratedMessageLite.R(this.defaultInstance, codedInputStream, extensionRegistryLite);
        }

        @Override // androidx.health.platform.client.proto.AbstractParser, androidx.health.platform.client.proto.Parser
        public T parsePartialFrom(byte[] bArr, int i2, int i3, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (T) GeneratedMessageLite.parsePartialFrom(this.defaultInstance, bArr, i2, i3, extensionRegistryLite);
        }
    }

    public static abstract class ExtendableMessage<MessageType extends ExtendableMessage<MessageType, BuilderType>, BuilderType extends ExtendableBuilder<MessageType, BuilderType>> extends GeneratedMessageLite<MessageType, BuilderType> implements ExtendableMessageOrBuilder<MessageType, BuilderType> {
        protected FieldSet<ExtensionDescriptor> extensions = FieldSet.emptySet();

        protected class ExtensionWriter {
            private final Iterator<Map.Entry<ExtensionDescriptor, Object>> iter;
            private final boolean messageSetWireFormat;
            private Map.Entry<ExtensionDescriptor, Object> next;

            private ExtensionWriter(boolean z2) {
                Iterator it = ExtendableMessage.this.extensions.iterator();
                this.iter = it;
                if (it.hasNext()) {
                    this.next = (Map.Entry) it.next();
                }
                this.messageSetWireFormat = z2;
            }

            public void writeUntil(int i2, CodedOutputStream codedOutputStream) throws IOException {
                while (true) {
                    Map.Entry<ExtensionDescriptor, Object> entry = this.next;
                    if (entry == null || entry.getKey().getNumber() >= i2) {
                        return;
                    }
                    ExtensionDescriptor key = this.next.getKey();
                    if (this.messageSetWireFormat && key.getLiteJavaType() == WireFormat.JavaType.MESSAGE && !key.isRepeated()) {
                        codedOutputStream.writeMessageSetExtension(key.getNumber(), (MessageLite) this.next.getValue());
                    } else {
                        FieldSet.writeField(key, this.next.getValue(), codedOutputStream);
                    }
                    if (this.iter.hasNext()) {
                        this.next = this.iter.next();
                    } else {
                        this.next = null;
                    }
                }
            }
        }

        private void eagerlyMergeMessageSetExtension(CodedInputStream codedInputStream, GeneratedExtension<?, ?> generatedExtension, ExtensionRegistryLite extensionRegistryLite, int i2) throws IOException {
            parseExtension(codedInputStream, extensionRegistryLite, generatedExtension, WireFormat.a(i2, 2), i2);
        }

        private void mergeMessageSetExtensionFromBytes(ByteString byteString, ExtensionRegistryLite extensionRegistryLite, GeneratedExtension<?, ?> generatedExtension) throws IOException {
            MessageLite messageLite = (MessageLite) this.extensions.getField(generatedExtension.f4454d);
            MessageLite.Builder builder = messageLite != null ? messageLite.toBuilder() : null;
            if (builder == null) {
                builder = generatedExtension.getMessageDefaultInstance().newBuilderForType();
            }
            builder.mergeFrom(byteString, extensionRegistryLite);
            V().setField(generatedExtension.f4454d, generatedExtension.d(builder.build()));
        }

        private <MessageType extends MessageLite> void mergeMessageSetExtensionFromCodedStream(MessageType messagetype, CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            int uInt32 = 0;
            ByteString bytes = null;
            GeneratedExtension<?, ?> generatedExtensionFindLiteExtensionByNumber = null;
            while (true) {
                int tag = codedInputStream.readTag();
                if (tag == 0) {
                    break;
                }
                if (tag == WireFormat.f4511c) {
                    uInt32 = codedInputStream.readUInt32();
                    if (uInt32 != 0) {
                        generatedExtensionFindLiteExtensionByNumber = extensionRegistryLite.findLiteExtensionByNumber(messagetype, uInt32);
                    }
                } else if (tag == WireFormat.f4512d) {
                    if (uInt32 == 0 || generatedExtensionFindLiteExtensionByNumber == null) {
                        bytes = codedInputStream.readBytes();
                    } else {
                        eagerlyMergeMessageSetExtension(codedInputStream, generatedExtensionFindLiteExtensionByNumber, extensionRegistryLite, uInt32);
                        bytes = null;
                    }
                } else if (!codedInputStream.skipField(tag)) {
                    break;
                }
            }
            codedInputStream.checkLastTagWas(WireFormat.f4510b);
            if (bytes == null || uInt32 == 0) {
                return;
            }
            if (generatedExtensionFindLiteExtensionByNumber != null) {
                mergeMessageSetExtensionFromBytes(bytes, extensionRegistryLite, generatedExtensionFindLiteExtensionByNumber);
            } else {
                A(uInt32, bytes);
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:4:0x0008  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private boolean parseExtension(androidx.health.platform.client.proto.CodedInputStream r6, androidx.health.platform.client.proto.ExtensionRegistryLite r7, androidx.health.platform.client.proto.GeneratedMessageLite.GeneratedExtension<?, ?> r8, int r9, int r10) throws java.io.IOException {
            /*
                Method dump skipped, instructions count: 293
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.health.platform.client.proto.GeneratedMessageLite.ExtendableMessage.parseExtension(androidx.health.platform.client.proto.CodedInputStream, androidx.health.platform.client.proto.ExtensionRegistryLite, androidx.health.platform.client.proto.GeneratedMessageLite$GeneratedExtension, int, int):boolean");
        }

        private void verifyExtensionContainingType(GeneratedExtension<MessageType, ?> generatedExtension) {
            if (generatedExtension.getContainingTypeDefaultInstance() != getDefaultInstanceForType()) {
                throw new IllegalArgumentException("This extension is for a different message type.  Please make sure that you are not suppressing any generics type warnings.");
            }
        }

        FieldSet V() {
            if (this.extensions.isImmutable()) {
                this.extensions = this.extensions.m15clone();
            }
            return this.extensions;
        }

        @Override // androidx.health.platform.client.proto.GeneratedMessageLite, androidx.health.platform.client.proto.MessageLiteOrBuilder
        public /* bridge */ /* synthetic */ MessageLite getDefaultInstanceForType() {
            return super.getDefaultInstanceForType();
        }

        @Override // androidx.health.platform.client.proto.GeneratedMessageLite.ExtendableMessageOrBuilder
        public final <Type> Type getExtension(ExtensionLite<MessageType, Type> extensionLite) {
            GeneratedExtension<MessageType, ?> generatedExtensionCheckIsLite = GeneratedMessageLite.checkIsLite(extensionLite);
            verifyExtensionContainingType(generatedExtensionCheckIsLite);
            Object field = this.extensions.getField(generatedExtensionCheckIsLite.f4454d);
            return field == null ? (Type) generatedExtensionCheckIsLite.f4452b : (Type) generatedExtensionCheckIsLite.b(field);
        }

        @Override // androidx.health.platform.client.proto.GeneratedMessageLite.ExtendableMessageOrBuilder
        public final <Type> int getExtensionCount(ExtensionLite<MessageType, List<Type>> extensionLite) {
            GeneratedExtension<MessageType, ?> generatedExtensionCheckIsLite = GeneratedMessageLite.checkIsLite(extensionLite);
            verifyExtensionContainingType(generatedExtensionCheckIsLite);
            return this.extensions.getRepeatedFieldCount(generatedExtensionCheckIsLite.f4454d);
        }

        @Override // androidx.health.platform.client.proto.GeneratedMessageLite.ExtendableMessageOrBuilder
        public final <Type> boolean hasExtension(ExtensionLite<MessageType, Type> extensionLite) {
            GeneratedExtension<MessageType, ?> generatedExtensionCheckIsLite = GeneratedMessageLite.checkIsLite(extensionLite);
            verifyExtensionContainingType(generatedExtensionCheckIsLite);
            return this.extensions.hasField(generatedExtensionCheckIsLite.f4454d);
        }

        @Override // androidx.health.platform.client.proto.GeneratedMessageLite, androidx.health.platform.client.proto.MessageLite
        public /* bridge */ /* synthetic */ MessageLite.Builder newBuilderForType() {
            return super.newBuilderForType();
        }

        @Override // androidx.health.platform.client.proto.GeneratedMessageLite, androidx.health.platform.client.proto.MessageLite
        public /* bridge */ /* synthetic */ MessageLite.Builder toBuilder() {
            return super.toBuilder();
        }

        @Override // androidx.health.platform.client.proto.GeneratedMessageLite.ExtendableMessageOrBuilder
        public final <Type> Type getExtension(ExtensionLite<MessageType, List<Type>> extensionLite, int i2) {
            GeneratedExtension<MessageType, ?> generatedExtensionCheckIsLite = GeneratedMessageLite.checkIsLite(extensionLite);
            verifyExtensionContainingType(generatedExtensionCheckIsLite);
            return (Type) generatedExtensionCheckIsLite.c(this.extensions.getRepeatedField(generatedExtensionCheckIsLite.f4454d, i2));
        }
    }

    private static <T extends GeneratedMessageLite<T, ?>> T parsePartialFrom(T t2, ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        CodedInputStream codedInputStreamNewCodedInput = byteString.newCodedInput();
        T t3 = (T) R(t2, codedInputStreamNewCodedInput, extensionRegistryLite);
        try {
            codedInputStreamNewCodedInput.checkLastTagWas(0);
            return t3;
        } catch (InvalidProtocolBufferException e2) {
            throw e2.setUnfinishedMessage(t3);
        }
    }
}
