package androidx.recyclerview.widget;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

/* loaded from: classes2.dex */
public class SortedList<T> {
    private static final int CAPACITY_GROWTH = 10;
    private static final int DELETION = 2;
    private static final int INSERTION = 1;
    public static final int INVALID_POSITION = -1;
    private static final int LOOKUP = 4;
    private static final int MIN_CAPACITY = 10;

    /* renamed from: a, reason: collision with root package name */
    Object[] f6105a;
    private BatchedCallback mBatchedCallback;
    private Callback mCallback;
    private int mNewDataStart;
    private T[] mOldData;
    private int mOldDataSize;
    private int mOldDataStart;
    private int mSize;
    private final Class<T> mTClass;

    public static class BatchedCallback<T2> extends Callback<T2> {

        /* renamed from: a, reason: collision with root package name */
        final Callback f6106a;
        private final BatchingListUpdateCallback mBatchingListUpdateCallback;

        public BatchedCallback(Callback<T2> callback) {
            this.f6106a = callback;
            this.mBatchingListUpdateCallback = new BatchingListUpdateCallback(callback);
        }

        @Override // androidx.recyclerview.widget.SortedList.Callback
        public boolean areContentsTheSame(T2 t2, T2 t22) {
            return this.f6106a.areContentsTheSame(t2, t22);
        }

        @Override // androidx.recyclerview.widget.SortedList.Callback
        public boolean areItemsTheSame(T2 t2, T2 t22) {
            return this.f6106a.areItemsTheSame(t2, t22);
        }

        @Override // androidx.recyclerview.widget.SortedList.Callback, java.util.Comparator
        public int compare(T2 t2, T2 t22) {
            return this.f6106a.compare(t2, t22);
        }

        public void dispatchLastEvent() {
            this.mBatchingListUpdateCallback.dispatchLastEvent();
        }

        @Override // androidx.recyclerview.widget.SortedList.Callback
        @Nullable
        public Object getChangePayload(T2 t2, T2 t22) {
            return this.f6106a.getChangePayload(t2, t22);
        }

        @Override // androidx.recyclerview.widget.SortedList.Callback
        public void onChanged(int i2, int i3) {
            this.mBatchingListUpdateCallback.onChanged(i2, i3, null);
        }

        @Override // androidx.recyclerview.widget.ListUpdateCallback
        public void onInserted(int i2, int i3) {
            this.mBatchingListUpdateCallback.onInserted(i2, i3);
        }

        @Override // androidx.recyclerview.widget.ListUpdateCallback
        public void onMoved(int i2, int i3) {
            this.mBatchingListUpdateCallback.onMoved(i2, i3);
        }

        @Override // androidx.recyclerview.widget.ListUpdateCallback
        public void onRemoved(int i2, int i3) {
            this.mBatchingListUpdateCallback.onRemoved(i2, i3);
        }

        @Override // androidx.recyclerview.widget.SortedList.Callback, androidx.recyclerview.widget.ListUpdateCallback
        public void onChanged(int i2, int i3, Object obj) {
            this.mBatchingListUpdateCallback.onChanged(i2, i3, obj);
        }
    }

    public static abstract class Callback<T2> implements Comparator<T2>, ListUpdateCallback {
        public abstract boolean areContentsTheSame(T2 t2, T2 t22);

        public abstract boolean areItemsTheSame(T2 t2, T2 t22);

        @Override // java.util.Comparator
        public abstract int compare(T2 t2, T2 t22);

        @Nullable
        public Object getChangePayload(T2 t2, T2 t22) {
            return null;
        }

        public abstract void onChanged(int i2, int i3);

        public void onChanged(int i2, int i3, Object obj) {
            onChanged(i2, i3);
        }
    }

    public SortedList(@NonNull Class<T> cls, @NonNull Callback<T> callback) {
        this(cls, callback, 10);
    }

    private void addAllInternal(T[] tArr) {
        if (tArr.length < 1) {
            return;
        }
        int iSortAndDedup = sortAndDedup(tArr);
        if (this.mSize != 0) {
            merge(tArr, iSortAndDedup);
            return;
        }
        this.f6105a = tArr;
        this.mSize = iSortAndDedup;
        this.mCallback.onInserted(0, iSortAndDedup);
    }

