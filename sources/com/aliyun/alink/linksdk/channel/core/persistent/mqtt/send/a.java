package com.aliyun.alink.linksdk.channel.core.persistent.mqtt.send;

import android.text.TextUtils;
import com.aliyun.alink.linksdk.channel.core.persistent.mqtt.request.MqttPublishRequest;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/* loaded from: classes2.dex */
public class a implements IMqttMessageListener {

    /* renamed from: a, reason: collision with root package name */
    private static Map<String, b> f10917a;

    public a(String str, b bVar) {
        if (f10917a == null) {
            f10917a = new HashMap();
        }
        if (bVar == null || TextUtils.isEmpty(str) || bVar.getRequest() == null || !(bVar.getRequest() instanceof MqttPublishRequest) || TextUtils.isEmpty(((MqttPublishRequest) bVar.getRequest()).msgId)) {
            return;
        }
        f10917a.put(str + ",id=" + ((MqttPublishRequest) bVar.getRequest()).msgId, bVar);
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttMessageListener
    public void messageArrived(String str, MqttMessage mqttMessage) {
        com.aliyun.alink.linksdk.channel.core.b.a.a("MqttRpcMessageCallback", "messageArrived()");
        if (TextUtils.isEmpty(str)) {
            return;
        }
        String strA = com.aliyun.alink.linksdk.channel.core.persistent.mqtt.a.a.a(mqttMessage.toString());
        if (TextUtils.isEmpty(strA)) {
            return;
        }
        if (f10917a.containsKey(str + ",id=" + strA)) {
            com.aliyun.alink.linksdk.channel.core.b.a.a("MqttRpcMessageCallback", "messageArrived(), match Id = <" + str + ",id=" + strA + ">");
            Map<String, b> map = f10917a;
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(",id=");
            sb.append(strA);
            map.get(sb.toString()).a(str, mqttMessage);
            f10917a.remove(str + ",id=" + strA);
        }
    }
}
