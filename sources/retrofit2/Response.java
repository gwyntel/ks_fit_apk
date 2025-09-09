package retrofit2;

import com.alibaba.ailabs.iot.aisbase.plugin.ota.ReportProgressUtil;
import okhttp3.Headers;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/* loaded from: classes5.dex */
public final class Response<T> {
    private final T body;
    private final ResponseBody errorBody;
    private final okhttp3.Response rawResponse;

    private Response(okhttp3.Response response, T t2, ResponseBody responseBody) {
        this.rawResponse = response;
        this.body = t2;
        this.errorBody = responseBody;
    }

    public static <T> Response<T> error(int i2, ResponseBody responseBody) {
        if (i2 >= 400) {
            return error(responseBody, new Response.Builder().code(i2).protocol(Protocol.HTTP_1_1).request(new Request.Builder().url("http://localhost/").build()).build());
        }
        throw new IllegalArgumentException("code < 400: " + i2);
    }

    public static <T> Response<T> success(T t2) {
        return success(t2, new Response.Builder().code(200).message(ReportProgressUtil.CODE_OK).protocol(Protocol.HTTP_1_1).request(new Request.Builder().url("http://localhost/").build()).build());
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

    public boolean isSuccessful() {
        return this.rawResponse.isSuccessful();
    }

    public String message() {
        return this.rawResponse.message();
    }

    public okhttp3.Response raw() {
        return this.rawResponse;
    }

    public static <T> Response<T> error(ResponseBody responseBody, okhttp3.Response response) {
        if (responseBody == null) {
            throw new NullPointerException("body == null");
        }
        if (response != null) {
            if (!response.isSuccessful()) {
                return new Response<>(response, null, responseBody);
            }
            throw new IllegalArgumentException("rawResponse should not be successful response");
        }
        throw new NullPointerException("rawResponse == null");
    }

    public static <T> Response<T> success(T t2, Headers headers) {
        if (headers != null) {
            return success(t2, new Response.Builder().code(200).message(ReportProgressUtil.CODE_OK).protocol(Protocol.HTTP_1_1).headers(headers).request(new Request.Builder().url("http://localhost/").build()).build());
        }
        throw new NullPointerException("headers == null");
    }

    public static <T> Response<T> success(T t2, okhttp3.Response response) {
        if (response != null) {
            if (response.isSuccessful()) {
                return new Response<>(response, t2, null);
            }
            throw new IllegalArgumentException("rawResponse must be successful response");
        }
        throw new NullPointerException("rawResponse == null");
    }
}
