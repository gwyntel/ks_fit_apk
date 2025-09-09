package com.huawei.hms.common.internal;

import com.huawei.hms.support.log.HMSLog;
import com.huawei.hms.utils.StringUtil;
import com.yc.utesdk.utils.close.AESUtils;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* loaded from: classes4.dex */
public class TransactionIdCreater {
    private static SecureRandom a() {
        try {
            return SecureRandom.getInstance(AESUtils.RNG_ALGORITHM);
        } catch (Exception unused) {
            HMSLog.e("TransactionIdCreater", "SecureRandom getInstance happpened NoSuchAlgorithmException!");
            return new SecureRandom();
        }
    }

    public static String getId(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(StringUtil.addByteForNum(str, 9, '0'));
        sb.append(StringUtil.addByteForNum(str2, 6, '0'));
        Locale locale = Locale.ENGLISH;
        sb.append(new SimpleDateFormat("yyyyMMddHHmmssSSS", locale).format(new Date()));
        sb.append(String.format(locale, "%06d", Integer.valueOf(a().nextInt(1000000))));
        return sb.toString();
    }
}
