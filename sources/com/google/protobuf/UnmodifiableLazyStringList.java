package com.google.protobuf;

import java.util.AbstractList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

@Deprecated
/* loaded from: classes2.dex */
public class UnmodifiableLazyStringList extends AbstractList<String> implements LazyStringList, RandomAccess {
    private final LazyStringList list;

    public UnmodifiableLazyStringList(LazyStringList lazyStringList) {
        this.list = lazyStringList;
    }

    @Override // com.google.protobuf.LazyStringList
    public void add(ByteString byteString) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.protobuf.LazyStringList
    public boolean addAllByteArray(Collection<byte[]> collection) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.protobuf.LazyStringList
    public boolean addAllByteString(Collection<? extends ByteString> collection) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.protobuf.LazyStringList
    public List<byte[]> asByteArrayList() {
        return Collections.unmodifiableList(this.list.asByteArrayList());
    }

    @Override // com.google.protobuf.ProtocolStringList
    public List<ByteString> asByteStringList() {
        return Collections.unmodifiableList(this.list.asByteStringList());
    }

    @Override // com.google.protobuf.LazyStringList
    public byte[] getByteArray(int i2) {
        return this.list.getByteArray(i2);
    }

    @Override // com.google.protobuf.LazyStringList
    public ByteString getByteString(int i2) {
        return this.list.getByteString(i2);
    }

    @Override // com.google.protobuf.LazyStringList
    public Object getRaw(int i2) {
        return this.list.getRaw(i2);
    }

    @Override // com.google.protobuf.LazyStringList
    public List<?> getUnderlyingElements() {
        return this.list.getUnderlyingElements();
    }

    @Override // com.google.protobuf.LazyStringList
    public LazyStringList getUnmodifiableView() {
        return this;
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
    public Iterator<String> iterator() {
        return new Iterator<String>() { // from class: com.google.protobuf.UnmodifiableLazyStringList.2

            /* renamed from: a, reason: collision with root package name */
            Iterator f15362a;

            {
                this.f15362a = UnmodifiableLazyStringList.this.list.iterator();
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.f15362a.hasNext();
            }

            @Override // java.util.Iterator
            public void remove() {
                throw new UnsupportedOperationException();
            }

            @Override // java.util.Iterator
            public String next() {
                return (String) this.f15362a.next();
            }
        };
    }

    @Override // java.util.AbstractList, java.util.List
    public ListIterator<String> listIterator(int i2) {
        return new ListIterator<String>(i2) { // from class: com.google.protobuf.UnmodifiableLazyStringList.1

            /* renamed from: a, reason: collision with root package name */
            ListIterator f15359a;

            /* renamed from: b, reason: collision with root package name */
            final /* synthetic */ int f15360b;

            {
                this.f15360b = i2;
                this.f15359a = UnmodifiableLazyStringList.this.list.listIterator(i2);
            }

            @Override // java.util.ListIterator, java.util.Iterator
            public boolean hasNext() {
                return this.f15359a.hasNext();
            }

            @Override // java.util.ListIterator
            public boolean hasPrevious() {
                return this.f15359a.hasPrevious();
            }

            @Override // java.util.ListIterator
            public int nextIndex() {
                return this.f15359a.nextIndex();
            }

            @Override // java.util.ListIterator
            public int previousIndex() {
                return this.f15359a.previousIndex();
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
                return (String) this.f15359a.next();
            }

            @Override // java.util.ListIterator
            public String previous() {
                return (String) this.f15359a.previous();
            }

            @Override // java.util.ListIterator
            public void set(String str) {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override // com.google.protobuf.LazyStringList
    public void mergeFrom(LazyStringList lazyStringList) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.protobuf.LazyStringList
    public void set(int i2, ByteString byteString) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        return this.list.size();
    }

    @Override // com.google.protobuf.LazyStringList
    public void add(byte[] bArr) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractList, java.util.List
    public String get(int i2) {
        return this.list.get(i2);
    }

    @Override // com.google.protobuf.LazyStringList
    public void set(int i2, byte[] bArr) {
        throw new UnsupportedOperationException();
    }
}
