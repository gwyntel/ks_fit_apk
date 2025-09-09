package com.huawei.hms.api;

import android.os.RemoteException;
import android.text.TextUtils;
import com.huawei.hms.core.aidl.CodecLookup;
import com.huawei.hms.core.aidl.DataBuffer;
import com.huawei.hms.core.aidl.IAIDLCallback;
import com.huawei.hms.core.aidl.IMessageEntity;
import com.huawei.hms.core.aidl.MessageCodec;
import com.huawei.hms.core.aidl.ResponseHeader;
import com.huawei.hms.support.api.transport.DatagramTransport;
import com.huawei.hms.support.log.HMSLog;

/* loaded from: classes.dex */
public class IPCCallback extends IAIDLCallback.Stub {
    private static final String TAG = "IPCCallback";
    private final DatagramTransport.a mCallback;
    private final Class<? extends IMessageEntity> mResponseClass;

    public IPCCallback(Class<? extends IMessageEntity> cls, DatagramTransport.a aVar) {
        this.mResponseClass = cls;
        this.mCallback = aVar;
    }

    @Override // com.huawei.hms.core.aidl.IAIDLCallback
    public void call(DataBuffer dataBuffer) throws RemoteException {
        IMessageEntity iMessageEntityNewResponseInstance;
        if (dataBuffer == null || TextUtils.isEmpty(dataBuffer.URI)) {
            HMSLog.e(TAG, "In call, URI cannot be empty.");
            throw new RemoteException();
        }
        MessageCodec messageCodecFind = CodecLookup.find(dataBuffer.getProtocol());
        if (dataBuffer.getBodySize() > 0) {
            iMessageEntityNewResponseInstance = newResponseInstance();
            if (iMessageEntityNewResponseInstance != null) {
                messageCodecFind.decode(dataBuffer.getBody(), iMessageEntityNewResponseInstance);
            }
        } else {
            iMessageEntityNewResponseInstance = null;
        }
        if (dataBuffer.header == null) {
            this.mCallback.a(0, iMessageEntityNewResponseInstance);
            return;
        }
        ResponseHeader responseHeader = new ResponseHeader();
        messageCodecFind.decode(dataBuffer.header, responseHeader);
        this.mCallback.a(responseHeader.getStatusCode(), iMessageEntityNewResponseInstance);
    }

    protected IMessageEntity newResponseInstance() {
        Class<? extends IMessageEntity> cls = this.mResponseClass;
        if (cls == null) {
            return null;
        }
        try {
            return cls.newInstance();
        } catch (IllegalAccessException | InstantiationException e2) {
            HMSLog.e(TAG, "In newResponseInstance, instancing exception." + e2.getMessage());
            return null;
        }
    }
}
