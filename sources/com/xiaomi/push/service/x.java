package com.xiaomi.push.service;

import android.content.Context;
import com.aliyun.alink.linksdk.alcs.lpbs.api.AlcsPalConst;
import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.push.hm;
import com.xiaomi.push.service.XMPushService;
import com.xiaomi.push.service.XMPushService.b;
import com.xiaomi.push.service.bf;
import java.util.Collection;
import java.util.Objects;

/* loaded from: classes4.dex */
public class x extends XMPushService.j {

    /* renamed from: a, reason: collision with root package name */
    private XMPushService f24633a;

    /* renamed from: a, reason: collision with other field name */
    private String f1115a;

    /* renamed from: a, reason: collision with other field name */
    private byte[] f1116a;

    /* renamed from: b, reason: collision with root package name */
    private String f24634b;

    /* renamed from: c, reason: collision with root package name */
    private String f24635c;

    public x(XMPushService xMPushService, String str, String str2, String str3, byte[] bArr) {
        super(9);
        this.f24633a = xMPushService;
        this.f1115a = str;
        this.f1116a = bArr;
        this.f24634b = str2;
        this.f24635c = str3;
    }

    @Override // com.xiaomi.push.service.XMPushService.j
    /* renamed from: a */
    public void mo433a() {
        bf.b next;
        u uVarM802a = v.m802a((Context) this.f24633a);
        if (uVarM802a == null) {
            try {
                uVarM802a = v.a(this.f24633a, this.f1115a, this.f24634b, this.f24635c);
            } catch (Exception e2) {
                com.xiaomi.channel.commonutils.logger.b.d("fail to register push account. " + e2);
            }
        }
        if (uVarM802a == null) {
            com.xiaomi.channel.commonutils.logger.b.d("no account for registration.");
            y.a(this.f24633a, ErrorCode.ERROR_AUTHERICATION_ERROR, "no account.");
            return;
        }
        com.xiaomi.channel.commonutils.logger.b.m91a("do registration now.");
        Collection<bf.b> collectionM755a = bf.a().m755a(AlcsPalConst.MODEL_TYPE_TGMESH);
        if (collectionM755a.isEmpty()) {
            next = uVarM802a.a(this.f24633a);
            ai.a(this.f24633a, next);
            bf.a().a(next);
        } else {
            next = collectionM755a.iterator().next();
        }
        if (!this.f24633a.m716c()) {
            y.a(this.f1115a, this.f1116a);
            this.f24633a.a(true);
            return;
        }
        try {
            bf.c cVar = next.f1038a;
            if (cVar == bf.c.binded) {
                ai.a(this.f24633a, this.f1115a, this.f1116a);
            } else if (cVar == bf.c.unbind) {
                y.a(this.f1115a, this.f1116a);
                XMPushService xMPushService = this.f24633a;
                Objects.requireNonNull(xMPushService);
                xMPushService.a(xMPushService.new b(next));
            }
        } catch (hm e3) {
            com.xiaomi.channel.commonutils.logger.b.d("meet error, disconnect connection. " + e3);
            this.f24633a.a(10, e3);
        }
    }

    @Override // com.xiaomi.push.service.XMPushService.j
    public String a() {
        return "register app";
    }
}
