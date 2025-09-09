package com.aliyun.alink.business.devicecenter.provision.core;

import android.text.TextUtils;
import android.util.Log;
import com.alibaba.ailabs.tg.utils.LogUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.alink.business.devicecenter.api.add.DeviceBindResultInfo;
import com.aliyun.alink.business.devicecenter.api.add.DeviceInfo;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.aliyun.alink.business.devicecenter.base.DCErrorCode;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.provision.core.mesh.ConcurrentGateMeshStrategy;
import com.aliyun.alink.business.devicecenter.utils.StringUtils;
import com.aliyun.alink.linksdk.connectsdk.ApiCallBack;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes2.dex */
public class N extends ApiCallBack<JSONArray> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ O f10540a;

    public N(O o2) {
        this.f10540a = o2;
    }

    @Override // com.aliyun.alink.linksdk.connectsdk.BaseCallBack
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(JSONArray jSONArray) {
        int i2;
        boolean zBooleanValue;
        String string;
        String string2;
        String string3;
        String string4;
        String string5;
        String string6;
        String string7;
        String string8;
        int intFromString;
        String string9;
        String str;
        if (this.f10540a.f10541a.provisionHasStopped.get()) {
            ALog.d(ConcurrentGateMeshStrategy.TAG, "provision has stopped, ignore check result.");
            return;
        }
        ArrayList<String> arrayList = new ArrayList();
        int i3 = 0;
        while (i3 < jSONArray.size()) {
            try {
                JSONObject jSONObject = jSONArray.getJSONObject(i3);
                zBooleanValue = jSONObject.getBoolean("success").booleanValue();
                string = jSONObject.getString("state");
                string2 = jSONObject.getString("gatewayIotId");
                JSONObject jSONObject2 = jSONObject.getJSONObject(AlinkConstants.KEY_DEVICE_INFO);
                string3 = jSONObject2.getString("deviceId");
                string4 = jSONObject2.getString("deviceIotId");
                string5 = jSONObject2.getString(AlinkConstants.KEY_DEVICE_PRODUCT_KEY_NAME);
                string6 = jSONObject2.getString("deviceName");
                string7 = jSONObject.getString(AlinkConstants.KEY_PAGE_ROUTER_URL);
                string8 = jSONObject.getString("code");
                intFromString = StringUtils.getIntFromString(string8);
                string9 = jSONObject.getString(AlinkConstants.KEY_LOCALIZED_MSG);
                str = ConcurrentGateMeshStrategy.TAG;
                i2 = i3;
            } catch (Exception e2) {
                e = e2;
                i2 = i3;
            }
            try {
                StringBuilder sb = new StringBuilder();
                sb.append("query mesh device provisioning ");
                sb.append(string3);
                sb.append(" state=");
                sb.append(string);
                sb.append(", isSuccess=");
                sb.append(zBooleanValue);
                ALog.d(str, sb.toString());
                if ("FINISH".equals(string)) {
                    DCErrorCode subcode = null;
                    if (!zBooleanValue) {
                        Log.d(ConcurrentGateMeshStrategy.TAG, "onResponse: provisioning false");
                        DeviceInfo deviceInfo = this.f10540a.f10541a.mUnprovisionedGateMeshDevice.getDeviceInfo(string3);
                        arrayList.add(deviceInfo.mac);
                        if (!zBooleanValue) {
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
                            subcode = msg.setSubcode(intFromString);
                        }
                        this.f10540a.f10541a.provisionResultCallback(deviceInfo, subcode);
                    } else if (!TextUtils.isEmpty(string2) && !TextUtils.isEmpty(string3) && !TextUtils.isEmpty(string6)) {
                        DeviceInfo deviceInfo2 = this.f10540a.f10541a.mUnprovisionedGateMeshDevice.getDeviceInfo(string3);
                        if (!TextUtils.isEmpty(string3)) {
                            deviceInfo2.deviceId = string3;
                        }
                        if (!TextUtils.isEmpty(string2)) {
                            deviceInfo2.regIotId = string2;
                        }
                        if (!TextUtils.isEmpty(string5)) {
                            deviceInfo2.productKey = string5;
                        }
                        if (!TextUtils.isEmpty(string6)) {
                            deviceInfo2.deviceName = string6;
                        }
                        if (!TextUtils.isEmpty(string4)) {
                            deviceInfo2.iotId = string4;
                        }
                        DeviceBindResultInfo deviceBindResultInfo = new DeviceBindResultInfo();
                        deviceInfo2.bindResultInfo = deviceBindResultInfo;
                        deviceBindResultInfo.productKey = string5;
                        deviceBindResultInfo.deviceName = string6;
                        deviceBindResultInfo.bindResult = 1;
                        deviceBindResultInfo.pageRouterUrl = string7;
                        deviceBindResultInfo.iotId = string4;
                        deviceBindResultInfo.errorCode = string8;
                        deviceBindResultInfo.localizedMsg = string9;
                        String str2 = ConcurrentGateMeshStrategy.TAG;
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append("query mesh device provisioning deviceInfo.iotId=");
                        sb3.append(deviceInfo2.iotId);
                        sb3.append("deviceInfo.deviceName=");
                        sb3.append(deviceInfo2.deviceName);
                        sb3.append(" state=");
                        sb3.append(string);
                        sb3.append(", isSuccess=");
                        sb3.append(true);
                        ALog.d(str2, sb3.toString());
                        arrayList.add(deviceInfo2.mac);
                        this.f10540a.f10541a.provisionResultCallback(deviceInfo2, null);
                    }
                }
            } catch (Exception e3) {
                e = e3;
                LogUtils.w(ConcurrentGateMeshStrategy.TAG, "query one device parse exception=" + e);
                i3 = i2 + 1;
            }
            i3 = i2 + 1;
        }
        try {
            if (arrayList.size() > 0) {
                Iterator it = this.f10540a.f10541a.mTaskIds.iterator();
                while (it.hasNext()) {
                    for (String str3 : arrayList) {
                        if (((String) it.next()).contains(str3)) {
                            String str4 = ConcurrentGateMeshStrategy.TAG;
                            StringBuilder sb4 = new StringBuilder();
                            sb4.append("onResponse: 删除");
                            sb4.append(str3);
                            sb4.append("查询任务");
                            Log.d(str4, sb4.toString());
                            it.remove();
                        }
                    }
                }
            }
        } catch (Exception e4) {
            Log.d(ConcurrentGateMeshStrategy.TAG, "onResponse: delete task Exception=" + e4);
        }
    }

    @Override // com.aliyun.alink.linksdk.connectsdk.BaseCallBack
    public void onFail(int i2, String str) {
    }
}
