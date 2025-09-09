package com.umeng.commonsdk.internal.crash;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/* loaded from: classes4.dex */
public class a {
    public static String a(Throwable th) throws IOException {
        String string = null;
        if (th == null) {
            return null;
        }
        try {
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            th.printStackTrace(printWriter);
            for (Throwable cause = th.getCause(); cause != null; cause = cause.getCause()) {
                cause.printStackTrace(printWriter);
            }
            string = stringWriter.toString();
            printWriter.close();
            stringWriter.close();
            return string;
        } catch (Exception unused) {
            return string;
        }
    }
}
