package androidx.datastore.preferences.protobuf;

import java.util.Collection;
import java.util.List;

/* loaded from: classes.dex */
public interface LazyStringList extends ProtocolStringList {
    void add(ByteString byteString);

    void add(byte[] bArr);

    boolean addAllByteArray(Collection<byte[]> collection);

    boolean addAllByteString(Collection<? extends ByteString> collection);

    List<byte[]> asByteArrayList();

    byte[] getByteArray(int i2);

    ByteString getByteString(int i2);

    Object getRaw(int i2);

    List<?> getUnderlyingElements();

    LazyStringList getUnmodifiableView();

    void mergeFrom(LazyStringList lazyStringList);

    void set(int i2, ByteString byteString);

    void set(int i2, byte[] bArr);
}
