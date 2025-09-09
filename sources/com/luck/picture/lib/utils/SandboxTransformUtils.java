package com.luck.picture.lib.utils;

import android.content.Context;
import android.net.Uri;
import com.luck.picture.lib.basic.PictureContentResolver;
import com.luck.picture.lib.config.PictureMimeType;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/* loaded from: classes4.dex */
public class SandboxTransformUtils {
    public static String copyPathToSandbox(Context context, String str, String str2) {
        return copyPathToSandbox(context, str, str2, "");
    }

    public static String copyPathToSandbox(Context context, String str, String str2, String str3) {
        try {
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        if (PictureMimeType.isHasHttp(str)) {
            return null;
        }
        String strCreateFilePath = PictureFileUtils.createFilePath(context, str2, str3);
        if (PictureFileUtils.writeFileFromIS(PictureMimeType.isContent(str) ? PictureContentResolver.openInputStream(context, Uri.parse(str)) : new FileInputStream(str), new FileOutputStream(strCreateFilePath))) {
            return strCreateFilePath;
        }
        return null;
    }
}
