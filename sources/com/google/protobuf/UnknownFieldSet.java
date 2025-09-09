package com.google.protobuf;

import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.ByteString;
import com.google.protobuf.MessageLite;
import com.google.protobuf.Writer;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.TreeMap;

/* loaded from: classes2.dex */
public final class UnknownFieldSet implements MessageLite {
    private final TreeMap<Integer, Field> fields;
    private static final UnknownFieldSet defaultInstance = new UnknownFieldSet(new TreeMap());
    private static final Parser PARSER = new Parser();

    public static final class Builder implements MessageLite.Builder {
        private TreeMap<Integer, Field.Builder> fieldBuilders = new TreeMap<>();

        private Builder() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static Builder create() {
            return new Builder();
        }

        private Field.Builder getFieldBuilder(int i2) {
            if (i2 == 0) {
                return null;
            }
            Field.Builder builder = this.fieldBuilders.get(Integer.valueOf(i2));
            if (builder != null) {
                return builder;
            }
            Field.Builder builderNewBuilder = Field.newBuilder();
            this.fieldBuilders.put(Integer.valueOf(i2), builderNewBuilder);
            return builderNewBuilder;
        }

        public Builder addField(int i2, Field field) {
            if (i2 > 0) {
                this.fieldBuilders.put(Integer.valueOf(i2), Field.newBuilder(field));
                return this;
            }
            throw new IllegalArgumentException(i2 + " is not a valid field number.");
        }

        public Map<Integer, Field> asMap() {
            TreeMap treeMap = new TreeMap();
            for (Map.Entry<Integer, Field.Builder> entry : this.fieldBuilders.entrySet()) {
                treeMap.put(entry.getKey(), entry.getValue().build());
            }
            return Collections.unmodifiableMap(treeMap);
        }

        public Builder clearField(int i2) {
            if (i2 > 0) {
                if (this.fieldBuilders.containsKey(Integer.valueOf(i2))) {
                    this.fieldBuilders.remove(Integer.valueOf(i2));
                }
                return this;
            }
            throw new IllegalArgumentException(i2 + " is not a valid field number.");
        }

        public boolean hasField(int i2) {
            return this.fieldBuilders.containsKey(Integer.valueOf(i2));
        }

        @Override // com.google.protobuf.MessageLiteOrBuilder
        public boolean isInitialized() {
            return true;
        }

        @Override // com.google.protobuf.MessageLite.Builder
        public boolean mergeDelimitedFrom(InputStream inputStream) throws IOException {
            int i2 = inputStream.read();
            if (i2 == -1) {
                return false;
            }
            mergeFrom((InputStream) new AbstractMessageLite.Builder.LimitedInputStream(inputStream, CodedInputStream.readRawVarint32(i2, inputStream)));
            return true;
        }

        public Builder mergeField(int i2, Field field) {
            if (i2 > 0) {
                if (hasField(i2)) {
                    getFieldBuilder(i2).mergeFrom(field);
                } else {
                    addField(i2, field);
                }
                return this;
            }
            throw new IllegalArgumentException(i2 + " is not a valid field number.");
        }

        public boolean mergeFieldFrom(int i2, CodedInputStream codedInputStream) throws IOException {
            int tagFieldNumber = WireFormat.getTagFieldNumber(i2);
            int tagWireType = WireFormat.getTagWireType(i2);
            if (tagWireType == 0) {
                getFieldBuilder(tagFieldNumber).addVarint(codedInputStream.readInt64());
                return true;
            }
            if (tagWireType == 1) {
                getFieldBuilder(tagFieldNumber).addFixed64(codedInputStream.readFixed64());
                return true;
            }
            if (tagWireType == 2) {
                getFieldBuilder(tagFieldNumber).addLengthDelimited(codedInputStream.readBytes());
                return true;
            }
            if (tagWireType == 3) {
                Builder builderNewBuilder = UnknownFieldSet.newBuilder();
                codedInputStream.readGroup(tagFieldNumber, builderNewBuilder, ExtensionRegistry.getEmptyRegistry());
                getFieldBuilder(tagFieldNumber).addGroup(builderNewBuilder.build());
                return true;
            }
            if (tagWireType == 4) {
                return false;
            }
            if (tagWireType != 5) {
                throw InvalidProtocolBufferException.invalidWireType();
            }
            getFieldBuilder(tagFieldNumber).addFixed32(codedInputStream.readFixed32());
            return true;
        }

