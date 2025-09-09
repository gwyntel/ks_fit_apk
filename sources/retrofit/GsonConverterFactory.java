package retrofit;

import com.google.gson.Gson;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import retrofit.Converter;

/* loaded from: classes5.dex */
public final class GsonConverterFactory extends Converter.Factory {
    private final Gson gson;

    private GsonConverterFactory(Gson gson) {
        if (gson == null) {
            throw new NullPointerException("gson == null");
        }
        this.gson = gson;
    }

    public static GsonConverterFactory create() {
        return create(new Gson());
    }

    @Override // retrofit.Converter.Factory
    public Converter<ResponseBody, ?> fromResponseBody(Type type, Annotation[] annotationArr) {
        return new GsonResponseBodyConverter(this.gson, type);
    }

    @Override // retrofit.Converter.Factory
    public Converter<?, RequestBody> toRequestBody(Type type, Annotation[] annotationArr) {
        return new GsonRequestBodyConverter(this.gson, type);
    }

    public static GsonConverterFactory create(Gson gson) {
        return new GsonConverterFactory(gson);
    }
}
