package com.aliyun.alink.business.devicecenter.api.diagnose;

/* loaded from: classes2.dex */
public class DiagnoseResult {
    public String code;
    public String codeVer;
    public String errMsg;
    public String sign;
    public String signSecretType;
    public String state;

    public String toString() {
        return "{\"code\":\"" + this.code + "\",\"codeVer\":\"" + this.codeVer + "\",\"state\":\"" + this.state + "\",\"errMsg\":\"" + this.errMsg + "\",\"signSecretType\":\"" + this.signSecretType + "\",\"sign\":\"" + this.sign + "\"}";
    }
}
