package com.yc.utesdk.bean;

import androidx.annotation.IntRange;
import java.util.List;

/* loaded from: classes4.dex */
public class BloodSugarLevelInfo {
    private int interval;
    private List<TimeLevel> timeLevelList;

    public static class TimeLevel {
        private int level;
        private int timeMinute;

        public TimeLevel() {
        }

        public int getLevel() {
            return this.level;
        }

        public int getTimeMinute() {
            return this.timeMinute;
        }

        public void setLevel(int i2) {
            this.level = i2;
        }

        public void setTimeMinute(int i2) {
            this.timeMinute = i2;
        }

        public TimeLevel(int i2, int i3) {
            this.timeMinute = i2;
            this.level = i3;
        }
    }

    public BloodSugarLevelInfo() {
    }

    public int getInterval() {
        return this.interval;
    }

    public List<TimeLevel> getTimeLevelList() {
        return this.timeLevelList;
    }

    public void setInterval(@IntRange(from = 1, to = 255) int i2) {
        this.interval = i2;
    }

    public void setTimeLevelList(List<TimeLevel> list) {
        this.timeLevelList = list;
    }

    public BloodSugarLevelInfo(@IntRange(from = 1, to = 255) int i2, List<TimeLevel> list) {
        this.interval = i2;
        this.timeLevelList = list;
    }
}
