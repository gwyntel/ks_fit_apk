package com.alibaba.sdk.android.openaccount.ui.helper;

import java.util.regex.Pattern;

/* loaded from: classes2.dex */
public class NumberCheckFactory implements ICheckListener {
    @Override // com.alibaba.sdk.android.openaccount.ui.helper.ICheckListener
    public int onCheck(String str) {
        int i2 = 0;
        while (Pattern.compile("[1-9]").matcher(str).find()) {
            i2++;
        }
        if (i2 <= 0) {
            return 0;
        }
        return i2;
    }
}
