package com.aliyun.alink.linksdk.tmp.connect.b;

import com.aliyun.alink.linksdk.alcs.lpbs.api.PluginHelper;
import com.aliyun.alink.linksdk.alcs.lpbs.data.group.PalGroupReqMessage;
import com.aliyun.alink.linksdk.tmp.connect.c;
import com.aliyun.alink.linksdk.tmp.connect.d;
import com.aliyun.alink.linksdk.tmp.data.auth.AccessInfo;

/* loaded from: classes2.dex */
public class a implements b {

    /* renamed from: c, reason: collision with root package name */
    private static final String f11140c = "[Tmp]GroupConnect";

    /* renamed from: a, reason: collision with root package name */
    protected AccessInfo f11141a;

    /* renamed from: b, reason: collision with root package name */
    protected String f11142b;

    public a(String str, AccessInfo accessInfo) {
        this.f11141a = accessInfo;
        this.f11142b = str;
    }

    @Override // com.aliyun.alink.linksdk.tmp.connect.b.b
    public boolean a(d dVar, c cVar) {
        return PluginHelper.asyncGroupSendRequest((PalGroupReqMessage) dVar.c(), new com.aliyun.alink.linksdk.tmp.connect.entity.a.c(cVar, dVar));
    }
}
