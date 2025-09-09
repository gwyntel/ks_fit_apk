package org.eclipse.paho.client.mqttv3.util;

import com.alipay.sdk.m.p.e;
import java.util.Enumeration;
import java.util.Properties;
import org.eclipse.paho.client.mqttv3.internal.ClientComms;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

/* loaded from: classes5.dex */
public class Debug {
    private static final String CLASS_NAME;
    private static final String lineSep;
    private static final Logger log;
    private static final String separator = "==============";
    private String clientID;
    private ClientComms comms;

    static {
        String name = ClientComms.class.getName();
        CLASS_NAME = name;
        log = LoggerFactory.getLogger(LoggerFactory.MQTT_CLIENT_MSG_CAT, name);
        lineSep = System.getProperty("line.separator", "\n");
    }

    public Debug(String str, ClientComms clientComms) {
        this.clientID = str;
        this.comms = clientComms;
        log.setResourceName(str);
    }

    public static String dumpProperties(Properties properties, String str) {
        StringBuffer stringBuffer = new StringBuffer();
        Enumeration<?> enumerationPropertyNames = properties.propertyNames();
        StringBuilder sb = new StringBuilder();
        String str2 = lineSep;
        sb.append(str2);
        sb.append(separator);
        sb.append(" ");
        sb.append(str);
        sb.append(" ");
        sb.append(separator);
        sb.append(str2);
        stringBuffer.append(sb.toString());
        while (enumerationPropertyNames.hasMoreElements()) {
            String str3 = (String) enumerationPropertyNames.nextElement();
            stringBuffer.append(left(str3, 28, ' ') + ":  " + properties.get(str3) + lineSep);
        }
        stringBuffer.append("==========================================" + lineSep);
        return stringBuffer.toString();
    }

    public static String left(String str, int i2, char c2) {
        if (str.length() >= i2) {
            return str;
        }
        StringBuffer stringBuffer = new StringBuffer(i2);
        stringBuffer.append(str);
        int length = i2 - str.length();
        while (true) {
            length--;
            if (length < 0) {
                return stringBuffer.toString();
            }
            stringBuffer.append(c2);
        }
    }

    protected void a() {
        log.dumpTrace();
    }

    protected void b() {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuilder sb = new StringBuilder();
        String str = lineSep;
        sb.append(str);
        sb.append(separator);
        sb.append(" Version Info ");
        sb.append(separator);
        sb.append(str);
        stringBuffer.append(sb.toString());
        stringBuffer.append(left(e.f9612g, 20, ' ') + ":  " + ClientComms.VERSION + str);
        stringBuffer.append(left("Build Level", 20, ' ') + ":  " + ClientComms.BUILD_LEVEL + str);
        StringBuilder sb2 = new StringBuilder();
        sb2.append("==========================================");
        sb2.append(str);
        stringBuffer.append(sb2.toString());
        log.fine(CLASS_NAME, "dumpVersion", stringBuffer.toString());
    }

    public void dumpBaseDebug() {
        b();
        dumpSystemProperties();
        a();
    }

    public void dumpClientComms() {
        ClientComms clientComms = this.comms;
        if (clientComms != null) {
            Properties debug = clientComms.getDebug();
            log.fine(CLASS_NAME, "dumpClientComms", dumpProperties(debug, this.clientID + " : ClientComms").toString());
        }
    }

    public void dumpClientDebug() {
        dumpClientComms();
        dumpConOptions();
        dumpClientState();
        dumpBaseDebug();
    }

    public void dumpClientState() {
        ClientComms clientComms = this.comms;
        if (clientComms == null || clientComms.getClientState() == null) {
            return;
        }
        Properties debug = this.comms.getClientState().getDebug();
        log.fine(CLASS_NAME, "dumpClientState", dumpProperties(debug, this.clientID + " : ClientState").toString());
    }

    public void dumpConOptions() {
        ClientComms clientComms = this.comms;
        if (clientComms != null) {
            Properties debug = clientComms.getConOptions().getDebug();
            log.fine(CLASS_NAME, "dumpConOptions", dumpProperties(debug, this.clientID + " : Connect Options").toString());
        }
    }

    public void dumpSystemProperties() {
        log.fine(CLASS_NAME, "dumpSystemProperties", dumpProperties(System.getProperties(), "SystemProperties").toString());
    }
}
