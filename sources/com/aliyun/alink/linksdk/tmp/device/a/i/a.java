package com.aliyun.alink.linksdk.tmp.device.a.i;

import android.text.TextUtils;
import com.aliyun.alink.linksdk.cmp.api.ConnectSDK;
import com.aliyun.alink.linksdk.cmp.connect.channel.PersistentConnectInfo;
import com.aliyun.alink.linksdk.cmp.core.base.AConnectInfo;
import com.aliyun.alink.linksdk.cmp.core.base.ARequest;
import com.aliyun.alink.linksdk.cmp.core.base.AResponse;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener;
import com.aliyun.alink.linksdk.tmp.api.DeviceBasicData;
import com.aliyun.alink.linksdk.tmp.config.DefaultProvisionConfig;
import com.aliyun.alink.linksdk.tmp.config.DeviceConfig;
import com.aliyun.alink.linksdk.tmp.connect.d;
import com.aliyun.alink.linksdk.tmp.connect.e;
import com.aliyun.alink.linksdk.tmp.data.auth.AuthPairData;
import com.aliyun.alink.linksdk.tmp.data.auth.ProvisionAuthData;
import com.aliyun.alink.linksdk.tmp.data.auth.SetupData;
import com.aliyun.alink.linksdk.tmp.device.payload.cloud.PrefixGetPayload;
import com.aliyun.alink.linksdk.tmp.listener.IDevListener;
import com.aliyun.alink.linksdk.tmp.utils.AuthInfoCreater;
import com.aliyun.alink.linksdk.tmp.utils.CloudUtils;
import com.aliyun.alink.linksdk.tmp.utils.ErrorInfo;
import com.aliyun.alink.linksdk.tmp.utils.GsonUtils;
import com.aliyun.alink.linksdk.tools.AError;
import com.aliyun.alink.linksdk.tools.ALog;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.InvocationTargetException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.NoSuchPaddingException;

/* loaded from: classes2.dex */
public class a extends b {

    /* renamed from: o, reason: collision with root package name */
    private static final String f11364o = "[Tmp]ChgPrvsRcerAuthTask";

    /* renamed from: p, reason: collision with root package name */
    private AuthPairData f11365p;

    /* renamed from: q, reason: collision with root package name */
    private DefaultProvisionConfig f11366q;

    public a(com.aliyun.alink.linksdk.tmp.device.a aVar, Object obj, DeviceBasicData deviceBasicData, DeviceConfig deviceConfig, IDevListener iDevListener) {
        super(obj, deviceBasicData, aVar, iDevListener);
        a(aVar);
        if (DeviceConfig.DeviceType.PROVISION == deviceConfig.getDeviceType()) {
            this.f11366q = (DefaultProvisionConfig) deviceConfig;
        }
    }

    public boolean b() {
        SetupData setupData = new SetupData();
        this.f11370n = setupData;
        setupData.configType = "ServerAuthInfo";
        ProvisionAuthData provisionAuthData = new ProvisionAuthData();
        AuthPairData authPairData = this.f11365p;
        provisionAuthData.authCode = authPairData.authCode;
        provisionAuthData.authSecret = authPairData.authSecret;
        provisionAuthData.productKey = this.f11295j.getProductKey();
        provisionAuthData.deviceName = this.f11295j.getDeviceName();
        this.f11370n.configValue.add(provisionAuthData);
        return super.a();
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.a.i.b, com.aliyun.alink.linksdk.tmp.device.a.d, com.aliyun.alink.linksdk.tmp.device.a.a
    public boolean a() throws IllegalAccessException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11364o, "ChgPrvsRcerAuthTask action");
        DefaultProvisionConfig defaultProvisionConfig = this.f11366q;
        if (defaultProvisionConfig == null || !defaultProvisionConfig.IsAutoChangeAuth()) {
            ALog.d(f11364o, "action config not IsAutoChangeAuth or not default");
            a((d) null, (e) null);
            return true;
        }
        if (DefaultProvisionConfig.AuthChangeType.LocalAuth == this.f11366q.getAuthChangeType()) {
            AuthPairData authPairDataCreateAuthInfo = AuthInfoCreater.getInstance().createAuthInfo(this.f11295j.getProductKey(), this.f11295j.getDeviceName(), "0");
            this.f11365p = authPairDataCreateAuthInfo;
            if (authPairDataCreateAuthInfo != null) {
                return b();
            }
            ALog.d(f11364o, "action mAuthInfo null");
            a((d) null, (e) null);
            return true;
        }
        if (DefaultProvisionConfig.AuthChangeType.CloudAuth == this.f11366q.getAuthChangeType()) {
            CloudUtils.registerPersistentConnect(null, null, null, null);
            AConnectInfo connectInfo = ConnectSDK.getInstance().getConnectInfo(ConnectSDK.getInstance().getPersistentConnectId());
            if (connectInfo == null || !(connectInfo instanceof PersistentConnectInfo)) {
                ALog.e(f11364o, "PersistentConnectInfo empty");
                b((a) null, new ErrorInfo(301, "param is invalid"));
                return false;
            }
            PersistentConnectInfo persistentConnectInfo = (PersistentConnectInfo) connectInfo;
            CloudUtils.queryPrefixSecret(persistentConnectInfo.productKey, persistentConnectInfo.deviceName, this.f11366q.getBasicData().getProductKey(), this.f11366q.getBasicData().getDeviceName(), new IConnectSendListener() { // from class: com.aliyun.alink.linksdk.tmp.device.a.i.a.1
                @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener
                public void onFailure(ARequest aRequest, AError aError) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    ALog.e(a.f11364o, "queryPrefx onResponse  error:" + aError);
                    a.this.b((a) null, aError == null ? null : new ErrorInfo(aError.getCode(), aError.getMsg()));
                }

                @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener
                public void onResponse(ARequest aRequest, AResponse aResponse) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    Object obj;
                    PrefixGetPayload.PrefixGetData prefixGetData;
                    ALog.d(a.f11364o, "registerPersistentConnect queryPrefixSecret send onResponse:" + aResponse);
                    if (aResponse == null || (obj = aResponse.data) == null) {
                        a.this.b((a) null, new ErrorInfo(300, "param is invalid"));
                        return;
                    }
                    PrefixGetPayload prefixGetPayload = (PrefixGetPayload) GsonUtils.fromJson(obj.toString(), new TypeToken<PrefixGetPayload>() { // from class: com.aliyun.alink.linksdk.tmp.device.a.i.a.1.1
                    }.getType());
                    if (prefixGetPayload == null || (prefixGetData = prefixGetPayload.data) == null || TextUtils.isEmpty(prefixGetData.prefix) || TextUtils.isEmpty(prefixGetPayload.data.deviceSecret)) {
                        a.this.b((a) null, new ErrorInfo(300, "param is invalid"));
                        return;
                    }
                    a aVar = a.this;
                    PrefixGetPayload.PrefixGetData prefixGetData2 = prefixGetPayload.data;
                    aVar.f11365p = new AuthPairData(null, null, prefixGetData2.prefix, prefixGetData2.deviceSecret);
                    a.this.b();
                }
            });
        } else {
            ALog.e(f11364o, "ChgPrvsRcerAuthTask action getAuthChangeType error :" + this.f11366q.getAuthChangeType());
            a((d) null, (e) null);
        }
        return true;
    }
}
