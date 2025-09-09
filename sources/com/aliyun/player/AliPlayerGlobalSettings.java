package com.aliyun.player;

import android.content.Context;
import com.aliyun.player.IPlayer;
import com.aliyun.utils.DeviceInfoUtils;
import com.aliyun.utils.f;
import com.cicada.player.utils.NativeUsed;
import java.nio.ByteBuffer;

/* loaded from: classes3.dex */
public class AliPlayerGlobalSettings {
    public static final int ALLOW_BOUNDS_CHANGE_ANIMATION = 10;
    public static final int ALLOW_RTS_DEGRADE = 6;
    public static final int CodecType_H265 = 0;
    public static final int DECODER_POOL_CAPACITY_CROSS_INSTANCE = 13;
    public static final int DISABLE_CAPTURE_SCALE = 8;
    public static final int ENABLE_ANDROID_DECODE_REUSE = 4;
    public static final int ENABLE_DECODER_FAST_FIRST_FRAME = 7;
    public static final int ENABLE_DECODER_REUSE_CROSS_INSTANCE = 12;
    public static final int ENABLE_H2_MULTIPLEX = 2;
    public static final int MAX_ERROR_FRAMES_HARDWARE_DECODE = 15;
    public static final int NOT_PAUSE_WHEN_PREPARING = 5;
    public static final int RENDER_IGNORE_DAR_SCALE = 14;
    public static final int SCENE_LOADER = 1;
    public static final int SCENE_PLAYER = 0;
    public static final int SET_DNS_PRIORITY_LOCAL_FIRST = 1;
    public static final int SET_EXTRA_DATA = 3;
    public static final int SET_PRE_CONNECT_DOMAIN = 0;
    private static OnGetBackupUrlCallback sOnGetBackupUrlCallback;
    private static OnGetUrlHashCallback sOnGetUrlHashCallback;
    private static OnNetworkCallback sOnNetworkCallback;

    public interface OnGetBackupUrlCallback {

        public enum BizScene {
            kPLAYER,
            kLOADER
        }

        public enum CodecType {
            kH265
        }

        String getBackupUrlCallback(int i2, int i3, String str);
    }

    public interface OnGetUrlHashCallback {
        String getUrlHashCallback(String str);
    }

    public interface OnNetworkCallback {
        boolean onNetworkDataProcess(String str, ByteBuffer byteBuffer, long j2, ByteBuffer byteBuffer2);
    }

    public enum StreamType {
        STREAM_VOICE_CALL,
        STREAM_SYSTEM,
        STREAM_RING,
        STREAM_MUSIC,
        STREAM_ALARM,
        STREAM_NOTIFICATION
    }

    static {
        f.f();
        sOnGetUrlHashCallback = null;
        sOnGetBackupUrlCallback = null;
        sOnNetworkCallback = null;
    }

    public static void clearCaches() {
        if (f.b()) {
            nClearCaches();
        }
    }

    public static void disableCrashUpload(boolean z2) {
        if (f.b()) {
            nDisableCrashUpload(z2);
        }
    }

    public static void enableBufferToLocalCache(boolean z2) {
        if (f.b()) {
            nEnableBufferToLocalCache(z2);
        }
    }

    public static void enableEnhancedHttpDns(boolean z2) {
        if (f.b()) {
            nEnableEnhancedHttpDns(z2);
        }
    }

    @Deprecated
    public static void enableHttpDns(boolean z2) {
        if (f.b()) {
            nEnableHttpDns(z2);
        }
    }

    public static String enableLocalCache(boolean z2, Context context) {
        if (!f.b()) {
            return "";
        }
        if (context == null) {
            context = DeviceInfoUtils.getSDKContext();
        }
        if (context == null) {
            nEnableLocalCache(z2, 0, "");
            return "";
        }
        String string = context.getCacheDir().toString();
        nEnableLocalCache(z2, 0, string);
        return string;
    }

    public static void enableNetworkBalance(boolean z2) {
        if (f.b()) {
            nEnableNetworkBalance(z2);
        }
    }

    public static void forceAudioRendingFormat(boolean z2, String str, int i2, int i3) {
        if (f.b()) {
            nForceAudioRendingFormat(z2, str, i2, i3);
        }
    }

    public static void loadClass() {
    }

    private static native void nClearCaches();

    private static native void nDisableCrashUpload(boolean z2);

