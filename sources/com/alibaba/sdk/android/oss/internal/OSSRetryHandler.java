package com.alibaba.sdk.android.oss.internal;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.common.OSSLog;
import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;

/* loaded from: classes2.dex */
public class OSSRetryHandler {
    private int maxRetryCount = 2;

    public OSSRetryHandler(int i2) {
        setMaxRetryCount(i2);
    }

    public void setMaxRetryCount(int i2) {
        this.maxRetryCount = i2;
    }

    public OSSRetryType shouldRetry(Exception exc, int i2) {
        if (i2 >= this.maxRetryCount) {
            return OSSRetryType.OSSRetryTypeShouldNotRetry;
        }
        if (!(exc instanceof ClientException)) {
            if (!(exc instanceof ServiceException)) {
                return OSSRetryType.OSSRetryTypeShouldNotRetry;
            }
            ServiceException serviceException = (ServiceException) exc;
            return (serviceException.getErrorCode() == null || !serviceException.getErrorCode().equalsIgnoreCase("RequestTimeTooSkewed")) ? serviceException.getStatusCode() >= 500 ? OSSRetryType.OSSRetryTypeShouldRetry : OSSRetryType.OSSRetryTypeShouldNotRetry : OSSRetryType.OSSRetryTypeShouldFixedTimeSkewedAndRetry;
        }
        if (((ClientException) exc).isCanceledException().booleanValue()) {
            return OSSRetryType.OSSRetryTypeShouldNotRetry;
        }
        Exception exc2 = (Exception) exc.getCause();
        if ((exc2 instanceof InterruptedIOException) && !(exc2 instanceof SocketTimeoutException)) {
            OSSLog.logError("[shouldRetry] - is interrupted!");
            return OSSRetryType.OSSRetryTypeShouldNotRetry;
        }
        if (exc2 instanceof IllegalArgumentException) {
            return OSSRetryType.OSSRetryTypeShouldNotRetry;
        }
        OSSLog.logDebug("shouldRetry - " + exc.toString());
        exc.getCause().printStackTrace();
        return OSSRetryType.OSSRetryTypeShouldRetry;
    }
}
