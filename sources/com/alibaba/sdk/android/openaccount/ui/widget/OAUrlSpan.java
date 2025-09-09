package com.alibaba.sdk.android.openaccount.ui.widget;

import android.content.Context;
import android.content.Intent;
import android.text.style.URLSpan;
import android.view.View;
import com.alibaba.sdk.android.openaccount.ui.TokenWebViewActivity;

/* loaded from: classes2.dex */
public class OAUrlSpan extends URLSpan {
    public OAUrlSpan(String str) {
        super(str);
    }

    @Override // android.text.style.URLSpan, android.text.style.ClickableSpan
    public void onClick(View view) {
        if (view == null || view.getContext() == null) {
            return;
        }
        Context context = view.getContext();
        Intent intent = new Intent();
        intent.setClass(context, TokenWebViewActivity.class);
        intent.putExtra("url", getURL());
        context.startActivity(intent);
    }
}
