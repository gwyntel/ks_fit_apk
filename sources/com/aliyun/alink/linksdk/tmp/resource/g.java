package com.aliyun.alink.linksdk.tmp.resource;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.alink.linksdk.cmp.api.ResourceRequest;
import com.aliyun.alink.linksdk.cmp.connect.alcs.CoAPResource;
import com.aliyun.alink.linksdk.cmp.core.base.AResource;
import com.aliyun.alink.linksdk.cmp.core.listener.IResourceRequestListener;
import com.aliyun.alink.linksdk.cmp.core.listener.IResourceResponseListener;
import com.aliyun.alink.linksdk.tmp.utils.ErrorInfo;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.aliyun.alink.linksdk.tools.AError;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class g implements IResourceRequestListener {

    /* renamed from: c, reason: collision with root package name */
    private static final String f11426c = "[Tmp]TResRequestListener";

    /* renamed from: a, reason: collision with root package name */
    protected b f11427a;

    /* renamed from: b, reason: collision with root package name */
    protected String f11428b;

    public g(String str, b bVar) {
        this.f11427a = bVar;
        this.f11428b = str;
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.listener.IBaseListener
    public void onFailure(AError aError) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11426c, "onFailure mIdentifer:" + this.f11428b);
        b bVar = this.f11427a;
        if (bVar != null) {
            bVar.onFail(null, new ErrorInfo(aError));
        }
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.listener.IResourceRequestListener
    public void onHandleRequest(AResource aResource, ResourceRequest resourceRequest, IResourceResponseListener iResourceResponseListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11426c, "onHandleRequest identifier:" + this.f11428b + " aResource:" + aResource + " request:" + resourceRequest + " mHandler:" + this.f11427a);
        String str = null;
        String str2 = (aResource == null || !(aResource instanceof CoAPResource)) ? null : ((CoAPResource) aResource).topic;
        if (resourceRequest != null && !TextUtils.isEmpty(this.f11428b) && this.f11428b.contains(TmpConstant.IDENTIFIER_RAW_DATA_DOWN)) {
            this.f11427a.onProcess(this.f11428b, str2, resourceRequest, new d(resourceRequest, aResource, iResourceResponseListener));
            return;
        }
        if (resourceRequest == null) {
            ALog.w(f11426c, "onHandleRequest request error" + resourceRequest);
            return;
        }
        Object obj = resourceRequest.payloadObj;
        if (obj instanceof byte[]) {
            try {
                str = new String((byte[]) obj, "UTF-8");
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } else {
            str = String.valueOf(obj);
        }
        ALog.d(f11426c, "onHandleRequest payload:" + str);
        String string = "0";
        try {
            JSONObject object = JSON.parseObject(str);
            if (object != null) {
                string = object.getString("id");
            }
        } catch (Exception e3) {
            ALog.w(f11426c, "onHandleRequest:" + e3.toString());
        }
        this.f11427a.onProcess(this.f11428b, resourceRequest.topic, str, new i(resourceRequest, aResource, string, iResourceResponseListener));
    }

    @Override // com.aliyun.alink.linksdk.cmp.core.listener.IBaseListener
    public void onSuccess() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11426c, "onSuccess mIdentifer:" + this.f11428b);
        b bVar = this.f11427a;
        if (bVar != null) {
            bVar.onSuccess(null, null);
        }
    }
}
