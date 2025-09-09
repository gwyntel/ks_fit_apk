package retrofit;

import com.squareup.okhttp.Headers;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

/* loaded from: classes5.dex */
public final class Response<T> {
    private final T body;
    private final ResponseBody errorBody;
    private final com.squareup.okhttp.Response rawResponse;

    private Response(com.squareup.okhttp.Response response, T t2, ResponseBody responseBody) {
        this.rawResponse = (com.squareup.okhttp.Response) Utils.a(response, "rawResponse == null");
        this.body = t2;
        this.errorBody = responseBody;
    }

    public static <T> Response<T> error(int i2, ResponseBody responseBody) {
        return error(responseBody, new Response.Builder().code(i2).protocol(Protocol.HTTP_1_1).request(new Request.Builder().url(HttpUrl.parse("http://localhost")).build()).build());
    }

    public static <T> Response<T> success(T t2) {
        return success(t2, new Response.Builder().code(200).protocol(Protocol.HTTP_1_1).request(new Request.Builder().url(HttpUrl.parse("http://localhost")).build()).build());
    }

    public T body() {
        return this.body;
    }

    public int code() {
        return this.rawResponse.code();
    }

    public ResponseBody errorBody() {
        return this.errorBody;
    }

    public Headers headers() {
        return this.rawResponse.headers();
    }

    public boolean isSuccess() {
        return this.rawResponse.isSuccessful();
    }

    public String message() {
        return this.rawResponse.message();
    }

    public com.squareup.okhttp.Response raw() {
        return this.rawResponse;
    }

    public static <T> Response<T> error(ResponseBody responseBody, com.squareup.okhttp.Response response) {
        return new Response<>(response, null, responseBody);
    }

    public static <T> Response<T> success(T t2, com.squareup.okhttp.Response response) {
        return new Response<>(response, t2, null);
    }
}
