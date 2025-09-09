package com.alipay.sdk.m.f0;

import com.alipay.tscenter.biz.rpc.report.general.model.DataReportRequest;
import com.alipay.tscenter.biz.rpc.report.general.model.DataReportResult;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class b {
    public static c a(DataReportResult dataReportResult) {
        c cVar = new c();
        if (dataReportResult == null) {
            return null;
        }
        cVar.f9237a = dataReportResult.success;
        cVar.f9238b = dataReportResult.resultCode;
        Map<String, String> map = dataReportResult.resultData;
        if (map != null) {
            cVar.f9244c = map.get("apdid");
            cVar.f9245d = map.get("apdidToken");
            cVar.f9248g = map.get("dynamicKey");
            cVar.f9249h = map.get("timeInterval");
            cVar.f9250i = map.get("webrtcUrl");
            cVar.f9251j = "";
            String str = map.get("drmSwitch");
            if (com.alipay.sdk.m.z.a.b(str)) {
                if (str.length() > 0) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(str.charAt(0));
                    cVar.f9246e = sb.toString();
                }
                if (str.length() >= 3) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(str.charAt(2));
                    cVar.f9247f = sb2.toString();
                }
            }
            if (map.containsKey("apse_degrade")) {
                cVar.f9252k = map.get("apse_degrade");
            }
        }
        return cVar;
    }

    public static DataReportRequest a(d dVar) {
        DataReportRequest dataReportRequest = new DataReportRequest();
        if (dVar == null) {
            return null;
        }
        dataReportRequest.os = dVar.f9253a;
        dataReportRequest.rpcVersion = dVar.f9262j;
        dataReportRequest.bizType = "1";
        HashMap map = new HashMap();
        dataReportRequest.bizData = map;
        map.put("apdid", dVar.f9254b);
        dataReportRequest.bizData.put("apdidToken", dVar.f9255c);
        dataReportRequest.bizData.put("umidToken", dVar.f9256d);
        dataReportRequest.bizData.put("dynamicKey", dVar.f9257e);
        dataReportRequest.deviceData = dVar.f9258f;
        return dataReportRequest;
    }
}
