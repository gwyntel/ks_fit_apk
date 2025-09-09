package com.aliyun.alink.linksdk.alcs.lpbs.a.e;

import com.aliyun.alink.linksdk.alcs.lpbs.api.PluginMgr;
import com.aliyun.alink.linksdk.alcs.lpbs.component.cloud.IThingCloudChannel;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalDeviceInfo;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalRspMessage;
import com.aliyun.alink.linksdk.alcs.lpbs.listener.PalMsgListener;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class m implements PalMsgListener {

    /* renamed from: a, reason: collision with root package name */
    private static final String f10761a = "[AlcsLPBS]UpToCloud";

    /* renamed from: b, reason: collision with root package name */
    private PalMsgListener f10762b;

    /* renamed from: c, reason: collision with root package name */
    private String f10763c;

    /* renamed from: d, reason: collision with root package name */
    private IThingCloudChannel f10764d;

    /* renamed from: e, reason: collision with root package name */
    private PalDeviceInfo f10765e;

    public m(PalDeviceInfo palDeviceInfo, IThingCloudChannel iThingCloudChannel, String str, PalMsgListener palMsgListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f10761a, "UpToCloud cloudChannel:" + iThingCloudChannel + " topic:" + iThingCloudChannel + " listener:" + palMsgListener);
        this.f10762b = palMsgListener;
        this.f10763c = str;
        this.f10764d = iThingCloudChannel;
        this.f10765e = palDeviceInfo;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.listener.PalMsgListener
    public void onLoad(PalRspMessage palRspMessage) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f10761a, "onLoad mCloudChannel:" + this.f10764d + " mListener:" + this.f10762b + " topic:" + this.f10763c + " response:" + palRspMessage);
        if (this.f10764d != null && PluginMgr.getInstance().isDataToCloud(this.f10765e)) {
            this.f10764d.reportData(this.f10763c, palRspMessage.payload);
        }
        PalMsgListener palMsgListener = this.f10762b;
        if (palMsgListener != null) {
            palMsgListener.onLoad(palRspMessage);
        }
    }
}
