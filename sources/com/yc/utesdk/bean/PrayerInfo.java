package com.yc.utesdk.bean;

import java.util.List;

/* loaded from: classes4.dex */
public class PrayerInfo {
    public static final int PRAY_TYPE_ASR = 2;
    public static final int PRAY_TYPE_FAJR = 0;
    public static final int PRAY_TYPE_ISHA = 4;
    public static final int PRAY_TYPE_MAGHRIB = 3;
    public static final int PRAY_TYPE_ZUHR = 1;
    private String calendar;
    private List<Pray> prayList;

    public static class Pray {
        private boolean praySwitch;
        private int prayTimeHour;
        private int prayTimeMinute;
        private int prayType;

        public int getPrayTimeHour() {
            return this.prayTimeHour;
        }

        public int getPrayTimeMinute() {
            return this.prayTimeMinute;
        }

        public int getPrayType() {
            return this.prayType;
        }

        public boolean isPraySwitch() {
            return this.praySwitch;
        }

        public void setPraySwitch(boolean z2) {
            this.praySwitch = z2;
        }

        public void setPrayTimeHour(int i2) {
            this.prayTimeHour = i2;
        }

        public void setPrayTimeMinute(int i2) {
            this.prayTimeMinute = i2;
        }

        public void setPrayType(int i2) {
            this.prayType = i2;
        }
    }

    public String getCalendar() {
        return this.calendar;
    }

    public List<Pray> getPrayList() {
        return this.prayList;
    }

    public void setCalendar(String str) {
        this.calendar = str;
    }

    public void setPrayList(List<Pray> list) {
        this.prayList = list;
    }
}
