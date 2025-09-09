package com.aliyun.alink.linksdk.alcs.lpbs.a.e;

import com.aliyun.alink.linksdk.alcs.lpbs.data.PalDeviceInfo;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalRspMessage;
import com.aliyun.alink.linksdk.alcs.lpbs.listener.PalMsgListener;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class h implements PalMsgListener {

    /* renamed from: a, reason: collision with root package name */
    private static final String f10748a = "[AlcsLPBS]PkDnChangeListener";

    /* renamed from: b, reason: collision with root package name */
    private PalMsgListener f10749b;

    /* renamed from: c, reason: collision with root package name */
    private PalDeviceInfo f10750c;

    public h(PalDeviceInfo palDeviceInfo, PalMsgListener palMsgListener) {
        this.f10749b = palMsgListener;
        this.f10750c = palDeviceInfo;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.listener.PalMsgListener
    public void onLoad(PalRspMessage palRspMessage) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        StringBuilder sb = new StringBuilder();
        sb.append("onLoad response code:");
        sb.append(palRspMessage != null ? String.valueOf(palRspMessage.code) : " response null");
        ALog.d(f10748a, sb.toString());
        PalMsgListener palMsgListener = this.f10749b;
        if (palMsgListener != null) {
            palMsgListener.onLoad(palRspMessage);
        }
    }
}
