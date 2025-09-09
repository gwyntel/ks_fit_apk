package com.aliyun.alink.business.devicecenter.provision.core;

import android.util.Log;
import com.alibaba.fastjson.JSONArray;
import com.aliyun.alink.business.devicecenter.biz.ProvisionRepositoryV2;
import com.aliyun.alink.business.devicecenter.provision.core.mesh.ConcurrentGateMeshStrategy;

/* loaded from: classes2.dex */
public class O implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ ConcurrentGateMeshStrategy f10541a;

    public O(ConcurrentGateMeshStrategy concurrentGateMeshStrategy) {
        this.f10541a = concurrentGateMeshStrategy;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.f10541a.provisionHasStopped.get() || this.f10541a.mTaskIds == null || this.f10541a.mTaskIds.size() == 0) {
            return;
        }
        JSONArray jSONArray = new JSONArray();
        for (String str : this.f10541a.mTaskIds) {
            Log.d(ConcurrentGateMeshStrategy.TAG, "run: s=" + str);
            jSONArray.add(str);
        }
        ProvisionRepositoryV2.getBatchMeshProvisionResult(jSONArray, new N(this));
    }
}
