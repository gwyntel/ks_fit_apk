package com.aliyun.alink.linksdk.tmp.connect.a;

import android.text.TextUtils;
import com.aliyun.alink.linksdk.cmp.api.CommonRequest;
import com.aliyun.alink.linksdk.tmp.connect.CommonRequestBuilder;
import com.aliyun.alink.linksdk.tmp.utils.GsonUtils;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class j<Builder, Payload> extends CommonRequestBuilder<Builder, Payload> {

    /* renamed from: m, reason: collision with root package name */
    private static final String f11126m = "[Tmp]TmpRequestBuilder";

    /* renamed from: o, reason: collision with root package name */
    protected Boolean f11127o;

    /* renamed from: p, reason: collision with root package name */
    protected String f11128p;

    /* renamed from: q, reason: collision with root package name */
    protected String f11129q;

    /* renamed from: r, reason: collision with root package name */
    protected String f11130r;

    /* renamed from: u, reason: collision with root package name */
    protected int f11133u;

    /* renamed from: v, reason: collision with root package name */
    protected String f11134v;

    /* renamed from: w, reason: collision with root package name */
    protected String f11135w;

    /* renamed from: t, reason: collision with root package name */
    protected boolean f11132t = false;

    /* renamed from: s, reason: collision with root package name */
    protected String f11131s = "sys";

    public void b(boolean z2) {
        this.f11132t = z2;
    }

    public Builder c(boolean z2) {
        this.f11127o = Boolean.valueOf(z2);
        return this.f11110k;
    }

    public boolean e() {
        return this.f11132t;
    }

    public String f() {
        return this.f11128p;
    }

    public String g() {
        return this.f11129q;
    }

    public String h() {
        return this.f11130r;
    }

    public String i() {
        return this.f11134v;
    }

    public Builder j(String str) {
        this.f11131s = str;
        return this.f11110k;
    }

    public Builder k(String str) {
        this.f11128p = str;
        return this.f11110k;
    }

    public Builder l(String str) {
        this.f11129q = str;
        return this.f11110k;
    }

    public Builder m(String str) {
        this.f11130r = str;
        return this.f11110k;
    }

    public Builder n(String str) {
        this.f11134v = str;
        return this.f11110k;
    }

    public Builder o(String str) {
        this.f11135w = str;
        return this.f11110k;
    }

    public Builder b(int i2) {
        this.f11133u = i2;
        return this.f11110k;
    }

    @Override // com.aliyun.alink.linksdk.tmp.connect.CommonRequestBuilder
    public com.aliyun.alink.linksdk.tmp.connect.d c() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        CommonRequest commonRequest = new CommonRequest();
        commonRequest.ip = this.f11104e;
        commonRequest.port = this.f11105f;
        commonRequest.topic = CommonRequestBuilder.a(f(), g(), h(), this.f11131s);
        commonRequest.mothod = b().toCRMethod();
        commonRequest.payload = TextUtils.isEmpty(this.f11106g) ? GsonUtils.toJson(this.f11111l) : this.f11106g;
        commonRequest.context = this.f11102c;
        commonRequest.type = Integer.valueOf(this.f11133u);
        commonRequest.isSecurity = this.f11107h;
        commonRequest.iotId = this.f11134v;
        commonRequest.tgMeshType = this.f11135w;
        com.aliyun.alink.linksdk.tmp.connect.entity.cmp.h hVar = new com.aliyun.alink.linksdk.tmp.connect.entity.cmp.h(commonRequest);
        hVar.a(f());
        hVar.b(g());
        hVar.a(this.f11102c);
        hVar.a(this.f11107h);
        ALog.d(f11126m, "createRequest payload: " + commonRequest.payload);
        return hVar;
    }

    public String j() {
        return this.f11135w;
    }
}
