package com.aliyun.alink.linksdk.alcs.lpbs.bridge.a;

import android.text.TextUtils;
import com.aliyun.alink.linksdk.alcs.lpbs.component.jsengine.IJSEngine;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalRspMessage;
import com.aliyun.alink.linksdk.alcs.lpbs.listener.PalMsgListener;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class a implements PalMsgListener {

    /* renamed from: d, reason: collision with root package name */
    private static final String f10766d = "[AlcsLPBS]CustomEventRspMsglistenerWrapper";

    /* renamed from: a, reason: collision with root package name */
    protected PalMsgListener f10767a;

    /* renamed from: b, reason: collision with root package name */
    protected String f10768b;

    /* renamed from: c, reason: collision with root package name */
    protected IJSEngine f10769c;

    public a(PalMsgListener palMsgListener, String str, IJSEngine iJSEngine) {
        this.f10767a = palMsgListener;
        this.f10768b = str;
        this.f10769c = iJSEngine;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.listener.PalMsgListener
    public void onLoad(PalRspMessage palRspMessage) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (palRspMessage == null) {
            ALog.e(f10766d, "onLoad ioTRspMessage or paylod null");
        } else {
            ALog.d(f10766d, "onLoad response code:" + palRspMessage.code + " mJsEngine:" + this.f10769c + " mJsCode isempty:" + TextUtils.isEmpty(this.f10768b));
            String strRawDataToProtocol = (this.f10769c == null || TextUtils.isEmpty(this.f10768b)) ? null : this.f10769c.rawDataToProtocol(this.f10768b, palRspMessage.payload);
            if (!TextUtils.isEmpty(strRawDataToProtocol)) {
                palRspMessage.payload = strRawDataToProtocol.getBytes();
            }
        }
        PalMsgListener palMsgListener = this.f10767a;
        if (palMsgListener != null) {
            palMsgListener.onLoad(palRspMessage);
        }
    }
}
