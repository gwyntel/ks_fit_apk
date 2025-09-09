package com.yc.utesdk.utils.open;

import com.yc.utesdk.log.LogUtils;

/* loaded from: classes4.dex */
public class PushMessageUtil {
    public static final int IS_DISPLAY_AMAZON = 16384;
    public static final int IS_DISPLAY_BOOKMYSHOW = 524288;
    public static final int IS_DISPLAY_CALENDAR = 1048576;
    public static final int IS_DISPLAY_DAILYHUNT = 131072;
    public static final int IS_DISPLAY_DUNZO = 32;
    public static final int IS_DISPLAY_FACEBOOK = 32;
    public static final int IS_DISPLAY_FACEBOOK_MESSENGER = 256;
    public static final int IS_DISPLAY_FLICKR = 1048576;
    public static final int IS_DISPLAY_FLIPKART = 8192;
    public static final int IS_DISPLAY_GAANA = 64;
    public static final int IS_DISPLAY_GMAIL = 524288;
    public static final int IS_DISPLAY_GOOGLECHAT = 256;
    public static final int IS_DISPLAY_GOOGLE_DRIVE = 128;
    public static final int IS_DISPLAY_GOOGLE_PLUS = 262144;
    public static final int IS_DISPLAY_GPAY = 512;
    public static final int IS_DISPLAY_HANGOUTS = 2048;
    public static final int IS_DISPLAY_HOTSTAR = 2048;
    public static final int IS_DISPLAY_IMO = 16;
    public static final int IS_DISPLAY_INSHORTS = 262144;
    public static final int IS_DISPLAY_INSTAGRAM = 8192;
    public static final int IS_DISPLAY_JIO_TV = 2097152;
    public static final int IS_DISPLAY_KAKAOTALK = 32768;
    public static final int IS_DISPLAY_LINE = 512;
    public static final int IS_DISPLAY_LINKED_IN = 4096;
    public static final int IS_DISPLAY_MAKE_MY_TRIP = 4194304;
    public static final int IS_DISPLAY_MICROSOFT_TEAMS = 32;
    public static final int IS_DISPLAY_MYNTRA = 32768;
    public static final int IS_DISPLAY_NETFLIX = 8388608;
    public static final int IS_DISPLAY_NOISEAPP = 65536;
    public static final int IS_DISPLAY_OLA = 1;
    public static final int IS_DISPLAY_OTHER = 16;
    public static final int IS_DISPLAY_OUTLOOK = 64;
    public static final int IS_DISPLAY_PAYTM = 4;
    public static final int IS_DISPLAY_PHONE = 8;
    public static final int IS_DISPLAY_PHONEPE = 1024;
    public static final int IS_DISPLAY_PINTEREST = 4194304;
    public static final int IS_DISPLAY_PRIMEVIDEO = 4096;
    public static final int IS_DISPLAY_QQ = 2;
    public static final int IS_DISPLAY_REFLEX_APP = 2;
    public static final int IS_DISPLAY_SKYPE = 1024;
    public static final int IS_DISPLAY_SLACK = 4096;
    public static final int IS_DISPLAY_SMS = 1;
    public static final int IS_DISPLAY_SNAPCHAT = 131072;
    public static final int IS_DISPLAY_SPOTIFY = 8192;
    public static final int IS_DISPLAY_SWIGGY = 128;
    public static final int IS_DISPLAY_TELEGRAM = 1;
    public static final int IS_DISPLAY_TITAN_SMART_WORLD = 2048;
    public static final int IS_DISPLAY_TRUECALLER = 2;
    public static final int IS_DISPLAY_TUMBLR = 2097152;
    public static final int IS_DISPLAY_TWITTER = 64;
    public static final int IS_DISPLAY_UBER = 4;
    public static final int IS_DISPLAY_VIBER = 16384;
    public static final int IS_DISPLAY_VKONTAKTE = 65536;
    public static final int IS_DISPLAY_WECHAT = 4;
    public static final int IS_DISPLAY_WHATSAPP = 128;
    public static final int IS_DISPLAY_WHATSAPP_BUSINESS = 16;
    public static final int IS_DISPLAY_WYNK_MUSIC = 512;
    public static final int IS_DISPLAY_YAHOO = 1024;
    public static final int IS_DISPLAY_YOUTUBE = 8388608;
    public static final int IS_DISPLAY_YT_MUSIC = 8;
    public static final int IS_DISPLAY_ZALO = 8;
    public static final int IS_DISPLAY_ZOMATO = 256;

    public static boolean isPushMessageDisplay1(int i2) {
        int pushMessageDisplay1 = SPUtil.getInstance().getPushMessageDisplay1();
        boolean z2 = (pushMessageDisplay1 & i2) == i2;
        LogUtils.d("isPushMessageDisplay1", "isSupport =" + z2 + ",functionType =" + i2 + ",function=" + pushMessageDisplay1);
        return z2;
    }

    public static boolean isPushMessageDisplay2(int i2) {
        int pushMessageDisplay2 = SPUtil.getInstance().getPushMessageDisplay2();
        boolean z2 = (pushMessageDisplay2 & i2) == i2;
        LogUtils.d("isPushMessageDisplay2", "isSupport =" + z2 + ",functionType =" + i2 + ",function=" + pushMessageDisplay2);
        return z2;
    }

    public static boolean isPushMessageDisplay3(int i2) {
        int pushMessageDisplay3 = SPUtil.getInstance().getPushMessageDisplay3();
        boolean z2 = (pushMessageDisplay3 & i2) == i2;
        LogUtils.d("isPushMessageDisplay3", "isSupport =" + z2 + ",functionType =" + i2 + ",function=" + pushMessageDisplay3);
        return z2;
    }
}
