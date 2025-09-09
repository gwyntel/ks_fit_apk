package com.aliyun.alink.linksdk.lpbs.lpbstgmesh;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.alibaba.ailabs.iot.mesh.AuthInfoListener;
import com.alibaba.ailabs.iot.mesh.MeshStatusCallback;
import com.alibaba.ailabs.iot.mesh.TgMeshManager;
import com.alibaba.ailabs.iot.mesh.bean.MeshAccessPayload;
import com.alibaba.ailabs.iot.mesh.callback.IActionListener;
import com.alibaba.ailabs.iot.mesh.callback.MeshMsgListener;
import com.alibaba.ailabs.iot.mesh.managers.MeshDeviceInfoManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect;
import com.aliyun.alink.linksdk.alcs.lpbs.component.cloud.ILpbsCloudProxy;
import com.aliyun.alink.linksdk.alcs.lpbs.component.cloud.IThingCloudChannel;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalConnectParams;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalDeviceInfo;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalReqMessage;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalRspMessage;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalSubMessage;
import com.aliyun.alink.linksdk.alcs.lpbs.data.cloud.TgMeshConvertParams;
import com.aliyun.alink.linksdk.alcs.lpbs.listener.PalConnectListener;
import com.aliyun.alink.linksdk.alcs.lpbs.listener.PalDeviceStateListener;
import com.aliyun.alink.linksdk.alcs.lpbs.listener.PalMsgListener;
import com.aliyun.alink.linksdk.lpbs.lpbstgmesh.data.TgMeshExtraConnectParams;
import com.aliyun.alink.linksdk.lpbs.lpbstgmesh.data.TgMeshReqExtraData;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.aliyun.alink.linksdk.tools.ALog;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import datasource.bean.SigmeshIotDev;
import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.List;
import org.spongycastle.util.encoders.Hex;

