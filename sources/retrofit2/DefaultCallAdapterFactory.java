package retrofit2;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import retrofit2.CallAdapter;

/* loaded from: classes5.dex */
final class DefaultCallAdapterFactory extends CallAdapter.Factory {
    static final CallAdapter.Factory INSTANCE = new DefaultCallAdapterFactory();

    DefaultCallAdapterFactory() {
    }

    @Override // retrofit2.CallAdapter.Factory
    public CallAdapter<?> get(Type type, Annotation[] annotationArr, Retrofit retrofit3) {
        if (CallAdapter.Factory.getRawType(type) != Call.class) {
            return null;
        }
        final Type callResponseType = Utils.getCallResponseType(type);
        return new CallAdapter<Call<?>>() { // from class: retrofit2.DefaultCallAdapterFactory.1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // retrofit2.CallAdapter
            public <R> Call<?> adapt(Call<R> call) {
                return call;
            }

            @Override // retrofit2.CallAdapter
            public Type responseType() {
                return callResponseType;
            }
        };
    }
}
