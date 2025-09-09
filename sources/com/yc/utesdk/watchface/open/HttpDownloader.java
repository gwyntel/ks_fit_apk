package com.yc.utesdk.watchface.open;

import com.yc.utesdk.log.LogUtils;
import com.yc.utesdk.watchface.close.FileUtil;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/* loaded from: classes4.dex */
public class HttpDownloader {
    private URL url = null;

    public static int downloadFile(String str, String str2, String str3) throws IOException {
        StringBuilder sb;
        String str4;
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            httpURLConnection.connect();
            int contentLength = httpURLConnection.getContentLength();
            System.out.println("file length---->" + contentLength);
            FileUtil fileUtil = new FileUtil();
            if (fileUtil.isFileExist(str2 + str3)) {
                fileUtil.deleteFile(str2 + str3);
            }
            BufferedInputStream bufferedInputStream = new BufferedInputStream(httpURLConnection.getInputStream());
            File file = new File(str2 + str3);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte[] bArr = new byte[1024];
            while (true) {
                int i2 = bufferedInputStream.read(bArr);
                if (i2 == -1) {
                    bufferedInputStream.close();
                    fileOutputStream.close();
                    return 0;
                }
                fileOutputStream.write(bArr, 0, i2);
            }
        } catch (MalformedURLException e2) {
            e = e2;
            e.printStackTrace();
            sb = new StringBuilder();
            str4 = "MalformedURLException =";
            sb.append(str4);
            sb.append(e);
            LogUtils.i(sb.toString());
            return -1;
        } catch (IOException e3) {
            e = e3;
            e.printStackTrace();
            sb = new StringBuilder();
            str4 = "IOException =";
            sb.append(str4);
            sb.append(e);
            LogUtils.i(sb.toString());
            return -1;
        }
    }

    public int downlaodFile(String str, String str2, String str3) throws IOException {
        InputStream inputStearmFormUrl = null;
        try {
            try {
                FileUtil fileUtil = new FileUtil();
                if (fileUtil.isFileExist(str2 + str3)) {
                    fileUtil.deleteFile(str2 + str3);
                }
                inputStearmFormUrl = getInputStearmFormUrl(str);
                if (fileUtil.write2SDFromInput(str2, str3, inputStearmFormUrl) == null) {
                    try {
                        inputStearmFormUrl.close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                    return -1;
                }
                try {
                    inputStearmFormUrl.close();
                    return 0;
                } catch (IOException e3) {
                    e3.printStackTrace();
                    return 0;
                }
            } catch (Throwable th) {
                try {
                    inputStearmFormUrl.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
                throw th;
            }
        } catch (IOException e5) {
            e5.printStackTrace();
            try {
                inputStearmFormUrl.close();
            } catch (IOException e6) {
                e6.printStackTrace();
            }
            return -1;
        }
    }

    public String download(String str) throws Throwable {
        BufferedReader bufferedReader;
        StringBuffer stringBuffer = new StringBuffer();
        BufferedReader bufferedReader2 = null;
        try {
            try {
                try {
                    URL url = new URL(str);
                    this.url = url;
                    bufferedReader = new BufferedReader(new InputStreamReader(((HttpURLConnection) url.openConnection()).getInputStream()));
                    while (true) {
                        try {
                            String line = bufferedReader.readLine();
                            if (line == null) {
                                break;
                            }
                            stringBuffer.append(line);
                        } catch (IOException e2) {
                            e = e2;
                            bufferedReader2 = bufferedReader;
                            e.printStackTrace();
                            bufferedReader2.close();
                            LogUtils.e("TAG", "下载txt文件");
                            LogUtils.e("TAG", stringBuffer.toString());
                            return stringBuffer.toString();
                        } catch (Throwable th) {
                            th = th;
                            try {
                                bufferedReader.close();
                            } catch (IOException e3) {
                                e3.printStackTrace();
                            }
                            throw th;
                        }
                    }
                    bufferedReader.close();
                } catch (Throwable th2) {
                    th = th2;
                    bufferedReader = bufferedReader2;
                }
            } catch (IOException e4) {
                e = e4;
            }
        } catch (IOException e5) {
            e5.printStackTrace();
        }
        LogUtils.e("TAG", "下载txt文件");
        LogUtils.e("TAG", stringBuffer.toString());
        return stringBuffer.toString();
    }

    public InputStream getInputStearmFormUrl(String str) {
        URL url = new URL(str);
        this.url = url;
        return ((HttpURLConnection) url.openConnection()).getInputStream();
    }
}
