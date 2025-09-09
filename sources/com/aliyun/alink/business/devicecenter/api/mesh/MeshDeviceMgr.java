package com.aliyun.alink.business.devicecenter.api.mesh;

import android.content.Context;
import com.alibaba.ailabs.iot.mesh.AuthInfoListener;
import com.alibaba.ailabs.iot.mesh.TgMeshManager;
import com.alibaba.fastjson.JSON;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.model.MeshNodeInfo;
import com.aliyun.iot.aep.sdk.apiclient.IoTAPIClientFactory;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTResponse;
import com.aliyun.iot.aep.sdk.apiclient.emuns.Scheme;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequest;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequestBuilder;
import java.util.HashMap;

/* loaded from: classes2.dex */
public class MeshDeviceMgr {

    /* renamed from: a, reason: collision with root package name */
    public String f10148a = "MeshDeviceMgr";

    /* renamed from: b, reason: collision with root package name */
    public Context f10149b;

    private interface IApiCallback {
        void onFail(int i2, String str);

        void onSuccess(Object obj);
    }

    private static class SingletonHolder {

        /* renamed from: a, reason: collision with root package name */
        public static final MeshDeviceMgr f10154a = new MeshDeviceMgr();
    }

    public static MeshDeviceMgr getInstance() {
        return SingletonHolder.f10154a;
    }

    public void disConnect() {
        TgMeshManager.getInstance().disconnect();
    }

    public void init(Context context) {
        this.f10149b = context;
        if (TgMeshManager.getInstance().isInitialized()) {
            return;
        }
        ALog.d(this.f10148a, "TgMeshManager need init.");
        TgMeshManager.getInstance().init(context, new AuthInfoListener() { // from class: com.aliyun.alink.business.devicecenter.api.mesh.MeshDeviceMgr.1
            @Override // com.alibaba.ailabs.iot.mesh.AuthInfoListener
            public String getAuthInfo() {
                return "";
            }
        });
    }

    @Deprecated
    public void resetMeshNode(String str) {
        a(str, new IApiCallback() { // from class: com.aliyun.alink.business.devicecenter.api.mesh.MeshDeviceMgr.2
            @Override // com.aliyun.alink.business.devicecenter.api.mesh.MeshDeviceMgr.IApiCallback
            public void onFail(int i2, String str2) {
                ALog.e(MeshDeviceMgr.this.f10148a, "resetMeshNode fail code:" + i2 + ";msg:" + str2);
            }

            @Override // com.aliyun.alink.business.devicecenter.api.mesh.MeshDeviceMgr.IApiCallback
            public void onSuccess(Object obj) {
                if (obj instanceof MeshNodeInfo) {
                    MeshNodeInfo meshNodeInfo = (MeshNodeInfo) obj;
                    TgMeshManager.getInstance().removeNodeAfterUnbind(meshNodeInfo.getDeviceKey(), meshNodeInfo.getNetKeyIndex(), meshNodeInfo.getPrimaryUnicastAddress());
                }
            }
        });
    }

    public void unInit() {
        TgMeshManager.getInstance().resetIoTMultiendInOneBridge();
        TgMeshManager.getInstance().stop(this.f10149b);
    }

    public final void a(String str, final IApiCallback iApiCallback) {
        HashMap map = new HashMap();
        map.put("iotId", str);
        IoTRequestBuilder authType = new IoTRequestBuilder().setApiVersion("1.0.0").setPath(AlinkConstants.HTTP_PATH_QUERY_NODE_INFO).setScheme(Scheme.HTTPS).setAuthType(AlinkConstants.KEY_IOT_AUTH);
        authType.setParams(map);
        new IoTAPIClientFactory().getClient().send(authType.build(), new IoTCallback() { // from class: com.aliyun.alink.business.devicecenter.api.mesh.MeshDeviceMgr.3
            @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
            public void onFailure(IoTRequest ioTRequest, Exception exc) {
                iApiCallback.onFail(-1, exc.getLocalizedMessage());
            }

            @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
            public void onResponse(IoTRequest ioTRequest, IoTResponse ioTResponse) {
                if (ioTResponse == null || ioTResponse.getCode() != 200) {
                    iApiCallback.onFail(ioTResponse == null ? -1 : ioTResponse.getCode(), ioTResponse == null ? "" : ioTResponse.getLocalizedMsg());
                    return;
                }
                if (ioTResponse.getData() == null) {
                    iApiCallback.onSuccess(null);
                    return;
                }
                Object data = ioTResponse.getData();
                try {
                    String string = ioTResponse.getData().toString();
                    String str2 = MeshDeviceMgr.this.f10148a;
                    StringBuilder sb = new StringBuilder();
                    sb.append("response data : ");
                    sb.append(string);
                    ALog.d(str2, sb.toString());
                    iApiCallback.onSuccess((MeshNodeInfo) JSON.parseObject(string, MeshNodeInfo.class));
                } catch (Exception unused) {
                }
                iApiCallback.onSuccess(data);
            }
        });
    }

    public void resetMeshNode(String str, int i2, int i3) {
        TgMeshManager.getInstance().removeNodeAfterUnbind(str, i2, i3);
    }
}
