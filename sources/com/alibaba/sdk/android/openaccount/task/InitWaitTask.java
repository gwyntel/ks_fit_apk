package com.alibaba.sdk.android.openaccount.task;

import android.content.Context;
import com.alibaba.sdk.android.openaccount.callback.FailureCallback;
import com.alibaba.sdk.android.openaccount.message.MessageUtils;
import com.alibaba.sdk.android.openaccount.trace.ActionTraceLogger;
import com.alibaba.sdk.android.openaccount.trace.TraceLoggerManager;
import com.alibaba.sdk.android.openaccount.ut.UserTrackerService;
import com.alibaba.sdk.android.openaccount.util.CommonUtils;
import com.alibaba.sdk.android.pluto.Pluto;

/* loaded from: classes2.dex */
public class InitWaitTask extends TaskWithDialog<Void, Void, Void> {
    protected FailureCallback failureCallback;

    /* renamed from: r, reason: collision with root package name */
    private Runnable f8931r;
    private String taskName;

    public InitWaitTask(Context context, FailureCallback failureCallback, Runnable runnable, String str, boolean z2) {
        super(context);
        this.f8931r = runnable;
        this.failureCallback = failureCallback;
        this.taskName = str;
        this.showDialog = z2;
    }

    @Override // com.alibaba.sdk.android.openaccount.task.AbsAsyncTask
    protected void doWhenException(Throwable th) {
        CommonUtils.toastSystemException();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.alibaba.sdk.android.openaccount.task.AbsAsyncTask
    public Void asyncExecute(Void... voidArr) {
        UserTrackerService userTrackerService = (UserTrackerService) Pluto.DEFAULT_INSTANCE.getBean(UserTrackerService.class, null);
        TraceLoggerManager traceLoggerManager = TraceLoggerManager.INSTANCE;
        String str = this.taskName;
        if (str == null) {
            str = "asyncExecute";
        }
        ActionTraceLogger actionTraceLoggerBegin = traceLoggerManager.action(str).begin();
        String str2 = this.taskName;
        if (str2 != null && userTrackerService != null) {
            userTrackerService.sendCustomHit(str2, null);
        }
        Boolean boolCheckInitStatus = InitTask.checkInitStatus();
        if (boolCheckInitStatus == null) {
            actionTraceLoggerBegin.failed("error", "init failed");
            CommonUtils.onFailure(this.failureCallback, MessageUtils.createMessage(10012, new Object[0]));
        } else if (boolCheckInitStatus.booleanValue()) {
            actionTraceLoggerBegin.success();
            this.f8931r.run();
        }
        return null;
    }

    public InitWaitTask(Context context, FailureCallback failureCallback, Runnable runnable, String str) {
        super(context);
        this.f8931r = runnable;
        this.failureCallback = failureCallback;
        this.taskName = str;
    }

    public InitWaitTask(Context context, FailureCallback failureCallback, Runnable runnable) {
        this(context, failureCallback, runnable, null);
    }
}
