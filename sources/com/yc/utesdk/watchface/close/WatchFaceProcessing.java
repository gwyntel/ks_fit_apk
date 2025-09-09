package com.yc.utesdk.watchface.close;

import android.os.Message;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;
import com.google.common.base.Ascii;
import com.google.gson.Gson;
import com.yc.utesdk.bean.ImageWatchFaceRk;
import com.yc.utesdk.bean.LocalWatchFaceInfo;
import com.yc.utesdk.command.CommandTimeOutUtils;
import com.yc.utesdk.command.WriteCommandToBLE;
import com.yc.utesdk.listener.UteListenerManager;
import com.yc.utesdk.log.LogUtils;
import com.yc.utesdk.utils.open.ByteDataUtil;
import com.yc.utesdk.watchface.bean.WatchFaceSerialNumberInfo;
import com.yc.utesdk.watchface.open.WatchFaceUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* loaded from: classes4.dex */
public class WatchFaceProcessing {
    private static WatchFaceProcessing instance;
    private int mOnlineDialFailCount = 0;
    private byte[] dialByte = new byte[0];
    private WriteCommandToBLE mWriteCommandToBLE = WriteCommandToBLE.getInstance();
    private OnlineDialTimeOut mOnlineDialTimeOut = OnlineDialTimeOut.getInstance();

    public static WatchFaceProcessing getInstance() {
        if (instance == null) {
            instance = new WatchFaceProcessing();
        }
        return instance;
    }

    public void dealWithLocalWatchFace(String str, byte[] bArr) {
        switch (bArr[1] & 255) {
            case 250:
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                UteListenerManager.getInstance().queryLocalWatchFaceSuccess(new LocalWatchFaceInfo(utenfor(bArr), utenif(bArr), utendo(str, bArr), bArr[10] & 255, bArr[11] & 255));
                break;
            case 251:
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                int i2 = bArr[2] & 255;
                if (i2 != 255) {
                    UteListenerManager.getInstance().setLocalWatchFaceStatus(1, i2);
                    break;
                } else {
                    UteListenerManager.getInstance().setLocalWatchFaceStatus(2, -1);
                    break;
                }
            case 252:
                UteListenerManager.getInstance().notifyCurrentWatchFaceIndex(bArr[2] & 255);
                break;
        }
    }

