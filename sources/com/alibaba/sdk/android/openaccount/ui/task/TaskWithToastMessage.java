package com.alibaba.sdk.android.openaccount.ui.task;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import com.alibaba.sdk.android.openaccount.model.Result;
import com.alibaba.sdk.android.openaccount.task.TaskWithDialog;
import com.alibaba.sdk.android.openaccount.ui.util.ToastUtils;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public abstract class TaskWithToastMessage<T> extends TaskWithDialog<Void, Void, Result<T>> {
    public TaskWithToastMessage(Activity activity) {
        super(activity);
        this.showDialog = true;
    }

    protected abstract void doFailAfterToast(Result<T> result);

    protected abstract void doSuccessAfterToast(Result<T> result);

    @Override // com.alibaba.sdk.android.openaccount.task.AbsAsyncTask
    protected void doWhenException(Throwable th) {
    }

    @Override // com.alibaba.sdk.android.openaccount.task.TaskWithDialog
    protected boolean needProgressDialog() {
        return this.showDialog;
    }

    protected abstract T parseData(JSONObject jSONObject);

    protected Result<T> parseJsonResult(Result<JSONObject> result) {
        JSONObject jSONObject = result.data;
        return jSONObject == null ? Result.result(result.code, result.message) : Result.result(result.code, result.message, parseData(jSONObject));
    }

    protected boolean toastMessageRequired(Result<T> result) {
        return this.showDialog;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public void onPostExecute(Result<T> result) {
        super.onPostExecute((TaskWithToastMessage<T>) result);
        try {
            if (result == null) {
                if (this.showDialog) {
                    ToastUtils.toastSystemError(this.context);
                }
            } else {
                if (result.code == 1) {
                    doSuccessAfterToast(result);
                    return;
                }
                if (toastMessageRequired(result)) {
                    ToastUtils.toast(this.context, result.message, result.code);
                }
                doFailAfterToast(result);
            }
        } catch (Throwable th) {
            Log.e("TaskWithToastMessage", "after post execute error", th);
            ToastUtils.toastSystemError(this.context);
        }
    }

    public TaskWithToastMessage(Context context) {
        super(context);
        this.showDialog = true;
    }
}
