package org.eclipse.paho.client.mqttv3.logging;

import java.lang.reflect.InvocationTargetException;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/* loaded from: classes5.dex */
public class LoggerFactory {
    private static final String CLASS_NAME = "org.eclipse.paho.client.mqttv3.logging.LoggerFactory";
    public static final String MQTT_CLIENT_MSG_CAT = "org.eclipse.paho.client.mqttv3.internal.nls.logcat";
    private static String jsr47LoggerClassName = JSR47Logger.class.getName();
    private static String overrideloggerClassName;

    public static Logger getLogger(String str, String str2) {
        String str3 = overrideloggerClassName;
        if (str3 == null) {
            str3 = jsr47LoggerClassName;
        }
        Logger logger = getLogger(str3, ResourceBundle.getBundle(str), str2, null);
        if (logger != null) {
            return logger;
        }
        throw new MissingResourceException("Error locating the logging class", CLASS_NAME, str2);
    }

    public static String getLoggingProperty(String str) throws IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        try {
            Class<?> cls = Class.forName("java.util.logging.LogManager");
            return (String) cls.getMethod("getProperty", String.class).invoke(cls.getMethod("getLogManager", null).invoke(null, null), str);
        } catch (Exception unused) {
            return null;
        }
    }

    public static void setLogger(String str) {
        overrideloggerClassName = str;
    }

    private static Logger getLogger(String str, ResourceBundle resourceBundle, String str2, String str3) {
        try {
            Logger logger = (Logger) Class.forName(str).newInstance();
            logger.initialise(resourceBundle, str2, str3);
            return logger;
        } catch (ClassNotFoundException | ExceptionInInitializerError | IllegalAccessException | InstantiationException | NoClassDefFoundError | SecurityException unused) {
            return null;
        }
    }
}
