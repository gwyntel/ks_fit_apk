package com.alibaba.sdk.android.openaccount.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import com.alibaba.sdk.android.openaccount.ui.R;
import java.lang.reflect.Field;

/* loaded from: classes2.dex */
public class OaToolbar extends Toolbar {
    private CharSequence mTitleText;
    private int mTitleTextAppearance;
    private int mTitleTextColor;
    private TextView mTitleTextView;

    public OaToolbar(Context context) {
        super(context);
        resolveAttribute(context, null, R.attr.toolbarStyle);
    }

    private void addCenterView(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        addView(view, layoutParams == null ? generateDefaultLayoutParams() : !checkLayoutParams(layoutParams) ? generateLayoutParams(layoutParams) : (Toolbar.LayoutParams) layoutParams);
    }

    private void resolveAttribute(Context context, @Nullable AttributeSet attributeSet, int i2) {
        Context context2 = getContext();
        TypedArray typedArrayObtainStyledAttributes = context2.obtainStyledAttributes(attributeSet, R.styleable.Toolbar, i2, 0);
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.Toolbar_titleTextAppearance, 0);
        if (resourceId != 0) {
            setTitleTextAppearance(context2, resourceId);
        }
        int i3 = this.mTitleTextColor;
        if (i3 != 0) {
            setTitleTextColor(i3);
        }
        typedArrayObtainStyledAttributes.recycle();
        post(new Runnable() { // from class: com.alibaba.sdk.android.openaccount.ui.widget.OaToolbar.1
            @Override // java.lang.Runnable
            public void run() {
                if (OaToolbar.this.getLayoutParams() instanceof Toolbar.LayoutParams) {
                    ((Toolbar.LayoutParams) OaToolbar.this.getLayoutParams()).gravity = 17;
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setCenter(String str) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        try {
            Field declaredField = getClass().getSuperclass().getDeclaredField(str);
            declaredField.setAccessible(true);
            Object obj = declaredField.get(this);
            if (obj != null && (obj instanceof View)) {
                View view = (View) obj;
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                if (layoutParams instanceof ActionBar.LayoutParams) {
                    ((ActionBar.LayoutParams) layoutParams).gravity = 17;
                    view.setLayoutParams(layoutParams);
                }
            }
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
        } catch (NoSuchFieldException e3) {
            e3.printStackTrace();
        }
    }

    @Override // androidx.appcompat.widget.Toolbar
    public CharSequence getTitle() {
        return this.mTitleText;
    }

    public void setGravityCenter() {
        post(new Runnable() { // from class: com.alibaba.sdk.android.openaccount.ui.widget.OaToolbar.2
            @Override // java.lang.Runnable
            public void run() throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
                OaToolbar.this.setCenter("mNavButtonView");
                OaToolbar.this.setCenter("mMenuView");
            }
        });
    }

    @Override // androidx.appcompat.widget.Toolbar
    public void setNavigationIcon(@Nullable Drawable drawable) {
        super.setNavigationIcon(drawable);
        setGravityCenter();
    }

    @Override // androidx.appcompat.widget.Toolbar
    public void setTitle(CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            TextView textView = this.mTitleTextView;
            if (textView != null && textView.getParent() == this) {
                removeView(this.mTitleTextView);
            }
        } else {
            if (this.mTitleTextView == null) {
                Context context = getContext();
                TextView textView2 = new TextView(context);
                this.mTitleTextView = textView2;
                textView2.setSingleLine();
                this.mTitleTextView.setEllipsize(TextUtils.TruncateAt.END);
                int i2 = this.mTitleTextAppearance;
                if (i2 != 0) {
                    this.mTitleTextView.setTextAppearance(context, i2);
                }
                int i3 = this.mTitleTextColor;
                if (i3 != 0) {
                    this.mTitleTextView.setTextColor(i3);
                }
            }
            if (this.mTitleTextView.getParent() != this) {
                addCenterView(this.mTitleTextView);
            }
        }
        TextView textView3 = this.mTitleTextView;
        if (textView3 != null) {
            textView3.setText(charSequence);
        }
        this.mTitleText = charSequence;
    }

    @Override // androidx.appcompat.widget.Toolbar
    public void setTitleTextAppearance(Context context, @StyleRes int i2) {
        this.mTitleTextAppearance = i2;
        TextView textView = this.mTitleTextView;
        if (textView != null) {
            textView.setTextAppearance(context, i2);
        }
    }

    @Override // androidx.appcompat.widget.Toolbar
    public void setTitleTextColor(@ColorInt int i2) {
        this.mTitleTextColor = i2;
        TextView textView = this.mTitleTextView;
        if (textView != null) {
            textView.setTextColor(i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.widget.Toolbar, android.view.ViewGroup
    public Toolbar.LayoutParams generateDefaultLayoutParams() {
        Toolbar.LayoutParams layoutParams = new Toolbar.LayoutParams(-2, -2);
        layoutParams.gravity = 17;
        return layoutParams;
    }

    public OaToolbar(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        resolveAttribute(context, attributeSet, R.attr.toolbarStyle);
    }

    @Override // androidx.appcompat.widget.Toolbar, android.view.ViewGroup
    public Toolbar.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        Toolbar.LayoutParams layoutParams = new Toolbar.LayoutParams(getContext(), attributeSet);
        layoutParams.gravity = 17;
        return layoutParams;
    }

    public OaToolbar(Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        resolveAttribute(context, attributeSet, i2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.widget.Toolbar, android.view.ViewGroup
    public Toolbar.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        Toolbar.LayoutParams layoutParams2;
        if (layoutParams instanceof Toolbar.LayoutParams) {
            layoutParams2 = new Toolbar.LayoutParams((Toolbar.LayoutParams) layoutParams);
        } else if (layoutParams instanceof ActionBar.LayoutParams) {
            layoutParams2 = new Toolbar.LayoutParams((ActionBar.LayoutParams) layoutParams);
        } else if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            layoutParams2 = new Toolbar.LayoutParams((ViewGroup.MarginLayoutParams) layoutParams);
        } else {
            layoutParams2 = new Toolbar.LayoutParams(layoutParams);
        }
        layoutParams2.gravity = 17;
        return layoutParams2;
    }
}
