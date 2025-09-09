package com.aliyun.alink.linksdk.alcs.lpbs.a.a;

import com.aliyun.alink.linksdk.alcs.lpbs.component.cloud.IDataDownListener;
import com.aliyun.alink.linksdk.alcs.lpbs.component.cloud.IThingCloudChannel;
import com.aliyun.alink.linksdk.alcs.lpbs.utils.TextHelper;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class b implements IDataDownListener, IThingCloudChannel {

    /* renamed from: a, reason: collision with root package name */
    private static final String f10691a = "[AlcsLPBS]CloudChannelProxy";

    /* renamed from: b, reason: collision with root package name */
    private IThingCloudChannel f10692b;

    public b(IThingCloudChannel iThingCloudChannel, IDataDownListener iDataDownListener) {
        this.f10692b = iThingCloudChannel;
        if (iThingCloudChannel != null) {
            iThingCloudChannel.addDownDataListener(iDataDownListener);
        }
    }

    IThingCloudChannel a() {
        return this.f10692b;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.component.cloud.IThingCloudChannel
    public void addDownDataListener(IDataDownListener iDataDownListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f10691a, "addDownDataListener listener:" + iDataDownListener + " mChannel:" + this.f10692b);
        IThingCloudChannel iThingCloudChannel = this.f10692b;
        if (iThingCloudChannel != null) {
            iThingCloudChannel.addDownDataListener(iDataDownListener);
        }
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.component.cloud.IDataDownListener
    public void onDataDown(String str, byte[] bArr) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f10691a, "onDataDown topic:" + str + " payload hex:" + TextHelper.byte2hex(bArr) + " mChannel:" + this.f10692b);
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.component.cloud.IThingCloudChannel
    public void removeDownDataListener(IDataDownListener iDataDownListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f10691a, "removeDownDataListener listener:" + iDataDownListener + " mChannel:" + this.f10692b);
        IThingCloudChannel iThingCloudChannel = this.f10692b;
        if (iThingCloudChannel != null) {
            iThingCloudChannel.removeDownDataListener(iDataDownListener);
        }
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.component.cloud.IThingCloudChannel
    public void reportData(String str, byte[] bArr) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (this.f10692b == null && bArr == null) {
            ALog.e(f10691a, "reportData topic:" + str + " payload:" + bArr + " mChannel :" + this.f10692b);
            return;
        }
        ALog.d(f10691a, "reportData topic:" + str + " payload hex:" + TextHelper.byte2hex(bArr) + " payload str:" + new String(bArr) + " mChannel:" + this.f10692b);
        this.f10692b.reportData(str, bArr);
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.component.cloud.IThingCloudChannel
    public void reportData(String str, Object obj, IThingCloudChannel.IChannelActionListener iChannelActionListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (this.f10692b == null && obj == null) {
            ALog.e(f10691a, "reportData topic:" + str + " payload:" + obj + " mChannel :" + this.f10692b + " listener:" + iChannelActionListener);
            return;
        }
        ALog.d(f10691a, "reportData topic:" + str + " payload " + obj + " mChannel:" + this.f10692b + " listener:" + iChannelActionListener);
        this.f10692b.reportData(str, obj, iChannelActionListener);
    }
}
