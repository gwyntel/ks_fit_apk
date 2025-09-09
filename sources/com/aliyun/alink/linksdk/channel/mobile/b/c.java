package com.aliyun.alink.linksdk.channel.mobile.b;

import android.text.TextUtils;
import com.aliyun.alink.linksdk.channel.mobile.b.b;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTResponse;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequest;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
final class c implements IoTCallback {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ b.a f11012a;

    c(b.a aVar) {
        this.f11012a = aVar;
    }

    @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
    public void onFailure(IoTRequest ioTRequest, Exception exc) {
        com.aliyun.alink.linksdk.channel.mobile.a.a.d("MobileAuthHttpRequest", "onErrorResponse(), error = " + exc.toString());
        this.f11012a.a(exc.toString());
    }

    @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
    public void onResponse(IoTRequest ioTRequest, IoTResponse ioTResponse) throws JSONException {
        StringBuilder sb = new StringBuilder();
        sb.append("onResponse(),rsp = ");
        sb.append((ioTResponse == null || ioTResponse.getData() == null) ? "" : ioTResponse.getData().toString());
        com.aliyun.alink.linksdk.channel.mobile.a.a.a("MobileAuthHttpRequest", sb.toString());
        try {
            String message = ioTResponse.getMessage();
            int code = ioTResponse.getCode();
            JSONObject jSONObject = (JSONObject) ioTResponse.getData();
            if (code != 200) {
                this.f11012a.a(message);
                return;
            }
            String string = jSONObject.getString("deviceName");
            String string2 = jSONObject.getString("deviceSecret");
            String string3 = jSONObject.getString("productKey");
            if (TextUtils.isEmpty(string3) || TextUtils.isEmpty(string) || TextUtils.isEmpty(string2)) {
                return;
            }
            l lVar = new l(string3, string, string2);
            lVar.f11051a = b.f11003b;
            this.f11012a.a(lVar);
        } catch (Exception e2) {
            com.aliyun.alink.linksdk.channel.mobile.a.a.d("MobileAuthHttpRequest", "onResponse(), error = " + e2.toString());
            this.f11012a.a(e2.toString());
        }
    }
}