    private void addToData(int i2, T t2) {
        int i3 = this.mSize;
        if (i2 > i3) {
            throw new IndexOutOfBoundsException("cannot add item to " + i2 + " because size is " + this.mSize);
        }
        Object[] objArr = this.f6105a;
        if (i3 == objArr.length) {
            Object[] objArr2 = (Object[]) Array.newInstance((Class<?>) this.mTClass, objArr.length + 10);
            System.arraycopy(this.f6105a, 0, objArr2, 0, i2);
            objArr2[i2] = t2;
            System.arraycopy(this.f6105a, i2, objArr2, i2 + 1, this.mSize - i2);
            this.f6105a = objArr2;
        } else {
            System.arraycopy(objArr, i2, objArr, i2 + 1, i3 - i2);
            this.f6105a[i2] = t2;
        }
        this.mSize++;
    }

    private T[] copyArray(T[] tArr) {
        T[] tArr2 = (T[]) ((Object[]) Array.newInstance((Class<?>) this.mTClass, tArr.length));
        System.arraycopy(tArr, 0, tArr2, 0, tArr.length);
        return tArr2;
    }

    private int findIndexOf(T t2, T[] tArr, int i2, int i3, int i4) {
        while (i2 < i3) {
            int i5 = (i2 + i3) / 2;
            T t3 = tArr[i5];
            int iCompare = this.mCallback.compare(t3, t2);
            if (iCompare < 0) {
                i2 = i5 + 1;
            } else {
                if (iCompare == 0) {
                    if (this.mCallback.areItemsTheSame(t3, t2)) {
                        return i5;
                    }
                    int iLinearEqualitySearch = linearEqualitySearch(t2, i5, i2, i3);
                    return i4 == 1 ? iLinearEqualitySearch == -1 ? i5 : iLinearEqualitySearch : iLinearEqualitySearch;
                }
                i3 = i5;
            }
        }
        if (i4 == 1) {
            return i2;
        }
        return -1;
    }

    private int findSameItem(T t2, T[] tArr, int i2, int i3) {
        while (i2 < i3) {
            if (this.mCallback.areItemsTheSame(tArr[i2], t2)) {
                return i2;
            }
            i2++;
        }
        return -1;
    }

    private int linearEqualitySearch(T t2, int i2, int i3, int i4) {
        Object obj;
        for (int i5 = i2 - 1; i5 >= i3; i5--) {
            Object obj2 = this.f6105a[i5];
            if (this.mCallback.compare(obj2, t2) != 0) {
                break;
            }
            if (this.mCallback.areItemsTheSame(obj2, t2)) {
                return i5;
            }
        }
        do {
            i2++;
            if (i2 >= i4) {
                return -1;
            }
            obj = this.f6105a[i2];
            if (this.mCallback.compare(obj, t2) != 0) {
                return -1;
            }
        } while (!this.mCallback.areItemsTheSame(obj, t2));
        return i2;
    }

