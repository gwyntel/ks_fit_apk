package com.yc.utesdk.bean;

import java.util.List;

/* loaded from: classes4.dex */
public class ElbsPpgDataInfo implements Comparable<ElbsPpgDataInfo> {

    /* renamed from: a, reason: collision with root package name */
    int f24756a;

    /* renamed from: b, reason: collision with root package name */
    int f24757b;

    /* renamed from: c, reason: collision with root package name */
    List f24758c;

    /* renamed from: d, reason: collision with root package name */
    byte[] f24759d;

    public ElbsPpgDataInfo() {
    }

    @Override // java.lang.Comparable
    public int compareTo(ElbsPpgDataInfo elbsPpgDataInfo) {
        return getSerialNum() - elbsPpgDataInfo.getSerialNum();
    }

    public int getCurDataLength() {
        return this.f24757b;
    }

    public byte[] getPpgData() {
        return this.f24759d;
    }

    public List<Integer> getPpgDataList() {
        return this.f24758c;
    }

    public int getSerialNum() {
        return this.f24756a;
    }

    public void setCurDataLength(int i2) {
        this.f24757b = i2;
    }

    public void setPpgData(byte[] bArr) {
        this.f24759d = bArr;
    }

    public void setPpgDataList(List<Integer> list) {
        this.f24758c = list;
    }

    public void setSerialNum(int i2) {
        this.f24756a = i2;
    }

    public ElbsPpgDataInfo(int i2, int i3, List<Integer> list, byte[] bArr) {
        this.f24756a = i2;
        this.f24757b = i3;
        this.f24758c = list;
        this.f24759d = bArr;
    }
}
