package com.aliyun.alink.business.devicecenter.provision.core.broadcast;

import android.content.Context;
import android.text.TextUtils;
import com.aliyun.alink.business.devicecenter.api.add.LinkType;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.aliyun.alink.business.devicecenter.base.AlinkHelper;
import com.aliyun.alink.business.devicecenter.base.DCErrorCode;
import com.aliyun.alink.business.devicecenter.config.BaseProvisionStrategy;
import com.aliyun.alink.business.devicecenter.config.IConfigCallback;
import com.aliyun.alink.business.devicecenter.config.IConfigExtraCallback;
import com.aliyun.alink.business.devicecenter.config.IConfigStrategy;
import com.aliyun.alink.business.devicecenter.config.annotation.ConfigStrategy;
import com.aliyun.alink.business.devicecenter.config.model.DCAlibabaConfigParams;
import com.aliyun.alink.business.devicecenter.config.model.DCConfigParams;
import com.aliyun.alink.business.devicecenter.config.phoneap.AlinkAESHelper;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.provision.core.C0451n;
import com.aliyun.alink.business.devicecenter.provision.core.RunnableC0453p;
import com.aliyun.alink.business.devicecenter.provision.core.RunnableC0454q;
import com.aliyun.alink.business.devicecenter.provision.core.RunnableC0455r;
import com.aliyun.alink.business.devicecenter.provision.core.RunnableC0456s;
import com.aliyun.alink.business.devicecenter.provision.core.RunnableC0457t;
import com.aliyun.alink.business.devicecenter.provision.core.Z;
import com.aliyun.alink.business.devicecenter.track.DCUserTrack;
import com.aliyun.alink.business.devicecenter.utils.AlinkWifiSolutionUtils;
import com.aliyun.alink.business.devicecenter.utils.StringUtils;
import com.aliyun.alink.business.devicecenter.utils.ThreadPool;
import com.google.android.gms.common.ConnectionResult;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@ConfigStrategy(linkType = LinkType.ALI_BROADCAST)
/* loaded from: classes2.dex */
public class AlinkBroadcastConfigStrategy extends BaseProvisionStrategy implements IConfigStrategy {
    public static String TAG = "AlinkBroadcastConfigStrategy";
    public long INTERVAL_UDP_Loop;
    public long INTERVAL_UDP_Loop_LEVEL1;
    public long INTERVAL_UDP_Loop_LEVEL2;
    public long INTERVAL_UDP_Packet;
    public long INTERVAL_UDP_Packet_LEVEL0;
    public long INTERVAL_UDP_Packet_LEVEL1;
    public long INTERVAL_UDP_Packet_LEVEL2;
    public long INTERVAL_UDP_SENDING;
    public long INTERVAL_UDP_SENDING_LEVEL1;
    public long INTERVAL_UDP_SENDING_LEVEL2;
    public final String MULTICAST_UDP_Addr_Prefix;
    public final int START_FLAG1;
    public final int START_FLAG2;
    public final int START_FLAG3;
    public final String UDP_Addr;
    public int UDP_LEVEL;
    public final int UDP_PORT;
    public InetAddress address;
    public InetAddress addressMulticast;
    public String appRandom;
    public byte[] buffer;
    public String cloudRandom;
    public AtomicInteger delayBroadcastTimeAI;
    public boolean enableMulticastSend;
    public boolean enableSendRegion;
    public String getTokenBssid;
    public boolean getTokenWithBssid;
    public boolean isChinessSSID;
    public AtomicBoolean isProvisioningAB;
    public Context mContext;
    public Z mP2PProvision;
    public byte[] packetMulticastByteArray;
    public int port;
    public int portIndex;
    public String securityAesKey;
    public int sendBssidByteLen;
    public DatagramPacket sendPacketMulticast;
    public byte[] send_data;
    public DatagramPacket send_packet;
    public Thread startMulProvisionThread;
    public Thread startP2PThread;
    public Thread startProvisionThread;
    public int testFlagByte;
    public int tokenByteLen;
    public DatagramSocket udpSocket;
    public MulticastSocket udpSocketMulticast;
    public AtomicBoolean useAppTokenAB;

