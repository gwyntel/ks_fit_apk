package com.aliyun.alink.linksdk.cmp.core.util;

import android.text.TextUtils;
import com.aliyun.alink.linksdk.alcs.api.client.AlcsClientConfig;
import com.aliyun.alink.linksdk.alcs.coap.AlcsCoAPConstant;
import com.aliyun.alink.linksdk.alcs.coap.AlcsCoAPRequest;
import com.aliyun.alink.linksdk.alcs.coap.AlcsCoAPResponse;
import com.aliyun.alink.linksdk.alcs.data.ica.ICAOptions;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalDeviceInfo;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalReqMessage;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalRspMessage;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalSubMessage;
import com.aliyun.alink.linksdk.channel.core.persistent.mqtt.request.MqttPublishRequest;
import com.aliyun.alink.linksdk.cmp.api.CommonRequest;
import com.aliyun.alink.linksdk.cmp.api.CommonResource;
import com.aliyun.alink.linksdk.cmp.connect.alcs.AlcsConnectConfig;
import com.aliyun.alink.linksdk.cmp.connect.alcs.CoAPRequest;
import com.aliyun.alink.linksdk.cmp.connect.alcs.CoAPResource;
import com.aliyun.alink.linksdk.cmp.connect.alcs.CoAPResponse;
import com.aliyun.alink.linksdk.cmp.connect.apigw.ApiGatewayRequest;
import com.aliyun.alink.linksdk.cmp.connect.channel.MqttResource;
import com.aliyun.alink.linksdk.cmp.connect.channel.MqttRrpcRegisterRequest;
import com.aliyun.alink.linksdk.cmp.connect.channel.MqttSubscribeRequest;
import com.aliyun.alink.linksdk.cmp.connect.hubapi.HubApiRequest;
import com.aliyun.alink.linksdk.cmp.core.base.AResponse;
import com.aliyun.alink.linksdk.lpbs.lpbstgmesh.data.TgMeshReqExtraData;
import com.aliyun.alink.linksdk.tools.AError;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class ClassSwitchHelper {
    private static final String TAG = "ClassSwitchHelper";

    public static CoAPResponse IotResTransfer(PalRspMessage palRspMessage) {
        if (palRspMessage == null) {
            return null;
        }
        CoAPResponse coAPResponse = new CoAPResponse();
        coAPResponse.data = palRspMessage.payload;
        return coAPResponse;
    }

    public static AError aErrorChannelToCmp(com.aliyun.alink.linksdk.channel.core.base.AError aError) {
        AError aError2 = new AError();
        aError2.setDomain(aError.getDomain());
        aError2.setSubDomain(aError.getSubDomain());
        aError2.setCode(aError.getCode());
        aError2.setMsg(aError.getMsg());
        aError2.setSubCode(aError.getSubCode());
        aError2.setSubMsg(aError.getSubMsg());
        aError2.setOriginResponseObject(aError.getOriginResponseObject());
        return aError2;
    }

    public static AResponse aRspChannelToCmp(com.aliyun.alink.linksdk.channel.core.base.AResponse aResponse) {
        AResponse aResponse2 = new AResponse();
        aResponse2.data = aResponse.data;
        return aResponse2;
    }

    public static com.aliyun.alink.linksdk.channel.core.base.AResponse aRspCmpToChannel(AResponse aResponse) {
        com.aliyun.alink.linksdk.channel.core.base.AResponse aResponse2 = new com.aliyun.alink.linksdk.channel.core.base.AResponse();
        aResponse2.data = aResponse.data;
        return aResponse2;
    }

    /* JADX WARN: Type inference failed for: r1v9, types: [ExtraConnectParams, ExtraParams] */
    public static AlcsClientConfig alcsConfigTransfer(AlcsConnectConfig alcsConnectConfig) {
        if (alcsConnectConfig == null) {
            return null;
        }
        AlcsClientConfig alcsClientConfig = new AlcsClientConfig();
        alcsClientConfig.setDstAddr(alcsConnectConfig.getDstAddr());
        alcsClientConfig.setDstPort(alcsConnectConfig.getDstPort());
        alcsClientConfig.setAccessKey(alcsConnectConfig.getAccessKey());
        alcsClientConfig.setAccessToken(alcsConnectConfig.getAccessToken());
        alcsClientConfig.setProductKey(alcsConnectConfig.getProductKey());
        alcsClientConfig.setDeviceName(alcsConnectConfig.getDeviceName());
        alcsClientConfig.setIsNeddAuth(alcsConnectConfig.isSecurity());
        alcsClientConfig.mDataFormat = alcsConnectConfig.mDataFormat;
        alcsClientConfig.mConnectKeepType = alcsConnectConfig.mConnectKeepType;
        alcsClientConfig.mExtraParams = alcsConnectConfig.mExtraConnectParams;
        alcsClientConfig.mPluginId = alcsConnectConfig.mPluginId;
        return alcsClientConfig;
    }

    public static PalReqMessage alcsRequestToIotReqMsg(String str, String str2, CoAPRequest coAPRequest) {
        if (coAPRequest == null) {
            return null;
        }
        PalReqMessage palReqMessage = new PalReqMessage();
        palReqMessage.deviceInfo = new PalDeviceInfo(str, str2);
        palReqMessage.payload = coAPRequest.payload.toString().getBytes();
        ICAOptions iCAOptions = new ICAOptions();
        iCAOptions.code = coAPRequest.code.value;
        AlcsCoAPConstant.Type type = coAPRequest.type;
        if (type == null) {
            iCAOptions.type = AlcsCoAPConstant.Type.CON.value;
        } else {
            iCAOptions.type = type.value;
        }
        palReqMessage.extraReqData = coAPRequest.extraReqData;
        iCAOptions.rspType = coAPRequest.rspType;
        palReqMessage.palOptions = iCAOptions;
        palReqMessage.topic = coAPRequest.topic;
        return palReqMessage;
    }

    public static PalSubMessage alcsRequestToIotSubMsg(String str, String str2, CoAPRequest coAPRequest) {
        if (coAPRequest == null) {
            return null;
        }
        PalSubMessage palSubMessage = new PalSubMessage();
        palSubMessage.deviceInfo = new PalDeviceInfo(str, str2);
        palSubMessage.payload = coAPRequest.payload.toString().getBytes();
        palSubMessage.topic = coAPRequest.topic;
        return palSubMessage;
    }

    public static AlcsCoAPRequest alcsRequestTransfer(CoAPRequest coAPRequest) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (coAPRequest == null) {
            return null;
        }
        AlcsCoAPRequest alcsCoAPRequest = new AlcsCoAPRequest(coAPRequest.code, coAPRequest.type);
        alcsCoAPRequest.setPayload((String) coAPRequest.payload);
        alcsCoAPRequest.setURI(coAPRequest.ip + ":" + coAPRequest.port + coAPRequest.topic);
        return alcsCoAPRequest;
    }

    public static CoAPResponse alcsResponseTransfer(AlcsCoAPResponse alcsCoAPResponse) {
        if (alcsCoAPResponse == null) {
            return null;
        }
        CoAPResponse coAPResponse = new CoAPResponse();
        coAPResponse.data = alcsCoAPResponse.getPayload();
        return coAPResponse;
    }

    public static ApiGatewayRequest commonReqToApiGwReq(CommonRequest commonRequest) {
        if (commonRequest == null) {
            return null;
        }
        ApiGatewayRequest apiGatewayRequestBuild = ApiGatewayRequest.build(commonRequest.topic, null, commonRequest.params);
        CommonRequest.METHOD method = commonRequest.mothod;
        if (method != null) {
            apiGatewayRequestBuild.method = method.toApiGwMethod();
        }
        String str = commonRequest.traceId;
        if (str != null) {
            apiGatewayRequestBuild.traceId = str;
        }
        apiGatewayRequestBuild.params = commonRequest.params;
        apiGatewayRequestBuild.path = commonRequest.getSpecificTopic(false);
        if (!TextUtils.isEmpty(commonRequest.ip) && commonRequest.port != 0) {
            apiGatewayRequestBuild.host = commonRequest.ip + ":" + commonRequest.port;
        }
        return apiGatewayRequestBuild;
    }

    /* JADX WARN: Type inference failed for: r1v16, types: [ExtraReqData, com.aliyun.alink.linksdk.lpbs.lpbstgmesh.data.TgMeshReqExtraData] */
    public static CoAPRequest commonReqToCoapReq(CommonRequest commonRequest) {
        if (commonRequest == null) {
            return null;
        }
        CoAPRequest coAPRequest = new CoAPRequest();
        CommonRequest.METHOD method = commonRequest.mothod;
        if (method != null) {
            coAPRequest.code = method.toCoapCode();
        }
        Object obj = commonRequest.type;
        if (obj != null && (obj instanceof AlcsCoAPConstant.Type)) {
            coAPRequest.type = (AlcsCoAPConstant.Type) obj;
        }
        coAPRequest.ip = commonRequest.ip;
        coAPRequest.port = commonRequest.port;
        coAPRequest.payload = commonRequest.payload;
        coAPRequest.topic = commonRequest.getSpecificTopic(true);
        coAPRequest.isSecurity = commonRequest.isSecurity;
        Object obj2 = commonRequest.rspType;
        coAPRequest.rspType = obj2 == null ? 0 : ((Integer) obj2).intValue();
        try {
            if (!TextUtils.isEmpty(commonRequest.tgMeshType)) {
                ?? tgMeshReqExtraData = new TgMeshReqExtraData();
                tgMeshReqExtraData.method = commonRequest.tgMeshType;
                tgMeshReqExtraData.iotId = commonRequest.iotId;
                coAPRequest.extraReqData = tgMeshReqExtraData;
            }
        } catch (Exception unused) {
            ALog.w(TAG, "");
        } catch (Throwable unused2) {
        }
        String str = commonRequest.traceId;
        if (str != null) {
            coAPRequest.traceId = str;
        }
        String str2 = commonRequest.alinkIdForTracker;
        if (str2 != null) {
            coAPRequest.alinkIdForTracker = str2;
        }
        return coAPRequest;
    }

    public static HubApiRequest commonReqToHubApiReq(CommonRequest commonRequest) {
        if (commonRequest == null) {
            return null;
        }
        return HubApiRequest.build(commonRequest.topic, commonRequest.params);
    }

    public static MqttRrpcRegisterRequest commonReqToMqttRrpcRegReq(CommonRequest commonRequest) {
        if (commonRequest == null) {
            return null;
        }
        MqttRrpcRegisterRequest mqttRrpcRegisterRequest = new MqttRrpcRegisterRequest();
        mqttRrpcRegisterRequest.topic = commonRequest.topic;
        return mqttRrpcRegisterRequest;
    }

    public static MqttSubscribeRequest commonReqToMqttSubReq(CommonRequest commonRequest) {
        if (commonRequest == null) {
            return null;
        }
        MqttSubscribeRequest mqttSubscribeRequest = new MqttSubscribeRequest();
        mqttSubscribeRequest.isSubscribe = true;
        mqttSubscribeRequest.topic = commonRequest.topic;
        return mqttSubscribeRequest;
    }

    public static MqttSubscribeRequest commonReqToMqttUnsubReq(CommonRequest commonRequest) {
        if (commonRequest == null) {
            return null;
        }
        MqttSubscribeRequest mqttSubscribeRequest = new MqttSubscribeRequest();
        mqttSubscribeRequest.isSubscribe = false;
        mqttSubscribeRequest.topic = commonRequest.topic;
        return mqttSubscribeRequest;
    }

    public static CoAPResource commonResToCoapRes(CommonResource commonResource) {
        if (commonResource == null) {
            return null;
        }
        CoAPResource coAPResource = new CoAPResource();
        coAPResource.topic = commonResource.topic;
        coAPResource.payload = commonResource.payload;
        coAPResource.isNeedAuth = commonResource.isNeedAuth;
        return coAPResource;
    }

    public static MqttResource commonResToMqttRes(CommonResource commonResource) {
        if (commonResource == null) {
            return null;
        }
        MqttResource mqttResource = new MqttResource();
        mqttResource.topic = commonResource.topic;
        mqttResource.replyTopic = commonResource.replyTopic;
        mqttResource.content = commonResource.payload;
        return mqttResource;
    }

    public static MqttPublishRequest mqttPubReqCmpToChannel(com.aliyun.alink.linksdk.cmp.connect.channel.MqttPublishRequest mqttPublishRequest) {
        MqttPublishRequest mqttPublishRequest2 = new MqttPublishRequest();
        mqttPublishRequest2.topic = mqttPublishRequest.topic;
        mqttPublishRequest2.isRPC = mqttPublishRequest.isRPC;
        mqttPublishRequest2.replyTopic = mqttPublishRequest.replyTopic;
        mqttPublishRequest2.msgId = mqttPublishRequest.msgId;
        mqttPublishRequest2.context = mqttPublishRequest.context;
        mqttPublishRequest2.payloadObj = mqttPublishRequest.payloadObj;
        mqttPublishRequest2.qos = mqttPublishRequest.qos;
        return mqttPublishRequest2;
    }

    public static com.aliyun.alink.linksdk.channel.core.persistent.mqtt.request.MqttSubscribeRequest mqttSubReqCmpToChannel(MqttSubscribeRequest mqttSubscribeRequest) {
        com.aliyun.alink.linksdk.channel.core.persistent.mqtt.request.MqttSubscribeRequest mqttSubscribeRequest2 = new com.aliyun.alink.linksdk.channel.core.persistent.mqtt.request.MqttSubscribeRequest();
        mqttSubscribeRequest2.topic = mqttSubscribeRequest.topic;
        mqttSubscribeRequest2.isSubscribe = mqttSubscribeRequest.isSubscribe;
        mqttSubscribeRequest2.context = mqttSubscribeRequest.context;
        mqttSubscribeRequest2.payloadObj = mqttSubscribeRequest.payloadObj;
        return mqttSubscribeRequest2;
    }
}
