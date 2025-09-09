package com.aliyun.alink.sdk.jsbridge.methodexport;

import android.content.Context;
import android.content.Intent;
import com.aliyun.alink.sdk.jsbridge.BoneCallback;
import com.aliyun.alink.sdk.jsbridge.IBonePlugin;
import com.aliyun.alink.sdk.jsbridge.IJSBridge;
import com.aliyun.alink.sdk.jsbridge.a;

/* loaded from: classes2.dex */
public abstract class BaseBonePlugin implements IBonePlugin {

    /* renamed from: a, reason: collision with root package name */
    a f11513a = new a();
    protected Context context;
    protected IJSBridge jsBridge;

    @Override // com.aliyun.alink.sdk.jsbridge.IBonePlugin
    public boolean call(String str, Object[] objArr, BoneCallback boneCallback) {
        return this.f11513a.a(this, str, objArr, boneCallback);
    }

    @Override // com.aliyun.alink.sdk.jsbridge.IBonePlugin
    public void destroy() {
        this.f11513a.a();
        this.context = null;
        this.jsBridge = null;
    }

    @Override // com.aliyun.alink.sdk.jsbridge.IBonePlugin
    public void onActivityResult(int i2, int i3, Intent intent) {
    }

    @Override // com.aliyun.alink.sdk.jsbridge.IBonePlugin
    public void onDestroy() {
    }

    @Override // com.aliyun.alink.sdk.jsbridge.IBonePlugin
    public void onInitialize(Context context, IJSBridge iJSBridge) throws SecurityException {
        this.context = context;
        this.jsBridge = iJSBridge;
        this.f11513a.a(this);
    }

    @Override // com.aliyun.alink.sdk.jsbridge.IBonePlugin
    public void onPause() {
    }

    @Override // com.aliyun.alink.sdk.jsbridge.IBonePlugin
    public void onResume() {
    }
}
