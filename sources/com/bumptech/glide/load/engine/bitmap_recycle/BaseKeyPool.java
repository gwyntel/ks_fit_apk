package com.bumptech.glide.load.engine.bitmap_recycle;

import com.bumptech.glide.load.engine.bitmap_recycle.Poolable;
import com.bumptech.glide.util.Util;
import java.util.Queue;

/* loaded from: classes3.dex */
abstract class BaseKeyPool<T extends Poolable> {
    private static final int MAX_SIZE = 20;
    private final Queue<T> keyPool = Util.createQueue(20);

    BaseKeyPool() {
    }

    abstract Poolable a();

    Poolable b() {
        T tPoll = this.keyPool.poll();
        return tPoll == null ? a() : tPoll;
    }

    public void offer(T t2) {
        if (this.keyPool.size() < 20) {
            this.keyPool.offer(t2);
        }
    }
}
