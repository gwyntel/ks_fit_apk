package org.mozilla.javascript.tools.debugger;

import java.awt.Component;
import javax.swing.JOptionPane;

/* loaded from: classes5.dex */
class MessageDialogWrapper {
    MessageDialogWrapper() {
    }

    public static void showMessageDialog(Component component, String str, String str2, int i2) {
        if (str.length() > 60) {
            StringBuilder sb = new StringBuilder();
            int length = str.length();
            int i3 = 0;
            int i4 = 0;
            while (i3 < length) {
                char cCharAt = str.charAt(i3);
                sb.append(cCharAt);
                if (Character.isWhitespace(cCharAt)) {
                    int i5 = i3 + 1;
                    while (i5 < length && !Character.isWhitespace(str.charAt(i5))) {
                        i5++;
                    }
                    if (i5 < length && (i5 - i3) + i4 > 60) {
                        sb.append('\n');
                        i4 = 0;
                    }
                }
                i3++;
                i4++;
            }
            str = sb.toString();
        }
        JOptionPane.showMessageDialog(component, str, str2, i2);
    }
}
