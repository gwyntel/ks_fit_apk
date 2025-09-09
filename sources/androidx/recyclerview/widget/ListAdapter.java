package androidx.recyclerview.widget;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import java.util.List;

/* loaded from: classes2.dex */
public abstract class ListAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    /* renamed from: a, reason: collision with root package name */
    final AsyncListDiffer f5997a;
    private final AsyncListDiffer.ListListener<T> mListener;

    /* renamed from: androidx.recyclerview.widget.ListAdapter$1, reason: invalid class name */
    class AnonymousClass1 implements AsyncListDiffer.ListListener<T> {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ ListAdapter f5998a;

        @Override // androidx.recyclerview.widget.AsyncListDiffer.ListListener
        public void onCurrentListChanged(@NonNull List<T> list, @NonNull List<T> list2) {
            this.f5998a.onCurrentListChanged(list, list2);
        }
    }

    @NonNull
    public List<T> getCurrentList() {
        return this.f5997a.getCurrentList();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.f5997a.getCurrentList().size();
    }

    public void onCurrentListChanged(@NonNull List<T> list, @NonNull List<T> list2) {
    }

    public void submitList(@Nullable List<T> list) {
        this.f5997a.submitList(list);
    }

    public void submitList(@Nullable List<T> list, @Nullable Runnable runnable) {
        this.f5997a.submitList(list, runnable);
    }
}
