package com.aliyun.linksdk.alcs;

import android.text.TextUtils;
import com.aliyun.alink.linksdk.alcs.api.server.AlcsServer;
import com.aliyun.alink.linksdk.alcs.api.server.AlcsServerConfig;
import com.aliyun.alink.linksdk.alcs.coap.AlcsCoAPContext;
import com.aliyun.alink.linksdk.alcs.coap.AlcsCoAPRequest;
import com.aliyun.alink.linksdk.alcs.coap.AlcsCoAPResponse;
import com.aliyun.alink.linksdk.alcs.coap.IAlcsCoAPResHandler;
import com.aliyun.alink.linksdk.alcs.coap.resources.AlcsCoAPResource;
import com.aliyun.alink.linksdk.tools.ALog;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes3.dex */
public class AlcsServerWrapper implements IAlcsServer {
    private static final String TAG = "AlcsServerWrapper";
    private AlcsServer alcsServer;
    private IServerMessageDeliver messageDeliver = null;

    private class ServerMessageDeleverWrapper implements IAlcsCoAPResHandler {
        private IAlcsCoAPResHandler handler;
        private boolean isSecurity;

        public ServerMessageDeleverWrapper(boolean z2, IAlcsCoAPResHandler iAlcsCoAPResHandler) {
            this.isSecurity = z2;
            this.handler = iAlcsCoAPResHandler;
        }

        @Override // com.aliyun.alink.linksdk.alcs.coap.IAlcsCoAPResHandler
        public void onRecRequest(AlcsCoAPContext alcsCoAPContext, AlcsCoAPRequest alcsCoAPRequest) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            ALog.d(AlcsServerWrapper.TAG, "onRecRequest()");
            IAlcsCoAPResHandler iAlcsCoAPResHandler = this.handler;
            if (iAlcsCoAPResHandler != null) {
                iAlcsCoAPResHandler.onRecRequest(alcsCoAPContext, alcsCoAPRequest);
            }
            if (AlcsServerWrapper.this.messageDeliver != null) {
                if (this.isSecurity) {
                    AlcsServerWrapper.this.messageDeliver.onRecSecurityRequest(alcsCoAPContext, alcsCoAPRequest);
                } else {
                    AlcsServerWrapper.this.messageDeliver.onRecRequest(alcsCoAPContext, alcsCoAPRequest);
                }
            }
        }
    }

    public AlcsServerWrapper(AlcsServerConfig alcsServerConfig) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "AlcsServerWrapper()，constructor");
        this.alcsServer = new AlcsServer(alcsServerConfig);
    }

    @Override // com.aliyun.linksdk.alcs.IAlcsServer
    public void addSvrAccessKey(String str, String str2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        AlcsServer alcsServer = this.alcsServer;
        if (alcsServer == null) {
            return;
        }
        alcsServer.addSvrAccessKey(str, str2);
    }

    @Override // com.aliyun.linksdk.alcs.IAlcsServer
    public boolean publishResoucre(String str, Object obj) throws IllegalAccessException, IOException, IllegalArgumentException, InvocationTargetException {
        byte[] byteArray;
        ALog.d(TAG, "publishResoucre()");
        if (this.alcsServer == null) {
            return false;
        }
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(obj);
            objectOutputStream.flush();
            byteArray = byteArrayOutputStream.toByteArray();
        } catch (Exception e2) {
            ALog.e(TAG, "publishResoucre(): convert payload Obj to byte array error");
            e2.printStackTrace();
            byteArray = null;
        }
        if (byteArray != null) {
            return this.alcsServer.notifyRes(str, byteArray);
        }
        ALog.d(TAG, "publishResoucre(): payload is empty");
        return false;
    }

    @Override // com.aliyun.linksdk.alcs.IAlcsServer
    public boolean registerAllResource(boolean z2, AlcsCoAPResource alcsCoAPResource) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "registerAllResource()");
        if (this.alcsServer == null || alcsCoAPResource == null) {
            return false;
        }
        alcsCoAPResource.setNeedAuth(z2 ? 1 : 0);
        alcsCoAPResource.setHandler(new ServerMessageDeleverWrapper(z2, alcsCoAPResource.getHandler()));
        return this.alcsServer.registerAllResource(alcsCoAPResource);
    }

    @Override // com.aliyun.linksdk.alcs.IAlcsServer
    public void removeSvrKey(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        AlcsServer alcsServer = this.alcsServer;
        if (alcsServer == null) {
            return;
        }
        alcsServer.removeSvrKey(str);
    }

    @Override // com.aliyun.linksdk.alcs.IAlcsServer
    public boolean sendResponse(boolean z2, AlcsCoAPResponse alcsCoAPResponse) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "sendResponse(), isSec = " + z2);
        AlcsServer alcsServer = this.alcsServer;
        if (alcsServer == null) {
            return false;
        }
        return z2 ? alcsServer.sendResponseSecure(alcsCoAPResponse) : alcsServer.sendResponse(alcsCoAPResponse);
    }

    @Override // com.aliyun.linksdk.alcs.IAlcsServer
    public void setServerMessageDeliver(IServerMessageDeliver iServerMessageDeliver) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "setServerMessageDeliverer()");
        this.messageDeliver = iServerMessageDeliver;
    }

    @Override // com.aliyun.linksdk.alcs.IAlcsServer
    public void startServer() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "startServer()");
        AlcsServer alcsServer = this.alcsServer;
        if (alcsServer == null) {
            return;
        }
        alcsServer.start();
    }

    @Override // com.aliyun.linksdk.alcs.IAlcsServer
    public void stopServer() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "stopServer()");
        AlcsServer alcsServer = this.alcsServer;
        if (alcsServer == null) {
            return;
        }
        alcsServer.stop();
    }

    @Override // com.aliyun.linksdk.alcs.IAlcsServer
    public void unRegisterResource(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "unRegisterResource(), path = " + str);
        if (this.alcsServer == null || TextUtils.isEmpty(str)) {
            return;
        }
        this.alcsServer.unRegisterResource(str);
    }

    @Override // com.aliyun.linksdk.alcs.IAlcsServer
    public void updateBlackList(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "updateBlackList()");
        AlcsServer alcsServer = this.alcsServer;
        if (alcsServer == null) {
            return;
        }
        alcsServer.updateBlackList(str);
    }

    @Override // com.aliyun.linksdk.alcs.IAlcsServer
    public void updateSvrPrefix(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "updatePrefixSecret()");
        AlcsServer alcsServer = this.alcsServer;
        if (alcsServer == null) {
            return;
        }
        alcsServer.updateSvrPrefix(str);
    }
}
