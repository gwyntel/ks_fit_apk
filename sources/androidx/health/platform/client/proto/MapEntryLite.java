package androidx.health.platform.client.proto;

import androidx.health.platform.client.proto.MessageLite;
import androidx.health.platform.client.proto.WireFormat;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.Map;

/* loaded from: classes.dex */
public class MapEntryLite<K, V> {
    private static final int KEY_FIELD_NUMBER = 1;
    private static final int VALUE_FIELD_NUMBER = 2;
    private final K key;
    private final Metadata<K, V> metadata;
    private final V value;

    /* renamed from: androidx.health.platform.client.proto.MapEntryLite$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f4467a;

        static {
            int[] iArr = new int[WireFormat.FieldType.values().length];
            f4467a = iArr;
            try {
                iArr[WireFormat.FieldType.MESSAGE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f4467a[WireFormat.FieldType.ENUM.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f4467a[WireFormat.FieldType.GROUP.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    static class Metadata<K, V> {
        public final K defaultKey;
        public final V defaultValue;
        public final WireFormat.FieldType keyType;
        public final WireFormat.FieldType valueType;

        public Metadata(WireFormat.FieldType fieldType, K k2, WireFormat.FieldType fieldType2, V v2) {
            this.keyType = fieldType;
            this.defaultKey = k2;
            this.valueType = fieldType2;
            this.defaultValue = v2;
        }
    }

    private MapEntryLite(WireFormat.FieldType fieldType, K k2, WireFormat.FieldType fieldType2, V v2) {
        this.metadata = new Metadata<>(fieldType, k2, fieldType2, v2);
        this.key = k2;
        this.value = v2;
    }

    static int a(Metadata metadata, Object obj, Object obj2) {
        return FieldSet.h(metadata.keyType, 1, obj) + FieldSet.h(metadata.valueType, 2, obj2);
    }

    static Map.Entry c(CodedInputStream codedInputStream, Metadata metadata, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        Object objD = metadata.defaultKey;
        Object objD2 = metadata.defaultValue;
        while (true) {
            int tag = codedInputStream.readTag();
            if (tag == 0) {
                break;
            }
            if (tag == WireFormat.a(1, metadata.keyType.getWireType())) {
                objD = d(codedInputStream, extensionRegistryLite, metadata.keyType, objD);
            } else if (tag == WireFormat.a(2, metadata.valueType.getWireType())) {
                objD2 = d(codedInputStream, extensionRegistryLite, metadata.valueType, objD2);
            } else if (!codedInputStream.skipField(tag)) {
                break;
            }
        }
        return new AbstractMap.SimpleImmutableEntry(objD, objD2);
    }

    static Object d(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite, WireFormat.FieldType fieldType, Object obj) throws IOException {
        int i2 = AnonymousClass1.f4467a[fieldType.ordinal()];
        if (i2 == 1) {
            MessageLite.Builder builder = ((MessageLite) obj).toBuilder();
            codedInputStream.readMessage(builder, extensionRegistryLite);
            return builder.buildPartial();
        }
        if (i2 == 2) {
            return Integer.valueOf(codedInputStream.readEnum());
        }
        if (i2 != 3) {
            return FieldSet.readPrimitiveField(codedInputStream, fieldType, true);
        }
        throw new RuntimeException("Groups are not allowed in maps.");
    }

    static void e(CodedOutputStream codedOutputStream, Metadata metadata, Object obj, Object obj2) {
        FieldSet.m(codedOutputStream, metadata.keyType, 1, obj);
        FieldSet.m(codedOutputStream, metadata.valueType, 2, obj2);
    }

    public static <K, V> MapEntryLite<K, V> newDefaultInstance(WireFormat.FieldType fieldType, K k2, WireFormat.FieldType fieldType2, V v2) {
        return new MapEntryLite<>(fieldType, k2, fieldType2, v2);
    }

    Metadata b() {
        return this.metadata;
    }

    public int computeMessageSize(int i2, K k2, V v2) {
        return CodedOutputStream.computeTagSize(i2) + CodedOutputStream.d(a(this.metadata, k2, v2));
    }

    public K getKey() {
        return this.key;
    }

    public V getValue() {
        return this.value;
    }

    public Map.Entry<K, V> parseEntry(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        return c(byteString.newCodedInput(), this.metadata, extensionRegistryLite);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void parseInto(MapFieldLite<K, V> mapFieldLite, CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
        int iPushLimit = codedInputStream.pushLimit(codedInputStream.readRawVarint32());
        Metadata<K, V> metadata = this.metadata;
        Object objD = metadata.defaultKey;
        Object objD2 = metadata.defaultValue;
        while (true) {
            int tag = codedInputStream.readTag();
            if (tag == 0) {
                break;
            }
            if (tag == WireFormat.a(1, this.metadata.keyType.getWireType())) {
                objD = d(codedInputStream, extensionRegistryLite, this.metadata.keyType, objD);
            } else if (tag == WireFormat.a(2, this.metadata.valueType.getWireType())) {
                objD2 = d(codedInputStream, extensionRegistryLite, this.metadata.valueType, objD2);
            } else if (!codedInputStream.skipField(tag)) {
                break;
            }
        }
        codedInputStream.checkLastTagWas(0);
        codedInputStream.popLimit(iPushLimit);
        mapFieldLite.put(objD, objD2);
    }

    public void serializeTo(CodedOutputStream codedOutputStream, int i2, K k2, V v2) throws IOException {
        codedOutputStream.writeTag(i2, 2);
        codedOutputStream.writeUInt32NoTag(a(this.metadata, k2, v2));
        e(codedOutputStream, this.metadata, k2, v2);
    }

    private MapEntryLite(Metadata<K, V> metadata, K k2, V v2) {
        this.metadata = metadata;
        this.key = k2;
        this.value = v2;
    }
}
