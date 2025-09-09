package com.aliyun.alink.linksdk.tmp.device.a.b;

import android.text.TextUtils;
import com.aliyun.alink.linksdk.tmp.api.DeviceBasicData;
import com.aliyun.alink.linksdk.tmp.connect.d;
import com.aliyun.alink.linksdk.tmp.connect.e;
import com.aliyun.alink.linksdk.tmp.devicemodel.DeviceModel;
import com.aliyun.alink.linksdk.tmp.event.INotifyHandler;
import com.aliyun.alink.linksdk.tmp.listener.IEventListener;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class c extends b {

    /* renamed from: q, reason: collision with root package name */
    protected static final String f11267q = "SubscribEventTask";

    /* renamed from: r, reason: collision with root package name */
    protected IEventListener f11268r;

    public c(com.aliyun.alink.linksdk.tmp.device.a aVar, DeviceBasicData deviceBasicData, DeviceModel deviceModel, IEventListener iEventListener, Object obj) {
        super(aVar, deviceBasicData, iEventListener);
        a(deviceModel);
        a(obj);
        this.f11268r = iEventListener;
        a(deviceBasicData);
        a(aVar);
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.a.b.b
    /* renamed from: b, reason: merged with bridge method [inline-methods] */
    public c a(INotifyHandler iNotifyHandler) {
        this.f11264o = iNotifyHandler;
        return this;
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.a.b.b
    /* renamed from: b, reason: merged with bridge method [inline-methods] */
    public c a(String str) {
        this.f11265p = str;
        return this;
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.a.a
    /* renamed from: b, reason: merged with bridge method [inline-methods] */
    public void a(d dVar, e eVar) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        b();
        super.a((c) dVar, (d) eVar);
    }

    protected boolean b() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        com.aliyun.alink.linksdk.tmp.device.a aVar = this.f11293h;
        if (aVar != null && !TextUtils.isEmpty(this.f11265p) && this.f11268r != null) {
            aVar.a(this.f11265p, aVar.j(), this.f11268r);
            return true;
        }
        ALog.e(f11267q, "addEventList deviceImpl empty or mEventName empty deviceImpl:" + aVar + " mEventName:" + this.f11265p + " mEventListener:" + this.f11268r);
        return false;
    }
}
