package com.aliyun.alink.linksdk.alcs.lpbs.bridge.a;

import com.aliyun.alink.linksdk.alcs.api.ICAConnectListener;
import com.aliyun.alink.linksdk.alcs.api.ICAMsgListener;
import com.aliyun.alink.linksdk.alcs.data.ica.ICAAuthPairs;
import com.aliyun.alink.linksdk.alcs.data.ica.ICAAuthParams;
import com.aliyun.alink.linksdk.alcs.data.ica.ICADeviceInfo;
import com.aliyun.alink.linksdk.alcs.data.ica.ICADiscoveryDeviceInfo;
import com.aliyun.alink.linksdk.alcs.data.ica.ICARspMessage;
import com.aliyun.alink.linksdk.alcs.lpbs.component.auth.IAuthProvider;
import com.aliyun.alink.linksdk.alcs.lpbs.component.auth.IAuthProviderListener;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalDeviceInfo;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class j implements IAuthProvider {

    /* renamed from: c, reason: collision with root package name */
    private static final String f10824c = "[AlcsLPBS]ICALocalAuthProvider";

    /* renamed from: a, reason: collision with root package name */
    protected com.aliyun.alink.linksdk.alcs.lpbs.bridge.a.b.d f10825a = new com.aliyun.alink.linksdk.alcs.lpbs.bridge.a.b.e();

    /* renamed from: b, reason: collision with root package name */
    protected com.aliyun.alink.linksdk.alcs.lpbs.bridge.a.b.f f10826b;

    public j(com.aliyun.alink.linksdk.alcs.lpbs.bridge.a.b.f fVar) {
        this.f10826b = fVar;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.component.auth.IAuthProvider
    public void queryAuthInfo(final PalDeviceInfo palDeviceInfo, String str, Object obj, final IAuthProviderListener iAuthProviderListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        com.aliyun.alink.linksdk.alcs.lpbs.bridge.a.b.f fVar;
        if (iAuthProviderListener == null) {
            ALog.e(f10824c, "queryAuthInfo listener null");
            return;
        }
        if (palDeviceInfo == null || obj == null || (fVar = this.f10826b) == null) {
            ALog.e(f10824c, "queryAuthInfo palDeviceInfo or params or mStoragenull");
            iAuthProviderListener.onComplete(palDeviceInfo, null);
            return;
        }
        ICAAuthParams iCAAuthParamsA = fVar.a(palDeviceInfo.getDevId());
        ALog.d(f10824c, "mStorage getAccessInfo icaAuthParams:" + iCAAuthParamsA);
        if (iCAAuthParamsA != null) {
            iAuthProviderListener.onComplete(palDeviceInfo, iCAAuthParamsA);
        } else {
            this.f10825a.a((ICADiscoveryDeviceInfo) obj, new ICAConnectListener() { // from class: com.aliyun.alink.linksdk.alcs.lpbs.bridge.a.j.1
                @Override // com.aliyun.alink.linksdk.alcs.api.ICAConnectListener
                public void onLoad(int i2, String str2, ICADeviceInfo iCADeviceInfo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    if (i2 != 200) {
                        iAuthProviderListener.onComplete(palDeviceInfo, null);
                    } else {
                        final ICAAuthPairs iCAAuthPairsA = com.aliyun.alink.linksdk.alcs.lpbs.bridge.a.b.b.a("0");
                        j.this.f10825a.a(iCAAuthPairsA.authServerParams, new ICAMsgListener() { // from class: com.aliyun.alink.linksdk.alcs.lpbs.bridge.a.j.1.1
                            @Override // com.aliyun.alink.linksdk.alcs.api.ICAMsgListener
                            public void onLoad(ICARspMessage iCARspMessage) {
                                if (iCARspMessage == null || iCARspMessage.code != 0) {
                                    j.this.f10825a.a();
                                    AnonymousClass1 anonymousClass1 = AnonymousClass1.this;
                                    iAuthProviderListener.onComplete(palDeviceInfo, null);
                                    return;
                                }
                                AnonymousClass1 anonymousClass12 = AnonymousClass1.this;
                                com.aliyun.alink.linksdk.alcs.lpbs.bridge.a.b.f fVar2 = j.this.f10826b;
                                String devId = palDeviceInfo.getDevId();
                                ICAAuthParams iCAAuthParams = iCAAuthPairsA.authParams;
                                fVar2.a(devId, iCAAuthParams.accessKey, iCAAuthParams.accessToken);
                                j.this.f10825a.a();
                                AnonymousClass1 anonymousClass13 = AnonymousClass1.this;
                                iAuthProviderListener.onComplete(palDeviceInfo, iCAAuthPairsA.authParams);
                            }
                        });
                    }
                }
            });
        }
    }
}
