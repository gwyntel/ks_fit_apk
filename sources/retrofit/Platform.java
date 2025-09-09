package retrofit;

import android.os.Handler;
import android.os.Looper;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;
import retrofit.CallAdapter;

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

        @Override // retrofit.Platform
        CallAdapter.Factory a(Executor executor) {
            if (executor == null) {
                executor = new MainThreadExecutor();
            }
            return new ExecutorCallAdapterFactory(executor);
        }
    }

    @IgnoreJRERequirement
    static class Java8 extends Platform {
        Java8() {
        }

        @Override // retrofit.Platform
        Object c(Method method, Class cls, Object obj, Object... objArr) {
            return MethodHandles.lookup().in(cls).unreflectSpecial(method, cls).bindTo(obj).invokeWithArguments(objArr);
        }

        @Override // retrofit.Platform
        boolean d(Method method) {
            return method.isDefault();
        }
    }

    Platform() {
    }

    static Platform b() {
        return PLATFORM;
    }

    private static Platform findPlatform() throws ClassNotFoundException {
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
    }

    CallAdapter.Factory a(Executor executor) {
        return executor != null ? new ExecutorCallAdapterFactory(executor) : DefaultCallAdapter.f26815a;
    }

    Object c(Method method, Class cls, Object obj, Object... objArr) {
        throw new UnsupportedOperationException();
    }

    boolean d(Method method) {
        return false;
    }
}
