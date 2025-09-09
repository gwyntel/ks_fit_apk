package com.aliyun.alink.business.devicecenter.channel.http.top;

import android.text.TextUtils;
import android.util.Log;
import com.alibaba.ailabs.tg.basebiz.user.UserManager;
import com.aliyun.alink.business.devicecenter.base.DCErrorCode;
import com.aliyun.alink.business.devicecenter.channel.http.ApiRequestClient;
import com.aliyun.alink.business.devicecenter.channel.http.DCError;
import com.aliyun.alink.business.devicecenter.channel.http.IRequestCallback;
import com.aliyun.alink.business.devicecenter.channel.http.model.request.QrCodeActivateRequest;
import com.aliyun.alink.business.devicecenter.channel.http.mtop.data.BindIotDeviceMtopResponse;
import com.aliyun.alink.business.devicecenter.channel.http.mtop.data.BindIotDeviceResult;
import com.aliyun.alink.business.devicecenter.channel.http.mtop.request.RtosDeviceBindRequest;
import com.aliyun.alink.business.devicecenter.channel.http.services.IActivationRtosRequestService;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.util.Map;

/* loaded from: classes2.dex */
public class DefaultTopRtosBindRequestService implements IActivationRtosRequestService {

    /* renamed from: a, reason: collision with root package name */
    public final String f10348a = "DefaultTopRtosBind";

    /* renamed from: b, reason: collision with root package name */
    public ApiRequestClient f10349b = new ApiRequestClient(false);

    /* renamed from: c, reason: collision with root package name */
    public IRequestCallback f10350c = null;

    @Override // com.aliyun.alink.business.devicecenter.channel.http.services.IActivationRtosRequestService
    public void qrCodeActivate(QrCodeActivateRequest qrCodeActivateRequest, IRequestCallback<BindIotDeviceResult> iRequestCallback) {
        ALog.d("DefaultTopRtosBind", "qrCodeActivate: " + qrCodeActivateRequest + ";callback=" + iRequestCallback);
        Map<String, Object> extraInfo = qrCodeActivateRequest.getExtraInfo();
        if (extraInfo.containsKey(TmpConstant.KEY_CLIENT_ID) && extraInfo.containsKey("code") && extraInfo.containsKey("productKey") && extraInfo.containsKey("token") && extraInfo.containsKey("deviceName")) {
            sendRequest(extraInfo, iRequestCallback);
        } else {
            iRequestCallback.onFail(new DCError(String.valueOf(DCErrorCode.PF_PARAMS_ERROR), "clientId, code,token or productkey not exist"), null);
        }
    }

    public void sendRequest(Map<String, Object> map, final IRequestCallback<BindIotDeviceResult> iRequestCallback) {
        try {
            RtosDeviceBindRequest rtosDeviceBindRequest = new RtosDeviceBindRequest();
            rtosDeviceBindRequest.setAuthInfo(UserManager.getInstance().getAuthInfoStr());
            RtosDeviceBindRequest.Model model = new RtosDeviceBindRequest.Model();
            String str = (String) map.get("network");
            if (TextUtils.isEmpty(str)) {
                str = "wifi";
            }
            model.setNetworkType(str);
            model.setProductKey((String) map.get("productKey"));
            RtosDeviceBindRequest.Param param = new RtosDeviceBindRequest.Param();
            param.setCode((String) map.get("code"));
            param.setClientId((String) map.get(TmpConstant.KEY_CLIENT_ID));
            param.setDeviceName((String) map.get("deviceName"));
            param.setToken((String) map.get("token"));
            param.setProductKey((String) map.get("productKey"));
            model.setParams(param);
            rtosDeviceBindRequest.setDeviceBindRequest(model);
            this.f10349b.send(rtosDeviceBindRequest, BindIotDeviceMtopResponse.class, new IRequestCallback() { // from class: com.aliyun.alink.business.devicecenter.channel.http.top.DefaultTopRtosBindRequestService.1
                @Override // com.aliyun.alink.business.devicecenter.channel.http.IRequestCallback
                public void onFail(DCError dCError, Object obj) {
                    iRequestCallback.onFail(dCError, null);
                }

                @Override // com.aliyun.alink.business.devicecenter.channel.http.IRequestCallback
                public void onSuccess(Object obj) {
                    Log.d("DefaultTopRtosBind", "onSuccess: data=" + obj);
                    if (obj instanceof BindIotDeviceMtopResponse) {
                        BindIotDeviceMtopResponse bindIotDeviceMtopResponse = (BindIotDeviceMtopResponse) obj;
                        if (bindIotDeviceMtopResponse.m59getData() == null || !bindIotDeviceMtopResponse.m59getData().isSuccess()) {
                            return;
                        }
                        iRequestCallback.onSuccess(bindIotDeviceMtopResponse.m59getData().getModel());
                    }
                }
            });
        } catch (Throwable th) {
            ALog.i("DefaultTopRtosBind", "checkILopTgCloudToken exception=" + th);
            th.printStackTrace();
        }
    }
}
