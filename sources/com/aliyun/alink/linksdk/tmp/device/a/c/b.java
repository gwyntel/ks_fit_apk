package com.aliyun.alink.linksdk.tmp.device.a.c;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.aliyun.alink.linksdk.tmp.config.GroupConfig;
import com.aliyun.alink.linksdk.tmp.data.auth.AccessInfo;
import com.aliyun.alink.linksdk.tmp.device.a.e;
import com.aliyun.alink.linksdk.tmp.device.request.IGateWayRequestListener;
import com.aliyun.alink.linksdk.tmp.device.request.localgroup.GetLocalControlInfoRequest;
import com.aliyun.alink.linksdk.tmp.device.request.localgroup.QueryLocalGroupDeviceRequest;
import com.aliyun.alink.linksdk.tmp.listener.IDevListener;
import com.aliyun.alink.linksdk.tmp.storage.TmpStorage;
import com.aliyun.alink.linksdk.tmp.utils.ErrorInfo;
import com.aliyun.alink.linksdk.tools.AError;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.NoSuchPaddingException;

/* loaded from: classes2.dex */
public class b extends e<b> {
    public b(com.aliyun.alink.linksdk.tmp.device.b bVar, IDevListener iDevListener) {
        super(bVar, iDevListener);
    }

    @Override // com.aliyun.alink.linksdk.tmp.device.a.a
    public synchronized boolean a() {
        com.aliyun.alink.linksdk.tmp.device.b bVar = this.f11326c.get();
        if (bVar == null) {
            ALog.e("[Tmp]AsyncTask", "groupImpl empty");
            b((b) null, new ErrorInfo(302, "param is invalid"));
            return false;
        }
        final GroupConfig groupConfigA = bVar.a();
        if (groupConfigA == null) {
            ALog.e("[Tmp]AsyncTask", "groupConfig empty");
            b((b) null, new ErrorInfo(302, "param is invalid"));
            return false;
        }
        final String str = groupConfigA.groupId;
        if (TextUtils.isEmpty(groupConfigA.localGroupId)) {
            groupConfigA.localGroupId = TmpStorage.getInstance().getLocalGroupId(str);
        }
        GetLocalControlInfoRequest getLocalControlInfoRequest = new GetLocalControlInfoRequest();
        getLocalControlInfoRequest.controlGroupId = str;
        ALog.d("[Tmp]AsyncTask", "GetLocalGroupInfotTask action localGroupId:" + groupConfigA.localGroupId);
        if (TextUtils.isEmpty(groupConfigA.localGroupId)) {
            getLocalControlInfoRequest.sendRequest(getLocalControlInfoRequest, new IGateWayRequestListener<GetLocalControlInfoRequest, GetLocalControlInfoRequest.GetLocalControlInfoResponse>() { // from class: com.aliyun.alink.linksdk.tmp.device.a.c.b.1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // com.aliyun.alink.linksdk.tmp.device.request.IGateWayRequestListener
                /* renamed from: a, reason: merged with bridge method [inline-methods] */
                public void onSuccess(GetLocalControlInfoRequest getLocalControlInfoRequest2, GetLocalControlInfoRequest.GetLocalControlInfoResponse getLocalControlInfoResponse) throws IllegalAccessException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalArgumentException, InvocationTargetException {
                    T t2;
                    if (getLocalControlInfoResponse == null || (t2 = getLocalControlInfoResponse.data) == 0) {
                        ALog.e("[Tmp]AsyncTask", "getLocalControlInfoRequest onSuccess result empty");
                        b.this.b((b) null, new ErrorInfo(302, "param is invalid"));
                        return;
                    }
                    AccessInfo accessInfo = new AccessInfo(((GetLocalControlInfoRequest.GetLocalControlInfoResponse.GetLocalControlInfoData) t2).accessKey, ((GetLocalControlInfoRequest.GetLocalControlInfoResponse.GetLocalControlInfoData) t2).accessToken);
                    GroupConfig groupConfig = groupConfigA;
                    groupConfig.localGroupId = ((GetLocalControlInfoRequest.GetLocalControlInfoResponse.GetLocalControlInfoData) getLocalControlInfoResponse.data).localGroupId;
                    groupConfig.accessInfo = accessInfo;
                    TmpStorage.getInstance().saveLocalGroupId(str, ((GetLocalControlInfoRequest.GetLocalControlInfoResponse.GetLocalControlInfoData) getLocalControlInfoResponse.data).localGroupId);
                    TmpStorage.getInstance().saveGroupAccessInfo(str, accessInfo);
                    b.this.a(groupConfigA);
                }

                @Override // com.aliyun.alink.linksdk.tmp.device.request.IGateWayRequestListener
                /* renamed from: a, reason: merged with bridge method [inline-methods] */
                public void onFail(GetLocalControlInfoRequest getLocalControlInfoRequest2, AError aError) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    ALog.e("[Tmp]AsyncTask", "getLocalControlInfoRequest onFail error :" + aError);
                    b.this.b((b) null, new ErrorInfo(302, "param is invalid"));
                }
            });
        } else {
            a(groupConfigA);
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final GroupConfig groupConfig) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (groupConfig == null) {
            ALog.e("[Tmp]AsyncTask", "getLocalState call empty groupConfig empty");
            b((b) null, new ErrorInfo(300, "param is invalid"));
            return;
        }
        if (groupConfig.localGroupDeviceData == null) {
            try {
                groupConfig.localGroupDeviceData = (QueryLocalGroupDeviceRequest.QueryLocalGroupDeviceResData) JSON.parseObject(TmpStorage.getInstance().getLocalGroupInfo(groupConfig.groupId), QueryLocalGroupDeviceRequest.QueryLocalGroupDeviceResData.class);
            } catch (Exception e2) {
                ALog.e("[Tmp]AsyncTask", "parseObject error:" + e2.toString());
            }
        }
        QueryLocalGroupDeviceRequest queryLocalGroupDeviceRequest = new QueryLocalGroupDeviceRequest();
        queryLocalGroupDeviceRequest.localGroupId = groupConfig.localGroupId;
        if (groupConfig.localGroupDeviceData != null) {
            a((b) null, (Object) null);
        } else {
            queryLocalGroupDeviceRequest.sendRequest(queryLocalGroupDeviceRequest, new IGateWayRequestListener<QueryLocalGroupDeviceRequest, QueryLocalGroupDeviceRequest.QueryLocalGroupDeviceResponse>() { // from class: com.aliyun.alink.linksdk.tmp.device.a.c.b.2
                /* JADX WARN: Multi-variable type inference failed */
                @Override // com.aliyun.alink.linksdk.tmp.device.request.IGateWayRequestListener
                /* renamed from: a, reason: merged with bridge method [inline-methods] */
                public void onSuccess(QueryLocalGroupDeviceRequest queryLocalGroupDeviceRequest2, QueryLocalGroupDeviceRequest.QueryLocalGroupDeviceResponse queryLocalGroupDeviceResponse) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    T t2;
                    if (queryLocalGroupDeviceResponse == null || (t2 = queryLocalGroupDeviceResponse.data) == 0) {
                        ALog.e("[Tmp]AsyncTask", "QueryLocalGroupDeviceRequest result empty");
                        b.this.b((b) null, new ErrorInfo(300, "param is invalid"));
                    } else {
                        groupConfig.localGroupDeviceData = (QueryLocalGroupDeviceRequest.QueryLocalGroupDeviceResData) t2;
                        TmpStorage.getInstance().saveLocalGroupInfo(groupConfig.groupId, JSON.toJSONString(queryLocalGroupDeviceResponse.data));
                        b.this.a((b) null, (Object) null);
                    }
                }

                @Override // com.aliyun.alink.linksdk.tmp.device.request.IGateWayRequestListener
                /* renamed from: a, reason: merged with bridge method [inline-methods] */
                public void onFail(QueryLocalGroupDeviceRequest queryLocalGroupDeviceRequest2, AError aError) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    ALog.e("[Tmp]AsyncTask", "queryLocalGroupDeviceRequest QueryLocalGroupDeviceRequest onFail error:" + aError);
                    b.this.b((b) null, new ErrorInfo(300, "param is invalid"));
                }
            });
        }
    }
}
