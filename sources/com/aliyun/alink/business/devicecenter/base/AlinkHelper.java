package com.aliyun.alink.business.devicecenter.base;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import com.aliyun.alink.business.devicecenter.api.add.LinkType;
import com.aliyun.alink.business.devicecenter.api.config.ProvisionConfigCenter;
import com.aliyun.alink.business.devicecenter.api.config.ProvisionConfigParams;
import com.aliyun.alink.business.devicecenter.config.IDataCallback;
import com.aliyun.alink.business.devicecenter.config.model.DCAlibabaConfigParams;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.utils.ReflectUtils;
import com.aliyun.alink.business.devicecenter.utils.StringUtils;
import com.aliyun.alink.linksdk.logextra.upload.Log2Cloud;
import com.aliyun.alink.linksdk.logextra.upload.OSSManager;
import java.util.List;

/* loaded from: classes2.dex */
public class AlinkHelper {
    public static WifiInfo a(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
        if (wifiManager == null) {
            return null;
        }
        return wifiManager.getConnectionInfo();
    }

    public static String getHalfMacFromMac(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        if (!str.contains(":")) {
            return null;
        }
        String[] strArrSplit = str.split(":");
        if (strArrSplit.length != 6) {
            return null;
        }
        return (strArrSplit[3] + strArrSplit[4] + strArrSplit[5]).toUpperCase();
    }

    public static String getMacFromAp(String str) {
        if (!isValidSoftAp(str, true)) {
            return null;
        }
        String[] strArrSplit = str.split(OpenAccountUIConstants.UNDER_LINE);
        if (strArrSplit.length == 3) {
            return strArrSplit[2];
        }
        if (strArrSplit.length == 2 && str.startsWith(AlinkConstants.MOCK_AP_SSID_PREFIX) && strArrSplit[1].length() >= 4) {
            return a(strArrSplit[1].substring(0, 4));
        }
        return null;
    }

    public static String getMacFromSimpleMac(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        int length = str.length();
        if (length != 12 || str.contains(":")) {
            ALog.w("AlinkHelper", "invalid simple mac");
            return str;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 0; i2 < length; i2 += 2) {
            stringBuffer.append(str.charAt(i2));
            int i3 = i2 + 1;
            stringBuffer.append(str.charAt(i3));
            if (i3 != length - 1) {
                stringBuffer.append(":");
            }
        }
        return stringBuffer.toString();
    }

    public static String getPkFromAp(String str) {
        if (!isValidSoftAp(str, true)) {
            return null;
        }
        String[] strArrSplit = str.split(OpenAccountUIConstants.UNDER_LINE);
        if (strArrSplit.length == 3) {
            return strArrSplit[1];
        }
        if (strArrSplit.length == 2 && str.startsWith(AlinkConstants.MOCK_AP_SSID_PREFIX)) {
            return strArrSplit[0].substring(3);
        }
        return null;
    }

