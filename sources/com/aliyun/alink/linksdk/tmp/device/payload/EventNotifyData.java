package com.aliyun.alink.linksdk.tmp.device.payload;

import java.util.List;

/* loaded from: classes2.dex */
public class EventNotifyData {
    protected List<KeyValuePair> args;
    protected String name;

    public List<KeyValuePair> getArgs() {
        return this.args;
    }

    public String getName() {
        return this.name;
    }

    public EventNotifyData setArgs(List<KeyValuePair> list) {
        this.args = list;
        return this;
    }

    public EventNotifyData setName(String str) {
        this.name = str;
        return this;
    }
}
