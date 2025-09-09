package com.aliyun.alink.business.devicecenter.provision.core;

import android.text.TextUtils;
import com.aliyun.alink.business.devicecenter.api.add.DeviceInfo;
import com.aliyun.alink.business.devicecenter.api.add.ProvisionStatus;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.aliyun.alink.business.devicecenter.channel.ble.IBleInterface;
import com.aliyun.alink.business.devicecenter.channel.ble.TLV;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.provision.core.ble.BreezeConfigState;
import com.aliyun.alink.business.devicecenter.provision.core.ble.BreezeConfigStrategy;
import com.aliyun.alink.business.devicecenter.utils.StringUtils;
import java.nio.ByteBuffer;

/* renamed from: com.aliyun.alink.business.devicecenter.provision.core.l, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0449l implements IBleInterface.IBleReceiverCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ BreezeConfigStrategy f10578a;

    public C0449l(BreezeConfigStrategy breezeConfigStrategy) {
        this.f10578a = breezeConfigStrategy;
    }

    @Override // com.aliyun.alink.business.devicecenter.channel.ble.IBleInterface.IBleReceiverCallback
    public void onDataReceived(byte[] bArr) {
        ALog.d(BreezeConfigStrategy.TAG, "onMessage() called with: data = " + StringUtils.byteArray2String(bArr) + ", hash=" + hashCode() + ", breeHashCode=" + this.f10578a.hashCode());
        if (bArr != null) {
            try {
                if (bArr.length < 1) {
                    return;
                }
                synchronized (this.f10578a.lockHandleDeviceNotifyLock) {
                    try {
                    } catch (Exception e2) {
                        String str = BreezeConfigStrategy.TAG;
                        StringBuilder sb = new StringBuilder();
                        sb.append("receive dev runtime log from ble channel, but handle throw exception=");
                        sb.append(e2);
                        ALog.w(str, sb.toString());
                    } catch (Exception e3) {
                        String str2 = BreezeConfigStrategy.TAG;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("receive dev wifi frame from ble channel, but handle throw exception=");
                        sb2.append(e3);
                        ALog.w(str2, sb2.toString());
                    } finally {
                    }
                    for (TLV.Element element : TLV.parse(bArr)) {
                        if (element != null) {
                            byte b2 = element.type;
                            if (b2 == 1) {
                                if (element.length < 1 || element.value[0] != 2) {
                                    byte b3 = element.value[0];
                                    if (b3 == 1) {
                                        ALog.i(BreezeConfigStrategy.TAG, "onMessage connect ap success.");
                                        this.f10578a.comboDeviceProvisionState = 1;
                                        this.f10578a.breezeConfigState = BreezeConfigState.BLE_SUCCESS;
                                        this.f10578a.provisionStatusCallback(ProvisionStatus.BLE_DEVICE_CONNECTED_AP);
                                        if (this.f10578a.deviceInfoNotifyListener != null && this.f10578a.mConfigParams != null && !TextUtils.isEmpty(this.f10578a.mConfigParams.productKey) && !TextUtils.isEmpty(this.f10578a.mConfigParams.deviceName)) {
                                            if (!AlinkConstants.DEVICE_TYPE_COMBO_SUBTYPE_3.equals(this.f10578a.mConfigParams.devType)) {
                                                ALog.i(BreezeConfigStrategy.TAG, "onMessage device connect ap success from breeze(subType!=3) channel-connect ap, wait for wifi connect ap or token or token check.");
                                            } else {
                                                if (this.f10578a.mConfigParams.isInSide) {
                                                    return;
                                                }
                                                ALog.i(BreezeConfigStrategy.TAG, "onMessage device connect ap success from breeze(subType=3) channel-connect ap.");
                                                DeviceInfo deviceInfo = new DeviceInfo();
                                                deviceInfo.productKey = this.f10578a.mConfigParams.productKey;
                                                deviceInfo.deviceName = this.f10578a.mConfigParams.deviceName;
                                                this.f10578a.deviceInfoNotifyListener.onDeviceFound(deviceInfo);
                                            }
                                            return;
                                        }
                                    } else if (b3 == 3) {
                                        String str3 = BreezeConfigStrategy.TAG;
                                        StringBuilder sb3 = new StringBuilder();
                                        sb3.append("onMessage token report success. params=");
                                        sb3.append(this.f10578a.mConfigParams);
                                        sb3.append(", isIlop=");
                                        sb3.append(this.f10578a.isIlop());
                                        ALog.i(str3, sb3.toString());
                                        this.f10578a.comboDeviceProvisionState = 2;
                                        this.f10578a.provisionStatusCallback(ProvisionStatus.BLE_DEVICE_CONNECTED_CLOUD);
                                        if (this.f10578a.deviceInfoNotifyListener != null && this.f10578a.mConfigParams != null && !TextUtils.isEmpty(this.f10578a.mConfigParams.productKey) && !TextUtils.isEmpty(this.f10578a.mConfigParams.deviceName) && !TextUtils.isEmpty(this.f10578a.mConfigParams.bindToken) && this.f10578a.isIlop()) {
                                            if (this.f10578a.mConfigParams.isInSide) {
                                                return;
                                            }
                                            DeviceInfo deviceInfo2 = new DeviceInfo();
                                            deviceInfo2.productKey = this.f10578a.mConfigParams.productKey;
                                            deviceInfo2.deviceName = this.f10578a.mConfigParams.deviceName;
                                            deviceInfo2.token = this.f10578a.mConfigParams.bindToken;
                                            ALog.i(BreezeConfigStrategy.TAG, "onMessage provision success from breeze channel-report token.");
                                            this.f10578a.deviceInfoNotifyListener.onDeviceFound(deviceInfo2);
                                            return;
                                        }
                                    } else {
                                        continue;
                                    }
                                } else {
                                    ALog.w(BreezeConfigStrategy.TAG, "onMessage device connect ap or connect mqtt failed.");
                                    this.f10578a.comboDeviceProvisionState = -1;
                                }
                            } else if (b2 == 3) {
                                this.f10578a.comboDeviceProvisionState = -1;
                                if (element.length == 2) {
                                    BreezeConfigStrategy breezeConfigStrategy = this.f10578a;
                                    byte[] bArr2 = element.value;
                                    breezeConfigStrategy.subErrorCode = (bArr2[0] & 255) | ((bArr2[1] & 255) << 8);
                                    String str4 = BreezeConfigStrategy.TAG;
                                    StringBuilder sb4 = new StringBuilder();
                                    sb4.append("onMessage device error code: ");
                                    sb4.append(this.f10578a.subErrorCode);
                                    ALog.i(str4, sb4.toString());
                                }
                            } else if (b2 == 4) {
                                if (element.length == 2) {
                                    BreezeConfigStrategy breezeConfigStrategy2 = this.f10578a;
                                    byte[] bArr3 = element.value;
                                    breezeConfigStrategy2.devSubErrorCodeFromBleReceived = (bArr3[0] & 255) | ((bArr3[1] & 255) << 8);
                                    String str5 = BreezeConfigStrategy.TAG;
                                    StringBuilder sb5 = new StringBuilder();
                                    sb5.append("onMessage device sub error code: ");
                                    sb5.append(this.f10578a.devSubErrorCodeFromBleReceived);
                                    ALog.i(str5, sb5.toString());
                                }
                            } else if (b2 == 7) {
                                this.f10578a.devInfoFromBleReceived = new String(element.value, "UTF-8");
                                String str6 = BreezeConfigStrategy.TAG;
                                StringBuilder sb6 = new StringBuilder();
                                sb6.append("receive dev info from ble channel, info=");
                                sb6.append(this.f10578a.devInfoFromBleReceived);
                                ALog.i(str6, sb6.toString());
                            } else if (b2 == 6) {
                                String str7 = BreezeConfigStrategy.TAG;
                                StringBuilder sb7 = new StringBuilder();
                                sb7.append("receive dev wifi frame from ble channel len=");
                                sb7.append((int) element.length);
                                ALog.i(str7, sb7.toString());
                                if (this.f10578a.hasAllocateWiFiByteBuffer.compareAndSet(false, true) && this.f10578a.devWiFiMFromFromBleReceivedByteBuffer == null) {
                                    this.f10578a.devWiFiMFromFromBleReceivedByteBuffer = ByteBuffer.allocate(5120);
                                }
                                if (this.f10578a.devWiFiMFromFromBleReceivedByteBuffer != null) {
                                    this.f10578a.devWiFiMFromFromBleReceivedByteBuffer.put(element.value);
                                }
                            } else if (b2 == 8) {
                                String str8 = BreezeConfigStrategy.TAG;
                                StringBuilder sb8 = new StringBuilder();
                                sb8.append("receive dev runtime log frame from ble channel len=");
                                sb8.append((int) element.length);
                                ALog.i(str8, sb8.toString());
                                String str9 = BreezeConfigStrategy.TAG;
                                StringBuilder sb9 = new StringBuilder();
                                sb9.append("log=");
                                sb9.append(new String(element.value, "UTF-8"));
                                ALog.d(str9, sb9.toString());
                                ALog.llogForExternal((byte) 5, AlinkConstants.EXTERNAL_LOG_TAG, element.value);
                            }
                        }
                    }
                    String str10 = BreezeConfigStrategy.TAG;
                    StringBuilder sb10 = new StringBuilder();
                    sb10.append("onMessage subErrorCode=");
                    sb10.append(this.f10578a.subErrorCode);
                    ALog.i(str10, sb10.toString());
                    if (this.f10578a.subErrorCode < 50404) {
                        if (this.f10578a.subErrorCode != 0) {
                            ALog.i(BreezeConfigStrategy.TAG, "onMessage device provision fail, device connect cloud failed, provisionFail.");
                            BreezeConfigStrategy breezeConfigStrategy3 = this.f10578a;
                            breezeConfigStrategy3.provisionFailFromBleNotify(breezeConfigStrategy3.subErrorCode, "device provision fail.");
                        }
                        return;
                    }
                    if (this.f10578a.comboDeviceProvisionState == -1) {
                        ALog.i(BreezeConfigStrategy.TAG, "onMessage device connect provision fail, wait for device to retry until timeout.");
                        return;
                    }
                    if (this.f10578a.comboDeviceProvisionState == 1) {
                        ALog.i(BreezeConfigStrategy.TAG, "onMessage device connect ap success, device connect cloud failed, wait until timeout.");
                    } else if (this.f10578a.comboDeviceProvisionState == 2) {
                        ALog.i(BreezeConfigStrategy.TAG, "onMessage device connect ap success, reportToken success, wait until loop cloud check.");
                    } else {
                        ALog.i(BreezeConfigStrategy.TAG, "onMessage device unexpected state returned, device connect cloud failed, wait until timeout.");
                    }
                }
            } catch (Exception e4) {
                e4.printStackTrace();
                ALog.w(BreezeConfigStrategy.TAG, "onMessage exception=" + e4);
            }
        }
    }
}
