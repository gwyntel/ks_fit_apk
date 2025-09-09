package com.yc.utesdk.bean;

import java.util.List;

/* loaded from: classes4.dex */
public class BloodSugarValueInfo {
    private int interval;
    private List<TimeValue> timeValueList;

    public static class TimeValue {
        private int timeMinute;
        private float value;

        public TimeValue() {
        }

        public int getTimeMinute() {
            return this.timeMinute;
        }

        public float getValue() {
            return this.value;
        }

        public void setTimeMinute(int i2) {
            this.timeMinute = i2;
        }

        public void setValue(float f2) {
            this.value = f2;
        }

        public TimeValue(int i2, float f2) {
            this.timeMinute = i2;
            this.value = f2;
        }
    }

    public BloodSugarValueInfo() {
    }

    public int getInterval() {
        return this.interval;
    }

    public List<TimeValue> getTimeValueList() {
        return this.timeValueList;
    }

    public void setInterval(int i2) {
        this.interval = i2;
    }

    public void setTimeValueList(List<TimeValue> list) {
        this.timeValueList = list;
    }

    public BloodSugarValueInfo(int i2, List<TimeValue> list) {
        this.interval = i2;
        this.timeValueList = list;
    }
}
