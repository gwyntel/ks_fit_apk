package com.huawei.hms.support.api.client;

import com.huawei.hms.common.api.Releasable;
import com.huawei.hms.support.api.client.Result;
import com.huawei.hms.support.log.HMSLog;

@Deprecated
/* loaded from: classes4.dex */
public abstract class ResultCallbacks<R extends Result> implements ResultCallback<R> {
    private static final String TAG = "ResultCallbacks";

    public abstract void onFailure(Status status);

    public abstract void onSuccess(R r2);

    @Override // com.huawei.hms.support.api.client.ResultCallback
    public final void onResult(R r2) {
        try {
            Status status = r2.getStatus();
            if (status.isSuccess()) {
                onSuccess(r2);
            } else {
                onFailure(status);
                if (r2 instanceof Releasable) {
                    ((Releasable) r2).release();
                }
            }
        } catch (Exception e2) {
            HMSLog.w(TAG, "Failed to release " + r2 + ", reason: " + e2);
        }
    }
}