    private void merge(T[] tArr, int i2) {
        boolean z2 = !(this.mCallback instanceof BatchedCallback);
        if (z2) {
            beginBatchedUpdates();
        }
        this.mOldData = (T[]) this.f6105a;
        int i3 = 0;
        this.mOldDataStart = 0;
        int i4 = this.mSize;
        this.mOldDataSize = i4;
        this.f6105a = (Object[]) Array.newInstance((Class<?>) this.mTClass, i4 + i2 + 10);
        this.mNewDataStart = 0;
        while (true) {
            int i5 = this.mOldDataStart;
            int i6 = this.mOldDataSize;
            if (i5 >= i6 && i3 >= i2) {
                break;
            }
            if (i5 == i6) {
                int i7 = i2 - i3;
                System.arraycopy(tArr, i3, this.f6105a, this.mNewDataStart, i7);
                int i8 = this.mNewDataStart + i7;
                this.mNewDataStart = i8;
                this.mSize += i7;
                this.mCallback.onInserted(i8 - i7, i7);
                break;
            }
            if (i3 == i2) {
                int i9 = i6 - i5;
                System.arraycopy(this.mOldData, i5, this.f6105a, this.mNewDataStart, i9);
                this.mNewDataStart += i9;
                break;
            }
            T t2 = this.mOldData[i5];
            T t3 = tArr[i3];
            int iCompare = this.mCallback.compare(t2, t3);
            if (iCompare > 0) {
                Object[] objArr = this.f6105a;
                int i10 = this.mNewDataStart;
                this.mNewDataStart = i10 + 1;
                objArr[i10] = t3;
                this.mSize++;
                i3++;
                this.mCallback.onInserted(i10, 1);
            } else if (iCompare == 0 && this.mCallback.areItemsTheSame(t2, t3)) {
                Object[] objArr2 = this.f6105a;
                int i11 = this.mNewDataStart;
                this.mNewDataStart = i11 + 1;
                objArr2[i11] = t3;
                i3++;
                this.mOldDataStart++;
                if (!this.mCallback.areContentsTheSame(t2, t3)) {
                    Callback callback = this.mCallback;
                    callback.onChanged(this.mNewDataStart - 1, 1, callback.getChangePayload(t2, t3));
                }
            } else {
                Object[] objArr3 = this.f6105a;
                int i12 = this.mNewDataStart;
                this.mNewDataStart = i12 + 1;
                objArr3[i12] = t2;
                this.mOldDataStart++;
            }
        }
        this.mOldData = null;
        if (z2) {
            endBatchedUpdates();
        }
    }

    private void removeItemAtIndex(int i2, boolean z2) {
        Object[] objArr = this.f6105a;
        System.arraycopy(objArr, i2 + 1, objArr, i2, (this.mSize - i2) - 1);
        int i3 = this.mSize - 1;
        this.mSize = i3;
        this.f6105a[i3] = null;
        if (z2) {
            this.mCallback.onRemoved(i2, 1);
        }
    }

    private void replaceAllInsert(T t2) {
        Object[] objArr = this.f6105a;
        int i2 = this.mNewDataStart;
        objArr[i2] = t2;
        this.mNewDataStart = i2 + 1;
        this.mSize++;
        this.mCallback.onInserted(i2, 1);
    }

    private void replaceAllInternal(@NonNull T[] tArr) {
        boolean z2 = !(this.mCallback instanceof BatchedCallback);
        if (z2) {
            beginBatchedUpdates();
        }
        this.mOldDataStart = 0;
        this.mOldDataSize = this.mSize;
        this.mOldData = (T[]) this.f6105a;
        this.mNewDataStart = 0;
        int iSortAndDedup = sortAndDedup(tArr);
        this.f6105a = (Object[]) Array.newInstance((Class<?>) this.mTClass, iSortAndDedup);
        while (true) {
            int i2 = this.mNewDataStart;
            if (i2 >= iSortAndDedup && this.mOldDataStart >= this.mOldDataSize) {
                break;
            }
            int i3 = this.mOldDataStart;
            int i4 = this.mOldDataSize;
            if (i3 >= i4) {
                int i5 = iSortAndDedup - i2;
                System.arraycopy(tArr, i2, this.f6105a, i2, i5);
                this.mNewDataStart += i5;
                this.mSize += i5;
                this.mCallback.onInserted(i2, i5);
                break;
            }
            if (i2 >= iSortAndDedup) {
                int i6 = i4 - i3;
                this.mSize -= i6;
                this.mCallback.onRemoved(i2, i6);
                break;
            }
            T t2 = this.mOldData[i3];
            T t3 = tArr[i2];
            int iCompare = this.mCallback.compare(t2, t3);
            if (iCompare < 0) {
                replaceAllRemove();
            } else if (iCompare > 0) {
                replaceAllInsert(t3);
            } else if (this.mCallback.areItemsTheSame(t2, t3)) {
                Object[] objArr = this.f6105a;
                int i7 = this.mNewDataStart;
                objArr[i7] = t3;
                this.mOldDataStart++;
                this.mNewDataStart = i7 + 1;
                if (!this.mCallback.areContentsTheSame(t2, t3)) {
                    Callback callback = this.mCallback;
                    callback.onChanged(this.mNewDataStart - 1, 1, callback.getChangePayload(t2, t3));
                }
            } else {
                replaceAllRemove();
                replaceAllInsert(t3);
            }
        }
        this.mOldData = null;
        if (z2) {
            endBatchedUpdates();
        }
    }

