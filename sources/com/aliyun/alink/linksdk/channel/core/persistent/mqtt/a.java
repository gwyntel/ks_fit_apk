package com.aliyun.alink.linksdk.channel.core.persistent.mqtt;

import android.text.TextUtils;
import com.aliyun.alink.linksdk.channel.core.base.AError;
import com.aliyun.alink.linksdk.channel.core.base.ARequest;
import com.aliyun.alink.linksdk.channel.core.base.AResponse;
import com.aliyun.alink.linksdk.channel.core.base.IOnCallListener;
import com.aliyun.alink.linksdk.channel.core.persistent.IOnRrpcResponseHandle;
import com.aliyun.alink.linksdk.channel.core.persistent.IOnSubscribeRrpcListener;
import com.aliyun.alink.linksdk.channel.core.persistent.PersistentConnectState;
import com.aliyun.alink.linksdk.channel.core.persistent.event.PersistentEventDispatcher;
import com.aliyun.alink.linksdk.channel.core.persistent.mqtt.request.MqttPublishRequest;
import com.aliyun.alink.linksdk.channel.core.persistent.mqtt.request.MqttRrpcRequest;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.aliyun.alink.linksdk.tools.ThreadTools;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* loaded from: classes2.dex */
public class a implements MqttCallbackExtended {

    /* renamed from: a, reason: collision with root package name */
    private Map<String, IOnSubscribeRrpcListener> f10874a;

    /* renamed from: b, reason: collision with root package name */
    private Map<String, IOnSubscribeRrpcListener> f10875b;

    /* renamed from: com.aliyun.alink.linksdk.channel.core.persistent.mqtt.a$a, reason: collision with other inner class name */
    private class C0071a implements IOnRrpcResponseHandle {

        /* renamed from: b, reason: collision with root package name */
        private String f10882b;

        /* renamed from: c, reason: collision with root package name */
        private IOnSubscribeRrpcListener f10883c;

        public C0071a(String str, IOnSubscribeRrpcListener iOnSubscribeRrpcListener) {
            this.f10882b = str;
            this.f10883c = iOnSubscribeRrpcListener;
        }

        @Override // com.aliyun.alink.linksdk.channel.core.persistent.IOnRrpcResponseHandle
        public void onRrpcResponse(String str, AResponse aResponse) {
            Object obj;
            com.aliyun.alink.linksdk.channel.core.b.a.a("MqttDefaulCallback", "onRrpcResponse(), reply topic = " + str);
            MqttPublishRequest mqttPublishRequest = new MqttPublishRequest();
            mqttPublishRequest.isRPC = false;
            if (TextUtils.isEmpty(str)) {
                mqttPublishRequest.topic = this.f10882b + TmpConstant.URI_TOPIC_REPLY_POST;
            } else {
                mqttPublishRequest.topic = str;
            }
            if (aResponse != null && (obj = aResponse.data) != null) {
                mqttPublishRequest.payloadObj = obj;
            }
            b.a().asyncSend(mqttPublishRequest, new IOnCallListener() { // from class: com.aliyun.alink.linksdk.channel.core.persistent.mqtt.a.a.1
                @Override // com.aliyun.alink.linksdk.channel.core.base.IOnCallListener
                public boolean needUISafety() {
                    return C0071a.this.f10883c.needUISafety();
                }

                @Override // com.aliyun.alink.linksdk.channel.core.base.IOnCallListener
                public void onFailed(ARequest aRequest, AError aError) {
                    com.aliyun.alink.linksdk.channel.core.b.a.a("MqttDefaulCallback", "onRrpcResponse(), publish fail");
                    C0071a.this.f10883c.onResponseFailed(C0071a.this.f10882b, aError);
                }

                @Override // com.aliyun.alink.linksdk.channel.core.base.IOnCallListener
                public void onSuccess(ARequest aRequest, AResponse aResponse2) {
                    com.aliyun.alink.linksdk.channel.core.b.a.a("MqttDefaulCallback", "onRrpcResponse(), publish succ");
                    C0071a.this.f10883c.onResponseSuccess(C0071a.this.f10882b);
                }
            });
        }
    }

