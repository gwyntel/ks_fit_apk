package org.eclipse.paho.client.mqttv3;

import org.eclipse.paho.client.mqttv3.internal.ClientComms;

/* loaded from: classes5.dex */
public interface MqttPingSender {
    void init(ClientComms clientComms);

    void schedule(long j2);

    void start();

    void stop();
}
