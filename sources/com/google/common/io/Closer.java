package com.google.common.io;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.J2ktIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.logging.Level;
import javax.annotation.CheckForNull;

@ElementTypesAreNonnullByDefault
@J2ktIncompatible
@GwtIncompatible
/* loaded from: classes3.dex */
public final class Closer implements Closeable {
    private static final Suppressor SUPPRESSING_SUPPRESSOR = new Suppressor() { // from class: com.google.common.io.a
        @Override // com.google.common.io.Closer.Suppressor
        public final void suppress(Closeable closeable, Throwable th, Throwable th2) {
            Closer.lambda$static$0(closeable, th, th2);
        }
    };

    /* renamed from: a, reason: collision with root package name */
    final Suppressor f14677a;
    private final Deque<Closeable> stack = new ArrayDeque(4);

    @CheckForNull
    private Throwable thrown;

    @VisibleForTesting
    interface Suppressor {
        void suppress(Closeable closeable, Throwable th, Throwable th2);
    }

    Closer(Suppressor suppressor) {
        this.f14677a = (Suppressor) Preconditions.checkNotNull(suppressor);
    }

    public static Closer create() {
        return new Closer(SUPPRESSING_SUPPRESSOR);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$static$0(Closeable closeable, Throwable th, Throwable th2) {
        if (th == th2) {
            return;
        }
        try {
            th.addSuppressed(th2);
        } catch (Throwable unused) {
            Closeables.f14676a.log(Level.WARNING, "Suppressing exception thrown when closing " + closeable, th2);
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws Throwable {
        Throwable th = this.thrown;
        while (!this.stack.isEmpty()) {
            Closeable closeableRemoveFirst = this.stack.removeFirst();
            try {
                closeableRemoveFirst.close();
            } catch (Throwable th2) {
                if (th == null) {
                    th = th2;
                } else {
                    this.f14677a.suppress(closeableRemoveFirst, th, th2);
                }
            }
        }
        if (this.thrown != null || th == null) {
            return;
        }
        Throwables.throwIfInstanceOf(th, IOException.class);
        Throwables.throwIfUnchecked(th);
        throw new AssertionError(th);
    }

    @CanIgnoreReturnValue
    @ParametricNullness
    public <C extends Closeable> C register(@ParametricNullness C c2) {
        if (c2 != null) {
            this.stack.addFirst(c2);
        }
        return c2;
    }

    public RuntimeException rethrow(Throwable th) throws Throwable {
        Preconditions.checkNotNull(th);
        this.thrown = th;
        Throwables.throwIfInstanceOf(th, IOException.class);
        Throwables.throwIfUnchecked(th);
        throw new RuntimeException(th);
    }

    public <X extends Exception> RuntimeException rethrow(Throwable th, Class<X> cls) throws Exception {
        Preconditions.checkNotNull(th);
        this.thrown = th;
        Throwables.throwIfInstanceOf(th, IOException.class);
        Throwables.throwIfInstanceOf(th, cls);
        Throwables.throwIfUnchecked(th);
        throw new RuntimeException(th);
    }

    public <X1 extends Exception, X2 extends Exception> RuntimeException rethrow(Throwable th, Class<X1> cls, Class<X2> cls2) throws Exception {
        Preconditions.checkNotNull(th);
        this.thrown = th;
        Throwables.throwIfInstanceOf(th, IOException.class);
        Throwables.throwIfInstanceOf(th, cls);
        Throwables.throwIfInstanceOf(th, cls2);
        Throwables.throwIfUnchecked(th);
        throw new RuntimeException(th);
    }
}
