package com.alipay.android.phone.mrpc.core.a;

import com.alipay.android.phone.mrpc.core.RpcException;
import com.alipay.sdk.m.u.l;
import java.lang.reflect.Type;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class d extends a {
    public d(Type type, byte[] bArr) {
        super(type, bArr);
    }

    @Override // com.alipay.android.phone.mrpc.core.a.c
    public final Object a() throws JSONException {
        try {
            String str = new String(this.f8968b);
            Thread.currentThread().getId();
            JSONObject jSONObject = new JSONObject(str);
            int i2 = jSONObject.getInt(l.f9812a);
            if (i2 == 1000) {
                return this.f8967a == String.class ? jSONObject.optString("result") : com.alipay.sdk.m.e.e.a(jSONObject.optString("result"), this.f8967a);
            }
            throw new RpcException(Integer.valueOf(i2), jSONObject.optString("tips"));
        } catch (Exception e2) {
            StringBuilder sb = new StringBuilder("response  =");
            sb.append(new String(this.f8968b));
            sb.append(":");
            sb.append(e2);
            throw new RpcException((Integer) 10, sb.toString() == null ? "" : e2.getMessage());
        }
    }
}
