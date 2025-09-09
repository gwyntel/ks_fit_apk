package com.luck.picture.lib.decoration;

import android.graphics.Rect;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes4.dex */
public class ViewPage2ItemDecoration extends RecyclerView.ItemDecoration {
    private final int spacing;
    private final int spanCount;

    public ViewPage2ItemDecoration(int i2, int i3) {
        this.spanCount = i2;
        this.spacing = i3;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(Rect rect, @NonNull View view, RecyclerView recyclerView, RecyclerView.State state) {
        int childAdapterPosition = recyclerView.getChildAdapterPosition(view);
        int i2 = this.spanCount;
        int i3 = childAdapterPosition % i2;
        int i4 = this.spacing;
        rect.left = i4 - ((i3 * i4) / i2);
        rect.right = ((i3 + 1) * i4) / i2;
    }
}
