package retrofit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import retrofit.CallAdapter;

/* loaded from: classes5.dex */
final class DefaultCallAdapter implements CallAdapter<Call<?>> {

    /* renamed from: a, reason: collision with root package name */
    static final CallAdapter.Factory f26815a = new CallAdapter.Factory() { // from class: retrofit.DefaultCallAdapter.1
        @Override // retrofit.CallAdapter.Factory
        public CallAdapter<?> get(Type type, Annotation[] annotationArr, Retrofit retrofit3) {
            if (Utils.getRawType(type) != Call.class) {
                return null;
            }
            return new DefaultCallAdapter(Utils.c(type));
        }
    };
    private final Type responseType;

    DefaultCallAdapter(Type type) {
        this.responseType = type;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // retrofit.CallAdapter
    public <R> Call<?> adapt(Call<R> call) {
        return call;
    }

    @Override // retrofit.CallAdapter
    public Type responseType() {
        return this.responseType;
    }
}
