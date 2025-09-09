package com.luck.picture.lib.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import androidx.annotation.Nullable;

/* loaded from: classes4.dex */
public class MarqueeTextView extends MediumBoldTextView {
    public MarqueeTextView(Context context) {
        super(context);
    }

    @Override // android.view.View
    public boolean isFocused() {
        return true;
    }

    @Override // android.view.View
    public boolean isSelected() {
        return true;
    }

    @Override // android.widget.TextView, android.view.View
    protected void onFocusChanged(boolean z2, int i2, Rect rect) {
        if (z2) {
            super.onFocusChanged(true, i2, rect);
        }
    }

    @Override // android.widget.TextView, android.view.View
    public void onWindowFocusChanged(boolean z2) {
        if (z2) {
            super.onWindowFocusChanged(true);
        }
    }

    public MarqueeTextView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
