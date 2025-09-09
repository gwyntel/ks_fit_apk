package com.yc.utesdk.utils.close;

/* loaded from: classes4.dex */
public class SleepRawDataInfo {
    private String calendar;
    private String rawData;

    public SleepRawDataInfo() {
    }

    public String getCalendar() {
        return this.calendar;
    }

    public String getRawData() {
        return this.rawData;
    }

    public void setCalendar(String str) {
        this.calendar = str;
    }

    public void setRawData(String str) {
        this.rawData = str;
    }

    public SleepRawDataInfo(String str) {
        this.calendar = str;
    }

    public SleepRawDataInfo(String str, String str2) {
        this.calendar = str;
        this.rawData = str2;
    }
}
