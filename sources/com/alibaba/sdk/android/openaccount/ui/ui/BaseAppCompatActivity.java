package com.alibaba.sdk.android.openaccount.ui.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.accessibility.AccessibilityEventCompat;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConfigs;
import com.alibaba.sdk.android.openaccount.ui.R;
import com.alibaba.sdk.android.openaccount.ui.ui.util.StatusBarHelper;
import io.flutter.plugin.platform.PlatformPlugin;

/* loaded from: classes2.dex */
public class BaseAppCompatActivity extends AppCompatActivity {
    public static final int sUiHidNavigationBarFlags = 4610;
    protected TextView mClockView;
    protected ViewGroup mContentView;
    protected Toolbar mToolBar;
    protected ViewGroup mViewGroup;

    private void initToolBar() {
        this.mToolBar = (Toolbar) findViewById(R.id.ali_user_toolbar);
        this.mClockView = (TextView) findViewById(R.id.tick_tock);
        Toolbar toolbar = this.mToolBar;
        if (toolbar != null) {
            ViewGroup.LayoutParams layoutParams = toolbar.getLayoutParams();
            layoutParams.height = (int) getResources().getDimension(R.dimen.ali_sdk_openaccount_title_bar_height);
            this.mToolBar.setLayoutParams(layoutParams);
            this.mToolBar.setNavigationIcon(R.drawable.openaccount_ic_actionbar_back);
            setSupportActionBar(this.mToolBar);
            Drawable navigationIcon = this.mToolBar.getNavigationIcon();
            if (navigationIcon != null) {
                Drawable drawableWrap = DrawableCompat.wrap(navigationIcon);
                drawableWrap.mutate();
                DrawableCompat.setTint(drawableWrap, ContextCompat.getColor(this, R.color.ali_sdk_openaccount_black));
            }
            this.mToolBar.setNavigationOnClickListener(new View.OnClickListener() { // from class: com.alibaba.sdk.android.openaccount.ui.ui.BaseAppCompatActivity.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    BaseAppCompatActivity.this.onBackPressed();
                }
            });
            this.mToolBar.setTitleTextColor(ContextCompat.getColor(getApplicationContext(), R.color.ali_user_edit_text_textcolor));
        }
        if (OpenAccountUIConfigs.showToolbar) {
            return;
        }
        getSupportActionBar().hide();
    }

    private void setUpViews() {
        try {
            StatusBarHelper.setStatusBarMode(this, OpenAccountUIConfigs.statusBarDarkMode);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        setContentView(R.layout.ali_sdk_openaccount_activity_parent_material);
        this.mViewGroup = (ViewGroup) findViewById(R.id.ali_user_main_content);
        this.mContentView = (ViewGroup) findViewById(R.id.ali_user_content);
        this.mContentView.addView((ViewGroup) getLayoutInflater().inflate(getLayoutContent(), this.mViewGroup, false));
    }

    protected void finishCurrentActivity() {
        finish();
    }

    protected int getLayoutContent() {
        return R.layout.ali_sdk_openaccount_activity_parent_default_content;
    }

    protected void initSystemUI() {
        Window window = getWindow();
        final View decorView = getWindow().getDecorView();
        final int systemUiVisibility = decorView.getSystemUiVisibility();
        if (OpenAccountUIConfigs.hideNavigationBar) {
            systemUiVisibility |= sUiHidNavigationBarFlags;
        }
        if (OpenAccountUIConfigs.statusBarTranslucent) {
            systemUiVisibility |= PlatformPlugin.DEFAULT_SYSTEM_UI;
            window.clearFlags(AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL);
            window.addFlags(Integer.MIN_VALUE);
            window.setStatusBarColor(0);
        }
        decorView.setSystemUiVisibility(systemUiVisibility);
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() { // from class: com.alibaba.sdk.android.openaccount.ui.ui.BaseAppCompatActivity.2
            @Override // android.view.View.OnSystemUiVisibilityChangeListener
            public void onSystemUiVisibilityChange(int i2) {
                if ((i2 & 4) == 0) {
                    decorView.setSystemUiVisibility(systemUiVisibility);
                }
            }
        });
    }

    protected void initViews() {
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        initSystemUI();
        super.onCreate(bundle);
        setUpViews();
        initToolBar();
        initViews();
    }
}
