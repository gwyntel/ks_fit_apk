package com.google.protobuf;

import java.io.IOException;

/* loaded from: classes2.dex */
public class LazyFieldLite {
    private static final ExtensionRegistryLite EMPTY_REGISTRY = ExtensionRegistryLite.getEmptyRegistry();

    /* renamed from: a, reason: collision with root package name */
    protected volatile MessageLite f15303a;
    private ByteString delayedBytes;
    private ExtensionRegistryLite extensionRegistry;
    private volatile ByteString memoizedBytes;

    public LazyFieldLite() {
    }

    private static void checkArguments(ExtensionRegistryLite extensionRegistryLite, ByteString byteString) {
        if (extensionRegistryLite == null) {
            throw new NullPointerException("found null ExtensionRegistry");
        }
        if (byteString == null) {
            throw new NullPointerException("found null ByteString");
        }
    }

    public static LazyFieldLite fromValue(MessageLite messageLite) {
        LazyFieldLite lazyFieldLite = new LazyFieldLite();
        lazyFieldLite.setValue(messageLite);
        return lazyFieldLite;
    }

    private static MessageLite mergeValueAndBytes(MessageLite messageLite, ByteString byteString, ExtensionRegistryLite extensionRegistryLite) {
        try {
            return messageLite.toBuilder().mergeFrom(byteString, extensionRegistryLite).build();
        } catch (InvalidProtocolBufferException unused) {
            return messageLite;
        }
    }

    protected void a(MessageLite messageLite) {
        if (this.f15303a != null) {
            return;
        }
        synchronized (this) {
            if (this.f15303a != null) {
                return;
            }
            try {
                if (this.delayedBytes != null) {
                    this.f15303a = messageLite.getParserForType().parseFrom(this.delayedBytes, this.extensionRegistry);
                    this.memoizedBytes = this.delayedBytes;
                } else {
                    this.f15303a = messageLite;
                    this.memoizedBytes = ByteString.EMPTY;
                }
            } catch (InvalidProtocolBufferException unused) {
                this.f15303a = messageLite;
                this.memoizedBytes = ByteString.EMPTY;
            }
        }
    }

    void b(Writer writer, int i2) throws IOException {
        if (this.memoizedBytes != null) {
            writer.writeBytes(i2, this.memoizedBytes);
            return;
        }
        ByteString byteString = this.delayedBytes;
        if (byteString != null) {
            writer.writeBytes(i2, byteString);
        } else if (this.f15303a != null) {
            writer.writeMessage(i2, this.f15303a);
        } else {
            writer.writeBytes(i2, ByteString.EMPTY);
        }
    }

    public void clear() {
        this.delayedBytes = null;
        this.f15303a = null;
        this.memoizedBytes = null;
    }

