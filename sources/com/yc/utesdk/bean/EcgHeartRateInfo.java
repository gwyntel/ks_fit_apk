package com.yc.utesdk.bean;

import androidx.annotation.IntRange;
import java.util.List;

/* loaded from: classes4.dex */
public class EcgHeartRateInfo {
    private List<HeartRateData> heartRateDataList;
    private int verHeartRate;

    public static class HeartRateData {
        private int heartRateCode;
        private String heartRateText;

        public int getHeartRateCode() {
            return this.heartRateCode;
        }

        public String getHeartRateText() {
            return this.heartRateText;
        }

        public void setHeartRateCode(@IntRange(from = 0, to = 17) int i2) {
            this.heartRateCode = i2;
        }

        public void setHeartRateText(String str) {
            this.heartRateText = str;
        }
    }

    public List<HeartRateData> getHeartRateDataList() {
        return this.heartRateDataList;
    }

    public int getVerHeartRate() {
        return this.verHeartRate;
    }

    public void setHeartRateDataList(List<HeartRateData> list) {
        this.heartRateDataList = list;
    }

    public void setVerHeartRate(int i2) {
        this.verHeartRate = i2;
    }
}
