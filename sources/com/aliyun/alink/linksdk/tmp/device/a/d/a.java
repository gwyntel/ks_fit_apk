package com.aliyun.alink.linksdk.tmp.device.a.d;

import android.text.TextUtils;
import com.aliyun.alink.linksdk.tmp.TmpSdk;
import com.aliyun.alink.linksdk.tmp.api.DeviceBasicData;
import com.aliyun.alink.linksdk.tmp.api.DeviceManager;
import com.aliyun.alink.linksdk.tmp.api.OutputParams;
import com.aliyun.alink.linksdk.tmp.component.cloud.ICloudProxyListener;
import com.aliyun.alink.linksdk.tmp.config.DefaultClientConfig;
import com.aliyun.alink.linksdk.tmp.config.DeviceConfig;
import com.aliyun.alink.linksdk.tmp.data.auth.AccessInfo;
import com.aliyun.alink.linksdk.tmp.device.panel.data.AccessInfoPayload;
import com.aliyun.alink.linksdk.tmp.device.panel.data.ProductInfoPayload;
import com.aliyun.alink.linksdk.tmp.device.request.GateWayRequest;
import com.aliyun.alink.linksdk.tmp.device.request.IGateWayRequestListener;
import com.aliyun.alink.linksdk.tmp.device.request.auth.GetComboAccessInfoRequest;
import com.aliyun.alink.linksdk.tmp.listener.IDevListener;
import com.aliyun.alink.linksdk.tmp.service.DevService;
import com.aliyun.alink.linksdk.tmp.storage.TmpStorage;
import com.aliyun.alink.linksdk.tmp.utils.ErrorCode;
import com.aliyun.alink.linksdk.tmp.utils.ErrorInfo;
import com.aliyun.alink.linksdk.tmp.utils.GsonUtils;
import com.aliyun.alink.linksdk.tools.AError;
import com.aliyun.alink.linksdk.tools.ALog;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.InvocationTargetException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.crypto.NoSuchPaddingException;

/* loaded from: classes2.dex */
public class a extends b {

    /* renamed from: n, reason: collision with root package name */
    public final int f11299n;

    /* renamed from: o, reason: collision with root package name */
    protected Runnable f11300o;

