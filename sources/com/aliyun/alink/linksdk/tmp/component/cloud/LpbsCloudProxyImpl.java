package com.aliyun.alink.linksdk.tmp.component.cloud;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.aliyun.alink.linksdk.alcs.lpbs.component.cloud.ILpbsCloudProxy;
import com.aliyun.alink.linksdk.alcs.lpbs.component.cloud.ILpbsCloudProxyListener;
import com.aliyun.alink.linksdk.alcs.lpbs.data.cloud.TgMeshConvertParams;
import com.aliyun.alink.linksdk.tmp.device.request.IGateWayRequestListener;
import com.aliyun.alink.linksdk.tmp.device.request.tgmesh.ConvertTgMeshProtocolRequest;
import com.aliyun.alink.linksdk.tools.AError;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class LpbsCloudProxyImpl implements ILpbsCloudProxy {
    public static final String TAG = "[Tmp]LpbsCloudProxyImpl";

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.component.cloud.ILpbsCloudProxy
    public void requestCloud(String str, Object obj, ILpbsCloudProxyListener iLpbsCloudProxyListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (!TextUtils.isEmpty(str)) {
            if (ILpbsCloudProxy.TOPIC_CONVERT_TGMESH_PARAMS.equalsIgnoreCase(str)) {
                requestTgMeshConvert(str, obj, iLpbsCloudProxyListener);
            }
        } else {
            ALog.e(TAG, "requestCloud topic empty");
            if (iLpbsCloudProxyListener != null) {
                iLpbsCloudProxyListener.onComplete(0, null);
            }
        }
    }

    public void requestTgMeshConvert(String str, Object obj, final ILpbsCloudProxyListener iLpbsCloudProxyListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (obj != null && (obj instanceof TgMeshConvertParams)) {
            TgMeshConvertParams tgMeshConvertParams = (TgMeshConvertParams) obj;
            ConvertTgMeshProtocolRequest convertTgMeshProtocolRequest = new ConvertTgMeshProtocolRequest();
            convertTgMeshProtocolRequest.deviceId = tgMeshConvertParams.iotId;
            try {
                convertTgMeshProtocolRequest.params = JSON.toJSONString(tgMeshConvertParams);
            } catch (Exception e2) {
                ALog.e(TAG, "requestTgMeshConvert toJSONString e:" + e2.toString());
            }
            convertTgMeshProtocolRequest.sendRequest(convertTgMeshProtocolRequest, new IGateWayRequestListener<ConvertTgMeshProtocolRequest, ConvertTgMeshProtocolRequest.ConvertTgMeshProtocolResponse>() { // from class: com.aliyun.alink.linksdk.tmp.component.cloud.LpbsCloudProxyImpl.1
                @Override // com.aliyun.alink.linksdk.tmp.device.request.IGateWayRequestListener
                public void onFail(ConvertTgMeshProtocolRequest convertTgMeshProtocolRequest2, AError aError) {
                    ILpbsCloudProxyListener iLpbsCloudProxyListener2 = iLpbsCloudProxyListener;
                    if (iLpbsCloudProxyListener2 != null) {
                        iLpbsCloudProxyListener2.onComplete(1, null);
                    }
                }

                @Override // com.aliyun.alink.linksdk.tmp.device.request.IGateWayRequestListener
                public void onSuccess(ConvertTgMeshProtocolRequest convertTgMeshProtocolRequest2, ConvertTgMeshProtocolRequest.ConvertTgMeshProtocolResponse convertTgMeshProtocolResponse) {
                    ILpbsCloudProxyListener iLpbsCloudProxyListener2 = iLpbsCloudProxyListener;
                    if (iLpbsCloudProxyListener2 != null) {
                        iLpbsCloudProxyListener2.onComplete(0, convertTgMeshProtocolResponse.data);
                    }
                }
            });
            return;
        }
        ALog.e(TAG, "requestTgMeshConvert empty error topic :" + str + " params:" + obj + " listener:" + iLpbsCloudProxyListener);
        if (iLpbsCloudProxyListener != null) {
            iLpbsCloudProxyListener.onComplete(1, null);
        }
    }
}