    private static native void nEnableBufferToLocalCache(boolean z2);

    private static native void nEnableEnhancedHttpDns(boolean z2);

    private static native void nEnableHttpDns(boolean z2);

    private static native void nEnableLocalCache(boolean z2, int i2, String str);

    private static native void nEnableNetworkBalance(boolean z2);

    private static native void nForceAudioRendingFormat(boolean z2, String str, int i2, int i3);

    @NativeUsed
    private static synchronized String nOnGetBackupUrlCallback(int i2, int i3, String str) {
        OnGetBackupUrlCallback onGetBackupUrlCallback = sOnGetBackupUrlCallback;
        if (onGetBackupUrlCallback == null) {
            return null;
        }
        return onGetBackupUrlCallback.getBackupUrlCallback(i2, i3, str);
    }

    @NativeUsed
    private static synchronized String nOnGetUrlHashCallback(String str) {
        OnGetUrlHashCallback onGetUrlHashCallback = sOnGetUrlHashCallback;
        if (onGetUrlHashCallback == null) {
            return null;
        }
        return onGetUrlHashCallback.getUrlHashCallback(str);
    }

    @NativeUsed
    private static synchronized boolean nOnNetworkDataProcessCallback(String str, ByteBuffer byteBuffer, long j2, ByteBuffer byteBuffer2) {
        OnNetworkCallback onNetworkCallback = sOnNetworkCallback;
        if (onNetworkCallback == null) {
            return false;
        }
        return onNetworkCallback.onNetworkDataProcess(str, byteBuffer, j2, byteBuffer2);
    }

    private static native void nSetAudioStreamType(int i2);

    private static native void nSetCacheFileClearConfig(long j2, long j3, long j4);

    private static native void nSetCacheUrlHashCallback(boolean z2);

    private static native void nSetDNSResolve(String str, String str2);

    private static native void nSetGetBackupUrlCallback(boolean z2);

    private static native void nSetIPResolveType(int i2);

    private static native void nSetNetworkCallback(boolean z2);

    private static native void nSetOption(int i2, int i3);

    private static native void nSetOption(int i2, String str);

    private static native void nSetUseHttp2(boolean z2);

    public static synchronized void setAdaptiveDecoderGetBackupURLCallback(OnGetBackupUrlCallback onGetBackupUrlCallback) {
        if (f.b()) {
            sOnGetBackupUrlCallback = onGetBackupUrlCallback;
            nSetGetBackupUrlCallback(onGetBackupUrlCallback != null);
        }
    }

    public static void setAudioStreamType(StreamType streamType) {
        if (f.b()) {
            nSetAudioStreamType(streamType.ordinal());
        }
    }

    public static void setCacheFileClearConfig(long j2, long j3, long j4) {
        if (f.b()) {
            nSetCacheFileClearConfig(j2, j3, j4);
        }
    }

    public static synchronized void setCacheUrlHashCallback(OnGetUrlHashCallback onGetUrlHashCallback) {
        if (f.b()) {
            sOnGetUrlHashCallback = onGetUrlHashCallback;
            nSetCacheUrlHashCallback(onGetUrlHashCallback != null);
        }
    }

    public static void setDNSResolve(String str, String str2) {
        if (f.b()) {
            nSetDNSResolve(str, str2);
        }
    }

    public static void setIPResolveType(IPlayer.IPResolveType iPResolveType) {
        if (f.b()) {
            nSetIPResolveType(iPResolveType.ordinal());
        }
    }

    public static synchronized void setNetworkCallback(OnNetworkCallback onNetworkCallback) {
        if (f.b()) {
            sOnNetworkCallback = onNetworkCallback;
            nSetNetworkCallback(onNetworkCallback != null);
        }
    }

    public static void setOption(int i2, int i3) {
        if (f.b()) {
            nSetOption(i2, i3);
            if (i2 == 12 && i3 == 1) {
                nSetOption(4, i3);
            }
        }
    }

    public static void setUseHttp2(boolean z2) {
        if (f.b()) {
            nSetUseHttp2(z2);
        }
    }

    public static void enableLocalCache(boolean z2, int i2, String str) {
        if (f.b()) {
            nEnableLocalCache(z2, i2, str);
        }
    }

    public static void setOption(int i2, String str) {
        if (!f.b() || i2 == 13 || i2 == 15) {
            return;
        }
        nSetOption(i2, str);
    }
}
