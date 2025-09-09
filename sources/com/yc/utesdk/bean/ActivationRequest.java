package com.yc.utesdk.bean;

import com.google.gson.Gson;

/* loaded from: classes4.dex */
public class ActivationRequest extends BaseRequest {
    private String firm;
    private String model;
    private String name;
    private String request_code;
    private SensorBean sensor;
    private String sinklib_version;

    public static class SensorBean {
        private int fs;
        private String model;

        public int getFs() {
            return this.fs;
        }

        public String getModel() {
            return this.model;
        }

        public void setFs(int i2) {
            this.fs = i2;
        }

        public void setModel(String str) {
            this.model = str;
        }
    }

    public String getFirm() {
        return this.firm;
    }

    public String getModel() {
        return this.model;
    }

    public String getName() {
        return this.name;
    }

    public String getRequest_code() {
        return this.request_code;
    }

    public SensorBean getSensor() {
        return this.sensor;
    }

    public String getSinklib_version() {
        return this.sinklib_version;
    }

    public void setFirm(String str) {
        this.firm = str;
    }

    public void setModel(String str) {
        this.model = str;
    }

    public void setName(String str) {
        this.name = str;
    }

    public void setRequest_code(String str) {
        this.request_code = str;
    }

    public void setSensor(SensorBean sensorBean) {
        this.sensor = sensorBean;
    }

    public void setSinklib_version(String str) {
        this.sinklib_version = str;
    }

    @Override // com.yc.utesdk.bean.BaseRequest
    public String toString() {
        return new Gson().toJson(this);
    }
}
