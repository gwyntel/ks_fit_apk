package anet.channel.appmonitor;

import anet.channel.statist.AlarmObject;
import anet.channel.statist.CountObject;
import anet.channel.statist.StatObject;

/* loaded from: classes2.dex */
public interface IAppMonitor {
    void commitAlarm(AlarmObject alarmObject);

    void commitCount(CountObject countObject);

    void commitStat(StatObject statObject);

    @Deprecated
    void register();

    @Deprecated
    void register(Class<?> cls);
}
