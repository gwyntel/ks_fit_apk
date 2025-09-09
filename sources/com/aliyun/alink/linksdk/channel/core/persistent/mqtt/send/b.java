package com.aliyun.alink.linksdk.channel.core.persistent.mqtt.send;

import com.aliyun.alink.linksdk.channel.core.base.AError;
import com.aliyun.alink.linksdk.channel.core.base.ARequest;
import com.aliyun.alink.linksdk.channel.core.base.AResponse;
import com.aliyun.alink.linksdk.channel.core.base.ASend;
import com.aliyun.alink.linksdk.channel.core.base.IOnCallListener;
import com.aliyun.alink.linksdk.channel.core.base.ISendStatus;
import com.aliyun.alink.linksdk.channel.core.persistent.BadNetworkException;
import com.aliyun.alink.linksdk.channel.core.persistent.IOnSubscribeListener;
import com.aliyun.alink.linksdk.channel.core.persistent.mqtt.request.MqttPublishRequest;
import com.aliyun.alink.linksdk.channel.core.persistent.mqtt.request.MqttSubscribeRequest;
import com.aliyun.alink.linksdk.tools.ThreadTools;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/* loaded from: classes2.dex */
public class b extends ASend implements IMqttActionListener {

    /* renamed from: a, reason: collision with root package name */
    private IOnSubscribeListener f10918a;

    /* renamed from: b, reason: collision with root package name */
    private HashMap<String, String> f10919b;

    public b(ARequest aRequest, IOnCallListener iOnCallListener) {
        super(aRequest, iOnCallListener);
        this.f10918a = null;
        this.f10919b = new HashMap<>();
        a(MqttSendStatus.waitingToSend);
    }

    public void a(MqttSendStatus mqttSendStatus) {
        this.status = mqttSendStatus;
    }

    public IOnSubscribeListener b() {
        return this.f10918a;
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttActionListener
    public void onFailure(IMqttToken iMqttToken, Throwable th) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        int i2;
        byte b2;
        byte b3;
        String message = th != null ? th.getMessage() : "MqttNet send failed: unknown error";
        a(MqttSendStatus.completed);
        ARequest aRequest = this.request;
        if (aRequest instanceof MqttSubscribeRequest) {
            if (th instanceof BadNetworkException) {
                b3 = 6;
                i2 = 4101;
            } else {
                b3 = 5;
                i2 = 4201;
            }
            IOnSubscribeListener iOnSubscribeListener = this.f10918a;
            if (iOnSubscribeListener != null) {
                if (iOnSubscribeListener.needUISafety()) {
                    ThreadTools.runOnUiThread(new d(this, b3, message));
                } else if (b3 == 6) {
                    AError aError = new AError();
                    aError.setCode(4101);
                    this.f10918a.onFailed(((MqttSubscribeRequest) this.request).topic, aError);
                } else {
                    AError aError2 = new AError();
                    aError2.setCode(4201);
                    aError2.setMsg(message);
                    this.f10918a.onFailed(((MqttSubscribeRequest) this.request).topic, aError2);
                }
            }
        } else if (aRequest instanceof MqttPublishRequest) {
            if (th instanceof BadNetworkException) {
                b2 = 3;
                i2 = 4101;
            } else {
                b2 = 2;
                i2 = 4201;
            }
            IOnCallListener iOnCallListener = this.listener;
            if (iOnCallListener != null) {
                if (iOnCallListener.needUISafety()) {
                    ThreadTools.runOnUiThread(new d(this, b2, message));
                } else if (b2 == 3) {
                    AError aError3 = new AError();
                    aError3.setCode(4101);
                    this.listener.onFailed(this.request, aError3);
                } else {
                    AError aError4 = new AError();
                    aError4.setCode(4201);
                    aError4.setMsg(message);
                    this.listener.onFailed(this.request, aError4);
                }
            }
        } else {
            i2 = 0;
        }
        a("endTime-send", String.valueOf(System.currentTimeMillis()));
        a("errorCode", String.valueOf(i2));
        com.aliyun.alink.linksdk.channel.core.persistent.mqtt.a.d.a("mqtt-send", this.f10919b);
    }

