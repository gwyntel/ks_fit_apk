package com.yc.utesdk.bean;

/* loaded from: classes4.dex */
public class ContactsInfo {
    private String name;
    private String phone;

    public ContactsInfo() {
    }

    public String getName() {
        return this.name;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setName(String str) {
        this.name = str;
    }

    public void setPhone(String str) {
        this.phone = str;
    }

    public ContactsInfo(String str, String str2) {
        setName(str);
        setPhone(str2);
    }
}
