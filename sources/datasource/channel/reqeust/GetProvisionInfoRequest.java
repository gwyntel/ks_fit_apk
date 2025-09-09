package datasource.channel.reqeust;

import com.aliyun.alink.linksdk.connectsdk.BaseApiRequest;

/* loaded from: classes4.dex */
public class GetProvisionInfoRequest extends BaseApiRequest {
    public String REQUEST_METHOD = "POST";
    public String MTOP_API_NAME = "mtop.alibaba.aicloud.sigMesh.getProvisionInfo";
    public String MTOP_VERSION = "1.0";
    public boolean MTOP_NEED_ECODE = false;
    public boolean MTOP_NEED_SESSION = false;
    public String API_HOST = "";
    public String API_PATH = "/living/awss/bt/mesh/provision/device/config/get";
    public String API_VERSION = "1.0.0";
    public String provisionBaseReq = null;

    public String getProvisionBaseReq() {
        return this.provisionBaseReq;
    }

    public void setProvisionBaseReq(String str) {
        this.provisionBaseReq = str;
    }
}
