package com.google.protobuf;

import androidx.health.connect.client.records.CervicalMucusRecord;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.google.protobuf.AbstractMessage;
import com.google.protobuf.Descriptors;
import com.google.protobuf.DynamicMessage;
import com.google.protobuf.FieldSet;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.LazyField;
import com.google.protobuf.Message;
import com.google.protobuf.MessageReflection;
import com.google.protobuf.UnknownFieldSet;
import com.google.protobuf.WireFormat;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/* loaded from: classes2.dex */
public abstract class GeneratedMessageV3 extends AbstractMessage implements Serializable {
    protected static boolean alwaysUseFieldBuilders = false;
    private static final long serialVersionUID = 1;
    protected UnknownFieldSet unknownFields;

    public static abstract class Builder<BuilderT extends Builder<BuilderT>> extends AbstractMessage.Builder<BuilderT> {
        private BuilderParent builderParent;
        private boolean isClean;
        private Builder<BuilderT>.BuilderParentImpl meAsParent;
        private Object unknownFieldsOrBuilder;

        private class BuilderParentImpl implements BuilderParent {
            private BuilderParentImpl() {
            }

            @Override // com.google.protobuf.AbstractMessage.BuilderParent
            public void markDirty() {
                Builder.this.p();
            }
        }

        protected Builder() {
            this(null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Map<Descriptors.FieldDescriptor, Object> getAllFieldsMutable() {
            TreeMap treeMap = new TreeMap();
            List<Descriptors.FieldDescriptor> fields = j().descriptor.getFields();
            int fieldCount = 0;
            while (fieldCount < fields.size()) {
                Descriptors.FieldDescriptor oneofFieldDescriptor = fields.get(fieldCount);
                Descriptors.OneofDescriptor containingOneof = oneofFieldDescriptor.getContainingOneof();
                if (containingOneof != null) {
                    fieldCount += containingOneof.getFieldCount() - 1;
                    if (hasOneof(containingOneof)) {
                        oneofFieldDescriptor = getOneofFieldDescriptor(containingOneof);
                        treeMap.put(oneofFieldDescriptor, getField(oneofFieldDescriptor));
                    }
                } else if (oneofFieldDescriptor.isRepeated()) {
                    List list = (List) getField(oneofFieldDescriptor);
                    if (!list.isEmpty()) {
                        treeMap.put(oneofFieldDescriptor, list);
                    }
                } else if (hasField(oneofFieldDescriptor)) {
                    treeMap.put(oneofFieldDescriptor, getField(oneofFieldDescriptor));
                }
                fieldCount++;
            }
            return treeMap;
        }

        private BuilderT setUnknownFieldsInternal(UnknownFieldSet unknownFieldSet) {
            this.unknownFieldsOrBuilder = unknownFieldSet;
            p();
            return this;
        }

        @Override // com.google.protobuf.AbstractMessage.Builder
        void a() {
            this.builderParent = null;
        }

        @Override // com.google.protobuf.AbstractMessage.Builder
        protected UnknownFieldSet.Builder b() {
            Object obj = this.unknownFieldsOrBuilder;
            if (obj instanceof UnknownFieldSet) {
                this.unknownFieldsOrBuilder = ((UnknownFieldSet) obj).toBuilder();
            }
            p();
            return (UnknownFieldSet.Builder) this.unknownFieldsOrBuilder;
        }

        @Override // com.google.protobuf.AbstractMessage.Builder
        protected void d() {
            this.isClean = true;
        }

        @Override // com.google.protobuf.AbstractMessage.Builder
        protected void g(UnknownFieldSet.Builder builder) {
            this.unknownFieldsOrBuilder = builder;
            p();
        }

        @Override // com.google.protobuf.MessageOrBuilder
        public Map<Descriptors.FieldDescriptor, Object> getAllFields() {
            return Collections.unmodifiableMap(getAllFieldsMutable());
        }

        public Descriptors.Descriptor getDescriptorForType() {
            return j().descriptor;
        }

        @Override // com.google.protobuf.MessageOrBuilder
        public Object getField(Descriptors.FieldDescriptor fieldDescriptor) {
            Object obj = j().getField(fieldDescriptor).get((Builder<?>) this);
            return fieldDescriptor.isRepeated() ? Collections.unmodifiableList((List) obj) : obj;
        }

        @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.Message.Builder
        public Message.Builder getFieldBuilder(Descriptors.FieldDescriptor fieldDescriptor) {
            return j().getField(fieldDescriptor).getBuilder(this);
        }

        @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageOrBuilder
        public Descriptors.FieldDescriptor getOneofFieldDescriptor(Descriptors.OneofDescriptor oneofDescriptor) {
            return j().getOneof(oneofDescriptor).get((Builder<?>) this);
        }

        @Override // com.google.protobuf.MessageOrBuilder
        public Object getRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, int i2) {
            return j().getField(fieldDescriptor).getRepeated((Builder<?>) this, i2);
        }

        @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.Message.Builder
        public Message.Builder getRepeatedFieldBuilder(Descriptors.FieldDescriptor fieldDescriptor, int i2) {
            return j().getField(fieldDescriptor).getRepeatedBuilder(this, i2);
        }

        @Override // com.google.protobuf.MessageOrBuilder
        public int getRepeatedFieldCount(Descriptors.FieldDescriptor fieldDescriptor) {
            return j().getField(fieldDescriptor).getRepeatedCount((Builder<?>) this);
        }

        @Override // com.google.protobuf.MessageOrBuilder
        public final UnknownFieldSet getUnknownFields() {
            Object obj = this.unknownFieldsOrBuilder;
            return obj instanceof UnknownFieldSet ? (UnknownFieldSet) obj : ((UnknownFieldSet.Builder) obj).buildPartial();
        }

        @Override // com.google.protobuf.MessageOrBuilder
        public boolean hasField(Descriptors.FieldDescriptor fieldDescriptor) {
            return j().getField(fieldDescriptor).has((Builder<?>) this);
        }

        @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageOrBuilder
        public boolean hasOneof(Descriptors.OneofDescriptor oneofDescriptor) {
            return j().getOneof(oneofDescriptor).has((Builder<?>) this);
        }

        protected BuilderParent i() {
            if (this.meAsParent == null) {
                this.meAsParent = new BuilderParentImpl();
            }
            return this.meAsParent;
        }

        @Override // com.google.protobuf.MessageLiteOrBuilder
        public boolean isInitialized() {
            for (Descriptors.FieldDescriptor fieldDescriptor : getDescriptorForType().getFields()) {
                if (fieldDescriptor.isRequired() && !hasField(fieldDescriptor)) {
                    return false;
                }
                if (fieldDescriptor.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                    if (fieldDescriptor.isRepeated()) {
                        Iterator it = ((List) getField(fieldDescriptor)).iterator();
                        while (it.hasNext()) {
                            if (!((Message) it.next()).isInitialized()) {
                                return false;
                            }
                        }
                    } else if (hasField(fieldDescriptor) && !((Message) getField(fieldDescriptor)).isInitialized()) {
                        return false;
                    }
                }
            }
            return true;
        }

        protected abstract FieldAccessorTable j();

        protected MapField k(int i2) {
            throw new IllegalArgumentException("No map fields found in " + getClass().getName());
        }

        protected MapField l(int i2) {
            throw new RuntimeException("No map fields found in " + getClass().getName());
        }

        protected boolean m() {
            return this.isClean;
        }

        protected final void n(int i2, int i3) {
            b().mergeVarintField(i2, i3);
        }

        @Override // com.google.protobuf.Message.Builder
        public Message.Builder newBuilderForField(Descriptors.FieldDescriptor fieldDescriptor) {
            return j().getField(fieldDescriptor).newBuilder();
        }

        protected void o() {
            if (this.builderParent != null) {
                d();
            }
        }

        protected final void p() {
            BuilderParent builderParent;
            if (!this.isClean || (builderParent = this.builderParent) == null) {
                return;
            }
            builderParent.markDirty();
            this.isClean = false;
        }

        protected boolean q(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite, int i2) {
            return codedInputStream.e() ? codedInputStream.skipField(i2) : b().mergeFieldFrom(i2, codedInputStream);
        }

        protected Builder(BuilderParent builderParent) {
            this.unknownFieldsOrBuilder = UnknownFieldSet.getDefaultInstance();
            this.builderParent = builderParent;
        }

        @Override // com.google.protobuf.Message.Builder
        public BuilderT addRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, Object obj) {
            j().getField(fieldDescriptor).addRepeated(this, obj);
            return this;
        }

        @Override // com.google.protobuf.Message.Builder
        public BuilderT clearField(Descriptors.FieldDescriptor fieldDescriptor) {
            j().getField(fieldDescriptor).clear(this);
            return this;
        }

        @Override // com.google.protobuf.Message.Builder
        public BuilderT setField(Descriptors.FieldDescriptor fieldDescriptor, Object obj) {
            j().getField(fieldDescriptor).set(this, obj);
            return this;
        }

        @Override // com.google.protobuf.Message.Builder
        public BuilderT setRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, int i2, Object obj) {
            j().getField(fieldDescriptor).setRepeated(this, i2, obj);
            return this;
        }

        public BuilderT setUnknownFields(UnknownFieldSet unknownFieldSet) {
            return (BuilderT) setUnknownFieldsInternal(unknownFieldSet);
        }

        @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.Message.Builder
        public BuilderT clearOneof(Descriptors.OneofDescriptor oneofDescriptor) {
            j().getOneof(oneofDescriptor).clear(this);
            return this;
        }

        @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.Message.Builder
        public BuilderT mergeUnknownFields(UnknownFieldSet unknownFieldSet) {
            if (UnknownFieldSet.getDefaultInstance().equals(unknownFieldSet)) {
                return this;
            }
            if (UnknownFieldSet.getDefaultInstance().equals(this.unknownFieldsOrBuilder)) {
                this.unknownFieldsOrBuilder = unknownFieldSet;
                p();
                return this;
            }
            b().mergeFrom(unknownFieldSet);
            p();
            return this;
        }

        @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
        public BuilderT clear() {
            this.unknownFieldsOrBuilder = UnknownFieldSet.getDefaultInstance();
            p();
            return this;
        }

