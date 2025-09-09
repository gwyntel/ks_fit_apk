package com.aliyun.alink.business.devicecenter.provision.core;

import android.text.TextUtils;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.aliyun.alink.business.devicecenter.base.AlinkHelper;
import com.aliyun.alink.business.devicecenter.config.IConfigCallback;
import com.aliyun.alink.business.devicecenter.config.model.BackupCheckType;
import com.aliyun.alink.business.devicecenter.config.model.DeviceReportTokenType;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.log.PerformanceLog;
import com.aliyun.alink.business.devicecenter.provision.core.broadcast.AlinkBroadcastConfigStrategy;
import java.util.EnumSet;

/* renamed from: com.aliyun.alink.business.devicecenter.provision.core.q, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class RunnableC0454q implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ IConfigCallback f10588a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ AlinkBroadcastConfigStrategy f10589b;

    public RunnableC0454q(AlinkBroadcastConfigStrategy alinkBroadcastConfigStrategy, IConfigCallback iConfigCallback) {
        this.f10589b = alinkBroadcastConfigStrategy;
        this.f10588a = iConfigCallback;
    }

    @Override // java.lang.Runnable
    public void run() throws InterruptedException {
        try {
            AlinkBroadcastConfigStrategy alinkBroadcastConfigStrategy = this.f10589b;
            alinkBroadcastConfigStrategy.packetData(alinkBroadcastConfigStrategy.mConfigParams.ssid, this.f10589b.mConfigParams.password);
            if (this.f10589b.enableMulticastSend) {
                AlinkBroadcastConfigStrategy alinkBroadcastConfigStrategy2 = this.f10589b;
                alinkBroadcastConfigStrategy2.packetMulticastData(alinkBroadcastConfigStrategy2.mConfigParams.ssid, this.f10589b.mConfigParams.password);
                if (this.f10589b.useAppTokenAB.get()) {
                    AlinkBroadcastConfigStrategy alinkBroadcastConfigStrategy3 = this.f10589b;
                    String token = alinkBroadcastConfigStrategy3.getToken(alinkBroadcastConfigStrategy3.appRandom, this.f10589b.mConfigParams.password);
                    if (!TextUtils.isEmpty(token) && token.length() > 31) {
                        this.f10589b.mConfigParams.bindToken = token.substring(0, 32).toUpperCase();
                    }
                }
                String str = AlinkBroadcastConfigStrategy.TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("useAppTokenAB=");
                sb.append(this.f10589b.useAppTokenAB);
                sb.append(", token=");
                sb.append(this.f10589b.mConfigParams.bindToken);
                sb.append(", appRandom=");
                sb.append(this.f10589b.appRandom);
                sb.append(", cloudRandom=");
                sb.append(this.f10589b.cloudRandom);
                ALog.d(str, sb.toString());
                if (this.f10588a != null && !AlinkHelper.isBatchBroadcast(this.f10589b.mConfigParams)) {
                    this.f10589b.updateBackupCheckType(DeviceReportTokenType.APP_TOKEN);
                    this.f10589b.startBackupCheck(true, 0L);
                }
                String str2 = AlinkBroadcastConfigStrategy.TAG;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("final token = ");
                sb2.append(this.f10589b.mConfigParams.bindToken);
                ALog.d(str2, sb2.toString());
            } else {
                this.f10589b.updateBackupCheckTypeSet(EnumSet.of(BackupCheckType.CHECK_COAP_GET));
                this.f10589b.startBackupCheck(true, 0L);
            }
            PerformanceLog.trace(AlinkBroadcastConfigStrategy.TAG, AlinkConstants.KEY_BROADCAST, PerformanceLog.getJsonObject("type", "smartConfig"));
            this.f10589b.setSendLevel(1);
            this.f10589b.send();
            while (!this.f10589b.provisionHasStopped.get()) {
                this.f10589b.setSendLevel(1);
                Thread.sleep(this.f10589b.INTERVAL_UDP_SENDING);
                this.f10589b.setSendLevel(2);
                Thread.sleep(this.f10589b.INTERVAL_UDP_SENDING);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
