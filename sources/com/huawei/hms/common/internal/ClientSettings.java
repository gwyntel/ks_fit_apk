package com.huawei.hms.common.internal;

import android.app.Activity;
import com.huawei.hms.support.api.client.SubAppInfo;
import com.huawei.hms.support.api.entity.auth.Scope;
import java.lang.ref.WeakReference;
import java.util.List;

/* loaded from: classes4.dex */
public class ClientSettings {

    /* renamed from: a, reason: collision with root package name */
    private String f15961a;

    /* renamed from: b, reason: collision with root package name */
    private String f15962b;

    /* renamed from: c, reason: collision with root package name */
    private List<Scope> f15963c;

    /* renamed from: d, reason: collision with root package name */
    private String f15964d;

    /* renamed from: e, reason: collision with root package name */
    private List<String> f15965e;

    /* renamed from: f, reason: collision with root package name */
    private String f15966f;

    /* renamed from: g, reason: collision with root package name */
    private SubAppInfo f15967g;

    /* renamed from: h, reason: collision with root package name */
    private WeakReference<Activity> f15968h;

    /* renamed from: i, reason: collision with root package name */
    private boolean f15969i;

    /* renamed from: j, reason: collision with root package name */
    private String f15970j;

    /* renamed from: k, reason: collision with root package name */
    private boolean f15971k;

    public ClientSettings(String str, String str2, List<Scope> list, String str3, List<String> list2) {
        this.f15961a = str;
        this.f15962b = str2;
        this.f15963c = list;
        this.f15964d = str3;
        this.f15965e = list2;
    }

    public List<String> getApiName() {
        return this.f15965e;
    }

    public String getAppID() {
        return this.f15964d;
    }

    public String getClientClassName() {
        return this.f15962b;
    }

    public String getClientPackageName() {
        return this.f15961a;
    }

    public Activity getCpActivity() {
        WeakReference<Activity> weakReference = this.f15968h;
        if (weakReference == null) {
            return null;
        }
        return weakReference.get();
    }

    public String getCpID() {
        return this.f15966f;
    }

    public String getInnerHmsPkg() {
        return this.f15970j;
    }

    public List<Scope> getScopes() {
        return this.f15963c;
    }

    public SubAppInfo getSubAppID() {
        return this.f15967g;
    }

    public boolean isHasActivity() {
        return this.f15969i;
    }

    public boolean isUseInnerHms() {
        return this.f15971k;
    }

    public void setApiName(List<String> list) {
        this.f15965e = list;
    }

    public void setAppID(String str) {
        this.f15964d = str;
    }

    public void setClientClassName(String str) {
        this.f15962b = str;
    }

    public void setClientPackageName(String str) {
        this.f15961a = str;
    }

    public void setCpActivity(Activity activity) {
        this.f15968h = new WeakReference<>(activity);
        this.f15969i = true;
    }

    public void setCpID(String str) {
        this.f15966f = str;
    }

    public void setInnerHmsPkg(String str) {
        this.f15970j = str;
    }

    public void setScopes(List<Scope> list) {
        this.f15963c = list;
    }

    public void setSubAppId(SubAppInfo subAppInfo) {
        this.f15967g = subAppInfo;
    }

    public void setUseInnerHms(boolean z2) {
        this.f15971k = z2;
    }

    public ClientSettings(String str, String str2, List<Scope> list, String str3, List<String> list2, SubAppInfo subAppInfo) {
        this(str, str2, list, str3, list2);
        this.f15967g = subAppInfo;
    }
}
