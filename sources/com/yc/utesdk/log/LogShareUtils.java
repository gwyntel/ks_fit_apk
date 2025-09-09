package com.yc.utesdk.log;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import androidx.core.content.FileProvider;
import androidx.webkit.internal.AssetHelper;
import com.yc.utesdk.utils.close.ZipUtils;
import com.yc.utesdk.utils.open.CalendarUtils;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
public class LogShareUtils {
    private static LogShareUtils instance;

    public LogShareUtils() {
        utendo();
    }

    public static LogShareUtils getInstance() {
        if (instance == null) {
            instance = new LogShareUtils();
        }
        return instance;
    }

    public void deleteLogFilesAll() {
        utendo(LogUtils.getRootPath());
    }

    public void deleteLogFilesSevenDayAgo() {
        utenif(LogUtils.getRootPath());
    }

    public boolean shareLog(Activity activity) throws IOException {
        String rootPath = LogUtils.getRootPath();
        if (TextUtils.isEmpty(rootPath)) {
            return false;
        }
        ZipUtils.ZipFolder(rootPath, rootPath + ".zip");
        utendo(activity, rootPath + ".zip");
        return true;
    }

    public final void utendo() {
        File file = new File(LogUtils.getLogPath());
        if (!file.exists()) {
            file.mkdirs();
        }
        deleteLogFilesSevenDayAgo();
    }

    public final boolean utenfor(String str) {
        return Pattern.compile("[0-9]*").matcher(str).matches();
    }

    public final void utenif(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        try {
            File file = new File(str);
            if (file.isDirectory()) {
                File[] fileArrListFiles = file.listFiles();
                int i2 = 0;
                while (true) {
                    Objects.requireNonNull(fileArrListFiles);
                    if (i2 >= fileArrListFiles.length) {
                        break;
                    }
                    utenif(fileArrListFiles[i2].getAbsolutePath());
                    i2++;
                }
            }
            if (file.isDirectory() || TextUtils.isEmpty(file.getName())) {
                return;
            }
            String name = file.getName();
            if (!name.endsWith(".zip")) {
                if (!name.endsWith(".log")) {
                    return;
                }
                String calendar = CalendarUtils.getCalendar(-7);
                String strSubstring = name.substring(0, name.length() - 4);
                if (!utenfor(strSubstring) || !utenfor(calendar) || Integer.parseInt(strSubstring) - Integer.parseInt(calendar) >= 0) {
                    return;
                }
            }
            file.delete();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public final void utendo(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        try {
            File file = new File(str);
            if (file.isDirectory()) {
                File[] fileArrListFiles = file.listFiles();
                int i2 = 0;
                while (true) {
                    Objects.requireNonNull(fileArrListFiles);
                    if (i2 >= fileArrListFiles.length) {
                        break;
                    }
                    utendo(fileArrListFiles[i2].getAbsolutePath());
                    i2++;
                }
            }
            if (file.isDirectory() || TextUtils.isEmpty(file.getName())) {
                return;
            }
            String name = file.getName();
            if (!name.endsWith(".zip") && !name.endsWith(".log")) {
                return;
            }
            file.delete();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public final void utendo(Activity activity, String str) {
        Uri uriFromFile;
        Intent intent = new Intent("android.intent.action.SEND");
        intent.addFlags(268435456);
        if (TextUtils.isEmpty(str)) {
            intent.setType(AssetHelper.DEFAULT_MIME_TYPE);
        } else {
            File file = new File(str);
            if (file.exists() && file.isFile()) {
                if (Build.VERSION.SDK_INT >= 24) {
                    uriFromFile = FileProvider.getUriForFile(activity, activity.getPackageName() + ".fileProvider", file);
                } else {
                    uriFromFile = Uri.fromFile(file);
                }
                intent.setType("application/x-zip-compressed");
                intent.putExtra("android.intent.extra.STREAM", uriFromFile);
            }
        }
        Intent intentCreateChooser = Intent.createChooser(intent, "MyLog");
        intentCreateChooser.addFlags(268435456);
        activity.startActivity(intentCreateChooser);
    }
}
