package com.taobao.accs;

import android.app.Notification;
import com.taobao.accs.ChannelService;
import com.taobao.accs.utl.ALog;

/* loaded from: classes4.dex */
class a implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ ChannelService.KernelService f20049a;

    a(ChannelService.KernelService kernelService) {
        this.f20049a = kernelService;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            ChannelService channelService = ChannelService.getInstance();
            int i2 = this.f20049a.f20048b.getPackageManager().getPackageInfo(this.f20049a.getPackageName(), 0).applicationInfo.icon;
            if (i2 != 0) {
                Notification.Builder builder = new Notification.Builder(this.f20049a.f20048b);
                builder.setSmallIcon(i2);
                builder.setContentText("正在运行…");
                channelService.startForeground(9371, builder.build());
                Notification.Builder builder2 = new Notification.Builder(this.f20049a.f20048b);
                builder2.setSmallIcon(i2);
                builder2.setContentText("正在运行…");
                ChannelService.KernelService.f20047a.startForeground(9371, builder2.build());
                ChannelService.KernelService.f20047a.stopForeground(true);
            }
            ChannelService.KernelService.f20047a.stopSelf();
        } catch (Throwable th) {
            ALog.e("ChannelService", " onStartCommand run", th, new Object[0]);
        }
    }
}
