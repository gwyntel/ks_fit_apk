package anet.channel.fulltrace;

import anet.channel.statist.RequestStatistic;

/* loaded from: classes2.dex */
public interface IFullTraceAnalysis {
    void commitRequest(String str, RequestStatistic requestStatistic);

    String createRequest();

    b getSceneInfo();
}
