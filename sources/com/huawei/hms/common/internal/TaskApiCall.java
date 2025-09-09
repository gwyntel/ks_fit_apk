package com.huawei.hms.common.internal;

import android.os.Parcelable;
import com.huawei.hmf.tasks.CancellationToken;
import com.huawei.hmf.tasks.TaskCompletionSource;
import com.huawei.hms.common.internal.AnyClient;
import com.huawei.hms.support.log.HMSLog;

/* loaded from: classes4.dex */
public abstract class TaskApiCall<ClientT extends AnyClient, ResultT> {

    /* renamed from: a, reason: collision with root package name */
    private final String f15992a;

    /* renamed from: b, reason: collision with root package name */
    private final String f15993b;

    /* renamed from: c, reason: collision with root package name */
    private Parcelable f15994c;

    /* renamed from: d, reason: collision with root package name */
    private String f15995d;

    /* renamed from: e, reason: collision with root package name */
    private CancellationToken f15996e;

    /* renamed from: f, reason: collision with root package name */
    private int f15997f;

    @Deprecated
    public TaskApiCall(String str, String str2) {
        this.f15997f = 1;
        this.f15992a = str;
        this.f15993b = str2;
        this.f15994c = null;
        this.f15995d = null;
    }

    protected abstract void doExecute(ClientT clientt, ResponseErrorCode responseErrorCode, String str, TaskCompletionSource<ResultT> taskCompletionSource);

    public int getApiLevel() {
        return this.f15997f;
    }

    @Deprecated
    public int getMinApkVersion() {
        return 30000000;
    }

    public Parcelable getParcelable() {
        return this.f15994c;
    }

    public String getRequestJson() {
        return this.f15993b;
    }

    public CancellationToken getToken() {
        return this.f15996e;
    }

    public String getTransactionId() {
        return this.f15995d;
    }

    public String getUri() {
        return this.f15992a;
    }

    public final void onResponse(ClientT clientt, ResponseErrorCode responseErrorCode, String str, TaskCompletionSource<ResultT> taskCompletionSource) {
        CancellationToken cancellationToken = this.f15996e;
        if (cancellationToken != null && cancellationToken.isCancellationRequested()) {
            HMSLog.i("TaskApiCall", "This Task has been canceled, uri:" + this.f15992a + ", transactionId:" + this.f15995d);
            return;
        }
        HMSLog.i("TaskApiCall", "doExecute, uri:" + this.f15992a + ", errorCode:" + responseErrorCode.getErrorCode() + ", transactionId:" + this.f15995d);
        doExecute(clientt, responseErrorCode, str, taskCompletionSource);
    }

    public void setApiLevel(int i2) {
        this.f15997f = i2;
    }

    public void setParcelable(Parcelable parcelable) {
        this.f15994c = parcelable;
    }

    public void setToken(CancellationToken cancellationToken) {
        this.f15996e = cancellationToken;
    }

    public void setTransactionId(String str) {
        this.f15995d = str;
    }

    public TaskApiCall(String str, String str2, String str3) {
        this.f15997f = 1;
        this.f15992a = str;
        this.f15993b = str2;
        this.f15994c = null;
        this.f15995d = str3;
    }

    public TaskApiCall(String str, String str2, String str3, int i2) {
        this.f15992a = str;
        this.f15993b = str2;
        this.f15994c = null;
        this.f15995d = str3;
        this.f15997f = i2;
    }
}
