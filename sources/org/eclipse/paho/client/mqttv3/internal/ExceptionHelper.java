package org.eclipse.paho.client.mqttv3.internal;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

/* loaded from: classes5.dex */
public class ExceptionHelper {
    private ExceptionHelper() {
    }

    public static MqttException createMqttException(int i2) {
        return (i2 == 4 || i2 == 5) ? new MqttSecurityException(i2) : new MqttException(i2);
    }

    public static boolean isClassAvailable(String str) throws ClassNotFoundException {
        try {
            Class.forName(str);
            return true;
        } catch (ClassNotFoundException unused) {
            return false;
        }
    }

    public static MqttException createMqttException(Throwable th) {
        if (th.getClass().getName().equals("java.security.GeneralSecurityException")) {
            return new MqttSecurityException(th);
        }
        return new MqttException(th);
    }
}
