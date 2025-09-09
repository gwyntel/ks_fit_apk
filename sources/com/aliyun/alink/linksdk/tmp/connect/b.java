package com.aliyun.alink.linksdk.tmp.connect;

import com.aliyun.alink.linksdk.tmp.utils.TmpEnum;

/* loaded from: classes2.dex */
public abstract class b implements IConnect {

    /* renamed from: a, reason: collision with root package name */
    protected TmpEnum.ConnectType f11139a = TmpEnum.ConnectType.CONNECT_TYPE_UNKNOWN;

    @Override // com.aliyun.alink.linksdk.tmp.connect.IConnect
    public boolean a(String str, int i2, Object obj) {
        return false;
    }

    @Override // com.aliyun.alink.linksdk.tmp.connect.IConnect
    public TmpEnum.ConnectType a() {
        return this.f11139a;
    }
}
