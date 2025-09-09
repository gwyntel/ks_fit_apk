package com.aliyun.alink.business.devicecenter.discover.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

/* loaded from: classes2.dex */
public abstract class AbilityReceiver {

    /* renamed from: a, reason: collision with root package name */
    public Context f10419a;

    /* renamed from: b, reason: collision with root package name */
    public BroadcastReceiver f10420b = new BroadcastReceiver() { // from class: com.aliyun.alink.business.devicecenter.discover.base.AbilityReceiver.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent == null || TextUtils.isEmpty(intent.getAction())) {
                return;
            }
            AbilityReceiver.this.onNotify(intent);
        }
    };

    public AbilityReceiver(Context context) {
        this.f10419a = null;
        this.f10419a = context.getApplicationContext();
    }

    public abstract void onNotify(Intent intent);

    public boolean register(String... strArr) {
        if (this.f10419a == null || strArr == null || strArr.length < 1) {
            return false;
        }
        IntentFilter intentFilter = new IntentFilter();
        int length = strArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            if (!TextUtils.isEmpty(strArr[i2])) {
                intentFilter.addAction(strArr[i2]);
            }
        }
        LocalBroadcastManager.getInstance(this.f10419a).registerReceiver(this.f10420b, intentFilter);
        return true;
    }

    public void unregister() {
        Context context = this.f10419a;
        if (context == null) {
            return;
        }
        LocalBroadcastManager.getInstance(context).unregisterReceiver(this.f10420b);
    }
}
