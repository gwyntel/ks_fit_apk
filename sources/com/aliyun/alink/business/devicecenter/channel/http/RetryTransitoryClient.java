package com.aliyun.alink.business.devicecenter.channel.http;

import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.iot.aep.sdk.apiclient.IoTAPIClient;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback;
import com.aliyun.iot.aep.sdk.apiclient.callback.IoTResponse;
import com.aliyun.iot.aep.sdk.apiclient.request.IoTRequest;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes2.dex */
public class RetryTransitoryClient {

    /* renamed from: c, reason: collision with root package name */
    public AtomicInteger f10313c;

    /* renamed from: d, reason: collision with root package name */
    public AtomicInteger f10314d;

    /* renamed from: f, reason: collision with root package name */
    public AtomicBoolean f10316f;

    /* renamed from: a, reason: collision with root package name */
    public final int f10311a = 3;

    /* renamed from: b, reason: collision with root package name */
    public final int f10312b = 10;

    /* renamed from: e, reason: collision with root package name */
    public AtomicBoolean f10315e = new AtomicBoolean(false);

    public RetryTransitoryClient(boolean z2) {
        this.f10313c = null;
        this.f10314d = null;
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        this.f10316f = atomicBoolean;
        atomicBoolean.set(z2);
        this.f10313c = new AtomicInteger(0);
        this.f10314d = new AtomicInteger(0);
    }

    public void cancelRequest() {
        this.f10315e.set(true);
    }

    public void send(final IoTAPIClient ioTAPIClient, final IoTRequest ioTRequest, final IoTCallback ioTCallback) {
        ALog.d("RetryTransitoryClient", "send() called with: client = [" + ioTAPIClient + "], request = [" + ioTRequest + "], callback = [" + ioTCallback + "], needRetrySend = [" + this.f10316f.get() + "]");
        if (ioTAPIClient == null) {
            if (ioTCallback != null) {
                ioTCallback.onFailure(ioTRequest, new IllegalArgumentException("IoTApiClientClientNull"));
                return;
            }
            return;
        }
        try {
            ioTAPIClient.send(ioTRequest, new IoTCallback() { // from class: com.aliyun.alink.business.devicecenter.channel.http.RetryTransitoryClient.1
                @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
                public void onFailure(IoTRequest ioTRequest2, Exception exc) throws InterruptedException {
                    ALog.w("RetryTransitoryClient", "onFailure() called with: ioTRequest = [" + TransitoryClient.getInstance().requestToStr(ioTRequest2) + "], e = [" + exc + "]");
                    if (RetryTransitoryClient.this.a(exc)) {
                        RetryTransitoryClient.this.f10314d.incrementAndGet();
                    } else {
                        RetryTransitoryClient.this.f10313c.incrementAndGet();
                    }
                    RetryTransitoryClient.this.a(ioTAPIClient, ioTRequest, ioTCallback, exc, null);
                }

                @Override // com.aliyun.iot.aep.sdk.apiclient.callback.IoTCallback
                public void onResponse(IoTRequest ioTRequest2, IoTResponse ioTResponse) throws InterruptedException {
                    ALog.d("RetryTransitoryClient", "onResponse() called with: ioTRequest = [" + TransitoryClient.getInstance().requestToStr(ioTRequest2) + "], ioTResponse = [" + TransitoryClient.getInstance().responseToStr(ioTResponse) + "]");
                    if (ioTResponse == null || ioTResponse.getCode() != 200) {
                        RetryTransitoryClient.this.f10313c.incrementAndGet();
                        RetryTransitoryClient.this.a(ioTAPIClient, ioTRequest, ioTCallback, null, ioTResponse);
                        return;
                    }
                    IoTCallback ioTCallback2 = ioTCallback;
                    if (ioTCallback2 != null) {
                        ioTCallback2.onResponse(ioTRequest2, ioTResponse);
                    } else {
                        ALog.w("RetryTransitoryClient", "callback is null.");
                    }
                }
            });
        } catch (Exception e2) {
            e2.printStackTrace();
            if (ioTCallback != null) {
                ioTCallback.onFailure(ioTRequest, e2);
            }
        }
    }

    public final void a(IoTAPIClient ioTAPIClient, IoTRequest ioTRequest, IoTCallback ioTCallback, Exception exc, IoTResponse ioTResponse) throws InterruptedException {
        ALog.d("RetryTransitoryClient", "retryCount=" + this.f10313c.get() + ", retryDnsCount" + this.f10314d.get() + ", needRetrySend" + this.f10316f.get() + ", stopRetry=" + this.f10315e.get());
        if (this.f10315e.get()) {
            ALog.w("RetryTransitoryClient", "stopRetry called.");
            return;
        }
        if (this.f10316f.get() && this.f10313c.get() < 3 && this.f10314d.get() < 10) {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException unused) {
            }
            if (this.f10315e.get()) {
                ALog.w("RetryTransitoryClient", "stopRetry called. sleep");
                return;
            } else {
                send(ioTAPIClient, ioTRequest, ioTCallback);
                return;
            }
        }
        ALog.d("RetryTransitoryClient", "callback return, current retryCount=" + this.f10313c.get() + ", retryDnsCount" + this.f10314d.get());
        if (ioTCallback != null && exc != null) {
            ioTCallback.onFailure(ioTRequest, exc);
        } else {
            if (ioTCallback == null || ioTResponse == null) {
                return;
            }
            ioTCallback.onResponse(ioTRequest, ioTResponse);
        }
    }

    public final boolean a(Exception exc) {
        return (exc == null || exc.toString() == null || !exc.toString().contains("UnknownHostException")) ? false : true;
    }
}
