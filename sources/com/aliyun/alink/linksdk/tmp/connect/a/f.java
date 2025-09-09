package com.aliyun.alink.linksdk.tmp.connect.a;

import com.aliyun.alink.linksdk.tmp.device.payload.permission.GroupAuthRequestPayload;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;

/* loaded from: classes2.dex */
public class f extends j<f, GroupAuthRequestPayload> {
    /* JADX WARN: Type inference failed for: r1v0, types: [Payload, com.aliyun.alink.linksdk.tmp.device.payload.CommonRequestPayload, com.aliyun.alink.linksdk.tmp.device.payload.permission.GroupAuthRequestPayload] */
    public f(String str, String str2) {
        k(str);
        l(str2);
        j("dev");
        m(TmpConstant.METHOD_SERVICE_AUTH_INFO);
        ?? groupAuthRequestPayload = new GroupAuthRequestPayload(str, str2);
        this.f11111l = groupAuthRequestPayload;
        groupAuthRequestPayload.setMethod(TmpConstant.METHOD_SERVICE_AUTH_INFO);
    }

    public static f a(String str, String str2) {
        return new f(str, str2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public f e(String str) {
        ((GroupAuthRequestPayload) this.f11111l).setOp(str);
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public f f(String str) {
        ((GroupAuthRequestPayload) this.f11111l).setDataType(str);
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public f g(String str) {
        ((GroupAuthRequestPayload) this.f11111l).setGroupId(str);
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public f h(String str) {
        ((GroupAuthRequestPayload) this.f11111l).setParam1(str);
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public f i(String str) {
        ((GroupAuthRequestPayload) this.f11111l).setParam2(str);
        return this;
    }
}
