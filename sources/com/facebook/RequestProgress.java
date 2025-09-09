package com.facebook;

import android.os.Handler;
import com.facebook.GraphRequest;

/* loaded from: classes3.dex */
class RequestProgress {
    private final Handler callbackHandler;
    private long lastReportedProgress;
    private long maxProgress;
    private long progress;
    private final GraphRequest request;
    private final long threshold = FacebookSdk.getOnProgressThreshold();

    RequestProgress(Handler handler, GraphRequest graphRequest) {
        this.request = graphRequest;
        this.callbackHandler = handler;
    }

    void addProgress(long j2) {
        long j3 = this.progress + j2;
        this.progress = j3;
        if (j3 >= this.lastReportedProgress + this.threshold || j3 >= this.maxProgress) {
            reportProgress();
        }
    }

    void addToMax(long j2) {
        this.maxProgress += j2;
    }

    long getMaxProgress() {
        return this.maxProgress;
    }

    long getProgress() {
        return this.progress;
    }

    void reportProgress() {
        if (this.progress > this.lastReportedProgress) {
            GraphRequest.Callback callback = this.request.getCallback();
            final long j2 = this.maxProgress;
            if (j2 <= 0 || !(callback instanceof GraphRequest.OnProgressCallback)) {
                return;
            }
            final long j3 = this.progress;
            final GraphRequest.OnProgressCallback onProgressCallback = (GraphRequest.OnProgressCallback) callback;
            Handler handler = this.callbackHandler;
            if (handler == null) {
                onProgressCallback.onProgress(j3, j2);
            } else {
                handler.post(new Runnable() { // from class: com.facebook.RequestProgress.1
                    @Override // java.lang.Runnable
                    public void run() {
                        onProgressCallback.onProgress(j3, j2);
                    }
                });
            }
            this.lastReportedProgress = this.progress;
        }
    }
}
