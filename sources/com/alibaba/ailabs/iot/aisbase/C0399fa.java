package com.alibaba.ailabs.iot.aisbase;

import com.alibaba.ailabs.iot.aisbase.callback.IActionListener;
import com.alibaba.ailabs.tg.utils.LogUtils;
import datasource.implemention.data.UpdateDeviceVersionRespData;

/* renamed from: com.alibaba.ailabs.iot.aisbase.fa, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0399fa implements IActionListener<UpdateDeviceVersionRespData> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ C0401ga f8403a;

    public C0399fa(C0401ga c0401ga) {
        this.f8403a = c0401ga;
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(UpdateDeviceVersionRespData updateDeviceVersionRespData) {
        LogUtils.d(this.f8403a.f8407a.f8485a, "Update device version success: " + updateDeviceVersionRespData.getModel());
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
    public void onFailure(int i2, String str) {
        LogUtils.d(this.f8403a.f8407a.f8485a, "Update device version failed, code is: " + i2 + ", desc: " + str);
    }
}
