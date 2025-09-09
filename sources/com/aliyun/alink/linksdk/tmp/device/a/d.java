package com.aliyun.alink.linksdk.tmp.device.a;

import com.aliyun.alink.linksdk.tmp.api.DeviceBasicData;
import com.aliyun.alink.linksdk.tmp.config.DeviceConfig;
import com.aliyun.alink.linksdk.tmp.devicemodel.DeviceModel;
import com.aliyun.alink.linksdk.tmp.listener.IDevListener;
import com.aliyun.alink.linksdk.tmp.listener.IDevRawDataListener;
import com.aliyun.alink.linksdk.tmp.utils.ErrorInfo;
import com.aliyun.alink.linksdk.tmp.utils.LogCat;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public abstract class d<Task> extends a<com.aliyun.alink.linksdk.tmp.connect.d, com.aliyun.alink.linksdk.tmp.connect.e> {

    /* renamed from: c, reason: collision with root package name */
    protected static final String f11288c = "[Tmp]DeviceAsyncTask";

    /* renamed from: e, reason: collision with root package name */
    protected Object f11290e;

    /* renamed from: f, reason: collision with root package name */
    protected IDevListener f11291f;

    /* renamed from: g, reason: collision with root package name */
    protected IDevRawDataListener f11292g;

    /* renamed from: h, reason: collision with root package name */
    protected com.aliyun.alink.linksdk.tmp.device.a f11293h;

    /* renamed from: i, reason: collision with root package name */
    protected com.aliyun.alink.linksdk.tmp.connect.b f11294i;

    /* renamed from: j, reason: collision with root package name */
    protected DeviceBasicData f11295j;

    /* renamed from: k, reason: collision with root package name */
    protected DeviceModel f11296k;

    /* renamed from: m, reason: collision with root package name */
    protected DeviceConfig f11298m;

    /* renamed from: d, reason: collision with root package name */
    protected Task f11289d = this;

    /* renamed from: l, reason: collision with root package name */
    protected boolean f11297l = true;

    /* JADX WARN: Multi-variable type inference failed */
    public d(com.aliyun.alink.linksdk.tmp.device.a aVar, IDevListener iDevListener) {
        this.f11293h = aVar;
        this.f11291f = iDevListener;
        if (aVar != null) {
            this.f11294i = aVar.a();
        }
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.a.a
    /* renamed from: b, reason: merged with bridge method [inline-methods] */
    public void a(com.aliyun.alink.linksdk.tmp.connect.d dVar, com.aliyun.alink.linksdk.tmp.connect.e eVar, ErrorInfo errorInfo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        a(dVar, eVar, errorInfo);
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.a.a
    /* renamed from: c, reason: merged with bridge method [inline-methods] */
    public void a(com.aliyun.alink.linksdk.tmp.connect.d dVar, ErrorInfo errorInfo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        b(dVar, errorInfo);
    }

    protected void b(com.aliyun.alink.linksdk.tmp.connect.d dVar, ErrorInfo errorInfo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        IDevListener iDevListener = this.f11291f;
        if (iDevListener == null) {
            LogCat.w(f11288c, "onFlowError empty error");
        } else {
            this.f11291f = null;
            iDevListener.onFail(this.f11290e, errorInfo);
        }
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.a.a
    public boolean a() {
        com.aliyun.alink.linksdk.tmp.device.a aVar = this.f11293h;
        if (aVar == null) {
            return true;
        }
        this.f11294i = aVar.a();
        return true;
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.a.a
    public boolean a(a aVar, com.aliyun.alink.linksdk.tmp.connect.d dVar, com.aliyun.alink.linksdk.tmp.connect.e eVar) {
        com.aliyun.alink.linksdk.tmp.device.a aVar2 = this.f11293h;
        if (aVar2 != null && this.f11294i == null) {
            this.f11294i = aVar2.a();
        }
        return super.a(aVar, (a) dVar, (com.aliyun.alink.linksdk.tmp.connect.d) eVar);
    }

    public Task a(DeviceConfig deviceConfig) {
        this.f11298m = deviceConfig;
        return this.f11289d;
    }

    public Task a(IDevRawDataListener iDevRawDataListener) {
        this.f11292g = iDevRawDataListener;
        return this.f11289d;
    }

    public Task a(boolean z2) {
        this.f11297l = z2;
        return this.f11289d;
    }

    public Task a(DeviceBasicData deviceBasicData) {
        this.f11295j = deviceBasicData;
        return this.f11289d;
    }

    public Task a(com.aliyun.alink.linksdk.tmp.connect.b bVar) {
        this.f11294i = bVar;
        return this.f11289d;
    }

    public Task a(com.aliyun.alink.linksdk.tmp.device.a aVar) {
        this.f11293h = aVar;
        return this.f11289d;
    }

    public Task a(Object obj) {
        this.f11290e = obj;
        return this.f11289d;
    }

    public Task a(IDevListener iDevListener) {
        this.f11291f = iDevListener;
        return this.f11289d;
    }

    public Task a(DeviceModel deviceModel) {
        this.f11296k = deviceModel;
        return this.f11289d;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    protected void a(com.aliyun.alink.linksdk.tmp.connect.d dVar, com.aliyun.alink.linksdk.tmp.connect.e eVar, ErrorInfo errorInfo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        IDevListener iDevListener = this.f11291f;
        if (iDevListener == null) {
            LogCat.e(f11288c, "onFlowComplete handler empty error");
        } else {
            this.f11291f = null;
            iDevListener.onSuccess(this.f11290e, null);
        }
    }
}
