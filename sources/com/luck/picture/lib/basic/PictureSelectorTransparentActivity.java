package com.luck.picture.lib.basic;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.luck.picture.lib.PictureOnlyCameraFragment;
import com.luck.picture.lib.PictureSelectorPreviewFragment;
import com.luck.picture.lib.PictureSelectorSystemFragment;
import com.luck.picture.lib.R;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.SelectorConfig;
import com.luck.picture.lib.config.SelectorProviders;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.immersive.ImmersiveManager;
import com.luck.picture.lib.interfaces.OnInjectActivityPreviewListener;
import com.luck.picture.lib.style.SelectMainStyle;
import com.luck.picture.lib.utils.StyleUtils;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class PictureSelectorTransparentActivity extends AppCompatActivity {
    private SelectorConfig selectorConfig;

    private void immersive() {
        if (this.selectorConfig.selectorStyle == null) {
            SelectorProviders.getInstance().getSelectorConfig();
        }
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

    private boolean isExternalPreview() {
        return getIntent().getIntExtra(PictureConfig.EXTRA_MODE_TYPE_SOURCE, 0) == 2;
    }

    @SuppressLint({"RtlHardcoded"})
    private void setActivitySize() {
        Window window = getWindow();
        window.setGravity(51);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.x = 0;
        attributes.y = 0;
        attributes.height = 1;
        attributes.width = 1;
        window.setAttributes(attributes);
    }

    private void setupFragment() {
        String fragmentTag;
        PictureSelectorPreviewFragment pictureSelectorPreviewFragmentNewInstance;
        Fragment fragmentNewInstance;
        int intExtra = getIntent().getIntExtra(PictureConfig.EXTRA_MODE_TYPE_SOURCE, 0);
        if (intExtra == 1) {
            fragmentTag = PictureSelectorSystemFragment.TAG;
            fragmentNewInstance = PictureSelectorSystemFragment.newInstance();
        } else if (intExtra == 2) {
            OnInjectActivityPreviewListener onInjectActivityPreviewListener = this.selectorConfig.onInjectActivityPreviewListener;
            PictureSelectorPreviewFragment pictureSelectorPreviewFragmentOnInjectPreviewFragment = onInjectActivityPreviewListener != null ? onInjectActivityPreviewListener.onInjectPreviewFragment() : null;
            if (pictureSelectorPreviewFragmentOnInjectPreviewFragment != null) {
                pictureSelectorPreviewFragmentNewInstance = pictureSelectorPreviewFragmentOnInjectPreviewFragment;
                fragmentTag = pictureSelectorPreviewFragmentOnInjectPreviewFragment.getFragmentTag();
            } else {
                fragmentTag = PictureSelectorPreviewFragment.TAG;
                pictureSelectorPreviewFragmentNewInstance = PictureSelectorPreviewFragment.newInstance();
            }
            int intExtra2 = getIntent().getIntExtra(PictureConfig.EXTRA_PREVIEW_CURRENT_POSITION, 0);
            ArrayList<LocalMedia> arrayList = new ArrayList<>(this.selectorConfig.selectedPreviewResult);
            pictureSelectorPreviewFragmentNewInstance.setExternalPreviewData(intExtra2, arrayList.size(), arrayList, getIntent().getBooleanExtra(PictureConfig.EXTRA_EXTERNAL_PREVIEW_DISPLAY_DELETE, false));
            fragmentNewInstance = pictureSelectorPreviewFragmentNewInstance;
        } else {
            fragmentTag = PictureOnlyCameraFragment.TAG;
            fragmentNewInstance = PictureOnlyCameraFragment.newInstance();
        }
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        Fragment fragmentFindFragmentByTag = supportFragmentManager.findFragmentByTag(fragmentTag);
        if (fragmentFindFragmentByTag != null) {
            supportFragmentManager.beginTransaction().remove(fragmentFindFragmentByTag).commitAllowingStateLoss();
        }
        FragmentInjectManager.injectSystemRoomFragment(supportFragmentManager, fragmentTag, fragmentNewInstance);
    }

    @Override // android.app.Activity
    public void finish() {
        super.finish();
        if (getIntent().getIntExtra(PictureConfig.EXTRA_MODE_TYPE_SOURCE, 0) == 2) {
            SelectorConfig selectorConfig = this.selectorConfig;
            if (!selectorConfig.isPreviewZoomEffect) {
                overridePendingTransition(0, selectorConfig.selectorStyle.getWindowAnimationStyle().activityExitAnimation);
                return;
            }
        }
        overridePendingTransition(0, R.anim.ps_anim_fade_out);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initSelectorConfig();
        immersive();
        setContentView(R.layout.ps_empty);
        if (!isExternalPreview()) {
            setActivitySize();
        }
        setupFragment();
    }
}
