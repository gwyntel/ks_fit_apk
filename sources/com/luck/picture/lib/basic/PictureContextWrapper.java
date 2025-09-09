package com.luck.picture.lib.basic;

import android.content.Context;
import android.content.ContextWrapper;
import com.luck.picture.lib.language.PictureLanguageUtils;

/* loaded from: classes4.dex */
public class PictureContextWrapper extends ContextWrapper {
    public PictureContextWrapper(Context context) {
        super(context);
    }

    public static ContextWrapper wrap(Context context, int i2, int i3) {
        if (i2 != -2) {
            PictureLanguageUtils.setAppLanguage(context, i2, i3);
        }
        return new PictureContextWrapper(context);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public Object getSystemService(String str) {
        return "audio".equals(str) ? getApplicationContext().getSystemService(str) : super.getSystemService(str);
    }
}
