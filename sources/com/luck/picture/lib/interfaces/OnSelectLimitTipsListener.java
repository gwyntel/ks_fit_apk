package com.luck.picture.lib.interfaces;

import android.content.Context;
import androidx.annotation.Nullable;
import com.luck.picture.lib.config.SelectorConfig;
import com.luck.picture.lib.entity.LocalMedia;

/* loaded from: classes4.dex */
public interface OnSelectLimitTipsListener {
    boolean onSelectLimitTips(Context context, @Nullable LocalMedia localMedia, SelectorConfig selectorConfig, int i2);
}
