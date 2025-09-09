package com.aliyun.alink.linksdk.cmp.connect.alcs;

import android.content.Context;
import android.text.TextUtils;
import com.aliyun.alink.linksdk.alcs.api.server.AlcsServerConfig;
import com.aliyun.alink.linksdk.alcs.coap.AlcsCoAPConstant;
import com.aliyun.alink.linksdk.alcs.coap.AlcsCoAPContext;
import com.aliyun.alink.linksdk.alcs.coap.AlcsCoAPRequest;
import com.aliyun.alink.linksdk.alcs.coap.AlcsCoAPResponse;
import com.aliyun.alink.linksdk.alcs.coap.IAlcsCoAPResHandler;
import com.aliyun.alink.linksdk.alcs.coap.resources.AlcsCoAPResource;
import com.aliyun.alink.linksdk.cmp.api.CommonResource;
import com.aliyun.alink.linksdk.cmp.api.ResourceRequest;
import com.aliyun.alink.linksdk.cmp.connect.alcs.AlcsServerConnectOption;
import com.aliyun.alink.linksdk.cmp.core.base.AConnectConfig;
import com.aliyun.alink.linksdk.cmp.core.base.AConnectOption;
import com.aliyun.alink.linksdk.cmp.core.base.AMultiportConnect;
import com.aliyun.alink.linksdk.cmp.core.base.ARequest;
import com.aliyun.alink.linksdk.cmp.core.base.AResource;
import com.aliyun.alink.linksdk.cmp.core.base.AResponse;
import com.aliyun.alink.linksdk.cmp.core.base.CmpError;
import com.aliyun.alink.linksdk.cmp.core.base.ConnectState;
import com.aliyun.alink.linksdk.cmp.core.listener.IBaseListener;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectAuth;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectInitListener;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectNotifyListener;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectResourceRegister;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectSubscribeListener;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectUnscribeListener;
import com.aliyun.alink.linksdk.cmp.core.listener.IResourceRequestListener;
import com.aliyun.alink.linksdk.cmp.core.listener.IResourceResponseListener;
import com.aliyun.alink.linksdk.cmp.core.util.CallbackHelper;
import com.aliyun.alink.linksdk.cmp.core.util.ClassSwitchHelper;
import com.aliyun.alink.linksdk.tools.AError;
import com.aliyun.alink.linksdk.tools.ALog;
import com.aliyun.linksdk.alcs.AlcsCmpSDK;
import com.aliyun.linksdk.alcs.IAlcsServer;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/* loaded from: classes2.dex */
public class AlcsServerConnect extends AMultiportConnect implements IConnectResourceRegister, IResourceResponseListener, IConnectAuth<Map<String, String>> {
    public static final String CONNECT_ID = "LINK_ALCS_MULTIPORT";
    private static final String TAG = "AlcsMultiportConnect";
    private IAlcsServer alcsServer = null;
    private AlcsServerConnectConfig config;
    private Context context;
    private IConnectInitListener initListener;

