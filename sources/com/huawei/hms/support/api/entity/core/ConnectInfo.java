package com.huawei.hms.support.api.entity.core;

import com.huawei.hms.core.aidl.IMessageEntity;
import com.huawei.hms.core.aidl.annotation.Packed;
import com.huawei.hms.support.api.entity.auth.Scope;
import java.util.List;

/* loaded from: classes4.dex */
public class ConnectInfo implements IMessageEntity {

    /* renamed from: a, reason: collision with root package name */
    @Packed
    private List<String> f18103a;

    /* renamed from: b, reason: collision with root package name */
    @Packed
    private List<Scope> f18104b;

    /* renamed from: c, reason: collision with root package name */
    @Packed
    private String f18105c;

    /* renamed from: d, reason: collision with root package name */
    @Packed
    private String f18106d;

    public ConnectInfo() {
    }

    public List<String> getApiNameList() {
        return this.f18103a;
    }

    public String getFingerprint() {
        return this.f18105c;
    }

    public List<Scope> getScopeList() {
        return this.f18104b;
    }

    public String getSubAppID() {
        return this.f18106d;
    }

    public void setApiNameList(List<String> list) {
        this.f18103a = list;
    }

    public void setFingerprint(String str) {
        this.f18105c = str;
    }

    public void setScopeList(List<Scope> list) {
        this.f18104b = list;
    }

    public void setSubAppID(String str) {
        this.f18106d = str;
    }

    public ConnectInfo(List<String> list, List<Scope> list2, String str, String str2) {
        this.f18103a = list;
        this.f18104b = list2;
        this.f18105c = str;
        this.f18106d = str2;
    }
}
