package anet.channel.statist;

import anet.channel.GlobalAppRuntimeInfo;
import anet.channel.status.NetworkStatusHelper;

@Monitor(module = "networkPrefer", monitorPoint = "amdc")
/* loaded from: classes2.dex */
public class AmdcStatistic extends StatObject {

    @Dimension
    public String errorCode;

    @Dimension
    public String errorMsg;

    @Dimension
    public String host;

    @Dimension
    public int retryTimes;

    @Dimension
    public String trace;

    @Dimension
    public String url;

    @Dimension
    public String netType = NetworkStatusHelper.getStatus().toString();

    @Dimension
    public String proxyType = NetworkStatusHelper.getProxyType();

    @Dimension
    public String ttid = GlobalAppRuntimeInfo.getTtid();
}