    public AlinkBroadcastConfigStrategy() {
        this.START_FLAG1 = 1248;
        this.START_FLAG2 = 1248;
        this.START_FLAG3 = 1248;
        this.UDP_PORT = 50000;
        this.MULTICAST_UDP_Addr_Prefix = "239.";
        this.UDP_Addr = "255.255.255.255";
        this.INTERVAL_UDP_SENDING_LEVEL1 = 10000L;
        this.INTERVAL_UDP_SENDING_LEVEL2 = 10000L;
        this.INTERVAL_UDP_Packet_LEVEL0 = 40L;
        this.INTERVAL_UDP_Packet_LEVEL1 = 20L;
        this.INTERVAL_UDP_Packet_LEVEL2 = 100L;
        this.INTERVAL_UDP_Loop_LEVEL1 = 200L;
        this.INTERVAL_UDP_Loop_LEVEL2 = 0L;
        this.isProvisioningAB = new AtomicBoolean(false);
        this.enableMulticastSend = true;
        this.enableSendRegion = false;
        this.delayBroadcastTimeAI = new AtomicInteger(3000);
        this.address = null;
        this.send_packet = null;
        this.addressMulticast = null;
        this.sendPacketMulticast = null;
        this.mContext = null;
        this.send_data = new byte[128];
        this.buffer = new byte[ConnectionResult.DRIVE_EXTERNAL_STORAGE_REQUIRED];
        this.packetMulticastByteArray = null;
        this.portIndex = 0;
        this.isChinessSSID = false;
        this.securityAesKey = null;
        this.startProvisionThread = null;
        this.startMulProvisionThread = null;
        this.startP2PThread = null;
        this.mP2PProvision = null;
        this.tokenByteLen = 3;
        this.cloudRandom = null;
        this.sendBssidByteLen = 3;
        this.getTokenWithBssid = true;
        this.getTokenBssid = null;
        this.testFlagByte = -1;
        this.appRandom = null;
        this.useAppTokenAB = new AtomicBoolean(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void UDP_SEND(int i2) throws InterruptedException, IOException {
        try {
            this.portIndex = (this.portIndex + 1) % 10000;
            DatagramPacket datagramPacket = new DatagramPacket(this.buffer, i2, this.address, this.port + this.portIndex);
            this.send_packet = datagramPacket;
            this.udpSocket.send(datagramPacket);
        } catch (Exception e2) {
            e2.printStackTrace();
            ALog.w(TAG, "UDP_SEND(),send data fail");
        }
        Thread.sleep(this.INTERVAL_UDP_Packet);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void closeSocket(DatagramSocket datagramSocket) {
        if (datagramSocket != null) {
            try {
                if (datagramSocket.isClosed()) {
                    return;
                }
                datagramSocket.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private byte getFlagByte(boolean z2) {
        byte bByteValue = (byte) (((byte) (new Byte((byte) 0).byteValue() | 8)) | 6);
        if (z2) {
            bByteValue = (byte) (bByteValue | 1);
        }
        if (this.isChinessSSID) {
            bByteValue = (byte) (bByteValue | 32);
        }
        if (!this.enableSendRegion) {
            return bByteValue;
        }
        byte b2 = (byte) (bByteValue & 39);
        int i2 = this.testFlagByte;
        return (byte) (((byte) (b2 | ((i2 & 3) << 3))) | ((i2 & 12) << 4));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getToken(String str, String str2) {
        String lastBssid = this.getTokenWithBssid ? StringUtils.getLastBssid(this.getTokenBssid, this.sendBssidByteLen) : "";
        String mDData = null;
        if (lastBssid == null) {
            ALog.w(TAG, "getTokenWithBssid=true, but get last bssid failed. no need check token.");
            return null;
        }
        if (str == null) {
            str = "";
        }
        if (str2 == null) {
            str2 = "";
        }
        String str3 = lastBssid + str + StringUtils.bytesToHexString(str2.getBytes());
        try {
            mDData = StringUtils.getMDData(StringUtils.hexStringTobytes(str3), "SHA-256");
        } catch (NoSuchAlgorithmException e2) {
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        ALog.d(TAG, "enc=" + str3 + ", token=" + mDData);
        return mDData;
    }

    private boolean isSSidDataASCII(byte[] bArr) {
        if (bArr != null && bArr.length != 0) {
            for (byte b2 : bArr) {
                int i2 = b2 & 255;
                if (i2 < 32 || i2 > 126) {
                    return false;
                }
            }
        }
        return true;
    }

    private byte[] packSSID(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return null;
        }
        int i2 = 0;
        if (isSSidDataASCII(bArr)) {
            this.isChinessSSID = false;
            byte[] bArr2 = new byte[bArr.length];
            while (i2 < bArr.length) {
                bArr2[i2] = (byte) ((bArr[i2] & 255) - 32);
                i2++;
            }
            return bArr2;
        }
        this.isChinessSSID = true;
        byte[] bArr3 = new byte[(bArr.length * 8) + 7];
        int i3 = 0;
        for (int i4 = 0; i4 < bArr.length; i4++) {
            bArr3[i3] = (byte) (bArr[i4] & 1);
            bArr3[i3 + 1] = (byte) ((bArr[i4] >> 1) & 1);
            bArr3[i3 + 2] = (byte) ((bArr[i4] >> 2) & 1);
            bArr3[i3 + 3] = (byte) ((bArr[i4] >> 3) & 1);
            bArr3[i3 + 4] = (byte) ((bArr[i4] >> 4) & 1);
            bArr3[i3 + 5] = (byte) ((bArr[i4] >> 5) & 1);
            int i5 = i3 + 7;
            bArr3[i3 + 6] = (byte) ((bArr[i4] >> 6) & 1);
            i3 += 8;
            bArr3[i5] = (byte) ((bArr[i4] >> 7) & 1);
        }
        int length = ((bArr.length * 8) + 5) / 6;
        byte[] bArr4 = new byte[length];
        while (i2 < length) {
            int i6 = i2 * 6;
            bArr4[i2] = (byte) ((bArr3[i6 + 5] << 5) | bArr3[i6] | (bArr3[i6 + 1] << 1) | (bArr3[i6 + 2] << 2) | (bArr3[i6 + 3] << 3) | (bArr3[i6 + 4] << 4));
            i2++;
        }
        return bArr4;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void packetData(String str, String str2) throws UnsupportedEncodingException {
        byte[] bytes;
        try {
            if (TextUtils.isEmpty(str2)) {
                bytes = str2.getBytes("UTF-8");
                for (int i2 = 0; i2 < bytes.length; i2++) {
                    bytes[i2] = (byte) ((bytes[i2] & 255) - 32);
                }
            } else {
                byte[] bArrEncrypt128CFB = AlinkAESHelper.encrypt128CFB(str2, this.securityAesKey);
                ALog.d(TAG, "packetData(), pwd encrypted data = ");
                AlinkWifiSolutionUtils.printByteArray(bArrEncrypt128CFB);
                bytes = AlinkWifiSolutionUtils.eightBitsToSixBits(bArrEncrypt128CFB);
                ALog.d(TAG, "packetData(), pwd encrypted 8->6 data = ");
                AlinkWifiSolutionUtils.printByteArray(bytes);
            }
            int i3 = 3;
            if (str == null || str.length() <= 0) {
                byte[] bArr = this.send_data;
                bArr[0] = (byte) (bytes.length + 5);
                bArr[1] = getFlagByte(false);
                this.send_data[2] = (byte) bytes.length;
                int i4 = 0;
                while (i4 < bytes.length) {
                    this.send_data[i3] = bytes[i4];
                    i4++;
                    i3++;
                }
            } else {
                byte[] bytes2 = str.getBytes("UTF-8");
                String str3 = TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("ssid len = ");
                sb.append(bytes2.length);
                ALog.d(str3, sb.toString());
                byte[] bArrPackSSID = packSSID(bytes2);
                String str4 = TAG;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("packed ssid len = ");
                sb2.append(bArrPackSSID.length);
                ALog.d(str4, sb2.toString());
                if (bArrPackSSID.length + bytes.length > 121) {
                    ALog.e(TAG, "SSID and passwd too long");
                    stopConfig();
                    return;
                }
                String str5 = TAG;
                StringBuilder sb3 = new StringBuilder();
                sb3.append("dataLen = ");
                sb3.append(bArrPackSSID.length + 6 + bytes.length);
                ALog.d(str5, sb3.toString());
                byte[] bArr2 = this.send_data;
                bArr2[0] = (byte) (bArrPackSSID.length + 6 + bytes.length);
                bArr2[1] = getFlagByte(true);
                byte[] bArr3 = this.send_data;
                bArr3[2] = (byte) bArrPackSSID.length;
                bArr3[3] = (byte) bytes.length;
                i3 = 4;
                int i5 = 0;
                while (i5 < bArrPackSSID.length) {
                    this.send_data[i3] = bArrPackSSID[i5];
                    i5++;
                    i3++;
                }
                int i6 = 0;
                while (i6 < bytes.length) {
                    this.send_data[i3] = bytes[i6];
                    i6++;
                    i3++;
                }
            }
            short s2 = 0;
            for (int i7 = 0; i7 < i3; i7++) {
                s2 = (short) (s2 + (this.send_data[i7] & 255));
            }
            byte[] bArr4 = this.send_data;
            bArr4[i3] = (byte) ((s2 >> 6) & 63);
            int i8 = i3 + 2;
            bArr4[i3 + 1] = (byte) (s2 & 63);
            if ((bArr4[i3] & 255) == 0) {
                bArr4[i3] = 1;
            }
            int i9 = i3 + 1;
            if ((bArr4[i9] & 255) == 0) {
                bArr4[i9] = 1;
            }
            String str6 = TAG;
            StringBuilder sb4 = new StringBuilder();
            sb4.append("send data i= ");
            sb4.append(i8);
            sb4.append(", sendDataLen=");
            sb4.append(this.send_data.length);
            ALog.d(str6, sb4.toString());
            String str7 = TAG;
            StringBuilder sb5 = new StringBuilder();
            sb5.append("send data = ");
            sb5.append(AlinkWifiSolutionUtils.bytesToHexString(this.send_data));
            ALog.i(str7, sb5.toString());
            AlinkWifiSolutionUtils.printByteArray(this.send_data);
        } catch (Exception e2) {
            ALog.w(TAG, "packetData(),error = " + e2.toString());
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0097 A[Catch: Exception -> 0x0028, UnsupportedEncodingException -> 0x002b, TryCatch #2 {UnsupportedEncodingException -> 0x002b, Exception -> 0x0028, blocks: (B:3:0x0002, B:8:0x0017, B:20:0x002f, B:22:0x0041, B:27:0x004a, B:31:0x0050, B:33:0x0054, B:35:0x0059, B:37:0x0064, B:40:0x006d, B:41:0x0079, B:46:0x0086, B:48:0x0097, B:51:0x009f, B:53:0x00a4, B:54:0x00a7, B:56:0x00ab, B:57:0x00b4, B:59:0x00b7, B:61:0x00c8, B:63:0x00d0, B:65:0x00e2, B:66:0x00ee, B:68:0x00f1, B:64:0x00dc, B:69:0x0100, B:70:0x010b, B:72:0x010e, B:73:0x011d, B:75:0x0121, B:76:0x012f, B:78:0x0132, B:81:0x0141, B:82:0x014c, B:42:0x007b, B:11:0x0020, B:13:0x0024, B:14:0x0027, B:6:0x0010), top: B:87:0x0002 }] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x009f A[Catch: Exception -> 0x0028, UnsupportedEncodingException -> 0x002b, TryCatch #2 {UnsupportedEncodingException -> 0x002b, Exception -> 0x0028, blocks: (B:3:0x0002, B:8:0x0017, B:20:0x002f, B:22:0x0041, B:27:0x004a, B:31:0x0050, B:33:0x0054, B:35:0x0059, B:37:0x0064, B:40:0x006d, B:41:0x0079, B:46:0x0086, B:48:0x0097, B:51:0x009f, B:53:0x00a4, B:54:0x00a7, B:56:0x00ab, B:57:0x00b4, B:59:0x00b7, B:61:0x00c8, B:63:0x00d0, B:65:0x00e2, B:66:0x00ee, B:68:0x00f1, B:64:0x00dc, B:69:0x0100, B:70:0x010b, B:72:0x010e, B:73:0x011d, B:75:0x0121, B:76:0x012f, B:78:0x0132, B:81:0x0141, B:82:0x014c, B:42:0x007b, B:11:0x0020, B:13:0x0024, B:14:0x0027, B:6:0x0010), top: B:87:0x0002 }] */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00a4 A[Catch: Exception -> 0x0028, UnsupportedEncodingException -> 0x002b, TryCatch #2 {UnsupportedEncodingException -> 0x002b, Exception -> 0x0028, blocks: (B:3:0x0002, B:8:0x0017, B:20:0x002f, B:22:0x0041, B:27:0x004a, B:31:0x0050, B:33:0x0054, B:35:0x0059, B:37:0x0064, B:40:0x006d, B:41:0x0079, B:46:0x0086, B:48:0x0097, B:51:0x009f, B:53:0x00a4, B:54:0x00a7, B:56:0x00ab, B:57:0x00b4, B:59:0x00b7, B:61:0x00c8, B:63:0x00d0, B:65:0x00e2, B:66:0x00ee, B:68:0x00f1, B:64:0x00dc, B:69:0x0100, B:70:0x010b, B:72:0x010e, B:73:0x011d, B:75:0x0121, B:76:0x012f, B:78:0x0132, B:81:0x0141, B:82:0x014c, B:42:0x007b, B:11:0x0020, B:13:0x0024, B:14:0x0027, B:6:0x0010), top: B:87:0x0002 }] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00ab A[Catch: Exception -> 0x0028, UnsupportedEncodingException -> 0x002b, TryCatch #2 {UnsupportedEncodingException -> 0x002b, Exception -> 0x0028, blocks: (B:3:0x0002, B:8:0x0017, B:20:0x002f, B:22:0x0041, B:27:0x004a, B:31:0x0050, B:33:0x0054, B:35:0x0059, B:37:0x0064, B:40:0x006d, B:41:0x0079, B:46:0x0086, B:48:0x0097, B:51:0x009f, B:53:0x00a4, B:54:0x00a7, B:56:0x00ab, B:57:0x00b4, B:59:0x00b7, B:61:0x00c8, B:63:0x00d0, B:65:0x00e2, B:66:0x00ee, B:68:0x00f1, B:64:0x00dc, B:69:0x0100, B:70:0x010b, B:72:0x010e, B:73:0x011d, B:75:0x0121, B:76:0x012f, B:78:0x0132, B:81:0x0141, B:82:0x014c, B:42:0x007b, B:11:0x0020, B:13:0x0024, B:14:0x0027, B:6:0x0010), top: B:87:0x0002 }] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00c8 A[Catch: Exception -> 0x0028, UnsupportedEncodingException -> 0x002b, TryCatch #2 {UnsupportedEncodingException -> 0x002b, Exception -> 0x0028, blocks: (B:3:0x0002, B:8:0x0017, B:20:0x002f, B:22:0x0041, B:27:0x004a, B:31:0x0050, B:33:0x0054, B:35:0x0059, B:37:0x0064, B:40:0x006d, B:41:0x0079, B:46:0x0086, B:48:0x0097, B:51:0x009f, B:53:0x00a4, B:54:0x00a7, B:56:0x00ab, B:57:0x00b4, B:59:0x00b7, B:61:0x00c8, B:63:0x00d0, B:65:0x00e2, B:66:0x00ee, B:68:0x00f1, B:64:0x00dc, B:69:0x0100, B:70:0x010b, B:72:0x010e, B:73:0x011d, B:75:0x0121, B:76:0x012f, B:78:0x0132, B:81:0x0141, B:82:0x014c, B:42:0x007b, B:11:0x0020, B:13:0x0024, B:14:0x0027, B:6:0x0010), top: B:87:0x0002 }] */
    /* JADX WARN: Removed duplicated region for block: B:72:0x010e A[Catch: Exception -> 0x0028, UnsupportedEncodingException -> 0x002b, LOOP:2: B:70:0x010b->B:72:0x010e, LOOP_END, TryCatch #2 {UnsupportedEncodingException -> 0x002b, Exception -> 0x0028, blocks: (B:3:0x0002, B:8:0x0017, B:20:0x002f, B:22:0x0041, B:27:0x004a, B:31:0x0050, B:33:0x0054, B:35:0x0059, B:37:0x0064, B:40:0x006d, B:41:0x0079, B:46:0x0086, B:48:0x0097, B:51:0x009f, B:53:0x00a4, B:54:0x00a7, B:56:0x00ab, B:57:0x00b4, B:59:0x00b7, B:61:0x00c8, B:63:0x00d0, B:65:0x00e2, B:66:0x00ee, B:68:0x00f1, B:64:0x00dc, B:69:0x0100, B:70:0x010b, B:72:0x010e, B:73:0x011d, B:75:0x0121, B:76:0x012f, B:78:0x0132, B:81:0x0141, B:82:0x014c, B:42:0x007b, B:11:0x0020, B:13:0x0024, B:14:0x0027, B:6:0x0010), top: B:87:0x0002 }] */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0121 A[Catch: Exception -> 0x0028, UnsupportedEncodingException -> 0x002b, TryCatch #2 {UnsupportedEncodingException -> 0x002b, Exception -> 0x0028, blocks: (B:3:0x0002, B:8:0x0017, B:20:0x002f, B:22:0x0041, B:27:0x004a, B:31:0x0050, B:33:0x0054, B:35:0x0059, B:37:0x0064, B:40:0x006d, B:41:0x0079, B:46:0x0086, B:48:0x0097, B:51:0x009f, B:53:0x00a4, B:54:0x00a7, B:56:0x00ab, B:57:0x00b4, B:59:0x00b7, B:61:0x00c8, B:63:0x00d0, B:65:0x00e2, B:66:0x00ee, B:68:0x00f1, B:64:0x00dc, B:69:0x0100, B:70:0x010b, B:72:0x010e, B:73:0x011d, B:75:0x0121, B:76:0x012f, B:78:0x0132, B:81:0x0141, B:82:0x014c, B:42:0x007b, B:11:0x0020, B:13:0x0024, B:14:0x0027, B:6:0x0010), top: B:87:0x0002 }] */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0141 A[Catch: Exception -> 0x0028, UnsupportedEncodingException -> 0x002b, LOOP:4: B:80:0x013f->B:81:0x0141, LOOP_END, TryCatch #2 {UnsupportedEncodingException -> 0x002b, Exception -> 0x0028, blocks: (B:3:0x0002, B:8:0x0017, B:20:0x002f, B:22:0x0041, B:27:0x004a, B:31:0x0050, B:33:0x0054, B:35:0x0059, B:37:0x0064, B:40:0x006d, B:41:0x0079, B:46:0x0086, B:48:0x0097, B:51:0x009f, B:53:0x00a4, B:54:0x00a7, B:56:0x00ab, B:57:0x00b4, B:59:0x00b7, B:61:0x00c8, B:63:0x00d0, B:65:0x00e2, B:66:0x00ee, B:68:0x00f1, B:64:0x00dc, B:69:0x0100, B:70:0x010b, B:72:0x010e, B:73:0x011d, B:75:0x0121, B:76:0x012f, B:78:0x0132, B:81:0x0141, B:82:0x014c, B:42:0x007b, B:11:0x0020, B:13:0x0024, B:14:0x0027, B:6:0x0010), top: B:87:0x0002 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void packetMulticastData(java.lang.String r10, java.lang.String r11) throws java.io.UnsupportedEncodingException {
        /*
            Method dump skipped, instructions count: 348
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aliyun.alink.business.devicecenter.provision.core.broadcast.AlinkBroadcastConfigStrategy.packetMulticastData(java.lang.String, java.lang.String):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void provisioning(IConfigCallback iConfigCallback) {
        DCErrorCode dCErrorCode = this.provisionErrorInfo;
        if (dCErrorCode != null) {
            dCErrorCode.setSubcode(DCErrorCode.SUBCODE_PT_NO_CONNECTAP_NOTIFY_AND_CHECK_TOKEN_FAIL).setMsg("noConnectApOrCheckTokenSuccess");
        }
        new Thread(new RunnableC0454q(this, iConfigCallback)).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void send() {
        if (this.isProvisioningAB.get() || this.provisionHasStopped.get()) {
            return;
        }
        this.isProvisioningAB.set(true);
        this.startProvisionThread = new Thread(new RunnableC0455r(this));
        if (this.enableMulticastSend) {
            Thread thread = new Thread(new RunnableC0456s(this));
            this.startMulProvisionThread = thread;
            thread.start();
        }
        this.startProvisionThread.start();
        if (this.provisionHasStopped.get() || this.mConfigParams == null) {
            return;
        }
        Thread thread2 = new Thread(new RunnableC0457t(this));
        this.startP2PThread = thread2;
        thread2.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendMulticastUdpPacket(InetAddress inetAddress, int i2) throws InterruptedException, IOException {
        try {
            this.portIndex = (this.portIndex + 1) % 10000;
            if (this.udpSocketMulticast != null) {
                DatagramPacket datagramPacket = new DatagramPacket(this.buffer, i2, inetAddress, this.port + this.portIndex);
                this.sendPacketMulticast = datagramPacket;
                this.udpSocketMulticast.send(datagramPacket);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            ALog.w(TAG, "UDP_SEND(), send multicast data fail");
        }
        Thread.sleep(this.INTERVAL_UDP_Packet);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSendLevel(int i2) {
        if (i2 == 0) {
            this.UDP_LEVEL = 0;
            this.INTERVAL_UDP_Loop = this.INTERVAL_UDP_Loop_LEVEL1;
            this.INTERVAL_UDP_Packet = this.INTERVAL_UDP_Packet_LEVEL0;
            this.INTERVAL_UDP_SENDING = this.INTERVAL_UDP_SENDING_LEVEL1;
            return;
        }
        if (i2 == 1) {
            this.UDP_LEVEL = 1;
            this.INTERVAL_UDP_Loop = this.INTERVAL_UDP_Loop_LEVEL1;
            this.INTERVAL_UDP_Packet = this.INTERVAL_UDP_Packet_LEVEL1;
            this.INTERVAL_UDP_SENDING = this.INTERVAL_UDP_SENDING_LEVEL1;
            return;
        }
        if (i2 != 2) {
            this.UDP_LEVEL = 2;
            this.INTERVAL_UDP_Loop = this.INTERVAL_UDP_Loop_LEVEL2;
            this.INTERVAL_UDP_Packet = this.INTERVAL_UDP_Packet_LEVEL2;
            this.INTERVAL_UDP_SENDING = this.INTERVAL_UDP_SENDING_LEVEL2;
            return;
        }
        this.UDP_LEVEL = 2;
        this.INTERVAL_UDP_Loop = this.INTERVAL_UDP_Loop_LEVEL2;
        this.INTERVAL_UDP_Packet = this.INTERVAL_UDP_Packet_LEVEL2;
        this.INTERVAL_UDP_SENDING = this.INTERVAL_UDP_SENDING_LEVEL2;
    }

    @Override // com.aliyun.alink.business.devicecenter.config.IConfigStrategy
    public void continueConfig(Map map) {
    }

    @Override // com.aliyun.alink.business.devicecenter.config.IConfigStrategy
    public void doExtraPrepareWork(IConfigExtraCallback iConfigExtraCallback) {
    }

    @Override // com.aliyun.alink.business.devicecenter.config.IConfigStrategy
    public String getProvisionType() {
        return LinkType.ALI_BROADCAST.getName();
    }

    @Override // com.aliyun.alink.business.devicecenter.config.IConfigStrategy
    public boolean hasExtraPrepareWork() {
        return false;
    }

    @Override // com.aliyun.alink.business.devicecenter.config.IConfigStrategy
    public boolean isSupport() {
        return true;
    }

    @Override // com.aliyun.alink.business.devicecenter.config.IConfigStrategy
    public boolean needWiFiSsidPwd() {
        return true;
    }

    @Override // com.aliyun.alink.business.devicecenter.config.IConfigStrategy
    public void preConfig(IConfigCallback iConfigCallback, DCConfigParams dCConfigParams) {
    }

    @Override // com.aliyun.alink.business.devicecenter.config.IConfigStrategy
    public void startConfig(IConfigCallback iConfigCallback, DCConfigParams dCConfigParams) throws Exception {
        ALog.d(TAG, "startConfig() called with: callback = [" + iConfigCallback + "], configParams = [" + dCConfigParams + "]");
        this.mConfigCallback = iConfigCallback;
        if (!(dCConfigParams instanceof DCAlibabaConfigParams)) {
            this.provisionErrorInfo = new DCErrorCode(DCErrorCode.PARAM_ERROR_MSG, DCErrorCode.PF_PARAMS_ERROR).setMsg("configParams error:").setSubcode(DCErrorCode.SUBCODE_PE_PROVISION_PARAMS_ERROR);
            provisionResultCallback(null);
            return;
        }
        DCUserTrack.addTrackData(AlinkConstants.KEY_PROVISION_STARTED, "true");
        this.provisionErrorInfo = new DCErrorCode(DCErrorCode.PROVISION_TIMEOUT_MSG, DCErrorCode.PF_PROVISION_TIMEOUT);
        DCAlibabaConfigParams dCAlibabaConfigParams = (DCAlibabaConfigParams) dCConfigParams;
        this.mConfigParams = dCAlibabaConfigParams;
        this.provisionHasStopped.set(false);
        this.appRandom = null;
        this.cloudRandom = null;
        this.useAppTokenAB.set(false);
        if (AlinkHelper.isBatchBroadcast(this.mConfigParams)) {
            setCallbackOnce(false);
            stopBackupCheck();
        } else {
            startProvisionTimer();
        }
        addProvisionOverListener(new C0451n(this));
        if (TextUtils.isEmpty(this.mConfigParams.password) || !TextUtils.isEmpty(this.mConfigParams.productEncryptKey)) {
            this.securityAesKey = dCAlibabaConfigParams.productEncryptKey;
            provisioning(iConfigCallback);
            return;
        }
        this.securityAesKey = null;
        DCErrorCode dCErrorCode = this.provisionErrorInfo;
        if (dCErrorCode != null) {
            dCErrorCode.setSubcode(DCErrorCode.SUBCODE_PT_GET_CIPHER_TIMEOUT).setMsg("getCipherTimeout");
        }
        ThreadPool.submit(new RunnableC0453p(this, iConfigCallback));
    }

    @Override // com.aliyun.alink.business.devicecenter.config.IConfigStrategy
    public void stopConfig() {
        ALog.d(TAG, "stopProvison()");
        this.provisionHasStopped.set(true);
        cancelRequest(this.retryTransitoryClient);
        removeProvisionOverListener();
        this.provisionErrorInfo = null;
        stopProvisionTimer();
        this.appRandom = null;
        this.cloudRandom = null;
        try {
            if (this.isProvisioningAB.compareAndSet(true, false)) {
                Thread thread = this.startProvisionThread;
                if (thread != null) {
                    thread.interrupt();
                }
                Thread thread2 = this.startMulProvisionThread;
                if (thread2 != null) {
                    thread2.interrupt();
                }
                Thread thread3 = this.startP2PThread;
                if (thread3 != null) {
                    thread3.interrupt();
                }
            }
        } catch (Exception unused) {
        }
        Z z2 = this.mP2PProvision;
        if (z2 != null) {
            z2.g();
            this.mP2PProvision = null;
        }
        this.useAppTokenAB.set(false);
        stopBackupCheck();
    }

    public AlinkBroadcastConfigStrategy(Context context) {
        this.START_FLAG1 = 1248;
        this.START_FLAG2 = 1248;
        this.START_FLAG3 = 1248;
        this.UDP_PORT = 50000;
        this.MULTICAST_UDP_Addr_Prefix = "239.";
        this.UDP_Addr = "255.255.255.255";
        this.INTERVAL_UDP_SENDING_LEVEL1 = 10000L;
        this.INTERVAL_UDP_SENDING_LEVEL2 = 10000L;
        this.INTERVAL_UDP_Packet_LEVEL0 = 40L;
        this.INTERVAL_UDP_Packet_LEVEL1 = 20L;
        this.INTERVAL_UDP_Packet_LEVEL2 = 100L;
        this.INTERVAL_UDP_Loop_LEVEL1 = 200L;
        this.INTERVAL_UDP_Loop_LEVEL2 = 0L;
        this.isProvisioningAB = new AtomicBoolean(false);
        this.enableMulticastSend = true;
        this.enableSendRegion = false;
        this.delayBroadcastTimeAI = new AtomicInteger(3000);
        this.address = null;
        this.send_packet = null;
        this.addressMulticast = null;
        this.sendPacketMulticast = null;
        this.mContext = null;
        this.send_data = new byte[128];
        this.buffer = new byte[ConnectionResult.DRIVE_EXTERNAL_STORAGE_REQUIRED];
        this.packetMulticastByteArray = null;
        this.portIndex = 0;
        this.isChinessSSID = false;
        this.securityAesKey = null;
        this.startProvisionThread = null;
        this.startMulProvisionThread = null;
        this.startP2PThread = null;
        this.mP2PProvision = null;
        this.tokenByteLen = 3;
        this.cloudRandom = null;
        this.sendBssidByteLen = 3;
        this.getTokenWithBssid = true;
        this.getTokenBssid = null;
        this.testFlagByte = -1;
        this.appRandom = null;
        this.useAppTokenAB = new AtomicBoolean(false);
        this.mContext = context;
        this.mP2PProvision = new Z(context);
    }
}
