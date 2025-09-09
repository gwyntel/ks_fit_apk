package com.alibaba.sdk.android.openaccount.task;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import com.alibaba.sdk.android.openaccount.OpenAccountConstants;
import com.alibaba.sdk.android.openaccount.trace.AliSDKLogger;
import com.alibaba.sdk.android.openaccount.util.ResourceUtils;
import com.alibaba.sdk.android.openaccount.widget.ProgressDialog;
import com.alibaba.sdk.android.pluto.Pluto;

/* loaded from: classes2.dex */
public abstract class TaskWithDialog<Params, Progress, Result> extends AbsAsyncTask<Params, Progress, Result> {
    protected Context context;
    protected ProgressDialog progressDialog;
    protected boolean showDialog = true;
    protected boolean showToast = true;
    protected boolean doFinally = false;

    public TaskWithDialog(Context context) {
        this.context = context;
        Pluto.DEFAULT_INSTANCE.inject(this);
    }

    public void dismissProgressDialog() {
        this.executorService.postUITask(new Runnable() { // from class: com.alibaba.sdk.android.openaccount.task.TaskWithDialog.2
            @Override // java.lang.Runnable
            public void run() {
                ProgressDialog progressDialog = TaskWithDialog.this.progressDialog;
                if (progressDialog == null || !progressDialog.isShowing()) {
                    return;
                }
                try {
                    try {
                        TaskWithDialog.this.progressDialog.dismiss();
                    } catch (Exception e2) {
                        AliSDKLogger.e(OpenAccountConstants.LOG_TAG, e2.getMessage(), e2);
                    }
                } finally {
                    TaskWithDialog.this.progressDialog = null;
                }
            }
        });
    }

    @Override // com.alibaba.sdk.android.openaccount.task.AbsAsyncTask
    protected void doFinally() {
        this.doFinally = true;
        if (this.progressDialog != null) {
            dismissProgressDialog();
        }
    }

    protected boolean needProgressDialog() {
        return this.showDialog;
    }

    @Override // android.os.AsyncTask
    protected void onPreExecute() {
        if (this.context != null) {
            showProgressDialog(ResourceUtils.getString("ali_sdk_openaccount_dynamic_loading_progress_message"), true, null, true);
        }
    }

    public void showProgressDialog(final String str, final boolean z2, final DialogInterface.OnCancelListener onCancelListener, final boolean z3) {
        dismissProgressDialog();
        this.executorService.postUITask(new Runnable() { // from class: com.alibaba.sdk.android.openaccount.task.TaskWithDialog.1
            @Override // java.lang.Runnable
            public void run() {
                Context context = TaskWithDialog.this.context;
                if (context != null && (context instanceof Activity)) {
                    if ((context instanceof Activity) && ((Activity) context).isFinishing()) {
                        return;
                    }
                    TaskWithDialog taskWithDialog = TaskWithDialog.this;
                    if (!taskWithDialog.doFinally && taskWithDialog.needProgressDialog()) {
                        TaskWithDialog.this.progressDialog = new ProgressDialog(TaskWithDialog.this.context);
                        TaskWithDialog.this.progressDialog.setMessage(str);
                        TaskWithDialog.this.progressDialog.setProgressVisiable(z3);
                        TaskWithDialog.this.progressDialog.setCancelable(z2);
                        TaskWithDialog.this.progressDialog.setOnCancelListener(onCancelListener);
                        TaskWithDialog.this.progressDialog.show();
                        TaskWithDialog.this.progressDialog.setCanceledOnTouchOutside(false);
                    }
                }
            }
        });
    }
}