    public void a(String str, IOnSubscribeRrpcListener iOnSubscribeRrpcListener) {
        com.aliyun.alink.linksdk.channel.core.b.a.a("MqttDefaulCallback", "registerRrpcListener(), topic = " + str);
        if (TextUtils.isEmpty(str) || iOnSubscribeRrpcListener == null) {
            com.aliyun.alink.linksdk.channel.core.b.a.a("MqttDefaulCallback", "registerRrpcListener(), params error ");
            return;
        }
        if (this.f10874a == null) {
            this.f10874a = new HashMap();
        }
        if (this.f10875b == null) {
            this.f10875b = new HashMap();
        }
        if (!str.contains(MqttTopic.MULTI_LEVEL_WILDCARD) && !str.contains(MqttTopic.SINGLE_LEVEL_WILDCARD)) {
            this.f10874a.put(str, iOnSubscribeRrpcListener);
        } else {
            com.aliyun.alink.linksdk.channel.core.b.a.a("MqttDefaulCallback", "registerRrpcListener(), pattern topic ");
            this.f10875b.put(str, iOnSubscribeRrpcListener);
        }
    }

    @Override // org.eclipse.paho.client.mqttv3.MqttCallbackExtended
    public void connectComplete(final boolean z2, String str) {
        com.aliyun.alink.linksdk.channel.core.b.a.b("MqttDefaulCallback", "mqtt connectComplete,reconnect = " + z2 + " ," + str);
        if (z2) {
            ThreadTools.submitTask(new Runnable() { // from class: com.aliyun.alink.linksdk.channel.core.persistent.mqtt.a.1
                @Override // java.lang.Runnable
                public void run() {
                    IMqttAsyncClient iMqttAsyncClientC = b.a().c();
                    com.aliyun.alink.linksdk.channel.core.b.a.a("MqttDefaulCallback", "connectComplete, reconnect=" + z2 + ", client=" + iMqttAsyncClientC + ",threadId=" + Thread.currentThread());
                    if (iMqttAsyncClientC == null || !b.a().d()) {
                        com.aliyun.alink.linksdk.channel.core.b.a.a("MqttDefaulCallback", "connectComplete, try reconnect");
                    } else {
                        b.a().a(PersistentConnectState.CONNECTED);
                        PersistentEventDispatcher.getInstance().broadcastMessage(1, null, null, 0, "reconnect  success");
                    }
                }
            }, true, 1000);
        } else {
            b.a().a(PersistentConnectState.CONNECTED);
            PersistentEventDispatcher.getInstance().broadcastMessage(1, null, null, 0, "connect success");
        }
    }

    @Override // org.eclipse.paho.client.mqttv3.MqttCallback
    public void connectionLost(Throwable th) {
        com.aliyun.alink.linksdk.channel.core.b.a.c("MqttDefaulCallback", "mqtt connectionLost,cause:" + th);
        if (th != null) {
            th.printStackTrace();
        }
        b.a().a(PersistentConnectState.DISCONNECTED);
        if (!(th instanceof MqttException)) {
            PersistentEventDispatcher.getInstance().broadcastMessage(2, null, null, 4201, "connection lost " + th);
            return;
        }
        MqttException mqttException = (MqttException) th;
        PersistentEventDispatcher.getInstance().broadcastMessage(2, null, null, mqttException.getReasonCode(), mqttException.getMessage() + "，" + mqttException);
    }

