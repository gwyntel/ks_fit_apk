package com.xiaomi.push;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import com.xiaomi.mipush.sdk.Constants;
import com.yc.utesdk.utils.open.CalendarUtils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
class ee {

    /* renamed from: a, reason: collision with root package name */
    private static String f23644a = "/MiPushLog";

    /* renamed from: a, reason: collision with other field name */
    private int f307a;

    /* renamed from: a, reason: collision with other field name */
    private boolean f310a;

    /* renamed from: b, reason: collision with other field name */
    private String f311b;

    /* renamed from: c, reason: collision with root package name */
    private String f23646c;

    /* renamed from: a, reason: collision with other field name */
    @SuppressLint({"SimpleDateFormat"})
    private final SimpleDateFormat f308a = new SimpleDateFormat(CalendarUtils.yyyy_MM_dd_HH_mm_ss);

    /* renamed from: b, reason: collision with root package name */
    private int f23645b = 2097152;

    /* renamed from: a, reason: collision with other field name */
    private ArrayList<File> f309a = new ArrayList<>();

    ee() {
    }

    ee a(Date date, Date date2) {
        if (date.after(date2)) {
            this.f311b = this.f308a.format(date2);
            this.f23646c = this.f308a.format(date);
        } else {
            this.f311b = this.f308a.format(date);
            this.f23646c = this.f308a.format(date2);
        }
        return this;
    }

    void a(int i2) {
        if (i2 != 0) {
            this.f23645b = i2;
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    ee m304a(File file) {
        if (file.exists()) {
            this.f309a.add(file);
        }
        return this;
    }

    private void a(BufferedReader bufferedReader, BufferedWriter bufferedWriter, Pattern pattern) throws IOException {
        char[] cArr = new char[4096];
        int i2 = bufferedReader.read(cArr);
        boolean z2 = false;
        while (i2 != -1 && !z2) {
            String str = new String(cArr, 0, i2);
            Matcher matcher = pattern.matcher(str);
            int i3 = 0;
            int i4 = 0;
            while (true) {
                if (i3 >= i2 || !matcher.find(i3)) {
                    break;
                }
                int iStart = matcher.start();
                String strSubstring = str.substring(iStart, this.f311b.length() + iStart);
                if (!this.f310a) {
                    if (strSubstring.compareTo(this.f311b) >= 0) {
                        this.f310a = true;
                        i4 = iStart;
                    }
                } else if (strSubstring.compareTo(this.f23646c) > 0) {
                    z2 = true;
                    i2 = iStart;
                    break;
                }
                int iIndexOf = str.indexOf(10, iStart);
                if (iIndexOf == -1) {
                    iIndexOf = this.f311b.length();
                }
                i3 = iStart + iIndexOf;
            }
            if (this.f310a) {
                int i5 = i2 - i4;
                this.f307a += i5;
                if (z2) {
                    bufferedWriter.write(cArr, i4, i5);
                    return;
                } else {
                    bufferedWriter.write(cArr, i4, i5);
                    if (this.f307a > this.f23645b) {
                        return;
                    }
                }
            }
            i2 = bufferedReader.read(cArr);
        }
    }

    private void a(File file) throws Throwable {
        BufferedReader bufferedReader;
        Pattern patternCompile = Pattern.compile("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}");
        BufferedReader bufferedReader2 = null;
        try {
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
                try {
                    bufferedWriter.write("model :" + Build.MODEL + "; os :" + Build.VERSION.INCREMENTAL + "; uid :" + com.xiaomi.push.service.bw.m767a() + "; lng :" + Locale.getDefault().toString() + "; sdk :48; andver :" + Build.VERSION.SDK_INT + "\n");
                    this.f307a = 0;
                    Iterator<File> it = this.f309a.iterator();
                    while (it.hasNext()) {
                        bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(it.next())));
                        try {
                            a(bufferedReader, bufferedWriter, patternCompile);
                            bufferedReader.close();
                            bufferedReader2 = bufferedReader;
                        } catch (FileNotFoundException e2) {
                            e = e2;
                            bufferedReader2 = bufferedWriter;
                            com.xiaomi.channel.commonutils.logger.b.c("LOG: filter error = " + e.getMessage());
                            x.a(bufferedReader2);
                            x.a(bufferedReader);
                            return;
                        } catch (IOException e3) {
                            e = e3;
                            bufferedReader2 = bufferedWriter;
                            com.xiaomi.channel.commonutils.logger.b.c("LOG: filter error = " + e.getMessage());
                            x.a(bufferedReader2);
                            x.a(bufferedReader);
                            return;
                        } catch (Throwable th) {
                            th = th;
                            bufferedReader2 = bufferedWriter;
                            x.a(bufferedReader2);
                            x.a(bufferedReader);
                            throw th;
                        }
                    }
                    bufferedWriter.write(dd.a().c());
                    x.a(bufferedWriter);
                    x.a(bufferedReader2);
                } catch (FileNotFoundException e4) {
                    e = e4;
                    bufferedReader = bufferedReader2;
                } catch (IOException e5) {
                    e = e5;
                    bufferedReader = bufferedReader2;
                } catch (Throwable th2) {
                    th = th2;
                    bufferedReader = bufferedReader2;
                }
            } catch (FileNotFoundException e6) {
                e = e6;
                bufferedReader = null;
            } catch (IOException e7) {
                e = e7;
                bufferedReader = null;
            } catch (Throwable th3) {
                th = th3;
                bufferedReader = null;
            }
        } catch (Throwable th4) {
            th = th4;
        }
    }

    File a(Context context, Date date, Date date2, File file) throws Throwable {
        File file2;
        if ("com.xiaomi.xmsf".equalsIgnoreCase(context.getPackageName())) {
            file2 = new File(context.getExternalFilesDir(null), com.xiaomi.push.service.bj.R);
            if (!file2.exists()) {
                file2 = new File(context.getFilesDir(), com.xiaomi.push.service.bj.R);
            }
            if (!file2.exists()) {
                file2 = context.getFilesDir();
            }
            m304a(new File(file2, "xmsf.log.1"));
            m304a(new File(file2, "xmsf.log"));
        } else {
            file2 = new File(context.getExternalFilesDir(null) + f23644a);
            m304a(new File(file2, "log0.txt"));
            m304a(new File(file2, "log1.txt"));
        }
        if (!file2.isDirectory()) {
            return null;
        }
        File file3 = new File(file, date.getTime() + Constants.ACCEPT_TIME_SEPARATOR_SERVER + date2.getTime() + ".zip");
        if (file3.exists()) {
            return null;
        }
        a(date, date2);
        long jCurrentTimeMillis = System.currentTimeMillis();
        File file4 = new File(file, "log.txt");
        a(file4);
        com.xiaomi.channel.commonutils.logger.b.c("LOG: filter cost = " + (System.currentTimeMillis() - jCurrentTimeMillis));
        if (file4.exists()) {
            long jCurrentTimeMillis2 = System.currentTimeMillis();
            x.a(file3, file4);
            com.xiaomi.channel.commonutils.logger.b.c("LOG: zip cost = " + (System.currentTimeMillis() - jCurrentTimeMillis2));
            file4.delete();
            if (file3.exists()) {
                return file3;
            }
        }
        return null;
    }
}
