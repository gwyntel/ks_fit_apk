package com.aliyun.alink.business.devicecenter.discover.ble;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.aliyun.alink.business.devicecenter.api.add.DeviceInfo;
import com.aliyun.alink.business.devicecenter.api.discovery.DiscoveryType;
import com.aliyun.alink.business.devicecenter.api.discovery.IDeviceDiscoveryListener;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.aliyun.alink.business.devicecenter.channel.ble.BleChannelClient;
import com.aliyun.alink.business.devicecenter.channel.ble.IBleInterface;
import com.aliyun.alink.business.devicecenter.config.DeviceCenterBiz;
import com.aliyun.alink.business.devicecenter.discover.annotation.DeviceDiscovery;
import com.aliyun.alink.business.devicecenter.discover.base.DiscoverChainBase;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.utils.PermissionCheckerUtils;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

@DeviceDiscovery(discoveryType = {DiscoveryType.BLE_ENROLLEE_DEVICE})
/* loaded from: classes2.dex */
public class BreezeDiscoverChain extends DiscoverChainBase {

    /* renamed from: c, reason: collision with root package name */
    public IDeviceDiscoveryListener f10422c;

    /* renamed from: d, reason: collision with root package name */
    public AtomicBoolean f10423d;

    /* renamed from: e, reason: collision with root package name */
    public Context f10424e;

    /* renamed from: f, reason: collision with root package name */
    public BleChannelClient f10425f;

    /* renamed from: g, reason: collision with root package name */
    public AtomicBoolean f10426g;

    /* renamed from: h, reason: collision with root package name */
    public IBleInterface.IBleScanCallback f10427h;

    public BreezeDiscoverChain(Context context) {
        super(context);
        this.f10422c = null;
        this.f10423d = new AtomicBoolean(false);
        this.f10424e = null;
        this.f10425f = null;
        this.f10426g = new AtomicBoolean(false);
        this.f10427h = new IBleInterface.IBleScanCallback() { // from class: com.aliyun.alink.business.devicecenter.discover.ble.BreezeDiscoverChain.1
            @Override // com.aliyun.alink.business.devicecenter.channel.ble.IBleInterface.IBleScanCallback
            public void onBLEDeviceFound(DeviceInfo deviceInfo) {
                final DiscoveryType discoveryType;
                if (deviceInfo == null || TextUtils.isEmpty(deviceInfo.devType) || TextUtils.isEmpty(deviceInfo.productId)) {
                    ALog.d("BreezeDiscoverChain", "onBLEDeviceFound invalid device. " + deviceInfo);
                    return;
                }
                if (AlinkConstants.DEVICE_TYPE_COMBO_SUBTYPE_2.equals(deviceInfo.devType)) {
                    discoveryType = DiscoveryType.BLE_ENROLLEE_DEVICE;
                } else if (AlinkConstants.DEVICE_TYPE_COMBO_SUBTYPE_3.equals(deviceInfo.devType)) {
                    discoveryType = DiscoveryType.COMBO_SUBTYPE_0X03_DEVICE;
                } else if (AlinkConstants.DEVICE_TYPE_COMBO_SUBTYPE_4.equals(deviceInfo.devType)) {
                    discoveryType = DiscoveryType.COMBO_SUBTYPE_0X04_DEVICE;
                } else {
                    if (!AlinkConstants.DEVICE_TYPE_COMBO_SUBTYPE_8.equals(deviceInfo.devType) && !AlinkConstants.DEVICE_TYPE_COMBO_SUBTYPE_9.equals(deviceInfo.devType) && !AlinkConstants.DEVICE_TYPE_COMBO_SUBTYPE_10.equals(deviceInfo.devType) && !AlinkConstants.DEVICE_TYPE_COMBO_SUBTYPE_11.equals(deviceInfo.devType) && !AlinkConstants.DEVICE_TYPE_COMBO_SUBTYPE_12.equals(deviceInfo.devType)) {
                        ALog.d("BreezeDiscoverChain", "onBLEDeviceFound invalid device subType. " + deviceInfo);
                        return;
                    }
                    discoveryType = DiscoveryType.BLE_ENROLLEE_DEVICE;
                }
                final ArrayList arrayList = new ArrayList();
                arrayList.add(deviceInfo);
                DeviceCenterBiz.getInstance().runOnUIThread(new Runnable() { // from class: com.aliyun.alink.business.devicecenter.discover.ble.BreezeDiscoverChain.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (BreezeDiscoverChain.this.f10422c != null) {
                            BreezeDiscoverChain.this.f10422c.onDeviceFound(discoveryType, arrayList);
                        }
                    }
                });
            }

            @Override // com.aliyun.alink.business.devicecenter.channel.ble.IBleInterface.IBleScanCallback
            public void onStartScan() {
            }

            @Override // com.aliyun.alink.business.devicecenter.channel.ble.IBleInterface.IBleScanCallback
            public void onStopScan() {
            }
        };
        if (context == null) {
            ALog.w("BreezeDiscoverChain", "start ble scan with context = null, return.");
            return;
        }
        Context applicationContext = context.getApplicationContext();
        this.f10424e = applicationContext;
        BleChannelClient bleChannelClient = new BleChannelClient(applicationContext);
        this.f10425f = bleChannelClient;
        bleChannelClient.init(this.f10424e);
    }

    @Override // com.aliyun.alink.business.devicecenter.discover.base.AbilityReceiver
    public void onNotify(Intent intent) {
        if (intent == null || TextUtils.isEmpty(intent.getAction()) || !"ACTION_SYSTEM_ABILITY_CHANGE".equals(intent.getAction())) {
            return;
        }
        String stringExtra = intent.getStringExtra("bluetooth_state");
        String stringExtra2 = intent.getStringExtra("location_state");
        if ("on".equals(stringExtra)) {
            a();
        }
        if ("on".equals(stringExtra2)) {
            a();
        }
    }

    @Override // com.aliyun.alink.business.devicecenter.discover.IDiscoverChain
    public void startDiscover(IDeviceDiscoveryListener iDeviceDiscoveryListener) {
        this.f10422c = iDeviceDiscoveryListener;
        this.f10423d.set(true);
        if (this.f10426g.compareAndSet(false, true)) {
            this.f10426g.set(register("ACTION_SYSTEM_ABILITY_CHANGE"));
        }
        a();
    }

    @Override // com.aliyun.alink.business.devicecenter.discover.IDiscoverChain
    public void stopDiscover() {
        BleChannelClient bleChannelClient = this.f10425f;
        if (bleChannelClient != null) {
            bleChannelClient.stopScan(this.f10427h);
        }
        if (this.f10426g.compareAndSet(true, false)) {
            try {
                unregister();
            } catch (Exception unused) {
            }
        }
    }

    public final void a() {
        if (!PermissionCheckerUtils.isBleAvailable(this.f10424e)) {
            ALog.w("BreezeDiscoverChain", "ble not available, donot start (ble)combo( scan, or it will do nothing for ever.");
            return;
        }
        if (!PermissionCheckerUtils.isLocationPermissionsGranted(this.f10424e)) {
            ALog.w("BreezeDiscoverChain", "Location permission is not granted, donot start (ble)combo( scan, or it will do nothing for ever.");
            return;
        }
        if (!PermissionCheckerUtils.hasBleScanPermission(this.f10424e)) {
            ALog.w("BreezeDiscoverChain", "android 12+ ble scan permission is not granted, donot start (ble)combo( scan, or it will crash.");
            return;
        }
        BleChannelClient bleChannelClient = this.f10425f;
        if (bleChannelClient != null) {
            bleChannelClient.startScan(this.f10427h);
        }
    }
}
