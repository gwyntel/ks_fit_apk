package com.luck.picture.lib.obj.pool;

import java.util.LinkedList;

/* loaded from: classes4.dex */
public final class ObjectPools {

    public interface Pool<T> {
        T acquire();

        void destroy();

        boolean release(T t2);
    }

    public static class SimpleObjectPool<T> implements Pool<T> {
        private final LinkedList<T> mPool = new LinkedList<>();

        private boolean isInPool(T t2) {
            return this.mPool.contains(t2);
        }

        @Override // com.luck.picture.lib.obj.pool.ObjectPools.Pool
        public T acquire() {
            return this.mPool.poll();
        }

        @Override // com.luck.picture.lib.obj.pool.ObjectPools.Pool
        public void destroy() {
            this.mPool.clear();
        }

        @Override // com.luck.picture.lib.obj.pool.ObjectPools.Pool
        public boolean release(T t2) {
            if (isInPool(t2)) {
                return false;
            }
            return this.mPool.add(t2);
        }
    }

    public static class SynchronizedPool<T> extends SimpleObjectPool<T> {
        private final Object mLock = new Object();

        @Override // com.luck.picture.lib.obj.pool.ObjectPools.SimpleObjectPool, com.luck.picture.lib.obj.pool.ObjectPools.Pool
        public T acquire() {
            T t2;
            synchronized (this.mLock) {
                t2 = (T) super.acquire();
            }
            return t2;
        }

        @Override // com.luck.picture.lib.obj.pool.ObjectPools.SimpleObjectPool, com.luck.picture.lib.obj.pool.ObjectPools.Pool
        public void destroy() {
            synchronized (this.mLock) {
                super.destroy();
            }
        }

        @Override // com.luck.picture.lib.obj.pool.ObjectPools.SimpleObjectPool, com.luck.picture.lib.obj.pool.ObjectPools.Pool
        public boolean release(T t2) {
            boolean zRelease;
            synchronized (this.mLock) {
                zRelease = super.release(t2);
            }
            return zRelease;
        }
    }
}
