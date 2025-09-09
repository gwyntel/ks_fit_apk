package com.luck.picture.lib.config;

import android.text.TextUtils;

/* loaded from: classes4.dex */
public final class PictureMimeType {
    public static final String AMR = ".amr";
    public static final String AMR_Q = "audio/amr";
    public static final String AVI = ".avi";
    public static final String AVI_Q = "video/avi";
    public static final String BMP = ".bmp";
    public static final String CAMERA = "Camera";
    public static final String DCIM = "DCIM/Camera";
    public static final String GIF = ".gif";
    public static final String JPEG = ".jpeg";
    public static final String JPEG_Q = "image/jpeg";
    public static final String JPG = ".jpg";
    private static final String MIME_TYPE_3GP = "video/3gp";
    public static final String MIME_TYPE_AUDIO = "audio/mpeg";
    public static final String MIME_TYPE_AUDIO_AMR = "audio/amr";
    private static final String MIME_TYPE_AVI = "video/avi";
    private static final String MIME_TYPE_BMP = "image/bmp";
    private static final String MIME_TYPE_GIF = "image/gif";
    private static final String MIME_TYPE_HEIC = "image/heic";
    public static final String MIME_TYPE_IMAGE = "image/jpeg";
    public static final String MIME_TYPE_JPEG = "image/jpeg";
    private static final String MIME_TYPE_JPG = "image/jpg";
    private static final String MIME_TYPE_MP4 = "video/mp4";
    private static final String MIME_TYPE_MPEG = "video/mpeg";
    private static final String MIME_TYPE_PNG = "image/png";
    public static final String MIME_TYPE_PREFIX_AUDIO = "audio";
    public static final String MIME_TYPE_PREFIX_IMAGE = "image";
    public static final String MIME_TYPE_PREFIX_VIDEO = "video";
    public static final String MIME_TYPE_VIDEO = "video/mp4";
    private static final String MIME_TYPE_WAP_BMP = "image/vnd.wap.wbmp";
    private static final String MIME_TYPE_WEBP = "image/webp";
    private static final String MIME_TYPE_XMS_BMP = "image/x-ms-bmp";
    public static final String MP3 = ".mp3";
    public static final String MP3_Q = "audio/mpeg";
    public static final String MP4 = ".mp4";
    public static final String MP4_Q = "video/mp4";
    public static final String PNG = ".png";
    public static final String PNG_Q = "image/png";
    public static final String WAV = ".wav";
    public static final String WAV_Q = "audio/x-wav";
    public static final String WEBP = ".webp";

    public static String getLastSourceSuffix(String str) {
        try {
            return str.substring(str.lastIndexOf("/")).replace("/", ".");
        } catch (Exception e2) {
            e2.printStackTrace();
            return JPG;
        }
    }

    public static int getMimeType(String str) {
        if (TextUtils.isEmpty(str)) {
            return 1;
        }
        if (str.startsWith("video")) {
            return 2;
        }
        return str.startsWith("audio") ? 3 : 1;
    }

    public static String getUrlToFileName(String str) {
        try {
            int iLastIndexOf = str.lastIndexOf("/");
            return iLastIndexOf != -1 ? str.substring(iLastIndexOf + 1) : "";
        } catch (Exception e2) {
            e2.printStackTrace();
            return "";
        }
    }

    public static boolean isContent(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return str.startsWith("content://");
    }

    public static boolean isHasAudio(String str) {
        return str != null && str.startsWith("audio");
    }

    public static boolean isHasBmp(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return str.startsWith(ofBMP()) || str.startsWith(ofXmsBMP()) || str.startsWith(ofWapBMP());
    }

    public static boolean isHasGif(String str) {
        return str != null && (str.equals(MIME_TYPE_GIF) || str.equals("image/GIF"));
    }

    public static boolean isHasHeic(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return str.startsWith(ofHeic());
    }

    public static boolean isHasHttp(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return str.startsWith("http") || str.startsWith("https");
    }

    public static boolean isHasImage(String str) {
        return str != null && str.startsWith("image");
    }

    public static boolean isHasVideo(String str) {
        return str != null && str.startsWith("video");
    }

    public static boolean isHasWebp(String str) {
        return str != null && str.equalsIgnoreCase("image/webp");
    }

    public static boolean isJPEG(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return str.startsWith("image/jpeg") || str.startsWith(MIME_TYPE_JPG);
    }

    public static boolean isJPG(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return str.startsWith(MIME_TYPE_JPG);
    }

    public static boolean isMimeTypeSame(String str, String str2) {
        return TextUtils.isEmpty(str) || getMimeType(str) == getMimeType(str2);
    }

    public static boolean isUrlHasAudio(String str) {
        return str.toLowerCase().endsWith(AMR) || str.toLowerCase().endsWith(MP3);
    }

    public static boolean isUrlHasGif(String str) {
        return str.toLowerCase().endsWith(GIF);
    }

    public static boolean isUrlHasImage(String str) {
        return str.toLowerCase().endsWith(JPG) || str.toLowerCase().endsWith(JPEG) || str.toLowerCase().endsWith(PNG) || str.toLowerCase().endsWith(".heic");
    }

    public static boolean isUrlHasVideo(String str) {
        return str.toLowerCase().endsWith(MP4);
    }

    public static boolean isUrlHasWebp(String str) {
        return str.toLowerCase().endsWith(WEBP);
    }

    public static String of3GP() {
        return MIME_TYPE_3GP;
    }

    public static String ofAVI() {
        return "video/avi";
    }

    public static String ofBMP() {
        return "image/bmp";
    }

    public static String ofGIF() {
        return MIME_TYPE_GIF;
    }

    public static String ofHeic() {
        return "image/heic";
    }

    public static String ofJPEG() {
        return "image/jpeg";
    }

    public static String ofMP4() {
        return "video/mp4";
    }

    public static String ofMPEG() {
        return "video/mpeg";
    }

    public static String ofPNG() {
        return "image/png";
    }

    public static String ofWEBP() {
        return "image/webp";
    }

    public static String ofWapBMP() {
        return MIME_TYPE_WAP_BMP;
    }

    public static String ofXmsBMP() {
        return MIME_TYPE_XMS_BMP;
    }
}
