package com.xiaomi.accountsdk.diagnosis;

import android.content.Context;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class a implements DiagnosisLogInterface {

    /* renamed from: b, reason: collision with root package name */
    private static DiagnosisLogInterface f23293b = new f();

    /* renamed from: c, reason: collision with root package name */
    private static DiagnosisLogInterface f23294c = new d();

    /* renamed from: a, reason: collision with root package name */
    private Context f23295a;

    public a(Context context) {
        this.f23295a = context;
    }

    private DiagnosisLogInterface a() {
        return a(this.f23295a) ? f23293b : f23294c;
    }

    @Override // com.xiaomi.accountsdk.diagnosis.DiagnosisLogInterface
    public void log(String str) {
        a().log(str);
    }

    @Override // com.xiaomi.accountsdk.diagnosis.DiagnosisLogInterface
    public String logGetRequest(String str, Map<String, String> map, String str2, Map<String, String> map2, Map<String, String> map3) {
        return a().logGetRequest(str, map, str2, map2, map3);
    }

    @Override // com.xiaomi.accountsdk.diagnosis.DiagnosisLogInterface
    public String logPostRequest(String str, Map<String, String> map, String str2, Map<String, String> map2, Map<String, String> map3, Map<String, String> map4) {
        return a().logPostRequest(str, map, str2, map2, map3, map4);
    }

    @Override // com.xiaomi.accountsdk.diagnosis.DiagnosisLogInterface
    public void logRequestException(Exception exc) {
        a().logRequestException(exc);
    }

    @Override // com.xiaomi.accountsdk.diagnosis.DiagnosisLogInterface
    public void logResponse(String str, String str2, Map<String, List<String>> map, Map<String, String> map2) {
        a().logResponse(str, str2, map, map2);
    }

    @Override // com.xiaomi.accountsdk.diagnosis.DiagnosisLogInterface
    public void logResponseCode(String str, int i2) {
        a().logResponseCode(str, i2);
    }

    @Override // com.xiaomi.accountsdk.diagnosis.DiagnosisLogInterface
    public void logResponseDecryptedBody(String str) {
        a().logResponseDecryptedBody(str);
    }

    private static boolean a(Context context) {
        return com.xiaomi.accountsdk.diagnosis.e.a.a(context);
    }
}
