package a.a.a.a.b.i;

import b.InterfaceC0326a;

/* loaded from: classes.dex */
public class y implements InterfaceC0326a.b {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ J f1524a;

    public y(J j2) {
        this.f1524a = j2;
    }

    @Override // b.InterfaceC0326a.b
    public void generate(String str) {
        a.a.a.a.b.m.a.c(this.f1524a.f1373a, "receive confirmationCloud: " + str);
        this.f1524a.D = str;
        if (this.f1524a.f1395w) {
            J j2 = this.f1524a;
            j2.onReceiveConfirmationFromCloud(j2.f1381i, this.f1524a.D);
        }
    }
}
