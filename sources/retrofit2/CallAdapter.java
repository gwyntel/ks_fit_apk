package retrofit2;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/* loaded from: classes5.dex */
public interface CallAdapter<T> {

    public static abstract class Factory {
        protected static Type getParameterUpperBound(int i2, ParameterizedType parameterizedType) {
            return Utils.getParameterUpperBound(i2, parameterizedType);
        }

        protected static Class<?> getRawType(Type type) {
            return Utils.getRawType(type);
        }

        public abstract CallAdapter<?> get(Type type, Annotation[] annotationArr, Retrofit retrofit3);
    }

    <R> T adapt(Call<R> call);

    Type responseType();
}
