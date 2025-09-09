package com.google.protobuf;

import com.google.protobuf.Internal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@CheckReturnValue
/* loaded from: classes2.dex */
abstract class ListFieldSchema {
    private static final ListFieldSchema FULL_INSTANCE;
    private static final ListFieldSchema LITE_INSTANCE;

    private static final class ListFieldSchemaFull extends ListFieldSchema {
        private static final Class<?> UNMODIFIABLE_LIST_CLASS = Collections.unmodifiableList(Collections.emptyList()).getClass();

        private ListFieldSchemaFull() {
            super();
        }

        static List f(Object obj, long j2) {
            return (List) UnsafeUtil.x(obj, j2);
        }

        /* JADX WARN: Multi-variable type inference failed */
        private static <L> List<L> mutableListAt(Object obj, long j2, int i2) {
            LazyStringArrayList lazyStringArrayList;
            List<L> listF = f(obj, j2);
            if (listF.isEmpty()) {
                List<L> lazyStringArrayList2 = listF instanceof LazyStringList ? new LazyStringArrayList(i2) : ((listF instanceof PrimitiveNonBoxingCollection) && (listF instanceof Internal.ProtobufList)) ? ((Internal.ProtobufList) listF).mutableCopyWithCapacity2(i2) : new ArrayList<>(i2);
                UnsafeUtil.K(obj, j2, lazyStringArrayList2);
                return lazyStringArrayList2;
            }
            if (UNMODIFIABLE_LIST_CLASS.isAssignableFrom(listF.getClass())) {
                ArrayList arrayList = new ArrayList(listF.size() + i2);
                arrayList.addAll(listF);
                UnsafeUtil.K(obj, j2, arrayList);
                lazyStringArrayList = arrayList;
            } else {
                if (!(listF instanceof UnmodifiableLazyStringList)) {
                    if (!(listF instanceof PrimitiveNonBoxingCollection) || !(listF instanceof Internal.ProtobufList)) {
                        return listF;
                    }
                    Internal.ProtobufList protobufList = (Internal.ProtobufList) listF;
                    if (protobufList.isModifiable()) {
                        return listF;
                    }
                    Internal.ProtobufList protobufListMutableCopyWithCapacity2 = protobufList.mutableCopyWithCapacity2(listF.size() + i2);
                    UnsafeUtil.K(obj, j2, protobufListMutableCopyWithCapacity2);
                    return protobufListMutableCopyWithCapacity2;
                }
                LazyStringArrayList lazyStringArrayList3 = new LazyStringArrayList(listF.size() + i2);
                lazyStringArrayList3.addAll((UnmodifiableLazyStringList) listF);
                UnsafeUtil.K(obj, j2, lazyStringArrayList3);
                lazyStringArrayList = lazyStringArrayList3;
            }
            return lazyStringArrayList;
        }

        @Override // com.google.protobuf.ListFieldSchema
        void c(Object obj, long j2) {
            Object objUnmodifiableList;
            List list = (List) UnsafeUtil.x(obj, j2);
            if (list instanceof LazyStringList) {
                objUnmodifiableList = ((LazyStringList) list).getUnmodifiableView();
            } else {
                if (UNMODIFIABLE_LIST_CLASS.isAssignableFrom(list.getClass())) {
                    return;
                }
                if ((list instanceof PrimitiveNonBoxingCollection) && (list instanceof Internal.ProtobufList)) {
                    Internal.ProtobufList protobufList = (Internal.ProtobufList) list;
                    if (protobufList.isModifiable()) {
                        protobufList.makeImmutable();
                        return;
                    }
                    return;
                }
                objUnmodifiableList = Collections.unmodifiableList(list);
            }
            UnsafeUtil.K(obj, j2, objUnmodifiableList);
        }

        @Override // com.google.protobuf.ListFieldSchema
        void d(Object obj, Object obj2, long j2) {
            List listF = f(obj2, j2);
            List listMutableListAt = mutableListAt(obj, j2, listF.size());
            int size = listMutableListAt.size();
            int size2 = listF.size();
            if (size > 0 && size2 > 0) {
                listMutableListAt.addAll(listF);
            }
            if (size > 0) {
                listF = listMutableListAt;
            }
            UnsafeUtil.K(obj, j2, listF);
        }

        @Override // com.google.protobuf.ListFieldSchema
        List e(Object obj, long j2) {
            return mutableListAt(obj, j2, 10);
        }
    }

    private static final class ListFieldSchemaLite extends ListFieldSchema {
        private ListFieldSchemaLite() {
            super();
        }

        static Internal.ProtobufList f(Object obj, long j2) {
            return (Internal.ProtobufList) UnsafeUtil.x(obj, j2);
        }

        @Override // com.google.protobuf.ListFieldSchema
        void c(Object obj, long j2) {
            f(obj, j2).makeImmutable();
        }

        @Override // com.google.protobuf.ListFieldSchema
        void d(Object obj, Object obj2, long j2) {
            Internal.ProtobufList protobufListF = f(obj, j2);
            Internal.ProtobufList protobufListF2 = f(obj2, j2);
            int size = protobufListF.size();
            int size2 = protobufListF2.size();
            if (size > 0 && size2 > 0) {
                if (!protobufListF.isModifiable()) {
                    protobufListF = protobufListF.mutableCopyWithCapacity2(size2 + size);
                }
                protobufListF.addAll(protobufListF2);
            }
            if (size > 0) {
                protobufListF2 = protobufListF;
            }
            UnsafeUtil.K(obj, j2, protobufListF2);
        }

        @Override // com.google.protobuf.ListFieldSchema
        List e(Object obj, long j2) {
            Internal.ProtobufList protobufListF = f(obj, j2);
            if (protobufListF.isModifiable()) {
                return protobufListF;
            }
            int size = protobufListF.size();
            Internal.ProtobufList protobufListMutableCopyWithCapacity2 = protobufListF.mutableCopyWithCapacity2(size == 0 ? 10 : size * 2);
            UnsafeUtil.K(obj, j2, protobufListMutableCopyWithCapacity2);
            return protobufListMutableCopyWithCapacity2;
        }
    }

    static {
        FULL_INSTANCE = new ListFieldSchemaFull();
        LITE_INSTANCE = new ListFieldSchemaLite();
    }

    private ListFieldSchema() {
    }

    static ListFieldSchema a() {
        return FULL_INSTANCE;
    }

    static ListFieldSchema b() {
        return LITE_INSTANCE;
    }

    abstract void c(Object obj, long j2);

    abstract void d(Object obj, Object obj2, long j2);

    abstract List e(Object obj, long j2);
}