    public a(com.aliyun.alink.linksdk.tmp.device.a aVar, DeviceBasicData deviceBasicData, DeviceConfig deviceConfig, IDevListener iDevListener) {
        super(aVar, deviceBasicData, deviceConfig, iDevListener);
        this.f11299n = 40000;
        this.f11300o = new Runnable() { // from class: com.aliyun.alink.linksdk.tmp.device.a.d.a.1
            @Override // java.lang.Runnable
            public void run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                ALog.e("[Tmp]CreateConnectTask", "CreateClientConnectTask time out");
                a aVar2 = a.this;
                aVar2.onFail(((com.aliyun.alink.linksdk.tmp.device.a.d) aVar2).f11290e, new ErrorInfo(ErrorCode.ERROR_CODE_TIMEOUT, "timeout"));
                com.aliyun.alink.linksdk.tmp.connect.a.a(a.this.f11310q);
            }
        };
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.a.d.b, com.aliyun.alink.linksdk.tmp.listener.IDevListener
    public void onFail(Object obj, ErrorInfo errorInfo) {
        TmpSdk.mHandler.removeCallbacks(this.f11300o);
        super.onFail(obj, errorInfo);
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.a.d.b, com.aliyun.alink.linksdk.tmp.listener.IDevListener
    public void onSuccess(Object obj, OutputParams outputParams) {
        TmpSdk.mHandler.removeCallbacks(this.f11300o);
        super.onSuccess(obj, outputParams);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        DeviceConfig deviceConfig = this.f11298m;
        DefaultClientConfig defaultClientConfig = (DefaultClientConfig) deviceConfig;
        if (!TextUtils.isEmpty(deviceConfig.getBasicData().getIotId()) && (TextUtils.isEmpty(((DefaultClientConfig) this.f11298m).getAccessKey()) || TextUtils.isEmpty(((DefaultClientConfig) this.f11298m).getAccessToken()))) {
            d();
        } else if (TextUtils.isEmpty(this.f11298m.getBasicData().getIotId()) || !TextUtils.isEmpty(defaultClientConfig.mDateFormat)) {
            c();
        } else {
            b();
        }
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.a.d, com.aliyun.alink.linksdk.tmp.device.a.a
    public synchronized boolean a() {
        try {
            TmpSdk.mHandler.postDelayed(this.f11300o, 40000L);
            ALog.d("[Tmp]CreateConnectTask", "action mDeviceBasicData:" + this.f11295j + " mConfig:" + this.f11298m);
            if (DevService.isDeviceWifiAndBleCombo(this.f11295j.getSupportedNetType())) {
                final AccessInfo comboAccessInfo = TmpStorage.getInstance().getComboAccessInfo(this.f11298m.getBasicData().getDevId());
                if (comboAccessInfo != null) {
                    e();
                }
                GateWayRequest getComboAccessInfoRequest = new GetComboAccessInfoRequest(this.f11298m.getBasicData().getProductKey(), this.f11298m.getBasicData().getDeviceName(), null);
                getComboAccessInfoRequest.sendRequest(getComboAccessInfoRequest, new IGateWayRequestListener<GetComboAccessInfoRequest, GetComboAccessInfoRequest.GetComboAccessInfoResponse>() { // from class: com.aliyun.alink.linksdk.tmp.device.a.d.a.2
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // com.aliyun.alink.linksdk.tmp.device.request.IGateWayRequestListener
                    /* renamed from: a, reason: merged with bridge method [inline-methods] */
                    public void onSuccess(GetComboAccessInfoRequest getComboAccessInfoRequest2, GetComboAccessInfoRequest.GetComboAccessInfoResponse getComboAccessInfoResponse) throws IllegalAccessException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalArgumentException, InvocationTargetException {
                        T t2;
                        T t3;
                        if (getComboAccessInfoResponse != null && (t3 = getComboAccessInfoResponse.data) != 0 && ((GetComboAccessInfoRequest.GetComboAccessInfoData) t3).accessInfo != null) {
                            TmpStorage tmpStorage = TmpStorage.getInstance();
                            String devId = ((com.aliyun.alink.linksdk.tmp.device.a.d) a.this).f11298m.getBasicData().getDevId();
                            T t4 = getComboAccessInfoResponse.data;
                            tmpStorage.saveComboAccessInfo(devId, ((GetComboAccessInfoRequest.GetComboAccessInfoData) t4).accessInfo.accessKey, ((GetComboAccessInfoRequest.GetComboAccessInfoData) t4).accessInfo.accessToken);
                        }
                        if (getComboAccessInfoResponse != null && (t2 = getComboAccessInfoResponse.data) != 0 && ((GetComboAccessInfoRequest.GetComboAccessInfoData) t2).syncAccessInfo != null) {
                            TmpStorage tmpStorage2 = TmpStorage.getInstance();
                            String devId2 = ((com.aliyun.alink.linksdk.tmp.device.a.d) a.this).f11298m.getBasicData().getDevId();
                            T t5 = getComboAccessInfoResponse.data;
                            tmpStorage2.saveSyncAccessInfo(devId2, ((GetComboAccessInfoRequest.GetComboAccessInfoData) t5).syncAccessInfo.accessKey, ((GetComboAccessInfoRequest.GetComboAccessInfoData) t5).syncAccessInfo.accessToken, ((GetComboAccessInfoRequest.GetComboAccessInfoData) t5).syncAccessInfo.authCode);
                        }
                        if (comboAccessInfo == null) {
                            a.this.e();
                        }
                    }

                    @Override // com.aliyun.alink.linksdk.tmp.device.request.IGateWayRequestListener
                    /* renamed from: a, reason: merged with bridge method [inline-methods] */
                    public void onFail(GetComboAccessInfoRequest getComboAccessInfoRequest2, AError aError) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                        ALog.e("[Tmp]CreateConnectTask", "GetComboAccessInfoRequest onFail:" + aError);
                        if (comboAccessInfo == null) {
                            a.this.e();
                        }
                    }
                });
            } else {
                e();
            }
        } catch (Throwable th) {
            throw th;
        }
        return true;
    }

    protected void b() {
        final DefaultClientConfig defaultClientConfig = (DefaultClientConfig) this.f11298m;
        if (!TextUtils.isEmpty(defaultClientConfig.mDateFormat)) {
            c();
            return;
        }
        ProductInfoPayload.ProductInfo productInfo = TmpStorage.getInstance().getProductInfo(this.f11298m.getBasicData().getIotId());
        if (productInfo == null || TextUtils.isEmpty(productInfo.dataFormat)) {
            TmpSdk.getCloudProxy().queryProductInfo(this.f11298m.getBasicData().getIotId(), new ICloudProxyListener() { // from class: com.aliyun.alink.linksdk.tmp.device.a.d.a.3
                @Override // com.aliyun.alink.linksdk.tmp.component.cloud.ICloudProxyListener
                public void onFailure(String str, AError aError) {
                    a.this.c();
                }

                @Override // com.aliyun.alink.linksdk.tmp.component.cloud.ICloudProxyListener
                public void onResponse(String str, Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    ProductInfoPayload.ProductInfo productInfo2;
                    if (obj == null) {
                        ALog.e("[Tmp]CreateConnectTask", "queryProductInfo aResponse error null");
                        a.this.c();
                        return;
                    }
                    ProductInfoPayload productInfoPayload = (ProductInfoPayload) GsonUtils.fromJson(obj.toString(), new TypeToken<ProductInfoPayload>() { // from class: com.aliyun.alink.linksdk.tmp.device.a.d.a.3.1
                    }.getType());
                    if (productInfoPayload == null || (productInfo2 = productInfoPayload.data) == null || TextUtils.isEmpty(productInfo2.dataFormat)) {
                        ALog.e("[Tmp]CreateConnectTask", "queryProductInfo payload error ");
                        a.this.c();
                        return;
                    }
                    defaultClientConfig.mDateFormat = productInfoPayload.data.dataFormat;
                    TmpStorage.getInstance().saveProductInfo(((com.aliyun.alink.linksdk.tmp.device.a.d) a.this).f11298m.getBasicData().getIotId(), productInfoPayload.data);
                    ALog.d("[Tmp]CreateConnectTask", "queryProductInfo onResponse dataFormat:" + defaultClientConfig.mDateFormat + " payload:" + productInfoPayload);
                    a.this.c();
                }
            });
        } else {
            defaultClientConfig.mDateFormat = productInfo.dataFormat;
            c();
        }
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.a.d.b
    protected void c() {
        DefaultClientConfig defaultClientConfig = (DefaultClientConfig) this.f11298m;
        DeviceManager.getInstance().addDevIotId(defaultClientConfig.getDevId(), defaultClientConfig.getBasicData().getIotId());
        super.c();
    }

    protected void d() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        AccessInfo accessInfo;
        ALog.d("[Tmp]CreateConnectTask", "queryAccessInfo start");
        if (TextUtils.isEmpty(this.f11298m.getBasicData().getDevId())) {
            TmpStorage.getInstance().getDeviceInfo(this.f11298m.getBasicData().getIotId());
            accessInfo = null;
        } else {
            accessInfo = TmpStorage.getInstance().getAccessInfo(this.f11298m.getBasicData().getDevId());
            a(this.f11298m.getBasicData().getProductKey(), this.f11298m.getBasicData().getDeviceName());
        }
        if (accessInfo == null) {
            TmpSdk.getCloudProxy().queryAccessInfo(this.f11298m.getBasicData().getIotId(), new ICloudProxyListener() { // from class: com.aliyun.alink.linksdk.tmp.device.a.d.a.4
                @Override // com.aliyun.alink.linksdk.tmp.component.cloud.ICloudProxyListener
                public void onFailure(String str, AError aError) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    ALog.e("[Tmp]CreateConnectTask", "queryAccessInfo onResponse  error:" + aError);
                    AccessInfo accessInfo2 = TmpStorage.getInstance().getAccessInfo(((com.aliyun.alink.linksdk.tmp.device.a.d) a.this).f11298m.getBasicData().getDevId(), "local");
                    if (accessInfo2 != null && !TextUtils.isEmpty(accessInfo2.mAccessKey) && !TextUtils.isEmpty(accessInfo2.mAccessToken)) {
                        DefaultClientConfig defaultClientConfig = (DefaultClientConfig) ((com.aliyun.alink.linksdk.tmp.device.a.d) a.this).f11298m;
                        defaultClientConfig.setAccessKey(accessInfo2.mAccessKey);
                        defaultClientConfig.setAccessToken(accessInfo2.mAccessToken);
                    }
                    a.this.b();
                }

                @Override // com.aliyun.alink.linksdk.tmp.component.cloud.ICloudProxyListener
                public void onResponse(String str, Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    List<AccessInfoPayload.AlcsDeviceInfo> list;
                    AccessInfoPayload accessInfoPayload = (AccessInfoPayload) GsonUtils.fromJson(obj.toString(), new TypeToken<AccessInfoPayload>() { // from class: com.aliyun.alink.linksdk.tmp.device.a.d.a.4.1
                    }.getType());
                    ALog.d("[Tmp]CreateConnectTask", "queryAccessInfo onResponse payload:" + accessInfoPayload);
                    if (accessInfoPayload == null || (list = accessInfoPayload.data) == null || list.isEmpty()) {
                        ALog.e("[Tmp]CreateConnectTask", "queryAccessInfo onResponse payload null");
                        AccessInfo accessInfo2 = TmpStorage.getInstance().getAccessInfo(((com.aliyun.alink.linksdk.tmp.device.a.d) a.this).f11298m.getBasicData().getDevId(), "local");
                        if (accessInfo2 != null && !TextUtils.isEmpty(accessInfo2.mAccessKey) && !TextUtils.isEmpty(accessInfo2.mAccessToken)) {
                            DefaultClientConfig defaultClientConfig = (DefaultClientConfig) ((com.aliyun.alink.linksdk.tmp.device.a.d) a.this).f11298m;
                            defaultClientConfig.setAccessKey(accessInfo2.mAccessKey);
                            defaultClientConfig.setAccessToken(accessInfo2.mAccessToken);
                        }
                    } else {
                        AccessInfoPayload.AlcsDeviceInfo alcsDeviceInfo = accessInfoPayload.data.get(0);
                        DefaultClientConfig defaultClientConfig2 = (DefaultClientConfig) ((com.aliyun.alink.linksdk.tmp.device.a.d) a.this).f11298m;
                        defaultClientConfig2.setAccessKey(alcsDeviceInfo.accessKey);
                        defaultClientConfig2.setAccessToken(alcsDeviceInfo.accessToken);
                        a.this.a(alcsDeviceInfo.productKey, alcsDeviceInfo.deviceName);
                        TmpStorage.DeviceInfo deviceInfo = new TmpStorage.DeviceInfo(alcsDeviceInfo.productKey, alcsDeviceInfo.deviceName);
                        TmpStorage.getInstance().saveDeviceInfo(((com.aliyun.alink.linksdk.tmp.device.a.d) a.this).f11298m.getBasicData().getIotId(), alcsDeviceInfo.productKey, alcsDeviceInfo.deviceName);
                        TmpStorage.getInstance().saveIotId(alcsDeviceInfo.productKey, alcsDeviceInfo.deviceName, ((com.aliyun.alink.linksdk.tmp.device.a.d) a.this).f11298m.getBasicData().getIotId());
                        TmpStorage.getInstance().saveAccessInfo(deviceInfo.getId(), alcsDeviceInfo.accessKey, alcsDeviceInfo.accessToken);
                    }
                    a.this.b();
                }
            });
            return;
        }
        DefaultClientConfig defaultClientConfig = (DefaultClientConfig) this.f11298m;
        defaultClientConfig.setAccessKey(accessInfo.mAccessKey);
        defaultClientConfig.setAccessToken(accessInfo.mAccessToken);
        b();
    }
}
