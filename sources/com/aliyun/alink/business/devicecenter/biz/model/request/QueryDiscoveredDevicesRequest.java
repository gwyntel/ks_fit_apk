package com.aliyun.alink.business.devicecenter.biz.model.request;

import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.aliyun.alink.linksdk.connectsdk.BaseApiRequest;
import java.util.List;

/* loaded from: classes2.dex */
public class QueryDiscoveredDevicesRequest extends BaseApiRequest {
    public List<Object> appReportDevices;
    public String gatewayIotId;
    public int pageNo;
    public int pageSize;
    public boolean scanStartedFirstQuery;
    public String REQUEST_METHOD = "POST";
    public String API_HOST = "";
    public String API_PATH = AlinkConstants.HTTP_PATH_GET_DISCOVERED_MESH_DEVICE;
    public String API_VERSION = "1.0.3";

    public List<Object> getAppReportDevices() {
        return this.appReportDevices;
    }

    public String getGatewayIotId() {
        return this.gatewayIotId;
    }

    public int getPageNo() {
        return this.pageNo;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public boolean isScanStartedFirstQuery() {
        return this.scanStartedFirstQuery;
    }

    public void setAppReportDevices(List<Object> list) {
        this.appReportDevices = list;
    }

    public void setGatewayIotId(String str) {
        this.gatewayIotId = str;
    }

    public void setPageNo(int i2) {
        this.pageNo = i2;
    }

    public void setPageSize(int i2) {
        this.pageSize = i2;
    }

    public void setScanStartedFirstQuery(boolean z2) {
        this.scanStartedFirstQuery = z2;
    }
}
