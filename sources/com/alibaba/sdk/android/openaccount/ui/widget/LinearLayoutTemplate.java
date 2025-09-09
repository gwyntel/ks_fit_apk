package com.alibaba.sdk.android.openaccount.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.alibaba.sdk.android.openaccount.ui.CustomWidget;
import com.alibaba.sdk.android.openaccount.ui.LayoutMapping;
import com.alibaba.sdk.android.openaccount.ui.util.AttributeUtils;
import com.alibaba.sdk.android.openaccount.ui.util.OpenAccountUIUtils;
import com.alibaba.sdk.android.openaccount.util.ResourceUtils;

/* loaded from: classes2.dex */
public abstract class LinearLayoutTemplate extends LinearLayout implements CustomWidget {
    protected Typeface iconfont;

    public LinearLayoutTemplate(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        if (isInEditMode()) {
            return;
        }
        LayoutInflater.from(context).inflate(getLayoutId(context), (ViewGroup) this, true);
        this.iconfont = OpenAccountUIUtils.getDefaultFont();
    }

    protected void doUseCustomAttrs(Context context, TypedArray typedArray) {
    }

    public View findViewById(String str) {
        return super.findViewById(ResourceUtils.getRId(getContext(), str));
    }

    @Override // com.alibaba.sdk.android.openaccount.ui.CustomWidget
    public int getLayoutId(Context context) {
        return LayoutMapping.hasCustomLayout(getClass()) ? LayoutMapping.get(getClass()).intValue() : ResourceUtils.getRLayout(context, getLayoutName());
    }

    protected String getLayoutName() {
        return null;
    }

    protected void setViewOnClickListener(View.OnClickListener onClickListener, View... viewArr) {
        for (View view : viewArr) {
            if (view != null) {
                view.setOnClickListener(onClickListener);
            }
        }
    }

    protected void setViewVisibility(int i2, View... viewArr) {
        for (View view : viewArr) {
            if (view != null && view.getVisibility() != i2) {
                view.setVisibility(i2);
            }
        }
    }

    protected void useCustomAttrs(Context context, AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = AttributeUtils.obtainStyledAttributes(context, attributeSet);
        doUseCustomAttrs(context, typedArrayObtainStyledAttributes);
        typedArrayObtainStyledAttributes.recycle();
    }
}
