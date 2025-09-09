package com.alibaba.ailabs.iot.aisbase.callback;

import com.alibaba.ailabs.iot.aisbase.C0436y;
import com.alibaba.ailabs.iot.aisbase.RequestManage;
import com.alibaba.ailabs.iot.aisbase.UTLogUtils;
import com.alibaba.ailabs.iot.aisbase.plugin.ota.IOTAPlugin;
import com.alibaba.ailabs.iot.aisbase.plugin.ota.ReportProgressUtil;
import com.alibaba.ailabs.iot.aisbase.spec.BluetoothDeviceWrapper;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;

/* loaded from: classes2.dex */
public class OtaActionListener implements IOTAPlugin.IOTAActionListener {

    /* renamed from: a, reason: collision with root package name */
    public static final String f8362a = "OtaActionListener";

    /* renamed from: b, reason: collision with root package name */
    public IOTAPlugin.IOTAActionListener f8363b;

    /* renamed from: c, reason: collision with root package name */
    public BluetoothDeviceWrapper f8364c;

    /* renamed from: d, reason: collision with root package name */
    public String f8365d;

    /* renamed from: e, reason: collision with root package name */
    public String f8366e;

    /* renamed from: f, reason: collision with root package name */
    public String f8367f;

    /* renamed from: g, reason: collision with root package name */
    public String f8368g;

    /* renamed from: h, reason: collision with root package name */
    public final String f8369h = ReportProgressUtil.CODE_OK;

    /* renamed from: i, reason: collision with root package name */
    public final String f8370i = ReportProgressUtil.CODE_ERR;

    /* renamed from: j, reason: collision with root package name */
    public final String f8371j = ReportProgressUtil.TAG_START;

    /* renamed from: k, reason: collision with root package name */
    public final String f8372k = "FINISH";

    public OtaActionListener(IOTAPlugin.IOTAActionListener iOTAActionListener, BluetoothDeviceWrapper bluetoothDeviceWrapper, String str, String str2, String str3, String str4) {
        this.f8363b = iOTAActionListener;
        this.f8364c = bluetoothDeviceWrapper;
        this.f8368g = str;
        this.f8365d = str2;
        this.f8366e = str3;
        this.f8367f = str4;
    }

    @Override // com.alibaba.ailabs.iot.aisbase.plugin.ota.IOTAPlugin.IOTAActionListener
    public void onFailed(int i2, String str) {
        IOTAPlugin.IOTAActionListener iOTAActionListener = this.f8363b;
        if (iOTAActionListener != null) {
            iOTAActionListener.onFailed(i2, str);
        }
        BluetoothDeviceWrapper bluetoothDeviceWrapper = this.f8364c;
        if (bluetoothDeviceWrapper != null) {
            UTLogUtils.updateBusInfo("ota", UTLogUtils.buildDeviceInfo(bluetoothDeviceWrapper), UTLogUtils.buildOtaBusInfo("error", 0, i2, str));
        }
        a("FINISH", ReportProgressUtil.CODE_ERR, str + OpenAccountUIConstants.UNDER_LINE + i2);
    }

    @Override // com.alibaba.ailabs.iot.aisbase.plugin.ota.IOTAPlugin.IOTAActionListener
    public void onProgress(int i2, int i3) {
        IOTAPlugin.IOTAActionListener iOTAActionListener = this.f8363b;
        if (iOTAActionListener != null) {
            iOTAActionListener.onProgress(i2, i3);
        }
    }

    @Override // com.alibaba.ailabs.iot.aisbase.plugin.ota.IOTAPlugin.IOTAActionListener
    public void onStateChanged(IOTAPlugin.OTAState oTAState) {
        IOTAPlugin.IOTAActionListener iOTAActionListener = this.f8363b;
        if (iOTAActionListener != null) {
            iOTAActionListener.onStateChanged(oTAState);
        }
    }

    @Override // com.alibaba.ailabs.iot.aisbase.plugin.ota.IOTAPlugin.IOTAActionListener
    public void onSuccess(String str) {
        IOTAPlugin.IOTAActionListener iOTAActionListener = this.f8363b;
        if (iOTAActionListener != null) {
            iOTAActionListener.onSuccess(str);
        }
        UTLogUtils.updateBusInfo("ota", UTLogUtils.buildDeviceInfo(this.f8364c), UTLogUtils.buildOtaBusInfo("success", 0, 0, ""));
        this.f8366e = str;
        a("FINISH", ReportProgressUtil.CODE_OK, "");
    }

    public final void a(String str, String str2, String str3) {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("currentVersion", (Object) this.f8366e);
        jSONObject.put("targetVersion", (Object) this.f8367f);
        jSONObject.put("tag", (Object) str);
        jSONObject.put("code", (Object) str2);
        jSONObject.put("message", (Object) str3);
        RequestManage.getInstance().gmaOtaProgressReport(this.f8368g, this.f8365d, jSONObject.toJSONString(), new C0436y(this));
    }
}
