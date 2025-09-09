package com.google.android.gms.common.api.internal;

import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Preconditions;

/* loaded from: classes3.dex */
final class zacz extends com.google.android.gms.internal.base.zau {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ zada f12763a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zacz(zada zadaVar, Looper looper) {
        super(looper);
        this.f12763a = zadaVar;
    }

    @Override // android.os.Handler
    public final void handleMessage(Message message) {
        int i2 = message.what;
        if (i2 != 0) {
            if (i2 == 1) {
                RuntimeException runtimeException = (RuntimeException) message.obj;
                Log.e("TransformedResultImpl", "Runtime exception on the transformation worker thread: ".concat(String.valueOf(runtimeException.getMessage())));
                throw runtimeException;
            }
            Log.e("TransformedResultImpl", "TransformationResultHandler received unknown message type: " + i2);
            return;
        }
        PendingResult pendingResult = (PendingResult) message.obj;
        synchronized (this.f12763a.zae) {
            try {
                zada zadaVar = (zada) Preconditions.checkNotNull(this.f12763a.zab);
                if (pendingResult == null) {
                    zadaVar.zaj(new Status(13, "Transform returned null"));
                } else if (pendingResult instanceof zacp) {
                    zadaVar.zaj(((zacp) pendingResult).a());
                } else {
                    zadaVar.zai(pendingResult);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
