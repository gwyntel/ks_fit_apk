package com.yc.utesdk.watchface.close;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import androidx.annotation.NonNull;
import androidx.core.view.MotionEventCompat;
import androidx.webkit.ProxyConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vivo.push.BuildConfig;
import com.yc.utesdk.ble.close.DeviceBusyLockUtils;
import com.yc.utesdk.ble.close.KeyType;
import com.yc.utesdk.ble.open.UteBleClient;
import com.yc.utesdk.command.CommandTimeOutUtils;
import com.yc.utesdk.command.WriteCommandToBLE;
import com.yc.utesdk.listener.FileService;
import com.yc.utesdk.listener.UteListenerManager;
import com.yc.utesdk.log.LogUtils;
import com.yc.utesdk.utils.open.ByteDataUtil;
import com.yc.utesdk.utils.open.ByteUtil;
import com.yc.utesdk.utils.open.GBUtils;
import com.yc.utesdk.utils.open.SPUtil;
import com.yc.utesdk.watchface.bean.acts.ApplyWatchFace;
import com.yc.utesdk.watchface.bean.acts.DevicePacketDataInfo;
import com.yc.utesdk.watchface.bean.acts.DeviceWatchFaceDao;
import com.yc.utesdk.watchface.bean.acts.ImageWatchFace;
import com.yc.utesdk.watchface.bean.acts.ImageWatchFaceConfig;
import com.yc.utesdk.watchface.bean.acts.SingleFileRequired;
import com.yc.utesdk.watchface.bean.acts.WatchFaceInfo;
import com.yc.utesdk.watchface.bean.acts.WatchFaceParams;
import com.yc.utesdk.watchface.bean.acts.WatchFaceReport;
import com.yc.utesdk.watchface.open.WatchFaceUtil;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes4.dex */
public class ActsDialCommandUtil {
    public static final int SEND_DATA_SEG_DELAY_TIME = 20;
    public static final int SYNC_WATCH_FACE_SECTION = 4;
    private static final String TAG = "ActsDialUtil--";
    private static ActsDialCommandUtil instance;
    private ApplyWatchFace mApplyWatchFace;
    private FileService.Callback mCallback;
    private File mDownloadImageFile;
    private String mDownloadImageName;
    public Handler mHandler;
    private ImageWatchFace mImageWatchFace;
    private ImageWatchFaceConfig mImageWatchFaceConfig;
    private WatchFaceInfo mWatchFaceInfo;
    private WatchFaceParams mWatchFaceParams;
    private int totalCount;
    private final int head01E3 = 483;
    private final int head01E4 = BuildConfig.VERSION_CODE;
    private final int WATCH_FACE_TYPE_ONLINE = 1;
    private final int WATCH_FACE_TYPE_IMAGE = 2;
    private int sendWatchDataFlag = 43777;
    public int NOsectionOnline = 0;
    private byte[] mWatchFaceDataSend = null;
    private byte[] mDownloadImageWatchFaceData = new byte[0];
    private int mDownloadImageWatchFaceDataSize = 1;
    private int mCurrDownloadImageWatchFaceDataSize = 0;
    public boolean isSendFinisCommand = false;
    private boolean isHadSendWatchFinis = false;
    public int mPhoneMtu = 0;
    private final int intervalEnd = 500;
    private int lastErase = 0;
    private int currErase = 0;
    private byte[] mWatchFaceParamsData = new byte[0];
    private byte[] mImageWatchFaceData = new byte[0];
    private byte[] mWatchFaceInfoData = new byte[0];
    private byte[] mAllSendData = null;
    private boolean isSendDataFD = false;
    private int mAppCrc = 0;
    private int mDataSegmentsFlag = 0;
    private int watchFaceType = 1;
    private final int SEND_DATA_SEG_MSG = 1;
    public final int DELAY_SEND_ONLINE_DIAL_MSG = 2;
    private OnlineDialTimeOut mOnlineDialTimeOut = OnlineDialTimeOut.getInstance();

    public class utendo extends Handler {
        public utendo(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i2;
            int i3 = message.what;
            if (i3 == 1) {
                if (!DeviceBusyLockUtils.getInstance().getDeviceBusy()) {
                    ActsDialCommandUtil.this.utenfor(message.arg1);
                    return;
                }
                LogUtils.i("设备忙，等20ms");
                Message message2 = new Message();
                message2.what = 1;
                message2.arg1 = message.arg1;
                ActsDialCommandUtil.this.mHandler.sendMessageDelayed(message2, 20L);
                return;
            }
            if (i3 != 2) {
                return;
            }
            if (!UteBleClient.getUteBleClient().isConnected()) {
                ActsDialCommandUtil.this.stopOnlineDialData();
                UteListenerManager.getInstance().onWatchFaceOnlineStatus(6);
                return;
            }
            if (DeviceBusyLockUtils.getInstance().getDeviceBusy()) {
                ActsDialCommandUtil.this.utennew(2);
                return;
            }
            ActsDialCommandUtil actsDialCommandUtil = ActsDialCommandUtil.this;
            actsDialCommandUtil.NOsectionOnline++;
            if (actsDialCommandUtil.totalCount > 0) {
                ActsDialCommandUtil actsDialCommandUtil2 = ActsDialCommandUtil.this;
                i2 = (actsDialCommandUtil2.NOsectionOnline * 100) / actsDialCommandUtil2.totalCount;
            } else {
                i2 = 0;
            }
            UteListenerManager.getInstance().onWatchFaceOnlineProgress(i2);
            LogUtils.i("progress = " + i2 + ",current =" + ActsDialCommandUtil.this.NOsectionOnline + ",totalCount =" + ActsDialCommandUtil.this.totalCount);
            ActsDialCommandUtil actsDialCommandUtil3 = ActsDialCommandUtil.this;
            actsDialCommandUtil3.utentry(actsDialCommandUtil3.NOsectionOnline);
        }
    }

