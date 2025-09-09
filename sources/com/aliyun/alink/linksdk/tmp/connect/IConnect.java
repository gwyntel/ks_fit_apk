package com.aliyun.alink.linksdk.tmp.connect;

import com.aliyun.alink.linksdk.cmp.core.base.ConnectState;
import com.aliyun.alink.linksdk.tmp.api.OutputParams;
import com.aliyun.alink.linksdk.tmp.data.discovery.DiscoveryConfig;
import com.aliyun.alink.linksdk.tmp.event.INotifyHandler;
import com.aliyun.alink.linksdk.tmp.listener.IDevStateChangeListener;
import com.aliyun.alink.linksdk.tmp.listener.IPublishResourceListener;
import com.aliyun.alink.linksdk.tmp.utils.TmpEnum;

/* loaded from: classes2.dex */
public interface IConnect {

    /* renamed from: b, reason: collision with root package name */
    public static final int f11112b = 1;

    /* renamed from: c, reason: collision with root package name */
    public static final int f11113c = 2;

    public enum TConnectState {
        UNKNOW(0),
        CONNECT(1),
        DISCONNECT(2);

        private int value;

        TConnectState(int i2) {
            this.value = i2;
        }

        public static TConnectState createConnectState(ConnectState connectState) {
            return connectState == ConnectState.CONNECTED ? CONNECT : connectState == ConnectState.DISCONNECTED ? DISCONNECT : UNKNOW;
        }
    }

    TmpEnum.ConnectType a();

    String a(TmpEnum.ConnectType connectType);

    boolean a(int i2, DiscoveryConfig discoveryConfig, INotifyHandler iNotifyHandler);

    boolean a(d dVar, c cVar);

    boolean a(d dVar, c cVar, INotifyHandler iNotifyHandler);

    boolean a(INotifyHandler iNotifyHandler);

    boolean a(IDevStateChangeListener iDevStateChangeListener);

    boolean a(String str, int i2, Object obj);

    boolean a(String str, String str2);

    boolean a(String str, String str2, String str3, OutputParams outputParams, IPublishResourceListener iPublishResourceListener);

    boolean a(String str, String str2, String str3, boolean z2, com.aliyun.alink.linksdk.tmp.resource.b bVar);

    boolean a(String str, String str2, byte[] bArr, IPublishResourceListener iPublishResourceListener);

    boolean b();

    boolean b(d dVar, c cVar);

    boolean b(IDevStateChangeListener iDevStateChangeListener);

    boolean c();

    boolean c(d dVar, c cVar);

    TmpEnum.DeviceState d();

    boolean e();

    boolean f();
}
