package com.aliyun.alink.linksdk.channel.core.persistent.mqtt.send;

import com.aliyun.alink.linksdk.channel.core.base.AError;
import com.aliyun.alink.linksdk.channel.core.persistent.mqtt.request.MqttSubscribeRequest;

/* loaded from: classes2.dex */
public class d implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    private b f10920a;

    /* renamed from: b, reason: collision with root package name */
    private byte f10921b;

    /* renamed from: c, reason: collision with root package name */
    private String f10922c;

    public d(b bVar, byte b2, String str) {
        this.f10920a = bVar;
        this.f10921b = b2;
        this.f10922c = str;
    }

    @Override // java.lang.Runnable
    public void run() {
        b bVar = this.f10920a;
        if (bVar == null) {
        }
        switch (this.f10921b) {
            case 1:
                if (bVar.getListener() != null) {
                    this.f10920a.getListener().onSuccess(this.f10920a.getRequest(), this.f10920a.getResponse());
                    break;
                }
                break;
            case 2:
            case 3:
                if (bVar.getListener() != null) {
                    AError aError = new AError();
                    if (this.f10921b == 3) {
                        aError.setCode(4101);
                    } else {
                        aError.setCode(4201);
                    }
                    aError.setMsg(this.f10922c);
                    this.f10920a.getListener().onFailed(this.f10920a.getRequest(), aError);
                    break;
                }
                break;
            case 4:
                if (bVar.b() != null) {
                    this.f10920a.b().onSuccess(((MqttSubscribeRequest) this.f10920a.getRequest()).topic);
                    break;
                }
                break;
            case 5:
            case 6:
                if (bVar.b() != null) {
                    AError aError2 = new AError();
                    if (this.f10921b == 3) {
                        aError2.setCode(4101);
                    } else {
                        aError2.setCode(4201);
                    }
                    aError2.setMsg(this.f10922c);
                    this.f10920a.b().onFailed(((MqttSubscribeRequest) this.f10920a.getRequest()).topic, aError2);
                    break;
                }
                break;
        }
    }
}
