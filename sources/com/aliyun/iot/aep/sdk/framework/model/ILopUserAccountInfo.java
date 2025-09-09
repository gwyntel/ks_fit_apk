package com.aliyun.iot.aep.sdk.framework.model;

import java.io.Serializable;

/* loaded from: classes3.dex */
public class ILopUserAccountInfo implements Serializable {
    public String email;
    public String phone;
    public String phoneCode;

    public String getEmail() {
        return this.email;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getPhoneCode() {
        return this.phoneCode;
    }

    public void setEmail(String str) {
        this.email = str;
    }

    public void setPhone(String str) {
        this.phone = str;
    }

    public void setPhoneCode(String str) {
        this.phoneCode = str;
    }

    public String toString() {
        return "ILopUserAccountInfo{phone='" + this.phone + "', phoneCode='" + this.phoneCode + "', email='" + this.email + "'}";
    }
}
