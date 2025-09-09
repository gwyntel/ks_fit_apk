package com.xiaomi.accountsdk.diagnosis;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import java.io.IOException;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
public class DiagnosisController {

    /* renamed from: a, reason: collision with root package name */
    private static volatile Context f23284a;

    /* renamed from: b, reason: collision with root package name */
    private static String f23285b;

    /* renamed from: c, reason: collision with root package name */
    private String f23286c;

    private class CheckDiagnosisEnabledTask extends AsyncTask<Void, Void, Boolean> {

        /* renamed from: b, reason: collision with root package name */
        private final c f23288b;

        private CheckDiagnosisEnabledTask(c cVar) {
            this.f23288b = cVar;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Boolean doInBackground(Void... voidArr) {
            return Boolean.valueOf(!TextUtils.isEmpty(DiagnosisController.this.getDiagnosisDomain()));
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(Boolean bool) {
            if (this.f23288b != null) {
                if (bool == null || !bool.booleanValue()) {
                    this.f23288b.onError();
                } else {
                    this.f23288b.onLaunch();
                }
            }
        }
    }

    private static class Holder {

        /* renamed from: a, reason: collision with root package name */
        static DiagnosisController f23289a = new DiagnosisController();

        private Holder() {
        }
    }

    private DiagnosisController() {
    }

    private static void a() {
        if (f23284a == null) {
            throw new RuntimeException("please call DiagnosisController.init() first!");
        }
    }

    private String b() {
        try {
            com.xiaomi.accountsdk.diagnosis.a.a aVarA = e.a();
            if (aVarA == null) {
                return null;
            }
            String str = aVarA.f23297b;
            this.f23286c = str;
            return str;
        } catch (IOException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static DiagnosisController get() {
        a();
        return Holder.f23289a;
    }

    public static Context getApplicationContext() {
        a();
        return f23284a;
    }

    public static String getLogName() {
        return f23285b;
    }

    public static void init(Context context, String str) {
        f23284a = context.getApplicationContext();
        DiagnosisLog.set(new a(f23284a));
        a(str);
    }

    public void checkStart(c cVar) {
        new CheckDiagnosisEnabledTask(cVar).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }

    public String getDiagnosisDomain() {
        return !TextUtils.isEmpty(this.f23286c) ? this.f23286c : b();
    }

    private static void a(String str) {
        if (!b(str)) {
            throw new IllegalArgumentException("name must be ^[A-Za-z]{0,10}$");
        }
        f23285b = str;
    }

    private static boolean b(String str) {
        return Pattern.compile("^[A-Za-z]{0,10}$").matcher(str).matches();
    }
}
