package androidx.datastore.preferences.protobuf;

import androidx.datastore.preferences.PreferencesProto;
import androidx.datastore.preferences.protobuf.AbstractMessageLite;
import androidx.datastore.preferences.protobuf.ArrayDecoders;
import androidx.datastore.preferences.protobuf.FieldSet;
import androidx.datastore.preferences.protobuf.GeneratedMessageLite;
import androidx.datastore.preferences.protobuf.GeneratedMessageLite.Builder;
import androidx.datastore.preferences.protobuf.Internal;
import androidx.datastore.preferences.protobuf.MessageLite;
import androidx.datastore.preferences.protobuf.WireFormat;
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
    private static Map<Object, GeneratedMessageLite<?, ?>> defaultInstanceMap = new ConcurrentHashMap();
    protected UnknownFieldSetLite unknownFields = UnknownFieldSetLite.getDefaultInstance();
    protected int memoizedSerializedSize = -1;

    /* renamed from: androidx.datastore.preferences.protobuf.GeneratedMessageLite$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f3929a;

        static {
            int[] iArr = new int[WireFormat.JavaType.values().length];
            f3929a = iArr;
            try {
                iArr[WireFormat.JavaType.MESSAGE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f3929a[WireFormat.JavaType.ENUM.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public static abstract class Builder<MessageType extends GeneratedMessageLite<MessageType, BuilderType>, BuilderType extends Builder<MessageType, BuilderType>> extends AbstractMessageLite.Builder<MessageType, BuilderType> {

        /* renamed from: a, reason: collision with root package name */
        protected GeneratedMessageLite f3930a;

        /* renamed from: b, reason: collision with root package name */
        protected boolean f3931b = false;
        private final MessageType defaultInstance;

        /* JADX WARN: Multi-variable type inference failed */
        protected Builder(GeneratedMessageLite generatedMessageLite) {
            this.defaultInstance = generatedMessageLite;
            this.f3930a = (GeneratedMessageLite) generatedMessageLite.k(MethodToInvoke.NEW_MUTABLE_INSTANCE);
        }

        private void mergeFromInstance(MessageType messagetype, MessageType messagetype2) {
            Protobuf.getInstance().schemaFor((Protobuf) messagetype).mergeFrom(messagetype, messagetype2);
        }

        protected void d() {
            if (this.f3931b) {
                GeneratedMessageLite generatedMessageLite = (GeneratedMessageLite) this.f3930a.k(MethodToInvoke.NEW_MUTABLE_INSTANCE);
                mergeFromInstance(generatedMessageLite, this.f3930a);
                this.f3930a = generatedMessageLite;
                this.f3931b = false;
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // androidx.datastore.preferences.protobuf.AbstractMessageLite.Builder
        /* renamed from: e, reason: merged with bridge method [inline-methods] */
        public Builder b(GeneratedMessageLite generatedMessageLite) {
            return mergeFrom((Builder<MessageType, BuilderType>) generatedMessageLite);
        }

        @Override // androidx.datastore.preferences.protobuf.MessageLiteOrBuilder
        public final boolean isInitialized() {
            return GeneratedMessageLite.q(this.f3930a, false);
        }

        @Override // androidx.datastore.preferences.protobuf.MessageLite.Builder
        public final MessageType build() {
            MessageType messagetype = (MessageType) buildPartial();
            if (messagetype.isInitialized()) {
                return messagetype;
            }
            throw AbstractMessageLite.Builder.c(messagetype);
        }

        @Override // androidx.datastore.preferences.protobuf.MessageLite.Builder
        public MessageType buildPartial() {
            if (this.f3931b) {
                return (MessageType) this.f3930a;
            }
            this.f3930a.r();
            this.f3931b = true;
            return (MessageType) this.f3930a;
        }

        @Override // androidx.datastore.preferences.protobuf.MessageLite.Builder
        public final BuilderType clear() {
            this.f3930a = (GeneratedMessageLite) this.f3930a.k(MethodToInvoke.NEW_MUTABLE_INSTANCE);
            return this;
        }

        @Override // androidx.datastore.preferences.protobuf.MessageLiteOrBuilder
        public MessageType getDefaultInstanceForType() {
            return this.defaultInstance;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // androidx.datastore.preferences.protobuf.AbstractMessageLite.Builder
        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public BuilderType mo8clone() {
            PreferencesProto.PreferenceMap.Builder builder = (BuilderType) getDefaultInstanceForType().newBuilderForType();
            builder.mergeFrom((PreferencesProto.PreferenceMap.Builder) buildPartial());
            return builder;
        }

        public BuilderType mergeFrom(MessageType messagetype) {
            d();
            mergeFromInstance(this.f3930a, messagetype);
            return this;
        }

        @Override // androidx.datastore.preferences.protobuf.AbstractMessageLite.Builder, androidx.datastore.preferences.protobuf.MessageLite.Builder
        public BuilderType mergeFrom(byte[] bArr, int i2, int i3, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            d();
            try {
                Protobuf.getInstance().schemaFor((Protobuf) this.f3930a).mergeFrom(this.f3930a, bArr, i2, i2 + i3, new ArrayDecoders.Registers(extensionRegistryLite));
                return this;
            } catch (InvalidProtocolBufferException e2) {
                throw e2;
            } catch (IOException e3) {
                throw new RuntimeException("Reading from byte array should not throw IOException.", e3);
            } catch (IndexOutOfBoundsException unused) {
                throw InvalidProtocolBufferException.truncatedMessage();
            }
        }

        @Override // androidx.datastore.preferences.protobuf.AbstractMessageLite.Builder, androidx.datastore.preferences.protobuf.MessageLite.Builder
        public BuilderType mergeFrom(byte[] bArr, int i2, int i3) throws InvalidProtocolBufferException {
            return (BuilderType) mergeFrom(bArr, i2, i3, ExtensionRegistryLite.getEmptyRegistry());
        }

        @Override // androidx.datastore.preferences.protobuf.AbstractMessageLite.Builder, androidx.datastore.preferences.protobuf.MessageLite.Builder
        public BuilderType mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            d();
            try {
                Protobuf.getInstance().schemaFor((Protobuf) this.f3930a).mergeFrom(this.f3930a, CodedInputStreamReader.forCodedInput(codedInputStream), extensionRegistryLite);
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
            FieldSet<ExtensionDescriptor> fieldSet = ((ExtendableMessage) this.f3930a).extensions;
            if (!fieldSet.isImmutable()) {
                return fieldSet;
            }
            FieldSet fieldSetClone = fieldSet.m9clone();
            ((ExtendableMessage) this.f3930a).extensions = fieldSetClone;
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
            ensureExtensionsAreMutable().addRepeatedField(generatedExtensionCheckIsLite.f3941d, generatedExtensionCheckIsLite.d(type));
            return this;
        }

        public final <Type> BuilderType clearExtension(ExtensionLite<MessageType, ?> extensionLite) {
            GeneratedExtension<MessageType, ?> generatedExtensionCheckIsLite = GeneratedMessageLite.checkIsLite(extensionLite);
            verifyExtensionContainingType(generatedExtensionCheckIsLite);
            d();
            ensureExtensionsAreMutable().clearField(generatedExtensionCheckIsLite.f3941d);
            return this;
        }

        @Override // androidx.datastore.preferences.protobuf.GeneratedMessageLite.Builder
        protected void d() {
            if (this.f3931b) {
                super.d();
                GeneratedMessageLite generatedMessageLite = this.f3930a;
                ((ExtendableMessage) generatedMessageLite).extensions = ((ExtendableMessage) generatedMessageLite).extensions.m9clone();
            }
        }

        @Override // androidx.datastore.preferences.protobuf.GeneratedMessageLite.ExtendableMessageOrBuilder
        public final <Type> Type getExtension(ExtensionLite<MessageType, Type> extensionLite) {
            return (Type) ((ExtendableMessage) this.f3930a).getExtension(extensionLite);
        }

        @Override // androidx.datastore.preferences.protobuf.GeneratedMessageLite.ExtendableMessageOrBuilder
        public final <Type> int getExtensionCount(ExtensionLite<MessageType, List<Type>> extensionLite) {
            return ((ExtendableMessage) this.f3930a).getExtensionCount(extensionLite);
        }

        @Override // androidx.datastore.preferences.protobuf.GeneratedMessageLite.ExtendableMessageOrBuilder
        public final <Type> boolean hasExtension(ExtensionLite<MessageType, Type> extensionLite) {
            return ((ExtendableMessage) this.f3930a).hasExtension(extensionLite);
        }

        public final <Type> BuilderType setExtension(ExtensionLite<MessageType, Type> extensionLite, Type type) {
            GeneratedExtension<MessageType, ?> generatedExtensionCheckIsLite = GeneratedMessageLite.checkIsLite(extensionLite);
            verifyExtensionContainingType(generatedExtensionCheckIsLite);
            d();
            ensureExtensionsAreMutable().setField(generatedExtensionCheckIsLite.f3941d, generatedExtensionCheckIsLite.e(type));
            return this;
        }

        @Override // androidx.datastore.preferences.protobuf.GeneratedMessageLite.ExtendableMessageOrBuilder
        public final <Type> Type getExtension(ExtensionLite<MessageType, List<Type>> extensionLite, int i2) {
            return (Type) ((ExtendableMessage) this.f3930a).getExtension(extensionLite, i2);
        }

        @Override // androidx.datastore.preferences.protobuf.GeneratedMessageLite.Builder, androidx.datastore.preferences.protobuf.MessageLite.Builder
        public final MessageType buildPartial() {
            if (this.f3931b) {
                return (MessageType) this.f3930a;
            }
            ((ExtendableMessage) this.f3930a).extensions.makeImmutable();
            return (MessageType) super.buildPartial();
        }

        public final <Type> BuilderType setExtension(ExtensionLite<MessageType, List<Type>> extensionLite, int i2, Type type) {
            GeneratedExtension<MessageType, ?> generatedExtensionCheckIsLite = GeneratedMessageLite.checkIsLite(extensionLite);
            verifyExtensionContainingType(generatedExtensionCheckIsLite);
            d();
            ensureExtensionsAreMutable().setRepeatedField(generatedExtensionCheckIsLite.f3941d, i2, generatedExtensionCheckIsLite.d(type));
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
        final Internal.EnumLiteMap f3933a;

        /* renamed from: b, reason: collision with root package name */
        final int f3934b;

        /* renamed from: c, reason: collision with root package name */
        final WireFormat.FieldType f3935c;

        /* renamed from: d, reason: collision with root package name */
        final boolean f3936d;

        /* renamed from: e, reason: collision with root package name */
        final boolean f3937e;

        ExtensionDescriptor(Internal.EnumLiteMap enumLiteMap, int i2, WireFormat.FieldType fieldType, boolean z2, boolean z3) {
            this.f3933a = enumLiteMap;
            this.f3934b = i2;
            this.f3935c = fieldType;
            this.f3936d = z2;
            this.f3937e = z3;
        }

        @Override // androidx.datastore.preferences.protobuf.FieldSet.FieldDescriptorLite
        public Internal.EnumLiteMap<?> getEnumType() {
            return this.f3933a;
        }

        @Override // androidx.datastore.preferences.protobuf.FieldSet.FieldDescriptorLite
        public WireFormat.JavaType getLiteJavaType() {
            return this.f3935c.getJavaType();
        }

        @Override // androidx.datastore.preferences.protobuf.FieldSet.FieldDescriptorLite
        public WireFormat.FieldType getLiteType() {
            return this.f3935c;
        }

        @Override // androidx.datastore.preferences.protobuf.FieldSet.FieldDescriptorLite
        public int getNumber() {
            return this.f3934b;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // androidx.datastore.preferences.protobuf.FieldSet.FieldDescriptorLite
        public MessageLite.Builder internalMergeFrom(MessageLite.Builder builder, MessageLite messageLite) {
            return ((Builder) builder).mergeFrom((Builder) messageLite);
        }

        @Override // androidx.datastore.preferences.protobuf.FieldSet.FieldDescriptorLite
        public boolean isPacked() {
            return this.f3937e;
        }

        @Override // androidx.datastore.preferences.protobuf.FieldSet.FieldDescriptorLite
        public boolean isRepeated() {
            return this.f3936d;
        }

        @Override // java.lang.Comparable
        public int compareTo(ExtensionDescriptor extensionDescriptor) {
            return this.f3934b - extensionDescriptor.f3934b;
        }
    }

    public static class GeneratedExtension<ContainingType extends MessageLite, Type> extends ExtensionLite<ContainingType, Type> {

        /* renamed from: a, reason: collision with root package name */
        final MessageLite f3938a;

        /* renamed from: b, reason: collision with root package name */
        final Object f3939b;

        /* renamed from: c, reason: collision with root package name */
        final MessageLite f3940c;

        /* renamed from: d, reason: collision with root package name */
        final ExtensionDescriptor f3941d;

        GeneratedExtension(MessageLite messageLite, Object obj, MessageLite messageLite2, ExtensionDescriptor extensionDescriptor, Class cls) {
            if (messageLite == null) {
                throw new IllegalArgumentException("Null containingTypeDefaultInstance");
            }
            if (extensionDescriptor.getLiteType() == WireFormat.FieldType.MESSAGE && messageLite2 == null) {
                throw new IllegalArgumentException("Null messageDefaultInstance");
            }
            this.f3938a = messageLite;
            this.f3939b = obj;
            this.f3940c = messageLite2;
            this.f3941d = extensionDescriptor;
        }

        Object b(Object obj) {
            if (!this.f3941d.isRepeated()) {
                return c(obj);
            }
            if (this.f3941d.getLiteJavaType() != WireFormat.JavaType.ENUM) {
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
            return this.f3941d.getLiteJavaType() == WireFormat.JavaType.ENUM ? this.f3941d.f3933a.findValueByNumber(((Integer) obj).intValue()) : obj;
        }

        Object d(Object obj) {
            return this.f3941d.getLiteJavaType() == WireFormat.JavaType.ENUM ? Integer.valueOf(((Internal.EnumLite) obj).getNumber()) : obj;
        }

        Object e(Object obj) {
            if (!this.f3941d.isRepeated()) {
                return d(obj);
            }
            if (this.f3941d.getLiteJavaType() != WireFormat.JavaType.ENUM) {
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
            return (ContainingType) this.f3938a;
        }

        @Override // androidx.datastore.preferences.protobuf.ExtensionLite
        public Type getDefaultValue() {
            return (Type) this.f3939b;
        }

        @Override // androidx.datastore.preferences.protobuf.ExtensionLite
        public WireFormat.FieldType getLiteType() {
            return this.f3941d.getLiteType();
        }

        @Override // androidx.datastore.preferences.protobuf.ExtensionLite
        public MessageLite getMessageDefaultInstance() {
            return this.f3940c;
        }

        @Override // androidx.datastore.preferences.protobuf.ExtensionLite
        public int getNumber() {
            return this.f3941d.getNumber();
        }

        @Override // androidx.datastore.preferences.protobuf.ExtensionLite
        public boolean isRepeated() {
            return this.f3941d.f3936d;
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

    protected static GeneratedMessageLite A(GeneratedMessageLite generatedMessageLite, CodedInputStream codedInputStream) {
        return B(generatedMessageLite, codedInputStream, ExtensionRegistryLite.getEmptyRegistry());
    }

    protected static GeneratedMessageLite B(GeneratedMessageLite generatedMessageLite, CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) {
        return checkMessageInitialized(I(generatedMessageLite, codedInputStream, extensionRegistryLite));
    }

    protected static GeneratedMessageLite C(GeneratedMessageLite generatedMessageLite, InputStream inputStream) {
        return checkMessageInitialized(I(generatedMessageLite, CodedInputStream.newInstance(inputStream), ExtensionRegistryLite.getEmptyRegistry()));
    }

    protected static GeneratedMessageLite D(GeneratedMessageLite generatedMessageLite, InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) {
        return checkMessageInitialized(I(generatedMessageLite, CodedInputStream.newInstance(inputStream), extensionRegistryLite));
    }

    protected static GeneratedMessageLite E(GeneratedMessageLite generatedMessageLite, ByteBuffer byteBuffer) {
        return F(generatedMessageLite, byteBuffer, ExtensionRegistryLite.getEmptyRegistry());
    }

    protected static GeneratedMessageLite F(GeneratedMessageLite generatedMessageLite, ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) {
        return checkMessageInitialized(B(generatedMessageLite, CodedInputStream.newInstance(byteBuffer), extensionRegistryLite));
    }

    protected static GeneratedMessageLite G(GeneratedMessageLite generatedMessageLite, byte[] bArr) {
        return checkMessageInitialized(J(generatedMessageLite, bArr, 0, bArr.length, ExtensionRegistryLite.getEmptyRegistry()));
    }

    protected static GeneratedMessageLite H(GeneratedMessageLite generatedMessageLite, byte[] bArr, ExtensionRegistryLite extensionRegistryLite) {
        return checkMessageInitialized(J(generatedMessageLite, bArr, 0, bArr.length, extensionRegistryLite));
    }

    static GeneratedMessageLite I(GeneratedMessageLite generatedMessageLite, CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        GeneratedMessageLite generatedMessageLite2 = (GeneratedMessageLite) generatedMessageLite.k(MethodToInvoke.NEW_MUTABLE_INSTANCE);
        try {
            Schema schemaSchemaFor = Protobuf.getInstance().schemaFor((Protobuf) generatedMessageLite2);
            schemaSchemaFor.mergeFrom(generatedMessageLite2, CodedInputStreamReader.forCodedInput(codedInputStream), extensionRegistryLite);
            schemaSchemaFor.makeImmutable(generatedMessageLite2);
            return generatedMessageLite2;
        } catch (IOException e2) {
            if (e2.getCause() instanceof InvalidProtocolBufferException) {
                throw ((InvalidProtocolBufferException) e2.getCause());
            }
            throw new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(generatedMessageLite2);
        } catch (RuntimeException e3) {
            if (e3.getCause() instanceof InvalidProtocolBufferException) {
                throw ((InvalidProtocolBufferException) e3.getCause());
            }
            throw e3;
        }
    }

    static GeneratedMessageLite J(GeneratedMessageLite generatedMessageLite, byte[] bArr, int i2, int i3, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        GeneratedMessageLite generatedMessageLite2 = (GeneratedMessageLite) generatedMessageLite.k(MethodToInvoke.NEW_MUTABLE_INSTANCE);
        try {
            Schema schemaSchemaFor = Protobuf.getInstance().schemaFor((Protobuf) generatedMessageLite2);
            schemaSchemaFor.mergeFrom(generatedMessageLite2, bArr, i2, i2 + i3, new ArrayDecoders.Registers(extensionRegistryLite));
            schemaSchemaFor.makeImmutable(generatedMessageLite2);
            if (generatedMessageLite2.memoizedHashCode == 0) {
                return generatedMessageLite2;
            }
            throw new RuntimeException();
        } catch (IOException e2) {
            if (e2.getCause() instanceof InvalidProtocolBufferException) {
                throw ((InvalidProtocolBufferException) e2.getCause());
            }
            throw new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(generatedMessageLite2);
        } catch (IndexOutOfBoundsException unused) {
            throw InvalidProtocolBufferException.truncatedMessage().setUnfinishedMessage(generatedMessageLite2);
        }
    }

    protected static void L(Class cls, GeneratedMessageLite generatedMessageLite) {
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

    private final void ensureUnknownFieldsInitialized() {
        if (this.unknownFields == UnknownFieldSetLite.getDefaultInstance()) {
            this.unknownFields = UnknownFieldSetLite.f();
        }
    }

    protected static Internal.ProtobufList n() {
        return ProtobufArrayList.emptyList();
    }

    public static <ContainingType extends MessageLite, Type> GeneratedExtension<ContainingType, Type> newRepeatedGeneratedExtension(ContainingType containingtype, MessageLite messageLite, Internal.EnumLiteMap<?> enumLiteMap, int i2, WireFormat.FieldType fieldType, boolean z2, Class cls) {
        return new GeneratedExtension<>(containingtype, Collections.emptyList(), messageLite, new ExtensionDescriptor(enumLiteMap, i2, fieldType, true, z2), cls);
    }

    public static <ContainingType extends MessageLite, Type> GeneratedExtension<ContainingType, Type> newSingularGeneratedExtension(ContainingType containingtype, Type type, MessageLite messageLite, Internal.EnumLiteMap<?> enumLiteMap, int i2, WireFormat.FieldType fieldType, Class cls) {
        return new GeneratedExtension<>(containingtype, type, messageLite, new ExtensionDescriptor(enumLiteMap, i2, fieldType, false, false), cls);
    }

    static GeneratedMessageLite o(Class cls) throws ClassNotFoundException {
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
            defaultInstanceForType = ((GeneratedMessageLite) UnsafeUtil.j(cls)).getDefaultInstanceForType();
            if (defaultInstanceForType == null) {
                throw new IllegalStateException();
            }
            defaultInstanceMap.put(cls, defaultInstanceForType);
        }
        return defaultInstanceForType;
    }

    static Object p(java.lang.reflect.Method method, Object obj, Object... objArr) {
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

    private static <T extends GeneratedMessageLite<T, ?>> T parsePartialDelimitedFrom(T t2, InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        try {
            int i2 = inputStream.read();
            if (i2 == -1) {
                return null;
            }
            CodedInputStream codedInputStreamNewInstance = CodedInputStream.newInstance(new AbstractMessageLite.Builder.LimitedInputStream(inputStream, CodedInputStream.readRawVarint32(i2, inputStream)));
            T t3 = (T) I(t2, codedInputStreamNewInstance, extensionRegistryLite);
            try {
                codedInputStreamNewInstance.checkLastTagWas(0);
                return t3;
            } catch (InvalidProtocolBufferException e2) {
                throw e2.setUnfinishedMessage(t3);
            }
        } catch (IOException e3) {
            throw new InvalidProtocolBufferException(e3.getMessage());
        }
    }

    private static <T extends GeneratedMessageLite<T, ?>> T parsePartialFrom(T t2, ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        CodedInputStream codedInputStreamNewCodedInput = byteString.newCodedInput();
        T t3 = (T) I(t2, codedInputStreamNewCodedInput, extensionRegistryLite);
        try {
            codedInputStreamNewCodedInput.checkLastTagWas(0);
            return t3;
        } catch (InvalidProtocolBufferException e2) {
            throw e2.setUnfinishedMessage(t3);
        }
    }

    protected static final boolean q(GeneratedMessageLite generatedMessageLite, boolean z2) {
        byte bByteValue = ((Byte) generatedMessageLite.k(MethodToInvoke.GET_MEMOIZED_IS_INITIALIZED)).byteValue();
        if (bByteValue == 1) {
            return true;
        }
        if (bByteValue == 0) {
            return false;
        }
        boolean zIsInitialized = Protobuf.getInstance().schemaFor((Protobuf) generatedMessageLite).isInitialized(generatedMessageLite);
        if (z2) {
            generatedMessageLite.l(MethodToInvoke.SET_MEMOIZED_IS_INITIALIZED, zIsInitialized ? generatedMessageLite : null);
        }
        return zIsInitialized;
    }

    protected static Internal.ProtobufList u(Internal.ProtobufList protobufList) {
        int size = protobufList.size();
        return protobufList.mutableCopyWithCapacity2(size == 0 ? 10 : size * 2);
    }

    protected static Object v(MessageLite messageLite, String str, Object[] objArr) {
        return new RawMessageInfo(messageLite, str, objArr);
    }

    protected static GeneratedMessageLite w(GeneratedMessageLite generatedMessageLite, InputStream inputStream) {
        return checkMessageInitialized(parsePartialDelimitedFrom(generatedMessageLite, inputStream, ExtensionRegistryLite.getEmptyRegistry()));
    }

    protected static GeneratedMessageLite x(GeneratedMessageLite generatedMessageLite, InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) {
        return checkMessageInitialized(parsePartialDelimitedFrom(generatedMessageLite, inputStream, extensionRegistryLite));
    }

    protected static GeneratedMessageLite y(GeneratedMessageLite generatedMessageLite, ByteString byteString) {
        return checkMessageInitialized(z(generatedMessageLite, byteString, ExtensionRegistryLite.getEmptyRegistry()));
    }

    protected static GeneratedMessageLite z(GeneratedMessageLite generatedMessageLite, ByteString byteString, ExtensionRegistryLite extensionRegistryLite) {
        return checkMessageInitialized(parsePartialFrom(generatedMessageLite, byteString, extensionRegistryLite));
    }

    protected boolean K(int i2, CodedInputStream codedInputStream) {
        if (WireFormat.getTagWireType(i2) == 4) {
            return false;
        }
        ensureUnknownFieldsInitialized();
        return this.unknownFields.b(i2, codedInputStream);
    }

    @Override // androidx.datastore.preferences.protobuf.AbstractMessageLite
    int c() {
        return this.memoizedSerializedSize;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (getDefaultInstanceForType().getClass().isInstance(obj)) {
            return Protobuf.getInstance().schemaFor((Protobuf) this).equals(this, (GeneratedMessageLite) obj);
        }
        return false;
    }

    @Override // androidx.datastore.preferences.protobuf.AbstractMessageLite
    void f(int i2) {
        this.memoizedSerializedSize = i2;
    }

    @Override // androidx.datastore.preferences.protobuf.MessageLite
    public final Parser<MessageType> getParserForType() {
        return (Parser) k(MethodToInvoke.GET_PARSER);
    }

    @Override // androidx.datastore.preferences.protobuf.MessageLite
    public int getSerializedSize() {
        if (this.memoizedSerializedSize == -1) {
            this.memoizedSerializedSize = Protobuf.getInstance().schemaFor((Protobuf) this).getSerializedSize(this);
        }
        return this.memoizedSerializedSize;
    }

    Object h() {
        return k(MethodToInvoke.BUILD_MESSAGE_INFO);
    }

    public int hashCode() {
        int i2 = this.memoizedHashCode;
        if (i2 != 0) {
            return i2;
        }
        int iHashCode = Protobuf.getInstance().schemaFor((Protobuf) this).hashCode(this);
        this.memoizedHashCode = iHashCode;
        return iHashCode;
    }

    protected final Builder i() {
        return (Builder) k(MethodToInvoke.NEW_BUILDER);
    }

    @Override // androidx.datastore.preferences.protobuf.MessageLiteOrBuilder
    public final boolean isInitialized() {
        return q(this, true);
    }

    protected final Builder j(GeneratedMessageLite generatedMessageLite) {
        return i().mergeFrom((Builder) generatedMessageLite);
    }

    protected Object k(MethodToInvoke methodToInvoke) {
        return m(methodToInvoke, null, null);
    }

    protected Object l(MethodToInvoke methodToInvoke, Object obj) {
        return m(methodToInvoke, obj, null);
    }

    protected abstract Object m(MethodToInvoke methodToInvoke, Object obj, Object obj2);

    protected void r() {
        Protobuf.getInstance().schemaFor((Protobuf) this).makeImmutable(this);
    }

    protected void s(int i2, ByteString byteString) {
        ensureUnknownFieldsInitialized();
        this.unknownFields.c(i2, byteString);
    }

    protected void t(int i2, int i3) {
        ensureUnknownFieldsInitialized();
        this.unknownFields.d(i2, i3);
    }

    public String toString() {
        return MessageLiteToString.b(this, super.toString());
    }

    @Override // androidx.datastore.preferences.protobuf.MessageLite
    public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
        Protobuf.getInstance().schemaFor((Protobuf) this).writeTo(this, CodedOutputStreamWriter.forCodedOutput(codedOutputStream));
    }

    @Override // androidx.datastore.preferences.protobuf.MessageLiteOrBuilder
    public final MessageType getDefaultInstanceForType() {
        return (MessageType) k(MethodToInvoke.GET_DEFAULT_INSTANCE);
    }

    @Override // androidx.datastore.preferences.protobuf.MessageLite
    public final BuilderType newBuilderForType() {
        return (BuilderType) k(MethodToInvoke.NEW_BUILDER);
    }

    @Override // androidx.datastore.preferences.protobuf.MessageLite
    public final BuilderType toBuilder() {
        BuilderType buildertype = (BuilderType) k(MethodToInvoke.NEW_BUILDER);
        buildertype.mergeFrom(this);
        return buildertype;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static class DefaultInstanceBasedParser<T extends GeneratedMessageLite<T, ?>> extends AbstractParser<T> {
        private final T defaultInstance;

        public DefaultInstanceBasedParser(T t2) {
            this.defaultInstance = t2;
        }

        @Override // androidx.datastore.preferences.protobuf.Parser
        public T parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (T) GeneratedMessageLite.I(this.defaultInstance, codedInputStream, extensionRegistryLite);
        }

        @Override // androidx.datastore.preferences.protobuf.AbstractParser, androidx.datastore.preferences.protobuf.Parser
        public T parsePartialFrom(byte[] bArr, int i2, int i3, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (T) GeneratedMessageLite.J(this.defaultInstance, bArr, i2, i3, extensionRegistryLite);
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
            MessageLite messageLite = (MessageLite) this.extensions.getField(generatedExtension.f3941d);
            MessageLite.Builder builder = messageLite != null ? messageLite.toBuilder() : null;
            if (builder == null) {
                builder = generatedExtension.getMessageDefaultInstance().newBuilderForType();
            }
            builder.mergeFrom(byteString, extensionRegistryLite);
            M().setField(generatedExtension.f3941d, generatedExtension.d(builder.build()));
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
                if (tag == WireFormat.f3992c) {
                    uInt32 = codedInputStream.readUInt32();
                    if (uInt32 != 0) {
                        generatedExtensionFindLiteExtensionByNumber = extensionRegistryLite.findLiteExtensionByNumber(messagetype, uInt32);
                    }
                } else if (tag == WireFormat.f3993d) {
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
            codedInputStream.checkLastTagWas(WireFormat.f3991b);
            if (bytes == null || uInt32 == 0) {
                return;
            }
            if (generatedExtensionFindLiteExtensionByNumber != null) {
                mergeMessageSetExtensionFromBytes(bytes, extensionRegistryLite, generatedExtensionFindLiteExtensionByNumber);
            } else {
                s(uInt32, bytes);
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:4:0x0008  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private boolean parseExtension(androidx.datastore.preferences.protobuf.CodedInputStream r6, androidx.datastore.preferences.protobuf.ExtensionRegistryLite r7, androidx.datastore.preferences.protobuf.GeneratedMessageLite.GeneratedExtension<?, ?> r8, int r9, int r10) throws java.io.IOException {
            /*
                Method dump skipped, instructions count: 293
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.preferences.protobuf.GeneratedMessageLite.ExtendableMessage.parseExtension(androidx.datastore.preferences.protobuf.CodedInputStream, androidx.datastore.preferences.protobuf.ExtensionRegistryLite, androidx.datastore.preferences.protobuf.GeneratedMessageLite$GeneratedExtension, int, int):boolean");
        }

        private void verifyExtensionContainingType(GeneratedExtension<MessageType, ?> generatedExtension) {
            if (generatedExtension.getContainingTypeDefaultInstance() != getDefaultInstanceForType()) {
                throw new IllegalArgumentException("This extension is for a different message type.  Please make sure that you are not suppressing any generics type warnings.");
            }
        }

        FieldSet M() {
            if (this.extensions.isImmutable()) {
                this.extensions = this.extensions.m9clone();
            }
            return this.extensions;
        }

        @Override // androidx.datastore.preferences.protobuf.GeneratedMessageLite, androidx.datastore.preferences.protobuf.MessageLiteOrBuilder
        public /* bridge */ /* synthetic */ MessageLite getDefaultInstanceForType() {
            return super.getDefaultInstanceForType();
        }

        @Override // androidx.datastore.preferences.protobuf.GeneratedMessageLite.ExtendableMessageOrBuilder
        public final <Type> Type getExtension(ExtensionLite<MessageType, Type> extensionLite) {
            GeneratedExtension<MessageType, ?> generatedExtensionCheckIsLite = GeneratedMessageLite.checkIsLite(extensionLite);
            verifyExtensionContainingType(generatedExtensionCheckIsLite);
            Object field = this.extensions.getField(generatedExtensionCheckIsLite.f3941d);
            return field == null ? (Type) generatedExtensionCheckIsLite.f3939b : (Type) generatedExtensionCheckIsLite.b(field);
        }

        @Override // androidx.datastore.preferences.protobuf.GeneratedMessageLite.ExtendableMessageOrBuilder
        public final <Type> int getExtensionCount(ExtensionLite<MessageType, List<Type>> extensionLite) {
            GeneratedExtension<MessageType, ?> generatedExtensionCheckIsLite = GeneratedMessageLite.checkIsLite(extensionLite);
            verifyExtensionContainingType(generatedExtensionCheckIsLite);
            return this.extensions.getRepeatedFieldCount(generatedExtensionCheckIsLite.f3941d);
        }

        @Override // androidx.datastore.preferences.protobuf.GeneratedMessageLite.ExtendableMessageOrBuilder
        public final <Type> boolean hasExtension(ExtensionLite<MessageType, Type> extensionLite) {
            GeneratedExtension<MessageType, ?> generatedExtensionCheckIsLite = GeneratedMessageLite.checkIsLite(extensionLite);
            verifyExtensionContainingType(generatedExtensionCheckIsLite);
            return this.extensions.hasField(generatedExtensionCheckIsLite.f3941d);
        }

        @Override // androidx.datastore.preferences.protobuf.GeneratedMessageLite, androidx.datastore.preferences.protobuf.MessageLite
        public /* bridge */ /* synthetic */ MessageLite.Builder newBuilderForType() {
            return super.newBuilderForType();
        }

        @Override // androidx.datastore.preferences.protobuf.GeneratedMessageLite, androidx.datastore.preferences.protobuf.MessageLite
        public /* bridge */ /* synthetic */ MessageLite.Builder toBuilder() {
            return super.toBuilder();
        }

        @Override // androidx.datastore.preferences.protobuf.GeneratedMessageLite.ExtendableMessageOrBuilder
        public final <Type> Type getExtension(ExtensionLite<MessageType, List<Type>> extensionLite, int i2) {
            GeneratedExtension<MessageType, ?> generatedExtensionCheckIsLite = GeneratedMessageLite.checkIsLite(extensionLite);
            verifyExtensionContainingType(generatedExtensionCheckIsLite);
            return (Type) generatedExtensionCheckIsLite.c(this.extensions.getRepeatedField(generatedExtensionCheckIsLite.f3941d, i2));
        }
    }

    private static <T extends GeneratedMessageLite<T, ?>> T parsePartialFrom(T t2, byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (T) checkMessageInitialized(J(t2, bArr, 0, bArr.length, extensionRegistryLite));
    }
}
