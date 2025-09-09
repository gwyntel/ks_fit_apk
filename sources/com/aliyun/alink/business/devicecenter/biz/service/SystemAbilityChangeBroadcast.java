package com.aliyun.alink.business.devicecenter.biz.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes2.dex */
public class SystemAbilityChangeBroadcast {
    public static final String ACTION_SYSTEM_ABILITY_CHANGE = "ACTION_SYSTEM_ABILITY_CHANGE";
    public static final String ACTION_SYSTEM_BLUETOOTH_STATE = "bluetooth_state";
    public static final String ACTION_SYSTEM_LOCATION_STATE = "location_state";

    /* renamed from: a, reason: collision with root package name */
    public Context f10202a;

    /* renamed from: b, reason: collision with root package name */
    public AtomicBoolean f10203b;

    /* renamed from: c, reason: collision with root package name */
    public BroadcastReceiver f10204c;

    private static class SingletonHolder {

        /* renamed from: a, reason: collision with root package name */
        public static final SystemAbilityChangeBroadcast f10206a = new SystemAbilityChangeBroadcast();
    }

    public static SystemAbilityChangeBroadcast getInstance() {
        return SingletonHolder.f10206a;
    }

    public void deInit() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d("SystemAbilityChangeBroa", "deInit() called");
        if (this.f10203b.get()) {
            Context context = this.f10202a;
            if (context != null) {
                context.unregisterReceiver(this.f10204c);
            }
            this.f10203b.set(false);
        }
    }

    public void init(Context context) {
        if (context == null) {
            return;
        }
        this.f10202a = context.getApplicationContext();
        if (this.f10203b.compareAndSet(false, true)) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.location.PROVIDERS_CHANGED");
            intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
            Context context2 = this.f10202a;
            if (context2 != null) {
                context2.registerReceiver(this.f10204c, intentFilter);
            }
        }
    }

    public SystemAbilityChangeBroadcast() {
        this.f10202a = null;
        this.f10203b = new AtomicBoolean(false);
        this.f10204c = new BroadcastReceiver() { // from class: com.aliyun.alink.business.devicecenter.biz.service.SystemAbilityChangeBroadcast.1
            /* JADX WARN: Removed duplicated region for block: B:22:0x0035  */
            @Override // android.content.BroadcastReceiver
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void onReceive(android.content.Context r6, android.content.Intent r7) throws java.lang.IllegalAccessException, java.lang.IllegalArgumentException, java.lang.reflect.InvocationTargetException {
                /*
                    r5 = this;
                    if (r7 == 0) goto L89
                    java.lang.String r0 = r7.getAction()     // Catch: java.lang.Exception -> L29
                    if (r0 != 0) goto La
                    goto L89
                La:
                    java.lang.String r0 = r7.getAction()     // Catch: java.lang.Exception -> L29
                    int r1 = r0.hashCode()     // Catch: java.lang.Exception -> L29
                    r2 = -1530327060(0xffffffffa4c90fec, float:-8.719683E-17)
                    r3 = 0
                    r4 = 1
                    if (r1 == r2) goto L2b
                    r2 = -1184851779(0xffffffffb96098bd, float:-2.1419204E-4)
                    if (r1 == r2) goto L1f
                    goto L35
                L1f:
                    java.lang.String r1 = "android.location.PROVIDERS_CHANGED"
                    boolean r0 = r0.equals(r1)     // Catch: java.lang.Exception -> L29
                    if (r0 == 0) goto L35
                    r0 = r4
                    goto L36
                L29:
                    r6 = move-exception
                    goto L7f
                L2b:
                    java.lang.String r1 = "android.bluetooth.adapter.action.STATE_CHANGED"
                    boolean r0 = r0.equals(r1)     // Catch: java.lang.Exception -> L29
                    if (r0 == 0) goto L35
                    r0 = r3
                    goto L36
                L35:
                    r0 = -1
                L36:
                    java.lang.String r1 = "on"
                    java.lang.String r2 = "off"
                    if (r0 == 0) goto L5b
                    if (r0 == r4) goto L3f
                    goto L89
                L3f:
                    boolean r6 = com.aliyun.alink.business.devicecenter.utils.PermissionUtils.isLocationEnabled(r6)     // Catch: java.lang.Exception -> L29
                    java.lang.String r7 = "location_state"
                    if (r6 != 0) goto L51
                    com.aliyun.alink.business.devicecenter.biz.service.SystemAbilityChangeBroadcast r6 = com.aliyun.alink.business.devicecenter.biz.service.SystemAbilityChangeBroadcast.this     // Catch: java.lang.Exception -> L29
                    java.lang.String[] r7 = new java.lang.String[]{r7, r2}     // Catch: java.lang.Exception -> L29
                    com.aliyun.alink.business.devicecenter.biz.service.SystemAbilityChangeBroadcast.a(r6, r7)     // Catch: java.lang.Exception -> L29
                    goto L89
                L51:
                    com.aliyun.alink.business.devicecenter.biz.service.SystemAbilityChangeBroadcast r6 = com.aliyun.alink.business.devicecenter.biz.service.SystemAbilityChangeBroadcast.this     // Catch: java.lang.Exception -> L29
                    java.lang.String[] r7 = new java.lang.String[]{r7, r1}     // Catch: java.lang.Exception -> L29
                    com.aliyun.alink.business.devicecenter.biz.service.SystemAbilityChangeBroadcast.a(r6, r7)     // Catch: java.lang.Exception -> L29
                    goto L89
                L5b:
                    java.lang.String r6 = "android.bluetooth.adapter.extra.STATE"
                    int r6 = r7.getIntExtra(r6, r3)     // Catch: java.lang.Exception -> L29
                    r7 = 10
                    java.lang.String r0 = "bluetooth_state"
                    if (r6 != r7) goto L71
                    com.aliyun.alink.business.devicecenter.biz.service.SystemAbilityChangeBroadcast r6 = com.aliyun.alink.business.devicecenter.biz.service.SystemAbilityChangeBroadcast.this     // Catch: java.lang.Exception -> L29
                    java.lang.String[] r7 = new java.lang.String[]{r0, r2}     // Catch: java.lang.Exception -> L29
                    com.aliyun.alink.business.devicecenter.biz.service.SystemAbilityChangeBroadcast.a(r6, r7)     // Catch: java.lang.Exception -> L29
                    goto L89
                L71:
                    r7 = 12
                    if (r6 != r7) goto L89
                    com.aliyun.alink.business.devicecenter.biz.service.SystemAbilityChangeBroadcast r6 = com.aliyun.alink.business.devicecenter.biz.service.SystemAbilityChangeBroadcast.this     // Catch: java.lang.Exception -> L29
                    java.lang.String[] r7 = new java.lang.String[]{r0, r1}     // Catch: java.lang.Exception -> L29
                    com.aliyun.alink.business.devicecenter.biz.service.SystemAbilityChangeBroadcast.a(r6, r7)     // Catch: java.lang.Exception -> L29
                    goto L89
                L7f:
                    r6.printStackTrace()
                    java.lang.String r6 = "SystemAbilityChangeBroa"
                    java.lang.String r7 = "mReceiver error"
                    com.aliyun.alink.linksdk.tools.ALog.e(r6, r7)
                L89:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.aliyun.alink.business.devicecenter.biz.service.SystemAbilityChangeBroadcast.AnonymousClass1.onReceive(android.content.Context, android.content.Intent):void");
            }
        };
    }

    public final void a(String... strArr) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (strArr == null || strArr.length < 1) {
            return;
        }
        Intent intent = new Intent("ACTION_SYSTEM_ABILITY_CHANGE");
        int length = strArr.length;
        for (int i2 = 0; i2 < length / 2; i2++) {
            if (!TextUtils.isEmpty(strArr[i2])) {
                int i3 = i2 + 1;
                if (!TextUtils.isEmpty(strArr[i3])) {
                    intent.putExtra(strArr[i2], strArr[i3]);
                }
            }
        }
        ALog.d("SystemAbilityChangeBroa", "sendBroadcast() called with: intent = [" + intent + "]");
        LocalBroadcastManager.getInstance(this.f10202a).sendBroadcast(intent);
    }
}