        public Builder mergeLengthDelimitedField(int i2, ByteString byteString) {
            if (i2 > 0) {
                getFieldBuilder(i2).addLengthDelimited(byteString);
                return this;
            }
            throw new IllegalArgumentException(i2 + " is not a valid field number.");
        }

        public Builder mergeVarintField(int i2, int i3) {
            if (i2 > 0) {
                getFieldBuilder(i2).addVarint(i3);
                return this;
            }
            throw new IllegalArgumentException(i2 + " is not a valid field number.");
        }

        @Override // com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
        public UnknownFieldSet build() {
            if (this.fieldBuilders.isEmpty()) {
                return UnknownFieldSet.getDefaultInstance();
            }
            TreeMap treeMap = new TreeMap();
            for (Map.Entry<Integer, Field.Builder> entry : this.fieldBuilders.entrySet()) {
                treeMap.put(entry.getKey(), entry.getValue().build());
            }
            return new UnknownFieldSet(treeMap);
        }

        @Override // com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
        public UnknownFieldSet buildPartial() {
            return build();
        }

        @Override // com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
        public Builder clear() {
            this.fieldBuilders = new TreeMap<>();
            return this;
        }

        @Override // com.google.protobuf.MessageLiteOrBuilder, com.google.protobuf.MessageOrBuilder
        public UnknownFieldSet getDefaultInstanceForType() {
            return UnknownFieldSet.getDefaultInstance();
        }

        @Override // com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public Builder m67clone() {
            Builder builderNewBuilder = UnknownFieldSet.newBuilder();
            for (Map.Entry<Integer, Field.Builder> entry : this.fieldBuilders.entrySet()) {
                builderNewBuilder.fieldBuilders.put(entry.getKey(), entry.getValue().m68clone());
            }
            return builderNewBuilder;
        }

