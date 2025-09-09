package com.luck.picture.lib.decoration;

import android.graphics.Rect;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes4.dex */
public class HorizontalItemDecoration extends RecyclerView.ItemDecoration {
    private final int spacing;
    private final int spanCount;

    public HorizontalItemDecoration(int i2, int i3) {
        this.spanCount = i2;
        this.spacing = i3;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(@NonNull Rect rect, @NonNull View view, RecyclerView recyclerView, RecyclerView.State state) {
        int childAdapterPosition = recyclerView.getChildAdapterPosition(view);
        int i2 = this.spanCount;
        int i3 = childAdapterPosition % i2;
        if (childAdapterPosition == 0) {
            int i4 = this.spacing;
            rect.left = i4 - ((i3 * i4) / i2);
        } else {
            rect.left = (this.spacing * i3) / i2;
        }
        int i5 = this.spacing;
        rect.right = i5 - (((i3 + 1) * i5) / i2);
        if (childAdapterPosition < i2) {
            rect.top = i5;
        }
        rect.bottom = i5;
    }
}
