package com.aliyun.alink.linksdk.tmp.device.a.d;

import android.text.TextUtils;
import com.aliyun.alink.linksdk.cmp.api.ConnectSDK;
import com.aliyun.alink.linksdk.cmp.core.base.ARequest;
import com.aliyun.alink.linksdk.cmp.core.base.AResponse;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener;
import com.aliyun.alink.linksdk.tmp.api.DeviceBasicData;
import com.aliyun.alink.linksdk.tmp.api.OutputParams;
import com.aliyun.alink.linksdk.tmp.config.DefaultServerConfig;
import com.aliyun.alink.linksdk.tmp.config.DeviceConfig;
import com.aliyun.alink.linksdk.tmp.data.auth.ServerEncryptInfo;
import com.aliyun.alink.linksdk.tmp.device.payload.cloud.PrefixGetPayload;
import com.aliyun.alink.linksdk.tmp.listener.IDevListener;
import com.aliyun.alink.linksdk.tmp.storage.TmpStorage;
import com.aliyun.alink.linksdk.tmp.utils.CloudUtils;
import com.aliyun.alink.linksdk.tmp.utils.ErrorInfo;
import com.aliyun.alink.linksdk.tmp.utils.GsonUtils;
import com.aliyun.alink.linksdk.tmp.utils.TextHelper;
import com.aliyun.alink.linksdk.tools.AError;
import com.aliyun.alink.linksdk.tools.ALog;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.InvocationTargetException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.NoSuchPaddingException;

/* loaded from: classes2.dex */
public class f extends b {

    /* renamed from: n, reason: collision with root package name */
    protected String f11313n;

    public f(com.aliyun.alink.linksdk.tmp.device.a aVar, DeviceBasicData deviceBasicData, DeviceConfig deviceConfig, IDevListener iDevListener) {
        super(aVar, deviceBasicData, deviceConfig, iDevListener);
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.a.d.b, com.aliyun.alink.linksdk.tmp.listener.IDevListener
    public void onSuccess(Object obj, OutputParams outputParams) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d("[Tmp]CreateConnectTask", "onSuccess returnValue:" + outputParams + " this :" + this + " mConnectId:" + this.f11310q);
        com.aliyun.alink.linksdk.tmp.device.a aVar = this.f11293h;
        if (aVar != null) {
            if (outputParams != null) {
                if (TextUtils.isEmpty(this.f11310q)) {
                    this.f11310q = String.valueOf(outputParams.get(com.aliyun.alink.linksdk.tmp.connect.a.f11115b).getValue());
                } else {
                    this.f11313n = String.valueOf(outputParams.get(com.aliyun.alink.linksdk.tmp.connect.a.f11115b).getValue());
                }
            } else if (TextUtils.isEmpty(this.f11310q) && outputParams == null) {
                onFail(null, new ErrorInfo(300, "param is invalid"));
                ALog.e("[Tmp]CreateConnectTask", "create connect fail");
                return;
            }
            ALog.d("[Tmp]CreateConnectTask", "create connect connectId:" + this.f11310q + " mSecondConnectId:" + this.f11313n);
            aVar.a(com.aliyun.alink.linksdk.tmp.connect.a.a(this.f11310q, this.f11313n));
        }
        a((f) null, (Object) null);
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.a.d, com.aliyun.alink.linksdk.tmp.device.a.a
    public boolean a() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        DefaultServerConfig defaultServerConfig = (DefaultServerConfig) this.f11298m;
        if (defaultServerConfig == null) {
            b((f) null, (ErrorInfo) null);
            return true;
        }
        ALog.d("[Tmp]CreateConnectTask", "getConnectType:" + defaultServerConfig.getConnectType());
        if (defaultServerConfig.getConnectType() == DefaultServerConfig.ConnectType.COAP) {
            if (TextUtils.isEmpty(((DefaultServerConfig) this.f11298m).mIotProductKey) || (!(TextUtils.isEmpty(((DefaultServerConfig) this.f11298m).getPrefix()) || TextUtils.isEmpty(((DefaultServerConfig) this.f11298m).getSecret())) || TextUtils.isEmpty(((DefaultServerConfig) this.f11298m).mIotDeviceName))) {
                onFail(this.f11290e, null);
            } else {
                b();
            }
        } else if (defaultServerConfig.getConnectType() == DefaultServerConfig.ConnectType.MQTT) {
            this.f11310q = ConnectSDK.getInstance().getPersistentConnectId();
            onSuccess(this.f11290e, null);
        } else if (defaultServerConfig.getConnectType() == DefaultServerConfig.ConnectType.COAP_AND_MQTT) {
            this.f11310q = ConnectSDK.getInstance().getPersistentConnectId();
            if (TextUtils.isEmpty(((DefaultServerConfig) this.f11298m).mIotProductKey) || (!(TextUtils.isEmpty(((DefaultServerConfig) this.f11298m).getPrefix()) || TextUtils.isEmpty(((DefaultServerConfig) this.f11298m).getSecret())) || TextUtils.isEmpty(((DefaultServerConfig) this.f11298m).mIotDeviceName))) {
                onFail(this.f11290e, null);
            } else {
                b();
            }
        } else {
            c();
        }
        return true;
    }