        @Override // com.google.protobuf.MessageLite.Builder
        public boolean mergeDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return mergeDelimitedFrom(inputStream);
        }

        public Builder mergeFrom(UnknownFieldSet unknownFieldSet) {
            if (unknownFieldSet != UnknownFieldSet.getDefaultInstance()) {
                for (Map.Entry entry : unknownFieldSet.fields.entrySet()) {
                    mergeField(((Integer) entry.getKey()).intValue(), (Field) entry.getValue());
                }
            }
            return this;
        }

        @Override // com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
        public Builder mergeFrom(CodedInputStream codedInputStream) throws IOException {
            int tag;
            do {
                tag = codedInputStream.readTag();
                if (tag == 0) {
                    break;
                }
            } while (mergeFieldFrom(tag, codedInputStream));
            return this;
        }

        @Override // com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
        public Builder mergeFrom(ByteString byteString) throws InvalidProtocolBufferException {
            try {
                CodedInputStream codedInputStreamNewCodedInput = byteString.newCodedInput();
                mergeFrom(codedInputStreamNewCodedInput);
                codedInputStreamNewCodedInput.checkLastTagWas(0);
                return this;
            } catch (InvalidProtocolBufferException e2) {
                throw e2;
            } catch (IOException e3) {
                throw new RuntimeException("Reading from a ByteString threw an IOException (should never happen).", e3);
            }
        }

        @Override // com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
        public Builder mergeFrom(byte[] bArr) throws InvalidProtocolBufferException {
            try {
                CodedInputStream codedInputStreamNewInstance = CodedInputStream.newInstance(bArr);
                mergeFrom(codedInputStreamNewInstance);
                codedInputStreamNewInstance.checkLastTagWas(0);
                return this;
            } catch (InvalidProtocolBufferException e2) {
                throw e2;
            } catch (IOException e3) {
                throw new RuntimeException("Reading from a byte array threw an IOException (should never happen).", e3);
            }
        }

        @Override // com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
        public Builder mergeFrom(InputStream inputStream) throws IOException {
            CodedInputStream codedInputStreamNewInstance = CodedInputStream.newInstance(inputStream);
            mergeFrom(codedInputStreamNewInstance);
            codedInputStreamNewInstance.checkLastTagWas(0);
            return this;
        }

        @Override // com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
        public Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return mergeFrom(codedInputStream);
        }

        @Override // com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
        public Builder mergeFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return mergeFrom(byteString);
        }

        @Override // com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
        public Builder mergeFrom(byte[] bArr, int i2, int i3) throws InvalidProtocolBufferException {
            try {
                CodedInputStream codedInputStreamNewInstance = CodedInputStream.newInstance(bArr, i2, i3);
                mergeFrom(codedInputStreamNewInstance);
                codedInputStreamNewInstance.checkLastTagWas(0);
                return this;
            } catch (InvalidProtocolBufferException e2) {
                throw e2;
            } catch (IOException e3) {
                throw new RuntimeException("Reading from a byte array threw an IOException (should never happen).", e3);
            }
        }

        @Override // com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
        public Builder mergeFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return mergeFrom(bArr);
        }

        @Override // com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
        public Builder mergeFrom(byte[] bArr, int i2, int i3, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return mergeFrom(bArr, i2, i3);
        }

        @Override // com.google.protobuf.MessageLite.Builder, com.google.protobuf.Message.Builder
        public Builder mergeFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return mergeFrom(inputStream);
        }

        @Override // com.google.protobuf.MessageLite.Builder
        public Builder mergeFrom(MessageLite messageLite) {
            if (messageLite instanceof UnknownFieldSet) {
                return mergeFrom((UnknownFieldSet) messageLite);
            }
            throw new IllegalArgumentException("mergeFrom(MessageLite) can only merge messages of the same type.");
        }
    }

    public static final class Field {
        private static final Field fieldDefaultInstance = newBuilder().build();
        private List<Integer> fixed32;
        private List<Long> fixed64;
        private List<UnknownFieldSet> group;
        private List<ByteString> lengthDelimited;
        private List<Long> varint;

        public static final class Builder {
            private Field result = new Field();

            private Builder() {
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static Builder create() {
                return new Builder();
            }

            public Builder addFixed32(int i2) {
                if (this.result.fixed32 == null) {
                    this.result.fixed32 = new ArrayList();
                }
                this.result.fixed32.add(Integer.valueOf(i2));
                return this;
            }

            public Builder addFixed64(long j2) {
                if (this.result.fixed64 == null) {
                    this.result.fixed64 = new ArrayList();
                }
                this.result.fixed64.add(Long.valueOf(j2));
                return this;
            }

            public Builder addGroup(UnknownFieldSet unknownFieldSet) {
                if (this.result.group == null) {
                    this.result.group = new ArrayList();
                }
                this.result.group.add(unknownFieldSet);
                return this;
            }

            public Builder addLengthDelimited(ByteString byteString) {
                if (this.result.lengthDelimited == null) {
                    this.result.lengthDelimited = new ArrayList();
                }
                this.result.lengthDelimited.add(byteString);
                return this;
            }

            public Builder addVarint(long j2) {
                if (this.result.varint == null) {
                    this.result.varint = new ArrayList();
                }
                this.result.varint.add(Long.valueOf(j2));
                return this;
            }

            public Field build() {
                Field field = new Field();
                if (this.result.varint == null) {
                    field.varint = Collections.emptyList();
                } else {
                    field.varint = Collections.unmodifiableList(new ArrayList(this.result.varint));
                }
                if (this.result.fixed32 == null) {
                    field.fixed32 = Collections.emptyList();
                } else {
                    field.fixed32 = Collections.unmodifiableList(new ArrayList(this.result.fixed32));
                }
                if (this.result.fixed64 == null) {
                    field.fixed64 = Collections.emptyList();
                } else {
                    field.fixed64 = Collections.unmodifiableList(new ArrayList(this.result.fixed64));
                }
                if (this.result.lengthDelimited == null) {
                    field.lengthDelimited = Collections.emptyList();
                } else {
                    field.lengthDelimited = Collections.unmodifiableList(new ArrayList(this.result.lengthDelimited));
                }
                if (this.result.group == null) {
                    field.group = Collections.emptyList();
                } else {
                    field.group = Collections.unmodifiableList(new ArrayList(this.result.group));
                }
                return field;
            }

            public Builder clear() {
                this.result = new Field();
                return this;
            }

            public Builder mergeFrom(Field field) {
                if (!field.varint.isEmpty()) {
                    if (this.result.varint == null) {
                        this.result.varint = new ArrayList();
                    }
                    this.result.varint.addAll(field.varint);
                }
                if (!field.fixed32.isEmpty()) {
                    if (this.result.fixed32 == null) {
                        this.result.fixed32 = new ArrayList();
                    }
                    this.result.fixed32.addAll(field.fixed32);
                }
                if (!field.fixed64.isEmpty()) {
                    if (this.result.fixed64 == null) {
                        this.result.fixed64 = new ArrayList();
                    }
                    this.result.fixed64.addAll(field.fixed64);
                }
                if (!field.lengthDelimited.isEmpty()) {
                    if (this.result.lengthDelimited == null) {
                        this.result.lengthDelimited = new ArrayList();
                    }
                    this.result.lengthDelimited.addAll(field.lengthDelimited);
                }
                if (!field.group.isEmpty()) {
                    if (this.result.group == null) {
                        this.result.group = new ArrayList();
                    }
                    this.result.group.addAll(field.group);
                }
                return this;
            }

            /* renamed from: clone, reason: merged with bridge method [inline-methods] */
            public Builder m68clone() {
                Field field = new Field();
                if (this.result.varint == null) {
                    field.varint = null;
                } else {
                    field.varint = new ArrayList(this.result.varint);
                }
                if (this.result.fixed32 == null) {
                    field.fixed32 = null;
                } else {
                    field.fixed32 = new ArrayList(this.result.fixed32);
                }
                if (this.result.fixed64 == null) {
                    field.fixed64 = null;
                } else {
                    field.fixed64 = new ArrayList(this.result.fixed64);
                }
                if (this.result.lengthDelimited == null) {
                    field.lengthDelimited = null;
                } else {
                    field.lengthDelimited = new ArrayList(this.result.lengthDelimited);
                }
                if (this.result.group == null) {
                    field.group = null;
                } else {
                    field.group = new ArrayList(this.result.group);
                }
                Builder builder = new Builder();
                builder.result = field;
                return builder;
            }
        }

        private Field() {
        }

        public static Field getDefaultInstance() {
            return fieldDefaultInstance;
        }

        private Object[] getIdentityArray() {
            return new Object[]{this.varint, this.fixed32, this.fixed64, this.lengthDelimited, this.group};
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof Field) {
                return Arrays.equals(getIdentityArray(), ((Field) obj).getIdentityArray());
            }
            return false;
        }

        public List<Integer> getFixed32List() {
            return this.fixed32;
        }

        public List<Long> getFixed64List() {
            return this.fixed64;
        }

        public List<UnknownFieldSet> getGroupList() {
            return this.group;
        }

        public List<ByteString> getLengthDelimitedList() {
            return this.lengthDelimited;
        }

        public int getSerializedSize(int i2) {
            Iterator<Long> it = this.varint.iterator();
            int iComputeGroupSize = 0;
            while (it.hasNext()) {
                iComputeGroupSize += CodedOutputStream.computeUInt64Size(i2, it.next().longValue());
            }
            Iterator<Integer> it2 = this.fixed32.iterator();
            while (it2.hasNext()) {
                iComputeGroupSize += CodedOutputStream.computeFixed32Size(i2, it2.next().intValue());
            }
            Iterator<Long> it3 = this.fixed64.iterator();
            while (it3.hasNext()) {
                iComputeGroupSize += CodedOutputStream.computeFixed64Size(i2, it3.next().longValue());
            }
            Iterator<ByteString> it4 = this.lengthDelimited.iterator();
            while (it4.hasNext()) {
                iComputeGroupSize += CodedOutputStream.computeBytesSize(i2, it4.next());
            }
            Iterator<UnknownFieldSet> it5 = this.group.iterator();
            while (it5.hasNext()) {
                iComputeGroupSize += CodedOutputStream.computeGroupSize(i2, it5.next());
            }
            return iComputeGroupSize;
        }

        public int getSerializedSizeAsMessageSetExtension(int i2) {
            Iterator<ByteString> it = this.lengthDelimited.iterator();
            int iComputeRawMessageSetExtensionSize = 0;
            while (it.hasNext()) {
                iComputeRawMessageSetExtensionSize += CodedOutputStream.computeRawMessageSetExtensionSize(i2, it.next());
            }
            return iComputeRawMessageSetExtensionSize;
        }

        public List<Long> getVarintList() {
            return this.varint;
        }

        public int hashCode() {
            return Arrays.hashCode(getIdentityArray());
        }

        void l(int i2, Writer writer) throws IOException {
            writer.writeInt64List(i2, this.varint, false);
            writer.writeFixed32List(i2, this.fixed32, false);
            writer.writeFixed64List(i2, this.fixed64, false);
            writer.writeBytesList(i2, this.lengthDelimited);
            if (writer.fieldOrder() == Writer.FieldOrder.ASCENDING) {
                for (int i3 = 0; i3 < this.group.size(); i3++) {
                    writer.writeStartGroup(i2);
                    this.group.get(i3).c(writer);
                    writer.writeEndGroup(i2);
                }
                return;
            }
            for (int size = this.group.size() - 1; size >= 0; size--) {
                writer.writeEndGroup(i2);
                this.group.get(size).c(writer);
                writer.writeStartGroup(i2);
            }
        }

        public ByteString toByteString(int i2) {
            try {
                ByteString.CodedBuilder codedBuilderNewCodedBuilder = ByteString.newCodedBuilder(getSerializedSize(i2));
                writeTo(i2, codedBuilderNewCodedBuilder.getCodedOutput());
                return codedBuilderNewCodedBuilder.build();
            } catch (IOException e2) {
                throw new RuntimeException("Serializing to a ByteString should never fail with an IOException", e2);
            }
        }

        public void writeAsMessageSetExtensionTo(int i2, CodedOutputStream codedOutputStream) throws IOException {
            Iterator<ByteString> it = this.lengthDelimited.iterator();
            while (it.hasNext()) {
                codedOutputStream.writeRawMessageSetExtension(i2, it.next());
            }
        }

        public void writeTo(int i2, CodedOutputStream codedOutputStream) throws IOException {
            Iterator<Long> it = this.varint.iterator();
            while (it.hasNext()) {
                codedOutputStream.writeUInt64(i2, it.next().longValue());
            }
            Iterator<Integer> it2 = this.fixed32.iterator();
            while (it2.hasNext()) {
                codedOutputStream.writeFixed32(i2, it2.next().intValue());
            }
            Iterator<Long> it3 = this.fixed64.iterator();
            while (it3.hasNext()) {
                codedOutputStream.writeFixed64(i2, it3.next().longValue());
            }
            Iterator<ByteString> it4 = this.lengthDelimited.iterator();
            while (it4.hasNext()) {
                codedOutputStream.writeBytes(i2, it4.next());
            }
            Iterator<UnknownFieldSet> it5 = this.group.iterator();
            while (it5.hasNext()) {
                codedOutputStream.writeGroup(i2, it5.next());
            }
        }

        public static Builder newBuilder(Field field) {
            return newBuilder().mergeFrom(field);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void writeAsMessageSetExtensionTo(int i2, Writer writer) throws IOException {
            if (writer.fieldOrder() == Writer.FieldOrder.DESCENDING) {
                List<ByteString> list = this.lengthDelimited;
                ListIterator<ByteString> listIterator = list.listIterator(list.size());
                while (listIterator.hasPrevious()) {
                    writer.writeMessageSetItem(i2, listIterator.previous());
                }
                return;
            }
            Iterator<ByteString> it = this.lengthDelimited.iterator();
            while (it.hasNext()) {
                writer.writeMessageSetItem(i2, it.next());
            }
        }
    }

    public static final class Parser extends AbstractParser<UnknownFieldSet> {
        @Override // com.google.protobuf.Parser
        public UnknownFieldSet parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            Builder builderNewBuilder = UnknownFieldSet.newBuilder();
            try {
                builderNewBuilder.mergeFrom(codedInputStream);
                return builderNewBuilder.buildPartial();
            } catch (InvalidProtocolBufferException e2) {
                throw e2.setUnfinishedMessage(builderNewBuilder.buildPartial());
            } catch (IOException e3) {
                throw new InvalidProtocolBufferException(e3).setUnfinishedMessage(builderNewBuilder.buildPartial());
            }
        }
    }

    public static UnknownFieldSet getDefaultInstance() {
        return defaultInstance;
    }

    public static Builder newBuilder() {
        return Builder.create();
    }

    public static UnknownFieldSet parseFrom(CodedInputStream codedInputStream) throws IOException {
        return newBuilder().mergeFrom(codedInputStream).build();
    }

    public Map<Integer, Field> asMap() {
        return (Map) this.fields.clone();
    }

    void b(Writer writer) throws IOException {
        if (writer.fieldOrder() == Writer.FieldOrder.DESCENDING) {
            for (Map.Entry<Integer, Field> entry : this.fields.descendingMap().entrySet()) {
                entry.getValue().writeAsMessageSetExtensionTo(entry.getKey().intValue(), writer);
            }
            return;
        }
        for (Map.Entry<Integer, Field> entry2 : this.fields.entrySet()) {
            entry2.getValue().writeAsMessageSetExtensionTo(entry2.getKey().intValue(), writer);
        }
    }

    void c(Writer writer) throws IOException {
        if (writer.fieldOrder() == Writer.FieldOrder.DESCENDING) {
            for (Map.Entry<Integer, Field> entry : this.fields.descendingMap().entrySet()) {
                entry.getValue().l(entry.getKey().intValue(), writer);
            }
            return;
        }
        for (Map.Entry<Integer, Field> entry2 : this.fields.entrySet()) {
            entry2.getValue().l(entry2.getKey().intValue(), writer);
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof UnknownFieldSet) && this.fields.equals(((UnknownFieldSet) obj).fields);
    }

    public Field getField(int i2) {
        Field field = this.fields.get(Integer.valueOf(i2));
        return field == null ? Field.getDefaultInstance() : field;
    }

    @Override // com.google.protobuf.MessageLite
    public int getSerializedSize() {
        int serializedSize = 0;
        if (!this.fields.isEmpty()) {
            for (Map.Entry<Integer, Field> entry : this.fields.entrySet()) {
                serializedSize += entry.getValue().getSerializedSize(entry.getKey().intValue());
            }
        }
        return serializedSize;
    }

    public int getSerializedSizeAsMessageSet() {
        int serializedSizeAsMessageSetExtension = 0;
        for (Map.Entry<Integer, Field> entry : this.fields.entrySet()) {
            serializedSizeAsMessageSetExtension += entry.getValue().getSerializedSizeAsMessageSetExtension(entry.getKey().intValue());
        }
        return serializedSizeAsMessageSetExtension;
    }

    public boolean hasField(int i2) {
        return this.fields.containsKey(Integer.valueOf(i2));
    }

    public int hashCode() {
        if (this.fields.isEmpty()) {
            return 0;
        }
        return this.fields.hashCode();
    }

    @Override // com.google.protobuf.MessageLiteOrBuilder
    public boolean isInitialized() {
        return true;
    }

    @Override // com.google.protobuf.MessageLite
    public byte[] toByteArray() {
        try {
            byte[] bArr = new byte[getSerializedSize()];
            CodedOutputStream codedOutputStreamNewInstance = CodedOutputStream.newInstance(bArr);
            writeTo(codedOutputStreamNewInstance);
            codedOutputStreamNewInstance.checkNoSpaceLeft();
            return bArr;
        } catch (IOException e2) {
            throw new RuntimeException("Serializing to a byte array threw an IOException (should never happen).", e2);
        }
    }

    @Override // com.google.protobuf.MessageLite
    public ByteString toByteString() {
        try {
            ByteString.CodedBuilder codedBuilderNewCodedBuilder = ByteString.newCodedBuilder(getSerializedSize());
            writeTo(codedBuilderNewCodedBuilder.getCodedOutput());
            return codedBuilderNewCodedBuilder.build();
        } catch (IOException e2) {
            throw new RuntimeException("Serializing to a ByteString threw an IOException (should never happen).", e2);
        }
    }

    public String toString() {
        return TextFormat.printer().printToString(this);
    }

    public void writeAsMessageSetTo(CodedOutputStream codedOutputStream) throws IOException {
        for (Map.Entry<Integer, Field> entry : this.fields.entrySet()) {
            entry.getValue().writeAsMessageSetExtensionTo(entry.getKey().intValue(), codedOutputStream);
        }
    }

    @Override // com.google.protobuf.MessageLite
    public void writeDelimitedTo(OutputStream outputStream) throws IOException {
        CodedOutputStream codedOutputStreamNewInstance = CodedOutputStream.newInstance(outputStream);
        codedOutputStreamNewInstance.writeUInt32NoTag(getSerializedSize());
        writeTo(codedOutputStreamNewInstance);
        codedOutputStreamNewInstance.flush();
    }

    @Override // com.google.protobuf.MessageLite
    public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
        for (Map.Entry<Integer, Field> entry : this.fields.entrySet()) {
            entry.getValue().writeTo(entry.getKey().intValue(), codedOutputStream);
        }
    }

    private UnknownFieldSet(TreeMap<Integer, Field> treeMap) {
        this.fields = treeMap;
    }

    public static Builder newBuilder(UnknownFieldSet unknownFieldSet) {
        return newBuilder().mergeFrom(unknownFieldSet);
    }

    public static UnknownFieldSet parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return newBuilder().mergeFrom(byteString).build();
    }

    @Override // com.google.protobuf.MessageLiteOrBuilder, com.google.protobuf.MessageOrBuilder
    public UnknownFieldSet getDefaultInstanceForType() {
        return defaultInstance;
    }

    @Override // com.google.protobuf.MessageLite, com.google.protobuf.Message
    public final Parser getParserForType() {
        return PARSER;
    }

    @Override // com.google.protobuf.MessageLite, com.google.protobuf.Message
    public Builder newBuilderForType() {
        return newBuilder();
    }

    @Override // com.google.protobuf.MessageLite, com.google.protobuf.Message
    public Builder toBuilder() {
        return newBuilder().mergeFrom(this);
    }

    public static UnknownFieldSet parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
        return newBuilder().mergeFrom(bArr).build();
    }

    public static UnknownFieldSet parseFrom(InputStream inputStream) throws IOException {
        return newBuilder().mergeFrom(inputStream).build();
    }

    @Override // com.google.protobuf.MessageLite
    public void writeTo(OutputStream outputStream) throws IOException {
        CodedOutputStream codedOutputStreamNewInstance = CodedOutputStream.newInstance(outputStream);
        writeTo(codedOutputStreamNewInstance);
        codedOutputStreamNewInstance.flush();
    }
}
