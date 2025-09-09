package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.text.TextUtils;

/* loaded from: classes4.dex */
class j implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Context f23412a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ e f151a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ String f152a;

    j(String str, Context context, e eVar) {
        this.f152a = str;
        this.f23412a = context;
        this.f151a = eVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        String strSubstring;
        if (TextUtils.isEmpty(this.f152a)) {
            return;
        }
        String[] strArrSplit = this.f152a.split(Constants.WAVE_SEPARATOR);
        int length = strArrSplit.length;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                strSubstring = "";
                break;
            }
            String str = strArrSplit[i2];
            if (!TextUtils.isEmpty(str) && str.startsWith("token:")) {
                strSubstring = str.substring(str.indexOf(":") + 1);
                break;
            }
            i2++;
        }
        if (TextUtils.isEmpty(strSubstring)) {
            com.xiaomi.channel.commonutils.logger.b.m91a("ASSEMBLE_PUSH : receive incorrect token");
            return;
        }
        com.xiaomi.channel.commonutils.logger.b.m91a("ASSEMBLE_PUSH : receive correct token");
        i.d(this.f23412a, this.f151a, strSubstring);
        i.m160a(this.f23412a);
    }
}
