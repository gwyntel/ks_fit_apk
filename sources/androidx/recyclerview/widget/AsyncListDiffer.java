package androidx.recyclerview.widget;

import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public class AsyncListDiffer<T> {
    private static final Executor sMainThreadExecutor = new MainThreadExecutor();

    /* renamed from: a, reason: collision with root package name */
    final AsyncDifferConfig f5794a;

    /* renamed from: b, reason: collision with root package name */
    Executor f5795b;

    /* renamed from: c, reason: collision with root package name */
    int f5796c;

    @Nullable
    private List<T> mList;
    private final List<ListListener<T>> mListeners;

    @NonNull
    private List<T> mReadOnlyList;
    private final ListUpdateCallback mUpdateCallback;

    public interface ListListener<T> {
        void onCurrentListChanged(@NonNull List<T> list, @NonNull List<T> list2);
    }

    private static class MainThreadExecutor implements Executor {

        /* renamed from: a, reason: collision with root package name */
        final Handler f5805a = new Handler(Looper.getMainLooper());

        MainThreadExecutor() {
        }

        @Override // java.util.concurrent.Executor
        public void execute(@NonNull Runnable runnable) {
            this.f5805a.post(runnable);
        }
    }

    public AsyncListDiffer(@NonNull RecyclerView.Adapter adapter, @NonNull DiffUtil.ItemCallback<T> itemCallback) {
        this(new AdapterListUpdateCallback(adapter), new AsyncDifferConfig.Builder(itemCallback).build());
    }

    private void onCurrentListChanged(@NonNull List<T> list, @Nullable Runnable runnable) {
        Iterator<ListListener<T>> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onCurrentListChanged(list, this.mReadOnlyList);
        }
        if (runnable != null) {
            runnable.run();
        }
    }

    void a(List list, DiffUtil.DiffResult diffResult, Runnable runnable) {
        List<T> list2 = this.mReadOnlyList;
        this.mList = list;
        this.mReadOnlyList = Collections.unmodifiableList(list);
        diffResult.dispatchUpdatesTo(this.mUpdateCallback);
        onCurrentListChanged(list2, runnable);
    }

    public void addListListener(@NonNull ListListener<T> listListener) {
        this.mListeners.add(listListener);
    }

    @NonNull
    public List<T> getCurrentList() {
        return this.mReadOnlyList;
    }

    public void removeListListener(@NonNull ListListener<T> listListener) {
        this.mListeners.remove(listListener);
    }

    public void submitList(@Nullable List<T> list) {
        submitList(list, null);
    }

    public void submitList(@Nullable final List<T> list, @Nullable final Runnable runnable) {
        final int i2 = this.f5796c + 1;
        this.f5796c = i2;
        final List<T> list2 = this.mList;
        if (list == list2) {
            if (runnable != null) {
                runnable.run();
                return;
            }
            return;
        }
        List<T> list3 = this.mReadOnlyList;
        if (list == null) {
            int size = list2.size();
            this.mList = null;
            this.mReadOnlyList = Collections.emptyList();
            this.mUpdateCallback.onRemoved(0, size);
            onCurrentListChanged(list3, runnable);
            return;
        }
        if (list2 != null) {
            this.f5794a.getBackgroundThreadExecutor().execute(new Runnable() { // from class: androidx.recyclerview.widget.AsyncListDiffer.1
                @Override // java.lang.Runnable
                public void run() {
                    final DiffUtil.DiffResult diffResultCalculateDiff = DiffUtil.calculateDiff(new DiffUtil.Callback() { // from class: androidx.recyclerview.widget.AsyncListDiffer.1.1
                        /* JADX WARN: Multi-variable type inference failed */
                        @Override // androidx.recyclerview.widget.DiffUtil.Callback
                        public boolean areContentsTheSame(int i3, int i4) {
                            Object obj = list2.get(i3);
                            Object obj2 = list.get(i4);
                            if (obj != null && obj2 != null) {
                                return AsyncListDiffer.this.f5794a.getDiffCallback().areContentsTheSame(obj, obj2);
                            }
                            if (obj == null && obj2 == null) {
                                return true;
                            }
                            throw new AssertionError();
                        }

                        /* JADX WARN: Multi-variable type inference failed */
                        @Override // androidx.recyclerview.widget.DiffUtil.Callback
                        public boolean areItemsTheSame(int i3, int i4) {
                            Object obj = list2.get(i3);
                            Object obj2 = list.get(i4);
                            return (obj == null || obj2 == null) ? obj == null && obj2 == null : AsyncListDiffer.this.f5794a.getDiffCallback().areItemsTheSame(obj, obj2);
                        }

                        /* JADX WARN: Multi-variable type inference failed */
                        @Override // androidx.recyclerview.widget.DiffUtil.Callback
                        @Nullable
                        public Object getChangePayload(int i3, int i4) {
                            Object obj = list2.get(i3);
                            Object obj2 = list.get(i4);
                            if (obj == null || obj2 == null) {
                                throw new AssertionError();
                            }
                            return AsyncListDiffer.this.f5794a.getDiffCallback().getChangePayload(obj, obj2);
                        }

                        @Override // androidx.recyclerview.widget.DiffUtil.Callback
                        public int getNewListSize() {
                            return list.size();
                        }

                        @Override // androidx.recyclerview.widget.DiffUtil.Callback
                        public int getOldListSize() {
                            return list2.size();
                        }
                    });
                    AsyncListDiffer.this.f5795b.execute(new Runnable() { // from class: androidx.recyclerview.widget.AsyncListDiffer.1.2
                        @Override // java.lang.Runnable
                        public void run() {
                            AnonymousClass1 anonymousClass1 = AnonymousClass1.this;
                            AsyncListDiffer asyncListDiffer = AsyncListDiffer.this;
                            if (asyncListDiffer.f5796c == i2) {
                                asyncListDiffer.a(list, diffResultCalculateDiff, runnable);
                            }
                        }
                    });
                }
            });
            return;
        }
        this.mList = list;
        this.mReadOnlyList = Collections.unmodifiableList(list);
        this.mUpdateCallback.onInserted(0, list.size());
        onCurrentListChanged(list3, runnable);
    }

    public AsyncListDiffer(@NonNull ListUpdateCallback listUpdateCallback, @NonNull AsyncDifferConfig<T> asyncDifferConfig) {
        this.mListeners = new CopyOnWriteArrayList();
        this.mReadOnlyList = Collections.emptyList();
        this.mUpdateCallback = listUpdateCallback;
        this.f5794a = asyncDifferConfig;
        if (asyncDifferConfig.getMainThreadExecutor() != null) {
            this.f5795b = asyncDifferConfig.getMainThreadExecutor();
        } else {
            this.f5795b = sMainThreadExecutor;
        }
    }
}
