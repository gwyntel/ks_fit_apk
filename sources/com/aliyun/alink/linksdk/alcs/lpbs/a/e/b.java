package com.aliyun.alink.linksdk.alcs.lpbs.a.e;

import com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect;
import com.aliyun.alink.linksdk.alcs.lpbs.component.cloud.IDataDownListener;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalDeviceInfo;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalReqMessage;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalRspMessage;
import com.aliyun.alink.linksdk.alcs.lpbs.listener.PalMsgListener;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class b implements IDataDownListener {

    /* renamed from: a, reason: collision with root package name */
    private static final String f10713a = "[AlcsLPBS]DataDownListenerProxy";

    /* renamed from: b, reason: collision with root package name */
    private IPalConnect f10714b;

    /* renamed from: c, reason: collision with root package name */
    private PalDeviceInfo f10715c;

    public b(IPalConnect iPalConnect, PalDeviceInfo palDeviceInfo) {
        this.f10714b = iPalConnect;
        this.f10715c = palDeviceInfo;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.component.cloud.IDataDownListener
    public void onDataDown(String str, byte[] bArr) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        StringBuilder sb = new StringBuilder();
        sb.append("onDataDown mConnect:");
        sb.append(this.f10714b);
        sb.append(" topic:");
        sb.append(str);
        sb.append(" payload:");
        sb.append(bArr);
        sb.append(" payloadstr:");
        sb.append(bArr == null ? " null" : new String(bArr));
        ALog.d(f10713a, sb.toString());
        if (this.f10714b != null) {
            PalReqMessage palReqMessage = new PalReqMessage();
            palReqMessage.topic = str;
            palReqMessage.payload = bArr;
            palReqMessage.deviceInfo = this.f10715c;
            this.f10714b.asyncSendRequest(palReqMessage, new PalMsgListener() { // from class: com.aliyun.alink.linksdk.alcs.lpbs.a.e.b.1
                @Override // com.aliyun.alink.linksdk.alcs.lpbs.listener.PalMsgListener
                public void onLoad(PalRspMessage palRspMessage) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("onDataDown asyncSendRequest onLoad code:");
                    sb2.append(palRspMessage == null ? "error" : Integer.valueOf(palRspMessage.code));
                    ALog.d(b.f10713a, sb2.toString());
                }
            });
        }
    }
}
