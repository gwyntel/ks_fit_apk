package retrofit;

import com.alipay.sdk.m.u.i;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import java.io.IOException;
import okio.Buffer;
import okio.BufferedSink;

/* loaded from: classes5.dex */
final class RequestBuilder {
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static final String PATH_SEGMENT_ENCODE_SET = " \"<>^`{}|/\\?#";
    private final HttpUrl baseUrl;
    private RequestBody body;
    private MediaType contentType;
    private FormEncodingBuilder formEncodingBuilder;
    private final boolean hasBody;
    private final String method;
    private MultipartBuilder multipartBuilder;
    private String relativeUrl;
    private final Request.Builder requestBuilder;
    private HttpUrl.Builder urlBuilder;

    private static class ContentTypeOverridingRequestBody extends RequestBody {
        private final MediaType contentType;
        private final RequestBody delegate;

        ContentTypeOverridingRequestBody(RequestBody requestBody, MediaType mediaType) {
            this.delegate = requestBody;
            this.contentType = mediaType;
        }

        @Override // com.squareup.okhttp.RequestBody
        public long contentLength() throws IOException {
            return this.delegate.contentLength();
        }

        @Override // com.squareup.okhttp.RequestBody
        public MediaType contentType() {
            return this.contentType;
        }

        @Override // com.squareup.okhttp.RequestBody
        public void writeTo(BufferedSink bufferedSink) throws IOException {
            this.delegate.writeTo(bufferedSink);
        }
    }

    RequestBuilder(String str, HttpUrl httpUrl, String str2, Headers headers, MediaType mediaType, boolean z2, boolean z3, boolean z4) {
        this.method = str;
        this.baseUrl = httpUrl;
        this.relativeUrl = str2;
        Request.Builder builder = new Request.Builder();
        this.requestBuilder = builder;
        this.contentType = mediaType;
        this.hasBody = z2;
        if (headers != null) {
            builder.headers(headers);
        }
        if (z3) {
            this.formEncodingBuilder = new FormEncodingBuilder();
        } else if (z4) {
            MultipartBuilder multipartBuilder = new MultipartBuilder();
            this.multipartBuilder = multipartBuilder;
            multipartBuilder.type(MultipartBuilder.FORM);
        }
    }

    static String g(String str, boolean z2) {
        int length = str.length();
        int iCharCount = 0;
        while (iCharCount < length) {
            int iCodePointAt = str.codePointAt(iCharCount);
            if (iCodePointAt < 32 || iCodePointAt >= 127 || PATH_SEGMENT_ENCODE_SET.indexOf(iCodePointAt) != -1 || (iCodePointAt == 37 && !z2)) {
                Buffer buffer = new Buffer();
                buffer.writeUtf8(str, 0, iCharCount);
                h(buffer, str, iCharCount, length, z2);
                return buffer.readUtf8();
            }
            iCharCount += Character.charCount(iCodePointAt);
        }
        return str;
    }

    static void h(Buffer buffer, String str, int i2, int i3, boolean z2) {
        Buffer buffer2 = null;
        while (i2 < i3) {
            int iCodePointAt = str.codePointAt(i2);
            if (!z2 || (iCodePointAt != 9 && iCodePointAt != 10 && iCodePointAt != 12 && iCodePointAt != 13)) {
                if (iCodePointAt < 32 || iCodePointAt >= 127 || PATH_SEGMENT_ENCODE_SET.indexOf(iCodePointAt) != -1 || (iCodePointAt == 37 && !z2)) {
                    if (buffer2 == null) {
                        buffer2 = new Buffer();
                    }
                    buffer2.writeUtf8CodePoint(iCodePointAt);
                    while (!buffer2.exhausted()) {
                        byte b2 = buffer2.readByte();
                        buffer.writeByte(37);
                        char[] cArr = HEX_DIGITS;
                        buffer.writeByte((int) cArr[((b2 & 255) >> 4) & 15]);
                        buffer.writeByte((int) cArr[b2 & 15]);
                    }
                } else {
                    buffer.writeUtf8CodePoint(iCodePointAt);
                }
            }
            i2 += Character.charCount(iCodePointAt);
        }
    }

    void a(String str, String str2, boolean z2) {
        if (z2) {
            this.formEncodingBuilder.addEncoded(str, str2);
        } else {
            this.formEncodingBuilder.add(str, str2);
        }
    }

    void b(String str, String str2) {
        if ("Content-Type".equalsIgnoreCase(str)) {
            this.contentType = MediaType.parse(str2);
        } else {
            this.requestBuilder.addHeader(str, str2);
        }
    }

    void c(Headers headers, RequestBody requestBody) {
        this.multipartBuilder.addPart(headers, requestBody);
    }

    void d(String str, String str2, boolean z2) {
        String str3 = this.relativeUrl;
        if (str3 == null) {
            throw new AssertionError();
        }
        this.relativeUrl = str3.replace("{" + str + i.f9804d, g(str2, z2));
    }

    void e(String str, String str2, boolean z2) {
        String str3 = this.relativeUrl;
        if (str3 != null) {
            this.urlBuilder = this.baseUrl.resolve(str3).newBuilder();
            this.relativeUrl = null;
        }
        if (z2) {
            this.urlBuilder.addEncodedQueryParameter(str, str2);
        } else {
            this.urlBuilder.addQueryParameter(str, str2);
        }
    }

    Request f() {
        HttpUrl.Builder builder = this.urlBuilder;
        HttpUrl httpUrlBuild = builder != null ? builder.build() : this.baseUrl.resolve(this.relativeUrl);
        RequestBody contentTypeOverridingRequestBody = this.body;
        if (contentTypeOverridingRequestBody == null) {
            FormEncodingBuilder formEncodingBuilder = this.formEncodingBuilder;
            if (formEncodingBuilder != null) {
                contentTypeOverridingRequestBody = formEncodingBuilder.build();
            } else {
                MultipartBuilder multipartBuilder = this.multipartBuilder;
                if (multipartBuilder != null) {
                    contentTypeOverridingRequestBody = multipartBuilder.build();
                } else if (this.hasBody) {
                    contentTypeOverridingRequestBody = RequestBody.create((MediaType) null, new byte[0]);
                }
            }
        }
        MediaType mediaType = this.contentType;
        if (mediaType != null) {
            if (contentTypeOverridingRequestBody != null) {
                contentTypeOverridingRequestBody = new ContentTypeOverridingRequestBody(contentTypeOverridingRequestBody, mediaType);
            } else {
                this.requestBuilder.addHeader("Content-Type", mediaType.toString());
            }
        }
        return this.requestBuilder.url(httpUrlBuild).method(this.method, contentTypeOverridingRequestBody).build();
    }

    void i(RequestBody requestBody) {
        this.body = requestBody;
    }

    void j(String str) {
        this.relativeUrl = str;
    }
}
