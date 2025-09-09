package com.luck.picture.lib.magical;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class BuildRecycleItemViewParams {
    private static final List<ViewParams> viewParams = new ArrayList();

    public static void clear() {
        List<ViewParams> list = viewParams;
        if (list.size() > 0) {
            list.clear();
        }
    }

    private static void fillPlaceHolder(List<View> list, int i2, int i3, int i4) {
        if (i3 > 0) {
            while (i3 >= 1) {
                list.add(0, null);
                i3--;
            }
        }
        if (i4 < i2) {
            for (int i5 = (i2 - 1) - i4; i5 >= 1; i5--) {
                list.add(null);
            }
        }
    }

    public static void generateViewParams(ViewGroup viewGroup, int i2) {
        int childCount;
        int count;
        int firstVisiblePosition;
        int lastVisiblePosition;
        ArrayList arrayList = new ArrayList();
        boolean z2 = viewGroup instanceof RecyclerView;
        if (z2) {
            childCount = ((RecyclerView) viewGroup).getChildCount();
        } else {
            if (!(viewGroup instanceof ListView)) {
                throw new IllegalArgumentException(viewGroup.getClass().getCanonicalName() + " Must be " + RecyclerView.class + " or " + ListView.class);
            }
            childCount = ((ListView) viewGroup).getChildCount();
        }
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = viewGroup.getChildAt(i3);
            if (childAt != null) {
                arrayList.add(childAt);
            }
        }
        if (z2) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) ((RecyclerView) viewGroup).getLayoutManager();
            if (gridLayoutManager == null) {
                return;
            }
            count = gridLayoutManager.getItemCount();
            firstVisiblePosition = gridLayoutManager.findFirstVisibleItemPosition();
            lastVisiblePosition = gridLayoutManager.findLastVisibleItemPosition();
        } else {
            ListView listView = (ListView) viewGroup;
            ListAdapter adapter = listView.getAdapter();
            if (adapter == null) {
                return;
            }
            count = adapter.getCount();
            firstVisiblePosition = listView.getFirstVisiblePosition();
            lastVisiblePosition = listView.getLastVisiblePosition();
        }
        if (lastVisiblePosition > count) {
            lastVisiblePosition = count - 1;
        }
        fillPlaceHolder(arrayList, count, firstVisiblePosition, lastVisiblePosition);
        viewParams.clear();
        for (int i4 = 0; i4 < arrayList.size(); i4++) {
            View view = (View) arrayList.get(i4);
            ViewParams viewParams2 = new ViewParams();
            if (view == null) {
                viewParams2.setLeft(0);
                viewParams2.setTop(0);
                viewParams2.setWidth(0);
                viewParams2.setHeight(0);
            } else {
                int[] iArr = new int[2];
                view.getLocationOnScreen(iArr);
                viewParams2.setLeft(iArr[0]);
                viewParams2.setTop(iArr[1] - i2);
                viewParams2.setWidth(view.getWidth());
                viewParams2.setHeight(view.getHeight());
            }
            viewParams.add(viewParams2);
        }
    }

    public static ViewParams getItemViewParams(int i2) {
        List<ViewParams> list = viewParams;
        if (list.size() > i2) {
            return list.get(i2);
        }
        return null;
    }
}