    public ActsDialCommandUtil() {
        utendo();
    }

    public static ActsDialCommandUtil getInstance() {
        if (instance == null) {
            instance = new ActsDialCommandUtil();
        }
        return instance;
    }

    public void abortDialBin(int i2, int i3) {
        LogUtils.i(TAG, "abortDialBin");
        CommandTimeOutUtils.getInstance().setCommandTimeOut(KeyType.ABORT_DIAL_BIN);
        WriteCommandToBLE.getInstance().writeCharaBle5(new byte[]{1, -29, -85, 7, 2, (byte) (i2 & 255), (byte) (i3 & 255)});
    }

    public WatchFaceParams analyticWatchFaceParamsData(int i2, int i3, int i4, byte[] bArr) {
        List<DevicePacketDataInfo> listDoDevicePacketData = DevicePacketDataUtils.getInstance().doDevicePacketData(i2, i3, i4, bArr);
        WatchFaceParams watchFaceParams = new WatchFaceParams();
        for (int i5 = 0; i5 < listDoDevicePacketData.size(); i5++) {
            int flag = listDoDevicePacketData.get(i5).getFlag();
            listDoDevicePacketData.get(i5).getLength();
            byte[] packetData = listDoDevicePacketData.get(i5).getPacketData();
            int packetValue = listDoDevicePacketData.get(i5).getPacketValue();
            switch (flag) {
                case 43521:
                    watchFaceParams.setMaxWatchFaceVersion(GBUtils.getInstance().asciiByteToString(packetData));
                    break;
                case 43522:
                    watchFaceParams.setWidth(packetValue);
                    break;
                case 43523:
                    watchFaceParams.setHeight(packetValue);
                    break;
                case 43524:
                    watchFaceParams.setSupportFileType(packetValue);
                    break;
                case 43525:
                    watchFaceParams.setSupportSort(packetValue == 1);
                    break;
                case 43526:
                    watchFaceParams.setEarlyWatchFaceVersion(GBUtils.getInstance().asciiByteToString(packetData));
                    break;
                case 43527:
                    if (packetData == null) {
                        break;
                    } else if (packetData.length == 1) {
                        watchFaceParams.setScreenType(packetValue);
                        break;
                    } else if (packetData.length == 3) {
                        watchFaceParams.setScreenType(packetData[0] & 255);
                        watchFaceParams.setScreenCorner(((packetData[1] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (packetData[2] & 255));
                        break;
                    } else {
                        break;
                    }
                case 43528:
                    watchFaceParams.setMaxFileSize(packetValue);
                    break;
                case 43529:
                    watchFaceParams.setWatchFaceCount(packetValue);
                    break;
            }
        }
        return watchFaceParams;
    }

    public void applyWatchFace(ApplyWatchFace applyWatchFace) {
        LogUtils.i(TAG, "39.3 应用表盘 applyWatchFace 01E3AB050A");
        LogUtils.i(TAG, "39.3 应用表盘 list =" + new Gson().toJson(applyWatchFace));
        CommandTimeOutUtils.getInstance().setCommandTimeOut(200);
        this.mApplyWatchFace = new ApplyWatchFace();
        WriteCommandToBLE.getInstance().writeCharaBle5(GBUtils.getInstance().hexStringToBytes("01E3AB050A" + ByteUtil.watchFaceIdToByteStr(applyWatchFace.getId()) + GBUtils.getInstance().stringToASCII(applyWatchFace.getVersion()) + String.format("%02X", Integer.valueOf(applyWatchFace.getOperate()))));
    }

    public void doWatchFaceData01E3(byte[] bArr) {
        UteListenerManager uteListenerManager;
        Object obj;
        int i2;
        int i3;
        UteListenerManager uteListenerManager2;
        switch (((bArr[2] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[3] & 255)) {
            case 43531:
                int i4 = bArr[4] & 255;
                if (i4 != 253) {
                    int length = bArr.length - 5;
                    byte[] bArr2 = new byte[length];
                    System.arraycopy(bArr, 5, bArr2, 0, length);
                    if (i4 == 0) {
                        this.mWatchFaceInfoData = bArr2;
                        return;
                    } else {
                        this.mWatchFaceInfoData = ByteDataUtil.getInstance().copyTwoArrays(this.mWatchFaceInfoData, bArr2);
                        return;
                    }
                }
                byte b2 = bArr[5];
                LogUtils.i("mWatchFaceInfoData =" + GBUtils.getInstance().bytes2HexString(this.mWatchFaceInfoData));
                WatchFaceInfo watchFaceInfoUtenfor = utenfor(this.mWatchFaceInfoData);
                this.mWatchFaceInfo = watchFaceInfoUtenfor;
                List<WatchFaceInfo.WatchFace> workFaceList = watchFaceInfoUtenfor.getWorkFaceList();
                new ArrayList();
                SPUtil.getInstance().setWatchFaceInfo((List) new Gson().fromJson(new Gson().toJson(workFaceList), new TypeToken<List<DeviceWatchFaceDao>>() { // from class: com.yc.utesdk.watchface.close.ActsDialCommandUtil.2
                }.getType()));
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                uteListenerManager = UteListenerManager.getInstance();
                obj = this.mWatchFaceInfo;
                i2 = 14;
                break;
            case 43532:
                int i5 = bArr[4] & 255;
                if (i5 != 253) {
                    int length2 = bArr.length - 5;
                    byte[] bArr3 = new byte[length2];
                    System.arraycopy(bArr, 5, bArr3, 0, length2);
                    if (i5 == 0) {
                        this.mImageWatchFaceData = bArr3;
                        return;
                    } else {
                        this.mImageWatchFaceData = ByteDataUtil.getInstance().copyTwoArrays(this.mImageWatchFaceData, bArr3);
                        return;
                    }
                }
                byte b3 = bArr[5];
                LogUtils.i("mImageWatchFaceData =" + GBUtils.getInstance().bytes2HexString(this.mImageWatchFaceData));
                this.mImageWatchFace = utendo(1, 1, 1, this.mImageWatchFaceData);
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                SPUtil.getInstance().setGetImageWatchFaceSp(this.mImageWatchFace);
                uteListenerManager = UteListenerManager.getInstance();
                obj = this.mImageWatchFace;
                i2 = 18;
                break;
            case 43690:
                int i6 = bArr[4] & 255;
                if (i6 != 253) {
                    int length3 = bArr.length - 5;
                    byte[] bArr4 = new byte[length3];
                    System.arraycopy(bArr, 5, bArr4, 0, length3);
                    if (i6 == 0) {
                        this.mWatchFaceParamsData = bArr4;
                        return;
                    } else {
                        this.mWatchFaceParamsData = ByteDataUtil.getInstance().copyTwoArrays(this.mWatchFaceParamsData, bArr4);
                        return;
                    }
                }
                byte b4 = bArr[5];
                LogUtils.i("mWatchFaceParamsData =" + GBUtils.getInstance().bytes2HexString(this.mWatchFaceParamsData));
                this.mWatchFaceParams = analyticWatchFaceParamsData(0, 2, 1, this.mWatchFaceParamsData);
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                int width = this.mWatchFaceParams.getWidth();
                int height = this.mWatchFaceParams.getHeight();
                int screenType = this.mWatchFaceParams.getScreenType();
                int maxFileSize = this.mWatchFaceParams.getMaxFileSize();
                int screenCorner = this.mWatchFaceParams.getScreenCorner();
                SPUtil.getInstance().setDialNumber(0);
                SPUtil.getInstance().setResolutionWidthHeight(width + ProxyConfig.MATCH_ALL_SCHEMES + height);
                SPUtil.getInstance().setWatchFaceShapeType(screenType);
                SPUtil.getInstance().setDialMaxDataSize(maxFileSize + "");
                SPUtil.getInstance().setDialScreenCompatibleLevel(2);
                SPUtil.getInstance().setDialScreenCornerAngle(screenCorner);
                SPUtil.getInstance().setWatchFaceConfigurationSuccess(true);
                SPUtil.getInstance().setWatchFaceParams(this.mWatchFaceParams);
                LogUtils.i("mWatchFaceParams =" + new Gson().toJson(this.mWatchFaceParams));
                uteListenerManager = UteListenerManager.getInstance();
                obj = this.mWatchFaceParams;
                i2 = 12;
                break;
            case 43781:
                this.mApplyWatchFace = utendo(bArr);
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                uteListenerManager = UteListenerManager.getInstance();
                obj = this.mApplyWatchFace;
                i2 = 16;
                break;
            case 43782:
                int i7 = bArr[4] & 255;
                if (i7 != 253) {
                    utenint(i7);
                    return;
                }
                LogUtils.i("Send setImageWatchFace 完成 bleCrc =" + (bArr[5] & 255));
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                uteListenerManager = UteListenerManager.getInstance();
                obj = this.mImageWatchFaceConfig;
                i2 = 20;
                break;
            case 43783:
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                UteListenerManager.getInstance().onWatchFaceParams(30, null);
                return;
            case 44033:
                UteListenerManager.getInstance().onWatchFaceParams(24, utenfor(5, 1, 1, bArr));
                return;
            case 44034:
                int i8 = bArr[5] & 255;
                if (i8 == 1) {
                    UteListenerManager.getInstance().onWatchFaceOnlineStatus(5);
                    return;
                }
                if (i8 == 2) {
                    LogUtils.i("0x02 表示设备端已经准备好，可以发送数据");
                    CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                    sendOnlineDialData(this.mWatchFaceDataSend);
                    return;
                }
                if (i8 == 3) {
                    uteListenerManager2 = UteListenerManager.getInstance();
                    i3 = 10;
                } else {
                    i3 = 6;
                    if (i8 == 4) {
                        stopOnlineDialData();
                        uteListenerManager2 = UteListenerManager.getInstance();
                    } else {
                        if (i8 == 5) {
                            UteListenerManager.getInstance().onWatchFaceOnlineStatus(7);
                            return;
                        }
                        if (i8 == 6) {
                            if (bArr.length >= 8) {
                                this.NOsectionOnline = ((bArr[7] & 255) | ((bArr[6] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK)) - 1;
                                LogUtils.i("0x06 发送数据不连续，后面跟两个字节为断点序号 NOsectionOnline =" + (this.NOsectionOnline + 1));
                                return;
                            }
                            return;
                        }
                        if (i8 == 7) {
                            this.mOnlineDialTimeOut.cancelCommandTimeOut();
                            LogUtils.i("0x07 表示继续发送数据");
                            utennew(WatchFaceUtil.DELAY_SYNC_WATCH_FACE_DATA_TIME);
                            return;
                        } else {
                            if (i8 != 8) {
                                return;
                            }
                            uteListenerManager2 = UteListenerManager.getInstance();
                            i3 = 11;
                        }
                    }
                }
                uteListenerManager2.onWatchFaceOnlineStatus(i3);
                return;
            default:
                return;
        }
        uteListenerManager.onWatchFaceParams(i2, obj);
    }

    public void doWatchFaceData01E4(byte[] bArr) throws IOException {
        int iUtennew;
        int i2 = ((bArr[2] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[3] & 255);
        if (i2 != 43522) {
            if (i2 != 44033) {
                return;
            }
            int iUtenint = utenint(bArr);
            SingleFileRequired singleFileRequired = new SingleFileRequired();
            singleFileRequired.setIndex(iUtenint);
            UteListenerManager.getInstance().onWatchFaceParams(22, singleFileRequired);
            return;
        }
        int i3 = ((bArr[4] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[5] & 255);
        StringBuilder sb = new StringBuilder();
        sb.append("AA02 segNumber1=");
        sb.append(i3);
        sb.append(",isEnd =");
        sb.append(i3 == 65021);
        LogUtils.i(sb.toString());
        if (i3 == 65021) {
            LogUtils.i("完成了？");
            byte b2 = bArr[6];
            LogUtils.i("isSuccess =" + saveBitmapFile(utenif(this.mDownloadImageWatchFaceData), this.mDownloadImageFile, this.mDownloadImageName));
            this.mCallback.onCompleted();
            return;
        }
        int length = bArr.length - 6;
        byte[] bArr2 = new byte[length];
        System.arraycopy(bArr, 6, bArr2, 0, length);
        if (i3 == 0) {
            this.mDownloadImageWatchFaceData = bArr2;
            if (length < 64) {
                return;
            } else {
                iUtennew = utennew(bArr2);
            }
        } else {
            byte[] bArrCopyTwoArrays = ByteDataUtil.getInstance().copyTwoArrays(this.mDownloadImageWatchFaceData, bArr2);
            this.mDownloadImageWatchFaceData = bArrCopyTwoArrays;
            int i4 = this.mDownloadImageWatchFaceDataSize;
            if (i4 != 1) {
                int length2 = this.mCurrDownloadImageWatchFaceDataSize + (bArr.length - 6);
                this.mCurrDownloadImageWatchFaceDataSize = length2;
                this.mCallback.onProgress(length2, i4);
                return;
            } else if (bArrCopyTwoArrays.length < 64) {
                return;
            } else {
                iUtennew = utennew(bArrCopyTwoArrays);
            }
        }
        int i5 = iUtennew + 64;
        this.mDownloadImageWatchFaceDataSize = i5;
        int length3 = bArr.length - 6;
        this.mCurrDownloadImageWatchFaceDataSize = length3;
        this.mCallback.onProgress(length3, i5);
    }

    public void downloadImageWatchFace(@NonNull File file, String str, int i2, FileService.Callback callback) {
        LogUtils.i(TAG, "39.1001 下载相册表盘文件 downloadImageWatchFace");
        this.mCallback = callback;
        this.mDownloadImageFile = file;
        this.mDownloadImageName = str;
        this.mDownloadImageWatchFaceData = new byte[0];
        WriteCommandToBLE.getInstance().writeCharaBle5(new byte[]{1, -28, -86, 2, 1, (byte) i2});
    }

    public void getImageWatchFace() {
        LogUtils.i(TAG, "39.8 获取相片表盘信息  getImageWatchFace 01E3 AA0C");
        CommandTimeOutUtils.getInstance().setCommandTimeOut(197);
        this.mImageWatchFaceData = new byte[0];
        WriteCommandToBLE.getInstance().writeCharaBle5(new byte[]{1, -29, -86, 12});
    }

    public void getWatchFaceInfo() {
        LogUtils.i(TAG, "39.2 获取设备表盘信息 getWatchFaceInfo 01E3 AA0B");
        CommandTimeOutUtils.getInstance().setCommandTimeOut(199);
        this.mWatchFaceInfo = new WatchFaceInfo();
        this.mWatchFaceInfoData = new byte[0];
        WriteCommandToBLE.getInstance().writeCharaBle5(new byte[]{1, -29, -86, 11});
    }

    public void getWatchFaceParams() {
        LogUtils.i(TAG, "39.1 获取设备表盘参数 getWatchFaceParams 01E3 AAAA");
        CommandTimeOutUtils.getInstance().setCommandTimeOut(194);
        WriteCommandToBLE.getInstance().writeCharaBle5(new byte[]{1, -29, -86, -86});
    }

    public int getWatchFaceType() {
        return this.watchFaceType;
    }

    public boolean saveBitmapFile(Bitmap bitmap, File file, String str) throws IOException {
        try {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bufferedOutputStream);
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
            return true;
        } catch (IOException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public void sendOnlineDialData(byte[] bArr) {
        this.NOsectionOnline = 0;
        this.mWatchFaceDataSend = bArr;
        this.isSendFinisCommand = false;
        this.isHadSendWatchFinis = false;
        this.lastErase = 0;
        this.currErase = 0;
        this.mPhoneMtu = SPUtil.getInstance().getMaxCommunicationLength();
        utentry(this.NOsectionOnline);
    }

    public void setImageWatchFace(ImageWatchFaceConfig imageWatchFaceConfig) {
        byte[] bArrUtendo;
        LogUtils.i(TAG, "39.9 设置相片表盘  setImageWatchFace ");
        CommandTimeOutUtils.getInstance().setCommandTimeOut(198);
        ImageWatchFaceConfig imageWatchFaceConfig2 = new ImageWatchFaceConfig();
        this.mImageWatchFaceConfig = imageWatchFaceConfig2;
        if (imageWatchFaceConfig == null) {
            bArrUtendo = new byte[1];
        } else {
            imageWatchFaceConfig2.setImageCount(imageWatchFaceConfig.getImageCount());
            this.mImageWatchFaceConfig.setPositionIndex(imageWatchFaceConfig.getPositionIndex());
            this.mImageWatchFaceConfig.setStyleIndex(imageWatchFaceConfig.getStyleIndex());
            this.mImageWatchFaceConfig.setTransferNum(imageWatchFaceConfig.getTransferNum());
            this.mImageWatchFaceConfig.setImageInfoConfigList(imageWatchFaceConfig.getImageInfoConfigList());
            this.mImageWatchFaceConfig = imageWatchFaceConfig;
            bArrUtendo = utendo(imageWatchFaceConfig);
        }
        utendo(ByteDataUtil.getInstance().copyTwoArrays(new byte[]{(byte) bArrUtendo.length}, bArrUtendo), 43782);
    }

    public void setWatchFaceType(int i2) {
        this.watchFaceType = i2;
        this.sendWatchDataFlag = i2 == 2 ? 43778 : 43777;
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

    public void uploadImageWatchFace(@NonNull File file, int i2) {
        LogUtils.i(TAG, "39.1002 上传相册表盘文件 uploadImageWatchFace");
        CommandTimeOutUtils.getInstance().setCommandTimeOut(196);
        this.mWatchFaceDataSend = utendo(file, i2);
        setWatchFaceType(2);
        utenif();
    }

    public void uploadWatchFace(File file) {
        LogUtils.i(TAG, "39.1000 上传在线表盘 uploadWatchFace");
        CommandTimeOutUtils.getInstance().setCommandTimeOut(195);
        this.mWatchFaceDataSend = utendo(file);
        setWatchFaceType(1);
        utenif();
    }

    public final int utenint(byte[] bArr) {
        int i2;
        int i3;
        byte b2;
        if (bArr == null || bArr.length < 5 || (i2 = bArr[4] & 255) == 0) {
            return 0;
        }
        if (i2 == 1) {
            return bArr[5] & 255;
        }
        if (i2 == 2) {
            i3 = (bArr[5] & 255) << 8;
            b2 = bArr[6];
        } else if (i2 == 3) {
            i3 = ((bArr[5] & 255) << 16) | ((bArr[6] & 255) << 8);
            b2 = bArr[7];
        } else {
            i3 = ((bArr[5] & 255) << 24) | ((bArr[6] & 255) << 16) | ((bArr[7] & 255) << 8);
            b2 = bArr[8];
        }
        return (b2 & 255) | i3;
    }

    public final int utennew(byte[] bArr) {
        if (bArr.length >= 64) {
            return utendo(bArr, 56, 4);
        }
        return 0;
    }

    public final void utentry(int i2) {
        int i3;
        byte[] bArr = this.mWatchFaceDataSend;
        if (bArr == null || (i3 = this.mPhoneMtu) == 0) {
            return;
        }
        int i4 = i3 - 6;
        int length = bArr.length;
        int i5 = length / i4;
        int i6 = length % i4;
        LogUtils.i("length=" + length + ",section =" + i2 + ",sendCount =" + i5 + "，lastCount = " + i6);
        if (i2 == 0) {
            this.isSendFinisCommand = false;
            this.isHadSendWatchFinis = false;
            this.lastErase = 0;
            this.currErase = 0;
            if (i6 == 0) {
                this.totalCount = i5;
            } else {
                this.totalCount = i5 + 1;
            }
        }
        if (i2 >= i5) {
            if (this.isHadSendWatchFinis) {
                LogUtils.i("已经发送260300了，不用再发送了");
            } else if (this.isSendFinisCommand) {
                this.isHadSendWatchFinis = true;
                utenfor();
                this.isSendFinisCommand = false;
                this.mHandler.removeMessages(2);
            } else if (i6 != 0) {
                byte[] bArr2 = new byte[i6 + 6];
                bArr2[0] = 1;
                bArr2[1] = -28;
                int i7 = this.sendWatchDataFlag;
                bArr2[2] = (byte) ((i7 >> 8) & 255);
                bArr2[3] = (byte) (i7 & 255);
                bArr2[4] = (byte) ((i2 >> 8) & 255);
                bArr2[5] = (byte) (i2 & 255);
                System.arraycopy(bArr, i4 * i2, bArr2, 6, i6);
                WriteCommandToBLE.getInstance().writeCharaBle5(bArr2);
                LogUtils.i("1最后一条延迟500ms, section =" + i2);
                utennew(500);
            }
            this.isSendFinisCommand = true;
            return;
        }
        byte[] bArr3 = new byte[i3];
        bArr3[0] = 1;
        bArr3[1] = -28;
        int i8 = this.sendWatchDataFlag;
        bArr3[2] = (byte) ((i8 >> 8) & 255);
        bArr3[3] = (byte) (i8 & 255);
        bArr3[4] = (byte) ((i2 >> 8) & 255);
        bArr3[5] = (byte) (i2 & 255);
        System.arraycopy(bArr, i4 * i2, bArr3, 6, i4);
        WriteCommandToBLE.getInstance().writeCharaBle5(bArr3);
        if (i2 == i5 - 1 && i6 == 0) {
            this.isSendFinisCommand = true;
            LogUtils.i("0最后一条延迟500ms, section =" + i2);
            utennew(500);
            return;
        }
        int i9 = ((i2 + 1) * i4) / 4096;
        this.currErase = i9;
        if (this.lastErase == i9) {
            if (i2 <= 0 || i2 % 10 != 0) {
                utennew(0);
                return;
            } else {
                utennew(WatchFaceUtil.DELAY_SYNC_WATCH_FACE_DATA_TIME);
                return;
            }
        }
        this.lastErase = i9;
        LogUtils.i("等待BLE返回01E3AC020107,section =" + i2);
        this.mOnlineDialTimeOut.setCommandTimeOut(4);
    }

    public final WatchFaceReport utenfor(int i2, int i3, int i4, byte[] bArr) {
        List<DevicePacketDataInfo> listDoDevicePacketData = DevicePacketDataUtils.getInstance().doDevicePacketData(i2, i3, i4, bArr);
        WatchFaceReport watchFaceReport = new WatchFaceReport();
        for (int i5 = 0; i5 < listDoDevicePacketData.size(); i5++) {
            int flag = listDoDevicePacketData.get(i5).getFlag();
            listDoDevicePacketData.get(i5).getLength();
            byte[] packetData = listDoDevicePacketData.get(i5).getPacketData();
            int packetValue = listDoDevicePacketData.get(i5).getPacketValue();
            if (flag == 1) {
                watchFaceReport.setId(ByteUtil.watchFaceIdToString(ByteUtil.byteArrayToInt(packetData)));
            } else if (flag == 2) {
                watchFaceReport.setVersion(GBUtils.getInstance().asciiByteToString(packetData));
            } else if (flag == 3) {
                watchFaceReport.setReportType(packetValue);
            }
        }
        return watchFaceReport;
    }

    public final Bitmap utenif(byte[] bArr) {
        byte[] bArrCopyOf = Arrays.copyOf(bArr, 64);
        LogUtils.i("header =" + GBUtils.getInstance().bytes2HexString(bArrCopyOf));
        int iUtendo = utendo(bArrCopyOf, 48, 2);
        int iUtendo2 = utendo(bArrCopyOf, 50, 2);
        LogUtils.i("width =" + iUtendo + ",height =" + iUtendo2);
        int i2 = iUtendo * iUtendo2 * 2;
        StringBuilder sb = new StringBuilder();
        sb.append("decompressedLength =");
        sb.append(i2);
        LogUtils.i(sb.toString());
        int length = bArr.length - 64;
        byte[] bArr2 = new byte[length];
        System.arraycopy(bArr, 64, bArr2, 0, length);
        byte[] bArrUteDecompress = UteCompressUtil.getInstance().uteDecompress(bArr2, i2);
        LogUtils.i("decompressData.length =" + bArrUteDecompress.length);
        return WatchFaceUtil.getInstance().data565ToBitmap8888(bArrUteDecompress, iUtendo, iUtendo2);
    }

    public final void utenint(int i2) {
        Message message = new Message();
        message.what = 1;
        message.arg1 = i2 + 1;
        this.mHandler.sendMessageDelayed(message, 0L);
    }

    public final void utennew(int i2) {
        Message message = new Message();
        message.what = 2;
        this.mHandler.sendMessageDelayed(message, i2);
    }

    public final ApplyWatchFace utendo(byte[] bArr) {
        ApplyWatchFace applyWatchFace = new ApplyWatchFace();
        if (bArr.length < 5) {
            return applyWatchFace;
        }
        applyWatchFace.setId(ByteUtil.watchFaceIdToString(ByteUtil.byteArrayToInt(bArr, 5)));
        byte[] bArr2 = new byte[5];
        System.arraycopy(bArr, 9, bArr2, 0, 5);
        applyWatchFace.setVersion(GBUtils.getInstance().asciiByteToString(bArr2));
        applyWatchFace.setNeedReceiveFile((bArr[14] & 255) == 1);
        return applyWatchFace;
    }

    public final WatchFaceInfo utenfor(byte[] bArr) {
        WatchFaceInfo watchFaceInfo = new WatchFaceInfo();
        ArrayList arrayList = new ArrayList();
        for (int i2 = 1; i2 < bArr.length; i2 += 10) {
            LogUtils.i("WatchFaceInfo  i =" + i2);
            byte[] bArr2 = new byte[4];
            System.arraycopy(bArr, i2, bArr2, 0, 4);
            String strWatchFaceIdToString = ByteUtil.watchFaceIdToString(ByteUtil.byteArrayToInt(bArr2));
            byte[] bArr3 = new byte[5];
            System.arraycopy(bArr, i2 + 4, bArr3, 0, 5);
            String strAsciiByteToString = GBUtils.getInstance().asciiByteToString(bArr3);
            int i3 = bArr[i2 + 9] & 255;
            WatchFaceInfo.WatchFace watchFace = new WatchFaceInfo.WatchFace();
            watchFace.setId(strWatchFaceIdToString);
            watchFace.setVersion(strAsciiByteToString);
            watchFace.setType(i3);
            arrayList.add(watchFace);
        }
        watchFaceInfo.setWorkFaceList(arrayList);
        LogUtils.i("WatchFaceInfo  =" + new Gson().toJson(watchFaceInfo));
        return watchFaceInfo;
    }

    public final List utenif(int i2, int i3, int i4, byte[] bArr) {
        List<DevicePacketDataInfo> listDoDevicePacketData = DevicePacketDataUtils.getInstance().doDevicePacketData(i2, i3, i4, bArr);
        ArrayList arrayList = new ArrayList();
        for (int i5 = 0; i5 < listDoDevicePacketData.size(); i5++) {
            ImageWatchFace.ImageInfo imageInfo = new ImageWatchFace.ImageInfo();
            int flag = listDoDevicePacketData.get(i5).getFlag();
            byte[] packetData = listDoDevicePacketData.get(i5).getPacketData();
            imageInfo.setIndex(flag);
            imageInfo.setName(GBUtils.getInstance().asciiByteToString(packetData));
            arrayList.add(imageInfo);
        }
        return arrayList;
    }

    public final ImageWatchFace utendo(int i2, int i3, int i4, byte[] bArr) {
        List<DevicePacketDataInfo> listDoDevicePacketData = DevicePacketDataUtils.getInstance().doDevicePacketData(i2, i3, i4, bArr);
        ImageWatchFace imageWatchFace = new ImageWatchFace();
        for (int i5 = 0; i5 < listDoDevicePacketData.size(); i5++) {
            int flag = listDoDevicePacketData.get(i5).getFlag();
            listDoDevicePacketData.get(i5).getLength();
            byte[] packetData = listDoDevicePacketData.get(i5).getPacketData();
            int packetValue = listDoDevicePacketData.get(i5).getPacketValue();
            switch (flag) {
                case 1:
                    imageWatchFace.setMaxCount(packetValue);
                    break;
                case 2:
                    imageWatchFace.setCanIntellectColor(packetValue == 1);
                    break;
                case 3:
                    imageWatchFace.setImageType(packetValue);
                    break;
                case 4:
                    imageWatchFace.setPositionIndex(packetValue);
                    break;
                case 5:
                    imageWatchFace.setStyleIndex(packetValue);
                    break;
                case 6:
                    imageWatchFace.setImageInfoList(utenif(0, 1, 1, packetData));
                    break;
                case 7:
                    imageWatchFace.setTransferNum(packetValue);
                    break;
                case 8:
                    imageWatchFace.setTimeColor(packetValue);
                    break;
                case 9:
                    imageWatchFace.setDateColor(packetValue);
                    break;
            }
        }
        return imageWatchFace;
    }

    public final void utenfor(int i2) {
        byte[] bArr = this.mAllSendData;
        if (bArr == null) {
            return;
        }
        int maxCommunicationLength = SPUtil.getInstance().getMaxCommunicationLength();
        int i3 = maxCommunicationLength - 5;
        int length = bArr.length;
        int i4 = length / i3;
        int i5 = length % i3;
        int i6 = 0;
        if (i2 < i4) {
            byte[] bArr2 = new byte[maxCommunicationLength];
            bArr2[0] = 1;
            bArr2[1] = -29;
            int i7 = this.mDataSegmentsFlag;
            bArr2[2] = (byte) ((i7 >> 8) & 255);
            bArr2[3] = (byte) (i7 & 255);
            bArr2[4] = (byte) i2;
            System.arraycopy(bArr, i2 * i3, bArr2, 5, i3);
            while (i6 < maxCommunicationLength) {
                this.mAppCrc = bArr2[i6] ^ this.mAppCrc;
                i6++;
            }
            WriteCommandToBLE.getInstance().writeCharaBle5(bArr2);
            if (i5 != 0) {
                return;
            }
        } else if (this.isSendDataFD) {
            utenif(this.mAppCrc);
            this.isSendDataFD = false;
        } else if (i5 != 0) {
            int i8 = i5 + 5;
            byte[] bArr3 = new byte[i8];
            bArr3[0] = 1;
            bArr3[1] = -29;
            int i9 = this.mDataSegmentsFlag;
            bArr3[2] = (byte) ((i9 >> 8) & 255);
            bArr3[3] = (byte) (i9 & 255);
            bArr3[4] = (byte) i2;
            System.arraycopy(bArr, i3 * i2, bArr3, 5, i5);
            while (i6 < i8) {
                this.mAppCrc = bArr3[i6] ^ this.mAppCrc;
                i6++;
            }
            WriteCommandToBLE.getInstance().writeCharaBle5(bArr3);
        }
        this.isSendDataFD = true;
    }

    public final void utenif() {
        LogUtils.i(TAG, "prepareSendWatchFaceData");
        WriteCommandToBLE.getInstance().writeCharaBle5(new byte[]{1, -29, -85, 3, 1, (byte) this.watchFaceType});
    }

    public final int utendo(byte[] bArr, int i2, int i3) {
        if (i3 == 1) {
            return bArr[i2] & 255;
        }
        if (i3 == 2) {
            return ((bArr[i2 + 1] & 255) << 8) | (bArr[i2] & 255);
        }
        if (i3 == 3) {
            return ((bArr[i2 + 2] & 255) << 16) | (bArr[i2] & 255) | ((bArr[i2 + 1] & 255) << 8);
        }
        if (i3 != 4) {
            return 0;
        }
        return ((bArr[i2 + 3] & 255) << 24) | (bArr[i2] & 255) | ((bArr[i2 + 1] & 255) << 8) | ((bArr[i2 + 2] & 255) << 16);
    }

    public final void utenfor() {
        WriteCommandToBLE.getInstance().writeCharaBle5(new byte[]{1, -29, -85, 4, 1, (byte) this.watchFaceType});
    }

    public final void utenif(int i2) {
        int i3 = this.mDataSegmentsFlag;
        LogUtils.i("sendDataFD mAppCrc =" + (i2 & 255));
        WriteCommandToBLE.getInstance().writeCharaBle5(new byte[]{1, -29, (byte) ((i3 >> 8) & 255), (byte) (i3 & 255), -3, (byte) i2});
        this.mAllSendData = null;
    }

    public final byte[] utendo(int i2) {
        ImageWatchFaceConfig imageWatchFaceConfig = this.mImageWatchFaceConfig;
        String name = "";
        if (imageWatchFaceConfig != null && imageWatchFaceConfig.getImageInfoConfigList() != null) {
            List<ImageWatchFaceConfig.ImageInfoConfig> imageInfoConfigList = this.mImageWatchFaceConfig.getImageInfoConfigList();
            for (int i3 = 0; i3 < imageInfoConfigList.size(); i3++) {
                if (imageInfoConfigList.get(i3).getIndex() == i2) {
                    name = imageInfoConfigList.get(i3).getName();
                }
            }
        }
        byte[] bArrHexStringToBytes = GBUtils.getInstance().hexStringToBytes(GBUtils.getInstance().stringToASCII(name));
        if (bArrHexStringToBytes == null) {
            return null;
        }
        return bArrHexStringToBytes.length <= 20 ? bArrHexStringToBytes : Arrays.copyOf(bArrHexStringToBytes, 20);
    }

    public final byte[] utendo(int i2, int i3, int i4) {
        byte b2 = (byte) 0;
        return new byte[]{-1, -1, -1, -1, (byte) (i2 & 255), (byte) ((i2 >> 8) & 255), (byte) (i3 & 255), (byte) ((i3 >> 8) & 255), b2, (byte) 2, b2, b2, (byte) (i4 & 255), (byte) ((i4 >> 8) & 255), (byte) ((i4 >> 16) & 255), (byte) ((i4 >> 24) & 255), 2, 1, 0, 0};
    }

    public final byte[] utendo(ImageWatchFaceConfig imageWatchFaceConfig) {
        int imageCount = imageWatchFaceConfig.getImageCount();
        List<ImageWatchFaceConfig.ImageInfoConfig> imageInfoConfigList = imageWatchFaceConfig.getImageInfoConfigList();
        int positionIndex = imageWatchFaceConfig.getPositionIndex();
        int styleIndex = imageWatchFaceConfig.getStyleIndex();
        int transferNum = imageWatchFaceConfig.getTransferNum();
        int timeColor = imageWatchFaceConfig.getTimeColor();
        int dateColor = imageWatchFaceConfig.getDateColor();
        StringBuilder sb = new StringBuilder();
        sb.append("0101");
        sb.append(String.format("%02X", Integer.valueOf(imageCount)));
        sb.append("0401");
        sb.append(String.format("%02X", Integer.valueOf(positionIndex)));
        sb.append("0501");
        sb.append(String.format("%02X", Integer.valueOf(styleIndex)));
        sb.append("0701");
        sb.append(String.format("%02X", Integer.valueOf(transferNum)));
        if (imageInfoConfigList.size() > 0) {
            StringBuilder sb2 = new StringBuilder();
            for (int i2 = 0; i2 < imageInfoConfigList.size(); i2++) {
                int index = imageInfoConfigList.get(i2).getIndex();
                String strStringToASCII = GBUtils.getInstance().stringToASCII(imageInfoConfigList.get(i2).getName());
                if (strStringToASCII.length() > 40) {
                    strStringToASCII = strStringToASCII.substring(0, 40);
                }
                sb2.append(String.format("%02X", Integer.valueOf(index)));
                sb2.append(String.format("%02X", Integer.valueOf(strStringToASCII.length() / 2)));
                sb2.append(strStringToASCII);
            }
            int length = sb2.length() / 2;
            sb.append("06");
            sb.append(String.format("%02X", Integer.valueOf(length)));
            sb.append((CharSequence) sb2);
            sb.append("0803");
            sb.append(String.format("%06X", Integer.valueOf(timeColor)));
            sb.append("0903");
            sb.append(String.format("%06X", Integer.valueOf(dateColor)));
        }
        return GBUtils.getInstance().hexStringToBytes(sb.toString());
    }

    public final byte[] utendo(File file, int i2) {
        String absolutePath = file.getAbsolutePath();
        LogUtils.i("filePath = " + absolutePath);
        Bitmap bitmapPicToBmpSDPath = WatchFaceUtil.getInstance().picToBmpSDPath(absolutePath);
        LogUtils.i("bitmap.width = " + bitmapPicToBmpSDPath.getWidth() + ",height =" + bitmapPicToBmpSDPath.getHeight());
        byte[] bArrBmpToRgb565Byte = WatchFaceUtil.getInstance().bmpToRgb565Byte(bitmapPicToBmpSDPath);
        StringBuilder sb = new StringBuilder();
        sb.append("watchFaceData.length = ");
        sb.append(bArrBmpToRgb565Byte.length);
        LogUtils.i(sb.toString());
        byte[] bArrUteCompress = UteCompressUtil.getInstance().uteCompress(bArrBmpToRgb565Byte);
        byte[] bArrUtendo = utendo(i2, bitmapPicToBmpSDPath.getWidth(), bitmapPicToBmpSDPath.getHeight(), bArrUteCompress.length, WatchFaceUtil.getInstance().uteDataCrc32(bArrUteCompress));
        LogUtils.i("header = " + GBUtils.getInstance().bytes2HexString(bArrUtendo));
        return ByteDataUtil.getInstance().copyTwoArrays(bArrUtendo, bArrUteCompress);
    }

    public final byte[] utendo(int i2, int i3, int i4, int i5, int i6) {
        byte[] bArr = new byte[64];
        bArr[0] = -1;
        bArr[1] = -1;
        bArr[2] = -1;
        bArr[3] = -1;
        bArr[7] = (byte) ((i6 >> 24) & 255);
        bArr[6] = (byte) ((i6 >> 16) & 255);
        bArr[5] = (byte) ((i6 >> 8) & 255);
        bArr[4] = (byte) (i6 & 255);
        bArr[8] = -1;
        bArr[9] = (byte) i2;
        byte[] bArrUtendo = utendo(i2);
        if (bArrUtendo != null) {
            System.arraycopy(bArrUtendo, 0, bArr, 10, bArrUtendo.length);
            bArr[30] = (byte) bArrUtendo.length;
        }
        for (int i7 = 31; i7 < 44; i7++) {
            bArr[i7] = -1;
        }
        byte[] bArrUtendo2 = utendo(i3, i4, i5);
        System.arraycopy(bArrUtendo2, 0, bArr, 44, bArrUtendo2.length);
        return bArr;
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

    public final void utendo(byte[] bArr, int i2) {
        if (bArr == null) {
            return;
        }
        this.mDataSegmentsFlag = i2;
        this.mAllSendData = bArr;
        LogUtils.i("mAllSendData.length() =" + this.mAllSendData.length);
        this.mAppCrc = 0;
        this.isSendDataFD = false;
        utenfor(0);
    }
}
