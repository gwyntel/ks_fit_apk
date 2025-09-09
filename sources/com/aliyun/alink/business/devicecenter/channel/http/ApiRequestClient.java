package com.aliyun.alink.business.devicecenter.channel.http;

import com.aliyun.alink.business.devicecenter.base.DCEnvHelper;
import com.aliyun.alink.business.devicecenter.base.DCErrorCode;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.linksdk.tools.ThreadTools;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes2.dex */
public class ApiRequestClient implements IApiClient {

    /* renamed from: a, reason: collision with root package name */
    public IApiClient f10283a;

    /* renamed from: d, reason: collision with root package name */
    public AtomicInteger f10286d;

    /* renamed from: e, reason: collision with root package name */
    public AtomicInteger f10287e;

    /* renamed from: g, reason: collision with root package name */
    public AtomicBoolean f10289g;

    /* renamed from: h, reason: collision with root package name */
    public IRequestCallback f10290h;

    /* renamed from: i, reason: collision with root package name */
    public IRequestCallback f10291i;

    /* renamed from: b, reason: collision with root package name */
    public final int f10284b = 3;

    /* renamed from: c, reason: collision with root package name */
    public final int f10285c = 10;

    /* renamed from: f, reason: collision with root package name */
    public AtomicBoolean f10288f = new AtomicBoolean(false);

    public ApiRequestClient(boolean z2) {
        this.f10283a = null;
        this.f10286d = null;
        this.f10287e = null;
        AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        this.f10289g = atomicBoolean;
        this.f10290h = null;
        this.f10291i = null;
        atomicBoolean.set(z2);
        this.f10286d = new AtomicInteger(0);
        this.f10287e = new AtomicInteger(0);
        if (DCEnvHelper.isTgEnv()) {
            this.f10283a = new MtopApiClientImpl();
        } else if (DCEnvHelper.hasApiClient()) {
            this.f10283a = new AliyunApiClientImpl();
        } else {
            ALog.w("ApiRequestClient", "Not exist mtop and aliyun api");
        }
    }

    public void cancelRequest() {
        this.f10288f.set(true);
    }

    @Override // com.aliyun.alink.business.devicecenter.channel.http.IApiClient
    public void send(final IRequest iRequest, final Class<?> cls, IRequestCallback iRequestCallback) {
        ALog.d("ApiRequestClient", "send request: " + iRequest + ", callback: " + iRequestCallback);
        if (this.f10283a == null) {
            ALog.d("ApiRequestClient", "mApiClient is null");
            if (iRequestCallback != null) {
                iRequestCallback.onFail(new DCError(String.valueOf(DCErrorCode.PF_UNKNOWN_ERROR), "request api client is empty."), null);
                return;
            }
            return;
        }
        this.f10291i = iRequestCallback;
        if (this.f10290h == null) {
            this.f10290h = new IRequestCallback() { // from class: com.aliyun.alink.business.devicecenter.channel.http.ApiRequestClient.1
                @Override // com.aliyun.alink.business.devicecenter.channel.http.IRequestCallback
                public void onFail(DCError dCError, Object obj) {
                    ALog.i("ApiRequestClient", "onFail() called with: dcError = [" + dCError + "], response = [" + obj + "]");
                    if (ApiRequestClient.this.a(dCError)) {
                        ApiRequestClient.this.f10287e.incrementAndGet();
                    } else {
                        ApiRequestClient.this.f10286d.incrementAndGet();
                    }
                    ApiRequestClient apiRequestClient = ApiRequestClient.this;
                    apiRequestClient.a(iRequest, cls, apiRequestClient.f10291i, dCError, obj);
                }

                @Override // com.aliyun.alink.business.devicecenter.channel.http.IRequestCallback
                public void onSuccess(final Object obj) {
                    ALog.i("ApiRequestClient", "onSuccess() called with: data = [" + obj + "]");
                    ThreadTools.runOnUiThread(new Runnable() { // from class: com.aliyun.alink.business.devicecenter.channel.http.ApiRequestClient.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (ApiRequestClient.this.f10291i != null) {
                                ApiRequestClient.this.f10291i.onSuccess(obj);
                            }
                        }
                    });
                }
            };
        }
        this.f10283a.send(iRequest, cls, this.f10290h);
    }

    public final boolean a(DCError dCError) {
        Throwable th;
        return (dCError == null || (th = dCError.throwable) == null || th.toString() == null || !dCError.throwable.toString().contains("UnknownHostException")) ? false : true;
    }

    public final void a(final IRequest iRequest, final Class<?> cls, final IRequestCallback iRequestCallback, final DCError dCError, final Object obj) {
        ALog.d("ApiRequestClient", "retryCount=" + this.f10286d.get() + ", retryDnsCount" + this.f10287e.get() + ", needRetrySend" + this.f10289g.get() + ", stopRetry=" + this.f10288f.get());
        if (this.f10288f.get()) {
            ALog.w("ApiRequestClient", "stopRetry called.");
            return;
        }
        if (this.f10289g.get() && this.f10286d.get() < 3 && this.f10287e.get() < 10) {
            if (this.f10288f.get()) {
                ALog.w("ApiRequestClient", "stopRetry called. sleep");
                return;
            } else {
                ThreadTools.submitTask(new Runnable() { // from class: com.aliyun.alink.business.devicecenter.channel.http.ApiRequestClient.2
                    @Override // java.lang.Runnable
                    public void run() {
                        ApiRequestClient.this.send(iRequest, cls, iRequestCallback);
                    }
                }, false, 1000);
                return;
            }
        }
        ALog.d("ApiRequestClient", "callback return, current retryCount=" + this.f10286d.get() + ", retryDnsCount" + this.f10287e.get());
        ThreadTools.runOnUiThread(new Runnable() { // from class: com.aliyun.alink.business.devicecenter.channel.http.ApiRequestClient.3
            @Override // java.lang.Runnable
            public void run() {
                IRequestCallback iRequestCallback2 = iRequestCallback;
                if (iRequestCallback2 != null) {
                    iRequestCallback2.onFail(dCError, obj);
                }
            }
        });
    }
}
