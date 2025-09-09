package com.aliyun.alink.business.devicecenter.provision.core;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.alink.business.devicecenter.api.add.DeviceBindResultInfo;
import com.aliyun.alink.business.devicecenter.api.add.DeviceInfo;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.aliyun.alink.business.devicecenter.base.DCErrorCode;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.provision.core.mesh.GatewayMeshStrategy;
import com.aliyun.alink.business.devicecenter.utils.StringUtils;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTResponse;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequest;

/* loaded from: classes2.dex */
public class T implements IoTCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ U f10545a;

    public T(U u2) {
        this.f10545a = u2;
    }

    @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
    public void onFailure(IoTRequest ioTRequest, Exception exc) {
    }

    @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
    public void onResponse(IoTRequest ioTRequest, IoTResponse ioTResponse) {
        if (this.f10545a.f10547b.provisionHasStopped.get()) {
            ALog.d(GatewayMeshStrategy.TAG, "provision has stopped, ignore check result.");
            return;
        }
        if (ioTResponse != null) {
            try {
                if (ioTResponse.getCode() != 200 || ioTResponse.getData() == null) {
                    return;
                }
                JSONObject object = JSON.parseObject(ioTResponse.getData().toString());
                boolean zBooleanValue = object.getBoolean("success").booleanValue();
                String string = object.getString("state");
                String string2 = object.getString("gatewayIotId");
                JSONObject jSONObject = object.getJSONObject(AlinkConstants.KEY_DEVICE_INFO);
                String string3 = jSONObject.getString("deviceId");
                String string4 = jSONObject.getString("deviceIotId");
                String string5 = jSONObject.getString("productKey");
                String string6 = jSONObject.getString("deviceName");
                String string7 = object.getString(AlinkConstants.KEY_PAGE_ROUTER_URL);
                String string8 = object.getString("code");
                int intFromString = StringUtils.getIntFromString(string8);
                String string9 = object.getString(AlinkConstants.KEY_LOCALIZED_MSG);
                String str = GatewayMeshStrategy.TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("mesh device provisioning state=");
                sb.append(string);
                sb.append(", isSuccess=");
                sb.append(zBooleanValue);
                ALog.d(str, sb.toString());
                this.f10545a.f10547b.cloudMeshProvisionState = string;
                if (zBooleanValue && "FINISH".equals(string)) {
                    if (this.f10545a.f10547b.mConfigParams != null && !this.f10545a.f10547b.provisionHasStopped.get()) {
                        if (!TextUtils.isEmpty(string2) && !TextUtils.isEmpty(string3)) {
                            if (string2.equals(this.f10545a.f10547b.mConfigParams.regIoTId) && string3.equals(this.f10545a.f10547b.mConfigParams.deviceId)) {
                                if (!TextUtils.isEmpty(string5)) {
                                    this.f10545a.f10547b.mConfigParams.productKey = string5;
                                }
                                if (!TextUtils.isEmpty(string6)) {
                                    this.f10545a.f10547b.mConfigParams.deviceName = string6;
                                }
                                if (this.f10545a.f10547b.mNotifyListner != null) {
                                    DeviceInfo deviceInfo = new DeviceInfo();
                                    deviceInfo.deviceId = string3;
                                    deviceInfo.regIotId = string2;
                                    deviceInfo.productKey = this.f10545a.f10547b.mConfigParams.productKey;
                                    deviceInfo.deviceName = this.f10545a.f10547b.mConfigParams.deviceName;
                                    DeviceBindResultInfo deviceBindResultInfo = new DeviceBindResultInfo();
                                    deviceInfo.bindResultInfo = deviceBindResultInfo;
                                    deviceBindResultInfo.productKey = this.f10545a.f10547b.mConfigParams.productKey;
                                    deviceInfo.bindResultInfo.deviceName = this.f10545a.f10547b.mConfigParams.deviceName;
                                    DeviceBindResultInfo deviceBindResultInfo2 = deviceInfo.bindResultInfo;
                                    deviceBindResultInfo2.iotId = string4;
                                    deviceBindResultInfo2.bindResult = 1;
                                    deviceBindResultInfo2.pageRouterUrl = string7;
                                    deviceBindResultInfo2.iotId = string4;
                                    deviceBindResultInfo2.errorCode = string8;
                                    deviceBindResultInfo2.localizedMsg = string9;
                                    this.f10545a.f10547b.mNotifyListner.onDeviceFound(deviceInfo);
                                    return;
                                }
                            }
                            this.f10545a.f10547b.provisionErrorInfo = new DCErrorCode(DCErrorCode.SERVER_ERROR_MSG, DCErrorCode.PF_SERVER_FAIL).setMsg("mesh/gateway/discovery/device/query gatewayIotId or iotId not equal to required.").setSubcode(DCErrorCode.SUBCODE_SRE_GET_MESH_PROVISION_RESULT_IOTID_NOT_EQUAL);
                            this.f10545a.f10547b.provisionResultCallback(null);
                            return;
                        }
                        this.f10545a.f10547b.provisionErrorInfo = new DCErrorCode(DCErrorCode.SERVER_ERROR_MSG, DCErrorCode.PF_SERVER_FAIL).setMsg("mesh/gateway/discovery/device/query gatewayIotId or deviceId is null").setSubcode(DCErrorCode.SUBCODE_SRE_GET_MESH_PROVISION_RESULT_IOTID_EMPTY);
                        this.f10545a.f10547b.provisionResultCallback(null);
                        return;
                    }
                    ALog.d(GatewayMeshStrategy.TAG, "provision has stopped, return.");
                    return;
                }
                if (zBooleanValue) {
                    return;
                }
                GatewayMeshStrategy gatewayMeshStrategy = this.f10545a.f10547b;
                DCErrorCode dCErrorCode = new DCErrorCode(DCErrorCode.SERVER_ERROR_MSG, DCErrorCode.PF_SERVER_FAIL);
                StringBuilder sb2 = new StringBuilder();
                sb2.append("mesh/gateway/discovery/device/query provision failed state=");
                sb2.append(string);
                sb2.append(", msg=");
                sb2.append(string9);
                DCErrorCode msg = dCErrorCode.setMsg(sb2.toString());
                if (intFromString == 0) {
                    intFromString = DCErrorCode.SUBCODE_SRE_GET_MESH_PROVISION_RESULT_BIZ_FAIL;
                }
                gatewayMeshStrategy.provisionErrorInfo = msg.setSubcode(intFromString);
                this.f10545a.f10547b.provisionResultCallback(null);
            } catch (Exception e2) {
                ALog.w(GatewayMeshStrategy.TAG, "getMeshProvisionResult parse logic exception=" + e2);
            }
        }
    }
}
