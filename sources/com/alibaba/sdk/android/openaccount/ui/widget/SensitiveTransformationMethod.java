package com.alibaba.sdk.android.openaccount.ui.widget;

import android.graphics.Rect;
import android.text.method.TransformationMethod;
import android.view.View;
import androidx.webkit.ProxyConfig;

/* loaded from: classes2.dex */
public class SensitiveTransformationMethod implements TransformationMethod {
    public static final SensitiveTransformationMethod INSTANCE = new SensitiveTransformationMethod();

    @Override // android.text.method.TransformationMethod
    public CharSequence getTransformation(CharSequence charSequence, View view) {
        if (charSequence.length() < 4) {
            return charSequence;
        }
        StringBuilder sb = new StringBuilder();
        int length = charSequence.length() / 3;
        sb.append(charSequence.subSequence(0, length));
        int length2 = (charSequence.length() - length) / 2;
        for (int i2 = 0; i2 < length2; i2++) {
            sb.append(ProxyConfig.MATCH_ALL_SCHEMES);
        }
        sb.append(charSequence.subSequence(length + length2, charSequence.length()));
        return sb.toString();
    }

    @Override // android.text.method.TransformationMethod
    public void onFocusChanged(View view, CharSequence charSequence, boolean z2, int i2, Rect rect) {
    }
}
