package com.aliyun.alink.linksdk.channel.core.persistent.mqtt.send;

import android.content.Context;
import android.text.TextUtils;
import com.aliyun.alink.linksdk.channel.core.base.ASend;
import com.aliyun.alink.linksdk.channel.core.persistent.BadNetworkException;
import com.aliyun.alink.linksdk.channel.core.persistent.ISendExecutor;
import com.aliyun.alink.linksdk.channel.core.persistent.PersistentConnectState;
import com.aliyun.alink.linksdk.channel.core.persistent.mqtt.MqttConfigure;
import com.aliyun.alink.linksdk.channel.core.persistent.mqtt.request.MqttPublishRequest;
import com.aliyun.alink.linksdk.channel.core.persistent.mqtt.request.MqttSubscribeRequest;
import com.aliyun.alink.linksdk.channel.core.persistent.mqtt.request.MqttSubscribeRequestParams;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.aliyun.alink.linksdk.tools.NetTools;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/* loaded from: classes2.dex */
public class c implements ISendExecutor {
    @Override // com.aliyun.alink.linksdk.channel.core.persistent.ISendExecutor
    public void asyncSend(ASend aSend) throws IllegalAccessException, IOException, IllegalArgumentException, InvocationTargetException {
        byte[] byteArray;
        String string;
        if (aSend == null || aSend.getRequest() == null) {
            com.aliyun.alink.linksdk.channel.core.b.a.d("MqttSendExecutor", "asyncSend(): bad parameters: NULL");
            return;
        }
        if (!(aSend instanceof b)) {
            com.aliyun.alink.linksdk.channel.core.b.a.a("MqttSendExecutor", "asyncSend(): bad parameter: need MqttSend");
            return;
        }
        b bVar = (b) aSend;
        IMqttAsyncClient iMqttAsyncClientC = com.aliyun.alink.linksdk.channel.core.persistent.mqtt.b.a().c();
        if (iMqttAsyncClientC == null) {
            com.aliyun.alink.linksdk.channel.core.b.a.d("MqttSendExecutor", "asyncSend(): MqttNet::getClient() return null");
            bVar.a(MqttSendStatus.completed);
            bVar.onFailure(null, new IllegalStateException("init mqtt first"));
            return;
        }
        Context contextE = com.aliyun.alink.linksdk.channel.core.persistent.mqtt.b.a().e();
        if (!MqttConfigure.disableNetworkCheckBeforeSend && contextE != null && !NetTools.isAvailable(contextE)) {
            com.aliyun.alink.linksdk.channel.core.b.a.a("MqttSendExecutor", "asyncSend(): bad Network");
            bVar.a(MqttSendStatus.completed);
            bVar.onFailure(null, new BadNetworkException());
            return;
        }
        if (com.aliyun.alink.linksdk.channel.core.persistent.mqtt.b.a().getConnectState() != PersistentConnectState.CONNECTED) {
            com.aliyun.alink.linksdk.channel.core.b.a.a("MqttSendExecutor", "asyncSend(): gateway disconnect");
            bVar.a(MqttSendStatus.completed);
            bVar.onFailure(null, new BadNetworkException());
            return;
        }
        bVar.a("startTime-send", String.valueOf(System.currentTimeMillis()));
        if (!(aSend.getRequest() instanceof MqttPublishRequest)) {
            if (aSend.getRequest() instanceof MqttSubscribeRequest) {
                MqttSubscribeRequest mqttSubscribeRequest = (MqttSubscribeRequest) aSend.getRequest();
                if (TextUtils.isEmpty(mqttSubscribeRequest.topic)) {
                    com.aliyun.alink.linksdk.channel.core.b.a.d("MqttSendExecutor", "asyncSend(): bad parameters: subsribe req , topic empty");
                    bVar.onFailure(null, new NullPointerException("subsribe req , topic empty"));
                    return;
                }
                try {
                    bVar.a(MqttSendStatus.waitingToComplete);
                    if (!mqttSubscribeRequest.isSubscribe) {
                        com.aliyun.alink.linksdk.channel.core.b.a.a("MqttSendExecutor", "unsubscribe: topic: [ " + mqttSubscribeRequest.topic + " ]");
                        iMqttAsyncClientC.unsubscribe(mqttSubscribeRequest.topic, (Object) null, bVar);
                        return;
                    }
                    com.aliyun.alink.linksdk.channel.core.b.a.a("MqttSendExecutor", "subscribe: topic: [ " + mqttSubscribeRequest.topic + " ]");
                    String str = mqttSubscribeRequest.topic;
                    MqttSubscribeRequestParams mqttSubscribeRequestParams = mqttSubscribeRequest.subscribeRequestParams;
                    iMqttAsyncClientC.subscribe(str, mqttSubscribeRequestParams == null ? 0 : mqttSubscribeRequestParams.qos, (Object) null, bVar);
                    return;
                } catch (Exception e2) {
                    com.aliyun.alink.linksdk.channel.core.b.a.a("MqttSendExecutor", "asyncSend(), send subsribe error, e = " + e2.toString());
                    bVar.a(MqttSendStatus.completed);
                    bVar.onFailure(null, new MqttThrowable(e2.getMessage()));
                    return;
                }
            }
            return;
        }
        MqttPublishRequest mqttPublishRequest = (MqttPublishRequest) aSend.getRequest();
        if (TextUtils.isEmpty(mqttPublishRequest.topic) || mqttPublishRequest.payloadObj == null) {
            com.aliyun.alink.linksdk.channel.core.b.a.d("MqttSendExecutor", "asyncSend(): bad parameters: topic or payload empty");
            bVar.onFailure(null, new NullPointerException("topic or payload empty"));
            return;
        }
        if (mqttPublishRequest.isRPC && (bVar.getStatus() == MqttSendStatus.waitingToSend || bVar.getStatus() == MqttSendStatus.completed)) {
            try {
                Object obj = mqttPublishRequest.payloadObj;
                if (obj instanceof String) {
                    string = obj.toString();
                } else if (obj instanceof byte[]) {
                    string = new String((byte[]) obj, "UTF-8");
                } else {
                    try {
                        string = obj.toString();
                    } catch (Exception e3) {
                        com.aliyun.alink.linksdk.channel.core.b.a.a("MqttSendExecutor", "asyncSend(), publish , toString error," + e3.toString());
                        bVar.a(MqttSendStatus.completed);
                        bVar.onFailure(null, new MqttThrowable("RPC request ,payload should be String or byte[]"));
                        return;
                    }
                }
                mqttPublishRequest.msgId = com.aliyun.alink.linksdk.channel.core.persistent.mqtt.a.a.a(string);
                if (TextUtils.isEmpty(mqttPublishRequest.replyTopic)) {
                    mqttPublishRequest.replyTopic = mqttPublishRequest.topic + TmpConstant.URI_TOPIC_REPLY_POST;
                }
                com.aliyun.alink.linksdk.channel.core.b.a.a("MqttSendExecutor", "publish: RPC sub reply topic: [ " + mqttPublishRequest.replyTopic + " ]");
                bVar.a(MqttSendStatus.waitingToSubReply);
                String str2 = mqttPublishRequest.replyTopic;
                iMqttAsyncClientC.subscribe(str2, 0, (Object) null, bVar, new a(str2, bVar));
                return;
            } catch (Exception e4) {
                com.aliyun.alink.linksdk.channel.core.b.a.a("MqttSendExecutor", "asyncSend(), publish , send subsribe reply error, e = " + e4.toString());
                bVar.a(MqttSendStatus.completed);
                bVar.onFailure(null, new MqttThrowable(e4.getMessage()));
                return;
            }
        }
        try {
            Object obj2 = mqttPublishRequest.payloadObj;
            if (obj2 instanceof String) {
                byteArray = obj2.toString().getBytes("utf-8");
            } else if (obj2 instanceof byte[]) {
                byteArray = (byte[]) obj2;
            } else {
                try {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                    objectOutputStream.writeObject(mqttPublishRequest.payloadObj);
                    objectOutputStream.flush();
                    byteArray = byteArrayOutputStream.toByteArray();
                } catch (Exception e5) {
                    com.aliyun.alink.linksdk.channel.core.b.a.d("MqttSendExecutor", "asyncSend(): convert payload Obj to byte array error");
                    e5.printStackTrace();
                    byteArray = null;
                }
            }
            if (mqttPublishRequest.payloadObj == null) {
                com.aliyun.alink.linksdk.channel.core.b.a.a("MqttSendExecutor", "asyncSend(): payload is empty");
                bVar.onFailure(null, new NullPointerException("payload empty"));
                return;
            }
            com.aliyun.alink.linksdk.channel.core.b.a.a("MqttSendExecutor", "publish: topic: [ " + mqttPublishRequest.topic + " ]");
            com.aliyun.alink.linksdk.channel.core.b.a.a("MqttSendExecutor", "publish: payload: [ " + mqttPublishRequest.payloadObj.toString() + " ]");
            MqttMessage mqttMessage = new MqttMessage(byteArray);
            mqttMessage.setQos(mqttPublishRequest.qos);
            if (mqttPublishRequest.isRPC) {
                bVar.a(MqttSendStatus.waitingToPublish);
            } else {
                bVar.a(MqttSendStatus.waitingToComplete);
            }
            iMqttAsyncClientC.publish(mqttPublishRequest.topic, mqttMessage, (Object) null, bVar);
        } catch (Exception e6) {
            com.aliyun.alink.linksdk.channel.core.b.a.a("MqttSendExecutor", "asyncSend(), send publish error, e = " + e6.toString());
            bVar.a(MqttSendStatus.completed);
            bVar.onFailure(null, new MqttThrowable(e6.getMessage()));
        }
    }
}
