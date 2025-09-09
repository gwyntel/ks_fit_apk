package anetwork.channel.entity;

import anetwork.channel.Param;

/* loaded from: classes2.dex */
public class StringParam implements Param {
    private String key;
    private String value;

    public StringParam(String str, String str2) {
        this.key = str;
        this.value = str2;
    }

    @Override // anetwork.channel.Param
    public String getKey() {
        return this.key;
    }

    @Override // anetwork.channel.Param
    public String getValue() {
        return this.value;
    }
}
