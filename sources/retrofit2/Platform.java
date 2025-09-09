package retrofit2;

import android.os.Handler;
import android.os.Looper;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;
import retrofit2.CallAdapter;

/* loaded from: classes5.dex */
class Platform {
    private static final Platform PLATFORM = findPlatform();

    static class Android extends Platform {

        static class MainThreadExecutor implements Executor {
            private final Handler handler = new Handler(Looper.getMainLooper());

            MainThreadExecutor() {
            }

            @Override // java.util.concurrent.Executor
            public void execute(Runnable runnable) {
                this.handler.post(runnable);
            }
        }

        Android() {
        }

        @Override // retrofit2.Platform
        CallAdapter.Factory defaultCallAdapterFactory(Executor executor) {
            return new ExecutorCallAdapterFactory(executor);
        }

        @Override // retrofit2.Platform
        public Executor defaultCallbackExecutor() {
            return new MainThreadExecutor();
        }
    }

    static class IOS extends Platform {

        static class MainThreadExecutor implements Executor {
            private static Method addOperation;
            private static Object queue;

            static {
                try {
                    Class<?> cls = Class.forName("org.robovm.apple.foundation.NSOperationQueue");
                    queue = cls.getDeclaredMethod("getMainQueue", null).invoke(null, null);
                    addOperation = cls.getDeclaredMethod("addOperation", Runnable.class);
                } catch (Exception e2) {
                    throw new AssertionError(e2);
                }
            }

            MainThreadExecutor() {
            }

            @Override // java.util.concurrent.Executor
            public void execute(Runnable runnable) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                try {
                    addOperation.invoke(queue, runnable);
                } catch (IllegalAccessException e2) {
                    e = e2;
                    throw new AssertionError(e);
                } catch (IllegalArgumentException e3) {
                    e = e3;
                    throw new AssertionError(e);
                } catch (InvocationTargetException e4) {
                    Throwable cause = e4.getCause();
                    if (cause instanceof RuntimeException) {
                        throw ((RuntimeException) cause);
                    }
                    if (!(cause instanceof Error)) {
                        throw new RuntimeException(cause);
                    }
                    throw ((Error) cause);
                }
            }
        }

        IOS() {
        }

        @Override // retrofit2.Platform
        CallAdapter.Factory defaultCallAdapterFactory(Executor executor) {
            return new ExecutorCallAdapterFactory(executor);
        }

        @Override // retrofit2.Platform
        public Executor defaultCallbackExecutor() {
            return new MainThreadExecutor();
        }
    }

    @IgnoreJRERequirement
    static class Java8 extends Platform {
        Java8() {
        }

        @Override // retrofit2.Platform
        Object invokeDefaultMethod(Method method, Class<?> cls, Object obj, Object... objArr) throws Throwable {
            Constructor declaredConstructor = a.a().getDeclaredConstructor(Class.class, Integer.TYPE);
            declaredConstructor.setAccessible(true);
            return b.a(declaredConstructor.newInstance(cls, -1)).unreflectSpecial(method, cls).bindTo(obj).invokeWithArguments(objArr);
        }

        @Override // retrofit2.Platform
        boolean isDefaultMethod(Method method) {
            return method.isDefault();
        }
    }

    Platform() {
    }

    private static Platform findPlatform() throws ClassNotFoundException {
        try {
            try {
                try {
                    Class.forName("android.os.Build");
                    return new Android();
                } catch (ClassNotFoundException unused) {
                    Class.forName("java.util.Optional");
                    return new Java8();
                }
            } catch (ClassNotFoundException unused2) {
                return new Platform();
            }
        } catch (ClassNotFoundException unused3) {
            Class.forName("org.robovm.apple.foundation.NSObject");
            return new IOS();
        }
    }

    static Platform get() {
        return PLATFORM;
    }

    CallAdapter.Factory defaultCallAdapterFactory(Executor executor) {
        return executor != null ? new ExecutorCallAdapterFactory(executor) : DefaultCallAdapterFactory.INSTANCE;
    }

    Executor defaultCallbackExecutor() {
        return null;
    }

    Object invokeDefaultMethod(Method method, Class<?> cls, Object obj, Object... objArr) throws Throwable {
        throw new UnsupportedOperationException();
    }

    boolean isDefaultMethod(Method method) {
        return false;
    }
}
