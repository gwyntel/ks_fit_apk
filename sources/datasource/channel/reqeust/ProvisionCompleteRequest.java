package datasource.channel.reqeust;

import com.aliyun.alink.linksdk.connectsdk.BaseApiRequest;

/* loaded from: classes4.dex */
public class ProvisionCompleteRequest extends BaseApiRequest {
    public String REQUEST_METHOD = "POST";
    public String MTOP_API_NAME = "mtop.alibaba.aicloud.sigMesh.provisionComplete";
    public String MTOP_VERSION = "1.0";
    public boolean MTOP_NEED_ECODE = false;
    public boolean MTOP_NEED_SESSION = false;
    public String API_HOST = "";
    public String API_PATH = "/living/awss/bt/mesh/provision/complete";
    public String API_VERSION = "1.0.0";
    public String provisionCompleteReq = null;

    public String getProvisionCompleteReq() {
        return this.provisionCompleteReq;
    }

    public void setProvisionCompleteReq(String str) {
        this.provisionCompleteReq = str;
    }
}
