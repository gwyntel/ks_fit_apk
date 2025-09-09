package anet.channel.heartbeat;

import anet.channel.Session;

/* loaded from: classes2.dex */
public interface IHeartbeat {
    void reSchedule();

    void start(Session session);

    void stop();
}
