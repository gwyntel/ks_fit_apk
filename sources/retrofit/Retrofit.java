package retrofit;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import retrofit.CallAdapter;
import retrofit.Converter;

/* loaded from: classes5.dex */
public final class Retrofit {
    private final List<CallAdapter.Factory> adapterFactories;
    private final BaseUrl baseUrl;
    private final Executor callbackExecutor;
    private final OkHttpClient client;
    private final List<Converter.Factory> converterFactories;
    private final Map<Method, MethodHandler<?>> methodHandlerCache;
    private final boolean validateEagerly;

    private void eagerlyValidateMethods(Class<?> cls) throws SecurityException {
        Platform platformB = Platform.b();
        for (Method method : cls.getDeclaredMethods()) {
            if (!platformB.d(method)) {
                a(method);
            }
        }
    }

    MethodHandler a(Method method) {
        MethodHandler<?> methodHandlerA;
        synchronized (this.methodHandlerCache) {
            try {
                methodHandlerA = this.methodHandlerCache.get(method);
                if (methodHandlerA == null) {
                    methodHandlerA = MethodHandler.a(this, method);
                    this.methodHandlerCache.put(method, methodHandlerA);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return methodHandlerA;
    }

    public BaseUrl baseUrl() {
        return this.baseUrl;
    }

    public CallAdapter<?> callAdapter(Type type, Annotation[] annotationArr) {
        return nextCallAdapter(null, type, annotationArr);
    }

    public List<CallAdapter.Factory> callAdapterFactories() {
        return Collections.unmodifiableList(this.adapterFactories);
    }

    public Executor callbackExecutor() {
        return this.callbackExecutor;
    }

    public OkHttpClient client() {
        return this.client;
    }

    public List<Converter.Factory> converterFactories() {
        return Collections.unmodifiableList(this.converterFactories);
    }

    public <T> T create(final Class<T> cls) throws SecurityException {
        Utils.h(cls);
        if (this.validateEagerly) {
            eagerlyValidateMethods(cls);
        }
        return (T) Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{cls}, new InvocationHandler() { // from class: retrofit.Retrofit.1
            private final Platform platform = Platform.b();

            @Override // java.lang.reflect.InvocationHandler
            public Object invoke(Object obj, Method method, Object... objArr) throws Throwable {
                return method.getDeclaringClass() == Object.class ? method.invoke(this, objArr) : this.platform.d(method) ? this.platform.c(method, cls, obj, objArr) : Retrofit.this.a(method).b(objArr);
            }
        });
    }

    public CallAdapter<?> nextCallAdapter(CallAdapter.Factory factory, Type type, Annotation[] annotationArr) {
        Utils.a(type, "returnType == null");
        Utils.a(annotationArr, "annotations == null");
        int iIndexOf = this.adapterFactories.indexOf(factory) + 1;
        int size = this.adapterFactories.size();
        for (int i2 = iIndexOf; i2 < size; i2++) {
            CallAdapter<?> callAdapter = this.adapterFactories.get(i2).get(type, annotationArr, this);
            if (callAdapter != null) {
                return callAdapter;
            }
        }
        StringBuilder sb = new StringBuilder("Could not locate call adapter for ");
        sb.append(type);
        sb.append(". Tried:");
        int size2 = this.adapterFactories.size();
        for (int i3 = iIndexOf; i3 < size2; i3++) {
            sb.append("\n * ");
            sb.append(this.adapterFactories.get(i3).getClass().getName());
        }
        if (factory != null) {
            sb.append("\nSkipped:");
            for (int i4 = 0; i4 < iIndexOf; i4++) {
                sb.append("\n * ");
                sb.append(this.adapterFactories.get(i4).getClass().getName());
            }
        }
        throw new IllegalArgumentException(sb.toString());
    }

    public <T> Converter<T, RequestBody> requestConverter(Type type, Annotation[] annotationArr) {
        Utils.a(type, "type == null");
        Utils.a(annotationArr, "annotations == null");
        int size = this.converterFactories.size();
        for (int i2 = 0; i2 < size; i2++) {
            Converter<T, RequestBody> converter = (Converter<T, RequestBody>) this.converterFactories.get(i2).toRequestBody(type, annotationArr);
            if (converter != null) {
                return converter;
            }
        }
        StringBuilder sb = new StringBuilder("Could not locate RequestBody converter for ");
        sb.append(type);
        sb.append(". Tried:");
        for (Converter.Factory factory : this.converterFactories) {
            sb.append("\n * ");
            sb.append(factory.getClass().getName());
        }
        throw new IllegalArgumentException(sb.toString());
    }

    public <T> Converter<ResponseBody, T> responseConverter(Type type, Annotation[] annotationArr) {
        Utils.a(type, "type == null");
        Utils.a(annotationArr, "annotations == null");
        int size = this.converterFactories.size();
        for (int i2 = 0; i2 < size; i2++) {
            Converter<ResponseBody, T> converter = (Converter<ResponseBody, T>) this.converterFactories.get(i2).fromResponseBody(type, annotationArr);
            if (converter != null) {
                return converter;
            }
        }
        StringBuilder sb = new StringBuilder("Could not locate ResponseBody converter for ");
        sb.append(type);
        sb.append(". Tried:");
        for (Converter.Factory factory : this.converterFactories) {
            sb.append("\n * ");
            sb.append(factory.getClass().getName());
        }
        throw new IllegalArgumentException(sb.toString());
    }

    private Retrofit(OkHttpClient okHttpClient, BaseUrl baseUrl, List<Converter.Factory> list, List<CallAdapter.Factory> list2, Executor executor, boolean z2) {
        this.methodHandlerCache = new LinkedHashMap();
        this.client = okHttpClient;
        this.baseUrl = baseUrl;
        this.converterFactories = list;
        this.adapterFactories = list2;
        this.callbackExecutor = executor;
        this.validateEagerly = z2;
    }

    public static final class Builder {
        private BaseUrl baseUrl;
        private Executor callbackExecutor;
        private OkHttpClient client;
        private boolean validateEagerly;
        private List<Converter.Factory> converterFactories = new ArrayList();
        private List<CallAdapter.Factory> adapterFactories = new ArrayList();

        public Builder() {
            this.converterFactories.add(new BuiltInConverters());
        }

        /* JADX WARN: Multi-variable type inference failed */
        public Builder addCallAdapterFactory(CallAdapter.Factory factory) {
            this.adapterFactories.add(Utils.a(factory, "factory == null"));
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public Builder addConverterFactory(Converter.Factory factory) {
            this.converterFactories.add(Utils.a(factory, "converterFactory == null"));
            return this;
        }

        public Builder baseUrl(String str) {
            Utils.a(str, "baseUrl == null");
            HttpUrl httpUrl = HttpUrl.parse(str);
            if (httpUrl != null) {
                return baseUrl(httpUrl);
            }
            throw new IllegalArgumentException("Illegal URL: " + str);
        }

        public Retrofit build() {
            if (this.baseUrl == null) {
                throw new IllegalStateException("Base URL required.");
            }
            OkHttpClient okHttpClient = this.client;
            if (okHttpClient == null) {
                okHttpClient = new OkHttpClient();
            }
            OkHttpClient okHttpClient2 = okHttpClient;
            ArrayList arrayList = new ArrayList(this.adapterFactories);
            arrayList.add(Platform.b().a(this.callbackExecutor));
            return new Retrofit(okHttpClient2, this.baseUrl, new ArrayList(this.converterFactories), arrayList, this.callbackExecutor, this.validateEagerly);
        }

        public Builder callbackExecutor(Executor executor) {
            this.callbackExecutor = (Executor) Utils.a(executor, "callbackExecutor == null");
            return this;
        }

        public Builder client(OkHttpClient okHttpClient) {
            this.client = (OkHttpClient) Utils.a(okHttpClient, "client == null");
            return this;
        }

        public Builder validateEagerly() {
            this.validateEagerly = true;
            return this;
        }

        public Builder baseUrl(final HttpUrl httpUrl) {
            Utils.a(httpUrl, "baseUrl == null");
            return baseUrl(new BaseUrl() { // from class: retrofit.Retrofit.Builder.1
                @Override // retrofit.BaseUrl
                public HttpUrl url() {
                    return httpUrl;
                }
            });
        }

        public Builder baseUrl(BaseUrl baseUrl) {
            this.baseUrl = (BaseUrl) Utils.a(baseUrl, "baseUrl == null");
            return this;
        }
    }
}
