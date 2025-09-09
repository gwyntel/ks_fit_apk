package org.android.agoo.control;

import com.taobao.accs.base.TaoBaseService;

/* loaded from: classes5.dex */
class b implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ byte[] f26522a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ String f26523b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ TaoBaseService.ExtraInfo f26524c;

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ AgooFactory f26525d;

    b(AgooFactory agooFactory, byte[] bArr, String str, TaoBaseService.ExtraInfo extraInfo) {
        this.f26525d = agooFactory;
        this.f26522a = bArr;
        this.f26523b = str;
        this.f26524c = extraInfo;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f26525d.msgReceiverPreHandler(this.f26522a, this.f26523b, this.f26524c, true);
    }
}
