package com.vivo.push.b;

import android.text.TextUtils;
import com.google.firebase.messaging.Constants;
import com.heytap.mcssdk.constant.IntentConstant;
import java.util.HashMap;

/* loaded from: classes4.dex */
public final class x extends com.vivo.push.o {

    /* renamed from: a, reason: collision with root package name */
    private HashMap<String, String> f23071a;

    /* renamed from: b, reason: collision with root package name */
    private long f23072b;

    public x() {
        super(2012);
    }

    public final void a(HashMap<String, String> map) {
        this.f23071a = map;
    }

    @Override // com.vivo.push.o
    public final void c(com.vivo.push.a aVar) {
        aVar.a("ReporterCommand.EXTRA_PARAMS", this.f23071a);
        aVar.a("ReporterCommand.EXTRA_REPORTER_TYPE", this.f23072b);
    }

    @Override // com.vivo.push.o
    public final void d(com.vivo.push.a aVar) {
        this.f23071a = (HashMap) aVar.d("ReporterCommand.EXTRA_PARAMS");
        this.f23072b = aVar.b("ReporterCommand.EXTRA_REPORTER_TYPE", this.f23072b);
    }

    @Override // com.vivo.push.o
    public final String toString() {
        return "ReporterCommand（" + this.f23072b + ")";
    }

    public x(long j2) {
        this();
        this.f23072b = j2;
    }

    public final void d() {
        if (this.f23071a == null) {
            com.vivo.push.util.p.d("ReporterCommand", "reportParams is empty");
            return;
        }
        StringBuilder sb = new StringBuilder("report message reportType:");
        sb.append(this.f23072b);
        sb.append(",msgId:");
        String str = this.f23071a.get(IntentConstant.MESSAGE_ID);
        if (TextUtils.isEmpty(str)) {
            str = this.f23071a.get(Constants.MessagePayloadKeys.MSGID_SERVER);
        }
        sb.append(str);
        com.vivo.push.util.p.d("ReporterCommand", sb.toString());
    }
}
