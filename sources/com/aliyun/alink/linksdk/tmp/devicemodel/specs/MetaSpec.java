package com.aliyun.alink.linksdk.tmp.devicemodel.specs;

import com.google.gson.annotations.SerializedName;

/* loaded from: classes2.dex */
public class MetaSpec {
    private String length;
    private String max;
    private String min;

    @SerializedName("1")
    private String one;
    private String precise;
    private String step;
    private String unit;

    @SerializedName("0")
    private String zero;

    public String getLength() {
        return this.length;
    }

    public String getMax() {
        return this.max;
    }

    public String getMin() {
        return this.min;
    }

    public String getOne() {
        return this.one;
    }

    public String getStep() {
        return this.step;
    }

    public String getUnit() {
        return this.unit;
    }

    public String getZero() {
        return this.zero;
    }

    public void setLength(String str) {
        this.length = str;
    }

    public void setMax(String str) {
        this.max = str;
    }

    public void setMin(String str) {
        this.min = str;
    }

    public void setOne(String str) {
        this.one = str;
    }

    public void setStep(String str) {
        this.step = str;
    }

    public void setUnit(String str) {
        this.unit = str;
    }

    public void setZero(String str) {
        this.zero = str;
    }
}
