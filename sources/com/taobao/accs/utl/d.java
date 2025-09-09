package com.taobao.accs.utl;

import com.taobao.accs.base.AccsDataListener;
import com.taobao.accs.base.TaoBaseService;
import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.ALog;
import com.vivo.push.PushClientConstants;

/* loaded from: classes4.dex */
class d implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ String f20360a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ String f20361b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ int f20362c;

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ AccsDataListener f20363d;

    /* renamed from: e, reason: collision with root package name */
    final /* synthetic */ String f20364e;

    /* renamed from: f, reason: collision with root package name */
    final /* synthetic */ byte[] f20365f;

    /* renamed from: g, reason: collision with root package name */
    final /* synthetic */ TaoBaseService.ExtraInfo f20366g;

    d(String str, String str2, int i2, AccsDataListener accsDataListener, String str3, byte[] bArr, TaoBaseService.ExtraInfo extraInfo) {
        this.f20360a = str;
        this.f20361b = str2;
        this.f20362c = i2;
        this.f20363d = accsDataListener;
        this.f20364e = str3;
        this.f20365f = bArr;
        this.f20366g = extraInfo;
    }

    @Override // java.lang.Runnable
    public void run() {
        ALog.Level level = ALog.Level.D;
        if (ALog.isPrintLog(level) || "accs-impaas".equals(this.f20360a)) {
            ALog.e(a.TAG, "onData start", Constants.KEY_DATA_ID, this.f20361b, Constants.KEY_SERVICE_ID, this.f20360a, "command", Integer.valueOf(this.f20362c), PushClientConstants.TAG_CLASS_NAME, this.f20363d.getClass().getName());
        }
        this.f20363d.onData(this.f20360a, this.f20364e, this.f20361b, this.f20365f, this.f20366g);
        if (ALog.isPrintLog(level) || "accs-impaas".equals(this.f20360a)) {
            ALog.e(a.TAG, "onData end", Constants.KEY_DATA_ID, this.f20361b);
        }
    }
}
