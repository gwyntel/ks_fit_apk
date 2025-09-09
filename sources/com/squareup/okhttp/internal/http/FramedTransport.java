package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.framed.ErrorCode;
import com.squareup.okhttp.internal.framed.FramedConnection;
import com.squareup.okhttp.internal.framed.FramedStream;
import com.squareup.okhttp.internal.framed.Header;
import java.io.IOException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import okio.ByteString;
import okio.Okio;
import okio.Sink;

/* loaded from: classes4.dex */
public final class FramedTransport implements Transport {
    private final FramedConnection framedConnection;
    private final HttpEngine httpEngine;
    private FramedStream stream;
    private static final List<ByteString> SPDY_3_PROHIBITED_HEADERS = Util.immutableList(ByteString.encodeUtf8("connection"), ByteString.encodeUtf8("host"), ByteString.encodeUtf8("keep-alive"), ByteString.encodeUtf8("proxy-connection"), ByteString.encodeUtf8("transfer-encoding"));
    private static final List<ByteString> HTTP_2_PROHIBITED_HEADERS = Util.immutableList(ByteString.encodeUtf8("connection"), ByteString.encodeUtf8("host"), ByteString.encodeUtf8("keep-alive"), ByteString.encodeUtf8("proxy-connection"), ByteString.encodeUtf8("te"), ByteString.encodeUtf8("transfer-encoding"), ByteString.encodeUtf8("encoding"), ByteString.encodeUtf8("upgrade"));

    public FramedTransport(HttpEngine httpEngine, FramedConnection framedConnection) {
        this.httpEngine = httpEngine;
        this.framedConnection = framedConnection;
    }

    private static boolean isProhibitedHeader(Protocol protocol, ByteString byteString) {
        if (protocol == Protocol.SPDY_3) {
            return SPDY_3_PROHIBITED_HEADERS.contains(byteString);
        }
        if (protocol == Protocol.HTTP_2) {
            return HTTP_2_PROHIBITED_HEADERS.contains(byteString);
        }
        throw new AssertionError(protocol);
    }

    private static String joinOnNull(String str, String str2) {
        return str + (char) 0 + str2;
    }

    public static Response.Builder readNameValueBlock(List<Header> list, Protocol protocol) throws NumberFormatException, IOException {
        Headers.Builder builder = new Headers.Builder();
        builder.set(OkHeaders.SELECTED_PROTOCOL, protocol.toString());
        int size = list.size();
        String str = null;
        String str2 = "HTTP/1.1";
        for (int i2 = 0; i2 < size; i2++) {
            ByteString byteString = list.get(i2).name;
            String strUtf8 = list.get(i2).value.utf8();
            int i3 = 0;
            while (i3 < strUtf8.length()) {
                int iIndexOf = strUtf8.indexOf(0, i3);
                if (iIndexOf == -1) {
                    iIndexOf = strUtf8.length();
                }
                String strSubstring = strUtf8.substring(i3, iIndexOf);
                if (byteString.equals(Header.RESPONSE_STATUS)) {
                    str = strSubstring;
                } else if (byteString.equals(Header.VERSION)) {
                    str2 = strSubstring;
                } else if (!isProhibitedHeader(protocol, byteString)) {
                    builder.add(byteString.utf8(), strSubstring);
                }
                i3 = iIndexOf + 1;
            }
        }
        if (str == null) {
            throw new ProtocolException("Expected ':status' header not present");
        }
        StatusLine statusLine = StatusLine.parse(str2 + " " + str);
        return new Response.Builder().protocol(protocol).code(statusLine.code).message(statusLine.message).headers(builder.build());
    }

