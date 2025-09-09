package com.alibaba.sdk.android.openaccount.ui.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.widget.Toolbar;
import com.alibaba.sdk.android.openaccount.OpenAccountConfigs;
import com.alibaba.sdk.android.openaccount.config.LanguageCode;
import com.alibaba.sdk.android.openaccount.executor.ExecutorService;
import com.alibaba.sdk.android.openaccount.ui.CustomActivity;
import com.alibaba.sdk.android.openaccount.ui.LayoutMapping;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConfigs;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import com.alibaba.sdk.android.openaccount.ui.util.AttributeUtils;
import com.alibaba.sdk.android.openaccount.util.ResourceUtils;
import com.alibaba.sdk.android.openaccount.util.WidgetUtils;
import com.alibaba.sdk.android.pluto.Pluto;
import com.alibaba.sdk.android.pluto.annotation.Autowired;
import java.util.Locale;

/* loaded from: classes2.dex */
public abstract class ActivityTemplate extends BaseAppCompatActivity implements CustomActivity {
    protected AttributeSet attrs;

    @Autowired
    protected ExecutorService executorService;

    protected void doUseCustomAttrs(Context context, TypedArray typedArray) {
        WidgetUtils.setBackgroundDrawable((LinearLayout) findViewById(ResourceUtils.getRId(this, "main")), AttributeUtils.getDrawable(context, typedArray, "ali_sdk_openaccount_attrs_main_bg"));
    }

    public View findViewById(String str) {
        return super.findViewById(ResourceUtils.getRId(this, str));
    }

    @Override // com.alibaba.sdk.android.openaccount.ui.ui.BaseAppCompatActivity
    protected int getLayoutContent() {
        return getLayoutId(this);
    }

    @Override // com.alibaba.sdk.android.openaccount.ui.CustomActivity
    public int getLayoutId(Context context) {
        return LayoutMapping.hasCustomLayout(getClass()) ? LayoutMapping.get(getClass()).intValue() : ResourceUtils.getRLayout(context, getLayoutName());
    }

    protected abstract String getLayoutName();

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        onLanguageSwitchNotify();
    }

    @Override // com.alibaba.sdk.android.openaccount.ui.ui.BaseAppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        onLanguageSwitchNotify();
        super.onCreate(bundle);
        Pluto.DEFAULT_INSTANCE.inject(this);
        this.attrs = Xml.asAttributeSet(getResources().getLayout(getLayoutId(this)));
    }

    protected void onLanguageSwitchNotify() {
        Configuration configuration = getResources().getConfiguration();
        String str = OpenAccountConfigs.clientLocal;
        if (TextUtils.isEmpty(str)) {
            str = LanguageCode.CHINESE;
        }
        String[] strArrSplit = str.split(OpenAccountUIConstants.UNDER_LINE);
        if (strArrSplit == null || strArrSplit.length != 2) {
            return;
        }
        configuration.locale = new Locale(strArrSplit[0], strArrSplit[1]);
        getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        onLanguageSwitchNotify();
    }

    protected void titleBarCustomAttrs(Context context, TypedArray typedArray) {
        Toolbar toolbar = this.mToolBar;
        if (toolbar != null) {
            WidgetUtils.setBackgroundDrawable(toolbar, AttributeUtils.getDrawable(context, typedArray, "ali_sdk_openaccount_attrs_title_bar_bg"));
            String string = AttributeUtils.getString(context, typedArray, "ali_sdk_openaccount_attrs_title_bar_title");
            if (string != null) {
                getSupportActionBar().setTitle(string);
            }
            this.mToolBar.setTitleTextColor(AttributeUtils.getColor(context, typedArray, "ali_sdk_openaccount_attrs_title_bar_text_color"));
            if (OpenAccountUIConfigs.showToolbar) {
                this.mToolBar.setVisibility(AttributeUtils.getInt(context, typedArray, "ali_sdk_openaccount_attrs_title_bar_visibility"));
            }
        }
    }

    protected void useCustomAttrs(Context context, AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = AttributeUtils.obtainStyledAttributes(context, attributeSet);
        doUseCustomAttrs(context, typedArrayObtainStyledAttributes);
        titleBarCustomAttrs(context, typedArrayObtainStyledAttributes);
        typedArrayObtainStyledAttributes.recycle();
    }
}
