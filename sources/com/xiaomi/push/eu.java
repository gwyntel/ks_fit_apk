package com.xiaomi.push;

import android.content.Context;
import org.android.agoo.common.AgooConstants;

/* loaded from: classes4.dex */
public class eu extends et {
    public eu(Context context, int i2) {
        super(context, i2);
    }

    @Override // com.xiaomi.push.et
    public ip a() {
        return ip.Storage;
    }

    @Override // com.xiaomi.push.et
    public String b() {
        return "ram:" + i.m494a() + ",rom:" + i.m499b() + "|ramOriginal:" + i.c() + ",romOriginal:" + i.d();
    }

    @Override // com.xiaomi.push.ah.a
    /* renamed from: a */
    public String mo224a() {
        return AgooConstants.REPORT_DUPLICATE_FAIL;
    }
}
