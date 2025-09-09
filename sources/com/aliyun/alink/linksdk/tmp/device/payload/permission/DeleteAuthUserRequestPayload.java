package com.aliyun.alink.linksdk.tmp.device.payload.permission;

import com.aliyun.alink.linksdk.tmp.device.payload.CommonRequestPayload;
import java.util.List;

/* loaded from: classes2.dex */
public class DeleteAuthUserRequestPayload extends CommonRequestPayload<DeleteAuthUserParam> {

    public static class DeleteAuthUserParam {
        protected String deviceName;
        protected String prodKey;
        protected List<String> uids;

        public String getDeviceName() {
            return this.deviceName;
        }

        public List<String> getIds() {
            return this.uids;
        }

        public String getProdKey() {
            return this.prodKey;
        }

        public void setDeviceName(String str) {
            this.deviceName = str;
        }

        public void setIds(List<String> list) {
            this.uids = list;
        }

        public void setProdKey(String str) {
            this.prodKey = str;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [T, com.aliyun.alink.linksdk.tmp.device.payload.permission.DeleteAuthUserRequestPayload$DeleteAuthUserParam] */
    public DeleteAuthUserRequestPayload(String str, String str2) {
        super(str, str2);
        ?? deleteAuthUserParam = new DeleteAuthUserParam();
        this.params = deleteAuthUserParam;
        deleteAuthUserParam.setProdKey(str);
        ((DeleteAuthUserParam) this.params).setDeviceName(str2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setIds(List<String> list) {
        ((DeleteAuthUserParam) this.params).setIds(list);
    }
}
