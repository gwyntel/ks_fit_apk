package com.aliyun.iot.aep.sdk.framework.region;

import androidx.health.connect.client.records.metadata.DeviceTypes;
import com.aliyun.alink.business.devicecenter.api.share.DeviceShareManager;
import com.aliyun.iot.aep.sdk.framework.base.BaseServerApi;
import com.aliyun.iot.aep.sdk.framework.config.GlobalConfig;
import com.aliyun.iot.aep.sdk.framework.network.BaseRequest;

/* loaded from: classes3.dex */
public class RegionQueryApi extends BaseServerApi {
    public static final String authType = "iotNoAuth";
    public static final String global_host = "cn-shanghai.api-iot.aliyuncs.com";
    public static final String host = GlobalConfig.getCentralHost();
    public static final String path = "/living/account/region/get";
    public static final String version = "1.0.2";

    public static class Request extends BaseRequest {
        public String authCode;
        public String countryCode;
        public String email;
        public String phone;
        public String phoneLocationCode;
        public String type;

        Request(String str, String str2, String str3, String str4, String str5, String str6) {
            this.type = str;
            this.countryCode = str2;
            this.phone = str3;
            this.email = str4;
            this.authCode = str5;
            this.phoneLocationCode = str6;
        }

        public static Request getLoginRequest(String str, String str2, String str3) {
            if (DeviceTypes.PHONE.equals(str)) {
                return new Request(DeviceTypes.PHONE, null, str2, null, null, str3);
            }
            if (DeviceShareManager.SHARE_DEVICE_ACCOUNT_ATTRTYPE_EMAIL.equals(str)) {
                return new Request(DeviceShareManager.SHARE_DEVICE_ACCOUNT_ATTRTYPE_EMAIL, null, null, str2, null, str3);
            }
            return null;
        }

        public static Request getRegRequest() {
            return new Request("COUNTRY_CODE", null, null, null, null, null);
        }

        public static Request getThirdLoginRequest(String str, String str2) {
            return new Request(str, null, null, null, str2, null);
        }

        public String getAuthCode() {
            return this.authCode;
        }

        public String getCountryCode() {
            return this.countryCode;
        }

        public String getEmail() {
            return this.email;
        }

        public String getPhone() {
            return this.phone;
        }

        public String getRegionEnglishName() {
            return this.type;
        }

        public String getType() {
            return this.type;
        }

        public void setAuthCode(String str) {
            this.authCode = str;
        }

        public void setCountryCode(String str) {
            this.countryCode = str;
        }

        public void setEmail(String str) {
            this.email = str;
        }

        public void setPhone(String str) {
            this.phone = str;
        }

        public void setRegionEnglishName(String str) {
            this.type = str;
        }

        public void setType(String str) {
            this.type = str;
        }
    }
}
