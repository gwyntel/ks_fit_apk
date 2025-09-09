package com.xiaomi.accountsdk.diagnosis.c;

import android.os.AsyncTask;
import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;
import com.xiaomi.accountsdk.diagnosis.DiagnosisController;
import com.xiaomi.accountsdk.diagnosis.e.c;
import com.xiaomi.accountsdk.diagnosis.e.e;
import com.xiaomi.accountsdk.diagnosis.e.f;
import com.xiaomi.infra.galaxy.fds.android.model.PutObjectResult;
import com.xiaomi.mipush.sdk.Constants;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

/* loaded from: classes4.dex */
public class a extends AsyncTask<Void, Void, String> {

    /* renamed from: a, reason: collision with root package name */
    private final InterfaceC0191a f23308a;

    /* renamed from: b, reason: collision with root package name */
    private final boolean f23309b;

    /* renamed from: c, reason: collision with root package name */
    private Random f23310c = new Random();

    /* renamed from: com.xiaomi.accountsdk.diagnosis.c.a$a, reason: collision with other inner class name */
    public interface InterfaceC0191a {
        void onFinished(boolean z2, String str);
    }

    public a(InterfaceC0191a interfaceC0191a, boolean z2) {
        this.f23308a = interfaceC0191a;
        this.f23309b = z2;
    }

    private String a() {
        return String.valueOf(this.f23310c.nextInt(1000000));
    }

    private String b() {
        return Base64.encodeToString(Build.MODEL.getBytes(), 10);
    }

    private String b(String str) {
        return str + Constants.ACCEPT_TIME_SEPARATOR_SERVER + DiagnosisController.getLogName() + Constants.ACCEPT_TIME_SEPARATOR_SERVER + b() + Constants.ACCEPT_TIME_SEPARATOR_SERVER + e.a(Locale.getDefault()) + Constants.ACCEPT_TIME_SEPARATOR_SERVER + (System.currentTimeMillis() / 1000);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public String doInBackground(Void... voidArr) throws InterruptedException {
        PutObjectResult putObjectResultA;
        String diagnosisDomain = DiagnosisController.get().getDiagnosisDomain();
        if (TextUtils.isEmpty(diagnosisDomain) && !this.f23309b) {
            return null;
        }
        File fileA = c.a();
        File fileA2 = com.xiaomi.accountsdk.diagnosis.b.a();
        ArrayList arrayList = new ArrayList();
        for (File file : fileA2.listFiles()) {
            if (file.isFile() && file.getName().endsWith(".log")) {
                arrayList.add(file);
            }
        }
        arrayList.add(fileA);
        String strA = a();
        File file2 = new File(com.xiaomi.accountsdk.diagnosis.b.c(), b(strA) + ".zip");
        File parentFile = file2.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        try {
            f.a((File[]) arrayList.toArray(new File[0]), file2);
            putObjectResultA = com.xiaomi.accountsdk.diagnosis.d.a.a(file2, diagnosisDomain);
        } catch (IOException e2) {
            e2.printStackTrace();
            putObjectResultA = null;
        }
        try {
            com.xiaomi.accountsdk.diagnosis.e.b.a(file2.getParentFile());
            com.xiaomi.accountsdk.diagnosis.e.b.a(fileA.getParentFile());
        } catch (IOException e3) {
            e3.printStackTrace();
        }
        if (putObjectResultA == null) {
            return null;
        }
        return strA;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onPostExecute(String str) {
        this.f23308a.onFinished(!TextUtils.isEmpty(str), str);
    }
}
