package com.leeson.image_pickers;

import android.content.Context;
import java.io.File;
import java.io.IOException;

/* loaded from: classes4.dex */
public class AppPath {
    private Context context;
    private String packageName;

    public AppPath(Context context) {
        this.context = context;
        String packageName = context.getPackageName();
        this.packageName = packageName;
        this.packageName = packageName.substring(packageName.lastIndexOf(".") + 1);
    }

    private void createNomedia(String str) throws IOException {
        File file = new File(str, ".nomedia");
        if (file.exists()) {
            return;
        }
        try {
            file.createNewFile();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public String getAppAudioDirPath() {
        File externalFilesDir = this.context.getExternalFilesDir("Audiobooks");
        return externalFilesDir != null ? externalFilesDir.getAbsolutePath() : this.context.getFilesDir().getAbsolutePath();
    }

    public String getAppDCIMDirPath() {
        File externalFilesDir = this.context.getExternalFilesDir("DCIM");
        return externalFilesDir != null ? externalFilesDir.getAbsolutePath() : this.context.getFilesDir().getAbsolutePath();
    }

    public String getAppDirPath() {
        File externalFilesDir = this.context.getExternalFilesDir(null);
        return externalFilesDir != null ? externalFilesDir.getAbsolutePath() : this.context.getFilesDir().getAbsolutePath();
    }

    public String getAppDocumentsDirPath() {
        File externalFilesDir = this.context.getExternalFilesDir("Documents");
        return externalFilesDir != null ? externalFilesDir.getAbsolutePath() : this.context.getFilesDir().getAbsolutePath();
    }

    public String getAppDownloadDirPath() {
        File externalFilesDir = this.context.getExternalFilesDir("Download");
        return externalFilesDir != null ? externalFilesDir.getAbsolutePath() : this.context.getFilesDir().getPath();
    }

    public String getAppImgDirPath() throws IOException {
        File externalFilesDir = this.context.getExternalFilesDir("Pictures");
        if (externalFilesDir == null) {
            return this.context.getFilesDir().getAbsolutePath();
        }
        createNomedia(externalFilesDir.getAbsolutePath());
        return externalFilesDir.getAbsolutePath();
    }

    public String getAppLogDirPath() {
        return getAppDocumentsDirPath() + "/logs/";
    }

    public String getAppMusicDirPath() {
        File externalFilesDir = this.context.getExternalFilesDir("Music");
        return externalFilesDir != null ? externalFilesDir.getAbsolutePath() : this.context.getFilesDir().getAbsolutePath();
    }

    public String getAppVideoDirPath() throws IOException {
        File externalFilesDir = this.context.getExternalFilesDir("Movies");
        if (externalFilesDir == null) {
            return this.context.getFilesDir().getAbsolutePath();
        }
        createNomedia(externalFilesDir.getAbsolutePath());
        return externalFilesDir.getAbsolutePath();
    }

    public String getPackageName() {
        return this.packageName;
    }
}