        @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.AbstractMessageLite.Builder
        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public BuilderT mo65clone() {
            BuilderT buildert = (BuilderT) getDefaultInstanceForType().newBuilderForType();
            buildert.mergeFrom(buildPartial());
            return buildert;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public interface BuilderParent extends AbstractMessage.BuilderParent {
    }

    public static abstract class ExtendableBuilder<MessageT extends ExtendableMessage<MessageT>, BuilderT extends ExtendableBuilder<MessageT, BuilderT>> extends Builder<BuilderT> implements ExtendableMessageOrBuilder<MessageT> {
        private FieldSet.Builder<Descriptors.FieldDescriptor> extensions;

        protected ExtendableBuilder() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public FieldSet<Descriptors.FieldDescriptor> buildExtensions() {
            FieldSet.Builder<Descriptors.FieldDescriptor> builder = this.extensions;
            return builder == null ? FieldSet.emptySet() : builder.buildPartial();
        }

        private void ensureExtensionsIsMutable() {
            if (this.extensions == null) {
                this.extensions = FieldSet.newBuilder();
            }
        }

        private void verifyContainingType(Descriptors.FieldDescriptor fieldDescriptor) {
            if (fieldDescriptor.getContainingType() != getDescriptorForType()) {
                throw new IllegalArgumentException("FieldDescriptor does not match message type.");
            }
        }

        private void verifyExtensionContainingType(Extension<MessageT, ?> extension) {
            if (extension.getDescriptor().getContainingType() == getDescriptorForType()) {
                return;
            }
            throw new IllegalArgumentException("Extension is for type \"" + extension.getDescriptor().getContainingType().getFullName() + "\" which does not match message type \"" + getDescriptorForType().getFullName() + "\".");
        }

        public final <T> BuilderT addExtension(ExtensionLite<MessageT, List<T>> extensionLite, T t2) {
            Extension<MessageT, ?> extensionCheckNotLite = GeneratedMessageV3.checkNotLite(extensionLite);
            verifyExtensionContainingType(extensionCheckNotLite);
            ensureExtensionsIsMutable();
            this.extensions.addRepeatedField(extensionCheckNotLite.getDescriptor(), extensionCheckNotLite.e(t2));
            p();
            return this;
        }

        public final <T> BuilderT clearExtension(ExtensionLite<MessageT, T> extensionLite) {
            Extension<MessageT, ?> extensionCheckNotLite = GeneratedMessageV3.checkNotLite(extensionLite);
            verifyExtensionContainingType(extensionCheckNotLite);
            ensureExtensionsIsMutable();
            this.extensions.clearField(extensionCheckNotLite.getDescriptor());
            p();
            return this;
        }

        @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.MessageOrBuilder
        public Map<Descriptors.FieldDescriptor, Object> getAllFields() {
            Map allFieldsMutable = getAllFieldsMutable();
            FieldSet.Builder<Descriptors.FieldDescriptor> builder = this.extensions;
            if (builder != null) {
                allFieldsMutable.putAll(builder.getAllFields());
            }
            return Collections.unmodifiableMap(allFieldsMutable);
        }

        @Override // com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLiteOrBuilder, com.google.protobuf.MessageOrBuilder
        public /* bridge */ /* synthetic */ MessageLite getDefaultInstanceForType() {
            return getDefaultInstanceForType();
        }

        @Override // com.google.protobuf.GeneratedMessageV3.ExtendableMessageOrBuilder
        public final <T> T getExtension(ExtensionLite<MessageT, T> extensionLite) {
            Extension<MessageT, ?> extensionCheckNotLite = GeneratedMessageV3.checkNotLite(extensionLite);
            verifyExtensionContainingType(extensionCheckNotLite);
            Descriptors.FieldDescriptor descriptor = extensionCheckNotLite.getDescriptor();
            FieldSet.Builder<Descriptors.FieldDescriptor> builder = this.extensions;
            Object field = builder == null ? null : builder.getField(descriptor);
            return field == null ? descriptor.isRepeated() ? (T) Collections.emptyList() : descriptor.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE ? (T) extensionCheckNotLite.getMessageDefaultInstance() : (T) extensionCheckNotLite.b(descriptor.getDefaultValue()) : (T) extensionCheckNotLite.b(field);
        }

        @Override // com.google.protobuf.GeneratedMessageV3.ExtendableMessageOrBuilder
        public final <T> int getExtensionCount(ExtensionLite<MessageT, List<T>> extensionLite) {
            Extension<MessageT, ?> extensionCheckNotLite = GeneratedMessageV3.checkNotLite(extensionLite);
            verifyExtensionContainingType(extensionCheckNotLite);
            Descriptors.FieldDescriptor descriptor = extensionCheckNotLite.getDescriptor();
            FieldSet.Builder<Descriptors.FieldDescriptor> builder = this.extensions;
            if (builder == null) {
                return 0;
            }
            return builder.getRepeatedFieldCount(descriptor);
        }

        @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.MessageOrBuilder
        public Object getField(Descriptors.FieldDescriptor fieldDescriptor) {
            if (!fieldDescriptor.isExtension()) {
                return super.getField(fieldDescriptor);
            }
            verifyContainingType(fieldDescriptor);
            FieldSet.Builder<Descriptors.FieldDescriptor> builder = this.extensions;
            Object field = builder == null ? null : builder.getField(fieldDescriptor);
            return field == null ? fieldDescriptor.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE ? DynamicMessage.getDefaultInstance(fieldDescriptor.getMessageType()) : fieldDescriptor.getDefaultValue() : field;
        }

        @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.Message.Builder
        public Message.Builder getFieldBuilder(Descriptors.FieldDescriptor fieldDescriptor) {
            if (!fieldDescriptor.isExtension()) {
                return super.getFieldBuilder(fieldDescriptor);
            }
            verifyContainingType(fieldDescriptor);
            if (fieldDescriptor.getJavaType() != Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                throw new UnsupportedOperationException("getFieldBuilder() called on a non-Message type.");
            }
            ensureExtensionsIsMutable();
            Object objA = this.extensions.a(fieldDescriptor);
            if (objA == null) {
                DynamicMessage.Builder builderNewBuilder = DynamicMessage.newBuilder(fieldDescriptor.getMessageType());
                this.extensions.setField(fieldDescriptor, builderNewBuilder);
                p();
                return builderNewBuilder;
            }
            if (objA instanceof Message.Builder) {
                return (Message.Builder) objA;
            }
            if (!(objA instanceof Message)) {
                throw new UnsupportedOperationException("getRepeatedFieldBuilder() called on a non-Message type.");
            }
            Message.Builder builder = ((Message) objA).toBuilder();
            this.extensions.setField(fieldDescriptor, builder);
            p();
            return builder;
        }

        @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.MessageOrBuilder
        public Object getRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, int i2) {
            if (!fieldDescriptor.isExtension()) {
                return super.getRepeatedField(fieldDescriptor, i2);
            }
            verifyContainingType(fieldDescriptor);
            FieldSet.Builder<Descriptors.FieldDescriptor> builder = this.extensions;
            if (builder != null) {
                return builder.getRepeatedField(fieldDescriptor, i2);
            }
            throw new IndexOutOfBoundsException();
        }

        @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.Message.Builder
        public Message.Builder getRepeatedFieldBuilder(Descriptors.FieldDescriptor fieldDescriptor, int i2) {
            if (!fieldDescriptor.isExtension()) {
                return super.getRepeatedFieldBuilder(fieldDescriptor, i2);
            }
            verifyContainingType(fieldDescriptor);
            ensureExtensionsIsMutable();
            if (fieldDescriptor.getJavaType() != Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                throw new UnsupportedOperationException("getRepeatedFieldBuilder() called on a non-Message type.");
            }
            Object objB = this.extensions.b(fieldDescriptor, i2);
            if (objB instanceof Message.Builder) {
                return (Message.Builder) objB;
            }
            if (!(objB instanceof Message)) {
                throw new UnsupportedOperationException("getRepeatedFieldBuilder() called on a non-Message type.");
            }
            Message.Builder builder = ((Message) objB).toBuilder();
            this.extensions.setRepeatedField(fieldDescriptor, i2, builder);
            p();
            return builder;
        }

        @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.MessageOrBuilder
        public int getRepeatedFieldCount(Descriptors.FieldDescriptor fieldDescriptor) {
            if (!fieldDescriptor.isExtension()) {
                return super.getRepeatedFieldCount(fieldDescriptor);
            }
            verifyContainingType(fieldDescriptor);
            FieldSet.Builder<Descriptors.FieldDescriptor> builder = this.extensions;
            if (builder == null) {
                return 0;
            }
            return builder.getRepeatedFieldCount(fieldDescriptor);
        }

        @Override // com.google.protobuf.GeneratedMessageV3.ExtendableMessageOrBuilder
        public final <T> boolean hasExtension(ExtensionLite<MessageT, T> extensionLite) {
            Extension<MessageT, ?> extensionCheckNotLite = GeneratedMessageV3.checkNotLite(extensionLite);
            verifyExtensionContainingType(extensionCheckNotLite);
            FieldSet.Builder<Descriptors.FieldDescriptor> builder = this.extensions;
            return builder != null && builder.hasField(extensionCheckNotLite.getDescriptor());
        }

        @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.MessageOrBuilder
        public boolean hasField(Descriptors.FieldDescriptor fieldDescriptor) {
            if (!fieldDescriptor.isExtension()) {
                return super.hasField(fieldDescriptor);
            }
            verifyContainingType(fieldDescriptor);
            FieldSet.Builder<Descriptors.FieldDescriptor> builder = this.extensions;
            return builder != null && builder.hasField(fieldDescriptor);
        }

        @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.MessageLiteOrBuilder
        public boolean isInitialized() {
            return super.isInitialized() && s();
        }

        @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
        public Message.Builder newBuilderForField(Descriptors.FieldDescriptor fieldDescriptor) {
            return fieldDescriptor.isExtension() ? DynamicMessage.newBuilder(fieldDescriptor.getMessageType()) : super.newBuilderForField(fieldDescriptor);
        }

        @Override // com.google.protobuf.GeneratedMessageV3.Builder
        protected boolean q(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite, int i2) {
            ensureExtensionsIsMutable();
            return MessageReflection.e(codedInputStream, codedInputStream.e() ? null : b(), extensionRegistryLite, getDescriptorForType(), new MessageReflection.ExtensionBuilderAdapter(this.extensions), i2);
        }

        protected boolean s() {
            FieldSet.Builder<Descriptors.FieldDescriptor> builder = this.extensions;
            return builder == null || builder.isInitialized();
        }

        public final <T> BuilderT setExtension(ExtensionLite<MessageT, T> extensionLite, T t2) {
            Extension<MessageT, ?> extensionCheckNotLite = GeneratedMessageV3.checkNotLite(extensionLite);
            verifyExtensionContainingType(extensionCheckNotLite);
            ensureExtensionsIsMutable();
            this.extensions.setField(extensionCheckNotLite.getDescriptor(), extensionCheckNotLite.f(t2));
            p();
            return this;
        }

        protected final void t(ExtendableMessage extendableMessage) {
            if (extendableMessage.extensions != null) {
                ensureExtensionsIsMutable();
                this.extensions.mergeFrom(extendableMessage.extensions);
                p();
            }
        }

        protected ExtendableBuilder(BuilderParent builderParent) {
            super(builderParent);
        }

        @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
        public BuilderT addRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, Object obj) {
            if (fieldDescriptor.isExtension()) {
                verifyContainingType(fieldDescriptor);
                ensureExtensionsIsMutable();
                this.extensions.addRepeatedField(fieldDescriptor, obj);
                p();
                return this;
            }
            return (BuilderT) super.addRepeatedField(fieldDescriptor, obj);
        }

        @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
        public BuilderT clearField(Descriptors.FieldDescriptor fieldDescriptor) {
            if (fieldDescriptor.isExtension()) {
                verifyContainingType(fieldDescriptor);
                ensureExtensionsIsMutable();
                this.extensions.clearField(fieldDescriptor);
                p();
                return this;
            }
            return (BuilderT) super.clearField(fieldDescriptor);
        }

        @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
        public BuilderT setField(Descriptors.FieldDescriptor fieldDescriptor, Object obj) {
            if (fieldDescriptor.isExtension()) {
                verifyContainingType(fieldDescriptor);
                ensureExtensionsIsMutable();
                this.extensions.setField(fieldDescriptor, obj);
                p();
                return this;
            }
            return (BuilderT) super.setField(fieldDescriptor, obj);
        }

