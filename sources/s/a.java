package s;

import com.aliyun.alink.business.devicecenter.channel.http.RequestServiceMgr;
import com.aliyun.alink.business.devicecenter.channel.http.services.IActivationRequestService;

/* loaded from: classes2.dex */
public abstract /* synthetic */ class a {
    public static void a(IActivationRequestService iActivationRequestService) {
        RequestServiceMgr.getInstance().registerRequestService("activationRequestService", iActivationRequestService);
    }
}
