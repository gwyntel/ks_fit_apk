package com.huawei.hmf.tasks;

import com.huawei.hmf.tasks.a.c;
import java.util.Iterator;

/* loaded from: classes3.dex */
public class CancellationTokenSource {
    private c impl = new c();

    public void cancel() {
        c cVar = this.impl;
        if (cVar.f15660c) {
            return;
        }
        synchronized (cVar.f15659b) {
            try {
                cVar.f15660c = true;
                Iterator<Runnable> it = cVar.f15658a.iterator();
                while (it.hasNext()) {
                    it.next().run();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public CancellationToken getToken() {
        return this.impl;
    }
}