    private void replaceAllRemove() {
        this.mSize--;
        this.mOldDataStart++;
        this.mCallback.onRemoved(this.mNewDataStart, 1);
    }

    private int sortAndDedup(@NonNull T[] tArr) {
        if (tArr.length == 0) {
            return 0;
        }
        Arrays.sort(tArr, this.mCallback);
        int i2 = 0;
        int i3 = 1;
        for (int i4 = 1; i4 < tArr.length; i4++) {
            T t2 = tArr[i4];
            if (this.mCallback.compare(tArr[i2], t2) == 0) {
                int iFindSameItem = findSameItem(t2, tArr, i2, i3);
                if (iFindSameItem != -1) {
                    tArr[iFindSameItem] = t2;
                } else {
                    if (i3 != i4) {
                        tArr[i3] = t2;
                    }
                    i3++;
                }
            } else {
                if (i3 != i4) {
                    tArr[i3] = t2;
                }
                i2 = i3;
                i3++;
            }
        }
        return i3;
    }

    private void throwIfInMutationOperation() {
        if (this.mOldData != null) {
            throw new IllegalStateException("Data cannot be mutated in the middle of a batch update operation such as addAll or replaceAll.");
        }
    }

    public int add(T t2) {
        throwIfInMutationOperation();
        return add(t2, true);
    }

    public void addAll(@NonNull T[] tArr, boolean z2) {
        throwIfInMutationOperation();
        if (tArr.length == 0) {
            return;
        }
        if (z2) {
            addAllInternal(tArr);
        } else {
            addAllInternal(copyArray(tArr));
        }
    }

    public void beginBatchedUpdates() {
        throwIfInMutationOperation();
        Callback callback = this.mCallback;
        if (callback instanceof BatchedCallback) {
            return;
        }
        if (this.mBatchedCallback == null) {
            this.mBatchedCallback = new BatchedCallback(callback);
        }
        this.mCallback = this.mBatchedCallback;
    }

    public void clear() {
        throwIfInMutationOperation();
        int i2 = this.mSize;
        if (i2 == 0) {
            return;
        }
        Arrays.fill(this.f6105a, 0, i2, (Object) null);
        this.mSize = 0;
        this.mCallback.onRemoved(0, i2);
    }

    public void endBatchedUpdates() {
        throwIfInMutationOperation();
        Callback callback = this.mCallback;
        if (callback instanceof BatchedCallback) {
            ((BatchedCallback) callback).dispatchLastEvent();
        }
        Callback callback2 = this.mCallback;
        BatchedCallback batchedCallback = this.mBatchedCallback;
        if (callback2 == batchedCallback) {
            this.mCallback = batchedCallback.f6106a;
        }
    }

