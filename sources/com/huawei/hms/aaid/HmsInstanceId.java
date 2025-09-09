package com.huawei.hms.aaid;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import android.text.TextUtils;
import com.huawei.hmf.tasks.Task;
import com.huawei.hmf.tasks.TaskCompletionSource;
import com.huawei.hmf.tasks.Tasks;
import com.huawei.hms.aaid.constant.ErrorEnum;
import com.huawei.hms.aaid.entity.AAIDResult;
import com.huawei.hms.aaid.entity.DeleteTokenReq;
import com.huawei.hms.aaid.entity.TokenReq;
import com.huawei.hms.aaid.entity.TokenResult;
import com.huawei.hms.aaid.plugin.ProxyCenter;
import com.huawei.hms.aaid.task.PushClientBuilder;
import com.huawei.hms.aaid.utils.BaseUtils;
import com.huawei.hms.aaid.utils.PushPreferences;
import com.huawei.hms.api.Api;
import com.huawei.hms.api.HuaweiApiAvailability;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.common.HuaweiApi;
import com.huawei.hms.common.internal.AbstractClientBuilder;
import com.huawei.hms.common.internal.Preconditions;
import com.huawei.hms.opendevice.a;
import com.huawei.hms.opendevice.b;
import com.huawei.hms.opendevice.d;
import com.huawei.hms.opendevice.e;
import com.huawei.hms.opendevice.f;
import com.huawei.hms.opendevice.h;
import com.huawei.hms.opendevice.i;
import com.huawei.hms.opendevice.o;
import com.huawei.hms.support.log.HMSLog;

/* loaded from: classes.dex */
public class HmsInstanceId {
    public static final String TAG = "HmsInstanceId";

    /* renamed from: a, reason: collision with root package name */
    private Context f15711a;

    /* renamed from: b, reason: collision with root package name */
    private PushPreferences f15712b;

    /* renamed from: c, reason: collision with root package name */
    private HuaweiApi<Api.ApiOptions.NoOptions> f15713c;

    private HmsInstanceId(Context context) {
        this.f15711a = context.getApplicationContext();
        this.f15712b = new PushPreferences(context, "aaid");
        Api api = new Api(HuaweiApiAvailability.HMS_API_NAME_PUSH);
        if (context instanceof Activity) {
            this.f15713c = new HuaweiApi<>((Activity) context, (Api<Api.ApiOptions>) api, (Api.ApiOptions) null, (AbstractClientBuilder) new PushClientBuilder());
        } else {
            this.f15713c = new HuaweiApi<>(context, (Api<Api.ApiOptions>) api, (Api.ApiOptions) null, new PushClientBuilder());
        }
        this.f15713c.setKitSdkVersion(60900300);
    }

    private void a(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        if (!d.e(this.f15711a)) {
            i.a(this.f15711a).removeKey("subjectId");
            return;
        }
        String string = i.a(this.f15711a).getString("subjectId");
        if (TextUtils.isEmpty(string)) {
            i.a(this.f15711a).saveString("subjectId", str);
            return;
        }
        if (string.contains(str)) {
            return;
        }
        i.a(this.f15711a).saveString("subjectId", string + "," + str);
    }

