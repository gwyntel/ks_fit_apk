package com.google.protobuf;

import com.google.protobuf.FieldSet;
import com.google.protobuf.FieldSet.FieldDescriptorLite;
import java.util.Map;

@CheckReturnValue
/* loaded from: classes2.dex */
abstract class ExtensionSchema<T extends FieldSet.FieldDescriptorLite<T>> {
    ExtensionSchema() {
    }

    abstract int a(Map.Entry entry);

    abstract Object b(ExtensionRegistryLite extensionRegistryLite, MessageLite messageLite, int i2);

    abstract FieldSet c(Object obj);

    abstract boolean d(MessageLite messageLite);

    abstract void e(Object obj);

    abstract Object f(Object obj, Reader reader, Object obj2, ExtensionRegistryLite extensionRegistryLite, FieldSet fieldSet, Object obj3, UnknownFieldSchema unknownFieldSchema);

    abstract void g(Reader reader, Object obj, ExtensionRegistryLite extensionRegistryLite, FieldSet fieldSet);

    abstract FieldSet getExtensions(Object obj);

    abstract void h(ByteString byteString, Object obj, ExtensionRegistryLite extensionRegistryLite, FieldSet fieldSet);

    abstract void i(Writer writer, Map.Entry entry);
}
