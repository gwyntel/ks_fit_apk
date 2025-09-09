package com.aliyun.alink.linksdk.tmp.devicemodel;

/* loaded from: classes2.dex */
public class Property {
    private String accessMode;
    private DataType dataType;
    private String desc;
    private String identifier;
    private String name;
    private boolean optional;
    private boolean required;

    public String getAccessMode() {
        return this.accessMode;
    }

    public DataType getDataType() {
        return this.dataType;
    }

    public String getDesc() {
        return this.desc;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public String getName() {
        return this.name;
    }

    @Deprecated
    public boolean isOptional() {
        return this.optional;
    }

    public boolean isRequired() {
        return this.required;
    }

    public void setAccessMode(String str) {
        this.accessMode = str;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public void setDesc(String str) {
        this.desc = str;
    }

    public void setIdentifier(String str) {
        this.identifier = str;
    }

    public void setName(String str) {
        this.name = str;
    }

    @Deprecated
    public void setOptional(boolean z2) {
        this.optional = z2;
    }

    public void setRequired(boolean z2) {
        this.required = z2;
    }
}
