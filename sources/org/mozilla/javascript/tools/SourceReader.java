package org.mozilla.javascript.tools;

import java.net.MalformedURLException;
import java.net.URL;

/* loaded from: classes5.dex */
public class SourceReader {
    /* JADX WARN: Removed duplicated region for block: B:57:0x00a7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.Object readFileOrUrl(java.lang.String r10, boolean r11, java.lang.String r12) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 256
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.tools.SourceReader.readFileOrUrl(java.lang.String, boolean, java.lang.String):java.lang.Object");
    }

    public static URL toUrl(String str) {
        if (str.indexOf(58) < 2) {
            return null;
        }
        try {
            return new URL(str);
        } catch (MalformedURLException unused) {
            return null;
        }
    }
}
