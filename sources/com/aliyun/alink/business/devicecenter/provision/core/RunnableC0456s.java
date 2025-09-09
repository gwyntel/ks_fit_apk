package com.aliyun.alink.business.devicecenter.provision.core;

import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.provision.core.broadcast.AlinkBroadcastConfigStrategy;
import com.aliyun.alink.business.devicecenter.track.DCUserTrack;
import com.aliyun.alink.business.devicecenter.utils.AlinkWifiSolutionUtils;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;

/* renamed from: com.aliyun.alink.business.devicecenter.provision.core.s, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class RunnableC0456s implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ AlinkBroadcastConfigStrategy f10591a;

    public RunnableC0456s(AlinkBroadcastConfigStrategy alinkBroadcastConfigStrategy) {
        this.f10591a = alinkBroadcastConfigStrategy;
    }

    @Override // java.lang.Runnable
    public void run() throws SocketException, InterruptedException {
        ALog.d(AlinkBroadcastConfigStrategy.TAG, "start send multicast called!");
        try {
            DCUserTrack.addTrackData(AlinkConstants.KEY_BROADCAST_MULTICAST, String.valueOf(System.currentTimeMillis()));
            int length = (this.f10591a.packetMulticastByteArray.length + 1) / 2;
            this.f10591a.udpSocketMulticast = new MulticastSocket();
            this.f10591a.udpSocketMulticast.setBroadcast(true);
            this.f10591a.udpSocketMulticast.setReuseAddress(true);
            AlinkWifiSolutionUtils.printByteArray(this.f10591a.packetMulticastByteArray);
            while (!this.f10591a.provisionHasStopped.get() && this.f10591a.isProvisioningAB.get()) {
                int i2 = 0;
                while (i2 < length && !this.f10591a.provisionHasStopped.get()) {
                    int i3 = i2 * 2;
                    int i4 = this.f10591a.packetMulticastByteArray[i3] & 255;
                    int i5 = (i2 == length + (-1) && this.f10591a.packetMulticastByteArray.length % 2 == 1) ? 0 : this.f10591a.packetMulticastByteArray[i3 + 1] & 255;
                    AlinkBroadcastConfigStrategy alinkBroadcastConfigStrategy = this.f10591a;
                    StringBuilder sb = new StringBuilder();
                    sb.append("239.");
                    sb.append(i2);
                    sb.append(".");
                    sb.append(i4);
                    sb.append(".");
                    sb.append(i5);
                    alinkBroadcastConfigStrategy.addressMulticast = InetAddress.getByName(sb.toString());
                    AlinkBroadcastConfigStrategy alinkBroadcastConfigStrategy2 = this.f10591a;
                    alinkBroadcastConfigStrategy2.sendMulticastUdpPacket(alinkBroadcastConfigStrategy2.addressMulticast, 1);
                    i2++;
                }
                Thread.sleep(this.f10591a.INTERVAL_UDP_Loop);
                String str = AlinkBroadcastConfigStrategy.TAG;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("multicast finish ");
                sb2.append(this.f10591a.INTERVAL_UDP_Loop);
                sb2.append("ms one loop send.");
                ALog.d(str, sb2.toString());
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        AlinkBroadcastConfigStrategy alinkBroadcastConfigStrategy3 = this.f10591a;
        alinkBroadcastConfigStrategy3.closeSocket(alinkBroadcastConfigStrategy3.udpSocketMulticast);
    }
}
