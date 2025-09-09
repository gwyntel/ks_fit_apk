package retrofit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/* loaded from: classes5.dex */
public interface CallAdapter<T> {

    public interface Factory {
        CallAdapter<?> get(Type type, Annotation[] annotationArr, Retrofit retrofit3);
    }

    <R> T adapt(Call<R> call);

    Type responseType();
}
