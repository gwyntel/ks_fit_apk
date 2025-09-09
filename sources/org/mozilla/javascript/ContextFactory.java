package org.mozilla.javascript;

import java.security.AccessController;
import java.security.PrivilegedAction;
import org.mozilla.javascript.xml.XMLLib;

/* loaded from: classes5.dex */
public class ContextFactory {
    private static ContextFactory global = new ContextFactory();
    private static volatile boolean hasCustomGlobal;
    private ClassLoader applicationClassLoader;
    private boolean disabledListening;
    private volatile Object listeners;
    private final Object listenersLock = new Object();
    private volatile boolean sealed;

    public interface GlobalSetter {
        ContextFactory getContextFactoryGlobal();

        void setContextFactoryGlobal(ContextFactory contextFactory);
    }

    public interface Listener {
        void contextCreated(Context context);

        void contextReleased(Context context);
    }

    public static ContextFactory getGlobal() {
        return global;
    }

    public static synchronized GlobalSetter getGlobalSetter() {
        if (hasCustomGlobal) {
            throw new IllegalStateException();
        }
        hasCustomGlobal = true;
        return new GlobalSetter() { // from class: org.mozilla.javascript.ContextFactory.1GlobalSetterImpl
            @Override // org.mozilla.javascript.ContextFactory.GlobalSetter
            public ContextFactory getContextFactoryGlobal() {
                return ContextFactory.global;
            }

            @Override // org.mozilla.javascript.ContextFactory.GlobalSetter
            public void setContextFactoryGlobal(ContextFactory contextFactory) {
                if (contextFactory == null) {
                    contextFactory = new ContextFactory();
                }
                ContextFactory unused = ContextFactory.global = contextFactory;
            }
        };
    }

    public static boolean hasExplicitGlobal() {
        return hasCustomGlobal;
    }

    public static synchronized void initGlobal(ContextFactory contextFactory) {
        if (contextFactory == null) {
            throw new IllegalArgumentException();
        }
        if (hasCustomGlobal) {
            throw new IllegalStateException();
        }
        hasCustomGlobal = true;
        global = contextFactory;
    }

    private boolean isDom3Present() throws NoSuchMethodException, SecurityException {
        Class<?> clsClassOrNull = Kit.classOrNull("org.w3c.dom.Node");
        if (clsClassOrNull == null) {
            return false;
        }
        try {
            clsClassOrNull.getMethod("getUserData", String.class);
            return true;
        } catch (NoSuchMethodException unused) {
            return false;
        }
    }

    public final void addListener(Listener listener) {
        checkNotSealed();
        synchronized (this.listenersLock) {
            try {
                if (this.disabledListening) {
                    throw new IllegalStateException();
                }
                this.listeners = Kit.addListener(this.listeners, listener);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final Object call(ContextAction contextAction) {
        return Context.call(this, contextAction);
    }

    protected final void checkNotSealed() {
        if (this.sealed) {
            throw new IllegalStateException();
        }
    }

    protected GeneratedClassLoader createClassLoader(final ClassLoader classLoader) {
        return (GeneratedClassLoader) AccessController.doPrivileged(new PrivilegedAction<DefiningClassLoader>() { // from class: org.mozilla.javascript.ContextFactory.1
            @Override // java.security.PrivilegedAction
            public DefiningClassLoader run() {
                return new DefiningClassLoader(classLoader);
            }
        });
    }

    final void disableContextListening() {
        checkNotSealed();
        synchronized (this.listenersLock) {
            this.disabledListening = true;
            this.listeners = null;
        }
    }

    protected Object doTopCall(Callable callable, Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        Object objCall = callable.call(context, scriptable, scriptable2, objArr);
        return objCall instanceof ConsString ? objCall.toString() : objCall;
    }

    @Deprecated
    public final Context enter() {
        return enterContext(null);
    }

    public Context enterContext() {
        return enterContext(null);
    }

    @Deprecated
    public final void exit() throws RuntimeException {
        Context.exit();
    }

    public final ClassLoader getApplicationClassLoader() {
        return this.applicationClassLoader;
    }

    protected XMLLib.Factory getE4xImplementationFactory() {
        if (isDom3Present()) {
            return XMLLib.Factory.create("org.mozilla.javascript.xmlimpl.XMLLibImpl");
        }
        return null;
    }

    protected boolean hasFeature(Context context, int i2) {
        switch (i2) {
            case 1:
                int languageVersion = context.getLanguageVersion();
                return languageVersion == 100 || languageVersion == 110 || languageVersion == 120;
            case 2:
                return false;
            case 3:
                return true;
            case 4:
                return context.getLanguageVersion() == 120;
            case 5:
                return true;
            case 6:
                int languageVersion2 = context.getLanguageVersion();
                return languageVersion2 == 0 || languageVersion2 >= 160;
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
                return false;
            case 14:
                return true;
            case 15:
                return context.getLanguageVersion() <= 170;
            case 16:
                return context.getLanguageVersion() >= 200;
            case 17:
            case 18:
                return false;
            default:
                throw new IllegalArgumentException(String.valueOf(i2));
        }
    }

    public final void initApplicationClassLoader(ClassLoader classLoader) {
        if (classLoader == null) {
            throw new IllegalArgumentException("loader is null");
        }
        if (!Kit.testIfCanLoadRhinoClasses(classLoader)) {
            throw new IllegalArgumentException("Loader can not resolve Rhino classes");
        }
        if (this.applicationClassLoader != null) {
            throw new IllegalStateException("applicationClassLoader can only be set once");
        }
        checkNotSealed();
        this.applicationClassLoader = classLoader;
    }

    public final boolean isSealed() {
        return this.sealed;
    }

    protected Context makeContext() {
        return new Context(this);
    }

    protected void observeInstructionCount(Context context, int i2) {
    }

    protected void onContextCreated(Context context) {
        Object obj = this.listeners;
        int i2 = 0;
        while (true) {
            Listener listener = (Listener) Kit.getListener(obj, i2);
            if (listener == null) {
                return;
            }
            listener.contextCreated(context);
            i2++;
        }
    }

    protected void onContextReleased(Context context) {
        Object obj = this.listeners;
        int i2 = 0;
        while (true) {
            Listener listener = (Listener) Kit.getListener(obj, i2);
            if (listener == null) {
                return;
            }
            listener.contextReleased(context);
            i2++;
        }
    }

    public final void removeListener(Listener listener) {
        checkNotSealed();
        synchronized (this.listenersLock) {
            try {
                if (this.disabledListening) {
                    throw new IllegalStateException();
                }
                this.listeners = Kit.removeListener(this.listeners, listener);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void seal() {
        checkNotSealed();
        this.sealed = true;
    }

    public final Context enterContext(Context context) {
        return Context.enter(context, this);
    }
}
