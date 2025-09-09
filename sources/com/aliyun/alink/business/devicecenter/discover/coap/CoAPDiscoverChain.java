package com.aliyun.alink.business.devicecenter.discover.coap;

import android.content.Context;
import android.content.Intent;
import com.aliyun.alink.business.devicecenter.api.add.DeviceInfo;
import com.aliyun.alink.business.devicecenter.api.discovery.DiscoveryType;
import com.aliyun.alink.business.devicecenter.api.discovery.IDeviceDiscoveryListener;
import com.aliyun.alink.business.devicecenter.base.DCErrorCode;
import com.aliyun.alink.business.devicecenter.channel.coap.CoAPClient;
import com.aliyun.alink.business.devicecenter.config.DeviceCenterBiz;
import com.aliyun.alink.business.devicecenter.config.IDeviceInfoNotifyListener;
import com.aliyun.alink.business.devicecenter.discover.CoAPDeviceInfoNotifyHandler;
import com.aliyun.alink.business.devicecenter.discover.annotation.DeviceDiscovery;
import com.aliyun.alink.business.devicecenter.discover.base.DiscoverChainBase;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.utils.ThreadPool;
import com.aliyun.alink.business.devicecenter.utils.WifiManagerUtil;
import com.aliyun.alink.linksdk.alcs.coap.AlcsCoAPRequest;
import com.aliyun.alink.linksdk.alcs.coap.IAlcsCoAPResHandler;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@DeviceDiscovery(discoveryType = {DiscoveryType.LOCAL_ONLINE_DEVICE})
/* loaded from: classes2.dex */
public class CoAPDiscoverChain extends DiscoverChainBase {

    /* renamed from: c, reason: collision with root package name */
    public Future f10449c;

    /* renamed from: d, reason: collision with root package name */
    public AlcsCoAPRequest f10450d;

    /* renamed from: e, reason: collision with root package name */
    public IAlcsCoAPResHandler f10451e;

    /* renamed from: f, reason: collision with root package name */
    public WifiManagerUtil f10452f;

    /* renamed from: g, reason: collision with root package name */
    public long f10453g;

    /* renamed from: h, reason: collision with root package name */
    public boolean f10454h;

    /* renamed from: i, reason: collision with root package name */
    public AtomicBoolean f10455i;

    /* renamed from: j, reason: collision with root package name */
    public int f10456j;

    public CoAPDiscoverChain(Context context) {
        super(context);
        this.f10449c = null;
        this.f10450d = null;
        this.f10451e = null;
        this.f10453g = -1L;
        this.f10454h = false;
        this.f10455i = new AtomicBoolean(false);
        this.f10456j = 5;
        try {
            this.f10452f = new WifiManagerUtil(DeviceCenterBiz.getInstance().getAppContext());
        } catch (Exception e2) {
            ALog.w("CoAPDiscoverChain", "CoAPDiscoverChain wifiManagerUtil create exception=" + e2);
            this.f10452f = null;
        }
        this.f10454h = false;
    }

    @Override // com.aliyun.alink.business.devicecenter.discover.base.AbilityReceiver
    public void onNotify(Intent intent) {
    }

    public void setPeriod(int i2) {
        ALog.d("CoAPDiscoverChain", "setPeriod period=" + i2);
        if (i2 < 2) {
            this.f10456j = 2;
        } else {
            this.f10456j = i2;
        }
    }

