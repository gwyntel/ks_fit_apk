package com.aliyun.alink.linksdk.tmp.device.b;

import android.text.TextUtils;
import com.aliyun.alink.linksdk.tmp.api.DeviceBasicData;
import com.aliyun.alink.linksdk.tmp.api.DeviceManager;
import com.aliyun.alink.linksdk.tmp.api.OutputParams;
import com.aliyun.alink.linksdk.tmp.config.DefaultClientConfig;
import com.aliyun.alink.linksdk.tmp.data.auth.AccessInfo;
import com.aliyun.alink.linksdk.tmp.device.a.c;
import com.aliyun.alink.linksdk.tmp.device.a.d.g;
import com.aliyun.alink.linksdk.tmp.listener.IDevListener;
import com.aliyun.alink.linksdk.tmp.storage.TmpStorage;
import com.aliyun.alink.linksdk.tmp.utils.ErrorInfo;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class a {

    /* renamed from: b, reason: collision with root package name */
    private static final String f11375b = "[Tmp]ALCSAutoConnector";

    /* renamed from: a, reason: collision with root package name */
    protected DeviceBasicData f11376a;

    public a(DeviceBasicData deviceBasicData) {
        this.f11376a = deviceBasicData;
    }

    public void a() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11375b, "startConnect mBasicData:" + this.f11376a);
        if (!"1".equalsIgnoreCase(this.f11376a.getModelType())) {
            ALog.d(f11375b, "startConnect not MODEL_TYPE_ALI_WIFI  auto connect return");
            return;
        }
        this.f11376a.setLocal(true);
        DeviceBasicData deviceBasicData = DeviceManager.getInstance().getDeviceBasicData(this.f11376a.getDevId());
        if (deviceBasicData == null) {
            ALog.w(f11375b, "startConnect local not found");
            return;
        }
        this.f11376a.setPort(deviceBasicData.getPort());
        this.f11376a.setAddr(deviceBasicData.getAddr());
        if (TextUtils.isEmpty(this.f11376a.getIotId())) {
            this.f11376a.setIotId(TmpStorage.getInstance().getIotId(this.f11376a.getProductKey(), this.f11376a.getDeviceName()));
        }
        AccessInfo accessInfo = TmpStorage.getInstance().getAccessInfo(this.f11376a.getDevId());
        c cVar = new c();
        DefaultClientConfig defaultClientConfig = new DefaultClientConfig(this.f11376a);
        if (accessInfo != null) {
            defaultClientConfig.mAccessKey = accessInfo.mAccessKey;
            defaultClientConfig.mAccessToken = accessInfo.mAccessToken;
        }
        IDevListener iDevListener = new IDevListener() { // from class: com.aliyun.alink.linksdk.tmp.device.b.a.1
            @Override // com.aliyun.alink.linksdk.tmp.listener.IDevListener
            public void onFail(Object obj, ErrorInfo errorInfo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                ALog.e(a.f11375b, "onFail errorInfo:" + errorInfo + " mBasicData:" + a.this.f11376a + " errorInfo:" + errorInfo);
            }

            @Override // com.aliyun.alink.linksdk.tmp.listener.IDevListener
            public void onSuccess(Object obj, OutputParams outputParams) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                ALog.d(a.f11375b, "onSuccess returnValue:" + outputParams);
            }
        };
        cVar.b(new g(null, this.f11376a, defaultClientConfig, iDevListener).a((Object) null)).b(new com.aliyun.alink.linksdk.tmp.device.a.d.a(null, this.f11376a, defaultClientConfig, iDevListener).a((Object) null)).a();
    }
}
