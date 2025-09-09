package com.vivo.push.d;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
final class v implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Context f23130a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ Map f23131b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ u f23132c;

    v(u uVar, Context context, Map map) {
        this.f23132c = uVar;
        this.f23130a = context;
        this.f23131b = map;
    }

    @Override // java.lang.Runnable
    public final void run() throws SecurityException {
        String packageName = this.f23130a.getPackageName();
        try {
            List<ActivityManager.RunningTaskInfo> runningTasks = ((ActivityManager) this.f23130a.getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getRunningTasks(100);
            if (runningTasks != null) {
                Iterator<ActivityManager.RunningTaskInfo> it = runningTasks.iterator();
                while (it.hasNext()) {
                    ComponentName componentName = it.next().topActivity;
                    if (componentName.getPackageName().equals(packageName)) {
                        com.vivo.push.util.p.d("OnNotificationClickTask", "topClassName=" + componentName.getClassName());
                        Intent intent = new Intent();
                        intent.setComponent(componentName);
                        intent.setFlags(335544320);
                        u.b(intent, this.f23131b);
                        this.f23130a.startActivity(intent);
                        return;
                    }
                }
            }
        } catch (Exception e2) {
            com.vivo.push.util.p.a("OnNotificationClickTask", "start recentIntent is error", e2);
        }
        Intent launchIntentForPackage = this.f23130a.getPackageManager().getLaunchIntentForPackage(this.f23130a.getPackageName());
        if (launchIntentForPackage == null) {
            com.vivo.push.util.p.a("OnNotificationClickTask", "LaunchIntent is null");
            return;
        }
        launchIntentForPackage.setFlags(268435456);
        u.b(launchIntentForPackage, this.f23131b);
        this.f23130a.startActivity(launchIntentForPackage);
    }
}
