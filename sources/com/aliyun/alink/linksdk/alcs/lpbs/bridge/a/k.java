package com.aliyun.alink.linksdk.alcs.lpbs.bridge.a;

import com.aliyun.alink.linksdk.alcs.api.ICAMsgListener;
import com.aliyun.alink.linksdk.alcs.data.ica.ICARspMessage;
import com.aliyun.alink.linksdk.alcs.lpbs.listener.PalMsgListener;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class k implements ICAMsgListener {

    /* renamed from: b, reason: collision with root package name */
    private static final String f10832b = "[AlcsLPBS]ICAMsgListenerWrapper";

    /* renamed from: a, reason: collision with root package name */
    protected PalMsgListener f10833a;

    public k(PalMsgListener palMsgListener) {
        this.f10833a = palMsgListener;
    }

    @Override // com.aliyun.alink.linksdk.alcs.api.ICAMsgListener
    public void onLoad(ICARspMessage iCARspMessage) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        StringBuilder sb = new StringBuilder();
        sb.append("response code:");
        sb.append(iCARspMessage != null ? String.valueOf(iCARspMessage.code) : "response null");
        ALog.d(f10832b, sb.toString());
        PalMsgListener palMsgListener = this.f10833a;
        if (palMsgListener != null) {
            palMsgListener.onLoad(m.a(iCARspMessage));
        }
    }
}
