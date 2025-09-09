package com.yc.utesdk.bean;

import java.util.List;

/* loaded from: classes4.dex */
public class ElHRVMiddleDataInfo implements Comparable<ElHRVMiddleDataInfo> {

    /* renamed from: a, reason: collision with root package name */
    int f24738a;

    /* renamed from: b, reason: collision with root package name */
    int f24739b;

    /* renamed from: c, reason: collision with root package name */
    List f24740c;

    /* renamed from: d, reason: collision with root package name */
    byte[] f24741d;

    public ElHRVMiddleDataInfo() {
    }

    @Override // java.lang.Comparable
    public int compareTo(ElHRVMiddleDataInfo elHRVMiddleDataInfo) {
        return getSerialNum() - elHRVMiddleDataInfo.getSerialNum();
    }

    public int getCurDataLength() {
        return this.f24739b;
    }

    public byte[] getMiddleData() {
        return this.f24741d;
    }

    public List<Double> getMiddleDataList() {
        return this.f24740c;
    }

    public int getSerialNum() {
        return this.f24738a;
    }

    public void setCurDataLength(int i2) {
        this.f24739b = i2;
    }

    public void setMiddleData(byte[] bArr) {
        this.f24741d = bArr;
    }

    public void setMiddleDataList(List<Double> list) {
        this.f24740c = list;
    }

    public void setSerialNum(int i2) {
        this.f24738a = i2;
    }

    public ElHRVMiddleDataInfo(int i2, int i3, List<Double> list, byte[] bArr) {
        this.f24738a = i2;
        this.f24739b = i3;
        this.f24740c = list;
        this.f24741d = bArr;
    }
}
