package com.yc.utesdk.listener;

import androidx.annotation.NonNull;
import com.yc.utesdk.bean.BaseResult;

/* loaded from: classes4.dex */
public interface NetBaseListener<T extends BaseResult> {
    void failed(@NonNull BaseResult baseResult);

    void success(@NonNull T t2);
}
