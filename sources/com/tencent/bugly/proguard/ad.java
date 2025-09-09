package com.tencent.bugly.proguard;

import android.util.Pair;
import androidx.browser.trusted.sharing.ShareTarget;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public final class ad {
    public static Pair<Integer, String> a(List<String> list) {
        try {
            HashMap map = new HashMap();
            map.put("Atta-Type", "batch-report");
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("attaid", "0d000062340").put("token", "2273782735").put("type", "batch").put("version", "v1.0.0");
            JSONArray jSONArray = new JSONArray();
            Iterator<String> it = list.iterator();
            while (it.hasNext()) {
                jSONArray.put(it.next());
            }
            jSONObject.put("datas", jSONArray);
            return a("https://h.trace.qq.com/kv", jSONObject.toString(), map);
        } catch (Throwable th) {
            al.b(th);
            return new Pair<>(-1, th.getMessage());
        }
    }

    private static Pair<Integer, String> a(String str, String str2, Map<String, String> map) throws IOException {
        InputStream inputStream;
        DataOutputStream dataOutputStream;
        String message;
        InputStream inputStream2;
        HttpURLConnection httpURLConnection = null;
        int responseCode = -1;
        try {
            HttpURLConnection httpURLConnection2 = (HttpURLConnection) new URL(str).openConnection();
            try {
                httpURLConnection2.setRequestMethod("POST");
                httpURLConnection2.setDoOutput(true);
                httpURLConnection2.setDoInput(true);
                httpURLConnection2.setUseCaches(false);
                httpURLConnection2.setRequestProperty("Content-Type", ShareTarget.ENCODING_TYPE_URL_ENCODED);
                a(httpURLConnection2, map);
                httpURLConnection2.setConnectTimeout(5000);
                httpURLConnection2.setReadTimeout(5000);
                httpURLConnection2.connect();
                byte[] bytes = str2.getBytes("UTF-8");
                dataOutputStream = new DataOutputStream(httpURLConnection2.getOutputStream());
                try {
                    dataOutputStream.write(bytes);
                    dataOutputStream.flush();
                    dataOutputStream.close();
                    responseCode = httpURLConnection2.getResponseCode();
                    if (responseCode >= 400) {
                        inputStream2 = httpURLConnection2.getErrorStream();
                    } else {
                        inputStream2 = httpURLConnection2.getInputStream();
                    }
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream2, "UTF-8"));
                        StringBuilder sb = new StringBuilder();
                        while (true) {
                            String line = bufferedReader.readLine();
                            if (line == null) {
                                break;
                            }
                            sb.append(line);
                            sb.append(IOUtils.LINE_SEPARATOR_WINDOWS);
                        }
                        bufferedReader.close();
                        message = sb.toString();
                        a((Closeable) null);
                        a(inputStream2);
                        httpURLConnection2.disconnect();
                    } catch (Throwable th) {
                        httpURLConnection = httpURLConnection2;
                        inputStream = inputStream2;
                        th = th;
                        dataOutputStream = null;
                        try {
                            al.b(th);
                            message = th.getMessage();
                            return new Pair<>(Integer.valueOf(responseCode), message);
                        } finally {
                            a(dataOutputStream);
                            a(inputStream);
                            if (httpURLConnection != null) {
                                httpURLConnection.disconnect();
                            }
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    httpURLConnection = httpURLConnection2;
                    inputStream = null;
                }
            } catch (Throwable th3) {
                th = th3;
                dataOutputStream = null;
                httpURLConnection = httpURLConnection2;
                inputStream = null;
            }
        } catch (Throwable th4) {
            th = th4;
            inputStream = null;
            dataOutputStream = null;
        }
        return new Pair<>(Integer.valueOf(responseCode), message);
    }

    private static void a(HttpURLConnection httpURLConnection, Map<String, String> map) {
        if (httpURLConnection == null || map == null || map.isEmpty()) {
            return;
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            httpURLConnection.setRequestProperty(entry.getKey(), entry.getValue());
        }
    }

    private static void a(Closeable closeable) throws IOException {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (Exception e2) {
            al.b(e2);
        }
    }
}
