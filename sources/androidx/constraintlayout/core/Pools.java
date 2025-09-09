package androidx.constraintlayout.core;

/* loaded from: classes.dex */
final class Pools {
    private static final boolean DEBUG = false;

    interface Pool<T> {
        T acquire();

        boolean release(T t2);

        void releaseAll(T[] tArr, int i2);
    }

    static class SimplePool<T> implements Pool<T> {
        private final Object[] mPool;
        private int mPoolSize;

        SimplePool(int i2) {
            if (i2 <= 0) {
                throw new IllegalArgumentException("The max pool size must be > 0");
            }
            this.mPool = new Object[i2];
        }

        private boolean isInPool(T t2) {
            for (int i2 = 0; i2 < this.mPoolSize; i2++) {
                if (this.mPool[i2] == t2) {
                    return true;
                }
            }
            return false;
        }

        @Override // androidx.constraintlayout.core.Pools.Pool
        public T acquire() {
            int i2 = this.mPoolSize;
            if (i2 <= 0) {
                return null;
            }
            int i3 = i2 - 1;
            Object[] objArr = this.mPool;
            T t2 = (T) objArr[i3];
            objArr[i3] = null;
            this.mPoolSize = i2 - 1;
            return t2;
        }

        @Override // androidx.constraintlayout.core.Pools.Pool
        public boolean release(T t2) {
            int i2 = this.mPoolSize;
            Object[] objArr = this.mPool;
            if (i2 >= objArr.length) {
                return false;
            }
            objArr[i2] = t2;
            this.mPoolSize = i2 + 1;
            return true;
        }

        @Override // androidx.constraintlayout.core.Pools.Pool
        public void releaseAll(T[] tArr, int i2) {
            if (i2 > tArr.length) {
                i2 = tArr.length;
            }
            for (int i3 = 0; i3 < i2; i3++) {
                T t2 = tArr[i3];
                int i4 = this.mPoolSize;
                Object[] objArr = this.mPool;
                if (i4 < objArr.length) {
                    objArr[i4] = t2;
                    this.mPoolSize = i4 + 1;
                }
            }
        }
    }

    private Pools() {
    }
}