/* loaded from: classes2.dex */
public class Connect implements IPalConnect {
    private static final String TAG = Utils.TAG + "Connect";
    protected Context mContext;
    private ILpbsCloudProxy mLpbsCloudProxy;
    private PalDeviceInfo mPalDeviceInfo;
    private PalDeviceStateListener mPalDeviceStateListener;
    private PalMsgListener mUploaderListener;
    protected int mMeshStatus = -1;
    protected Handler mHandler = new Handler(Looper.getMainLooper());
    private MeshMsgListener mMeshMsgListener = new MeshMsgListener() { // from class: com.aliyun.alink.linksdk.lpbs.lpbstgmesh.Connect.1
        @Override // com.alibaba.ailabs.iot.mesh.callback.MeshMsgListener
        public void onReceiveMeshMessage(byte[] bArr, MeshAccessPayload meshAccessPayload) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            ALog.d(Connect.TAG, "onReceiveMeshMessage bytes:" + bArr + ";opcode:" + meshAccessPayload.getOpCode());
            SigmeshIotDev sigmeshIotDevQueryDeviceInfoByUnicastAddress = MeshDeviceInfoManager.getInstance().queryDeviceInfoByUnicastAddress(ByteBuffer.wrap(bArr).order(ByteOrder.BIG_ENDIAN).getShort(), meshAccessPayload.getNetKey());
            PalRspMessage palRspMessage = new PalRspMessage(0);
            JSONObject jSONObject = new JSONObject();
            if (sigmeshIotDevQueryDeviceInfoByUnicastAddress != null) {
                jSONObject.put("devId", (Object) sigmeshIotDevQueryDeviceInfoByUnicastAddress.getDevId());
            }
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put(PushConstants.PARAMS, (Object) Hex.toHexString(meshAccessPayload.getParameters()));
            jSONObject.put("meshPayload", (Object) jSONObject2);
            palRspMessage.payload = jSONObject.toString().getBytes();
            if (Connect.this.mUploaderListener != null) {
                Connect.this.mUploaderListener.onLoad(palRspMessage);
            }
        }
    };
    private MeshStatusCallback mMeshStatusCallback = new MeshStatusCallback() { // from class: com.aliyun.alink.linksdk.lpbs.lpbstgmesh.Connect.2
        @Override // com.alibaba.ailabs.iot.mesh.StatusCallback
        public void onStatus(int i2, String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            ALog.d(Connect.TAG, "onStatus status:" + i2 + " message:" + str + " mPalDeviceStateListener:" + Connect.this.mPalDeviceStateListener);
            Connect connect = Connect.this;
            connect.mMeshStatus = i2;
            if (i2 == -2) {
                if (connect.mPalDeviceStateListener != null) {
                    Connect.this.mPalDeviceStateListener.onDeviceStateChange(Connect.this.mPalDeviceInfo, 0);
                }
            } else if (i2 == 2) {
                if (connect.mPalDeviceStateListener != null) {
                    Connect.this.mPalDeviceStateListener.onDeviceStateChange(Connect.this.mPalDeviceInfo, 1);
                }
            } else if (i2 == 8 && connect.mPalDeviceStateListener != null) {
                Connect.this.mPalDeviceStateListener.onDeviceStateChange(Connect.this.mPalDeviceInfo, 2);
            }
        }
    };

    public Connect(Context context, PalDeviceInfo palDeviceInfo, ILpbsCloudProxy iLpbsCloudProxy) {
        this.mPalDeviceInfo = palDeviceInfo;
        this.mLpbsCloudProxy = iLpbsCloudProxy;
        this.mContext = context;
        TgMeshManager.getInstance().registerMeshMessageListener(this.mMeshMsgListener);
        TgMeshManager.getInstance().registerCallback(this.mMeshStatusCallback);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect
    public boolean asyncSendRequest(PalReqMessage palReqMessage, final PalMsgListener palMsgListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        JSONObject jSONObject;
        String str = TAG;
        ALog.d(str, "asyncSendRequest palReqMessage:" + palReqMessage + " palMsgListener:" + palMsgListener);
        if (palReqMessage == null) {
            ALog.e(str, "asyncSendRequest palReqMessage empty");
            if (palMsgListener != null) {
                palMsgListener.onLoad(new PalRspMessage(1));
            }
            return false;
        }
        if (palReqMessage.palOptions != null) {
            ExtraReqData extrareqdata = palReqMessage.extraReqData;
            if (extrareqdata instanceof TgMeshReqExtraData) {
                TgMeshReqExtraData tgMeshReqExtraData = (TgMeshReqExtraData) extrareqdata;
                if (this.mLpbsCloudProxy == null) {
                    PalRspMessage palRspMessage = new PalRspMessage(1);
                    if (palMsgListener != null) {
                        palMsgListener.onLoad(palRspMessage);
                    }
                    return false;
                }
                try {
                    jSONObject = JSON.parseObject(new String(palReqMessage.payload, "UTF-8")).getJSONObject("params");
                } catch (Exception e2) {
                    ALog.e(TAG, "payload jsparse error:" + e2.toString());
                    jSONObject = null;
                }
                if (jSONObject == null) {
                    PalRspMessage palRspMessage2 = new PalRspMessage(1);
                    palRspMessage2.payload = "Invalid parameters. property value can not be null!".getBytes();
                    if (palMsgListener != null) {
                        palMsgListener.onLoad(palRspMessage2);
                    }
                    return false;
                }
                TgMeshConvertParams tgMeshConvertParams = new TgMeshConvertParams();
                tgMeshConvertParams.productKey = this.mPalDeviceInfo.productModel;
                tgMeshConvertParams.method = tgMeshReqExtraData.method;
                String str2 = tgMeshReqExtraData.iotId;
                tgMeshConvertParams.iotId = str2;
                tgMeshConvertParams.params = jSONObject;
                String jSONString = JSON.toJSONString(jSONObject);
                HashMap map = new HashMap();
                map.put("iotId", tgMeshConvertParams.iotId);
                map.put("productKey", tgMeshConvertParams.productKey);
                TgMeshManager.getInstance().sendMessge(str2, 0, "thing.attribute.set", jSONString, map, new IActionListener() { // from class: com.aliyun.alink.linksdk.lpbs.lpbstgmesh.Connect.4
                    @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
                    public void onFailure(int i2, String str3) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                        ALog.d(Connect.TAG, "TgMeshManager sendMessge onFailure erorcode:" + i2 + " errormsg:" + str3);
                        PalRspMessage palRspMessage3 = new PalRspMessage(1);
                        palRspMessage3.payload = TextUtils.isEmpty(str3) ? null : str3.getBytes();
                        PalMsgListener palMsgListener2 = palMsgListener;
                        if (palMsgListener2 != null) {
                            palMsgListener2.onLoad(palRspMessage3);
                        }
                    }

                    @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
                    public void onSuccess(Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                        ALog.d(Connect.TAG, "TgMeshManager sendMessge onSuccess result:" + obj);
                        PalRspMessage palRspMessage3 = new PalRspMessage(0);
                        JSONObject jSONObject2 = new JSONObject();
                        jSONObject2.put("code", (Object) 200);
                        try {
                            palRspMessage3.payload = jSONObject2.toString().getBytes();
                        } catch (Exception e3) {
                            ALog.e(Connect.TAG, "sendMessge toString e:" + e3.toString());
                        }
                        PalMsgListener palMsgListener2 = palMsgListener;
                        if (palMsgListener2 != null) {
                            palMsgListener2.onLoad(palRspMessage3);
                        }
                    }
                });
                return true;
            }
        }
        ALog.e(str, "asyncSendRequest palOptions error :" + palReqMessage.palOptions);
        if (palMsgListener != null) {
            palMsgListener.onLoad(new PalRspMessage(1));
        }
        return false;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect
    public int getConnectType(PalDeviceInfo palDeviceInfo) {
        return 5;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect
    public String getPluginId() {
        return Utils.LPBS_TGMESH_PLUGINID;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect
    public boolean isDeviceConnected(PalDeviceInfo palDeviceInfo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "isDeviceConnected ret");
        return true;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect
    public void onCloudChannelCreate(IThingCloudChannel iThingCloudChannel) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "onCloudChannelCreate iThingCloudChannel return");
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect
    public boolean regDeviceStateListener(PalDeviceInfo palDeviceInfo, PalDeviceStateListener palDeviceStateListener) {
        this.mPalDeviceStateListener = palDeviceStateListener;
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect
    public void startConnect(PalConnectParams palConnectParams, PalConnectListener palConnectListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        List<String> list;
        String str = TAG;
        ALog.d(str, "startConnect isInitialized:" + TgMeshManager.getInstance().isInitialized() + " palConnectParams:" + palConnectParams + " palConnectListener:" + palConnectListener);
        if (!TgMeshManager.getInstance().isInitialized()) {
            TgMeshManager.getInstance().init(this.mContext, new AuthInfoListener() { // from class: com.aliyun.alink.linksdk.lpbs.lpbstgmesh.Connect.3
                @Override // com.alibaba.ailabs.iot.mesh.AuthInfoListener
                public String getAuthInfo() {
                    return "";
                }
            });
            if (palConnectListener != null) {
                palConnectListener.onLoad(1, null, this.mPalDeviceInfo);
                return;
            }
            return;
        }
        if (palConnectParams == null) {
            ALog.e(str, "palConnectParams empty");
            if (palConnectListener != null) {
                palConnectListener.onLoad(1, null, this.mPalDeviceInfo);
                return;
            }
            return;
        }
        ExtraConnectparams extraconnectparams = palConnectParams.extraConnectParams;
        if (extraconnectparams == 0 || !(extraconnectparams instanceof TgMeshExtraConnectParams)) {
            ALog.e(str, "palConnectParams extraConnectParams empty or method error");
            if (palConnectListener != null) {
                palConnectListener.onLoad(1, null, this.mPalDeviceInfo);
                return;
            }
            return;
        }
        palConnectListener.onLoad(0, null, this.mPalDeviceInfo);
        TgMeshExtraConnectParams tgMeshExtraConnectParams = (TgMeshExtraConnectParams) palConnectParams.extraConnectParams;
        if (tgMeshExtraConnectParams != null && (list = tgMeshExtraConnectParams.whiteList) != null && !list.isEmpty()) {
            TgMeshManager.getInstance().setMacAddressWhiteList(tgMeshExtraConnectParams.whiteList);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("TgMeshManager connect unicastAddresses：");
        Object obj = TmpConstant.GROUP_ROLE_UNKNOWN;
        sb.append(tgMeshExtraConnectParams == null ? TmpConstant.GROUP_ROLE_UNKNOWN : tgMeshExtraConnectParams.unicastAddresses);
        sb.append(" whiteList:");
        if (tgMeshExtraConnectParams != null) {
            obj = tgMeshExtraConnectParams.whiteList;
        }
        sb.append(obj);
        ALog.d(str, sb.toString());
        TgMeshManager.getInstance().connect(tgMeshExtraConnectParams.unicastAddresses);
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect
    public void stopConnect(PalDeviceInfo palDeviceInfo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "stopConnect palDeviceInfo:" + palDeviceInfo);
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect
    public boolean subscribe(PalSubMessage palSubMessage, PalMsgListener palMsgListener, PalMsgListener palMsgListener2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.w(TAG, "subscribe palSubMessage:" + palSubMessage + " subListener:" + palMsgListener + " uploaderListener:" + palMsgListener2);
        this.mUploaderListener = palMsgListener2;
        if (palMsgListener != null) {
            PalRspMessage palRspMessage = new PalRspMessage();
            palRspMessage.code = 0;
            palRspMessage.payload = palSubMessage.payload;
            palMsgListener.onLoad(palRspMessage);
        }
        return false;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect
    public boolean unregDeviceStateListener(PalDeviceInfo palDeviceInfo, PalDeviceStateListener palDeviceStateListener) {
        this.mPalDeviceStateListener = null;
        return true;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect
    public boolean unsubscribe(PalSubMessage palSubMessage, PalMsgListener palMsgListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.w(TAG, "unsubscribe palSubMessage:" + palSubMessage + " palMsgListener:" + palMsgListener);
        return false;
    }
}
