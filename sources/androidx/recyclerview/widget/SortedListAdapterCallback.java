package androidx.recyclerview.widget;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

/* loaded from: classes2.dex */
public abstract class SortedListAdapterCallback<T2> extends SortedList.Callback<T2> {

    /* renamed from: a, reason: collision with root package name */
    final RecyclerView.Adapter f6107a;

    public SortedListAdapterCallback(RecyclerView.Adapter adapter) {
        this.f6107a = adapter;
    }

    @Override // androidx.recyclerview.widget.SortedList.Callback
    public void onChanged(int i2, int i3) {
        this.f6107a.notifyItemRangeChanged(i2, i3);
    }

    @Override // androidx.recyclerview.widget.ListUpdateCallback
    public void onInserted(int i2, int i3) {
        this.f6107a.notifyItemRangeInserted(i2, i3);
    }

    @Override // androidx.recyclerview.widget.ListUpdateCallback
    public void onMoved(int i2, int i3) {
        this.f6107a.notifyItemMoved(i2, i3);
    }

    @Override // androidx.recyclerview.widget.ListUpdateCallback
    public void onRemoved(int i2, int i3) {
        this.f6107a.notifyItemRangeRemoved(i2, i3);
    }

    @Override // androidx.recyclerview.widget.SortedList.Callback, androidx.recyclerview.widget.ListUpdateCallback
    public void onChanged(int i2, int i3, Object obj) {
        this.f6107a.notifyItemRangeChanged(i2, i3, obj);
    }
}
