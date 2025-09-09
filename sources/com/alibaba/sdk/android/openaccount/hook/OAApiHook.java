package com.alibaba.sdk.android.openaccount.hook;

import com.alibaba.sdk.android.openaccount.rpc.model.RpcRequest;
import com.alibaba.sdk.android.openaccount.rpc.model.RpcResponse;

/* loaded from: classes2.dex */
public interface OAApiHook {
    RpcResponse onInterceptRequest(RpcRequest rpcRequest);

    boolean onInterceptResponse(RpcRequest rpcRequest, RpcResponse rpcResponse);
}
