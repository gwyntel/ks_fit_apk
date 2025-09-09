package com.aliyun.alink.linksdk.cmp.api;

import com.aliyun.alink.linksdk.alcs.coap.AlcsCoAPConstant;
import com.aliyun.alink.linksdk.cmp.core.base.ARequest;
import com.aliyun.iot.aep.sdk.apiclient.emuns.Method;
import java.util.Map;

/* loaded from: classes2.dex */
public class CommonRequest extends ARequest {
    public String alinkIdForTracker;
    public Object context;
    public String iotId;
    public String ip;
    public boolean isSecurity;
    public METHOD mothod = null;
    public Map<String, Object> params;
    public Object payload;
    public int port;
    public Object rspType;
    public String tgMeshType;
    public String topic;
    public String traceId;
    public Object type;

    /* renamed from: com.aliyun.alink.linksdk.cmp.api.CommonRequest$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$aliyun$alink$linksdk$cmp$api$CommonRequest$METHOD;

        static {
            int[] iArr = new int[METHOD.values().length];
            $SwitchMap$com$aliyun$alink$linksdk$cmp$api$CommonRequest$METHOD = iArr;
            try {
                iArr[METHOD.POST.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$aliyun$alink$linksdk$cmp$api$CommonRequest$METHOD[METHOD.GET.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$aliyun$alink$linksdk$cmp$api$CommonRequest$METHOD[METHOD.DELETE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$aliyun$alink$linksdk$cmp$api$CommonRequest$METHOD[METHOD.PUT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public enum METHOD {
        POST,
        GET,
        DELETE,
        PUT;

        public Method toApiGwMethod() {
            int i2 = AnonymousClass1.$SwitchMap$com$aliyun$alink$linksdk$cmp$api$CommonRequest$METHOD[ordinal()];
            if (i2 == 1) {
                return Method.POST;
            }
            if (i2 == 2) {
                return Method.GET;
            }
            if (i2 == 3) {
                return Method.DELETE;
            }
            if (i2 != 4) {
                return null;
            }
            return Method.PUT;
        }

        public AlcsCoAPConstant.Code toCoapCode() {
            int i2 = AnonymousClass1.$SwitchMap$com$aliyun$alink$linksdk$cmp$api$CommonRequest$METHOD[ordinal()];
            if (i2 == 1) {
                return AlcsCoAPConstant.Code.POST;
            }
            if (i2 == 2) {
                return AlcsCoAPConstant.Code.GET;
            }
            if (i2 == 3) {
                return AlcsCoAPConstant.Code.DELETE;
            }
            if (i2 != 4) {
                return null;
            }
            return AlcsCoAPConstant.Code.PUT;
        }
    }

    public String getSpecificTopic(boolean z2) {
        return this.topic;
    }
}
