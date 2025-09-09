package com.yalantis.ucrop.model;

import android.graphics.Bitmap;
import android.net.Uri;

/* loaded from: classes4.dex */
public class CropParameters {
    private Bitmap.CompressFormat mCompressFormat;
    private int mCompressQuality;
    private Uri mContentImageInputUri;
    private Uri mContentImageOutputUri;
    private ExifInfo mExifInfo;
    private String mImageInputPath;
    private String mImageOutputPath;
    private int mMaxResultImageSizeX;
    private int mMaxResultImageSizeY;

    public CropParameters(int i2, int i3, Bitmap.CompressFormat compressFormat, int i4, String str, String str2, ExifInfo exifInfo) {
        this.mMaxResultImageSizeX = i2;
        this.mMaxResultImageSizeY = i3;
        this.mCompressFormat = compressFormat;
        this.mCompressQuality = i4;
        this.mImageInputPath = str;
        this.mImageOutputPath = str2;
        this.mExifInfo = exifInfo;
    }

    public Bitmap.CompressFormat getCompressFormat() {
        return this.mCompressFormat;
    }

    public int getCompressQuality() {
        return this.mCompressQuality;
    }

    public Uri getContentImageInputUri() {
        return this.mContentImageInputUri;
    }

    public Uri getContentImageOutputUri() {
        return this.mContentImageOutputUri;
    }

    public ExifInfo getExifInfo() {
        return this.mExifInfo;
    }

    public String getImageInputPath() {
        return this.mImageInputPath;
    }

    public String getImageOutputPath() {
        return this.mImageOutputPath;
    }

    public int getMaxResultImageSizeX() {
        return this.mMaxResultImageSizeX;
    }

    public int getMaxResultImageSizeY() {
        return this.mMaxResultImageSizeY;
    }

    public void setContentImageInputUri(Uri uri) {
        this.mContentImageInputUri = uri;
    }

    public void setContentImageOutputUri(Uri uri) {
        this.mContentImageOutputUri = uri;
    }
}
