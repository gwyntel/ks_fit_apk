package com.google.protobuf;

import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.google.protobuf.Descriptors;
import com.google.protobuf.Internal;
import com.google.protobuf.StructuralMessageInfo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
final class DescriptorMessageInfoFactory implements MessageInfoFactory {
    private static final String GET_DEFAULT_INSTANCE_METHOD_NAME = "getDefaultInstance";

    /* renamed from: a, reason: collision with root package name */
    public static final /* synthetic */ int f15196a = 0;
    private static final DescriptorMessageInfoFactory instance = new DescriptorMessageInfoFactory();
    private static final Set<String> specialFieldNames = new HashSet(Arrays.asList("Class", "DefaultInstanceForType", "ParserForType", "SerializedSize", "AllFields", "DescriptorForType", "InitializationErrorString", "UnknownFields", "CachedSize"));
    private static IsInitializedCheckAnalyzer isInitializedCheckAnalyzer = new IsInitializedCheckAnalyzer();

    /* renamed from: com.google.protobuf.DescriptorMessageInfoFactory$3, reason: invalid class name */
    static /* synthetic */ class AnonymousClass3 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f15199a;

        /* renamed from: b, reason: collision with root package name */
        static final /* synthetic */ int[] f15200b;

        /* renamed from: c, reason: collision with root package name */
        static final /* synthetic */ int[] f15201c;

