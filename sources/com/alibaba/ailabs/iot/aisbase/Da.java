package com.alibaba.ailabs.iot.aisbase;

import com.alibaba.ailabs.iot.aisbase.plugin.ota.ReportProgressUtil;
import com.alibaba.ailabs.tg.utils.LogUtils;
import datasource.NetworkCallback;
import datasource.implemention.data.OtaProgressRespData;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public class Da implements NetworkCallback<OtaProgressRespData> {
    @Override // datasource.NetworkCallback
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(OtaProgressRespData otaProgressRespData) {
        LogUtils.d(ReportProgressUtil.f8513a, "Report ota progress successful");
    }

    @Override // datasource.NetworkCallback
    public void onFailure(String str, String str2) {
        LogUtils.e(ReportProgressUtil.f8513a, "Failed to report ota progress");
    }
}
