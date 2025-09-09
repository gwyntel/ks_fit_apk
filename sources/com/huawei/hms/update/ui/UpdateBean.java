package com.huawei.hms.update.ui;

import java.io.Serializable;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class UpdateBean implements Serializable {

    /* renamed from: a, reason: collision with root package name */
    private boolean f18164a;

    /* renamed from: b, reason: collision with root package name */
    private boolean f18165b;

    /* renamed from: c, reason: collision with root package name */
    private String f18166c;

    /* renamed from: d, reason: collision with root package name */
    private int f18167d;

    /* renamed from: e, reason: collision with root package name */
    private String f18168e;

    /* renamed from: f, reason: collision with root package name */
    private String f18169f;

    /* renamed from: g, reason: collision with root package name */
    private ArrayList f18170g;

    /* renamed from: h, reason: collision with root package name */
    private boolean f18171h = true;

    private static <T> T a(T t2) {
        return t2;
    }

    public String getClientAppId() {
        return (String) a(this.f18168e);
    }

    public String getClientAppName() {
        return (String) a(this.f18169f);
    }

    public String getClientPackageName() {
        return (String) a(this.f18166c);
    }

    public int getClientVersionCode() {
        return ((Integer) a(Integer.valueOf(this.f18167d))).intValue();
    }

    public boolean getResolutionInstallHMS() {
        return this.f18165b;
    }

    public ArrayList getTypeList() {
        return (ArrayList) a(this.f18170g);
    }

    public boolean isHmsOrApkUpgrade() {
        return ((Boolean) a(Boolean.valueOf(this.f18164a))).booleanValue();
    }

    public boolean isNeedConfirm() {
        return ((Boolean) a(Boolean.valueOf(this.f18171h))).booleanValue();
    }

    public void setClientAppId(String str) {
        this.f18168e = str;
    }

    public void setClientAppName(String str) {
        this.f18169f = str;
    }

    public void setClientPackageName(String str) {
        this.f18166c = str;
    }

    public void setClientVersionCode(int i2) {
        this.f18167d = i2;
    }

    public void setHmsOrApkUpgrade(boolean z2) {
        this.f18164a = z2;
    }

    public void setNeedConfirm(boolean z2) {
        this.f18171h = z2;
    }

    public void setResolutionInstallHMS(boolean z2) {
        this.f18165b = z2;
    }

    public void setTypeList(ArrayList arrayList) {
        this.f18170g = arrayList;
    }
}
