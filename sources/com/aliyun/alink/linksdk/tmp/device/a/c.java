package com.aliyun.alink.linksdk.tmp.device.a;

import android.os.AsyncTask;
import com.aliyun.alink.linksdk.tmp.utils.ErrorInfo;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public class c<Request, Response> {

    /* renamed from: a, reason: collision with root package name */
    protected static final String f11269a = "[Tmp]AsyncTaskFlow";

    /* renamed from: d, reason: collision with root package name */
    protected static Executor f11270d;

    /* renamed from: b, reason: collision with root package name */
    protected List<a> f11271b = new LinkedList();

    /* renamed from: c, reason: collision with root package name */
    protected a<Request, Response> f11272c;

    public c() {
        if (f11270d == null) {
            f11270d = AsyncTask.THREAD_POOL_EXECUTOR;
        }
    }

    public void a(a aVar, Request request, Response response) {
        b(aVar, request, response);
    }

    protected void b(a aVar, Request request, Response response) {
        a<Request, Response> aVarA = a(aVar);
        if (aVarA == null) {
            a((c<Request, Response>) request, (Request) response, new ErrorInfo(300, "task flow error"));
            return;
        }
        a<Request, Response> aVar2 = this.f11272c;
        if (aVarA == aVar2) {
            return;
        }
        try {
            this.f11272c = aVarA;
            aVarA.a((a) aVar2, (a<Request, Response>) request, (Request) response);
            f11270d.execute(new Runnable() { // from class: com.aliyun.alink.linksdk.tmp.device.a.c.2
                @Override // java.lang.Runnable
                public void run() {
                    c.this.f11272c.a();
                }
            });
        } catch (Exception e2) {
            this.f11272c.b(request, new ErrorInfo(4001, e2.toString()));
        }
    }

    public void a(a aVar, Request request, ErrorInfo errorInfo) {
        a<Request, Response> aVar2 = this.f11272c;
        if (aVar2 == null || aVar2 != aVar) {
            return;
        }
        aVar2.a((a<Request, Response>) request, errorInfo);
    }

    public boolean a() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (this.f11271b.isEmpty()) {
            return false;
        }
        a<Request, Response> aVar = this.f11271b.get(0);
        this.f11272c = aVar;
        aVar.a((a) null, (a) null, (Request) null);
        try {
            f11270d.execute(new Runnable() { // from class: com.aliyun.alink.linksdk.tmp.device.a.c.1
                @Override // java.lang.Runnable
                public void run() {
                    c.this.f11272c.a();
                }
            });
            return true;
        } catch (Exception e2) {
            ALog.e(f11269a, "action error:" + e2.toString());
            return true;
        }
    }

    public void a(Request request, Response response, ErrorInfo errorInfo) {
        a<Request, Response> aVar = this.f11272c;
        if (aVar != null) {
            aVar.a((a<Request, Response>) request, (Request) response, errorInfo);
        }
    }

    public c b(a aVar) {
        aVar.a(this);
        this.f11271b.add(aVar);
        return this;
    }

    protected a a(a aVar) {
        a<Request, Response> aVar2 = this.f11272c;
        if (aVar != aVar2) {
            return aVar2;
        }
        int iIndexOf = this.f11271b.indexOf(aVar2) + 1;
        if (iIndexOf >= this.f11271b.size()) {
            return null;
        }
        return this.f11271b.get(iIndexOf);
    }
}
