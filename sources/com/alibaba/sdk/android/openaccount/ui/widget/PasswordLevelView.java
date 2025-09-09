package com.alibaba.sdk.android.openaccount.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.alibaba.sdk.android.openaccount.ui.R;

/* loaded from: classes2.dex */
public class PasswordLevelView extends LinearLayout {
    private TextView mLevelTextView;
    private LinearLayout mLinearLayout;
    private int mPasswordLevel;

    public PasswordLevelView(Context context) {
        this(context, null);
    }

    private void initView(View view) {
        this.mLinearLayout = (LinearLayout) view.findViewById(R.id.level_view_container);
        this.mLevelTextView = (TextView) view.findViewById(R.id.tv_level);
        setPasswordLevel(this.mPasswordLevel);
    }

    public void setPasswordLevel(int i2) {
        int i3;
        this.mLinearLayout.removeAllViews();
        this.mLevelTextView.setText(i2 != 0 ? i2 != 1 ? i2 != 2 ? i2 != 3 ? R.string.ali_sdk_openaccount_text_register_password_extremely_weak : R.string.ali_sdk_openaccount_text_register_password_strong : R.string.ali_sdk_openaccount_text_register_password_secondary : R.string.ali_sdk_openaccount_text_register_password_weak : R.string.ali_sdk_openaccount_text_register_password_extremely_weak);
        this.mPasswordLevel = i2;
        int i4 = 0;
        while (true) {
            i3 = this.mPasswordLevel;
            if (i4 >= i3) {
                break;
            }
            View view = new View(getContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(100, 10);
            layoutParams.leftMargin = 10;
            view.setLayoutParams(layoutParams);
            view.setBackgroundResource(R.color.ali_sdk_openaccount_text_display);
            this.mLinearLayout.addView(view);
            i4++;
        }
        int i5 = 3 - i3;
        for (int i6 = 0; i6 < i5; i6++) {
            View view2 = new View(getContext());
            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(100, 10);
            layoutParams2.leftMargin = 10;
            view2.setLayoutParams(layoutParams2);
            view2.setBackgroundResource(R.color.ali_sdk_openaccount_button_bg_disable);
            this.mLinearLayout.addView(view2);
        }
    }

    public PasswordLevelView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, -1);
    }

    public PasswordLevelView(Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.PasswordLevelView);
        this.mPasswordLevel = typedArrayObtainStyledAttributes.getInt(R.styleable.PasswordLevelView_password_level, 0);
        typedArrayObtainStyledAttributes.recycle();
        initView(LayoutInflater.from(context).inflate(R.layout.ali_sdk_openaccount_password_level_view, (ViewGroup) this, true));
    }
}
