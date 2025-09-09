package com.yc.utesdk.bean;

import java.util.List;

/* loaded from: classes4.dex */
public class ElbpMiddleDataInfo implements Comparable<ElbpMiddleDataInfo> {

    /* renamed from: a, reason: collision with root package name */
    int f24748a;

    /* renamed from: b, reason: collision with root package name */
    int f24749b;

    /* renamed from: c, reason: collision with root package name */
    List f24750c;

    /* renamed from: d, reason: collision with root package name */
    byte[] f24751d;

    public ElbpMiddleDataInfo() {
    }

    @Override // java.lang.Comparable
    public int compareTo(ElbpMiddleDataInfo elbpMiddleDataInfo) {
        return getSerialNum() - elbpMiddleDataInfo.getSerialNum();
    }

    public int getDataState() {
        return this.f24749b;
    }

    public byte[] getMiddleData() {
        return this.f24751d;
    }

    public List<Double> getMiddleDataList() {
        return this.f24750c;
    }

    public int getSerialNum() {
        return this.f24748a;
    }

    public void setDataState(int i2) {
        this.f24749b = i2;
    }

    public void setMiddleData(byte[] bArr) {
        this.f24751d = bArr;
    }

    public void setMiddleDataList(List<Double> list) {
        this.f24750c = list;
    }

    public void setSerialNum(int i2) {
        this.f24748a = i2;
    }

    public ElbpMiddleDataInfo(int i2, int i3, List<Double> list, byte[] bArr) {
        this.f24748a = i2;
        this.f24749b = i3;
        this.f24750c = list;
    }
}