    /* renamed from: com.aliyun.alink.linksdk.cmp.connect.alcs.AlcsServerConnect$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$aliyun$alink$linksdk$cmp$connect$alcs$AlcsServerConnectOption$OptionFlag;

        static {
            int[] iArr = new int[AlcsServerConnectOption.OptionFlag.values().length];
            $SwitchMap$com$aliyun$alink$linksdk$cmp$connect$alcs$AlcsServerConnectOption$OptionFlag = iArr;
            try {
                iArr[AlcsServerConnectOption.OptionFlag.ADD_PREFIX_SECRET.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$aliyun$alink$linksdk$cmp$connect$alcs$AlcsServerConnectOption$OptionFlag[AlcsServerConnectOption.OptionFlag.DELETE_PREFIX.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$aliyun$alink$linksdk$cmp$connect$alcs$AlcsServerConnectOption$OptionFlag[AlcsServerConnectOption.OptionFlag.UPDATE_BLACK_LIST.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private class AlcsCoAPResHandler implements IAlcsCoAPResHandler {
        private AResource resource;
        private IResourceRequestListener resourceRequestListener;
        private String topic;

        public AlcsCoAPResHandler(AResource aResource, String str, IResourceRequestListener iResourceRequestListener) {
            this.resource = aResource;
            this.topic = str;
            this.resourceRequestListener = iResourceRequestListener;
        }

        @Override // com.aliyun.alink.linksdk.alcs.coap.IAlcsCoAPResHandler
        public void onRecRequest(AlcsCoAPContext alcsCoAPContext, AlcsCoAPRequest alcsCoAPRequest) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            ALog.d(AlcsServerConnect.TAG, "onRecRequest(),topic = " + this.topic);
            if (this.resourceRequestListener == null) {
                return;
            }
            ResourceRequest resourceRequest = new ResourceRequest();
            resourceRequest.topic = this.topic;
            if (alcsCoAPContext != null) {
                resourceRequest.payloadObj = alcsCoAPRequest.getPayload();
            }
            resourceRequest.context = alcsCoAPRequest;
            this.resourceRequestListener.onHandleRequest(this.resource, resourceRequest, AlcsServerConnect.this);
        }
    }

    private void initAndStart() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "initAndStart()");
        try {
            AlcsCmpSDK.init(this.context);
            AlcsServerConfig.Builder builder = new AlcsServerConfig.Builder();
            builder.setProdKey(this.config.getProductKey());
            builder.setDevcieName(this.config.getDeviceName());
            builder.setBlackList(this.config.getBlackClients());
            builder.addPrefixSec(this.config.getPrefix(), this.config.getSecret());
            builder.setUnsafePort(5683);
            IAlcsServer iAlcsServerInitServer = AlcsCmpSDK.initServer(builder.build());
            this.alcsServer = iAlcsServerInitServer;
            iAlcsServerInitServer.startServer();
            IConnectInitListener iConnectInitListener = this.initListener;
            if (iConnectInitListener != null) {
                iConnectInitListener.onSuccess();
            }
            updateConnectState(ConnectState.CONNECTED);
        } catch (Exception e2) {
            ALog.d(TAG, "init(),error");
            e2.printStackTrace();
            if (this.initListener != null) {
                CmpError cmpErrorALCS_INIT_MULTIPORT_FAIL = CmpError.ALCS_INIT_MULTIPORT_FAIL();
                cmpErrorALCS_INIT_MULTIPORT_FAIL.setSubMsg(e2.toString());
                this.initListener.onFailure(cmpErrorALCS_INIT_MULTIPORT_FAIL);
            }
            updateConnectState(ConnectState.CONNECTFAIL);
        }
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.base.AConnect
    public void init(Context context, AConnectConfig aConnectConfig, IConnectInitListener iConnectInitListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "init()");
        if (context == null || aConnectConfig == null || !(aConnectConfig instanceof AlcsServerConnectConfig) || !aConnectConfig.checkVaild()) {
            ALog.d(TAG, "init()，params error");
            CallbackHelper.paramError(iConnectInitListener, "init, cxt or config is invalid");
            return;
        }
        this.connectId = CONNECT_ID;
        this.context = context;
        this.initListener = iConnectInitListener;
        AlcsServerConnectConfig alcsServerConnectConfig = (AlcsServerConnectConfig) aConnectConfig;
        this.config = alcsServerConnectConfig;
        updateConnectState(ConnectState.CONNECTING);
        if (alcsServerConnectConfig.isNeedAuthInfo()) {
            iConnectInitListener.onPrepareAuth(this);
        } else {
            initAndStart();
        }
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.base.AConnect
    public void onDestroy() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "onDestroy()");
        IAlcsServer iAlcsServer = this.alcsServer;
        if (iAlcsServer != null) {
            iAlcsServer.stopServer();
        }
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectAuth
    public void onPrepareAuthFail(AError aError) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "onPrepareFail()");
        IConnectInitListener iConnectInitListener = this.initListener;
        if (iConnectInitListener != null) {
            iConnectInitListener.onFailure(aError);
        }
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.listener.IResourceResponseListener
    public void onResponse(AResource aResource, ResourceRequest resourceRequest, Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        boolean z2;
        boolean z3;
        AlcsCoAPResponse alcsCoAPResponse;
        ALog.d(TAG, "onResponse()");
        if (this.alcsServer == null) {
            ALog.d(TAG, "onResponse(),params error,alcsServer null, return");
            return;
        }
        if (aResource == null || !(((z2 = aResource instanceof CoAPResource)) || (aResource instanceof CommonResource))) {
            ALog.d(TAG, "onResponse(),params error,resource error, return");
            return;
        }
        if (resourceRequest == null) {
            ALog.d(TAG, "onResponse(),params error, resoucreReq is null");
            return;
        }
        Object obj2 = resourceRequest.context;
        if (obj2 == null || !(obj2 instanceof AlcsCoAPRequest)) {
            ALog.d(TAG, "onResponse(),params error,resoucre request context error, return" + resourceRequest.context);
            return;
        }
        if (obj == null || !(((z3 = obj instanceof AlcsCoAPResponse)) || (obj instanceof AResponse))) {
            ALog.d(TAG, "onResponse(),params error,responseerror, return");
            return;
        }
        if (z3) {
            alcsCoAPResponse = (AlcsCoAPResponse) obj;
        } else {
            AlcsCoAPResponse alcsCoAPResponseCreateResponse = AlcsCoAPResponse.createResponse((AlcsCoAPRequest) obj2, AlcsCoAPConstant.ResponseCode.CONTENT);
            Object data = ((AResponse) obj).getData();
            if (data instanceof String) {
                alcsCoAPResponseCreateResponse.setPayload((String) data);
            } else if (data instanceof byte[]) {
                alcsCoAPResponseCreateResponse.setPayload((byte[]) data);
            } else {
                try {
                    alcsCoAPResponseCreateResponse.setPayload(data.toString());
                } catch (Exception e2) {
                    ALog.w(TAG, "onResponse(), send , toString error," + e2.toString());
                    return;
                }
            }
            alcsCoAPResponse = alcsCoAPResponseCreateResponse;
        }
        boolean z4 = z2 ? ((CoAPResource) aResource).isNeedAuth : aResource instanceof CommonResource ? ((CommonResource) aResource).isNeedAuth : false;
        ALog.w(TAG, "onResponse(), exe sendResponse, isNeedAuth = " + z4);
        this.alcsServer.sendResponse(z4, alcsCoAPResponse);
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectResourceRegister
    public void publishResource(AResource aResource, IBaseListener iBaseListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "publishResource");
        if (this.alcsServer == null) {
            return;
        }
        boolean z2 = aResource instanceof CoAPResource;
        if (!z2 && !(aResource instanceof CommonResource)) {
            if (iBaseListener != null) {
                iBaseListener.onFailure(CmpError.UNSUPPORT());
            }
        } else {
            CoAPResource coAPResourceCommonResToCoapRes = z2 ? (CoAPResource) aResource : aResource instanceof CommonResource ? ClassSwitchHelper.commonResToCoapRes((CommonResource) aResource) : null;
            this.alcsServer.publishResoucre(coAPResourceCommonResToCoapRes.topic, coAPResourceCommonResToCoapRes.payload);
            if (iBaseListener != null) {
                iBaseListener.onSuccess();
            }
        }
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectResourceRegister
    public void registerResource(AResource aResource, IResourceRequestListener iResourceRequestListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "registerResource()");
        if (this.alcsServer == null) {
            return;
        }
        boolean z2 = aResource instanceof CoAPResource;
        if (!z2 && !(aResource instanceof CommonResource)) {
            if (iResourceRequestListener != null) {
                iResourceRequestListener.onFailure(CmpError.UNSUPPORT());
                return;
            }
            return;
        }
        CoAPResource coAPResourceCommonResToCoapRes = z2 ? (CoAPResource) aResource : aResource instanceof CommonResource ? ClassSwitchHelper.commonResToCoapRes((CommonResource) aResource) : null;
        AlcsCoAPResource alcsCoAPResource = new AlcsCoAPResource(coAPResourceCommonResToCoapRes.topic);
        alcsCoAPResource.setPath(coAPResourceCommonResToCoapRes.topic);
        alcsCoAPResource.setPermission(3);
        alcsCoAPResource.setHandler(new AlcsCoAPResHandler(aResource, coAPResourceCommonResToCoapRes.topic, iResourceRequestListener));
        this.alcsServer.registerAllResource(coAPResourceCommonResToCoapRes.isNeedAuth, alcsCoAPResource);
        if (iResourceRequestListener != null) {
            iResourceRequestListener.onSuccess();
        }
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.base.AConnect
    public void send(ARequest aRequest, IConnectSendListener iConnectSendListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "send()");
        if (iConnectSendListener != null) {
            iConnectSendListener.onFailure(aRequest, CmpError.UNSUPPORT());
        }
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.base.AConnect
    public void setNotifyListener(IConnectNotifyListener iConnectNotifyListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "setNotifyListener()");
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.base.AConnect
    public void subscribe(ARequest aRequest, IConnectSubscribeListener iConnectSubscribeListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "subscribe()");
        if (iConnectSubscribeListener != null) {
            iConnectSubscribeListener.onFailure(CmpError.UNSUPPORT());
        }
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectResourceRegister
    public void unregisterResource(AResource aResource, IBaseListener iBaseListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "unregisterResource()");
        if (iBaseListener != null) {
            iBaseListener.onFailure(CmpError.UNSUPPORT());
        }
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.base.AConnect
    public void unsubscribe(ARequest aRequest, IConnectUnscribeListener iConnectUnscribeListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "unsubscribe()");
        if (iConnectUnscribeListener != null) {
            iConnectUnscribeListener.onFailure(CmpError.UNSUPPORT());
        }
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.base.AConnect
    public void updateConnectOption(AConnectOption aConnectOption) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "updateConnectOption()");
        if (aConnectOption == null || !(aConnectOption instanceof AlcsServerConnectOption)) {
            return;
        }
        super.updateConnectOption(aConnectOption);
        if (this.alcsServer == null) {
            return;
        }
        AlcsServerConnectOption alcsServerConnectOption = (AlcsServerConnectOption) aConnectOption;
        String prefix = alcsServerConnectOption.getPrefix();
        String blackClients = alcsServerConnectOption.getBlackClients();
        String secrect = alcsServerConnectOption.getSecrect();
        int i2 = AnonymousClass1.$SwitchMap$com$aliyun$alink$linksdk$cmp$connect$alcs$AlcsServerConnectOption$OptionFlag[alcsServerConnectOption.getOptionFlag().ordinal()];
        if (i2 == 1) {
            if (TextUtils.isEmpty(prefix) || TextUtils.isEmpty(secrect)) {
                return;
            }
            this.alcsServer.addSvrAccessKey(prefix, secrect);
            return;
        }
        if (i2 != 2) {
            if (i2 != 3) {
                return;
            }
            this.alcsServer.updateBlackList(blackClients);
        } else {
            if (TextUtils.isEmpty(prefix)) {
                return;
            }
            this.alcsServer.removeSvrKey(prefix);
        }
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.listener.IConnectAuth
    public void onAuth(Map<String, String> map) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(TAG, "auth()");
        if (map != null && map.containsKey("PREFIX") && map.containsKey("SECRET")) {
            this.config.setPrefix(map.get("PREFIX"));
            this.config.setSecret(map.get("SECRET"));
            initAndStart();
        } else {
            IConnectInitListener iConnectInitListener = this.initListener;
            if (iConnectInitListener != null) {
                iConnectInitListener.onFailure(CmpError.CONNECT_AUTH_INFO_ERROR());
            }
        }
    }
}
