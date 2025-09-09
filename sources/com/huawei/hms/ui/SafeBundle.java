package com.huawei.hms.ui;

import android.os.Bundle;
import com.huawei.hms.base.ui.LogUtil;

/* loaded from: classes4.dex */
public class SafeBundle {

    /* renamed from: a, reason: collision with root package name */
    private final Bundle f18157a;

    public SafeBundle() {
        this(new Bundle());
    }

    public boolean containsKey(String str) {
        try {
            return this.f18157a.containsKey(str);
        } catch (Exception unused) {
            LogUtil.e("SafeBundle", "containsKey exception. key:");
            return false;
        }
    }

    public Object get(String str) {
        try {
            return this.f18157a.get(str);
        } catch (Exception e2) {
            LogUtil.e("SafeBundle", "get exception: " + e2.getMessage(), true);
            return null;
        }
    }

    public Bundle getBundle() {
        return this.f18157a;
    }

    public int getInt(String str) {
        return getInt(str, 0);
    }

    public String getString(String str) {
        try {
            return this.f18157a.getString(str);
        } catch (Exception e2) {
            LogUtil.e("SafeBundle", "getString exception: " + e2.getMessage(), true);
            return "";
        }
    }

    public boolean isEmpty() {
        try {
            return this.f18157a.isEmpty();
        } catch (Exception unused) {
            LogUtil.e("SafeBundle", "isEmpty exception");
            return true;
        }
    }

    public int size() {
        try {
            return this.f18157a.size();
        } catch (Exception unused) {
            LogUtil.e("SafeBundle", "size exception");
            return 0;
        }
    }

    public String toString() {
        return this.f18157a.toString();
    }

    public SafeBundle(Bundle bundle) {
        this.f18157a = bundle == null ? new Bundle() : bundle;
    }

    public int getInt(String str, int i2) {
        try {
            return this.f18157a.getInt(str, i2);
        } catch (Exception e2) {
            LogUtil.e("SafeBundle", "getInt exception: " + e2.getMessage(), true);
            return i2;
        }
    }

    public String getString(String str, String str2) {
        try {
            return this.f18157a.getString(str, str2);
        } catch (Exception e2) {
            LogUtil.e("SafeBundle", "getString exception: " + e2.getMessage(), true);
            return str2;
        }
    }
}
