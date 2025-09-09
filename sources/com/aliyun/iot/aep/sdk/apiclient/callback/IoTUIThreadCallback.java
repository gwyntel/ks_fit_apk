package com.aliyun.iot.aep.sdk.apiclient.callback;

import android.os.Handler;
import android.os.Looper;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequest;

/* loaded from: classes3.dex */
public class IoTUIThreadCallback implements IoTCallback {

    /* renamed from: a, reason: collision with root package name */
    public IoTCallback f11632a;

    /* renamed from: b, reason: collision with root package name */
    public Handler f11633b = new Handler(Looper.getMainLooper());

    public class a implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ IoTRequest f11634a;

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ Exception f11635b;

        public a(IoTRequest ioTRequest, Exception exc) {
            this.f11634a = ioTRequest;
            this.f11635b = exc;
        }

        @Override // java.lang.Runnable
        public void run() {
            IoTUIThreadCallback.this.f11632a.onFailure(this.f11634a, this.f11635b);
        }
    }

    public class b implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public final /* synthetic */ IoTRequest f11637a;

        /* renamed from: b, reason: collision with root package name */
        public final /* synthetic */ IoTResponse f11638b;

        public b(IoTRequest ioTRequest, IoTResponse ioTResponse) {
            this.f11637a = ioTRequest;
            this.f11638b = ioTResponse;
        }

        @Override // java.lang.Runnable
        public void run() {
            IoTUIThreadCallback.this.f11632a.onResponse(this.f11637a, this.f11638b);
        }
    }

    public IoTUIThreadCallback(IoTCallback ioTCallback) {
        this.f11632a = ioTCallback;
    }

    @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
    public void onFailure(IoTRequest ioTRequest, Exception exc) {
        if (this.f11632a == null) {
            return;
        }
        this.f11633b.post(new a(ioTRequest, exc));
    }

    @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
    public void onResponse(IoTRequest ioTRequest, IoTResponse ioTResponse) {
        if (this.f11632a == null) {
            return;
        }
        this.f11633b.post(new b(ioTRequest, ioTResponse));
    }
}