    @Override // com.aliyun.alink.business.devicecenter.discover.IDiscoverChain
    public void startDiscover(final IDeviceDiscoveryListener iDeviceDiscoveryListener) {
        ALog.d("CoAPDiscoverChain", "startDiscover call. listener=" + iDeviceDiscoveryListener);
        stopDiscover();
        WifiManagerUtil wifiManagerUtil = this.f10452f;
        if (wifiManagerUtil != null) {
            wifiManagerUtil.acquireMulticastLock();
        }
        this.f10455i.set(true);
        if (!this.f10454h) {
            this.f10451e = new CoAPDeviceInfoNotifyHandler(new IDeviceInfoNotifyListener() { // from class: com.aliyun.alink.business.devicecenter.discover.coap.CoAPDiscoverChain.1
                @Override // com.aliyun.alink.business.devicecenter.config.IDeviceInfoNotifyListener
                public void onDeviceFound(final DeviceInfo deviceInfo) {
                    if (deviceInfo == null || !CoAPDiscoverChain.this.f10455i.get()) {
                        return;
                    }
                    ALog.d("CoAPDiscoverChain", "onDeviceFound discover found dev=" + deviceInfo);
                    DeviceCenterBiz.getInstance().runOnUIThread(new Runnable() { // from class: com.aliyun.alink.business.devicecenter.discover.coap.CoAPDiscoverChain.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (iDeviceDiscoveryListener != null) {
                                ArrayList arrayList = new ArrayList();
                                arrayList.add(deviceInfo);
                                iDeviceDiscoveryListener.onDeviceFound(DiscoveryType.LOCAL_ONLINE_DEVICE, arrayList);
                            }
                        }
                    });
                }

                @Override // com.aliyun.alink.business.devicecenter.config.IDeviceInfoNotifyListener
                public void onFailure(DCErrorCode dCErrorCode) {
                }
            });
            CoAPClient.getInstance().addNotifyListener(this.f10451e);
        }
        this.f10449c = ThreadPool.scheduleAtFixedRate(new Runnable() { // from class: com.aliyun.alink.business.devicecenter.discover.coap.CoAPDiscoverChain.2
            @Override // java.lang.Runnable
            public void run() throws UnknownHostException {
                if (CoAPDiscoverChain.this.f10455i.get()) {
                    CoAPDiscoverChain.this.a(iDeviceDiscoveryListener);
                }
            }
        }, 0L, this.f10456j, TimeUnit.SECONDS);
    }

    @Override // com.aliyun.alink.business.devicecenter.discover.IDiscoverChain
    public void stopDiscover() {
        ALog.d("CoAPDiscoverChain", "stopDiscover call.");
        CoAPClient.getInstance().removeNotifyListener(this.f10451e);
        a(this.f10450d);
        this.f10454h = false;
        WifiManagerUtil wifiManagerUtil = this.f10452f;
        if (wifiManagerUtil != null) {
            wifiManagerUtil.releaseMulticastLock();
        }
        this.f10455i.set(false);
        try {
            Future future = this.f10449c;
            if (future != null) {
                future.cancel(true);
                this.f10449c = null;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x00bb  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00be A[Catch: Exception -> 0x006c, TRY_ENTER, TryCatch #1 {Exception -> 0x006c, blocks: (B:3:0x001b, B:5:0x0027, B:7:0x0036, B:10:0x0058, B:11:0x005b, B:16:0x0070, B:18:0x0077, B:20:0x0086, B:22:0x008d, B:33:0x00c2, B:35:0x00cc, B:37:0x00d6, B:39:0x00ed, B:41:0x00f9, B:43:0x011f, B:32:0x00be, B:26:0x00a4, B:28:0x00ab, B:23:0x009e), top: B:50:0x001b, inners: #0, #2 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void a(final com.aliyun.alink.business.devicecenter.api.discovery.IDeviceDiscoveryListener r7) throws java.net.UnknownHostException {
        /*
            Method dump skipped, instructions count: 339
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aliyun.alink.business.devicecenter.discover.coap.CoAPDiscoverChain.a(com.aliyun.alink.business.devicecenter.api.discovery.IDeviceDiscoveryListener):void");
    }

    public CoAPDiscoverChain(Context context, boolean z2) {
        this(context);
        try {
            this.f10452f = new WifiManagerUtil(DeviceCenterBiz.getInstance().getAppContext());
        } catch (Exception e2) {
            ALog.w("CoAPDiscoverChain", "CoAPDiscoverChain wifiManagerUtil create exception=" + e2);
            this.f10452f = null;
        }
        this.f10454h = z2;
    }

    public final void a(AlcsCoAPRequest alcsCoAPRequest) {
        if (alcsCoAPRequest != null) {
            alcsCoAPRequest.cancel();
        }
        if (this.f10453g != -1) {
            CoAPClient.getInstance().cancelMessage(this.f10453g);
        }
    }
}
