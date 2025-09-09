package androidx.health.platform.client.proto;

import androidx.health.platform.client.proto.MapEntryLite;
import androidx.health.platform.client.proto.WireFormat;
import androidx.health.platform.client.proto.Writer;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@CheckReturnValue
/* loaded from: classes.dex */
final class CodedOutputStreamWriter implements Writer {
    private final CodedOutputStream output;

    /* renamed from: androidx.health.platform.client.proto.CodedOutputStreamWriter$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f4414a;

        static {
            int[] iArr = new int[WireFormat.FieldType.values().length];
            f4414a = iArr;
            try {
                iArr[WireFormat.FieldType.BOOL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f4414a[WireFormat.FieldType.FIXED32.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f4414a[WireFormat.FieldType.INT32.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f4414a[WireFormat.FieldType.SFIXED32.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f4414a[WireFormat.FieldType.SINT32.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f4414a[WireFormat.FieldType.UINT32.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f4414a[WireFormat.FieldType.FIXED64.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f4414a[WireFormat.FieldType.INT64.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                f4414a[WireFormat.FieldType.SFIXED64.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                f4414a[WireFormat.FieldType.SINT64.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                f4414a[WireFormat.FieldType.UINT64.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                f4414a[WireFormat.FieldType.STRING.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
        }
    }

    private CodedOutputStreamWriter(CodedOutputStream codedOutputStream) {
        CodedOutputStream codedOutputStream2 = (CodedOutputStream) Internal.b(codedOutputStream, "output");
        this.output = codedOutputStream2;
        codedOutputStream2.f4409a = this;
    }

    public static CodedOutputStreamWriter forCodedOutput(CodedOutputStream codedOutputStream) {
        CodedOutputStreamWriter codedOutputStreamWriter = codedOutputStream.f4409a;
        return codedOutputStreamWriter != null ? codedOutputStreamWriter : new CodedOutputStreamWriter(codedOutputStream);
    }

    private <V> void writeDeterministicBooleanMapEntry(int i2, boolean z2, V v2, MapEntryLite.Metadata<Boolean, V> metadata) throws IOException {
        this.output.writeTag(i2, 2);
        this.output.writeUInt32NoTag(MapEntryLite.a(metadata, Boolean.valueOf(z2), v2));
        MapEntryLite.e(this.output, metadata, Boolean.valueOf(z2), v2);
    }

    private <V> void writeDeterministicIntegerMap(int i2, MapEntryLite.Metadata<Integer, V> metadata, Map<Integer, V> map) throws IOException {
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
            V v2 = map.get(Integer.valueOf(i5));
            this.output.writeTag(i2, 2);
            this.output.writeUInt32NoTag(MapEntryLite.a(metadata, Integer.valueOf(i5), v2));
            MapEntryLite.e(this.output, metadata, Integer.valueOf(i5), v2);
        }
    }

    private <V> void writeDeterministicLongMap(int i2, MapEntryLite.Metadata<Long, V> metadata, Map<Long, V> map) throws IOException {
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
            V v2 = map.get(Long.valueOf(j2));
            this.output.writeTag(i2, 2);
            this.output.writeUInt32NoTag(MapEntryLite.a(metadata, Long.valueOf(j2), v2));
            MapEntryLite.e(this.output, metadata, Long.valueOf(j2), v2);
        }
    }

    private <K, V> void writeDeterministicMap(int i2, MapEntryLite.Metadata<K, V> metadata, Map<K, V> map) throws IOException {
        switch (AnonymousClass1.f4414a[metadata.keyType.ordinal()]) {
            case 1:
                V v2 = map.get(Boolean.FALSE);
                if (v2 != null) {
                    writeDeterministicBooleanMapEntry(i2, false, v2, metadata);
                }
                V v3 = map.get(Boolean.TRUE);
                if (v3 != null) {
                    writeDeterministicBooleanMapEntry(i2, true, v3, metadata);
                    return;
                }
                return;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                writeDeterministicIntegerMap(i2, metadata, map);
                return;
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                writeDeterministicLongMap(i2, metadata, map);
                return;
            case 12:
                writeDeterministicStringMap(i2, metadata, map);
                return;
            default:
                throw new IllegalArgumentException("does not support key type: " + metadata.keyType);
        }
    }

    private <V> void writeDeterministicStringMap(int i2, MapEntryLite.Metadata<String, V> metadata, Map<String, V> map) throws IOException {
        int size = map.size();
        String[] strArr = new String[size];
        Iterator<String> it = map.keySet().iterator();
        int i3 = 0;
        while (it.hasNext()) {
            strArr[i3] = it.next();
            i3++;
        }
        Arrays.sort(strArr);
        for (int i4 = 0; i4 < size; i4++) {
            String str = strArr[i4];
            V v2 = map.get(str);
            this.output.writeTag(i2, 2);
            this.output.writeUInt32NoTag(MapEntryLite.a(metadata, str, v2));
            MapEntryLite.e(this.output, metadata, str, v2);
        }
    }

    private void writeLazyString(int i2, Object obj) throws IOException {
        if (obj instanceof String) {
            this.output.writeString(i2, (String) obj);
        } else {
            this.output.writeBytes(i2, (ByteString) obj);
        }
    }

    @Override // androidx.health.platform.client.proto.Writer
    public Writer.FieldOrder fieldOrder() {
        return Writer.FieldOrder.ASCENDING;
    }

    public int getTotalBytesWritten() {
        return this.output.getTotalBytesWritten();
    }

    @Override // androidx.health.platform.client.proto.Writer
    public void writeBool(int i2, boolean z2) throws IOException {
        this.output.writeBool(i2, z2);
    }

    @Override // androidx.health.platform.client.proto.Writer
    public void writeBoolList(int i2, List<Boolean> list, boolean z2) throws IOException {
        int i3 = 0;
        if (!z2) {
            while (i3 < list.size()) {
                this.output.writeBool(i2, list.get(i3).booleanValue());
                i3++;
            }
            return;
        }
        this.output.writeTag(i2, 2);
        int iComputeBoolSizeNoTag = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            iComputeBoolSizeNoTag += CodedOutputStream.computeBoolSizeNoTag(list.get(i4).booleanValue());
        }
        this.output.writeUInt32NoTag(iComputeBoolSizeNoTag);
        while (i3 < list.size()) {
            this.output.writeBoolNoTag(list.get(i3).booleanValue());
            i3++;
        }
    }

    @Override // androidx.health.platform.client.proto.Writer
    public void writeBytes(int i2, ByteString byteString) throws IOException {
        this.output.writeBytes(i2, byteString);
    }

    @Override // androidx.health.platform.client.proto.Writer
    public void writeBytesList(int i2, List<ByteString> list) throws IOException {
        for (int i3 = 0; i3 < list.size(); i3++) {
            this.output.writeBytes(i2, list.get(i3));
        }
    }

    @Override // androidx.health.platform.client.proto.Writer
    public void writeDouble(int i2, double d2) throws IOException {
        this.output.writeDouble(i2, d2);
    }

    @Override // androidx.health.platform.client.proto.Writer
    public void writeDoubleList(int i2, List<Double> list, boolean z2) throws IOException {
        int i3 = 0;
        if (!z2) {
            while (i3 < list.size()) {
                this.output.writeDouble(i2, list.get(i3).doubleValue());
                i3++;
            }
            return;
        }
        this.output.writeTag(i2, 2);
        int iComputeDoubleSizeNoTag = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            iComputeDoubleSizeNoTag += CodedOutputStream.computeDoubleSizeNoTag(list.get(i4).doubleValue());
        }
        this.output.writeUInt32NoTag(iComputeDoubleSizeNoTag);
        while (i3 < list.size()) {
            this.output.writeDoubleNoTag(list.get(i3).doubleValue());
            i3++;
        }
    }

    @Override // androidx.health.platform.client.proto.Writer
    @Deprecated
    public void writeEndGroup(int i2) throws IOException {
        this.output.writeTag(i2, 4);
    }

    @Override // androidx.health.platform.client.proto.Writer
    public void writeEnum(int i2, int i3) throws IOException {
        this.output.writeEnum(i2, i3);
    }

    @Override // androidx.health.platform.client.proto.Writer
    public void writeEnumList(int i2, List<Integer> list, boolean z2) throws IOException {
        int i3 = 0;
        if (!z2) {
            while (i3 < list.size()) {
                this.output.writeEnum(i2, list.get(i3).intValue());
                i3++;
            }
            return;
        }
        this.output.writeTag(i2, 2);
        int iComputeEnumSizeNoTag = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            iComputeEnumSizeNoTag += CodedOutputStream.computeEnumSizeNoTag(list.get(i4).intValue());
        }
        this.output.writeUInt32NoTag(iComputeEnumSizeNoTag);
        while (i3 < list.size()) {
            this.output.writeEnumNoTag(list.get(i3).intValue());
            i3++;
        }
    }

    @Override // androidx.health.platform.client.proto.Writer
    public void writeFixed32(int i2, int i3) throws IOException {
        this.output.writeFixed32(i2, i3);
    }

    @Override // androidx.health.platform.client.proto.Writer
    public void writeFixed32List(int i2, List<Integer> list, boolean z2) throws IOException {
        int i3 = 0;
        if (!z2) {
            while (i3 < list.size()) {
                this.output.writeFixed32(i2, list.get(i3).intValue());
                i3++;
            }
            return;
        }
        this.output.writeTag(i2, 2);
        int iComputeFixed32SizeNoTag = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            iComputeFixed32SizeNoTag += CodedOutputStream.computeFixed32SizeNoTag(list.get(i4).intValue());
        }
        this.output.writeUInt32NoTag(iComputeFixed32SizeNoTag);
        while (i3 < list.size()) {
            this.output.writeFixed32NoTag(list.get(i3).intValue());
            i3++;
        }
    }

    @Override // androidx.health.platform.client.proto.Writer
    public void writeFixed64(int i2, long j2) throws IOException {
        this.output.writeFixed64(i2, j2);
    }

    @Override // androidx.health.platform.client.proto.Writer
    public void writeFixed64List(int i2, List<Long> list, boolean z2) throws IOException {
        int i3 = 0;
        if (!z2) {
            while (i3 < list.size()) {
                this.output.writeFixed64(i2, list.get(i3).longValue());
                i3++;
            }
            return;
        }
        this.output.writeTag(i2, 2);
        int iComputeFixed64SizeNoTag = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            iComputeFixed64SizeNoTag += CodedOutputStream.computeFixed64SizeNoTag(list.get(i4).longValue());
        }
        this.output.writeUInt32NoTag(iComputeFixed64SizeNoTag);
        while (i3 < list.size()) {
            this.output.writeFixed64NoTag(list.get(i3).longValue());
            i3++;
        }
    }

    @Override // androidx.health.platform.client.proto.Writer
    public void writeFloat(int i2, float f2) throws IOException {
        this.output.writeFloat(i2, f2);
    }

    @Override // androidx.health.platform.client.proto.Writer
    public void writeFloatList(int i2, List<Float> list, boolean z2) throws IOException {
        int i3 = 0;
        if (!z2) {
            while (i3 < list.size()) {
                this.output.writeFloat(i2, list.get(i3).floatValue());
                i3++;
            }
            return;
        }
        this.output.writeTag(i2, 2);
        int iComputeFloatSizeNoTag = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            iComputeFloatSizeNoTag += CodedOutputStream.computeFloatSizeNoTag(list.get(i4).floatValue());
        }
        this.output.writeUInt32NoTag(iComputeFloatSizeNoTag);
        while (i3 < list.size()) {
            this.output.writeFloatNoTag(list.get(i3).floatValue());
            i3++;
        }
    }

    @Override // androidx.health.platform.client.proto.Writer
    @Deprecated
    public void writeGroup(int i2, Object obj) throws IOException {
        this.output.writeGroup(i2, (MessageLite) obj);
    }

    @Override // androidx.health.platform.client.proto.Writer
    @Deprecated
    public void writeGroupList(int i2, List<?> list) throws IOException {
        for (int i3 = 0; i3 < list.size(); i3++) {
            writeGroup(i2, list.get(i3));
        }
    }

    @Override // androidx.health.platform.client.proto.Writer
    public void writeInt32(int i2, int i3) throws IOException {
        this.output.writeInt32(i2, i3);
    }

    @Override // androidx.health.platform.client.proto.Writer
    public void writeInt32List(int i2, List<Integer> list, boolean z2) throws IOException {
        int i3 = 0;
        if (!z2) {
            while (i3 < list.size()) {
                this.output.writeInt32(i2, list.get(i3).intValue());
                i3++;
            }
            return;
        }
        this.output.writeTag(i2, 2);
        int iComputeInt32SizeNoTag = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            iComputeInt32SizeNoTag += CodedOutputStream.computeInt32SizeNoTag(list.get(i4).intValue());
        }
        this.output.writeUInt32NoTag(iComputeInt32SizeNoTag);
        while (i3 < list.size()) {
            this.output.writeInt32NoTag(list.get(i3).intValue());
            i3++;
        }
    }

    @Override // androidx.health.platform.client.proto.Writer
    public void writeInt64(int i2, long j2) throws IOException {
        this.output.writeInt64(i2, j2);
    }

    @Override // androidx.health.platform.client.proto.Writer
    public void writeInt64List(int i2, List<Long> list, boolean z2) throws IOException {
        int i3 = 0;
        if (!z2) {
            while (i3 < list.size()) {
                this.output.writeInt64(i2, list.get(i3).longValue());
                i3++;
            }
            return;
        }
        this.output.writeTag(i2, 2);
        int iComputeInt64SizeNoTag = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            iComputeInt64SizeNoTag += CodedOutputStream.computeInt64SizeNoTag(list.get(i4).longValue());
        }
        this.output.writeUInt32NoTag(iComputeInt64SizeNoTag);
        while (i3 < list.size()) {
            this.output.writeInt64NoTag(list.get(i3).longValue());
            i3++;
        }
    }

    @Override // androidx.health.platform.client.proto.Writer
    public <K, V> void writeMap(int i2, MapEntryLite.Metadata<K, V> metadata, Map<K, V> map) throws IOException {
        if (this.output.i()) {
            writeDeterministicMap(i2, metadata, map);
            return;
        }
        for (Map.Entry<K, V> entry : map.entrySet()) {
            this.output.writeTag(i2, 2);
            this.output.writeUInt32NoTag(MapEntryLite.a(metadata, entry.getKey(), entry.getValue()));
            MapEntryLite.e(this.output, metadata, entry.getKey(), entry.getValue());
        }
    }

    @Override // androidx.health.platform.client.proto.Writer
    public void writeMessage(int i2, Object obj) throws IOException {
        this.output.writeMessage(i2, (MessageLite) obj);
    }

    @Override // androidx.health.platform.client.proto.Writer
    public void writeMessageList(int i2, List<?> list) throws IOException {
        for (int i3 = 0; i3 < list.size(); i3++) {
            writeMessage(i2, list.get(i3));
        }
    }

    @Override // androidx.health.platform.client.proto.Writer
    public final void writeMessageSetItem(int i2, Object obj) throws IOException {
        if (obj instanceof ByteString) {
            this.output.writeRawMessageSetExtension(i2, (ByteString) obj);
        } else {
            this.output.writeMessageSetExtension(i2, (MessageLite) obj);
        }
    }

    @Override // androidx.health.platform.client.proto.Writer
    public void writeSFixed32(int i2, int i3) throws IOException {
        this.output.writeSFixed32(i2, i3);
    }

    @Override // androidx.health.platform.client.proto.Writer
    public void writeSFixed32List(int i2, List<Integer> list, boolean z2) throws IOException {
        int i3 = 0;
        if (!z2) {
            while (i3 < list.size()) {
                this.output.writeSFixed32(i2, list.get(i3).intValue());
                i3++;
            }
            return;
        }
        this.output.writeTag(i2, 2);
        int iComputeSFixed32SizeNoTag = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            iComputeSFixed32SizeNoTag += CodedOutputStream.computeSFixed32SizeNoTag(list.get(i4).intValue());
        }
        this.output.writeUInt32NoTag(iComputeSFixed32SizeNoTag);
        while (i3 < list.size()) {
            this.output.writeSFixed32NoTag(list.get(i3).intValue());
            i3++;
        }
    }

    @Override // androidx.health.platform.client.proto.Writer
    public void writeSFixed64(int i2, long j2) throws IOException {
        this.output.writeSFixed64(i2, j2);
    }

    @Override // androidx.health.platform.client.proto.Writer
    public void writeSFixed64List(int i2, List<Long> list, boolean z2) throws IOException {
        int i3 = 0;
        if (!z2) {
            while (i3 < list.size()) {
                this.output.writeSFixed64(i2, list.get(i3).longValue());
                i3++;
            }
            return;
        }
        this.output.writeTag(i2, 2);
        int iComputeSFixed64SizeNoTag = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            iComputeSFixed64SizeNoTag += CodedOutputStream.computeSFixed64SizeNoTag(list.get(i4).longValue());
        }
        this.output.writeUInt32NoTag(iComputeSFixed64SizeNoTag);
        while (i3 < list.size()) {
            this.output.writeSFixed64NoTag(list.get(i3).longValue());
            i3++;
        }
    }

    @Override // androidx.health.platform.client.proto.Writer
    public void writeSInt32(int i2, int i3) throws IOException {
        this.output.writeSInt32(i2, i3);
    }

    @Override // androidx.health.platform.client.proto.Writer
    public void writeSInt32List(int i2, List<Integer> list, boolean z2) throws IOException {
        int i3 = 0;
        if (!z2) {
            while (i3 < list.size()) {
                this.output.writeSInt32(i2, list.get(i3).intValue());
                i3++;
            }
            return;
        }
        this.output.writeTag(i2, 2);
        int iComputeSInt32SizeNoTag = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            iComputeSInt32SizeNoTag += CodedOutputStream.computeSInt32SizeNoTag(list.get(i4).intValue());
        }
        this.output.writeUInt32NoTag(iComputeSInt32SizeNoTag);
        while (i3 < list.size()) {
            this.output.writeSInt32NoTag(list.get(i3).intValue());
            i3++;
        }
    }

    @Override // androidx.health.platform.client.proto.Writer
    public void writeSInt64(int i2, long j2) throws IOException {
        this.output.writeSInt64(i2, j2);
    }

    @Override // androidx.health.platform.client.proto.Writer
    public void writeSInt64List(int i2, List<Long> list, boolean z2) throws IOException {
        int i3 = 0;
        if (!z2) {
            while (i3 < list.size()) {
                this.output.writeSInt64(i2, list.get(i3).longValue());
                i3++;
            }
            return;
        }
        this.output.writeTag(i2, 2);
        int iComputeSInt64SizeNoTag = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            iComputeSInt64SizeNoTag += CodedOutputStream.computeSInt64SizeNoTag(list.get(i4).longValue());
        }
        this.output.writeUInt32NoTag(iComputeSInt64SizeNoTag);
        while (i3 < list.size()) {
            this.output.writeSInt64NoTag(list.get(i3).longValue());
            i3++;
        }
    }

    @Override // androidx.health.platform.client.proto.Writer
    @Deprecated
    public void writeStartGroup(int i2) throws IOException {
        this.output.writeTag(i2, 3);
    }

    @Override // androidx.health.platform.client.proto.Writer
    public void writeString(int i2, String str) throws IOException {
        this.output.writeString(i2, str);
    }

    @Override // androidx.health.platform.client.proto.Writer
    public void writeStringList(int i2, List<String> list) throws IOException {
        int i3 = 0;
        if (!(list instanceof LazyStringList)) {
            while (i3 < list.size()) {
                this.output.writeString(i2, list.get(i3));
                i3++;
            }
        } else {
            LazyStringList lazyStringList = (LazyStringList) list;
            while (i3 < list.size()) {
                writeLazyString(i2, lazyStringList.getRaw(i3));
                i3++;
            }
        }
    }

    @Override // androidx.health.platform.client.proto.Writer
    public void writeUInt32(int i2, int i3) throws IOException {
        this.output.writeUInt32(i2, i3);
    }

    @Override // androidx.health.platform.client.proto.Writer
    public void writeUInt32List(int i2, List<Integer> list, boolean z2) throws IOException {
        int i3 = 0;
        if (!z2) {
            while (i3 < list.size()) {
                this.output.writeUInt32(i2, list.get(i3).intValue());
                i3++;
            }
            return;
        }
        this.output.writeTag(i2, 2);
        int iComputeUInt32SizeNoTag = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            iComputeUInt32SizeNoTag += CodedOutputStream.computeUInt32SizeNoTag(list.get(i4).intValue());
        }
        this.output.writeUInt32NoTag(iComputeUInt32SizeNoTag);
        while (i3 < list.size()) {
            this.output.writeUInt32NoTag(list.get(i3).intValue());
            i3++;
        }
    }

    @Override // androidx.health.platform.client.proto.Writer
    public void writeUInt64(int i2, long j2) throws IOException {
        this.output.writeUInt64(i2, j2);
    }

    @Override // androidx.health.platform.client.proto.Writer
    public void writeUInt64List(int i2, List<Long> list, boolean z2) throws IOException {
        int i3 = 0;
        if (!z2) {
            while (i3 < list.size()) {
                this.output.writeUInt64(i2, list.get(i3).longValue());
                i3++;
            }
            return;
        }
        this.output.writeTag(i2, 2);
        int iComputeUInt64SizeNoTag = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            iComputeUInt64SizeNoTag += CodedOutputStream.computeUInt64SizeNoTag(list.get(i4).longValue());
        }
        this.output.writeUInt32NoTag(iComputeUInt64SizeNoTag);
        while (i3 < list.size()) {
            this.output.writeUInt64NoTag(list.get(i3).longValue());
            i3++;
        }
    }

    @Override // androidx.health.platform.client.proto.Writer
    public void writeGroup(int i2, Object obj, Schema schema) throws IOException {
        this.output.l(i2, (MessageLite) obj, schema);
    }

    @Override // androidx.health.platform.client.proto.Writer
    public void writeMessage(int i2, Object obj, Schema schema) throws IOException {
        this.output.n(i2, (MessageLite) obj, schema);
    }

    @Override // androidx.health.platform.client.proto.Writer
    public void writeGroupList(int i2, List<?> list, Schema schema) throws IOException {
        for (int i3 = 0; i3 < list.size(); i3++) {
            writeGroup(i2, list.get(i3), schema);
        }
    }

    @Override // androidx.health.platform.client.proto.Writer
    public void writeMessageList(int i2, List<?> list, Schema schema) throws IOException {
        for (int i3 = 0; i3 < list.size(); i3++) {
            writeMessage(i2, list.get(i3), schema);
        }
    }
}
