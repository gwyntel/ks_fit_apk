package com.aliyun.alink.linksdk.channel.core.persistent.mqtt.a;

import com.xiaomi.account.openauth.XiaomiOAuthConstants;
import java.util.ResourceBundle;
import org.android.agoo.common.AgooConstants;
import org.eclipse.paho.client.mqttv3.logging.Logger;

/* loaded from: classes2.dex */
public class b implements Logger {

    /* renamed from: a, reason: collision with root package name */
    private String f10885a;

    private void a(String str, String str2, String str3, String str4, Object[] objArr, Throwable th) {
        if ("warning".equals(str)) {
            com.aliyun.alink.linksdk.channel.core.b.a.c("MqttPaho", str + ", c= " + str2 + " , method = " + str3 + " , msg = " + str4 + ", inserts = " + a(objArr) + ", throwable = " + th);
            return;
        }
        if (XiaomiOAuthConstants.EXTRA_INFO.equals(str) || "config".equals(str)) {
            com.aliyun.alink.linksdk.channel.core.b.a.b("MqttPaho", str + ", c= " + str2 + " , method = " + str3 + " , msg = " + str4 + ", inserts = " + a(objArr) + ", throwable = " + th);
            return;
        }
        com.aliyun.alink.linksdk.channel.core.b.a.a("MqttPaho", str + ", c= " + str2 + " , method = " + str3 + " , msg = " + str4 + ", inserts = " + a(objArr) + ", throwable = " + th);
    }

    @Override // org.eclipse.paho.client.mqttv3.logging.Logger
    public void config(String str, String str2, String str3) {
        a("config", str, str2, str3, null, null);
    }

    @Override // org.eclipse.paho.client.mqttv3.logging.Logger
    public void dumpTrace() {
        com.aliyun.alink.linksdk.channel.core.b.a.a("MqttPaho", "dumpTrace()");
    }

    @Override // org.eclipse.paho.client.mqttv3.logging.Logger
    public void fine(String str, String str2, String str3) {
        a("fine", str, str2, str3, null, null);
    }

    @Override // org.eclipse.paho.client.mqttv3.logging.Logger
    public void finer(String str, String str2, String str3) {
        a("finer", str, str2, str3, null, null);
    }

    @Override // org.eclipse.paho.client.mqttv3.logging.Logger
    public void finest(String str, String str2, String str3) {
        a("finest", str, str2, str3, null, null);
    }

    @Override // org.eclipse.paho.client.mqttv3.logging.Logger
    public String formatMessage(String str, Object[] objArr) {
        return str;
    }

    @Override // org.eclipse.paho.client.mqttv3.logging.Logger
    public void info(String str, String str2, String str3) {
        a(XiaomiOAuthConstants.EXTRA_INFO, str, str2, str3, null, null);
    }

    @Override // org.eclipse.paho.client.mqttv3.logging.Logger
    public void initialise(ResourceBundle resourceBundle, String str, String str2) {
        com.aliyun.alink.linksdk.channel.core.b.a.a("MqttPaho", "initialise，loggerId = " + str + " , name = " + str2);
    }

    @Override // org.eclipse.paho.client.mqttv3.logging.Logger
    public boolean isLoggable(int i2) {
        return true;
    }

    @Override // org.eclipse.paho.client.mqttv3.logging.Logger
    public void log(int i2, String str, String str2, String str3, Object[] objArr, Throwable th) {
        a("log " + i2, str, str2, str3, objArr, th);
    }

    @Override // org.eclipse.paho.client.mqttv3.logging.Logger
    public void setResourceName(String str) {
        com.aliyun.alink.linksdk.channel.core.b.a.a("MqttPaho", "setResourceName(), " + str);
        this.f10885a = str;
    }

    @Override // org.eclipse.paho.client.mqttv3.logging.Logger
    public void severe(String str, String str2, String str3) {
        a("severe", str, str2, str3, null, null);
    }

