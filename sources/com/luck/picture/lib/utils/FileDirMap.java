package com.luck.picture.lib.utils;

import android.content.Context;
import android.os.Environment;
import java.io.File;
import java.util.HashMap;

/* loaded from: classes4.dex */
public final class FileDirMap {
    private static final HashMap<Integer, String> dirMap = new HashMap<>();

    public static void clear() {
        dirMap.clear();
    }

    public static String getFileDirPath(Context context, int i2) {
        HashMap<Integer, String> map = dirMap;
        String str = map.get(Integer.valueOf(i2));
        if (str != null) {
            return str;
        }
        init(context);
        return map.get(Integer.valueOf(i2));
    }

    public static void init(Context context) {
        if (ActivityCompatHelper.assertValidRequest(context)) {
            HashMap<Integer, String> map = dirMap;
            if (map.get(1) == null) {
                File externalFilesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                map.put(1, (externalFilesDir == null || !externalFilesDir.exists()) ? context.getCacheDir().getPath() : externalFilesDir.getPath());
            }
            if (map.get(2) == null) {
                File externalFilesDir2 = context.getExternalFilesDir(Environment.DIRECTORY_MOVIES);
                map.put(2, (externalFilesDir2 == null || !externalFilesDir2.exists()) ? context.getCacheDir().getPath() : externalFilesDir2.getPath());
            }
            if (map.get(3) == null) {
                File externalFilesDir3 = context.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
                map.put(3, (externalFilesDir3 == null || !externalFilesDir3.exists()) ? context.getCacheDir().getPath() : externalFilesDir3.getPath());
            }
        }
    }
}
