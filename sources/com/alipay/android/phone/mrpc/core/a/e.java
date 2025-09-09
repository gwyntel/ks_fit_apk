package com.alipay.android.phone.mrpc.core.a;

import com.alipay.android.phone.mrpc.core.RpcException;
import java.util.ArrayList;
import java.util.Objects;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

/* loaded from: classes2.dex */
public final class e extends b {

    /* renamed from: c, reason: collision with root package name */
    public int f8971c;

    /* renamed from: d, reason: collision with root package name */
    public Object f8972d;

    public e(int i2, String str, Object obj) {
        super(str, obj);
        this.f8971c = i2;
    }

    @Override // com.alipay.android.phone.mrpc.core.a.f
    public final void a(Object obj) {
        this.f8972d = obj;
    }

    @Override // com.alipay.android.phone.mrpc.core.a.f
    public final byte[] a() {
        try {
            ArrayList arrayList = new ArrayList();
            if (this.f8972d != null) {
                arrayList.add(new BasicNameValuePair("extParam", com.alipay.sdk.m.e.f.a(this.f8972d)));
            }
            arrayList.add(new BasicNameValuePair("operationType", this.f8969a));
            StringBuilder sb = new StringBuilder();
            sb.append(this.f8971c);
            arrayList.add(new BasicNameValuePair("id", sb.toString()));
            Objects.toString(this.f8970b);
            Object obj = this.f8970b;
            arrayList.add(new BasicNameValuePair("requestData", obj == null ? "[]" : com.alipay.sdk.m.e.f.a(obj)));
            return URLEncodedUtils.format(arrayList, "utf-8").getBytes();
        } catch (Exception e2) {
            StringBuilder sb2 = new StringBuilder("request  =");
            sb2.append(this.f8970b);
            sb2.append(":");
            sb2.append(e2);
            throw new RpcException(9, sb2.toString() == null ? "" : e2.getMessage(), e2);
        }
    }
}
