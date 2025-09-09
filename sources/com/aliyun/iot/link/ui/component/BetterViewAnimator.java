package com.aliyun.iot.link.ui.component;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.widget.ViewAnimator;

/* loaded from: classes3.dex */
public class BetterViewAnimator extends ViewAnimator {
    public BetterViewAnimator(Context context) {
        super(context);
    }

    public int getDisplayedChildId() {
        return getChildAt(getDisplayedChild()).getId();
    }

    public void setDisplayedChildId(int i2) throws Resources.NotFoundException {
        if (getDisplayedChildId() == i2) {
            return;
        }
        int childCount = getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            if (getChildAt(i3).getId() == i2) {
                setDisplayedChild(i3);
                return;
            }
        }
        new IllegalArgumentException("No view with ID " + getResources().getResourceEntryName(i2)).printStackTrace();
    }

    public BetterViewAnimator(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
