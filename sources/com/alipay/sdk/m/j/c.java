package com.alipay.sdk.m.j;

import androidx.media3.common.PlaybackException;
import com.alipay.sdk.app.OpenAuthTask;

/* loaded from: classes2.dex */
public enum c {
    SUCCEEDED(9000, "处理成功"),
    FAILED(OpenAuthTask.SYS_ERR, "系统繁忙，请稍后再试"),
    CANCELED(6001, "用户取消"),
    NETWORK_ERROR(6002, "网络连接异常"),
    ACTIVITY_NOT_START_EXIT(PlaybackException.ERROR_CODE_DRM_DEVICE_REVOKED, "支付未完成"),
    PARAMS_ERROR(4001, "参数错误"),
    DOUBLE_REQUEST(5000, "重复请求"),
    PAY_WAITTING(8000, "支付结果确认中");


    /* renamed from: a, reason: collision with root package name */
    public int f9328a;

    /* renamed from: b, reason: collision with root package name */
    public String f9329b;

    c(int i2, String str) {
        this.f9328a = i2;
        this.f9329b = str;
    }

    public void a(int i2) {
        this.f9328a = i2;
    }

    public int b() {
        return this.f9328a;
    }

    public static c b(int i2) {
        return i2 != 4001 ? i2 != 5000 ? i2 != 8000 ? i2 != 9000 ? i2 != 6001 ? i2 != 6002 ? FAILED : NETWORK_ERROR : CANCELED : SUCCEEDED : PAY_WAITTING : DOUBLE_REQUEST : PARAMS_ERROR;
    }

    public void a(String str) {
        this.f9329b = str;
    }

    public String a() {
        return this.f9329b;
    }
}
