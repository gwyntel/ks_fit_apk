package com.aliyun.alink.linksdk.alcs.coap;

import android.text.TextUtils;
import android.util.SparseArray;
import com.aliyun.alink.linksdk.alcs.coap.option.Option;
import com.aliyun.alink.linksdk.alcs.coap.option.OptionSet;
import com.aliyun.alink.linksdk.alcs.coap.resources.AlcsCoAPResource;
import com.aliyun.alink.linksdk.alcs.coap.resources.Resource;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes2.dex */
public class AlcsCoAP {
    protected static final String TAG = "[alcs_coap_sdk]AlcsCoAP";
    protected static SparseArray<IAuthHandler> mAuthHandlerList;
    protected static Map<Long, AlcsCoAPContext> mContextList;
    protected static Map<Long, Map<Long, IAlcsCoAPReqHandler>> mReqHandlerList;
    protected static Map<String, AlcsCoAPResource> mResourceList;
    protected static AtomicInteger mUserData;

    static {
        System.loadLibrary("coap");
        mUserData = new AtomicInteger(0);
    }

    public AlcsCoAP() {
        if (mContextList == null) {
            mContextList = new HashMap();
        }
        if (mResourceList == null) {
            mResourceList = new HashMap();
        }
        if (mReqHandlerList == null) {
            mReqHandlerList = new HashMap();
        }
        if (mAuthHandlerList == null) {
            mAuthHandlerList = new SparseArray<>();
        }
    }

