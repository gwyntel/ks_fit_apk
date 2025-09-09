package com.luck.picture.lib.basic;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.luck.picture.lib.PictureSelectorFragment;
import com.luck.picture.lib.R;
import com.luck.picture.lib.config.SelectorConfig;
import com.luck.picture.lib.config.SelectorProviders;
import com.luck.picture.lib.immersive.ImmersiveManager;
import com.luck.picture.lib.language.PictureLanguageUtils;
import com.luck.picture.lib.style.SelectMainStyle;
import com.luck.picture.lib.utils.StyleUtils;

/* loaded from: classes4.dex */
public class PictureSelectorSupporterActivity extends AppCompatActivity {
    private SelectorConfig selectorConfig;

    private void immersive() {
        SelectMainStyle selectMainStyle = this.selectorConfig.selectorStyle.getSelectMainStyle();
        int statusBarColor = selectMainStyle.getStatusBarColor();
        int navigationBarColor = selectMainStyle.getNavigationBarColor();
        boolean zIsDarkStatusBarBlack = selectMainStyle.isDarkStatusBarBlack();
        if (!StyleUtils.checkStyleValidity(statusBarColor)) {
            statusBarColor = ContextCompat.getColor(this, R.color.ps_color_grey);
        }
        if (!StyleUtils.checkStyleValidity(navigationBarColor)) {
            navigationBarColor = ContextCompat.getColor(this, R.color.ps_color_grey);
        }
        ImmersiveManager.immersiveAboveAPI23(this, statusBarColor, navigationBarColor, zIsDarkStatusBarBlack);
    }

    private void initSelectorConfig() {
        this.selectorConfig = SelectorProviders.getInstance().getSelectorConfig();
    }

    private void setupFragment() {
        FragmentInjectManager.injectFragment(this, PictureSelectorFragment.TAG, PictureSelectorFragment.newInstance());
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.ContextThemeWrapper, android.content.ContextWrapper
    protected void attachBaseContext(Context context) {
        SelectorConfig selectorConfig = SelectorProviders.getInstance().getSelectorConfig();
        if (selectorConfig != null) {
            super.attachBaseContext(PictureContextWrapper.wrap(context, selectorConfig.language, selectorConfig.defaultLanguage));
        } else {
            super.attachBaseContext(context);
        }
    }

    @Override // android.app.Activity
    public void finish() {
        super.finish();
        SelectorConfig selectorConfig = this.selectorConfig;
        if (selectorConfig != null) {
            overridePendingTransition(0, selectorConfig.selectorStyle.getWindowAnimationStyle().activityExitAnimation);
        }
    }

    public void initAppLanguage() {
        int i2;
        SelectorConfig selectorConfig = this.selectorConfig;
        if (selectorConfig == null || (i2 = selectorConfig.language) == -2 || selectorConfig.isOnlyCamera) {
            return;
        }
        PictureLanguageUtils.setAppLanguage(this, i2, selectorConfig.defaultLanguage);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(@NonNull Configuration configuration) {
        super.onConfigurationChanged(configuration);
        initAppLanguage();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initSelectorConfig();
        immersive();
        setContentView(R.layout.ps_activity_container);
        setupFragment();
    }
}
