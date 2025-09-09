package com.google.protobuf.util;

import com.google.common.base.CaseFormat;
import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.protobuf.Descriptors;
import com.google.protobuf.FieldMask;
import com.google.protobuf.Internal;
import com.google.protobuf.Message;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import javax.annotation.Nullable;

/* loaded from: classes2.dex */
public final class FieldMaskUtil {
    private static final String FIELD_PATH_SEPARATOR = ",";
    private static final String FIELD_PATH_SEPARATOR_REGEX = ",";
    private static final String FIELD_SEPARATOR_REGEX = "\\.";

    public static final class MergeOptions {
        private boolean replaceMessageFields = false;
        private boolean replaceRepeatedFields = false;
        private boolean replacePrimitiveFields = false;

        public boolean replaceMessageFields() {
            return this.replaceMessageFields;
        }

        public boolean replacePrimitiveFields() {
            return this.replacePrimitiveFields;
        }

        public boolean replaceRepeatedFields() {
            return this.replaceRepeatedFields;
        }

        @CanIgnoreReturnValue
        public MergeOptions setReplaceMessageFields(boolean z2) {
            this.replaceMessageFields = z2;
            return this;
        }

        @CanIgnoreReturnValue
        public MergeOptions setReplacePrimitiveFields(boolean z2) {
            this.replacePrimitiveFields = z2;
            return this;
        }

        @CanIgnoreReturnValue
        public MergeOptions setReplaceRepeatedFields(boolean z2) {
            this.replaceRepeatedFields = z2;
            return this;
        }
    }

    private FieldMaskUtil() {
    }

    public static FieldMask fromFieldNumbers(Class<? extends Message> cls, int... iArr) {
        return fromFieldNumbers(cls, Ints.asList(iArr));
    }

