package com.alibaba.ailabs.iot.bluetoothlesdk;

/* loaded from: classes2.dex */
public abstract /* synthetic */ class e {
    public static /* synthetic */ String a(CharSequence charSequence, CharSequence[] charSequenceArr) {
        if (charSequence == null) {
            throw new NullPointerException("delimiter");
        }
        StringBuilder sb = new StringBuilder();
        if (charSequenceArr.length > 0) {
            sb.append(charSequenceArr[0]);
            for (int i2 = 1; i2 < charSequenceArr.length; i2++) {
                sb.append(charSequence);
                sb.append(charSequenceArr[i2]);
            }
        }
        return sb.toString();
    }
}
