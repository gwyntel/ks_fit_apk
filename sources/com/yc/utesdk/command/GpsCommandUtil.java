package com.yc.utesdk.command;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import androidx.annotation.NonNull;
import androidx.core.view.MotionEventCompat;
import com.alipay.sdk.m.u.i;
import com.google.gson.Gson;
import com.yc.utesdk.bean.EphemerisFileInfo;
import com.yc.utesdk.bean.EphemerisParams;
import com.yc.utesdk.bean.EphemerisRequired;
import com.yc.utesdk.bean.GpsCoordinates;
import com.yc.utesdk.bean.GpsNetworkParams;
import com.yc.utesdk.bean.GpsState;
import com.yc.utesdk.ble.close.DeviceBusyLockUtils;
import com.yc.utesdk.ble.open.UteBleClient;
import com.yc.utesdk.listener.FileService;
import com.yc.utesdk.listener.UteListenerManager;
import com.yc.utesdk.log.LogUtils;
import com.yc.utesdk.utils.open.ByteDataUtil;
import com.yc.utesdk.utils.open.GBUtils;
import com.yc.utesdk.utils.open.SPUtil;
import com.yc.utesdk.watchface.bean.acts.DevicePacketDataInfo;
import com.yc.utesdk.watchface.close.DevicePacketDataUtils;
import com.yc.utesdk.watchface.open.WatchFaceUtil;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/* loaded from: classes4.dex */
public class GpsCommandUtil {
    private static final int SYNC_WATCH_FACE_SECTION = 4;
    private static GpsCommandUtil instance;
    private FileService.MultiCallback mCallback;
    private byte[] mEphemerisParamsData;
    public Handler mHandler;
    private int totalCount;
    private final int head01E9 = 489;
    private final int sendWatchDataFlag = 43780;
    private int mAppCrc = 0;
    private List<CountDownLatch> setEphemerisStatusLatchList = new ArrayList();
    public int NOsectionOnline = 0;
    public int mPhoneMtu = 0;
    private byte[] mWatchFaceDataSend = null;
    public boolean isSendFinisCommand = false;
    private boolean isHadSendWatchFinis = false;
    private final int intervalEnd = 500;
    private int lastErase = 0;
    private int currErase = 0;
    public final int DELAY_SEND_ONLINE_DIAL_MSG = 2;
    private final int SEND_DATA_SEG_MSG = 1;

