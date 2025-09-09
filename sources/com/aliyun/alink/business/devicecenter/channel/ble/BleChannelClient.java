package com.aliyun.alink.business.devicecenter.channel.ble;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.aliyun.alink.business.devicecenter.base.DCEnvHelper;
import com.aliyun.alink.business.devicecenter.channel.ble.IBleInterface;
import com.aliyun.alink.business.devicecenter.config.model.DCConfigParams;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.utils.PermissionCheckerUtils;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes2.dex */
public class BleChannelClient implements IBleInterface {

    /* renamed from: a, reason: collision with root package name */
    public Context f10227a;

    /* renamed from: b, reason: collision with root package name */
    public int f10228b;
    public IBleInterface bleStrategy;

    /* renamed from: c, reason: collision with root package name */
    public AtomicBoolean f10229c = new AtomicBoolean(false);

    /* renamed from: d, reason: collision with root package name */
    public BroadcastReceiver f10230d = new BroadcastReceiver() { // from class: com.aliyun.alink.business.devicecenter.channel.ble.BleChannelClient.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent == null || intent.getAction() == null) {
                return;
            }
            try {
                String action = intent.getAction();
                if (action.hashCode() == -1530327060 && action.equals("android.bluetooth.adapter.action.STATE_CHANGED")) {
                    int intExtra = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", 0);
                    BleChannelClient.this.f10228b = intExtra;
                    switch (intExtra) {
                        case 10:
                            ALog.d("BleChannelClient", "BluetoothAdapter.STATE_OFF");
                            break;
                        case 11:
                            ALog.d("BleChannelClient", "BluetoothAdapter.STATE_TURNING_ON");
                            break;
                        case 12:
                            ALog.d("BleChannelClient", "BluetoothAdapter.STATE_ON");
                            break;
                        case 13:
                            ALog.d("BleChannelClient", "BluetoothAdapter.STATE_TURNING_OFF");
                            break;
                    }
                }
            } catch (Exception e2) {
                ALog.w("BleChannelClient", "onReceive ble exception:" + e2);
            }
        }
    };

    public BleChannelClient(Context context) {
        this.bleStrategy = null;
        this.f10227a = null;
        this.f10228b = 10;
        if (context == null) {
            return;
        }
        this.f10227a = context.getApplicationContext();
        if (DCEnvHelper.isTgEnv()) {
            this.bleStrategy = new TgBleChannelImpl();
        } else {
            this.bleStrategy = new ILopBleChannelImpl();
        }
        this.f10228b = PermissionCheckerUtils.isBleAvailable(context) ? 12 : 10;
    }

    public final void b(Context context) {
        if (context == null) {
            return;
        }
        context.unregisterReceiver(this.f10230d);
    }

    @Override // com.aliyun.alink.business.devicecenter.channel.ble.IBleInterface
    public boolean channelEncrypt(IBleInterface.IBleChannelDevice iBleChannelDevice) {
        return this.bleStrategy.channelEncrypt(iBleChannelDevice);
    }

    @Override // com.aliyun.alink.business.devicecenter.channel.ble.IBleInterface
    public void connect(String str, IBleInterface.IBleConnectionCallback iBleConnectionCallback) {
        this.bleStrategy.connect(str, iBleConnectionCallback);
    }

    @Override // com.aliyun.alink.business.devicecenter.channel.ble.IBleInterface
    public void deinit() {
        if (this.f10229c.compareAndSet(true, false)) {
            b(this.f10227a);
        }
        this.bleStrategy.deinit();
    }

    @Override // com.aliyun.alink.business.devicecenter.channel.ble.IBleInterface
    public void disconnect(String str, IBleInterface.IBleConnectionCallback iBleConnectionCallback) {
        this.bleStrategy.disconnect(str, iBleConnectionCallback);
    }

    public int getBluetoothState() {
        return this.f10228b;
    }

    @Override // com.aliyun.alink.business.devicecenter.channel.ble.IBleInterface
    public void getDeviceName(IBleInterface.IBleChannelDevice iBleChannelDevice, IBleInterface.IBleDeviceInfoCallback iBleDeviceInfoCallback) {
        this.bleStrategy.getDeviceName(iBleChannelDevice, iBleDeviceInfoCallback);
    }

    @Override // com.aliyun.alink.business.devicecenter.channel.ble.IBleInterface
    public String getType() {
        return this.bleStrategy.getType();
    }

    @Override // com.aliyun.alink.business.devicecenter.channel.ble.IBleInterface
    public void init(Context context) {
        if (context == null) {
            return;
        }
        this.bleStrategy.init(context);
        a(context);
    }

    @Override // com.aliyun.alink.business.devicecenter.channel.ble.IBleInterface
    public boolean isSupport() {
        return this.bleStrategy.isSupport();
    }

    @Override // com.aliyun.alink.business.devicecenter.channel.ble.IBleInterface
    public boolean needGetDeviceName(IBleInterface.IBleChannelDevice iBleChannelDevice) {
        return this.bleStrategy.needGetDeviceName(iBleChannelDevice);
    }

    @Override // com.aliyun.alink.business.devicecenter.channel.ble.IBleInterface
    public void registerOnReceivedListener(IBleInterface.IBleChannelDevice iBleChannelDevice, IBleInterface.IBleReceiverCallback iBleReceiverCallback) {
        this.bleStrategy.registerOnReceivedListener(iBleChannelDevice, iBleReceiverCallback);
    }

    @Override // com.aliyun.alink.business.devicecenter.channel.ble.IBleInterface
    public void sendMessage(IBleInterface.IBleChannelDevice iBleChannelDevice, int i2, byte[] bArr, IBleInterface.IBleActionCallback iBleActionCallback) {
        this.bleStrategy.sendMessage(iBleChannelDevice, i2, bArr, iBleActionCallback);
    }

    @Override // com.aliyun.alink.business.devicecenter.channel.ble.IBleInterface
    public void setConfigParams(DCConfigParams dCConfigParams) {
        this.bleStrategy.setConfigParams(dCConfigParams);
    }

    @Override // com.aliyun.alink.business.devicecenter.channel.ble.IBleInterface
    public boolean startScan(IBleInterface.IBleScanCallback iBleScanCallback) {
        return this.bleStrategy.startScan(iBleScanCallback);
    }

    @Override // com.aliyun.alink.business.devicecenter.channel.ble.IBleInterface
    public void stopScan(IBleInterface.IBleScanCallback iBleScanCallback) {
        this.bleStrategy.stopScan(iBleScanCallback);
        deinit();
    }

    @Override // com.aliyun.alink.business.devicecenter.channel.ble.IBleInterface
    public void unregisterOnReceivedListener(IBleInterface.IBleChannelDevice iBleChannelDevice, IBleInterface.IBleReceiverCallback iBleReceiverCallback) {
        this.bleStrategy.unregisterOnReceivedListener(iBleChannelDevice, iBleReceiverCallback);
    }

    public final void a(Context context) {
        if (context == null) {
            return;
        }
        this.f10229c.set(true);
        ALog.d("BleChannelClient", "registerBleStatusReceiver() called with: context = [" + context + "]");
        context.registerReceiver(this.f10230d, new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED"));
    }
}
