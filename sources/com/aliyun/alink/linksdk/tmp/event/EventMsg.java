package com.aliyun.alink.linksdk.tmp.event;

import com.aliyun.alink.linksdk.tmp.device.payload.EventNotifyData;

/* loaded from: classes2.dex */
public class EventMsg {
    protected String deviceId;
    protected EventNotifyData eventNotifyData;
    protected long timeStamp;

    public String getDeviceId() {
        return this.deviceId;
    }

    public EventNotifyData getEventNotifyData() {
        return this.eventNotifyData;
    }

    public long getTimeStamp() {
        return this.timeStamp;
    }

    public void setDeviceId(String str) {
        this.deviceId = str;
    }

    public void setEventNotifyData(EventNotifyData eventNotifyData) {
        this.eventNotifyData = eventNotifyData;
    }

    public void setTimeStamp(long j2) {
        this.timeStamp = j2;
    }
}
