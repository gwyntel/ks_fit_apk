package com.google.protobuf.util;

import com.google.common.base.Splitter;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.protobuf.Descriptors;
import com.google.protobuf.FieldMask;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.Message;
import com.google.protobuf.util.FieldMaskUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Logger;

/* loaded from: classes2.dex */
final class FieldMaskTree {
    private static final String FIELD_PATH_SEPARATOR_REGEX = "\\.";
    private static final Logger logger = Logger.getLogger(FieldMaskTree.class.getName());
    private final Node root = new Node();

    private static final class Node {

        /* renamed from: a, reason: collision with root package name */
        final SortedMap f15391a;

        private Node() {
            this.f15391a = new TreeMap();
        }
    }

    FieldMaskTree() {
    }

    private static void getFieldPaths(Node node, String str, List<String> list) {
        if (node.f15391a.isEmpty()) {
            list.add(str);
            return;
        }
        for (Map.Entry entry : node.f15391a.entrySet()) {
            getFieldPaths((Node) entry.getValue(), str.isEmpty() ? (String) entry.getKey() : str + "." + ((String) entry.getKey()), list);
        }
    }

    private static void merge(Node node, Message message, Message.Builder builder, FieldMaskUtil.MergeOptions mergeOptions) {
        if (message.getDescriptorForType() != builder.getDescriptorForType()) {
            throw new IllegalArgumentException(String.format("source (%s) and destination (%s) descriptor must be equal", message.getDescriptorForType().getFullName(), builder.getDescriptorForType().getFullName()));
        }
        Descriptors.Descriptor descriptorForType = message.getDescriptorForType();
        for (Map.Entry entry : node.f15391a.entrySet()) {
            Descriptors.FieldDescriptor fieldDescriptorFindFieldByName = descriptorForType.findFieldByName((String) entry.getKey());
            if (fieldDescriptorFindFieldByName == null) {
                logger.warning("Cannot find field \"" + ((String) entry.getKey()) + "\" in message type " + descriptorForType.getFullName());
            } else if (((Node) entry.getValue()).f15391a.isEmpty()) {
                if (fieldDescriptorFindFieldByName.isRepeated()) {
                    if (mergeOptions.replaceRepeatedFields()) {
                        builder.setField(fieldDescriptorFindFieldByName, message.getField(fieldDescriptorFindFieldByName));
                    } else {
                        Iterator it = ((List) message.getField(fieldDescriptorFindFieldByName)).iterator();
                        while (it.hasNext()) {
                            builder.addRepeatedField(fieldDescriptorFindFieldByName, it.next());
                        }
                    }
                } else if (fieldDescriptorFindFieldByName.getJavaType() == Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                    if (mergeOptions.replaceMessageFields()) {
                        if (message.hasField(fieldDescriptorFindFieldByName)) {
                            builder.setField(fieldDescriptorFindFieldByName, message.getField(fieldDescriptorFindFieldByName));
                        } else {
                            builder.clearField(fieldDescriptorFindFieldByName);
                        }
                    } else if (message.hasField(fieldDescriptorFindFieldByName)) {
                        builder.setField(fieldDescriptorFindFieldByName, ((Message) builder.getField(fieldDescriptorFindFieldByName)).toBuilder().mergeFrom((Message) message.getField(fieldDescriptorFindFieldByName)).build());
                    }
                } else if (message.hasField(fieldDescriptorFindFieldByName) || !mergeOptions.replacePrimitiveFields()) {
                    builder.setField(fieldDescriptorFindFieldByName, message.getField(fieldDescriptorFindFieldByName));
                } else {
                    builder.clearField(fieldDescriptorFindFieldByName);
                }
            } else if (fieldDescriptorFindFieldByName.isRepeated() || fieldDescriptorFindFieldByName.getJavaType() != Descriptors.FieldDescriptor.JavaType.MESSAGE) {
                logger.warning("Field \"" + fieldDescriptorFindFieldByName.getFullName() + "\" is not a singular message field and cannot have sub-fields.");
            } else if (message.hasField(fieldDescriptorFindFieldByName) || builder.hasField(fieldDescriptorFindFieldByName)) {
                Message.Builder fieldBuilder = builder instanceof GeneratedMessage.Builder ? builder.getFieldBuilder(fieldDescriptorFindFieldByName) : ((Message) builder.getField(fieldDescriptorFindFieldByName)).toBuilder();
                merge((Node) entry.getValue(), (Message) message.getField(fieldDescriptorFindFieldByName), fieldBuilder, mergeOptions);
                builder.setField(fieldDescriptorFindFieldByName, fieldBuilder.buildPartial());
            }
        }
    }

