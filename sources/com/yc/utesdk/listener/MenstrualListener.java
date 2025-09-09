package com.yc.utesdk.listener;

import com.yc.utesdk.bean.MenstrualParam;
import java.util.List;

/* loaded from: classes4.dex */
public interface MenstrualListener {
    void onMenstrualMonthData(boolean z2, List<Integer> list);

    void onMenstrualParam(boolean z2, MenstrualParam menstrualParam);
}
