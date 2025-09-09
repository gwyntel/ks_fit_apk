package com.google.protobuf;

import com.google.protobuf.Descriptors;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.WireFormat;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
final class ExtensionSchemaFull extends ExtensionSchema<Descriptors.FieldDescriptor> {
    private static final long EXTENSION_FIELD_OFFSET = getExtensionsFieldOffset();

    /* renamed from: a, reason: collision with root package name */
    public static final /* synthetic */ int f15224a = 0;

    /* renamed from: com.google.protobuf.ExtensionSchemaFull$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f15225a;

        static {
            int[] iArr = new int[WireFormat.FieldType.values().length];
            f15225a = iArr;
            try {
                iArr[WireFormat.FieldType.DOUBLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f15225a[WireFormat.FieldType.FLOAT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f15225a[WireFormat.FieldType.INT64.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f15225a[WireFormat.FieldType.UINT64.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f15225a[WireFormat.FieldType.INT32.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f15225a[WireFormat.FieldType.FIXED64.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f15225a[WireFormat.FieldType.FIXED32.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f15225a[WireFormat.FieldType.BOOL.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                f15225a[WireFormat.FieldType.UINT32.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                f15225a[WireFormat.FieldType.SFIXED32.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                f15225a[WireFormat.FieldType.SFIXED64.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                f15225a[WireFormat.FieldType.SINT32.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                f15225a[WireFormat.FieldType.SINT64.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                f15225a[WireFormat.FieldType.ENUM.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                f15225a[WireFormat.FieldType.BYTES.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                f15225a[WireFormat.FieldType.STRING.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                f15225a[WireFormat.FieldType.GROUP.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                f15225a[WireFormat.FieldType.MESSAGE.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
        }
    }

    ExtensionSchemaFull() {
    }

    private static <T> long getExtensionsFieldOffset() {
        return UnsafeUtil.C(GeneratedMessageV3.ExtendableMessage.class.getDeclaredField("extensions"));
    }

    @Override // com.google.protobuf.ExtensionSchema
    int a(Map.Entry entry) {
        return ((Descriptors.FieldDescriptor) entry.getKey()).getNumber();
    }

    @Override // com.google.protobuf.ExtensionSchema
    Object b(ExtensionRegistryLite extensionRegistryLite, MessageLite messageLite, int i2) {
        return ((ExtensionRegistry) extensionRegistryLite).findImmutableExtensionByNumber(((Message) messageLite).getDescriptorForType(), i2);
    }

    @Override // com.google.protobuf.ExtensionSchema
    FieldSet c(Object obj) {
        FieldSet<Descriptors.FieldDescriptor> extensions = getExtensions(obj);
        if (!extensions.isImmutable()) {
            return extensions;
        }
        FieldSet fieldSetM66clone = extensions.m66clone();
        j(obj, fieldSetM66clone);
        return fieldSetM66clone;
    }

    @Override // com.google.protobuf.ExtensionSchema
    boolean d(MessageLite messageLite) {
        return messageLite instanceof GeneratedMessageV3.ExtendableMessage;
    }

    @Override // com.google.protobuf.ExtensionSchema
    void e(Object obj) {
        getExtensions(obj).makeImmutable();
    }

    @Override // com.google.protobuf.ExtensionSchema
    Object f(Object obj, Reader reader, Object obj2, ExtensionRegistryLite extensionRegistryLite, FieldSet fieldSet, Object obj3, UnknownFieldSchema unknownFieldSchema) throws IOException {
        Object objValueOf;
        Object field;
        ArrayList arrayList;
        ArrayList arrayList2;
        ExtensionRegistry.ExtensionInfo extensionInfo = (ExtensionRegistry.ExtensionInfo) obj2;
        int number = extensionInfo.descriptor.getNumber();
        if (extensionInfo.descriptor.isRepeated() && extensionInfo.descriptor.isPacked()) {
            switch (AnonymousClass1.f15225a[extensionInfo.descriptor.getLiteType().ordinal()]) {
                case 1:
                    arrayList = new ArrayList();
                    reader.readDoubleList(arrayList);
                    arrayList2 = arrayList;
                    fieldSet.setField(extensionInfo.descriptor, arrayList2);
                    break;
                case 2:
                    arrayList = new ArrayList();
                    reader.readFloatList(arrayList);
                    arrayList2 = arrayList;
                    fieldSet.setField(extensionInfo.descriptor, arrayList2);
                    break;
                case 3:
                    arrayList = new ArrayList();
                    reader.readInt64List(arrayList);
                    arrayList2 = arrayList;
                    fieldSet.setField(extensionInfo.descriptor, arrayList2);
                    break;
                case 4:
                    arrayList = new ArrayList();
                    reader.readUInt64List(arrayList);
                    arrayList2 = arrayList;
                    fieldSet.setField(extensionInfo.descriptor, arrayList2);
                    break;
                case 5:
                    arrayList = new ArrayList();
                    reader.readInt32List(arrayList);
                    arrayList2 = arrayList;
                    fieldSet.setField(extensionInfo.descriptor, arrayList2);
                    break;
                case 6:
                    arrayList = new ArrayList();
                    reader.readFixed64List(arrayList);
                    arrayList2 = arrayList;
                    fieldSet.setField(extensionInfo.descriptor, arrayList2);
                    break;
                case 7:
                    arrayList = new ArrayList();
                    reader.readFixed32List(arrayList);
                    arrayList2 = arrayList;
                    fieldSet.setField(extensionInfo.descriptor, arrayList2);
                    break;
                case 8:
                    arrayList = new ArrayList();
                    reader.readBoolList(arrayList);
                    arrayList2 = arrayList;
                    fieldSet.setField(extensionInfo.descriptor, arrayList2);
                    break;
                case 9:
                    arrayList = new ArrayList();
                    reader.readUInt32List(arrayList);
                    arrayList2 = arrayList;
                    fieldSet.setField(extensionInfo.descriptor, arrayList2);
                    break;
                case 10:
                    arrayList = new ArrayList();
                    reader.readSFixed32List(arrayList);
                    arrayList2 = arrayList;
                    fieldSet.setField(extensionInfo.descriptor, arrayList2);
                    break;
                case 11:
                    arrayList = new ArrayList();
                    reader.readSFixed64List(arrayList);
                    arrayList2 = arrayList;
                    fieldSet.setField(extensionInfo.descriptor, arrayList2);
                    break;
                case 12:
                    arrayList = new ArrayList();
                    reader.readSInt32List(arrayList);
                    arrayList2 = arrayList;
                    fieldSet.setField(extensionInfo.descriptor, arrayList2);
                    break;
                case 13:
                    arrayList = new ArrayList();
                    reader.readSInt64List(arrayList);
                    arrayList2 = arrayList;
                    fieldSet.setField(extensionInfo.descriptor, arrayList2);
                    break;
                case 14:
                    ArrayList arrayList3 = new ArrayList();
                    reader.readEnumList(arrayList3);
                    arrayList2 = new ArrayList();
                    Iterator<Integer> it = arrayList3.iterator();
                    while (it.hasNext()) {
                        int iIntValue = it.next().intValue();
                        Descriptors.EnumValueDescriptor enumValueDescriptorFindValueByNumber = extensionInfo.descriptor.getEnumType().findValueByNumber(iIntValue);
                        if (enumValueDescriptorFindValueByNumber != null) {
                            arrayList2.add(enumValueDescriptorFindValueByNumber);
                        } else {
                            obj3 = SchemaUtil.G(obj, number, iIntValue, obj3, unknownFieldSchema);
                        }
                    }
                    fieldSet.setField(extensionInfo.descriptor, arrayList2);
                    break;
                default:
                    throw new IllegalStateException("Type cannot be packed: " + extensionInfo.descriptor.getLiteType());
            }
        } else {
            if (extensionInfo.descriptor.getLiteType() != WireFormat.FieldType.ENUM) {
                switch (AnonymousClass1.f15225a[extensionInfo.descriptor.getLiteType().ordinal()]) {
                    case 1:
                        objValueOf = Double.valueOf(reader.readDouble());
                        break;
                    case 2:
                        objValueOf = Float.valueOf(reader.readFloat());
                        break;
                    case 3:
                        objValueOf = Long.valueOf(reader.readInt64());
                        break;
                    case 4:
                        objValueOf = Long.valueOf(reader.readUInt64());
                        break;
                    case 5:
                        objValueOf = Integer.valueOf(reader.readInt32());
                        break;
                    case 6:
                        objValueOf = Long.valueOf(reader.readFixed64());
                        break;
                    case 7:
                        objValueOf = Integer.valueOf(reader.readFixed32());
                        break;
                    case 8:
                        objValueOf = Boolean.valueOf(reader.readBool());
                        break;
                    case 9:
                        objValueOf = Integer.valueOf(reader.readUInt32());
                        break;
                    case 10:
                        objValueOf = Integer.valueOf(reader.readSFixed32());
                        break;
                    case 11:
                        objValueOf = Long.valueOf(reader.readSFixed64());
                        break;
                    case 12:
                        objValueOf = Integer.valueOf(reader.readSInt32());
                        break;
                    case 13:
                        objValueOf = Long.valueOf(reader.readSInt64());
                        break;
                    case 14:
                        throw new IllegalStateException("Shouldn't reach here.");
                    case 15:
                        objValueOf = reader.readBytes();
                        break;
                    case 16:
                        objValueOf = reader.readString();
                        break;
                    case 17:
                        objValueOf = reader.readGroup(extensionInfo.defaultInstance.getClass(), extensionRegistryLite);
                        break;
                    case 18:
                        objValueOf = reader.readMessage(extensionInfo.defaultInstance.getClass(), extensionRegistryLite);
                        break;
                    default:
                        objValueOf = null;
                        break;
                }
            } else {
                int int32 = reader.readInt32();
                objValueOf = extensionInfo.descriptor.getEnumType().findValueByNumber(int32);
                if (objValueOf == null) {
                    return SchemaUtil.G(obj, number, int32, obj3, unknownFieldSchema);
                }
            }
            if (extensionInfo.descriptor.isRepeated()) {
                fieldSet.addRepeatedField(extensionInfo.descriptor, objValueOf);
            } else {
                int i2 = AnonymousClass1.f15225a[extensionInfo.descriptor.getLiteType().ordinal()];
                if ((i2 == 17 || i2 == 18) && (field = fieldSet.getField(extensionInfo.descriptor)) != null) {
                    objValueOf = Internal.d(field, objValueOf);
                }
                fieldSet.setField(extensionInfo.descriptor, objValueOf);
            }
        }
        return obj3;
    }

    @Override // com.google.protobuf.ExtensionSchema
    void g(Reader reader, Object obj, ExtensionRegistryLite extensionRegistryLite, FieldSet fieldSet) throws IOException {
        ExtensionRegistry.ExtensionInfo extensionInfo = (ExtensionRegistry.ExtensionInfo) obj;
        if (!ExtensionRegistryLite.isEagerlyParseMessageSets()) {
            fieldSet.setField(extensionInfo.descriptor, new LazyField(extensionInfo.defaultInstance, extensionRegistryLite, reader.readBytes()));
        } else {
            fieldSet.setField(extensionInfo.descriptor, reader.readMessage(extensionInfo.defaultInstance.getClass(), extensionRegistryLite));
        }
    }

    @Override // com.google.protobuf.ExtensionSchema
    public FieldSet<Descriptors.FieldDescriptor> getExtensions(Object obj) {
        return (FieldSet) UnsafeUtil.x(obj, EXTENSION_FIELD_OFFSET);
    }

    @Override // com.google.protobuf.ExtensionSchema
    void h(ByteString byteString, Object obj, ExtensionRegistryLite extensionRegistryLite, FieldSet fieldSet) throws IOException {
        ExtensionRegistry.ExtensionInfo extensionInfo = (ExtensionRegistry.ExtensionInfo) obj;
        Message messageBuildPartial = extensionInfo.defaultInstance.newBuilderForType().buildPartial();
        if (!ExtensionRegistryLite.isEagerlyParseMessageSets()) {
            fieldSet.setField(extensionInfo.descriptor, new LazyField(extensionInfo.defaultInstance, extensionRegistryLite, byteString));
            return;
        }
        BinaryReader binaryReaderNewInstance = BinaryReader.newInstance(ByteBuffer.wrap(byteString.toByteArray()), true);
        Protobuf.getInstance().mergeFrom(messageBuildPartial, binaryReaderNewInstance, extensionRegistryLite);
        fieldSet.setField(extensionInfo.descriptor, messageBuildPartial);
        if (binaryReaderNewInstance.getFieldNumber() != Integer.MAX_VALUE) {
            throw InvalidProtocolBufferException.invalidEndTag();
        }
    }

    @Override // com.google.protobuf.ExtensionSchema
    void i(Writer writer, Map.Entry entry) throws IOException {
        Descriptors.FieldDescriptor fieldDescriptor = (Descriptors.FieldDescriptor) entry.getKey();
        if (!fieldDescriptor.isRepeated()) {
            switch (AnonymousClass1.f15225a[fieldDescriptor.getLiteType().ordinal()]) {
                case 1:
                    writer.writeDouble(fieldDescriptor.getNumber(), ((Double) entry.getValue()).doubleValue());
                    break;
                case 2:
                    writer.writeFloat(fieldDescriptor.getNumber(), ((Float) entry.getValue()).floatValue());
                    break;
                case 3:
                    writer.writeInt64(fieldDescriptor.getNumber(), ((Long) entry.getValue()).longValue());
                    break;
                case 4:
                    writer.writeUInt64(fieldDescriptor.getNumber(), ((Long) entry.getValue()).longValue());
                    break;
                case 5:
                    writer.writeInt32(fieldDescriptor.getNumber(), ((Integer) entry.getValue()).intValue());
                    break;
                case 6:
                    writer.writeFixed64(fieldDescriptor.getNumber(), ((Long) entry.getValue()).longValue());
                    break;
                case 7:
                    writer.writeFixed32(fieldDescriptor.getNumber(), ((Integer) entry.getValue()).intValue());
                    break;
                case 8:
                    writer.writeBool(fieldDescriptor.getNumber(), ((Boolean) entry.getValue()).booleanValue());
                    break;
                case 9:
                    writer.writeUInt32(fieldDescriptor.getNumber(), ((Integer) entry.getValue()).intValue());
                    break;
                case 10:
                    writer.writeSFixed32(fieldDescriptor.getNumber(), ((Integer) entry.getValue()).intValue());
                    break;
                case 11:
                    writer.writeSFixed64(fieldDescriptor.getNumber(), ((Long) entry.getValue()).longValue());
                    break;
                case 12:
                    writer.writeSInt32(fieldDescriptor.getNumber(), ((Integer) entry.getValue()).intValue());
                    break;
                case 13:
                    writer.writeSInt64(fieldDescriptor.getNumber(), ((Long) entry.getValue()).longValue());
                    break;
                case 14:
                    writer.writeInt32(fieldDescriptor.getNumber(), ((Descriptors.EnumValueDescriptor) entry.getValue()).getNumber());
                    break;
                case 15:
                    writer.writeBytes(fieldDescriptor.getNumber(), (ByteString) entry.getValue());
                    break;
                case 16:
                    writer.writeString(fieldDescriptor.getNumber(), (String) entry.getValue());
                    break;
                case 17:
                    writer.writeGroup(fieldDescriptor.getNumber(), entry.getValue());
                    break;
                case 18:
                    writer.writeMessage(fieldDescriptor.getNumber(), entry.getValue());
                    break;
            }
        }
        switch (AnonymousClass1.f15225a[fieldDescriptor.getLiteType().ordinal()]) {
            case 1:
                SchemaUtil.writeDoubleList(fieldDescriptor.getNumber(), (List) entry.getValue(), writer, fieldDescriptor.isPacked());
                break;
            case 2:
                SchemaUtil.writeFloatList(fieldDescriptor.getNumber(), (List) entry.getValue(), writer, fieldDescriptor.isPacked());
                break;
            case 3:
                SchemaUtil.writeInt64List(fieldDescriptor.getNumber(), (List) entry.getValue(), writer, fieldDescriptor.isPacked());
                break;
            case 4:
                SchemaUtil.writeUInt64List(fieldDescriptor.getNumber(), (List) entry.getValue(), writer, fieldDescriptor.isPacked());
                break;
            case 5:
                SchemaUtil.writeInt32List(fieldDescriptor.getNumber(), (List) entry.getValue(), writer, fieldDescriptor.isPacked());
                break;
            case 6:
                SchemaUtil.writeFixed64List(fieldDescriptor.getNumber(), (List) entry.getValue(), writer, fieldDescriptor.isPacked());
                break;
            case 7:
                SchemaUtil.writeFixed32List(fieldDescriptor.getNumber(), (List) entry.getValue(), writer, fieldDescriptor.isPacked());
                break;
            case 8:
                SchemaUtil.writeBoolList(fieldDescriptor.getNumber(), (List) entry.getValue(), writer, fieldDescriptor.isPacked());
                break;
            case 9:
                SchemaUtil.writeUInt32List(fieldDescriptor.getNumber(), (List) entry.getValue(), writer, fieldDescriptor.isPacked());
                break;
            case 10:
                SchemaUtil.writeSFixed32List(fieldDescriptor.getNumber(), (List) entry.getValue(), writer, fieldDescriptor.isPacked());
                break;
            case 11:
                SchemaUtil.writeSFixed64List(fieldDescriptor.getNumber(), (List) entry.getValue(), writer, fieldDescriptor.isPacked());
                break;
            case 12:
                SchemaUtil.writeSInt32List(fieldDescriptor.getNumber(), (List) entry.getValue(), writer, fieldDescriptor.isPacked());
                break;
            case 13:
                SchemaUtil.writeSInt64List(fieldDescriptor.getNumber(), (List) entry.getValue(), writer, fieldDescriptor.isPacked());
                break;
            case 14:
                List list = (List) entry.getValue();
                ArrayList arrayList = new ArrayList();
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    arrayList.add(Integer.valueOf(((Descriptors.EnumValueDescriptor) it.next()).getNumber()));
                }
                SchemaUtil.writeInt32List(fieldDescriptor.getNumber(), arrayList, writer, fieldDescriptor.isPacked());
                break;
            case 15:
                SchemaUtil.writeBytesList(fieldDescriptor.getNumber(), (List) entry.getValue(), writer);
                break;
            case 16:
                SchemaUtil.writeStringList(fieldDescriptor.getNumber(), (List) entry.getValue(), writer);
                break;
            case 17:
                SchemaUtil.writeGroupList(fieldDescriptor.getNumber(), (List) entry.getValue(), writer);
                break;
            case 18:
                SchemaUtil.writeMessageList(fieldDescriptor.getNumber(), (List) entry.getValue(), writer);
                break;
        }
    }

    void j(Object obj, FieldSet fieldSet) {
        UnsafeUtil.K(obj, EXTENSION_FIELD_OFFSET, fieldSet);
    }
}
