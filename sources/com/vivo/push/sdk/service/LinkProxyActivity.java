package com.vivo.push.sdk.service;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import com.vivo.push.util.p;
import com.vivo.push.util.z;
import java.util.List;

/* loaded from: classes4.dex */
public class LinkProxyActivity extends Activity {
    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        PackageManager packageManager;
        List<ResolveInfo> listQueryIntentServices;
        ResolveInfo resolveInfo;
        ServiceInfo serviceInfo;
        super.onCreate(bundle);
        Intent intent = getIntent();
        if (intent == null) {
            p.d("LinkProxyActivity", "enter RequestPermissionsActivity onCreate, intent is null, finish");
            finish();
            return;
        }
        boolean z2 = true;
        try {
            Window window = getWindow();
            window.setGravity(8388659);
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.x = 0;
            attributes.y = 0;
            attributes.height = 1;
            attributes.width = 1;
            window.setAttributes(attributes);
        } catch (Throwable th) {
            p.b("LinkProxyActivity", "enter onCreate error ", th);
        }
        String packageName = getPackageName();
        p.d("LinkProxyActivity", hashCode() + " enter onCreate " + packageName);
        if ("com.vivo.abe".equals(packageName)) {
            try {
                if (intent.getExtras() == null) {
                    p.d("LinkProxyActivity", "adapterToService getExtras() is null");
                } else {
                    Intent intent2 = (Intent) intent.getExtras().get("previous_intent");
                    if (intent2 == null) {
                        p.d("LinkProxyActivity", "adapterToService proxyIntent is null");
                    } else {
                        z.a(this, intent2);
                    }
                }
            } catch (Exception e2) {
                p.a("LinkProxyActivity", e2.toString(), e2);
            }
        } else {
            try {
                if (intent.getExtras() != null) {
                    Intent intent3 = (Intent) intent.getExtras().get("previous_intent");
                    if (intent3 == null || (packageManager = getPackageManager()) == null || (listQueryIntentServices = packageManager.queryIntentServices(intent3, 576)) == null || listQueryIntentServices.isEmpty() || (resolveInfo = listQueryIntentServices.get(0)) == null || (serviceInfo = resolveInfo.serviceInfo) == null || !serviceInfo.exported) {
                        z2 = false;
                    }
                    if (z2) {
                        startService(intent3);
                    } else {
                        p.b("LinkProxyActivity", "service's exported is ".concat(String.valueOf(z2)));
                    }
                }
            } catch (Exception e3) {
                p.a("LinkProxyActivity", e3.toString(), e3);
            }
        }
        finish();
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        p.d("LinkProxyActivity", hashCode() + " onDestory " + getPackageName());
    }
}
