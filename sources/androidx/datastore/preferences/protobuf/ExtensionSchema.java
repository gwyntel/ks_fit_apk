package androidx.datastore.preferences.protobuf;

import androidx.datastore.preferences.protobuf.FieldSet;
import androidx.datastore.preferences.protobuf.FieldSet.FieldDescriptorLite;
import java.util.Map;

/* loaded from: classes.dex */
abstract class ExtensionSchema<T extends FieldSet.FieldDescriptorLite<T>> {
    ExtensionSchema() {
    }

    abstract int a(Map.Entry entry);

    abstract Object b(ExtensionRegistryLite extensionRegistryLite, MessageLite messageLite, int i2);

    abstract FieldSet c(Object obj);

    abstract FieldSet d(Object obj);

    abstract boolean e(MessageLite messageLite);

    abstract void f(Object obj);

    abstract Object g(Reader reader, Object obj, ExtensionRegistryLite extensionRegistryLite, FieldSet fieldSet, Object obj2, UnknownFieldSchema unknownFieldSchema);

    abstract void h(Reader reader, Object obj, ExtensionRegistryLite extensionRegistryLite, FieldSet fieldSet);

    abstract void i(ByteString byteString, Object obj, ExtensionRegistryLite extensionRegistryLite, FieldSet fieldSet);

    abstract void j(Writer writer, Map.Entry entry);
}