    public static FieldMask fromJsonString(String str) {
        Iterable<String> iterableSplit = Splitter.on(",").split(str);
        FieldMask.Builder builderNewBuilder = FieldMask.newBuilder();
        for (String str2 : iterableSplit) {
            if (!str2.isEmpty()) {
                builderNewBuilder.addPaths(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, str2));
            }
        }
        return builderNewBuilder.build();
    }

    public static FieldMask fromString(String str) {
        return fromStringList(Arrays.asList(str.split(",")));
    }

    public static FieldMask fromStringList(Class<? extends Message> cls, Iterable<String> iterable) {
        return fromStringList(((Message) Internal.getDefaultInstance(cls)).getDescriptorForType(), iterable);
    }

    public static FieldMask intersection(FieldMask fieldMask, FieldMask fieldMask2) {
        FieldMaskTree fieldMaskTree = new FieldMaskTree(fieldMask);
        FieldMaskTree fieldMaskTree2 = new FieldMaskTree();
        Iterator<String> it = fieldMask2.getPathsList().iterator();
        while (it.hasNext()) {
            fieldMaskTree.b(it.next(), fieldMaskTree2);
        }
        return fieldMaskTree2.g();
    }

    public static boolean isValid(Class<? extends Message> cls, FieldMask fieldMask) {
        return isValid(((Message) Internal.getDefaultInstance(cls)).getDescriptorForType(), fieldMask);
    }

    public static void merge(FieldMask fieldMask, Message message, Message.Builder builder, MergeOptions mergeOptions) {
        new FieldMaskTree(fieldMask).c(message, builder, mergeOptions);
    }

    public static FieldMask normalize(FieldMask fieldMask) {
        return new FieldMaskTree(fieldMask).g();
    }

    public static FieldMask subtract(FieldMask fieldMask, FieldMask fieldMask2, FieldMask... fieldMaskArr) {
        FieldMaskTree fieldMaskTreeF = new FieldMaskTree(fieldMask).f(fieldMask2);
        for (FieldMask fieldMask3 : fieldMaskArr) {
            fieldMaskTreeF.f(fieldMask3);
        }
        return fieldMaskTreeF.g();
    }

    public static String toJsonString(FieldMask fieldMask) {
        ArrayList arrayList = new ArrayList(fieldMask.getPathsCount());
        for (String str : fieldMask.getPathsList()) {
            if (!str.isEmpty()) {
                arrayList.add(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, str));
            }
        }
        return Joiner.on(",").join(arrayList);
    }

    public static String toString(FieldMask fieldMask) {
        StringBuilder sb = new StringBuilder();
        boolean z2 = true;
        for (String str : fieldMask.getPathsList()) {
            if (!str.isEmpty()) {
                if (z2) {
                    z2 = false;
                } else {
                    sb.append(",");
                }
                sb.append(str);
            }
        }
        return sb.toString();
    }

    public static <P extends Message> P trim(FieldMask fieldMask, P p2) {
        Message.Builder builderNewBuilderForType = p2.newBuilderForType();
        merge(fieldMask, p2, builderNewBuilderForType);
        return (P) builderNewBuilderForType.build();
    }

    public static FieldMask union(FieldMask fieldMask, FieldMask fieldMask2, FieldMask... fieldMaskArr) {
        FieldMaskTree fieldMaskTreeD = new FieldMaskTree(fieldMask).d(fieldMask2);
        for (FieldMask fieldMask3 : fieldMaskArr) {
            fieldMaskTreeD.d(fieldMask3);
        }
        return fieldMaskTreeD.g();
    }

    public static FieldMask fromFieldNumbers(Class<? extends Message> cls, Iterable<Integer> iterable) {
        Descriptors.Descriptor descriptorForType = ((Message) Internal.getDefaultInstance(cls)).getDescriptorForType();
        FieldMask.Builder builderNewBuilder = FieldMask.newBuilder();
        for (Integer num : iterable) {
            Descriptors.FieldDescriptor fieldDescriptorFindFieldByNumber = descriptorForType.findFieldByNumber(num.intValue());
            Preconditions.checkArgument(fieldDescriptorFindFieldByNumber != null, String.format("%s is not a valid field number for %s.", num, cls));
            builderNewBuilder.addPaths(fieldDescriptorFindFieldByNumber.getName());
        }
        return builderNewBuilder.build();
    }

    public static FieldMask fromString(Class<? extends Message> cls, String str) {
        return fromStringList(cls, Arrays.asList(str.split(",")));
    }

    public static FieldMask fromStringList(Descriptors.Descriptor descriptor, Iterable<String> iterable) {
        return fromStringList((Optional<Descriptors.Descriptor>) Optional.of(descriptor), iterable);
    }

    public static void merge(FieldMask fieldMask, Message message, Message.Builder builder) {
        merge(fieldMask, message, builder, new MergeOptions());
    }

    public static FieldMask fromStringList(Iterable<String> iterable) {
        return fromStringList((Optional<Descriptors.Descriptor>) Optional.absent(), iterable);
    }

    public static boolean isValid(Descriptors.Descriptor descriptor, FieldMask fieldMask) {
        Iterator<String> it = fieldMask.getPathsList().iterator();
        while (it.hasNext()) {
            if (!isValid(descriptor, it.next())) {
                return false;
            }
        }
        return true;
    }

    private static FieldMask fromStringList(Optional<Descriptors.Descriptor> optional, Iterable<String> iterable) {
        FieldMask.Builder builderNewBuilder = FieldMask.newBuilder();
        for (String str : iterable) {
            if (!str.isEmpty()) {
                if (optional.isPresent() && !isValid(optional.get(), str)) {
                    throw new IllegalArgumentException(str + " is not a valid path for " + optional.get().getFullName());
                }
                builderNewBuilder.addPaths(str);
            }
        }
        return builderNewBuilder.build();
    }

    public static boolean isValid(Class<? extends Message> cls, String str) {
        return isValid(((Message) Internal.getDefaultInstance(cls)).getDescriptorForType(), str);
    }

    public static boolean isValid(@Nullable Descriptors.Descriptor descriptor, String str) {
        Descriptors.FieldDescriptor fieldDescriptorFindFieldByName;
        String[] strArrSplit = str.split(FIELD_SEPARATOR_REGEX);
        if (strArrSplit.length == 0) {
            return false;
        }
        for (String str2 : strArrSplit) {
            if (descriptor == null || (fieldDescriptorFindFieldByName = descriptor.findFieldByName(str2)) == null) {
                return false;
            }
            descriptor = (fieldDescriptorFindFieldByName.isRepeated() || fieldDescriptorFindFieldByName.getJavaType() != Descriptors.FieldDescriptor.JavaType.MESSAGE) ? null : fieldDescriptorFindFieldByName.getMessageType();
        }
        return true;
    }
}