    @Override // org.eclipse.paho.client.mqttv3.logging.Logger
    public void trace(int i2, String str, String str2, String str3, Object[] objArr, Throwable th) {
        a(AgooConstants.MESSAGE_TRACE, str, str2, str3, objArr, th);
    }

    @Override // org.eclipse.paho.client.mqttv3.logging.Logger
    public void warning(String str, String str2, String str3) {
        a("warning", str, str2, str3, null, null);
    }

    @Override // org.eclipse.paho.client.mqttv3.logging.Logger
    public void config(String str, String str2, String str3, Object[] objArr) {
        a("config", str, str2, str3, objArr, null);
    }

    @Override // org.eclipse.paho.client.mqttv3.logging.Logger
    public void fine(String str, String str2, String str3, Object[] objArr) {
        a("fine", str, str2, str3, objArr, null);
    }

    @Override // org.eclipse.paho.client.mqttv3.logging.Logger
    public void finer(String str, String str2, String str3, Object[] objArr) {
        a("finer", str, str2, str3, objArr, null);
    }

    @Override // org.eclipse.paho.client.mqttv3.logging.Logger
    public void finest(String str, String str2, String str3, Object[] objArr) {
        a("finest", str, str2, str3, objArr, null);
    }

    @Override // org.eclipse.paho.client.mqttv3.logging.Logger
    public void info(String str, String str2, String str3, Object[] objArr) {
        a(XiaomiOAuthConstants.EXTRA_INFO, str, str2, str3, objArr, null);
    }

    @Override // org.eclipse.paho.client.mqttv3.logging.Logger
    public void severe(String str, String str2, String str3, Object[] objArr) {
        a("severe", str, str2, str3, objArr, null);
    }

    @Override // org.eclipse.paho.client.mqttv3.logging.Logger
    public void warning(String str, String str2, String str3, Object[] objArr) {
        a("warning", str, str2, str3, null, null);
    }

    @Override // org.eclipse.paho.client.mqttv3.logging.Logger
    public void config(String str, String str2, String str3, Object[] objArr, Throwable th) {
        a("config", str, str2, str3, objArr, th);
    }

    @Override // org.eclipse.paho.client.mqttv3.logging.Logger
    public void fine(String str, String str2, String str3, Object[] objArr, Throwable th) {
        a("fine", str, str2, str3, objArr, th);
    }

    @Override // org.eclipse.paho.client.mqttv3.logging.Logger
    public void finer(String str, String str2, String str3, Object[] objArr, Throwable th) {
        a("finer", str, str2, str3, objArr, th);
    }

    @Override // org.eclipse.paho.client.mqttv3.logging.Logger
    public void finest(String str, String str2, String str3, Object[] objArr, Throwable th) {
        a("finest", str, str2, str3, objArr, th);
    }

    @Override // org.eclipse.paho.client.mqttv3.logging.Logger
    public void info(String str, String str2, String str3, Object[] objArr, Throwable th) {
        a(XiaomiOAuthConstants.EXTRA_INFO, str, str2, str3, objArr, th);
    }

    @Override // org.eclipse.paho.client.mqttv3.logging.Logger
    public void severe(String str, String str2, String str3, Object[] objArr, Throwable th) {
        a("severe", str, str2, str3, objArr, th);
    }

    @Override // org.eclipse.paho.client.mqttv3.logging.Logger
    public void warning(String str, String str2, String str3, Object[] objArr, Throwable th) {
        a("warning", str, str2, str3, objArr, th);
    }

    private String a(Object[] objArr) {
        if (objArr == null || objArr.length < 1) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 0; i2 < objArr.length; i2++) {
            stringBuffer.append(String.valueOf(objArr[i2]));
            if (i2 != objArr.length - 1 && stringBuffer.length() > 1) {
                stringBuffer.append(",");
            }
        }
        return stringBuffer.toString();
    }
}
