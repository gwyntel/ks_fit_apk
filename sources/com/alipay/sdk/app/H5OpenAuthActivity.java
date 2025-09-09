package com.alipay.sdk.app;

import android.content.Intent;
import android.net.Uri;
import com.alipay.sdk.m.k.b;
import com.alipay.sdk.m.s.a;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;

/* loaded from: classes2.dex */
public class H5OpenAuthActivity extends H5PayActivity {

    /* renamed from: i, reason: collision with root package name */
    public boolean f9113i = false;

    @Override // com.alipay.sdk.app.H5PayActivity
    public void a() {
    }

    @Override // com.alipay.sdk.app.H5PayActivity, android.app.Activity
    public void onDestroy() {
        if (this.f9113i) {
            try {
                a aVarA = a.C0055a.a(getIntent());
                if (aVarA != null) {
                    com.alipay.sdk.m.k.a.b(this, aVarA, "", aVarA.f9727d);
                }
            } catch (Throwable unused) {
            }
        }
        super.onDestroy();
    }

    @Override // android.app.Activity, android.content.ContextWrapper, android.content.Context
    public void startActivity(Intent intent) {
        try {
            a aVarA = a.C0055a.a(intent);
            try {
                super.startActivity(intent);
                Uri data = intent != null ? intent.getData() : null;
                if (data == null || !data.toString().startsWith("alipays://platformapi/startapp")) {
                    return;
                }
                finish();
            } catch (Throwable th) {
                String string = (intent == null || intent.getData() == null) ? TmpConstant.GROUP_ROLE_UNKNOWN : intent.getData().toString();
                if (aVarA != null) {
                    com.alipay.sdk.m.k.a.a(aVarA, b.f9364l, b.f9367m0, th, string);
                }
                this.f9113i = true;
                throw th;
            }
        } catch (Throwable unused) {
            finish();
        }
    }
}