    public static List<Header> writeNameValueBlock(Request request, Protocol protocol, String str) {
        Headers headers = request.headers();
        ArrayList arrayList = new ArrayList(headers.size() + 10);
        arrayList.add(new Header(Header.TARGET_METHOD, request.method()));
        arrayList.add(new Header(Header.TARGET_PATH, RequestLine.requestPath(request.httpUrl())));
        String strHostHeader = Util.hostHeader(request.httpUrl());
        if (Protocol.SPDY_3 == protocol) {
            arrayList.add(new Header(Header.VERSION, str));
            arrayList.add(new Header(Header.TARGET_HOST, strHostHeader));
        } else {
            if (Protocol.HTTP_2 != protocol) {
                throw new AssertionError();
            }
            arrayList.add(new Header(Header.TARGET_AUTHORITY, strHostHeader));
        }
        arrayList.add(new Header(Header.TARGET_SCHEME, request.httpUrl().scheme()));
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        int size = headers.size();
        for (int i2 = 0; i2 < size; i2++) {
            ByteString byteStringEncodeUtf8 = ByteString.encodeUtf8(headers.name(i2).toLowerCase(Locale.US));
            String strValue = headers.value(i2);
            if (!isProhibitedHeader(protocol, byteStringEncodeUtf8) && !byteStringEncodeUtf8.equals(Header.TARGET_METHOD) && !byteStringEncodeUtf8.equals(Header.TARGET_PATH) && !byteStringEncodeUtf8.equals(Header.TARGET_SCHEME) && !byteStringEncodeUtf8.equals(Header.TARGET_AUTHORITY) && !byteStringEncodeUtf8.equals(Header.TARGET_HOST) && !byteStringEncodeUtf8.equals(Header.VERSION)) {
                if (linkedHashSet.add(byteStringEncodeUtf8)) {
                    arrayList.add(new Header(byteStringEncodeUtf8, strValue));
                } else {
                    int i3 = 0;
                    while (true) {
                        if (i3 >= arrayList.size()) {
                            break;
                        }
                        if (((Header) arrayList.get(i3)).name.equals(byteStringEncodeUtf8)) {
                            arrayList.set(i3, new Header(byteStringEncodeUtf8, joinOnNull(((Header) arrayList.get(i3)).value.utf8(), strValue)));
                            break;
                        }
                        i3++;
                    }
                }
            }
        }
        return arrayList;
    }

    @Override // com.squareup.okhttp.internal.http.Transport
    public boolean canReuseConnection() {
        return true;
    }

    @Override // com.squareup.okhttp.internal.http.Transport
    public Sink createRequestBody(Request request, long j2) throws IOException {
        return this.stream.getSink();
    }

    @Override // com.squareup.okhttp.internal.http.Transport
    public void disconnect(HttpEngine httpEngine) throws IOException {
        FramedStream framedStream = this.stream;
        if (framedStream != null) {
            framedStream.close(ErrorCode.CANCEL);
        }
    }

    @Override // com.squareup.okhttp.internal.http.Transport
    public void finishRequest() throws IOException {
        this.stream.getSink().close();
    }

    @Override // com.squareup.okhttp.internal.http.Transport
    public ResponseBody openResponseBody(Response response) throws IOException {
        return new RealResponseBody(response.headers(), Okio.buffer(this.stream.getSource()));
    }

    @Override // com.squareup.okhttp.internal.http.Transport
    public Response.Builder readResponseHeaders() throws IOException {
        return readNameValueBlock(this.stream.getResponseHeaders(), this.framedConnection.getProtocol());
    }

    @Override // com.squareup.okhttp.internal.http.Transport
    public void releaseConnectionOnIdle() {
    }

    @Override // com.squareup.okhttp.internal.http.Transport
    public void writeRequestBody(RetryableSink retryableSink) throws IOException {
        retryableSink.writeToSocket(this.stream.getSink());
    }

    @Override // com.squareup.okhttp.internal.http.Transport
    public void writeRequestHeaders(Request request) throws IOException {
        if (this.stream != null) {
            return;
        }
        this.httpEngine.writingRequestHeaders();
        boolean zE = this.httpEngine.e();
        String strVersion = RequestLine.version(this.httpEngine.getConnection().getProtocol());
        FramedConnection framedConnection = this.framedConnection;
        FramedStream framedStreamNewStream = framedConnection.newStream(writeNameValueBlock(request, framedConnection.getProtocol(), strVersion), zE, true);
        this.stream = framedStreamNewStream;
        framedStreamNewStream.readTimeout().timeout(this.httpEngine.f19995a.getReadTimeout(), TimeUnit.MILLISECONDS);
    }
}
