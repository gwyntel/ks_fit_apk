package com.aliyun.alink.linksdk.tmp.connect;

import android.os.Looper;
import android.os.Message;
import com.aliyun.alink.linksdk.tmp.event.INotifyHandler;
import com.aliyun.alink.linksdk.tmp.utils.ErrorInfo;
import com.aliyun.alink.linksdk.tmp.utils.LogCat;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class f implements com.aliyun.alink.linksdk.tmp.connect.c, INotifyHandler {

    /* renamed from: a, reason: collision with root package name */
    protected static final String f11182a = "[Tmp]TmpSyncRequestHandler";

    /* renamed from: e, reason: collision with root package name */
    public static com.aliyun.alink.linksdk.tmp.device.a.f f11183e;

    /* renamed from: b, reason: collision with root package name */
    protected com.aliyun.alink.linksdk.tmp.connect.c f11184b;

    /* renamed from: c, reason: collision with root package name */
    protected INotifyHandler f11185c;

    /* renamed from: d, reason: collision with root package name */
    protected com.aliyun.alink.linksdk.tmp.connect.d f11186d;

    /* renamed from: f, reason: collision with root package name */
    protected b f11187f;

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        public com.aliyun.alink.linksdk.tmp.connect.c f11188a;

        /* renamed from: b, reason: collision with root package name */
        public com.aliyun.alink.linksdk.tmp.connect.d f11189b;

        /* renamed from: c, reason: collision with root package name */
        public ErrorInfo f11190c;

        /* renamed from: d, reason: collision with root package name */
        public b f11191d;

        public a(com.aliyun.alink.linksdk.tmp.connect.c cVar, b bVar, com.aliyun.alink.linksdk.tmp.connect.d dVar, ErrorInfo errorInfo) {
            this.f11188a = cVar;
            this.f11189b = dVar;
            this.f11190c = errorInfo;
            this.f11191d = bVar;
        }
    }

    public static abstract class b implements com.aliyun.alink.linksdk.tmp.connect.c {
    }

    public static class c {

        /* renamed from: a, reason: collision with root package name */
        public com.aliyun.alink.linksdk.tmp.connect.c f11192a;

        /* renamed from: b, reason: collision with root package name */
        public com.aliyun.alink.linksdk.tmp.connect.d f11193b;

        /* renamed from: c, reason: collision with root package name */
        public e f11194c;

        /* renamed from: d, reason: collision with root package name */
        public b f11195d;

        public c(com.aliyun.alink.linksdk.tmp.connect.c cVar, b bVar, com.aliyun.alink.linksdk.tmp.connect.d dVar, e eVar) {
            this.f11192a = cVar;
            this.f11193b = dVar;
            this.f11194c = eVar;
            this.f11195d = bVar;
        }
    }

    public static class d {

        /* renamed from: a, reason: collision with root package name */
        public INotifyHandler f11196a;

        /* renamed from: b, reason: collision with root package name */
        public com.aliyun.alink.linksdk.tmp.connect.d f11197b;

        /* renamed from: c, reason: collision with root package name */
        public e f11198c;

        /* renamed from: d, reason: collision with root package name */
        public b f11199d;

        public d(INotifyHandler iNotifyHandler, b bVar, com.aliyun.alink.linksdk.tmp.connect.d dVar, e eVar) {
            this.f11196a = iNotifyHandler;
            this.f11197b = dVar;
            this.f11198c = eVar;
            this.f11199d = bVar;
        }
    }

    public f(com.aliyun.alink.linksdk.tmp.connect.c cVar, b bVar, com.aliyun.alink.linksdk.tmp.connect.d dVar) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.f11184b = cVar;
        this.f11186d = dVar;
        this.f11187f = bVar;
        if (b()) {
            LogCat.d(f11182a, "TmpSyncRequestHandler multhead callback");
        } else {
            a();
        }
    }

    public static synchronized void a() {
        if (f11183e == null) {
            f11183e = new com.aliyun.alink.linksdk.tmp.device.a.f(Looper.getMainLooper());
        }
    }

    protected boolean b() {
        com.aliyun.alink.linksdk.tmp.connect.d dVar = this.f11186d;
        return dVar != null && dVar.a();
    }

    @Override // com.aliyun.alink.linksdk.tmp.event.INotifyHandler
    public void onMessage(com.aliyun.alink.linksdk.tmp.connect.d dVar, e eVar) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11182a, "onMessage ");
        if (this.f11185c == null) {
            LogCat.e(f11182a, "onMessage handler empty");
            return;
        }
        if (b()) {
            LogCat.d(f11182a, "onMessage mulcallback");
            this.f11185c.onMessage(this.f11186d, eVar);
            return;
        }
        LogCat.d(f11182a, "onMessage mainthreadcallback");
        Message messageObtain = Message.obtain();
        messageObtain.what = 3;
        messageObtain.obj = new d(this.f11185c, this.f11187f, this.f11186d, eVar);
        f11183e.sendMessage(messageObtain);
    }

    public f a(com.aliyun.alink.linksdk.tmp.connect.d dVar) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.f11186d = dVar;
        if (b()) {
            LogCat.d(f11182a, "TmpSyncRequestHandler multhead callback");
        } else {
            a();
        }
        return this;
    }

    public f(com.aliyun.alink.linksdk.tmp.connect.c cVar, com.aliyun.alink.linksdk.tmp.connect.d dVar) {
        this(cVar, null, dVar);
    }

    @Override // com.aliyun.alink.linksdk.tmp.connect.c
    public void a(com.aliyun.alink.linksdk.tmp.connect.d dVar, e eVar) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11182a, "onLoad response :" + eVar);
        if (this.f11184b == null) {
            ALog.e(f11182a, "onLoad handler empty");
            return;
        }
        if (b()) {
            ALog.d(f11182a, "onLoad mulcallback");
            this.f11184b.a(this.f11186d, eVar);
            return;
        }
        ALog.d(f11182a, "onLoad mainthreadcallback");
        Message messageObtain = Message.obtain();
        messageObtain.what = 1;
        messageObtain.obj = new c(this.f11184b, this.f11187f, this.f11186d, eVar);
        f11183e.sendMessage(messageObtain);
    }

    public f(INotifyHandler iNotifyHandler, com.aliyun.alink.linksdk.tmp.connect.d dVar) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.f11185c = iNotifyHandler;
        this.f11186d = dVar;
        if (b()) {
            LogCat.d(f11182a, "TmpSyncRequestHandler multhead callback");
        } else {
            a();
        }
    }

    public f(INotifyHandler iNotifyHandler) {
        this(iNotifyHandler, (com.aliyun.alink.linksdk.tmp.connect.d) null);
    }

    @Override // com.aliyun.alink.linksdk.tmp.connect.c
    public void a(com.aliyun.alink.linksdk.tmp.connect.d dVar, ErrorInfo errorInfo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (this.f11184b == null) {
            LogCat.e(f11182a, "onError handler empty");
            return;
        }
        if (b()) {
            LogCat.d(f11182a, "onError mulcallback");
            this.f11184b.a(this.f11186d, errorInfo);
            return;
        }
        LogCat.d(f11182a, "onError mainthreadcallback");
        Message messageObtain = Message.obtain();
        messageObtain.what = 2;
        messageObtain.obj = new a(this.f11184b, this.f11187f, this.f11186d, errorInfo);
        f11183e.sendMessage(messageObtain);
    }
}
