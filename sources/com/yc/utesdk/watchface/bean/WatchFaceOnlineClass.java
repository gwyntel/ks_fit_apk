package com.yc.utesdk.watchface.bean;

import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class WatchFaceOnlineClass implements Comparable<WatchFaceOnlineClass> {

    /* renamed from: a, reason: collision with root package name */
    List f24953a = new ArrayList();
    private String appkey;
    private String btname;
    private String id;
    private String name;
    private int orderNum;

    @Override // java.lang.Comparable
    public int compareTo(@NonNull WatchFaceOnlineClass watchFaceOnlineClass) {
        return getOrderNum() - watchFaceOnlineClass.getOrderNum();
    }

    public String getAppkey() {
        return this.appkey;
    }

    public String getBtname() {
        return this.btname;
    }

    public List<WatchFaceOnlineOneInfo> getDialClassList() {
        return this.f24953a;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getOrderNum() {
        return this.orderNum;
    }

    public void setAppkey(String str) {
        this.appkey = str;
    }

    public void setBtname(String str) {
        this.btname = str;
    }

    public void setDialClassList(List<WatchFaceOnlineOneInfo> list) {
        this.f24953a = list;
    }

    public void setId(String str) {
        this.id = str;
    }

    public void setName(String str) {
        this.name = str;
    }

    public void setOrderNum(int i2) {
        this.orderNum = i2;
    }
}
