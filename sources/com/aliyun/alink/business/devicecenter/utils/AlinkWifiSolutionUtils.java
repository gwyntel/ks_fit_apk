package com.aliyun.alink.business.devicecenter.utils;

import androidx.media3.datasource.cache.CacheDataSink;
import androidx.media3.extractor.AacUtil;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.umeng.analytics.pro.q;
import com.xiaomi.infra.galaxy.fds.android.FDSClientConfiguration;
import com.xiaomi.mipush.sdk.Constants;
import io.flutter.plugin.platform.PlatformPlugin;
import java.util.Random;

/* loaded from: classes2.dex */
public class AlinkWifiSolutionUtils {
    public static int[] CRC16_TABLE = {0, 49345, 49537, 320, 49921, 960, 640, 49729, 50689, 1728, 1920, 51009, PlatformPlugin.DEFAULT_SYSTEM_UI, 50625, 50305, 1088, 52225, 3264, 3456, 52545, 3840, 53185, 52865, 3648, 2560, 51905, 52097, 2880, 51457, 2496, 2176, 51265, 55297, 6336, 6528, 55617, 6912, 56257, 55937, 6720, 7680, 57025, 57217, 8000, 56577, 7616, 7296, 56385, 5120, 54465, 54657, 5440, 55041, 6080, 5760, 54849, 53761, 4800, 4992, 54081, q.a.f21889k, 53697, 53377, 4160, 61441, 12480, 12672, 61761, 13056, 62401, 62081, 12864, 13824, 63169, 63361, 14144, 62721, 13760, 13440, 62529, 15360, 64705, 64897, 15680, 65281, 16320, AacUtil.AAC_HE_V1_MAX_RATE_BYTES_PER_SECOND, 65089, 64001, 15040, 15232, 64321, 14592, 63937, 63617, 14400, FDSClientConfiguration.DEFAULT_WORK_QUEUE_CAPACITY, 59585, 59777, 10560, 60161, 11200, 10880, 59969, 60929, 11968, 12160, 61249, 11520, 60865, 60545, 11328, 58369, 9408, 9600, 58689, 9984, 59329, 59009, 9792, 8704, 58049, 58241, 9024, 57601, 8640, 8320, 57409, 40961, 24768, 24960, 41281, 25344, 41921, 41601, 25152, 26112, 42689, 42881, 26432, 42241, 26048, 25728, 42049, 27648, 44225, 44417, 27968, 44801, 28608, 28288, 44609, 43521, 27328, 27520, 43841, 26880, 43457, 43137, 26688, 30720, 47297, 47489, 31040, 47873, 31680, 31360, 47681, 48641, 32448, 32640, 48961, 32000, 48577, 48257, 31808, 46081, 29888, 30080, 46401, 30464, 47041, 46721, 30272, 29184, 45761, 45953, 29504, 45313, 29120, 28800, 45121, CacheDataSink.DEFAULT_BUFFER_SIZE, 37057, 37249, 20800, 37633, 21440, 21120, 37441, 38401, 22208, 22400, 38721, 21760, 38337, 38017, 21568, 39937, 23744, 23936, 40257, 24320, 40897, 40577, 24128, 23040, 39617, 39809, 23360, 39169, 22976, 22656, 38977, 34817, 18624, 18816, 35137, 19200, 35777, 35457, 19008, 19968, 36545, 36737, 20288, 36097, 19904, 19584, 35905, 17408, 33985, 34177, 17728, 34561, 18368, 18048, 34369, 33281, 17088, 17280, 33601, 16640, 33217, 32897, 16448};
    public static final char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static byte[] byteMerge(byte[] bArr, byte[] bArr2, int i2) {
        if (bArr2 == null) {
            return bArr;
        }
        System.arraycopy(bArr2, 0, bArr, i2, bArr2.length);
        return bArr;
    }

    public static String bytesToHexString(byte[] bArr) {
        char[] cArr = new char[bArr.length * 2];
        for (int i2 = 0; i2 < bArr.length; i2++) {
            byte b2 = bArr[i2];
            int i3 = i2 * 2;
            char[] cArr2 = hexArray;
            cArr[i3] = cArr2[(b2 & 255) >>> 4];
            cArr[i3 + 1] = cArr2[b2 & 15];
        }
        return new String(cArr);
    }

