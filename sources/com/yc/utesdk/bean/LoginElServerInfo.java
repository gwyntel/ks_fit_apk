package com.yc.utesdk.bean;

import androidx.annotation.NonNull;
import com.google.gson.Gson;

/* loaded from: classes4.dex */
public class LoginElServerInfo {
    private String account;
    private int accountType;
    private String appKey;
    private String birthday;
    private int gender;
    private int height;
    private String name;
    private String password;
    private int weight;

    public String getAccount() {
        return this.account;
    }

    public int getAccountType() {
        return this.accountType;
    }

    public String getAppKey() {
        return this.appKey;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public int getGender() {
        return this.gender;
    }

    public int getHeight() {
        return this.height;
    }

    public String getName() {
        return this.name;
    }

    public String getPassword() {
        return this.password;
    }

    public int getWeight() {
        return this.weight;
    }

    public void setAccount(@NonNull String str) {
        this.account = str;
    }

    public void setAccountType(int i2) {
        this.accountType = i2;
    }

    public void setAppKey(@NonNull String str) {
        this.appKey = str;
    }

    public void setBirthday(@NonNull String str) {
        this.birthday = str;
    }

    public void setGender(int i2) {
        this.gender = i2;
    }

    public void setHeight(int i2) {
        this.height = i2;
    }

    public void setName(@NonNull String str) {
        this.name = str;
    }

    public void setPassword(@NonNull String str) {
        this.password = str;
    }

    public void setWeight(int i2) {
        this.weight = i2;
    }

    @NonNull
    public String toString() {
        return new Gson().toJson(this);
    }
}
