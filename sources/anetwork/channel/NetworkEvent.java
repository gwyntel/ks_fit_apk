package anetwork.channel;

import anetwork.channel.statist.StatisticData;

/* loaded from: classes2.dex */
public class NetworkEvent {

    public interface FinishEvent {
        String getDesc();

        int getHttpCode();

        StatisticData getStatisticData();
    }

    public interface ProgressEvent {
        byte[] getBytedata();

        String getDesc();

        int getIndex();

        int getSize();

        int getTotal();
    }
}