    public boolean containsDefaultInstance() {
        ByteString byteString;
        ByteString byteString2 = this.memoizedBytes;
        ByteString byteString3 = ByteString.EMPTY;
        return byteString2 == byteString3 || (this.f15303a == null && ((byteString = this.delayedBytes) == null || byteString == byteString3));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LazyFieldLite)) {
            return false;
        }
        LazyFieldLite lazyFieldLite = (LazyFieldLite) obj;
        MessageLite messageLite = this.f15303a;
        MessageLite messageLite2 = lazyFieldLite.f15303a;
        return (messageLite == null && messageLite2 == null) ? toByteString().equals(lazyFieldLite.toByteString()) : (messageLite == null || messageLite2 == null) ? messageLite != null ? messageLite.equals(lazyFieldLite.getValue(messageLite.getDefaultInstanceForType())) : getValue(messageLite2.getDefaultInstanceForType()).equals(messageLite2) : messageLite.equals(messageLite2);
    }

    public int getSerializedSize() {
        if (this.memoizedBytes != null) {
            return this.memoizedBytes.size();
        }
        ByteString byteString = this.delayedBytes;
        if (byteString != null) {
            return byteString.size();
        }
        if (this.f15303a != null) {
            return this.f15303a.getSerializedSize();
        }
        return 0;
    }

    public MessageLite getValue(MessageLite messageLite) {
        a(messageLite);
        return this.f15303a;
    }

    public int hashCode() {
        return 1;
    }

    public void merge(LazyFieldLite lazyFieldLite) {
        ByteString byteString;
        if (lazyFieldLite.containsDefaultInstance()) {
            return;
        }
        if (containsDefaultInstance()) {
            set(lazyFieldLite);
            return;
        }
        if (this.extensionRegistry == null) {
            this.extensionRegistry = lazyFieldLite.extensionRegistry;
        }
        ByteString byteString2 = this.delayedBytes;
        if (byteString2 != null && (byteString = lazyFieldLite.delayedBytes) != null) {
            this.delayedBytes = byteString2.concat(byteString);
            return;
        }
        if (this.f15303a == null && lazyFieldLite.f15303a != null) {
            setValue(mergeValueAndBytes(lazyFieldLite.f15303a, this.delayedBytes, this.extensionRegistry));
        } else if (this.f15303a == null || lazyFieldLite.f15303a != null) {
            setValue(this.f15303a.toBuilder().mergeFrom(lazyFieldLite.f15303a).build());
        } else {
            setValue(mergeValueAndBytes(this.f15303a, lazyFieldLite.delayedBytes, lazyFieldLite.extensionRegistry));
        }
    }

    public void mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        if (containsDefaultInstance()) {
            setByteString(codedInputStream.readBytes(), extensionRegistryLite);
            return;
        }
        if (this.extensionRegistry == null) {
            this.extensionRegistry = extensionRegistryLite;
        }
        ByteString byteString = this.delayedBytes;
        if (byteString != null) {
            setByteString(byteString.concat(codedInputStream.readBytes()), this.extensionRegistry);
        } else {
            try {
                setValue(this.f15303a.toBuilder().mergeFrom(codedInputStream, extensionRegistryLite).build());
            } catch (InvalidProtocolBufferException unused) {
            }
        }
    }

    public void set(LazyFieldLite lazyFieldLite) {
        this.delayedBytes = lazyFieldLite.delayedBytes;
        this.f15303a = lazyFieldLite.f15303a;
        this.memoizedBytes = lazyFieldLite.memoizedBytes;
        ExtensionRegistryLite extensionRegistryLite = lazyFieldLite.extensionRegistry;
        if (extensionRegistryLite != null) {
            this.extensionRegistry = extensionRegistryLite;
        }
    }

    public void setByteString(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) {
        checkArguments(extensionRegistryLite, byteString);
        this.delayedBytes = byteString;
        this.extensionRegistry = extensionRegistryLite;
        this.f15303a = null;
        this.memoizedBytes = null;
    }

    public MessageLite setValue(MessageLite messageLite) {
        MessageLite messageLite2 = this.f15303a;
        this.delayedBytes = null;
        this.memoizedBytes = null;
        this.f15303a = messageLite;
        return messageLite2;
    }

    public ByteString toByteString() {
        if (this.memoizedBytes != null) {
            return this.memoizedBytes;
        }
        ByteString byteString = this.delayedBytes;
        if (byteString != null) {
            return byteString;
        }
        synchronized (this) {
            try {
                if (this.memoizedBytes != null) {
                    return this.memoizedBytes;
                }
                if (this.f15303a == null) {
                    this.memoizedBytes = ByteString.EMPTY;
                } else {
                    this.memoizedBytes = this.f15303a.toByteString();
                }
                return this.memoizedBytes;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public LazyFieldLite(ExtensionRegistryLite extensionRegistryLite, ByteString byteString) {
        checkArguments(extensionRegistryLite, byteString);
        this.extensionRegistry = extensionRegistryLite;
        this.delayedBytes = byteString;
    }
}
