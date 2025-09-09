package com.umeng.commonsdk.stateless;

import android.content.Context;
import android.text.TextUtils;
import com.umeng.analytics.pro.bh;
import com.umeng.analytics.pro.bl;
import com.umeng.commonsdk.framework.UMEnvelopeBuild;
import com.umeng.commonsdk.statistics.SdkVersion;
import com.umeng.commonsdk.statistics.common.DataHelper;

/* loaded from: classes4.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    private String f22313a = "10.0.0.172";

    /* renamed from: b, reason: collision with root package name */
    private int f22314b = 80;

    /* renamed from: c, reason: collision with root package name */
    private Context f22315c;

    public c(Context context) {
        this.f22315c = context;
    }

    public void a() {
        String strImprintProperty = UMEnvelopeBuild.imprintProperty(this.f22315c, "sl_domain_p", "");
        if (TextUtils.isEmpty(strImprintProperty)) {
            return;
        }
        a.f22297h = DataHelper.assembleStatelessURL(strImprintProperty);
    }

    public void b() {
        String strImprintProperty = UMEnvelopeBuild.imprintProperty(this.f22315c, "sl_domain_p", "");
        String strImprintProperty2 = UMEnvelopeBuild.imprintProperty(this.f22315c, "oversea_sl_domain_p", "");
        if (!TextUtils.isEmpty(strImprintProperty)) {
            a.f22296g = DataHelper.assembleStatelessURL(strImprintProperty);
        }
        if (!TextUtils.isEmpty(strImprintProperty2)) {
            a.f22299j = DataHelper.assembleStatelessURL(strImprintProperty2);
        }
        a.f22297h = a.f22299j;
        if (TextUtils.isEmpty(com.umeng.commonsdk.statistics.b.f22323b)) {
            return;
        }
        if (com.umeng.commonsdk.statistics.b.f22323b.startsWith("460") || com.umeng.commonsdk.statistics.b.f22323b.startsWith("461")) {
            a.f22297h = a.f22296g;
        }
    }

    public boolean a(byte[] bArr, String str, String str2, String str3) throws Throwable {
        String str4 = str2 + "/" + str;
        if (SdkVersion.SDK_TYPE == 1) {
            return a(bArr, str4, str3);
        }
        if (bh.a().b()) {
            String strReplace = str4.replace("ulogs", "cnlogs");
            String strC = bl.b().c();
            if (!TextUtils.isEmpty(strC)) {
                strReplace = bh.a(strC, str);
            }
            boolean zA = a(bArr, strReplace, str3);
            if (!zA) {
                String strA = bh.a().a(str);
                if (!TextUtils.isEmpty(strA) && !strReplace.equalsIgnoreCase(strA)) {
                    return a(bArr, strA, str3);
                }
            }
            return zA;
        }
        return a(bArr, str4, str3);
    }

    /* JADX WARN: Removed duplicated region for block: B:62:0x0127 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:66:0x012c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:81:? A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean a(byte[] r10, java.lang.String r11, java.lang.String r12) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 319
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.commonsdk.stateless.c.a(byte[], java.lang.String, java.lang.String):boolean");
    }
}
