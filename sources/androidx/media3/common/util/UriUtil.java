package androidx.media3.common.util;

import android.net.Uri;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import com.google.common.base.Ascii;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.io.IOUtils;

@UnstableApi
/* loaded from: classes.dex */
public final class UriUtil {
    private static final int FRAGMENT = 3;
    private static final int INDEX_COUNT = 4;
    private static final int PATH = 1;
    private static final int QUERY = 2;
    private static final int SCHEME_COLON = 0;

    private UriUtil() {
    }

    @UnstableApi
    public static String getRelativePath(Uri uri, Uri uri2) {
        if (uri.isOpaque() || uri2.isOpaque()) {
            return uri2.toString();
        }
        String scheme = uri.getScheme();
        String scheme2 = uri2.getScheme();
        if (scheme != null ? !(scheme2 == null || !Ascii.equalsIgnoreCase(scheme, scheme2)) : scheme2 == null) {
            if (Util.areEqual(uri.getAuthority(), uri2.getAuthority())) {
                List<String> pathSegments = uri.getPathSegments();
                List<String> pathSegments2 = uri2.getPathSegments();
                int iMin = Math.min(pathSegments.size(), pathSegments2.size());
                int i2 = 0;
                for (int i3 = 0; i3 < iMin && pathSegments.get(i3).equals(pathSegments2.get(i3)); i3++) {
                    i2++;
                }
                StringBuilder sb = new StringBuilder();
                for (int i4 = i2; i4 < pathSegments.size(); i4++) {
                    sb.append("../");
                }
                while (i2 < pathSegments2.size()) {
                    sb.append(pathSegments2.get(i2));
                    if (i2 < pathSegments2.size() - 1) {
                        sb.append("/");
                    }
                    i2++;
                }
                return sb.toString();
            }
        }
        return uri2.toString();
    }

    private static int[] getUriIndices(String str) {
        int iIndexOf;
        int[] iArr = new int[4];
        if (TextUtils.isEmpty(str)) {
            iArr[0] = -1;
            return iArr;
        }
        int length = str.length();
        int iIndexOf2 = str.indexOf(35);
        if (iIndexOf2 != -1) {
            length = iIndexOf2;
        }
        int iIndexOf3 = str.indexOf(63);
        if (iIndexOf3 == -1 || iIndexOf3 > length) {
            iIndexOf3 = length;
        }
        int iIndexOf4 = str.indexOf(47);
        if (iIndexOf4 == -1 || iIndexOf4 > iIndexOf3) {
            iIndexOf4 = iIndexOf3;
        }
        int iIndexOf5 = str.indexOf(58);
        if (iIndexOf5 > iIndexOf4) {
            iIndexOf5 = -1;
        }
        int i2 = iIndexOf5 + 2;
        if (i2 < iIndexOf3 && str.charAt(iIndexOf5 + 1) == '/' && str.charAt(i2) == '/') {
            iIndexOf = str.indexOf(47, iIndexOf5 + 3);
            if (iIndexOf == -1 || iIndexOf > iIndexOf3) {
                iIndexOf = iIndexOf3;
            }
        } else {
            iIndexOf = iIndexOf5 + 1;
        }
        iArr[0] = iIndexOf5;
        iArr[1] = iIndexOf;
        iArr[2] = iIndexOf3;
        iArr[3] = length;
        return iArr;
    }

    public static boolean isAbsolute(@Nullable String str) {
        return (str == null || getUriIndices(str)[0] == -1) ? false : true;
    }

    private static String removeDotSegments(StringBuilder sb, int i2, int i3) {
        int i4;
        int iLastIndexOf;
        if (i2 >= i3) {
            return sb.toString();
        }
        if (sb.charAt(i2) == '/') {
            i2++;
        }
        int i5 = i2;
        int i6 = i5;
        while (i5 <= i3) {
            if (i5 == i3) {
                i4 = i5;
            } else if (sb.charAt(i5) == '/') {
                i4 = i5 + 1;
            } else {
                i5++;
            }
            int i7 = i6 + 1;
            if (i5 == i7 && sb.charAt(i6) == '.') {
                sb.delete(i6, i4);
                i3 -= i4 - i6;
            } else {
                if (i5 == i6 + 2 && sb.charAt(i6) == '.' && sb.charAt(i7) == '.') {
                    iLastIndexOf = sb.lastIndexOf("/", i6 - 2) + 1;
                    int i8 = iLastIndexOf > i2 ? iLastIndexOf : i2;
                    sb.delete(i8, i4);
                    i3 -= i4 - i8;
                } else {
                    iLastIndexOf = i5 + 1;
                }
                i6 = iLastIndexOf;
            }
            i5 = i6;
        }
        return sb.toString();
    }

    public static Uri removeQueryParameter(Uri uri, String str) {
        Uri.Builder builderBuildUpon = uri.buildUpon();
        builderBuildUpon.clearQuery();
        for (String str2 : uri.getQueryParameterNames()) {
            if (!str2.equals(str)) {
                Iterator<String> it = uri.getQueryParameters(str2).iterator();
                while (it.hasNext()) {
                    builderBuildUpon.appendQueryParameter(str2, it.next());
                }
            }
        }
        return builderBuildUpon.build();
    }

    public static String resolve(@Nullable String str, @Nullable String str2) {
        StringBuilder sb = new StringBuilder();
        if (str == null) {
            str = "";
        }
        if (str2 == null) {
            str2 = "";
        }
        int[] uriIndices = getUriIndices(str2);
        if (uriIndices[0] != -1) {
            sb.append(str2);
            removeDotSegments(sb, uriIndices[1], uriIndices[2]);
            return sb.toString();
        }
        int[] uriIndices2 = getUriIndices(str);
        if (uriIndices[3] == 0) {
            sb.append((CharSequence) str, 0, uriIndices2[3]);
            sb.append(str2);
            return sb.toString();
        }
        if (uriIndices[2] == 0) {
            sb.append((CharSequence) str, 0, uriIndices2[2]);
            sb.append(str2);
            return sb.toString();
        }
        int i2 = uriIndices[1];
        if (i2 != 0) {
            int i3 = uriIndices2[0] + 1;
            sb.append((CharSequence) str, 0, i3);
            sb.append(str2);
            return removeDotSegments(sb, uriIndices[1] + i3, i3 + uriIndices[2]);
        }
        if (str2.charAt(i2) == '/') {
            sb.append((CharSequence) str, 0, uriIndices2[1]);
            sb.append(str2);
            int i4 = uriIndices2[1];
            return removeDotSegments(sb, i4, uriIndices[2] + i4);
        }
        int i5 = uriIndices2[0] + 2;
        int i6 = uriIndices2[1];
        if (i5 >= i6 || i6 != uriIndices2[2]) {
            int iLastIndexOf = str.lastIndexOf(47, uriIndices2[2] - 1);
            int i7 = iLastIndexOf == -1 ? uriIndices2[1] : iLastIndexOf + 1;
            sb.append((CharSequence) str, 0, i7);
            sb.append(str2);
            return removeDotSegments(sb, uriIndices2[1], i7 + uriIndices[2]);
        }
        sb.append((CharSequence) str, 0, i6);
        sb.append(IOUtils.DIR_SEPARATOR_UNIX);
        sb.append(str2);
        int i8 = uriIndices2[1];
        return removeDotSegments(sb, i8, uriIndices[2] + i8 + 1);
    }

    public static Uri resolveToUri(@Nullable String str, @Nullable String str2) {
        return Uri.parse(resolve(str, str2));
    }
}
