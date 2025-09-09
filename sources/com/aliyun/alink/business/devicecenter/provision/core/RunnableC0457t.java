package com.aliyun.alink.business.devicecenter.provision.core;

import com.aliyun.alink.business.devicecenter.base.AlinkHelper;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.provision.core.broadcast.AlinkBroadcastConfigStrategy;

/* renamed from: com.aliyun.alink.business.devicecenter.provision.core.t, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class RunnableC0457t implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ AlinkBroadcastConfigStrategy f10592a;

    public RunnableC0457t(AlinkBroadcastConfigStrategy alinkBroadcastConfigStrategy) {
        this.f10592a = alinkBroadcastConfigStrategy;
    }

    @Override // java.lang.Runnable
    public void run() throws InterruptedException {
        ALog.d(AlinkBroadcastConfigStrategy.TAG, "startP2PThread run!");
        try {
            if (this.f10592a.provisionHasStopped.get()) {
                ALog.d(AlinkBroadcastConfigStrategy.TAG, "provision has stopped, ignore p2p send");
                return;
            }
            if (!AlinkHelper.isBatchBroadcast(this.f10592a.mConfigParams) && this.f10592a.delayBroadcastTimeAI.get() > 0) {
                Thread.sleep(this.f10592a.delayBroadcastTimeAI.get());
            }
            ALog.d(AlinkBroadcastConfigStrategy.TAG, "start send p2p.");
            if (this.f10592a.provisionHasStopped.get() || this.f10592a.mP2PProvision == null) {
                return;
            }
            this.f10592a.mP2PProvision.a(this.f10592a.mConfigParams);
        } catch (Exception e2) {
            ALog.d(AlinkBroadcastConfigStrategy.TAG, "send start p2p send e= " + e2);
        }
    }
}
