package com.aliyun.alink.linksdk.alcs.coap;

import com.aliyun.alink.linksdk.alcs.coap.AlcsCoAPConstant;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class AlcsCoAPResponse extends AlcsCoAPMessage {
    private AlcsCoAPConstant.ResponseCode code;
    private boolean last = true;
    private long rtt;

    public AlcsCoAPResponse(AlcsCoAPConstant.ResponseCode responseCode) {
        this.code = responseCode;
    }

    public static AlcsCoAPResponse createResponse(AlcsCoAPRequest alcsCoAPRequest, AlcsCoAPConstant.ResponseCode responseCode) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        AlcsCoAPResponse alcsCoAPResponse = new AlcsCoAPResponse(responseCode);
        alcsCoAPResponse.setDestination(alcsCoAPRequest.getSource());
        alcsCoAPResponse.setDestinationPort(alcsCoAPRequest.getSourcePort());
        alcsCoAPResponse.setToken(alcsCoAPRequest.getToken());
        return alcsCoAPResponse;
    }

    public AlcsCoAPConstant.ResponseCode getCode() {
        return this.code;
    }

    public long getRTT() {
        return this.rtt;
    }

    @Override // com.aliyun.alink.linksdk.alcs.coap.AlcsCoAPMessage
    public int getRawCode() {
        return this.code.value;
    }

    public boolean hasBlockOption() {
        return getOptions().hasBlock1() || getOptions().hasBlock2();
    }

    public final boolean isClientError() {
        return AlcsCoAPConstant.ResponseCode.isClientError(this.code);
    }

    public final boolean isError() {
        return isClientError() || isServerError();
    }

    public boolean isLast() {
        return this.last;
    }

    public boolean isNotification() {
        return getOptions().hasObserve();
    }

    public final boolean isServerError() {
        return AlcsCoAPConstant.ResponseCode.isServerError(this.code);
    }

    public void setLast(boolean z2) {
        this.last = z2;
    }

    public final AlcsCoAPResponse setObserve() {
        getOptions().setObserve(0);
        return this;
    }

    public void setRTT(long j2) {
        this.rtt = j2;
    }

    public void setResponseCode(int i2) {
        this.code = AlcsCoAPConstant.ResponseCode.valueOf(i2);
    }

    public AlcsCoAPResponse(int i2) {
        this.code = AlcsCoAPConstant.ResponseCode.valueOf(i2);
    }
}
