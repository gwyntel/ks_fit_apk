package com.alibaba.sdk.android.openaccount.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.alibaba.sdk.android.oauth.OauthModule;
import com.alibaba.sdk.android.openaccount.OauthService;
import com.alibaba.sdk.android.openaccount.callback.LoginCallback;
import com.alibaba.sdk.android.openaccount.trace.AliSDKLogger;
import com.alibaba.sdk.android.openaccount.ui.util.AttributeUtils;
import com.alibaba.sdk.android.openaccount.util.ResourceUtils;
import com.alibaba.sdk.android.openaccount.util.WidgetUtils;
import com.alibaba.sdk.android.pluto.Pluto;

/* loaded from: classes2.dex */
public class OauthWidget extends LinearLayoutTemplate implements View.OnClickListener {
    private static final int OAUTH_PLATEFORM_COUNT = 5;
    private static final String TAG = "OauthWidget";
    protected OauthOnClickListener oauthOnClickListener;
    protected TextView[] oauths;
    protected TextView[] texts;

    public OauthWidget(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.oauths = new TextView[5];
        this.texts = new TextView[5];
        int i2 = 0;
        while (i2 < 5) {
            StringBuilder sb = new StringBuilder();
            sb.append("oauth_");
            int i3 = i2 + 1;
            sb.append(i3);
            TextView textView = (TextView) findViewById(sb.toString());
            if (textView != null) {
                textView.setTypeface(this.iconfont);
                textView.setOnClickListener(this);
            }
            this.oauths[i2] = textView;
            this.texts[i2] = (TextView) findViewById("oauth_" + i3 + "_text");
            i2 = i3;
        }
        useCustomAttrs(context, attributeSet);
    }

    public void authorizeCallback(int i2, int i3, Intent intent) {
        OauthService oauthService = (OauthService) Pluto.DEFAULT_INSTANCE.getBean(OauthService.class);
        if (oauthService != null) {
            oauthService.authorizeCallback(i2, i3, intent);
        }
    }

    @Override // com.alibaba.sdk.android.openaccount.ui.widget.LinearLayoutTemplate
    protected void doUseCustomAttrs(Context context, TypedArray typedArray) {
        super.doUseCustomAttrs(context, typedArray);
        int i2 = AttributeUtils.getInt(context, typedArray, "ali_sdk_openaccount_attrs_login_oauth_plateform");
        int i3 = 0;
        boolean z2 = false;
        while (i3 < 5) {
            if (((i2 >>> i3) & 1) != 0) {
                LinearLayout linearLayout = (LinearLayout) findViewById(ResourceUtils.getRId(context, "oauth_" + (i3 + 1) + "_layout"));
                if (i3 == 0 ? OauthModule.isSecondPartTaobaoSdkAvailable() || OauthModule.isThirdPartMemberSdkAvaiable() : i3 == 4 ? OauthModule.isAlipaySdkAvailable() : OauthModule.isUmengAvailable()) {
                    linearLayout.setVisibility(0);
                }
                z2 = true;
            }
            TextView textView = this.oauths[i3];
            if (textView != null) {
                WidgetUtils.setBackgroundDrawable(textView, AttributeUtils.getDrawable(context, typedArray, "ali_sdk_openaccount_attrs_login_oauth_" + (i3 + 1) + "_bg"));
                this.oauths[i3].setTextColor(AttributeUtils.getColor(context, typedArray, "ali_sdk_openaccount_attrs_login_oauth_text_color"));
            }
            TextView textView2 = this.texts[i3];
            if (textView2 != null) {
                textView2.setTextColor(AttributeUtils.getColor(context, typedArray, "ali_sdk_openaccount_attrs_login_oauth_text_text_color"));
            }
            i3++;
        }
        if (z2) {
            setVisibility(0);
        }
        TextView textView3 = (TextView) findViewById(ResourceUtils.getRId(context, "other_plateform_login"));
        if (textView3 != null) {
            textView3.setTextColor(AttributeUtils.getColor(context, typedArray, "ali_sdk_openaccount_attrs_login_other_plateform_login_text_color"));
        }
    }

    @Override // com.alibaba.sdk.android.openaccount.ui.widget.LinearLayoutTemplate
    protected String getLayoutName() {
        return "ali_sdk_openaccount_oauth";
    }

    public OauthOnClickListener getOauthOnClickListener() {
        return this.oauthOnClickListener;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (this.oauthOnClickListener == null) {
            return;
        }
        for (int i2 = 0; i2 < 5; i2++) {
            if (view == this.oauths[i2]) {
                this.oauthOnClickListener.onClick(view, i2 + 1);
            }
        }
    }

    public void setOauthOnClickListener(OauthOnClickListener oauthOnClickListener) {
        this.oauthOnClickListener = oauthOnClickListener;
    }

    public void setOauthOnClickListener(final LoginCallback loginCallback) {
        this.oauthOnClickListener = new OauthOnClickListener() { // from class: com.alibaba.sdk.android.openaccount.ui.widget.OauthWidget.1
            @Override // com.alibaba.sdk.android.openaccount.ui.widget.OauthOnClickListener
            public void onClick(View view, int i2) {
                OauthService oauthService = (OauthService) Pluto.DEFAULT_INSTANCE.getBean(OauthService.class);
                if (oauthService != null) {
                    oauthService.oauth((Activity) view.getContext(), i2, loginCallback);
                } else {
                    AliSDKLogger.e(OauthWidget.TAG, "oauth service is null");
                }
            }
        };
    }
}
