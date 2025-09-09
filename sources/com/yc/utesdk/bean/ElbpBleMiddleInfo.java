package com.yc.utesdk.bean;

import java.util.List;

/* loaded from: classes4.dex */
public class ElbpBleMiddleInfo implements Comparable<ElbpBleMiddleInfo> {

    /* renamed from: a, reason: collision with root package name */
    String f24742a;

    /* renamed from: b, reason: collision with root package name */
    byte[] f24743b;

    /* renamed from: c, reason: collision with root package name */
    List f24744c;

    public ElbpBleMiddleInfo() {
    }

    @Override // java.lang.Comparable
    public int compareTo(ElbpBleMiddleInfo elbpBleMiddleInfo) {
        return elbpBleMiddleInfo.getStartDate().compareTo(getStartDate());
    }

    public byte[] getMiddleData() {
        return this.f24743b;
    }

    public List<List<Double>> getMiddleDataList() {
        return this.f24744c;
    }

    public String getStartDate() {
        return this.f24742a;
    }

    public void setMiddleData(byte[] bArr) {
        this.f24743b = bArr;
    }

    public void setMiddleDataList(List<List<Double>> list) {
        this.f24744c = list;
    }

    public void setStartDate(String str) {
        this.f24742a = str;
    }

    public ElbpBleMiddleInfo(String str, byte[] bArr) {
        this.f24742a = str;
        this.f24743b = bArr;
    }

    public ElbpBleMiddleInfo(String str, byte[] bArr, List<List<Double>> list) {
        this.f24742a = str;
        this.f24743b = bArr;
        this.f24744c = list;
    }
}
