package retrofit;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import okio.Buffer;

/* loaded from: classes5.dex */
final class GsonRequestBodyConverter<T> implements Converter<T, RequestBody> {
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
    private static final Charset UTF_8 = Charset.forName("UTF-8");
    private final Gson gson;
    private final Type type;

    GsonRequestBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // retrofit.Converter
    public /* bridge */ /* synthetic */ RequestBody convert(Object obj) throws IOException {
        return convert((GsonRequestBodyConverter<T>) obj);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // retrofit.Converter
    public RequestBody convert(T t2) throws JsonIOException, IOException {
        Buffer buffer = new Buffer();
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(buffer.outputStream(), UTF_8);
        try {
            this.gson.toJson(t2, this.type, outputStreamWriter);
            outputStreamWriter.flush();
            return RequestBody.create(MEDIA_TYPE, buffer.readByteString());
        } catch (IOException e2) {
            throw new AssertionError(e2);
        }
    }
}
