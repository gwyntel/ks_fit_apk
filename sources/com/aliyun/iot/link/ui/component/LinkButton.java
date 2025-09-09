package com.aliyun.iot.link.ui.component;

import android.content.Context;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatButton;

/* loaded from: classes3.dex */
public class LinkButton extends AppCompatButton {
    public LinkButton(Context context) {
        this(context, null);
    }

    public LinkButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.buttonStyle);
    }

    public LinkButton(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
    }
}