    public static byte[] eightBitsToSevenBits(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return null;
        }
        byte[] bArr2 = new byte[(bArr.length * 8) + 6];
        int i2 = 0;
        for (int i3 = 0; i3 < bArr.length; i3++) {
            bArr2[i2] = (byte) (bArr[i3] & 1);
            bArr2[i2 + 1] = (byte) ((bArr[i3] >> 1) & 1);
            bArr2[i2 + 2] = (byte) ((bArr[i3] >> 2) & 1);
            bArr2[i2 + 3] = (byte) ((bArr[i3] >> 3) & 1);
            bArr2[i2 + 4] = (byte) ((bArr[i3] >> 4) & 1);
            bArr2[i2 + 5] = (byte) ((bArr[i3] >> 5) & 1);
            int i4 = i2 + 7;
            bArr2[i2 + 6] = (byte) ((bArr[i3] >> 6) & 1);
            i2 += 8;
            bArr2[i4] = (byte) ((bArr[i3] >> 7) & 1);
        }
        int length = ((bArr.length * 8) + 6) / 7;
        byte[] bArr3 = new byte[length];
        for (int i5 = 0; i5 < length; i5++) {
            int i6 = i5 * 7;
            bArr3[i5] = (byte) ((bArr2[i6 + 6] << 6) | bArr2[i6] | (bArr2[i6 + 1] << 1) | (bArr2[i6 + 2] << 2) | (bArr2[i6 + 3] << 3) | (bArr2[i6 + 4] << 4) | (bArr2[i6 + 5] << 5));
        }
        return bArr3;
    }

    public static byte[] eightBitsToSixBits(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return null;
        }
        byte[] bArr2 = new byte[(bArr.length * 8) + 5];
        int i2 = 0;
        for (int i3 = 0; i3 < bArr.length; i3++) {
            bArr2[i2] = (byte) (bArr[i3] & 1);
            bArr2[i2 + 1] = (byte) ((bArr[i3] >> 1) & 1);
            bArr2[i2 + 2] = (byte) ((bArr[i3] >> 2) & 1);
            bArr2[i2 + 3] = (byte) ((bArr[i3] >> 3) & 1);
            bArr2[i2 + 4] = (byte) ((bArr[i3] >> 4) & 1);
            bArr2[i2 + 5] = (byte) ((bArr[i3] >> 5) & 1);
            int i4 = i2 + 7;
            bArr2[i2 + 6] = (byte) ((bArr[i3] >> 6) & 1);
            i2 += 8;
            bArr2[i4] = (byte) ((bArr[i3] >> 7) & 1);
        }
        int length = ((bArr.length * 8) + 5) / 6;
        byte[] bArr3 = new byte[length];
        for (int i5 = 0; i5 < length; i5++) {
            int i6 = i5 * 6;
            bArr3[i5] = (byte) ((bArr2[i6 + 5] << 5) | bArr2[i6] | (bArr2[i6 + 1] << 1) | (bArr2[i6 + 2] << 2) | (bArr2[i6 + 3] << 3) | (bArr2[i6 + 4] << 4));
        }
        return bArr3;
    }

    public static byte[] getCrc(byte[] bArr) {
        int i2 = 0;
        for (byte b2 : bArr) {
            i2 = CRC16_TABLE[(i2 ^ b2) & 255] ^ (i2 >>> 8);
        }
        return shortToByteArray((short) i2);
    }

    public static String getInetAddress(int i2) {
        return (i2 & 255) + "." + ((i2 >> 8) & 255) + "." + ((i2 >> 16) & 255) + "." + ((i2 >> 24) & 255);
    }

    public static String getRandomString(int i2) {
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i3 = 0; i3 < i2; i3++) {
            stringBuffer.append("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".charAt(random.nextInt(62)));
        }
        return stringBuffer.toString();
    }

    public static byte[] hexStringTobytes(String str) {
        int length = str.length();
        byte[] bArr = new byte[length / 2];
        for (int i2 = 0; i2 < length; i2 += 2) {
            bArr[i2 / 2] = (byte) ((Character.digit(str.charAt(i2), 16) << 4) + Character.digit(str.charAt(i2 + 1), 16));
        }
        return bArr;
    }

    public static String longToIp(long j2) {
        return (j2 >>> 24) + "." + String.valueOf((16777215 & j2) >>> 16) + "." + String.valueOf((65535 & j2) >>> 8) + "." + String.valueOf(j2 & 255);
    }

    public static void printByteArray(byte[] bArr) {
        ALog.d("AlinkWifiSolutionUtils", "send byte array:");
        StringBuilder sb = new StringBuilder();
        sb.append("start");
        for (byte b2 : bArr) {
            sb.append(Constants.ACCEPT_TIME_SEPARATOR_SERVER + ((int) b2) + Constants.ACCEPT_TIME_SEPARATOR_SERVER);
        }
        ALog.d("AlinkWifiSolutionUtils", sb.toString());
    }

    public static byte[] shortToByteArray(short s2) {
        return new byte[]{(byte) ((s2 >> 12) & 15), (byte) ((s2 >> 8) & 15), (byte) ((s2 >> 4) & 15), (byte) (s2 & 15)};
    }
}
