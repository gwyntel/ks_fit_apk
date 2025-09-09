package com.xiaomi.accountsdk.diagnosis.ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.xiaomi.accountsdk.diagnosis.DiagnosisController;
import com.xiaomi.accountsdk.diagnosis.R;
import com.xiaomi.accountsdk.diagnosis.c;
import com.xiaomi.accountsdk.diagnosis.c.a;
import com.xiaomi.accountsdk.diagnosis.c.b;
import com.xiaomi.accountsdk.diagnosis.e.a;

/* loaded from: classes4.dex */
public class PassportDiagnosisActivity extends AppCompatActivity {

    /* renamed from: a, reason: collision with root package name */
    private ScrollView f23321a;

    /* renamed from: b, reason: collision with root package name */
    private View f23322b;

    /* renamed from: c, reason: collision with root package name */
    private View f23323c;

    /* renamed from: e, reason: collision with root package name */
    private ProgressDialog f23325e;

    /* renamed from: d, reason: collision with root package name */
    private CompoundButton.OnCheckedChangeListener f23324d = new CompoundButton.OnCheckedChangeListener() { // from class: com.xiaomi.accountsdk.diagnosis.ui.PassportDiagnosisActivity.1
        @Override // android.widget.CompoundButton.OnCheckedChangeListener
        public void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
            a.a(DiagnosisController.getApplicationContext(), z2);
            PassportDiagnosisActivity.this.a(z2);
        }
    };

    /* renamed from: f, reason: collision with root package name */
    private volatile boolean f23326f = false;

    /* renamed from: g, reason: collision with root package name */
    private View.OnClickListener f23327g = new View.OnClickListener() { // from class: com.xiaomi.accountsdk.diagnosis.ui.PassportDiagnosisActivity.2
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (PassportDiagnosisActivity.this.f23326f) {
                return;
            }
            PassportDiagnosisActivity.this.f23325e = new ProgressDialog(PassportDiagnosisActivity.this);
            PassportDiagnosisActivity.this.f23325e.setMessage(PassportDiagnosisActivity.this.getString(R.string.sending));
            PassportDiagnosisActivity.this.f23325e.setCancelable(false);
            PassportDiagnosisActivity.this.f23325e.getWindow().setGravity(80);
            PassportDiagnosisActivity.this.f23325e.show();
            PassportDiagnosisActivity.this.f23326f = true;
            new com.xiaomi.accountsdk.diagnosis.c.a(new a.InterfaceC0191a() { // from class: com.xiaomi.accountsdk.diagnosis.ui.PassportDiagnosisActivity.2.1
                @Override // com.xiaomi.accountsdk.diagnosis.c.a.InterfaceC0191a
                public void onFinished(boolean z2, String str) {
                    PassportDiagnosisActivity.this.f23326f = false;
                    if (PassportDiagnosisActivity.this.f23325e != null) {
                        PassportDiagnosisActivity.this.f23325e.dismiss();
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(PassportDiagnosisActivity.this);
                    builder.setMessage((!z2 || TextUtils.isEmpty(str)) ? PassportDiagnosisActivity.this.getString(R.string.diagnosis_log_send_failed) : PassportDiagnosisActivity.this.getString(R.string.diagnosis_log_sent_format, str));
                    builder.setNeutralButton(R.string.ok, (DialogInterface.OnClickListener) null);
                    builder.setCancelable(false);
                    builder.show();
                }
            }, false).execute(new Void[0]);
        }
    };

    public static void start(final Context context) {
        DiagnosisController.get().checkStart(new c() { // from class: com.xiaomi.accountsdk.diagnosis.ui.PassportDiagnosisActivity.4
            @Override // com.xiaomi.accountsdk.diagnosis.c
            public void onError() {
                Toast.makeText(context, R.string.temporary_not_available, 0).show();
            }

            @Override // com.xiaomi.accountsdk.diagnosis.c
            public void onLaunch() {
                context.startActivity(new Intent(context, (Class<?>) PassportDiagnosisActivity.class));
            }
        });
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.passport_diagnosis);
        this.f23321a = (ScrollView) findViewById(R.id.log_scroller);
        this.f23323c = findViewById(R.id.upload_diagnosis);
        this.f23322b = findViewById(R.id.footer);
        CompoundButton compoundButton = (CompoundButton) findViewById(R.id.switch_diagnosis);
        compoundButton.setChecked(a());
        compoundButton.setOnCheckedChangeListener(this.f23324d);
        this.f23323c.setOnClickListener(this.f23327g);
        b();
        a(a());
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
    }

    private void b() {
        new b(this, new b.a() { // from class: com.xiaomi.accountsdk.diagnosis.ui.PassportDiagnosisActivity.3
            @Override // com.xiaomi.accountsdk.diagnosis.c.b.a
            public void onReadLog(String str) {
                ((TextView) PassportDiagnosisActivity.this.findViewById(R.id.tv_log)).setText(str);
                PassportDiagnosisActivity.this.f23321a.post(new Runnable() { // from class: com.xiaomi.accountsdk.diagnosis.ui.PassportDiagnosisActivity.3.1
                    @Override // java.lang.Runnable
                    public void run() {
                        PassportDiagnosisActivity.this.f23321a.fullScroll(130);
                    }
                });
            }
        }, 512).execute(new Void[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(boolean z2) {
        int i2 = z2 ? 0 : 8;
        this.f23321a.setVisibility(i2);
        this.f23322b.setVisibility(i2);
        this.f23323c.setVisibility(i2);
    }

    private static boolean a() {
        return com.xiaomi.accountsdk.diagnosis.e.a.a(DiagnosisController.getApplicationContext());
    }
}
