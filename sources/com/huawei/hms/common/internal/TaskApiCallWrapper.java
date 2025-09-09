package com.huawei.hms.common.internal;

import com.huawei.hmf.tasks.TaskCompletionSource;

/* loaded from: classes4.dex */
public class TaskApiCallWrapper<TResult> extends BaseContentWrapper {

    /* renamed from: a, reason: collision with root package name */
    private final TaskApiCall<? extends AnyClient, TResult> f15998a;

    /* renamed from: b, reason: collision with root package name */
    private final TaskCompletionSource<TResult> f15999b;

    public TaskApiCallWrapper(TaskApiCall<? extends AnyClient, TResult> taskApiCall, TaskCompletionSource<TResult> taskCompletionSource) {
        super(1);
        this.f15998a = taskApiCall;
        this.f15999b = taskCompletionSource;
    }

    public TaskApiCall<? extends AnyClient, TResult> getTaskApiCall() {
        return this.f15998a;
    }

    public TaskCompletionSource<TResult> getTaskCompletionSource() {
        return this.f15999b;
    }
}
