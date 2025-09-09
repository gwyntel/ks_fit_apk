package com.alibaba.ailabs.iot.bluetoothlesdk;

import com.alibaba.ailabs.iot.aisbase.callback.IActionListener;
import com.alibaba.fastjson.JSONObject;

/* loaded from: classes2.dex */
public class ControlMessage {
    boolean enqueued;
    boolean finished;
    IActionListener mCallback;
    private JSONObject mJsonParameters;
    private byte[] mParameters;
    private byte request;
    final Type type;

    public enum Type {
        BIND,
        UNBIND,
        CONTROL
    }

    public ControlMessage(Type type) {
        this.request = (byte) 2;
        this.type = type;
    }

    public ControlMessage callback(IActionListener iActionListener) {
        this.mCallback = iActionListener;
        return this;
    }

    public JSONObject getJsonParameters() {
        return this.mJsonParameters;
    }

    public byte[] getParameters() {
        return this.mParameters;
    }

    public byte getRequest() {
        return this.request;
    }

    public ControlMessage(Type type, byte b2, byte[] bArr) {
        this.request = b2;
        this.type = type;
        this.mParameters = bArr;
    }

    public ControlMessage(Type type, JSONObject jSONObject) {
        this.request = (byte) 2;
        this.type = type;
        this.mJsonParameters = jSONObject;
    }
}