        static {
            int[] iArr = new int[Descriptors.FieldDescriptor.Type.values().length];
            f15201c = iArr;
            try {
                iArr[Descriptors.FieldDescriptor.Type.BOOL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f15201c[Descriptors.FieldDescriptor.Type.BYTES.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f15201c[Descriptors.FieldDescriptor.Type.DOUBLE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f15201c[Descriptors.FieldDescriptor.Type.ENUM.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f15201c[Descriptors.FieldDescriptor.Type.FIXED32.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f15201c[Descriptors.FieldDescriptor.Type.FIXED64.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f15201c[Descriptors.FieldDescriptor.Type.FLOAT.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f15201c[Descriptors.FieldDescriptor.Type.GROUP.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                f15201c[Descriptors.FieldDescriptor.Type.INT32.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                f15201c[Descriptors.FieldDescriptor.Type.INT64.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                f15201c[Descriptors.FieldDescriptor.Type.MESSAGE.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                f15201c[Descriptors.FieldDescriptor.Type.SFIXED32.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                f15201c[Descriptors.FieldDescriptor.Type.SFIXED64.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                f15201c[Descriptors.FieldDescriptor.Type.SINT32.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                f15201c[Descriptors.FieldDescriptor.Type.SINT64.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                f15201c[Descriptors.FieldDescriptor.Type.STRING.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                f15201c[Descriptors.FieldDescriptor.Type.UINT32.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                f15201c[Descriptors.FieldDescriptor.Type.UINT64.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            int[] iArr2 = new int[JavaType.values().length];
            f15200b = iArr2;
            try {
                iArr2[JavaType.BOOLEAN.ordinal()] = 1;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                f15200b[JavaType.BYTE_STRING.ordinal()] = 2;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                f15200b[JavaType.DOUBLE.ordinal()] = 3;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                f15200b[JavaType.FLOAT.ordinal()] = 4;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                f15200b[JavaType.ENUM.ordinal()] = 5;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                f15200b[JavaType.INT.ordinal()] = 6;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                f15200b[JavaType.LONG.ordinal()] = 7;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                f15200b[JavaType.STRING.ordinal()] = 8;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                f15200b[JavaType.MESSAGE.ordinal()] = 9;
            } catch (NoSuchFieldError unused27) {
            }
            int[] iArr3 = new int[Descriptors.FileDescriptor.Syntax.values().length];
            f15199a = iArr3;
            try {
                iArr3[Descriptors.FileDescriptor.Syntax.PROTO2.ordinal()] = 1;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                f15199a[Descriptors.FileDescriptor.Syntax.PROTO3.ordinal()] = 2;
            } catch (NoSuchFieldError unused29) {
            }
        }
    }

    static class IsInitializedCheckAnalyzer {
        private final Map<Descriptors.Descriptor, Boolean> resultCache = new ConcurrentHashMap();
        private int index = 0;
        private final Stack<Node> stack = new Stack<>();
        private final Map<Descriptors.Descriptor, Node> nodeCache = new HashMap();

        private static class Node {

            /* renamed from: a, reason: collision with root package name */
            final Descriptors.Descriptor f15202a;

            /* renamed from: b, reason: collision with root package name */
            final int f15203b;

            /* renamed from: c, reason: collision with root package name */
            int f15204c;

            /* renamed from: d, reason: collision with root package name */
            StronglyConnectedComponent f15205d = null;

            Node(Descriptors.Descriptor descriptor, int i2) {
                this.f15202a = descriptor;
                this.f15203b = i2;
                this.f15204c = i2;
            }
        }

        private static class StronglyConnectedComponent {

            /* renamed from: a, reason: collision with root package name */
            final List f15206a;

            /* renamed from: b, reason: collision with root package name */
            boolean f15207b;

            private StronglyConnectedComponent() {
                this.f15206a = new ArrayList();
                this.f15207b = false;
            }
        }

        IsInitializedCheckAnalyzer() {
        }

        private void analyze(StronglyConnectedComponent stronglyConnectedComponent) {
            boolean z2;
            StronglyConnectedComponent stronglyConnectedComponent2;
            Iterator it = stronglyConnectedComponent.f15206a.iterator();
            loop0: while (true) {
                if (!it.hasNext()) {
                    z2 = false;
                    break;
                }
                Descriptors.Descriptor descriptor = (Descriptors.Descriptor) it.next();
                z2 = true;
                if (descriptor.isExtendable()) {
                    break;
                }
                for (Descriptors.FieldDescriptor fieldDescriptor : descriptor.getFields()) {
                    if (fieldDescriptor.isRequired() || (fieldDescriptor.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE && (stronglyConnectedComponent2 = this.nodeCache.get(fieldDescriptor.getMessageType()).f15205d) != stronglyConnectedComponent && stronglyConnectedComponent2.f15207b)) {
                        break loop0;
                    }
                }
            }
            stronglyConnectedComponent.f15207b = z2;
            Iterator it2 = stronglyConnectedComponent.f15206a.iterator();
            while (it2.hasNext()) {
                this.resultCache.put((Descriptors.Descriptor) it2.next(), Boolean.valueOf(stronglyConnectedComponent.f15207b));
            }
        }

        private Node dfs(Descriptors.Descriptor descriptor) {
            Node nodePop;
            int i2 = this.index;
            this.index = i2 + 1;
            Node node = new Node(descriptor, i2);
            this.stack.push(node);
            this.nodeCache.put(descriptor, node);
            for (Descriptors.FieldDescriptor fieldDescriptor : descriptor.getFields()) {
                if (fieldDescriptor.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                    Node node2 = this.nodeCache.get(fieldDescriptor.getMessageType());
                    if (node2 == null) {
                        node.f15204c = Math.min(node.f15204c, dfs(fieldDescriptor.getMessageType()).f15204c);
                    } else if (node2.f15205d == null) {
                        node.f15204c = Math.min(node.f15204c, node2.f15204c);
                    }
                }
            }
            if (node.f15203b == node.f15204c) {
                StronglyConnectedComponent stronglyConnectedComponent = new StronglyConnectedComponent();
                do {
                    nodePop = this.stack.pop();
                    nodePop.f15205d = stronglyConnectedComponent;
                    stronglyConnectedComponent.f15206a.add(nodePop.f15202a);
                } while (nodePop != node);
                analyze(stronglyConnectedComponent);
            }
            return node;
        }

        public boolean needsIsInitializedCheck(Descriptors.Descriptor descriptor) {
            Boolean bool = this.resultCache.get(descriptor);
            if (bool != null) {
                return bool.booleanValue();
            }
            synchronized (this) {
                try {
                    Boolean bool2 = this.resultCache.get(descriptor);
                    if (bool2 != null) {
                        return bool2.booleanValue();
                    }
                    return dfs(descriptor).f15205d.f15207b;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    private static final class OneofState {
        private OneofInfo[] oneofs;

        private OneofState() {
            this.oneofs = new OneofInfo[2];
        }

        private static OneofInfo newInfo(Class<?> cls, Descriptors.OneofDescriptor oneofDescriptor) {
            String strSnakeCaseToLowerCamelCase = DescriptorMessageInfoFactory.snakeCaseToLowerCamelCase(oneofDescriptor.getName());
            return new OneofInfo(oneofDescriptor.getIndex(), DescriptorMessageInfoFactory.field(cls, strSnakeCaseToLowerCamelCase + "Case_"), DescriptorMessageInfoFactory.field(cls, strSnakeCaseToLowerCamelCase + OpenAccountUIConstants.UNDER_LINE));
        }

        OneofInfo a(Class cls, Descriptors.OneofDescriptor oneofDescriptor) {
            int index = oneofDescriptor.getIndex();
            OneofInfo[] oneofInfoArr = this.oneofs;
            if (index >= oneofInfoArr.length) {
                this.oneofs = (OneofInfo[]) Arrays.copyOf(oneofInfoArr, index * 2);
            }
            OneofInfo oneofInfo = this.oneofs[index];
            if (oneofInfo != null) {
                return oneofInfo;
            }
            OneofInfo oneofInfoNewInfo = newInfo(cls, oneofDescriptor);
            this.oneofs[index] = oneofInfoNewInfo;
            return oneofInfoNewInfo;
        }
    }

    private DescriptorMessageInfoFactory() {
    }

    private static java.lang.reflect.Field bitField(Class<?> cls, int i2) {
        return field(cls, "bitField" + i2 + OpenAccountUIConstants.UNDER_LINE);
    }

    private static FieldInfo buildOneofMember(Class<?> cls, Descriptors.FieldDescriptor fieldDescriptor, OneofState oneofState, boolean z2, Internal.EnumVerifier enumVerifier) {
        OneofInfo oneofInfoA = oneofState.a(cls, fieldDescriptor.getContainingOneof());
        FieldType fieldType = getFieldType(fieldDescriptor);
        return FieldInfo.forOneofMemberField(fieldDescriptor.getNumber(), fieldType, oneofInfoA, getOneofStoredType(cls, fieldDescriptor, fieldType), z2, enumVerifier);
    }

    static String c(Descriptors.FieldDescriptor fieldDescriptor) {
        String name = fieldDescriptor.getType() == Descriptors.FieldDescriptor.Type.GROUP ? fieldDescriptor.getMessageType().getName() : fieldDescriptor.getName();
        return snakeCaseToLowerCamelCase(name) + (specialFieldNames.contains(snakeCaseToUpperCamelCase(name)) ? "__" : OpenAccountUIConstants.UNDER_LINE);
    }

    private static java.lang.reflect.Field cachedSizeField(Class<?> cls, Descriptors.FieldDescriptor fieldDescriptor) {
        return field(cls, getCachedSizeFieldName(fieldDescriptor));
    }

    private static MessageInfo convert(Class<?> cls, Descriptors.Descriptor descriptor) {
        int i2 = AnonymousClass3.f15199a[descriptor.getFile().getSyntax().ordinal()];
        if (i2 == 1) {
            return convertProto2(cls, descriptor);
        }
        if (i2 == 2) {
            return convertProto3(cls, descriptor);
        }
        throw new IllegalArgumentException("Unsupported syntax: " + descriptor.getFile().getSyntax());
    }

    private static StructuralMessageInfo convertProto2(Class<?> cls, Descriptors.Descriptor descriptor) {
        List<Descriptors.FieldDescriptor> fields = descriptor.getFields();
        StructuralMessageInfo.Builder builderNewBuilder = StructuralMessageInfo.newBuilder(fields.size());
        builderNewBuilder.withDefaultInstance(getDefaultInstance(cls));
        builderNewBuilder.withSyntax(ProtoSyntax.PROTO2);
        builderNewBuilder.withMessageSetWireFormat(descriptor.getOptions().getMessageSetWireFormat());
        AnonymousClass1 anonymousClass1 = null;
        OneofState oneofState = new OneofState();
        java.lang.reflect.Field fieldBitField = null;
        int i2 = 0;
        int i3 = 0;
        int i4 = 1;
        while (i2 < fields.size()) {
            final Descriptors.FieldDescriptor fieldDescriptor = fields.get(i2);
            boolean javaStringCheckUtf8 = fieldDescriptor.getFile().getOptions().getJavaStringCheckUtf8();
            Descriptors.FieldDescriptor.JavaType javaType = fieldDescriptor.getJavaType();
            Descriptors.FieldDescriptor.JavaType javaType2 = Descriptors.FieldDescriptor.JavaType.ENUM;
            Internal.EnumVerifier enumVerifier = javaType == javaType2 ? new Internal.EnumVerifier() { // from class: com.google.protobuf.DescriptorMessageInfoFactory.1
                @Override // com.google.protobuf.Internal.EnumVerifier
                public boolean isInRange(int i5) {
                    return fieldDescriptor.getEnumType().findValueByNumber(i5) != null;
                }
            } : anonymousClass1;
            if (fieldDescriptor.getContainingOneof() != null) {
                builderNewBuilder.withField(buildOneofMember(cls, fieldDescriptor, oneofState, javaStringCheckUtf8, enumVerifier));
            } else {
                java.lang.reflect.Field field = field(cls, fieldDescriptor);
                int number = fieldDescriptor.getNumber();
                FieldType fieldType = getFieldType(fieldDescriptor);
                if (fieldDescriptor.isMapField()) {
                    final Descriptors.FieldDescriptor fieldDescriptorFindFieldByNumber = fieldDescriptor.getMessageType().findFieldByNumber(2);
                    if (fieldDescriptorFindFieldByNumber.getJavaType() == javaType2) {
                        enumVerifier = new Internal.EnumVerifier() { // from class: com.google.protobuf.DescriptorMessageInfoFactory.2
                            @Override // com.google.protobuf.Internal.EnumVerifier
                            public boolean isInRange(int i5) {
                                return fieldDescriptorFindFieldByNumber.getEnumType().findValueByNumber(i5) != null;
                            }
                        };
                    }
                    builderNewBuilder.withField(FieldInfo.forMapField(field, number, SchemaUtil.B(cls, fieldDescriptor.getName()), enumVerifier));
                } else if (!fieldDescriptor.isRepeated()) {
                    if (fieldBitField == null) {
                        fieldBitField = bitField(cls, i3);
                    }
                    if (fieldDescriptor.isRequired()) {
                        builderNewBuilder.withField(FieldInfo.forProto2RequiredField(field, number, fieldType, fieldBitField, i4, javaStringCheckUtf8, enumVerifier));
                    } else {
                        builderNewBuilder.withField(FieldInfo.forProto2OptionalField(field, number, fieldType, fieldBitField, i4, javaStringCheckUtf8, enumVerifier));
                    }
                } else if (enumVerifier != null) {
                    if (fieldDescriptor.isPacked()) {
                        builderNewBuilder.withField(FieldInfo.forPackedFieldWithEnumVerifier(field, number, fieldType, enumVerifier, cachedSizeField(cls, fieldDescriptor)));
                    } else {
                        builderNewBuilder.withField(FieldInfo.forFieldWithEnumVerifier(field, number, fieldType, enumVerifier));
                    }
                } else if (fieldDescriptor.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                    builderNewBuilder.withField(FieldInfo.forRepeatedMessageField(field, number, fieldType, getTypeForRepeatedMessageField(cls, fieldDescriptor)));
                } else if (fieldDescriptor.isPacked()) {
                    builderNewBuilder.withField(FieldInfo.forPackedField(field, number, fieldType, cachedSizeField(cls, fieldDescriptor)));
                } else {
                    builderNewBuilder.withField(FieldInfo.forField(field, number, fieldType, javaStringCheckUtf8));
                }
                i2++;
                anonymousClass1 = null;
            }
            i4 <<= 1;
            if (i4 == 0) {
                i3++;
                i4 = 1;
                fieldBitField = null;
            }
            i2++;
            anonymousClass1 = null;
        }
        ArrayList arrayList = new ArrayList();
        for (int i5 = 0; i5 < fields.size(); i5++) {
            Descriptors.FieldDescriptor fieldDescriptor2 = fields.get(i5);
            if (fieldDescriptor2.isRequired() || (fieldDescriptor2.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE && needsIsInitializedCheck(fieldDescriptor2.getMessageType()))) {
                arrayList.add(Integer.valueOf(fieldDescriptor2.getNumber()));
            }
        }
        int[] iArr = new int[arrayList.size()];
        for (int i6 = 0; i6 < arrayList.size(); i6++) {
            iArr[i6] = ((Integer) arrayList.get(i6)).intValue();
        }
        builderNewBuilder.withCheckInitialized(iArr);
        return builderNewBuilder.build();
    }

    private static StructuralMessageInfo convertProto3(Class<?> cls, Descriptors.Descriptor descriptor) {
        List<Descriptors.FieldDescriptor> fields = descriptor.getFields();
        StructuralMessageInfo.Builder builderNewBuilder = StructuralMessageInfo.newBuilder(fields.size());
        builderNewBuilder.withDefaultInstance(getDefaultInstance(cls));
        builderNewBuilder.withSyntax(ProtoSyntax.PROTO3);
        OneofState oneofState = new OneofState();
        for (int i2 = 0; i2 < fields.size(); i2++) {
            Descriptors.FieldDescriptor fieldDescriptor = fields.get(i2);
            if (fieldDescriptor.getContainingOneof() != null && !fieldDescriptor.getContainingOneof().isSynthetic()) {
                builderNewBuilder.withField(buildOneofMember(cls, fieldDescriptor, oneofState, true, null));
            } else if (fieldDescriptor.isMapField()) {
                builderNewBuilder.withField(FieldInfo.forMapField(field(cls, fieldDescriptor), fieldDescriptor.getNumber(), SchemaUtil.B(cls, fieldDescriptor.getName()), null));
            } else if (fieldDescriptor.isRepeated() && fieldDescriptor.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                builderNewBuilder.withField(FieldInfo.forRepeatedMessageField(field(cls, fieldDescriptor), fieldDescriptor.getNumber(), getFieldType(fieldDescriptor), getTypeForRepeatedMessageField(cls, fieldDescriptor)));
            } else if (fieldDescriptor.isPacked()) {
                builderNewBuilder.withField(FieldInfo.forPackedField(field(cls, fieldDescriptor), fieldDescriptor.getNumber(), getFieldType(fieldDescriptor), cachedSizeField(cls, fieldDescriptor)));
            } else {
                builderNewBuilder.withField(FieldInfo.forField(field(cls, fieldDescriptor), fieldDescriptor.getNumber(), getFieldType(fieldDescriptor), true));
            }
        }
        return builderNewBuilder.build();
    }

    private static Descriptors.Descriptor descriptorForType(Class<?> cls) {
        return getDefaultInstance(cls).getDescriptorForType();
    }

    private static java.lang.reflect.Field field(Class<?> cls, Descriptors.FieldDescriptor fieldDescriptor) {
        return field(cls, c(fieldDescriptor));
    }

    private static String getCachedSizeFieldName(Descriptors.FieldDescriptor fieldDescriptor) {
        return snakeCaseToLowerCamelCase(fieldDescriptor.getName()) + "MemoizedSerializedSize";
    }

    private static Message getDefaultInstance(Class<?> cls) {
        try {
            return (Message) cls.getDeclaredMethod(GET_DEFAULT_INSTANCE_METHOD_NAME, null).invoke(null, null);
        } catch (Exception e2) {
            throw new IllegalArgumentException("Unable to get default instance for message class " + cls.getName(), e2);
        }
    }

    private static FieldType getFieldType(Descriptors.FieldDescriptor fieldDescriptor) {
        switch (AnonymousClass3.f15201c[fieldDescriptor.getType().ordinal()]) {
            case 1:
                return !fieldDescriptor.isRepeated() ? FieldType.BOOL : fieldDescriptor.isPacked() ? FieldType.BOOL_LIST_PACKED : FieldType.BOOL_LIST;
            case 2:
                return fieldDescriptor.isRepeated() ? FieldType.BYTES_LIST : FieldType.BYTES;
            case 3:
                return !fieldDescriptor.isRepeated() ? FieldType.DOUBLE : fieldDescriptor.isPacked() ? FieldType.DOUBLE_LIST_PACKED : FieldType.DOUBLE_LIST;
            case 4:
                return !fieldDescriptor.isRepeated() ? FieldType.ENUM : fieldDescriptor.isPacked() ? FieldType.ENUM_LIST_PACKED : FieldType.ENUM_LIST;
            case 5:
                return !fieldDescriptor.isRepeated() ? FieldType.FIXED32 : fieldDescriptor.isPacked() ? FieldType.FIXED32_LIST_PACKED : FieldType.FIXED32_LIST;
            case 6:
                return !fieldDescriptor.isRepeated() ? FieldType.FIXED64 : fieldDescriptor.isPacked() ? FieldType.FIXED64_LIST_PACKED : FieldType.FIXED64_LIST;
            case 7:
                return !fieldDescriptor.isRepeated() ? FieldType.FLOAT : fieldDescriptor.isPacked() ? FieldType.FLOAT_LIST_PACKED : FieldType.FLOAT_LIST;
            case 8:
                return fieldDescriptor.isRepeated() ? FieldType.GROUP_LIST : FieldType.GROUP;
            case 9:
                return !fieldDescriptor.isRepeated() ? FieldType.INT32 : fieldDescriptor.isPacked() ? FieldType.INT32_LIST_PACKED : FieldType.INT32_LIST;
            case 10:
                return !fieldDescriptor.isRepeated() ? FieldType.INT64 : fieldDescriptor.isPacked() ? FieldType.INT64_LIST_PACKED : FieldType.INT64_LIST;
            case 11:
                return fieldDescriptor.isMapField() ? FieldType.MAP : fieldDescriptor.isRepeated() ? FieldType.MESSAGE_LIST : FieldType.MESSAGE;
            case 12:
                return !fieldDescriptor.isRepeated() ? FieldType.SFIXED32 : fieldDescriptor.isPacked() ? FieldType.SFIXED32_LIST_PACKED : FieldType.SFIXED32_LIST;
            case 13:
                return !fieldDescriptor.isRepeated() ? FieldType.SFIXED64 : fieldDescriptor.isPacked() ? FieldType.SFIXED64_LIST_PACKED : FieldType.SFIXED64_LIST;
            case 14:
                return !fieldDescriptor.isRepeated() ? FieldType.SINT32 : fieldDescriptor.isPacked() ? FieldType.SINT32_LIST_PACKED : FieldType.SINT32_LIST;
            case 15:
                return !fieldDescriptor.isRepeated() ? FieldType.SINT64 : fieldDescriptor.isPacked() ? FieldType.SINT64_LIST_PACKED : FieldType.SINT64_LIST;
            case 16:
                return fieldDescriptor.isRepeated() ? FieldType.STRING_LIST : FieldType.STRING;
            case 17:
                return !fieldDescriptor.isRepeated() ? FieldType.UINT32 : fieldDescriptor.isPacked() ? FieldType.UINT32_LIST_PACKED : FieldType.UINT32_LIST;
            case 18:
                return !fieldDescriptor.isRepeated() ? FieldType.UINT64 : fieldDescriptor.isPacked() ? FieldType.UINT64_LIST_PACKED : FieldType.UINT64_LIST;
            default:
                throw new IllegalArgumentException("Unsupported field type: " + fieldDescriptor.getType());
        }
    }

    public static DescriptorMessageInfoFactory getInstance() {
        return instance;
    }

    private static Class<?> getOneofStoredType(Class<?> cls, Descriptors.FieldDescriptor fieldDescriptor, FieldType fieldType) {
        switch (AnonymousClass3.f15200b[fieldType.getJavaType().ordinal()]) {
            case 1:
                return Boolean.class;
            case 2:
                return ByteString.class;
            case 3:
                return Double.class;
            case 4:
                return Float.class;
            case 5:
            case 6:
                return Integer.class;
            case 7:
                return Long.class;
            case 8:
                return String.class;
            case 9:
                return getOneofStoredTypeForMessage(cls, fieldDescriptor);
            default:
                throw new IllegalArgumentException("Invalid type for oneof: " + fieldType);
        }
    }

    private static Class<?> getOneofStoredTypeForMessage(Class<?> cls, Descriptors.FieldDescriptor fieldDescriptor) {
        try {
            return cls.getDeclaredMethod(getterForField(fieldDescriptor.getType() == Descriptors.FieldDescriptor.Type.GROUP ? fieldDescriptor.getMessageType().getName() : fieldDescriptor.getName()), null).getReturnType();
        } catch (Exception e2) {
            throw new RuntimeException(e2);
        }
    }

    private static Class<?> getTypeForRepeatedMessageField(Class<?> cls, Descriptors.FieldDescriptor fieldDescriptor) {
        try {
            return cls.getDeclaredMethod(getterForField(fieldDescriptor.getType() == Descriptors.FieldDescriptor.Type.GROUP ? fieldDescriptor.getMessageType().getName() : fieldDescriptor.getName()), Integer.TYPE).getReturnType();
        } catch (Exception e2) {
            throw new RuntimeException(e2);
        }
    }

    private static String getterForField(String str) {
        String strSnakeCaseToLowerCamelCase = snakeCaseToLowerCamelCase(str);
        return TmpConstant.PROPERTY_IDENTIFIER_GET + Character.toUpperCase(strSnakeCaseToLowerCamelCase.charAt(0)) + strSnakeCaseToLowerCamelCase.substring(1, strSnakeCaseToLowerCamelCase.length());
    }

    private static boolean needsIsInitializedCheck(Descriptors.Descriptor descriptor) {
        return isInitializedCheckAnalyzer.needsIsInitializedCheck(descriptor);
    }

    private static String snakeCaseToCamelCase(String str, boolean z2) {
        StringBuilder sb = new StringBuilder(str.length() + 1);
        for (int i2 = 0; i2 < str.length(); i2++) {
            char cCharAt = str.charAt(i2);
            if (cCharAt == '_') {
                z2 = true;
            } else if (Character.isDigit(cCharAt)) {
                sb.append(cCharAt);
                z2 = true;
            } else if (z2) {
                sb.append(Character.toUpperCase(cCharAt));
                z2 = false;
            } else if (i2 == 0) {
                sb.append(Character.toLowerCase(cCharAt));
            } else {
                sb.append(cCharAt);
            }
        }
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String snakeCaseToLowerCamelCase(String str) {
        return snakeCaseToCamelCase(str, false);
    }

    private static String snakeCaseToUpperCamelCase(String str) {
        return snakeCaseToCamelCase(str, true);
    }

    @Override // com.google.protobuf.MessageInfoFactory
    public boolean isSupported(Class<?> cls) {
        return GeneratedMessageV3.class.isAssignableFrom(cls);
    }

    @Override // com.google.protobuf.MessageInfoFactory
    public MessageInfo messageInfoFor(Class<?> cls) {
        if (GeneratedMessageV3.class.isAssignableFrom(cls)) {
            return convert(cls, descriptorForType(cls));
        }
        throw new IllegalArgumentException("Unsupported message type: " + cls.getName());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.reflect.Field field(Class<?> cls, String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (Exception unused) {
            throw new IllegalArgumentException("Unable to find field " + str + " in message class " + cls.getName());
        }
    }
}
