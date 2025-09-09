package com.xiaomi.accountsdk.diagnosis;

/* loaded from: classes4.dex */
public class DiagnosisLog {

    /* renamed from: a, reason: collision with root package name */
    private static DiagnosisLogInterface f23290a = new d();

    private DiagnosisLog() {
    }

    public static DiagnosisLogInterface get() {
        return f23290a;
    }

    public static void set(DiagnosisLogInterface diagnosisLogInterface) {
        f23290a = diagnosisLogInterface;
    }
}
