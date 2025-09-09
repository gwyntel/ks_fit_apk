package s;

import com.aliyun.alink.business.devicecenter.channel.http.RequestServiceMgr;
import com.aliyun.alink.business.devicecenter.channel.http.services.IStaticBindRequestService;

/* loaded from: classes2.dex */
public abstract /* synthetic */ class c {
    public static void a(IStaticBindRequestService iStaticBindRequestService) {
        RequestServiceMgr.getInstance().registerRequestService("staticBindRequestService", iStaticBindRequestService);
    }
}
