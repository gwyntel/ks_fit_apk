package com.yc.utesdk.watchface.close;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import com.luck.picture.lib.config.PictureMimeType;
import com.umeng.analytics.pro.bc;
import com.yc.utesdk.ble.open.UteBleClient;
import com.yc.utesdk.command.WriteCommandToBLE;
import com.yc.utesdk.utils.open.SPUtil;
import com.yc.utesdk.watchface.bean.CustomViewPositionInfo;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.commons.lang.CharUtils;

/* loaded from: classes4.dex */
public class PicUtils {
    private static PicUtils Instance;
    private PreviewScaleInfo mPreviewScaleInfo;
    private String folderDial = "";
    private int pathStatus = 0;
    private int watchFaceShapeType = 2;
    private int screenType = 0;
    private Bitmap mDialBackgroundBitmap = null;
    private int myCurrentFontColor = ViewCompat.MEASURED_SIZE_MASK;
    private boolean isMyFontColorChange = false;
    private byte[] mSendToBleData = new byte[0];
    private List<CustomViewPositionInfo> mCustomViewPositionList = new ArrayList();

    public class utendo implements Comparator {
        public utendo() {
        }

        @Override // java.util.Comparator
        /* renamed from: utendo, reason: merged with bridge method [inline-methods] */
        public int compare(File file, File file2) {
            if (file.isDirectory() && file2.isFile()) {
                return -1;
            }
            if (file.isFile() && file2.isDirectory()) {
                return 1;
            }
            if ((file.getName().endsWith(PictureMimeType.PNG) || file.getName().endsWith(".PNG")) && (file2.getName().endsWith(PictureMimeType.PNG) || file2.getName().endsWith(".PNG"))) {
                String strSubstring = file.getName().substring(0, file.getName().length() - 4);
                String strSubstring2 = file2.getName().substring(0, file2.getName().length() - 4);
                if (PicUtils.this.isNumeric(strSubstring) && PicUtils.this.isNumeric(strSubstring2)) {
                    return Integer.parseInt(strSubstring) - Integer.parseInt(strSubstring2);
                }
            }
            return file.getName().compareTo(file2.getName());
        }
    }

    public class utenif implements Comparator {
        public utenif() {
        }

        @Override // java.util.Comparator
        /* renamed from: utendo, reason: merged with bridge method [inline-methods] */
        public int compare(String str, String str2) {
            if ((str.endsWith(PictureMimeType.PNG) || str.endsWith(".PNG")) && (str2.endsWith(PictureMimeType.PNG) || str2.endsWith(".PNG"))) {
                String strSubstring = str.substring(0, str.length() - 4);
                String strSubstring2 = str2.substring(0, str2.length() - 4);
                if (PicUtils.this.isNumeric(strSubstring) && PicUtils.this.isNumeric(strSubstring2)) {
                    return Integer.parseInt(strSubstring) - Integer.parseInt(strSubstring2);
                }
            }
            return str.compareTo(str2);
        }
    }

    public static PicUtils getInstance() {
        if (Instance == null) {
            Instance = new PicUtils();
        }
        return Instance;
    }

