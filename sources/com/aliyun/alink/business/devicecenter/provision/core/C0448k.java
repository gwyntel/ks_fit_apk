package com.aliyun.alink.business.devicecenter.provision.core;

import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.aliyun.alink.business.devicecenter.channel.ble.IBleInterface;
import com.aliyun.alink.business.devicecenter.channel.ble.TLV;
import com.aliyun.alink.business.devicecenter.config.model.DeviceReportTokenType;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.log.PerformanceLog;
import com.aliyun.alink.business.devicecenter.provision.core.ble.BreezeConfigState;
import com.aliyun.alink.business.devicecenter.provision.core.ble.BreezeConfigStrategy;
import com.aliyun.alink.business.devicecenter.track.DCUserTrack;
import com.aliyun.alink.business.devicecenter.utils.StringUtils;
import com.umeng.message.common.inter.ITagManager;

/* renamed from: com.aliyun.alink.business.devicecenter.provision.core.k, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0448k implements IBleInterface.IBleActionCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ BreezeConfigStrategy f10577a;

    public C0448k(BreezeConfigStrategy breezeConfigStrategy) {
        this.f10577a = breezeConfigStrategy;
    }

    @Override // com.aliyun.alink.business.devicecenter.channel.ble.IBleInterface.IBleActionCallback
    public void onResponse(int i2, byte[] bArr) {
        ALog.d(BreezeConfigStrategy.TAG, "onResponse() called with: code = [" + i2 + "], data = " + StringUtils.byteArray2String(bArr));
        try {
            if (this.f10577a.waitForResult.get()) {
                this.f10577a.notifyReceiveSwitchApAck();
                DCUserTrack.addTrackData(AlinkConstants.KEY_END_TIME_SWITCH_AP, String.valueOf(System.currentTimeMillis()));
                if (i2 != 0) {
                    PerformanceLog.trace(BreezeConfigStrategy.TAG, "broadcastResult", PerformanceLog.getJsonObject("result", ITagManager.FAIL));
                    String str = BreezeConfigStrategy.TAG;
                    StringBuilder sb = new StringBuilder();
                    sb.append("handleBreBiz response error=");
                    sb.append(i2);
                    sb.append(",data=");
                    sb.append(StringUtils.byteArray2String(bArr));
                    ALog.w(str, sb.toString());
                    BreezeConfigStrategy breezeConfigStrategy = this.f10577a;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("ble response error: ");
                    sb2.append(i2);
                    sb2.append(",data=");
                    sb2.append(StringUtils.byteArray2String(bArr));
                    breezeConfigStrategy.breezeResponseInfo = sb2.toString();
                    return;
                }
                String str2 = null;
                boolean z2 = false;
                boolean z3 = false;
                for (TLV.Element element : TLV.parse(bArr)) {
                    if (element.type == 6) {
                        for (TLV.Element element2 : TLV.parse(element.value)) {
                            byte b2 = element2.type;
                            if (b2 == 1 && element2.value[0] == 1) {
                                ALog.i(BreezeConfigStrategy.TAG, "onResponse success.");
                                this.f10577a.breezeResponseInfo = "ble response switchap success";
                                z3 = false;
                            } else if (b2 == 1 && element2.value[0] == 2) {
                                ALog.w(BreezeConfigStrategy.TAG, "onResponse response fail.");
                                BreezeConfigStrategy breezeConfigStrategy2 = this.f10577a;
                                StringBuilder sb3 = new StringBuilder();
                                sb3.append("ble response switchap failed. creason: ");
                                sb3.append(str2);
                                breezeConfigStrategy2.breezeResponseInfo = sb3.toString();
                                z3 = true;
                            } else if (b2 == 2) {
                                str2 = new String(element2.value, "UTF-8");
                                String str3 = BreezeConfigStrategy.TAG;
                                StringBuilder sb4 = new StringBuilder();
                                sb4.append("onResponse message=");
                                sb4.append(str2);
                                ALog.i(str3, sb4.toString());
                                BreezeConfigStrategy breezeConfigStrategy3 = this.f10577a;
                                StringBuilder sb5 = new StringBuilder();
                                sb5.append("ble response switchap failed. reason: ");
                                sb5.append(str2);
                                breezeConfigStrategy3.breezeResponseInfo = sb5.toString();
                            } else if (b2 == 3) {
                                byte b3 = element2.value[0];
                                if (b3 == 1) {
                                    this.f10577a.deviceReportTokenType = DeviceReportTokenType.CLOUD_TOKEN;
                                    BreezeConfigStrategy breezeConfigStrategy4 = this.f10577a;
                                    breezeConfigStrategy4.updateBackupCheckType(breezeConfigStrategy4.deviceReportTokenType, this.f10577a.isIlop());
                                } else {
                                    if (b3 == 0) {
                                        this.f10577a.deviceReportTokenType = DeviceReportTokenType.APP_TOKEN;
                                        BreezeConfigStrategy breezeConfigStrategy5 = this.f10577a;
                                        breezeConfigStrategy5.updateBackupCheckType(breezeConfigStrategy5.deviceReportTokenType, this.f10577a.isIlop());
                                    }
                                    String str4 = BreezeConfigStrategy.TAG;
                                    StringBuilder sb6 = new StringBuilder();
                                    sb6.append("onResponse tokenType=");
                                    sb6.append((int) b3);
                                    ALog.i(str4, sb6.toString());
                                }
                                z2 = true;
                                String str42 = BreezeConfigStrategy.TAG;
                                StringBuilder sb62 = new StringBuilder();
                                sb62.append("onResponse tokenType=");
                                sb62.append((int) b3);
                                ALog.i(str42, sb62.toString());
                            }
                        }
                    }
                }
                if (!z2) {
                    if (this.f10577a.sendAppToken2DeviceAB.get()) {
                        this.f10577a.deviceReportTokenType = DeviceReportTokenType.APP_TOKEN;
                        BreezeConfigStrategy breezeConfigStrategy6 = this.f10577a;
                        breezeConfigStrategy6.updateBackupCheckType(breezeConfigStrategy6.deviceReportTokenType, this.f10577a.isIlop());
                    } else {
                        this.f10577a.deviceReportTokenType = DeviceReportTokenType.UNKNOWN;
                        BreezeConfigStrategy breezeConfigStrategy7 = this.f10577a;
                        breezeConfigStrategy7.updateBackupCheckType(breezeConfigStrategy7.deviceReportTokenType, this.f10577a.isIlop());
                    }
                }
                if (z3) {
                    PerformanceLog.trace(BreezeConfigStrategy.TAG, "broadcastResult", PerformanceLog.getJsonObject("result", ITagManager.FAIL));
                    return;
                }
                PerformanceLog.trace(BreezeConfigStrategy.TAG, "broadcastResult", PerformanceLog.getJsonObject("result", "success"));
                this.f10577a.updateProvisionState(BreezeConfigState.BLE_SUCCESS);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            ALog.w(BreezeConfigStrategy.TAG, "onResponse exception=" + e2);
            this.f10577a.breezeResponseInfo = "parse ble response exception " + e2;
        }
    }
}