    public T get(int i2) throws IndexOutOfBoundsException {
        int i3;
        if (i2 < this.mSize && i2 >= 0) {
            T[] tArr = this.mOldData;
            return (tArr == null || i2 < (i3 = this.mNewDataStart)) ? (T) this.f6105a[i2] : tArr[(i2 - i3) + this.mOldDataStart];
        }
        throw new IndexOutOfBoundsException("Asked to get item at " + i2 + " but size is " + this.mSize);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int indexOf(T t2) {
        if (this.mOldData == null) {
            return findIndexOf(t2, this.f6105a, 0, this.mSize, 4);
        }
        int iFindIndexOf = findIndexOf(t2, this.f6105a, 0, this.mNewDataStart, 4);
        if (iFindIndexOf != -1) {
            return iFindIndexOf;
        }
        int iFindIndexOf2 = findIndexOf(t2, this.mOldData, this.mOldDataStart, this.mOldDataSize, 4);
        if (iFindIndexOf2 != -1) {
            return (iFindIndexOf2 - this.mOldDataStart) + this.mNewDataStart;
        }
        return -1;
    }

    public void recalculatePositionOfItemAt(int i2) throws IndexOutOfBoundsException {
        throwIfInMutationOperation();
        T t2 = get(i2);
        removeItemAtIndex(i2, false);
        int iAdd = add(t2, false);
        if (i2 != iAdd) {
            this.mCallback.onMoved(i2, iAdd);
        }
    }

    public boolean remove(T t2) {
        throwIfInMutationOperation();
        return remove(t2, true);
    }

    public T removeItemAt(int i2) throws IndexOutOfBoundsException {
        throwIfInMutationOperation();
        T t2 = get(i2);
        removeItemAtIndex(i2, true);
        return t2;
    }

    public void replaceAll(@NonNull T[] tArr, boolean z2) {
        throwIfInMutationOperation();
        if (z2) {
            replaceAllInternal(tArr);
        } else {
            replaceAllInternal(copyArray(tArr));
        }
    }

    public int size() {
        return this.mSize;
    }

    public void updateItemAt(int i2, T t2) throws IndexOutOfBoundsException {
        throwIfInMutationOperation();
        T t3 = get(i2);
        boolean z2 = t3 == t2 || !this.mCallback.areContentsTheSame(t3, t2);
        if (t3 != t2 && this.mCallback.compare(t3, t2) == 0) {
            this.f6105a[i2] = t2;
            if (z2) {
                Callback callback = this.mCallback;
                callback.onChanged(i2, 1, callback.getChangePayload(t3, t2));
                return;
            }
            return;
        }
        if (z2) {
            Callback callback2 = this.mCallback;
            callback2.onChanged(i2, 1, callback2.getChangePayload(t3, t2));
        }
        removeItemAtIndex(i2, false);
        int iAdd = add(t2, false);
        if (i2 != iAdd) {
            this.mCallback.onMoved(i2, iAdd);
        }
    }

    public SortedList(@NonNull Class<T> cls, @NonNull Callback<T> callback, int i2) {
        this.mTClass = cls;
        this.f6105a = (Object[]) Array.newInstance((Class<?>) cls, i2);
        this.mCallback = callback;
        this.mSize = 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private int add(T t2, boolean z2) {
        int iFindIndexOf = findIndexOf(t2, this.f6105a, 0, this.mSize, 1);
        if (iFindIndexOf == -1) {
            iFindIndexOf = 0;
        } else if (iFindIndexOf < this.mSize) {
            Object obj = this.f6105a[iFindIndexOf];
            if (this.mCallback.areItemsTheSame(obj, t2)) {
                if (this.mCallback.areContentsTheSame(obj, t2)) {
                    this.f6105a[iFindIndexOf] = t2;
                    return iFindIndexOf;
                }
                this.f6105a[iFindIndexOf] = t2;
                Callback callback = this.mCallback;
                callback.onChanged(iFindIndexOf, 1, callback.getChangePayload(obj, t2));
                return iFindIndexOf;
            }
        }
        addToData(iFindIndexOf, t2);
        if (z2) {
            this.mCallback.onInserted(iFindIndexOf, 1);
        }
        return iFindIndexOf;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private boolean remove(T t2, boolean z2) {
        int iFindIndexOf = findIndexOf(t2, this.f6105a, 0, this.mSize, 2);
        if (iFindIndexOf == -1) {
            return false;
        }
        removeItemAtIndex(iFindIndexOf, z2);
        return true;
    }

    public void replaceAll(@NonNull T... tArr) {
        replaceAll(tArr, false);
    }

    public void addAll(@NonNull T... tArr) {
        addAll(tArr, false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void replaceAll(@NonNull Collection<T> collection) {
        replaceAll(collection.toArray((Object[]) Array.newInstance((Class<?>) this.mTClass, collection.size())), true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void addAll(@NonNull Collection<T> collection) {
        addAll(collection.toArray((Object[]) Array.newInstance((Class<?>) this.mTClass, collection.size())), true);
    }
}
