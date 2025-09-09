package com.alibaba.cloudapi.sdk.util;

import com.alibaba.cloudapi.sdk.constant.HttpConstant;
import com.alibaba.cloudapi.sdk.constant.SdkConstant;
import com.alibaba.cloudapi.sdk.exception.SdkException;
import com.alibaba.cloudapi.sdk.model.ApiRequest;
import com.alibaba.cloudapi.sdk.signature.ISignerFactory;
import com.alibaba.cloudapi.sdk.signature.ISinger;
import com.alibaba.cloudapi.sdk.signature.SignerFactoryManager;
import com.huawei.hms.framework.common.ContainerUtils;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/* loaded from: classes2.dex */
public class SignUtil {
    private static String buildHeaders(ApiRequest apiRequest) {
        TreeMap treeMap = new TreeMap();
        StringBuilder sb = new StringBuilder();
        int i2 = 0;
        for (Map.Entry<String, List<String>> entry : apiRequest.getHeaders().entrySet()) {
            if (entry.getKey().startsWith(SdkConstant.CLOUDAPI_CA_HEADER_TO_SIGN_PREFIX_SYSTEM)) {
                if (i2 != 0) {
                    sb.append(",");
                }
                i2++;
                sb.append(entry.getKey());
                treeMap.put(entry.getKey(), apiRequest.getFirstHeaderValue(entry.getKey()));
            }
        }
        apiRequest.addHeader(SdkConstant.CLOUDAPI_X_CA_SIGNATURE_HEADERS, sb.toString());
        StringBuilder sb2 = new StringBuilder();
        for (Map.Entry entry2 : treeMap.entrySet()) {
            sb2.append((String) entry2.getKey());
            sb2.append(':');
            sb2.append((String) entry2.getValue());
            sb2.append("\n");
        }
        return sb2.toString();
    }

    private static String buildResource(ApiRequest apiRequest) {
        StringBuilder sb = new StringBuilder();
        sb.append(apiRequest.getPath());
        TreeMap treeMap = new TreeMap();
        if (apiRequest.getQuerys() != null && apiRequest.getQuerys().size() > 0) {
            treeMap.putAll(apiRequest.getQuerys());
        }
        if (apiRequest.getFormParams() != null && apiRequest.getFormParams().size() > 0) {
            treeMap.putAll(apiRequest.getFormParams());
        }
        if (treeMap.size() > 0) {
            sb.append("?");
            boolean z2 = true;
            for (String str : treeMap.keySet()) {
                if (z2) {
                    z2 = false;
                } else {
                    sb.append("&");
                }
                sb.append(str);
                String str2 = (String) treeMap.get(str);
                if (str2 != null && !"".equals(str2)) {
                    sb.append(ContainerUtils.KEY_VALUE_DELIMITER);
                    sb.append(str2);
                }
            }
        }
        return sb.toString();
    }

    private static String buildStringToSign(ApiRequest apiRequest) {
        StringBuilder sb = new StringBuilder();
        sb.append(apiRequest.getMethod().getValue());
        sb.append("\n");
        if (apiRequest.getFirstHeaderValue(HttpConstant.CLOUDAPI_HTTP_HEADER_ACCEPT) != null) {
            sb.append(apiRequest.getFirstHeaderValue(HttpConstant.CLOUDAPI_HTTP_HEADER_ACCEPT));
        }
        sb.append("\n");
        if (apiRequest.getFirstHeaderValue("content-md5") != null) {
            sb.append(apiRequest.getFirstHeaderValue("content-md5"));
        }
        sb.append("\n");
        if (apiRequest.getFirstHeaderValue("content-type") != null) {
            sb.append(apiRequest.getFirstHeaderValue("content-type"));
        }
        sb.append("\n");
        if (apiRequest.getFirstHeaderValue("date") != null) {
            sb.append(apiRequest.getFirstHeaderValue("date"));
        }
        sb.append("\n");
        sb.append(buildHeaders(apiRequest));
        sb.append(buildResource(apiRequest));
        return sb.toString();
    }

    public static String sign(ApiRequest apiRequest, String str) {
        try {
            String strBuildStringToSign = buildStringToSign(apiRequest);
            ISignerFactory iSignerFactoryFindSignerFactory = SignerFactoryManager.findSignerFactory(apiRequest.getSignatureMethod());
            if (iSignerFactoryFindSignerFactory == null) {
                throw new SdkException("unsupported signature method:" + apiRequest.getSignatureMethod());
            }
            ISinger signer = iSignerFactoryFindSignerFactory.getSigner();
            if (signer == null) {
                throw new SdkException("Oops!");
            }
            try {
                return signer.sign(strBuildStringToSign, str);
            } catch (Exception e2) {
                throw new SdkException(e2);
            }
        } catch (Exception e3) {
            throw new RuntimeException(e3);
        }
    }
}
