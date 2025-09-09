package com.aliyun.alink.linksdk.alcs.pal.ica;

import com.aliyun.alink.linksdk.alcs.api.ICAConnectListener;
import com.aliyun.alink.linksdk.alcs.api.ICADisconnectListener;
import com.aliyun.alink.linksdk.alcs.api.ICAMsgListener;
import com.aliyun.alink.linksdk.alcs.api.ICAProbeListener;
import com.aliyun.alink.linksdk.alcs.data.ica.ICAAuthParams;
import com.aliyun.alink.linksdk.alcs.data.ica.ICAConnectParams;
import com.aliyun.alink.linksdk.alcs.data.ica.ICADeviceInfo;
import com.aliyun.alink.linksdk.alcs.data.ica.ICAGroupReqMessage;
import com.aliyun.alink.linksdk.alcs.data.ica.ICAInitData;
import com.aliyun.alink.linksdk.alcs.data.ica.ICAReqMessage;
import com.aliyun.alink.linksdk.alcs.data.ica.ICARspMessage;
import com.aliyun.alink.linksdk.alcs.data.ica.ICASubMessage;
import com.aliyun.alink.linksdk.alcs.data.ica.ICAUTConnectListener;
import com.aliyun.alink.linksdk.alcs.data.ica.ICAUTPointEx;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class ICAAlcsNative {
    public static final int ALCS_DISCOVERY_TYPE_FINISH = 0;
    public static final int ALCS_DISCOVERY_TYPE_FOUND = 1;
    public static final int ALCS_MSG_CODE_DELETE = 4;
    public static final int ALCS_MSG_CODE_GET = 1;
    public static final int ALCS_MSG_CODE_POST = 2;
    public static final int ALCS_MSG_CODE_PUT = 3;
    public static final int ALCS_MSG_TYPE_ACK = 2;
    public static final int ALCS_MSG_TYPE_CON = 0;
    public static final int ALCS_MSG_TYPE_NON = 1;
    public static final int ALCS_MSG_TYPE_RST = 3;
    public static final int ALCS_RESULT_DUPLICATE = 5;
    public static final int ALCS_RESULT_FAIL = 1;
    public static final int ALCS_RESULT_INSUFFICIENT_MEM = 2;
    public static final int ALCS_RESULT_INVALIDPARAM = 4;
    public static final int ALCS_RESULT_NOTFOUND = 3;
    public static final int ALCS_RESULT_OK = 0;
    public static final int ALCS_ROLE_BOTH = 2;
    public static final int ALCS_ROLE_CLIENT = 0;
    public static final int ALCS_ROLE_SERVER = 1;
    private static final String TAG = "[alcs_coap_sdk]ICAAlcsNative";

    static {
        System.loadLibrary("coap");
    }

    public static void connectDevice(String str, int i2, ICAConnectParams iCAConnectParams, ICAConnectListener iCAConnectListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ICADeviceInfo iCADeviceInfo = iCAConnectParams.deviceInfo;
        ICAUTConnectListener iCAUTConnectListener = new ICAUTConnectListener(iCAConnectListener, new ICAUTPointEx(iCADeviceInfo.productKey, iCADeviceInfo.deviceName));
        int iConnectDeviceNative = connectDeviceNative(str, i2, iCAConnectParams.deviceInfo, iCAConnectParams.authInfo, iCAUTConnectListener);
        ALog.d(TAG, "connectDevice ret:" + iConnectDeviceNative + " listener:" + iCAConnectListener);
        if (iConnectDeviceNative >= 0 || iCAConnectListener == null) {
            return;
        }
        iCAUTConnectListener.onLoad(iConnectDeviceNative, "connect fail", iCAConnectParams.deviceInfo);
    }

    protected static native int connectDeviceNative(String str, int i2, ICADeviceInfo iCADeviceInfo, ICAAuthParams iCAAuthParams, ICAConnectListener iCAConnectListener);

    public static void deInitPal() {
        deInitPalNative();
    }

    protected static native void deInitPalNative();

    public static void disConnectDevice(ICADeviceInfo iCADeviceInfo) {
        disConnectDeviceNative(iCADeviceInfo);
    }

    protected static native int disConnectDeviceNative(ICADeviceInfo iCADeviceInfo);

    public static boolean discoveryDevice(int i2, ICADiscoveryListener iCADiscoveryListener) {
        return discoveryDeviceNative(i2, iCADiscoveryListener) == 0;
    }

    protected static native int discoveryDeviceNative(int i2, ICADiscoveryListener iCADiscoveryListener);

    public static void initPal(ICAInitData iCAInitData) {
        initPalNative(iCAInitData.deviceInfo, iCAInitData.role);
    }

    protected static native int initPalNative(ICADeviceInfo iCADeviceInfo, int i2);

    public static boolean isDeviceOnline(ICADeviceInfo iCADeviceInfo) {
        return isDeviceOnlineNative(iCADeviceInfo);
    }

    protected static native boolean isDeviceOnlineNative(ICADeviceInfo iCADeviceInfo);

    public static void probeDevice(String str, int i2, ICADeviceInfo iCADeviceInfo, ICAProbeListener iCAProbeListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        int iProbeDeviceNative = probeDeviceNative(str, i2, iCADeviceInfo, iCAProbeListener);
        ALog.d(TAG, "probeDevice ret:" + iProbeDeviceNative + " listener:" + iCAProbeListener);
        if (iProbeDeviceNative >= 0 || iCAProbeListener == null) {
            return;
        }
        iCAProbeListener.onComplete(iCADeviceInfo, iProbeDeviceNative);
    }

    protected static native int probeDeviceNative(String str, int i2, ICADeviceInfo iCADeviceInfo, ICAProbeListener iCAProbeListener);

    public static boolean regDeviceNotifyListener(ICADiscoveryListener iCADiscoveryListener) {
        return regDeviceNotifyListenerNative(iCADiscoveryListener) == 0;
    }

    protected static native int regDeviceNotifyListenerNative(ICADiscoveryListener iCADiscoveryListener);

    public static boolean removeDeviceDisconnectListener(ICADeviceInfo iCADeviceInfo) {
        return removeDeviceDisconnectListenerNative(iCADeviceInfo);
    }

    protected static native boolean removeDeviceDisconnectListenerNative(ICADeviceInfo iCADeviceInfo);

    public static boolean sendGroupRequest(ICAGroupReqMessage iCAGroupReqMessage, ICAAuthParams iCAAuthParams, ICAMsgListener iCAMsgListener) {
        int iSendGroupRequestNative = sendGroupRequestNative(iCAGroupReqMessage, iCAAuthParams, iCAMsgListener);
        if (iSendGroupRequestNative < 0 && iCAMsgListener != null) {
            iCAMsgListener.onLoad(new ICARspMessage(iSendGroupRequestNative));
        }
        return iSendGroupRequestNative == 0;
    }

    protected static native int sendGroupRequestNative(ICAGroupReqMessage iCAGroupReqMessage, ICAAuthParams iCAAuthParams, ICAMsgListener iCAMsgListener);

    public static boolean sendRequest(ICAReqMessage iCAReqMessage, ICAMsgListener iCAMsgListener) {
        int iSendRequestNative = sendRequestNative(iCAReqMessage, iCAMsgListener);
        if (iSendRequestNative < 0 && iCAMsgListener != null) {
            iCAMsgListener.onLoad(new ICARspMessage(iSendRequestNative));
        }
        return iSendRequestNative == 0;
    }

    protected static native int sendRequestNative(ICAReqMessage iCAReqMessage, ICAMsgListener iCAMsgListener);

    public static boolean sendResponse(ICARspMessage iCARspMessage, int i2, ICAMsgListener iCAMsgListener) {
        int iSendResponseNative = sendResponseNative(iCARspMessage, i2, iCAMsgListener);
        if (iSendResponseNative < 0 && iCAMsgListener != null) {
            iCAMsgListener.onLoad(new ICARspMessage(iSendResponseNative));
        }
        return iSendResponseNative == 0;
    }

    protected static native int sendResponseNative(ICARspMessage iCARspMessage, int i2, ICAMsgListener iCAMsgListener);

    public static boolean setDeviceDisconnectListener(ICADeviceInfo iCADeviceInfo, ICADisconnectListener iCADisconnectListener) {
        return setDeviceDisconnectListenerNative(iCADeviceInfo, iCADisconnectListener);
    }

    protected static native boolean setDeviceDisconnectListenerNative(ICADeviceInfo iCADeviceInfo, ICADisconnectListener iCADisconnectListener);

    public static boolean stopDiscoveryDevice() {
        return stopDiscoveryDeviceNative() == 0;
    }

    protected static native int stopDiscoveryDeviceNative();

    public static boolean subcribe(ICASubMessage iCASubMessage, ICAMsgListener iCAMsgListener, ICAMsgListener iCAMsgListener2) {
        int iSubcribeNative = subcribeNative(iCASubMessage, iCAMsgListener, iCAMsgListener2);
        if (iSubcribeNative < 0 && iCAMsgListener != null) {
            iCAMsgListener.onLoad(new ICARspMessage(iSubcribeNative));
        }
        return iSubcribeNative == 0;
    }

    protected static native int subcribeNative(ICASubMessage iCASubMessage, ICAMsgListener iCAMsgListener, ICAMsgListener iCAMsgListener2);

    public static boolean unregDeviceNotifyListener() {
        return regDeviceNotifyListenerNative(null) == 0;
    }

    public static boolean unsubcribe(ICASubMessage iCASubMessage, ICAMsgListener iCAMsgListener) {
        int iUnsubcribeNative = unsubcribeNative(iCASubMessage, iCAMsgListener);
        if (iUnsubcribeNative < 0 && iCAMsgListener != null) {
            iCAMsgListener.onLoad(new ICARspMessage(iUnsubcribeNative));
        }
        return iUnsubcribeNative == 0;
    }

    protected static native int unsubcribeNative(ICASubMessage iCASubMessage, ICAMsgListener iCAMsgListener);
}
