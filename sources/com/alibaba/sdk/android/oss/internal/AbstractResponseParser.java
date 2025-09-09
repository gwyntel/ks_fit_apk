package com.alibaba.sdk.android.oss.internal;

import com.alibaba.sdk.android.oss.common.OSSHeaders;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.model.OSSResult;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.CheckedInputStream;
import okhttp3.Headers;
import okhttp3.Response;

/* loaded from: classes2.dex */
public abstract class AbstractResponseParser<T extends OSSResult> implements ResponseParser {
    private Map<String, String> parseResponseHeader(Response response) {
        HashMap map = new HashMap();
        Headers headers = response.headers();
        for (int i2 = 0; i2 < headers.size(); i2++) {
            map.put(headers.name(i2), headers.value(i2));
        }
        return map;
    }

    public static void safeCloseResponse(ResponseMessage responseMessage) {
        try {
            responseMessage.close();
        } catch (Exception unused) {
        }
    }

    public boolean needCloseResponse() {
        return true;
    }

    @Override // com.alibaba.sdk.android.oss.internal.ResponseParser
    public T parse(ResponseMessage responseMessage) throws IOException {
        try {
            try {
                T t2 = (T) ((Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]).newInstance();
                if (t2 != null) {
                    t2.setRequestId((String) responseMessage.getHeaders().get(OSSHeaders.OSS_HEADER_REQUEST_ID));
                    t2.setStatusCode(responseMessage.getStatusCode());
                    t2.setResponseHeader(parseResponseHeader(responseMessage.getResponse()));
                    setCRC(t2, responseMessage);
                    t2 = (T) parseData(responseMessage, t2);
                }
                return t2;
            } catch (Exception e2) {
                IOException iOException = new IOException(e2.getMessage(), e2);
                e2.printStackTrace();
                OSSLog.logThrowable2Local(e2);
                throw iOException;
            }
        } finally {
            if (needCloseResponse()) {
                safeCloseResponse(responseMessage);
            }
        }
    }

    abstract T parseData(ResponseMessage responseMessage, T t2) throws Exception;

    public <Result extends OSSResult> void setCRC(Result result, ResponseMessage responseMessage) {
        InputStream content = responseMessage.getRequest().getContent();
        if (content != null && (content instanceof CheckedInputStream)) {
            result.setClientCRC(Long.valueOf(((CheckedInputStream) content).getChecksum().getValue()));
        }
        String str = (String) responseMessage.getHeaders().get(OSSHeaders.OSS_HASH_CRC64_ECMA);
        if (str != null) {
            result.setServerCRC(Long.valueOf(new BigInteger(str).longValue()));
        }
    }
}
