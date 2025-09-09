package retrofit;

import com.squareup.okhttp.ResponseBody;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/* loaded from: classes5.dex */
final class MethodHandler<T> {
    private final CallAdapter<T> callAdapter;
    private final RequestFactory requestFactory;
    private final Converter<ResponseBody, T> responseConverter;

    /* renamed from: retrofit, reason: collision with root package name */
    private final Retrofit f26823retrofit;

    private MethodHandler(Retrofit retrofit3, RequestFactory requestFactory, CallAdapter<T> callAdapter, Converter<ResponseBody, T> converter) {
        this.f26823retrofit = retrofit3;
        this.requestFactory = requestFactory;
        this.callAdapter = callAdapter;
        this.responseConverter = converter;
    }

    static MethodHandler a(Retrofit retrofit3, Method method) {
        CallAdapter<?> callAdapterCreateCallAdapter = createCallAdapter(method, retrofit3);
        Type typeResponseType = callAdapterCreateCallAdapter.responseType();
        return new MethodHandler(retrofit3, RequestFactoryParser.a(method, typeResponseType, retrofit3), callAdapterCreateCallAdapter, createResponseConverter(method, retrofit3, typeResponseType));
    }

    private static CallAdapter<?> createCallAdapter(Method method, Retrofit retrofit3) {
        Type genericReturnType = method.getGenericReturnType();
        if (Utils.hasUnresolvableType(genericReturnType)) {
            throw Utils.f(method, "Method return type must not include a type variable or wildcard: %s", genericReturnType);
        }
        if (genericReturnType == Void.TYPE) {
            throw Utils.f(method, "Service methods cannot return void.", new Object[0]);
        }
        try {
            return retrofit3.callAdapter(genericReturnType, method.getAnnotations());
        } catch (RuntimeException e2) {
            throw Utils.e(e2, method, "Unable to create call adapter for %s", genericReturnType);
        }
    }

    private static Converter<ResponseBody, ?> createResponseConverter(Method method, Retrofit retrofit3, Type type) {
        try {
            return retrofit3.responseConverter(type, method.getAnnotations());
        } catch (RuntimeException e2) {
            throw Utils.e(e2, method, "Unable to create converter for %s", type);
        }
    }

    Object b(Object... objArr) {
        return this.callAdapter.adapt(new OkHttpCall(this.f26823retrofit, this.requestFactory, this.responseConverter, objArr));
    }
}