        @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.Message.Builder
        public BuilderT setRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, int i2, Object obj) {
            if (fieldDescriptor.isExtension()) {
                verifyContainingType(fieldDescriptor);
                ensureExtensionsIsMutable();
                this.extensions.setRepeatedField(fieldDescriptor, i2, obj);
                p();
                return this;
            }
            return (BuilderT) super.setRepeatedField(fieldDescriptor, i2, obj);
        }

        @Override // com.google.protobuf.GeneratedMessageV3.ExtendableMessageOrBuilder
        public final <T> boolean hasExtension(Extension<MessageT, T> extension) {
            return hasExtension((ExtensionLite) extension);
        }

        @Override // com.google.protobuf.GeneratedMessageV3.Builder, com.google.protobuf.AbstractMessage.Builder, com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
        public BuilderT clear() {
            this.extensions = null;
            return (BuilderT) super.clear();
        }

        @Override // com.google.protobuf.GeneratedMessageV3.ExtendableMessageOrBuilder
        public final <T> int getExtensionCount(Extension<MessageT, List<T>> extension) {
            return getExtensionCount((ExtensionLite) extension);
        }

        @Override // com.google.protobuf.GeneratedMessageV3.ExtendableMessageOrBuilder
        public final <T> boolean hasExtension(GeneratedMessage.GeneratedExtension<MessageT, T> generatedExtension) {
            return hasExtension((ExtensionLite) generatedExtension);
        }

        public final <T> BuilderT clearExtension(Extension<MessageT, T> extension) {
            return (BuilderT) clearExtension((ExtensionLite) extension);
        }

        @Override // com.google.protobuf.GeneratedMessageV3.ExtendableMessageOrBuilder
        public final <T> int getExtensionCount(GeneratedMessage.GeneratedExtension<MessageT, List<T>> generatedExtension) {
            return getExtensionCount((ExtensionLite) generatedExtension);
        }

        public final <T> BuilderT addExtension(Extension<MessageT, List<T>> extension, T t2) {
            return (BuilderT) addExtension(extension, (Extension<MessageT, List<T>>) t2);
        }

        public <T> BuilderT clearExtension(GeneratedMessage.GeneratedExtension<MessageT, T> generatedExtension) {
            return (BuilderT) clearExtension((ExtensionLite) generatedExtension);
        }

        public final <T> BuilderT setExtension(ExtensionLite<MessageT, List<T>> extensionLite, int i2, T t2) {
            Extension<MessageT, ?> extensionCheckNotLite = GeneratedMessageV3.checkNotLite(extensionLite);
            verifyExtensionContainingType(extensionCheckNotLite);
            ensureExtensionsIsMutable();
            this.extensions.setRepeatedField(extensionCheckNotLite.getDescriptor(), i2, extensionCheckNotLite.e(t2));
            p();
            return this;
        }

        public <T> BuilderT addExtension(GeneratedMessage.GeneratedExtension<MessageT, List<T>> generatedExtension, T t2) {
            return (BuilderT) addExtension((ExtensionLite<MessageT, List<GeneratedMessage.GeneratedExtension<MessageT, List<T>>>>) generatedExtension, (GeneratedMessage.GeneratedExtension<MessageT, List<T>>) t2);
        }

        @Override // com.google.protobuf.GeneratedMessageV3.ExtendableMessageOrBuilder
        public final <T> T getExtension(ExtensionLite<MessageT, List<T>> extensionLite, int i2) {
            Extension<MessageT, ?> extensionCheckNotLite = GeneratedMessageV3.checkNotLite(extensionLite);
            verifyExtensionContainingType(extensionCheckNotLite);
            Descriptors.FieldDescriptor descriptor = extensionCheckNotLite.getDescriptor();
            FieldSet.Builder<Descriptors.FieldDescriptor> builder = this.extensions;
            if (builder != null) {
                return (T) extensionCheckNotLite.d(builder.getRepeatedField(descriptor, i2));
            }
            throw new IndexOutOfBoundsException();
        }

        public final <T> BuilderT setExtension(Extension<MessageT, T> extension, T t2) {
            return (BuilderT) setExtension(extension, (Extension<MessageT, T>) t2);
        }

        public <T> BuilderT setExtension(GeneratedMessage.GeneratedExtension<MessageT, T> generatedExtension, T t2) {
            return (BuilderT) setExtension((ExtensionLite<MessageT, GeneratedMessage.GeneratedExtension<MessageT, T>>) generatedExtension, (GeneratedMessage.GeneratedExtension<MessageT, T>) t2);
        }

        public final <T> BuilderT setExtension(Extension<MessageT, List<T>> extension, int i2, T t2) {
            return (BuilderT) setExtension((ExtensionLite<MessageT, List<int>>) extension, i2, (int) t2);
        }

        public <T> BuilderT setExtension(GeneratedMessage.GeneratedExtension<MessageT, List<T>> generatedExtension, int i2, T t2) {
            return (BuilderT) setExtension((ExtensionLite<MessageT, List<int>>) generatedExtension, i2, (int) t2);
        }

        @Override // com.google.protobuf.GeneratedMessageV3.ExtendableMessageOrBuilder
        public final <T> T getExtension(Extension<MessageT, T> extension) {
            return (T) getExtension((ExtensionLite) extension);
        }

        @Override // com.google.protobuf.GeneratedMessageV3.ExtendableMessageOrBuilder
        public final <T> T getExtension(GeneratedMessage.GeneratedExtension<MessageT, T> generatedExtension) {
            return (T) getExtension((ExtensionLite) generatedExtension);
        }

        @Override // com.google.protobuf.GeneratedMessageV3.ExtendableMessageOrBuilder
        public final <T> T getExtension(Extension<MessageT, List<T>> extension, int i2) {
            return (T) getExtension((ExtensionLite) extension, i2);
        }

        @Override // com.google.protobuf.GeneratedMessageV3.ExtendableMessageOrBuilder
        public final <T> T getExtension(GeneratedMessage.GeneratedExtension<MessageT, List<T>> generatedExtension, int i2) {
            return (T) getExtension((ExtensionLite) generatedExtension, i2);
        }
    }

    public interface ExtendableMessageOrBuilder<MessageT extends ExtendableMessage<MessageT>> extends MessageOrBuilder {
        @Override // com.google.protobuf.MessageOrBuilder, com.google.protobuf.MessageLiteOrBuilder, com.google.protobuf.MessageOrBuilder
        Message getDefaultInstanceForType();

        @Override // com.google.protobuf.MessageOrBuilder
        /* bridge */ /* synthetic */ MessageLite getDefaultInstanceForType();

        <T> T getExtension(Extension<MessageT, T> extension);

        <T> T getExtension(Extension<MessageT, List<T>> extension, int i2);

        <T> T getExtension(ExtensionLite<MessageT, T> extensionLite);

        <T> T getExtension(ExtensionLite<MessageT, List<T>> extensionLite, int i2);

        <T> T getExtension(GeneratedMessage.GeneratedExtension<MessageT, T> generatedExtension);

        <T> T getExtension(GeneratedMessage.GeneratedExtension<MessageT, List<T>> generatedExtension, int i2);

        <T> int getExtensionCount(Extension<MessageT, List<T>> extension);

        <T> int getExtensionCount(ExtensionLite<MessageT, List<T>> extensionLite);

        <T> int getExtensionCount(GeneratedMessage.GeneratedExtension<MessageT, List<T>> generatedExtension);

        <T> boolean hasExtension(Extension<MessageT, T> extension);

        <T> boolean hasExtension(ExtensionLite<MessageT, T> extensionLite);

        <T> boolean hasExtension(GeneratedMessage.GeneratedExtension<MessageT, T> generatedExtension);
    }

    interface ExtensionDescriptorRetriever {
        Descriptors.FieldDescriptor getDescriptor();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static final class UnusedPrivateParameter {

        /* renamed from: a, reason: collision with root package name */
        static final UnusedPrivateParameter f15294a = new UnusedPrivateParameter();

        private UnusedPrivateParameter() {
        }
    }

    protected GeneratedMessageV3() {
        this.unknownFields = UnknownFieldSet.getDefaultInstance();
    }

    protected static boolean canUseUnsafe() {
        return UnsafeUtil.A() && UnsafeUtil.B();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <MessageT extends ExtendableMessage<MessageT>, T> Extension<MessageT, T> checkNotLite(ExtensionLite<MessageT, T> extensionLite) {
        if (extensionLite.a()) {
            throw new IllegalArgumentException("Expected non-lite extension.");
        }
        return (Extension) extensionLite;
    }

    protected static int computeStringSize(int i2, Object obj) {
        return obj instanceof String ? CodedOutputStream.computeStringSize(i2, (String) obj) : CodedOutputStream.computeBytesSize(i2, (ByteString) obj);
    }

    protected static int computeStringSizeNoTag(Object obj) {
        return obj instanceof String ? CodedOutputStream.computeStringSizeNoTag((String) obj) : CodedOutputStream.computeBytesSizeNoTag((ByteString) obj);
    }

    protected static Internal.BooleanList emptyBooleanList() {
        return BooleanArrayList.emptyList();
    }

    protected static Internal.DoubleList emptyDoubleList() {
        return DoubleArrayList.emptyList();
    }

    protected static Internal.FloatList emptyFloatList() {
        return FloatArrayList.emptyList();
    }

    protected static Internal.IntList emptyIntList() {
        return IntArrayList.emptyList();
    }

    protected static Internal.LongList emptyLongList() {
        return LongArrayList.emptyList();
    }

    static void enableAlwaysUseFieldBuildersForTesting() {
        setAlwaysUseFieldBuildersForTesting(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0066  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.util.Map<com.google.protobuf.Descriptors.FieldDescriptor, java.lang.Object> getAllFieldsMutable(boolean r7) {
        /*
            r6 = this;
            java.util.TreeMap r0 = new java.util.TreeMap
            r0.<init>()
            com.google.protobuf.GeneratedMessageV3$FieldAccessorTable r1 = r6.internalGetFieldAccessorTable()
            com.google.protobuf.Descriptors$Descriptor r1 = com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.a(r1)
            java.util.List r1 = r1.getFields()
            r2 = 0
        L12:
            int r3 = r1.size()
            if (r2 >= r3) goto L70
            java.lang.Object r3 = r1.get(r2)
            com.google.protobuf.Descriptors$FieldDescriptor r3 = (com.google.protobuf.Descriptors.FieldDescriptor) r3
            com.google.protobuf.Descriptors$OneofDescriptor r4 = r3.getContainingOneof()
            if (r4 == 0) goto L37
            int r3 = r4.getFieldCount()
            int r3 = r3 + (-1)
            int r2 = r2 + r3
            boolean r3 = r6.hasOneof(r4)
            if (r3 != 0) goto L32
            goto L6d
        L32:
            com.google.protobuf.Descriptors$FieldDescriptor r3 = r6.getOneofFieldDescriptor(r4)
            goto L54
        L37:
            boolean r4 = r3.isRepeated()
            if (r4 == 0) goto L4d
            java.lang.Object r4 = r6.getField(r3)
            java.util.List r4 = (java.util.List) r4
            boolean r5 = r4.isEmpty()
            if (r5 != 0) goto L6d
            r0.put(r3, r4)
            goto L6d
        L4d:
            boolean r4 = r6.hasField(r3)
            if (r4 != 0) goto L54
            goto L6d
        L54:
            if (r7 == 0) goto L66
            com.google.protobuf.Descriptors$FieldDescriptor$JavaType r4 = r3.getJavaType()
            com.google.protobuf.Descriptors$FieldDescriptor$JavaType r5 = com.google.protobuf.Descriptors.FieldDescriptor.JavaType.STRING
            if (r4 != r5) goto L66
            java.lang.Object r4 = r6.getFieldRaw(r3)
            r0.put(r3, r4)
            goto L6d
        L66:
            java.lang.Object r4 = r6.getField(r3)
            r0.put(r3, r4)
        L6d:
            int r2 = r2 + 1
            goto L12
        L70:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.GeneratedMessageV3.getAllFieldsMutable(boolean):java.util.Map");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.reflect.Method getMethodOrDie(Class<?> cls, String str, Class<?>... clsArr) {
        try {
            return cls.getMethod(str, clsArr);
        } catch (NoSuchMethodException e2) {
            throw new IllegalStateException("Generated message class \"" + cls.getName() + "\" missing method \"" + str + "\".", e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @CanIgnoreReturnValue
    public static Object invokeOrDie(java.lang.reflect.Method method, Object obj, Object... objArr) {
        try {
            return method.invoke(obj, objArr);
        } catch (IllegalAccessException e2) {
            throw new IllegalStateException("Couldn't use Java reflection to implement protocol message reflection.", e2);
        } catch (InvocationTargetException e3) {
            Throwable cause = e3.getCause();
            if (cause instanceof RuntimeException) {
                throw ((RuntimeException) cause);
            }
            if (cause instanceof Error) {
                throw ((Error) cause);
            }
            throw new IllegalStateException("Unexpected exception thrown by generated accessor method.", cause);
        }
    }

    protected static boolean isStringEmpty(Object obj) {
        return obj instanceof String ? ((String) obj).isEmpty() : ((ByteString) obj).isEmpty();
    }

    protected static <ListT extends Internal.ProtobufList<?>> ListT makeMutableCopy(ListT listt) {
        int size = listt.size();
        return (ListT) listt.mutableCopyWithCapacity2(size == 0 ? 10 : size * 2);
    }

    private static <V> void maybeSerializeBooleanEntryTo(CodedOutputStream codedOutputStream, Map<Boolean, V> map, MapEntry<Boolean, V> mapEntry, int i2, boolean z2) throws IOException {
        if (map.containsKey(Boolean.valueOf(z2))) {
            codedOutputStream.writeMessage(i2, mapEntry.newBuilderForType().setKey(Boolean.valueOf(z2)).setValue(map.get(Boolean.valueOf(z2))).build());
        }
    }

    protected static Internal.IntList mutableCopy(Internal.IntList intList) {
        return (Internal.IntList) makeMutableCopy(intList);
    }

    protected static Internal.BooleanList newBooleanList() {
        return new BooleanArrayList();
    }

    protected static Internal.DoubleList newDoubleList() {
        return new DoubleArrayList();
    }

    protected static Internal.FloatList newFloatList() {
        return new FloatArrayList();
    }

    protected static Internal.IntList newIntList() {
        return new IntArrayList();
    }

    protected static Internal.LongList newLongList() {
        return new LongArrayList();
    }

    protected static <M extends Message> M parseDelimitedWithIOException(Parser<M> parser, InputStream inputStream) throws IOException {
        try {
            return parser.parseDelimitedFrom(inputStream);
        } catch (InvalidProtocolBufferException e2) {
            throw e2.unwrapIOException();
        }
    }

    protected static <M extends Message> M parseWithIOException(Parser<M> parser, InputStream inputStream) throws IOException {
        try {
            return parser.parseFrom(inputStream);
        } catch (InvalidProtocolBufferException e2) {
            throw e2.unwrapIOException();
        }
    }

    protected static <V> void serializeBooleanMapTo(CodedOutputStream codedOutputStream, MapField<Boolean, V> mapField, MapEntry<Boolean, V> mapEntry, int i2) throws IOException {
        Map<Boolean, V> map = mapField.getMap();
        if (!codedOutputStream.i()) {
            serializeMapTo(codedOutputStream, map, mapEntry, i2);
        } else {
            maybeSerializeBooleanEntryTo(codedOutputStream, map, mapEntry, i2, false);
            maybeSerializeBooleanEntryTo(codedOutputStream, map, mapEntry, i2, true);
        }
    }

    protected static <V> void serializeIntegerMapTo(CodedOutputStream codedOutputStream, MapField<Integer, V> mapField, MapEntry<Integer, V> mapEntry, int i2) throws IOException {
        Map<Integer, V> map = mapField.getMap();
        if (!codedOutputStream.i()) {
            serializeMapTo(codedOutputStream, map, mapEntry, i2);
            return;
        }
        int size = map.size();
        int[] iArr = new int[size];
        Iterator<Integer> it = map.keySet().iterator();
        int i3 = 0;
        while (it.hasNext()) {
            iArr[i3] = it.next().intValue();
            i3++;
        }
        Arrays.sort(iArr);
        for (int i4 = 0; i4 < size; i4++) {
            int i5 = iArr[i4];
            codedOutputStream.writeMessage(i2, mapEntry.newBuilderForType().setKey(Integer.valueOf(i5)).setValue(map.get(Integer.valueOf(i5))).build());
        }
    }

    protected static <V> void serializeLongMapTo(CodedOutputStream codedOutputStream, MapField<Long, V> mapField, MapEntry<Long, V> mapEntry, int i2) throws IOException {
        Map<Long, V> map = mapField.getMap();
        if (!codedOutputStream.i()) {
            serializeMapTo(codedOutputStream, map, mapEntry, i2);
            return;
        }
        int size = map.size();
        long[] jArr = new long[size];
        Iterator<Long> it = map.keySet().iterator();
        int i3 = 0;
        while (it.hasNext()) {
            jArr[i3] = it.next().longValue();
            i3++;
        }
        Arrays.sort(jArr);
        for (int i4 = 0; i4 < size; i4++) {
            long j2 = jArr[i4];
            codedOutputStream.writeMessage(i2, mapEntry.newBuilderForType().setKey(Long.valueOf(j2)).setValue(map.get(Long.valueOf(j2))).build());
        }
    }

    private static <K, V> void serializeMapTo(CodedOutputStream codedOutputStream, Map<K, V> map, MapEntry<K, V> mapEntry, int i2) throws IOException {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            codedOutputStream.writeMessage(i2, mapEntry.newBuilderForType().setKey(entry.getKey()).setValue(entry.getValue()).build());
        }
    }

    protected static <V> void serializeStringMapTo(CodedOutputStream codedOutputStream, MapField<String, V> mapField, MapEntry<String, V> mapEntry, int i2) throws IOException {
        Map<String, V> map = mapField.getMap();
        if (!codedOutputStream.i()) {
            serializeMapTo(codedOutputStream, map, mapEntry, i2);
            return;
        }
        String[] strArr = (String[]) map.keySet().toArray(new String[map.size()]);
        Arrays.sort(strArr);
        for (String str : strArr) {
            codedOutputStream.writeMessage(i2, mapEntry.newBuilderForType().setKey(str).setValue(map.get(str)).build());
        }
    }

    static void setAlwaysUseFieldBuildersForTesting(boolean z2) {
        alwaysUseFieldBuilders = z2;
    }

    protected static void writeString(CodedOutputStream codedOutputStream, int i2, Object obj) throws IOException {
        if (obj instanceof String) {
            codedOutputStream.writeString(i2, (String) obj);
        } else {
            codedOutputStream.writeBytes(i2, (ByteString) obj);
        }
    }

    protected static void writeStringNoTag(CodedOutputStream codedOutputStream, Object obj) throws IOException {
        if (obj instanceof String) {
            codedOutputStream.writeStringNoTag((String) obj);
        } else {
            codedOutputStream.writeBytesNoTag((ByteString) obj);
        }
    }

    @Override // com.google.protobuf.MessageOrBuilder
    public Map<Descriptors.FieldDescriptor, Object> getAllFields() {
        return Collections.unmodifiableMap(getAllFieldsMutable(false));
    }

    Map<Descriptors.FieldDescriptor, Object> getAllFieldsRaw() {
        return Collections.unmodifiableMap(getAllFieldsMutable(true));
    }

    @Override // com.google.protobuf.MessageOrBuilder
    public Descriptors.Descriptor getDescriptorForType() {
        return internalGetFieldAccessorTable().descriptor;
    }

    @Override // com.google.protobuf.MessageOrBuilder
    public Object getField(Descriptors.FieldDescriptor fieldDescriptor) {
        return internalGetFieldAccessorTable().getField(fieldDescriptor).get(this);
    }

    Object getFieldRaw(Descriptors.FieldDescriptor fieldDescriptor) {
        return internalGetFieldAccessorTable().getField(fieldDescriptor).getRaw(this);
    }

    @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.MessageOrBuilder
    public Descriptors.FieldDescriptor getOneofFieldDescriptor(Descriptors.OneofDescriptor oneofDescriptor) {
        return internalGetFieldAccessorTable().getOneof(oneofDescriptor).get(this);
    }

    @Override // com.google.protobuf.MessageLite, com.google.protobuf.Message
    public Parser<? extends GeneratedMessageV3> getParserForType() {
        throw new UnsupportedOperationException("This is supposed to be overridden by subclasses.");
    }

    @Override // com.google.protobuf.MessageOrBuilder
    public Object getRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, int i2) {
        return internalGetFieldAccessorTable().getField(fieldDescriptor).getRepeated(this, i2);
    }

    @Override // com.google.protobuf.MessageOrBuilder
    public int getRepeatedFieldCount(Descriptors.FieldDescriptor fieldDescriptor) {
        return internalGetFieldAccessorTable().getField(fieldDescriptor).getRepeatedCount(this);
    }

    @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite
    public int getSerializedSize() {
        int i2 = this.memoizedSize;
        if (i2 != -1) {
            return i2;
        }
        int iC = MessageReflection.c(this, getAllFieldsRaw());
        this.memoizedSize = iC;
        return iC;
    }

    @Override // com.google.protobuf.MessageOrBuilder
    public UnknownFieldSet getUnknownFields() {
        return this.unknownFields;
    }

    @Override // com.google.protobuf.MessageOrBuilder
    public boolean hasField(Descriptors.FieldDescriptor fieldDescriptor) {
        return internalGetFieldAccessorTable().getField(fieldDescriptor).has(this);
    }

    @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.MessageOrBuilder
    public boolean hasOneof(Descriptors.OneofDescriptor oneofDescriptor) {
        return internalGetFieldAccessorTable().getOneof(oneofDescriptor).has(this);
    }

    protected abstract FieldAccessorTable internalGetFieldAccessorTable();

    protected MapField internalGetMapField(int i2) {
        throw new IllegalArgumentException("No map fields found in " + getClass().getName());
    }

    @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLiteOrBuilder
    public boolean isInitialized() {
        for (Descriptors.FieldDescriptor fieldDescriptor : getDescriptorForType().getFields()) {
            if (fieldDescriptor.isRequired() && !hasField(fieldDescriptor)) {
                return false;
            }
            if (fieldDescriptor.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                if (fieldDescriptor.isRepeated()) {
                    Iterator it = ((List) getField(fieldDescriptor)).iterator();
                    while (it.hasNext()) {
                        if (!((Message) it.next()).isInitialized()) {
                            return false;
                        }
                    }
                } else if (hasField(fieldDescriptor) && !((Message) getField(fieldDescriptor)).isInitialized()) {
                    return false;
                }
            }
        }
        return true;
    }

    protected void makeExtensionsImmutable() {
    }

    @Deprecated
    protected void mergeFromAndMakeImmutableInternal(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        Schema schemaSchemaFor = Protobuf.getInstance().schemaFor((Protobuf) this);
        try {
            schemaSchemaFor.mergeFrom(this, CodedInputStreamReader.forCodedInput(codedInputStream), extensionRegistryLite);
            schemaSchemaFor.makeImmutable(this);
        } catch (InvalidProtocolBufferException e2) {
            throw e2.setUnfinishedMessage(this);
        } catch (IOException e3) {
            throw new InvalidProtocolBufferException(e3).setUnfinishedMessage(this);
        }
    }

    @Override // com.google.protobuf.AbstractMessage
    protected Message.Builder newBuilderForType(final AbstractMessage.BuilderParent builderParent) {
        return newBuilderForType(new BuilderParent() { // from class: com.google.protobuf.GeneratedMessageV3.1
            @Override // com.google.protobuf.AbstractMessage.BuilderParent
            public void markDirty() {
                builderParent.markDirty();
            }
        });
    }

    protected abstract Message.Builder newBuilderForType(BuilderParent builderParent);

    protected Object newInstance(UnusedPrivateParameter unusedPrivateParameter) {
        throw new UnsupportedOperationException("This method must be overridden by the subclass.");
    }

    protected boolean parseUnknownField(CodedInputStream codedInputStream, UnknownFieldSet.Builder builder, ExtensionRegistryLite extensionRegistryLite, int i2) throws IOException {
        return codedInputStream.e() ? codedInputStream.skipField(i2) : builder.mergeFieldFrom(i2, codedInputStream);
    }

    protected boolean parseUnknownFieldProto3(CodedInputStream codedInputStream, UnknownFieldSet.Builder builder, ExtensionRegistryLite extensionRegistryLite, int i2) throws IOException {
        return parseUnknownField(codedInputStream, builder, extensionRegistryLite, i2);
    }

    void setUnknownFields(UnknownFieldSet unknownFieldSet) {
        this.unknownFields = unknownFieldSet;
    }

    protected Object writeReplace() throws ObjectStreamException {
        return new GeneratedMessageLite.SerializedForm(this);
    }

    @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLite
    public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
        MessageReflection.g(this, getAllFieldsRaw(), codedOutputStream, false);
    }

    public static abstract class ExtendableMessage<MessageT extends ExtendableMessage<MessageT>> extends GeneratedMessageV3 implements ExtendableMessageOrBuilder<MessageT> {
        private static final long serialVersionUID = 1;
        private final FieldSet<Descriptors.FieldDescriptor> extensions;

        protected class ExtensionWriter {
            private final Iterator<Map.Entry<Descriptors.FieldDescriptor, Object>> iter;
            private final boolean messageSetWireFormat;
            private Map.Entry<Descriptors.FieldDescriptor, Object> next;

            public void writeUntil(int i2, CodedOutputStream codedOutputStream) throws IOException {
                while (true) {
                    Map.Entry<Descriptors.FieldDescriptor, Object> entry = this.next;
                    if (entry == null || entry.getKey().getNumber() >= i2) {
                        return;
                    }
                    Descriptors.FieldDescriptor key = this.next.getKey();
                    if (!this.messageSetWireFormat || key.getLiteJavaType() != WireFormat.JavaType.MESSAGE || key.isRepeated()) {
                        FieldSet.writeField(key, this.next.getValue(), codedOutputStream);
                    } else if (this.next instanceof LazyField.LazyEntry) {
                        codedOutputStream.writeRawMessageSetExtension(key.getNumber(), ((LazyField.LazyEntry) this.next).getField().toByteString());
                    } else {
                        codedOutputStream.writeMessageSetExtension(key.getNumber(), (Message) this.next.getValue());
                    }
                    if (this.iter.hasNext()) {
                        this.next = this.iter.next();
                    } else {
                        this.next = null;
                    }
                }
            }

            private ExtensionWriter(boolean z2) {
                Iterator<Map.Entry<Descriptors.FieldDescriptor, Object>> it = ExtendableMessage.this.extensions.iterator();
                this.iter = it;
                if (it.hasNext()) {
                    this.next = it.next();
                }
                this.messageSetWireFormat = z2;
            }
        }

        protected ExtendableMessage() {
            this.extensions = FieldSet.newFieldSet();
        }

        private void verifyContainingType(Descriptors.FieldDescriptor fieldDescriptor) {
            if (fieldDescriptor.getContainingType() != getDescriptorForType()) {
                throw new IllegalArgumentException("FieldDescriptor does not match message type.");
            }
        }

        private void verifyExtensionContainingType(Extension<MessageT, ?> extension) {
            if (extension.getDescriptor().getContainingType() == getDescriptorForType()) {
                return;
            }
            throw new IllegalArgumentException("Extension is for type \"" + extension.getDescriptor().getContainingType().getFullName() + "\" which does not match message type \"" + getDescriptorForType().getFullName() + "\".");
        }

        protected boolean extensionsAreInitialized() {
            return this.extensions.isInitialized();
        }

        protected int extensionsSerializedSize() {
            return this.extensions.getSerializedSize();
        }

        protected int extensionsSerializedSizeAsMessageSet() {
            return this.extensions.getMessageSetSerializedSize();
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.MessageOrBuilder
        public Map<Descriptors.FieldDescriptor, Object> getAllFields() {
            Map allFieldsMutable = getAllFieldsMutable(false);
            allFieldsMutable.putAll(getExtensionFields());
            return Collections.unmodifiableMap(allFieldsMutable);
        }

        @Override // com.google.protobuf.GeneratedMessageV3
        public Map<Descriptors.FieldDescriptor, Object> getAllFieldsRaw() {
            Map allFieldsMutable = getAllFieldsMutable(false);
            allFieldsMutable.putAll(getExtensionFields());
            return Collections.unmodifiableMap(allFieldsMutable);
        }

        @Override // com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLiteOrBuilder, com.google.protobuf.MessageOrBuilder
        public /* bridge */ /* synthetic */ MessageLite getDefaultInstanceForType() {
            return getDefaultInstanceForType();
        }

        @Override // com.google.protobuf.GeneratedMessageV3.ExtendableMessageOrBuilder
        public final <T> T getExtension(ExtensionLite<MessageT, T> extensionLite) {
            Extension<MessageT, ?> extensionCheckNotLite = GeneratedMessageV3.checkNotLite(extensionLite);
            verifyExtensionContainingType(extensionCheckNotLite);
            Descriptors.FieldDescriptor descriptor = extensionCheckNotLite.getDescriptor();
            Object field = this.extensions.getField(descriptor);
            return field == null ? descriptor.isRepeated() ? (T) Collections.emptyList() : descriptor.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE ? (T) extensionCheckNotLite.getMessageDefaultInstance() : (T) extensionCheckNotLite.b(descriptor.getDefaultValue()) : (T) extensionCheckNotLite.b(field);
        }

        @Override // com.google.protobuf.GeneratedMessageV3.ExtendableMessageOrBuilder
        public final <T> int getExtensionCount(ExtensionLite<MessageT, List<T>> extensionLite) {
            Extension<MessageT, ?> extensionCheckNotLite = GeneratedMessageV3.checkNotLite(extensionLite);
            verifyExtensionContainingType(extensionCheckNotLite);
            return this.extensions.getRepeatedFieldCount(extensionCheckNotLite.getDescriptor());
        }

        protected Map<Descriptors.FieldDescriptor, Object> getExtensionFields() {
            return this.extensions.getAllFields();
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.MessageOrBuilder
        public Object getField(Descriptors.FieldDescriptor fieldDescriptor) {
            if (!fieldDescriptor.isExtension()) {
                return super.getField(fieldDescriptor);
            }
            verifyContainingType(fieldDescriptor);
            Object field = this.extensions.getField(fieldDescriptor);
            return field == null ? fieldDescriptor.isRepeated() ? Collections.emptyList() : fieldDescriptor.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE ? DynamicMessage.getDefaultInstance(fieldDescriptor.getMessageType()) : fieldDescriptor.getDefaultValue() : field;
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.MessageOrBuilder
        public Object getRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, int i2) {
            if (!fieldDescriptor.isExtension()) {
                return super.getRepeatedField(fieldDescriptor, i2);
            }
            verifyContainingType(fieldDescriptor);
            return this.extensions.getRepeatedField(fieldDescriptor, i2);
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.MessageOrBuilder
        public int getRepeatedFieldCount(Descriptors.FieldDescriptor fieldDescriptor) {
            if (!fieldDescriptor.isExtension()) {
                return super.getRepeatedFieldCount(fieldDescriptor);
            }
            verifyContainingType(fieldDescriptor);
            return this.extensions.getRepeatedFieldCount(fieldDescriptor);
        }

        @Override // com.google.protobuf.GeneratedMessageV3.ExtendableMessageOrBuilder
        public final <T> boolean hasExtension(ExtensionLite<MessageT, T> extensionLite) {
            Extension<MessageT, ?> extensionCheckNotLite = GeneratedMessageV3.checkNotLite(extensionLite);
            verifyExtensionContainingType(extensionCheckNotLite);
            return this.extensions.hasField(extensionCheckNotLite.getDescriptor());
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.MessageOrBuilder
        public boolean hasField(Descriptors.FieldDescriptor fieldDescriptor) {
            if (!fieldDescriptor.isExtension()) {
                return super.hasField(fieldDescriptor);
            }
            verifyContainingType(fieldDescriptor);
            return this.extensions.hasField(fieldDescriptor);
        }

        @Override // com.google.protobuf.GeneratedMessageV3, com.google.protobuf.AbstractMessage, com.google.protobuf.MessageLiteOrBuilder
        public boolean isInitialized() {
            return super.isInitialized() && extensionsAreInitialized();
        }

        @Override // com.google.protobuf.GeneratedMessageV3
        protected void makeExtensionsImmutable() {
            this.extensions.makeImmutable();
        }

        protected ExtendableMessage<MessageT>.ExtensionWriter newExtensionWriter() {
            return new ExtensionWriter(false);
        }

        protected ExtendableMessage<MessageT>.ExtensionWriter newMessageSetExtensionWriter() {
            return new ExtensionWriter(true);
        }

        @Override // com.google.protobuf.GeneratedMessageV3
        protected boolean parseUnknownField(CodedInputStream codedInputStream, UnknownFieldSet.Builder builder, ExtensionRegistryLite extensionRegistryLite, int i2) throws IOException {
            if (codedInputStream.e()) {
                builder = null;
            }
            return MessageReflection.e(codedInputStream, builder, extensionRegistryLite, getDescriptorForType(), new MessageReflection.ExtensionAdapter(this.extensions), i2);
        }

        @Override // com.google.protobuf.GeneratedMessageV3
        protected boolean parseUnknownFieldProto3(CodedInputStream codedInputStream, UnknownFieldSet.Builder builder, ExtensionRegistryLite extensionRegistryLite, int i2) throws IOException {
            return parseUnknownField(codedInputStream, builder, extensionRegistryLite, i2);
        }

        protected ExtendableMessage(ExtendableBuilder<MessageT, ?> extendableBuilder) {
            super(extendableBuilder);
            this.extensions = extendableBuilder.buildExtensions();
        }

        @Override // com.google.protobuf.GeneratedMessageV3.ExtendableMessageOrBuilder
        public final <T> boolean hasExtension(Extension<MessageT, T> extension) {
            return hasExtension((ExtensionLite) extension);
        }

        @Override // com.google.protobuf.GeneratedMessageV3.ExtendableMessageOrBuilder
        public final <T> int getExtensionCount(Extension<MessageT, List<T>> extension) {
            return getExtensionCount((ExtensionLite) extension);
        }

        @Override // com.google.protobuf.GeneratedMessageV3.ExtendableMessageOrBuilder
        public final <T> boolean hasExtension(GeneratedMessage.GeneratedExtension<MessageT, T> generatedExtension) {
            return hasExtension((ExtensionLite) generatedExtension);
        }

        @Override // com.google.protobuf.GeneratedMessageV3.ExtendableMessageOrBuilder
        public final <T> int getExtensionCount(GeneratedMessage.GeneratedExtension<MessageT, List<T>> generatedExtension) {
            return getExtensionCount((ExtensionLite) generatedExtension);
        }

        @Override // com.google.protobuf.GeneratedMessageV3.ExtendableMessageOrBuilder
        public final <T> T getExtension(ExtensionLite<MessageT, List<T>> extensionLite, int i2) {
            Extension<MessageT, ?> extensionCheckNotLite = GeneratedMessageV3.checkNotLite(extensionLite);
            verifyExtensionContainingType(extensionCheckNotLite);
            return (T) extensionCheckNotLite.d(this.extensions.getRepeatedField(extensionCheckNotLite.getDescriptor(), i2));
        }

        @Override // com.google.protobuf.GeneratedMessageV3.ExtendableMessageOrBuilder
        public final <T> T getExtension(Extension<MessageT, T> extension) {
            return (T) getExtension((ExtensionLite) extension);
        }

        @Override // com.google.protobuf.GeneratedMessageV3.ExtendableMessageOrBuilder
        public final <T> T getExtension(GeneratedMessage.GeneratedExtension<MessageT, T> generatedExtension) {
            return (T) getExtension((ExtensionLite) generatedExtension);
        }

        @Override // com.google.protobuf.GeneratedMessageV3.ExtendableMessageOrBuilder
        public final <T> T getExtension(Extension<MessageT, List<T>> extension, int i2) {
            return (T) getExtension((ExtensionLite) extension, i2);
        }

        @Override // com.google.protobuf.GeneratedMessageV3.ExtendableMessageOrBuilder
        public final <T> T getExtension(GeneratedMessage.GeneratedExtension<MessageT, List<T>> generatedExtension, int i2) {
            return (T) getExtension((ExtensionLite) generatedExtension, i2);
        }
    }

    public static final class FieldAccessorTable {
        private String[] camelCaseNames;
        private final Descriptors.Descriptor descriptor;
        private final FieldAccessor[] fields;
        private volatile boolean initialized;
        private final OneofAccessor[] oneofs;

        private interface FieldAccessor {
            void addRepeated(Builder<?> builder, Object obj);

            void clear(Builder<?> builder);

            Object get(Builder<?> builder);

            Object get(GeneratedMessageV3 generatedMessageV3);

            Message.Builder getBuilder(Builder<?> builder);

            Object getRaw(GeneratedMessageV3 generatedMessageV3);

            Object getRepeated(Builder<?> builder, int i2);

            Object getRepeated(GeneratedMessageV3 generatedMessageV3, int i2);

            Message.Builder getRepeatedBuilder(Builder<?> builder, int i2);

            int getRepeatedCount(Builder<?> builder);

            int getRepeatedCount(GeneratedMessageV3 generatedMessageV3);

            boolean has(Builder<?> builder);

            boolean has(GeneratedMessageV3 generatedMessageV3);

            Message.Builder newBuilder();

            void set(Builder<?> builder, Object obj);

            void setRepeated(Builder<?> builder, int i2, Object obj);
        }

        private static class MapFieldAccessor implements FieldAccessor {
            private final Descriptors.FieldDescriptor field;
            private final Message mapEntryMessageDefaultInstance;

            MapFieldAccessor(Descriptors.FieldDescriptor fieldDescriptor, Class cls) {
                this.field = fieldDescriptor;
                this.mapEntryMessageDefaultInstance = getMapField((GeneratedMessageV3) GeneratedMessageV3.invokeOrDie(GeneratedMessageV3.getMethodOrDie(cls, "getDefaultInstance", new Class[0]), null, new Object[0])).b();
            }

            private Message coerceType(Message message) {
                if (message == null) {
                    return null;
                }
                return this.mapEntryMessageDefaultInstance.getClass().isInstance(message) ? message : this.mapEntryMessageDefaultInstance.toBuilder().mergeFrom(message).build();
            }

            private MapField<?, ?> getMapField(GeneratedMessageV3 generatedMessageV3) {
                return generatedMessageV3.internalGetMapField(this.field.getNumber());
            }

            private MapField<?, ?> getMutableMapField(Builder<?> builder) {
                return builder.l(this.field.getNumber());
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public void addRepeated(Builder<?> builder, Object obj) {
                getMutableMapField(builder).c().add(coerceType((Message) obj));
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public void clear(Builder<?> builder) {
                getMutableMapField(builder).c().clear();
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public Object get(GeneratedMessageV3 generatedMessageV3) {
                ArrayList arrayList = new ArrayList();
                for (int i2 = 0; i2 < getRepeatedCount(generatedMessageV3); i2++) {
                    arrayList.add(getRepeated(generatedMessageV3, i2));
                }
                return Collections.unmodifiableList(arrayList);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public Message.Builder getBuilder(Builder<?> builder) {
                throw new UnsupportedOperationException("Nested builder not supported for map fields.");
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public Object getRaw(GeneratedMessageV3 generatedMessageV3) {
                return get(generatedMessageV3);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public Object getRepeated(GeneratedMessageV3 generatedMessageV3, int i2) {
                return getMapField(generatedMessageV3).a().get(i2);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public Message.Builder getRepeatedBuilder(Builder<?> builder, int i2) {
                throw new UnsupportedOperationException("Map fields cannot be repeated");
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public int getRepeatedCount(GeneratedMessageV3 generatedMessageV3) {
                return getMapField(generatedMessageV3).a().size();
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public boolean has(GeneratedMessageV3 generatedMessageV3) {
                throw new UnsupportedOperationException("hasField() is not supported for repeated fields.");
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public Message.Builder newBuilder() {
                return this.mapEntryMessageDefaultInstance.newBuilderForType();
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public void set(Builder<?> builder, Object obj) {
                clear(builder);
                Iterator it = ((List) obj).iterator();
                while (it.hasNext()) {
                    addRepeated(builder, it.next());
                }
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public void setRepeated(Builder<?> builder, int i2, Object obj) {
                getMutableMapField(builder).c().set(i2, coerceType((Message) obj));
            }

            private MapField<?, ?> getMapField(Builder<?> builder) {
                return builder.k(this.field.getNumber());
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public Object getRepeated(Builder<?> builder, int i2) {
                return getMapField(builder).a().get(i2);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public int getRepeatedCount(Builder<?> builder) {
                return getMapField(builder).a().size();
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public boolean has(Builder<?> builder) {
                throw new UnsupportedOperationException("hasField() is not supported for repeated fields.");
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public Object get(Builder<?> builder) {
                ArrayList arrayList = new ArrayList();
                for (int i2 = 0; i2 < getRepeatedCount(builder); i2++) {
                    arrayList.add(getRepeated(builder, i2));
                }
                return Collections.unmodifiableList(arrayList);
            }
        }

        private static class RepeatedFieldAccessor implements FieldAccessor {

            /* renamed from: a, reason: collision with root package name */
            protected final Class f15287a;

            /* renamed from: b, reason: collision with root package name */
            protected final MethodInvoker f15288b;

            interface MethodInvoker {
                void addRepeated(Builder<?> builder, Object obj);

                void clear(Builder<?> builder);

                Object get(Builder<?> builder);

                Object get(GeneratedMessageV3 generatedMessageV3);

                Object getRepeated(Builder<?> builder, int i2);

                Object getRepeated(GeneratedMessageV3 generatedMessageV3, int i2);

                int getRepeatedCount(Builder<?> builder);

                int getRepeatedCount(GeneratedMessageV3 generatedMessageV3);

                void setRepeated(Builder<?> builder, int i2, Object obj);
            }

            private static final class ReflectionInvoker implements MethodInvoker {
                private final java.lang.reflect.Method addRepeatedMethod;
                private final java.lang.reflect.Method clearMethod;
                private final java.lang.reflect.Method getCountMethod;
                private final java.lang.reflect.Method getCountMethodBuilder;
                private final java.lang.reflect.Method getMethod;
                private final java.lang.reflect.Method getMethodBuilder;
                private final java.lang.reflect.Method getRepeatedMethod;
                private final java.lang.reflect.Method getRepeatedMethodBuilder;
                private final java.lang.reflect.Method setRepeatedMethod;

                ReflectionInvoker(Descriptors.FieldDescriptor fieldDescriptor, String str, Class cls, Class cls2) {
                    this.getMethod = GeneratedMessageV3.getMethodOrDie(cls, TmpConstant.PROPERTY_IDENTIFIER_GET + str + "List", new Class[0]);
                    this.getMethodBuilder = GeneratedMessageV3.getMethodOrDie(cls2, TmpConstant.PROPERTY_IDENTIFIER_GET + str + "List", new Class[0]);
                    StringBuilder sb = new StringBuilder();
                    sb.append(TmpConstant.PROPERTY_IDENTIFIER_GET);
                    sb.append(str);
                    String string = sb.toString();
                    Class cls3 = Integer.TYPE;
                    java.lang.reflect.Method methodOrDie = GeneratedMessageV3.getMethodOrDie(cls, string, cls3);
                    this.getRepeatedMethod = methodOrDie;
                    this.getRepeatedMethodBuilder = GeneratedMessageV3.getMethodOrDie(cls2, TmpConstant.PROPERTY_IDENTIFIER_GET + str, cls3);
                    Class<?> returnType = methodOrDie.getReturnType();
                    this.setRepeatedMethod = GeneratedMessageV3.getMethodOrDie(cls2, TmpConstant.PROPERTY_IDENTIFIER_SET + str, cls3, returnType);
                    this.addRepeatedMethod = GeneratedMessageV3.getMethodOrDie(cls2, TmpConstant.GROUP_OP_ADD + str, returnType);
                    this.getCountMethod = GeneratedMessageV3.getMethodOrDie(cls, TmpConstant.PROPERTY_IDENTIFIER_GET + str + "Count", new Class[0]);
                    this.getCountMethodBuilder = GeneratedMessageV3.getMethodOrDie(cls2, TmpConstant.PROPERTY_IDENTIFIER_GET + str + "Count", new Class[0]);
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(CervicalMucusRecord.Appearance.CLEAR);
                    sb2.append(str);
                    this.clearMethod = GeneratedMessageV3.getMethodOrDie(cls2, sb2.toString(), new Class[0]);
                }

                @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.RepeatedFieldAccessor.MethodInvoker
                public void addRepeated(Builder<?> builder, Object obj) {
                    GeneratedMessageV3.invokeOrDie(this.addRepeatedMethod, builder, obj);
                }

                @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.RepeatedFieldAccessor.MethodInvoker
                public void clear(Builder<?> builder) {
                    GeneratedMessageV3.invokeOrDie(this.clearMethod, builder, new Object[0]);
                }

                @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.RepeatedFieldAccessor.MethodInvoker
                public Object get(GeneratedMessageV3 generatedMessageV3) {
                    return GeneratedMessageV3.invokeOrDie(this.getMethod, generatedMessageV3, new Object[0]);
                }

                @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.RepeatedFieldAccessor.MethodInvoker
                public Object getRepeated(GeneratedMessageV3 generatedMessageV3, int i2) {
                    return GeneratedMessageV3.invokeOrDie(this.getRepeatedMethod, generatedMessageV3, Integer.valueOf(i2));
                }

                @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.RepeatedFieldAccessor.MethodInvoker
                public int getRepeatedCount(GeneratedMessageV3 generatedMessageV3) {
                    return ((Integer) GeneratedMessageV3.invokeOrDie(this.getCountMethod, generatedMessageV3, new Object[0])).intValue();
                }

                @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.RepeatedFieldAccessor.MethodInvoker
                public void setRepeated(Builder<?> builder, int i2, Object obj) {
                    GeneratedMessageV3.invokeOrDie(this.setRepeatedMethod, builder, Integer.valueOf(i2), obj);
                }

                @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.RepeatedFieldAccessor.MethodInvoker
                public Object get(Builder<?> builder) {
                    return GeneratedMessageV3.invokeOrDie(this.getMethodBuilder, builder, new Object[0]);
                }

                @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.RepeatedFieldAccessor.MethodInvoker
                public Object getRepeated(Builder<?> builder, int i2) {
                    return GeneratedMessageV3.invokeOrDie(this.getRepeatedMethodBuilder, builder, Integer.valueOf(i2));
                }

                @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.RepeatedFieldAccessor.MethodInvoker
                public int getRepeatedCount(Builder<?> builder) {
                    return ((Integer) GeneratedMessageV3.invokeOrDie(this.getCountMethodBuilder, builder, new Object[0])).intValue();
                }
            }

            RepeatedFieldAccessor(Descriptors.FieldDescriptor fieldDescriptor, String str, Class cls, Class cls2) {
                ReflectionInvoker reflectionInvoker = new ReflectionInvoker(fieldDescriptor, str, cls, cls2);
                this.f15287a = reflectionInvoker.getRepeatedMethod.getReturnType();
                this.f15288b = a(reflectionInvoker);
            }

            static MethodInvoker a(ReflectionInvoker reflectionInvoker) {
                return reflectionInvoker;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public void addRepeated(Builder<?> builder, Object obj) {
                this.f15288b.addRepeated(builder, obj);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public void clear(Builder<?> builder) {
                this.f15288b.clear(builder);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public Object get(GeneratedMessageV3 generatedMessageV3) {
                return this.f15288b.get(generatedMessageV3);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public Message.Builder getBuilder(Builder<?> builder) {
                throw new UnsupportedOperationException("getFieldBuilder() called on a non-Message type.");
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public Object getRaw(GeneratedMessageV3 generatedMessageV3) {
                return get(generatedMessageV3);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public Object getRepeated(GeneratedMessageV3 generatedMessageV3, int i2) {
                return this.f15288b.getRepeated(generatedMessageV3, i2);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public Message.Builder getRepeatedBuilder(Builder<?> builder, int i2) {
                throw new UnsupportedOperationException("getRepeatedFieldBuilder() called on a non-Message type.");
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public int getRepeatedCount(GeneratedMessageV3 generatedMessageV3) {
                return this.f15288b.getRepeatedCount(generatedMessageV3);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public boolean has(GeneratedMessageV3 generatedMessageV3) {
                throw new UnsupportedOperationException("hasField() called on a repeated field.");
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public Message.Builder newBuilder() {
                throw new UnsupportedOperationException("newBuilderForField() called on a non-Message type.");
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public void set(Builder<?> builder, Object obj) {
                clear(builder);
                Iterator it = ((List) obj).iterator();
                while (it.hasNext()) {
                    addRepeated(builder, it.next());
                }
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public void setRepeated(Builder<?> builder, int i2, Object obj) {
                this.f15288b.setRepeated(builder, i2, obj);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public Object get(Builder<?> builder) {
                return this.f15288b.get(builder);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public Object getRepeated(Builder<?> builder, int i2) {
                return this.f15288b.getRepeated(builder, i2);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public int getRepeatedCount(Builder<?> builder) {
                return this.f15288b.getRepeatedCount(builder);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public boolean has(Builder<?> builder) {
                throw new UnsupportedOperationException("hasField() called on a repeated field.");
            }
        }

        private static final class RepeatedMessageFieldAccessor extends RepeatedFieldAccessor {
            private final java.lang.reflect.Method getBuilderMethodBuilder;
            private final java.lang.reflect.Method newBuilderMethod;

            RepeatedMessageFieldAccessor(Descriptors.FieldDescriptor fieldDescriptor, String str, Class cls, Class cls2) {
                super(fieldDescriptor, str, cls, cls2);
                this.newBuilderMethod = GeneratedMessageV3.getMethodOrDie(this.f15287a, "newBuilder", new Class[0]);
                this.getBuilderMethodBuilder = GeneratedMessageV3.getMethodOrDie(cls2, TmpConstant.PROPERTY_IDENTIFIER_GET + str + "Builder", Integer.TYPE);
            }

            private Object coerceType(Object obj) {
                return this.f15287a.isInstance(obj) ? obj : ((Message.Builder) GeneratedMessageV3.invokeOrDie(this.newBuilderMethod, null, new Object[0])).mergeFrom((Message) obj).build();
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.RepeatedFieldAccessor, com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public void addRepeated(Builder<?> builder, Object obj) {
                super.addRepeated(builder, coerceType(obj));
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.RepeatedFieldAccessor, com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public Message.Builder getRepeatedBuilder(Builder<?> builder, int i2) {
                return (Message.Builder) GeneratedMessageV3.invokeOrDie(this.getBuilderMethodBuilder, builder, Integer.valueOf(i2));
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.RepeatedFieldAccessor, com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public Message.Builder newBuilder() {
                return (Message.Builder) GeneratedMessageV3.invokeOrDie(this.newBuilderMethod, null, new Object[0]);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.RepeatedFieldAccessor, com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public void setRepeated(Builder<?> builder, int i2, Object obj) {
                super.setRepeated(builder, i2, coerceType(obj));
            }
        }

        private static class SingularFieldAccessor implements FieldAccessor {

            /* renamed from: a, reason: collision with root package name */
            protected final Class f15289a;

            /* renamed from: b, reason: collision with root package name */
            protected final Descriptors.FieldDescriptor f15290b;

            /* renamed from: c, reason: collision with root package name */
            protected final boolean f15291c;

            /* renamed from: d, reason: collision with root package name */
            protected final boolean f15292d;

            /* renamed from: e, reason: collision with root package name */
            protected final MethodInvoker f15293e;

            private interface MethodInvoker {
                void clear(Builder<?> builder);

                Object get(Builder<?> builder);

                Object get(GeneratedMessageV3 generatedMessageV3);

                int getOneofFieldNumber(Builder<?> builder);

                int getOneofFieldNumber(GeneratedMessageV3 generatedMessageV3);

                boolean has(Builder<?> builder);

                boolean has(GeneratedMessageV3 generatedMessageV3);

                void set(Builder<?> builder, Object obj);
            }

            private static final class ReflectionInvoker implements MethodInvoker {
                private final java.lang.reflect.Method caseMethod;
                private final java.lang.reflect.Method caseMethodBuilder;
                private final java.lang.reflect.Method clearMethod;
                private final java.lang.reflect.Method getMethod;
                private final java.lang.reflect.Method getMethodBuilder;
                private final java.lang.reflect.Method hasMethod;
                private final java.lang.reflect.Method hasMethodBuilder;
                private final java.lang.reflect.Method setMethod;

                ReflectionInvoker(Descriptors.FieldDescriptor fieldDescriptor, String str, Class cls, Class cls2, String str2, boolean z2, boolean z3) {
                    java.lang.reflect.Method methodOrDie;
                    java.lang.reflect.Method methodOrDie2;
                    java.lang.reflect.Method methodOrDie3;
                    java.lang.reflect.Method methodOrDie4 = GeneratedMessageV3.getMethodOrDie(cls, TmpConstant.PROPERTY_IDENTIFIER_GET + str, new Class[0]);
                    this.getMethod = methodOrDie4;
                    this.getMethodBuilder = GeneratedMessageV3.getMethodOrDie(cls2, TmpConstant.PROPERTY_IDENTIFIER_GET + str, new Class[0]);
                    this.setMethod = GeneratedMessageV3.getMethodOrDie(cls2, TmpConstant.PROPERTY_IDENTIFIER_SET + str, methodOrDie4.getReturnType());
                    java.lang.reflect.Method methodOrDie5 = null;
                    if (z3) {
                        methodOrDie = GeneratedMessageV3.getMethodOrDie(cls, "has" + str, new Class[0]);
                    } else {
                        methodOrDie = null;
                    }
                    this.hasMethod = methodOrDie;
                    if (z3) {
                        methodOrDie2 = GeneratedMessageV3.getMethodOrDie(cls2, "has" + str, new Class[0]);
                    } else {
                        methodOrDie2 = null;
                    }
                    this.hasMethodBuilder = methodOrDie2;
                    this.clearMethod = GeneratedMessageV3.getMethodOrDie(cls2, CervicalMucusRecord.Appearance.CLEAR + str, new Class[0]);
                    if (z2) {
                        methodOrDie3 = GeneratedMessageV3.getMethodOrDie(cls, TmpConstant.PROPERTY_IDENTIFIER_GET + str2 + "Case", new Class[0]);
                    } else {
                        methodOrDie3 = null;
                    }
                    this.caseMethod = methodOrDie3;
                    if (z2) {
                        methodOrDie5 = GeneratedMessageV3.getMethodOrDie(cls2, TmpConstant.PROPERTY_IDENTIFIER_GET + str2 + "Case", new Class[0]);
                    }
                    this.caseMethodBuilder = methodOrDie5;
                }

                @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.SingularFieldAccessor.MethodInvoker
                public void clear(Builder<?> builder) {
                    GeneratedMessageV3.invokeOrDie(this.clearMethod, builder, new Object[0]);
                }

                @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.SingularFieldAccessor.MethodInvoker
                public Object get(GeneratedMessageV3 generatedMessageV3) {
                    return GeneratedMessageV3.invokeOrDie(this.getMethod, generatedMessageV3, new Object[0]);
                }

                @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.SingularFieldAccessor.MethodInvoker
                public int getOneofFieldNumber(GeneratedMessageV3 generatedMessageV3) {
                    return ((Internal.EnumLite) GeneratedMessageV3.invokeOrDie(this.caseMethod, generatedMessageV3, new Object[0])).getNumber();
                }

                @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.SingularFieldAccessor.MethodInvoker
                public boolean has(GeneratedMessageV3 generatedMessageV3) {
                    return ((Boolean) GeneratedMessageV3.invokeOrDie(this.hasMethod, generatedMessageV3, new Object[0])).booleanValue();
                }

                @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.SingularFieldAccessor.MethodInvoker
                public void set(Builder<?> builder, Object obj) {
                    GeneratedMessageV3.invokeOrDie(this.setMethod, builder, obj);
                }

                @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.SingularFieldAccessor.MethodInvoker
                public Object get(Builder<?> builder) {
                    return GeneratedMessageV3.invokeOrDie(this.getMethodBuilder, builder, new Object[0]);
                }

                @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.SingularFieldAccessor.MethodInvoker
                public int getOneofFieldNumber(Builder<?> builder) {
                    return ((Internal.EnumLite) GeneratedMessageV3.invokeOrDie(this.caseMethodBuilder, builder, new Object[0])).getNumber();
                }

                @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.SingularFieldAccessor.MethodInvoker
                public boolean has(Builder<?> builder) {
                    return ((Boolean) GeneratedMessageV3.invokeOrDie(this.hasMethodBuilder, builder, new Object[0])).booleanValue();
                }
            }

            SingularFieldAccessor(Descriptors.FieldDescriptor fieldDescriptor, String str, Class cls, Class cls2, String str2) {
                boolean z2 = (fieldDescriptor.getContainingOneof() == null || fieldDescriptor.getContainingOneof().isSynthetic()) ? false : true;
                this.f15291c = z2;
                boolean z3 = fieldDescriptor.getFile().getSyntax() == Descriptors.FileDescriptor.Syntax.PROTO2 || fieldDescriptor.hasOptionalKeyword() || (!z2 && fieldDescriptor.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE);
                this.f15292d = z3;
                ReflectionInvoker reflectionInvoker = new ReflectionInvoker(fieldDescriptor, str, cls, cls2, str2, z2, z3);
                this.f15290b = fieldDescriptor;
                this.f15289a = reflectionInvoker.getMethod.getReturnType();
                this.f15293e = a(reflectionInvoker);
            }

            static MethodInvoker a(ReflectionInvoker reflectionInvoker) {
                return reflectionInvoker;
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public void addRepeated(Builder<?> builder, Object obj) {
                throw new UnsupportedOperationException("addRepeatedField() called on a singular field.");
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public void clear(Builder<?> builder) {
                this.f15293e.clear(builder);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public Object get(GeneratedMessageV3 generatedMessageV3) {
                return this.f15293e.get(generatedMessageV3);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public Message.Builder getBuilder(Builder<?> builder) {
                throw new UnsupportedOperationException("getFieldBuilder() called on a non-Message type.");
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public Object getRaw(GeneratedMessageV3 generatedMessageV3) {
                return get(generatedMessageV3);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public Object getRepeated(GeneratedMessageV3 generatedMessageV3, int i2) {
                throw new UnsupportedOperationException("getRepeatedField() called on a singular field.");
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public Message.Builder getRepeatedBuilder(Builder<?> builder, int i2) {
                throw new UnsupportedOperationException("getRepeatedFieldBuilder() called on a non-Message type.");
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public int getRepeatedCount(GeneratedMessageV3 generatedMessageV3) {
                throw new UnsupportedOperationException("getRepeatedFieldSize() called on a singular field.");
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public boolean has(GeneratedMessageV3 generatedMessageV3) {
                return !this.f15292d ? this.f15291c ? this.f15293e.getOneofFieldNumber(generatedMessageV3) == this.f15290b.getNumber() : !get(generatedMessageV3).equals(this.f15290b.getDefaultValue()) : this.f15293e.has(generatedMessageV3);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public Message.Builder newBuilder() {
                throw new UnsupportedOperationException("newBuilderForField() called on a non-Message type.");
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public void set(Builder<?> builder, Object obj) {
                this.f15293e.set(builder, obj);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public void setRepeated(Builder<?> builder, int i2, Object obj) {
                throw new UnsupportedOperationException("setRepeatedField() called on a singular field.");
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public Object get(Builder<?> builder) {
                return this.f15293e.get(builder);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public Object getRepeated(Builder<?> builder, int i2) {
                throw new UnsupportedOperationException("getRepeatedField() called on a singular field.");
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public int getRepeatedCount(Builder<?> builder) {
                throw new UnsupportedOperationException("getRepeatedFieldSize() called on a singular field.");
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public boolean has(Builder<?> builder) {
                if (!this.f15292d) {
                    if (this.f15291c) {
                        return this.f15293e.getOneofFieldNumber(builder) == this.f15290b.getNumber();
                    }
                    return !get(builder).equals(this.f15290b.getDefaultValue());
                }
                return this.f15293e.has(builder);
            }
        }

        private static final class SingularMessageFieldAccessor extends SingularFieldAccessor {
            private final java.lang.reflect.Method getBuilderMethodBuilder;
            private final java.lang.reflect.Method newBuilderMethod;

            SingularMessageFieldAccessor(Descriptors.FieldDescriptor fieldDescriptor, String str, Class cls, Class cls2, String str2) {
                super(fieldDescriptor, str, cls, cls2, str2);
                this.newBuilderMethod = GeneratedMessageV3.getMethodOrDie(this.f15289a, "newBuilder", new Class[0]);
                this.getBuilderMethodBuilder = GeneratedMessageV3.getMethodOrDie(cls2, TmpConstant.PROPERTY_IDENTIFIER_GET + str + "Builder", new Class[0]);
            }

            private Object coerceType(Object obj) {
                return this.f15289a.isInstance(obj) ? obj : ((Message.Builder) GeneratedMessageV3.invokeOrDie(this.newBuilderMethod, null, new Object[0])).mergeFrom((Message) obj).buildPartial();
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.SingularFieldAccessor, com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public Message.Builder getBuilder(Builder<?> builder) {
                return (Message.Builder) GeneratedMessageV3.invokeOrDie(this.getBuilderMethodBuilder, builder, new Object[0]);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.SingularFieldAccessor, com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public Message.Builder newBuilder() {
                return (Message.Builder) GeneratedMessageV3.invokeOrDie(this.newBuilderMethod, null, new Object[0]);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.SingularFieldAccessor, com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public void set(Builder<?> builder, Object obj) {
                super.set(builder, coerceType(obj));
            }
        }

        private static final class SingularStringFieldAccessor extends SingularFieldAccessor {
            private final java.lang.reflect.Method getBytesMethod;
            private final java.lang.reflect.Method setBytesMethodBuilder;

            SingularStringFieldAccessor(Descriptors.FieldDescriptor fieldDescriptor, String str, Class cls, Class cls2, String str2) {
                super(fieldDescriptor, str, cls, cls2, str2);
                this.getBytesMethod = GeneratedMessageV3.getMethodOrDie(cls, TmpConstant.PROPERTY_IDENTIFIER_GET + str + "Bytes", new Class[0]);
                this.setBytesMethodBuilder = GeneratedMessageV3.getMethodOrDie(cls2, TmpConstant.PROPERTY_IDENTIFIER_SET + str + "Bytes", ByteString.class);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.SingularFieldAccessor, com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public Object getRaw(GeneratedMessageV3 generatedMessageV3) {
                return GeneratedMessageV3.invokeOrDie(this.getBytesMethod, generatedMessageV3, new Object[0]);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.SingularFieldAccessor, com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public void set(Builder<?> builder, Object obj) {
                if (obj instanceof ByteString) {
                    GeneratedMessageV3.invokeOrDie(this.setBytesMethodBuilder, builder, obj);
                } else {
                    super.set(builder, obj);
                }
            }
        }

        public FieldAccessorTable(Descriptors.Descriptor descriptor, String[] strArr, Class<? extends GeneratedMessageV3> cls, Class<? extends Builder<?>> cls2) {
            this(descriptor, strArr);
            ensureFieldAccessorsInitialized(cls, cls2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public FieldAccessor getField(Descriptors.FieldDescriptor fieldDescriptor) {
            if (fieldDescriptor.getContainingType() != this.descriptor) {
                throw new IllegalArgumentException("FieldDescriptor does not match message type.");
            }
            if (fieldDescriptor.isExtension()) {
                throw new IllegalArgumentException("This type does not have extensions.");
            }
            return this.fields[fieldDescriptor.getIndex()];
        }

        /* JADX INFO: Access modifiers changed from: private */
        public OneofAccessor getOneof(Descriptors.OneofDescriptor oneofDescriptor) {
            if (oneofDescriptor.getContainingType() == this.descriptor) {
                return this.oneofs[oneofDescriptor.getIndex()];
            }
            throw new IllegalArgumentException("OneofDescriptor does not match message type.");
        }

        public FieldAccessorTable ensureFieldAccessorsInitialized(Class<? extends GeneratedMessageV3> cls, Class<? extends Builder<?>> cls2) {
            if (this.initialized) {
                return this;
            }
            synchronized (this) {
                try {
                    if (this.initialized) {
                        return this;
                    }
                    int length = this.fields.length;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= length) {
                            break;
                        }
                        Descriptors.FieldDescriptor fieldDescriptor = this.descriptor.getFields().get(i2);
                        String str = fieldDescriptor.getContainingOneof() != null ? this.camelCaseNames[fieldDescriptor.getContainingOneof().getIndex() + length] : null;
                        if (fieldDescriptor.isRepeated()) {
                            if (fieldDescriptor.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                                if (fieldDescriptor.isMapField()) {
                                    this.fields[i2] = new MapFieldAccessor(fieldDescriptor, cls);
                                } else {
                                    this.fields[i2] = new RepeatedMessageFieldAccessor(fieldDescriptor, this.camelCaseNames[i2], cls, cls2);
                                }
                            } else if (fieldDescriptor.getJavaType() == Descriptors.FieldDescriptor.JavaType.ENUM) {
                                this.fields[i2] = new RepeatedEnumFieldAccessor(fieldDescriptor, this.camelCaseNames[i2], cls, cls2);
                            } else {
                                this.fields[i2] = new RepeatedFieldAccessor(fieldDescriptor, this.camelCaseNames[i2], cls, cls2);
                            }
                        } else if (fieldDescriptor.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                            this.fields[i2] = new SingularMessageFieldAccessor(fieldDescriptor, this.camelCaseNames[i2], cls, cls2, str);
                        } else if (fieldDescriptor.getJavaType() == Descriptors.FieldDescriptor.JavaType.ENUM) {
                            this.fields[i2] = new SingularEnumFieldAccessor(fieldDescriptor, this.camelCaseNames[i2], cls, cls2, str);
                        } else if (fieldDescriptor.getJavaType() == Descriptors.FieldDescriptor.JavaType.STRING) {
                            this.fields[i2] = new SingularStringFieldAccessor(fieldDescriptor, this.camelCaseNames[i2], cls, cls2, str);
                        } else {
                            this.fields[i2] = new SingularFieldAccessor(fieldDescriptor, this.camelCaseNames[i2], cls, cls2, str);
                        }
                        i2++;
                    }
                    int length2 = this.oneofs.length;
                    for (int i3 = 0; i3 < length2; i3++) {
                        this.oneofs[i3] = new OneofAccessor(this.descriptor, i3, this.camelCaseNames[i3 + length], cls, cls2);
                    }
                    this.initialized = true;
                    this.camelCaseNames = null;
                    return this;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        private static class OneofAccessor {
            private final java.lang.reflect.Method caseMethod;
            private final java.lang.reflect.Method caseMethodBuilder;
            private final java.lang.reflect.Method clearMethod;
            private final Descriptors.Descriptor descriptor;
            private final Descriptors.FieldDescriptor fieldDescriptor;

            OneofAccessor(Descriptors.Descriptor descriptor, int i2, String str, Class cls, Class cls2) {
                this.descriptor = descriptor;
                Descriptors.OneofDescriptor oneofDescriptor = descriptor.getOneofs().get(i2);
                if (oneofDescriptor.isSynthetic()) {
                    this.caseMethod = null;
                    this.caseMethodBuilder = null;
                    this.fieldDescriptor = oneofDescriptor.getFields().get(0);
                } else {
                    this.caseMethod = GeneratedMessageV3.getMethodOrDie(cls, TmpConstant.PROPERTY_IDENTIFIER_GET + str + "Case", new Class[0]);
                    this.caseMethodBuilder = GeneratedMessageV3.getMethodOrDie(cls2, TmpConstant.PROPERTY_IDENTIFIER_GET + str + "Case", new Class[0]);
                    this.fieldDescriptor = null;
                }
                this.clearMethod = GeneratedMessageV3.getMethodOrDie(cls2, CervicalMucusRecord.Appearance.CLEAR + str, new Class[0]);
            }

            public void clear(Builder<?> builder) {
                GeneratedMessageV3.invokeOrDie(this.clearMethod, builder, new Object[0]);
            }

            public Descriptors.FieldDescriptor get(GeneratedMessageV3 generatedMessageV3) {
                Descriptors.FieldDescriptor fieldDescriptor = this.fieldDescriptor;
                if (fieldDescriptor != null) {
                    if (generatedMessageV3.hasField(fieldDescriptor)) {
                        return this.fieldDescriptor;
                    }
                    return null;
                }
                int number = ((Internal.EnumLite) GeneratedMessageV3.invokeOrDie(this.caseMethod, generatedMessageV3, new Object[0])).getNumber();
                if (number > 0) {
                    return this.descriptor.findFieldByNumber(number);
                }
                return null;
            }

            public boolean has(GeneratedMessageV3 generatedMessageV3) {
                Descriptors.FieldDescriptor fieldDescriptor = this.fieldDescriptor;
                return fieldDescriptor != null ? generatedMessageV3.hasField(fieldDescriptor) : ((Internal.EnumLite) GeneratedMessageV3.invokeOrDie(this.caseMethod, generatedMessageV3, new Object[0])).getNumber() != 0;
            }

            public boolean has(Builder<?> builder) {
                Descriptors.FieldDescriptor fieldDescriptor = this.fieldDescriptor;
                if (fieldDescriptor != null) {
                    return builder.hasField(fieldDescriptor);
                }
                return ((Internal.EnumLite) GeneratedMessageV3.invokeOrDie(this.caseMethodBuilder, builder, new Object[0])).getNumber() != 0;
            }

            public Descriptors.FieldDescriptor get(Builder<?> builder) {
                Descriptors.FieldDescriptor fieldDescriptor = this.fieldDescriptor;
                if (fieldDescriptor == null) {
                    int number = ((Internal.EnumLite) GeneratedMessageV3.invokeOrDie(this.caseMethodBuilder, builder, new Object[0])).getNumber();
                    if (number > 0) {
                        return this.descriptor.findFieldByNumber(number);
                    }
                    return null;
                }
                if (builder.hasField(fieldDescriptor)) {
                    return this.fieldDescriptor;
                }
                return null;
            }
        }

        public FieldAccessorTable(Descriptors.Descriptor descriptor, String[] strArr) {
            this.descriptor = descriptor;
            this.camelCaseNames = strArr;
            this.fields = new FieldAccessor[descriptor.getFields().size()];
            this.oneofs = new OneofAccessor[descriptor.getOneofs().size()];
            this.initialized = false;
        }

        private static final class RepeatedEnumFieldAccessor extends RepeatedFieldAccessor {
            private java.lang.reflect.Method addRepeatedValueMethod;
            private final Descriptors.EnumDescriptor enumDescriptor;
            private java.lang.reflect.Method getRepeatedValueMethod;
            private java.lang.reflect.Method getRepeatedValueMethodBuilder;
            private final java.lang.reflect.Method getValueDescriptorMethod;
            private java.lang.reflect.Method setRepeatedValueMethod;
            private final boolean supportUnknownEnumValue;
            private final java.lang.reflect.Method valueOfMethod;

            RepeatedEnumFieldAccessor(Descriptors.FieldDescriptor fieldDescriptor, String str, Class cls, Class cls2) {
                super(fieldDescriptor, str, cls, cls2);
                this.enumDescriptor = fieldDescriptor.getEnumType();
                this.valueOfMethod = GeneratedMessageV3.getMethodOrDie(this.f15287a, "valueOf", Descriptors.EnumValueDescriptor.class);
                this.getValueDescriptorMethod = GeneratedMessageV3.getMethodOrDie(this.f15287a, "getValueDescriptor", new Class[0]);
                boolean zB = fieldDescriptor.getFile().b();
                this.supportUnknownEnumValue = zB;
                if (zB) {
                    String str2 = TmpConstant.PROPERTY_IDENTIFIER_GET + str + "Value";
                    Class cls3 = Integer.TYPE;
                    this.getRepeatedValueMethod = GeneratedMessageV3.getMethodOrDie(cls, str2, cls3);
                    this.getRepeatedValueMethodBuilder = GeneratedMessageV3.getMethodOrDie(cls2, TmpConstant.PROPERTY_IDENTIFIER_GET + str + "Value", cls3);
                    this.setRepeatedValueMethod = GeneratedMessageV3.getMethodOrDie(cls2, TmpConstant.PROPERTY_IDENTIFIER_SET + str + "Value", cls3, cls3);
                    this.addRepeatedValueMethod = GeneratedMessageV3.getMethodOrDie(cls2, TmpConstant.GROUP_OP_ADD + str + "Value", cls3);
                }
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.RepeatedFieldAccessor, com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public void addRepeated(Builder<?> builder, Object obj) {
                if (this.supportUnknownEnumValue) {
                    GeneratedMessageV3.invokeOrDie(this.addRepeatedValueMethod, builder, Integer.valueOf(((Descriptors.EnumValueDescriptor) obj).getNumber()));
                } else {
                    super.addRepeated(builder, GeneratedMessageV3.invokeOrDie(this.valueOfMethod, null, obj));
                }
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.RepeatedFieldAccessor, com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public Object get(GeneratedMessageV3 generatedMessageV3) {
                ArrayList arrayList = new ArrayList();
                int repeatedCount = getRepeatedCount(generatedMessageV3);
                for (int i2 = 0; i2 < repeatedCount; i2++) {
                    arrayList.add(getRepeated(generatedMessageV3, i2));
                }
                return Collections.unmodifiableList(arrayList);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.RepeatedFieldAccessor, com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public Object getRepeated(GeneratedMessageV3 generatedMessageV3, int i2) {
                return this.supportUnknownEnumValue ? this.enumDescriptor.findValueByNumberCreatingIfUnknown(((Integer) GeneratedMessageV3.invokeOrDie(this.getRepeatedValueMethod, generatedMessageV3, Integer.valueOf(i2))).intValue()) : GeneratedMessageV3.invokeOrDie(this.getValueDescriptorMethod, super.getRepeated(generatedMessageV3, i2), new Object[0]);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.RepeatedFieldAccessor, com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public void setRepeated(Builder<?> builder, int i2, Object obj) {
                if (this.supportUnknownEnumValue) {
                    GeneratedMessageV3.invokeOrDie(this.setRepeatedValueMethod, builder, Integer.valueOf(i2), Integer.valueOf(((Descriptors.EnumValueDescriptor) obj).getNumber()));
                } else {
                    super.setRepeated(builder, i2, GeneratedMessageV3.invokeOrDie(this.valueOfMethod, null, obj));
                }
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.RepeatedFieldAccessor, com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public Object get(Builder<?> builder) {
                ArrayList arrayList = new ArrayList();
                int repeatedCount = getRepeatedCount(builder);
                for (int i2 = 0; i2 < repeatedCount; i2++) {
                    arrayList.add(getRepeated(builder, i2));
                }
                return Collections.unmodifiableList(arrayList);
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.RepeatedFieldAccessor, com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public Object getRepeated(Builder<?> builder, int i2) {
                if (!this.supportUnknownEnumValue) {
                    return GeneratedMessageV3.invokeOrDie(this.getValueDescriptorMethod, super.getRepeated(builder, i2), new Object[0]);
                }
                return this.enumDescriptor.findValueByNumberCreatingIfUnknown(((Integer) GeneratedMessageV3.invokeOrDie(this.getRepeatedValueMethodBuilder, builder, Integer.valueOf(i2))).intValue());
            }
        }

        private static final class SingularEnumFieldAccessor extends SingularFieldAccessor {
            private final Descriptors.EnumDescriptor enumDescriptor;
            private final java.lang.reflect.Method getValueDescriptorMethod;
            private java.lang.reflect.Method getValueMethod;
            private java.lang.reflect.Method getValueMethodBuilder;
            private java.lang.reflect.Method setValueMethod;
            private final boolean supportUnknownEnumValue;
            private final java.lang.reflect.Method valueOfMethod;

            SingularEnumFieldAccessor(Descriptors.FieldDescriptor fieldDescriptor, String str, Class cls, Class cls2, String str2) {
                super(fieldDescriptor, str, cls, cls2, str2);
                this.enumDescriptor = fieldDescriptor.getEnumType();
                this.valueOfMethod = GeneratedMessageV3.getMethodOrDie(this.f15289a, "valueOf", Descriptors.EnumValueDescriptor.class);
                this.getValueDescriptorMethod = GeneratedMessageV3.getMethodOrDie(this.f15289a, "getValueDescriptor", new Class[0]);
                boolean zB = fieldDescriptor.getFile().b();
                this.supportUnknownEnumValue = zB;
                if (zB) {
                    this.getValueMethod = GeneratedMessageV3.getMethodOrDie(cls, TmpConstant.PROPERTY_IDENTIFIER_GET + str + "Value", new Class[0]);
                    this.getValueMethodBuilder = GeneratedMessageV3.getMethodOrDie(cls2, TmpConstant.PROPERTY_IDENTIFIER_GET + str + "Value", new Class[0]);
                    this.setValueMethod = GeneratedMessageV3.getMethodOrDie(cls2, TmpConstant.PROPERTY_IDENTIFIER_SET + str + "Value", Integer.TYPE);
                }
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.SingularFieldAccessor, com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public Object get(GeneratedMessageV3 generatedMessageV3) {
                if (!this.supportUnknownEnumValue) {
                    return GeneratedMessageV3.invokeOrDie(this.getValueDescriptorMethod, super.get(generatedMessageV3), new Object[0]);
                }
                return this.enumDescriptor.findValueByNumberCreatingIfUnknown(((Integer) GeneratedMessageV3.invokeOrDie(this.getValueMethod, generatedMessageV3, new Object[0])).intValue());
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.SingularFieldAccessor, com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public void set(Builder<?> builder, Object obj) {
                if (this.supportUnknownEnumValue) {
                    GeneratedMessageV3.invokeOrDie(this.setValueMethod, builder, Integer.valueOf(((Descriptors.EnumValueDescriptor) obj).getNumber()));
                } else {
                    super.set(builder, GeneratedMessageV3.invokeOrDie(this.valueOfMethod, null, obj));
                }
            }

            @Override // com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.SingularFieldAccessor, com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.FieldAccessor
            public Object get(Builder<?> builder) {
                if (this.supportUnknownEnumValue) {
                    return this.enumDescriptor.findValueByNumberCreatingIfUnknown(((Integer) GeneratedMessageV3.invokeOrDie(this.getValueMethodBuilder, builder, new Object[0])).intValue());
                }
                return GeneratedMessageV3.invokeOrDie(this.getValueDescriptorMethod, super.get(builder), new Object[0]);
            }
        }
    }

    protected static Internal.LongList mutableCopy(Internal.LongList longList) {
        return (Internal.LongList) makeMutableCopy(longList);
    }

    protected GeneratedMessageV3(Builder<?> builder) {
        this.unknownFields = builder.getUnknownFields();
    }

    protected static Internal.FloatList mutableCopy(Internal.FloatList floatList) {
        return (Internal.FloatList) makeMutableCopy(floatList);
    }

    protected static <M extends Message> M parseDelimitedWithIOException(Parser<M> parser, InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        try {
            return parser.parseDelimitedFrom(inputStream, extensionRegistryLite);
        } catch (InvalidProtocolBufferException e2) {
            throw e2.unwrapIOException();
        }
    }

    protected static <M extends Message> M parseWithIOException(Parser<M> parser, InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        try {
            return parser.parseFrom(inputStream, extensionRegistryLite);
        } catch (InvalidProtocolBufferException e2) {
            throw e2.unwrapIOException();
        }
    }

    protected static Internal.DoubleList mutableCopy(Internal.DoubleList doubleList) {
        return (Internal.DoubleList) makeMutableCopy(doubleList);
    }

    protected static Internal.BooleanList mutableCopy(Internal.BooleanList booleanList) {
        return (Internal.BooleanList) makeMutableCopy(booleanList);
    }

    protected static <M extends Message> M parseWithIOException(Parser<M> parser, CodedInputStream codedInputStream) throws IOException {
        try {
            return parser.parseFrom(codedInputStream);
        } catch (InvalidProtocolBufferException e2) {
            throw e2.unwrapIOException();
        }
    }

    protected static <M extends Message> M parseWithIOException(Parser<M> parser, CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        try {
            return parser.parseFrom(codedInputStream, extensionRegistryLite);
        } catch (InvalidProtocolBufferException e2) {
            throw e2.unwrapIOException();
        }
    }
}
