package androidx.health.platform.client.proto;

import java.util.AbstractList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

/* loaded from: classes.dex */
public class UnmodifiableLazyStringList extends AbstractList<String> implements LazyStringList, RandomAccess {
    private final LazyStringList list;

    public UnmodifiableLazyStringList(LazyStringList lazyStringList) {
        this.list = lazyStringList;
    }

    @Override // androidx.health.platform.client.proto.LazyStringList
    public void add(ByteString byteString) {
        throw new UnsupportedOperationException();
    }

    @Override // androidx.health.platform.client.proto.LazyStringList
    public boolean addAllByteArray(Collection<byte[]> collection) {
        throw new UnsupportedOperationException();
    }

    @Override // androidx.health.platform.client.proto.LazyStringList
    public boolean addAllByteString(Collection<? extends ByteString> collection) {
        throw new UnsupportedOperationException();
    }

    @Override // androidx.health.platform.client.proto.LazyStringList
    public List<byte[]> asByteArrayList() {
        return Collections.unmodifiableList(this.list.asByteArrayList());
    }

    @Override // androidx.health.platform.client.proto.ProtocolStringList
    public List<ByteString> asByteStringList() {
        return Collections.unmodifiableList(this.list.asByteStringList());
    }

    @Override // androidx.health.platform.client.proto.LazyStringList
    public byte[] getByteArray(int i2) {
        return this.list.getByteArray(i2);
    }

    @Override // androidx.health.platform.client.proto.LazyStringList
    public ByteString getByteString(int i2) {
        return this.list.getByteString(i2);
    }

    @Override // androidx.health.platform.client.proto.LazyStringList
    public Object getRaw(int i2) {
        return this.list.getRaw(i2);
    }

    @Override // androidx.health.platform.client.proto.LazyStringList
    public List<?> getUnderlyingElements() {
        return this.list.getUnderlyingElements();
    }

    @Override // androidx.health.platform.client.proto.LazyStringList
    public LazyStringList getUnmodifiableView() {
        return this;
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
    public Iterator<String> iterator() {
        return new Iterator<String>() { // from class: androidx.health.platform.client.proto.UnmodifiableLazyStringList.2

            /* renamed from: a, reason: collision with root package name */
            Iterator f4503a;

            {
                this.f4503a = UnmodifiableLazyStringList.this.list.iterator();
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.f4503a.hasNext();
            }

            @Override // java.util.Iterator
            public void remove() {
                throw new UnsupportedOperationException();
            }

            @Override // java.util.Iterator
            public String next() {
                return (String) this.f4503a.next();
            }
        };
    }

    @Override // java.util.AbstractList, java.util.List
    public ListIterator<String> listIterator(int i2) {
        return new ListIterator<String>(i2) { // from class: androidx.health.platform.client.proto.UnmodifiableLazyStringList.1

            /* renamed from: a, reason: collision with root package name */
            ListIterator f4500a;

            /* renamed from: b, reason: collision with root package name */
            final /* synthetic */ int f4501b;

            {
                this.f4501b = i2;
                this.f4500a = UnmodifiableLazyStringList.this.list.listIterator(i2);
            }

            @Override // java.util.ListIterator, java.util.Iterator
            public boolean hasNext() {
                return this.f4500a.hasNext();
            }

            @Override // java.util.ListIterator
            public boolean hasPrevious() {
                return this.f4500a.hasPrevious();
            }

            @Override // java.util.ListIterator
            public int nextIndex() {
                return this.f4500a.nextIndex();
            }

            @Override // java.util.ListIterator
            public int previousIndex() {
                return this.f4500a.previousIndex();
            }

            @Override // java.util.ListIterator, java.util.Iterator
            public void remove() {
                throw new UnsupportedOperationException();
            }

            @Override // java.util.ListIterator
            public void add(String str) {
                throw new UnsupportedOperationException();
            }

            @Override // java.util.ListIterator, java.util.Iterator
            public String next() {
                return (String) this.f4500a.next();
            }

            @Override // java.util.ListIterator
            public String previous() {
                return (String) this.f4500a.previous();
            }

            @Override // java.util.ListIterator
            public void set(String str) {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override // androidx.health.platform.client.proto.LazyStringList
    public void mergeFrom(LazyStringList lazyStringList) {
        throw new UnsupportedOperationException();
    }

    @Override // androidx.health.platform.client.proto.LazyStringList
    public void set(int i2, ByteString byteString) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        return this.list.size();
    }

    @Override // androidx.health.platform.client.proto.LazyStringList
    public void add(byte[] bArr) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractList, java.util.List
    public String get(int i2) {
        return this.list.get(i2);
    }

    @Override // androidx.health.platform.client.proto.LazyStringList
    public void set(int i2, byte[] bArr) {
        throw new UnsupportedOperationException();
    }
}
