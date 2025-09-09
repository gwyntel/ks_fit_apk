package a.a.a.a.a;

import androidx.annotation.RequiresApi;

/* loaded from: classes.dex */
public class d implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ g f1162a;

    public d(g gVar) {
        this.f1162a = gVar;
    }

    @Override // java.lang.Runnable
    @RequiresApi(api = 21)
    public void run() {
        a.a.a.a.b.m.a.c("AdvertiseManager", "stop alternateAdvertiseTask");
        this.f1162a.f1173h.b();
        this.f1162a.a();
    }
}
