package com.yc.utesdk.bean;

import com.google.gson.Gson;

/* loaded from: classes4.dex */
public class ActivationResult extends BaseResult {
    private DataBean data;

    public static class DataBean {
        private String activation_code;

        public String getActivation_code() {
            return this.activation_code;
        }

        public void setActivation_code(String str) {
            this.activation_code = str;
        }
    }

    public DataBean getData() {
        return this.data;
    }

    public void setData(DataBean dataBean) {
        this.data = dataBean;
    }

    @Override // com.yc.utesdk.bean.BaseResult
    public String toString() {
        return new Gson().toJson(this);
    }
}
