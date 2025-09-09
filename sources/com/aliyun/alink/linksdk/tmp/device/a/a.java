package com.aliyun.alink.linksdk.tmp.device.a;

import com.aliyun.alink.linksdk.tmp.utils.ErrorInfo;

/* loaded from: classes2.dex */
public abstract class a<Request, Response> {

    /* renamed from: a, reason: collision with root package name */
    protected static final String f11227a = "[Tmp]AsyncTask";

    /* renamed from: b, reason: collision with root package name */
    protected c<Request, Response> f11228b;

    public abstract void a(Request request, ErrorInfo errorInfo);

    public abstract void a(Request request, Response response, ErrorInfo errorInfo);

    public abstract boolean a();

    public boolean a(a aVar, Request request, Response response) {
        return true;
    }

    public void b(Request request, ErrorInfo errorInfo) {
        c<Request, Response> cVar = this.f11228b;
        if (cVar != null) {
            cVar.a((a) this, (a<Request, Response>) request, errorInfo);
        }
    }

    public void a(c cVar) {
        this.f11228b = cVar;
    }

    public void a(Request request, Response response) {
        c<Request, Response> cVar = this.f11228b;
        if (cVar != null) {
            cVar.a((a) this, (a<Request, Response>) request, (Request) response);
        }
    }
}
