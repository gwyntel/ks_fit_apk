package com.aliyun.alink.linksdk.alcs.lpbs.bridge.a;

import android.text.TextUtils;
import com.aliyun.alink.linksdk.alcs.lpbs.component.jsengine.IJSEngine;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalRspMessage;
import com.aliyun.alink.linksdk.alcs.lpbs.listener.PalMsgListener;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class b implements PalMsgListener {

    /* renamed from: d, reason: collision with root package name */
    private static final String f10771d = "[AlcsLPBS]CustomFormatMsgListenerWrapper";

    /* renamed from: a, reason: collision with root package name */
    protected PalMsgListener f10772a;

    /* renamed from: b, reason: collision with root package name */
    protected String f10773b;

    /* renamed from: c, reason: collision with root package name */
    protected IJSEngine f10774c;

    public b(PalMsgListener palMsgListener, String str, IJSEngine iJSEngine) {
        this.f10772a = palMsgListener;
        this.f10773b = str;
        this.f10774c = iJSEngine;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.listener.PalMsgListener
    public void onLoad(PalRspMessage palRspMessage) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (palRspMessage == null) {
            ALog.e(f10771d, "onLoad ioTRspMessage or paylod null");
        } else {
            ALog.d(f10771d, "onLoad response code:" + palRspMessage.code + "  mJsEngine:" + this.f10774c);
            IJSEngine iJSEngine = this.f10774c;
            String strRawDataToProtocol = iJSEngine != null ? iJSEngine.rawDataToProtocol(this.f10773b, palRspMessage.payload) : null;
            if (!TextUtils.isEmpty(strRawDataToProtocol)) {
                palRspMessage.payload = strRawDataToProtocol.getBytes();
            }
        }
        PalMsgListener palMsgListener = this.f10772a;
        if (palMsgListener != null) {
            palMsgListener.onLoad(palRspMessage);
        }
    }
}
