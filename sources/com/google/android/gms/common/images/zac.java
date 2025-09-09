package com.google.android.gms.common.images;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.SystemClock;
import androidx.annotation.Nullable;
import com.google.android.gms.common.images.ImageManager;
import com.google.android.gms.common.internal.Asserts;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

/* loaded from: classes3.dex */
final class zac implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ ImageManager f12805a;
    private final Uri zab;

    @Nullable
    private final Bitmap zac;
    private final CountDownLatch zad;

    public zac(ImageManager imageManager, @Nullable Uri uri, Bitmap bitmap, boolean z2, CountDownLatch countDownLatch) {
        this.f12805a = imageManager;
        this.zab = uri;
        this.zac = bitmap;
        this.zad = countDownLatch;
    }

    @Override // java.lang.Runnable
    public final void run() {
        Asserts.checkMainThread("OnBitmapLoadedRunnable must be executed in the main thread");
        ImageManager.ImageReceiver imageReceiver = (ImageManager.ImageReceiver) this.f12805a.zai.remove(this.zab);
        if (imageReceiver != null) {
            ArrayList arrayList = imageReceiver.zac;
            int size = arrayList.size();
            for (int i2 = 0; i2 < size; i2++) {
                zag zagVar = (zag) arrayList.get(i2);
                Bitmap bitmap = this.zac;
                if (bitmap != null) {
                    zagVar.c(this.f12805a.zad, bitmap, false);
                } else {
                    this.f12805a.zaj.put(this.zab, Long.valueOf(SystemClock.elapsedRealtime()));
                    ImageManager imageManager = this.f12805a;
                    zagVar.b(imageManager.zad, imageManager.zag, false);
                }
                if (!(zagVar instanceof zaf)) {
                    this.f12805a.zah.remove(zagVar);
                }
            }
        }
        this.zad.countDown();
        synchronized (ImageManager.zaa) {
            ImageManager.zab.remove(this.zab);
        }
    }
}
