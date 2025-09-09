package com.aliyun.alink.linksdk.alcs.lpbs.bridge.a;

import com.aliyun.alink.linksdk.alcs.data.ica.ICADeviceInfo;
import com.aliyun.alink.linksdk.alcs.data.ica.ICADiscoveryDeviceInfo;
import com.aliyun.alink.linksdk.alcs.lpbs.api.AlcsPalConst;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalDeviceInfo;
import com.aliyun.alink.linksdk.alcs.lpbs.data.ica.ICAPalDiscoveryDevInfo;
import com.aliyun.alink.linksdk.alcs.lpbs.listener.PalDiscoveryListener;
import com.aliyun.alink.linksdk.alcs.pal.ica.ICADiscoveryListener;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class i implements ICADiscoveryListener {

    /* renamed from: a, reason: collision with root package name */
    private static final String f10821a = "[AlcsLPBS]ICADisHandlerWrapper";

    /* renamed from: b, reason: collision with root package name */
    private PalDiscoveryListener f10822b;

    /* renamed from: c, reason: collision with root package name */
    private c f10823c;

    public i(c cVar, PalDiscoveryListener palDiscoveryListener) {
        this.f10822b = palDiscoveryListener;
        this.f10823c = cVar;
    }

    public String a(ICADiscoveryDeviceInfo iCADiscoveryDeviceInfo) {
        return iCADiscoveryDeviceInfo.isDataNeedConvert() ? AlcsPalConst.DATA_FORMAT_CUNSTOM : "ALINK_FORMAT";
    }

    public String b(ICADiscoveryDeviceInfo iCADiscoveryDeviceInfo) {
        return (ICADiscoveryListener.PAL_LINKKIT_RAW.equalsIgnoreCase(iCADiscoveryDeviceInfo.pal) || ICADiscoveryListener.PAL_LINKKIT_ICA.equalsIgnoreCase(iCADiscoveryDeviceInfo.pal) || !ICADiscoveryListener.PAL_DLCP_RAW.equalsIgnoreCase(iCADiscoveryDeviceInfo.pal)) ? "1" : "3";
    }

    @Override // com.aliyun.alink.linksdk.alcs.pal.ica.ICADiscoveryListener
    public void onDiscoveryDevice(String str, int i2, String str2, ICADeviceInfo iCADeviceInfo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (iCADeviceInfo == null) {
            ALog.e(f10821a, "onDiscoveryDevice deviceInfo null");
            return;
        }
        ALog.d(f10821a, "onDiscoveryDevice addr:" + str + " port:" + i2 + " deviceInfo:" + iCADeviceInfo.toString() + " pal:" + str2);
        ICADiscoveryDeviceInfo iCADiscoveryDeviceInfo = new ICADiscoveryDeviceInfo(iCADeviceInfo, str, i2, str2);
        this.f10823c.a(iCADeviceInfo.getDevId(), iCADiscoveryDeviceInfo);
        ICADeviceInfo iCADeviceInfo2 = iCADiscoveryDeviceInfo.deviceInfo;
        ICAPalDiscoveryDevInfo iCAPalDiscoveryDevInfo = new ICAPalDiscoveryDevInfo(new PalDeviceInfo(iCADeviceInfo2.productKey, iCADeviceInfo2.deviceName), iCADiscoveryDeviceInfo.isPkDnNeedConvert());
        iCAPalDiscoveryDevInfo.modelType = b(iCADiscoveryDeviceInfo);
        iCAPalDiscoveryDevInfo.dataFormat = a(iCADiscoveryDeviceInfo);
        iCAPalDiscoveryDevInfo.isDataToCloud = iCADiscoveryDeviceInfo.isDataToCloud();
        iCAPalDiscoveryDevInfo.tslData = iCADiscoveryDeviceInfo.tslData;
        iCAPalDiscoveryDevInfo.deviceInfo.ip = iCADiscoveryDeviceInfo.addr;
        PalDiscoveryListener palDiscoveryListener = this.f10822b;
        if (palDiscoveryListener != null) {
            palDiscoveryListener.onDiscoveryDevice(iCAPalDiscoveryDevInfo);
        }
    }

    @Override // com.aliyun.alink.linksdk.alcs.pal.ica.ICADiscoveryListener
    public void onDiscoveryFinish() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f10821a, "onDiscoveryFinish mListener:" + this.f10822b);
        PalDiscoveryListener palDiscoveryListener = this.f10822b;
        if (palDiscoveryListener != null) {
            palDiscoveryListener.onDiscoveryFinish();
        }
    }
}
