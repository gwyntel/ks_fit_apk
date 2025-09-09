package com.yc.utesdk.bean;

import androidx.annotation.IntRange;

/* loaded from: classes4.dex */
public class ChatGptStatus {
    public static final int ANSWER_FINISH = 10;
    public static final int APP_RESPONDING_LAST_QUESTION = 11;
    public static final int CONFIRM_CONTENT = 8;
    public static final int END_RECORDING = 3;
    public static final int ENTER_CHAT_GPT = 1;
    public static final int EXIT_CHAT_GPT = 4;
    public static final int RECOGNITION_FAIL = 6;
    public static final int RECOGNITION_SUCCESS = 7;
    public static final int REMIND_OPEN_APP = 5;
    public static final int START_ANSWER = 9;
    public static final int START_RECORDING = 2;
    private int status;

    public ChatGptStatus() {
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(@IntRange(from = 1, to = 7) int i2) {
        this.status = i2;
    }

    public ChatGptStatus(@IntRange(from = 1, to = 7) int i2) {
        this.status = i2;
    }
}
