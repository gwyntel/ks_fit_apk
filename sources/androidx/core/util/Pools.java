package androidx.core.util;

import androidx.annotation.IntRange;
import androidx.exifinterface.media.ExifInterface;
import com.kingsmith.miot.KsProperty;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\u0018\u00002\u00020\u0001:\u0003\u0003\u0004\u0005B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0006"}, d2 = {"Landroidx/core/util/Pools;", "", "()V", "Pool", "SimplePool", "SynchronizedPool", "core_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
public final class Pools {

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\bf\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0002J\u000f\u0010\u0003\u001a\u0004\u0018\u00018\u0000H&¢\u0006\u0002\u0010\u0004J\u0015\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00028\u0000H&¢\u0006\u0002\u0010\bø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\tÀ\u0006\u0001"}, d2 = {"Landroidx/core/util/Pools$Pool;", ExifInterface.GPS_DIRECTION_TRUE, "", "acquire", "()Ljava/lang/Object;", "release", "", "instance", "(Ljava/lang/Object;)Z", "core_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface Pool<T> {
        @Nullable
        T acquire();

        boolean release(@NotNull T instance);
    }

    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0016\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u000f\u0012\b\b\u0001\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u000f\u0010\u000b\u001a\u0004\u0018\u00018\u0000H\u0016¢\u0006\u0002\u0010\fJ\u0015\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010\u0010J\u0015\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0010R\u0018\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\bX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\tR\u000e\u0010\n\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Landroidx/core/util/Pools$SimplePool;", ExifInterface.GPS_DIRECTION_TRUE, "", "Landroidx/core/util/Pools$Pool;", "maxPoolSize", "", "(I)V", "pool", "", "[Ljava/lang/Object;", "poolSize", "acquire", "()Ljava/lang/Object;", "isInPool", "", "instance", "(Ljava/lang/Object;)Z", "release", "core_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    @SourceDebugExtension({"SMAP\nPools.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Pools.kt\nandroidx/core/util/Pools$SimplePool\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,134:1\n1#2:135\n*E\n"})
    public static class SimplePool<T> implements Pool<T> {

        @NotNull
        private final Object[] pool;
        private int poolSize;

        public SimplePool(@IntRange(from = 1) int i2) {
            if (i2 <= 0) {
                throw new IllegalArgumentException("The max pool size must be > 0".toString());
            }
            this.pool = new Object[i2];
        }

        private final boolean isInPool(T instance) {
            int i2 = this.poolSize;
            for (int i3 = 0; i3 < i2; i3++) {
                if (this.pool[i3] == instance) {
                    return true;
                }
            }
            return false;
        }

        @Override // androidx.core.util.Pools.Pool
        @Nullable
        public T acquire() {
            int i2 = this.poolSize;
            if (i2 <= 0) {
                return null;
            }
            int i3 = i2 - 1;
            T t2 = (T) this.pool[i3];
            Intrinsics.checkNotNull(t2, "null cannot be cast to non-null type T of androidx.core.util.Pools.SimplePool");
            this.pool[i3] = null;
            this.poolSize--;
            return t2;
        }

        @Override // androidx.core.util.Pools.Pool
        public boolean release(@NotNull T instance) {
            Intrinsics.checkNotNullParameter(instance, "instance");
            if (!(!isInPool(instance))) {
                throw new IllegalStateException("Already in the pool!".toString());
            }
            int i2 = this.poolSize;
            Object[] objArr = this.pool;
            if (i2 >= objArr.length) {
                return false;
            }
            objArr[i2] = instance;
            this.poolSize = i2 + 1;
            return true;
        }
    }

    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0016\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u000f\u0010\b\u001a\u0004\u0018\u00018\u0000H\u0016¢\u0006\u0002\u0010\tJ\u0015\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\rR\u000e\u0010\u0007\u001a\u00020\u0002X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Landroidx/core/util/Pools$SynchronizedPool;", ExifInterface.GPS_DIRECTION_TRUE, "", "Landroidx/core/util/Pools$SimplePool;", "maxPoolSize", "", "(I)V", KsProperty.Lock, "acquire", "()Ljava/lang/Object;", "release", "", "instance", "(Ljava/lang/Object;)Z", "core_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    @SourceDebugExtension({"SMAP\nPools.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Pools.kt\nandroidx/core/util/Pools$SynchronizedPool\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,134:1\n1#2:135\n*E\n"})
    public static class SynchronizedPool<T> extends SimplePool<T> {

        @NotNull
        private final Object lock;

        public SynchronizedPool(int i2) {
            super(i2);
            this.lock = new Object();
        }

        @Override // androidx.core.util.Pools.SimplePool, androidx.core.util.Pools.Pool
        @Nullable
        public T acquire() {
            T t2;
            synchronized (this.lock) {
                t2 = (T) super.acquire();
            }
            return t2;
        }

        @Override // androidx.core.util.Pools.SimplePool, androidx.core.util.Pools.Pool
        public boolean release(@NotNull T instance) {
            boolean zRelease;
            Intrinsics.checkNotNullParameter(instance, "instance");
            synchronized (this.lock) {
                zRelease = super.release(instance);
            }
            return zRelease;
        }
    }

    private Pools() {
    }
}