    private void b() throws ApiException {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            throw ErrorEnum.ERROR_MAIN_THREAD.toApiException();
        }
    }

    public static HmsInstanceId getInstance(Context context) {
        Preconditions.checkNotNull(context);
        o.c(context);
        return new HmsInstanceId(context);
    }

    public void deleteAAID() throws ApiException {
        b();
        try {
            if (this.f15712b.containsKey("aaid")) {
                this.f15712b.removeKey("aaid");
                this.f15712b.removeKey("creationTime");
                if (b.e(this.f15711a)) {
                    if (ProxyCenter.getProxy() != null) {
                        HMSLog.i(TAG, "use proxy delete all token after delete AaId.");
                        ProxyCenter.getProxy().deleteAllToken(this.f15711a);
                        return;
                    }
                    DeleteTokenReq deleteTokenReqB = b.b(this.f15711a);
                    deleteTokenReqB.setDeleteType(1);
                    deleteTokenReqB.setMultiSender(false);
                    a(deleteTokenReqB, 1);
                    BaseUtils.deleteAllTokenCache(this.f15711a);
                }
            }
        } catch (ApiException e2) {
            throw e2;
        } catch (Exception unused) {
            throw ErrorEnum.ERROR_INTERNAL_ERROR.toApiException();
        }
    }

    public void deleteToken(String str, String str2) throws ApiException {
        b();
        a();
        DeleteTokenReq deleteTokenReqA = b.a(this.f15711a, str, str2);
        deleteTokenReqA.setMultiSender(false);
        a(deleteTokenReqA, 1);
    }

    public Task<AAIDResult> getAAID() {
        try {
            return Tasks.callInBackground(new a(this.f15711a.getApplicationContext()));
        } catch (Exception unused) {
            TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
            taskCompletionSource.setException(ErrorEnum.ERROR_INTERNAL_ERROR.toApiException());
            return taskCompletionSource.getTask();
        }
    }

    public long getCreationTime() {
        try {
            if (!this.f15712b.containsKey("creationTime")) {
                getAAID();
            }
            return this.f15712b.getLong("creationTime");
        } catch (Exception unused) {
            return 0L;
        }
    }

    public String getId() {
        return b.c(this.f15711a);
    }

    @Deprecated
    public String getToken() {
        try {
            return getToken(null, null);
        } catch (Exception unused) {
            return null;
        }
    }

    public String getToken(String str, String str2) throws ApiException {
        b();
        a();
        TokenReq tokenReqB = b.b(this.f15711a, null, str2);
        tokenReqB.setAaid(getId());
        tokenReqB.setMultiSender(false);
        i.a(this.f15711a).saveString(this.f15711a.getPackageName(), "1");
        return a(tokenReqB, 1);
    }

    public void deleteToken(String str) throws ApiException {
        b();
        a();
        if (!TextUtils.isEmpty(str)) {
            String strD = b.d(this.f15711a);
            if (!TextUtils.isEmpty(strD)) {
                if (str.equals(strD)) {
                    deleteToken(null, null);
                    return;
                }
                DeleteTokenReq deleteTokenReqA = b.a(this.f15711a, str);
                deleteTokenReqA.setMultiSender(true);
                a(deleteTokenReqA, 2);
                return;
            }
            throw ErrorEnum.ERROR_MISSING_PROJECT_ID.toApiException();
        }
        throw ErrorEnum.ERROR_ARGUMENTS_INVALID.toApiException();
    }

    public String getToken(String str) throws ApiException {
        b();
        a();
        if (!TextUtils.isEmpty(str)) {
            String strD = b.d(this.f15711a);
            if (!TextUtils.isEmpty(strD)) {
                if (str.equals(strD)) {
                    return getToken(null, null);
                }
                TokenReq tokenReqB = b.b(this.f15711a, str);
                tokenReqB.setAaid(getId());
                tokenReqB.setMultiSender(true);
                return a(tokenReqB, 2);
            }
            throw ErrorEnum.ERROR_MISSING_PROJECT_ID.toApiException();
        }
        throw ErrorEnum.ERROR_ARGUMENTS_INVALID.toApiException();
    }

    private String a(TokenReq tokenReq, int i2) throws ApiException {
        if (ProxyCenter.getProxy() != null) {
            HMSLog.i(TAG, "use proxy get token, please check HmsMessageService.onNewToken receive result.");
            ProxyCenter.getProxy().getToken(this.f15711a, tokenReq.getSubjectId(), null);
            return null;
        }
        a(tokenReq.getSubjectId());
        String strA = h.a(this.f15711a, "push.gettoken");
        try {
            HMSLog.d(TAG, "getToken req :" + tokenReq.toString());
            f fVar = new f("push.gettoken", tokenReq, this.f15711a, strA);
            fVar.setApiLevel(i2);
            return ((TokenResult) Tasks.await(this.f15713c.doWrite(fVar))).getToken();
        } catch (Exception e2) {
            if (e2.getCause() instanceof ApiException) {
                ApiException apiException = (ApiException) e2.getCause();
                h.a(this.f15711a, "push.gettoken", strA, apiException.getStatusCode());
                throw apiException;
            }
            Context context = this.f15711a;
            ErrorEnum errorEnum = ErrorEnum.ERROR_INTERNAL_ERROR;
            h.a(context, "push.gettoken", strA, errorEnum);
            throw errorEnum.toApiException();
        }
    }

    private void a(DeleteTokenReq deleteTokenReq, int i2) throws ApiException {
        String subjectId = deleteTokenReq.getSubjectId();
        if (ProxyCenter.getProxy() != null) {
            HMSLog.i(TAG, "use proxy delete token");
            ProxyCenter.getProxy().deleteToken(this.f15711a, subjectId, null);
            return;
        }
        String strA = h.a(this.f15711a, "push.deletetoken");
        try {
            String strB = i.a(this.f15711a).b(subjectId);
            if (!deleteTokenReq.isMultiSender() || (!TextUtils.isEmpty(strB) && !strB.equals(i.a(this.f15711a).b(null)))) {
                deleteTokenReq.setToken(strB);
                e eVar = new e("push.deletetoken", deleteTokenReq, strA);
                eVar.setApiLevel(i2);
                Tasks.await(this.f15713c.doWrite(eVar));
                i.a(this.f15711a).c(subjectId);
                return;
            }
            i.a(this.f15711a).removeKey(subjectId);
            HMSLog.i(TAG, "The local subject token is null");
        } catch (Exception e2) {
            if (e2.getCause() instanceof ApiException) {
                ApiException apiException = (ApiException) e2.getCause();
                h.a(this.f15711a, "push.deletetoken", strA, apiException.getStatusCode());
                throw apiException;
            }
            Context context = this.f15711a;
            ErrorEnum errorEnum = ErrorEnum.ERROR_INTERNAL_ERROR;
            h.a(context, "push.deletetoken", strA, errorEnum);
            throw errorEnum.toApiException();
        }
    }

    private void a() throws ApiException {
        if (BaseUtils.getProxyInit(this.f15711a) && ProxyCenter.getProxy() == null && !BaseUtils.isMainProc(this.f15711a)) {
            HMSLog.e(TAG, "Operations in child processes are not supported.");
            throw ErrorEnum.ERROR_OPER_IN_CHILD_PROCESS.toApiException();
        }
    }
}
