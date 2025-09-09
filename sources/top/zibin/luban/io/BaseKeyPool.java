package top.zibin.luban.io;

import java.util.ArrayDeque;
import java.util.Queue;
import top.zibin.luban.io.PoolAble;

/* loaded from: classes5.dex */
abstract class BaseKeyPool<T extends PoolAble> {
    private static final int MAX_SIZE = 20;
    private final Queue<T> keyPool = createQueue(20);

    BaseKeyPool() {
    }

    public static <T> Queue<T> createQueue(int i2) {
        return new ArrayDeque(i2);
    }

    abstract PoolAble a();

    PoolAble b() {
        T tPoll = this.keyPool.poll();
        return tPoll == null ? a() : tPoll;
    }

    public void offer(T t2) {
        if (this.keyPool.size() < 20) {
            this.keyPool.offer(t2);
        }
    }
}
