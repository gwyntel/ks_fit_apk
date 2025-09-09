package com.aliyun.alink.linksdk.tmp.device.a.d;

import android.text.TextUtils;
import com.aliyun.alink.linksdk.tmp.api.DeviceBasicData;
import com.aliyun.alink.linksdk.tmp.api.OutputParams;
import com.aliyun.alink.linksdk.tmp.config.DeviceConfig;
import com.aliyun.alink.linksdk.tmp.listener.IDevListener;
import com.aliyun.alink.linksdk.tmp.storage.TmpStorage;
import com.aliyun.alink.linksdk.tmp.utils.ErrorInfo;
import com.aliyun.alink.linksdk.tools.ALog;

/* loaded from: classes2.dex */
public class b extends com.aliyun.alink.linksdk.tmp.device.a.d<b> implements IDevListener {

    /* renamed from: p, reason: collision with root package name */
    protected static final String f11309p = "[Tmp]CreateConnectTask";

    /* renamed from: q, reason: collision with root package name */
    protected String f11310q;

    public b(com.aliyun.alink.linksdk.tmp.device.a aVar, DeviceBasicData deviceBasicData, DeviceConfig deviceConfig, IDevListener iDevListener) {
        super(aVar, iDevListener);
        a(aVar);
        a(deviceBasicData);
        a(deviceConfig);
    }

    public void a(String str, String str2) {
        com.aliyun.alink.linksdk.tmp.device.a aVar = this.f11293h;
        if (aVar != null) {
            aVar.a(str, str2);
        }
    }

    protected void c() {
        if (this.f11294i != null) {
            onSuccess(null, null);
            return;
        }
        this.f11310q = com.aliyun.alink.linksdk.tmp.connect.a.a(this.f11295j, this.f11298m, this);
        com.aliyun.alink.linksdk.tmp.device.a aVar = this.f11293h;
        ALog.d(f11309p, "create connect connectId:" + this.f11310q);
        if (aVar == null || TextUtils.isEmpty(this.f11310q)) {
            return;
        }
        String str = this.f11310q;
        DeviceBasicData deviceBasicData = this.f11295j;
        aVar.a(com.aliyun.alink.linksdk.tmp.connect.a.a(str, deviceBasicData == null ? null : deviceBasicData.productKey, deviceBasicData != null ? deviceBasicData.deviceName : null));
    }

    public void onFail(Object obj, ErrorInfo errorInfo) {
        ALog.d(f11309p, "onFail errorInfo:" + errorInfo);
        if (errorInfo != null && (errorInfo.getErrorCode() == 502 || errorInfo.getErrorCode() == 506 || errorInfo.getErrorCode() == 501)) {
            ALog.d(f11309p, "onFail AUTH_ACCESS_TOKEN_INVALID clear storage");
            if (TextUtils.isEmpty(this.f11298m.getBasicData().getDevId())) {
                TmpStorage.DeviceInfo deviceInfo = TmpStorage.getInstance().getDeviceInfo(this.f11298m.getBasicData().getIotId());
                if (deviceInfo != null) {
                    TmpStorage.getInstance().saveAccessInfo(deviceInfo.getId(), "", "");
                }
            } else {
                TmpStorage.getInstance().saveAccessInfo(this.f11298m.getBasicData().getDevId(), "", "");
            }
        }
        b((b) null, errorInfo);
    }

    public void onSuccess(Object obj, OutputParams outputParams) {
        ALog.d(f11309p, "onSuccess returnValue:" + outputParams + " this :" + this + " mConnectId:" + this.f11310q);
        com.aliyun.alink.linksdk.tmp.device.a aVar = this.f11293h;
        if (aVar != null) {
            if (!TextUtils.isEmpty(this.f11310q)) {
                ALog.d(f11309p, "onSuccess mConnectId:" + this.f11310q);
            } else {
                if (!TextUtils.isEmpty(this.f11310q) || outputParams == null) {
                    onFail(null, new ErrorInfo(300, "param is invalid"));
                    ALog.e(f11309p, "create connect fail");
                    return;
                }
                this.f11310q = String.valueOf(outputParams.get(com.aliyun.alink.linksdk.tmp.connect.a.f11115b).getValue());
            }
            ALog.d(f11309p, "create connect connectId:" + this.f11310q);
            String str = this.f11310q;
            DeviceBasicData deviceBasicData = this.f11295j;
            aVar.a(com.aliyun.alink.linksdk.tmp.connect.a.a(str, deviceBasicData == null ? null : deviceBasicData.productKey, deviceBasicData == null ? null : deviceBasicData.deviceName));
        }
        a((b) null, (Object) null);
    }
}
