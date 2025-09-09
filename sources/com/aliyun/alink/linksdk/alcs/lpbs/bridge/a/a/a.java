package com.aliyun.alink.linksdk.alcs.lpbs.bridge.a.a;

import com.aliyun.alink.linksdk.alcs.data.ica.ICAAuthParams;
import com.aliyun.alink.linksdk.alcs.data.ica.ICAGroupReqMessage;
import com.aliyun.alink.linksdk.alcs.data.ica.ICAOptions;
import com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalGroupConnect;
import com.aliyun.alink.linksdk.alcs.lpbs.bridge.a.c;
import com.aliyun.alink.linksdk.alcs.lpbs.bridge.a.k;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalRspMessage;
import com.aliyun.alink.linksdk.alcs.lpbs.data.group.PalGroupInfo;
import com.aliyun.alink.linksdk.alcs.lpbs.data.group.PalGroupReqMessage;
import com.aliyun.alink.linksdk.alcs.lpbs.data.ica.group.ICAGroupOption;
import com.aliyun.alink.linksdk.alcs.lpbs.listener.PalMsgListener;
import com.aliyun.alink.linksdk.alcs.pal.ica.ICAAlcsNative;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class a implements IPalGroupConnect {

    /* renamed from: a, reason: collision with root package name */
    private static final String f10770a = "[AlcsLPBS]ICAAlcsGroupConnect";

    public a(c cVar, PalGroupInfo palGroupInfo) {
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalGroupConnect
    public boolean asyncSendRequest(PalGroupReqMessage palGroupReqMessage, PalMsgListener palMsgListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Object obj;
        ALog.d(f10770a, "ICAAlcsGroupConnect asyncSendRequest  reqMessageInfo:" + palGroupReqMessage + " callback:" + palMsgListener);
        if (palGroupReqMessage == null || palGroupReqMessage.groupInfo == null || (obj = palGroupReqMessage.palOptions) == null || !(obj instanceof ICAGroupOption)) {
            ALog.e(f10770a, "asyncSendRequest error params empty");
            if (palMsgListener != null) {
                palMsgListener.onLoad(new PalRspMessage(1));
            }
            return false;
        }
        ICAGroupOption iCAGroupOption = (ICAGroupOption) obj;
        if (iCAGroupOption == null || iCAGroupOption.icaOptions == null || iCAGroupOption.icaAuthParams == null) {
            ALog.e(f10770a, "asyncSendRequest error icaGroupOption empty");
            if (palMsgListener != null) {
                palMsgListener.onLoad(new PalRspMessage(1));
            }
            return false;
        }
        ICAGroupReqMessage iCAGroupReqMessage = new ICAGroupReqMessage();
        PalGroupInfo palGroupInfo = palGroupReqMessage.groupInfo;
        iCAGroupReqMessage.groupId = palGroupInfo.groupId;
        iCAGroupReqMessage.deviceInfo = palGroupInfo.getIcaGroupInfo();
        ICAOptions iCAOptions = iCAGroupOption.icaOptions;
        iCAGroupReqMessage.code = iCAOptions.code;
        iCAGroupReqMessage.type = iCAOptions.type;
        iCAGroupReqMessage.rspType = iCAOptions.rspType;
        iCAGroupReqMessage.topic = palGroupReqMessage.topic;
        iCAGroupReqMessage.payload = palGroupReqMessage.payload;
        ICAAuthParams iCAAuthParams = new ICAAuthParams();
        ICAAuthParams iCAAuthParams2 = iCAGroupOption.icaAuthParams;
        iCAAuthParams.accessKey = iCAAuthParams2.accessKey;
        iCAAuthParams.accessToken = iCAAuthParams2.accessToken;
        return ICAAlcsNative.sendGroupRequest(iCAGroupReqMessage, iCAAuthParams, new k(palMsgListener));
    }
}