    public Bitmap changeWatchFaceBackgroundAndColor(Bitmap bitmap, int i2) throws NumberFormatException {
        utendo(true);
        utendo(bitmap);
        utenif(i2);
        byte[] bArrUtendo = new byte[0];
        if (this.pathStatus == 0) {
            try {
                bArrUtendo = utendo(bitmap, i2, false);
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        } else {
            bArrUtendo = utenif(bitmap, i2, false);
        }
        return Rgb.getInstance().mergeBitmap(utendo(bArrUtendo));
    }

    public boolean dealWithWatchFaceCustomData(Bitmap bitmap) throws NumberFormatException {
        byte[] bArrUtendo = new byte[0];
        boolean z2 = SPUtil.getInstance().getDialScreenCompatibleLevel() >= 2;
        if (this.pathStatus == 0) {
            try {
                bArrUtendo = utendo(utentry(), utenint(), z2);
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        } else {
            bArrUtendo = utenif(utentry(), utenint(), z2);
        }
        if (bitmap == null) {
            bitmap = getWatchFaceDefaultPreview();
        }
        if (bArrUtendo == null || bArrUtendo.length <= 0 || bitmap == null) {
            return false;
        }
        byte[] bArrCompositePreviewToByte = Rgb.getInstance().compositePreviewToByte(bArrUtendo, Rgb.getInstance().compositePreviewBitmap(bitmap, this.pathStatus, this.folderDial), z2);
        this.mSendToBleData = bArrCompositePreviewToByte;
        Rgb.LogD("最终发送的1 =" + bArrCompositePreviewToByte.length + ",isSupportsCompress = " + z2);
        return true;
    }

    public List<CustomViewPositionInfo> getCustomViewPosition() {
        return this.mCustomViewPositionList;
    }

    public Bitmap getDefaultPreviewAssetPath() throws Throwable {
        InputStream inputStreamOpen;
        String str = this.folderDial;
        AssetManager assetManagerUtenfor = utenfor();
        String[] list = assetManagerUtenfor.list(str);
        InputStream inputStream = null;
        utendo((PreviewScaleInfo) null);
        Bitmap bitmapRoundedCornerBitmap = null;
        for (int i2 = 0; i2 < list.length; i2++) {
            String str2 = list[i2];
            str2.hashCode();
            if (str2.equals("preview")) {
                String str3 = str + "/" + list[i2];
                String[] list2 = assetManagerUtenfor.list(str3);
                for (int i3 = 0; i3 < list2.length; i3++) {
                    if (list2[i3].endsWith("png") || list2[i3].endsWith("PNG") || list2[i3].endsWith("bmp") || list2[i3].endsWith("BMP") || list2[i3].endsWith("jpg") || list2[i3].endsWith("JPG")) {
                        if (list2[i3].equals("1.png") || list2[i3].equals("1.PNG") || list2[i3].equals("01.png") || list2[i3].equals("01.PNG")) {
                            try {
                                inputStreamOpen = assetManagerUtenfor.open(str3 + "/" + list2[i3]);
                            } catch (Throwable th) {
                                th = th;
                            }
                            try {
                                Bitmap bitmapDecodeStream = BitmapFactory.decodeStream(inputStreamOpen);
                                bitmapDecodeStream.copyPixelsToBuffer(ByteBuffer.allocate(bitmapDecodeStream.getByteCount()));
                                if (inputStreamOpen != null) {
                                    inputStreamOpen.close();
                                }
                                bitmapRoundedCornerBitmap = bitmapDecodeStream;
                            } catch (Throwable th2) {
                                th = th2;
                                inputStream = inputStreamOpen;
                                if (inputStream != null) {
                                    inputStream.close();
                                }
                                throw th;
                            }
                        }
                    } else if (list2[i3].endsWith("txt") || list2[i3].endsWith("TXT")) {
                        String strReplaceAll = list2[i3].replaceAll("config_", "").replaceAll(".txt", "");
                        Rgb.LogD("all2 =" + strReplaceAll + "," + list[i2]);
                        if (strReplaceAll.contains("scalepreview_")) {
                            String strReplaceAll2 = strReplaceAll.replaceAll("scalepreview_", "");
                            if (strReplaceAll2.contains(OpenAccountUIConstants.UNDER_LINE)) {
                                String[] strArrSplit = strReplaceAll2.split(OpenAccountUIConstants.UNDER_LINE);
                                if (strArrSplit.length >= 3) {
                                    utendo(new PreviewScaleInfo(Float.parseFloat(strArrSplit[0]), Float.parseFloat(strArrSplit[1]), Float.parseFloat(strArrSplit[2])));
                                }
                            }
                        }
                    }
                }
            }
        }
        PreviewScaleInfo previewScaleInfo = getPreviewScaleInfo();
        if (previewScaleInfo == null) {
            return bitmapRoundedCornerBitmap;
        }
        if (previewScaleInfo.getPreviewRound() > 0.0f) {
            bitmapRoundedCornerBitmap = Rgb.getInstance().roundedCornerBitmap(bitmapRoundedCornerBitmap, previewScaleInfo.getPreviewRound(), previewScaleInfo.getPreviewRound());
        }
        if (bitmapRoundedCornerBitmap == null) {
            return bitmapRoundedCornerBitmap;
        }
        return Rgb.getInstance().zoomBitmap(bitmapRoundedCornerBitmap, previewScaleInfo.getPreviewWidth() / bitmapRoundedCornerBitmap.getWidth(), previewScaleInfo.getPreviewHeight() / bitmapRoundedCornerBitmap.getHeight());
    }

    public Bitmap getDefaultPreviewSDPath(String str) {
        File[] fileArrUtenif = utenif(str);
        Bitmap bitmapRoundedCornerBitmap = null;
        if (fileArrUtenif == null) {
            Rgb.LogD("请检查图片路径是否正确4 list =" + Arrays.toString(fileArrUtenif));
            return null;
        }
        utendo((PreviewScaleInfo) null);
        for (int i2 = 0; i2 < fileArrUtenif.length; i2++) {
            String name = fileArrUtenif[i2].getName();
            name.hashCode();
            if (name.equals("preview")) {
                File[] fileArrListFiles = fileArrUtenif[i2].listFiles();
                for (int i3 = 0; i3 < fileArrListFiles.length; i3++) {
                    if (fileArrListFiles[i3].getName().endsWith("png") || fileArrListFiles[i3].getName().endsWith("PNG") || fileArrListFiles[i3].getName().endsWith("bmp") || fileArrListFiles[i3].getName().endsWith("BMP") || fileArrListFiles[i3].getName().endsWith("jpg") || fileArrListFiles[i3].getName().endsWith("JPG")) {
                        if (fileArrListFiles[i3].getName().equals("1.png") || fileArrListFiles[i3].getName().equals("1.PNG") || fileArrListFiles[i3].getName().equals("01.png") || fileArrListFiles[i3].getName().equals("01.PNG")) {
                            bitmapRoundedCornerBitmap = Rgb.getInstance().picToBmpSDPath(fileArrListFiles[i3].getAbsolutePath());
                        }
                    } else if (fileArrListFiles[i3].getName().endsWith("txt") || fileArrListFiles[i3].getName().endsWith("TXT")) {
                        String strReplaceAll = fileArrListFiles[i3].getName().replaceAll("config_", "").replaceAll(".txt", "");
                        Rgb.LogD("all2 =" + strReplaceAll + "," + fileArrUtenif[i2]);
                        if (strReplaceAll.contains("scalepreview_")) {
                            String strReplaceAll2 = strReplaceAll.replaceAll("scalepreview_", "");
                            if (strReplaceAll2.contains(OpenAccountUIConstants.UNDER_LINE)) {
                                String[] strArrSplit = strReplaceAll2.split(OpenAccountUIConstants.UNDER_LINE);
                                if (strArrSplit.length >= 3) {
                                    utendo(new PreviewScaleInfo(Float.parseFloat(strArrSplit[0]), Float.parseFloat(strArrSplit[1]), Float.parseFloat(strArrSplit[2])));
                                }
                            }
                        }
                    }
                }
            }
        }
        PreviewScaleInfo previewScaleInfo = getPreviewScaleInfo();
        if (previewScaleInfo == null) {
            return bitmapRoundedCornerBitmap;
        }
        if (previewScaleInfo.getPreviewRound() > 0.0f) {
            bitmapRoundedCornerBitmap = Rgb.getInstance().roundedCornerBitmap(bitmapRoundedCornerBitmap, previewScaleInfo.getPreviewRound(), previewScaleInfo.getPreviewRound());
        }
        if (bitmapRoundedCornerBitmap == null) {
            return bitmapRoundedCornerBitmap;
        }
        return Rgb.getInstance().zoomBitmap(bitmapRoundedCornerBitmap, previewScaleInfo.getPreviewWidth() / bitmapRoundedCornerBitmap.getWidth(), previewScaleInfo.getPreviewHeight() / bitmapRoundedCornerBitmap.getHeight());
    }

    public List<CustomViewPositionInfo> getDefaultViewPosition() {
        ArrayList arrayList = new ArrayList();
        if (this.pathStatus != 0) {
            return utenif();
        }
        try {
            return utendo();
        } catch (IOException e2) {
            e2.printStackTrace();
            return arrayList;
        }
    }

    public Bitmap getDialDefaultBackground() {
        if (this.pathStatus == 1) {
            return this.watchFaceShapeType == 2 ? Rgb.getInstance().roundedCornerBitmap(utendo(this.folderDial)) : utendo(this.folderDial);
        }
        try {
            return this.watchFaceShapeType == 2 ? Rgb.getInstance().roundedCornerBitmap(utennew()) : utennew();
        } catch (IOException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public int getPathStatus() {
        return this.pathStatus;
    }

    public Bitmap getPreviewBgAssetPath() {
        String str = this.folderDial;
        AssetManager assetManagerUtenfor = utenfor();
        String[] list = assetManagerUtenfor.list(str);
        InputStream inputStream = null;
        Bitmap bitmap = null;
        for (int i2 = 0; i2 < list.length; i2++) {
            String str2 = list[i2];
            str2.hashCode();
            if (str2.equals("preview")) {
                String str3 = str + "/" + list[i2];
                String[] list2 = assetManagerUtenfor.list(str3);
                for (int i3 = 0; i3 < list2.length; i3++) {
                    if ((list2[i3].endsWith("png") || list2[i3].endsWith("PNG") || list2[i3].endsWith("bmp") || list2[i3].endsWith("BMP") || list2[i3].endsWith("jpg") || list2[i3].endsWith("JPG")) && (list2[i3].equals("preview_bg.png") || list2[i3].equals("preview_bg.PNG"))) {
                        try {
                            InputStream inputStreamOpen = assetManagerUtenfor.open(str3 + "/" + list2[i3]);
                            try {
                                Bitmap bitmapDecodeStream = BitmapFactory.decodeStream(inputStreamOpen);
                                bitmapDecodeStream.copyPixelsToBuffer(ByteBuffer.allocate(bitmapDecodeStream.getByteCount()));
                                if (inputStreamOpen != null) {
                                    inputStreamOpen.close();
                                }
                                bitmap = bitmapDecodeStream;
                            } catch (Throwable th) {
                                th = th;
                                inputStream = inputStreamOpen;
                                if (inputStream != null) {
                                    inputStream.close();
                                }
                                throw th;
                            }
                        } catch (Throwable th2) {
                            th = th2;
                        }
                    }
                }
            }
        }
        return bitmap;
    }

    public Bitmap getPreviewBgSDPath(String str) {
        File[] fileArrUtenif = utenif(str);
        Bitmap bitmapPicToBmpSDPath = null;
        if (fileArrUtenif == null) {
            Rgb.LogD("请检查图片路径是否正确3 dialList =" + Arrays.toString(fileArrUtenif) + ",path =" + str);
            return null;
        }
        for (int i2 = 0; i2 < fileArrUtenif.length; i2++) {
            String name = fileArrUtenif[i2].getName();
            name.hashCode();
            if (name.equals("preview")) {
                File[] fileArrListFiles = fileArrUtenif[i2].listFiles();
                for (int i3 = 0; i3 < fileArrListFiles.length; i3++) {
                    if ((fileArrListFiles[i3].getName().endsWith("png") || fileArrListFiles[i3].getName().endsWith("PNG") || fileArrListFiles[i3].getName().endsWith("bmp") || fileArrListFiles[i3].getName().endsWith("BMP") || fileArrListFiles[i3].getName().endsWith("jpg") || fileArrListFiles[i3].getName().endsWith("JPG")) && (fileArrListFiles[i3].getName().equals("preview_bg.png") || fileArrListFiles[i3].getName().equals("preview_bg.PNG"))) {
                        bitmapPicToBmpSDPath = Rgb.getInstance().picToBmpSDPath(fileArrListFiles[i3].getAbsolutePath());
                    }
                }
            }
        }
        return bitmapPicToBmpSDPath;
    }

    public PreviewScaleInfo getPreviewScaleInfo() {
        return this.mPreviewScaleInfo;
    }

    public Bitmap getWatchFaceDefaultPreview() {
        if (this.pathStatus == 1) {
            return getDefaultPreviewSDPath(this.folderDial);
        }
        try {
            return getDefaultPreviewAssetPath();
        } catch (IOException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public int getWatchFaceShapeType() {
        return this.watchFaceShapeType;
    }

    public boolean isNumeric(String str) {
        return Pattern.compile("[0-9]*").matcher(str).matches();
    }

    public void resetCustomViewPosition() {
        this.mCustomViewPositionList = new ArrayList();
    }

    public void resetWatchFaceBackgroundAndColor() {
        this.mDialBackgroundBitmap = null;
        this.myCurrentFontColor = ViewCompat.MEASURED_SIZE_MASK;
        this.isMyFontColorChange = false;
    }

    public void setCustomViewPosition(List<CustomViewPositionInfo> list) {
        this.mCustomViewPositionList = list;
    }

    public void setFolderDial(String str) {
        this.folderDial = str;
    }

    public void setPathStatus(int i2) {
        this.pathStatus = i2;
        Rgb.LogD("watchFaceShapeType =" + this.watchFaceShapeType + ",pathStatus =" + i2 + ",folderDial =" + this.folderDial);
    }

    public void setWatchFaceShapeType(int i2) {
        this.watchFaceShapeType = i2;
    }

    public boolean syncCustomDialData() {
        byte[] bArr = this.mSendToBleData;
        if (bArr == null || bArr.length <= 0) {
            return false;
        }
        WriteCommandToBLE.getInstance().sendOnlineDialData(this.mSendToBleData);
        Rgb.LogD("最终发送的2 =" + this.mSendToBleData.length);
        this.mSendToBleData = new byte[0];
        return true;
    }

    public final boolean utenbyte() {
        return this.isMyFontColorChange;
    }

    public final RgbAarrayInfo utendo(RgbAarrayInfo rgbAarrayInfo) {
        if (rgbAarrayInfo != null) {
            List<PicDataInfo> picDataConfig = rgbAarrayInfo.getPicDataConfig();
            ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 < picDataConfig.size() - 1; i2++) {
                for (int size = picDataConfig.size() - 1; size > i2; size--) {
                    if (Arrays.equals(picDataConfig.get(size).getPicData(), picDataConfig.get(i2).getPicData())) {
                        arrayList.add(new DuplicateInfo(picDataConfig.get(i2).getType(), picDataConfig.get(size).getType()));
                        picDataConfig.remove(size);
                    }
                }
            }
            ArrayList arrayList2 = new ArrayList();
            for (int i3 = 0; i3 < picDataConfig.size(); i3++) {
                arrayList2.add(Integer.valueOf(picDataConfig.get(i3).getType()));
            }
            int size2 = ((rgbAarrayInfo.getPicTypeConfigList().size() + 1) * 24) + 2;
            for (int i4 = 0; i4 < rgbAarrayInfo.getPicTypeConfigList().size(); i4++) {
                int iBytes2HLExchangeInt = Rgb.getInstance().bytes2HLExchangeInt(rgbAarrayInfo.getPicTypeConfigList().get(i4).getType());
                if (arrayList2.contains(Integer.valueOf(iBytes2HLExchangeInt))) {
                    byte[] picStartAddress = rgbAarrayInfo.getPicTypeConfigList().get(i4).getPicStartAddress();
                    System.arraycopy(Rgb.getInstance().bytes4HLExchange(Rgb.getInstance().intToBytes4L(size2)), 0, rgbAarrayInfo.getPicTypeConfig().get(i4).getPicType(), 4, picStartAddress.length);
                    rgbAarrayInfo.getPicTypeConfigList().get(i4).setPicStartAddress(Rgb.getInstance().bytes4HLExchange(Rgb.getInstance().intToBytes4L(size2)));
                    size2 += Rgb.getInstance().bytes4HLExchangeInt(picStartAddress);
                } else {
                    int i5 = 0;
                    while (true) {
                        if (i5 >= arrayList.size()) {
                            break;
                        }
                        if (iBytes2HLExchangeInt == ((DuplicateInfo) arrayList.get(i5)).getTypeSecond()) {
                            int i6 = 0;
                            while (true) {
                                if (i6 >= rgbAarrayInfo.getPicTypeConfigList().size()) {
                                    break;
                                }
                                if (((DuplicateInfo) arrayList.get(i5)).getTypeFirst() == Rgb.getInstance().bytes2HLExchangeInt(rgbAarrayInfo.getPicTypeConfigList().get(i6).getType())) {
                                    System.arraycopy(rgbAarrayInfo.getPicTypeConfigList().get(i6).getPicStartAddress(), 0, rgbAarrayInfo.getPicTypeConfig().get(i4).getPicType(), 4, 4);
                                    rgbAarrayInfo.getPicTypeConfigList().get(i4).setPicStartAddress(rgbAarrayInfo.getPicTypeConfigList().get(i6).getPicStartAddress());
                                    break;
                                }
                                i6++;
                            }
                        } else {
                            i5++;
                        }
                    }
                }
            }
        }
        return rgbAarrayInfo;
    }

    public final AssetManager utenfor() {
        return UteBleClient.getContext().getResources().getAssets();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0033  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.List utenif() throws java.lang.NumberFormatException {
        /*
            Method dump skipped, instructions count: 872
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yc.utesdk.watchface.close.PicUtils.utenif():java.util.List");
    }

    public final int utenint() {
        return this.myCurrentFontColor;
    }

    public final Bitmap utennew() throws Throwable {
        InputStream inputStreamOpen;
        String str = this.folderDial;
        AssetManager assetManagerUtenfor = utenfor();
        String[] list = assetManagerUtenfor.list(str);
        InputStream inputStream = null;
        Bitmap bitmap = null;
        for (int i2 = 0; i2 < list.length; i2++) {
            String str2 = list[i2];
            str2.hashCode();
            if (str2.equals("background")) {
                String str3 = str + "/" + list[i2];
                String[] list2 = assetManagerUtenfor.list(str3);
                for (int i3 = 0; i3 < list2.length; i3++) {
                    if ((list2[i3].endsWith("png") || list2[i3].endsWith("PNG") || list2[i3].endsWith("bmp") || list2[i3].endsWith("BMP") || list2[i3].endsWith("jpg") || list2[i3].endsWith("JPG")) && (list2[i3].equals("1.png") || list2[i3].equals("1.PNG") || list2[i3].equals("01.png") || list2[i3].equals("01.PNG"))) {
                        try {
                            inputStreamOpen = assetManagerUtenfor.open(str3 + "/" + list2[i3]);
                        } catch (Throwable th) {
                            th = th;
                        }
                        try {
                            Bitmap bitmapDecodeStream = BitmapFactory.decodeStream(inputStreamOpen);
                            bitmapDecodeStream.copyPixelsToBuffer(ByteBuffer.allocate(bitmapDecodeStream.getByteCount()));
                            if (inputStreamOpen != null) {
                                inputStreamOpen.close();
                            }
                            bitmap = bitmapDecodeStream;
                        } catch (Throwable th2) {
                            th = th2;
                            inputStream = inputStreamOpen;
                            if (inputStream != null) {
                                inputStream.close();
                            }
                            throw th;
                        }
                    }
                }
            }
        }
        return bitmap;
    }

    public final Bitmap utentry() {
        return this.mDialBackgroundBitmap;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:7:0x002e  */
    /* JADX WARN: Type inference failed for: r1v18 */
    /* JADX WARN: Type inference failed for: r1v4 */
    /* JADX WARN: Type inference failed for: r1v7 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.List utendo() throws java.io.IOException, java.lang.NumberFormatException {
        /*
            Method dump skipped, instructions count: 896
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yc.utesdk.watchface.close.PicUtils.utendo():java.util.List");
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public final byte[] utenif(Bitmap bitmap, int i2, boolean z2) throws NumberFormatException {
        byte[] bArr;
        int i3;
        int i4;
        RgbAarrayInfo rgbAarrayInfo;
        File[] fileArr;
        String str;
        Bitmap bitmap2;
        ArrayList arrayList;
        ArrayList arrayList2;
        ArrayList arrayList3;
        int i5;
        Bitmap bitmap3;
        int i6;
        byte[] bArrBmpToRgb565Byte;
        File[] fileArr2;
        int i7;
        Bitmap bitmap4;
        int i8;
        File[] fileArr3;
        char c2;
        char c3;
        PicUtils picUtils = this;
        int i9 = 0;
        String str2 = "";
        int resolutionWidth = SPUtil.getInstance().getResolutionWidth();
        int resolutionHeight = SPUtil.getInstance().getResolutionHeight();
        Bitmap dialDefaultBackground = bitmap == null ? getDialDefaultBackground() : bitmap;
        File[] fileArrUtenif = picUtils.utenif(picUtils.folderDial);
        if (fileArrUtenif == null) {
            Rgb.LogD("请检查图片路径是否正确1 dialList =" + Arrays.toString(fileArrUtenif) + ",path =" + picUtils.folderDial);
            return null;
        }
        RgbAarrayInfo rgbAarrayInfo2 = new RgbAarrayInfo();
        ArrayList arrayList4 = new ArrayList();
        ArrayList arrayList5 = new ArrayList();
        ArrayList arrayList6 = new ArrayList();
        int i10 = 0;
        int i11 = 0;
        int i12 = 0;
        while (i10 < fileArrUtenif.length) {
            byte[] bArr2 = new byte[i9];
            PicTypeConfigInfo picTypeConfigInfo = new PicTypeConfigInfo();
            PicTypeInfo picTypeInfo = new PicTypeInfo();
            PicDataInfo picDataInfo = new PicDataInfo();
            String name = fileArrUtenif[i10].getName();
            name.hashCode();
            char c4 = 65535;
            switch (name.hashCode()) {
                case -2076896158:
                    if (name.equals("timeday1")) {
                        c4 = 0;
                        break;
                    }
                    break;
                case -2076896157:
                    if (name.equals("timeday2")) {
                        c4 = 1;
                        break;
                    }
                    break;
                case -2076763599:
                    if (name.equals("timehour")) {
                        c4 = 2;
                        break;
                    }
                    break;
                case -2076620692:
                    if (name.equals("timemin1")) {
                        c4 = 3;
                        break;
                    }
                    break;
                case -2076620691:
                    if (name.equals("timemin2")) {
                        c3 = 4;
                        c4 = c3;
                        break;
                    }
                    break;
                case -1893555910:
                    if (name.equals("stepnum")) {
                        c4 = 5;
                        break;
                    }
                    break;
                case -1553608662:
                    if (name.equals("day2month")) {
                        c3 = 6;
                        c4 = c3;
                        break;
                    }
                    break;
                case -1332194002:
                    if (name.equals("background")) {
                        c4 = 7;
                        break;
                    }
                    break;
                case -1313922641:
                    if (name.equals("timeday")) {
                        c3 = '\b';
                        c4 = c3;
                        break;
                    }
                    break;
                case -1313913755:
                    if (name.equals("timemin")) {
                        c4 = '\t';
                        break;
                    }
                    break;
                case -914129323:
                    if (name.equals("iconbluetooth")) {
                        c3 = '\n';
                        c4 = c3;
                        break;
                    }
                    break;
                case -736993595:
                    if (name.equals("iconstep")) {
                        c4 = 11;
                        break;
                    }
                    break;
                case -331239923:
                    if (name.equals(bc.Z)) {
                        c4 = '\f';
                        break;
                    }
                    break;
                case -318184504:
                    if (name.equals("preview")) {
                        c4 = CharUtils.CR;
                        break;
                    }
                    break;
                case -278000284:
                    if (name.equals("stepproccess")) {
                        c3 = 14;
                        c4 = c3;
                        break;
                    }
                    break;
                case -246168380:
                    if (name.equals("hour2min")) {
                        c4 = 15;
                        break;
                    }
                    break;
                case 2998057:
                    if (name.equals("ampm")) {
                        c3 = 16;
                        c4 = c3;
                        break;
                    }
                    break;
                case 3645428:
                    if (name.equals("week")) {
                        c3 = 17;
                        c4 = c3;
                        break;
                    }
                    break;
                case 44837920:
                    if (name.equals("timehour1")) {
                        c4 = 18;
                        break;
                    }
                    break;
                case 44837921:
                    if (name.equals("timehour2")) {
                        c4 = 19;
                        break;
                    }
                    break;
                case 49448915:
                    if (name.equals("timemonth")) {
                        c3 = 20;
                        c4 = c3;
                        break;
                    }
                    break;
                case 201103807:
                    if (name.equals("hearticon")) {
                        c3 = 21;
                        c4 = c3;
                        break;
                    }
                    break;
                case 201269937:
                    if (name.equals("heartnum1")) {
                        c3 = 22;
                        c4 = c3;
                        break;
                    }
                    break;
                case 201269938:
                    if (name.equals("heartnum2")) {
                        c3 = 23;
                        c4 = c3;
                        break;
                    }
                    break;
                case 201269939:
                    if (name.equals("heartnum3")) {
                        c3 = 24;
                        c4 = c3;
                        break;
                    }
                    break;
                case 1429308983:
                    if (name.equals("stepnum1")) {
                        c4 = 25;
                        break;
                    }
                    break;
                case 1429308984:
                    if (name.equals("stepnum2")) {
                        c3 = JSONLexer.EOI;
                        c4 = c3;
                        break;
                    }
                    break;
                case 1429308985:
                    if (name.equals("stepnum3")) {
                        c3 = 27;
                        c4 = c3;
                        break;
                    }
                    break;
                case 1429308986:
                    if (name.equals("stepnum4")) {
                        c3 = 28;
                        c4 = c3;
                        break;
                    }
                    break;
                case 1429308987:
                    if (name.equals("stepnum5")) {
                        c3 = 29;
                        c4 = c3;
                        break;
                    }
                    break;
                case 1532916414:
                    if (name.equals("timemonth1")) {
                        c3 = 30;
                        c4 = c3;
                        break;
                    }
                    break;
                case 1532916415:
                    if (name.equals("timemonth2")) {
                        c3 = 31;
                        c4 = c3;
                        break;
                    }
                    break;
            }
            switch (c4) {
                case 0:
                    bArr = bArr2;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(65)));
                    picTypeInfo.setType(65);
                    i3 = 65;
                    picDataInfo.setType(i3);
                    break;
                case 1:
                    bArr = bArr2;
                    i4 = 66;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(66)));
                    picTypeInfo.setType(i4);
                    i3 = i4;
                    picDataInfo.setType(i3);
                    break;
                case 2:
                    bArr = bArr2;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(4)));
                    picTypeInfo.setType(4);
                    i3 = 4;
                    picDataInfo.setType(i3);
                    break;
                case 3:
                    bArr = bArr2;
                    i4 = 59;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(59)));
                    picTypeInfo.setType(i4);
                    i3 = i4;
                    picDataInfo.setType(i3);
                    break;
                case 4:
                    bArr = bArr2;
                    i4 = 60;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(60)));
                    picTypeInfo.setType(i4);
                    i3 = i4;
                    picDataInfo.setType(i3);
                    break;
                case 5:
                    bArr = bArr2;
                    i4 = 25;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(25)));
                    picTypeInfo.setType(i4);
                    i3 = i4;
                    picDataInfo.setType(i3);
                    break;
                case 6:
                    bArr = bArr2;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(14)));
                    picTypeInfo.setType(14);
                    i3 = 14;
                    picDataInfo.setType(i3);
                    break;
                case 7:
                    bArr = bArr2;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(17)));
                    picTypeInfo.setType(17);
                    i3 = 17;
                    i12 = 1;
                    picDataInfo.setType(i3);
                    break;
                case '\b':
                    bArr = bArr2;
                    i4 = 13;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(13)));
                    picTypeInfo.setType(i4);
                    i3 = i4;
                    picDataInfo.setType(i3);
                    break;
                case '\t':
                    bArr = bArr2;
                    i4 = 5;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(5)));
                    picTypeInfo.setType(i4);
                    i3 = i4;
                    picDataInfo.setType(i3);
                    break;
                case '\n':
                    bArr = bArr2;
                    i4 = 9;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(9)));
                    picTypeInfo.setType(i4);
                    i3 = i4;
                    picDataInfo.setType(i3);
                    break;
                case 11:
                    bArr = bArr2;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(24)));
                    picTypeInfo.setType(24);
                    i3 = 24;
                    picDataInfo.setType(i3);
                    break;
                case '\f':
                    bArr = bArr2;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(8)));
                    picTypeInfo.setType(8);
                    i3 = 8;
                    picDataInfo.setType(i3);
                    break;
                case '\r':
                    bArr = bArr2;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(10)));
                    picTypeInfo.setType(10);
                    i3 = 10;
                    picDataInfo.setType(i3);
                    break;
                case 14:
                    bArr = bArr2;
                    i4 = 32;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(32)));
                    picTypeInfo.setType(i4);
                    i3 = i4;
                    picDataInfo.setType(i3);
                    break;
                case 15:
                    bArr = bArr2;
                    i4 = 7;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(7)));
                    picTypeInfo.setType(i4);
                    i3 = i4;
                    picDataInfo.setType(i3);
                    break;
                case 16:
                    bArr = bArr2;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(16)));
                    picTypeInfo.setType(16);
                    i3 = 16;
                    picDataInfo.setType(i3);
                    break;
                case 17:
                    bArr = bArr2;
                    i4 = 15;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(15)));
                    picTypeInfo.setType(i4);
                    i3 = i4;
                    picDataInfo.setType(i3);
                    break;
                case 18:
                    bArr = bArr2;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(57)));
                    picTypeInfo.setType(57);
                    i3 = 57;
                    picDataInfo.setType(i3);
                    break;
                case 19:
                    bArr = bArr2;
                    i4 = 58;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(58)));
                    picTypeInfo.setType(i4);
                    i3 = i4;
                    picDataInfo.setType(i3);
                    break;
                case 20:
                    bArr = bArr2;
                    i4 = 11;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(11)));
                    picTypeInfo.setType(i4);
                    i3 = i4;
                    picDataInfo.setType(i3);
                    break;
                case 21:
                    bArr = bArr2;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(18)));
                    picTypeInfo.setType(18);
                    i3 = 18;
                    picDataInfo.setType(i3);
                    break;
                case 22:
                    bArr = bArr2;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(35)));
                    picTypeInfo.setType(35);
                    i3 = 35;
                    picDataInfo.setType(i3);
                    break;
                case 23:
                    bArr = bArr2;
                    i4 = 36;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(36)));
                    picTypeInfo.setType(i4);
                    i3 = i4;
                    picDataInfo.setType(i3);
                    break;
                case 24:
                    bArr = bArr2;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(37)));
                    picTypeInfo.setType(37);
                    i3 = 37;
                    picDataInfo.setType(i3);
                    break;
                case 25:
                    bArr = bArr2;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(38)));
                    picTypeInfo.setType(38);
                    i3 = 38;
                    picDataInfo.setType(i3);
                    break;
                case 26:
                    bArr = bArr2;
                    i4 = 39;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(39)));
                    picTypeInfo.setType(i4);
                    i3 = i4;
                    picDataInfo.setType(i3);
                    break;
                case 27:
                    bArr = bArr2;
                    i4 = 40;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(40)));
                    picTypeInfo.setType(i4);
                    i3 = i4;
                    picDataInfo.setType(i3);
                    break;
                case 28:
                    bArr = bArr2;
                    i4 = 41;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(41)));
                    picTypeInfo.setType(i4);
                    i3 = i4;
                    picDataInfo.setType(i3);
                    break;
                case 29:
                    bArr = bArr2;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(42)));
                    picTypeInfo.setType(42);
                    i3 = 42;
                    picDataInfo.setType(i3);
                    break;
                case 30:
                    bArr = bArr2;
                    i4 = 63;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(63)));
                    picTypeInfo.setType(i4);
                    i3 = i4;
                    picDataInfo.setType(i3);
                    break;
                case 31:
                    bArr = bArr2;
                    i4 = 64;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(64)));
                    picTypeInfo.setType(i4);
                    i3 = i4;
                    picDataInfo.setType(i3);
                    break;
                default:
                    bArr = bArr2;
                    break;
            }
            CustomViewPositionInfo customViewPositionInfoUtendo = picUtils.utendo(picTypeInfo.getType());
            if (customViewPositionInfoUtendo == null || customViewPositionInfoUtendo.isViewDisplay()) {
                File[] fileArrUtendo = picUtils.utendo(fileArrUtenif[i10].listFiles());
                ArrayList arrayList7 = arrayList4;
                rgbAarrayInfo = rgbAarrayInfo2;
                ArrayList arrayList8 = arrayList5;
                int i13 = 0;
                int i14 = 0;
                int viewY = 0;
                int viewX = 0;
                int i15 = 0;
                while (i13 < fileArrUtendo.length) {
                    ArrayList arrayList9 = arrayList6;
                    if (fileArrUtendo[i13].getName().endsWith("txt") || fileArrUtendo[i13].getName().endsWith("TXT")) {
                        String strReplaceAll = fileArrUtendo[i13].getName().replaceAll("config_", str2).replaceAll(".txt", str2);
                        StringBuilder sb = new StringBuilder();
                        i8 = i14;
                        sb.append("all2 =");
                        sb.append(strReplaceAll);
                        sb.append(",");
                        sb.append(fileArrUtenif[i10]);
                        Rgb.LogD(sb.toString());
                        if (strReplaceAll.contains("scalepreview_")) {
                            fileArr3 = fileArrUtenif;
                            if (fileArrUtenif[i10].getName().equals("preview")) {
                                String strReplaceAll2 = strReplaceAll.replaceAll("scalepreview_", str2);
                                if (strReplaceAll2.contains(OpenAccountUIConstants.UNDER_LINE)) {
                                    String[] strArrSplit = strReplaceAll2.split(OpenAccountUIConstants.UNDER_LINE);
                                    if (strArrSplit.length >= 3) {
                                        picUtils.utendo(new PreviewScaleInfo(Float.parseFloat(strArrSplit[0]), Float.parseFloat(strArrSplit[1]), Float.parseFloat(strArrSplit[2])));
                                    }
                                }
                            }
                        } else {
                            fileArr3 = fileArrUtenif;
                        }
                        if (strReplaceAll.contains(OpenAccountUIConstants.UNDER_LINE)) {
                            String[] strArrSplit2 = strReplaceAll.split(OpenAccountUIConstants.UNDER_LINE);
                            String str3 = strArrSplit2[0];
                            if (str3.contains("x")) {
                                String str4 = str3.split("x")[0];
                                String[] strArrSplit3 = str3.split("x");
                                c2 = 1;
                                String str5 = strArrSplit3[1];
                                i15 = Integer.parseInt(str4);
                                i8 = Integer.parseInt(str5);
                            } else {
                                c2 = 1;
                            }
                            String str6 = strArrSplit2[c2];
                            Rgb.LogD("xy =" + str6);
                            if (str6.contains("x")) {
                                String str7 = str6.split("x")[0];
                                String str8 = str6.split("x")[1];
                                viewX = Integer.parseInt(str7);
                                viewY = Integer.parseInt(str8);
                            }
                        }
                        i14 = i8;
                        Rgb.LogD("width =" + i15 + ",height =" + i14 + ",xx =" + viewX + ",yy =" + viewY);
                        CustomViewPositionInfo customViewPositionInfoUtendo2 = picUtils.utendo(picTypeInfo.getType());
                        if (customViewPositionInfoUtendo2 != null) {
                            if (customViewPositionInfoUtendo2.getViewX() >= 0 && customViewPositionInfoUtendo2.getViewX() <= resolutionWidth - i15) {
                                viewX = customViewPositionInfoUtendo2.getViewX();
                            }
                            if (customViewPositionInfoUtendo2.getViewY() >= 0 && customViewPositionInfoUtendo2.getViewY() <= resolutionHeight - i14) {
                                viewY = customViewPositionInfoUtendo2.getViewY();
                            }
                            Rgb.LogD("新的width =" + i15 + ",height =" + i14 + ",xx =" + viewX + ",yy =" + viewY);
                        }
                        picTypeConfigInfo.setPicWidth(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(i15)));
                        picTypeConfigInfo.setPicHeight(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(i14)));
                        picTypeConfigInfo.setX(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(viewX)));
                        picTypeConfigInfo.setY(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(viewY)));
                        picTypeConfigInfo.setAnimationTime(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(0)));
                        picTypeConfigInfo.setReserved(Rgb.getInstance().picReserved());
                        i13++;
                        arrayList6 = arrayList9;
                        fileArrUtenif = fileArr3;
                    } else {
                        i8 = i14;
                        fileArr3 = fileArrUtenif;
                    }
                    i14 = i8;
                    i13++;
                    arrayList6 = arrayList9;
                    fileArrUtenif = fileArr3;
                }
                ArrayList arrayList10 = arrayList6;
                int i16 = i14;
                fileArr = fileArrUtenif;
                str = str2;
                int length = i11;
                byte[] bArr3 = bArr;
                int i17 = 0;
                int i18 = 0;
                int length2 = 0;
                while (i17 < fileArrUtendo.length) {
                    if ((!fileArrUtendo[i17].getName().endsWith("png") && !fileArrUtendo[i17].getName().endsWith("PNG") && !fileArrUtendo[i17].getName().endsWith("bmp") && !fileArrUtendo[i17].getName().endsWith("BMP") && !fileArrUtendo[i17].getName().endsWith("jpg") && !fileArrUtendo[i17].getName().endsWith("JPG")) || fileArrUtendo[i17].getName().equals("preview_bg.png") || fileArrUtendo[i17].getName().equals("preview_bg.PNG")) {
                        fileArr2 = fileArrUtendo;
                        bitmap4 = dialDefaultBackground;
                    } else {
                        int i19 = i18 + 1;
                        if (picTypeInfo.getType() == 17) {
                            bitmap3 = dialDefaultBackground;
                            bArrBmpToRgb565Byte = Rgb.getInstance().antiAliasingBg(bitmap3, viewX, viewY);
                            i6 = i19;
                        } else {
                            bitmap3 = dialDefaultBackground;
                            i6 = i19;
                            bArrBmpToRgb565Byte = picTypeInfo.getType() == 10 ? Rgb.getInstance().bmpToRgb565Byte(Rgb.getInstance().picToBmpSDPath(fileArrUtendo[i17].getAbsolutePath())) : Rgb.getInstance().antiAliasingDefault(bitmap3, Rgb.getInstance().picToBmpSDPath(fileArrUtendo[i17].getAbsolutePath()), viewX, viewY, utenbyte(), i2);
                        }
                        if (z2) {
                            int type = picTypeInfo.getType();
                            fileArr2 = fileArrUtendo;
                            if ((type < 1 || type > 3) && ((type < 35 || type > 37) && ((type < 38 || type > 42) && ((type < 43 || type > 46) && ((type < 52 || type > 56) && (type < 57 || type > 70)))))) {
                                i7 = i16;
                                bArrBmpToRgb565Byte = Rgb.getInstance().picDataCompress(Rgb.getInstance().addHeadBeForeCompress(bArrBmpToRgb565Byte, i15, i7));
                            } else {
                                i7 = i16;
                            }
                            length2 += bArrBmpToRgb565Byte.length;
                        } else {
                            fileArr2 = fileArrUtendo;
                            i7 = i16;
                        }
                        bitmap4 = bitmap3;
                        byte[] bArr4 = new byte[bArr3.length + bArrBmpToRgb565Byte.length];
                        i16 = i7;
                        System.arraycopy(bArr3, 0, bArr4, 0, bArr3.length);
                        System.arraycopy(bArrBmpToRgb565Byte, 0, bArr4, bArr3.length, bArrBmpToRgb565Byte.length);
                        length += bArrBmpToRgb565Byte.length;
                        bArr3 = bArr4;
                        i18 = i6;
                    }
                    i17++;
                    i16 = i16;
                    fileArrUtendo = fileArr2;
                    dialDefaultBackground = bitmap4;
                }
                bitmap2 = dialDefaultBackground;
                int i20 = i16;
                picTypeConfigInfo.setAnimaitonCnt(Rgb.getInstance().intToBytes1L(i18));
                if (!z2) {
                    length2 = i15 * i20 * 2 * i18;
                }
                byte[] bArrBytes4HLExchange = Rgb.getInstance().bytes4HLExchange(Rgb.getInstance().intToBytes4L(length2));
                Rgb.LogD("picCount =" + i18 + ",dataLength =" + length2 + ",addr = " + Rgb.getInstance().bytes2HexString(bArrBytes4HLExchange));
                picTypeConfigInfo.setPicStartAddress(bArrBytes4HLExchange);
                arrayList = arrayList10;
                arrayList.add(picTypeConfigInfo);
                byte[] bArr5 = new byte[24];
                System.arraycopy(picTypeConfigInfo.getType(), 0, bArr5, 0, picTypeConfigInfo.getType().length);
                System.arraycopy(picTypeConfigInfo.getPicWidth(), 0, bArr5, 2, picTypeConfigInfo.getPicWidth().length);
                System.arraycopy(picTypeConfigInfo.getPicStartAddress(), 0, bArr5, 4, picTypeConfigInfo.getPicStartAddress().length);
                System.arraycopy(picTypeConfigInfo.getPicHeight(), 0, bArr5, 8, picTypeConfigInfo.getPicHeight().length);
                System.arraycopy(picTypeConfigInfo.getX(), 0, bArr5, 10, picTypeConfigInfo.getX().length);
                System.arraycopy(picTypeConfigInfo.getY(), 0, bArr5, 12, picTypeConfigInfo.getY().length);
                System.arraycopy(picTypeConfigInfo.getAnimationTime(), 0, bArr5, 14, picTypeConfigInfo.getAnimationTime().length);
                System.arraycopy(picTypeConfigInfo.getAnimaitonCnt(), 0, bArr5, 16, picTypeConfigInfo.getAnimaitonCnt().length);
                System.arraycopy(picTypeConfigInfo.getReserved(), 0, bArr5, 17, picTypeConfigInfo.getReserved().length);
                picTypeInfo.setPicType(bArr5);
                arrayList2 = arrayList7;
                arrayList2.add(picTypeInfo);
                picDataInfo.setPicData(bArr3);
                arrayList3 = arrayList8;
                arrayList3.add(picDataInfo);
                i11 = length;
                i5 = 1;
            } else {
                Rgb.LogD("不显示类型 =" + picTypeInfo.getType());
                str = str2;
                arrayList2 = arrayList4;
                arrayList = arrayList6;
                rgbAarrayInfo = rgbAarrayInfo2;
                arrayList3 = arrayList5;
                fileArr = fileArrUtenif;
                bitmap2 = dialDefaultBackground;
                i5 = 1;
            }
            i10 += i5;
            arrayList6 = arrayList;
            arrayList4 = arrayList2;
            arrayList5 = arrayList3;
            dialDefaultBackground = bitmap2;
            rgbAarrayInfo2 = rgbAarrayInfo;
            fileArrUtenif = fileArr;
            str2 = str;
            i9 = 0;
            picUtils = this;
        }
        ArrayList arrayList11 = arrayList4;
        ArrayList arrayList12 = arrayList6;
        RgbAarrayInfo rgbAarrayInfo3 = rgbAarrayInfo2;
        ArrayList arrayList13 = arrayList5;
        WatchConfigInfo watchConfigInfo = new WatchConfigInfo();
        watchConfigInfo.setSnNo(Rgb.getInstance().SnNo());
        watchConfigInfo.setFileSize(Rgb.getInstance().bytes4HLExchange(Rgb.getInstance().intToBytes4L((fileArrUtenif.length * 24) + i11)));
        watchConfigInfo.setPixelWidth(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(resolutionWidth)));
        watchConfigInfo.setPixelHeight(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(resolutionHeight)));
        watchConfigInfo.setScreenType(Rgb.getInstance().intToBytes1L(this.screenType));
        watchConfigInfo.setHasBg(Rgb.getInstance().intToBytes1L(i12));
        watchConfigInfo.setIsWatchVaild(Rgb.getInstance().intToBytes1L(255));
        watchConfigInfo.setReserved(Rgb.getInstance().watchReserved());
        watchConfigInfo.setFileCrc(Rgb.getInstance().bytes4HLExchange(Rgb.getInstance().intToBytes4L(805233808)));
        rgbAarrayInfo3.setWatchConfigInfo(watchConfigInfo);
        byte[] bArr6 = new byte[24];
        System.arraycopy(watchConfigInfo.getSnNo(), 0, bArr6, 0, watchConfigInfo.getSnNo().length);
        System.arraycopy(watchConfigInfo.getFileSize(), 0, bArr6, 4, watchConfigInfo.getFileSize().length);
        System.arraycopy(watchConfigInfo.getFileCrc(), 0, bArr6, 8, watchConfigInfo.getFileCrc().length);
        System.arraycopy(watchConfigInfo.getPixelWidth(), 0, bArr6, 12, watchConfigInfo.getPixelWidth().length);
        System.arraycopy(watchConfigInfo.getPixelHeight(), 0, bArr6, 14, watchConfigInfo.getPixelHeight().length);
        System.arraycopy(watchConfigInfo.getScreenType(), 0, bArr6, 16, watchConfigInfo.getScreenType().length);
        System.arraycopy(watchConfigInfo.getHasBg(), 0, bArr6, 17, watchConfigInfo.getHasBg().length);
        System.arraycopy(watchConfigInfo.getIsWatchVaild(), 0, bArr6, 18, watchConfigInfo.getIsWatchVaild().length);
        System.arraycopy(watchConfigInfo.getReserved(), 0, bArr6, 19, watchConfigInfo.getReserved().length);
        rgbAarrayInfo3.setWatchConfigInfo(watchConfigInfo);
        rgbAarrayInfo3.setWatchConfig(bArr6);
        Collections.sort(arrayList12);
        Collections.sort(arrayList11);
        Collections.sort(arrayList13);
        rgbAarrayInfo3.setPicTypeConfigList(arrayList12);
        rgbAarrayInfo3.setPicTypeConfig(arrayList11);
        rgbAarrayInfo3.setPicDataConfig(arrayList13);
        byte[] bArrStitchingArrays = Rgb.getInstance().stitchingArrays(utendo(rgbAarrayInfo3));
        watchConfigInfo.setFileSize(Rgb.getInstance().bytes4HLExchange(Rgb.getInstance().intToBytes4L(bArrStitchingArrays.length - 24)));
        System.arraycopy(watchConfigInfo.getFileSize(), 0, bArrStitchingArrays, 4, watchConfigInfo.getFileSize().length);
        byte[] bArrBytes4HLExchange2 = Rgb.getInstance().bytes4HLExchange(Rgb.getInstance().intToBytes4L(Rgb.getInstance().uteDataCrc32(bArrStitchingArrays)));
        watchConfigInfo.setFileCrc(bArrBytes4HLExchange2);
        System.arraycopy(bArrBytes4HLExchange2, 0, bArrStitchingArrays, 8, bArrBytes4HLExchange2.length);
        if (z2) {
            bArrStitchingArrays[19] = (byte) SPUtil.getInstance().getDialScreenCompatibleLevel();
        }
        return bArrStitchingArrays;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public final byte[] utendo(Bitmap bitmap, int i2, boolean z2) throws IOException, NumberFormatException {
        ArrayList arrayList;
        int i3;
        int i4;
        AssetManager assetManager;
        String str;
        String str2;
        String[] strArr;
        Bitmap bitmap2;
        ArrayList arrayList2;
        ArrayList arrayList3;
        ArrayList arrayList4;
        int i5;
        String str3;
        Bitmap bitmap3;
        String[] strArr2;
        byte[] bArrBmpToRgb565Byte;
        Bitmap bitmap4;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        String[] strArr3;
        String str4;
        char c2;
        char c3;
        PicUtils picUtils = this;
        int i11 = 0;
        String str5 = "";
        int resolutionWidth = SPUtil.getInstance().getResolutionWidth();
        int resolutionHeight = SPUtil.getInstance().getResolutionHeight();
        Bitmap dialDefaultBackground = bitmap == null ? getDialDefaultBackground() : bitmap;
        String str6 = picUtils.folderDial;
        AssetManager assetManagerUtenfor = utenfor();
        String[] list = assetManagerUtenfor.list(str6);
        RgbAarrayInfo rgbAarrayInfo = new RgbAarrayInfo();
        ArrayList arrayList5 = new ArrayList();
        ArrayList arrayList6 = new ArrayList();
        ArrayList arrayList7 = new ArrayList();
        int i12 = 0;
        int i13 = 0;
        int i14 = 0;
        while (i12 < list.length) {
            byte[] bArr = new byte[i11];
            PicTypeConfigInfo picTypeConfigInfo = new PicTypeConfigInfo();
            PicTypeInfo picTypeInfo = new PicTypeInfo();
            PicDataInfo picDataInfo = new PicDataInfo();
            String str7 = list[i12];
            str7.hashCode();
            RgbAarrayInfo rgbAarrayInfo2 = rgbAarrayInfo;
            char c4 = 65535;
            switch (str7.hashCode()) {
                case -2076896158:
                    if (str7.equals("timeday1")) {
                        c4 = 0;
                        break;
                    }
                    break;
                case -2076896157:
                    if (str7.equals("timeday2")) {
                        c4 = 1;
                        break;
                    }
                    break;
                case -2076763599:
                    if (str7.equals("timehour")) {
                        c4 = 2;
                        break;
                    }
                    break;
                case -2076620692:
                    if (str7.equals("timemin1")) {
                        c4 = 3;
                        break;
                    }
                    break;
                case -2076620691:
                    if (str7.equals("timemin2")) {
                        c3 = 4;
                        c4 = c3;
                        break;
                    }
                    break;
                case -1893555910:
                    if (str7.equals("stepnum")) {
                        c4 = 5;
                        break;
                    }
                    break;
                case -1553608662:
                    if (str7.equals("day2month")) {
                        c3 = 6;
                        c4 = c3;
                        break;
                    }
                    break;
                case -1332194002:
                    if (str7.equals("background")) {
                        c4 = 7;
                        break;
                    }
                    break;
                case -1313922641:
                    if (str7.equals("timeday")) {
                        c3 = '\b';
                        c4 = c3;
                        break;
                    }
                    break;
                case -1313913755:
                    if (str7.equals("timemin")) {
                        c4 = '\t';
                        break;
                    }
                    break;
                case -914129323:
                    if (str7.equals("iconbluetooth")) {
                        c3 = '\n';
                        c4 = c3;
                        break;
                    }
                    break;
                case -736993595:
                    if (str7.equals("iconstep")) {
                        c4 = 11;
                        break;
                    }
                    break;
                case -331239923:
                    if (str7.equals(bc.Z)) {
                        c4 = '\f';
                        break;
                    }
                    break;
                case -318184504:
                    if (str7.equals("preview")) {
                        c4 = CharUtils.CR;
                        break;
                    }
                    break;
                case -278000284:
                    if (str7.equals("stepproccess")) {
                        c3 = 14;
                        c4 = c3;
                        break;
                    }
                    break;
                case -246168380:
                    if (str7.equals("hour2min")) {
                        c4 = 15;
                        break;
                    }
                    break;
                case 2998057:
                    if (str7.equals("ampm")) {
                        c3 = 16;
                        c4 = c3;
                        break;
                    }
                    break;
                case 3645428:
                    if (str7.equals("week")) {
                        c3 = 17;
                        c4 = c3;
                        break;
                    }
                    break;
                case 44837920:
                    if (str7.equals("timehour1")) {
                        c4 = 18;
                        break;
                    }
                    break;
                case 44837921:
                    if (str7.equals("timehour2")) {
                        c4 = 19;
                        break;
                    }
                    break;
                case 49448915:
                    if (str7.equals("timemonth")) {
                        c3 = 20;
                        c4 = c3;
                        break;
                    }
                    break;
                case 201103807:
                    if (str7.equals("hearticon")) {
                        c3 = 21;
                        c4 = c3;
                        break;
                    }
                    break;
                case 201269937:
                    if (str7.equals("heartnum1")) {
                        c3 = 22;
                        c4 = c3;
                        break;
                    }
                    break;
                case 201269938:
                    if (str7.equals("heartnum2")) {
                        c3 = 23;
                        c4 = c3;
                        break;
                    }
                    break;
                case 201269939:
                    if (str7.equals("heartnum3")) {
                        c3 = 24;
                        c4 = c3;
                        break;
                    }
                    break;
                case 1429308983:
                    if (str7.equals("stepnum1")) {
                        c4 = 25;
                        break;
                    }
                    break;
                case 1429308984:
                    if (str7.equals("stepnum2")) {
                        c3 = JSONLexer.EOI;
                        c4 = c3;
                        break;
                    }
                    break;
                case 1429308985:
                    if (str7.equals("stepnum3")) {
                        c3 = 27;
                        c4 = c3;
                        break;
                    }
                    break;
                case 1429308986:
                    if (str7.equals("stepnum4")) {
                        c3 = 28;
                        c4 = c3;
                        break;
                    }
                    break;
                case 1429308987:
                    if (str7.equals("stepnum5")) {
                        c3 = 29;
                        c4 = c3;
                        break;
                    }
                    break;
                case 1532916414:
                    if (str7.equals("timemonth1")) {
                        c3 = 30;
                        c4 = c3;
                        break;
                    }
                    break;
                case 1532916415:
                    if (str7.equals("timemonth2")) {
                        c3 = 31;
                        c4 = c3;
                        break;
                    }
                    break;
            }
            switch (c4) {
                case 0:
                    arrayList = arrayList6;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(65)));
                    picTypeInfo.setType(65);
                    i3 = 65;
                    picDataInfo.setType(i3);
                    break;
                case 1:
                    arrayList = arrayList6;
                    i4 = 66;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(66)));
                    picTypeInfo.setType(i4);
                    i3 = i4;
                    picDataInfo.setType(i3);
                    break;
                case 2:
                    arrayList = arrayList6;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(4)));
                    picTypeInfo.setType(4);
                    i3 = 4;
                    picDataInfo.setType(i3);
                    break;
                case 3:
                    arrayList = arrayList6;
                    i4 = 59;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(59)));
                    picTypeInfo.setType(i4);
                    i3 = i4;
                    picDataInfo.setType(i3);
                    break;
                case 4:
                    arrayList = arrayList6;
                    i4 = 60;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(60)));
                    picTypeInfo.setType(i4);
                    i3 = i4;
                    picDataInfo.setType(i3);
                    break;
                case 5:
                    arrayList = arrayList6;
                    i4 = 25;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(25)));
                    picTypeInfo.setType(i4);
                    i3 = i4;
                    picDataInfo.setType(i3);
                    break;
                case 6:
                    arrayList = arrayList6;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(14)));
                    picTypeInfo.setType(14);
                    i3 = 14;
                    picDataInfo.setType(i3);
                    break;
                case 7:
                    arrayList = arrayList6;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(17)));
                    picTypeInfo.setType(17);
                    i3 = 17;
                    i14 = 1;
                    picDataInfo.setType(i3);
                    break;
                case '\b':
                    arrayList = arrayList6;
                    i4 = 13;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(13)));
                    picTypeInfo.setType(i4);
                    i3 = i4;
                    picDataInfo.setType(i3);
                    break;
                case '\t':
                    arrayList = arrayList6;
                    i4 = 5;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(5)));
                    picTypeInfo.setType(i4);
                    i3 = i4;
                    picDataInfo.setType(i3);
                    break;
                case '\n':
                    arrayList = arrayList6;
                    i4 = 9;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(9)));
                    picTypeInfo.setType(i4);
                    i3 = i4;
                    picDataInfo.setType(i3);
                    break;
                case 11:
                    arrayList = arrayList6;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(24)));
                    picTypeInfo.setType(24);
                    i3 = 24;
                    picDataInfo.setType(i3);
                    break;
                case '\f':
                    arrayList = arrayList6;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(8)));
                    picTypeInfo.setType(8);
                    i3 = 8;
                    picDataInfo.setType(i3);
                    break;
                case '\r':
                    arrayList = arrayList6;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(10)));
                    picTypeInfo.setType(10);
                    i3 = 10;
                    picDataInfo.setType(i3);
                    break;
                case 14:
                    arrayList = arrayList6;
                    i4 = 32;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(32)));
                    picTypeInfo.setType(i4);
                    i3 = i4;
                    picDataInfo.setType(i3);
                    break;
                case 15:
                    arrayList = arrayList6;
                    i4 = 7;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(7)));
                    picTypeInfo.setType(i4);
                    i3 = i4;
                    picDataInfo.setType(i3);
                    break;
                case 16:
                    arrayList = arrayList6;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(16)));
                    picTypeInfo.setType(16);
                    i3 = 16;
                    picDataInfo.setType(i3);
                    break;
                case 17:
                    arrayList = arrayList6;
                    i4 = 15;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(15)));
                    picTypeInfo.setType(i4);
                    i3 = i4;
                    picDataInfo.setType(i3);
                    break;
                case 18:
                    arrayList = arrayList6;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(57)));
                    picTypeInfo.setType(57);
                    i3 = 57;
                    picDataInfo.setType(i3);
                    break;
                case 19:
                    arrayList = arrayList6;
                    i4 = 58;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(58)));
                    picTypeInfo.setType(i4);
                    i3 = i4;
                    picDataInfo.setType(i3);
                    break;
                case 20:
                    arrayList = arrayList6;
                    i4 = 11;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(11)));
                    picTypeInfo.setType(i4);
                    i3 = i4;
                    picDataInfo.setType(i3);
                    break;
                case 21:
                    arrayList = arrayList6;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(18)));
                    picTypeInfo.setType(18);
                    i3 = 18;
                    picDataInfo.setType(i3);
                    break;
                case 22:
                    arrayList = arrayList6;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(35)));
                    picTypeInfo.setType(35);
                    i3 = 35;
                    picDataInfo.setType(i3);
                    break;
                case 23:
                    arrayList = arrayList6;
                    i4 = 36;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(36)));
                    picTypeInfo.setType(i4);
                    i3 = i4;
                    picDataInfo.setType(i3);
                    break;
                case 24:
                    arrayList = arrayList6;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(37)));
                    picTypeInfo.setType(37);
                    i3 = 37;
                    picDataInfo.setType(i3);
                    break;
                case 25:
                    arrayList = arrayList6;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(38)));
                    picTypeInfo.setType(38);
                    i3 = 38;
                    picDataInfo.setType(i3);
                    break;
                case 26:
                    arrayList = arrayList6;
                    i4 = 39;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(39)));
                    picTypeInfo.setType(i4);
                    i3 = i4;
                    picDataInfo.setType(i3);
                    break;
                case 27:
                    arrayList = arrayList6;
                    i4 = 40;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(40)));
                    picTypeInfo.setType(i4);
                    i3 = i4;
                    picDataInfo.setType(i3);
                    break;
                case 28:
                    arrayList = arrayList6;
                    i4 = 41;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(41)));
                    picTypeInfo.setType(i4);
                    i3 = i4;
                    picDataInfo.setType(i3);
                    break;
                case 29:
                    arrayList = arrayList6;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(42)));
                    picTypeInfo.setType(42);
                    i3 = 42;
                    picDataInfo.setType(i3);
                    break;
                case 30:
                    arrayList = arrayList6;
                    i4 = 63;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(63)));
                    picTypeInfo.setType(i4);
                    i3 = i4;
                    picDataInfo.setType(i3);
                    break;
                case 31:
                    arrayList = arrayList6;
                    i3 = 64;
                    picTypeConfigInfo.setType(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(64)));
                    picTypeInfo.setType(64);
                    picDataInfo.setType(i3);
                    break;
                default:
                    arrayList = arrayList6;
                    break;
            }
            CustomViewPositionInfo customViewPositionInfoUtendo = picUtils.utendo(picTypeInfo.getType());
            if (customViewPositionInfoUtendo == null || customViewPositionInfoUtendo.isViewDisplay()) {
                String str8 = str6 + "/" + list[i12];
                String[] strArrUtendo = picUtils.utendo(assetManagerUtenfor.list(str8));
                ArrayList arrayList8 = arrayList5;
                assetManager = assetManagerUtenfor;
                str = str6;
                ArrayList arrayList9 = arrayList7;
                int viewY = 0;
                int viewX = 0;
                int i15 = 0;
                int i16 = 0;
                int i17 = 0;
                while (i16 < strArrUtendo.length) {
                    int i18 = i15;
                    if (strArrUtendo[i16].endsWith("txt") || strArrUtendo[i16].endsWith("TXT")) {
                        String strReplaceAll = strArrUtendo[i16].replaceAll("config_", str5).replaceAll(".txt", str5);
                        StringBuilder sb = new StringBuilder();
                        i10 = i17;
                        sb.append("all2 =");
                        sb.append(strReplaceAll);
                        sb.append(",");
                        sb.append(list[i12]);
                        Rgb.LogD(sb.toString());
                        if (strReplaceAll.contains("scalepreview_")) {
                            strArr3 = list;
                            if (list[i12].equals("preview")) {
                                String strReplaceAll2 = strReplaceAll.replaceAll("scalepreview_", str5);
                                if (strReplaceAll2.contains(OpenAccountUIConstants.UNDER_LINE)) {
                                    String[] strArrSplit = strReplaceAll2.split(OpenAccountUIConstants.UNDER_LINE);
                                    if (strArrSplit.length >= 3) {
                                        picUtils.utendo(new PreviewScaleInfo(Float.parseFloat(strArrSplit[0]), Float.parseFloat(strArrSplit[1]), Float.parseFloat(strArrSplit[2])));
                                    }
                                }
                            }
                        } else {
                            strArr3 = list;
                        }
                        if (strReplaceAll.contains(OpenAccountUIConstants.UNDER_LINE)) {
                            String[] strArrSplit2 = strReplaceAll.split(OpenAccountUIConstants.UNDER_LINE);
                            String str9 = strArrSplit2[0];
                            if (str9.contains("x")) {
                                String str10 = str9.split("x")[0];
                                String[] strArrSplit3 = str9.split("x");
                                c2 = 1;
                                String str11 = strArrSplit3[1];
                                int i19 = Integer.parseInt(str10);
                                i10 = Integer.parseInt(str11);
                                i15 = i19;
                            } else {
                                c2 = 1;
                                i15 = i18;
                            }
                            String str12 = strArrSplit2[c2];
                            StringBuilder sb2 = new StringBuilder();
                            str4 = str5;
                            sb2.append("xy =");
                            sb2.append(str12);
                            Rgb.LogD(sb2.toString());
                            if (str12.contains("x")) {
                                String str13 = str12.split("x")[0];
                                String str14 = str12.split("x")[1];
                                viewX = Integer.parseInt(str13);
                                viewY = Integer.parseInt(str14);
                            }
                        } else {
                            str4 = str5;
                            i15 = i18;
                        }
                        i17 = i10;
                        Rgb.LogD("width =" + i15 + ",height =" + i17 + ",xx =" + viewX + ",yy =" + viewY);
                        CustomViewPositionInfo customViewPositionInfoUtendo2 = picUtils.utendo(picTypeInfo.getType());
                        if (customViewPositionInfoUtendo2 != null) {
                            if (customViewPositionInfoUtendo2.getViewX() >= 0 && customViewPositionInfoUtendo2.getViewX() <= resolutionWidth - i15) {
                                viewX = customViewPositionInfoUtendo2.getViewX();
                            }
                            if (customViewPositionInfoUtendo2.getViewY() >= 0 && customViewPositionInfoUtendo2.getViewY() <= resolutionHeight - i17) {
                                viewY = customViewPositionInfoUtendo2.getViewY();
                            }
                            Rgb.LogD("新的width =" + i15 + ",height =" + i17 + ",xx =" + viewX + ",yy =" + viewY);
                        }
                        picTypeConfigInfo.setPicWidth(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(i15)));
                        picTypeConfigInfo.setPicHeight(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(i17)));
                        picTypeConfigInfo.setX(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(viewX)));
                        picTypeConfigInfo.setY(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(viewY)));
                        picTypeConfigInfo.setAnimationTime(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(0)));
                        picTypeConfigInfo.setReserved(Rgb.getInstance().picReserved());
                        i16++;
                        list = strArr3;
                        str5 = str4;
                    } else {
                        strArr3 = list;
                        i10 = i17;
                    }
                    str4 = str5;
                    i15 = i18;
                    i17 = i10;
                    i16++;
                    list = strArr3;
                    str5 = str4;
                }
                str2 = str5;
                strArr = list;
                int i20 = i15;
                int i21 = i17;
                int length = i13;
                byte[] bArr2 = bArr;
                int i22 = 0;
                int i23 = 0;
                int length2 = 0;
                while (i22 < strArrUtendo.length) {
                    if ((!strArrUtendo[i22].endsWith("png") && !strArrUtendo[i22].endsWith("PNG") && !strArrUtendo[i22].endsWith("bmp") && !strArrUtendo[i22].endsWith("BMP") && !strArrUtendo[i22].endsWith("jpg") && !strArrUtendo[i22].endsWith("JPG")) || strArrUtendo[i22].equals("preview_bg.png") || strArrUtendo[i22].equals("preview_bg.PNG")) {
                        str3 = str8;
                        i8 = viewY;
                        i9 = viewX;
                        bitmap4 = dialDefaultBackground;
                        i6 = i20;
                        i7 = i21;
                        strArr2 = strArrUtendo;
                    } else {
                        i23++;
                        String str15 = str8 + "/" + strArrUtendo[i22];
                        str3 = str8;
                        if (picTypeInfo.getType() == 17) {
                            bitmap3 = dialDefaultBackground;
                            bArrBmpToRgb565Byte = Rgb.getInstance().antiAliasingBg(bitmap3, viewX, viewY);
                            strArr2 = strArrUtendo;
                        } else {
                            bitmap3 = dialDefaultBackground;
                            strArr2 = strArrUtendo;
                            bArrBmpToRgb565Byte = picTypeInfo.getType() == 10 ? Rgb.getInstance().bmpToRgb565Byte(Rgb.getInstance().picToBmpAssetPath(str15)) : Rgb.getInstance().antiAliasingDefault(bitmap3, Rgb.getInstance().picToBmpAssetPath(str15), viewX, viewY, utenbyte(), i2);
                        }
                        if (z2) {
                            int type = picTypeInfo.getType();
                            if ((type < 1 || type > 3) && ((type < 35 || type > 37) && ((type < 38 || type > 42) && ((type < 43 || type > 46) && ((type < 52 || type > 56) && (type < 57 || type > 70)))))) {
                                bitmap4 = bitmap3;
                                i6 = i20;
                                i7 = i21;
                                bArrBmpToRgb565Byte = Rgb.getInstance().picDataCompress(Rgb.getInstance().addHeadBeForeCompress(bArrBmpToRgb565Byte, i6, i7));
                            } else {
                                bitmap4 = bitmap3;
                                i6 = i20;
                                i7 = i21;
                            }
                            length2 += bArrBmpToRgb565Byte.length;
                        } else {
                            bitmap4 = bitmap3;
                            i6 = i20;
                            i7 = i21;
                        }
                        i8 = viewY;
                        byte[] bArr3 = new byte[bArr2.length + bArrBmpToRgb565Byte.length];
                        i9 = viewX;
                        System.arraycopy(bArr2, 0, bArr3, 0, bArr2.length);
                        System.arraycopy(bArrBmpToRgb565Byte, 0, bArr3, bArr2.length, bArrBmpToRgb565Byte.length);
                        length += bArrBmpToRgb565Byte.length;
                        bArr2 = bArr3;
                    }
                    i22++;
                    i21 = i7;
                    i20 = i6;
                    viewY = i8;
                    viewX = i9;
                    strArrUtendo = strArr2;
                    str8 = str3;
                    dialDefaultBackground = bitmap4;
                }
                bitmap2 = dialDefaultBackground;
                int i24 = i20;
                int i25 = i21;
                picTypeConfigInfo.setAnimaitonCnt(Rgb.getInstance().intToBytes1L(i23));
                if (!z2) {
                    length2 = i24 * i25 * 2 * i23;
                }
                byte[] bArrBytes4HLExchange = Rgb.getInstance().bytes4HLExchange(Rgb.getInstance().intToBytes4L(length2));
                Rgb.LogD("picCount =" + i23 + ",dataLength =" + length2 + ",addr = " + Rgb.getInstance().bytes2HexString(bArrBytes4HLExchange));
                picTypeConfigInfo.setPicStartAddress(bArrBytes4HLExchange);
                arrayList2 = arrayList9;
                arrayList2.add(picTypeConfigInfo);
                byte[] bArr4 = new byte[24];
                System.arraycopy(picTypeConfigInfo.getType(), 0, bArr4, 0, picTypeConfigInfo.getType().length);
                System.arraycopy(picTypeConfigInfo.getPicWidth(), 0, bArr4, 2, picTypeConfigInfo.getPicWidth().length);
                System.arraycopy(picTypeConfigInfo.getPicStartAddress(), 0, bArr4, 4, picTypeConfigInfo.getPicStartAddress().length);
                System.arraycopy(picTypeConfigInfo.getPicHeight(), 0, bArr4, 8, picTypeConfigInfo.getPicHeight().length);
                System.arraycopy(picTypeConfigInfo.getX(), 0, bArr4, 10, picTypeConfigInfo.getX().length);
                System.arraycopy(picTypeConfigInfo.getY(), 0, bArr4, 12, picTypeConfigInfo.getY().length);
                System.arraycopy(picTypeConfigInfo.getAnimationTime(), 0, bArr4, 14, picTypeConfigInfo.getAnimationTime().length);
                System.arraycopy(picTypeConfigInfo.getAnimaitonCnt(), 0, bArr4, 16, picTypeConfigInfo.getAnimaitonCnt().length);
                System.arraycopy(picTypeConfigInfo.getReserved(), 0, bArr4, 17, picTypeConfigInfo.getReserved().length);
                picTypeInfo.setPicType(bArr4);
                arrayList3 = arrayList8;
                arrayList3.add(picTypeInfo);
                picDataInfo.setPicData(bArr2);
                arrayList4 = arrayList;
                arrayList4.add(picDataInfo);
                i13 = length;
                i5 = 1;
            } else {
                Rgb.LogD("不显示类型 =" + picTypeInfo.getType());
                str2 = str5;
                strArr = list;
                arrayList3 = arrayList5;
                assetManager = assetManagerUtenfor;
                str = str6;
                arrayList2 = arrayList7;
                bitmap2 = dialDefaultBackground;
                arrayList4 = arrayList;
                i5 = 1;
            }
            i12 += i5;
            arrayList7 = arrayList2;
            arrayList5 = arrayList3;
            arrayList6 = arrayList4;
            dialDefaultBackground = bitmap2;
            rgbAarrayInfo = rgbAarrayInfo2;
            assetManagerUtenfor = assetManager;
            str6 = str;
            list = strArr;
            str5 = str2;
            i11 = 0;
            picUtils = this;
        }
        ArrayList arrayList10 = arrayList6;
        ArrayList arrayList11 = arrayList5;
        RgbAarrayInfo rgbAarrayInfo3 = rgbAarrayInfo;
        ArrayList arrayList12 = arrayList7;
        WatchConfigInfo watchConfigInfo = new WatchConfigInfo();
        watchConfigInfo.setSnNo(Rgb.getInstance().SnNo());
        watchConfigInfo.setFileSize(Rgb.getInstance().bytes4HLExchange(Rgb.getInstance().intToBytes4L((list.length * 24) + i13)));
        watchConfigInfo.setPixelWidth(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(resolutionWidth)));
        watchConfigInfo.setPixelHeight(Rgb.getInstance().bytes2HLExchange(Rgb.getInstance().intToBytes2L(resolutionHeight)));
        watchConfigInfo.setScreenType(Rgb.getInstance().intToBytes1L(this.screenType));
        watchConfigInfo.setHasBg(Rgb.getInstance().intToBytes1L(i14));
        watchConfigInfo.setIsWatchVaild(Rgb.getInstance().intToBytes1L(255));
        watchConfigInfo.setReserved(Rgb.getInstance().watchReserved());
        watchConfigInfo.setFileCrc(Rgb.getInstance().bytes4HLExchange(Rgb.getInstance().intToBytes4L(805233808)));
        rgbAarrayInfo3.setWatchConfigInfo(watchConfigInfo);
        byte[] bArr5 = new byte[24];
        System.arraycopy(watchConfigInfo.getSnNo(), 0, bArr5, 0, watchConfigInfo.getSnNo().length);
        System.arraycopy(watchConfigInfo.getFileSize(), 0, bArr5, 4, watchConfigInfo.getFileSize().length);
        System.arraycopy(watchConfigInfo.getFileCrc(), 0, bArr5, 8, watchConfigInfo.getFileCrc().length);
        System.arraycopy(watchConfigInfo.getPixelWidth(), 0, bArr5, 12, watchConfigInfo.getPixelWidth().length);
        System.arraycopy(watchConfigInfo.getPixelHeight(), 0, bArr5, 14, watchConfigInfo.getPixelHeight().length);
        System.arraycopy(watchConfigInfo.getScreenType(), 0, bArr5, 16, watchConfigInfo.getScreenType().length);
        System.arraycopy(watchConfigInfo.getHasBg(), 0, bArr5, 17, watchConfigInfo.getHasBg().length);
        System.arraycopy(watchConfigInfo.getIsWatchVaild(), 0, bArr5, 18, watchConfigInfo.getIsWatchVaild().length);
        System.arraycopy(watchConfigInfo.getReserved(), 0, bArr5, 19, watchConfigInfo.getReserved().length);
        rgbAarrayInfo3.setWatchConfigInfo(watchConfigInfo);
        rgbAarrayInfo3.setWatchConfig(bArr5);
        Collections.sort(arrayList12);
        Collections.sort(arrayList11);
        Collections.sort(arrayList10);
        rgbAarrayInfo3.setPicTypeConfigList(arrayList12);
        rgbAarrayInfo3.setPicTypeConfig(arrayList11);
        rgbAarrayInfo3.setPicDataConfig(arrayList10);
        byte[] bArrStitchingArrays = Rgb.getInstance().stitchingArrays(utendo(rgbAarrayInfo3));
        watchConfigInfo.setFileSize(Rgb.getInstance().bytes4HLExchange(Rgb.getInstance().intToBytes4L(bArrStitchingArrays.length - 24)));
        System.arraycopy(watchConfigInfo.getFileSize(), 0, bArrStitchingArrays, 4, watchConfigInfo.getFileSize().length);
        byte[] bArrBytes4HLExchange2 = Rgb.getInstance().bytes4HLExchange(Rgb.getInstance().intToBytes4L(Rgb.getInstance().uteDataCrc32(bArrStitchingArrays)));
        watchConfigInfo.setFileCrc(bArrBytes4HLExchange2);
        System.arraycopy(bArrBytes4HLExchange2, 0, bArrStitchingArrays, 8, bArrBytes4HLExchange2.length);
        if (z2) {
            bArrStitchingArrays[19] = (byte) SPUtil.getInstance().getDialScreenCompatibleLevel();
        }
        return bArrStitchingArrays;
    }

    public final File[] utenif(String str) {
        File[] fileArrListFiles = new File(str).listFiles();
        if (fileArrListFiles == null) {
            Rgb.LogD("请检查图片路径是否正确5 dialList =" + Arrays.toString(fileArrListFiles) + ",path =" + str);
            return null;
        }
        for (int i2 = 0; i2 < fileArrListFiles.length; i2++) {
            int i3 = 0;
            while (true) {
                if (i3 >= fileArrListFiles.length) {
                    break;
                }
                if (fileArrListFiles[i3].getName().startsWith("dial_")) {
                    fileArrListFiles = fileArrListFiles[i3].listFiles();
                    break;
                }
                i3++;
            }
        }
        return fileArrListFiles;
    }

    public final Bitmap utendo(String str) {
        File[] fileArrUtenif = utenif(str);
        Bitmap bitmapPicToBmpSDPath = null;
        if (fileArrUtenif == null) {
            Rgb.LogD("请检查图片路径是否正确2 dialList =" + Arrays.toString(fileArrUtenif) + ",path =" + str);
            return null;
        }
        for (int i2 = 0; i2 < fileArrUtenif.length; i2++) {
            String name = fileArrUtenif[i2].getName();
            name.hashCode();
            if (name.equals("background")) {
                File[] fileArrListFiles = fileArrUtenif[i2].listFiles();
                for (int i3 = 0; i3 < fileArrListFiles.length; i3++) {
                    if ((fileArrListFiles[i3].getName().endsWith("png") || fileArrListFiles[i3].getName().endsWith("PNG") || fileArrListFiles[i3].getName().endsWith("bmp") || fileArrListFiles[i3].getName().endsWith("BMP") || fileArrListFiles[i3].getName().endsWith("jpg") || fileArrListFiles[i3].getName().endsWith("JPG")) && (fileArrListFiles[i3].getName().equals("1.png") || fileArrListFiles[i3].getName().equals("1.PNG") || fileArrListFiles[i3].getName().equals("01.png") || fileArrListFiles[i3].getName().equals("01.PNG"))) {
                        bitmapPicToBmpSDPath = Rgb.getInstance().picToBmpSDPath(fileArrListFiles[i3].getAbsolutePath());
                    }
                }
            }
        }
        return bitmapPicToBmpSDPath;
    }

    public final void utenif(int i2) {
        this.myCurrentFontColor = i2;
    }

    public final CustomViewPositionInfo utendo(int i2) {
        List<CustomViewPositionInfo> list = this.mCustomViewPositionList;
        if (list != null && list.size() != 0) {
            for (CustomViewPositionInfo customViewPositionInfo : this.mCustomViewPositionList) {
                if (i2 == customViewPositionInfo.getViewType()) {
                    return customViewPositionInfo;
                }
            }
        }
        return null;
    }

    public final ArrayList utendo(byte[] bArr) {
        int i2;
        BitmapInfo bitmapInfo;
        ArrayList arrayList = new ArrayList();
        int i3 = 0;
        while (true) {
            i2 = 24;
            if (i3 >= bArr.length / 24) {
                i3 = 0;
                break;
            }
            int i4 = i3 * 24;
            if (((bArr[i4 + 1] & 255) | ((bArr[i4] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK)) == 0 && i3 > 1) {
                break;
            }
            i3++;
        }
        int i5 = 0;
        while (i5 < i3) {
            byte[] bArr2 = new byte[i2];
            int i6 = i5 * 24;
            if (i5 == 0) {
                System.arraycopy(bArr, i6, bArr2, 0, i2);
                WatchConfigInfo watchConfigInfo = new WatchConfigInfo();
                System.arraycopy(bArr2, 0, watchConfigInfo.getSnNo(), 0, watchConfigInfo.getSnNo().length);
                System.arraycopy(bArr2, 4, watchConfigInfo.getFileSize(), 0, watchConfigInfo.getFileSize().length);
                System.arraycopy(bArr2, 8, watchConfigInfo.getFileCrc(), 0, watchConfigInfo.getFileCrc().length);
                System.arraycopy(bArr2, 12, watchConfigInfo.getPixelWidth(), 0, watchConfigInfo.getPixelWidth().length);
                System.arraycopy(bArr2, 14, watchConfigInfo.getPixelHeight(), 0, watchConfigInfo.getPixelHeight().length);
                System.arraycopy(bArr2, 16, watchConfigInfo.getScreenType(), 0, watchConfigInfo.getScreenType().length);
                System.arraycopy(bArr2, 17, watchConfigInfo.getHasBg(), 0, watchConfigInfo.getHasBg().length);
                System.arraycopy(bArr2, 18, watchConfigInfo.getIsWatchVaild(), 0, watchConfigInfo.getIsWatchVaild().length);
                System.arraycopy(bArr2, 19, watchConfigInfo.getReserved(), 0, watchConfigInfo.getReserved().length);
                Rgb.getInstance().bytes1ToInt(watchConfigInfo.getScreenType());
                Rgb.getInstance().bytes2HLExchangeInt(watchConfigInfo.getPixelWidth());
                Rgb.getInstance().bytes2HLExchangeInt(watchConfigInfo.getPixelHeight());
            } else {
                System.arraycopy(bArr, i6, bArr2, 0, i2);
                PicTypeConfigInfo picTypeConfigInfo = new PicTypeConfigInfo();
                System.arraycopy(bArr2, 0, picTypeConfigInfo.getType(), 0, picTypeConfigInfo.getType().length);
                System.arraycopy(bArr2, 2, picTypeConfigInfo.getPicWidth(), 0, picTypeConfigInfo.getPicWidth().length);
                System.arraycopy(bArr2, 4, picTypeConfigInfo.getPicStartAddress(), 0, picTypeConfigInfo.getPicStartAddress().length);
                System.arraycopy(bArr2, 8, picTypeConfigInfo.getPicHeight(), 0, picTypeConfigInfo.getPicHeight().length);
                System.arraycopy(bArr2, 10, picTypeConfigInfo.getX(), 0, picTypeConfigInfo.getX().length);
                System.arraycopy(bArr2, 12, picTypeConfigInfo.getY(), 0, picTypeConfigInfo.getY().length);
                System.arraycopy(bArr2, 14, picTypeConfigInfo.getAnimationTime(), 0, picTypeConfigInfo.getAnimationTime().length);
                System.arraycopy(bArr2, 16, picTypeConfigInfo.getAnimaitonCnt(), 0, picTypeConfigInfo.getAnimaitonCnt().length);
                System.arraycopy(bArr2, 17, picTypeConfigInfo.getReserved(), 0, picTypeConfigInfo.getReserved().length);
                int iBytes2HLExchangeInt = Rgb.getInstance().bytes2HLExchangeInt(picTypeConfigInfo.getType());
                int iBytes2HLExchangeInt2 = Rgb.getInstance().bytes2HLExchangeInt(picTypeConfigInfo.getPicWidth());
                int iBytes2HLExchangeInt3 = Rgb.getInstance().bytes2HLExchangeInt(picTypeConfigInfo.getPicHeight());
                int iBytes2HLExchangeInt4 = Rgb.getInstance().bytes2HLExchangeInt(picTypeConfigInfo.getX());
                int iBytes2HLExchangeInt5 = Rgb.getInstance().bytes2HLExchangeInt(picTypeConfigInfo.getY());
                int iBytes4HLExchangeInt = Rgb.getInstance().bytes4HLExchangeInt(picTypeConfigInfo.getPicStartAddress());
                int i7 = iBytes2HLExchangeInt2 * iBytes2HLExchangeInt3;
                int i8 = i7 * 2;
                byte[] bArr3 = new byte[i8];
                if (iBytes2HLExchangeInt == 4) {
                    System.arraycopy(bArr, (i7 * 6) + iBytes4HLExchangeInt, bArr3, 0, i8);
                    arrayList.add(new BitmapInfo(iBytes2HLExchangeInt, Rgb.getInstance().data565ToBitmap8888(bArr3, iBytes2HLExchangeInt2, iBytes2HLExchangeInt3), iBytes2HLExchangeInt2, iBytes2HLExchangeInt3, iBytes2HLExchangeInt4, iBytes2HLExchangeInt5));
                    System.arraycopy(bArr, iBytes4HLExchangeInt + (i7 * 22), bArr3, 0, i8);
                    bitmapInfo = new BitmapInfo(iBytes2HLExchangeInt, Rgb.getInstance().data565ToBitmap8888(bArr3, iBytes2HLExchangeInt2, iBytes2HLExchangeInt3), iBytes2HLExchangeInt2, iBytes2HLExchangeInt3, iBytes2HLExchangeInt4 + iBytes2HLExchangeInt2, iBytes2HLExchangeInt5);
                } else if (iBytes2HLExchangeInt == 5) {
                    System.arraycopy(bArr, (i7 * 12) + iBytes4HLExchangeInt, bArr3, 0, i8);
                    arrayList.add(new BitmapInfo(iBytes2HLExchangeInt, Rgb.getInstance().data565ToBitmap8888(bArr3, iBytes2HLExchangeInt2, iBytes2HLExchangeInt3), iBytes2HLExchangeInt2, iBytes2HLExchangeInt3, iBytes2HLExchangeInt4, iBytes2HLExchangeInt5));
                    System.arraycopy(bArr, iBytes4HLExchangeInt + (i7 * 10), bArr3, 0, i8);
                    bitmapInfo = new BitmapInfo(iBytes2HLExchangeInt, Rgb.getInstance().data565ToBitmap8888(bArr3, iBytes2HLExchangeInt2, iBytes2HLExchangeInt3), iBytes2HLExchangeInt2, iBytes2HLExchangeInt3, iBytes2HLExchangeInt4 + iBytes2HLExchangeInt2, iBytes2HLExchangeInt5);
                } else if (iBytes2HLExchangeInt == 8) {
                    System.arraycopy(bArr, iBytes4HLExchangeInt + (i7 * 8), bArr3, 0, i8);
                    bitmapInfo = new BitmapInfo(iBytes2HLExchangeInt, Rgb.getInstance().data565ToBitmap8888(bArr3, iBytes2HLExchangeInt2, iBytes2HLExchangeInt3), iBytes2HLExchangeInt2, iBytes2HLExchangeInt3, iBytes2HLExchangeInt4, iBytes2HLExchangeInt5);
                } else if (iBytes2HLExchangeInt == 11) {
                    System.arraycopy(bArr, (i7 * 6) + iBytes4HLExchangeInt, bArr3, 0, i8);
                    arrayList.add(new BitmapInfo(iBytes2HLExchangeInt, Rgb.getInstance().data565ToBitmap8888(bArr3, iBytes2HLExchangeInt2, iBytes2HLExchangeInt3), iBytes2HLExchangeInt2, iBytes2HLExchangeInt3, iBytes2HLExchangeInt4, iBytes2HLExchangeInt5));
                    System.arraycopy(bArr, iBytes4HLExchangeInt + (i7 * 10), bArr3, 0, i8);
                    bitmapInfo = new BitmapInfo(iBytes2HLExchangeInt, Rgb.getInstance().data565ToBitmap8888(bArr3, iBytes2HLExchangeInt2, iBytes2HLExchangeInt3), iBytes2HLExchangeInt2, iBytes2HLExchangeInt3, iBytes2HLExchangeInt4 + iBytes2HLExchangeInt2, iBytes2HLExchangeInt5);
                } else if (iBytes2HLExchangeInt == 13) {
                    System.arraycopy(bArr, (i7 * 10) + iBytes4HLExchangeInt, bArr3, 0, i8);
                    arrayList.add(new BitmapInfo(iBytes2HLExchangeInt, Rgb.getInstance().data565ToBitmap8888(bArr3, iBytes2HLExchangeInt2, iBytes2HLExchangeInt3), iBytes2HLExchangeInt2, iBytes2HLExchangeInt3, iBytes2HLExchangeInt4, iBytes2HLExchangeInt5));
                    System.arraycopy(bArr, iBytes4HLExchangeInt + (i7 * 20), bArr3, 0, i8);
                    bitmapInfo = new BitmapInfo(iBytes2HLExchangeInt, Rgb.getInstance().data565ToBitmap8888(bArr3, iBytes2HLExchangeInt2, iBytes2HLExchangeInt3), iBytes2HLExchangeInt2, iBytes2HLExchangeInt3, iBytes2HLExchangeInt4 + iBytes2HLExchangeInt2, iBytes2HLExchangeInt5);
                } else if (iBytes2HLExchangeInt == 25) {
                    System.arraycopy(bArr, (i7 * 10) + iBytes4HLExchangeInt, bArr3, 0, i8);
                    arrayList.add(new BitmapInfo(iBytes2HLExchangeInt, Rgb.getInstance().data565ToBitmap8888(bArr3, iBytes2HLExchangeInt2, iBytes2HLExchangeInt3), iBytes2HLExchangeInt2, iBytes2HLExchangeInt3, iBytes2HLExchangeInt4, iBytes2HLExchangeInt5));
                    System.arraycopy(bArr, (i7 * 12) + iBytes4HLExchangeInt, bArr3, 0, i8);
                    arrayList.add(new BitmapInfo(iBytes2HLExchangeInt, Rgb.getInstance().data565ToBitmap8888(bArr3, iBytes2HLExchangeInt2, iBytes2HLExchangeInt3), iBytes2HLExchangeInt2, iBytes2HLExchangeInt3, iBytes2HLExchangeInt4 + iBytes2HLExchangeInt2, iBytes2HLExchangeInt5));
                    int i9 = iBytes4HLExchangeInt + (i7 * 6);
                    System.arraycopy(bArr, i9, bArr3, 0, i8);
                    arrayList.add(new BitmapInfo(iBytes2HLExchangeInt, Rgb.getInstance().data565ToBitmap8888(bArr3, iBytes2HLExchangeInt2, iBytes2HLExchangeInt3), iBytes2HLExchangeInt2, iBytes2HLExchangeInt3, iBytes2HLExchangeInt4 + (iBytes2HLExchangeInt2 * 2), iBytes2HLExchangeInt5));
                    System.arraycopy(bArr, i9, bArr3, 0, i8);
                    arrayList.add(new BitmapInfo(iBytes2HLExchangeInt, Rgb.getInstance().data565ToBitmap8888(bArr3, iBytes2HLExchangeInt2, iBytes2HLExchangeInt3), iBytes2HLExchangeInt2, iBytes2HLExchangeInt3, iBytes2HLExchangeInt4 + (iBytes2HLExchangeInt2 * 3), iBytes2HLExchangeInt5));
                    System.arraycopy(bArr, i9, bArr3, 0, i8);
                    bitmapInfo = new BitmapInfo(iBytes2HLExchangeInt, Rgb.getInstance().data565ToBitmap8888(bArr3, iBytes2HLExchangeInt2, iBytes2HLExchangeInt3), iBytes2HLExchangeInt2, iBytes2HLExchangeInt3, iBytes2HLExchangeInt4 + (iBytes2HLExchangeInt2 * 4), iBytes2HLExchangeInt5);
                } else if (iBytes2HLExchangeInt != 32) {
                    switch (iBytes2HLExchangeInt) {
                        case 15:
                            System.arraycopy(bArr, iBytes4HLExchangeInt + (i7 * 20), bArr3, 0, i8);
                            bitmapInfo = new BitmapInfo(iBytes2HLExchangeInt, Rgb.getInstance().data565ToBitmap8888(bArr3, iBytes2HLExchangeInt2, iBytes2HLExchangeInt3), iBytes2HLExchangeInt2, iBytes2HLExchangeInt3, iBytes2HLExchangeInt4, iBytes2HLExchangeInt5);
                            break;
                        case 16:
                            System.arraycopy(bArr, iBytes4HLExchangeInt + (i7 * 4), bArr3, 0, i8);
                            bitmapInfo = new BitmapInfo(iBytes2HLExchangeInt, Rgb.getInstance().data565ToBitmap8888(bArr3, iBytes2HLExchangeInt2, iBytes2HLExchangeInt3), iBytes2HLExchangeInt2, iBytes2HLExchangeInt3, iBytes2HLExchangeInt4, iBytes2HLExchangeInt5);
                            break;
                        case 17:
                            System.arraycopy(bArr, iBytes4HLExchangeInt, bArr3, 0, i8);
                            bitmapInfo = new BitmapInfo(iBytes2HLExchangeInt, Rgb.getInstance().dataToBitmap(bArr3, iBytes2HLExchangeInt2, iBytes2HLExchangeInt3), iBytes2HLExchangeInt2, iBytes2HLExchangeInt3, iBytes2HLExchangeInt4, iBytes2HLExchangeInt5);
                            break;
                        case 18:
                            System.arraycopy(bArr, iBytes4HLExchangeInt, bArr3, 0, i8);
                            bitmapInfo = new BitmapInfo(iBytes2HLExchangeInt, Rgb.getInstance().data565ToBitmap8888(bArr3, iBytes2HLExchangeInt2, iBytes2HLExchangeInt3), iBytes2HLExchangeInt2, iBytes2HLExchangeInt3, iBytes2HLExchangeInt4, iBytes2HLExchangeInt5);
                            break;
                        default:
                            switch (iBytes2HLExchangeInt) {
                                case 35:
                                    System.arraycopy(bArr, iBytes4HLExchangeInt + (i7 * 8), bArr3, 0, i8);
                                    bitmapInfo = new BitmapInfo(iBytes2HLExchangeInt, Rgb.getInstance().data565ToBitmap8888(bArr3, iBytes2HLExchangeInt2, iBytes2HLExchangeInt3), iBytes2HLExchangeInt2, iBytes2HLExchangeInt3, iBytes2HLExchangeInt4, iBytes2HLExchangeInt5);
                                    break;
                                case 36:
                                    System.arraycopy(bArr, iBytes4HLExchangeInt + (i7 * 6), bArr3, 0, i8);
                                    bitmapInfo = new BitmapInfo(iBytes2HLExchangeInt, Rgb.getInstance().data565ToBitmap8888(bArr3, iBytes2HLExchangeInt2, iBytes2HLExchangeInt3), iBytes2HLExchangeInt2, iBytes2HLExchangeInt3, iBytes2HLExchangeInt4, iBytes2HLExchangeInt5);
                                    break;
                                case 37:
                                    System.arraycopy(bArr, iBytes4HLExchangeInt + (i7 * 10), bArr3, 0, i8);
                                    bitmapInfo = new BitmapInfo(iBytes2HLExchangeInt, Rgb.getInstance().data565ToBitmap8888(bArr3, iBytes2HLExchangeInt2, iBytes2HLExchangeInt3), iBytes2HLExchangeInt2, iBytes2HLExchangeInt3, iBytes2HLExchangeInt4, iBytes2HLExchangeInt5);
                                    break;
                                case 38:
                                    System.arraycopy(bArr, iBytes4HLExchangeInt + (i7 * 10), bArr3, 0, i8);
                                    bitmapInfo = new BitmapInfo(iBytes2HLExchangeInt, Rgb.getInstance().data565ToBitmap8888(bArr3, iBytes2HLExchangeInt2, iBytes2HLExchangeInt3), iBytes2HLExchangeInt2, iBytes2HLExchangeInt3, iBytes2HLExchangeInt4, iBytes2HLExchangeInt5);
                                    break;
                                case 39:
                                    System.arraycopy(bArr, iBytes4HLExchangeInt + (i7 * 12), bArr3, 0, i8);
                                    bitmapInfo = new BitmapInfo(iBytes2HLExchangeInt, Rgb.getInstance().data565ToBitmap8888(bArr3, iBytes2HLExchangeInt2, iBytes2HLExchangeInt3), iBytes2HLExchangeInt2, iBytes2HLExchangeInt3, iBytes2HLExchangeInt4, iBytes2HLExchangeInt5);
                                    break;
                                case 40:
                                    System.arraycopy(bArr, iBytes4HLExchangeInt + (i7 * 6), bArr3, 0, i8);
                                    bitmapInfo = new BitmapInfo(iBytes2HLExchangeInt, Rgb.getInstance().data565ToBitmap8888(bArr3, iBytes2HLExchangeInt2, iBytes2HLExchangeInt3), iBytes2HLExchangeInt2, iBytes2HLExchangeInt3, iBytes2HLExchangeInt4, iBytes2HLExchangeInt5);
                                    break;
                                case 41:
                                    System.arraycopy(bArr, iBytes4HLExchangeInt + (i7 * 6), bArr3, 0, i8);
                                    bitmapInfo = new BitmapInfo(iBytes2HLExchangeInt, Rgb.getInstance().data565ToBitmap8888(bArr3, iBytes2HLExchangeInt2, iBytes2HLExchangeInt3), iBytes2HLExchangeInt2, iBytes2HLExchangeInt3, iBytes2HLExchangeInt4, iBytes2HLExchangeInt5);
                                    break;
                                case 42:
                                    System.arraycopy(bArr, iBytes4HLExchangeInt + (i7 * 6), bArr3, 0, i8);
                                    bitmapInfo = new BitmapInfo(iBytes2HLExchangeInt, Rgb.getInstance().data565ToBitmap8888(bArr3, iBytes2HLExchangeInt2, iBytes2HLExchangeInt3), iBytes2HLExchangeInt2, iBytes2HLExchangeInt3, iBytes2HLExchangeInt4, iBytes2HLExchangeInt5);
                                    break;
                                default:
                                    switch (iBytes2HLExchangeInt) {
                                        case 57:
                                            System.arraycopy(bArr, iBytes4HLExchangeInt + (i7 * 6), bArr3, 0, i8);
                                            bitmapInfo = new BitmapInfo(iBytes2HLExchangeInt, Rgb.getInstance().data565ToBitmap8888(bArr3, iBytes2HLExchangeInt2, iBytes2HLExchangeInt3), iBytes2HLExchangeInt2, iBytes2HLExchangeInt3, iBytes2HLExchangeInt4, iBytes2HLExchangeInt5);
                                            break;
                                        case 58:
                                            System.arraycopy(bArr, iBytes4HLExchangeInt + (i7 * 22), bArr3, 0, i8);
                                            bitmapInfo = new BitmapInfo(iBytes2HLExchangeInt, Rgb.getInstance().data565ToBitmap8888(bArr3, iBytes2HLExchangeInt2, iBytes2HLExchangeInt3), iBytes2HLExchangeInt2, iBytes2HLExchangeInt3, iBytes2HLExchangeInt4, iBytes2HLExchangeInt5);
                                            break;
                                        case 59:
                                            System.arraycopy(bArr, iBytes4HLExchangeInt + (i7 * 12), bArr3, 0, i8);
                                            bitmapInfo = new BitmapInfo(iBytes2HLExchangeInt, Rgb.getInstance().data565ToBitmap8888(bArr3, iBytes2HLExchangeInt2, iBytes2HLExchangeInt3), iBytes2HLExchangeInt2, iBytes2HLExchangeInt3, iBytes2HLExchangeInt4, iBytes2HLExchangeInt5);
                                            break;
                                        case 60:
                                            System.arraycopy(bArr, iBytes4HLExchangeInt + (i7 * 10), bArr3, 0, i8);
                                            bitmapInfo = new BitmapInfo(iBytes2HLExchangeInt, Rgb.getInstance().data565ToBitmap8888(bArr3, iBytes2HLExchangeInt2, iBytes2HLExchangeInt3), iBytes2HLExchangeInt2, iBytes2HLExchangeInt3, iBytes2HLExchangeInt4, iBytes2HLExchangeInt5);
                                            break;
                                        default:
                                            switch (iBytes2HLExchangeInt) {
                                                case 63:
                                                    System.arraycopy(bArr, iBytes4HLExchangeInt + (i7 * 6), bArr3, 0, i8);
                                                    bitmapInfo = new BitmapInfo(iBytes2HLExchangeInt, Rgb.getInstance().data565ToBitmap8888(bArr3, iBytes2HLExchangeInt2, iBytes2HLExchangeInt3), iBytes2HLExchangeInt2, iBytes2HLExchangeInt3, iBytes2HLExchangeInt4, iBytes2HLExchangeInt5);
                                                    break;
                                                case 64:
                                                    System.arraycopy(bArr, iBytes4HLExchangeInt + (i7 * 10), bArr3, 0, i8);
                                                    bitmapInfo = new BitmapInfo(iBytes2HLExchangeInt, Rgb.getInstance().data565ToBitmap8888(bArr3, iBytes2HLExchangeInt2, iBytes2HLExchangeInt3), iBytes2HLExchangeInt2, iBytes2HLExchangeInt3, iBytes2HLExchangeInt4, iBytes2HLExchangeInt5);
                                                    break;
                                                case 65:
                                                    System.arraycopy(bArr, iBytes4HLExchangeInt + (i7 * 10), bArr3, 0, i8);
                                                    bitmapInfo = new BitmapInfo(iBytes2HLExchangeInt, Rgb.getInstance().data565ToBitmap8888(bArr3, iBytes2HLExchangeInt2, iBytes2HLExchangeInt3), iBytes2HLExchangeInt2, iBytes2HLExchangeInt3, iBytes2HLExchangeInt4, iBytes2HLExchangeInt5);
                                                    break;
                                                case 66:
                                                    System.arraycopy(bArr, iBytes4HLExchangeInt + (i7 * 20), bArr3, 0, i8);
                                                    bitmapInfo = new BitmapInfo(iBytes2HLExchangeInt, Rgb.getInstance().data565ToBitmap8888(bArr3, iBytes2HLExchangeInt2, iBytes2HLExchangeInt3), iBytes2HLExchangeInt2, iBytes2HLExchangeInt3, iBytes2HLExchangeInt4, iBytes2HLExchangeInt5);
                                                    break;
                                                default:
                                                    System.arraycopy(bArr, iBytes4HLExchangeInt, bArr3, 0, i8);
                                                    bitmapInfo = new BitmapInfo(iBytes2HLExchangeInt, Rgb.getInstance().data565ToBitmap8888(bArr3, iBytes2HLExchangeInt2, iBytes2HLExchangeInt3), iBytes2HLExchangeInt2, iBytes2HLExchangeInt3, iBytes2HLExchangeInt4, iBytes2HLExchangeInt5);
                                                    break;
                                            }
                                    }
                            }
                    }
                } else {
                    System.arraycopy(bArr, iBytes4HLExchangeInt + (i7 * 16), bArr3, 0, i8);
                    bitmapInfo = new BitmapInfo(iBytes2HLExchangeInt, Rgb.getInstance().data565ToBitmap8888(bArr3, iBytes2HLExchangeInt2, iBytes2HLExchangeInt3), iBytes2HLExchangeInt2, iBytes2HLExchangeInt3, iBytes2HLExchangeInt4, iBytes2HLExchangeInt5);
                }
                arrayList.add(bitmapInfo);
            }
            i5++;
            i2 = 24;
        }
        return arrayList;
    }

    public final void utendo(Bitmap bitmap) {
        this.mDialBackgroundBitmap = bitmap;
    }

    public final void utendo(boolean z2) {
        this.isMyFontColorChange = z2;
        Rgb.LogD("isMyFontColorChange =" + this.isMyFontColorChange);
    }

    public final void utendo(PreviewScaleInfo previewScaleInfo) {
        this.mPreviewScaleInfo = previewScaleInfo;
    }

    public final File[] utendo(File[] fileArr) {
        Collections.sort(Arrays.asList(fileArr), new utendo());
        return fileArr;
    }

    public final String[] utendo(String[] strArr) {
        Collections.sort(Arrays.asList(strArr), new utenif());
        return strArr;
    }
}