    public void dealWithOnlineDial(String str, byte[] bArr) {
        UteListenerManager uteListenerManager;
        int i2;
        UteListenerManager uteListenerManager2;
        UteListenerManager uteListenerManager3;
        int i3;
        switch (bArr[1] & 255) {
            case 1:
                LogUtils.i("获取手环的表盘配置");
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                WatchFaceUtil.getInstance().parsingDdialInformation(str, bArr);
                UteListenerManager.getInstance().onWatchFaceOnlineStatus(0);
                break;
            case 2:
                LogUtils.i("准备开始发送表盘数据");
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                UteListenerManager.getInstance().onWatchFaceOnlineStatus(2);
                this.mOnlineDialFailCount = 0;
                break;
            case 3:
                int i4 = bArr[2] & 255;
                if (i4 != 0) {
                    if (i4 != 1) {
                        if (i4 != 2) {
                            if (i4 != 3) {
                                if (i4 == 4) {
                                    this.mOnlineDialFailCount = 0;
                                    this.mOnlineDialTimeOut.cancelCommandTimeOut();
                                    LogUtils.i("发送数据段OK，发下一段");
                                    this.mWriteCommandToBLE.mHandler.removeMessages(2);
                                    Message message = new Message();
                                    Objects.requireNonNull(this.mWriteCommandToBLE);
                                    message.what = 2;
                                    this.mWriteCommandToBLE.mHandler.sendMessageDelayed(message, WatchFaceUtil.DELAY_SYNC_WATCH_FACE_DATA_TIME);
                                    break;
                                }
                            } else {
                                this.mWriteCommandToBLE.NOsectionOnline = ((bArr[4] & 255) | ((bArr[3] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK)) - 1;
                                LogUtils.i("断点序号，重发 NOsectionOnline =" + (this.mWriteCommandToBLE.NOsectionOnline + 1));
                                int i5 = this.mOnlineDialFailCount + 1;
                                this.mOnlineDialFailCount = i5;
                                if (i5 > 2 && !this.mWriteCommandToBLE.isReduceDialSpeed()) {
                                    this.mWriteCommandToBLE.setReduceDialSpeed(true);
                                    break;
                                }
                            }
                        } else {
                            UteListenerManager.getInstance().onWatchFaceOnlineStatus(7);
                        }
                    } else {
                        UteListenerManager.getInstance().onWatchFaceOnlineStatus(6);
                    }
                    this.mWriteCommandToBLE.stopOnlineDialData();
                    break;
                } else {
                    uteListenerManager = UteListenerManager.getInstance();
                    i2 = 5;
                    uteListenerManager.onWatchFaceOnlineStatus(i2);
                    break;
                }
                break;
            case 5:
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                int length = bArr.length - 2;
                byte[] bArr2 = new byte[length];
                System.arraycopy(bArr, 2, bArr2, 0, length);
                this.dialByte = bArr2;
                UteListenerManager.getInstance().onWatchFaceParams(25, utendo(bArr2));
                this.dialByte = new byte[0];
                break;
            case 6:
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                uteListenerManager = UteListenerManager.getInstance();
                i2 = 28;
                uteListenerManager.onWatchFaceOnlineStatus(i2);
                break;
            case 8:
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                int i6 = bArr[2] & 255;
                int i7 = bArr[3] & 255;
                int i8 = bArr[4] & 255;
                LogUtils.i("删除在线表盘 serialNumber =" + i6 + "，maxWatchFaceCount =" + i7 + "，deleteState =" + i8);
                uteListenerManager2 = UteListenerManager.getInstance();
                if (i8 != 0) {
                    if (i8 != 1) {
                        uteListenerManager2.onDeleteWatchFaceOnline(9);
                        break;
                    }
                    uteListenerManager2.onDeleteWatchFaceOnline(8);
                    break;
                } else {
                    uteListenerManager2.onDeleteWatchFaceOnline(7);
                    break;
                }
            case 9:
                int i9 = bArr[2] & 255;
                if (i9 != 253) {
                    int length2 = bArr.length - 3;
                    byte[] bArr3 = new byte[length2];
                    System.arraycopy(bArr, 3, bArr3, 0, length2);
                    if (i9 != 0) {
                        this.dialByte = ByteDataUtil.getInstance().copyTwoArrays(this.dialByte, bArr3);
                        break;
                    } else {
                        this.dialByte = bArr3;
                        break;
                    }
                } else {
                    CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                    UteListenerManager.getInstance().onWatchFaceParams(26, utendo(this.dialByte));
                    this.dialByte = new byte[0];
                    break;
                }
            case 10:
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                UteListenerManager.getInstance().onWatchFaceParams(34, parsingImageWatchFaceRk(bArr));
                break;
            case 11:
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                int i10 = bArr[2] & 255;
                uteListenerManager3 = UteListenerManager.getInstance();
                i3 = i10 == 0 ? 36 : 38;
                uteListenerManager3.onWatchFaceOnlineStatus(i3);
                break;
            case 12:
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                uteListenerManager2 = UteListenerManager.getInstance();
                uteListenerManager2.onDeleteWatchFaceOnline(8);
                break;
            case 13:
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                UteListenerManager.getInstance().onWatchFaceOnlineStatus(8);
                int i11 = bArr[2] & 255;
                uteListenerManager3 = UteListenerManager.getInstance();
                i3 = i11 == 0 ? 32 : 33;
                uteListenerManager3.onWatchFaceOnlineStatus(i3);
                break;
        }
    }

    public ImageWatchFaceRk parsingImageWatchFaceRk(byte[] bArr) {
        ImageWatchFaceRk imageWatchFaceRk = new ImageWatchFaceRk();
        imageWatchFaceRk.setMaxCount(bArr[2] & 255);
        imageWatchFaceRk.setHadSetCount(bArr[3] & 255);
        imageWatchFaceRk.setPicIndex(bArr[4] & 255);
        imageWatchFaceRk.setNeedPreview((bArr[5] & 255) == 1);
        imageWatchFaceRk.setSupportFontColor((bArr[6] & 255) == 1);
        imageWatchFaceRk.setPositionIndex(bArr[7] & 255);
        imageWatchFaceRk.setStyleIndex(bArr[8] & 255);
        imageWatchFaceRk.setImageType(bArr[9] & 255);
        imageWatchFaceRk.setPreviewWidth(((bArr[10] & 255) << 8) | (bArr[11] & 255));
        imageWatchFaceRk.setPreviewHeight(((bArr[12] & 255) << 8) | (bArr[13] & 255));
        imageWatchFaceRk.setMaxSize((bArr[17] & 255) | ((bArr[16] & 255) << 8) | ((bArr[14] & 255) << 24) | ((bArr[15] & 255) << 16));
        LogUtils.i("ImageWatchFaceRk =" + new Gson().toJson(imageWatchFaceRk));
        return imageWatchFaceRk;
    }

    public final List utendo(byte[] bArr) {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < bArr.length; i2 += 5) {
            byte[] bArr2 = new byte[5];
            System.arraycopy(bArr, i2, bArr2, 0, 5);
            int i3 = bArr2[0] & 255;
            int i4 = ((bArr2[1] << Ascii.CAN) & ViewCompat.MEASURED_STATE_MASK) | (bArr2[4] & 255) | ((bArr2[3] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | ((bArr2[2] << 16) & 16711680);
            WatchFaceSerialNumberInfo watchFaceSerialNumberInfo = new WatchFaceSerialNumberInfo();
            watchFaceSerialNumberInfo.setDisplayIndex(i3);
            watchFaceSerialNumberInfo.setBleID(String.valueOf(i4));
            arrayList.add(watchFaceSerialNumberInfo);
        }
        return arrayList;
    }

    public final int utenfor(byte[] bArr) {
        return ((bArr[2] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[3] & 255);
    }

    public final int utenif(byte[] bArr) {
        return ((bArr[4] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[5] & 255);
    }

    public final int utendo(String str, byte[] bArr) {
        if (str.startsWith("FFFFFFFF", 12)) {
            LogUtils.i("本地表盘 表示当前没有显示的在线表盘");
            return -1;
        }
        int i2 = bArr[9] & 255;
        int i3 = (bArr[8] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK;
        return i2 | i3 | ((bArr[7] << 16) & 16711680) | ((bArr[6] << Ascii.CAN) & ViewCompat.MEASURED_STATE_MASK);
    }
}
