package com.aliyun.alink.linksdk.tmp.device.a;

import com.aliyun.alink.linksdk.tmp.listener.IDevListener;
import com.aliyun.alink.linksdk.tmp.utils.ErrorInfo;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public abstract class e<Task> extends a<com.aliyun.alink.linksdk.tmp.connect.d, com.aliyun.alink.linksdk.tmp.connect.e> {

    /* renamed from: c, reason: collision with root package name */
    protected WeakReference<com.aliyun.alink.linksdk.tmp.device.b> f11326c;

    /* renamed from: d, reason: collision with root package name */
    protected IDevListener f11327d;

    /* renamed from: e, reason: collision with root package name */
    protected Task f11328e = this;

    /* JADX WARN: Multi-variable type inference failed */
    public e(com.aliyun.alink.linksdk.tmp.device.b bVar, IDevListener iDevListener) {
        this.f11326c = new WeakReference<>(bVar);
        this.f11327d = iDevListener;
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.a.a
    /* renamed from: b, reason: merged with bridge method [inline-methods] */
    public void a(com.aliyun.alink.linksdk.tmp.connect.d dVar, ErrorInfo errorInfo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        c(dVar, errorInfo);
    }

    protected void c(com.aliyun.alink.linksdk.tmp.connect.d dVar, ErrorInfo errorInfo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        IDevListener iDevListener = this.f11327d;
        if (iDevListener == null) {
            ALog.w("[Tmp]AsyncTask", "onFlowError empty error");
        } else {
            this.f11327d = null;
            iDevListener.onFail(null, errorInfo);
        }
    }

    protected void b(com.aliyun.alink.linksdk.tmp.connect.d dVar, com.aliyun.alink.linksdk.tmp.connect.e eVar, ErrorInfo errorInfo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        IDevListener iDevListener = this.f11327d;
        if (iDevListener == null) {
            ALog.e("[Tmp]AsyncTask", "onFlowComplete handler empty error");
        } else {
            this.f11327d = null;
            iDevListener.onSuccess(null, null);
        }
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.a.a
    public void a(com.aliyun.alink.linksdk.tmp.connect.d dVar, com.aliyun.alink.linksdk.tmp.connect.e eVar, ErrorInfo errorInfo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        b(dVar, eVar, errorInfo);
    }
}
