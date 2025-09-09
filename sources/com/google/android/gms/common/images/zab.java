package com.google.android.gms.common.images;

import android.net.Uri;
import android.os.SystemClock;
import com.google.android.gms.common.images.ImageManager;
import com.google.android.gms.common.images.ImageManager.ImageReceiver;
import com.google.android.gms.common.internal.Asserts;

/* loaded from: classes3.dex */
final class zab implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ ImageManager f12804a;
    private final zag zab;

    public zab(ImageManager imageManager, zag zagVar) {
        this.f12804a = imageManager;
        this.zab = zagVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        Asserts.checkMainThread("LoadImageRunnable must be executed on the main thread");
        ImageManager.ImageReceiver imageReceiver = (ImageManager.ImageReceiver) this.f12804a.zah.get(this.zab);
        if (imageReceiver != null) {
            ImageManager imageManager = this.f12804a;
            imageManager.zah.remove(this.zab);
            imageReceiver.zac(this.zab);
        }
        zag zagVar = this.zab;
        zad zadVar = zagVar.f12806a;
        Uri uri = zadVar.zaa;
        if (uri == null) {
            ImageManager imageManager2 = this.f12804a;
            zagVar.b(imageManager2.zad, imageManager2.zag, true);
            return;
        }
        Long l2 = (Long) this.f12804a.zaj.get(uri);
        if (l2 != null) {
            if (SystemClock.elapsedRealtime() - l2.longValue() < 3600000) {
                zag zagVar2 = this.zab;
                ImageManager imageManager3 = this.f12804a;
                zagVar2.b(imageManager3.zad, imageManager3.zag, true);
                return;
            } else {
                ImageManager imageManager4 = this.f12804a;
                imageManager4.zaj.remove(zadVar.zaa);
            }
        }
        this.zab.a(null, false, true, false);
        ImageManager imageManager5 = this.f12804a;
        ImageManager.ImageReceiver imageReceiver2 = (ImageManager.ImageReceiver) imageManager5.zai.get(zadVar.zaa);
        if (imageReceiver2 == null) {
            ImageManager.ImageReceiver imageReceiver3 = this.f12804a.new ImageReceiver(zadVar.zaa);
            ImageManager imageManager6 = this.f12804a;
            imageManager6.zai.put(zadVar.zaa, imageReceiver3);
            imageReceiver2 = imageReceiver3;
        }
        imageReceiver2.zab(this.zab);
        zag zagVar3 = this.zab;
        if (!(zagVar3 instanceof zaf)) {
            this.f12804a.zah.put(zagVar3, imageReceiver2);
        }
        synchronized (ImageManager.zaa) {
            try {
                if (!ImageManager.zab.contains(zadVar.zaa)) {
                    ImageManager.zab.add(zadVar.zaa);
                    imageReceiver2.zad();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
