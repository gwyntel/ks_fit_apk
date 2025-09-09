package com.yc.utesdk.bean;

import java.util.List;

/* loaded from: classes4.dex */
public class ElbpPpgDataInfo implements Comparable<ElbpPpgDataInfo> {

    /* renamed from: a, reason: collision with root package name */
    int f24752a;

    /* renamed from: b, reason: collision with root package name */
    int f24753b;

    /* renamed from: c, reason: collision with root package name */
    List f24754c;

    /* renamed from: d, reason: collision with root package name */
    byte[] f24755d;

    public ElbpPpgDataInfo() {
    }

    @Override // java.lang.Comparable
    public int compareTo(ElbpPpgDataInfo elbpPpgDataInfo) {
        return getSerialNum() - elbpPpgDataInfo.getSerialNum();
    }

    public int getCurDataLength() {
        return this.f24753b;
    }

    public byte[] getPpgData() {
        return this.f24755d;
    }

    public List<Integer> getPpgDataList() {
        return this.f24754c;
    }

    public int getSerialNum() {
        return this.f24752a;
    }

    public void setCurDataLength(int i2) {
        this.f24753b = i2;
    }

    public void setPpgData(byte[] bArr) {
        this.f24755d = bArr;
    }

    public void setPpgDataList(List<Integer> list) {
        this.f24754c = list;
    }

    public void setSerialNum(int i2) {
        this.f24752a = i2;
    }

    public ElbpPpgDataInfo(int i2, int i3, List<Integer> list, byte[] bArr) {
        this.f24752a = i2;
        this.f24753b = i3;
        this.f24754c = list;
        this.f24755d = bArr;
    }
}
