package com.huawei.hms.aaid.init;

import android.content.Context;

/* loaded from: classes.dex */
public class a implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    private Context f15717a;

    a(Context context) {
        this.f15717a = context;
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:21:0x008e
        	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1178)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:53)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
        */
    @Override // java.lang.Runnable
    public void run() {
        /*
            r8 = this;
            java.lang.String r0 = "push kit sdk not exists"
            java.lang.String r1 = "AutoInit"
            com.huawei.hms.aaid.constant.ErrorEnum r2 = com.huawei.hms.aaid.constant.ErrorEnum.SUCCESS     // Catch: java.lang.Exception -> L27
            int r2 = r2.getInternalCode()     // Catch: java.lang.Exception -> L27
            r3 = 0
            android.content.Context r4 = r8.f15717a     // Catch: java.lang.Exception -> L27 com.huawei.hms.common.ApiException -> L29
            com.huawei.hms.aaid.HmsInstanceId r4 = com.huawei.hms.aaid.HmsInstanceId.getInstance(r4)     // Catch: java.lang.Exception -> L27 com.huawei.hms.common.ApiException -> L29
            android.content.Context r5 = r8.f15717a     // Catch: java.lang.Exception -> L27 com.huawei.hms.common.ApiException -> L29
            java.lang.String r5 = com.huawei.hms.utils.Util.getAppId(r5)     // Catch: java.lang.Exception -> L27 com.huawei.hms.common.ApiException -> L29
            java.lang.String r3 = r4.getToken(r5, r3)     // Catch: java.lang.Exception -> L27 com.huawei.hms.common.ApiException -> L29
            java.lang.String r4 = "Push init succeed"
            com.huawei.hms.support.log.HMSLog.i(r1, r4)     // Catch: java.lang.Exception -> L27 com.huawei.hms.common.ApiException -> L29
            boolean r4 = android.text.TextUtils.isEmpty(r3)     // Catch: java.lang.Exception -> L27 com.huawei.hms.common.ApiException -> L29
            if (r4 == 0) goto L33
            return
        L27:
            r0 = move-exception
            goto L92
        L29:
            r2 = move-exception
            int r2 = r2.getStatusCode()     // Catch: java.lang.Exception -> L27
            java.lang.String r4 = "new Push init failed"
            com.huawei.hms.support.log.HMSLog.e(r1, r4)     // Catch: java.lang.Exception -> L27
        L33:
            android.content.Context r4 = r8.f15717a     // Catch: java.lang.Exception -> L27 android.content.pm.PackageManager.NameNotFoundException -> L8e
            android.content.pm.PackageManager r4 = r4.getPackageManager()     // Catch: java.lang.Exception -> L27 android.content.pm.PackageManager.NameNotFoundException -> L8e
            android.content.Context r5 = r8.f15717a     // Catch: java.lang.Exception -> L27 android.content.pm.PackageManager.NameNotFoundException -> L8e
            java.lang.String r5 = r5.getPackageName()     // Catch: java.lang.Exception -> L27 android.content.pm.PackageManager.NameNotFoundException -> L8e
            r6 = 128(0x80, float:1.8E-43)
            android.content.pm.ApplicationInfo r4 = r4.getApplicationInfo(r5, r6)     // Catch: java.lang.Exception -> L27 android.content.pm.PackageManager.NameNotFoundException -> L8e
            android.os.Bundle r4 = r4.metaData     // Catch: java.lang.Exception -> L27 android.content.pm.PackageManager.NameNotFoundException -> L8e
            if (r4 == 0) goto L8a
            java.lang.String r5 = "com.huawei.hms.client.service.name:push"
            java.lang.String r4 = r4.getString(r5)     // Catch: java.lang.Exception -> L27 android.content.pm.PackageManager.NameNotFoundException -> L8e
            if (r4 == 0) goto L8a
            android.content.Intent r4 = new android.content.Intent     // Catch: java.lang.Exception -> L27 android.content.pm.PackageManager.NameNotFoundException -> L8e
            java.lang.String r5 = "com.huawei.push.action.MESSAGING_EVENT"
            r4.<init>(r5)     // Catch: java.lang.Exception -> L27 android.content.pm.PackageManager.NameNotFoundException -> L8e
            android.content.Context r5 = r8.f15717a     // Catch: java.lang.Exception -> L27 android.content.pm.PackageManager.NameNotFoundException -> L8e
            java.lang.String r5 = r5.getPackageName()     // Catch: java.lang.Exception -> L27 android.content.pm.PackageManager.NameNotFoundException -> L8e
            r4.setPackage(r5)     // Catch: java.lang.Exception -> L27 android.content.pm.PackageManager.NameNotFoundException -> L8e
            android.os.Bundle r5 = new android.os.Bundle     // Catch: java.lang.Exception -> L27 android.content.pm.PackageManager.NameNotFoundException -> L8e
            r5.<init>()     // Catch: java.lang.Exception -> L27 android.content.pm.PackageManager.NameNotFoundException -> L8e
            java.lang.String r6 = "message_type"
            java.lang.String r7 = "new_token"
            r5.putString(r6, r7)     // Catch: java.lang.Exception -> L27 android.content.pm.PackageManager.NameNotFoundException -> L8e
            java.lang.String r6 = "device_token"
            r5.putString(r6, r3)     // Catch: java.lang.Exception -> L27 android.content.pm.PackageManager.NameNotFoundException -> L8e
            java.lang.String r3 = "error"
            r5.putInt(r3, r2)     // Catch: java.lang.Exception -> L27 android.content.pm.PackageManager.NameNotFoundException -> L8e
            com.huawei.hms.opendevice.l r2 = new com.huawei.hms.opendevice.l     // Catch: java.lang.Exception -> L27 android.content.pm.PackageManager.NameNotFoundException -> L8e
            r2.<init>()     // Catch: java.lang.Exception -> L27 android.content.pm.PackageManager.NameNotFoundException -> L8e
            android.content.Context r3 = r8.f15717a     // Catch: java.lang.Exception -> L27 android.content.pm.PackageManager.NameNotFoundException -> L8e
            boolean r2 = r2.a(r3, r5, r4)     // Catch: java.lang.Exception -> L27 android.content.pm.PackageManager.NameNotFoundException -> L8e
            if (r2 != 0) goto L97
            java.lang.String r2 = "start service failed"
            com.huawei.hms.support.log.HMSLog.e(r1, r2)     // Catch: java.lang.Exception -> L27 android.content.pm.PackageManager.NameNotFoundException -> L8e
            goto L97
        L8a:
            com.huawei.hms.support.log.HMSLog.i(r1, r0)     // Catch: java.lang.Exception -> L27 android.content.pm.PackageManager.NameNotFoundException -> L8e
            goto L97
        L8e:
            com.huawei.hms.support.log.HMSLog.i(r1, r0)     // Catch: java.lang.Exception -> L27
            goto L97
        L92:
            java.lang.String r2 = "Push init failed"
            com.huawei.hms.support.log.HMSLog.e(r1, r2, r0)
        L97:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.aaid.init.a.run():void");
    }
}
