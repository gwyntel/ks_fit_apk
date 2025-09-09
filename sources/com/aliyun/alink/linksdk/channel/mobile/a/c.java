package com.aliyun.alink.linksdk.channel.mobile.a;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.alink.linksdk.channel.core.persistent.mqtt.MqttConfigure;
import com.aliyun.alink.linksdk.channel.mobile.a.b;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/* loaded from: classes2.dex */
final class c implements Callback {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ b.a f10998a;

    c(b.a aVar) {
        this.f10998a = aVar;
    }

    @Override // okhttp3.Callback
    public void onFailure(Call call, IOException iOException) {
        StringBuilder sb = new StringBuilder();
        sb.append("onFailure, e =");
        sb.append(iOException != null ? iOException.toString() : TmpConstant.GROUP_ROLE_UNKNOWN);
        a.a("DynamicHostRequest", sb.toString());
        b.a aVar = this.f10998a;
        if (aVar != null) {
            aVar.b();
        }
    }

    @Override // okhttp3.Callback
    public void onResponse(Call call, Response response) {
        a.a("DynamicHostRequest", "onResponse()");
        try {
            String strString = response.body().string();
            a.a("DynamicHostRequest", "onResponse(), rsp = " + strString);
            JSONObject object = JSON.parseObject(strString);
            String string = object.getJSONObject("data").getJSONObject("resources").getJSONObject("mqtt").getString("host");
            int intValue = object.getJSONObject("data").getJSONObject("resources").getJSONObject("mqtt").getIntValue("port");
            if (!TextUtils.isEmpty(string) && intValue != 0) {
                String unused = b.f10996b = string + ":" + intValue;
                StringBuilder sb = new StringBuilder();
                sb.append("onResponse(), host = ");
                sb.append(b.f10996b);
                a.a("DynamicHostRequest", sb.toString());
                MqttConfigure.mqttHost = b.f10996b;
                b.a aVar = this.f10998a;
                if (aVar != null) {
                    aVar.a();
                    return;
                }
                return;
            }
        } catch (Exception e2) {
            a.a("DynamicHostRequest", "onResponse(), error = " + e2.toString());
        }
        b.a aVar2 = this.f10998a;
        if (aVar2 != null) {
            aVar2.b();
        }
    }
}
