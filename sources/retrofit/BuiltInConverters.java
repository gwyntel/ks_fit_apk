package retrofit;

import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import retrofit.Converter;
import retrofit.http.Streaming;

/* loaded from: classes5.dex */
final class BuiltInConverters extends Converter.Factory {

    static final class OkHttpRequestBodyConverter implements Converter<RequestBody, RequestBody> {
        OkHttpRequestBodyConverter() {
        }

        @Override // retrofit.Converter
        public RequestBody convert(RequestBody requestBody) throws IOException {
            return requestBody;
        }
    }

    static final class OkHttpResponseBodyConverter implements Converter<ResponseBody, ResponseBody> {
        private final boolean isStreaming;

        OkHttpResponseBodyConverter(boolean z2) {
            this.isStreaming = z2;
        }

        @Override // retrofit.Converter
        public ResponseBody convert(ResponseBody responseBody) throws IOException {
            if (this.isStreaming) {
                return responseBody;
            }
            try {
                return Utils.g(responseBody);
            } finally {
                Utils.b(responseBody);
            }
        }
    }

    static final class VoidConverter implements Converter<ResponseBody, Void> {
        VoidConverter() {
        }

        @Override // retrofit.Converter
        public Void convert(ResponseBody responseBody) throws IOException {
            responseBody.close();
            return null;
        }
    }

    BuiltInConverters() {
    }

    @Override // retrofit.Converter.Factory
    public Converter<ResponseBody, ?> fromResponseBody(Type type, Annotation[] annotationArr) {
        if (ResponseBody.class.equals(type)) {
            return new OkHttpResponseBodyConverter(Utils.d(annotationArr, Streaming.class));
        }
        if (Void.class.equals(type)) {
            return new VoidConverter();
        }
        return null;
    }

    @Override // retrofit.Converter.Factory
    public Converter<?, RequestBody> toRequestBody(Type type, Annotation[] annotationArr) {
        if ((type instanceof Class) && RequestBody.class.isAssignableFrom((Class) type)) {
            return new OkHttpRequestBodyConverter();
        }
        return null;
    }
}
