package retrofit;

import com.google.gson.Gson;
import com.squareup.okhttp.ResponseBody;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;

/* loaded from: classes5.dex */
final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final Type type;

    GsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override // retrofit.Converter
    public T convert(ResponseBody responseBody) throws IOException {
        Reader readerCharStream = responseBody.charStream();
        try {
            return (T) this.gson.fromJson(readerCharStream, this.type);
        } finally {
            Utils.b(readerCharStream);
        }
    }
}
