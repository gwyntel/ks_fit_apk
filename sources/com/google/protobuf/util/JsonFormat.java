package com.google.protobuf.util;

import anetwork.channel.util.RequestConstant;
import com.alipay.sdk.m.u.i;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.facebook.GraphRequest;
import com.google.common.base.Preconditions;
import com.google.common.io.BaseEncoding;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.stream.JsonReader;
import com.google.protobuf.Any;
import com.google.protobuf.BoolValue;
import com.google.protobuf.ByteString;
import com.google.protobuf.BytesValue;
import com.google.protobuf.Descriptors;
import com.google.protobuf.DoubleValue;
import com.google.protobuf.Duration;
import com.google.protobuf.DynamicMessage;
import com.google.protobuf.FieldMask;
import com.google.protobuf.FloatValue;
import com.google.protobuf.Int32Value;
import com.google.protobuf.Int64Value;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.ListValue;
import com.google.protobuf.Message;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.NullValue;
import com.google.protobuf.StringValue;
import com.google.protobuf.Struct;
import com.google.protobuf.Timestamp;
import com.google.protobuf.UInt32Value;
import com.google.protobuf.UInt64Value;
import com.google.protobuf.Value;
import com.kingsmith.miot.KsProperty;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Logger;
import javax.annotation.Nullable;

/* loaded from: classes2.dex */
public class JsonFormat {
    private static final Logger logger = Logger.getLogger(JsonFormat.class.getName());

    /* renamed from: com.google.protobuf.util.JsonFormat$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f15392a;

        static {
            int[] iArr = new int[Descriptors.FieldDescriptor.Type.values().length];
            f15392a = iArr;
            try {
                iArr[Descriptors.FieldDescriptor.Type.INT32.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f15392a[Descriptors.FieldDescriptor.Type.SINT32.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f15392a[Descriptors.FieldDescriptor.Type.SFIXED32.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f15392a[Descriptors.FieldDescriptor.Type.INT64.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f15392a[Descriptors.FieldDescriptor.Type.SINT64.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f15392a[Descriptors.FieldDescriptor.Type.SFIXED64.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f15392a[Descriptors.FieldDescriptor.Type.BOOL.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f15392a[Descriptors.FieldDescriptor.Type.FLOAT.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                f15392a[Descriptors.FieldDescriptor.Type.DOUBLE.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                f15392a[Descriptors.FieldDescriptor.Type.UINT32.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                f15392a[Descriptors.FieldDescriptor.Type.FIXED32.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                f15392a[Descriptors.FieldDescriptor.Type.UINT64.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                f15392a[Descriptors.FieldDescriptor.Type.FIXED64.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                f15392a[Descriptors.FieldDescriptor.Type.STRING.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                f15392a[Descriptors.FieldDescriptor.Type.BYTES.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                f15392a[Descriptors.FieldDescriptor.Type.ENUM.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                f15392a[Descriptors.FieldDescriptor.Type.MESSAGE.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                f15392a[Descriptors.FieldDescriptor.Type.GROUP.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
        }
    }

    private static final class CompactTextGenerator implements TextGenerator {
        private final Appendable output;

        /* synthetic */ CompactTextGenerator(Appendable appendable, AnonymousClass1 anonymousClass1) {
            this(appendable);
        }

        @Override // com.google.protobuf.util.JsonFormat.TextGenerator
        public void indent() {
        }

        @Override // com.google.protobuf.util.JsonFormat.TextGenerator
        public void outdent() {
        }

        @Override // com.google.protobuf.util.JsonFormat.TextGenerator
        public void print(CharSequence charSequence) throws IOException {
            this.output.append(charSequence);
        }

