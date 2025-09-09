package com.xiaomi.push;

import com.xiaomi.push.cr;

/* loaded from: classes4.dex */
public class cn extends cr.d {

    /* renamed from: a, reason: collision with root package name */
    protected String f23539a;

    public cn(String str, String str2, String[] strArr, String str3) {
        super(str, str2, strArr);
        this.f23539a = str3;
    }

    public static cn a(String str) {
        return new cn(str, "status = ?", new String[]{String.valueOf(2)}, "a job build to delete uploaded job");
    }
}
