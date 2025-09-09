package androidx.recyclerview.widget;

import android.util.SparseArray;
import android.util.SparseIntArray;
import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
interface ViewTypeStorage {

    public static class IsolatedViewTypeStorage implements ViewTypeStorage {

        /* renamed from: a, reason: collision with root package name */
        SparseArray f6168a = new SparseArray();

        /* renamed from: b, reason: collision with root package name */
        int f6169b = 0;

        class WrapperViewTypeLookup implements ViewTypeLookup {

            /* renamed from: a, reason: collision with root package name */
            final NestedAdapterWrapper f6170a;
            private SparseIntArray mLocalToGlobalMapping = new SparseIntArray(1);
            private SparseIntArray mGlobalToLocalMapping = new SparseIntArray(1);

            WrapperViewTypeLookup(NestedAdapterWrapper nestedAdapterWrapper) {
                this.f6170a = nestedAdapterWrapper;
            }

            @Override // androidx.recyclerview.widget.ViewTypeStorage.ViewTypeLookup
            public void dispose() {
                IsolatedViewTypeStorage.this.b(this.f6170a);
            }

            @Override // androidx.recyclerview.widget.ViewTypeStorage.ViewTypeLookup
            public int globalToLocal(int i2) {
                int iIndexOfKey = this.mGlobalToLocalMapping.indexOfKey(i2);
                if (iIndexOfKey >= 0) {
                    return this.mGlobalToLocalMapping.valueAt(iIndexOfKey);
                }
                throw new IllegalStateException("requested global type " + i2 + " does not belong to the adapter:" + this.f6170a.adapter);
            }

            @Override // androidx.recyclerview.widget.ViewTypeStorage.ViewTypeLookup
            public int localToGlobal(int i2) {
                int iIndexOfKey = this.mLocalToGlobalMapping.indexOfKey(i2);
                if (iIndexOfKey > -1) {
                    return this.mLocalToGlobalMapping.valueAt(iIndexOfKey);
                }
                int iA = IsolatedViewTypeStorage.this.a(this.f6170a);
                this.mLocalToGlobalMapping.put(i2, iA);
                this.mGlobalToLocalMapping.put(iA, i2);
                return iA;
            }
        }

        int a(NestedAdapterWrapper nestedAdapterWrapper) {
            int i2 = this.f6169b;
            this.f6169b = i2 + 1;
            this.f6168a.put(i2, nestedAdapterWrapper);
            return i2;
        }

        void b(NestedAdapterWrapper nestedAdapterWrapper) {
            for (int size = this.f6168a.size() - 1; size >= 0; size--) {
                if (((NestedAdapterWrapper) this.f6168a.valueAt(size)) == nestedAdapterWrapper) {
                    this.f6168a.removeAt(size);
                }
            }
        }

        @Override // androidx.recyclerview.widget.ViewTypeStorage
        @NonNull
        public ViewTypeLookup createViewTypeWrapper(@NonNull NestedAdapterWrapper nestedAdapterWrapper) {
            return new WrapperViewTypeLookup(nestedAdapterWrapper);
        }

        @Override // androidx.recyclerview.widget.ViewTypeStorage
        @NonNull
        public NestedAdapterWrapper getWrapperForGlobalType(int i2) {
            NestedAdapterWrapper nestedAdapterWrapper = (NestedAdapterWrapper) this.f6168a.get(i2);
            if (nestedAdapterWrapper != null) {
                return nestedAdapterWrapper;
            }
            throw new IllegalArgumentException("Cannot find the wrapper for global view type " + i2);
        }
    }

    public static class SharedIdRangeViewTypeStorage implements ViewTypeStorage {

        /* renamed from: a, reason: collision with root package name */
        SparseArray f6172a = new SparseArray();

        class WrapperViewTypeLookup implements ViewTypeLookup {

            /* renamed from: a, reason: collision with root package name */
            final NestedAdapterWrapper f6173a;

            WrapperViewTypeLookup(NestedAdapterWrapper nestedAdapterWrapper) {
                this.f6173a = nestedAdapterWrapper;
            }

            @Override // androidx.recyclerview.widget.ViewTypeStorage.ViewTypeLookup
            public void dispose() {
                SharedIdRangeViewTypeStorage.this.a(this.f6173a);
            }

            @Override // androidx.recyclerview.widget.ViewTypeStorage.ViewTypeLookup
            public int globalToLocal(int i2) {
                return i2;
            }

            @Override // androidx.recyclerview.widget.ViewTypeStorage.ViewTypeLookup
            public int localToGlobal(int i2) {
                List arrayList = (List) SharedIdRangeViewTypeStorage.this.f6172a.get(i2);
                if (arrayList == null) {
                    arrayList = new ArrayList();
                    SharedIdRangeViewTypeStorage.this.f6172a.put(i2, arrayList);
                }
                if (!arrayList.contains(this.f6173a)) {
                    arrayList.add(this.f6173a);
                }
                return i2;
            }
        }

        void a(NestedAdapterWrapper nestedAdapterWrapper) {
            for (int size = this.f6172a.size() - 1; size >= 0; size--) {
                List list = (List) this.f6172a.valueAt(size);
                if (list.remove(nestedAdapterWrapper) && list.isEmpty()) {
                    this.f6172a.removeAt(size);
                }
            }
        }

        @Override // androidx.recyclerview.widget.ViewTypeStorage
        @NonNull
        public ViewTypeLookup createViewTypeWrapper(@NonNull NestedAdapterWrapper nestedAdapterWrapper) {
            return new WrapperViewTypeLookup(nestedAdapterWrapper);
        }

        @Override // androidx.recyclerview.widget.ViewTypeStorage
        @NonNull
        public NestedAdapterWrapper getWrapperForGlobalType(int i2) {
            List list = (List) this.f6172a.get(i2);
            if (list != null && !list.isEmpty()) {
                return (NestedAdapterWrapper) list.get(0);
            }
            throw new IllegalArgumentException("Cannot find the wrapper for global view type " + i2);
        }
    }

    public interface ViewTypeLookup {
        void dispose();

        int globalToLocal(int i2);

        int localToGlobal(int i2);
    }

    @NonNull
    ViewTypeLookup createViewTypeWrapper(@NonNull NestedAdapterWrapper nestedAdapterWrapper);

    @NonNull
    NestedAdapterWrapper getWrapperForGlobalType(int i2);
}
