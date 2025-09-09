package com.yc.utesdk.bean;

import java.util.List;

/* loaded from: classes4.dex */
public class ElbpBlePpgInfo implements Comparable<ElbpBlePpgInfo> {

    /* renamed from: a, reason: collision with root package name */
    String f24745a;

    /* renamed from: b, reason: collision with root package name */
    byte[] f24746b;

    /* renamed from: c, reason: collision with root package name */
    List f24747c;

    public ElbpBlePpgInfo() {
    }

    @Override // java.lang.Comparable
    public int compareTo(ElbpBlePpgInfo elbpBlePpgInfo) {
        return elbpBlePpgInfo.getStartDate().compareTo(getStartDate());
    }

    public byte[] getPpgData() {
        return this.f24746b;
    }

    public List<List<Integer>> getPpgDataList() {
        return this.f24747c;
    }

    public String getStartDate() {
        return this.f24745a;
    }

    public void setPpgData(byte[] bArr) {
        this.f24746b = bArr;
    }

    public void setPpgDataList(List<List<Integer>> list) {
        this.f24747c = list;
    }

    public void setStartDate(String str) {
        this.f24745a = str;
    }

    public ElbpBlePpgInfo(String str, byte[] bArr) {
        this.f24745a = str;
        this.f24746b = bArr;
    }

    public ElbpBlePpgInfo(String str, byte[] bArr, List<List<Integer>> list) {
        this.f24745a = str;
        this.f24746b = bArr;
        this.f24747c = list;
    }
}
