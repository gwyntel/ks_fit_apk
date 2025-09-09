package com.aliyun.alink.business.devicecenter.provision.core;

import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.aliyun.alink.business.devicecenter.base.AlinkHelper;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.provision.core.broadcast.AlinkBroadcastConfigStrategy;
import com.aliyun.alink.business.devicecenter.track.DCUserTrack;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/* renamed from: com.aliyun.alink.business.devicecenter.provision.core.r, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class RunnableC0455r implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ AlinkBroadcastConfigStrategy f10590a;

    public RunnableC0455r(AlinkBroadcastConfigStrategy alinkBroadcastConfigStrategy) {
        this.f10590a = alinkBroadcastConfigStrategy;
    }

    @Override // java.lang.Runnable
    public void run() throws SocketException, InterruptedException {
        ALog.d(AlinkBroadcastConfigStrategy.TAG, "startProvisonThread run!");
        try {
            this.f10590a.address = InetAddress.getByName("255.255.255.255");
            this.f10590a.udpSocket = new DatagramSocket();
            this.f10590a.udpSocket.setBroadcast(true);
            this.f10590a.udpSocket.setReuseAddress(true);
            if (!AlinkHelper.isBatchBroadcast(this.f10590a.mConfigParams) && this.f10590a.delayBroadcastTimeAI.get() > 0) {
                Thread.sleep(this.f10590a.delayBroadcastTimeAI.get() + 200);
            }
            DCUserTrack.addTrackData(AlinkConstants.KEY_BROADCAST, String.valueOf(System.currentTimeMillis()));
            ALog.d(AlinkBroadcastConfigStrategy.TAG, "start send broadcast packet.");
            while (!this.f10590a.provisionHasStopped.get() && this.f10590a.isProvisioningAB.get()) {
                this.f10590a.port = 50000;
                this.f10590a.portIndex = 0;
                this.f10590a.UDP_SEND(1248);
                this.f10590a.UDP_SEND(1248);
                this.f10590a.UDP_SEND(1248);
                this.f10590a.UDP_SEND(1248);
                this.f10590a.UDP_SEND(1248);
                this.f10590a.UDP_SEND(1248);
                byte b2 = this.f10590a.send_data[0];
                int i2 = 0;
                int i3 = 2;
                for (int i4 = 0; i4 < b2; i4++) {
                    this.f10590a.UDP_SEND((i3 * 128) + (this.f10590a.send_data[i4] & 255));
                    if (i4 % 8 == 7) {
                        int i5 = i2 + 1;
                        int i6 = i2 + 993;
                        this.f10590a.UDP_SEND(i6);
                        this.f10590a.UDP_SEND(i6);
                        if (this.f10590a.provisionHasStopped.get()) {
                            break;
                        } else {
                            i2 = i5;
                        }
                    }
                    i3++;
                    if (i3 == 10) {
                        i3 = 2;
                    }
                }
                if (this.f10590a.provisionHasStopped.get()) {
                    break;
                }
                Thread.sleep(this.f10590a.INTERVAL_UDP_Loop);
                String str = AlinkBroadcastConfigStrategy.TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("broadcast finish ");
                sb.append(this.f10590a.INTERVAL_UDP_Loop);
                sb.append("ms one loop send.");
                ALog.d(str, sb.toString());
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        AlinkBroadcastConfigStrategy alinkBroadcastConfigStrategy = this.f10590a;
        alinkBroadcastConfigStrategy.closeSocket(alinkBroadcastConfigStrategy.udpSocket);
    }
}
