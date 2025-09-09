package com.google.common.base;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.J2ktIncompatible;
import com.google.common.base.internal.Finalizer;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.CheckForNull;
import org.apache.commons.io.IOUtils;

@J2ktIncompatible
@GwtIncompatible
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public class FinalizableReferenceQueue implements Closeable {
    private static final String FINALIZER_CLASS_NAME = "com.google.common.base.internal.Finalizer";
    private static final Logger logger = Logger.getLogger(FinalizableReferenceQueue.class.getName());
    private static final Method startFinalizer = g(loadFinalizer(new SystemLoader(), new DecoupledLoader(), new DirectLoader()));

    /* renamed from: a, reason: collision with root package name */
    final ReferenceQueue f13649a;

    /* renamed from: b, reason: collision with root package name */
    final PhantomReference f13650b;

    /* renamed from: c, reason: collision with root package name */
    final boolean f13651c;

    static class DecoupledLoader implements FinalizerLoader {
        private static final String LOADING_ERROR = "Could not load Finalizer in its own class loader. Loading Finalizer in the current class loader instead. As a result, you will not be able to garbage collect this class loader. To support reclaiming this class loader, either resolve the underlying issue, or move Guava to your system class path.";

        DecoupledLoader() {
        }

        URL a() throws IOException {
            String str = FinalizableReferenceQueue.FINALIZER_CLASS_NAME.replace('.', IOUtils.DIR_SEPARATOR_UNIX) + ".class";
            URL resource = getClass().getClassLoader().getResource(str);
            if (resource == null) {
                throw new FileNotFoundException(str);
            }
            String string = resource.toString();
            if (string.endsWith(str)) {
                return new URL(resource, string.substring(0, string.length() - str.length()));
            }
            throw new IOException("Unsupported path style: " + string);
        }

        URLClassLoader b(URL url) {
            return new URLClassLoader(new URL[]{url}, null);
        }

        @Override // com.google.common.base.FinalizableReferenceQueue.FinalizerLoader
        @CheckForNull
        public Class<?> loadFinalizer() {
            try {
                return b(a()).loadClass(FinalizableReferenceQueue.FINALIZER_CLASS_NAME);
            } catch (Exception e2) {
                FinalizableReferenceQueue.logger.log(Level.WARNING, LOADING_ERROR, (Throwable) e2);
                return null;
            }
        }
    }

    static class DirectLoader implements FinalizerLoader {
        DirectLoader() {
        }

        @Override // com.google.common.base.FinalizableReferenceQueue.FinalizerLoader
        public Class<?> loadFinalizer() {
            try {
                int i2 = Finalizer.f13687a;
                return Finalizer.class;
            } catch (ClassNotFoundException e2) {
                throw new AssertionError(e2);
            }
        }
    }

    interface FinalizerLoader {
        @CheckForNull
        Class<?> loadFinalizer();
    }

    static class SystemLoader implements FinalizerLoader {

        /* renamed from: a, reason: collision with root package name */
        static boolean f13652a;

        SystemLoader() {
        }

        @Override // com.google.common.base.FinalizableReferenceQueue.FinalizerLoader
        @CheckForNull
        public Class<?> loadFinalizer() {
            if (f13652a) {
                return null;
            }
            try {
                ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
                if (systemClassLoader != null) {
                    try {
                        return systemClassLoader.loadClass(FinalizableReferenceQueue.FINALIZER_CLASS_NAME);
                    } catch (ClassNotFoundException unused) {
                    }
                }
                return null;
            } catch (SecurityException unused2) {
                FinalizableReferenceQueue.logger.info("Not allowed to access system class loader.");
                return null;
            }
        }
    }

    public FinalizableReferenceQueue() {
        boolean z2 = true;
        ReferenceQueue referenceQueue = new ReferenceQueue();
        this.f13649a = referenceQueue;
        PhantomReference phantomReference = new PhantomReference(this, referenceQueue);
        this.f13650b = phantomReference;
        try {
            startFinalizer.invoke(null, FinalizableReference.class, referenceQueue, phantomReference);
        } catch (IllegalAccessException e2) {
            throw new AssertionError(e2);
        } catch (Throwable th) {
            logger.log(Level.INFO, "Failed to start reference finalizer thread. Reference cleanup will only occur when new references are created.", th);
            z2 = false;
        }
        this.f13651c = z2;
    }

    static Method g(Class cls) {
        try {
            return cls.getMethod("startFinalizer", Class.class, ReferenceQueue.class, PhantomReference.class);
        } catch (NoSuchMethodException e2) {
            throw new AssertionError(e2);
        }
    }

    private static Class<?> loadFinalizer(FinalizerLoader... finalizerLoaderArr) {
        for (FinalizerLoader finalizerLoader : finalizerLoaderArr) {
            Class<?> clsLoadFinalizer = finalizerLoader.loadFinalizer();
            if (clsLoadFinalizer != null) {
                return clsLoadFinalizer;
            }
        }
        throw new AssertionError();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.f13650b.enqueue();
        f();
    }

    /* JADX WARN: Multi-variable type inference failed */
    void f() {
        if (this.f13651c) {
            return;
        }
        while (true) {
            Reference referencePoll = this.f13649a.poll();
            if (referencePoll == 0) {
                return;
            }
            referencePoll.clear();
            try {
                ((FinalizableReference) referencePoll).finalizeReferent();
            } catch (Throwable th) {
                logger.log(Level.SEVERE, "Error cleaning up after reference.", th);
            }
        }
    }
}
