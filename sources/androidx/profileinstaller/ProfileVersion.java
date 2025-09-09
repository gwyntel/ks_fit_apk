package androidx.profileinstaller;

import androidx.annotation.RestrictTo;
import com.alibaba.ailabs.iot.aisbase.Constants;
import java.util.Arrays;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes2.dex */
public class ProfileVersion {
    public static final int MAX_SUPPORTED_SDK = 34;
    public static final int MIN_SUPPORTED_SDK = 24;

    /* renamed from: a, reason: collision with root package name */
    static final byte[] f5761a = {Constants.CMD_TYPE.CMD_AUDIO_UPSTREAM, Constants.CMD_TYPE.CMD_STATUS_REPORT, Constants.CMD_TYPE.CMD_SIGNATURE_RES, 0};

    /* renamed from: b, reason: collision with root package name */
    static final byte[] f5762b = {Constants.CMD_TYPE.CMD_AUDIO_UPSTREAM, Constants.CMD_TYPE.CMD_STATUS_REPORT, Constants.CMD_TYPE.CMD_AUDIO_UPSTREAM, 0};

    /* renamed from: c, reason: collision with root package name */
    static final byte[] f5763c = {Constants.CMD_TYPE.CMD_AUDIO_UPSTREAM, Constants.CMD_TYPE.CMD_AUDIO_UPSTREAM, 57, 0};

    /* renamed from: d, reason: collision with root package name */
    static final byte[] f5764d = {Constants.CMD_TYPE.CMD_AUDIO_UPSTREAM, Constants.CMD_TYPE.CMD_AUDIO_UPSTREAM, Constants.CMD_TYPE.CMD_SIGNATURE_RES, 0};

    /* renamed from: e, reason: collision with root package name */
    static final byte[] f5765e = {Constants.CMD_TYPE.CMD_AUDIO_UPSTREAM, Constants.CMD_TYPE.CMD_AUDIO_UPSTREAM, Constants.CMD_TYPE.CMD_STATUS_REPORT, 0};

    /* renamed from: f, reason: collision with root package name */
    static final byte[] f5766f = {Constants.CMD_TYPE.CMD_AUDIO_UPSTREAM, Constants.CMD_TYPE.CMD_AUDIO_UPSTREAM, Constants.CMD_TYPE.CMD_STATUS_REPORT, 0};

    /* renamed from: g, reason: collision with root package name */
    static final byte[] f5767g = {Constants.CMD_TYPE.CMD_AUDIO_UPSTREAM, Constants.CMD_TYPE.CMD_AUDIO_UPSTREAM, Constants.CMD_TYPE.CMD_SEND_DEVICE_INFO, 0};

    private ProfileVersion() {
    }

    static String a(byte[] bArr) {
        return (Arrays.equals(bArr, f5765e) || Arrays.equals(bArr, f5764d)) ? ":" : "!";
    }
}
