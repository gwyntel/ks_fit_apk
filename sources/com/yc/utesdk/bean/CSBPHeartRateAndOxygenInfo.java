package com.yc.utesdk.bean;

/* loaded from: classes4.dex */
public class CSBPHeartRateAndOxygenInfo {
    private String calendar;
    private int heartRateValue;
    private int oxygenValue;

    public CSBPHeartRateAndOxygenInfo() {
    }

    public String getCalendar() {
        return this.calendar;
    }

    public int getHeartRateValue() {
        return this.heartRateValue;
    }

    public int getOxygenValue() {
        return this.oxygenValue;
    }

    public void setCalendar(String str) {
        this.calendar = str;
    }

    public void setHeartRateValue(int i2) {
        this.heartRateValue = i2;
    }

    public void setOxygenValue(int i2) {
        this.oxygenValue = i2;
    }

    public CSBPHeartRateAndOxygenInfo(String str, int i2, int i3) {
        this.calendar = str;
        this.heartRateValue = i2;
        this.oxygenValue = i3;
    }
}