    @Override // org.eclipse.paho.client.mqttv3.MqttCallback
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        StringBuilder sb = new StringBuilder();
        sb.append("deliveryComplete! ");
        sb.append((iMqttDeliveryToken == null || iMqttDeliveryToken.getResponse() == null) ? TmpConstant.GROUP_ROLE_UNKNOWN : iMqttDeliveryToken.getResponse().getKey());
        com.aliyun.alink.linksdk.channel.core.b.a.a("MqttDefaulCallback", sb.toString());
    }

    @Override // org.eclipse.paho.client.mqttv3.MqttCallback
    public void messageArrived(String str, MqttMessage mqttMessage) {
        try {
            com.aliyun.alink.linksdk.channel.core.b.a.a("MqttDefaulCallback", "messageArrived,topic = [" + str + "] , msg = [" + new String(mqttMessage.getPayload(), "UTF-8") + "],  ");
            try {
                PersistentEventDispatcher.getInstance().broadcastMessage(3, str, mqttMessage.getPayload(), 0, null);
            } catch (Exception unused) {
                com.aliyun.alink.linksdk.channel.core.b.a.a("MqttDefaulCallback", "messageArrived(), send broadcastMsg error");
            }
            Map<String, IOnSubscribeRrpcListener> map = this.f10874a;
            if (map == null || !map.containsKey(str)) {
                Map<String, IOnSubscribeRrpcListener> map2 = this.f10875b;
                if (map2 != null && map2.size() > 0) {
                    Iterator<String> it = this.f10875b.keySet().iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        String next = it.next();
                        if (a(next, str)) {
                            com.aliyun.alink.linksdk.channel.core.b.a.a("MqttDefaulCallback", "messageArrived(), match pattern");
                            MqttRrpcRequest mqttRrpcRequest = new MqttRrpcRequest();
                            mqttRrpcRequest.setTopic(str);
                            mqttRrpcRequest.payloadObj = mqttMessage.getPayload();
                            a(mqttRrpcRequest, this.f10875b.get(next));
                            break;
                        }
                    }
                }
            } else {
                MqttRrpcRequest mqttRrpcRequest2 = new MqttRrpcRequest();
                mqttRrpcRequest2.setTopic(str);
                mqttRrpcRequest2.payloadObj = mqttMessage.getPayload();
                a(mqttRrpcRequest2, this.f10874a.get(str));
            }
            b.a().a(str, mqttMessage);
        } catch (Throwable th) {
            com.aliyun.alink.linksdk.channel.core.b.a.d("MqttDefaulCallback", "messageArrived() handle error:" + th.toString());
        }
    }

    private void a(final MqttRrpcRequest mqttRrpcRequest, final IOnSubscribeRrpcListener iOnSubscribeRrpcListener) {
        com.aliyun.alink.linksdk.channel.core.b.a.a("MqttDefaulCallback", "handleRrpcRequest()");
        if (iOnSubscribeRrpcListener == null || mqttRrpcRequest == null) {
            return;
        }
        if (iOnSubscribeRrpcListener.needUISafety()) {
            ThreadTools.runOnUiThread(new Runnable() { // from class: com.aliyun.alink.linksdk.channel.core.persistent.mqtt.a.2
                @Override // java.lang.Runnable
                public void run() {
                    IOnSubscribeRrpcListener iOnSubscribeRrpcListener2 = iOnSubscribeRrpcListener;
                    MqttRrpcRequest mqttRrpcRequest2 = mqttRrpcRequest;
                    String str = mqttRrpcRequest2.topic;
                    iOnSubscribeRrpcListener2.onReceived(str, mqttRrpcRequest2, a.this.new C0071a(str, iOnSubscribeRrpcListener2));
                }
            });
        } else {
            String str = mqttRrpcRequest.topic;
            iOnSubscribeRrpcListener.onReceived(str, mqttRrpcRequest, new C0071a(str, iOnSubscribeRrpcListener));
        }
    }

    private boolean a(String str, String str2) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            try {
                if (str.contains(MqttTopic.MULTI_LEVEL_WILDCARD) && str2.startsWith(str.split(MqttTopic.MULTI_LEVEL_WILDCARD)[0])) {
                    return true;
                }
                if (str.contains(MqttTopic.SINGLE_LEVEL_WILDCARD)) {
                    String str3 = str.split("\\+")[0];
                    String str4 = str.split("\\+", 2)[1];
                    if (str2.startsWith(str3)) {
                        if (str2.endsWith(str4)) {
                            return true;
                        }
                    }
                }
            } catch (Exception e2) {
                com.aliyun.alink.linksdk.channel.core.b.a.a("MqttDefaulCallback", "isTopicMatchForPattern(),e = " + e2.toString());
            }
        }
        return false;
    }
}
