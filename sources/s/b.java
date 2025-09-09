package s;

import com.aliyun.alink.business.devicecenter.channel.http.RequestServiceMgr;
import com.aliyun.alink.business.devicecenter.channel.http.services.IActivationRtosRequestService;

/* loaded from: classes2.dex */
public abstract /* synthetic */ class b {
    public static void a(IActivationRtosRequestService iActivationRtosRequestService) {
        RequestServiceMgr.getInstance().registerRequestService("rtosBindRequestService", iActivationRtosRequestService);
    }
}
