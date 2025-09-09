package com.bumptech.glide.load.engine;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/* loaded from: classes3.dex */
class ResourceRecycler {
    private final Handler handler = new Handler(Looper.getMainLooper(), new ResourceRecyclerCallback());
    private boolean isRecycling;

    private static final class ResourceRecyclerCallback implements Handler.Callback {
        ResourceRecyclerCallback() {
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            if (message.what != 1) {
                return false;
            }
            ((Resource) message.obj).recycle();
            return true;
        }
    }

    ResourceRecycler() {
    }

    synchronized void a(Resource resource, boolean z2) {
        try {
            if (this.isRecycling || z2) {
                this.handler.obtainMessage(1, resource).sendToTarget();
            } else {
                this.isRecycling = true;
                resource.recycle();
                this.isRecycling = false;
            }
        } catch (Throwable th) {
            throw th;
        }
    }
}
