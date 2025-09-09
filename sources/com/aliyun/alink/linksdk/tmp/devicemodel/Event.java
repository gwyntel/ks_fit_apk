package com.aliyun.alink.linksdk.tmp.devicemodel;

import java.util.List;

/* loaded from: classes2.dex */
public class Event {
    private String desc;
    private String identifier;
    private String method;
    private String name;
    private List<Arg> outputData;
    private String type;

    public String getDesc() {
        return this.desc;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public String getMethod() {
        return this.method;
    }

    public String getName() {
        return this.name;
    }

    public List<Arg> getOutputData() {
        return this.outputData;
    }

    public String getType() {
        return this.type;
    }

    public void setDesc(String str) {
        this.desc = str;
    }

    public void setIdentifier(String str) {
        this.identifier = str;
    }

    public void setMethod(String str) {
        this.method = str;
    }

    public void setName(String str) {
        this.name = str;
    }

    public void setOutputData(List<Arg> list) {
        this.outputData = list;
    }

    public void setType(String str) {
        this.type = str;
    }
}
