package com.meizu.cloud.pushsdk.d;

import android.util.Log;
import com.yc.utesdk.utils.open.CalendarUtils;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import org.apache.commons.io.IOUtils;

/* loaded from: classes4.dex */
class e {

    /* renamed from: a, reason: collision with root package name */
    private final SimpleDateFormat f19217a = new SimpleDateFormat(CalendarUtils.yyyy_MM_dd);

    /* renamed from: b, reason: collision with root package name */
    private final d f19218b = new d("lo");

    /* renamed from: c, reason: collision with root package name */
    private BufferedWriter f19219c;

    class a implements FileFilter {
        a() {
        }

        @Override // java.io.FileFilter
        public boolean accept(File file) {
            return file.getName().endsWith(".log.txt");
        }
    }

    class b implements Comparator<File> {
        b() {
        }

        @Override // java.util.Comparator
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public int compare(File file, File file2) {
            long jLastModified = file.lastModified() - file2.lastModified();
            if (jLastModified > 0) {
                return -1;
            }
            return jLastModified == 0 ? 0 : 1;
        }
    }

    public void a() throws IOException {
        BufferedWriter bufferedWriter = this.f19219c;
        if (bufferedWriter != null) {
            bufferedWriter.flush();
            this.f19219c.close();
            this.f19219c = null;
        }
    }

    void a(File file) {
        File[] fileArrListFiles = file.listFiles(new a());
        if (fileArrListFiles != null) {
            if (fileArrListFiles.length > 7) {
                Arrays.sort(fileArrListFiles, new b());
                for (int i2 = 7; i2 < fileArrListFiles.length; i2++) {
                    fileArrListFiles[i2].delete();
                }
            }
        }
    }

    public void a(String str) throws IOException {
        File file = new File(str);
        if (!file.exists() && !file.mkdirs()) {
            throw new IOException("create " + str + " dir failed!!!");
        }
        String str2 = this.f19217a.format(new Date());
        File file2 = new File(str, str2 + ".log.txt");
        if (!file2.exists()) {
            if (file2.createNewFile()) {
                a(file);
            } else {
                Log.e("EncryptionWriter", "create new file " + str2 + " failed !!!");
            }
        }
        this.f19219c = new BufferedWriter(new FileWriter(file2, true));
    }

    public void a(String str, String str2, String str3) throws IOException {
        if (this.f19219c != null) {
            this.f19219c.write(this.f19218b.a((str + str2 + " " + str3).getBytes()));
            this.f19219c.write(IOUtils.LINE_SEPARATOR_WINDOWS);
        }
    }
}
