package anet.channel.statist;

import anet.channel.status.NetworkStatusHelper;

@Monitor(module = "networkPrefer", monitorPoint = "policyVersion")
/* loaded from: classes2.dex */
public class PolicyVersionStat extends StatObject {

    @Dimension
    public String host;

    @Dimension
    public int reportType;

    @Dimension
    public int version;

    @Dimension
    public String netType = NetworkStatusHelper.getNetworkSubType();

    @Dimension
    public String mnc = NetworkStatusHelper.getSimOp();

    public PolicyVersionStat(String str, int i2) {
        this.host = str;
        this.version = i2;
    }
}
