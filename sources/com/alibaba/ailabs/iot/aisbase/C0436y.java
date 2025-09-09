package com.alibaba.ailabs.iot.aisbase;

import com.alibaba.ailabs.iot.aisbase.callback.OtaActionListener;
import com.alibaba.ailabs.tg.utils.LogUtils;
import datasource.NetworkCallback;
import datasource.implemention.data.OtaProgressRespData;

/* renamed from: com.alibaba.ailabs.iot.aisbase.y, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0436y implements NetworkCallback<OtaProgressRespData> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ OtaActionListener f8592a;

    public C0436y(OtaActionListener otaActionListener) {
        this.f8592a = otaActionListener;
    }

    @Override // datasource.NetworkCallback
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(OtaProgressRespData otaProgressRespData) {
        LogUtils.d(OtaActionListener.f8362a, "Report ota progress successful");
    }

    @Override // datasource.NetworkCallback
    public void onFailure(String str, String str2) {
        LogUtils.e(OtaActionListener.f8362a, "Failed to report ota progress");
    }
}
