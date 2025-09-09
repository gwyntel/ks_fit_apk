package com.alipay.sdk.m.c0;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public final class d {

    /* renamed from: a, reason: collision with root package name */
    public static String f9221a = "";

    /* renamed from: b, reason: collision with root package name */
    public static String f9222b = "";

    /* renamed from: c, reason: collision with root package name */
    public static String f9223c = "";

    public static synchronized void a(String str) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(str);
        a(arrayList);
    }

    public static synchronized void a(String str, String str2, String str3) {
        f9221a = str;
        f9222b = str2;
        f9223c = str3;
    }

    public static synchronized void a(Throwable th) {
        String string;
        try {
            ArrayList arrayList = new ArrayList();
            if (th != null) {
                StringWriter stringWriter = new StringWriter();
                th.printStackTrace(new PrintWriter(stringWriter));
                string = stringWriter.toString();
            } else {
                string = "";
            }
            arrayList.add(string);
            a(arrayList);
        } catch (Throwable th2) {
            throw th2;
        }
    }

    public static synchronized void a(List<String> list) {
        try {
            if (!com.alipay.sdk.m.z.a.a(f9222b) && !com.alipay.sdk.m.z.a.a(f9223c)) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(f9223c);
                Iterator<String> it = list.iterator();
                while (it.hasNext()) {
                    stringBuffer.append(", " + it.next());
                }
                stringBuffer.append("\n");
                try {
                    File file = new File(f9221a);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    File file2 = new File(f9221a, f9222b);
                    if (!file2.exists()) {
                        file2.createNewFile();
                    }
                    FileWriter fileWriter = ((long) stringBuffer.length()) + file2.length() <= 51200 ? new FileWriter(file2, true) : new FileWriter(file2);
                    fileWriter.write(stringBuffer.toString());
                    fileWriter.flush();
                    fileWriter.close();
                } catch (Exception unused) {
                }
            }
        } finally {
        }
    }
}