    public class utendo extends Handler {
        public utendo(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what != 2) {
                return;
            }
            if (!UteBleClient.getUteBleClient().isConnected()) {
                GpsCommandUtil.this.stopOnlineDialData();
                GpsCommandUtil.this.mCallback.onFail(620, new Throwable("蓝牙已断开连接"));
                return;
            }
            if (DeviceBusyLockUtils.getInstance().getDeviceBusy()) {
                GpsCommandUtil.this.utendo(2);
                return;
            }
            GpsCommandUtil gpsCommandUtil = GpsCommandUtil.this;
            if (gpsCommandUtil.NOsectionOnline <= 1) {
                gpsCommandUtil.mCallback.onStart("");
            }
            GpsCommandUtil gpsCommandUtil2 = GpsCommandUtil.this;
            int i2 = gpsCommandUtil2.NOsectionOnline + 1;
            gpsCommandUtil2.NOsectionOnline = i2;
            gpsCommandUtil2.utenif(i2);
            FileService.MultiCallback multiCallback = GpsCommandUtil.this.mCallback;
            GpsCommandUtil gpsCommandUtil3 = GpsCommandUtil.this;
            multiCallback.onProgress("", gpsCommandUtil3.NOsectionOnline, gpsCommandUtil3.totalCount);
        }
    }

    public GpsCommandUtil() {
        utendo();
    }

    public static synchronized GpsCommandUtil getInstance() {
        try {
            if (instance == null) {
                instance = new GpsCommandUtil();
            }
        } catch (Throwable th) {
            throw th;
        }
        return instance;
    }

    public void do56FD(byte[] bArr) {
        if ((bArr[1] & 255) != 253) {
            return;
        }
        int i2 = bArr[2] & 255;
        CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
        UteListenerManager.getInstance().onGPSNotify(6, new GpsState(i2));
    }

    public void doGpsData01E9(byte[] bArr) {
        FileService.MultiCallback multiCallback;
        Throwable th;
        switch (((bArr[2] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[3] & 255)) {
            case 43521:
                int i2 = bArr[4] & 255;
                if (i2 != 253) {
                    int length = bArr.length - 5;
                    byte[] bArr2 = new byte[length];
                    System.arraycopy(bArr, 5, bArr2, 0, length);
                    if (i2 != 0) {
                        this.mEphemerisParamsData = ByteDataUtil.getInstance().copyTwoArrays(this.mEphemerisParamsData, bArr2);
                        break;
                    } else {
                        this.mEphemerisParamsData = bArr2;
                        break;
                    }
                } else {
                    CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                    byte b2 = bArr[5];
                    LogUtils.i("mEphemerisParamsData =" + GBUtils.getInstance().bytes2HexString(this.mEphemerisParamsData));
                    UteListenerManager.getInstance().onGPSNotify(2, utendo(0, 1, 1, this.mEphemerisParamsData));
                    break;
                }
            case 43778:
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                UteListenerManager.getInstance().onGPSStatus(true, 1);
                break;
            case 43779:
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                UteListenerManager.getInstance().onGPSStatus(true, 3);
                break;
            case 44034:
                UteListenerManager.getInstance().onGPSNotify(4, new GpsNetworkParams(bArr[5] & 255));
                break;
            case 44035:
                UteListenerManager.getInstance().onGPSNotify(5, new EphemerisRequired(bArr[5] & 255));
                break;
            case 44036:
                LogUtils.i("返回星历数据传输状态 state =" + (bArr[5] & 255));
                int i3 = bArr[5] & 255;
                if (i3 != 1) {
                    if (i3 != 2) {
                        if (i3 != 3) {
                            if (i3 != 4) {
                                if (i3 != 5) {
                                    if (i3 != 6) {
                                        if (i3 == 7) {
                                            ElpoTimeOut.getInstance().cancelCommandTimeOut();
                                            LogUtils.i("0x07 表示继续发送数据");
                                            utendo(WatchFaceUtil.DELAY_SYNC_WATCH_FACE_DATA_TIME);
                                            break;
                                        }
                                    } else if (bArr.length >= 8) {
                                        this.NOsectionOnline = ((bArr[7] & 255) | ((bArr[6] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK)) - 1;
                                        LogUtils.i("0x06 发送数据不连续，后面跟两个字节为断点序号 NOsectionOnline =" + (this.NOsectionOnline + 1));
                                        break;
                                    }
                                } else {
                                    ElpoTimeOut.getInstance().cancelCommandTimeOut();
                                    multiCallback = this.mCallback;
                                    th = new Throwable("0x05 表示表盘数据文件太大");
                                }
                            } else {
                                ElpoTimeOut.getInstance().cancelCommandTimeOut();
                                stopOnlineDialData();
                                multiCallback = this.mCallback;
                                th = new Throwable("0x04 表示数据CRC校验失败");
                            }
                        } else {
                            ElpoTimeOut.getInstance().cancelCommandTimeOut();
                            multiCallback = this.mCallback;
                            th = new Throwable("0x03 表示取消发送");
                        }
                        multiCallback.onFail(i3, th);
                        break;
                    }
                } else {
                    ElpoTimeOut.getInstance().cancelCommandTimeOut();
                    this.mCallback.onCompleted("");
                    break;
                }
                break;
        }
    }

    public void getEphemerisParams() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(224);
        this.mEphemerisParamsData = new byte[0];
        WriteCommandToBLE.getInstance().writeCharaBle5(new byte[]{1, -23, -86, 1});
    }

    public void queryEphemerisState() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(226);
        WriteCommandToBLE.getInstance().writeChara(new byte[]{86, -3});
    }

    public byte[] readEphemerisFile(@NonNull List<EphemerisFileInfo> list) {
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        StringBuilder sb3 = new StringBuilder();
        Iterator<EphemerisFileInfo> it = list.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            File file = it.next().getFile();
            String name = file.getName();
            if (name.contains(".")) {
                name = name.substring(0, name.lastIndexOf("."));
            }
            String strStringToASCII = GBUtils.getInstance().stringToASCII(name);
            sb2.append(String.format("%02X", Integer.valueOf(strStringToASCII.length() / 2)));
            sb2.append(strStringToASCII);
            int length = (int) file.length();
            sb2.append(String.format("%08X", Integer.valueOf(length)));
            i2 += length;
            LogUtils.i("filename = " + name + ",file.getAbsolutePath()" + file.getAbsolutePath() + ",file.length() = " + file.length());
        }
        sb.append(String.format("%04X", Integer.valueOf(sb2.length() / 2)));
        sb.append((CharSequence) sb2);
        sb3.append(String.format("%08X", Integer.valueOf(i2)));
        sb.append((CharSequence) sb3);
        byte[] bArrHexStringToBytes = GBUtils.getInstance().hexStringToBytes(sb.toString());
        LogUtils.i("所有文件名长度、文明名数据、文件长度  data.length = " + bArrHexStringToBytes.length);
        LogUtils.i("所有文件名长度、文明名数据、文件长度 data1 = " + GBUtils.getInstance().bytes2HexString(bArrHexStringToBytes));
        Iterator<EphemerisFileInfo> it2 = list.iterator();
        while (it2.hasNext()) {
            byte[] bArrUtendo = utendo(it2.next().getFile());
            LogUtils.i("最终 fileData.length = " + bArrUtendo.length);
            bArrHexStringToBytes = ByteDataUtil.getInstance().copyTwoArrays(bArrHexStringToBytes, bArrUtendo);
        }
        LogUtils.i("最终 data.length = " + bArrHexStringToBytes.length);
        return bArrHexStringToBytes;
    }

    public void sendGpsCoordinates(@NonNull GpsCoordinates gpsCoordinates) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(223);
        int timestamp = gpsCoordinates.getTimestamp();
        int latitude = (int) (gpsCoordinates.getLatitude() * 1.0E7d);
        int longitude = (int) (gpsCoordinates.getLongitude() * 1.0E7d);
        WriteCommandToBLE.getInstance().writeCharaBle5(new byte[]{1, -23, -85, 2, 12, (byte) ((timestamp >> 24) & 255), (byte) ((timestamp >> 16) & 255), (byte) ((timestamp >> 8) & 255), (byte) (timestamp & 255), (byte) ((latitude >> 24) & 255), (byte) ((latitude >> 16) & 255), (byte) ((latitude >> 8) & 255), (byte) (latitude & 255), (byte) ((longitude >> 24) & 255), (byte) ((longitude >> 16) & 255), (byte) ((longitude >> 8) & 255), (byte) (longitude & 255)});
    }

    public void setEphemerisStatus(int i2) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(225);
        WriteCommandToBLE.getInstance().writeCharaBle5(new byte[]{1, -23, -85, 3, 1, (byte) i2});
    }

    public void stopOnlineDialData() {
        Handler handler = this.mHandler;
        if (handler != null) {
            this.mWatchFaceDataSend = null;
            this.lastErase = 0;
            this.currErase = 0;
            handler.removeMessages(2);
        }
    }

    public void uploadEphemeris(@NonNull List<EphemerisFileInfo> list, FileService.MultiCallback multiCallback) {
        this.mCallback = multiCallback;
        LogUtils.i("uploadEphemeris  ephemerisFileList = " + new Gson().toJson(list));
        byte[] ephemerisFile = readEphemerisFile(list);
        this.mWatchFaceDataSend = ephemerisFile;
        if (ephemerisFile == null || ephemerisFile.length <= 0) {
            return;
        }
        utendo(ephemerisFile);
        LogUtils.i("textDataWatchFace.length = " + this.mWatchFaceDataSend.length);
    }

    public final void utenfor(int i2) {
        WriteCommandToBLE.getInstance().writeCharaBle5(new byte[]{1, -23, -85, 4, -3, -3, (byte) i2});
    }

    public final EphemerisParams utendo(int i2, int i3, int i4, byte[] bArr) {
        EphemerisParams ephemerisParams = new EphemerisParams();
        List<DevicePacketDataInfo> listDoDevicePacketData = DevicePacketDataUtils.getInstance().doDevicePacketData(i2, i3, i4, bArr);
        for (int i5 = 0; i5 < listDoDevicePacketData.size(); i5++) {
            int flag = listDoDevicePacketData.get(i5).getFlag();
            byte[] packetData = listDoDevicePacketData.get(i5).getPacketData();
            int packetValue = listDoDevicePacketData.get(i5).getPacketValue();
            if (flag == 1) {
                ephemerisParams.setInterval(packetValue);
            } else if (flag == 2) {
                ephemerisParams.setVersion(packetValue);
            } else if (flag == 3) {
                ephemerisParams.setUrlList(utendo(GBUtils.getInstance().asciiByteToString(packetData)));
            }
        }
        return ephemerisParams;
    }

    public final void utenif(int i2) {
        int i3;
        int i4;
        byte[] bArr = this.mWatchFaceDataSend;
        if (bArr == null || (i3 = this.mPhoneMtu) == 0) {
            return;
        }
        int i5 = i3 - 6;
        int length = bArr.length;
        int i6 = length / i5;
        int i7 = length % i5;
        LogUtils.i("length=" + length + ",section =" + i2 + ",sendCount =" + i6 + "，lastCount = " + i7);
        if (i2 == 0) {
            this.isSendFinisCommand = false;
            this.isHadSendWatchFinis = false;
            this.lastErase = 0;
            this.currErase = 0;
            if (i7 == 0) {
                this.totalCount = i6;
            } else {
                this.totalCount = i6 + 1;
            }
        }
        if (i2 >= i6) {
            if (this.isHadSendWatchFinis) {
                LogUtils.i("已经发送260300了，不用再发送了");
            } else if (this.isSendFinisCommand) {
                this.isHadSendWatchFinis = true;
                utenfor(this.mAppCrc);
                this.isSendFinisCommand = false;
                this.mHandler.removeMessages(2);
            } else if (i7 != 0) {
                int i8 = i7 + 6;
                byte[] bArr2 = new byte[i8];
                bArr2[0] = 1;
                bArr2[1] = -23;
                bArr2[2] = -85;
                bArr2[3] = 4;
                bArr2[4] = (byte) ((i2 >> 8) & 255);
                bArr2[5] = (byte) (i2 & 255);
                System.arraycopy(bArr, i5 * i2, bArr2, 6, i7);
                for (int i9 = 0; i9 < i8; i9++) {
                    this.mAppCrc = bArr2[i9] ^ this.mAppCrc;
                }
                WriteCommandToBLE.getInstance().writeCharaBle5(bArr2);
                LogUtils.i("1最后一条延迟500ms, section =" + i2);
                utendo(500);
            }
            this.isSendFinisCommand = true;
            return;
        }
        byte[] bArr3 = new byte[i3];
        bArr3[0] = 1;
        bArr3[1] = -23;
        bArr3[2] = -85;
        bArr3[3] = 4;
        bArr3[4] = (byte) ((i2 >> 8) & 255);
        bArr3[5] = (byte) (i2 & 255);
        System.arraycopy(bArr, i5 * i2, bArr3, 6, i5);
        for (int i10 = 0; i10 < i3; i10++) {
            this.mAppCrc = bArr3[i10] ^ this.mAppCrc;
        }
        WriteCommandToBLE.getInstance().writeCharaBle5(bArr3);
        if (i2 == i6 - 1 && i7 == 0) {
            this.isSendFinisCommand = true;
            LogUtils.i("0最后一条延迟500ms, section =" + i2);
            i4 = 500;
        } else {
            int i11 = ((i2 + 1) * i5) / 4096;
            this.currErase = i11;
            if (this.lastErase != i11) {
                this.lastErase = i11;
                LogUtils.i("等待BLE返回01E3AC020107,section =" + i2);
                ElpoTimeOut.getInstance().setCommandTimeOut(4);
                return;
            }
            if (i2 <= 0 || i2 % 10 != 0) {
                utendo(0);
                return;
            }
            i4 = WatchFaceUtil.DELAY_SYNC_WATCH_FACE_DATA_TIME;
        }
        utendo(i4);
    }

    public final void utendo() {
        HandlerThread handlerThread = new HandlerThread("HandlerThread");
        handlerThread.start();
        this.mHandler = new utendo(handlerThread.getLooper());
    }

    public final byte[] utendo(File file) {
        byte[] bArr = new byte[0];
        if (file == null || file.length() <= 0) {
            return bArr;
        }
        try {
            return WatchFaceUtil.getInstance().readBinToByte(file);
        } catch (IOException e2) {
            e2.printStackTrace();
            return bArr;
        }
    }

    public final void utendo(int i2) {
        Message message = new Message();
        message.what = 2;
        this.mHandler.sendMessageDelayed(message, i2);
    }

    public final void utendo(byte[] bArr) {
        this.NOsectionOnline = 0;
        this.mWatchFaceDataSend = bArr;
        this.isSendFinisCommand = false;
        this.isHadSendWatchFinis = false;
        this.lastErase = 0;
        this.currErase = 0;
        this.mAppCrc = 0;
        this.mPhoneMtu = SPUtil.getInstance().getMaxCommunicationLength();
        utenif(this.NOsectionOnline);
    }

    public final List utendo(String str) {
        return new ArrayList(Arrays.asList(str.split(i.f9802b)));
    }
}
