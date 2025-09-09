package com.leeson.image_pickers.activitys;

import android.os.Bundle;

/* loaded from: classes4.dex */
public class PermissionActivity extends BaseActivity {
    public static final String PERMISSIONS = "PERMISSIONS";
    private final int CODE = 505;
    private String[] strings;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        String[] stringArrayExtra = getIntent().getStringArrayExtra(PERMISSIONS);
        this.strings = stringArrayExtra;
        requestPermission(stringArrayExtra, 505);
    }

    @Override // com.leeson.image_pickers.activitys.BaseActivity
    public void permissionFail(int i2) {
        super.permissionFail(i2);
        setResult(0);
        finish();
    }

    @Override // com.leeson.image_pickers.activitys.BaseActivity
    public void permissionSuccess(int i2) {
        super.permissionSuccess(i2);
        setResult(-1, getIntent());
        finish();
    }

    @Override // com.leeson.image_pickers.activitys.BaseActivity
    public void permissonNecessity(int i2) {
        super.permissonNecessity(i2);
        setResult(0);
        finish();
    }
}
