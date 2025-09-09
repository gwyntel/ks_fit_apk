package com.aliyun.alink.linksdk.tmp.utils;

import android.net.Uri;
import android.text.TextUtils;
import com.aliyun.alink.linksdk.tmp.devicemodel.DeviceModel;
import com.aliyun.alink.linksdk.tmp.resource.ResDescpt;
import com.aliyun.alink.linksdk.tmp.resource.e;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Random;

/* loaded from: classes2.dex */
public class TextHelper {
    static final String RANDOM_BYTE = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    protected static final String TAG = "[Tmp]TextHelper";
    protected static Random sRandom = new Random();

    /* renamed from: com.aliyun.alink.linksdk.tmp.utils.TextHelper$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$aliyun$alink$linksdk$tmp$resource$ResDescpt$ResElementType;

        static {
            int[] iArr = new int[ResDescpt.ResElementType.values().length];
            $SwitchMap$com$aliyun$alink$linksdk$tmp$resource$ResDescpt$ResElementType = iArr;
            try {
                iArr[ResDescpt.ResElementType.PROPERTY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$aliyun$alink$linksdk$tmp$resource$ResDescpt$ResElementType[ResDescpt.ResElementType.SERVICE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$aliyun$alink$linksdk$tmp$resource$ResDescpt$ResElementType[ResDescpt.ResElementType.EVENT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$aliyun$alink$linksdk$tmp$resource$ResDescpt$ResElementType[ResDescpt.ResElementType.DISCOVERY.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$aliyun$alink$linksdk$tmp$resource$ResDescpt$ResElementType[ResDescpt.ResElementType.ALCS.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public static String byte2hex(byte[] bArr, int i2) {
        if (bArr == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        if (bArr.length < i2) {
            i2 = bArr.length;
        }
        for (int i3 = 0; i3 < i2; i3++) {
            sb.append(String.format("%02X", Integer.valueOf(bArr[i3] & 255)));
        }
        return sb.toString();
    }

    public static String combineStr(String str, String str2) {
        return str + str2;
    }

    public static String foramtMethod(String str) {
        return TmpConstant.METHOD_SERVICE_PRE + str;
    }

    public static String formatDownRawId(String str, String str2) {
        return str + str2 + "/" + TmpConstant.IDENTIFIER_RAW_DATA_DOWN;
    }

    public static String formatDownRawTopic(String str, String str2) {
        return "/sys/" + str + "/" + str2 + TmpConstant.URI_THING + TmpConstant.URI_MODEL + "/down_raw";
    }

    public static String formatPostReplyTopic(String str, String str2) {
        return "/sys/" + str + "/" + str2 + "/" + TmpConstant.EVENT_PROPERTY_URI_PRE + "/" + TmpConstant.EVENT_PROPERTY_URI_POST_REPLY;
    }

    public static String formatReplaceTopic(String str, String str2, String str3) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return str.replace(TmpConstant.URI_PRODUCT_PRODUCT_REPLACE, str2).replace(TmpConstant.URI_PRODUCT_DEVICE_REPLACE, str3);
    }

    public static String formatResponseData(Object obj) {
        if (obj == null) {
            return null;
        }
        if (!(obj instanceof byte[])) {
            return String.valueOf(obj);
        }
        try {
            return new String((byte[]) obj, "UTF-8");
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static String formatTopic(String str, String str2, String str3) {
        return "/dev/" + str + "/" + str2 + str3;
    }

    public static int getRandomInt() {
        return sRandom.nextInt();
    }

    public static String getRandomString() {
        return getRandomString(16);
    }

    public static String getTopicStr(DeviceModel deviceModel, String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ResDescpt.ResElementType resElementTypeA = e.a(deviceModel, str);
        ALog.d(TAG, "getTopicStr identifier:" + str + " type:" + resElementTypeA + " deviceModel:" + deviceModel);
        if (TextUtils.isEmpty(str) || resElementTypeA == null) {
            ALog.e(TAG, "getTopicStr null");
            return null;
        }
        StringBuilder sb = new StringBuilder();
        int i2 = AnonymousClass1.$SwitchMap$com$aliyun$alink$linksdk$tmp$resource$ResDescpt$ResElementType[resElementTypeA.ordinal()];
        if (i2 != 1) {
            if (i2 != 2) {
                if (i2 != 3) {
                    if (i2 == 4) {
                        sb = new StringBuilder(TmpConstant.PATH_DISCOVERY);
                    } else if (i2 == 5 && deviceModel != null) {
                        sb = new StringBuilder("/");
                        sb.append("dev");
                        sb.append("/");
                        sb.append(deviceModel.getProfile().getProdKey());
                        sb.append("/");
                        sb.append(deviceModel.getProfile().getName());
                        sb.append("/");
                        sb.append(TmpConstant.URI_PREFIX_ALCS_SERVICE);
                        sb.append("/");
                        sb.append(str);
                    }
                } else if (deviceModel != null) {
                    sb = new StringBuilder("/");
                    sb.append("sys");
                    sb.append("/");
                    sb.append(deviceModel.getProfile().getProdKey());
                    sb.append("/");
                    sb.append(deviceModel.getProfile().getName());
                    if (str.equalsIgnoreCase("post")) {
                        sb.append("/");
                        sb.append(TmpConstant.EVENT_PROPERTY_URI_PRE);
                        sb.append("/");
                        sb.append(str);
                    } else {
                        sb.append("/");
                        sb.append(TmpConstant.EVENT_URI_PRE);
                        sb.append("/");
                        sb.append(str);
                        sb.append("/");
                        sb.append("post");
                    }
                }
            } else if (deviceModel != null) {
                sb = new StringBuilder("/");
                sb.append("sys");
                sb.append("/");
                sb.append(deviceModel.getProfile().getProdKey());
                sb.append("/");
                sb.append(deviceModel.getProfile().getName());
                sb.append("/");
                sb.append(TmpConstant.METHOD_URI_PRE);
                sb.append("/");
                sb.append(str);
            }
        } else if (deviceModel != null) {
            sb = new StringBuilder("/");
            sb.append("sys");
            sb.append("/");
            sb.append(deviceModel.getProfile().getProdKey());
            sb.append("/");
            sb.append(deviceModel.getProfile().getName());
            sb.append("/");
            sb.append(TmpConstant.PROPERTY_URI_PRE);
            sb.append("/");
            sb.append(str);
        }
        return sb.toString();
    }

    public static String queryDeviceName(Uri uri) {
        List<String> pathSegments = uri.getPathSegments();
        if (pathSegments == null || pathSegments.size() <= 2) {
            return null;
        }
        return pathSegments.get(2);
    }

    public static String queryEventIdentifier(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (str.equalsIgnoreCase(TmpConstant.METHOD_PROPERTY_POST)) {
            return "post";
        }
        String[] strArrSplit = str.split("\\.");
        if (strArrSplit == null || strArrSplit.length <= 1) {
            return null;
        }
        return "post".equalsIgnoreCase(strArrSplit[strArrSplit.length - 1]) ? strArrSplit[strArrSplit.length - 2] : strArrSplit[strArrSplit.length - 1];
    }

    public static String queryProduct(Uri uri) {
        List<String> pathSegments = uri.getPathSegments();
        if (pathSegments == null || pathSegments.size() <= 1) {
            return null;
        }
        return pathSegments.get(1);
    }

    public static String combineStr(String str, String str2, String str3) {
        return str + str2 + str3;
    }

    public static String getRandomString(int i2) {
        StringBuilder sb = new StringBuilder(i2);
        for (int i3 = 0; i3 < i2; i3++) {
            sb.append(RANDOM_BYTE.charAt(sRandom.nextInt(62)));
        }
        return sb.toString();
    }

    public static String byte2hex(byte[] bArr) {
        if (bArr == null) {
            return "";
        }
        return byte2hex(bArr, bArr.length);
    }
}
