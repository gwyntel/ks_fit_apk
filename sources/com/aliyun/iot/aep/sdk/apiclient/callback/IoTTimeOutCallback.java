package com.aliyun.iot.aep.sdk.apiclient.callback;

import android.os.Handler;
import android.os.Message;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequest;
import java.util.concurrent.TimeoutException;

/* loaded from: classes3.dex */
public class IoTTimeOutCallback implements IoTCallback, Handler.Callback {

    /* renamed from: a, reason: collision with root package name */
    public IoTCallback f11629a;

    /* renamed from: b, reason: collision with root package name */
    public Handler f11630b;

    /* renamed from: c, reason: collision with root package name */
    public boolean f11631c = false;

    public IoTTimeOutCallback(IoTCallback ioTCallback, long j2) {
        j2 = j2 < 1000 ? 1000L : j2;
        this.f11629a = ioTCallback;
        Handler handler = new Handler(this);
        this.f11630b = handler;
        handler.sendEmptyMessageDelayed(1717986918, j2);
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        this.f11631c = true;
        this.f11629a.onFailure(null, new TimeoutException("request timeout"));
        this.f11630b.removeCallbacksAndMessages(null);
        return this.f11631c;
    }

    @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
    public void onFailure(IoTRequest ioTRequest, Exception exc) {
        if (this.f11631c) {
            return;
        }
        this.f11629a.onFailure(ioTRequest, exc);
        this.f11630b.removeCallbacksAndMessages(null);
    }

    @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
    public void onResponse(IoTRequest ioTRequest, IoTResponse ioTResponse) {
        if (this.f11631c) {
            return;
        }
        this.f11629a.onResponse(ioTRequest, ioTResponse);
        this.f11630b.removeCallbacksAndMessages(null);
    }
}
