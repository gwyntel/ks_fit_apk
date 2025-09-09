package com.google.android.gms.common.images;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Asserts;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/* loaded from: classes3.dex */
final class zaa implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ ImageManager f12803a;
    private final Uri zab;

    @Nullable
    private final ParcelFileDescriptor zac;

    public zaa(ImageManager imageManager, @Nullable Uri uri, ParcelFileDescriptor parcelFileDescriptor) {
        this.f12803a = imageManager;
        this.zab = uri;
        this.zac = parcelFileDescriptor;
    }

    @Override // java.lang.Runnable
    public final void run() throws InterruptedException, IOException {
        Asserts.checkNotMainThread("LoadBitmapFromDiskRunnable can't be executed in the main thread");
        ParcelFileDescriptor parcelFileDescriptor = this.zac;
        Bitmap bitmapDecodeFileDescriptor = null;
        boolean z2 = false;
        if (parcelFileDescriptor != null) {
            try {
                bitmapDecodeFileDescriptor = BitmapFactory.decodeFileDescriptor(parcelFileDescriptor.getFileDescriptor());
            } catch (OutOfMemoryError e2) {
                Log.e("ImageManager", "OOM while loading bitmap for uri: ".concat(String.valueOf(this.zab)), e2);
                z2 = true;
            }
            try {
                this.zac.close();
            } catch (IOException e3) {
                Log.e("ImageManager", "closed failed", e3);
            }
        }
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ImageManager imageManager = this.f12803a;
        imageManager.zae.post(new zac(imageManager, this.zab, bitmapDecodeFileDescriptor, z2, countDownLatch));
        try {
            countDownLatch.await();
        } catch (InterruptedException unused) {
            Log.w("ImageManager", "Latch interrupted while posting ".concat(String.valueOf(this.zab)));
        }
    }
}
