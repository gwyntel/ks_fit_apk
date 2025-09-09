package androidx.media3.exoplayer.rtsp;

import android.net.Uri;
import android.util.Base64;
import androidx.media3.common.ParserException;
import androidx.media3.common.util.Util;
import androidx.media3.exoplayer.rtsp.RtspMessageUtil;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* loaded from: classes.dex */
final class RtspAuthenticationInfo {
    private static final String ALGORITHM = "MD5";
    public static final int BASIC = 1;
    private static final String BASIC_AUTHORIZATION_HEADER_FORMAT = "Basic %s";
    public static final int DIGEST = 2;
    private static final String DIGEST_AUTHORIZATION_HEADER_FORMAT = "Digest username=\"%s\", realm=\"%s\", nonce=\"%s\", uri=\"%s\", response=\"%s\"";
    private static final String DIGEST_AUTHORIZATION_HEADER_FORMAT_WITH_OPAQUE = "Digest username=\"%s\", realm=\"%s\", nonce=\"%s\", uri=\"%s\", response=\"%s\", opaque=\"%s\"";
    public final int authenticationMechanism;
    public final String nonce;
    public final String opaque;
    public final String realm;

    public RtspAuthenticationInfo(int i2, String str, String str2, String str3) {
        this.authenticationMechanism = i2;
        this.realm = str;
        this.nonce = str2;
        this.opaque = str3;
    }

    private String getBasicAuthorizationHeaderValue(RtspMessageUtil.RtspAuthUserInfo rtspAuthUserInfo) {
        return Util.formatInvariant(BASIC_AUTHORIZATION_HEADER_FORMAT, Base64.encodeToString(RtspMessageUtil.getStringBytes(rtspAuthUserInfo.username + ":" + rtspAuthUserInfo.password), 0));
    }

    private String getDigestAuthorizationHeaderValue(RtspMessageUtil.RtspAuthUserInfo rtspAuthUserInfo, Uri uri, int i2) throws ParserException, NoSuchAlgorithmException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            String methodString = RtspMessageUtil.toMethodString(i2);
            String hexString = Util.toHexString(messageDigest.digest(RtspMessageUtil.getStringBytes(rtspAuthUserInfo.username + ":" + this.realm + ":" + rtspAuthUserInfo.password)));
            StringBuilder sb = new StringBuilder();
            sb.append(methodString);
            sb.append(":");
            sb.append(uri);
            String hexString2 = Util.toHexString(messageDigest.digest(RtspMessageUtil.getStringBytes(hexString + ":" + this.nonce + ":" + Util.toHexString(messageDigest.digest(RtspMessageUtil.getStringBytes(sb.toString()))))));
            return this.opaque.isEmpty() ? Util.formatInvariant(DIGEST_AUTHORIZATION_HEADER_FORMAT, rtspAuthUserInfo.username, this.realm, this.nonce, uri, hexString2) : Util.formatInvariant(DIGEST_AUTHORIZATION_HEADER_FORMAT_WITH_OPAQUE, rtspAuthUserInfo.username, this.realm, this.nonce, uri, hexString2, this.opaque);
        } catch (NoSuchAlgorithmException e2) {
            throw ParserException.createForManifestWithUnsupportedFeature(null, e2);
        }
    }

    public String getAuthorizationHeaderValue(RtspMessageUtil.RtspAuthUserInfo rtspAuthUserInfo, Uri uri, int i2) throws ParserException {
        int i3 = this.authenticationMechanism;
        if (i3 == 1) {
            return getBasicAuthorizationHeaderValue(rtspAuthUserInfo);
        }
        if (i3 == 2) {
            return getDigestAuthorizationHeaderValue(rtspAuthUserInfo, uri, i2);
        }
        throw ParserException.createForManifestWithUnsupportedFeature(null, new UnsupportedOperationException());
    }
}
