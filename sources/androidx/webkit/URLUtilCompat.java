package androidx.webkit;

import android.net.Uri;
import android.webkit.MimeTypeMap;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import com.alipay.sdk.m.u.i;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* loaded from: classes2.dex */
public final class URLUtilCompat {
    private static final Pattern DISPOSITION_PATTERN = Pattern.compile("\\s*(\\S+?) # Group 1: parameter name\n\\s*=\\s* # Match equals sign\n(?: # non-capturing group of options\n   '( (?: [^'\\\\] | \\\\. )* )' # Group 2: single-quoted\n | \"( (?: [^\"\\\\] | \\\\. )*  )\" # Group 3: double-quoted\n | ( [^'\"][^;\\s]* ) # Group 4: un-quoted parameter\n)\\s*;? # Optional end semicolon", 4);

    private URLUtilCompat() {
    }

    @NonNull
    private static String encodePlusCharacters(@NonNull String str, @NonNull String str2) {
        Charset charsetForName = Charset.forName(str2);
        StringBuilder sb = new StringBuilder();
        for (byte b2 : charsetForName.encode(MqttTopic.SINGLE_LEVEL_WILDCARD).array()) {
            sb.append(String.format("%02x", Byte.valueOf(b2)));
        }
        return str.replaceAll("\\+", sb.toString());
    }

    private static boolean extensionDifferentFromMimeType(@NonNull String str, @NonNull String str2) {
        String mimeTypeFromExtension = MimeTypeMap.getSingleton().getMimeTypeFromExtension(str.substring(str.lastIndexOf(46) + 1));
        return (mimeTypeFromExtension == null || mimeTypeFromExtension.equalsIgnoreCase(str2)) ? false : true;
    }

    @Nullable
    public static String getFilenameFromContentDisposition(@NonNull String str) {
        String[] strArrSplit = str.trim().split(i.f9802b, 2);
        String extValueString = null;
        if (strArrSplit.length < 2 || "inline".equalsIgnoreCase(strArrSplit[0].trim())) {
            return null;
        }
        Matcher matcher = DISPOSITION_PATTERN.matcher(strArrSplit[1]);
        String str2 = null;
        while (matcher.find()) {
            String strGroup = matcher.group(1);
            String strRemoveSlashEscapes = matcher.group(2) != null ? removeSlashEscapes(matcher.group(2)) : matcher.group(3) != null ? removeSlashEscapes(matcher.group(3)) : matcher.group(4);
            if (strGroup != null && strRemoveSlashEscapes != null) {
                if ("filename*".equalsIgnoreCase(strGroup)) {
                    extValueString = parseExtValueString(strRemoveSlashEscapes);
                } else if ("filename".equalsIgnoreCase(strGroup)) {
                    str2 = strRemoveSlashEscapes;
                }
            }
        }
        return extValueString != null ? extValueString : str2;
    }

    @NonNull
    private static String getFilenameSuggestion(@NonNull String str, @Nullable String str2) {
        String lastPathSegment;
        String filenameFromContentDisposition;
        if (str2 != null && (filenameFromContentDisposition = getFilenameFromContentDisposition(str2)) != null) {
            return replacePathSeparators(filenameFromContentDisposition);
        }
        Uri uri = Uri.parse(str);
        return (uri == null || (lastPathSegment = uri.getLastPathSegment()) == null) ? "downloadfile" : replacePathSeparators(lastPathSegment);
    }

    @NonNull
    public static String guessFileName(@NonNull String str, @Nullable String str2, @Nullable String str3) {
        String filenameSuggestion = getFilenameSuggestion(str, str2);
        String strSuggestExtensionFromMimeType = suggestExtensionFromMimeType(str3);
        if (filenameSuggestion.indexOf(46) < 0) {
            return filenameSuggestion + strSuggestExtensionFromMimeType;
        }
        if (str3 == null || !extensionDifferentFromMimeType(filenameSuggestion, str3)) {
            return filenameSuggestion;
        }
        return filenameSuggestion + strSuggestExtensionFromMimeType;
    }

    private static String parseExtValueString(String str) {
        String[] strArrSplit = str.split("'", 3);
        if (strArrSplit.length < 3) {
            return null;
        }
        String str2 = strArrSplit[0];
        try {
            return URLDecoder.decode(encodePlusCharacters(strArrSplit[2], str2), str2);
        } catch (UnsupportedEncodingException | RuntimeException unused) {
            return null;
        }
    }

    private static String removeSlashEscapes(String str) {
        if (str == null) {
            return null;
        }
        return str.replaceAll("\\\\(.)", "$1");
    }

    @NonNull
    private static String replacePathSeparators(@NonNull String str) {
        return str.replaceAll("/", OpenAccountUIConstants.UNDER_LINE);
    }

    @NonNull
    private static String suggestExtensionFromMimeType(@Nullable String str) {
        if (str == null) {
            return ".bin";
        }
        String extensionFromMimeType = MimeTypeMap.getSingleton().getExtensionFromMimeType(str);
        if (extensionFromMimeType == null) {
            return str.equalsIgnoreCase("text/html") ? ".html" : str.toLowerCase(Locale.ROOT).startsWith("text/") ? ".txt" : ".bin";
        }
        return "." + extensionFromMimeType;
    }
}