    @Override // org.eclipse.paho.client.mqttv3.IMqttActionListener
    public void onSuccess(IMqttToken iMqttToken) throws IllegalAccessException, IOException, IllegalArgumentException, InvocationTargetException {
        ARequest aRequest = this.request;
        boolean z2 = true;
        if (!(aRequest instanceof MqttSubscribeRequest)) {
            if (aRequest instanceof MqttPublishRequest) {
                if (!((MqttPublishRequest) aRequest).isRPC) {
                    a(MqttSendStatus.completed);
                    IOnCallListener iOnCallListener = this.listener;
                    if (iOnCallListener != null) {
                        if (iOnCallListener.needUISafety()) {
                            ThreadTools.runOnUiThread(new d(this, (byte) 1, null));
                        } else {
                            this.listener.onSuccess(this.request, this.response);
                        }
                    }
                    a("endTime-send", String.valueOf(System.currentTimeMillis()));
                    com.aliyun.alink.linksdk.channel.core.persistent.mqtt.a.d.a("mqtt-send", this.f10919b);
                    return;
                }
                ISendStatus iSendStatus = this.status;
                if (iSendStatus == MqttSendStatus.waitingToSubReply) {
                    a(MqttSendStatus.subReplyed);
                    a("endTime-send", String.valueOf(System.currentTimeMillis()));
                    com.aliyun.alink.linksdk.channel.core.persistent.mqtt.a.d.a("mqtt-send", this.f10919b);
                    new c().asyncSend(this);
                    return;
                }
                if (iSendStatus == MqttSendStatus.waitingToPublish) {
                    a(MqttSendStatus.published);
                    a("endTime-send", String.valueOf(System.currentTimeMillis()));
                    com.aliyun.alink.linksdk.channel.core.persistent.mqtt.a.d.a("mqtt-send", this.f10919b);
                    return;
                }
                return;
            }
            return;
        }
        a(MqttSendStatus.completed);
        try {
            if (iMqttToken.getGrantedQos()[0] == 128) {
                z2 = false;
            }
        } catch (Exception unused) {
            com.aliyun.alink.linksdk.channel.core.b.a.a("MqttSend", "onSuccess(),getGrantedQos");
        }
        IOnSubscribeListener iOnSubscribeListener = this.f10918a;
        if (iOnSubscribeListener == null) {
            a("endTime-send", String.valueOf(System.currentTimeMillis()));
            com.aliyun.alink.linksdk.channel.core.persistent.mqtt.a.d.a("mqtt-send", this.f10919b);
            return;
        }
        if (iOnSubscribeListener.needUISafety()) {
            ThreadTools.runOnUiThread(new d(this, z2 ? (byte) 4 : (byte) 5, null));
            a("endTime-send", String.valueOf(System.currentTimeMillis()));
            if (!z2) {
                a("errorCode", String.valueOf(4201));
            }
            com.aliyun.alink.linksdk.channel.core.persistent.mqtt.a.d.a("mqtt-send", this.f10919b);
            return;
        }
        if (z2) {
            this.f10918a.onSuccess(((MqttSubscribeRequest) this.request).topic);
            a("endTime-send", String.valueOf(System.currentTimeMillis()));
            com.aliyun.alink.linksdk.channel.core.persistent.mqtt.a.d.a("mqtt-send", this.f10919b);
            return;
        }
        AError aError = new AError();
        aError.setCode(4103);
        aError.setMsg("subACK Failure");
        this.f10918a.onFailed(((MqttSubscribeRequest) this.request).topic, aError);
        a("endTime-send", String.valueOf(System.currentTimeMillis()));
        a("errorCode", String.valueOf(4103));
        com.aliyun.alink.linksdk.channel.core.persistent.mqtt.a.d.a("mqtt-send", this.f10919b);
    }

    @Override // com.aliyun.alink.linksdk.channel.core.base.ASend
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public MqttSendStatus getStatus() {
        return (MqttSendStatus) this.status;
    }

    public void a(String str, String str2) {
        com.aliyun.alink.linksdk.channel.core.b.a.a("MqttSend", "addTrackData() called with: key = [" + str + "], value = [" + str2 + "]");
        HashMap<String, String> map = this.f10919b;
        if (map != null) {
            map.put(str, str2);
        }
    }

    public b(ARequest aRequest, IOnSubscribeListener iOnSubscribeListener) {
        super(aRequest, null);
        this.f10918a = null;
        this.f10919b = new HashMap<>();
        this.f10918a = iOnSubscribeListener;
        a(MqttSendStatus.waitingToSend);
    }

    public void a(String str, MqttMessage mqttMessage) {
        com.aliyun.alink.linksdk.channel.core.b.a.a("MqttSend", "rpcMessageArrived(), topic =" + str + " msg =" + mqttMessage.toString());
        ARequest aRequest = this.request;
        if (aRequest instanceof MqttPublishRequest) {
            MqttPublishRequest mqttPublishRequest = (MqttPublishRequest) aRequest;
            if (mqttPublishRequest.isRPC) {
                ISendStatus iSendStatus = this.status;
                if ((iSendStatus == MqttSendStatus.published || iSendStatus == MqttSendStatus.waitingToPublish) && str.equals(mqttPublishRequest.replyTopic)) {
                    com.aliyun.alink.linksdk.channel.core.b.a.a("MqttSend", "messageArrived(), match!");
                    a(MqttSendStatus.completed);
                    if (this.response == null) {
                        this.response = new AResponse();
                    }
                    this.response.data = mqttMessage.toString();
                    IOnCallListener iOnCallListener = this.listener;
                    if (iOnCallListener != null) {
                        if (iOnCallListener.needUISafety()) {
                            ThreadTools.runOnUiThread(new d(this, (byte) 1, null));
                        } else {
                            this.listener.onSuccess(this.request, this.response);
                        }
                    }
                }
            }
        }
    }
}
