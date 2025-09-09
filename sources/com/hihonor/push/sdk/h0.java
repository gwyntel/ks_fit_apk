package com.hihonor.push.sdk;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import com.hihonor.push.framework.aidl.DataBuffer;
import com.hihonor.push.framework.aidl.IMessageEntity;
import com.hihonor.push.framework.aidl.IPushCallback;
import com.hihonor.push.framework.aidl.MessageCodec;
import com.hihonor.push.framework.aidl.entity.ResponseHeader;
import com.hihonor.push.sdk.common.data.ApiException;
import com.hihonor.push.sdk.z;

/* loaded from: classes3.dex */
public class h0 extends IPushCallback.Stub {

    /* renamed from: a, reason: collision with root package name */
    public final Object f15500a;

    /* renamed from: b, reason: collision with root package name */
    public final i0 f15501b;

    public h0(Object obj, i0 i0Var) {
        this.f15500a = obj;
        this.f15501b = i0Var;
    }

    @Override // com.hihonor.push.framework.aidl.IPushCallback
    public void onResult(DataBuffer dataBuffer) {
        Log.i("IPCCallback", "onResult parse start.");
        Bundle header = dataBuffer.getHeader();
        Bundle body = dataBuffer.getBody();
        ResponseHeader responseHeader = new ResponseHeader();
        MessageCodec.parseMessageEntity(header, responseHeader);
        Object obj = this.f15500a;
        if (obj instanceof IMessageEntity) {
            MessageCodec.parseMessageEntity(body, (IMessageEntity) obj);
        }
        i0 i0Var = this.f15501b;
        ApiException apiException = new ApiException(responseHeader.getStatusCode(), responseHeader.getStatusMessage());
        Object obj2 = this.f15500a;
        z.b bVar = (z.b) i0Var;
        bVar.getClass();
        z zVar = z.f15569c;
        f1<?> f1Var = bVar.f15578a;
        zVar.getClass();
        Log.i("HonorApiManager", "sendResolveResult start");
        Handler handler = zVar.f15570a;
        handler.sendMessage(handler.obtainMessage(2, f1Var));
        bVar.f15578a.b(apiException, obj2);
        Log.i("IPCCallback", "onResult parse end.");
    }
}
