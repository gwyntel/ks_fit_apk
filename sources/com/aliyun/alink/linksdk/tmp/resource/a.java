package com.aliyun.alink.linksdk.tmp.resource;

import android.text.TextUtils;
import com.aliyun.alink.linksdk.tmp.api.OutputParams;
import com.aliyun.alink.linksdk.tmp.device.payload.ValueWrapper;
import com.aliyun.alink.linksdk.tmp.devicemodel.DeviceModel;
import com.aliyun.alink.linksdk.tmp.devicemodel.Profile;
import com.aliyun.alink.linksdk.tmp.listener.ITResRequestHandler;
import com.aliyun.alink.linksdk.tmp.listener.ITResResponseCallback;
import com.aliyun.alink.linksdk.tmp.utils.ErrorInfo;
import com.aliyun.alink.linksdk.tmp.utils.GsonUtils;
import com.aliyun.alink.linksdk.tmp.utils.WifiManagerUtil;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashMap;

/* loaded from: classes2.dex */
public class a implements ITResRequestHandler {

    /* renamed from: e, reason: collision with root package name */
    private static final String f11410e = "[Tmp]DiscoveryResHander";

    /* renamed from: a, reason: collision with root package name */
    protected String f11411a;

    /* renamed from: b, reason: collision with root package name */
    protected String f11412b;

    /* renamed from: c, reason: collision with root package name */
    protected DeviceModel f11413c;

    /* renamed from: d, reason: collision with root package name */
    protected int f11414d;

    public a(String str, String str2, DeviceModel deviceModel) {
        a(str, str2, deviceModel);
    }

    public void a(String str, String str2, DeviceModel deviceModel) {
        this.f11411a = str;
        this.f11412b = str2;
        this.f11413c = deviceModel;
        if (deviceModel != null) {
            String json = GsonUtils.toJson(deviceModel);
            if (TextUtils.isEmpty(json)) {
                return;
            }
            this.f11414d = json.length();
        }
    }

    @Override // com.aliyun.alink.linksdk.tmp.listener.IDevListener
    public void onFail(Object obj, ErrorInfo errorInfo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11410e, "onFail errorInfo:" + errorInfo);
    }

    @Override // com.aliyun.alink.linksdk.tmp.listener.ITResRequestHandler
    public void onProcess(String str, Object obj, ITResResponseCallback iTResResponseCallback) throws IllegalAccessException, SocketException, IllegalArgumentException, InvocationTargetException {
        Profile profile = new Profile();
        profile.setProdKey(this.f11411a);
        profile.setName(this.f11412b);
        profile.port = 5683;
        InetAddress ipAddress = WifiManagerUtil.getIpAddress(WifiManagerUtil.NetworkType.WLAN);
        profile.addr = ipAddress == null ? "" : ipAddress.getHostAddress();
        ValueWrapper valueWrapper = new ValueWrapper();
        ALog.d(f11410e, "onProcess identifier mDeviceModelLength:" + this.f11414d + " mDeviceModel:" + this.f11413c);
        DeviceModel deviceModel = this.f11413c;
        if (deviceModel == null || this.f11414d > 3072) {
            HashMap map = new HashMap();
            map.put("profile", profile);
            valueWrapper.setValue(map);
        } else {
            deviceModel.setProfile(profile);
            valueWrapper.setValue(this.f11413c);
        }
        iTResResponseCallback.onComplete("dev", null, new OutputParams("deviceModel", valueWrapper));
    }

    @Override // com.aliyun.alink.linksdk.tmp.listener.IDevListener
    public void onSuccess(Object obj, OutputParams outputParams) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11410e, "onSuccess returnValue:" + outputParams);
    }
}