    @CanIgnoreReturnValue
    private static boolean removeFieldPath(Node node, List<String> list, int i2) {
        String str = list.get(i2);
        if (!node.f15391a.containsKey(str)) {
            return false;
        }
        if (i2 == list.size() - 1) {
            node.f15391a.remove(str);
            return node.f15391a.isEmpty();
        }
        if (removeFieldPath((Node) node.f15391a.get(str), list, i2 + 1)) {
            node.f15391a.remove(str);
        }
        return node.f15391a.isEmpty();
    }

    FieldMaskTree a(String str) {
        String[] strArrSplit = str.split(FIELD_PATH_SEPARATOR_REGEX);
        if (strArrSplit.length == 0) {
            return this;
        }
        Node node = this.root;
        boolean z2 = false;
        for (String str2 : strArrSplit) {
            if (!z2 && node != this.root && node.f15391a.isEmpty()) {
                return this;
            }
            if (node.f15391a.containsKey(str2)) {
                node = (Node) node.f15391a.get(str2);
            } else {
                Node node2 = new Node();
                node.f15391a.put(str2, node2);
                z2 = true;
                node = node2;
            }
        }
        node.f15391a.clear();
        return this;
    }

    void b(String str, FieldMaskTree fieldMaskTree) {
        if (this.root.f15391a.isEmpty()) {
            return;
        }
        String[] strArrSplit = str.split(FIELD_PATH_SEPARATOR_REGEX);
        if (strArrSplit.length == 0) {
            return;
        }
        Node node = this.root;
        for (String str2 : strArrSplit) {
            if (node != this.root && node.f15391a.isEmpty()) {
                fieldMaskTree.a(str);
                return;
            } else {
                if (!node.f15391a.containsKey(str2)) {
                    return;
                }
                node = (Node) node.f15391a.get(str2);
            }
        }
        ArrayList arrayList = new ArrayList();
        getFieldPaths(node, str, arrayList);
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            fieldMaskTree.a((String) it.next());
        }
    }

    void c(Message message, Message.Builder builder, FieldMaskUtil.MergeOptions mergeOptions) {
        if (message.getDescriptorForType() != builder.getDescriptorForType()) {
            throw new IllegalArgumentException("Cannot merge messages of different types.");
        }
        if (this.root.f15391a.isEmpty()) {
            return;
        }
        merge(this.root, message, builder, mergeOptions);
    }

    FieldMaskTree d(FieldMask fieldMask) {
        Iterator<String> it = fieldMask.getPathsList().iterator();
        while (it.hasNext()) {
            a(it.next());
        }
        return this;
    }

    FieldMaskTree e(String str) {
        List<String> listSplitToList = Splitter.onPattern(FIELD_PATH_SEPARATOR_REGEX).splitToList(str);
        if (listSplitToList.isEmpty()) {
            return this;
        }
        removeFieldPath(this.root, listSplitToList, 0);
        return this;
    }

    FieldMaskTree f(FieldMask fieldMask) {
        Iterator<String> it = fieldMask.getPathsList().iterator();
        while (it.hasNext()) {
            e(it.next());
        }
        return this;
    }

    FieldMask g() {
        if (this.root.f15391a.isEmpty()) {
            return FieldMask.getDefaultInstance();
        }
        ArrayList arrayList = new ArrayList();
        getFieldPaths(this.root, "", arrayList);
        return FieldMask.newBuilder().addAllPaths(arrayList).build();
    }

    public String toString() {
        return FieldMaskUtil.toString(g());
    }

    FieldMaskTree(FieldMask fieldMask) {
        d(fieldMask);
    }
}
