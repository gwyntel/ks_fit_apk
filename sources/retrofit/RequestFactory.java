package retrofit;

import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Request;

/* loaded from: classes5.dex */
final class RequestFactory {
    private final BaseUrl baseUrl;
    private final MediaType contentType;
    private final boolean hasBody;
    private final Headers headers;
    private final boolean isFormEncoded;
    private final boolean isMultipart;
    private final String method;
    private final String relativeUrl;
    private final RequestBuilderAction[] requestBuilderActions;

    RequestFactory(String str, BaseUrl baseUrl, String str2, Headers headers, MediaType mediaType, boolean z2, boolean z3, boolean z4, RequestBuilderAction[] requestBuilderActionArr) {
        this.method = str;
        this.baseUrl = baseUrl;
        this.relativeUrl = str2;
        this.headers = headers;
        this.contentType = mediaType;
        this.hasBody = z2;
        this.isFormEncoded = z3;
        this.isMultipart = z4;
        this.requestBuilderActions = requestBuilderActionArr;
    }

    Request a(Object... objArr) {
        RequestBuilder requestBuilder = new RequestBuilder(this.method, this.baseUrl.url(), this.relativeUrl, this.headers, this.contentType, this.hasBody, this.isFormEncoded, this.isMultipart);
        if (objArr != null) {
            RequestBuilderAction[] requestBuilderActionArr = this.requestBuilderActions;
            if (requestBuilderActionArr.length != objArr.length) {
                throw new IllegalArgumentException("Argument count (" + objArr.length + ") doesn't match action count (" + requestBuilderActionArr.length + ")");
            }
            int length = objArr.length;
            for (int i2 = 0; i2 < length; i2++) {
                requestBuilderActionArr[i2].a(requestBuilder, objArr[i2]);
            }
        }
        return requestBuilder.f();
    }
}
