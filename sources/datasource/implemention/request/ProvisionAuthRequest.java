package datasource.implemention.request;

import mtopsdk.mtop.domain.IMTOPDataObject;

/* loaded from: classes4.dex */
public class ProvisionAuthRequest implements IMTOPDataObject {
    public String API_NAME = "mtop.alibaba.aicloud.sigMesh.provisionAuth";
    public String VERSION = "1.0";
    public boolean NEED_ECODE = true;
    public boolean NEED_SESSION = true;
    public String provisionAuthorizationReq = null;
    public String authInfo = null;

    public String getAPI_NAME() {
        return this.API_NAME;
    }

    public String getAuthInfo() {
        return this.authInfo;
    }

    public String getProvisionAuthorizationReq() {
        return this.provisionAuthorizationReq;
    }

    public String getVERSION() {
        return this.VERSION;
    }

    public boolean isNEED_ECODE() {
        return this.NEED_ECODE;
    }

    public boolean isNEED_SESSION() {
        return this.NEED_SESSION;
    }

    public void setAPI_NAME(String str) {
        this.API_NAME = str;
    }

    public void setAuthInfo(String str) {
        this.authInfo = str;
    }

    public void setNEED_ECODE(boolean z2) {
        this.NEED_ECODE = z2;
    }

    public void setNEED_SESSION(boolean z2) {
        this.NEED_SESSION = z2;
    }

    public void setProvisionAuthorizationReq(String str) {
        this.provisionAuthorizationReq = str;
    }

    public void setVERSION(String str) {
        this.VERSION = str;
    }
}
