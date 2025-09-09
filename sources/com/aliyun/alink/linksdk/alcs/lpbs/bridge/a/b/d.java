package com.aliyun.alink.linksdk.alcs.lpbs.bridge.a.b;

import com.aliyun.alink.linksdk.alcs.api.ICAConnectListener;
import com.aliyun.alink.linksdk.alcs.api.ICAMsgListener;
import com.aliyun.alink.linksdk.alcs.data.ica.ICAAuthServerParams;
import com.aliyun.alink.linksdk.alcs.data.ica.ICADiscoveryDeviceInfo;

/* loaded from: classes2.dex */
public interface d {

    /* renamed from: a, reason: collision with root package name */
    public static final String f10792a = "ServerAuthInfo";

    /* renamed from: b, reason: collision with root package name */
    public static final String f10793b = "core.service.setup";

    /* renamed from: c, reason: collision with root package name */
    public static final String f10794c = "1.0";

    void a();

    void a(ICADiscoveryDeviceInfo iCADiscoveryDeviceInfo, ICAConnectListener iCAConnectListener);

    boolean a(ICAAuthServerParams iCAAuthServerParams, ICAMsgListener iCAMsgListener);
}