    protected void b() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d("[Tmp]CreateConnectTask", "queryPrefx start");
        DefaultServerConfig defaultServerConfig = (DefaultServerConfig) this.f11298m;
        final String strCombineStr = TextHelper.combineStr(defaultServerConfig.mIotProductKey, defaultServerConfig.mIotDeviceName);
        ServerEncryptInfo serverEnptInfo = TmpStorage.getInstance().getServerEnptInfo(strCombineStr);
        if (serverEnptInfo == null) {
            CloudUtils.queryPrefixSecret(this.f11298m.getBasicData().getProductKey(), this.f11298m.getBasicData().getDeviceName(), new IConnectSendListener() { // from class: com.aliyun.alink.linksdk.tmp.device.a.d.f.1
                @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener
                public void onFailure(ARequest aRequest, AError aError) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    ALog.e("[Tmp]CreateConnectTask", "queryPrefx onResponse  error:" + aError);
                    ServerEncryptInfo serverEnptInfo2 = TmpStorage.getInstance().getServerEnptInfo(((com.aliyun.alink.linksdk.tmp.device.a.d) f.this).f11298m.getDevId(), "local");
                    if (serverEnptInfo2 != null && !TextUtils.isEmpty(serverEnptInfo2.mPrefix) && !TextUtils.isEmpty(serverEnptInfo2.mSecret)) {
                        DefaultServerConfig defaultServerConfig2 = (DefaultServerConfig) ((com.aliyun.alink.linksdk.tmp.device.a.d) f.this).f11298m;
                        defaultServerConfig2.setPrefix(serverEnptInfo2.mPrefix);
                        defaultServerConfig2.setSecret(serverEnptInfo2.mSecret);
                    }
                    f.this.c();
                }

                @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener
                public void onResponse(ARequest aRequest, AResponse aResponse) throws IllegalAccessException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalArgumentException, InvocationTargetException {
                    Object obj;
                    ALog.d("[Tmp]CreateConnectTask", "registerPersistentConnect send onResponse:" + aResponse);
                    if (aResponse == null || (obj = aResponse.data) == null) {
                        ALog.e("[Tmp]CreateConnectTask", "registerPersistentConnect send onResponse error");
                        ServerEncryptInfo serverEnptInfo2 = TmpStorage.getInstance().getServerEnptInfo(((com.aliyun.alink.linksdk.tmp.device.a.d) f.this).f11298m.getDevId(), "local");
                        if (serverEnptInfo2 != null && !TextUtils.isEmpty(serverEnptInfo2.mPrefix) && !TextUtils.isEmpty(serverEnptInfo2.mSecret)) {
                            DefaultServerConfig defaultServerConfig2 = (DefaultServerConfig) ((com.aliyun.alink.linksdk.tmp.device.a.d) f.this).f11298m;
                            defaultServerConfig2.setPrefix(serverEnptInfo2.mPrefix);
                            defaultServerConfig2.setSecret(serverEnptInfo2.mSecret);
                        }
                    } else {
                        PrefixGetPayload prefixGetPayload = (PrefixGetPayload) GsonUtils.fromJson(obj.toString(), new TypeToken<PrefixGetPayload>() { // from class: com.aliyun.alink.linksdk.tmp.device.a.d.f.1.1
                        }.getType());
                        if (prefixGetPayload != null && prefixGetPayload.data != null) {
                            DefaultServerConfig defaultServerConfig3 = (DefaultServerConfig) ((com.aliyun.alink.linksdk.tmp.device.a.d) f.this).f11298m;
                            defaultServerConfig3.setPrefix(prefixGetPayload.data.prefix);
                            defaultServerConfig3.setSecret(prefixGetPayload.data.deviceSecret);
                            TmpStorage tmpStorage = TmpStorage.getInstance();
                            String str = strCombineStr;
                            PrefixGetPayload.PrefixGetData prefixGetData = prefixGetPayload.data;
                            tmpStorage.saveServerEnptInfo(str, prefixGetData.prefix, prefixGetData.deviceSecret);
                        }
                    }
                    f.this.c();
                }
            });
            return;
        }
        defaultServerConfig.setPrefix(serverEnptInfo.mPrefix);
        defaultServerConfig.setSecret(serverEnptInfo.mSecret);
        c();
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.a.d.b
    protected void c() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (this.f11294i != null) {
            onSuccess(null, null);
            return;
        }
        ALog.d("[Tmp]CreateConnectTask", "createConnect this :" + this);
        if (TextUtils.isEmpty(this.f11310q)) {
            this.f11310q = com.aliyun.alink.linksdk.tmp.connect.a.a(this.f11295j, this.f11298m, this);
        } else {
            this.f11313n = com.aliyun.alink.linksdk.tmp.connect.a.a(this.f11295j, this.f11298m, this);
        }
    }
}