    public static String getUppercaseMacFromSimpleMac(String str) {
        String macFromSimpleMac = getMacFromSimpleMac(str);
        if (TextUtils.isEmpty(macFromSimpleMac)) {
            return null;
        }
        return macFromSimpleMac.toUpperCase();
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x004a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getWifiSsid(android.content.Context r6) {
        /*
            java.lang.String r0 = "UTF-8"
            java.lang.String r1 = "\""
            android.net.wifi.WifiInfo r2 = a(r6)
            java.lang.String r3 = ""
            if (r2 != 0) goto Ld
            return r3
        Ld:
            java.lang.String r4 = new java.lang.String     // Catch: java.lang.Exception -> L35 java.io.UnsupportedEncodingException -> L37
            java.lang.String r2 = r2.getSSID()     // Catch: java.lang.Exception -> L35 java.io.UnsupportedEncodingException -> L37
            java.lang.String r2 = r2.replace(r1, r3)     // Catch: java.lang.Exception -> L35 java.io.UnsupportedEncodingException -> L37
            byte[] r2 = r2.getBytes()     // Catch: java.lang.Exception -> L35 java.io.UnsupportedEncodingException -> L37
            r4.<init>(r2, r0)     // Catch: java.lang.Exception -> L35 java.io.UnsupportedEncodingException -> L37
            java.lang.String r2 = "<unknown ssid>"
            boolean r2 = r4.equals(r2)     // Catch: java.lang.Exception -> L2f java.io.UnsupportedEncodingException -> L31
            if (r2 != 0) goto L33
            java.lang.String r2 = "0x"
            boolean r2 = r4.equals(r2)     // Catch: java.lang.Exception -> L2f java.io.UnsupportedEncodingException -> L31
            if (r2 == 0) goto L42
            goto L33
        L2f:
            r2 = move-exception
            goto L3a
        L31:
            r2 = move-exception
            goto L3f
        L33:
            r4 = r3
            goto L42
        L35:
            r2 = move-exception
            goto L39
        L37:
            r2 = move-exception
            goto L3e
        L39:
            r4 = r3
        L3a:
            r2.printStackTrace()
            goto L42
        L3e:
            r4 = r3
        L3f:
            r2.printStackTrace()
        L42:
            boolean r2 = android.text.TextUtils.isEmpty(r4)
            java.lang.String r5 = "AlinkHelper"
            if (r2 == 0) goto L7f
            java.lang.String r2 = "getWifiSsid(),try CONNECTIVITY_SERVICE"
            com.aliyun.alink.business.devicecenter.log.ALog.d(r5, r2)
            java.lang.String r2 = "connectivity"
            java.lang.Object r6 = r6.getSystemService(r2)
            android.net.ConnectivityManager r6 = (android.net.ConnectivityManager) r6
            if (r6 == 0) goto L5f
            r2 = 1
            android.net.NetworkInfo r6 = r6.getNetworkInfo(r2)
            goto L60
        L5f:
            r6 = 0
        L60:
            if (r6 == 0) goto L7f
            java.lang.String r2 = r6.getExtraInfo()
            if (r2 == 0) goto L7f
            java.lang.String r2 = new java.lang.String     // Catch: java.lang.Exception -> L7b
            java.lang.String r6 = r6.getExtraInfo()     // Catch: java.lang.Exception -> L7b
            java.lang.String r6 = r6.replace(r1, r3)     // Catch: java.lang.Exception -> L7b
            byte[] r6 = r6.getBytes()     // Catch: java.lang.Exception -> L7b
            r2.<init>(r6, r0)     // Catch: java.lang.Exception -> L7b
            r4 = r2
            goto L7f
        L7b:
            r6 = move-exception
            r6.printStackTrace()
        L7f:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r0 = "getWifiSsid(), result ssid = "
            r6.append(r0)
            r6.append(r4)
            java.lang.String r6 = r6.toString()
            com.aliyun.alink.business.devicecenter.log.ALog.d(r5, r6)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aliyun.alink.business.devicecenter.base.AlinkHelper.getWifiSsid(android.content.Context):java.lang.String");
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0030  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean isBatch(com.aliyun.alink.business.devicecenter.config.model.DCAlibabaConfigParams r4) {
        /*
            r0 = 0
            java.lang.String r1 = "AlinkHelper"
            if (r4 == 0) goto L46
            com.aliyun.alink.business.devicecenter.api.add.LinkType r2 = r4.linkType     // Catch: java.lang.Exception -> L2e
            if (r2 == 0) goto L46
            com.aliyun.alink.business.devicecenter.api.add.LinkType r2 = com.aliyun.alink.business.devicecenter.api.add.LinkType.ALI_BROADCAST_IN_BATCHES     // Catch: java.lang.Exception -> L2e
            java.lang.String r2 = r2.getName()     // Catch: java.lang.Exception -> L2e
            com.aliyun.alink.business.devicecenter.api.add.LinkType r3 = r4.linkType     // Catch: java.lang.Exception -> L2e
            java.lang.String r3 = r3.getName()     // Catch: java.lang.Exception -> L2e
            boolean r2 = r2.equals(r3)     // Catch: java.lang.Exception -> L2e
            if (r2 != 0) goto L30
            com.aliyun.alink.business.devicecenter.api.add.LinkType r2 = com.aliyun.alink.business.devicecenter.api.add.LinkType.ALI_ZERO_IN_BATCHES     // Catch: java.lang.Exception -> L2e
            java.lang.String r2 = r2.getName()     // Catch: java.lang.Exception -> L2e
            com.aliyun.alink.business.devicecenter.api.add.LinkType r4 = r4.linkType     // Catch: java.lang.Exception -> L2e
            java.lang.String r4 = r4.getName()     // Catch: java.lang.Exception -> L2e
            boolean r4 = r2.equals(r4)     // Catch: java.lang.Exception -> L2e
            if (r4 == 0) goto L46
            goto L30
        L2e:
            r4 = move-exception
            goto L32
        L30:
            r0 = 1
            goto L46
        L32:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "unfkg believable e="
            r2.append(r3)
            r2.append(r4)
            java.lang.String r4 = r2.toString()
            com.aliyun.alink.business.devicecenter.log.ALog.d(r1, r4)
        L46:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r2 = "isBatch="
            r4.append(r2)
            r4.append(r0)
            java.lang.String r4 = r4.toString()
            com.aliyun.alink.business.devicecenter.log.ALog.d(r1, r4)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aliyun.alink.business.devicecenter.base.AlinkHelper.isBatch(com.aliyun.alink.business.devicecenter.config.model.DCAlibabaConfigParams):boolean");
    }

    public static boolean isBatchBroadcast(DCAlibabaConfigParams dCAlibabaConfigParams) {
        boolean z2 = false;
        if (dCAlibabaConfigParams != null) {
            try {
                if (dCAlibabaConfigParams.linkType != null) {
                    if (LinkType.ALI_BROADCAST_IN_BATCHES.getName().equals(dCAlibabaConfigParams.linkType.getName())) {
                        z2 = true;
                    }
                }
            } catch (Exception e2) {
                ALog.d("AlinkHelper", "unfkg believable e=" + e2);
            }
        }
        ALog.d("AlinkHelper", "isBatchBroadcast=" + z2);
        return z2;
    }

    public static boolean isBatchZero(DCAlibabaConfigParams dCAlibabaConfigParams) {
        boolean z2 = false;
        if (dCAlibabaConfigParams != null) {
            try {
                if (dCAlibabaConfigParams.linkType != null) {
                    if (LinkType.ALI_ZERO_IN_BATCHES.getName().equals(dCAlibabaConfigParams.linkType.getName())) {
                        z2 = true;
                    }
                }
            } catch (Exception e2) {
                ALog.d("AlinkHelper", "unfkg believable e=" + e2);
            }
        }
        ALog.d("AlinkHelper", "isBatchZero=" + z2);
        return z2;
    }

    public static boolean isValidSoftAp(String str, boolean z2) {
        List<String> list;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        ProvisionConfigParams provisionConfigParams = ProvisionConfigCenter.getInstance().getProvisionConfigParams();
        if (provisionConfigParams == null || (list = provisionConfigParams.deviceApPrefixList) == null || list.isEmpty()) {
            return str.startsWith(AlinkConstants.SOFT_AP_SSID_PREFIX) || (z2 && str.startsWith(AlinkConstants.MOCK_AP_SSID_PREFIX));
        }
        int size = provisionConfigParams.deviceApPrefixList.size();
        for (int i2 = 0; i2 < size; i2++) {
            String str2 = provisionConfigParams.deviceApPrefixList.get(i2);
            if (!TextUtils.isEmpty(str2) && str.startsWith(str2)) {
                return true;
            }
        }
        return false;
    }

    public static byte[] sixBitsToEightBits(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return null;
        }
        long j2 = 0;
        for (int i2 = 0; i2 < bArr.length; i2++) {
            j2 |= ((bArr[i2] - 32) & 63) << (i2 * 6);
        }
        int length = (bArr.length * 6) / 8;
        byte[] bArr2 = new byte[length];
        for (int i3 = 0; i3 < length; i3++) {
            bArr2[i3] = (byte) (((4294967295L & j2) >> (i3 * 8)) & 255);
        }
        return bArr2;
    }

    public static void uploadData2Oss(byte[] bArr, final IDataCallback<String> iDataCallback) {
        String string;
        ALog.d("AlinkHelper", "uploadData2Oss() called with: data = [" + bArr + "], dataCallback = [" + iDataCallback + "]");
        if (bArr == null || bArr.length < 1) {
            if (iDataCallback != null) {
                iDataCallback.onState("ignored", "data empty, upload ignored.");
                return;
            }
            return;
        }
        try {
            if (!DCEnvHelper.getHasLogExtraSDK()) {
                ALog.w("AlinkHelper", "uploadData2Oss ignored, no log upload sdk exists.");
                if (iDataCallback != null) {
                    iDataCallback.onState("ignored", "current env not need, upload ignored.");
                    return;
                }
                return;
            }
            Object identifyId = ReflectUtils.getIdentifyId();
            if (identifyId == null || TextUtils.isEmpty(identifyId.toString())) {
                string = null;
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append(String.valueOf(identifyId));
                sb.append("/fb_dev_");
                sb.append(System.currentTimeMillis());
                sb.append(".log");
                string = sb.toString();
            }
            if (iDataCallback != null) {
                iDataCallback.onState("devOssKey", string);
            }
            Log2Cloud.getInstance().uploadOriginalByteLog(bArr, string, new OSSManager.OSSUploadCallback() { // from class: com.aliyun.alink.business.devicecenter.base.AlinkHelper.1
                public void onUploadFailed(int i2, String str) {
                    ALog.d("AlinkHelper", "uploadData2Oss onUploadFailed() called with: i = [" + i2 + "], s = [" + str + "]");
                    IDataCallback iDataCallback2 = iDataCallback;
                    if (iDataCallback2 != null) {
                        iDataCallback2.onResult(false, i2 + str);
                    }
                }

                public void onUploadSuccess(String str, String str2) {
                    ALog.d("AlinkHelper", "uploadData2Oss onUploadSuccess() called with: ossObjectName = [" + str + "], s1 = [" + str2 + "]");
                    IDataCallback iDataCallback2 = iDataCallback;
                    if (iDataCallback2 != null) {
                        iDataCallback2.onResult(true, str);
                    }
                }
            });
        } catch (Exception e2) {
            ALog.w("AlinkHelper", "uploadData2Oss exception= " + e2);
        }
    }

    public static String a(String str) {
        if (str == null || str.length() != 4) {
            return null;
        }
        String strBytesToHexString = StringUtils.bytesToHexString(sixBitsToEightBits(str.getBytes()));
        ALog.d("AlinkHelper", "getMacFromConvertedMac conv=" + str + ", pri=" + strBytesToHexString);
        return strBytesToHexString;
    }
}
