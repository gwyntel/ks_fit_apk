package com.yc.utesdk.bean;

import android.support.v4.media.MediaDescriptionCompat;
import androidx.annotation.IntRange;

/* loaded from: classes4.dex */
public class LargeModeType {
    public static final int large_mode_deepseek = 1;
    public static final int large_mode_doubao = 0;
    public static final int large_mode_kimi = 6;
    public static final int large_mode_qianwen = 4;
    public static final int large_mode_xinghuo = 5;
    public static final int large_mode_yiyan = 3;
    public static final int large_mode_zhipu = 2;
    private int status;

    public LargeModeType() {
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(@IntRange(from = 0, to = MediaDescriptionCompat.BT_FOLDER_TYPE_YEARS) int i2) {
        this.status = i2;
    }

    public LargeModeType(@IntRange(from = 0, to = MediaDescriptionCompat.BT_FOLDER_TYPE_YEARS) int i2) {
        this.status = i2;
    }
}
