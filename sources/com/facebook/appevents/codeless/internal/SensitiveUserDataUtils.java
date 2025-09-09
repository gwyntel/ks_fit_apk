package com.facebook.appevents.codeless.internal;

import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;

/* loaded from: classes3.dex */
public class SensitiveUserDataUtils {
    private static boolean isCreditCard(TextView textView) {
        String strReplaceAll = ViewHierarchy.getTextOfView(textView).replaceAll("\\s", "");
        int length = strReplaceAll.length();
        if (length < 12 || length > 19) {
            return false;
        }
        int i2 = 0;
        boolean z2 = false;
        for (int i3 = length - 1; i3 >= 0; i3--) {
            char cCharAt = strReplaceAll.charAt(i3);
            if (cCharAt < '0' || cCharAt > '9') {
                return false;
            }
            int i4 = cCharAt - '0';
            if (z2 && (i4 = i4 * 2) > 9) {
                i4 = (i4 % 10) + 1;
            }
            i2 += i4;
            z2 = !z2;
        }
        return i2 % 10 == 0;
    }

    private static boolean isEmail(TextView textView) {
        if (textView.getInputType() == 32) {
            return true;
        }
        String textOfView = ViewHierarchy.getTextOfView(textView);
        if (textOfView == null || textOfView.length() == 0) {
            return false;
        }
        return Patterns.EMAIL_ADDRESS.matcher(textOfView).matches();
    }

    private static boolean isPassword(TextView textView) {
        if (textView.getInputType() == 128) {
            return true;
        }
        return textView.getTransformationMethod() instanceof PasswordTransformationMethod;
    }

    private static boolean isPersonName(TextView textView) {
        return textView.getInputType() == 96;
    }

    private static boolean isPhoneNumber(TextView textView) {
        return textView.getInputType() == 3;
    }

    private static boolean isPostalAddress(TextView textView) {
        return textView.getInputType() == 112;
    }

    public static boolean isSensitiveUserData(View view) {
        if (!(view instanceof TextView)) {
            return false;
        }
        TextView textView = (TextView) view;
        return isPassword(textView) || isCreditCard(textView) || isPersonName(textView) || isPostalAddress(textView) || isPhoneNumber(textView) || isEmail(textView);
    }
}
