package com.aliyun.alink.linksdk.tmp.connect.entity.cmp;

import android.text.TextUtils;
import android.util.Log;
import com.aliyun.alink.linksdk.cmp.core.base.AMessage;
import com.aliyun.alink.linksdk.cmp.core.base.AResponse;
import com.aliyun.alink.linksdk.cmp.core.base.ConnectState;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectNotifyListener;
import com.aliyun.alink.linksdk.tmp.event.INotifyHandler;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class g extends com.aliyun.alink.linksdk.tmp.connect.f implements IConnectNotifyListener {

    /* renamed from: g, reason: collision with root package name */
    protected static final String f11166g = "[Tmp]CpNotifyHandler";

    /* renamed from: h, reason: collision with root package name */
    protected String f11167h;

    /* renamed from: i, reason: collision with root package name */
    protected com.aliyun.alink.linksdk.tmp.connect.d f11168i;

    public g(String str, com.aliyun.alink.linksdk.tmp.connect.d dVar, INotifyHandler iNotifyHandler) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        super(iNotifyHandler);
        this.f11167h = str;
        this.f11168i = dVar;
        a(dVar);
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectNotifyListener
    public void onConnectStateChange(String str, ConnectState connectState) {
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectNotifyListener
    public void onNotify(String str, String str2, AMessage aMessage) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        AResponse aResponse = new AResponse();
        aResponse.data = aMessage.data;
        i iVar = new i(aResponse);
        ALog.d(f11166g, "onNotify data:" + String.valueOf(aMessage.data));
        super.onMessage(this.f11168i, iVar);
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectNotifyListener
    public boolean shouldHandle(String str, String str2) {
        Log.d(f11166g, "shouldHandle connetId:" + str + " topic:" + str2 + " mConnectedId:" + this.f11167h + " requtopic:" + this.f11168i.d());
        return !TextUtils.isEmpty(str) && str.equalsIgnoreCase(this.f11167h) && !TextUtils.isEmpty(str2) && str2.equalsIgnoreCase(this.f11168i.d());
    }
}