        private CompactTextGenerator(Appendable appendable) {
            this.output = appendable;
        }
    }

    public static class Parser {
        private static final int DEFAULT_RECURSION_LIMIT = 100;
        private final boolean ignoringUnknownFields;
        private final TypeRegistry oldRegistry;
        private final int recursionLimit;
        private final com.google.protobuf.TypeRegistry registry;

        /* synthetic */ Parser(com.google.protobuf.TypeRegistry typeRegistry, TypeRegistry typeRegistry2, boolean z2, int i2, AnonymousClass1 anonymousClass1) {
            this(typeRegistry, typeRegistry2, z2, i2);
        }

        public Parser ignoringUnknownFields() {
            return new Parser(this.registry, this.oldRegistry, true, this.recursionLimit);
        }

        public void merge(String str, Message.Builder builder) throws InvalidProtocolBufferException {
            new ParserImpl(this.registry, this.oldRegistry, this.ignoringUnknownFields, this.recursionLimit).j(str, builder);
        }

        public Parser usingTypeRegistry(TypeRegistry typeRegistry) {
            if (this.oldRegistry == TypeRegistry.getEmptyTypeRegistry() && this.registry == com.google.protobuf.TypeRegistry.getEmptyTypeRegistry()) {
                return new Parser(com.google.protobuf.TypeRegistry.getEmptyTypeRegistry(), typeRegistry, this.ignoringUnknownFields, this.recursionLimit);
            }
            throw new IllegalArgumentException("Only one registry is allowed.");
        }

        private Parser(com.google.protobuf.TypeRegistry typeRegistry, TypeRegistry typeRegistry2, boolean z2, int i2) {
            this.registry = typeRegistry;
            this.oldRegistry = typeRegistry2;
            this.ignoringUnknownFields = z2;
            this.recursionLimit = i2;
        }

        public void merge(Reader reader, Message.Builder builder) throws IOException {
            new ParserImpl(this.registry, this.oldRegistry, this.ignoringUnknownFields, this.recursionLimit).i(reader, builder);
        }

        public Parser usingTypeRegistry(com.google.protobuf.TypeRegistry typeRegistry) {
            if (this.oldRegistry == TypeRegistry.getEmptyTypeRegistry() && this.registry == com.google.protobuf.TypeRegistry.getEmptyTypeRegistry()) {
                return new Parser(typeRegistry, this.oldRegistry, this.ignoringUnknownFields, this.recursionLimit);
            }
            throw new IllegalArgumentException("Only one registry is allowed.");
        }
    }

    private static class ParserImpl {
        private static final double EPSILON = 1.0E-6d;
        private static final BigDecimal MAX_DOUBLE;
        private static final BigDecimal MIN_DOUBLE;
        private static final BigDecimal MORE_THAN_ONE;
        private final boolean ignoringUnknownFields;
        private final TypeRegistry oldRegistry;
        private final int recursionLimit;
        private final com.google.protobuf.TypeRegistry registry;
        private static final Map<String, WellKnownTypeParser> wellKnownTypeParsers = buildWellKnownTypeParsers();
        private static final BigInteger MAX_UINT64 = new BigInteger("FFFFFFFFFFFFFFFF", 16);
        private final Map<Descriptors.Descriptor, Map<String, Descriptors.FieldDescriptor>> fieldNameMaps = new HashMap();
        private int currentDepth = 0;

        private interface WellKnownTypeParser {
            void merge(ParserImpl parserImpl, JsonElement jsonElement, Message.Builder builder) throws InvalidProtocolBufferException;
        }

        static {
            BigDecimal bigDecimal = new BigDecimal(String.valueOf(1.000001d));
            MORE_THAN_ONE = bigDecimal;
            MAX_DOUBLE = new BigDecimal(String.valueOf(Double.MAX_VALUE)).multiply(bigDecimal);
            MIN_DOUBLE = new BigDecimal(String.valueOf(-1.7976931348623157E308d)).multiply(bigDecimal);
        }

        ParserImpl(com.google.protobuf.TypeRegistry typeRegistry, TypeRegistry typeRegistry2, boolean z2, int i2) {
            this.registry = typeRegistry;
            this.oldRegistry = typeRegistry2;
            this.ignoringUnknownFields = z2;
            this.recursionLimit = i2;
        }

        private static Map<String, WellKnownTypeParser> buildWellKnownTypeParsers() {
            HashMap map = new HashMap();
            map.put(Any.getDescriptor().getFullName(), new WellKnownTypeParser() { // from class: com.google.protobuf.util.JsonFormat.ParserImpl.1
                @Override // com.google.protobuf.util.JsonFormat.ParserImpl.WellKnownTypeParser
                public void merge(ParserImpl parserImpl, JsonElement jsonElement, Message.Builder builder) throws InvalidProtocolBufferException {
                    parserImpl.mergeAny(jsonElement, builder);
                }
            });
            WellKnownTypeParser wellKnownTypeParser = new WellKnownTypeParser() { // from class: com.google.protobuf.util.JsonFormat.ParserImpl.2
                @Override // com.google.protobuf.util.JsonFormat.ParserImpl.WellKnownTypeParser
                public void merge(ParserImpl parserImpl, JsonElement jsonElement, Message.Builder builder) throws InvalidProtocolBufferException {
                    parserImpl.mergeWrapper(jsonElement, builder);
                }
            };
            map.put(BoolValue.getDescriptor().getFullName(), wellKnownTypeParser);
            map.put(Int32Value.getDescriptor().getFullName(), wellKnownTypeParser);
            map.put(UInt32Value.getDescriptor().getFullName(), wellKnownTypeParser);
            map.put(Int64Value.getDescriptor().getFullName(), wellKnownTypeParser);
            map.put(UInt64Value.getDescriptor().getFullName(), wellKnownTypeParser);
            map.put(StringValue.getDescriptor().getFullName(), wellKnownTypeParser);
            map.put(BytesValue.getDescriptor().getFullName(), wellKnownTypeParser);
            map.put(FloatValue.getDescriptor().getFullName(), wellKnownTypeParser);
            map.put(DoubleValue.getDescriptor().getFullName(), wellKnownTypeParser);
            map.put(Timestamp.getDescriptor().getFullName(), new WellKnownTypeParser() { // from class: com.google.protobuf.util.JsonFormat.ParserImpl.3
                @Override // com.google.protobuf.util.JsonFormat.ParserImpl.WellKnownTypeParser
                public void merge(ParserImpl parserImpl, JsonElement jsonElement, Message.Builder builder) throws InvalidProtocolBufferException {
                    parserImpl.mergeTimestamp(jsonElement, builder);
                }
            });
            map.put(Duration.getDescriptor().getFullName(), new WellKnownTypeParser() { // from class: com.google.protobuf.util.JsonFormat.ParserImpl.4
                @Override // com.google.protobuf.util.JsonFormat.ParserImpl.WellKnownTypeParser
                public void merge(ParserImpl parserImpl, JsonElement jsonElement, Message.Builder builder) throws InvalidProtocolBufferException {
                    parserImpl.mergeDuration(jsonElement, builder);
                }
            });
            map.put(FieldMask.getDescriptor().getFullName(), new WellKnownTypeParser() { // from class: com.google.protobuf.util.JsonFormat.ParserImpl.5
                @Override // com.google.protobuf.util.JsonFormat.ParserImpl.WellKnownTypeParser
                public void merge(ParserImpl parserImpl, JsonElement jsonElement, Message.Builder builder) throws InvalidProtocolBufferException {
                    parserImpl.mergeFieldMask(jsonElement, builder);
                }
            });
            map.put(Struct.getDescriptor().getFullName(), new WellKnownTypeParser() { // from class: com.google.protobuf.util.JsonFormat.ParserImpl.6
                @Override // com.google.protobuf.util.JsonFormat.ParserImpl.WellKnownTypeParser
                public void merge(ParserImpl parserImpl, JsonElement jsonElement, Message.Builder builder) throws InvalidProtocolBufferException {
                    parserImpl.mergeStruct(jsonElement, builder);
                }
            });
            map.put(ListValue.getDescriptor().getFullName(), new WellKnownTypeParser() { // from class: com.google.protobuf.util.JsonFormat.ParserImpl.7
                @Override // com.google.protobuf.util.JsonFormat.ParserImpl.WellKnownTypeParser
                public void merge(ParserImpl parserImpl, JsonElement jsonElement, Message.Builder builder) throws InvalidProtocolBufferException {
                    parserImpl.mergeListValue(jsonElement, builder);
                }
            });
            map.put(Value.getDescriptor().getFullName(), new WellKnownTypeParser() { // from class: com.google.protobuf.util.JsonFormat.ParserImpl.8
                @Override // com.google.protobuf.util.JsonFormat.ParserImpl.WellKnownTypeParser
                public void merge(ParserImpl parserImpl, JsonElement jsonElement, Message.Builder builder) throws InvalidProtocolBufferException {
                    parserImpl.mergeValue(jsonElement, builder);
                }
            });
            return map;
        }

        private Map<String, Descriptors.FieldDescriptor> getFieldNameMap(Descriptors.Descriptor descriptor) {
            if (this.fieldNameMaps.containsKey(descriptor)) {
                return this.fieldNameMaps.get(descriptor);
            }
            HashMap map = new HashMap();
            for (Descriptors.FieldDescriptor fieldDescriptor : descriptor.getFields()) {
                map.put(fieldDescriptor.getName(), fieldDescriptor);
                map.put(fieldDescriptor.getJsonName(), fieldDescriptor);
            }
            this.fieldNameMaps.put(descriptor, map);
            return map;
        }

        private void merge(JsonElement jsonElement, Message.Builder builder) throws InvalidProtocolBufferException {
            WellKnownTypeParser wellKnownTypeParser = wellKnownTypeParsers.get(builder.getDescriptorForType().getFullName());
            if (wellKnownTypeParser != null) {
                wellKnownTypeParser.merge(this, jsonElement, builder);
            } else {
                mergeMessage(jsonElement, builder, false);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mergeAny(JsonElement jsonElement, Message.Builder builder) throws InvalidProtocolBufferException {
            Descriptors.Descriptor descriptorForType = builder.getDescriptorForType();
            Descriptors.FieldDescriptor fieldDescriptorFindFieldByName = descriptorForType.findFieldByName("type_url");
            Descriptors.FieldDescriptor fieldDescriptorFindFieldByName2 = descriptorForType.findFieldByName("value");
            if (fieldDescriptorFindFieldByName == null || fieldDescriptorFindFieldByName2 == null || fieldDescriptorFindFieldByName.getType() != Descriptors.FieldDescriptor.Type.STRING || fieldDescriptorFindFieldByName2.getType() != Descriptors.FieldDescriptor.Type.BYTES) {
                throw new InvalidProtocolBufferException("Invalid Any type.");
            }
            if (!(jsonElement instanceof JsonObject)) {
                throw new InvalidProtocolBufferException("Expect message object but got: " + jsonElement);
            }
            JsonObject jsonObject = (JsonObject) jsonElement;
            if (jsonObject.entrySet().isEmpty()) {
                return;
            }
            JsonElement jsonElement2 = jsonObject.get("@type");
            if (jsonElement2 == null) {
                throw new InvalidProtocolBufferException("Missing type url when parsing: " + jsonElement);
            }
            String asString = jsonElement2.getAsString();
            Descriptors.Descriptor descriptorForTypeUrl = this.registry.getDescriptorForTypeUrl(asString);
            if (descriptorForTypeUrl == null && (descriptorForTypeUrl = this.oldRegistry.a(asString)) == null) {
                throw new InvalidProtocolBufferException("Cannot resolve type: " + asString);
            }
            builder.setField(fieldDescriptorFindFieldByName, asString);
            DynamicMessage.Builder builderNewBuilderForType = DynamicMessage.getDefaultInstance(descriptorForTypeUrl).newBuilderForType();
            WellKnownTypeParser wellKnownTypeParser = wellKnownTypeParsers.get(descriptorForTypeUrl.getFullName());
            if (wellKnownTypeParser != null) {
                JsonElement jsonElement3 = jsonObject.get("value");
                if (jsonElement3 != null) {
                    wellKnownTypeParser.merge(this, jsonElement3, builderNewBuilderForType);
                }
            } else {
                mergeMessage(jsonElement, builderNewBuilderForType, true);
            }
            builder.setField(fieldDescriptorFindFieldByName2, builderNewBuilderForType.build().toByteString());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mergeDuration(JsonElement jsonElement, Message.Builder builder) throws InvalidProtocolBufferException {
            try {
                builder.mergeFrom(Durations.parse(jsonElement.getAsString()).toByteString());
            } catch (UnsupportedOperationException | ParseException e2) {
                InvalidProtocolBufferException invalidProtocolBufferException = new InvalidProtocolBufferException("Failed to parse duration: " + jsonElement);
                invalidProtocolBufferException.initCause(e2);
                throw invalidProtocolBufferException;
            }
        }

        private void mergeField(Descriptors.FieldDescriptor fieldDescriptor, JsonElement jsonElement, Message.Builder builder) throws InvalidProtocolBufferException {
            if (fieldDescriptor.isRepeated()) {
                if (builder.getRepeatedFieldCount(fieldDescriptor) > 0) {
                    throw new InvalidProtocolBufferException("Field " + fieldDescriptor.getFullName() + " has already been set.");
                }
            } else if (builder.hasField(fieldDescriptor)) {
                throw new InvalidProtocolBufferException("Field " + fieldDescriptor.getFullName() + " has already been set.");
            }
            if (fieldDescriptor.isRepeated() && (jsonElement instanceof JsonNull)) {
                return;
            }
            if (fieldDescriptor.isMapField()) {
                mergeMapField(fieldDescriptor, jsonElement, builder);
                return;
            }
            if (fieldDescriptor.isRepeated()) {
                mergeRepeatedField(fieldDescriptor, jsonElement, builder);
                return;
            }
            if (fieldDescriptor.getContainingOneof() != null) {
                mergeOneofField(fieldDescriptor, jsonElement, builder);
                return;
            }
            Object fieldValue = parseFieldValue(fieldDescriptor, jsonElement, builder);
            if (fieldValue != null) {
                builder.setField(fieldDescriptor, fieldValue);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mergeFieldMask(JsonElement jsonElement, Message.Builder builder) throws InvalidProtocolBufferException {
            builder.mergeFrom(FieldMaskUtil.fromJsonString(jsonElement.getAsString()).toByteString());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mergeListValue(JsonElement jsonElement, Message.Builder builder) throws InvalidProtocolBufferException {
            Descriptors.FieldDescriptor fieldDescriptorFindFieldByName = builder.getDescriptorForType().findFieldByName("values");
            if (fieldDescriptorFindFieldByName == null) {
                throw new InvalidProtocolBufferException("Invalid ListValue type.");
            }
            mergeRepeatedField(fieldDescriptorFindFieldByName, jsonElement, builder);
        }

        private void mergeMapField(Descriptors.FieldDescriptor fieldDescriptor, JsonElement jsonElement, Message.Builder builder) throws InvalidProtocolBufferException {
            if (!(jsonElement instanceof JsonObject)) {
                throw new InvalidProtocolBufferException("Expect a map object but found: " + jsonElement);
            }
            Descriptors.Descriptor messageType = fieldDescriptor.getMessageType();
            Descriptors.FieldDescriptor fieldDescriptorFindFieldByName = messageType.findFieldByName(KsProperty.Key);
            Descriptors.FieldDescriptor fieldDescriptorFindFieldByName2 = messageType.findFieldByName("value");
            if (fieldDescriptorFindFieldByName == null || fieldDescriptorFindFieldByName2 == null) {
                throw new InvalidProtocolBufferException("Invalid map field: " + fieldDescriptor.getFullName());
            }
            for (Map.Entry<String, JsonElement> entry : ((JsonObject) jsonElement).entrySet()) {
                Message.Builder builderNewBuilderForField = builder.newBuilderForField(fieldDescriptor);
                Object fieldValue = parseFieldValue(fieldDescriptorFindFieldByName, new JsonPrimitive(entry.getKey()), builderNewBuilderForField);
                Object fieldValue2 = parseFieldValue(fieldDescriptorFindFieldByName2, entry.getValue(), builderNewBuilderForField);
                if (fieldValue2 != null) {
                    builderNewBuilderForField.setField(fieldDescriptorFindFieldByName, fieldValue);
                    builderNewBuilderForField.setField(fieldDescriptorFindFieldByName2, fieldValue2);
                    builder.addRepeatedField(fieldDescriptor, builderNewBuilderForField.build());
                } else if (!this.ignoringUnknownFields || fieldDescriptorFindFieldByName2.getType() != Descriptors.FieldDescriptor.Type.ENUM) {
                    throw new InvalidProtocolBufferException("Map value cannot be null.");
                }
            }
        }

        private void mergeMessage(JsonElement jsonElement, Message.Builder builder, boolean z2) throws InvalidProtocolBufferException {
            if (!(jsonElement instanceof JsonObject)) {
                throw new InvalidProtocolBufferException("Expect message object but got: " + jsonElement);
            }
            Map<String, Descriptors.FieldDescriptor> fieldNameMap = getFieldNameMap(builder.getDescriptorForType());
            for (Map.Entry<String, JsonElement> entry : ((JsonObject) jsonElement).entrySet()) {
                if (!z2 || !entry.getKey().equals("@type")) {
                    Descriptors.FieldDescriptor fieldDescriptor = fieldNameMap.get(entry.getKey());
                    if (fieldDescriptor != null) {
                        mergeField(fieldDescriptor, entry.getValue(), builder);
                    } else if (!this.ignoringUnknownFields) {
                        throw new InvalidProtocolBufferException("Cannot find field: " + entry.getKey() + " in message " + builder.getDescriptorForType().getFullName());
                    }
                }
            }
        }

        private void mergeOneofField(Descriptors.FieldDescriptor fieldDescriptor, JsonElement jsonElement, Message.Builder builder) throws InvalidProtocolBufferException {
            Object fieldValue = parseFieldValue(fieldDescriptor, jsonElement, builder);
            if (fieldValue == null) {
                return;
            }
            if (builder.getOneofFieldDescriptor(fieldDescriptor.getContainingOneof()) == null) {
                builder.setField(fieldDescriptor, fieldValue);
                return;
            }
            throw new InvalidProtocolBufferException("Cannot set field " + fieldDescriptor.getFullName() + " because another field " + builder.getOneofFieldDescriptor(fieldDescriptor.getContainingOneof()).getFullName() + " belonging to the same oneof has already been set ");
        }

        private void mergeRepeatedField(Descriptors.FieldDescriptor fieldDescriptor, JsonElement jsonElement, Message.Builder builder) throws InvalidProtocolBufferException {
            if (!(jsonElement instanceof JsonArray)) {
                throw new InvalidProtocolBufferException("Expected an array for " + fieldDescriptor.getName() + " but found " + jsonElement);
            }
            JsonArray jsonArray = (JsonArray) jsonElement;
            for (int i2 = 0; i2 < jsonArray.size(); i2++) {
                Object fieldValue = parseFieldValue(fieldDescriptor, jsonArray.get(i2), builder);
                if (fieldValue != null) {
                    builder.addRepeatedField(fieldDescriptor, fieldValue);
                } else if (!this.ignoringUnknownFields || fieldDescriptor.getType() != Descriptors.FieldDescriptor.Type.ENUM) {
                    throw new InvalidProtocolBufferException("Repeated field elements cannot be null in field: " + fieldDescriptor.getFullName());
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mergeStruct(JsonElement jsonElement, Message.Builder builder) throws InvalidProtocolBufferException {
            Descriptors.FieldDescriptor fieldDescriptorFindFieldByName = builder.getDescriptorForType().findFieldByName(GraphRequest.FIELDS_PARAM);
            if (fieldDescriptorFindFieldByName == null) {
                throw new InvalidProtocolBufferException("Invalid Struct type.");
            }
            mergeMapField(fieldDescriptorFindFieldByName, jsonElement, builder);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mergeTimestamp(JsonElement jsonElement, Message.Builder builder) throws InvalidProtocolBufferException {
            try {
                builder.mergeFrom(Timestamps.parse(jsonElement.getAsString()).toByteString());
            } catch (UnsupportedOperationException | ParseException e2) {
                InvalidProtocolBufferException invalidProtocolBufferException = new InvalidProtocolBufferException("Failed to parse timestamp: " + jsonElement);
                invalidProtocolBufferException.initCause(e2);
                throw invalidProtocolBufferException;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mergeValue(JsonElement jsonElement, Message.Builder builder) throws InvalidProtocolBufferException {
            Descriptors.Descriptor descriptorForType = builder.getDescriptorForType();
            if (jsonElement instanceof JsonPrimitive) {
                JsonPrimitive jsonPrimitive = (JsonPrimitive) jsonElement;
                if (jsonPrimitive.isBoolean()) {
                    builder.setField(descriptorForType.findFieldByName("bool_value"), Boolean.valueOf(jsonPrimitive.getAsBoolean()));
                    return;
                } else if (jsonPrimitive.isNumber()) {
                    builder.setField(descriptorForType.findFieldByName("number_value"), Double.valueOf(jsonPrimitive.getAsDouble()));
                    return;
                } else {
                    builder.setField(descriptorForType.findFieldByName("string_value"), jsonPrimitive.getAsString());
                    return;
                }
            }
            if (jsonElement instanceof JsonObject) {
                Descriptors.FieldDescriptor fieldDescriptorFindFieldByName = descriptorForType.findFieldByName("struct_value");
                Message.Builder builderNewBuilderForField = builder.newBuilderForField(fieldDescriptorFindFieldByName);
                merge(jsonElement, builderNewBuilderForField);
                builder.setField(fieldDescriptorFindFieldByName, builderNewBuilderForField.build());
                return;
            }
            if (jsonElement instanceof JsonArray) {
                Descriptors.FieldDescriptor fieldDescriptorFindFieldByName2 = descriptorForType.findFieldByName("list_value");
                Message.Builder builderNewBuilderForField2 = builder.newBuilderForField(fieldDescriptorFindFieldByName2);
                merge(jsonElement, builderNewBuilderForField2);
                builder.setField(fieldDescriptorFindFieldByName2, builderNewBuilderForField2.build());
                return;
            }
            if (jsonElement instanceof JsonNull) {
                builder.setField(descriptorForType.findFieldByName("null_value"), NullValue.NULL_VALUE.getValueDescriptor());
                return;
            }
            throw new IllegalStateException("Unexpected json data: " + jsonElement);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mergeWrapper(JsonElement jsonElement, Message.Builder builder) throws InvalidProtocolBufferException {
            Descriptors.Descriptor descriptorForType = builder.getDescriptorForType();
            Descriptors.FieldDescriptor fieldDescriptorFindFieldByName = descriptorForType.findFieldByName("value");
            if (fieldDescriptorFindFieldByName != null) {
                builder.setField(fieldDescriptorFindFieldByName, parseFieldValue(fieldDescriptorFindFieldByName, jsonElement, builder));
                return;
            }
            throw new InvalidProtocolBufferException("Invalid wrapper type: " + descriptorForType.getFullName());
        }

        private boolean parseBool(JsonElement jsonElement) throws InvalidProtocolBufferException {
            if (jsonElement.getAsString().equals("true")) {
                return true;
            }
            if (jsonElement.getAsString().equals(RequestConstant.FALSE)) {
                return false;
            }
            throw new InvalidProtocolBufferException("Invalid bool value: " + jsonElement);
        }

        private ByteString parseBytes(JsonElement jsonElement) {
            try {
                return ByteString.copyFrom(BaseEncoding.base64().decode(jsonElement.getAsString()));
            } catch (IllegalArgumentException unused) {
                return ByteString.copyFrom(BaseEncoding.base64Url().decode(jsonElement.getAsString()));
            }
        }

        private double parseDouble(JsonElement jsonElement) throws InvalidProtocolBufferException {
            if (jsonElement.getAsString().equals("NaN")) {
                return Double.NaN;
            }
            if (jsonElement.getAsString().equals("Infinity")) {
                return Double.POSITIVE_INFINITY;
            }
            if (jsonElement.getAsString().equals("-Infinity")) {
                return Double.NEGATIVE_INFINITY;
            }
            try {
                BigDecimal bigDecimal = new BigDecimal(jsonElement.getAsString());
                if (bigDecimal.compareTo(MAX_DOUBLE) <= 0 && bigDecimal.compareTo(MIN_DOUBLE) >= 0) {
                    return bigDecimal.doubleValue();
                }
                throw new InvalidProtocolBufferException("Out of range double value: " + jsonElement);
            } catch (RuntimeException e2) {
                InvalidProtocolBufferException invalidProtocolBufferException = new InvalidProtocolBufferException("Not a double value: " + jsonElement);
                invalidProtocolBufferException.initCause(e2);
                throw invalidProtocolBufferException;
            }
        }

        @Nullable
        private Descriptors.EnumValueDescriptor parseEnum(Descriptors.EnumDescriptor enumDescriptor, JsonElement jsonElement) throws InvalidProtocolBufferException {
            String asString = jsonElement.getAsString();
            Descriptors.EnumValueDescriptor enumValueDescriptorFindValueByName = enumDescriptor.findValueByName(asString);
            if (enumValueDescriptorFindValueByName == null) {
                try {
                    int int32 = parseInt32(jsonElement);
                    enumValueDescriptorFindValueByName = enumDescriptor.getFile().getSyntax() == Descriptors.FileDescriptor.Syntax.PROTO3 ? enumDescriptor.findValueByNumberCreatingIfUnknown(int32) : enumDescriptor.findValueByNumber(int32);
                } catch (InvalidProtocolBufferException unused) {
                }
                if (enumValueDescriptorFindValueByName == null && !this.ignoringUnknownFields) {
                    throw new InvalidProtocolBufferException("Invalid enum value: " + asString + " for enum type: " + enumDescriptor.getFullName());
                }
            }
            return enumValueDescriptorFindValueByName;
        }

        @Nullable
        private Object parseFieldValue(Descriptors.FieldDescriptor fieldDescriptor, JsonElement jsonElement, Message.Builder builder) throws InvalidProtocolBufferException {
            if (jsonElement instanceof JsonNull) {
                if (fieldDescriptor.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE && fieldDescriptor.getMessageType().getFullName().equals(Value.getDescriptor().getFullName())) {
                    return builder.newBuilderForField(fieldDescriptor).mergeFrom(Value.newBuilder().setNullValueValue(0).build().toByteString()).build();
                }
                if (fieldDescriptor.getJavaType() == Descriptors.FieldDescriptor.JavaType.ENUM && fieldDescriptor.getEnumType().getFullName().equals(NullValue.getDescriptor().getFullName())) {
                    return fieldDescriptor.getEnumType().findValueByNumber(0);
                }
                return null;
            }
            if ((jsonElement instanceof JsonObject) && fieldDescriptor.getType() != Descriptors.FieldDescriptor.Type.MESSAGE && fieldDescriptor.getType() != Descriptors.FieldDescriptor.Type.GROUP) {
                throw new InvalidProtocolBufferException(String.format("Invalid value: %s for expected type: %s", jsonElement, fieldDescriptor.getType()));
            }
            switch (AnonymousClass1.f15392a[fieldDescriptor.getType().ordinal()]) {
                case 1:
                case 2:
                case 3:
                    return Integer.valueOf(parseInt32(jsonElement));
                case 4:
                case 5:
                case 6:
                    return Long.valueOf(parseInt64(jsonElement));
                case 7:
                    return Boolean.valueOf(parseBool(jsonElement));
                case 8:
                    return Float.valueOf(parseFloat(jsonElement));
                case 9:
                    return Double.valueOf(parseDouble(jsonElement));
                case 10:
                case 11:
                    return Integer.valueOf(parseUint32(jsonElement));
                case 12:
                case 13:
                    return Long.valueOf(parseUint64(jsonElement));
                case 14:
                    return parseString(jsonElement);
                case 15:
                    return parseBytes(jsonElement);
                case 16:
                    return parseEnum(fieldDescriptor.getEnumType(), jsonElement);
                case 17:
                case 18:
                    int i2 = this.currentDepth;
                    if (i2 >= this.recursionLimit) {
                        throw new InvalidProtocolBufferException("Hit recursion limit.");
                    }
                    this.currentDepth = i2 + 1;
                    Message.Builder builderNewBuilderForField = builder.newBuilderForField(fieldDescriptor);
                    merge(jsonElement, builderNewBuilderForField);
                    this.currentDepth--;
                    return builderNewBuilderForField.build();
                default:
                    throw new InvalidProtocolBufferException("Invalid field type: " + fieldDescriptor.getType());
            }
        }

        private float parseFloat(JsonElement jsonElement) throws InvalidProtocolBufferException, NumberFormatException {
            if (jsonElement.getAsString().equals("NaN")) {
                return Float.NaN;
            }
            if (jsonElement.getAsString().equals("Infinity")) {
                return Float.POSITIVE_INFINITY;
            }
            if (jsonElement.getAsString().equals("-Infinity")) {
                return Float.NEGATIVE_INFINITY;
            }
            try {
                double d2 = Double.parseDouble(jsonElement.getAsString());
                if (d2 <= 3.402826869208755E38d && d2 >= -3.402826869208755E38d) {
                    return (float) d2;
                }
                throw new InvalidProtocolBufferException("Out of range float value: " + jsonElement);
            } catch (RuntimeException e2) {
                new InvalidProtocolBufferException("Not a float value: " + jsonElement).initCause(e2);
                throw e2;
            }
        }

        private int parseInt32(JsonElement jsonElement) throws InvalidProtocolBufferException {
            try {
                try {
                    return Integer.parseInt(jsonElement.getAsString());
                } catch (RuntimeException unused) {
                    return new BigDecimal(jsonElement.getAsString()).intValueExact();
                }
            } catch (RuntimeException e2) {
                InvalidProtocolBufferException invalidProtocolBufferException = new InvalidProtocolBufferException("Not an int32 value: " + jsonElement);
                invalidProtocolBufferException.initCause(e2);
                throw invalidProtocolBufferException;
            }
        }

        private long parseInt64(JsonElement jsonElement) throws InvalidProtocolBufferException {
            try {
                try {
                    return Long.parseLong(jsonElement.getAsString());
                } catch (RuntimeException unused) {
                    return new BigDecimal(jsonElement.getAsString()).longValueExact();
                }
            } catch (RuntimeException e2) {
                InvalidProtocolBufferException invalidProtocolBufferException = new InvalidProtocolBufferException("Not an int64 value: " + jsonElement);
                invalidProtocolBufferException.initCause(e2);
                throw invalidProtocolBufferException;
            }
        }

        private String parseString(JsonElement jsonElement) {
            return jsonElement.getAsString();
        }

        private int parseUint32(JsonElement jsonElement) throws InvalidProtocolBufferException, NumberFormatException {
            try {
                try {
                    long j2 = Long.parseLong(jsonElement.getAsString());
                    if (j2 >= 0 && j2 <= 4294967295L) {
                        return (int) j2;
                    }
                    throw new InvalidProtocolBufferException("Out of range uint32 value: " + jsonElement);
                } catch (RuntimeException unused) {
                    BigInteger bigIntegerExact = new BigDecimal(jsonElement.getAsString()).toBigIntegerExact();
                    if (bigIntegerExact.signum() >= 0 && bigIntegerExact.compareTo(new BigInteger("FFFFFFFF", 16)) <= 0) {
                        return bigIntegerExact.intValue();
                    }
                    throw new InvalidProtocolBufferException("Out of range uint32 value: " + jsonElement);
                }
            } catch (RuntimeException e2) {
                InvalidProtocolBufferException invalidProtocolBufferException = new InvalidProtocolBufferException("Not an uint32 value: " + jsonElement);
                invalidProtocolBufferException.initCause(e2);
                throw invalidProtocolBufferException;
            }
        }

        private long parseUint64(JsonElement jsonElement) throws InvalidProtocolBufferException {
            try {
                BigInteger bigIntegerExact = new BigDecimal(jsonElement.getAsString()).toBigIntegerExact();
                if (bigIntegerExact.compareTo(BigInteger.ZERO) >= 0 && bigIntegerExact.compareTo(MAX_UINT64) <= 0) {
                    return bigIntegerExact.longValue();
                }
                throw new InvalidProtocolBufferException("Out of range uint64 value: " + jsonElement);
            } catch (RuntimeException e2) {
                InvalidProtocolBufferException invalidProtocolBufferException = new InvalidProtocolBufferException("Not an uint64 value: " + jsonElement);
                invalidProtocolBufferException.initCause(e2);
                throw invalidProtocolBufferException;
            }
        }

        void i(Reader reader, Message.Builder builder) throws IOException {
            try {
                JsonReader jsonReader = new JsonReader(reader);
                jsonReader.setLenient(false);
                merge(JsonParser.parseReader(jsonReader), builder);
            } catch (JsonIOException e2) {
                if (!(e2.getCause() instanceof IOException)) {
                    throw new InvalidProtocolBufferException(e2.getMessage(), e2);
                }
                throw ((IOException) e2.getCause());
            } catch (RuntimeException e3) {
                throw new InvalidProtocolBufferException(e3.getMessage(), e3);
            }
        }

        void j(String str, Message.Builder builder) throws InvalidProtocolBufferException {
            try {
                JsonReader jsonReader = new JsonReader(new StringReader(str));
                jsonReader.setLenient(false);
                merge(JsonParser.parseReader(jsonReader), builder);
            } catch (RuntimeException e2) {
                InvalidProtocolBufferException invalidProtocolBufferException = new InvalidProtocolBufferException(e2.getMessage());
                invalidProtocolBufferException.initCause(e2);
                throw invalidProtocolBufferException;
            }
        }
    }

    private static final class PrettyTextGenerator implements TextGenerator {
        private boolean atStartOfLine;
        private final StringBuilder indent;
        private final Appendable output;

        /* synthetic */ PrettyTextGenerator(Appendable appendable, AnonymousClass1 anonymousClass1) {
            this(appendable);
        }

        private void write(CharSequence charSequence) throws IOException {
            if (charSequence.length() == 0) {
                return;
            }
            if (this.atStartOfLine) {
                this.atStartOfLine = false;
                this.output.append(this.indent);
            }
            this.output.append(charSequence);
        }

        @Override // com.google.protobuf.util.JsonFormat.TextGenerator
        public void indent() {
            this.indent.append("  ");
        }

        @Override // com.google.protobuf.util.JsonFormat.TextGenerator
        public void outdent() {
            int length = this.indent.length();
            if (length < 2) {
                throw new IllegalArgumentException(" Outdent() without matching Indent().");
            }
            this.indent.delete(length - 2, length);
        }

        @Override // com.google.protobuf.util.JsonFormat.TextGenerator
        public void print(CharSequence charSequence) throws IOException {
            int length = charSequence.length();
            int i2 = 0;
            for (int i3 = 0; i3 < length; i3++) {
                if (charSequence.charAt(i3) == '\n') {
                    int i4 = i3 + 1;
                    write(charSequence.subSequence(i2, i4));
                    this.atStartOfLine = true;
                    i2 = i4;
                }
            }
            write(charSequence.subSequence(i2, length));
        }

        private PrettyTextGenerator(Appendable appendable) {
            this.indent = new StringBuilder();
            this.atStartOfLine = true;
            this.output = appendable;
        }
    }

    public static class Printer {
        private boolean alwaysOutputDefaultValueFields;
        private Set<Descriptors.FieldDescriptor> includingDefaultValueFields;
        private final TypeRegistry oldRegistry;
        private final boolean omittingInsignificantWhitespace;
        private final boolean preservingProtoFieldNames;
        private final boolean printingEnumsAsInts;
        private final com.google.protobuf.TypeRegistry registry;
        private final boolean sortingMapKeys;

        /* synthetic */ Printer(com.google.protobuf.TypeRegistry typeRegistry, TypeRegistry typeRegistry2, boolean z2, Set set, boolean z3, boolean z4, boolean z5, boolean z6, AnonymousClass1 anonymousClass1) {
            this(typeRegistry, typeRegistry2, z2, set, z3, z4, z5, z6);
        }

        private void checkUnsetIncludingDefaultValueFields() {
            if (this.alwaysOutputDefaultValueFields || !this.includingDefaultValueFields.isEmpty()) {
                throw new IllegalStateException("JsonFormat includingDefaultValueFields has already been set.");
            }
        }

        private void checkUnsetPrintingEnumsAsInts() {
            if (this.printingEnumsAsInts) {
                throw new IllegalStateException("JsonFormat printingEnumsAsInts has already been set.");
            }
        }

        public void appendTo(MessageOrBuilder messageOrBuilder, Appendable appendable) throws IOException {
            new PrinterImpl(this.registry, this.oldRegistry, this.alwaysOutputDefaultValueFields, this.includingDefaultValueFields, this.preservingProtoFieldNames, appendable, this.omittingInsignificantWhitespace, this.printingEnumsAsInts, this.sortingMapKeys).i(messageOrBuilder);
        }

        public Printer includingDefaultValueFields() {
            checkUnsetIncludingDefaultValueFields();
            return new Printer(this.registry, this.oldRegistry, true, Collections.emptySet(), this.preservingProtoFieldNames, this.omittingInsignificantWhitespace, this.printingEnumsAsInts, this.sortingMapKeys);
        }

        public Printer omittingInsignificantWhitespace() {
            return new Printer(this.registry, this.oldRegistry, this.alwaysOutputDefaultValueFields, this.includingDefaultValueFields, this.preservingProtoFieldNames, true, this.printingEnumsAsInts, this.sortingMapKeys);
        }

        public Printer preservingProtoFieldNames() {
            return new Printer(this.registry, this.oldRegistry, this.alwaysOutputDefaultValueFields, this.includingDefaultValueFields, true, this.omittingInsignificantWhitespace, this.printingEnumsAsInts, this.sortingMapKeys);
        }

        public String print(MessageOrBuilder messageOrBuilder) throws InvalidProtocolBufferException {
            try {
                StringBuilder sb = new StringBuilder();
                appendTo(messageOrBuilder, sb);
                return sb.toString();
            } catch (InvalidProtocolBufferException e2) {
                throw e2;
            } catch (IOException e3) {
                throw new IllegalStateException(e3);
            }
        }

        public Printer printingEnumsAsInts() {
            checkUnsetPrintingEnumsAsInts();
            return new Printer(this.registry, this.oldRegistry, this.alwaysOutputDefaultValueFields, this.includingDefaultValueFields, this.preservingProtoFieldNames, this.omittingInsignificantWhitespace, true, this.sortingMapKeys);
        }

        public Printer sortingMapKeys() {
            return new Printer(this.registry, this.oldRegistry, this.alwaysOutputDefaultValueFields, this.includingDefaultValueFields, this.preservingProtoFieldNames, this.omittingInsignificantWhitespace, this.printingEnumsAsInts, true);
        }

        public Printer usingTypeRegistry(TypeRegistry typeRegistry) {
            if (this.oldRegistry == TypeRegistry.getEmptyTypeRegistry() && this.registry == com.google.protobuf.TypeRegistry.getEmptyTypeRegistry()) {
                return new Printer(com.google.protobuf.TypeRegistry.getEmptyTypeRegistry(), typeRegistry, this.alwaysOutputDefaultValueFields, this.includingDefaultValueFields, this.preservingProtoFieldNames, this.omittingInsignificantWhitespace, this.printingEnumsAsInts, this.sortingMapKeys);
            }
            throw new IllegalArgumentException("Only one registry is allowed.");
        }

        private Printer(com.google.protobuf.TypeRegistry typeRegistry, TypeRegistry typeRegistry2, boolean z2, Set<Descriptors.FieldDescriptor> set, boolean z3, boolean z4, boolean z5, boolean z6) {
            this.registry = typeRegistry;
            this.oldRegistry = typeRegistry2;
            this.alwaysOutputDefaultValueFields = z2;
            this.includingDefaultValueFields = set;
            this.preservingProtoFieldNames = z3;
            this.omittingInsignificantWhitespace = z4;
            this.printingEnumsAsInts = z5;
            this.sortingMapKeys = z6;
        }

        public Printer includingDefaultValueFields(Set<Descriptors.FieldDescriptor> set) {
            Preconditions.checkArgument((set == null || set.isEmpty()) ? false : true, "Non-empty Set must be supplied for includingDefaultValueFields.");
            checkUnsetIncludingDefaultValueFields();
            return new Printer(this.registry, this.oldRegistry, false, Collections.unmodifiableSet(new HashSet(set)), this.preservingProtoFieldNames, this.omittingInsignificantWhitespace, this.printingEnumsAsInts, this.sortingMapKeys);
        }

        public Printer usingTypeRegistry(com.google.protobuf.TypeRegistry typeRegistry) {
            if (this.oldRegistry == TypeRegistry.getEmptyTypeRegistry() && this.registry == com.google.protobuf.TypeRegistry.getEmptyTypeRegistry()) {
                return new Printer(typeRegistry, this.oldRegistry, this.alwaysOutputDefaultValueFields, this.includingDefaultValueFields, this.preservingProtoFieldNames, this.omittingInsignificantWhitespace, this.printingEnumsAsInts, this.sortingMapKeys);
            }
            throw new IllegalArgumentException("Only one registry is allowed.");
        }
    }

    private static final class PrinterImpl {
        private static final Map<String, WellKnownTypePrinter> wellKnownTypePrinters = buildWellKnownTypePrinters();
        private final boolean alwaysOutputDefaultValueFields;
        private final CharSequence blankOrNewLine;
        private final CharSequence blankOrSpace;
        private final TextGenerator generator;
        private final Gson gson = GsonHolder.DEFAULT_GSON;
        private final Set<Descriptors.FieldDescriptor> includingDefaultValueFields;
        private final TypeRegistry oldRegistry;
        private final boolean preservingProtoFieldNames;
        private final boolean printingEnumsAsInts;
        private final com.google.protobuf.TypeRegistry registry;
        private final boolean sortingMapKeys;

        private static class GsonHolder {
            private static final Gson DEFAULT_GSON = new GsonBuilder().create();

            private GsonHolder() {
            }
        }

        private interface WellKnownTypePrinter {
            void print(PrinterImpl printerImpl, MessageOrBuilder messageOrBuilder) throws IOException;
        }

        PrinterImpl(com.google.protobuf.TypeRegistry typeRegistry, TypeRegistry typeRegistry2, boolean z2, Set set, boolean z3, Appendable appendable, boolean z4, boolean z5, boolean z6) {
            this.registry = typeRegistry;
            this.oldRegistry = typeRegistry2;
            this.alwaysOutputDefaultValueFields = z2;
            this.includingDefaultValueFields = set;
            this.preservingProtoFieldNames = z3;
            this.printingEnumsAsInts = z5;
            this.sortingMapKeys = z6;
            AnonymousClass1 anonymousClass1 = null;
            if (z4) {
                this.generator = new CompactTextGenerator(appendable, anonymousClass1);
                this.blankOrSpace = "";
                this.blankOrNewLine = "";
            } else {
                this.generator = new PrettyTextGenerator(appendable, anonymousClass1);
                this.blankOrSpace = " ";
                this.blankOrNewLine = "\n";
            }
        }

        private static Map<String, WellKnownTypePrinter> buildWellKnownTypePrinters() {
            HashMap map = new HashMap();
            map.put(Any.getDescriptor().getFullName(), new WellKnownTypePrinter() { // from class: com.google.protobuf.util.JsonFormat.PrinterImpl.1
                @Override // com.google.protobuf.util.JsonFormat.PrinterImpl.WellKnownTypePrinter
                public void print(PrinterImpl printerImpl, MessageOrBuilder messageOrBuilder) throws IOException {
                    printerImpl.printAny(messageOrBuilder);
                }
            });
            WellKnownTypePrinter wellKnownTypePrinter = new WellKnownTypePrinter() { // from class: com.google.protobuf.util.JsonFormat.PrinterImpl.2
                @Override // com.google.protobuf.util.JsonFormat.PrinterImpl.WellKnownTypePrinter
                public void print(PrinterImpl printerImpl, MessageOrBuilder messageOrBuilder) throws IOException {
                    printerImpl.printWrapper(messageOrBuilder);
                }
            };
            map.put(BoolValue.getDescriptor().getFullName(), wellKnownTypePrinter);
            map.put(Int32Value.getDescriptor().getFullName(), wellKnownTypePrinter);
            map.put(UInt32Value.getDescriptor().getFullName(), wellKnownTypePrinter);
            map.put(Int64Value.getDescriptor().getFullName(), wellKnownTypePrinter);
            map.put(UInt64Value.getDescriptor().getFullName(), wellKnownTypePrinter);
            map.put(StringValue.getDescriptor().getFullName(), wellKnownTypePrinter);
            map.put(BytesValue.getDescriptor().getFullName(), wellKnownTypePrinter);
            map.put(FloatValue.getDescriptor().getFullName(), wellKnownTypePrinter);
            map.put(DoubleValue.getDescriptor().getFullName(), wellKnownTypePrinter);
            map.put(Timestamp.getDescriptor().getFullName(), new WellKnownTypePrinter() { // from class: com.google.protobuf.util.JsonFormat.PrinterImpl.3
                @Override // com.google.protobuf.util.JsonFormat.PrinterImpl.WellKnownTypePrinter
                public void print(PrinterImpl printerImpl, MessageOrBuilder messageOrBuilder) throws IOException {
                    printerImpl.printTimestamp(messageOrBuilder);
                }
            });
            map.put(Duration.getDescriptor().getFullName(), new WellKnownTypePrinter() { // from class: com.google.protobuf.util.JsonFormat.PrinterImpl.4
                @Override // com.google.protobuf.util.JsonFormat.PrinterImpl.WellKnownTypePrinter
                public void print(PrinterImpl printerImpl, MessageOrBuilder messageOrBuilder) throws IOException {
                    printerImpl.printDuration(messageOrBuilder);
                }
            });
            map.put(FieldMask.getDescriptor().getFullName(), new WellKnownTypePrinter() { // from class: com.google.protobuf.util.JsonFormat.PrinterImpl.5
                @Override // com.google.protobuf.util.JsonFormat.PrinterImpl.WellKnownTypePrinter
                public void print(PrinterImpl printerImpl, MessageOrBuilder messageOrBuilder) throws IOException {
                    printerImpl.printFieldMask(messageOrBuilder);
                }
            });
            map.put(Struct.getDescriptor().getFullName(), new WellKnownTypePrinter() { // from class: com.google.protobuf.util.JsonFormat.PrinterImpl.6
                @Override // com.google.protobuf.util.JsonFormat.PrinterImpl.WellKnownTypePrinter
                public void print(PrinterImpl printerImpl, MessageOrBuilder messageOrBuilder) throws IOException {
                    printerImpl.printStruct(messageOrBuilder);
                }
            });
            map.put(Value.getDescriptor().getFullName(), new WellKnownTypePrinter() { // from class: com.google.protobuf.util.JsonFormat.PrinterImpl.7
                @Override // com.google.protobuf.util.JsonFormat.PrinterImpl.WellKnownTypePrinter
                public void print(PrinterImpl printerImpl, MessageOrBuilder messageOrBuilder) throws IOException {
                    printerImpl.printValue(messageOrBuilder);
                }
            });
            map.put(ListValue.getDescriptor().getFullName(), new WellKnownTypePrinter() { // from class: com.google.protobuf.util.JsonFormat.PrinterImpl.8
                @Override // com.google.protobuf.util.JsonFormat.PrinterImpl.WellKnownTypePrinter
                public void print(PrinterImpl printerImpl, MessageOrBuilder messageOrBuilder) throws IOException {
                    printerImpl.printListValue(messageOrBuilder);
                }
            });
            return map;
        }

        private void print(MessageOrBuilder messageOrBuilder, @Nullable String str) throws IOException {
            boolean z2;
            Map<Descriptors.FieldDescriptor, Object> allFields;
            this.generator.print("{" + ((Object) this.blankOrNewLine));
            this.generator.indent();
            if (str != null) {
                this.generator.print("\"@type\":" + ((Object) this.blankOrSpace) + this.gson.toJson(str));
                z2 = true;
            } else {
                z2 = false;
            }
            if (this.alwaysOutputDefaultValueFields || !this.includingDefaultValueFields.isEmpty()) {
                TreeMap treeMap = new TreeMap(messageOrBuilder.getAllFields());
                for (Descriptors.FieldDescriptor fieldDescriptor : messageOrBuilder.getDescriptorForType().getFields()) {
                    if (fieldDescriptor.isOptional()) {
                        if (fieldDescriptor.getJavaType() != Descriptors.FieldDescriptor.JavaType.MESSAGE || messageOrBuilder.hasField(fieldDescriptor)) {
                            if (fieldDescriptor.getContainingOneof() == null || messageOrBuilder.hasField(fieldDescriptor)) {
                            }
                        }
                    }
                    if (!treeMap.containsKey(fieldDescriptor) && (this.alwaysOutputDefaultValueFields || this.includingDefaultValueFields.contains(fieldDescriptor))) {
                        treeMap.put(fieldDescriptor, messageOrBuilder.getField(fieldDescriptor));
                    }
                }
                allFields = treeMap;
            } else {
                allFields = messageOrBuilder.getAllFields();
            }
            for (Map.Entry<Descriptors.FieldDescriptor, Object> entry : allFields.entrySet()) {
                if (z2) {
                    this.generator.print("," + ((Object) this.blankOrNewLine));
                } else {
                    z2 = true;
                }
                printField(entry.getKey(), entry.getValue());
            }
            if (z2) {
                this.generator.print(this.blankOrNewLine);
            }
            this.generator.outdent();
            this.generator.print(i.f9804d);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void printAny(MessageOrBuilder messageOrBuilder) throws IOException {
            if (Any.getDefaultInstance().equals(messageOrBuilder)) {
                this.generator.print("{}");
                return;
            }
            Descriptors.Descriptor descriptorForType = messageOrBuilder.getDescriptorForType();
            Descriptors.FieldDescriptor fieldDescriptorFindFieldByName = descriptorForType.findFieldByName("type_url");
            Descriptors.FieldDescriptor fieldDescriptorFindFieldByName2 = descriptorForType.findFieldByName("value");
            if (fieldDescriptorFindFieldByName == null || fieldDescriptorFindFieldByName2 == null || fieldDescriptorFindFieldByName.getType() != Descriptors.FieldDescriptor.Type.STRING || fieldDescriptorFindFieldByName2.getType() != Descriptors.FieldDescriptor.Type.BYTES) {
                throw new InvalidProtocolBufferException("Invalid Any type.");
            }
            String str = (String) messageOrBuilder.getField(fieldDescriptorFindFieldByName);
            Descriptors.Descriptor descriptorForTypeUrl = this.registry.getDescriptorForTypeUrl(str);
            if (descriptorForTypeUrl == null && (descriptorForTypeUrl = this.oldRegistry.a(str)) == null) {
                throw new InvalidProtocolBufferException("Cannot find type for url: " + str);
            }
            DynamicMessage from = DynamicMessage.getDefaultInstance(descriptorForTypeUrl).getParserForType().parseFrom((ByteString) messageOrBuilder.getField(fieldDescriptorFindFieldByName2));
            WellKnownTypePrinter wellKnownTypePrinter = wellKnownTypePrinters.get(JsonFormat.getTypeName(str));
            if (wellKnownTypePrinter == null) {
                print(from, str);
                return;
            }
            this.generator.print("{" + ((Object) this.blankOrNewLine));
            this.generator.indent();
            this.generator.print("\"@type\":" + ((Object) this.blankOrSpace) + this.gson.toJson(str) + "," + ((Object) this.blankOrNewLine));
            TextGenerator textGenerator = this.generator;
            StringBuilder sb = new StringBuilder();
            sb.append("\"value\":");
            sb.append((Object) this.blankOrSpace);
            textGenerator.print(sb.toString());
            wellKnownTypePrinter.print(this, from);
            this.generator.print(this.blankOrNewLine);
            this.generator.outdent();
            this.generator.print(i.f9804d);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void printDuration(MessageOrBuilder messageOrBuilder) throws IOException {
            Duration from = Duration.parseFrom(toByteString(messageOrBuilder));
            this.generator.print("\"" + Durations.toString(from) + "\"");
        }

        private void printField(Descriptors.FieldDescriptor fieldDescriptor, Object obj) throws IOException {
            if (this.preservingProtoFieldNames) {
                this.generator.print("\"" + fieldDescriptor.getName() + "\":" + ((Object) this.blankOrSpace));
            } else {
                this.generator.print("\"" + fieldDescriptor.getJsonName() + "\":" + ((Object) this.blankOrSpace));
            }
            if (fieldDescriptor.isMapField()) {
                printMapFieldValue(fieldDescriptor, obj);
            } else if (fieldDescriptor.isRepeated()) {
                printRepeatedFieldValue(fieldDescriptor, obj);
            } else {
                printSingleFieldValue(fieldDescriptor, obj);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void printFieldMask(MessageOrBuilder messageOrBuilder) throws IOException {
            FieldMask from = FieldMask.parseFrom(toByteString(messageOrBuilder));
            this.generator.print("\"" + FieldMaskUtil.toJsonString(from) + "\"");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void printListValue(MessageOrBuilder messageOrBuilder) throws IOException {
            Descriptors.FieldDescriptor fieldDescriptorFindFieldByName = messageOrBuilder.getDescriptorForType().findFieldByName("values");
            if (fieldDescriptorFindFieldByName == null) {
                throw new InvalidProtocolBufferException("Invalid ListValue type.");
            }
            printRepeatedFieldValue(fieldDescriptorFindFieldByName, messageOrBuilder.getField(fieldDescriptorFindFieldByName));
        }

        private void printMapFieldValue(Descriptors.FieldDescriptor fieldDescriptor, Object obj) throws IOException {
            Descriptors.Descriptor messageType = fieldDescriptor.getMessageType();
            Descriptors.FieldDescriptor fieldDescriptorFindFieldByName = messageType.findFieldByName(KsProperty.Key);
            Descriptors.FieldDescriptor fieldDescriptorFindFieldByName2 = messageType.findFieldByName("value");
            if (fieldDescriptorFindFieldByName == null || fieldDescriptorFindFieldByName2 == null) {
                throw new InvalidProtocolBufferException("Invalid map field.");
            }
            this.generator.print("{" + ((Object) this.blankOrNewLine));
            this.generator.indent();
            Collection<Message> collectionValues = (List) obj;
            if (this.sortingMapKeys && !collectionValues.isEmpty()) {
                TreeMap treeMap = new TreeMap(fieldDescriptorFindFieldByName.getType() == Descriptors.FieldDescriptor.Type.STRING ? new Comparator<Object>() { // from class: com.google.protobuf.util.JsonFormat.PrinterImpl.9
                    @Override // java.util.Comparator
                    public int compare(Object obj2, Object obj3) {
                        return ByteString.unsignedLexicographicalComparator().compare(ByteString.copyFromUtf8((String) obj2), ByteString.copyFromUtf8((String) obj3));
                    }
                } : null);
                for (Object obj2 : collectionValues) {
                    treeMap.put(((Message) obj2).getField(fieldDescriptorFindFieldByName), obj2);
                }
                collectionValues = treeMap.values();
            }
            boolean z2 = false;
            for (Message message : collectionValues) {
                Object field = message.getField(fieldDescriptorFindFieldByName);
                Object field2 = message.getField(fieldDescriptorFindFieldByName2);
                if (z2) {
                    this.generator.print("," + ((Object) this.blankOrNewLine));
                } else {
                    z2 = true;
                }
                printSingleFieldValue(fieldDescriptorFindFieldByName, field, true);
                this.generator.print(":" + ((Object) this.blankOrSpace));
                printSingleFieldValue(fieldDescriptorFindFieldByName2, field2);
            }
            if (z2) {
                this.generator.print(this.blankOrNewLine);
            }
            this.generator.outdent();
            this.generator.print(i.f9804d);
        }

        private void printRepeatedFieldValue(Descriptors.FieldDescriptor fieldDescriptor, Object obj) throws IOException {
            this.generator.print("[");
            boolean z2 = false;
            for (Object obj2 : (List) obj) {
                if (z2) {
                    this.generator.print("," + ((Object) this.blankOrSpace));
                } else {
                    z2 = true;
                }
                printSingleFieldValue(fieldDescriptor, obj2);
            }
            this.generator.print("]");
        }

        private void printSingleFieldValue(Descriptors.FieldDescriptor fieldDescriptor, Object obj) throws IOException {
            printSingleFieldValue(fieldDescriptor, obj, false);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void printStruct(MessageOrBuilder messageOrBuilder) throws IOException {
            Descriptors.FieldDescriptor fieldDescriptorFindFieldByName = messageOrBuilder.getDescriptorForType().findFieldByName(GraphRequest.FIELDS_PARAM);
            if (fieldDescriptorFindFieldByName == null) {
                throw new InvalidProtocolBufferException("Invalid Struct type.");
            }
            printMapFieldValue(fieldDescriptorFindFieldByName, messageOrBuilder.getField(fieldDescriptorFindFieldByName));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void printTimestamp(MessageOrBuilder messageOrBuilder) throws IOException {
            Timestamp from = Timestamp.parseFrom(toByteString(messageOrBuilder));
            this.generator.print("\"" + Timestamps.toString(from) + "\"");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void printValue(MessageOrBuilder messageOrBuilder) throws IOException {
            Map<Descriptors.FieldDescriptor, Object> allFields = messageOrBuilder.getAllFields();
            if (allFields.isEmpty()) {
                this.generator.print(TmpConstant.GROUP_ROLE_UNKNOWN);
                return;
            }
            if (allFields.size() != 1) {
                throw new InvalidProtocolBufferException("Invalid Value type.");
            }
            for (Map.Entry<Descriptors.FieldDescriptor, Object> entry : allFields.entrySet()) {
                Descriptors.FieldDescriptor key = entry.getKey();
                if (key.getType() == Descriptors.FieldDescriptor.Type.DOUBLE) {
                    Double d2 = (Double) entry.getValue();
                    if (d2.isNaN() || d2.isInfinite()) {
                        throw new IllegalArgumentException("google.protobuf.Value cannot encode double values for infinity or nan, because they would be parsed as a string.");
                    }
                }
                printSingleFieldValue(key, entry.getValue());
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void printWrapper(MessageOrBuilder messageOrBuilder) throws IOException {
            Descriptors.FieldDescriptor fieldDescriptorFindFieldByName = messageOrBuilder.getDescriptorForType().findFieldByName("value");
            if (fieldDescriptorFindFieldByName == null) {
                throw new InvalidProtocolBufferException("Invalid Wrapper type.");
            }
            printSingleFieldValue(fieldDescriptorFindFieldByName, messageOrBuilder.getField(fieldDescriptorFindFieldByName));
        }

        private ByteString toByteString(MessageOrBuilder messageOrBuilder) {
            return messageOrBuilder instanceof Message ? ((Message) messageOrBuilder).toByteString() : ((Message.Builder) messageOrBuilder).build().toByteString();
        }

        void i(MessageOrBuilder messageOrBuilder) throws IOException {
            WellKnownTypePrinter wellKnownTypePrinter = wellKnownTypePrinters.get(messageOrBuilder.getDescriptorForType().getFullName());
            if (wellKnownTypePrinter != null) {
                wellKnownTypePrinter.print(this, messageOrBuilder);
            } else {
                print(messageOrBuilder, null);
            }
        }

        private void printSingleFieldValue(Descriptors.FieldDescriptor fieldDescriptor, Object obj, boolean z2) throws IOException {
            switch (AnonymousClass1.f15392a[fieldDescriptor.getType().ordinal()]) {
                case 1:
                case 2:
                case 3:
                    if (z2) {
                        this.generator.print("\"");
                    }
                    this.generator.print(((Integer) obj).toString());
                    if (z2) {
                        this.generator.print("\"");
                        break;
                    }
                    break;
                case 4:
                case 5:
                case 6:
                    this.generator.print("\"" + ((Long) obj).toString() + "\"");
                    break;
                case 7:
                    if (z2) {
                        this.generator.print("\"");
                    }
                    if (((Boolean) obj).booleanValue()) {
                        this.generator.print("true");
                    } else {
                        this.generator.print(RequestConstant.FALSE);
                    }
                    if (z2) {
                        this.generator.print("\"");
                        break;
                    }
                    break;
                case 8:
                    Float f2 = (Float) obj;
                    if (!f2.isNaN()) {
                        if (!f2.isInfinite()) {
                            if (z2) {
                                this.generator.print("\"");
                            }
                            this.generator.print(f2.toString());
                            if (z2) {
                                this.generator.print("\"");
                                break;
                            }
                        } else if (f2.floatValue() >= 0.0f) {
                            this.generator.print("\"Infinity\"");
                            break;
                        } else {
                            this.generator.print("\"-Infinity\"");
                            break;
                        }
                    } else {
                        this.generator.print("\"NaN\"");
                        break;
                    }
                    break;
                case 9:
                    Double d2 = (Double) obj;
                    if (!d2.isNaN()) {
                        if (!d2.isInfinite()) {
                            if (z2) {
                                this.generator.print("\"");
                            }
                            this.generator.print(d2.toString());
                            if (z2) {
                                this.generator.print("\"");
                                break;
                            }
                        } else if (d2.doubleValue() >= 0.0d) {
                            this.generator.print("\"Infinity\"");
                            break;
                        } else {
                            this.generator.print("\"-Infinity\"");
                            break;
                        }
                    } else {
                        this.generator.print("\"NaN\"");
                        break;
                    }
                    break;
                case 10:
                case 11:
                    if (z2) {
                        this.generator.print("\"");
                    }
                    this.generator.print(JsonFormat.unsignedToString(((Integer) obj).intValue()));
                    if (z2) {
                        this.generator.print("\"");
                        break;
                    }
                    break;
                case 12:
                case 13:
                    this.generator.print("\"" + JsonFormat.unsignedToString(((Long) obj).longValue()) + "\"");
                    break;
                case 14:
                    this.generator.print(this.gson.toJson(obj));
                    break;
                case 15:
                    this.generator.print("\"");
                    this.generator.print(BaseEncoding.base64().encode(((ByteString) obj).toByteArray()));
                    this.generator.print("\"");
                    break;
                case 16:
                    if (!fieldDescriptor.getEnumType().getFullName().equals("google.protobuf.NullValue")) {
                        if (!this.printingEnumsAsInts) {
                            Descriptors.EnumValueDescriptor enumValueDescriptor = (Descriptors.EnumValueDescriptor) obj;
                            if (enumValueDescriptor.getIndex() != -1) {
                                this.generator.print("\"" + enumValueDescriptor.getName() + "\"");
                                break;
                            }
                        }
                        this.generator.print(String.valueOf(((Descriptors.EnumValueDescriptor) obj).getNumber()));
                        break;
                    } else {
                        if (z2) {
                            this.generator.print("\"");
                        }
                        this.generator.print(TmpConstant.GROUP_ROLE_UNKNOWN);
                        if (z2) {
                            this.generator.print("\"");
                            break;
                        }
                    }
                    break;
                case 17:
                case 18:
                    i((Message) obj);
                    break;
            }
        }
    }

    interface TextGenerator {
        void indent();

        void outdent();

        void print(CharSequence charSequence) throws IOException;
    }

    public static class TypeRegistry {
        private final Map<String, Descriptors.Descriptor> types;

        public static class Builder {
            private boolean built;
            private final Set<String> files;
            private final Map<String, Descriptors.Descriptor> types;

            /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
                this();
            }

            private void addFile(Descriptors.FileDescriptor fileDescriptor) {
                if (this.files.add(fileDescriptor.getFullName())) {
                    Iterator<Descriptors.FileDescriptor> it = fileDescriptor.getDependencies().iterator();
                    while (it.hasNext()) {
                        addFile(it.next());
                    }
                    Iterator<Descriptors.Descriptor> it2 = fileDescriptor.getMessageTypes().iterator();
                    while (it2.hasNext()) {
                        addMessage(it2.next());
                    }
                }
            }

            private void addMessage(Descriptors.Descriptor descriptor) {
                Iterator<Descriptors.Descriptor> it = descriptor.getNestedTypes().iterator();
                while (it.hasNext()) {
                    addMessage(it.next());
                }
                if (!this.types.containsKey(descriptor.getFullName())) {
                    this.types.put(descriptor.getFullName(), descriptor);
                    return;
                }
                JsonFormat.logger.warning("Type " + descriptor.getFullName() + " is added multiple times.");
            }

            @CanIgnoreReturnValue
            public Builder add(Descriptors.Descriptor descriptor) {
                if (this.built) {
                    throw new IllegalStateException("A TypeRegistry.Builder can only be used once.");
                }
                addFile(descriptor.getFile());
                return this;
            }

            public TypeRegistry build() {
                this.built = true;
                return new TypeRegistry(this.types, null);
            }

            private Builder() {
                this.files = new HashSet();
                this.types = new HashMap();
                this.built = false;
            }

            @CanIgnoreReturnValue
            public Builder add(Iterable<Descriptors.Descriptor> iterable) {
                if (!this.built) {
                    Iterator<Descriptors.Descriptor> it = iterable.iterator();
                    while (it.hasNext()) {
                        addFile(it.next().getFile());
                    }
                    return this;
                }
                throw new IllegalStateException("A TypeRegistry.Builder can only be used once.");
            }
        }

        private static class EmptyTypeRegistryHolder {
            private static final TypeRegistry EMPTY = new TypeRegistry(Collections.emptyMap(), null);

            private EmptyTypeRegistryHolder() {
            }
        }

        /* synthetic */ TypeRegistry(Map map, AnonymousClass1 anonymousClass1) {
            this(map);
        }

        public static TypeRegistry getEmptyTypeRegistry() {
            return EmptyTypeRegistryHolder.EMPTY;
        }

        public static Builder newBuilder() {
            return new Builder(null);
        }

        Descriptors.Descriptor a(String str) {
            return find(JsonFormat.getTypeName(str));
        }

        @Nullable
        public Descriptors.Descriptor find(String str) {
            return this.types.get(str);
        }

        private TypeRegistry(Map<String, Descriptors.Descriptor> map) {
            this.types = map;
        }
    }

    private JsonFormat() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getTypeName(String str) throws InvalidProtocolBufferException {
        String[] strArrSplit = str.split("/");
        if (strArrSplit.length != 1) {
            return strArrSplit[strArrSplit.length - 1];
        }
        throw new InvalidProtocolBufferException("Invalid type url found: " + str);
    }

    public static Parser parser() {
        return new Parser(com.google.protobuf.TypeRegistry.getEmptyTypeRegistry(), TypeRegistry.getEmptyTypeRegistry(), false, 100, null);
    }

    public static Printer printer() {
        return new Printer(com.google.protobuf.TypeRegistry.getEmptyTypeRegistry(), TypeRegistry.getEmptyTypeRegistry(), false, Collections.emptySet(), false, false, false, false, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String unsignedToString(int i2) {
        return i2 >= 0 ? Integer.toString(i2) : Long.toString(i2 & 4294967295L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String unsignedToString(long j2) {
        if (j2 >= 0) {
            return Long.toString(j2);
        }
        return BigInteger.valueOf(j2 & Long.MAX_VALUE).setBit(63).toString();
    }
}
