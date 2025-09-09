package androidx.datastore.preferences.protobuf;

import androidx.datastore.preferences.protobuf.ArrayDecoders;
import java.io.IOException;

/* loaded from: classes.dex */
interface Schema<T> {
    boolean equals(T t2, T t3);

    int getSerializedSize(T t2);

    int hashCode(T t2);

    boolean isInitialized(T t2);

    void makeImmutable(T t2);

    void mergeFrom(T t2, Reader reader, ExtensionRegistryLite extensionRegistryLite) throws IOException;

    void mergeFrom(T t2, T t3);

    void mergeFrom(T t2, byte[] bArr, int i2, int i3, ArrayDecoders.Registers registers) throws IOException;

    T newInstance();

    void writeTo(T t2, Writer writer) throws IOException;
}
