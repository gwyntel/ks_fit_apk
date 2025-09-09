package com.umeng.analytics.pro;

import com.google.android.gms.fitness.FitnessStatusCodes;

/* loaded from: classes4.dex */
public enum bi {
    UnKnownCode(5000),
    Timeout(5001),
    NetworkUnavailable(5002),
    SSLException(5003),
    IOException(5004),
    UnKnownHostException(FitnessStatusCodes.UNKNOWN_AUTH_ERROR),
    HttpError(FitnessStatusCodes.MISSING_BLE_PERMISSION),
    EmptyResponse(FitnessStatusCodes.UNSUPPORTED_PLATFORM),
    ErrorResponse(FitnessStatusCodes.TRANSIENT_ERROR),
    ErrorMakeRequestBody(FitnessStatusCodes.EQUIVALENT_SESSION_ENDED);


    /* renamed from: k, reason: collision with root package name */
    private final int f21509k;

    bi(int i2) {
        this.f21509k = i2;
    }

    private String b() {
        return "错误码：" + this.f21509k + " 错误信息：";
    }

    public String a() {
        if (this == UnKnownCode) {
            return b() + "--未知错误--";
        }
        if (this == Timeout) {
            return b() + "--连接超时--";
        }
        if (this == NetworkUnavailable) {
            return b() + "--网络不可用--";
        }
        if (this == SSLException) {
            return b() + "--SSL证书认证失败--";
        }
        if (this == IOException) {
            return b() + "--IO异常--";
        }
        if (this == HttpError) {
            return b() + "--服务端返回HTTP错误--";
        }
        if (this == EmptyResponse) {
            return b() + "--服务端返回数据为空--";
        }
        if (this == ErrorResponse) {
            return b() + "--服务端返回错误数据--";
        }
        if (this != ErrorMakeRequestBody) {
            return "unknown";
        }
        return b() + "--请求报文构建错误--";
    }
}
