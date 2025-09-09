package com.hihonor.push.sdk;

import com.hihonor.push.sdk.common.data.ApiException;
import com.hihonor.push.sdk.common.data.UpMsgType;

/* loaded from: classes3.dex */
public class a0 {
    public Void a(boolean z2) throws ApiException {
        try {
            g1 g1Var = new g1(z2 ? UpMsgType.TURN_ON_PUSH : UpMsgType.TURN_OFF_PUSH, null);
            g1Var.f15492e = b.a();
            b.a(z.f15569c.a(g1Var));
            return null;
        } catch (Exception e2) {
            throw b.a(e2);
        }
    }
}
