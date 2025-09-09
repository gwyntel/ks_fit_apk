package com.yc.utesdk.utils.close;

import android.text.TextUtils;
import com.google.common.net.HttpHeaders;
import com.huawei.hms.framework.common.ContainerUtils;
import com.yc.utesdk.log.LogUtils;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/* loaded from: classes4.dex */
public class HttpUtil {
    private static final String BOUNDARY = UUID.randomUUID().toString();
    private static final String LINE_END = "\r\n";
    private static String TAG = "UteHttpUtil";
    private static final String TWO_HYPHENS = "--";

    public static String doPost(String str, Map<String, String> map) {
        return utendo(str, map, (String) null, (Map) null);
    }

    public static String doPostJson(String str, String str2) {
        return utendo(str, (Map) null, str2, (Map) null);
    }

    public static String uploadFile(String str, File file, String str2, String str3, Map<String, String> map, Map<String, String> map2) {
        return utendo(str, file, null, null, str2, str3, map, map2);
    }

    public static String utendo(File file, String str, String str2) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("\r\n");
        stringBuffer.append(TWO_HYPHENS);
        stringBuffer.append(BOUNDARY);
        stringBuffer.append("\r\n");
        stringBuffer.append("Content-Disposition: form-data; name=\"" + str + "\"; filename=\"" + file.getName() + "\"");
        stringBuffer.append("\r\n");
        StringBuilder sb = new StringBuilder();
        sb.append("Content-Type: ");
        sb.append(str2);
        stringBuffer.append(sb.toString());
        stringBuffer.append("\r\n");
        stringBuffer.append("Content-Lenght: " + file.length());
        stringBuffer.append("\r\n");
        stringBuffer.append("\r\n");
        return stringBuffer.toString();
    }

    public static String utenif(Map map) {
        StringBuilder sb = new StringBuilder();
        try {
            boolean z2 = true;
            for (Map.Entry entry : map.entrySet()) {
                if (z2) {
                    z2 = false;
                } else {
                    sb.append("&");
                }
                sb.append(URLEncoder.encode((String) entry.getKey(), "UTF-8"));
                sb.append(ContainerUtils.KEY_VALUE_DELIMITER);
                sb.append(URLEncoder.encode((String) entry.getValue(), "UTF-8"));
            }
            return sb.toString();
        } catch (UnsupportedEncodingException unused) {
            return null;
        }
    }

    public static String doPost(String str, Map<String, String> map, Map<String, String> map2) {
        return utendo(str, map, (String) null, map2);
    }

    public static String doPostJson(String str, String str2, Map<String, String> map) {
        return utendo(str, (Map) null, str2, map);
    }

    public static String uploadFile(String str, List<File> list, String str2, String str3, Map<String, String> map, Map<String, String> map2) {
        return utendo(str, null, list, null, str2, str3, map, map2);
    }

    public static String utendo(Map map) {
        StringBuffer stringBuffer = new StringBuffer();
        for (String str : map.keySet()) {
            stringBuffer.append(TWO_HYPHENS);
            stringBuffer.append(BOUNDARY);
            stringBuffer.append("\r\n");
            stringBuffer.append("Content-Disposition: form-data; name=\"" + str + "\"");
            stringBuffer.append("\r\n");
            stringBuffer.append("Content-Type: text/plain");
            stringBuffer.append("\r\n");
            stringBuffer.append("Content-Lenght: " + ((String) map.get(str)).length());
            stringBuffer.append("\r\n");
            stringBuffer.append("\r\n");
            stringBuffer.append((String) map.get(str));
            stringBuffer.append("\r\n");
        }
        return stringBuffer.toString();
    }

    public static String uploadFile(String str, Map<String, File> map, String str2, String str3, Map<String, String> map2, Map<String, String> map3) {
        return utendo(str, null, null, map, str2, str3, map2, map3);
    }

    public static String utendo(Map map, String str) {
        if (map != null) {
            return utenif(map);
        }
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return str;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:47:0x012b  */
    /* JADX WARN: Type inference failed for: r4v2 */
    /* JADX WARN: Type inference failed for: r4v3, types: [java.net.HttpURLConnection] */
    /* JADX WARN: Type inference failed for: r4v4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String utendo(java.lang.String r2, java.util.Map r3, java.lang.String r4, java.util.Map r5) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 303
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yc.utesdk.utils.close.HttpUtil.utendo(java.lang.String, java.util.Map, java.lang.String, java.util.Map):java.lang.String");
    }

    public static void utendo(HttpURLConnection httpURLConnection, Map map) {
        if (map != null) {
            for (String str : map.keySet()) {
                httpURLConnection.setRequestProperty(str, (String) map.get(str));
            }
        }
    }

    public static String utendo(String str, File file, List list, Map map, String str2, String str3, Map map2, Map map3) throws IOException {
        LogUtils.i(TAG, "uploadFile requestURL=" + str + ",file=" + file + ",fileKey=" + str2 + ",fileType=" + str3 + ",paramsMap=" + map2);
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setConnectTimeout(50000);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestProperty("Connection", HttpHeaders.KEEP_ALIVE);
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data; BOUNDARY=" + BOUNDARY);
            if (map3 != null) {
                utendo(httpURLConnection, map3);
            }
            httpURLConnection.connect();
            DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
            if (map2 != null) {
                dataOutputStream.write(utendo(map2).getBytes());
                dataOutputStream.flush();
            }
            if (file != null) {
                utendo(file, str2, str3, dataOutputStream);
            } else if (list != null) {
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    utendo((File) it.next(), str2, str3, dataOutputStream);
                }
            } else if (map != null) {
                for (String str4 : map.keySet()) {
                    utendo((File) map.get(str4), str4, str3, dataOutputStream);
                }
            }
            dataOutputStream.write(("\r\n--" + BOUNDARY + TWO_HYPHENS + "\r\n").getBytes());
            dataOutputStream.flush();
            int responseCode = httpURLConnection.getResponseCode();
            LogUtils.i(TAG, "uploadFile RETURN code=" + responseCode);
            if (responseCode == 200) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                while (true) {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        String string = sb.toString();
                        LogUtils.i(TAG, "uploadFile RETURN DATA=" + string);
                        return string;
                    }
                    sb.append(line);
                }
            }
        } catch (Exception unused) {
        }
        return null;
    }

    public static void utendo(File file, String str, String str2, DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.write(utendo(file, str, str2).getBytes());
        dataOutputStream.flush();
        FileInputStream fileInputStream = new FileInputStream(file);
        file.length();
        byte[] bArr = new byte[2048];
        while (true) {
            int i2 = fileInputStream.read(bArr);
            if (i2 == -1) {
                dataOutputStream.flush();
                fileInputStream.close();
                return;
            }
            dataOutputStream.write(bArr, 0, i2);
        }
    }
}
