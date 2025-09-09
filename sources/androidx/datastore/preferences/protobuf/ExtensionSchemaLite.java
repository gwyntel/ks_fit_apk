package androidx.datastore.preferences.protobuf;

import androidx.datastore.preferences.protobuf.GeneratedMessageLite;
import androidx.datastore.preferences.protobuf.WireFormat;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
final class ExtensionSchemaLite extends ExtensionSchema<GeneratedMessageLite.ExtensionDescriptor> {

    /* renamed from: androidx.datastore.preferences.protobuf.ExtensionSchemaLite$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f3918a;

        static {
            int[] iArr = new int[WireFormat.FieldType.values().length];
            f3918a = iArr;
            try {
                iArr[WireFormat.FieldType.DOUBLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f3918a[WireFormat.FieldType.FLOAT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f3918a[WireFormat.FieldType.INT64.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f3918a[WireFormat.FieldType.UINT64.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f3918a[WireFormat.FieldType.INT32.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f3918a[WireFormat.FieldType.FIXED64.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f3918a[WireFormat.FieldType.FIXED32.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f3918a[WireFormat.FieldType.BOOL.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                f3918a[WireFormat.FieldType.UINT32.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                f3918a[WireFormat.FieldType.SFIXED32.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                f3918a[WireFormat.FieldType.SFIXED64.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                f3918a[WireFormat.FieldType.SINT32.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                f3918a[WireFormat.FieldType.SINT64.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                f3918a[WireFormat.FieldType.ENUM.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                f3918a[WireFormat.FieldType.BYTES.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                f3918a[WireFormat.FieldType.STRING.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                f3918a[WireFormat.FieldType.GROUP.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                f3918a[WireFormat.FieldType.MESSAGE.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
        }
    }

    ExtensionSchemaLite() {
    }

    @Override // androidx.datastore.preferences.protobuf.ExtensionSchema
    int a(Map.Entry entry) {
        return ((GeneratedMessageLite.ExtensionDescriptor) entry.getKey()).getNumber();
    }

    @Override // androidx.datastore.preferences.protobuf.ExtensionSchema
    Object b(ExtensionRegistryLite extensionRegistryLite, MessageLite messageLite, int i2) {
        return extensionRegistryLite.findLiteExtensionByNumber(messageLite, i2);
    }

    @Override // androidx.datastore.preferences.protobuf.ExtensionSchema
    FieldSet c(Object obj) {
        return ((GeneratedMessageLite.ExtendableMessage) obj).extensions;
    }

    @Override // androidx.datastore.preferences.protobuf.ExtensionSchema
    FieldSet d(Object obj) {
        return ((GeneratedMessageLite.ExtendableMessage) obj).M();
    }

    @Override // androidx.datastore.preferences.protobuf.ExtensionSchema
    boolean e(MessageLite messageLite) {
        return messageLite instanceof GeneratedMessageLite.ExtendableMessage;
    }

    @Override // androidx.datastore.preferences.protobuf.ExtensionSchema
    void f(Object obj) {
        c(obj).makeImmutable();
    }

    @Override // androidx.datastore.preferences.protobuf.ExtensionSchema
    Object g(Reader reader, Object obj, ExtensionRegistryLite extensionRegistryLite, FieldSet fieldSet, Object obj2, UnknownFieldSchema unknownFieldSchema) throws IOException {
        Object objValueOf;
        Object field;
        ArrayList arrayList;
        GeneratedMessageLite.GeneratedExtension generatedExtension = (GeneratedMessageLite.GeneratedExtension) obj;
        int number = generatedExtension.getNumber();
        if (generatedExtension.f3941d.isRepeated() && generatedExtension.f3941d.isPacked()) {
            switch (AnonymousClass1.f3918a[generatedExtension.getLiteType().ordinal()]) {
                case 1:
                    arrayList = new ArrayList();
                    reader.readDoubleList(arrayList);
                    break;
                case 2:
                    arrayList = new ArrayList();
                    reader.readFloatList(arrayList);
                    break;
                case 3:
                    arrayList = new ArrayList();
                    reader.readInt64List(arrayList);
                    break;
                case 4:
                    arrayList = new ArrayList();
                    reader.readUInt64List(arrayList);
                    break;
                case 5:
                    arrayList = new ArrayList();
                    reader.readInt32List(arrayList);
                    break;
                case 6:
                    arrayList = new ArrayList();
                    reader.readFixed64List(arrayList);
                    break;
                case 7:
                    arrayList = new ArrayList();
                    reader.readFixed32List(arrayList);
                    break;
                case 8:
                    arrayList = new ArrayList();
                    reader.readBoolList(arrayList);
                    break;
                case 9:
                    arrayList = new ArrayList();
                    reader.readUInt32List(arrayList);
                    break;
                case 10:
                    arrayList = new ArrayList();
                    reader.readSFixed32List(arrayList);
                    break;
                case 11:
                    arrayList = new ArrayList();
                    reader.readSFixed64List(arrayList);
                    break;
                case 12:
                    arrayList = new ArrayList();
                    reader.readSInt32List(arrayList);
                    break;
                case 13:
                    arrayList = new ArrayList();
                    reader.readSInt64List(arrayList);
                    break;
                case 14:
                    arrayList = new ArrayList();
                    reader.readEnumList(arrayList);
                    obj2 = SchemaUtil.z(number, arrayList, generatedExtension.f3941d.getEnumType(), obj2, unknownFieldSchema);
                    break;
                default:
                    throw new IllegalStateException("Type cannot be packed: " + generatedExtension.f3941d.getLiteType());
            }
            fieldSet.setField(generatedExtension.f3941d, arrayList);
        } else {
            if (generatedExtension.getLiteType() != WireFormat.FieldType.ENUM) {
                switch (AnonymousClass1.f3918a[generatedExtension.getLiteType().ordinal()]) {
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
                        objValueOf = reader.readGroup(generatedExtension.getMessageDefaultInstance().getClass(), extensionRegistryLite);
                        break;
                    case 18:
                        objValueOf = reader.readMessage(generatedExtension.getMessageDefaultInstance().getClass(), extensionRegistryLite);
                        break;
                    default:
                        objValueOf = null;
                        break;
                }
            } else {
                int int32 = reader.readInt32();
                if (generatedExtension.f3941d.getEnumType().findValueByNumber(int32) == null) {
                    return SchemaUtil.F(number, int32, obj2, unknownFieldSchema);
                }
                objValueOf = Integer.valueOf(int32);
            }
            if (generatedExtension.isRepeated()) {
                fieldSet.addRepeatedField(generatedExtension.f3941d, objValueOf);
            } else {
                int i2 = AnonymousClass1.f3918a[generatedExtension.getLiteType().ordinal()];
                if ((i2 == 17 || i2 == 18) && (field = fieldSet.getField(generatedExtension.f3941d)) != null) {
                    objValueOf = Internal.d(field, objValueOf);
                }
                fieldSet.setField(generatedExtension.f3941d, objValueOf);
            }
        }
        return obj2;
    }

    @Override // androidx.datastore.preferences.protobuf.ExtensionSchema
    void h(Reader reader, Object obj, ExtensionRegistryLite extensionRegistryLite, FieldSet fieldSet) throws IOException {
        GeneratedMessageLite.GeneratedExtension generatedExtension = (GeneratedMessageLite.GeneratedExtension) obj;
        fieldSet.setField(generatedExtension.f3941d, reader.readMessage(generatedExtension.getMessageDefaultInstance().getClass(), extensionRegistryLite));
    }

    @Override // androidx.datastore.preferences.protobuf.ExtensionSchema
    void i(ByteString byteString, Object obj, ExtensionRegistryLite extensionRegistryLite, FieldSet fieldSet) throws IOException {
        GeneratedMessageLite.GeneratedExtension generatedExtension = (GeneratedMessageLite.GeneratedExtension) obj;
        MessageLite messageLiteBuildPartial = generatedExtension.getMessageDefaultInstance().newBuilderForType().buildPartial();
        BinaryReader binaryReaderNewInstance = BinaryReader.newInstance(ByteBuffer.wrap(byteString.toByteArray()), true);
        Protobuf.getInstance().mergeFrom(messageLiteBuildPartial, binaryReaderNewInstance, extensionRegistryLite);
        fieldSet.setField(generatedExtension.f3941d, messageLiteBuildPartial);
        if (binaryReaderNewInstance.getFieldNumber() != Integer.MAX_VALUE) {
            throw InvalidProtocolBufferException.invalidEndTag();
        }
    }

    @Override // androidx.datastore.preferences.protobuf.ExtensionSchema
    void j(Writer writer, Map.Entry entry) throws IOException {
        GeneratedMessageLite.ExtensionDescriptor extensionDescriptor = (GeneratedMessageLite.ExtensionDescriptor) entry.getKey();
        if (!extensionDescriptor.isRepeated()) {
            switch (AnonymousClass1.f3918a[extensionDescriptor.getLiteType().ordinal()]) {
                case 1:
                    writer.writeDouble(extensionDescriptor.getNumber(), ((Double) entry.getValue()).doubleValue());
                    break;
                case 2:
                    writer.writeFloat(extensionDescriptor.getNumber(), ((Float) entry.getValue()).floatValue());
                    break;
                case 3:
                    writer.writeInt64(extensionDescriptor.getNumber(), ((Long) entry.getValue()).longValue());
                    break;
                case 4:
                    writer.writeUInt64(extensionDescriptor.getNumber(), ((Long) entry.getValue()).longValue());
                    break;
                case 5:
                    writer.writeInt32(extensionDescriptor.getNumber(), ((Integer) entry.getValue()).intValue());
                    break;
                case 6:
                    writer.writeFixed64(extensionDescriptor.getNumber(), ((Long) entry.getValue()).longValue());
                    break;
                case 7:
                    writer.writeFixed32(extensionDescriptor.getNumber(), ((Integer) entry.getValue()).intValue());
                    break;
                case 8:
                    writer.writeBool(extensionDescriptor.getNumber(), ((Boolean) entry.getValue()).booleanValue());
                    break;
                case 9:
                    writer.writeUInt32(extensionDescriptor.getNumber(), ((Integer) entry.getValue()).intValue());
                    break;
                case 10:
                    writer.writeSFixed32(extensionDescriptor.getNumber(), ((Integer) entry.getValue()).intValue());
                    break;
                case 11:
                    writer.writeSFixed64(extensionDescriptor.getNumber(), ((Long) entry.getValue()).longValue());
                    break;
                case 12:
                    writer.writeSInt32(extensionDescriptor.getNumber(), ((Integer) entry.getValue()).intValue());
                    break;
                case 13:
                    writer.writeSInt64(extensionDescriptor.getNumber(), ((Long) entry.getValue()).longValue());
                    break;
                case 14:
                    writer.writeInt32(extensionDescriptor.getNumber(), ((Integer) entry.getValue()).intValue());
                    break;
                case 15:
                    writer.writeBytes(extensionDescriptor.getNumber(), (ByteString) entry.getValue());
                    break;
                case 16:
                    writer.writeString(extensionDescriptor.getNumber(), (String) entry.getValue());
                    break;
                case 17:
                    writer.writeGroup(extensionDescriptor.getNumber(), entry.getValue(), Protobuf.getInstance().schemaFor((Class) entry.getValue().getClass()));
                    break;
                case 18:
                    writer.writeMessage(extensionDescriptor.getNumber(), entry.getValue(), Protobuf.getInstance().schemaFor((Class) entry.getValue().getClass()));
                    break;
            }
        }
        switch (AnonymousClass1.f3918a[extensionDescriptor.getLiteType().ordinal()]) {
            case 1:
                SchemaUtil.writeDoubleList(extensionDescriptor.getNumber(), (List) entry.getValue(), writer, extensionDescriptor.isPacked());
                break;
            case 2:
                SchemaUtil.writeFloatList(extensionDescriptor.getNumber(), (List) entry.getValue(), writer, extensionDescriptor.isPacked());
                break;
            case 3:
                SchemaUtil.writeInt64List(extensionDescriptor.getNumber(), (List) entry.getValue(), writer, extensionDescriptor.isPacked());
                break;
            case 4:
                SchemaUtil.writeUInt64List(extensionDescriptor.getNumber(), (List) entry.getValue(), writer, extensionDescriptor.isPacked());
                break;
            case 5:
                SchemaUtil.writeInt32List(extensionDescriptor.getNumber(), (List) entry.getValue(), writer, extensionDescriptor.isPacked());
                break;
            case 6:
                SchemaUtil.writeFixed64List(extensionDescriptor.getNumber(), (List) entry.getValue(), writer, extensionDescriptor.isPacked());
                break;
            case 7:
                SchemaUtil.writeFixed32List(extensionDescriptor.getNumber(), (List) entry.getValue(), writer, extensionDescriptor.isPacked());
                break;
            case 8:
                SchemaUtil.writeBoolList(extensionDescriptor.getNumber(), (List) entry.getValue(), writer, extensionDescriptor.isPacked());
                break;
            case 9:
                SchemaUtil.writeUInt32List(extensionDescriptor.getNumber(), (List) entry.getValue(), writer, extensionDescriptor.isPacked());
                break;
            case 10:
                SchemaUtil.writeSFixed32List(extensionDescriptor.getNumber(), (List) entry.getValue(), writer, extensionDescriptor.isPacked());
                break;
            case 11:
                SchemaUtil.writeSFixed64List(extensionDescriptor.getNumber(), (List) entry.getValue(), writer, extensionDescriptor.isPacked());
                break;
            case 12:
                SchemaUtil.writeSInt32List(extensionDescriptor.getNumber(), (List) entry.getValue(), writer, extensionDescriptor.isPacked());
                break;
            case 13:
                SchemaUtil.writeSInt64List(extensionDescriptor.getNumber(), (List) entry.getValue(), writer, extensionDescriptor.isPacked());
                break;
            case 14:
                SchemaUtil.writeInt32List(extensionDescriptor.getNumber(), (List) entry.getValue(), writer, extensionDescriptor.isPacked());
                break;
            case 15:
                SchemaUtil.writeBytesList(extensionDescriptor.getNumber(), (List) entry.getValue(), writer);
                break;
            case 16:
                SchemaUtil.writeStringList(extensionDescriptor.getNumber(), (List) entry.getValue(), writer);
                break;
            case 17:
                List list = (List) entry.getValue();
                if (list != null && !list.isEmpty()) {
                    SchemaUtil.writeGroupList(extensionDescriptor.getNumber(), (List) entry.getValue(), writer, Protobuf.getInstance().schemaFor((Class) list.get(0).getClass()));
                    break;
                }
                break;
            case 18:
                List list2 = (List) entry.getValue();
                if (list2 != null && !list2.isEmpty()) {
                    SchemaUtil.writeMessageList(extensionDescriptor.getNumber(), (List) entry.getValue(), writer, Protobuf.getInstance().schemaFor((Class) list2.get(0).getClass()));
                    break;
                }
                break;
        }
    }
}
