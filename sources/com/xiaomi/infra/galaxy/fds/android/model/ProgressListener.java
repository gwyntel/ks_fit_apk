package com.xiaomi.infra.galaxy.fds.android.model;

/* loaded from: classes4.dex */
public abstract class ProgressListener {
    public abstract void onProgress(long j2, long j3);

    public long progressInterval() {
        return 500L;
    }
}