    protected static IAlcsCoAPReqHandler getRequestCallback(long j2, long j3) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "getRequestCallback coapContextId: " + j2 + " msgId:" + j3);
        Map<Long, IAlcsCoAPReqHandler> map = mReqHandlerList.get(Long.valueOf(j2));
        if (map != null) {
            return map.get(Long.valueOf(j3));
        }
        ALog.e(TAG, "getRequestCallback not find context");
        return null;
    }

    public static void onClientAuthComplete(long j2, String str, int i2, int i3, int i4) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "onClientAuthComplete contextId:" + j2 + " Ip:" + str + " port:" + i2 + " result:" + i4 + " userdata:" + i3);
        IAuthHandler iAuthHandler = mAuthHandlerList.get(i3);
        if (iAuthHandler != null) {
            iAuthHandler.onAuthResult(str, i2, i4);
        } else {
            ALog.e(TAG, "onClientAuthComplete error handler not found");
        }
        mAuthHandlerList.remove(i3);
    }

    public static void onRecvRequestHandler(long j2, String str, String str2, int i2, AlcsCoAPRequest alcsCoAPRequest) throws IllegalAccessException, IllegalArgumentException, UnknownHostException, InvocationTargetException {
        InetAddress byName;
        ALog.d(TAG, "onRecvRequestHandler test contextId:" + j2 + " resourceId:" + str + " Ip:" + str2 + " port:" + i2 + " request:" + alcsCoAPRequest);
        alcsCoAPRequest.setSourcePort(i2);
        try {
            byName = InetAddress.getByName(str2);
        } catch (Exception e2) {
            e2.printStackTrace();
            byName = null;
        }
        alcsCoAPRequest.setSource(byName);
        AlcsCoAPResource alcsCoAPResource = mResourceList.get(str);
        AlcsCoAPContext alcsCoAPContext = mContextList.get(Long.valueOf(j2));
        if (alcsCoAPResource == null || alcsCoAPResource.getHandler() == null) {
            ALog.e(TAG, "onRecvRequestHandler callback error null");
        } else {
            alcsCoAPResource.getHandler().onRecRequest(alcsCoAPContext, alcsCoAPRequest);
        }
    }

    public static void onSendRequestComplete(long j2, long j3, String str, int i2, int i3, AlcsCoAPResponse alcsCoAPResponse) throws IllegalAccessException, IllegalArgumentException, UnknownHostException, InvocationTargetException {
        InetAddress byName;
        ALog.d(TAG, "onSendRequestComplete contextId:" + j2 + " msgId:" + j3 + " Ip:" + str + " port:" + i2 + " result:" + i3 + " response:" + alcsCoAPResponse);
        try {
            byName = InetAddress.getByName(str);
        } catch (Exception e2) {
            e2.printStackTrace();
            byName = null;
        }
        if (alcsCoAPResponse != null) {
            alcsCoAPResponse.setSource(byName);
            alcsCoAPResponse.setSourcePort(i2);
        }
        AlcsCoAPContext alcsCoAPContext = mContextList.get(Long.valueOf(j2));
        IAlcsCoAPReqHandler requestCallback = getRequestCallback(j2, j3);
        if (requestCallback != null) {
            requestCallback.onReqComplete(alcsCoAPContext, i3, alcsCoAPResponse);
        } else {
            ALog.e(TAG, "onSendRequestComplete callback error null");
        }
    }

    public static void setLogLevelEx(int i2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "setNativeLogLevel logLevel:" + i2);
        setLogLevelNative(i2);
    }

    protected static native void setLogLevelNative(int i2);

    public boolean addAlcsSvrAccessKey(long j2, String str, String str2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        boolean zAddSvrAccessKey = addSvrAccessKey(j2, str, str2);
        ALog.d(TAG, "addAlcsSvrAccessKey coapContextId:" + j2 + " prefix:" + str + " ret:" + zAddSvrAccessKey);
        return zAddSvrAccessKey;
    }

    protected native void addIntOption(long j2, long j3, int i2, int i3);

    protected native boolean addKey(String str, int i2, int i3, String str2);

    protected native void addStringOption(long j2, long j3, int i2, String str);

    protected native boolean addSvrAccessKey(long j2, String str, String str2);

    public native void alcsStart(long j2);

    public native void alcsStop(long j2);

    protected native boolean authHasKey(long j2, String str, int i2, String str2, String str3, String str4, String str5, int i3);

    public boolean authHasKey(long j2, String str, int i2, String str2, String str3, String str4, String str5, IAuthHandler iAuthHandler) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        int iIncrementAndGet = mUserData.incrementAndGet();
        ALog.d(TAG, "authHasKey coapContextId:" + j2 + " ip:" + str + " port:" + i2 + " userData:" + iIncrementAndGet + " productKey:" + str2 + " deviceName:" + str3 + " accessKey:" + str4 + " accessToken:" + str5);
        mAuthHandlerList.put(iIncrementAndGet, iAuthHandler);
        return authHasKey(j2, str, i2, str2, str3, str4, str5, iIncrementAndGet);
    }

    public native boolean cancelMessage(long j2, long j3);

    protected native long createCoAPContext(AlcsCoAPContext alcsCoAPContext);

    public long createCoAPContext(AlcsCoAPContext alcsCoAPContext, AlcsCoAPResource alcsCoAPResource) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        long jCreateNewCoAPContext = createNewCoAPContext(alcsCoAPContext);
        registerAllResource(jCreateNewCoAPContext, alcsCoAPResource, null, null);
        return jCreateNewCoAPContext;
    }

    public long createNewCoAPContext(AlcsCoAPContext alcsCoAPContext) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (alcsCoAPContext == null) {
            ALog.e(TAG, "createNewCoAPContext error context null");
            return -1L;
        }
        long contextByPort = getContextByPort(alcsCoAPContext.getPort());
        if (contextByPort != 0) {
            alcsCoAPContext.mContextId = contextByPort;
            return contextByPort;
        }
        long jCreateCoAPContext = createCoAPContext(alcsCoAPContext);
        alcsCoAPContext.setContextId(jCreateCoAPContext);
        mContextList.put(Long.valueOf(jCreateCoAPContext), alcsCoAPContext);
        return jCreateCoAPContext;
    }

    public long freeCoAPContext(long j2) {
        mContextList.remove(Long.valueOf(j2));
        freeContext(j2);
        return j2;
    }

    protected native void freeContext(long j2);

    protected long getContextByPort(int i2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Map<Long, AlcsCoAPContext> map = mContextList;
        long jLongValue = 0;
        if (map == null) {
            ALog.e(TAG, "getContextByPort mContextList empty");
            return 0L;
        }
        Iterator<Map.Entry<Long, AlcsCoAPContext>> it = map.entrySet().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Map.Entry<Long, AlcsCoAPContext> next = it.next();
            if (next.getValue().getPort() == i2) {
                jLongValue = next.getKey().longValue();
                break;
            }
        }
        ALog.d(TAG, "getContextByPort port:" + i2 + " contextId:" + jLongValue);
        return jLongValue;
    }

    public native boolean initAuth(long j2, String str, String str2, int i2);

    protected void initOptionSet(long j2, long j3, OptionSet optionSet) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        List<Option> listAsSortedList;
        if (optionSet == null || (listAsSortedList = optionSet.asSortedList()) == null || listAsSortedList.isEmpty()) {
            return;
        }
        for (Option option : listAsSortedList) {
            if (TextUtils.isEmpty(option.getStringValue())) {
                ALog.e(TAG, "initOptionSet stringvalue empty");
            } else {
                addStringOption(j2, j3, option.getNumber(), option.getStringValue());
            }
        }
    }

    protected native long initRequest(long j2, AlcsCoAPRequest alcsCoAPRequest);

    protected native long initResponse(long j2, AlcsCoAPResponse alcsCoAPResponse);

    public boolean isServerDevOnline(long j2, String str, int i2, String str2, String str3) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        boolean zIsServerOnLine = isServerOnLine(j2, str, i2, str2, str3);
        ALog.d(TAG, "isServerDevOnline coapContextId:" + j2 + " ip:" + str + " port:" + i2 + " pk:" + str2 + " dn:" + str3 + " ret:" + zIsServerOnLine);
        return zIsServerOnLine;
    }

    protected native boolean isServerOnLine(long j2, String str, int i2, String str2, String str3);

    public native boolean notifyObserve(long j2, String str, byte[] bArr);

    protected void putRequestCallback(long j2, long j3, IAlcsCoAPReqHandler iAlcsCoAPReqHandler) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "putRequestCallback coapContextId: " + j2 + " msgId:" + j3);
        Map<Long, IAlcsCoAPReqHandler> map = mReqHandlerList.get(Long.valueOf(j2));
        if (map == null) {
            map = new HashMap<>();
            mReqHandlerList.put(Long.valueOf(j2), map);
        }
        map.put(Long.valueOf(j3), iAlcsCoAPReqHandler);
    }

    public void registerAllResource(long j2, AlcsCoAPResource alcsCoAPResource, String str, String str2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (alcsCoAPResource == null || TextUtils.isEmpty(alcsCoAPResource.getPath())) {
            ALog.e(TAG, "registerAllResource resource null");
            return;
        }
        registerResource(j2, alcsCoAPResource, str, str2);
        mResourceList.put(alcsCoAPResource.getPath(), alcsCoAPResource);
        Collection<Resource> children = alcsCoAPResource.getChildren();
        if (children == null || children.isEmpty()) {
            return;
        }
        Iterator<Resource> it = children.iterator();
        while (it.hasNext()) {
            registerResource(j2, (AlcsCoAPResource) it.next(), str, str2);
        }
    }

    protected native long registerResource(long j2, AlcsCoAPResource alcsCoAPResource, String str, String str2);

    public boolean removeAlcsSvrKey(long j2, String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        boolean zRemoveSvrKey = removeSvrKey(j2, str);
        ALog.d(TAG, "removeAlcsSvrKey coapContextId:" + j2 + " prefix:" + str + " ret:" + zRemoveSvrKey);
        return zRemoveSvrKey;
    }

    protected native boolean removeKey(String str, int i2, int i3);

    protected native boolean removeSvrKey(long j2, String str);

    protected native boolean sendAlcsRequest(long j2, long j3, String str, int i2);

    protected native boolean sendAlcsRequestSecure(long j2, long j3, String str, int i2, String str2, String str3);

    protected native boolean sendAlcsResponse(long j2, long j3, String str, int i2);

    protected native boolean sendAlcsResponseSecure(long j2, long j3, String str, int i2, String str2, String str3);

    public boolean sendObserveResponse(long j2, AlcsCoAPRequest alcsCoAPRequest, AlcsCoAPResponse alcsCoAPResponse) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "sendObserveResponse coapContextId:" + j2);
        return notifyObserve(j2, alcsCoAPRequest.getURI(), alcsCoAPResponse.getPayload());
    }

    public long sendRequest(long j2, AlcsCoAPRequest alcsCoAPRequest, IAlcsCoAPReqHandler iAlcsCoAPReqHandler) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "sendRequest coapContextId:" + j2 + " requestCallback:" + iAlcsCoAPReqHandler);
        long jInitRequest = initRequest(j2, alcsCoAPRequest);
        putRequestCallback(j2, jInitRequest, iAlcsCoAPReqHandler);
        initOptionSet(j2, jInitRequest, alcsCoAPRequest.getOptions());
        sendAlcsRequest(j2, jInitRequest, alcsCoAPRequest.getDestination().getHostAddress(), alcsCoAPRequest.getDestinationPort());
        unInitMessage(j2, jInitRequest);
        return jInitRequest;
    }

    public long sendRequestS(long j2, AlcsCoAPRequest alcsCoAPRequest, String str, String str2, IAlcsCoAPReqHandler iAlcsCoAPReqHandler) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "sendRequests coapContextId:" + j2 + " requestCallback:" + iAlcsCoAPReqHandler);
        long jInitRequest = initRequest(j2, alcsCoAPRequest);
        putRequestCallback(j2, jInitRequest, iAlcsCoAPReqHandler);
        initOptionSet(j2, jInitRequest, alcsCoAPRequest.getOptions());
        sendAlcsRequestSecure(j2, jInitRequest, alcsCoAPRequest.getDestination().getHostAddress(), alcsCoAPRequest.getDestinationPort(), str, str2);
        unInitMessage(j2, jInitRequest);
        return jInitRequest;
    }

    public boolean sendResponse(long j2, AlcsCoAPResponse alcsCoAPResponse) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        long jInitResponse = initResponse(j2, alcsCoAPResponse);
        ALog.d(TAG, "sendResponse coapContextId:" + j2 + " msgId:" + jInitResponse);
        initOptionSet(j2, jInitResponse, alcsCoAPResponse.getOptions());
        sendAlcsResponse(j2, jInitResponse, alcsCoAPResponse.getDestination().getHostAddress(), alcsCoAPResponse.getDestinationPort());
        unInitMessage(j2, jInitResponse);
        return true;
    }

    public boolean sendResponseS(long j2, AlcsCoAPResponse alcsCoAPResponse, String str, String str2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "sendResponse coapContextId:" + j2);
        long jInitResponse = initResponse(j2, alcsCoAPResponse);
        initOptionSet(j2, jInitResponse, alcsCoAPResponse.getOptions());
        sendAlcsResponseSecure(j2, jInitResponse, alcsCoAPResponse.getDestination().getHostAddress(), alcsCoAPResponse.getDestinationPort(), str, str2);
        unInitMessage(j2, jInitResponse);
        return true;
    }

    public void setLogLevel(int i2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "setNativeLogLevel logLevel:" + i2);
        setLogLevelNative(i2);
    }

    protected native void unInitMessage(long j2, long j3);

    protected native long unRegisterResource(long j2, String str);

    public void unRegisterResource(long j2, AlcsCoAPResource alcsCoAPResource) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        unRegisterResourceByPath(j2, alcsCoAPResource.getPath());
    }

    public long unRegisterResourceByPath(long j2, String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "unRegisterResourceByPath contextId:" + j2 + " path:" + str);
        return unRegisterResource(j2, str);
    }

    public boolean updateAlcsSvrBlackList(long j2, String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        boolean zUpdateSvrBlackList = updateSvrBlackList(j2, str);
        ALog.d(TAG, "updateAlcsSvrBlackList coapContextId:" + j2 + " blackList:" + str);
        return zUpdateSvrBlackList;
    }

    protected native boolean updateSvrBlackList(long j2, String str);

    public void registerAllResource(long j2, AlcsCoAPResource alcsCoAPResource) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        registerAllResource(j2, alcsCoAPResource, null, null);
    }
}
