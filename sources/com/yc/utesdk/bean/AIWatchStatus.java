package com.yc.utesdk.bean;

import androidx.annotation.IntRange;

/* loaded from: classes4.dex */
public class AIWatchStatus {
    public static final int CONFIRM_SET_AI_WATCH = 12;
    public static final int END_RECORDING = 3;
    public static final int ENTER_AI_WATCH = 1;
    public static final int EXIT_AI_WATCH = 4;
    public static final int GENERATE_SERVER_BUSY = 11;
    public static final int GENERATE_TIMEOUT = 10;
    public static final int RECOGNITION_FAIL = 6;
    public static final int RECOGNITION_SUCCESS = 7;
    public static final int REGENERATE = 13;
    public static final int REMIND_OPEN_APP = 5;
    public static final int RERECORD = 8;
    public static final int START_GENERATE = 9;
    public static final int START_RECORDING = 2;
    public static final int USED_UP = 14;
    private int status;

    public AIWatchStatus() {
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(@IntRange(from = 1, to = 14) int i2) {
        this.status = i2;
    }

    public AIWatchStatus(@IntRange(from = 1, to = 14) int i2) {
        this.status = i2;
    }
}
