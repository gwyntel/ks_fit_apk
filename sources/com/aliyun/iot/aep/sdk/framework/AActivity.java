package com.aliyun.iot.aep.sdk.framework;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import com.aliyun.iot.aep.sdk.framework.language.LanguageManager;

/* loaded from: classes3.dex */
public abstract class AActivity extends FragmentActivity {

    /* renamed from: a, reason: collision with root package name */
    private int f11749a = 0;

    protected void a(boolean z2) {
        AApplication.getInstance().getBus().blockChannel(this.f11749a, z2);
    }

    @Override // android.app.Activity, android.view.ContextThemeWrapper, android.content.ContextWrapper
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(LanguageManager.replaceLanguage(context));
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        this.f11749a = 0;
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
        a(true);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        a(false);
    }
}
