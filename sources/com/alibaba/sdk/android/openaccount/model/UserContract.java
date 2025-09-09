package com.alibaba.sdk.android.openaccount.model;

/* loaded from: classes2.dex */
public class UserContract {
    String email;
    String hash;
    String hash_key;
    String loginId;
    String mobile;
    String nick;
    String userid;

    public UserContract(String str, String str2, String str3, String str4, String str5, String str6) {
        this.userid = str3;
        this.mobile = str2;
        this.nick = str4;
        this.hash = str;
        this.email = str5;
        this.loginId = str6;
    }

    public String getEmail() {
        return this.email;
    }

    public String getHash() {
        return this.hash;
    }

    public String getHash_key() {
        return this.hash_key;
    }

    public String getLoginId() {
        return this.loginId;
    }

    public String getMobile() {
        return this.mobile;
    }

    public String getNick() {
        return this.nick;
    }

    public String getUserid() {
        return this.userid;
    }

    public void setEmail(String str) {
        this.email = str;
    }

    public void setHash(String str) {
        this.hash = str;
    }

    public void setHash_key(String str) {
        this.hash_key = str;
    }

    public void setLoginId(String str) {
        this.loginId = str;
    }

    public void setMobile(String str) {
        this.mobile = str;
    }

    public void setNick(String str) {
        this.nick = str;
    }

    public void setUserid(String str) {
        this.userid = str;
    }
}
