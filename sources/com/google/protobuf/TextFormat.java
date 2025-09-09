package com.google.protobuf;

import anetwork.channel.util.RequestConstant;
import com.alibaba.ailabs.iot.aisbase.Constants;
import com.google.protobuf.Descriptors;
import com.google.protobuf.DynamicMessage;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.Message;
import com.google.protobuf.MessageReflection;
import com.google.protobuf.TextFormatParseInfoTree;
import com.google.protobuf.UnknownFieldSet;
import com.xiaomi.mipush.sdk.Constants;
import java.io.IOException;
import java.lang.Character;
import java.math.BigInteger;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.mozilla.javascript.typedarrays.Conversions;

/* loaded from: classes2.dex */
public final class TextFormat {
    private static final String DEBUG_STRING_SILENT_MARKER = "\t ";
    private static final Logger logger = Logger.getLogger(TextFormat.class.getName());
    private static final Parser PARSER = Parser.newBuilder().build();

    /* renamed from: com.google.protobuf.TextFormat$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f15340a;

        /* renamed from: b, reason: collision with root package name */
        static final /* synthetic */ int[] f15341b;

        static {
            int[] iArr = new int[Descriptors.FieldDescriptor.Type.values().length];
            f15341b = iArr;
            try {
                iArr[Descriptors.FieldDescriptor.Type.INT32.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f15341b[Descriptors.FieldDescriptor.Type.SINT32.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f15341b[Descriptors.FieldDescriptor.Type.SFIXED32.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f15341b[Descriptors.FieldDescriptor.Type.INT64.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f15341b[Descriptors.FieldDescriptor.Type.SINT64.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f15341b[Descriptors.FieldDescriptor.Type.SFIXED64.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f15341b[Descriptors.FieldDescriptor.Type.BOOL.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f15341b[Descriptors.FieldDescriptor.Type.FLOAT.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                f15341b[Descriptors.FieldDescriptor.Type.DOUBLE.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                f15341b[Descriptors.FieldDescriptor.Type.UINT32.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                f15341b[Descriptors.FieldDescriptor.Type.FIXED32.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                f15341b[Descriptors.FieldDescriptor.Type.UINT64.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                f15341b[Descriptors.FieldDescriptor.Type.FIXED64.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                f15341b[Descriptors.FieldDescriptor.Type.STRING.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                f15341b[Descriptors.FieldDescriptor.Type.BYTES.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                f15341b[Descriptors.FieldDescriptor.Type.ENUM.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                f15341b[Descriptors.FieldDescriptor.Type.MESSAGE.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                f15341b[Descriptors.FieldDescriptor.Type.GROUP.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            int[] iArr2 = new int[Descriptors.FieldDescriptor.JavaType.values().length];
            f15340a = iArr2;
            try {
                iArr2[Descriptors.FieldDescriptor.JavaType.BOOLEAN.ordinal()] = 1;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                f15340a[Descriptors.FieldDescriptor.JavaType.LONG.ordinal()] = 2;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                f15340a[Descriptors.FieldDescriptor.JavaType.INT.ordinal()] = 3;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                f15340a[Descriptors.FieldDescriptor.JavaType.STRING.ordinal()] = 4;
            } catch (NoSuchFieldError unused22) {
            }
        }
    }

    public static class InvalidEscapeSequenceException extends IOException {
        private static final long serialVersionUID = -8164033650142593304L;

        InvalidEscapeSequenceException(String str) {
            super(str);
        }
    }

    public static class ParseException extends IOException {
        private static final long serialVersionUID = 3196188060225107702L;
        private final int column;
        private final int line;

        public ParseException(String str) {
            this(-1, -1, str);
        }

        public int getColumn() {
            return this.column;
        }

        public int getLine() {
            return this.line;
        }

        public ParseException(int i2, int i3, String str) {
            super(Integer.toString(i2) + ":" + i3 + ": " + str);
            this.line = i2;
            this.column = i3;
        }
    }

    public static class Parser {
        private static final int BUFFER_SIZE = 4096;
        private final boolean allowUnknownEnumValues;
        private final boolean allowUnknownExtensions;
        private final boolean allowUnknownFields;
        private TextFormatParseInfoTree.Builder parseInfoTreeBuilder;
        private final SingularOverwritePolicy singularOverwritePolicy;
        private final TypeRegistry typeRegistry;

        public static class Builder {
            private boolean allowUnknownFields = false;
            private boolean allowUnknownEnumValues = false;
            private boolean allowUnknownExtensions = false;
            private SingularOverwritePolicy singularOverwritePolicy = SingularOverwritePolicy.ALLOW_SINGULAR_OVERWRITES;
            private TextFormatParseInfoTree.Builder parseInfoTreeBuilder = null;
            private TypeRegistry typeRegistry = TypeRegistry.getEmptyTypeRegistry();

            public Parser build() {
                return new Parser(this.typeRegistry, this.allowUnknownFields, this.allowUnknownEnumValues, this.allowUnknownExtensions, this.singularOverwritePolicy, this.parseInfoTreeBuilder, null);
            }

            public Builder setAllowUnknownExtensions(boolean z2) {
                this.allowUnknownExtensions = z2;
                return this;
            }

            public Builder setAllowUnknownFields(boolean z2) {
                this.allowUnknownFields = z2;
                return this;
            }

            public Builder setParseInfoTreeBuilder(TextFormatParseInfoTree.Builder builder) {
                this.parseInfoTreeBuilder = builder;
                return this;
            }

            public Builder setSingularOverwritePolicy(SingularOverwritePolicy singularOverwritePolicy) {
                this.singularOverwritePolicy = singularOverwritePolicy;
                return this;
            }

            public Builder setTypeRegistry(TypeRegistry typeRegistry) {
                this.typeRegistry = typeRegistry;
                return this;
            }
        }

        public enum SingularOverwritePolicy {
            ALLOW_SINGULAR_OVERWRITES,
            FORBID_SINGULAR_OVERWRITES
        }

        static final class UnknownField {

            /* renamed from: a, reason: collision with root package name */
            final String f15342a;

            /* renamed from: b, reason: collision with root package name */
            final Type f15343b;

            enum Type {
                FIELD,
                EXTENSION
            }

            UnknownField(String str, Type type) {
                this.f15342a = str;
                this.f15343b = type;
            }
        }

        /* synthetic */ Parser(TypeRegistry typeRegistry, boolean z2, boolean z3, boolean z4, SingularOverwritePolicy singularOverwritePolicy, TextFormatParseInfoTree.Builder builder, AnonymousClass1 anonymousClass1) {
            this(typeRegistry, z2, z3, z4, singularOverwritePolicy, builder);
        }

        private void checkUnknownFields(List<UnknownField> list) throws ParseException {
            int i2;
            if (list.isEmpty()) {
                return;
            }
            StringBuilder sb = new StringBuilder("Input contains unknown fields and/or extensions:");
            for (UnknownField unknownField : list) {
                sb.append('\n');
                sb.append(unknownField.f15342a);
            }
            if (this.allowUnknownFields) {
                TextFormat.logger.warning(sb.toString());
                return;
            }
            if (this.allowUnknownExtensions) {
                Iterator<UnknownField> it = list.iterator();
                i2 = 0;
                while (it.hasNext()) {
                    if (it.next().f15343b != UnknownField.Type.FIELD) {
                        i2++;
                    }
                }
                TextFormat.logger.warning(sb.toString());
                return;
            }
            i2 = 0;
            String[] strArrSplit = list.get(i2).f15342a.split(":");
            throw new ParseException(Integer.parseInt(strArrSplit[0]), Integer.parseInt(strArrSplit[1]), sb.toString());
        }

        private void consumeFieldValue(Tokenizer tokenizer, ExtensionRegistry extensionRegistry, MessageReflection.MergeTarget mergeTarget, Descriptors.FieldDescriptor fieldDescriptor, ExtensionRegistry.ExtensionInfo extensionInfo, TextFormatParseInfoTree.Builder builder, List<UnknownField> list) throws ParseException {
            String str;
            Object objFinish;
            if (this.singularOverwritePolicy == SingularOverwritePolicy.FORBID_SINGULAR_OVERWRITES && !fieldDescriptor.isRepeated()) {
                if (mergeTarget.hasField(fieldDescriptor)) {
                    throw tokenizer.r("Non-repeated field \"" + fieldDescriptor.getFullName() + "\" cannot be overwritten.");
                }
                if (fieldDescriptor.getContainingOneof() != null && mergeTarget.hasOneof(fieldDescriptor.getContainingOneof())) {
                    Descriptors.OneofDescriptor containingOneof = fieldDescriptor.getContainingOneof();
                    throw tokenizer.r("Field \"" + fieldDescriptor.getFullName() + "\" is specified along with field \"" + mergeTarget.getOneofFieldDescriptor(containingOneof).getFullName() + "\", another member of oneof \"" + containingOneof.getName() + "\".");
                }
            }
            Object objValueOf = null;
            if (fieldDescriptor.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                if (tokenizer.s("<")) {
                    str = ">";
                } else {
                    tokenizer.c("{");
                    str = com.alipay.sdk.m.u.i.f9804d;
                }
                String str2 = str;
                if (fieldDescriptor.getMessageType().getFullName().equals("google.protobuf.Any") && tokenizer.s("[")) {
                    MessageReflection.MergeTarget mergeTargetNewMergeTargetForField = mergeTarget.newMergeTargetForField(fieldDescriptor, DynamicMessage.getDefaultInstance(fieldDescriptor.getMessageType()));
                    mergeAnyFieldValue(tokenizer, extensionRegistry, mergeTargetNewMergeTargetForField, builder, list, fieldDescriptor.getMessageType());
                    objFinish = mergeTargetNewMergeTargetForField.finish();
                    tokenizer.c(str2);
                } else {
                    MessageReflection.MergeTarget mergeTargetNewMergeTargetForField2 = mergeTarget.newMergeTargetForField(fieldDescriptor, extensionInfo != null ? extensionInfo.defaultInstance : null);
                    while (!tokenizer.s(str2)) {
                        if (tokenizer.b()) {
                            throw tokenizer.q("Expected \"" + str2 + "\".");
                        }
                        mergeField(tokenizer, extensionRegistry, mergeTargetNewMergeTargetForField2, builder, list);
                    }
                    objFinish = mergeTargetNewMergeTargetForField2.finish();
                }
                objValueOf = objFinish;
            } else {
                switch (AnonymousClass1.f15341b[fieldDescriptor.getType().ordinal()]) {
                    case 1:
                    case 2:
                    case 3:
                        objValueOf = Integer.valueOf(tokenizer.f());
                        break;
                    case 4:
                    case 5:
                    case 6:
                        objValueOf = Long.valueOf(tokenizer.g());
                        break;
                    case 7:
                        objValueOf = Boolean.valueOf(tokenizer.consumeBoolean());
                        break;
                    case 8:
                        objValueOf = Float.valueOf(tokenizer.consumeFloat());
                        break;
                    case 9:
                        objValueOf = Double.valueOf(tokenizer.consumeDouble());
                        break;
                    case 10:
                    case 11:
                        objValueOf = Integer.valueOf(tokenizer.h());
                        break;
                    case 12:
                    case 13:
                        objValueOf = Long.valueOf(tokenizer.i());
                        break;
                    case 14:
                        objValueOf = tokenizer.consumeString();
                        break;
                    case 15:
                        objValueOf = tokenizer.d();
                        break;
                    case 16:
                        Descriptors.EnumDescriptor enumType = fieldDescriptor.getEnumType();
                        if (tokenizer.o()) {
                            int iF = tokenizer.f();
                            objValueOf = enumType.findValueByNumber(iF);
                            if (objValueOf == null) {
                                String str3 = "Enum type \"" + enumType.getFullName() + "\" has no value with number " + iF + '.';
                                if (this.allowUnknownEnumValues) {
                                    TextFormat.logger.warning(str3);
                                    return;
                                }
                                throw tokenizer.r("Enum type \"" + enumType.getFullName() + "\" has no value with number " + iF + '.');
                            }
                        } else {
                            String strE = tokenizer.e();
                            objValueOf = enumType.findValueByName(strE);
                            if (objValueOf == null) {
                                String str4 = "Enum type \"" + enumType.getFullName() + "\" has no value named \"" + strE + "\".";
                                if (!this.allowUnknownEnumValues) {
                                    throw tokenizer.r(str4);
                                }
                                TextFormat.logger.warning(str4);
                                return;
                            }
                        }
                        break;
                    case 17:
                    case 18:
                        throw new RuntimeException("Can't get here.");
                }
            }
            if (fieldDescriptor.isRepeated()) {
                mergeTarget.addRepeatedField(fieldDescriptor, objValueOf);
            } else {
                mergeTarget.setField(fieldDescriptor, objValueOf);
            }
        }

        private void consumeFieldValues(Tokenizer tokenizer, ExtensionRegistry extensionRegistry, MessageReflection.MergeTarget mergeTarget, Descriptors.FieldDescriptor fieldDescriptor, ExtensionRegistry.ExtensionInfo extensionInfo, TextFormatParseInfoTree.Builder builder, List<UnknownField> list) throws ParseException {
            if (!fieldDescriptor.isRepeated() || !tokenizer.s("[")) {
                consumeFieldValue(tokenizer, extensionRegistry, mergeTarget, fieldDescriptor, extensionInfo, builder, list);
            } else {
                if (tokenizer.s("]")) {
                    return;
                }
                while (true) {
                    consumeFieldValue(tokenizer, extensionRegistry, mergeTarget, fieldDescriptor, extensionInfo, builder, list);
                    if (tokenizer.s("]")) {
                        return;
                    } else {
                        tokenizer.c(",");
                    }
                }
            }
        }

        private String consumeFullTypeName(Tokenizer tokenizer) throws ParseException {
            if (!tokenizer.s("[")) {
                return tokenizer.e();
            }
            String strE = tokenizer.e();
            while (tokenizer.s(".")) {
                strE = strE + "." + tokenizer.e();
            }
            if (tokenizer.s("/")) {
                strE = strE + "/" + tokenizer.e();
                while (tokenizer.s(".")) {
                    strE = strE + "." + tokenizer.e();
                }
            }
            tokenizer.c("]");
            return strE;
        }

        private void detectSilentMarker(Tokenizer tokenizer, Descriptors.Descriptor descriptor, String str) {
        }

        private void guessFieldTypeAndSkip(Tokenizer tokenizer, Descriptors.Descriptor descriptor) throws ParseException {
            boolean zS = tokenizer.s(":");
            if (tokenizer.n("[")) {
                skipFieldShortFormedRepeated(tokenizer, zS, descriptor);
            } else if (!zS || tokenizer.n("{") || tokenizer.n("<")) {
                skipFieldMessage(tokenizer, descriptor);
            } else {
                skipFieldValue(tokenizer);
            }
        }

        private void mergeAnyFieldValue(Tokenizer tokenizer, ExtensionRegistry extensionRegistry, MessageReflection.MergeTarget mergeTarget, TextFormatParseInfoTree.Builder builder, List<UnknownField> list, Descriptors.Descriptor descriptor) throws ParseException {
            String str;
            StringBuilder sb = new StringBuilder();
            while (true) {
                sb.append(tokenizer.e());
                if (tokenizer.s("]")) {
                    detectSilentMarker(tokenizer, descriptor, sb.toString());
                    tokenizer.s(":");
                    if (tokenizer.s("<")) {
                        str = ">";
                    } else {
                        tokenizer.c("{");
                        str = com.alipay.sdk.m.u.i.f9804d;
                    }
                    String str2 = str;
                    String string = sb.toString();
                    try {
                        Descriptors.Descriptor descriptorForTypeUrl = this.typeRegistry.getDescriptorForTypeUrl(string);
                        if (descriptorForTypeUrl == null) {
                            throw tokenizer.q("Unable to parse Any of type: " + string + ". Please make sure that the TypeRegistry contains the descriptors for the given types.");
                        }
                        DynamicMessage.Builder builderNewBuilderForType = DynamicMessage.getDefaultInstance(descriptorForTypeUrl).newBuilderForType();
                        MessageReflection.BuilderAdapter builderAdapter = new MessageReflection.BuilderAdapter(builderNewBuilderForType);
                        while (!tokenizer.s(str2)) {
                            mergeField(tokenizer, extensionRegistry, builderAdapter, builder, list);
                        }
                        mergeTarget.setField(descriptor.findFieldByName("type_url"), sb.toString());
                        mergeTarget.setField(descriptor.findFieldByName("value"), builderNewBuilderForType.build().toByteString());
                        return;
                    } catch (InvalidProtocolBufferException unused) {
                        throw tokenizer.q("Invalid valid type URL. Found: " + string);
                    }
                }
                if (tokenizer.s("/")) {
                    sb.append("/");
                } else {
                    if (!tokenizer.s(".")) {
                        throw tokenizer.r("Expected a valid type URL.");
                    }
                    sb.append(".");
                }
            }
        }

        private void mergeField(Tokenizer tokenizer, ExtensionRegistry extensionRegistry, MessageReflection.MergeTarget mergeTarget, List<UnknownField> list) throws ParseException {
            mergeField(tokenizer, extensionRegistry, mergeTarget, this.parseInfoTreeBuilder, list);
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        private void skipField(Tokenizer tokenizer, Descriptors.Descriptor descriptor) throws ParseException {
            detectSilentMarker(tokenizer, descriptor, consumeFullTypeName(tokenizer));
            guessFieldTypeAndSkip(tokenizer, descriptor);
            if (tokenizer.s(com.alipay.sdk.m.u.i.f9802b)) {
                return;
            }
            tokenizer.s(",");
        }

        private void skipFieldMessage(Tokenizer tokenizer, Descriptors.Descriptor descriptor) throws ParseException {
            String str;
            if (tokenizer.s("<")) {
                str = ">";
            } else {
                tokenizer.c("{");
                str = com.alipay.sdk.m.u.i.f9804d;
            }
            while (!tokenizer.n(">") && !tokenizer.n(com.alipay.sdk.m.u.i.f9804d)) {
                skipField(tokenizer, descriptor);
            }
            tokenizer.c(str);
        }

        private void skipFieldShortFormedRepeated(Tokenizer tokenizer, boolean z2, Descriptors.Descriptor descriptor) throws ParseException {
            if (!tokenizer.s("[") || tokenizer.s("]")) {
                return;
            }
            while (true) {
                if (tokenizer.n("{") || tokenizer.n("<")) {
                    skipFieldMessage(tokenizer, descriptor);
                } else {
                    if (!z2) {
                        throw tokenizer.q("Invalid repeated scalar field: missing \":\" before \"[\".");
                    }
                    skipFieldValue(tokenizer);
                }
                if (tokenizer.s("]")) {
                    return;
                } else {
                    tokenizer.c(",");
                }
            }
        }

        private void skipFieldValue(Tokenizer tokenizer) throws ParseException {
            if (tokenizer.v()) {
                while (tokenizer.v()) {
                }
                return;
            }
            if (tokenizer.t() || tokenizer.u() || tokenizer.tryConsumeUInt64() || tokenizer.tryConsumeDouble() || tokenizer.tryConsumeFloat()) {
                return;
            }
            throw tokenizer.q("Invalid field value: " + tokenizer.currentToken);
        }

        private static StringBuilder toStringBuilder(Readable readable) throws IOException {
            StringBuilder sb = new StringBuilder();
            CharBuffer charBufferAllocate = CharBuffer.allocate(4096);
            while (true) {
                int i2 = readable.read(charBufferAllocate);
                if (i2 == -1) {
                    return sb;
                }
                Java8Compatibility.b(charBufferAllocate);
                sb.append((CharSequence) charBufferAllocate, 0, i2);
            }
        }

        public void merge(Readable readable, Message.Builder builder) throws IOException {
            merge(readable, ExtensionRegistry.getEmptyRegistry(), builder);
        }

        private Parser(TypeRegistry typeRegistry, boolean z2, boolean z3, boolean z4, SingularOverwritePolicy singularOverwritePolicy, TextFormatParseInfoTree.Builder builder) {
            this.typeRegistry = typeRegistry;
            this.allowUnknownFields = z2;
            this.allowUnknownEnumValues = z3;
            this.allowUnknownExtensions = z4;
            this.singularOverwritePolicy = singularOverwritePolicy;
            this.parseInfoTreeBuilder = builder;
        }

        private void mergeField(Tokenizer tokenizer, ExtensionRegistry extensionRegistry, MessageReflection.MergeTarget mergeTarget, TextFormatParseInfoTree.Builder builder, List<UnknownField> list) throws ParseException {
            String strE;
            Descriptors.FieldDescriptor fieldDescriptor;
            ExtensionRegistry.ExtensionInfo extensionInfo;
            Descriptors.FieldDescriptor fieldDescriptor2;
            int iK = tokenizer.k();
            int iJ = tokenizer.j();
            Descriptors.Descriptor descriptorForType = mergeTarget.getDescriptorForType();
            if ("google.protobuf.Any".equals(descriptorForType.getFullName()) && tokenizer.s("[")) {
                mergeAnyFieldValue(tokenizer, extensionRegistry, mergeTarget, builder, list, descriptorForType);
                return;
            }
            if (tokenizer.s("[")) {
                StringBuilder sb = new StringBuilder(tokenizer.e());
                while (tokenizer.s(".")) {
                    sb.append('.');
                    sb.append(tokenizer.e());
                }
                strE = sb.toString();
                ExtensionRegistry.ExtensionInfo extensionInfoFindExtensionByName = mergeTarget.findExtensionByName(extensionRegistry, strE);
                if (extensionInfoFindExtensionByName == null) {
                    list.add(new UnknownField((tokenizer.m() + 1) + ":" + (tokenizer.l() + 1) + ":\t" + descriptorForType.getFullName() + ".[" + strE + "]", UnknownField.Type.EXTENSION));
                    fieldDescriptor2 = null;
                } else {
                    if (extensionInfoFindExtensionByName.descriptor.getContainingType() != descriptorForType) {
                        throw tokenizer.r("Extension \"" + strE + "\" does not extend message type \"" + descriptorForType.getFullName() + "\".");
                    }
                    fieldDescriptor2 = extensionInfoFindExtensionByName.descriptor;
                }
                tokenizer.c("]");
                extensionInfo = extensionInfoFindExtensionByName;
                fieldDescriptor = fieldDescriptor2;
            } else {
                strE = tokenizer.e();
                Descriptors.FieldDescriptor fieldDescriptorFindFieldByName = descriptorForType.findFieldByName(strE);
                if (fieldDescriptorFindFieldByName == null && (fieldDescriptorFindFieldByName = descriptorForType.findFieldByName(strE.toLowerCase(Locale.US))) != null && fieldDescriptorFindFieldByName.getType() != Descriptors.FieldDescriptor.Type.GROUP) {
                    fieldDescriptorFindFieldByName = null;
                }
                if (fieldDescriptorFindFieldByName != null && fieldDescriptorFindFieldByName.getType() == Descriptors.FieldDescriptor.Type.GROUP && !fieldDescriptorFindFieldByName.getMessageType().getName().equals(strE)) {
                    fieldDescriptorFindFieldByName = null;
                }
                if (fieldDescriptorFindFieldByName == null) {
                    list.add(new UnknownField((tokenizer.m() + 1) + ":" + (tokenizer.l() + 1) + ":\t" + descriptorForType.getFullName() + "." + strE, UnknownField.Type.FIELD));
                }
                fieldDescriptor = fieldDescriptorFindFieldByName;
                extensionInfo = null;
            }
            if (fieldDescriptor == null) {
                detectSilentMarker(tokenizer, descriptorForType, strE);
                guessFieldTypeAndSkip(tokenizer, descriptorForType);
                return;
            }
            if (fieldDescriptor.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                detectSilentMarker(tokenizer, descriptorForType, fieldDescriptor.getFullName());
                tokenizer.s(":");
                if (builder != null) {
                    consumeFieldValues(tokenizer, extensionRegistry, mergeTarget, fieldDescriptor, extensionInfo, builder.getBuilderForSubMessageField(fieldDescriptor), list);
                } else {
                    consumeFieldValues(tokenizer, extensionRegistry, mergeTarget, fieldDescriptor, extensionInfo, builder, list);
                }
            } else {
                detectSilentMarker(tokenizer, descriptorForType, fieldDescriptor.getFullName());
                tokenizer.c(":");
                consumeFieldValues(tokenizer, extensionRegistry, mergeTarget, fieldDescriptor, extensionInfo, builder, list);
            }
            if (builder != null) {
                builder.setLocation(fieldDescriptor, TextFormatParseLocation.a(iK, iJ));
            }
            if (tokenizer.s(com.alipay.sdk.m.u.i.f9802b)) {
                return;
            }
            tokenizer.s(",");
        }

        public void merge(CharSequence charSequence, Message.Builder builder) throws ParseException {
            merge(charSequence, ExtensionRegistry.getEmptyRegistry(), builder);
        }

        public void merge(Readable readable, ExtensionRegistry extensionRegistry, Message.Builder builder) throws IOException {
            merge(toStringBuilder(readable), extensionRegistry, builder);
        }

        public void merge(CharSequence charSequence, ExtensionRegistry extensionRegistry, Message.Builder builder) throws ParseException {
            Tokenizer tokenizer = new Tokenizer(charSequence, null);
            MessageReflection.BuilderAdapter builderAdapter = new MessageReflection.BuilderAdapter(builder);
            ArrayList arrayList = new ArrayList();
            while (!tokenizer.b()) {
                mergeField(tokenizer, extensionRegistry, builderAdapter, arrayList);
            }
            checkUnknownFields(arrayList);
        }
    }

    public static final class Printer {
        private static final Printer DEFAULT = new Printer(true, TypeRegistry.getEmptyTypeRegistry());
        private final boolean escapeNonAscii;
        private final TypeRegistry typeRegistry;

        private static class MapEntryAdapter implements Comparable<MapEntryAdapter> {
            private Object entry;
            private final Descriptors.FieldDescriptor.JavaType fieldType;
            private MapEntry mapEntry;

            MapEntryAdapter(Object obj, Descriptors.FieldDescriptor fieldDescriptor) {
                if (obj instanceof MapEntry) {
                    this.mapEntry = (MapEntry) obj;
                } else {
                    this.entry = obj;
                }
                this.fieldType = extractFieldType(fieldDescriptor);
            }

            private static Descriptors.FieldDescriptor.JavaType extractFieldType(Descriptors.FieldDescriptor fieldDescriptor) {
                return fieldDescriptor.getMessageType().getFields().get(0).getJavaType();
            }

            Object a() {
                MapEntry mapEntry = this.mapEntry;
                return mapEntry != null ? mapEntry : this.entry;
            }

            Object b() {
                MapEntry mapEntry = this.mapEntry;
                if (mapEntry != null) {
                    return mapEntry.getKey();
                }
                return null;
            }

            @Override // java.lang.Comparable
            public int compareTo(MapEntryAdapter mapEntryAdapter) {
                if (b() == null || mapEntryAdapter.b() == null) {
                    TextFormat.logger.info("Invalid key for map field.");
                    return -1;
                }
                int i2 = AnonymousClass1.f15340a[this.fieldType.ordinal()];
                if (i2 == 1) {
                    Boolean bool = (Boolean) b();
                    bool.booleanValue();
                    Boolean bool2 = (Boolean) mapEntryAdapter.b();
                    bool2.booleanValue();
                    return bool.compareTo(bool2);
                }
                if (i2 == 2) {
                    Long l2 = (Long) b();
                    l2.longValue();
                    Long l3 = (Long) mapEntryAdapter.b();
                    l3.longValue();
                    return l2.compareTo(l3);
                }
                if (i2 == 3) {
                    Integer num = (Integer) b();
                    num.intValue();
                    Integer num2 = (Integer) mapEntryAdapter.b();
                    num2.intValue();
                    return num.compareTo(num2);
                }
                if (i2 != 4) {
                    return 0;
                }
                String str = (String) b();
                String str2 = (String) mapEntryAdapter.b();
                if (str == null && str2 == null) {
                    return 0;
                }
                if (str == null && str2 != null) {
                    return -1;
                }
                if (str == null || str2 != null) {
                    return str.compareTo(str2);
                }
                return 1;
            }
        }

        private Printer(boolean z2, TypeRegistry typeRegistry) {
            this.escapeNonAscii = z2;
            this.typeRegistry = typeRegistry;
        }

        private boolean printAny(MessageOrBuilder messageOrBuilder, TextGenerator textGenerator) throws IOException {
            Descriptors.Descriptor descriptorForType = messageOrBuilder.getDescriptorForType();
            Descriptors.FieldDescriptor fieldDescriptorFindFieldByNumber = descriptorForType.findFieldByNumber(1);
            Descriptors.FieldDescriptor fieldDescriptorFindFieldByNumber2 = descriptorForType.findFieldByNumber(2);
            if (fieldDescriptorFindFieldByNumber != null && fieldDescriptorFindFieldByNumber.getType() == Descriptors.FieldDescriptor.Type.STRING && fieldDescriptorFindFieldByNumber2 != null && fieldDescriptorFindFieldByNumber2.getType() == Descriptors.FieldDescriptor.Type.BYTES) {
                String str = (String) messageOrBuilder.getField(fieldDescriptorFindFieldByNumber);
                if (str.isEmpty()) {
                    return false;
                }
                Object field = messageOrBuilder.getField(fieldDescriptorFindFieldByNumber2);
                try {
                    Descriptors.Descriptor descriptorForTypeUrl = this.typeRegistry.getDescriptorForTypeUrl(str);
                    if (descriptorForTypeUrl == null) {
                        return false;
                    }
                    DynamicMessage.Builder builderNewBuilderForType = DynamicMessage.getDefaultInstance(descriptorForTypeUrl).newBuilderForType();
                    builderNewBuilderForType.mergeFrom((ByteString) field);
                    textGenerator.print("[");
                    textGenerator.print(str);
                    textGenerator.print("] {");
                    textGenerator.eol();
                    textGenerator.indent();
                    print(builderNewBuilderForType, textGenerator);
                    textGenerator.outdent();
                    textGenerator.print(com.alipay.sdk.m.u.i.f9804d);
                    textGenerator.eol();
                    return true;
                } catch (InvalidProtocolBufferException unused) {
                }
            }
            return false;
        }

        private void printMessage(MessageOrBuilder messageOrBuilder, TextGenerator textGenerator) throws IOException {
            for (Map.Entry<Descriptors.FieldDescriptor, Object> entry : messageOrBuilder.getAllFields().entrySet()) {
                printField(entry.getKey(), entry.getValue(), textGenerator);
            }
            printUnknownFields(messageOrBuilder.getUnknownFields(), textGenerator);
        }

        private void printSingleField(Descriptors.FieldDescriptor fieldDescriptor, Object obj, TextGenerator textGenerator) throws IOException {
            if (fieldDescriptor.isExtension()) {
                textGenerator.print("[");
                if (fieldDescriptor.getContainingType().getOptions().getMessageSetWireFormat() && fieldDescriptor.getType() == Descriptors.FieldDescriptor.Type.MESSAGE && fieldDescriptor.isOptional() && fieldDescriptor.getExtensionScope() == fieldDescriptor.getMessageType()) {
                    textGenerator.print(fieldDescriptor.getMessageType().getFullName());
                } else {
                    textGenerator.print(fieldDescriptor.getFullName());
                }
                textGenerator.print("]");
            } else if (fieldDescriptor.getType() == Descriptors.FieldDescriptor.Type.GROUP) {
                textGenerator.print(fieldDescriptor.getMessageType().getName());
            } else {
                textGenerator.print(fieldDescriptor.getName());
            }
            Descriptors.FieldDescriptor.JavaType javaType = fieldDescriptor.getJavaType();
            Descriptors.FieldDescriptor.JavaType javaType2 = Descriptors.FieldDescriptor.JavaType.MESSAGE;
            if (javaType == javaType2) {
                textGenerator.print(" {");
                textGenerator.eol();
                textGenerator.indent();
            } else {
                textGenerator.print(": ");
            }
            printFieldValue(fieldDescriptor, obj, textGenerator);
            if (fieldDescriptor.getJavaType() == javaType2) {
                textGenerator.outdent();
                textGenerator.print(com.alipay.sdk.m.u.i.f9804d);
            }
            textGenerator.eol();
        }

        private static void printUnknownField(int i2, int i3, List<?> list, TextGenerator textGenerator) throws IOException {
            for (Object obj : list) {
                textGenerator.print(String.valueOf(i2));
                textGenerator.print(": ");
                printUnknownFieldValue(i3, obj, textGenerator);
                textGenerator.eol();
            }
        }

        private static void printUnknownFieldValue(int i2, Object obj, TextGenerator textGenerator) throws IOException {
            int tagWireType = WireFormat.getTagWireType(i2);
            if (tagWireType == 0) {
                textGenerator.print(TextFormat.unsignedToString(((Long) obj).longValue()));
                return;
            }
            if (tagWireType == 1) {
                textGenerator.print(String.format(null, "0x%016x", (Long) obj));
                return;
            }
            if (tagWireType != 2) {
                if (tagWireType == 3) {
                    printUnknownFields((UnknownFieldSet) obj, textGenerator);
                    return;
                } else {
                    if (tagWireType == 5) {
                        textGenerator.print(String.format(null, "0x%08x", (Integer) obj));
                        return;
                    }
                    throw new IllegalArgumentException("Bad tag: " + i2);
                }
            }
            try {
                UnknownFieldSet from = UnknownFieldSet.parseFrom((ByteString) obj);
                textGenerator.print("{");
                textGenerator.eol();
                textGenerator.indent();
                printUnknownFields(from, textGenerator);
                textGenerator.outdent();
                textGenerator.print(com.alipay.sdk.m.u.i.f9804d);
            } catch (InvalidProtocolBufferException unused) {
                textGenerator.print("\"");
                textGenerator.print(TextFormat.escapeBytes((ByteString) obj));
                textGenerator.print("\"");
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void printUnknownFields(UnknownFieldSet unknownFieldSet, TextGenerator textGenerator) throws IOException {
            for (Map.Entry<Integer, UnknownFieldSet.Field> entry : unknownFieldSet.asMap().entrySet()) {
                int iIntValue = entry.getKey().intValue();
                UnknownFieldSet.Field value = entry.getValue();
                printUnknownField(iIntValue, 0, value.getVarintList(), textGenerator);
                printUnknownField(iIntValue, 5, value.getFixed32List(), textGenerator);
                printUnknownField(iIntValue, 1, value.getFixed64List(), textGenerator);
                printUnknownField(iIntValue, 2, value.getLengthDelimitedList(), textGenerator);
                for (UnknownFieldSet unknownFieldSet2 : value.getGroupList()) {
                    textGenerator.print(entry.getKey().toString());
                    textGenerator.print(" {");
                    textGenerator.eol();
                    textGenerator.indent();
                    printUnknownFields(unknownFieldSet2, textGenerator);
                    textGenerator.outdent();
                    textGenerator.print(com.alipay.sdk.m.u.i.f9804d);
                    textGenerator.eol();
                }
            }
        }

        public Printer escapingNonAscii(boolean z2) {
            return new Printer(z2, this.typeRegistry);
        }

        public void print(MessageOrBuilder messageOrBuilder, Appendable appendable) throws IOException {
            print(messageOrBuilder, TextFormat.multiLineOutput(appendable));
        }

        public void printField(Descriptors.FieldDescriptor fieldDescriptor, Object obj, Appendable appendable) throws IOException {
            printField(fieldDescriptor, obj, TextFormat.multiLineOutput(appendable));
        }

        public String printFieldToString(Descriptors.FieldDescriptor fieldDescriptor, Object obj) {
            try {
                StringBuilder sb = new StringBuilder();
                printField(fieldDescriptor, obj, sb);
                return sb.toString();
            } catch (IOException e2) {
                throw new IllegalStateException(e2);
            }
        }

        public void printFieldValue(Descriptors.FieldDescriptor fieldDescriptor, Object obj, Appendable appendable) throws IOException {
            printFieldValue(fieldDescriptor, obj, TextFormat.multiLineOutput(appendable));
        }

        public String printToString(MessageOrBuilder messageOrBuilder) {
            try {
                StringBuilder sb = new StringBuilder();
                print(messageOrBuilder, sb);
                return sb.toString();
            } catch (IOException e2) {
                throw new IllegalStateException(e2);
            }
        }

        public String shortDebugString(MessageOrBuilder messageOrBuilder) {
            try {
                StringBuilder sb = new StringBuilder();
                print(messageOrBuilder, TextFormat.singleLineOutput(sb));
                return sb.toString();
            } catch (IOException e2) {
                throw new IllegalStateException(e2);
            }
        }

        public Printer usingTypeRegistry(TypeRegistry typeRegistry) {
            if (this.typeRegistry == TypeRegistry.getEmptyTypeRegistry()) {
                return new Printer(this.escapeNonAscii, typeRegistry);
            }
            throw new IllegalArgumentException("Only one typeRegistry is allowed.");
        }

        private void printField(Descriptors.FieldDescriptor fieldDescriptor, Object obj, TextGenerator textGenerator) throws IOException {
            if (!fieldDescriptor.isMapField()) {
                if (!fieldDescriptor.isRepeated()) {
                    printSingleField(fieldDescriptor, obj, textGenerator);
                    return;
                }
                Iterator it = ((List) obj).iterator();
                while (it.hasNext()) {
                    printSingleField(fieldDescriptor, it.next(), textGenerator);
                }
                return;
            }
            ArrayList arrayList = new ArrayList();
            Iterator it2 = ((List) obj).iterator();
            while (it2.hasNext()) {
                arrayList.add(new MapEntryAdapter(it2.next(), fieldDescriptor));
            }
            Collections.sort(arrayList);
            Iterator it3 = arrayList.iterator();
            while (it3.hasNext()) {
                printSingleField(fieldDescriptor, ((MapEntryAdapter) it3.next()).a(), textGenerator);
            }
        }

        private void printFieldValue(Descriptors.FieldDescriptor fieldDescriptor, Object obj, TextGenerator textGenerator) throws IOException {
            switch (AnonymousClass1.f15341b[fieldDescriptor.getType().ordinal()]) {
                case 1:
                case 2:
                case 3:
                    textGenerator.print(((Integer) obj).toString());
                    break;
                case 4:
                case 5:
                case 6:
                    textGenerator.print(((Long) obj).toString());
                    break;
                case 7:
                    textGenerator.print(((Boolean) obj).toString());
                    break;
                case 8:
                    textGenerator.print(((Float) obj).toString());
                    break;
                case 9:
                    textGenerator.print(((Double) obj).toString());
                    break;
                case 10:
                case 11:
                    textGenerator.print(TextFormat.unsignedToString(((Integer) obj).intValue()));
                    break;
                case 12:
                case 13:
                    textGenerator.print(TextFormat.unsignedToString(((Long) obj).longValue()));
                    break;
                case 14:
                    textGenerator.print("\"");
                    textGenerator.print(this.escapeNonAscii ? TextFormatEscaper.e((String) obj) : TextFormat.escapeDoubleQuotesAndBackslashes((String) obj).replace("\n", "\\n"));
                    textGenerator.print("\"");
                    break;
                case 15:
                    textGenerator.print("\"");
                    if (obj instanceof ByteString) {
                        textGenerator.print(TextFormat.escapeBytes((ByteString) obj));
                    } else {
                        textGenerator.print(TextFormat.escapeBytes((byte[]) obj));
                    }
                    textGenerator.print("\"");
                    break;
                case 16:
                    textGenerator.print(((Descriptors.EnumValueDescriptor) obj).getName());
                    break;
                case 17:
                case 18:
                    print((MessageOrBuilder) obj, textGenerator);
                    break;
            }
        }

        public void print(UnknownFieldSet unknownFieldSet, Appendable appendable) throws IOException {
            printUnknownFields(unknownFieldSet, TextFormat.multiLineOutput(appendable));
        }

        private void print(MessageOrBuilder messageOrBuilder, TextGenerator textGenerator) throws IOException {
            if (messageOrBuilder.getDescriptorForType().getFullName().equals("google.protobuf.Any") && printAny(messageOrBuilder, textGenerator)) {
                return;
            }
            printMessage(messageOrBuilder, textGenerator);
        }

        public String printToString(UnknownFieldSet unknownFieldSet) {
            try {
                StringBuilder sb = new StringBuilder();
                print(unknownFieldSet, sb);
                return sb.toString();
            } catch (IOException e2) {
                throw new IllegalStateException(e2);
            }
        }

        public String shortDebugString(Descriptors.FieldDescriptor fieldDescriptor, Object obj) {
            try {
                StringBuilder sb = new StringBuilder();
                printField(fieldDescriptor, obj, TextFormat.singleLineOutput(sb));
                return sb.toString();
            } catch (IOException e2) {
                throw new IllegalStateException(e2);
            }
        }

        public String shortDebugString(UnknownFieldSet unknownFieldSet) {
            try {
                StringBuilder sb = new StringBuilder();
                printUnknownFields(unknownFieldSet, TextFormat.singleLineOutput(sb));
                return sb.toString();
            } catch (IOException e2) {
                throw new IllegalStateException(e2);
            }
        }
    }

    private static final class TextGenerator {
        private boolean atStartOfLine;
        private final StringBuilder indent;
        private final Appendable output;
        private final boolean singleLineMode;

        /* synthetic */ TextGenerator(Appendable appendable, boolean z2, AnonymousClass1 anonymousClass1) {
            this(appendable, z2);
        }

        public void eol() throws IOException {
            if (!this.singleLineMode) {
                this.output.append("\n");
            }
            this.atStartOfLine = true;
        }

        public void indent() {
            this.indent.append("  ");
        }

        public void outdent() {
            int length = this.indent.length();
            if (length == 0) {
                throw new IllegalArgumentException(" Outdent() without matching Indent().");
            }
            this.indent.setLength(length - 2);
        }

        public void print(CharSequence charSequence) throws IOException {
            if (this.atStartOfLine) {
                this.atStartOfLine = false;
                this.output.append(this.singleLineMode ? " " : this.indent);
            }
            this.output.append(charSequence);
        }

        private TextGenerator(Appendable appendable, boolean z2) {
            this.indent = new StringBuilder();
            this.atStartOfLine = false;
            this.output = appendable;
            this.singleLineMode = z2;
        }
    }

    private static final class Tokenizer {
        private int column;
        private boolean containsSilentMarkerAfterCurrentToken;
        private boolean containsSilentMarkerAfterPrevToken;
        private String currentToken;
        private int line;
        private final Matcher matcher;
        private int pos;
        private int previousColumn;
        private int previousLine;
        private final CharSequence text;
        private static final Pattern WHITESPACE = Pattern.compile("(\\s|(#.*$))++", 8);
        private static final Pattern TOKEN = Pattern.compile("[a-zA-Z_][0-9a-zA-Z_+-]*+|[.]?[0-9+-][0-9a-zA-Z_.+-]*+|\"([^\"\n\\\\]|\\\\.)*+(\"|\\\\?$)|'([^'\n\\\\]|\\\\.)*+('|\\\\?$)", 8);
        private static final Pattern DOUBLE_INFINITY = Pattern.compile("-?inf(inity)?", 2);
        private static final Pattern FLOAT_INFINITY = Pattern.compile("-?inf(inity)?f?", 2);
        private static final Pattern FLOAT_NAN = Pattern.compile("nanf?", 2);

        /* synthetic */ Tokenizer(CharSequence charSequence, AnonymousClass1 anonymousClass1) {
            this(charSequence);
        }

        private void consumeByteString(List<ByteString> list) throws ParseException {
            char cCharAt = this.currentToken.length() > 0 ? this.currentToken.charAt(0) : (char) 0;
            if (cCharAt != '\"' && cCharAt != '\'') {
                throw q("Expected string.");
            }
            if (this.currentToken.length() >= 2) {
                String str = this.currentToken;
                if (str.charAt(str.length() - 1) == cCharAt) {
                    try {
                        String str2 = this.currentToken;
                        ByteString byteStringUnescapeBytes = TextFormat.unescapeBytes(str2.substring(1, str2.length() - 1));
                        p();
                        list.add(byteStringUnescapeBytes);
                        return;
                    } catch (InvalidEscapeSequenceException e2) {
                        throw q(e2.getMessage());
                    }
                }
            }
            throw q("String missing ending quote.");
        }

        private ParseException floatParseException(NumberFormatException numberFormatException) {
            return q("Couldn't parse number: " + numberFormatException.getMessage());
        }

        private ParseException integerParseException(NumberFormatException numberFormatException) {
            return q("Couldn't parse integer: " + numberFormatException.getMessage());
        }

        private void skipWhitespace() {
            this.matcher.usePattern(WHITESPACE);
            if (this.matcher.lookingAt()) {
                Matcher matcher = this.matcher;
                matcher.region(matcher.end(), this.matcher.regionEnd());
            }
        }

        boolean b() {
            return this.currentToken.length() == 0;
        }

        void c(String str) throws ParseException {
            if (s(str)) {
                return;
            }
            throw q("Expected \"" + str + "\".");
        }

        public boolean consumeBoolean() throws ParseException {
            if (this.currentToken.equals("true") || this.currentToken.equals("True") || this.currentToken.equals("t") || this.currentToken.equals("1")) {
                p();
                return true;
            }
            if (this.currentToken.equals(RequestConstant.FALSE) || this.currentToken.equals("False") || this.currentToken.equals("f") || this.currentToken.equals("0")) {
                p();
                return false;
            }
            throw q("Expected \"true\" or \"false\". Found \"" + this.currentToken + "\".");
        }

        public double consumeDouble() throws ParseException, NumberFormatException {
            if (DOUBLE_INFINITY.matcher(this.currentToken).matches()) {
                boolean zStartsWith = this.currentToken.startsWith(Constants.ACCEPT_TIME_SEPARATOR_SERVER);
                p();
                return zStartsWith ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
            }
            if (this.currentToken.equalsIgnoreCase("nan")) {
                p();
                return Double.NaN;
            }
            try {
                double d2 = Double.parseDouble(this.currentToken);
                p();
                return d2;
            } catch (NumberFormatException e2) {
                throw floatParseException(e2);
            }
        }

        public float consumeFloat() throws ParseException, NumberFormatException {
            if (FLOAT_INFINITY.matcher(this.currentToken).matches()) {
                boolean zStartsWith = this.currentToken.startsWith(Constants.ACCEPT_TIME_SEPARATOR_SERVER);
                p();
                return zStartsWith ? Float.NEGATIVE_INFINITY : Float.POSITIVE_INFINITY;
            }
            if (FLOAT_NAN.matcher(this.currentToken).matches()) {
                p();
                return Float.NaN;
            }
            try {
                float f2 = Float.parseFloat(this.currentToken);
                p();
                return f2;
            } catch (NumberFormatException e2) {
                throw floatParseException(e2);
            }
        }

        public String consumeString() throws ParseException {
            return d().toStringUtf8();
        }

        ByteString d() throws ParseException {
            ArrayList arrayList = new ArrayList();
            consumeByteString(arrayList);
            while (true) {
                if (!this.currentToken.startsWith("'") && !this.currentToken.startsWith("\"")) {
                    return ByteString.copyFrom(arrayList);
                }
                consumeByteString(arrayList);
            }
        }

        String e() throws ParseException {
            for (int i2 = 0; i2 < this.currentToken.length(); i2++) {
                char cCharAt = this.currentToken.charAt(i2);
                if (('a' > cCharAt || cCharAt > 'z') && (('A' > cCharAt || cCharAt > 'Z') && !(('0' <= cCharAt && cCharAt <= '9') || cCharAt == '_' || cCharAt == '.'))) {
                    throw q("Expected identifier. Found '" + this.currentToken + "'");
                }
            }
            String str = this.currentToken;
            p();
            return str;
        }

        int f() throws ParseException {
            try {
                int iD = TextFormat.d(this.currentToken);
                p();
                return iD;
            } catch (NumberFormatException e2) {
                throw integerParseException(e2);
            }
        }

        long g() throws ParseException {
            try {
                long jE = TextFormat.e(this.currentToken);
                p();
                return jE;
            } catch (NumberFormatException e2) {
                throw integerParseException(e2);
            }
        }

        int h() throws ParseException {
            try {
                int iF = TextFormat.f(this.currentToken);
                p();
                return iF;
            } catch (NumberFormatException e2) {
                throw integerParseException(e2);
            }
        }

        long i() throws ParseException {
            try {
                long jG = TextFormat.g(this.currentToken);
                p();
                return jG;
            } catch (NumberFormatException e2) {
                throw integerParseException(e2);
            }
        }

        int j() {
            return this.column;
        }

        int k() {
            return this.line;
        }

        int l() {
            return this.previousColumn;
        }

        int m() {
            return this.previousLine;
        }

        boolean n(String str) {
            return this.currentToken.equals(str);
        }

        boolean o() {
            if (this.currentToken.length() == 0) {
                return false;
            }
            char cCharAt = this.currentToken.charAt(0);
            return ('0' <= cCharAt && cCharAt <= '9') || cCharAt == '-' || cCharAt == '+';
        }

        void p() {
            this.previousLine = this.line;
            this.previousColumn = this.column;
            while (this.pos < this.matcher.regionStart()) {
                if (this.text.charAt(this.pos) == '\n') {
                    this.line++;
                    this.column = 0;
                } else {
                    this.column++;
                }
                this.pos++;
            }
            if (this.matcher.regionStart() == this.matcher.regionEnd()) {
                this.currentToken = "";
                return;
            }
            this.matcher.usePattern(TOKEN);
            if (this.matcher.lookingAt()) {
                this.currentToken = this.matcher.group();
                Matcher matcher = this.matcher;
                matcher.region(matcher.end(), this.matcher.regionEnd());
            } else {
                this.currentToken = String.valueOf(this.text.charAt(this.pos));
                Matcher matcher2 = this.matcher;
                matcher2.region(this.pos + 1, matcher2.regionEnd());
            }
            skipWhitespace();
        }

        ParseException q(String str) {
            return new ParseException(this.line + 1, this.column + 1, str);
        }

        ParseException r(String str) {
            return new ParseException(this.previousLine + 1, this.previousColumn + 1, str);
        }

        boolean s(String str) {
            if (!this.currentToken.equals(str)) {
                return false;
            }
            p();
            return true;
        }

        boolean t() {
            try {
                e();
                return true;
            } catch (ParseException unused) {
                return false;
            }
        }

        public boolean tryConsumeDouble() throws NumberFormatException {
            try {
                consumeDouble();
                return true;
            } catch (ParseException unused) {
                return false;
            }
        }

        public boolean tryConsumeFloat() throws NumberFormatException {
            try {
                consumeFloat();
                return true;
            } catch (ParseException unused) {
                return false;
            }
        }

        public boolean tryConsumeUInt64() {
            try {
                i();
                return true;
            } catch (ParseException unused) {
                return false;
            }
        }

        boolean u() {
            try {
                g();
                return true;
            } catch (ParseException unused) {
                return false;
            }
        }

        boolean v() {
            try {
                consumeString();
                return true;
            } catch (ParseException unused) {
                return false;
            }
        }

        private Tokenizer(CharSequence charSequence) {
            this.pos = 0;
            this.line = 0;
            this.column = 0;
            this.previousLine = 0;
            this.previousColumn = 0;
            this.containsSilentMarkerAfterCurrentToken = false;
            this.containsSilentMarkerAfterPrevToken = false;
            this.text = charSequence;
            this.matcher = WHITESPACE.matcher(charSequence);
            skipWhitespace();
            p();
        }
    }

    public static class UnknownFieldParseException extends ParseException {
        private final String unknownField;

        public UnknownFieldParseException(String str) {
            this(-1, -1, "", str);
        }

        public String getUnknownField() {
            return this.unknownField;
        }

        public UnknownFieldParseException(int i2, int i3, String str, String str2) {
            super(i2, i3, str2);
            this.unknownField = str;
        }
    }

    private TextFormat() {
    }

    static int d(String str) {
        return (int) parseInteger(str, true, false);
    }

    private static int digitValue(byte b2) {
        return (48 > b2 || b2 > 57) ? (97 > b2 || b2 > 122) ? b2 - 55 : b2 - 87 : b2 - Constants.CMD_TYPE.CMD_AUDIO_UPSTREAM;
    }

    static long e(String str) {
        return parseInteger(str, true, true);
    }

    public static String escapeBytes(ByteString byteString) {
        return TextFormatEscaper.a(byteString);
    }

    public static String escapeDoubleQuotesAndBackslashes(String str) {
        return TextFormatEscaper.d(str);
    }

    static int f(String str) {
        return (int) parseInteger(str, false, false);
    }

    static long g(String str) {
        return parseInteger(str, false, true);
    }

    public static Parser getParser() {
        return PARSER;
    }

    private static boolean isHex(byte b2) {
        return (48 <= b2 && b2 <= 57) || (97 <= b2 && b2 <= 102) || (65 <= b2 && b2 <= 70);
    }

    private static boolean isOctal(byte b2) {
        return 48 <= b2 && b2 <= 55;
    }

    public static void merge(Readable readable, Message.Builder builder) throws IOException {
        PARSER.merge(readable, builder);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static TextGenerator multiLineOutput(Appendable appendable) {
        return new TextGenerator(appendable, false, null);
    }

    public static <T extends Message> T parse(CharSequence charSequence, Class<T> cls) throws ParseException {
        Message.Builder builderNewBuilderForType = ((Message) Internal.getDefaultInstance(cls)).newBuilderForType();
        merge(charSequence, builderNewBuilderForType);
        return (T) builderNewBuilderForType.build();
    }

    private static long parseInteger(String str, boolean z2, boolean z3) throws NumberFormatException {
        int i2;
        int i3 = 0;
        if (str.startsWith(com.xiaomi.mipush.sdk.Constants.ACCEPT_TIME_SEPARATOR_SERVER, 0)) {
            if (!z2) {
                throw new NumberFormatException("Number must be positive: " + str);
            }
            i3 = 1;
        }
        int i4 = i3;
        if (str.startsWith("0x", i3)) {
            i3 += 2;
            i2 = 16;
        } else {
            i2 = str.startsWith("0", i3) ? 8 : 10;
        }
        String strSubstring = str.substring(i3);
        if (strSubstring.length() < 16) {
            long j2 = Long.parseLong(strSubstring, i2);
            if (i4 != 0) {
                j2 = -j2;
            }
            if (z3) {
                return j2;
            }
            if (z2) {
                if (j2 <= 2147483647L && j2 >= -2147483648L) {
                    return j2;
                }
                throw new NumberFormatException("Number out of range for 32-bit signed integer: " + str);
            }
            if (j2 < Conversions.THIRTYTWO_BIT && j2 >= 0) {
                return j2;
            }
            throw new NumberFormatException("Number out of range for 32-bit unsigned integer: " + str);
        }
        BigInteger bigInteger = new BigInteger(strSubstring, i2);
        if (i4 != 0) {
            bigInteger = bigInteger.negate();
        }
        if (z3) {
            if (z2) {
                if (bigInteger.bitLength() > 63) {
                    throw new NumberFormatException("Number out of range for 64-bit signed integer: " + str);
                }
            } else if (bigInteger.bitLength() > 64) {
                throw new NumberFormatException("Number out of range for 64-bit unsigned integer: " + str);
            }
        } else if (z2) {
            if (bigInteger.bitLength() > 31) {
                throw new NumberFormatException("Number out of range for 32-bit signed integer: " + str);
            }
        } else if (bigInteger.bitLength() > 32) {
            throw new NumberFormatException("Number out of range for 32-bit unsigned integer: " + str);
        }
        return bigInteger.longValue();
    }

    @InlineMe(imports = {"com.google.protobuf.TextFormat"}, replacement = "TextFormat.printer().print(message, output)")
    @Deprecated
    public static void print(MessageOrBuilder messageOrBuilder, Appendable appendable) throws IOException {
        printer().print(messageOrBuilder, appendable);
    }

    @Deprecated
    public static void printField(Descriptors.FieldDescriptor fieldDescriptor, Object obj, Appendable appendable) throws IOException {
        printer().printField(fieldDescriptor, obj, appendable);
    }

    @Deprecated
    public static String printFieldToString(Descriptors.FieldDescriptor fieldDescriptor, Object obj) {
        return printer().printFieldToString(fieldDescriptor, obj);
    }

    @InlineMe(imports = {"com.google.protobuf.TextFormat"}, replacement = "TextFormat.printer().printFieldValue(field, value, output)")
    @Deprecated
    public static void printFieldValue(Descriptors.FieldDescriptor fieldDescriptor, Object obj, Appendable appendable) throws IOException {
        printer().printFieldValue(fieldDescriptor, obj, appendable);
    }

    @InlineMe(imports = {"com.google.protobuf.TextFormat"}, replacement = "TextFormat.printer().printToString(message)")
    @Deprecated
    public static String printToString(MessageOrBuilder messageOrBuilder) {
        return printer().printToString(messageOrBuilder);
    }

    @InlineMe(imports = {"com.google.protobuf.TextFormat"}, replacement = "TextFormat.printer().escapingNonAscii(false).printToString(message)")
    @Deprecated
    public static String printToUnicodeString(MessageOrBuilder messageOrBuilder) {
        return printer().escapingNonAscii(false).printToString(messageOrBuilder);
    }

    @InlineMe(imports = {"com.google.protobuf.TextFormat"}, replacement = "TextFormat.printer().escapingNonAscii(false).print(message, output)")
    @Deprecated
    public static void printUnicode(MessageOrBuilder messageOrBuilder, Appendable appendable) throws IOException {
        printer().escapingNonAscii(false).print(messageOrBuilder, appendable);
    }

    @Deprecated
    public static void printUnicodeFieldValue(Descriptors.FieldDescriptor fieldDescriptor, Object obj, Appendable appendable) throws IOException {
        printer().escapingNonAscii(false).printFieldValue(fieldDescriptor, obj, appendable);
    }

    public static void printUnknownFieldValue(int i2, Object obj, Appendable appendable) throws IOException {
        printUnknownFieldValue(i2, obj, multiLineOutput(appendable));
    }

    public static Printer printer() {
        return Printer.DEFAULT;
    }

    public static String shortDebugString(MessageOrBuilder messageOrBuilder) {
        return printer().shortDebugString(messageOrBuilder);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static TextGenerator singleLineOutput(Appendable appendable) {
        return new TextGenerator(appendable, true, null);
    }

    public static ByteString unescapeBytes(CharSequence charSequence) throws InvalidEscapeSequenceException {
        int i2;
        int i3;
        int length;
        int i4;
        ByteString byteStringCopyFromUtf8 = ByteString.copyFromUtf8(charSequence.toString());
        int size = byteStringCopyFromUtf8.size();
        byte[] bArr = new byte[size];
        int i5 = 0;
        int i6 = 0;
        while (i5 < byteStringCopyFromUtf8.size()) {
            byte bByteAt = byteStringCopyFromUtf8.byteAt(i5);
            if (bByteAt == 92) {
                int i7 = i5 + 1;
                if (i7 >= byteStringCopyFromUtf8.size()) {
                    throw new InvalidEscapeSequenceException("Invalid escape sequence: '\\' at end of string.");
                }
                byte bByteAt2 = byteStringCopyFromUtf8.byteAt(i7);
                if (isOctal(bByteAt2)) {
                    int iDigitValue = digitValue(bByteAt2);
                    int i8 = i5 + 2;
                    if (i8 < byteStringCopyFromUtf8.size() && isOctal(byteStringCopyFromUtf8.byteAt(i8))) {
                        iDigitValue = (iDigitValue * 8) + digitValue(byteStringCopyFromUtf8.byteAt(i8));
                        i7 = i8;
                    }
                    i5 = i7 + 1;
                    if (i5 >= byteStringCopyFromUtf8.size() || !isOctal(byteStringCopyFromUtf8.byteAt(i5))) {
                        i5 = i7;
                    } else {
                        iDigitValue = (iDigitValue * 8) + digitValue(byteStringCopyFromUtf8.byteAt(i5));
                    }
                    i4 = i6 + 1;
                    bArr[i6] = (byte) iDigitValue;
                } else {
                    if (bByteAt2 == 34) {
                        i2 = i6 + 1;
                        bArr[i6] = Constants.CMD_TYPE.CMD_REQUEST_OTA;
                    } else if (bByteAt2 == 39) {
                        i2 = i6 + 1;
                        bArr[i6] = 39;
                    } else if (bByteAt2 != 63) {
                        if (bByteAt2 == 85) {
                            int i9 = i5 + 2;
                            i3 = i5 + 9;
                            if (i3 >= byteStringCopyFromUtf8.size()) {
                                throw new InvalidEscapeSequenceException("Invalid escape sequence: '\\U' with too few hex chars");
                            }
                            int iDigitValue2 = 0;
                            int i10 = i9;
                            while (true) {
                                int i11 = i5 + 10;
                                if (i10 < i11) {
                                    byte bByteAt3 = byteStringCopyFromUtf8.byteAt(i10);
                                    if (!isHex(bByteAt3)) {
                                        throw new InvalidEscapeSequenceException("Invalid escape sequence: '\\U' with too few hex chars");
                                    }
                                    iDigitValue2 = (iDigitValue2 << 4) | digitValue(bByteAt3);
                                    i10++;
                                } else {
                                    if (!Character.isValidCodePoint(iDigitValue2)) {
                                        throw new InvalidEscapeSequenceException("Invalid escape sequence: '\\U" + byteStringCopyFromUtf8.substring(i9, i11).toStringUtf8() + "' is not a valid code point value");
                                    }
                                    Character.UnicodeBlock unicodeBlockOf = Character.UnicodeBlock.of(iDigitValue2);
                                    if (unicodeBlockOf != null && (unicodeBlockOf.equals(Character.UnicodeBlock.LOW_SURROGATES) || unicodeBlockOf.equals(Character.UnicodeBlock.HIGH_SURROGATES) || unicodeBlockOf.equals(Character.UnicodeBlock.HIGH_PRIVATE_USE_SURROGATES))) {
                                        throw new InvalidEscapeSequenceException("Invalid escape sequence: '\\U" + byteStringCopyFromUtf8.substring(i9, i11).toStringUtf8() + "' refers to a surrogate code unit");
                                    }
                                    byte[] bytes = new String(new int[]{iDigitValue2}, 0, 1).getBytes(Internal.f15296b);
                                    System.arraycopy(bytes, 0, bArr, i6, bytes.length);
                                    length = bytes.length;
                                }
                            }
                        } else if (bByteAt2 == 92) {
                            i2 = i6 + 1;
                            bArr[i6] = 92;
                        } else if (bByteAt2 == 102) {
                            i2 = i6 + 1;
                            bArr[i6] = 12;
                        } else if (bByteAt2 == 110) {
                            i2 = i6 + 1;
                            bArr[i6] = 10;
                        } else if (bByteAt2 == 114) {
                            i2 = i6 + 1;
                            bArr[i6] = 13;
                        } else if (bByteAt2 == 120) {
                            int i12 = i5 + 2;
                            if (i12 >= byteStringCopyFromUtf8.size() || !isHex(byteStringCopyFromUtf8.byteAt(i12))) {
                                throw new InvalidEscapeSequenceException("Invalid escape sequence: '\\x' with no digits");
                            }
                            int iDigitValue3 = digitValue(byteStringCopyFromUtf8.byteAt(i12));
                            i5 += 3;
                            if (i5 >= byteStringCopyFromUtf8.size() || !isHex(byteStringCopyFromUtf8.byteAt(i5))) {
                                i5 = i12;
                            } else {
                                iDigitValue3 = (iDigitValue3 * 16) + digitValue(byteStringCopyFromUtf8.byteAt(i5));
                            }
                            i4 = i6 + 1;
                            bArr[i6] = (byte) iDigitValue3;
                        } else if (bByteAt2 == 97) {
                            i2 = i6 + 1;
                            bArr[i6] = 7;
                        } else if (bByteAt2 != 98) {
                            switch (bByteAt2) {
                                case 116:
                                    i2 = i6 + 1;
                                    bArr[i6] = 9;
                                    break;
                                case 117:
                                    int i13 = i5 + 2;
                                    i3 = i5 + 5;
                                    if (i3 < byteStringCopyFromUtf8.size() && isHex(byteStringCopyFromUtf8.byteAt(i13))) {
                                        int i14 = i5 + 3;
                                        if (isHex(byteStringCopyFromUtf8.byteAt(i14))) {
                                            int i15 = i5 + 4;
                                            if (isHex(byteStringCopyFromUtf8.byteAt(i15)) && isHex(byteStringCopyFromUtf8.byteAt(i3))) {
                                                char cDigitValue = (char) ((digitValue(byteStringCopyFromUtf8.byteAt(i15)) << 4) | (digitValue(byteStringCopyFromUtf8.byteAt(i13)) << 12) | (digitValue(byteStringCopyFromUtf8.byteAt(i14)) << 8) | digitValue(byteStringCopyFromUtf8.byteAt(i3)));
                                                if (cDigitValue >= 55296 && cDigitValue <= 57343) {
                                                    throw new InvalidEscapeSequenceException("Invalid escape sequence: '\\u' refers to a surrogate");
                                                }
                                                byte[] bytes2 = Character.toString(cDigitValue).getBytes(Internal.f15296b);
                                                System.arraycopy(bytes2, 0, bArr, i6, bytes2.length);
                                                length = bytes2.length;
                                                break;
                                            }
                                        }
                                    }
                                    throw new InvalidEscapeSequenceException("Invalid escape sequence: '\\u' with too few hex chars");
                                case 118:
                                    i2 = i6 + 1;
                                    bArr[i6] = 11;
                                    break;
                                default:
                                    throw new InvalidEscapeSequenceException("Invalid escape sequence: '\\" + ((char) bByteAt2) + '\'');
                            }
                        } else {
                            i2 = i6 + 1;
                            bArr[i6] = 8;
                        }
                        i6 += length;
                        i5 = i3;
                    } else {
                        i2 = i6 + 1;
                        bArr[i6] = 63;
                    }
                    i6 = i2;
                    i5 = i7;
                }
                i6 = i4;
            } else {
                bArr[i6] = bByteAt;
                i6++;
            }
            i5++;
        }
        return size == i6 ? ByteString.wrap(bArr) : ByteString.copyFrom(bArr, 0, i6);
    }

    public static String unsignedToString(int i2) {
        return i2 >= 0 ? Integer.toString(i2) : Long.toString(i2 & 4294967295L);
    }

    public static String escapeBytes(byte[] bArr) {
        return TextFormatEscaper.c(bArr);
    }

    public static void merge(CharSequence charSequence, Message.Builder builder) throws ParseException {
        PARSER.merge(charSequence, builder);
    }

    @Deprecated
    public static void print(UnknownFieldSet unknownFieldSet, Appendable appendable) throws IOException {
        printer().print(unknownFieldSet, appendable);
    }

    @Deprecated
    public static String printToString(UnknownFieldSet unknownFieldSet) {
        return printer().printToString(unknownFieldSet);
    }

    @Deprecated
    public static String printToUnicodeString(UnknownFieldSet unknownFieldSet) {
        return printer().escapingNonAscii(false).printToString(unknownFieldSet);
    }

    @Deprecated
    public static void printUnicode(UnknownFieldSet unknownFieldSet, Appendable appendable) throws IOException {
        printer().escapingNonAscii(false).print(unknownFieldSet, appendable);
    }

    private static void printUnknownFieldValue(int i2, Object obj, TextGenerator textGenerator) throws IOException {
        int tagWireType = WireFormat.getTagWireType(i2);
        if (tagWireType == 0) {
            textGenerator.print(unsignedToString(((Long) obj).longValue()));
            return;
        }
        if (tagWireType == 1) {
            textGenerator.print(String.format(null, "0x%016x", (Long) obj));
            return;
        }
        if (tagWireType != 2) {
            if (tagWireType == 3) {
                Printer.printUnknownFields((UnknownFieldSet) obj, textGenerator);
                return;
            } else {
                if (tagWireType == 5) {
                    textGenerator.print(String.format(null, "0x%08x", (Integer) obj));
                    return;
                }
                throw new IllegalArgumentException("Bad tag: " + i2);
            }
        }
        try {
            UnknownFieldSet from = UnknownFieldSet.parseFrom((ByteString) obj);
            textGenerator.print("{");
            textGenerator.eol();
            textGenerator.indent();
            Printer.printUnknownFields(from, textGenerator);
            textGenerator.outdent();
            textGenerator.print(com.alipay.sdk.m.u.i.f9804d);
        } catch (InvalidProtocolBufferException unused) {
            textGenerator.print("\"");
            textGenerator.print(escapeBytes((ByteString) obj));
            textGenerator.print("\"");
        }
    }

    @Deprecated
    public static String shortDebugString(Descriptors.FieldDescriptor fieldDescriptor, Object obj) {
        return printer().shortDebugString(fieldDescriptor, obj);
    }

    public static void merge(Readable readable, ExtensionRegistry extensionRegistry, Message.Builder builder) throws IOException {
        PARSER.merge(readable, extensionRegistry, builder);
    }

    @Deprecated
    public static String shortDebugString(UnknownFieldSet unknownFieldSet) {
        return printer().shortDebugString(unknownFieldSet);
    }

    public static String unsignedToString(long j2) {
        if (j2 >= 0) {
            return Long.toString(j2);
        }
        return BigInteger.valueOf(j2 & Long.MAX_VALUE).setBit(63).toString();
    }

    public static void merge(CharSequence charSequence, ExtensionRegistry extensionRegistry, Message.Builder builder) throws ParseException {
        PARSER.merge(charSequence, extensionRegistry, builder);
    }

    public static <T extends Message> T parse(CharSequence charSequence, ExtensionRegistry extensionRegistry, Class<T> cls) throws ParseException {
        Message.Builder builderNewBuilderForType = ((Message) Internal.getDefaultInstance(cls)).newBuilderForType();
        merge(charSequence, extensionRegistry, builderNewBuilderForType);
        return (T) builderNewBuilderForType.build();
    }
}
