package com.xiaomi.account.auth;

import android.net.Uri;
import android.text.TextUtils;
import com.xiaomi.account.utils.Base64Coder;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

/* loaded from: classes4.dex */
public class AuthorizeHelper {
    private static final String HMAC_SHA1 = "HmacSHA1";
    private static final String UTF8 = "UTF-8";
    private static Random random = new Random();

    protected static HashMap<String, String> buildMacRequestHead(String str, String str2, String str3) throws UnsupportedEncodingException {
        String str4 = String.format("MAC access_token=\"%s\", nonce=\"%s\",mac=\"%s\"", URLEncoder.encode(str, "UTF-8"), URLEncoder.encode(str2, "UTF-8"), URLEncoder.encode(str3, "UTF-8"));
        HashMap<String, String> map = new HashMap<>();
        map.put("Authorization", str4);
        return map;
    }

    protected static String encodeSign(byte[] bArr) {
        return new String(Base64Coder.encode(bArr));
    }

    protected static byte[] encryptHMACSha1(byte[] bArr, byte[] bArr2) throws IllegalStateException, NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(bArr2, "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(secretKeySpec);
        mac.update(bArr);
        return mac.doFinal();
    }

    protected static String generateNonce() {
        return random.nextLong() + ":" + ((int) (System.currentTimeMillis() / 60000));
    }

    protected static String generateUrl(String str, List<NameValuePair> list) {
        if (list == null || list.size() <= 0) {
            return str;
        }
        Uri.Builder builderBuildUpon = Uri.parse(str).buildUpon();
        for (NameValuePair nameValuePair : list) {
            builderBuildUpon.appendQueryParameter(nameValuePair.getName(), nameValuePair.getValue());
        }
        return builderBuildUpon.build().toString();
    }

    protected static String getMacAccessTokenSignatureString(String str, String str2, String str3, String str4, String str5, String str6, String str7) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        if (!"HmacSHA1".equalsIgnoreCase(str7)) {
            throw new NoSuchAlgorithmException("error mac algorithm : " + str7);
        }
        StringBuilder sb = new StringBuilder("");
        sb.append(str + "\n");
        sb.append(str2.toUpperCase() + "\n");
        sb.append(str3 + "\n");
        sb.append(str4 + "\n");
        if (!TextUtils.isEmpty(str5)) {
            StringBuffer stringBuffer = new StringBuffer();
            ArrayList arrayList = new ArrayList();
            URLEncodedUtils.parse(arrayList, new Scanner(str5), "UTF-8");
            Collections.sort(arrayList, new Comparator<NameValuePair>() { // from class: com.xiaomi.account.auth.AuthorizeHelper.1
                @Override // java.util.Comparator
                public int compare(NameValuePair nameValuePair, NameValuePair nameValuePair2) {
                    return nameValuePair.getName().compareTo(nameValuePair2.getName());
                }
            });
            stringBuffer.append(URLEncodedUtils.format(arrayList, "UTF-8"));
            sb.append(stringBuffer.toString() + "\n");
        }
        return encodeSign(encryptHMACSha1(sb.toString().getBytes("UTF-8"), str6.getBytes("UTF-8")));
    }
}
