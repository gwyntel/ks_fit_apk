package com.luck.picture.lib.decoration;

import android.graphics.Rect;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes4.dex */
public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
    private final boolean includeEdge;
    private final int spacing;
    private final int spanCount;

    public GridSpacingItemDecoration(int i2, int i3, boolean z2) {
        this.spanCount = i2;
        this.spacing = i3;
        this.includeEdge = z2;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(@NonNull Rect rect, @NonNull View view, RecyclerView recyclerView, RecyclerView.State state) {
        int childAdapterPosition = recyclerView.getChildAdapterPosition(view);
        int i2 = this.spanCount;
        int i3 = childAdapterPosition % i2;
        if (this.includeEdge) {
            int i4 = this.spacing;
            rect.left = i4 - ((i3 * i4) / i2);
            rect.right = ((i3 + 1) * i4) / i2;
        } else {
            int i5 = this.spacing;
            rect.left = (i3 * i5) / i2;
            rect.right = i5 - (((i3 + 1) * i5) / i2);
        }
        if (childAdapterPosition < i2) {
            rect.top = this.spacing;
        }
        rect.bottom = this.spacing;
    }
}
