package com.google.protobuf;

import com.google.protobuf.Internal;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;

@CheckReturnValue
/* loaded from: classes2.dex */
final class SchemaUtil {
    private static final int DEFAULT_LOOK_UP_START_NUMBER = 40;
    private static final Class<?> GENERATED_MESSAGE_CLASS = getGeneratedMessageClass();
    private static final UnknownFieldSchema<?, ?> PROTO2_UNKNOWN_FIELD_SET_SCHEMA = getUnknownFieldSetSchema(false);
    private static final UnknownFieldSchema<?, ?> PROTO3_UNKNOWN_FIELD_SET_SCHEMA = getUnknownFieldSetSchema(true);
    private static final UnknownFieldSchema<?, ?> UNKNOWN_FIELD_SET_LITE_SCHEMA = new UnknownFieldSetLiteSchema();

    private SchemaUtil() {
    }

    static Object A(Object obj, int i2, List list, Internal.EnumVerifier enumVerifier, Object obj2, UnknownFieldSchema unknownFieldSchema) {
        if (enumVerifier == null) {
            return obj2;
        }
        if (list instanceof RandomAccess) {
            int size = list.size();
            int i3 = 0;
            for (int i4 = 0; i4 < size; i4++) {
                Integer num = (Integer) list.get(i4);
                int iIntValue = num.intValue();
                if (enumVerifier.isInRange(iIntValue)) {
                    if (i4 != i3) {
                        list.set(i3, num);
                    }
                    i3++;
                } else {
                    obj2 = G(obj, i2, iIntValue, obj2, unknownFieldSchema);
                }
            }
            if (i3 != size) {
                list.subList(i3, size).clear();
            }
        } else {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                int iIntValue2 = ((Integer) it.next()).intValue();
                if (!enumVerifier.isInRange(iIntValue2)) {
                    obj2 = G(obj, i2, iIntValue2, obj2, unknownFieldSchema);
                    it.remove();
                }
            }
        }
        return obj2;
    }

    static Object B(Class cls, String str) {
        try {
            java.lang.reflect.Field[] declaredFields = Class.forName(cls.getName() + "$" + H(str, true) + "DefaultEntryHolder").getDeclaredFields();
            if (declaredFields.length == 1) {
                return UnsafeUtil.y(declaredFields[0]);
            }
            throw new IllegalStateException("Unable to look up map field default entry holder class for " + str + " in " + cls.getName());
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }

    static void C(ExtensionSchema extensionSchema, Object obj, Object obj2) {
        FieldSet extensions = extensionSchema.getExtensions(obj2);
        if (extensions.l()) {
            return;
        }
        extensionSchema.c(obj).mergeFrom(extensions);
    }

    static void D(MapFieldSchema mapFieldSchema, Object obj, Object obj2, long j2) {
        UnsafeUtil.K(obj, j2, mapFieldSchema.mergeFrom(UnsafeUtil.x(obj, j2), UnsafeUtil.x(obj2, j2)));
    }

    static void E(UnknownFieldSchema unknownFieldSchema, Object obj, Object obj2) {
        unknownFieldSchema.p(obj, unknownFieldSchema.k(unknownFieldSchema.g(obj), unknownFieldSchema.g(obj2)));
    }

    static boolean F(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }

    static Object G(Object obj, int i2, int i3, Object obj2, UnknownFieldSchema unknownFieldSchema) {
        if (obj2 == null) {
            obj2 = unknownFieldSchema.f(obj);
        }
        unknownFieldSchema.e(obj2, i2, i3);
        return obj2;
    }

    static String H(String str, boolean z2) {
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < str.length(); i2++) {
            char cCharAt = str.charAt(i2);
            if ('a' > cCharAt || cCharAt > 'z') {
                if ('A' > cCharAt || cCharAt > 'Z') {
                    if ('0' <= cCharAt && cCharAt <= '9') {
                        sb.append(cCharAt);
                    }
                    z2 = true;
                } else if (i2 != 0 || z2) {
                    sb.append(cCharAt);
                } else {
                    sb.append((char) (cCharAt + ' '));
                }
            } else if (z2) {
                sb.append((char) (cCharAt - ' '));
            } else {
                sb.append(cCharAt);
            }
            z2 = false;
        }
        return sb.toString();
    }

    static int a(int i2, List list, boolean z2) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return z2 ? CodedOutputStream.computeTagSize(i2) + CodedOutputStream.d(size) : size * CodedOutputStream.computeBoolSize(i2, true);
    }

    static int b(List list) {
        return list.size();
    }

    static int c(int i2, List list) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int iComputeTagSize = size * CodedOutputStream.computeTagSize(i2);
        for (int i3 = 0; i3 < list.size(); i3++) {
            iComputeTagSize += CodedOutputStream.computeBytesSizeNoTag((ByteString) list.get(i3));
        }
        return iComputeTagSize;
    }

    static int d(int i2, List list, boolean z2) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int iE = e(list);
        return z2 ? CodedOutputStream.computeTagSize(i2) + CodedOutputStream.d(iE) : iE + (size * CodedOutputStream.computeTagSize(i2));
    }

    static int e(List list) {
        int iComputeEnumSizeNoTag;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof IntArrayList) {
            IntArrayList intArrayList = (IntArrayList) list;
            iComputeEnumSizeNoTag = 0;
            while (i2 < size) {
                iComputeEnumSizeNoTag += CodedOutputStream.computeEnumSizeNoTag(intArrayList.getInt(i2));
                i2++;
            }
        } else {
            iComputeEnumSizeNoTag = 0;
            while (i2 < size) {
                iComputeEnumSizeNoTag += CodedOutputStream.computeEnumSizeNoTag(((Integer) list.get(i2)).intValue());
                i2++;
            }
        }
        return iComputeEnumSizeNoTag;
    }

    static int f(int i2, List list, boolean z2) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return z2 ? CodedOutputStream.computeTagSize(i2) + CodedOutputStream.d(size * 4) : size * CodedOutputStream.computeFixed32Size(i2, 0);
    }

    static int g(List list) {
        return list.size() * 4;
    }

    private static Class<?> getGeneratedMessageClass() {
        try {
            boolean z2 = GeneratedMessageV3.alwaysUseFieldBuilders;
            return GeneratedMessageV3.class;
        } catch (Throwable unused) {
            return null;
        }
    }

    private static UnknownFieldSchema<?, ?> getUnknownFieldSetSchema(boolean z2) {
        try {
            Class<?> unknownFieldSetSchemaClass = getUnknownFieldSetSchemaClass();
            if (unknownFieldSetSchemaClass == null) {
                return null;
            }
            return (UnknownFieldSchema) unknownFieldSetSchemaClass.getConstructor(Boolean.TYPE).newInstance(Boolean.valueOf(z2));
        } catch (Throwable unused) {
            return null;
        }
    }

    private static Class<?> getUnknownFieldSetSchemaClass() {
        return UnknownFieldSetSchema.class;
    }

    static int h(int i2, List list, boolean z2) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return z2 ? CodedOutputStream.computeTagSize(i2) + CodedOutputStream.d(size * 8) : size * CodedOutputStream.computeFixed64Size(i2, 0L);
    }

    static int i(List list) {
        return list.size() * 8;
    }

    static int j(int i2, List list, Schema schema) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int iB = 0;
        for (int i3 = 0; i3 < size; i3++) {
            iB += CodedOutputStream.b(i2, (MessageLite) list.get(i3), schema);
        }
        return iB;
    }

    static int k(int i2, List list, boolean z2) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int iL = l(list);
        return z2 ? CodedOutputStream.computeTagSize(i2) + CodedOutputStream.d(iL) : iL + (size * CodedOutputStream.computeTagSize(i2));
    }

    static int l(List list) {
        int iComputeInt32SizeNoTag;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof IntArrayList) {
            IntArrayList intArrayList = (IntArrayList) list;
            iComputeInt32SizeNoTag = 0;
            while (i2 < size) {
                iComputeInt32SizeNoTag += CodedOutputStream.computeInt32SizeNoTag(intArrayList.getInt(i2));
                i2++;
            }
        } else {
            iComputeInt32SizeNoTag = 0;
            while (i2 < size) {
                iComputeInt32SizeNoTag += CodedOutputStream.computeInt32SizeNoTag(((Integer) list.get(i2)).intValue());
                i2++;
            }
        }
        return iComputeInt32SizeNoTag;
    }

    static int m(int i2, List list, boolean z2) {
        if (list.size() == 0) {
            return 0;
        }
        int iN = n(list);
        return z2 ? CodedOutputStream.computeTagSize(i2) + CodedOutputStream.d(iN) : iN + (list.size() * CodedOutputStream.computeTagSize(i2));
    }

    static int n(List list) {
        int iComputeInt64SizeNoTag;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof LongArrayList) {
            LongArrayList longArrayList = (LongArrayList) list;
            iComputeInt64SizeNoTag = 0;
            while (i2 < size) {
                iComputeInt64SizeNoTag += CodedOutputStream.computeInt64SizeNoTag(longArrayList.getLong(i2));
                i2++;
            }
        } else {
            iComputeInt64SizeNoTag = 0;
            while (i2 < size) {
                iComputeInt64SizeNoTag += CodedOutputStream.computeInt64SizeNoTag(((Long) list.get(i2)).longValue());
                i2++;
            }
        }
        return iComputeInt64SizeNoTag;
    }

    static int o(int i2, Object obj, Schema schema) {
        return obj instanceof LazyFieldLite ? CodedOutputStream.computeLazyFieldSize(i2, (LazyFieldLite) obj) : CodedOutputStream.e(i2, (MessageLite) obj, schema);
    }

    static int p(int i2, List list, Schema schema) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int iComputeTagSize = CodedOutputStream.computeTagSize(i2) * size;
        for (int i3 = 0; i3 < size; i3++) {
            Object obj = list.get(i3);
            iComputeTagSize += obj instanceof LazyFieldLite ? CodedOutputStream.computeLazyFieldSizeNoTag((LazyFieldLite) obj) : CodedOutputStream.f((MessageLite) obj, schema);
        }
        return iComputeTagSize;
    }

    public static UnknownFieldSchema<?, ?> proto2UnknownFieldSetSchema() {
        return PROTO2_UNKNOWN_FIELD_SET_SCHEMA;
    }

    public static UnknownFieldSchema<?, ?> proto3UnknownFieldSetSchema() {
        return PROTO3_UNKNOWN_FIELD_SET_SCHEMA;
    }

    static int q(int i2, List list, boolean z2) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int iR = r(list);
        return z2 ? CodedOutputStream.computeTagSize(i2) + CodedOutputStream.d(iR) : iR + (size * CodedOutputStream.computeTagSize(i2));
    }

    static int r(List list) {
        int iComputeSInt32SizeNoTag;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof IntArrayList) {
            IntArrayList intArrayList = (IntArrayList) list;
            iComputeSInt32SizeNoTag = 0;
            while (i2 < size) {
                iComputeSInt32SizeNoTag += CodedOutputStream.computeSInt32SizeNoTag(intArrayList.getInt(i2));
                i2++;
            }
        } else {
            iComputeSInt32SizeNoTag = 0;
            while (i2 < size) {
                iComputeSInt32SizeNoTag += CodedOutputStream.computeSInt32SizeNoTag(((Integer) list.get(i2)).intValue());
                i2++;
            }
        }
        return iComputeSInt32SizeNoTag;
    }

    public static void requireGeneratedMessage(Class<?> cls) {
        Class<?> cls2;
        if (!GeneratedMessageLite.class.isAssignableFrom(cls) && (cls2 = GENERATED_MESSAGE_CLASS) != null && !cls2.isAssignableFrom(cls)) {
            throw new IllegalArgumentException("Message classes must extend GeneratedMessageV3 or GeneratedMessageLite");
        }
    }

    static int s(int i2, List list, boolean z2) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int iT = t(list);
        return z2 ? CodedOutputStream.computeTagSize(i2) + CodedOutputStream.d(iT) : iT + (size * CodedOutputStream.computeTagSize(i2));
    }

    public static boolean shouldUseTableSwitch(int i2, int i3, int i4) {
        if (i3 < 40) {
            return true;
        }
        long j2 = i3 - i2;
        long j3 = i4;
        return j2 + 10 <= ((2 * j3) + 3) + ((j3 + 3) * 3);
    }

    static int t(List list) {
        int iComputeSInt64SizeNoTag;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof LongArrayList) {
            LongArrayList longArrayList = (LongArrayList) list;
            iComputeSInt64SizeNoTag = 0;
            while (i2 < size) {
                iComputeSInt64SizeNoTag += CodedOutputStream.computeSInt64SizeNoTag(longArrayList.getLong(i2));
                i2++;
            }
        } else {
            iComputeSInt64SizeNoTag = 0;
            while (i2 < size) {
                iComputeSInt64SizeNoTag += CodedOutputStream.computeSInt64SizeNoTag(((Long) list.get(i2)).longValue());
                i2++;
            }
        }
        return iComputeSInt64SizeNoTag;
    }

    static int u(int i2, List list) {
        int size = list.size();
        int i3 = 0;
        if (size == 0) {
            return 0;
        }
        int iComputeTagSize = CodedOutputStream.computeTagSize(i2) * size;
        if (list instanceof LazyStringList) {
            LazyStringList lazyStringList = (LazyStringList) list;
            while (i3 < size) {
                Object raw = lazyStringList.getRaw(i3);
                iComputeTagSize += raw instanceof ByteString ? CodedOutputStream.computeBytesSizeNoTag((ByteString) raw) : CodedOutputStream.computeStringSizeNoTag((String) raw);
                i3++;
            }
        } else {
            while (i3 < size) {
                Object obj = list.get(i3);
                iComputeTagSize += obj instanceof ByteString ? CodedOutputStream.computeBytesSizeNoTag((ByteString) obj) : CodedOutputStream.computeStringSizeNoTag((String) obj);
                i3++;
            }
        }
        return iComputeTagSize;
    }

    public static UnknownFieldSchema<?, ?> unknownFieldSetLiteSchema() {
        return UNKNOWN_FIELD_SET_LITE_SCHEMA;
    }

    static int v(int i2, List list, boolean z2) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int iW = w(list);
        return z2 ? CodedOutputStream.computeTagSize(i2) + CodedOutputStream.d(iW) : iW + (size * CodedOutputStream.computeTagSize(i2));
    }

    static int w(List list) {
        int iComputeUInt32SizeNoTag;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof IntArrayList) {
            IntArrayList intArrayList = (IntArrayList) list;
            iComputeUInt32SizeNoTag = 0;
            while (i2 < size) {
                iComputeUInt32SizeNoTag += CodedOutputStream.computeUInt32SizeNoTag(intArrayList.getInt(i2));
                i2++;
            }
        } else {
            iComputeUInt32SizeNoTag = 0;
            while (i2 < size) {
                iComputeUInt32SizeNoTag += CodedOutputStream.computeUInt32SizeNoTag(((Integer) list.get(i2)).intValue());
                i2++;
            }
        }
        return iComputeUInt32SizeNoTag;
    }

    public static void writeBool(int i2, boolean z2, Writer writer) throws IOException {
        if (z2) {
            writer.writeBool(i2, true);
        }
    }

    public static void writeBoolList(int i2, List<Boolean> list, Writer writer, boolean z2) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        writer.writeBoolList(i2, list, z2);
    }

    public static void writeBytes(int i2, ByteString byteString, Writer writer) throws IOException {
        if (byteString == null || byteString.isEmpty()) {
            return;
        }
        writer.writeBytes(i2, byteString);
    }

    public static void writeBytesList(int i2, List<ByteString> list, Writer writer) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        writer.writeBytesList(i2, list);
    }

    public static void writeDouble(int i2, double d2, Writer writer) throws IOException {
        if (Double.doubleToRawLongBits(d2) != 0) {
            writer.writeDouble(i2, d2);
        }
    }

    public static void writeDoubleList(int i2, List<Double> list, Writer writer, boolean z2) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        writer.writeDoubleList(i2, list, z2);
    }

    public static void writeEnum(int i2, int i3, Writer writer) throws IOException {
        if (i3 != 0) {
            writer.writeEnum(i2, i3);
        }
    }

    public static void writeEnumList(int i2, List<Integer> list, Writer writer, boolean z2) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        writer.writeEnumList(i2, list, z2);
    }

    public static void writeFixed32(int i2, int i3, Writer writer) throws IOException {
        if (i3 != 0) {
            writer.writeFixed32(i2, i3);
        }
    }

    public static void writeFixed32List(int i2, List<Integer> list, Writer writer, boolean z2) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        writer.writeFixed32List(i2, list, z2);
    }

    public static void writeFixed64(int i2, long j2, Writer writer) throws IOException {
        if (j2 != 0) {
            writer.writeFixed64(i2, j2);
        }
    }

    public static void writeFixed64List(int i2, List<Long> list, Writer writer, boolean z2) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        writer.writeFixed64List(i2, list, z2);
    }

    public static void writeFloat(int i2, float f2, Writer writer) throws IOException {
        if (Float.floatToRawIntBits(f2) != 0) {
            writer.writeFloat(i2, f2);
        }
    }

    public static void writeFloatList(int i2, List<Float> list, Writer writer, boolean z2) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        writer.writeFloatList(i2, list, z2);
    }

    public static void writeGroupList(int i2, List<?> list, Writer writer) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        writer.writeGroupList(i2, list);
    }

    public static void writeInt32(int i2, int i3, Writer writer) throws IOException {
        if (i3 != 0) {
            writer.writeInt32(i2, i3);
        }
    }

    public static void writeInt32List(int i2, List<Integer> list, Writer writer, boolean z2) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        writer.writeInt32List(i2, list, z2);
    }

    public static void writeInt64(int i2, long j2, Writer writer) throws IOException {
        if (j2 != 0) {
            writer.writeInt64(i2, j2);
        }
    }

    public static void writeInt64List(int i2, List<Long> list, Writer writer, boolean z2) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        writer.writeInt64List(i2, list, z2);
    }

    public static void writeLazyFieldList(int i2, List<?> list, Writer writer) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        Iterator<?> it = list.iterator();
        while (it.hasNext()) {
            ((LazyFieldLite) it.next()).b(writer, i2);
        }
    }

    public static void writeMessage(int i2, Object obj, Writer writer) throws IOException {
        if (obj != null) {
            writer.writeMessage(i2, obj);
        }
    }

    public static void writeMessageList(int i2, List<?> list, Writer writer) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        writer.writeMessageList(i2, list);
    }

    public static void writeSFixed32(int i2, int i3, Writer writer) throws IOException {
        if (i3 != 0) {
            writer.writeSFixed32(i2, i3);
        }
    }

    public static void writeSFixed32List(int i2, List<Integer> list, Writer writer, boolean z2) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        writer.writeSFixed32List(i2, list, z2);
    }

    public static void writeSFixed64(int i2, long j2, Writer writer) throws IOException {
        if (j2 != 0) {
            writer.writeSFixed64(i2, j2);
        }
    }

    public static void writeSFixed64List(int i2, List<Long> list, Writer writer, boolean z2) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        writer.writeSFixed64List(i2, list, z2);
    }

    public static void writeSInt32(int i2, int i3, Writer writer) throws IOException {
        if (i3 != 0) {
            writer.writeSInt32(i2, i3);
        }
    }

    public static void writeSInt32List(int i2, List<Integer> list, Writer writer, boolean z2) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        writer.writeSInt32List(i2, list, z2);
    }

    public static void writeSInt64(int i2, long j2, Writer writer) throws IOException {
        if (j2 != 0) {
            writer.writeSInt64(i2, j2);
        }
    }

    public static void writeSInt64List(int i2, List<Long> list, Writer writer, boolean z2) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        writer.writeSInt64List(i2, list, z2);
    }

    public static void writeString(int i2, Object obj, Writer writer) throws IOException {
        if (obj instanceof String) {
            writeStringInternal(i2, (String) obj, writer);
        } else {
            writeBytes(i2, (ByteString) obj, writer);
        }
    }

    private static void writeStringInternal(int i2, String str, Writer writer) throws IOException {
        if (str == null || str.isEmpty()) {
            return;
        }
        writer.writeString(i2, str);
    }

    public static void writeStringList(int i2, List<String> list, Writer writer) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        writer.writeStringList(i2, list);
    }

    public static void writeUInt32(int i2, int i3, Writer writer) throws IOException {
        if (i3 != 0) {
            writer.writeUInt32(i2, i3);
        }
    }

    public static void writeUInt32List(int i2, List<Integer> list, Writer writer, boolean z2) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        writer.writeUInt32List(i2, list, z2);
    }

    public static void writeUInt64(int i2, long j2, Writer writer) throws IOException {
        if (j2 != 0) {
            writer.writeUInt64(i2, j2);
        }
    }

    public static void writeUInt64List(int i2, List<Long> list, Writer writer, boolean z2) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        writer.writeUInt64List(i2, list, z2);
    }

    static int x(int i2, List list, boolean z2) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int iY = y(list);
        return z2 ? CodedOutputStream.computeTagSize(i2) + CodedOutputStream.d(iY) : iY + (size * CodedOutputStream.computeTagSize(i2));
    }

    static int y(List list) {
        int iComputeUInt64SizeNoTag;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof LongArrayList) {
            LongArrayList longArrayList = (LongArrayList) list;
            iComputeUInt64SizeNoTag = 0;
            while (i2 < size) {
                iComputeUInt64SizeNoTag += CodedOutputStream.computeUInt64SizeNoTag(longArrayList.getLong(i2));
                i2++;
            }
        } else {
            iComputeUInt64SizeNoTag = 0;
            while (i2 < size) {
                iComputeUInt64SizeNoTag += CodedOutputStream.computeUInt64SizeNoTag(((Long) list.get(i2)).longValue());
                i2++;
            }
        }
        return iComputeUInt64SizeNoTag;
    }

    static Object z(Object obj, int i2, List list, Internal.EnumLiteMap enumLiteMap, Object obj2, UnknownFieldSchema unknownFieldSchema) {
        if (enumLiteMap == null) {
            return obj2;
        }
        if (list instanceof RandomAccess) {
            int size = list.size();
            int i3 = 0;
            for (int i4 = 0; i4 < size; i4++) {
                Integer num = (Integer) list.get(i4);
                int iIntValue = num.intValue();
                if (enumLiteMap.findValueByNumber(iIntValue) != null) {
                    if (i4 != i3) {
                        list.set(i3, num);
                    }
                    i3++;
                } else {
                    obj2 = G(obj, i2, iIntValue, obj2, unknownFieldSchema);
                }
            }
            if (i3 != size) {
                list.subList(i3, size).clear();
            }
        } else {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                int iIntValue2 = ((Integer) it.next()).intValue();
                if (enumLiteMap.findValueByNumber(iIntValue2) == null) {
                    obj2 = G(obj, i2, iIntValue2, obj2, unknownFieldSchema);
                    it.remove();
                }
            }
        }
        return obj2;
    }

    public static boolean shouldUseTableSwitch(FieldInfo[] fieldInfoArr) {
        if (fieldInfoArr.length == 0) {
            return false;
        }
        return shouldUseTableSwitch(fieldInfoArr[0].getFieldNumber(), fieldInfoArr[fieldInfoArr.length - 1].getFieldNumber(), fieldInfoArr.length);
    }

    public static void writeGroupList(int i2, List<?> list, Writer writer, Schema schema) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        writer.writeGroupList(i2, list, schema);
    }

    public static void writeMessageList(int i2, List<?> list, Writer writer, Schema schema) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        writer.writeMessageList(i2, list, schema);
    }
}
