package com.aliyun.alink.linksdk.cmp.connect.channel;

/* loaded from: classes2.dex */
public class MqttPublishRequest extends PersistentRequest {
    public boolean isRPC = false;
    public String msgId = "";
    public int qos = 0;
    public String replyTopic;
    public String topic;
}
