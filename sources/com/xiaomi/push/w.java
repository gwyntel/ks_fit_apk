package com.xiaomi.push;

import java.io.File;
import java.util.HashMap;

/* loaded from: classes4.dex */
public class w {

    /* renamed from: a, reason: collision with root package name */
    private static final HashMap<String, String> f24640a;

    static {
        HashMap<String, String> map = new HashMap<>();
        f24640a = map;
        map.put("FFD8FF", "jpg");
        map.put("89504E47", "png");
        map.put("47494638", "gif");
        map.put("474946", "gif");
        map.put("424D", "bmp");
    }

    public static long a(File file) {
        long jA = 0;
        try {
            File[] fileArrListFiles = file.listFiles();
            for (int i2 = 0; i2 < fileArrListFiles.length; i2++) {
                jA += fileArrListFiles[i2].isDirectory() ? a(fileArrListFiles[i2]) : fileArrListFiles[i2].length();
            }
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
        }
        return jA;
    }
}
