package retrofit;

import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/* loaded from: classes5.dex */
public interface Converter<F, T> {

    public static abstract class Factory {
        public Converter<ResponseBody, ?> fromResponseBody(Type type, Annotation[] annotationArr) {
            return null;
        }

        public Converter<?, RequestBody> toRequestBody(Type type, Annotation[] annotationArr) {
            return null;
        }
    }

    T convert(F f2) throws IOException;
}
