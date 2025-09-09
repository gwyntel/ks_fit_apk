package com.aliyun.alink.linksdk.tmp.device.a.g;

import com.aliyun.alink.linksdk.tmp.api.DeviceBasicData;
import com.aliyun.alink.linksdk.tmp.connect.a.k;
import com.aliyun.alink.linksdk.tmp.connect.c;
import com.aliyun.alink.linksdk.tmp.connect.e;
import com.aliyun.alink.linksdk.tmp.device.a.d;
import com.aliyun.alink.linksdk.tmp.listener.IDevRawDataListener;
import com.aliyun.alink.linksdk.tmp.utils.ErrorInfo;
import com.aliyun.alink.linksdk.tmp.utils.LogCat;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class a extends d<com.aliyun.alink.linksdk.tmp.device.a.h.a> implements c {

    /* renamed from: n, reason: collision with root package name */
    private static final String f11356n = "[Tmp]SendRawDataTask";

    /* renamed from: o, reason: collision with root package name */
    private byte[] f11357o;

    public a(com.aliyun.alink.linksdk.tmp.device.a aVar, DeviceBasicData deviceBasicData, IDevRawDataListener iDevRawDataListener) {
        super(aVar, null);
        a(iDevRawDataListener);
        a(deviceBasicData);
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.a.d, com.aliyun.alink.linksdk.tmp.device.a.a
    /* renamed from: b */
    public void a(com.aliyun.alink.linksdk.tmp.connect.d dVar, e eVar, ErrorInfo errorInfo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        IDevRawDataListener iDevRawDataListener = this.f11292g;
        if (iDevRawDataListener == null) {
            LogCat.e(f11356n, "onFlowComplete handler empty error");
        } else {
            this.f11292g = null;
            iDevRawDataListener.onSuccess(this.f11290e, eVar.f());
        }
    }

    public a a(byte[] bArr) {
        this.f11357o = bArr;
        return this;
    }

    @Override // com.aliyun.alink.linksdk.tmp.connect.c
    public void a(com.aliyun.alink.linksdk.tmp.connect.d dVar, e eVar) {
        if (eVar != null && eVar.b()) {
            a((a) dVar, (com.aliyun.alink.linksdk.tmp.connect.d) eVar);
        } else {
            a(dVar, (ErrorInfo) null);
        }
    }

    @Override // com.aliyun.alink.linksdk.tmp.connect.c
    public void a(com.aliyun.alink.linksdk.tmp.connect.d dVar, ErrorInfo errorInfo) {
        b((a) dVar, errorInfo);
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.a.d, com.aliyun.alink.linksdk.tmp.device.a.a
    public boolean a() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        super.a();
        DeviceBasicData deviceBasicData = this.f11295j;
        if (deviceBasicData != null && this.f11294i != null) {
            boolean zA = this.f11294i.a(k.a(deviceBasicData.getProductKey(), this.f11295j.getDeviceName()).a(this.f11357o).c(), this);
            ALog.d(f11356n, "action bRet:" + zA);
            return zA;
        }
        ALog.e(f11356n, "mDeviceBasicData or mDeviceModel or mConnect null");
        b((a) null, new ErrorInfo(300, "param is invalid"));
        return false;
    }
}
