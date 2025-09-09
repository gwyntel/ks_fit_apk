package anetwork.channel.stat;

import anetwork.channel.statist.StatisticData;

/* loaded from: classes2.dex */
public interface INetworkStat {
    String get(String str);

    void put(String str, StatisticData statisticData);

    void reset(String str);
}
